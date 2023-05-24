
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="DAO.StudentDAO" %>
<%@ page import="VO.ClassNoteVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
String studentCheckType = request.getParameter("studentCheckType");
String nameValue = request.getParameter("nameValue");
ClassNoteVO vo = null;
if (studentCheckType != null && nameValue != null) {
    int sct = Integer.parseInt(studentCheckType);
    int no = Integer.parseInt(nameValue);
    JSONArray studentArray = new JSONArray();
    StudentDAO dao = new StudentDAO();
   		
    ArrayList<ClassNoteVO> list = dao.studentCheckSelectAllByNo(no);
        
        for (ClassNoteVO classNote : list) {
            JSONObject student = new JSONObject();
            vo = classNote;
    	    dao.studentCheckUpdate(vo);
    	    vo.setStudentNo(no);
    	    vo.setStudentCheckType(sct);
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
            student.put("studentchecktype", vo.getStudentCheckType());
            studentArray.add(student);
        }

    // ArrayList를 JSON 문자열로 변환하여 출력합니다.
    out.println(studentArray.toJSONString());
    System.out.println(studentArray.toJSONString());
    }

%>
