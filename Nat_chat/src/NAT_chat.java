//package nat_chat;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import sun.security.x509.IPAddressName;

public class NAT_chat {
        
    static myIP ip;
    HttpConnect httpConn = null;
    static int port1 = 9590;
        
    public static void main(String[] args) {
        try{
            ip = new myIP(port1);
            System.out.println(ip.getIP());
            System.out.println(ip.getPort());
            ServerSocket ss = new ServerSocket(port1);
            while(true)
                new HttpConnect(ss.accept());
        }catch(ArrayIndexOutOfBoundsException ae){
            System.err.println("usage port");
            System.exit(0);
        }catch(IOException e){
            System.out.println();
        }
    }
}

class HttpConnect extends Thread{
    
    private Socket sock;
        
    HttpConnect(Socket s){
        sock = s;
        setPriority(NORM_PRIORITY-1);
        start();
    }
    
    public void run(){
        try{
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()),true);
            pw.println("load");
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String req = br.readLine();
            System.out.println("Req: " + req);
            sock.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
}

    

    
    

