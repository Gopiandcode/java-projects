package com.gopiandcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class KeyStrokeSample {
    private static final String ACTION_KEY = "theAction";
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Keystroke example");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


            JButton buttonA = new JButton("<html><center>FOCUSED<br>control alt 7");
            JButton buttonB = new JButton("<html><center>FOCUS/RELEASE<br>VK_ENTER");
            JButton buttonC = new JButton("<html><center>ANCESTOR<br>VK_F4+SHIFT_MASK");
            JButton buttonD = new JButton("<html><center>WINDOW<br>' '");

            Action actionListener = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton source = (JButton) e.getSource();
                    System.out.println("Activated: " + source.getText());
                }
            };

            KeyStroke controlAlt7 = KeyStroke.getKeyStroke("control alt 7");
            InputMap inputMap = buttonA.getInputMap();
            inputMap.put(controlAlt7, ACTION_KEY);
            ActionMap actionMap = buttonA.getActionMap();
            actionMap.put(ACTION_KEY,actionListener);

            KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
            inputMap = buttonB.getInputMap();
            inputMap.put(enter, ACTION_KEY);
            buttonB.setActionMap(actionMap);

            KeyStroke shiftF4 = KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.SHIFT_MASK);
            inputMap = buttonC.getInputMap();
            inputMap.put(shiftF4, ACTION_KEY);
            buttonC.setActionMap(actionMap);


            KeyStroke space = KeyStroke.getKeyStroke(' ');
            inputMap = buttonD.getInputMap();
            inputMap.put(space, ACTION_KEY);
            buttonD.setActionMap(actionMap);


            frame.setLayout(new GridLayout(2,2));
            frame.add(buttonA);
            frame.add(buttonB);
            frame.add(buttonC);
            frame.add(buttonD);

            frame.setSize(400, 200);
            frame.setVisible(true);
        });
    }
}
