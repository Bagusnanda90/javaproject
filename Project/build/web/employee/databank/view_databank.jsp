<%-- 
    Class Name   : template.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 10:59:14 PM
    Function     : This is template
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmCompany"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmEmployeeCompetency"%>
<%@page import="com.dimata.system.session.dataupload.SessDataUpload"%>
<%@page import="com.dimata.util.blob.ImageLoader"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="usu.util.StringUtil"%>
<%@page import="com.dimata.harisma.entity.recruitment.PstRecrApplication"%>
<%@page import="com.dimata.harisma.entity.recruitment.RecrApplication"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.clinic.MedicalType"%>
<%@page import="com.dimata.harisma.entity.clinic.MedExpenseType"%>
<%@page import="com.dimata.harisma.entity.clinic.PstMedExpenseType"%>
<%@page import="com.dimata.harisma.entity.clinic.PstMedicalType"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>

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
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.location.Location" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployeePicture" %>
<%@page import = "com.dimata.harisma.form.masterdata.FrmKecamatan" %>
<!--%@ page import="javax.servlet.http.HttpUtils.*" %-->

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>
<!DOCTYPE html>
<%
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    long oidEmployee = FRMQueryString.requestLong(request, "oid");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int hidden_command = FRMQueryString.requestInt(request, "hidden_flag_cmd");
    
    long hidden_recr_application_id = FRMQueryString.requestLong(request, "hidden_recr_application_id");
    I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
    //update by satrya 2013-04-09
    int schedulePerWeek = 0;
    int recordToGet = 7;
    try {
        schedulePerWeek = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_DEFAULT_SCHEDULE_PER_WEEK"));
        if (schedulePerWeek != 0) {
            recordToGet = 35;
        }
    } catch (Exception ex) {
        System.out.println("Execption ATTANDACE_DEFAULT_SCHEDULE_PER_WEEK: " + ex.toString());
        schedulePerWeek = 0;
    }

    int iErrCode = FRMMessage.ERR_NONE;
    String errMsg = "";
    String whereClause = "";
    String orderClause = "";
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    
    //out.println("iCommand : "+iCommand);
    ControlLine ctrLine = new ControlLine();
    //System.out.println("iCommand = "+iCommand);
    //System.out.println("prevCommand = "+prevCommand);
    
    iErrCode = ctrlEmployee.action(iCommand, oidEmployee, request, emplx.getFullName(), appUserIdSess);
    
    String empPin = FRMQueryString.requestString(request, "emp_pin");
    String barNum = FRMQueryString.requestString(request, "barcode_num");
    if(iCommand == 99){
        Employee em = new Employee();
        em = PstEmployee.fetchExc(oidEmployee);
        em.setEmpPin(empPin);
        try{
            PstEmployee.updateExc(em);
        }catch(Exception ss){
            
        } 
    }
    
    /**
     * Description : save custom field
     * Date : 2015-06-12
     * Author : Hendra Putu 
     */
    int commandCustom = FRMQueryString.requestInt(request, "command_custom");
    /* input0 = text field */
    String[] input0 = FRMQueryString.requestStringValues(request, "input0");
    String[] hidden0 = FRMQueryString.requestStringValues(request, "hidden0");
    String[] dataType0 = FRMQueryString.requestStringValues(request, "data_type0");
    /* input1 = textarea */
    String[] input1 = FRMQueryString.requestStringValues(request, "input1");
    String[] hidden1 = FRMQueryString.requestStringValues(request, "hidden1");
    String[] dataType1 = FRMQueryString.requestStringValues(request, "data_type1");
    /* input2 = selection */
    String[] input2 = FRMQueryString.requestStringValues(request, "input2");
    String[] hidden2 = FRMQueryString.requestStringValues(request, "hidden2");
    String[] dataType2 = FRMQueryString.requestStringValues(request, "data_type2");
    /* input3 = multiple selection */
    String[] input3 = FRMQueryString.requestStringValues(request, "input3");
    String[] hidden3 = FRMQueryString.requestStringValues(request, "hidden3");
    String[] dataType3 = FRMQueryString.requestStringValues(request, "data_type3");
    /* input4 = datepicker */
    String[] input4 = FRMQueryString.requestStringValues(request, "input4");
    String[] hidden4 = FRMQueryString.requestStringValues(request, "hidden4");
    String[] dataType4 = FRMQueryString.requestStringValues(request, "data_type4");
    /* input5 = datepicker and time */
    String[] input5 = FRMQueryString.requestStringValues(request, "input5");
    String[] hidden5 = FRMQueryString.requestStringValues(request, "hidden5");
    String[] dataType5 = FRMQueryString.requestStringValues(request, "data_type5");
    /* input6 = check box */
    String[] input6 = FRMQueryString.requestStringValues(request, "input6");
    String[] hidden6 = FRMQueryString.requestStringValues(request, "hidden6");
    String[] dataType6 = FRMQueryString.requestStringValues(request, "data_type6");
    /* input7 = radio button */
    String[] input7 = FRMQueryString.requestStringValues(request, "input7");
    String[] hidden7 = FRMQueryString.requestStringValues(request, "hidden7");
    String[] dataType7 = FRMQueryString.requestStringValues(request, "data_type7");
    
    if (commandCustom > 0){
        /* text field*/
        if (hidden0 != null && hidden0.length > 0){
            
            for(int h=0; h<hidden0.length; h++){
                EmpCustomField empCustom = new EmpCustomField();
                empCustom.setCustomFieldId(Long.valueOf(hidden0[h]));
                Date tgl = new Date();
                empCustom.setDataDate(tgl);
                empCustom.setDataNumber(0);
                empCustom.setDataText("-");
                switch(Integer.valueOf(dataType0[h])){
                    case 0: empCustom.setDataText(input0[h]); break;
                    case 1: empCustom.setDataNumber(Double.valueOf(input0[h])); break;
                    case 2: empCustom.setDataNumber(Integer.valueOf(input0[h])); break;
                    case 3: empCustom.setDataText(input0[h]); break;
                    case 4: empCustom.setDataText(input0[h]); break;
                }
                empCustom.setEmployeeId(oidEmployee);
                // insert or update
                String whereEmpCustom = "CUSTOM_FIELD_ID="+hidden0[h]+" AND EMPLOYEE_ID="+oidEmployee;
                Vector listEmpCustom = PstEmpCustomField.list(0, 0, whereEmpCustom, "");
                if (listEmpCustom != null && listEmpCustom.size()>0){
                    long empCustomID = 0;
                    for(int e=0; e<listEmpCustom.size(); e++){
                        EmpCustomField empCust = (EmpCustomField)listEmpCustom.get(e);
                        empCustomID = empCust.getOID();
                    }
                    try {
                        empCustom.setOID(empCustomID);
                        PstEmpCustomField.updateExc(empCustom);
                    } catch(Exception ex){
                        System.out.println("insert emp custom field=>"+ex.toString());
                    }
                } else {
                    try {
                        PstEmpCustomField.insertExc(empCustom);
                    } catch(Exception ex){
                        System.out.println("insert emp custom field=>"+ex.toString());
                    }
                }
            }
            
            
        }
        /* textarea */
        if (hidden1 != null && hidden1.length > 0){
            
            for(int h=0; h<hidden1.length; h++){
                EmpCustomField empCustom = new EmpCustomField();
                empCustom.setCustomFieldId(Long.valueOf(hidden1[h]));
                Date tgl = new Date();
                empCustom.setDataDate(tgl);
                empCustom.setDataNumber(0);
                empCustom.setDataText("-");
                switch(Integer.valueOf(dataType1[h])){
                    case 0: empCustom.setDataText(input1[h]); break;
                    case 1: empCustom.setDataNumber(Double.valueOf(input1[h])); break;
                    case 2: empCustom.setDataNumber(Integer.valueOf(input1[h])); break;
                    case 3: empCustom.setDataText(input1[h]); break;
                    case 4: empCustom.setDataText(input1[h]); break;
                }
                empCustom.setEmployeeId(oidEmployee);
                // insert or update
                String whereEmpCustom = "CUSTOM_FIELD_ID="+hidden1[h]+" AND EMPLOYEE_ID="+oidEmployee;
                Vector listEmpCustom = PstEmpCustomField.list(0, 0, whereEmpCustom, "");
                if (listEmpCustom != null && listEmpCustom.size()>0){
                    long empCustomID = 0;
                    for(int e=0; e<listEmpCustom.size(); e++){
                        EmpCustomField empCust = (EmpCustomField)listEmpCustom.get(e);
                        empCustomID = empCust.getOID();
                    }
                    try {
                        empCustom.setOID(empCustomID);
                        PstEmpCustomField.updateExc(empCustom);
                    } catch(Exception ex){
                        System.out.println("insert emp custom field=>"+ex.toString());
                    }
                } else {
                    try {
                        PstEmpCustomField.insertExc(empCustom);
                    } catch(Exception ex){
                        System.out.println("insert emp custom field=>"+ex.toString());
                    }
                }
            }
            
            
        }
        /* datepicker */
        if (hidden4 != null && hidden4.length > 0){
            
            for(int h=0; h<hidden4.length; h++){
                EmpCustomField empCustom = new EmpCustomField();
                empCustom.setCustomFieldId(Long.valueOf(hidden4[h]));
                if ((!(input4[h].equals("null")))&&(input4[h].length()>0)){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
                    Date tgl = dateFormat.parse(input4[h]); 

                    empCustom.setDataDate(tgl);
                    empCustom.setDataNumber(0);
                    empCustom.setDataText("-");
                    empCustom.setEmployeeId(oidEmployee);
                    // insert or update
                    String whereEmpCustom = "CUSTOM_FIELD_ID="+hidden4[h]+" AND EMPLOYEE_ID="+oidEmployee;
                    Vector listEmpCustom = PstEmpCustomField.list(0, 0, whereEmpCustom, "");
                    if (listEmpCustom != null && listEmpCustom.size()>0){
                        long empCustomID = 0;
                        for(int e=0; e<listEmpCustom.size(); e++){
                            EmpCustomField empCust = (EmpCustomField)listEmpCustom.get(e);
                            empCustomID = empCust.getOID();
                        }
                        try {
                            empCustom.setOID(empCustomID);
                            PstEmpCustomField.updateExc(empCustom);
                        } catch(Exception ex){
                            System.out.println("insert emp custom field=>"+ex.toString());
                        }
                    } else {
                        try {
                            PstEmpCustomField.insertExc(empCustom);
                        } catch(Exception ex){
                            System.out.println("insert emp custom field=>"+ex.toString());
                        }
                    }
                } else {
                    String whereEmpCustom = "CUSTOM_FIELD_ID="+hidden4[h]+" AND EMPLOYEE_ID="+oidEmployee;
                    Vector listEmpCustom = PstEmpCustomField.list(0, 0, whereEmpCustom, "");
                    if (listEmpCustom != null && listEmpCustom.size()>0){
                        long empCustomID = 0;
                        for(int e=0; e<listEmpCustom.size(); e++){
                            EmpCustomField empCust = (EmpCustomField)listEmpCustom.get(e);
                            empCustomID = empCust.getOID();
                        }
                        try {
                            PstEmpCustomField.deleteExc(empCustomID);
                        } catch(Exception ex){
                            System.out.println("insert emp custom field=>"+ex.toString());
                        }
                    }
                }
            }
            
            
        }
        
        
    }
    String DATE_FORMAT_NOW = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

    errMsg = ctrlEmployee.getMessage();
    FrmEmployee frmEmployee = ctrlEmployee.getForm();
    //Employee employee = ctrlEmployee.getEmployee();
    //oidEmployee = employee.getOID();
    //--------------------------------------
    //sehubungan dengan picture
    //update by satrya 2013-02-12
    Employee employee = new Employee();

    try {
        if (oidEmployee != 0) {
            employee = PstEmployee.fetchExc(oidEmployee);
        }
    } catch (Exception exc) {
        employee = new Employee();
        System.out.println("Exception employee" + exc);
    }
    ///update by satrya 2013-10-21
    if (iCommand == Command.GOTO) {
        iCommand = Command.ADD;
        frmEmployee.requestEntityObject(employee);
    }
    if (iCommand == Command.ADD) {
        //employee = new Employee();
        //frmEmployee.requestEntityObject(employee);
    }

    System.out.println("Oid Employee: " + oidEmployee);

   // if (iErrCode != 0) {
    // if (iErrCode != 0 && iCommand == Command.SAVE) {
    //update by sarya 2013-08-13
    //karena waktu save empnya tidak muncul
    if (iCommand == Command.SAVE) {
        employee = ctrlEmployee.getEmployee();
    }
         //}
    //}

    //----------------------------------------
    //locker;
    FrmLocker frmLocker = ctrlEmployee.getFormLocker();
    Locker locker = ctrlEmployee.getLocker();

        String ClientName = "";
        try {
            ClientName = String.valueOf(PstSystemProperty.getValueByName("CLIENT_NAME"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
    
    
//	if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmEmployee.errorSize()<1)){
    if (iCommand == Command.DELETE) {
%>
<jsp:forward page="employee_list.jsp">
    <jsp:param name="prev_command" value="<%=prevCommand%>" />
    <jsp:param name="start" value="<%=start%>" />
    <jsp:param name="employee_oid" value="<%=employee.getOID()%>" />
</jsp:forward>
<%
    }

    //if ((iCommand == Command.SAVE) && (iErrCode == 0)) {
    //  iCommand = Command.EDIT;
    //}
    //Gede_21Nov2011
    boolean isCopy = FRMQueryString.requestBoolean(request, "hidden_copy_status");
    long gotoEmployee = FRMQueryString.requestLong(request, "hidden_goto_employee");

    long companyId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMPANY_ID]);
    long divisionId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID]);
    long departementId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DEPARTMENT_ID]);

    long wacompanyId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_COMPANY_ID]);
    long wadivisionId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DIVISION_ID]);
    long wadepartementId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DEPARTMENT_ID]);

    long wasectionId = FRMQueryString.requestLong(request, FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_SECTION_ID]);

    //update by satrya 20130907
    if (iCommand == Command.GOTO) {
        employee.setCompanyId(companyId);
        employee.setDivisionId(divisionId);
        employee.setDepartmentId(departementId);
    }
    //long sectionId = FRMQueryString.requestLong(request, "hidden_companyId");
    I_Dictionary dictionaryD = userSession.getUserDictionary();
    
 
       if (hidden_recr_application_id != 0) {
           
            try {
                RecrApplication recrApplication = PstRecrApplication.fetchExc(hidden_recr_application_id);
                employee.setFullName(recrApplication.getFullName());

                long positionId = PstPosition.getPositionId(recrApplication.getPosition());

                employee.setPositionId(positionId);
                employee.setBirthDate(recrApplication.getBirthDate());
                employee.setBirthPlace(recrApplication.getBirthPlace());
                employee.setAddressPermanent(recrApplication.getAddress());
                employee.setIndentCardNr(recrApplication.getIdCardNum());
                employee.setPostalCode(recrApplication.getPostalCode());
                employee.setAstekNum(recrApplication.getAstekNum());
                employee.setBloodType(recrApplication.getBloodType());
                employee.setSex(recrApplication.getSex());
                employee.setReligionId(recrApplication.getReligionId());
                employee.setPhone(recrApplication.getPhone());

                employee.setMaritalId(recrApplication.getMaritalId());
                //long oid = PstEmployee.insertExc(employee);
            } catch (Exception e) {
            }

        } 
