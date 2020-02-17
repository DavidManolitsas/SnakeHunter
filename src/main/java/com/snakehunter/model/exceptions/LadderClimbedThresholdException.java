package com.snakehunter.model.exceptions;

/**
 * @author WeiYi Yu
 * @date 2019-09-21
 */
public class LadderClimbedThresholdException
        extends Exception {

    private static final String ERROR_MESSAGE = "Haven't climbed 3 ladders yet, go back to Square 1.";

    public LadderClimbedThresholdException() {
        super(ERROR_MESSAGE);
    }
}
