package com.snakehunter;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public enum GameStage {
    INITIAL(0), SECOND(100), FINAL(40);

    private int maxTurns;

    GameStage(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public int getMaxTurns() {
        return maxTurns;
    }
}
