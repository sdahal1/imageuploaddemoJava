<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- c:out ; c:foreach; c:if  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
    <!-- formatting things like dates  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
    <h1>Welcome ${loggedinuser.firstName}!</h1>
    <p>Followers:${loggedinuser.followers.size()}</p>
    <p>Following:${loggedinuser.following.size()}</p>
    <a href="/logout">Logout</a>
    <p class="text-danger">${message}</p>
    <form action="/dashboard/upload" method="post" enctype="multipart/form-data" >
        <div class="form-group">
            <label for="">Upload a picture</label>
            <input type="file" name="pic" id="">
        </div>
        <div class="form-group">
            <label for="">Description</label>
            <textarea name="description" id="" cols="30" rows="10"></textarea>
        </div>
        <input type="submit" value="Upload Picture!">
    </form>

    <div>
        <h3>Users you can follow!</h3>

        <c:forEach items='${ allusers }' var='user'>
            <c:if test='${user.id != loggedinuser.id}'>
            <p>${user.firstName} ${user.lastName}- <a href="/follow/${user.id}">Follow</a></p>
            </c:if>
        </c:forEach>
    </div>
</body>
</html>