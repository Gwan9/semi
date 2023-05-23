
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
//search.jsp

//1. 파라미터 값 가져오기
String studentName = request.getParameter("studentName").trim();
String grade = request.getParameter("studentGrade").trim();
String lectureClass = request.getParameter("lectureClass").trim();
String lectureName = request.getParameter("lectureName").trim();

//파라미터 잘 가져왔는지 확인
/* System.out.println(lectureClass);
System.out.println(lectureName);
System.out.println(grade);
 */
//-------------------------------------------------------------------------------
//JSONArray 
JSONArray j = new JSONArray();

//dao 생성
StudentDAO dao = new StudentDAO();

//db로부터 모든 데이터 가져오기
//리스트 변수 선언
ArrayList<ClassNoteVO> list;

//-------------------------------------------------------------------------------

//이름만
if (studentName != null && !studentName.isEmpty()) {
	list = dao.studentSearchSelectAllByName(studentName);
}

//학년만
else if (!"전체".equals(grade) && "전체".equals(lectureClass) && "전체".equals(lectureClass))  {
	int studentGrade = Integer.parseInt(grade);
	list = dao.studentSearchSelectAllByGrade(studentGrade);
		
}
//분반만
else if (!"전체".equals(lectureClass) && "전체".equals(grade) && "전체".equals(lectureName)) {
	list = dao.studentSearchSelectByLectureClass(lectureClass);
	System.out.println("분반: " + list);
}

//강의명만
else if (!"전체".equals(lectureName) && "전체".equals(grade) && "전체".equals(lectureClass)) {
	list = dao.studentSearchSelectByLectureName(lectureName);
} 

//학년+분반
else if (!"전체".equals(grade)&& !"전체".equals(lectureClass) && "전체".equals(lectureName)) {
	int studentGrade = Integer.parseInt(grade);
	list = dao.studentSearchSelectByGradeLectureClass(studentGrade, lectureClass);
} 

//학년+강의명
else if (!"전체".equals(grade)&& "전체".equals(lectureClass) && !"전체".equals(lectureName)) {
	int studentGrade = Integer.parseInt(grade);
	list = dao.studentSearchSelectByGradeLectureName(studentGrade, lectureName);
} 

//분반+강의명
else if ("전체".equals(grade)&& !"전체".equals(lectureClass) && !"전체".equals(lectureName)) {
	list = dao.studentSearchSelectByLectureClassLectureName(lectureClass, lectureName);
}

//학년 + 분반 + 강의명
else if (!"전체".equals(grade)&& !"전체".equals(lectureClass) && !"전체".equals(lectureName)) {
	int studentGrade = Integer.parseInt(grade);
	list = dao.studentSearchSelectByGradeLectureClassLectureName(studentGrade, lectureClass, lectureName);
} 

//아니라면 그냥 기본 빈 리스트를 생성
else {
	list = new ArrayList<>();
	System.out.println(list);
}

for (ClassNoteVO vo : list) {

	JSONObject student = new JSONObject();

	student.put("studentNo", vo.getStudentNo());
	student.put("studentName", vo.getStudentName());
	student.put("studentSchoolName", vo.getStudentSchoolName());
	student.put("studentGrade", vo.getStudentGrade());
	student.put("lectureClass", vo.getLectureClass());
	student.put("lectureName", vo.getLectureName());
	student.put("studentPhone", vo.getStudentPhone());
	student.put("studentRegistDate", vo.getStudentRegistDate());
	student.put("studentGender", vo.isStudentGender());
	student.put("studentParentsName", vo.getStudentParentsName());
	student.put("studentParentsPhone", vo.getStudentParentsPhone());


	j.add(student);

}

out.println(j.toString());
%>


