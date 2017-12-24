package com.gopiandcode.graphics;

import com.gopiandcode.file.ConfigurationLoader;
import com.gopiandcode.network.NetworkConnector;
import com.gopiandcode.network.Server;
import com.gopiandcode.network.ServerList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by gopia on 11/06/2017.
 */
public class MainFrame extends JFrame {
    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 800;

    private NetworkConnector netConnector = new NetworkConnector();

    private JTextField username_textfield = new JTextField(10);
    private JPasswordField password_textfield = new JPasswordField(10);
    private JTextArea  communication_textarea = new JTextArea(15,25);
    private JTextField input_textfield = new JTextField(25);

    private JComboBox<Server> server_list = new JComboBox<>();
    private JButton connect_button = new JButton("Connect");
    private JButton send_button = new JButton("Send");
    private ServerFrame serverframe;

    private Server current_server;

    public void configureComponents() {
        server_list.setRenderer(new ListCellRenderer<Server>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Server> list, Server value, int index, boolean isSelected, boolean cellHasFocus) {
                BasicComboBoxRenderer renderer = new BasicComboBoxRenderer();
                if(value != null)
                    renderer.setText(value.getServer_name());
                else
                    renderer.setText("");
                if(isSelected) renderer.setBackground(new Color(177, 194, 248));
                else renderer.setBackground(new Color(246, 255, 255));

                return renderer;
            }
        });
        updateItems();

        ServerList.get().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                updateItems();
            }
        });

        server_list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                current_server = (Server) server_list.getSelectedItem();
            }
        });

        send_button.setEnabled(false);
        connect_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(netConnector.isConnected()) {
                    // Disconnect
                    new Thread(() ->
                    netConnector.disconnect()).start();
                    communication_textarea.setText("");
                } else {
                    new Thread(()->{
                    Server s = (Server) server_list.getSelectedItem();
                    if(s != null) {
                        try {
                            netConnector.initialize(s);
                            if(!netConnector.checkUsername()) {
                                JOptionPane.showMessageDialog(MainFrame.this, "Please enter a username before connecting", "Invalid Input - Username Required", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                netConnector.connect();
                            }
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(MainFrame.this, "Could not initialize the network sockets.", "Fatal Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }}).start();
                }

            }
        });

        netConnector.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if(netConnector.isConnected()) {
                    connect_button.setText("Disconnect");
                    username_textfield.setEnabled(false);
                    password_textfield.setEnabled(false);
                    send_button.setEnabled(true);
                }
                else {
                    connect_button.setText("Connect");
                    username_textfield.setEnabled(true);
                    password_textfield.setEnabled(true);
                    connect_button.setEnabled(true);
                }

            }
        });
        netConnector.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if(netConnector.isConnected()) {
                    String input = netConnector.getInput();
                    display(input);
                }
            }
        });

        send_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(netConnector.isConnected()) {
                    String s = input_textfield.getText();
                    new Thread(() -> {
                    netConnector.sendString(s);
                        display(s);
                    }).start() ;
                    input_textfield.setText("");
                }

            }
        });

        username_textfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = username_textfield.getText();
                netConnector.setUsername(text);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = username_textfield.getText();
                netConnector.setUsername(text);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = username_textfield.getText();
                netConnector.setUsername(text);
            }
        });

        password_textfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = password_textfield.getText();
                netConnector.setPassword(text);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = password_textfield.getText();
                netConnector.setPassword(text);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = password_textfield.getText();
                netConnector.setPassword(text);
            }
        });
    }

    public void updateItems() {
        server_list.removeAllItems();
        for(Server s : ServerList.get().getList()) {
        server_list.addItem(s);
        }
    }

    public MainFrame() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public MainFrame(int width, int height) {
        setSize(width, height);
        setup();
    }

    private Component getUserFieldView() {
        JPanel input_panel = new JPanel(new GridLayout(2,2));

        JLabel username_label = new JLabel("Username: ");
        username_label.setHorizontalAlignment(JLabel.RIGHT);

        JLabel password_label = new JLabel("Password: ");
        password_label.setHorizontalAlignment(JLabel.RIGHT);

        input_panel.add(username_label);
        input_panel.add(username_textfield);
        input_panel.add(password_label);
        input_panel.add(password_textfield);
        return input_panel;
    }


    private Component getServerInputView() {

        JPanel server_select_panel = new JPanel();

        server_select_panel.setLayout(new BoxLayout(server_select_panel, BoxLayout.PAGE_AXIS));
        server_select_panel.add(server_list);
        server_select_panel.add(connect_button);
        server_select_panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        server_select_panel.setMaximumSize(server_select_panel.getPreferredSize());
        return server_select_panel;
    }

    private Component getConfigurationView() {
        JPanel configuration_panel = new JPanel();
        BoxLayout layout = new BoxLayout(configuration_panel, BoxLayout.LINE_AXIS);
        configuration_panel.setLayout(layout);
        configuration_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Component user_input_view = getUserFieldView();
        Component server_selection_view = getServerInputView();
        configuration_panel.add(user_input_view);
        configuration_panel.add(server_selection_view);
        configuration_panel.setMaximumSize(configuration_panel.getPreferredSize());
        return configuration_panel;
    }

    private Component getInputView() {
        input_textfield.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int)send_button.getPreferredSize().getHeight()));
        JPanel input_panel = new JPanel();
        input_panel.setLayout(new BoxLayout(input_panel, BoxLayout.LINE_AXIS));
        input_panel.add(input_textfield);
        input_panel.add(send_button);
        return input_panel;
    }

    private void setup() {
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        //panel.add(Box.createVerticalGlue());
        panel.add(getConfigurationView());
        //panel.add(Box.createVerticalGlue());
        panel.add(new JScrollPane(communication_textarea));
        //panel.add(Box.createVerticalGlue());
        panel.add(getInputView());
        //panel.add(Box.createVerticalGlue());
        add(panel);
        pack();

        configureComponents();

        setupMenu();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public void display(String text) {
        EventQueue.invokeLater(() ->{
            communication_textarea.append(text);
            communication_textarea.append("\n");
        });
    }
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem open_config = new JMenuItem("Open Configuration", 'o');
        JMenuItem save_config = new JMenuItem("Save Configuration", 's');
        open_config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigurationLoader.loadConfiguration(MainFrame.this, MainFrame.this);

            }
        });
        save_config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigurationLoader.saveConfiguration(MainFrame.this);
            }
        });
        fileMenu.add(open_config);
        fileMenu.add(save_config);
        fileMenu.setMnemonic('f');
        menuBar.add(fileMenu);

        JMenu serverMenu = new JMenu("Servers");
        JMenuItem edit_servers = new JMenuItem("Edit Servers", 'e');

        edit_servers.addActionListener((ActionEvent e) ->
                EventQueue.invokeLater(() -> {
                    if(serverframe != null)
                        serverframe.setVisible(true);
                    else {
                        serverframe = new ServerFrame();
                        serverframe.setVisible(true);
                    }
                }));
        serverMenu.add(edit_servers);
        serverMenu.setMnemonic('s');
        menuBar.add(serverMenu);

        JMenu about_option = new JMenu("About");
        about_option.setMnemonic('a');
        about_option.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                //EventQueue.invokeLater(() ->
                System.out.println("Pressed a button");
                JOptionPane.showMessageDialog(MainFrame.this, "Simple IRC Client Made By Gopiandcode.", "About - Simple IRC Client", JOptionPane.INFORMATION_MESSAGE);}
            @Override public void menuDeselected(MenuEvent e) {}
            @Override public void menuCanceled(MenuEvent e) {}
        });

        menuBar.add(about_option);

        setJMenuBar(menuBar);
    }

}
