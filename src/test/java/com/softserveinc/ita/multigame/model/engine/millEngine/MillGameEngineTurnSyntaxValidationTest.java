package com.softserveinc.ita.multigame.model.engine.millEngine;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MillGameEngineTurnSyntaxValidationTest {
    private MillGameEngine mill = new MillGameEngine();

    @Test
    public void whenUseValidTurnCommandForPutStoneAndPositionWithin0To23() {
        String syntax = Commands.PUT_NEW_STONE + " " + 5;

        boolean response = mill.validateTurnSyntax(syntax);

        assertTrue(response);
    }

    @Test
    public void whenUseValidTurnCommandForPutStoneAndPositionOutside0To23() {
        String syntax = Commands.PUT_NEW_STONE + " " + 45;

        boolean response = mill.validateTurnSyntax(syntax);

        assertFalse(response);
    }

    @Test
    public void whenUseValidTurnSyntaxForPutStoneWithALotOfSpaces() {
        String syntax = Commands.PUT_NEW_STONE + "      " + 5 + "    ";

        boolean response = mill.validateTurnSyntax(syntax);

        assertTrue(response);
    }

    @Test
    public void whenUseValidTurnSyntaxForDropStone() {
        String syntax = Commands.DROP_STONE + 5;

        boolean response = mill.validateTurnSyntax(syntax);

        assertTrue(response);
    }

    @Test
    public void whenUseValidTurnSyntaxForDropStoneWithMoreThanOnePosition() {
        String syntax = Commands.DROP_STONE + 5 + " " + 5;

        boolean response = mill.validateTurnSyntax(syntax);

        assertFalse(response);
    }

    @Test
    public void whenUseValidTurnSyntaxForReplaceStone() {
        String syntax = Commands.REPLACE_STONE + 5 + " " + 5;

        boolean response = mill.validateTurnSyntax(syntax);

        assertTrue(response);
    }

    @Test
    public void whenUseValidTurnSyntaxForReplaceStoneWithALotOfSpaces() {
        String syntax = Commands.REPLACE_STONE + 5 + "               " + 5;

        boolean response = mill.validateTurnSyntax(syntax);

        assertTrue(response);
    }

    @Test
    public void whenUseValidTurnCommandForReplaceStoneWithOnePosition() {
        String syntax = Commands.REPLACE_STONE + 5;

        boolean response = mill.validateTurnSyntax(syntax);

        assertFalse(response);
    }

    @Test
    public void whenUseWrongTurnCommand() {
        String syntax = "ppp" + " " + 5;

        boolean response = mill.validateTurnSyntax(syntax);

        assertFalse(response);
    }







}
