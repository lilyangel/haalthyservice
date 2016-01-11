package com.haalthy.service.common;

/**
 * Created by Ken on 2016-01-05.
 */
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 编码工具类
 * 1.将byte[]转为各种进制的字符串
 * 2.base 64 encode
 * 3.base 64 decode
 * 4.获取byte[]的md5值
 * 5.获取字符串md5值
 * 6.结合base64实现md5加密
 * 7.AES加密
 * 8.AES加密为base 64 code
 * 9.AES解密
 * 10.将base 64 code AES解密
 * @author uikoo9
 * @version 0.0.7.20140601
 */
public class EncodeUtil {

    private static String ENCRYPTKEY = "www.haalthy.com";
    private static String SOURCESTRING = "89qwertyuipas56xcvb789qwerpasd123tyui45hjklz67fgnm12lzxcv34dfghjkbnm";
    private static String SOURCESTRINGNUMERIC = "78945612300321456789963852741085201479631590487623";

    public static void main(String[] args) throws Exception {
        String content = "我爱你";
        System.out.println("加密前：\n" + content);

        String key = "";
        System.out.println("加密密钥和解密密钥：\n" + key);


    }

    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.IsEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * 获取byte[]的md5值
     * @param bytes byte[]
     * @return md5
     * @throws Exception
     */
    public static byte[] md5(byte[] bytes) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);

        return md.digest();
    }

    /**
     * 获取字符串md5值
     * @param msg
     * @return md5
     * @throws Exception
     */
    public static byte[] md5(String msg) throws Exception {
        return StringUtils.IsEmpty(msg) ? null : md5(msg.getBytes());
    }

    /**
     * 结合base64实现md5加密
     * @param msg 待加密字符串
     * @return 获取md5后转为base64
     * @throws Exception
     */
    public static String md5Encrypt(String msg) throws Exception{
        return StringUtils.IsEmpty(msg) ? null : base64Encode(md5(msg));
    }

    /**
     * AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content) throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(ENCRYPTKEY.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content) throws Exception {

        return base64Encode(aesEncryptToBytes(content));
    }

    /**
     * AES解密
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes) throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(ENCRYPTKEY.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }

    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr) throws Exception {

        return StringUtils.IsEmpty(encryptStr)? null : aesDecryptByBytes(base64Decode(encryptStr));
    }

    /**
     * 获取指定位数的验证码
     * @param byteCount 验证码的位数
     * @return 验证码String
     * @throws Exception
     */
    public static String getAuthCode(int byteCount) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        if(byteCount <= 0 && byteCount > 10)
            byteCount = 6;
        Random rdm = new Random(System.currentTimeMillis());
        for(int i = 0;i<byteCount;i++)
            stringBuilder.append(SOURCESTRING.charAt(Math.abs(rdm.nextInt())%68));
        return stringBuilder.toString().toUpperCase();
    }
    /**
     * 获取指定位数的验证码
     * @param byteCount 验证码的位数
     * @return 验证码String
     * @throws Exception
     */
    public static String getNumericAuthCode(int byteCount) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        if(byteCount <= 0 && byteCount > 10)
            byteCount = 6;
        Random rdm = new Random(System.currentTimeMillis());
        for(int i = 0;i<byteCount;i++)
            stringBuilder.append(SOURCESTRINGNUMERIC.charAt(Math.abs(rdm.nextInt())%50));
        return stringBuilder.toString().toUpperCase();
    }

    public  static String getEncryptAuthCode(int byteCount) throws Exception{
        return aesEncrypt(md5Encrypt(getAuthCode(byteCount)));
    }

    public  static String getEncryptAuthCode(String authCode) throws Exception{
        return aesEncrypt(md5Encrypt(authCode));
    }

}