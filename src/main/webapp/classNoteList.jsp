<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	JSONArray JA = new JSONArray();
	StudentDAO dao = new StudentDAO();
	ArrayList<ClassNoteVO> list = null;
	

	String lectureName = request.getParameter("lectureName").trim();
	String lectureClass = request.getParameter("lectureClass").trim();
	
	if(lectureName.equals("전체") && lectureClass.equals("전체")){
		list = new ArrayList<ClassNoteVO>();
		list = dao.studentNoteSelectAll();
		
		System.out.println(list);
		
	}else if(!lectureName.equals("전체") && lectureClass.equals("전체")){
		list = new ArrayList<ClassNoteVO>();
		list = dao.studentNoteSelectAll(lectureName);
		
	}else if(!lectureName.equals("전체") && !lectureClass.equals("전체")){
		list = new ArrayList<ClassNoteVO>();
		list = dao.studentNoteSelectAll(lectureName, lectureClass);
		
	}else if(lectureName.equals("전체") && !lectureClass.equals("전체")){
		list = new ArrayList<ClassNoteVO>();
		list = dao.studentNoteSelectAllbyLectureClass(lectureClass);
		
	}
	
	for(ClassNoteVO vo : list){
		JSONObject notelist = new JSONObject();
		
		notelist.put("noteno", vo.getNoteNo());
		notelist.put("notedate", vo.getNoteDate());
		notelist.put("notetitle",vo.getNoteTitle());
		notelist.put("studentname", vo.getStudentName());
		notelist.put("lecturename", vo.getLectureName());
		notelist.put("lectureclass", vo.getLectureClass());
		notelist.put("teachername", vo.getTeacherName());

		JA.add(notelist);
	}

	out.println(JA.toJSONString());
%>
