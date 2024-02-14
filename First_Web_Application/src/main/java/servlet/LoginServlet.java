package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.MysqlConnection;

@WebServlet("/loginlink1")
public class LoginServlet extends HttpServlet{
	static String tempuserData;

static Connection con;
private static String query1="select userPass from voting where email=?";
private static String query2="select userPass from voting where contact=?";
	
	public void init() throws ServletException {
		con=MysqlConnection.getConnection();
	}
	
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String userData=req.getParameter("email");
			String userPassword=req.getParameter("pass");
			
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			PrintWriter pw=resp.getWriter();
			if(userData.contains("@"))
			{
				try {
					pstmt=con.prepareStatement(query1);
					pstmt.setString(1,userData);
					rs=pstmt.executeQuery();
					
					if(rs.next()) {
						if(rs.getString(1).equals(userPassword)) {
							tempuserData=userData;
							HttpSession session=req.getSession();
							RequestDispatcher rd=req.getRequestDispatcher("Voting.html");
							
							rd.forward(req, resp);
						}
						else {
							RequestDispatcher rd=req.getRequestDispatcher("index.html");
							pw.print("<h1>INVALID USERNAME OR PASSWORD...!</h1>");
							rd.include(req, resp);
						}
					}
					else
					{

						RequestDispatcher rd=req.getRequestDispatcher("index.html");
						pw.print("<h1>INVALID USERNAME OR PASSWORD</h1>");
						rd.include(req, resp);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					pstmt=con.prepareStatement(query2);
					pstmt.setString(1,userData);
					rs=pstmt.executeQuery();
					
					if(rs.next()) {
						if(rs.getString(1).equals(userPassword)) {
							tempuserData=userData;
							HttpSession session=req.getSession();
							RequestDispatcher rd=req.getRequestDispatcher("Voting.html");
							rd.forward(req, resp);
						}
						else {
							RequestDispatcher rd=req.getRequestDispatcher("index.html");
							pw.print("<h1>INVALID USERNAME OR PASSWORD...!</h1>");
							rd.include(req, resp);
						}
					}
					else
					{

						RequestDispatcher rd=req.getRequestDispatcher("index.html");
						pw.print("<h1>INVALID USERNAME OR PASSWORD</h1>");
						rd.include(req, resp);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			}
			
		}	
}
