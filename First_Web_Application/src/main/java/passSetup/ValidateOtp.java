package passSetup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/otpValidation")
public class ValidateOtp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int value=Integer.parseInt(request.getParameter("otp"));
		HttpSession session=request.getSession();
		int otp=(int)session.getAttribute("otp");
		
		
		RequestDispatcher dispatcher=null;
		PrintWriter pw=response.getWriter();
		
		
		if (value==otp) 
		{
				request.setAttribute("email", request.getParameter("email"));
				request.setAttribute("status", "success");
			  dispatcher=request.getRequestDispatcher("setNewPass_Page.html");
			dispatcher.include(request, response);
		}
		else
		{	
			
			dispatcher=request.getRequestDispatcher("enterOtp.html");
			dispatcher.include(request, response);
			pw.print("<html><body><center><div style=\"width: 775px;"
					+" width: 850px;"
					+ "  height: 50px;"
					+ "  text-align: center;"
					+ "  background-color: white;"
						+" margin: 10px;"
						+ "  padding:10px;"
					+ " \">");
			pw.print("<b><h2 style=\"color:red;\">Invalid OTP Please Check the OTP</h2>");
			pw.print("</div></center></body></html>");
			
			
		
		}
	}
}
