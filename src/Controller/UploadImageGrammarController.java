package Controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.GrammarGuidelineDAO;
import DB.DBConnection;


@WebServlet("/UploadImageGrammarController")
public class UploadImageGrammarController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImageGrammarController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DBConnection.CreateConnection();
		String grammarguidelineidstr = request.getParameter("grammarguidelineid");
		int grammarguidelineid = Integer.parseInt(grammarguidelineidstr);
		GrammarGuidelineDAO.UploadFileImageGrammar(conn,request, response, grammarguidelineid);
		
	}

}
