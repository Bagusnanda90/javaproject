<%@ page language = "java" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.AccessTMA" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.utility.machine.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_ADMIN, AppObjInfo.G2_ADMIN_TIMEKEEPING, AppObjInfo.OBJ_ADMIN_TIMEKEEPING_RESET); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
    privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%
    String reset = request.getParameter("cb_reset");
    String machineNumberReset = FRMQueryString.requestString(request, "hide_number_machine");
    Vector v = new Vector();
    Vector v2 = new Vector();	
    v = null;
    v2 = null;	
    String machineNumber = "01";
    String machineNumbers;
    Vector vMsg = new Vector();
    boolean succeed = false;
    if (privStart) {
        if (reset != null) {
            try {
				machineNumber = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
				StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
			  //  machineNumbers = new String[strTokenizer.countTokens()];
				int count = 0;
				while(strTokenizer.hasMoreTokens()){
					count++;
					machineNumbers = strTokenizer.nextToken();
					System.out.println("ABSEN MACHINE :::::::::: "+machineNumbers);
					String msg = "";
					if(!(machineNumbers.equals("")) && machineNumbers.length()>0){
						I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbers);
						if(i_Machine.processCheckMachine()){
							if(i_Machine.processReset()){
								succeed = true;
                                msg = "Reset for Machine-"+i_Machine.getMachineNumber()+" : SUCCESS";
							}else{
								succeed = false;
								msg = "Reset for Machine-"+i_Machine.getMachineNumber()+" : REJECTED";
							}
						}else{
							msg = "<font color='red'>Unable to reset for Machine-"+i_Machine.getMachineNumber()+", please cek connection</font>";
						}
						vMsg.add(msg);
						
					}
				}		
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
<title>HRIS - Reset Timekeeping Machine</title>
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
			  <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Timekeeping &gt; Reset Machine<!-- #EndEditable --> 
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
                                            MACHINE <%=machineNumberReset%></b> |===---</div>
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td>&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td> 
                                          <%
                                                if ((reset != null)) {
                                          %>
                                                <%
													for(int i=0;i<vMsg.size();i++){
														String strMsgReport = (String)vMsg.get(i);
														out.println("<br> --&gt;"+strMsgReport);
													}
                                                                                                        
												%>
												<br><br><br>
                                          <%
                                                } else 
                                                {
                                           %>
                                          <div align="center">Failed...! <br>
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
                              <a href="reset.jsp">Back To Reset Menu</a>
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
