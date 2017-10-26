/**   
 * @date 2017-5-25 下午6:52:20
 * @Title: CommonUtil.java
 * @Package com.act.web.util
 * @Description: 公共方法类
 * @author xujian
 * @modifier xujian
 * @date 2017-5-25 下午6:52:20
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import com.act.web.utilcode.AESUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CommonUtil {
	private static final int RANDOM_STR_LENGTH = 20;// 默认随机字符串长度
	private static final String GZIP = ".gz";
	private static final String POINT = ".";
	private static Logger log = Logger.getLogger(CommonUtil.class);
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");// 获取yyyy-MM-dd时间
	private static int FLOW_NUM_SUFF = 0;

	/**
	 *
	 * @name 中文名称
	 * @Description: 按yyyy-MM-dd获取当前时间
	 * @author xujian
	 * @date 创建时间:2017-6-2 下午7:35:58
	 * @return
	 * @version V1.0
	 */
	public static String getTimes() {
		return SDF.format(new Date());
	}

	/**
	 *
	 * @name 中文名称
	 * @Description: 创建指定路径文件或文件夹,不存在时创建
	 * @author xujian
	 * @date 创建时间:2017-6-2 下午7:38:41
	 * @param path
	 * @version V1.0
	 */
	public static void mkdirsIfNotExist(String path) {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
	}

	/**
	 *
	 * @name 中文名称
	 * @Description: 删除目录下的 所有文件
	 * @author xujian
	 * @date 创建时间:2017-6-15 上午10:08:25
	 * @param file
	 * @version V1.0
	 */
	public static void deleteFileAll(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}

	public static void deleteAllZipFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.getName().endsWith(".zip")) {
					f.delete();
				}
			}
		}
	}

	/**
	 *
	 * @name 中文名称
	 * @Description: 上报文件名
	 * @author xujian
	 * @date 创建时间:2017-6-2 下午7:52:21
	 * @param type
	 *            类型：1-递归文件;2-权威文件;3-域名注册
	 * @return
	 * @version V1.0
	 */
	public static Long getUploadFileName(int type) {
		/*
		 * 流水号生产，为了避免重复，同是不与截留接口生成的重复，采用如下方式 格式 ：xyyMMddHHmmssXXX
		 * 前面的x代表来源（1-递归文件;2-权威文件;3-域名注册 ），中间的yyMMddHHmmss为时间格式精确到秒，后面的3个XXX
		 * 代表后缀，该后缀累加生成
		 */
		SimpleDateFormat newFormat = new SimpleDateFormat("yyMMddHHmmss");
		String flowNumber = "1" + newFormat.format(new Date())
				+ getFlowNumberSuff();
		return Long.parseLong(flowNumber);
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 获取流水号
	 * @author xujian
	 * @date 创建时间:2017-6-2 下午7:49:40
	 * @return
	 * @version V1.0
	 */
	public static synchronized String getFlowNumberSuff() {
		if (FLOW_NUM_SUFF == 999) {
			FLOW_NUM_SUFF = 0;
		}
		FLOW_NUM_SUFF++;
		String tmp = String.valueOf(FLOW_NUM_SUFF);
		switch (tmp.length()) {
		case 3:
			return tmp;
		case 2:
			return "0" + tmp;
		case 1:
			return "00" + tmp;
		default:
			return "000";
		}
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 获取业务流水号
	 * @author xujian
	 * @date 创建时间:2017-6-2 下午2:36:04
	 * @param enterpriseId
	 *            企业表示id
	 * @param incressmentId
	 *            自增id
	 * @return
	 * @version V1.0
	 */
	public static String getBussinessNum(String enterpriseId,
			String incressmentId) {
		Long times = System.currentTimeMillis();
		int timestamp = (int) (times / 1000);
		return enterpriseId + timestamp + incressmentId;
	}

	/**
	 * 将xml文件写到filepath中
	 * 
	 * @param filePath
	 * @param xsmstring
	 * @return
	 * @throws Exception
	 */
	public static String createUploadXmlFile(String filePath, String xsmstring)
			throws Exception {
		// 文件对象
		File file = new File(filePath);
		// 文件输出流
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			Document doc = null;
			doc = DocumentHelper.parseText(xsmstring);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter write = null;
			write = new XMLWriter(fos, format);
			write.write(doc);
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return filePath;
	}

	/**
	 * 读取path路径下的xml文件内容
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String getXMLInputStream(String path) throws Exception {
		// 获取文件输入流
		FileInputStream fis = new FileInputStream(new File(path));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis,
				"utf-8"));
		StringBuffer xmlBuffer = new StringBuffer(fis.available());
		String line = null;
		while ((line = reader.readLine()) != null) {
			xmlBuffer.append(line);
		}
		return xmlBuffer.toString();
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 生成指定长度的字符串，默认生成20个字符串长度
	 * @author xujian
	 * @date 创建时间:2017-6-2 下午8:18:40
	 * @param length
	 * @return
	 * @version V1.0
	 */
	public static String genRandomString(int length) {
		if (length <= 0) {
			return genRandomString();
		}
		Random rand = new Random(System.nanoTime());
		StringBuffer sb = new StringBuffer(RANDOM_STR_LENGTH);
		char head[] = { '0', 'A', 'a' };
		while (sb.length() != length) {
			int num = rand.nextInt(78);
			int x = num / 26;
			int y = num % 26;
			if (x == 0 && y >= 10)
				continue;
			sb.append((char) (head[x] + y));
		}
		return sb.toString();
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 生成20长度的随机字符串
	 * @author xujian
	 * @date 创建时间:2017-6-2 下午8:17:45
	 * @return
	 * @version V1.0
	 */
	public static String genRandomString() {
		Random rand = new Random(System.nanoTime());
		StringBuffer sb = new StringBuffer(RANDOM_STR_LENGTH);
		char head[] = { '0', 'A', 'a' };
		while (sb.length() != RANDOM_STR_LENGTH) {
			int num = rand.nextInt(78);
			int x = num / 26;
			int y = num % 26;
			if (x == 0 && y >= 10)
				continue;
			sb.append((char) (head[x] + y));
		}
		return sb.toString();
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 
	 *               将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
	 * @author xujian
	 * @date 创建时间:2017-6-8 下午9:21:10
	 * @param sourceFilePath
	 *            待压缩的文件路径
	 * @param zipFilePath
	 *            压缩后存放路径
	 * @param fileName
	 *            压缩后文件的名称
	 * @return
	 * @version V1.0
	 */
	public static File zipFile(String sourceFilePath, String zipFilePath,
			String fileName) {
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		deleteAllZipFile(sourceFile);

		if (sourceFile.exists() == false) {
			log.info("待压缩的文件目录：" + sourceFilePath + "不存在.");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if (zipFile.exists()) {
					// log.info(zipFilePath + "目录下存在名字为:" + fileName +".zip"
					// +"打包文件.");
					zipFile.delete();
					// log.info("删除"+zipFilePath + "目录下名字为:" + fileName +".zip"
					// +"的打包文件.");
					zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				}
				File[] sourceFiles = sourceFile.listFiles();
				if (null == sourceFiles || sourceFiles.length < 1) {
					log.info("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
				} else {
					fos = new FileOutputStream(zipFile);
					zos = new ZipOutputStream(new BufferedOutputStream(fos));
					byte[] bufs = new byte[1024 * 10];
					for (int i = 0; i < sourceFiles.length; i++) {
						// 创建ZIP实体，并添加进压缩包
						ZipEntry zipEntry = new ZipEntry(
								sourceFiles[i].getName());
						zos.putNextEntry(zipEntry);
						// 读取待压缩的文件并写进压缩包里
						fis = new FileInputStream(sourceFiles[i]);
						bis = new BufferedInputStream(fis, 1024 * 10);
						int read = 0;
						while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
							zos.write(bufs, 0, read);
						}
					}
				}
				return zipFile;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				// 关闭流
				try {
					if (null != bis)
						bis.close();
					if (null != zos)
						zos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 结果代码转换
	 * @author xujian
	 * @date 创建时间:2017-6-7 下午5:47:04
	 * @param flag
	 * @return
	 * @version V1.0
	 */
	public static String getResultMsg(Integer flag) {
		switch (flag) {
		case 0:
			return "上报成功！";
		case 1:
			return "操作成功，数据已经下载完毕";
		case 2:
			return "目前服务器端没有可以下载的数据";
		case 3:
			return "服务器端数据需要下载，请继续调用本接口进行下载";
		case 4:
			return "用户名错误";
		case 5:
			return "密码错误";
		case 6:
			return "解密失败";
		case 7:
			return "哈希值验证未通过";
		case 8:
			return "解压缩失败";
		case 9:
			return "加密算法类型错误";
		case 10:
			return "Hash算法类型错误";
		case 11:
			return "压缩格式错误";
		case 12:
			return "认证信息错误，服务器拒绝响应";
		case 13:
			return "非本省企业，服务器拒绝响应";
		case 14:
			return "认证错误，随机数小于20个字符，服务器拒绝响应";
		case 15:
			return "上报的数据文件过大，服务器拒绝响应，请将上报的数据文件调整为50MB内并重新上报";
		case 16:
			return "您的上报权限未开放，请联系所在省通信管理局";
		case 901:
			return "系统正在维护中，您的上报请求未被受理，请稍后重新报备";
		case 902:
			return "系统正在维护中，您的下载请求未被受理，请稍后重新下载";
		case 998:
			return "数据ID错误";
		case 997:
			return "数据格式错误，无法通过xsd校验";
		case 996:
			return "上报超时";
		default:
			return "其他错误";
		}
	}

	/**
	 * @Description 基础代码下载请求xml拼接方法
	 * @author liuyang
	 * @date 创建时间:2017年6月9日14:10:05
	 * @param type
	 *            基础代码类型 1-企业性质 2-区域代码 3-证件类型
	 * @param version
	 *            企业该代码数据的最大版本号
	 * @return String
	 * @version 1.0
	 */
	public static String getDownloadXml(String type, String version) {
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<download><type>").append(type).append("</type><version>")
				.append(version).append("</version>").append("</download>");
		return xml.toString();
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description:把字符串写到文件中
	 * @author xujian
	 * @date 创建时间:2017-6-9 下午6:46:04
	 * @param f
	 * @version V1.0
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public static void writeStrToFile(File f, String str) throws Exception {
		FileOutputStream out = new FileOutputStream(f);
		out.write(str.getBytes("utf-8"));
		out.close();
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 删除文件信息
	 * @author xujian
	 * @date 创建时间:2017-6-12 上午10:43:33
	 * @param fileName
	 * @version V1.0
	 */
	public static void deleteFile(String fileName) {
		File f = new File(fileName);
		f.delete();
	}

	/**
	 * 压缩文件
	 * 
	 * @param file
	 *            文件
	 * @return
	 * @throws IOException
	 */
	public static File zipFile(File file) throws IOException {
		// 文件输入流
		FileInputStream in = null;
		// 压缩输出流
		ZipOutputStream zipOut = null;
		// 文件输出流
		FileOutputStream out = null;
		try {
			File zipFile = new File(file.getParent(), file.getName() + ".zip");
			in = new FileInputStream(file);
			out = new FileOutputStream(zipFile);
			zipOut = new ZipOutputStream(out);
			ZipEntry entry = new ZipEntry(file.getName());
			zipOut.putNextEntry(entry);
			int nNumber;
			while ((nNumber = in.read()) != -1) {
				zipOut.write(nNumber);
			}
			return zipFile;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (zipOut != null) {
					zipOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 判断文件修改日期是否是今天，是-true;否-false
	 * @author xujian
	 * @date 创建时间:2017-6-16 上午9:15:36
	 * @param f
	 * @return
	 * @version V1.0
	 */
	public static boolean checkFileIsChange(File f) {
		long zero = System.currentTimeMillis() / (1000 * 3600 * 24)
				* (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 当天凌晨毫秒数
		long twelve = zero + 24 * 60 * 60 * 1000 - 1;// 当天23点59分59秒的毫秒数
		if (null == f) {
			return false;
		}
		long modifed = f.lastModified();
		if (0 >= modifed) {
			return false;
		}
		if (modifed < zero) {// 今天为修改
			return false;
		}
		if (modifed > twelve) {// 时间不对
			return false;
		}

		return true;

	}

	private static String getFileSource(File file) {
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer xmlBuffer = new StringBuffer(inputStream.available());
			String line = null;
			while ((line = reader.readLine()) != null) {
				xmlBuffer.append(line);
			}
			return xmlBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
				if (null != reader)
					reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 加密压缩文件
	 * @author xujian
	 * @date 创建时间:2017-6-16 上午10:48:16
	 * @param path
	 * @param pwd
	 * @param pyl
	 * @version V1.0
	 */
	public static void encyptZipFile(String path, String pwd, String pyl) {
		FileOutputStream out = null;
		File infile = null;
		File outFile = null;
		try {

			infile = new File(path);
			String name = infile.getName();// 获取文件名
			outFile = new File(path.substring(0, path.indexOf(".")) + "_temp"
					+ name.substring(name.indexOf(".")));

			byte[] zipFile = AESUtil.encrypt(
					getFileSource(infile).getBytes("utf-8"),
					AESUtil.decodeHex(pwd), AESUtil.decodeHex(pyl));
			out = new FileOutputStream(infile);
			out.write(Base64.encodeBase64(zipFile));
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.out.println(outFile.renameTo(infile));

	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 解密文件
	 * @author xujian
	 * @date 创建时间:2017-6-16 上午10:48:30
	 * @param path
	 * @param pwd
	 * @param pyl
	 * @version V1.0
	 */
	public static void dencyptZipFile(String path, String pwd, String pyl) {
		FileOutputStream out = null;
		File infile = null;
		File outFile = null;
		try {

			infile = new File(path);
			String name = infile.getName();// 获取文件名
			outFile = new File(path.substring(0, path.indexOf(".")) + "_temp"
					+ name.substring(name.indexOf(".")));

			byte[] zipFile = AESUtil.decrypt(Base64.decodeBase64(getFileSource(
					infile).getBytes("utf-8")), AESUtil.decodeHex(pwd), AESUtil
					.decodeHex(pyl));
			out = new FileOutputStream(infile);
			out.write(zipFile);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		outFile.renameTo(infile);
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description:文件转字节
	 * @author xujian
	 * @date 创建时间:2017-6-19 上午9:10:56
	 * @param file
	 * @return
	 * @throws Exception
	 * @version V1.0
	 */
	public static byte[] file2byte(File file) throws Exception {
		byte[] buffer = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
			return buffer;
		} finally {
			if (null != fis) {
				fis.close();
			}
			if (null != bos) {
				bos.close();
			}
		}
	}

	public static void encryptZipFile(String zipFileName, String pwd, String pyl)
			throws Exception {
		File zipFile = new File(zipFileName);
		byte[] fileUtf8Array = file2byte(zipFile);
		byte[] passwordBinaryArray = AESUtil.decodeHex(pwd);
		byte[] iv = AESUtil.decodeHex(pyl);
		byte[] encyptData = AESUtil.encrypt(fileUtf8Array, passwordBinaryArray,
				iv);
		zipFile.delete();
		byte2file(encyptData, zipFileName);
		log.info(zipFileName + "加密完成!");
	}

	public static void byte2file(byte[] buf, String path) throws Exception {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			File f = new File(path);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} finally {
			if (null != bos) {
				bos.close();
			}
			if (null != fos) {
				fos.close();
			}
		}
	}

	public static String createUploadFile(String filePath, String xsmstring)
			throws Exception {
		// 文件对象
		File file = new File(filePath);
		// 文件输出流
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] b = xsmstring.getBytes();
			fos.write(b);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return filePath;
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 对文件进行重命名
	 * @author xujian
	 * @date 创建时间:2017-7-5 上午11:46:02
	 * @param srcpath
	 *            源文件
	 * @param destpath
	 *            目标文件
	 * @version V1.0
	 */
	public static void fileRename(String srcpath, String destpath) {
		File oldFile = new File(srcpath);
		File newFile = new File(destpath);
		oldFile.renameTo(newFile);
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 对文件进行gzip压缩
	 * @author xujian
	 * @date 创建时间:2017-7-10 上午9:49:08
	 * @param filePath
	 *            文件路径
	 * @param charSet
	 *            编码
	 * @param isBak
	 *            是否保留原文件
	 * @version V1.0
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static String gZipFile(String filePath, String charSet, boolean isBak)
			throws IOException {
		BufferedReader in = null;
		BufferedOutputStream bos = null;
		String gzFile = filePath.substring(0, filePath.lastIndexOf(POINT))
				+ GZIP;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					filePath), charSet));

			bos = new BufferedOutputStream(new GZIPOutputStream(
					new FileOutputStream(gzFile)));
			int c;
			while ((c = in.read()) != -1) {
				bos.write(String.valueOf((char) c).getBytes(charSet));
			}
			bos.flush();
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (in != null) {
				in.close();
			}
		}
		if (!isBak) {// 不保留原文件
			deleteFile(filePath);
		}
		return gzFile;
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 解压缩gz文件
	 * @author xujian
	 * @date 创建时间:2017-7-10 上午10:29:44
	 * @param filePath
	 *            文件路径
	 * @param charSet
	 *            编码
	 * @param isBak
	 *            是否保留原文件
	 * @version V1.0
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void unGZipFile(String filePath, String charSet, boolean isBak)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		BufferedReader in = null;
		BufferedOutputStream bos = null;
		try {
			in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
					new FileInputStream(filePath)), charSet));
			bos = new BufferedOutputStream(new FileOutputStream(
					filePath.substring(0, filePath.indexOf(GZIP))));
			String s;
			// 读取压缩文件里的内容
			while ((s = in.readLine()) != null) {
				bos.write(s.getBytes(charSet));
			}
			bos.flush();
		} finally {
			if (in != null) {
				in.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
		if (!isBak) {
			deleteFile(filePath);
		}
	}


}
