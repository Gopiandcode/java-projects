package com.gopiandcode;

import javax.lang.model.element.ElementVisitor;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;

public class AncestorSampler {
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Ancestor Sampler");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            AncestorListener listener = new AncestorListener() {
                @Override
                public void ancestorAdded(AncestorEvent event) {
                    System.out.println("Added");
                }

                @Override
                public void ancestorRemoved(AncestorEvent event) {
                    System.out.println("Moved");
                }

                @Override
                public void ancestorMoved(AncestorEvent event) {
                    System.out.println("Removed");
                }
            };

            frame.getRootPane().addAncestorListener(listener);
            frame.setSize(300,200);
            frame.setVisible(true);
            frame.getRootPane().setVisible(false);
            frame.getRootPane().setVisible(true);
        });
    }
}
