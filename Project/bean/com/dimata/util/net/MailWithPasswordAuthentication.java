/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.util.net;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailWithPasswordAuthentication {	
	private void sendMail(String mailTo, String mailFrom, String subject, String body, 
                              String smtp, String port, String username, String password, boolean SSL) throws MessagingException {
		Message message = new MimeMessage(getSession(smtp, port, username, password, SSL));

		message.addRecipient(RecipientType.TO, new InternetAddress(mailTo));
		message.addFrom(new InternetAddress[] { new InternetAddress(mailFrom) });

		message.setSubject(subject);
		message.setContent(body, "text/plain");
		Transport.send(message);
	}

	private Session getSession(String smtp, String port, String username, String password, boolean SSL) {
		Authenticator authenticator = new Authenticator(username, password);

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.auth", "true");

		properties.setProperty("mail.smtp.host", smtp);
		properties.setProperty("mail.smtp.port", port);
                
                if(SSL){
                    properties.put("mail.smtp.socketFactory.port", port);
                    properties.put("mail.smtp.socketFactory.class",
                                    "javax.net.ssl.SSLSocketFactory");
                }

		return Session.getInstance(properties, authenticator);
	}

	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator(String username, String password) {
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
}