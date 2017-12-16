package com.gopiandcode;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ActionChangedListener implements PropertyChangeListener {
    private JButton button;

    public ActionChangedListener(JButton button) {
        this.button = button;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if(propertyName.equals(Action.NAME)) {
            String text = (String) evt.getNewValue();
            button.setText(text);
            button.repaint();
        } else if (propertyName.equals("enabled")) {
            Boolean enableState = (Boolean) evt.getNewValue();
            button.setEnabled(enableState.booleanValue());
            button.repaint();
        } else if(propertyName.equals(Action.SMALL_ICON)) {
            Icon icon = (Icon) evt.getNewValue();
            button.setIcon(icon);
            button.invalidate();
            button.repaint();
        }

    }
}
