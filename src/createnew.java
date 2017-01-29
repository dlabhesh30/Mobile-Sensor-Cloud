import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import SensorOperations.*;

public class createnew extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String sensorname = request.getParameter("sensorname");
		String source = request.getParameter("source");
		String state = "Running";
		invokeSensor so = new invokeSensor();
		
		try{
			boolean flag = so.create(username, sensorname, source, state);
			if(flag){
				response.sendRedirect("sensorsuccess.jsp");
			}
			else{
				response.sendRedirect("sensorfailure.jsp");
			}
		}
		catch(Exception e){
			System.out.println("exception in createnew.java");
			e.printStackTrace();
		}
	}
}