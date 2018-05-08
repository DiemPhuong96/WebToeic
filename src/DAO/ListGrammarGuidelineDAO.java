package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BEAN.GrammarGuideline;

public class ListGrammarGuidelineDAO {
	public static List<GrammarGuideline> DisplayGrammar(int start, int count, Connection conn){
		List<GrammarGuideline> list = new ArrayList<GrammarGuideline>();
	
		try {
			PreparedStatement ptmt = conn.prepareStatement("select* from grammerguideline limit "+(start-1)+", "+count);
			ResultSet rs = ptmt.executeQuery();
			while(rs.next()){
				GrammarGuideline gg = new GrammarGuideline();
				int grammarguidelineid = rs.getInt("grammarguidelineid");
				String grammarname = rs.getString("grammarname");
				String 	grammarimage = rs.getString("grammarimage");
				
				gg.setGrammarguidelineid(grammarguidelineid);
				gg.setGrammarimage(grammarimage);

				gg.setGrammarname(grammarname);
				list.add(gg);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return list;
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
	
	public static String DisplayContentGrammar(Connection conn, int id){
		
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

}
