package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import VO.CwkVO;

public class CwkDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";		//서버주소
	String user = "scott";										//서버아이디
	String password = "tiger";									//서버비번
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	StringBuffer sb = new StringBuffer();

	public CwkDAO() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("db 연결 실패");
			e.printStackTrace();
		}
	}
	
	public ArrayList<CwkVO> noteStudentSelectAll(String lectureName){
		ArrayList<CwkVO> list = new ArrayList<>();
		CwkVO vo = null;
		
		sb.setLength(0);
		sb.append("select lecture_name, lecture_class, student_name "
				+ "from (select * "
				+ "from student s, teacher t, lecture l, class_register c, class_note n "
				+ "where n.teacher_no = t.teacher_no and c.teacher_no = t.teacher_no and c.lecture_no = l.lecture_no "
				+ "and c.student_no = s.student_no)"
				+ "where lecture_name = ? ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, lectureName);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new CwkVO();
				vo.setLectureName(rs.getString("lecture_name"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentName(rs.getString("student_name"));
				
				list.add(vo);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<CwkVO> noteStudentSelectAll(String lectureName, String lectureClass){
		ArrayList<CwkVO> list = new ArrayList<>();
		CwkVO vo = null;
		
		sb.setLength(0);
		sb.append("select lecture_name, lecture_class, student_name "
				+ "from (select * "
				+ "from student s, teacher t, lecture l, class_register c, class_note n "
				+ "where n.teacher_no = t.teacher_no and c.teacher_no = t.teacher_no and c.lecture_no = l.lecture_no "
				+ "and c.student_no = s.student_no)"
				+ "where lecture_name = ? and where lecture_class = ? ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, lectureName);
			pstmt.setString(2, lectureClass);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new CwkVO();
				vo.setLectureName(rs.getString("lecture_name"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentName(rs.getString("student_name"));
				
				list.add(vo);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
//	강의 목록 출력
//	------------------------------------------------------------
	public ArrayList<CwkVO> lectureSelectAll(){
		ArrayList<CwkVO> list = new ArrayList<>();
		CwkVO vo = null;
		
		sb.setLength(0);
		sb.append("select * from lecture ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				vo = new CwkVO();
				vo.setLectureNo(rs.getInt("lecture_no"));
				vo.setLectureName(rs.getString("lecture_name"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setLectureStartDate(rs.getString("lecture_start_date"));
				vo.setLectureEndDate(rs.getString("lecture_end_date"));
				vo.setLectureTuition(rs.getInt("lecture_tuition"));
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
//	학생 목록 출력
//	----------------------------------------------------------------------
	public ArrayList<CwkVO> studentSelectAll(){
		ArrayList<CwkVO> list = new ArrayList<>();
		CwkVO vo = null;
		
		sb.setLength(0);
		sb.append("select * from student ");
		
		try {
			
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				StringBuffer stdPhoto = new StringBuffer();
				StringBuffer stdAddrs = new StringBuffer();
				StringBuffer stdEmail = new StringBuffer();
				vo = new CwkVO();
				
				vo.setStudentNo(rs.getInt("STUDENT_NO"));
				vo.setStudentName(rs.getString("STUDENT_NAME"));
				vo.setStudentGrade(rs.getInt("STUDENT_GRADE"));
				vo.setStudentPhone(rs.getString("STUDENT_PHONE"));
				vo.setStudentRegistDate(rs.getString("STUDENT_REGIST_DATE"));
				vo.setStudentParentsName(rs.getString("STUDENT_PARENTS_NAME"));
				vo.setStudentParentsPhone(rs.getString("STUDENT_PARENTS_PHONE"));
				vo.setStudentDueDate(rs.getString("STUDENT_DUE_DATE"));
				
				stdPhoto.append(rs.getString("STUDENT_PHOTO"));
				vo.setStudentPhoto(stdPhoto.toString());
				vo.setStudentGender(rs.getBoolean("STUDENT_GENDER"));
				vo.setStudentBirth(rs.getString("STUDENT_BIRTH"));
				
				stdAddrs.append(rs.getString("STUDENT_ADDRS"));
				vo.setStudentAddrs(stdAddrs.toString());
				
				stdEmail.append(rs.getString("STUDENT_EMAIL"));
				vo.setStudentEmail(stdEmail.toString());
				vo.setStudentSchoolName(rs.getString("STUDENT_SCHOOL_NAME"));
				vo.setStudentStatus(rs.getBoolean("STUDENT_STATUS"));
				
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<CwkVO> studentSelectAll(String stdName){
		ArrayList<CwkVO> list = new ArrayList<>();
		CwkVO vo = null;
		
		sb.setLength(0);
		sb.append("select * from student where student_name = ? ");
		
		try {
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, stdName);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				StringBuffer stdPhoto = new StringBuffer();
				StringBuffer stdAddrs = new StringBuffer();
				StringBuffer stdEmail = new StringBuffer();
				vo = new CwkVO();
				
				vo.setStudentNo(rs.getInt("STUDENT_NO"));
				vo.setStudentName(rs.getString("STUDENT_NAME"));
				vo.setStudentGrade(rs.getInt("STUDENT_GRADE"));
				vo.setStudentPhone(rs.getString("STUDENT_PHONE"));
				vo.setStudentRegistDate(rs.getString("STUDENT_REGIST_DATE"));
				vo.setStudentParentsName(rs.getString("STUDENT_PARENTS_NAME"));
				vo.setStudentParentsPhone(rs.getString("STUDENT_PARENTS_PHONE"));
				vo.setStudentDueDate(rs.getString("STUDENT_DUE_DATE"));
				
				stdPhoto.append(rs.getString("STUDENT_PHOTO"));
				vo.setStudentPhoto(stdPhoto.toString());
				vo.setStudentGender(rs.getBoolean("STUDENT_GENDER"));
				vo.setStudentBirth(rs.getString("STUDENT_BIRTH"));
				
				stdAddrs.append(rs.getString("STUDENT_ADDRS"));
				vo.setStudentAddrs(stdAddrs.toString());
				
				stdEmail.append(rs.getString("STUDENT_EMAIL"));
				vo.setStudentEmail(stdEmail.toString());
				vo.setStudentSchoolName(rs.getString("STUDENT_SCHOOL_NAME"));
				vo.setStudentStatus(rs.getBoolean("STUDENT_STATUS"));
				
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
