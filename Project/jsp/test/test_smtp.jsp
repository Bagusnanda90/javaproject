<%@ page language="java"%>

<%@ page import = "java.util.*"%>
<%@ page import = "java.text.*"%>
<%@ page import = "javax.mail.*"%>
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.Properties"%>
<%@ page import = "javax.mail.Authenticator"%>
<%@ page import = "javax.mail.Message"%>
<%@ page import = "javax.mail.MessagingException"%>
<%@ page import = "javax.mail.Session"%>
<%@ page import = "javax.mail.Transport"%>
<%@ page import = "javax.mail.internet.InternetAddress"%>
<%@ page import = "javax.mail.internet.MimeMessage"%>
<%@ page import = "javax.mail.internet.MimeMessage"%>
<%@ page import = "javax.mail.PasswordAuthentication"%>



<html>
<head>
<title>Test Mail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor="#FFFFFF" text="#000000">
    
 <% 
 try{
 String smtpHost="172.16.2.30";
 if(request.getParameter("smtpHost")!=null){
     smtpHost=request.getParameter("smtpHost");
 }
 
 String fromMail="hrsystem@ptkti.com"; 
 if(request.getParameter("fromMail")!=null){
     fromMail=request.getParameter("fromMail");
 }
 
 String fromName="Dimata Harisma System";
 String to ="eduardus@ptkti.com";
 if(request.getParameter("to")!=null){
     to=request.getParameter("to");
 }
 
 String subject="Test email from HR System";
 String message = "This is test email from HR System. Please inform Pak Ketut - Dimata, when you receive this email. Thanks b4";
     
 %>
 <%=(System.getProperty("java.home"))%><br>
 <input type="text" name="smtpHost" value ="<%=smtpHost%>"/>
 <input type="text" name="fromMail" value ="<%=fromMail%>"/>
 <input type="text" name="to" value ="<%=to%>"/>
 
 <%
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", smtpHost);
        Session ses = Session.getInstance(properties, null);

        MimeMessage mmsg = new MimeMessage(ses);

        // create address fromName
        InternetAddress fromAddr = null;
        if(fromMail == null || fromMail.length() == 0) {
	    fromAddr = new InternetAddress(fromName); 
        }else{
            fromAddr = new InternetAddress(fromMail, fromName);
        }
        mmsg.setFrom(fromAddr);

        // create address to
        InternetAddress toAddr = new InternetAddress(to);
        mmsg.setRecipient(Message.RecipientType.TO,toAddr);

        mmsg.setSubject(subject);
        mmsg.setText(message);
        Transport.send(mmsg);
               } catch(Exception exc) {
                   out.println(exc);
               }
 %>
</body>

</html>

