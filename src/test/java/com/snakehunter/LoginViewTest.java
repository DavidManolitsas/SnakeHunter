package com.snakehunter;

import com.snakehunter.controller.LoginController;
import com.snakehunter.model.exceptions.InvalidDetailException;
import com.snakehunter.view.LoginView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginViewTest {

    private LoginController loginController;
    private LoginView loginView;

    @Before
    public void setUp() {

        loginView = new LoginView();

        loginController = new LoginController(loginView);
    }

    // Start of Login Tests
    // Positive Test 1
    @Test
    public void successLoginTest() {

        loginView.setUsernameTxtF("human");
        loginView.setPasswordTxtF("password");

        try {
            loginController.validateLogin();
        } catch (InvalidDetailException e) {
            e.printStackTrace();
        }

        assertTrue(loginController.getIsLoginSuccess());
    }

    // Positive Test 2
    @Test
    public void successLoginTest2() {

        loginView.setUsernameTxtF("HuMaN");
        loginView.setPasswordTxtF("password");

        try {
            loginController.validateLogin();
        } catch (InvalidDetailException e) {
            e.printStackTrace();
        }

        assertTrue(loginController.getIsLoginSuccess());
    }

    // Negative Test 1
    @Test(expected = InvalidDetailException.class)
    public void wrongCaseLoginTest() throws InvalidDetailException {

        loginView.setUsernameTxtF("human");
        loginView.setPasswordTxtF("Password");
        loginController.validateLogin();
    }

    // Negative Test 2
    @Test(expected = InvalidDetailException.class)
    public void emptyLoginTest() throws InvalidDetailException {

        assertEquals("", loginView.getUsernameTxtF().getText());
        assertArrayEquals(new char[0], loginView.getPasswordTxtF().getPassword());

        loginController.validateLogin();
    }
    // End of Login Tests

//    // Start of New Account Tests
//    private void assertEmptyNewAccount() {
//        assertEquals(EMPTY_USERNAME, loginView.getNewUsernameTxtF().getText());
//        assertArrayEquals(EMPTY_PASSWORD, loginView.getNewPasswordTxtF().getPassword());
//    }
//
//    // Positive Test 1 & 2
//    @Test
//    public void createNewAccountAndLoginTest() {
//
//        assertEmptyNewAccount();
//
//        String newAccountName = getRandomString();
//        loginView.setNewUsernameTxtF(newAccountName);
//
//        String newAccountPW = getRandomString();
//        loginView.setNewPasswordTxtF(newAccountPW);
//
//        try {
//            loginView.createNewAccount();
//        } catch (InvalidDetailException e) {
//            e.printStackTrace();
//        }
//
//        assertTrue(loginView.isLoginSuccess());
//
//        loginView.setUsernameTxtF(newAccountName);
//        loginView.setPasswordTxtF(newAccountPW);
//        try {
//            loginView.validateLogin();
//        } catch (InvalidDetailException e) {
//            e.printStackTrace();
//        }
//
//        assertTrue(loginView.isLoginSuccess());
//    }
//
//    // For generating a random string length of 6 for both username and password
//    private String getRandomString() {
//
//        StringBuilder stringBuilder = new StringBuilder();
//
//        Random random = new Random();
//
//        for (int i = 0; i < 6; i++) {
//
//            int j = random.nextInt(36);
//
//            char c = 'a';
//            int k;
//
//            if (j < 26) {
//                c = (char) (j + c);
//                stringBuilder.append(c);
//            } else {
//                k = (j - 26);
//                stringBuilder.append(k);
//            }
//
//        }
//        return stringBuilder.toString();
//    }
//
//    // Negative Test 1
//    @Test
//    public void emptyNewAccountTest() {
//
//        assertEmptyNewAccount();
//
//        try {
//            try {
//                loginView.createNewAccount();
//            } catch (InvalidDetailException e) {
//                e.printStackTrace();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        assertFalse(loginView.isLoginSuccess());
//    }
//
//    // Negative Test 2
//    @Test
//    public void createExistingAccountTest() {
//
//        assertEmptyNewAccount();
//
//        loginView.setNewUsernameTxtF("human");
//        loginView.setNewPasswordTxtF("password");
//
//        try {
//            loginView.createNewAccount();
//        } catch (InvalidDetailException ex) {
//            ex.printStackTrace();
//        }
//
//        assertFalse(loginView.isLoginSuccess());
//    }
//    // End of New Account Tests
}