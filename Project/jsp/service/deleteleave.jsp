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

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_DOWNLOAD_DATA); %>
<%@ include file = "../main/checkuser.jsp" %>

<%!
public static final int LEAVE_TYPE_DP = 0;
public static final int LEAVE_TYPE_AL = 1;
public static final int LEAVE_TYPE_LL = 2;
public static final String arrSLeaveType[] = {
	"Day Off Payment",
	"Annual Leave",
	"Long Leave"
};
%>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int iLeaveType = FRMQueryString.requestInt(request, "LEAVE_TYPE");
long lDepartementOid = FRMQueryString.requestLong(request, "DEPARTMENT_OID");

String strMessage = "";
if(iCommand == Command.SUBMIT)
{
	switch(iLeaveType)
	{
		case LEAVE_TYPE_DP : 		
			PstDpStockManagement objPstDpStockManagement = new PstDpStockManagement();
			objPstDpStockManagement.deleteDpStockPerDepartment(lDepartementOid);
			
		case LEAVE_TYPE_AL : 		
			PstAlStockManagement objPstAlStockManagement = new PstAlStockManagement();
			objPstAlStockManagement.deleteAlStockPerDepartment(lDepartementOid);

		case LEAVE_TYPE_LL : 		
			PstLLStockManagement objPstLLStockManagement = new PstLLStockManagement();
			objPstLLStockManagement.deleteLlStockPerDepartment(lDepartementOid);			
	}
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Delete Leave Stock Management</title>
<script language="JavaScript">
<!--
function deleteDp(){	 
	document.frdeletedp.command.value="<%=Command.SUBMIT%>";
	document.frdeletedp.action="deleteleave.jsp";
	document.frdeletedp.submit();
}
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> <!-- #EndEditable --> 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Presence 
                  &gt; Delete Leave Stock Manually<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frdeletedp" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td height="20"><b>. : : DELETE LEAVE 
                                            STOCK (DP, AL dan LL) MANUALLY : : 
                                            .</b></td>
                                        </tr>
                                        <tr> 
                                          <td>This process will delete DP, AL 
                                            or LL stock per employee on selected 
                                            department.</td>
                                        </tr>
                                        <tr>
                                          <td><b>Leave Type&nbsp;&nbsp;: </b>
                                            <% 
											Vector leave_value = new Vector(1,1);
											Vector leave_key = new Vector(1,1);        
											Vector listLeave = new Vector(1,1);

											leave_key.add(arrSLeaveType[LEAVE_TYPE_DP]);
											leave_value.add(String.valueOf(LEAVE_TYPE_DP));

											leave_key.add(arrSLeaveType[LEAVE_TYPE_AL]);
											leave_value.add(String.valueOf(LEAVE_TYPE_AL));

											leave_key.add(arrSLeaveType[LEAVE_TYPE_LL]);
											leave_value.add(String.valueOf(LEAVE_TYPE_LL));

											out.println(ControlCombo.draw("LEAVE_TYPE","formElemen",null,""+iLeaveType, leave_value, leave_key, ""));
											%>										  
										  </td>
                                        </tr>
                                        <tr> 
                                          <td><b>Department&nbsp;: </b> 
                                            <% 
											Vector dept_value = new Vector(1,1);
											Vector dept_key = new Vector(1,1);        
											Vector listDept = new Vector(1,1);
											listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");																											
											for (int i = 0; i < listDept.size(); i++) 
											{
												Department dept = (Department) listDept.get(i);
												dept_key.add(dept.getDepartment());
												dept_value.add(String.valueOf(dept.getOID()));
											}
											out.println(ControlCombo.draw("DEPARTMENT_OID","formElemen",null,""+lDepartementOid, dept_value, dept_key, ""));
											%>
                                            <input type="button" name="btnDelete" value="  Delete Leave  " onClick="javascript:deleteDp()" class="formElemen">
                                          </td>
                                        </tr>
                                        <%
										if(strMessage!=null && strMessage.length()>0)
										{
										%>
                                        <tr> 
                                          <td width="48%" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="48%" valign="top"><%=strMessage%> </td>
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
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>

<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
