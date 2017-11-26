package com.gopiandcode;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ModifyModelSample {
    static String labels[] = {
            "Chardonnay", "Sauvignon",
            "Riesling", "Cabernet",
            "Zinfandel", "Merlot",
            "Pinot Noir", "Sauvignon Blanc",
            "Syrah", "Gewurztraminer"
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Modifying model example");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            final DefaultListModel<String> model = new DefaultListModel<String>();
            for(int i = 0, n= labels.length; i < n; i++) {
                    model.addElement(labels[i]);
            }


            JList<String> jlist = new JList<>(model);
            JScrollPane scrollpanel = new JScrollPane(jlist);

            frame.add(scrollpanel, BorderLayout.WEST);

            final JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane2 = new JScrollPane(textArea);
            frame.add(scrollPane2, BorderLayout.CENTER);

            ListDataListener listDataListener = new ListDataListener() {
                @Override
                public void intervalAdded(ListDataEvent e) {
                    appendEvent(e);
                }

                @Override
                public void intervalRemoved(ListDataEvent e) {
                    appendEvent(e);
                }

                @Override
                public void contentsChanged(ListDataEvent e) {
                    appendEvent(e);
                }

                private void appendEvent(ListDataEvent e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);

                    switch(e.getType()) {
                        case ListDataEvent.CONTENTS_CHANGED:
                            pw.print("Type: Contents Changed");
                            break;
                        case ListDataEvent.INTERVAL_ADDED:
                            pw.print("Type: Interval Added");
                            break;
                        case ListDataEvent.INTERVAL_REMOVED:
                            pw.print("Type: Interval Removed");
                            break;
                    }
                    pw.print(", Index0: "  + e.getIndex0());
                    pw.print(", Index1: "  + e.getIndex1());
                    DefaultListModel theModel = (DefaultListModel) e.getSource();
                    pw.println(theModel);
                    textArea.append(sw.toString());
                }

            };
            model.addListDataListener(listDataListener);


            JPanel jp = new JPanel(new GridLayout(2,1));
            JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
            JPanel jp2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
            jp.add(jp1);
            jp.add(jp2);

            JButton jb = new JButton("add F");
            jp1.add(jb);

            jb.addActionListener((ActionEvent e) -> model.add(0, "first"));

            jb = new JButton("AddElement L");
            jp1.add(jb);
            jb.addActionListener(e -> model.addElement("Last"));

            jb = new JButton("insertElementAt M");
            jp1.add(jb);
            jb.addActionListener(e -> {
                int size = model.getSize();
                model.insertElementAt("Middle", size/2);
            });


            jb = new JButton("set F");
            jp1.add(jb);
            jb.addActionListener(e -> {
                int size = model.getSize();
                if (size != 0) {
                    model.set(0, "New first");
                }
            });


            jb = new JButton("setElementAt L");
            jp1.add(jb);
            jb.addActionListener(e -> {
                int size = model.getSize();
                if(size != 0) {
                    model.setElementAt("New Last", size-1);
                }
            });


            jb = new JButton("load 10");
            jp1.add(jb);
            jb.addActionListener(e -> {
                for(int i = 0; i < labels.length; i++) {
                    model.addElement(labels[i]);
                }
            });


            jb = new JButton("clear");
            jp2.add(jb);
            jb.addActionListener(e -> model.clear());


            jb = new JButton("remove F");
            jp2.add(jb);
            jb.addActionListener(e -> {
                int size = model.getSize();
                if(size != 0) {
                    model.remove(0);
                }
            });


            jb = new JButton("removeAllElements");
            jp2.add(jb);
            jb.addActionListener(e -> model.removeAllElements());

            jb = new JButton("removeElement 'Last'");
            jp2.add(jb);
            jb.addActionListener(e -> model.removeElement("Last"));


            jb = new JButton("removeElementAt M");
            jp2.add(jb);
            jb.addActionListener(e -> {
                int size = model.getSize();
                if(size != 0) {
                    model.removeElementAt(size/2);
                }
            });


            jb = new JButton("removeRange FM");
            jp2.add(jb);
            jb.addActionListener(e -> {
                int size = model.getSize();
                if(size != 0){
                    model.removeRange(0, size/2);
                }
            });


            frame.add(jp, BorderLayout.SOUTH);
            frame.setSize(640, 300);
            frame.setVisible(true);
        });
    }
}
