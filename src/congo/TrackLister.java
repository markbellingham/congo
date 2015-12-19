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
 * Servlet implementation class TrackLister
 */
@WebServlet("/TrackLister")
public class TrackLister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrackLister() {
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
							"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\"></head><body>";
		response.setContentType("text/html"); 
		
		PrintWriter out = response.getWriter();			// Initialise the printwriter for outputting to the browser			
		HttpSession session = request.getSession();		// Get a session
		
		// Get parameters which were passed from the previous page
		String recording_id = request.getParameter("r_id");
		String artist = request.getParameter("name");
		String album = request.getParameter("album");
		int trackNumber = 0;
		
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
		    String selectSQL = "select * from music_tracks where recording_id = " + recording_id ;
		    Statement stmt = conn.createStatement();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    // Retrieve the results
		    out.println("These are the tracks from " + album + " by " + artist + "<br/><br/>");
		    // Table header
		    out.println("<table id=\"musicList\"><tr><th>Track Number</th><th>Track</th><th>Duration</th></tr>");
		    while(rs1.next()){
		    	// Print each track line by line
			    trackNumber++;													// Track number is generated here
			    out.println("<td> " + trackNumber + "</td>");
				out.println("<td>" + rs1.getString("title") + "</td>");
				int minutes = Integer.parseInt(rs1.getString("duration"))/60;	// Calculate minutes
				int seconds = Integer.parseInt(rs1.getString("duration"))%60;	// Calculate seconds
				out.println("<td>" + minutes + "m " + String.format("%02d", seconds) + "sec" + "</td>");	// Using String.format so that there is a 0 in front of single digits
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
