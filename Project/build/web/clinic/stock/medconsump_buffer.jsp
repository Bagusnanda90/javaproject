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
<title>Print Medicine Consumption</title>
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
int month = FRMQueryString.requestInt(request, FrmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MONTH]+"_mn");
int year = FRMQueryString.requestInt(request, FrmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MONTH]+"_yr");

Date dtPeriod = new Date(year-1900,month-1,1);
//System.out.println("dtPeriod "+dtPeriod);
Hashtable listAll = new Hashtable();
Vector listConsumption = new Vector(1,1);

String strMonth = "";
if(dtPeriod != null){
	strMonth = YearMonth.getLongEngMonthName(dtPeriod.getMonth()+1)+" "+(dtPeriod.getYear()+1900);
}

listConsumption = PstMedicineConsumption.listMedConsumpt(dtPeriod);
if((listConsumption != null)&& (listConsumption.size() > 0)){					
	listAll.put(strMonth, listConsumption);		
}

if(session.getValue("MED_CONSUMPTION")!=null){
	session.removeValue("MED_CONSUMPTION");
}
session.putValue("MED_CONSUMPTION",listAll);
%>

<script language="JavaScript">
document.location="<%=printroot%>.report.clinic.MedConsumptionPdf";
</script>


