<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Добавить контакт</title></head>
<body>
<h2>Добавление контакта</h2>
<form action="${pageContext.request.contextPath}/add-contact" method="post">
    Фамилия: <input type="text" name="lastName" required><br><br>
    Телефон: <input type="text" name="phoneNumber" required><br><br>
    <input type="submit" value="Сохранить">
</form>
</body>
</html>
