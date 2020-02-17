package com.snakehunter.model;

import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Piece;
import com.snakehunter.model.piece.Snake;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2019-08-26
 */
public class Square {

    private int squareNo;
    private int column;
    private int row;
    private boolean isGuarded = false;
    private List<Piece> pieceList;

    public Square(int squareNo, int column, int row) {
        this.squareNo = squareNo;
        this.row = row;
        this.column = column;
        pieceList = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        pieceList.add(piece);
    }

    public List<Piece> getPieceList() {
        return pieceList;
    }

    public void removePiece(Piece piece) {
        pieceList.remove(piece);
    }

    public int getSquareNo() {
        return squareNo;
    }

    public void setGuarded(boolean guarded) {
        isGuarded = guarded;
    }

    public boolean isGuarded() {
        return isGuarded;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean hasPiece(Piece piece) {
        return pieceList.contains(piece);
    }

    public Snake getSnake() {
        for (Piece piece : pieceList) {
            if (piece instanceof Snake) {
                    return (Snake) piece;
                }
        }

        return null;
    }

    public Ladder getLadder() {
        for (Piece piece : pieceList) {
            if (piece instanceof Ladder) {
                return (Ladder) piece;
            }
        }
        return null;
    }
}
