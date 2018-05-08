package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BEAN.AnswerUser;
import BEAN.Examinationquestion;
import BEAN.result;
import DAO.DisplayExamDAO;
import DAO.ExamTestDAO;
import DB.DBConnection;


/**
 * Servlet implementation class DetailExamForward
 */
@WebServlet("/DetailExamForward")
public class DetailExamForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailExamForward() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String examinationidstr = request.getParameter("examinationid");
		String memberidstr = request.getParameter("memberid");
		int memberid = Integer.parseInt(memberidstr);
		int examinationid = Integer.parseInt(examinationidstr);
	
		Connection conn = DBConnection.CreateConnection();
			request.setAttribute("memberid", memberid);
			request.setAttribute("examinationid", examinationid);
			List<Examinationquestion> list = ExamTestDAO.DisplayExamQuesion(conn, examinationid);
			request.setAttribute("listexamquesion", list);
			RequestDispatcher rd = request.getRequestDispatcher("View/DetailExam.jsp");
			rd.forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DBConnection.CreateConnection();
		String examinationidstr = request.getParameter("examinationid");
		String memberidstr = request.getParameter("memberid");
		int memberid = Integer.parseInt(memberidstr);
		int examinationid = Integer.parseInt(examinationidstr);
		
		int countrow = DisplayExamDAO.countrowquestion(conn, examinationid);
		List<Examinationquestion> listcorrectanswer = DisplayExamDAO.Exportcorrectanswer(conn, examinationid);
		List<AnswerUser> listansweruser = new ArrayList<AnswerUser>();
		
		int countcorrect = 0;
		for(int i = 1; i <= countrow; i ++){
			String answer = request.getParameter("ans["+i+"]");
		
			if(answer != null){
				AnswerUser au = new AnswerUser();
				au.setNum(i);
				au.setAnswer(answer);
				listansweruser.add(au);
				String correctanswer = DisplayExamDAO.correctanswer(conn, examinationid, i);
				if(answer.equals(correctanswer)) {
					countcorrect ++;
				}

		}
			else{
				
				AnswerUser au = new AnswerUser();
				au.setNum(i);
				au.setAnswer("emty");
				listansweruser.add(au);
		}
		}
		
	int countincorrect = countrow -countcorrect;
		result rs = new result();
		Date time =  new Date();
		rs.setCorrectanswernum(countcorrect);
		rs.setIncorrectanswernum(countincorrect);
		rs.setTime(time.toString());
		rs.setMemberid(memberid);
		rs.setExaminationid(examinationid);
		DisplayExamDAO.insertResult(conn, rs);
		request.setAttribute("countcorrect", countcorrect);
		request.setAttribute("sum", countrow);
		request.setAttribute("listcorrectanswer", listcorrectanswer);
		request.setAttribute("listansweruser", listansweruser);
		RequestDispatcher rd = request.getRequestDispatcher("View/ResultUsers.jsp");
		rd.forward(request, response);
	}

}
