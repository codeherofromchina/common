package com.erui.comm.util.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;


/**
 * 邮件发送工具类
 * 
 * @date 2015年7月9日 下午1:50:51
 * @version 0.0.1
 */
public class SendEmailUtil {
	
	/**
	 * 发送邮件
	 * @date 2015年7月9日 下午1:51:11
	 * @throws 
	 * @return void 返回类型
	 */
    @SuppressWarnings("static-access")
	public static boolean sendMessage(
				String messageType,
				String smtpHost,//邮件服务器
				String from,//发件人邮箱
				String fromUserPassword,//发件人邮箱密码
				List<String> sendTo, //收件人邮箱
	            String subject, //邮件主题
	            String messageText //邮件内容
            ) {  
    	
			// 第一步：配置javax.mail.Session对象  
			Properties props = new Properties();  
			props.put("mail.smtp.host", smtpHost);  
			props.put("mail.smtp.starttls.enable","true");//使用 STARTTLS安全连接  
			//props.put("mail.smtp.port", "25"); //google使用465或587端口  
			props.put("mail.smtp.auth", "true"); // 使用验证  
			props.put("mail.debug", "true");  
			
			Session mailSession = null;
			try {
				mailSession = Session.getInstance(props,new MyAuthenticator(from,fromUserPassword));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("服务器连接失败");
				return false;
			}  
  
			// 第二步：编写消息  
			MimeMessage message = null;
			try {
				InternetAddress fromAddress = new InternetAddress(from); 
				
				//InternetAddress toAddress = new InternetAddress(sendTo);  
  
				Address[] toAddrArray = new InternetAddress[sendTo.size()];  
				for (int i = 0; i < sendTo.size(); i++) {
					toAddrArray[i] = new InternetAddress(sendTo.get(i));
				}
				
				message = new MimeMessage(mailSession);  
				message.setFrom(fromAddress);  
				message.addRecipients(RecipientType.TO, toAddrArray ); 
  
				message.setSentDate(Calendar.getInstance().getTime());  
				message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B")); //解决在Linux平台下,发送邮件标题乱码的问题 
				message.setContent(messageText, messageType);
			} catch (AddressException e) {
				e.printStackTrace();
				System.out.println("消息编写失败");
				return false;
			} catch (MessagingException e) {
				e.printStackTrace();
				System.out.println("消息编写失败");
				return false;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}  
  
			// 第三步：发送消息  
			try {
				Transport transport = mailSession.getTransport("smtp");  
				transport.connect(smtpHost,"新鸿儒", fromUserPassword);  
				transport.send(message, message.getRecipients(RecipientType.TO));  
				System.out.println("邮件发送成功");
				return true;
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
				System.out.println("邮件发送失败");
				return false;
			} catch (MessagingException e) {
				e.printStackTrace();
				System.out.println("邮件发送失败");
				return false;
			}
    }
    
    /**
     * 测试邮件的发送功能
     * 
     * @date 2015年7月9日 下午1:57:37
     * @throws 
     * @return void 返回类型
     */
    public static void main(String[] args) {  
    	List<String> list = new ArrayList<>();
    	list.add("javadevyan@163.com");
    	long start = System.currentTimeMillis();
    	sendMessage(
    			"text/html;charset=utf-8",
    			"smtp.163.com",
    			"xyan0303@163.com",
    			"www.mail163.com",
    			list, 
    			"邮件系统开发测试",  
    			"这是一封测试邮件"
    			);  
    	System.out.println(System.currentTimeMillis() - start);
    }  
}



class MyAuthenticator extends Authenticator{  
    String userName="";  
    String password="";  
    
    public MyAuthenticator(){ 
    	
    }  
    public MyAuthenticator(String userName,String password){  
        this.userName=userName;  
        this.password=password;  
    }  
     protected PasswordAuthentication getPasswordAuthentication(){     
        return new PasswordAuthentication(userName, password);     
     }   
}  
