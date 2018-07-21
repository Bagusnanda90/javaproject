
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
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.form.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.session.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.form.leave.ll.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.ll.*" %>
<%@ page import = "com.dimata.harisma.session.leave.ll.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_DP_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/

//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=false;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request,"start");
int countLLTaken = FRMQueryString.requestInt(request,"countLLTaken");
long oidLLAppMain = FRMQueryString.requestLong(request, "oidLLAppMain");
long oidEmployee = FRMQueryString.requestLong(request, "oidEmployee");
long lTakenDate = FRMQueryString.requestLong(request, "lTakenDate");

int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";


LLAppMain lLAppMain = new LLAppMain();
if(oidLLAppMain>0){
    lLAppMain = PstLLAppMain.fetchExc(oidLLAppMain);
}
/**
    MENGECEK JUMLAH APPROVAL YANG DIPERLUKAN

*/

I_Leave leaveConfig = null;           
try {
    leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
}catch(Exception e) {
    System.out.println("Exception : " + e.getMessage());
}

int maxApporval = leaveConfig.getMaxApproval(oidEmployee);

    

ControlLine ctrLine = new ControlLine();

/** ///////////////////////////////////////////////////////
Menyimpan data
*/

    CtrlLLAppMain ctrlLLAppMain = new CtrlLLAppMain(request);
 /* EXECUTE ACTION COMMAND */
    iErrCode = ctrlLLAppMain.action(iCommand, oidLLAppMain);    
    errMsg = ctrlLLAppMain.getMessage();
    lLAppMain = ctrlLLAppMain.getEntity();
    oidLLAppMain = lLAppMain.getOID();
    if(iCommand == Command.SAVE){
          int valReq = 0;
          if(oidLLAppMain>0 && (lLAppMain.getStartDate().getTime()<=lLAppMain.getEndDate().getTime())){
            Date dtTempStart = (Date)lLAppMain.getStartDate().clone();
            Date dtTempEnd = (Date)lLAppMain.getEndDate().clone();
            while(dtTempStart.getTime()<=dtTempEnd.getTime()){
                dtTempStart.setDate(dtTempStart.getDate()+1);
                valReq +=1;
            }
          }
          lLAppMain.setRequestQty(valReq);
          try{
            PstLLAppMain.updateExc(lLAppMain);
          }catch(Exception xe){}
    }
    if(iCommand == Command.DELETE & iErrCode==CtrlLLAppMain.RSLT_OK){
            %>
                <jsp:forward page="../attendance/empschedule_list.jsp">
                <jsp:param name="start" value="<%=start%>" />
                <jsp:param name="iCommand" value="<%=Command.LIST%>" />        
                </jsp:forward>
            <%
    }
    
//////////////////////////////////////////////////////

// get employee and deparment data
String strEmpFullName = "";
String strEmpDepartment = "";
String strEmpPosition = "";
String strEmpDivision = "";
Employee objEmployee = new Employee();

try
{
	if( oidEmployee == 0 && lLAppMain.getEmployeeId()>0)
	{
		oidEmployee =  lLAppMain.getEmployeeId();
	}
	
	objEmployee = PstEmployee.fetchExc(oidEmployee);

	// get fullname
	strEmpFullName = objEmployee.getFullName();
	
	// get deparment
	Department objDepartment = new Department();
	objDepartment = PstDepartment.fetchExc(objEmployee.getDepartmentId());
	strEmpDepartment = objDepartment.getDepartment();
        
        //get position
        Position objPosition = new Position();
        objPosition = PstPosition.fetchExc(objEmployee.getPositionId());
        strEmpPosition = objPosition.getPosition();
        
        //get division
        Division objDivision = new Division();
        objDivision = PstDivision.fetchExc(objEmployee.getDivisionId());
        strEmpDivision = objDivision.getDivision();
}
catch(Exception e)
{
	System.out.println("Exc when fetch employee and deparment data : " + e.toString());
}

Vector vListDp = new Vector(1,1);

Date dateTemp = new Date(lTakenDate);
//System.out.println("================>>>> "+Formater.formatDate(dateTemp, "dd-MM-yyyy"));
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - LL Application</title>
<script language="JavaScript">
<!--

function cmdPrint()
{ 
    //alert("CREATE REPORT < % //=String.valueOf(oidDpAppMain)%>");
    pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LLAppPdf?oidLLAppMain=<%=String.valueOf(oidLLAppMain)%>";
    window.open(pathUrl);
}


function cmdCancel()
{
	document.frm_ll_application.command.value="<%=String.valueOf(Command.CANCEL)%>";
	document.frm_ll_application.action="ll_application_edit_fr_schld.jsp";
	document.frm_ll_application.submit();
} 

