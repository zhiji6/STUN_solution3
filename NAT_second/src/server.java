//package nat_second;

import java.io.IOException;
import java.net.SocketException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

public class server {
    
    static String AllMess = "_ ";
    static int server_local_port;
    static myIP server_IP;
    int rndport = 24893;
    static int max_users;
    static users[] user_arr;
    static int last_user = 0;
    
    server() throws IOException{
        System.out.println("OK");
        upload_IP_on_server();
        System.out.println("OK");
        new server_connect_listener();
        System.out.println("OK");
    }
    
    public static void main(String[] args) throws SocketException, IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Max users: ");
        max_users = Integer.parseInt(br.readLine());
        user_arr = new users[max_users];
        server_local_port = 13372;
        server_IP = new myIP(server_local_port);
        //server_IP.start_indications();
        new server();
    }
    
    void upload_IP_on_server() throws MalformedURLException, IOException{
        URL ur = new URL("ftp://0files-server:123456@files-server.ucoz.ru/serverIP.txt");
        URLConnection upload = ur.openConnection();   
        PrintWriter writer = new PrintWriter(upload.getOutputStream());
        writer.println(server_IP.getIP());
        writer.println(server_IP.getPort());
        writer.close();
    }
    
    class server_connect_listener extends Thread{
        
        int connect_mes_length = 32;
        
        server_connect_listener(){
            this.start();
        }
        
        public void run(){
            try {
                DatagramSocket ds = new DatagramSocket(server_local_port);
                while(true){
                    DatagramPacket pack = new DatagramPacket(new byte[connect_mes_length], connect_mes_length);
                    System.out.println("check00");
                    ds.receive(pack);
                    System.out.println("check01");
                    byte[] arr = pack.getData();
                    byte[] tbyte = new byte[8];
                    for (int i = 0; i < tbyte.length; i++){
                        tbyte[i] = arr[i];
                    }
                    System.out.println("check02");
                    
                    if ((tbyte[0] == (byte) 91) && (tbyte[1] == 38) && (tbyte[2] == 37) && (tbyte[3] == (byte) 93)){
                        if ((tbyte[4] == (byte) 99) && (tbyte[5] == (byte) 111) && (tbyte[6] == (byte) 110) && (tbyte[7] == (byte) 110)){
                            byte[] tbyteip = new byte[4];
                            for (int i = 0; i < tbyteip.length; i++){
                                tbyteip[i] = arr[i+8];
                            }
                            byte[] tbyteport = new byte[4];
                            
                            for (int i = 0; i < tbyteport.length; i++){
                                tbyteport[i] = arr[i+12];
                            }
                            int tport = myFunc.ByteToInt(tbyteport)-1;
                            System.out.print("PORT::");
                            System.out.println(tport);
                            myIP tmyIP = new myIP(rndport);
                            InetAddress addr = InetAddress.getByAddress(tbyteip);
                            user_arr[last_user] = new users(tbyteip, tport, new String(new StringBuilder("user_").append(rndport)), rndport);
                            byte[] senddata = new byte[12];
                            senddata[0] = 91; //[
                            senddata[1] = 38; //&
                            senddata[2] = 37; //%
                            senddata[3] = 93; //]
                            senddata[4] = 97; //a
                            senddata[5] = 99; //c
                            senddata[6] = 99; //c
                            senddata[7] = 101; //e
                            byte[] trndport = myFunc.IntToByte(user_arr[last_user].listening_IP.getPort());
                            for (int i = 0; i < trndport.length; i++){
                                senddata[8+i] = trndport[i];
                            }
                            //int[] tbyteip2 = tmyIP.getIParr();
                            //for (int i = 0; i < tbyteip.length; i++){
                            //    senddata[i+8] = (byte) tbyteip2[i];
                            //}
                            //int sendport = tmyIP.getPort();
                            //byte[] sendportbyte = myFunc.IntToByte(sendport);
                            //for (int i = 0; i < tbyteip.length; i++){
                            //    senddata[i+8] = (byte) tbyteip2[i];
                            //    senddata[i+12] = sendportbyte[i];
                            //}
                            //System.out.println("check05");
                            //System.out.print("ADDR: ");
                            //System.out.println(addr.toString());
                            //System.out.print("TPORT: ");
                            //System.out.println(tport);
                            DatagramPacket tdp = new DatagramPacket(senddata, senddata.length, addr, tport);
                            //DatagramSocket();
                            //ds.close();
                            //DatagramSocket dsr = new DatagramSocket(server_local_port);
                            
                            ds.send(tdp);
                            
                            //dsr.close();
                            //ds = new DatagramSocket(server_local_port);
                
                            //new users(tbyteip, tport, new String(new StringBuilder("user_").append(rndport)), rndport);
                            rndport++;
                            last_user++;
                        }
                    }
                }
            } catch (SocketException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);                
            } catch (IOException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
