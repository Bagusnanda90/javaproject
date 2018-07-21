<%-- 
    Document   : MultipleSetAttendanceStatusandReason
    Created on : 10-Dec-2015, 16:30:16
    Author     : GUSWIK
--%>

<%@page import="com.dimata.harisma.form.attendance.CtrlEmpSchedule"%>
<%@page import="com.dimata.harisma.session.employee.SessSpecialEmployee"%>
<%@page import="com.dimata.harisma.session.employee.SearchSpecialQuery"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>


<%@ include file = "../../main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_MANUAL_PRESENCE); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%//@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PRESENCE, AppObjInfo.OBJ_MANUAL_PRESENCE); %>
<%// int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_MANUAL_PRESENCE); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<!-- Jsp Block -->

<%
int iCommand = FRMQueryString.requestCommand(request);
String massage ="";
String empNum = FRMQueryString.requestString(request, "empNum");
String nama = FRMQueryString.requestString(request, "nama");
Long companyId = FRMQueryString.requestLong(request, "companyId");
Long divisionId = FRMQueryString.requestLong(request, "divisionId");
Long departmentId = FRMQueryString.requestLong(request, "departmentId");
Long sectionId = FRMQueryString.requestLong(request, "sectionId");
Long positionId = FRMQueryString.requestLong(request, "positionId");
Date dateFrom = FRMQueryString.requestDate(request, "dateFrom");
Date dateTo = FRMQueryString.requestDate(request, "dateTo");

Long payGroup = FRMQueryString.requestLong(request,"payGroup");
String note = FRMQueryString.requestString(request, "note");
int reason_status = FRMQueryString.requestInt(request,"reason_status");
int statusPresence = FRMQueryString.requestInt(request,"statusPresence");

int upTipe = FRMQueryString.requestInt(request,"upTipe");

 Vector listEmployee = new Vector();
if(iCommand==Command.SUBMIT)
{        
    
    String whereClause = "1=1";
        if (!empNum.equals("")){
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +  " LIKE '%" + empNum + "%' ";
        }
        if (!nama.equals("")){
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +  " LIKE '%" + nama + "%' ";
        }
        if (companyId !=0){
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + "=" + companyId;
        }
        if (divisionId !=0){
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + divisionId;
        }
        if (departmentId !=0){
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + departmentId;
        }
        if (sectionId !=0){
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + sectionId;
        }
        if (positionId !=0){
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + positionId;
        }
        if (payGroup !=0){
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_PAYROLL_GROUP] + "=" + payGroup;
        }
    
     listEmployee = PstEmployee.list(0, 0, whereClause, "");
     int i= 1;
      dateTo.setDate(dateTo.getDate()+1);
     do{
         if (listEmployee.size() != 0){
             for (int x=0; x < listEmployee.size(); x++){
                 Employee employee = (Employee) listEmployee.get(x);
                 long periodId = PstPeriod.getPeriodIdBySelectedDate(dateFrom);
                 int result = 1;
                 
                 if ((upTipe == 1)|| (upTipe == 0) ){
                 String fieldstatus = PstEmpSchedule.fieldNames[(PstEmpSchedule.OFFSET_INDEX_STATUS+(dateFrom.getDate()-1))];
                 int nilaiStatus =  PstEmpSchedule.updatestatusnew(periodId, employee.getOID(), fieldstatus, statusPresence);
                 result=0;
                 }
                 if ((upTipe == 2)|| (upTipe == 0) ){
                 String fieldreason = PstEmpSchedule.fieldNames[(PstEmpSchedule.OFFSET_INDEX_REASON+(dateFrom.getDate()-1))];
                 int nilaiReason =  PstEmpSchedule.updatereasonnew(periodId, employee.getOID(), fieldreason, reason_status);
                 result=0;
                 }
                 String fieldnote = PstEmpSchedule.fieldNames[(PstEmpSchedule.OFFSET_INDEX_NOTE+(dateFrom.getDate()-1))];
                 int nilaiNote =  PstEmpSchedule.updatenotenew(periodId, employee.getOID(), fieldnote, note);
                 massage =  CtrlEmpSchedule.resultText[0][result];
             }
         }
         
         dateFrom.setDate(dateFrom.getDate()+1);
     } while (dateFrom.before(dateTo));
    // PstEmpSchedule.updatePresence(periodId, date, empId, status);
}

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Manual Presence</title>
<script language="JavaScript">

<%
//update by devin 2014-01-22
long oidHr = 0;
long oidEdp = 0;
try{
    oidHr  = Long.parseLong(PstSystemProperty.getValueByName(OID_HRD_DEPARTMENT));
}catch(Exception exc){

}
try{
   oidEdp  = Long.parseLong(PstSystemProperty.getValueByName(OID_EDP_SECTION)); 
}catch(Exception exc){
    
}
 

if( false && departmentOid!=oidHr && departmentOid!=oidEdp){%>
	window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
<%//}else if(departmentOid==oidHr && positionType!=PstPosition.LEVEL_MANAGER){%>
	//window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
<%}else if(false && departmentOid==oidEdp && positionType!=PstPosition.LEVEL_SUPERVISOR){%>
	window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
<%}

