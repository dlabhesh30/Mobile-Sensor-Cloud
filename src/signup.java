import SensorOperations.invokeSensor;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.CreateConnection;

public class signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String mobilenumber = request.getParameter("mobileNumber");
		String creditCardDetails = request.getParameter("creditCardDetails");
		String SecurityNumber = request.getParameter("securityPin");
		String ExpiryDate = request.getParameter("expiryDate");
		String NameOnCard = request.getParameter("nameOnCard");
		CreateConnection conn = new CreateConnection();
		boolean flag = conn.insert(firstname,lastname,username, password, email, address,
				mobilenumber, creditCardDetails, SecurityNumber, ExpiryDate, NameOnCard);
		if(flag){
			// call dashboard
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			System.out.println("username in if is "+username);
			response.sendRedirect("userdashboard.jsp");
		}
		else{
			// call login page
			response.getWriter().append("Something went wrong. Please try again.");
		}
	}
}