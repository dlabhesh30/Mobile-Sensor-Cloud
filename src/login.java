import Database.CreateConnection;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;  

public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		CreateConnection conn = new CreateConnection();
		boolean flag = conn.checkUser(username,password);
		
		if(flag){
			// call dashboard
			if(username.equals("admin"))	response.sendRedirect("adminDashboard.jsp");

			else	response.sendRedirect("userdashboard.jsp");
		}
		else{
			// call login page
			response.getWriter().append("Wrong Password, try again.");
		}
	}
}