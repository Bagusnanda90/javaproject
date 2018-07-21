
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
<%@ page import = "com.dimata.harisma.entity.admin.service.*" %>
<%@ page import = "com.dimata.harisma.form.admin.service.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass, int start)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No.","5%");
		ctrlist.addHeader("Date ","16%");
		ctrlist.addHeader("Time ","10%");
		ctrlist.addHeader("Target 1 ","23%");
		ctrlist.addHeader("Target 2 ","23%");
		ctrlist.addHeader("Target 3 ","23%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			Logger logger = (Logger)objectClass.get(i);
			
			Vector rowx = new Vector(1,1);
			
			rowx.add(""+(start+(i+1)));
			rowx.add(Formater.formatDate(logger.getDateCreated(),"dd-MMM-yyyy"));
			rowx.add(Formater.formatDate(logger.getTimeCreated(),"HH:mm:ss"));
			rowx.add(logger.getTarget1Note());
			rowx.add(logger.getTarget2Note());
			rowx.add(logger.getTarget3Note());

			lstData.add(rowx);			
		}


		return ctrlist.draw(index);
	}

%>

<%
	Date stTime=new Date();
	String periode="";
	String srcPath="";
	String target1="";
	String target2="";
	String target3="";
	int limit = 10;
	ServiceManager svcMan = new ServiceManager();
	System.out.println(PstServiceManager.checkServiceConfig());
	if(PstServiceManager.checkServiceConfig())
	{
		System.out.println("Loading config...");
		try{	
			svcMan=PstServiceManager.fetchServiceConfig();
		}catch(Exception e){
			System.out.println(e);
		}
	}


	ControlLine ctrLine = new ControlLine();
	CtrlBackUpService ctrlBackUpService = new CtrlBackUpService(request);
	long oidBackUpService = FRMQueryString.requestLong(request, "hidden_back_up_conf_id");
	int logCommand = FRMQueryString.requestInt(request, "log_command");
	int start = FRMQueryString.requestInt(request,"start");
	
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
	

	iErrCode = ctrlBackUpService.action(iCommand , oidBackUpService, request);

	errMsg = ctrlBackUpService.getMessage();
	FrmBackUpService frmBackUpService = ctrlBackUpService.getForm();
	BackUpService backUpService = ctrlBackUpService.getBackUpService();
	oidBackUpService = backUpService.getOID();
	
	switch (iCommand)
	{
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
	
	//----------
	
	boolean running = svcMan.getStatus();			
					
	Vector data = new Vector(1,1);
	int maxLog=0;
	if(logCommand==Command.LIST){
		maxLog=svcMan.getMaxLog();
        data = svcMan.getLog(start,limit);
	}										  

	if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  		(iCommand == Command.NEXT || iCommand == Command.LAST)){
			start = ctrlBackUpService.actionList(iCommand, start, maxLog, limit);
 	} 
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Harisma - Back Up Service</title>
<script language="JavaScript">

