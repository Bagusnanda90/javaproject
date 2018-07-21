<%-- 
    Document   : input_attendance
    Created on : Jan 20, 2009, 1:35:31 PM
    Author     : bayu
--%>

<%@page contentType="text/html"%>

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
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% 
    int appObjCodeGen = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_GENERAL_TRAINING); 
    int appObjCodeDept = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_DEPARTMENTAL_TRAINING); 
    int appObjCode = 0; 
    
    // check training privilege (0 = none, 1 = general, 2 = departmental)
    int trainType = checkTrainingType(appObjCodeGen, appObjCodeDept, userSession);
    
    if(trainType == PRIV_GENERAL) {    
        appObjCode = appObjCodeGen;
    }
    else if(trainType == PRIV_DEPT) {  
        appObjCode = appObjCodeDept;
    }

    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%@ include file = "../../main/checktraining.jsp" %>

<%!
    public String drawList(Vector employee, TrainingActivityPlan trainPlan, long oidTrainingPlan, long oidDepartment, int hours){
        
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        
        ctrlist.addHeader("No","5%");
        ctrlist.addHeader("Payroll Num","10%");
        ctrlist.addHeader("Full Name","45%");
        ctrlist.addHeader("Department","35%");
        ctrlist.addHeader("Total Hours","5%");

        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        
        int number = 1;
        
        // get training plan
        trainPlan = new TrainingActivityPlan();       
    
        try {          
            trainPlan = PstTrainingActivityPlan.fetchExc(oidTrainingPlan);                       
        }
        catch(Exception e) {
            trainPlan = new TrainingActivityPlan();
        }  

        Department dept = new Department();
        
        try {
            dept = PstDepartment.fetchExc(oidDepartment);
        }
        catch(Exception e) {
            dept = new Department();
        }
             
        // get attendance data from attendance plan
        Vector listAttendance = PstTrainingAttendancePlan.listEmployeeByPlan(oidTrainingPlan, oidDepartment);
        
       
        // map attendance oids and hours on hash
        Hashtable hashAttendance = new Hashtable();
        
        if(listAttendance != null && listAttendance.size() > 0) {
            Enumeration enumAttendance = listAttendance.elements();
            
            while(enumAttendance.hasMoreElements()) {
                TrainingAttendancePlan list = (TrainingAttendancePlan) enumAttendance.nextElement();
                // put record on hash
                hashAttendance.put(String.valueOf(list.getEmployeeId()), String.valueOf(list.getDuration()));
            }
        }
               

        for(int i=0; i<employee.size(); i++) {
            Employee emp = (Employee) employee.get(i);              
            
            Vector rowx = new Vector();
            rowx.add("<input type='hidden' name='txtHiddenID" + i + "' value='" + emp.getOID() + "'>" + String.valueOf(number++));
            rowx.add(emp.getEmployeeNum());
            
            // if employee is in attendance hash, display checked
            if(hashAttendance.get(String.valueOf(emp.getOID())) != null) {    
                rowx.add("<input type='checkbox' name='txtID" + i + "' checked value='" + emp.getOID() + "' onclick='javascript:checkEnabled(" + i + ")'>" + emp.getFullName());
            }
            // if not, display unchecked
            else {
                rowx.add("<input type='checkbox' name='txtID" + i + "' value='" + emp.getOID() + "'  onclick='javascript:checkEnabled(" + i + ")'>" + emp.getFullName());
            }
            
            //rowx.add(emp.getFullName() + "<input type='checkbox' name='txtID" + i + "' value='" + emp.getOID() + "'>");
            rowx.add(dept.getDepartment());
                     
            // if employee is in attendance hash, display his/her hours
            if(hashAttendance.get(String.valueOf(emp.getOID())) != null) {      
                String hour = SessTraining.getDurationString(Integer.parseInt(String.valueOf(hashAttendance.get(String.valueOf(emp.getOID())))));
                rowx.add("<input type='text' name='txtHour" + i + "' value='" + hour + "' onchange='javascript:checkValue(\"" + hour + "\",\"" + i + "\")'>");
            }
            // if not, display hours based on planning
            else {
                String hour = SessTraining.getDurationString(hours);
                rowx.add("<input type='text' name='txtHour" + i + "' value='" + 0 + "' onchange='javascript:checkValue(\"" + hour + "\",\"" + i +  "\")'>");
            }

            lstData.add(rowx);          
        }
        
        return ctrlist.draw();
    }

    public String getSectionLink(String sectionId){
        String str = "";
        try{
            Section section = PstSection.fetchExc(Long.valueOf(sectionId));
            str = section.getSection();
            return str;
        } catch(Exception e){
            System.out.println(e);
        }
        return str;
    }
    
   
