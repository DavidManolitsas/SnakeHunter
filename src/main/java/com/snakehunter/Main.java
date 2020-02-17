package com.snakehunter;

import com.snakehunter.controller.LoginController;
import com.snakehunter.view.LoginView;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class Main {

    private static boolean isDebugMode = false;

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        new LoginController(loginView);
    }

    public static boolean isDebugMode() {
        return isDebugMode;
    }
}
