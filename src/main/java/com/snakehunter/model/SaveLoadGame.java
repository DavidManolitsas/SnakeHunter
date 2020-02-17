package com.snakehunter.model;

import com.snakehunter.GameStage;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;
import com.snakehunter.view.GameView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SaveLoadGame {

    private static final int SNAKE_GUARD_LIMIT = 3;
    private static final int SNAKE_LIMIT = 5;
    private static final int LADDER_LIMIT = 5;
    private static final int NUM_OF_HUMANS = 4;
    private static final int NOT_PLACED_SNAKE_GUARD_POSITION = -1;
    private static final int FIRST_VALID_SNAKE_PLACEMENT_SQUARE = 3;
    private static final int HUMAN_LADDER_CLIMBED_MINIMUM = 3;
    private static final int PLACEHOLDER_FOR_NO_LADDER_CLIMBED = -1;

    private GameModelImpl gameModel;
    private GameView gameView;

    private static final String DELIMITER = ":";
    private static final String saveFileNameTemplate = "savefile";
    private int saveNumber = 1;
    private String saveFileName = saveFileNameTemplate + saveNumber;
    private File file = new File("src/main/java/com/snakehunter/file/" + saveFileName + ".txt");
    private static final String FOLDER_PATH = "src/main/java/com/snakehunter/file";
    private File saveFile = null;

    private String humanName;
    private String snakeName;

    public SaveLoadGame() {
    }

    public void saveGame() {
        try {
            if (saveFile != null) {
                file = saveFile;
            } else {
                while (file.exists()) {
                    saveNumber++;
                    saveFileName = saveFileNameTemplate + saveNumber;
                    file = new File("src/main/java/com/snakehunter/file/" + saveFileName + ".txt");
                }
            }

            FileWriter fileWriter = new FileWriter(file, false);

            fileWriter.write("humanName" + DELIMITER + gameModel.getHumanPlayer().getName());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("snakeName" + DELIMITER + gameModel.getSnakePlayer().getName());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("stage" + DELIMITER + gameModel.getGameStage());
            fileWriter.write(System.lineSeparator());

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("snakePos");
            for (Snake snakeInList : gameModel.getSnakeList()) {
                stringBuilder.append(DELIMITER).append(snakeInList.getConnectedPosition()).append(DELIMITER)
                        .append(snakeInList.getPosition());
            }
            writing(stringBuilder, fileWriter);

            stringBuilder.append("ladderPos");
            for (Ladder ladderInList : gameModel.getLadderList()) {
                stringBuilder.append(DELIMITER).append(ladderInList.getPosition()).append(DELIMITER)
                        .append(ladderInList.getConnectedPosition());
            }
            writing(stringBuilder, fileWriter);

            List<Human> humanList = gameModel.getHumanList();

            stringBuilder.append("piecePos");
            for (Human humanInlist : humanList) {
                stringBuilder.append(DELIMITER).append(humanInlist.getPosition());
            }
            writing(stringBuilder, fileWriter);

            stringBuilder.append("pieceParalyzedTurnRemaining");
            for (Human humanInlist : humanList) {
                stringBuilder.append(DELIMITER).append(humanInlist.getParalyzeTurns());
            }
            writing(stringBuilder, fileWriter);

            stringBuilder.append("climbedLadder");
            for (Human humanInlist : humanList) {
                List<Ladder> ladderClimbedList = humanInlist.getLadderClimbedList();

                if (ladderClimbedList.size() == 0) {
                    for (int i = 0; i < HUMAN_LADDER_CLIMBED_MINIMUM; i++) {
                        stringBuilder.append(DELIMITER).append(PLACEHOLDER_FOR_NO_LADDER_CLIMBED);
                    }
                } else {
                    for (Ladder ladder : ladderClimbedList) {
                        stringBuilder.append(DELIMITER).append(ladder.getPosition());
                    }

                    for (int i = 0; i < HUMAN_LADDER_CLIMBED_MINIMUM - ladderClimbedList.size(); i++) {
                        stringBuilder.append(DELIMITER).append(PLACEHOLDER_FOR_NO_LADDER_CLIMBED);
                    }
                }
            }
            writing(stringBuilder, fileWriter);

            stringBuilder.append("snakeGuardPos");
            Square[][] twoDSquareArray = gameModel.getSquares();
            if (gameModel.getRemainingGuards() == SNAKE_GUARD_LIMIT) {
                for (int i = 0; i < SNAKE_GUARD_LIMIT; i++) {
                    stringBuilder.append(DELIMITER).append(NOT_PLACED_SNAKE_GUARD_POSITION);
                }
            } else {
                int loopCounter = 0;

                for (Square[] squareArray : twoDSquareArray) {
                    for (Square square : squareArray) {
                        if (square.isGuarded()) {
                            stringBuilder.append(DELIMITER).append(square.getSquareNo());
                            loopCounter++;
                        }
                    }
                }

                for (int i = loopCounter; i < SNAKE_GUARD_LIMIT; i++) {
                    stringBuilder.append(DELIMITER).append(NOT_PLACED_SNAKE_GUARD_POSITION);
                }
            }
            writing(stringBuilder, fileWriter);

            fileWriter.write("turnNumber" + DELIMITER + gameModel.getNumOfTurns());

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        saveFile = file;
        JOptionPane.showMessageDialog(new JFrame(), "Game Saved Successfully\n" + "Your save file is " + file.getName(), "Game Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    private void writing(StringBuilder stringBuilder, FileWriter fileWriter) throws IOException {
        fileWriter.write(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
        fileWriter.write(System.lineSeparator());
    }

    public void loadGame() {
        JFileChooser jFileChooser = new JFileChooser(FOLDER_PATH);

        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = jFileChooser.getSelectedFile();

            getPlayerNameInFile(file);

            if (gameModel.getHumanPlayer().getName().equals(humanName) && gameModel.getSnakePlayer().getName().equals(snakeName)) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String st;
                    String[] stringArray;

                    while (true) {
                        try {
                            if ((st = br.readLine()) == null) {
                                break;
                            }

                            stringArray = st.split(DELIMITER);

                            switch (stringArray[0]) {
                                case "humanName":
                                    gameModel.getHumanPlayer().setName(stringArray[1]);
                                    break;
                                case "snakeName":
                                    gameModel.getSnakePlayer().setName(stringArray[1]);
                                    break;
                                case "stage":
                                    setStage(stringArray[1]);
                                    break;
                                case "snakePos":
                                    setSnakePos(stringArray);
                                    break;
                                case "ladderPos":
                                    setLadderPos(stringArray);
                                    break;
                                case "piecePos":
                                    setPiecePos(stringArray);
                                    break;
                                case "pieceParalyzedTurnRemaining":
                                    setParalyzedTurn(stringArray);
                                    break;
                                case "climbedLadder":
                                    setClimbedLadder(stringArray);
                                    break;
                                case "snakeGuardPos":
                                    setGuardPos(stringArray);
                                    break;
                                case "turnNumber":
                                    setTurnNumber(stringArray[1]);
                                    break;
                                default:
                                    break;
                            }
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(new JFrame(), e.toString(), "IOException", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    saveFile = file;
                    gameView.hideSettingPanel();
                    gameView.showTurnPanel();
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(new JFrame(), "File not found", "File not found", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (file.getName().contains(saveFileNameTemplate)) {
                    JOptionPane.showMessageDialog(new JFrame(), "Incorrect save file for current players", "Incorrect save file", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Not a valid save file", "Invalid save file", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    private void getPlayerNameInFile(File file) {
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String[] stringArray;

            while (true) {
                try {
                    if ((st = br.readLine()) == null) {
                        break;
                    }

                    stringArray = st.split(DELIMITER);

                    switch (stringArray[0]) {
                        case "humanName":
                            humanName = stringArray[1];
                            break;
                        case "snakeName":
                            snakeName = stringArray[1];
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "No save file found in folder", "No save file found", JOptionPane.WARNING_MESSAGE);
        } catch (NullPointerException nullEx) {
        }
    }

    private void setStage(String stage) {
        GameStage gameStage;

        if (stage.equals("SECOND")) {
            gameStage = GameStage.SECOND;
        } else {
            gameStage = GameStage.FINAL;
        }

        gameModel.setGameStage(gameStage);
        gameView.updateStage(gameStage);
    }

    private void setSnakePos(String[] stringArray) {

        for (Square[] squares : gameModel.getSquares()) {
            for (Square square : squares) {
                if (square.getPieceList() != null) {
                    square.getPieceList().clear();
                }
            }
        }

        gameModel.getSnakeList().clear();

        for (int i = 0; i < SNAKE_LIMIT; i++) {
            gameModel.addSnake(new Snake(Integer.parseInt(stringArray[i * 2 + 2]), Integer.parseInt(stringArray[i * 2 + 1])));

            if (gameModel.getSnakeList().size() != i + 1) {
                for (int j = FIRST_VALID_SNAKE_PLACEMENT_SQUARE; j < gameModel.getSquares().length * gameModel.getSquares()[0].length; j++) {
                    if (gameModel.getSquare(j).getPieceList().isEmpty()
                            && gameModel.getSquare(j - 1).getPieceList().isEmpty()) {
                        Snake snake = new Snake(j, j - 1);

                        gameModel.addSnake(snake);
                        gameModel.getSquare(j).getPieceList().remove(snake);

                        snake.setPosition(Integer.parseInt(stringArray[i * 2 + 2]));
                        snake.setConnectedPosition(Integer.parseInt(stringArray[i * 2 + 1]));
                        gameModel.getSquare(Integer.parseInt(stringArray[i * 2 + 2])).addPiece(snake);
                        break;
                    }
                }
            }
        }
    }

    private void setLadderPos(String[] stringArray) {
        gameModel.getLadderList().clear();

        for (int i = 0; i < LADDER_LIMIT; i++) {
            Ladder ladder =
                    new Ladder(Integer.parseInt(stringArray[i * 2 + 1]), Integer.parseInt(stringArray[i * 2 + 2]));
            gameModel.addLadder(ladder);
        }
    }

    private void setPiecePos(String[] stringArray) {
        gameModel.initHumans(NUM_OF_HUMANS);

        for (int i = 0; i < NUM_OF_HUMANS; i++) {

            gameModel.getHumanList().get(i).setPosition(Integer.parseInt(stringArray[i + 1]));
        }
    }

    private void setParalyzedTurn(String[] stringArray) {
        for (int i = 0; i < NUM_OF_HUMANS; i++) {
            gameModel.getHumanList().get(i).setParalyzedTurns(Integer.parseInt(stringArray[i + 1]));
        }
    }

    private void setClimbedLadder(String[] stringArray) {
        for (int i = 0; i < NUM_OF_HUMANS; i++) {
            for (int j = 1; j <= HUMAN_LADDER_CLIMBED_MINIMUM; j++) {
                int ladderPosition = Integer.parseInt(stringArray[i * HUMAN_LADDER_CLIMBED_MINIMUM + j]);

                if (ladderPosition != -1) {
                    for (Ladder ladder : gameModel.getLadderList()) {
                        if (ladder.getPosition() == ladderPosition) {
                            gameModel.getHumanList().get(i).addToLadderClimbedList(ladder);
                        }
                    }
                }

            }
        }

    }

    private void setGuardPos(String[] stringArray) {

        for (int i = 0; i < SNAKE_GUARD_LIMIT; i++) {

            int guardPos = Integer.parseInt(stringArray[i + 1]);

            if (guardPos != NOT_PLACED_SNAKE_GUARD_POSITION) {
                gameModel.addGuard(guardPos);
            }
        }
        gameView.updateGuardNo();
    }

    private void setTurnNumber(String s) {
        int turn = Integer.parseInt(s);
        gameModel.setNumOfTurns(turn);
        gameView.updateTurnNo(turn);
    }

    public void setGameModel(GameModelImpl gameModel) {
        this.gameModel = gameModel;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}
