
<% 
/* 
 * Page Name  		:  leave_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_GENERAL   , AppObjInfo.OBJ_SG_LEAVE_DP   	); %>
<% //int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_SPECIFIC   , AppObjInfo.OBJ_SS_LEAVE_DP   	); %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
<% int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_LEAVE_MANAGEMENT   	); %>

<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//	boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));

	boolean privStartSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_VIEW));
    boolean privAddSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_ADD));
    boolean privUpdateSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_UPDATE));
    boolean privDeleteSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_DELETE));

	
	//out.println("privStart : "+privStart+", privAdd : "+privAdd+", privUpdate : "+privUpdate+", privDelete : "+privDelete);
	
%>
<!-- Jsp Block -->
<%

	CtrlLeave ctrlLeave = new CtrlLeave(request);
	long oidLeave = FRMQueryString.requestLong(request, "hidden_leave_id");

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlLeave.action(iCommand , oidLeave);

	errMsg = ctrlLeave.getMessage();
	FrmLeave frmLeave = ctrlLeave.getForm();
	Leave leave = ctrlLeave.getLeave();
	oidLeave = leave.getOID();
        System.out.println("oidLeave = " + oidLeave);

	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmLeave.errorSize()<1)){
	%>
<jsp:forward page="leave_list.jsp"> 
<jsp:param name="start" value="<%=start%>" />
<jsp:param name="hidden_leave_id" value="<%=leave.getOID()%>" />
</jsp:forward>
<%
	}

        long oidEmployee = leave.getEmployeeId();
        Employee employee = new Employee();
        long oidPosition = 0;
        Position position = new Position();
        long oidDepartment = 0;
        Department department = new Department();
        //if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {
        //if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand==Command.SAVE)) {
        //if (oidLeave != 0) {
        if ((oidLeave != 0) || (oidEmployee != 0)) {
            employee = PstEmployee.fetchExc(oidEmployee);
            oidPosition = employee.getPositionId();
            position = PstPosition.fetchExc(oidPosition);
            oidDepartment = employee.getDepartmentId();
            department = PstDepartment.fetchExc(oidDepartment);
        }
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Leave Management</title>
<script language="JavaScript">

	function cmdCancel(){
		document.frm_leave.command.value="<%=Command.ADD%>";
		document.frm_leave.action="leave_edit.jsp";
		document.frm_leave.submit();
	} 
	function cmdCancel(){
		document.frm_leave.command.value="<%=Command.CANCEL%>";
		document.frm_leave.action="leave_edit.jsp";
		document.frm_leave.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_leave.command.value="<%=Command.EDIT%>";
		document.frm_leave.action="leave_edit.jsp";
		document.frm_leave.submit(); 
	} 

	function cmdSave(){
		document.frm_leave.command.value="<%=Command.SAVE%>"; 
		document.frm_leave.action="leave_edit.jsp";
		document.frm_leave.submit();
	}

	function cmdAsk(oid){
		document.frm_leave.command.value="<%=Command.ASK%>"; 
		document.frm_leave.action="leave_edit.jsp";
		document.frm_leave.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_leave.command.value="<%=Command.DELETE%>";
		document.frm_leave.action="leave_edit.jsp"; 
		document.frm_leave.submit();
	}  

	function cmdBack(){
		document.frm_leave.command.value="<%=Command.FIRST%>"; 
		document.frm_leave.action="leave_list.jsp";
		document.frm_leave.submit();
	}
	
	function cmdSearchEmp(){
            window.open("empleavesearch.jsp?emp_number=" + document.frm_leave.EMP_NUMBER.value + "&emp_fullname=" + document.frm_leave.EMP_FULLNAME.value + "&emp_department=" + document.frm_leave.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no, scrolbars=yes");
	}

	function cmdClearSearchEmp() {
		document.frm_leave.EMP_FULLNAME.value = "";
		document.frm_leave.EMP_NUMBER.value = "";
		document.frm_leave.EMP_POSITION.value = "";
		document.frm_leave.EMP_COMMENCING_DATE.value = "";
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnCancelOn.jpg')">
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
			  <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Attendance &gt; Leave Management<!-- #EndEditable --> 
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
							  <%if(privStart  || privStartSpec){%>
                                    <form name="frm_leave" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_leave_id" value="<%=oidLeave%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                              <tr> 
                                                <td> 
                                                  <div align="center"><b><font size="3">APPLICATION 
                                                    FOR LEAVE / ABSENCE FORM</font></b> 
                                                  </div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <input type="hidden" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=leave.getEmployeeId()%>" class="formElemen">
                                                      <td width="15%"> 
                                                        <div align="right">Name 
                                                          : </div>
                                                      </td>
                                                      <td width="30%"> 
                                                        <%
                                                            //if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand==Command.SAVE)) {
                                                            //if (oidLeave != 0) {
                                                            if ((oidLeave != 0) || (oidEmployee != 0)) {
                                                        %>
                                                        <%= employee.getFullName() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_FULLNAME" size="30">
                                                        * <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_EMPLOYEE_ID)%> 
                                                        <% } %>
                                                      </td>
                                                      <td width="15%"> 
                                                        <div align="right">Employee 
                                                          Number : </div>
                                                      </td>
                                                      <td width="40%"> 
                                                        <% 
                                                            //if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand==Command.SAVE)) {
                                                            //if (oidLeave != 0) {
                                                            if ((oidLeave != 0) || (oidEmployee != 0)) {
                                                        %>
                                                        <%= employee.getEmployeeNum() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_NUMBER">
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="15%"> 
                                                        <div align="right">Position 
                                                          : </div>
                                                      </td>
                                                      <td width="30%"> 
                                                        <% 
                                                            //if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand==Command.SAVE)) {
                                                            //if (oidLeave != 0) {
                                                            if ((oidLeave != 0) || (oidEmployee != 0)) {
                                                        %>
                                                        <%= position.getPosition() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_POSITION" readonly size="30">
                                                        <% } %>
                                                      </td>
                                                      <td width="15%"> 
                                                        <div align="right">Department 
                                                          : </div>
                                                      </td>
                                                      <td width="40%"> 
                                                        <% 
                                                            //if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand==Command.SAVE)) {
                                                            //if (oidLeave != 0) {
                                                            if ((oidLeave != 0) || (oidEmployee != 0)) {
                                                        %>
                                                        <%= department.getDepartment() %> 
                                                        <% } else {%>
                                                        <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                                        <% 
														
															//Department deptx = userSession.getDepartment();
															//String where = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"="+deptx.getOID();
														
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
                                                            dept_value.add("0");
                                                            dept_key.add("...ALL (may take long time)...");
                                                        %>
                                                        <%= ControlCombo.draw("EMP_DEPARTMENT","formElemen",null, "", dept_value, dept_key) %> 
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="15%"> 
                                                        <div align="right">Commencing 
                                                          Date : </div>
                                                      </td>
                                                      <td width="30%"> 
                                                        <% 
                                                            //if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand==Command.SAVE)) {
                                                            //if (oidLeave != 0) {
                                                            if ((oidLeave != 0) || (oidEmployee != 0)) {
                                                        %>
                                                        <%= Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy") %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_COMMENCING_DATE" readonly>
                                                        <% } %>
                                                      </td>
                                                      <td width="15%"> 
                                                        <div align="right"></div>
                                                      </td>
                                                      <td width="40%"> 
                                                        <% 
                                                            //if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand==Command.SAVE)) {
                                                            //if (oidLeave != 0) {
                                                            if ((oidLeave != 0) || (oidEmployee != 0)) {
                                                        %>
                                                        <% } else {%>
                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="15"><a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                            <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td class="command" nowrap width="99"> 
                                                              <div align="left"><a href="javascript:cmdSearchEmp()">Search 
                                                                Employee</a></div>
                                                            </td>
                                                            <td width="10"><img src="<%=approot%>/images/spacer.gif" width="10" height="4"></td>
                                                            <td width="15"><a href="javascript:cmdClearSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                            <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td class="command" nowrap width="99"> 
                                                              <div align="left"><a href="javascript:cmdClearSearchEmp()">Clear 
                                                                Search </a></div>
                                                            </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                      <% } %>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2" >
                                                    <tr> 
                                                      <td colspan="8">Please approve 
                                                        my leave / absence of 
                                                        work </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="8"> 
                                                        <table width="56%" border="0" cellspacing="2" cellpadding="2">
                                                          <tr> 
                                                            <td width="22%">&nbsp;</td>
                                                            <td width="10%"> 
                                                              <div align="right">from 
                                                                : </div>
                                                            </td>
                                                            <td width="68%"><%=ControlDate.drawDate(FrmLeave.fieldNames[FrmLeave.FRM_FIELD_LEAVE_FROM], leave.getLeaveFrom() == null ? new Date() : leave.getLeaveFrom(),"formElemen", 1, -5)%> 
                                                              *<%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_LEAVE_FROM)%></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="22%">&nbsp;</td>
                                                            <td width="10%"> 
                                                              <div align="right">to 
                                                                : </div>
                                                            </td>
                                                            <td width="68%"><%=ControlDate.drawDate(FrmLeave.fieldNames[FrmLeave.FRM_FIELD_LEAVE_TO], leave.getLeaveTo() == null ? new Date() : leave.getLeaveTo(),"formElemen", 1, -5)%> 
                                                              *<%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_LEAVE_TO)%></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="22%">&nbsp;</td>
                                                            <td width="10%"> 
                                                              <div align="right">for 
                                                                : </div>
                                                            </td>
                                                            <td width="68%"> 
                                                              <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_DURATION]%>" value="<%=leave.getDuration()%>" class="formElemen" size="5">
                                                              working days * <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_DURATION)%></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="8">TYPE OF 
                                                        LEAVE: </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="8"> 
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                          <!--tr> 
                                                            <td width="2%">&nbsp;</td>
                                                            <td width="13%">
                                                              <div align="right">Opname 
                                                                Leave Type</div>
                                                            </td>
                                                            <td width="12%"> 
                                                              <% 
                                                            Vector type_value = new Vector(1,1);
                                                            Vector type_key = new Vector(1,1);        
                                                            for (int i = 0; i < PstLeave.leaveTypeStr.length; i++) {                                                                    
                                                                    type_key.add(PstLeave.leaveTypeStr[i]);
                                                                    type_value.add(""+i);
                                                            }
                                                        %>
                                                              <%= ControlCombo.draw(FrmLeave.fieldNames[FrmLeave.FRM_FIELD_LEAVE_TYPE] ,"formElemen",null, ""+leave.getLeaveType(), type_value, type_key, "") %></td>
                                                            <td width="14%">&nbsp;</td>
                                                            <td width="54%">&nbsp;</td>
                                                          </tr-->
                                                          <tr> 
                                                            <td width="2%">&nbsp;</td>
                                                            <td width="13%">&nbsp;</td>
                                                            <td width="12%">&nbsp;</td>
                                                            <td width="14%">&nbsp;</td>
                                                            <td width="54%">&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="2%">&nbsp;</td>
                                                            <td width="13%"> 
                                                              <div align="right">Long 
                                                                leave : </div>
                                                            </td>
                                                            <td width="12%"> 
                                                              <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_LONG_LEAVE]%>" value="<%=leave.getLongLeave()%>" class="formElemen" size="5">
                                                              <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_LONG_LEAVE)%>days 
                                                            </td>
                                                            <td width="14%"> 
                                                              <div align="right">Day 
                                                                off : </div>
                                                            </td>
                                                            <td width="54%"> 
                                                              <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_DAY_OFF]%>" value="<%=leave.getDayOff()%>" class="formElemen" size="5">
                                                              <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_DAY_OFF)%>days 
                                                            </td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="2%">&nbsp;</td>
                                                            <td width="13%"> 
                                                              <div align="right">Annual 
                                                                leave : </div>
                                                            </td>
                                                            <td width="12%"> 
                                                              <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_ANNUAL_LEAVE]%>" value="<%=leave.getAnnualLeave()%>" class="formElemen" size="5">
                                                              <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_ANNUAL_LEAVE)%>days 
                                                            </td>
                                                            <td width="14%"> 
                                                              <div align="right">Public 
                                                                holiday : </div>
                                                            </td>
                                                            <td width="54%"> 
                                                              <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_PUBLIC_HOLIDAY]%>" value="<%=leave.getPublicHoliday()%>" class="formElemen" size="5">
                                                              <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_PUBLIC_HOLIDAY)%>days 
                                                            </td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="2%">&nbsp;</td>
                                                            <td width="13%"> 
                                                              <div align="right">Leave 
                                                                without pay : 
                                                              </div>
                                                            </td>
                                                            <td width="12%"> 
                                                              <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_LEAVE_WO_PAY]%>" value="<%=leave.getLeaveWoPay()%>" class="formElemen" size="5">
                                                              <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_LEAVE_WO_PAY)%>days 
                                                            </td>
                                                            <td width="14%"> 
                                                              <div align="right">Extra 
                                                                day off : </div>
                                                            </td>
                                                            <td width="54%"> 
                                                              <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_EXTRA_DAY_OFF]%>" value="<%=leave.getExtraDayOff()%>" class="formElemen" size="5">
                                                              <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_EXTRA_DAY_OFF)%>days 
                                                            </td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="2%">&nbsp;</td>
                                                            <td width="13%"> 
                                                              <div align="right">Maternity 
                                                                leave : </div>
                                                            </td>
                                                            <td width="12%"> 
                                                              <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_MATERNITY_LEAVE]%>" value="<%=leave.getMaternityLeave()%>" class="formElemen" size="5">
                                                              <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_MATERNITY_LEAVE)%>days 
                                                            </td>
                                                            <td width="14%"> 
                                                              <div align="right">Sick 
                                                                leave : </div>
                                                            </td>
                                                            <td width="54%"> 
                                                              <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_SICK_LEAVE]%>" value="<%=leave.getSickLeave()%>" class="formElemen" size="5">
                                                              <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_SICK_LEAVE)%>days 
                                                            </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="8"> 
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                          <tr> 
                                                            <td width="18%" valign="top"> 
                                                              <div align="right">Reason 
                                                                for request : 
                                                              </div>
                                                            </td>
                                                            <td width="82%"> 
                                                              <textarea name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_REASON]%>" cols="45" class="formElemen"><%=leave.getReason()%></textarea>
                                                              * <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_REASON)%></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="18%" valign="top"> 
                                                              <div align="right">Submit 
                                                                date : </div>
                                                            </td>
                                                            <td width="82%"><%=ControlDate.drawDate(FrmLeave.fieldNames[FrmLeave.FRM_FIELD_SUBMIT_DATE], leave.getSubmitDate() == null ? new Date() : leave.getSubmitDate(),"formElemen", 1, -5)%> 
                                                              *<%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_SUBMIT_DATE)%></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <div align="left">The calculation 
                                                    of employee leave taken:</div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td width="16%" nowrap>Period 
                                                        of Annual Leave : </td>
                                                      <td width="26%" nowrap> 
                                                        <b><%//=Formater.formatDate(new Date(), "yyyy")%></b> 
                                                        <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_PERIOD_AL_FROM]%>" value="<%=leave.getPeriodAlFrom()%>" class="formElemen" size="10">														
                                                        <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_PERIOD_AL_FROM)%> 
                                                        to 
                                                        <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_PERIOD_AL_TO]%>" value="<%=leave.getPeriodAlTo()%>" class="formElemen" size="10">
                                                        <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_PERIOD_AL_TO)%> 
                                                      </td>
                                                      <td width="20%"> 
                                                        <div align="right">Period 
                                                          of Long Leave : </div>
                                                      </td>
                                                      <td width="38%" nowrap> 
                                                        <div align="left"> <b><%//=Formater.formatDate(new Date(), "yyyy")%></b>	
                                                          <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_PERIOD_LL_FROM]%>" value="<%=leave.getPeriodLlFrom()%>" class="formElemen" size="10">
                                                          <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_PERIOD_LL_FROM)%> 
                                                          to 
                                                          <input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_PERIOD_LL_TO]%>" value="<%=leave.getPeriodLlTo()%>" class="formElemen" size="10">
                                                          <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_PERIOD_LL_TO)%> 
                                                        </div>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="16%"> 
                                                        <div align="right"></div>
                                                      </td>
                                                      <td width="26%">&nbsp; </td>
                                                      <td width="20%">&nbsp;</td>
                                                      <td width="38%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="16%"> 
                                                        <div align="right">Leave 
                                                          entitlement : </div>
                                                      </td>
                                                      <%
													  int alTaken = 0;
													  int llTaken = 0;
													  LeaveStock leaveStock = new LeaveStock();
													  if ((oidLeave != 0) || (oidEmployee != 0)) {
													  		//out.println(employee.getOID());
													  
													  		whereClause = PstLeaveStock.fieldNames[PstLeaveStock.FLD_EMPLOYEE_ID]+"="+employee.getOID();
															Vector temp = PstLeaveStock.list(0,0,whereClause , null);
															
															if(temp!=null && temp.size()>0){
																leaveStock = (LeaveStock)temp.get(0);
															}
													  		llTaken = SessLeave.getTakenLongLeave(employee.getOID());
															alTaken = SessLeave.getTakenAnualLeave(employee.getOID());
													  }
													  %>
                                                      <td width="26%"> 
                                                        <!--input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_AL_ENTITLEMENT]%>" value="<%=leave.getAlEntitlement()%>" class="formElemen" size="5"-->
                                                        <input type="text" name="AL_AMOUNT" value="<%=leaveStock.getAlAmount()%>" class="formElemen" size="10" disabled="true">
                                                        <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_AL_ENTITLEMENT)%> 
                                                      </td>
                                                      <td width="20%"> 
                                                        <div align="right">Leave 
                                                          entitlement : </div>
                                                      </td>
                                                      <td width="38%"> 
                                                        <div align="left"> 
                                                          <!--input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_LL_ENTITLEMENT]%>" value="<%=leave.getLlEntitlement()%>" class="formElemen" size="5"-->
                                                          <input type="text" name="LL_AMOUNT" value="<%=leaveStock.getLlAmount()%>" class="formElemen" size="10" disabled="true">
                                                          <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_LL_ENTITLEMENT)%></div>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="16%"> 
                                                        <div align="right">Leave 
                                                          taken : </div>
                                                      </td>
                                                      <td width="26%"> 
                                                        <!--input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_AL_TAKEN]%>" value="<%=leave.getAlTaken()%>" class="formElemen" size="5"-->
                                                        <input type="text" name="AL_TAKEN" value="<%=alTaken%>" class="formElemen" size="10" disabled="true">
                                                        <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_AL_TAKEN)%></td>
                                                      <td width="20%"> 
                                                        <div align="right">Leave 
                                                          taken : </div>
                                                      </td>
                                                      <td width="38%"> 
                                                        <div align="left"> 
                                                          <!--input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_LL_TAKEN]%>" value="<%=leave.getLlTaken()%>" class="formElemen" size="5"-->
                                                          <input type="text" name="LL_TAKEN" value="<%=llTaken%>" class="formElemen" size="10" disabled="true">
                                                          <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_LL_TAKEN)%></div>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="16%"> 
                                                        <div align="right">Balance 
                                                          : </div>
                                                      </td>
                                                      <td width="26%"> 
                                                        <!--input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_AL_BALANCE]%>" value="<%=leave.getAlBalance()%>" class="formElemen" size="5"-->
                                                        <input type="text" name="AL_BALANCE" value="<%=leaveStock.getAlAmount() - alTaken%>" class="formElemen" size="10" disabled="true">
                                                        <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_AL_BALANCE)%></td>
                                                      <td width="20%"> 
                                                        <div align="right">Balance 
                                                          : </div>
                                                      </td>
                                                      <td width="38%"> 
                                                        <div align="left"> 
                                                          <!--input type="text" name="<%=FrmLeave.fieldNames[FrmLeave.FRM_FIELD_LL_BALANCE]%>" value="<%=leave.getLlBalance()%>" class="formElemen" size="5"-->
                                                          <input type="text" name="LL_BALANCE" value="<%=leaveStock.getLlAmount() - llTaken%>" class="formElemen" size="10" disabled="true">
                                                          <%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_LL_BALANCE)%></div>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <div align="center"><b>APPROVAL 
                                                    BY </b></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="32%" nowrap> 
                                                        <div align="center">SUPERVISOR</div>
                                                      </td>
                                                      <td width="32%" nowrap> 
                                                        <div align="center">DEPARTMENT 
                                                          HEAD</div>
                                                      </td>
                                                      <td width="32%" nowrap> 
                                                        <div align="center">PERSONNEL 
                                                          MANAGER </div>
                                                      </td>
                                                      <td width="2%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="30%" nowrap> 
                                                        <div align="center">Date 
                                                          : </div>
                                                      </td>
                                                      <td width="38%" nowrap> 
                                                        <div align="center">Date 
                                                          : </div>
                                                      </td>
                                                      <td width="28%" nowrap> 
                                                        <div align="center">Date 
                                                          : </div>
                                                      </td>
                                                      <td width="2%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="30%" nowrap> 
                                                        <div align="center"><%=ControlDate.drawDate(FrmLeave.fieldNames[FrmLeave.FRM_FIELD_APR_SPV_DATE], leave.getAprSpvDate(),"formElemen", 1, -5)%><%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_APR_SPV_DATE)%></div>
                                                      </td>
                                                      <td width="38%" nowrap> 
                                                        <div align="center"><%=ControlDate.drawDate(FrmLeave.fieldNames[FrmLeave.FRM_FIELD_APR_DEPTHEAD_DATE], leave.getAprDeptheadDate(),"formElemen", 1, -5)%><%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_APR_DEPTHEAD_DATE)%></div>
                                                      </td>
                                                      <td width="28%" nowrap> 
                                                        <div align="center"><%=ControlDate.drawDate(FrmLeave.fieldNames[FrmLeave.FRM_FIELD_APR_PMGR_DATE], leave.getAprPmgrDate(),"formElemen", 1, -5)%><%=frmLeave.getErrorMsg(FrmLeave.FRM_FIELD_APR_PMGR_DATE)%></div>
                                                      </td>
                                                      <td width="2%">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80");
							String scomDel = "javascript:cmdAsk('"+oidLeave+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidLeave+"')";
							String scancel = "javascript:cmdEdit('"+oidLeave+"')";
							ctrLine.setBackCaption("Back to List Leave");
							ctrLine.setCommandStyle("buttonlink");
							ctrLine.setConfirmDelCaption("Yes Delete Leave");
							ctrLine.setDeleteCaption("Delete Leave");
							ctrLine.setSaveCaption("Save Leave");

							if (privDelete || privDeleteSpec){
								ctrLine.setConfirmDelCommand(sconDelCom);
								ctrLine.setDeleteCommand(scomDel);
								ctrLine.setEditCommand(scancel);
							}else{ 
								ctrLine.setConfirmDelCaption("");
								ctrLine.setDeleteCaption("");
								ctrLine.setEditCaption("");
							}

							if((!privAdd && !privAddSpec) && (!privUpdate && privUpdateSpec)){
								ctrLine.setSaveCaption("");
							}

							if (!privAdd && !privAddSpec){
								ctrLine.setAddCaption("");
							}
							%>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%></td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                      </table>
                                      <table width="100%" cellspacing="0" cellpadding="0" >
                                        <tr align="left"> 
                                          <td colspan="3">&nbsp; </td>
                                        </tr>
                                      </table>
                                    </form>
									<%}else{%>
									<div align="center">You do not have sufficient privilege to access this page.</div>
									<%}%>
                                    <!-- #EndEditable -->
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
