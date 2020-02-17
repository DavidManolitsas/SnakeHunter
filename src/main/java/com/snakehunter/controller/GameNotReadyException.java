package com.snakehunter.controller;

/**
 * @author Aspen
 * @date 2019-09-25
 */
public class GameNotReadyException
        extends Exception {


    public GameNotReadyException(String message) {
        super(message);
    }
}
