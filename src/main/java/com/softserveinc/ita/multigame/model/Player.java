package com.softserveinc.ita.multigame.model;

public class Player {
    private String login;
    private String fullName;

    public Player(String login, String fullName) {
        this.login = login;
        this.fullName = fullName;
    }

    public Player(String login) {
        this.login = login;
        this.fullName = "";
    }

    public String getLogin() {
        return login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return login != null ? login.equals(player.login) : player.login == null;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
