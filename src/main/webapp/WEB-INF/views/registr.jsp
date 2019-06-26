<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<%@ include file="jspf/head.jspf"%>
<script src='https://www.google.com/recaptcha/api.js?hl=ru'></script>
<body>
	<h1>Registration</h1>
	<form:form action="registration" method="POST" modelAttribute="user">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<table>
			<tr>
				<td><label> Login </label></td>
				<td><form:input type="text" path="login" id="login" /></td>
				<td align="left"><form:errors path="login" /></td>

			</tr>
			<tr>
				<td><label> Password </label></td>
				<td><form:input type="password" path="password" id="password" /></td>
				<td align="left"><form:errors path="password" /></td>
			</tr>
			<tr>
				<td><label> Password again </label></td>
				<td><form:input type="password" path="repassword"
						id="repassword" /></td>
				<td align="left"><form:errors path="repassword" /></td>
			</tr>
			<tr>
				<td><label> Email </label></td>
				<td><form:input type="email" path="email" id="email" /></td>
				<td align="left"><form:errors path="email" /></td>
			</tr>
			<tr>
				<td><label> First name </label></td>
				<td><form:input type="text" path="firstName" id="first_name" /></td>
				<td align="left"><form:errors path="firstName" /></td>
			</tr>
			<tr>
				<td><label> Last name </label></td>
				<td><form:input type="text" path="lastName" id="last_name" /></td>
				<td align="left"><form:errors path="lastName" /></td>
			</tr>
			<tr>
				<td><label> Birthday </label></td>
				<td><form:input type="date" path="birthday" id="birthday" /></td>
				<td align="left"><form:errors path="birthday" /></td>
			</tr>


		</table>
		<div class="form-row input-field">
			<c:choose>
				<c:when test="${not empty requestScope.errorMessage}">
					<h3>${requestScope.errorMessage}</h3>
				</c:when>
			</c:choose>
		</div>
		<form:input type="hidden" path="role" value="user" />
		<div class="form-group row">
			<div class="g-recaptcha"
				data-sitekey="6LegeagUAAAAAMg0wzCLyzeF7KeM4la-Fo7Mi8i0"></div>
			<p class="incorrect">${captchaError}</p>
		</div>
		<input type="submit" id="Rbtn" value="Ok">
		<button onclick="location.href='<c:url value="/home" />'"
			type="button">cancer</button>
	</form:form>


</body>
<%@ include file="jspf/footer.jspf"%>
<script>
	$(document).ready(function() {
		$("#Rbtn").click(function() {
			var login = $("#login").val();
			var password = $("#password").val();
			var repassword = $("#repassword").val();
			var email = $("#email").val();
			var firstName = $("#first_name").val();
			var lastName = $("#last_name").val();
			var birthday = $("#birthday").val();

			if (login == null || login == "") {
				alert("login cannot be Empty");
				return false;
			} else if (password == "" || password == null) {
				alert("Password cannot be Empty");
				return false;
			} else if (repassword == "" || repassword == null) {
				alert("Re-password cannot be Empty");
				return false;
			} else if (repassword !== password) {
				alert("Re-password and password should be equal");
				return false;
			} else if (email == "" || email == null) {
				alert("Email cannot be Empty");
				return false;

			} else if (firstName == "" || firstName == null) {
				alert("First name cannot be Empty");
				return false;
			} else if (lastName == "" || lastName == null) {
				alert("Last name cannot be Empty");
				return false;
			} else if (birthday == "" || birthday == null) {
				alert("Birthday cannot be Empty");
				return false;
			} else {
				$("#formid").submit();
				return true;
			}

		});
	});
</script>
</html>