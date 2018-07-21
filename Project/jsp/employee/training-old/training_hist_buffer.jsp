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
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
<html>
<head>
<title>Print Training History</title>
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

String whereClause = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+"="+oidEmployee;
String orderClause = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE];
Vector vctTrHistory = PstTrainingHistory.list(0, 0, whereClause, orderClause);	

Vector vctHeaderPrint = new Vector(1,1);
if(session.getValue("HEADER_BUF")!=null){
	vctHeaderPrint = (Vector)session.getValue("HEADER_BUF");
}


//for test only
/*
Vector c = new Vector(1,1);
for(int i=0; i<vctTrHistory.size(); i++){
	TrainingHistory sp = (TrainingHistory)vctTrHistory.get(i);
	for(int x=0; x<20; x++){
		c.add(sp);
	}
}
listAll.add(c);
*/

listAll.add(vctHeaderPrint);
listAll.add(vctTrHistory);

System.out.println("listAll >>>>>>>>>>>>>>>>>>> "+listAll.size());

if(session.getValue("TRAINING_HISTORY")!=null){
	session.removeValue("TRAINING_HISTORY");
}
session.putValue("TRAINING_HISTORY",listAll);

%>

<script language="JavaScript">
document.location="<%=printroot%>.report.employee.TrainingHistoryPdf?approot=<%=approot%>";
</script>


