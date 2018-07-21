<%@ page language="java" %>
<%@ include file = "../../main/javainit.jsp" %>
<!--package java-->
<%@ page import="java.util.*" %>
<!--package qdep-->
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.util.*" %>
<!--package harisma-->
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.*" %>

<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>


<html>
<head>
<title>Print Training Activity Plan</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF" text="#000000">
Loading ... 
<script language="JavaScript">
	window.focus();
</script>
</body>
</html>
<!-- JSP Block -->
<%
Date date = FRMQueryString.requestDate(request,"date");
String whereClause = "";

Hashtable hash = new Hashtable();
Vector listActivities = PstTrainingActivityActual.listActivity(date,0,0);
hash.put(date, listActivities);

if(session.getValue("TRAINING_ACTIVITY")!=null){
	session.removeValue("TRAINING_ACTIVITY");
}
session.putValue("TRAINING_ACTIVITY",hash);
%>

<script language="JavaScript">
document.location="<%=printroot%>.report.employee.TrainingActivityActualPdf";
</script>


