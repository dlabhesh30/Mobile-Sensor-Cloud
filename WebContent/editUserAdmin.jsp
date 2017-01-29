<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.util.Date" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Edit User</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- jvectormap -->
  <link rel="stylesheet" href="plugins/jvectormap/jquery-jvectormap-1.2.2.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">
  <link rel="stylesheet" href="dist/css/skins/custom.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <header class="main-header">

    <!-- Logo -->
    <a href="userdashboard.jsp" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>A</b>LT</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>City Guide</b></span>
    </a>

    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs"><%= session.getAttribute("username") %></span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">

              </li>              
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat">Profile</a>
                </div>
                <div class="pull-right">
                  <a href="index.jsp" class="btn btn-default btn-flat">Sign out</a>
                </div>
              </li>
            </ul>
          </li>
          
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p><%= session.getAttribute("username") %></p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">MAIN NAVIGATION</li>
        <li class="active treeview">
          <a href="#">
            <i class="fa fa-dashboard"></i> <span>Dashboard</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="adminDashboard.jsp"><i class="fa fa-circle-o"></i> Admin Dashboard</a></li>            
          </ul>
        </li>
        
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        User Manager  
        
      </h1>
      <ol class="breadcrumb">
        <li><a href="userdashboard.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">User Manager</li>
      </ol>
    </section>

<br><br>
<form action="changeDetailsByAdmin">
<p align="center">
Welcome <%= session.getAttribute("username") %><br><br>
Current values are displayed in the text-boxes. Update values you want to edit.<br>
The username field cannot be edited.<br>
</p>
<%

Statement st= null;
Connection con = null;

	try{  
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(  
				"jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3147883","sql3147883","2yC7asI8Rs");
		
		String username =(String)request.getParameter("user");
		System.out.println("user here is"+username);
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
			<table align="center">
				<tr>
					<td>First Name: </td>
					<td><input type="text" name="firstname" value="<%= rs.getString("firstname")%> "/></td>
				</tr>
				<tr>
					<td>Last Name: </td>
					<td><input type="text" name="lastname" value="<%= rs.getString("lastname")%>"/></td>
				</tr>
				<tr>
					<td>Username: </td>
					<td><%= rs.getString("username")%></td>
				</tr>
				<tr>
					<td>Password: </td>
					<td><input type="password" name="password" value="<%= rs.getString("password")%>"/></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" value="<%= rs.getString("email")%>"/></td>
				</tr>
				<tr>
					<td>Address: </td>
					<td><input type="text" name="address" value="<%= rs.getString("address")%>"/></td>
				</tr>
				<tr>
					<td>Mobile Number: </td>
					<td><input type="text" name="mobileNumber" value="<%= rs.getString("mobileNumber")%>"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center">Payment Details<br></td>
				</tr>
				<tr>
					<td>Credit Card Number:</td>
					<td> <input type="text" name="creditCardDetails" value="<%= rs.getString("creditCardDetails")%>"/></td>
				</tr>
				<tr>
					<td>Security Pin: </td>
					<td><input type="text" name="securityNumber" value="<%= rs.getString("securityNumber")%>"/></td>
				</tr>
				<tr>
					<td>Credit Card Number:</td>
					<td> <input type="text" name="creditCardDetails" value="<%= rs.getString("creditCardDetails")%>"/></td>
				</tr>
				<tr>
					<td>Expiry Date: </td>
					<td> <input type="text" name="expiryDate" value="<%= rs.getString("expiryDate")%>"/></td>
				</tr>
				<tr>
					<td>Name On Card: </td>
					<td> <input type="text" name="nameOnCard" value="<%= rs.getString("nameOnCard")%>"/><br></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Edit Changes"/></td>
				</tr>
				<input type="hidden" name="username" value="<%= username%>"/>
			</table>
<%		}
	}catch(Exception e){
		System.out.println("Exception in editUserAdmin.jsp");
		e.printStackTrace();
	}
	
%>
</form>
</body>
</html>