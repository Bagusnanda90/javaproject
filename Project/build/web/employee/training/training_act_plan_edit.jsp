<%
/* 
 * Page Name  		:  training_act_plan_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% 
    int appObjCodeGen = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_GENERAL_TRAINING); 
    int appObjCodeDept = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_DEPARTMENTAL_TRAINING); 
    int appObjCode = 0; 
    
    // check training privilege (0 = none, 1 = general, 2 = departmental)
    int trainType = checkTrainingType(appObjCodeGen, appObjCodeDept, userSession);
    
    if(trainType == PRIV_GENERAL) {    
        appObjCode = appObjCodeGen;
    }
    else if(trainType == PRIV_DEPT) {  
        appObjCode = appObjCodeDept;
    }

    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%@ include file = "../../main/checktraining.jsp" %>

<%--@ include file = "../../main/javainit.jsp" %>

<%// int  appObjCode = 0;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_TRAINING_ACTIVITIES);%>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_TRAINING_ACTIVITIES);%>
<%@ include file = "../../main/checkuser.jsp" --%>

<!-- Jsp Block -->
<%
	CtrlTrainingActivityPlan ctrlTrainingActivityPlan = new CtrlTrainingActivityPlan(request);
	long oidTrainingActivityPlan = FRMQueryString.requestLong(request, "hidden_training_activity_plan_id");
	long depOID = FRMQueryString.requestLong(request, FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_DEPARTMENT_ID]);
	
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlTrainingActivityPlan.action(iCommand , oidTrainingActivityPlan);

	errMsg = ctrlTrainingActivityPlan.getMessage();
	FrmTrainingActivityPlan frmTrainingActivityPlan = ctrlTrainingActivityPlan.getForm();
	TrainingActivityPlan trainingActivityPlan = ctrlTrainingActivityPlan.getTrainingActivityPlan();
	oidTrainingActivityPlan = trainingActivityPlan.getOID();
	
	if(depOID!=0){
		trainingActivityPlan.setDepartmentId(depOID);
	}

        orderClause = "";  

	//if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmTrainingActivityPlan.errorSize()<1)){
	%><%--
	<jsp:forward page="training_act_plan_list.jsp"> 
	<jsp:param name="start" value="<%=start%>" />
	<jsp:param name="hidden_training_activity_plan_id" value="<%=trainingActivityPlan.getOID()%>" />
	</jsp:forward>--%>
	<%
	//}
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training Activity Plan</title>
<script language="JavaScript">
	function cmdCancel(){
		document.frm_trainingactivityplan.command.value="<%=Command.CANCEL%>";
		document.frm_trainingactivityplan.action="training_act_plan_edit.jsp";
		document.frm_trainingactivityplan.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_trainingactivityplan.command.value="<%=Command.EDIT%>";
		document.frm_trainingactivityplan.action="training_act_plan_edit.jsp";
		document.frm_trainingactivityplan.submit(); 
	} 

	function cmdSave(){
		document.frm_trainingactivityplan.command.value="<%=Command.SAVE%>"; 
		document.frm_trainingactivityplan.action="training_act_plan_edit.jsp";
		document.frm_trainingactivityplan.submit();
	}

	function cmdAsk(oid){
		document.frm_trainingactivityplan.command.value="<%=Command.ASK%>"; 
		document.frm_trainingactivityplan.action="training_act_plan_edit.jsp";
		document.frm_trainingactivityplan.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_trainingactivityplan.command.value="<%=Command.DELETE%>";
		document.frm_trainingactivityplan.action="training_act_plan_edit.jsp"; 
		document.frm_trainingactivityplan.submit();
	}  

	function cmdBack(){
		document.frm_trainingactivityplan.command.value="<%=Command.BACK%>"; 
		document.frm_trainingactivityplan.action="training_act_plan_list.jsp";
		document.frm_trainingactivityplan.submit();
	}
	
	function cmdChangeDept(){
		document.frm_trainingactivityplan.command.value="0";
		document.frm_trainingactivityplan.action="training_act_plan_edit.jsp";
		document.frm_trainingactivityplan.submit();
	}

//-------------- script form image -------------------

	function cmdDelPic(oid){
		document.frm_trainingactivityplan.command.value="<%=Command.POST%>"; 
		document.frm_trainingactivityplan.action="training_act_plan_edit.jsp";
		document.frm_trainingactivityplan.submit();
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
        //document.frm_trainingactivityplan.<%//=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_DEPARTMENT_ID]%>.style.visibility = "hidden";
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
        //document.frm_trainingactivityplan.<%//=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_DEPARTMENT_ID]%>.style.visibility = "";
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
                  &gt; Training Activity &gt; Plan<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frm_trainingactivityplan" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_training_activity_plan_id" value="<%=oidTrainingActivityPlan%>">
                                      <table width="100%" cellspacing="2" cellpadding="0" >
                                        <tr> 
                                          <td colspan="3">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td colspan="2" nowrap><b>TRAINING ACTIVITIES 
                                            PLAN EDITOR</b></td>
                                        </tr>
                                        <tr>
                                          <td width="2%">&nbsp;</td>
                                          <td class="comment" width="13%" nowrap>&nbsp;</td>
                                          <td class="comment" width="85%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td class="comment" width="13%" nowrap>&nbsp;</td>
                                          <td class="comment" width="85%">*) entry 
                                            required</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="13%"  valign="top" nowrap  >Department 
                                          </td>                                   
                                          <td  width="85%"  valign="top"> 
                                            <% 
                                                String where = "";

                                                if(trainType == PRIV_DEPT) 
                                                    where = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=" + departmentOid;

                                                Vector listDepartment = PstDepartment.list(0, 0, where, " DEPARTMENT ");
                                                Vector deptValue = new Vector(1,1); //hidden values that will be deliver on request (oids) 
                                                Vector deptKey = new Vector(1,1); //texts that displayed on combo box

                                                for(int d=0;d<listDepartment.size();d++){
                                                        Department department = (Department)listDepartment.get(d);
                                                        deptValue.add(""+department.getOID());
                                                        deptKey.add(department.getDepartment());
                                                }											
                                                String select_departmentid = ""+trainingActivityPlan.getDepartmentId(); //selected on combo box
                                            %>
                                            <%=ControlCombo.draw(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_DEPARTMENT_ID], null, select_departmentid, deptValue, deptKey, "onChange=\"javascript:cmdChangeDept()\"", "formElemen")%>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="13%"  valign="top" nowrap  >Date</td>
                                          <td  width="85%"  valign="top"> <%=ControlDate.drawDateMY(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_DATE], (trainingActivityPlan.getDate()==null) ? new Date() : trainingActivityPlan.getDate(),"MMM", "formElemen", +4,-8)%> </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="13%"  valign="top" nowrap  >Training 
                                            Program </td>                                           
                                          <td  width="85%"  valign="top"> 
                                            <% 
                                                Vector dept_value = new Vector(1,1);
                                                Vector dept_key = new Vector(1,1);     

                                                where = "";

                                                if(trainType == PRIV_DEPT) 
                                                     where = PstTrainingDept.fieldNames[PstTrainingDept.FLD_DEPARTMENT_ID]+"="+departmentOid;
                                                else
                                                     where = PstTrainingDept.fieldNames[PstTrainingDept.FLD_DEPARTMENT_ID]+"="+trainingActivityPlan.getDepartmentId();
            
                                                Vector vctTrProgram = PstTrainingDept.list(0,0, where, null);
                                                
                                                for (int i = 0; i < vctTrProgram.size(); i++) {
                                                        TrainingDept dept = (TrainingDept) vctTrProgram.get(i);
                                                        Training trn = new Training();
                                                        try{
                                                                trn = PstTraining.fetchExc(dept.getTrainingId());
                                                        }
                                                        catch(Exception e){
                                                                trn = new Training();
                                                        }
                                                        dept_key.add(trn.getName());
                                                        dept_value.add(String.valueOf(trn.getOID()));
                                                }

                                            %>
                                            <%= ControlCombo.draw(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINING_ID],"formElemen",null, ""+trainingActivityPlan.getTrainingId(), dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"") %> * <%=frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TRAINING_ID)%>
                                           </td>
                                        </tr>                                       
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="13%"  valign="top" nowrap  >Trainer</td>
                                          <td  width="85%"  valign="top"> 
                                            <input type="text" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINER]%>" value="<%=trainingActivityPlan.getTrainer()%>" class="formElemen" size="30">
                                            * <%=frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TRAINER)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="13%"  valign="top" nowrap  >Number 
                                            of Programs Plan</td>
                                          <td  width="85%"  valign="top"> 
                                            <input type="text" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_PROGRAMS_PLAN]%>" value="<%=trainingActivityPlan.getProgramsPlan()==0?"":""+trainingActivityPlan.getProgramsPlan()%>" class="formElemen" size="10">
                                            * <%=frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_PROGRAMS_PLAN)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="13%"  valign="top" nowrap  >Total 
                                            Hours Plan</td>
                                          <td  width="85%"  valign="top"> 
                                            <input type="text" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TOT_HOURS_PLAN]%>" value="<%=trainingActivityPlan.getTotHoursPlan()==0?"":""+trainingActivityPlan.getTotHoursPlan()%>" class="formElemen" size="10">
                                            * <%=frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TOT_HOURS_PLAN)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="13%"  valign="top" nowrap  >Trainees 
                                            Plan</td>
                                          <td  width="85%"  valign="top"> 
                                            <input type="text" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINEES_PLAN]%>" value="<%=trainingActivityPlan.getTraineesPlan()==0?"":""+trainingActivityPlan.getTraineesPlan()%>" class="formElemen" size="10">
                                            * <%=frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TRAINEES_PLAN)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="13%"  valign="top" nowrap  >Remark</td>
                                          <td  width="85%"  valign="top"> 
                                            <textarea name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_REMARK]%>" cols="30" class="formElemen" rows="3" wrap="VIRTUAL"><%=trainingActivityPlan.getRemark()%></textarea>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="13%"  valign="top" nowrap  >&nbsp;</td>
                                          <td  width="85%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="3"> 
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("90");
                                                String scomDel = "javascript:cmdAsk('"+oidTrainingActivityPlan+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidTrainingActivityPlan+"')";
                                                String scancel = "javascript:cmdEdit('"+oidTrainingActivityPlan+"')";
                                                ctrLine.setBackCaption("Back to Search Training Activity Plan");
                                                ctrLine.setDeleteCaption("Delete Training Activity Plan");
                                                ctrLine.setConfirmDelCaption("Yes Delete Training Activity Plan");
                                                ctrLine.setSaveCaption("Save Training Activity Plan");
                                                ctrLine.setAddCaption("Add Training Activity Plan");
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

                                                if ((iCommand == Command.SAVE) || (iCommand == Command.DELETE)) {
                                                    ctrLine.setAddCaption("");
                                                }
												
												if(iCommand==Command.NONE){
													iCommand = Command.EDIT;
												}
                                            %>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> </td>
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
