
<% 
/* 
 * Page Name  		:  position.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: Yunny
 * @version  		: 01 
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//kondisi privDelete diberikan nilai false agar command Delete tidak tampak dan user tidak bisa menghapus data master
//Edited By yunny
//privDelete=false;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

    public String drawList(Vector objectClass ,  long reasonId)

    {
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");            
            ctrlist.addHeader("No","10%");
            //update by satrya 2012-10-19
            ctrlist.addHeader("Reason Code","15%");
            ctrlist.addHeader("Reason","15%");
            ctrlist.addHeader("Schedule","15%");
            ctrlist.addHeader("Show In Summary Report ","10%");
            ctrlist.addHeader("Show In Pay Input ","10%");
            ctrlist.addHeader("Description","40%");
            ctrlist.addHeader("Number Of Show","40%");
            ctrlist.addHeader("Count Able","20%");

            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            int index = -1;

            for (int i = 0; i < objectClass.size(); i++) {
                    Reason reason = (Reason)objectClass.get(i);
                     Vector rowx = new Vector();
                     if(reasonId == reason.getOID())
                             index = i;

                    rowx.add(" " + reason.getNo()); 
                    rowx.add(" " + reason.getKodeReason()); 
                    rowx.add(" " +reason.getReason());
                    ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                    String schedule = "";
                    
                    if(reason.getScheduleId() != 0){
                        try{
                        
                            scheduleSymbol = PstScheduleSymbol.fetchExc(reason.getScheduleId());    
                            schedule = scheduleSymbol.getSchedule();                                                   
                        
                        }catch(Exception e){
                            schedule = "-";
                        }
                    }else{
                        schedule = "-";
                    }
                    
                    rowx.add(""+schedule);
                    rowx.add(PstReason.timeReasonKey[reason.getTimeReason()]);
                    rowx.add(PstReason.showReasonPayInputKey[reason.getFlagShowInPayInput()]);
                    rowx.add(reason.getDescription());
                    rowx.add(""+reason.getNumberOfShow());
                    rowx.add(PstReason.countAbleKey[reason.getCountAble()]);
                    

                    lstData.add(rowx);
                    lstLinkData.add(String.valueOf(reason.getOID()));
            }
            return ctrlist.draw(index);
    }

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidReason = FRMQueryString.requestLong(request, "hidden_reason_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = ""+PstReason.fieldNames[PstReason.FLD_NO];

CtrlReason ctrlReason = new CtrlReason(request);
ControlLine ctrLine = new ControlLine();
Vector listReason = new Vector(1,1);

/*switch statement */
iErrCode = ctrlReason.action(iCommand , oidReason);
/* end switch*/
FrmReason frmReason = ctrlReason.getForm();

/*count list All Position*/
int vectSize = PstReason.getCount(whereClause);

Reason reason = ctrlReason.getReason();
msgString =  ctrlReason.getMessage();
 
