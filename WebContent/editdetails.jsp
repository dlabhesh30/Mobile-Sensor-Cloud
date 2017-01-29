<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="changeDetails">
Welcome <%= session.getAttribute("username") %><br><br>
Current values are displayed in the text-boxes. Update values you want to edit.<br>
The username field cannot be edited.<br>
<%

Statement st= null;
Connection con = null;

	try{  
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(  
				"jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3147883","sql3147883","2yC7asI8Rs");
		
		String username =(String)session.getAttribute("username");
		st=con.createStatement();
		String query = "select * from users where username='"+username+"'";
		ResultSet rs = st.executeQuery(query);
		if (!rs.isBeforeFirst()) {
			%>
			<br>
			No data recovered.
			<%
		}
		else{
			rs.next();
%>
			First Name: <input type="text" name="firstname" value="<%= rs.getString("firstname")%> "/><br>
			Last Name: <input type="text" name="lastname" value="<%= rs.getString("lastname")%>"/><br>
			Username: <%= rs.getString("username")%><br>
			Password: <input type="password" name="password" value="<%= rs.getString("password")%>"/><br>
			Email: <input type="text" name="email" value="<%= rs.getString("email")%>"/><br>
			Address: <input type="text" name="address" value="<%= rs.getString("address")%>"/><br>
			Mobile Number: <input type="text" name="mobileNumber" value="<%= rs.getString("mobileNumber")%>"/><br><br>
			Payment Details-<br>
			Credit Card Number: <input type="text" name="creditCardDetails" value="<%= rs.getString("creditCardDetails")%>"/><br>
			Security Pin: <input type="text" name="securityNumber" value="<%= rs.getString("securityNumber")%>"/><br>
			Expiry Date: <input type="text" name="expiryDate" value="<%= rs.getString("expiryDate")%>"/><br>
			Name On Card: <input type="text" name="nameOnCard" value="<%= rs.getString("nameOnCard")%>"/><br>
			<input type="submit" value="Edit Changes"/>
<%		}
	}catch(Exception e){
		System.out.println("Exception in editdetails.jsp");
		e.printStackTrace();
	}
	
%>
</form>
   <!-- jQuery -->

    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->

    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>



    <!-- Theme JavaScript -->

    <script src="js/clean-blog.min.js"></script>
</body>
</html>