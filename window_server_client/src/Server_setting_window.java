//package window_server_client;

import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Server_setting_window extends JDialog implements ActionListener{
    
    JButton jbtn_back, jbtn_create;
    JLabel jlbl_name, jlbl_max, jlbl_port;
    JTextField jtf_name, jtf_max, jtf_port;
    
    Server_setting_window(JFrame parent, String title){
        super(parent, title, true);
        this.setBounds(parent.getBounds());
        
        //JPanel jp = new JPanel();
        
        jbtn_back = new JButton("Back");
        jbtn_back.setBounds(70,250,100,40);
        jbtn_back.addActionListener(this);
        this.add(jbtn_back);
        
        jbtn_create = new JButton("Create");
        jbtn_create.setBounds(300,250,100,40);
        jbtn_create.addActionListener(this);
        this.add(jbtn_create);
        
        jlbl_name = new JLabel("Server name");
        jlbl_name.setBounds(70,50,100,20);
        this.add(jlbl_name);
        
        jtf_name = new JTextField();
        jtf_name.setBounds(250,45,150,30);
        this.add(jtf_name);
        
        jlbl_port = new JLabel("Port");
        jlbl_port.setBounds(70,100,100,20);
        this.add(jlbl_port);
        
        jtf_port = new JTextField();
        jtf_port.setBounds(250,95,150,30);
        this.add(jtf_port);
        
        jlbl_max = new JLabel("Max users");
        jlbl_max.setBounds(70,150,100,20);
        this.add(jlbl_max);
        
        jtf_max = new JTextField();
        jtf_max.setBounds(250,145,150,30);
        this.add(jtf_max);
        
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jbtn_back)){
            this.setVisible(false);
            this.dispose();
            System.out.println("back");
        }else if(e.getSource().equals(jbtn_create)){
            try {
                ServerSocket ss = new ServerSocket(Integer.parseInt(jtf_port.getText()));
                ss.close();
                DatagramSocket ds1 = new DatagramSocket(Integer.parseInt(jtf_port.getText()));
                ds1.close();
                this.setVisible(false);
                this.dispose();
                new server(jtf_name.getText(), Integer.parseInt(jtf_port.getText()), Integer.parseInt(jtf_max.getText()));
                System.out.println("create");
            } catch (SocketException ex) {
                System.out.println("Port is used");
            } catch (IOException ex) {
                Logger.getLogger(Server_setting_window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.setVisible(false);
        this.dispose();
    }
    
}
