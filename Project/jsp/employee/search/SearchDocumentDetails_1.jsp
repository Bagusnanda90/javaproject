<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMainRequestOnly"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMain"%>
<%@page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.form.search.FrmSrcEmployee" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.AlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstAlUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessAlUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%//@include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%!
public String drawList(Vector objectClass, int st){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No.","4%");
	ctrlist.addHeader("Payroll","10%");
	ctrlist.addHeader("Name","27%");
	ctrlist.addHeader("Commencing Date","11%");
	ctrlist.addHeader("Division","12%");
	ctrlist.addHeader("Department","12%");
	ctrlist.addHeader("Position","12%");
	//ctrlist.addHeader("Section","12%");
		
	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	for (int i = 0; i < objectClass.size(); i++) {
		Vector temp = (Vector)objectClass.get(i);
		Employee employee = (Employee)temp.get(0);
		Department department = (Department)temp.get(1);
		Position position = (Position)temp.get(2);
		Section section = (Section)temp.get(3);
		//EmpCategory empCategory = (EmpCategory)temp.get(4);
		//Level level = (Level)temp.get(5);
		//Religion religion = (Religion)temp.get(6);
		//Marital marital = (Marital)temp.get(7);
		Division division = (Division)temp.get(9);

		Vector rowx = new Vector();
		rowx.add(String.valueOf(st + 1 + i));
		rowx.add(employee.getEmployeeNum());
		rowx.add(employee.getFullName());
                if(employee.getCommencingDate()!=null){
                    rowx.add(Formater.formatDate(employee.getCommencingDate(),"yyyy-MM-dd"));
                }else{
                    rowx.add("");
                }
		rowx.add(division.getDivision());
		rowx.add(department.getDepartment());
		rowx.add(position.getPosition());
		//rowx.add(section.getSection());
		
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(employee.getOID()) + "','" + employee.getEmployeeNum() + "','" + employee.getFullName() + "','" + department.getDepartment()+ "','" + employee.getAddress());
	}
	return ctrlist.draw();
}
%>

<%
    boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;


    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]);
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    int iErrCode = FRMMessage.ERR_NONE;
    String formName = FRMQueryString.requestString(request,"formName");
    String empPathId = FRMQueryString.requestString(request,"empPathId");
    
    int value = FRMQueryString.requestInt(request,"value");
    
    
    
    
    if (value>0){
    session.putValue("value", value);
    }
    if(session.getValue("value")!=null){
    value = (Integer) session.getValue("value");
    }
    String employee_id = "FRM_FIELD_EMPLOYEE_ID"+value;
    String emp_fullname = "EMP_FULLNAME"+value;
    String emp_address = "EMP_ADDRESS"+value;
    String emp_num = "EMP_NUM"+value;
    
    ControlLine ctrLine = new ControlLine();
    SrcEmployee srcEmployee = new SrcEmployee();
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
    frmSrcEmployee.requestEntityObject(srcEmployee);
    //update by satrya 2014-06-19; 
String employeeIdLeaveConfigDinamis = "";//PstLeaveConfigurationMain.listJoinDetail(emplx.getOID()); 
if(!isHRDLogin){
    //update by satrya 2014-07-02
    employeeIdLeaveConfigDinamis = PstLeaveConfigurationMainRequestOnly.listJoinDetail(emplx.getOID());
    if(employeeIdLeaveConfigDinamis!=null && employeeIdLeaveConfigDinamis.length()>0){
        //maka tidak ada apa"
    }else{
        employeeIdLeaveConfigDinamis = PstLeaveConfigurationMain.listJoinDetail(emplx.getOID());
    }
    //employeeIdLeaveConfigDinamis = PstLeaveConfigurationMain.listJoinDetail(emplx.getOID());
    srcEmployee.setEmployeeIdLeaveConfig(employeeIdLeaveConfigDinamis);   
}
    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 10;
    int vectSize = 0;
    
    String msgStr = "";
    String orderClause = "";
