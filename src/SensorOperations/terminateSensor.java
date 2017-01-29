package SensorOperations;

import hubOperation.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

import Database.CreateConnection;
import hubOperation.stopHub;



public class terminateSensor {

	public boolean terminate(String instanceID, String username, String sensorname){
		
		CreateConnection cr = new CreateConnection();
		if(cr.checkSameInstanceSensorTerminate(instanceID)){
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
				// terminate hub
				System.out.println("1");
				cr = new CreateConnection();
				System.out.println("2");
				Date date = new Date();
				System.out.println("3");
				Timestamp endtime=new Timestamp(date.getTime());
				System.out.println("4");
				boolean bill=cr.updateBill(username, instanceID, endtime);
				System.out.println("5");
				String hubid = cr.getHubIDBySensor(instanceID, username);
				System.out.println("6");
				boolean flag = cr.deleteSensor(instanceID, username);
				if(flag){
					System.out.println("7");
					
					cr = new CreateConnection();
					System.out.println("hub id is"+hubid);
					terminateHub sh = new terminateHub();
					flag = sh.terminate(hubid);
					if(flag) return true;
					else return false;
				}
				else return false;
			}
			else{
				// terminate hub
				
				
				boolean flag = cr.deleteSensor(instanceID, username);
				Date date = new Date();
				Timestamp endtime=new Timestamp(date.getTime());
				boolean bill=cr.updateBill(username,instanceID, endtime);
				if(flag) return true;
				else return false;
			}
		}
		else{
			Date date = new Date();
			Timestamp endtime=new Timestamp(date.getTime());
			boolean bill=cr.updateBill(username,instanceID, endtime);
			boolean flag = cr.deleteSensor(instanceID, username);
			if(flag) return true;
			else return false;
		}
	}
}