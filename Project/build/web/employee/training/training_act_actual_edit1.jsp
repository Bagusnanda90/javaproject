
<% 
/* 
 * Page Name  		:  training_act_actual_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>


<% int  appObjCode = 0;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_TRAINING_ACTIVITIES); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//privAdd = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//privUpdate = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//privDelete =true;// userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//privPrint = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<!-- Jsp Block -->
<%

	CtrlTrainingActivityActual ctrlTrainingActivityActual = new CtrlTrainingActivityActual(request);
	long oidTrainingActivityActual = FRMQueryString.requestLong(request,"hidden_training_activity_id");
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlTrainingActivityActual.action(iCommand , oidTrainingActivityActual,request);

	errMsg = ctrlTrainingActivityActual.getMessage();
	FrmTrainingActivityActual frmTrainingActivityActual = ctrlTrainingActivityActual.getForm();
	TrainingActivityActual trainingActivityActual = ctrlTrainingActivityActual.getTrainingActivityActual();
	
	
	
	oidTrainingActivityActual = trainingActivityActual.getOID();

%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training Activity Actual</title>
<script language="JavaScript">

	function cmdCancel(){
		document.fractual.command.value="<%=Command.CANCEL%>";
		document.fractual.action="training_act_actual_edit.jsp";
		document.fractual.submit();
	} 

	function cmdEdit(oid){ 
		document.fractual.command.value="<%=Command.EDIT%>";
		document.fractual.action="training_act_actual_edit.jsp";
		document.fractual.submit(); 
	} 

	function cmdSave(){
		document.fractual.command.value="<%=Command.SAVE%>"; 
		document.fractual.action="training_act_actual_edit.jsp";
		document.fractual.submit();
	}
	
	function cmdAdd(){
		document.fractual.command.value="<%=Command.ADD%>"; 
		document.fractual.action="training_act_actual_edit.jsp";
		document.fractual.submit();
	} 

	function cmdAsk(oid){
		document.fractual.command.value="<%=Command.ASK%>"; 
		document.fractual.action="training_act_actual_edit.jsp";
		document.fractual.submit();
	} 

	function cmdConfirmDelete(oid){
		document.fractual.command.value="<%=Command.DELETE%>";
		document.fractual.action="training_act_actual_edit.jsp"; 
		document.fractual.submit();
	}  

	function cmdBack(){
		document.fractual.command.value="<%=Command.BACK%>"; 
		document.fractual.action="training_act_actual_list.jsp";
		document.fractual.submit();
	}
	
	function cmdChg(){
        window.open("search_plan.jsp", null, "height=600,width=800, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Training 
                  &gt; Training Activity <!-- #EndEditable --> </strong></font> 
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
                                    <form name="fractual" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_training_activity_id" value="<%=oidTrainingActivityActual%>">
                                      <input type="hidden" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID]%>" value="<%=trainingActivityActual.getTrainingActivityPlanId()%>">
                                      <table width="100%" cellspacing="1" cellpadding="1" >
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td class="txtheading1" align="center" width="12%" nowrap>&nbsp;</td>
                                          <td class="comment" width="86%">*) entry 
                                            required</td>
                                        </tr>
                                        <%
                                              whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID]+" = "+trainingActivityActual.getTrainingActivityPlanId();
                                              Vector plans = PstTrainingActivityPlan.list(0,0,whereClause,"");
                                              String topic = "";
                                              if(plans != null && plans.size()>0){
                                                            TrainingActivityPlan trnActPlan = (TrainingActivityPlan)plans.get(0);
															try{
																Training tr = PstTraining.fetchExc(trnActPlan.getTrainingId());
																topic = tr.getName();
															}
															catch(Exception e){
															}
                                                           // topic = trnActPlan.getProgram();
                                              }
                                          %>
                                        <tr> 
                                          <td width="2%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="12%"  valign="top" align="left" nowrap  >Training 
                                            Program</td>
                                          <td  width="86%"  valign="top" align="left"> 
                                            <input type="text" name="title" value="<%=topic%>" size="50">
                                            * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID)%> 
                                            <a href="javascript:cmdChg()">search 
                                            for existing plan</a></td>
                                        </tr>
                                        <tr> 
                                          <td width="2%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="12%"  valign="top" align="left" nowrap  >Date</td>
                                          <td  width="86%"  valign="top" align="left"> 
                                            <%=ControlDate.drawDateWithStyle(FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_DATE], (trainingActivityActual.getDate()==null) ? new Date() : trainingActivityActual.getDate(), 1, -5, "formElemen", "")%> 
                                            * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_DATE)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="2%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="12%"  valign="top" align="left" nowrap  > 
                                            Time</td>
                                          <td  width="86%"  valign="top" align="left"> 
                                            <%=ControlDate.drawTime(FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_START_TIME], (trainingActivityActual.getStartTime()==null) ? new Date() : trainingActivityActual.getStartTime(), "formElemen")%> 
                                            <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_START_TIME)%> 
                                            <%="to"%> <%=ControlDate.drawTime(FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_END_TIME], (trainingActivityActual.getEndTime()==null) ? new Date() : trainingActivityActual.getEndTime(), "formElemen")%> 
                                            * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_END_TIME)%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="12%"  valign="top" align="left" nowrap  >Atendees</td>
                                          <td  width="86%"  valign="top" align="left"> 
                                            <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_ATENDEES]%>" value="<%if(trainingActivityActual.getAtendees()>0){%><%=trainingActivityActual.getAtendees()%><%}%>" class="formElemen" size="10">
                                            <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_ATENDEES)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="2%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="12%"  valign="top" align="left" nowrap  >Venue</td>
                                          <td  width="86%"  valign="top" align="left"> 
                                            <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_VENUE]%>" value="<%=trainingActivityActual.getVenue()%>" class="formElemen" size="30">
                                            * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_VENUE)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="2%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="12%"  valign="top" align="left" nowrap  >Remark</td>
                                          <td  width="86%"  valign="top" align="left"> 
                                            <textarea name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_REMARK]%>" class="formElemen" cols="30" wrap="VIRTUAL" rows="3"><%=trainingActivityActual.getRemark()%></textarea>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="12%"  valign="top" align="left" nowrap  >&nbsp;</td>
                                          <td  width="86%"  valign="top" align="left">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("85%");
                                                String scomDel = "javascript:cmdAsk('"+oidTrainingActivityActual+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidTrainingActivityActual+"')";
                                                String scancel = "javascript:cmdEdit('"+oidTrainingActivityActual+"')";
                                                ctrLine.setBackCaption("Back to Search Training Activity");
                                                ctrLine.setDeleteCaption("Delete Training Activity");
                                                ctrLine.setSaveCaption("Save Training Activity");
                                                ctrLine.setAddCaption("Add Training Activity");
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
