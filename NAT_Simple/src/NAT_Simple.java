//package nat_simple;

import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.math.*;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.*;

public class NAT_Simple {

    private String host = "stun.t-online.de"; //69.0.208.27 87.242.77.38 132.177.123.13 stun.sipgate.net stun3.l.google.com stun.t-online.de
    private int port = 3478;//19302;//3478//;
    
    NAT_Simple(String host, int port){
        //this.host = host;
        //this.port = port;
    }
    
    private void sendMassage(String mes){
        try{
            String dy = LocalDate.now().toString();
            String tme = LocalTime.now().toString();
            //System.out.println(dy);
            tme = tme.replace(':', '-');
            tme = tme.replace('.', '-');
            //System.out.println(tme);
            URL ur = new URL(new StringBuilder("ftp://0files-server:123456@files-server.ucoz.ru/").append(dy).append('-').append(tme).append(".txt").toString());
            URLConnection upload = ur.openConnection();   
            PrintWriter writer = new PrintWriter(upload.getOutputStream());
            int asd;
            short length_message = 0;
            short temp_short;
            int temp_int;
            byte[] data = new byte[20];
            byte[] temp_byte = null;
            
            //STUN Massage Type
            //System.out.println("check1");
            temp_short = 0x0001;
            //oos.flush();
            temp_byte = ShortToByte(temp_short);
            data[0]=temp_byte[0];
            data[1]=temp_byte[1];
            //System.out.println("---");
            //System.out.println(temp_short);
            //System.out.println(temp_byte.length);
            //System.out.println(temp_byte[0]);
            //System.out.println(temp_byte[1]);
            
            //Massage Length
            //System.out.println("check2");
            temp_short = length_message;
            temp_byte = ShortToByte(temp_short);
            data[2]=temp_byte[0];
            data[3]=temp_byte[1];
            
            //Magic Cookie
            //System.out.println("check3");
            temp_int = 0x2112A442;
            temp_byte = IntToByte(temp_int);
            data[4] = temp_byte[0];
            data[5] = temp_byte[1];
            data[6] = temp_byte[2];
            data[7] = temp_byte[3];
            
            //Вывод массива
            for(int i = 0; i < 8; i++){
                writer.println(data[i]);
            }
            
            //Translation ID
            //System.out.println("check4");
            for (int i = 0; i < 3; i++){
                temp_int = 12314+i;
                temp_byte = IntToByte(temp_int);
                data[8+i*4] = temp_byte[0];
                data[9+i*4] = temp_byte[1];
                data[10+i*4] = temp_byte[2];
                data[11+i*4] = temp_byte[3];
            }
                        
            /*
            System.out.println(temp_int);
            for (int i=0;i<temp_byte.length;i++){
                System.out.println(temp_byte[i]);
            }
            int f = 4;
            data = mes.getBytes();
            data = new byte[200];
            data[0]=0;
                    */
            //Short.
            writer.println("write");
            for (int i = 0; i<data.length; i++)
            {
                writer.println(data[i]);
            }
            InetAddress addr = InetAddress.getByName(host);
            writer.println(addr);
            DatagramPacket pack = new DatagramPacket(data, data.length, addr, port);
            DatagramSocket ds = new DatagramSocket(19302);
            ds.send(pack);
            int btlength = 64;
            writer.println("-=-=-=-=-=-");
            boolean isExit = false;
            short Res_type_message = 0, Res_length_message = 0, Res_atribute_type = 0, Res_atribute_length = 0;
            int Res_magic_cookie = 0;
            byte Res_family = 0;
            short Res_port = 0;
            int[] Res_IP = new int[4];
            int[] Res_translationID = new int[3];
            while(!isExit){
                DatagramPacket packet = new DatagramPacket(new byte[btlength], btlength);
                if (packet!=null) isExit = true;
                ds.receive(packet);
                //ds.
                //System.out.println(": ");
                byte[] arr = packet.getData();
                //char[] crr = new char[arr.length];
                //for (int i = 0; i<arr.length;i++){
                //    crr[i] = (char) arr[i];
                //}
                //PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, "Cp1251"), true);
                //pw.println(crr);
                for (int i = 0; i < arr.length; i++)
                {
                    writer.println(arr[i]);
                }
                
                //if(!isExit){
                temp_byte = new byte[2];
                temp_byte[0] = arr[0];
                temp_byte[1] = arr[1];
                Res_type_message = ByteToShort(temp_byte);
                
                temp_byte = new byte[2];
                temp_byte[0] = arr[2];
                temp_byte[1] = arr[3];
                Res_length_message = ByteToShort(temp_byte);
                
                temp_byte = new byte[4];
                temp_byte[0] = arr[4];
                temp_byte[1] = arr[5];
                temp_byte[2] = arr[6];
                temp_byte[3] = arr[7];
                Res_magic_cookie = ByteToInt(temp_byte);
                
                
                for (int i = 0; i < 3; i++)
                {
                    temp_byte = new byte[4];
                    temp_byte[0] = arr[8+i*4];
                    temp_byte[1] = arr[9+i*4];
                    temp_byte[2] = arr[10+i*4];
                    temp_byte[3] = arr[11+i*4];
                    Res_translationID[i] = ByteToInt(temp_byte);
                }
                
                //for (int i = 0; i<Res_length_message/)
                //
                temp_byte = new byte[2];
                temp_byte[0] = arr[20];
                temp_byte[1] = arr[21];
                Res_atribute_type = ByteToShort(temp_byte);
                
                temp_byte = new byte[2];
                temp_byte[0] = arr[22];
                temp_byte[1] = arr[23];
                Res_atribute_length = ByteToShort(temp_byte);
                
                Res_family = arr[25];
                
                temp_byte = new byte[2];
                temp_byte[0] = arr[26];
                temp_byte[1] = arr[27];
                Res_port = ByteToShort(temp_byte);
                
                for(int i = 0; i< 4;i++){
                    Res_IP[i] = Byte.toUnsignedInt(arr[28+i]);
                }
                //}
                
                //------------------------------------------------------
                writer.println("++++++++++++");
                writer.println(Integer.toHexString(Res_type_message));
                //System.out.println(Res_type_message);
                writer.println(Res_length_message);
                writer.println(Integer.toHexString(Res_magic_cookie));
                writer.println("TranslationID");
                for (int i =0;i<3;i++){
                    writer.println(Res_translationID[i]);
                }
                writer.println("Type:");
                if (Res_atribute_type == 0x0001){
                    writer.println("MAPPED-ADDRESS");
                }
                else
                {    
                    writer.println(Res_atribute_type);
                }
                writer.println("Port:");
                writer.println(Res_port);
                writer.println("IP:");
                for (int i = 0; i < 4; i++){
                    writer.print(Res_IP[i]);
                    writer.print(".");
                }
                writer.println();
                //System.out.println();
                //System.out.println(crr);
                
            }
            
            short indic = 0x0010;
            temp_short = indic;
            temp_byte = ShortToByte(temp_short);
            data[0]=temp_byte[0];
            data[1]=temp_byte[1];
            
            new Indications(data, ds, addr);
            //Recipent r1;
            //r1 = new Recipent(ds);
            writer.close();
            /*
            while(true){
                DatagramPacket packe = new DatagramPacket(new byte[1024], 1024);
                ds.receive(packe);
                //System.out.println(": ");
                byte[] arr2 = packe.getData();
                char[] crr2 = new char[arr2.length];
                for (int i = 0; i<arr2.length;i++){
                    crr2[i] = (char) arr2[i];
                }
                //PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, "Cp1251"), true);
                //pw.println(crr);
                System.out.println(crr2);
                
            }
                    */
            //ds.close();
            System.out.println("OK");
        }catch(IOException e){
            System.err.println(e);
        }
    }
    
