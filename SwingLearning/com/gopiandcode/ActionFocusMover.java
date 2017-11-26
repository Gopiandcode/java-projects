package com.gopiandcode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gopia on 26/11/2017.
 */
public class ActionFocusMover implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }
}
