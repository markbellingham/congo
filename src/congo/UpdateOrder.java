package congo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateOrder
 */
@WebServlet("/UpdateOrder")
public class UpdateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String docType = 	"<!DOCTYPE HTML >" +
							"<html><head>" +
							"<meta charset=\"UTF-8\">" +
							"<title>Congo's Music Store</title>" +
							"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\"></head><body>";
		
				response.setContentType("text/html"); 
				PrintWriter out = response.getWriter();
				
				// find out the album name and quantity passed in
				String album_name = request.getParameter("title");
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				// print the title and menu
				out.println(docType);
				out.println("<img id=\"logo\" src=\"images/logo.png\">");
				out.println("<header id=\"name\">");
				out.println("<h1>Congo's Music Store</h1></header><br/>");
				out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a>" +
						" | <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a> | <a href=\"show_my_order\">Show Order</a></nav><br /><br />");
				
				// get a session
				HttpSession session = request.getSession();
				
				ArrayList<String> albumArray;  // albumArray is list of the albums in our order
				
				albumArray = (ArrayList<String>)session.getAttribute("myorder");
				
				// First remove the selected album from the order
				albumArray.removeAll(Collections.singleton(album_name));
				
				// Then add the selected album back to the order for the required amount
				for (int i = 0; i < quantity; i++) {
					albumArray.add(album_name);
				}
				
				// update the session with the latest info from albumArray
				session.setAttribute("myorder", albumArray);
					
				// Might as well display the album details
				// Getting the  details from the database				
				Connection conn =null; // Create connection object
				String database = "bellingm"; // Name of database
				String user = "bellingm"; // 
				String password = "Lerkmant3";
				String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk/" + database;
				
				
				try{
				    Class.forName("com.mysql.jdbc.Driver").newInstance();
				} catch(Exception e) {
				    System.err.println(e);
				}
				
				// connecting to database
				try{
				    conn = DriverManager.getConnection(url, user, password);
				    String selectSQL = "select * from music_recordings where title ='" + album_name + "'";
				    System.err.println("DEBUG: Query: " + selectSQL);
				    Statement stmt = conn.createStatement();
				    ResultSet rs1 = stmt.executeQuery(selectSQL);
				    rs1.next();
				    
				    out.println("<p>The following album has been added to your order:<p>");
				    out.println("<table id=\"musicList\" <tr><th></th><th></th></tr>");
				    out.println("<tr><td>Artist</td><td>" + rs1.getString("artist_name") + "</td></tr>");
				    out.println("<tr><td>Album</td><td>" + rs1.getString("title") + "</td></tr>");
				    out.println("<tr><td>Price</td><td>£" + rs1.getFloat("price") + "</td></tr>");
				    out.println("</table>");
				}catch(SQLException e){
				    System.err.println(e);
				}
				out.println("<p><a href=\"index.html\">Go Home</a><br> or see what you've <a href=\"show_my_order\">ordered</a>");
				out.println("</body></html>");
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
