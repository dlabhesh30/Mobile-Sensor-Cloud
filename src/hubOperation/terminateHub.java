package hubOperation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

import Database.CreateConnection;

public class terminateHub {

	public boolean terminate(String instanceID){
		
		CreateConnection cr = new CreateConnection();
		
		List<String> instance = new ArrayList<String>();
		instance.add(instanceID);
		AmazonEC2 amazonEC2Client = new AmazonEC2Client(new BasicAWSCredentials("AKIAJL5QRCJH3PZC7BTA", "TfIiDvetCvRZIl/Bry6BYwSkdbb12NHfCa3fNPaC"));
		//amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");
		DescribeInstancesRequest in_req=new DescribeInstancesRequest();
		in_req.withInstanceIds(instance);
		DescribeInstancesResult in_res=amazonEC2Client.describeInstances(in_req);
		String state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
		if(state.equals("running") || state.equals("stopped")){
			TerminateInstancesRequest start = new TerminateInstancesRequest();
			start.setInstanceIds(instance);
			amazonEC2Client.terminateInstances(start);
			state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();

			while(state.equals("running") || state.equals("stopped")){
				in_res=amazonEC2Client.describeInstances(in_req);
				state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
			}
			
			cr = new CreateConnection();
			boolean flag = cr.deleteHub(instanceID);
			if(flag) return true;
			else return false;
		}
		else{
			// terminate hub
			cr = new CreateConnection();
			boolean flag = cr.deleteHub(instanceID);
			if(flag) return true;
			else return false;
		}
	}
}
