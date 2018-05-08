package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BEAN.CmtGrammar;

public class CommentCmtDAO {
	
	public static void InsertcommentGrammar(Connection conn, CmtGrammar cmtg)
	{
		PreparedStatement ptmt = null;
		
		String sql = "insert into cmtgrammar(cmtgrammercontent,grammarguidelineid,memberid) values (?,?,?)";
		
		try 
		{
			ptmt = conn.prepareStatement(sql);
			
			String cmtgrammercontent = cmtg.getCmtgrammercontent();
			int grammarguidelineid = cmtg.getCmtgrammarid();
			int memberid = cmtg.getMemberid();
			ptmt.setString(1,cmtgrammercontent );
			ptmt.setInt(2,grammarguidelineid );
			ptmt.setInt(3, memberid);
			ptmt.executeUpdate();
			
			
			ptmt.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

	
	public static List<CmtGrammar> DisplaycommentGrammar (Connection conn, int grammarguidelineid)
	{
		List<CmtGrammar> list = new ArrayList<CmtGrammar>();
		
		String sql = "select * from cmtgrammar where grammarguidelineid="+grammarguidelineid;
		try 
		{
			PreparedStatement ptmt = conn.prepareStatement(sql);
			
			ResultSet rs = ptmt.executeQuery();
			
			
			while (rs.next())
			{
				CmtGrammar cmt = new CmtGrammar();
				String content = rs.getString("cmtgrammercontent");
				int memberid = rs.getInt("memberid");
				int commentid = rs.getInt("cmtgrammarid");
				String fullname = CommentCmtDAO.getfullname(conn, memberid);
				cmt.setFullname(fullname);
				cmt.setCmtgrammercontent(content);
				cmt.setCmtgrammarid(commentid);
				list.add(cmt);
			}
			
			
			
		} 
		catch (SQLException e) 
		{


			e.printStackTrace();
		}
		
		return list;
	}
	
	public static int countrowcmt(Connection conn, int grammarguidelineid){
		int count = 0;
		try {
			PreparedStatement ptmt = conn.prepareStatement("select count(*) from cmtgrammar where grammarguidelineid="+grammarguidelineid);
			ResultSet rs = ptmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return count;
	}
	
	

	public static String getfullname(Connection conn, int memberid){
		String fullname = null;
		try {
			PreparedStatement ptmt = conn.prepareStatement("select fullname from member where memberid ="+memberid);
			ResultSet rs = ptmt.executeQuery();
			rs.next();
			fullname = rs.getString("fullname");
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return fullname;
	}
	
	
	

}
