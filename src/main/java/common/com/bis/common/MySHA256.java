package com.bis.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class MySHA256 {
    
//    public static void main(String[] args) {
//        MySHA256 my = new MySHA256();
//        String a = my.JavaSHA256("puhDDCPVhQ0zLfaBaZOpFgEdpv95ddGGIstjFRScFDY/api/b75932b0-2774-465c-985e-45521b75f5f3{\"appid\":\"app3\"}");
//        System.out.println(a);
//    }

    /** 
     * @Title: ApacheSHA256 
     * @Description: ApacheSHA256 
     * @param msg
     * @return 
     */
    public String ApacheSHA256(String msg)
    {
        System.out.println(msg);
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(msg.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        return encdeStr;
        
    }
    
    /** 
     * @Title: JavaSHA256 
     * @Description: java自带sha256
     * @param msg
     * @return 
     */
    public String JavaSHA256(String msg){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(msg.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr.toUpperCase();
    }
    
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
