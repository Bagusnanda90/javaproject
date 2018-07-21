
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
<%@ page import = "com.dimata.harisma.report.leave.*" %> 

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
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

// ngambil data leave application detail
Vector vectLeaveApplicationDetail = new Vector(1,1);
if(iCommand == Command.SAVE)
{
	try
	{
		String strLeaveType[] = request.getParameterValues(FrmLeaveApplicationDetail.fieldNames[FrmLeaveApplicationDetail.FRM_FLD_LEAVE_TYPE]);
		String strTakenDateYr[] = request.getParameterValues(FrmLeaveApplicationDetail.fieldNames[FrmLeaveApplicationDetail.FRM_FLD_TAKEN_DATE]+"_yr");
		String strTakenDateMn[] = request.getParameterValues(FrmLeaveApplicationDetail.fieldNames[FrmLeaveApplicationDetail.FRM_FLD_TAKEN_DATE]+"_mn");
		String strTakenDateDy[] = request.getParameterValues(FrmLeaveApplicationDetail.fieldNames[FrmLeaveApplicationDetail.FRM_FLD_TAKEN_DATE]+"_dy");		
		
		int maxIterate = strLeaveType.length;
		for(int i=0; i<maxIterate; i++)
		{
			int iLeaveType = Integer.parseInt(strLeaveType[i]);
			int iTakenDateYr = Integer.parseInt(strTakenDateYr[i]);
			int iTakenDateMn = Integer.parseInt(strTakenDateMn[i]);
			int iTakenDateDy = Integer.parseInt(strTakenDateDy[i]);
			Date dtTaken = new Date(iTakenDateYr,iTakenDateMn,iTakenDateDy);
						
			if( (iLeaveType>-1) && (dtTaken!=null) )
			{
				LeaveApplicationDetail objLeaveApplicationDetail = new LeaveApplicationDetail();
				objLeaveApplicationDetail.setLeaveType(iLeaveType);
				objLeaveApplicationDetail.setTakenDate(dtTaken);
				vectLeaveApplicationDetail.add(objLeaveApplicationDetail);				
			}
		}
	}
	catch(Exception e)
	{
		System.out.println("Exc when store leave detail : " + e.toString());
	}
}

// ngambil dari form emp schedule
long oidEmployee = FRMQueryString.requestLong(request, FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]);

int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";

ControlLine ctrLine = new ControlLine();
CtrlLeaveApplication ctrlLeaveApplication = new CtrlLeaveApplication(request);
iErrCode = ctrlLeaveApplication.action(iCommand, oidLeaveApplication, vectLeaveApplicationDetail,approot);

errMsg = ctrlLeaveApplication.getMessage();
FrmLeaveApplication frmLeaveApplication = ctrlLeaveApplication.getForm();
LeaveApplication objLeaveApplication = ctrlLeaveApplication.getLeaveApplication();   
oidLeaveApplication = objLeaveApplication.getOID();

if(iCommand==Command.DELETE && frmLeaveApplication.errorSize()<1)
{
%>
	<jsp:forward page="leave_application_list.jsp">
	<jsp:param name="start" value="<%=start%>" />
	<jsp:param name="iCommand" value="<%=Command.LIST%>" />        
	</jsp:forward>
<%
}

