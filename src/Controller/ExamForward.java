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

import BEAN.Examination;
import DAO.ExamTestDAO;
import DB.DBConnection;

/**
 * Servlet implementation class ExamForward
 */
@WebServlet("/ExamForward")
public class ExamForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExamForward() {
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
			if(pageid!=1) {
				pageid = pageid -1;
				pageid = pageid*count +1;
			}
			 
			int numberpage = pageid;
			Connection conn = DBConnection.CreateConnection();
			
			List<Examination> list = ExamTestDAO.DisplayExamTest(pageid, count, conn, request);
			int sumrow = ExamTestDAO.countrow(conn);
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
			request.setAttribute("listexamtest", list);
			request.setAttribute("numberpage", numberpage);
			conn.close();
		} catch (SQLException e) {
			request.setAttribute("msglisexam", e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("View/Admin/ExamTest.jsp");
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
