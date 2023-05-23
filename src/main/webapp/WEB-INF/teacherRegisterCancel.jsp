<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
session.invalidate(); // 세션을 무효화

//메인 페이지로 리다이렉트
response.sendRedirect("main.jsp");
%>