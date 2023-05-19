package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import VO.ClassNoteVO;



public class TeacherDAOptm {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String user = "scott";
	String password = "tiger";
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	StringBuffer sb = new StringBuffer();
	
	public TeacherDAOptm() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("conn : " + conn);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
	}
	
	public void addOne(ClassNoteVO vo) {
		
		sb.setLength(0);
		sb.append("INSERT INTO teacher ");
		sb.append("VALUES (TEACHER_TNO_SEQ.NEXTVAL, ?, ?, ?, ?, 0, ?, ?, 0, ?, 0, SYSDATE, ?, ?) ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			String g;
			if(vo.isTeacherGender())
				g = "1";
			else
				g = "0";
			
			pstmt.setString(1, vo.getTeacherId());
			pstmt.setString(2, vo.getTeacherPw());
			pstmt.setString(3, vo.getTeacherName());
			pstmt.setString(4, vo.getTeacherAddress());
			pstmt.setString(5, vo.getTeacherPhone());
			pstmt.setString(6, vo.getTeacherEmail());
			pstmt.setString(7, vo.getTeacherPhoto());
			pstmt.setString(8, vo.getTeacherBirth());
			pstmt.setString(9, g);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ClassNoteVO getTwo(String teacherId, String teacherPw) {
		sb.setLength(0);
		sb.append("SELECT * FROM teacher WHERE teacher_id = ? and teacher_pw = ? ");
		
		ClassNoteVO vo = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, teacherId);
			pstmt.setString(2, teacherPw);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int teacherNo = rs.getInt("teacher_no");
				String teacherName = rs.getString("teacher_name");
				String teacherAddress = rs.getString("teacher_address");
				int teacherSal = rs.getInt("teacher_sal");
				String teacherPhone = rs.getString("teacher_phone");
				String teacherEmail = rs.getString("teacher_email");
				String teacherSubject = rs.getString("teacher_subject");
				String teacherPhoto = rs.getString("teacher_photo");
				String teacherWorktype = rs.getString("teacher_worktype");
				String teacherHiredate = rs.getString("teacher_hiredate");
				String teacherBirth = rs.getString("teacher_birth");
				
				boolean g;
				if(rs.getString("teacher_gender") == "1")
					g = true;
				else
					g = false;
				
				vo = new ClassNoteVO(teacherNo, teacherId, teacherPw, teacherSal, teacherName, teacherAddress
						, teacherBirth, teacherBirth, teacherSal, teacherPhone, teacherEmail, teacherSubject, teacherPhoto
						, teacherWorktype, teacherHiredate, teacherBirth, g, teacherBirth, teacherBirth, teacherBirth, teacherBirth
						, teacherSal, g, teacherBirth, teacherSal, teacherBirth, teacherSal, teacherBirth, teacherBirth, teacherBirth
						, teacherBirth, teacherBirth, teacherBirth, g, teacherBirth, teacherBirth, teacherBirth, teacherBirth, g, teacherSal
						, teacherBirth, teacherBirth, teacherBirth, teacherBirth, teacherSal);
				
				System.out.println("dao:"+vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	public ClassNoteVO selectOne(int teacherNo) {
		sb.setLength(0);
		sb.append("SELECT * FROM teacher WHERE teacher_no = ? ");
		
		ClassNoteVO vo = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, teacherNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String teacherId = rs.getString("teacher_id");
				String teacherPw = rs.getString("teacher_pw");
				String teacherName = rs.getString("teacher_name");
				vo = new ClassNoteVO();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	public ClassNoteVO getOne(String teacherId) {
		sb.setLength(0);
		sb.append("SELECT * FROM teacher WHERE teacher_id = ? ");
		
		ClassNoteVO vo = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, teacherId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int teacherNo = rs.getInt("teacher_no");
				String teacherPw = rs.getString("teacher_pw");
				String teacherName = rs.getString("teacher_name");
				String teacherAddress = rs.getString("teacher_address");
				int teacherSal = rs.getInt("teacher_sal");
				String teacherPhone = rs.getString("teacher_phone");
				String teacherEmail = rs.getString("teacher_email");
				String teacherSubject = rs.getString("teacher_subject");
				String teacherPhoto = rs.getString("teacher_photo");
				String teacherWorktype = rs.getString("teacher_worktype");
				String teacherHiredate = rs.getString("teacher_hiredate");
				String teacherBirth = rs.getString("teacher_birth");
				String teacherGender = rs.getString("teacher_gender");
				
				vo = new ClassNoteVO();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
}
