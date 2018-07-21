/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.net.MailSender;
import java.util.Properties;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
public class email {
//SettingEmail settingEmail = new SettingEmail();
//Vector settingEmail=PstSettingEmail.list(0,100,"","");
//SettingEmail detailSettingEmail = (SettingEmail)settingEmail.get(0);
/*String d_email = "bookingonline@aplikasi-hotel.com",
     d_password = "b00k1ng0nl1ne",
     d_host = "mail.aplikasi-hotel.com",
     d_port = "465";*/

//String d_email = "aguspriskasuryana@gmail.com";
//String d_password = "hinduab11021993";
//String d_host = "smtp.gmail.com";
//String d_port = "465";
//String d_emailhairisma = "aguspriskasuryana@gmail.com";
//String d_emailBCC = "";
    /*String d_email = "dev2@dimata.com",
     d_password = "dev2123",
     d_host = "mail.dimata.com",
     d_port = "465";*/
    String d_email = PstSystemProperty.getValueByName("USER_EMAIL_END_CONTRACT");
    String d_password = PstSystemProperty.getValueByName("PWD_EMAIL_END_CONTRACT");
    String d_host = PstSystemProperty.getValueByName("HOST_EMAIL_END_CONTRACT");
    String d_port = PstSystemProperty.getValueByName("PORT_EMAIL_END_CONTRACT");
    String d_emailhairisma = PstSystemProperty.getValueByName("EMAIL_HAIRISMA_END_CONTRACT");
    String d_emailBCC = PstSystemProperty.getValueByName("EMAIL_BCC_END_CONTRACT");

    public email() {
    }

    public void sendEmail(String m_to, String m_subject, String m_text, boolean useSSL) {
        Properties props = new Properties();
        props.put("mail.smtp.user", d_email);
        props.put("mail.smtp.host", d_host);
        props.put("mail.smtp.port", d_port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", d_port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();
        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            MimeMessage msg = new MimeMessage(session);
//msg.setText(m_text);
            msg.setContent(m_text, "text/html; charset=utf-8");
            msg.setSubject(m_subject);
            msg.setFrom(new InternetAddress(d_email));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
            msg.addRecipients(Message.RecipientType.CC, (InternetAddress.parse(d_emailhairisma)));
            msg.addRecipients(Message.RecipientType.BCC, (InternetAddress.parse(d_emailBCC)));
            Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(d_email, d_password);
        }
    }

    private static void addAttachment(Multipart multipart, DataSource source, String sourcename) throws MessagingException {
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(sourcename);
        multipart.addBodyPart(messageBodyPart);
    }

    private static void addAttachment(Multipart multipart, String filename) throws MessagingException {
        DataSource source = new FileDataSource(filename);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
    }

    /**
     * @author Kartika 2015-09-10 , based on code of satria for leave app, modified to enable attch from on the file
     * sending
     * @param oidEmployeeLogin
     * @param listRec
     * @param recipientsCC
     * @param recipientsBCC
     * @param m_subject
     * @param m_text
     * @param attachement
     * @param dataName
     */
    public static String sendEmail( Vector<String> listRecTo, Vector<String> recipientsCC, Vector<String> recipientsBCC,
            String subject, String txtMessage, Vector<DataSource> attachment, Vector<String> dataName) {

        String harismaURL = PstSystemProperty.getValueByName("HARISMA_URL");

        if (harismaURL != null) {
            if (!harismaURL.endsWith("/login.jsp")) {
                harismaURL = harismaURL + "/login.jsp";
            }
        }
        //update by satrya 2013-11-04

        String harismaUrlInternet = PstSystemProperty.getValueByName("HARISMA_URL_AKSES_INTERNET").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) || PstSystemProperty.getValueByName("HARISMA_URL_AKSES_INTERNET").length() == 0 ? null : PstSystemProperty.getValueByName("HARISMA_URL_AKSES_INTERNET");
        if (harismaUrlInternet != null && harismaUrlInternet.length() > 0 && !harismaUrlInternet.equalsIgnoreCase("-")) {
            if (!harismaUrlInternet.endsWith("/login.jsp")) {
                harismaUrlInternet = harismaUrlInternet + "/login.jsp";
            }
        }

        String from = PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_FROM"); //"support@dimata.com"
        String host = PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_HOST"); //"beagle2.webappcabaret.net";
        int port = 25;

        try {
            port = Integer.parseInt(PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_PORT")); //25;
        } catch (Exception exc) {
        }

        boolean configEmailWithImage = false;
        try {
            configEmailWithImage = Boolean.parseBoolean(PstSystemProperty.getValueByName("CONFIGURASI_EMAIL_LEAVE_WITH_PICTURE")); //25;
        } catch (Exception exc) {
            configEmailWithImage = false;
        }
        String username = PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_USERNAME"); //"support@dimata.com";
        String password = PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_PASSWORD"); //"doxxxxx";
        boolean SSL = false;
        try {
            String sSSL = PstSystemProperty.getValueByName("EMAIL_SSL_SETTING");
            SSL = Boolean.parseBoolean(sSSL);
        } catch (Exception exc) {
            SSL = false;
        }

        if (configEmailWithImage) {

            if (listRecTo == null || listRecTo.size() < 1) {
                return ("No recipient defined ");
            }

            if (listRecTo != null && listRecTo.size() > 0) {
                try {// send email as a thread .. 
                    MailSender.postMailThread(listRecTo, recipientsCC,
                            recipientsBCC, subject, txtMessage, from,
                            host, port, username, password, SSL, attachment, dataName, configEmailWithImage);
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }

        }
        return "Done";
    }

}
