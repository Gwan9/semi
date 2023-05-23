<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	String registDateStart = request.getParameter("registDateStart");
	String registDateEnd = request.getParameter("registDateEnd");
	
// 	System.out.println("시작 날짜" + registDateStart);
// 	System.out.println("종료 날짜" + registDateEnd);
	

	JSONArray teacher = new JSONArray();
	StudentDAO dao = new StudentDAO();
	
	ArrayList<ClassNoteVO> list = dao.teacherDateAtoB(registDateStart, registDateEnd);
	
	
	for(ClassNoteVO vo : list) {
		JSONObject teach = new JSONObject();
		
		teach.put("no", vo.getTeacherNo());
		teach.put("name", vo.getTeacherName());
		teach.put("id", vo.getTeacherId());
		teach.put("pw", vo.getTeacherPw());
		teach.put("phone", vo.getTeacherPhone());
		teach.put("subject", vo.getTeacherSubject());
		teach.put("lectureStartDate", vo.getLectureStartDate());
		teach.put("lectureEndDate", vo.getLectureEndDate());
		
		teacher.add(teach);
		
	}
	System.out.println("SearchTeacherDateOK에서 온 값 : " + registDateStart);
	System.out.println("SearchTeacherDateOK에서 온 값 : " + registDateEnd);
	out.println(teacher);


%>