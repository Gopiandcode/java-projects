package com.gopiandcode;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ActionButtonSample {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Default Button");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command = e.getActionCommand();
                    System.out.println("selected: " + command);
                }
            };

            frame.setLayout(new GridLayout(2,2, 10,10));

            JButton button1 = new JButton("Text button");
            button1.setMnemonic(KeyEvent.VK_B);
            button1.setActionCommand("First");
            button1.addActionListener(actionListener);
            frame.add(button1);

            Icon warnIco = new ImageIcon("Warn.gif");
            JButton button2 = new JButton(warnIco);
            button2.setActionCommand("Second");
            button2.addActionListener(actionListener);
            frame.add(button2);

            JButton button3 = new JButton("Warning", warnIco);
            button3.setActionCommand("Third");
            button3.addActionListener(actionListener);
            frame.add(button3);

            String htmlButton = "<html><sup>HTML</sup> <sub><em>Button</em></sub><br>" +
                    "<font color=\"#FF0080\"><u>Multi-Line</u></font>";
            JButton button4 = new JButton(htmlButton);
            button4.setActionCommand("Fourth");
            button4.addActionListener(actionListener);
            frame.add(button4);

            JRootPane rootPane = frame.getRootPane();
            rootPane.setDefaultButton(button2);
            frame.setSize(300,200);
            frame.setVisible(true);
        });
    }
}