%>
function cmdAdd(){
	document.frmsrcpresence.command.value="<%=Command.ADD%>";
	document.frmsrcpresence.action="presence_edit.jsp";
	document.frmsrcpresence.submit();
}
function cmdSearchSchedule(){
	document.frmsrcpresence.command.value="<%=Command.ADD%>";
	document.frmsrcpresence.action="manual_attendance_all_employee.jsp";
	document.frmsrcpresence.submit();
}
function cmdSearch(){
	document.frmsrcpresence.command.value="<%=Command.LIST%>";
	document.frmsrcpresence.action="presence_list.jsp";
	document.frmsrcpresence.submit();
}
function cmdSubmit(){
	document.frmsrcpresence.command.value="<%=Command.SUBMIT%>";
	document.frmsrcpresence.action="MultipleSetAttendanceStatusandReason.jsp";
	document.frmsrcpresence.submit();
}
function cmdUpdate(){
	document.frmsrcpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frmsrcpresence.action="MultipleSetAttendanceStatusandReason.jsp"; 
	document.frmsrcpresence.submit();
}
function fnTrapKD(){
    alert("ss");
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}
//-------------- script control line -------------------
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
<!--
function hideObjectForEmployee(){ 
    
    
	document.frmsrcpresence.departmentId.style.visibility="hidden";  
	document.frmsrcpresence.sectionId.style.visibility="hidden";  
	document.frmsrcpresence.positionId.style.visibility="hidden";  
} 

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
}

