/**   
 * @date 2017-6-2 下午8:23:56
 * @Title: ParamsUtil.java
 * @Package com.act.web.util
 * @Description: 接口参数变换工具类
 * @author xujian
 * @modifier xujian
 * @date 2017-6-2 下午8:23:56
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import com.act.web.utilcode.AESUtil;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;


public class ParamsUtil {
	private static final String SHA256 = "SHA-256";

	/**
	 * 
	 * @name 中文名称
	 * @Description: 对字符串和随机字符串拼接后获取hash值
	 * @author xujian
	 * @date 创建时间:2017-6-2 下午8:26:49
	 * @param password
	 * @param randVal
	 * @return
	 * @throws Exception
	 * @version V1.0
	 */
	public static String generatePwdHash(String password, String randVal) throws Exception {
        String temp = password + randVal;
        byte[] tempData = temp.getBytes("utf-8");
        return sha256String(tempData);
    }
	
	
	/**
	 * 对文件进行sha256散列.
	 */
	public static byte[] sha256File(InputStream input) throws Exception {
		return digest(input, SHA256);
	}
	public static byte[] sha256(byte[]  input) throws Exception {
		return digest(input, SHA256);
	}
    public static String sha256String(byte[]  input) throws Exception {
        return AESUtil.encodeHexString(digest(input, SHA256));
    }
    
    /**
	 * 对字符串进行散列
	 */
	private static byte[] digest(byte[] input, String algorithm)throws Exception {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] result = digest.digest(input);
			return result;
		} catch (GeneralSecurityException e) {
            throw new Exception(e);
		}
	}
	/**
	 * 
	 * @name 中文名称
	 * @Description: 对文件进行散列
	 * @author xujian
	 * @date 创建时间:2017-6-2 下午8:28:15
	 * @param input
	 * @param algorithm
	 * @return
	 * @throws Exception
	 * @version V1.0
	 */
	private static byte[] digest(InputStream input, String algorithm) throws Exception {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 8 * 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return messageDigest.digest();
		} catch (GeneralSecurityException e) {
			throw new Exception(e);
		}
	}
}
