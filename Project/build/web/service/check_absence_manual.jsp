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
<%@ page import = "com.dimata.harisma.utility.service.leavedp.*" %>
<%@ page import = "com.dimata.harisma.utility.service.backupdb.*" %>
<%@ page import = "com.dimata.harisma.entity.service.*" %>
<%@ page import = "com.dimata.harisma.form.service.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_MANUAL_CHECKING, AppObjInfo.OBJ_MANUAL_CHECKING_ABSENTEEISM); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
String sEmployeeName = FRMQueryString.requestString(request, "EMPLOYEE_NAME");
long lDepartmentOid = FRMQueryString.requestLong(request, "DEPARTMENT_OID");
long lSectionOid = FRMQueryString.requestLong(request, "SECTION_OID");
Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
if(iCommand == Command.NONE)
{
	Date dtNow = new Date();
	selectedDateFrom = new Date(dtNow.getYear(), dtNow.getMonth(), (dtNow.getDate()-1));
	selectedDateTo = new Date(dtNow.getYear(), dtNow.getMonth(), (dtNow.getDate()-1));	
}

String strMessage = "";
if(iCommand == Command.SUBMIT)
{
	Date dtSelectedFrom = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), selectedDateFrom.getDate());
	Date dtSelectedTo = new Date(selectedDateTo.getYear(), selectedDateTo.getMonth(), selectedDateTo.getDate());	
	Date nowDate = new Date();		
	Date dtNow = new Date(nowDate.getYear(), nowDate.getMonth(), nowDate.getDate());	
	
	if( (dtSelectedFrom.getTime() < dtNow.getTime()) && (dtSelectedTo.getTime() < dtNow.getTime()) )
	{	
		long diffStartToFinish = dtSelectedTo.getTime() - dtSelectedFrom.getTime();
		if(diffStartToFinish >= 0)
		{
			int itDate = Integer.parseInt(String.valueOf(diffStartToFinish/86400000));
			for(int i=0; i<=itDate; i++)
			{				
				Date selectedDate = new Date(dtSelectedFrom.getYear(), dtSelectedFrom.getMonth(), (dtSelectedFrom.getDate()+i));
				AbsenceAnalyser absenceAnalyser = new AbsenceAnalyser();
				absenceAnalyser.checkEmployeeAbsence(selectedDate,lDepartmentOid,lSectionOid,sEmployeeName);	
				System.out.println("Process check absence on : " + selectedDate);
			}		
			strMessage = "<div class=\"msginfo\">Checking absence process done ...</div>";					
		}
		else
		{
			strMessage = "<div class=\"msginfo\">Selected start date should less than or equal to finish date ...</div>";				
		}
	}
	else
	{
		strMessage = "<div class=\"msginfo\">Selected start date or finish date should less than today ...</div>";	
	}		
}
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Check Absence Manual</title>
<script language="JavaScript">
function deptChange() 
{
	document.frmcheckabsence.command.value = "<%=Command.GOTO%>";
	document.frmcheckabsence.hidden_goto_dept.value = document.frmcheckabsence.DEPARTMENT_OID.value;	
	document.frmcheckabsence.action = "check_absence_manual.jsp";
	document.frmcheckabsence.submit();
}

function cmdCheck()
{
  document.frmcheckabsence.command.value="<%= Command.SUBMIT %>";	  
  document.frmcheckabsence.action="check_absence_manual.jsp";    
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Application 
                  &gt; Check Absence Manually<!-- #EndEditable --> </strong></font> 
                </td>
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
                                    <form name="frmcheckabsence" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="hidden_goto_dept" value="<%=lDepartmentOid%>">									  
									  
                                      <table width="100%" border="0" cellpadding="2" cellspacing="2">
                                        <tr> 
                                          <td colspan="3" valign="top"><b>. : 
                                            : CHECKING ABSENCE MANUALLY : : .</b></td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top">This process 
                                            is used to analyze employee's absence 
                                            and update their status to be &quot;ABSENCE&quot;.</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top">This process 
                                            also checks leave stock (DP, AL and 
                                            LL) taking process.</td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">&nbsp;</td>
                                          <td width="1%" valign="top">&nbsp;</td>
                                          <td width="86%" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">Employee 
                                            Name </td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"> 
                                            <input type="text" name="EMPLOYEE_NAME"  value="<%=sEmployeeName%>" class="elemenForm" size="40">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"> 
                                            <% 
											Vector dept_value = new Vector(1,1);
											Vector dept_key = new Vector(1,1);        
											dept_value.add("0");
											dept_key.add("All Department ...");                                                          
											Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
											for (int i = 0; i < listDept.size(); i++) 
											{
													Department dept = (Department) listDept.get(i);
													dept_key.add(dept.getDepartment());
													dept_value.add(String.valueOf(dept.getOID()));
											}
											%>
                                            <%= ControlCombo.draw("DEPARTMENT_OID","formElemen",null, ""+lDepartmentOid, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %></td>
                                        </tr>
										
										<%
										if(lDepartmentOid != 0)
										{
										%>
                                        <tr> 
                                          <td width="13%" valign="top"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
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
                                            <%= ControlCombo.draw("SECTION_OID","formElemen",null, ""+lSectionOid, sec_value, sec_key, "") %></td>
                                        </tr>
										<%
										}
										%>
										
                                        <tr> 
                                          <td width="13%" valign="top">Selected 
                                            Date </td>
                                          <td width="1%" valign="top">:</td>
                                          <td width="86%" valign="top"><%=ControlDate.drawDateWithStyle("check_date_start", selectedDateFrom, 0, installInterval, "formElemen", "")%> to <%=ControlDate.drawDateWithStyle("check_date_finish", selectedDateTo, 0, installInterval, "formElemen", "")%></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%" valign="top">&nbsp;</td>
                                          <td width="1%" valign="top">&nbsp;</td>
                                          <td width="86%" valign="top"> 
                                            <%if(privStart){%>
                                            <input type="button" name="btnStart" value="  Start  " onClick="javascript:cmdCheck()" class="formElemen">
                                            <%}%>
                                          </td>
                                        </tr>
                                        <%
										if(strMessage!=null && strMessage.length()>0)
										{
										%>
                                        <tr> 
                                          <td width="13%" valign="top">&nbsp;</td>
                                          <td width="1%" valign="top">&nbsp;</td>
                                          <td width="86%" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top"><%=strMessage%> </td>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