//    String whereClause = "RESIGNED = 0";

    SessEmployee sessEmployee = new SessEmployee();
    vectSize = sessEmployee.countEmployee(srcEmployee);
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        start = ctrlEmployee.actionList(iCommand, start, vectSize, recordToGet);
    }else{
        start = 0;
    }
   Vector listEmployee = new Vector(1,1);
   try{
        listEmployee = sessEmployee.searchEmployee(srcEmployee, start, recordToGet);
   }catch(Exception ex){
   
   }
%>

<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Search Employee</title>
<script language="JavaScript">
    
function cmdEdit(oid, number, fullname, department,address) {
        alert("1");
        var FRM_FIELD_EMPLOYEE_ID = "FRM_FIELD_EMPLOYEE_ID<%=value%>";
        var EMP_FULLNAME = "EMP_FULLNAME<%=value%>";
        
       // self.opener.document.<%=formName%>.<%=employee_id%>.value = oid;
        self.opener.document.<%=formName%>.EMP_NUM.value = number;
        alert("2");
        self.opener.document.<%=formName%>.<%=emp_fullname%>.value = fullname;
        self.opener.document.<%=formName%>.<%=emp_address%>.value = address;
       // self.opener.document.<%=formName%>.EMP_DEPARTMENT.value = department;
       // alert("1");
        self.close();
    }
function cmdAdd() {
        window.open("editEmployee.jsp", "edit_employee", "height=580,width=900, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes,resizable=no,top=50,left=50");
    }
 
//update by satrya 2013-09-17
function cmdUpdateDiv(){
    document.searchEmp.command.value="<%=String.valueOf(Command.ADD)%>";
   //document.searchEmp.hidden_goto_div.value = document.searchEmp.<//%//=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_COMPANY_ID]%>.value;
    document.searchEmp.action="SearchDocumentDetails.jsp";
    document.searchEmp.submit();
}
function cmdUpdateDep(){
    document.searchEmp.command.value="<%=String.valueOf(Command.ADD)%>";
    ///document.searchEmp.hidden_goto_dept.value = document.searchEmp.<//%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DIVISION_ID]%>.value;
    document.searchEmp.action="SearchDocumentDetails.jsp";
    document.searchEmp.submit();
}
function cmdUpdatePos(){
    document.searchEmp.command.value="<%=String.valueOf(Command.ADD)%>";
    //document.searchEmp.hidden_goto_sec.value = document.searchEmp.<//%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]%>.value;
    document.searchEmp.action="SearchDocumentDetails.jsp";
    document.searchEmp.submit();
}
//end
function deptChange() {
    document.searchEmp.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.searchEmp.hidden_goto_dept.value = document.searchEmp.<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]%>.value;
    document.searchEmp.action = "SearchDocumentDetails.jsp";
    document.searchEmp.submit();
}

function cmdSearch() {
    document.searchEmp.command.value = "<%=String.valueOf(Command.LIST)%>";									
    document.searchEmp.action = "SearchDocumentDetails.jsp";
    document.searchEmp.submit();
}

//-------------- script control line -------------------
        function cmdListFirst(){
		document.searchEmp.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.searchEmp.action="SearchDocumentDetails.jsp";
		document.searchEmp.submit();
	}

	function cmdListPrev(){
		document.searchEmp.command.value="<%=String.valueOf(Command.PREV)%>";
		document.searchEmp.action="SearchDocumentDetails.jsp";
		document.searchEmp.submit();
	}

	function cmdListNext(){
		document.searchEmp.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.searchEmp.action="SearchDocumentDetails.jsp";
		document.searchEmp.submit();
	}

	function cmdListLast(){
		document.searchEmp.command.value="<%=String.valueOf(Command.LAST)%>";
		document.searchEmp.action="SearchDocumentDetails.jsp";
		document.searchEmp.submit();
	}


        function MM_swapImgRestore() 
        { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
        }

        function MM_preloadImages() 
        { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
        }

        function MM_findObj(n, d) 
        { //v4.0
                var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                if(!x && document.getElementById) x=document.getElementById(n); return x;
        }

        function MM_swapImage() 
        { //v3.0
                var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
        }
</script>


<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
    
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
    <tr><td id="ds_calclass">
    </td></tr>
