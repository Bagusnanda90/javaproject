<%@ page language = "java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.util.net.*" %>
<%@ page import = "com.dimata.harisma.utility.odbc.*" %>


<%@ include file = "../../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_DOWNLOAD_DATA); %>
<%@ include file = "../../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%	
response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "nocache");  

String resultRead = "";
String tma01Status = "";
//String tma02Status = "";
int numOfTransaction = 0 ;

boolean succeed01 = false;
boolean succeed02 = false;

//Vector v2 = new Vector();
ReturnTransferDataCanteen rtDt = new ReturnTransferDataCanteen();;
if (privStart) 
{
	try 
	{
		TransferCanteenFromMdbTidex canteenMdbAccess = new TransferCanteenFromMdbTidex();
                //canteenTMAAccess.setIgnoreTime(CANTEEN_IGNORE_SWEEP_TIME);
                //canteenTMAAccess.setCheckSweepTime(CANTEEN_CHECK_SWEEP_TIME);                
                
                String station="01";
                String mode="";
                String setHarismaSt="0";
                String startDate="05/01/2008 00:00:01";
                String endDate="05/09/2008 00:00:01";
                int iTrans = canteenMdbAccess.updateAllDataMdb(station, mode, setHarismaSt, startDate,endDate);
                
		if (iTrans>0) 
		{
			succeed01 = true;
		}
		tma01Status = "" + iTrans + " transaction(s) set on MDB";
	}
	catch (Exception e) 
	{
		tma01Status = "Unable to set MDB";
	}

	/*try 
	{
		CanteenTMAAccess canteenTMAAccess = new CanteenTMAAccess();
                //canteenTMAAccess.setIgnoreTime(CANTEEN_IGNORE_SWEEP_TIME);
                //canteenTMAAccess.setCheckSweepTime(CANTEEN_CHECK_SWEEP_TIME);
		v2 = canteenTMAAccess.executeCommand(canteenTMAAccess.DOWNLOAD, "02", "");
		if (String.valueOf(v2.get(0)).equals("OFF") == false) 
		{
			succeed02 = true;
		}
		tma02Status = String.valueOf(v2.get(0)) + " transaction(s) downloaded from TMA-02";;
	}
	catch (Exception e) 
	{
		tma02Status = "Unable to download from TMA-02";
	} */
}


/*
// Send email (download information) to anyone base on setting on system property
String SMTP_SERVER = String.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("SMTP_SERVER"));                                
String HARISMA_EMAIL = String.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("HARISMA_EMAIL"));                                
String ADMIN_EMAIL = String.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("ADMIN_EMAIL"));                                
String ADMIN_CC = String.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("ADMIN_CC"));                                
String ADMIN_BCC = String.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("ADMIN_BCC"));                                
String strSmtp = SMTP_SERVER;        
String strSenderAddr = HARISMA_EMAIL;
String strSenderName = "Administrator Harisma";
String strReceiver = ADMIN_EMAIL;
String strCc = ADMIN_CC;
String strBcc = ADMIN_BCC;
String strSubject = "Harisma download data (manual process)";
String strMsg = "Download data process " + 
			    "\n\t- Date   :  " + new Date() +
			    "\n\n\t- Status : " + 
			    "\n\t\t\t" + tma01Status +
			    "\n\t\t\t" + tma02Status +
				"\n\nRegards," +
				"\nHarisma Administrator"; 	 

MailSender mSend = new MailSender();
mSend.setSMTPHost(strSmtp);
mSend.setSenderAddr(strSenderAddr);
mSend.setSender(strSenderName);
mSend.setReceiver(strReceiver);
mSend.setCc(strCc);
mSend.setBcc(strBcc);
mSend.setSubject(strSubject);
mSend.setMessage(strMsg);
try
{
	mSend.sendMail();
	System.out.println("\tEmail sent succesfully ...");            
}
catch(Exception e) 
{
	System.out.println("\tError occur, email cannot sent ...");
}
*/
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

    function confirmDelete() {
        sMsg = "Do you want to proceed?";
        if (!window.confirm(sMsg)) {
            window.event.returnValue = false;
        }
        else {
            document.frmReset.submit();
        }
    }
</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../../main/header.jsp" %>
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
			  <!-- #BeginEditable "contenttitle" -->Timekeeping >> Download Data<!-- #EndEditable --> 
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
                                    <form name="frmReset" method="post" action="download.jsp">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td width="3%"></td>
                                          <td> 
                                            <div align="left">
                                            <br>
                                            <%=tma01Status%><br>
                                            <%
                                            
                                            %>
                                            
<!-- <br>
                                            <%//=tma02Status%><br> -->
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
