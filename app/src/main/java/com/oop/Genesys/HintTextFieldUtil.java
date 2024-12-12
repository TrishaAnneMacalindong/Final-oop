package com.oop.Genesys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class HintTextFieldUtil {

    public static void addHint(JTextField textField, String hint) {
        textField.setForeground(Color.GRAY);
        textField.setText(hint);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Remove hint when the field gains focus
                if (textField.getText().equals(hint)) {
                    textField.setForeground(Color.BLACK);
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore hint if the field is empty when it loses focus
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(hint);
                }
            }
        });
    }
}