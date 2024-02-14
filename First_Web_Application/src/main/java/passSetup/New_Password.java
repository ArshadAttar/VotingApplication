package passSetup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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

@WebServlet("/setPassLink")
public class New_Password extends HttpServlet {
	static Connection con;
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/1eja10","root","sql123");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String newPassword = req.getParameter("pass1");
		String confPassword = req.getParameter("pass2");
		RequestDispatcher dispatcher = null;
		PrintWriter pw=resp.getWriter();
		
		String query="update voting set userPass = ? where email = ? ";
		
		if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {
			
			try {
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, newPassword);
				pst.setString(2, (String) session.getAttribute("email"));

				int rowCount = pst.executeUpdate();
				if (rowCount > 0) {
					dispatcher = req.getRequestDispatcher("home.html");
					
				} else {
					pw.print("<h1>Something Erro in New Password Page</h1>");
					dispatcher = req.getRequestDispatcher("index.html");
					
				}
				dispatcher.include(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			dispatcher = req.getRequestDispatcher("newPass.html");
			dispatcher.include(req, resp);
			pw.print("<html><body><div style=\"width: 775px;"
					+" max-width: 850px;"
					+ "  height: 60px;"
					+ "  text-align: center;"
					+ "  background-color: white;"
					+ " \">");
			pw.print("<b><h2 style=\"color:red;\">Password Not Matched Enter Correct Password</h2>");
			pw.print("</div></body></html>");
			
		}
	}
}
