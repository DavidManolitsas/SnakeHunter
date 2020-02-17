package com.snakehunter.model;

import com.snakehunter.GameStage;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2019-10-17
 */
public interface GameModel {
    void setGameStage(GameStage gameStage);

    GameStage getGameStage();

    Square[][] getSquares();

    void addSnake(Snake snake);

    void addLadder(Ladder ladder);

    void addGuard(int position);

    void addHumans(int numOfHumans);

    Player getCurrentPlayer();

    boolean isGameReady();

    void nextTurn();

    int movePlayer(int index, int steps);

    void movePlayer(int index, Square destSquare);

    String moveSnake(int index, int steps);

    void setOnDataChangedListener(DataChangedListener listener);

    Square getSquare(int squareNo);

    int getNumOfTurns();

    int getRemainingGuards();

    void resetGameModel();

    // Test only
    void setNumOfGuards(int num);

    void setNumOfTurns(int num);

    List<Human> getHumanList();

    List<Snake> getSnakeList();

    List<Ladder> getLadderList();
}
