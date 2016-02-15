//package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    
    static myIP m_IP;
    static int this_port;
    static String conIP;
    static int conPort;
    static String message;
    
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter IP to connect: ");
        conIP = br.readLine();
        System.out.println("Enter Port to connect: ");
        conPort = Integer.parseInt(br.readLine());
        InetAddress ia = InetAddress.getByName(conIP);
        boolean isExit = false;
        
        while(!isExit){
            message = sc.nextLine();
        }
        
        
        String mes;
        DatagramSocket ds = new DatagramSocket(this_port);
        while(!isExit){
            mes = br.readLine();
            if (mes=="-exit") isExit = true;
            char[] ch = mes.toCharArray();
            byte[] bto = new byte[ch.length];
            for (int i = 0; i<bto.length; i++){
                bto[i] = (byte) ch[i];
            }
            DatagramPacket dpo = new DatagramPacket(bto, bto.length, ia, conPort);
            ds.send(dpo);
            DatagramPacket dpi = new DatagramPacket(new byte[1024], 1024);
            ds.receive(dpi);
            byte[] bti = dpi.getData();
            char[] chi = new char[bti.length];
            for (int i = 0; i<chi.length;i++){
                chi[i] = (char) bti[i];
            }
            System.out.println(new String(chi).trim());
        }
        ds.close();
    }
    
    class Sender extends Thread{
        
        Sender(){
            this.start();
        }
        
        @Override
        public void run(){
            
        }
        
    }
}
