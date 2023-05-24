package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import VO.ClassNoteVO;

public class StudentDAO {

	// 기본생성자 (JDBC의 1-3단계)
	// 1. 환경변수
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.26:1521:orcl"; // CWK
//	String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // localhost
	String user = "scott";
	String password = "tiger";
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	StringBuffer sb = new StringBuffer();

	// 기본생성자

	public StudentDAO() {
		try {
			// 2. 드라이버 로딩
			Class.forName(driver);

			// 3. 연결
			conn = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩실패");
		} catch (SQLException e) {
			System.out.println("DB에 연결실패");
			e.printStackTrace();
		}
	} // 기본생성자 끝

	// Note-------------------------------------------------------------------------------------------------------------------------------

	// 학생 목록 출력
	// student-------------------------------------------------------------------------------------------------------------------------------
	public ArrayList<ClassNoteVO> studentSelectAll() {
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;

		sb.setLength(0);
		sb.append("select * from student ");

		try {

			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				StringBuffer stdPhoto = new StringBuffer();
				StringBuffer stdAddrs = new StringBuffer();
				StringBuffer stdEmail = new StringBuffer();
				vo = new ClassNoteVO();

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

	// 학생목록 JOIN
	// teacher 전부 출력하기
	public ArrayList<ClassNoteVO> studentSelectByAll() {

		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		// 4. SQL문
		sb.setLength(0); // 초기화
		sb.append(
				"SELECT S.STUDENT_NO, S.STUDENT_NAME, S.STUDENT_PHONE, S.STUDENT_PARENTS_PHONE, S.STUDENT_SCHOOL_NAME, L.LECTURE_CLASS, L.LECTURE_START_DATE, L.LECTURE_END_DATE ");
		sb.append("FROM CLASS_REGISTER CR ");
		sb.append("FULL OUTER JOIN STUDENT S ON CR.STUDENT_NO = S.STUDENT_NO ");
		sb.append("FULL OUTER JOIN LECTURE L ON CR.LECTURE_NO = L.LECTURE_NO ");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {
				ClassNoteVO vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("STUDENT_NO"));
				vo.setStudentName(rs.getString("STUDENT_NAME"));
				vo.setStudentPhone(rs.getString("STUDENT_PHONE"));
				vo.setStudentParentsPhone(rs.getString("STUDENT_PARENTS_PHONE"));
				vo.setStudentSchoolName(rs.getString("STUDENT_SCHOOL_NAME"));
				vo.setLectureClass(rs.getString("LECTURE_CLASS"));
				vo.setLectureStartDate(rs.getString("LECTURE_START_DATE"));
				vo.setLectureEndDate(rs.getString("LECTURE_END_DATE"));

				list.add(vo);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<ClassNoteVO> studentSelectAllByName(String stdName) {
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;

		sb.setLength(0);
		sb.append("select * from student where student_name = ? ");

		try {

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, stdName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				StringBuffer stdPhoto = new StringBuffer();
				StringBuffer stdAddrs = new StringBuffer();
				StringBuffer stdEmail = new StringBuffer();
				vo = new ClassNoteVO();

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

	// studentSearch-------------------------------------------------------------------------------------------------------------------------------
	// 메서드에서 (JDBC의 4-7단계)

	// <전체조회> - 입력값 없이 조회

	// student - class_register - lecture 조인

	public ArrayList<ClassNoteVO> studenSearchSelectAll() {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);
		// sb.append("SELECT * FROM student "); //조인안될때 예비로 쓴 쿼리문

		// 아니 디비에서는 되는데 대체 뭐가 문제야 ==> 음... 이름값을 받아오는 selectName() 에서 AND student_name=?
		// 이 쿼리문을 빼먹어서
		sb.append("SELECT * ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");

		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				// 객체 생성하면서 자동으로 기본생성자 생성
				vo = new ClassNoteVO();
				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));
				vo.setStudentEmail(rs.getString("student_email"));
				vo.setStudentBirth(rs.getString("student_birth"));
				vo.setStudentAddrs(rs.getString("student_addrs"));
				vo.setStudentPhoto(rs.getString("student_photo"));
				vo.setStudentStatus(rs.getBoolean("student_status"));

				list.add(vo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	} // selectAll() 끝

	

	// 학생 번호로 상세정보 검색
	public ArrayList<ClassNoteVO> studentSearchSelectAllByNo(int studentNo) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);

		sb.append(
				"SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, s.student_email, s.student_birth, s.student_addrs, ");
		sb.append("l.lecture_class, s.student_phone, s.student_regist_date, s.student_gender, s.student_photo, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s ");
		sb.append("JOIN class_register c ON s.student_no = c.student_no ");
		sb.append("JOIN lecture l ON c.lecture_no = l.lecture_no ");
		sb.append("WHERE s.student_no = ? ");

		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, studentNo); // bind 변수 값 주기

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(studentNo); // 번호
				vo.setStudentName(rs.getString("student_name")); // 이름
				vo.setStudentGrade(rs.getInt("student_grade")); // 학년
				vo.setStudentSchoolName(rs.getString("student_school_name")); // 학교명
				vo.setStudentPhone(rs.getString("student_phone")); // 휴대전화

				boolean g;
				if (rs.getString("student_gender") == "1") {
					g = true;
				} else {
					g = false;
				}

				vo.setStudentEmail(rs.getString("student_email"));
				vo.setStudentBirth(rs.getString("student_birth"));// 생년월일
				vo.setStudentRegistDate(rs.getString("student_regist_date")); // 등록일
				vo.setStudentParentsName(rs.getString("student_parents_name")); // 학부모 이름
				vo.setStudentParentsPhone(rs.getString("student_parents_phone")); // 학부모 전화
				vo.setLectureClass(rs.getString("lecture_class")); // 수강반
				vo.setStudentAddrs(rs.getString("student_addrs"));// 주소
				vo.setStudentPhoto(rs.getString("student_photo"));

				list.add(vo);

				System.out.println("dao 에서의 값 : " + vo);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 학생 번호로 상세정보 검색 (1명)
	public ClassNoteVO studentSearchSelectByNo(int studentNo) {

		// vo 초기화
		ClassNoteVO vo = new ClassNoteVO();

		// 4. sql문 작성 (조인)
		sb.setLength(0);

		sb.append(
				"SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, s.student_email, s.student_birth, s.student_addrs, ");
		sb.append("l.lecture_class, s.student_phone, s.student_regist_date, s.student_gender, s.student_photo, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s ");
		sb.append("FULL OUTER JOIN class_register c ON s.student_no = c.student_no ");
		sb.append("FULL OUTER JOIN lecture l ON c.lecture_no = l.lecture_no ");
		sb.append("WHERE s.student_no = ? ");

		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, studentNo); // bind 변수 값 주기

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(studentNo); // 번호
				vo.setStudentName(rs.getString("student_name")); // 이름
				vo.setStudentGrade(rs.getInt("student_grade")); // 학년
				vo.setStudentSchoolName(rs.getString("student_school_name")); // 학교명
				vo.setStudentPhone(rs.getString("student_phone")); // 휴대전화

				boolean g;
				if (rs.getString("student_gender") == "1") {
					g = true;
				} else {
					g = false;
				}

				vo.setStudentEmail(rs.getString("student_email"));
				vo.setStudentBirth(rs.getString("student_birth"));// 생년월일
				vo.setStudentRegistDate(rs.getString("student_regist_date")); // 등록일
				vo.setStudentParentsName(rs.getString("student_parents_name")); // 학부모 이름
				vo.setStudentParentsPhone(rs.getString("student_parents_phone")); // 학부모 전화
				vo.setLectureClass(rs.getString("lecture_class")); // 수강반
				vo.setStudentAddrs(rs.getString("student_addrs"));// 주소
				vo.setStudentPhoto(rs.getString("student_photo"));

				System.out.println("dao 에서의 값 : " + vo);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vo;
	}

	// teacher-------------------------------------------------------------------------------------------------------------------------------

