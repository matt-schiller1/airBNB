<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dashboard</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container">
<div class="row">
  <div class="col-md-4">
  <p align="right"><a href="/${currentUser.id}/edit">edit</a></p>
    <h3 align="center">Welcome <c:out value="${currentUser.username}" />!</h3>
    <p align="center"><img src="${currentUser.pic}" height="50" width="50"></p>
    <p align="center">
    <c:out value="${whoFollowing}"/> Following | <c:out value="${followers}"/> Followers</p>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" class="btn btn-link" />
    </form>
    

    <hr>
    <table>
    
    <c:forEach var="user" items="${allUsers}">
	<tr>
	
	<c:choose>
		<c:when test="${user.id == currentUser.id}">

	 	</c:when> 
		<c:otherwise>
		<c:out value="${user.name}"/>
		<c:choose>
				<c:when test="${ currentUser.followings.contains(user) }">
					<a href="/transmit/${user.id}/unfollow">Unfollow</a><br>
				</c:when>
				<c:otherwise>
					<a href="/transmit/${user.id}/follow">Follow</a><br>						
				</c:otherwise>
		</c:choose>
		</c:otherwise>
	</c:choose>
		</td>
	</tr>
	
	</c:forEach>
	</table>
	</div>
	<div class="col-md-4">
	<form:form method="POST" action="/post" modelAttribute="newTransmit">
		<form:hidden path="user" value="${currentUser.id}" />
       <p>
           <form:textarea path="content" class="form-control"/>
       </p>
       <input type="submit" value="Transmit!" class="btn btn-primary">
</form:form>
    <hr>
	<table width=100%>
    <c:forEach var="post" items="${posts}">
	<tr>
	<td>
	<hr>
	<p><img src="${post.user.pic}" height="50" width="50"/><c:out value="${post.user.name}"/><strong><small>(@<c:out value="${post.user.username}"/>)</small></strong></p><c:out value="${post.content}" escapeXml="false"/>
	<hr>
	<c:choose>
		<c:when test="${post.users.contains(currentUser) }">
			<c:out value="${post.upvote}"/> <a href="/transmit/${post.id}/undo/u">Undo</a> | <c:out value="${post.downvote}"/> downvote - Score: ${post.upvote + post.downvote} 
		</c:when>
	<c:otherwise>
	<c:out value="${post.upvote}"/> <a href="/transmit/${post.id}/upvote/u">upvote</a> | <c:out value="${post.downvote}"/> <a href="/transmit/${post.id}/downvote/u">downvote</a> - Score: ${post.upvote + post.downvote}
	</c:otherwise>
	</c:choose>
	</td>
	</tr>
	</c:forEach>
	</table>
	</div>
	</div>
	<div class="col-md-4">
	</div>
</div>
</body>
</html>