/*
 * @(#)InstructionCheckUtils.java 1.00 2017-9-21
 *
 * Copyright (c) 2017-9-21 Shenzhen Surfilter Network Technology Co.,Ltd. All rights reserved.
 */
package com.act.ucenter.common.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXValidator;
import org.dom4j.util.XMLErrorHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 该方法用于指令校验
 * 
 * @author Administrator
 * 
 */
public class InstructionCheckUtils {
	private static final String BLACK_COMMAND_XSD = "instructionXsd/blackCommand.xsd";
	private static final String FILTER_COMMAND_XSD = "instructionXsd/filterCommand.xsd";
	private static Logger log = Logger.getLogger(InstructionCheckUtils.class);

	/**
	 * @Description: 域名信息校验
	 * @param domain
	 * @return 是域名返回true
	 */
	public static boolean checkDomain(String domain) {
		boolean flag = false;
		String regEx="^[a-zA-Z0-9\\u4E00-\\u9FA5]+(?:\\-+[a-zA-Z0-9\\u4E00-\u9FA5]+)*(?:\\.[a-zA-Z0-9\\u4E00-\\u9FA5]+(?:\\-+[a-zA-Z0-9\\u4E00-\\u9FA5]+)*)+(;[a-zA-Z0-9\\u4E00-\\u9FA5]+(?:\\-+[a-zA-Z0-9\\u4E00-\\u9FA5]+)*(?:\\.[a-zA-Z0-9\\u4E00-\u9FA5]+(?:\\-+[a-zA-Z0-9\u4E00-\u9FA5]+)*)+)*$";
		//String regEx = "^(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(domain);
		if (matcher.matches()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @Description: 时间信息校验yyyy- MM-dd HH:mm:ss格式
	 * @param time
	 * @return
	 */
	public static boolean checkTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		try {
			format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 根据xml字符串校验xml
	 * @author xujian
	 * @date 创建时间:2017-9-22 下午3:00:32
	 * @param xmlPath
	 * @param type
	 * @return
	 * @version V1.0
	 */
	public static boolean checkByXsd(String xmlPath, Integer type) {

		// 建立schema工厂
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
		Validator validator;
		try {
			URL url = getXsdFilePath(type);
			if (url == null) {
				log.info("xsd文件不存在，XML校验失败");
				return false;
			}
			Schema schema = schemaFactory.newSchema(url);
			validator = schema.newValidator();
		} catch (Exception e) {
			// 如果XML非格式验证异常，则不验证
			log.error("XML格式异常", e);
			return false;
		}
		// 得到验证的数据源
		try {
			Source source = new StreamSource(xmlPath);
			validator.validate(source);
		} catch (Exception ex) {
			log.error("XML校验失败", ex);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 使用xsd校验下发指令xml字符串
	 * @author xujian
	 * @date 创建时间:2017-9-22 下午2:41:03
	 * @param xmlStr
	 * @param type
	 *            指令类型:2-域名过滤;3-黑名单列表
	 * @return
	 * @version V1.0
	 */
	public static String validateCommandByXsd(String xmlStr, Integer type) {
		StringBuffer sb = null;
		String xsdFileName = getXsdFileName(type);
		if (xsdFileName == null) {// 没有获取到xsd文件名，即xsd文件不存在
			log.error("xsd文件不存在，校验失败!");
			return "校验失败!";
		}
		try {
			// 创建默认的XML错误处理器
			XMLErrorHandler errorHandler = new XMLErrorHandler();
			// 获取基于 SAX 的解析器的实例
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 解析器在解析时验证 XML内容。
			factory.setValidating(true);
			// 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
			factory.setNamespaceAware(true);
			// 使用当前配置的工厂参数创建 SAXParser 的一个新实例。
			SAXParser parser = factory.newSAXParser();
			// 获取要校验xml文档实例
			Document document = DocumentHelper.parseText(xmlStr);
			// 设置 XMLReader 的基础实现中的特定属性。核心功能和属性列表可以在
			// http://sax.sourceforge.net/?selected=get-set 中找到。
			parser.setProperty(
					"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
					"http://www.w3.org/2001/XMLSchema");
			parser.setProperty(
					"http://java.sun.com/xml/jaxp/properties/schemaSource",
					"file:" + xsdFileName);
			// 创建一个SAXValidator校验工具，并设置校验工具的属性
			SAXValidator validator = new SAXValidator(parser.getXMLReader());
			// 设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
			validator.setErrorHandler(errorHandler);
			// 校验
			validator.validate(document);
			// 如果错误信息不为空，说明校验失败，打印错误信息
			if (errorHandler.getErrors().hasContent()) {
				sb = new StringBuffer();
				log.error("XML文件: " + xmlStr + " 通过XSD文件:" + xsdFileName
						+ "检验失败,错误信息如下：");
				Element element = errorHandler.getErrors();
				for (Iterator<Element> iter = element.elementIterator("error"); iter
						.hasNext();) {
					Element childElement = iter.next();
					String childMessage = childElement.getText();
					log.error(childMessage);
					sb.append(childMessage);
				}
//				return false;
			}

			log.info("XML文件: " + xmlStr + " 通过XSD文件:" + xsdFileName + "检验成功！");
			if (sb!=null) {
				return sb.length()<128?sb.toString():sb.substring(0, 128);
			}
			if (type == 3) {
				return checkBlackInfo(xmlStr);
			}
			if (type==2) {
				return checkBlockInfo(xmlStr);
			}
			log.info("xml数据校验通过!");
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("XML文件: " + xmlStr + " 通过XSD文件:" + xsdFileName
					+ "检验失败。\n原因： " + ex.getMessage());
			return "检验失败";
		}
	}

	/**
	 * 过滤指令
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	private static String checkBlockInfo(String xmlStr) throws Exception {

		Document doc = DocumentHelper.parseText(xmlStr);
		String domain = getSingleNodeText(doc, "/command/domain");
		String effectTime = getSingleNodeText(doc, "/command/time/effectTime");
		String expiredTime = getSingleNodeText(doc, "/command/time/expiredTime");
		String timeStamp = getSingleNodeText(doc, "/command/timeStamp");
		if (!ValidatorUtil.validateStrEmpty(domain)) {
			return "域名为空";
		}
		if (!checkDomain(domain)) {
			return "域名不符合规范";
		}
		if (!checkTime(effectTime)||!checkTime(expiredTime)||!checkTime(timeStamp)) {
			return "输入时间格式错误";
		}
		return null;
		
	}

	/**
	 * 3：黑名单网站列表指令
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	private static String checkBlackInfo(String xmlStr) throws Exception {
		Document doc = DocumentHelper.parseText(xmlStr);
		
		String contents = getSingleNodeText(doc, "/blacklist/contents");
		String timeStamp = getSingleNodeText(doc, "/blacklist/timeStamp");
		

		if (!ValidatorUtil.validateStrEmpty(contents)) {
			return "域名为空";
		}
		if (!checkDomain(contents)) {
			return "域名不符合规范";
		}
		if (!checkTime(timeStamp)) {
			return "输入时间格式错误";
		}
		return null;
		// map.put("commandId", commandId);
		// map.put("dnsId", dnsId);
		// map.put("operationType", operationType);
		// map.put("contents", contents);
		// map.put("timeStamp", timeStamp);
		// map.put("reason", reason);
		//
		// //2017年8月15日14:34:34,黑名单加入生效时间,过期时间
		// calendar.setTime(new Date());
		// calendar.add(Calendar.HOUR, -1);//获取一个小时前的时间
		// String effectTime = sdf.format(calendar.getTime());
		// calendar.add(Calendar.YEAR, 30);//获取30年之后的时间
		// String expiredTime = sdf.format(calendar.getTime());
		// calendar.clear();
		// map.put("effectTime", effectTime);
		// map.put("expiredTime", expiredTime);

	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 根据指令类型获取xsd文件名
	 * @author xujian
	 * @date 创建时间:2017-9-22 下午2:43:18
	 * @param type
	 * @return
	 * @version V1.0
	 */
	private static String getXsdFileName(Integer type) {
		if (type == null || type <= 0) {
			return null;
		}
		switch (type) {
		case 3:// 黑名单指令
			return InstructionCheckUtils.class.getClassLoader()
					.getResource(BLACK_COMMAND_XSD).getPath();
		case 2:// 过滤指令
			return InstructionCheckUtils.class.getClassLoader()
					.getResource(FILTER_COMMAND_XSD).getPath();
		default:
			return null;
		}
	}

	private static String getSingleNodeText(Document doc, String xpath) {
		Node selectSingleNode = doc.selectSingleNode(xpath);
		if (null == selectSingleNode) {
			return "";
		}
		return selectSingleNode.getText().trim();
	}

	private static URL getXsdFilePath(Integer type) {
		if (type == null || type <= 0) {
			return null;
		}
		switch (type) {
		case 1:// 黑名单指令
			return InstructionCheckUtils.class.getClassLoader().getResource(
					BLACK_COMMAND_XSD);
		default:
			return null;
		}
	}

	public static void main(String[] args) {
		// validateXMLByXSD2("<?xml version=\"1.0\" encoding=\"UTF-8\"?><blacklist><dnsId>1111</dnsId><commandId>12314</commandId><operationType>1daf</operationType><contents>baidu.com</contents><timeStamp>2017-09-20 18:20:31</timeStamp><reason>ceshi</reason></blacklist>");
		//String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><blacklist><dnsId>11ss11</dnsId><commandId>1231ea4</commandId><operationType>2</operationType><contents>baidu.com</contents><timeStamp>2017-09-20 18:20:55</timeStamp><reason>ceshi</reason></blacklist>";
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><command><commandId>2017060100453100002</commandId><type>1</type><domain>baidu.com</domain><urgency>3</urgency><action><reason>过滤</reason><log>1</log><report>0</report></action><time><effectTime>2017-09-20 18:20:30</effectTime><expiredTime>2017-09-20 18:10:31</expiredTime></time><range><dnsId>2017060100453100002</dnsId></range><privilege><owner>111</owner><visible>1</visible></privilege><operationType>0</operationType><timeStamp>2017-09-20 18:10:31</timeStamp></command>";
		// System.out.println(InstructionCheckUtils.class.getClassLoader().getResource());
		// checkByXsd(str, 1);
		// System.out.println(checkNumberOne("3"));
		System.out.println(validateCommandByXsd(str, 2));
		System.out.println(checkTime("2017-13-20 23:69:5"));
		System.out.println(checkDomain("www.baidu.com"));
		
	}
}
