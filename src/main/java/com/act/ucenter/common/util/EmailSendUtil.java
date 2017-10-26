package com.act.ucenter.common.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;


public class EmailSendUtil {
	private static Logger logger = Logger.getLogger(EmailSendUtil.class);
	

	/**
	 * 
	* @Title: sendEmail 
	* @Description: 发送邮件 
	* @param @param title 邮件主题
	* @param @param contentHtml    html内容
	* @return void    返回类型 
	* @throws
	 */
	public static  void sendEmail(String contentHtml){
		
		String email_off = SysConfigUtil.loadConfig("email_off");
		String mailstmpauth = "true";
		String mailsmtphost = SysConfigUtil.loadConfig("email_from_host");
		String mailuser = SysConfigUtil.loadConfig("email_from_user");
		String mailpassword = SysConfigUtil.loadConfig("email_from_pwd");
		String toEmailAdress = SysConfigUtil.loadConfig("email_to_user");
		String title = SysConfigUtil.loadConfig("email_content_title");
		if(email_off.equals("N")){
			logger.info("邮件通知开关关闭状态，暂不发送邮件");
			return;
		}
		logger.info("开始发送邮件");
		logger.info("邮件发送的邮箱："+mailuser);
		logger.info("邮件接收的邮箱："+toEmailAdress);
		// 配置发送邮件的环境属性
	    final Properties props = new Properties();
	    
	    /* * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
	     * mail.user / mail.from*/
	     
	    // 表示SMTP发送邮件，需要进行身份验证
	    props.put("mail.smtp.auth", mailstmpauth);
	    props.put("mail.smtp.host", mailsmtphost);
	    // 发件人的账号
	    props.put("mail.user", mailuser);
	    // 访问SMTP服务时需要提供的密码
	    props.put("mail.password", mailpassword);

	    // 构建授权信息，用于进行SMTP进行身份验证
	    Authenticator authenticator = new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            // 用户名、密码
	            String userName = props.getProperty("mail.user");
	            String password = props.getProperty("mail.password");
	            return new PasswordAuthentication(userName, password);
	        }
	    };
	    // 使用环境属性和授权信息，创建邮件会话
	    Session mailSession = Session.getInstance(props, authenticator);
	    // 创建邮件消息
	    MimeMessage message = new MimeMessage(mailSession);
	    // 设置发件人
	    try {
			InternetAddress form = new InternetAddress(
			        props.getProperty("mail.user"));
			message.setFrom(form);

			// 设置收件人
			String[] tos=toEmailAdress.split(";");
			InternetAddress[] sendTo = new InternetAddress[tos.length];
			for (int i = 0; i < tos.length; i++){ 
		         sendTo[i] = new InternetAddress(tos[i]); 
		    }
			message.setRecipients(RecipientType.TO, sendTo);

			// 设置抄送
			/*InternetAddress cc = new InternetAddress("2213224047@qq.com");
			message.setRecipient(RecipientType.CC, cc);*/

			// 设置密送，其他的收件人不能看到密送的邮件地址
			/*InternetAddress bcc = new InternetAddress("aaaaa@163.com");
			message.setRecipient(RecipientType.CC, bcc);*/

			// 设置邮件标题
			message.setSubject(title);

			// 设置邮件的内容体
			message.setContent(contentHtml, "text/html;charset=UTF-8");

			// 发送邮件
			Transport.send(message);
			logger.info("邮件发送完毕");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
