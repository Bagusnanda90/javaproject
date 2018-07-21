<%@ page language="java" %>
<%@ include file = "../../main/javainit.jsp" %>

<!--import harisma-->
<%@ page import="com.dimata.harisma.entity.clinic.*" %>
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
int month = FRMQueryString.requestInt(request, FrmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_PERIODE]+"_mn");
int year = FRMQueryString.requestInt(request, FrmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_PERIODE]+"_yr");
int person = FRMQueryString.requestInt(request, "persons");

Date dtPeriod = new Date(year-1900,month-1,1);

Vector listAll = new Vector(1,1);
Hashtable listData = new Hashtable();
Vector listAllExpense = new Vector(1,1);

String strMonth = "";
if(dtPeriod != null){
	strMonth = YearMonth.getLongEngMonthName(dtPeriod.getMonth()+1)+" "+(dtPeriod.getYear()+1900);
}

listAllExpense = PstExpRecapitulate.getMedicalExpense(dtPeriod);													
if((listAllExpense != null)&& (listAllExpense.size() > 0)){					
	listData.put(strMonth, listAllExpense);		
}
listAll.add(listData);
listAll.add(String.valueOf(person));

if(session.getValue("MED_EXPENSE")!=null){
	session.removeValue("MED_EXPENSE");
}
session.putValue("MED_EXPENSE",listAll);
%>

<script language="JavaScript">
document.location="<%=printroot%>MedExpensePdf";
</script>


