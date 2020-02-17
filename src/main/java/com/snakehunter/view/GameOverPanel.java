package com.snakehunter.view;

import com.snakehunter.model.GameModel;
import com.snakehunter.model.piece.Player;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Aspen
 * @date 2019-09-23
 */
public class GameOverPanel
        extends JPanel
        implements ActionListener {
    private GameModel gameModel;
    private ActionListener listener;
    private JLabel gameOverText;
    private JLabel winnerLabel;
    private JButton restartButton;
    private String winnerText = "%s Wins!";


    public GameOverPanel(ActionListener listener, GameModel gameModel) {
        this.listener = listener;
        this.gameModel = gameModel;

        setSize(150, 400);

        gameOverText = new JLabel("Game Over!");
        gameOverText.setPreferredSize(new Dimension(150, 50));
        add(gameOverText);


        winnerLabel = new JLabel(winnerText);
        winnerLabel.setPreferredSize(new Dimension(150, 50));
        add(winnerLabel);

        restartButton = new JButton("Play again");
        restartButton.setPreferredSize(new Dimension(150, 50));
        restartButton.addActionListener(this);
        add(restartButton);
    }

    public void updateWinner(Player winner){
        winnerLabel.setText(String.format(winnerText, "'" + winner.getName().toString() + "'"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }
}
