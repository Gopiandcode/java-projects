package com.gopiandcode.graphics;

import com.gopiandcode.network.Server;
import com.gopiandcode.network.ServerList;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by gopia on 11/06/2017.
 */
public class ServerFrame extends JFrame {

    private class ServerOrOption {
        private boolean isServer;
        private Server server;


        public ServerOrOption() {
            this.isServer = false;
        }

        public ServerOrOption(Server server) {
            this.server = server;
            this.isServer = true;
        }


        public boolean isServer() {
            return isServer;
        }

        public Server getServer() {
            return server;
        }

    }

    private class PanelRenderer implements ListCellRenderer<ServerOrOption> {


        @Override
        public Component getListCellRendererComponent(JList<? extends ServerOrOption> list, ServerOrOption value, int index, boolean isSelected, boolean cellHasFocus) {
            if(value.isServer()) {
                JButton server_button = new JButton(value.getServer().getServer_name());
                if(value.getServer().getServer_name().equals("")) {
                    server_button.setText("[No-Name]");
                }
                if(value.getServer().getServer_name().length() >= 10) {
                    server_button.setText(value.getServer().getServer_name().substring(0,10)+"...");
                }

                server_button.setBorderPainted(false);
                server_button.setFocusPainted(false);
                server_button.setContentAreaFilled(false);
                server_button.setMaximumSize(new Dimension(Title.getWidth(), server_button.getFontMetrics(server_button.getFont()).getHeight()));
                //server_button.setMinimumSize(new Dimension(Integer.MIN_VALUE, server_button.getFontMetrics(server_button.getFont()).getHeight()));
                return server_button;
            }
            else {
                JButton modify_button = new JButton("Add new server");
                modify_button.setMaximumSize(new Dimension(Title.getWidth(), modify_button.getFontMetrics(modify_button.getFont()).getHeight()));
                modify_button.setMaximumSize(new Dimension(Integer.MIN_VALUE, modify_button.getFontMetrics(modify_button.getFont()).getHeight()));
                return modify_button;
            }
        }
    }

    private static final int DEFAULT_HEIGHT =  1200;
    private static final int DEFAULT_WIDTH = 800;

    private JList<ServerOrOption> server_list;
    private JTextField server_name_textfield = new JTextField();
    private JTextField address_textfield = new JTextField();
    private JTextField channel_textfield = new JTextField();
    private JTextField port_textfield = new JTextField();
    private JCheckBox  ssl_enabled_checkbox = new JCheckBox();
    private JButton   delete_button = new JButton("Delete");
    private JLabel Title;

    private Server current_selection;


