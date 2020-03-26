package com.commonsl.util;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;





public class RSAUtils {
    private static final Logger log = LoggerFactory.getLogger(RSAUtils.class);
    public static String RSAFilePath =  PropertyUtil.getProperty("RSAFilePath");
    private static RSAPublicKey publicKey;
    private static RSAPrivateKey privateKey;
   

    /***
     * 
    * @Title: RSAUtils.java
    * @Package commons.lander.util
    * @Description: 生成公钥与私钥的方法
    * @author chenwenhao 
    * @date 2016-12-1 上午11:02:38
     */
    public static void genKeyPair(String filePath) {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        try {
            // 得到公钥字符串
            String publicKeyString = Base64.encode(publicKey.getEncoded());
            // 得到私钥字符串
            String privateKeyString = Base64.encode(privateKey.getEncoded());
            // 将密钥对写入到文件
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            BufferedWriter pribw = new BufferedWriter(prifw);
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void createKeyPair(String filePath) {
        genKeyPair(RSAFilePath+filePath);
    }

//    /**
//     * 从文件中输入流中加载公钥
//     *
//     * @param in
//     *            公钥输入流
//     * @throws Exception
//     *             加载公钥时产生的异常
//     */
//    public static String loadPublicKeyByFile(boolean online) {
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(RSAUtils.class.getClassLoader()
//                    .getResourceAsStream("pub-" + (online ? "product" : "test") + ".key"), "utf-8"));
//            String readLine = null;
//            StringBuilder sb = new StringBuilder();
//            while ((readLine = br.readLine()) != null) {
//                sb.append(readLine);
//            }
//            br.close();
//            return sb.toString();
//        } catch (Exception e) {
//            log.error("load public key error", e);
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 从字符串中加载公钥
     * 
     * @param publicKeyStr
     *            公钥数据字符串
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) {
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            log.error("load public key error", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文件中加载私钥
     * 
     * @param keyFileName
     *            私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public static String readToString(String keyFileName) {
        String encoding = "UTF-8";
        File file = new File(keyFileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) {
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
        	 e.printStackTrace();
        }
        return null;
    }

    public static String encrypt2(String toBeEncrypt,String keyFileName) {
        return  encrypt(toBeEncrypt, RSAFilePath+keyFileName);
    }

    /**
     * 公钥加密过程
     * 
     * @param toBeEncrypt
     *            toBeEncrypt
     *            true:正式环境 false:测试环境
     * @return
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt(String toBeEncrypt,String keyFileName) {
        if (publicKey == null) {
        	String publicKeyStr = RSAUtils.readToString(keyFileName);
            publicKey = RSAUtils.loadPublicKeyByStr(publicKeyStr);
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(toBeEncrypt.getBytes("utf-8"));
            return Base64.encode(output);
        } catch (Exception e) {
            log.error("load public key error", e);
        }
        return StringUtils.EMPTY;
    }


    public static String decrypt2(String cipherData, String keyFileName) {
        return  decrypt(cipherData, RSAFilePath+keyFileName) ;
    }

    /**
     * 私钥解密过程
     * 
     * @param cipherData
     *            密文数据
     *           true:正式环境 false:测试环境
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String cipherData, String keyFileName) {

        if (privateKey == null) {
            privateKey = RSAUtils.loadPrivateKeyByStr(RSAUtils.readToString(keyFileName));
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(Base64.decode(cipherData));
            return new String(output, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   
   


    public static void main(String[] args) throws Exception {
    	
//    	genKeyPair("E:/rsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    	
//    	//公钥加密
    	String token = "hwg123456";
        String encryptString = RSAUtils.encrypt(token, "E:/rsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/publicKey.keystore");
        System.out.println("密文:"+encryptString);
//        
        //私钥解密
       token = RSAUtils.decrypt(encryptString, "E:/rsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/privateKey.keystore");
        System.out.println("明文:"+token);
        
        

    }
    
    

}
