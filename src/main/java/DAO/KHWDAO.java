package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import VO.ClassNoteVO;

public class KHWDAO {
	// 기본생성자 1~3

	// 1. 환경변수 선언
	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String url = "jdbc:oracle:thin:@192.168.0.26:1521:orcl";
	String user = "scott";
	String password = "tiger";
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	StringBuffer sb = new StringBuffer();

	public KHWDAO() {

		// 2. JDBC 드라이버
		try {
			Class.forName(driver);

			// 3. 연결
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("conn : " + conn);

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------

	public ArrayList<ClassNoteVO> teacherSelectAll() {

		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		// 4. SQL문
		sb.setLength(0); // 초기화
		sb.append(
				"SELECT TEACHER_NO, TEACHER_ID, TEACHER_PW, TEACHER_NAME, TEACHER_PHONE, TEACHER_EMAIL, TEACHER_PHOTO, TEACHER_HIREDATE, TEACHER_ADDRESS, TEACHER_SAL, TEACHER_SUBJECT, TEACHER_WORKTYPE, TEACHER_BIRTH, TEACHER_GENDER ");
		sb.append("FROM TEACHER");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {
				ClassNoteVO vo = new ClassNoteVO();

				vo.setTeacherNo(rs.getInt("TEACHER_NO"));
				vo.setTeacherId(rs.getString("TEACHER_ID"));
				vo.setTeacherPw(rs.getString("TEACHER_PW"));
				vo.setTeacherName(rs.getString("TEACHER_NAME"));
				vo.setTeacherPhone(rs.getString("TEACHER_PHONE"));
				vo.setTeacherEmail(rs.getString("TEACHER_EMAIL"));
				vo.setTeacherPhoto(rs.getString("TEACHER_PHOTO"));
				vo.setTeacherHiredate(rs.getString("TEACHER_HIREDATE"));
				vo.setTeacherAddress(rs.getString("TEACHER_ADDRESS"));
				vo.setTeacherSal(rs.getInt("TEACHER_SAL"));
				vo.setTeacherSubject(rs.getString("TEACHER_SUBJECT"));
				vo.setTeacherWorktype(rs.getString("TEACHER_WORKTYPE"));
				vo.setTeacherBirth(rs.getString("TEACHER_BIRTH"));
				vo.setTeacherGender(rs.getBoolean("TEACHER_GENDER"));

				list.add(vo);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// --------------------------------------------------------------------

	public ClassNoteVO teacherGetOne(int teacherNo) {

		// vo 초기화
		ClassNoteVO vo = new ClassNoteVO();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append(
				"SELECT TEACHER_NO, TEACHER_ID, TEACHER_PW, TEACHER_NAME, TEACHER_PHONE, TEACHER_EMAIL, TEACHER_PHOTO, TEACHER_HIREDATE, TEACHER_ADDRESS, TEACHER_SAL, TEACHER_SUBJECT, TEACHER_WORKTYPE, TEACHER_BIRTH, TEACHER_GENDER ");
		sb.append("FROM TEACHER ");
		sb.append("WHERE TEACHER_NO = ? ");

		try {
			// 5. SQL 문장 객체
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, teacherNo);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드 별 로직 처리
			while (rs.next()) {
				vo = new ClassNoteVO();

				vo.setTeacherNo(teacherNo);
				vo.setTeacherId(rs.getString("TEACHER_ID"));
				vo.setTeacherPw(rs.getString("TEACHER_PW"));
				vo.setTeacherName(rs.getString("TEACHER_NAME"));
				vo.setTeacherPhone(rs.getString("TEACHER_PHONE"));
				vo.setTeacherEmail(rs.getString("TEACHER_EMAIL"));
				vo.setTeacherPhoto(rs.getString("TEACHER_PHOTO"));
				vo.setTeacherHiredate(rs.getString("TEACHER_HIREDATE"));
				vo.setTeacherAddress(rs.getString("TEACHER_ADDRESS"));
				vo.setTeacherSal(rs.getInt("TEACHER_SAL"));
				vo.setTeacherSubject(rs.getString("TEACHER_SUBJECT"));
				vo.setTeacherWorktype(rs.getString("TEACHER_WORKTYPE"));
				vo.setTeacherBirth(rs.getString("TEACHER_BIRTH"));
				vo.setTeacherGender(rs.getBoolean("TEACHER_GENDER"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;

	}

	// ----------------------------------------------------------------------------------

	public void addOne(ClassNoteVO vo) {

		// 4. SQL문
		sb.setLength(0);
		sb.append(
				"Insert into TEACHER (TEACHER_NO, TEACHER_ID, TEACHER_PW, TEACHER_NAME, TEACHER_PHONE, TEACHER_EMAIL, TEACHER_PHOTO, TEACHER_HIREDATE, TEACHER_ADDRESS, TEACHER_SAL, TEACHER_SUBJECT, TEACHER_WORKTYPE, TEACHER_BIRTH, TEACHER_GENDER) ");
		sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getTeacherNo());
			pstmt.setString(2, vo.getTeacherId());
			pstmt.setString(3, vo.getTeacherPw());
			pstmt.setString(4, vo.getTeacherName());
			pstmt.setString(5, vo.getTeacherPhone());
			pstmt.setString(6, vo.getTeacherEmail());
			pstmt.setString(7, vo.getTeacherPhoto());
			pstmt.setString(8, vo.getTeacherHiredate());
			pstmt.setString(9, vo.getTeacherAddress());
			pstmt.setInt(10, vo.getTeacherSal());
			pstmt.setString(11, vo.getTeacherSubject());
			pstmt.setString(12, vo.getTeacherWorktype());
			pstmt.setString(13, vo.getTeacherBirth());
			pstmt.setBoolean(14, vo.isTeacherGender());

			// 6. 실행
			int result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("데이터 삽입 성공!");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------------

	public void updateOne(ClassNoteVO vo) {

		// 4. SQL문 작성
		sb.setLength(0); // 초기화
		sb.append("update teacher ");
		sb.append(
				"set TEACHER_ID = ?, TEACHER_PW = ?, TEACHER_NAME = ?, TEACHER_PHONE = ?, TEACHER_EMAIL = ?, TEACHER_PHOTO = ?, TEACHER_HIREDATE = ?,  TEACHER_ADDRESS = ?, TEACHER_SAL = ?, TEACHER_SUBJECT = ?, TEACHER_WORKTYPE = ?, TEACHER_BIRTH = ?, TEACHER_GENDER = ? ");
		sb.append("where TEACHER_NO = ? ");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getTeacherId());
			pstmt.setString(2, vo.getTeacherPw());
			pstmt.setString(3, vo.getTeacherName());
			pstmt.setString(4, vo.getTeacherPhone());
			pstmt.setString(5, vo.getTeacherEmail());
			pstmt.setString(6, vo.getTeacherPhoto());
			pstmt.setString(7, vo.getTeacherHiredate());
			pstmt.setString(8, vo.getTeacherAddress());
			pstmt.setInt(9, vo.getTeacherSal());
			pstmt.setString(10, vo.getTeacherSubject());
			pstmt.setString(11, vo.getTeacherWorktype());
			pstmt.setString(12, vo.getTeacherBirth());
			pstmt.setBoolean(13, vo.isTeacherGender());
			pstmt.setInt(14, vo.getTeacherNo());

			// 6. 실행
			int result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("데이터 수정 성공!");
			}

			// 7. 레코드 별 로직 처리
			while (rs.next()) {

				vo.setTeacherNo(rs.getInt("TEACHER_NO"));
				vo.setTeacherId(rs.getString("TEACHER_ID"));
				vo.setTeacherPw(rs.getString("TEACHER_PW"));
				vo.setTeacherName(rs.getString("TEACHER_NAME"));
				vo.setTeacherPhone(rs.getString("TEACHER_PHONE"));
				vo.setTeacherEmail(rs.getString("TEACHER_EMAIL"));
				vo.setTeacherPhoto(rs.getString("TEACHER_PHOTO"));
				vo.setTeacherHiredate(rs.getString("TEACHER_HIREDATE"));
				vo.setTeacherAddress(rs.getString("TEACHER_ADDRESS"));
				vo.setTeacherSal(rs.getInt("TEACHER_SAL"));
				vo.setTeacherSubject(rs.getString("TEACHER_SUBJECT"));
				vo.setTeacherWorktype(rs.getString("TEACHER_WORKTYPE"));
				vo.setTeacherBirth(rs.getString("TEACHER_BIRTH"));
				vo.setTeacherGender(rs.getBoolean("TEACHER_GENDER"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// -----------------------------------------------------------------------

	// 수강 기간을 특정하여 조회하기 위한 메서드
	public ArrayList<ClassNoteVO> selectAtoB(String startDate, String endDate) {

		return null;
	}

	// -----------------------------------------------------------------------

	// 사람을 특정하여 조회하기 위한 메서드
	public ArrayList<ClassNoteVO> searchTeacher(String teacherName) {

		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = new ClassNoteVO();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append(
				"SELECT TEACHER_NO, TEACHER_ID, TEACHER_PW, TEACHER_NAME, TEACHER_PHONE, TEACHER_EMAIL, TEACHER_PHOTO, TEACHER_HIREDATE, TEACHER_ADDRESS, TEACHER_SAL, TEACHER_SUBJECT, TEACHER_WORKTYPE, TEACHER_BIRTH, TEACHER_GENDER ");
		sb.append("FROM TEACHER ");
		sb.append("WHERE TEACHER_NAME = ? ");

		try {
			// 5. SQL 문장 객체
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, teacherName);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드 별 로직 처리
			while (rs.next()) {
				vo = new ClassNoteVO();

				vo.setTeacherNo(rs.getInt("TEACHER_NO"));
				vo.setTeacherId(rs.getString("TEACHER_ID"));
				vo.setTeacherPw(rs.getString("TEACHER_PW"));
				vo.setTeacherName(teacherName);
				vo.setTeacherPhone(rs.getString("TEACHER_PHONE"));
				vo.setTeacherEmail(rs.getString("TEACHER_EMAIL"));
				vo.setTeacherPhoto(rs.getString("TEACHER_PHOTO"));
				vo.setTeacherHiredate(rs.getString("TEACHER_HIREDATE"));
				vo.setTeacherAddress(rs.getString("TEACHER_ADDRESS"));
				vo.setTeacherSal(rs.getInt("TEACHER_SAL"));
				vo.setTeacherSubject(rs.getString("TEACHER_SUBJECT"));
				vo.setTeacherWorktype(rs.getString("TEACHER_WORKTYPE"));
				vo.setTeacherBirth(rs.getString("TEACHER_BIRTH"));
				vo.setTeacherGender(rs.getBoolean("TEACHER_GENDER"));

				list.add(vo);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public void deleteTeacher(int teacherNo) {

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("DELETE FROM TEACHER ");
		sb.append("WHERE TEACHER_NO = ? ");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, teacherNo);

			// 6. 실행
			int result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("데이터 삭제 성공!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	------------------------------------------------------------------------------

	// 회계 프로그램 월별 조회하기

	public ArrayList<ClassNoteVO> selectAllAccounting() {

		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("select * ");
		sb.append("FROM CLASS_REGISTER ");
		sb.append("INNER JOIN STUDENT ON CLASS_REGISTER.STUDENT_NO = STUDENT.STUDENT_NO ");
		sb.append("INNER JOIN LECTURE ON CLASS_REGISTER.LECTURE_NO = LECTURE.LECTURE_NO");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {
				ClassNoteVO vo = new ClassNoteVO();
				
				vo.setClass_registerNo(rs.getInt("CLASS_REGISTER_NO"));
				vo.setPay(rs.getBoolean("ISPAY"));
				vo.setPayType(rs.getString("PAY_TYPE"));
				vo.setStudentNo(rs.getInt("STUDENT_NO"));
				vo.setTeacherNo(rs.getInt("TEACHER_NO"));
				vo.setLectureNo(rs.getInt("LECTURE_NO"));
				vo.setStudentName(rs.getString("STUDENT_NAME"));
				vo.setStudentGrade(rs.getInt("STUDENT_GRADE"));
				vo.setStudentPhone(rs.getString("STUDENT_PHONE"));
				vo.setStudentRegistDate(rs.getString("STUDENT_REGIST_DATE"));
				vo.setStudentParentsName(rs.getString("STUDENT_PARENTS_NAME"));
				vo.setStudentParentsPhone(rs.getString("STUDENT_PARENTS_PHONE"));
				vo.setStudentDueDate(rs.getString("STUDENT_DUE_DATE"));
				vo.setStudentPhoto(rs.getString("STUDENT_PHOTO"));
				vo.setStudentGender(rs.getBoolean("STUDENT_GENDER"));
				vo.setStudentBirth(rs.getString("STUDENT_BIRTH"));
				vo.setStudentAddrs(rs.getString("STUDENT_ADDRS"));
				vo.setStudentEmail(rs.getString("STUDENT_EMAIL"));
				vo.setStudentSchoolName(rs.getString("STUDENT_SCHOOL_NAME"));
				vo.setStudentStatus(rs.getBoolean("STUDENT_STATUS"));
				vo.setLectureName(rs.getString("LECTURE_NAME"));
				vo.setLectureClass(rs.getString("LECTURE_CLASS"));
				vo.setLectureStartDate(rs.getString("LECTURE_START_DATE"));
				vo.setLectureEndDate(rs.getString("LECTURE_END_DATE"));
				vo.setLectureTuition(rs.getInt("LECTURE_TUITION"));

				list.add(vo);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
