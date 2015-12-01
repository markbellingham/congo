package congo;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.sql.*;

/**
 * Servlet implementation class categories
 */
@WebServlet("/categories")
public class Categories extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Categories() {
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

		String docType =
		    "<!DOCTYPE HTML >";

		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
		
		String category = request.getParameter("category");
		
		out.println(docType + "<h1>Congo's Music Store</h1>");
		out.println("<a href=\"index.html\">Home</a> | <a href=\"categories\">Categories</a><br /><br />");
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
		    String selectSQL = "select category from music_recordings";
		    Statement stmt = conn.createStatement();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    // Retrieve the results
		    out.println("Choose your favourite category here ");
		    out.println("<form action=\"albumLister\">");
		    out.println("<select name=\"category\">");
		    while(rs1.next()){
			out.println("<option value=\'" + rs1.getString("category") + "\'>" + rs1.getString("category") + "</option>");
		    }
		    out.println("</select>");
		    out.println("<input type=\"submit\" value=\"submit\">");
		    out.println("</form>");
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
