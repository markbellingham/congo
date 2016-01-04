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
		// Create string with the HTML header information
		String docType 	= 	"<!DOCTYPE HTML >" +
							"<html><head>" +
							"<meta charset=\"UTF-8\">" +
							"<title>Congo's Music Store</title>" +
							"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\"></head><body>";
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();			// Initialise the printwriter for outputting to the browser			
		HttpSession session = request.getSession();		// Get a session
		
		// find out the album name and quantity passed in
		String r_id = request.getParameter("r_id");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		if (session.getAttribute("custid") == null) {	
			// Redirect if the user somehow reaches this page without being logged in
			request.getRequestDispatcher("login.html").forward(request,response);
		} else {
			// If they are logged in, welcome them by name
			out.print("Welcome " + session.getAttribute("fname") + " " + session.getAttribute("lname"));
		}
		
		// Print the title, headers and menu
		out.println(docType);
		out.println("<img id=\"logo\" src=\"images/logo.png\">");
		out.println("<header id=\"name\">");
		out.println("<h1>Congo's Music Store</h1></header><br/>");
		out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a> | <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a>" +
					" | <a href=\"show_my_order\">Show Order</a> | <a href=\"ShowAllCustOrders\">Show all my orders</a> | <a href=\"login.html\">Log in/Register</a></nav><br /><br />");
		
		ArrayList<String> albumArray;  // albumArray is list of the albums in our order
		
		// Get the copy of our order from the session
		albumArray = (ArrayList<String>)session.getAttribute("myorder");
		
		// First remove all copies of the selected album from the order
		albumArray.removeAll(Collections.singleton(r_id));
		
		// Then add the selected album back to the order for the required amount
		for (int i = 0; i < quantity; i++) {
			albumArray.add(r_id);
		}
		
		// update the session with the latest info from albumArray
		session.setAttribute("myorder", albumArray);
			
		// Might as well display the album details
		// Getting the  details from the database			
		Connection conn = null; 						// Create connection object
		String database = "xxxxxxxx"; 					// Name of database
		String user 	= "xxxxxxxx";
		String password = "xxxxxxxx";
		String url 		= "xxxxxxxx" + database;
		
		
		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e) {
		    System.err.println(e);
		}
		
		
		try{
			// connecting to database
		    conn = DriverManager.getConnection(url, user, password);
		    // Create the string to query the database and execute it
		    String selectSQL = "select * from music_recordings where recording_id ='" + Integer.parseInt(r_id) + "'";
		    System.err.println("DEBUG: Query: " + selectSQL);
		    Statement stmt = conn.createStatement();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    rs1.next();
		    
		    // Print useful information to the user in a table
		    out.println("<p>Your order now contains " + quantity + " copies of the following album:<p>");
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
