<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transmittr</title>
<link rel="stylesheet" type="text/css" href="/css/normalize.css">
<link rel="stylesheet" type="text/css" href="/css/grid.css">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<link rel="stylesheet" type="text/css" href="/css/ionicons.min.css">

</head>
<body>
	<header>
    	<div class="atTheTop">
        	<div class="row">
                <p class="sig">Transmittr</p>
                <div class="login-container">
 <c:if test="${logoutMessage != null}">
        <c:out value="${logoutMessage}"></c:out>
    </c:if>
    <c:if test="${errorMessage != null}">
        <c:out value="${errorMessage}"></c:out>
    </c:if>
    <form method="POST" action="/login">

            <input type="text" id="username" placeholder="username" name="username"/>


            <input type="password" id="password" placeholder="password" name="password"/>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" class="button" value="Login!"/>
    </form>

                </div>                          
            </div>
            
        </div>
	<p><form:errors path="user.*"/></p>
<div class="span-1-of-3">
<div class="registerBox">
<h1>Register</h1>
    <form:form method="POST" action="/registration" modelAttribute="user">
        <p align="left">
            <form:label path="name">Name:</form:label><br>
            <form:input path="name" class="myBox"/>
        </p>
        <p align="left">
            <form:label path="username">Username:</form:label><br>
            <form:input path="username" class="myBox"/>
        </p>
        <p align="left">
            <form:label path="email">Email:</form:label><br>
            <form:input type="email" class="myBox"  path="email"/>
        </p>
        <p align="left">
            <form:label path="password">Password:</form:label><br>
            <form:password path="password" class="myBox" />
        </p>
        <p align="left">
            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
            <form:password path="passwordConfirmation" class="myBox"/>
        </p>
        <input type="submit" class="registerbtn" value="Register!"/>
    </form:form>
    </div>
</div>



</header>
</body>
</html>