<%@ page language="java" %>
<%@ include file = "../../main/javainit.jsp" %>

<!--import harisma-->
<%@ page import="com.dimata.harisma.entity.search.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.form.search.*" %>
<%@ page import="com.dimata.harisma.entity.clinic.*" %>
<%@ page import="com.dimata.harisma.session.clinic.*" %>

<!--import java-->
<%@ page import="java.util.*" %>

<!--import qdep-->
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.form.*" %> 


<html>
<head>
<title>Print Employee Visit</title>
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
SrcEmployeeVisit  srcEmployeeVisit = new SrcEmployeeVisit();
FrmSrcEmployeeVisit frmSrcEmployeeVisit = new FrmSrcEmployeeVisit(request, srcEmployeeVisit);
frmSrcEmployeeVisit.requestEntityObject(srcEmployeeVisit);
SessEmployeeVisit sessEmployeeVisit = new SessEmployeeVisit();

int vectSize = 0;
Hashtable listAll = new Hashtable();
Vector listEmpVisit = new Vector(1,1);

Date startDt = srcEmployeeVisit.getVisitDateFrom();
Date toDt = srcEmployeeVisit.getVisitDateTo();

String strMonth = "";
if(startDt != null && toDt != null){
/*	for(int i = ;i <=toDt.getMonth();i++){
		strMonth = 
		if(i < toDt.getMonth())
			strMonth = strMonth + ", ";	
	}
	for(int i = ;i <=toDt.getYear();i++){
		strMonth = strMonth +
	}*/
	strMonth = YearMonth.getLongEngMonthName(startDt.getMonth()+1)+ " "+(startDt.getYear()+1900)+ " - "+
			   YearMonth.getLongEngMonthName(toDt.getMonth()+1)+ " "+(toDt.getYear()+1900);
	
}
System.out.println("strMonth "+strMonth);
vectSize = sessEmployeeVisit.countEmployeeVisit(srcEmployeeVisit);
if(vectSize > 0){			
	listEmpVisit = sessEmployeeVisit.searchEmployeeVisit(srcEmployeeVisit,0, 0);		
	listAll.put(strMonth,listEmpVisit);			
}

if(session.getValue("EMP_VISIT")!=null){
	session.removeValue("EMP_VISIT");
}
session.putValue("EMP_VISIT",listAll);
%>

<script language="JavaScript">
document.location="<%=printroot%>.report.clinic.EmployeeVisitPdf";
</script>


