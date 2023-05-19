package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import VO.ClassNoteVO;

public class StudentDAO {

	// 기본생성자 (JDBC의 1-3단계)
	// 1. 환경변수
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.0.26:1521:orcl";
	// 조장님꺼
	// String url = "jdbc:oracle:thin:@192.168.0.26:1521:orcl";
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

	// --------------------------------------------------------------------------------
	// 메서드에서 (JDBC의 4-7단계)

	// <전체조회> - 입력값 없이 조회
	public ArrayList<ClassNoteVO> selectAll() {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);
//		sb.append("SELECT * FROM student "); //조인안될때 예비로 쓴 쿼리문

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
	
	//check.jsp 페이지에서 날짜 값으로 학생 모두조회
		public ArrayList<ClassNoteVO> studentSelectAll(String date){
			ArrayList<ClassNoteVO> list = new ArrayList<>();
			ClassNoteVO vo = null;
			
			sb.setLength(0);
			sb.append("SELECT * ");
			sb.append("FROM STUDENT_CHECK ");
			sb.append("INNER JOIN STUDENT ");
			sb.append("ON STUDENT_CHECK.STUDENT_NO = STUDENT.STUDENT_NO ");
			sb.append("WHERE STUDENT_CHECK.STUDENT_CHECK_DATE = ? ");
			
			// 5. 문장객체
			try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while ( rs.next() ) {
			    vo = new ClassNoteVO();
			    vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				//vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));
			    vo.setStudentCheckNo(rs.getInt("student_check_no"));
			    vo.setStudentCheckIn(rs.getString("student_check_in"));
			    vo.setStudentCheckLate(rs.getString("student_check_late"));
			    vo.setStudentCheckLeave(rs.getString("student_check_leave"));
			    vo.setStudentCheckDate(rs.getString("student_check_date"));
			    list.add(vo);
			    System.out.println("LIST " + list);
			}

			}catch (SQLException e) {			
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
			}	
		public ArrayList<ClassNoteVO> studentSelectAll(String date1, String date2){
			ArrayList<ClassNoteVO> list = new ArrayList<>();
			ClassNoteVO vo = null;
			
			sb.setLength(0);
			sb.append("SELECT * ");
			sb.append("FROM STUDENT_CHECK ");
			sb.append("INNER JOIN STUDENT ");
			sb.append("ON STUDENT_CHECK.STUDENT_NO = STUDENT.STUDENT_NO ");
			sb.append("WHERE TO_CHAR(STUDENT_CHECK.STUDENT_CHECK_DATE, 'YYYY-MM-DD') ");
			sb.append("BETWEEN ? AND ? ");
			
			// 5. 문장객체
			try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			rs = pstmt.executeQuery();
			while ( rs.next() ) {
			    vo = new ClassNoteVO();
			    vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				//vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));
			    vo.setStudentCheckNo(rs.getInt("student_check_no"));
			    vo.setStudentCheckIn(rs.getString("student_check_in"));
			    vo.setStudentCheckLate(rs.getString("student_check_late"));
			    vo.setStudentCheckLeave(rs.getString("student_check_leave"));
			    vo.setStudentCheckDate(rs.getString("student_check_date"));
			    
			    list.add(vo);
			    System.out.println("LIST " + list);
			}

			}catch (SQLException e) {			
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
			}	
			

	// -------------------------------------------------------------------------------------------------------------------------------

	// <이름을 입력하면 리스트 조회>
	public ArrayList<ClassNoteVO> selectAllByName(String studentName) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);
//		sb.append("SELECT * FROM student WHERE student_name=? ");

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

	// -------------------------------------------------------------------------------------------------------------------------------

	// <학년 입력> 학생 학년으로 모두 조회
	public ArrayList<ClassNoteVO> selectAllByGrade(int studentGrade) {

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

	// -------------------------------------------------------------------------------------------------------------------------------

	// <분반 입력> 학생 반으로 모두조회
	public ArrayList<ClassNoteVO> selectByLectureClass(String lectureClass) {

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
				
				
				//System.out.println(list);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// -------------------------------------------------------------------------------------------------------------------------------

	// <강의명 입력>
	public ArrayList<ClassNoteVO> selectByLectureName(String lectureName) {

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

	// -------------------------------------------------------------------------------------------------------------------------------

	// 학년+분반
	public ArrayList<ClassNoteVO> selectByGradeLectureClass(int studentGrade, String lectureClass) {

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

	// -------------------------------------------------------------------------------------------------------------------------------

	// 학년+강의명
	public ArrayList<ClassNoteVO> selectByGradeLectureName(int studentGrade, String lectureName) {

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

	// -------------------------------------------------------------------------------------------------------------------------------

	// 분반+강의명
	public ArrayList<ClassNoteVO> selectByLectureClassLectureName(String lectureClass, String lectureName) {

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
		public ArrayList<ClassNoteVO> selectByGradeLectureClassLectureName(int studentGrade, String lectureClass, String lectureName) {

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

		
		
		// -------------------------------------------------------------------------------------------------------------------------------

		// 이름값을 넘겨주고 detail.jsp에 
		public ArrayList<ClassNoteVO> selectAll(String studentName) {

			ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

			ClassNoteVO vo = null;

			// 4. sql문 작성 (조인)
			sb.setLength(0);
//			sb.append("SELECT * FROM student "); //조인안될때 예비로 쓴 쿼리문

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

//					조장님이 주신 ClassNoteVO 사용하려면 set 해줘야함

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
		
		// -------------------------------------------------------------------------------------------------------------------------------

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
		
		public ArrayList<ClassNoteVO> teacherSelectAll(){
			ArrayList<ClassNoteVO> list = new ArrayList<>();
			ClassNoteVO vo = null;
			
			sb.setLength(0);
			sb.append("select * from teacher ");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					vo = new ClassNoteVO();
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
		
		public ArrayList<ClassNoteVO> teacherSelectAll(String date1, String date2){
			ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();
			ClassNoteVO vo = null;
			
			sb.setLength(0);
			sb.append( "SELECT * FROM TEACHER_CHECK WHERE TO_CHAR(TEACHER_CHECK_DATE, 'YYYY-MM-DD') BETWEEN ? AND ? " );
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, date1);
				pstmt.setString(2, date2);
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
		public void checkInTeacher(ClassNoteVO vo) {
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
		public void checkOutTeacher(ClassNoteVO vo) {
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
		
		public void workTimeTeacher(ClassNoteVO vo) {
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