</table> 
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >

  
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                Search Employee
                <!-- #EndEditable --> 
                  </strong></font> </td>
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
                                <% if (privStart) { %>
                                    <form name="searchEmp" method="post" action="">
                                    <%if(iCommand == Command.SAVE || iCommand == Command.ACTIVATE){ %>
                                        <input type="hidden" name="command" value="<%=String.valueOf(Command.LIST)%>">
                                    <%}else{%>
                                        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <%}%>
                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <input type="hidden" name="formName" value="<%=String.valueOf(formName)%>">
                                    <input type="hidden" name="empPathId" value="<%=String.valueOf(empPathId)%>">
                                    
                                    <input type="hidden" name="empPathId" value="<%=String.valueOf(empPathId)%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="19%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                  <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]%>"  value="<%=String.valueOf(srcEmployee.getName())%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                  <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER]%>"  value="<%=String.valueOf(srcEmployee.getEmpnumber())%>" class="elemenForm">
                                                </td>
                                              </tr>
                                             
                                              <tr> 
                                                <td width="19%">Category</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                  <% 
							Vector cat_value = new Vector(1,1);
							Vector cat_key = new Vector(1,1);        
							cat_value.add("0");
							cat_key.add("all category ...");                                                          
							Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");                                                        
							for (int i = 0; i < listCat.size(); i++) {
								EmpCategory cat = (EmpCategory) listCat.get(i);
								cat_key.add(cat.getEmpCategory());
								cat_value.add(String.valueOf(cat.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMP_CATEGORY],"formElemen",null,String.valueOf(srcEmployee.getEmpCategory()), cat_value, cat_key, "") %> </td>
                                              </tr>
                                              
                                              <tr> 
                                                <td width="19%">Company</td>
                                                <td width="1%">:</td>
                                                <td width="80%">
  <%
					Vector comp_value = new Vector(1, 1);
					Vector comp_key = new Vector(1, 1);  
String whereComp="";
 String whereClauseLeaveDinamis="";  
/*if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
    whereComp = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+srcOvertime.getCompanyId();
}*/   
    Vector div_value = new Vector(1, 1);
    Vector div_key = new Vector(1, 1);      
    
    Vector dept_value = new Vector(1, 1);
    Vector dept_key = new Vector(1, 1);                                      
 if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                    div_value.add("0");
                   div_key.add("select ...");
                   
                    dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                    // comp_value.add("0");
                    // comp_key.add("select ...");
                     
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+  whereDiv +")": whereDiv;
                    
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
                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+ whereClsDep +")":whereClsDep;
                     
                }
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
     div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }               
    Vector listCostDept = new Vector();//PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    //update by satrya 2014-06-07
    whereClauseLeaveDinamis = "cm."+PstLeaveConfigurationMainRequestOnly.fieldNames[PstLeaveConfigurationMainRequestOnly.FLD_EMPLOYEE_ID]+"="+emplx.getOID();
    listCostDept = PstLeaveConfigurationMainRequestOnly.listCompLeaveConfig(whereClauseLeaveDinamis, ""); 
    if(listCostDept!=null && listCostDept.size()>0){
        //update by satrya 2014-07-02
    }else{
        whereClauseLeaveDinamis = "cm."+PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_EMPLOYEE_ID]+"="+emplx.getOID();
        listCostDept = PstLeaveConfigurationMain.listCompLeaveConfig(whereClauseLeaveDinamis, ""); 
    }
    //whereClauseLeaveDinamis = "cm."+PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_EMPLOYEE_ID]+"="+emplx.getOID();
    //listCostDept = PstLeaveConfigurationMain.listCompLeaveConfig(whereClauseLeaveDinamis, ""); 
    if(listCostDept!=null && listCostDept.size()>0){ 
        /// maka yg dipakai adalah list config dinamis
    }else{
        listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    }
    String prevCompany="";
    String prevDivision="";
    


