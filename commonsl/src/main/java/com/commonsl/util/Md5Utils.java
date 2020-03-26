package com.commonsl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Utils {

    /** 16进制的字符数组 */
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    /**
     * 
     * 
     * @param source
     *            需要加密的原字符串
     * @param encoding
     *            指定编码类型
     * @param uppercase
     *            是否转为大写字符串
     * @return
     */
    public static String MD5Encode(String source, String encoding, boolean uppercase) {
        String result = null;
        try {
            result = source;
            // 获得MD5摘要对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用指定的字节数组更新摘要信息
            messageDigest.update(result.getBytes(encoding));
            // messageDigest.digest()获得16位长度
            result = byteArrayToHexString(messageDigest.digest());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return uppercase ? result.toUpperCase() : result;
    }

    /**
     * 转换字节数组为16进制字符串
     * 
     * @param bytes
     *            字节数组
     * @return
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte tem : bytes) {
            stringBuilder.append(byteToHexString(tem));
        }
        return stringBuilder.toString();
    }

    /**
     * 转换byte到16进制
     * 
     * @param b
     *            要转换的byte
     * @return 16进制对应的字符
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    ////////////////////////////////////////////////////////////////////

    /**
     * @param origin 输入字符串
     * @return MD5结果
     */
    public static String getMD5(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (Exception exception) {
        }
        return resultString;
    }

    /**
     * @param data 输入数据
     * @return MD5结果
     */
    public static String getMD5(byte[] data) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(data));
        } catch (Exception exception) {
        }
        return resultString;
    }

    /**
     * 生成文件的md5校验值
     * @param file 文件
     * @return 文件的MD5值, 或者 null
     */
    public static String getMD5(File file) throws IOException {
        InputStream fis = new FileInputStream(file);
        try {
            byte[] buffer = new byte[1024];
            int numRead = 0;
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
//                Log.d(TAG, "MessageDigest don't support MD5.");
                return null;
            }
            while ((numRead = fis.read(buffer)) > 0) {
                md.update(buffer, 0, numRead);
            }
            return byteArrayToHexString(md.digest());
        } finally {
            fis.close();
        }
    }

}

//备注:其实apache commones Codec包中有常用的一些加密算法实现