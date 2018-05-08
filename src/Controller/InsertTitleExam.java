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

import BEAN.Examination;
import DAO.ExamTestDAO;
import DB.DBConnection;

/**
 * Servlet implementation class InsertTitleExam
 */
@WebServlet("/InsertTitleExam")
public class InsertTitleExam extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertTitleExam() {
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
		String examinationname = request.getParameter("examinationname");
		Examination ex = new Examination();
		ex.setExaminationname(examinationname);
		Connection conn= DBConnection.CreateConnection();
		try {
			boolean kt = ExamTestDAO.insertTileExam(conn, ex, request);
			if(kt) {
				int examinationid = ExamTestDAO.RetrieveIdExamtest(ex, conn, request);
				request.setAttribute("examinationid", examinationid);
				RequestDispatcher rd = request.getRequestDispatcher("View/Admin/UploadImageFileExamTest.jsp");
				rd.forward(request, response);
			}
			
			else {
				request.setAttribute("msgexamtest", "Insert name fail");
				RequestDispatcher rd = request.getRequestDispatcher("ExamTestForward");
				rd.forward(request, response);
			}
			conn.close();
		} catch (SQLException e) {
			request.setAttribute("msgexamtest", e.getMessage());
		}
	}

}
