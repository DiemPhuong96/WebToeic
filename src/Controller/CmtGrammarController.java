package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BEAN.CmtGrammar;

import DAO.CommentCmtDAO;

import DB.DBConnection;

/**
 * Servlet implementation class CmtGrammarController
 */
@WebServlet("/CmtGrammarController")
public class CmtGrammarController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CmtGrammarController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String content = request.getParameter("content");
			//String memberidstr = request.getParameter("memberid");
			int memberid = Integer.parseInt(request.getParameter("memberid"));
			//String grammarguidelineidstr = request.getParameter("grammarguidelineid");
			int grammarguidelineid = Integer.parseInt(request.getParameter("grammarguidelineid"));
			Connection conn = DBConnection.CreateConnection();
			
			CmtGrammar cmt = new CmtGrammar();
			cmt.setCmtgrammercontent(content);
			cmt.setMemberid(memberid);
			cmt.setCmtgrammarid(grammarguidelineid);
		
			CommentCmtDAO.InsertcommentGrammar(conn, cmt);
			
			List<CmtGrammar> list = CommentCmtDAO.DisplaycommentGrammar(conn, grammarguidelineid);
			
			request.setAttribute("listgrammarcomment",list);
			int countrowcmt = CommentCmtDAO.countrowcmt(conn, grammarguidelineid);
			request.setAttribute("countrowcmt", countrowcmt);
			
			RequestDispatcher rd = request.getRequestDispatcher("View/ListCmtGrammar.jsp");
			rd.forward(request,response);
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
	}

	}


