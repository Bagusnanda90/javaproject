<%@ page language = "java" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.CanteenTMAAccess" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_ADMIN, AppObjInfo.G2_ADMIN_TIMEKEEPING, AppObjInfo.OBJ_ADMIN_TIMEKEEPING_RESET); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
    privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%
    String reset = request.getParameter("cb_reset");
    Vector v = new Vector();
    Vector v2 = new Vector();	
    v = null;
    v2 = null;	
    boolean succeed = false;
    if (privStart) {
        if (reset != null) {
            try {
                CanteenTMAAccess svc = new CanteenTMAAccess();
                v = svc.executeCommand(svc.RESET, "01", "");
                //System.out.println("\t...---> v = " + v.get(0));
                v2 = svc.executeCommand(svc.RESET, "02", "");
                //System.out.println("\t...---> v2 = " + v2.get(0));				
                succeed = true;
            }
            catch (Exception e) {
                System.err.println("Exception on resetting machine : " + e);
            }
            if (succeed == true) {
                PstEmployee.deleteBarcode();
            }
        }
    }
%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HRIS - Reset Canteen Machine</title>
<!-- #EndEditable --> 
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
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
			  <!-- #BeginEditable "contenttitle" -->Timekeeping >> Reset Machine<!-- #EndEditable --> 
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
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td> 
                                          <div align="center">---===| <b>RESET 
                                            MACHINE</b> |===---</div>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td>&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td> 
                                          <%
                                                if ((reset != null) && (succeed == true)) {
                                          %>
                                          <div align="center">Done...!<br>
                                            The Time Attendance machine has just 
                                            resetted. You can reload data now. 
                                          </div>
                                          <%
                                                } else 
                                                if ((reset != null) && (succeed == false))
                                                {
                                           %>
                                          <div align="center">Failed...!<br>
                                            Unable to reset the Time Attendance machine. It is likely that the Time Attendance machine is off.<br>
                                            Please <a href="reset.jsp">retry</a> and make sure to turn on the Time Attendance machine.
                                          </div>
                                          <%
                                                } else 
                                                {
                                           %>
                                          <div align="center">Failed...!<br>
                                            Unable to reset the Time Attendance machine.<br>
                                            Please <a href="reset.jsp">retry</a> and be sure to check the confirmation check box.
                                          </div>
                                          <% } %>
                                        </td>
                                      </tr>
                                    </table>
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
