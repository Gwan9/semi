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
//	String url = "jdbc:oracle:thin:@192.168.0.26:1521:orcl"; // CWK
	String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // localhost
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
	

	public int getTotalCount() {
		int cnt = 0;
		
		sb.setLength(0);
		sb.append("select count(*) cnt from class_note ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			rs.next();
			cnt = rs.getInt("cnt");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return cnt;
	}
	
	public void studentNoteInsert(String title, String tarea, String tname) {
		
//		로그인 되어있는 교사의 교사번호를 담은 무언가
		
		sb.setLength(0);
		sb.append("insert into class_note values(note_no_seq.nextval, sysdate, ?, ?, (select teacher_no from teacher where teacher_name = ? )) ");
		
		
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
	public ArrayList<ClassNoteVO> studentNoteSelectAll() {
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = new ClassNoteVO();
		
		sb.setLength(0);
		sb.append("select note_no, note_date, note_title, note_contents, teacher_no from class_note ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo.setNoteNo(rs.getInt("note_no"));
				vo.setNoteDate(rs.getString("note_date"));
				vo.setNoteTitle(rs.getString("note_title"));
				vo.setNoteContents(rs.getString("note_contests"));
				vo.setTeacherNo(rs.getInt("teacher_no"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
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
	    sb.append("SELECT note_date, note_title, note_contents, t.teacher_name, r.class_register_no, s.student_name, l.lecture_name, l.lecture_class ");
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

	
	public ArrayList<ClassNoteVO> studentNoteSelectAll(int startno, int endno){
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;
		
		sb.setLength(0);
		sb.append("select rn, note_no, note_date, note_title, note_contents, class_register_no "
				+ " from (select rownum rn, note_no, note_date, note_title, note_contents, class_register_no "
				+ " from (select note_no, note_date, note_title, note_contents, class_register_no from class_note order by note_no desc) "
				+ " where rownum <= ?) "
				+ " where rn >= ? " );
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, endno);
			pstmt.setInt(2, startno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new ClassNoteVO();
				
				vo.setNoteNo(rs.getInt("note_no"));
				vo.setNoteDate(rs.getString("note_date"));
				vo.setNoteTitle(rs.getString("note_title"));
				vo.setNoteContents(rs.getString("note_contents"));
				vo.setClassRegisterNo(rs.getInt("class_register_no"));
				
				list.add(vo);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	


	public ArrayList<ClassNoteVO> studentNoteSelectAll(String lectureName){
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;
		
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
				vo = new ClassNoteVO();
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
	
	public ArrayList<ClassNoteVO> studentNoteSelectAll(String lectureName, String lectureClass){
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;
		
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
				vo = new ClassNoteVO();
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
	
	// lecture-------------------------------------------------------------------------------------------------------------------------------
	public ArrayList<ClassNoteVO> lectureSelectAll(){
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;
		
		sb.setLength(0);
		sb.append("select * from lecture ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
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
	
	// 학생 목록 출력
	// student-------------------------------------------------------------------------------------------------------------------------------
	public ArrayList<ClassNoteVO> studentSelectAll(){
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;
		
		sb.setLength(0);
		sb.append("select * from student ");
		
		try {
			
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
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
	public ArrayList<ClassNoteVO> studentSelectAllByName(String stdName){
		ArrayList<ClassNoteVO> list = new ArrayList<>();
		ClassNoteVO vo = null;
		
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
	
			

	// studentCheck-------------------------------------------------------------------------------------------------------------------------------
		// student_check - student 조인해서 STUDENT_CHECK_DATE 값으로 전체 조회
	public ArrayList<ClassNoteVO> studentCheckSelectAllByDate(String date){
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
	// STUDENT_CHECK - STUDENT 조인하고 시작날짜 끝 날짜 값으로 
	public ArrayList<ClassNoteVO> studenCheckSelectAllByDate1Date2(String date1, String date2){
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
	
	// sgh 변경 확인 필요
		public void studentCheckInsertAll() {
			sb.setLength(0);
			sb.append( "INSERT INTO student_check" );
			sb.append( "SELECT STUDENT_CHECK_NO_SEQ.nextval, null, student_no, to_date(to_char(sysdate, 'YYYY-MM-dd'),'YYYY-MM-dd')" );
			sb.append( "FROM student" );
			sb.append( "WHERE ROWNUM <= (SELECT COUNT(student_no) FROM student);" );
			
			try {
		        pstmt = conn.prepareStatement(sb.toString());
		        pstmt.executeUpdate();
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// sgh 구현확인필요
		public boolean studentCheckIsExist() {
			boolean exist = false;
			
				
	             sb.append("SELECT COUNT(student_no) ");
	             sb.append("FROM student_check "); 
	             sb.append("WHERE (SELECT student_check_date FROM student_check WHERE student_check_date = sysdate) IS NULL; "); 

	             try {
					pstmt = conn.prepareStatement(sb.toString());
					rs = pstmt.executeQuery();
					ResultSet rs = pstmt.executeQuery();
					
					if (rs.next()) {
						int count = rs.getInt(1);
						exist = count > 0;
					}
	             } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	             }
		            // 예외 처리 코드 작성
		        

		        return exist;
		    }
		
			

	// studentSearch-------------------------------------------------------------------------------------------------------------------------------
	
		// 이름값을 넘겨주고 detail.jsp에 
	public ArrayList<ClassNoteVO> studentSearchSelectAllByNameToDetail(String studentName) {
		ArrayList<ClassNoteVO> list = new ArrayList<ClassNoteVO>();
		ClassNoteVO vo = null;

		sb.setLength(0);
		//s b.append("SELECT * FROM student "); //조인안될때 예비로 쓴 쿼리문

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
		//	sb.append("SELECT * FROM student WHERE student_name=? ");

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
				
				
				//System.out.println(list);
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
	public ArrayList<ClassNoteVO> studentSearchSelectByLectureClassLectureName(String lectureClass, String lectureName) {

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
	public ArrayList<ClassNoteVO> studentSearchSelectByGradeLectureClassLectureName(int studentGrade, String lectureClass, String lectureName) {

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
	// teacher-------------------------------------------------------------------------------------------------------------------------------
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


	public ClassNoteVO teacherSelectAllByNo(int teacherNo) {

		// vo 초기화
		ClassNoteVO vo = new ClassNoteVO();

		// 4. SQL 문
		sb.setLength(0); // 초기화
		sb.append("SELECT TEACHER_NO, TEACHER_ID, TEACHER_PW, TEACHER_NAME, TEACHER_PHONE, TEACHER_EMAIL, TEACHER_PHOTO, TEACHER_HIREDATE, TEACHER_ADDRESS, TEACHER_SAL, TEACHER_SUBJECT, TEACHER_WORKTYPE, TEACHER_BIRTH, TEACHER_GENDER ");
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
	public ArrayList<ClassNoteVO> teacherSelectAllByDate(String date){
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
	public ArrayList<ClassNoteVO> teacherCheckSelectAllByDate1toDate2(String date1, String date2){
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

	public void teacherInsertAll(ClassNoteVO vo) {

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

	

	// 사람을 특정하여 조회하기 위한 메서드
	
	public ArrayList<ClassNoteVO> teacherSelectAllByName(String teacherName) {

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

	public void teacherDeleteByNo(int teacherNo) {

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
		
	public void teacherInsertByAll(ClassNoteVO vo) {
			
		sb.setLength(0);
		sb.append("INSERT INTO teacher ");
		sb.append("VALUES (TEACHER_NO_SEQ.NEXTVAL, ?, ?, ?, ?, 0, ?, ?, 0, ?, 0, SYSDATE, ?, ?) ");
		
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
	
	public ClassNoteVO teacherSelectAllByIdPw(String teacherId, String teacherPw) {
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
				
				vo = new ClassNoteVO(teacherNo, teacherId, teacherPw, teacherName, teacherAddress
						, teacherSal, teacherPhone, teacherEmail, teacherSubject, teacherPhoto
						, teacherWorktype, teacherHiredate, teacherBirth, g);
				
				System.out.println("dao:"+vo);
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

	// ------------------------------------------------------------------------------

	// 회계 프로그램 월별 조회하기

	public ArrayList<ClassNoteVO> teacherSelectAllAccounting() {

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
		
	// teacherCheck-------------------------------------------------------------------------------------------------------------------------------
	
	// check.jsp 출결확인페이지 오늘날짜 출결 조회
	public ArrayList<ClassNoteVO> teacherCheckSelectAllbyDate(String date){
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
	
	public ArrayList<ClassNoteVO> teacherCheckSelectAll(){
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
	//sgh sql 문 teacher_no부분 확인필요
	public void teacherCheckInsertAll() {
	
		sb.setLength(0);
		sb.append( "INSERT INTO teacher_check" );
		sb.append( "SELECT TEACHER_CHECK_NO_SEQ.nextval, null, null, null, TEACHER_NO ,  to_date(to_char(sysdate, 'YYYY-MM-dd'),'YYYY-MM-dd') ) " );
		sb.append( "FROM teacher " );
		sb.append( "WHERE ROWNUM <= (SELECT COUNT(teacher_no) FROM teacher); " );
		//seq 확인
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	// sgh 구현확인필요
	public boolean teacherCheckIsExist() {
		boolean exist = false;
		
			
             sb.append("SELECT COUNT(teacher_no) ");
             sb.append("FROM teacher_check "); 
             sb.append("WHERE (SELECT teacher_check_date FROM teacher_check WHERE teacher_check_date = sysdate) IS NULL; "); 
             try {
				pstmt = conn.prepareStatement(sb.toString());
				rs = pstmt.executeQuery();
				ResultSet rs = pstmt.executeQuery();
				
				if (rs.next()) {
					int count = rs.getInt(1);
					exist = count > 0;
				}
             } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
             }
	            // 예외 처리 코드 작성
	        

	        return exist;
	    }
	//sgh 구현 확인 예정
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
	//sgh 구현 확인 예정
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
	//sgh 일한시간 sql문 확인필요
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
	
	
		// -----------------------------------------------------------------------

		// 수강 기간을 특정하여 조회하기 위한 메서드
		public ArrayList<ClassNoteVO> selectAtoB(String startDate, String endDate) {

			return null;
		}

		
		
		
		// -----------------------------------------------------------------------
	
		
		//내 dao 추가
				public ArrayList<ClassNoteVO> studentSelectAllByRegistDate(String date1, String date2){
					ArrayList<ClassNoteVO> list = new ArrayList<>();
					ClassNoteVO vo = null;
			
					sb.setLength(0);
					sb.append("SELECT s.student_no, s.student_name, s.student_school_name, s.student_grade, ");
					sb.append("l.lecture_class , s.student_phone, s.student_regist_date, s.student_gender, ");
					sb.append("s.student_parents_name, s.student_parents_phone ");
					sb.append("FROM student s, class_register c, lecture l ");
					sb.append("WHERE s.student_no = c.student_no ");
					sb.append("AND c.lecture_no = l.lecture_no ");
					sb.append("AND TO_CHAR(student_regist_date, 'YYYY-MM-DD') "); 
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



}