/*
 * Update by Hendra Putu | 2016-05-10
 * Description : if status administrator maka input without disable
 */   
    String disableElmt = "";
    if (appUserSess.getAdminStatus()==0){
        disableElmt = " disabled=\"disabled\" ";
    } else {
        disableElmt = "";
    }
    
%>
<%
    long oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
    String modul = FRMQueryString.requestString(request, "modul");
    if(iCommand == Command.UPDATE){
      
            try {
            ImageLoader uploader = new ImageLoader();
            int numFiles = uploader.uploadImage(config, request, response);

            //System.out.println("oid di proses upload image : "+oidmaterial);
            String fieldFormName = "fileupload";
            Object obj = uploader.getImage(fieldFormName);

            String fileName = uploader.getFileName();

            byte[] byteOfObj = (byte[]) obj;
            int intByteOfObjLength = byteOfObj.length;
            SessDataUpload objSessDataUpload = new SessDataUpload();	
            if (intByteOfObjLength > 0) {
                String pathFileName = objSessDataUpload.getAbsoluteFileName(fileName);
                try{
                        if(modul.equals("award")){
                            PstEmpAward.updateFileName(fileName,oid);
                        } else if(modul.equals("reprimand")){
                            PstEmpReprimand.updateFileName(fileName,oid);
                        } else if (modul.equals("reldoc")){
                            PstEmpRelevantDoc.updateFileName(fileName, oid);
                        }
                        
                        System.out.println("update sukses.."+fileName);
                 }catch(Exception e){
                        System.out.println("err update.."+e.toString())	;
                 }
                java.io.ByteArrayInputStream byteIns = new java.io.ByteArrayInputStream(byteOfObj);
                uploader.writeCache(byteIns, pathFileName, true);
                try {
                    //PROSES UPDATE

                } catch (Exception eY) {
                    System.out.print("");
                }

            }
            
        } catch(Exception ex){
        
        }
        }  
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>View Profile | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<%= approot%>/assets/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="<%= approot%>/assets/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            @media (min-width: 768px ) {
                .row {
                    position: relative;
                }

                .bottom-align-text {
                  position: absolute;
                  bottom: 0;
                  right: 0;
                }
              }
              .show {
                display: block !important;
              }
              .hidden {
                display: none !important;
                visibility: hidden !important;
              }
        </style>
        <style type="text/css">
            @media screen and (max-width: 480px) {
                #empCompetencyElement{
                    overflow-x: auto;
                }
                #empLanguageElement{
                    overflow-x: auto;
                }
                #empExperienceElement{
                    overflow-x: auto;
                }
                #empEducationElement{
                    overflow-x: auto;
                }#familyMemberElement{
                    overflow-x: auto;
                }#trainingHistoryElement{
                    overflow-x: auto;
                }#empCareerPathElement{
                    overflow-x: auto;
                }#empRelevantDocElement{
                    overflow-x: auto;
                }#empAwardElement{
                    overflow-x: auto;
                }#empWarningElement{
                    overflow-x: auto;
                }#empReprimandElement{
                    overflow-x: auto;
                }#career_element_parent{
                    overflow-x: auto;
                }#contract_element_parent{
                    overflow-x: auto;
                }#career_element_parent{
                    overflow-x: auto;
                }
            }
        </style>
        <script language="JavaScript">
             function cmdUpPictCam(oid){
                window.open("<%=approot%>/employee/databank/employee_cam.jsp?employee_oid="+oid, "Up Pict","height=500,width=500, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes").focus();
            }
            function cmdUpPict(oid){
                window.open("<%=approot%>/employee/databank/up_picture.jsp?employee_oid="+oid, "Up Pict","height=500,width=500, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes").focus();
            }
        </script>
<%@ include file="../../template/css.jsp" %>
<%@ include file="../../template/header.jsp" %>
    </head> 
    <body class="hold-transition skin-blue <%=sidebar%>">
        <div class="wrapper">
            
            <% if (!(namaUser1.equals("Employee"))){ %>
                <%@ include file="../../template/sidebar.jsp" %>
            <% } %>
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
        <div class="wrapper">
            
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Databank
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Databank</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="nav-tabs-custom">
                                                <ul class="nav nav-tabs">
                                                    <li class="dropdown active">
                                                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                                            Profile <span class="caret"></span>
                                                        </a>
                                                        <ul class="dropdown-menu">
                                                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#tab_personal" data-toggle="tab">Personal Data</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabFam" role="menuitem" tabindex="-1" href="#tab_family" data-toggle="tab">Family Member</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabCom" role="menuitem" tabindex="-1" href="#tab_competencies" data-toggle="tab">Competencies</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabLang" role="menuitem" tabindex="-1" href="#tab_language" data-toggle="tab">Language</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabEdu" role="menuitem" tabindex="-1" href="#tab_education" data-toggle="tab">Education</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabExp" role="menuitem" tabindex="-1" href="#tab_experience" data-toggle="tab">Experience</a></li>
                                                        </ul>
                                                    </li>
                                                    <li class="dropdown">
                                                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                                            Career <span class="caret"></span>
                                                        </a>
                                                        <ul class="dropdown-menu">
                                                            <li role="presentation"><a class="tabCareer" role="menuitem" tabindex="-1" href="#tab_cpath" data-toggle="tab">Career Path</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabTraining" role="menuitem" tabindex="-1" href="#tab_training" data-toggle="tab">Training History</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabWarning" role="menuitem" tabindex="-1" href="#tab_warning" data-toggle="tab">Warning</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabReprimand" role="menuitem" tabindex="-1" href="#tab_reprimand" data-toggle="tab">Reprimand</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabAward" role="menuitem" tabindex="-1" href="#tab_award" data-toggle="tab">Award</a></li>
                                                        </ul>
                                                    </li>
                                                    <li><a href="#tab_relevant" data-toggle="tab" class="tabRelevantDoc">Relevant Document</a></li>
                                                    <li><a href="#tab_assets" data-toggle="tab">Assets & Inventory</a></li>
                                                    <li><a href="#tab_setting" data-toggle="tab">Setting</a></li>
                                                </ul>
                                                <div class="tab-content">
                                                    <div class="tab-pane active" id="tab_personal">
                                                        <form name="frm_employee" method="post" action="" id="frm_employee">
                                                        <input type="hidden" name="command" value="">
                                                        <input type="hidden" name="command_custom" value="<%=commandCustom%>">
                                                        <input type="hidden" name="start" value="<%=start%>">

                                                        <!-- Gede_21Nov2011 -->
                                                        <input type="hidden" name="hidden_goto_employee" value="<%=String.valueOf(gotoEmployee)%>">
                                                        <input type="hidden" name="hidden_copy_status">
                                                        <input type="hidden" name="oid" value="<%=employee.getOID()%>">
                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                        <input type="hidden" name="hidden_flag_cmd" value="<%=hidden_command%>">
                                                        <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED]%>" value="<%=employee.getResigned()%>">
                                                        <%
                                                        Date resignDate = employee.getResignedDate()== null ? new Date() : employee.getResignedDate();
                                                        String strResignDate = sdf.format(resignDate);
                                                        %>
                                                        <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DATE_STRING]%>" value="<%=strResignDate%>" class="form-control datepicker" id="datepicker">
                                                        <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_REASON_ID]%>" value="<%=employee.getResignedReasonId()%>" class="form-control">
                                                        <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DESC]%>" value="<%=employee.getResignedDesc()%>">
                                                        <div class="box-info">
                                                            <div class="box-body">
                                                                <div class="row">
                                                                    <div class="col-sm-2">
                                                                        <%
                                                                            String pictPath = "";
                                                                            try {
                                                                                SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                                                                                if(employee.getEmployeeNum().equals("")){
                                                                                    pictPath = "/imgcache/no-img.jpg";
                                                                                }else {
                                                                                    pictPath = sessEmployeePicture.fetchImageEmployee(employee.getEmployeeNum());
                                                                                }    
                                                                            } catch (Exception e) {
                                                                                System.out.println("err." + e.toString());
                                                                            }%> <a>
                                                                            <%
                                                                                 if (pictPath != null && pictPath.length() > 0) {
                                                                                    out.println("<img height=\"135\" id=\"photo\" title=\"Click here to upload\"  src=\"" + approot + "/" + pictPath + "\">");
                                                                                 } else {
                                                                            %>
                                                                            <img width="135" height="135" id="photo" src="<%=approot%>/imgcache/no-img.jpg" />
                                                                            <%

                                                                                }
                                                                            %> </a>
                                                                    </div>
                                                                    <div class="col-sm-6">
                                                                        <h3>
                                                                        <div id="title-info-name"><%=employee.getFullName()%> [<%=employee.getEmployeeNum()%>]</div>
                                                                        <br />
                                                                        <div id="title-info-desc"><%=PstEmployee.getCompanyStructureName(employee.getOID())%></div>
