<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	
// 	System.out.println("시작 날짜" + registDateStart);
// 	System.out.println("종료 날짜" + registDateEnd);
	

	JSONArray accounting = new JSONArray();
	StudentDAO dao = new StudentDAO();
	
	ArrayList<ClassNoteVO> list = dao.accountingSelectByDate(startDate, endDate);
	
	
	for(ClassNoteVO vo : list) {
		JSONObject money = new JSONObject();
		
		money.put("registerno", vo.getClass_registerNo());
		money.put("lectureClass", vo.getLectureClass());
		money.put("studentName", vo.getStudentName());
		money.put("isPay", vo.isPay());
		money.put("lectureName", vo.getLectureName());
		money.put("lectureTuition", vo.getLectureTuition());
		money.put("payType", vo.getPayType());
		money.put("lectureStartDate", vo.getLectureStartDate());
		money.put("lectureEndDate", vo.getLectureEndDate());
		money.put("studentDueDate", vo.getStudentDueDate());
		money.put("studentParentsPhone", vo.getStudentParentsPhone());
		
		accounting.add(money);
		
	}
	System.out.println("SearchTeacherDateOK에서 온 값 : " + startDate);
	System.out.println("SearchTeacherDateOK에서 온 값 : " + endDate);
	out.println(accounting);


%>