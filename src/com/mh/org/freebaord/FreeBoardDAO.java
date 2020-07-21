package com.mh.org.freebaord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mg.org.util.DataSource;

public class FreeBoardDAO {

	public List<FreeBoardDTO> selectALL(){
		List<FreeBoardDTO> list = new ArrayList<FreeBoardDTO>();
		
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DataSource.getConnection();
			pstmt = conn.prepareStatement("select top 10 * from freeboard order by reg_date desc");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FreeBoardDTO dto = new FreeBoardDTO(
									rs.getInt("idx"),
									rs.getString("title"),
									rs.getString("content"),
									rs.getString("reg_date"),
									rs.getString("mod_date")
									);
				list.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DataSource.doClose(rs, pstmt, conn);
		}
		return list;
	}

	public void insertFreeboard(FreeBoardDTO dto) throws Exception{
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DataSource.getConnection();
		
		int idx = 0;
		
		pstmt = conn.prepareStatement("exec PRO_SEQS 'FREE_BOARD_SEQ'");
		pstmt.setEscapeProcessing(true);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			idx = rs.getInt("value");
		}
		
		pstmt = conn.prepareStatement(
				"insert into freeboard " + 
				"(idx,title,content,reg_date,mod_date) " + 
				"values " + 
				"(?,?,?,getdate(),getdate()) ");
		
		pstmt.setInt(1, idx);
		pstmt.setString(2, dto.getTitle());
		pstmt.setString(3, dto.getContent());
		
		pstmt.executeUpdate();
		
		DataSource.doClose(null, pstmt, conn);
	}

	public void deleteAll(String[] idx) throws Exception{
		String idxs = "";
		for(int i =0; i<idx.length ; i++) {
			if( (idx.length - 1) == i )
				idxs += idx[i];
			else
				idxs += idx[i]+",";
		}
		Connection conn= null;
		PreparedStatement pstmt = null;
		
		conn = DataSource.getConnection();
		
		pstmt = conn.prepareStatement("delete from freeboard " + 
										"where idx in ( "+idxs+" ) " );
		
		pstmt.executeUpdate();
		
		DataSource.doClose(null, pstmt, conn);
	}

	public FreeBoardDTO selectOne(String idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FreeBoardDTO dto = null;
		try {
			conn = DataSource.getConnection();
			pstmt = conn.prepareStatement("select * from freeboard where idx = ?");
			pstmt.setInt(1, Integer.parseInt(idx));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new FreeBoardDTO(
								rs.getInt("idx"), 
								rs.getString("title"), 
								rs.getString("content"), 
								rs.getString("reg_date"), 
								rs.getString("mod_date")
								);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DataSource.doClose(rs, pstmt, conn);
		}
		return dto;
	}
	
	public void updateFreeBoard(FreeBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DataSource.getConnection();
			pstmt = conn.prepareStatement("update freeboard set"
									+ " title =?, content=?, "
									+ " mod_date=getdate()"
									+ " where idx = ?");
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getIdx());
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DataSource.doClose(null, pstmt, conn);
		}
	}
}




