<!--                                                                        <div ><a class="btn-default fa fa-camera" id="Take"  href="javascript:cmdUpPictCam('<%=oidEmployee%>')"></a>  <a class="btn-default fa fa-folder" id="Take"  href="javascript:cmdUpPict('<%=oidEmployee%>')"></a></div>-->
                                                                        </h3>
                                                                        
                                                                    </div>
                                                                </div>
                                                            
                                                            
                                                        </div>
                                                        </div>
                                                        <hr />
                                                        
                                                        <div class="row">
                                                            <div class="col-md-4" >
                                                                <div class="box" id="hide_basic" style="display: none">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Basic Information</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="show_basic_b">Show</button></div>
                                                                    </div>
                                                                </div>    
                                                                <div class="box" id="show_basic">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Basic Information</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="hide_basic_b">Hide</button></div>
                                                                    </div>
                                                                    <div class="box-body">
                                                                        <div class="form-group">
                                                                            <label>Employee Number</label>
                                                                            <p><%=employee.getEmployeeNum()%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Full Name</label>
                                                                            <p><%=(!(employee.getFullName().equals("")) ? employee.getFullName() : "-" ) %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Temporary Address</label>
                                                                            <%
                                                                              String negara = "";
                                                                              String provinsi = "";
                                                                              String kabupaten = "";
                                                                              String kecamatan = "";
                                                                              
                                                                              Negara addrCountry = new Negara();
                                                                              try {
                                                                                  addrCountry = PstNegara.fetchExc(employee.getAddrCountryId());
                                                                                  negara = addrCountry.getNmNegara();
                                                                              } catch (Exception exc){
                                                                                  
                                                                              }
                                                                              
                                                                              Provinsi addrProvince = new Provinsi();
                                                                              try {
                                                                                  addrProvince = PstProvinsi.fetchExc(employee.getAddrProvinceId());
                                                                                  provinsi = addrProvince.getNmProvinsi();
                                                                              } catch (Exception exc) {
                                                                                  
                                                                              }
                                                                              
                                                                              Kabupaten addrRegency = new Kabupaten();
                                                                              try{
                                                                                  addrRegency = PstKabupaten.fetchExc(employee.getAddrRegencyId());
                                                                                  kabupaten = addrRegency.getNmKabupaten();
                                                                              } catch (Exception exc){
                                                                                  
                                                                              }
                                                                              
                                                                              Kecamatan addrSubRegency = new Kecamatan();
                                                                              try{
                                                                                  addrSubRegency = PstKecamatan.fetchExc(employee.getAddrPmntSubRegencyId());
                                                                                  kecamatan = addrSubRegency.getNmKecamatan();
                                                                              } catch (Exception exc){
                                                                                  
                                                                              }
                                                                              
                                                                            %>    
                                                                            <p><%=employee.getAddress()%><%="," + (!(negara.equals("")) ? negara : "-") + "," + (!(provinsi.equals("")) ? provinsi : "-") + "," + (!(kabupaten.equals("")) ? kabupaten : "-") + "," + (!(kecamatan.equals("")) ? kecamatan : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Permanent Address</label>
                                                                            <%
                                                                              String negaraPmnt = "";
                                                                              String provinsiPmnt = "";
                                                                              String kabupatenPmnt = "";
                                                                              String kecamatanPmnt = "";
                                                                              
                                                                              Negara addrCountryPmnt = new Negara();
                                                                              try {
                                                                                  addrCountryPmnt = PstNegara.fetchExc(employee.getAddrCountryId());
                                                                                  negaraPmnt = addrCountry.getNmNegara();
                                                                              } catch (Exception exc){
                                                                                  
                                                                              }
                                                                              
                                                                              Provinsi addrProvincePmnt = new Provinsi();
                                                                              try {
                                                                                  addrProvincePmnt = PstProvinsi.fetchExc(employee.getAddrProvinceId());
                                                                                  provinsiPmnt = addrProvince.getNmProvinsi();
                                                                              } catch (Exception exc) {
                                                                                  
                                                                              }
                                                                              
                                                                              Kabupaten addrRegencyPmnt = new Kabupaten();
                                                                              try{
                                                                                  addrRegencyPmnt = PstKabupaten.fetchExc(employee.getAddrRegencyId());
                                                                                  kabupatenPmnt = addrRegency.getNmKabupaten();
                                                                              } catch (Exception exc){
                                                                                  
                                                                              }
                                                                              
                                                                              Kecamatan addrSubRegencyPmnt = new Kecamatan();
                                                                              try{
                                                                                  addrSubRegencyPmnt = PstKecamatan.fetchExc(employee.getAddrPmntSubRegencyId());
                                                                                  kecamatanPmnt = addrSubRegency.getNmKecamatan();
                                                                              } catch (Exception exc){
                                                                                  
                                                                              }
                                                                              
                                                                            %>
                                                                            <p><%=employee.getAddressPermanent()%><%="," + (!(negaraPmnt.equals("")) ? negaraPmnt : "-") + "," + (!(provinsiPmnt.equals("")) ? provinsiPmnt : "-") + "," + (!(kabupatenPmnt.equals("")) ? kabupatenPmnt : "-") + "," + (!(kecamatanPmnt.equals("")) ? kecamatanPmnt : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Zip Code</label>
                                                                            <p><%=(employee.getPostalCode() > 0 ? employee.getPostalCode() : "-")%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Telephone / Handphone</label>
                                                                            <p><%= (!(employee.getPhone().equals("")) ? employee.getPhone() : "-") + " / " + (!(employee.getHandphone().equals("")) ? employee.getHandphone() : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Emergency Phone / Person Name</label>
                                                                            <p><%= (!(employee.getPhoneEmergency().equals("")) ? employee.getPhoneEmergency() : "-" ) + " / " + (!(employee.getNameEmg().equals("")) ? employee.getPhoneEmergency() : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Emergency Address</label>
                                                                            <p><%= (!(employee.getAddressEmg().equals("")) ? employee.getAddressEmg() : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Gender &nbsp</label>
                                                                            <p><%=PstEmployee.sexKey[employee.getSex()]%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Place of Birth</label>  
                                                                            <p><%=employee.getBirthPlace()%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Date of Birth</label>
                                                                             <%
                                                                            Date birthDate = employee.getBirthDate()== null ? new Date() : employee.getBirthDate();
                                                                            String strBirthDate = sdf.format(birthDate);
                                                                            %>
                                                                            <p><%=strBirthDate %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Shio </label>
                                                                            <p><%=employee.getShio()%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Element </label>
                                                                            <p><%=employee.getElemen()%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Religion</label>
                                                                        <%
                                                                            Religion religion = new Religion();
                                                                            try {
                                                                                religion = PstReligion.fetchExc(employee.getReligionId());
                                                                            } catch (Exception exc){
                                                                                
                                                                            }
                                                                        %>
                                                                            <p><%= (!(religion.getReligion().equals("")) ? religion.getReligion() : "-") %></p>
                                                                        </div>
                                                                    
                                                                        <div class="form-group">
                                                                            <label>Marital Status for HR</label>    
                                                                        <%
                                                                            Marital marital = new Marital();
                                                                            try {
                                                                                marital = PstMarital.fetchExc(employee.getMaritalId());
                                                                            } catch (Exception exc){
                                                                                
                                                                            }
                                                                            
                                                                        %>
                                                                            <p><%= (!(marital.getMaritalStatus().equals("")) ? marital.getMaritalStatus() : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Marital Status for Tax Report</label>
                                                                        <%
                                                                            Marital taxMarital = new Marital();
                                                                            try{
                                                                                taxMarital = PstMarital.fetchExc(employee.getTaxMaritalId());
                                                                            } catch (Exception exc){
                                                                                
                                                                            }
                                                                        %>
                                                                            <p><%= (!(taxMarital.getMaritalStatus().equals("")) ? taxMarital.getMaritalStatus() : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Blood Type</label>
                                                                            <p><%=employee.getBloodType()%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Race</label>
                                                                        <%
                                                                            Race race = new Race();
                                                                            try {
                                                                                race = PstRace.fetchExc(employee.getRace());
                                                                            } catch (Exception exc){
                                                                                
                                                                            }
                                                                        %>
                                                                            <p><%=(!(race.getRaceName().equals("")) ? race.getRaceName() : "-")%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Barcode Number</label>
                                                                            <p><%=employee.getBarcodeNumber()%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>ID Card Type / Number</label>
                                                                            <p><%= employee.getIdcardtype() + " / " + (!(employee.getIndentCardNr().equals("")) ? employee.getIndentCardNr() : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>ID Card Valid Until</label>
                                                                            <%
                                                                            Date idDate = employee.getIndentCardValidTo()== null ? new Date() : employee.getIndentCardValidTo();
                                                                            String strIdDate = sdf.format(idDate);
                                                                            %>
                                                                            <p><%=strIdDate %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Secondary ID Card Type / Number</label>
                                                                            <p><%= employee.getSecondaryIdType() + " / " + (!(employee.getSecondaryIdNo().equals("")) ? employee.getSecondaryIdNo() : "-" ) %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Secondary ID Card Valid Until</label>
                                                                            <%
                                                                            Date secIdDate = employee.getSecondaryIDValid()== null ? new Date() : employee.getSecondaryIDValid();
                                                                            String strSecIdDate = sdf.format(secIdDate);
                                                                            %>
                                                                            <p><%= strSecIdDate %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Email</label>
                                                                            <p><%= (!(employee.getEmailAddress().equals("")) ? employee.getEmailAddress() : "-" ) %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Payroll Group</label>
                                                                        <%
                                                                            PayrollGroup payrollGroup = new PayrollGroup();
                                                                            try{
                                                                               payrollGroup = PstPayrollGroup.fetchExc(employee.getPayrollGroup()); 
                                                                            } catch(Exception exc){
                                                                                
                                                                            }
                                                                        %>
                                                                        <p><%=(!(payrollGroup.getPayrollGroupName().equals("")) ? payrollGroup.getPayrollGroupName() : "-" )%></p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                  
                                                            </div>
                                                            
                                                            <div class="col-md-4">
                                                                
                                                                <div class="box" id="hide_comp" style="display: none">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Company Information</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="show_comp_b">Show</button></div>
                                                                    </div>
                                                                </div>   
                                                                <div class="box" id="show_comp">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Company Information</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="hide_comp_b">Hide</button></div>
                                                                    </div>
                                                                    <div class="box-body">
                                                                        <div class="form-group">
                                                                        <label>
                                                                            Company 
                                                                        </label>
                                                                            <%
                                                                                Company comp = new Company();
                                                                                try{
                                                                                    comp = PstCompany.fetchExc(employee.getCompanyId());
                                                                                } catch (Exception exc){

                                                                                }
                                                                            %>
                                                                            <p><%= comp.getCompany() %></p>
                                                                        </div>
                                                                        <div class="form-group">    
                                                                        <label>
                                                                           Division
                                                                        </label>
                                                                            <%
                                                                                Division divisi = new Division();
                                                                                try {
                                                                                    divisi = PstDivision.fetchExc(employee.getDivisionId());
                                                                                } catch (Exception exc){

                                                                                }
                                                                            %>
                                                                            <p><%= divisi.getDivision() %></p>
                                                                        </div>
                                                                        <div class="form-group">  
                                                                        <label>
                                                                            Department
                                                                        </label>
                                                                            <%  
                                                                                Department depart = new Department();
                                                                                try {
                                                                                    depart = PstDepartment.fetchExc(employee.getDepartmentId());
                                                                                } catch (Exception exc){
                                                                                    
                                                                                }
                                                                            %>
                                                                            <p><%= depart.getDepartment() %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">  
                                                                        <label>
                                                                            Section
                                                                        </label>
                                                                            <%  Section section = new Section();
                                                                                try {
                                                                                    section = PstSection.fetchExc(employee.getSubSectionId());
                                                                                } catch (Exception exc){
                                                                                    
                                                                                }
                                                                            %>
                                                                            <p><%= (!(section.getSection().equals("")) ? section.getSection() : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">  
                                                                        <label>
                                                                            Employee Category
                                                                        </label>
                                                                            <%  EmpCategory empCategory = new EmpCategory();
                                                                                try {
                                                                                    empCategory = PstEmpCategory.fetchExc(employee.getEmpCategoryId());
                                                                                } catch (Exception exc){
                                                                                    
                                                                                }
                                                                            %>
                                                                            <p><%= empCategory.getEmpCategory() %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>
                                                                            Level
                                                                        </label>
                                                                            <%  Level level = new Level();
                                                                                try {
                                                                                    level = PstLevel.fetchExc(employee.getLevelId());
                                                                                } catch (Exception exc){
                                                                                    
                                                                                }
                                                                                
                                                                                GradeLevel gradeLevel = new GradeLevel();
                                                                                try{
                                                                                    gradeLevel = PstGradeLevel.fetchExc(employee.getGradeLevelId());
                                                                                } catch (Exception exc){
                                                                                    
                                                                                }
                                                                            %>
                                                                            <p><%= level.getLevel() + " - " + gradeLevel.getCodeLevel() %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>
                                                                            Position
                                                                        </label>
                                                                            <%  Position position = new Position();
                                                                                try {
                                                                                    position = PstPosition.fetchExc(employee.getPositionId());
                                                                                } catch (Exception exc){
                                                                                    
                                                                                }
                                                                            %>
                                                                            <p><%= position.getPosition() %></p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                
                                                                <div class="box" id="hide_wa" style="display: none">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Work Assign Information</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="show_wa_b">Show</button></div>
                                                                    </div>
                                                                </div> 
                                                                <div class="box" id="show_wa">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Work Assign Information</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="hide_wa_b">Hide</button></div>
                                                                    </div>
                                                                    <div class="box-body">
                                                                        <div class="box-body">
                                                                        <div class="form-group">
                                                                        <label>
                                                                            W.A. Company
                                                                        </label>
                                                                            <% 
                                                                               Company compWa = new Company();
                                                                               try {
                                                                                   compWa = PstCompany.fetchExc(employee.getWorkassigncompanyId());
                                                                               } catch (Exception exc){

                                                                               }
                                                                            %>
                                                                            <p><%= (!(compWa.getCompany().equals("")) ? compWa.getCompany() : "-" ) %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">    
                                                                        <label>
                                                                           W.A. Division
                                                                        </label>
                                                                            <%
                                                                                Division divisiWa = new Division();
                                                                                try{
                                                                                    divisiWa = PstDivision.fetchExc(employee.getWorkassigndivisionId());
                                                                                } catch (Exception exc){

                                                                                }
                                                                            %>
                                                                            <p><%= (!(divisiWa.getDivision().equals("")) ? divisiWa.getDivision() : "-") %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>
                                                                            W.A. Department
                                                                        </label>
                                                                            <%
                                                                                Department departWa = new Department();
                                                                                try{
                                                                                    departWa = PstDepartment.fetchExc(employee.getWorkassigndepartmentId());
                                                                                } catch (Exception exc){
                                                                                    
                                                                                }
                                                                            %>
                                                                            <p><%= (!(departWa.getDepartment().equals("")) ? departWa.getDepartment() : "-" ) %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>
                                                                            W.A. Section
                                                                        </label>
                                                                            <%
                                                                                Section sectionWa = new Section();
                                                                                try{
                                                                                    sectionWa = PstSection.fetchExc(employee.getWorkassignsectionId());
                                                                                } catch (Exception exc){

                                                                                }
                                                                            %>
                                                                            <p><%= (!(sectionWa.getSection().equals("")) ? sectionWa.getSection() : "-" ) %></p>
                                                                        </div>

                                                                        <div class="form-group">
                                                                        <label>
                                                                            W.A. Position
                                                                        </label>
                                                                            <%
                                                                                Position positionWa = new Position();
                                                                                try{
                                                                                    positionWa = PstPosition.fetchExc(employee.getWorkassignpositionId());
                                                                                } catch (Exception exc){

                                                                                }
                                                                            %>
                                                                            <p><%= (!(positionWa.getPosition().equals("")) ? positionWa.getPosition() : "-" ) %></p>
                                                                        </div>   
                                                                        
                                                                        <div class="form-group">
                                                                        <label>
                                                                            W.A. Provider
                                                                        </label>
                                                                            <%
                                                                                ContactList waContact = new ContactList();
                                                                                try {
                                                                                    waContact = PstContactList.fetchExc(employee.getProviderID());
                                                                                } catch (Exception exc){

                                                                                }
                                                                            %>
                                                                            <pa><%= (!(waContact.getCompName().equals("")) ? waContact.getCompName() : "-" ) %></pa>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>
                                                                                Duration
                                                                            </label>
                                                                            <%
                                                                            Date waFromDate = employee.getWaFrom()== null ? new Date() : employee.getWaFrom();
                                                                            Date waToDate = employee.getWaTo()== null ? new Date() : employee.getWaTo();
                                                                            String strWaFromDate = sdf.format(waFromDate);
                                                                            String strWaToDate = sdf.format(waToDate);
                                                                            %>
                                                                            <p><%= strWaFromDate + " to " + strWaToDate %></p>
                                                                             
                                                                            </div>  
                                                                    </div>
                                                                    </div>
                                                                </div>
                                                                
                                                                <div class="box" id="hide_career" style="display: none">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Contract</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="show_career_b">Show</button></div>
                                                                    </div>
                                                                </div> 
                                                                <div class="box" id="show_career">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Contract</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="hide_career_b">Hide</button></div>
                                                                    </div>
                                                                
                                                                    <div class="box-body">
                                                                        <div class="form-group">
                                                                        <label>Commencing Date</label>
                                                                        <div class="input-group">
                                                                            <%
                                                                            Date commDate = employee.getCommencingDate()== null ? new Date() : employee.getCommencingDate();
                                                                            String strCommDate = sdf.format(commDate);
                                                                            %>
                                                                            <p><%= strCommDate %></p>
                                                                        </div>
                                                                        <div class="form-group">
                                                                        <label>Probation End Date</label>
                                                                            <%
                                                                            Date probDate = employee.getProbationEndDate()== null ? new Date() : employee.getProbationEndDate();
                                                                            String strProbDate = sdf.format(probDate);
                                                                            %>
                                                                            <p><%= strProbDate %></p>
                                                                        </div>
                                                                        <div class="form-group">
                                                                        <label>Ended Contract</label>
                                                                            <%
                                                                            Date endContractDate = employee.getEnd_contract()== null ? new Date() : employee.getEnd_contract();
                                                                            String strContractDate = sdf.format(endContractDate);
                                                                            %>
                                                                            <p><%= strContractDate %></p>
                                                                        </div>
                                                                    </div>
                                                                    </div>
                                                                </div>
                                                                        <div class="box" id="hide_bank" style="display: none">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Bank Account</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="show_bank_b">Show</button></div>
                                                                    </div>
                                                                </div>         
                                                                <div class="box" id="show_bank">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Bank Account</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="hide_bank_b">Hide</button></div>
                                                                    </div>
                                                                    <div class="box-body">
                                                                        <div class="form-group">
                                                                            <label>No Rekening</label>
                                                                            <p><%=(!(employee.getNoRekening().equals("")) ? employee.getNoRekening() : "-")%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>NPWP</label>
                                                                            <p><%=(!(employee.getNpwp().equals("")) ? employee.getNpwp() : "-")%></p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4">
                                                            <div class="box" id="hide_other" style="display: none">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Other Information</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="show_other_b">Show</button></div>
                                                                    </div>
                                                                </div>         
                                                                <div class="box" id="show_other">
                                                                     <div class="box-header with-border">
                                                                        <div class="pull-left">Other Information</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="hide_other_b">Hide</button></div>
                                                                    </div>
                                                                    <div class="box-body">
                                                                        <div class="form-group">
                                                                            <label>BPJS Ketenaga Kerjaan Number</label>
                                                                            <p><%=(!(employee.getAstekNum().equals("")) ? employee.getAstekNum() : "-" )%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>BPJS Ketenaga Kerjaan Date</label>
                                                                            <%
                                                                            Date astekDate = employee.getAstekDate()== null ? new Date() : employee.getAstekDate();
                                                                            String strAstekDate = sdf.format(astekDate);
                                                                            %>
                                                                            <p><%= strAstekDate %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>BPJS Kesehatan No.</label>
                                                                            <p><%=(!(employee.getBpjs_no().equals("")) ? employee.getBpjs_no() : "-")%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>BPJS Kesehatan Date</label>
                                                                            <%
                                                                            Date bpjsDate = employee.getBpjs_date()== null ? new Date() : employee.getBpjs_date();
                                                                            String strBpjsDate = sdf.format(bpjsDate);
                                                                            %>
                                                                            <p><%= strBpjsDate %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Member of BPJS Kesehatan</label>
                                                                            <p><%=PstEmployee.memberOfBPJSKesehatanKey[employee.getMemOfBpjsKesahatan()]%> </p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Member of BPJS Ketenagakerjaan</label>
                                                                            <p><%=PstEmployee.memberOfBPJSKetenagaKerjaanKey[employee.getMemOfBpjsKetenagaKerjaan()]%> </p> 
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Status Pensiun Program</label>
                                                                            <p><%=PstEmployee.statusPensiunProgramKey[employee.getStatusPensiunProgram()]%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Start Date Program Pensiun (Mulai diangkat)</label>
                                                                            <%
                                                                            Date pensiunDate = employee.getStartDatePensiun()== null ? new Date() : employee.getStartDatePensiun();
                                                                            String strPensiunDate = sdf.format(pensiunDate);
                                                                            %>
                                                                            <p><%= strPensiunDate %></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Dana Pendidikan</label>
                                                                        <p><%=PstEmployee.statusDanaPendidikanKey[employee.getDanaPendidikan()]%></p>
                                                                        </div>
                                                                        
<!--                                                                        <div class="form-group"> 
                                                                        <label>Employee PIN</label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_PIN)%></font>&nbsp;
                                                                        <input type="password"  name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_PIN]%>" value="<%=(employee.getEmpPin() != null ? employee.getEmpPin() : "")%>" autocomplete="new-password" class="form-control">
                                                                         
                                                                        </div>-->
                                                                        
                                                                        
                                                                        
                                                                    </div>
                                                                </div>
                                                                <div class="box" id="hide_IQ" style="display: none">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Quotient Information</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="show_IQ_b">Show</button></div>
                                                                    </div>
                                                                </div>         
                                                                <div class="box" id="show_IQ">
                                                                    <div class="box-header with-border">
                                                                       <div class="pull-left">Quotient Information</div>
                                                                       <div class="pull-right"><button type="button" class="btn btn-sm" id="hide_IQ_b">Hide</button></div>
                                                                    </div>
                                                                    <div class="box-body">
                                                                        <div class="form-group">
                                                                            <label>IQ</label>
                                                                            <p><%=(!(employee.getIq().equals("")) ? employee.getIq() : "-")%></p>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>EQ</label>
                                                                            <p><%=(!(employee.getEq().equals("")) ? employee.getEq() : "-")%></p>
                                                                        </div>
                                                                        
                                                                    </div>
                                                                </div>
                                                                
                                                                
                                                                <div class="box" id="hide_schedule" style="display: none">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Default Schedule</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="show_schedule_b">Show</button></div>
                                                                    </div>
                                                                </div>     
                                                                <div class="box" id="show_schedule">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left">Default Schedule</div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="hide_schedule_b">Hide</button></div>
                                                                    </div>
                                                                    <div class="box-body" >
                                                                     <%
                                                                        String whereClauseDS = PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]+"="+employee.getOID();
                                                                        String orderDS= PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX] ;
                                                                        Vector dftSchedules = PstDefaultSchedule.list(0, 35, whereClauseDS, orderDS);
                                                                        //Vector dftSchedules = PstDefaultSchedule.list(0, 7, whereClauseDS, orderDS);
                                                                    %> 
                                                                    <div class="table-responsive">
                                                                    <table>
                                                                        <tr>
                                                                            <td>&nbsp;</td>
                                                                            <th>Sun</th>
                                                                            <th>Mon</th>
                                                                            <th>Tue</th>
                                                                            <th>Wed</th>
                                                                            <th>Thu</th>
                                                                            <th>Fri</th>
                                                                            <th>Sat</th>
                                                                        </tr>            
                                                                        <tr>
                                                                            <%
                                                                            String week="";
                                                                            if(schedulePerWeek!=0){
                                                                                week= " Week ";
                                                                            }    
                                                                            %>
                                                                            <td> <%=week%> 1st </td>
                                                                            <%                    
                                                                              for(int idx=1;idx <= 7; idx++){
                                                                                DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                                                                %>
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" disabled='disabled' /> </td>
                                                                                
                                                                                <%
                                                                                }%>
                                                                        </tr>
                                                                        <!-- update by satrya 2013-04-08 -->
                                                                        <%if(schedulePerWeek!=0){%>
                                                                        <tr>
                                                                            <td> Week 2nd </td>
                                                                            <%                    
                                                                              for(int idx=8;idx <= 14; idx++){
                                                                                DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                                                                %>
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" disabled='disabled' /> </td>
                                                                                <%
                                                                                }%>
                                                                        </tr>
                                                                        <tr>
                                                                            <td> Week 3rd </td>
                                                                            <%                    
                                                                              for(int idx=15;idx <= 21; idx++){
                                                                                DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                                                                %>
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" disabled='disabled' /> </td>
                                                                                <%
                                                                                }%>
                                                                        </tr>
                                                                        <tr>
                                                                            <td> Week 4th </td>
                                                                            <%                    
                                                                              for(int idx=22;idx <= 28; idx++){
                                                                                DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                                                                %>
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" disabled='disabled' /> </td>
                                                                                <%
                                                                                }%>
                                                                        </tr>
                                                                        <tr>
                                                                            <td> Week 5th </td>
                                                                            <%                    
                                                                              for(int idx=29;idx <=35; idx++){
                                                                                DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                                                                %>
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" disabled='disabled' /> </td>
                                                                                <%
                                                                                }%>
                                                                        </tr>
                                                                        <%}%>
                                                                        <%if(schedulePerWeek!=0){%>
                                                                        <tr>
                                                                            <td>2nd2</td>
                                                                                <%                    
                                                                              for(int idx=1;idx <= 7; idx++){
                                                                                DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                                                                %>
                                                                                <td><input type="text" size="4" name="sche2_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule2()): "-" )%>" disabled='disabled' </td>
                                                                                <%
                                                                                }%>                
                                                                        </tr>
                                                                        <%}%>
                                                                    </table>
                                                                    </div>
                                                                    <div style="margin: 11px 0px;">
                                                                        <strong>Presence Check Parameter</strong> &nbsp;:&nbsp;
                                                                        <%
                                                                        if (appUserSess.getAdminStatus()==0){
                                                                            %>
                                                                            <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PRESENCE_CHECK_PARAMETER]%>" value="<%=employee.getPresenceCheckParameter()%>" />
                                                                            Tidak Dapat Di-setting (No Privilege)
                                                                            <%
                                                                        } else {
                                                                             for (int i = 0; i < PstEmployee.presenceCheckValue.length; i++) {
                                                                                 String strPresenceCheck = "";
                                                                                 if (employee.getPresenceCheckParameter() == PstEmployee.presenceCheckValue[i]) {
                                                                                     strPresenceCheck = "checked";
                                                                                 }
                                                                             %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PRESENCE_CHECK_PARAMETER]%>" value="<%="" + PstEmployee.presenceCheckValue[i]%>" <%=strPresenceCheck%> style="border:'none'">
                                                                             <%=PstEmployee.presenceCheckKey[i]%> 
                                                                             <%}%> 
                                                                        <%
                                                                        }
                                                                        %>

                                                                    </div>
<!--                                                                        <a id="btn" href="javascript:editDefaultSch()">Edit Default Schedule</a>-->
                                                                    
                                                                    </div>
                                                                </div>          
                                                            </div> 
                                                       <% if(iErrCode!=FRMMessage.ERR_NONE  ){%>
                                                        <% 
                                                         int maxFld = FrmEmployee.fieldNames.length;
                                                         for(int idx=0;idx< maxFld;idx++)
                                                         {     
                                                            String msgX = frmEmployee.getErrorMsg(idx); 
                                                            if(msgX!=null && msgX.length()>0){
                                                               //out.println( FrmEmployee.fieldNamesForUser[idx]+" = "+ "<font color='red'>"+frmEmployee.getErrorMsg(idx)+"</font>;");
                                                             }
                                                         }%>
                                                         <br> 
                                                         <div class="alert alert-danger col-md-6" role="alert">
                                                            <strong>Can't Save !</strong> Some Data Doesn't Complete, Please Check  .. Before Save
                                                         </div>
                                                          <%
                                                           }
                                                         %>
                                                        
                                                        </div>
                                                        
                                                        
                                                         
                                                    </div><!-- /.tab-pane -->
                                                    <div class="tab-pane" id="tab_family"  >
                                                        <a> <h3>Family Member List : </h3></a>

                                                        <hr>
                                                        <div id="familyMemberElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 3%">No</th>
                                                                        <th style="width: 10%">Full Name / Sex</th>
                                                                        <th style="width: 10%">Relationship</th>
                                                                        <th style="width: 5%">Guaranted</th>
                                                                        <th style="width: 10%">Date of Birth</th>
                                                                        <th style="width: 10%">Job / Address</th>
                                                                        <th style="width: 10%">ID KTP</th>
                                                                        <th style="width: 10%">Education / Religion</th>
                                                                        <th style="width: 10%">Phone Number</th>
                                                                        <th style="width: 10%">BPJS Number</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_competencies"  >
                                                        <a> <h3>Competency List : </h3></a>

                                                        <hr>
                                                        <div id="empCompetencyElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th>No.</th>
                                                                        <th>Competency</th>
                                                                        <th>Level Value</th>					
                                                                        <th>Date of Achievment</th>
                                                                        <th>Special Achievment</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>   
                                                        
                                                    
                                                    <div class="tab-pane" id="tab_language"  >
                                                        <a> <h3>Language List : </h3></a>

                                                        <hr>
                                                        <div id="empLanguageElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th>No.</th>
                                                                        <th>Language</th>
                                                                        <th>Oral</th>					
                                                                        <th>Written</th>
                                                                        <th>Description</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <div class="tab-pane" id="tab_education"  >
                                                        <a> <h3>Education List : </h3></a>

                                                        <hr>
                                                        <div id="empEducationElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 3%">No.</th>
                                                                        <th style="width: 10%">Education</th>
                                                                        <th style="width: 15%">University/Institution</th>
                                                                        <th style="width: 15%">Graduation</th>
                                                                        <th style="width: 10%">Start Year</th>					
                                                                        <th style="width: 10%">End Year</th>
                                                                        <th style="width: 10%">Point</th>
                                                                        <th style="width: 10%">Description</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <div class="tab-pane" id="tab_experience"  >
                                                        <a> <h3>Experience List : </h3></a>

                                                        <hr>
                                                        <div id="empExperienceElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 3%">No.</th>
                                                                        <th style="width: 20%">Company Name</th>
                                                                        <th style="width: 5%">Start Date</th>					
                                                                        <th style="width: 5%">End Date</th>
                                                                        <th style="width: 10%">Position</th>
                                                                        <th style="width: 25%">Move Reason</th>
                                                                        <th style="width: 20%">Provider</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_award"  >
                                                        <a> <h3>Employee Award List : </h3></a>

                                                        <hr>
                                                        <div id="empAwardElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 2%">No.</th>
                                                                        <th style="width: 5%">Date</th>
                                                                        <th style="width: 10%">Award Title</th>					
                                                                        <th style="width: 10%">Division (Internal)</th>
                                                                        <th style="width: 10%">Award From (External)</th>
                                                                        <th style="width: 10%">Award Type</th>
                                                                        <th style="width: 10%">Description</th>
                                                                        <th style="width: 10%">Document</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_cpath">
                                                        <a><h3>Career Path</h3></a>
                                                        <hr>
                                                        <div id="career_element_parent">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 2%">No</th>
                                                                        <th style="width: 8%">Company</th>
                                                                        <th style="width: 8%">Division</th>
                                                                        <th style="width: 8%">Department</th>
                                                                        <th style="width: 8%">Section</th>
                                                                        <th style="width: 8%">Position</th>
                                                                        <th style="width: 8%">Level</th>
                                                                        <th style="width: 8%">Category</th>
                                                                        <th style="width: 8%">History From</th>
                                                                        <th style="width: 8%">History To</th>
                                                                        <th style="width: 8%">Contract From</th>
                                                                        <th style="width: 8%">Contract To</th>
                                                                        <th style="width: 8%">History Type</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_relevant"  >
                                                        <a> <h3>Employee Relevant Doc List : </h3></a>

                                                        <hr>
                                                        <div id="empRelevantDocElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th>No.</th>
                                                                        <th>Document Group</th>
                                                                        <th>Document Title</th>					
                                                                        <th>Description</th>
                                                                        <th>File Name</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_assets"  >
                                                        <a> <h3>Assets & Inventory : </h3></a>
                                                        <hr>
                                                            <a> <h4>No Record </h4></a>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_setting"  >
                                                        <a> <h3>Employee Setting : </h3></a>
                                                        <hr>
                                                        
                                                        <div class="form-group"> 
                                                        <label>Employee PIN</label> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_PIN)%>&nbsp;
                                                        <input type="password"  name="emp_pin" value="<%=(employee.getEmpPin() != null ? employee.getEmpPin() : "")%>" autocomplete="new-password" class="form-control">
                                                        </div>
                                                        
<!--                                                        <div class="form-group">
                                                            <label>Barcode Number</label>  * <%=frmEmployee.getErrorMsgModif(FrmEmployee.FRM_FIELD_BARCODE_NUMBER)%>
                                                            <input  type="text" name="barcode_num" title=" If Employe is a Daily Worker (DL)  please replace 'DL-' with '12' ,for  example 'DL-333' become to '12333'.     If Employe's  Status  'Resigned'  please input the barcode number, barcode number is unique for example -R(BarcodeNumb/PinNumber)" value="<%=(employee.getBarcodeNumber() != null ? employee.getBarcodeNumber() : "")%>" class="form-control">
                                                        </div>-->
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                <strong><a href="javascript:cmdSaveSetting('<%=employee.getOID()%>')" class="btn btn-primary"><i class="fa fa-save"></i> Save Setting</a></strong>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_training" >
                                                        <a> <h3>Training History List : </h3></a>

                                                        <hr>
                                                        <div id="trainingHistoryElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 3%">No.</th>
                                                                        <th style="width: 20%">Training Program</th>
                                                                        <th style="width: 20%">Training Title</th>					
                                                                        <th style="width: 10%">Trainer</th>
                                                                        <th style="width: 10%">Start Date</th>
                                                                        <th style="width: 10%">End Date</th>
                                                                        <th style="width: 5%">Duration</th>
                                                                        <th style="width: 10%">Remark</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div> 
                                                    
                                                    <div class="tab-pane" id="tab_warning"  >
                                                        <a> <h3>Warning List : </h3></a>

                                                        <hr>
                                                        <div id="empWarningElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 3%">No</th>
                                                                        <th style="width: 10%">Company</th>
                                                                        <th style="width: 10%">Division</th>
                                                                        <!--<th>Department</th>-->
                                                                        <th style="width: 10%">Position</th>
                                                                        <th style="width: 10%">Break Date</th>
                                                                        <th style="width: 10%">Break Fact</th>
                                                                        <th style="width: 10%">Warning Date</th>
                                                                        <th style="width: 5%">Warning Level ( Point )</th>
                                                                        <th style="width: 10%">Warning By </th>
                                                                        <th style="width: 10%">Valid Until </th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>            
                                                                
                                                     <div class="tab-pane" id="tab_reprimand"  >
                                                        <a> <h3>Rerimand List : </h3></a>

                                                        <hr>
                                                        <div id="empReprimandElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                         <th style="width: 3%">No</th>
                                                                        <!--<th style="width: 10%">Company</th>-->
                                                                        <th style="width: 10%">Division</th>
                                                                        <th style="width: 10%">Position</th>
                                                                        <th style="width: 10%">Date</th>
                                                                        <th style="width: 5%">Chapter</th>
                                                                        <th style="width: 5%">Article</th>
                                                                        <th style="width: 5%">Page</th>
                                                                        <th style="width: 10%">Description</th>
                                                                        <th style="width: 10%">Valid Until </th>
                                                                        <th style="width: 5%">Point</th>
                                                                        <th style="width: 10%">Document </th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>            
                                                                
                                                    </form>  
                                                    <!-- /.tab-pane -->
                                                </div><!-- /.tab-content -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                                                        <!-- Modal -->
<!--                                <div id="myModalLg" class="modal fade" role="dialog">
                                     <div class="modal-dialog modal-lg">

                                         Modal content

                                            <div class="modal-content">
                                            <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="addeditgeneral-title"></h4>
                                            </div>
                                            <form id="form-modal" class="form-horizontal">
                                             <input type="hidden" name="oid" id="oid">
                                             <input type="hidden" name="empId" id="empId">
                                             <input type="hidden" name="datafor" id="datafor">
                                             <input type="hidden" name="command" id="command">
                                            <div class="modal-body active-scroll" id="modalbody1">
                                            </div>
                                            <div class="modal-footer">
                                                 <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                                                 <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
                                            </div>
                                            </form>
                                            </div>
                                            </div>
                                </div>
                                <div id="myModalSm" class="modal fade" role="dialog">
                                     <div class="modal-dialog">

                                         Modal content

                                            <div class="modal-content">
                                            <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="addeditgeneral-title"></h4>
                                            </div>
                                            <form id="form-modal" class="form-horizontal">
                                             <input type="hidden" name="oid" id="oidSm">
                                             <input type="hidden" name="empId" id="empIdSm">
                                             <input type="hidden" name="datafor" id="dataforSm">
                                             <input type="hidden" name="command" id="commandSm">
                                             <input type="hidden" id="hiddenvalue">
                                             <input type="hidden" id="oidhiddenvalue">
                                            <div class="modal-body active-scroll" id="modalbody2">
                                            </div>
                                            <div class="modal-footer">
                                                 <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                                                 <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
                                            </div>
                                            </form>
                                            </div>
                                            </div>
                                </div>                                        
                                 <div id="myModal2" class="modal fade" role="dialog">
                                     <div class="modal-dialog">

                                         Modal content

                                            <div class="modal-content">
                                            <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="addeditgeneral-title"></h4>
                                            </div>
                                            <form id="form-modal">
                                             <input type="hidden" name="oid" id="oid">
                                             <input type="hidden" name="empId" id="empId">
                                             <input type="hidden" name="datafor" id="datafor">
                                             <input type="hidden" name="command" id="command">
                                            <div class="modal-body active-scroll" id="modalbody2">
                                                <div id="listEmployee">
                                                            </div>
                                            </div>
                                            <div class="modal-footer">
                                                 <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                                                 <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
                                            </div>
                                            </form>
                                            </div>
                                            </div>
                                </div>
                                <div id="modalUpload" class="modal fade" role="dialog">
                                     <div class="modal-dialog">

                                         Modal content

                                            <div class="modal-content">
                                            <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="addeditgeneral-title"></h4>
                                            </div>
                                            <form method="POST" action="employee_edit.jsp" id="formupload" enctype="multipart/form-data">
                                             <input type="hidden" name="oid" id="oid">
                                             <input type="hidden" name="empId" id="empId">
                                             <input type="hidden" name="datafor" id="datafor">
                                             <input type="hidden" name="command" id="command">
                                            <div class="modal-body active-scroll" id="modalbodyupload">
                                                
                                            </div>
                                            <div class="modal-footer">
                                                 <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                                                 <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
                                            </div>
                                            </form>
                                            </div>
                                            </div>
                                </div>
                                                                        
                                Sub Modal Training
                                <div class="modal fade" id="myModal0" data-focus-on="input:first" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                              <div class="modal-dialog">
                                                <div class="modal-content">
                                                  <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                    <h4 class="addeditgeneral-title2" id="myModalLabel2"></h4>
                                                  </div>
                                                  <form id="form-modal2">
                                                    <input type="hidden" name="oid" id="oid2">
                                                    <input type="hidden" name="datafor" id="datafor2">
                                                    <input type="hidden" name="command" id="command2">
                                                   <div class="modal-body" style="height: 500px;overflow-y: auto;">
                                                       <div class="row">
                                                           <div class="col-md-12">
                                                               <input class="form-control" placeholder="search position..." type="text" name="position_name" size="50" id="positionname"/>
                                                           </div>
                                                       </div>
                                                       <div class="row">
                                                           <div class="col-md-12" id="modalbody3">
                                                           </div>
                                                       </div>
                                                   </div>
                                                  </form>
                                                </div>
                                              </div>
                                 </div>
                                                                        
                                 <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">

                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="myModalLabel">Confirm Delete</h4>
                                            </div>

                                            <div class="modal-body">
                                                <strong>Are You Sure To Delete ?</strong>
                                                
                                            </div>

                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                                                <a class="btn btn-danger btn-ok">Yes</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>-->
                                
                                <div class="box-footer">
                                    <div class="row pull-right">
                                        <div class="col-md-12">
<!--                                            <strong><a href="javascript:cmdSave('<%=employee.getOID()%>')" class="btn btn-primary"><i class="fa fa-save"></i> Save Employee</a></strong>
                                            <strong><a href="<%= approot %>/employee/databank/employee_list.jsp" class="btn btn-primary"><i class="fa fa-backward"></i> Back to List Employee</a></strong>
                                            <strong><a href="javascript:cmdConfirmDelete('<%=employee.getOID()%>')" class="btn btn-danger"><i class="fa fa-trash-o"></i> Delete Employee</a></strong>-->
                                            <strong><a href="javascript:cmdPrintCV('<%=employee.getOID()%>')" class="btn btn-primary"><i class="fa fa-file"></i> Print CV</a></strong> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>   
                </section>
            </div><!-- /.content-wrapper -->
            <%@ include file="../../template/plugin.jsp" %>
            <%@ include file="../../template/footer.jsp" %>
        </div>
        
        <script>
        $('#confirm-delete').on('show.bs.modal', function(e) {
            $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
            
           // $('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');
        });
        </script>
        
        <script>
            $(function () {
                $(".comboDivision").select2({
                    placeholder: "Division"
                });
                $(".comboCompany").select2({
                    placeholder: "Company"
                });
                $(".comboDepartment").select2({
                    placeholder: "Department"
                });
                $(".comboSection").select2({
                    placeholder: "Section"
                });
                $(".comboPosition").select2({
                    placeholder: "Position"
                });
            });
            
            $('.datepicker').datepicker({
                format: 'yyyy-mm-dd'
            });
            
        </script> 
<!--        <script>
            function loadCompany(oid) {
                if (oid.length == 0) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "ajax/company.jsp?employee_id=" + oid, true);
                    xmlhttp.send();
                }
            }
            

            function loadDivision(str) {
                if (str.length == 0) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "ajax/company.jsp?company_id=" + str, true);
                    xmlhttp.send();
                }
            }

            function loadDepartment(comp_id, divisi_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "ajax/company.jsp?company_id="+comp_id+"&division_id=" + divisi_id, true);
                    xmlhttp.send();
                }
            }

            function loadSection(comp_id, divisi_id, depart_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)&&(depart_id.length == 0)) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "ajax/company.jsp?company_id="+comp_id+"&division_id=" + divisi_id +"&department_id="+depart_id, true);
                    xmlhttp.send();
                }
            }
            
            function loadWaCompany(oid) {
                if (oid.length == 0) { 
                    document.getElementById("listWa").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("listWa").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "ajax/company_wa.jsp?employee_id=" + oid, true);
                    xmlhttp.send();
                }
            }

            function loadWaDivision(str) {
                if (str.length == 0) { 
                    document.getElementById("listWa").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("listWa").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "ajax/company_wa.jsp?company_id=" + str, true);
                    xmlhttp.send();
                }
            }

            function loadWaDepartment(comp_id, divisi_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)) { 
                    document.getElementById("listWa").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("listWa").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "ajax/company_wa.jsp?company_id="+comp_id+"&division_id=" + divisi_id, true);
                    xmlhttp.send();
                }
            }

            function loadWaSection(comp_id, divisi_id, depart_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)&&(depart_id.length == 0)) { 
                    document.getElementById("listWa").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("listWa").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "ajax/company_wa.jsp?company_id="+comp_id+"&division_id=" + divisi_id +"&department_id="+depart_id, true);
                    xmlhttp.send();
                }
            }
            </script>-->
            <script language="JavaScript">
            function cmdSave(){
                document.frm_employee.command.value="<%=Command.SAVE%>";
                document.frm_employee.action="employee_edit.jsp";
                
                document.frm_employee.submit();
            }
            function cmdSaveSetting(){
                document.frm_employee.command.value="99";
                document.frm_employee.action="view_databank.jsp";
                document.frm_employee.submit();
            }
            function cmdConfirmDelete(oid){
                var r = confirm("Are you sure to delete data ?");
                if (r == true) {
                document.frm_employee.command.value="<%=Command.DELETE%>";
                document.frm_employee.action="employee_edit.jsp";
                document.frm_employee.submit();
            }
            }
            function cmdPrintCV(){
                window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeDetailPdf?oid=<%=oidEmployee%>");
            }
            
            
            </script>
            <script type="text/javascript">
    $(document).ready(function () {
        
        $("#hide_basic_b").click(function(){
            $("#show_basic").addClass('hidden');
            $("#hide_basic").addClass('show');
            $("#hide_basic").removeClass('hidden');
        });

        $("#show_basic_b").click(function(){
            $("#show_basic").addClass('show');
            $("#hide_basic").addClass('hidden');
            $("#show_basic").removeClass('hidden');
        });
        $("#hide_IQ_b").click(function(){
            $("#show_IQ").addClass('hidden');
            $("#hide_IQ").addClass('show');
            $("#hide_IQ").removeClass('hidden');
        });

        $("#show_IQ_b").click(function(){
            $("#show_IQ").addClass('show');
            $("#hide_IQ").addClass('hidden');
            $("#show_IQ").removeClass('hidden');
        });

        $("#hide_bank_b").click(function(){
            $("#show_bank").addClass('hidden');
            $("#hide_bank").addClass('show');
            $("#hide_bank").removeClass('hidden');
        });

        $("#show_bank_b").click(function(){
            $("#show_bank").addClass('show');
            $("#hide_bank").addClass('hidden');
            $("#show_bank").removeClass('hidden');
        });

        $("#hide_parent_b").click(function(){
            $("#show_parent").addClass('hidden');
            $("#hide_parent").addClass('show');
            $("#hide_parent").removeClass('hidden');
        });

        $("#show_parent_b").click(function(){
            $("#show_parent").addClass('show');
            $("#hide_parent").addClass('hidden');
            $("#show_parent").removeClass('hidden');
        });

        $("#hide_schedule_b").click(function(){
            $("#show_schedule").addClass('hidden');
            $("#hide_schedule").addClass('show');
            $("#hide_schedule").removeClass('hidden');
        });

        $("#show_schedule_b").click(function(){
            $("#show_schedule").addClass('show');
            $("#hide_schedule").addClass('hidden');
            $("#show_schedule").removeClass('hidden');
        });

        $("#hide_comp_b").click(function(){
            $("#show_comp").addClass('hidden');
            $("#hide_comp").addClass('show');
            $("#hide_comp").removeClass('hidden');
        });

        $("#show_comp_b").click(function(){
            $("#show_comp").addClass('show');
            $("#hide_comp").addClass('hidden');
            $("#show_comp").removeClass('hidden');
        });

        $("#hide_wa_b").click(function(){
            $("#show_wa").addClass('hidden');
            $("#hide_wa").addClass('show');
            $("#hide_wa").removeClass('hidden');
        });

        $("#show_wa_b").click(function(){
            $("#show_wa").addClass('show');
            $("#hide_wa").addClass('hidden');
            $("#show_wa").removeClass('hidden');
        });

        $("#hide_career_b").click(function(){
            $("#show_career").addClass('hidden');
            $("#hide_career").addClass('show');
            $("#hide_career").removeClass('hidden');
        });

        $("#show_career_b").click(function(){
            $("#show_career").addClass('show');
            $("#hide_career").addClass('hidden');
            $("#show_career").removeClass('hidden');
        });

        $("#hide_other_b").click(function(){
            $("#show_other").addClass('hidden');
            $("#hide_other").addClass('show');
            $("#hide_other").removeClass('hidden');
        });

        $("#show_other_b").click(function(){
            $("#show_other").addClass('show');
            $("#hide_other").addClass('hidden');
            $("#show_other").removeClass('hidden');
        });
        
        //SET ACTIVE MENU
        var activeMenu = function (parentId, childId) {
            $(parentId).addClass("active").find(".treeview-menu").slideDown();
            $(childId).addClass("active");
        }
        $('[data-toggle="tooltip"]').tooltip();

        activeMenu("#masterdata", "#empcompetency");
        $("#division").chained("#company");
        $("#department").chained("#division");
        $("#section").chained("#department");
        $("#divisionWa").chained("#companyWa");
        $("#departmentWa").chained("#divisionWa");
        $("#sectionWa").chained("#departmentWa");

        var approot = $("#approot").val();
        var command = $("#command").val();
        var dataSend = null;

        var oid = null;
        var dataFor = null;
        var empId = null;
        var userName = null;
        var userId = null;

        //FUNCTION VARIABLE
        var onDone = null;
        var onSuccess = null;
        var callBackDataTables = null;
        var iCheckBox = null;
        var addeditgeneral = null;
        var areaTypeForm = null;
        var deletegeneral = null;
        var deletesingle = null;
        
        var servletName = null;

        //MODAL SETTING
        var modalSetting = function (elementId, backdrop, keyboard, show) {
            $(elementId).modal({
                backdrop: backdrop,
                keyboard: keyboard,
                show: show
            });
        };

        function datePicker(contentId, formatDate) {
            $(contentId).datepicker({
                format: formatDate
            });
            $(contentId).on('changeDate', function (ev) {
                $(this).datepicker('hide');
            });
        }
        
        function addEditGeo(selector){
            $(selector).click(function(){
               var oid = $(this).data("oid");
               var empId = '<%=employee.getOID()%>';
               var dataFor = $(this).data("for");
               var command = $(this).data("command");

               $("#oidSm").val(oid);
               $("#empIdSm").val(empId);
               $("#dataforSm").val(dataFor);
               $("#commandSm").val("4");

               if(oid != 0){
                if(dataFor == 'showgeoform'){
                    $(".addeditgeneral-title").html("Select Geo");
                }

                }else{
                    if(dataFor == 'showgeoform'){
                        $(".addeditgeneral-title").html("Select Geo");
                    }
                }

               var dataSend1 = {
                   "oid" : oid,
                   "empId" : empId,
                   "datafor" : dataFor,
                   "command" : command
               }
               var onDone1 = function(data){
                   $("#modalbody2").html(data).fadeIn("medium");
                   setHiddenValue(".controlcombo");
                   $("#propinsi").chained("#negara");
                   $("#kabupaten").chained("#propinsi");
                   $("#kecamatan").chained("#kabupaten");
                }

               var onSuccess1 = function(data){

               };

               $("#myModalSm").modal("show");
               $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");

               sendData("<%= approot %>/employee/databank/ajax/geo_addres.jsp", dataSend1, onDone1, onSuccess1);
            });
        }

        $("#geo_address").focusin(function(){
            addEditGeo(".addeditgeo");
        });

        $("#geo_address_pmnt").focusin(function(){
            addEditGeo(".addeditgeo");
        });


        function setHiddenValue(selector){
            //OID
            $(selector).change(function(){
                var oidNegara = $("#negara").val();
                var oidProvinsi = $("#propinsi").val();
                var oidKabupaten = $("#kabupaten").val();
                var oidKecamatan = $("#kecamatan").val();
                var dataFor = $("#dataforSm").val();

                if(dataFor == "geoaddresspmnt"){
                    $("#oidnegarapmnt").val(oidNegara);
                    $("#oidprovinsipmnt").val(oidProvinsi);
                    $("#oidkabupatenpmnt").val(oidKabupaten);
                    $("#oidkecamatanpmnt").val(oidKecamatan);
                }else{
                    $("#oidnegara").val(oidNegara);
                    $("#oidprovinsi").val(oidProvinsi);
                    $("#oidkabupaten").val(oidKabupaten);
                    $("#oidkecamatan").val(oidKecamatan);                            
                }


                var nameNegara = "-";
                var nameProvinsi = "-";
                var nameKabupaten = "-";
                var nameKecamatan = "-";

                if(oidNegara != 0){
                    nameNegara = $("#negara option:selected").text();
                }

                if(oidProvinsi != 0){
                    nameProvinsi = $("#propinsi option:selected").text();
                }

                if(oidKabupaten != 0){
                    nameKabupaten = $("#kabupaten option:selected").text();
                }

                if(oidKecamatan != 0){
                    nameKecamatan = $("#kecamatan option:selected").text();
                }

                var stringValue = ""+nameNegara+","+nameProvinsi+","+nameKabupaten+","+nameKecamatan;
                $("#hiddenvalue").val(stringValue);
                $("#hiddenvalue")
            });

        };

        $("form#form-modal").submit(function(){
            var dataFor = $("#dataforSm").val();
            if(dataFor == "geoaddresspmnt"){
                $("#geo_address_pmnt").val($("#hiddenvalue").val());
            }else{
                $("#geo_address").val($("#hiddenvalue").val());
            }
            $("#myModalSm").modal("hide");
            return false;
        })
        
        function sendData(url, datasend, onDone, onSuccess){
        //alert("WORK");
         $.ajax({
             type    : "GET",
             data    : datasend,
             url     : url,
             success : function(data){
                 onSuccess(data);
             },
             error: function(xhr,err){
                 alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
                 alert("responseText: "+xhr.responseText);
             }
         }).done(function(data){
             onDone(data);
         });

     }

        var getDataFunction = function (onDone, onSuccess, approot, command, dataSend, servletName, dataAppendTo, notification, dataType) {
            /*
             * getDataFor	: # FOR PROCCESS FILTER
             * onDone	: # ON DONE FUNCTION,
             * onSuccess	: # ON ON SUCCESS FUNCTION,
             * approot	: # APPLICATION ROOT,
             * dataSend	: # DATA TO SEND TO THE SERVLET,
             * servletName  : # SERVLET'S NAME,
             * dataType	: # Data Type (JSON, HTML, TEXT)
             */
            $(this).getData({
                onDone: function (data) {
                    onDone(data);
                },
                onSuccess: function (data) {
                    onSuccess(data);
                },
                approot: approot,
                dataSend: dataSend,
                servletName: servletName,
                dataAppendTo: dataAppendTo,
                notification: notification,
                ajaxDataType: dataType
            });
        }

        //SHOW ADD OR EDIT FORM
        addeditgeneral = function (elementId) {
            $(elementId).click(function () {
                $("#addeditgeneral").modal("show");
                command = $("#command").val();
                oid = $(this).data('oid');
                dataFor = $(this).data('for');
                empId = "<%=employee.getOID()%>";
                userName = "<%=emplx.getFullName()%>";
                userId = <%=appUserIdSess%>;
                $("#generaldatafor").val(dataFor);
                $("#oid").val(oid);
                $("#empId").val(empId);
                
                //SET TITLE MODAL
                if (oid != 0) {
                    if (dataFor == 'showempcompetencyform') {
                        $(".addeditgeneral-title").html("Edit Employee Competency");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showemplanguageform') {
                        $(".addeditgeneral-title").html("Edit Employee Language");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showempexperienceform') {
                        $(".addeditgeneral-title").html("Edit Employee Experience");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showempeducationform') {
                        $(".addeditgeneral-title").html("Edit Employee Education");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showfamilymemberform') {
                        $(".addeditgeneral-title").html("Edit Family Member");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showtraininghistoryform') {
                        $(".addeditgeneral-title").html("Edit Training History");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showEmpMutationForm') {
                        $(".addeditgeneral-title").html("Edit Employee Contract");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showEmpRelevantDocForm') {
                        $(".addeditgeneral-title").html("Edit Employee Relevant Document");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showEmpAwardForm') {
                        $(".addeditgeneral-title").html("Edit Employee Award");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showempwarningform') {
                        $(".addeditgeneral-title").html("Edit Warning");
                        $(".modal-dialog").css("width", "85%");
                    }
                    if (dataFor == 'showEmpReprimandForm') {
                        $(".addeditgeneral-title").html("Edit Reprimand");
                        $(".modal-dialog").css("width", "85%");
                    }
                    if (dataFor == 'contract_type_form'){
                        $(".addeditgeneral-title").html("Edit Contract Type");
                        $(".modal-dialog").css("width", "50%");
                    }
                    

                } else {
                    if (dataFor == 'showempcompetencyform') {
                        $(".addeditgeneral-title").html("Add Employee Competency");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showemplanguageform') {
                        $(".addeditgeneral-title").html("Add Employee Language");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showempexperienceform') {
                        $(".addeditgeneral-title").html("Add Employee Experience");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showempeducationform') {
                        $(".addeditgeneral-title").html("Add Employee Education");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showfamilymemberform') {
                        $(".addeditgeneral-title").html("Add Family Member");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showtraininghistoryform') {
                        $(".addeditgeneral-title").html("Add Training History");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showEmpMutationForm') {
                        $(".addeditgeneral-title").html("Add Employee Contract");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showEmpRelevantDocForm') {
                        $(".addeditgeneral-title").html("Add Employee Relevant Document");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showEmpAwardForm') {
                        $(".addeditgeneral-title").html("Add Employee Award");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'showempwarningform') {
                        $(".addeditgeneral-title").html("Add Warning");
                        $(".modal-dialog").css("width", "75%");
                    }
                    if (dataFor == 'showEmpReprimandForm') {
                        $(".addeditgeneral-title").html("Add Reprimand");
                        $(".modal-dialog").css("width", "85%");
                    }
                    if (dataFor == 'contract_type_form'){
                        $(".addeditgeneral-title").html("Add Contract Type");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'do_resign_form'){
                        $(".addeditgeneral-title").html("Do Resign");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'career_form'){
                        $(".addeditgeneral-title").html("Add Career Path");
                        $(".modal-dialog").css("width", "50%");
                    }
                    if (dataFor == 'do_mutation_form'){
                        $(".addeditgeneral-title").html("Do Mutation");
                        $(".modal-dialog").css("width", "50%");
                    }
                }
                if (dataFor == 'generateDocAward'){
                    $(".addeditgeneral-title").html("Award Document");
                    $(".modal-dialog").css("width", "85%");
                }
                //Set Servlet
                if (dataFor == 'showempcompetencyform') {
                    servletName = "AjaxEmpCompetency";
                }
                if (dataFor == 'showemplanguageform') {
                    servletName = "AjaxEmpLanguage";
                }
                if (dataFor == 'showempexperienceform') {
                    servletName = "AjaxEmpExperience";
                }
                if (dataFor == 'showempeducationform') {
                    servletName = "AjaxEmpEducation";
                }
                if (dataFor == 'showfamilymemberform') {
                    servletName = "AjaxFamilyMember";
                }
                if (dataFor == 'showtraininghistoryform') {
                    servletName = "AjaxTrainingHistory";
                }
                if (dataFor == 'showEmpMutationForm') {
                    servletName = "AjaxEmpCareerPath";
                }
                if (dataFor == 'showEmpRelevantDocForm') {
                    servletName = "AjaxEmpRelevantDoc";
                }
                if (dataFor == 'showEmpAwardForm') {
                    servletName = "AjaxEmpAward";
                }
                if (dataFor == 'showempwarningform') {
                    servletName = "AjaxEmpWarning";
                }
                if (dataFor == 'showEmpReprimandForm') {
                   servletName = "AjaxEmpReprimand";
                }
                if (dataFor == 'contract_type_form'){
                    servletName = "ContractTypeAjax";
                }
                if (dataFor == 'do_resign_form'){
                    servletName = "CareerAjax";
                }
                if (dataFor == 'career_form'){
                    servletName = "CareerAjax";
                }
                if (dataFor == 'do_mutation_form'){
                    servletName = "CareerAjax";
                }

                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "FRM_FIELD_OID": oid,
                    "command": command,
                    "empId" : empId,
                    "userName" : userName,
                    "userId" : userId
                }
                onDone = function (data) {
                    $('#internal').click(function()
                    {
                      $('.internal').removeAttr("disabled");
                      $('.external').attr("disabled","disabled")
                    });
                    $('#external').click(function()
                    {
                      $('.external').removeAttr("disabled");
                      $('.internal').attr("disabled","disabled")
                    });
                    datePicker(".datepicker", "yyyy-mm-dd");
                    
                    $('.datetimepicker').datetimepicker({
                        format : 'LT',
                        locale: 'ru'
                    });
                    $(".colorpicker").colorpicker();
                    loadDivision(".datachange");
                    if (dataFor == 'career_form' || dataFor == 'do_mutation_form'){
                        var mutationId = "0";
                        $(".company_struct").change(function(){
                            mutationId = $(this).val();
                            var companyId = $(this).val();
                            dataFor = $(this).data("for");
                            var target = $(this).data("replacement");
                            dataSend = {
                                "FRM_FIELD_DATA_FOR": dataFor,
                                "FRM_FIELD_OID": oid,
                                "command": command,
                                "empId" : empId,
                                "userName" : userName,
                                "userId" : userId,
                                "company_id" : companyId,
                                "mutation_id" : mutationId
                            }
                            getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, target, false, "json");
                        });
                    }
                };
                onSuccess = function (data) {

                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, ".addeditgeneral-body", false, "json");
            });
        };

        //DELETE GENERAL
        deletegeneral = function (elementId, checkboxelement) {

            $(elementId).unbind().click(function () {
                dataFor = $(this).data("for");
                var checkBoxes = (checkboxelement);
                var vals = "";
                $(checkboxelement).each(function (i) {

                    if ($(this).is(":checked")) {
                        if (vals.length == 0) {
                            vals += "" + $(this).val();
                        } else {
                            vals += "," + $(this).val();
                        }
                    }
                });

                var confirmText = "Are you sure want to delete these data?";
                if (vals.length == 0) {
                    alert("Please select the data");
                } else {
                    command = <%= Command.DELETEALL%>;
                    var currentHtml = $(this).html();
                    $(this).html("Deleting...").attr({"disabled": true});
                    if (confirm(confirmText)) {
                        dataSend = {
                            "FRM_FIELD_DATA_FOR": dataFor,
                            "FRM_FIELD_OID_DELETE": vals,
                            "command": command
                        };
                        onSuccess = function (data) {

                        };
                        if (dataFor == "empcompetency") {
                            servletName = "AjaxEmpCompetency";
                            onDone = function (data) {
                                runDataTables("showempcompetencyform");
                            };
                        }
                        if (dataFor == "emplanguage") {
                            servletName = "AjaxEmpLanguage";
                            onDone = function (data) {
                                runDataTables("showemplanguageform");
                            };
                        }
                        if (dataFor == "empexperience") {
                            servletName = "AjaxEmpExperience";
                            onDone = function (data) {
                                runDataTables("showempexperienceform");
                            };
                        }
                        if (dataFor == "empeducation") {
                            servletName = "AjaxEmpEducation";
                            onDone = function (data) {
                                runDataTables("showempeducationform");
                            };
                        }
                        if (dataFor == "familymember") {
                            servletName = "AjaxFamilyMember";
                            onDone = function (data) {
                                runDataTables("showfamilymemberform");
                            };
                        }
                        if (dataFor == "traininghistory") {
                            servletName = "AjaxTrainingHistory";
                            onDone = function (data) {
                                runDataTables("showtraininghistoryform");
                            };
                        }
                        if (dataFor == "showEmpMutationForm") {
                            servletName = "AjaxEmpCareerPath";
                            onDone = function (data) {
                                runDataTables("showEmpCareerPathForm");
                            };
                        }
                        if (dataFor == "empRelevantDoc") {
                            servletName = "AjaxEmpRelevantDoc";
                            onDone = function (data) {
                                runDataTables("showEmpRelevantDocForm");
                            };
                        }
                        if (dataFor == "empAward") {
                            servletName = "AjaxEmpAward";
                            onDone = function (data) {
                                runDataTables("showEmpAwardForm");
                            };
                        }
                        if (dataFor == "empwarning") {
                            servletName = "AjaxEmpWarning";
                            onDone = function (data) {
                                runDataTables("showempwarningform");
                            };
                        }
                        if (dataFor == "empReprimand") {
                            servletName = "AjaxEmpReprimand";
                            onDone = function (data) {
                                runDataTables("showEmpReprimandForm");
                            };
                        }
                        if (dataFor == "career_form") {
                            servletName = "CareerAjax";
                            onDone = function (data) {
                                runDataTables("career_form");
                            };
                        }
                        if (dataFor == "contract_type_form") {
                            servletName = "ContractTypeAjax";
                            onDone = function (data) {
                                runDataTables("contract_type_form");
                            };
                        }
                        
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, null, true, "json");
                        $(this).removeAttr("disabled").html(currentHtml);
                    } else {
                        $(this).removeAttr("disabled").html(currentHtml);
                    }
                }

            });
        };
        
        //DELETE SINGLE
        deletesingle = function (elementId) {

            $(elementId).unbind().click(function () {
                dataFor = $(this).data("for");
                var vals = $(this).data("oid");
                var confirmTextSingle = "Are you sure want to delete these data?";
                
                command = <%= Command.DELETE%>;
                var currentHtml = $(this).html();
                $(this).html("Deleting...").attr({"disabled": true});
                if (confirm(confirmTextSingle)) {
                    dataSend = {
                        "FRM_FIELD_DATA_FOR": dataFor,
                        "FRM_FIELD_OID": vals,
                        "command": command
                    };
                    onSuccess = function (data) {

                    };
                    if (dataFor == "deleteEmpCompetencySingle") {
                        servletName = "AjaxEmpCompetency";
                        onDone = function (data) {
                            runDataTables("showempcompetencyform");
                        };
                    }
                    if (dataFor == "deleteEmpLangSingle") {
                        servletName = "AjaxEmpLanguage";
                        onDone = function (data) {
                            runDataTables("showemplanguageform");
                        };
                    }
                    if (dataFor == "deleteEmpExperienceSingle") {
                        servletName = "AjaxEmpExperience";
                        onDone = function (data) {
                            runDataTables("showempexperienceform");
                        };
                    }
                    if (dataFor == "deleteEmpEducationSingle") {
                        servletName = "AjaxEmpEducation";
                        onDone = function (data) {
                            runDataTables("showempeducationform");
                        };
                    }
                    if (dataFor == "deleteFamMemberSingle") {
                        servletName = "AjaxFamilyMember";
                        onDone = function (data) {
                            runDataTables("showfamilymemberform");
                        };
                    }
                    if (dataFor == "deleteTrainHistSingle") {
                        servletName = "AjaxTrainingHistory";
                        onDone = function (data) {
                            runDataTables("showtraininghistoryform");
                        };
                    }
                    if (dataFor == "showEmpMutationForm") {
                        servletName = "AjaxEmpCareerPath";
                        onDone = function (data) {
                            runDataTables("showEmpCareerPathForm");
                        };
                    }
                    if (dataFor == "deleteEmpRlvtDocSingle") {
                        servletName = "AjaxEmpRelevantDoc";
                        onDone = function (data) {
                            runDataTables("showEmpRelevantDocForm");
                        };
                    }
                    if (dataFor == "deleteAwardSingle") {
                        servletName = "AjaxEmpAward";
                        onDone = function (data) {
                            runDataTables("showEmpAwardForm");
                        };
                    }
                    if (dataFor == "deleteEmpWarningSingle") {
                        servletName = "AjaxEmpWarning";
                        onDone = function (data) {
                            runDataTables("showempwarningform");
                        };
                    }
                    if (dataFor == "deleteEmpRepSingle") {
                        servletName = "AjaxEmpReprimand";
                        onDone = function (data) {
                            runDataTables("showEmpReprimandForm");
                        };
                    }
                    if (dataFor == "career_form") {
                        servletName = "CareerAjax";
                        onDone = function (data) {
                            runDataTables("career_form");
                        };
                    }
                    if (dataFor == "contract_type_form") {
                        servletName = "ContractTypeAjax";
                        onDone = function (data) {
                            runDataTables("contract_type_form");
                        };
                    }

                    getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, null, true, "json");
                    $(this).removeAttr("disabled").html(currentHtml);
                } else {
                        $(this).removeAttr("disabled").html(currentHtml);
                    }
            });
        };
        
        function viewHistory(selector){
            $(selector).click(function(){
               var oid = $(this).data("oid");
               var dataFor = $(this).data("for");
               var command = $(this).data("command");

               $("#oid").val(oid);
               $("#datafor").val(dataFor);
               $("#command").val(command);
               $(".addeditgeneral-title").html("ID Card History");
               var dataSend1 = {
                   "oid" : oid,
                   "datafor" : dataFor,
                   "command" : command
               }
               var onDone1 = function(data){
                   $("#modalbody2").html(data).fadeIn("medium");
               }

               var onSuccess1 = function(data){

               };

               $("#myModalSm").modal("show");
               $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");

               sendData("<%= approot %>/employee/databank/ajax/idcard.jsp", dataSend1, onDone1, onSuccess1);
            });
        }
        viewHistory(".viewhistory");
        
        
        var loadDivision = function(selector){
            $(selector).change(function(){
               var datachange = $(this).val();
               dataFor = $(this).data("for");
               command = $("#command").val();
               var target = $(this).data("target");
               
               dataSend = {
                   "datachange" : datachange,
                   "FRM_FIELD_DATA_FOR" : dataFor,
                   "command" : command
               }
               
               onSuccess = function(data){
                   
               };
               
               onDone = function(data){
                   
               };
               
               if (dataFor == 'showempwarningform') {
                    servletName = "AjaxEmpWarning";
                }
               if (dataFor == 'showEmpReprimandForm') {
                    servletName = "AjaxEmpReprimand";
                } 
               
               getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, target, false, "json");
               
            });
        
        };

        //FUNCTION FOR DATA TABLES
        callBackDataTables = function () {
            addeditgeneral(".btneditgeneral");
            upload(".btnupload");
            deletesingle(".btndeletesingle");
            iCheckBox();
        }

        //FORM HANDLER
        empCompetencyForm = function () {
            validateOptions("#<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_COMPETENCY_ID]%>", 'text', 'has-error', 1, null);
        }
        empLanguageForm = function () {
            validateOptions("#<%= FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID]%>", 'text', 'has-error', 1, null);
        }
        empExperienceForm = function () {
            validateOptions("#<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_COMPANY_NAME]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_START_DATE]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_END_DATE]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_MOVE_REASON]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_POSITION]%>", 'text', 'has-error', 1, null);
        }
        empEducationForm = function () {
            validateOptions("#<%= FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_ID]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_START_DATE]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_END_DATE]%>", 'text', 'has-error', 1, null);
        }
        familyMemberForm = function () {
            validateOptions("#<%= FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELATIONSHIP]%>", 'text', 'has-error', 1, null);            
        }
        trainingHistoryForm = function () {
            validateOptions("#<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_ID]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_DATE]%>", 'text', 'has-error', 1, null); 
            validateOptions("#<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_DATE]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_TITLE]%>", 'text', 'has-error', 1, null);             
        }
        empAwardForm = function () {
            validateOptions("#<%= FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_TYPE]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_TITLE]%>", 'text', 'has-error', 1, null); 
        }
        relevantDocumentForm = function () {
            validateOptions("#<%= FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_EMP_RELVT_DOC_GRP_ID]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_TITLE]%>", 'text', 'has-error', 1, null); 
        }
        empWarningForm = function (){
            validateOptions("#<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_COMPANY_ID]%>", 'text', 'has-error', 1, null);
            validateOptions("#division", 'text', 'has-error', 1, null);
            validateOptions("#department", 'text', 'has-error', 1, null);
            validateOptions("#section", 'text', 'has-error', 1, null);
            validateOptions("#position", 'text', 'has-error', 1, null);
            validateOptions("#level", 'text', 'has-error', 1, null);
            validateOptions("#emp_category", 'text', 'has-error', 1, null);
            validateOptions("#emp_category", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_BREAK_DATE]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_BREAK_FACT]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_DATE]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_LEVEL_ID]%>", 'text', 'has-error', 1, null);
            //validateOptions("#<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_BY]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_VALID_DATE]%>", 'text', 'has-error', 1, null);
        }
        empReprimandForm = function (){
            validateOptions("#<%= FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_COMPANY_ID]%>", 'text', 'has-error', 1, null);
            validateOptions("#division", 'text', 'has-error', 1, null);
            validateOptions("#department", 'text', 'has-error', 1, null);
            validateOptions("#section", 'text', 'has-error', 1, null);
            validateOptions("#position", 'text', 'has-error', 1, null);
            validateOptions("#level", 'text', 'has-error', 1, null);
            validateOptions("#emp_category", 'text', 'has-error', 1, null);
            validateOptions("#emp_category", 'text', 'has-error', 1, null);
            //validateOptions("#<%= FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_LEVEL_ID]%>", 'text', 'has-error', 1, null);
        }
        contractTypeValidation = function () {
            validateOptions("#field_contract_type", 'text', 'has-error', 1, null);
        }

        //VALIDATE FORM
        function validateOptions(elementId, checkType, classError, minLength, matchValue) {

            /* OPTIONS
             * minLength    : INT VALUE,
             * matchValue   : STRING OR INT VALUE,
             * classError   : STRING VALUE,
             * checkType    : STRING VALUE ('text' for default, 'email' for email check
             */

            $(elementId).validate({
                minLength: minLength,
                matchValue: matchValue,
                classError: classError,
                checkType: checkType
            });
        }

        //iCheck
        iCheckBox = function () {
            $("input[type='checkbox'], input[type='radio']").iCheck({
                checkboxClass: 'icheckbox_flat-red',
                radioClass: 'icheckbox_square-blue'
            });


        }
        
        //UPLOAD
        upload = function(elementId){
            $(elementId).click(function(){
                $("#btnupload").html("Save").removeAttr("disabled");
                $(".modal-dialog").css("width", "50%");
                $("#uploaddoc").modal("show");
                
                oid = $(this).data('oid');
                dataFor = $(this).data('for');
                if (dataFor == "showEmpRelevantDocForm") {
                    $("#formupload").attr("action","<%=approot%>/employee/databank/upload.jsp?FRM_FIELD_OID="+oid+"&empId=<%=employee.getOID()%>&modul=reldoc&approot=<%=approot%>");
                }
                if (dataFor == "showEmpAwardForm") {
                    $("#formupload").attr("action","<%=approot%>/employee/databank/upload.jsp?FRM_FIELD_OID="+oid+"&empId=<%=employee.getOID()%>&modul=award&approot=<%=approot%>");
                }
                if (dataFor == "showEmpReprimandForm") {
                    $("#formupload").attr("action","<%=approot%>/employee/databank/upload.jsp?FRM_FIELD_OID="+oid+"&empId=<%=employee.getOID()%>&modul=reprimand&approot=<%=approot%>");
                }
                $("#tempname").val("");
                $("#generaldatafor").val(dataFor);
                $("#oiddata").val(oid);
            });
        };

        //UPLOAD

        function uploadTrigger(){
            $("#uploadtrigger").click(function(){
                $("#FRM_DOC").trigger('click');
            });

            $("#FRM_DOC").change(function(){
                var doc = $("#FRM_DOC").val();
                $("#tempname").val(doc);
            });

            $("#tempname").click(function(){
                $("#FRM_DOC").trigger('click');
            });
        }


        //DATA TABLES SETTING
        function dataTablesOptions(elementIdParent, elementId, servletName, dataFor, callBackDataTables) {
            var datafilter = $("#datafilter").val();
            var privUpdate = "false";
            var approot = "<%=approot%>";
            var aTargets = null;
            if (privUpdate == "true"){
                aTargets = "[0, -1]";
            } else {
                aTargets = "[1]";
            }
            empId = "<%=employee.getOID()%>";
            $(elementIdParent).find('table').addClass('table-bordered table-hover').attr({'id': elementId});
            $("#" + elementId).dataTable({"bDestroy": true,
                "iDisplayLength": 10,
                "bProcessing": true,
                "oLanguage": {
                    "sProcessing": "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
                },
                "bServerSide": true,
                "sAjaxSource": "<%= approot%>/" + servletName + "?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER=" + datafilter + "&FRM_FIELD_DATA_FOR=" + dataFor + "&privUpdate=" + privUpdate + "&empId=" + empId +"&FRM_FIELD_APPROOT=" +approot,
                aoColumnDefs: [
                    {
                        bSortable: false,
                        aTargets: aTargets
                    }
                ],
                "initComplete": function (settings, json) {
                    if (callBackDataTables != null) {
                        callBackDataTables();
                    }
                },
                "fnDrawCallback": function (oSettings) {
                    if (callBackDataTables != null) {
                        callBackDataTables();
                    }
                },
                "fnPageChange": function (oSettings) {

                }
            });

            $(elementIdParent).find("#" + elementId + "_filter").find("input").addClass("form-control");
            $(elementIdParent).find("#" + elementId + "_length").find("select").addClass("form-control");
            $("#" + elementId).css("width", "100%");
        }

        function runDataTables(dataFor) {
            var elementIdParent = null;
            var elementId = null;
            servletName = null;
            var listData = null;
            if (dataFor == "showempcompetencyform"){
                elementIdParent = "#empCompetencyElement";
                servletName = "AjaxEmpCompetency";
                listData = "listempcompetency";
                elementId = "tableempCompetencyElement";
            }
            if (dataFor == "showemplanguageform" ){
                elementIdParent = "#empLanguageElement";
                servletName = "AjaxEmpLanguage";
                listData = "listemplanguage";
                elementId = "tableempLanguageElement";
            }
            if (dataFor == "showempexperienceform" ){
                elementIdParent = "#empExperienceElement";
                servletName = "AjaxEmpExperience";
                listData = "listempexperience";
                elementId = "tableempExperienceElement";
            }
            if (dataFor == "showempeducationform" ){
                elementIdParent = "#empEducationElement";
                servletName = "AjaxEmpEducation";
                listData = "listempeducation";
                elementId = "tableempEducationElement";
            }
            if (dataFor == "showfamilymemberform" ){
                elementIdParent = "#familyMemberElement";
                servletName = "AjaxFamilyMember";
                listData = "listfamilymember";
                elementId = "tablefamilyMemberElement";
            }
            if (dataFor == "showtraininghistoryform" ){
                elementIdParent = "#trainingHistoryElement";
                servletName = "AjaxTrainingHistory";
                listData = "listtraining";
                elementId = "tabletrainingHistoryElement";
            }
            if (dataFor == "showEmpCareerPathForm" ){
                elementIdParent = "#empCareerPathElement";
                servletName = "AjaxEmpCareerPath";
                listData = "listEmpCareerPath";
                elementId = "tableEmpCareerPathElement";
            }
            if (dataFor == "showEmpRelevantDocForm" ){
                elementIdParent = "#empRelevantDocElement";
                servletName = "AjaxEmpRelevantDoc";
                listData = "listEmpRelevantDoc";
                elementId = "tableEmpRelevantDocElement";
            }
            if (dataFor == "showEmpAwardForm" ){
                elementIdParent = "#empAwardElement";
                servletName = "AjaxEmpAward";
                listData = "listEmpAward";
                elementId = "tableEmpAwardElement";
            }
            if (dataFor == "showempwarningform" ){
                elementIdParent = "#empWarningElement";
                servletName = "AjaxEmpWarning";
                listData = "listempwarning";
                elementId = "tableempwarningElement";
            }
            if (dataFor == "showEmpReprimandForm" ){
                elementIdParent = "#empReprimandElement";
                servletName = "AjaxEmpReprimand";
                listData = "listEmpReprimand";
                elementId = "tableEmpReprimandElement";
            }
            if (dataFor == "career_form"){
                elementIdParent = "#career_element_parent";
                servletName = "CareerAjax";
                listData = "career_list";
                elementId = "career_element";
            }
            if (dataFor == "contract_type_form"){
                elementIdParent = "#contract_element_parent";
                servletName = "ContractTypeAjax";
                listData = "contract_list";
                elementId = "contract_element";
            }
            if (dataFor == "do_contract_form"){
                elementIdParent = "#career_element_parent";
                servletName = "CareerAjax";
                listData = "career_list";
                elementId = "career_element";
            }
            if (dataFor == "do_mutation_form"){
                elementIdParent = "#career_element_parent";
                servletName = "CareerAjax";
                listData = "career_list";
                elementId = "career_element";
            }
            dataTablesOptions(elementIdParent, elementId, servletName, listData, callBackDataTables);
        }

        modalSetting("#addeditgeneral", "static", false, false);
        addeditgeneral(".btnaddgeneral");
        
        
        $('.tabCom').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showempcompetencyform");
            deletegeneral(".btndeleteempcompetency", ".<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_COMP_ID]%>");
        });
        
        $('.tabLang').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showemplanguageform");
            deletegeneral(".btndeleteemplanguage", ".<%= FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_EMP_LANGUAGE_ID]%>");
        });
        $('.tabExp').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showempexperienceform");
            deletegeneral(".btndeleteempexperience", ".<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_WORK_HISTORY_PAST_ID]%>");
        });
        $('.tabEdu').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showempeducationform");
            deletegeneral(".btndeleteempeducation", ".<%= FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMP_EDUCATION_ID]%>");
        });
        $('.tabFam').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showfamilymemberform");
            deletegeneral(".btndeletefamilymember", ".<%= FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FAMILY_MEMBER_ID]%>");
        });
        $('.tabTraining').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showtraininghistoryform");
            deletegeneral(".btndeletetraininghistory", ".<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_HISTORY_ID]%>");
        });
