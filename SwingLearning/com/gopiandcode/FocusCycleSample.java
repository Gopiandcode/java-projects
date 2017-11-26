package com.gopiandcode;

import javax.swing.*;
import java.awt.*;

public class FocusCycleSample {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Focus Cycle example");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            frame.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.BOTH;


            constraints.gridy = 0;
            for(int i = 0; i < 3; i++){
                JButton button = new JButton("" + i);
                constraints.gridx = i;
                frame.add(button, constraints);
            }


            JPanel panel = new JPanel();
            panel.setFocusCycleRoot(true);
            panel.setFocusTraversalPolicyProvider(true);
            panel.setLayout(new GridLayout(1,3));

            for(int i = 0; i < 3; i++){
                JButton button = new JButton("" + (i+3));
                panel.add(button);
            }

            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 3;
            frame.add(panel, constraints);

            constraints.gridy = 2;
            constraints.gridwidth = 1;
            for(int i = 0; i < 3; i++){
                JButton button = new JButton("" + (i+6));
                constraints.gridx = i;
                frame.add(button, constraints);
            }


            frame.setSize(300, 200);
            frame.setVisible(true);
        });
    }

}
