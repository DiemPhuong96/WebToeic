package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BEAN.Vocabularycontent;

import DAO.VocabularyDAO;
import DB.DBConnection;


@WebServlet("/ListVocabulary")
public class ListVocabulary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ListVocabulary() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageidstr = request.getParameter("pageid");
		int pageid = Integer.parseInt(pageidstr);
		String vocabularyguidelineidstr = request.getParameter("vocabularyguidelineid");
		int vocabularyguidelineid = Integer.parseInt(vocabularyguidelineidstr);
		
		int count = 4;
		int numberpage = pageid;
		if(pageid!=1) {
			pageid = pageid -1;
			pageid = pageid*count +1;
		}
		 
		Connection conn = DBConnection.CreateConnection();
		
		List<Vocabularycontent> list = VocabularyDAO.DisplayListVocabulary(pageid, count, conn, request, vocabularyguidelineid);
		int sumrow = VocabularyDAO.countrowDetail(conn,vocabularyguidelineid);
		int maxpageid = 0;
		if(sumrow < count) {
			maxpageid = 1;
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
		request.setAttribute("listvocabulary", list);
		request.setAttribute("vocabularyguidelineid", vocabularyguidelineid);
		request.setAttribute("numberpage", numberpage);
		
		RequestDispatcher rd = request.getRequestDispatcher("View/DetailVocabolary.jsp");
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
