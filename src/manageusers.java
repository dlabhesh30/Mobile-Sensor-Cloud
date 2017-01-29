import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.CreateConnection;
import SensorOperations.startSensor;
import SensorOperations.stopSensor;
import SensorOperations.terminateSensor;

/**
 * Servlet implementation class magageusers
 */
public class manageusers extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("user");
		String operation = request.getParameter("operation");
		CreateConnection cr = new CreateConnection();
		String firstname = cr.getInstanceID(username);
		//System.out.println("instance id is "+instanceid);
		
		if(firstname!=null){
			if(operation.equals("Edit")){
				
				System.out.println("in manage users "+username);
				response.sendRedirect("editUserAdmin.jsp");
			}
			if(operation.equals("Delete")){
				boolean flag = cr.deleteUser(username);
				if(flag){
					PrintWriter out = response.getWriter();
		            out.print("Delete operation completed successfully.");
				}
				else{
					PrintWriter out = response.getWriter();
		            out.print("Delete operation unsuccessfull. Please try again.");
				}
			}
		}
		else{
			PrintWriter out = response.getWriter();
            out.print("Invalid Username. Please try again later.");
		}
	}
}