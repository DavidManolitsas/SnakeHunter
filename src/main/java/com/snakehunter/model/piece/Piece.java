package com.snakehunter.model.piece;

import com.snakehunter.model.Square;

/**
 * @author WeiYi Yu
 * @date 2019-09-06
 */
public abstract class Piece {
    private int position;

    public Piece(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Square getSquare(Square[][] squares, int squareNo) {
        int column;
        int row;

        row = (int) Math.ceil(squareNo / 10f) - 1;

        if (row % 2 == 0) {   // Odd row
            column = squareNo % 10 - 1;
            if (column == -1) {
                column = 9;
            }
        } else {    // Even row
            column = 10 - squareNo % 10;
            if (column == 10) {
                column = 0;
            }
        }
        return squares[column][row];
    }

}
