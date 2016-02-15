//package nat_chat;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class client extends JFrame {
    
    TextField tf = new TextField(30);
    Button bt = new Button("Send");
    java.util.Timer tmr = new java.util.Timer();
    Socket sock = null;
    PrintWriter pw = null;
    BufferedReader br = null;
    static String host = "127.0.0.1";
    static int port = 9588;
    String line = null;
    
    client(String s){
        super(s);
        setBackground(Color.WHITE);
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("check1");
               
        try{
            sock = new Socket(host,port);
            System.out.println("check1,1");
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            pw.write("нов подкл");
            pw.flush();
            sock.close();
        }catch(Exception e){
            System.err.println(e);
        }
        System.out.println("check2");
        tf.setBounds(100, 30, 100, 25);
        add(tf);
        bt.setBounds(100, 75, 100, 30);
        add(bt);
        bt.addActionListener(new BtnClick());
        
        setVisible(true);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        host = sc.next();
        port = sc.nextInt();
        new client("Клиент");        
    }
    
    class BtnClick implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            try{
                sock = new Socket(host,port);
                pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
                pw.write(tf.getText());
                pw.flush();
                br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                line = br.readLine().toString();
                System.out.println(line);
                sock.close();
            }catch(Exception e){
                System.err.println(e);
            }
        }
    }
    
}
