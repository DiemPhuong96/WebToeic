package Controller;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BEAN.GrammarGuideline;
import DAO.GrammarGuidelineDAO;
import DB.DBConnection;

/**
 * Servlet implementation class InsertNameGrammarController
 */
@WebServlet("/InsertNameGrammarController")
public class InsertNameGrammarController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertNameGrammarController() {
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
		
		String name = request.getParameter("grammarname");
		GrammarGuideline gg = new GrammarGuideline();
		gg.setGrammarname(name);
		Connection conn= DBConnection.CreateConnection();
		try {
			boolean kt = GrammarGuidelineDAO.insertGrammarGuidelineTitle(conn, gg, request);
			if(kt) {
				int grammarguidelineid = GrammarGuidelineDAO.RetrieveIdGrammarGuideline(gg, conn, request);
				request.setAttribute("grammarguidelineid", grammarguidelineid);
				RequestDispatcher rd = request.getRequestDispatcher("View/Admin/InsertGrammarImage.jsp");
				rd.forward(request, response);
			}
			
			else {
				request.setAttribute("msglistgrammar", "Insert name fail");
				RequestDispatcher rd = request.getRequestDispatcher("ListGrammarGuidelineForward?pageid=1");
				rd.forward(request, response);
			}
			conn.close();
		} catch (SQLException e) {
			request.setAttribute("msglistgrammar", e.getMessage());
		}
	}

}
