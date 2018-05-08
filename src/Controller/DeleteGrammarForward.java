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

import DAO.GrammarGuidelineDAO;
import DB.DBConnection;

/**
 * Servlet implementation class DeleteGrammarForward
 */
@WebServlet("/DeleteGrammarForward")
public class DeleteGrammarForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGrammarForward() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String grammarguidelineidstr= request.getParameter("grammarguidelineid");
		int grammarguidelineid = Integer.parseInt(grammarguidelineidstr);
		Connection conn = DBConnection.CreateConnection();
		try {
			
			GrammarGuidelineDAO.deletegrammarguidelineidcmt(grammarguidelineid, conn);
			GrammarGuidelineDAO.deleteGrammar(grammarguidelineid, conn);
			RequestDispatcher rd = request.getRequestDispatcher("ListGrammarGuidelineForward?pageid=1");
			rd.forward(request, response);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
