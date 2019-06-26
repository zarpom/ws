<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="jspf/directive/taglib.jspf"%>
</head>
<%@ include file="jspf/header.jspf"%>
<a href=" <c:url value="/admin/add" />">Add new user</a>
<%@ taglib uri="usersTag" prefix="myTag"%>
<h1>HI ${currentUser.firstName } ${currentUser.lastName }</h1>
<myTag:printTable userList="${users}" />
<%@ include file="jspf/footer.jspf"%>
