package com.softserveinc.ita.multigame.controllers;

import com.softserveinc.ita.multigame.model.Player;
import com.softserveinc.ita.multigame.model.Game;
import com.softserveinc.ita.multigame.model.managers.GameManager;
import com.softserveinc.ita.multigame.model.managers.PlayerManager;
import com.softserveinc.ita.multigame.model.managers.impl.GameListManager;
import com.softserveinc.ita.multigame.model.managers.impl.PlayerListManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/game/*")
public class GameController extends HttpServlet {
    private PlayerManager playerManager = PlayerListManager.getInstance();
    private GameManager gameManager = GameListManager.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String login = req.getParameter("login");

        Game game = gameManager.getGameById(id);

        //TODO delete game if it finished

        req.setAttribute("id", id);
        req.setAttribute("login", login);
        req.setAttribute("stones", game.getStones());
        req.setAttribute("board", game.getBoard());

        Player player = playerManager.getPlayerByLogin(login);

        String whatIsWrongMessage = game.whatIsWrongMessage(player);
        String whichTurnMessage =  game.whichTurnMessage(player);
        if (!whatIsWrongMessage.equals("")){
            req.setAttribute("tip",">" + game.getColor(player) + ": " + whatIsWrongMessage);
        } else {
            req.setAttribute("tip", ">" + game.getColor(player) + ": " + whichTurnMessage);
        }

        String firstPlayerLogin = game.getFirstPlayerLogin();
        String secondPlayerLogin = game.getSecondPlayerLogin();

        if (login.equals(firstPlayerLogin) && secondPlayerLogin == null) {
            //creating game
            req.setAttribute("message", "Waiting for an opponent");
            req.setAttribute("opponent", "");
            req.getRequestDispatcher("/game.jsp").forward(req, resp);
        }

        if ((login.equals(firstPlayerLogin) && secondPlayerLogin != null)
                || (login.equals(secondPlayerLogin) && firstPlayerLogin != null)) {
            //playing game
            String opponentLogin;
            if (login.equals(firstPlayerLogin)){
                opponentLogin = secondPlayerLogin;
            } else {
                opponentLogin = firstPlayerLogin;
            }
            req.setAttribute("message", login + " vs ");
            req.setAttribute("opponent", opponentLogin);
            req.getRequestDispatcher("/game.jsp").forward(req, resp);
        }
        if (!login.equals(firstPlayerLogin)
                && !login.equals(secondPlayerLogin)
                && firstPlayerLogin != null) {
            //waiting game
            req.setAttribute("gameOwner", firstPlayerLogin);
            req.getRequestDispatcher("/wantJoin.jsp").forward(req, resp);
        }
    }

}
