package com.gopiandcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonSample {
    public static void main(String[] args) {
        Runnable runner = () -> {
            JFrame frame = new JFrame("Button Sample");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton button = new JButton("Select me");

            button.addActionListener((event) -> {
                System.out.println("I was selected!");
            });

            frame.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(SwingUtilities.isLeftMouseButton(e)) {
                        System.out.println("Mouse left clicked w " + e.paramString());
                    } else if (SwingUtilities.isMiddleMouseButton(e)){
                        System.out.println("Mouse middle clicked w " + e.paramString());
                    } else if(SwingUtilities.isRightMouseButton(e)) {
                        System.out.println("Mouse right clicked w " + e.paramString());
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("Mouse pressed");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    System.out.println("Mouse released");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    System.out.println("Mouse entered");
                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            frame.add(button, BorderLayout.SOUTH);
            frame.setSize(300, 100);
            frame.setVisible(true);

        };
        EventQueue.invokeLater(runner);
    }
}
