package com.gopiandcode;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;

public class KeyTextComponent extends JComponent {
    private EventListenerList actionListenerList = new EventListenerList();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Key Text Component sample");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            KeyTextComponent keyTextComponent = new KeyTextComponent();
            final JTextField textField = new JTextField();
            ActionListener actionListener = (ActionEvent e) -> {
                String keyText = e.getActionCommand();
                textField.setText(keyText);
            };

            keyTextComponent.addActionListener(actionListener);
            frame.add(keyTextComponent, BorderLayout.CENTER);
            frame.add(textField, BorderLayout.SOUTH);
            frame.setSize(300,200);
            frame.setVisible(true);
        });
    }

    public KeyTextComponent() {
        setBackground(Color.CYAN);
        KeyListener internalKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (actionListenerList != null) {
                    int keycode = e.getKeyCode();
                    String keyText = KeyEvent.getKeyText(keycode);
                    ActionEvent actionEvent = new ActionEvent(
                            this,
                            ActionEvent.ACTION_PERFORMED,
                            keyText
                    );
                    fireActionPerformed(actionEvent);
                }
            }
        };

        MouseListener internalMouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
            }
        };

        addKeyListener(internalKeyListener);
        addMouseListener(internalMouseListener);
    }


    public void addActionListener(ActionListener listener) {
        actionListenerList.add(ActionListener.class, listener);
    }


    public void removeActionListener(ActionListener listener) {
        actionListenerList.remove(ActionListener.class, listener);
    }

    protected void fireActionPerformed(ActionEvent actionEvent) {
        EventListener listenerList[] = actionListenerList.getListeners(ActionListener.class);
        for(int i = 0; i < listenerList.length; i++) {
            ((ActionListener)listenerList[i]).actionPerformed(actionEvent);
        }
    }

    public boolean isFocusable(){
        return true;
    }
}
