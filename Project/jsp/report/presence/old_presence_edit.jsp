
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
String empNum= FRMQueryString.requestString(request, "hidden_emp_num");

int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";
String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"='"+empNum+"'";
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


/*long oidEmployee = presence.getEmployeeId();
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
}*/
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Manual Presence</title>
<script language="JavaScript">

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
                                    <form name="frpresence" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_emp_num" value="<%=empNum%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
										  <%
										  	Vector listEmp = PstEmployee.list(0,0,whereClause,"");
											System.out.println("whereClause  "+whereClause);
										  %>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                              <tr> 
                                                <td> 
                                                  <div align="center"><b><font size="3">MANUAL 
                                                    STATUS OF PRESENCE</font></b></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <input type="hidden" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=presence.getEmployeeId()%>" class="formElemen">
                                                      <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%> 
                                                      <td width="12%" nowrap> 
                                                        <div align="left">Name</div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%//= employee.getFullName() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_FULLNAME" size="30"> * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%> 
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left">Employee 
                                                          Number</div>
                                                      </td>
                                                      <td width="49%"> : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%//= employee.getEmployeeNum() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_NUMBER">
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%//= position.getPosition() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_POSITION" readonly size="30">
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="49%"> : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%//= department.getDepartment() %> 
                                                        <% } else {%>
                                                        <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
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
                                                            dept_value.add("0");
                                                            dept_key.add("...ALL (may take long time)...");
                                                        %>
                                                        <%= ControlCombo.draw("EMP_DEPARTMENT","formElemen",null, "", dept_value, dept_key) %> 
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" nowrap> 
                                                        <div align="left">Commencing 
                                                          Date</div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%//= Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy") %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_COMMENCING_DATE" readonly>
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="49%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <% } else {%>
                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="15"><a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                            <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td class="command" nowrap width="99"> 
                                                              <div align="left"><a href="javascript:cmdSearchEmp()">Search 
                                                                Employee</a></div>
                                                            </td>
                                                            <td width="10"><img src="<%=approot%>/images/spacer.gif" width="10" height="4"></td>
                                                            <td width="15"><a href="javascript:cmdClearSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Schedule"></a></td>
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
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td width="12%" valign="top" nowrap> 
                                                        <div align="left">Presence 
                                                          Date Time</div>
                                                      </td>
                                                      <td width="88%"> : 
                                                        <%-- <%//=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATE], presence.getPresenceDate() != null ? presence.getPresenceDate() : new Date(),"formElemen", 1, -5)%>
                                                        <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_PRESENCE_DATE)%>
                                                        <input type="text" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME]%>" value="<%=presence.getPresenceDatetime()%>" class="formElemen">
                                                        --%>
                                                        <%=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], presence.getPresenceDatetime() != null ? presence.getPresenceDatetime() : new Date(),"formElemen", 1, -5)%> 
                                                        <%=ControlDate.drawTime(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], presence.getPresenceDatetime() != null ? presence.getPresenceDatetime() : new Date(),"formElemen", 24, 1, 0)%> 
                                                        * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_PRESENCE_DATETIME)%> 
                                                      </td>
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
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
