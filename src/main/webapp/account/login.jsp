<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/styles.css">
</head>
<body>
<div class="container">
    <h1>Login</h1>
    <p>Enter your details to log in</p>
    <form action="<%=request.getContextPath()%>/login" method="POST">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <label>
            <input type="checkbox" name="rememberMe">
            <span></span>
            Remember me
        </label>

        <button type="submit">Log in</button>
    </form>

    <p class="no-account">
        Don't have an account? <a href="<%=request.getContextPath()%>/register">Register</a>
    </p>
</div>
</body>
</html>
