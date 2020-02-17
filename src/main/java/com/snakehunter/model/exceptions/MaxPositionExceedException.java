package com.snakehunter.model.exceptions;

/**
 * @author WeiYi Yu
 * @date 2019-09-21
 */
public class MaxPositionExceedException
        extends Exception {

    private static final String ERROR_MESSAGE = "Position is over 100.";

    public MaxPositionExceedException() {
        super(ERROR_MESSAGE);
    }
}
