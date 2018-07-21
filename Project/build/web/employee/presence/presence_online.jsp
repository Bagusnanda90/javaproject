
<% 
/* 
 * Page Name  		:  presence_edit.jsp
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

<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PRESENCE, AppObjInfo.OBJ_MANUAL_PRESENCE); %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_PRESENCE   	); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%
CtrlPresence ctrlPresence = new CtrlPresence(request);
long oidPresence = FRMQueryString.requestLong(request, "hidden_presence_id");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");

int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";
String whereClause = "";
String orderClause = "";
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request,"start");

ControlLine ctrLine = new ControlLine();
iErrCode = ctrlPresence.action(iCommand , oidPresence, request);
errMsg = ctrlPresence.getMessage();
FrmPresence frmPresence = ctrlPresence.getForm();
Presence presence = ctrlPresence.getPresence();
oidPresence = presence.getOID();

if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmPresence.errorSize()<1))
{
	%>
	<jsp:forward page="presence_list.jsp"> 
	<jsp:param name="prev_command" value="<%=prevCommand%>" />
	<jsp:param name="command" value="<%=Command.BACK%>" />		
	<jsp:param name="start" value="<%=start%>" />
	<jsp:param name="hidden_presence_id" value="<%=presence.getOID()%>" />
	</jsp:forward>
	<%
}

long oidEmployee = presence.getEmployeeId();
Employee employee = new Employee();
long oidPosition = 0;
Position position = new Position();
long oidDepartment = 0;
Department department = new Department();
if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) 
{
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
<title>HARISMA - Manual Presence</title>
<script language="JavaScript">
<!--



function cmdCancel(){
		document.frm_presence.command.value="<%=Command.ADD%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	} 
	function cmdCancel(){
		document.frm_presence.command.value="<%=Command.CANCEL%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_presence.command.value="<%=Command.EDIT%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit(); 
	} 

	function cmdSave(){
		document.frm_presence.command.value="<%=Command.SAVE%>"; 
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	}

	function cmdAsk(oid){
		document.frm_presence.command.value="<%=Command.ASK%>"; 
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_presence.command.value="<%=Command.DELETE%>";
		document.frm_presence.action="presence_edit.jsp"; 
		document.frm_presence.submit();
	}  

	function cmdBack(){
		document.frm_presence.command.value="<%=Command.FIRST%>"; 
		document.frm_presence.action="presence_list.jsp";
		document.frm_presence.submit();
	}

	function cmdSearchEmp(){
            window.open("emppresencesearch.jsp?emp_number=" + document.frm_presence.EMP_NUMBER.value + "&emp_fullname=" + document.frm_presence.EMP_FULLNAME.value + "&emp_department=" + document.frm_presence.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}

	function cmdClearSearchEmp() {
		document.frm_presence.EMP_FULLNAME.value = "";
		document.frm_presence.EMP_NUMBER.value = "";
		document.frm_presence.EMP_POSITION.value = "";
		document.frm_presence.EMP_COMMENCING_DATE.value = "";
	}
//-------------- script control line -------------------
//-->
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
	<% if((iCommand == Command.ADD) || (iCommand == Command.SAVE && frmPresence.errorSize()>0)){%>
		document.frm_presence.EMP_DEPARTMENT.style.visibility="hidden";
	<% }%>
}
function showObjectForMenu(){
	<% if((iCommand == Command.ADD) || (iCommand == Command.SAVE && frmPresence.errorSize()>0)){%>
		document.frm_presence.EMP_DEPARTMENT.style.visibility="";
	<% }%>
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
			  <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Attendance &gt; Manual Presence<!-- #EndEditable --> 
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
                                    <form name="frm_presence" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_presence_id" value="<%=oidPresence%>">
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
                                                  <div align="center"><b><font size="3">ONLINE 
                                                    REGISTRATION OF PRESENCE</font></b></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td align="center"> 
                                                  <table width="50%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td width="10%" nowrap>Date 
                                                        / Time</td>
                                                      <td width="49%">: ( automatis 
                                                        di set dari server time 
                                                        ); dan akan di record 
                                                        saat posting dilakukan. 
                                                        Bukan yang tampil saat 
                                                        entry </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" valign="top" nowrap> 
                                                        <div align="left">Status</div>
                                                      </td>
                                                      <td width="88%"> : 
                                                        <%-- <%//=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_TIME_IN], presence.getTimeIn(),"formElemen", 1, -5)%>
                                                        <%//=ControlDate.drawTime(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_TIME_IN], presence.getTimeIn(), "elemenForm", 24, 1, 0) %> 
                                                        <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_TIME_IN)%>
                                                        <input type="text" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS]%>" value="<%=presence.getStatus()%>" class="formElemen">
                                                        <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_STATUS)%> --%>
                                                        <% 
                                                            Vector status_value = new Vector(1,1);
                                                            Vector status_key = new Vector(1,1);
                                                            //Vector listDept = PstDepartment.listAll();
                                                            //for (int i = 0; i < listDept.size(); i++) {
                                                                    //Department dept = (Department) listDept.get(i);
                                                                    status_key.add("In"); status_value.add("0");
                                                                    status_key.add("Out - Home"); status_value.add("1");
                                                                    status_key.add("Out - On duty"); status_value.add("2");
                                                                    status_key.add("In - Lunch"); status_value.add("3");
                                                                    status_key.add("In - Break"); status_value.add("4");
                                                                    status_key.add("In - Callback"); status_value.add("5");
                                                            //}
                                                        %>
                                                        <%= ControlCombo.draw(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS],"formElemen",null, String.valueOf(presence.getStatus()), status_value, status_key) %> 
                                                        * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_STATUS)%> 
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" nowrap>&nbsp;</td>
                                                      <td width="49%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <input type="hidden" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=presence.getEmployeeId()%>" class="formElemen">
                                                      <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%>
                                                      <td width="10%" nowrap> 
                                                        <div align="left">Employee 
                                                          Number</div>
                                                      </td>
                                                      <td width="49%"> : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= employee.getEmployeeNum() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_NUMBER">
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" nowrap>Employee 
                                                        Private Code </td>
                                                      <td width="49%"> : 
                                                        <input type="text" name="EMP_NUMBER2">
                                                        ( ini adalah kode masing2 
                                                        orang, contoh : <br>
                                                        jumlah gaji pokok+6-code 
                                                        atm : 500.000-123456 . 
                                                        Ini akan ditambahkan di 
                                                        Employee master data, 
                                                        dlm bentuk spt password. 
                                                        ( ada confirmnya )</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" nowrap>Employee 
                                                        Name </td>
                                                      <td width="49%">: tampil 
                                                        setelah submit</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="49%"> : tampil 
                                                        setelah submit</td>
                                                    </tr>
                                                    <tr>
                                                      <td width="10%" nowrap>Employment 
                                                        Type </td>
                                                      <td width="49%">: tampil 
                                                        setelah submit ( fixed, 
                                                        trainee, contract, sesuai 
                                                        yg di set di data employee)</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" nowrap>&nbsp;</td>
                                                      <td width="49%"> 
                                                        <input type="submit" name="Submit" value="Submit">
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
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td width="12%" valign="top" nowrap>&nbsp;</td>
                                                      <td width="88%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" valign="top" nowrap>&nbsp;</td>
                                                      <td width="88%">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td>
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
							String scomDel = "javascript:cmdAsk('"+oidPresence+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidPresence+"')";
							String scancel = "javascript:cmdEdit('"+oidPresence+"')";
							ctrLine.setBackCaption("Back to List Presence");
							ctrLine.setCommandStyle("buttonlink");
							ctrLine.setConfirmDelCaption("Yes Delete Presence");
							ctrLine.setDeleteCaption("Delete Presence");
							ctrLine.setSaveCaption("Save Presence");

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
                                          <td width="2%">&nbsp;</td>
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
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
