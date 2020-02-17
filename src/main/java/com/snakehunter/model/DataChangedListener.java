package com.snakehunter.model;

import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;

/**
 * @author WeiYi Yu
 * @date 2019-10-17
 */
public interface DataChangedListener {
    void onSnakeAdded(Snake snake);

    void onLadderAdded(Ladder ladder);

    void onAddSnakeFailed(String errorMessage);

    void onAddLadderFailed(String errorMessage);

    void onGuardAdded(int position);

    void onHumanMoved(String message);

    void onExceedMaxNumOfGuards();

    void onExceedMaxPosition();

    void onLadderClimbedThresholdException();

    void onNextTurn(Player player);

    void onGameOver(Player winner);

    void onNumOfHumansEnteredError();

    void showClimbedLadder();
}
