import Database.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class monitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		CreateConnection create = new CreateConnection();
		ResultSet rs = create.getSensors(username);
		try{
			if (!rs.next()) response.getWriter().append("No rows found.");
			else{
				response.setContentType("text/html");
			    PrintWriter out = response.getWriter();
			    out.println("<html>");
			    out.println("<head><title>All Sensors</title></head>");
			    out.println("<body>");
			    out.println("<center><h1>All Sensors</h1>");
			    out.println("<table><tr><th>Sensor ID		Username		Latitude		Longitude</th></tr>");
			   
			    while (rs.next()) {
			        String sensorid = rs.getString("sensorid");
			        String latitude = rs.getString("latitude");
			        String longitude = rs.getString("longitude");
				    out.print("<tr><td>"+sensorid+"			"+username+"		"+latitude+"		"+longitude+"</td></tr>");
			    }
			
			    out.println("</table>");
			    out.println("</center>");
			    out.println("</body>");
			    out.println("</html>");
			    out.close();
		    }
		}catch(Exception e){
		    	System.out.println("Exception at monitor.java");
		}
	}
}