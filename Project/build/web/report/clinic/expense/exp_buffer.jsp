<%@ page language="java" %>
<%@ include file = "../../../main/javainit.jsp" %>

<!--import harisma-->
<%@ page import="com.dimata.harisma.entity.clinic.*" %>
<%@ page import="com.dimata.harisma.session.clinic.*" %>
<%@ page import="com.dimata.harisma.form.clinic.*" %>
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

int person = FRMQueryString.requestInt(request, "persons");


Vector listAll = new Vector(1,1);
Hashtable listData = new Hashtable();
Vector listAllExpense = new Vector(1,1);

String strMonth = "";
if(dtPeriod != null){
	strMonth = YearMonth.getLongEngMonthName(dtPeriodend.getMonth()+1)+" "+(dtPeriodend.getYear()+1900);
	strMonth = strMonth + "    -   ( "+Formater.formatDate(dtPeriod,"dd MMMM yyyy")+" to "+Formater.formatDate(dtPeriodend,"dd MMMM yyyy")+" )";
}

listAllExpense = SessMedicalRecord.getMedicalExpense(dtPeriod,dtPeriodend);		
if((listAllExpense != null)&& (listAllExpense.size() > 0)){						
	listData.put(strMonth, listAllExpense);
	listAll.add(listData);		
}

listAll.add(String.valueOf(person));

if(session.getValue("MED_EXPENSE")!=null){
	session.removeValue("MED_EXPENSE");
}
session.putValue("MED_EXPENSE",listAll);
%>

<script language="JavaScript">
document.location="<%=printroot%>.report.clinic.MedExpensePdf";
</script>


