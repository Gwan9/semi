<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String teacherName = request.getParameter("teacherName");
ClassNoteVO vo = null;
if (teacherName != null) {
    JSONArray teacherArray = new JSONArray();
    StudentDAO dao = new StudentDAO();
    
    ArrayList<ClassNoteVO> list = dao.teacherCheckSelectAllByName(teacherName);
    
	
    for (ClassNoteVO classNote : list) {
        JSONObject teacher = new JSONObject();
        vo = classNote;
        dao.teacherCheckOutUpdateByName(vo);
        vo.setTeacherName(teacherName);
        teacher.put("teacherNo", classNote.getTeacherNo());
        teacher.put("teacherName", classNote.getTeacherName());
        teacher.put("teacherSubject", classNote.getTeacherSubject());
        teacher.put("teacherPhone", classNote.getTeacherPhone());
        teacher.put("teacherCheckIn", classNote.getTeacherCheckIn());
        teacher.put("teacherCheckOut", classNote.getTeacherCheckOut());
        teacher.put("teacherCheckDate", classNote.getTeacherCheckDate());
        teacherArray.add(teacher);
        
       //dao.worktime(vo)
	    
        teacher.put("teacherCheckWorkTime", classNote.getTeacherWorkTime());
	    
	    
    }
   	

    out.println(teacherArray.toJSONString());
    System.out.println(teacherArray.toJSONString());
}
%>


