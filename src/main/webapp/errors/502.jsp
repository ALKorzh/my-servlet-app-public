<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8" />
  <title>Ошибка 502 - Плохой шлюз</title>
  <style>
    body { font-family: Arial, sans-serif; background: #f2f2f2; text-align: center; padding: 50px; }
    h1 { color: #d9534f; }
    p { color: #555; }
    a { color: #337ab7; text-decoration: none; }
    a:hover { text-decoration: underline; }
  </style>
</head>
<body>
<h1>Ошибка 502 - Плохой шлюз</h1>
<p>Сервер получил недопустимый ответ от вышестоящего сервера.</p>
<p><a href="<%=request.getContextPath()%>/">Попробовать снова</a></p>
</body>
</html>