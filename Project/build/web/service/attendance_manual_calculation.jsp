<% 
/* 
 * Page Name  		:  back_up.jsp
 * Created on 		:  [date] [time] AM/PM    
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version]    
 */

/*******************************************************************
 * Page Description : [project description ... ] 
 * Imput Parameters : [input parameter ...] 
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
<%@ page import = "com.dimata.harisma.utility.service.presence.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.utility.service.leavedp.*" %>
<%@ page import = "com.dimata.harisma.utility.service.backupdb.*" %>
<%@ page import = "com.dimata.harisma.entity.service.*" %>
<%@ page import = "com.dimata.harisma.form.service.*" %>
<%@page import="com.dimata.harisma.utility.machine.ManualAttandance"%>

<%@ include file = "../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_MANUAL_CHECKING, AppObjInfo.OBJ_MANUAL_CHECKING_ALL); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
//boolean privView=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
%>

<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
String sEmployeeName = FRMQueryString.requestString(request, "EMPLOYEE_NAME");
long lDivisionOid = FRMQueryString.requestLong(request, "DIVISION_OID");
long lDepartmentOid = FRMQueryString.requestLong(request, "DEPARTMENT_OID");
long lSectionOid = FRMQueryString.requestLong(request, "SECTION_OID");
long periodId = FRMQueryString.requestLong(request, "PERIODE_ID");
//String sEmployeePayroll = FRMQueryString.requestString(request, "EMPLOYEE_PAYROLL");
String sPayrolNum = FRMQueryString.requestString(request, "PAYROL_NUMBER");
Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
String sEmpPayrolNum = FRMQueryString.requestString(request, "PAYROL_NUMBER_EMP");
String sFullName = FRMQueryString.requestString(request, "EMPLOYEE_FULL_NAME");
Date selectDateFrom = FRMQueryString.requestDate(request, "check_date_from");
Date selectDateTo = FRMQueryString.requestDate(request, "check_date_to");

//priska menambahkan menu category 20150718
String[] stsEmpCategory = null;
    int sizeCategory = PstEmpCategory.listAll() != null ? PstEmpCategory.listAll().size() : 0;
    stsEmpCategory = new String[sizeCategory];
    String stsEmpCategorySel = "";
    int maxEmpCat = 0;
    for (int j = 0; j < sizeCategory; j++) {
        String name = "EMP_CAT_" + j;
        String val = FRMQueryString.requestString(request, name);
        stsEmpCategory[j] = val;
        if (val != null && val.length() > 0) {
            //stsEmpCategorySel.add(""+val); 
            stsEmpCategorySel = stsEmpCategorySel + val + ",";
        }
        maxEmpCat++;
    }


    String strStatusManualAttd = "";
    ManualAttandance manualAttd = ManualAttandance.getInstance(true);  /* }*/
    Date mDateFrom = new Date();
    Date mDateTo = new Date();
    if (manualAttd.getAssistant().getFromDate() != null) {
        long mlDateFrom = manualAttd.getAssistant().getFromDate().getTime() + (1000 * 60 * 60 * 24);
        mDateFrom = new Date(mlDateFrom);
    }
    if (manualAttd.getAssistant().getToDate() != null) {
        long mlDateTo = manualAttd.getAssistant().getToDate().getTime() - (1000 * 60 * 60 * 24);
        mDateTo = new Date(mlDateTo);
    }
