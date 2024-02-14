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

@WebServlet("/votingLink")
public class VotingServlet extends HttpServlet{
	
 private static	String votingQuery1="select doneVoting from voting where email=?";
 private static	String votingQuery2="select doneVoting from voting where contact=?";
 
	static Connection con;
	
	public void init() throws ServletException {
		con=MysqlConnection.getConnection();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw=resp.getWriter();
		String userData=LoginServlet.tempuserData;
		
		ResultSet rs=null;
		
		HttpSession session=req.getSession(false);
		
		if(session!=null) {
			
		if(userData.contains("@")) {
			PreparedStatement pstmt=null,pstmt1=null;
			try {
				pstmt=con.prepareStatement(votingQuery1);
				pstmt.setString(1, userData);
				rs=pstmt.executeQuery();
				if(rs.next() && rs.getInt(1)==0) {
						pstmt1=con.prepareStatement("update voting set doneVoting=1 where email=?");
						
						pstmt1.setString(1, userData);
						int count=pstmt1.executeUpdate();
						if(count>0) {
							int tempcount=voting(req, resp);
							if(tempcount>0){
									pw.print("<h1> Voting Done & Updated</h1>");
									pw.print("<button style='background-color: red; color: black; padding: 10px; "
											+ "border: none; border-radius: 5px;'><a href='index.html' style='text-decoration: none;"
											+ " color: white;'>Logout</a></button>");
									
								
							}					 
						}else {
							pw.print("<h1> Something error in Update Query</h1>");
						}
				}else {
					pw.print("<h1>You Have already Voted, Sorry Your Vote Doesn't Count</h1>");
					pw.print("<button style='background-color: green; color: white; padding: 10px; "
							+ "border: none; border-radius: 5px;'><a href='index.html' style='text-decoration: none;"
							+ " color: white;'>Home Page</a></button>");

				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			//validation For Contact Number
			PreparedStatement pstmt=null,pstmt1=null;
			try {
				pstmt=con.prepareStatement(votingQuery2);
				pstmt.setString(1, userData);
				rs=pstmt.executeQuery();
				if(rs.next() && rs.getInt(1)==0) {
						pstmt1=con.prepareStatement("update voting set doneVoting=1 where contact=?");
						
						pstmt1.setString(1, userData);
						int count=pstmt1.executeUpdate();
						if(count>0) {
							int tempcount=voting(req, resp);
							if(tempcount>0){
								pw.print("<h1> Voting Done & Updated</h1>");
								pw.print("<button style='background-color: red; color: black; padding: 10px; "
										+ "border: none; border-radius: 5px;'><a href='index.html' style='text-decoration: none;"
										+ " color: white;'>Logout</a></button>");
							}
							 
						}else {
							pw.print("<h1> Something error in Update Query</h1>");
						}
				}else {
					pw.print("<h1>You Have already Voted, Sorry Your Vote Doesn't Count</h1>");
					pw.print("<button style='background-color: green; color: white; padding: 10px; border: none; "
							+ "border-radius: 5px;'><a href='index.html' style='text-decoration: none; "
							+ "color: white;'>Home Page</a></button>");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}else {
			pw.print("<h1>FIRST LOGIN</h1>");
			req.getRequestDispatcher("index.html").include(req, resp);
		}
		
	}
	private static int voting(HttpServletRequest req, HttpServletResponse resp) {
		String result=req.getParameter("voting");
		int count=0;
		String votecountQuery="select voting from partyvotes where p_name=?";
		String updateVote="update partyvotes set voting=voting+1 where p_name=? ";
		
		PreparedStatement pstmt=null,pstmt1=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(votecountQuery);
			pstmt.setString(1, result);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				pstmt1=con.prepareStatement(updateVote);
				pstmt1.setString(1, result);
				count=pstmt1.executeUpdate();
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return count;
		
	}
}
