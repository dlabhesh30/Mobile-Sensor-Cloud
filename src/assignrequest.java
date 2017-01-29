import Database.CreateConnection;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 
import javax.servlet.RequestDispatcher; 

public class assignrequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String location = request.getParameter("location");
		System.out.println("In servelet, location is ");
		HttpSession session = request.getSession();
		session.setAttribute("location", location);
		CreateConnection conn = new CreateConnection();
		if(location!=""){
			response.sendRedirect( "loadbalncer.jsp?location=location" );
		}
		else{
			// call login page
			response.getWriter().append("Location is empty.");
		}
	}
}