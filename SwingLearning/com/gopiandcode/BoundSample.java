package com.gopiandcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import static javax.swing.SwingConstants.NORTH;
import static javax.swing.SwingConstants.SOUTH;

public class BoundSample {
    public static void main(final String[] args) {
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Button Sample");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                final JButton button1 = new JButton("Select one!");
                final JButton button2 = new JButton("No Select me!");

                final Random random = new Random();

                ActionListener listener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        int red = random.nextInt(255);
                        int green = random.nextInt(255);
                        int blue = random.nextInt(255);
                        button.setBackground(new Color(red, green, blue));
                    }
                };

                PropertyChangeListener propertyChangeListener = evt -> {
                    String property = evt.getPropertyName();
                    System.out.println(property);
                    if("background".equals(property)) {
                        button2.setBackground((Color) evt.getNewValue());
                    }
                };

                button1.addActionListener(listener);
                button1.addPropertyChangeListener(propertyChangeListener);
                button2.addActionListener(listener);

                frame.add(button1, BorderLayout.NORTH);
                frame.add(button2, BorderLayout.SOUTH);

                frame.setSize(300, 100);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runner);



    }
}
