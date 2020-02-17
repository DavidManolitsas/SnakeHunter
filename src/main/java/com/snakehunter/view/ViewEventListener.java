package com.snakehunter.view;

import com.snakehunter.controller.GameNotReadyException;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;

/**
 * @author WeiYi Yu
 * @date 2019-10-17
 */
public interface ViewEventListener {
    void onAddSnakeClick();

    void onSnakeBuilt(Snake snake);

    void onAddLadderClick();

    void onLadderBuilt(Ladder ladder);

    void onAddHumansClick();

    void onStartClick() throws GameNotReadyException;

    void onSaveClick();

    void onLoadClick();

    void onRandomSnakeClick();

    void onRandomLadderClick();

    void onDiceClick();

    void onPlaceGuard();

    void onDiceRolled(int player, int num);

    void onSnakeMove(int snake, int steps);

    void onNumOfHumansEntered(int numOfHumans);

    void onKnightClick(int humanPiece);

    void onMoveKnight(int movesSquareItem, int humanListItem);

    void onCheckClimbedLadder();
}
