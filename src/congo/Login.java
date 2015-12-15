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
		
		/*// print the title and menu
		out.println(docType);
		out.println("<img id=\"logo\" src=\"images/logo.png\">");
		out.println("<header id=\"name\">");
		out.println("<h1>Congo's Music Store</h1></header><br/>");
		out.println("<nav><a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a>" +
				" | <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a> | <a href=\"show_my_order\">Show Order</a></nav><br /><br />");
		*/
	    // going to check the Session for albums, need to 'get' it			
		HttpSession session = request.getSession();
	
		Connection conn = null; // Create connection object
		String database = "bellingm"; // Name of database
		String user 	= "bellingm"; // 
		String password = "Lerkmant3";
		String url 		= "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk/" + database;
		
		String email 	= request.getParameter("email");
		String passwd 	= request.getParameter("password");
		
		System.out.println(email + " " + passwd);
		
		if (LoginDAO.validate(email, passwd)) {
			out.print("Welcome");
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
