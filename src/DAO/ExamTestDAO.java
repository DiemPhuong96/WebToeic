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

import BEAN.Examination;
import BEAN.Examinationquestion;



public class ExamTestDAO {
	

	public static List<Examination> DisplayExamTest(int start, int count, Connection conn, HttpServletRequest request){
		List<Examination> list = new ArrayList<Examination>();
		String sql = "SELECT * FROM examination limit "+(start-1)+", "+count;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ResultSet rs = ptmt.executeQuery();
			
			if(rs.isBeforeFirst()) {
				while(rs.next()) {
					Examination ex = new Examination();
					int examinationid = rs.getInt("examinationid");
					String examinationname = rs.getString("examinationname");
					String examinationimage = rs.getString("examinationimage");
					ex.setExaminationid(examinationid);
					ex.setExaminationimage(examinationimage);
					ex.setExaminationname(examinationname);
					
					list.add(ex);
				}
			}
			else {
				request.setAttribute("msgexamtest", "Lession is empty" );
				
			}
		
			
		} catch (SQLException e) {
			request.setAttribute("msgexamtest", e.getMessage() );
		}
		return list;
	}
	
	public static boolean insertTileExam(Connection conn, Examination ex,HttpServletRequest request){
		PreparedStatement ptmt = null;
		String sql = "insert into examination(examinationname) values(?)";
		try {
			ptmt = conn.prepareStatement(sql);
			
			String examinationname = ex.getExaminationname();
			
			ptmt.setString(1,examinationname);
			
			
			int kt = ptmt.executeUpdate();
			
			if(kt != 0) return true;
			
			ptmt.close();
		
		} catch (SQLException e) {
			request.setAttribute("msgexamtest", e.getMessage());
		}
		
		return false;
	}
	
	public static int RetrieveIdExamtest(Examination ex, Connection conn, HttpServletRequest request){
		
		String sql = "SELECT examinationid FROM examination where examinationname='"+ex.getExaminationname()+"'";
		int examinationid =0;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ResultSet rs = ptmt.executeQuery();
			
			
				while(rs.next()) {
					
					examinationid = rs.getInt("examinationid");
				
				}
		ptmt.close();
		rs.close();
		
			
		} catch (SQLException e) {
			request.setAttribute("msgexamtest", e.getMessage() );
		}
		return examinationid;
	}
	
	public static String UploadFileImageExam(Connection conn,HttpServletRequest request, HttpServletResponse response, int examinationid) throws ServletException, IOException {
		String test = "";
		ServletContext context = request.getServletContext();
		response.setContentType("text/html; charset=UTF-8");
		
		final String Address = context.getRealPath("ImageUpload/");
	
		//final String Address = "F://";
		final int MaxMemorySize = 1024 * 1024 * 3; //3MB
		final int MaxRequestSize = 1024 * 1024 * 50; //50 MB
		
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
			    			ExamTestDAO.insertidExam(conn, fileName, request, examinationid);
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
	
	public static void insertidExam(Connection conn, String image,HttpServletRequest request,int examinationid){
		PreparedStatement ptmt = null;
		String sql = "update examination set examinationimage=? where examinationid="+examinationid;
		
			
			
			try {
				ptmt = conn.prepareStatement(sql);
				
				
				
				ptmt.setString(1,image);
				
				
				ptmt.executeUpdate();
				
				
				ptmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	
	}
	
	public static int countrow(Connection conn){
		int count = 0;
		try {
			PreparedStatement ptmt = conn.prepareStatement("select count(*) from examination");
			ResultSet rs = ptmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return count;
	}
	
	public static void deleteResult(int examinationid, Connection conn) {
		String sql = "delete from result where examinationid ="+examinationid;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
			ptmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void deleteExamination(int examinationid, Connection conn) {
		String sql = "delete from examination where examinationid ="+examinationid;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
			ptmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void deleteExaminationQuesiton(int examinationid, Connection conn) {
		String sql = "delete from examinationquestion where examinationid ="+examinationid;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate();
			ptmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	// them cau hoi de thi vao mysql
	
	public static void ImportExamQuestionFromExcel(HttpServletRequest request, HttpServletResponse response, Connection conn, String adress, int examinationid) throws ServletException, IOException{
		// doc file vo
		InputStream inp;
		try {
			inp = new FileInputStream(adress);//truyen file
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp)); 
			
			Sheet sheet = wb.getSheetAt(0);// 0 tro ve sheet 1
			
			for(int i = 1; i <= sheet.getLastRowNum(); i++){
				Row row = sheet.getRow(i); // tro vo tung hang
			
				int num = (int)row.getCell(0).getNumericCellValue();
				String imagequestion = row.getCell(1).getStringCellValue();
				String audiomp3 = row.getCell(2).getStringCellValue();
				String 	audiogg = row.getCell(3).getStringCellValue();
				String paragraph = row.getCell(4).getStringCellValue();
				String 	question = row.getCell(5).getStringCellValue();
				String 	optiion1 = row.getCell(6).getStringCellValue();
				String 	option2 = row.getCell(7).getStringCellValue();
				String 	option3= row.getCell(8).getStringCellValue();
				String 	option4 = row.getCell(9).getStringCellValue();
				String 	correctanswer = row.getCell(10).getStringCellValue();
				Examinationquestion ex = new Examinationquestion();
				ex.setNum(num);
				ex.setAudiogg(audiogg);
				ex.setAudiomp3(audiomp3);
				ex.setParagraph(paragraph);
				ex.setCorrectanswer(correctanswer);
				ex.setQuestion(question);
				ex.setOption1(optiion1);
				ex.setOption2(option2);
				ex.setOption3(option3);
				ex.setOption4(option4);
				ex.setImagequestion(imagequestion);
				ex.setExaminationid(examinationid);
				ExamTestDAO.InsertData(request, ex, conn);
			}
			
			wb.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
		 catch (IOException e) {
		e.printStackTrace();
		
		}
		
	
	}
	
	public static void InsertData(HttpServletRequest request,Examinationquestion ex, Connection conn){
		String sql = "insert into examinationquestion(num,imagequestion,audiogg,audiomp3,paragraph,question,optiion1,option2,option3,option4,correctanswer,examinationid) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			int num = ex.getNum();
			ptmt.setInt(1, num);
			String imagequestion = ex.getImagequestion();
			ptmt.setString(2, imagequestion);
			ptmt.setString(3, ex.getAudiogg());
			ptmt.setString(4, ex.getAudiomp3());
			ptmt.setString(5, ex.getParagraph());
			ptmt.setString(6, ex.getQuestion());
			ptmt.setString(7, ex.getOption1());
			ptmt.setString(8, ex.getOption2());
			ptmt.setString(9, ex.getOption3());
			ptmt.setString(10, ex.getOption4());
			ptmt.setString(11, ex.getCorrectanswer());
			int examinationid = ex.getExaminationid();
			ptmt.setInt(12, examinationid);
			ptmt.executeUpdate();
			
			ptmt.close();
			
		
		} catch (SQLException e) {
			request.setAttribute("message", e.getMessage());
			
		}
	}
	
	// ham them file vao thu muc 
	
	public static String UploadFileExcelExam(Connection conn,HttpServletRequest request, HttpServletResponse response, int examinationid) throws ServletException, IOException {
		String test = "";
		ServletContext context = request.getServletContext();
		response.setContentType("text/html; charset=UTF-8");
		
		final String Address = context.getRealPath("FileExam/");
	
		//final String Address = "F://";
		final int MaxMemorySize = 1024 * 1024 * 3; //3MB
		final int MaxRequestSize = 1024 * 1024 * 50; //50 MB
		
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
			    			ExamTestDAO.ImportExamQuestionFromExcel(request, response, conn, pathFile, examinationid);
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
	


	public static String UploadFileImageAudioExam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String test = "";
		ServletContext context = request.getServletContext();
		response.setContentType("text/html; charset=UTF-8");
		
		final String Address = context.getRealPath("ImageAudioExam/");
	
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
	
	public static List<Examinationquestion> DisplayExamQuesion(Connection conn, int examinationid){
		List<Examinationquestion> list = new ArrayList<Examinationquestion>();
		String sql = "SELECT * FROM examinationquestion where examinationid ="+examinationid;
		
			PreparedStatement ptmt;
			try {
				ptmt = conn.prepareStatement(sql);
				ResultSet rs = ptmt.executeQuery();
				
				
				while(rs.next()) {
					Examinationquestion ex = new Examinationquestion();
					int examinationquestionid = rs.getInt("examinationquestionid");
					int num = rs.getInt("num");
					String imagequestion = rs.getString("imagequestion");
					String audiogg = rs.getString("audiogg");
					String audiomp3 = rs.getString("audiomp3");
					String paragraph = rs.getString("paragraph");
					String question = rs.getString("question");
					String option1 = rs.getString("optiion1");
					String option2 = rs.getString("option2");
					String option3 = rs.getString("option3");
					String option4 = rs.getString("option4");
					String correctanswer = rs.getString("correctanswer");
					ex.setAudiogg(audiogg);
					ex.setAudiomp3(audiomp3);
					ex.setCorrectanswer(correctanswer);
					ex.setExaminationid(examinationid);
					ex.setExaminationquestionid(examinationquestionid);
					ex.setImagequestion(imagequestion);
					ex.setNum(num);
					ex.setOption1(option1);
					ex.setOption2(option2);
					ex.setOption3(option3);
					ex.setOption4(option4);
					ex.setParagraph(paragraph);
					ex.setQuestion(question);
					
					
					list.add(ex);
				}
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return list;
	}
	
}
