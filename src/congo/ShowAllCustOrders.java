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
 * Servlet implementation class ShowAllCustOrders
 */
@WebServlet("/ShowAllCustOrders")
public class ShowAllCustOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAllCustOrders() {
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
		
		if (session.getAttribute("custid") == null) {
			response.sendRedirect("login.html");
		}
		
			String docType = "<!DOCTYPE HTML >" +
					"<html><head>" +
					"<meta charset=\"UTF-8\">" +
					"<title>Congo's Music Store</title>" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\">" +
					"<script src=\"sorttable.js\"></script></head><body>";		
			response.setContentType("text/html");
			
			// print the title and menu
			out.println(docType);
			out.print("Welcome " + session.getAttribute("fname") + " " + session.getAttribute("lname"));
			out.println("<img id=\"logo\" src=\"images/logo.png\">");
			out.println("<header id=\"name\">");
			out.println("<h1>Congo's Music Store</h1></header><br/>");
			out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a> | <a href=\"price.html\">Price Picker</a>" +
					" | <a href=\"artist.html\">Artist Finder</a> | <a href=\"show_my_order\">Show Order</a> | <a href=\"login.html\">Log in/Register</a></nav><br /><br />");
			
			if (session.getAttribute("custid") == null) {
				out.print("Oops, something went wrong. Please click <a href=\"index.html\">here to go home</a>");
				response.sendRedirect("index.html");
			} else {
				out.print("Welcome " + session.getAttribute("fname") + " " + session.getAttribute("lname"));
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
			
			// Get all the customer's orders
			int custid = Integer.parseInt((String) session.getAttribute("custid"));
			String selectSQL1 = "select * from congo_orders o, congo_order_details d, music_recordings r where o.custid = " + custid + " and o.orderid = d.orderid and d.recording_id = r.recording_id";
			
			try {
			    System.err.println("DEBUG: Query: " + selectSQL1);
			    Statement stmt = conn.createStatement();
			    ResultSet rs1 = stmt.executeQuery(selectSQL1);
			    
			    out.println("<div id=\"page_title\"><h2>Previous Orders</h2></div>");
			    // print out table header
				out.println("<table id=\"musicList\" class=\"sortable\">" +
				    "<tr><th>Artist</th><th>Album</th><th>Album Price</th><th>Quantity</th><th>Total</th><th>Date Ordered</th></tr>");
			    
			    while (rs1.next()) {
			    	out.print("<tr>");
			    	out.print("<td>" + rs1.getString("artist_name") + "</td>");
			    	out.print("<td>" + rs1.getString("title") + "</td>");
			    	out.print("<td>" + rs1.getString("price") + "</td>");
			    	out.print("<td>" + rs1.getString("order_quantity") + "</td>");
			    	// Calculate total cost for this album
			    	float total = rs1.getFloat("price") * rs1.getInt("order_quantity");
			    	out.print("<td>" + String.format("%.2f", total));
			    	out.print("<td>" + rs1.getString("order_date") + "</td>");
			    }
				
			} catch(SQLException e ){
			    System.err.println(e);				
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
