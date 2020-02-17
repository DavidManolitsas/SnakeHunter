package com.snakehunter.model.exceptions;

/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-09-23
 */

public class SnakeMoveOutOfBoundsException
        extends Exception {

    private static final String ERROR_MESSAGE = "Invalid Move: Snake can not move off the board";

    public SnakeMoveOutOfBoundsException() {
        super(ERROR_MESSAGE);
    }
}
