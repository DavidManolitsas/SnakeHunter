package com.snakehunter.controller;

import com.snakehunter.Main;
import com.snakehunter.model.GameModel;
import com.snakehunter.model.GameModelImpl;
import com.snakehunter.model.SaveLoadGame;
import com.snakehunter.model.exceptions.InvalidDetailException;
import com.snakehunter.view.GameView;
import com.snakehunter.view.GameViewImpl;
import com.snakehunter.view.LoginView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginController {

    private LoginView loginView;

    private final File file = new File("src/main/java/com/snakehunter/file/UsernamePassword.txt");
    private static final String DELIMITER = ":";

    private HashMap<String, String> usernamePassword = new HashMap<>();
    private String[] playersUsername = new String[2];
    private boolean loginSuccess = false;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;

        System.out.println(instruction());

        importUsernamePassword();

        loginView.getPasswordTxtF().addActionListener(e -> {
            try {
                validateLogin();
            } catch (InvalidDetailException ex) {
            }
        });

        loginView.getLoginButton().addActionListener(e -> {
            try {
                validateLogin();
            } catch (InvalidDetailException ex) {
            }
        });

        loginView.getNewAccountButton().addActionListener(e -> {
            try {
                createNewAccount();
            } catch (InvalidDetailException ex) {
            }
        });

        if (Main.isDebugMode()) {
            playersUsername[0] = "testing";
            playersUsername[1] = "debug";
            startGame(playersUsername);
        }
    }

    private String instruction() {
        return "Accounts for testing:\n"
                + "HUNTER | Hunter2\n"
                + "SERPENT | password\n"
                + "APPLE | banana\n"
                + "USERNAME | password\n";
    }

    private void importUsernamePassword() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st = null;
            while (true) {
                try {
                    if ((st = br.readLine()) == null) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] newUsernamePassword = st.split(DELIMITER);
                usernamePassword.put(newUsernamePassword[0], newUsernamePassword[1]);

            }
        } catch (FileNotFoundException e) {

            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void validateLogin() throws InvalidDetailException {
        String username = loginView.getUsernameTxtF().getText().toUpperCase();
        String hashedPassword = hashPassword(loginView.getPasswordTxtF().getPassword());

        for (Iterator<Map.Entry<String, String>> iterator = usernamePassword.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey().equals(username) && entry.getValue().equals(hashedPassword)) {
                setPlayerUsername(username);
                loginSuccess = true;
                loginView.getUsernameTxtF().requestFocus();
                break;
            } else if (!iterator.hasNext()) {
                throw new InvalidDetailException(loginView.getLoginMessages(), "Invalid username or password");
            }
        }

    }

    private void createNewAccount() throws InvalidDetailException {

        if (loginView.getUsernameTxtF().getText().length() != 0 && loginView.getPasswordTxtF().getPassword().length != 0) {

            String newUsername = loginView.getUsernameTxtF().getText().toUpperCase();

            // Write to file immediately if there are no record
            if (usernamePassword.size() == 0) {
                writeToFile();
                setPlayerUsername(newUsername);
                loginSuccess = true;
            }

            for (Iterator<Map.Entry<String, String>> iterator = usernamePassword.entrySet().iterator(); iterator
                    .hasNext(); ) {

                Map.Entry<String, String> entry = iterator.next();
                if (entry.getKey().equals(newUsername)) {
                    throw new InvalidDetailException(loginView.getLoginMessages(), "Username already exists");
                } else if (!iterator.hasNext()) {
                    writeToFile();
                    setPlayerUsername(newUsername);
                    loginSuccess = true;
                }
            }
        } else {
            throw new InvalidDetailException(loginView.getLoginMessages(), "Please fill in username or password");
        }
    }

    private void writeToFile() {
        String newUsername = loginView.getUsernameTxtF().getText().toUpperCase();
        String newHashedPassword = hashPassword(loginView.getPasswordTxtF().getPassword());

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(System.lineSeparator());
            fileWriter.write(newUsername + DELIMITER + newHashedPassword);
            fileWriter.close();

            usernamePassword.put(newUsername, newHashedPassword);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("New account added");
        //JFRAME.setVisible(false);
    }

    private void setPlayerUsername(String username) {
        if (playersUsername[0] == null) {
            playersUsername[0] = username;
            loginView.getHumanPlayerLabel().setText("Human Player: " + username);
        } else {
            playersUsername[1] = username;
            loginView.getSnakePlayerLabel().setText("Snake Player: " + username);
        }

        if (playersUsername[1] != null) {
            startGame(playersUsername);
        }

        loginView.getUsernameTxtF().setText("");
        loginView.getPasswordTxtF().setText("");
    }

    private String hashPassword(char[] password) {
        StringBuilder sb = new StringBuilder();
        String passwordToBeHash = Arrays.toString(password);

        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            //Add password bytes to digest
            md.update(passwordToBeHash.getBytes());

            //Get the hash's bytes
            byte[] bytes = md.digest();

            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Get complete hashed password in hex format
        return sb.toString();
    }

    private void startGame(String[] playersUsername) {
        loginView.getjFrame().setVisible(false);

        GameModel gameModel = new GameModelImpl();
        GameView gameView = new GameViewImpl(gameModel);
        SaveLoadGame saveLoadGame = new SaveLoadGame();
        GameController gameController = new GameController(gameView, gameModel, saveLoadGame);

        gameModel.setOnDataChangedListener(gameView);
        gameView.setOnViewEventListener(gameController);

        ((GameModelImpl) gameModel).getHumanPlayer().setName(playersUsername[0]);
        ((GameModelImpl) gameModel).getSnakePlayer().setName(playersUsername[1]);
    }

    // For JUnit test only
    public boolean getIsLoginSuccess() {
        return loginSuccess;
    }
}
