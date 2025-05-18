<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Email Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            padding: 40px;
            text-align: center;
        }
        .box {
            background-color: #fff;
            padding: 30px;
            display: inline-block;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
        }
        p {
            color: #666;
            margin-bottom: 20px;
        }
        a {
            display: inline-block;
            text-decoration: none;
            color: #ffffff;
            background-color: #4CAF50;
            padding: 10px 20px;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="box">
    <h1>Check your email</h1>
    <p>We've sent a confirmation link to your email address.</p>
    <p>Please click the link in the message to confirm your registration.</p>
    <a href="/login">Go to Login</a>
</div>
</body>
</html>
