<% 
/* 
 * Page Name  		:  leaveperiod.jsp
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_LEAVE_PERIOD); %>
<%//@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(int iCommand,FrmLeavePeriod frmObject, LeavePeriod objEntity, Vector objectClass,  long leavePeriodId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("70%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
        //ctrlist.addHeader("No","5%");
		ctrlist.addHeader("Start Date","40%");
		ctrlist.addHeader("End Date","40%");
		//ctrlist.addHeader("Status","20%");

		ctrlist.setLinkRow(-1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
		String whereCls = "";
		String orderCls = "";

		/* selected Status*/
		Vector status_value = new Vector(1,1);
		Vector status_key = new Vector(1,1);
		for(int i=0; i<PstLeavePeriod.statusStr.length; i++){
			status_value.add(""+i);
			status_key.add(PstLeavePeriod.statusStr[i]);
		}

		for (int i = 0; i < objectClass.size(); i++) {
			 LeavePeriod LeavePeriod = (LeavePeriod)objectClass.get(i);
			 rowx = new Vector();
			 if(leavePeriodId == LeavePeriod.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
					
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmLeavePeriod.FRM_FIELD_START_DATE] , LeavePeriod.getStartDate(), 1,-5, "formElemen", ""));
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmLeavePeriod.FRM_FIELD_END_DATE] , LeavePeriod.getEndDate(), 1,-5, "formElemen", ""));
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmLeavePeriod.FRM_FIELD_STATUS],null, ""+LeavePeriod.getStatus(), status_value , status_key, "formElemen", ""));
			}else{

				String str_dt_StartDate = ""; 
				try{
					Date dt_StartDate = LeavePeriod.getStartDate();
					if(dt_StartDate==null){
						dt_StartDate = new Date();
					}

				str_dt_StartDate = Formater.formatDate(dt_StartDate, "dd MMMM yyyy");
				}catch(Exception e){ str_dt_StartDate = ""; }
				//rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(LeavePeriod.getOID())+"')\">"+str_dt_StartDate+"</a>");
                rowx.add(str_dt_StartDate);

				String str_dt_EndDate = "";
				try{
					Date dt_EndDate = LeavePeriod.getEndDate();
					if(dt_EndDate==null){
						dt_EndDate = new Date();
					}

				str_dt_EndDate = Formater.formatDate(dt_EndDate, "dd MMMM yyyy");
				}catch(Exception e){ str_dt_EndDate = ""; }
				rowx.add(str_dt_EndDate);

				/*if(LeavePeriod.getStatus()){
				    rowx.add("Valid");
				}else{
					rowx.add("History");
				}*/
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmLeavePeriod.FRM_FIELD_START_DATE] , objEntity.getStartDate(), 1,-5, "formElemen", ""));
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmLeavePeriod.FRM_FIELD_END_DATE] , objEntity.getEndDate(), 1,-5, "formElemen", ""));
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmLeavePeriod.FRM_FIELD_STATUS],null, ""+objEntity.getStatus(), status_value , status_key, "formElemen", ""));

		}

		lstData.add(rowx);

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidLeavePeriod = FRMQueryString.requestLong(request, "hidden_leave_period_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE];

CtrlLeavePeriod ctrlLeavePeriod = new CtrlLeavePeriod(request);
ControlLine ctrLine = new ControlLine();
Vector listLeavePeriod = new Vector(1,1);

/*switch statement */
iErrCode = ctrlLeavePeriod.action(iCommand , oidLeavePeriod);
/* end switch*/
FrmLeavePeriod frmLeavePeriod = ctrlLeavePeriod.getForm();

/*count list All LeavePeriod*/
int vectSize = PstLeavePeriod.getCount(whereClause);

/*switch list LeavePeriod*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlLeavePeriod.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

LeavePeriod LeavePeriod = ctrlLeavePeriod.getLeavePeriod();
msgString =  ctrlLeavePeriod.getMessage();

/* get record to display */
listLeavePeriod = PstLeavePeriod.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listLeavePeriod.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listLeavePeriod = PstLeavePeriod.list(start,recordToGet, whereClause , orderClause);
}
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>harisma--</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmLeavePeriod.hidden_leave_period_id.value="0";
	document.frmLeavePeriod.command.value="<%=Command.ADD%>";
	document.frmLeavePeriod.prev_command.value="<%=prevCommand%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdAsk(oidLeavePeriod){
	document.frmLeavePeriod.hidden_leave_period_id.value=oidLeavePeriod;
	document.frmLeavePeriod.command.value="<%=Command.ASK%>";
	document.frmLeavePeriod.prev_command.value="<%=prevCommand%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdConfirmDelete(oidLeavePeriod){
	document.frmLeavePeriod.hidden_leave_period_id.value=oidLeavePeriod;
	document.frmLeavePeriod.command.value="<%=Command.DELETE%>";
	document.frmLeavePeriod.prev_command.value="<%=prevCommand%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdSave(){
	document.frmLeavePeriod.command.value="<%=Command.SAVE%>";
	document.frmLeavePeriod.prev_command.value="<%=prevCommand%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdEdit(oidLeavePeriod){
	document.frmLeavePeriod.hidden_leave_period_id.value=oidLeavePeriod;
	document.frmLeavePeriod.command.value="<%=Command.EDIT%>";
	document.frmLeavePeriod.prev_command.value="<%=prevCommand%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdCancel(oidLeavePeriod){
	document.frmLeavePeriod.hidden_leave_period_id.value=oidLeavePeriod;
	document.frmLeavePeriod.command.value="<%=Command.EDIT%>";
	document.frmLeavePeriod.prev_command.value="<%=prevCommand%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdBack(){
	document.frmLeavePeriod.command.value="<%=Command.BACK%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdListFirst(){
	document.frmLeavePeriod.command.value="<%=Command.FIRST%>";
	document.frmLeavePeriod.prev_command.value="<%=Command.FIRST%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdListPrev(){
	document.frmLeavePeriod.command.value="<%=Command.PREV%>";
	document.frmLeavePeriod.prev_command.value="<%=Command.PREV%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdListNext(){
	document.frmLeavePeriod.command.value="<%=Command.NEXT%>";
	document.frmLeavePeriod.prev_command.value="<%=Command.NEXT%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

