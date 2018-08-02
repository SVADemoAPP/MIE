/**   
 * @Title: RSAUtils.java 
 * @Package com.bis.common 
 * @Description: RSA 工具 
 * @author labelCS   
 * @date 2018年8月2日 上午10:00:21 
 * @version V1.0   
 */
package com.bis.common;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/** 
 * @ClassName: RSAUtils 
 * @Description: RSA 工具 
 * @author labelCS 
 * @date 2018年8月2日 上午10:00:21 
 *  
 */
public class RSAUtils
{
private static final String ALGORITHM = "RSA";
    
    private static final String PROVIDER = "BC";
    
    private static final String TRANSFORMATION = "RSA/None/PKCS1Padding";
    
    private static final int KEY_SIZE = 1024;
    
    private static KeyPair keyPair = null;
    
    
    /**
     * 初始化密钥对
     */
    static {
        try{
             Security.addProvider(new BouncyCastleProvider());
             SecureRandom secureRandom = new SecureRandom();
             KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
             keyPairGenerator.initialize(KEY_SIZE, secureRandom);
             keyPair = keyPairGenerator.generateKeyPair();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 获取公钥
     * @return
     */
    public static RSAPublicKey getRSAPublicKey() {
         return (RSAPublicKey)keyPair.getPublic();
    } 
    
    /**
     * 获取Base64编码的公钥
     * @return
     */
    public static String getBase64PublicKey() {
        RSAPublicKey publicKey = getRSAPublicKey();
        //return new String(Base64.encodeBase64(publicKey.getEncoded()));
        return Base64.encodeBase64String(publicKey.getEncoded());
    } 
    
    
    /**
     * 使用公钥加密
     * @param data
     * @return
     */
    public static String encrypt(byte[] data) {
        String ciphertext = "";
        try {
            Cipher cipher = Cipher.getInstance(keyPair.getPublic().getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            ciphertext = Base64.encodeBase64String(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ciphertext;
    }
    
    
    
    /**
     * 使用私钥解密
     * @param ciphertext
     * @return
     */
    public static String decrypt(String ciphertext) {
        String plaintext = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
            RSAPrivateKey pbk = (RSAPrivateKey)keyPair.getPrivate();
            cipher.init(Cipher.DECRYPT_MODE, pbk);
            byte[] data = cipher.doFinal(Base64.decodeBase64(ciphertext));
            plaintext = new String(data);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return plaintext;
    }
}
