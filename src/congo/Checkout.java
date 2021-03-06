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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
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
		
		if (session.getAttribute("custid") == null) {
			// If the user somehow got to this page without being logged in, redirect them
			response.sendRedirect("login.html");
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
		
		float totalPerAlbum = 0.0f;
		float grandTotal = 0.0f;
		
		// albumArray is an array of the album names in our order
		ArrayList<String> albumArray = (ArrayList<String>)session.getAttribute("myorder");
		
		// Check to see if we got here without choosing a album			
		if ( albumArray == null ){
			out.print("Sorry there has been a mistake. Please click the link to <a href=\"index.html\">go Home</a>");
		    return;
		}else{
			//get the current list of albums ordered
		    albumArray = (ArrayList<String>)session.getAttribute("myorder");
		}

		// Create a new arrayList to store the final order for submission
		ArrayList<Integer[]> orderArray = new ArrayList<Integer[]>();
		
		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    conn = DriverManager.getConnection(url, user, password);
		} catch(Exception e) {
		    System.err.println(e);
		}

		
		// Use names stored in albumArray to query database
		String selectSQL1 = "select * from Music_Recordings where ";
		for ( int i = 0; i < albumArray.size(); i++){
			// build up select statement from all albums currently ordered and stored in albumArray
		    if (i != 0){
			selectSQL1 += " OR ";
		    }
		    selectSQL1 += "recording_id = '" + albumArray.get(i) + "'";
		}
		
		try{
		    System.err.println("DEBUG: Query: " + selectSQL1);
		    Statement stmt 	= conn.createStatement();
		    ResultSet rs1 	= stmt.executeQuery(selectSQL1);
		    
			System.out.println(albumArray);							// Print the array to the console for debugging
		    
		    out.println("<div id=\"page_title\"><h2>Checkout</h2></div>");
		    // print out table header
			out.println("<table id=\"musicList\">" +
			    "<tr><th>Artist</th><th>Album</th><th>Album Price</th><th>Quantity</th><th>Totals</th></tr>");
			// print out table rows one for each row returned in rs1
			while(rs1.next()){
			    out.print("<tr>");
			    out.print("<td>" + rs1.getString("artist_name") + "</td>");
			    out.print("<td>" + rs1.getString("title") + "</td>");
			    out.print("<td>£" + rs1.getFloat("price") + "</td>");
			    
			    // Find how many copies of each album are in the order
			    int quantity = Collections.frequency(albumArray, rs1.getString("recording_id"));
			    
			    totalPerAlbum = rs1.getFloat("price") * quantity;	// Get the total cost for each album
			    grandTotal += totalPerAlbum;						// Get the total cost of all albums
			    out.print("<td>" + quantity + "</td>");				// Show how many of each album there are
			    out.print("<td>£" + totalPerAlbum + "</td>");		// Print total cost for each album
			    // Create arrayList containing the final order that we can insert into the database
			    Integer array[] = {rs1.getInt("recording_id"), quantity};
			    orderArray.add(array);
			}
		    }catch(SQLException e ){
		    System.err.println(e);
		    }
			out.print("<tr><td colspan=\"4\"><b>Total</b></td>");	// Print the total for all albums, formatted to 2 decimal places
			out.print("<td><b>£" + String.format("%.2f", grandTotal) + "</b></td></tr>");
			// Close table
			out.println("</table>");
			out.println("<br/><br/>");
			out.println("<form action=\"SubmitOrder\" method=\"get\">" +
						"<input type=\"submit\" value=\"Submit Order\"></form>");
			
			out.println("<p><a href=\"index.html\">Go Home<a>");
			out.println("</body></html>");
			
			session.setAttribute("myFinalOrder", orderArray);		// Put the finalised order into the session
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
