package com.softserveinc.ita.multigame.model.engine.millEngine;


class Mill {
    private Position one, two, three;


    Mill(Position one, Position two, Position three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    boolean isMillCreate() {
        Stone sOne = one.getStone();
        Stone sTwo = two.getStone();
        Stone sThree = three.getStone();

        if (sOne != null && sTwo != null && sThree != null) {
            if (sOne.equals(sTwo) && sTwo.equals(sThree)) {
                return true;
            }
        }
        return false;
    }

    boolean contains(Position position){
        return position == one || position == two || position == three;
    }
}
