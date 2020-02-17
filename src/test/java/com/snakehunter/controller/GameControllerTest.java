//package com.snakehunter.controller;
//
//import com.snakehunter.GameContract.GameModel;
//import com.snakehunter.GameContract.GameView;
//import com.snakehunter.GameStage;
//import com.snakehunter.model.piece.Snake;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * @author WeiYi Yu
// * @date 2019-09-02
// */
//@RunWith(MockitoJUnitRunner.class)
//public class GameControllerTest {
//
//    private GameController gameController;
//
//    @Mock
//    private GameView gameView;
//
//    @Mock
//    private GameModel gameModel;
//
//    @Before
//    public void setUp() throws Exception {
//        gameController = new GameController(gameView, gameModel);
//    }
//
//    @Test
//    public void whenAddSnakeClick_thenCallShowSnakeBuilder() {
//        // when
//        gameController.onAddSnakeClick();
//
//        // then
//        verify(gameView).showSnakeBuilder();
//    }
//
//    @Test
//    public void whenSnakeBuilt_thenCallAddSnake() {
//        // given
//        Snake snake = new Snake(10, 5);
//
//        // when
//        gameController.onSnakeBuilt(snake);
//
//        // then
//        verify(gameModel).addSnake(snake);
//    }
//
//    @Test
//    public void whenAddHumansClick_thenCallShowHumanBuilder() {
//        // when
//        gameController.onAddHumansClick();
//
//        // then
//        verify(gameView).showHumanBuilder();
//    }
//
//    @Test
//    public void givenGameIsReady_whenStartClick_thenHideTheSettingPanel_andCallNextTurn() {
//        // given
//        when(gameModel.isGameReady()).thenReturn(true);
//
//        // when
//        try {
//            gameController.onStartClick();
//        }
//        catch (GameNotReadyException e) {
//            System.out.println(e.getMessage());
//        }
//
//        // then
//        verify(gameView).hideSettingPanel();
//        verify(gameModel).setGameStage(GameStage.SECOND);
//        verify(gameModel).nextTurn();
//    }
//
//    @Test
//    public void givenGameIsNotReady_whenStartClick_thenCallShowErrorDialog() {
//        // given
//        when(gameModel.isGameReady()).thenReturn(false);
//
//        // when
//        try {
//            gameController.onStartClick();
//        } catch (GameNotReadyException e){
//            System.out.println(e.getMessage());
//        }
//
//        // then
//        verify(gameView).showErrorDialog(anyString());
//    }
//
//    @Test
//    public void givenGameStageIsSecond_whenDiceClick_thenCallRollTheDice() {
//        // given
//        when(gameModel.getGameStage()).thenReturn(GameStage.SECOND);
//
//        // when
//        gameController.onDiceClick();
//
//        // then
//        verify(gameView).rollTheDice();
//    }
//
//    @Test
//    public void givenGameStageIsInitial_whenDiceClick_thenDoNothing() {
//        // given
//        when(gameModel.getGameStage()).thenReturn(GameStage.INITIAL);
//
//        // when
//        gameController.onDiceClick();
//
//        // then
//        verify(gameView, times(0)).rollTheDice();
//    }
//
//    @Test
//    public void givenDiceRolls5_whenDiceRolled_thenMoveThePlayer_andNextTurn() {
//        // when
//        gameController.onDiceRolled(0, 5);
//
//        // then
//        verify(gameModel).movePlayer(0,5);
//        verify(gameModel).nextTurn();
//    }
//
//    @Test
//    public void whenonNumOfHumansEntered_thenAddHumans() {
//        // when
//        gameController.onNumOfHumansEntered(4);
//
//        // then
//        verify(gameModel).addHumans(4);
//    }
//}