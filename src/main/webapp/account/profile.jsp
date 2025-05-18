<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}"/>
<fmt:setBundle basename="messages"/>

<%
    String username = (String) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="${sessionScope.locale != null ? sessionScope.locale.language : 'en'}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="profile.settings"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/profile.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="container">

    <div class="language-switcher">
        <form action="${pageContext.request.contextPath}/changeLanguage" method="post" class="language-form">
            <input type="hidden" name="lang" value="en">
            <button type="submit"><fmt:message key="language.english"/></button>
        </form>
        <form action="${pageContext.request.contextPath}/changeLanguage" method="post" class="language-form">
            <input type="hidden" name="lang" value="ru">
            <button type="submit"><fmt:message key="language.russian"/></button>
        </form>
    </div>


    <h2><fmt:message key="profile.settings"/></h2>

    <div class="profile-photo">
        <img src="${pageContext.request.contextPath}/getPhoto?user=<%= username %>" alt="Profile Photo"
             onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default.png'">

        <form action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="uploadPhoto">
            <input type="file" name="photo" accept="image/*" required>
            <button type="submit"><fmt:message key="upload.photo"/></button>
        </form>

        <form action="${pageContext.request.contextPath}/profile" method="post">
            <input type="hidden" name="action" value="deletePhoto">
            <button type="submit" class="delete"><fmt:message key="delete.photo"/></button>
        </form>
    </div>

    <div class="form-section">
        <form action="${pageContext.request.contextPath}/profile" method="post">
            <input type="hidden" name="action" value="updateUsername">
            <label for="username"><fmt:message key="change.username"/></label>
            <input type="text" id="username" name="newUsername" value="<%= username %>" required>
            <button type="submit"><fmt:message key="update.username"/></button>
        </form>
    </div>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/index.jsp" class="back"><fmt:message key="back.to.main"/></a>
        <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
            <button type="submit" class="logout"><fmt:message key="logout"/></button>
        </form>

    </div>
</div>
</body>
</html>
