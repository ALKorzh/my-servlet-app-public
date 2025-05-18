<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ошибка приложения</title>
    <style>
        body { font-family: sans-serif; background: #f8f8f8; color: #333; padding: 40px; }
        .error-container { background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); max-width: 800px; margin: auto; }
        h1 { color: #c00; }
        pre { background: #eee; padding: 10px; border-radius: 5px; overflow-x: auto; }
        a { color: #0066cc; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="error-container">
    <h1>Произошла ошибка в приложении</h1>
    <p><strong>Код ошибки:</strong> <%= request.getAttribute("javax.servlet.error.status_code") %></p>
    <p><strong>Тип исключения:</strong> <%= request.getAttribute("javax.servlet.error.exception_type") %></p>
    <p><strong>Сообщение:</strong> <%= request.getAttribute("javax.servlet.error.message") %></p>
    <p><strong>Путь запроса:</strong> <%= request.getAttribute("javax.servlet.error.request_uri") %></p>

    <%
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (throwable != null) {
    %>
    <h2>Стек вызовов:</h2>
    <pre><%= org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(throwable) %></pre>
    <%
        }
    %>

    <p><a href="<%=request.getContextPath()%>/">Вернуться на главную</a></p>
</div>
</body>
</html>

