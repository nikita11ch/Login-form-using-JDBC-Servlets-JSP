package login;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet 
{
	Connection con;
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

    
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // Call super.init() to ensure proper initialization
        
        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Establish the database connection
            this.con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin");
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error initializing database connection", e);
        }
    }

	
	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s1=request.getParameter("uname");
		String s2=request.getParameter("pword");
		try {
			PreparedStatement pstmt=con.prepareStatement("select * from uinfo where uname=? and pword=?");
			pstmt.setString(1,s1);
			pstmt.setString(2,s2);
			ResultSet rs=pstmt.executeQuery();
			PrintWriter pw= response.getWriter();
			pw.println("<html><body bgcolor=yellow text=blue><h1><center>");
			pw.println("Success");
			if(rs.next())
			{
				pw.println("WELCOME "+s1+"!");
			}
			else
			{
				pw.println("INVALID USERNAME/PASSWORD");
			}
			pw.println("</center></h1></body></html>");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
