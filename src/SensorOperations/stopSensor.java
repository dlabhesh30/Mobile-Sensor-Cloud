package SensorOperations;

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
import com.amazonaws.services.ec2.model.StopInstancesRequest;

import Database.CreateConnection;
import hubOperation.*;

public class stopSensor {
	
	public boolean stop(String instanceID, String username,String sensorname){
		
		CreateConnection cr = new CreateConnection();
		System.out.println("instance id before for stop: "+instanceID);
		if(cr.checkSameInstanceSensor(instanceID)){
			System.out.println("1 stopSensor");
			List<String> instance = new ArrayList<String>();
			instance.add(instanceID);
			AmazonEC2 amazonEC2Client = new AmazonEC2Client(new BasicAWSCredentials("AKIAJL5QRCJH3PZC7BTA", "TfIiDvetCvRZIl/Bry6BYwSkdbb12NHfCa3fNPaC"));
			//amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");
			DescribeInstancesRequest in_req=new DescribeInstancesRequest();
			in_req.withInstanceIds(instance);
			DescribeInstancesResult in_res=amazonEC2Client.describeInstances(in_req);
			String state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
			if(!state.equals("stopped")){
				System.out.println("2 stopSensor");
				StopInstancesRequest start = new StopInstancesRequest();
				start.setInstanceIds(instance);
				amazonEC2Client.stopInstances(start);
				state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
	
				while(!state.equals("running")){
					in_res=amazonEC2Client.describeInstances(in_req);
					state=in_res.getReservations().get(0).getInstances().get(0).getState().getName().toString();
				}
				cr = new CreateConnection();
				Date date = new Date();
				Timestamp endtime=new Timestamp(date.getTime());
				System.out.println("3 stopSensor");
				boolean bill=cr.updateBill(username,instanceID, endtime);
				System.out.println("3.2 stopSensor");

				boolean flag = cr.updateState(instanceID, username, "Stopped");
				System.out.println("3.3 stopSensor");

				// now we have to stop hub as well
				
				cr = new CreateConnection();
				String hubid = cr.getHubName(username, sensorname);
				stopHub sh = new stopHub();
				flag = sh.stop(hubid);
				if(flag) return true;
				else return false;
			}
			else{
				System.out.println("4 stopSensor");
				cr = new CreateConnection();
				Date date = new Date();
				Timestamp endtime=new Timestamp(date.getTime());
				boolean bill=cr.updateBill(username,instanceID, endtime);
				boolean flag = cr.updateState(instanceID, username, "Stopped");
				if(flag) return true;
				else return false;
			}
		}
		else{
			System.out.println("5 stopSensor");
			Date date = new Date();
			Timestamp endtime=new Timestamp(date.getTime());
			boolean bill=cr.updateBill(username,instanceID, endtime);
			boolean flag = cr.updateState(instanceID, username, "Stopped");
			if(flag) return true;
			else return false;
		}
	}
}