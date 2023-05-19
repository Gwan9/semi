<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%


JSONArray JA = new JSONArray();


	StudentDAO dao = new StudentDAO();

	ArrayList<ClassNoteVO> list = dao.lectureSelectAll();

for(ClassNoteVO vo : list){
	JSONObject lecture = new JSONObject();

	lecture.put("lno", vo.getLectureNo());
	lecture.put("lname", vo.getLectureName());
	lecture.put("lclass", vo.getLectureClass());
	lecture.put("lstart", vo.getLectureStartDate());
	lecture.put("lend", vo.getLectureEndDate());
	lecture.put("ltution", vo.getLectureTuition());
	
	JA.add(lecture);
}

out.println(JA.toJSONString());
%>