package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import BEAN.GrammarGuideline;

public class SearchDAO {
	public static List<GrammarGuideline> Displayresult (HttpServletRequest request,Connection conn, String name1)
	//public static void Displayresult (HttpServletRequest request,Connection conn, String name1)
	{
		List<GrammarGuideline> list = new ArrayList<GrammarGuideline>();
		
		String sql = "select * from grammerguideline where grammarname like '%"+name1+"%'";
		try 
		{
			PreparedStatement ptmt = conn.prepareStatement(sql);
			
			ResultSet rs = ptmt.executeQuery();
			
			if (!rs.isBeforeFirst())
			{
				request.setAttribute("ketqua","No Result");
			}
			else 
			{
				while (rs.next())
				{
					GrammarGuideline gg = new GrammarGuideline();
					String name = rs.getString("grammarname");
					int id = rs.getInt("grammarguidelineid");
					String image = rs.getString("grammarimage");
					gg.setGrammarguidelineid(id);
					gg.setGrammarimage(image);
					gg.setGrammarname(name);
					list.add(gg);
					//request.setAttribute("listemp",list);
				}
			}
			
		} 
		catch (SQLException e) 
		{


			e.printStackTrace();
		}
		
		return list;
	}
}
