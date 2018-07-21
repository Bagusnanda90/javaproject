
<% 
/* 
 * Page Name  		:  employeevisit_edit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [lkarunia] 
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
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_EMPLOYEE_VISIT, AppObjInfo.OBJ_EMPLOYEE_VISIT); %>
<%@ include file = "../../main/checkuser.jsp" %>  
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%

	CtrlEmployeeVisit ctrlEmployeeVisit = new CtrlEmployeeVisit(request);
	long oidEmployeeVisit = FRMQueryString.requestLong(request, "employee_visit_oid");
	int iCommand = FRMQueryString.requestCommand(request);
	int prevCommand = FRMQueryString.requestInt(request,"prev_command");
	int start = FRMQueryString.requestInt(request,"start");
	long employeeOID =  FRMQueryString.requestLong(request, FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_EMPLOYEE_ID]);
	
	//out.println(employeeOID);
	
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	Vector listVisitor = new Vector(1,1);
	
        ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlEmployeeVisit.action(iCommand , oidEmployeeVisit);

	errMsg = ctrlEmployeeVisit.getMessage();
	FrmEmployeeVisit frmEmployeeVisit = ctrlEmployeeVisit.getForm();
	EmployeeVisit employeeVisit = ctrlEmployeeVisit.getEmployeeVisit();
        oidEmployeeVisit = employeeVisit.getOID();
        
        
	Employee employee = new Employee();
	Employee empVisitor = new Employee();
	Position position = new Position();
	Position posVisitor = new Position();
	Department department = new Department();
	Department depVisitor = new Department();
	//out.println("oidEmployeeVisit "+oidEmployeeVisit);
	if (oidEmployeeVisit != 0 ) {
		try{
                    if(!(employeeVisit.getOID()>0)){
                        employeeVisit = PstEmployeeVisit.fetchExc(oidEmployeeVisit);
                    }
                    employee = PstEmployee.fetchExc(employeeVisit.getEmployeeId());
                    empVisitor= PstEmployee.fetchExc(employeeVisit.getVisitedBy());

                    position = PstPosition.fetchExc(employee.getPositionId());
                    department = PstDepartment.fetchExc(employee.getDepartmentId());

                    posVisitor = PstPosition.fetchExc(empVisitor.getPositionId());
                    depVisitor = PstDepartment.fetchExc(empVisitor.getDepartmentId());
                                
		}catch(Exception exc){}		
	}else{
	}
        //out.println("employee "+employee.getFullName());

if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmEmployeeVisit.errorSize()<1)){
	%>
	<jsp:forward page="srcemployeevisit.jsp"> 	
	<jsp:param name="command" value="<%=Command.LIST%>" />
	<jsp:param name="prev_command" value="<%=prevCommand%>" />
	<jsp:param name="employee_visit_oid" value="<%=employeeVisit.getOID()%>" />
	</jsp:forward>
<%
  }
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Employee Visit Editor</title>
<script language="JavaScript">

	function cmdCancel(){
		document.frm_employeevisit.command.value="<%=""+Command.ADD%>";
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	} 
	function cmdCancel(){
		document.frm_employeevisit.command.value="<%=""+Command.CANCEL%>";
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_employeevisit.command.value="<%=""+Command.EDIT%>";
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit(); 
	} 

	function cmdSave(){
		document.frm_employeevisit.command.value="<%=""+Command.SAVE%>"; 
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	}
	
	function cmdAdd(){
		document.frm_employeevisit.command.value="<%=""+Command.ADD%>"; 
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	} 

	function cmdAsk(oid){
		document.frm_employeevisit.command.value="<%=""+Command.ASK%>"; 
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_employeevisit.command.value="<%=""+Command.DELETE%>";
		document.frm_employeevisit.action="employeevisit_edit.jsp"; 
		document.frm_employeevisit.submit();
	}  

	function cmdBack(){
		document.frm_employeevisit.command.value="<%=""+Command.BACK%>"; 
		document.frm_employeevisit.action="srcemployeevisit.jsp";
		document.frm_employeevisit.submit();
	}
	
	function chgDepart(){
		document.frm_employeevisit.command.value="<%=""+Command.LIST%>"; 
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	}
	
	function cmdSearchEmp(){
             window.open("<%=approot%>/employee/search/searchEmp.jsp?formName=frm_employeevisit&firstName=&empPathId=<%=FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
	}
	function cmdSearchEmpVisitor(){
            window.open("<%=approot%>/employee/search/searchEmp.jsp?formName=frm_employeevisit&firstName=V_&empPathId=<%=FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_VISITED_BY]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
	}
	
	function calVisitor() 
	{
	visitor = document.frm_employeevisit.chg_visitor.value;
	document.frm_employeevisit.<%=frmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_VISITED_BY]%>.value=visitor;
	<%
		for(int i = 0; i < listVisitor.size(); i++) {
			Employee visemployee = (Employee)listVisitor.get(i);		
			long oid = visemployee.getOID();
			%>
				if(visitor=="<%=""+oid%>"){
                                  alert("<%=employee.getFullName()%>");
				  document.frm_employeevisit.visitor.value="<%=visemployee.getFullName()%>";
                               }
			<%
		}
	%>
	
	}
	
	function cmdClear(){
		document.frm_employeevisit.<%=FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_EMPLOYEE_ID]%>.value="";
		document.frm_employeevisit.EMP_FULLNAME.value=""; 
		document.frm_employeevisit.EMP_NUMBER.value=""; 
		document.frm_employeevisit.EMP_POSITION.value=""; 
		document.frm_employeevisit.EMP_COMMENCING_DATE.value=""; 
		document.frm_employeevisit.EMP_DEPARTMENT.value=""; 
	}
         
	function cmdClearVisitor(){
		document.frm_employeevisit.<%=FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_VISITED_BY]%>.value="";
		document.frm_employeevisit.V_EMP_FULLNAME.value=""; 
		document.frm_employeevisit.V_EMP_NUMBER.value=""; 
		document.frm_employeevisit.V_EMP_POSITION.value=""; 
		document.frm_employeevisit.V_EMP_COMMENCING_DATE.value=""; 
		document.frm_employeevisit.V_EMP_DEPARTMENT.value=""; 
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
			  <!-- #BeginEditable "contenttitle" -->Clinic 
                  &gt; Employee Visit<!-- #EndEditable --> 
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
                                    <form name="frm_employeevisit" method="post" action="">
                                      <input type="hidden" name="command" value="<%=""+iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=""+prevCommand%>">
                                      <input type="hidden" name="start" value="<%=""+start%>">
                                      <input type="hidden" name="employee_visit_oid" value="<%=""+oidEmployeeVisit%>">
                                      <input type="hidden" name="<%=FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=""+employeeVisit.getEmployeeId()%>">
                                      <input type="hidden" name="<%=FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_VISITED_BY]%>" value="<%=""+employeeVisit.getVisitedBy()%>">
                                      <table width="100%" cellspacing="1" cellpadding="1" >
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">&nbsp;</td>
                                          <td width="27%">&nbsp;</td>
                                          <td width="15%">&nbsp;</td>
                                          <td width="39%">&nbsp;</td>
                                        </tr>										
                                        
                                        <%if(oidEmployeeVisit == 0){%>
                                        <tr> 
                                          <td colspan="5"><b>Change Employee</b></td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Employee Name </td>
                                          <td width="27%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= employee.getFullName() %></b>
                                            <% } else {%>
                                            <input type="text" name="EMP_FULLNAME" size="30" readonly value="<%= employee.getFullName() %>">
                                            <% } %>
                                          </td>
                                          <td width="15%">Employee Number </td>
                                          <td width="39%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= employee.getEmployeeNum() %></b>
                                            <% } else {%>
                                            <input type="text" name="EMP_NUMBER" readonly value="<%= (employee.getEmployeeNum().length()>0) ? (employee.getEmployeeNum().trim()) : "" %>">
                                            <% } %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Employee Position </td>
                                          <td width="27%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= position.getPosition() %></b>
                                            <% } else {%>
                                            <input type="text" name="EMP_POSITION" size="30" readonly value="<%= position.getPosition() %> ">
                                            <% } %>
                                          </td>
                                          <td width="15%">Employee Department 
                                          </td>
                                          <td width="39%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= department.getDepartment() %></b>
                                            <% } else {%>
                                            <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                               <input type="text" name="EMP_DEPARTMENT" size="30" readonly value="<%= department.getDepartment() %> ">
                                            <% } %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Commencing Date </td>
                                          <td width="27%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= (employee.getCommencingDate()!=null? Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy"):"-") %> 
                                            </b>
                                            <% } else {%>
                                            <input type="text" name="EMP_COMMENCING_DATE" readonly value="<%=(employee.getCommencingDate()==null) ? "" : Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy") %>"> 
                                            <% } %>
                                          </td>
                                          <td width="15%">&nbsp;</td>
                                          <td width="39%"> 
                                            <%if(oidEmployeeVisit == 0){%>
                                            <table cellpadding="0" cellspacing="0" border="0" width="217">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="15"><a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap><a href="javascript:cmdSearchEmp()">Search 
                                                  Employee</a></td>
                                                <td class="command" nowrap><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap><a href="javascript:cmdClear()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                <td nowrap><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap> 
                                                  <div align="left"><a href="javascript:cmdClear()">Clear Form</a></div>
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="5"> 
                                            <hr>
                                          </td>
                                        </tr>
                                        <%if(oidEmployeeVisit == 0){%>
                                        <tr> 
                                          <td colspan="5"><b>Change Visitor</b></td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Visitor Name </td>
                                          <td width="27%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= empVisitor.getFullName() %></b>
                                            <% } else {%>
                                            <input type="text" name="V_EMP_FULLNAME" size="30" readonly value="<%= employee.getFullName() %>">
                                            <% } %>
                                          </td>
                                          <td width="15%">Visitor Number </td>
                                          <td width="39%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= empVisitor.getEmployeeNum() %></b>
                                            <% } else {%>
                                            <input type="text" name="V_EMP_NUMBER" readonly value="<%= (empVisitor.getEmployeeNum().length()>0) ? (empVisitor.getEmployeeNum().trim()) : "" %>">
                                            <% } %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Visitor Position </td>
                                          <td width="27%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= posVisitor.getPosition() %></b>
                                            <% } else {%>
                                            <input type="text" name="V_EMP_POSITION" size="30" readonly value="<%= posVisitor.getPosition() %> ">
                                            <% } %>
                                          </td>
                                          <td width="15%">Visitor Department 
                                          </td>
                                          <td width="39%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= depVisitor.getDepartment() %></b>
                                            <% } else {%>
                                            <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                               <input type="text" name="V_EMP_DEPARTMENT" size="30" readonly value="<%= depVisitor.getDepartment() %> ">
                                            <% } %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Commencing Date </td>
                                          <td width="27%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            : <b><%= (empVisitor.getCommencingDate()!=null? Formater.formatDate(empVisitor.getCommencingDate(), "dd MMMM yyyy"):"-") %> 
                                            </b>
                                            <% } else {%>
                                            <input type="text" name="V_EMP_COMMENCING_DATE" readonly value="<%=(empVisitor.getCommencingDate()==null) ? "" : Formater.formatDate(empVisitor.getCommencingDate(), "dd MMMM yyyy") %>"> 
                                            <% } %>
                                          </td>
                                          <td width="15%">&nbsp;</td>
                                          <td width="39%"> 
                                            <%if(oidEmployeeVisit == 0){%>
                                            <table cellpadding="0" cellspacing="0" border="0" width="217">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="15"><a href="javascript:cmdSearchEmpVisitor()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap><a href="javascript:cmdSearchEmpVisitor()">Search 
                                                  Employee</a></td>
                                                <td class="command" nowrap><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap><a href="javascript:cmdClearVisitor()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                <td nowrap><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap> 
                                                  <div align="left"><a href="javascript:cmdClearVisitor()">Clear Form</a></div>
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="5"  valign="top"  > 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >Visit 
                                            Date</td>
                                          <td  width="27%"  valign="top"> <%=ControlDate.drawDateWithStyle(FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_VISIT_DATE], (employeeVisit.getVisitDate()==null) ? new Date() : employeeVisit.getVisitDate(), 1, -10, "formElemen", "")%> 
                                            * <%=frmEmployeeVisit.getErrorMsg(FrmEmployeeVisit.FRM_FIELD_VISIT_DATE)%></td>
                                          <td  width="15%"  valign="top">&nbsp;</td>
                                          <td  width="39%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >Diagnose</td>
                                          <td  width="27%"  valign="top"> 
                                            <input type="text" name="<%=FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_DIAGNOSE]%>" value="<%=employeeVisit.getDiagnose()%>" class="formElemen">
                                            * <%=frmEmployeeVisit.getErrorMsg(FrmEmployeeVisit.FRM_FIELD_DIAGNOSE)%></td>
                                          <td  width="15%"  valign="top">&nbsp;</td>
                                          <td  width="39%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >Description</td>
                                          <td  width="27%"  valign="top"> 
                                            <textarea name="<%=FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_DESCRIPTION]%>" cols="30" class="formElemen" rows="3"><%=employeeVisit.getDescription()%></textarea>
                                          </td>
                                          <td  width="15%"  valign="top">&nbsp;</td>
                                          <td  width="39%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >&nbsp;</td>
                                          <td  width="27%"  valign="top">&nbsp;</td>
                                          <td  width="15%"  valign="top">&nbsp;</td>
                                          <td  width="39%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="5">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="5"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80%");
							String scomDel = "javascript:cmdAsk('"+oidEmployeeVisit+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidEmployeeVisit+"')";
							String scancel = "javascript:cmdEdit('"+oidEmployeeVisit+"')";
							ctrLine.setBackCaption("Back to List Employee Visit");
							ctrLine.setConfirmDelCaption("Yes Delete Employee Visit");
							ctrLine.setDeleteCaption("Delete Employee Visit");
							ctrLine.setSaveCaption("Save Employee Visit");
							ctrLine.setAddCaption("Add Employee Visit");
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
							
							if(iCommand==Command.LIST){
								iCommand=Command.EDIT;							
							}
							%>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="5">&nbsp;</td>
                                        </tr>
                                      </table>
									  <% if(iCommand==Command.ADD){%>
									  <script language="JavaScript">
										cmdClear();
									  </script>
									  	
									  <%} %>
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
<!-- #BeginEditable "script" -->&nbsp;{script}<!-- #EndEditable -->
<!-- #EndTemplate --></html>