//        $('.tabCareer').click(function() {
//            runDataTables("showEmpCareerPathForm");
//            deletegeneral(".btndeleteEmpCareerPath", ".<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID]%>");
//        });
        $('.tabRelevantDoc').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showEmpRelevantDocForm");
            uploadTrigger();
            deletegeneral(".btndeleteEmpRelevantDoc", ".<%= FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_RELEVANT_ID]%>");
        });
        $('.tabAward').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showEmpAwardForm");
            uploadTrigger();
            deletegeneral(".btndeleteEmpAward", ".<%= FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_ID]%>");
            
        });
        $('.tabWarning').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showempwarningform");
            deletegeneral(".btndeleteempwarning", ".<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARNING_ID]%>");
        });
        $('.tabReprimand').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showEmpReprimandForm");
            uploadTrigger();
            deletegeneral(".btndeleteEmpReprimand", ".<%= FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_ID]%>");
        });
        $('.tabCareer').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("career_form");
            deletegeneral(".btn_delete_career", ".<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID]%>");
        });
        $('.tabContract').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("contract_type_form");
            deletegeneral(".btn_delete_contract", ".contract_type_id");
        });
        

        //FORM SUBMIT
        $("form#generalform").submit(function () {
            var currentBtnHtml = $("#btngeneralform").html();
            $("#btngeneralform").html("Saving...").attr({"disabled": "true"});
            var generaldatafor = $("#generaldatafor").val();
            if (generaldatafor == "showempcompetencyform") {
                empCompetencyForm();
                servletName = "AjaxEmpCompetency";
                onDone = function (data) {
                    runDataTables("showempcompetencyform");
                };
            }
            if (generaldatafor == "showemplanguageform") {
                empLanguageForm();
                servletName = "AjaxEmpLanguage";
                onDone = function (data) {
                    runDataTables("showemplanguageform");
                };
            }
            if (generaldatafor == "showempexperienceform") {
                empExperienceForm();
                servletName = "AjaxEmpExperience";
                onDone = function (data) {
                    runDataTables("showempexperienceform");
                };
            }
            if (generaldatafor == "showempeducationform") {
                empEducationForm();
                servletName = "AjaxEmpEducation";
                onDone = function (data) {
                    runDataTables("showempeducationform");
                };
            }
            if (generaldatafor == "showfamilymemberform") {
                familyMemberForm();
                servletName = "AjaxFamilyMember";
                onDone = function (data) {
                    runDataTables("showfamilymemberform");
                };
            }
            if (generaldatafor == "showtraininghistoryform") {
                trainingHistoryForm();
                servletName = "AjaxTrainingHistory";
                onDone = function (data) {
                    runDataTables("showtraininghistoryform");
                };
            }
            if (generaldatafor == "showEmpMutationForm") {
                empExperienceForm();
                servletName = "AjaxEmpCareerPath";
                onDone = function (data) {
                    runDataTables("showEmpCareerPathForm");
                };
            }
            if (generaldatafor == "showEmpRelevantDocForm") {
                relevantDocumentForm();
                servletName = "AjaxEmpRelevantDoc";
                onDone = function (data) {
                    runDataTables("showEmpRelevantDocForm");
                };
            }
            if (generaldatafor == "showEmpAwardForm") {
                empAwardForm();
                servletName = "AjaxEmpAward";
                onDone = function (data) {
                    runDataTables("showEmpAwardForm");
                };
            }
            if (generaldatafor == "showempwarningform") {
                empWarningForm();
                servletName = "AjaxEmpWarning";
                onDone = function (data) {
                    runDataTables("showempwarningform");
                };
            }
            if (generaldatafor == "showEmpReprimandForm") {
                empReprimandForm();
                servletName = "AjaxEmpReprimand";
                onDone = function (data) {
                    runDataTables("showEmpReprimandForm");
                };
            }
            if (generaldatafor == "career_form"){
                servletName = "CareerAjax";
                onDone = function (data){
                    runDataTables("career_form");
                };
            }
            
            if (generaldatafor == "contract_type_form"){
                contractTypeValidation();
                servletName = "ContractTypeAjax";
                onDone = function (data){
                    runDataTables("contract_type_form");
                };
            }
            
            if (generaldatafor == "do_contract_form"){
                servletName = "CareerAjax";
                onDone = function (data){
                    runDataTables("do_contract_form");
                };
            }
            
            if (generaldatafor == "do_resign_form"){
                servletName = "CareerAjax";
                onDone = function (data){
                    runDataTables("career_form");
                };
            }

            if (generaldatafor == "do_mutation_form"){
                servletName = "CareerAjax";
                onDone = function (data){
                    runDataTables("do_mutation_form");
                };
            }

            if ($(this).find(".has-error").length == 0) {
                onSuccess = function (data) {
                    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
                    $("#addeditgeneral").modal("hide");
                };

                dataSend = $(this).serialize();
                getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, null, true, "json");
            } else {
                $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
            }

            return false;
        });
        
        $(".fancybox-close").click(function(){
            alert();
        });

        $("#do_contract").click(function(){
            $("#addeditgeneral").modal("show");
            command = $("#command").val();
            oid = $(this).data('oid');
            dataFor = $(this).data('for');
            empId = "<%=employee.getOID()%>";
            userName = "<%=emplx.getFullName()%>";
            userId = <%=appUserIdSess%>;
            $("#generaldatafor").val(dataFor);
            $("#oid").val(oid);
            $("#empId").val(empId);
            $(".addeditgeneral-title").html("Add Contract");
            servletName = "CareerAjax";
            dataSend = {
                "FRM_FIELD_DATA_FOR": dataFor,
                "FRM_FIELD_OID": oid,
                "command": command,
                "empId" : empId,
                "userName" : userName,
                "userId" : userId
            }
            onDone = function (data) {
                datePicker(".datepicker", "yyyy-mm-dd");
            };
            onSuccess = function (data) {

            };
            getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, ".addeditgeneral-body", false, "json");
        });
        
         //FancyBox
            $('.fancybox').fancybox({
                    helpers:{
                        overlay : {closeClick:false}
                    }
                });

                /*
                 *  Different effects
                 */

                // Change title type, overlay closing speed
                $(".fancybox-effects-a").fancybox({
                    helpers: {
                        title : {
                            type : 'outside'
                        },
                        overlay : {
                            speedOut : 0
                        }
                    }
                });

                // Disable opening and closing animations, change title type
                $(".fancybox-effects-b").fancybox({
                    openEffect  : 'none',
                    closeEffect	: 'none',

                    helpers : {
                        title : {
                            type : 'over'
                        }
                    }
                });

                // Set custom style, close if clicked, change title type and overlay color
                $(".fancybox-effects-c").fancybox({
                    wrapCSS    : 'fancybox-custom',
                    closeClick : true,

                    openEffect : 'none',

                    helpers : {
                        title : {
                            type : 'inside'
                        },
                        overlay : {
                            css : {
                                'background' : 'rgba(238,238,238,0.85)'
                            }
                        }
                    }
                });

                // Remove padding, set opening and closing animations, close if clicked and disable overlay
                $(".fancybox-effects-d").fancybox({
                    padding: 0,

                    openEffect : 'elastic',
                    openSpeed  : 150,

                    closeEffect : 'elastic',
                    closeSpeed  : 150,

                    closeClick : true,

                    helpers : {
                        overlay : null
                    }
                });

                /*
                 *  Button helper. Disable animations, hide close button, change title type and content
                 */

                $('.fancybox-buttons').fancybox({
                    openEffect  : 'none',
                    closeEffect : 'none',

                    prevEffect : 'none',
                    nextEffect : 'none',

                    closeBtn  : false,

                    helpers : {
                        title : {
                            type : 'inside'
                        },
                        buttons	: {}
                    },

                    afterLoad : function() {
                        this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
                    }
                });


                /*
                 *  Thumbnail helper. Disable animations, hide close button, arrows and slide to next gallery item if clicked
                 */

                $('.fancybox-thumbs').fancybox({
                    prevEffect : 'none',
                    nextEffect : 'none',

                    closeBtn  : false,
                    arrows    : false,
                    nextClick : true,

                    helpers : {
                        thumbs : {
                            width  : 50,
                            height : 50
                        }
                    }
                });

                /*
                 *  Media helper. Group items, disable animations, hide arrows, enable media and button helpers.
                 */
                $('.fancybox-media')
                .attr('rel', 'media-gallery')
                .fancybox({
                    openEffect : 'none',
                    closeEffect : 'none',
                    prevEffect : 'none',
                    nextEffect : 'none',

                    arrows : false,
                    helpers : {
                        media : {},
                        buttons : {}
                    }
                });

                /*
                 *  Open manually
                 */

                $("#fancybox-manual-a").click(function() {
                    $.fancybox.open('1_b.jpg');
                });

                $("#fancybox-manual-b").click(function() {
                    $.fancybox.open({
                        href : 'member.jsp',
                        type : 'iframe',
                        padding : 5
                    });
                });

                $("#fancybox-manual-c").click(function() {
                    $.fancybox.open([
                        {
                            href : '1_b.jpg',
                            title : 'My title'
                        }, {
                            href : '2_b.jpg',
                            title : '2nd title'
                        }, {
                            href : '3_b.jpg'
                        }
                    ], {
                        helpers : {
                            thumbs : {
                                width: 175,
                                height: 150
                            }
                        }
                    });
               


            });

    })
