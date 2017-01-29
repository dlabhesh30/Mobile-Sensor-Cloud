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
  <title>View Hubs</title>
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
            <li><a href="userdashboard.jsp"><i class="fa fa-circle-o"></i>Main Dashboard</a></li>            
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
        View Hubs
        
      </h1>
      <ol class="breadcrumb">
        <li><a href="userdashboard.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"> view sensors</li>
      </ol>
    </section>

<br><br>


<%

Statement st= null;
Connection con = null;
boolean flag =false;

	try{  
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(  
				"jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3147883","sql3147883","2yC7asI8Rs");
		
		String username =(String)session.getAttribute("username");
		
		st=con.createStatement();
		String query = "select * from sensortable";
		ResultSet rs = st.executeQuery(query);
		if (!rs.isBeforeFirst()) {
			%>
			<br>
			You do not have any sensors currently.
			<%
		}
		else{
			flag = true;
			System.out.println("in else");
%>			
			<!-- TABLE: LATEST ORDERS -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">View Hubs</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <div class="table-responsive">
                <table class="table no-margin">
                  <thead>
                  <tr>
                    <th>Sensor Name</th>
                    <th>Source</th>
                    <th>State</th>
                    <th>Hub Name</th>                    
                  </tr>
                  </thead>                  
<%		
			while (rs.next()) {
            	String sensorname = rs.getString("sensorid");
            	String source = rs.getString("source");
            	String state = rs.getString("State");
            	String hubname = rs.getString("hubname");
%>  			<tbody>
                  <tr>
                    <td><%= sensorname %></td>
                    <td><%= source %></td>
                    <td><span class="label label-success"><%= state %></span></td>
                    <td><%= hubname %></td>                    
                  </tr>                  
                  
        <!-- /.col -->              
<%			
        	}
		}
	}
	catch(Exception e){ 
		e.printStackTrace();
		System.out.println("Exception at printing sensors");
	}
%>

<hr>
 </tbody>
                </table>
<br><br><hr>
               <h2 align="center" class="box-title">Manage all sensors</h2>
               <div class="box box-info">
            <div class="box-header with-border">
              
            </div>
            <!-- /.box-header -->
            <!-- form start -->
             
                        </div>                    

		

<%
	
	if(flag){
		%>
		<br>
            <div class="box-header with-border">
              
            </div>
            <!-- /.box-header -->
            <!-- form start -->
             <h3 align="center"> Choose an action from the drop down to manage existing sensors<br></h3>
             
            <form class="form-horizontal" action="manage">
              <div class="box-body">
                <div class="form-group">
                  <label for="inputEmail3" class="col-sm-2 control-label">Sensor Name</label>

                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputEmail3" name="sensor" placeholder="Enter Sensor Name">
                  </div>
                </div>
                <div class="form-group">					              
               
                               
              </div>
              <input type="radio" name="operation" value="Start"> Start
  				<input type="radio" name="operation" value="Stop"> Stop
  				<input type="radio" name="operation" value="Terminate"> Terminate<br>
              <div class="box-footer">                
                <button type="submit" class="btn btn-info pull-right" name="Go">Go</button>
              </div>
              <!-- /.box-footer -->
            </form>
        
              </div> 
		</div>
		
		<%
	}
	
%>

</div></div>

              </div>           
        </div>
        </div>

<!-- jQuery 2.2.3 -->
<script src="plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="bootstrap/js/bootstrap.min.js"></script>
<!-- FastClick -->
<script src="plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/app.min.js"></script>
<!-- Sparkline -->
<script src="plugins/sparkline/jquery.sparkline.min.js"></script>
<!-- jvectormap -->
<script src="plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- SlimScroll 1.3.0 -->
<script src="plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- ChartJS 1.0.1 -->
<script src="plugins/chartjs/Chart.min.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script src="dist/js/pages/dashboard2.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="dist/js/demo.js"></script>
</body>
</html>