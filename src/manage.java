import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import SensorOperations.*;
import Database.*;

public class manage extends HttpServlet {
	   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sensor = request.getParameter("sensor");
		String operation = request.getParameter("operation");
		CreateConnection cr = new CreateConnection();
		String instanceid = cr.getInstanceID(sensor);
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		

		//System.out.println("instance id is "+instanceid);
		
		if(instanceid!=null){
			if(operation.equals("Start")){
				startSensor start = new startSensor();
				if(start.start(instanceid, username,sensor)){
					response.sendRedirect("successuser.jsp");
				}
				else{
					response.sendRedirect("failureuser.jsp");
				}
			}
			if(operation.equals("Stop")){
				System.out.println("inside stop in manage.java");
				stopSensor stop = new stopSensor();
				if(stop.stop(instanceid, username, sensor)){
					System.out.println("stop method returned true in manage.java");
					response.sendRedirect("successuser.jsp");
				}
				else{
					response.sendRedirect("failureuser.jsp");
				}
			}
			if(operation.equals("Terminate")){
				terminateSensor terminate = new terminateSensor();
				if(terminate.terminate(instanceid, username, sensor)){
					response.sendRedirect("successuser.jsp");
				}
				else{
					response.sendRedirect("failureuser.jsp");
				}
			}

		}
		else{
			PrintWriter out = response.getWriter();
            out.print("Invalid Instance ID. Please try again later.");
		}
	}
}