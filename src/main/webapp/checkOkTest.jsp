<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//파라미터 값
String today = request.getParameter("today");
String selectVal = request.getParameter("selectVal");
String studentName = request.getParameter("studentName");
String lectureName = request.getParameter("lectureName");
String lectureClass = request.getParameter("lectureClass");
String teacherName = request.getParameter("teacherName");
//Null 체크
selectVal = (selectVal != null) ? selectVal.trim() : "";
studentName = (studentName != null) ? studentName.trim() : "";
lectureName = (lectureName != null) ? lectureName.trim() : "";
lectureClass = (lectureClass != null) ? lectureClass.trim() : "";
teacherName = (teacherName != null) ? teacherName.trim() : "";

if (Integer.parseInt(selectVal) == 1) {
JSONArray studentArray = new JSONArray();
StudentDAO dao1 = new StudentDAO();
ArrayList<ClassNoteVO> list;
if (studentName.isEmpty() || "학생명선택".equals(studentName)) {
    if ("반선택".equals(lectureClass) && "강의선택".equals(lectureName)) {
        list = dao1.studentCheckSelectAllByDate(today);
    } else if (!"반선택".equals(lectureClass) && "강의선택".equals(lectureName)) {
        list = dao1.studentCheckSelectByLectureClassByDate(today, lectureClass);
    } else if ("반선택".equals(lectureClass) && !"강의선택".equals(lectureName)) {
        list = dao1.studentCheckSelectByLectureNameByDate(today, lectureName);
    } else {
        list = dao1.studentCheckSelectByLectureNameByLectureClassByDate(today, lectureName, lectureClass);
    }
} else {
    list = dao1.studentSearchSelectAllByName(studentName);
}

for (ClassNoteVO vo : list) {
    JSONObject student = new JSONObject();
    student.put("studentNo", vo.getStudentNo());
    student.put("studentName", vo.getStudentName());
    student.put("studentSchoolName", vo.getStudentSchoolName());
    student.put("studentGrade", vo.getStudentGrade());
    student.put("lectureClass", vo.getLectureClass());
    student.put("studentPhone", vo.getStudentPhone());
    student.put("studentParentsPhone", vo.getStudentParentsPhone());
    student.put("studentCheckNo", vo.getStudentCheckNo());
    student.put("studentCheckLate", vo.getStudentCheckStatus());
    student.put("studentCheckDate", vo.getStudentCheckDate());

    studentArray.add(student);
}

out.println(studentArray.toJSONString());
System.out.println(studentArray.toJSONString());
} else if (Integer.parseInt(selectVal) == 2) {
JSONArray teacherArray = new JSONArray();
StudentDAO dao2 = new StudentDAO();
ArrayList<ClassNoteVO> list2 = dao2.teacherSelectAllByDate(today, teacherName);

for (ClassNoteVO vo : list2) {
    JSONObject teacher = new JSONObject();
    teacher.put("teacherNo", vo.getTeacherNo());
    teacher.put("teacherCheckIn", vo.getTeacherCheckIn());
    teacher.put("teacherCheckOut", vo.getTeacherCheckOut());
    teacher.put("teacherCheckWorkTime", vo.getTeacherWorkTime());

    teacherArray.add(teacher);
}

out.println(teacherArray.toJSONString());
System.out.println(teacherArray.toJSONString());
} 
else {
ArrayList<ClassNoteVO> list3 = new ArrayList<>();
System.out.println(list3);
}





%>