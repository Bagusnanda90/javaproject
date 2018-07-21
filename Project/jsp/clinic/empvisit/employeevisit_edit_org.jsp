
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
	long oidDepartVisit = FRMQueryString.requestLong(request, "depart_visitor");
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
	if(oidDepartVisit == 0){
		Vector tempDepart = PstDepartment.list(0,0,PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+ " LIKE '%HUMAN%'","");
		if(tempDepart.size()<1)
			tempDepart = PstDepartment.list(0,0,"","");
			
		Department dept = (Department)tempDepart.get(0);
		oidDepartVisit = dept.getOID();
		listVisitor = PstEmployee.list(0,0,PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+oidDepartVisit,""); 
	}else
		listVisitor = PstEmployee.list(0,0,PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+oidDepartVisit,""); 
	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlEmployeeVisit.action(iCommand , oidEmployeeVisit);

	errMsg = ctrlEmployeeVisit.getMessage();
	FrmEmployeeVisit frmEmployeeVisit = ctrlEmployeeVisit.getForm();
	EmployeeVisit employeeVisit = ctrlEmployeeVisit.getEmployeeVisit();
            oidEmployeeVisit = employeeVisit.getOID();
        
	long oidEmployee = employeeVisit.getEmployeeId();
	Employee employee = new Employee();
	long oidPosition = 0;
	Position position = new Position();
	long oidDepartment = 0;
	Department department = new Department();
	Department depVisitor = new Department();
	//out.println("oidEmployeeVisit "+oidEmployeeVisit);
	if (oidEmployeeVisit != 0 ) {
		try{
			employee = PstEmployee.fetchExc(oidEmployee);
                        //out.println("employee "+employee.getFullName());
			oidPosition = employee.getPositionId();
			position = PstPosition.fetchExc(oidPosition);
			oidDepartment = employee.getDepartmentId();
			department = PstDepartment.fetchExc(oidDepartment);
			depVisitor = PstDepartment.fetchExc(oidDepartVisit);
		}catch(Exception exc){
			employee = new Employee();
			position = new Position();
			department = new Department();
		}		
	}else{
		if(employeeOID!=0){
			try{
				employee = PstEmployee.fetchExc(employeeOID);
							//out.println("employee1 "+employee.getFullName());
				oidPosition = employee.getPositionId();
				position = PstPosition.fetchExc(oidPosition);
				oidDepartment = employee.getDepartmentId();
				department = PstDepartment.fetchExc(oidDepartment);
				depVisitor = PstDepartment.fetchExc(oidDepartVisit);
			}catch(Exception exc){
				employee = new Employee();
				position = new Position();
				department = new Department();
			}	
		}
	}

        //out.println("employee "+employee.getFullName());

