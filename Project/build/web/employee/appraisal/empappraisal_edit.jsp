 
<% 
/* 
 * Page Name  		:  empappraisal_edit.jsp
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
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_PERFORMANCE_APPRAISAL); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;


//cek tipe browser, browser detection
    //String userAgent = request.getHeader("User-Agent");
    //boolean isMSIE = (userAgent!=null && userAgent.indexOf("MSIE") !=-1); 

%>
<%

	CtrlEmpAppraisal ctrlEmpAppraisal = new CtrlEmpAppraisal(request);
	long oidEmpAppraisal = FRMQueryString.requestLong(request, "employee_appraisal_oid");
	long oidDepartAppraisor = FRMQueryString.requestLong(request, "depart_appraisor");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
        
	
//	out.print("oidEmpAppraisal : "+oidEmpAppraisal);
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	Vector listAppraisor = new Vector(1,1);
	if(oidDepartAppraisor == 0){
		Vector tempDepart = PstDepartment.list(0,0,PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+ " LIKE '%HUMAN%'","");
		if(tempDepart.size()<1)
			tempDepart = PstDepartment.list(0,0,"","");
			
		Department dept = (Department)tempDepart.get(0);
		oidDepartAppraisor = dept.getOID();
		listAppraisor = PstEmployee.list(0,0,PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+oidDepartAppraisor,""); 
	}else
		listAppraisor = PstEmployee.list(0,0,PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+oidDepartAppraisor,""); 
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlEmpAppraisal.action(iCommand , oidEmpAppraisal, request);

	errMsg = ctrlEmpAppraisal.getMessage();
	FrmEmpAppraisal frmEmpAppraisal = ctrlEmpAppraisal.getForm();
	EmpAppraisal empAppraisal = ctrlEmpAppraisal.getEmpAppraisal();
	oidEmpAppraisal = empAppraisal.getOID();

	if(iCommand==Command.DELETE){
            %>
            <jsp:forward page="empappraisal_list.jsp"> 
            <jsp:param name="prev_command" value="<%=prevCommand%>" />
            <jsp:param name="start" value="<%=start%>" />
            </jsp:forward>
            <%
        }

    long oidEmployee = empAppraisal.getEmployeeId();

    Employee employee = new Employee();
    long oidPosition = 0;
    Position position = new Position();
    long oidDepartment = 0;
    Department department = new Department();
    Department depAppraisor = new Department();

    if (empAppraisal.getOID() != 0) {
            try{
                    employee = PstEmployee.fetchExc(oidEmployee);
                    oidPosition = employee.getPositionId();
                    position = PstPosition.fetchExc(oidPosition);
                    oidDepartment = employee.getDepartmentId();
                    department = PstDepartment.fetchExc(oidDepartment);
                    depAppraisor = PstDepartment.fetchExc(oidDepartAppraisor);
            }catch(Exception exc){
                    employee = new Employee();
                    position = new Position();
                    department = new Department();
                    depAppraisor = new Department();
            }

    }
    //out.println(" | employee : " + employee.getFullName());
    //out.println(" | oidEmployee "+oidEmployee);
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Employee Appraisal</title>
<script language="JavaScript">

	function cmdEdit(oid){ 
		document.frm_empappraisal.command.value="<%=Command.EDIT%>";
		document.frm_empappraisal.action="empappraisal_edit.jsp";
		document.frm_empappraisal.submit(); 
	} 

	function cmdSave(){
		document.frm_empappraisal.command.value="<%=Command.SAVE%>"; 
		document.frm_empappraisal.action="empappraisal_edit.jsp";
		document.frm_empappraisal.submit();
	}

	function cmdAsk(oid){
		document.frm_empappraisal.command.value="<%=Command.ASK%>"; 
		document.frm_empappraisal.action="empappraisal_edit.jsp";
		document.frm_empappraisal.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_empappraisal.command.value="<%=Command.DELETE%>";
		document.frm_empappraisal.action="empappraisal_edit.jsp"; 
		document.frm_empappraisal.submit();
	}  

	function cmdBack(){
		document.frm_empappraisal.command.value="<%=Command.LIST%>"; 
		document.frm_empappraisal.action="empappraisal_list.jsp";
		document.frm_empappraisal.submit();
	}
	
	function chgDepart(){
		document.frm_empappraisal.command.value="<%=iCommand%>"; 
		document.frm_empappraisal.action="empappraisal_edit.jsp";
		document.frm_empappraisal.submit();
	}	
	
	function cmdSearchEmp(){
            window.open("empdopsearch.jsp?emp_number=" + document.frm_empappraisal.EMP_NUMBER.value + "&emp_fullname=" + document.frm_empappraisal.EMP_FULLNAME.value + "&emp_department=" + document.frm_empappraisal.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	
	function calAppraisor() 
	{
	appraisor = document.frm_empappraisal.chg_appraisor.value;
	document.frm_empappraisal.<%=frmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_APPRAISOR_ID]%>.value=appraisor;
	<%
		for(int i = 0; i < listAppraisor.size(); i++) {
			Employee emp = (Employee)listAppraisor.get(i);		
			long oid = emp.getOID();
			%>
				if(appraisor=="<%=oid%>")
				  document.frm_empappraisal.appraisor.value="<%=emp.getFullName()%>";
			<%
		}								
	%>
	
	}


	function cmdPrint(){
		document.frm_empappraisal.command.value="<%=Command.EDIT%>";                  
                document.frm_empappraisal.target="PrintAppraisal";                
		document.frm_empappraisal.action="empappraisal_print.jsp";
		document.frm_empappraisal.submit();
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
	<%if(empAppraisal.getOID()==0){%>
		document.frm_empappraisal.EMP_DEPARTMENT.style.visibility="hidden";
		document.frm_empappraisal.chg_appraisor.style.visibility="hidden";
	<%}%>
}

function showObjectForMenu(){
	<%if(empAppraisal.getOID()==0){%>
		document.frm_empappraisal.EMP_DEPARTMENT.style.visibility="";
		document.frm_empappraisal.chg_appraisor.style.visibility="";
	<%}%>
}


</SCRIPT>
<!-- #EndEditable -->
</head>  

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr>  
  <tr> 
    <td bgcolor="#9BC1FF"  ID="MAINMENU" valign="middle" height="15"> <!-- #BeginEditable "menumain" --> 
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
				  <td>
					<font color="#FF6600" face="Arial"><strong>
					  <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Employee Appraisal <!-- #EndEditable --> 
					</strong></font>
				  </td>
				</tr>
				<tr> 
				 <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                              <form name="frm_empappraisal" method="post" action="">
                                <input type="hidden" name="command" value="">
                                <input type="hidden" name="start" value="<%=start%>">
                                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                <input type="hidden" name="employee_appraisal_oid" value="<%=oidEmpAppraisal%>">
                                <table width="100%" border="0" cellspacing="1" cellpadding="1" height="504">
                                  <tr> 
                                    <td valign="top" height="551"> 
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <% if(oidEmpAppraisal != 0){%>
                                        <tr> 
                                          <td> 
                                            <table  border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td  valign="top" height="20" width="104"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="txtalign">
                                                    <tr> 
                                                      <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/active_left.jpg" width="12" height="29"></td>
                                                      <td   valign="middle" background="<%=approot%>/images/tab/active_bg.jpg" nowrap> 
                                                        <div align="center" class="tablink">Appraisal</div>
                                                      </td>
                                                      <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td  valign="top" height="20" width="158"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td   valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                      <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap > 
                                                        <div align="center" class="tablink"><a href="perfevaluation.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&employee_oid=<%=oidEmployee%>" class="tablink">Performance 
                                                          Evaluation </a></div>
                                                      </td>
                                                      <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td  valign="top" height="20"  width="165"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                      <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="137"> 
                                                        <div align="center" class="tablink"><a href="devimprovement.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&employee_oid=<%=oidEmployee%>" class="tablink">Improvement 
                                                          Appraisal</a></div>
                                                      </td>
                                                      <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td  valign="top" height="20" width="125"> 
                                                  <table width="97%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                      <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="110"> 
                                                        <div align="center" class="tablink"><a href="devimprovementplan.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&employee_oid=<%=oidEmployee%>" class="tablink">Improvement 
                                                          Plan</a></div>
                                                      </td>
                                                      <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td width="150"  valign="top" height="20">&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td class="tablecolor"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                              <tr> 
                                                <td valign="top"> 
                                                  <table width="100%" cellspacing="1" cellpadding="1" class="tabbg" height="100%">
                                                    <tr> 
                                                      <td width="3%">&nbsp;</td>
                                                      <td class="comment" width="18%"> 
                                                        <div align="center"></div>
                                                      </td>
                                                      <td class="comment" width="76%">*) 
                                                        entry required</td>
                                                      <td class="comment" width="3%">&nbsp;</td>
                                                    </tr>                                                    
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td colspan="2"  > 
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                          <tr> 
                                                            <td width="18%">&nbsp;</td>
                                                            <td colspan="3"><input type="hidden" name="<%=FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=empAppraisal.getEmployeeId()%>" class="formElemen">
                                                            </td>
                                                          </tr>
                                                          <tr> 
                                                            
                                                            <td width="18%"> 
                                                              <div align="right">Name 
                                                                : </div>
                                                            </td>
                                                            <td width="31%"> 
                                                              <% if (empAppraisal.getOID() != 0) {%>
                                                              <%= employee.getFullName() %> 
                                                              <% } else {%>
                                                              <input type="text" name="EMP_FULLNAME" size="30">
                                                              * <%=frmEmpAppraisal.getErrorMsg(FrmEmpAppraisal.FRM_FIELD_EMPLOYEE_ID)%>
                                                              <% } %>
                                                            </td>
                                                            <td width="13%"> 
                                                              <div align="right">Employee 
                                                                Number : </div>
                                                            </td>
                                                            <td width="38%"> 
                                                              <% if (empAppraisal.getOID() != 0) {%>
                                                              <%= employee.getEmployeeNum() %> 
                                                              <% } else {%>
                                                              <input type="text" name="EMP_NUMBER">
                                                              <% } %>
                                                            </td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="18%"> 
                                                              <div align="right">Position 
                                                                : </div>
                                                            </td>
                                                            <td width="31%"> 
                                                              <% if (empAppraisal.getOID() != 0) {%>
                                                              <%= position.getPosition() %> 
                                                              <% } else {%>
                                                              <input type="text" name="EMP_POSITION" readonly size="30">
                                                              <% } %>
                                                            </td>
                                                            <td width="13%"> 
                                                              <div align="right">Department 
                                                                : </div>
                                                            </td>
                                                            <td width="38%"> 
                                                              <% if (empAppraisal.getOID() != 0) {%>
                                                              <%= department.getDepartment() %> 
                                                              <% } else {%>
                                                              <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                                              <% 
                                                           /* Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
                                                            //dept_value.add("0");
                                                            //dept_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }*/
                                                        %>
                                                        <%

                                                                                                    Vector dept_value = new Vector(1, 1);
                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                    //Vector listDept = new Vector(1, 1);
                                                                                                    DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                                                                                    if (processDependOnUserDept) {
                                                                                                        if (emplx.getOID() > 0) {
                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                            } else {
                                                                                                                position = null;
                                                                                                                try {
                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                } catch (Exception exc) {
                                                                                                                }
                                                                                                                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                                                                                                                } else {

                                                                                                                    String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                                                                                            + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                                                                                                                    try {
                                                                                                                        String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                                                        Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                                        int grpIdx = -1;
                                                                                                                        int maxGrp = depGroup == null ? 0 : depGroup.size();
                                                                                                                        int countIdx = 0;
                                                                                                                        int MAX_LOOP = 10;
                                                                                                                        int curr_loop = 0;
                                                                                                                        do { // find group department belonging to curretn user base in departmentOid
                                                                                                                            curr_loop++;
                                                                                                                            String[] grp = (String[]) depGroup.get(countIdx);
                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                String comp = grp[g];
                                                                                                                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                    grpIdx = countIdx;   // A ha .. found here 
                                                                                                                                }
                                                                                                                            }
                                                                                                                            countIdx++;
                                                                                                                        } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                                                                                        // compose where clause
                                                                                                                        if (grpIdx >= 0) {
                                                                                                                            String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                String comp = grp[g];
                                                                                                                                whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                                                                                            }
                                                                                                                        }
                                                                                                                    } catch (Exception exc) {
                                                                                                                            System.out.println(" Parsing Join Dept" + exc);
                                                                                                                         
                                                                                                                    }
                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                                                                                                                    //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            //dept_value.add("0");
                                                                                                            //dept_key.add("select ...");
                                                                                                            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                            //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                        }
                                                                                                    } else {
                                                                                                        //dept_value.add("0");
                                                                                                        //dept_key.add("select ...");
                                                                                                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                        //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                    }
                                                                                                    dept_value = keyList.getDepIDs();
                                                                                                    dept_key = keyList.getDepNames();

                                                                                                    /*for (int i = 0; i < listDept.size(); i++) {
                                                                                                    Department dept = (Department) listDept.get(i);
                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                    } */


                                                                                                    String selectValueDepartment = "" + oidDepartment;//+objSrcLeaveApp.getDepartmentId();

                                                                                                %>
                                                              <%= ControlCombo.draw("EMP_DEPARTMENT","formElemen",null, selectValueDepartment, dept_value, dept_key) %> 
                                                              <% } %>
                                                            </td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="18%"> 
                                                              <div align="right">Commencing 
                                                                Date : </div>
                                                            </td>
                                                            <td width="31%"> 
                                                              <% if (empAppraisal.getOID() != 0) {%>
                                                              <%= Formater.formatDate(employee.getCommencingDate(), "dd MMMMM yyyy") %> 
                                                              <% } else {%>
                                                              <input type="text" name="EMP_COMMENCING_DATE" readonly>
                                                              <% } %>
                                                            </td>
                                                            <td width="13%"> 
                                                              <div align="right"></div>
                                                            </td>
                                                            <td width="38%"> 
                                                              <% if (empAppraisal.getOID() != 0) {%>
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
                                                      <td width="3%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td colspan="2"  valign="top" align="left"> 
                                                        <hr>
                                                      </td>
                                                      <td width="3%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left" height="27"  >&nbsp;</td>
                                                      <td width="18%" height="27"  > 
                                                        <div align="right">Department 
                                                          Appraisor : </div>
                                                      </td>
                                                      <td  width="76%" height="27"> 
                                                        <% 
                                                          String appraisorName = "";
                                                          long selAppraisor = 0;
                                                          if(oidEmpAppraisal != 0){%>
                                                        <%=depAppraisor.getDepartment()%>														
                                                  <%}else{%>
                                                        <%  /*Vector department_value = new Vector(1,1);
                                                                Vector department_key = new Vector(1,1);
                                                                Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                for(int i = 0;i <listDepartment.size();i++){
                                                                        department = (Department)listDepartment.get(i);
                                                                        department_value.add(""+department.getOID());
                                                                        department_key.add(department.getDepartment());
                                                                }*/
                                                          %>
                                                           <%

                                                                                                    Vector dept_value = new Vector(1, 1);
                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                    //Vector listDept = new Vector(1, 1);
                                                                                                    DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                                                                                    if (processDependOnUserDept) {
                                                                                                        if (emplx.getOID() > 0) {
                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                            } else {
                                                                                                                position = null;
                                                                                                                try {
                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                } catch (Exception exc) {
                                                                                                                }
                                                                                                                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                                                                                                                } else {

                                                                                                                    String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                                                                                            + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                                                                                                                    try {
                                                                                                                        String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                                                        Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                                        int grpIdx = -1;
                                                                                                                        int maxGrp = depGroup == null ? 0 : depGroup.size();
                                                                                                                        int countIdx = 0;
                                                                                                                        int MAX_LOOP = 10;
                                                                                                                        int curr_loop = 0;
                                                                                                                        do { // find group department belonging to curretn user base in departmentOid
                                                                                                                            curr_loop++;
                                                                                                                            String[] grp = (String[]) depGroup.get(countIdx);
                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                String comp = grp[g];
                                                                                                                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                    grpIdx = countIdx;   // A ha .. found here 
                                                                                                                                }
                                                                                                                            }
                                                                                                                            countIdx++;
                                                                                                                        } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                                                                                        // compose where clause
                                                                                                                        if (grpIdx >= 0) {
                                                                                                                            String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                String comp = grp[g];
                                                                                                                                whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                                                                                            }
                                                                                                                        }
                                                                                                                    } catch (Exception exc) {
                                                                                                                            System.out.println(" Parsing Join Dept" + exc);
                                                                                                                         
                                                                                                                    }
                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                                                                                                                    //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            //dept_value.add("0");
                                                                                                            //dept_key.add("select ...");
                                                                                                            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                            //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                        }
                                                                                                    } else {
                                                                                                        //dept_value.add("0");
                                                                                                        //dept_key.add("select ...");
                                                                                                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                        //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                    }
                                                                                                    dept_value = keyList.getDepIDs();
                                                                                                    dept_key = keyList.getDepNames();

                                                                                                    /*for (int i = 0; i < listDept.size(); i++) {
                                                                                                    Department dept = (Department) listDept.get(i);
                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                    } */


                                                                                                    String selectValueDepartment = "" + oidDepartment;//+objSrcLeaveApp.getDepartmentId();

                                                                                                %>
                                                          <%= ControlCombo.draw("depart_appraisor","elementForm", null, ""+oidDepartAppraisor, dept_value, dept_key, "onChange=\"javascript:chgDepart()\"") %> 
                                                          &nbsp;<a href="javascript:chgDepart()" class="command">change</a>&nbsp;&nbsp; 
                                                          <%
                                                              Vector appraisor_key = new Vector(1,1);
                                                              Vector appraisor_value = new Vector(1,1); 
                                                              if(listAppraisor != null && listAppraisor.size()>0){												
                                                                    for(int i = 0;i < listAppraisor.size();i++){
                                                                            Employee appraisor = (Employee)listAppraisor.get(i);
                                                                            if(i==0)
                                                                                    appraisorName = appraisor.getFullName();
                                                                            if(empAppraisal.getAppraisorId()==0 && i==0)
                                                                                    selAppraisor = appraisor.getOID();
                                                                            appraisor_key.add(appraisor.getFullName());
                                                                            appraisor_value.add(""+appraisor.getOID());
                                                                    }
                                                            }
                                                            %>
                                                        <%= ControlCombo.draw("chg_appraisor", null, ""+selAppraisor, appraisor_value, appraisor_key, "class=\"elementForm\" onChange=\"javascript:calAppraisor();\"") %> 
                                                    <%}%>
                                                     <input type="hidden" name="<%=frmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_APPRAISOR_ID]%>" value="<%=oidEmpAppraisal == 0 ? selAppraisor:empAppraisal.getAppraisorId()%>">
                                                      </td>
                                                      <td  width="3%" height="27">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td width="18%"  > 
                                                        <div align="right">Appraisor 
                                                          : </div>
                                                      </td>
                                                      <td  width="76%"> 
                                                        <%if(oidEmpAppraisal != 0){%>
							<%
                                                            Employee emp = new Employee();
                                                            try{
                                                                    emp = PstEmployee.fetchExc(empAppraisal.getAppraisorId());
                                                                    appraisorName = emp.getFullName();
                                                            }catch(Exception exc){
                                                                    emp = new Employee();
                                                            }
                                                            %> 
                                                        <%=appraisorName%> 
                                                        <%}else{%>
                                                        <input type="text" name="appraisor" size="30" value="<%=appraisorName%>" disabled="true">
                                                        * 
                                                        <%}%>
                                                      </td>
                                                      <td  width="3%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left" height="16"  >&nbsp;</td>
                                                      <td colspan="2" height="16"  > 
                                                        <hr>
                                                      </td>
                                                      <td  width="3%" height="16">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td width="18%"  > 
                                                        <div align="right">Last 
                                                          Appraisal : </div>
                                                      </td>
                                                      <td  width="76%"> <%=ControlDate.drawDate(FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_LAST_APPRAISAL], empAppraisal.getLastAppraisal(),"MMMM","formElemen","","",2,-6)%></td> 
                                                      <td  width="3%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td width="18%"  > 
                                                        <div align="right">Date 
                                                          of Appraisal : </div>
                                                      </td>
                                                      <td  width="76%"> <%=ControlDate.drawDate(FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_DATE_OF_APPRAISAL], empAppraisal.getDateOfAppraisal()==null?new Date(): empAppraisal.getDateOfAppraisal(),"formElemen", "MMMM")%> 
                                                        * <%=frmEmpAppraisal.getErrorMsg(FrmEmpAppraisal.FRM_FIELD_DATE_OF_APPRAISAL)%></td>
                                                      <td  width="3%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td width="18%"  > 
                                                        <div align="right">Date 
                                                          Performance : </div>
                                                      </td>
                                                      <td  width="76%"> <%=ControlDate.drawDate(FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_DATE_PERFORMANCE], empAppraisal.getDatePerformance()==null?new Date():empAppraisal.getDatePerformance(),"formElemen", "MMMM")%> 
                                                        * <%=frmEmpAppraisal.getErrorMsg(FrmEmpAppraisal.FRM_FIELD_DATE_PERFORMANCE)%></td>
                                                      <td  width="3%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td width="18%"  > 
                                                        <div align="right">Time 
                                                          Performance : </div>
                                                      </td>
                                                      <td  width="76%"> <%=ControlDate.drawTime(FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_TIME_PERFORMANCE], empAppraisal.getTimePerformance(),"formElemen")%> 
                                                        * <%=frmEmpAppraisal.getErrorMsg(FrmEmpAppraisal.FRM_FIELD_TIME_PERFORMANCE)%></td>
                                                      <td  width="3%">&nbsp;</td>
                                                    </tr>
                                                    <% if(oidEmpAppraisal != 0){%>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td width="18%"  > 
                                                        <div align="right">Total 
                                                          Score : </div>
                                                      </td>
                                                      <td  width="76%"  valign="top" align="left"> 
                                                        <input type="text" disabled="true" name="<%=FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_TOTAL_SCORE]%>" value="<%=empAppraisal.getTotalScore()%>" class="formElemen" size="10">
                                                      </td>
                                                      <td  width="3%"  valign="top" align="left">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td width="18%"  > 
                                                        <div align="right">Total 
                                                          Criteria : </div>
                                                      </td>
                                                      <td  width="76%"  valign="top" align="left"> 
                                                        <input type="text" name="<%=FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_TOTAL_CRITERIA]%>" disabled="true" value="<%=empAppraisal.getTotalCriteria()%>" class="formElemen" size="10">
                                                      </td>
                                                      <td  width="3%"  valign="top" align="left">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td width="18%"  > 
                                                        <div align="right">Score 
                                                          Average : </div>
                                                      </td>
                                                      <td  width="76%"  valign="top" align="left"> 
                                                        <input type="text" name="<%=FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_SCORE_AVERAGE]%>" disabled="true" value="<%=(empAppraisal.getTotalCriteria()==0 ? 0.0:empAppraisal.getScoreAverage())%>" class="formElemen" size="10">
                                                      </td>
                                                      <td  width="3%"  valign="top" align="left">&nbsp;</td>
                                                    </tr>
                                                    <% }%>
                                                    <tr> 
                                                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                                                      <td width="18%"  valign="top" align="left"  ><p align="right">Appraisal Level :</p>
                                                       : </td>
                                                      <td  width="76%"  valign="top" align="left">&nbsp;
													  <input type="hidden" name="<%=frmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_GROUP_RANK_ID]%>" value="<%=empAppraisal.getGroupRankId()%>">
 
													  <%
														if(empAppraisal!=null && empAppraisal.getGroupRankId()>0){
														  try{
														  GroupRank grpRnk = PstGroupRank.fetchExc(empAppraisal.getGroupRankId());
													  %>
													  	
													     <%=(grpRnk.getGroupName()+" / " +grpRnk.getDescription())%>
													  
													  <%
													     } catch (Exception exc){;}
													    } else {%>
													    ( reload on save)
													  <%}%>
													  </td>
                                                      <td  width="3%"  valign="top" align="left">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="4">
                                                      
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="4" align="left"> 
                                                        <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("90");
							String scomDel = "javascript:cmdAsk('"+oidEmpAppraisal+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidEmpAppraisal+"')";
							String scancel = "javascript:cmdEdit('"+oidEmpAppraisal+"')";
							ctrLine.setBackCaption("Back to List Employee Appraisal");
							ctrLine.setCommandStyle("buttonlink");
							ctrLine.setConfirmDelCaption("Yes Delete Employee Appraisal");
							ctrLine.setDeleteCaption("Delete Employee Appraisal");
							ctrLine.setAddCaption("Add New Employee Appraisal");
							ctrLine.setSaveCaption("Save Employee Appraisal");
							
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
                                                      <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> &nbsp;                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="4" height="16">&nbsp;<% if(oidEmpAppraisal!=0){%><a href="javascript:cmdPrint()"> Print Appraisal</a><%}%></td>
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
      </table>
		  </td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> 	  
      <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
