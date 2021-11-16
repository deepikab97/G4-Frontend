package com.deccan.utils;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	
	@Value("${from}")
	private  String from;
	@Value("${password}")
	private   String password;
	@Value("${host}")
	private  String host;
	@Value("${port}")
	private  String port;
	
	public  String sendMail(String mailId,String subject, String body) throws  MessagingException{

		final String to = mailId;		//change email id 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
		message.setSubject(subject);
		message.setText(body);
		Transport.send(message);
		
		return "success";
	}
}
