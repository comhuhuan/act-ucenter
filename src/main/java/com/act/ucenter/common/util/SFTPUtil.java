
/**   
 * @date 2017-6-6 下午1:47:42
 * @Title: SFTPUtil.java
 * @Package com.act.web.util
 * @Description: 实现SFTP文件上传和下载
 * @author xujian
 * @modifier xujian
 * @date 2017-6-6 下午1:47:42
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import org.apache.log4j.Logger;

import java.io.*;

public class SFTPUtil {
	
	private static final String CHANNEL_TYPE = "sftp";
	private static final int FILE_READ_BYTE = 1024;
	private static Logger log = Logger.getLogger(SFTPUtil.class);
//	private Session session = null;
	
	/**
	 * 
	 * @name 中文名称
	 * @Description: 获取SFTP连接session
	 * @author xujian
	 * @date 创建时间:2017-6-6 下午1:57:21
	 * @param ip IP地址
	 * @param username 用户名
	 * @param pwd 密码
	 * @param port 端口,如果取默认值,传-1
	 * @return
	 * @version V1.0
	 * @throws JSchException 
	 */
	public static Session getSftpSession(String ip, String username, String pwd, int port) throws JSchException{
		JSch jsch = new JSch();
		Session session = null;
		if(port <=0){
			//连接服务器，采用默认端口
			session = jsch.getSession(username, ip);
		}else{
			//采用指定的端口连接服务器
			session = jsch.getSession(username, ip ,port);
		}
		//设置密码
		session.setPassword(pwd);
		//设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		//设置超时时间
		session.connect();
		return session;
		
	}
	
	public static void closedConnect(ChannelSftp sftp){
		try {
			Session session = sftp.getSession();
			if (session !=null) {
				session.disconnect();
			}
		} catch (JSchException e) {
			log.error("关闭seesion异常：",e);
		}
		sftp.disconnect();
	}
	
	
	/**
	 * 
	 * @name 中文名称
	 * @Description: 获取sftp连接
	 * @author xujian
	 * @date 创建时间:2017-6-6 下午4:29:30
	 * @param ip
	 * @param username
	 * @param pwd
	 * @param port
	 * @return
	 * @throws JSchException
	 * @version V1.0
	 */
	public static ChannelSftp getSftp(String ip, String username, String pwd, int port) throws JSchException{
		Session session = getSftpSession(ip, username, pwd, port);
		Channel channel = session.openChannel(CHANNEL_TYPE);
		channel.connect();
		return (ChannelSftp) channel;
	}
	/**
	 * 
	 * @name 中文名称
	 * @Description: 文件上传
	 * @author xujian
	 * @date 创建时间:2017-6-6 下午4:51:17
	 * @param sftp sftp连接
	 * @param srcDir 源文件
	 * @param targetDir 目标文件
	 * @throws Exception
	 * @version V1.0
	 */
	public static void uploadFileToSftp(ChannelSftp sftp, String srcDir, String targetDir) throws Exception {
		OutputStream os = null;
		InputStream in = null;
		try {
			os = sftp.put(targetDir);
			in = new FileInputStream(new File(srcDir));
			byte[] b = new byte[FILE_READ_BYTE];
			int n = 0;
			while((n = in.read(b)) != -1){
				os.write(b, 0, n);
			}
			os.flush();
		} catch (Exception e) {
			throw e;
		} finally{
			if (os!=null) {
				os.close();
			}
			if (in!=null) {
				in.close();
			}
		}
		
	}
	
	/**
	 * 
	 * @name 中文名称
	 * @Description: 将本地文件名为src的文件上传到目标服务器，
	 * 				   目标文件名为dst，若dst为目录，则目标文件名将与src文件名相同。
	 * 				   采用默认的传输模式：OVERWRITE
	 * @author xujian
	 * @date 创建时间:2017-6-14 下午4:08:25
	 * @param sftp
	 * @param src
	 * @param dst
	 * @throws SftpException
	 * @version V1.0
	 */
	public static void uploadToSftp(ChannelSftp sftp, String src, String dst) throws SftpException{
		sftp.put(src, src);
	}
	/**
	 * 
	 * @name 中文名称
	 * @Description: 从SFTP下载文件
	 * @author xujian
	 * @date 创建时间:2017-6-6 下午4:58:48
	 * @param sftp sftp连接
	 * @param srcDir 源文件
	 * @param targetDir 目标文件
	 * @throws Exception
	 * @version V1.0
	 */
	public static void downloadFileFromSftp(ChannelSftp sftp, String srcDir, String targetDir) throws Exception{
		InputStream in = sftp.get(srcDir);
		OutputStream os = new FileOutputStream(new File(targetDir));
		byte[] b = new byte[FILE_READ_BYTE];
		int n = 0;
		while((n = in.read(b)) != -1){
			os.write(b, 0, n);
		}
		os.flush();
		os.close();
		in.close();
	}


	/**
	 * 
	 * @name 中文名称
	 * @Description: 对SFTP文件重新命名
	 * @author xujian
	 * @date 创建时间:2017-6-7 下午9:14:32
	 * @param sftp
	 * @param path 路径
	 * @param oldName 旧名称
	 * @param newName 新名称
	 * @throws Exception
	 * @version V1.0
	 */
	public static void renameFile(ChannelSftp sftp,String path, String oldName, String newName) throws Exception{
		if (!ValidatorUtil.validateStrEmpty(path)) {
			sftp.rename(oldName, newName);
		} else {
			sftp.cd(path);
			sftp.rename(oldName, newName);
		}
		
	}
	
	
}
