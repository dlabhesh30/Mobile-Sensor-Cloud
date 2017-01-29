import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.CreateConnection;

public class patroling extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CreateConnection cc = new CreateConnection();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String location = request.getParameter("location");
		//String sensor ;
		
		if(location == ""){
			PrintWriter out = response.getWriter();
            out.print("Please enter location name.");
		}
		else{
			session.setAttribute("location", location);
			System.out.println("username "+username);
			//System.out.println("sensor "+sensor);
			System.out.println("location "+location);
			// call the google map API from here
			
		}
	}
}