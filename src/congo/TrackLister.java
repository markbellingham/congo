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
		Connection conn =null; // Create connection object
		String database = "bellingm"; // Name of database
		String user = "bellingm"; // 
		String password = "Lerkmant3";
		String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk/" + database;

		String docType = 	"<!DOCTYPE HTML >" +
							"<html><head>" +
							"<meta charset=\"UTF-8\">" +
							"<title>Congo's Music Store</title>" +
							"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\"></head><body>";

		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
		
		String album_name = request.getParameter("r_id");
		int trackNumber = 0;
		
		out.println(docType + "<h1>Congo's Music Store</h1>");
		out.println("<a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a>" +
				"| <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a><br /><br />");
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
		// Create select statement and execute it
		try{
			System.out.println(album_name);
			
		    String selectSQL = "select  * from music_tracks where recording_id ="+ album_name ;
		    System.out.print(selectSQL);
		    Statement stmt = conn.createStatement();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    // Retrieve the results
		    out.println("<br/><br/>");
		    out.println("<table id=\"musicList\"><tr><th>Track Number</th><th>Track</th><th>Duration</th></tr>");
		    while(rs1.next()){
		    trackNumber++;
		    out.println("<td> " + trackNumber + "</td>");
			out.println("<td>" + rs1.getString("title") + "</td>");
			int minutes = Integer.parseInt(rs1.getString("duration"))/60;
			int seconds = Integer.parseInt(rs1.getString("duration"))%60;
			out.println("<td>" + minutes + "m" + String.format("%02d", seconds) + "sec" + "</td>");
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