function cmdEdit(oid)
{ 
	document.frm_ll_application.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frm_ll_application.action="ll_application_edit_fr_schld.jsp";
	document.frm_ll_application.submit(); 
} 

function cmdSave()
{
	document.frm_ll_application.command.value="<%=String.valueOf(Command.SAVE)%>"; 
	document.frm_ll_application.action="ll_application_edit_fr_schld.jsp";
	document.frm_ll_application.submit();
}

function cmdAsk(oid)
{
	document.frm_ll_application.command.value="<%=String.valueOf(Command.ASK)%>"; 
	document.frm_ll_application.action="ll_application_edit_fr_schld.jsp";
	document.frm_ll_application.submit();
} 

function cmdConfirmDelete(oid)
{
	document.frm_ll_application.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frm_ll_application.action="ll_application_edit_fr_schld.jsp"; 
	document.frm_ll_application.submit();
}  

function cmdBack()
{
	document.frm_ll_application.command.value="<%=String.valueOf(Command.FIRST)%>"; 
	document.frm_ll_application.action="<%=approot%>/employee/attendance/empschedule_list.jsp";
	document.frm_ll_application.submit();
}

//--------------- Calendar  -------------------------------
function getThn(){
            <%=ControlDatePopup.writeDateCaller("frm_ll_application",FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_SUBMISSION_DATE])%>
            <%=ControlDatePopup.writeDateCaller("frm_ll_application",FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_START_DATE])%>
            <%=ControlDatePopup.writeDateCaller("frm_ll_application",FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_END_DATE])%>
            getDays();
}


    function hideObjectForDate(index){
    }

    function showObjectForDate(){
    } 

function getDays(){
    startYr = document.frm_ll_application.<%=FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_START_DATE]%>_yr.value;
    startMn = document.frm_ll_application.<%=FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_START_DATE]%>_mn.value;
    startDy = document.frm_ll_application.<%=FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_START_DATE]%>_dy.value;
    
    endYr = document.frm_ll_application.<%=FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_END_DATE]%>_yr.value;
    endMn = document.frm_ll_application.<%=FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_END_DATE]%>_mn.value;
    endDy = document.frm_ll_application.<%=FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_END_DATE]%>_dy.value;

    status = 200;
    counter = 0;
    
    cek = true;
    if(startYr>endYr){
        cek = false;
    }else if(startYr==endYr){
        if(startMn>endMn){
            cek = false;
        }else if(startMn==endMn){
            if(startDy>endDy){
                cek = false;
            }else if(startDy==endDy){
                cek = false;
                counter = 1;
            }
        }
    }
    if(cek==true){
        for(i=0; i<status; i++){
            m = startMn;
            days = 0;
            if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
                    days = 31;
            } else if (m == 4 || m == 6 || m == 9 || m == 11) {
                    days = 30;
            } else {
                    days = (y % 4 == 0) ? 29 : 28;
            }
            
            counter++;
            //Memajukan hari
            startDy = startDy+1;
            if(startDy>days){
                startMn = startMn+1;
                startDy = 1;
            }
            
            if(startMn>12){
                startYr = startYr+1;
                startMn = 1;
            }
            if(startYr==endYr && startMn==endMn && startDy==endDy){
                status = 0;
            }
         }
    }
    document.frm_ll_application.NO_DAYS_REQ.value = counter;
}

