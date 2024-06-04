package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestbookVo;



@Repository
public class GuestbookRepository {

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.0.201:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			System.out.println("연결 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 회원 정보 삽입 
	public void insert(GuestbookVo vo) {
	    try (
	        Connection conn = getConnection();
	        PreparedStatement pstmt1 = conn.prepareStatement("insert into guestbook(name,password,contents,reg_date) values(?, ?, ?, now())");
	    	PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
	    ) {
	        pstmt1.setString(1, vo.getName());
	        pstmt1.setString(2, vo.getPassword());
	        pstmt1.setString(3, vo.getContents());
	        pstmt1.executeUpdate();
	        ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ?  rs.getLong(1) : null);
			rs.close();
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	}
	
	
	public List<GuestbookVo> findAll() {
	    List<GuestbookVo> result = new ArrayList<>();
	    try (
	        Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("select no, name, password, contents, reg_date from guestbook order by no desc");
	        ResultSet rs = pstmt.executeQuery();
	    ) {
	        while(rs.next()) {
	            Long no = rs.getLong(1);
	            String name = rs.getString(2);
	            String password = rs.getString(3);
	            String contents = rs.getString(4);
	            // 데이터베이스에서 직접 시간 정보를 가져옴 (Timestamp 형식)
	            Timestamp reg_date = rs.getTimestamp(5);

	            GuestbookVo vo = new GuestbookVo();
	            
	            vo.setNo(no);
	            vo.setName(name);
	            vo.setPassword(password);
	            vo.setRegDate(reg_date);
	            vo.setContents(contents);
	            result.add(vo);
	        }
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }

	    return result;
	}
	
	// delete 
	public void delete(String no, String password) {
	 		try (
	 				Connection conn = getConnection();
	 				PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where no = ? and password = ?");
	 				) {
	 			Long num = Long.parseLong(no);
	 			pstmt.setLong(1, num);
	 			pstmt.setString(2, password);
	 		    pstmt.executeUpdate();
	 	} catch(SQLException e) {
	 		System.out.println("error: "+ e);
	 	}
	 	
	}
	
}