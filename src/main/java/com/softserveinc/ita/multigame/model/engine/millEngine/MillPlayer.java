package com.softserveinc.ita.multigame.model.engine.millEngine;

import java.util.ArrayList;
import java.util.List;

class MillPlayer {
    private String color;

    private List<Stone> stonesInHand = new ArrayList<>();
    private List<Stone> stonesInGame = new ArrayList<>();

    MillPlayer(String color) {
        this.color = color;
        initStones();
    }

    String getColor() {
        return color;
    }

    Stone getNewStone(){
        Stone stone = stonesInHand.remove(0);
        stonesInGame.add(stone);
        return stone;
    }

    void dropStone(Stone stone){
        stonesInGame.remove(stone);
    }

    int getCountOfStones() {
        return stonesInGame.size() + stonesInHand.size();
    }
    int getCountOfStonesInHand() {
        return stonesInHand.size();
    }

    private void initStones(){
        for (int i = 0; i < 9; i++) {
            stonesInHand.add(new Stone(color));
        }
    }
}