function cmdListLast(){
	document.frmLeavePeriod.command.value="<%=Command.LAST%>";
	document.frmLeavePeriod.prev_command.value="<%=Command.LAST%>";
	document.frmLeavePeriod.action="leaveperiod.jsp";
	document.frmLeavePeriod.submit();
}

//-------------- script form image -------------------

function cmdDelPict(oidLeavePeriod){
	document.frmimage.hidden_leave_period_id.value=oidLeavePeriod;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="leaveperiod.jsp";
	document.frmimage.submit();
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
                  Company &gt; Leave Periode<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frmLeavePeriod" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_leave_period_id" value="<%=oidLeavePeriod%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Leave 
                                                  Periode List</td>
                                              </tr>
                                              <%
							try{
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(iCommand,frmLeavePeriod, LeavePeriod,listLeavePeriod,oidLeavePeriod)%> </td>
                                              </tr>
                                              <% 
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
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                              </tr>
											  <%
											 // if(iCommand!=Command.ADD && iCommand!=Command.EDIT && iCommand!=Command.ASK && iErrCode==FRMMessage.NONE){
											  if(privAdd && (iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmLeavePeriod.errorSize()<1)){
											  %>
                                              <!--<tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add 
                                                  New</a></td>
                                              </tr>-->
											  
                                              <tr align="left" valign="top"> 
											  	<td>
												</td>
                                              </tr>											  
											  <%}%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidLeavePeriod+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidLeavePeriod+"')";
									String scancel = "javascript:cmdEdit('"+oidLeavePeriod+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");

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
									%>
									<%//if(iCommand==Command.ADD || iCommand==Command.EDIT || iCommand==Command.ASK || iErrCode!=FRMMessage.NONE){%>
                                            <%//= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
											<%//}%></td>
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
<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
