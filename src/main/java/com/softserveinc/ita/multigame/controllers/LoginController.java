package com.softserveinc.ita.multigame.controllers;


import com.softserveinc.ita.multigame.model.managers.PlayerManager;
import com.softserveinc.ita.multigame.model.managers.impl.PlayerListManager;
import com.softserveinc.ita.multigame.services.CreateUsersAndGamesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private PlayerManager playerManager = PlayerListManager.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUsersAndGamesService.create();

        String login = req.getParameter("login");

        if (req.getParameter("loginBtn") != null) {
            if (playerManager.contains(login)) {
                req.getRequestDispatcher("/gameList").forward(req, resp);
            } else {
                req.setAttribute("message", "You have to register first");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }

        if (req.getParameter("signUpBtn") != null) {
            if (login.equals("")){
                req.setAttribute("message", "Please enter you login");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
            if (playerManager.contains(login)) {
                req.setAttribute("message", "You have already registered");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            } else {
                req.setAttribute("login", login);
                req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            }
        }
    }
}

