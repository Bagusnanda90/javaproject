
<% 
/* 
 * Page Name  		:  leave_edit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_GENERAL   , AppObjInfo.OBJ_SG_LEAVE_DP   	); %>
<% //int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_SPECIFIC   , AppObjInfo.OBJ_SS_LEAVE_DP   	); %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
<% //int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_LEAVE_MANAGEMENT   	); %>

<%//@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/

boolean privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));

boolean privStartSpec=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_VIEW));
boolean privAddSpec=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_ADD));
boolean privUpdateSpec=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_UPDATE));
boolean privDeleteSpec=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_DELETE));

//out.println("privStart : "+privStart+", privAdd : "+privAdd+", privUpdate : "+privUpdate+", privDelete : "+privDelete);	
%>

<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request,"start");
long oidLeaveApplication = FRMQueryString.requestLong(request, "hidden_leave_application_id");
boolean depHeadAuthorize = FRMQueryString.requestBoolean(request, "hidden_dep_head_authorize");
boolean hrManAuthorize = FRMQueryString.requestBoolean(request, "hidden_hr_man_authorize");
long depHeadApprovalId = FRMQueryString.requestLong(request, "dep_head_id");
long hrManApprovalId = FRMQueryString.requestLong(request, "hr_man_id");

int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";

ControlLine ctrLine = new ControlLine();
CtrlLeaveApplication ctrlLeaveApplication = new CtrlLeaveApplication(request);
iErrCode = ctrlLeaveApplication.action(iCommand, oidLeaveApplication,null,approot);

errMsg = ctrlLeaveApplication.getMessage();
FrmLeaveApplication frmLeaveApplication = ctrlLeaveApplication.getForm();
LeaveApplication objLeaveApplication = ctrlLeaveApplication.getLeaveApplication();   
oidLeaveApplication = objLeaveApplication.getOID();



String strEmpNum = "";
String strEmpFullName = "";
String strEmpPosition = "";
String strEmpDepartment = "";

String strDpDate = "";
String strDpNotes = "";

Employee objEmployee = new Employee();
if(iCommand != Command.ADD)
{
	// get employee and deparment data
	try
	{	
		objEmployee = PstEmployee.fetchExc(objLeaveApplication.getEmployeeId());
	
		// get empnum and fullname
		strEmpNum = objEmployee.getEmployeeNum();
		strEmpFullName = objEmployee.getFullName();
		
		// get deparment
		Department objDepartment = new Department();
		objDepartment = PstDepartment.fetchExc(objEmployee.getDepartmentId());
		strEmpDepartment = objDepartment.getDepartment();
		
		// get position
		Position objPosition = new Position();
		objPosition = PstPosition.fetchExc(objEmployee.getPositionId());
		strEmpPosition = objPosition.getPosition();		
	}
	catch(Exception e)
	{
		System.out.println("Exc when fetch employee and deparment data : " + e.toString());
	}	
}


// mencari stock utk masing masing AL dan LL
Vector vectListAL = new Vector(1,1);
Vector vectListLL = new Vector(1,1);
if(oidLeaveApplication!=0)
{
	SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();
	srcLeaveManagement.setEmpNum(strEmpNum);
	srcLeaveManagement.setEmpName(strEmpFullName);
	srcLeaveManagement.setEmpDeptId(objEmployee.getDepartmentId());
	
	vectListAL = SessLeaveManagement.listSummaryAlStock(srcLeaveManagement, 0, 0);	
	vectListLL = SessLeaveManagement.listSummaryLlStock(srcLeaveManagement, 0, 0);
}

System.out.println("vectListAL : "+vectListAL.size());
System.out.println("vectListLL : "+vectListLL.size());
AlStockManagement alStockManagement = new AlStockManagement();
if(vectListAL!=null && vectListAL.size()>0)
{
	Vector vectTemp = (Vector) vectListAL.get(0);
	alStockManagement = (AlStockManagement) vectTemp.get(0);
}

LLStockManagement llStockManagement = new LLStockManagement();
if(vectListLL!=null && vectListLL.size()>0)
{
	Vector vectTemp = (Vector) vectListLL.get(0);
	llStockManagement = (LLStockManagement) vectTemp.get(0);  
}
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Leave Application</title>
<script language="JavaScript">
<!--
function cmdPrint()
{ 
		var linkPage  = "leave_application_buffer.jsp"; 
		window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 					
}

function cmdCancel()
{
	document.frm_leave_application.command.value="<%=Command.CANCEL%>";
	document.frm_leave_application.action="leave_application_edit.jsp";
	document.frm_leave_application.submit();
} 

function cmdEdit(oid)
{ 
	document.frm_leave_application.command.value="<%=Command.EDIT%>";
	document.frm_leave_application.action="leave_application_edit.jsp";
	document.frm_leave_application.submit(); 
} 

function cmdSave()
{
	document.frm_leave_application.command.value="<%=Command.SAVE%>"; 
	document.frm_leave_application.action="leave_application_edit.jsp";
	document.frm_leave_application.submit();
}

function cmdAsk(oid)
{
	document.frm_leave_application.command.value="<%=Command.ASK%>"; 
	document.frm_leave_application.action="leave_application_edit.jsp";
	document.frm_leave_application.submit();
} 

function cmdConfirmDelete(oid)
{
	document.frm_leave_application.command.value="<%=Command.DELETE%>";
	document.frm_leave_application.action="leave_application_edit.jsp"; 
	document.frm_leave_application.submit();
}  

function cmdBack()
{
	document.frm_leave_application.command.value="<%=Command.FIRST%>"; 
	document.frm_leave_application.action="leave_application_list.jsp";
	document.frm_leave_application.submit();
}

function searchEmployee()
{
	window.open("leave_application_empsearch.jsp?emp_fullname="+document.frm_leave_application.EMP_NAME.value+"&EMP_NUMBER="+document.frm_leave_application.EMP_NUM.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
}

function searchDp()
{
	var emp_oid = document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>.value;
	if(emp_oid != 0)
	{	
		window.open("leave_application_dpstock.jsp?emp_oid=" + emp_oid, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no, scrolbars=yes");
	}
	else
	{
		alert('Please select an employee first ...');
	}
}

function checkApproval()
{
	var empLoggedIn = "<%=emplx.getOID()%>";
	var empApprovalSelected = document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL]%>.value;
	if(empLoggedIn != 0)
	{
		if(empApprovalSelected != 0)
		{
			if(empLoggedIn != empApprovalSelected)
			{
				document.frm_leave_application.command.value="<%=Command.LIST%>"; 
				document.frm_leave_application.action="leave_application_app_login.jsp";
				document.frm_leave_application.submit();  		
			}
		}
		else
		{
			alert('Please choose an authorized Department Head to approve this Leave Application ...');    					
		}
	}
	else
	{
		alert('You should login into Harisma as an authorized user ...'); 
		document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL]%>.value = "0";   		
	}  
}


function checkHrManApproval()
{
	var empLoggedIn = "<%=emplx.getOID()%>";
	var empApprovalSelected = document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL]%>.value;
	if(empLoggedIn != 0)
	{
		if(empApprovalSelected != 0)
		{
			if(empLoggedIn != empApprovalSelected)
			{
				document.frm_leave_application.command.value="<%=Command.LIST%>"; 
				document.frm_leave_application.action="leave_application_app_hrman_login.jsp";
				document.frm_leave_application.submit();  		
			}
		}
		else
		{
			alert('Please choose an authorized HRD Manager to approve this Leave Application ...');    					
		}
	}
	else
	{
		alert('You should login into Harisma as an authorized user ...'); 
		document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL]%>.value = "0";   		
	}  
}

function MM_swapImgRestore() { //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
	var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
	var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Leave &gt; Leave Application<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="tablecolor"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_leave_application" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">  
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_leave_application_id" value="<%=oidLeaveApplication%>">
                                      <input type="hidden" name="hidden_dep_head_authorize" value="<%=depHeadAuthorize%>">									  									  
                                      <input type="hidden" name="hidden_hr_man_authorize" value="<%=hrManAuthorize%>">									  									  									  									  
                                      <input type="hidden" name="dep_head_id" value="<%=depHeadApprovalId%>">									  									  									  
                                      <input type="hidden" name="hr_man_id" value="<%=hrManApprovalId%>">									  									  									  									  
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%" valign="top">										  
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                              <tr> 
                                                <td align="center"><b><font size="4">LEAVE 
                                                  APPLICATION FORM</font></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="center"> 
                                                  <div align="center"><b></b></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0">
                                                    <tr> 
                                                      <td width="11%">&nbsp;</td>
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="42%">&nbsp;</td>
                                                      <td width="13%">Date of 
                                                        Application</td>
                                                      <td width="2%">:</td>
                                                      <td width="30%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SUBMISSION_DATE], (objLeaveApplication.getSubmissionDate()==null ? new Date() : objLeaveApplication.getSubmissionDate()), "formElemen", 1, -5)%></td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="11%">Name</td>
                                                      <td width="2%">:</td>
                                                      <td width="42%"> 
                                                        <input type="text" name="EMP_NAME" size="46" value="<%=strEmpFullName%>">  
                                                      </td>
                                                      <td width="13%">Position</td>
                                                      <td width="2%">:</td>
                                                      <td width="30%"> 
                                                        <input type="text" name="EMP_POSITION" size="40" style="background-color:#F5F5F5" readonly value="<%=strEmpPosition%>">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="11%">Employee 
                                                        Num </td>
                                                      <td width="2%">:</td>
                                                      <td width="42%"> 
                                                        <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>" value="<%=objLeaveApplication.getEmployeeId()%>">
                                                        <input type="text" name="EMP_NUM" size="20" value="<%=strEmpNum%>">
                                                        <input type="button" name="btnSchEmp" value="Search Employee" onClick="javascript:searchEmployee()">
                                                        * </td>
                                                      <td width="13%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                      <td width="2%">:</td>
                                                      <td width="30%"><b> 
                                                        <input type="text" name="EMP_DEPARTMENT" size="40" value="<%=strEmpDepartment%>" style="background-color:#F5F5F5" readonly>
                                                        </b></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" class="tablecolor" cellpadding="1" cellspacing="1">
                                                    <tr> 
                                                      <td> 
                                                        <table width="100%" border="0"  bgcolor="#F9FCFF">
                                                          <tr> 
                                                            <td colspan="8">Leave 
                                                              applied for : <i>Please 
                                                              tick (v) the box 
                                                              as appropriate : 
                                                              </i> </td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="10%" align="right"> 
                                                              <input type="checkbox" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_CHECK_AL]%>" <%=(objLeaveApplication.isCheckAL() ? "checked" : "")%> value="1">
                                                            </td>
                                                            <td width="20%"> Annual 
                                                              Leave</td>
                                                            <td width="6%"><i>from</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_AL_TAKEN_START_DATE], (objLeaveApplication.getAlTakenStartDate()==null ? new Date() : objLeaveApplication.getAlTakenStartDate()), "formElemen", 1, -5)%></td>
                                                            <td width="4%"><i>to</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_AL_TAKEN_END_DATE], (objLeaveApplication.getAlTakenEndDate()==null ? new Date() : objLeaveApplication.getAlTakenEndDate()), "formElemen", 1, -5)%></td>
                                                            <td width="5%" align="center">=</td>
                                                            <td width="15%" align="right"><i> 
                                                              <input type="text" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_AL_TAKEN_AMOUNT]%>" size="10" style="text-align:right" value="<%=objLeaveApplication.getAlTakenAmount()%>">
                                                              days</i></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="10%" align="right"> 
                                                              <input type="checkbox" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_CHECK_LL]%>" <%=(objLeaveApplication.isCheckLL() ? "checked" : "")%> value="1">
                                                            </td>
                                                            <td width="20%">Long 
                                                              Leave</td>
                                                            <td width="6%"><i>from</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LL_TAKEN_START_DATE], (objLeaveApplication.getLlTakenStartDate()==null ? new Date() : objLeaveApplication.getLlTakenStartDate()), "formElemen", 1, -5)%></td>
                                                            <td width="4%"><i>to</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LL_TAKEN_END_DATE], (objLeaveApplication.getLlTakenEndDate()==null ? new Date() : objLeaveApplication.getLlTakenEndDate()), "formElemen", 1, -5)%></td>
                                                            <td width="5%" align="center">=</td>
                                                            <td width="15%" align="right"><i> 
                                                              <input type="text" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LL_TAKEN_AMOUNT]%>" size="10" style="text-align:right" value="<%=objLeaveApplication.getLlTakenAmount()%>">
                                                              days</i></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="10%" align="right"> 
                                                              <input type="checkbox" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_CHECK_MATERNITY]%>" <%=(objLeaveApplication.isCheckMaternity() ? "checked" : "")%> value="1">
                                                            </td>
                                                            <td width="20%">Maternity 
                                                              Leave</td>
                                                            <td width="6%"><i>from</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_MAT_TAKEN_START_DATE], (objLeaveApplication.getMatTakenStartDate()==null ? new Date() : objLeaveApplication.getMatTakenStartDate()), "formElemen", 1, -5)%></td>
                                                            <td width="4%"><i>to</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_MAT_TAKEN_END_DATE], (objLeaveApplication.getMatTakenEndDate()==null ? new Date() : objLeaveApplication.getMatTakenEndDate()), "formElemen", 1, -5)%></td>
                                                            <td width="5%" align="center">=</td>
                                                            <td width="15%" align="right"><i> 
                                                              <input type="text" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_MAT_TAKEN_AMOUNT]%>" size="10" style="text-align:right" value="<%=objLeaveApplication.getMatTakenAmount()%>">
                                                              days</i></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="10%" align="right"> 
                                                              <input type="checkbox" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_CHECK_SICK]%>" <%=(objLeaveApplication.isCheckSick() ? "checked" : "")%> value="1">
                                                            </td>
                                                            <td width="20%">Sick 
                                                              Leave</td>
                                                            <td width="6%"><i>from</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SICK_TAKEN_START_DATE], (objLeaveApplication.getSickTakenStartDate()==null ? new Date() : objLeaveApplication.getSickTakenStartDate()), "formElemen", 1, -5)%></td>
                                                            <td width="4%"><i>to</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SICK_TAKEN_END_DATE], (objLeaveApplication.getSickTakenEndDate()==null ? new Date() : objLeaveApplication.getMatTakenEndDate()), "formElemen", 1, -5)%></td>
                                                            <td width="5%" align="center">=</td>
                                                            <td width="15%" align="right"><i> 
                                                              <input type="text" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SICK_TAKEN_AMOUNT]%>" size="10" style="text-align:right" value="<%=objLeaveApplication.getSickTakenAmount()%>">
                                                              days</i></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="10%" align="right"> 
                                                              <input type="checkbox" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_CHECK_UNPAID]%>" <%=(objLeaveApplication.isCheckUnpaid() ? "checked" : "")%> value="1">
                                                            </td>
                                                            <td width="20%">Unpaid 
                                                              Leave</td>
                                                            <td width="6%"><i>from</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_UN_TAKEN_START_DATE], (objLeaveApplication.getUnTakenStartDate()==null ? new Date() : objLeaveApplication.getUnTakenStartDate()), "formElemen", 1, -5)%></td>
                                                            <td width="4%"><i>to</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_UN_TAKEN_END_DATE], (objLeaveApplication.getUnTakenEndDate()==null ? new Date() : objLeaveApplication.getUnTakenEndDate()), "formElemen", 1, -5)%></td>
                                                            <td width="5%" align="center">=</td>
                                                            <td width="15%" align="right"><i> 
                                                              <input type="text" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_UN_TAKEN_AMOUNT]%>" size="10" style="text-align:right" value="<%=objLeaveApplication.getUnTakenAmount()%>">
                                                              days</i></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="10%" align="right"> 
                                                              <input type="checkbox" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_CHECK_SPECIAL]%>" <%=(objLeaveApplication.isCheckSpecial() ? "checked" : "")%> value="1">
                                                            </td>
                                                            <td width="20%">Special/Other 
                                                              Leave</td>
                                                            <td width="6%"><i>from</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SPE_TAKEN_START_DATE], (objLeaveApplication.getSpeTakenStartDate()==null ? new Date() : objLeaveApplication.getSpeTakenStartDate()), "formElemen", 1, -5)%></td>
                                                            <td width="4%"><i>to</i></td>
                                                            <td width="20%"> <%=ControlDate.drawDate(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SPE_TAKEN_END_DATE], (objLeaveApplication.getSpeTakenEndDate()==null ? new Date() : objLeaveApplication.getSpeTakenEndDate()), "formElemen", 1, -5)%></td>
                                                            <td width="5%" align="center">=</td>
                                                            <td width="15%" align="right"><i> 
                                                              <input type="text" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_SPE_TAKEN_AMOUNT]%>" size="10" style="text-align:right" value="<%=objLeaveApplication.getSpeTakenAmount()%>">
                                                              days</i></td>
                                                          </tr>
                                                          <tr> 
                                                            <td colspan="5" align="right">&nbsp;</td>
                                                            <td width="20%">Total 
                                                              Leave</td>
                                                            <td width="5%" align="center">=</td>
                                                            <td width="15%" align="right"><i> 
                                                              <input type="text" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_TOTAL_TAKEN_AMOUNT]%>" size="10" style="text-align:right" value="<%=objLeaveApplication.getTotalTakenAmount()%>">
                                                              days</i></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0">
                                                    <tr> 
                                                      <td width="50%">Reason :</td>
                                                      <td width="50%" align="center"> 
                                                        <%
													  if(oidLeaveApplication > 0)
													  {
													  %>
                                                        Leave Statistic 
                                                        <%
													  }
													  else
													  {
													  %>
                                                        &nbsp; 
                                                        <%
													  }
													  %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="50%" valign="top"> 
                                                        <textarea name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_REASON]%>" cols="45" class="formElemen" rows="5"><%=objLeaveApplication.getLeaveReason()%></textarea>
                                                      </td>
                                                      <td width="50%"> 
                                                        <%
													  if(oidLeaveApplication > 0)
													  {
													  %>
                                                        <table width="100%" border="0" class="tablecolor" cellpadding="1" cellspacing="1">
                                                          <tr> 
                                                            <td> 
                                                              <table width="100%" border="0" bgcolor="#F9FCFF">
                                                                <tr> 
                                                                  <td width="20%">&nbsp;</td>
                                                                  <td width="4%">&nbsp;</td>
                                                                  <td width="20%" align="center">Annual 
                                                                    Leave</td>
                                                                  <td width="2%" align="center">&nbsp;</td>
                                                                  <td width="20%" align="center">Long 
                                                                    Leave</td>
                                                                  <td width="4%" align="right">&nbsp;</td>
                                                                  <td width="30%">&nbsp;</td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="20%">Total 
                                                                    Entitle</td>
                                                                  <td width="4%" align="center">:</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield" size="10" style="text-align:right" value="<%=alStockManagement.getAlQty()%>">
                                                                  </td>
                                                                  <td width="2%" align="center">+</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield2" size="10" style="text-align:right" value="<%=llStockManagement.getLLQty()%>">
                                                                  </td>
                                                                  <td width="4%" align="right">=</td>
                                                                  <td width="30%" align="right"><i> 
                                                                    <input type="text" name="textfield22" size="10" style="text-align:right">
                                                                    days</i></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="20%">Has 
                                                                    been taken</td>
                                                                  <td width="4%" align="center">:</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield3" size="10" style="text-align:right" value="<%=alStockManagement.getQtyUsed()%>">
                                                                  </td>
                                                                  <td width="2%" align="center">+</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield7" size="10" style="text-align:right" value="<%=llStockManagement.getLLQty()%>">
                                                                  </td>
                                                                  <td width="4%" align="right">=</td>
                                                                  <td width="30%" align="right"><i> 
                                                                    <input type="text" name="textfield23" size="10" style="text-align:right"">
                                                                    days</i></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="20%">Sub 
                                                                    Total</td>
                                                                  <td width="4%" align="center">:</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield4" size="10" style="text-align:right" value="<%=alStockManagement.getAlQty()%>">
                                                                  </td>
                                                                  <td width="2%" align="center">+</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield8" size="10" style="text-align:right" value="<%=llStockManagement.getLLQty()%>">
                                                                  </td>
                                                                  <td width="4%" align="right">=</td>
                                                                  <td width="30%" align="right"><i> 
                                                                    <input type="text" name="textfield24" size="10" style="text-align:right">
                                                                    days</i></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="20%">To 
                                                                    be taken</td>
                                                                  <td width="4%" align="center">:</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield5" size="10" style="text-align:right" value="<%=alStockManagement.getAlQty()%>">
                                                                  </td>
                                                                  <td width="2%" align="center">+</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield9" size="10" style="text-align:right" value="<%=llStockManagement.getLLQty()%>">
                                                                  </td>
                                                                  <td width="4%" align="right">=</td>
                                                                  <td width="30%" align="right"><i> 
                                                                    <input type="text" name="textfield25" size="10" style="text-align:right">
                                                                    days</i></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="20%">Balance</td>
                                                                  <td width="4%" align="center">:</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield6" size="10" style="text-align:right" value="<%=alStockManagement.getQtyResidue()%>">
                                                                  </td>
                                                                  <td width="2%" align="center">+</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield10" size="10" style="text-align:right" value="<%=llStockManagement.getLLQty()%>">
                                                                  </td>
                                                                  <td width="4%" align="right">=</td>
                                                                  <td width="30%" align="right"><i> 
                                                                    <input type="text" name="textfield26" size="10" style="text-align:right">
                                                                    days</i></td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                        </table>
                                                        <%
														}
														else
														{
														%>
                                                        &nbsp; 
                                                        <%
														}
														%>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td valign="top">
												  
												  <%												  
												  if(oidLeaveApplication!=0)
												  {
												  %>													   
                                                  <table width="100%" border="0">
                                                    <tr align="center"> 
                                                      <td width="33%">Requested 
                                                        by, </td>
                                                      <td width="34%">Approved 
                                                        by, </td>
                                                      <td width="33%">
													  <%
													  if( objLeaveApplication.getDepHeadApproval()!=0 )
													  {
													  %>
													  Approved by, 
													  <%
													  }
													  else
													  {
													  %>
													  &nbsp;
													  <%
													  }
													  %>
													  </td>
                                                    </tr>
                                                    <tr align="center"> 
                                                      <td width="33%">&nbsp;</td>
                                                      <td width="34%">&nbsp;</td>
                                                      <td width="33%">&nbsp;</td>
                                                    </tr>
                                                    <tr align="center"> 
                                                      <td width="33%"> 
                                                        <input type="text" name="REQUESTER" style="background-color:#F5F5F5" readonly size="30" value="<%=strEmpFullName%>">
                                                      </td>
                                                      <td width="34%">
                                                      <%												  
													  Vector divHeadKey = new Vector(1,1);
													  Vector divHeadValue = new Vector(1,1);
													  
													  divHeadKey.add("Select Department Head");
													  divHeadValue.add("0");
													  
													  String selectedApproval = ""+objLeaveApplication.getDepHeadApproval();
													  if(depHeadAuthorize)
													  {
														  selectedApproval = ""+depHeadApprovalId;
													  }
													  
													  Vector listDivHead = SessEmployee.listEmployeeByPositionLevel(objEmployee, PstPosition.LEVEL_MANAGER);
													  for(int i=0; i<listDivHead.size(); i++)
													  {
														  Employee objEmp = (Employee)listDivHead.get(i);
														  
														  if(objEmployee.getOID() != objEmp.getOID())
														  {
															  divHeadKey.add(objEmp.getFullName());
															  divHeadValue.add(""+objEmp.getOID());
														  }
													  }
													  String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval()\"";  
													  out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_DEP_HEAD_APPROVAL], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                      %>													  
													  </td>
                                                      <td width="33%">
													  <%
													  if( objLeaveApplication.getDepHeadApproval()!=0 )
													  {
														  Vector hrManKey = new Vector(1,1);
														  Vector hrManValue = new Vector(1,1);
														  
														  hrManKey.add("Select HR Manager");
														  hrManValue.add("0");
														  
														  String selectedHrMan = ""+objLeaveApplication.getHrManApproval();
														  if(hrManAuthorize)
														  {
															  selectedHrMan = ""+hrManApprovalId;
														  }
														  
														  long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
														  Vector vectPositionLvl = new Vector(1,1);
														  vectPositionLvl.add(""+PstPosition.LEVEL_MANAGER);
														  
														  Vector listHrMan = SessEmployee.listEmployeeByPositionLevel(hrdDepartmentOid, vectPositionLvl);
														  for(int i=0; i<listHrMan.size(); i++)
														  {
															  Employee objEmp = (Employee)listHrMan.get(i);
															  
															  if(objEmployee.getOID() != objEmp.getOID())
															  {
																  hrManKey.add(objEmp.getFullName());
																  hrManValue.add(""+objEmp.getOID());
															  }
														  }
														  String strAttributeHr = "class=\"formElemen\" onChange=\"javascript:checkHrManApproval()\"";
														  out.println(ControlCombo.draw(FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_HR_MAN_APPROVAL], null, selectedHrMan, hrManValue, hrManKey, strAttributeHr));														  
													  }
													  else
													  {
														 out.println("&nbsp;");
													  } 
                                                      %>													  													  
													  </td>
                                                    </tr>													
                                                  </table>
												  <%
												  }
												  %>
												  
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                <%
												ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												ctrLine.setTableWidth("80");
												ctrLine.setCommandStyle("buttonlink");												
												
												String scomDel = "javascript:cmdAsk('"+oidLeaveApplication+"')";
												String sconDelCom = "javascript:cmdConfirmDelete('"+oidLeaveApplication+"')";
												String scancel = "javascript:cmdEdit('"+oidLeaveApplication+"')";
												
												ctrLine.setAddCaption("");												
												ctrLine.setBackCaption("Back to List Leave Application");
												ctrLine.setConfirmDelCaption("Yes Delete Leave Application");
												ctrLine.setDeleteCaption("Delete Leave Application");
												ctrLine.setSaveCaption("Save Leave Application");
					
												if (privDelete || privDeleteSpec)
												{
													ctrLine.setConfirmDelCommand(sconDelCom);
													ctrLine.setDeleteCommand(scomDel);
													ctrLine.setEditCommand(scancel);
												}
												else
												{												 
													ctrLine.setConfirmDelCaption("");
													ctrLine.setDeleteCaption("");
													ctrLine.setEditCaption("");
												}
					
												if((!privAdd && !privAddSpec) && (!privUpdate && privUpdateSpec))
												{
													ctrLine.setSaveCaption("");
												}
					
												if (!privAdd && !privAddSpec)
												{
													ctrLine.setAddCaption("");
												}
												
												out.println(ctrLine.drawImage(iCommand, iErrCode, errMsg));
												%>												
                                                </td>
                                              </tr>
											  <%
											  if(oidLeaveApplication > 0)
											  {
											  %>
                                              <tr> 
                                                <td> 
                                                  <table width="22%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td width="12%" valign="top"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image321','','/harisma_proj/images/BtnNewOn.jpg',1)"><img name="Image321" border="0" src="/harisma_proj/images/BtnNew.jpg" width="24" height="24"></a></td>
                                                      <td width="88%" valign="top" nowrap class="button">&nbsp; 
                                                        <a href="javascript:cmdPrint()" class="buttonlink">Print 
                                                        Leave Application</a></td>
                                                    </tr>
                                                  </table> 																								
												</td>
                                              </tr>											  
											  <%
											  }
											  %>
                                            </table>
                                          </td>
                                          <td width="2%">&nbsp;</td>   
                                        </tr>
                                      </table>
                                    </form>
									<script language="javascript">
										document.frm_leave_application.EMP_NAME.focus();
									</script>									
                                    <!-- #EndEditable --> </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> <!-- #EndEditable --> 
<!-- #EndTemplate --></html>
