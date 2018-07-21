
<%-- 
    Document   : upload_to_harisma
    Created on : Dec 8, 2009, 11:09:19 AM
    Author     : Tu Roy
--%>

<%@ page language = "java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "javax.comm.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.utility.machine.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_SERVICE_MANAGER); %>
<%@ include file = "../../main/checkuser.jsp" %>


<%
response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "nocache");

String strButtonStatusMachine = "";


	if (privStart) 
	{
		
		String iCommandMachine = request.getParameter("iCommandMachine");
		if (iCommandMachine != null) 
		{
			if (iCommandMachine.equalsIgnoreCase("Run")) 
			{
				try 
				{                                    
                                    String driver = "com.mysql.jdbc.Driver";
                                    String p_url = "jdbc:mysql://localhost:3306/mybase";
                                    String p_user = "root";
                                    String p_pass = "root";
                                    Vector dataMachine = SessMachineTransaction.takedatamachineTransaction(driver,p_url,p_user,p_pass);
                                    
                                    if(dataMachine != null ){
                                       SessMachineTransaction.updateStatusAbsence(driver,p_url,p_user,p_pass);  
                                    }
                                    
                                    int insertToMachine = SessMachineTransaction.insertDataToMachineTransaction(dataMachine);
                                    Vector anl = SessMachineTransaction.analisis();        
				    //svrmgrMachine.startWatcherMachine();
				}
				catch (Exception e) 
				{
					System.out.println("\t Exception svrmgrMachine.startWatcherMachine() = " + e);
				}
			}			
		}
		strButtonStatusMachine = "Run";
	}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Machine Service</title>
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

    function cmdSetStatusMachine(cmd) {
        document.frmsvcmgr.iCommandMachine.value = cmd;
        document.frmsvcmgr.action = "upload_to_harisma.jsp";
        document.frmsvcmgr.submit();
    }

    function cmdSetStatusPresence(cmd) {
        document.frmsvcmgr.iCommandPresence.value = cmd;
        document.frmsvcmgr.action = "upload_to_harisma.jsp";
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
                                      <input type="hidden" name="iCommandMachine" value="">
                                      <input type="hidden" name="iCommandPresence" value="">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td> 
                                            <div align="left">
                                            <b>Upload data from machine database</b>
                                            <br>
                                            <input type="button" name="btnStatusMachine" value="<%=strButtonStatusMachine%>" onClick="javascript:cmdSetStatusMachine('<%=strButtonStatusMachine%>');"> Click this button to download data from machine database. 
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

