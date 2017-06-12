package com.gopiandcode.main;

import com.gopiandcode.graphics.MainFrame;
import com.gopiandcode.graphics.ServerFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gopia on 11/06/2017.
 */
public class Main {

    private static void configureStyle() {
        UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
        for(UIManager.LookAndFeelInfo info : installedLookAndFeels) {
            System.out.println(info.getName());
            if(info.getName().toLowerCase().contains("windows")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for(UIManager.LookAndFeelInfo info : installedLookAndFeels) {
            System.out.println(info.getName());
            if(info.getName().toLowerCase().contains("metal")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static void main(String[] args) {
        configureStyle();
        EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
