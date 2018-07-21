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
SrcGuestHandling  srcGuestHandling = new SrcGuestHandling();
FrmSrcGuestHandling frmSrcGuestHandling = new FrmSrcGuestHandling(request, srcGuestHandling);
frmSrcGuestHandling.requestEntityObject(srcGuestHandling);
SessGuestHandling sessGuestHandling = new SessGuestHandling();

int vectSize = 0;
Hashtable listAll = new Hashtable();
Vector listGuestHandling = new Vector(1,1);

Date startDt = srcGuestHandling.getDateFrom();
Date toDt = srcGuestHandling.getDateTo();

String strMonth = "";
if(startDt != null && toDt != null){
	/*for(int i = startDt.getMonth();i <=toDt.getMonth();i++){
		strMonth = strMonth + YearMonth.getLongEngMonthName(i+1);
		if(i < toDt.getMonth())
			strMonth = strMonth + ", ";	
	}
	for(int i = startDt.getYear();i <=toDt.getYear();i++){
		strMonth = strMonth + " "+(i+1900);
	}*/
	strMonth = YearMonth.getLongEngMonthName(startDt.getMonth()+1)+ " "+(startDt.getYear()+1900)+ " - "+
			   YearMonth.getLongEngMonthName(toDt.getMonth()+1)+ " "+(toDt.getYear()+1900);
}

vectSize = sessGuestHandling.countGuestHandling(srcGuestHandling);
if(vectSize > 0){
	listGuestHandling = sessGuestHandling.searchGuestHandling(srcGuestHandling,0, 0);				
	listAll.put(strMonth, listGuestHandling);		
}

if(session.getValue("GUEST_HANDLING")!=null){
	session.removeValue("GUEST_HANDLING");
}
session.putValue("GUEST_HANDLING",listAll);
%>

<script language="JavaScript">
document.location="<%=printroot%>.report.clinic.GuestHandlingPdf";
</script>


