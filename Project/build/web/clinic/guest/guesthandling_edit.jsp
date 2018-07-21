
<% 
/* 
 * Page Name  		:  guesthandling_edit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  lkarunia 
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
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_GUEST_HANDLING, AppObjInfo.OBJ_GUEST_HANDLING); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%

	CtrlGuestHandling ctrlGuestHandling = new CtrlGuestHandling(request);

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
	int prevCommand = FRMQueryString.requestInt(request,"prev_command");
	long oidGuestHandling = FRMQueryString.requestLong(request,"guest_handling_oid");

	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlGuestHandling.action(iCommand , oidGuestHandling);

	errMsg = ctrlGuestHandling.getMessage();
	FrmGuestHandling frmGuestHandling = ctrlGuestHandling.getForm();
	GuestHandling guestHandling = ctrlGuestHandling.getGuestHandling();
	oidGuestHandling = guestHandling.getOID();

	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmGuestHandling.errorSize()<1)){
	%>
	<jsp:forward page="srcguesthandling.jsp"> 	
	<jsp:param name="command" value="<%=iCommand%>" />
	<jsp:param name="prev_command" value="<%=prevCommand%>" />
	<jsp:param name="guest_handling_oid" value="<%=guestHandling.getOID()%>" />
	</jsp:forward>
<%
  }
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Guest Handling Editor</title>
<script language="JavaScript">

	function cmdCancel(){
		document.frm_guesthandling.command.value="<%=Command.ADD%>";
		document.frm_guesthandling.action="guesthandling_edit.jsp";
		document.frm_guesthandling.submit();
	} 
	function cmdCancel(){
		document.frm_guesthandling.command.value="<%=Command.CANCEL%>";
		document.frm_guesthandling.action="guesthandling_edit.jsp";
		document.frm_guesthandling.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_guesthandling.command.value="<%=Command.EDIT%>";
		document.frm_guesthandling.action="guesthandling_edit.jsp";
		document.frm_guesthandling.submit(); 
	} 

	function cmdSave(){
		document.frm_guesthandling.command.value="<%=Command.SAVE%>"; 
		document.frm_guesthandling.action="guesthandling_edit.jsp";
		document.frm_guesthandling.submit();
	}

	function cmdAsk(oid){
		document.frm_guesthandling.command.value="<%=Command.ASK%>"; 
		document.frm_guesthandling.action="guesthandling_edit.jsp";
		document.frm_guesthandling.submit();
	}
	
	function cmdAdd(){
		document.frm_guesthandling.command.value="<%=Command.ADD%>"; 
		document.frm_guesthandling.action="guesthandling_edit.jsp";
		document.frm_guesthandling.submit();
	}  

	function cmdConfirmDelete(oid){
		document.frm_guesthandling.command.value="<%=Command.DELETE%>";
		document.frm_guesthandling.action="guesthandling_edit.jsp"; 
		document.frm_guesthandling.submit();
	}  

	function cmdBack(){
		document.frm_guesthandling.command.value="<%=Command.BACK%>"; 
		document.frm_guesthandling.action="srcguesthandling.jsp";
		document.frm_guesthandling.submit();
	}

//-------------- script form image -------------------

	function cmdDelPic(oid){
		document.frm_guesthandling.command.value="<%=Command.POST%>"; 
		document.frm_guesthandling.action="guesthandling_edit.jsp";
		document.frm_guesthandling.submit();
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
			  <!-- #BeginEditable "contenttitle" -->Clinic 
                  &gt; Guest Handling<!-- #EndEditable --> 
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
                                    <form name="frm_guesthandling" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
									  <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="start" value="<%=start%>">
									  <input type="hidden" name="guest_handling_oid" value="<%=oidGuestHandling%>">
                                      <table width="100%" cellspacing="1" cellpadding="1" >
                                        <tr> 
                                          <td colspan="3">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td class="txtheading1" width="11%">&nbsp;</td>
                                          <td class="comment" width="85%">*) entry 
                                            required</td>
                                        </tr>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td width="11%">&nbsp;</td>
                                          <td width="85%">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >Date</td>
                                          <td  width="85%"  valign="top"> <%=ControlDate.drawDateWithStyle(FrmGuestHandling.fieldNames[FrmGuestHandling.FRM_FIELD_DATE], (guestHandling.getDate()==null) ? new Date() : guestHandling.getDate(), 1, -5, "formElemen", "")%> 
                                            * <%=frmGuestHandling.getErrorMsg(FrmGuestHandling.FRM_FIELD_DATE)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >Guest 
                                            Name</td>
                                          <td  width="85%"  valign="top"> 
                                            <input type="text" name="<%=FrmGuestHandling.fieldNames[FrmGuestHandling.FRM_FIELD_GUEST_NAME]%>" value="<%=guestHandling.getGuestName()%>" class="formElemen" size="40">
                                            * <%=frmGuestHandling.getErrorMsg(FrmGuestHandling.FRM_FIELD_GUEST_NAME)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >Room</td>
                                          <td  width="85%"  valign="top"> 
                                            <input type="text" name="<%=FrmGuestHandling.fieldNames[FrmGuestHandling.FRM_FIELD_ROOM]%>" value="<%=guestHandling.getRoom()%>" class="formElemen" size="15">
                                            * <%=frmGuestHandling.getErrorMsg(FrmGuestHandling.FRM_FIELD_ROOM)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >Diagnosis</td>
                                          <td  width="85%"  valign="top"> 
                                            <input type="text" name="<%=FrmGuestHandling.fieldNames[FrmGuestHandling.FRM_FIELD_DIAGNOSIS]%>" value="<%=guestHandling.getDiagnosis()%>" class="formElemen" size="40">
                                            * <%=frmGuestHandling.getErrorMsg(FrmGuestHandling.FRM_FIELD_DIAGNOSIS)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >Treatment</td>
                                          <td  width="85%"  valign="top"> 
                                            <input type="text" name="<%=FrmGuestHandling.fieldNames[FrmGuestHandling.FRM_FIELD_TREATMENT]%>" value="<%=guestHandling.getTreatment()%>" class="formElemen" size="50">
                                            * <%=frmGuestHandling.getErrorMsg(FrmGuestHandling.FRM_FIELD_TREATMENT)%> 
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >Remarks</td>
                                          <td  width="85%"  valign="top"> 
                                            <textarea name="<%=FrmGuestHandling.fieldNames[FrmGuestHandling.FRM_FIELD_DESCRIPTION]%>" cols="30" class="formElemen" rows="3"><%=guestHandling.getDescription()%></textarea>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >&nbsp;</td>
                                          <td  width="85%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%">&nbsp; </td>
                                          <td colspan="2">
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80%");
							String scomDel = "javascript:cmdAsk('"+oidGuestHandling+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidGuestHandling+"')";
							String scancel = "javascript:cmdEdit('"+oidGuestHandling+"')";
							ctrLine.setBackCaption("Back to List Guest Handling");
							ctrLine.setDeleteCaption("Delete Guest Handling");
							ctrLine.setConfirmDelCaption("Yes Delete Guest Handling");
							ctrLine.setSaveCaption("Save Guest Handling");
							ctrLine.setAddCaption("Add Guest Handling");
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
                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%></td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->&nbsp;{script}<!-- #EndEditable -->
<!-- #EndTemplate --></html>
