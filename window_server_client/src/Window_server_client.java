//package window_server_client;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window_server_client extends JFrame implements ActionListener{
    
    JMenu menu_option, menu_server, menu_client;
    JMenuItem menuitem_option_exit, menuitem_server_new, menuitem_client_new;
    Button btn_CreateServer;
    JButton jbtn_CreateServer;
    Container cont_first, cont_chat;
    
    Window_server_client(String a){
        super(a);
        this.setBounds(250, 150, 500, 350);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btn_CreateServer = new Button();
        jbtn_CreateServer = new JButton();
        jbtn_CreateServer.setBounds(50, 75, 150, 35);
        jbtn_CreateServer.setText("Create server");
        jbtn_CreateServer.setBackground(Color.WHITE);
        
        btn_CreateServer.setBounds(50, 160, 150, 35);
        btn_CreateServer.setLabel("Create server");
        btn_CreateServer.setBackground(Color.WHITE);
        
        JMenuBar mb = new JMenuBar();
        this.setJMenuBar(mb);
        menu_server = new JMenu("Server");
        menu_client = new JMenu("Client");
        menu_option = new JMenu("Option");
        menuitem_server_new = new JMenuItem("Create Server");
        menuitem_option_exit = new JMenuItem("Exit");
        menuitem_client_new = new JMenuItem("Start client");
        mb.add(menu_option);
        menu_option.add(menuitem_option_exit);
        mb.add(menu_server);
        menu_server.add(menuitem_server_new);
        mb.add(menu_client);
        menu_client.add(menuitem_client_new);
        menuitem_client_new.addActionListener(this);
        menuitem_option_exit.addActionListener(this);
        menuitem_server_new.addActionListener(this);
        
        btn_CreateServer.addActionListener(this);
        this.add(jbtn_CreateServer);
        this.add(btn_CreateServer);
        
        this.setLayout(null);
    }
    
    public static void main(String[] args) {
        new Window_server_client(" ");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(menuitem_option_exit)){
            System.exit(0);
        }else if (e.getSource().equals(menuitem_server_new)){
            new Server_setting_window(this, "nm");
        }else if (e.getSource().equals(btn_CreateServer)){
            new Server_setting_window(this, "nm");
        }else if (e.getSource().equals(menuitem_client_new)){
            new Chat_setting_window(this, "nm");
        }
    }
    
}
