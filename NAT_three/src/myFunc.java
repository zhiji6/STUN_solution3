//package nat_three;

public class myFunc {
    
    public static byte[] ShortToByte(short a){
        int length = Short.BYTES;
        byte[] bt = new byte[length];
        for (int i = 0; i < length; i++){
            bt[length - i - 1] = (byte)(a%256);
            a = (short) (a / 256);            
        }
        return bt;
    }
    
    public static byte[] IntToByte(int a){
        int length = Integer.BYTES;
        byte[] bt = new byte[length];
        for (int i = 0; i < length; i++){
            bt[length - i - 1] = (byte)(a%256);
            a = (int) (a / 256);            
        }
        return bt;
    }
    
    public static byte[] LongToByte(long a){
        int length = Long.BYTES;
        byte[] bt = new byte[length];
        for (int i = 0; i < length; i++){
            bt[length - i - 1] = (byte)(a%256);
            a = (long) (a / 256);            
        }
        return bt;
    }
    
    public static short ByteToShort(byte[] ar){
        short re = 0;
        for (int i = 0; i < Short.BYTES; i++){            
            re = (short) (re + (short) (( short) (Byte.toUnsignedInt(ar[Short.BYTES - i - 1])) * (short) Math.pow(256, i)));
        }
        return re;
    }
    
    public static int ByteToInt(byte[] ar){
        int re = 0;
        for (int i = 0; i < Integer.BYTES; i++){
            re = (int) (re + (int) ((int) (Byte.toUnsignedInt(ar[Integer.BYTES - i - 1])) * (int) Math.pow(256, i)));
        }
        return re;
    }
    
    public static long ByteToLong(byte[] ar){
        long re = 0;
        for (int i = 0; i < Long.BYTES; i++){
            re = (long) (re + (long) ((long) (Byte.toUnsignedInt(ar[Integer.BYTES - i - 1])) * (long) Math.pow(256, i)));
        }
        return re;
    }
    
}
