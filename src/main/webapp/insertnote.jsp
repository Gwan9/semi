<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%

// 	전달된 데이터 가져오기
	String selectStudentList = request.getParameter("selectStudentList");
	String date = request.getParameter("date");
	String lectureNameText = request.getParameter("lectureNameText");
	String lectureClassText = request.getParameter("lectureClassText");
	String teachername = request.getParameter("teachername");
	String notetitle = request.getParameter("notetitle");
	String tarea = request.getParameter("tarea");

//   Oracle 연결 정보
//   dao 처리
  
//   String url = "jdbc:oracle:thin:@localhost:1521:XE";
//   String username = "your_username";
//   String password = "your_password";

//   Connection conn = null;
//   PreparedStatement pstmt = null;

//   try {
//     // Oracle 연결
//     Class.forName("oracle.jdbc.driver.OracleDriver");
//     conn = DriverManager.getConnection(url, username, password);

//     // SQL 문장 준비
//     String sql = "INSERT INTO class_note (student, date, lecture, class, teacher, title, content) VALUES (?, ?, ?, ?, ?, ?, ?)";
//     pstmt = conn.prepareStatement(sql);
//     pstmt.setString(1, selectStudentList);
//     pstmt.setString(2, date);
//     pstmt.setString(3, lectureNameText);
//     pstmt.setString(4, lectureClassText);
//     pstmt.setString(5, teachername);
//     pstmt.setString(6, notetitle);
//     pstmt.setString(7, tarea);

//     // SQL 실행
//     pstmt.executeUpdate();

    // 처리 완료 후 다른 페이지로 리디렉션
    response.sendRedirect("classNote.jsp");

  } catch (SQLException e) {
    e.printStackTrace();
  } catch (ClassNotFoundException e) {
    e.printStackTrace();
  }
%>