package com.gopiandcode;



import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class ShareModel {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Sharing sample");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Container content = frame.getContentPane();
            JTextArea textArea1 = new JTextArea();
            Document document = textArea1.getDocument();
            JTextArea textArea2 = new JTextArea(document);
            JTextArea textArea3 = new JTextArea(document);
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            content.add(new JScrollPane(textArea1));
            content.add(new JScrollPane(textArea2));
            content.add(new JScrollPane(textArea3));
            frame.setSize(300, 400);
            frame.setVisible(true);
        });

    }

}
