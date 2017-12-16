package com.gopiandcode;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RadioButtonUtils {
    private RadioButtonUtils(){

    }
    public static Container createRadioButtonGrouping(String[] elements) {
        return createRadioButtonGrouping(elements, null);
    }

    public static Container createRadioButtonGrouping(String[] elements, String title) {
        JPanel panel = new JPanel(new GridLayout(0,1));
        if(title != null) {
            Border border = BorderFactory.createTitledBorder(title);
            panel.setBorder(border);
        }

        ButtonGroup group = new ButtonGroup();
        JRadioButton aRadioButton;
        for (String element : elements) {
            aRadioButton = new JRadioButton(element);
            panel.add(aRadioButton);
            group.add(aRadioButton);
        }
        return panel;
    }
}