if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmEmployeeVisit.errorSize()<1)){
	%>
	<jsp:forward page="srcemployeevisit.jsp"> 	
	<jsp:param name="command" value="<%=iCommand%>" />
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
		document.frm_employeevisit.command.value="<%=Command.ADD%>";
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	} 
	function cmdCancel(){
		document.frm_employeevisit.command.value="<%=Command.CANCEL%>";
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_employeevisit.command.value="<%=Command.EDIT%>";
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit(); 
	} 

	function cmdSave(){
		document.frm_employeevisit.command.value="<%=Command.SAVE%>"; 
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	}
	
	function cmdAdd(){
		document.frm_employeevisit.command.value="<%=Command.ADD%>"; 
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	} 

	function cmdAsk(oid){
		document.frm_employeevisit.command.value="<%=Command.ASK%>"; 
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_employeevisit.command.value="<%=Command.DELETE%>";
		document.frm_employeevisit.action="employeevisit_edit.jsp"; 
		document.frm_employeevisit.submit();
	}  

	function cmdBack(){
		document.frm_employeevisit.command.value="<%=Command.BACK%>"; 
		document.frm_employeevisit.action="srcemployeevisit.jsp";
		document.frm_employeevisit.submit();
	}
	
	function chgDepart(){
		document.frm_employeevisit.command.value="<%=Command.LIST%>"; 
		document.frm_employeevisit.action="employeevisit_edit.jsp";
		document.frm_employeevisit.submit();
	}
	
	function cmdSearchEmp(){
            window.open("empdopsearch.jsp?emp_number=" + document.frm_employeevisit.EMP_NUMBER.value + "&emp_fullname=" + document.frm_employeevisit.EMP_FULLNAME.value + "&emp_department=" + document.frm_employeevisit.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
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
		document.frm_employeevisit.EMP_FULLNAME.value=""; 
		document.frm_employeevisit.EMP_NUMBER.value=""; 
		document.frm_employeevisit.EMP_POSITION.value=""; 
		document.frm_employeevisit.EMP_COMMENCING_DATE.value=""; 
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
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="employee_visit_oid" value="<%=""+oidEmployeeVisit%>">
                                      <input type="hidden" name="<%=FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=""+oidEmployeeVisit%>">
                                      <table width="100%" cellspacing="1" cellpadding="1" >
                                        <tr> 
                                          <td colspan="5">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td class="txtheading1" width="17%">&nbsp;</td>
                                          <td class="comment" width="27%">*) entry 
                                            required</td>
                                          <td class="comment" width="15%">&nbsp;</td>
                                          <td class="comment" width="39%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">&nbsp;</td>
                                          <td width="27%">&nbsp;</td>
                                          <td width="15%">&nbsp;</td>
                                          <td width="39%">&nbsp;</td>
                                        </tr>										
                                        
										<%if(oidEmployeeVisit == 0){%>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td colspan="4"><b>Change Employee</b></td>
                                        </tr>
										<%}%>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Employee Name </td>
                                          <td width="27%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            <%= employee.getFullName() %> 
                                            <% } else {%>
                                            <input type="text" name="EMP_FULLNAME" size="30" value="<%= employee.getFullName() %>">
                                            <% } %>
                                          </td>
                                          <td width="15%">Employee Number </td>
                                          <td width="39%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            <%= employee.getEmployeeNum() %> 
                                            <% } else {%>
                                            <input type="text" name="EMP_NUMBER" value="<%= (employee.getEmployeeNum().length()>0) ? (employee.getEmployeeNum().trim()) : "" %>">
                                            <% } %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Employee Position </td>
                                          <td width="27%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            <%= position.getPosition() %> 
                                            <% } else {%>
                                            <input type="text" name="EMP_POSITION" size="30" value="<%= position.getPosition() %> ">
                                            <% } %>
                                          </td>
                                          <td width="15%">Employee Department 
                                          </td>
                                          <td width="39%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            <%= department.getDepartment() %> 
                                            <% } else {%>
                                            <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                            <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
                                                            //dept_value.add("0");
                                                            //dept_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %>
                                            <%= ControlCombo.draw("EMP_DEPARTMENT","formElemen",null, ""+department.getOID(), dept_value, dept_key) %> 
                                            <% } %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Commencing Date </td>
                                          <td width="27%"> 
                                            <%if(oidEmployeeVisit != 0){%>
                                            <%= (employee.getCommencingDate()!=null? Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy"):"-") %> 
                                            <% } else {%>
                                            <input type="text" name="EMP_COMMENCING_DATE" value="<%=(employee.getCommencingDate()==null) ? "" : Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy") %>"> 
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
                                          <td width="2%">&nbsp;</td>
                                          <td colspan="4"> 
                                            <hr>
                                          </td>
                                        </tr>
                                            <%if(oidEmployeeVisit == 0){%>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td colspan="4"><b>Change Visitor</b></td>
                                        </tr>
										<%}%>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Department Visitor</td>
                                          <td colspan="3"> 
                                          <% 
                                          String visitorName = "";
                                          long selVisitor = 0;
                                          if(oidEmployeeVisit != 0){%>
                                                        <%=depVisitor.getDepartment()%>
                                          <%}else{%>
                                            <%  Vector department_value = new Vector(1,1);
                                                Vector department_key = new Vector(1,1);
                                                Vector listDepartment = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                for(int i = 0;i <listDepartment.size();i++){
                                                        department = (Department)listDepartment.get(i);
                                                        department_value.add(""+department.getOID());
                                                        department_key.add(department.getDepartment());
                                                }
                                          %>
                                            <%= ControlCombo.draw("depart_visitor","elementForm", null, ""+oidDepartVisit, department_value, department_key, "onChange=\"javascript:chgDepart()\"") %> 
                                            &nbsp;<a href="javascript:chgDepart()" class="command">change</a>&nbsp;&nbsp; 
                                            <%
                                              Vector visitor_key = new Vector(1,1);
                                              Vector visitor_value = new Vector(1,1); 
                                              if(listVisitor != null && listVisitor.size()>0){												
                                                    for(int i = 0;i < listVisitor.size();i++){
                                                            Employee visitor = (Employee)listVisitor.get(i);
                                                            if(i==0)
                                                                    visitorName = visitor.getFullName();
                                                            if(employeeVisit.getVisitedBy()==0 && i==0)
                                                                    selVisitor = visitor.getOID();
                                                            visitor_key.add(visitor.getEmployeeNum()+"  >  "+visitor.getFullName());
                                                            visitor_value.add(""+visitor.getOID());
                                                    }
                                            }
                                            %>
                                            <%= ControlCombo.draw("chg_visitor", null, ""+selVisitor, visitor_value, visitor_key, "class=\"elementForm\" onChange=\"javascript:calVisitor();\"") %>                                           	
										  <%}%>
										  <input type="hidden" name="<%=frmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_VISITED_BY]%>" value="<%=oidEmployeeVisit == 0 ? selVisitor:employeeVisit.getVisitedBy()%>">
										  </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="17%">Visitor</td>
                                          <td width="27%"> 
                                            <%
										    try{
												if(employeeVisit.getVisitedBy()!=0){
													Employee visitor = PstEmployee.fetchExc(employeeVisit.getVisitedBy());
													visitorName = visitor.getFullName();
												}
											}catch(Exception exc){
												visitorName = "";
											}	
										  	%>
                                            <%if(oidEmployeeVisit != 0){%>
                                            <%=visitorName%> 
                                            <%}else{%>
                                            <input type="text" name="visitor" size="30" value="<%=visitorName%>">
											<%}%>
                                          </td>
                                          <td width="15%">&nbsp;</td>
                                          <td width="39%">&nbsp; </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td colspan="4"  valign="top"  > 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="2%"  valign="top"  >&nbsp;</td>
                                          <td width="17%"  valign="top"  >Visit 
                                            Date</td>
                                          <td  width="27%"  valign="top"> <%=ControlDate.drawDateWithStyle(FrmEmployeeVisit.fieldNames[FrmEmployeeVisit.FRM_FIELD_VISIT_DATE], (employeeVisit.getVisitDate()==null) ? new Date() : employeeVisit.getVisitDate(), 1, -5, "formElemen", "")%> 
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
