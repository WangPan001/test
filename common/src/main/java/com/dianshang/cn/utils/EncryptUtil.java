package com.dianshang.cn.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * TODO 加密工具类 md5加密出来的长度是32位   
 * sha加密出来的长度是40位.
 * 
 * @ClassName: Encrypt 
 * @author wangpan
 * @date 2018年8月9日 下午10:45:30
 * @version V2.0 
 *
 */
@SuppressWarnings("restriction")
public class EncryptUtil {
	

	/** The Constant UTF8. */
	private static final String UTF8 = "utf-8"; 
	
	/**
	 * 加密.
	 *
	 * @param inputText the input text
	 * @return the string
	 */
	public static String e(String inputText) {
		return md5(inputText);
	}
	
	/**
	 * 加密.
	 *
	 * @param inputText the input text
	 * @return the string
	 */
	public static String e16(String inputText) {
		return encrypt16(md5(inputText.replaceAll(" ","")));
	}

	/**
	 * 二次加密，应该破解不了了吧？.
	 *
	 * @param inputText the input text
	 * @return the string
	 */
	public static String md5AndSha(String inputText) {
		return sha(md5(inputText));
	}

	/**
	 * md5加密.
	 *
	 * @param inputText the input text
	 * @return the string
	 */
	public static String md5(String inputText) {
		return encrypt(inputText, "md5");
	}

	/**
	 * sha加密.
	 *
	 * @param inputText the input text
	 * @return the string
	 */
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	/**
	 * md5或者sha-1加密.
	 *
	 * @param inputText 要加密的内容
	 * @param algorithmName 加密算法名称：md5或者sha-1，不区分大小写
	 * @return the string
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	/**
	 * 返回十六进制字符串.
	 *
	 * @param arr the arr
	 * @return the string
	 */
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,
					3));
		}
		return sb.toString();
	}

	/**
	 * Encrypt16.
	 *
	 * @param md5 the md5
	 * @return the string
	 */
	public static String encrypt16(String md5) {
		int intCode = 0;
		String strRtn = new String();
		for (int i = 0; i < md5.length() / 2; i++) {
			intCode = Integer.parseInt(md5.substring(i, i + 1), 16)
					^ Integer.parseInt(md5.substring(16 + i, 16 + i + 1), 16);
			strRtn += Integer.toHexString(intCode);
		}
		return strRtn;
	}
    
    /**
     * *
     * MD5加码 生成32位md5码.
     *
     * @param inStr the in str
     * @return the string
     */
    public static String string2MD5(String inStr){
	MessageDigest md5 = null;
	try{
	    md5 = MessageDigest.getInstance("MD5");
	}catch (Exception e){
	    System.out.println(e.toString());
	    e.printStackTrace();
	    return "";
	}
	char[] charArray = inStr.toCharArray();
	byte[] byteArray = new byte[charArray.length];

	for (int i = 0; i < charArray.length; i++)
	    byteArray[i] = (byte) charArray[i];
	byte[] md5Bytes = md5.digest(byteArray);
	StringBuffer hexValue = new StringBuffer();
	for (int i = 0; i < md5Bytes.length; i++){
	    int val = ((int) md5Bytes[i]) & 0xff;
	    if (val < 16)
		hexValue.append("0");
	    hexValue.append(Integer.toHexString(val));
	}
	return hexValue.toString();
    }
        
    /**
     * 加密解密算法 执行一次加密，两次解密.
     *
     * @param inStr the in str
     * @return the string
     */ 
    public static String convertMD5(String inStr){

	char[] a = inStr.toCharArray();
	for (int i = 0; i < a.length; i++){
	    a[i] = (char) (a[i] ^ 't');
	}
	String s = new String(a);
	return s;
    }
    
    /**
     * BASE64编码.
     *
     * @param src the src
     * @return the string
     * @throws Exception the exception
     */  
	public String base64Encoder(String src) throws Exception {  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(src.getBytes(UTF8));  
    }  
      
    /**
     * BASE64解码.
     *
     * @param dest the dest
     * @return the string
     * @throws Exception the exception
     */  
	public String base64Decoder(String dest) throws Exception {  
        BASE64Decoder decoder = new BASE64Decoder();  
        return new String(decoder.decodeBuffer(dest), UTF8);  
    } 
    
    /**
     * MD5数字签名  短信发送验证码.
     *
     * @param src the src
     * @return the string
     * @throws Exception the exception
     */  
    public String md5Digest(String src) throws Exception {  
       // 定义数字签名方法, 可用：MD5, SHA-1  
       MessageDigest md = MessageDigest.getInstance("MD5");  
       byte[] b = md.digest(src.getBytes(UTF8));  
       return this.byte2HexStr(b);  
    }
      
    /**
     * 字节数组转化为大写16进制字符串.
     *
     * @param b the b
     * @return the string
     */  
    private String byte2HexStr(byte[] b) {  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < b.length; i++) {  
            String s = Integer.toHexString(b[i] & 0xFF);  
            if (s.length() == 1) {  
                sb.append("0");  
            }  
            sb.append(s.toUpperCase());  
        }  
        return sb.toString();  
    }    
        
        /**
         * The main method.
         *
         * @param args the args
         */
        public static void main(String []args){
            String ss =md5("a123456");
            System.out.println("加密后:"+ss);
            System.out.println("解密后:"+convertMD5(convertMD5(ss)));
            String s2Md5=string2MD5("a123456");
            System.out.println("解密后:"+convertMD5(convertMD5(s2Md5)));
//            System.out.println(encrypt16("111111"));
            
            String s = new String("a123456");  
            System.out.println("原始：" + s);  
            System.out.println("MD5后：" + string2MD5(s));  
            System.out.println("加密的：" + convertMD5(s));  
            System.out.println("解密的：" + convertMD5(convertMD5(s)));  
            
         

        }
}
