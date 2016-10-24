package com.softserveinc.ita.multigame.model.engine.millEngine;

import java.util.ArrayList;
import java.util.List;

class Board {
    private List<Position> positions = new ArrayList<>();
    private List<Mill> mills = new ArrayList<>();
    private int countOfMill = 0;

    Board() {
        initPosition();
        initPositionNeighbors();
        initMillPosition();
    }

    List<Position> getPositions() {
        return positions;
    }

    Position getPosition(int position) {
        return positions.get(position);
    }

    boolean isNewMillCreate() {
        if (checkMills() > countOfMill) {
            countOfMill = checkMills();
            return true;
        }
        return false;
    }

    boolean isMillDestroy() {
        if (checkMills() < countOfMill) {
            countOfMill = checkMills();
            return true;
        }
        return false;
    }

    boolean isPositionInMill(Position p){
        boolean isInMill = false;
        for (Mill mill : mills) {
            if (mill.isMillCreate()){
                isInMill = isInMill || mill.contains(p);
            }
        }
        return isInMill;
    }

    String drawField() {
        return  String.format("%s              %s              %s\n\n\n", drawStone(0), drawStone(1), drawStone(2)) +
                String.format("     %s         %s         %s     \n\n\n", drawStone(8), drawStone(9), drawStone(10)) +
                String.format("          %s    %s    %s          \n\n\n", drawStone(16), drawStone(17), drawStone(18)) +
                String.format("%s    %s    %s         %s    %s    %s\n\n\n", drawStone(7), drawStone(15), drawStone(23), drawStone(19), drawStone(11), drawStone(3)) +
                String.format("          %s    %s    %s          \n\n\n", drawStone(22), drawStone(21), drawStone(20)) +
                String.format("     %s         %s         %s     \n\n\n", drawStone(14), drawStone(13), drawStone(12)) +
                String.format("%s              %s              %s", drawStone(6), drawStone(5), drawStone(4));
    }

    private int checkMills() {
        int count = 0;
        for (Mill mill : mills) {
            if (mill.isMillCreate()){
                count++;
            }
        }
        return count;
    }

    private String drawStone(int i){
        Position p = getPosition(i);
        Stone stone = p.getStone();
        if (stone != null) {
            return stone.getSimpleColorName().toUpperCase();
        } else {
            return " ";
        }
    }

    private void initPosition() {
        for (int i = 0; i < 24; i++) {
            positions.add(new Position(i));
        }
    }

    private void initPositionNeighbors() {
        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 8 || i == 16) {
                positions.get(i).getNeighbors().add(positions.get(i + 1));
                positions.get(i).getNeighbors().add(positions.get(i + 7));
            } else if (i == 7 || i == 15 || i == 23) {
                positions.get(i).getNeighbors().add(positions.get(i - 1));
                positions.get(i).getNeighbors().add(positions.get(i - 7));
            } else {
                positions.get(i).getNeighbors().add(positions.get(i + 1));
                positions.get(i).getNeighbors().add(positions.get(i - 1));
            }

            if (i % 2 != 0) {
                if ((i > 0) && (i < 8)) {
                    positions.get(i).getNeighbors().add(positions.get(i + 8));
                }
                if ((i > 8) && (i < 16)) {
                    positions.get(i).getNeighbors().add(positions.get(i + 8));
                    positions.get(i).getNeighbors().add(positions.get(i - 8));
                }
                if (i > 16) {
                    positions.get(i).getNeighbors().add(positions.get(i - 8));
                }
            }
        }
    }

    private void initMillPosition() {
        mills.add(new Mill(positions.get(0), positions.get(1), positions.get(2)));
        mills.add(new Mill(positions.get(2), positions.get(3), positions.get(4)));
        mills.add(new Mill(positions.get(4), positions.get(5), positions.get(6)));
        mills.add(new Mill(positions.get(6), positions.get(7), positions.get(0)));

        mills.add(new Mill(positions.get(8), positions.get(9), positions.get(10)));
        mills.add(new Mill(positions.get(10), positions.get(11), positions.get(12)));
        mills.add(new Mill(positions.get(12), positions.get(13), positions.get(14)));
        mills.add(new Mill(positions.get(14), positions.get(15), positions.get(8)));

        mills.add(new Mill(positions.get(16), positions.get(17), positions.get(18)));
        mills.add(new Mill(positions.get(18), positions.get(19), positions.get(20)));
        mills.add(new Mill(positions.get(20), positions.get(21), positions.get(22)));
        mills.add(new Mill(positions.get(22), positions.get(23), positions.get(16)));

        mills.add(new Mill(positions.get(1), positions.get(9), positions.get(17)));
        mills.add(new Mill(positions.get(3), positions.get(11), positions.get(19)));
        mills.add(new Mill(positions.get(5), positions.get(13), positions.get(21)));
        mills.add(new Mill(positions.get(7), positions.get(15), positions.get(23)));
    }
}