<%if(logCommand==Command.LIST){%>
	window.location="#go";
<%}%>
function cmdStop(){
      document.frm_backupservice.command.value="<%= Command.STOP %>";
	  document.frm_backupservice.start.value="0";
	  document.frm_backupservice.maxLog.value="0";  
	  document.frm_backupservice.submit();
	  
  }
  
  function cmdStart(){
  	  document.frm_backupservice.start.value="0";
	  document.frm_backupservice.maxLog.value="0";
      document.frm_backupservice.command.value="<%= Command.START %>";	  
	  document.frm_backupservice.submit();
  }
  
  function cmdUpdate(){
  	  alert("Attention...\nThe change will not take effect until you restart the BackUp Service Manager!\n\t-If the service is running, please stop and then start again.\n\t-If the service is stopped, just start the service.");
      document.frm_backupservice.command.value="<%= Command.SAVE %>";
	  document.frm_backupservice.start.value="0";
	  document.frm_backupservice.maxLog.value="0";
	  document.frm_backupservice.submit();
  } 
  function cmdListLog(){
      document.frm_backupservice.log_command.value="<%= Command.LIST%>";
	  document.frm_backupservice.start.value="0";
	  document.frm_backupservice.maxLog.value="0";
	  document.frm_backupservice.submit();
  }  
  function cmdClearLog(){
  	  if(confirm("Are you sure you want to delete all existing log?\nWarning...\nThis can not be undone!!!")){
		  document.frm_backupservice.log_command.value="<%= Command.DELETE %>";		  
		  document.frm_backupservice.submit();
	  }
  } 
  function cmdListNext(){
  	  if(document.frm_backupservice.start.value>=<%=(maxLog-limit)%>){
	  		alert("End of list ...");
	  }else{
		  document.frm_backupservice.log_command.value="<%= Command.LIST %>";
		  document.frm_backupservice.start.value="<%=(start+limit)%>";
		  document.frm_backupservice.submit();
	  }
  }
  function cmdListPrev(){
  	  if(document.frm_backupservice.start.value==0){
	  		alert("Beginning of list ...");
	  }else{
		  document.frm_backupservice.log_command.value="<%= Command.LIST %>";
		  if(parseInt("<%=(start-limit)%>")<0)
		  		document.frm_backupservice.start.value="0";
		  else
		  		document.frm_backupservice.start.value="<%=(start-limit)%>";
		  document.frm_backupservice.submit();
	  }
  }     
  function cmdListFirst(){
  	  if(document.frm_backupservice.start.value==0){
	  		alert("Beginning of list ...");
	  }else{
		  document.frm_backupservice.log_command.value="<%= Command.LIST %>";
		  document.frm_backupservice.start.value="0";
		  document.frm_backupservice.submit();
	  }
  }   
  function cmdListLast(){
  	  if(document.frm_backupservice.start.value>=<%=(maxLog-limit)%>){
	  		alert("End of list ...");
	  }else{
		  document.frm_backupservice.log_command.value="<%= Command.LIST %>";
		  document.frm_backupservice.start.value="<%=(maxLog-limit)%>";
		  document.frm_backupservice.submit();
	  }
  }    

	function cmdCancel(){
		document.frm_backupservice.command.value="<%=Command.EDIT%>";
		document.frm_backupservice.action="backupservice_edit.jsp";
		document.frm_backupservice.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_backupservice.command.value="<%=Command.EDIT%>";
		document.frm_backupservice.action="backupservice_edit.jsp";
		document.frm_backupservice.submit(); 
	} 

	function cmdSave(){
		document.frm_backupservice.command.value="<%=Command.SAVE%>"; 
		document.frm_backupservice.action="backupservice_edit.jsp";
		document.frm_backupservice.submit();
	}

	function cmdAsk(oid){
		document.frm_backupservice.command.value="<%=Command.ASK%>"; 
		document.frm_backupservice.action="backupservice_edit.jsp";
		document.frm_backupservice.submit();
	} 

	function cmdDelete(oid){
		document.frm_backupservice.command.value="<%=Command.DELETE%>";
		document.frm_backupservice.action="backupservice_edit.jsp"; 
		document.frm_backupservice.submit();
	}  

	function cmdBack(){
		document.frm_backupservice.command.value="<%=Command.FIRST%>"; 
		document.frm_backupservice.action="backupservice_edit.jsp";
		document.frm_backupservice.submit();
	}
	
	function backMenu(){		
		document.frm_backupservice.action="<%=approot%>/management/main_manactiv.jsp";
		document.frm_backupservice.submit();
	}


//-------------- script control line -------------------
	function MM_swapImgRestore() { //v3.0
		var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}

function MM_preloadImages() { //v3.0
		var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}

function MM_findObj(n, d) { //v4.0
		var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		if(!x && document.getElementById) x=document.getElementById(n); return x;
	}

