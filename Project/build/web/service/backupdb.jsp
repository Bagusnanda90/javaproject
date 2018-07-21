<% 
/* 
 * Page Name  		:  back_up.jsp
 * Created on 		:  [date] [time] AM/PM    
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version]    
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
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
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_MANUAL_CHECKING, AppObjInfo.OBJ_MANUAL_CHECKING_ABSENTEEISM); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<!-- Jsp Block -->
<%
int iCommandBackup = FRMQueryString.requestInt(request,"command_backup");
long oidServiceBackup = FRMQueryString.requestLong(request, "hidden_service_backup_id");  
String strStartTimeHour = FRMQueryString.requestString(request,FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME]+"_hr");
String strStartTimeMinutes = FRMQueryString.requestString(request,FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME]+"_mi");
String strInterval = FRMQueryString.requestString(request,FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE]);

CtrlServiceConfiguration ctrlServiceConfiguration = new CtrlServiceConfiguration(request);	
ServiceConfiguration serviceConfBackup = ctrlServiceConfiguration.action(iCommandBackup, oidServiceBackup, PstServiceConfiguration.SERVICE_TYPE_BACKUPDB, strStartTimeHour, strStartTimeMinutes, strInterval);
oidServiceBackup = serviceConfBackup.getOID();  
%>
<!-- End of Jsp Block -->

<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Backup data</title>
<script language="JavaScript">
function cmdStopBackup()
{
  document.frm_servicecenter.command_backup.value="<%= Command.STOP %>"; 
  document.frm_servicecenter.submit();  
}

function cmdStartBackup()
{
  document.frm_servicecenter.command_backup.value="<%= Command.START %>";   
  document.frm_servicecenter.action="backupdb.jsp";  
  document.frm_servicecenter.submit();
}

function cmdUpdateBackup(){
  document.frm_servicecenter.command_backup.value="<%= Command.SAVE %>";     
  document.frm_servicecenter.submit();
} 

// -------------- script control line -------------------
function MM_swapImgRestore() 
{ //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.0
	var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() 
{ //v3.0 
	var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/ctr_line/back_f2.jpg')">
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
                  Service Center<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frm_servicecenter" method="post" action="">
                                        <input type="hidden" name="command" value="">
										<input type="hidden" name="command_backup" value="<%=iCommandBackup%>">										
									    <input type="hidden" name="hidden_service_backup_id" value="<%=oidServiceBackup%>">																																								
                                        <input type="hidden" name="log_command" value="0">
                                      <table width="100%" border="0" cellpadding="2" cellspacing="2">
                                        <tr> 
                                          <td width="48%" valign="top"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
                                              <tr> 
                                                <td width="65%"><b>. : : BACKUP 
                                                  DATABASE SERVICE : : .</b></td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">This 
                                                  process will backup database 
                                                  based on service configuration 
                                                  on the right side.</td>
                                                <td align="left"><b><u>Backup 
                                                  Database Service Configuration</u></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">start</font> 
                                                  button to start backup database 
                                                  service process.</td>
                                                <%
												  Date dtBackup=null;
												  try
												  {
													dtBackup = serviceConfBackup.getStartTime();  
													if(dtBackup==null)
													{
														dtBackup = new Date();
													}
												  }
												  catch(Exception e)
												  {
													dtBackup = new Date();
												  }
												  %>												  												  
                                                <td align="left">-&nbsp;Start 
                                                  Time&nbsp;: <%=ControlDate.drawTime(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME], dtBackup, "formElemen")%></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">stop</font> 
                                                  button to stop backup database 
                                                  service process.</td>
                                                <td align="left">-&nbsp;Interval&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: 
                                                  <input type="text" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE]%>" value="<%=serviceConfBackup.getPeriode()%>" class="formElemen" size="10">
                                                  <input type="hidden" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_SERVICE_TYPE]%>" value="<%=PstServiceConfiguration.SERVICE_TYPE_BACKUPDB%>">
                                                  <i>(minutes)</i></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">Backup 
                                                  database service status is&nbsp;&nbsp; 
                                                  <% 														
													String stCommandScript = "d:\\tomcat\\webapps\\harisma_proj\\backup_canteen.bat";    																											
													BackupDatabaseProcess objBackupDatabaseProcess = new BackupDatabaseProcess(stCommandScript);
													switch (iCommandBackup)
													{
														case  Command.START :
															try
															{
																objBackupDatabaseProcess.startService();  //menghidupkan service presence analyser
															}
															catch(Exception e)
															{
																System.out.println("exc when objBackupDatabaseProcess.startService() : " + e.toString());
															}
															break;
													
														case  Command.STOP :
															try
															{
																objBackupDatabaseProcess.stopService();  //mematikan service
															}
															catch(Exception e)
															{
																System.out.println("exc when objBackupDatabaseProcess.stopService : " + e.toString());
															}
															break;
													}	
													
													boolean serviceBackupDatabaseRunning = objBackupDatabaseProcess.getStatus();  													
													if(serviceBackupDatabaseRunning)
													{
													%>
                                                  <font color="#009900">Running...</font> 
                                                  <%
													}
													else
													{
													%>
                                                  <font color="#FF0000">Stopped</font> 
                                                  <%
													}
													%>
                                                  <% 
													String stopStsBackup="";
													String startStsBackup="";
													if(serviceBackupDatabaseRunning)
													{ 					
														startStsBackup="disabled=\"true\"";
														stopStsBackup="";
													} 
													else
													{
														startStsBackup="";
														stopStsBackup="disabled=\"true\"";
													}
													%>
                                                </td>
                                                <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
												  <%if(privStart){%>
                                                  <input type="button" name="btnSaveBackup" value="   Save   " onClick="javascript:cmdUpdateBackup()" class="formElemen" <%=startStsBackup%>>
												  <%}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="65%"> 
                                                  <%if(privStart){%>
                                                  <input type="button" name="Button4" value="  Start  " onClick="javascript:cmdStartBackup()" class="formElemen" <%=startStsBackup%>>
                                                  <input type="button" name="Submit24" value="  Stop  " onClick="javascript:cmdStopBackup()" class="formElemen" <%=stopStsBackup%>>
                                                  <%}%>
                                                </td>
                                                <td>&nbsp; </td>
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
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
