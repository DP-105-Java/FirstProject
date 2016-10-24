package com.softserveinc.ita.multigame.controllers;

import com.softserveinc.ita.multigame.model.Player;
import com.softserveinc.ita.multigame.model.managers.impl.PlayerListManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegisterController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String fullName = req.getParameter("full_name");

        PlayerListManager.getInstance().registerNewPlayer(new Player(login, fullName));

        req.getRequestDispatcher("/gameList").forward(req, resp);
    }
}
