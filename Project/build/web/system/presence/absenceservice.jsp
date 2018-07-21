<%@ page language = "java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "javax.comm.*" %>

<%@ page import = "com.dimata.gui.jsp.*"%>
<%@ page import = "com.dimata.util.*"%>
<%@ page import = "com.dimata.qdep.form.*"%>

<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.utility.service.presence.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.util.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  1;//AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_SERVICE_MANAGER); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%
response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "nocache");

int iCommand = FRMQueryString.requestCommand(request);
Date presenceDate = FRMQueryString.requestDate(request,"presenceDate");
AbsenceAnalyser absenceAnalyser = new AbsenceAnalyser();	
switch (iCommand)    
{
	case  Command.START :
		  if(presenceDate.before(new Date()))
		  {
			absenceAnalyser.setDate(presenceDate);
			absenceAnalyser.startService();  
		  }
		  break;

	case  Command.STOP :
		  Date dtx = new Date();
		  dtx.setDate(dtx.getDate()-1);
		  absenceAnalyser.setDate(dtx);
		  absenceAnalyser.stopService();  
		  break;
}	

boolean running = absenceAnalyser.getStatus();
String msg = "";
if(running)
{
	msg = "Running...";
}
else
{
	if(presenceDate.before(new Date()))
	{
		msg = "Stopped";
	}
	else
	{
		msg = "Date service must before date now...";
	}
}

/*
String strMessage = "";
if (privStart && iCommand==Command.SUBMIT) {
	AbsenceAnalyser absenceAnalyser = new AbsenceAnalyser();	
	System.out.println("\t[AbsenceAnalyser] starting ...");                
	absenceAnalyser.checkEmployeeAbsence(presenceDate);
	System.out.println("\t[AbsenceAnalyser] stopped ..."); 
	strMessage = "Analyze absence data done ...";	       
}
*/
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Absence Analyzer</title>
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
function cmdStop(){
  document.frmanalyzeabsence.command.value="<%=Command.STOP%>";
  document.frmanalyzeabsence.action = "absenceservice.jsp";
  document.frmanalyzeabsence.submit();  
}

function cmdStart(){  	 	  
  document.frmanalyzeabsence.command.value="<%=Command.START%>";
  document.frmanalyzeabsence.action = "absenceservice.jsp";
  document.frmanalyzeabsence.submit();
}

function cmdStartAnalyze() {
	document.frmanalyzeabsence.command.value = "<%=Command.SUBMIT%>";
	document.frmanalyzeabsence.action = "absenceservice.jsp";
	document.frmanalyzeabsence.submit();
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Presence &gt; Absence Analyzer<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frmanalyzeabsence" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="70%" border="0" cellspacing="1" cellpadding="1" align="center">
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="176"> 
                                            <div align ="right"><b>Status </b>&nbsp;&nbsp;</div>
                                          </td>
                                          <td colspan="2"> 
                                            <% if(running){%>
                                            <font color="#009900"><%=msg%></font> 
                                            <%}else{%>
                                            <font color="#FF0000"><%=msg%></font> 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="176"> 
                                            <div align ="right"><b>Date </b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                          </td>
                                          <td colspan="2"><%=ControlDate.drawDate("presenceDate", (presenceDate==null || iCommand==Command.NONE) ? new Date() : presenceDate, "formElemen", 1, -2)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="176">&nbsp;</td>
                                          <td colspan="2"> 
                                            <% String stopSts="";
											   String startSts="";
											if(running){ 					
											   startSts="disabled=\"true\"";
											   stopSts="";
											}else{
											   startSts="";
											   stopSts="disabled=\"true\"";
											}%>
                                            <%//if(hasExecutePriv){%>
                                            <input type="button" name="Button" value="  Start  " onClick="javascript:cmdStart()" class="formElemen" <%=startSts%>>
                                            <input type="button" name="Submit2" value="  Stop  " onClick="javascript:cmdStop()" class="formElemen" <%=stopSts%>>
                                            <%//}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <hr noshade size="1">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"><strong>Information:</strong></td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">&nbsp;&nbsp;If you click <font color="#0000FF">start</font> 
                                            button, the service will be running. 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">&nbsp;&nbsp;If you click <font color="#0000FF">stop</font> 
                                            button , the service will be stop.</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <hr size="1">
                                          </td>
                                        </tr>
                                        <tr align="right"> 
                                          <td colspan="3">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" align="right">&nbsp;</td>
                                        </tr>
                                      </table>									  									  
                                    </form>
                                	<% 
									} 
                                    else
                                    {
                                	%>
                                	<div align="center">You do not have sufficient privilege to access this page.</div>
                                	<% 
									} 
									%>
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
