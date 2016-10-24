package com.softserveinc.ita.multigame.controllers;

import com.softserveinc.ita.multigame.model.Player;
import com.softserveinc.ita.multigame.model.managers.PlayerManager;
import com.softserveinc.ita.multigame.model.managers.impl.PlayerListManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile/*")
public class ProfileController extends HttpServlet {
    private PlayerManager playerManager = PlayerListManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String opponentLogin = req.getParameter("opponent");

        Player opponent = playerManager.getPlayerByLogin(opponentLogin);

        String opponentFullName = opponent.getFullName();

        req.setAttribute("login", login);
        req.setAttribute("opponentLogin", opponentLogin);
        req.setAttribute("opponentFullName", opponentFullName);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);




    }
}
