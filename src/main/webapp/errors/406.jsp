<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8" />
  <title>Ошибка 406 - Не приемлемо</title>
  <style>
    body { font-family: Arial, sans-serif; background: #f2f2f2; text-align: center; padding: 50px; }
    h1 { color: #d9534f; }
    p { color: #555; }
    a { color: #337ab7; text-decoration: none; }
    a:hover { text-decoration: underline; }
  </style>
</head>
<body>
<h1>Ошибка 406 - Не приемлемо</h1>
<p>Запрошенный ресурс не может удовлетворить заголовки Accept.</p>
<p><a href="<%=request.getContextPath()%>/">Главная страница</a></p>
</body>
</html>