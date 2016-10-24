<%@ page import="java.util.List" %>
<%@ page import="com.softserveinc.ita.multigame.model.managers.impl.GameListManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MillGame game list</title>
    <style>
        @import "css/gameList.css";
    </style>
</head>

<body>

<h1>${login}'s Game List</h1>

<div class="buttons_container">
    <form method="post" action="createGame">
        <input type="hidden" name="login" value=${login}>
        <input type="submit" id="create_btn" value="Create">
    </form>
    <form method="post" action="gameList">
        <input type="hidden" name="login" value=${login}>
        <input type="submit" id="refresh_btn" value="Refresh">
    </form>
    <form method="get" action="login.jsp">
        <input type="submit" id="back_btn" value="Exit">
    </form>
</div>



<div class="play_container">
    <h2>Playing game</h2>
    <ol>
        <%
            for (Long id : (List<Long>)request.getAttribute("playingGames")) {
                String opponent = GameListManager.getInstance().getGameById(id).getFirstPlayerLogin();
                if (opponent.equals(request.getAttribute("login"))){
                    opponent = GameListManager.getInstance().getGameById(id).getSecondPlayerLogin();
                }
                out.println(String.format("<li><a href=\"game?id=%s&login=%s\">Game # %s <em>%s</em></a></li>", id, request.getAttribute("login"), id, opponent));
            }
        %>
    </ol>
</div>

<div class="create_container">
    <h2>Created game</h2>
    <ol>
        <%
            for (Long id : (List<Long>)request.getAttribute("createdGames")) {
                out.println(String.format("<li><a href=\"game?id=%s&login=%s\">Game # %s</a></li>", id, request.getAttribute("login"), id));
            }
        %>
    </ol>
</div>

<div class="wait_container">
    <h2>Waiting game</h2>
    <ol>
        <%
            for (Long id : (List<Long>)request.getAttribute("waitingGames")) {
                String owner = GameListManager.getInstance().getGameById(id).getFirstPlayerLogin();
                out.println(String.format("<li><a href=\"game?id=%s&login=%s\">Game # %s <em>%s</em></a></li>", id, request.getAttribute("login"), id, owner));
            }
        %>
    </ol>
</div>

</body>
</html>