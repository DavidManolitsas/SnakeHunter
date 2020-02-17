package com.snakehunter.model.piece;

import com.snakehunter.model.Square;
import com.snakehunter.model.exceptions.LadderClimbedThresholdException;
import com.snakehunter.model.exceptions.MaxPositionExceedException;
import com.snakehunter.model.exceptions.SnakeMoveOutOfBoundsException;
import com.snakehunter.model.exceptions.SnakeMoveToGuardedSquareException;

/**
 * @author WeiYi Yu
 * @date 2019-09-06
 */
public interface Movable {
    // return a message describes the movement
    String move(Square[][] squares, int steps) throws SnakeMoveOutOfBoundsException,
            SnakeMoveToGuardedSquareException, MaxPositionExceedException, LadderClimbedThresholdException;
    Square moveKnight(Square[][] squares, Square newSquare);
}

