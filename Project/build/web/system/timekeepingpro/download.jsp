<%@page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.utility.machine.*" %>
<%@ page import = "com.dimata.util.net.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_DOWNLOAD_DATA); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%	
response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "nocache");  

int iCommand = FRMQueryString.requestCommand(request);

String resultRead = "";
String tma01Status = "";
String tma02Status = "";
int numOfTransaction = 0 ;

String machineNumber = "01";
String machineNumbers = "";

boolean succeed01 = false;
boolean succeed02 = false;
Vector vData = new Vector();
Vector vMsg = new Vector();
String msg = "";
%>

<%
if (privStart) 
{
	if(iCommand == Command.GET){
    machineNumber = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
    StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
  //  machineNumbers = new String[strTokenizer.countTokens()];
    int count = 0;
    while(strTokenizer.hasMoreTokens()){
		count++;
        machineNumbers = strTokenizer.nextToken();
        System.out.println("ABSEN MACHINE :::::::::: "+machineNumbers);
        if(!(machineNumbers.equals("")) && machineNumbers.length()>0){
            I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbers);
            if(i_Machine.processCheckMachine()){
                vData = i_Machine.processDownloadTransaction();
              int countVal = 0;
            /*    for(int i=0;i<vData.size();i++){
                        Vector vTemp = (Vector)vData.get(i);
                        String strVal = (String)vTemp.get(1);
                        if(strVal.equals("true")){
                                countVal++;
                        }
                } */
                Vector vTransaction = new Vector(1,1);
                String strInvalidData = "";
                int iInvalisData = 0;
                try{
                    vTransaction = (Vector)vData.get(0);
                    strInvalidData = (String)vData.get(1);
                    iInvalisData = Integer.parseInt(strInvalidData);
                }catch(Exception ex){}
                msg = (vTransaction.size()+iInvalisData)+" transaction(s) downloaded from Machine-"+i_Machine.getMachineNumber();
                if(vTransaction.size()>0){
                    msg += "<br>-->  Data transactions download     : "+String.valueOf(vTransaction.size());
                    msg += "<br>-->  Not Valid data download : "+strInvalidData;
                }
            }else{
                msg = "<font color='red'>Unable to download from Machine-"+i_Machine.getMachineNumber()+"</font>";
            }
            vMsg.add(msg);
            
        }
    }
 //	iCommand = Command.SAVE;
    
	}
        
        if(iCommand == Command.START){
            SessMachineTransaction.resetAllTransactions();
            SessMachineTransaction.analistPresentAll();
        }
        
}


%>


<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Download</title>
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

    function cmdDownload() {
        document.frmDownload.command.value = "<%=String.valueOf(Command.GET)%>";
		document.frmDownload.action = "download.jsp";
		document.frmDownload.submit();
    }
    
    function cmdReprocess() {
        document.frmDownload.command.value = "<%=String.valueOf(Command.START)%>";
		document.frmDownload.action = "download.jsp";
		document.frmDownload.submit();
    }
    
    function cmdDefault(){
        document.frmDownload.command.value = "";
		document.frmDownload.action = "download.jsp";
		document.frmDownload.submit();
    }
    
    function cmdWriteData(text){
        document.getElementById('resultTxt').value = text;
		timerDM = setTimeout("",2000);
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
                  &gt; Timekeeping &gt; Download Data<!-- #EndEditable --> 
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
									
                                    <form name="frmDownload" method="post">
										<input type="hidden" name="command">							
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                           <%
										   	if(iCommand == Command.GET){
												%>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                        <tr> 
                                          <td width="35%" align="left"> 
										  <h2>Result :</h2></td>
									<!--	  <td width="65%" align="left"> 
										  <marquee direction="right"><font color="#FF0000">&gt;&gt;&gt;
										  </font></marquee></td> -->
                                        </tr>
										<tr>
											<td>
												<%
													for(int i=0;i<vMsg.size();i++){
														String strMsgReport = (String)vMsg.get(i);
														out.println("<hr>"+strMsgReport);
													}
                                                                                                        
												%>
                                                                                                 <hr>
											</td>
										</tr>
                                                                                 <tr><td>
                                                                                     <br><br><input type="submit" value="Back to download" onClick="javascript:cmdDefault()">
                                                                                 </td></tr>
                                      </table>
												<%
											}else{
												%>
													<input type="submit" value="Download Transaction" onClick="javascript:cmdDownload()">
													<input type="submit" value="Reprocess Transaction" onClick="javascript:cmdReprocess()">
												<%
											}
										   %>
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