	// student의 수강시작일 기준으로 기간을 특정하여 조회하기 위한 메서드 (HW)
	public ArrayList<ClassNoteVO> studentDateAtoB(String startDate, String endDate) {
	
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();
	
		// 4. SQL 문
		sb.append(
				"SELECT S.STUDENT_NO, S.STUDENT_NAME, S.STUDENT_PHONE, S.STUDENT_PARENTS_PHONE, S.STUDENT_SCHOOL_NAME, L.LECTURE_CLASS, L.LECTURE_START_DATE, L.LECTURE_END_DATE ");
		sb.append("FROM CLASS_REGISTER CR ");
		sb.append("FULL OUTER JOIN STUDENT S ON CR.STUDENT_NO = S.STUDENT_NO ");
		sb.append("FULL OUTER JOIN LECTURE L ON CR.LECTURE_NO = L.LECTURE_NO ");
		sb.append(
				"WHERE L.LECTURE_START_DATE BETWEEN TO_DATE(? || ' 00:00:00', 'YYYY-MM-DD HH24:MI:SS') AND TO_DATE(? || ' 23:59:59', 'YYYY-MM-DD HH24:MI:SS') ");
	
		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
	
			System.out.println("dao에서 출력되는 값" + startDate);
			System.out.println("dao에서 출력되는 값" + endDate);
	
			// 6. 실행
			rs = pstmt.executeQuery();
	
			// 7. 레코드별 로직 처리
			while (rs.next()) {
				ClassNoteVO vo = new ClassNoteVO();
	
				vo.setStudentNo(rs.getInt("STUDENT_NO"));
				vo.setStudentName(rs.getString("STUDENT_NAME"));
				vo.setStudentPhone(rs.getString("STUDENT_PHONE"));
				vo.setStudentParentsPhone(rs.getString("STUDENT_PARENTS_PHONE"));
				vo.setStudentSchoolName(rs.getString("STUDENT_SCHOOL_NAME"));
				vo.setLectureClass(rs.getString("LECTURE_CLASS"));
				vo.setLectureStartDate(rs.getString("LECTURE_START_DATE"));
				vo.setLectureEndDate(rs.getString("LECTURE_END_DATE"));
	
				list.add(vo);
	
				System.out.println("dao에서 출력되는 값 : " + vo);
	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return list;
	}

	// student 신규 등록 (HW)
	
	public void studentAddOne(ClassNoteVO vo) {
	
		// 4. SQL문
		sb.setLength(0);
		sb.append(
				"Insert into STUDENT (STUDENT_NO, STUDENT_NAME, STUDENT_GRADE, STUDENT_PHONE, STUDENT_REGIST_DATE, STUDENT_PARENTS_NAME, STUDENT_PARENTS_PHONE, STUDENT_PHOTO, STUDENT_GENDER, STUDENT_BIRTH, STUDENT_ADDRS, STUDENT_EMAIL, STUDENT_SCHOOL_NAME, STUDENT_STATUS) ");
		sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
	
		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());
	
			pstmt.setInt(1, vo.getStudentNo());
			pstmt.setString(2, vo.getStudentName());
			pstmt.setInt(3, vo.getStudentGrade());
			pstmt.setString(4, vo.getStudentPhone());
			pstmt.setString(5, vo.getStudentRegistDate());
			pstmt.setString(6, vo.getStudentParentsName());
			pstmt.setString(7, vo.getStudentParentsPhone());
			pstmt.setString(8, vo.getStudentPhoto());
			pstmt.setBoolean(9, vo.isStudentGender());
			pstmt.setString(10, vo.getStudentBirth());
			pstmt.setString(11, vo.getStudentAddrs());
			pstmt.setString(12, vo.getStudentEmail());
			pstmt.setString(13, vo.getStudentSchoolName());
			pstmt.setBoolean(14, vo.isStudentStatus());
	
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

	// student 신규 등록 (HW)
	
	
	
	// -----------------------------------------------
	// student 정보 수정하기
	
	// 이름으로 검색했을 경우 수정하기 (HW)
	public void studentUpdateAllByNo(ClassNoteVO vo) {
	
		// 4. SQL문 작성
		sb.setLength(0); // 초기화
		sb.append("update student ");
		sb.append(
				"set STUDENT_NAME = ?, STUDENT_GRADE = ?, STUDENT_PHONE = ?, STUDENT_REGIST_DATE = ?, STUDENT_PARENTS_NAME = ?, STUDENT_PARENTS_PHONE = ?, STUDENT_PHOTO = ?, STUDENT_GENDER = ?, STUDENT_BIRTH = ?, STUDENT_ADDRS = ?, STUDENT_EMAIL = ?, STUDENT_SCHOOL_NAME = ?, STUDENT_STATUS = ? ");
		sb.append("where STUDENT_NO = ? ");
	
		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());
	
			pstmt.setString(1, vo.getStudentName());
			pstmt.setInt(2, vo.getStudentGrade());
			pstmt.setString(3, vo.getStudentPhone());
			pstmt.setString(4, vo.getStudentRegistDate());
			pstmt.setString(5, vo.getStudentParentsName());
			pstmt.setString(6, vo.getStudentParentsPhone());
			pstmt.setString(7, vo.getStudentPhoto());
			pstmt.setBoolean(8, vo.isStudentGender());
			pstmt.setString(9, vo.getStudentBirth());
			pstmt.setString(10, vo.getStudentAddrs());
			pstmt.setString(11, vo.getStudentEmail());
			pstmt.setString(12, vo.getStudentSchoolName());
			pstmt.setBoolean(13, vo.isStudentStatus());
			pstmt.setInt(14, vo.getStudentNo());
	
			// 6. 실행
			int result = pstmt.executeUpdate();
	
			if (result == 1) {
				System.out.println("데이터 수정 성공!");
			}
	
			// 7. 레코드 별 로직 처리
			while (rs.next()) {
	
				vo.setStudentNo(rs.getInt("STUDENT_NO"));
				vo.setStudentName(rs.getString("STUDENT_NAME"));
				vo.setStudentGrade(rs.getInt("STUDENT_GRADE"));
				vo.setStudentPhone(rs.getString("STUDENT_PHONE"));
				vo.setStudentRegistDate(rs.getString("STUDENT_REGIST_DATE"));
				vo.setStudentParentsName(rs.getString("STUDENT_PARENTS_NAME"));
				vo.setStudentParentsPhone(rs.getString("STUDENT_PARENTS_PHONE"));
				vo.setStudentPhoto(rs.getString("STUDENT_PHOTO"));
				vo.isStudentGender();
				vo.setStudentBirth(rs.getString("STUDENT_BIRTH"));
				vo.setStudentAddrs(rs.getString("STUDENT_ADDRS"));
				vo.setStudentEmail(rs.getString("STUDENT_EMAIL"));
				vo.setStudentSchoolName(rs.getString("STUDENT_SCHOOL_NAME"));
				vo.isStudentStatus();
	
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	// ---------------------------------------------------
	// teacher object 을 사용해서 조회하기 위한 메서드
	
	public ArrayList<ClassNoteVO> studentSelectBySubject(String lectureClass) {
	
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();
	
		ClassNoteVO vo = null;
	
		// 4. sql문 작성 (조인)
		sb.setLength(0);
	
		sb.append(
				"SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, s.student_email, s.student_birth, s.student_addrs, ");
		sb.append("l.lecture_class, s.student_phone, s.student_regist_date, s.student_gender, s.student_photo, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s ");
		sb.append("FULL OUTER JOIN class_register c ON s.student_no = c.student_no ");
		sb.append("FULL OUTER JOIN lecture l ON c.lecture_no = l.lecture_no ");
		sb.append("WHERE l.lecture_class = ? ");
	
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, lectureClass); // bind 변수 값 주기
	
			// 6. 실행
			rs = pstmt.executeQuery();
	
			// 7. 레코드별 로직 처리
			while (rs.next()) {
	
				vo = new ClassNoteVO();
	
				vo.setStudentNo(rs.getInt("student_no")); // 번호
				vo.setStudentName(rs.getString("student_name")); // 이름
				vo.setStudentGrade(rs.getInt("student_grade")); // 학년
				vo.setStudentSchoolName(rs.getString("student_school_name")); // 학교명
				vo.setStudentPhone(rs.getString("student_phone")); // 휴대전화
	
				boolean g;
				if (rs.getString("student_gender") == "1") {
					g = true;
				} else {
					g = false;
				}
	
				vo.setStudentEmail(rs.getString("student_email"));
				vo.setStudentBirth(rs.getString("student_birth"));// 생년월일
				vo.setStudentRegistDate(rs.getString("student_regist_date")); // 등록일
				vo.setStudentParentsName(rs.getString("student_parents_name")); // 학부모 이름
				vo.setStudentParentsPhone(rs.getString("student_parents_phone")); // 학부모 전화
				vo.setLectureClass(lectureClass); // 수강반
				vo.setStudentAddrs(rs.getString("student_addrs"));// 주소
				vo.setStudentPhoto(rs.getString("student_photo"));
	
				list.add(vo);
	
				System.out.println("dao 에서의 값 : " + vo);
	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return list;
	}

	// -----------------------------------------
	
	// student name 을 사용해서 조회하기 위한 메서드 (HW)
	public ArrayList<ClassNoteVO> studentSelectByName(String studentName) {
	
		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();
	
		ClassNoteVO vo = new ClassNoteVO();
	
		// 4. SQL 문
		sb.append(
				"SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, s.student_email, s.student_birth, s.student_addrs, ");
		sb.append(
				"l.lecture_class, s.student_phone, s.student_regist_date, s.student_gender, s.student_photo, l.LECTURE_START_DATE, l.LECTURE_END_DATE, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s ");
		sb.append("FULL OUTER JOIN class_register c ON s.student_no = c.student_no ");
		sb.append("FULL OUTER JOIN lecture l ON c.lecture_no = l.lecture_no ");
		sb.append("WHERE s.student_name = ? ");
	
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, studentName); // bind 변수 값 주기
	
			// 6. 실행
			rs = pstmt.executeQuery();
	
			// 7. 레코드별 로직 처리
			while (rs.next()) {
	
				vo = new ClassNoteVO();
	
				vo.setStudentNo(rs.getInt("student_no")); // 번호
				vo.setStudentName(studentName); // 이름
				vo.setStudentGrade(rs.getInt("student_grade")); // 학년
				vo.setStudentSchoolName(rs.getString("student_school_name")); // 학교명
				vo.setStudentPhone(rs.getString("student_phone")); // 휴대전화
	
				boolean g;
				if (rs.getString("student_gender") == "1") {
					g = true;
				} else {
					g = false;
				}
	
				vo.setStudentEmail(rs.getString("student_email"));
				vo.setStudentBirth(rs.getString("student_birth"));// 생년월일
				vo.setStudentRegistDate(rs.getString("student_regist_date")); // 등록일
				vo.setStudentParentsName(rs.getString("student_parents_name")); // 학부모 이름
				vo.setStudentParentsPhone(rs.getString("student_parents_phone")); // 학부모 전화
				vo.setLectureClass(rs.getString("lecture_class")); // 수강반
				vo.setStudentAddrs(rs.getString("student_addrs"));// 주소
				vo.setStudentPhoto(rs.getString("student_photo"));
				vo.setLectureStartDate(rs.getString("LECTURE_START_DATE"));
				vo.setLectureEndDate(rs.getString("LECTURE_END_DATE"));
	
				list.add(vo);
	
				System.out.println("dao 에서의 값 : " + vo);
	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return list;
	}

	// -----------------------------------------
	// teacher 삭제
	
	// teacher 이름으로 조회했을 경우
	public void studentDeleteByNo(int studentNo) {
	
		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("DELETE ");
		sb.append("FROM STUDENT ");
		sb.append("WHERE STUDENT_NO = ? ");
	
		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, studentNo);
	
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
	
	
	// teacher 이름으로 조회했을 경우
		public void classregisterDeleteByNo(int classregisterNo) {
		
			// 4. SQL 문
			sb.setLength(0); // 초기화
			sb.append("DELETE ");
			sb.append("FROM CLASS_REGISTER ");
			sb.append("WHERE CLASS_REGISTER_NO = ? ");
		
			try {
				// 5. 문장 객체화
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, classregisterNo);
		
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

	// teacher 전체 출력하기
	public ArrayList<ClassNoteVO> teacherSelectAll() {

		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		// 4. SQL문
		sb.append(
				"SELECT T.TEACHER_NO, T.TEACHER_NAME, T.TEACHER_ID, T.TEACHER_PW, T.TEACHER_PHONE, T.TEACHER_SUBJECT, L.LECTURE_START_DATE, L.LECTURE_END_DATE ");
		sb.append("FROM CLASS_REGISTER CR ");
		sb.append("FULL OUTER JOIN TEACHER T ON CR.TEACHER_NO = T.TEACHER_NO ");
		sb.append("FULL OUTER JOIN LECTURE L ON CR.LECTURE_NO = L.LECTURE_NO ");

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

				if (rs.getString("teacher_gender") == "1")
					vo.setTeacherGender(false);
				else
					vo.setTeacherGender(true);

				list.add(vo);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// --------------------------------------------------------------------

	// teacher 전부 출력하기
	public ArrayList<ClassNoteVO> teacherSelectByAll() {

		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		// 4. SQL문
		sb.setLength(0); // 초기화
		sb.append(
				"SELECT T.TEACHER_NO, T.TEACHER_NAME, T.TEACHER_ID, T.TEACHER_PW, T.TEACHER_PHONE, T.TEACHER_SUBJECT, L.LECTURE_START_DATE, L.LECTURE_END_DATE ");
		sb.append("FROM CLASS_REGISTER CR ");
		sb.append("FULL OUTER JOIN TEACHER T ON CR.TEACHER_NO = T.TEACHER_NO ");
		sb.append("FULL OUTER JOIN LECTURE L ON CR.LECTURE_NO = L.LECTURE_NO ");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {
				ClassNoteVO vo = new ClassNoteVO();

				vo.setTeacherNo(rs.getInt("TEACHER_NO"));
				vo.setTeacherName(rs.getString("TEACHER_NAME"));
				vo.setTeacherId(rs.getString("TEACHER_ID"));
				vo.setTeacherPw(rs.getString("TEACHER_PW"));
				vo.setTeacherPhone(rs.getString("TEACHER_PHONE"));
				vo.setTeacherSubject(rs.getString("TEACHER_SUBJECT"));
				vo.setLectureStartDate(rs.getString("LECTURE_START_DATE"));
				vo.setLectureEndDate(rs.getString("LECTURE_END_DATE"));

				list.add(vo);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	//
	// teacher no 을 사용해서 조회하기 위한 메서드(HW)
	public ClassNoteVO teacherSelectAllByNo(int teacherNo) {

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
	// -----------------------------------------------------------------------

	// teacher의 강의시작일 기준으로 기간을 특정하여 조회하기 위한 메서드 (HW)
	public ArrayList<ClassNoteVO> teacherDateAtoB(String startDate, String endDate) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		// 4. SQL 문
		sb.append(
				"SELECT T.TEACHER_NO, T.TEACHER_NAME, T.TEACHER_ID, T.TEACHER_PW, T.TEACHER_PHONE, T.TEACHER_SUBJECT, L.LECTURE_START_DATE, L.LECTURE_END_DATE ");
		sb.append("FROM CLASS_REGISTER CR ");
		sb.append("FULL OUTER JOIN TEACHER T ON CR.TEACHER_NO = T.TEACHER_NO ");
		sb.append("FULL OUTER JOIN LECTURE L ON CR.LECTURE_NO = L.LECTURE_NO ");
		sb.append(
				"WHERE L.LECTURE_START_DATE BETWEEN TO_DATE(? || ' 00:00:00', 'YYYY-MM-DD HH24:MI:SS') AND TO_DATE(? || ' 23:59:59', 'YYYY-MM-DD HH24:MI:SS') ");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);

			System.out.println("dao에서 출력되는 값" + startDate);
			System.out.println("dao에서 출력되는 값" + endDate);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {
				ClassNoteVO vo = new ClassNoteVO();

				vo.setTeacherNo(rs.getInt("TEACHER_NO"));
				vo.setTeacherName(rs.getString("TEACHER_NAME"));
				vo.setTeacherId(rs.getString("TEACHER_ID"));
				vo.setTeacherPw(rs.getString("TEACHER_PW"));
				vo.setTeacherPhone(rs.getString("TEACHER_PHONE"));
				vo.setTeacherSubject(rs.getString("TEACHER_SUBJECT"));
				vo.setLectureStartDate(rs.getString("LECTURE_START_DATE"));
				vo.setLectureEndDate(rs.getString("LECTURE_END_DATE"));

				list.add(vo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	

	// -------------------------------------------------------------------------

	

	// teacher 신규 등록 (HW)

	public void teacherAddOne(ClassNoteVO vo) {

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

	// student 신규 등록 (HW)

	

	// -----------------------------------------------
	// student 정보 수정하기

	

	// 과목으로 검색했을 경우 수정하기

	// 기간으로 검색했을 경우 수정하기

	// -----------------------------------------------
	// teacher 정보 수정하기

	// 이름으로 검색했을 경우 수정하기 (HW)
	public void teacherUpdateAllByNo(ClassNoteVO vo) {

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

	// ---------------------------------------------------
	// teacher object 을 사용해서 조회하기 위한 메서드

	public ArrayList<ClassNoteVO> teacherSelectBySubject(String subject) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = new ClassNoteVO();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append(
				"SELECT TEACHER_NO, TEACHER_ID, TEACHER_PW, TEACHER_NAME, TEACHER_PHONE, TEACHER_EMAIL, TEACHER_PHOTO, TEACHER_HIREDATE, TEACHER_ADDRESS, TEACHER_SAL, TEACHER_SUBJECT, TEACHER_WORKTYPE, TEACHER_BIRTH, TEACHER_GENDER ");
		sb.append("FROM TEACHER ");
		sb.append("WHERE TEACHER_SUBJECT = ? ");

		try {
			// 5. SQL 문 객체화
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, subject);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {
				vo.setTeacherNo(rs.getInt("TEACHER_NO"));
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
				vo.setTeacherSubject(subject);
				vo.setTeacherWorktype(rs.getString("TEACHER_WORKTYPE"));
				vo.setTeacherBirth(rs.getString("TEACHER_BIRTH"));
				vo.setTeacherGender(rs.getBoolean("TEACHER_GENDER"));

				list.add(vo);

			}
			;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// ---------------------------------------------------
	// teacher object 을 사용해서 조회하기 위한 메서드

	// teacher name 을 사용해서 조회하기 위한 메서드 (HW)
	public ArrayList<ClassNoteVO> teacherSelectByName(String teacherName) {

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

				boolean g;
				if (rs.getString("TEACHER_GENDER") == "1") {
					g = true;
				} else {
					g = false;
				}

				list.add(vo);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// -----------------------------------------

	

	// -----------------------------------------

	// teacher 삭제

	// teacher 이름으로 조회했을 경우
	public void teacherDeleteByNo(int teacherNo) {

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("DELETE ");
		sb.append("FROM TEACHER ");
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

	// 과목으로 조회했을 경우
	public void teacherDeleteBySubject(int teacherNo) {

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("DELETE ");
		sb.append("FROM TEACHER ");
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

	// 강의시작일로 조회했을 경우 지우는 메서드
	public void teacherDeleteByDate(int teacherNo) {

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("DELETE FROM CLASS_REGISTER ");
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

	// -----------------------------------------
	// teacher 삭제

	

	// -----------------------------------------

	public void teacherInsertByAll(ClassNoteVO vo) {

		sb.setLength(0);
		sb.append("INSERT INTO teacher ");
		sb.append("VALUES (TEACHER_NO_SEQ.NEXTVAL, ?, ?, ?, ?, 0, ?, ?, 0, ?, 0, SYSDATE, ?, ?) ");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			String g;
			if (vo.isTeacherGender())
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

	public ClassNoteVO teacherSelectAllByIdPw(String teacherId, String teacherPw) {
		sb.setLength(0);
		sb.append("SELECT * FROM teacher WHERE teacher_id = ? and teacher_pw = ? ");

		ClassNoteVO vo = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, teacherId);
			pstmt.setString(2, teacherPw);

			rs = pstmt.executeQuery();

			while (rs.next()) {
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
				if (rs.getString("teacher_gender") == "1")
					g = true;
				else
					g = false;

				vo = new ClassNoteVO(teacherNo, teacherId, teacherPw, teacherName, teacherAddress, teacherSal,
						teacherPhone, teacherEmail, teacherSubject, teacherPhoto, teacherWorktype, teacherHiredate,
						teacherBirth, g);

				System.out.println("dao:" + vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	public ClassNoteVO teacherSelectAllById(String teacherId) {
		sb.setLength(0);
		sb.append("SELECT * FROM teacher WHERE teacher_id = ? ");

		ClassNoteVO vo = null;

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, teacherId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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

	// ------------------------------------------------------------------------------

	// 회계 프로그램 전체 조회하기

	public ArrayList<ClassNoteVO> accountingSelectByAll() {

		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("SELECT * ");
		sb.append("FROM CLASS_REGISTER ");
		sb.append("FULL OUTER JOIN STUDENT ON CLASS_REGISTER.STUDENT_NO = STUDENT.STUDENT_NO ");
		sb.append("FULL OUTER JOIN LECTURE ON CLASS_REGISTER.LECTURE_NO = LECTURE.LECTURE_NO");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {
				ClassNoteVO vo = new ClassNoteVO();

				vo.setClassRegisterNo(rs.getInt("CLASS_REGISTER_NO"));
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

	// 회계 프로그램 -- 월별 출력하기

	public ArrayList<ClassNoteVO> accountingSelectByDate(String startDate, String endDate) {

		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append(
				"SELECT CR.CLASS_REGISTER_NO, L.LECTURE_CLASS, S.STUDENT_NAME, CR.ISPAY, L.LECTURE_NAME, L.LECTURE_TUITION, CR.PAY_TYPE, L.LECTURE_START_DATE, L.LECTURE_END_DATE, S.STUDENT_DUE_DATE, S.STUDENT_PARENTS_PHONE ");
		sb.append("FROM CLASS_REGISTER CR ");
		sb.append("FULL OUTER JOIN STUDENT S ON CR.STUDENT_NO = S.STUDENT_NO ");
		sb.append("FULL OUTER JOIN LECTURE L ON CR.LECTURE_NO = L.LECTURE_NO ");
		sb.append(
				"WHERE L.LECTURE_START_DATE BETWEEN TO_DATE(?, 'DD-MM-YYYY HH24:MI:SS') AND TO_DATE(?, 'DD-MM-YYYY HH24:MI:SS') ");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리
			while (rs.next()) {
				ClassNoteVO vo = new ClassNoteVO();

				vo.setClassRegisterNo(rs.getInt("CLASS_REGISTER_NO"));
				vo.setLectureClass(rs.getString("LECTURE_CLASS"));
				vo.setStudentName(rs.getString("STUDENT_NAME"));
				vo.setPay(rs.getBoolean("ISPAY"));
				vo.setLectureName(rs.getString("LECTURE_NAME"));
				vo.setLectureTuition(rs.getInt("LECTURE_TUITION"));
				vo.setPayType(rs.getString("PAY_TYPE"));
				vo.setLectureStartDate(rs.getString("LECTURE_START_DATE"));
				vo.setLectureEndDate(rs.getString("LECTURE_END_DATE"));
				vo.setStudentDueDate(rs.getString("STUDENT_DUE_DATE"));
				vo.setStudentParentsPhone(rs.getString("STUDENT_PARENTS_PHONE"));

				list.add(vo);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// CLASS_REGISTER 수강번호를 클릭하면 수강정보 출력

	// CLASS_REGISTER_NO 을 사용해서 조회하기 위한 메서드(HW)
	public ClassNoteVO registerSelectByNo(int classRegisterNo) {

		// vo 초기화
		ClassNoteVO vo = new ClassNoteVO();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("SELECT CLASS_REGISTER_NO, ISPAY, PAY_TYPE,STUDENT_NO,TEACHER_NO,LECTURE_NO ");
		sb.append("FROM CLASS_REGISTER ");
		sb.append("WHERE CLASS_REGISTER_NO = ? ");

		try {
			// 5. SQL 문장 객체
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, classRegisterNo);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드 별 로직 처리
			while (rs.next()) {
				vo = new ClassNoteVO();

				vo.setClassRegisterNo(classRegisterNo);

				boolean g;
				if (rs.getString("ISPAY") == "1") {
					g = true;
				} else {
					g = false;
				}

				vo.setStudentNo(rs.getInt("STUDENT_NO"));
				vo.setTeacherNo(rs.getInt("TEACHER_NO"));
				vo.setLectureNo(rs.getInt("LECTURE_NO"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;

	}
	
	
	// CLASS_REGISTER 수강정보 수정하는 메서드
	
		public void registerUpdateAllByNo(ClassNoteVO vo) {

			// 4. SQL문 작성
			sb.setLength(0); // 초기화
			sb.append("UPDATE CLASS_REGISTER ");
			sb.append(
					"SET ISPAY = ?, PAY_TYPE = ?, STUDENT_NO = ?, TEACHER_NO = ?, LECTURE_NO = ? ");
			sb.append("WHERE CLASS_REGISTER_NO = ? ");

			try {
				// 5. 문장 객체화
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setBoolean(1, vo.isPay());
				pstmt.setString(2, vo.getPayType());
				pstmt.setInt(3, vo.getStudentNo());
				pstmt.setInt(4, vo.getTeacherNo());
				pstmt.setInt(5, vo.getLectureNo());
				pstmt.setInt(6, vo.getClassRegisterNo());
				
				// 6. 실행
				int result = pstmt.executeUpdate();

				if (result == 1) {
					System.out.println("데이터 수정 성공!");
				}

				// 7. 레코드 별 로직 처리
				while (rs.next()) {
					
					boolean g;
					if (rs.getString("student_gender") == "1") {
						g = true;
					} else {
						g = false;
					}

					vo.setPay(g);
					vo.setPayType(driver);
					vo.setStudentNo(result);
					vo.setTeacherNo(result);
					vo.setLectureNo(result);
					vo.setClassRegisterNo(result);

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	
	
	
	
	
	
	
	// -----------------------------------------------------------------------------------
	
	// 강의 신규 등록하는 메서드
	
	
	
	

	// 수강번호 => LECTURE 강의번호를 클릭하면 강의 상세정보 출력

	// Note-------------------------------------------------------------------------------------------------------------------------------
		
		// lecture-------------------------------------------------------------------------------------------------------------------------------
		public ArrayList<ClassNoteVO> lectureSelectAll() {
			ArrayList<ClassNoteVO> list = new ArrayList<>();
			ClassNoteVO vo = null;
		
			sb.setLength(0);
			sb.append("select * from lecture ");
		
			try {
				pstmt = conn.prepareStatement(sb.toString());
				rs = pstmt.executeQuery();
		
				while (rs.next()) {
					vo = new ClassNoteVO();
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

	// LECTURE_NO 을 사용해서 조회하기 위한 메서드
	public ClassNoteVO lectureSelectByNo(int lectureNo) {

		// vo 초기화
		ClassNoteVO vo = new ClassNoteVO();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append(
				"SELECT LECTURE_NO, LECTURE_NAME, LECTURE_CLASS, LECTURE_START_DATE, LECTURE_END_DATE, LECTURE_TUITION ");
		sb.append("FROM LECTURE ");
		sb.append("WHERE LECTURE_NO = ? ");

		try {
			// 5. SQL 문장 객체
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, lectureNo);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드 별 로직 처리
			while (rs.next()) {
				vo = new ClassNoteVO();

				vo.setLectureNo(lectureNo);
				vo.setLectureName(rs.getString("LECTURE_NAME"));
				vo.setLectureClass(rs.getString("LECTURE_CLASS"));
				vo.setLectureStartDate(rs.getString("LECTURE_START_DATE"));
				vo.setLectureEndDate(rs.getString("LECTURE_END_DATE"));
				vo.setLectureTuition(rs.getInt("LECTURE_TUITION"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;

	}

	// 강의 정보 수정하는 메서드
	
	public void lectureUpdateAllByNo(ClassNoteVO vo) {

		// 4. SQL문 작성
		sb.setLength(0); // 초기화
		sb.append("UPDATE LECTURE ");
		sb.append(
				"SET LECTURE_NAME = ?, LECTURE_CLASS = ?, LECTURE_START_DATE = ?, LECTURE_END_DATE = ?, LECTURE_TUITION = ? ");
		sb.append("WHERE LECTURE_NO = ? ");

		try {
			// 5. 문장 객체화
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, vo.getLectureName());
			pstmt.setString(2, vo.getLectureClass());
			pstmt.setString(3, vo.getLectureStartDate());
			pstmt.setString(4, vo.getLectureEndDate());
			pstmt.setInt(5, vo.getLectureTuition());
			pstmt.setInt(6, vo.getLectureNo());
	
			// 6. 실행
			int result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("데이터 수정 성공!");
			}

			// 7. 레코드 별 로직 처리
			while (rs.next()) {

				vo.setLectureNo(rs.getInt("LECTURE_NO"));
				vo.setLectureName(rs.getString("LECTURE_NAME"));
				vo.setLectureClass(rs.getString("LECTURE_CLASS"));
				vo.setLectureStartDate(rs.getString("LECTURE_START_DATE"));
				vo.setLectureEndDate(rs.getString("LECTURE_END_DATE"));
				vo.setLectureTuition(rs.getInt("LECTURE_TUITION"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// teacherCheck-------------------------------------------------------------------------------------------------------------------------------

	
	
	// jsb // studentList-------------------------------------------------------------------------------------------------------------------------------

	
	
		// studentSearch-------------------------------------------------------------------------------------------------------------------------------
	// 메서드에서 (JDBC의 4-7단계)

	// <전체조회> - 입력값 없이 조회

	// student - class_register - lecture 조인

	public ArrayList<ClassNoteVO> studentSearchSelectAll() {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);
		// sb.append("SELECT * FROM student "); //조인안될때 예비로 쓴 쿼리문

		// 아니 디비에서는 되는데 대체 뭐가 문제야 ==> 음... 이름값을 받아오는 selectName() 에서 AND student_name=?
		// 이 쿼리문을 빼먹어서
		sb.append("SELECT * ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");

		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				// 객체 생성하면서 자동으로 기본생성자 생성
				vo = new ClassNoteVO();
				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));
				vo.setStudentEmail(rs.getString("student_email"));
				vo.setStudentBirth(rs.getString("student_birth"));
				vo.setStudentAddrs(rs.getString("student_addrs"));
				vo.setStudentPhoto(rs.getString("student_photo"));
				vo.setStudentStatus(rs.getBoolean("student_status"));

				list.add(vo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	} // selectAll() 끝



// studentSearch-------------------------------------------------------------------------------------------------------------------------------

	// 이름값을 넘겨주고 detail.jsp에
	public ArrayList<ClassNoteVO> studentSearchSelectAllByNameToDetail(String studentName) {
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();
		ClassNoteVO vo = null;

		sb.setLength(0);
		// s b.append("SELECT * FROM student "); //조인안될때 예비로 쓴 쿼리문

		// 아니 디비에서는 되는데 대체 뭐가 문제야 ==> 음... 이름값을 받아오는 selectName() 에서 AND student_name=?
		// 이 쿼리문을 빼먹어서
		sb.append("SELECT * ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND student_name=?  ");

		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, studentName);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				// 조장님이 주신 ClassNoteVO 사용하려면 set 해줘야함

				// 객체 생성하면서 자동으로 기본생성자 생성
				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				vo.setStudentEmail(rs.getString("student_email"));
				vo.setStudentBirth(rs.getString("student_birth"));
				vo.setStudentAddrs(rs.getString("student_addrs"));
				vo.setStudentPhoto(rs.getString("student_photo"));
				vo.setStudentStatus(rs.getBoolean("student_status"));

				list.add(vo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	} // selectAll() 끝
		// <이름을 입력하면 리스트 조회>

	public ArrayList<ClassNoteVO> studentSearchSelectAllByName(String studentName) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);
		// sb.append("SELECT * FROM student WHERE student_name=? ");

		// 아니 디비에서는 되는데 대체 뭐가 문제야 ==> AND student_name=? 이 쿼리문을 빼먹어서
		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND student_name=?  "); // where절은 한 쿼리문에 두번 쓸 수 없다 (AND로 쓰기)

		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, studentName); // bind 변수 값 주기

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// <학년 입력> 학생 학년으로 모두 조회
	public ArrayList<ClassNoteVO> studentSearchSelectAllByGrade(int studentGrade) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);

		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND student_grade=?  ");
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, studentGrade); // bind 변수 값 주기

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// <분반 입력> 학생 반으로 모두조회
	public ArrayList<ClassNoteVO> studentSearchSelectByLectureClass(String lectureClass) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성
		sb.setLength(0);

		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND lecture_class=?  ");
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, lectureClass); // bind 변수 값 주기

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);

				// System.out.println(list);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// <강의명 입력>
	public ArrayList<ClassNoteVO> studentSearchSelectByLectureName(String lectureName) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성
		sb.setLength(0);

		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND lecture_name=?  ");
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, lectureName); // bind 변수 값 주기

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 학년+분반
	public ArrayList<ClassNoteVO> studentSearchSelectByGradeLectureClass(int studentGrade, String lectureClass) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성
		sb.setLength(0);

		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND student_grade=? AND lecture_class=?  ");
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, studentGrade);
			pstmt.setString(2, lectureClass);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 학년+강의명
	public ArrayList<ClassNoteVO> studentSearchSelectByGradeLectureName(int studentGrade, String lectureName) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성
		sb.setLength(0);

		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND student_grade=? AND lecture_name=?  ");
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, studentGrade);
			pstmt.setString(2, lectureName);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 분반+강의명
	public ArrayList<ClassNoteVO> studentSearchSelectByLectureClassLectureName(String lectureClass,
			String lectureName) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성
		sb.setLength(0);

		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND lecture_class=? AND lecture_name=?  ");
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, lectureClass);
			pstmt.setString(2, lectureName);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 학년+분반+강의명
	public ArrayList<ClassNoteVO> studentSearchSelectByGradeLectureClassLectureName(int studentGrade,
			String lectureClass, String lectureName) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성
		sb.setLength(0);

		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND student_grade=? AND lecture_class=? AND lecture_name=?  ");
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, studentGrade);
			pstmt.setString(2, lectureClass);
			pstmt.setString(3, lectureName);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}



// jsb dao 추가
	//날짜만 검색
	public ArrayList<ClassNoteVO> studentSelectAllByRegistDate(String date1, String date2) {
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;

		sb.setLength(0);
		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND s.student_regist_date ");
		sb.append("BETWEEN ? AND ? ");

		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, date1); // bind 변수 값 주기
			pstmt.setString(2, date2); // bind 변수 값 주기

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	
	//학년+분반+강의명+날짜
	public ArrayList<ClassNoteVO> studentSearchSelectByGradeLectureClassLectureNameRegistDate(int studentGrade,
			String lectureClass, String lectureName, String date1, String date2) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성
		sb.setLength(0);

		sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
		sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
		sb.append("s.student_parents_name, s.student_parents_phone ");
		sb.append("FROM student s, class_register c, lecture l ");
		sb.append("WHERE s.student_no = c.student_no ");
		sb.append("AND c.lecture_no = l.lecture_no ");
		sb.append("AND student_grade=? AND lecture_class=? AND lecture_name=?  ");
		sb.append("AND s.student_regist_date ");
		sb.append("BETWEEN ? AND ? ");
		try {
			// 5. 문장객체 생성
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, studentGrade);
			pstmt.setString(2, lectureClass);
			pstmt.setString(3, lectureName);
			pstmt.setString(4, date1);
			pstmt.setString(5, date2);

			// 6. 실행
			rs = pstmt.executeQuery();

			// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
			while (rs.next()) {

				vo = new ClassNoteVO();

				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentRegistDate(rs.getString("student_regist_date"));
				vo.setStudentGender(rs.getBoolean("student_gender"));
				vo.setStudentParentsName(rs.getString("student_parents_name"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	//학년+분반+날짜
		public ArrayList<ClassNoteVO> studentSearchSelectByGradeLectureClassRegistDate(int studentGrade,
				String lectureClass, String date1, String date2) {

			ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

			ClassNoteVO vo = null;

			// 4. sql문 작성
			sb.setLength(0);

			sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
			sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
			sb.append("s.student_parents_name, s.student_parents_phone ");
			sb.append("FROM student s, class_register c, lecture l ");
			sb.append("WHERE s.student_no = c.student_no ");
			sb.append("AND c.lecture_no = l.lecture_no ");
			sb.append("AND student_grade=? AND lecture_class=? ");
			sb.append("AND s.student_regist_date ");
			sb.append("BETWEEN ? AND ? ");
			try {
				// 5. 문장객체 생성
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, studentGrade);
				pstmt.setString(2, lectureClass);
				pstmt.setString(3, date1);
				pstmt.setString(4, date2);

				// 6. 실행
				rs = pstmt.executeQuery();

				// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
				while (rs.next()) {

					vo = new ClassNoteVO();

					vo.setStudentNo(rs.getInt("student_no"));
					vo.setStudentName(rs.getString("student_name"));
					vo.setStudentSchoolName(rs.getString("student_school_name"));
					vo.setStudentGrade(rs.getInt("student_grade"));
					vo.setLectureClass(rs.getString("lecture_class"));
					vo.setStudentPhone(rs.getString("student_phone"));
					vo.setStudentRegistDate(rs.getString("student_regist_date"));
					vo.setStudentGender(rs.getBoolean("student_gender"));
					vo.setStudentParentsName(rs.getString("student_parents_name"));
					vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

					list.add(vo);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return list;
		}
		
		//학년+강의명+날짜
				public ArrayList<ClassNoteVO> studentSearchSelectByGradeLectureNameRegistDate(int studentGrade,
						String lectureName, String date1, String date2) {

					ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

					ClassNoteVO vo = null;

					// 4. sql문 작성
					sb.setLength(0);

					sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
					sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
					sb.append("s.student_parents_name, s.student_parents_phone ");
					sb.append("FROM student s, class_register c, lecture l ");
					sb.append("WHERE s.student_no = c.student_no ");
					sb.append("AND c.lecture_no = l.lecture_no ");
					sb.append("AND student_grade=? AND lecture_name=? ");
					sb.append("AND s.student_regist_date ");
					sb.append("BETWEEN ? AND ? ");
					try {
						// 5. 문장객체 생성
						pstmt = conn.prepareStatement(sb.toString());
						pstmt.setInt(1, studentGrade);
						pstmt.setString(2, lectureName);
						pstmt.setString(3, date1);
						pstmt.setString(4, date2);

						// 6. 실행
						rs = pstmt.executeQuery();

						// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
						while (rs.next()) {

							vo = new ClassNoteVO();

							vo.setStudentNo(rs.getInt("student_no"));
							vo.setStudentName(rs.getString("student_name"));
							vo.setStudentSchoolName(rs.getString("student_school_name"));
							vo.setStudentGrade(rs.getInt("student_grade"));
							vo.setLectureClass(rs.getString("lecture_class"));
							vo.setStudentPhone(rs.getString("student_phone"));
							vo.setStudentRegistDate(rs.getString("student_regist_date"));
							vo.setStudentGender(rs.getBoolean("student_gender"));
							vo.setStudentParentsName(rs.getString("student_parents_name"));
							vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

							list.add(vo);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return list;
				}
				
				//분반+강의명+날짜
				public ArrayList<ClassNoteVO> studentSearchSelectByLectureClassLectureNameRegistDate(String lectureClass,
						String lectureName, String date1, String date2) {

					ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

					ClassNoteVO vo = null;

					// 4. sql문 작성
					sb.setLength(0);

					sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
					sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
					sb.append("s.student_parents_name, s.student_parents_phone ");
					sb.append("FROM student s, class_register c, lecture l ");
					sb.append("WHERE s.student_no = c.student_no ");
					sb.append("AND c.lecture_no = l.lecture_no ");
					sb.append("AND lecture_class=? AND lecture_name=? ");
					sb.append("AND s.student_regist_date ");
					sb.append("BETWEEN ? AND ? ");
					try {
						// 5. 문장객체 생성
						pstmt = conn.prepareStatement(sb.toString());
						pstmt.setString(1, lectureClass);
						pstmt.setString(2, lectureName);
						pstmt.setString(3, date1);
						pstmt.setString(4, date2);

						// 6. 실행
						rs = pstmt.executeQuery();

						// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
						while (rs.next()) {

							vo = new ClassNoteVO();

							vo.setStudentNo(rs.getInt("student_no"));
							vo.setStudentName(rs.getString("student_name"));
							vo.setStudentSchoolName(rs.getString("student_school_name"));
							vo.setStudentGrade(rs.getInt("student_grade"));
							vo.setLectureClass(rs.getString("lecture_class"));
							vo.setStudentPhone(rs.getString("student_phone"));
							vo.setStudentRegistDate(rs.getString("student_regist_date"));
							vo.setStudentGender(rs.getBoolean("student_gender"));
							vo.setStudentParentsName(rs.getString("student_parents_name"));
							vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

							list.add(vo);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return list;
				}
				
				//학년+날짜
				public ArrayList<ClassNoteVO> studentSearchSelectByGradeRegistDate(int studentGrade,
						 String date1, String date2) {

					ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

					ClassNoteVO vo = null;

					// 4. sql문 작성
					sb.setLength(0);

					sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
					sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
					sb.append("s.student_parents_name, s.student_parents_phone ");
					sb.append("FROM student s, class_register c, lecture l ");
					sb.append("WHERE s.student_no = c.student_no ");
					sb.append("AND c.lecture_no = l.lecture_no ");
					sb.append("AND student_grade=?  ");
					sb.append("AND s.student_regist_date ");
					sb.append("BETWEEN ? AND ? ");
					try {
						// 5. 문장객체 생성
						pstmt = conn.prepareStatement(sb.toString());
						pstmt.setInt(1, studentGrade);
						pstmt.setString(2, date1);
						pstmt.setString(3, date2);

						// 6. 실행
						rs = pstmt.executeQuery();

						// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
						while (rs.next()) {

							vo = new ClassNoteVO();

							vo.setStudentNo(rs.getInt("student_no"));
							vo.setStudentName(rs.getString("student_name"));
							vo.setStudentSchoolName(rs.getString("student_school_name"));
							vo.setStudentGrade(rs.getInt("student_grade"));
							vo.setLectureClass(rs.getString("lecture_class"));
							vo.setStudentPhone(rs.getString("student_phone"));
							vo.setStudentRegistDate(rs.getString("student_regist_date"));
							vo.setStudentGender(rs.getBoolean("student_gender"));
							vo.setStudentParentsName(rs.getString("student_parents_name"));
							vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

							list.add(vo);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return list;
				}
				
				//분반+날짜
				public ArrayList<ClassNoteVO> studentSearchSelectByLectureClassRegistDate(String lectureClass,
						 String date1, String date2) {

					ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

					ClassNoteVO vo = null;

					// 4. sql문 작성
					sb.setLength(0);

					sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
					sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
					sb.append("s.student_parents_name, s.student_parents_phone ");
					sb.append("FROM student s, class_register c, lecture l ");
					sb.append("WHERE s.student_no = c.student_no ");
					sb.append("AND c.lecture_no = l.lecture_no ");
					sb.append("AND lecture_class=?  ");
					sb.append("AND s.student_regist_date ");
					sb.append("BETWEEN ? AND ? ");
					try {
						// 5. 문장객체 생성
						pstmt = conn.prepareStatement(sb.toString());
						pstmt.setString(1, lectureClass);
						pstmt.setString(2, date1);
						pstmt.setString(3, date2);

						// 6. 실행
						rs = pstmt.executeQuery();

						// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
						while (rs.next()) {

							vo = new ClassNoteVO();

							vo.setStudentNo(rs.getInt("student_no"));
							vo.setStudentName(rs.getString("student_name"));
							vo.setStudentSchoolName(rs.getString("student_school_name"));
							vo.setStudentGrade(rs.getInt("student_grade"));
							vo.setLectureClass(rs.getString("lecture_class"));
							vo.setStudentPhone(rs.getString("student_phone"));
							vo.setStudentRegistDate(rs.getString("student_regist_date"));
							vo.setStudentGender(rs.getBoolean("student_gender"));
							vo.setStudentParentsName(rs.getString("student_parents_name"));
							vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

							list.add(vo);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return list;
				}
				
				//강의명+날짜
				public ArrayList<ClassNoteVO> studentSearchSelectByLectureNameRegistDate(String lectureName,
						 String date1, String date2) {

					ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

					ClassNoteVO vo = null;

					// 4. sql문 작성
					sb.setLength(0);

					sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
					sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
					sb.append("s.student_parents_name, s.student_parents_phone ");
					sb.append("FROM student s, class_register c, lecture l ");
					sb.append("WHERE s.student_no = c.student_no ");
					sb.append("AND c.lecture_no = l.lecture_no ");
					sb.append("AND lecture_name=?  ");
					sb.append("AND s.student_regist_date ");
					sb.append("BETWEEN ? AND ? ");
					try {
						// 5. 문장객체 생성
						pstmt = conn.prepareStatement(sb.toString());
						pstmt.setString(1, lectureName);
						pstmt.setString(2, date1);
						pstmt.setString(3, date2);

						// 6. 실행
						rs = pstmt.executeQuery();

						// 7. 레코드별 로직 처리 (출력하고 싶은것만 하는게 아니라 모든 매개변수 다 가져와 일단)
						while (rs.next()) {

							vo = new ClassNoteVO();

							vo.setStudentNo(rs.getInt("student_no"));
							vo.setStudentName(rs.getString("student_name"));
							vo.setStudentSchoolName(rs.getString("student_school_name"));
							vo.setStudentGrade(rs.getInt("student_grade"));
							vo.setLectureClass(rs.getString("lecture_class"));
							vo.setStudentPhone(rs.getString("student_phone"));
							vo.setStudentRegistDate(rs.getString("student_regist_date"));
							vo.setStudentGender(rs.getBoolean("student_gender"));
							vo.setStudentParentsName(rs.getString("student_parents_name"));
							vo.setStudentParentsPhone(rs.getString("student_parents_phone"));

							list.add(vo);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return list;
				}



	// -----------------------------------------------------------------------

//ptm-----------------------------------------------------------------------------------

				
//	teacherSelectAllById 중복 주석
//	public ClassNoteVO teacherSelectAllById(String teacherId) {
//			sb.setLength(0);
//			sb.append("SELECT * FROM teacher WHERE teacher_id = ? ");
//			
//			ClassNoteVO vo = null;
//			
//			try {
//				pstmt = conn.prepareStatement(sb.toString());
//				pstmt.setString(1, teacherId);
//				rs = pstmt.executeQuery();
//				
//				while(rs.next()) {
//					int teacherNo = rs.getInt("teacher_no");
//					String teacherPw = rs.getString("teacher_pw");
//					String teacherName = rs.getString("teacher_name");
//					String teacherAddress = rs.getString("teacher_address");
//					int teacherSal = rs.getInt("teacher_sal");
//					String teacherPhone = rs.getString("teacher_phone");
//					String teacherEmail = rs.getString("teacher_email");
//					String teacherSubject = rs.getString("teacher_subject");
//					String teacherPhoto = rs.getString("teacher_photo");
//					String teacherWorktype = rs.getString("teacher_worktype");
//					String teacherHiredate = rs.getString("teacher_hiredate");
//					String teacherBirth = rs.getString("teacher_birth");
//					String teacherGender = rs.getString("teacher_gender");
//					
//					vo = new ClassNoteVO();
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return vo;
//		}
	
//		teacherSelectAllByIdPw 중복 주석
//		public ClassNoteVO teacherSelectAllByIdPw(String teacherId, String teacherPw) {
//			sb.setLength(0);
//			sb.append("SELECT * FROM teacher WHERE teacher_id = ? and teacher_pw = ? ");
//			
//			ClassNoteVO vo = null;
//			try {
//				pstmt = conn.prepareStatement(sb.toString());
//				pstmt.setString(1, teacherId);
//				pstmt.setString(2, teacherPw);
//				
//				rs = pstmt.executeQuery();
//				
//				while(rs.next()) {
//					int teacherNo = rs.getInt("teacher_no");
//					String teacherName = rs.getString("teacher_name");
//					String teacherAddress = rs.getString("teacher_address");
//					int teacherSal = rs.getInt("teacher_sal");
//					String teacherPhone = rs.getString("teacher_phone");
//					String teacherEmail = rs.getString("teacher_email");
//					String teacherSubject = rs.getString("teacher_subject");
//					String teacherPhoto = rs.getString("teacher_photo");
//					String teacherWorktype = rs.getString("teacher_worktype");
//					String teacherHiredate = rs.getString("teacher_hiredate");
//					String teacherBirth = rs.getString("teacher_birth");
//					
//					boolean g;
//					if(rs.getString("teacher_gender") == "1")
//						g = true;
//					else
//						g = false;
//					
//					vo = new ClassNoteVO(teacherNo, teacherId, teacherPw, teacherName, teacherAddress
//							, teacherSal, teacherPhone, teacherEmail, teacherSubject, teacherPhoto
//							, teacherWorktype, teacherHiredate, teacherBirth, g);
//					
//					System.out.println("dao:"+vo);
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return vo;
//		}
//		teacherSelectAllByNo 중복 주석
//		public ClassNoteVO teacherSelectAllByNo(int teacherNo) {
//
//			// vo 초기화
//			ClassNoteVO vo = new ClassNoteVO();
//
//			// 4. SQL 문
//			sb.setLength(0); // 초기화
//			sb.append("SELECT TEACHER_NO, TEACHER_ID, TEACHER_PW, TEACHER_NAME, TEACHER_PHONE, TEACHER_EMAIL, TEACHER_PHOTO, TEACHER_HIREDATE, TEACHER_ADDRESS, TEACHER_SAL, TEACHER_SUBJECT, TEACHER_WORKTYPE, TEACHER_BIRTH, TEACHER_GENDER ");
//			sb.append("FROM TEACHER ");
//			sb.append("WHERE TEACHER_NO = ? ");
//
//			try {
//				// 5. SQL 문장 객체
//				pstmt = conn.prepareStatement(sb.toString());
//				pstmt.setInt(1, teacherNo);
//
//				// 6. 실행
//				rs = pstmt.executeQuery();
//
//				// 7. 레코드 별 로직 처리
//				while (rs.next()) {
//					vo = new ClassNoteVO();
//
//					vo.setTeacherNo(teacherNo);
//					vo.setTeacherId(rs.getString("TEACHER_ID"));
//					vo.setTeacherPw(rs.getString("TEACHER_PW"));
//					vo.setTeacherName(rs.getString("TEACHER_NAME"));
//					vo.setTeacherPhone(rs.getString("TEACHER_PHONE"));
//					vo.setTeacherEmail(rs.getString("TEACHER_EMAIL"));
//					vo.setTeacherPhoto(rs.getString("TEACHER_PHOTO"));
//					vo.setTeacherHiredate(rs.getString("TEACHER_HIREDATE"));
//					vo.setTeacherAddress(rs.getString("TEACHER_ADDRESS"));
//					vo.setTeacherSal(rs.getInt("TEACHER_SAL"));
//					vo.setTeacherSubject(rs.getString("TEACHER_SUBJECT"));
//					vo.setTeacherWorktype(rs.getString("TEACHER_WORKTYPE"));
//					vo.setTeacherBirth(rs.getString("TEACHER_BIRTH"));
//					vo.setTeacherGender(rs.getBoolean("TEACHER_GENDER"));
//
//				}
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			return vo;
//
//		}
		
		public void updateOne(ClassNoteVO vo) {
			sb.setLength(0);
			sb.append("UPDATE teacher ");
			sb.append("SET teacher_photo = ?, teacher_pw = ?, teacher_phone = ?, teacher_email = ? ");
			sb.append("WHERE teacher_no = ? ");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, vo.getTeacherPhoto());
				pstmt.setString(2, vo.getTeacherPw());
				pstmt.setString(3, vo.getTeacherPhone());
				pstmt.setString(4, vo.getTeacherEmail());
				pstmt.setInt(5, vo.getTeacherNo());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
//		teacherInsertByAll 중복 주석
//		public void teacherInsertByAll(ClassNoteVO vo) {
//			
//			sb.setLength(0);
//			sb.append("INSERT INTO teacher ");
//			sb.append("VALUES (TEACHER_NO_SEQ.NEXTVAL, ?, ?, ?, ?, 0, ?, ?, 0, ?, 0, SYSDATE, ?, ?) ");
//			
//			try {
//				pstmt = conn.prepareStatement(sb.toString());
//				
//				String g;
//				if(vo.isTeacherGender())
//					g = "1";
//				else
//					g = "0";
//				
//				pstmt.setString(1, vo.getTeacherId());
//				pstmt.setString(2, vo.getTeacherPw());
//				pstmt.setString(3, vo.getTeacherName());
//				pstmt.setString(4, vo.getTeacherAddress());
//				pstmt.setString(5, vo.getTeacherPhone());
//				pstmt.setString(6, vo.getTeacherEmail());
//				pstmt.setString(7, vo.getTeacherPhoto());
//				pstmt.setString(8, vo.getTeacherBirth());
//				pstmt.setString(9, g);
//				
//				pstmt.executeUpdate();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	//----------------------------------------------------------------------------------------------------
		public void studentNoteInsert(String title, String tarea, String tname) {

//		로그인 되어있는 교사의 교사번호를 담은 무언가

		sb.setLength(0);
		sb.append(
				"insert into class_note values(note_no_seq.nextval, sysdate, ?, ?, (select teacher_no from teacher where teacher_name = ? )) ");

		try {
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, title);
			pstmt.setString(2, tarea);
			pstmt.setString(3, tname);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void studentNoteDeleteOne(int noteno) {
		sb.setLength(0);
		sb.append("delete from class_note where note_no = ? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, noteno);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void studentNoteUpdateOne(ClassNoteVO vo) {
		sb.setLength(0);
		sb.append("update class_note set note_title = ?, note_contents = ?, note_date = sysdate where note_no = ? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getNoteTitle());
			pstmt.setString(2, vo.getNoteContents());
			pstmt.setInt(3, vo.getNoteNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ClassNoteVO studentNoteSelectOne(int noteno) {
		ClassNoteVO vo = null;

		sb.setLength(0);
		sb.append(
				"SELECT note_date, note_title, note_contents, t.teacher_name, r.class_register_no, s.student_name, l.lecture_name, l.lecture_class ");
		sb.append("FROM class_note n ");
		sb.append("JOIN class_register r ON r.class_register_no = n.class_register_no ");
		sb.append("join student s on s.student_no = r.student_no ");
		sb.append("JOIN teacher t ON t.teacher_no = r.teacher_no ");
		sb.append("JOIN lecture l ON l.lecture_no = r.lecture_no ");
		sb.append("WHERE n.note_no = ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, noteno);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new ClassNoteVO();
				vo.setNoteNo(noteno);
				vo.setNoteDate(rs.getString("note_date"));
				vo.setNoteTitle(rs.getString("note_title"));
				vo.setNoteContents(rs.getString("note_contents"));
				vo.setTeacherName(rs.getString("teacher_name"));
				vo.setClassRegisterNo(rs.getInt("class_register_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setLectureName(rs.getString("lecture_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vo;
	}

	public ArrayList<ClassNoteVO> studentNoteSelectAll() {
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;

		sb.setLength(0);
		sb.append(
				"select cn.note_no, cn.note_date, cn.note_title, cn.note_contents, cn.class_register_no, s.student_name, l.lecture_name, l.lecture_class, t.teacher_name "
						+ "from class_note cn "
						+ "join class_register cr ON cn.class_register_no = cr.class_register_no "
						+ "join lecture l on cr.lecture_no = l.lecture_no "
						+ "join student s on cr.student_no = s.student_no "
						+ "join teacher t on cr.teacher_no = t.teacher_no ");

		try {
			
			pstmt = conn.prepareStatement(sb.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ClassNoteVO();
				
				vo.setNoteNo(rs.getInt("note_no"));
				vo.setNoteDate(rs.getString("note_date"));
				vo.setNoteTitle(rs.getString("note_title"));
				vo.setNoteContents(rs.getString("note_contents"));
				vo.setTeacherNo(rs.getInt("class_register_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setLectureName(rs.getString("lecture_name"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setTeacherName(rs.getString("teacher_name"));

				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<ClassNoteVO> studentNoteSelectStudentName(String name) {
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = new ClassNoteVO();

		sb.setLength(0);
		sb.append(
				"select cn.note_no, cn.note_date, cn.note_title, cn.note_contents, cn.class_register_no, s.student_name, l.lecture_name, l.lecture_class, T.TEACHER_NAME "
				+ "from class_note cn "
				+ "join class_register cr ON cn.class_register_no = cr.class_register_no "
				+ "join lecture l ON cr.lecture_no = l.lecture_no "
				+ "join student s ON cr.student_no = s.student_no "
				+ "join teacher T ON cr.teacher_no = T.teacher_no "
				+ "where s.student_name = ? ");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				vo.setNoteNo(rs.getInt("note_no"));
				vo.setNoteDate(rs.getString("note_date"));
				vo.setNoteTitle(rs.getString("note_title"));
				vo.setNoteContents(rs.getString("note_contents"));
				vo.setTeacherNo(rs.getInt("class_register_no"));
				vo.setStudentName(name);
				vo.setLectureName(rs.getString("lecture_name"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setTeacherName(rs.getString("teacher_name"));

				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	

	public ArrayList<ClassNoteVO> studentNoteSelectAll(String lectureName) {
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;

		sb.setLength(0);
		sb.append(
				"select cn.note_no, cn.note_date, cn.note_title, cn.note_contents, cn.class_register_no, s.student_name, l.lecture_name, l.lecture_class, t.teacher_name "
						+ "from class_note cn "
						+ "join class_register cr ON cn.class_register_no = cr.class_register_no "
						+ "join lecture l on cr.lecture_no = l.lecture_no "
						+ "join student s on cr.student_no = s.student_no "
						+ "join teacher t on cr.teacher_no = t.teacher_no " + "where lecture_name = ? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, lectureName);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ClassNoteVO();

				vo.setNoteNo(rs.getInt("note_no"));
				vo.setNoteDate(rs.getString("note_date"));
				vo.setNoteTitle(rs.getString("note_title"));
				vo.setNoteContents(rs.getString("note_contents"));
				vo.setClassRegisterNo(rs.getInt("class_register_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setLectureName(rs.getString("lecture_name"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setTeacherName(rs.getString("teacher_name"));

				list.add(vo);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<ClassNoteVO> studentNoteSelectAll(String lectureName, String lectureClass) {
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;

		sb.setLength(0);
		sb.append(
				"select cn.note_no, cn.note_date, cn.note_title, cn.note_contents, cn.class_register_no, s.student_name, l.lecture_name, l.lecture_class, t.teacher_name "
						+ "from class_note cn "
						+ "join class_register cr ON cn.class_register_no = cr.class_register_no "
						+ "join lecture l on cr.lecture_no = l.lecture_no "
						+ "join student s on cr.student_no = s.student_no "
						+ "join teacher t on cr.teacher_no = t.teacher_no "
						+ "where l.lecture_name = ? and l.lecture_class = ? ");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, lectureName);
			pstmt.setString(2, lectureClass);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ClassNoteVO();

				vo.setNoteNo(rs.getInt("note_no"));
				vo.setNoteDate(rs.getString("note_date"));
				vo.setNoteTitle(rs.getString("note_title"));
				vo.setNoteContents(rs.getString("note_contents"));
				vo.setClassRegisterNo(rs.getInt("class_register_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setLectureName(rs.getString("lecture_name"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setTeacherName(rs.getString("teacher_name"));

				list.add(vo);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<ClassNoteVO> studentNoteSelectAllbyLectureClass(String Class) {
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;

		sb.setLength(0);
		sb.append(
				"select cn.note_no, cn.note_date, cn.note_title, cn.note_contents, cn.class_register_no, s.student_name, l.lecture_name, l.lecture_class, t.teacher_name "
						+ "from class_note cn "
						+ "join class_register cr ON cn.class_register_no = cr.class_register_no "
						+ "join lecture l on cr.lecture_no = l.lecture_no "
						+ "join student s on cr.student_no = s.student_no "
						+ "join teacher t on cr.teacher_no = t.teacher_no " + "where l.lecture_class = ? ");

		try {

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, Class);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ClassNoteVO();

				vo.setNoteNo(rs.getInt("note_no"));
				vo.setNoteDate(rs.getString("note_date"));
				vo.setNoteTitle(rs.getString("note_title"));
				vo.setNoteContents(rs.getString("note_contents"));
				vo.setClassRegisterNo(rs.getInt("class_register_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setLectureName(rs.getString("lecture_name"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setTeacherName(rs.getString("teacher_name"));

				list.add(vo);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
//	choi won kyu
// ----------------------------------------------------------------------------------------------------
	// close 메서드
	
	public void close() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
	

}
