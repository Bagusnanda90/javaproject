
<% 
/* 
 * Page Name  		:  recognition_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%//@ include file = "../../main/javainit.jsp" %>
<%// int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_GENERAL   , AppObjInfo.OBJ_SG_RECOGNITION   	); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//	boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_RECOGNITION , AppObjInfo.OBJ_UPDATE_PER_EMPLOYEE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%

	CtrlRecognition ctrlRecognition = new CtrlRecognition(request);
	long oidRecognition = FRMQueryString.requestLong(request, "hidden_recognition_id");

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlRecognition.action(iCommand , oidRecognition);

	errMsg = ctrlRecognition.getMessage();
	FrmRecognition frmRecognition = ctrlRecognition.getForm();
	Recognition recognition = ctrlRecognition.getRecognition();
	oidRecognition = recognition.getOID();

	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmRecognition.errorSize()<1)){
	%>
        <jsp:forward page="recognition_list.jsp"> 
        <jsp:param name="start" value="<%=start%>" />
        <jsp:param name="hidden_recognition_id" value="<%=recognition.getOID()%>" />
        </jsp:forward>
        <%
	}

        long oidEmployee = recognition.getEmployeeId();
        Employee employee = new Employee();
        long oidPosition = 0;
        Position position = new Position();
        long oidDepartment = 0;
        Department department = new Department();
        if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {
            employee = PstEmployee.fetchExc(oidEmployee);
            oidPosition = employee.getPositionId();
            position = PstPosition.fetchExc(oidPosition);
            oidDepartment = employee.getDepartmentId();
            department = PstDepartment.fetchExc(oidDepartment);
        }
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Recognition</title>
<script language="JavaScript">

	function cmdCancel(){
		document.frm_recognition.command.value="<%=Command.CANCEL%>";
		document.frm_recognition.action="recognition_edit.jsp";
		document.frm_recognition.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_recognition.command.value="<%=Command.EDIT%>";
		document.frm_recognition.action="recognition_edit.jsp";
		document.frm_recognition.submit(); 
	} 

	function cmdSave(){
		document.frm_recognition.command.value="<%=Command.SAVE%>"; 
		document.frm_recognition.action="recognition_edit.jsp";
		document.frm_recognition.submit();
	}

	function cmdAsk(oid){
		document.frm_recognition.command.value="<%=Command.ASK%>"; 
		document.frm_recognition.action="recognition_edit.jsp";
		document.frm_recognition.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_recognition.command.value="<%=Command.DELETE%>";
		document.frm_recognition.action="recognition_edit.jsp"; 
		document.frm_recognition.submit();
	}  

	function cmdBack(){
		document.frm_recognition.command.value="<%=Command.FIRST%>"; 
		document.frm_recognition.action="recognition_list.jsp";
		document.frm_recognition.submit();
	}

	function cmdSearchEmp(){
            window.open("empdopsearch.jsp?emp_number=" + document.frm_recognition.EMP_NUMBER.value + "&emp_fullname=" + document.frm_recognition.EMP_FULLNAME.value + "&emp_department=" + document.frm_recognition.EMP_DEPARTMENT.value, null, "height=600,width=600,status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
	}

	function cmdClearSearchEmp() {
		document.frm_recognition.EMP_FULLNAME.value = "";
		document.frm_recognition.EMP_NUMBER.value = "";
		document.frm_recognition.EMP_POSITION.value = "";
		document.frm_recognition.EMP_COMMENCING_DATE.value = "";
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
                  Employee &gt; Recognition<!-- #EndEditable --> </strong></font> 
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_recognition" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_recognition_id" value="<%=oidRecognition%>">
                                      <table width="100%" cellspacing="1" cellpadding="1" border="0">
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td colspan="2" class="comment">*) entry 
                                            required</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%"> </td>
                                          <td width="12%">&nbsp;</td>
                                          <td width="86%">&nbsp;</td>
                                        </tr>
                                        <% if ((iCommand == Command.EDIT) || (iCommand == Command.ASK)) { %>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  >Payroll 
                                          </td>
                                          <td  width="86%"  valign="top"> 
                                            <input type="hidden" name="<%=FrmRecognition.fieldNames[FrmRecognition.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=recognition.getEmployeeId()%>" class="formElemen">
                                            <%=frmRecognition.getErrorMsg(FrmRecognition.FRM_FIELD_EMPLOYEE_ID)%> 
                                            <%
                                                //Employee employee = PstEmployee.fetchExc(recognition.getEmployeeId());
                                                //Department department = PstDepartment.fetchExc(employee.getDepartmentId());
                                                //Position position = PstPosition.fetchExc(employee.getPositionId());
                                            %>
                                            <%=employee.getEmployeeNum()%> </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  >Full 
                                            Name</td>
                                          <td  width="86%"  valign="top"> <%=employee.getFullName()%> 
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  ><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td  width="86%"  valign="top"> <%=department.getDepartment()%> 
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  >Position</td>
                                          <td  width="86%"  valign="top"> <%=position.getPosition()%> 
                                          </td>
                                        </tr>
                                        <% }
                                            else if ((iCommand == Command.ADD) || (iCommand == Command.SAVE)) {
                                        %>
                                        <%-- <input type="hidden" name="<%=FrmRecognition.fieldNames[FrmRecognition.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=recognition.getEmployeeId()%>" class="formElemen">
                                            <%=frmRecognition.getErrorMsg(FrmRecognition.FRM_FIELD_EMPLOYEE_ID)%> --%>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td colspan="2"  valign="top"  > 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <input type="hidden" name="<%=FrmRecognition.fieldNames[FrmRecognition.FRM_FIELD_EMPLOYEE_ID]%>" value="<%//=dayOfPayment.getEmployeeId()%>" class="formElemen">
                                                <td width="12%" nowrap> 
                                                  <div align="left">Name</div>
                                                </td>
                                                <td width="28%" nowrap> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= employee.getFullName() %> 
                                                  <% } else {%>
                                                  : 
                                                  <input type="text" name="EMP_FULLNAME" size="30">
                                                  * <%=frmRecognition.getErrorMsg(FrmRecognition.FRM_FIELD_EMPLOYEE_ID)%> 
                                                  <% } %>
                                                </td>
                                                <td width="10%" nowrap> 
                                                  <div align="right">Employee 
                                                    Number</div>
                                                </td>
                                                <td width="50%"> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= employee.getEmployeeNum() %> 
                                                  <% } else {%>
                                                  : 
                                                  <input type="text" name="EMP_NUMBER">
                                                  <% } %>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="12%" nowrap> 
                                                  <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                </td>
                                                <td width="28%" nowrap> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= position.getPosition() %> 
                                                  <% } else {%>
                                                  : 
                                                  <input type="text" name="EMP_POSITION" readonly size="30">
                                                  <% } %>
                                                </td>
                                                <td width="10%" nowrap> 
                                                  <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                </td>
                                                <td width="50%"> : 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= department.getDepartment() %> 
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
                                                <td width="28%" nowrap> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy") %> 
                                                  <% } else {%>
                                                  : 
                                                  <input type="text" name="EMP_COMMENCING_DATE" readonly>
                                                  <% } %>
                                                </td>
                                                <td width="10%" nowrap> 
                                                  <div align="right"></div>
                                                </td>
                                                <td width="50%"> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <% } else {%>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                      <td width="24"><a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                      <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td class="command" nowrap width="99"> 
                                                        <div align="left"><a href="javascript:cmdSearchEmp()">Search 
                                                          Employee</a></div>
                                                      </td>
                                                      <td width="10"><img src="<%=approot%>/images/spacer.gif" width="10" height="4"></td>
                                                      <td width="24"><a href="javascript:cmdClearSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Employee"></a></td>
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
                                          <% } %>
                                        </tr>
                                        <tr align="left">
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td colspan="2"  valign="top"  >
                                            <hr>
                                          </td>
                                        </tr>
                                        <%-- <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  >Employee</td>
                                          <td  width="86%"  valign="top"> 
                                            <%
                                          Vector listEmployee = PstEmployee.list(0, 0, "RESIGNED = 0", "EMPLOYEE_NUM");
                                          Vector empKey = new Vector(1,1);
                                          Vector empValue = new Vector(1,1);
                                          for(int i =0;i <listEmployee.size();i++){
                                                Employee employee = (Employee)listEmployee.get(i);
                                                Department dept = PstDepartment.fetchExc(employee.getDepartmentId());
                                                empKey.add(employee.getEmployeeNum() + " - " + employee.getFullName() + " - (" + dept.getDepartment() + ")");
                                                empValue.add(""+employee.getOID());
                                          }
                                          %>
                                            <%=ControlCombo.draw(FrmRecognition.fieldNames[FrmRecognition.FRM_FIELD_EMPLOYEE_ID],"select...",""+recognition.getEmployeeId(),empValue,empKey)%> 
                                            <% } %>
                                          </td>
                                        </tr>--%>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  >Recognition 
                                            Date</td>
                                          <td  width="86%"  valign="top"> <%=ControlDate.drawDateWithStyle(FrmRecognition.fieldNames[FrmRecognition.FRM_FIELD_RECOG_DATE], (recognition.getRecogDate()==null) ? new Date() : recognition.getRecogDate(), 1, -5, "formElemen", "")%> 
                                            * <%=frmRecognition.getErrorMsg(FrmRecognition.FRM_FIELD_RECOG_DATE)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  >Point</td>
                                          <td  width="86%"  valign="top"> 
                                            <input type="text" name="<%=FrmRecognition.fieldNames[FrmRecognition.FRM_FIELD_POINT]%>" value="<%=recognition.getPoint()%>" class="formElemen" size="3">
                                            * <%=frmRecognition.getErrorMsg(FrmRecognition.FRM_FIELD_POINT)%></td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="3"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80");
							String scomDel = "javascript:cmdAsk('"+oidRecognition+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidRecognition+"')";
							String scancel = "javascript:cmdEdit('"+oidRecognition+"')";
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
