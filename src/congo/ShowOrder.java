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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowOrder
 */
@WebServlet("/show_my_order")
public class ShowOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
	    // going to check the Session for albums, need to 'get' it			
		HttpSession session = request.getSession();
		
			String docType = "<!DOCTYPE HTML >" +
					"<html><head>" +
					"<meta charset=\"UTF-8\">" +
					"<title>Congo's Music Store</title>" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\">" +
					"<script src=\"sorttable.js\"></script></head><body>";		
			response.setContentType("text/html");
			
			if (session.getAttribute("justOnce") == null) {
				session.setAttribute("justOnce", true);
				response.sendRedirect("show_my_order");
			}
			
			// print the title and menu
			out.println(docType);
			out.print("Welcome " + session.getAttribute("fname") + " " + session.getAttribute("lname"));
			out.println("<img id=\"logo\" src=\"images/logo.png\">");
			out.println("<header id=\"name\">");
			out.println("<h1>Congo's Music Store</h1></header><br/>");
			out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a> | <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a>" +
					" | <a href=\"show_my_order\">Show Order</a> | <a href=\"ShowAllCustOrders\">Show all my orders</a> | <a href=\"login.html\">Log in/Register</a></nav><br /><br />");
			
			if (session.getAttribute("custid") == null) {
				out.print("Oops, something went wrong. Please click <a href=\"index.html\">here to go home</a>");
			} else {
				out.print("Welcome " + session.getAttribute("fname") + " " + session.getAttribute("lname"));
			}
			
			float totalPerAlbum = 0.0f;
			float grandTotal = 0.0f;
			
			// albumArray is an array of the album names in our order
			ArrayList<String> albumArray = (ArrayList<String>)session.getAttribute("myorder");
			
			//Check to see if we got here without choosing a album, if we did session is 'new'			
			if ( albumArray == null ){
				// create a list to hold order
			    albumArray = new ArrayList<String>();
			    //put empty list in session to hold choices under attribute name 'myorder'
			    session.setAttribute("myorder",albumArray);
			    return;
			}else{
				//get the current list of albums ordered
			    albumArray = (ArrayList<String>)session.getAttribute("myorder");
			}

			
			
			Connection conn = null; // Create connection object
			String database = "bellingm"; // Name of database
			String user = "bellingm"; // 
			String password = "Lerkmant3";
			String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk/" + database;
			

			try{
			    Class.forName("com.mysql.jdbc.Driver").newInstance();
			    conn = DriverManager.getConnection(url, user, password);
			} catch(Exception e) {
			    System.err.println(e);
			}

			
			// use names stored in albumArray to query database
			String selectSQL1 = "select * from music_recordings where ";
			for ( int i = 0; i < albumArray.size(); i++){
				// build up select statement from all albums currently ordered and stored in albumArray
			    if (i != 0){
				selectSQL1 += " OR ";
			    }
			    selectSQL1 += "recording_id = '" + albumArray.get(i) + "'";
			}
			
			try{
			    System.err.println("DEBUG: Query: " + selectSQL1);
			    Statement stmt = conn.createStatement();
			    ResultSet rs1 = stmt.executeQuery(selectSQL1);
			    
			    out.println("<div id=\"page_title\"><h2>Current Order</h2></div>");
			    // print out table header
				out.println("<table id=\"musicList\" class=\"sortable\">" +
				    "<tr><th>Artist</th><th>Album</th><th>Album Price</th><th>Quantity</th><th>Totals</th><th class=\"sorttable_nosort\"></th></tr>");
				
				System.out.println(albumArray);
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
				    // Show how many of each album there are with button to update quantities
				    out.print("<td><form action=\"UpdateOrder\" method=\"get\">" +
				    		 	"<input type=\"number\" name=\"quantity\" value=\"" + quantity + "\" min=\"1\" max=\"5\" style=\"width:30px\">" +				    			
				    			"<input type=\"hidden\" name=\"r_id\" value=\"" + rs1.getString("recording_id") + "\">" +
				    			"<input type=\"submit\" value=\"Update\"></td></form><td>£" + totalPerAlbum + "</td><td>");
				    // button to remove album from the order
				    out.print("<form action=\"RemoveAlbum\" method=\"get\">" +
				    			"<input type=\"hidden\" name=\"r_id\" value=\"" + rs1.getString("recording_id") + "\">" +
						      	"<input type=\"submit\" value=\"Delete\"></td></form>");
				}
			    }catch(SQLException e ){
			    System.err.println(e);
			    }
				out.println("<tr>");
				// Print the total for all albums
				out.print("<tfoot><td colspan=\"4\"><b>Total</b></td>");
				out.print("<td><b>£" + String.format("%.2f", grandTotal) + "</b></td><td></td></tfoot>");
				// Close table
				out.println("</tr></table>");
			    
				out.println("<p><a href=\"index.html\">Go Home<a> or <a href=\"Checkout\">Checkout</a>");
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
