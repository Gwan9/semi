<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

String selectStudentList = request.getParameter("selectStudentList");
String date = request.getParameter("date");
String lectureNameText = request.getParameter("lectureNameText");
String lectureClassText = request.getParameter("lectureClassText");
String teachername = request.getParameter("teachername");
String notetitle = request.getParameter("notetitle");
String tarea = request.getParameter("tarea");

StudentDAO dao = new StudentDAO();

dao.studentNoteInsert(notetitle, tarea, teachername);

%>