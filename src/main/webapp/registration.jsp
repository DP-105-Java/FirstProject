<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MillGame registration</title>
    <style>
        @import "css/registration.css";
    </style>
</head>

<body>
        <h1>Hi, ${login}!</h1>
    <form method="post" action="registration">
        <input type="hidden" name="login" value=${login}>
        <input type="text" id="fullNameField" name="fullName" placeholder="enter your full name, please">
        <input type="submit" id="signUpBtn" name="signUpBtn" value="Sign up">
    </form>

    <form method="get" action="login.jsp">
        <input type="submit" id="backBtn" name="backBtn" value="Game List">
    </form>
</body>
</html>