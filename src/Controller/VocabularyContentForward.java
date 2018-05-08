package Controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.VocabularyDAO;
import DB.DBConnection;

/**
 * Servlet implementation class VocabularyContentForward
 */
@WebServlet("/VocabularyContentForward")
public class VocabularyContentForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VocabularyContentForward() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String vocabularyguidelineid = request.getParameter("vocabularyguidelineid");
		request.setAttribute("vocabularyguidelineid", vocabularyguidelineid);
		RequestDispatcher rd = request.getRequestDispatcher("View/Admin/InsertVocaContent.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DBConnection.CreateConnection();
		String vocabularyguidelineidstr = request.getParameter("vocabularyguidelineid");
		int vocabularyguidelineid = Integer.parseInt(vocabularyguidelineidstr);
		
		
		String test = VocabularyDAO.UploadFileExcelVoca(conn, request, response, vocabularyguidelineid);
		if (test.equals("Success"))
		{
		
			RequestDispatcher rd = request.getRequestDispatcher("View/Admin/InsertImageAudioVoca.jsp");
			rd.forward(request,response);	
		}
		else 
		{
			request.setAttribute("msgvoca",test);
			request.setAttribute("vocabularyguidelineid", vocabularyguidelineid);
	    	RequestDispatcher rd = request.getRequestDispatcher("View/Admin/InsertVocaContent.jsp");
			rd.forward(request,response);		 
		}
	}

}
