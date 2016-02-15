//package window_server_client;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

 public class Chat_window extends JFrame implements ActionListener{

    static int m_port = 12116;
    static String server_IP, server_connect_IP;
    static int server_first_port, server_connect_port;
    static String name;
    static int max_mes_length = 1024;
    static myIP m_IP;
    static public String message = "";
    JButton jbtn_send;
    static JTextPane jta_in;
    static JEditorPane jep;
    //JTextArea jta_in;
    JTextField jtf_out;
    JScrollPane jsp_in;
    
    Chat_window(JFrame parent, String title, String in_name, String in_ip, int in_port, int in_thisport){
        //super(parent, title, true);
        super();
        try {
            this.setBounds(parent.getBounds());
            
            System.out.println("-1-");
            jta_in = new JTextPane();
            //jta_in = new JTextArea();
            
            System.out.println("-2-");
            jta_in.setEditable(false);
            System.out.println("-3-");
            jsp_in = new JScrollPane(jta_in);
            jsp_in.setBounds(10,10,300,150);
            jsp_in.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            System.out.println("-4-");
            //jp1.add(jta_in);
            this.add(jsp_in);
            System.out.println("-5-");
            //this.add(jp1);
            
            jtf_out = new JTextField();
            jtf_out.setBounds(10,200,150,50);
            this.add(jtf_out);
            
            jbtn_send = new JButton("Send");
            jbtn_send.setBounds(170,210,90,40);
            this.add(jbtn_send);
            jbtn_send.addActionListener(this);
            
            m_port = in_thisport;
            
            
            URL url = new URL("ftp://0files-server:123456@files-server.ucoz.ru/serverIP.txt");
            Scanner scan = new Scanner(url.openStream());
            server_IP = scan.nextLine();
            server_first_port = scan.nextInt();
            scan.close();
            //server_connect_IP = server_IP;
            
            /*
            server_IP = in_ip;
            server_first_port = in_port;
                    */
            InetAddress addr = InetAddress.getByName(server_IP);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            name = in_name;
            //System.out.print("Enter work port: ");
            //m_port = Integer.parseInt(br.readLine());
            //System.out.print("Enter message: ");
            //message = br.readLine();
            System.out.println("OK");
            m_IP = new myIP(m_port);
            //m_IP.start_indications();
            
            System.out.println(server_IP);
            System.out.println(server_first_port);
            System.out.println("check--");
            System.out.println(m_IP.getIP_Port());
            
            Connection(addr, server_first_port);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Chat_window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Chat_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            this.setLayout(null);
            this.setVisible(true);
            this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            
            this.repaint();
            
            new SendListn();
    }
    
    static class SendListn extends Thread{
        
        SendListn(){
            super();
            this.start();
            System.out.println("CHEEEEECK");
        }
        
        public void run(){
            try {
                System.out.println("work");
                InetAddress ia1 = InetAddress.getByName(server_connect_IP);
                //DatagramSocket ds = new DatagramSocket(m_port);
                while (message!="-exit"){
                    //
                    DatagramSocket ds = new DatagramSocket(m_port);
                    //
                    byte[] data;
                    if (message!=null){
                    int lng = message.length();
                    data = new byte[lng + 12];
                    data[0] = 91;
                    data[1] = 38;
                    data[2] = 37;
                    data[3] = 93;
                    data[4] = 109;
                    data[5] = 101;
                    data[6] = 115;
                    data[7] = 115;
                    byte[] tbyte = myFunc.IntToByte(lng);
                    for (int i = 0; i < tbyte.length; i++){
                        data[8+i] = tbyte[i];
                    }
                    char[] tchar = message.toCharArray();
                    for (int i = 0; i < tchar.length; i++){
                        data[12+i] = (byte) tchar[i];
                    }
                    message = "";
                    System.out.println("IIIIII!II!I!II!I!");
                    System.out.println(tchar);
                    }else{
                        data = new byte[12];
                        data[0] = 91;
                        data[1] = 38;
                        data[2] = 37;
                        data[3] = 93;
                        data[4] = 109;
                        data[5] = 101;
                        data[6] = 115;
                        data[7] = 115;
                        data[8] = 0;
                        data[9] = 0;
                        data[10] = 0;
                        data[11] = 0;
                    }
                    System.out.println("Check1");
                    System.out.println("work2");
                    DatagramPacket pack = new DatagramPacket(data, data.length, ia1 , server_connect_port);
                    System.out.println(ia1.toString());
                    System.out.println(server_connect_port);
                    ds.send(pack);
                    System.out.println("Check2");
                
                    DatagramPacket in_pack = new DatagramPacket(new byte[1024], 1024);
                    ds.receive(in_pack);
                    System.out.println("work2");
                    //System.out.println(ds.getInetAddress());
                    System.out.println("Check3");
                
                    byte[] in_data = in_pack.getData();
                    char[] inch = new char[1024];
                    for (int i = 0; i<1024; i++){
                        inch[i] = (char) in_data[i];
                    }
                    
                    jta_in.setText(new String(inch).trim());
                    //jep.setText(new String(inch).trim());
                    //System.out.println(new String(inch).trim());
                    //message = null;
                    //
                    ds.close();
                    //
                    Thread.sleep(2000);
                }
            } catch (SocketException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    static void sendMessage(String s){
        int lng = s.length();
        byte[] data = new byte[lng + 12];
        data[0] = 91;
        data[1] = 38;
        data[2] = 37;
        data[3] = 93;
        data[4] = 109;
        data[5] = 101;
        data[6] = 115;
        data[7] = 115;
        byte[] tbyte = myFunc.IntToByte(lng);
        for (int i = 0; i < tbyte.length; i++){
            data[8+i] = tbyte[i];
        }
        char[] tchar = s.toCharArray();
        for (int i = 0; i < tchar.length; i++){
            data[12+i] = (byte) tchar[i];
        }
    }
    
    static void Connection(InetAddress taddr, int tport) throws SocketException, IOException{
        byte[] data = new byte[name.length() + 16];
        data[0] = 91;
        data[1] = 38;
        data[2] = 37;
        data[3] = 93;
        data[4] = 99;
        data[5] = 111;
        data[6] = 110;
        data[7] = 110;
        int[] tdata = m_IP.getIParray();
        data[8] = (byte) tdata[0];
        data[9] = (byte) tdata[1];
        data[10] = (byte) tdata[2];
        data[11] = (byte) tdata[3];
        byte[] tdata2 = myFunc.IntToByte(m_IP.getPort()+1);
        System.out.println(m_IP.getPort());
        data[12] = (byte) tdata2[0];
        data[13] = (byte) tdata2[1];
        data[14] = (byte) tdata2[2];
        data[15] = (byte) tdata2[3];
        char[] nm = name.toCharArray();
        for (int i = 0; i < nm.length; i++){
            data[i+16] = (byte) nm[i];
        }
        System.out.println("check1");
        DatagramPacket pack = new DatagramPacket(data, data.length, taddr, tport);
        DatagramSocket ds = new DatagramSocket(m_port);
        ds.send(pack);
        System.out.println("check2");
        DatagramPacket in_pack = new DatagramPacket(new byte[16], 16);
        System.out.println("check21");
        ds.receive(in_pack);
        System.out.println("check3");
        byte[] in_data = in_pack.getData();
        byte[] tin_data = new byte[4];
        tin_data[0] = in_data[12];
        tin_data[1] = in_data[13];
        tin_data[2] = in_data[14];
        tin_data[3] = in_data[15];
        
        //for (int i = 12; i < 16; i++){
        //    System.out.print(in_data[i]);
        //}
        //System.out.println();
        
        server_connect_port = myFunc.ByteToInt(tin_data);
        
        byte[] tin_datac = new byte[4];
        tin_datac[0] =  in_data[8];
        tin_datac[1] =  in_data[9];
        tin_datac[2] =  in_data[10];
        tin_datac[3] =  in_data[11];
        server_connect_port = myFunc.ByteToInt(tin_datac);
        server_connect_IP = server_IP;
        //server_connect_IP = new StringBuilder().append(tin_datac[0]).append(".").append(tin_datac[1]).append(".").append(tin_datac[2]).append(".").append(tin_datac[3]).toString();
        
        //System.out.println(server_connect_IP);
        //System.out.println(server_connect_port);
        
        ds.close();
    }
    
    public void actionPerformed(ActionEvent e) {
        message = jtf_out.getText();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
