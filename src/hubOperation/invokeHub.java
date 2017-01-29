package hubOperation;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;

import Database.CreateConnection;

public class invokeHub {

	public String create(String cityName) throws InterruptedException {
		
		String state = "";
		String instanceID="";
			
		try{
			AmazonEC2 amazonEC2Client = new AmazonEC2Client(new BasicAWSCredentials("AKIAJL5QRCJH3PZC7BTA", "TfIiDvetCvRZIl/Bry6BYwSkdbb12NHfCa3fNPaC"));
			//amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");
			RunInstancesRequest runn = new RunInstancesRequest();
			runn.withImageId("ami-b73b63a0")
	                   .withInstanceType("t2.micro")
	                   .withMinCount(1)
	                   .withMaxCount(1)
	                   .withKeyName("sidKey")
	                   .withSecurityGroups("default");
				  
			RunInstancesResult result = amazonEC2Client.runInstances(runn);
			DescribeInstancesRequest in_req=new DescribeInstancesRequest();
			in_req.withInstanceIds(result.getReservation().getInstances().get(0).getInstanceId());
			DescribeInstancesResult in_res=amazonEC2Client.describeInstances(in_req);
			instanceID = in_res.getReservations().get(0).getInstances().get(0).getInstanceId().toString();
			
			
			while(!state.equals("running")){
				in_res=amazonEC2Client.describeInstances(in_req);
				state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
			}
	
			while(true){
				if(in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString()!="pending"){
					break;
				}
			}
		}catch(Exception e){
			
			System.out.println("Exception at invokeHub in create()");
			e.printStackTrace();
			return "";
		}
		
		return instanceID;
	}
}
