package SensorOperations;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import Database.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.autoscaling.model.Tag;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;

public class invokeSensor {

	public boolean create(String username, String sensorname, String source,
			String state) throws InterruptedException {
		//System.out.println("inside create()");
		// here we have to check if for same location another sensor is present or not
		// for that we will check if the sensor source is present in the db or not
		String instance_id = null;
		CreateConnection cr = new CreateConnection();
		String flag = cr.checkSameLocationSensor(source);
		if(flag == ""){
			System.out.println("Creating new sensor");
			AmazonEC2 amazonEC2Client = new AmazonEC2Client(new BasicAWSCredentials("AKIAJL5QRCJH3PZC7BTA", "TfIiDvetCvRZIl/Bry6BYwSkdbb12NHfCa3fNPaC"));
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
			instance_id = in_res.getReservations().get(0).getInstances().get(0).getInstanceId().toString();
			
			
			while(!state.equals("running")){
				in_res=amazonEC2Client.describeInstances(in_req);
				state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
			}
	
			while(true){
				if(in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString()!="pending"){
					break;
				}
			}
		}
		else{
			System.out.println("old instance used with id "+flag);
			instance_id = flag;
			List<String> instance = new ArrayList<String>();
			instance.add(flag);
			AmazonEC2 amazonEC2Client = new AmazonEC2Client(new BasicAWSCredentials("AKIAJL5QRCJH3PZC7BTA", "TfIiDvetCvRZIl/Bry6BYwSkdbb12NHfCa3fNPaC"));
			//amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");
			DescribeInstancesRequest in_req=new DescribeInstancesRequest();
			in_req.withInstanceIds(instance);
			DescribeInstancesResult in_res=amazonEC2Client.describeInstances(in_req);
			state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
			if(!state.equals("running")){
				StartInstancesRequest start = new StartInstancesRequest();
				start.setInstanceIds(instance);
				amazonEC2Client.startInstances(start);
			
				state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
	
				while(!state.equals("running")){
					in_res=amazonEC2Client.describeInstances(in_req);
					state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
				}
			}
		}
		cr = new CreateConnection();
		Date date = new Date();
		//System.out.println("after date");		
		Timestamp starttime=new Timestamp(date.getTime());
		//System.out.println("time done");
		boolean bill=cr.setBill(username,sensorname,instance_id, starttime);
		System.out.println("Instance id for the new sensor is");
		return cr.addSensor(sensorname, username, source, "Running", instance_id);
	}
}