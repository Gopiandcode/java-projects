package com.gopiandcode;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEnterFocusMover extends MouseAdapter {
    @Override
    public void mouseEntered(MouseEvent e) {
        Component component = e.getComponent();
        if(!component.hasFocus()){
            component.requestFocusInWindow();
        }
    }
}