long prevCompanyTmp=0;        
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                //}
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
            String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
             //untuk karyawan admin yg hanya bisa akses departement tertentu (ketika di awal)
             ////update
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                if(srcEmployee!=null && srcEmployee.getCompanyId()!=0){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
                }
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                    if(srcEmployee!=null && srcEmployee.getCompanyId()!=0){
                       div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                       dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision();             
                   }
                    //update by satrya 2013-09-19
                    else if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){
                        div_key.add(dept.getDivision());
                        div_value.add(""+dept.getDivisionId()); 
                        
                        //update by satrya 2013-09-19
                        dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                   }
                   
                }else{
                    
                     div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                       dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                }
            }
        }
 }else{
      if(srcEmployee!=null && srcEmployee.getCompanyId()!=0){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
       }
 }
             ///batas lama
                /*if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
                }*/
             //batas lama
             
            }
           else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }
           
        }
    }  
			%>
                        <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + srcEmployee.getCompanyId(), comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%">DIVISION</td>
                                                <td width="1%">:</td>
                                                <td width="80%">
<%
					
                                   //update by satrya 2013-08-13
                                   //jika user memilih select kembali
                                   if(srcEmployee!=null && srcEmployee.getCompanyId()==0){ 
                                       srcEmployee.setDivisionId(0); 
                                   }

if(srcEmployee!=null && srcEmployee.getCompanyId()!=0){
    whereComp = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp) + ") AND " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+srcEmployee.getCompanyId();
    //listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    whereClauseLeaveDinamis = "("+(whereClauseLeaveDinamis!=null && whereClauseLeaveDinamis.length()==0?"1=1":whereClauseLeaveDinamis) + ") AND comp." + PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID] +"="+srcEmployee.getCompanyId(); 
    //update by satrya 2014-07-02
    listCostDept = PstLeaveConfigurationMainRequestOnly.listCompLeaveConfig(whereClauseLeaveDinamis, ""); 
    if(listCostDept!=null && listCostDept.size()>0){ 
        //tidak melakukan apa"
    }else{
        listCostDept = PstLeaveConfigurationMain.listCompLeaveConfig(whereClauseLeaveDinamis, ""); 
    }
    //listCostDept = PstLeaveConfigurationMain.listCompLeaveConfig(whereClauseLeaveDinamis, ""); 
    if(listCostDept!=null && listCostDept.size()>0){ 
        /// maka yg dipakai adalah list config dinamis
    }else{
        listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    }
    prevCompany="";
    prevDivision="";
    
    div_value = new Vector(1, 1);
    div_key = new Vector(1, 1);      
    
    dept_value = new Vector(1, 1);
    dept_key = new Vector(1, 1); 

    prevCompanyTmp=0;  
long tmpFirstDiv=0;   

