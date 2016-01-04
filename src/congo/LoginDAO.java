/**
 * Mark Bellingham - 14032098
 * Web and Mobile Development assignment 2015
 */

package congo;

import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.*;

/**
 * Servlet implementation class loginDAO
 */
@WebServlet("/loginDAO")
public class LoginDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static boolean validate(String email, String passwd, HttpServletRequest request, HttpServletResponse response) {
    	
    	boolean status = false;							// Default login status is false
    	
		// Connection information			
		Connection conn = null; 						// Create connection object
		String database = "xxxxxxxx"; 					// Name of database
		String user 	= "xxxxxxxx";
		String password = "xxxxxxxx";
		String url 		= "jdbc:mysql://xxxxxxxx" + database;
		
		ArrayList<String> albumArray = new ArrayList<String>();  // albumArray is list of the albums in our order
		
		HttpSession session = request.getSession();				// Get a session
		
		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    conn = DriverManager.getConnection(url, user, password);
		    
		    java.sql.PreparedStatement ps = conn.prepareStatement("select * from congo_customers where email = '" + email + "' and password = '" + passwd + "'");		    
		    ResultSet rs = ps.executeQuery();
		    
		    if (rs.next()) {
		    	status = true;
		    	// If the login information is correct, put these parameters into the session
		    	session.setAttribute("custid", 	rs.getString("custid"));
		    	session.setAttribute("fname", 	rs.getString("fname"));
		    	session.setAttribute("lname", 	rs.getString("lname"));
		    	session.setAttribute("myorder",albumArray); //add array to session 
		    	/*
		    	if (rs.getInt("admin") == 1) {
		    		session.setAttribute("admin", 1);
		    	} else {
		    		session.setAttribute("admin", 0);
		    	}
		    	*/
		    }		    
		} catch(Exception e) {
		    System.err.println(e);
		}
		return status;
		
		
    }

}
