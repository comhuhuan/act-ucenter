/**   
 * @Title: FileUtil.java 
 * @Package com.act.web.util 
 * @Description: (文件处理类) 
 * @author   fmj
 * @date 2017-2-17 下午3:29:13 
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import com.act.web.constant.CommonContant;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	// 根据后缀名称 判断调用哪种解析方法
	private static final String EXCEL = "xls";
	private static final String WPS_EXCEL = "et";
	private static final String EXCEL2007 = "xlsx";
	private static final String TXT = "txt";
	private static final String CSV = "csv";
	private static final int MAX_COLUMN_COUNT = 60;
	// 导出 私有域
	public static int PAGE_SIZE = 49999;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private String sheetId;
	private MultipartFile file;
	private HttpServletRequest request;
	private HttpServletResponse response;

	// 导入初始化
	public FileUtil(MultipartFile file, String sheetId) {
		this.file = file;
		this.sheetId = sheetId;
	}

	// 导出初始化
	public FileUtil(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

    /**
     * 删除文件件下所有文件.首先判断该路径是否为文件夹进行文件删除.如果是文件只会删除改文件
     * @param path
     *      {@code File} 删除文件夹对象
     *
     * @see com.act.web.module.dnsm.service.impl.RecurCompanyServiceImpl#approvedFile(Integer, MultipartFile, String)
     * @see com.act.web.module.dnsm.service.impl.AuthorityCompanyServiceImpl#approvedFile(Integer, MultipartFile, String)
     * @see com.act.web.module.dnsm.service.impl.DomainCompanyServiceImpl#approvedFile(Integer, MultipartFile, String)
     *
     * @since version2.0
     *
     */
    public static void deleteAllFile(File path) {
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            if(files != null){
                for (File file : files) {
                    deleteAllFile(file);
                }
            }
        }else{
            path.delete();
        }
    }

	// 下载 downloadZip
	public static HttpServletResponse downloadZip(File file,
			HttpServletResponse response) {
		try {
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(
					file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();

			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ file.getName());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				File f = new File(file.getPath());
				f.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	// 把接受的全部文件打成压缩包
	public static void zipFile(File[] files, ZipOutputStream outputStream) {
		for (File file : files) {
			zipFile(file, outputStream);
		}
	}

	// 根据输入的文件与输出流对文件进行打包
	public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
		FileInputStream in = null;
		BufferedInputStream bins = null;
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					in = new FileInputStream(inputFile);
					bins = new BufferedInputStream(in, 512);
					// org.apache.tools.zip.ZipEntry
					ZipEntry entry = new ZipEntry(inputFile.getName());
					ouputStream.putNextEntry(entry);
					// 向压缩文件中输出数据
					int nNumber;
					byte[] buffer = new byte[512];
					while ((nNumber = bins.read(buffer)) != -1) {
						ouputStream.write(buffer, 0, nNumber);
					}
				} else {
					try {
						File[] files = inputFile.listFiles();
						for (int i = 0; i < files.length; i++) {
							zipFile(files[i], ouputStream);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bins) {
				try {
					bins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @Title: writeStrToGz
	 * @Description: 字符串写入gz文件
	 * @create 2017-7-6 下午7:10:31
	 * @update 2017-7-6 下午7:10:31
	 */
	public static void writeStrToGz(String filePath, String str)
			throws IOException {

		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		byte[] data = new String(str).getBytes("utf-8");
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
				new FileOutputStream(file));
		gzipOutputStream.write(data);
		gzipOutputStream.flush();
		gzipOutputStream.close();
	}

	/**
	 * @Title: writeStrToGz
	 * @Description: xml写入gz文件
	 * @create 2017-7-6 下午7:10:31
	 * @update 2017-7-6 下午7:10:31
	 */
	public static void writeXmlToGz(String filePath, String xml)
			throws IOException, Exception {

		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		Document doc = null;
		doc = DocumentHelper.parseText(xml);

		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
				new FileOutputStream(file));
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter write = null;
		write = new XMLWriter(gzipOutputStream, format);
		write.write(doc);
		gzipOutputStream.flush();
		gzipOutputStream.close();
	}

	/**
	 *
	 * @Title: getFileToByte
	 * @Description: 文件转为二进制
	 * @create 2017-7-6 下午2:04:06
	 * @update 2017-7-6 下午2:04:06
	 */
	public static byte[] getFileToByte(File file) {
		byte[] by = new byte[(int) file.length()];
		try {
			InputStream is = new FileInputStream(file);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			byte[] bb = new byte[2048];
			int ch;
			ch = is.read(bb);
			while (ch != -1) {
				bytestream.write(bb, 0, ch);
				ch = is.read(bb);
			}
			by = bytestream.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return by;
	}

	// 向指定模板文件写入数据
	public void writeSheetBaseTemplate(String type, List<Object[]> resultList)
			throws Exception {
		if (null == resultList || resultList.size() == 0) {
			return;
		}
		OutputStream os = null;
		WritableWorkbook wwb = null;
		Workbook wb = null;
		try {
			int listSize = resultList.size() - 1;
			if (PAGE_SIZE < listSize) {
				// 头信息
				Object fistObject = resultList.get(0);
				// 截取除头之外的数据
				List dataList = new ArrayList(resultList.subList(1,
						resultList.size()));
				int pages = listSize / PAGE_SIZE;
				String path = getTempFilePath(type, null);
				List<Object> childList = null;
				if (listSize % PAGE_SIZE == 0) {
					for (int i = 0; i < pages; i++) {
						childList = new ArrayList(dataList.subList(i
								* PAGE_SIZE, (i + 1) * PAGE_SIZE));
						// 把头信息重新追加到集合中
						childList.add(0, fistObject);
						writeSheetBypage(type, childList, path, i);
					}
				} else {
					for (int i = 0; i < (pages + 1); i++) {
						if ((i + 1) * PAGE_SIZE < listSize) {
							childList = new ArrayList(dataList.subList(i
									* PAGE_SIZE, (i + 1) * PAGE_SIZE));
						} else {
							childList = new ArrayList(dataList.subList(i
									* PAGE_SIZE, listSize));
						}
						// 把头信息重新追加到集合中
						childList.add(0, fistObject);
						writeSheetBypage(type, childList, path, i);
					}
				}
				String zipPath = this.getTempFilePath(type, "zip");
				responseZip(zipPath, path);
				// 删除放excel的文件夹
				deleteDir(new File(path));
				return;
			}

			String fileName = URLEncoder.encode(
					this.getFileName(type) + ".xls", "UTF-8");

			getResponse().setContentType(CommonContant.CONTENT_TYPE);
			getResponse().setHeader(CommonContant.CONTENT_DISPOSITION,
					CommonContant.HEADER_VALUE + fileName);
			os = getResponse().getOutputStream();

			wb = Workbook.getWorkbook(new File(this.getFilePath(type)));
			WorkbookSettings settings = new WorkbookSettings();
			settings.setWriteAccess(null);
			wwb = Workbook.createWorkbook(os, wb, settings);
			WritableSheet sheet = null;

			sheet = wwb.getSheet(0);
			sheet.getSettings().setSelected(true);
			ExcelUtil.writeSheetForBasic(resultList, sheet);

			getResponse().flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wwb != null) {
				wwb.write();
				wwb.close();
			}
			if (os != null) {
				os.close();
			}
			if (wb != null) {
				wb.close();
			}
		}
	}

	/**
	 *
	 * @Title: writeSheet
	 * @Description: 导出excel
	 * @param  fileName 文件吗
	 * @param  reportName
	 * @param  resultList
	 * @create 2017-6-14 下午7:02:03
	 * @update 2017-6-14 下午7:02:03
	 */
	public void writeSheet(String fileName,String reportName, List resultList) throws Exception {
		if (null == resultList) {
			return;
		}
		if (PAGE_SIZE < resultList.size()) {
			return;
		}

//		String name = "exportfile_"
//				+ StringUtil.getCurrentDate("yyyyMMddHHmmssSSS");
		fileName = URLEncoder.encode(fileName + ".xls", "UTF-8");

		getResponse().setContentType(CommonContant.CONTENT_TYPE);
		getResponse().addHeader(CommonContant.CONTENT_DISPOSITION,
				CommonContant.HEADER_VALUE + fileName);
		OutputStream os = getResponse().getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet sheet = null;
		sheet = wwb.createSheet(reportName, 0);
		// List list =establish(resultList);
		ExcelUtil.writeSheet(resultList, sheet, reportName);
		ExcelUtil.closeExcel(os, wwb);

		getResponse().flushBuffer();

	}

	// 得到文件名称
	public String getFileName(String type) {
		String filename = "";
		if (type.equals("ircs")) {
			filename = "IRCS经营单位信息";
		}
		return filename;
	}

	// 删除文件夹
	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	// 压缩分页excel到response
	public HttpServletResponse responseZip(String zipPath, String path)
			throws Exception {
		File file = new File(path);
		File zipFileDir = new File(zipPath);
		if (!zipFileDir.exists()) {
			zipFileDir.mkdirs();
		}
		String tmp = "exportfile_"
				+ StringUtil.getCurrentDate("yyyyMMddHHmmssSSS");
		String fileName = URLEncoder.encode(tmp + ".zip", "UTF-8");
		File zipFile = new File(zipPath + fileName);
		FileOutputStream fous = null;
		ZipOutputStream zipOut = null;
		try {
			fous = new FileOutputStream(zipFile);
			zipOut = new ZipOutputStream(fous);
			zipFile(file.listFiles(), zipOut);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != zipOut) {
				zipOut.close();
			}
			if (null != fous) {
				fous.close();
			}
		}
		return downloadZip(zipFile, getResponse());
	}

	// 分页导出
	public void writeSheetBypage(String reportName, List resultList,
			String path, int step) throws Exception {
		File zipDir = new File(path);
		if (!zipDir.exists()) {
			zipDir.mkdirs();
		}
		OutputStream out = null;
		Workbook wb = null;
		WritableWorkbook wwb = null;
		try {
			String fileName = URLEncoder.encode("exportfile_" + step + ".xls",
					"UTF-8");
			File file = new File(path + fileName);
			String fileUrl = this.getFilePath(reportName);
			out = new FileOutputStream(file);
			wb = Workbook.getWorkbook(new File(fileUrl));
			WorkbookSettings settings = new WorkbookSettings();
			settings.setWriteAccess(null);
			wwb = Workbook.createWorkbook(out, wb, settings);
			WritableSheet sheet = null;
			sheet = wwb.getSheet(0);
			sheet.getSettings().setSelected(true);
			ExcelUtil.writeSheetForBasic(resultList, sheet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wwb != null) {
				wwb.write();
				wwb.close();
			}
			if (null != wb) {
				wb.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	// 得到文件路径
	public String getFilePath(String type) {
		String webpath = this.getClass().getClassLoader().getResource("/")
				.getPath();
		String downPath = webpath.substring(0, webpath.indexOf("WEB-INF"));

		String filename = "";
		if (type.equals("ircs")) {
			filename = "ircsOperatorInfo.xls";
		}
		return downPath + "download" + File.separator + filename;
	}

	// 得到临时文件路径
	private String getTempFilePath(String type, String zip) {
		String webpath = this.getClass().getClassLoader().getResource("/")
				.getPath();
		String downPath = webpath.substring(0, webpath.indexOf("WEB-INF"));
		String seqNo = StringUtil.getCurrentDate("yyyyMMddHHmmssSSS");
		String fileDir = "";
		if (type.equals("ircs")) {
			fileDir = "ircsOperatorInfo";
		}
		if ("zip".equals(zip)) {
			return downPath + "download/zip/" + fileDir + "/";
		} else {
			return downPath + "download/temp/" + fileDir + "/" + seqNo + "/";
		}
	}

	// 初始化数据 data
	public void parse() {
		if (EXCEL2007.equalsIgnoreCase(sheetId)) {
			parse2007();
		} else if (EXCEL.equalsIgnoreCase(sheetId)
				|| WPS_EXCEL.equalsIgnoreCase(sheetId)) {
			parse2003();
		} else if (TXT.equalsIgnoreCase(sheetId)
				|| CSV.equalsIgnoreCase(sheetId)) {
		}
	}

	// xls et 后缀解析excel
	private void parse2003() {
		if (null != file) {
			HSSFWorkbook workbook = initFile2003(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormat df = new DecimalFormat("#");
			for (int row = 0; row < sheet.getLastRowNum() + 1; ++row) {
				int rowContent = 0;
				Map<String, Object> map = new ListOrderedMap();
				try {
					int i = 0;
					HSSFCell timeCell = null;
					HSSFRow aRow = sheet.getRow(row);
					for (int j = 0; j < aRow.getLastCellNum(); j++) {
						timeCell = aRow.getCell(j);
						String temp = "";
						if (timeCell != null) {
							temp = timeCell.toString();
							if (null != temp) {
								int cellType = timeCell.getCellType();
								if (cellType == 0) {
									if (temp.indexOf("月") > 0) {
										dateFormat.setLenient(false);
										temp = dateFormat.format(timeCell
												.getDateCellValue());
									} else {
										temp = df.format(timeCell
												.getNumericCellValue());
									}
								}
							}
						}
						map.put("C" + i, temp);

						i++;
						if (MAX_COLUMN_COUNT < i) {
							break;
						}
						if (temp != null && !"".equalsIgnoreCase(temp)) {
							rowContent++;
						}
					}
					if (rowContent == 0) {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (rowContent != 0) {
						data.add(map);
					}
				}
			}
			workbook = null;
		}
	}

	private HSSFWorkbook initFile2003(MultipartFile file) {
		InputStream is = null;
		try {
			is = file.getInputStream();
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			return workbook;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void parse2007() {
		if (null != file) {
			XSSFWorkbook workbook = initFile2007(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (int row = 0; row < sheet.getLastRowNum() + 1; ++row) {
				int rowContent = 0;
				Map<String, Object> map = new ListOrderedMap();
				try {
					int i = 0;
					XSSFCell timeCell = null;
					XSSFRow aRow = sheet.getRow(row);
					for (int j = 0; j < aRow.getLastCellNum(); j++) {
						timeCell = aRow.getCell(j);
						String temp = "";
						if (timeCell != null) {
							temp = timeCell.toString();
							if (null != temp) {
								int cellType = timeCell.getCellType();
								if (cellType == 0) {
									if (temp.indexOf("月") > 0) {
										dateFormat.setLenient(false);
										temp = dateFormat.format(timeCell
												.getDateCellValue());
									} else {
										temp = timeCell.getRawValue().trim();
									}
								}
							}
						}
						map.put("C" + i, temp);

						i++;
						if (MAX_COLUMN_COUNT < i) {
							break;
						}
						if (temp != null && !"".equalsIgnoreCase(temp)) {
							rowContent++;
						}
					}
					if (rowContent == 0) {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (rowContent != 0) {
						data.add(map);
					}
				}
			}
			workbook = null;
		}
	}

	private XSSFWorkbook initFile2007(MultipartFile file) {
		InputStream is = null;
		try {
			is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			return workbook;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	// 根据 规定格式的表头 和数据 返回导出数据
	public <T> List<Object[]> initExport(List<T> dataList,
			String dataHeaderStrRepost) {
		try {
			List<Object[]> resultList = new ArrayList<Object[]>(); // 导出数据

			String[] headerTotalTemp = dataHeaderStrRepost.split("\\|");// 表头数组
			List<String> fieldList = new ArrayList<String>(); // 导出表头

			String[] fieldName = new String[headerTotalTemp.length]; // 标题数组
			for (int i = 0; i < headerTotalTemp.length; i++) {
				String[] attributeTemp = headerTotalTemp[i].split(":");
				fieldList.add(attributeTemp[0].trim());// 获取导出字段名
				fieldName[i] = attributeTemp[1].trim();// 导出标题
			}
			resultList.add(fieldName);
			for (int i = 0; i < dataList.size(); i++) {
				T info = dataList.get(i);
				Object[] resultObj = new Object[fieldList.size()];
				Class<?> cls = info.getClass();
				for (int j = 0; j < fieldList.size(); j++) {
					Method m = cls.getMethod("get" + fieldList.get(j));
					resultObj[j] = m.invoke(info);
				}
				resultList.add(resultObj); // 将结果集加入list中
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
