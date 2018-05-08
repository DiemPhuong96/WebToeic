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


import BEAN.Vocabularyguideline;
import DAO.VocabularyDAO;
import DB.DBConnection;

/**
 * Servlet implementation class VocabularyForward
 */
@WebServlet("/VocabularyForward")
public class VocabularyForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VocabularyForward() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String pageidstr = request.getParameter("pageid");
			int pageid = Integer.parseInt(pageidstr);
			int count = 10;
			int numberpage = pageid;
			if(pageid!=1) {
				pageid = pageid -1;
				pageid = pageid*count +1;
			}
			 
		
			Connection conn = DBConnection.CreateConnection();
			
			List<Vocabularyguideline> list = VocabularyDAO.DisplayVocabularyguideline(pageid, count, conn, request);
			int sumrow = VocabularyDAO.countrow(conn);
			int maxpageid = 0;
			if(sumrow < count) {
				maxpageid = 0;
			}
			else {
				if((sumrow%count)!=0) {
					maxpageid = sumrow/count +1;
				}
				else {
					maxpageid = sumrow/count;
				}
				
			}
			request.setAttribute("maxpageid", maxpageid);
			request.setAttribute("listvoca", list);
			request.setAttribute("numberpage", numberpage);
			conn.close();
		} catch (SQLException e) {
			request.setAttribute("msglistvoca", e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("View/Admin/Vocabulary.jsp");
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
