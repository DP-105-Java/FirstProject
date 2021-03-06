package com.softserveinc.ita.multigame.controllers;


import com.softserveinc.ita.multigame.model.Player;
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

@WebServlet("/createGame")
public class CreateGameController extends HttpServlet {
    private PlayerManager playerManager = PlayerListManager.getInstance();
    private GameManager gameManager = GameListManager.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");

        Player player = playerManager.getPlayerByLogin(login);
        gameManager.createGame(player);

        req.getRequestDispatcher("/gameList").forward(req, resp);
    }
}
