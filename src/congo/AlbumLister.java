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
 * Servlet implementation class albumLister
 */
@WebServlet("/albumLister")
public class AlbumLister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlbumLister() {
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
							"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\">" +
							"<script src=\"sorttable.js\"></script></head><body>";

		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
		
	    // going to check the Session for albums, need to 'get' it			
		HttpSession session = request.getSession();
		
		String category = request.getParameter("category");
		
		// print the title and menu
		out.println(docType);
		out.println("<img id=\"logo\" src=\"images/logo.png\">");
		out.println("<header id=\"name\">");
		out.println("<h1>Congo's Music Store</h1></header><br/>");
		out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a>" +
				" | <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a> | <a href=\"show_my_order\">Show Order</a></nav><br /><br />");
		
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
		out.println("These are the albums in the " + category + " category:");
		try{
		    String selectSQL = "select * from music_recordings where category = '" + category + "'";
		    Statement stmt = conn.createStatement();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    // Retrieve the results
		    out.println("<table id=\"musicList\" class=\"sortable\"><tr class=\"th\"><th><a href=# class=\"th\">Artist</a></th><th><a href=# class=\"th\">Album</a></th>" +
		    			"<th><a href=# class=\"th\">Number of Tracks</a></th><th><a href=# class=\"th\">Price</a></th></tr>");
		    while(rs1.next()){
			out.println("<tr><td> "+ rs1.getString("artist_name") + "</td>");
			out.println("<td><a href=\"TrackLister?r_id="+rs1.getInt("recording_id") + "&&name=" + rs1.getString("artist_name") + "&&album="+ rs1.getString("title") + "\">" + rs1.getString("title") + "</a></td>"); 	  
			out.println("<td>" + rs1.getString("num_tracks") + "</td>");
			out.println("<td>£" + rs1.getFloat("price") + "</td>");
			if (rs1.getInt("stock_count") > 0 && session.getAttribute("custid") != null) {
				out.println("<td><form action=\"add_to_order\" method=\"get\">" +
						"<input type=\"hidden\" name=\"title\" value=\"" + rs1.getString("title") + "\">" +
						"<input type=\"submit\" value=\"Add\" >" + "</form>");				
			} else {
				out.println("<td><form action=\"add_to_order\" method=\"get\">" +
						"<input type=\"hidden\" name=\"title\" value=\"" + rs1.getString("title") + "\">" +
						"<input type=\"submit\" value=\"Add\" disabled>" + "</form>");
			}

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
