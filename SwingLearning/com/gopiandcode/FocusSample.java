package com.gopiandcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Created by gopia on 26/11/2017.
 */
public class FocusSample {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Focus sample");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            ActionListener actionListener = new ActionFocusMover();
            MouseListener mouseListener = new MouseEnterFocusMover();

            frame.setLayout(new GridLayout(3,3));
            for(int i =1 ; i< 10; i++){
                JButton button = new JButton(Integer.toString(i));
                button.addActionListener(actionListener);
                button.addMouseListener(mouseListener);
                if((i%2) != 0) {
                    button.setFocusable(false);
                }
                frame.add(button);
            }

            frame.setSize(300,200);
            frame.setVisible(true);
        });

    }
}
