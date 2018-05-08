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


import DAO.VocabularyDAO;
import DB.DBConnection;

/**
 * Servlet implementation class DeleteVocaForward
 */
@WebServlet("/DeleteVocaForward")
public class DeleteVocaForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteVocaForward() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String vocabularyguidelineidstr= request.getParameter("vocabularyguidelineid");
		int vocabularyguidelineid	 = Integer.parseInt(vocabularyguidelineidstr);
		Connection conn = DBConnection.CreateConnection();
		try {
			
			VocabularyDAO.deleteVocaContent(vocabularyguidelineid, conn);
			VocabularyDAO.deleteVocaGuideline(vocabularyguidelineid, conn);
			RequestDispatcher rd = request.getRequestDispatcher("VocabularyForward?pageid=1");
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
