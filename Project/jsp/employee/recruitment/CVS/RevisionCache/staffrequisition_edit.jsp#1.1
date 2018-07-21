
<% 
/* 
 * Page Name  		:  staffrequisition_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_STAFF_REQUISITION); %>
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

	CtrlStaffRequisition ctrlStaffRequisition = new CtrlStaffRequisition(request);
	long oidStaffRequisition = FRMQueryString.requestLong(request, "hidden_staff_requisition_id");

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlStaffRequisition.action(iCommand , oidStaffRequisition);

	errMsg = ctrlStaffRequisition.getMessage();
	FrmStaffRequisition frmStaffRequisition = ctrlStaffRequisition.getForm();
	StaffRequisition staffRequisition = ctrlStaffRequisition.getStaffRequisition();
	oidStaffRequisition = staffRequisition.getOID();

	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmStaffRequisition.errorSize()<1)){
	%>
<jsp:forward page="staffrequisition_list.jsp"> 
<jsp:param name="start" value="<%=start%>" />
<jsp:param name="hidden_staff_requisition_id" value="<%=staffRequisition.getOID()%>" />
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
	function cmdSearchEmp(emp, formname){
            switch(emp) {
                case 0:
                    window.open("lookupemployee.jsp?department=" + 
                        document.frm_staffrequisition.EMPDEPARTMENT0.value + 
                        "&fielddept=EMPDEPARTMENT0" +
                        "&fieldname=EMPFULLNAME0" +
                        "&type=" + emp + 
                        "&formname=" + formname + 
                        "&fieldoid=<%=FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_ACKNOWLEDGED_BY]%>", null, 
                        "height=600,width=800,status=yes,toolbar=no,scrollbars= yes,,menubar=no,location=no");
                    break;
                case 1:
                    window.open("lookupemployee.jsp?department=" + 
                        document.frm_staffrequisition.EMPDEPARTMENT1.value + 
                        "&fielddept=EMPDEPARTMENT1" +
                        "&fieldname=EMPFULLNAME1" +
                        "&type=" + emp + 
                        "&formname=" + formname + 
                        "&fieldoid=<%=FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_APPROVED_BY]%>", null, 
                        "height=600,width=800,status=yes,toolbar=no,scrollbars= yes,menubar=no,location=no");
                    break;
                case 2:
                    window.open("lookupemployee.jsp?department=" + 
                        document.frm_staffrequisition.EMPDEPARTMENT2.value + 
                        "&fielddept=EMPDEPARTMENT2" +
                        "&fieldname=EMPFULLNAME2" +
                        "&type=" + emp + 
                        "&formname=" + formname + 
                        "&fieldoid=<%=FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_REQUESTED_BY]%>", null, 
                        "height=600,width=800,status=yes,toolbar=no,scrollbars= yes,,menubar=no,location=no");
                    break;
                default:
                    break;
            }
	}

	function cmdCancel(){
		document.frm_staffrequisition.command.value="<%=Command.CANCEL%>";
		document.frm_staffrequisition.action="staffrequisition_edit.jsp";
		document.frm_staffrequisition.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_staffrequisition.command.value="<%=Command.EDIT%>";
		document.frm_staffrequisition.action="staffrequisition_edit.jsp";
		document.frm_staffrequisition.submit(); 
	} 

	function cmdSave(){
		document.frm_staffrequisition.command.value="<%=Command.SAVE%>"; 
		document.frm_staffrequisition.action="staffrequisition_edit.jsp";
		document.frm_staffrequisition.submit();
	}

	function cmdAsk(oid){
		document.frm_staffrequisition.command.value="<%=Command.ASK%>"; 
		document.frm_staffrequisition.action="staffrequisition_edit.jsp";
		document.frm_staffrequisition.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_staffrequisition.command.value="<%=Command.DELETE%>";
		document.frm_staffrequisition.action="staffrequisition_edit.jsp"; 
		document.frm_staffrequisition.submit();
	}  

	function cmdBack(){
		document.frm_staffrequisition.command.value="<%=Command.FIRST%>"; 
		document.frm_staffrequisition.action="staffrequisition_list.jsp";
		document.frm_staffrequisition.submit();
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
                  Employee &gt; Recruitment &gt; Staff Requisitin Edit<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>;"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_staffrequisition" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_staff_requisition_id" value="<%=oidStaffRequisition%>">
                                      <table width="100%" cellspacing="2" cellpadding="2" >
                                        <tr> 
                                          <td colspan="3"> 
                                            <div align="center"><font size="3"><b>STAFF 
                                              REQUISITION FORM</b></font></div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td colspan="2" class="txtheading1">*) 
                                            entry required</td>
                                        </tr>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td width="17%">Requisition Type</td>
                                          <td width="79%"> 
                                            <%  for(int i=0;i<PstStaffRequisition.reqtypeValue.length;i++){
                                                    String strReq = "";
                                                    if(staffRequisition.getRequisitionType()==PstStaffRequisition.reqtypeValue[i]){
                                                        strReq = "checked";
                                                    }
                                            %>
                                            <input type="radio" name="<%=frmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_REQUISITION_TYPE]%>" value="<%=PstStaffRequisition.reqtypeValue[i]%>" <%=strReq%> style="border:'none'">
                                            <%=PstStaffRequisition.reqtypeKey[i]%> 
                                            <% } %>
                                            * <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_REQUISITION_TYPE)%> 
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  ><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td  width="79%"  valign="top"> 
                                            <% 
                                                Vector dept_value = new Vector(1,1);
                                                Vector dept_key = new Vector(1,1);                                                            
                                                Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
                                                for (int i = 0; i < listDept.size(); i++) {
                                                        Department dept = (Department) listDept.get(i);
                                                        dept_key.add(dept.getDepartment());
                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                }
                                            %>
                                            <%=ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, ""+staffRequisition.getDepartmentId(), dept_value, dept_key) %> 
                                            * <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_DEPARTMENT_ID)%> 
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  ><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                          <td  width="79%"  valign="top"> 
                                            <% 
                                                Vector sec_value = new Vector(1,1);
                                                Vector sec_key = new Vector(1,1);                                                            
                                                Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");                                                          
                                                for (int i = 0; i < listSec.size(); i++) {
                                                        Section sec = (Section) listSec.get(i);
                                                        sec_key.add(sec.getSection());
                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_SECTION_ID],"formElemen",null, "" + staffRequisition.getSectionId(), sec_value, sec_key) %> 
                                            * <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_SECTION_ID)%> 
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >Position</td>
                                          <td  width="79%"  valign="top"> 
                                            <% 
                                                Vector pos_value = new Vector(1,1);
                                                Vector pos_key = new Vector(1,1);
                                                Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                for (int i = 0; i < listPos.size(); i++) {
                                                        Position pos = (Position) listPos.get(i);
                                                        pos_key.add(pos.getPosition());
                                                        pos_value.add(String.valueOf(pos.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_POSITION_ID],"formElemen",null, "" + staffRequisition.getPositionId(), pos_value, pos_key) %> 
                                            * <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_POSITION_ID)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >Status</td>
                                          <td  width="79%"  valign="top"> 
                                            <% 
                                                Vector st_value = new Vector(1,1);
                                                Vector st_key = new Vector(1,1); 
                                                Vector listSt = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");
                                                for (int i = 0; i < listSt.size(); i++) {
                                                        EmpCategory st = (EmpCategory) listSt.get(i);
                                                        st_key.add(st.getEmpCategory());
                                                        st_value.add(String.valueOf(st.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_EMP_CATEGORY_ID],"formElemen",null, "" + staffRequisition.getEmpCategoryId(), st_value, st_key) %> 
                                            * <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_EMP_CATEGORY_ID)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >&nbsp;</td>
                                          <td  width="79%"  valign="top">&nbsp; </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >Total 
                                            Needed</td>
                                          <td  width="79%"  valign="top"> 
                                            <input type="text" name="<%=FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_NEEDED_MALE]%>" value="<%=staffRequisition.getNeededMale()%>" class="formElemen" size="10">
                                            <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_NEEDED_MALE)%> 
                                            male &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                            <input type="text" name="<%=FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_NEEDED_FEMALE]%>" value="<%=staffRequisition.getNeededFemale()%>" class="formElemen" size="10">
                                            <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_NEEDED_FEMALE)%> 
                                            female </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >Expected 
                                            Commencing Date</td>
                                          <td  width="79%"  valign="top"> <%=ControlDate.drawDateWithStyle(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_EXP_COMM_DATE], (staffRequisition.getExpCommDate()==null) ? new Date() : staffRequisition.getExpCommDate(), 1, -5, "formElemen", "")%><%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_EXP_COMM_DATE)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >Temporary 
                                            for</td>
                                          <td  width="79%"  valign="top"> 
                                            <input type="text" name="<%=FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_TEMP_FOR]%>" value="<%=staffRequisition.getTempFor()%>" class="formElemen" size="10">
                                            <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_TEMP_FOR)%> 
                                            months </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >&nbsp;</td>
                                          <td  width="79%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td colspan="2"  valign="top"  > 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="28%">Acknowledged By</td>
                                                <td width="28%">Approved By</td>
                                                <td width="44%">Requested By</td>
                                              </tr>
                                              <tr> 
                                                <td width="33%"> 
                                                  <input type="hidden" name="<%=FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_ACKNOWLEDGED_BY]%>" value="<%=staffRequisition.getAcknowledgedBy()%>" class="formElemen">
                                                  <input type="text" name="EMPFULLNAME0" value="" class="formElemen" size="38">
                                                </td>
                                                <td width="33%"> 
                                                  <input type="hidden" name="<%=FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_APPROVED_BY]%>" value="<%=staffRequisition.getApprovedBy()%>" class="formElemen">
                                                  <input type="text" name="EMPFULLNAME1" value="" class="formElemen" size="38">
                                                </td>
                                                <td width="33%"> 
                                                  <input type="hidden" name="<%=FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_REQUESTED_BY]%>" value="<%=staffRequisition.getRequestedBy()%>" class="formElemen">
                                                  <input type="text" name="EMPFULLNAME2" value="" class="formElemen" size="38">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="28%"> <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_ACKNOWLEDGED_BY)%> 
                                                  <%
                                                        Vector empdept1_value = new Vector(1,1);
                                                        Vector empdept1_key = new Vector(1,1);
                                                        Vector listempDept1 = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        for (int i = 0; i < listempDept1.size(); i++) {
                                                                Department empdept1 = (Department) listempDept1.get(i);
                                                                empdept1_key.add(empdept1.getDepartment());
                                                                empdept1_value.add(String.valueOf(empdept1.getOID()));
                                                        }
                                                  %>
                                                  <%= ControlCombo.draw("EMPDEPARTMENT0","formElemen",null, "", empdept1_value, empdept1_key) %> 
                                                  <input type="button" name="ButtonAck" value="Search..." onClick="javascript:cmdSearchEmp(0, 'frm_staffrequisition');">
                                                </td>
                                                <td width="28%" nowrap> <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_APPROVED_BY)%> 
                                                  <%
                                                        Vector empdept2_value = new Vector(1,1);
                                                        Vector empdept2_key = new Vector(1,1);
                                                        Vector listempDept2 = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        for (int i = 0; i < listempDept2.size(); i++) {
                                                                Department empdept2 = (Department) listempDept2.get(i);
                                                                empdept2_key.add(empdept2.getDepartment());
                                                                empdept2_value.add(String.valueOf(empdept2.getOID()));
                                                        }
                                                  %>
                                                  <%= ControlCombo.draw("EMPDEPARTMENT1","formElemen",null, "", empdept2_value, empdept2_key) %> 
                                                  <input type="button" name="ButtonAck" value="Search..." onClick="javascript:cmdSearchEmp(1, 'frm_staffrequisition');">
                                                </td>
                                                <td width="44%" nowrap> <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_REQUESTED_BY)%> 
                                                  <%
                                                        Vector empdept3_value = new Vector(1,1);
                                                        Vector empdept3_key = new Vector(1,1);
                                                        Vector listempDept3 = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        for (int i = 0; i < listempDept3.size(); i++) {
                                                                Department empdept3 = (Department) listempDept3.get(i);
                                                                empdept3_key.add(empdept3.getDepartment());
                                                                empdept3_value.add(String.valueOf(empdept3.getOID()));
                                                        }
                                                  %>
                                                  <%= ControlCombo.draw("EMPDEPARTMENT2","formElemen",null, "", empdept3_value, empdept3_key) %> 
                                                  <input type="button" name="ButtonApr" value="Search..." onClick="javascript:cmdSearchEmp(2, 'frm_staffrequisition');">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="28%">Date</td>
                                                <td width="28%">Date</td>
                                                <td width="44%">Date</td>
                                              </tr>
                                              <tr> 
                                                <td width="28%"> <%=ControlDate.drawDateWithStyle(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_ACKNOWLEDGED_DATE], staffRequisition.getAcknowledgedDate(), 1, -5, "formElemen", "")%> 
                                                  <%//=ControlDate.drawDate(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_ACKNOWLEDGED_DATE], (staffRequisition.getAcknowledgedDate()==null) ? new Date() : staffRequisition.getAcknowledgedDate(), 1, -5)%>
                                                  <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_ACKNOWLEDGED_DATE)%> 
                                                </td>
                                                <td width="28%"> <%=ControlDate.drawDateWithStyle(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_APPROVED_DATE], staffRequisition.getApprovedDate(), 1, -5, "formElemen", "")%> 
                                                  <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_APPROVED_DATE)%> 
                                                </td>
                                                <td width="44%"> <%=ControlDate.drawDateWithStyle(FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_REQUESTED_DATE], staffRequisition.getRequestedDate(), 1, -5, "formElemen", "")%> 
                                                  <%=frmStaffRequisition.getErrorMsg(FrmStaffRequisition.FRM_FIELD_REQUESTED_DATE)%> 
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="3"> 
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("80");
                                                String scomDel = "javascript:cmdAsk('"+oidStaffRequisition+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidStaffRequisition+"')";
                                                String scancel = "javascript:cmdEdit('"+oidStaffRequisition+"')";
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
