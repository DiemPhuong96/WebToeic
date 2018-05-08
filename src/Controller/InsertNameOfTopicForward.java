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


import BEAN.Vocabularyguideline;

import DAO.VocabularyDAO;
import DB.DBConnection;

/**
 * Servlet implementation class InsertNameOfTopicForward
 */
@WebServlet("/InsertNameOfTopicForward")
public class InsertNameOfTopicForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertNameOfTopicForward() {
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
		String name = request.getParameter("TopicName");
		Vocabularyguideline voca = new Vocabularyguideline();
		voca.setVocabularyname(name);
		Connection conn= DBConnection.CreateConnection();
		try {
			boolean kt = VocabularyDAO.insertNameOfTopic(conn, voca, request);
			if(kt) {
				int vocabularyguidelineid = VocabularyDAO.RetrieveVocabularyguidelineid(voca, conn, request);
				request.setAttribute("vocabularyguidelineid", vocabularyguidelineid);
				RequestDispatcher rd = request.getRequestDispatcher("View/Admin/InsertAvatarVoca.jsp");
				rd.forward(request, response);
			}
			
			else {
				request.setAttribute("msglistvoca", "Insert name fail");
				RequestDispatcher rd = request.getRequestDispatcher("VocabularyForward?pageid=1");
				rd.forward(request, response);
			}
			conn.close();
		} catch (SQLException e) {
			request.setAttribute("msglistvoca", e.getMessage());
		}
	}

}
