<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8" />
  <title>Ошибка 400 - Неверный запрос</title>
  <style>
    body { font-family: Arial, sans-serif; background: #f2f2f2; text-align: center; padding: 50px; }
    h1 { color: #d9534f; }
    p { color: #555; }
    a { color: #337ab7; text-decoration: none; }
    a:hover { text-decoration: underline; }
  </style>
</head>
<body>
<h1>Ошибка 400 - Неверный запрос</h1>
<p>Ваш запрос некорректен. Пожалуйста, проверьте URL или обратитесь к администратору.</p>
<p><a href="<%=request.getContextPath()%>/">Вернуться на главную страницу</a></p>
</body>
</html>
