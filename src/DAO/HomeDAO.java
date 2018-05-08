package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BEAN.slidebanner;


public class HomeDAO {
	public static List<slidebanner> DisplaySlideBanner(Connection conn){
		List<slidebanner> list = new ArrayList<slidebanner>();
		String sql = "select* from slidebanner";
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ResultSet rs = ptmt.executeQuery();
			while(rs.next()) {
				slidebanner sb = new slidebanner();
				sb.setSlidename(rs.getString("slidename"));
				sb.setSlidecontent(rs.getString("slidecontent"));
				sb.setSlideimage(rs.getString("slideimage"));
				list.add(sb);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
