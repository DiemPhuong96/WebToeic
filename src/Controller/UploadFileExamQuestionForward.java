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
 * Servlet implementation class UploadFileExamQuestionForward
 */
@WebServlet("/UploadFileExamQuestionForward")
public class UploadFileExamQuestionForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UploadFileExamQuestionForward() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String examinationidstr = request.getParameter("examinationid");
		int examinationid = Integer.parseInt(examinationidstr);
		request.setAttribute("examinationid", examinationid);
		RequestDispatcher rd = request.getRequestDispatcher("View/Admin/UploadFileExamQuestion.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DBConnection.CreateConnection();
		String examinationidstr = request.getParameter("examinationid");
		int examinationid = Integer.parseInt(examinationidstr);
		
		
		String test = ExamTestDAO.UploadFileExcelExam(conn, request, response, examinationid);
		if (test.equals("Success"))
		{
			request.setAttribute("msgimageexam",test);
			RequestDispatcher rd = request.getRequestDispatcher("View/Admin/InsertImageAudioExam.jsp");
			rd.forward(request,response);	
		}
		else 
		{
			request.setAttribute("msgimageexam",test);
			request.setAttribute("examinationid", examinationid);
	    	RequestDispatcher rd = request.getRequestDispatcher("View/Admin/UploadFileExamQuestion.jsp");
			rd.forward(request,response);		 
		}
	}

}
