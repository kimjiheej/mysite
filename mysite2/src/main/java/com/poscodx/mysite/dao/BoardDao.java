package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.GuestbookVo;

public class BoardDao {

	// 커넥션 만들기 
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.0.11/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	 
	// 게시글 추가  
	public void insert(BoardVo vo) {
	    try (
	        Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO board (title,contents,hit,reg_date,g_no,o_no,depth,user_no) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)");
	    		PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
	    ) {
	        pstmt.setString(1, vo.getTitle());
	        pstmt.setString(2, vo.getContents());
	        pstmt.setInt(3, 0);
	        pstmt.setString(4, vo.getReg_date());
	        pstmt.setInt(5, vo.getG_no());
	        pstmt.setInt(6, vo.getO_no());
	        pstmt.setInt(7, vo.getDepth());
	        pstmt.setLong(8, vo.getUser_no());
	        
	        pstmt.executeUpdate();
	        ResultSet rs = pstmt2.executeQuery();
	    	vo.setNo(rs.next() ?  rs.getInt(1) : null);
			rs.close();
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	}
	// 게시글 그룹. order 업데이트 해주기 
	public void Update(int g_no, int o_no) {
	    try (
	        Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET o_no = o_no + 1 WHERE g_no = ? AND o_no >= ?");
	    ) {
	        pstmt.setInt(1, g_no);
	        pstmt.setInt(2, o_no);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	}
	// boardNo 로 board 삭제해주기  
		public void deleteBoard(String no) {
		    try (
		        Connection conn = getConnection();
		        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM board WHERE no=?");
		    ) {
		        pstmt.setInt(1, Integer.parseInt(no));
		        pstmt.executeUpdate();
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }
		}
	// hit update 해주기 
	public void modifyHit(String no) {
	    try (
	        Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET hit = hit + 1 WHERE no=?");
	    ) {
	        pstmt.setInt(1, Integer.parseInt(no));
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	}
	
	 // pageNo 와 item 별 페이지로 페이지 찾아주기 
	 public List<BoardVo> findPage(int currentPage, int itemsPerPage) {
	        List<BoardVo> result = new ArrayList<>();
	        int offset = (currentPage - 1) * itemsPerPage;
	        try (
	            Connection conn = getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(
	            		"SELECT a.no, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name\r\n"
	            		+ "FROM board a, user b\r\n"
	            		+ "WHERE a.user_no = b.no\r\n"
	            		+ "ORDER BY g_no DESC, o_no ASC\r\n"
	            		+ "LIMIT ?, ?")
	     ) {
	            pstmt.setInt(1, offset);
	            pstmt.setInt(2, itemsPerPage);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                    BoardVo vo = new BoardVo();
	                    vo.setNo(rs.getInt("no"));
	                    vo.setTitle(rs.getString("title"));
	                    vo.setContents(rs.getString("contents"));
	                    vo.setHit(rs.getInt("hit"));
	                    vo.setReg_date(rs.getString("reg_date"));
	                    vo.setG_no(rs.getInt("g_no"));
	                    vo.setO_no(rs.getInt("o_no"));
	                    vo.setDepth(rs.getInt("depth"));
	                    vo.setUser_no(rs.getLong("user_no"));
	                    vo.setName(rs.getString("name"));
	                    result.add(vo);
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("error:" + e);
	        }
	        return result;
	    }
	    // board 의 totalCount 찾아주기 
	    public int getTotalBoard() {   
	        int totalCount = 0;

	        try (
	            Connection conn = getConnection();
	            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM board")
	        ) {
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    totalCount = rs.getInt(1);
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("error:" + e);
	        }

	        return totalCount;
	    }
	    // boardNo 로 board 수정해주기 
		public void modifyBoard(String no, String title, String contents) {
		    try (
		        Connection conn = getConnection();
		        PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET title=?, contents=?, reg_date=current_time() WHERE no=?");
		    ) {
		        pstmt.setString(1, title);
		        pstmt.setString(2, contents);
		        pstmt.setInt(3, Integer.parseInt(no));
		        pstmt.executeUpdate();
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }
		}
		   // No 로 BoardVo 찾아주기 
	public BoardVo getBoard(String no) {
	    BoardVo result = null;
	    try (
	        Connection conn = getConnection();
	        PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM board WHERE no = ?");
	        PreparedStatement pstmt2 = conn.prepareStatement("SELECT A.name FROM user A, board B WHERE A.no = B.user_no AND B.no = ?");
	    ) {
	        // 첫 번째 SQL 문 준비 및 실행
	        pstmt1.setString(1, no);
	        try (ResultSet rs1 = pstmt1.executeQuery()) {
	            // 첫 번째 결과 처리
	            if (rs1.next()) {
	                result = new BoardVo();
	                result.setNo(rs1.getInt("no"));
	                result.setTitle(rs1.getString("title"));
	                result.setContents(rs1.getString("contents"));
	                result.setHit(rs1.getInt("hit"));
	                result.setReg_date(rs1.getString("reg_date"));
	                result.setG_no(rs1.getInt("g_no"));
	                result.setO_no(rs1.getInt("o_no"));
	                result.setDepth(rs1.getInt("depth"));
	                result.setUser_no(rs1.getLong("user_no"));
	            }
	        }
	        // 두 번째 SQL 문 준비 및 실행
	        pstmt2.setString(1, no);
	        try (ResultSet rs2 = pstmt2.executeQuery()) {
	            // 두 번째 결과 처리
	            if (rs2.next() && result != null) {
	                result.setName(rs2.getString("name"));
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }

	    return result;
	}
	 // 다음 group 의 no 찾아주기 
	public int getNextNumber() {
	    int result = 1;
	    try (
	        Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("SELECT COALESCE(MAX(g_no), 0) + 1 AS nextGroupNo FROM board");
	        ResultSet rs = pstmt.executeQuery();
	    ) {
	        // 결과처리
	        if (rs.next()) {
	            result = rs.getInt("nextGroupNo");
	        }
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	    return result;
	}
	

}