//-------------- script control line -------------------
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
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<!-- End Calender-->
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
                  Employee &gt; Leave Management &gt; LL Application<!-- #EndEditable --> 
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
                                    <form name="frm_ll_application" method="post" action="">
                                      <input type="hidden" name="<%=FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_LL_APP_ID]%>" value="<%=String.valueOf(lLAppMain.getOID())%>">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="oidLLAppMain" value="<%=String.valueOf(lLAppMain.getOID())%>">
                                      <input type="hidden" name="oidEmployee" value="<%=String.valueOf(oidEmployee)%>">
                                      <input type="hidden" name="lTakenDate" value="<%=String.valueOf(lTakenDate)%>">
                                      <input type="hidden" name="countLLTaken" value="<%=String.valueOf(countLLTaken)%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <input type="hidden" name="TO_DEPARTMENT" size="40" value="HRD">
                                        <input type="hidden" name="TO_POSITION" size="40" value="HR MANAGER">
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                              <tr> 
                                              
                                                <td align="center"><b><font size="3">
                                                    LONG LEAVE (LL) REQUEST
                                                  </font></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="center"> 
                                                  <div align="center"><b><font size="3">
                                                  </font></b></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0">
                                                    <tr align="left"> 
                                                      <!--Name -->
                                                      <td  width="10%">Name</td>
                                                      <td  width="2%">:</td>
                                                        <input type="hidden" name="<%=FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_EMPLOYEE_ID]%>" value="<%=String.valueOf(oidEmployee)%>">
                                                      <td  width="88%"colspan="4"><b><%=strEmpFullName%></b></td>
                                                    </tr>
                                                    <tr> 
                                                      <!--Position -->
                                                      <td width="10%">Position</td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=strEmpPosition%></b></td>
                                                      <!--Payroll-->
                                                      <td width="10%">Payroll</td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=objEmployee.getEmployeeNum()%></b></td>
                                                    </tr>
                                                    <tr> 
                                                      <!--Division -->
                                                      <td width="10%"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=strEmpDivision%></b></td>
                                                      <!--Department-->
                                                      <td width="10%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=strEmpDepartment%></b></td>
                                                    </tr>
                                                    <tr> 
                                                      <!--Commencing Date -->
                                                      <td width="10%">Commencing Date</td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=Formater.formatDate(objEmployee.getCommencingDate(), "MMMM dd, yyyy")%></b></td>
                                                      <!--Date of Request-->
                                                      <td width="10%">Date of Request</td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b>
                                                          <%=ControlDatePopup.writeDate(FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_SUBMISSION_DATE],(lLAppMain.getSubmissionDate()==null ? dateTemp : lLAppMain.getSubmissionDate()))%>
                                                      </b></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                            </tr>
                                            
                                                    <tr><td><hr></td></tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0">
                                                    <tr> 
                                                    <%
                                                          int balance = SessLongLeave.getLLBalance(objEmployee.getOID(),new Date());
                                                          if(lLAppMain.getOID()>0){
                                                            balance = lLAppMain.getBalance();
                                                          }
                                                          %>
                                                    <input type="hidden" name="<%=FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_BALANCE]%>" value="<%=String.valueOf(balance)%>">
                                                      <td colspan="3">No. of days eligible : <b><%=String.valueOf(balance)%></b></td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="3">
                                                        <!-- TEMPAT MEMILIH DATA -->
                                                        <table width="100%" border="0" class="tablecolor" cellpadding="1" cellspacing="1">
                                                            <tr> 
                                                              <td valign="top"> 
                                                                <table width="100%" border="0" bgcolor="#F9FCFF">
                                                                  <tr>
                                                                      <td>
                                                                          
                                                                      </td>
                                                                  </tr>
                                                                  <tr>
                                                                      <td width="15%" >
                                                                          From
                                                                      </td>
                                                                      <td  width="2%" >
                                                                          :
                                                                      </td>
                                                                      <td  width="83%" >
                                                                          <%=ControlDatePopup.writeDate(FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_START_DATE],(lLAppMain.getStartDate()==null ? dateTemp : lLAppMain.getStartDate()))%>
                                                                      </td>
                                                                  </tr>
                                                                  <tr>
                                                                      <td width="15%" >
                                                                          To
                                                                      </td>
                                                                      <td  width="2%" >
                                                                          :
                                                                      </td>
                                                                      <td  width="83%" >
                                                                          <%=ControlDatePopup.writeDate(FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_END_DATE],(lLAppMain.getEndDate()==null ? dateTemp : lLAppMain.getEndDate()))%>
                                                                      </td>
                                                                  </tr>
                                                                  <tr>
                                                                      <td width="15%" valign="top" >
                                                                          Number of days requested
                                                                      </td>
                                                                      <td  width="2%" >
                                                                          :
                                                                      </td>
                                                                      <td  width="83%" >
                                                                          <%
                                                                          int valReq = 0;
                                                                          if(oidLLAppMain>0 && (lLAppMain.getStartDate().getTime()<=lLAppMain.getEndDate().getTime())){
                                                                            valReq = lLAppMain.getRequestQty();
                                                                          }
                                                                          %>
                                                                          <input type="text" name="NO_DAYS_REQ" value="<%=String.valueOf(valReq)%>" readonly='true'>
                                                                      </td>
                                                                  </tr>
                                                                </table>
                                                              </td>
                                                            </tr>
                                                       </table>
                                                        <!-- END MEMILIH DATA-->
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp; </td>
                                              </tr>
                                              <tr> 
                                                <td valign="top"> 
                                                  <table width="100%" border="0" class="tablecolor" cellpadding="1" cellspacing="1">
                                                    <tr> 
                                                      <td valign="top"> 
                                                        <table width="100%" border="0" bgcolor="#F9FCFF">
                                                          <tr>
                                                                    <td width="15%" align="left"><b>Approval</b></td >
                                                                    <td width="2%" ></td>
                                                                    <td width="60%" align="center"><b>Signature</b></td>
                                                                    <td width="23%" align="center"><b>Date</b></td>
                                                                </tr>
                                                          <!--Employee-->
                                                          <tr>
                                                              <td width="15%" >Employee</td >
                                                              <td width="2%" >:</td>
                                                              <td width="60%" ><b><%=strEmpFullName%></b></td>
                                                              <td width="23%" ><b>
                                                                  <%=Formater.formatDate(lLAppMain.getSubmissionDate()==null ? new Date() : lLAppMain.getSubmissionDate(), "MMMM dd, yyyy")%>
                                                                  <%--=ControlDatePopup.writeDate(FrmLLAppMain.fieldNames[FrmLLAppMain.FRM_FLD_SUBMISSION_DATE]+"1",(lLAppMain.getSubmissionDate()==null ? new Date() : lLAppMain.getSubmissionDate()))--%>
                                                              </b></td>
                                                          </tr>
                                                          <!--Dept Head-->
                                                          <%
                                                          if(lLAppMain.getApprovalId()>0 && maxApporval>=1){
                                                              String strApp = "";
                                                              Employee employee = new Employee();
                                                              try{
                                                                  employee = PstEmployee.fetchExc(lLAppMain.getApprovalId());
                                                                  strApp = employee.getFullName();
                                                              }catch(Exception ex){}
                                                          %>
                                                          <tr>
                                                              <td width="15%" >Div./Department Head</td >
                                                              <td width="2%" >:</td>
                                                              <td width="60%" ><b><%=strApp%></b></td>
                                                              <td width="23%" ><b>
                                                                  <%=Formater.formatDate(lLAppMain.getApprovalDate(), "MMMM dd, yyyy")%>
                                                              </b></td>
                                                          </tr>
                                                          <%}%>
                                                          <!--Exc Prod-->
                                                          <%
                                                          if(lLAppMain.getApproval2Id()>0 && maxApporval>=2){
                                                              String strApp = "";
                                                              Employee employee = new Employee();
                                                              try{
                                                                  employee = PstEmployee.fetchExc(lLAppMain.getApproval2Id());
                                                                  strApp = employee.getFullName();
                                                              }catch(Exception ex){}
                                                          %>
                                                          <tr>
                                                              <td width="15%" >Exceutive Producer</td >
                                                              <td width="2%" >:</td>
                                                              <td width="60%" ><b><%=strApp%></b></td>
                                                              <td width="23%" ><b>
                                                                  <%=Formater.formatDate(lLAppMain.getApproval2Date(), "MMMM dd, yyyy")%>
                                                              </b></td>
                                                          </tr>
                                                          <%}%>
                                                          <!--Prod Talent-->
                                                          <%
                                                          if(lLAppMain.getApproval3Id()>0 && maxApporval>=3){
                                                              String strApp = "";
                                                              Employee employee = new Employee();
                                                              try{
                                                                  employee = PstEmployee.fetchExc(lLAppMain.getApproval3Id());
                                                                  strApp = employee.getFullName();
                                                              }catch(Exception ex){}
                                                          %>
                                                          <tr>
                                                              <td width="15%" >Producer-Talent</td >
                                                              <td width="2%" >:</td>
                                                              <td width="60%" ><b><%=strApp%></b></td>
                                                              <td width="23%" ><b>
                                                                  <%=Formater.formatDate(lLAppMain.getApproval3Date(), "MMMM dd, yyyy")%>
                                                              </b></td>
                                                          </tr>
                                                          <%}%>
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
                                                  <%
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("80");
                                                    ctrLine.setCommandStyle("buttonlink");												

                                                    String scomDel = "javascript:cmdAsk('"+oidLLAppMain+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidLLAppMain+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidLLAppMain+"')";

                                                    ctrLine.setAddCaption("");												
                                                    ctrLine.setBackCaption("Back to List Employee Schedule");
                                                    ctrLine.setConfirmDelCaption("Yes Delete LL Application");
                                                    ctrLine.setDeleteCaption("Delete LL Application");
                                                    ctrLine.setSaveCaption("Save LL Application"); 

                                                    if ( (privDelete) && (lLAppMain.getApprovalId()==0) )
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

                                                    if(lLAppMain.getApprovalId()!=0)
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
                                              <%if(lLAppMain.getApproval3Id()>0){%>
                                               <tr> 
                                                <td>
                                                <table><tr>
                                                  <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                  <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                  <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                  <td height="22" valign="middle" colspan="3" width="951"> 
                                                    <a href="javascript:cmdPrint()" class="command">Print</a> </td>
                                                    </tr>
                                                </table>
                                                </td>
                                                </tr>
                                              <%}%>
                                            </table>
                                          </td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                      </table>
                                    </form>
									<%
									if( (lLAppMain.getApprovalId()==0) )
									{
									%>									
                                    <script language="javascript">
										document.frm_ll_application.btnSrcDp.focus();
									</script>	
									<%
									}
									%>
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
