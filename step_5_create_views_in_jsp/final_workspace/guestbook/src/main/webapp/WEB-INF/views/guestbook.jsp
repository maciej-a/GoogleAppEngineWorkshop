<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Guest Book</title>

<!-- JQuery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>


<style type="text/css">
.navbar-inverse {
	background-image: linear-gradient(to bottom, rgba(255, 255, 255, 1) 0px,
		rgba(200, 200, 200, 1) 100%);
	border-color: rgba(255, 255, 255, 1);
}
</style>

</head>
<body>
	<!-- NAVBAR -->
	<div class="navbar navbar-inverse navbar-static-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Guest Book</a>
			</div>
			<div class="navbar-collapse collapse">
				<div class="nav navbar-nav navbar-right">
					<div class="navbar-form">
						<a href="TODO-signOutUrl" class="btn btn-primary"><span class="glyphicon glyphicon-log-out"></span> Sign Out</a>
						<a href="TODO-signInUrl" class="btn btn-primary"><span class="glyphicon glyphicon-log-in"></span> Sign In</a> 
					</div>
				</div>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>
	<!-- END NAVBAR -->

	<!-- CONTAINTER -->
	<div class="container">
		<div id="comments">
			<c:forEach items="${comments}" var="comment">
				<div class="panel panel-default">
					<div class="panel-body">
						<span style="font-style: italic;">${comment.text}</span> <br />
						<c:if test="${not empty comment.imageUrl}">
							<img class="img-responsive img-rounded" width="250" src="${comment.imageUrl}">
							<br/>
						</c:if> 
						<span
							id="commentInfo" class="pull-right"
							style="font-style: italic; color: gray;">
							${comment.authorNickname} (
								<c:if test="${comment.isAnonymous}">anonymous</c:if>
								<c:if test="${not comment.isAnonymous}">${comment.authorAuthEmail}</c:if>
								), <fmt:formatDate value="${comment.timestamp}" pattern="yyyy-MM-dd HH:mm:ss" />
								<c:if test="${comment.isAnonymous}">
									<span class="glyphicon glyphicon-question-sign"></span>
								 </c:if>
								 <c:if test="${not comment.isAnonymous}">
									<span class="glyphicon glyphicon-user"></span>
								</c:if>
						</span>
					</div>
				</div>
			</c:forEach>
		</div>
		<!-- ADD COMMENT -->
		<div class="panel panel-primary" style="margin-top: 50px;">
			<div class="panel-heading">
				<h3 class="panel-title">Add your comment</h3>
			</div>
			<div class="panel-body">


				<form:form name="addCommentForm" role="form" method="post" id="addCommentForm"
					commandName="newComment" action="/addComment" enctype="multipart/form-data">
					<div class="form-group">
						<label for="comment">Comment</label>
						<spring:bind path="newComment.text">
							<textarea rows="4" cols="30" class="form-control"
								name='<c:out value="${status.expression}"/>' id="comment"
								placeholder="Type your comment"></textarea>
						</spring:bind>
					</div>
					<!-- IMAGES CAN BE POSTED ONLY BY AUTHENTICATED USER -->
						<div class="well well-sm">
							<div class="form-group">
								<label for="image">Image</label>
								<input type="file" id="image" name="image"
									accept="image/*">
								<p class="help-block">Select image for your comment</p>
							</div>
						</div>
					<div class="form-group">
						<label for="nickname">Nickname</label>
						<spring:bind path="newComment.authorNickname">
							<input type="text" class="form-control"
								name='<c:out value="${status.expression}"/>' id="nickname"
								placeholder="Nickname">
						</spring:bind>
					</div>
					<button type="submit" class="btn btn-primary">Add comment</button>
				</form:form>
			</div>
		</div>
		<hr>
		<footer>
			<p>&copy; 2014</p>
		</footer>
	</div>
	<!-- END CONTAINER -->

</body>
</html>