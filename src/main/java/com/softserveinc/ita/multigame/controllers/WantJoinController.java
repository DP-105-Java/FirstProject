package com.softserveinc.ita.multigame.controllers;

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

@WebServlet("/wantJoin")
public class WantJoinController extends HttpServlet {

    private GameManager gameManager = GameListManager.getInstance();
    private PlayerManager playerManager = PlayerListManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String login = req.getParameter("login");

        Game game = gameManager.getGameById(id);
        game.joinToGame(playerManager.getPlayerByLogin(login));

        req.getRequestDispatcher(String.format("game?id=%s&login=%s", id, login)).forward(req, resp);
    }
}
