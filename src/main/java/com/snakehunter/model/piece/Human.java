package com.snakehunter.model.piece;

import com.snakehunter.model.Square;
import com.snakehunter.model.exceptions.LadderClimbedException;
import com.snakehunter.model.exceptions.LadderClimbedThresholdException;
import com.snakehunter.model.exceptions.MaxClimbNumExceedException;
import com.snakehunter.model.exceptions.MaxPositionExceedException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Manolitsas
 * @date 2019-09-08
 */
public class Human
        extends Piece
        implements Movable {

    private static final int PARALYZE_TURNS = 3;
    private static final int NUM_OF_LADDER_CLIMBED_THRESHOLD = 3;
    private static final int MAX_LADDERS_CLIMBED = 3;
    private static final int MAX_POSITION = 100;


    private boolean isDead = false;
    private boolean unkillable = false;
    private int paralyzedAtTurn;

    private int paralyzedTurns = 0;
    private List<Ladder> ladderClimbedList;

    public Human(int position) {
        super(position);

        ladderClimbedList = new ArrayList<>();
    }

    public boolean isDead() {
        return isDead;
    }

    public void killHuman() {
        isDead = true;
    }

    public boolean isParalyzed(int numOfTurns) {
        if (paralyzedTurns == 0) {
            return false;
        } else {
            if (paralyzedAtTurn != numOfTurns) {
                paralyzedTurns--;
            }
            return true;
        }
    }

    public int getParalyzeTurns() {
        return paralyzedTurns;
    }

    /**
     * 1. Check params is valid
     * 2. Check new position is not exceed MAX_POSITION
     * 3. Move by the dice number
     * 4. Move to Snake'stail / Ladder'stop if the piece lands on a Snake / Ladder
     * 5. Paralyze the piece if it swallowed by a Snake
     *
     * @param squares board array
     * @param steps   number rolled from dice
     * @return Message about this movement
     */
    @Override
    public String move(Square[][] squares, int steps) throws MaxPositionExceedException,
            LadderClimbedThresholdException {
        StringBuilder stringBuilder = new StringBuilder();

        Square currentSquare = getSquare(squares, getPosition());

        // Calculate new position
        int newPosition = getPosition() + steps;
        if (newPosition > MAX_POSITION) {
            throw new MaxPositionExceedException();
        } else if (newPosition == MAX_POSITION) {
            if (ladderClimbedList.size() >= NUM_OF_LADDER_CLIMBED_THRESHOLD) {
                setPosition(MAX_POSITION);
                currentSquare.removePiece(this);
                getSquare(squares, MAX_POSITION).addPiece(this);
                return "Lands on Square max position! Enter Final stage";
            } else {
                setPosition(1);
                getSquare(squares, 1).addPiece(this);
                throw new LadderClimbedThresholdException();
            }
        }

        // Remove piece from current square
        currentSquare.removePiece(this);

        stringBuilder.append(String.format("Move to position %1s", newPosition));

        // Check if the destSquare has snakes or ladders
        Square destSquare = getSquare(squares, newPosition);
        ConnectorPiece connectorPiece = null;
        if (destSquare.getLadder() != null && destSquare.getSquareNo() == destSquare.getLadder().getPosition()) {
            connectorPiece = destSquare.getLadder();
        } else if (destSquare.getSnake() != null && destSquare.getSquareNo() == destSquare.getSnake().getPosition()) {
            connectorPiece = destSquare.getSnake();
        }

        if (connectorPiece == null) {
            setPosition(newPosition);
            destSquare.addPiece(this);
            return stringBuilder.toString();
        }

        String message = "";
        if (connectorPiece instanceof Snake && destSquare.getSquareNo() == (connectorPiece.getPosition())) {
            message = paralyzeHuman(connectorPiece);
        } else if (connectorPiece instanceof Ladder) { // connector piece is a Ladder
            try {
                addLadderClimbed((Ladder) connectorPiece);
                message = String.format(" then climb a ladder to position %1s", connectorPiece.getConnectedPosition());
            } catch (LadderClimbedException | MaxClimbNumExceedException e) {
                destSquare.addPiece(this);
                setPosition(newPosition);

                message = e.getMessage();
                stringBuilder.append("\n").append(message);
                return stringBuilder.toString();
            }
        }

        newPosition = connectorPiece.getConnectedPosition();
        destSquare =

                getSquare(squares, newPosition);
        destSquare.addPiece(this);

        setPosition(newPosition);

        return stringBuilder.append(message).toString();
    }

    @Override
    public Square moveKnight(Square[][] squares, Square newSquare) {
        //remove this human piece from its previous square
        Square currSquare = getSquare(squares, getPosition());
        currSquare.removePiece(this);

        //add this human piece to its new square
        newSquare.addPiece(this);
        setPosition(newSquare.getSquareNo());

        //Check if Snake is on new Square
        if (isLandOnSnakeTail(getSquare(squares, getPosition()))) {
            System.out.println("killing snake");
            killSnake(squares);
        }
        return newSquare;
    }

    public String[] getValidKnightMoves(Square[][] squares) {
        List<Square> squareList = getValidMoves(squares);

        String[] squareNoList = new String[squareList.size()];
        for (int i = 0; i < squareList.size(); i++) {
            squareNoList[i] = Integer.toString(squareList.get(i).getSquareNo());
        }
        return squareNoList;
    }

    public ArrayList<Square> getValidMoves(Square[][] squares) {
        ArrayList<Square> validSquares = new ArrayList<>();

        Square currSquare = getSquare(squares, getPosition());
        int currCol = currSquare.getColumn();
        int currRow = currSquare.getRow();

        int[][] knightCoords = {
                {-1, 2},
                {1, 2},
                {2, 1},
                {2, -1},
                {1, -2},
                {-1, -2},
                {-2, -1},
                {-2, 1},
                {-1, -1},
                {1, 1},
                {-1, 1},
                {1, -1}
        };

        for (int i = 0; i < knightCoords.length; i++) {
            if (currCol + knightCoords[i][0] <= 9 && currCol + knightCoords[i][0] >= 0 &&
                    currRow + knightCoords[i][1] <= 9 && currRow + knightCoords[i][1] >= 0) {
                validSquares.add(squares[currCol + knightCoords[i][0]][currRow + knightCoords[i][1]]);
            }
        }
        return validSquares;
    }

    //region private methods
    void paralyze() {
        paralyzedTurns = PARALYZE_TURNS;
    }

    void addLadderClimbed(Ladder ladder) throws LadderClimbedException, MaxClimbNumExceedException {
        if (ladderClimbedList.contains(ladder)) {
            throw new LadderClimbedException();
        } else if (ladderClimbedList.size() == MAX_LADDERS_CLIMBED) {
            throw new MaxClimbNumExceedException();
        }

        ladderClimbedList.add(ladder);
    }

    private boolean isLandOnSnakeTail(Square square) {
        Snake snake = square.getSnake();
        System.out.println(snake != null && snake.getConnectedPosition() == square.getSquareNo());
        return snake != null && snake.getConnectedPosition() == square.getSquareNo();
    }

    String paralyzeHuman(ConnectorPiece connectorPiece) {
        String message;
        paralyze();
        message = String.format(" then swallowed by a snake and back to position %1s",
                connectorPiece.getConnectedPosition());
        return message;
    }

    private void killSnake(Square[][] squares) {
        Square currentSquare = getSquare(squares, getPosition());
        Snake snake = currentSquare.getSnake();
        snake.killSnake();
        Square snakeHeadSquare = getSquare(squares, snake.getPosition());
        currentSquare.removePiece(snake);
        snakeHeadSquare.removePiece(snake);
    }
    //endregion

    public void setParalyzedTurns(int paralyzedTurns) {
        this.paralyzedTurns = paralyzedTurns;
    }

    public void setParalyzedAtTurn(int paralyzedAtTurn) {
        this.paralyzedAtTurn = paralyzedAtTurn;
    }

    public void addToLadderClimbedList(Ladder ladder) {
        ladderClimbedList.add(ladder);
    }

    public boolean isUnkillable() {
        return unkillable;
    }

    public void setUnkillable(boolean unkillable) {
        this.unkillable = unkillable;
    }

    public List<Ladder> getLadderClimbedList() {
        return ladderClimbedList;
    }

}
