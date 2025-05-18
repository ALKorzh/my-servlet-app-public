<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.karzhou.app.entity.Contact" %>
<html>
<head><title>Контакт добавлен</title></head>
<body>
<h2>Контакт успешно добавлен!</h2>

<h3>Список всех абонентов:</h3>
<table>
    <tr>
        <th>Фамилия</th>
        <th>Телефон</th>
    </tr>
    <%
        List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
        for (Contact c : contacts) {
    %>
    <tr>
        <td><%= c.getLastName() %></td>
        <td><%= c.getPhoneNumber() %></td>
    </tr>
    <% } %>
</table>

<br><a href="add-contact">Добавить ещё</a>
</body>
</html>
