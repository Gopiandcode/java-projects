package com.gopiandcode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class SelectingCheckBox {
    private static String DESELECTED_LABEL = "Deselected";
    private static String SELECTED_LABEL = "Selected";
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Selecting Checkbox");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JCheckBox checkBox = new JCheckBox(DESELECTED_LABEL);

            ActionListener actionListener = e -> {
                AbstractButton button = (AbstractButton) e.getSource();
                boolean selected = button.getModel().isSelected();
                String newLabel = (selected ? SELECTED_LABEL : DESELECTED_LABEL);
                button.setText(newLabel);
            };

            ChangeListener changeListener = e -> {
               AbstractButton abstractButton = (AbstractButton) e.getSource();
                ButtonModel model = abstractButton.getModel();
                boolean armed = model.isArmed();
                boolean pressed = model.isPressed();
                boolean selected = model.isSelected();
                System.out.println("Changed: " + armed + "/" + pressed + "/" + selected);
            };

            ItemListener itemListener = e -> {
               AbstractButton  abstractButton = (AbstractButton) e.getSource();
                Color foreground = abstractButton.getForeground();
                Color background = abstractButton.getBackground();

                int state = e.getStateChange();
                if(state == ItemEvent.SELECTED) {
                    abstractButton.setForeground(background);
                    abstractButton.setBackground(foreground);
                }
            };

            checkBox.addActionListener(actionListener);
            checkBox.addChangeListener(changeListener);
            checkBox.addItemListener(itemListener);

            checkBox.setMnemonic(KeyEvent.VK_S);
            frame.add(checkBox, BorderLayout.NORTH);
            frame.setSize(300, 100);
            frame.setVisible(true);
        });
    }
}
