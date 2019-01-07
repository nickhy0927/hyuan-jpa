<%@page import="com.baidu.ueditor.UeditorActionEnter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setHeader("Content-Type", "text/html");
	String rootPath = application.getRealPath("/");
	out.write(new UeditorActionEnter(request, rootPath).exec());
%>