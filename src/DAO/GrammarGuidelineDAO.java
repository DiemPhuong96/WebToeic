package DAO;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import BEAN.GrammarGuideline;



public class GrammarGuidelineDAO {
	public static List<GrammarGuideline> DisplayGrammarGuideline(int start, int count, Connection conn, HttpServletRequest request){
		List<GrammarGuideline> list = new ArrayList<GrammarGuideline>();
		String sql = "SELECT * FROM grammerguideline limit "+(start-1)+", "+count;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ResultSet rs = ptmt.executeQuery();
			
			if(rs.isBeforeFirst()) {
				while(rs.next()) {
					GrammarGuideline gg = new GrammarGuideline();
					int grammarguidelineid = rs.getInt("grammarguidelineid");
					gg.setGrammarguidelineid(grammarguidelineid);
					String grammarname = rs.getString("grammarname");
					gg.setGrammarname(grammarname);
					gg.setGrammarimage(rs.getString("grammarimage"));
					gg.setContent(rs.getString("content"));
					list.add(gg);
				}
			}
			else {
				request.setAttribute("msglistgrammar", "Lession is empty" );
				
			}
		
			
		} catch (SQLException e) {
			request.setAttribute("msglistgrammar", e.getMessage() );
		}
		return list;
	}
	
	public static int RetrieveIdGrammarGuideline(GrammarGuideline gg, Connection conn, HttpServletRequest request){
	
		String sql = "SELECT grammarguidelineid FROM grammerguideline where grammarname='"+gg.getGrammarname()+"'";
		int grammarguidelineid =0;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ResultSet rs = ptmt.executeQuery();
			
			
				while(rs.next()) {
					
					 grammarguidelineid = rs.getInt("grammarguidelineid");
				
				}
		ptmt.close();
		rs.close();
		
			
		} catch (SQLException e) {
			request.setAttribute("msggrammarimage", e.getMessage() );
		}
		return grammarguidelineid;
	}
	
	public static boolean insertGrammarGuidelineTitle(Connection conn, GrammarGuideline gg,HttpServletRequest request){
		PreparedStatement ptmt = null;
		String sql = "insert into grammerguideline(grammarname) values(?)";
		try {
			ptmt = conn.prepareStatement(sql);
			
			String grammarname = gg.getGrammarname();
			
			ptmt.setString(1,grammarname);
			
			
			int kt = ptmt.executeUpdate();
			
			if(kt != 0) return true;
			
			ptmt.close();
		
		} catch (SQLException e) {
			request.setAttribute("msglistgrammar", e.getMessage());
		}
		
		return false;
	}
	//insert avata grammar
	public static void UploadFileImageGrammar(Connection conn,HttpServletRequest request, HttpServletResponse response, int grammarguidelineid) throws ServletException, IOException{
		ServletContext context = request.getServletContext();
		
		
		final String Address = context.getRealPath("/ImageUpload/");
		final int MaxMemorySize = 1024*1024*3; // 3MB
		
		final int MaxRequestSize = 1024*104*5; //50MB
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); // neu nguoi lap trinh thieu enctype="multipart/form-data" thi se thong bao loi
		if(! isMultipart){
			request.setAttribute("msggrammarimage", "not have enctypr: multipart/form-data");
			request.setAttribute("grammarguidelineid", grammarguidelineid);
			RequestDispatcher rd = request.getRequestDispatcher("UploadImageGrammarForward");
	    	rd.forward(request, response);
		} 
		//lop tao vung nho dem (tao bo nho dem, bo nho tam)
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//truyen vao kich thuoc vung nho
		factory.setSizeThreshold(MaxMemorySize);
		//truyen vao vi tri chua vung nho
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	
		// Create a new file upload handler (chuyen file)
		ServletFileUpload upload = new ServletFileUpload(factory);// lop servletfileupload giup su ly viec upload file
		
		// Set overall request siz constraint(dung luong toi da cua file ma user upload)
		upload.setSizeMax(MaxRequestSize);
	
		// Parse the request
		try {
			
			//chuyen file vo mot list
			List<FileItem> items = upload.parseRequest(request);// dua file vao trong list
			
			// Process the uploaded items (lay file trong list ra)
			Iterator<FileItem> iter = items.iterator();
			
			while (iter.hasNext()) { // kt trong do co file hay chua
				
			    FileItem item = iter.next(); // neu co roi thi chuyen toi file do

			    if (!item.isFormField()) {
			        String fileName = item.getName(); //lay ten file
			        // pathFile: vi tri ma chung ta muon upload file vao
			        //gui cho server
			        String pathFile = Address + File.separator + fileName;
			        
			        File uploadFile = new File(pathFile);
			       boolean kt = uploadFile.exists();
			        try {
			        	if(kt == true){
			        	
			        	
			        		request.setAttribute("msggrammarimage", "File exits. Require: test file again!");
			        		
			        	
			        	}
			        	else {
			        		
			        		item.write(uploadFile);
			        		
							GrammarGuidelineDAO.insertidGrammarGuideline(conn, fileName, request, grammarguidelineid);
							request.setAttribute("grammarguidelineid", grammarguidelineid);
							RequestDispatcher rd = request.getRequestDispatcher("ListGrammarGuidelineForward?pageid=1");
					    	rd.forward(request, response);
			        	}
						
						
					} catch (Exception e) {
					  	
						
						request.setAttribute("msggrammarimage", e.getMessage());
					
		        	
					}
			    } 
			    else {
			    	request.setAttribute("msggrammarimage", "Upload file fail");
			    	
			    }
			
			}
			
			
		} catch (FileUploadException e) {
			
			request.setAttribute("msggrammarimage", e.getMessage());
			
			
		}
		
		request.setAttribute("grammarguidelineid", grammarguidelineid);
		RequestDispatcher rd = request.getRequestDispatcher("View/Admin/InsertGrammarImage.jsp");
    	rd.forward(request, response);
    	// neu lam tieng viet se bi loi phong vi vay nen chuyen voi == string, qua trang forward de setattribute(ma chắc k cần do thằng dạy ngu thôi)
}
	public static void insertidGrammarGuideline(Connection conn, String image,HttpServletRequest request,int grammarguidelineid){
		PreparedStatement ptmt = null;
		String sql = "update grammerguideline set grammarimage=? where grammarguidelineid="+grammarguidelineid;
		try {
			ptmt = conn.prepareStatement(sql);
			
			
			
			ptmt.setString(1,image);
			
			
			ptmt.executeUpdate();
			
			
			
			ptmt.close();
		
		} catch (SQLException e) {
			request.setAttribute("msggrammarimage", e.getMessage());
		}
		
	
	}
	
	public static boolean insertGrammarGuidelineContent(Connection conn, GrammarGuideline gg,HttpServletRequest request, int grammarguidelineid){
		PreparedStatement ptmt = null;
		String sql = "update grammerguideline set content=? where grammarguidelineid="+grammarguidelineid;
		try {
			ptmt = conn.prepareStatement(sql);
			
			String content = gg.getContent();
			
			ptmt.setString(1,content);
			
			
			int kt = ptmt.executeUpdate();
			
			if(kt != 0) return true;
			
			ptmt.close();
		
		} catch (SQLException e) {
			request.setAttribute("msggrammarcontent", e.getMessage());
		}
		
		return false;
	}
	
	public static int countrow(Connection conn){
		int count = 0;
		try {
			PreparedStatement ptmt = conn.prepareStatement("select count(*) from grammerguideline");
			ResultSet rs = ptmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return count;
	}
	
	public static void deleteGrammar(int grammarguidelineid, Connection conn) {
		String sql = "delete from grammerguideline where grammarguidelineid ="+grammarguidelineid;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
			ptmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void deletegrammarguidelineidcmt(int grammarguidelineid, Connection conn) {
		String sql = "delete from cmtgrammar where grammarguidelineid ="+grammarguidelineid;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
			ptmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
