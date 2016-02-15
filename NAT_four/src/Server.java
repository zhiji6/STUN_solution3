//package server;

//import func.myIP;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    static int this_port;
    static myIP my_ip = null;
    
    public static void main(String[] Args) throws SocketException, IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter PORT: ");
        this_port = sc.nextInt();
        my_ip = new myIP(this_port);
        DatagramSocket ds = new DatagramSocket(this_port);
        
        DatagramPacket dpi = new DatagramPacket(new byte[1024], 1024);
        ds.receive(dpi);
        byte[] bti = dpi.getData();
        char[] chi = new char[bti.length];
        for (int i = 0; i<chi.length;i++){
            chi[i] = (char) bti[i];
        }
        System.out.println(new String(chi).trim());
    }
    
}
