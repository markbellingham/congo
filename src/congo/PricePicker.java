/**
 * Mark Bellingham - 14032098
 * Web and Mobile Development assignment 2015
 */

package congo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PricePicker
 */
@WebServlet("/PricePicker")
public class PricePicker extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PricePicker() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Connection information			
		Connection conn = null; 						// Create connection object
		String database = "congo"; 					// Name of database
		String user 	= "mark";
		String password = "Excite10";
		String url 		= "jdbc:mysql://localhost:3306/" + database;

		// Create string with the HTML header information
		String docType = 	"<!DOCTYPE HTML >" +
							"<html><head>" +
							"<meta charset=\"UTF-8\">" +
							"<title>Congo's Music Store</title>" +
							"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\">" +
							"<script src=\"sorttable.js\"></script></head><body>";

		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();			// Initialise the printwriter for outputting to the browser			
		HttpSession session = request.getSession();		// Get a session
		
		float price = Float.parseFloat(request.getParameter("price"));	// Get the price parameter from the HTML page we came from
		
		if (session.getAttribute("custid") == null) {
			// If the user is not logged in, tell them
			out.print("You are not logged in");
		} else {
			// Otherwise welcome them by name
			out.print("Welcome " + session.getAttribute("fname") + " " + session.getAttribute("lname"));
		}
		
		// Print the title, headers and menu
		out.println(docType);
		out.println("<img id=\"logo\" src=\"images/logo.png\">");
		out.println("<header id=\"name\">");
		out.println("<h1>Congo's Music Store</h1></header><br/>");
		out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a> | <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a>" +
				" | <a href=\"show_my_order\">Show Order</a> | <a href=\"ShowAllCustOrders\">Show all my orders</a> | <a href=\"login.html\">Log in/Register</a></nav><br /><br />");
		
		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e) {
		    System.err.println(e);
		}
		
		// connecting to database
		try{
		    conn = DriverManager.getConnection(url, user, password);			
		}
		catch(SQLException se) {
		    System.err.println(se);
		}
				
		try{
			// Create select statement and execute it
		    String selectSQL = "select * from Music_Recordings where price <= '" + price + "' and price >= '" + (price - 1) + "'";
		    Statement stmt = conn.createStatement();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    // Prints the table headers
		    out.println("<table id=\"musicList\" class=\"sortable\"><tr class=\"th\"><th><a href=# class=\"th\">Artist</a></th><th><a href=# class=\"th\">Album</a></th>" +
	    				"<th><a href=# class=\"th\">Number of Tracks</a></th><th><a href=# class=\"th\">Price</a></th></tr>");
		    while(rs1.next()){
		    	// Retrieve the results that came back from the database and print them in the table, one album per line
		    	out.println("<tr><td> "+ rs1.getString("artist_name") + "</td>");
		    	// Create link to show the tracks for the album - passing the album and artist names too
		    	out.println("<td><a href=\"TrackLister?r_id="+rs1.getInt("recording_id") + "&&name=" + rs1.getString("artist_name") + "&&album="+ rs1.getString("title") + "\">" + rs1.getString("title") + "</a></td>"); 	  
		    	out.println("<td>" + rs1.getString("num_tracks") + "</td>");
		    	out.println("<td>£" + rs1.getFloat("price") + "</td>");
		    	// Only activate the Add to Order button if the album is in stock and the user is logged in
		    	if (rs1.getInt("stock_count") > 0 && session.getAttribute("custid") != null) {
		    		out.println("<td><form action=\"add_to_order\" method=\"get\">" +
		    					"<input type=\"hidden\" name=\"r_id\" value=\"" + rs1.getInt("recording_id") + "\">" +
		    					"<input type=\"submit\" value=\"Add\" >" + "</form></td>");				
		    	} else {
		    		out.println("<td><form><input type=\"submit\" value=\"Add\" disabled>" + "</form></td>");
		    	}
		    	out.println("</tr>");
		 	}
		   	out.println("</table>");
		   	conn.close();
		} catch(SQLException se) {
		    System.err.println(se);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
