
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MillGame do you want to play</title>
    <style>
        @import "css/wantJoin.css";
    </style>
</head>

<body>
<h1>Do you want to play</h1>
<h2><a href="profile?login=${login}&opponent=${gameOwner}">with ${gameOwner}</a></h2>
<div class="button_container">
    <form method="get" action="wantJoin">
        <input type="hidden" name="id" value=${id}>
        <input type="hidden" name="login" value=${login}>
        <input type="submit" id="yes_btn" value="Yes">
    </form>
    <form method="post" action="gameList">
        <input type="hidden" name="login" value=${login}>
        <input type="submit" id="no_btn" value="No">
    </form>

</div>
</body>
</html>