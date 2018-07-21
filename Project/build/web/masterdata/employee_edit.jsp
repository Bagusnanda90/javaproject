<%-- 
    Class Name   : template.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 10:59:14 PM
    Function     : This is template
--%>

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
        <title>Employee Edit | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 --> 
        <link rel="stylesheet" href="<%= approot%>/assets/bootstrap/css/bootstrap.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="<%= approot%>/assets/font-awesome/4.6.1/css/font-awesome.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="<%= approot%>/assets/ionicons/2.0.1/css/ionicons.css">
        <!-- daterange picker -->
        <link rel="stylesheet" href="<%= approot%>/assets/plugins/daterangepicker/daterangepicker-bs3.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="<%= approot%>/assets/plugins/select2/select2.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/AdminLTE.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/skins/skin-blue.css">
        <link rel="stylesheet" href="<%= approot%>/assets/bootstrap/datepicker/bootstrap-datepicker3.css">
        <link rel="stylesheet" href="<%= approot %>/assets/plugins/datatables/dataTables.bootstrap.css">
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
        <script language="JavaScript">
             function cmdUpPictCam(oid){
                window.open("<%=approot%>/employee/databank/employee_cam.jsp?employee_oid="+oid, "Up Pict","height=500,width=500, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes").focus();
            }
            function cmdUpPict(oid){
                window.open("<%=approot%>/employee/databank/up_picture.jsp?employee_oid="+oid, "Up Pict","height=500,width=500, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes").focus();
            }
        </script>
    </head>

    <body class="hold-transition skin-blue sidebar-mini sidebar-collapse"  onload="pageLoad()">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
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
                                                    <li><a href="#tab_relevant" data-toggle="tab">Relevant Document</a></li>
                                                    <li><a href="#tab_assets" data-toggle="tab">Assets & Inventory</a></li>
                                                    <li><a href="#tab_setting" data-toggle="tab">Setting</a></li>
                                                </ul>
                                                <div class="tab-content">
                                                    <div class="tab-pane active" id="tab_personal">
                                                        <form name="frm_employee" method="post" action="">
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
                                                                                pictPath = sessEmployeePicture.fetchImageEmployee(employee.getEmployeeNum());

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
                                                                        <div ><a class="btn-default fa fa-camera" id="Take"  href="javascript:cmdUpPictCam('<%=oidEmployee%>')"></a>  <a class="btn-default fa fa-folder" id="Take"  href="javascript:cmdUpPict('<%=oidEmployee%>')"></a></div>
                                                                        </h3>
                                                                        
                                                                    </div>
                                                                </div>
                                                            
                                                            
                                                        </div>
                                                        </div>
                                                        <hr />
                                                        
                                                        <div class="row">
                                                            <div class="col-md-6" >
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
                                                                        <label>Employee Number</label> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMPLOYEE_NUM)%>
                                                                        <% if(attdConfig != null && attdConfig.getConfigurasiInputEmpNum() == I_Atendance.CONFIGURATION_II_GENERATE_AUTOMATIC_EMPLOYEE_NUMBER){
                                                                        %> 
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM]%>" value="<%=employee.getEmployeeNum()==null || employee.getEmployeeNum().length()==0?"automatic":employee.getEmployeeNum()%>" class="form-control" readonly="readonly">
                                                                        <% } else { %>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM]%>"  value="<%=employee.getEmployeeNum()%>" class="form-control"  >
                                                                        <% } %>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Full Name</label> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_FULL_NAME)%>  
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME]%>" value="<%=employee.getFullName()%>" class="form-control">
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Temporary Address</label> <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_ADDRESS)%> 
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS]%>" size="50" value="<%=employee.getAddress()%>" class="form-control">
                                                                        <input class="form-control addeditgeo"  type="text" name="geo_address" readonly="true" value="<%=employee.getGeoAddress()%>" size="50" id="geo_address"  data-for="geoaddress">
                                                                        
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_COUNTRY_ID]%>" value="<%="" + employee.getAddrCountryId()%>" id="oidnegara">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PROVINCE_ID]%>" value="<%="" + employee.getAddrProvinceId()%>" id="oidprovinsi">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_REGENCY_ID]%>" value="<%="" + employee.getAddrRegencyId()%>" id="oidkabupaten">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_SUBREGENCY_ID]%>" value="<%="" + employee.getAddrSubRegencyId()%>" id="oidkecamatan">
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Permanent Address</label>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_PERMANENT]%>" size="50" value="<%=(employee.getAddressPermanent() != null ? employee.getAddressPermanent() : "")%>" class="form-control">
                                                                        <input class="form-control addeditgeo"  type="text" name="geo_address_pmnt" id="geo_address_pmnt" readonly="true" size="50" value="<%=employee.getGeoAddressPmnt()%>" data-for="geoaddresspmnt">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_COUNTRY_ID]%>" value="<%="" + employee.getAddrPmntCountryId()%>" id="oidnegarapmnt">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_PROVINCE_ID]%>" value="<%="" + employee.getAddrPmntProvinceId()%>" id="oidprovinsipmnt">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_REGENCY_ID]%>" value="<%="" + employee.getAddrPmntRegencyId()%>" id="oidkabupatenpmnt">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_SUBREGENCY_ID]%>" value="<%="" + employee.getAddrPmntSubRegencyId()%>" id="oidkecamatanpmnt">        
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Zip Code</label>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSTAL_CODE]%>" value="<%=employee.getPostalCode() == 0 ? "" : "" + employee.getPostalCode()%>" class="form-control" >
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Telephone / Handphone</label>
                                                                        <div class="input-group">
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PHONE]%>" value="<%=(employee.getPhone() != null ? employee.getPhone() : "")%>" class="form-control" >
                                                                        <span class="input-group-addon">/</span>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_HANDPHONE]%>" value="<%=employee.getHandphone()%>" class="form-control" >
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Emergency Phone / Person Name</label>
                                                                        <div class="input-group">
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PHONE_EMERGENCY]%>" value="<%=(employee.getPhoneEmergency() != null ? employee.getPhoneEmergency() : "")%>" class="form-control">
                                                                        <span class="input-group-addon">/</span>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NAME_EMG]%>" value="<%=(employee.getNameEmg() != null ? employee.getNameEmg() : "")%>" class="form-control">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Emergency Address</label>
                                                                        <textarea class="form-control" rows="2" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_EMG]%>" ><%=employee.getAddressEmg()%></textarea>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Gender &nbsp;&nbsp;&nbsp;&nbsp;</label> 
                                                                        <% for (int i = 0; i < PstEmployee.sexValue.length; i++) {
                                                                            String str = "";
                                                                            if (employee.getSex() == PstEmployee.sexValue[i]) {
                                                                                str = "checked";
                                                                            }
                                                                        %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SEX]%>" value="<%="" + PstEmployee.sexValue[i]%>" <%=str%> style="border:none">
                                                                        <%=PstEmployee.sexKey[i]%> <% }%>
                                                                        * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_SEX)%> 
                                                                        <br />
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Place of Birth</label> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_BIRTH_PLACE)%>   
                                                                        <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_PLACE]%>" value="<%=employee.getBirthPlace()%>" class="form-control" >
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Date of Birth</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                             <%
                                                                            Date birthDate = employee.getBirthDate()== null ? new Date() : employee.getBirthDate();
                                                                            String strBirthDate = sdf.format(birthDate);
                                                                            %>
                                                                            <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_DATE_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strBirthDate %>">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Shio </label> * Auto
                                                                        <input type="text" disabled="disable" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SHIO]%>" value="<%=employee.getShio()%>" class="form-control"> 
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Element </label> * Auto
                                                                        <input type="text" disabled="disable" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ELEMEN]%>" value="<%=employee.getElemen()%>" class="form-control">
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Religion</label> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_RELIGION_ID)%> 
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RELIGION_ID]%>">
                                                                        <option value="0">-select-</option>
                                                                        <%
                                                                            Vector listReligion = PstReligion.listAll();
                                                                                for (int i = 0; i < listReligion.size(); i++) {
                                                                                    Religion religion = (Religion) listReligion.get(i);
                                                                                    if (employee.getReligionId() == religion.getOID()){
                                                                                        %><option selected="selected" value="<%=religion.getOID()%>"><%=religion.getReligion()%></option><%
                                                                                    } else {
                                                                                        %><option value="<%=religion.getOID()%>"><%=religion.getReligion()%></option><%
                                                                                    }
                                                                            }
        
                                                                        %>
                                                                        </select>
                                                                        </div>
                                                                    
                                                                        <div class="form-group">
                                                                        <label>Marital Status for HR</label> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_MARITAL_ID)%>      
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MARITAL_ID]%>">
                                                                        <option value="0">-select-</option>
                                                                        <%
                                                                            Vector listMarital = PstMarital.list(0, 0, "", " MARITAL_STATUS ");
                                                                                for (int i = 0; i < listMarital.size(); i++) {
                                                                                    Marital marital = (Marital) listMarital.get(i);
                                                                                    if (employee.getMaritalId()== marital.getOID()){
                                                                                        %><option selected="selected" value="<%=marital.getOID()%>"><%=marital.getMaritalStatus() + " - " + marital.getNumOfChildren()%></option><%
                                                                                    } else {
                                                                                        %><option value="<%=marital.getOID()%>"><%=marital.getMaritalStatus() + " - " + marital.getNumOfChildren()%></option><%
                                                                                    }
                                                                            }
        
                                                                        %>
                                                                        </select>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Marital Status for Tax Report</label> * (Set per 1 January conform to Tax Regulation )  <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_TAX_MARITAL_ID)%>    
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_TAX_MARITAL_ID]%>">
                                                                        <option value="0">-select-</option>
                                                                        <%
                                                                            
                                                                                for (int i = 0; i < listMarital.size(); i++) {
                                                                                    Marital marital = (Marital) listMarital.get(i);
                                                                                    if (employee.getTaxMaritalId()== marital.getOID()){
                                                                                        %><option selected="selected" value="<%=marital.getOID()%>"><%=marital.getMaritalStatus() + " - " + marital.getNumOfChildren()%></option><%
                                                                                    } else {
                                                                                        %><option value="<%=marital.getOID()%>"><%=marital.getMaritalStatus() + " - " + marital.getNumOfChildren()%></option><%
                                                                                    }
                                                                            }
        
                                                                        %>
                                                                        </select>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Blood Type</label>
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BLOOD_TYPE]%>">
                                                                        <%
                                                                          Vector listBloodType =  PstEmployee.getBlood();
                                                                          for(int idx=0; idx < listBloodType.size();idx++){
                                                                              String bloodType = (String) listBloodType.get(idx);
                                                                              if (employee.getBloodType().equals(bloodType)) {
                                                                        %>      
                                                                                <option selected="selected" value="<%=bloodType%>"><%=bloodType%></option>
                                                                        <%
                                                                              } else {
                                                                        %>
                                                                                <option value="<%=bloodType%>"><%=bloodType%></option>
                                                                        <%
                                                                                }
                                                                              
                                                                          }  
                                                                        %>
                                                                        </select>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Race</label>
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RACE]%>">
                                                                        <option value="0">-select-</option>
                                                                        <%
                                                                            Vector listRace = PstRace.list(0, 0, "", PstRace.fieldNames[PstRace.FLD_RACE_NAME]);
                                                                                for (int i = 0; i < listRace.size(); i++) {
                                                                                    Race race = (Race) listRace.get(i);
                                                                                    if (employee.getRace()== race.getOID()){
                                                                                        %><option selected="selected" value="<%=race.getOID()%>"><%=race.getRaceName()%></option><%
                                                                                    } else {
                                                                                        %><option value="<%=race.getOID()%>"><%=race.getRaceName()%></option><%
                                                                                    }
                                                                            }
        
                                                                        %>
                                                                        </select>
                                                                        
                                                                        <label>Barcode Number</label>  * <%=frmEmployee.getErrorMsgModif(FrmEmployee.FRM_FIELD_BARCODE_NUMBER)%>
                                                                        <input  type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BARCODE_NUMBER]%>" title=" If Employe is a Daily Worker (DL)  please replace 'DL-' with '12' ,for  example 'DL-333' become to '12333'.     If Employe's  Status  'Resigned'  please input the barcode number, barcode number is unique for example -R(BarcodeNumb/PinNumber)" value="<%=(employee.getBarcodeNumber() != null ? employee.getBarcodeNumber() : "")%>" class="form-control">
                                                                    
                                                                        <label>ID Card</label> |  <strong><a href="javascript:" data-oid="<%= employee.getOID() %>" data-for="showprimecardhistory" class="viewhistory" data-command="<%= Command.NONE %>"><i class="fa fa-search"></i> View History</a></strong>
                                                                        <div class="input-group">
                                                                        <span class="input-group-addon">Type</span>
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ID_CARD_TYPE]%>">
                                                                        <%
                                                                          Vector listIDCardType =  PstEmployee.getId_Card_Type();
                                                                          for(int idx=0; idx < listIDCardType.size();idx++){
                                                                              String idCard = (String) listIDCardType.get(idx);
                                                                              if (employee.getIdcardtype().equals(idCard)) {
                                                                        %>      
                                                                                <option selected="selected" value="<%=listIDCardType.get(idx)%>"><%=listIDCardType.get(idx)%></option>
                                                                        <%
                                                                              } else {
                                                                        %>
                                                                                <option value="<%=listIDCardType.get(idx)%>"><%=listIDCardType.get(idx)%></option>
                                                                        <%
                                                                                }
                                                                              
                                                                          }  
                                                                        %>
                                                                        </select>
                                                                        <span class="input-group-addon">No </span>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_INDENT_CARD_NR]%>" value="<%=employee.getIndentCardNr()%>" class="form-control">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>ID Card Valid Until</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                            <%
                                                                            Date idDate = employee.getIndentCardValidTo()== null ? new Date() : employee.getIndentCardValidTo();
                                                                            String strIdDate = sdf.format(idDate);
                                                                            %>
                                                                            <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_INDENT_CARD_VALID_TO_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strIdDate %>">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Secondary ID Card</label>
                                                                        <div class="input-group">
                                                                        <span class="input-group-addon">Type</span>
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECONDARY_ID_TYPE]%>">
                                                                        <%
                                                                          Vector listIdCardType =  PstEmployee.getId_Card_Type();
                                                                          for(int idx=0; idx < listIdCardType.size();idx++){
                                                                              String idCard = (String) listIdCardType.get(idx);
                                                                              if (employee.getSecondaryIdType().equals(idCard)) {
                                                                        %>      
                                                                                <option selected="selected" value="<%=listIDCardType.get(idx)%>"><%=listIDCardType.get(idx)%></option>
                                                                        <%
                                                                              } else {
                                                                        %>
                                                                                <option value="<%=listIDCardType.get(idx)%>"><%=listIDCardType.get(idx)%></option>
                                                                        <%
                                                                                }
                                                                              
                                                                          }  
                                                                        %>
                                                                        </select>
                                                                        <span class="input-group-addon">No </span>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECONDARY_ID_NO]%>" value="<%=employee.getSecondaryIdNo()%>" class="form-control">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Secondary ID Card Valid Until</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                            <%
                                                                            Date secIdDate = employee.getSecondaryIDValid()== null ? new Date() : employee.getSecondaryIDValid();
                                                                            String strSecIdDate = sdf.format(secIdDate);
                                                                            %>
                                                                            <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECONDARY_ID_DATE_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strSecIdDate %>">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Email</label>
                                                                        <input type="text" placeholder="e.g: jhon@domain.com" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMAIL_ADDRESS]%>" class="form-control"  value="<%=employee.getEmailAddress()%>" />
                                                                        </div>
                                                                        
                                                                        <label>Payroll Group</label>
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PAYROLL_GROUP]%>">
                                                                        <%
                                                                          Vector listPayrollGroup = PstPayrollGroup.list(0, 0, "", "PAYROLL_GROUP_NAME");
                                                                          for (int i = 0; i < listPayrollGroup.size(); i++) {
                                                                              PayrollGroup payrollGroup = (PayrollGroup) listPayrollGroup.get(i);
                                                                              if (employee.getPayrollGroup() == payrollGroup.getOID()) {
                                                                        %>      
                                                                                <option selected="selected" value="<%=payrollGroup.getOID()%>"><%=payrollGroup.getPayrollGroupName()%></option>
                                                                        <%
                                                                              } else {
                                                                        %>
                                                                                <option value="<%=payrollGroup.getOID()%>"><%=payrollGroup.getPayrollGroupName()%></option>
                                                                        <%
                                                                                }
                                                                              
                                                                          }  
                                                                        %>
                                                                        </select>
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
                                                                        <label>IQ</label>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_IQ]%>" value="<%=(employee.getIq() != null ? employee.getIq() : "")%>" class="form-control">
                                                                    
                                                                        <label>EQ</label>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EQ]%>" value="<%=(employee.getEq() != null ? employee.getEq() : "")%>" class="form-control">
                                                                        
                                                                        
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
                                                                        <label>No Rekening</label>
                                                                        <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NO_REKENING]%>" class="form-control" value="<%=employee.getNoRekening()%>" />
                                                                       
                                                                        <label>NPWP</label>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NPWP]%>" value="<%=(employee.getNpwp() != null ? employee.getNpwp() : "")%>" class="form-control">
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
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
                                                                                
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
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
                                                                                <%
                                                                                }%>
                                                                        </tr>
                                                                        <tr>
                                                                            <td> Week 3rd </td>
                                                                            <%                    
                                                                              for(int idx=15;idx <= 21; idx++){
                                                                                DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                                                                %>
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
                                                                                <%
                                                                                }%>
                                                                        </tr>
                                                                        <tr>
                                                                            <td> Week 4th </td>
                                                                            <%                    
                                                                              for(int idx=22;idx <= 28; idx++){
                                                                                DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                                                                %>
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
                                                                                <%
                                                                                }%>
                                                                        </tr>
                                                                        <tr>
                                                                            <td> Week 5th </td>
                                                                            <%                    
                                                                              for(int idx=29;idx <=35; idx++){
                                                                                DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                                                                %>
                                                                                <td><input type="text" size="4" name="sche1_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%>" readonly="true" /> </td>
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
                                                                                <td><input type="text" size="4" name="sche2_<%=idx%>" value="<%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule2()): "-" )%>" readonly="true" </td>
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
                                                                        <a id="btn" href="javascript:editDefaultSch()">Edit Default Schedule</a>
                                                                    
                                                                    </div>
                                                                </div>    
                                                            </div>
                                                            
                                                            <div class="col-md-6">
                                                                
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
                                                                        <div id="txtHint"></div>
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
                                                                        <div id="listWa"></div>
                                                                        <div class="form-group">
                                                                            <label>
                                                                                Duration
                                                                            </label>
                                                                            <div class="input-group">
                                                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                                <%
                                                                                Date waFromDate = employee.getWaFrom()== null ? new Date() : employee.getWaFrom();
                                                                                Date waToDate = employee.getWaTo()== null ? new Date() : employee.getWaTo();
                                                                                String strWaFromDate = sdf.format(waFromDate);
                                                                                String strWaToDate = sdf.format(waToDate);
                                                                                %>
                                                                                <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WA_FROM_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strWaFromDate %>">
                                                                                <span class="input-group-addon">to</span>
                                                                                <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WA_TO_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strWaToDate %>">
                                                                            </div>    
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
                                                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                            <%
                                                                            Date commDate = employee.getCommencingDate()== null ? new Date() : employee.getCommencingDate();
                                                                            String strCommDate = sdf.format(commDate);
                                                                            %>
                                                                            <input type="text" name="<%= frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMMENCING_DATE_STRING] %>" class="form-control pull-right datepicker" id="datepicker" value="<%= strCommDate%>">
                                                                        </div>
                                                                        </div>
                                                                        <div class="form-group">
                                                                        <label>Probation End Date</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                            <%
                                                                            Date probDate = employee.getProbationEndDate()== null ? new Date() : employee.getProbationEndDate();
                                                                            String strProbDate = sdf.format(probDate);
                                                                            %>
                                                                            <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PROBATION_END_DATE_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strProbDate %>">
                                                                        </div>
                                                                        </div>
                                                                        <div class="form-group">
                                                                        <label>Ended Contract</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                            <%
                                                                            Date endContractDate = employee.getEnd_contract()== null ? new Date() : employee.getEnd_contract();
                                                                            String strContractDate = sdf.format(endContractDate);
                                                                            %>
                                                                            <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_END_CONTRACT_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strContractDate %>">
                                                                        </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                
                                                                        
                                                                <div class="box" id="hide_other" style="display: none">
                                                                    <div class="box-header with-border">
                                                                        <div class="pull-left"></div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="show_other_b">Show</button></div>
                                                                    </div>
                                                                </div>         
                                                                <div class="box" id="show_other">
                                                                     <div class="box-header with-border">
                                                                        <div class="pull-left"></div>
                                                                        <div class="pull-right"><button type="button" class="btn btn-sm" id="hide_other_b">Hide</button></div>
                                                                    </div>
                                                                    <div class="box-body">
                                                                        <div class="form-group">
                                                                        <label>BPJS Ketenaga Kerjaan Number</label>
                                                                        <input type="text" autocomplete="off" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ASTEK_NUM]%>" value="<%=employee.getAstekNum()%>" class="form-control">
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>BPJS Ketenaga Kerjaan Date</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                            <%
                                                                            Date astekDate = employee.getAstekDate()== null ? new Date() : employee.getAstekDate();
                                                                            String strAstekDate = sdf.format(astekDate);
                                                                            %>
                                                                            <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ASTEK_DATE_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strAstekDate %>">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>BPJS Kesehatan No.</label>
                                                                        <input type="text" autocomplete="off" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BPJS_NO]%>" value="<%=employee.getBpjs_no()!=null? employee.getBpjs_no():""%>" class="form-control">
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>BPJS Kesehatan Date</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                            <%
                                                                            Date bpjsDate = employee.getBpjs_date()== null ? new Date() : employee.getBpjs_date();
                                                                            String strBpjsDate = sdf.format(bpjsDate);
                                                                            %>
                                                                            <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BPJS_DATE_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strBpjsDate %>">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Member of BPJS Kesehatan</label>
                                                                        <% for (int i = 0; i < PstEmployee.memberOfBPJSKesehatanValue.length; i++) {
                                                                                String strMemOfBpjsKesehatan = "";
                                                                                if (employee.getMemOfBpjsKesahatan() == PstEmployee.memberOfBPJSKesehatanValue[i]) {
                                                                                    strMemOfBpjsKesehatan = "checked";
                                                                                }
                                                                        %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MEMBER_OF_BPJS_KESEHATAN]%>" value="<%="" + PstEmployee.memberOfBPJSKesehatanValue[i]%>" <%=strMemOfBpjsKesehatan%> style="border:'none'">
                                                                        <%=PstEmployee.memberOfBPJSKesehatanKey[i]%> <%}%> 
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Member of BPJS Ketenagakerjaan</label>
                                                                        <% for (int i = 0; i < PstEmployee.memberOfBPJSKetenagaKerjaanValue.length; i++) {
                                                                                String strMemOfBpjsKetenagaKerjaan = "";
                                                                                if (employee.getMemOfBpjsKetenagaKerjaan() == PstEmployee.memberOfBPJSKetenagaKerjaanValue[i]) {
                                                                                    strMemOfBpjsKetenagaKerjaan = "checked";
                                                                                }
                                                                        %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MEMBER_OF_BPJS_KETENAGAKERJAAN]%>" value="<%="" + PstEmployee.memberOfBPJSKetenagaKerjaanValue[i]%>" <%=strMemOfBpjsKetenagaKerjaan%> style="border:'none'">
                                                                        <%=PstEmployee.memberOfBPJSKetenagaKerjaanKey[i]%> <%}%> 
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Status Pensiun Program</label>
                                                                        <%
                                                                             for (int i = 0; i < PstEmployee.statusPensiunProgramValue.length; i++) {
                                                                                String strStPensiun = "";
                                                                                if (employee.getStatusPensiunProgram() == PstEmployee.statusPensiunProgramValue[i]) {
                                                                                        strStPensiun = "checked";
                                                                                }
                                                                        %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_STATUS_PENSIUN_PROGRAM]%>" value="<%="" + PstEmployee.statusPensiunProgramValue[i]%>" <%=strStPensiun%> style="border:'none'">
                                                                        <%=PstEmployee.statusPensiunProgramKey[i]%> <%}%>    
                                                                        
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Start Date Program Pensiun (Mulai diangkat)</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                                            <%
                                                                            Date pensiunDate = employee.getStartDatePensiun()== null ? new Date() : employee.getStartDatePensiun();
                                                                            String strPensiunDate = sdf.format(pensiunDate);
                                                                            %>
                                                                            <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_START_DATE_PENSIUN_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strPensiunDate %>">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Dana Pendidikan</label>
                                                                        <% for (int i = 0; i < PstEmployee.statusDanaPendidikanValue.length; i++) {
                                                                                String strDanaPendidikan = "";
                                                                                if (employee.getDanaPendidikan() == PstEmployee.statusDanaPendidikanValue[i]) {
                                                                                        strDanaPendidikan = "checked";
                                                                                }
                                                                        %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DANA_PENDIDIKAN]%>" value="<%="" + PstEmployee.statusDanaPendidikanValue[i]%>" <%=strDanaPendidikan%> style="border:'none'">
                                                                        <%=PstEmployee.statusDanaPendidikanKey[i]%> <%}%> 
                                                                        </div>
                                                                        
                                                                        <div class="form-group"> 
                                                                        <label>Employee PIN</label> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_PIN)%>&nbsp;
                                                                        <input type="password"  name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_PIN]%>" value="<%=(employee.getEmpPin() != null ? employee.getEmpPin() : "")%>" autocomplete="new-password" class="form-control">
                                                                         
                                                                        </div>
                                                                        
                                                                        
                                                                        
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
                                                               out.println( FrmEmployee.fieldNamesForUser[idx]+" = "+ frmEmployee.getErrorMsg(idx)+";");
                                                             }
                                                         }

                                                         //}

                                                         } %>
                                                        </div>
                                                        
                                                        
                                                         
                                                    </div><!-- /.tab-pane -->
                                                    <div class="tab-pane" id="tab_family"  >
                                                        <a> <h3>Family Member List : </h3></a>
                                                        <hr>
                                                            <div id="listFam">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdata" data-oid="0" data-for="showfamform" data-command="0"> Add New Family</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_competencies"  >
                                                        <a> <h3>Competency List : </h3></a>
                                                        <hr>
                                                            <div id="listCom">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdatacomp" data-oid="0" data-for="showcompetencyform" data-command="0"> Add New Competency</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    <div class="tab-pane" id="tab_language"  >
                                                        <a> <h3>Language List : </h3></a>
                                                        <hr>
                                                            <div id="listLang">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdatalang" data-oid="0" data-for="showlanguageform" data-command="0"> Add New Language</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    <div class="tab-pane" id="tab_education"  >
                                                        <a> <h3>Education List : </h3></a>
                                                        <hr>
                                                            <div id="listEdu">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdataedu" data-oid="0" data-for="showeducationform" data-command="0"> Add New Education</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    <div class="tab-pane" id="tab_experience"  >
                                                        <a> <h3>Experience List : </h3></a>
                                                        <hr>
                                                            <div id="listExp">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdataexp" data-oid="0" data-for="showexpform" data-command="0"> Add New Experience</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_award"  >
                                                        <a> <h3>Award List : </h3></a>
                                                        <hr>
                                                            <div id="listAward">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdataaward" data-oid="0" data-for="showawardform" data-command="0"> Add New Award</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_cpath"  >
                                                        <a> <h3>Career Path : </h3></a>
                                                        <hr>
                                                            <div id="listCareer">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus docontract" data-oid="0" data-for="docontractform" data-command="0"> Do Contract</button>
                                                                    <button type="button" id="do_mutation" class="btn btn-primary btn-sm fa fa-plus domutation" data-oid="0" data-for="domutationform" data-command="0"> Do Mutation</button>                                                                    
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus doresign" data-oid="0" data-for="doresignform" data-command="0"> Do Resign</button>
                                                                </div>
                                                            </div>
                                                            <div class="row pull-right">
                                                            <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdatacpath" data-oid="0" data-for="showcpathform" data-command="0"> Add New Career Path</button>
                                                            </div>    
                                                        </div>
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_relevant"  >
                                                        <a> <h3>Relevant Document : </h3></a>
                                                        <hr>
                                                            <a> <h4>No Record </h4></a>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdataaward" data-oid="0" data-for="showdocform" data-command="0"> Add New Document</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    <div class="tab-pane" id="tab_assets"  >
                                                        <a> <h3>Assets & Inventory : </h3></a>
                                                        <hr>
                                                            <a> <h4>No Record </h4></a>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdataassets" data-oid="0" data-for="showassetsform" data-command="0"> Add New Assets & Inventory</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_setting"  >
                                                        <a> <h3>Employee Setting : </h3></a>
                                                        <hr>
                                                        
                                                        <div class="form-group"> 
                                                        <label>Employee PIN</label> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_PIN)%>&nbsp;
                                                        <input type="password"  name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_PIN]%>" value="<%=(employee.getEmpPin() != null ? employee.getEmpPin() : "")%>" autocomplete="new-password" class="form-control">
                                                        </div>
                                                        
                                                        <div class="form-group">
                                                            <label>Barcode Number</label>  * <%=frmEmployee.getErrorMsgModif(FrmEmployee.FRM_FIELD_BARCODE_NUMBER)%>
                                                            <input  type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BARCODE_NUMBER]%>" title=" If Employe is a Daily Worker (DL)  please replace 'DL-' with '12' ,for  example 'DL-333' become to '12333'.     If Employe's  Status  'Resigned'  please input the barcode number, barcode number is unique for example -R(BarcodeNumb/PinNumber)" value="<%=(employee.getBarcodeNumber() != null ? employee.getBarcodeNumber() : "")%>" class="form-control">
                                                        </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                <strong><a href="javascript:cmdSave('<%=employee.getOID()%>')"><i class="fa fa-save"></i> Save Setting</a></strong>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_training"  >
                                                        <a> <h3>Employee Training : </h3></a>
                                                        <hr>
                                                            <div id="listTraining">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdatatraining" data-oid="0" data-for="showtrainingform" data-command="0"> Add New Training</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div> 
                                                    
                                                    <div class="tab-pane" id="tab_warning"  >
                                                        <a> <h3>Employee Warning : </h3></a>
                                                        <hr>
                                                            <div id="listWarning">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdatawarning" data-oid="0" data-for="showwarnform" data-command="0"> Add New Warning</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>            
                                                                
                                                     <div class="tab-pane" id="tab_reprimand"  >
                                                        <a> <h3>Employee Reprimand : </h3></a>
                                                        <hr>
                                                            <div id="listReprimand">
                                                            </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdatareprimand" data-oid="0" data-for="showrepform" data-command="0"> Add New Reprimand</button>
                                                                </div>
                                                            </div>
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
                                <div id="myModalLg" class="modal fade" role="dialog">
                                     <div class="modal-dialog modal-lg">

                                        <!-- Modal content-->

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
                                 <div id="myModal2" class="modal fade" role="dialog">
                                     <div class="modal-dialog">

                                        <!-- Modal content-->

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

                                        <!-- Modal content-->

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
                                <div class="box-footer">
                                    <div class="row pull-right">
                                        <div class="col-md-12">
                                            <strong><a href="javascript:cmdSave('<%=employee.getOID()%>')"><i class="fa fa-save"></i> Save Employee</a></strong>
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                            <strong><a href="<%= approot %>/employee/databank/employee_list.jsp"><i class="fa fa-backward"></i> Back to List Employee</a></strong>
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                            <strong><a href="#"><i class="fa fa-times"></i> Delete Employee</a></strong>
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                            <strong><a href="#"><i class="fa fa-file"></i> Print CV</a></strong> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>   
                </section>
            </div><!-- /.content-wrapper -->
            <%@ include file="../../template/footer.jsp" %>
        </div>
        <!-- jQuery 2.1.4 -->
        <script src="<%= approot%>/assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="<%= approot%>/assets/bootstrap/js/bootstrap.js"></script>
        <!-- Select2 -->
        <script src="<%= approot%>/assets/plugins/select2/select2.full.min.js"></script>
        <!-- FastClick -->
        <script src="<%= approot%>/assets/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="<%= approot%>/assets/dist/js/app.js"></script>
        <!-- SlimScroll 1.3.0 -->
        <script src="<%= approot%>/assets/plugins/slimScroll/jquery.slimscroll.js"></script>
        <!-- date-range-picker -->
        <script src="<%= approot%>/assets/bootstrap/datepicker/bootstrap-datepicker.js"></script>
        <script src="<%= approot%>/assets/plugins/daterangepicker/daterangepicker.js"></script>
        <script src="<%= approot%>/assets/plugins/jQuery/jquery.chained.min.js"></script>
        <script src="<%= approot %>/assets/plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
        <script src="<%= approot %>/assets/plugins/datatables/dataTables.bootstrap.js" type="text/javascript"></script>
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
        <script>
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
            </script>
            <script language="JavaScript">
            function cmdSave(){
                document.frm_employee.command.value="<%=Command.SAVE%>";
                document.frm_employee.action="employee_edit.jsp";
                
                document.frm_employee.submit();
            }
            </script>
            <script type="text/javascript">
            $(document).ready(function(){
                
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
                
                function modalSetting(selector, keyboard, show, backdrop){
                    $(selector).modal({
                        show : keyboard,
                        backdrop : backdrop,
                        keyboard : keyboard
                    });
                }
                
                var height = $(window).height() - 200; //value corresponding to the modal heading + footer
                $(".active-scroll").css({"maxheight":height,"overflow-y":"auto"});
                
                var datePicker = function(contentId, formatDate){
                    $(contentId).datepicker({
                 format : formatDate
                    });
                    $(contentId).on('changeDate', function(ev){
                 $(this).datepicker('hide');
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
                modalSetting("#myModalSm", false, false, 'static');
                viewHistory(".viewhistory");
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
                
                
                function loadFamily(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listfamily";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditFam(".addeditdata");
                           deleteDataFam(".deletedata");
                           
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/family_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                /*$('.tabFam').click(function() {
                    loadFamily("#listFam");
                    
                });*/
                
                function deleteDataFam(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadFamily("#listFam");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/family_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                function addEditFam(selector){
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
			if(dataFor == 'showfamform'){
			    $(".addeditgeneral-title").html("Edit Family Member");
			}

                        }else{
                            if(dataFor == 'showfamform'){
                                $(".addeditgeneral-title").html("Add Family Member");
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
                           
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalSm").modal("show");
                       $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/family_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                
                $('.tabFam').click(function() {
                    loadFamily("#listFam");
                    modalSetting("#myModalSm", false, false, 'static');
                    $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalSm").modal("hide");
                        loadFamily("#listFam");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/family_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    });
                });
                
                function loadCompetency(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listcompetency";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditComp(".addeditdatacomp");
                           deleteDataComp(".deletedatacomp")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/competency_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function deleteDataComp(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadCompetency("#listCom");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/competency_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                
                function addEditComp(selector){
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
			if(dataFor == 'showcompetencyform'){
			    $(".addeditgeneral-title").html("Edit Competency");
			}

                        }else{
                            if(dataFor == 'showcompetencyform'){
                                $(".addeditgeneral-title").html("Add Competency");
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
                           
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalSm").modal("show");
                       $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/competency_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                
                $('.tabCom').click(function() {
                    loadCompetency("#listCom");
                    modalSetting("#myModalSm", false, false, 'static');
                    $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalSm").modal("hide");
                        loadCompetency("#listCom");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/competency_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    });
                });
                
                //Language
                function loadLanguage(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listlanguage";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditLang(".addeditdatalang");
                           deleteDataLang(".deletedatalang")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/emplang_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function deleteDataLang(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadLanguage("#listLang");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/emplang_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                function addEditLang(selector){
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
			if(dataFor == 'showlanguageform'){
			    $(".addeditgeneral-title").html("Edit Employee Language");
			}

                        }else{
                            if(dataFor == 'showlanguageform'){
                                $(".addeditgeneral-title").html("Add Employee Language");
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
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalSm").modal("show");
                       $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/emplang_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                $('.tabLang').click(function() {
                    loadLanguage("#listLang");
                    modalSetting("#myModalSm", false, false, 'static');
                    $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalSm").modal("hide");
                        loadLanguage("#listLang");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/emplang_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    });
                });
                
                //Experience
                function loadExperience(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listexperience";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditExp(".addeditdataexp");
                           deleteDataExp(".deletedataexp")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/experience_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function deleteDataExp(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadExperience("#listExp");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/experience_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                function addEditExp(selector){
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
			if(dataFor == 'showexpform'){
			    $(".addeditgeneral-title").html("Edit Employee Experience");
			}

                        }else{
                            if(dataFor == 'showexpform'){
                                $(".addeditgeneral-title").html("Add Employee Experience");
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
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalSm").modal("show");
                       $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/experience_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                $('.tabExp').click(function() {
                    loadExperience("#listExp");
                    modalSetting("#myModalSm", false, false, 'static');
                    $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalSm").modal("hide");
                        loadExperience("#listExp");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/experience_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    });
                });
                
                //Education
                function loadEducation(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listeducation";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditEdu(".addeditdataedu");
                           deleteDataEdu(".deletedataedu")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/empeducation_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function deleteDataEdu(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadEducation("#listEdu");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/empeducation_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                function addEditEdu(selector){
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
			if(dataFor == 'showeducationform'){
			    $(".addeditgeneral-title").html("Edit Employee Education");
			}

                        }else{
                            if(dataFor == 'showeducationform'){
                                $(".addeditgeneral-title").html("Add Employee Education");
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
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalSm").modal("show");
                       $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/empeducation_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                $('.tabEdu').click(function() {
                    loadEducation("#listEdu");
                    modalSetting("#myModalSm", false, false, 'static');
                    $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalSm").modal("hide");
                        loadEducation("#listEdu");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/empeducation_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    });
                });
                
                
                function uploadTrigger(){
                $("#uploadtrigger").click(function(){
                    $("#FRM_ICON").trigger('click');
                });
                
                $("#FRM_ICON").change(function(){
                    var icon = $("#FRM_ICON").val();
                    $("#tempname").val(icon);
                });
                
                $("#tempname").click(function(){
                    $("#FRM_ICON").trigger('click');
                });
            }
                
                //Award
                function loadAward(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listaward";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditAward(".addeditdataaward");
                           deleteDataAward(".deletedataaward")
                           upload(".uploaddata");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/award_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function deleteDataAward(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadAward("#listAward");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/award_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                function addEditAward(selector){
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
			if(dataFor == 'showawardform'){
			    $(".addeditgeneral-title").html("Edit Employee Award");
			}

                        }else{
                            if(dataFor == 'showawardform'){
                                $(".addeditgeneral-title").html("Add Employee Award");
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
                            $("#division").chained("#company");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalSm").modal("show");
                       $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/award_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                
                function upload(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var empId = $(this).data("empid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#formupload #oid").val(oid);
                       $("#formupload #empId").val(empId);
                       $("#formupload #datafor").val(dataFor);
                       $("#formupload #command").val(command);
                       
                        $(".addeditgeneral-title").html("Upload Document");
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : command
                       };
                       var onDone1 = function(data){
                           $("#modalbodyupload").html(data).fadeIn("medium");
                           uploadTrigger("#uploadtrigger");
                           changeFileInput("#fileupload");
                       };
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#modalUpload").modal("show");
                       $("#modalbodyupload").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       if(dataFor == 'showupload'){
			    $("#formupload").attr("action","<%=approot%>/employee/databank/employee_edit.jsp?FRM_FIELD_OID="+oid+"&oid="+empId+"&command="+command+"&modul=award");
                            sendData("<%= approot %>/employee/databank/ajax/award_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    	} else if(dataFor == 'showuploadrep'){
                            $("#formupload").attr("action","<%=approot%>/employee/databank/employee_edit.jsp?FRM_FIELD_OID="+oid+"&oid="+empId+"&command="+command+"&modul=reprimand");
                            sendData("<%= approot %>/employee/databank/ajax/reprimand_ajax.jsp", dataSend1, onDone1, onSuccess1);
                        }
                       
                       });
                }
                
                
                
                var uploadTrigger = function(elementId){
                    $(elementId).click(function(){
                        $("#fileupload").trigger("click");
                    });
                };
                
                var changeFileInput = function(elementId){
                    $(elementId).change(function(){
                        var imageName = $("#fileupload").val();
                        $("#tempname").val(imageName);
                    });
                };
                
                
                $('.tabAward').click(function() {
                    loadAward("#listAward");
                    modalSetting("#myModalSm", false, false, 'static');
                    $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalSm").modal("hide");
                        loadAward("#listAward");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/award_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    });
                });
                
               
               //Career Path
                function loadCareer(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listcareer";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditCareer(".addeditdatacareer");
                           deleteDataCareer(".deletedatacareer");
                           doResign(".doresign")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/cpath_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function deleteDataCareer(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadCareer("#listCareer");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/cpath_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                function addEditCareer(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var empId = '<%=employee.getOID()%>';
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid").val(oid);
                       $("#empId").val(empId);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       if(oid != 0){
			if(dataFor == 'showawardform'){
			    $(".addeditgeneral-title").html("Edit Employee Award");
			}

                        }else{
                            if(dataFor == 'showawardform'){
                                $(".addeditgeneral-title").html("Add Employee Award");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody").html(data).fadeIn("medium");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal").modal("show");
                       $("#modalbody").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/cpath_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                
                function doResign(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var empId = '<%=employee.getOID()%>';
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oidSm").val(oid);
                       $("#empIdSm").val(empId);
                       $("#dataforSm").val(dataFor);
                       $("#commandSm").val("4");
                       
                       if(dataFor == 'doresignform'){
			    $(".addeditgeneral-title").html("Do Resign");
                       }
                       var dataSend1 = {
                           "oid" : oid,
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody2").html(data).fadeIn("medium");
                            datePicker(".datepicker","yyyy-mm-dd");
                           
                        }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalSm").modal("show");
                       $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/do_resign.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                
                $('.tabCareer').click(function() {
                    loadCareer("#listCareer");
                    
                });
                
                modalSetting("#myModalSm", false, false, 'static');
                $("#myModalSm form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalSm").modal("hide");
                        loadCareer("#listCareer");
                        
                    }
                    var onSuccess = function(data){
                    }
                    //get data for
                    var dataForDo = $("#dataforSm").val();
                    var ajaxPath = "";
                    if(dataForDo == "doresignform"){
                        ajaxPath = "/employee/databank/ajax/do_resign.jsp";
                    }
                    sendData("<%= approot %>"+ajaxPath, dataSend, onDone, onSuccess);
                    return false;
                });
                //Training
                function loadTraining(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listtraining";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditTraining(".addeditdatatraining");
                           deleteDataTraining(".deletedatatraining")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/training_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function deleteDataTraining(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadTraining("#listTraining");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/training_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                function addEditTraining(selector){
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
			if(dataFor == 'showtrainingform'){
			    $(".addeditgeneral-title").html("Edit Employee Training");
			}

                        }else{
                            if(dataFor == 'showtrainingform'){
                                $(".addeditgeneral-title").html("Add Employee Training");
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
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalSm").modal("show");
                       $("#modalbody2").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/training_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                $('.tabTraining').click(function() {
                    loadTraining("#listTraining");
                    modalSetting("#myModalSm", false, false, 'static');
                    $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalSm").modal("hide");
                        loadTraining("#listTraining");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/training_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    });
                });
                
                //Reprimand
                function loadReprimand(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listreprimand";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditReprimand(".addeditdatareprimand");
                           deleteDataReprimand(".deletedatareprimand")
                           upload(".uploaddata");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/reprimand_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function deleteDataReprimand(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadReprimand("#listReprimand");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/reprimand_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                
                function addEditReprimand(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var empId = '<%=employee.getOID()%>';
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid").val(oid);
                       $("#empId").val(empId);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       if(oid != 0){
			if(dataFor == 'showrepform'){
			    $(".addeditgeneral-title").html("Edit Employee Reprimand");
			}

                        }else{
                            if(dataFor == 'showrepform'){
                                $(".addeditgeneral-title").html("Add Employee Reprimand");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody1").html(data).fadeIn("medium");
                           $("#division").chained("#company");
                           $("#department").chained("#division");
                           $("#section").chained("#department");
                           $("#pasal").chained("#bab");
                           $("#ayat").chained("#pasal");
                           datePicker(".datepicker","yyyy-mm-dd");
                           
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalLg").modal("show");
                       $("#modalbody1").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/reprimand_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                $('.tabReprimand').click(function() {
                    loadReprimand("#listReprimand");
                    modalSetting("#myModalLg", false, false, 'static');
                    $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalLg").modal("hide");
                        loadReprimand("#listReprimand");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/reprimand_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    });
                });
                
                //Warning
                function loadWarning(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listwarning";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEditWarning(".addeditdatawarning");
                           deleteDataWarning(".deletedatawarning")
                           
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/warning_ajax.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function loadEmployee(selector){
                    
                       var empId = '<%= employee.getOID()%>';
                       var dataFor = "listemployee";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           }
                           $('#listemployee').DataTable( {
                                
                            } );
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/search_employee.jsp", dataSend, onDone, onSuccess);
               
                }
                
                function deleteDataWarning(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadWarning("#listWarning");
                           
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/warning_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                function addEditWarning(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var empId = '<%=employee.getOID()%>';
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid").val(oid);
                       $("#empId").val(empId);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       if(oid != 0){
			if(dataFor == 'showwarnform'){
			    $(".addeditgeneral-title").html("Edit Employee Warning");
			}

                        }else{
                            if(dataFor == 'showwarnform'){
                                $(".addeditgeneral-title").html("Add Employee Warning");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "empId" : empId,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody1").html(data).fadeIn("medium");
                           searchEmp(".searchEmployee");
                           $("#division").chained("#company");
                           $("#department").chained("#division");
                           $("#section").chained("#department");
                           datePicker(".datepicker","yyyy-mm-dd");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModalLg").modal("show");
                       $("#modalbody1").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/employee/databank/ajax/warning_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                
                function searchEmp(selector){
                    $(selector).click(function(){
                       var onDone1 = function(data){
                           $("#modalbody2").html(data).fadeIn("medium");
                           }
                           loadEmployee("#listEmployee");
                           
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal2").modal("show");
                       });
                }
                
                
                $('.tabWarning').click(function() {
                    loadWarning("#listWarning");
                    modalSetting("#myModalLg", false, false, 'static');
                    $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModalLg").modal("hide");
                        loadWarning("#listWarning");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/warning_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    });
                });
                
                function addEditGeo(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var empId = '<%=employee.getOID()%>';
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oidS").val(oid);
                       $("#empIdSm").val(empId);
                       $("#dataforSm").val(dataFor);
                       $("#commandSm").val("4");
                       
                       if(oid != 0){
			if(dataFor == 'showwarnform'){
			    $(".addeditgeneral-title").html("Edit Employee Warning");
			}

                        }else{
                            if(dataFor == 'showwarnform'){
                                $(".addeditgeneral-title").html("Add Employee Warning");
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
                        var dataFor = $("#datafor").val();
                        
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
                    var dataFor = $("#datafor").val();
                    if(dataFor == "geoaddresspmnt"){
                        $("#geo_address_pmnt").val($("#hiddenvalue").val());
                    }else{
                        $("#geo_address").val($("#hiddenvalue").val());
                    }
                    $("#myModalSm").modal("hide");
                    return false;
                })
                
                function doMutation(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var empId = '<%=employee.getOID()%>';
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid").val(oid);
                       $("#empId").val(empId);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       $(".addeditgeneral-title").html("Do Mutation");
		       
                       var dataSend1 = {
                           "oid" : oid,
                           "empId" : empId,
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
                       
                       sendData("<%= approot %>/employee/databank/ajax/mutation.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                
                $("#do_mutation").click(function(){
                    doMutation(".domutation");
                });
                
               
                
            });
        </script>
            <script>
                function pageLoad(){ loadCompany('<%=employee.getOID()%>'); loadWaCompany('<%=employee.getOID()%>'); }  
            </script>
    </body>
</html>
