<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns:th="http://www.thymeleaf.org">
  <csrf disabled="true"/>

<div class="container">
	<div class="row">
		<form:form action="login" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
			<div class="form-row input-field">
				<label for="login">Login</label>
				<form:input path="login" size="30" class="form-control"
					placeholder="Enter login" />
				<label> <form:errors path="login" cssClass="error" />
				</label>
			</div>
			<div class="form-row input-field">
				<label for="password">Password</label> <input type="password"
					name="password" class="form-control" id="password"
					placeholder="Password"> <label> <form:errors
						path="password" cssClass="password" />
				</label>
			</div>

			<div class="form-row input-field">${error}${status}</div>


			<button type="submit" id="Rbtn" class="btn">Submit</button>
			<a href="<c:url value="/registration" />">Registration</a>
        </form:form>
	</div>
</div>
<%@ include file="jspf/footer.jspf"%>
<script>
    $("form").each(function() {
        $(this).validate({
          rules: { 
            login: {
              required: true,
              email: true
            }, 
            password: {
              required: true,
              minlength: 5
            }
          },
          messages: {
              login: {
              required: "Email не может быть пустым",
              email: "Неверный формат Email"
            },
            password: {
              required: "Пароль не может быть пустым",
              minlength: jQuery.validator.format("Введите не менее {0} символов")
            }
          }
        });
      }); 
    });
    </script>
</html>