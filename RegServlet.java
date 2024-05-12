package login;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class RegServlet extends HttpServlet 
{
	Connection con;
	private static final long serialVersionUID = 1L;
       
    public RegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	public void init(ServletConfig config) throws ServletException 
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");
		} 
		catch (SQLException  | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	
	}

	public void destroy() 
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s1= request.getParameter("fname");
		String s2=request.getParameter("lname");
		String s3=request.getParameter("uname");
		String s4=request.getParameter("pword");
		
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into uinfo values(?,?,?,?)");
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			pstmt.setString(3, s3);
			pstmt.setString(4, s4);
			pstmt.executeUpdate();
		    PrintWriter pw= response.getWriter();
		    pw.println("<html><body bgcolor=cyan text=blue><center>");
		    pw.println("<h1>YOU HAVE REGISTERED SUCCESSFULLY</h1>");
		    pw.println("<a href=login.html>Login</a>");
		    pw.println("</center></body></html>");
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}