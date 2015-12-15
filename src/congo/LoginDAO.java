package congo;

import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;

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
    	
    	boolean status = false;
    	
		Connection conn = null; // Create connection object
		String database = "bellingm"; // Name of database
		String user = "bellingm"; // 
		String password = "Lerkmant3";
		String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk/" + database;
		
		//get a session
		HttpSession session = request.getSession();
		
		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    conn = DriverManager.getConnection(url, user, password);
		    
		    java.sql.PreparedStatement ps = conn.prepareStatement("select * from congo_customers where email = '" + email + "' and password = '" + passwd + "'");
		    
		    ResultSet rs = ps.executeQuery();
		    
		    if (rs.next()) {
		    	status = true;
		    	session.setAttribute("custid", rs.getString("custid"));
		    	session.setAttribute("firstName", rs.getString("fname"));
		    	session.setAttribute("lastName", rs.getString("lname"));
		    }
		    
		} catch(Exception e) {
		    System.err.println(e);
		}
		return status;
		
		
    }

}
