package Controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import DAO.VocabularyDAO;
import DB.DBConnection;

/**
 * Servlet implementation class InsertAvatarVocaController
 */
@WebServlet("/InsertAvatarVocaController")
public class InsertAvatarVocaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertAvatarVocaController() {
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
		Connection conn = DBConnection.CreateConnection();
		String vocabularyguidelineidstr = request.getParameter("vocabularyguidelineid");
		int vocabularyguidelineid = Integer.parseInt(vocabularyguidelineidstr);
		VocabularyDAO.UploadFileImageVoca(conn, request, response, vocabularyguidelineid);
	}

}
