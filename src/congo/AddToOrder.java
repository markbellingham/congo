/**
 * Mark Bellingham - 14032098
 * Web and Mobile Development assignment 2015
 */

package congo;

import java.io.*;
import java.util.ArrayList;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class AddToOrder
 */
@WebServlet("/add_to_order")
public class AddToOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToOrder() {
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

			if (session.getAttribute("custid") == null) {	
				// Redirect if the user somehow reaches this page without being logged in
				request.getRequestDispatcher("login.html").forward(request,response);
			} else {
				// If they are logged in, welcome them by name
				out.print("Welcome " + session.getAttribute("fname") + " " + session.getAttribute("lname"));
			}
			
			String r_id = request.getParameter("r_id");		// Find out the recording ID passed in
			
			// Print the title, headers and menu
			out.println(docType);
			out.println("<img id=\"logo\" src=\"images/logo.png\">");
			out.println("<header id=\"name\">");
			out.println("<h1>Congo's Music Store</h1></header><br/>");
			out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a> | <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a>" +
					" | <a href=\"show_my_order\">Show Order</a> | <a href=\"ShowAllCustOrders\">Show all my orders</a> | <a href=\"login.html\">Log in/Register</a></nav><br /><br />");
			
			ArrayList<String> albumArray;  					// albumArray is list of the albums in our order
			
			// Check to see if this is a new order
			if ( session.isNew() ){
			    // new order(session) so create a new ArrayList
			    albumArray = new ArrayList<String>();
			}else{
				// already ordered something, get current order
			   albumArray = (ArrayList<String>)session.getAttribute("myorder");
			}
			
			albumArray.add(r_id);							// Add our album to the order and
			session.setAttribute("myorder", albumArray);	// add the array to the session to keep it up to date
				
			// Might as well display the album details
			// Getting the  details from the database			
			Connection conn = null; 						// Create connection object
			String database = "congo"; 					// Name of database
			String user 	= "mark";
			String password = "Excite10";
			String url 		= "jdbc:mysql://localhost:3306/" + database;
			

			try{
			    Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch(Exception e) {
			    System.err.println(e);
			}
			
			try{
				// connecting to database
			    conn = DriverManager.getConnection(url, user, password);
			    // Create the string to query the database
			    String selectSQL = "select * from Music_Recordings where recording_id ='" + Integer.parseInt(r_id) + "'";
			    System.err.println("DEBUG: Query: " + selectSQL);	// Print the SQL string to the console for debugging
			    Statement stmt = conn.createStatement();			
			    ResultSet rs1 = stmt.executeQuery(selectSQL);		// Here is where we actually query the database, the data returned is stored in a ResultSet
			    rs1.next();											// and is read one line at a time, though here we only have one line returned
			    // The details of the album are shown in a table
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
	}

}
