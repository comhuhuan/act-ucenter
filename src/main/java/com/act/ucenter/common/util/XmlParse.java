package com.act.ucenter.common.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.net.URL;

public class XmlParse {

	/**
	 * 
	 * @name 中文名称
	 * @Description: 通过xsd上报信息
	 * @author xujian
	 * @date 创建时间:2017-6-6 上午10:40:57
	 * @param xmlPath xml路径
	 * @param xsdPath xsd路径
	 * @return
	 * @version V1.0
	 */
	public static boolean validateXMLByXSD(String xmlPath, String xsdPath){
		//建立schema工厂
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		//建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
		Validator validator ;
		try {
			URL url = XmlParse.class.getClassLoader().getResource(xsdPath);
			Schema schema = schemaFactory.newSchema(url);
			validator = schema.newValidator();
			Source source = new StreamSource(xmlPath);
			validator.validate(source);
		} catch (Exception e) {
			//如果XML非格式验证异常，则不验证
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static Document getDocument(String filepath) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			File f = new File(filepath);
			document = reader.read(f);
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
			System.out.println("读取xml文件发生异常，请检查文件" + filepath + "是否存在！");
			e.printStackTrace();
		}
		return document;
	}


}
