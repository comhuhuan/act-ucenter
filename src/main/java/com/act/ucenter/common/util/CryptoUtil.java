/**
 * @Title: CryptoUtil.java 
 * @Package com.act.web.util 
 * @Description: 加密解密方法 
 * @author   fmj
 * @modifier fmj
 * @date 2017-6-29 上午10:57:59
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CryptoUtil {

	/**
	 * 
	 * @Title: compressByte
	 * @Description:对byte字节进行压缩
	 * @create 2017-6-29 上午10:58:39
	 * @update 2017-6-29 上午10:58:39
	 */
	public static final byte[] compressByte(byte[] str) throws Exception {
		if (str == null)
			return null;

		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str);
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressed;
	}

	/**
	 * 
	 * @Title: encryptByVectorAndKey
	 * @Description: 根据encryptAlgorithm 加密 并base64
	 * @create 2017-6-29 上午11:03:08
	 * @update 2017-6-29 上午11:03:08
	 */
	public static String encryptAESAndBase64(byte[] sSrc, String vector,
			String secretkey) throws Exception {
		if (secretkey == null) {
			return null;
		}
//		secretkey = changeHexToString(secretkey);
		if (secretkey.length() != 16 && secretkey.length() != 32) {
			return null;
		}
		byte[] raw = secretkey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec(vector.getBytes(),0,16);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc);

		return new String(new Base64().encode(encrypted), "UTF-8");// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	/**
	 * @Title: encodeHexAndBase64
	 * @Description:根据hashAlgorithm参数进行哈希运算
	 * @create 2017-6-29 上午11:06:30
	 * @update 2017-6-29 上午11:06:30
	 */
	public static String encodeHexAndBase64(int encryption, byte[] tmp)
			throws Exception {

		String base64tmp = new String();
		switch (encryption) {
		case 0:
			base64tmp = new String(new Base64().encode(tmp));
			base64tmp = base64tmp.replace("\r", "").replace("\n", "")
					.replace("\r\n", "").replace("\t", "");
			break;
		case 1:
			base64tmp = Base64.encodeBase64String(DigestUtils.md5Hex(tmp)
					.getBytes("utf-8"));
			break;
		case 2:
			base64tmp = Base64.encodeBase64String(DigestUtils.sha1Hex(tmp)
					.getBytes("utf-8"));
			break;
		default:
			base64tmp = new String(new Base64().encode(tmp));
			base64tmp = base64tmp.replace("\r", "").replace("\n", "")
					.replace("\r\n", "").replace("\t", "");
			break;
		}
		return base64tmp;
	}

}
