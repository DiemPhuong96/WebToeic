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

import BEAN.GrammarGuideline;
import DAO.GrammarGuidelineDAO;
import DB.DBConnection;

/**
 * Servlet implementation class ListGrammarGuidelineForward
 */
@WebServlet("/ListGrammarGuidelineForward")
public class ListGrammarGuidelineForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListGrammarGuidelineForward() {
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
			int count = 4;
			if(pageid == 1){
				
			}
			 
			else{
				pageid = pageid - 1;
				pageid = pageid*count + 1;
			}
			Connection conn = DBConnection.CreateConnection();
			
			List<GrammarGuideline> list = GrammarGuidelineDAO.DisplayGrammarGuideline(pageid, count, conn, request);
			int sumrow = GrammarGuidelineDAO.countrow(conn);
			int maxpageid = 0;
			if((sumrow/count)%2 == 0) {
				 maxpageid = (sumrow/count);
			}
			else {
				 maxpageid = (sumrow/count) +1;
			}
			request.setAttribute("maxpageid", maxpageid);
			request.setAttribute("listgrammarguide", list);
			request.setAttribute("numberpage", Integer.parseInt(pageidstr));
			conn.close();
		} catch (SQLException e) {
			request.setAttribute("msglistgrammar", e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("View/Admin/ListGrammarGuideline.jsp");
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
