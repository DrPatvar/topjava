<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h3><a href="addMeals.jsp">Add Meal</a></h3>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <c:if test="${meal.excess}">
            <tr style="color: red">
                <td>${meal.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="">Update</a></td>
                <td><a href="">Delete</a></td>
            </tr>
        </c:if>
        <tr style="color: green">
            <td>${meal.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="">Update</a></td>
            <td><a href="">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>