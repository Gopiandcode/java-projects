package com.gopiandcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerSample {
   public static void main(String[] args) {
       EventQueue.invokeLater(() -> {
           ActionListener listener = e -> {
               System.out.println("Hello world timer!");
           };

           Timer timer = new Timer(500, listener);
           timer.start();
       });
   }
}
