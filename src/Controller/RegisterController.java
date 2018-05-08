package Controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BEAN.Member;
import DAO.RegisterDAO;
import DB.DBConnection;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("View/Register.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DBConnection.CreateConnection();
		String name = request.getParameter("name");
		String membername = request.getParameter("membername");
		String pass = request.getParameter("pass");
		Member member = new Member();
		member.setMembername(name);
		member.setFullname(membername);
		member.setMemberpass(pass);
		boolean test = RegisterDAO.insertAccount(conn, member, request);
		if(test) {
			request.setAttribute("msgregister", "Sign up success");
			RequestDispatcher rd = request.getRequestDispatcher("View/Register.jsp");
			rd.forward(request, response);
		}
		
		else {
			request.setAttribute("msgregister", "Sign up failed");
			RequestDispatcher rd = request.getRequestDispatcher("View/Register.jsp");
			rd.forward(request, response);
		}
	}

}
