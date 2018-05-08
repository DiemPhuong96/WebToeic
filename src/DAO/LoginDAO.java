package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import BEAN.Member;

public class LoginDAO {
	public static String AuthenicationMember(Connection conn, Member mb,HttpServletRequest request){
		
		String test = null;
		PreparedStatement ptmt = null;
		String sql = "select membername, memberpass from member";
		try {
			ptmt = conn.prepareStatement(sql);
			
			ResultSet rs = ptmt.executeQuery();
			
			while(rs.next()) {
				String membername = rs.getString("membername");
				String memberpass = rs.getString("memberpass");
				
					if((mb.getMembername().equals(membername)) && (mb.getMemberpass().equals(memberpass))) {
						test = "success";
						return test;
					}
					else {
						test = "false";
					}
				
			}
			
			ptmt.close();
			rs.close();
		
		} catch (SQLException e) {
			request.setAttribute("msglogin", e.getMessage());
		}
		
		return test;
	}
	
	
public static int AuthorizationMember(Connection conn, Member mb,HttpServletRequest request){
		int categorymemberid = 0;
		PreparedStatement ptmt = null;
		String sql = "select categorymemberid from member where membername= '"+mb.getMembername()+"' AND memberpass = '"+mb.getMemberpass()+"'";
		try {
			ptmt = conn.prepareStatement(sql);
			
			ResultSet rs = ptmt.executeQuery();
			
			while(rs.next()) {
				categorymemberid = rs.getInt("categorymemberid");
				
					
			}
			
			ptmt.close();
			rs.close();
		
		} catch (SQLException e) {
			request.setAttribute("msglogin", e.getMessage());
		}
		
		return categorymemberid;
	}

public static String ExportMemberName(Connection conn, Member mb, HttpServletRequest request){
	
	PreparedStatement ptmt = null;
	String name = null;
	String sql = "select fullname from member where membername= '"+mb.getMembername()+"' AND memberpass = '"+mb.getMemberpass()+"'";
	try {
		ptmt = conn.prepareStatement(sql);
		
		ResultSet rs = ptmt.executeQuery();
		
		while(rs.next()) {
			name = rs.getString("fullname");
		
		}
		
		ptmt.close();
		rs.close();
	
	} catch (SQLException e) {
		request.setAttribute("msglogin", e.getMessage());
	}
	
	return name;
}
public static int ExportMemberid(Connection conn, Member mb, HttpServletRequest request){
	
	PreparedStatement ptmt = null;
	int memberid = 0;
	String sql = "select memberid from member where membername= '"+mb.getMembername()+"' AND memberpass = '"+mb.getMemberpass()+"'";
	try {
		ptmt = conn.prepareStatement(sql);
		
		ResultSet rs = ptmt.executeQuery();
		
		while(rs.next()) {
			memberid = rs.getInt("memberid");
		
		}
		
		ptmt.close();
		rs.close();
	
	} catch (SQLException e) {
		request.setAttribute("msglogin", e.getMessage());
	}
	
	return memberid;
}
}
