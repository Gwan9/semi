<%@page import="VO.CwkVO"%>
<%@page import="DAO.CwkDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	JSONArray JA = new JSONArray();
	
		CwkDAO dao = new CwkDAO();
	
		ArrayList<CwkVO> list = dao.studentSelectAll();
	
		for(CwkVO vo : list){
	JSONObject student = new JSONObject();
		
	student.put("no", vo.getStudentNo());
	student.put("name", vo.getStudentName());
	student.put("grade", vo.getStudentGrade());
	student.put("phone", vo.getStudentPhone());
	student.put("regist_date", vo.getStudentRegistDate());
	student.put("parents_name", vo.getStudentParentsName());
	student.put("parents_phone", vo.getStudentParentsPhone());
	student.put("due_date", vo.getStudentDueDate());
	student.put("photo", vo.getStudentPhoto());
	student.put("gender", vo.isStudentGender());
	student.put("birth", vo.getStudentBirth());
	student.put("addrs", vo.getStudentAddrs());
	student.put("email", vo.getStudentEmail());
	student.put("school_name", vo.getStudentSchoolName());
	student.put("status", vo.isStudentStatus());
	
	JA.add(student);
		}
	
	out.println(JA.toJSONString());
%>