</script>
            <script>
                //function pageLoad(){ loadCompany('<%=employee.getOID()%>'); loadWaCompany('<%=employee.getOID()%>'); }  
            </script>
        <div id="addeditgeneral" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addeditgeneral-title"></h4>
		</div>
                <form id="generalform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="datachange" id="datachange">
                    <input type="hidden" name="empId" id="empId">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body addeditgeneral-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btngeneralform"><i class="fa fa-check"></i> Save</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <div id="uploaddoc" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addeditgeneral-title-doc">Upload Document</h4>
		</div>
                <form method="POST" id="formupload" enctype="multipart/form-data">		    
		    <input type="hidden" name="command" value="<%= Command.POST %>">
                    <input style="width:0px; height:0px;"  type="file" name="FRM_DOC" id="FRM_DOC">
		   <input type="hidden" name="FRM_FIELD_OID" id="oiddata">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body upload-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Upload Document</label>
                                                <div class="input-group my-colorpicker2 colorpicker-element">
                                                    <input required id="tempname" class="form-control" type="text">
                                                    <div style="cursor: pointer" class="input-group-addon" id="uploadtrigger">
                                                        <i class="fa fa-file-pdf-o"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>    
				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnupload"><i class="fa fa-check"></i> Save</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <div id="myModalSm" class="modal fade" role="dialog">
    <div class="modal-dialog">

       <!-- Modal content-->

           <div class="modal-content">
           <div class="modal-header">
           <button type="button" class="close" data-dismiss="modal">&times;</button>
           <h4 class="addeditgeneral-title"></h4>
           </div>
           <form id="form-modal" class="form-horizontal">
            <input type="hidden" name="oid" id="oidSm">
            <input type="hidden" name="empId" id="empIdSm">
            <input type="hidden" name="datafor" id="dataforSm">
            <input type="hidden" name="command" id="commandSm">
            <input type="hidden" id="hiddenvalue">
            <input type="hidden" id="oidhiddenvalue">
           <div class="modal-body active-scroll" id="modalbody2">
           </div>
           <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
           </div>
           </form>
           </div>
           </div>
</div>                   
    </body>
</html>
