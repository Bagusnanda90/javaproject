<%@ page language="java" %>

<%@ page import="java.util.*, com.dimata.util.Command" %>
<%@ page import="com.dimata.harisma.session.admin.*" %>
<%@ page import="com.dimata.harisma.utility.service.parser.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.util.blob.*" %>
<%@ page import="com.dimata.util.*" %>

<%@ page import="java.lang.System"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.ByteArrayInputStream"%>
<%@ page import="java.io.*"%>
<%@ page import="java.io.File"%>
<%@ page import="com.dimata.harisma.utility.odbc.*"%>

<%@ include file="main/javainit.jsp"%>
<%

/*TextLoader tl = new TextLoader();
String str = tl.getFileString("D:/user/edarmasusila/project/harisma/NIKKO/kde/CARDRAW.SDF");

out.println(str);

StringReader sr = new StringReader(str);
LineNumberReader ln = new LineNumberReader(sr);
Vector strBuffer = new Vector(1,1);
boolean stop = false;
int i=0;
while(!stop){
	String s = ln.readLine();
	
	out.println("<br>"+s);
	
	i = i+1;
	ln.setLineNumber(i);
	if(s==null){
		stop = true;
	}
	else{
		CardneticText ct = new CardneticText(s);
		out.println("<br>"+ct.getSwappingDate());
		out.println("<br>"+ct.getSwappingId());
		out.println("<br>"+ct.getSwappingType()+"<br>");
	}
	
}

out.println("<br>200604201526");
out.println("<br>"+Formater.formatDate("200604201526", "yyyyMMddhhmm"));
String stx = "20060420152600103441266290011";
out.println("<br>"+stx);
out.println("<br>"+stx.substring(0,12));
out.println("<br>"+stx.substring(12,24));
out.println("<br>"+stx.substring(24,25));


File filex = new File("D:/temp/test.txt");
try{
	String execBatFileName = "rename.bat";
	String cmd = "d:\n";
	cmd = cmd + "cd"+System.getProperty("file.separator")+"\n";
	cmd = cmd + "cd temp\n";
	cmd = cmd + "rename test.txt test-1.txt\n";
	
	FileOutputStream fff = new FileOutputStream("d:"+System.getProperty("file.separator")+"temp"+System.getProperty("file.separator")+execBatFileName);
	fff.write(cmd.getBytes());
	fff.flush();
	fff.close();

	ExecCommand exccomm = new ExecCommand();
	exccomm.runCommmand("d:"+System.getProperty("file.separator")+"temp"+System.getProperty("file.separator")+execBatFileName);
}
catch(Exception e){
	out.println(e.toString());
}

com.dimata.util.blob.File ff = new com.dimata.util.blob.File();
ff.renameFile("C", "Program Files/Cardnetic/Smart 2k/DATA", "CARDRAW-1.SDF", "CARDRAW.SDF");

//File filex = new File("D:\temp\test.txt");
//if(filex.isFile()){
	
//}

*/

TransferPresenceFromMdfText xx = new TransferPresenceFromMdfText();
out.println(xx.retrieveData());


%>
<html>
<head>
<title>text loader</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF" text="#000000">

</body>
</html>
