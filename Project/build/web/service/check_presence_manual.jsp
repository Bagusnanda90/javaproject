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

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>  
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
String sEmployeeName = FRMQueryString.requestString(request, "EMPLOYEE_NAME");
long lDepartmentOid = FRMQueryString.requestLong(request, "DEPARTMENT_OID");
long lSectionOid = FRMQueryString.requestLong(request, "SECTION_OID");

String strMessage = "";
if(iCommand == Command.SUBMIT)
{
	PresenceAnalyser objPresenceAnalyser = new PresenceAnalyser();
	objPresenceAnalyser.analyzeEmpPresenceData(lDepartmentOid,lSectionOid,sEmployeeName);		
	strMessage = "<div class=\"msginfo\">Checking presence process done ...</div>";					
}
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Check Presence Manual</title>
<script language="JavaScript">
function deptChange() 
{
	document.frmcheckpresence.command.value = "<%=Command.GOTO%>";
	document.frmcheckpresence.hidden_goto_dept.value = document.frmcheckpresence.DEPARTMENT_OID.value;	
	document.frmcheckpresence.action = "check_presence_manual.jsp";
	document.frmcheckpresence.submit();
}

function cmdCheck()
{
  document.frmcheckpresence.command.value="<%= Command.SUBMIT %>";	  
  document.frmcheckpresence.action="check_presence_manual.jsp";    
  document.frmcheckpresence.submit();  
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
                  &gt; Check Presence Manually<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frmcheckpresence" method="post" action="">
                                      <input type="hidden" name="command" value="">
									  <input type="hidden" name="hidden_goto_dept" value="<%=lDepartmentOid%>">									  
																			
                                      <table width="100%" border="0" cellpadding="2" cellspacing="2">
                                        <tr> 
                                          <td colspan="3" valign="top"><b>. : 
                                            : CHECKING PRESENCE MANUALLY : : .</b></td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" valign="top">This process 
                                            is used to prepare employee's attendance 
                                            data for the next process i.e.: Absence 
                                            Service, Lateness Service and Leave 
                                            Stock Service. </td>
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
                                          <td width="13%" valign="top">&nbsp; </td>
                                          <td width="1%" valign="top">&nbsp;</td>
                                          <td width="86%" valign="top"> 
                                            <input type="button" name="btnStart" value="  Start  " onClick="javascript:cmdCheck()" class="formElemen">
                                          </td>
                                        </tr>
                                        <%
										if(strMessage!=null && strMessage.length()>0)
										{
										%>
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
