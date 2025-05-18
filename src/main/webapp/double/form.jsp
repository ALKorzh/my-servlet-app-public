<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Удвоение числа</title>
</head>
<body>
<h2>Введите число:</h2>
<form action="${pageContext.request.contextPath}/double" method="post">
    <input type="number" name="number" required>
    <input type="submit" value="Отправить">
</form>
</body>
</html>
