package com.softserveinc.ita.multigame.model.engine.millEngine;


class Stone {
    private String color;

    Stone(String color) {
        this.color = color;
    }

    String getColor() {
        return color;
    }

    String getSimpleColorName() {
        return color.substring(0, 1);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stone stone = (Stone) o;

        return color != null ? color.equals(stone.color) : stone.color == null;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}

