package com.snakehunter.view;

import com.snakehunter.Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * @author WeiYi Yu
 * @date 2019-08-24
 */
public class DiceView
        extends ClickablePanel {

    private static final int DICE_LENGTH = 45;
    private static final int DOT_LENGTH = 5;

    public static final int DICE_NUM_MIN = 1;
    public static final int DICE_NUM_MAX = 6;

    private ViewEventListener listener;

    private Random random;
    private int lastNum = 1;
    private int storedValue;
    private boolean usedStoredValue = true;

    public DiceView() {
        setSize(DICE_LENGTH, DICE_LENGTH);
        random = new Random();
        addMouseListener(this);
    }

    public void setOnViewEventListener(ViewEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (listener != null) {
            listener.onDiceClick();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(Color.RED);
        graphics.fill3DRect(0, 0, DICE_LENGTH, DICE_LENGTH, false);
        graphics.setColor(Color.BLACK);

        if (lastNum == 1) {
            graphics.fillOval(20, 20, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 2) {
            graphics.fillOval(10, 20, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 20, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 3) {
            graphics.fillOval(20, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 4) {
            graphics.fillOval(10, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 5) {
            graphics.fillOval(10, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(20, 20, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 6) {
            graphics.fillOval(10, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 20, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 20, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        }
    }

    public int getLastNum() {
        return lastNum;
    }

    public synchronized void roll() {
        if (isEnabled()) {
            setEnabled(false);
            lastNum = 1;
            if (Main.isDebugMode()) {
                new Thread(() -> {
                    String s = JOptionPane.showInputDialog("DEBUG - input dice number");
                    try {
                        lastNum = Integer.parseInt(s);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Invalid dice number");
                    }
                    System.out.println(lastNum);
                    storedValue = lastNum;
                }).start();

                setEnabled(true);
            } else {

                new Thread(() -> {
                    int num;

                    if (usedStoredValue) {
                        // fake dice animation
                        for (int i = 1; i <= 20; i++) {
                            do {
                                num = getRandomNumber(DICE_NUM_MIN, DICE_NUM_MAX);
                            } while (lastNum == num);
                            lastNum = num;

                            repaint();
                            // This creates the fake dice roll animation
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                System.out.println("Dice roll exception " + e);
                            }
                        }
                        System.out.println(lastNum);
                        storedValue = lastNum;
                    } else {
                        lastNum = storedValue;
                    }
                }).start();
            }
        }
    }

    void moveSelectedPiece(String selectedPiece) {

        int playerIndex;

        boolean validPlayerChosen = false;
        setEnabled(true);
        do {
            try {

                if (selectedPiece == null) {
                    System.out.println("cancelled");
                    usedStoredValue = false;
                    return;
                }
                playerIndex = Integer.parseInt(selectedPiece);
                listener.onDiceRolled(playerIndex - 1, lastNum);
                validPlayerChosen = true;
                usedStoredValue = true;
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(this, "Please input a valid player number");
            } catch (Exception e) {
                JOptionPane
                        .showMessageDialog(this, "You can't move humans above square 100! - skipping turn");
                validPlayerChosen = true;
                usedStoredValue = true;
                listener.onDiceRolled(-2, lastNum);
            }
        } while (!validPlayerChosen);
    }

    private int getRandomNumber(int min, int max) {
        return random.nextInt(max) + min;
    }

}