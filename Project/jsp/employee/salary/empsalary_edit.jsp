
<% 
/* 
 * Page Name  		:  empsalary_edit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_SALARY, AppObjInfo.OBJ_EMP_SALARY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
    /*boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));*/
    //out.print("privView=" + privView + " | privAdd=" + privAdd);
%>

<%// int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_SALARY, AppObjInfo.OBJ_EMP_SALARY); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int prevCommand = FRMQueryString.requestInt(request,"prev_command");
    int start = FRMQueryString.requestInt(request,"start");
    long oidEmpSalary = FRMQueryString.requestLong(request,"emp_salary_oid");
    //out.println("oidEmpSalary "+oidEmpSalary);
    int iErrCode = FRMMessage.ERR_NONE;
    String errMsg = "";
    String whereClause = "";
    String orderClause = "";

    CtrlEmpSalary ctrlEmpSalary = new CtrlEmpSalary(request);
    ControlLine ctrLine = new ControlLine();

    iErrCode = ctrlEmpSalary.action(iCommand , oidEmpSalary);

    errMsg = ctrlEmpSalary.getMessage();
    FrmEmpSalary frmEmpSalary = ctrlEmpSalary.getForm();
    EmpSalary empSalary = ctrlEmpSalary.getEmpSalary();
    oidEmpSalary = empSalary.getOID();
    Vector listEmployee = PstEmployee.listAll();

    if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmEmpSalary.errorSize()<1)){
    %>
    <jsp:forward page="empsalary_list.jsp"> 
    <jsp:param name="start" value="<%=start%>" />
    <jsp:param name="prev_command" value="<%=prevCommand%>" />
    <jsp:param name="command" value="<%=iCommand%>" />
    </jsp:forward>
    <%
    }

    long oidEmployee = empSalary.getEmployeeId();
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
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Salary</title>
<script language="JavaScript">
function cmdCancel(){
		document.frm_empsalary.command.value="<%=Command.ADD%>";
		document.frm_empsalary.action="empsalary_edit.jsp";
		document.frm_empsalary.submit();
	} 
	function cmdCancel(){
		document.frm_empsalary.command.value="<%=Command.CANCEL%>";
		document.frm_empsalary.action="empsalary_edit.jsp";
		document.frm_empsalary.submit();
	}
	
	function changeEmp(){
		document.frm_empsalary.command.value="<%=Command.EDIT%>";
		document.frm_empsalary.action="empsalary_edit.jsp";
		document.frm_empsalary.submit(); 		
	} 

	function cmdEdit(oid){ 
		document.frm_empsalary.command.value="<%=Command.EDIT%>";
		document.frm_empsalary.action="empsalary_edit.jsp";
		document.frm_empsalary.submit(); 
	} 

	function cmdSave(){
		document.frm_empsalary.command.value="<%=Command.SAVE%>"; 
		document.frm_empsalary.action="empsalary_edit.jsp";
		document.frm_empsalary.submit();
	}

	function cmdAsk(oid){
		document.frm_empsalary.command.value="<%=Command.ASK%>"; 
		document.frm_empsalary.action="empsalary_edit.jsp";
		document.frm_empsalary.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_empsalary.command.value="<%=Command.DELETE%>";
		document.frm_empsalary.action="empsalary_edit.jsp"; 
		document.frm_empsalary.submit();
	}  

	function cmdBack(){
		document.frm_empsalary.command.value="<%=Command.FIRST%>"; 
		document.frm_empsalary.action="empsalary_list.jsp";
		document.frm_empsalary.submit();
	}

	function cmdSearchEmp(){
            window.open("empdopsearch.jsp?emp_number=" + document.frm_empsalary.EMP_NUMBER.value + "&emp_fullname=" + document.frm_empsalary.EMP_FULLNAME.value + "&emp_department=" + document.frm_empsalary.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	
	function calCurrent()
	{
	  var curr_basic = document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_CURR_BASIC]%>.value;
	  var curr_transport = document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_CURR_TRANSPORT]%>.value;		  							
		  document.frm_empsalary.curr_total.value = parseFloat(curr_basic) + parseFloat(curr_transport);
		  if (document.frm_empsalary.curr_total.value=="NaN")
			{
			   alert("Check Current Salary");
			   document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_CURR_BASIC]%>.value="";
			   document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_CURR_TRANSPORT]%>.value="";
			   document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_CURR_BASIC]%>.focus();
			}else{
				document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_CURR_TOTAL]%>.value=document.frm_empsalary.curr_total.value;
			}
	}
	
	function calNew()
	{
	  new_basic = document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_NEW_BASIC]%>.value;
	  new_transport = document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_NEW_TRANSPORT]%>.value;
		  document.frm_empsalary.new_total.value= parseFloat(new_basic) + parseFloat(new_transport);
		  if (document.frm_empsalary.new_total.value=="NaN")
			{
			   alert("Check New Salary");
			   document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_NEW_BASIC]%>.value="";
			   document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_NEW_TRANSPORT]%>.value="";
			   document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_NEW_BASIC]%>.focus();
			}else{				
			   document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_NEW_TOTAL]%>.value=document.frm_empsalary.new_total.value;		
			}	
	}
	
	function cmdAdditional(){
		additional =  document.frm_empsalary.<%=frmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_ADDITIONAL]%>.value;
		if (additional=="NaN"){
			alert("Check Additional");
		}else{
			document.frm_empsalary.inc_total.value=<%=empSalary.getIncSalary()+empSalary.getIncTransport()%>+ parseFloat(additional);
		}
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
<!--
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
//-->
</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
			  <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Salary &gt; Employee Salary Editor<!-- #EndEditable --> 
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
                                <% if (privView) { %>
                                    <form name="frm_empsalary" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
									  <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="start" value="<%=start%>">
									  <input type="hidden" name="emp_salary_oid" value="<%=oidEmpSalary%>">
									  <input type="hidden" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_CURR_TOTAL]%>" value="<%=empSalary.getCurrTotal()%>">
									  <input type="hidden" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_NEW_TOTAL]%>" value="<%=empSalary.getNewTotal()%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="94%">&nbsp;</td>
                                          <td width="3%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="94%"> 
                                            <table width="100%" cellspacing="2" cellpadding="2"   >
                                              <tr> 
                                                <td colspan="4" class="txtheading1" align="center">EMPLOYEE 
                                                  SALARY </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="4" class="txtheading1" align="center">As 
                                                  Of <%=Formater.formatDate(empSalary.getCurrDate()==null?new Date():empSalary.getCurrDate(), "dd MMMM yyyy")%></td>
                                              </tr>
                                              <tr> 
                                                <td colspan="4" class="txtheading1" align="center">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td colspan="4" class="listedittitle"> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <input type="hidden" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=empSalary.getEmployeeId()%>" class="formElemen">
                                                      <%=frmEmpSalary.getErrorMsg(FrmEmpSalary.FRM_FIELD_EMPLOYEE_ID)%> 
                                                      <td width="19%"> 
                                                        <div align="right">Name 
                                                          : </div>
                                                      </td>
                                                      <td width="29%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= employee.getFullName() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_FULLNAME" size="30">
                                                        <% } %>
                                                      </td>
                                                      <td width="14%"> 
                                                        <div align="right">Employee 
                                                          Number : </div>
                                                      </td>
                                                      <td width="38%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= employee.getEmployeeNum() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_NUMBER">
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="19%"> 
                                                        <div align="right">Position 
                                                          : </div>
                                                      </td>
                                                      <td width="29%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= position.getPosition() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_POSITION" readonly size="30">
                                                        <% } %>
                                                      </td>
                                                      <td width="14%"> 
                                                        <div align="right">Department 
                                                          : </div>
                                                      </td>
                                                      <td width="38%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= department.getDepartment() %> 
                                                        <% } else {%>
                                                        <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                                        <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
                                                            //dept_value.add("0");
                                                            //dept_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.listAll();                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("EMP_DEPARTMENT","formElemen",null, "", dept_value, dept_key) %> 
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="19%"> 
                                                        <div align="right">Commencing 
                                                          Date : </div>
                                                      </td>
                                                      <td width="29%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy") %> 
                                                        <% } else {%>
                                                        <input type="text" name="EMP_COMMENCING_DATE" readonly>
                                                        <% } %>
                                                      </td>
                                                      <td width="14%"> 
                                                        <div align="right"></div>
                                                      </td>
                                                      <td width="38%"> 
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
                                                <td colspan="4"  valign="top"  align="left"  > 
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2" align="right" rowspan="3"> 
                                                  <table width="100%" height="100%" cellspacing="2" cellpadding="2"  >
                                                    <tr> 
                                                      <td width="20%" align="right"  >Length 
                                                        Of Service (LOS)1 : </td>
                                                      <td  width="24%"  valign="top" align="left"> 
                                                        <input type="text" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_LOS1]%>" value="<%if((empSalary.getLos1()-0)>0){%><%=empSalary.getLos1()%><%}%>" class="formElemen" size="10">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="20%" align="right"  >Length 
                                                        Of Service (LOS)2 : </td>
                                                      <td  width="24%"  valign="top" align="left"> 
                                                        <input type="text" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_LOS2]%>" value="<%if((empSalary.getLos2()-0)>0){%><%=empSalary.getLos2()%><%}%>" class="formElemen" size="10">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="20%" align="right"  > 
                                                        Current Basic : </td>
                                                      <td  width="24%"> 
                                                        <input type="text" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_CURR_BASIC]%>" onBlur="javascript:calCurrent()" value="<%=empSalary.getCurrBasic()%>" class="formElemen" size="20">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="20%" align="right"  >Current 
                                                        Transportation : </td>
                                                      <td  width="24%"> 
                                                        <input type="text" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_CURR_TRANSPORT]%>" onBlur="javascript:calCurrent()" value="<%=empSalary.getCurrTransport()%>" class="formElemen" size="20">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="20%" align="right"  >Current 
                                                        Total : </td>
                                                      <td  width="24%"> 
                                                        <input type="text" name="curr_total" value="<%if(((empSalary.getCurrBasic()+ empSalary.getCurrTransport())-0.0f)>0.0f){%><%=empSalary.getCurrBasic()+ empSalary.getCurrTransport()%><%}%>" disabled="true" class="formElemen" size="20">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="20%" align="right"  >New 
                                                        Basic : </td>
                                                      <td  width="24%"> 
                                                        <input type="text" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_NEW_BASIC]%>" value="<%=empSalary.getNewBasic()%>" onBlur="javascript:calNew()" class="formElemen" size="20">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="20%" align="right"  >New 
                                                        Transportation : </td>
                                                      <td  width="24%"> 
                                                        <input type="text" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_NEW_TRANSPORT]%>" value="<%=empSalary.getNewTransport()%>" onBlur="javascript:calNew()" class="formElemen" size="20">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="20%" align="right"  >New 
                                                        Total : </td>
                                                      <td  width="24%"> 
                                                        <input type="text" name="new_total" value="<%=empSalary.getNewBasic()+ empSalary.getNewTransport()%>" disabled="true" class="formElemen" size="20">
                                                      </td>
                                                    </tr>
                                                    <% if(oidEmpSalary == 0){%>
                                                    <tr> 
                                                      <td width="20%" align="right"  >Additional 
                                                        : </td>
                                                      <td  width="24%"> 
                                                        <input type="text" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_ADDITIONAL]%>" value="<%if((empSalary.getAdditional()-0.0f)>0.0f){%><%=empSalary.getAdditional()%><%}%>" class="formElemen" size="20">
                                                      </td>
                                                    </tr>
                                                    <%}%>
                                                  </table>
                                                </td>
                                                <td colspan="2" align="left">	
                                                  <table width="100%" height="100%" cellspacing="2" cellpadding="2"  >
                                                    <% if(oidEmpSalary != 0){%>
                                                    <tr> 
                                                      <td  width="32%" align="right">Increment 
                                                        Basic : </td>
                                                      <td  width="68%" align="left"> 
                                                        <input type="text" name="textfield" disabled="true" size="20" value="<%=empSalary.getIncSalary()%>">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td  width="32%" align="right">Increment 
                                                        Transportation : </td>
                                                      <td  width="68%" align="left"> 
                                                        <input type="text" name="textfield2" disabled="true" size="20" value="<%=empSalary.getIncTransport()%>">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td  width="32%" align="right">Additional 
                                                        : </td>
                                                      <td  width="68%"> 
                                                        <input type="text" name="<%=FrmEmpSalary.fieldNames[FrmEmpSalary.FRM_FIELD_ADDITIONAL]%>" value="<%if((empSalary.getAdditional()-0.0f)>0.0f){%><%=empSalary.getAdditional()%><%}%>" class="formElemen" size="20" onBlur="javascript:cmdAdditional()">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td  width="32%" align="right">Increment 
                                                        Total : </td>
                                                      <td  width="68%"> 
                                                        <input type="text" name="inc_total" disabled="true" size="20" value="<%=empSalary.getIncTotal()%>">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td  width="32%" align="right"> 
                                                        Percentage Basic : </td>
                                                      <td  width="68%"> 
                                                        <input type="text" name="textfield4" disabled="true" size="20" value="<%=empSalary.getPercentageBasic()%>">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td  width="32%" align="right">Percentage 
                                                        Transportation : </td>
                                                      <td  width="68%"> 
                                                        <input type="text" name="textfield5" disabled="true" size="20" value="<%=empSalary.getPercentTransport()%>">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td  width="32%" align="right">Percentage 
                                                        Total : </td>
                                                      <td  width="68%"> 
                                                        <input type="text" name="textfield6" disabled="true" size="20" value="<%=empSalary.getPercentageTotal()%>">
                                                      </td>
                                                    </tr>
                                                    <%}%>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td  width="18%" height="31">&nbsp;</td>
                                                <td  width="38%" height="31">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td  width="18%">&nbsp;</td>
                                                <td  width="38%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="20%" align="right"  >&nbsp;</td>
                                                <td  width="24%">&nbsp;</td>
                                                <td  width="18%">&nbsp;</td>
                                                <td  width="38%">&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td width="3%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="94%">&nbsp;</td>
                                          <td width="3%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="94%"> 
                                            <%
											ctrLine.setLocationImg(approot+"/images");
											ctrLine.initDefault();
											ctrLine.setTableWidth("80");
											String scomDel = "javascript:cmdAsk('"+oidEmpSalary+"')";
											String sconDelCom = "javascript:cmdConfirmDelete('"+oidEmpSalary+"')";
											String scancel = "javascript:cmdEdit('"+oidEmpSalary+"')";
											ctrLine.setBackCaption("Back to List Salary");
											ctrLine.setCommandStyle("buttonlink");
											ctrLine.setSaveCaption("Save Salary");
											ctrLine.setConfirmDelCaption("Yes Delete Salary");
											ctrLine.setDeleteCaption("Delete Salary");
				
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
                                          <td width="3%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="94%">&nbsp;</td>
                                          <td width="3%">&nbsp;</td>
                                        </tr>
                                      </table>
                                    </form>
                                <% } 
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% } %>
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
