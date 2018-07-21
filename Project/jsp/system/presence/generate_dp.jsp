<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.Vector"%>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ page import ="com.dimata.harisma.entity.attendance.*"%>
<%@ page import ="com.dimata.harisma.form.attendance.*"%>
<%@ page import ="com.dimata.harisma.entity.search.*"%>
<%@ page import ="com.dimata.harisma.form.search.*"%>
<%@ page import ="com.dimata.harisma.session.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>


<%
int iCommand = FRMQueryString.requestCommand(request);

SrcEmpSchedule srcEmpSchedule = new SrcEmpSchedule();
String strMessage = "";
if(iCommand == Command.LIST)
{  
	FrmSrcEmpSchedule frmSrcEmpSchedule = new FrmSrcEmpSchedule(request, srcEmpSchedule);  
	frmSrcEmpSchedule.requestEntityObject(srcEmpSchedule);  
	
	SessEmpSchedule sessEmpSchedule = new SessEmpSchedule();	
	Vector records = sessEmpSchedule.searchEmpSchedule(srcEmpSchedule, 0, 0);  
	if(records!=null && records.size()>0)
	{
		int maxEmpSchedule = records.size();
		CtrlDpStockManagement ctrlDpStockManagement = new CtrlDpStockManagement(request);		
		for(int i=0; i<maxEmpSchedule; i++)  
		{
			Vector vectResult = (Vector) records.get(i);
			EmpSchedule empSchedule = (EmpSchedule) vectResult.get(0);    
			ctrlDpStockManagement.generateDp(empSchedule, empSchedule.getPeriodId(), empSchedule.getEmployeeId());
		}
		
		strMessage = "Generate DP process done ...";
	}
	else
	{
		strMessage = "No DP generated ...";  
	}	
}			
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<script language="JavaScript">
<!--
function generateDp(){	 
	document.frdp.command.value="<%=Command.LIST%>";
	document.frdp.action="generate_dp.jsp";
	document.frdp.submit();
}
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> <!-- #EndEditable --> 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Generate Day Off Payment<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frdp" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
										
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2" >
                                        <tr> 
                                          <td width="10%"> 
                                            <div align="left">Name</div>
                                          </td>
                                          <td width="90%"> 
                                            <input type="text" name="<%=FrmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_EMPLOYEE] %>"  value="<%= srcEmpSchedule.getEmployee() %>" class="elemenForm" size="40"> 
                                          </td>
                                          <script language="javascript">
												document.frdp.<%=FrmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_EMPLOYEE]%>.focus();  
											</script>
                                        </tr>
                                        <tr> 
                                          <td width="10%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="90%"> 
                                            <% 
											Vector dept_value = new Vector(1,1);
											Vector dept_key = new Vector(1,1);        
											Vector listDept = new Vector(1,1);
											//dept_value.add("0");
											//dept_key.add("select ...");                                                          
											listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
											for (int i = 0; i < listDept.size(); i++) 
											{
												Department dept = (Department) listDept.get(i);
												dept_key.add(dept.getDepartment());
												dept_value.add(String.valueOf(dept.getOID()));
											}
											%>
                                            <%= ControlCombo.draw(FrmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcEmpSchedule.getDepartment(), dept_value, dept_key, "") %> </td>
                                        </tr>
										
										<!--
                                        <tr> 
                                          <td width="10%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                          </td>
                                          <td width="90%"> 
                                            <% 
											/*
											Vector sec_value = new Vector(1,1);
											Vector sec_key = new Vector(1,1); 
											sec_value.add("0");
											sec_key.add("select ...");                                                              
											Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");                                                          
											for (int i = 0; i < listSec.size(); i++) 
											{
												Section sec = (Section) listSec.get(i);
												sec_key.add(sec.getSection());
												sec_value.add(String.valueOf(sec.getOID()));
											}
											*/
											%>
                                            <%//= ControlCombo.draw(FrmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_SECTION],"formElemen",null, "" + srcEmpSchedule.getSection(), sec_value, sec_key, "") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="10%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                          </td>
                                          <td width="90%"> 
                                            <% 
											/*
											Vector pos_value = new Vector(1,1);
											Vector pos_key = new Vector(1,1); 
											pos_value.add("0");
											pos_key.add("select ...");                                                       
											Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
											for (int i = 0; i < listPos.size(); i++) 
											{
												Position pos = (Position) listPos.get(i);
												pos_key.add(pos.getPosition());
												pos_value.add(String.valueOf(pos.getOID()));
											}
											*/
											%>
                                            <%//= ControlCombo.draw(FrmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_POSITION],"formElemen",null, "" + srcEmpSchedule.getPosition(), pos_value, pos_key, "") %> </td>
                                        </tr>
										-->
                                        <tr> 
                                          <td width="10%"> 
                                            <div align="left">Period</div>
                                          </td>
                                          <td width="90%"> 
                                            <%
											Vector period_value = new Vector(1,1);
											Vector period_key = new Vector(1,1);
											String selectValuePeriod = (String)srcEmpSchedule.getPeriod();
											//period_value.add("0");
											//period_key.add("select ...");
											Vector listPeriod = new Vector(1,1);
											listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
											for (int i = 0; i < listPeriod.size(); i++) {
												Period period = (Period) listPeriod.get(i);
												period_key.add(period.getPeriod());
												period_value.add(String.valueOf(period.getOID()));
											}
											%>
                                            <%= ControlCombo.draw(FrmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_PERIOD], null, selectValuePeriod, period_value, period_key, "") %> </td>
                                        </tr>										
									    <%
									    if(strMessage!=null && strMessage.length()>0)
									    {
										%>
                                        <tr> 
                                          <td width="10%">&nbsp;</td>
                                          <td width="90%">
										  <% 
											  out.println("<i>"+strMessage+"</i>");
										  %>
										  </td>
                                        </tr>										
										<%
										}
										%> 										
                                        <tr> 
                                          <td width="10%">&nbsp;</td>
                                          <td width="90%">
											<table width="18%" border="0" cellspacing="1" cellpadding="1">
                                              <tr> 
                                                <td width="17%"><a href="javascript:generateDp()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0" alt="Generate DP"></a></td>
                                                <td width="83%"><b><a href="javascript:generateDp()" class="command">Generate 
                                                  DP</a></b> </td>
                                              </tr>
                                            </table>										  
										  </td>
                                        </tr>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>

<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
