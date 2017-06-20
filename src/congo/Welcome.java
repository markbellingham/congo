package congo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Welcome() {
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
		
		int custid = 0;
		if (session.getAttribute("custid") == null) {
			// If the user somehow got to this page without being logged in, redirect them
			request.getRequestDispatcher("login.html").forward(request,response);
		} else {
			// Otherwise welcome them by name and get their id
			out.print("Welcome " + session.getAttribute("fname") + " " + session.getAttribute("lname"));
			custid = Integer.parseInt((String) session.getAttribute("custid"));
		}
		
		// Print the title, headers and menu
		out.println(docType);
		out.println("<img id=\"logo\" src=\"images/logo.png\">");
		out.println("<header id=\"name\">");
		out.println("<h1>Congo's Music Store</h1></header><br/>");
		out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a> | <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a>" +
					" | <a href=\"show_my_order\">Show Order</a> | <a href=\"ShowAllCustOrders\">Show all my orders</a> | <a href=\"login.html\">Log in/Register</a></nav><br/><br/>");
		
		/* Enable admin options if admin is logged in
		int admin = Integer.parseInt((String) session.getAttribute("admin"));		
		if (admin == 1) {
			out.println("<a href=\"ShowCurrentOrders.html\">Show Orders from a date range</a><br/><br/>");
			out.println("<a href=\"AddOrDelete\">Add or delete albums</a>");
		}
		*/
		
		// albumArray is an array of the album names in our order
		ArrayList<String> albumArray = (ArrayList<String>)session.getAttribute("myorder");		

		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    conn = DriverManager.getConnection(url, user, password);
		} catch(Exception e) {
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
