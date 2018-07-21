
<% 
/* 
 * Page Name  		:  locker_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../main/javainit.jsp" %>

<%// int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOCKER, AppObjInfo.G2_LOCKER, AppObjInfo.OBJ_LOCKER); %>
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

	CtrlLocker ctrlLocker = new CtrlLocker(request);
	long oidLocker = FRMQueryString.requestLong(request, "hidden_locker_id");

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlLocker.action(iCommand , oidLocker);

	errMsg = ctrlLocker.getMessage();
	FrmLocker frmLocker = ctrlLocker.getForm();
	Locker locker = ctrlLocker.getLocker();
	oidLocker = locker.getOID();
	
	if(locker.getOID()==0){
		locker.setConditionId(-1);
	}

	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(iErrCode==FRMMessage.ERR_NONE)){
            System.out.println("------------------BACK");
	%>
            <jsp:forward page="locker_list.jsp"> 
            <jsp:param name="start" value="<%=start%>" />
            <jsp:param name="hidden_locker_id" value="<%=oidLocker%>" />
            </jsp:forward>
<%
	}

%>


<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Locker</title>
<script language="JavaScript">
function cmdAddToLocationLocker(){
    var linkPage = "<%=approot%>/masterdata/lockerlocation.jsp"; 
                //window.open(linkPage,"Absence Edit","height=600,width=800,status=yes,toolbar=yes,menubar=yes,location=yes");  			
                var newWin = window.open(linkPage,"location_locker","height=600,width=800,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=no");  			
                newWin.focus();
    
}
function cmdAddToConditionLocker(){
      var linkPage = "<%=approot%>/masterdata/lockercondition.jsp"; 
                //window.open(linkPage,"Absence Edit","height=600,width=800,status=yes,toolbar=yes,menubar=yes,location=yes");  			
                var newWin = window.open(linkPage,"condition_locker","height=600,width=800,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=no");  			
                newWin.focus();
}
	function cmdCancel(){
		document.frm_locker.command.value="<%=Command.CANCEL%>";
		document.frm_locker.action="locker_edit.jsp";
		document.frm_locker.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_locker.command.value="<%=Command.EDIT%>";
		document.frm_locker.action="locker_edit.jsp";
		document.frm_locker.submit(); 
	} 

	function cmdSave(){
		document.frm_locker.command.value="<%=Command.SAVE%>"; 
		document.frm_locker.action="locker_edit.jsp";
		document.frm_locker.submit();
	}

	function cmdAsk(oid){
		document.frm_locker.command.value="<%=Command.ASK%>"; 
		document.frm_locker.action="locker_edit.jsp";
		document.frm_locker.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_locker.command.value="<%=Command.DELETE%>";
		document.frm_locker.action="locker_edit.jsp"; 
		document.frm_locker.submit();
	}  

	function cmdBack(){
		document.frm_locker.command.value="<%=Command.LIST%>"; 
		document.frm_locker.action="locker_list.jsp";
		document.frm_locker.submit();
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
           <%@include file="../styletemplate/template_header.jsp" %>
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
                  Locker &gt; Locker Edit<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frm_locker" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_locker_id" value="<%=oidLocker%>">
                                      <table width="100%" cellspacing="1" cellpadding="1" >
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                                <td colspan="2" class="comment">*)= 
                                                  required</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="10%">&nbsp;</td>
                                          <td width="88%">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="10%"  valign="top"  >Location</td>
                                          <td  width="88%"  valign="top"> 
                                            <% 
                                                Vector locationid_value = new Vector(1,1);
                                                Vector locationid_key = new Vector(1,1);
                                                String sel_locationid = ""+locker.getLocationId();
                                                //String sel_locationid = "" + sessLocker.getLocationId();
                                                Vector listLockerLocation = new Vector(1,1);
                                                listLockerLocation = PstLockerLocation.listAll();
                                                for (int i = 0; i < listLockerLocation.size(); i++) {
                                                        LockerLocation lockerLocation = (LockerLocation) listLockerLocation.get(i);
                                                        locationid_key.add(lockerLocation.getLocation());
                                                        locationid_value.add(String.valueOf(lockerLocation.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(FrmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCATION_ID],null, sel_locationid, locationid_value, locationid_key) %> 
                                            * <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_LOCATION_ID)%>
                                           <a href="javascript:cmdAddToLocationLocker()" class="command">Add Location Locker</a></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="10%"  valign="top"  >Locker 
                                            Number</td>
                                          <td  width="88%"  valign="top"> 
                                            <input type="text" name="<%=FrmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCKER_NUMBER]%>" value="<%=locker.getLockerNumber()%>" class="formElemen">
                                            * <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_LOCKER_NUMBER)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="10%"  valign="top"  >Key 
                                            Number</td>
                                          <td  width="88%"  valign="top"> 
                                            <input type="text" name="<%=FrmLocker.fieldNames[FrmLocker.FRM_FIELD_KEY_NUMBER]%>" value="<%=locker.getKeyNumber()%>" class="formElemen">
                                            <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_KEY_NUMBER)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="10%"  valign="top"  >Spare 
                                            Key</td>
                                          <td  width="88%"  valign="top"> 
                                            <input type="text" name="<%=FrmLocker.fieldNames[FrmLocker.FRM_FIELD_SPARE_KEY]%>" value="<%=(locker.getSpareKey()==null)? "" : locker.getSpareKey()%>" class="formElemen">
                                            <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_SPARE_KEY)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="10%"  valign="top"  >Condition</td>
                                          <td  width="88%"  valign="top"> 
                                            <% 
                                                Vector conditionid_value = new Vector(1,1);
                                                Vector conditionid_key = new Vector(1,1);
                                                String sel_conditionid = ""+locker.getConditionId();
                                                //String sel_conditionid = "" + sessLocker.getConditionId();
                                                Vector listLockerCondition = new Vector(1,1);
                                                listLockerCondition = PstLockerCondition.listAll();
                                                for (int i = 0; i < listLockerCondition.size(); i++) {
                                                        LockerCondition lockerCondition = (LockerCondition) listLockerCondition.get(i);
                                                        conditionid_key.add(lockerCondition.getCondition());
                                                        conditionid_value.add(String.valueOf(lockerCondition.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(FrmLocker.fieldNames[FrmLocker.FRM_FIELD_CONDITION_ID],null, sel_conditionid, conditionid_value, conditionid_key) %> 
                                            * <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_CONDITION_ID)%>
                                             <a href="javascript:cmdAddToConditionLocker()" class="command">Add Condition Locker</a>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="10%"  valign="top"  >&nbsp;</td>
                                          <td  width="88%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="10%"  valign="top"  >&nbsp;</td>
                                          <td  width="88%"  valign="top"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("50%");
							String scomDel = "javascript:cmdAsk('"+oidLocker+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidLocker+"')";
							String scancel = "javascript:cmdEdit('"+oidLocker+"')";
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
