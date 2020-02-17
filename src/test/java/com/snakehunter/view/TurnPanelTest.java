package com.snakehunter.view;

import com.snakehunter.model.GameModel;
import com.snakehunter.controller.GameController;
import com.snakehunter.controller.GameNotReadyException;
import com.snakehunter.model.GameModelImpl;
import com.snakehunter.model.SaveLoadGame;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Piece;
import com.snakehunter.model.piece.Snake;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Aspen
 * @date 2019-09-23
 */
public class TurnPanelTest {

    GameModel gameModel = new GameModelImpl();
    GameView gameView = new GameViewImpl(gameModel);
    SaveLoadGame saveLoadGame = new SaveLoadGame();
    GameController gameController = new GameController(gameView, gameModel, saveLoadGame);

    @Before
    public void setUp() throws Exception {

        gameModel.setOnDataChangedListener(gameView);
        gameView.setOnViewEventListener(gameController);

        Snake s1 = new Snake(34, 27);
        Snake s2 = new Snake(74, 63);
        Ladder l1 = new Ladder(23, 46);
        Ladder l2 = new Ladder(45, 63);
        gameModel.addSnake(s1);
        gameModel.addSnake(s2);
        gameModel.addLadder(l1);
        gameModel.addLadder(l2);

        gameModel.addHumans(4);
    }


    @Test
    public void testThatRelevantErrorMessageShownOnStartClick(){
        try {
            gameController.onStartClick();
            fail();
        } catch (Exception e){
            System.out.println("yep, game not ready " + e.getMessage());
        }
    }

    @Test
    public void testThatGameIsReady(){
        assertTrue(gameModel.isGameReady());
    }

    @Test
    public void testThatTurnPanelIsHiddenOnStart() {
        assertEquals(false, gameView.getTurnPanel().isVisible());
    }




    @Test
    public void testThatTurnPanelHasHumanButtonsToBegin(){
        try {
           gameModel.addSnake(new Snake(30, 23));
           gameModel.addSnake(new Snake(87, 65));
           gameModel.addSnake(new Snake(76, 48));

           gameModel.addLadder(new Ladder(47, 61));
           gameModel.addLadder(new Ladder(12, 32));
           gameModel.addLadder(new Ladder(64, 78));

            gameController.onStartClick();
        } catch (GameNotReadyException e){
            System.out.println(e.getMessage());
        }


        List<JButton> buttons = new ArrayList<>();

        for(Component c : gameView.getTurnPanel().getComponents()){
            System.out.println(c);
            if (c instanceof JButton){
                buttons.add((JButton)c);
            }
        }

        assertEquals(3, buttons.size());
    }


    @Test
    public void testThatTurnNoIsCorrectlyDisplaying(){
        try {
            gameModel.addSnake(new Snake(30, 23));
            gameModel.addSnake(new Snake(87, 65));
            gameModel.addSnake(new Snake(76, 48));

            gameModel.addLadder(new Ladder(47, 61));
            gameModel.addLadder(new Ladder(12, 32));
            gameModel.addLadder(new Ladder(64, 78));
            gameController.onStartClick();
        } catch (GameNotReadyException e){
            System.out.println(e.getMessage());
        }
         assertEquals("Turn No: 1/50", gameView.getTurnPanel().getTurnNoLabel().getText());

        gameController.onDiceRolled(2, 3);

        assertEquals("Turn No: 1/50", gameView.getTurnPanel().getTurnNoLabel().getText());

        gameController.onSnakeMove(2, 0);

        assertEquals("Turn No: 2/50", gameView.getTurnPanel().getTurnNoLabel().getText());

        gameController.onDiceRolled(2, 3);
        gameController.onSnakeMove(2, 0);

        assertEquals("Turn No: 3/50", gameView.getTurnPanel().getTurnNoLabel().getText());
    }


    @Test
    public void testThatHumanMoverChoosesCorrectHuman(){
        try {
            gameModel.addSnake(new Snake(30, 23));
            gameModel.addSnake(new Snake(87, 65));
            gameModel.addSnake(new Snake(76, 48));

            gameModel.addLadder(new Ladder(47, 61));
            gameModel.addLadder(new Ladder(12, 32));
            gameModel.addLadder(new Ladder(64, 78));
            gameController.onStartClick();
        } catch (GameNotReadyException e){
            System.out.println(e.getMessage());
        }
        List<Piece> pieces = new ArrayList<>(gameModel.getCurrentPlayer().getPieceList());
        int initPosition = pieces.get(3).getPosition();
        gameController.onDiceRolled(3, 4);
        assertEquals(initPosition + 4, pieces.get(3).getPosition());
    }

    @After
    public void tearDown() throws Exception {
    }
}