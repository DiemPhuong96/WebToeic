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

import DAO.ExamTestDAO;
import DB.DBConnection;

/**
 * Servlet implementation class DeleteExamForward
 */
@WebServlet("/DeleteExamForward")
public class DeleteExamForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteExamForward() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String examinationidstr= request.getParameter("examinationid");
		int examinationid = Integer.parseInt(examinationidstr);
		Connection conn = DBConnection.CreateConnection();
		try {
			
			ExamTestDAO.deleteResult(examinationid, conn);
			ExamTestDAO.deleteExamination(examinationid, conn);
			ExamTestDAO.deleteExaminationQuesiton(examinationid, conn);
			RequestDispatcher rd = request.getRequestDispatcher("ExamForward?pageid=1");
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
