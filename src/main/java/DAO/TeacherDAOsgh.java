package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import VO.ClassNoteVOsgh;

//qwe


public class TeacherDAOsgh {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.26:1521:orcl";		//서버주소
	String user = "scott";										//서버아이디
	String password = "tiger";									//서버비번
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	StringBuffer sb = new StringBuffer();
	
	public TeacherDAOsgh() {
	
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
	
//----------------------------------------------------------------------------------
	
	// check.jsp 출결확인페이지 오늘날짜 출결 조회
	
	public ArrayList<ClassNoteVO> teacherSelectAll(String date){
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();
		ClassNoteVO vo = null;
		
		sb.setLength(0);
		sb.append( "select * from teacher_check where teacher_check_date = ? " );
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			
		System.out.println(rs.next());
		while ( rs.next() ) {
			vo = new ClassNoteVO();
			vo.setTeacherCheckNo(rs.getInt( "teacher_check_no" ) );
			vo.setTeacherCheckIn(rs.getString( "teacher_check_in" ) );
			vo.setTeacherCheckOut(rs.getString( "teacher_check_out" ) );
			vo.setTeacherWorkTime(rs.getString( "teacher_work_time" ) );
			vo.setTeacherCheckDate(rs.getString( "teacher_check_date" ) );
			vo.setTeacherNo(rs.getInt( "teacher_no" ) );
			list.add(vo);
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<ClassNoteVOsgh> teacherSelectAll(){
		ArrayList<ClassNoteVOsgh> list = new ArrayList<>();
		ClassNoteVOsgh vo = null;
		
		sb.setLength(0);
		sb.append("select * from teacher ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				vo = new ClassNoteVOsgh();
				vo.setTeacherNo(rs.getInt("teacher_no"));
				vo.setTeacherId(rs.getString("teacher_id"));
				vo.setTeacherPw(rs.getString("teacher_pw"));
				vo.setTeacherName(rs.getString("teacher_name"));
				vo.setTeacherAddress(rs.getString("teacher_address"));
				vo.setTeacherSal(rs.getInt("teacher_sal"));
				vo.setTeacherPhone(rs.getString("teacher_phone"));
				vo.setTeacherWorktype(rs.getString("teacher_work_type"));
				vo.setTeacherHiredate(rs.getString("teacher_hiredate"));
				
				if(rs.getString("teacher_gender") == "1")
					vo.setTeacherGender(false);
				else
					vo.setTeacherGender(true);
				
				list.add(vo);
			};
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<ClassNoteVOsgh> teacherSelectAll(String date1, String date2){
		ArrayList<ClassNoteVOsgh> list = new ArrayList<ClassNoteVOsgh>();
		ClassNoteVOsgh vo = null;
		
		sb.setLength(0);
		sb.append( "SELECT * FROM TEACHER_CHECK WHERE TO_CHAR(TEACHER_CHECK_DATE, 'YYYY-MM-DD') BETWEEN ? AND ? " );
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			rs = pstmt.executeQuery();
			
		System.out.println(rs.next());
		while ( rs.next() ) {
			vo = new ClassNoteVOsgh();
			vo.setTeacherCheckNo(rs.getInt( "teacher_check_no" ) );
			vo.setTeacherCheckIn(rs.getString( "teacher_check_in" ) );
			vo.setTeacherCheckOut(rs.getString( "teacher_check_out" ) );
			vo.setTeacherWorkTime(rs.getString( "teacher_work_time" ) );
			vo.setTeacherCheckDate(rs.getString( "teacher_check_date" ) );
			vo.setTeacherNo(rs.getInt( "teacher_no" ) );
			list.add(vo);
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public void addAllTeacher() {
		sb.setLength(0);
		sb.append( "INSERT INTO TEACHER_CHECK VALUES ( TEACHER_CHECK_NO_SEQ.nextval, null, null, null, 2, to_date(to_char(sysdate, 'YYYY-MM-dd'),'YYYY-MM-dd') )" );
		// 2 -> TEACHER_NO_SEQ.nextval 조인해서 수정예정
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void checkInTeacher(ClassNoteVOsgh vo) {
		sb.setLength(0);
		sb.append( "UPDATE TEACHER_CHECK " );
		sb.append( "SET TEACHER_CHECK_IN = SYSDATE ) " );
		sb.append( "WHERE TEACHER_NO IN= ( " );
		sb.append( "SELECT TEACHER_NO " );
		sb.append( "FROM TEACHER " );
		sb.append( "WHERE TEACHER_NAME = ? ) " );
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getTeacherName() );
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void checkOutTeacher(ClassNoteVOsgh vo) {
		sb.setLength(0);
		sb.append( "UPDATE TEACHER_CHECK " );
		sb.append( "SET TEACHER_CHECK_OUT = SYSDATE ) " );
		sb.append( "WHERE TEACHER_NO IN= ( " );
		sb.append( "SELECT TEACHER_NO " );
		sb.append( "FROM TEACHER " );
		sb.append( "WHERE TEACHER_NAME = ? ) " );
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getTeacherName() );
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void workTimeTeacher(ClassNoteVOsgh vo) {
		sb.setLength(0);
		sb.append( "UPDATE TEACHER_CHECK " );
		sb.append( "SET TEACHER_WORK_TIME =  ) " );
		sb.append( "WHERE TEACHER_CHECK_NO = ? ) " );
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getTeacherCheckNo() );
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
