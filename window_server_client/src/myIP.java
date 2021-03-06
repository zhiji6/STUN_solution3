//package window_server_client;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class myIP extends Thread{
    
    private String STUN_host = "stun.t-online.de";
    private int[] IPv4;
    private int getport = 3478;
    private int port;
    private int local_port;
    Indications ind = new Indications();
    InetAddress ia;
    private boolean isIndications = false;
    private byte[] data = new byte[20];
            
    myIP(int temp_local_port) throws UnknownHostException, SocketException, IOException{
        try{
            local_port = temp_local_port;
            ia = InetAddress.getByName(STUN_host);
            
            short length_message = 0;
            short temp_short;
            int temp_int;
            byte[] temp_byte = null;
            
            //STUN Massage Type
            temp_short = 0x0001;
            temp_byte = myFunc.ShortToByte(temp_short);
            data[0]=temp_byte[0];
            data[1]=temp_byte[1];
            
            //Massage Length
            temp_short = length_message;
            temp_byte = myFunc.ShortToByte(temp_short);
            data[2]=temp_byte[0];
            data[3]=temp_byte[1];
            
            //Magic Cookie
            temp_int = 0x2112A442;
            temp_byte = myFunc.IntToByte(temp_int);
            data[4] = temp_byte[0];
            data[5] = temp_byte[1];
            data[6] = temp_byte[2];
            data[7] = temp_byte[3];
            
            //Translation ID
            for (int i = 0; i < 3; i++){
                temp_int = 12314+i;
                temp_byte = myFunc.IntToByte(temp_int);
                data[8+i*4] = temp_byte[0];
                data[9+i*4] = temp_byte[1];
                data[10+i*4] = temp_byte[2];
                data[11+i*4] = temp_byte[3];
            }
               
            DatagramSocket ds = new DatagramSocket(local_port);
            DatagramPacket pack = new DatagramPacket(data, data.length, ia, getport);
            //for (int i = 0; i<data.length; i++){
            //    System.out.println(data[i]);
            //}
            ds.send(pack);
            int btlength = 64;
            short Res_type_message = 0, Res_length_message = 0, Res_atribute_type = 0, Res_atribute_length = 0;
            int Res_magic_cookie = 0;
            byte Res_family = 0;
            int Res_port = 0;
            int[] Res_IP = new int[4];
            int[] Res_translationID = new int[3];
            byte Res_error_class, Res_error_number;
            int Res_error_code;
            int summ_length_atribute = 0;
            DatagramPacket packet = new DatagramPacket(new byte[btlength], btlength);
            ds.receive(packet);
            byte[] arr = packet.getData();
                
            temp_byte = new byte[2];
            temp_byte[0] = arr[0];
            temp_byte[1] = arr[1];
            Res_type_message = myFunc.ByteToShort(temp_byte);
                
            temp_byte = new byte[2];
            temp_byte[0] = arr[2];
            temp_byte[1] = arr[3];
            Res_length_message = myFunc.ByteToShort(temp_byte);
                
            temp_byte = new byte[4];
            temp_byte[0] = arr[4];
            temp_byte[1] = arr[5];
            temp_byte[2] = arr[6];
            temp_byte[3] = arr[7];
            Res_magic_cookie = myFunc.ByteToInt(temp_byte);                
                
            for (int i = 0; i < 3; i++)
            {
                temp_byte = new byte[4];
                temp_byte[0] = arr[8+i*4];
                temp_byte[1] = arr[9+i*4];
                temp_byte[2] = arr[10+i*4];
                temp_byte[3] = arr[11+i*4];
                Res_translationID[i] = myFunc.ByteToInt(temp_byte);
            }
            
            if (Res_type_message==0x0111){
                System.out.println("Error Response");
                
                Res_error_class = arr[22];
                Res_error_number = arr[23];
                Res_error_code = Res_error_class * 100 + Res_error_number;
                
                switch (Res_error_code){
                    case 300: System.out.println("Try Alternative"); break;
                    case 400: System.out.println("Bad Request"); break;
                    case 500: System.out.println("Server Error"); break;
                    case 600: System.out.println("Global Failure"); break;
                }
                
            }else if (Res_type_message==0x0101){
                temp_byte = new byte[2];
                temp_byte[0] = arr[20];
                temp_byte[1] = arr[21];
                Res_atribute_type = myFunc.ByteToShort(temp_byte);
                
                temp_byte = new byte[2];
                temp_byte[0] = arr[22];
                temp_byte[1] = arr[23];
                Res_atribute_length = myFunc.ByteToShort(temp_byte);
                    
                Res_family = arr[25];
                        
                temp_byte = new byte[2];
                temp_byte[0] = arr[26];
                temp_byte[1] = arr[27];
                Res_port = myFunc.ByteToShort(temp_byte);
                port = Res_port;
            
                for(int i = 0; i< 4;i++){
                    Res_IP[i] = Byte.toUnsignedInt(arr[28+i]);
                }
                IPv4 = Res_IP;
            }
            short indic = 0x0010;
            temp_short = indic;
            temp_byte = myFunc.ShortToByte(temp_short);
            data[0]=temp_byte[0];
            data[1]=temp_byte[1];
            ds.close();
        }catch(IOException e){
            System.err.println(e);
        }
    }
    
    public void start_indications(){
        if (!isIndications){
            ind = new Indications(data);
            isIndications = true;
        }
    }
    
    public String getIP(){
        return new StringBuilder().append(IPv4[0]).append('.').append(IPv4[1]).append('.').append(IPv4[2]).append('.').append(IPv4[3]).toString();
    }
    
    public int getPort(){
        return port;
    }
    
    public int[] getIParray(){
        return IPv4;
    }
    
    public String getIP_Port(){
        return new StringBuilder().append(getIP()).append(':').append(getPort()).toString();
    }
    
    class Indications extends Thread{
        byte[] byte_packet1;
        DatagramSocket dtss;
        
        Indications(){}
        
        Indications(byte[] bt){
            byte_packet1 = bt;
            this.start();
        }
        
        public void run(){
            while(true){
                try {
                    DatagramSocket ds = null;
                    boolean a = false;
                    while(!a){
                        try {
                            ds = new DatagramSocket(local_port);
                            a = true;
                        } catch (SocketException ex) {}
                    }
                    DatagramPacket packin = new DatagramPacket(byte_packet1, byte_packet1.length, ia, getport);
                    ds.send(packin);
                    ds.close();
                    Thread.sleep(5000);
                } catch (IOException ex) {
                    Logger.getLogger(myIP.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(myIP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
            
}
