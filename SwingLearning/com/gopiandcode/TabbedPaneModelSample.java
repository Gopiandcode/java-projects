package com.gopiandcode;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;

public class TabbedPaneModelSample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tabbed Pane shared model sample");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();


        DefaultListModel<String> model = new DefaultListModel<>();
        JList list = new JList(model);
        list.getModel().addListDataListener(new TabbedPaneListDataConnector<String>(tabbedPane, (String text) -> {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder(text));
            panel.add(new JLabel(text), BorderLayout.CENTER);
            return panel;
        }));
        final int[] index = {0};

        frame.setLayout(new GridLayout(2,2));
        frame.add(list);
        frame.add(tabbedPane);

        JButton button = new JButton("Add");
        button.addActionListener(e -> model.addElement("Kiran" + index[0]++));
        frame.add(button);

        button = new JButton("Remove");
        button.addActionListener(e -> {
            int size = model.getSize();
            if(size > 0) {
                model.remove(0);
            }
        });
        frame.add(button);


        frame.setSize(640, 360);
        frame.setVisible(true);
    }


    private static class TabbedPaneListDataConnector<T> implements ListDataListener {
        private JTabbedPane tabbedPane;
        private TabbedPaneRenderer<T> renderer;
        private java.util.List<Map.Entry<T, Container>> elements = new ArrayList<>();

        public TabbedPaneListDataConnector(JTabbedPane tabbedPane, TabbedPaneRenderer<T> renderer) {
            this.tabbedPane = tabbedPane;
            this.renderer = renderer;
        }
        @Override
        public void intervalAdded(ListDataEvent e) {
            System.out.println("Interval Added: " + e);
                ListModel list = (ListModel) e.getSource();

                for(int i = e.getIndex1(); i >= e.getIndex1(); i--){
                    System.out.println("i: "+i);
                    T item = (T) list.getElementAt(i);
                    Container container = renderer.renderModel(item);
                    elements.add(i, new HashMap.SimpleEntry<>(item, container));
                    tabbedPane.add(container);
               }
        }

        @Override
        public void intervalRemoved(ListDataEvent e) {
            System.out.println("Interval Removed: " + e);
             ListModel list = (ListModel) e.getSource();
           for(int i = e.getIndex1(); i >= e.getIndex0(); i--){
                    tabbedPane.remove(i);
                    elements.remove(i);
               }
        }

        @Override
        public void contentsChanged(ListDataEvent e) {
            System.out.println("contents Changed: " + e);
                 ListModel list = (ListModel) e.getSource();
                for(int i = e.getIndex1(); i >= e.getIndex0(); i--){
                    T item = (T) list.getElementAt(i);
                    if(!item.equals(elements.get(i).getKey())) {
                        Container container = renderer.renderModel(item);
                        elements.set(i, new HashMap.SimpleEntry<>(item, container));
                        tabbedPane.remove(i);
                        tabbedPane.add(container,i);
                    }
              }

       }
    }
}