//String strButtonStatusMachine ="";

        //update by satrya 2012-06-30
                   String strMessage = "";        
                if (privStart) 
                    
                {
        long ldtSelectedFrom = selectedDateFrom.getTime()- (1000 * 60 * 60 * 24);
        long ldtSelectedTo = selectedDateTo.getTime() + (1000 * 60 * 60 * 24); 
        Date dtSelectedFrom = new Date(ldtSelectedFrom);
	Date dtSelectedTo = new Date(ldtSelectedTo);
        //Date dtSelectedFrom = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), selectedDateFrom.getDate());
	//Date dtSelectedTo = new Date(selectedDateTo.getYear(), selectedDateTo.getMonth(), selectedDateTo.getDate());
	Date nowDate = new Date();		
	Date dtNow = new Date(nowDate.getYear(), nowDate.getMonth(), nowDate.getDate());	
	String commandManualReg = request.getParameter("commandManualReg");
                        if (commandManualReg != null) 
                        {
                                if (commandManualReg.equalsIgnoreCase("Run")) 
                                {                               
                                        try 
                                        {
                                            if (stsEmpCategorySel != "" && stsEmpCategorySel.length() > 0){
                                            stsEmpCategorySel = stsEmpCategorySel.substring(0, stsEmpCategorySel.length()-1);
                                            }
                                            manualAttd.startTransfer(lDepartmentOid,sEmployeeName.trim(),dtSelectedFrom,dtSelectedTo,lSectionOid,sPayrolNum.trim(),stsEmpCategorySel,lDivisionOid); 
                                            //update by satrya 2012-08-19 penambahan trim
                                             //manualAttd.startTransfer(lDepartmentOid,sEmployeeName,dtSelectedFrom,dtSelectedTo,lSectionOid); 
                                                

                                        }
                                        catch (Exception e) 
                                        {
                                                System.out.println("\t Exception manualAttd.startAnalisa() = " + e);
                                        }
                                }
                                else if (commandManualReg.equalsIgnoreCase("Stop")) 
                                {
                                        try 
                                        {
                                                manualAttd.stopTransfer();
                                                
                                                
                                        }
                                        catch (Exception e) 
                                        {
                                                System.out.println("\t Exception svrmgrMachine.stopAnalisa() = " + e);
                                        }
                                }
                        }///comand run

                        if (manualAttd.getStatus())
                        {
                                //strStatusManualAttd = "Run";
                                strStatusManualAttd = "Run";
                                strStatusManualAttd = "Stop";
		
                        }
                       
                         else 
                        {
                               strStatusManualAttd = "Stop";
                               strStatusManualAttd = "Run";
                                                      
                         }
                       //  if(strStatusManualAttd.equalsIgnoreCase("Run") ){
                         
	
        }else
	{
		strMessage = "<div class=\"msginfo\">Selected start date or finish date should less than today ...</div>";	
	}	
                       
                       
            
                              
                         
                       
if(iCommand == Command.NONE){
    Date dtNow = new Date();
    selectedDateFrom = new Date(dtNow.getYear(), dtNow.getMonth(), (dtNow.getDate()-1));
    selectedDateTo = new Date(dtNow.getYear(), dtNow.getMonth(), (dtNow.getDate()-1));
}


if(iCommand == Command.SUBMIT){
    
	
}

if(iCommand == Command.DELETE)
{
       //System.out.println("Reset Data In and Out : periode ID="+periodId+" And empID"+sPayrolNum);
       PstEmpSchedule.resetScheduleDataFromDateToDate(selectDateFrom,selectDateTo,sEmpPayrolNum.trim(), sFullName.trim());
}

%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Manual Calculation Of Attendance Data</title>
<script language="JavaScript">
function deptChange() 
{
	//document.frmcheckabsence.command.value = "<//%=Command.GOTO%>";
	document.frmcheckabsence.hidden_goto_div.value = document.frmcheckabsence.DIVISION_OID.value;
	document.frmcheckabsence.hidden_goto_dept.value = document.frmcheckabsence.DEPARTMENT_OID.value;	
	document.frmcheckabsence.action = "attendance_manual_calculation.jsp";
	document.frmcheckabsence.submit();
}


function cmdCheck(cmd)
{  
  document.frmcheckabsence.commandManualReg.value = cmd;
  //document.frmcheckabsence.command.value="<%//= Command.SUBMIT %>";
  
  document.frmcheckabsence.action="attendance_manual_calculation.jsp";    
  document.frmcheckabsence.submit();  
}

