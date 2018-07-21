
<%@ page language = "java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "javax.comm.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.utility.service.presence.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_SERVICE_MANAGER); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%!
    //public static ServiceManagerTMA svrmgrTMA = new ServiceManagerTMA();
%>

<%
response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "nocache");

String TKEEPING_TYPE = PstSystemProperty.getValueByName("TIMEKEEPING_TYPE");
String strStatusSvrmgrTMA = "";
String strButtonStatusTMA = "";
String strStatusSvrmgrPresence = "";
String strButtonStatusPresence = "";
String strStatusPortStatus = "";
String strButtonPortStatus = "";
String sTime01 = "";
String sTime02 = "";

if(TKEEPING_TYPE.equalsIgnoreCase("TMA")){

	if (privStart) 
	{
		ServiceManagerTMA svrmgrTMA = new ServiceManagerTMA();
		String iCommandTMA = request.getParameter("iCommandTMA");
		if (iCommandTMA != null) 
		{
			if (iCommandTMA.equalsIgnoreCase("Run")) 
			{
				try 
				{
					svrmgrTMA.startWatcherTMA();
				}
				catch (Exception e) 
				{
					System.out.println("\t Exception svrmgrTMA.startWatcherTMA() = " + e);
				}
			}
			else if (iCommandTMA.equalsIgnoreCase("Stop")) 
			{
				try 
				{
					svrmgrTMA.stopWatcherTMA();
				}
				catch (Exception e) 
				{
					System.out.println("\t Exception svrmgrTMA.stopWatcherTMA() = " + e);
				}
			}
		}
		
		if (svrmgrTMA.getStatus()) 
		{
			strStatusSvrmgrTMA = "Run";
			strButtonStatusTMA = "Stop";
		}
		else 
		{
			strStatusSvrmgrTMA = "Stop";
			strButtonStatusTMA = "Run";
		}
	}
}
//cardnetic mechine
else{

	if (privStart) 
	{
		ServiceManagerCardnetic svrmgrCardnetic = new ServiceManagerCardnetic();
		String iCommandCardnetic = request.getParameter("iCommandTMA");
		if (iCommandCardnetic != null) 
		{
			if (iCommandCardnetic.equalsIgnoreCase("Run")) 
			{
				try 
				{
					svrmgrCardnetic.startWatcherCardnetic();
				}
				catch (Exception e) 
				{
					System.out.println("\t Exception svrmgrCardnetic.startWatcherCardnetic() = " + e);
				}
			}
			else if (iCommandCardnetic.equalsIgnoreCase("Stop")) 
			{
				try 
				{
					svrmgrCardnetic.stopWatcherCardnetic();
				}
				catch (Exception e) 
				{
					System.out.println("\t Exception svrmgrCardnetic.stopWatcherCardnetic() = " + e);
				}
			}
		}
		
		if (svrmgrCardnetic.getStatus()) 
		{
			strStatusSvrmgrTMA = "Run";
			strButtonStatusTMA = "Stop";
		}
		else 
		{
			strStatusSvrmgrTMA = "Stop";
			strButtonStatusTMA = "Run";
		}
	}	
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Scheduler</title>
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
    function hideObjectForEmployee(){    
    } 

    function hideObjectForLockers(){ 
    }

    function hideObjectForCanteen(){
    }

    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }

    function cmdSetStatusTMA(cmd) {
        document.frmsvcmgr.iCommandTMA.value = cmd;
        document.frmsvcmgr.action = "svcmgr.jsp";
        document.frmsvcmgr.submit();
    }

    function cmdSetStatusPresence(cmd) {
        document.frmsvcmgr.iCommandPresence.value = cmd;
        document.frmsvcmgr.action = "svcmgr.jsp";
        document.frmsvcmgr.submit();
    }

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Timekeeping &gt; Service Manager<!-- #EndEditable --> 
            </strong></font>
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                <% if (privStart) { %>
                                    <form name="frmsvcmgr" method="post" action="">
                                      <input type="hidden" name="iPortStatus" value="">
                                      <input type="hidden" name="iCommandTMA" value="">
                                      <input type="hidden" name="iCommandPresence" value="">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td> 
                                            <div align="left">
                                            Status of Service Manager for TMA is <b><%=strStatusSvrmgrTMA%></b>.<br>
                                            <input type="button" name="btnStatusTMA" value="<%=strButtonStatusTMA%>" onclick="javascript:cmdSetStatusTMA('<%=strButtonStatusTMA%>');"> Click this button to <%=strButtonStatusTMA%> the TMA Service Manager. 
                                            </div>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                <% } 
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% } %>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
