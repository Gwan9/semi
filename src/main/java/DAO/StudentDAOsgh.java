package DAO;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import VO.ClassNoteVO;

public class StudentDAOsgh {

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
	
	public StudentDAOsgh() {
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
	
	// sgh-------------------------------------------------------------------------------------------------------------------------------
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

				if(rs.getString("teacher_gender") == "1")
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

	public ArrayList<ClassNoteVO> studentSearchSelectAll() {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);
		//sb.append("SELECT * FROM student "); //조인안될때 예비로 쓴 쿼리문

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
				vo.setLectureName(rs.getString("lecture_name"));
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
	} 
	public boolean studentCheckIsExist(String date) {
		boolean exist = false;
		
			 sb.setLength(0);
             sb.append("SELECT student_no ");
             sb.append("FROM student_check "); 
             sb.append("WHERE student_check_date = ? "); 

             try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, date);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					exist = true;
				}
             } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
             }
	            // 예외 처리 코드 작성
	        

	        return exist;
	    }
	public boolean teacherCheckIsExist(String date) {
		boolean exist = false;
		
		 sb.setLength(0);
         sb.append("SELECT teacher_check_no ");
         sb.append("FROM teacher_check "); 
         sb.append("WHERE teacher_check_date = ? "); 
         try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				exist = true;
			}
         } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
         }
	            // 예외 처리 코드 작성
	        

	        return exist;
	    }
	
	public void studentCheckInsertAll() {
		sb.setLength(0);
		sb.append("INSERT INTO STUDENT_CHECK ");
		sb.append("(STUDENT_CHECK_NO, STUDENT_CHECK_DATE, STUDENT_NO, STUDENT_CHECK_STATUS, STUDENT_CHECK_TYPE) ");
		sb.append("SELECT STUDENT_CHECK_NO_SEQ.nextval, TRUNC(SYSDATE), STUDENT_NO, NULL, 0 ");
		sb.append("FROM STUDENT ");
		sb.append("WHERE ROWNUM <= (SELECT COUNT(STUDENT_NO) FROM STUDENT)");
		
		
	    try {
	        pstmt = conn.prepareStatement(sb.toString());
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        // 예외 처리
	        e.printStackTrace();
	    }
	}
	
	public void teacherCheckInsertAll() {
		
		sb.setLength(0);
		sb.append("INSERT INTO teacher_check ");
		sb.append("(TEACHER_CHECK_NO, TEACHER_NO, TEACHER_CHECK_DATE) ");
		sb.append("SELECT TEACHER_CHECK_NO_SEQ.nextval, TEACHER_NO, TRUNC(SYSDATE) ");
		sb.append("FROM TEACHER ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	public ArrayList<ClassNoteVO> studentCheeckSelectAll() {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);
		//sb.append("SELECT * FROM student "); //조인안될때 예비로 쓴 쿼리문

		// 아니 디비에서는 되는데 대체 뭐가 문제야 ==> 음... 이름값을 받아오는 selectName() 에서 AND student_name=?
		// 이 쿼리문을 빼먹어서
		sb.append("SELECT * ");
        sb.append("FROM student s, class_register c, lecture l, student_check sc ");
        sb.append("WHERE sc.student_no = s.student_no ");
        sb.append("AND s.student_no = c.student_no ");
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
				vo.setLectureName(rs.getString("lecture_name"));
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
				vo.setStudentCheckStatus(rs.getString("student_check_status"));
				vo.setStudentCheckDate(rs.getString("student_check_date"));
				vo.setStudentCheckType(rs.getInt("student_check_type"));
				

				list.add(vo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	} // selectAll() 끝
	public ArrayList<ClassNoteVO> teacherCheckSelectAll(){
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;
		
		sb.setLength(0);
		sb.append("select * from teacher t, teacher_check tc ");
		sb.append("where t.teacher_no = tc.teacher_no ");
		
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
				vo.setTeacherSubject(rs.getString("teacher_subject"));
				vo.setTeacherSal(rs.getInt("teacher_sal"));
				vo.setTeacherPhone(rs.getString("teacher_phone"));
				vo.setTeacherWorktype(rs.getString("teacher_worktype"));
				vo.setTeacherCheckIn(rs.getString("teacher_check_in"));
				vo.setTeacherCheckOut(rs.getString("teacher_check_out"));
				vo.setTeacherWorkTime(rs.getString("teacher_work_time"));
				vo.setTeacherCheckNo(rs.getInt("teacher_check_no"));
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
	public ArrayList<ClassNoteVO> studentCheckUpdateByTypeByNo(int studentCheckType1, int studentNo1, int studentNo2,int studentCheckType2 ) {
	    ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();
	    ClassNoteVO vo = null;
	    sb.setLength(0);
	    sb.append("update student_check ");
	    sb.append("set student_check_type = ? ");
	    sb.append("WHERE student_no = ? ");
	    sb.append("UPDATE student_check ");
	    sb.append("SET student_check_status = ");
	    sb.append("CASE ");
	    sb.append("WHEN student_check_type = 1 THEN '등교' ");
	    sb.append("WHEN student_check_type = 2 THEN '지각' ");
	    sb.append("WHEN student_check_type = 3 THEN '조퇴' ");
	    sb.append("WHEN student_check_type = 4 THEN '결석' ");
	    sb.append("WHEN student_check_type = 0 THEN '미정' ");
	    sb.append("ELSE student_check_status ");
	    sb.append("END WHERE student_no = ? ");
	    sb.append("and student_check_type = ? ");
	    
	    try {
	        pstmt = conn.prepareStatement(sb.toString());
	        pstmt.setInt(1, studentCheckType1);
	        pstmt.setInt(2, studentNo1);
	        pstmt.setInt(3, studentNo2);
	        pstmt.setInt(4, studentCheckType2);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return list;
	}
	public ArrayList<ClassNoteVO> teacherCheckSelectAllByName(String teacherName) {

		// vo 초기화
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = new ClassNoteVO();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("SELECT * ");
		sb.append("FROM TEACHER t, teacher_check tc ");
		sb.append("WHERE t.teacher_no = tc.teacher_no ");
		sb.append("AND t.TEACHER_NAME = ? ");

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
				vo.setTeacherCheckIn(rs.getString("teacher_check_in"));
				vo.setTeacherCheckOut(rs.getString("teacher_check_out"));
				vo.setTeacherWorkTime(rs.getString("teacher_work_time"));

				list.add(vo);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	public void teacherCheckInUpdateByName(ClassNoteVO vo) {
		sb.setLength(0);
		sb.append( "UPDATE TEACHER_CHECK " );
		sb.append( "SET TEACHER_CHECK_IN = SYSDATE " );
		sb.append( "WHERE TEACHER_NO = ( " );
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
	public void teacherCheckOutUpdateByName(ClassNoteVO vo) {
		sb.setLength(0);
		sb.append( "UPDATE TEACHER_CHECK " );
		sb.append( "SET TEACHER_CHECK_OUT = SYSDATE " );
		sb.append( "WHERE TEACHER_NO = ( " );
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
	public ArrayList<ClassNoteVO> studentCheckSelectAllByDate1ToDate2(String date1, String date2){
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
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setStudentPhone(rs.getString("student_phone"));
				vo.setStudentParentsPhone(rs.getString("student_parents_phone"));
			    vo.setStudentCheckNo(rs.getInt("student_check_no"));
			    vo.setStudentCheckDate(rs.getString("student_check_date"));
			    vo.setStudentCheckStatus(rs.getString("student_check_status"));
			    vo.setStudentCheckStatus(rs.getString("student_check_type"));
			    
			    list.add(vo);
			    System.out.println("LIST " + list);
			}
	
		}catch (SQLException e) {			
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	return list;
	}	
	public ArrayList<ClassNoteVO> teacherCheckSelectAllByDate1ToDate2(String date1, String date2){
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
	
	public ArrayList<ClassNoteVO> studentCheckSelectAllByNo(int no) {

		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();

		ClassNoteVO vo = null;

		// 4. sql문 작성 (조인)
		sb.setLength(0);
		//sb.append("SELECT * FROM student "); //조인안될때 예비로 쓴 쿼리문

		// 아니 디비에서는 되는데 대체 뭐가 문제야 ==> 음... 이름값을 받아오는 selectName() 에서 AND student_name=?
		// 이 쿼리문을 빼먹어서
		sb.append("SELECT * ");
        sb.append("FROM student s, class_register c, lecture l, student_check sc ");
        sb.append("WHERE sc.student_no = s.student_no ");
        sb.append("AND s.student_no = c.student_no ");
        sb.append("AND c.lecture_no = l.lecture_no ");
        sb.append("AND sc.student_no = ? ");


        try {
    		pstmt = conn.prepareStatement(sb.toString());
    		pstmt.setInt(1, no);
    		rs = pstmt.executeQuery();
    		while ( rs.next() ) {
    			vo = new ClassNoteVO();
				vo.setStudentNo(rs.getInt("student_no"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentSchoolName(rs.getString("student_school_name"));
				vo.setStudentGrade(rs.getInt("student_grade"));
				vo.setLectureClass(rs.getString("lecture_class"));
				vo.setLectureName(rs.getString("lecture_name"));
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
				vo.setStudentCheckStatus(rs.getString("student_check_status"));
				vo.setStudentCheckDate(rs.getString("student_check_date"));
				vo.setStudentCheckType(rs.getInt("student_check_type"));
				

				list.add(vo);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	} // sele
	public void studentCheckUpdate(ClassNoteVO vo) {
    	sb.setLength(0);
	    sb.append("update student_check ");
	    sb.append("set student_check_type = ? ");
	    sb.append("WHERE student_no = ? ");
	    sb.append("UPDATE student_check ");
	    sb.append("SET student_check_status = ");
	    sb.append("CASE ");
	    sb.append("WHEN student_check_type = 1 THEN '등교' ");
	    sb.append("WHEN student_check_type = 2 THEN '지각' ");
	    sb.append("WHEN student_check_type = 3 THEN '조퇴' ");
	    sb.append("WHEN student_check_type = 4 THEN '결석' ");
	    sb.append("WHEN student_check_type = 0 THEN '미정' ");
	    sb.append("ELSE student_check_status ");
	    sb.append("END WHERE student_no = ? ");
	    sb.append("and student_check_type = ? ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getStudentNo() );
			pstmt.setInt(2, vo.getStudentCheckType() );
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void teacherWorkTimeUpdateByName(ClassNoteVO vo) {
		sb.setLength(0);
		sb.append( "UPDATE TEACHER " );
		sb.append( "SET TEACHER_WORKING_TIME = ( " );
		sb.append( "SELECT TEACHER_CHECK_OUT - TEACHER_CHECK_IN " );
		sb.append( "FROM TEACHER_CHECK tc " );
		sb.append( "WHERE tc.TEACHER_NO = TEACHER.TEACHER_NO) " );
		sb.append( "WHERE TEACHER_NAME = ? ; " );
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getTeacherCheckNo() );
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block2
			e.printStackTrace();
		}
	}
	
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

