<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Details Page for Viewer</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body class="bg-secondary">
	<div class="container">
		<div class="d-flex justify-content-between mt-5 text-dark">
			<h2>Welcome, <c:out value="${user.firstName}"/>!</h2>
			<p><a href="/logout" class="btn btn-primary">Logout</a></p>
		</div>
		<hr>
		<div class="d-flex justify-content-around mt-5">
			<div>
				<form:form action="/books/${book.id}/edit" method="post" modelAttribute="book">
					<input type="hidden" name="_method" value="put"/>
					<form:errors path="title" class="text-danger"/>
					<form:input path="title" type="text" class="form-control mb-3"/>
					<p>Added by: ${book.user.firstName} ${book.user.lastName}</p>
					<p>Added on: <fmt:formatDate pattern="MMMM dd, yyyy @ h:m a" value="${book.createdAt}"/> </p>
					<p>Last updated on:
						<c:choose>
							<c:when test="${book.updatedAt!=null}">
								<fmt:formatDate pattern="MMMM dd, yyyy @ h:m a" value="${book.updatedAt}"/>
							</c:when>
							<c:otherwise>
								<fmt:formatDate pattern="MMMM dd, yyyy @ h:m a" value="${book.createdAt}"/>
							</c:otherwise>
						</c:choose> 
					</p>
					<div class="d-flex">
						<div>
							<p>Description:</p>
						</div>
						<div class="ms-3">
							<form:errors path="description" class="text-danger"/>
							<form:textarea path="description" rows="5" cols="20" class="form-control"/> 
						</div>
					</div>
					<form:input type="hidden" path="user" value="${user.id}"/>
					<div class="d-flex justify-content-end">
						<input type="submit" value="Update" class="btn btn-primary w-25 mt-3"/>
						<a href="/books/${book.id}/delete" class="btn btn-danger w-25 mt-3 ms-5 me-2">Delete</a>
					</div>
				</form:form>
			</div>
			<div>
				<h3>Users Who Like This Book:</h3>
				<c:forEach var="favoriteUser" items="${favoriteUsers}">
					<div class="d-flex justify-content-between">
						<p class="ms-5 mt-3">- ${favoriteUser.firstName} ${favoriteUser.lastName}</p>
						<c:if test="${favoriteUser.id == user.id}">
							<a href="/books/${book.id}/unfavorite" class="btn btn-primary align-self-center">Unfavorite</a>
						</c:if>
						<c:if test="${favoriteUser.id == user.id}">
							<c:set var="favorited" value="true"/>
						</c:if>
					</div>
				</c:forEach>
				<div class="d-flex justify-content-end">
					<c:if test="${favorited != true}">
						<a href="/books/${book.id}/favorite" class="btn btn-primary mt-5">Add to Favorites</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>