%>

<% 
        int iCommand = FRMQueryString.requestCommand(request);
        long oidPlan = FRMQueryString.requestLong(request, "plan");
        int hours = FRMQueryString.requestInt(request, "hours");     

    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "section_id");
            
        CtrlTrainingAttendacePlan ctrlAttendance = new CtrlTrainingAttendacePlan(request);
        TrainingActivityPlan trainPlan = new TrainingActivityPlan();
        Vector listEmployee = new Vector(1, 1);
        
        SrcEmployee srcEmployee = new SrcEmployee();
        FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee();
        
        if(iCommand == Command.GOTO) {
            frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
            frmSrcEmployee.requestEntityObject(srcEmployee);
        }
        
    String name = FRMQueryString.requestString(request, FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]);
    String empNum = FRMQueryString.requestString(request, FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER]);
                      
        // get employee data
        if(iCommand == Command.LIST) { 
            /* 
            * Description : Code has been off
            * Update by : Hendra Putu | 2015-11-24
            * Requested by : BPD
            *   
            frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
            frmSrcEmployee.requestEntityObject(srcEmployee);
                       
            String whereClause = "";     

            whereClause = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";                               

            if(srcEmployee.getDepartment() != 0 ){
                whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcEmployee.getDepartment();
            }

            if(srcEmployee.getSection() != 0 ){
                whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcEmployee.getSection();
            }
            
            if(srcEmployee.getEmpnumber().trim().length() > 0){
                whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + srcEmployee.getEmpnumber() + "%'";
            }
            
            if(srcEmployee.getName().trim().length() > 0){
                whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + srcEmployee.getName() + "%'";
            }
            */
            String whereClause = " 1=1 ";
             if(empNum.length() > 0){
                whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + empNum + "%'";
            }
            
            if(name.length() > 0){
                whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + name + "%'";
            }
            if (companyId != 0){
                whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId;
            }
            if (divisionId != 0){
                whereClause += " AND ";
                whereClause += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
            }
            if (departmentId != 0){
                whereClause += " AND ";
                whereClause += PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
            }
            if (sectionId != 0){
                whereClause += " AND ";
                whereClause += PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId;
            }
            listEmployee = PstEmployee.list(0, 0, whereClause, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
        }
        
 /*
    * Description : get value Company, Division, Department, and Section
    * Date : 2015-02-17
    * Author : Hendra Putu
    */
// List Company
    Vector comp_value = new Vector(1, 1);
    Vector comp_key = new Vector(1, 1);
    comp_value.add("0");
    comp_key.add("-select-");
    String comp_selected = "";
// List Division
    Vector div_value = new Vector(1, 1);
    Vector div_key = new Vector(1, 1);
    String whereDivision = "COMPANY_ID = "+companyId;
    div_value.add("0");
    div_key.add("-select-");
    String div_selected = "";
// List Department
    Vector depart_value = new Vector(1,1);
    Vector depart_key = new Vector(1,1);
    String whereComp = ""+companyId;
    String whereDiv = "" +divisionId;
    depart_value.add("0");
    depart_key.add("-select-");
    String depart_selected = "";

// List Section
    Vector section_value = new Vector(1,1);
    Vector section_key = new Vector(1,1);
    String whereSection = "DEPARTMENT_ID = "+departmentId;
    section_value.add("0");
    section_key.add("-select-");


    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
    for (int i = 0; i < listComp.size(); i++) {
    Company comp = (Company) listComp.get(i);
        if (comp.getOID() == companyId){
          comp_selected = String.valueOf(companyId);
        }
        comp_key.add(comp.getCompany());
        comp_value.add(String.valueOf(comp.getOID()));
    }
    
    Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
    if (listDiv != null && listDiv.size() > 0) {
        for (int i = 0; i < listDiv.size(); i++) {
            Division division = (Division) listDiv.get(i);
            if (division.getOID() == divisionId){
                div_selected = String.valueOf(divisionId);
            }
            div_key.add(division.getDivision());
            div_value.add(String.valueOf(division.getOID()));
        }
    }

    Vector listDepart = PstDepartment.listDepartmentVer1(0, 0, whereComp, whereDiv);
    if (listDepart != null && listDepart.size() > 0) {
        for (int i = 0; i < listDepart.size(); i++) {
            Department depart = (Department) listDepart.get(i);
            if (depart.getOID()==departmentId){
                depart_selected = String.valueOf(departmentId);
            }
            depart_key.add(depart.getDepartment());
            depart_value.add(String.valueOf(depart.getOID()));
        }
    }

    Vector listSection = PstSection.list(0, 0, whereSection, "");
    if (listSection != null && listSection.size() > 0) {
        for (int i = 0; i < listSection.size(); i++) {
            Section section = (Section) listSection.get(i);
            section_key.add(section.getSection());
            section_value.add(String.valueOf(section.getOID()));
            String sectionLink = section.getSectionLinkTo();
            if ((sectionLink != null) && sectionLink.length()>0){

                for (String retval : sectionLink.split(",")) {
                    section_value.add(retval);
                    section_key.add(getSectionLink(retval));
                }
            }
        }
    }
        
%>
<html>
<head><title>Search Employee</title>
<script language="javascript">
    
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


        function cmdSave() {
             document.frm_inputattendance.command.value="<%=Command.SAVE%>";
             document.frm_inputattendance.action="input_attendance.jsp";
             document.frm_inputattendance.submit();
        }
        /*
        function cmdChangeDept(){
             document.frm_inputattendance.command.value="<%=Command.GOTO%>"; 
             document.frm_inputattendance.submit();
        }*/   
       
        function cmdList(){
          
             document.frm_inputattendance.command.value="<%=Command.LIST%>"; 
            
             document.frm_inputattendance.submit();
        }        
            </script>
            <script>
        function setSelected() {
            <% for(int i=0 ; i<listEmployee.size(); i++) { %>	            
                document.frm_inputattendance.txtHour<%=i%>.value = "<%=SessTraining.getDurationString(hours)%>";   
                document.frm_inputattendance.txtID<%=i%>.checked = "true";  
            <% } %>
        }
    
        function setUnselected() {           
            <% for(int i=0 ; i<listEmployee.size(); i++) { %>	            
                document.frm_inputattendance.txtHour<%=i%>.value = "0";  
                document.frm_inputattendance.txtID<%=i%>.checked = "";   
            <% } %>           
        }
        
     
        function checkEnabled(index) {
            <% for(int i=0 ; i<listEmployee.size(); i++) { %> 
                if(<%=String.valueOf(i)%> == index) {
                    if(document.frm_inputattendance.txtID<%=String.valueOf(i)%>.checked == "") {
                        document.frm_inputattendance.txtHour<%=String.valueOf(i)%>.value = "0";             
                    }
                    else {
                        document.frm_inputattendance.txtHour<%=String.valueOf(i)%>.value = "<%=SessTraining.getDurationString(hours)%>";
                    }
                    
                }
            <% } %>           
        }
        
        function checkValue(hour, index){
            <% for(int j=0 ; j<listEmployee.size(); j++) { %> 
                if(<%=String.valueOf(j)%> == index) {
                    if(document.frm_inputattendance.txtHour<%=String.valueOf(j)%>.value == "" || document.frm_inputattendance.txtHour<%=String.valueOf(j)%>.value == "0") {
                        document.frm_inputattendance.txtHour<%=String.valueOf(j)%>.value = "0";             
                        document.frm_inputattendance.txtID<%=String.valueOf(j)%>.checked = "";         
                    }
                    else {
                        //document.frm_inputattendance.txtHour<%=String.valueOf(j)%>.value = hour;
                        document.frm_inputattendance.txtID<%=String.valueOf(j)%>.checked = "true";    
                    }
                    
                }
            <%}%>
        }
        
          
        <% if((iCommand == Command.SAVE)) {
                      
            // get employee count
            int totalAttendances = FRMQueryString.requestInt(request, "total_attendance");
                             
            // get each value
            for(int i=0; i<totalAttendances; i++) {                
                String id = FRMQueryString.requestString(request, "txtHiddenID" + i );
                String hour = FRMQueryString.requestString(request, "txtHour" + i);              
                
                long loid = 0;
                int ihour = 0;
                
                try {
                    loid = Long.parseLong(id);
                    ihour = SessTraining.getTrainingDuration(hour);                    
                }
                catch(Exception e) {
                    loid = 0;
                    ihour = 0;
                }
                            
                //if(ihour > 0) { 
                if(ihour != 0) {
                    // save employee data, or update if it's already exists
                    ctrlAttendance.updateAttendance(Command.SAVE, loid, ihour, oidPlan);
                }
                else {
                    // delete employee data if it's already exists
                    ctrlAttendance.updateAttendance(Command.DELETE, loid, ihour, oidPlan);
                }
            }         
        
            //
            
            %>
                
            self.opener.document.frm_trainingplan.command.value="<%=Command.BACK%>";
            self.opener.document.frm_trainingplan.submit();
            self.close();
            
        <% } %>		        
       
</script>
<script type="text/javascript">
            function compChange(val) 
            {
                document.frm_inputattendance.command.value = "<%=Command.GOTO%>";
                document.frm_inputattendance.company_id.value = val;
                document.frm_inputattendance.action = "input_attendance.jsp";
                document.frm_inputattendance.submit();
            }
            function divisiChange(val) 
            {
                document.frm_inputattendance.command.value = "<%=Command.GOTO%>";
                document.frm_inputattendance.division_id.value = val;
                document.frm_inputattendance.action = "input_attendance.jsp";
                document.frm_inputattendance.submit();
            }
            function deptChange(val) 
            {
                document.frm_inputattendance.command.value = "<%=Command.GOTO%>";	
                document.frm_inputattendance.department_id.value = val;
                document.frm_inputattendance.action = "input_attendance.jsp";
                document.frm_inputattendance.submit();
            }
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <form name="frm_inputattendance" method="post" action="">
        <input type="hidden" name="command" value="<%=iCommand%>">       
        <input type="hidden" name="plan" value="<%=oidPlan%>">
        <input type="hidden" name="hours" value="<%=hours%>">
        <input type="hidden" name="total_attendance" value="<%= (listEmployee != null) ? listEmployee.size() : 0 %>">

        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FCFDEC" >
        <tr> 
            <td width="100%" valign="top" align="left"> 
            <table width="100%" border="0" cellspacing="1" cellpadding="1">
            <tr> 
                <td width="100%"> 
                <table>                
                <tr> 
                    <td valign="center" colspan="2"> 
                        <font color="#FF6600" face="Arial"><strong>Select Employee</strong></font>
                    </td>                    
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr align="left" valign="top"> 
                    <td width="17%" nowrap><div align="left">&nbsp;Payroll Number</div></td>
                    <td width="83%"> 
                        <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER] %>"  value="<%= srcEmployee.getEmpnumber() %>" class="elemenForm"> 
                    </td>
                </tr>
                <tr align="left" valign="top"> 
                    <td width="17%" nowrap><div align="left">&nbsp;Name</div></td>
                    <td width="83%"> 
                        <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME] %>"  value="<%= srcEmployee.getName() %>" class="elemenForm" size="40"> 
                    </td>
                </tr>
                <tr>
                    <td valign="top">Company</td>
                    <td valign="top">
                        <%= ControlCombo.draw("company_id", "formElemen", null, comp_selected, comp_value, comp_key, " onChange=\"javascript:compChange(this.value)\"") %>
                    </td>
                </tr>
                <tr>
                    <td><%= dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                    <td>
                        <%= ControlCombo.draw("division_id", "formElemen", null, div_selected, div_value, div_key, " onChange=\"javascript:divisiChange(this.value)\"") %>
                    </td>
                </tr>
                <tr align="left" valign="top"> 
                    <td width="17%" nowrap><div align="left">&nbsp;<%= dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                    <td width="83%"> 
                        <%           
                        /*
            * Description : Code has been off
            * Update by : Hendra Putu | 2015-11-24
            * Requested by : BPD 
                            String where = "";
                            Vector listDept = new Vector(1,1);
                            
                            try {
                                trainPlan = PstTrainingActivityPlan.fetchExc(oidPlan);
                                listDept = PstTrainingDept.getDepartmentByTraining(trainPlan.getTrainingId());
                            }
                            catch(Exception e) {}
                            
                            if(listDept != null && listDept.size() > 0) {
                                //update by devin 2014-04-10
                                
                                //where = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " IN (";
                                where = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " IN (";
                                                           
                                for(int i=0; i<listDept.size(); i++) {
                                    where += (String)listDept.get(i);

                                    if(i < listDept.size() - 1)
                                        where += ",";
                                    else
                                        where += ")";
                                }
                            }
                            //update by devin 2014-04-10
                            Vector listDepartment = PstDepartment.list(0, 0, where, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                            Vector deptValue = new Vector(1,1); 
                            Vector deptKey = new Vector(1,1); 

                            for(int i=0; i<listDepartment.size(); i++){
                                Department department = (Department)listDepartment.get(i);
                                deptValue.add(String.valueOf(department.getOID()));
                                deptKey.add(department.getDepartment());
                            }	
 * 
 * ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT], null, "" + srcEmployee.getDepartment(), deptValue, deptKey, "onChange=\"javascript:cmdChangeDept()\"", "formElemen")                            								
                            */
                        %>                        
                        <%= ControlCombo.draw("department_id", "formElemen", null, depart_selected, depart_value, depart_key, " onChange=\"javascript:deptChange(this.value)\"") %>
                    </td>				
                </tr>
                <tr align="left" valign="top"> 
                    <td width="17%" nowrap><div align="left">&nbsp;<%= dictionaryD.getWord("SECTION") %></div></td>
                    <td width="83%"> 
                        <% /*
            * Description : Code has been off
            * Update by : Hendra Putu | 2015-11-24
            * Requested by : BPD
                            Vector sec_value = new Vector(1,1);
                            Vector sec_key = new Vector(1,1);
                            //update by devin 2014-04-10
                            
                            sec_value.add("0");
                            sec_key.add("-- ALL --");
                            //update by devin 2014-04-10
                           if(iCommand==Command.NONE){
                               long idDepartment=Long.parseLong(String.valueOf(deptValue.get(0)));   
                              srcEmployee.setDepartment(idDepartment);  
                           }
                            String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+srcEmployee.getDepartment();
                            Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
                            
                            for (int i = 0; i < listSec.size(); i++) {
                                    Section sec = (Section) listSec.get(i);
                                    sec_key.add(sec.getSection());
                                    sec_value.add(String.valueOf(sec.getOID()));
                            }
                        ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION],null, "" + srcEmployee.getSection(), sec_value, sec_key,"formElemen")
                        */
                        %> 
                        <%= ControlCombo.draw("section_id", "formElemen", null, "0", section_value, section_key, "") %>
                    </td>										
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td width="3%"><a href="javascript:cmdList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10c','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10c" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                            <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                            <td width="96%" class="command" nowrap><a href="javascript:cmdList()">Search Employee</a></td>                                                        
                         </tr>       
                        </table>                       
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
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
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr align="left" valign="top"> 
                                    <td height="8"  colspan="3"> 
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <p>&nbsp;Set hours to 'zero' (0) or uncheck employee(s) name to remove from training attendees
                                               <br>&nbsp;Separate hours and minute with 'dot' sign (.) e.g. 1 hour and 30 minutes = 1.30
                                               <br>&nbsp;Press 'Save' button below when done
                                            </p>
                                        </td>
                                    </tr>    
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr align="left" valign="top"> 
                                        <td height="22" valign="middle"> 
                                            <%= drawList(listEmployee, trainPlan, oidPlan, srcEmployee.getDepartment(), hours) %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    
                                    <% if(listEmployee.size() > 0) { %>
                                    <tr> 
                                        <td nowrap>
                                            <a href="javascript:setSelected()">&nbsp;Include All</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                                            <a href="javascript:setUnselected()">Remove All</a>                                            
                                        </td>                        
                                    </tr>     
                                    <tr>
                                        <td nowrap align="right">   
                                            <table>                                                 
                                            <tr>
                                                <td><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10m','','<%=approot%>/images/BtnSaveOn.jpg',1)" id="aSearch"><img name="Image10m" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Attendance"></a></td>
                                                <td><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td class="command" nowrap><a href="javascript:cmdSave()">Save Attendees</a></td>                                                        
                                            </tr>
                                            </table>   
                                        </td>
                                    </tr>
                                    <% } %>
                                                                         
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
                </td>
            </tr>
            </table>
            </td>
        </tr>
        <tr> 
            <td colspan="2" height="20" bgcolor="#15A9F5">
                <%@ include file = "../../main/footer.jsp" %>
            </td>
        </tr>
        </table>
        </form>
</body>
</html>