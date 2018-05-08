package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import BEAN.Vocabularycontent;
import BEAN.Vocabularyguideline;

public class VocabularyDAO {
	public static List<Vocabularyguideline> DisplayVocabularyguideline(int start, int count, Connection conn, HttpServletRequest request){
		List<Vocabularyguideline> list = new ArrayList<Vocabularyguideline>();
		String sql = "SELECT * FROM vocabularyguideline limit "+(start-1)+", "+count;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ResultSet rs = ptmt.executeQuery();
			
			if(rs.isBeforeFirst()) {
				while(rs.next()) {
					Vocabularyguideline voca = new Vocabularyguideline();
					int vocabularyguidelineid = rs.getInt("vocabularyguidelineid");
					voca.setVocabularyguidelineid(vocabularyguidelineid);
					String vocabularyname = rs.getString("vocabularyname");
					voca.setVocabularyname(vocabularyname);
					voca.setVocabularyimage(rs.getString("vocabularyimage"));
					
					list.add(voca);
				}
			}
			else {
				request.setAttribute("msglistvoca", "Vocabulary is empty" );
				
			}
		
			
		} catch (SQLException e) {
			request.setAttribute("msglistvoca", e.getMessage() );
		}
		return list;
	}
	
	public static int countrow(Connection conn){
		int count = 0;
		try {
			PreparedStatement ptmt = conn.prepareStatement("select count(*) from vocabularyguideline");
			ResultSet rs = ptmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return count;
	}
	
	public static int countrowDetail(Connection conn, int vocabularyguidelineid){
		int count = 0;
		try {
			PreparedStatement ptmt = conn.prepareStatement("select count(*) from vocabularycontent where vocabularyguidelineid="+vocabularyguidelineid);
			ResultSet rs = ptmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return count;
	}
	public static void deleteVocaContent(int vocabularyguidelineid, Connection conn) {
		String sql = "delete from vocabularycontent where vocabularyguidelineid ="+vocabularyguidelineid;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
			ptmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void deleteVocaGuideline(int vocabularyguidelineid, Connection conn) {
		String sql = "delete from vocabularyguideline where vocabularyguidelineid ="+vocabularyguidelineid;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
			ptmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean insertNameOfTopic(Connection conn, Vocabularyguideline voca,HttpServletRequest request){
		PreparedStatement ptmt = null;
		String sql = "insert into vocabularyguideline(vocabularyname) values(?)";
		try {
			ptmt = conn.prepareStatement(sql);
			
			String vocabularyname = voca.getVocabularyname();
			
			ptmt.setString(1,vocabularyname);
			
			
			int kt = ptmt.executeUpdate();
			
			if(kt != 0) return true;
			
			ptmt.close();
		
		} catch (SQLException e) {
			request.setAttribute("msglistvoca", e.getMessage());
		}
		
		return false;
	}
	
	public static int RetrieveVocabularyguidelineid(Vocabularyguideline voca, Connection conn, HttpServletRequest request){
		
		String sql = "SELECT vocabularyguidelineid FROM vocabularyguideline where vocabularyname='"+voca.getVocabularyname()+"'";
		int vocabularyguidelineid =0;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ResultSet rs = ptmt.executeQuery();
			
			
				while(rs.next()) {
					
					vocabularyguidelineid = rs.getInt("vocabularyguidelineid");
				
				}
		ptmt.close();
		rs.close();
		
			
		} catch (SQLException e) {
			request.setAttribute("msgglistvoca", e.getMessage() );
		}
		return vocabularyguidelineid;
	}
	
	public static String UploadFileExcelVoca(Connection conn,HttpServletRequest request, HttpServletResponse response, int vocabularyguidelineid) throws ServletException, IOException {
		String test = "";
		ServletContext context = request.getServletContext();
		response.setContentType("text/html; charset=UTF-8");
		
		final String Address = context.getRealPath("FileVoca/");
	
		//final String Address = "F://";
		final int MaxMemorySize = 1024 * 1024 * 10; //3MB
		final int MaxRequestSize = 1024 * 1024 * 10; //50 MB
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (!isMultipart)
		{
			test = "not have enctypr: multipart/form-data";
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		
		// Set factory constraints
		factory.setSizeThreshold(MaxMemorySize);

		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		
		// Set overall request size constraint
		upload.setSizeMax(MaxRequestSize);
		
		
		
		try 
		{
			// Parse the request
			List<FileItem> items = upload.parseRequest(request);
			
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			
			while (iter.hasNext()) 
			{
			    FileItem item = iter.next();

			    if (!item.isFormField()) 
			    {
			    	 String fileName = item.getName();
			    	 
			    	 //pathFile: vị trí mà chúng ta muốn upload file vào
			    	 //gửi cho server
			    	 
			    	String pathFile = Address + File.separator + fileName;
			    	 
			    	File uploadedFile = new File(pathFile);
			    	
			    	
			    	boolean kt = uploadedFile.exists();
			    	 
			    	try 
			    	{
			    		
			    		if (kt == true)
			    		{
			    					    
			    			test = "File exits. Require: test file again!";
			    		}
			    		else
			    		{		    			
			    			item.write(uploadedFile);
			    			VocabularyDAO.ImportVocaFromExcel(request, response, conn,pathFile , vocabularyguidelineid);
			    			test="Success";
			    		}
						
						
					} 
			    	catch (Exception e) 
			    	{ 		
			    		test = e.getMessage();
					}   	 
			    } 
			    else 
			    {
			    	test = "Upload file fail";
			    }
			}
			
		} 
		catch (FileUploadException e) 
		{
			test = e.getMessage();
		}
		
		return test;
	}
	
	
	public static void ImportVocaFromExcel(HttpServletRequest request, HttpServletResponse response, Connection conn, String adress, int vocabularyguidelineid) throws ServletException, IOException{
		// doc file vo
		InputStream inp;
		try {
			inp = new FileInputStream(adress);//truyen file
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp)); 
			
			Sheet sheet = wb.getSheetAt(0);// 0 tro ve sheet 1
			
			for(int i = 1; i <= sheet.getLastRowNum(); i++){
				Row row = sheet.getRow(i); // tro vo tung hang
			
				int num = (int)row.getCell(0).getNumericCellValue();
				String vocabularycontentname = row.getCell(1).getStringCellValue();
				String transcribe = row.getCell(2).getStringCellValue();
				String 	audiomp3 = row.getCell(3).getStringCellValue();
				String audiogg = row.getCell(4).getStringCellValue();
				String 	image = row.getCell(5).getStringCellValue();
				String 	mean = row.getCell(6).getStringCellValue();
				String 	example = row.getCell(7).getStringCellValue();
				Vocabularycontent voca = new Vocabularycontent();
				
				voca.setNum(num);
				voca.setVocabularycontentname(vocabularycontentname);
				voca.setAudiogg(audiogg);
				voca.setAudiomp3(audiomp3);
				voca.setExample(example);
				voca.setImage(image);
				voca.setMean(mean);
				voca.setTranscribe(transcribe);
				voca.setVocabularyguidelineid(vocabularyguidelineid);
				
				VocabularyDAO.InsertData(request, voca, conn);
			}
			
			wb.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
		 catch (IOException e) {
		e.printStackTrace();
		
		}
		
	}
	
	
	public static void InsertData(HttpServletRequest request,Vocabularycontent voca, Connection conn){
		String sql = "insert into vocabularycontent(vocabularycontentname,transcribe,audiomp3,audiogg,mean,vocabularyguidelineid,image,num,Example) values (?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, voca.getVocabularycontentname());
			ptmt.setString(2, voca.getTranscribe());
			ptmt.setString(3, voca.getAudiomp3());
			ptmt.setString(4, voca.getAudiogg());
			ptmt.setString(5, voca.getMean());
			ptmt.setInt(6, voca.getVocabularyguidelineid());
			ptmt.setString(7, voca.getImage());
			ptmt.setInt(8, voca.getNum());
			ptmt.setString(9, voca.getExample());
			
			ptmt.executeUpdate();
			
			ptmt.close();
			
		
		} catch (SQLException e) {
			request.setAttribute("message", e.getMessage());
			
		}
	}
	
	
	public static void UploadFileImageVoca(Connection conn,HttpServletRequest request, HttpServletResponse response, int vocabularyguidelineid ) throws ServletException, IOException{
		ServletContext context = request.getServletContext();
		
		
		final String Address = context.getRealPath("/ImageUpload/");
		final int MaxMemorySize = 1024*1024*10; // 3MB
		
		final int MaxRequestSize = 1024*104*10; //50MB
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); // neu nguoi lap trinh thieu enctype="multipart/form-data" thi se thong bao loi
		if(! isMultipart){
			request.setAttribute("msgvocaimage", "not have enctypr: multipart/form-data");
			request.setAttribute("vocabularyguidelineid", vocabularyguidelineid);
			RequestDispatcher rd = request.getRequestDispatcher("View/Admin/InsertAvatarVoca.jsp");
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
		
		// Set overall request size constraint(dung luong toi da cua file ma user upload)
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
			        	
			        	
			        		request.setAttribute("msgvocaimage", "File exits. Require: test file again!");
			        		
			        	
			        	}
			        	else {
			        		
			        		item.write(uploadFile);
			        		
							VocabularyDAO.insertVocabularyguidelineid(conn, fileName, request, vocabularyguidelineid);
							request.setAttribute("vocabularyguidelineid", vocabularyguidelineid);
							RequestDispatcher rd = request.getRequestDispatcher("VocabularyForward?pageid=1");
					    	rd.forward(request, response);
			        	}
						
						
					} catch (Exception e) {
					  	
						
						request.setAttribute("msgvocaimage", e.getMessage());
					
		        	
					}
			    } 
			    else {
			    	request.setAttribute("msgvocaimage", "Upload file fail");
			    	
			    }
			
			}
			
			
		} catch (FileUploadException e) {
			
			//request.setAttribute("msgvocaimage", e.getMessage());
			e.printStackTrace();
			
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("InsertAvatarVocaController");
    	rd.forward(request, response);
		}
		
		public static void insertVocabularyguidelineid(Connection conn, String image,HttpServletRequest request,int vocabularyguidelineid){
			PreparedStatement ptmt = null;
			String sql = "update vocabularyguideline set vocabularyimage=? where vocabularyguidelineid="+vocabularyguidelineid;
			try {
				ptmt = conn.prepareStatement(sql);
				
				
				
				ptmt.setString(1,image);
				
				
				ptmt.executeUpdate();
				
				
				
				ptmt.close();
			
			} catch (SQLException e) {
				request.setAttribute("msgvocaimage", e.getMessage());
			}
			
		
		}
		
		public static List<Vocabularycontent> DisplayListVocabulary(int start, int count, Connection conn, HttpServletRequest request, int vocabularyguidelineid){
			List<Vocabularycontent> list = new ArrayList<Vocabularycontent>();
			String sql = "SELECT * FROM vocabularycontent where vocabularyguidelineid = "+vocabularyguidelineid+" limit "+(start-1)+","+count;
			
				PreparedStatement ptmt;
				try {
					ptmt = conn.prepareStatement(sql);
					
					ResultSet rs = ptmt.executeQuery();
					
					
					while(rs.next()) {
						Vocabularycontent voca = new Vocabularycontent();
						int vocabularycontentid = rs.getInt("vocabularycontentid");
						voca.setVocabularycontentid(vocabularycontentid);
						String vocabularycontentname = rs.getString("vocabularycontentname");
						voca.setVocabularycontentname(vocabularycontentname);
						String transcribe = rs.getString("transcribe");
						voca.setTranscribe(transcribe);
						String audiomp3 = rs.getString("audiomp3");
						voca.setAudiomp3(audiomp3);
						String audiogg = rs.getString("audiogg");
						voca.setAudiogg(audiogg);
						String mean = rs.getString("mean");
						voca.setMean(mean);
						voca.setVocabularyguidelineid(vocabularyguidelineid);
						String image = rs.getString("image");
						voca.setImage(image);
						int num = rs.getInt("num");
						voca.setNum(num);
						String example = rs.getString("Example");
						voca.setExample(example);
						
						list.add(voca);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			
			
			return list;
		}
		
}
