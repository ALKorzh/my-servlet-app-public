<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Email Confirmation Failed</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f44336;
            color: white;
            text-align: center;
            padding-top: 100px;
        }

        .container {
            background-color: #fff;
            color: #333;
            padding: 30px;
            margin: auto;
            max-width: 500px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
        }

        h1 {
            color: #e53935;
        }

        a {
            color: #e53935;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Confirmation Failed</h1>
    <p>
        Sorry, we couldn’t confirm your email address. The confirmation link may be invalid or already used.
    </p>

    <p style="color: red;">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </p>

    <p>
        Please <a href="/register">try registering again</a> or contact support.
    </p>
</div>
</body>
</html>
