//package nat_second;

import java.io.IOException;
import java.net.SocketException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class users extends Thread {
    
    private byte[] user_IP;
    private int user_port;
    private int listening_port;
    private String username;
    private String AllMess = ": ";
    private InetAddress ia3;
    myIP listening_IP;
    
    users(){}
    
    users(byte[] tip, int tport, String tusername, int tlport) throws SocketException, IOException{
        System.out.println("work1");
        user_IP = new byte[tip.length];
        for (int i = 0; i < tip.length; i++){
            user_IP[i] = tip[i];
        }
        user_port = tport;
        listening_port = tlport;
        username = tusername;
        listening_IP = new myIP(listening_port);
        ia3 = InetAddress.getByAddress(user_IP);
        this.start();
        System.out.println("work2");
        System.out.println(listening_IP.getIP_Port());
    }
    
    public void run(){
        try {
            //DatagramSocket ds = new DatagramSocket(listening_port);
            while(true){
                //
                DatagramSocket ds = new DatagramSocket(listening_port);
                //
                //System.out.println("Check1");
                DatagramPacket pack = new DatagramPacket(new byte[32], 32);
                //System.out.println(listening_IP.getIP_Port());
                
                //System.out.println("Check1");
                
                //System.out.println("Check2");
                
                ds.receive(pack);
                System.out.println("Check3");
                
                byte[] arr = pack.getData();
                char[] inch = new char[arr.length-12];
                for (int i = 0; i<inch.length; i++){
                    inch[i] = (char) arr[i+12];
                }
                String sty = new String(inch);
                sty = sty.trim();
                System.out.println(sty);
                server.AllMess = new StringBuilder().append(server.AllMess).append(sty).toString();
                //server.AllMess = new String(new StringBuilder(server.AllMess).append(sty));
                //server.AllMess.concat(sty);
                //if (sty!=null) server.AllMess.concat(sty);
                char[] cht = server.AllMess.toCharArray();
                byte[] bt = new byte[cht.length];
                for (int i = 0; i < cht.length; i++){
                    bt[i] = (byte) cht[i];
                }
                DatagramPacket sendp = new DatagramPacket(bt, bt.length, ia3, user_port);
                ds.send(sendp);
                //
                ds.close();
                Thread.sleep(200);
                //
            }
        } catch (SocketException ex) {
            Logger.getLogger(users.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(users.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
