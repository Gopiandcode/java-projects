package com.gopiandcode;

import sun.swing.ImageIconUIResource;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by gopia on 26/11/2017.
 */
public class PrintHelloAction extends AbstractAction {
    private static final Icon printIcon = new ImageIcon("print.gif");
    PrintHelloAction() {
        super("Print", printIcon);
        putValue(Action.SHORT_DESCRIPTION, "Hello, World");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Hello world");
    }
}
