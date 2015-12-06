package congo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowOrder
 */
@WebServlet("/show_my_order")
public class ShowOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String docType = "<!DOCTYPE HTML >" +
					"<html><head>" +
					"<meta charset=\"UTF-8\">" +
					"<title>Congo's Music Store</title>" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/stylesheet.css\"></head><body>";
		
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter();
			
			out.println(docType + "<h1>Congo's Music Store</h1>");
			out.println("<a href=\"index.html\">Home</a> | <a href=\"category.html\">Categories</a>" +
					"| <a href=\"price.html\">Price Picker</a> | <a href=\"artist.html\">Artist Finder</a> | <a href=\"show_my_order\">Show Order</a><br /><br />");
			
			float total = 0.0f;
			float totalPerAlbum = 0.0f;
			float grandTotal = 0.0f;
		    int position = 0;
			// going to check the Session for albums, need to 'get' it
			
			HttpSession session = request.getSession();
		
			ArrayList<String> albumArray = (ArrayList<String>)session.getAttribute("myorder");  // albumArray is an array of the album names in our order
			
			//Check to see if we got here without choosing a album, if we did session is 'new'
			
			if ( albumArray == null ){
				// create a list to hold order
			    albumArray = new ArrayList<String>();
			    //put empty list in session to hold choices under attribute name 'myorder'
			    session.setAttribute("myorder",albumArray);
			    return;
			}else{
				//get the current list of albums ordered
			    albumArray = (ArrayList<String>)session.getAttribute("myorder");
			}

			
			
			Connection conn =null; // Create connection object
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

			
			// use names stored in pizzaArray to query database
			String selectSQL1 = "select * from music_recordings where ";
			for ( int i = 0; i < albumArray.size(); i++){
				//build up select statement from all albums currently ordered and stored in albumArray
			    if (i != 0){
				selectSQL1 += " OR ";
			    }
			    selectSQL1 += "title = '" + albumArray.get(i) + "'";
			}
			
			try{
			    System.err.println("DEBUG: Query: " + selectSQL1);
			    Statement stmt = conn.createStatement();
			    ResultSet rs1 = stmt.executeQuery(selectSQL1);
			    
			    out.println("<Center><H1>Current Order</Center>");
			    // print out table header
				out.println("<table id=\"musicList\">" +
				    "<tr><th>Artist</th><th>Album</th><th>Album Price</th><th style=\"width:150px\">Quantity</th><th>Totals</th><th></th></tr>");

				//print out table rows one for each row returned in rs1
				while(rs1.next()){
				    out.print("<tr>");
				    out.print("<td>" + rs1.getString("artist_name") + "</td>");
				    out.print("<td>" + rs1.getString("title") + "</td>");
				    out.print("<td>" + rs1.getFloat("price") + "</td>");
				    
				    //Total up albums
					int quantity = 0;
				    for (int i = 0; i < albumArray.size(); i++){
				    	if (albumArray.get(i).equals(albumArray.get(position))) {
				    		quantity++;
				    	}
				    }
				    position++;
				    totalPerAlbum = rs1.getFloat("price") * quantity;
				    grandTotal += totalPerAlbum;
				    // Add button to change quantities
				    out.print("<td><form action=\"UpdateOrder\" method=\"get\">" +
				    		 	"<input type=\"number\" name=\"quantity\" value=\"" + quantity + "\" min=\"1\" max=\"5\" style=\"width:30px\">" +				    			
				    			"<input type=\"hidden\" name=\"title\" value=\"" + rs1.getString("title") + "\">" +
				    			"<input type=\"submit\" value=\"Update\"></td></form><td>" + totalPerAlbum + "</td><td>");
				    // Add button to remove album which goes to new servlet RemoveAlbum.java
				    out.print("<form action=\"RemoveAlbum\" method=\"get\">" +
				    			"<input type=\"hidden\" name=\"name\" value=\"" + rs1.getString("title") + "\">" +
						      	"<input type=\"submit\" value=\"Delete\"></td></form>");
				}
			    }catch(SQLException e ){
			    System.err.println(e);
			    }
				//Close table
				out.println("<tr>");
				out.print("<td colspan=\"4\"><b>Total</b></td>");
				out.print("<td><b>" + grandTotal + "</b></td><td></td>");
				out.println("</tr></table>");
			    
				out.println("<p><a href=\"index.html\">Go Home<a>");
				out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
