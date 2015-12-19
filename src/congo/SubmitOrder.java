/**
 * Mark Bellingham - 14032098
 * Web and Mobile Development assignment 2015
 */

package congo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class SubmitOrder
 */
@WebServlet("/SubmitOrder")
public class SubmitOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Connection information			
		Connection conn = null; 						// Create connection object
		String database = "bellingm"; 					// Name of database
		String user 	= "bellingm";
		String password = "Lerkmant3";
		String url 		= "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk/" + database;
		
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
			request.getRequestDispatcher("login.html").forward(request,response);
		} else {
			// Otherwise welcome them by name and get their id
			out.print("Welcome " + session.getAttribute("fname") + " " + session.getAttribute("lname"));
		}
		
		// Print the title, headers and menu
		out.println(docType);
		out.println("<img id=\"logo\" src=\"images/logo.png\">");
		out.println("<header id=\"name\">");
		out.println("<h1>Congo's Music Store</h1></header><br/>");
		out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a> | <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a>" +
					" | <a href=\"show_my_order\">Show Order</a> | <a href=\"ShowAllCustOrders\">Show all my orders</a> | <a href=\"login.html\">Log in/Register</a></nav><br /><br />");
		
		// albumArray is an array of the album names in our order
		ArrayList<Integer[]> orderArray = (ArrayList<Integer[]>)session.getAttribute("myFinalOrder");
		
		// Get the customer ID to insert into the database
		int custid = Integer.parseInt((String) session.getAttribute("custid"));
		
		//Check to see if we got here without choosing a album, if we did session is 'new'			
		if ( orderArray == null ){
			out.print("Sorry there has been a mistake. Please click the link to <a href=\"index.html\">go Home</a>");
		    return;
		}else{
			//get the current list of albums ordered
		    orderArray = (ArrayList<Integer[]>)session.getAttribute("myFinalOrder");
		}
		
		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    conn = DriverManager.getConnection(url, user, password);
		} catch(Exception e) {
		    System.err.println(e);
		}
		
		// Create statement for inserting the order into the database
		String insertSQL1 = "insert into congo_orders (custid, order_date) values (?,?)";
		int orderid = 0;
		try {
			// Set values in the insert statement and ask for the auto-generated orderid key to be returned
			java.sql.PreparedStatement pst2 = conn.prepareStatement(insertSQL1, PreparedStatement.RETURN_GENERATED_KEYS);
			pst2.setInt(1, custid);
			pst2.setDate(2, getCurrentDate());
			pst2.execute();
			
	        try (ResultSet generatedKeys = pst2.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                orderid = generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Creating order failed, no ID obtained.");
	            }
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		for ( int i = 0; i < orderArray.size(); i++){
		    try {
		    	// Insert into the second table, each album bought with references to the orderid from the first table
		    	int r_id 		= orderArray.get(i)[0];
		    	int quantity 	= orderArray.get(i)[1];
				String insertSQL2 = "insert into congo_order_details values (" + orderid + ", " + r_id + ", " + quantity + ")";
				System.err.println("DEBUG: Query: " + insertSQL2);
				java.sql.Statement stmt = conn.createStatement();
				stmt.executeUpdate(insertSQL2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		out.println("Your order has been successfully submitted.");
		out.println("To see all your orders, <a href=\"ShowAllCustOrders\">Click Here</a>");
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}


}
