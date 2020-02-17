package com.snakehunter.model.piece;

import com.snakehunter.model.GameModelImpl;
import com.snakehunter.model.Square;
import com.snakehunter.model.exceptions.SnakeMoveOutOfBoundsException;
import com.snakehunter.model.exceptions.SnakeMoveToGuardedSquareException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-09-18
 */
public class SnakeMoveTest {

    Snake snake1, snake2, snake3, snake4;
    GameModelImpl gameModel;
    Square[][] squares;

    /**
     * LEGEND:
     * [col][row]
     * UP = 0;
     * DOWN = 1;
     * LEFT = 2;
     * RIGHT = 3;
     */

    @Before
    public void setUp() throws Exception {
        snake1 = new Snake (63, 41); //left col
        snake2 = new Snake (97, 75 ); //top row
        snake3 = new Snake (25, 6); //bottom row
        snake4 = new Snake (71, 53); //right col
        gameModel = new GameModelImpl();
        initSquare();
    }

    //Positive Tests
    //Move a snake in all four directions
    @Test
    public void snakesValidMoves() throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        //Actions
        snake1.move(squares, 0); //Up
        snake2.move(squares, 1); //Down
        snake3.move(squares, 3); //Right
        snake4.move(squares, 2); //Left

        //Actual
        int testHead1 = snake1.getPosition();
        int testTail1 = snake1.getConnectedPosition();

        int testHead2 = snake2.getPosition();
        int testTail2 = snake2.getConnectedPosition();

        int testHead3 = snake3.getPosition();
        int testTail3 = snake3.getConnectedPosition();

        int testHead4 = snake4.getPosition();
        int testTail4 = snake4.getConnectedPosition();

        //Assertions
        assertEquals(78, testHead1);
        assertEquals(60, testTail1);

        assertEquals(84, testHead2);
        assertEquals(66, testTail2);

        assertEquals(26, testHead3);
        assertEquals(7, testTail3);

        assertEquals(72, testHead4);
        assertEquals(54, testTail4);
    }


    //Positive Test 2
    //Give a single snake multiple movement directions
    @Test
    public void snakeMultiMove() throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        snake1.move(squares, 1); //Down
        snake1.move(squares, 3); //Right
        snake1.move(squares, 3); //Right
        snake1.move(squares, 1); //Down
        snake1.move(squares, 0); //Up

        //Actual
        int testHead = snake1.getPosition();
        int testTail = snake1.getConnectedPosition();

        //Assertions
        assertEquals(56, testHead);
        assertEquals(38, testTail);

    }

    //Negative Tests
    //Snake tries to move off the board
    @Test (expected = SnakeMoveOutOfBoundsException.class)
    public void moveOffBoard() throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        Square currSquare = snake2.getSquare(squares, snake2.getPosition());

        snake2.moveUp(squares, currSquare);
    }

    //Snake tries to move on to a guarded square
    @Test (expected = SnakeMoveToGuardedSquareException.class)
    public void moveToGuardedSquare() throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        squares[3][6].setGuarded(true); //64
        Square currSquare = snake1.getSquare(squares, snake1.getPosition());

        snake1.moveRight(squares, currSquare);
    }


    @After
    public void tearDown() throws Exception {
    }

    public void initSquare() {
        squares = new Square[10][10];

        int x = 0; //col
        int y = 0; //row
        int increment = 1;

        for (int i = 1; i <= 100; i++) {
            squares[x][y] = new Square(i, x, y);
            if (i % 10 == 0) {
                increment = -increment;
                y++;
            } else {
                x += increment;
            }
        }
    }

    //Old Tests

    //Negative Test 1
//    @Test
//    public void snakesMovesOutOfBounds() throws InvalidSnakeMovementException {
//        //Actions
//        snake1.move(squares, 2);
//        snake2.move(squares, 0);
//        snake3.move(squares, 1);
//        snake4.move(squares, 3);
//
//        //Actual
//        int test1 = snake1.getPosition();
//        int test2 = snake2.getPosition();
//        int test3 = snake3.getPosition();
//        int test4 = snake4.getPosition();
//
//        //Assertions
//        assertEquals(63, test1);
//        assertEquals(97, test2);
//        assertEquals(25, test3);
//        assertEquals(71, test4);
//    }
//
//
//    //Negative Test 2
//    @Test
//    public void snakesMovesGuardedSquare() throws InvalidSnakeMovementException {
//        //Actions
//        squares[3][6].setGuarded(true); //64
//        squares[3][8].setGuarded(true); //84
//        squares[4][3].setGuarded(true); //36
//        squares[8][7].setGuarded(true); //72
//
//        snake1.move(squares, 3);
//        snake2.move(squares, 1);
//        snake3.move(squares, 0);
//        snake4.move(squares, 2);
//
//        //Actual
//        int test1 = snake1.getPosition();
//        int test2 = snake2.getPosition();
//        int test3 = snake3.getPosition();
//        int test4 = snake4.getPosition();
//
//        //Assertions
//        assertEquals(63, test1);
//        assertEquals(97, test2);
//        assertEquals(25, test3);
//        assertEquals(71, test4);
//    }

}