if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                   div_value.add("0");
                   div_key.add("select ...");
                   
                   dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                } 
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
    div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }
   long prevDivTmp=0;
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //update
                if(srcEmployee!=null && srcEmployee.getDivisionId()!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                }
                //lama
                /*
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
                */
                
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
           String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             //comp_key.add(dept.getCompany());
             //comp_value.add(""+dept.getCompanyId());
             
            
             
             if(prevDivTmp!=dept.getDivisionId()){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 
                    prevDivTmp=dept.getDivisionId();
              }
             
                    tmpFirstDiv=dept.getDivisionId(); 

                   // dept_key.add(dept.getDepartment());
                 //   dept_value.add(String.valueOf(dept.getOID()));           
               
            prevCompany = dept.getCompany();
            prevDivision = dept.getDivision();             
            }
           /*else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }*/
          String chkAdaDpt="";
          String chkAdaDptDinamis="";
          /* update by satrya 2014-06-18if(whereComp!=null && whereComp.length()>0){
                chkAdaDpt = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp)+ ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+srcEmployee.getDivisionId();
          }
            Vector listCheckAdaDept = PstDepartment.listWithCompanyDiv(0, 0, chkAdaDpt);*/
          if(whereComp!=null && whereComp.length()>0){
                chkAdaDpt = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp)+ ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+srcEmployee.getDivisionId();
          }
          if(whereClauseLeaveDinamis!=null && whereClauseLeaveDinamis.length()>0){
                chkAdaDptDinamis = "("+(whereClauseLeaveDinamis!=null && whereClauseLeaveDinamis.length()==0?"1=1":whereClauseLeaveDinamis)+ ") AND divs." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+srcEmployee.getDivisionId();
          }
            Vector listCheckAdaDept = new Vector();//PstDepartment.listWithCompanyDiv(0, 0, chkAdaDpt); 
            //update by satrya 2014-07-02
              listCheckAdaDept = PstLeaveConfigurationMainRequestOnly.listCompLeaveConfig(chkAdaDptDinamis, ""); 
            if(listCheckAdaDept!=null && listCheckAdaDept.size()>0){
              //tidak melakukan apa"
            }else{
                listCheckAdaDept = PstLeaveConfigurationMain.listCompLeaveConfig(chkAdaDptDinamis, ""); 
            }
            //listCheckAdaDept = PstLeaveConfigurationMain.listCompLeaveConfig(chkAdaDptDinamis, ""); 
            if(listCheckAdaDept!=null && listCheckAdaDept.size()>0){ 
                /// maka yg dipakai adalah list config dinamis
            }else{
                listCheckAdaDept = PstDepartment.listWithCompanyDiv(0, 0, chkAdaDpt);
            }
            if((listCheckAdaDept==null || listCheckAdaDept.size()==0)){
                
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
               /* if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                      
                }else{
                     srcOvertime.setDivisionId(tmpFirstDiv);
                }*/ 
                srcEmployee.setDivisionId(tmpFirstDiv);
            }
        }
 }else{
     srcEmployee.setDivisionId(tmpFirstDiv);
 }
               
            }
        }
    }
}
			%>  <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DIVISION_ID], "formElemen", null,""+srcEmployee.getDivisionId() , div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%">DEPARTMENT</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                <%

            //update by satrya 2013-08-13
            //jika user memilih select kembali
            if(srcEmployee!=null && srcEmployee.getDepartment()==0){   
                srcEmployee.setSection(0); 
            }
