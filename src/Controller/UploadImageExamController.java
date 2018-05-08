package Controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ExamTestDAO;
import DB.DBConnection;

/**
 * Servlet implementation class UploadImageExamController
 */
@WebServlet("/UploadImageExamController")
public class UploadImageExamController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImageExamController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DBConnection.CreateConnection();
		String examinationidstr = request.getParameter("examinationid");
		int examinationid = Integer.parseInt(examinationidstr);
		
		
		String test = ExamTestDAO.UploadFileImageExam(conn, request, response, examinationid);
		if (test.equals("Success"))
		{
			RequestDispatcher rd = request.getRequestDispatcher("ExamForward?pageid=1");//vi sao k chuyen đc qua trang examtestforward
			rd.forward(request,response);	
		}
		else 
		{
			request.setAttribute("msgimageexam",test);
			request.setAttribute("examinationid", examinationid);
	    	RequestDispatcher rd = request.getRequestDispatcher("View/Admin/UploadImageFileExamTest.jsp");
			rd.forward(request,response);		 
		}
		
	}

}
