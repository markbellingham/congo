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
	
		// albumArray is an array of the album names in our order
		ArrayList<String> albumArray = (ArrayList<String>)session.getAttribute("myorder");		

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
		
		String fname = (String) session.getAttribute("fname");
		String lname = (String) session.getAttribute("lname");
		out.println("Welcome " + fname + " " + lname);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
