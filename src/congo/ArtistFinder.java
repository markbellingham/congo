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
 * Servlet implementation class ArtistFinder
 */
@WebServlet("/ArtistFinder")
public class ArtistFinder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArtistFinder() {
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
		
		String name = request.getParameter("name");
		
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
		    String selectSQL = "select * from music_recordings where artist_name = '" + name + "'";
		    Statement stmt = conn.createStatement();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    if (rs1.next()) {
			    // Retrieve the results
			    out.println("<table id=\"musicList\"><tr><th>Artist</th><th>Album</th><th>Number of Tracks</th><th>Price</th></tr>");
		    	do{
					out.println("<tr><td> "+ rs1.getString("artist_name") + "</td>");
					out.println("<td><a href=\"TrackLister?r_id="+rs1.getInt("recording_id") + "\">" + rs1.getString("title") + "</a></td>"); 	  
					out.println("<td> " + rs1.getString("num_tracks") + "</td>");
					out.println("<td> " + rs1.getFloat("price") + "</td>");
					out.println("<td><form action=\"add_to_order\" method=\"get\">" +
							"<input type=\"hidden\" name=\"title\" value=\"" + rs1.getString("title") + "\">" +
							"<input type=\"submit\" value=\"Add\" >" + "</form>");
					out.println("</tr>");			
				    }while(rs1.next());
					} else {
						out.println("The query returned no results. Please check your spelling or try a different artist");
					}
		    		out.println("</table>");
		    		conn.close();
				}catch(SQLException se) {
				    System.err.println(se);
				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
