/**
 * @Title: FileUploadSmmsUtil.java 
 * @Package com.act.web.util 
 * @Description: 基础数据上报 管局工具类
 * @author   fmj
 * @modifier fmj
 * @date 2017-6-28 下午3:58:17
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUploadSmmsUtil {
	
	private static final Logger log = LoggerFactory
			.getLogger(FileUploadSmmsUtil.class);
	
	/**
	 * 压缩格式 Y-zip【1】
	 */
	private static final Integer COMPRESSION_FORMAT_ZIP = 1;

	/**
	 * 对称加密算法 Y-加密【1】
	 */
	private static final Integer ENCRYPT_ALGORITHM_YES = 1;

	/**
	 * @Title: handleDataUpload
	 * @Description: 处理 dataUpload节点 1.根据 compressionFormat 进行压缩(0:无压缩; 1:Zip)
	 *               2.根据encryptAlgorithm 加密(0：不进行加密, 1：AES加密算法) 3.base64
	 * @param compressionFormat
	 *            0:无压缩; 1:Zip压缩格式 ISMS应根据SMMS的要求完成压缩算法的具体实现
	 * @param encryptAlgorithm
	 *            对称加密算法
	 * @return
	 * @create 2017-6-28 下午5:33:28
	 * @update 2017-6-28 下午5:33:28
	 */
	public static String handleDataUpload(String xml,
			Integer compressionFormat, Integer encryptAlgorithm,
			String aesPassword, String aesIv) throws Exception {
		byte[] dataUploadByte = xml.getBytes("UTF-8");
		if (COMPRESSION_FORMAT_ZIP.equals(compressionFormat)) {
			dataUploadByte = CryptoUtil.compressByte(dataUploadByte);
		}

		if (ENCRYPT_ALGORITHM_YES.equals(encryptAlgorithm)) {

			// 根据encryptAlgorithm 加密 并base64
			String encryptedData = CryptoUtil.encryptAESAndBase64(
					dataUploadByte, aesIv, aesPassword);

			return encryptedData;
		}
		xml = new String(new Base64().encode(dataUploadByte), "UTF-8");
		return xml;
	}

	/**
	 * @Title: handleDataHash
	 * @Description: 处理dataHash节点 1.根据compressionFormat要求进行压缩 2.串接消息认证密钥
	 *               3.根据hashAlgorithm参数进行哈希运算 4.base64
	 * @param compressionFormat
	 *            压缩格式如下 0:无压缩; 1:Zip压缩格式
	 * @param hashAlgorithm
	 *            哈希算法 0：无hash; 1：MD5; 2：SHA-1
	 * @throws Exception
	 * @create 2017-6-28 下午7:51:55
	 * @update 2017-6-28 下午7:51:55
	 */
	public static String handleDataHash(String xml, Integer compressionFormat,
			Integer hashAlgorithm, String msgKey) throws Exception {
		byte[] dataHashByte = xml.getBytes("UTF-8");
		if (COMPRESSION_FORMAT_ZIP.equals(compressionFormat)) {
			dataHashByte = CryptoUtil.compressByte(dataHashByte);
		}
		byte[] tmp = new byte[dataHashByte.length + msgKey.getBytes().length];
		System.arraycopy(dataHashByte, 0, tmp, 0, dataHashByte.length);
		System.arraycopy(msgKey.getBytes(), 0, tmp, dataHashByte.length,
				msgKey.getBytes().length);
		return CryptoUtil.encodeHexAndBase64(hashAlgorithm, tmp);
	}

	/**
	 * 
	 * @Title: uploadSftp
	 * @Description: 上传文件到sftp
	 * @param ip
	 *            sftp ip地址
	 * @param user
	 *            用户名
	 * @param passWord
	 *            密码
	 * @param port
	 *            端口
	 * @param sftpPath
	 *            上传夹路径
	 * @param srcDir
	 *            本地文件路径
	 * @param destFile
	 *            sftp文件路径
	 * @param backups
	 *            是否保留上传文件
	 * @create 2017-6-30 上午9:05:38
	 * @update 2017-6-30 上午9:05:38
	 */
	public static void uploadSftp(String ip, String user, String passWord,
			Integer port, String uploadPath, String srcDir, String destFile,
			Boolean backups) throws Exception {
		ChannelSftp sftp = null;
		try {
			log.info("企业基础信息上报");
			log.info("ip：" + ip);
			log.info("user：" + user);
			log.info("passWord：" + passWord);
			log.info("port：" + port);
			log.info("上传夹路径：" + uploadPath);
			log.info("本地文件路径：" + srcDir);
			
			sftp = SFTPUtil.getSftp(ip, user, passWord, port);
			try {
				sftp.cd(uploadPath);
			} catch (SftpException e) {
				mkdirBasicFolder(uploadPath, sftp);
				sftp.mkdir(uploadPath);
				sftp.cd(uploadPath);
			}
			SFTPUtil.uploadFileToSftp(sftp, srcDir, destFile);
			if (!backups) {
				CommonUtil.deleteFile(srcDir);
			}
		} finally {
			if (null != sftp)
//				sftp.disconnect();
				SFTPUtil.closedConnect(sftp);
		}

	}

	/**
	 * @Title: mkdirBasicFolder
	 * @Description: 创建基础数据根目录
	 * @create 2017-7-6 下午4:20:37
	 * @update 2017-7-6 下午4:20:37
	 */
	private static void mkdirBasicFolder(String uploadPath, ChannelSftp sftp)
			throws Exception {

		String sftpPath = uploadPath.substring(0, uploadPath.lastIndexOf("/"));// 上传路径

		try {
			sftp.cd(sftpPath);
		} catch (SftpException e) {
			sftp.mkdir(sftpPath);
		}

	}

}