if(srcEmployee!=null && srcEmployee.getDivisionId()!=0){
    if(whereComp!=null && whereComp.length()>0){
        whereComp = "("+whereComp + ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+srcEmployee.getDivisionId();
    }
    
    //listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    whereClauseLeaveDinamis = "("+whereClauseLeaveDinamis + ") AND dpt." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+srcEmployee.getDivisionId();
   //update by satrya 2014-07-02
     listCostDept = PstLeaveConfigurationMainRequestOnly.listCompLeaveConfig(whereClauseLeaveDinamis, ""); 
    if(listCostDept!=null && listCostDept.size()>0){ 
    //maka tidak ada apa"
    }else{
         listCostDept = PstLeaveConfigurationMain.listCompLeaveConfig(whereClauseLeaveDinamis, ""); 
    }
    // listCostDept = PstLeaveConfigurationMain.listCompLeaveConfig(whereClauseLeaveDinamis, ""); 
    if(listCostDept!=null && listCostDept.size()>0){ 
        /// maka yg dipakai adalah list config dinamis
    }else{
        listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    }
    prevCompany="";
    prevDivision="";
    
    div_value = new Vector(1, 1);
    div_key = new Vector(1, 1);      
    
    dept_value = new Vector(1, 1);
    dept_key = new Vector(1, 1); 

    prevCompanyTmp=0; 

if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                   div_value.add("0");
                   div_key.add("select ...");
                   
                   dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                } 
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
    div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }
                
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
            String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             comp_key.add(dept.getCompany());
             comp_value.add(""+dept.getCompanyId());
             
             
             div_key.add(dept.getDivision());
             div_value.add(""+dept.getDivisionId()); 
              
             dept_key.add(dept.getDepartment());
             dept_value.add(String.valueOf(dept.getOID()));
            prevCompany = dept.getCompany();
            prevDivision = dept.getDivision();             
            }else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }
           
        }
    }
}
			%>  <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT], "formElemen", null, "" + srcEmployee.getDepartment(), dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%>  </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%">I_Dictionary.SECTION</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                <% 
            //Vector sec_value = new Vector(1, 1);
            //Vector sec_key = new Vector(1, 1);
 
            //sec_value.add("0");
            //sec_key.add("select ...");
            
            /*String whereSection = "";
            Vector listSec = new Vector();
                                                   
            if(isHRDLogin || isEdpLogin || isGeneralManager){

                if(srcEmployee.getDepartment() != 0){ 
                    whereSection = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+" = "+srcEmployee.getDepartment();
                    listSec = PstSection.list(0, 0, whereSection, " SECTION "); 
                }
                
                
                
            }else{
                
                String joinDeptSection = PstSystemProperty.getValueByName("JOIN_DEPARTMENT_SECTION");
                Vector depSecGroup = com.dimata.util.StringParser.parseGroup(joinDeptSection);
                int maxGrpSec = depSecGroup == null ? 0 : depSecGroup.size();                                
                
                Vector idxSecGroup = new Vector();
                
                for(int j = 0 ; j < maxGrpSec ; j++){
                    
                    String[] grp = (String[]) depSecGroup.get(j);
                     
                    if(grp[0].trim().compareToIgnoreCase(""+srcEmployee.getDepartment())==0){
                        
                        Counter counter = new Counter();
                        counter.setCounter(j);
                        idxSecGroup.add(counter);
                        
                    }                                    
                    
                }
                
                Vector SectionList = new Vector();
                
                if(idxSecGroup != null && idxSecGroup.size() > 0){
                    
                    for(int i = 0 ; i < idxSecGroup.size(); i++){
                        
                        Counter counter = (Counter)idxSecGroup.get(i);
                                
                        String[] grp = (String[]) depSecGroup.get(counter.getCounter());
                                
                        Section sec = new Section();
                        sec.setDepartmentId(Long.parseLong(grp[1]));
                        sec.setOID(Long.parseLong(grp[2]));
                        SectionList.add(sec);
                        
                    }
                    
                }
                
                if(SectionList != null && SectionList.size() > 0){
                    
                    for(int i = 0 ; i < SectionList.size() ; i++){
                        
                        Section section = new Section();
                        section = (Section)SectionList.get(i);
                        
                        if(section.getDepartmentId() == srcEmployee.getDepartment()){ 
                            
                            Section objSection = new Section();
                            
                            try{
                                objSection = PstSection.fetchExc(section.getOID());
                               
                                listSec.add(objSection);
                            }catch(Exception e){
                                System.out.println("Exception "+e.toString());
                            }
                        }
                        
                    }
                    
                }
                
                if(listSec == null || listSec.size() <= 0){
                    
                    //if(oidDepartment != 0){
                        whereSection = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+" = "+srcEmployee.getDepartment();
                    //}
                
                    listSec = PstSection.list(0, 0, whereSection, " SECTION ");                    
                    
                }
                
            }
					//Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
					//String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + srcOvertime.getDepartmentId();
					//Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
                                      if(listSec!=null && listSec.size()>0 || srcEmployee.getDepartment()!=0){
                                          sec_value = new Vector(1, 1); 
                                          sec_key = new Vector(1, 1);
 
                                          sec_value.add("0");
                                          sec_key.add("select ...");
					for (int i = 0; i < listSec.size(); i++) {
						Section sec = (Section) listSec.get(i);
						sec_key.add(sec.getSection());
						sec_value.add(String.valueOf(sec.getOID()));
					}
                                      }*/
                                      //update by satrya 2014-06-18
