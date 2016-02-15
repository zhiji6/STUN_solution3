//package window_server_client;

import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Chat_setting_window extends JDialog implements ActionListener {

    JFrame jf_par;
    JButton jbtn_back, jbtn_connect;
    JLabel jlbl_server, jlbl_port, jlbl_nickname, jlbl_thisport;
    JTextField jtf_server, jtf_port, jtf_nickname, jtf_thisport;
    
    Chat_setting_window(JFrame parent, String title){
        super(parent, title, true);
        this.setBounds(parent.getBounds());
        jf_par = parent;
        
        jbtn_back = new JButton("Back");
        jbtn_back.setBounds(70,250,100,40);
        jbtn_back.addActionListener(this);
        this.add(jbtn_back);
        
        jbtn_connect = new JButton("Connect");
        jbtn_connect.setBounds(300,250,100,40);
        jbtn_connect.addActionListener(this);
        this.add(jbtn_connect);
        
        jlbl_server = new JLabel("Server IP");
        jlbl_server.setBounds(70,50,100,20);
        this.add(jlbl_server);
        
        jtf_server = new JTextField();
        jtf_server.setBounds(250,45,150,30);
        this.add(jtf_server);
        
        jlbl_port = new JLabel("Server port");
        jlbl_port.setBounds(70,100,100,20);
        this.add(jlbl_port);
        
        jtf_port = new JTextField();
        jtf_port.setBounds(250,95,150,30);
        this.add(jtf_port);
        
        jlbl_nickname = new JLabel("Nickname");
        jlbl_nickname.setBounds(70,150,100,20);
        this.add(jlbl_nickname);
        
        jtf_nickname = new JTextField();
        jtf_nickname.setBounds(250,145,150,30);
        this.add(jtf_nickname);
        
        jlbl_thisport = new JLabel("Port this computer");
        jlbl_thisport.setBounds(70,200,130,20);
        this.add(jlbl_thisport);
        
        jtf_thisport = new JTextField();
        jtf_thisport.setBounds(250,190,150,30);
        this.add(jtf_thisport);
        
        this.setLayout(null);
        this.setVisible(true);
        this.repaint();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jbtn_back)){
            this.setVisible(false);
            this.dispose();
        } else if(e.getSource().equals(jbtn_connect)){
            try {
                ServerSocket ss = new ServerSocket(Integer.parseInt(jtf_thisport.getText()));
                ss.close();
                DatagramSocket ds1 = new DatagramSocket(Integer.parseInt(jtf_thisport.getText()));
                ds1.close();
                this.setVisible(false);
                this.dispose();
                new Chat_window(jf_par, "chat", jtf_nickname.getText(), jtf_server.getText(), Integer.parseInt(jtf_port.getText()), Integer.parseInt(jtf_thisport.getText()));
            } catch (SocketException ex) {
                System.out.println("Port is used");
            } catch (IOException ex) {
                Logger.getLogger(Chat_setting_window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        
    }
    
}
