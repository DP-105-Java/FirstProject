
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MillGame profile</title>
    <style>
        @import "css/profile.css";
    </style>
</head>

<body>
<h1>Player ${opponentLogin}</h1>

<h2>full name: ${opponentFullName}</h2>
<div class="button_container">
    <form method="post" action="gameList">
        <input type="hidden" name="login" value=${login}>
        <input type="submit" id="back_btn" value="Game List">
    </form>
</div>
</body>
</html>