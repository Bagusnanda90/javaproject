<%@page contentType="text/html"%>

<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.report.leave.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>

<html>
<head>
<title>Leave Application Report Loading ...</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF" text="#000000">
<script language="JavaScript">
	window.focus();
</script>
<table width="100%" border="0">
  <tr> 
    <td align="center" height="100%"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0" width="98" height="8">
        <param name=movie value="../image/loader.swf">
        <param name=quality value=high>
        <embed src="../image/loader.swf" quality=high pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="98" height="8">
        </embed> 
      </object></td>
  </tr>
</table>
</body>
</html>


<% 
/*
String strDestName = FRMQueryString.requestString(request, "destname");
String strDestDepartment = FRMQueryString.requestString(request, "destdept");
String strApplicantName = FRMQueryString.requestString(request, "applname");
String strApplicantDepartment = FRMQueryString.requestString(request, "appldept");
String strDpOwningDate = FRMQueryString.requestString(request, "owning");
String strDpNote = FRMQueryString.requestString(request, "dpnote");
String strDpApprovedName = FRMQueryString.requestString(request, "appvname");
String strDpTakenApproved = FRMQueryString.requestString(request, "takenappv");
int intSubmissionDate = FRMQueryString.requestInt(request, "submissiondate");
int intSubmissionMonth = FRMQueryString.requestInt(request, "submissionmonth");
int intSubmissionYear = FRMQueryString.requestInt(request, "submissionyear");
Date submissionDate = new Date(intSubmissionYear-1900, intSubmissionMonth-1, intSubmissionDate);
String strSubmission = Formater.formatDate(submissionDate,"MMM dd, yyyy");
int intDpTakenDate = FRMQueryString.requestInt(request, "takendate");
int intDpTakenMonth = FRMQueryString.requestInt(request, "takenmonth");
int intDpTakenYear = FRMQueryString.requestInt(request, "takenyear");
Date dtDpTaken = new Date(intDpTakenYear-1900, intDpTakenMonth-1, intDpTakenDate);
String strDpTaken = Formater.formatDate(dtDpTaken,"MMM dd, yyyy");
*/
	
LeaveApplicationReport objLeaveApplicationReport = new LeaveApplicationReport();
objLeaveApplicationReport.setDateOfApplication("Jan 13, 2005");
objLeaveApplicationReport.setApplicatName("I Nyoman Bagus Nuradita");
objLeaveApplicationReport.setApplicantPayroll("51002947");
objLeaveApplicationReport.setApplicantDepartment("ACCOUNTING");
objLeaveApplicationReport.setApplicantPosition("EDP SUPERVISOR");
objLeaveApplicationReport.setAlStartDate("Jan 14, 2005");
objLeaveApplicationReport.setAlEndDate("Jan 14, 2005");
objLeaveApplicationReport.setAlAmount("1");
objLeaveApplicationReport.setLlStartDate("Jan 15, 2005");
objLeaveApplicationReport.setLlEndDate("Jan 16, 2005");
objLeaveApplicationReport.setLlAmount("2");
objLeaveApplicationReport.setMatStartDate("-");
objLeaveApplicationReport.setMatEndDate("-");
objLeaveApplicationReport.setMatAmount("0");
objLeaveApplicationReport.setSickStartDate("-");
objLeaveApplicationReport.setSickEndDate("-");
objLeaveApplicationReport.setSickAmount("0");
objLeaveApplicationReport.setUnpaidStartDate("-");
objLeaveApplicationReport.setUnpaidEndDate("-");
objLeaveApplicationReport.setUnpaidAmount("0");
objLeaveApplicationReport.setSpecialStartDate("-");
objLeaveApplicationReport.setSpecialEndDate("-");
objLeaveApplicationReport.setSpecialAmount("0");
objLeaveApplicationReport.setLeaveAmount("3");
objLeaveApplicationReport.setLeaveReason("Ada upacara keagamaan yaitu upcara a, upcara b, upacara c, upacara d dan upacara-upacara seterusnya");
objLeaveApplicationReport.setDepHeadApproval("I Gst Putu Ekawana");
objLeaveApplicationReport.setHrManApproval("Ni Putu Shinta Sutami, SH");
objLeaveApplicationReport.setDateRequestApproval("Jan 13, 2005");
objLeaveApplicationReport.setDateDeptHeadApproval("Jan 14, 2005");
objLeaveApplicationReport.setDateHrManApproval("Jan 14, 2005");
objLeaveApplicationReport.setAlEntitle("13");
objLeaveApplicationReport.setAlHasBeenTaken("2");
objLeaveApplicationReport.setAlSubTotal("11");
objLeaveApplicationReport.setAlToBeTaken("1");
objLeaveApplicationReport.setAlBalance("10");
objLeaveApplicationReport.setLlEntitle("26");
objLeaveApplicationReport.setLlHasBeenTaken("15");
objLeaveApplicationReport.setLlSubTotal("11");
objLeaveApplicationReport.setLlToBeTaken("2");
objLeaveApplicationReport.setLlBalance("9");

try
{
	session.putValue("LEAVE_APPLICATION",objLeaveApplicationReport);
}
catch(Exception e)
{
	System.out.println("Exc when put LEAVE_APPLICATION to session");	
}	
%>

<script language="JavaScript">
	document.location="<%=printroot%>.report.leave.LeaveApplicationPdf?gettime=<%=System.currentTimeMillis()%>";
</script>