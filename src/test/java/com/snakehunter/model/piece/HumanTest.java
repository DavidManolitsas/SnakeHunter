//package com.snakehunter.model.piece;
//
//import com.snakehunter.model.Square;
//import com.snakehunter.model.exceptions.LadderClimbedException;
//import com.snakehunter.model.exceptions.MaxClimbNumExceedException;
//import com.snakehunter.model.exceptions.MaxPositionExceedException;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
///**
// * @author WeiYi Yu
// * @date 2019-09-17
// */
//public class HumanTest {
//
//    private Square[][] squares;
//
//    @Before
//    public void setUp() throws Exception {
//        squares = getSquares();
//    }
//
//    //region test basic move
//    @Test
//    public void givenHumanAtPosition1_whenMove1Step_thenHumanMoveCorrectly() throws MaxPositionExceedException {
//        // given
//        int steps = 1;
//        Human human = new Human(1);
//
//        // when
//        String message = human.move(squares, steps);
//
//        // then
//        assertTrue(getSquare(2).hasPiece(human));
//        assertEquals(2, human.getPosition());
//        assertEquals("Move to position 2", message);
//    }
//
//    @Test
//    public void givenHumanAtPosition99_whenMove1Steps_thenHumanMoveCorrectly() throws MaxPositionExceedException {
//        // given
//        int steps = 1;
//        Human human = new Human(99);
//
//        // when
//        String message = human.move(squares, steps);
//
//        // then
//        assertTrue(getSquare(100).hasPiece(human));
//        assertEquals(100, human.getPosition());
//        assertEquals("Move to position 100", message);
//    }
//
//    @Test(expected = MaxPositionExceedException.class)
//    public void givenHumanAtPosition99_whenMove2Steps_thenThrowMaxPositionExceedException() throws
//            MaxPositionExceedException {
//        // given
//        int steps = 2;
//        Human human = new Human(99);
//
//        // when
//        human.move(squares, steps);
//    }
//
//    @Test(expected = MaxPositionExceedException.class)
//    public void givenHumanAtPosition95_whenMove6Steps_thenThrowMaxPositionExceedException() throws
//            MaxPositionExceedException {
//        // given
//        int steps = 6;
//        Human human = new Human(95);
//
//        // when
//        human.move(squares, steps);
//    }
//    //endregion
//
//    //region test human climb ladders
//    @Test
//    public void givenHumanHasZeroLadderClimbed_whenLandOnLadderBottom_thenHumanClimbToTheLadderTop() throws
//            MaxPositionExceedException {
//        // given
//        int steps = 6;
//        Human human = new Human(10);
//        Ladder ladder = new Ladder(16, 25);
//        getSquare(16).addPiece(ladder);
//
//        // when
//        String message = human.move(squares, steps);
//
//        // then
//        assertTrue(getSquare(25).hasPiece(human));
//        assertEquals(25, human.getPosition());
//        assertEquals("Move to position 16 then climb a ladder to position 25", message);
//    }
//
//    @Test
//    public void givenHumanHasOneLadderClimbed_whenLandOnLadderBottom_thenHumanClimbToTheLadderTop() throws
//            MaxPositionExceedException {
//        // given
//        Human human = new Human(10);
//        human.getLadderClimbedList().add(new Ladder(50, 60));
//
//        Ladder ladder = new Ladder(16, 25);
//        getSquare(16).addPiece(ladder);
//
//        // when
//        String message = human.move(squares, 6);
//
//        // then
//        assertTrue(getSquare(25).hasPiece(human));
//        assertEquals(25, human.getPosition());
//        assertEquals("Move to position 16 then climb a ladder to position 25", message);
//    }
//
//    @Test
//    public void givenHumanHasThreeLadderClimbed_whenLandOnLadderBottom_thenHumanStayAtLadderBottom() throws
//            MaxPositionExceedException {
//        // given
//        Human human = new Human(10);
//        human.getLadderClimbedList().add(new Ladder(50, 60));
//        human.getLadderClimbedList().add(new Ladder(70, 80));
//        human.getLadderClimbedList().add(new Ladder(80, 90));
//
//        Ladder ladder = new Ladder(16, 25);
//        getSquare(16).addPiece(ladder);
//
//        // when
//        String message = human.move(squares, 6);
//
//        // then
//        assertTrue(getSquare(16).hasPiece(human));
//        assertEquals(16, human.getPosition());
//        assertEquals("Move to position 16\nMaximum climb number of ladders exceeded.", message);
//    }
//
//    @Test
//    public void givenHumanHas1LadderClimbed_whenLandOnTheSameLadderAgain_thenHumanStayAtLadderBottom() throws
//            MaxPositionExceedException {
//        // given
//        Human human = new Human(10);
//        Ladder ladder = new Ladder(16, 25);
//        human.getLadderClimbedList().add(ladder);
//
//        getSquare(16).addPiece(ladder);
//
//        // when
//        String message = human.move(squares, 6);
//
//        // then
//        assertTrue(getSquare(16).hasPiece(human));
//        assertEquals(16, human.getPosition());
//        assertEquals("Move to position 16\nAlready climbed this ladder before.", message);
//    }
//    //endregion
//
//    //region test human swallowed by snake
//    @Test
//    public void whenHumanLandOnSnakeHead_thenHumanSwallowedToSnakeTail_andParalyzed() throws
//            MaxPositionExceedException {
//        // given
//        Human human = new Human(10);
//        Snake snake = new Snake(16, 5);
//        getSquare(16).addPiece(snake);
//
//        // when
//        String message = human.move(squares, 6);
//
//        // then
//        assertTrue(getSquare(5).hasPiece(human));
//        assertEquals(5, human.getPosition());
//        assertTrue(human.isParalyzed());
//        assertEquals("Move to position 16 then swallowed by a snake and back to position 5", message);
//    }
//    //endregion
//
//    //region test add ladders climbed
//    @Test(expected = MaxClimbNumExceedException.class)
//    public void givenClimbedThreeLadders_wheAddLadderClimbed_thenThrowException() throws MaxClimbNumExceedException,
//            LadderClimbedException {
//        // given
//        Human human = new Human(1);
//        human.addLadderClimbed(new Ladder(10, 20));
//        human.addLadderClimbed(new Ladder(30, 40));
//        human.addLadderClimbed(new Ladder(50, 60));
//
//        // when
//        human.addLadderClimbed(new Ladder(70, 80));
//    }
//
//    @Test(expected = LadderClimbedException.class)
//    public void givenLadders_wheAddLadderClimbedBefore_thenThrowException() throws MaxClimbNumExceedException,
//            LadderClimbedException {
//        // given
//        Human human = new Human(1);
//        Ladder ladder = new Ladder(10, 20);
//        human.addLadderClimbed(ladder);
//
//        // when
//        human.addLadderClimbed(ladder);
//    }
//    //endregion
//
//    private Square[][] getSquares() {
//        Square[][] squares = new Square[10][10];
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
//        return squares;
//    }
//
//    private Square getSquare(int squareNo) {
//        int x, y;
//
//        y = (int) Math.ceil(squareNo / 10f) - 1;
//
//        if (y % 2 == 0) {   // Odd row
//            x = squareNo % 10 - 1;
//            if (x == -1) {
//                x = 9;
//            }
//        } else {    // Even row
//            x = 10 - squareNo % 10;
//            if (x == 10) {
//                x = 0;
//            }
//        }
//        return squares[x][y];
//    }
//}