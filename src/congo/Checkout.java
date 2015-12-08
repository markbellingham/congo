package congo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		String docType = "<!DOCTYPE HTML >" +
				"<html><head>" +
				"<meta charset=\"UTF-8\">" +
				"<title>Congo's Music Store</title>" +
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\"></head><body>";
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// print the title and menu
		out.println(docType + "<h1>Congo's Music Store</h1>");
		out.println("<a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a>" +
				"| <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a> | <a href=\"show_my_order\">Show Order</a><br /><br />");
		
		float total = 0.0f;
		float totalPerAlbum = 0.0f;
		float grandTotal = 0.0f;
	    int position = 0;
		
	    // going to check the Session for albums, need to 'get' it			
		HttpSession session = request.getSession();
	
		// albumArray is an array of the album names in our order
		ArrayList<String> albumArray = (ArrayList<String>)session.getAttribute("myorder");
		
		//Check to see if we got here without choosing a album, if we did session is 'new'			
		if ( albumArray == null ){
			out.print("Sorry there has been a mistake. Please click the link to <a href=\"index.html\">go Home</a>");
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

		
		// use names stored in pizzaArray to query database
		String selectSQL1 = "select * from music_recordings where ";
		for ( int i = 0; i < albumArray.size(); i++){
			// build up select statement from all albums currently ordered and stored in albumArray
		    if (i != 0){
			selectSQL1 += " OR ";
		    }
		    selectSQL1 += "title = '" + albumArray.get(i) + "'";
		}
		
		try{
		    System.err.println("DEBUG: Query: " + selectSQL1);
		    Statement stmt = conn.createStatement();
		    ResultSet rs1 = stmt.executeQuery(selectSQL1);
		    
		    out.println("<Center><H1>Checkout</Center>");
		    // print out table header
			out.println("<table id=\"musicList\">" +
			    "<tr><th>Artist</th><th>Album</th><th>Album Price</th><th style=\"width:150px\">Quantity</th><th>Totals</th></tr>");
			
			System.out.println(albumArray);
			// print out table rows one for each row returned in rs1
			while(rs1.next()){
			    out.print("<tr>");
			    out.print("<td>" + rs1.getString("artist_name") + "</td>");
			    out.print("<td>" + rs1.getString("title") + "</td>");
			    out.print("<td>" + rs1.getFloat("price") + "</td>");
			    
			    // Find how many copies of each album are in the order
				int quantity = 0;
			    for (int i = 0; i < albumArray.size(); i++){
			    	if (albumArray.get(i).equals(albumArray.get(position))) {
			    		quantity++;
			    	}
			    }
			    position++;		// Relevant for the for-loop above
			    totalPerAlbum = rs1.getFloat("price") * quantity;	// Get the total cost for each album
			    grandTotal += totalPerAlbum;						// Get the total cost of all albums
			    // Show how many of each album there are
			    out.print("<td>" + quantity + "</td>");
			    out.print("<td>" + totalPerAlbum + "</td>");
			}
		    }catch(SQLException e ){
		    System.err.println(e);
		    }
			out.println("<tr>");
			// Print the total for all albums
			out.print("<td colspan=\"4\"><b>Total</b></td>");
			out.print("<td><b>" + grandTotal + "</b></td>");
			// Close table
			out.println("</tr></table>");
			out.println("<br/><br/>");
			
			// print the form for the customer address
			out.println("<form action=\"SubmitOrder\" method=\"post\">" +
						"<input type=\"text\" name=\"fname\" placeholder=\"First name\" required><br/><br/>" +
						"<input type=\"text\" name=\"lname\" placeholder=\"Surname\" required><br/><br/>" +
						"<input type=\"text\" name=\"address1\" placeholder=\"Address line 1\" required><br/><br/>" +
						"<input type=\"text\" name=\"address2\" placeholder=\"Address line 2\"><br/><br/>" +
						"<input type=\"text\" name=\"city\" placeholder=\"Town or city\" required><br/><br/>" +
						"<input type=\"text\" name=\"postcode\" placeholder=\"Postcode\" required><br/><br/>" +
						"<input type=\"text\" name=\"phone\" placeholder=\"Telephone number\" required><br/><br/>" +
						"<input type=\"email\" name=\"email\" placeholder=\"Email address\" required><br/><br/>" +
						"<input type=\"checkbox\" name=\"mailList\">" +
						"Check this if you want to be entered into our mailing list<br/><br/>" +						
						"<input type=\"submit\" value=\"Submit\">" +
						"</form>");
		    
			out.println("<p><a href=\"index.html\">Go Home<a>");
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