    public static void main(String[] args) {
        String strin = null;
        try{
            while (strin!="exit"){
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                //BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "Cp1251"));
                //strin = br.readLine();
                strin = "check";
                NAT_Simple sndr = new NAT_Simple("stun.1und1.de", 3478);
                sndr.sendMassage(strin);
                strin = br.readLine();
            }
            System.out.println("accept");
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    public static byte[] ShortToByte(short a){
        int length = Short.BYTES;
        byte[] bt = new byte[length];
        for (int i = 0; i < length; i++){
            //System.out.println("1-");
            bt[length - i - 1] = (byte)(a%256);
            //System.out.println("2-");
            a = (short) (a / 256);
            //System.out.println("3-");
        }
        return bt;
    }
    
    public static byte[] IntToByte(int a){
        int length = Integer.BYTES;
        byte[] bt = new byte[length];
        for (int i = 0; i < length; i++){
            //System.out.println("1-");
            bt[length - i - 1] = (byte)(a%256);
            //System.out.println("2-");
            a = (int) (a / 256);
            //System.out.println("3-");
        }
        return bt;
    }
    
    public static byte[] LongToByte(long a){
        int length = Long.BYTES;
        byte[] bt = new byte[length];
        for (int i = 0; i < length; i++){
            //System.out.println("1-");
            bt[length - i - 1] = (byte)(a%256);
            //System.out.println("2-");
            a = (long) (a / 256);
            //System.out.println("3-");
        }
        return bt;
    }
    
    public static short ByteToShort(byte[] ar){
        short re = 0;
        for (int i = 0; i < Short.BYTES; i++){            
            re = (short) (re + (short) (( short) (ar[Short.BYTES - i - 1]) * (short) Math.pow(256, i)));
        }
        return re;
    }
    
    public static int ByteToInt(byte[] ar){
        int re = 0;
        for (int i = 0; i < Integer.BYTES; i++){
            re = (int) (re + (int) ((int) (ar[Integer.BYTES - i - 1]) * (int) Math.pow(256, i)));
        }
        return re;
    }
    
    public static long ByteToLong(byte[] ar){
        long re = 0;
        for (int i = 0; i < Long.BYTES; i++){
            re = (long) (re + (long) ((long) (ar[Long.BYTES - i -1]) * (long) Math.pow(256, i)));
        }
        return re;
    }
    
    class Indications extends Thread{
        
        byte[] byte_packet1;
        DatagramSocket dtss;
        InetAddress ia;
        
        Indications(byte[] pb, DatagramSocket dst, InetAddress iat){
            ia = iat;
            dtss = dst;
            byte_packet1 = pb;
            this.start();
        }
        
        public void run(){
            while(true){
                try {
                    //System.out.println("Indications");
                    DatagramPacket packin = new DatagramPacket(byte_packet1, byte_packet1.length, ia, port);
                    dtss.send(packin);
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NAT_Simple.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(NAT_Simple.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }        
        
    }
    
    class Recipent extends Thread{
        
        String[] arra = new String[1000];
        //byte[] byte_packet1;
        DatagramSocket dtss, ds;
        int last = 0;
        //InetAddress ia;
        
        Recipent(DatagramSocket dst) throws SocketException{
            //ia = iat;
            ds = dst;
            //dtss.close();
            //ds = new DatagramSocket(19302);
            //byte_packet1 = pb;
            this.start();
        }
        
        public void run(){
            while(true){
                try {
                    //System.out.println("Recipent");
                    DatagramPacket packr = new DatagramPacket(new byte[1024], 1024);
                    //System.out.println("check1");
                    //dtss.
                    //Thread.sleep(500);
                    ds.receive(packr);
                    //System.out.println("check15");
                    byte[] arr2 = packr.getData();
                    //System.out.println("check2");
                    char[] crr2 = new char[arr2.length];
                    for (int i = 0; i<arr2.length;i++){
                        crr2[i] = (char) arr2[i];
                    }
                    //System.out.println("check3");
                    System.out.println(crr2);
                    arra[last]=crr2.toString();
                    last++;
                    //ds.send(packr);
                } catch (IOException ex) {
                    Logger.getLogger(NAT_Simple.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }        
        
    }
    
}
