<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<html lang="ru">
<head>
    <title>Edit Meals</title>
</head>
<body>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Add Meal</h2>
    <input type="hidden" name="id" value="${meal.id}">
    <table>
        <tr>
            <th><label>DateTime</label></th>
            <th><input type="datetime-local" name="datetime" value="${meal.dateTime}"
                       required></th>
        </tr>
        <tr>
            <th><label>Description</label></th>
            <th><input type="text" name="description" value="${meal.description}" placeholder="${meal.description}"
                       required>
            </th>
        </tr>
        <tr>
            <th><label>Calories</label></th>
            <th><input type="text" name="calories" value="${meal.calories}" placeholder="${meal.calories}" required>
            </th>
        </tr>
    </table>
    <button type="submit">Сохранить</button>
    <button type="reset" onclick="window.history.back()">Отменить</button>
</form>
</body>
</html>