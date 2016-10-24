package com.softserveinc.ita.multigame.model.engine.millEngine;


import java.util.ArrayList;
import java.util.List;

class Position {
    private int id;
    private List<Position> neighbors = new ArrayList<>();
    private Stone stone = null;

    Position(int id) {
        this.id = id;
    }

    List<Position> getNeighbors() {
        return neighbors;
    }

    int getId() {
        return id;
    }

    Stone getStone() {
        return stone;
    }

    void setStone(Stone stone) {
        this.stone = stone;
    }
}
