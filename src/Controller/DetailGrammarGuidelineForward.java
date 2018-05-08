package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BEAN.CmtGrammar;
import DAO.CommentCmtDAO;
import DAO.ListGrammarGuidelineDAO;
import DB.DBConnection;


@WebServlet("/DetailGrammarGuidelineForward")
public class DetailGrammarGuidelineForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailGrammarGuidelineForward() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	String grammarguidelineid = request.getParameter("grammarguidelineid");
		int id = Integer.parseInt(request.getParameter("grammarguidelineid"));
		Connection conn = DBConnection.CreateConnection();	
		String content  = ListGrammarGuidelineDAO.DisplayContentGrammar(conn, id);
		request.setAttribute("grammarguidelineid", id);
		request.setAttribute("content", content);
		//con in dam va in nghien
		request.setAttribute("databasesenter", "\n");
		request.setAttribute("htmlenter", "<br/>");
		List<CmtGrammar> listcmt = CommentCmtDAO.DisplaycommentGrammar(conn, id);
		request.setAttribute("listcmt", listcmt);
		int count = CommentCmtDAO.countrowcmt(conn, id);
		request.setAttribute("countrowcmt", count);
		RequestDispatcher rd = request.getRequestDispatcher("View/DetailGrammarGuideline.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
