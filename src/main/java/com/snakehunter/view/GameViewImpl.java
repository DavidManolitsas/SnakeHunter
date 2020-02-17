package com.snakehunter.view;

import com.snakehunter.model.GameModel;
import com.snakehunter.view.ViewEventListener;
import com.snakehunter.GameStage;
import com.snakehunter.Main;
import com.snakehunter.controller.GameNotReadyException;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class GameViewImpl
        extends JFrame
        implements com.snakehunter.view.GameView,
                   ActionListener {

    private GameModel gameModel;

    private ViewEventListener listener;
    private BoardView boardView;
    private DiceView diceView;
    private SettingPanel settingPanel;
    private TurnPanel turnPanel;
    private GameOverPanel gameOverPanel;

    public GameViewImpl(GameModel gameModel) {
        super("Snakes n Ladders!");
        this.gameModel = gameModel;

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 600);

        Container contentPane = getContentPane();

        boardView = new BoardView(gameModel);
        boardView.setLocation(0, 0);
        contentPane.add(boardView);

        diceView = new DiceView();
        diceView.setLocation(507, 450);
        contentPane.add(diceView);

        settingPanel = new SettingPanel(this);
        settingPanel.setLocation(450, 20);
        contentPane.add(settingPanel);

        turnPanel = new TurnPanel(this, gameModel, this.getBackground());
        turnPanel.setLocation(450, 20);
        turnPanel.setVisible(false);
        contentPane.add(turnPanel);

        gameOverPanel = new GameOverPanel(this, gameModel);
        gameOverPanel.setLocation(450, 20);
        gameOverPanel.setVisible(false);
        contentPane.add(gameOverPanel);

        setVisible(true);
    }

    //region interaction
    @Override
    public void rollTheDice() {
        if (Main.isDebugMode()) {
            diceView.roll();
//            int num = 0;
//            try {
//                num = Integer.parseInt(JOptionPane.showInputDialog("Enter 0 to throw dice. Enter 1 - 6 for Testing
//                ."));
//            } catch (NumberFormatException e) {
//                showErrorDialog("PLease enters a valid number!");
//                return;
//            }
//
//            if (num == 0) {
//                diceView.roll();
//            } else {
//                //TODO add this back in later
//                //listener.onDiceRolled(num);
//            }
        } else {
            diceView.roll();
        }
    }

    @Override
    public void showSnakeBuilder() {
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(0, 2, 2, 2));

        JTextField headField = new LimitTextField();
        JTextField tailField = new LimitTextField();

        pane.add(new JLabel("Head position"));
        pane.add(headField);

        pane.add(new JLabel("Tail position"));
        pane.add(tailField);

        int option = JOptionPane.showConfirmDialog(this, pane, "Add Snake", JOptionPane.OK_CANCEL_OPTION,
                                                   JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String headInput = headField.getText();
            String tailInput = tailField.getText();

            try {
                int head = Integer.parseInt(headInput);
                int tail = Integer.parseInt(tailInput);
                listener.onSnakeBuilt(new Snake(head, tail));
            } catch (NumberFormatException nfe) {
                listener.onSnakeBuilt(null);
            }
        }
    }

    @Override
    public void showLadderBuilder() {
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(0, 2, 2, 2));

        JTextField topField = new LimitTextField();
        JTextField baseField = new LimitTextField();

        pane.add(new JLabel("Top position"));
        pane.add(topField);

        pane.add(new JLabel("Base position"));
        pane.add(baseField);

        int option = JOptionPane.showConfirmDialog(this, pane, "Add Ladder", JOptionPane.OK_CANCEL_OPTION,
                                                   JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String topInput = topField.getText();
            String bottomInput = baseField.getText();

            try {
                int top = Integer.parseInt(topInput);
                int bottom = Integer.parseInt(bottomInput);
                listener.onLadderBuilt(new Ladder(bottom, top));
            } catch (NumberFormatException nfe) {
                listener.onLadderBuilt(null);
            }
        }
    }

    @Override
    public void showHumanBuilder() {
        if (Main.isDebugMode()) {
            int numOfHumans;

            try {
                numOfHumans = Integer.parseInt(JOptionPane.showInputDialog("How many players? (2 ~ 4)"));
            } catch (NumberFormatException e) {
                if (listener != null) {
                    listener.onNumOfHumansEntered(-1);
                }
                return;
            }

            if (listener != null) {
                listener.onNumOfHumansEntered(numOfHumans);
            }
        } else {
            if (listener != null) {
                listener.onNumOfHumansEntered(4);
            }
        }

    }

    @Override
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showTurnPanel() {
        turnPanel.setVisible(true);
    }

    @Override
    public void hideTurnPanel() {
        turnPanel.setVisible(false);
    }

    public void updateTurnNo(int turnNo) {
        turnPanel.updateTurnNo(turnNo);
    }

    @Override
    public void updateParalyzedTurn() {
        turnPanel.updateParalyzedTurn();
    }

    @Override
    public void updateStage(GameStage s) {
        turnPanel.updateStage(s);
    }

    @Override
    public void updateGuardNo() {
        turnPanel.updateGuardNo();
    }

    @Override
    public void hideDicePanel() {
        diceView.setVisible(false);
    }

    @Override
    public void showDicePanel() {
        diceView.setVisible(true);
    }

    @Override
    public void hideSettingPanel() {
        settingPanel.setVisible(false);
    }

    @Override
    public void showSettingPanel() {
        settingPanel.setVisible(true);
    }

    @Override
    public void hideGameOverPanel() {
    }

    @Override
    public void showGameOverPanel(Player p) {
        gameOverPanel.setVisible(true);
        gameOverPanel.updateWinner(p);
    }

    @Override
    public void showGuardPlacer() {
        try {
            int squareNo = Integer.parseInt(JOptionPane.showInputDialog("Enter square number to place guard on:"));
            gameModel.addGuard(squareNo);
        } catch (Exception e) {
            showErrorDialog("Enter a valid square number");
        }
    }
    //endregion

    //region GameModel interaction
    @Override
    public void onSnakeAdded(Snake snake) {
        boardView.addSnake(snake);
    }

    @Override
    public void onLadderAdded(Ladder ladder) {
        boardView.addLadder(ladder);
    }

    @Override
    public void onAddSnakeFailed(String errorMessage) {
        showErrorDialog(errorMessage);
    }

    @Override
    public void onAddLadderFailed(String errorMessage) {
        showErrorDialog(errorMessage);
    }

    @Override
    public void onGuardAdded(int position) {
        // FIXME: try to remove this from the view
        gameModel.nextTurn();
    }

    @Override
    public void showClimbedLadder() {
        turnPanel.showClimbedLadder();
    }

    @Override
    public void onHumanMoved(String message) {
        showInfoDialog(message);
    }

    @Override
    public void onExceedMaxNumOfGuards() {
        showErrorDialog("Cannot place more than 3 guards.");
    }

    @Override
    public void onExceedMaxPosition() {
        showErrorDialog("Exceed max square! Stay at the same square.");
    }

    @Override
    public void onLadderClimbedThresholdException() {
        showErrorDialog("Haven't climbed 3 ladders yet, go back to Square 1.");
    }

    @Override
    public void onNextTurn(Player player) {
        updateGuardNo();
        updateStage(gameModel.getGameStage());
        updateTurnNo(gameModel.getNumOfTurns());

        if (player.isSnake()) {
            hideDicePanel();
            getTurnPanel().hidePieceButtons();
            updateParalyzedTurn();
        } else {

        }
    }

    @Override
    public void onGameOver(Player winner) {
        hideDicePanel();
        hideTurnPanel();
        showGameOverPanel(winner);
    }

    @Override
    public void onNumOfHumansEnteredError() {
        showErrorDialog("Please enter a number between 2 ~ 4.");
    }
    //endregion

    //region Getters
    public TurnPanel getTurnPanel() {
        return turnPanel;
    }
    //endregion

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener == null) {
            return;
        }

        switch (e.getActionCommand()) {
        case "Add Snake":
            listener.onAddSnakeClick();
            break;
        case "Add Ladder":
            listener.onAddLadderClick();
            break;
        case "Add Humans":
            listener.onAddHumansClick();
            break;
        case "Start":
            try {
                listener.onStartClick();
            } catch (GameNotReadyException gnre) {
                JOptionPane.showMessageDialog(this, "Game not yet ready: " + gnre.getMessage());
            }

            break;
        case "Add 5 Random Snakes":
            listener.onRandomSnakeClick();
            break;
        case "Add 5 Random Ladders":
            listener.onRandomLadderClick();
            break;
        case "Add 5 Random S&L":
            listener.onRandomSnakeClick();
            listener.onRandomLadderClick();
            break;
        case "Up":
            listener.onSnakeMove(turnPanel.getSnakeJListSelectedItem(), 0);
            break;
        case "Down":
            listener.onSnakeMove(turnPanel.getSnakeJListSelectedItem(), 1);
            break;
        case "Left":
            listener.onSnakeMove(turnPanel.getSnakeJListSelectedItem(), 2);
            break;
        case "Right":
            listener.onSnakeMove(turnPanel.getSnakeJListSelectedItem(), 3);
            break;
        case "Roll Dice":
            //listener.onDiceShow();
            listener.onDiceClick();
            break;
        case "Place Guard":
            listener.onPlaceGuard();
            break;
            case "Check Climbed Ladder":
                listener.onCheckClimbedLadder();
                break;
        case "Save Game":
            listener.onSaveClick();
            break;
        case "Load Game":
            listener.onLoadClick();
            break;
        case "1":
            diceView.moveSelectedPiece("1");
            break;
        case "2":
            diceView.moveSelectedPiece("2");
            break;
        case "3":
            diceView.moveSelectedPiece("3");
            break;
        case "4":
            diceView.moveSelectedPiece("4");
            break;
        case "Show Valid Moves":
            turnPanel.showValidMoves(turnPanel.getHumanListItem());
            //listener.onKnightClick(turnPanel.getHumanListItem());
            break;

        case "Move Human":
            System.out.println("test2");
            if (turnPanel.getMovesSquareItem() >= 0){
                listener.onMoveKnight(turnPanel.getHumanListItem(), turnPanel.getMovesSquareItem());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a square to move to");
            }
            break;

        case "Play Again":

            break;
        default:
            break;
        }
    }

    @Override
    public void setOnViewEventListener(ViewEventListener listener) {
        this.listener = listener;
        diceView.setOnViewEventListener(listener);
    }
}
