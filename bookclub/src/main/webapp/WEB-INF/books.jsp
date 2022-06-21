<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book Club</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body class="bg-secondary">
	<div class="container">
		<div class="d-flex justify-content-between mt-5 text-dark">
			<h2>Welcome, <c:out value="${user.firstName}"/>!</h2>
			<p><a href="/logout" class="btn btn-primary">Logout</a></p>
		</div>
		<hr>
		<div class="d-flex justify-content-around">
			<div>
				<form:form action="/books" method="post" modelAttribute="book">
					<h3>Add a New Book</h3>
					<div class="mt-3 p-4 border border-dark border-3">
						<div class="form-group p-4 d-block">
							<form:label path="title" class="form-label text-start">Title: </form:label><br>
							<form:errors path="title" class="text-danger"/>
							<form:input path="title" type="text" class="form-control"/>
						</div>
						<div class="form-group p-4 d-block">
							<form:label path="description" class="form-label text-start">Description: </form:label><br>
							<form:errors path="description" class="text-danger"/>
							<form:input path="description" type="text" class="form-control"/>
						</div>
						<div class="d-flex justify-content-center">
							<input type="submit" value="Add" class="btn btn-primary w-50 mt-3"/>
						</div>
					</div>
				</form:form>
			</div>
			<div>
				<h3>All Books</h3>
				<c:forEach var="book" items="${books}">
					<div class="mt-3 p-4 border border-dark border-3">
						<h4 class="text-center"><a href="/books/${book.id}" class="text-white"><c:out value="${book.title}"/></a></h4>
						<p class="text-center">(added by ${book.user.firstName} ${book.user.lastName})</p>
						<c:forEach var="favoriteUser" items="${book.users}">
							<c:if test="${favoriteUser.id == user.id}">
								<c:set var="favorited" value="true"/>
							</c:if>
						</c:forEach>
						<c:choose>
							<c:when test="${favorited == true}">
								<p class="fst-italic text-center">this is one of your favorites</p>
							</c:when>
							<c:otherwise>
								<a href="/books/${book.id}/favorite/main" class="btn btn-primary ms-5">Add to Favorites</a>
							</c:otherwise>
						</c:choose>
					</div>
					<c:set var="favorited" value="false"/>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>