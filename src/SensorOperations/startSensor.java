package SensorOperations;

import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;

import Database.CreateConnection;
import hubOperation.stopHub;
import hubOperation.*;


public class startSensor {

	public boolean start(String instanceID, String username, String sensorname){
		
		// here we have to check if the instance id of the sensor is related to some other user or not
		// if related we will start sensor but will update only the username row for that sensor
		// else we can start the only row
		
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
			Date date = new Date();
			System.out.println("after date");		
			Timestamp starttime=new Timestamp(date.getTime());
			System.out.println("time done");
			System.out.println("startSensor()");
			System.out.println("instance id"+instanceID);
			System.out.println("username"+username);
			System.out.println("sensorname"+sensorname);
			
			boolean bill=cr.setBill(username,sensorname,instanceID, starttime);
			System.out.println("calling update state from startSensor()");
			boolean flag2 = cr.updateState(instanceID, username, "Running");
			if(flag2){
				System.out.println("Returned true");
				
				cr = new CreateConnection();
				String hubid = cr.getHubName(username, sensorname);
				startHub sh = new startHub();
				boolean flag = sh.start(hubid);
				if(flag) return true;
				else return false;
			}
			else return false;
		}
		else{
			
			CreateConnection cr = new CreateConnection();
			Date date = new Date();
			System.out.println("after date");		
			Timestamp starttime=new Timestamp(date.getTime());
			System.out.println("time done");
			System.out.println("startSensor()");
			System.out.println("instance id"+instanceID);
			System.out.println("username"+username);
			System.out.println("sensorname"+sensorname);
			
			boolean bill=cr.setBill(username,sensorname,instanceID, starttime);
			
			System.out.println("calling update state from startSensor()");
			
			boolean flag2 = cr.updateState(instanceID, username, "Running");
			if(flag2){
				System.out.println("Returned true");
				cr = new CreateConnection();
				String hubid = cr.getHubName(username, sensorname);
				startHub sh = new startHub();
				boolean flag = sh.start(hubid);
				if(flag) return true;
				else return false;
			}
			else return false;
		}
	}	
}