// get employee, position and deparment data
String strEmpNum = "";
String strEmpFullName = "";
String strEmpPosition = "";
String strEmpDepartment = "";
Employee objEmployee = new Employee();
try
{	
	if( oidEmployee == 0 )
	{
		oidEmployee =  objLeaveApplication.getEmployeeId();
	}

	objEmployee = PstEmployee.fetchExc(oidEmployee);

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


// utk tampilan leave detail dan kalkulasi stock AL dan LL
int listOfDetailSize = 0;
int intAlToBeTaken = 0;
int intLlToBeTaken = 0;
Vector listOfDetail = objLeaveApplication.getListOfDetail(); 
if(listOfDetail!=null && listOfDetail.size()>0)
{
	listOfDetailSize = listOfDetail.size();
	for(int ik=0; ik<listOfDetailSize; ik++)
	{
		LeaveApplicationDetail objLeaveApplicationDetail = (LeaveApplicationDetail) listOfDetail.get(ik);
		if(objLeaveApplicationDetail.getLeaveType() == PstLeaveApplicationDetail.LEAVE_APPLICATION_AL)
		{
			if(objLeaveApplicationDetail.getTakenDate()!=null)
			{                    
				intAlToBeTaken++;
			}			
		}
		
		if(objLeaveApplicationDetail.getLeaveType() == PstLeaveApplicationDetail.LEAVE_APPLICATION_LL)
		{
			if(objLeaveApplicationDetail.getTakenDate()!=null)
			{                    
				intLlToBeTaken++;
			}						
		}
	}
}

// mempersiapkan data utk reporting ke pdf
LeaveApplicationReport objLeaveApplicationReport = new LeaveApplicationReport(); 
int intAlEntitle = alStockManagement.getAlQty();
int intAlHasBeenTaken = alStockManagement.getQtyUsed();
int intAlSubTotal = alStockManagement.getQtyResidue();
int intAlBalance = intAlSubTotal - intAlToBeTaken;
int intLlEntitle = llStockManagement.getLLQty();
int intLlHasBeenTaken = llStockManagement.getQtyUsed();
int intLlSubTotal = llStockManagement.getQtyResidue();
int intLlBalance = intLlSubTotal - intLlToBeTaken;

if((objLeaveApplication.getDepHeadApproval()!=0) && (objLeaveApplication.getHrManApproval()!=0))
{
	String strDeptHeadName = "";
	String strHRManName = "";	
	try
	{	
		Employee objEmployee1 = PstEmployee.fetchExc(objLeaveApplication.getDepHeadApproval());
		Employee objEmployee2 = PstEmployee.fetchExc(objLeaveApplication.getHrManApproval());			
		strDeptHeadName = objEmployee1.getFullName();
		strHRManName = objEmployee2.getFullName();		
	}
	catch(Exception e)
	{
		System.out.println("Exc when fetch employee : " + e.toString());
	}
	
	objLeaveApplicationReport.setDateOfApplication(objLeaveApplication.getSubmissionDate()!=null ? Formater.formatDate(objLeaveApplication.getSubmissionDate(),"MMM dd, yyyy") : "-");
	objLeaveApplicationReport.setApplicatName(strEmpFullName);
	objLeaveApplicationReport.setApplicantPayroll(strEmpNum);
	objLeaveApplicationReport.setApplicantDepartment(strEmpDepartment);
	objLeaveApplicationReport.setApplicantPosition(strEmpPosition);
	objLeaveApplicationReport.setListOfDetail(listOfDetail);
	objLeaveApplicationReport.setLeaveReason(objLeaveApplication.getLeaveReason());
	objLeaveApplicationReport.setDepHeadApproval(strDeptHeadName);
	objLeaveApplicationReport.setHrManApproval(strHRManName);
	objLeaveApplicationReport.setDateRequestApproval(Formater.formatDate(new Date(),"MMM dd, yyyy"));
	objLeaveApplicationReport.setDateDeptHeadApproval(Formater.formatDate(new Date(),"MMM dd, yyyy"));
	objLeaveApplicationReport.setDateHrManApproval(Formater.formatDate(new Date(),"MMM dd, yyyy"));
	objLeaveApplicationReport.setAlEntitle(intAlEntitle>=0 ? ""+intAlEntitle : "("+(-1*intAlEntitle)+")");
	objLeaveApplicationReport.setAlHasBeenTaken(intAlHasBeenTaken>=0 ? ""+intAlHasBeenTaken : "("+(-1*intAlHasBeenTaken)+")");
	objLeaveApplicationReport.setAlSubTotal(intAlSubTotal>=0 ? ""+intAlSubTotal : "("+(-1*intAlSubTotal)+")");
	objLeaveApplicationReport.setAlToBeTaken(intAlToBeTaken>=0 ? ""+intAlToBeTaken : "("+(-1*intAlToBeTaken)+")");
	objLeaveApplicationReport.setAlBalance(intAlBalance>=0 ? ""+intAlBalance : "("+(-1*intAlBalance)+")");
	objLeaveApplicationReport.setLlEntitle(intLlEntitle>=0 ? ""+intLlEntitle : "("+(-1*intLlEntitle)+")");
	objLeaveApplicationReport.setLlHasBeenTaken(intLlHasBeenTaken>=0 ? ""+intLlHasBeenTaken : "("+(-1*intLlHasBeenTaken)+")");
	objLeaveApplicationReport.setLlSubTotal(intLlSubTotal>=0 ? ""+intLlSubTotal : "("+(-1*intLlSubTotal)+")");
	objLeaveApplicationReport.setLlToBeTaken(intLlToBeTaken>=0 ? ""+intLlToBeTaken : "("+(-1*intLlToBeTaken)+")");
	objLeaveApplicationReport.setLlBalance(intLlBalance>=0 ? ""+intLlBalance : "("+(-1*intLlBalance)+")");
}

try
{
	session.putValue("LEAVE_APPLICATION",objLeaveApplicationReport);
}
catch(Exception e)
{
	System.out.println("Exc when put LEAVE_APPLICATION to session");	
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
		//var linkPage  = "leave_application_buffer.jsp";   
		var linkPage  = "<%=printroot%>.report.leave.LeaveApplicationPdf?gettime=<%=System.currentTimeMillis()%>";
		window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 					
}

function cmdCancel()
{
	document.frm_leave_application.command.value="<%=Command.CANCEL%>";
	document.frm_leave_application.action="leave_application_edit_fr_schld.jsp";
	document.frm_leave_application.submit();
} 

function cmdEdit(oid)
{ 
	document.frm_leave_application.command.value="<%=Command.EDIT%>";
	document.frm_leave_application.action="leave_application_edit_fr_schld.jsp";
	document.frm_leave_application.submit(); 
} 

function cmdSave()
{
	document.frm_leave_application.command.value="<%=Command.SAVE%>"; 
	document.frm_leave_application.action="leave_application_edit_fr_schld.jsp";
	document.frm_leave_application.submit();
}

function cmdAsk(oid)
{
	document.frm_leave_application.command.value="<%=Command.ASK%>"; 
	document.frm_leave_application.action="leave_application_edit_fr_schld.jsp";
	document.frm_leave_application.submit();
} 

function cmdConfirmDelete(oid)
{
	document.frm_leave_application.command.value="<%=Command.DELETE%>";
	document.frm_leave_application.action="leave_application_edit_fr_schld.jsp"; 
	document.frm_leave_application.submit();
}  

function cmdBack()
{
	document.frm_leave_application.command.value="<%=Command.FIRST%>"; 
	document.frm_leave_application.action="<%=approot%>/employee/attendance/empschedule_list.jsp";
	document.frm_leave_application.submit();
}

function searchEmployee()
{
	window.open("leave_application_empsearch.jsp?emp_fullname="+document.frm_leave_application.EMP_NAME.value+"&EMP_NUMBER="+document.frm_leave_application.EMP_NUM.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
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
                                                        <input type="text" name="EMP_NAME" style="background-color:#F5F5F5" readonly size="46" value="<%=strEmpFullName%>">
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
                                                        <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_EMPLOYEE_ID]%>" value="<%=oidEmployee%>">
                                                        <input type="text" name="EMP_NUM" style="background-color:#F5F5F5" readonly size="20" value="<%=strEmpNum%>">
                                                        <!--<input type="button" name="btnSchEmp" value="Search Employee" onClick="javascript:searchEmployee()">-->
                                                        </td>
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
                                                        <table width="100%" border="0"  bgcolor="#F9FCFF" cellpadding="1" cellspacing="1">
                                                          <tr> 
                                                            <td colspan="7">Leave 
                                                              applied for : <i>Please 
                                                              tick (v) the box 
                                                              as appropriate : 
                                                              </i> </td>
                                                          </tr>
														  
														  <%
														  int detLeaveType = -1;
														  Date detTakenDate = null;													  
														  for(int it=0; it<listOfDetailSize; it++)												  														  
														  {														  
																LeaveApplicationDetail objLeaveApplicationDetail = (LeaveApplicationDetail) listOfDetail.get(it);
																detLeaveType = objLeaveApplicationDetail.getLeaveType();  
																detTakenDate = objLeaveApplicationDetail.getTakenDate();  
																
																out.println("<input type=\"hidden\" name="+FrmLeaveApplicationDetail.fieldNames[FrmLeaveApplicationDetail.FRM_FLD_LEAVE_TYPE]+" value="+detLeaveType+">");
																out.println("<input type=\"hidden\" name="+FrmLeaveApplicationDetail.fieldNames[FrmLeaveApplicationDetail.FRM_FLD_TAKEN_DATE]+"_yr value="+detTakenDate.getYear()+">");
																out.println("<input type=\"hidden\" name="+FrmLeaveApplicationDetail.fieldNames[FrmLeaveApplicationDetail.FRM_FLD_TAKEN_DATE]+"_mn value="+detTakenDate.getMonth()+">");
																out.println("<input type=\"hidden\" name="+FrmLeaveApplicationDetail.fieldNames[FrmLeaveApplicationDetail.FRM_FLD_TAKEN_DATE]+"_dy value="+detTakenDate.getDate()+">");																																
														  }
														  %>
														  
														  <%
														  Vector vectLeaveAppDetailView = PstLeaveApplication.synchLeaveAppDetail(listOfDetail);
														  for(int it=0; it<vectLeaveAppDetailView.size(); it++)												  														  
														  {
																LeaveApplicationDetailView objLeaveApplicationDetailView = (LeaveApplicationDetailView) vectLeaveAppDetailView.get(it);
														  %>
                                                          <tr> 
                                                            <td width="4%" align="right">&nbsp; 
                                                            </td>
                                                            <td width="25%"> 
															<%=(it+1)+".&nbsp;"%><input type="text" name="LEAVE_APP_TYPE" style="background-color:#F5F5F5" readonly value="<%=PstLeaveApplicationDetail.strApplicationType[objLeaveApplicationDetailView.getILeaveType()]%>">															
															</td>
                                                            <td width="6%"><i>from</i></td>   
                                                            <td width="31%"> 
															<input type="text" name="LEAVE_APP_DATE_FROM" style="background-color:#F5F5F5" readonly value="<%=Formater.formatDate(objLeaveApplicationDetailView.getDTakenDateFrom(),"MMMM dd, yyyy")%>">																																													
															</td>
                                                            <td width="4%"><i>to</i></td>   
                                                            <td width="27%"> 
															<input type="text" name="LEAVE_APP_DATE_TO" style="background-color:#F5F5F5" readonly value="<%=Formater.formatDate(objLeaveApplicationDetailView.getDTakenDateTo(),"MMMM dd, yyyy")%>">																																																																											
															</td>
                                                            <td width="3%" align="center">&nbsp;</td>
                                                          </tr>														  
														  <%
														  }
														  %>
														  
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
                                                              <table width="100%" border="0" bgcolor="#F9FCFF" cellpadding="1" cellspacing="1">
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
                                                                  <td width="20%" bgcolor="#F7F7F7">Total 
                                                                    Entitle</td>
                                                                  <td width="4%" align="center" bgcolor="#F7F7F7">:</td>
                                                                  <td width="20%" align="center" bgcolor="#F7F7F7"> 
                                                                    <input type="text" name="textfield" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intAlEntitle>=0 ? ""+intAlEntitle : "("+(-1*intAlEntitle)+")"%>">
                                                                  </td>
                                                                  <td width="2%" align="center" bgcolor="#F7F7F7">+</td>
                                                                  <td width="20%" align="center" bgcolor="#F7F7F7"> 
                                                                    <input type="text" name="textfield2" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intLlEntitle>=0 ? ""+intLlEntitle : "("+(-1*intLlEntitle)+")"%>">
                                                                  </td>
                                                                  <td width="4%" align="right" bgcolor="#F7F7F7">=</td>
                                                                  <td width="30%" align="right" bgcolor="#F7F7F7"><i> 
                                                                    <input type="text" name="textfield22" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=(intAlEntitle+intLlEntitle)>=0 ? ""+(intAlEntitle+intLlEntitle) : "("+(-1*(intAlEntitle+intLlEntitle))+")"%>">   
                                                                    days</i></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="20%">Has 
                                                                    been taken</td>
                                                                  <td width="4%" align="center">:</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield3" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intAlHasBeenTaken>=0 ? ""+intAlHasBeenTaken : "("+(-1*intAlHasBeenTaken)+")"%>">
                                                                  </td>
                                                                  <td width="2%" align="center">+</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield7" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intLlHasBeenTaken>=0 ? ""+intLlHasBeenTaken : "("+(-1*intLlHasBeenTaken)+")"%>">
                                                                  </td>
                                                                  <td width="4%" align="right">=</td>
                                                                  <td width="30%" align="right"><i> 
                                                                    <input type="text" name="textfield23" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=(intAlHasBeenTaken+intLlHasBeenTaken)>=0 ? ""+(intAlHasBeenTaken+intLlHasBeenTaken) : "("+(-1*(intAlHasBeenTaken+intLlHasBeenTaken))+")"%>">
                                                                    days</i></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="20%" bgcolor="#F7F7F7">Sub 
                                                                    Total</td>
                                                                  <td width="4%" align="center" bgcolor="#F7F7F7">:</td>
                                                                  <td width="20%" align="center" bgcolor="#F7F7F7"> 
                                                                    <input type="text" name="textfield4" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intAlSubTotal>=0 ? ""+intAlSubTotal : "("+(-1*intAlSubTotal)+")"%>">
                                                                  </td>
                                                                  <td width="2%" align="center" bgcolor="#F7F7F7">+</td>
                                                                  <td width="20%" align="center" bgcolor="#F7F7F7"> 
																  <input type="text" name="textfield8" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intLlSubTotal>=0 ? ""+intLlSubTotal : "("+(-1*intLlSubTotal)+")"%>">
                                                                    
                                                                  </td>
                                                                  <td width="4%" align="right" bgcolor="#F7F7F7">=</td>
                                                                  <td width="30%" align="right" bgcolor="#F7F7F7"><i> 
                                                                    <input type="text" name="textfield24" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=(intAlSubTotal+intLlSubTotal)>=0 ? ""+(intAlSubTotal+intLlSubTotal) : "("+(-1*(intAlSubTotal+intLlSubTotal))+")"%>">
                                                                    days</i></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="20%">To 
                                                                    be taken</td>
                                                                  <td width="4%" align="center">:</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield5" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intAlToBeTaken>=0 ? ""+intAlToBeTaken : "("+(-1*intAlToBeTaken)+")"%>">
                                                                  </td>
                                                                  <td width="2%" align="center">+</td>
                                                                  <td width="20%" align="center"> 
                                                                    <input type="text" name="textfield9" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intLlToBeTaken>=0 ? ""+intLlToBeTaken : "("+(-1*intLlToBeTaken)+")"%>">
                                                                  </td>
                                                                  <td width="4%" align="right">=</td>
                                                                  <td width="30%" align="right"><i> 
                                                                    <input type="text" name="textfield25" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=(intAlToBeTaken+intLlToBeTaken)>=0 ? ""+(intAlToBeTaken+intLlToBeTaken) : "("+(-1*(intAlToBeTaken+intLlToBeTaken))+")"%>">
                                                                    days</i></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="20%" bgcolor="#F7F7F7">Balance</td>
                                                                  <td width="4%" align="center" bgcolor="#F7F7F7">:</td>
                                                                  <td width="20%" align="center" bgcolor="#F7F7F7"> 
                                                                    <input type="text" name="textfield6" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intAlBalance>=0 ? ""+intAlBalance : "("+(-1*intAlBalance)+")"%>">
                                                                  </td>
                                                                  <td width="2%" align="center" bgcolor="#F7F7F7">+</td>
                                                                  <td width="20%" align="center" bgcolor="#F7F7F7"> 
                                                                    <input type="text" name="textfield10" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=intLlBalance>=0 ? ""+intLlBalance : "("+(-1*intLlBalance)+")"%>">
                                                                  </td>
                                                                  <td width="4%" align="right" bgcolor="#F7F7F7">=</td>
                                                                  <td width="30%" align="right" bgcolor="#F7F7F7"><i> 
                                                                    <input type="text" name="textfield26" size="10" style="text-align:right;background-color:#F5F5F5" readonly value="<%=(intAlBalance+intLlBalance)>=0 ? ""+(intAlBalance+intLlBalance) : "("+(-1*(intAlBalance+intLlBalance))+")"%>">
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
                                                      <td width="34%">
													  <%
													  if(objLeaveApplication.getDepHeadApproval()!=0)
													  {
													  %>													  
													  Approved by,
													  <%
													  }
													  %>
													  </td>
                                                      <td width="33%"> 
                                                        <%
													  if( objLeaveApplication.getHrManApproval()!=0 )
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
                                                        <input type="text" name="REQUESTER" style="text-align:center;background-color:#F5F5F5" readonly size="30" value="<%=strEmpFullName%>">
                                                      </td>
                                                      <td width="34%"> 
                                                      <%
													  if(objLeaveApplication.getDepHeadApproval()!=0)
													  {	
														  String strDivHeadManager = "";
														  try
														  {
														  	Employee objEmp = PstEmployee.fetchExc(objLeaveApplication.getDepHeadApproval());
															strDivHeadManager = objEmp.getFullName();
														  }
														  catch(Exception e)
														  {
														  	System.out.println("Exc when fetch DepHeadApproval : " + e.toString());
														  }
														  
														  out.println("<input type=\"text\" name=\"DEPHEADAPP\" style=\"text-align:center;background-color:#F5F5F5\" readonly size=\"30\" value=\""+strDivHeadManager+"\">");														  
													  }
													  else
													  {
														 out.println("&nbsp;");
													  } 													  
                                                      %>
                                                      </td>
                                                      <td width="33%"> 
                                                      <%
													  if( objLeaveApplication.getHrManApproval()!=0 )
													  {
														  String strHrMan = "";
														  try
														  {
														  	Employee objEmp = PstEmployee.fetchExc(objLeaveApplication.getHrManApproval());
															strHrMan = objEmp.getFullName();
														  }
														  catch(Exception e)
														  {
														  	System.out.println("Exc when fetch HrManApproval : " + e.toString());
														  }

														  out.println("<input type=\"text\" name=\"HRMANDAPP\" style=\"text-align:center;background-color:#F5F5F5\" readonly size=\"30\" value=\""+strHrMan+"\">");														  														  														  
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
												ctrLine.setBackCaption("Back to List Employee Schedule");
												ctrLine.setConfirmDelCaption("Yes Delete Leave Application");
												ctrLine.setDeleteCaption("Delete Leave Application");
												ctrLine.setSaveCaption("Save Leave Application");
					
												if ( (privDelete) && (objLeaveApplication.getDepHeadApproval()==0) && (objLeaveApplication.getHrManApproval()==0) )
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
					
												if((!privAdd) && (!privUpdate))
												{
													ctrLine.setSaveCaption("");
												}
					
												if (!privAdd)
												{
													ctrLine.setAddCaption("");
												}
												
												if(objLeaveApplication.getDepHeadApproval()!=0)
												{
													ctrLine.setConfirmDelCaption("");
													ctrLine.setDeleteCaption("");
													ctrLine.setEditCaption("");
													ctrLine.setSaveCaption("");													
													ctrLine.setAddCaption("");													
												}
												
												out.println(ctrLine.drawImage(iCommand, iErrCode, errMsg));
												%>
                                                </td>
                                              </tr>
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
