<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html lang="ru">
<head>
    <title>Edit Meals</title>
</head>
<body>
<form>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Add Meal</h2>
    <table>
        <tr>
            <th><label>DateTime</label></th>
            <th><input type="date" name="datetime"></th>
        </tr>
        <tr>
            <th><label>Description</label></th>
            <th><input type="text" name="description"></th>
        </tr>
        <tr>
            <th><label>Calories</label></th>
            <th><input type="text" name="calories"></th>
        </tr>
    </table>

    <button type="submit">Сохранить</button>
    <button type="reset" onclick="window.history.back()">Отменить</button>
</form>
</body>
</html>