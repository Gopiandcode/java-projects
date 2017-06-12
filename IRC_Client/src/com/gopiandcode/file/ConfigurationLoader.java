package com.gopiandcode.file;

import com.gopiandcode.graphics.MainFrame;
import com.gopiandcode.network.Server;
import com.gopiandcode.network.ServerList;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by gopia on 11/06/2017.
 */
public class ConfigurationLoader {

    public static void saveConfiguration(Component component) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("IRC Configuration File", "gopi"));
        EventQueue.invokeLater(() -> {

            int result = fileChooser.showSaveDialog(component);
            File f = fileChooser.getSelectedFile();

            if(result == JFileChooser.APPROVE_OPTION) {
                if(f.getName().endsWith(".gopi")) {
                    saveServerListToFile(f, component);
                } else {
                    System.out.println(f.getParentFile().getAbsolutePath());
                    f = new File(f.getParentFile().getAbsolutePath(),f.getName() + ".gopi");
                    System.out.println(f.getAbsolutePath());
                    saveServerListToFile(f, component);
                }
            }
        });
    }

    public static void loadConfiguration(Component component, MainFrame context) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("IRC Configuration File", "gopi"));
        EventQueue.invokeLater(() -> {

                    int result = fileChooser.showDialog(component, "Open");

                    if (result == JFileChooser.APPROVE_OPTION) {
                        File f = fileChooser.getSelectedFile();
                        loadServerListFromFile(f, component);
                        context.updateItems();
                    }
        });
    }

    public static void loadServerListFromFile(File file, Component component) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
            List<Server> list = (List<Server>) input.readObject();
            ServerList.SetServers(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(component, "Not a valid Configuration File", "Error - Invalid File", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void saveServerListToFile(File file, Component component) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            java.util.List<Server> list = ServerList.get().getList();
            output.writeObject(list);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(component, "Could not save file", "Error - File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
