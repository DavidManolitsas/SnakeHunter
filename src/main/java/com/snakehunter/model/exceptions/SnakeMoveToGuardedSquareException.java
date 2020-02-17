package com.snakehunter.model.exceptions;

/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-09-25
 */
public class SnakeMoveToGuardedSquareException extends Exception {

    private static final String ERROR_MESSAGE = "Invalid Move: Snake can not move to a guarded square";

    public SnakeMoveToGuardedSquareException() {
        super(ERROR_MESSAGE);
    }

}




