package com.snakehunter.model;

import com.snakehunter.model.DataChangedListener;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Piece;
import com.snakehunter.model.piece.Snake;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author WeiYi Yu
 * @date 2019-09-03
 */
@RunWith(MockitoJUnitRunner.class)
public class GameModelImplTest {

    private GameModel gameModel;

    @Mock
    private DataChangedListener listener;

    @Before
    public void setUp() throws Exception {
        gameModel = new GameModelImpl();
        gameModel.setOnDataChangedListener(listener);
    }

    @Test
    public void givenValidSnakeOnRandomPosition_whenAddSnake_thenSquareAddedCorrectly() {
        // given
        Snake snake = new Snake(31, 1);
        Snake snake2 = new Snake(99, 69);
        Snake snake3 = new Snake(50, 49);

        // when
        gameModel.addSnake(snake);
        gameModel.addSnake(snake2);
        gameModel.addSnake(snake3);

        // then
        verify(listener).onSnakeAdded(snake);
        verify(listener).onSnakeAdded(snake2);
        verify(listener).onSnakeAdded(snake3);

        Assert.assertEquals(snake, gameModel.getSquare(31).getSnake());
        Assert.assertEquals(snake2, gameModel.getSquare(99).getSnake());
        Assert.assertEquals(snake3, gameModel.getSquare(50).getSnake());
    }

    @Test
    public void givenInvalidSnake_whenAddSnake_thenShowError() {
        // given
        Snake headAt1 = new Snake(1, 0);
        Snake tailAt0 = new Snake(25, 0);
        Snake lengthOver30 = new Snake(32, 1);
        Snake tailGreaterThanHead = new Snake(32, 40);
        Snake headAt100 = new Snake(100, 80);
        Snake headEqualsTail = new Snake(50, 50);


        // when
        gameModel.addSnake(null);
        verify(listener).onAddSnakeFailed("Please enter valid positions.");
        reset(listener);

        gameModel.addSnake(headAt1);
        verify(listener).onAddSnakeFailed("Please enter valid positions.");
        reset(listener);

        gameModel.addSnake(tailAt0);
        verify(listener).onAddSnakeFailed("Please enter valid positions.");

        gameModel.addSnake(tailGreaterThanHead);
        verify(listener).onAddSnakeFailed("Head's position need to greater than tail's.");

        gameModel.addSnake(headAt100);
        verify(listener).onAddSnakeFailed("Cannot put a snake at position 100.");

        gameModel.addSnake(headEqualsTail);
        verify(listener).onAddSnakeFailed("Cannot put the head and tail in the same position.");

        gameModel.addSnake(lengthOver30);
        verify(listener).onAddSnakeFailed("Snake's length cannot greater than 30.");
    }

    @Test
    public void givenValidLadderOnRandomPosition_whenAddSnake_thenSquareAddedCorrectly() {
        // FIXME: Ladder class need to change

        // given
        Ladder ladder = new Ladder(2, 20);
        Ladder ladder2 = new Ladder(31, 40);
        Ladder ladder3 = new Ladder(69, 80);

        // when
        gameModel.addLadder(ladder);
        gameModel.addLadder(ladder2);
        gameModel.addLadder(ladder3);

        // then
        verify(listener).onLadderAdded(ladder);
        verify(listener).onLadderAdded(ladder2);
        verify(listener).onLadderAdded(ladder3);

        Assert.assertEquals(ladder, gameModel.getSquare(2).getLadder());
        Assert.assertEquals(ladder2, gameModel.getSquare(31).getLadder());
        Assert.assertEquals(ladder3, gameModel.getSquare(69).getLadder());
    }

    @Test
    public void given0Guard_whenAddGuard_thenSquareGuardedCorrectly() {
        // given
        gameModel.setNumOfGuards(0);

        // when
        gameModel.addGuard(10);

        // then
        verify(listener).onGuardAdded(10);
        assertTrue(gameModel.getSquare(10).isGuarded());
    }

    @Test
    public void given3Guards_whenAddGuard_thenReturnExceedMaxGuards() {
        // given
        gameModel.setNumOfGuards(3);

        // when
        gameModel.addGuard(10);

        // then
        verify(listener).onExceedMaxNumOfGuards();
        assertFalse(gameModel.getSquare(10).isGuarded());
    }

    @Test
    public void givenValidNumOfHumans_whenAddHumans_thenAddedCorrectly() {
        // when
        gameModel.addHumans(2);

        assertEquals(2, gameModel.getSquare(1).getPieceList().size());

        for (Piece piece : gameModel.getSquare(1).getPieceList()) {
            assertTrue(piece instanceof Human);
            assertEquals(1, piece.getPosition());
        }
    }

    @Test
    public void givenInvalidNumOfHumans_whenAddHumans_thenCallNumOfHumansEnteredError() {
        // when
        gameModel.addHumans(1);
        gameModel.addHumans(5);

        // then
        verify(listener, times(2)).onNumOfHumansEnteredError();
    }

    // TODO: test for movePlayer
}