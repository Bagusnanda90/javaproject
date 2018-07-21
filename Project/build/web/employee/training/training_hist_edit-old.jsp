
<% 
/* 
 * Page Name  		:  training_hist_edit.jsp
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

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_TRAINING_HISTORY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<!-- Jsp Block -->
<%
	CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
	long oidTrainingHistory = FRMQueryString.requestLong(request, "hidden_training_history_id");
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();
	iErrCode = ctrlTrainingHistory.action(iCommand , oidTrainingHistory);
	errMsg = ctrlTrainingHistory.getMessage();
	FrmTrainingHistory frmTrainingHistory = ctrlTrainingHistory.getForm();
	TrainingHistory trainingHistory = ctrlTrainingHistory.getTrainingHistory();
	oidTrainingHistory = trainingHistory.getOID();
	
	long oidEmployee = trainingHistory.getEmployeeId();
	//out.println("oidEmployee "+oidEmployee);
        Employee employee = new Employee();
        long oidPosition = 0;
        Position position = new Position();
        long oidDepartment = 0;
        Department department = new Department();
        if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {
            try{
                employee = PstEmployee.fetchExc(oidEmployee);
                oidPosition = employee.getPositionId();
                position = PstPosition.fetchExc(oidPosition);
                oidDepartment = employee.getDepartmentId();
                department = PstDepartment.fetchExc(oidDepartment);
            }catch(Exception exc){
                employee = new Employee();
                position = new Position();
                department = new Department();
            }
        }
	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmTrainingHistory.errorSize()<1)){
	%>
	<jsp:forward page="training_hist_list.jsp"> 
	<jsp:param name="start" value="<%=start%>" />
	<jsp:param name="hidden_training_history_id" value="<%=trainingHistory.getOID()%>" />
	</jsp:forward>
	<%
	}
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training</title>
<script language="JavaScript">
	function cmdCancel(){
		document.fredit.command.value="<%=Command.CANCEL%>";
		document.fredit.action="training_hist_edit.jsp";
		document.fredit.submit();
	} 

	function cmdEdit(oid){ 
		document.fredit.command.value="<%=Command.EDIT%>";
		document.fredit.action="training_hist_edit.jsp";
		document.fredit.submit(); 
	} 

	function cmdSave(){
		document.fredit.command.value="<%=Command.SAVE%>"; 
		document.fredit.action="training_hist_edit.jsp";
		document.fredit.submit();
	}

	function cmdAsk(oid){
		document.fredit.command.value="<%=Command.ASK%>"; 
		document.fredit.action="training_hist_edit.jsp";
		document.fredit.submit();
	} 

	function cmdConfirmDelete(oid){
		document.fredit.command.value="<%=Command.DELETE%>";
		document.fredit.action="training_hist_edit.jsp"; 
		document.fredit.submit();
	}  

	function cmdBack(){
		document.fredit.command.value="<%=Command.FIRST%>"; 
		document.fredit.action="training_hist_list.jsp";
		document.fredit.submit();
	}
	function cmdSearchEmp(){
            window.open("empdopsearch.jsp?emp_number=" + document.fredit.EMP_NUMBER.value + "&emp_fullname=" + document.fredit.EMP_FULLNAME.value + "&emp_department=" + document.fredit.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
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
    	//document.fredit.EMP_DEPARTMENT.style.visibility = "hidden";
    } 
	 
    function hideObjectForLockers(){ 
    }
	
    function hideObjectForCanteen(){
    }
	
    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    	//document.fredit.EMP_DEPARTMENT.style.visibility = "hidden";
    }
	
    function showObjectForMenu(){
        //document.fredit.EMP_DEPARTMENT.style.visibility = "";
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Training &gt; Training History<!-- #EndEditable --> </strong></font> 
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
                                    <form name="fredit" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_training_history_id" value="<%=oidTrainingHistory%>">
                                      <input type="hidden" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=trainingHistory.getEmployeeId()%>">
                                      <table width="100%" cellspacing="2" cellpadding="1" >
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2"  valign="top" align="left"  > 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="11%" nowrap> 
                                                  <div align="left">Name</div>
                                                </td>
                                                <td width="27%" nowrap> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= employee.getFullName() %> 
                                                  <% } else {%>
                                                  <input type="text" name="EMP_FULLNAME" size="30">
                                                  <% } %>
                                                </td>
                                                <td width="9%" nowrap> 
                                                  <div align="left">Employee Number</div>
                                                </td>
                                                <td width="53%"> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= employee.getEmployeeNum() %> 
                                                  <% } else {%>
                                                  <input type="text" name="EMP_NUMBER">
                                                  <% } %>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%" nowrap> 
                                                  <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                </td>
                                                <td width="27%" nowrap> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= position.getPosition() %> 
                                                  <% } else {%>
                                                  <input type="text" name="EMP_POSITION" readonly size="30">
                                                  <% } %>
                                                </td>
                                                <td width="9%" nowrap> 
                                                  <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                </td>
                                                <td width="53%"> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= department.getDepartment() %> 
                                                  <% } else {%>
                                                  <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                                  <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);
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
                                                <td width="11%" nowrap> 
                                                  <div align="left">Commencing 
                                                    Date</div>
                                                </td>
                                                <td width="27%" nowrap> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <%= Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy") %> 
                                                  <% } else {%>
                                                  <input type="text" name="EMP_COMMENCING_DATE" readonly>
                                                  <% } %>
                                                </td>
                                                <td width="9%" nowrap> 
                                                  <div align="left"></div>
                                                </td>
                                                <td width="53%"> 
                                                  <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                  <% } else {%>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td width="15"><a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                      <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td class="command" nowrap width="99"> 
                                                        <div align="left"><a href="javascript:cmdSearchEmp()">Search 
                                                          Employee</a></div>
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
                                          <td colspan="3"  valign="top" align="left"  > 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >&nbsp;</td>
                                          <td  width="88%"  valign="top" align="left" class="comment">*) 
                                            entry required</td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Training 
                                            Program</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <textarea name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_PROGRAM]%>" class="formElemen" cols="30" rows="2" wrap="VIRTUAL"><%=trainingHistory.getTrainingProgram()%></textarea>
                                            * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_TRAINING_PROGRAM)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Start 
                                            Date</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <%=ControlDate.drawDateWithStyle(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_DATE], (trainingHistory.getStartDate()==null) ? new Date() : trainingHistory.getStartDate(), 0, -4, "formElemen", "")%> 
                                            * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_START_DATE)%> 
                                            &nbsp;&nbsp;to &nbsp;&nbsp;<%=ControlDate.drawDateWithStyle(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_DATE], (trainingHistory.getEndDate()==null) ? new Date() : trainingHistory.getEndDate(), 0, -4, "formElemen", "")%> 
                                            * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_END_DATE)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Trainer</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <input type="text" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINER]%>" value="<%=trainingHistory.getTrainer()%>" class="formElemen" size="40">
                                            * <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_TRAINER)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Remark</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <textarea name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_REMARK]%>" cols="30" class="formElemen" rows="3"><%=trainingHistory.getRemark()%></textarea>
                                            <%=frmTrainingHistory.getErrorMsg(FrmTrainingHistory.FRM_FIELD_REMARK)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%"  valign="top" align="left"  >&nbsp;</td>
                                          <td  width="88%"  valign="top" align="left">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="3"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("90");
							String scomDel = "javascript:cmdAsk('"+oidTrainingHistory+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidTrainingHistory+"')";
							String scancel = "javascript:cmdEdit('"+oidTrainingHistory+"')";
							ctrLine.setBackCaption("Back to List Training History");
							ctrLine.setDeleteCaption("Delete Training History");
							ctrLine.setSaveCaption("Save Training History");
							ctrLine.setConfirmDelCaption("Yes Delete Training History");
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
