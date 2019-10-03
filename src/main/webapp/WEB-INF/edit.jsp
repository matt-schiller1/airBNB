<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Information</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
	<div class="container">
	<p><form:errors path="editUser.*"/></p>
	<h4>Edit Details</h4>
	    <form:form method="POST" action="/updateinfo/u" modelAttribute="editUser" enctype="multipart/form-data">
	    <form:hidden path="id" value="${details.id}"/>
	    <input type="hidden" value="PUT" name="_method">
    	<p>
            <form:label path="name">Name:</form:label>
            <form:errors path="name"/>
            <form:input type="name" value="${details.name}" path="name" class="form-control" />
        </p>
        <p>
            <form:label path="username">Username:</form:label>
            <form:errors path="username"/>
            <form:input path="username" value="${details.username}" class="form-control" />
        </p>
        <p>
            <form:label path="bio">Bio:</form:label>
            <form:errors path="bio"/>
            <form:textarea path="bio" value="${details.bio}" class="form-control" />
        </p>
        <p>
			<input type="file" name="picture" /><br/>
        </p>
        <input type="submit" value="Update" class="btn btn-primary"/>
    </form:form>	
	</div>	
</body>
</html>