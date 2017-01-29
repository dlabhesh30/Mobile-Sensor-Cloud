package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import SensorOperations.*;
import hubOperation.*;

public class CreateConnection {

	Statement st= null;
	Connection con = null;
	
	public CreateConnection(){
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(  
					"jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3147883","sql3147883","2yC7asI8Rs");
		}
		catch(Exception e){ 
			e.printStackTrace();			
		}
	}
	
	public boolean checkUser(String name, String password){  
		
		try{
			String pass ="";
			st=con.createStatement();
			String query = "select password from users where username='"+name+"'";
			ResultSet rs = st.executeQuery(query);
		
			while (rs.next()) {
	            pass = rs.getString("password");
	            break;
	        }
			
			if(pass.equals(password))
				return true;
			
			return false;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in checkUser");
			e.printStackTrace();
		}
		return false;
	} 	 
		
	public boolean insert(String firstname, String lastname, String username, String password, 
		String email, String address, String mobilenumber, String creditCardDetails, 
		String SecurityNumber, String ExpiryDate, String NameOnCard){
		
		Statement stmt = null;
	    try {
	    	con.setAutoCommit(false);
	        stmt = con.createStatement();
	    	
	        String query = " insert into users (firstname, lastname, username, password, email,"
	        		+ "address,mobileNumber,creditCardDetails, securityNumber, expiryDate, nameOnCard)"
	                + " values (?, ?, ?, ?, ?,?,?,?,?,?,?)";
	    	
	        PreparedStatement preparedStmt = con.prepareStatement(query);
	        preparedStmt.setString (1, firstname);
	        preparedStmt.setString (2, lastname);
	        preparedStmt.setString (3, username);
	        preparedStmt.setString (4, password);
	        preparedStmt.setString (5, email);
	        preparedStmt.setString (6, address);
	        preparedStmt.setString (7, mobilenumber);
	        preparedStmt.setString (8, creditCardDetails);
	        preparedStmt.setString (9, SecurityNumber);
	        preparedStmt.setString (10, ExpiryDate);
	        preparedStmt.setString (11, NameOnCard);
            preparedStmt.execute();
  	    	con.commit();
            con.close();
            return true;
	    }catch(Exception b){
			System.out.println("Exception at createConnection.java in insert");
	    	b.printStackTrace();
	    } 
	    return false;
	}
	
	public ResultSet getSensors(String username){
		
		try{
			String pass ="";
			st=con.createStatement();
			String query = "select * from sensortable where username='"+username+"'";
			ResultSet rs = st.executeQuery(query);
			return rs;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in getSensors");
			e.printStackTrace();
		}
		return null;
	}

	public boolean addSensor(String sensorname, String username, 
		String source, String state, String instanceid){
		//System.out.println("1");
		String[] hub = source.split(",");
		String hubName = null;//"Hub_"+(hub[hub.length-3]).trim();
		
		if(hub.length > 3){
			hubName = "Hub_"+(hub[hub.length-3]).trim();
		}
		else hubName = hub[0];
		
		boolean checkHub = checkHub(hubName);
		//System.out.println("2");
		
		if(!checkHub)
			createHub(hubName);
	//	System.out.println("3");
		
	    try {
//	    	System.out.println("4a");
				con.setAutoCommit(false);
	        String query = " insert into sensortable (sensorid, username, source, State,"
	        		+ "instanceid, hubname) values (?, ?, ?, ?, ?, ?)";
	        PreparedStatement preparedStmt = con.prepareStatement(query);
	        preparedStmt.setString (1, sensorname);
	        preparedStmt.setString (2, username);
	        preparedStmt.setString (3, source);
	        preparedStmt.setString (4, state);
	        preparedStmt.setString (5, instanceid);
	        preparedStmt.setString (6, hubName);
		    preparedStmt.execute();
		    //System.out.println("5");
			con.commit();
			//System.out.println("6");
			con.close();
			//System.out.println("7");
			return true;
	    }catch(Exception b){
			System.out.println("Exception at createConnection.java in addSensor()");
	    	b.printStackTrace();
	    } 
	    return false;
	}

	public String getInstanceID(String sensorname){
		
		try{
			String instanceID ="";
			st=con.createStatement();
			String query = "select instanceid from sensortable where sensorid='"+sensorname+"'";
			ResultSet rs = st.executeQuery(query);
		
			while (rs.next()) {
				instanceID = rs.getString("instanceid");
	            break;
	        }
			return instanceID;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in getInstanceID");
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateState(String instanceid, String username, String state){
		System.out.println("updateState()");
		System.out.println("username is"+username);
		System.out.println("instance id"+instanceid);
		System.out.println("state"+state);
		
		String query = "update sensortable set State = ? where instanceid = ? and username = ?";
		try{
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, state);
			preparedStmt.setString(2, instanceid);
			preparedStmt.setString(3, username);
			preparedStmt.executeUpdate();
			if(preparedStmt.getUpdateCount() >-1)
			//con.close();
			return true;
			else return false;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in updateState()");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteSensor(String instanceid, String username){
		
		String query = "delete from sensortable where instanceid = ? and username = ?";
		try{
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, instanceid);
			preparedStmt.setString(2, username);
			preparedStmt.execute();
			con.close();
			return true;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in deleteSensor");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean editDetails(String firstname, String lastname, String username, String password, 
			String email, String address, String mobilenumber, String creditCardDetails, 
			String SecurityNumber, String ExpiryDate, String NameOnCard){
		
		Statement stmt = null;
	    try {
	    	con.setAutoCommit(false);
	        stmt = con.createStatement();
	    	// String query = "update sensortavle set State = ? where instanceid = ?";
	        String query = " update users set firstname = ?, lastname = ?, password = ?, email= ?,"
	        		+ "address = ?, mobileNumber = ?, creditCardDetails = ?, securityNumber = ?,"
	        		+ "expiryDate = ?, nameOnCard = ? where username = ?";
	    	
	        PreparedStatement preparedStmt = con.prepareStatement(query);
	        preparedStmt.setString (1, firstname);
	        preparedStmt.setString (2, lastname);
	        preparedStmt.setString (3, password);
	        preparedStmt.setString (4, email);
	        preparedStmt.setString (5, address);
	        preparedStmt.setString (6, mobilenumber);
	        preparedStmt.setString (7, creditCardDetails);
	        preparedStmt.setString (8, SecurityNumber);
	        preparedStmt.setString (9, ExpiryDate);
	        preparedStmt.setString (10, NameOnCard);
	        preparedStmt.setString (11, username);
            preparedStmt.executeUpdate();
  	    	con.commit();
            con.close();
            return true;
	    }catch(Exception b){
			System.out.println("Exception at createConnection.java in editDetails");
	    	b.printStackTrace();
	    } 
	    return false;
	}
	
	public boolean setBill(String username,String sensorname,String instanceid,Timestamp starttime){
		System.out.println("in set bill");
		Timestamp endtime=null;
		double cost= 0.15;
		
	    try {
	    	con.setAutoCommit(false);
	    	System.out.println("query now");
	        String query = " insert into billing (username,sensorname, instanceid,starttime, endtime,"
	        		+ "cost) values (?, ?, ?, ?, ?,?)";
	        PreparedStatement preparedStmt = con.prepareStatement(query);
	        //System.out.println("1 here");
	        preparedStmt.setString (1, username);
	        //System.out.println("2 here");
	        preparedStmt.setString (2, sensorname);
	        preparedStmt.setString (3, instanceid);
	        //System.out.println("3 here");
	        preparedStmt.setTimestamp (4, starttime);
	        //System.out.println("4 here");
	        preparedStmt.setTimestamp (5, endtime);
	        //System.out.println("5 here");
	        preparedStmt.setDouble (6, cost);
	        //System.out.println("6 here");
	        preparedStmt.execute();
	        //System.out.println("committing");
	        con.commit();
	        //System.out.println("done");
            
            
            return true;
	    }catch(Exception b){
			System.out.println("Exception at setBill");
			System.err.println(b.getMessage());
			
	    } 
	    return false;
		
	}
	
	public boolean updateBill(String username,String instanceid,Timestamp endtime){		
		//System.out.println("in update bill");
		double cost= 0;
		Statement stmt = null;
		Timestamp starttime = null;
	    try {
			//System.out.println("1 updateTime");

			st=con.createStatement();
			String query1 = "select starttime from billing where instanceid='"+instanceid+"' and username='"+username+"'";
		//	System.out.println("1 updateTime");
			ResultSet rs = st.executeQuery(query1);		
			while (rs.next()) {
	//			System.out.println("2 updateTime");
				starttime = rs.getTimestamp("starttime");
	            break;
	        }
	    }catch(Exception b){
			System.out.println("Exception at createConnection.java in updateBill");
	    	b.printStackTrace();
	    } 
//		System.out.println("3 updateTime");
			long duration  = endtime.getTime() - starttime.getTime();
			//System.out.println(duration);
			long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		//	System.out.println(diffInSeconds);
			cost=0.15+(diffInSeconds*0.0000418);		
	//		System.out.println("4 updateTime");

	        String query = "update billing set endtime = ?,cost = ? where instanceid = ? and username=?";
			try{
				//System.out.println("5 updateTime");

				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setTimestamp(1, endtime);
				preparedStmt.setDouble(2, cost);
				preparedStmt.setString(3, instanceid);
				preparedStmt.setString(4, username);
				preparedStmt.executeUpdate();
				//System.out.println("6 updateTime");

				return true;
			}catch(Exception e){
				System.out.println("Exception at createConnection.java in updateState");
				e.printStackTrace();
			}		   
	    
	    return false;
		
	}
	
	public String getCurrentLocation(String username, String sensorname){
		
		try{
			String source ="";
			st=con.createStatement();
			String query = "select source from sensortable where sensorid='"+sensorname+"' and username='"+username+"'";
			ResultSet rs = st.executeQuery(query);
		
			while (rs.next()) {
				source = rs.getString("source");
	            break;
	        }
			return source;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in getCurrentLocation");
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteUser(String username){
		
		String query = "delete from users where username = ?";
		try{
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, username);
			preparedStmt.execute();
			con.close();
			return true;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in deleteUser");
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkHub(String cityName){
		
		try{
			String getCityName="";
			st=con.createStatement();
			String query = "select * from hubtable";
			ResultSet rs = st.executeQuery(query);
		
			while (rs.next()) {
				getCityName = rs.getString("hubname");
				if(getCityName.equals(cityName)){
					return true;
				}
	        }
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in checkHub");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean createHub(String cityName){
		
		invokeHub newHub = new invokeHub();
		
		try{
			String instanceID = newHub.create(cityName);
			if(instanceID.length()==0) return false;
			
	    	con.setAutoCommit(false);
	        
	        String query = " insert into hubtable (hubname, hubid, instanceid, state)"
	                + " values (?,?, ?,?)";
	    	
	        PreparedStatement preparedStmt = con.prepareStatement(query);
	        preparedStmt.setString (1, cityName);
	        preparedStmt.setString (2, "default");
	        preparedStmt.setString (3, instanceID);
	        preparedStmt.setString (4, "Running");
            preparedStmt.execute();
  	    	con.commit();
            
            return true;
	    }catch(Exception b){
			System.out.println("Exception at createConnection.java in createHub");
	    	b.printStackTrace();
	    }
		return false;
	}
	
	public String getHubID(String hubname){
		//System.out.println("1 getHubID");

		String hubid="";
		try{
			//System.out.println("1 getHubID");
			st=con.createStatement();
			String query = "select instanceid from hubtable where hubname='"+hubname+"'";
			ResultSet rs = st.executeQuery(query);
			//System.out.println("2 getHubID");
			while (rs.next()) {
			    hubid = rs.getString("instanceid");
	            break;
	        }
			//System.out.println("3 getHubID");
			return hubid;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in getHubID()");
			e.printStackTrace();
		}
		return null;
	}
	
	public String checkSameLocationSensor(String source){
		
		try{
			st=con.createStatement();
			String query = "select * from sensortable where source='"+source+"'";
			ResultSet rs = st.executeQuery(query);
			
			if (!rs.isBeforeFirst() ) {    
			    return ""; // this means no sensor at that position is present
			}
			else{
				rs.next();
				String instanceid = rs.getString("instanceid");
				
				return instanceid; // is present
			}
			
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in checkSameLocationSensor()");
			e.printStackTrace();
		}
		return "";
	}

	public boolean checkSameInstanceSensor(String instanceID){
		
		try{
			st=con.createStatement();
			String query = "select * from sensortable where instanceid='"+instanceID+"'";
			ResultSet rs = st.executeQuery(query);
			int i=0;
			while (rs.next() ) {    
			    String state = rs.getString("state");
				if(state.equals("Running")){
			    	i++;
			    }
			}
			if(i>1) return false;
			else return true;
			
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in checkSameInstanceSensor()");
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkSameInstanceSensorTerminate(String instanceID){
	
		try{
			st=con.createStatement();
			String query = "select * from sensortable where instanceid='"+instanceID+"'";
			ResultSet rs = st.executeQuery(query);
			int i=0;
			while (rs.next() ) {    
			    i++;
			}
			if(i>1) return false;
			else return true;
			
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in checkSameInstanceSensor()");
			e.printStackTrace();
		}
		return false;
	}
	
	public String getHubName(String username, String sensorname){
		
		try{
			String hubname ="";
			st=con.createStatement();
			String query = "select hubname from sensortable where sensorid='"+sensorname+"' and username='"+username+"'";
			ResultSet rs = st.executeQuery(query);
			//System.out.println("1 getHubName");

			while (rs.next()) {
				hubname = rs.getString("hubname");
	            break;
	        }
			//System.out.println("2 getHubName");

			String hubid = getHubID(hubname);
			//System.out.println("3 getHubName");

			return hubid;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in getCurrentLocation");
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateHubState(String instanceid, String state){
		
		String query = "update hubtable set State = ? where instanceid = ?";
		try{
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, state);
			preparedStmt.setString(2, instanceid);
			
			preparedStmt.executeUpdate();
			con.close();
			return true;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in updateHubState()");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteHub(String instanceid){
		
		String query = "delete from hubtable where instanceid = ?";
		try{
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, instanceid);
			preparedStmt.execute();
			con.close();
			return true;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in deleteHub()");
			e.printStackTrace();
		}
		return false;
	}
	
	public String getHubIDBySensor(String instanceid, String username){
		
		try{
			String hubname ="";
			st=con.createStatement();
			System.out.println("instance id"+instanceid);
			System.out.println("username id"+username);
			String query = "select * from sensortable where username='"+username+"' and instanceid='"+instanceid+"'";
			System.out.println("executing query");
			ResultSet rs = st.executeQuery(query);
			System.out.println("after query");
			
			while (rs.next()) {
				System.out.println("in");
				
				hubname = rs.getString("hubname");
	            break;
	        }
			System.out.println("hubname is"+hubname);
			String hubid = getHubID(hubname);
			System.out.println("hubid in getHub is"+hubid);
			return hubid;
		}catch(Exception e){
			System.out.println("Exception at createConnection.java in getHubIDBySensor");
			e.printStackTrace();
		}
		return "";
	}
}