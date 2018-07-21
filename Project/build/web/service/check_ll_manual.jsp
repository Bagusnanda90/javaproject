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
				Date selectedDate = new Date(dtSelectedFrom.getYear(), dtSelectedFrom.getMonth(), (dtSelectedFrom.getDate()+1+i));
				ServiceLLStock objServiceLLStock = new ServiceLLStock();
				objServiceLLStock.processLLStock(selectedDate);									
				System.out.println("Process check ll owning on : " + selectedDate);
			}		
			strMessage = "<div class=\"msginfo\">Checking LLs acquisition process done ...</div>";					
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
<title>HARISMA - Check Long Leave Manual</title>
<script language="JavaScript">
function cmdCheck()
{
  document.frmcheckll.command.value="<%= Command.SUBMIT %>";	  
  document.frmcheckll.action="check_ll_manual.jsp";    
  document.frmcheckll.submit();  
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
                  &gt; Check Long Leave Manually<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frmcheckll" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <table width="100%" border="0" cellpadding="2" cellspacing="2">
                                        <tr> 
                                          <td width="48%" valign="top"><b>. : 
                                            : CHECKING LL MANUALLY : : .</b></td>
                                        </tr>
                                        <tr> 
                                          <td width="48%" valign="top">This process 
                                            will check and update LLs data per 
                                            employee for <b><i>acquisition</i></b>.</td>
                                        </tr>
                                        <tr> 
                                          <td width="48%" valign="top"><b>Check 
                                            from <%=ControlDate.drawDateWithStyle("check_date_start", selectedDateFrom, 0, installInterval, "formElemen", "")%> to <%=ControlDate.drawDateWithStyle("check_date_finish", selectedDateTo, 0, installInterval, "formElemen", "")%></b> 
                                            <input type="button" name="btnStart" value="  Start  " onClick="javascript:cmdCheck()" class="formElemen">
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
                                          <td width="48%" valign="top"><%=strMessage%> 
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
