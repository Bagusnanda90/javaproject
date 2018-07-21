
<% 
/* 
 * Page Name  		:  orichecklist_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.recruitment.*" %>
<%@ page import = "com.dimata.harisma.form.recruitment.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_ORIENTATION_CHECK_LIST); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%--
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
--%>
<!-- Jsp Block -->
<%
	CtrlOriChecklist ctrlOriChecklist = new CtrlOriChecklist(request);
	long oidOriChecklist = FRMQueryString.requestLong(request, "hidden_ori_checklist_id");
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

    if ((iCommand == Command.DELETE) && (oidOriChecklist > 0)) {
        //System.out.println("Delete Checklist...");
        String[] arrActivityId = null;
        String[] arrDone = null;
        String[] arrActivityDate_yr = null;
        String[] arrActivityDate_mn = null;
        String[] arrActivityDate_dy = null;

        try {
            arrActivityId = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ORI_ACTIVITY_ID]);
            //arrDone = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_DONE]);
            arrActivityDate_yr = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ACTIVITY_DATE] + "_yr");
            arrActivityDate_mn = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ACTIVITY_DATE] + "_mn");
            arrActivityDate_dy = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ACTIVITY_DATE] + "_dy");
                //request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ACTIVITY_DATE]);
        }
        catch (Exception e) {}

        if ((arrActivityId != null) && (arrActivityId.length > 0)) {
            OriChecklistActivity oriChecklistActivity = new OriChecklistActivity();
            for (int i = 0; i < arrActivityId.length; i++) {
                String done = "";
                int iDone = 0;
                String sActDate = "";
                int actDate_yr = Integer.parseInt(arrActivityDate_yr[i]);
                int actDate_mn = Integer.parseInt(arrActivityDate_mn[i]);
                int actDate_dy = Integer.parseInt(arrActivityDate_dy[i]);
                Date actDate = null;

                if ((actDate_yr != -1) && (actDate_mn != -1) && (actDate_dy != -1)) {
                    actDate = new Date(actDate_yr-1900, actDate_mn-1, actDate_dy);
                    sActDate = Formater.formatDate(actDate, "yyyy-MM-dd");
                }
                try {
                    done = FRMQueryString.requestString(request, arrActivityId[i]);
                    if (done.equalsIgnoreCase("on")) { iDone = 1; }
                }
                catch (Exception e) {
                }

                //System.out.println(arrActivityId[i] + " - " + sActDate + " - " + done);
                String where  = PstOriChecklistActivity.fieldNames[PstOriChecklistActivity.FLD_ORI_CHECKLIST_ID] + "=" + oidOriChecklist;
                       where += " AND " + PstOriChecklistActivity.fieldNames[PstOriChecklistActivity.FLD_ORI_ACTIVITY_ID] + "=" + arrActivityId[i];
                Vector listoca = PstOriChecklistActivity.list(0, 0, where, "");
                if (listoca.size() > 0) {
                    oriChecklistActivity = (OriChecklistActivity) listoca.get(0);
                    PstOriChecklistActivity.deleteExc(oriChecklistActivity.getOID());
                }
                else {
                    System.out.println("Cannot delete CheckListActivity...");
                }
            }
        }
    }

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";

	//System.out.println("iCommand : "+iCommand+ " - delete = " + Command.DELETE);
	ControlLine ctrLine = new ControlLine();
	iErrCode = ctrlOriChecklist.action(iCommand , oidOriChecklist);
	errMsg = ctrlOriChecklist.getMessage();
	FrmOriChecklist frmOriChecklist = ctrlOriChecklist.getForm();
	OriChecklist oriChecklist = ctrlOriChecklist.getOriChecklist();
	oidOriChecklist = oriChecklist.getOID();
        String errNoChecklist = "";

    if ((iCommand == Command.SAVE) && (oidOriChecklist > 0)) {
        String[] arrActivityId = null;
        String[] arrDone = null;
        String[] arrActivityDate_yr = null;
        String[] arrActivityDate_mn = null;
        String[] arrActivityDate_dy = null;

        try {
            arrActivityId = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ORI_ACTIVITY_ID]);
            //arrDone = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_DONE]);
            arrActivityDate_yr = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ACTIVITY_DATE] + "_yr");
            arrActivityDate_mn = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ACTIVITY_DATE] + "_mn");
            arrActivityDate_dy = request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ACTIVITY_DATE] + "_dy");
                //request.getParameterValues(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ACTIVITY_DATE]);
        }
        catch (Exception e) {}

        if ((arrActivityId != null) && (arrActivityId.length > 0)) {
            OriChecklistActivity oriChecklistActivity = new OriChecklistActivity();
            for (int i = 0; i < arrActivityId.length; i++) {
                String done = "";
                int iDone = 0;
                String sActDate = "";
                int actDate_yr = Integer.parseInt(arrActivityDate_yr[i]);
                int actDate_mn = Integer.parseInt(arrActivityDate_mn[i]);
                int actDate_dy = Integer.parseInt(arrActivityDate_dy[i]);
                Date actDate = null;

                if ((actDate_yr != -1) && (actDate_mn != -1) && (actDate_dy != -1)) {
                    actDate = new Date(actDate_yr-1900, actDate_mn-1, actDate_dy);
                    sActDate = Formater.formatDate(actDate, "yyyy-MM-dd");
                }
                try {
                    done = FRMQueryString.requestString(request, arrActivityId[i]);
                    if (done.equalsIgnoreCase("on")) { iDone = 1; }
                }
                catch (Exception e) {
                }

                //System.out.println(arrActivityId[i] + " - " + sActDate + " - " + done);
                String where  = PstOriChecklistActivity.fieldNames[PstOriChecklistActivity.FLD_ORI_CHECKLIST_ID] + "=" + oidOriChecklist;
                       where += " AND " + PstOriChecklistActivity.fieldNames[PstOriChecklistActivity.FLD_ORI_ACTIVITY_ID] + "=" + arrActivityId[i];
                Vector listoca = PstOriChecklistActivity.list(0, 0, where, "");

                if (listoca.size() > 0) {
                    oriChecklistActivity = (OriChecklistActivity) listoca.get(0);
                    oriChecklistActivity.setOriChecklistId(oidOriChecklist);
                    oriChecklistActivity.setOriActivityId(Long.parseLong(arrActivityId[i]));
                    oriChecklistActivity.setDone(iDone);
                    oriChecklistActivity.setActivityDate(actDate);
                    PstOriChecklistActivity.updateExc(oriChecklistActivity);
                }
                else {
                    oriChecklistActivity.setOriChecklistId(oidOriChecklist);
                    oriChecklistActivity.setOriActivityId(Long.parseLong(arrActivityId[i]));
                    oriChecklistActivity.setDone(iDone);
                    oriChecklistActivity.setActivityDate(actDate);
                    PstOriChecklistActivity.insertExc(oriChecklistActivity);
                }
            }
        }
    }
    else {
        errNoChecklist = "Please select the staff/trainee first...";
    }

    RecrApplication recrApplication = new RecrApplication();
    if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {
        recrApplication = PstRecrApplication.fetchExc(oriChecklist.getRecrApplicationId());
    }

/*
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();
	iErrCode = ctrlOriChecklist.action(iCommand , oidOriChecklist);
	errMsg = ctrlOriChecklist.getMessage();
	FrmOriChecklist frmOriChecklist = ctrlOriChecklist.getForm();
	OriChecklist oriChecklist = ctrlOriChecklist.getOriChecklist();
	oidOriChecklist = oriChecklist.getOID();
*/
	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmOriChecklist.errorSize()<1)){
	//if((iCommand==Command.SAVE)&&(frmOriChecklist.errorSize()<1)){
	%>
            <jsp:forward page="orichecklist_list.jsp"> 
            <jsp:param name="start" value="<%=start%>" />
            <jsp:param name="hidden_ori_checklist_id" value="<%=oriChecklist.getOID()%>" />
            </jsp:forward>
        <%
	}
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Recruitment</title>
<script language="JavaScript">

	function cmdCancel(){
		document.frm_orichecklist.command.value="<%=Command.ADD%>";
		document.frm_orichecklist.action="orichecklist_edit.jsp";
		document.frm_orichecklist.submit();
	} 
	function cmdCancel(){
		document.frm_orichecklist.command.value="<%=Command.CANCEL%>";
		document.frm_orichecklist.action="orichecklist_edit.jsp";
		document.frm_orichecklist.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_orichecklist.command.value="<%=Command.EDIT%>";
		document.frm_orichecklist.action="orichecklist_edit.jsp";
		document.frm_orichecklist.submit(); 
	} 

	function cmdSave(){
		document.frm_orichecklist.command.value="<%=Command.SAVE%>"; 
		document.frm_orichecklist.action="orichecklist_edit.jsp";
		document.frm_orichecklist.submit();
	}

	function cmdAsk(oid){
		document.frm_orichecklist.command.value="<%=Command.ASK%>"; 
		document.frm_orichecklist.action="orichecklist_edit.jsp";
		document.frm_orichecklist.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_orichecklist.command.value="<%=Command.DELETE%>";
		document.frm_orichecklist.action="orichecklist_edit.jsp"; 
		document.frm_orichecklist.submit();
	}  

	function cmdBack(){
		document.frm_orichecklist.command.value="<%=Command.FIRST%>"; 
		document.frm_orichecklist.action="orichecklist_list.jsp";
		document.frm_orichecklist.submit();
	}

	function cmdSearchEmp(){
                /*
                window.open("lookupemployee.jsp?department=" + document.frm_orichecklist.EMPDEPARTMENT.value, null, 
                            "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
                */
                    window.open("lookupemployee.jsp?department=" + 
                        document.frm_orichecklist.EMPDEPARTMENT.value + 
                        "&fielddept=EMPDEPARTMENT" +
                        "&fieldname=EMPFULLNAME" +
                        "&type=0" + 
                        "&formname=frm_orichecklist" + 
                        "&fieldoid=<%=FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_INTERVIEWER_ID]%>", null, 
                        "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");

	}

	function cmdSearchApplicant(){
                window.open("lookupapplicant.jsp?fullname=" + document.frm_orichecklist.FULLNAME.value + 
                            "&department=" + document.frm_orichecklist.DEPARTMENT.value, null, 
                            "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}

	function cmdClearSearchApplicant() {
		document.frm_orichecklist.FULLNAME.value = "";
		document.frm_orichecklist.POSITION.value = "";
		document.frm_orichecklist.COMM_DATE.value = "";
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
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
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
        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Recruitment &gt; Orientation Checklist<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_orichecklist" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_ori_checklist_id" value="<%=oidOriChecklist%>">
                                      <input type="hidden" name="<%=FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_RECR_APPLICATION_ID]%>" value="<%=oriChecklist.getRecrApplicationId()%>" class="formElemen">
                                      <table width="100%" cellspacing="2" cellpadding="2" >
                                        <tr> 
                                          <td width="100%"> 
                                            <div align="center"><b><font size="3">ORIENTATION 
                                              CHECKLIST</font></b></div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="100%"> 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="13%" nowrap>Name of 
                                                  Staff/Trainee</td>
                                                <td width="38%"> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <input type="text" name="FULLNAME" size="30" value="<%=recrApplication.getFullName()%>" readonly>
                                                  <% } else {%>
                                                  <input type="text" name="FULLNAME" size="30" value="">
                                                  <input type="button" name="search" value="Search..." onclick="javascript:cmdSearchApplicant();">
                                                  <input type="button" name="clear" value="Clear" onclick="javascript:cmdClearSearchApplicant();">
                                                  <% } %>
                                                </td>
                                                <td width="12%" nowrap>Department/Section</td>
                                                <td width="37%"> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {
                                                        Department dep = PstDepartment.fetchExc(recrApplication.getFnlDepartmentId());
                                                  %>
                                                  <input type="text" name="DEPARTMENT" value="<%=dep.getDepartment()%>">
                                                  <% } else {%>
                                                  <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                                  <% 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        dept_value.add("0");
                                                        dept_key.add("select ...");
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                Department dept = (Department) listDept.get(i);
                                                                dept_key.add(dept.getDepartment());
                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                  <%= ControlCombo.draw("DEPARTMENT","formElemen",null, "", dept_value, dept_key) %> 
                                                  <% } %>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="13%" nowrap>Job Title</td>
                                                <td width="38%"> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%
                                                    Position pos = PstPosition.fetchExc(recrApplication.getFnlPositionId());
                                                  %>
                                                  <input type="text" name="POSITION" size="30" value="<%=pos.getPosition()%>" readonly>
                                                  <% } else {%>
                                                  <input type="text" name="POSITION" size="30" value="" readonly>
                                                  <% } %>
                                                </td>
                                                <td width="12%" nowrap>Commencement 
                                                  Date</td>
                                                <td width="37%"> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <input type="text" name="COMM_DATE" readonly value="<%=Formater.formatDate(recrApplication.getFnlCommDate(), "dd MMM yyyy")%>">
                                                  <% } else {%>
                                                  <input type="text" name="COMM_DATE" readonly>
                                                  <% } %>
                                                </td>
                                              </tr>
                                              <%
                                                if ((iCommand == Command.SAVE) && (oidOriChecklist == 0)) {
                                              %>
                                              <tr> 
                                                <td colspan="4" nowrap bgcolor="#FFFF00"><font color="#FF0000"><i><%=errNoChecklist%></i></font></td>
                                              </tr>
                                              <%
                                                }
                                              %>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="100%"> 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="100%"> 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <%
                                                Vector listOriGroup = PstOriGroup.listAll();
                                                for (int i=0; i<listOriGroup.size(); i++) {
                                                    OriGroup og = (OriGroup) listOriGroup.get(i);
                                              %>
                                              <tr> 
                                                <td><b><%=og.getGroupName()%></b></td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <%
                                                        if (String.valueOf(og.getGroupName()).compareToIgnoreCase("SKILLS") < 0) {
                                                            String where = PstOriActivity.fieldNames[PstOriActivity.FLD_ORI_GROUP_ID] + 
                                                                "=" + og.getOID();
                                                            Vector listOriActivity = PstOriActivity.list(0, 0, where, "");
                                                            for (int j=0; j<listOriActivity.size(); j++) {
                                                                OriActivity oa = (OriActivity) listOriActivity.get(j);
                                                                String checked = "";

                                                                String whereOCA = PstOriChecklistActivity.fieldNames[PstOriChecklistActivity.FLD_ORI_CHECKLIST_ID] + "=";
                                                                    whereOCA += oidOriChecklist + " AND ";
                                                                    whereOCA += PstOriChecklistActivity.fieldNames[PstOriChecklistActivity.FLD_ORI_ACTIVITY_ID] + "=" ;
                                                                    whereOCA += oa.getOID();
                                                                Vector listOCA = PstOriChecklistActivity.list(0, 0, whereOCA, "");
                                                                OriChecklistActivity oca = new OriChecklistActivity();
                                                                if (listOCA.size() > 0) {
                                                                    oca = (OriChecklistActivity) listOCA.get(0);
                                                                    checked = (oca.getDone() == 0) ? "" : "checked";
                                                                }
                                                                %>
                                                                <tr> 
                                                                  <td width="20%" nowrap> 
                                                                    <input type="hidden" name="<%=FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ORI_ACTIVITY_ID]%>" value="<%=oa.getOID()%>">
                                                                    <%=oa.getActivity()%> 
                                                                  </td>
                                                                  <td width="5%" nowrap> 
                                                                    <input type="checkbox" name="<%=oa.getOID()%>" <%=checked%>>
                                                                    Done&nbsp;&nbsp;&nbsp;&nbsp; 
                                                                  </td>
                                                                  <td> <%=ControlDate.drawDateWithStyle(FrmOriChecklistActivity.fieldNames[FrmOriChecklistActivity.FRM_FIELD_ACTIVITY_DATE], oca.getActivityDate(), 1, -5, "formElemen", "")%> 
                                                                  </td>
                                                                </tr>
                                                        <%
                                                                }
                                                            }
                                                            else {
                                                            %>
                                                                <tr>
                                                                  <td>
                                                                    Please mention all training subjects that have been learnt so far:
                                                                  </td>
                                                                </tr>
                                                                <tr>
                                                                  <td>
                                                                    <textarea cols="50" rows="10" name="<%=FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_SKILLS]%>"><%=oriChecklist.getSkills()%></textarea>
                                                                  </td>
                                                                </tr>
                                                            <%
                                                        }
                                                        %>
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
                                          <td><hr>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td width="100%">
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="9%" nowrap>Signature 
                                                  date</td>
                                                <td width="91%" nowrap><%=ControlDate.drawDateWithStyle(FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_SIGNATURE_DATE], oriChecklist.getSignatureDate(), 1, -5, "formElemen", "")%> 
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="9%" nowrap>Interviewed 
                                                  by</td>
                                                <td width="91%"> 
                                                  <input type="hidden" name="<%=FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_INTERVIEWER_ID]%>" value="<%=oriChecklist.getInterviewerId()%>" class="formElemen">
                                                  <%
                                                    String empname = "";
                                                    Employee emp = new Employee();
                                                    if (oriChecklist.getInterviewerId() > 0) {
                                                        emp = PstEmployee.fetchExc(oriChecklist.getInterviewerId());
                                                    }
                                                  %>
                                                  <input type="text" name="EMPFULLNAME" value="<%=emp.getFullName()%>" class="formElemen">
                                                  <% 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        //dept_value.add("0");
                                                        //dept_key.add("select ...");
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                Department dept = (Department) listDept.get(i);
                                                                dept_key.add(dept.getDepartment());
                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                  <%= ControlCombo.draw("EMPDEPARTMENT","formElemen",null, ""+emp.getDepartmentId(), dept_value, dept_key) %> 
                                                  <input type="button" name="search2" value="Search..." onClick="javascript:cmdSearchEmp();">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="9%" nowrap>Interview 
                                                  date</td>
                                                <td width="91%"><%=ControlDate.drawDateWithStyle(FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_INTERVIEW_DATE], oriChecklist.getInterviewDate(), 1, -5, "formElemen", "")%></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="100%">
                                            <%--
                                            <table border="0" cellpadding="0" cellspacing="0" width="100">
                                              <tr> 
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></td>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="50" class="command" nowrap><a href="javascript:cmdSave()">Save</a></td>
                                              </tr>
                                            </table>
                                            --%>
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("80");
                                                String scomDel = "javascript:cmdAsk('"+oidOriChecklist+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidOriChecklist+"')";
                                                String scancel = "javascript:cmdEdit('"+oidOriChecklist+"')";
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
                                      <%--
                                      <table width="100%" cellspacing="2" cellpadding="2" >
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td colspan="2" class="txtheading1">*) 
                                            entry required</td>
                                        </tr>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td width="14%">&nbsp;</td>
                                          <td width="82%">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="14%"  valign="top"  >Recr 
                                            Application Id</td>
                                          <td  width="82%"  valign="top"> 
                                            <input type="text" name="<%=FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_RECR_APPLICATION_ID]%>" value="<%=oriChecklist.getRecrApplicationId()%>" class="formElemen">
                                            * <%=frmOriChecklist.getErrorMsg(FrmOriChecklist.FRM_FIELD_RECR_APPLICATION_ID)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="14%"  valign="top"  >Interviewer 
                                            Id</td>
                                          <td  width="82%"  valign="top"> 
                                            <input type="text" name="<%=FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_INTERVIEWER_ID]%>" value="<%=oriChecklist.getInterviewerId()%>" class="formElemen">
                                            <%=frmOriChecklist.getErrorMsg(FrmOriChecklist.FRM_FIELD_INTERVIEWER_ID)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="14%"  valign="top"  >Signature 
                                            Date</td>
                                          <td  width="82%"  valign="top"> <%=ControlDate.drawDateWithStyle(FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_SIGNATURE_DATE], (oriChecklist.getSignatureDate()==null) ? new Date() : oriChecklist.getSignatureDate(), 1, -5, "formElemen", "")%><%=frmOriChecklist.getErrorMsg(FrmOriChecklist.FRM_FIELD_SIGNATURE_DATE)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="14%"  valign="top"  >Interview 
                                            Date</td>
                                          <td  width="82%"  valign="top"> <%=ControlDate.drawDateWithStyle(FrmOriChecklist.fieldNames[FrmOriChecklist.FRM_FIELD_INTERVIEW_DATE], (oriChecklist.getInterviewDate()==null) ? new Date() : oriChecklist.getInterviewDate(), 1, -5, "formElemen", "")%><%=frmOriChecklist.getErrorMsg(FrmOriChecklist.FRM_FIELD_INTERVIEW_DATE)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="3"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80");
							String scomDel = "javascript:cmdAsk('"+oidOriChecklist+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidOriChecklist+"')";
							String scancel = "javascript:cmdEdit('"+oidOriChecklist+"')";
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
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                      </table>
                                      --%>
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
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
