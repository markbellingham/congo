package congo;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String docType = 	"<!DOCTYPE HTML >" +
				"<html><head>" +
				"<meta charset=\"UTF-8\">" +
				"<title>Congo's Music Store</title>" +
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\"></head><body>";
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
		
		// print the title and menu
		out.println(docType);
		out.println("<img id=\"logo\" src=\"images/logo.png\">");
		out.println("<header id=\"name\">");
		out.println("<h1>Congo's Music Store</h1></header><br/>");
		out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a> | <a href=\"price.html\">Price Picker</a>" +
				" | <a href=\"artist.html\">Artist Finder</a> | <a href=\"show_my_order\">Show Order</a> | <a href=\"login.html\">Log in/Register</a></nav><br /><br />");
		
	    // going to check the Session for albums, need to 'get' it			
		HttpSession session = request.getSession();
	
		Connection conn = null; // Create connection object
		String database = "bellingm"; // Name of database
		String user = "bellingm"; // 
		String password = "Lerkmant3";
		String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk/" + database;
		
		String fName = request.getParameter("fname");
		String lName = request.getParameter("lname");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String postcode = request.getParameter("postcode");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String passwd = request.getParameter("password");

		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    conn = DriverManager.getConnection(url, user, password);
		    
		    java.sql.PreparedStatement ps = conn.prepareStatement("insert into congo_customers (fname, lname, address1, address2, city, postcode, phone, email, password) values (?,?,?,?,?,?,?,?,?)");
		    
		    ps.setString(1, fName);
		    ps.setString(2, lName);
		    ps.setString(3, address1);
		    ps.setString(4, address2);
		    ps.setString(5, city);
		    ps.setString(6, postcode);
		    ps.setString(7, phone);
		    ps.setString(8, email);
		    ps.setString(9, passwd);
		    
		    int i = ps.executeUpdate();
		    if (i > 0) {
		    	out.print("You are successfully registered");
		    }
		    
		} catch(Exception e) {
		    System.err.println(e);
		}
		out.close();
	}

}
