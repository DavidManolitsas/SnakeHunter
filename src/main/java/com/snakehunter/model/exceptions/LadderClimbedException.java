package com.snakehunter.model.exceptions;

/**
 * @author WeiYi Yu
 * @date 2019-09-21
 */
public class LadderClimbedException
        extends Exception {

    private static final String ERROR_MESSAGE = "Already climbed this ladder before.";

    public LadderClimbedException() {
        super(ERROR_MESSAGE);
    }
}
