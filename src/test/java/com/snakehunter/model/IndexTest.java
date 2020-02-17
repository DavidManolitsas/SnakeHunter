//package com.snakehunter.model;
//
//import com.snakehunter.GameContract.GameModel;
//import com.snakehunter.model.piece.Snake;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
///**
// * @author David Manolitsas
// * @project SnakeHunting
// * @date 2019-09-15
// */
//public class IndexTest {
//
//    Snake snake, snake1;
//    GameModelImpl gameModel;
//    Square[][] squares;
//
//    @Before
//    public void setUp() throws Exception {
//        snake = new Snake (65, 46);
//        snake1 = new Snake (97, 75 );
//        gameModel = new GameModelImpl();
//        initSquare();
//    }
//
//    //[col][row]
//
//    //snake move up test
//    @Test
//    public void snakeMoveUp(){
//        //Actions
//        snake.moveUp(squares, squares[4][6]);
//
//        //actual results
//        int testHead = snake.getPosition();
//        int testTail = snake.getConnectedPosition();
//
//        //assertion
//        assertEquals(76, testHead);
//        assertEquals(55, testTail);
//    }
//
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    public void initSquare() {
//        squares = new Square[10][10];
//
//        int x = 0; //col
//        int y = 0; //row
//        int increment = 1;
//
//        for (int i = 1; i <= 100; i++) {
//            squares[x][y] = new Square(i, x, y);
//            if (i % 10 == 0) {
//                increment = -increment;
//                y++;
//            } else {
//                x += increment;
//            }
//        }
//    }
//}