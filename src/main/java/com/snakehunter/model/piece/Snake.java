package com.snakehunter.model.piece;

import com.snakehunter.model.Square;
import com.snakehunter.model.exceptions.SnakeMoveOutOfBoundsException;
import com.snakehunter.model.exceptions.SnakeMoveToGuardedSquareException;

/**
 * @author David Manolitsas
 * @date 2019-09-08
 */

public class Snake
        extends ConnectorPiece
        implements Movable {
    public Snake(int position, int connectedPosition) {
        super(position, connectedPosition);
    }

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private boolean isSnakeDead = false;

    public void killSnake() {
        isSnakeDead = true;
    }

    public boolean isSnakeDead() {
        return isSnakeDead;
    }

    @Override
    public String move(Square[][] squares, int steps) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        String message;
        Square currSquare = getSquare(squares, getPosition());

        try {
            if (steps == UP) {
                if (moveUp(squares, currSquare)) {
                    return null;
                }
            } else if (steps == DOWN) {
                if (moveDown(squares, currSquare)) {
                    return null;
                }

            } else if (steps == LEFT) {
                if (moveLeft(squares, currSquare)) {
                    return null;
                }

            } else if (steps == RIGHT) {
                if (moveRight(squares, currSquare)) {
                    return null;
                }
            } else {
                return message = "Invalid Input";
            }
        } catch (SnakeMoveOutOfBoundsException | SnakeMoveToGuardedSquareException e) {
            return message = e.getMessage();
        }
        return message = "invalid move";
    }

    @Override
    public Square moveKnight(Square[][] squares, Square destSquare) {
        return null;
    }

    public void eatHuman(Square newHeadSquare) {
        for (Piece p : newHeadSquare.getPieceList()) {
            if (p instanceof Human) {
                ((Human) p).paralyzeHuman(this);
                p.setPosition(getConnectedPosition());
            }
        }
    }

    public void killHuman(Square newHeadSquare) {
        for (Piece p : newHeadSquare.getPieceList()) {
            if (p instanceof Human) {
                if (!((Human) p).isUnkillable()) {
                    ((Human) p).killHuman();
                }
            }
        }
    }

    public boolean moveUp(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int newHeadPosition;
        Square newHeadSquare, newTailSquare;
        int intialLength = getLength();

        if (headRow + 1 > 9) {
            throw new SnakeMoveOutOfBoundsException();
        } else {
            newHeadSquare = squares[headCol][headRow + 1];
            if (newHeadSquare.isGuarded()) {
                throw new SnakeMoveToGuardedSquareException();
            } else {
                //move head up
                currSquare.removePiece(this);
                tail.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail up
                setConnectedPosition(getPosition() - intialLength);
                newTailSquare = getSquare(squares, getConnectedPosition());
                newTailSquare.addPiece(this);

                return true;
            }
        }
    }

    public boolean moveDown(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;
        int intialLength = getLength();

        if (headRow - 1 < 0) {
            throw new SnakeMoveOutOfBoundsException();
        } else {
            newHeadSquare = squares[headCol][headRow - 1];
            if (newHeadSquare.isGuarded()) {
                throw new SnakeMoveToGuardedSquareException();
            } else {
                //move head down
                currSquare.removePiece(this);
                tail.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail
                if (getPosition() - intialLength < 1) {
                    setConnectedPosition(1);
                } else {
                    setConnectedPosition(getPosition() - intialLength);
                }
                newTailSquare = getSquare(squares, getConnectedPosition());
                newTailSquare.addPiece(this);
                return true;
            }
        }
    }

    public boolean moveRight(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int newHeadPosition;
        Square newHeadSquare, newTailSquare;
        int intialLength = getLength();

        if ((headCol + 1 > 9)) {
            throw new SnakeMoveOutOfBoundsException();
        } else {
            newHeadSquare = squares[headCol + 1][headRow];
            if (newHeadSquare.isGuarded()) {
                throw new SnakeMoveToGuardedSquareException();
            } else {
                //move head right
                currSquare.removePiece(this);
                tail.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail right
                if (getPosition() - intialLength < 1) {
                    setConnectedPosition(1);
                } else {
                    setConnectedPosition(getPosition() - intialLength);
                }
                newTailSquare = getSquare(squares, getConnectedPosition());
                newTailSquare.addPiece(this);
                return true;
            }
        }
    }

    public boolean moveLeft(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int newHeadPosition;
        Square newHeadSquare, newTailSquare;
        int intialLength = getLength();

        if ((headCol - 1 < 0)) {
            throw new SnakeMoveOutOfBoundsException();
        } else {
            newHeadSquare = squares[headCol - 1][headRow];
            if (newHeadSquare.isGuarded()) {
                throw new SnakeMoveToGuardedSquareException();
            } else {
                //move head left
                currSquare.removePiece(this);
                tail.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail left
                if (getPosition() - intialLength < 1) {
                    setConnectedPosition(1);
                } else {
                    setConnectedPosition(getPosition() - intialLength);
                }
                newTailSquare = getSquare(squares, getConnectedPosition());
                newTailSquare.addPiece(this);
                return true;
            }
        }
    }
}