function MM_swapImage() { //v3.0
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
	
	function showObjectForMenu(){
    }
</SCRIPT>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Service 
                  &gt; Back Up Service<!-- #EndEditable --> </strong></font> </td>
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
									  <input type="hidden" name="start" value="<%=start%>">
									  <input type="hidden" name="maxLog" value="<%=maxLog%>">
                                      <table width="70%" border="0" cellspacing="1" cellpadding="1" align="center">
                                        <tr> 
                                          <td colspan="2">&nbsp;</td>
                                          <td width="66%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="18%"><b>Status</b></td>
                                          <td colspan="2"> 
                                            <% if(running){%>
                                            <font color="#009900">Running...</font> 
                                            <%}else{%>
                                            <font color="#FF0000">Stopped</font> 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="18%">&nbsp;</td>
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
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"><b>Configurations</b></td>
                                          <td width="66%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="18%">&nbsp;</td>
                                          <td width="16%" nowrap>Start Time</td>
                                          <%  Date dt=null;
											  try{
												dt = backUpService.getStartTime();
												if(dt==null){
													dt = new Date();
												}
											  }
											  catch(Exception e){
												dt = new Date();
											  }
											  %>
                                          <td width="66%"><%=ControlDate.drawTime(FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_START_TIME], dt, "formElemen")%> 
                                            * <%=frmBackUpService.getErrorMsg(FrmBackUpService.FRM_FIELD_START_TIME)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="18%">&nbsp;</td>
                                          <td width="16%" nowrap>Periode</td>
                                          <td width="66%" valign="middle"> 
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_PERIODE]%>" value="<%=backUpService.getPeriode()%>" class="formElemen" size="10">
                                            * <%=frmBackUpService.getErrorMsg(FrmBackUpService.FRM_FIELD_PERIODE)%>(in 
                                            Minutes)</td>
                                        </tr>
                                        <tr> 
                                          <td width="18%">&nbsp;</td>
                                          <td width="16%" nowrap>Source Path</td>
                                          <td width="66%" nowrap valign="middle"> 
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_SOURCE_PATH]%>" value="<%=backUpService.getSourcePath()%>" class="formElemen" size="40">
                                            * (database directory)</td>
                                        </tr>
                                        <tr> 
                                          <td width="18%">&nbsp;</td>
                                          <td width="16%" nowrap>Target 1</td>
                                          <td width="66%" nowrap valign="middle"> 
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_TARGET1]%>" value="<%=backUpService.getTarget1()%>" class="formElemen" size="60">
                                            * <%=frmBackUpService.getErrorMsg(FrmBackUpService.FRM_FIELD_TARGET1)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="18%">&nbsp;</td>
                                          <td width="16%" nowrap>Tartget 2</td>
                                          <td width="66%" nowrap valign="middle"> 
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_TARGET2]%>" value="<%=backUpService.getTarget2()%>" class="formElemen" size="60">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="18%">&nbsp;</td>
                                          <td width="16%" nowrap>Tartget 3</td>
                                          <td width="66%" nowrap valign="middle"> 
                                            <input type="text" name="<%=FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_TARGET3]%>" value="<%=backUpService.getTarget3()%>" class="formElemen" size="60">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="18%">&nbsp;</td>
                                          <td colspan="2"><font color="#FF0000">note 
                                            :</font> <font color="#CC0000">use 
                                            slash( &quot;/&quot; ) as path separator</font></td>
                                        </tr>
                                        <tr> 
                                          <td width="18%">&nbsp;</td>
                                          <td colspan="2"> 
                                            <%if(true){//if(hasUpdatePriv){%>
                                            <input type="button" name="Button" value="Update Configurations" class="formElemen" onClick="cmdUpdate()">
                                            <%}%>
                                            <%if((iCommand==Command.SAVE) && (iErrCode==FRMMessage.NONE)){%>
                                            <i><font color="#009900">Configuration 
                                            updated...</font></i> 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"><b>Log</b></td>
                                          <td width="66%">&nbsp;</td>
                                        </tr>
										
                                        <tr> 
                                          <td width="18%"><a name="go"></a></td>
                                          <td colspan="2">                                             
                                            <%//if(hasExecutePriv){%>
                                            <input type="button" name="Button" value="List log" onClick="cmdListLog()" class="formElemen">
                                            <%//}%>
                                            <%
											String str="";
											if(data == null || data.size()<1)
												str="disabled=\"true\"";
											else
												str="";
											%>
                                            <%if(true){//hasDeletePriv){%>
                                            <input type="button" name="Submit3" value="Clear Log" onClick="cmdClearLog()" class="formElemen" <%=str%>>
                                            <%}%>
                                            <%if(logCommand==Command.DELETE){%>
                                            <font color="#00CC00"><i><font color="#009900">List 
                                            cleared...</font></i></font> 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> </td>
                                        </tr>
                                        <%										  										  
										  if(logCommand==Command.LIST){                                          	 
											  
										%>
                                        <tr> 
                                          <td colspan="3"> 
                                            <%
											if(data !=null && data.size()>0){
											  %>
                                            <%=drawList(data,start)%> 
                                            <%}else{ %>
                                            <%="<font color='red'>List is empty...</font>"%> 
                                            <%}%>
                                          </td>
                                        </tr>
                                        <%}%>
                                        <tr align="right"> 
                                          <td colspan="3"> 
                                            <% 
										   int cmd = 0;
											if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
												(iCommand == Command.NEXT || iCommand == Command.LAST))
													cmd =iCommand; 
										    else{											  
												cmd = Command.FIRST;											  
										    } 
											ctrLine.setLocationImg(approot+"/images");
											ctrLine.initDefault();
											%>
                                            <%=ctrLine.drawImageListLimit(cmd,maxLog,start,limit)%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3" align="right">&nbsp;</td>
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
