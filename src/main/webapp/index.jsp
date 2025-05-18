<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}"/>
<fmt:setBundle basename="messages"/>

<%
    String username = (String) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="main.title" /></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/main.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="header">
    <div class="username">
        <fmt:message key="main.welcome">
            <fmt:param><%= username %></fmt:param>
        </fmt:message>
    </div>
    <div class="nav-links">
        <a class="profile" href="<%=request.getContextPath()%>/profile">
            <fmt:message key="nav.profile" />
        </a>
        <form action="<%=request.getContextPath()%>/logout" method="post" style="display:inline;">
            <button type="submit" class="logout"><fmt:message key="logout" /></button>
        </form>



        <!-- Language Switch Form using POST -->
        <form class="language-switcher" action="${pageContext.request.contextPath}/changeLanguage" method="post">
            <button type="submit" name="lang" value="en">English</button>
            <button type="submit" name="lang" value="ru">Русский</button>
        </form>
    </div>
</div>

<div class="content">
    <h2><fmt:message key="main.title" /></h2>
    <p><fmt:message key="main.secured" /></p>
</div>
</body>
</html>