function showObjectForMenu(){
	document.frmsrcpresence.departmentId.style.visibility="";  
	document.frmsrcpresence.sectionId.style.visibility="";  
	document.frmsrcpresence.positionId.style.visibility="";  

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
</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Attendance &gt; Manual Presence<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcpresence" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
									  <table width="100%" border="0" cellspacing="0" cellpadding="0">
										  <tr>
											<td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="100%" >
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left">Payroll 
                                                          Number</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <input type="text" name="empNum"  value="<%=empNum %>" class="elemenForm" onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left">Full 
                                                          Name</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <input type="text" name="nama"  value="<%=nama %>" class="elemenForm" onkeydown="javascript:fnTrapKD()" size="35">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.COMPANY) %></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector company_value = new Vector(1,1);
                                                            Vector company_key = new Vector(1,1);
                                                            company_value.add("0");
                                                            company_key.add("select ...");   
                                                                                                                                                                           
                                                            Vector listCom = PstCompany.list(0, 0, "", "");                                                        
                                                            String selectValueCompany = String.valueOf(companyId);
                                                            for (int i = 0; i < listCom.size(); i++) {
                                                                    Company company = (Company) listCom.get(i);
                                                                    company_key.add(company.getCompany());
                                                                    company_value.add(String.valueOf(company.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("companyId","elementForm", null, selectValueCompany, company_value, company_key, "onChange=\"javascript:cmdUpdate()\"") %> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector division_value = new Vector(1,1);
                                                            Vector division_key = new Vector(1,1);
                                                            division_value.add("0");
                                                            division_key.add("select ...");   
                                                            String wherecomp = "";
                                                            if (companyId != 0){
                                                            wherecomp = "COMPANY_ID = " + companyId;
                                                            }                                                                                                                   
                                                            Vector listDiv = PstDivision.list(0, 0, wherecomp, " DIVISION ");                                                        
                                                            String selectValueDivision = String.valueOf(divisionId);
                                                            for (int i = 0; i < listDiv.size(); i++) {
                                                                    Division division = (Division) listDiv.get(i);
                                                                    division_key.add(division.getDivision());
                                                                    division_value.add(String.valueOf(division.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("divisionId","elementForm", null, selectValueDivision, division_value, division_key, "onChange=\"javascript:cmdUpdate()\"") %> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector department_value = new Vector(1,1);
                                                            Vector department_key = new Vector(1,1);
                                                            department_value.add("0");
                                                            department_key.add("select ...");    
                                                            String wherediv = "";
                                                            if (divisionId != 0){
                                                            wherediv = "DIVISION_ID = "+divisionId;
                                                            }                                                                                                                 
                                                            Vector listDept = PstDepartment.list(0, 0, wherediv, " DEPARTMENT ");                                                        
                                                            String selectValueDepartment = String.valueOf(departmentId);
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    department_key.add(dept.getDepartment());
                                                                    department_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("departmentId","elementForm", null, selectValueDepartment, department_value, department_key, "onChange=\"javascript:cmdUpdate()\"") %> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector section_value = new Vector(1,1);
                                                            Vector section_key = new Vector(1,1);
                                                            section_value.add("0");
                                                            section_key.add("select ...");                                                              
                                                            //Vector listSec = PstSection.list(0, 0, "", "SECTION "); 
                                                            String wheredep = "";
                                                            if (departmentId != 0){
                                                            wheredep = "DEPARTMENT_ID = "+departmentId;
                                                            }
        						    Vector listSec = PstSection.list(0, 0, wheredep, "SECTION ");                                                          
                                                            String selectValueSection = String.valueOf(sectionId);
                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                    Section sec = (Section) listSec.get(i);
                                                                    section_key.add(sec.getSection());
                                                                    section_value.add(String.valueOf(sec.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("sectionId","elementForm", null, selectValueSection, section_value, section_key,"onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector position_value = new Vector(1,1);
                                                            Vector position_key = new Vector(1,1);
                                                            position_value.add("0");
                                                            position_key.add("select ...");                                                       
                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
                                                            String selectValuePosition = String.valueOf(positionId);
                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                    Position pos = (Position) listPos.get(i);
                                                                    position_key.add(pos.getPosition());
                                                                    position_value.add(String.valueOf(pos.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("positionId","elementForm", null, selectValuePosition, position_value, position_key,"onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                     <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL)%> <%=dictionaryD.getWord(I_Dictionary.GROUP)%></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector payG_value = new Vector(1,1);
                                                            Vector payG_key = new Vector(1,1);
                                                            payG_value.add("0");
                                                            payG_key.add("select ...");                                                       
                                                            Vector listPayG= PstPayrollGroup.list(0, 0, "", "PAYROLL_GROUP_NAME");    
                                                            for (int i = 0; i < listPayG.size(); i++) {
                                                                    PayrollGroup payrollGroup = (PayrollGroup) listPayG.get(i);
                                                                    payG_key.add(payrollGroup.getPayrollGroupName());
                                                                    payG_value.add(String.valueOf(payrollGroup.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("payGroup","elementForm", null, String.valueOf(payGroup), payG_value, payG_key,"onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.PRESENCE)%> <%=dictionaryD.getWord(I_Dictionary.STATUS)%></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector statusVal = new Vector();
                                                            Vector statusTxt = new Vector();
                                                            for (int s = 0; s < PstEmpSchedule.strPresenceStatus.length; s++) {
                                                                statusVal.add("" + s);
                                                                statusTxt.add("" + PstEmpSchedule.strPresenceStatus[s]);
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("statusPresence","elementForm", null, String.valueOf(statusPresence), statusVal, statusTxt,"onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>    
                                                        <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left">reason</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                           Vector reason_value = new Vector(1, 1);
                                                            Vector reason_key = new Vector(1, 1);
                                                            Vector reason_tooltip = new Vector(1, 1);
                                                            Vector  listReason = PstReason.list(0, 0, "", "REASON");
                                                            for (int r = 0; r < listReason.size(); r++) {
                                                                    Reason reason = (Reason) listReason.get(r);
                                                                    reason_tooltip.add(reason.getReason()); 
                                                                     reason_key.add(reason.getKodeReason());
                                                                    reason_value.add(String.valueOf(reason.getNo()));
                                                            } 
                                                        %>
                                                        <%= ControlCombo.drawTooltip("reason_status", "select...", ""+reason_status, reason_value,  reason_key, "onkeydown=\"javascript:fnTrapKD()\"", reason_tooltip) %> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.PRESENCE)%> <%=dictionaryD.getWord(I_Dictionary.NOTE)%></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> <textarea name="note" class="elemenForm" cols="30" rows="3"><%=note%></textarea>
                                                                                                                    </td>
                                                    </tr>
                                                     <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left">Update Tipe</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                           Vector upTipe_value = new Vector(1, 1);
                                                           Vector upTipe_key = new Vector(1, 1);
                                                           upTipe_value.add("1");
                                                           upTipe_value.add("2");
                                                           upTipe_value.add("3");
                                                           upTipe_key.add("reason and status");
                                                           upTipe_key.add("only status");
                                                           upTipe_key.add("only reason");
                                                        %>
                                                        <%= ControlCombo.drawTooltip("upTip", null, ""+upTipe, upTipe_value,  upTipe_key, "onkeydown=\"javascript:fnTrapKD()\"", reason_tooltip) %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="21" valign="top" width="10%">Select Date</td>
                                                      <td height="21" width="2%">:</td>
                                                      <td height="21" width="88%"> 
                                                        <table width="100%" border="0">
                                                          <tr> 
                                                            <td width="5%">From</td>
															<%	
															Date selectedDateFrom = dateFrom!=null ? dateFrom : new Date();
															%>
                                                            <td width="95%"><%=ControlDate.drawDate("dateFrom", selectedDateFrom, 0, installInterval) %></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="5%">To</td>
															<%
															Date selectedDateTo = dateTo!=null ? dateTo : new Date();
															%>															
                                                            <td width="95%"><%=ControlDate.drawDate("dateTo", selectedDateTo, 0, installInterval) %></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%">  
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="88%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td nowrap width="2%">&nbsp;</td>
                                                      <td nowrap width="88%"> 
                                                        <table border="0" cellpadding="0" cellspacing="0" width="60">
                                                          <tr> 
                                                              <td width="20%" style="font-size: 18px; text-align: center; " ><%=massage %></td>
                                                           
                                                          </tr>
                                                          <tr> 
                                                            <td width="20%"><a href="javascript:cmdSubmit()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdSubmit()">Submit</a></td>
                                                           
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
									  
                                      </form>
                                    <!-- #EndEditable -->
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td>&nbsp; </td>
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
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
