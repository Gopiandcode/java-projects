package com.gopiandcode;

import javax.swing.*;
import java.awt.*;

public class VerifierSample {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Verififer Sample");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            JTextField textField1 = new JTextField();
            JTextField textField2 = new JTextField();
            JTextField textField3 = new JTextField();

            InputVerifier verifier = new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    boolean returnValue;
                    JTextField textField = (JTextField) input;
                    try {
                        Integer.parseInt(textField.getText());
                        returnValue = true;
                    } catch(NumberFormatException e) {
                        returnValue = false;
                    }


                    return returnValue;
                }
            };

            textField1.setInputVerifier(verifier);
            textField3.setInputVerifier(verifier);

            frame.add(textField1, BorderLayout.NORTH);
            frame.add(textField2, BorderLayout.CENTER);
            frame.add(textField3, BorderLayout.SOUTH);

            frame.setSize(300,100);
            frame.setVisible(true);
        });
    }
}
