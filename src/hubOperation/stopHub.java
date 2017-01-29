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
import com.amazonaws.services.ec2.model.StopInstancesRequest;

import Database.CreateConnection;

public class stopHub {

	public boolean stop(String instanceID){
		
		if(instanceID != null){
			
			List<String> instance = new ArrayList<String>();
			instance.add(instanceID);
			AmazonEC2 amazonEC2Client = new AmazonEC2Client(new BasicAWSCredentials("AKIAJL5QRCJH3PZC7BTA", "TfIiDvetCvRZIl/Bry6BYwSkdbb12NHfCa3fNPaC"));
			//amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");
			DescribeInstancesRequest in_req=new DescribeInstancesRequest();
			in_req.withInstanceIds(instance);
			DescribeInstancesResult in_res=amazonEC2Client.describeInstances(in_req);
			String state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
			if(!state.equals("stopped")){
				StopInstancesRequest start = new StopInstancesRequest();
				start.setInstanceIds(instance);
				amazonEC2Client.stopInstances(start);
				state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
	
				while(!state.equals("running")){
					in_res=amazonEC2Client.describeInstances(in_req);
					state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
				}
				System.out.println("hub stopped1, calling db update now");
				// now we have to update table too
				CreateConnection cr = new CreateConnection();
				return cr.updateHubState(instanceID,"Stopped");
			}
			else{
				System.out.println("hub stopped2");
				return true;
			}
		}
		else{
			System.out.println("hub not stopped1");
			return false;
		}
	}	
}