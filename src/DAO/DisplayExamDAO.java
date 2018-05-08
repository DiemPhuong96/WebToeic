package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BEAN.Examination;
import BEAN.Examinationquestion;
import BEAN.result;


public class DisplayExamDAO {
	public static List<Examination> DisplayExam(int start, int count, Connection conn){
		List<Examination> list = new ArrayList<Examination>();
	
		try {
			PreparedStatement ptmt = conn.prepareStatement("select* from examination limit "+(start-1)+", "+count);
			ResultSet rs = ptmt.executeQuery();
			while(rs.next()){
				Examination ex = new Examination();
				int examinationid = rs.getInt("examinationid");
				String 	examinationname = rs.getString("examinationname");
				String 	examinationimage = rs.getString("examinationimage");
				
				ex.setExaminationid(examinationid);
				ex.setExaminationimage(examinationimage);
				ex.setExaminationname(examinationname);
			
				list.add(ex);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return list;
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
	
	public static String DisplayContentExam(Connection conn, int id){
		
	String content = "";
		try {
			PreparedStatement ptmt = conn.prepareStatement("select content from grammerguideline where grammarguidelineid="+id);
			ResultSet rs = ptmt.executeQuery();
			while(rs.next()){
				
				content = rs.getString("content");
				
			
				
			
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return content;
	}
	
	public static List<Examinationquestion> Exportcorrectanswer(Connection conn, int examinationid){
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
	
	public static int countrowquestion(Connection conn, int examinationid){
		int count = 0;
		try {
			PreparedStatement ptmt = conn.prepareStatement("select count(*) from examinationquestion where examinationid ="+examinationid);
			ResultSet rs = ptmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return count;
	}
	
	public static void insertResult(Connection conn, result result) {
		PreparedStatement ptmt = null;
		String sql = "insert into result(correctanswernum,incorrectanswernum,time,examinationid,memberid) values(?,?,?,?,?)";
		try {
			ptmt = conn.prepareStatement(sql);
			int correctanswernum = result.getCorrectanswernum();
			int incorrectanswernum = result.getIncorrectanswernum();
			String time = result.getTime();
			int examinationid = result.getExaminationid();
			int memberid = result.getMemberid();
			
			ptmt.setInt(1, correctanswernum);
			ptmt.setInt(2, incorrectanswernum);
			ptmt.setString(3, time);
			ptmt.setInt(4,examinationid);
			ptmt.setInt(5, memberid);
			ptmt.executeUpdate();
			ptmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String correctanswer(Connection conn, int examinationid, int num) {

		String correctanswer = "";
		String sql = "SELECT correctanswer FROM examinationquestion where examinationid ="+examinationid+" and num ="+num;
		
			PreparedStatement ptmt;
		
				try {
					ptmt = conn.prepareStatement(sql);
					ResultSet rs = ptmt.executeQuery();
					
					
					while(rs.next()) {
						correctanswer =	rs.getString(1);
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
return correctanswer;
}
	}