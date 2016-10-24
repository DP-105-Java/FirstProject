<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MillGame game</title>
    <style>
        @import "css/game.css";
    </style>
</head>

<body>
<h1>Game #${id}</h1>
    <h2>${message} <a href="profile?login=${login}&opponent=${opponent}">${opponent}</a></h2>

        <div class="turn_container">
            <form method="get" action="makeTurn">
                <input type="hidden" name="id" value=${id}>
                <input type="hidden" name="login" value=${login}>
                <input type="text" id="turn_field" name="turn" placeholder="enter 'p' for put, 'r' for replace, 'd' for drop stone">
                <input type="submit" id="turn_btn" value="Make turn">
            </form>
        </div>

        <div class="field_container">
            <div class="playerStones">
                <%
                    for (String stone : (List<String>)request.getAttribute("stones")) {
                        if (stone.equals("W")){
                            out.println("<p>W</p>");
                        }
                    }
                %>
            </div>
            <textarea name="field" readonly="readonly" wrap="off">${board}</textarea>
            <div class="opponentStones">
                <%
                    for (String stone : (List<String>)request.getAttribute("stones")) {
                        if (stone.equals("B")){
                            out.println("<p>B</p>");
                        }
                    }
                %>
            </div>
        </div>
        <div class="tip_container">
            <p>${tip}</p>
        </div>
        <div class="button_container">
            <form method="get" action="game?id=${id}&login=${login}">
                <input type="hidden" name="id" value=${id}>
                <input type="hidden" name="login" value=${login}>
                <input type="submit" id="refresh_btn" value="Refresh">
            </form>
            <form method="post" action="gameList">
                <input type="hidden" name="login" value=${login}>
                <input type="submit" id="back_btn" value="Game List">
            </form>
        </div>
</body>
</html>