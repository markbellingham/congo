/**
 * Mark Bellingham - 14032098
 * Web and Mobile Development assignment 2015
 */

package congo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;  

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		
	    // going to check the Session for albums, need to 'get' it			
		HttpSession session = request.getSession();
	
		Connection conn = null;			// Create connection object
		String database = "xxxxxxxx";	// Name of database
		String user 	= "xxxxxxxx"; 
		String password = "xxxxxxxx";
		String url 		= "jdbc:mysql://xxxxxxxx" + database;
		
		String email 	= request.getParameter("email");
		String passwd 	= request.getParameter("password");
		
		// Use LoginDAO to check if the user information is correct
		if (LoginDAO.validate(email, passwd, request, response)) {
			RequestDispatcher rd = request.getRequestDispatcher("Welcome");
			rd.forward(request, response);
		} else {
			out.print("Sorry, the email or password was incorrect");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
		out.close();
	}

}
