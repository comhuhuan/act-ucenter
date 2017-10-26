package com.act.ucenter.common.util;

import com.kpr.kui.runtime.Menu;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;

public class XmlUtil {
	public static String marshal(Object obj) throws JAXBException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			marshal(obj,out);
			out.flush();
			return new String(out.toByteArray(),"UTF-8");
		}finally{
			out.close();
		}
	}
	
	public static void marshal(Object obj,OutputStream out) throws JAXBException {
		assert obj!=null;
		JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());  
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();  
        // output pretty printed  
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
        jaxbMarshaller.marshal(obj, out);  
	}
	
	public static <T>T unmarshal(Class<T> beanClass,String xmlStr) throws Exception{
		ByteArrayInputStream in = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
		try {
			return unmarshal(beanClass, in);
		}finally {
			in.close();
		}
		
	}

    @SuppressWarnings("unchecked")
	public static <T>T unmarshal(Class<T> beanClass,InputStream in) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(beanClass);  
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
        return (T) jaxbUnmarshaller.unmarshal(in);  
	}
    
	public static String toXMLString(Document document) throws TransformerException, IOException{
		return toXMLString(document,"UTF-8");
	}
	
	public static String toXMLString(Document document,String encoding) throws TransformerException, IOException {
		if (document==null)
			return "";
		TransformerFactory   tf=TransformerFactory.newInstance();
		//tf.setAttribute("indent-number", new Integer(2)); 
		Transformer   transformer=tf.newTransformer();     
		DOMSource   source=new  DOMSource(document);  
		transformer.setOutputProperty(OutputKeys.ENCODING,encoding);     
		transformer.setOutputProperty(OutputKeys.INDENT,"yes");
		transformer.setParameter("format-pretty-print",
                                   new Boolean("true"));
		//if (transformer.getOutputProperties().contains(value))
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "2");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		OutputStreamWriter   pw=new   OutputStreamWriter(out,encoding);
		try {
			StreamResult   result=new   StreamResult(pw);     
			transformer.transform(source,result);
			pw.flush();
			out.flush();
		}finally {
			pw.close();
			out.close();
		}
		return new String(out.toByteArray(),encoding);
	}
	
	public static Document toXML(String xmlString) throws ParserConfigurationException, SAXException, IOException {
		return toXML(xmlString, "UTF-8");
	}
	
	public static Document toXML(String xmlString,String encoding) throws ParserConfigurationException, SAXException, IOException {
		if (xmlString==null||xmlString.length()==0)
			return null;
		DocumentBuilderFactory factory = null; 
		DocumentBuilder builder = null; 
		InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(encoding));
		
		Document doc = null;
		
		try {
			 factory=DocumentBuilderFactory.newInstance(); 
			 builder=factory.newDocumentBuilder(); 
			 doc = builder.parse(inputStream);
			 return doc;
		} finally{
			inputStream.close();
		}		
	}
	
    public static boolean isBaseClass(Class<?> c){
    	if (String.class==c||java.sql.Timestamp.class==c||java.sql.Date.class == c||java.util.Date.class == c
    			||short.class==c||Short.class==c||int.class==c||Integer.class==c||long.class==c||Long.class==c
    			||float.class==c||Float.class==c||double.class==c||Double.class==c
    			||BigDecimal.class==c||boolean.class==c||Boolean.class==c||byte[].class==c)
    		return true;
    	else
    		return false;
    }
	
	public static void main(String[] args) throws Exception{
		Menu root = new Menu();
		root.setId("root");
		root.setText("aaa");
		Menu top1 = new Menu();
		top1.setId("1");
		top1.setText("订单管理");
		root.getChildren().add(top1);
		Menu c11 = new Menu();
		c11.setId("11");
		c11.setText("新建订单");
		c11.setUrl("order/create");
		top1.getChildren().add(c11);
		Menu c12 = new Menu();
		c12.setId("12");
		c12.setText("订单提交");
		c12.setUrl("order/commit");
		top1.getChildren().add(c12);
		Menu c13 = new Menu();
		c13.setId("13");
		c13.setText("订单查询");
		c13.setUrl("order/query");
		top1.getChildren().add(c13);

		Menu top2 = new Menu();
		top2.setId("2");
		top2.setText("系统管理");
		root.getChildren().add(top2);
		Menu c21 = new Menu();
		c21.setId("21");
		c21.setText("用户管理");
		c21.setUrl("page/account/user");
		top2.getChildren().add(c21);
		Menu c22 = new Menu();
		c22.setId("22");
		c22.setText("用户组管理");
		c22.setUrl("page/account/usergroup");
		top2.getChildren().add(c22);		
		System.out.println(XmlUtil.marshal(root));
		
	}
    
}
