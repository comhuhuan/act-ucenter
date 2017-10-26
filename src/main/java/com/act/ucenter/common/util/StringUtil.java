/**   
 * @Title: StringUtils.java 
 * @Package com.act.web.util 
 * @Description: (字符串处理公共类) 
 * @author   fmj
 * @date 2017-2-7 下午2:07:42 
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtil {

	/**
	 * DES秘钥
	 */
	public static final String KEY_DES = "yhsj_idc@act2016";

	/**
	 * DES算法
	 */
	private static final String ALGORITHM_DES = "DES";
	/**
	 * codeSequence
	 */
	public static char[] codeSequence = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
	/**
	 * 默认日期格式
	 */
	private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * MD5 加密
	 */
	public static String encoderByMd5(String str)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String md5Str = DigestUtils.md5Hex(str);
		return md5Str;
	}

	/**
	 * DES加密
	 */
	public static String DESEncrypt(String data) {
		return DESEncrypt(data, KEY_DES);
	}

	/**
	 * DES解密
	 */
	public static String DESDecrypt(String data) {
		return DESDecrypt(data, KEY_DES);
	}

	/**
	 * DES解密
	 */
	public static String DESDecrypt(String data, String key) {
		return DESCipher(data, key, Cipher.DECRYPT_MODE);
	}

	/**
	 * DES加密 返回加密后的数据(经过base64编码)
	 */
	public static String DESEncrypt(String data, String key) {
		return DESCipher(data, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * DES的加密解密 返回加密或解密的数据
	 */
	private static String DESCipher(String data, String key, int mode) {
		try {
			Key k = getKey(key.getBytes());
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			cipher.init(mode, k);
			return mode == Cipher.DECRYPT_MODE ? new String(
					cipher.doFinal(TranscodeUtil.base64StrToByteArray(data)))
					: TranscodeUtil.byteArrayToBase64Str(cipher.doFinal(data
							.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将base64编码后的密钥字符串转换成密钥对象 返回密钥对象
	 */
	private static Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;

	}

	/**
	 * 生成随机字符串
	 */
	public static String getRandomName(int n) {
		StringBuffer buffer = new StringBuffer(
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < n; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}

	/**
	 * 根据不同日期格式类型获取当前日期
	 */
	public static String getCurrentDate(String format) {
		if (format == null) {
			format = DEFAULT_DATE_FORMAT;
		}
		Date utilDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(utilDate);
	}

	/**
	 * 
	 * @Title: getRandom
	 * @Description: 得到 随机整数
	 * @create 2017-7-4 下午2:45:40
	 * @update 2017-7-4 下午2:45:40
	 */
	public static Integer getRandomInt() {
		Random random = new Random();
		return random.nextInt(100);
	}
	
	
}
