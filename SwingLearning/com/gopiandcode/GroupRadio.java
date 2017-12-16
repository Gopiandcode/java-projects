package com.gopiandcode;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GroupRadio {
    public static final String[] sliceOptions = {
            "4 slices", "8 slices", "12 slices", "16 slices"
    };
    private static final String[] crustOptions = {
            "Sicillian", "Thin Crust", "Thick Crust", "Stuffed Crust"
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Grouping example");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            Container sliceContainer = RadioButtonUtils.createRadioButtonGrouping(sliceOptions, "Slice Count");
            Container crustContainer = RadioButtonUtils.createRadioButtonGrouping(crustOptions, "Crust Type");

            frame.add(sliceContainer, BorderLayout.WEST);
            frame.add(crustContainer, BorderLayout.EAST);
            frame.setSize(300,200);
            frame.setVisible(true);

        });
    }


}
