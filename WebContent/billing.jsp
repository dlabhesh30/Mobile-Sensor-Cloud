<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Billing</title>
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
            <li><a href="userdashboard.jsp"><i class="fa fa-circle-o"></i> Main Dashboard </a></li>            
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
        Invoice
        <small>#007612</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        
        <li class="active">Invoice</li>
      </ol>
    </section>

    <%
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date();
    
    
    %>
    <!-- Main content -->
    <section class="invoice">
      <!-- title row -->
      <div class="row">
        <div class="col-xs-12">
          <h2 class="page-header">
            <i class="fa fa-globe"></i> <%= session.getAttribute("username") %>
            <small class="pull-right"><%= dateFormat.format(date) %></small>
          </h2>
        </div>
        <!-- /.col -->
      </div>
      <!-- info row -->
      <div class="row invoice-info">
        <div class="col-sm-4 invoice-col">
          From
          <address>
            <strong>Admin, Inc., CityGuide</strong><br>            
            Phone: (669) 123-5432<br>
            Email: info@cityguide.com
          </address>
        </div>

<!-- retrieving user info -->
<%

Statement st= null;
Connection con = null;
String fname=null ;
String lname=null;
String email=null;
String address=null;
String mobile=null;


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
      You do not have any sensors currently.
      <%
    }
    else{
      System.out.println("in else");
      while (rs.next()) {
        fname = rs.getString("firstname");
              lname = rs.getString("lastname");
              email = rs.getString("email");
              address = rs.getString("address");
              mobile = rs.getString("mobileNumber");
    
          }
    }
  }
  
  catch(Exception e){ 
    e.printStackTrace();
    System.out.println("Exception at printing sensors");
  }
%>
        <!-- /.col -->
        <div class="col-sm-4 invoice-col">
          To,
          <address>
            <strong><%= fname %> <%= lname %></strong><br>
            <%= address%><br>
            Phone: <%= mobile%><br>
            Email: <%= email%>
          </address>
        </div>
        <!-- /.col -->
        <div class="col-sm-4 invoice-col">
          <b>Invoice #007612</b><br>
          <br>          
          <b>Payment Due:</b> 12/13/2016<br>
          <b>Account:</b> 968-34567
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->

   <%
   Double subtotal=0.0;
   Double tax=2.46;
   Double total=0.0;
  try{  
     
    String username =(String)session.getAttribute("username");    
    st=con.createStatement();
    String query1 = "select * from billing where username='"+username+"'";
    ResultSet rs = st.executeQuery(query1);
    if (!rs.isBeforeFirst()) {
      %>
      <br>
      You do not have any any outstanding amount.
      <%
    }
    else{
      System.out.println("in else");
%>    
      <!-- Table row -->
      <div class="row">
        <div class="col-xs-12 table-responsive">
          <table class="table table-striped">
            <thead>
            <tr>              
              <th>Sensor Name</th>
              <th>Sensor Id</th>
              <th>Start Time</th>
              <th>End Time</th>
              <th>Cost</th>
            </tr>
            </thead>                 
<%    
      while (rs.next()) {              
              String sensorname = rs.getString("sensorname");
              String instanceid = rs.getString("instanceid");
              Timestamp starttime = rs.getTimestamp("starttime");
              Timestamp endtime = rs.getTimestamp("endtime");
              Double cost=rs.getDouble("cost");
              subtotal=subtotal+cost;
              total=subtotal+tax;
%>        <tbody>
                  <tr>
                    <td><%= sensorname %></td>
                    <td><%= instanceid %></td>
                    <td><%= starttime %></td> 
                    <td><%= endtime %></td>                   
                    <td>$ <%= cost %></td>                   
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
</tbody>
                </table>
              
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->

      <div class="row">
        <!-- accepted payments column -->
        <div class="col-xs-6">
          <p class="lead">Payment Methods:</p>
          <img src=" dist/img/credit/visa.png" alt="Visa">
          <img src=" dist/img/credit/mastercard.png" alt="Mastercard">
          <img src=" dist/img/credit/american-express.png" alt="American Express">
          <img src=" dist/img/credit/paypal2.png" alt="Paypal">          
        </div>
        <!-- /.col -->
        <div class="col-xs-6">
          <p class="lead">Amount Due 12/22/2016</p>

          <div class="table-responsive">
            <table class="table">
              <tr>
                <th style="width:50%">Subtotal:</th>
                <td>$ <%= subtotal %> </td>
              </tr>
              <tr>
                <th>Tax (9.3%)</th>
                <td>$ <%= tax %></td>
              </tr>
              
              <tr>
                <th>Total:</th>
                <td>$ <%= total %></td>
              </tr>
            </table>
          </div>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->

      <!-- this row will not appear when printing -->
      <div class="row no-print">
        <div class="col-xs-12">
          <a href="invoice-print.jsp" target="_blank" class="btn btn-default"><i class="fa fa-print"></i> Print</a>
          <button type="button" class="btn btn-success pull-right"><i class="fa fa-credit-card"></i> Submit Payment
          </button>
          
        </div>
      </div>
    </section>
    <!-- /.content -->
    <div class="clearfix"></div>
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer no-print">
    <div class="pull-right hidden-xs">
      <b>Version</b> 2.3.8
    </div>
    <strong>Copyright &copy; 2016 Labhesh Deshpande.</strong> All rights
    reserved.
  </footer>


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
