package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import BEAN.Member;

public class RegisterDAO {
	public static boolean insertAccount(Connection conn, Member mb,HttpServletRequest request){
		PreparedStatement ptmt = null;
		String sql = "insert into member(membername,memberpass,categorymemberid,fullname) values(?,?,?,?)";
		try {
			ptmt = conn.prepareStatement(sql);
			
			String membername = mb.getMembername();
			String memberpass = mb.getMemberpass();
			String fullname = mb.getFullname();
			int cate = 1;
			ptmt.setString(1, membername);
			ptmt.setString(2, memberpass);
			ptmt.setInt(3, cate);
			ptmt.setString(4, fullname);
			
			int kt = ptmt.executeUpdate();
			
			if(kt != 0) return true;
			
			ptmt.close();
		
		} catch (SQLException e) {
			request.setAttribute("msgregister", e.getMessage());
		}
		
		return false;
	}
}
