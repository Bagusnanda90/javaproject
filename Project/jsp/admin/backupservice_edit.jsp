<% 
/* 
 * Page Name  		:  backupservice_edit.jsp
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
<!--package hanoman -->
<%@ page import = "com.dimata.harisma.entity.admin.service.*" %>
<%@ page import = "com.dimata.harisma.form.admin.service.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_BACKUP_SERVICE); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//boolean privStart = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
//boolean privStop = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_STOP));

//if of "hasn't access" condition  
if(!privView && !privStart){
%>
<script language="javascript">
	window.location="<%=approot%>/nopriv.html";
</script>
<!-- if of "has access" condition -->
<%
}else{
%>
<!-- Jsp Block -->
<%
Date stTime=new Date();
String periode="";
String srcPath="";
String target1="";
String target2="";
String target3="";
ServiceManager svcMan = new ServiceManager();
System.out.println(WP_ServiceManager.checkServiceConfig());

if(WP_ServiceManager.checkServiceConfig()){
	try{	
		svcMan=WP_ServiceManager.fetchServiceConfig();
	}catch(Exception e){
		System.out.println(e);
	}
}

CtrlBackUpService ctrlBackUpService = new CtrlBackUpService(request);
long oidBackUpService = FRMQueryString.requestLong(request, "hidden_back_up_conf_id");
String xx = FRMQueryString.requestString(request, "hidden_back_up_conf_id");
int logCommand = FRMQueryString.requestInt(request, "log_command");
	
Vector vct = PstBackUpService.listAll();
if(vct!=null && vct.size()>0){
	BackUpService bcServ = (BackUpService)vct.get(0);
	oidBackUpService = bcServ.getOID();		
}

int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";
String whereClause = "";
String orderClause = "";
int iCommand = FRMQueryString.requestCommand(request);	
if(iCommand==Command.NONE){
	if(privStart){
		iCommand = Command.STOP;
	}
}
	
ControlLine ctrLine = new ControlLine();
iErrCode = ctrlBackUpService.action(iCommand , oidBackUpService, request);
errMsg = ctrlBackUpService.getMessage();
FrmBackUpService frmBackUpService = ctrlBackUpService.getForm();
BackUpService backUpService = ctrlBackUpService.getBackUpService();
oidBackUpService = backUpService.getOID();

switch (iCommand){
	case  Command.START :	
		  svcMan.startService();
		  break;

	case  Command.STOP :
		  svcMan.stopService();
		  break;
}

if(logCommand==Command.DELETE){
	svcMan.clearLog();
}

boolean running = svcMan.getStatus();
int limit=5;
int startRow=0;

if(request.getParameter("startRow")!=null){
	startRow=Integer.parseInt(request.getParameter("startRow"));
}
int maxLog=0;
if(request.getParameter("maxLog")==null || ((String)request.getParameter("maxLog")).equals("0")){
	maxLog=svcMan.getMaxLog();
}else{
	maxLog=Integer.parseInt(request.getParameter("maxLog"));
}	
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA</title>
<script language="JavaScript">
<%if(logCommand==Command.LIST){%>
	window.location="#go";
<%}%>

function cmdStop(){
  document.frm_backupservice.command.value="<%= Command.STOP %>";
  document.frm_backupservice.startRow.value="0";
  document.frm_backupservice.maxLog.value="0";  
  document.frm_backupservice.submit();
  
}
  
function cmdStart(){
  document.frm_backupservice.startRow.value="0";
  document.frm_backupservice.maxLog.value="0";
  document.frm_backupservice.command.value="<%= Command.START %>";	  
  document.frm_backupservice.submit();
}
  
function cmdUpdate(){
  alert("Attention...\nConfiguration will changed until you restart BackUp Service Manager!\n\t-If service already running, please stop then run again.\n\t-If service stop, run service.");
  document.frm_backupservice.command.value="<%= Command.SAVE %>";
  document.frm_backupservice.startRow.value="0";
  document.frm_backupservice.maxLog.value="0";
  document.frm_backupservice.submit();
} 

function cmdListLog(){
  document.frm_backupservice.log_command.value="<%= Command.LIST%>";
  document.frm_backupservice.startRow.value="0";
  document.frm_backupservice.maxLog.value="0";
  document.frm_backupservice.submit();
}  

function cmdClearLog(){
  if(confirm("Are you sure clear data logs?\nAttention...\nProcess undone!!!")){
	  document.frm_backupservice.log_command.value="<%= Command.DELETE %>";		  
	  document.frm_backupservice.submit();
  }
} 

function cmdNext(){
  if(document.frm_backupservice.startRow.value>=<%=(maxLog-limit)%>){
	  msgLog.innerHTML = "<font color=\"#FF0000\">&nbsp;Last record ...</font>";			
  }else{
	  document.frm_backupservice.log_command.value="<%= Command.LIST %>";
	  document.frm_backupservice.startRow.value="<%=(startRow+limit)%>";
	  document.frm_backupservice.submit();
  }
}

function cmdPrev(){
  if(document.frm_backupservice.startRow.value==0){
		msgLog.innerHTML = "<font color=\"#FF0000\">&nbsp;First record ...</font>";
  }else{
	  document.frm_backupservice.log_command.value="<%= Command.LIST %>";
	  if(parseInt("<%=(startRow-limit)%>")<0)
			document.frm_backupservice.startRow.value="0";
	  else
			document.frm_backupservice.startRow.value="<%=(startRow-limit)%>";
	  document.frm_backupservice.submit();
  }
}     

function cmdFirst(){
  if(document.frm_backupservice.startRow.value==0){
		msgLog.innerHTML = "<font color=\"#FF0000\">&nbsp;First record ...</font>";			
  }else{
	  document.frm_backupservice.log_command.value="<%= Command.LIST %>";
	  document.frm_backupservice.startRow.value="0";
	  document.frm_backupservice.submit();
  }
}   

function cmdLast(){
  if(document.frm_backupservice.startRow.value>=<%=(maxLog-limit)%>){
		msgLog.innerHTML = "<font color=\"#FF0000\">&nbsp;Last record ...</font>";			
  }else{
	  document.frm_backupservice.log_command.value="<%= Command.LIST %>";
	  document.frm_backupservice.startRow.value="<%=(maxLog-limit)%>";
	  document.frm_backupservice.submit();
  }
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
      <%@ include file = "../main/mnmain_full.jsp" %>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Utility 
                  &gt; Back Up Data<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frm_backupservice" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="hidden_back_up_conf_id" value="<%=oidBackUpService%>">
                                      <input type="hidden" name="log_command" value="0">
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td colspan="2">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="9%"><b>Status</b></td>
                                          <td colspan="2"> 
                                            <%if(running){%>
                                            <font color="#009900">Running...</font> 
                                            <%}else{%>
                                            <font color="#FF0000">Stopped</font> 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td colspan="2"> 
                                            <% 
							String stopSts="";
							String startSts="";
							if(running){ 					
							   startSts="disabled=\"true\"";
							   stopSts="";
							}else{
							   startSts="";
							   stopSts="disabled=\"true\"";
							}
							%>
                                            <%if(privStart){%>
                                            <input type="button" name="Button" value="  Start  " onClick="javascript:cmdStart()" class="formElemen" <%=startSts%>>
                                            <input type="button" name="Submit2" value="  Stop  " onClick="javascript:cmdStop()" class="formElemen" <%=stopSts%>>
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <hr size="1" noshade>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"><b>Configuration</b></td>
                                          <td width="83%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td width="8%" nowrap>Started Time</td>
                                          <%
						  Date dt=null;
						  try{
							dt = backUpService.getStartTime();
							if(dt==null){
								dt = new Date();
							}
						  }catch(Exception e){
							dt = new Date();
						  }
						  %>
                                          <td width="83%"><%=ControlDate.drawTime(FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_START_TIME], dt, "formElemen")%> * <%=frmBackUpService.getErrorMsg(FrmBackUpService.FRM_FIELD_START_TIME)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td width="8%" nowrap>Period</td>
                                          <td width="83%" valign="middle"> 
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_PERIODE]%>" value="<%=backUpService.getPeriode()%>" class="formElemen" size="10">
                                            * <%=frmBackUpService.getErrorMsg(FrmBackUpService.FRM_FIELD_PERIODE)%>(dlm Menit)</td>
                                        </tr>
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td width="8%" nowrap>Path Data</td>
                                          <td width="83%" nowrap valign="middle"> 
                                            <!--<input type="text" readonly name="<%//=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_SOURCE_PATH]%>" value="<%//=databaseHome%>" class="formElemen" size="40">-->
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_SOURCE_PATH]%>" value="<%=backUpService.getSourcePath()%>" class="formElemen" size="40">
                                            * (directory basis data)</td>
                                        </tr>
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td width="8%" nowrap>Target 1</td>
                                          <td width="83%" nowrap valign="middle"> 
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_TARGET1]%>" value="<%=backUpService.getTarget1()%>" class="formElemen" size="75">
                                            * <%=frmBackUpService.getErrorMsg(FrmBackUpService.FRM_FIELD_TARGET1)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td width="8%" nowrap>Tartget 2</td>
                                          <td width="83%" nowrap valign="middle"> 
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_TARGET2]%>" value="<%=backUpService.getTarget2()%>" class="formElemen" size="75">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td width="8%" nowrap>Tartget 3</td>
                                          <td width="83%" nowrap valign="middle"> 
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_TARGET3]%>" value="<%=backUpService.getTarget3()%>" class="formElemen" size="75">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td colspan="2"><font color="#FF0000">note 
                                            :</font> <font color="#CC0000">use</font><font color="#CC0000"> 
                                            slash( &quot;/&quot; ) as path separator</font></td>
                                        </tr>
                                        <tr> 
                                          <td width="9%">&nbsp;</td>
                                          <td colspan="2"> 
                                            <%if(privStart){%>
                                            <input type="button" name="Button" value="Change Configuration" class="formElemen" onClick="cmdUpdate()">
                                            <%}%>
                                            <%if((iCommand==Command.SAVE) && (iErrCode==FRMMessage.NONE)){%>
                                            <i><font color="#009900">Configuration 
                                            changed...</font></i> 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <hr size="1" noshade>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"><b>Log</b></td>
                                          <td width="83%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="9%"><a name="go"></a></td>
                                          <td colspan="2"> 
                                            <%if(privStart){%>
                                            <input type="button" name="Button" value="Logs list" onClick="cmdListLog()" class="formElemen">
                                            <%}%>
                                            <%
							String str="";
							if(svcMan.getMaxLog()<5)
								str="disabled=\"true\"";
							else
								str="";
							%>
                                            <%if(true){%>
                                            <input type="button" name="Submit3" value="Clear Log" onClick="cmdClearLog()" class="formElemen" <%=str%>>
                                            <%}%>
                                            <%if(logCommand==Command.DELETE){%>
                                            <font color="#00CC00"><i><font color="#009900">Logs 
                                            list cleared...</font></i></font> 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> </td>
                                        </tr>
                                        <tr> 
                                          <%
						  int nr=0;
						  if(logCommand==Command.LIST){%>
                                          <td colspan="3"> 
                                            <%
							  Vector data = svcMan.getLog(startRow,limit);
							  if(data !=null && data.size()>0){
							  %>
                                            <table width="100%" cellpadding="2" cellspacing="1">
                                              <tr> 
                                                <td  class="listgentitle" width="2%" height="17">No</td>
                                                <td  class="listgentitle" width="6%" height="17">Tanggal</td>
                                                <td  class="listgentitle" width="7%" height="17">Waktu</td>
                                                <td  class="listgentitle" width="27%" height="17">Target 
                                                  1</td>
                                                <td  class="listgentitle" width="29%" height="17">Target 
                                                  2</td>
                                                <td  class="listgentitle" width="29%" height="17">Target 
                                                  3</td>
                                              </tr>
                                              <% 
							  
							  
							  try{
							  //int nr=0;
							  for(int i=0;i<data.size();i++){
									//Vector row=(Vector)data.get(i);
									Logger logger = (Logger)data.get(i);							
									nr=i+1+startRow;
							  %>
                                              <tr valign="top"> 
                                                <td  align="center" class="listgensell" nowrap width="2%"><%=nr%></td>
                                                <td  class="listgensell" nowrap width="6%"><%=Formater.formatDate(logger.getDateCreated(),"dd/MM/yyyy")%></td>
                                                <% 
								System.out.println("created time : "+logger.getTimeCreated());
								System.out.println("created date : "+logger.getDateCreated());
								Date dat= logger.getDateCreated();%>
                                                <td  class="listgensell" nowrap width="7%"><%=logger.getTimeCreated()%></td>
                                                <td  class="listgensell" width="27%"><%=logger.getTarget1Note()%></td>
                                                <td  class="listgensell" width="29%"><%=logger.getTarget2Note()%></td>
                                                <td  class="listgensell" width="29%"><%=logger.getTarget3Note()%></td>
                                              </tr>
                                              <%}  
							  
							  }
							  catch(Exception e){
								System.out.println("Exception 333 : "+e.toString());
							  }
							  %>
                                            </table>
                                            <%}else{					
							out.println("<font color='red'>List is empty...</font>");
							}%>
                                          </td>
                                          <%}%>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" ID="msgLog"> 
                                          <td align="right" width="83%"> 
                                            <input type="hidden" name="startRow" value="<%=startRow%>">
                                            <input type="hidden" name="maxLog" value="<%=maxLog%>">
                                            <input type="hidden" name="limit" value="<%=limit%>">
                                            <%if(logCommand==Command.LIST && nr>0){%>
                                            <a href="javascript:cmdFirst()">Awal</a> 
                                            | <a href="javascript:cmdPrev()">&lt;&lt; 
                                            Mundur</a> | <a href="javascript:cmdNext()">Maju 
                                            &gt;&gt; </a> | <a href="javascript:cmdLast()">Akhir</a> 
                                            <%}%>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
<!-- endif of "has access" condition -->
<%}%>
