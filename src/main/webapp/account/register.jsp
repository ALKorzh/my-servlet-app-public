<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/styles.css">
</head>
<body>
<div class="container">
  <h1>Register</h1>
  <p>Create a new account</p>
  <form action="<%=request.getContextPath()%>/register" method="POST" onsubmit="return validatePasswords();">
    <input type="text" name="username" placeholder="Username" required>
    <input type="email" name="email" placeholder="Email" required>
    <input type="password" id="password" name="password" placeholder="Password" required>
    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
    <p id="passwordError" style="color: red; display: none; font-size: small">Passwords do not match</p>
    <button type="submit">Register</button>
  </form>
  <p class="already-account">
    Already have an account? <a href="<%=request.getContextPath()%>/login">Log in</a>
  </p>
</div>

<script src="<%=request.getContextPath()%>/js/validate.js"></script>
</body>
</html>
