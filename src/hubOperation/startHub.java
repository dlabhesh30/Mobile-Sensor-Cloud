package hubOperation;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import java.util.List;
import java.util.ArrayList;
import Database.CreateConnection;

public class startHub {

	public boolean start(String instanceID){
		List<String> instance = new ArrayList<String>();
		instance.add(instanceID);
		AmazonEC2 amazonEC2Client = new AmazonEC2Client(new BasicAWSCredentials("AKIAJL5QRCJH3PZC7BTA", "TfIiDvetCvRZIl/Bry6BYwSkdbb12NHfCa3fNPaC"));
		//amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");
		DescribeInstancesRequest in_req=new DescribeInstancesRequest();
		in_req.withInstanceIds(instance);
		DescribeInstancesResult in_res=amazonEC2Client.describeInstances(in_req);
		String state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
		if(!state.equals("running")){
			StartInstancesRequest start = new StartInstancesRequest();
			start.setInstanceIds(instance);
			amazonEC2Client.startInstances(start);
		
			state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();

			while(!state.equals("running")){
				in_res=amazonEC2Client.describeInstances(in_req);
				state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
			}
			CreateConnection cr = new CreateConnection();
			boolean flag = cr.updateHubState(instanceID,  "Running");
			if(flag) return true;
			else return false;
		}
		else return true;
	}	
}