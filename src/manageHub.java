import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.CreateConnection;
import SensorOperations.startSensor;
import SensorOperations.stopSensor;
import SensorOperations.terminateSensor;
import hubOperation.*;


@WebServlet("/manageHub")
public class manageHub extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String hub = request.getParameter("hub");
		String operation = request.getParameter("operation");
		CreateConnection cr = new CreateConnection();
		String hubid = cr.getHubID(hub);
		System.out.println("instance id is "+hubid);
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		
		if(hubid!=null){
			if(operation.equals("Start")){
				startHub start = new startHub();
				if(start.start(hubid)){
					PrintWriter out = response.getWriter();
		            out.print("Start operation completed successfully.");
				}
				else{
					PrintWriter out = response.getWriter();
		            out.print("Start operation failed. Please try again later.");
				}
			}
			if(operation.equals("Stop")){
				stopSensor stop = new stopSensor();
				if(stop.stop(hubid, username,hub)){
					PrintWriter out = response.getWriter();
		            out.print("Stop operation completed successfully.");
				}
				else{
					PrintWriter out = response.getWriter();
		            out.print("Stop operation failed. Please try again later.");
				}
			}
			if(operation.equals("Terminate")){
				terminateSensor terminate = new terminateSensor();
				if(terminate.terminate(hubid, username, "")){
					PrintWriter out = response.getWriter();
		            out.print("Terminate operation completed successfully.");
				}
				else{
					PrintWriter out = response.getWriter();
		            out.print("Terminate operation failed. Please try again later.");
				}
			}

		}
		else{
			PrintWriter out = response.getWriter();
            out.print("Invalid Instance ID. Please try again later.");
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}