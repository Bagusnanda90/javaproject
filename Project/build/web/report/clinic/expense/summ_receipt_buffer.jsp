<%@ page language="java" %>
<%@ include file = "../../../main/javainit.jsp" %>

<!--import harisma-->
<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.clinic.*" %>
<%@ page import="com.dimata.harisma.form.clinic.*" %>
<%@ page import="com.dimata.harisma.session.clinic.*" %>
<%@ page import="com.dimata.harisma.report.clinic.*" %>
<!--import java-->
<%@ page import="java.util.*" %>

<!--import qdep-->
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.form.*" %> 


<html>
<head>
<title>Print Medical Expense</title>
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
int perMonth = FRMQueryString.requestInt(request, "periode_mn");
int perYear = FRMQueryString.requestInt(request, "periode_yr");

int dt = FRMQueryString.requestInt(request, "date_periode_dy");
int month = FRMQueryString.requestInt(request, "date_periode_mn");
int year = FRMQueryString.requestInt(request, "date_periode_yr");
Date dtPeriod = new Date(year-1900,month-1,dt);
System.out.println("start Date : "+dtPeriod); 

int dtend = FRMQueryString.requestInt(request, "date_periode_end_dy");
int monthend = FRMQueryString.requestInt(request, "date_periode_end_mn");
int yearend = FRMQueryString.requestInt(request, "date_periode_end_yr");
Date dtPeriodend = new Date(yearend-1900,monthend-1,dtend);
System.out.println("end date : "+dtPeriodend); 

long medicalType = FRMQueryString.requestLong(request, "med_type");
long department = FRMQueryString.requestLong(request, "dept_id");
// String perName = FRMQueryString.requestString(request, "periode_name");
String medTypeName = FRMQueryString.requestString(request, "med_type_name");
String deptName = FRMQueryString.requestString(request, "dept_name");

Vector vectSummReceipt = new Vector(1,1);
Vector listSummReceipt = SessReportSummReceipt.listSummaryReceipt(dtPeriod,dtPeriodend,medicalType,department);
vectSummReceipt.add(listSummReceipt);

String perName = Formater.formatDate(dtPeriodend,"MMMM yyyy")+
	" - ( "+Formater.formatDate(dtPeriod,"dd MMM yyyy")+" to "+Formater.formatDate(dtPeriodend,"dd MMM yyyy")+" )";
	
vectSummReceipt.add(perName);
vectSummReceipt.add(medTypeName);
vectSummReceipt.add(medTypeName);

if(session.getValue("SUMM_RECEIPT")!=null){
	session.removeValue("SUMM_RECEIPT");
}
session.putValue("SUMM_RECEIPT",vectSummReceipt);
%>

<script language="JavaScript">
document.location="<%=printroot%>.report.clinic.SummReceiptPdf";
</script>


