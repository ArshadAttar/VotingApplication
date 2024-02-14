package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.MysqlConnection;


@WebServlet("/registerLink1")
public class Registration extends HttpServlet{
	
	static Connection con;
	
	@Override
	public void init() throws ServletException {
		con=MysqlConnection.getConnection();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String userName=req.getParameter("name");
		String email=req.getParameter("email");
		String contact=req.getParameter("contact");
		String gender=req.getParameter("gender");
		String state=req.getParameter("state");
		String idProof=req.getParameter("idprof");
		String idNumber=req.getParameter("idNumber");
		String password=req.getParameter("pass1");
		
		PreparedStatement pstmt=null;
		PrintWriter pw=resp.getWriter();
		
		String insertData="insert into voting values(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			pstmt=con.prepareStatement(insertData);
			
			pstmt.setInt(1, 0);
			pstmt.setString(2, userName);
			pstmt.setString(3, email);
			pstmt.setString(4, contact);
			pstmt.setString(5, gender);
			pstmt.setString(6, state);
			pstmt.setString(7, idProof);
			pstmt.setString(8, idNumber);
			pstmt.setString(9, password);
			pstmt.setInt(10, 0);
			
			int count=pstmt.executeUpdate();
			if(count>0)
			{
				RequestDispatcher rd=req.getRequestDispatcher("");
				pw.print("<h1> REGISTRATION SUCCESSFULL </h1>");
				rd.include(req, resp);
			}
			else {
				pw.print("<h1> SOMETHING WENT WRONG </h1>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
