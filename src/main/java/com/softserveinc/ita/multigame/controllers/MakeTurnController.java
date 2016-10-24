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

@WebServlet("/makeTurn")
public class MakeTurnController extends HttpServlet {
    private static PlayerManager playerManager = PlayerListManager.getInstance();
    private static GameManager gameManager = GameListManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String login = req.getParameter("login");
        String turn = req.getParameter("turn");

        Player player = playerManager.getPlayerByLogin(login);
        Game game = gameManager.getGameById(id);

        game.makeTurn(player, turn);
        req.getRequestDispatcher(String.format("game?id=%s&login=%s", id, login)).forward(req, resp);
    }
}
