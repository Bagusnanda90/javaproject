
<% 
/* 
 * Page Name  		:  canteenschedule.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
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
<%@ page import = "com.dimata.harisma.entity.canteen.*" %>
<%@ page import = "com.dimata.harisma.form.canteen.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_SCHEDULE, AppObjInfo.OBJ_SCHEDULE_SYMBOL); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass , long canteenScheduleId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("70%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");				
		ctrlist.addHeader("Code","20%");
		ctrlist.addHeader("Name","40%");
		ctrlist.addHeader("Time Open","20%");
		ctrlist.addHeader("Time Close","20%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		Vector lsLL = new Vector(1,1);
		lsLL = PstScheduleCategory.listAll();

		for (int i = 0; i < objectClass.size(); i++) 
		{
			CanteenSchedule canteenSchedule = (CanteenSchedule)objectClass.get(i);
			Vector rowx = new Vector();
			if(canteenScheduleId == canteenSchedule.getOID())
				 index = i;
				 
			rowx.add(canteenSchedule.getSCode());			
			rowx.add(canteenSchedule.getSName());

			String str_dt_TimeOpen = ""; 
			try
			{
				Date dt_TimeOpen = canteenSchedule.getTTimeOpen();
				if(dt_TimeOpen==null)
				{
					dt_TimeOpen = new Date();
				}
				str_dt_TimeOpen = Formater.formatTimeLocale(dt_TimeOpen);
			}
            catch(Exception e)
			{ 
				str_dt_TimeOpen = ""; 
			}

			String str_dt_TimeClose = ""; 
			try
			{
				Date dt_TimeClose = canteenSchedule.getTTimeClose();
				if(dt_TimeClose==null)
				{
					dt_TimeClose = new Date();
				}
				str_dt_TimeClose = Formater.formatTimeLocale(dt_TimeClose);
			}
			catch(Exception e)
			{ 
				str_dt_TimeClose = ""; 
			}

			rowx.add(str_dt_TimeOpen);
			rowx.add(str_dt_TimeClose);

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(canteenSchedule.getOID()));
		}

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidCanteenSchedule = FRMQueryString.requestLong(request, "hidden_schedule_id");

int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_TIME_OPEN]+ 
					 ", " + PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_TIME_CLOSE]+
					 ", " + PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE];

CtrlCanteenSchedule ctrlCanteenSchedule = new CtrlCanteenSchedule(request);
ControlLine ctrLine = new ControlLine();

Vector listCanteenSchedule = new Vector(1,1);
iErrCode = ctrlCanteenSchedule.action(iCommand, oidCanteenSchedule, request);
FrmCanteenSchedule frmCanteenSchedule = ctrlCanteenSchedule.getForm();

int vectSize = PstCanteenSchedule.getCount(whereClause);

CanteenSchedule canteenSchedule = ctrlCanteenSchedule.getCanteenSchedule();
msgString =  ctrlCanteenSchedule.getMessage();

if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
{
	oidCanteenSchedule = canteenSchedule.getOID();
}

if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST)
{
		start = ctrlCanteenSchedule.actionList(iCommand, start, vectSize, recordToGet);
} 

listCanteenSchedule = PstCanteenSchedule.list(start, recordToGet, whereClause, orderClause);

if (listCanteenSchedule.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
	 {
			start = start - recordToGet;   
	 }
	 else
	 {
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; 
	 }
	 listCanteenSchedule = PstCanteenSchedule.list(start, recordToGet, whereClause, orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Canteen Schedule</title>
<script language="JavaScript">
function cmdAdd(){
	document.frmcanteenschedule.hidden_schedule_id.value="0";
	document.frmcanteenschedule.command.value="<%=Command.ADD%>";
	document.frmcanteenschedule.prev_command.value="<%=prevCommand%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
}

function cmdAsk(oidCanteenSchedule){
	document.frmcanteenschedule.hidden_schedule_id.value=oidCanteenSchedule;
	document.frmcanteenschedule.command.value="<%=Command.ASK%>";
	document.frmcanteenschedule.prev_command.value="<%=prevCommand%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
}

function cmdConfirmDelete(oidCanteenSchedule){
	document.frmcanteenschedule.hidden_schedule_id.value=oidCanteenSchedule;
	document.frmcanteenschedule.command.value="<%=Command.DELETE%>";
	document.frmcanteenschedule.prev_command.value="<%=prevCommand%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
}

function cmdSave(){
	document.frmcanteenschedule.command.value="<%=Command.SAVE%>";
	document.frmcanteenschedule.prev_command.value="<%=prevCommand%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
	}

function cmdEdit(oidCanteenSchedule){
	document.frmcanteenschedule.hidden_schedule_id.value=oidCanteenSchedule;
	document.frmcanteenschedule.command.value="<%=Command.EDIT%>";
	document.frmcanteenschedule.prev_command.value="<%=prevCommand%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
	}

function cmdCancel(oidCanteenSchedule){
	document.frmcanteenschedule.hidden_schedule_id.value=oidCanteenSchedule;
	document.frmcanteenschedule.command.value="<%=Command.EDIT%>";
	document.frmcanteenschedule.prev_command.value="<%=prevCommand%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
}

function cmdBack(){
	document.frmcanteenschedule.command.value="<%=Command.BACK%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
	}

function cmdListFirst(){
	document.frmcanteenschedule.command.value="<%=Command.FIRST%>";
	document.frmcanteenschedule.prev_command.value="<%=Command.FIRST%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
}

function cmdListPrev(){
	document.frmcanteenschedule.command.value="<%=Command.PREV%>";
	document.frmcanteenschedule.prev_command.value="<%=Command.PREV%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
	}

function cmdListNext(){
	document.frmcanteenschedule.command.value="<%=Command.NEXT%>";
	document.frmcanteenschedule.prev_command.value="<%=Command.NEXT%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
}

function cmdListLast(){
	document.frmcanteenschedule.command.value="<%=Command.LAST%>";
	document.frmcanteenschedule.prev_command.value="<%=Command.LAST%>";
	document.frmcanteenschedule.action="canteenschedule.jsp";
	document.frmcanteenschedule.submit();
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
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Canteen &gt; Canteen Schedule<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmcanteenschedule" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_schedule_id" value="<%=oidCanteenSchedule%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" >&nbsp;<span class="listtitle">Canteen 
                                                  Schedule List </span></td>
                                              </tr>
                                              <%
											  if (listCanteenSchedule.size()>0)
											  {
											  %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listCanteenSchedule,oidCanteenSchedule)%> </td>
                                              </tr>
                                              <%  
											  } 
											  %>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
												  int cmd = 0;
												  if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
														(iCommand == Command.NEXT || iCommand == Command.LAST))
												  { 		
															cmd =iCommand; 
												  }			
												  else
												  {
													  if(iCommand == Command.NONE || prevCommand == Command.NONE)
														cmd = Command.FIRST;
													  else 
														cmd =prevCommand; 
												  } 
                                                  ctrLine.setLocationImg(approot+"/images");
												  ctrLine.initDefault();
												 
                                                  out.println(ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet));
												  %>
												  </span> </td>
                                              </tr>
											  <%
											  if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmCanteenSchedule.errorSize()<1))
											  { 
											  %>
                                              <tr align="left" valign="top"> 
                                                <td> 
                                                  <table cellpadding="0" cellspacing="0" border="0" width="50%">
                                                    <tr> 
													<%if(privAdd){
													%>
                                                      <td width="5%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Schedule"></a></td>
                                                      <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="93%" valign="middle"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add 
                                                        New Schedule</a> </td>
														<%}%>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%
											  }
											  %>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp; </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmCanteenSchedule.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="2" class="listtitle"><%=oidCanteenSchedule==0?"Add":"Edit"%> Canteen Schedule</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%" width="35%" colspan="2"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="60%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="21%">&nbsp;</td>
                                                      <td width="79%" class="comment">*) 
                                                        entry required </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="21%"> 
                                                        Code</td>
                                                      <td width="79%"> 
                                                        <input type="text" name="<%=frmCanteenSchedule.fieldNames[FrmCanteenSchedule.FRM_FIELD_CODE] %>"  value="<%= canteenSchedule.getSCode() %>" class="elemenForm">
                                                        * <%=frmCanteenSchedule.getErrorMsg(FrmCanteenSchedule.FRM_FIELD_CODE)%></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="21%"> 
                                                        Name</td>
                                                      <td width="79%"> 
                                                        <input type="text" name="<%=frmCanteenSchedule.fieldNames[FrmCanteenSchedule.FRM_FIELD_NAME] %>"  value="<%= canteenSchedule.getSName() %>" class="elemenForm">
                                                        * <%=frmCanteenSchedule.getErrorMsg(FrmCanteenSchedule.FRM_FIELD_NAME)%></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="21%">Date</td>
                                                      <td width="79%"><%=ControlDate.drawDate(frmCanteenSchedule.fieldNames[frmCanteenSchedule.FRM_FIELD_SCHEDULE_DATE], canteenSchedule.getDScheduleDate(), "elemenForm", 0, 2) %></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="21%">Time 
                                                        Open </td>
                                                      <td width="79%"> <%=ControlDate.drawTime(frmCanteenSchedule.fieldNames[frmCanteenSchedule.FRM_FIELD_TIME_OPEN], canteenSchedule.getTTimeOpen(), "elemenForm", 24, 1, 0) %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="21%">Time 
                                                        Close </td>
                                                      <td width="79%"> <%=ControlDate.drawTime(frmCanteenSchedule.fieldNames[frmCanteenSchedule.FRM_FIELD_TIME_CLOSE], canteenSchedule.getTTimeClose(), "elemenForm", 24, 1, 0) %> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr align="left" > 
                                                <td colspan="2" class="command" valign="top"> 
                                                  <%
													ctrLine.setLocationImg(approot+"/images");
													ctrLine.initDefault();
													ctrLine.setTableWidth("80%");
													String scomDel = "javascript:cmdAsk('"+oidCanteenSchedule+"')";
													String sconDelCom = "javascript:cmdConfirmDelete('"+oidCanteenSchedule+"')";
													String scancel = "javascript:cmdEdit('"+oidCanteenSchedule+"')";
													ctrLine.setBackCaption("Back to List Schedule");
													ctrLine.setCommandStyle("buttonlink");
													ctrLine.setAddCaption("Add Schedule");
													ctrLine.setSaveCaption("Save Schedule");
													ctrLine.setDeleteCaption("Delete Schedule");
													ctrLine.setConfirmDelCaption("Yes Delete Schedule");
				
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
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> </td>
                                              </tr>
                                              <tr> 
                                                <td width="35%">&nbsp;</td>
                                                <td width="65%">&nbsp;</td>
                                              </tr>
                                              <tr align="left" > 
                                                <td colspan="3" valign="top"> 
                                                  <div align="left"></div>
                                                </td>
                                              </tr>
                                            </table>
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
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
