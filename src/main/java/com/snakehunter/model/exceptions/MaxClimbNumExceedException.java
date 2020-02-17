package com.snakehunter.model.exceptions;

/**
 * @author WeiYi Yu
 * @date 2019-09-21
 */
public class MaxClimbNumExceedException
        extends Exception {

    private static final String ERROR_MESSAGE = "Maximum climb number of ladders exceeded.";

    public MaxClimbNumExceedException() {
        super(ERROR_MESSAGE);
    }
}