function cmdResetData()
{  
  alert("This process will reset Data In an Data Out on Employee Schedule ");
  document.frmcheckabsence.command.value="<%= Command.DELETE %>";	  
  document.frmcheckabsence.action="attendance_manual_calculation.jsp";    
  document.frmcheckabsence.submit();  
}
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> </td>   
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../main/mnmain.jsp" %>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Application 
                  &gt; Manual Calculation of Attendance Data<!-- #EndEditable --> 
                  </strong></font> </td>
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmcheckabsence" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                       <input type="hidden" name="commandManualReg" value="">
                                      <input type="hidden" name="hidden_goto_div" value="<%=lDivisionOid%>">	
                                      <input type="hidden" name="hidden_goto_dept" value="<%=lDepartmentOid%>">	
                                        <!--  update by satrya 2012-09-13
                                                      untuk menghitung status saja
													  update by satrya 2012-09-18
                                        -->
					
						<table>
					<tr> 
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                        </tr>
                                </table>
										<!-- End menghitung status-->
						              <table width="100%" border="0" cellpadding="2" cellspacing="2" bgcolor="#00FF00">
                                          <tr><td></td></tr>
                                        <tr> 
                                                <td colspan="3" valign="top"><b>. : : PROCESS ANALYZE DATA IN, OUT AND STATUS : : .</b></td>
                                                <td colspan="3" valign="top">&nbsp;</td>
                                                <td colspan="3" valign="top">&nbsp;</td>
                                 		</tr>
										<tr> 
                                          <td colspan="3" valign="top">This process 
                                            is used to :</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top">&nbsp;&nbsp;&nbsp;- 
                                            Prepare employee's attendance data 
                                            for &quot;Absence&quot; and &quot;Lateness&quot; 
                                            calculation process.</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top">&nbsp;&nbsp;&nbsp;- 
                                            Analyze employee's absence and update 
                                            their status to be &quot;ABSENCE&quot;.</td>
                                        </tr>                                        
                                        <tr> 
                                          <td colspan="3" valign="top">&nbsp;&nbsp;&nbsp;- 
                                            Analyze employee's lateness and update 
                                            their status to be &quot;LATE&quot;. 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top">&nbsp;</td>
                                        </tr>
                                        <!-- update by satrya 2012-08-01 -->
                                        <tr> 
                                          <td width="13%" valign="top">Payrol 
                                            Numb </td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"> <input type="text" name="PAYROL_NUMBER"  value="<%=!manualAttd.getStatus() ? sPayrolNum : manualAttd.getAssistant().getPayrolNum() %>" class="elemenForm" size="40"> 
                                         
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">Employee 
                                            Name </td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"> <input type="text" name="EMPLOYEE_NAME"  value="<%=!manualAttd.getStatus() ? sEmployeeName : manualAttd.getAssistant().getFullName() %>" class="elemenForm" size="40"> 
                                         
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">Department</td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"> 
                                            <% 
											Vector div_value = new Vector(1,1);
											Vector div_key = new Vector(1,1);        
											div_value.add("0");
											div_key.add("All Div ...");                                                          
											 Vector  listDiv = PstDivision.list(0, 0, "", " DIVISION ");                                                        
											for (int i = 0; i < listDiv.size(); i++) 
											{
													Division div = (Division) listDiv.get(i);
													div_key.add(div.getDivision());
													div_value.add(String.valueOf(div.getOID()));
											}
											%>
                                            <%= ControlCombo.draw("DIVISION_OID","formElemen",null, ""+(!manualAttd.getStatus() ? lDivisionOid : manualAttd.getAssistant().getOidDivision() ), div_value, div_key, "onchange=\"javascript:deptChange();\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">Department</td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"> 
                                            <% 
											Vector dept_value = new Vector(1,1);
											Vector dept_key = new Vector(1,1);        
											dept_value.add("0");
											dept_key.add("All Department ...");  
                                                                                        String where = ""; 
                                                                                        if (lDivisionOid > 0){
                                                                                            where = " hr_department."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+" = "+lDivisionOid;
                                                                                        } 
                                                                                        Vector  listDept = PstDepartment.list(0, 0, where, " DEPARTMENT ");                                                        
											for (int i = 0; i < listDept.size(); i++) 
											{
													Department dept = (Department) listDept.get(i);
													dept_key.add(dept.getDepartment());
													dept_value.add(String.valueOf(dept.getOID()));
											}
											%>
                                            <%= ControlCombo.draw("DEPARTMENT_OID","formElemen",null, ""+(!manualAttd.getStatus() ? lDepartmentOid : manualAttd.getAssistant().getOidDepartement() ), dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %> </td>
                                        </tr>
                                        <%
										if(lDepartmentOid != 0)
										{
										%>
                                        <tr> 
                                          <td width="13%" valign="top">Section</td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"> 
                                            <% 
											String whereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + lDepartmentOid;
											String orderSec = PstSection.fieldNames[PstSection.FLD_SECTION];											
											Vector sec_value = new Vector(1,1);
											Vector sec_key = new Vector(1,1); 
											sec_value.add("0");
											sec_key.add("All Section ...");
											Vector listSec = PstSection.list(0, 0, whereSec, orderSec);
											for (int i = 0; i < listSec.size(); i++) 
											{
													Section sec = (Section) listSec.get(i);
													sec_key.add(sec.getSection());
													sec_value.add(String.valueOf(sec.getOID()));
											}
											%>
                                            <%= ControlCombo.draw("SECTION_OID","formElemen",null, ""+(manualAttd.getAssistant().getOidsection() !=0 ?manualAttd.getAssistant().getOidsection() :lSectionOid), sec_value, sec_key, "") %></td>
                                        </tr>
                                        <%
					}
					%>
                                        <tr> 
                                          <td width="13%" valign="top">Selected 
                                            Date </td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"><%=ControlDate.drawDateWithStyle("check_date_start",mDateFrom, 0, installInterval, "formElemen", "")%> 
                                            to <%=ControlDate.drawDateWithStyle("check_date_finish",mDateTo, 0, installInterval, "formElemen", "")%></td>
                                          <!--<td width="86%" valign="top"><//%=ControlDate.drawDateWithStyle("check_date_start", manualAttd.getAssistant().getFromDate()!=null ? manualAttd.getAssistant().getFromDate() : new Date(), 0, installInterval, "formElemen", "")%> 
                                            to <//%=ControlDate.drawDateWithStyle("check_date_finish",manualAttd.getAssistant().getToDate() !=null ? manualAttd.getAssistant().getToDate() : new Date(), 0, installInterval, "formElemen", "")%></td>-->
                                        </tr>
                                        
                                        <tr>
                                        <td>Emp. Category </td>
                                        <td width="1%" valign="top">:</td>
                                        <td width="86%" valign="top">
                                            <table>
                                                <tr>
                                                    <td width="83%" colspan="4">
                                                        <table>
                                                            <%

                                                                int numCol = 5;
                                                                boolean createTr = false;
                                                                int numOfColCreated = 0;
                                                                Hashtable hashGetListEmpSel = new Hashtable();
                                                                if (stsEmpCategorySel != null && stsEmpCategorySel.length() > 0) {
                                                                    stsEmpCategorySel = stsEmpCategorySel.substring(0, stsEmpCategorySel.length() - 1);
                                                                    String whereClause = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " IN (" + stsEmpCategorySel + ")";
                                                                    hashGetListEmpSel = PstEmpCategory.getHashListEmpSchedule(0, 0, whereClause, "");
                                                                }
                                                                Vector vObjek = PstEmpCategory.listAll();
                                                                //String checked="unchecked"; 
                                                                long oidEmpCat = 0;
                                                                for (int tc = 0; (vObjek != null) && (tc < vObjek.size()); tc++) {
                                                                    EmpCategory empCategory = (EmpCategory) vObjek.get(tc);
                                                                    oidEmpCat = 0;
                                                                    if (tc % numCol == 0) {
                                                                        out.println("<tr><td nowrap>");
                                                                        createTr = true;
                                                                        numOfColCreated = 1;
                                                                    } else {
                                                                        out.println("<td nowrap>");
                                                                        numOfColCreated = 1 + numOfColCreated;
                                                                    }
                                                                    if (empCategory != null) {

                                                                        if (hashGetListEmpSel != null && hashGetListEmpSel.size() > 0 && hashGetListEmpSel.get(empCategory.getOID()) != null) {
                                                                            EmpCategory empCategoryHas = (EmpCategory) hashGetListEmpSel.get(empCategory.getOID());
                                                                            oidEmpCat = empCategoryHas.getOID();

                                                                        }

                                                                        if (oidEmpCat != 0) {
                                                            %>
                                                            <input type="checkbox" name="<%="EMP_CAT_" + tc%>"  checked="checked" value="<%=empCategory.getOID()%>"  />
                                                            <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;
                                                            <%} else {%>
                                                            <input type="checkbox" name="<%="EMP_CAT_" + tc%>"  value="<%=empCategory.getOID()%>"  />
                                                            <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;
                                                            <%}%>
                                                            <%
                                                                        if (numOfColCreated == numCol || (tc + 2) > vObjek.size()) {
                                                                            out.println("</td></tr>");
                                                                        } else {
                                                                            out.println("</td>");
                                                                        }
                                                                    }
                                                                }

                                                            %>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table></td>
                                        </tr>
                                        
                                        
                                        <tr> 
                                          <td width="13%" valign="top">&nbsp;</td>
                                          <td width="1%" valign="top">&nbsp;</td>
                                          <td width="86%" valign="top"> 
                                            <%if(privStart){%>
                                            <!--input type="button" name="btnStart" value="  Start  " onClick="javascript:cmdCheck()" class="formElemen" --> 
                                            <input type="button" name="btnStart" value="<%=strStatusManualAttd%>" onClick="javascript:cmdCheck('<%=strStatusManualAttd%>');"> Click this button to <%=strStatusManualAttd%> the Attendance Manual 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <%
										if(true)
										{
										%>
                                        <tr> 
                                          <td width="13%" valign="top">&nbsp;</td>
                                          <td width="1%" valign="top">&nbsp;</td>
                                          <td width="86%" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td valign="top">Process employee's attendance data </td>
                                          <td valign="top">&nbsp;</td>
                                          <td>
                                                <!--img alt=""  src="../../images/loading.gif" height="8" width="<//%=(manualAttd.getTotalRecordAssistant()==0?"":(""+manualAttd.getProcentTransferAssistant()*300/manualAttd.getTotalRecordAssistant())) %>" > <//%=(manualAttd.getTotalRecordAssistant()==0?"":(""+manualAttd.getProcentTransferAssistant()*100/manualAttd.getTotalRecordAssistant())) %>%  -->
                                                <img alt=""  src="<%=approot%>/images/loading.gif" height="10" width="<%=(manualAttd.getTotalRecordAssistant()==0?"":(""+manualAttd.getProcentTransferAssistant()*300/manualAttd.getTotalRecordAssistant())) %>" > <%=(manualAttd.getTotalRecordAssistant()==0?"":(""+manualAttd.getProcentTransferAssistant()*100/manualAttd.getTotalRecordAssistant())) %>%
                                               
                                          <BR>
                                          </td>
                                        </tr>
					<tr> 
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                          <td>Message : <%=manualAttd.getTransferAssistantManualMessage()%></td>
                                        </tr>
                                        <!-- update by satrya 2012-09-04 
                                             untuk message proses check absensi
                                        -->
					
                                         <tr> 
                                          <td valign="top">Analyze employee's presence status</td>
                                          <td valign="top">&nbsp;</td>
                                          <td>
                                                <img alt=""  src="<%=approot%>/images/loading.gif" height="10" width="<%=(manualAttd.getTotalRecordAssistantAbsence()==0?"":(""+manualAttd.getProcentTransferAssistantAbsence()*300/manualAttd.getTotalRecordAssistantAbsence())) %>" > <%=(manualAttd.getTotalRecordAssistantAbsence()==0?"":(""+manualAttd.getProcentTransferAssistantAbsence()*100/manualAttd.getTotalRecordAssistantAbsence())) %>%
                                               
                                          <BR>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                          <td>Message : <%=manualAttd.getCheckProcessAbsenceMessage()%></td>
                                        </tr>
                                        
										
						</table>
						<table>
					<tr> 
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                        </tr>
                         </table>							<!-- table reset -->
                         <!-- update by satrya 2012-08-01 -->
                         <table  width="100%" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#FFFF99">
                             <tr><td></td></tr> 
                                        <tr> 
                                            <td colspan="3" valign="top"><b>. : : RESET DATA  IN, OUT AND STATUS : : .</b></td>
                                            <td colspan="3" valign="top">&nbsp;</td>
                                            <td colspan="3" valign="top">&nbsp;</td>
                                        </tr>	
                                        <tr> 
                                          <td colspan="3" valign="top">This process 
                                            is used to :</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top">&nbsp;&nbsp;&nbsp;- 
                                            Reset Data In and Data Out and Reset 
                                            Status of Selected Employee and Period</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top">&nbsp;&nbsp;&nbsp;- 
                                            After this process you should process 
                                            Re-Update the Data In and Out by selecting 
                                            option below</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                        <td width="13%" valign="top">Payrol Numb </td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"> <input type="text" name="PAYROL_NUMBER_EMP"  value="<%= sEmpPayrolNum %>" class="elemenForm" size="40"> 
                                         
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">Employee Name </td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"> <input type="text" name="EMPLOYEE_FULL_NAME"  value="<%=sFullName%>" class="elemenForm" size="40"> 
                                         
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">Selected 
                                            Date </td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"><%=ControlDate.drawDateWithStyle("check_date_from", selectDateFrom!=null ? selectDateFrom : new Date(), 0, installInterval, "formElemen", "")%> 
                                            to <%=ControlDate.drawDateWithStyle("check_date_to",selectDateTo !=null ? selectDateTo : new Date(), 0, installInterval, "formElemen", "")%></td>
                                        </tr>
                                        <tr> 
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top"> 
                                            <%if(privStart){%>
                                            <input type="button" name="btnStart" value="  Start  " onClick="javascript:cmdResetData()" class="formElemen"> 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                        </tr>
                                          <tr>
					  <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
					  </tr>
                                        <tr> 
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                          <td valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top"><%=strMessage%> 
                                          </td>
                                        </tr>
                                        <%
										}
										%>
                                      </table>
                                      </form>
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
  
    <!-- #BeginEditable "footer" --> 
  <%if(false){%>
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
         
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
       <%}%>
       <%if(manualAttd.getStatus()){%>
        <script language="JavaScript">

//enter refresh time in "minutes:seconds" Minutes should range from 0 to inifinity. Seconds should range from 0 to 59
var limit="0:08"
if (document.images){
var parselimit=limit.split(":")
parselimit=parselimit[0]*60+parselimit[1]*1
}
function beginrefresh(){
if (!document.images)
return

if (parselimit==1)
window.location = window.location.href //agar tidak memunculkan pesan confirmasi
else{
parselimit-=1
//curmin=Math.floor(parselimit/60)
//cursec=parselimit%60
//if (curmin!=0)
//curtime=curmin+" minutes and "+cursec+" seconds left until page refresh!"

setTimeout("beginrefresh()",100)
}
}

window.onload=beginrefresh
//-->
</script>
    <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
