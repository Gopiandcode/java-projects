package com.gopiandcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gopia on 26/11/2017.
 */
public class ActionTester {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Action Tester Sample");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            final Action printAction = new PrintHelloAction();

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("File");
            menuBar.add(menu);
            menu.add(new JMenuItem(printAction));
            menu.add(menuBar);
            JToolBar toolbar = new JToolBar();
            toolbar.add(new JButton(printAction));

            JButton enableButton = new JButton("Enable");
            ActionListener enableActionListener = (ActionEvent e) -> printAction.setEnabled(true);
            enableButton.addActionListener(enableActionListener);

            JButton disableButton = new JButton("Disable");
            disableButton.addActionListener((ActionEvent e) -> printAction.setEnabled(false));

            JButton relabelButton = new JButton("Relabel");
            relabelButton.addActionListener((ActionEvent e) -> printAction.putValue(Action.NAME, "Hello, World"));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(enableButton);
            buttonPanel.add(disableButton);
            buttonPanel.add(relabelButton);

            frame.setJMenuBar(menuBar);
            frame.add(toolbar, BorderLayout.SOUTH);
            frame.add(buttonPanel, BorderLayout.NORTH);
            frame.setSize(300, 200);
            frame.setVisible(true);
        });
    }
}
