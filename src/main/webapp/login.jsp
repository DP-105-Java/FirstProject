<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MillGame login</title>
    <style>
        @import "css/login.css";
    </style>
</head>

<body>

<div class="logo">
    <img id="logo1" src="img/mill_logo_200x200_shadow.png">
</div>

<div class="main_container">
    <form method="post" action="login">
        <h1>The Mill Game</h1>
        <input type="text" id="login_field" name="login" placeholder="enter your login">
        <input type="submit" id="loginBtn" name="loginBtn" value="Log in" >
        <input type="submit" id="signUpBtn" name="signUpBtn" value="Sign up">
    </form>
    <p class="error">${message}</p>

    <p class="intro">
        The Mill Game is a strategy board game for two players also known as Nine Man's Morris.
        This is a game in which either player can be a winner or loser. Are you ready for it?
    </p>
    <p class="addition">
        Brief rules: The board consists of a grid with twenty-four position. Each player
        has nine stones, colored black or white. Players make turn one after another trying
        to form 'mills' - three of their own stones lined horizontally or vertically.
        'Mills' allowing a player to remove an opponent's stone from the game.
        A player wins when his opponent has only to two stones.
    </p>
</div>
</body>
</html>