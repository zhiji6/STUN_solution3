//package window_server_client;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class client {
    
    static int m_port = 12116;
    static String server_IP, server_connect_IP;
    static int server_first_port, server_connect_port;
    static String name;
    static int max_mes_length = 1024;
    static myIP m_IP;
    static String message = "st_1";
    
    public static void main(String[] args) throws MalformedURLException, IOException{
        
        URL url = new URL("ftp://0files-server:123456@files-server.ucoz.ru/serverIP.txt");
	Scanner scan = new Scanner(url.openStream());
        server_IP = scan.nextLine();
        server_first_port = scan.nextInt();
	scan.close();
        InetAddress addr = InetAddress.getByName(server_IP);
        //String strin;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //strin = br.readLine();
        
        System.out.print("Enter your name: ");
        name = br.readLine();
        System.out.print("Enter work port: ");
        m_port = Integer.parseInt(br.readLine());
        System.out.print("Enter message: ");
        message = br.readLine();
        System.out.println("OK");
        m_IP = new myIP(m_port);
        //m_IP.start_indications();
        
        System.out.println(server_IP);
        System.out.println(server_first_port);
        System.out.println("check--");
        System.out.println(m_IP.getIP_Port());
        
        Connection(addr, server_first_port);
        
        new SendListn(); 
        
        //while(true){
            //message = br.readLine();
            //System.out.println("iswork");
            //if (message == "-exit") break;
            //message = br.readLine();
            //System.out.println("iswork2");
            //sendMessage(strin);
        //}
        
        
    }
    
    static class SendListn extends Thread{
        
        SendListn(){
            this.start();
        }
        
        public void run(){
            try {
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
                
                    DatagramPacket pack = new DatagramPacket(data, data.length, ia1 , server_connect_port);
                    System.out.println(ia1.toString());
                    System.out.println(server_connect_port);
                    ds.send(pack);
                    System.out.println("Check2");
                
                    DatagramPacket in_pack = new DatagramPacket(new byte[1024], 1024);
                    ds.receive(in_pack);
                    System.out.println(ds.getInetAddress());
                    System.out.println("Check3");
                
                    byte[] in_data = in_pack.getData();
                    char[] inch = new char[1024];
                    for (int i = 0; i<1024; i++){
                        inch[i] = (char) in_data[i];
                    }
                    System.out.println(new String(inch));
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
        
        for (int i = 12; i < 16; i++){
            System.out.print(in_data[i]);
        }
        System.out.println();
        
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
    
}
