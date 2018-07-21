<% 
/* 
 * Page Name  		:  lockertreatment_edit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
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
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOCKER, AppObjInfo.G2_LOCKER, AppObjInfo.OBJ_LOCKER_TREATMENT); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    //out.print("privView=" + privView + " | privAdd=" + privAdd);
%>

<!-- Jsp Block -->
<%

	CtrlLockerTreatment ctrlLockerTreatment = new CtrlLockerTreatment(request);
	long oidLockerTreatment = FRMQueryString.requestLong(request, "hidden_treatment_id");

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlLockerTreatment.action(iCommand , oidLockerTreatment);

	errMsg = ctrlLockerTreatment.getMessage();
	FrmLockerTreatment frmLockerTreatment = ctrlLockerTreatment.getForm();
	LockerTreatment lockerTreatment = ctrlLockerTreatment.getLockerTreatment();
	oidLockerTreatment = lockerTreatment.getOID();

	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmLockerTreatment.errorSize()<1)){
	%>
<jsp:forward page="lockertreatment_list.jsp"> 
<jsp:param name="start" value="<%=start%>" />
<jsp:param name="hidden_treatment_id" value="<%=lockerTreatment.getOID()%>" />
</jsp:forward>
<%
	}

%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Locker Treatment</title>
<script language="JavaScript">
    function trapKeyDown() {
        if (event.keyCode == 13) {
		document.frm_lockertreatment.command.value="<%=Command.SAVE%>"; 
		document.frm_lockertreatment.action="lockertreatment_edit.jsp";
		document.frm_lockertreatment.submit();
        }
    }
	function cmdCancel(){
		document.frm_lockertreatment.command.value="<%=Command.CANCEL%>";
		document.frm_lockertreatment.action="lockertreatment_edit.jsp";
		document.frm_lockertreatment.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_lockertreatment.command.value="<%=Command.EDIT%>";
		document.frm_lockertreatment.action="lockertreatment_edit.jsp";
		document.frm_lockertreatment.submit(); 
	} 

	function cmdSave(){
		document.frm_lockertreatment.command.value="<%=Command.SAVE%>"; 
		document.frm_lockertreatment.action="lockertreatment_edit.jsp";
		document.frm_lockertreatment.submit();
	}

	function cmdAsk(oid){
		document.frm_lockertreatment.command.value="<%=Command.ASK%>"; 
		document.frm_lockertreatment.action="lockertreatment_edit.jsp";
		document.frm_lockertreatment.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_lockertreatment.command.value="<%=Command.DELETE%>";
		document.frm_lockertreatment.action="lockertreatment_edit.jsp"; 
		document.frm_lockertreatment.submit();
	}  

	function cmdBack(){
		//document.frm_lockertreatment.command.value="<%=Command.FIRST%>"; 
                document.frm_lockertreatment.command.value="<%=Command.LIST%>"; 
		document.frm_lockertreatment.action="lockertreatment_list.jsp";
		document.frm_lockertreatment.submit();
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
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Locker &gt; Locker Treatment<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_lockertreatment" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_treatment_id" value="<%=oidLockerTreatment%>">
                                      <table width="100%" cellspacing="1" cellpadding="1" >
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td width="11%">&nbsp;</td>
                                          <td width="85%">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >Location</td>
                                          <td  width="85%"  valign="top"> 
					<% 
                                                            Vector locationid_value = new Vector(1,1);
                                                            Vector locationid_key = new Vector(1,1);
                                                            String sel_locationid = ""+lockerTreatment.getLocationId();
                                                            Vector listLockerLocation = new Vector(1,1);
                                                            listLockerLocation = PstLockerLocation.listAll();
                                                            for (int i = 0; i < listLockerLocation.size(); i++) {
                                                                    LockerLocation lockerLocation = (LockerLocation) listLockerLocation.get(i);
                                                                    locationid_key.add(lockerLocation.getLocation());
                                                                    locationid_value.add(String.valueOf(lockerLocation.getOID()));
                                                            }
                                                        %>
					<%=ControlCombo.draw(FrmLockerTreatment.fieldNames[FrmLockerTreatment.FRM_FIELD_LOCATION_ID], null, sel_locationid, locationid_value, locationid_key, "", "formElemen")%> 
                                            * <%=frmLockerTreatment.getErrorMsg(FrmLockerTreatment.FRM_FIELD_LOCATION_ID)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >Treatment 
                                            Date</td>
                                          <td  width="85%"  valign="top"> <%=ControlDate.drawDateWithStyle(FrmLockerTreatment.fieldNames[FrmLockerTreatment.FRM_FIELD_TREATMENT_DATE], (lockerTreatment.getTreatmentDate()==null) ? new Date() : lockerTreatment.getTreatmentDate(), 1, -5, "formElemen", "")%> 
                                            * <%=frmLockerTreatment.getErrorMsg(FrmLockerTreatment.FRM_FIELD_TREATMENT_DATE)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >Treatment</td>
                                          <td  width="85%"  valign="top"> 
                                            <input type="text" name="<%=FrmLockerTreatment.fieldNames[FrmLockerTreatment.FRM_FIELD_TREATMENT]%>" value="<%=lockerTreatment.getTreatment()%>" class="formElemen" onkeydown="javascript:trapKeyDown();">
                                            * <%=frmLockerTreatment.getErrorMsg(FrmLockerTreatment.FRM_FIELD_TREATMENT)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >&nbsp;</td>
                                          <td  width="85%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="11%"  valign="top"  >&nbsp;</td>
                                          <td  width="85%"  valign="top"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80");
							String scomDel = "javascript:cmdAsk('"+oidLockerTreatment+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidLockerTreatment+"')";
							String scancel = "javascript:cmdEdit('"+oidLockerTreatment+"')";
							ctrLine.setBackCaption("Back to List");
								ctrLine.setDeleteCaption("Delete");
								ctrLine.setSaveCaption("Save");
								ctrLine.setAddCaption("");
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
                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> 
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