    public ServerFrame() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public ServerFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setup();
    }


    private void configureServerList() {

        Vector<ServerOrOption> items = getServerListForListView();


        server_list = new JList<ServerOrOption>(items);
        server_list.setCellRenderer(new PanelRenderer());
        server_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        server_list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if(!e.getValueIsAdjusting() && server_list.getSelectedValue() != null) {

                    if(!server_list.getSelectedValue().isServer()) {

                        Server s = new Server("Server","example.irc.net","#examplechannel", 0, false);

                        ServerList.get().add(s);

                        Vector<ServerOrOption> v = getServerListForListView();


                        server_list.setListData(v);

                    } else {
                        current_selection = server_list.getSelectedValue().getServer();
                        server_name_textfield.setText(current_selection.getServer_name());
                        address_textfield.setText(current_selection.getAddress());
                        channel_textfield.setText(current_selection.getChannel());
                        port_textfield.setText("" + current_selection.getPort());
                        ssl_enabled_checkbox.setSelected(current_selection.isSSL_enabled());
                    }

                }

            }
        });


    }

    private Vector<ServerOrOption> getServerListForListView() {
        Vector<ServerOrOption> items = new Vector<>();
        for(Server s : ServerList.get().getList()) {
            items.add(new ServerOrOption(s));
        }
        items.add(new ServerOrOption());
        return items;
    }

    private void updateListComponent() {
        Vector<ServerOrOption> v = getServerListForListView();
        server_list.setListData(v);
    }

    private void configureComponents() {
        server_name_textfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = server_name_textfield.getText();
                if(current_selection != null) {
                    current_selection.setServer_name(text);
                    updateListComponent();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = server_name_textfield.getText();
                if(current_selection != null) {
                    current_selection.setServer_name(text);
                    updateListComponent();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = server_name_textfield.getText();
                if(current_selection != null) {
                    current_selection.setServer_name(text);
                    updateListComponent();
                }
            }
        });

        address_textfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = address_textfield.getText();
                if(current_selection != null) {
                    current_selection.setAddress(text);
                    updateListComponent();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = address_textfield.getText();
                if(current_selection != null) {
                    current_selection.setAddress(text);
                    updateListComponent();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = address_textfield.getText();
                if(current_selection != null) {
                    current_selection.setAddress(text);
                    updateListComponent();
                }
            }
        });

        channel_textfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = channel_textfield.getText();
                if(current_selection != null) {
                    current_selection.setChannel(text);
                    updateListComponent();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = channel_textfield.getText();
                if(current_selection != null) {
                    current_selection.setChannel(text);
                    updateListComponent();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = channel_textfield.getText();
                if(current_selection != null) {
                    current_selection.setChannel(text);
                    updateListComponent();
                }
            }
        });


        PlainDocument doc = (PlainDocument) port_textfield.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.delete(offset, offset+length);

                if(test(sb.toString())) {

                    super.remove(fb, offset, length);
                } else {
                    notifyError();
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();

                sb.append(doc.getText(0, doc.getLength()));
                sb.insert(offset, string);

                if(test(sb.toString())) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    notifyError();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.replace(offset, offset+length, text);

                if(test(text)) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    notifyError();
                }


            }

            private boolean test(String text) {
                try {
                    Integer.parseInt(text);
                    return true;
                } catch(NumberFormatException e) {
                    return false;
                }
            }

            private void notifyError() {
                JOptionPane.showMessageDialog(ServerFrame.this,"Port values must be integers.", "Warning - Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        port_textfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                attemptParseValue();
            }

            private void attemptParseValue() {
                try {
                    int value = Integer.parseInt(port_textfield.getText());
                    if (current_selection != null) {
                        current_selection.setPort(value);
                        updateListComponent();
                    }
                } catch(NumberFormatException e) {

                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                attemptParseValue();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                attemptParseValue();
            }
        });

        ssl_enabled_checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
                boolean value = ssl_enabled_checkbox.isSelected();
                if(current_selection != null) {
                    current_selection.setSSL_enabled(value);
                    updateListComponent();
                }
            }
        });

        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(current_selection != null) {
                    int index = ServerList.get().getIndex(current_selection);
                    ServerList.get().remove(index);
                    current_selection = null;
                    updateListComponent();
                    server_name_textfield.setText("");
                    address_textfield.setText("");
                    channel_textfield.setText("");
                    port_textfield.setText("0");
                    ssl_enabled_checkbox.setSelected(false);
                }
            }
        });
    }

    private void setup() {
        configureServerList();
        configureComponents();

        /*JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();

        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.weighty = 10;

        JScrollPane pane = new JScrollPane(server_list);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(pane, constraint);

        constraint = new GridBagConstraints();
        constraint.gridx = 1;
        constraint.gridy = 1;
        constraint.weighty = 10;
        mainPanel.add(getInputPanel(), constraint);


        constraint = new GridBagConstraints();
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.weighty = 10;
        Title = new JLabel("Server List", SwingConstants.CENTER);
        mainPanel.add(Title, constraint);

        add(mainPanel);
        */

        JPanel mainPanel = new JPanel(new GridLayout(1,2));
        JScrollPane pane = new JScrollPane(server_list);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        Title = new JLabel("Server List", SwingConstants.CENTER);
        panel.add(Title);
        panel.add(pane);
        mainPanel.add(panel);

        mainPanel.add(getInputPanel());
        add(mainPanel);
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }



    private Component getInputPanel() {
        JPanel inputpanel = new JPanel();
        inputpanel.setLayout(new BorderLayout());

        JPanel listpanel = new JPanel(new GridLayout(5,2));




        listpanel.add(new JLabel("Server Name: ", SwingConstants.RIGHT));
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.LINE_AXIS));
        temp.add(server_name_textfield);
        temp.setPreferredSize(server_name_textfield.getPreferredSize());
        listpanel.add(temp);


        listpanel.add(new JLabel("Address: ", SwingConstants.RIGHT));
        temp = new JPanel(new GridLayout(1,1));
        temp.setLayout(new BoxLayout(temp, BoxLayout.LINE_AXIS));
        temp.add(address_textfield);
        temp.setPreferredSize(address_textfield.getPreferredSize());
        listpanel.add(temp);


        listpanel.add(new JLabel("Channel: ", SwingConstants.RIGHT));
        temp = new JPanel(new GridLayout(1,1));
        temp.setLayout(new BoxLayout(temp, BoxLayout.LINE_AXIS));
        temp.add(channel_textfield);
        temp.setPreferredSize(channel_textfield.getPreferredSize());
        listpanel.add(temp);


        listpanel.add(new JLabel("Port: ", SwingConstants.RIGHT));
        temp = new JPanel(new GridLayout(1,1));
        temp.setLayout(new BoxLayout(temp, BoxLayout.LINE_AXIS));
        temp.add(port_textfield);
        temp.setPreferredSize(port_textfield.getPreferredSize());
        listpanel.add(temp);


        listpanel.add(Box.createRigidArea(new Dimension(0,0)));

        JPanel checkbox_panel = new JPanel();
        checkbox_panel.setLayout(new BoxLayout(checkbox_panel, BoxLayout.LINE_AXIS));
        checkbox_panel.add(ssl_enabled_checkbox);
        checkbox_panel.add(new JLabel("SSL Enabled", SwingConstants.LEFT));
        checkbox_panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        listpanel.add(checkbox_panel);

        inputpanel.add(listpanel, BorderLayout.CENTER);
        inputpanel.add(delete_button, BorderLayout.SOUTH);
        inputpanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        server_name_textfield.setMaximumSize(new Dimension((int) inputpanel.getPreferredSize().getWidth(), (int) Title.getPreferredSize().getHeight()+10));
        address_textfield.setMaximumSize(new Dimension((int) inputpanel.getPreferredSize().getWidth(), (int) Title.getPreferredSize().getHeight()+10));
        channel_textfield.setMaximumSize(new Dimension((int) inputpanel.getPreferredSize().getWidth(), (int) Title.getPreferredSize().getHeight()+10));
        port_textfield.setMaximumSize(new Dimension((int) inputpanel.getPreferredSize().getWidth(), (int) Title.getPreferredSize().getHeight()+10));



        return inputpanel;
    }
}


