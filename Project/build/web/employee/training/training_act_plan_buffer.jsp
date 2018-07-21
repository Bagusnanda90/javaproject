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

<% int  appObjCode = 0;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>


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
Date trnDate = FRMQueryString.requestDate(request,"date");

Hashtable hash = new Hashtable();

Vector listActivities = new Vector();
Vector listDepartment = PstDepartment.list(0,0,"",PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
Department department = new Department();
department.setDepartment("Generic Training");
String whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]+" = 0"+
					" AND "+PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DATE]+" = '"+Formater.formatDate(trnDate,"yyyy-MM-dd")+"'";
Vector listPlanning = PstTrainingActivityPlan.list(0,0,whereClause,"");
Vector temp = new Vector(1,1);
temp.add(department);
temp.add(listPlanning);
listActivities.add(temp);
for(int i=0;i<listDepartment.size();i++){
	temp = new Vector(1,1);
	department = (Department)listDepartment.get(i);
    whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]+" = "+department.getOID()+
				 " AND "+PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DATE]+" = '"+Formater.formatDate(trnDate,"yyyy-MM-dd")+"'";
	listPlanning = PstTrainingActivityPlan.list(0,0,whereClause,"");	
	temp.add(department);
	temp.add(listPlanning);
	listActivities.add(temp);
}

if(listActivities != null && listActivities.size()>0){
	hash.put(trnDate,listActivities);
}

if(session.getValue("TRAINING_ACTIVITY")!=null){
	session.removeValue("TRAINING_ACTIVITY");
}
session.putValue("TRAINING_ACTIVITY",hash);
%>

<script language="JavaScript">
document.location="<%=printroot%>.report.employee.TrainingActivityPlanPdf";
</script>


