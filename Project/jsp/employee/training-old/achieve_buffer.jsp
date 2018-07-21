<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<!--import harisma-->

<html>
<head>
<title>Print Special Achieve</title>
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
Vector listAll = new Vector();//SessSpecialAchievement.searchSpecialAchievement(srcSpecialAchievement,0,0);
long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");

String whereClause = PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_EMPLOYEE_ID]+"="+oidEmployee;
String orderClause = PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_DATE];
Vector vctAchieve = PstSpecialAchievement.list(0, 0, whereClause, orderClause);


/*
//for test only

Vector c = new Vector(1,1);
for(int i=0; i<vctAchieve.size(); i++){
	SpecialAchievement sp = (SpecialAchievement)vctAchieve.get(i);
	for(int x=0; x<20; x++){
		c.add(sp);
	}
}*/

	
Vector vctHeaderPrint = new Vector(1,1);
if(session.getValue("HEADER_CNT")!=null){
	vctHeaderPrint = (Vector)session.getValue("HEADER_CNT");
}

listAll.add(vctHeaderPrint);
listAll.add(vctAchieve);	
	
		
if(session.getValue("ACHIEVE")!=null){
	session.removeValue("ACHIEVE");
}

System.out.println("========================================================================");
System.out.println("listAll ===== "+listAll);
System.out.println("listAll ===== "+listAll.size());
System.out.println("listAll ===== "+listAll.size());
System.out.println("listAll ===== "+listAll.size());
System.out.println("vctAchieve ===== "+vctAchieve);



session.putValue("ACHIEVE",listAll);

%>

<script language="JavaScript">
	window.location="<%=printroot%>.report.employee.AchievementPdf?approot=<%=approot%>";
</script>


