package com.snakehunter.model.exceptions;

import javax.swing.JLabel;

public class InvalidDetailException
        extends Throwable {
    public InvalidDetailException(JLabel label, String message) {
        label.setText(message);
    }
}