Vector sec_value = new Vector(1, 1);
            Vector sec_key = new Vector(1, 1);
 
            sec_value.add("0");
            sec_key.add("select ...");
            
            String whereSection = "";
            Vector listSec = new Vector();
                                                   
            if(isHRDLogin || isEdpLogin || isGeneralManager || isDirector){

                if(srcEmployee.getDepartment() != 0){
                    whereSection = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+" = "+srcEmployee.getDepartment();
                }
                
                listSec = PstSection.list(0, 0, whereSection, " SECTION ");
                
            }else{
                
                String joinDeptSection = PstSystemProperty.getValueByName("JOIN_DEPARTMENT_SECTION");
                Vector depSecGroup = com.dimata.util.StringParser.parseGroup(joinDeptSection);
                int maxGrpSec = depSecGroup == null ? 0 : depSecGroup.size();                                
                
                Vector idxSecGroup = new Vector();
                
                for(int j = 0 ; j < maxGrpSec ; j++){
                    
                    String[] grp = (String[]) depSecGroup.get(j);
                     
                    if(grp[0].trim().compareToIgnoreCase(""+departmentOid)==0){
                        
                        Counter counter = new Counter();
                        counter.setCounter(j);
                        idxSecGroup.add(counter);
                        
                    }                                    
                    
                }
                
                Vector SectionList = new Vector();
                
                if(idxSecGroup != null && idxSecGroup.size() > 0){
                    
                    for(int i = 0 ; i < idxSecGroup.size(); i++){
                        
                        Counter counter = (Counter)idxSecGroup.get(i);
                                
                        String[] grp = (String[]) depSecGroup.get(counter.getCounter());
                                
                        Section sec = new Section();
                        sec.setDepartmentId(Long.parseLong(grp[1]));
                        sec.setOID(Long.parseLong(grp[2]));
                        SectionList.add(sec);
                        
                    }
                    
                }
                
                if(SectionList != null && SectionList.size() > 0){
                    
                    for(int i = 0 ; i < SectionList.size() ; i++){
                        
                        Section section = new Section();
                        section = (Section)SectionList.get(i);
                        
                        if(section.getDepartmentId() == srcEmployee.getDepartment()){
                            
                            Section objSection = new Section();
                            
                            try{
                                objSection = PstSection.fetchExc(section.getOID());
                               
                                listSec.add(objSection);
                            }catch(Exception e){
                                System.out.println("Exception "+e.toString());
                            }
                        }
                        
                    }
                    
                }
                
                if(listSec == null || listSec.size() <= 0){
                    
                    //if(oidDepartment != 0){
                        whereSection = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+" = "+srcEmployee.getDepartment();
                    //}
                
                    listSec = PstSection.list(0, 0, whereSection, " SECTION ");                    
                    
                }
                
            }
							
							/*sec_value.add("0");
							sec_key.add("all section ...");
                                                        String strWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
							Vector listSec = PstSection.list(0, 0, strWhere, " DEPARTMENT_ID, SECTION ");*/
							for (int i = 0; i < listSec.size(); i++) {
								Section sec = (Section) listSec.get(i);
								sec_key.add(sec.getSection());
								sec_value.add(String.valueOf(sec.getOID()));
							}
			%>
                        <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION], "formElemen", null, "" + srcEmployee.getSection(), sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                              </tr>
                                              <tr> 
                                                <td width="19%">Position</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                              <% 
							Vector pos_value = new Vector(1,1);
							Vector pos_key = new Vector(1,1); 
							pos_value.add("0");
							pos_key.add("all position ...");                                                       
							Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                        for (int i = 0; i < listPos.size(); i++) {
								Position pos = (Position) listPos.get(i);
								pos_key.add(pos.getPosition());
								pos_value.add(String.valueOf(pos.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_POSITION],"formElemen",null,String.valueOf(srcEmployee.getPosition()), pos_value, pos_key, "") %> </td>
                                              </tr>
					      
                                              <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RESIGNED]%>" value="0">
                                              <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SEX]%>" value="3">
                                              
                                              <tr> 
                                                <td width="19%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="80%"> 
                                                  <input type="submit" name="Submit" value="Search Employee" onClick="javascript:cmdSearch()">
                                                <!--  <input type="submit" name="Submit" value="Add Employee" onClick="javascript:cmdAdd()">
                                                -->
                                                </td>
                                              </tr>
                                              
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                            
                                          <% if (listEmployee.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <%if((listEmployee!=null)&&(listEmployee.size()>0)){%>
                                                <tr> 
                                                  <td height="8" width="100%"><%=drawList(listEmployee, start)%></td>
                                                </tr>
                                                <%}else{%>
                                                <tr> 
                                                  <td height="8" width="100%" class="comment"><span class="comment"><br>
                                                    &nbsp;No Employee available</span> 
                                                  </td>
                                                </tr>
                                                <%}%>
                                              <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %><%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
                                                </td>
                                              </tr>
                                            </table>
                                          <% } %>
                                          </td>
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
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