/*switch list Division*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstDivision.findLimitStart(division.getOID(),recordToGet, whereClause);
	oidReason = reason.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlReason.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listReason = PstReason.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listReason.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listReason = PstReason.list(start,recordToGet, whereClause , orderClause);
}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Division</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmreason.hidden_reason_id.value="0";
	document.frmreason.command.value="<%=Command.ADD%>";
	document.frmreason.prev_command.value="<%=prevCommand%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
}

function cmdAsk(oidReason){
	document.frmreason.hidden_reason_id.value=oidReason;
	document.frmreason.command.value="<%=Command.ASK%>";
	document.frmreason.prev_command.value="<%=prevCommand%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
}

function cmdConfirmDelete(oidReason){
	document.frmreason.hidden_reason_id.value=oidReason;
	document.frmreason.command.value="<%=Command.DELETE%>";
	document.frmreason.prev_command.value="<%=prevCommand%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
}
function cmdSave(){
	document.frmreason.command.value="<%=Command.SAVE%>";
	document.frmreason.prev_command.value="<%=prevCommand%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
	}

function cmdEdit(oidReason){
	document.frmreason.hidden_reason_id.value=oidReason;
	document.frmreason.command.value="<%=Command.EDIT%>";
	document.frmreason.prev_command.value="<%=prevCommand%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
	}

function cmdCancel(oidReason){
	document.frmreason.hidden_reason_id.value=oidReason;
	document.frmreason.command.value="<%=Command.EDIT%>";
	document.frmreason.prev_command.value="<%=prevCommand%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
}

function cmdBack(){
	document.frmreason.command.value="<%=Command.BACK%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
	}

function cmdListFirst(){
	document.frmreason.command.value="<%=Command.FIRST%>";
	document.frmreason.prev_command.value="<%=Command.FIRST%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
}

function cmdListPrev(){
	document.frmreason.command.value="<%=Command.PREV%>";
	document.frmreason.prev_command.value="<%=Command.PREV%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
	}

function cmdListNext(){
	document.frmreason.command.value="<%=Command.NEXT%>";
	document.frmreason.prev_command.value="<%=Command.NEXT%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
}

function cmdListLast(){
	document.frmreason.command.value="<%=Command.LAST%>";
	document.frmreason.prev_command.value="<%=Command.LAST%>";
	document.frmreason.action="reason.jsp";
	document.frmreason.submit();
}

function fnTrapKD(){
	//alert(event.keyCode);
	switch(event.keyCode) {
		case <%=LIST_PREV%>:
			cmdListPrev();
			break;
		case <%=LIST_NEXT%>:
			cmdListNext();
			break;
		case <%=LIST_FIRST%>:
			cmdListFirst();
			break;
		case <%=LIST_LAST%>:
			cmdListLast();
			break;
		default:
			break;
	}
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
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
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

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
                  Master Data &gt; Absence Reason<!-- #EndEditable -->
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmreason" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_reason_id" value="<%=oidReason%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Absence Reason
                                                  List </td>
                                              </tr>
                                              <%
							try{
								if (listReason.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listReason,oidReason)%>
                                                </td>
                                              </tr>
                                              <%  } 
						  }catch(Exception exc){ 
						  }%>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
								   int cmd = 0;
									   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
										(iCommand == Command.NEXT || iCommand == Command.LAST))
											cmd =iCommand; 
								   else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
										cmd = Command.FIRST;
									  else 
									  	cmd =prevCommand; 
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
											<%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmDivision.errorSize()<1)){
											   if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmReason.errorSize()<1)){
											   if(privAdd){%>
                                              <tr align="left" valign="top"> 
											  	<td>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add 
                                                        New Absence Reason</a> </td>
														
                                                    </tr>
													
                                                  </table>
												</td>
                                              </tr>
											  <%}
											  }%>
                                            </table>
                                          </td>
                                        </tr>
										   <%if(iErrCode ==4){%>
                                                    <tr>
														
                                                       <td bgcolor="#FFFF00"> Message :  <%= CtrlReason.resultText[CtrlReason.LANGUAGE_FOREIGN][iErrCode]%></td>
													  
                                                    </tr>
                                                     <% }%>
										<tr>
											<td>&nbsp;
											</td>
										</tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(iErrCode>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidReason == 0?"Add":"Edit"%> Absence Reason</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
												  
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="29%">&nbsp;</td>
                                                      <td width="71%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
													<tr align="left" valign="top"> 
													 <td valign="top" width="29%"> 
                                                        No.</td>
                                                      <%
														int number = PstReason.getMaxNo(""); 
														int no = number +1;
														%>
                                                      <td width="71%"> 
													 <%
													  if(iCommand == Command.EDIT){
													  %>
													   <input type="hidden" name="<%=frmReason.fieldNames[FrmReason.FRM_FIELD_NO] %>"  value="<%=reason.getNo() %>">
                                                                                                            <input type="text" name="Reason_show"  value="<%=reason.getNo() %>" class="elemenForm" size="5" disabled>
														 *<%=frmReason.getErrorMsg(FrmReason.FRM_FIELD_NO)%>
														<%
														}else{
														%>
                                                        <input type="text" name="<%=frmReason.fieldNames[FrmReason.FRM_FIELD_NO] %>"  value="<%=reason.getNo() %>" class="elemenForm" size="5">
														 *<%=frmReason.getErrorMsg(FrmReason.FRM_FIELD_NO)%>
														 <%
														 }
														 %>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="29%"> 
                                                        Reason Code</td>
                                                      <td width="71%"> 
                                                        <input type="text" name="<%=frmReason.fieldNames[FrmReason.FRM_FIELD_REASON_CODE] %>"  value="<%= reason.getKodeReason() %>" class="elemenForm" size="30">
                                                        *<%=frmReason.getErrorMsg(FrmReason.FRM_FIELD_REASON_CODE)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="29%"> 
                                                        Absence Reason</td>
                                                      <td width="71%"> 
                                                        <input type="text" name="<%=frmReason.fieldNames[FrmReason.FRM_FIELD_REASON] %>"  value="<%= reason.getReason() %>" class="elemenForm" size="30">
                                                        *<%=frmReason.getErrorMsg(FrmReason.FRM_FIELD_REASON)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="29%"> 
                                                        Schedule</td>
                                                      <td width="71%"> 
                                                      <%
                                                      Vector schedule_id = new Vector();
                                                      Vector schedule_name = new Vector();
                                                      ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                                                      schedule_id.add(""+0);
                                                      schedule_name.add("< none >");
                                                      
                                                      Vector resultSymbolSchedule = SessEmpSchedule.listScheduleAbsence();
                                                      
                                                      String selectedSchedule = "";
                                                      selectedSchedule = ""+reason.getScheduleId();
                                                      
                                                      if(resultSymbolSchedule != null && resultSymbolSchedule.size() > 0){
                                                          
                                                            for(int idxSchedule = 0; idxSchedule < resultSymbolSchedule.size() ; idxSchedule++ ){
                                                                
                                                               
                                                                scheduleSymbol = (ScheduleSymbol)resultSymbolSchedule.get(idxSchedule);
                                                                
                                                                schedule_id.add(""+scheduleSymbol.getOID());
                                                                schedule_name.add(""+scheduleSymbol.getSchedule());
                                                                
                                                            }
                                                                
                                                      }
                                                      
                                                      out.println(ControlCombo.draw(frmReason.fieldNames[FrmReason.FRM_FIELD_SCHEDULE_ID], null, selectedSchedule, schedule_id, schedule_name, null));														  
                                                      %>
                                                        
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="29%"> 
                                                        Description </td>
                                                      <td width="71%">                                                       
                                                        <textarea name="<%=frmReason.fieldNames[FrmReason.FRM_FIELD_DESCRIPTION] %>" class="elemenForm" cols="30" rows="3"><%= reason.getDescription() %></textarea>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="29%"> 
                                                        Reason Time </td>
                                                      <td width="71%"> 
                                                          <%=ControlCombo.draw(frmReason.fieldNames[FrmReason.FRM_FIELD_REASON_TIME], "-select-", ""+reason.getTimeReason(), PstReason.getTimeReasonValue(),  PstReason.getTimeReasonKey(), "")%> 
                                                        &nbsp;<%= frmReason.getErrorMsg(frmReason.FRM_FIELD_REASON_TIME) %>
                                                      </td>
                                                    </tr>
                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="29%"> 
                                                       Show in Pay Input </td>
                                                      <td width="71%"> 
                                                          <%=ControlCombo.draw(frmReason.fieldNames[FrmReason.FRM_FIELD_FLAG_IN_PAY_INPUT], null, ""+reason.getFlagShowInPayInput(), PstReason.getShowInReasonValue(),   PstReason.getShowInReasonKey(), "")%> 
                                                        &nbsp;<%= frmReason.getErrorMsg(frmReason.FRM_FIELD_FLAG_IN_PAY_INPUT) %>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="29%"> 
                                                       Number of Show </td>
                                                      <td width="71%"> 
                                                         <input type="text" name="<%=frmReason.fieldNames[FrmReason.FRM_FIELD_NUMBER_OF_SHOW] %>"  value="<%= reason.getNumberOfShow() %>" class="elemenForm" size="30">
                                                        *<%=frmReason.getErrorMsg(FrmReason.FRM_FIELD_NUMBER_OF_SHOW)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="29%"> 
                                                       Count Able </td>
                                                      <td width="71%"> 
                                                          <%=ControlCombo.draw(frmReason.fieldNames[FrmReason.FRM_FIELD_COUNT_ABLE], null, ""+reason.getCountAble(), PstReason.getCountAbleValue(),   PstReason.getCountAbleKey(), "")%> 
                                                        &nbsp;<%= frmReason.getErrorMsg(frmReason.FRM_FIELD_COUNT_ABLE) %>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidReason+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidReason+"')";
									String scancel = "javascript:cmdEdit('"+oidReason+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setBackCaption("Back to List Reason");
									ctrLine.setSaveCaption("Save Reason");
									ctrLine.setConfirmDelCaption("Yes Delete Reason");
									ctrLine.setDeleteCaption("Delete Reason");

									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
									}
									
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString); 
									%>
                                                                         
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>  
                                                
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
                                           
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
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
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
