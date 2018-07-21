<%-- 
    Class Name   : template.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 10:59:14 PM
    Function     : This is template
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmEmpDoc"%>
<%@page import="com.dimata.harisma.form.admin.FrmFingerPatern"%>
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
        em.setBarcodeNumber(barNum);
        try{
            PstEmployee.updateExc(em);
        }catch(Exception ss){
            
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

        Vector listFingerPatern = new Vector(1,1);
        String whereFinger ="";
        String order ="";
        Hashtable<Integer, Boolean> fingerType = new Hashtable<Integer, Boolean>();
        whereFinger = " "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID]+"="+oidEmployee;
        order =" "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+" ";
        listFingerPatern = PstFingerPatern.list(0, 0, whereFinger, order);
        if (listFingerPatern.size()>0){
            for (int i = 0; i<listFingerPatern.size();i++){
                FingerPatern fingerPatern = (FingerPatern)listFingerPatern.get(i);
                fingerType.put(fingerPatern.getFingerType(), true);
            }
        }        
        
        String urlC = request.getRequestURL().toString();
        String baseURL = urlC.substring(0, urlC.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Employee Edit | Dimata Hairisma</title>
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
              .finger{
                width:20%; 
                height:90px;
                padding : 2%;
                float:left;
             }
            .finger_spot{
                width:90%;
                height: 80px;
                background-color :#e5e5e5;
                border : thin solid #c5c5c5;
                font-size: 14px;
                font-family:calibri;
                text-align:center;
                color :#FFF;
                border-radius: 3px;
            }

            .green{
               background-color : #5CB85C;
               border : thin solid #4CAE4C;
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
                }#contract_element_parent{
                    overflow-x: auto;
                }#career_element_parent{
                    overflow-x: auto;
                }
                .modal-dialog {
                    width: 100%;
                }
            }
            @media screen and ( max-width: 960px ) {
            img#photoprofile { width: 110px; }
            img#photoprofile { height: auto; }
            }
        </style>
        <script language="JavaScript">
             function cmdUpPictCam(oid){
                window.open("<%=approot%>/employee/databank/employee_cam.jsp?employee_oid="+oid, "Up Pict","height=500,width=500, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes").focus();
            }
            function cmdUpPict(oid){
                window.open("<%=approot%>/employee/databank/up_picture.jsp?employee_oid="+oid, "Up Pict","height=500,width=500, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes").focus();
            }
            function cmdSendEmail(relevanDocId, empId){
		  window.open("editPenerimaRelevantDoc.jsp?relevanDocId="+relevanDocId+"&empId="+empId, "Search_HOD",  "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
            }
        </script>
        <%@ include file="../../template/css.jsp" %>
        <script src="../../styles/ckeditor/ckeditor.js"></script>
    </head>

    <body class="hold-transition skin-blue fixed sidebar-mini sidebar-collapse" >
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
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
                                                        </a> <% if (employee.getOID() != 0) { %>
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
                                                        </ul> <% } %>
                                                    </li> <% if (employee.getOID() != 0) { %>
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
                                                    <li class="dropdown">
                                                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                                            Document <span class="caret"></span>
                                                        </a>
                                                        <ul class="dropdown-menu">
                                                            <li role="presentation"><a class="tabRelevantDoc" role="menuitem" tabindex="-1" href="#tab_relevant" data-toggle="tab">Relevant Document</a></li>
                                                            <li role="presentation" class="divider"></li>
                                                            <li role="presentation"><a class="tabGeneralDoc" role="menuitem" tabindex="-1" href="#tab_general" data-toggle="tab">General Document</a></li>
                                                        </ul>
                                                    </li>
                                                    <li><a class="tabAsset" href="#tab_assets" data-toggle="tab">Assets & Inventory</a></li>
                                                    <li><a class="tabFinger" href="#tab_setting" data-toggle="tab">Setting</a></li>
                                                    <% } %>
                                                </ul>
                                                <div class="tab-content">
                                                    <div class="tab-pane active" id="tab_personal">
                                                        <form name="frm_employee" method="post" action="" id="frm_employee">
                                                        <input type="hidden" name="command" value="">
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
                                                                                    out.println("<img height=\"135\" img width=\"135\" id=\"photoprofile\" title=\"Click here to upload\"  src=\"" + approot + "/" + pictPath + "\">");
                                                                                 } else {
                                                                            %>
                                                                            <img width="135" height="135" id="photoprofile" src="<%=approot%>/imgcache/no-img.jpg" />
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
                                                                        <label>Full Name</label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_FULL_NAME)%> </font> 
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME]%>" value="<%=employee.getFullName()%>" class="form-control" placeholder="Input Employee Name">
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Temporary Address</label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_ADDRESS)%> </font>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS]%>" size="50" value="<%=employee.getAddress()%>" class="form-control" placeholder="Input Temporary address">
                                                                        <input class="form-control addeditgeo"  type="text" name="geo_address" readonly="true" value="<%=employee.getGeoAddress()%>" size="50" id="geo_address"  data-for="geoaddress" placeholder="Click Here For Geo Address">
                                                                        
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_COUNTRY_ID]%>" value="<%="" + employee.getAddrCountryId()%>" id="oidnegara">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PROVINCE_ID]%>" value="<%="" + employee.getAddrProvinceId()%>" id="oidprovinsi">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_REGENCY_ID]%>" value="<%="" + employee.getAddrRegencyId()%>" id="oidkabupaten">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_SUBREGENCY_ID]%>" value="<%="" + employee.getAddrSubRegencyId()%>" id="oidkecamatan">
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Permanent Address</label>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_PERMANENT]%>" size="50" value="<%=(employee.getAddressPermanent() != null ? employee.getAddressPermanent() : "")%>" class="form-control" placeholder="Input Permananet address">
                                                                        <input class="form-control addeditgeo"  type="text" name="geo_address_pmnt" id="geo_address_pmnt" readonly="true" size="50" value="<%=employee.getGeoAddressPmnt()%>" data-for="geoaddresspmnt" placeholder="Click Here For Geo Address">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_COUNTRY_ID]%>" value="<%="" + employee.getAddrPmntCountryId()%>" id="oidnegarapmnt">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_PROVINCE_ID]%>" value="<%="" + employee.getAddrPmntProvinceId()%>" id="oidprovinsipmnt">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_REGENCY_ID]%>" value="<%="" + employee.getAddrPmntRegencyId()%>" id="oidkabupatenpmnt">
                                                                        <input type="hidden" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_SUBREGENCY_ID]%>" value="<%="" + employee.getAddrPmntSubRegencyId()%>" id="oidkecamatanpmnt">        
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Zip Code</label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_POSTAL_CODE)%> </font>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSTAL_CODE]%>" value="<%=employee.getPostalCode() == 0 ? "" : "" + employee.getPostalCode()%>" class="form-control" placeholder="Input Zip/Kode Pos">
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Telephone / Handphone</label>
                                                                        <div class="input-group">
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PHONE]%>" value="<%=(employee.getPhone() != null ? employee.getPhone() : "")%>" class="form-control" placeholder="Input Phone Number">
                                                                        <span class="input-group-addon">/</span>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_HANDPHONE]%>" value="<%=employee.getHandphone()%>" class="form-control"  placeholder="Input HandPhone Number">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Emergency Phone / Person Name</label>
                                                                        <div class="input-group">
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PHONE_EMERGENCY]%>" value="<%=(employee.getPhoneEmergency() != null ? employee.getPhoneEmergency() : "")%>" class="form-control" placeholder="Input Telephone Number Emergency">
                                                                        <span class="input-group-addon">/</span>
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NAME_EMG]%>" value="<%=(employee.getNameEmg() != null ? employee.getNameEmg() : "")%>" class="form-control" placeholder="Input Emergency Person Name">
                                                                        </div>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Emergency Address</label>
                                                                        <textarea class="form-control" rows="2" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_EMG]%>"  placeholder="Input Emergency Address"><%=employee.getAddressEmg()%></textarea>
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Gender &nbsp</label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_SEX)%> </font><br>
                                                                        <% for (int i = 0; i < PstEmployee.sexValue.length; i++) {
                                                                            String str = "";
                                                                            if (employee.getSex() == PstEmployee.sexValue[i]) {
                                                                                str = "checked";
                                                                            }
                                                                        %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SEX]%>" value="<%="" + PstEmployee.sexValue[i]%>" <%=str%> style="border:none">
                                                                        <%=PstEmployee.sexKey[i]%> <% }%>
                                                                        
                                                                        <br />
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Place of Birth</label> *<font color="red"> <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_BIRTH_PLACE)%> </font>  
                                                                        <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_PLACE]%>" value="<%=employee.getBirthPlace()%>" placeholder="Input Birth Pleace" class="form-control" >
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
                                                                        <input type="text" disabled="disable" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SHIO]%>" value="<%=employee.getShio()%>" placeholder="automatic" class="form-control"> 
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                        <label>Element </label> * Auto
                                                                        <input type="text" disabled="disable" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ELEMEN]%>" value="<%=employee.getElemen()%>" placeholder="automatic" class="form-control">
                                                                        </div>
                                                                        
                                                                        <div class="form-group">
                                                                            <label>Religion</label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_RELIGION_ID)%></font> 
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RELIGION_ID]%>">
                                                                        <option value="0">Select Religion...</option>
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
                                                                            <label>Marital Status for HR</label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_MARITAL_ID)%></font>      
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MARITAL_ID]%>">
                                                                        <option value="0">Select Marital Status for HR...</option>
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
                                                                            <label>Marital Status for Tax Report</label> * (Set per 1 January conform to Tax Regulation )  <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_TAX_MARITAL_ID)%></font>  
                                                                        <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_TAX_MARITAL_ID]%>">
                                                                        <option value="0">Select Marital Status for Tax...</option>
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
                                                                        <option value="0">Select Race...</option>
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
                                                                        
                                                                        <label>Barcode Number</label>  * <font color="red"><%=frmEmployee.getErrorMsgModif(FrmEmployee.FRM_FIELD_BARCODE_NUMBER)%>
                                                                        <input  type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BARCODE_NUMBER]%>" title=" If Employe is a Daily Worker (DL)  please replace 'DL-' with '12' ,for  example 'DL-333' become to '12333'.     If Employe's  Status  'Resigned'  please input the barcode number, barcode number is unique for example -R(BarcodeNumb/PinNumber)" value="<%=(employee.getBarcodeNumber() != null ? employee.getBarcodeNumber() : "")%>" placeholder="Input Barcode" class="form-control"></font>
                                                                    
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
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_INDENT_CARD_NR]%>" value="<%=employee.getIndentCardNr()%>" placeholder="Type Card Id Number" class="form-control">
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
                                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECONDARY_ID_NO]%>" value="<%=employee.getSecondaryIdNo()%>" placeholder="Type Card Id Number" class="form-control">
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
                                                                        <input type="text" placeholder="type value"  name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_IQ]%>" value="<%=(employee.getIq() != null ? employee.getIq() : "")%>"  class="form-control">
                                                                    
                                                                        <label>EQ</label>
                                                                        <input type="text" placeholder="type value" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EQ]%>" value="<%=(employee.getEq() != null ? employee.getEq() : "")%>" class="form-control">
                                                                        
                                                                        
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
                                                                        <input type="text" placeholder="Type Rekening Number" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NO_REKENING]%>" class="form-control" value="<%=employee.getNoRekening()%>" />
                                                                       
                                                                        <label>NPWP</label>
                                                                        <input type="text" placeholder="Type NPWP Number" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NPWP]%>" value="<%=(employee.getNpwp() != null ? employee.getNpwp() : "")%>" class="form-control">
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
                                                                        <div class="form-group">
                                                                        <label>
                                                                            Company 
                                                                        </label> *  <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_COMPANY_ID)%></font>
                                                                            <select class="form-control"  name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMPANY_ID]%>" id="company" data-target="#positionSelect">
                                                                                <option value="">Select Company..</option>
                                                                                <%
                                                                                Vector listCompany = PstCompany.list(0, 0, "", PstCompany.fieldNames[PstCompany.FLD_COMPANY]);
                                                                                if (listCompany != null && listCompany.size()>0){
                                                                                    for(int i=0; i<listCompany.size(); i++){
                                                                                        Company comp = (Company)listCompany.get(i);
                                                                                        if (employee.getCompanyId() == comp.getOID()){
                                                                                            %>
                                                                                            <option selected="selected" value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                                                                                            <%
                                                                                        } else {
                                                                                            %>
                                                                                            <option value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                                                                                            <%
                                                                                        }
                                                                                    }
                                                                                }
                                                                                %>
                                                                            </select>
                                                                        </div>
                                                                        <div class="form-group">    
                                                                        <label>
                                                                           Division
                                                                        </label> *  <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_DIVISION_ID)%></font>
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID]%>" id="division" data-target="#positionSelect">
                                                                                <option value="">Select Division...</option>
                                                                                <%
                                                                                    Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                                                                                    if (listDivision != null && listDivision.size()>0){
                                                                                        for(int i=0; i<listDivision.size(); i++){
                                                                                            Division divisi = (Division)listDivision.get(i);
                                                                                            if (employee.getDivisionId() == divisi.getOID()){
                                                                                                %><option selected="selected" value="<%=divisi.getOID()%>" class="<%=divisi.getCompanyId()%>"><%=divisi.getDivision()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=divisi.getOID()%>" class="<%=divisi.getCompanyId()%>"><%=divisi.getDivision()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </div>

                                                                        <label>
                                                                            Department
                                                                        </label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_DEPARTMENT_ID)%></font>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DEPARTMENT_ID]%>" id="department" data-target="#positionSelect">
                                                                                <option value="">Select Department...</option>
                                                                                <%
                                                                                    Vector listDepart = PstDepartment.listAll();
                                                                                    if (listDepart != null && listDepart.size()>0){
                                                                                        for(int i=0; i<listDepart.size(); i++){
                                                                                            Department depart = (Department)listDepart.get(i);
                                                                                            if (employee.getDepartmentId() == depart.getOID()){
                                                                                                %><option selected="selected" value="<%=depart.getOID()%>" class="<%=depart.getDivisionId()%>"><%=depart.getDepartment()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=depart.getOID()%>" class="<%=depart.getDivisionId()%>"><%=depart.getDepartment()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </div>

                                                                        <label>
                                                                            Section
                                                                        </label>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECTION_ID]%>" id="section" data-target="#positionSelect">
                                                                                <option value="">Select Section...</option>
                                                                                <%
                                                                                    Vector listSection = PstSection.list(0, 0, "", PstSection.fieldNames[PstSection.FLD_SECTION]);

                                                                                    if (listSection != null && listSection.size()>0){
                                                                                        for(int i=0; i<listSection.size(); i++){
                                                                                            Section section = (Section)listSection.get(i);
                                                                                            if (employee.getSectionId() == section.getOID()){
                                                                                                %><option selected="selected" value="<%=section.getOID()%>" class="<%=section.getDepartmentId()%>"><%=section.getSection()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=section.getOID()%>" class="<%=section.getDepartmentId()%>"><%=section.getSection()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </div>
                                                                        <label>
                                                                            Sub Section
                                                                        </label>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SUB_SECTION_ID]%>" id="subSection" data-target="#positionSelect">
                                                                                <option value="">Select Sub Section...</option>
                                                                                <%
                                                                                    Vector listSubSection = PstSubSection.list(0, 0, "", PstSubSection.fieldNames[PstSubSection.FLD_SUB_SECTION]);

                                                                                    if (listSubSection != null && listSubSection.size()>0){
                                                                                        for(int i=0; i<listSubSection.size(); i++){
                                                                                            SubSection subSection = (SubSection)listSubSection.get(i);
                                                                                            if (employee.getSubSectionId() == subSection.getOID()){
                                                                                                %><option selected="selected" value="<%=subSection.getOID()%>" class="<%=subSection.getSectionId()%>"><%=subSection.getSubSection()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=subSection.getOID()%>" class="<%=subSection.getSectionId()%>"><%=subSection.getSubSection()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </div>    
                                                                        <label>
                                                                            Employee Category
                                                                        </label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID)%></font>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID]%>">
                                                                                <option value="0">Select Category...</option>
                                                                                <%
                                                                                    Vector listCategory = PstEmpCategory.list(0, 0, "", PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]);

                                                                                    if (listCategory != null && listCategory.size()>0){
                                                                                        for(int i=0; i<listCategory.size(); i++){
                                                                                            EmpCategory empCategory = (EmpCategory)listCategory.get(i);
                                                                                            if (employee.getEmpCategoryId() == empCategory.getOID()){
                                                                                                %><option selected="selected" value="<%=empCategory.getOID()%>"><%=empCategory.getEmpCategory()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=empCategory.getOID()%>"><%=empCategory.getEmpCategory()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }


                                                                                %>
                                                                            </select>
                                                                        </div>
                                                                        <div class="form-group">
                                                                        <label>
                                                                            Level
                                                                        </label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_LEVEL_ID)%></font>
                                                                        <div class="input-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LEVEL_ID]%>">
                                                                                <option value="0">Select Level...</option>
                                                                                <%
                                                                                    Vector listLevel = PstLevel.list(0, 0, "", PstLevel.fieldNames[PstLevel.FLD_LEVEL]);

                                                                                    if (listLevel != null && listLevel.size()>0){
                                                                                        for(int i=0; i<listLevel.size(); i++){
                                                                                            Level level = (Level)listLevel.get(i);
                                                                                            if (employee.getLevelId() == level.getOID()){
                                                                                                %><option selected="selected" value="<%=level.getOID()%>"><%=level.getLevel()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=level.getOID()%>"><%=level.getLevel()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }


                                                                                %>
                                                                            </select>
                                                                            <span class="input-group-addon">-</span>
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_GRADE_LEVEL_ID]%>">
                                                                                <option value="0">Select Grade...</option>
                                                                                <%
                                                                                    Vector listGrade = PstGradeLevel.list(0, 0, "", PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_CODE]);

                                                                                    if (listGrade != null && listGrade.size()>0){
                                                                                        for(int i=0; i<listGrade.size(); i++){
                                                                                            GradeLevel gradeLevel = (GradeLevel)listGrade.get(i);
                                                                                            if (employee.getGradeLevelId() == gradeLevel.getOID()){
                                                                                                %><option selected="selected" value="<%=gradeLevel.getOID()%>"><%=gradeLevel.getCodeLevel()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=gradeLevel.getOID()%>"><%=gradeLevel.getCodeLevel()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }


                                                                                %>
                                                                            </select>
                                                                        </div>
                                                                        </div>    
                                                                        <label>
                                                                            Position
                                                                        </label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_POSITION_ID)%></font>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSITION_ID]%>" id="positionSelect">
                                                                                <option value="">Select Position...</option>
                                                                                <%
                                                                                    Vector listPosition = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);

                                                                                    if (listPosition != null && listPosition.size()>0){
                                                                                        for(int i=0; i<listPosition.size(); i++){
                                                                                            Position position = (Position)listPosition.get(i);
                                                                                            if (employee.getPositionId() == position.getOID()){
                                                                                                %><option selected="selected" value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
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
                                                                            <select class="form-control"  name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_COMPANY_ID]%>" id="companyWa">
                                                                                <option value="0">Select Work Assign Company...</option>
                                                                                <%
                                                                                Vector listWaCompany = PstCompany.list(0, 0, "", PstCompany.fieldNames[PstCompany.FLD_COMPANY]);
                                                                                if (listWaCompany != null && listWaCompany.size()>0){
                                                                                    for(int i=0; i<listWaCompany.size(); i++){
                                                                                        Company comp = (Company)listWaCompany.get(i);
                                                                                        if (employee.getWorkassigncompanyId() == comp.getOID()){
                                                                                            %>
                                                                                            <option selected="selected" value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                                                                                            <%
                                                                                        } else {
                                                                                            %>
                                                                                            <option value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                                                                                            <%
                                                                                        }
                                                                                    }
                                                                                }
                                                                                %>
                                                                            </select>
                                                                        </div>
                                                                        <div class="form-group">    
                                                                        <label>
                                                                           W.A. Division
                                                                        </label>
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DIVISION_ID]%>" id="divisionWa">
                                                                                <option value="">Select Work Assign Division...</option>
                                                                                <%
                                                                                    Vector listWaDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                                                                                    if (listWaDivision != null && listWaDivision.size()>0){
                                                                                        for(int i=0; i<listWaDivision.size(); i++){
                                                                                            Division divisi = (Division)listWaDivision.get(i);
                                                                                            if (employee.getWorkassigndivisionId() == divisi.getOID()){
                                                                                                %><option selected="selected" value="<%=divisi.getOID()%>" class="<%=divisi.getCompanyId()%>"><%=divisi.getDivision()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=divisi.getOID()%>" class="<%=divisi.getCompanyId()%>"><%=divisi.getDivision()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </div>

                                                                        <label>
                                                                            W.A. Department
                                                                        </label>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DEPARTMENT_ID]%>" id="departmentWa">
                                                                                <option value="">Select Work Assign Department...</option>
                                                                                <%
                                                                                    Vector listWaDepart = PstDepartment.listAll();
                                                                                    if (listWaDepart != null && listWaDepart.size()>0){
                                                                                        for(int i=0; i<listWaDepart.size(); i++){
                                                                                            Department depart = (Department)listWaDepart.get(i);
                                                                                            if (employee.getWorkassigndepartmentId() == depart.getOID()){
                                                                                                %><option selected="selected" value="<%=depart.getOID()%>" class="<%=depart.getDivisionId()%>"><%=depart.getDepartment()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=depart.getOID()%>" class="<%=depart.getDivisionId()%>"><%=depart.getDepartment()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </div>

                                                                        <label>
                                                                            W.A. Section
                                                                        </label>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_SECTION_ID]%>" id="sectionWa">
                                                                                <option value="">Select Work Assign Section...</option>
                                                                                <%
                                                                                    String whereSection = "";
                                                                                    Vector listWaSection = PstSection.list(0, 0, whereSection, PstSection.fieldNames[PstSection.FLD_SECTION]);

                                                                                    if (listWaSection != null && listWaSection.size()>0){
                                                                                        for(int i=0; i<listWaSection.size(); i++){
                                                                                            Section section = (Section)listWaSection.get(i);
                                                                                            if (employee.getWorkassignsectionId() == section.getOID()){
                                                                                                %><option selected="selected" value="<%=section.getOID()%>" class="<%=section.getDepartmentId()%>"><%=section.getSection()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=section.getOID()%>" class="<%=section.getDepartmentId()%>"><%=section.getSection()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </div>
                                                                        
                                                                        <label>
                                                                            W.A Sub Section
                                                                        </label>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WA_SUB_SECTION_ID]%>" id="subSectionWa">
                                                                                <option value="">Select Work Assign Sub Section...</option>
                                                                                <%
                                                                                    Vector listWaSubSection = PstSubSection.list(0, 0, "", PstSubSection.fieldNames[PstSubSection.FLD_SUB_SECTION]);

                                                                                    if (listWaSubSection != null && listWaSubSection.size()>0){
                                                                                        for(int i=0; i<listWaSubSection.size(); i++){
                                                                                            SubSection subSection = (SubSection)listWaSubSection.get(i);
                                                                                            if (employee.getWaSubSectionId() == subSection.getOID()){
                                                                                                %><option selected="selected" value="<%=subSection.getOID()%>" class="<%=subSection.getSectionId()%>"><%=subSection.getSubSection()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=subSection.getOID()%>" class="<%=subSection.getSectionId()%>"><%=subSection.getSubSection()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </div>        
                                                                            
                                                                        <label>
                                                                            W.A. Position
                                                                        </label>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_POSITION_ID]%>">
                                                                                <option value="0">Select Work Assign Position...</option>
                                                                                <%
                                                                                    Vector listWaPosition = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);

                                                                                    if (listWaPosition != null && listWaPosition.size()>0){
                                                                                        for(int i=0; i<listWaPosition.size(); i++){
                                                                                            Position position = (Position)listWaPosition.get(i);
                                                                                            if (employee.getWorkassignpositionId() == position.getOID()){
                                                                                                %><option selected="selected" value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=position.getOID()%>"><%=position.getPosition()%></option><%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </div>   
                                                                        <label>
                                                                            W.A. Reference
                                                                        </label>
                                                                        <div class="form-group">
                                                                            <select class="form-control" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PROVIDER_ID]%>">
                                                                                <option value="0">Select Work Assign Reference...</option>
                                                                                <%
                                                                                    Vector listProvider = PstContactList.list(0, 0, "", ""+ PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+","+ PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]);
                                                                                        for (int i = 0; i < listProvider.size(); i++) {
                                                                                            ContactList waContact = (ContactList) listProvider.get(i);
                                                                                            if (employee.getProviderID() == waContact.getOID()){
                                                                                                %><option selected="selected" value="<%=waContact.getOID()%>"><%=waContact.getCompName()%></option><%
                                                                                            } else {
                                                                                                %><option value="<%=waContact.getOID()%>"><%=waContact.getCompName()%></option><%
                                                                                            }
                                                                                        }
                                                                                %>
                                                                            </select>
                                                                        </div>
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
                                                                        <input type="text" autocomplete="off" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ASTEK_NUM]%>" value="<%=employee.getAstekNum()%>" placeholder="Input BPJS TK Number" class="form-control">
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
                                                                        <input type="text" autocomplete="off" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BPJS_NO]%>" value="<%=employee.getBpjs_no()!=null? employee.getBpjs_no():""%>" placeholder="Input BPJS Number" class="form-control">
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
                                                                        <label>Employee PIN</label> * <font color="red"><%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_PIN)%></font>&nbsp;
                                                                        <input type="password"  name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_PIN]%>" value="<%=(employee.getEmpPin() != null ? employee.getEmpPin() : "")%>" autocomplete="new-password" placeholder="Input Password For Login by Employee" class="form-control">
                                                                         
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
                                                               //out.println( FrmEmployee.fieldNamesForUser[idx]+" = "+ "<font color='red'>"+frmEmployee.getErrorMsg(idx)+"</font>;");
                                                             }
                                                         }%>
                                                         <br> 
                                                         <div class="alert alert-danger col-md-6" role="alert" id="error_msg" tabindex="-1">
                                                            <strong><%=errMsg%></strong>
                                                         </div>
                                                          <%
                                                           }
                                                         %>
                                                        
                                                        </div>
                                                        
                                                        
                                                         
                                                    </div><!-- /.tab-pane -->
                                                    <div class="tab-pane" id="tab_family"  >
                                                        <a> <h3>Family Member List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>
                                                            <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showfamilymemberform">
                                                                <i class="fa fa-plus"></i> Add Family Member
                                                            </button>
                                                            <button button type="button" class="btn btn-danger btndeletefamilymember" data-for="showfamilymemberform">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="familyMemberElement">
                                                            <table class="table table-bordered display nowrap" width="100%">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 2%" ></th>
                                                                        <th style="width: 3%" >No</th>
                                                                        <th style="width: 10%" >Full Name / Sex</th>
                                                                        <th style="width: 10%" >Relationship</th>
                                                                        <th style="width: 5%" >Guaranted</th>
                                                                        <th style="width: 10%" >Date of Birth</th>
                                                                        <th style="width: 10%" >Job / Address</th>
                                                                        <th style="width: 10%" >ID KTP</th>
                                                                        <th style="width: 10%" >Education / Religion</th>
                                                                        <th style="width: 10%" >Phone Number</th>
                                                                        <th style="width: 10%" >BPJS Number</th>
                                                                        <th style="width: 15%" >Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        
                                                        </div>
                                                    
                                                    <div class="tab-pane" id="tab_competencies"  >
                                                        <a> <h3>Competency List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>
                                                            <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showempcompetencyform">
                                                                <i class="fa fa-plus"></i> Add Competency
                                                            </button>
                                                            <button button type="button" class="btn btn-danger btndeleteempcompetency" data-for="empcompetency">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="empCompetencyElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th></th>
                                                                        <th>No.</th>
                                                                        <th>Competency</th>
                                                                        <th>Level Value</th>					
                                                                        <th>Date of Achievment</th>
                                                                        <th>Special Achievment</th>
                                                                        <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        
                                                        </div>
                                                        
                                                    
                                                    <div class="tab-pane" id="tab_language"  >
                                                        <a> <h3>Language List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>
                                                            <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showemplanguageform">
                                                                <i class="fa fa-plus"></i> Add Language
                                                            </button>
                                                            <button type="button" class="btn btn-danger btndeleteemplanguage" data-for="emplanguage">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="empLanguageElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th></th>
                                                                        <th>No.</th>
                                                                        <th>Language</th>
                                                                        <th>Oral</th>					
                                                                        <th>Written</th>
                                                                        <th>Description</th>
                                                                        <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        
                                                        
                                                    </div>
                                                    <div class="tab-pane" id="tab_education"  >
                                                        <a> <h3>Education List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>
                                                            <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showempeducationform">
                                                                <i class="fa fa-plus"></i> Add Education
                                                            </button>
                                                            <button type="button" class="btn btn-danger btndeleteempeducation" data-for="empeducation">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="empEducationElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th></th>
                                                                        <th>No.</th>
                                                                        <th>Education</th>
                                                                        <th>University/Institution</th>
                                                                        <th>Graduation</th>
                                                                        <th>Start Year</th>					
                                                                        <th>End Year</th>
                                                                        <th>Point</th>
                                                                        <th>Description</th>
                                                                        <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        
                                                        
                                                    </div>
                                                    <div class="tab-pane" id="tab_experience"  >
                                                        <a> <h3>Experience List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>
                                                            <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showempexperienceform">
                                                                <i class="fa fa-plus"></i> Add Experience
                                                            </button>
                                                            <button type="button" class="btn btn-danger btndeleteempexperience" data-for="empexperience">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="empExperienceElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 2%"></th>
                                                                        <th style="width: 3%">No.</th>
                                                                        <th style="width: 20%">Company Name</th>
                                                                        <th style="width: 10%">Start Date</th>					
                                                                        <th style="width: 10%">End Date</th>
                                                                        <th style="width: 10%">Position</th>
                                                                        <th style="width: 20%">Move Reason</th>
                                                                        <th style="width: 15%">Reference</th>
                                                                        <th style="width: 15%">Salary Received</th>
                                                                        <th style="width: 10%">Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_award"  >
                                                        <a> <h3>Employee Award List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>
                                                            <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showEmpAwardForm">
                                                                <i class="fa fa-plus"></i> Add Award
                                                            </button>
                                                            <button button type="button" class="btn btn-danger btndeleteEmpAward" data-for="empAward">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="empAwardElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 2%"></th>
                                                                        <th style="width: 2%">No.</th>
                                                                        <th style="width: 5%">Date</th>
                                                                        <th style="width: 8%">Award Title</th>					
                                                                        <th style="width: 14%">Division (Internal)</th>
                                                                        <th style="width: 14%">Award From (External)</th>
                                                                        <th style="width: 8%">Award Type</th>
                                                                        <th style="width: 8%">Description</th>
                                                                        <th style="width: 8%">Document</th>
                                                                        <th style="width: 16%">Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_cpath">
                                                        <a><h3>Career Path</h3></a>
                                                        <hr>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                    <button type="button" class="btn btn-primary" id="do_contract" data-oid="0" data-for="do_contract_form" data-command="0">Do Contract</button>
                                                                    <button type="button" id="do_mutation" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="do_mutation_form" data-command="0">Do Mutation</button>                                                                    
                                                                    <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="do_resign_form" data-command="0">Do Resign</button>
                                                                </div>
                                                            </div>
                                                            <div class="row pull-right">
                                                                <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="career_form"> Add New Career Path</button>
                                                                <button button type="button" class="btn btn-danger btn_delete_career" data-for="career_form">
                                                                    <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                    </div>
                                                        <div id="career_element_parent">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 2%"></th>
                                                                        <th style="width: 2%">No</th>
                                                                        <th style="width: 5%">Company</th>
                                                                        <th style="width: 8%">Division</th>
                                                                        <th style="width: 8%">Department</th>
                                                                        <th style="width: 8%">Section</th>
                                                                        <th style="width: 8%">Position</th>
                                                                        <th style="width: 5%">Level</th>
                                                                        <th style="width: 5%">Category</th>
                                                                        <th style="width: 8%">History From</th>
                                                                        <th style="width: 8%">History To</th>
                                                                        <th style="width: 8%">Contract From</th>
                                                                        <th style="width: 8%">Contract To</th>
                                                                        <th style="width: 5%">History Type</th>
                                                                        <th style="width: 5%">Document</th>
                                                                        <th style="width: 11%">Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        
                                                                </div>
                                                    
                                                    <div class="tab-pane" id="tab_general"  >
                                                        <a> <h3>Employee General Doc List : </h3></a>

                                                        <hr>
                                                        <div id="empGeneralDocElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th>No.</th>
                                                                        <th>Document Group</th>
                                                                        <th>Document Title</th>					
                                                                        <th>Document Number</th>
                                                                        <th>Document Date</th>
                                                                        <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        <div class='box-footer'>
<!--                                                            <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showEmpRelevantDocForm">
                                                                <i class="fa fa-plus"></i> Add Relevant Document
                                                            </button>
                                                            <button button type="button" class="btn btn-danger btndeleteEmpRelevantDoc" data-for="empRelevantDoc">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>-->
                                                        </div>
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_relevant"  >
                                                        <a> <h3>Employee Relevant Doc List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>
                                                            <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showEmpRelevantDocForm">
                                                                <i class="fa fa-plus"></i> Add Relevant Document
                                                            </button>
                                                            <button button type="button" class="btn btn-danger btndeleteEmpRelevantDoc" data-for="empRelevantDoc">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="empRelevantDocElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th></th>
                                                                        <th>No.</th>
                                                                        <th>Document Group</th>
                                                                        <th>Document Title</th>					
                                                                        <th>Description</th>
                                                                        <th>File Name</th>
                                                                        <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        
                                                    </div>
                                                    <div class="tab-pane" id="tab_assets"  >
                                                        <a> <h3>Assets & Inventory : </h3></a>
                                                        <hr>
                                                            <div id="empAssetElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th>No.</th>
                                                                        <th>Asset Name</th>
                                                                        <th>Qty</th>
                                                                        <th>Detail</th>					
                                                                        <th>Purpose</th>
                                                                        <th>Received Date</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                
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
                                                        
                                                        <div class="form-group">
                                                            <label>Barcode Number</label>  * <%=frmEmployee.getErrorMsgModif(FrmEmployee.FRM_FIELD_BARCODE_NUMBER)%>
                                                            <input  type="text" name="barcode_num" title=" If Employe is a Daily Worker (DL)  please replace 'DL-' with '12' ,for  example 'DL-333' become to '12333'.     If Employe's  Status  'Resigned'  please input the barcode number, barcode number is unique for example -R(BarcodeNumb/PinNumber)" value="<%=(employee.getBarcodeNumber() != null ? employee.getBarcodeNumber() : "")%>" class="form-control">
                                                        </div>
                                                        <label>U Are U Finger</label>
                                                        <div id="empFinger">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th></th>
                                                                        <th>No</th>
                                                                        <th>Description</th>
                                                                        <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        <div class="box-footer">
                                                            <div class="row pull-left">
                                                                <div class="col-md-12">
                                                                <strong><a href="javascript:cmdSaveSetting('<%=employee.getOID()%>')" class="btn btn-primary"><i class="fa fa-save"></i> Save Setting</a></strong>
                                                                <button type="button" class="btn btn-primary btnaddfinger" data-oid="0" data-for="showFingerPaternForm">
                                                                    <i class="fa fa-plus"></i> Add U Are U Finger
                                                                </button>
                                                                <button button type="button" class="btn btn-danger btndeleteEmpFinger" data-for="fingerPatern">
                                                                    <i class="fa fa-trash"></i> Delete U Are U
                                                                </button>
                                                                <button type="button" class="btn btn-primary btnuploadFinger" data-oid="0" data-for="showUploadForm">
                                                                    <i class="fa fa-plus"></i> Upload Nama
                                                                </button>
                                                                <button type="button" class="btn btn-primary btnttransferfinger" data-oid="0" data-for="showTransferForm">
                                                                    <i class="fa fa-plus"></i> Transfer Finger
                                                                </button>
                                                                <button button type="button" class="btn btn-danger btnuploadFinger" data-for="deleteFinger">
                                                                    <i class="fa fa-trash"></i> Delete Finger
                                                                </button>
                                                                
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                    </div>
                                                    
                                                    <div class="tab-pane" id="tab_training" >
                                                        <a> <h3>Training History List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>
                                                            <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showtraininghistoryform">
                                                                <i class="fa fa-plus"></i> Add Training History
                                                            </button>
                                                            <button button type="button" class="btn btn-danger btndeletetraininghistory" data-for="showtraininghistoryform">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="trainingHistoryElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 2%"></th>
                                                                        <th style="width: 3%">No.</th>
                                                                        <th style="width: 20%">Training Program</th>
                                                                        <th style="width: 20%">Training Title</th>					
                                                                        <th style="width: 10%">Trainer</th>
                                                                        <th style="width: 10%">Start Date</th>
                                                                        <th style="width: 10%">End Date</th>
                                                                        <th style="width: 5%">Duration</th>
                                                                        <th style="width: 10%">Remark</th>
                                                                        <th style="width: 10%">Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                        
                                                        
                                                    </div> 
                                                    
                                                    <div class="tab-pane" id="tab_warning"  >
                                                        <a> <h3>Warning List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>                                                        <%
                                                           int EmpWarnPoint = PstEmpWarning.EmpWarnPoint(oidEmployee); 
                                                           
                                                           if (EmpWarnPoint==2){
                                                            
                                                        %>    
                                                        <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showEmpReprimandForm">
                                                                     <i class="fa fa-plus"></i> Add Reprimand
                                                        </button>
                                                        <%   
                                                       }else {%>
                                                        <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showempwarningform" >
                                                              <i class="fa fa-plus"></i> Add Warning
                                                        </button>
                                                      <%
                                                       }
                                                      %>
                                                            <button type="button" class="btn btn-danger btndeleteempwarning" data-for="empwarning">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="empWarningElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 2%"></th>
                                                                        <th style="width: 3%">No</th>
                                                                        <th style="width: 9%">Company</th>
                                                                        <th style="width: 9%">Division</th>
                                                                        <!--<th>Department</th>-->
                                                                        <th style="width: 9%">Position</th>
                                                                        <th style="width: 9%">Break Date</th>
                                                                        <th style="width: 9%">Break Fact</th>
                                                                        <th style="width: 9%">Warning Date</th>
                                                                        <th style="width: 14%">Warning Level ( Point )</th>
                                                                        <th style="width: 9%">Warning By </th>
                                                                        <th style="width: 9%">Valid Until </th>
                                                                        <th style="width: 9%">Action</th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                           
                                                            
                                                    </div>            
                                                                
                                                     <div class="tab-pane" id="tab_reprimand"  >
                                                        <a> <h3>Rerimand List : </h3></a>

                                                        <hr>
                                                        <div class='box-footer'>
                                                        <button type="button" class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showEmpReprimandForm">
                                                                     <i class="fa fa-plus"></i> Add Reprimand
                                                        </button>
                                                            <button type="button" class="btn btn-danger btndeleteEmpReprimand" data-for="empReprimand">
                                                                <i class="fa fa-trash"></i> Delete
                                                            </button>
                                                        </div>
                                                        <div id="empReprimandElement">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="width: 2%"></th>
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
                                                                        <th style="width: 20%">Action</th>
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
                                            <strong><a href="javascript:cmdSave('<%=employee.getOID()%>')" class="btn btn-primary"><i class="fa fa-save"></i> Save Employee</a></strong>
                                            <strong><a href="<%= approot %>/employee/databank/employee_list.jsp" class="btn btn-primary"><i class="fa fa-backward"></i> Back to List Employee</a></strong>
                                            <strong><a href="javascript:cmdConfirmDelete('<%=employee.getOID()%>')" class="btn btn-danger"><i class="fa fa-trash-o"></i> Delete Employee</a></strong>
                                            <% if(employee.getAcknowledgeStatus() == 0){ %>
                                               <button class='btn btn-warning btnacknowledge' id="btnacknowledgeemp" data-oid="<%=employee.getOID()%>" data-for='showEmpAcknowledge' ><i class='fa fa-thumbs-up'></i> Acknowledge</button>
                                            <% } else if(employee.getAcknowledgeStatus() > 0){ %>
                                               <button class='btn btn-success' data-oid="<%=employee.getOID()%>" data-for='showEmpAcknowledge' type='button' ><i class='fa fa-check-circle'></i> Acknowledged</button>
                                            <% } %>
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
                todayHighlight: true,
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
                document.frm_employee.action="employee_edit.jsp";
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
            function editDefaultSch(){
                 window.open("<%=approot%>/employee/databank/default_schedule.jsp?employeeId=<%=oidEmployee%>",
                            "Harisma_Edit_Default_Schedule", "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
             }
            
            
            </script>
            <script type="text/javascript">
    $(document).ready(function () {
        $("#databank").addClass("treeview active");
        $("#AddEmp").addClass("treeview active");
        $("#error_msg").focus();
        
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
        $("#subSection").chained("#section");
        $("#divisionWa").chained("#companyWa");
        $("#departmentWa").chained("#divisionWa");
        $("#sectionWa").chained("#departmentWa");
        $("#subSectionWa").chained("#sectionWa");
        
        $('#company').on('change', function() {
            $('#companyWa').val($('#company').val()).trigger("change");
        });
        $('#division').on('change', function() {
            $('#divisionWa').val($('#division').val()).trigger("change");
        });
        $('#department').on('change', function() {
            $('#departmentWa').val($('#department').val()).trigger("change");
        });
        $('#section').on('change', function() {
            $('#sectionWa').val($('#section').val()).trigger("change");
        });
        $('#position').on('change', function() {
            $('#positionWa').val($('#position').val()).trigger("change");
        });

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
        var addfinger = null;
        var areaTypeForm = null;
        var deletegeneral = null;
        var deletesingle = null;
        
        var servletName = null;
        var editor = "";
        
        // Change hash for page-reload
        $('.nav-tabs a').on('shown.bs.tab', function (e) {
            window.location.hash = e.target.hash;
        })
        
        var interval =0;
        var interval2 =0;
        var currentCount = <%= listFingerPatern.size()%>   
        

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
                todayHighlight: true,
                format: formatDate
            });
            $(contentId).on('changeDate', function (ev) {
                $(this).datepicker('hide');
            });
        }
        
        function datePicker1(contentId, formatDate) {
            $(contentId).datepicker1({
                todayHighlight: true,
                format: formatDate
            });
            $(contentId).on('changeDate', function (ev) {
                $(this).datepicker1('hide');
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

        //Update Finger
        var oidGeneral = "";
        function update(url,data,type,appendTo,another){
            $.ajax({
                url : ""+url+"",
                data: ""+data+"",
                type : ""+type+"",
                async : false,
                cache: false,
                success : function(data) {


                },
                error : function(data){
                    alert('error');
                }
            }).done(function(data){
                    //alert(another);
                    if (another=="erase"){
                        //alert("test");
                        checkFingerPatern(oidGeneral);    
                    }else if (another=="checkFingerPatern"){
                        //alert(data);
                        if (data=="1"){
                            clearInterval(interval);
                            alert('Your finger patern has successfull updated');
                            location.reload();
                        }
                    }else if (another=="deleteFingerPatern"){
                        location.reload();

                    }
                }
            );

        }
        function fingerUpdateEmpty(){
            $('.fingerSpotUpdate').click(function(){
                var url ="<%=approot%>/RegisterFingerData";
                var oid = $(this).data("oid");
                var data = "command=<%= Command.DELETE%>&oidFinger="+oid+"";

                oidGeneral = oid;
                update(url,data,"POST","","erase");
            });
        }
        function checkFingerPatern(oidGeneral){
            interval = setInterval(function() {
                var url ="<%=approot%>/RegisterFingerData";
                var data = "command=<%= Command.LOAD%>&oidFinger="+oidGeneral+"";
                update(url,data,"POST","","checkFingerPatern");
            },5000);

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
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showemplanguageform') {
                        $(".addeditgeneral-title").html("Edit Employee Language");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showempexperienceform') {
                        $(".addeditgeneral-title").html("Edit Employee Experience");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showempeducationform') {
                        $(".addeditgeneral-title").html("Edit Employee Education");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showfamilymemberform') {
                        $(".addeditgeneral-title").html("Edit Family Member");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }

                    }
                    if (dataFor == 'showtraininghistoryform') {
                        $(".addeditgeneral-title").html("Edit Training History");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showEmpMutationForm') {
                        $(".addeditgeneral-title").html("Edit Employee Contract");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showEmpRelevantDocForm') {
                        $(".addeditgeneral-title").html("Edit Employee Relevant Document");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showEmpAwardForm') {
                        $(".addeditgeneral-title").html("Edit Employee Award");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showempwarningform') {
                        $(".addeditgeneral-title").html("Edit Warning");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "85%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showEmpReprimandForm') {
                        $(".addeditgeneral-title").html("Edit Reprimand");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "85%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'contract_type_form'){
                        $(".addeditgeneral-title").html("Edit Contract Type");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    

                } else {
                    if (dataFor == 'showempcompetencyform') {
                        $(".addeditgeneral-title").html("Add Employee Competency");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showemplanguageform') {
                        $(".addeditgeneral-title").html("Add Employee Language");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showempexperienceform') {
                        $(".addeditgeneral-title").html("Add Employee Experience");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showempeducationform') {
                        $(".addeditgeneral-title").html("Add Employee Education");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showfamilymemberform') {
                        $(".addeditgeneral-title").html("Add Family Member");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showtraininghistoryform') {
                        $(".addeditgeneral-title").html("Add Training History");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showEmpMutationForm') {
                        $(".addeditgeneral-title").html("Add Employee Contract");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showEmpRelevantDocForm') {
                        $(".addeditgeneral-title").html("Add Employee Relevant Document");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showEmpAwardForm') {
                        $(".addeditgeneral-title").html("Add Employee Award");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showempwarningform') {
                        $(".addeditgeneral-title").html("Add Warning");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "75%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'showEmpReprimandForm') {
                        $(".addeditgeneral-title").html("Add Reprimand");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "85%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'contract_type_form'){
                        $(".addeditgeneral-title").html("Add Contract Type");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'do_resign_form'){
                        $(".addeditgeneral-title").html("Do Resign");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'career_form'){
                        $(".addeditgeneral-title").html("Add Career Path");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                    if (dataFor == 'do_mutation_form'){
                        $(".addeditgeneral-title").html("Do Mutation");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                }
                if (dataFor == 'generateDocAward'){
                    $(".addeditgeneral-title").html("Award Document");
                    if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "85%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
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
                    datePicker(".datepicker1", "yyyy-mm");
                    
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
        
        //SHOW ADD OR EDIT FORM
        addfinger = function (elementId) {
            $(elementId).click(function () {
                $("#fingerModal").modal("show");
                command = $("#command").val();
                oid = $(this).data('oid');
                dataFor = $(this).data('for');
                empId = "<%=employee.getOID()%>";
                userName = "<%=emplx.getFullName()%>";
                userId = <%=appUserIdSess%>;
                $("#generaldatafor").val(dataFor);
                $("#oid").val(oid);
                $("#empId").val(empId);
                var url = "<%=baseURL%>";
                
                //SET TITLE MODAL
                if (oid != 0) {
                    if (dataFor == 'showFingerPaternForm') {
                        $(".fingerModal-title").html("Edit Employee Finger");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                } else {
                    if (dataFor == 'showFingerPaternForm') {
                        $(".fingerModal-title").html("Add Employee Finger");
                        if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "50%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    }
                }
                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "FRM_FIELD_OID": oid,
                    "FRM_FIELD_APPROOT" : url,
                    "command": command,
                    "empId" : empId,
                    "userName" : userName,
                    "userId" : userId
                }
                onDone = function (data) {
                    //Add FInger
                    function ajax(url,data,type,appendTo,another){
                            $.ajax({
                                url : ""+url+"",
                                data: ""+data+"",
                                type : ""+type+"",
                                async : false,
                                cache: false,
                                success : function(data) {
                                //alert(data + '' + currentCount);
                                    if (another=="getCount"){
                                        if (currentCount<data){
                                            currentCount = data;
                                            //alert(currentCount);
                                            //$('#modalReport').modal({backdrop: 'static', keyboard: false});
                                            clearInterval(interval);
                                            alert('Your finger patern has successfull registered');
                                            location.reload(); 
                                        }
                                    }

                                },
                                error : function(data){
                                    alert('error');
                                }
                            }).done(function(){

                            });

                        }

                        function cekCountFinger(){
                            var url ="<%=approot%>/RegisterFingerData";
                            var data = "command=<%= Command.GET%>&user_id=<%= employee.getOID()%>";
                            //alert(data);
                            ajax(url,data,"POST","","getCount");
                        }



                        $('.fingerSpot').click(function(){
                            $("#buttonFinger").html("Please Wait...").attr({"disabled": "true"});
                            interval = setInterval(function() {
                               cekCountFinger();
                            },5000);
                        });
                        // End Add Finger
                                            
                    $('#fingerId').change(function(){
                        var nilai = $(this).val();
                        if (nilai!=""){
                            $('.fingerSpot').attr({'href':'findspot:findspot protocol;'+nilai+''});
                        }else{
                            $('.fingerSpot').attr({'href':'#'});
                        }

                    });
                };
                onSuccess = function (data) {

                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxFingerPatern", ".fingerModal-body", false, "json");
            });
        };
        
        var uploadfinger = function (elementId) {
            $(elementId).click(function () {
                $("#uploadFingerModal").modal("show");
                command = $("#command").val();
                oid = $(this).data('oid');
                dataFor = $(this).data('for');
                empId = "<%=employee.getOID()%>";
                userName = "<%=emplx.getFullName()%>";
                userId = <%=appUserIdSess%>;
                $("#frmUploadFinger #generaldatafor").val(dataFor);
                $("#frmUploadFinger #oid").val(oid);
                $("#frmUploadFinger #empId").val(empId);
                var url = "<%=baseURL%>";
                
                //SET TITLE MODAL
                if (dataFor === "showUploadForm"){
                    $(".uploadFingerModal-title").html("Upload Finger");
                    if ($(window).width() > 480) {
                        $(".modal-dialog").css("width", "50%");
                    } else {
                        $(".modal-dialog").css("width", "100%");
                    }
                    $( "#btnUploadFInger" ).html('<i class="fa fa-check"></i> Save');
                    $( "#btnUploadFInger" ).removeClass('btn-danger').addClass('btn-primary');
                } else if (dataFor === "deleteFinger"){
                    $(".uploadFingerModal-title").html("Delete Finger");
                    if ($(window).width() > 480) {
                        $(".modal-dialog").css("width", "50%");
                    } else {
                        $(".modal-dialog").css("width", "100%");
                    }
                    $( "#btnUploadFInger" ).html('<i class="fa fa-trash"></i> Delete');
                    $( "#btnUploadFInger" ).removeClass('btn-primary').addClass('btn-danger');
                }

                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "FRM_FIELD_OID": oid,
                    "FRM_FIELD_APPROOT" : url,
                    "command": command,
                    "empId" : empId,
                    "userName" : userName,
                    "userId" : userId
                }
                onDone = function (data) {
                    //Add FInger
                };
                onSuccess = function (data) {

                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxFingerMachine", ".uploadFingerModal-body", false, "json");
            });
        };
        
        var transferfinger = function (elementId) {
            $(elementId).click(function () {
                $("#transferFingerModal").modal("show");
                command = $("#command").val();
                oid = $(this).data('oid');
                dataFor = $(this).data('for');
                empId = "<%=employee.getOID()%>";
                userName = "<%=emplx.getFullName()%>";
                userId = <%=appUserIdSess%>;
                $("#frmTransferFinger #generaldatafor").val(dataFor);
                $("#frmTransferFinger #oid").val(oid);
                $("#frmTransferFinger #empId").val(empId);
                var url = "<%=baseURL%>";
                
                //SET TITLE MODAL
                $(".transferFingerModal-title").html("Transfer Finger");
                if ($(window).width() > 480) {
                    $(".modal-dialog").css("width", "50%");
                } else {
                    $(".modal-dialog").css("width", "100%");
                }

                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "FRM_FIELD_OID": oid,
                    "FRM_FIELD_APPROOT" : url,
                    "command": command,
                    "empId" : empId,
                    "userName" : userName,
                    "userId" : userId
                }
                onDone = function (data) {
                    $("select").chosen();
                    $("#firstMachine").change(function(){
                            var machineId = $(this).val();
                            dataFor = $(this).data("for");
                            var target = $(this).data("replacement");
                            var targetRemove = $(this).data("remove");
                            var dataSends = {
                                "FRM_FIELD_DATA_FOR": dataFor,
                                "FRM_FIELD_OID": oid,
                                "command": command,
                                "machineId":machineId
                            }
                            var onDones = function(data){
                                $(targetRemove).chosen("destroy");
                                $(targetRemove).html(data.FRM_FIELD_HTML).chosen();
                                //$(targetRemove).empty();
                                //$(targetRemove).append(data.FRM_FIELD_HTML)();
                                $(targetRemove+" option[value='"+machineId+"']").remove();
//                                
                                
                            };
                            var onSuccesses = function(data){

                            };
                            getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxFingerMachine", null, false, "json");
                        });
                        $("#secondMachine").change(function(){
                            var firstMachineId = $('select[name=firstMachine]').val()
                            $("#frmTransferFinger #secondMachineStr").val($(this).val());
                            dataFor = $(this).data("for");
                            var target = $(this).data("replacement");
                            var dataSends = {
                                "FRM_FIELD_DATA_FOR": dataFor,
                                "FRM_FIELD_OID": oid,
                                "command": command,
                                "machineId":firstMachineId,
                                "empId" : empId
                            }
                            var onDones = function(data){
                                $(target).chosen("destroy");
                                $(target).html(data.FRM_FIELD_HTML).chosen();
//                                $(target).empty();
//                                $(target).append(data.FRM_FIELD_HTML)();
                                  
                                
                            };
                            var onSuccesses = function(data){

                            };
                            getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxFingerMachine", null, false, "json");
                        });
                        $("#finger").change(function(){
                            $("#frmTransferFinger #fingerId").val($('#finger option:selected').text());
                        });
                };
                onSuccess = function (data) {

                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxFingerMachine", ".transferFingerModal-body", false, "json");
            });
        };
        
        
        //SHOW EDITOR FORM
        var editormodal = function(elementId){
            $(elementId).click(function(){
                $("#editor").modal("show");
                command = $("#command").val();
                oid = $(this).data('oid');
                dataFor = $(this).data('for');
                var template = $(this).data('template1');
                $("#editorform #generaldatafor").val(dataFor);
                $("#editorform #oid").val(oid);
                $("#editor .modal-dialog").css("width", "100%");
                //SET TITLE MODAL
                $(".editor-title").html("Editor");


                dataSend = {
                    "FRM_FIELD_DATA_FOR"	: dataFor,
                    "FRM_FIELD_OID"		 : oid,
                    "command"		 : command,
                    "datachange"               : template
                }
                onDone = function(data){
                    editor = CKEDITOR.instances['FRM_FIELD_DETAILS'];
                    if (editor) { editor.destroy(true); }
                    CKEDITOR.replace('FRM_FIELD_DETAILS');
//                        if (CKEDITOR.instances.FRM_FIELD_DETAILS){
//                          CKEDITOR.instances.FRM_FIELD_DETAILS.destroy();  
//                        } 
//                        editor = CKEDITOR.replace('FRM_FIELD_DETAILS');
                    datePicker(".datepicker", "yyyy-mm-dd");
                    $(".colorpicker").colorpicker();
                    //loadDocNumber(".datachange");
                    //loadTemplate(".datachange");
//                        CKEDITOR.replace('FRM_FIELD_DETAILS');
                    $("#FRM_FIELD_DETAILS").val($("#editor_text").val());

                    $('#btnClose').click(function(){
                        $('#addempsingle').modal('toggle');
                    });
                };
                onSuccess = function(data){

                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".editor-body", false, "json");
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
                var array = vals.split(",");

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
                                location.reload();
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
                        if (dataFor == "fingerPatern"){
                            servletName = "AjaxFingerPatern";
                            onDone = function (data) {
                                runDataTables("showFingerPaternForm");
                            }
                            currentCount = currentCount - array.length;
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
                var empid = $(this).data("empid");
                var delResign = $(this).data("resign");
                var confirmTextSingle = "Are you sure want to delete these data?";
                
                command = <%= Command.DELETE%>;
                var currentHtml = $(this).html();
                $(this).html("Deleting...").attr({"disabled": true});
                if (confirm(confirmTextSingle)) {
                    dataSend = {
                        "FRM_FIELD_DATA_FOR": dataFor,
                        "FRM_FIELD_OID": vals,
                        "command": command,
                        "empId": empid,
                        "delete_resign": delResign
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
                            location.reload();
                            runDataTables("showempwarningform");
                        };
                    }
                    if (dataFor == "deleteEmpRepSingle") {
                        servletName = "AjaxEmpReprimand";
                        onDone = function (data) {
                            runDataTables("showEmpReprimandForm");
                        };
                    }
                    if (dataFor == "deleteCareer") {
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
                    if (dataFor == "deleteFingerPaternSingle"){
                        servletName = "AjaxFingerPatern";
                        onDone = function (data) {
                            runDataTables("showFingerPaternForm");
                        }
                        currentCount = currentCount - 1;
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
                if (dataFor == 'do_mutation_form') {
                    servletName = "CareerAjax";
                }
               
               getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, target, false, "json");
               
            });
        
        };

        //FUNCTION FOR DATA TABLES
        callBackDataTables = function () {
            addeditgeneral(".btneditgeneral");
            upload(".btnupload");
            sendEmail(".btnsend");
            deletesingle(".btndeletesingle");
            addfinger(".btnaddfinger");
            uploadfinger(".btnuploadFinger");
            transferfinger(".btnttransferfinger");
            approvalModal(".btnacknowledge","1");
            viewDoc(".btneditor");
            iCheckBox();
            fingerUpdateEmpty();
            editormodal(".btneditormodal");
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
        contractForm = function () {
            validateOptions("#emp_category_id", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_FROM] %>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_TO] %>", 'text', 'has-error', 1, null);            
        }
        mutationForm = function () {
            validateOptions("#<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_MUTATION_TYPE] %>", 'text', 'has-error', 1, null);
            validateOptions("#company_mutation", 'text', 'has-error', 1, null);
            validateOptions("#division_mutation", 'text', 'has-error', 1, null);
            validateOptions("#department_mutation", 'text', 'has-error', 1, null);
            validateOptions("#section_mutation", 'text', 'has-error', 1, null);
            validateOptions("#position_mutation", 'text', 'has-error', 1, null);
            validateOptions("#level_mutation", 'text', 'has-error', 1, null);
        }
        resignForm = function () {
            validateOptions("#resign_date", 'text', 'has-error', 1, null);
        }
        careerForm = function () {
            validateOptions("#company_career", 'text', 'has-error', 1, null);
            validateOptions("#division_career", 'text', 'has-error', 1, null);            
            validateOptions("#department_career", 'text', 'has-error', 1, null);            
            validateOptions("#section_career", 'text', 'has-error', 1, null);            
            validateOptions("#position_career", 'text', 'has-error', 1, null);            
            validateOptions("#level_career", 'text', 'has-error', 1, null);            
            validateOptions("#category_career", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_HISTORY_TYPE] %>", 'text', 'has-error', 1, null);
            validateOptions("#work_from_career", 'text', 'has-error', 1, null);
            validateOptions("#work_to_career", 'text', 'has-error', 1, null);           
            validateOptions("#contract_from_career", 'text', 'has-error', 1, null);            
            validateOptions("#contract_to_career", 'text', 'has-error', 1, null);                        
        }
        relDocEmail = function () {
            validateOptions("#subject", 'text', 'has-error', 1, null);
            validateOptions("#email", 'text', 'has-error', 1, null);
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
        
        sendEmail = function(elementId){
            $(elementId).unbind().click(function(){
                $("#sendEmailModal").modal("show");
                if ($(window).width() > 480) {
                        $(".modal-dialog").css("width", "85%");
                    } else {
                        $(".modal-dialog").css("width", "100%");
                    }
                var empId = $(this).data("empid");
                var relevanDocId = $(this).data("oid");
                $("#empId").val(empId);
                $("#relevanDocId").val(relevanDocId);

                dataFor = $(this).data("for");

                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor
                }
                onDone = function (data) {
                    $("#message").wysihtml5();
                    $('#cc').tagit({
                        fieldName: "ccEmail"
                    });
                    $('#bcc').tagit({
                        fieldName: "bccEmail"
                    }); 
                };
                onSuccess = function (data) {
                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpRelevantDoc", ".sendemail-body", false, "json");
            });
        };
        
        //FORM SUBMIT
//        $("form#formupload").submit(function () {
//            var currentBtnHtml = $("#btnupload").html();
//            $("#btnupload").html("Uploading...").attr({"disabled": "true"});
//            var generaldatafor = $("#generaldatafor").val();
//            var cc = $("#cc").val();
//            
//            if (generaldatafor == "showEmpRelevantDocForm") {
//                servletName = "AjaxEmpRelevantDoc";
//                onDone = function (data) {
//                    runDataTables("showEmpRelevantDocForm");
//                };
//            }
//
//            if ($(this).find(".has-error").length == 0) {
//                onSuccess = function (data) {
//                    $("#btnupload").removeAttr("disabled").html(currentBtnHtml);
//                    $("#uploaddoc").modal("hide");
//                };
//
//                dataSend = $(this).serialize();
//                getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, null, false, null);
//            } else {
//                $("#btnupload").removeAttr("disabled").html(currentBtnHtml);
//            }
//
//            return false;
//        });
        
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
                if (dataFor == "showEmpCareer"){
                    $("#formupload").attr("action","<%=approot%>/employee/databank/upload.jsp?FRM_FIELD_OID="+oid+"&empId=<%=employee.getOID()%>&modul=career&approot=<%=approot%>");
                }
                $("#tempname").val("");
                $("#generaldatafor").val(dataFor);
                $("#oiddata").val(oid);
            });
        };

        //UPLOAD

        function uploadTrigger(){
            $("#uploadtrigger").unbind().click(function(){
                $("#FRM_DOC").trigger('click');
            });

            $("#FRM_DOC").unbind().change(function(){
                var doc = $("#FRM_DOC").val();
                if (doc.indexOf("'") > -1){
                    $('#btnupload').prop('disabled', true);
                    alert("File Name Can't Contain ' Character ")
                    $("#tempname").val('');
                } else {
                    $('#btnupload').prop('disabled', false);
                    $("#tempname").val(doc);
                }
                
            });

            $("#tempname").unbind().click(function(){
                $("#FRM_DOC").trigger('click');
            });
        }


        //DATA TABLES SETTING
        function dataTablesOptions(elementIdParent, elementId, servletName, dataFor, callBackDataTables) {
            var table = $('#'+elementId).DataTable();
            table.destroy();
            var datafilter = $("#datafilter").val();
            var privUpdate = "true";
            var scroll = false;
            if (elementId == "tableempwarningElement"){
                scroll = true;
            } 
            if (elementId == "tablefamilyMemberElement"){
                scroll = true;
            }
            if (elementId == "tableEmpReprimandElement"){
                scroll = true;
            }
            if (elementId == "tableEmpAwardElement"){
                scroll = true;
            }
            if (elementId == "career_element"){
                scroll = true;
            }
            
            var approot = "<%=approot%>";
            empId = "<%=employee.getOID()%>";
            var url = "<%=baseURL%>";
            var aTargets = null;
            $(elementIdParent).find('table').addClass('table-bordered table-hover').attr({'id': elementId});
            $("#" + elementId).dataTable({"bDestroy": true,
                "iDisplayLength": 10,
                "bProcessing": true,
                "scrollX" : scroll,
                "oLanguage": {
                    "sProcessing": "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
                },
                "bServerSide": true,
                "sAjaxSource": "<%= approot%>/" + servletName + "?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER=" + datafilter + "&FRM_FIELD_DATA_FOR=" + dataFor + "&privUpdate=" + privUpdate + "&empId=" + empId +"&FRM_FIELD_APPROOT=" +url,
                aoColumnDefs: [
                    {
                        bSortable: false,
                        aTargets: [0, -1]
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
            if (dataFor == "showFingerPaternForm"){
                elementIdParent = "#empFinger";
                servletName = "AjaxFingerPatern";
                listData = "listFinger";
                elementId = "tableEmpFingerElement";
            }
            if (dataFor == "showEmpGeneralDoc"){
                elementIdParent = "#empGeneralDocElement";
                servletName = "AjaxEmpDocGeneral";
                listData = "listEmpGeneralDoc";
                elementId = "tableEmpGeneralDocElement";
            }
            if (dataFor == "showAssetForm"){
                elementIdParent = "#empAssetElement";
                servletName = "AjaxEmpAssetInventoryItem";
                listData = "listEmpAssetItem";
                elementId = "tableEmpAssetElement";
            }
            dataTablesOptions(elementIdParent, elementId, servletName, listData, callBackDataTables);
        }

        
        modalSetting("#addeditgeneral", "static", false, false);
        modalSetting("#sendEmailModal", "static", false, false);
        modalSetting("#fingerModal", "static", false, false);
        addeditgeneral(".btnaddgeneral");
        addfinger(".btnaddfinger");
        uploadfinger(".btnuploadFinger");
        transferfinger(".btnttransferfinger");
        
        
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
        $('.tabGeneralDoc').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showEmpGeneralDoc");
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
            uploadTrigger();
            deletegeneral(".btn_delete_career", ".<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID]%>");
        });
        $('.tabContract').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("contract_type_form");
            deletegeneral(".btn_delete_contract", ".contract_type_id");
        });
        $('.tabFinger').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showFingerPaternForm");
            deletegeneral(".btndeleteEmpFinger", ".<%= FrmFingerPatern.fieldNames[FrmFingerPatern.FRM_FIELD_FINGER_PATERN_ID]%>");
        });
        $('.tabAsset').click(function() {
            $('input:checkbox').removeAttr('checked');
            runDataTables("showAssetForm");
        });
        
        // Javascript to enable link to tab
        var url = document.location.toString();
        if (url.match('#')) {
            $('.nav-tabs a[href="#' + url.split('#')[1] + '"]').tab('show');
            if(url.split('#')[1] == "tab_family") {
                $('input:checkbox').removeAttr('checked');
                runDataTables("showfamilymemberform");
                deletegeneral(".btndeletefamilymember", ".<%= FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FAMILY_MEMBER_ID]%>");
            }
            else if(url.split('#')[1] == "tab_competencies") {
                $('input:checkbox').removeAttr('checked');
                runDataTables("showempcompetencyform");
                deletegeneral(".btndeleteempcompetency", ".<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_COMP_ID]%>");
            } 
            else if(url.split('#')[1] == "tab_language") {
                $('input:checkbox').removeAttr('checked');
                runDataTables("showemplanguageform");
                deletegeneral(".btndeleteemplanguage", ".<%= FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_EMP_LANGUAGE_ID]%>");
            } 
            else if(url.split('#')[1] == "tab_education") {
                $('input:checkbox').removeAttr('checked');
                runDataTables("showempeducationform");
                deletegeneral(".btndeleteempeducation", ".<%= FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMP_EDUCATION_ID]%>");
            } 
            else if(url.split('#')[1] == "tab_experience") {
                $('input:checkbox').removeAttr('checked');
                runDataTables("showempexperienceform");
                deletegeneral(".btndeleteempexperience", ".<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_WORK_HISTORY_PAST_ID]%>");
            } 
            else if(url.split('#')[1] == "tab_training") {
                $('input:checkbox').removeAttr('checked');
                runDataTables("showtraininghistoryform");
                deletegeneral(".btndeletetraininghistory", ".<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_HISTORY_ID]%>");
            } 
            else if(url.split('#')[1] == "tab_warning") {
                $('input:checkbox').removeAttr('checked');
                runDataTables("showempwarningform");
                deletegeneral(".btndeleteempwarning", ".<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARNING_ID]%>");
            } else if(url.split('#')[1] == "tab_award"){
                $('input:checkbox').removeAttr('checked');
                runDataTables("showEmpAwardForm");
                uploadTrigger();
                deletegeneral(".btndeleteEmpAward", ".<%= FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_ID]%>");
            } else if (url.split('#')[1] == "tab_reprimand"){
               $('input:checkbox').removeAttr('checked');
                runDataTables("showEmpReprimandForm");
                uploadTrigger();
                deletegeneral(".btndeleteEmpReprimand", ".<%= FrmEmpReprimand.fieldNames[FrmEmpReprimand.FRM_FIELD_REPRIMAND_ID]%>");
            } else if (url.split('#')[1] == "tab_relevant"){
               $('input:checkbox').removeAttr('checked');
                runDataTables("showEmpRelevantDocForm");
                uploadTrigger();
                deletegeneral(".btndeleteEmpRelevantDoc", ".<%= FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_RELEVANT_ID]%>");
            } else if (url.split('#')[1] == "tab_setting"){
                $('input:checkbox').removeAttr('checked');
                runDataTables("showFingerPaternForm");
                deletegeneral(".btndeleteEmpFinger", ".<%= FrmFingerPatern.fieldNames[FrmFingerPatern.FRM_FIELD_FINGER_PATERN_ID]%>");
            } else if (url.split('#')[1] == "tab_cpath"){
                runDataTables("career_form");
                uploadTrigger();
                deletegeneral(".btn_delete_career", ".<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID]%>");
            }
        }
        

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
                    window.location.reload();
                    runDataTables("showempwarningform");
                };
            }
            if (generaldatafor == "showEmpReprimandForm") {
                empReprimandForm();
                servletName = "AjaxEmpReprimand";
                onDone = function (data) {
                    location.href="#tab_reprimand";
                    location.reload();
                    runDataTables("showEmpReprimandForm");
                };
            }
            if (generaldatafor == "career_form"){
                careerForm();
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
                contractForm();
                servletName = "CareerAjax";
                onDone = function (data){
                    runDataTables("do_contract_form");
                };
            }
            
            if (generaldatafor == "do_resign_form"){
                resignForm();
                servletName = "CareerAjax";
                onDone = function (data){
                    runDataTables("career_form");
                };
            }

            if (generaldatafor == "do_mutation_form"){
                mutationForm();
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
        
        $("form#frmUploadFinger").submit(function () {
            var currentBtnHtml = $("#btnUploadFInger").html();
            $("#btnUploadFInger").html("Saving...").attr({"disabled": "true"});
            var generaldatafor = $("#frmUploadFinger #generaldatafor").val();
            onDone = function (data) {
            };
            if ($(this).find(".has-error").length == 0) {
                onSuccess = function (data) {
                    $("#btnUploadFInger").removeAttr("disabled").html(currentBtnHtml);
                    $("#uploadFingerModal").modal("hide");
                    
                };

                dataSend = $(this).serialize();
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxFingerMachine", null, true, "json");
            } else {
                $("#btnUploadFInger").removeAttr("disabled").html(currentBtnHtml);
            }

            return false;
        });
        
        $("form#frmTransferFinger").submit(function () {
            var currentBtnHtml = $("#btntransfer").html();
            $("#btntransfer").html("Saving...").attr({"disabled": "true"});
            var generaldatafor = $("#frmTransferFinger #generaldatafor").val();
            onDone = function (data) {
            };
            if ($(this).find(".has-error").length == 0) {
                onSuccess = function (data) {
                    $("#btntransfer").removeAttr("disabled").html(currentBtnHtml);
                    $("#transferFingerModal").modal("hide");
                };

                dataSend = $(this).serialize();
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxFingerMachine", null, true, "json");
            } else {
                $("#btntransfer").removeAttr("disabled").html(currentBtnHtml);
            }

            return false;
        });
        
        $("#sendEmail").click(function(){
        $("#sendEmailModal").modal("show");
        if ($(window).width() > 480) {
                $(".modal-dialog").css("width", "85%");
            } else {
                $(".modal-dialog").css("width", "100%");
            }
        var empId = $(this).data("empid");
        var relevanDocId = $(this).data("oid");
        $("#empId").val(empId);
        $("#relevanDocId").val(relevanDocId);
        
        dataFor = $(this).data("for");

        dataSend = {
            "FRM_FIELD_DATA_FOR": dataFor
        }
        onDone = function (data) {
            $("#message").wysihtml5();
        };
        onSuccess = function (data) {
        };
        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpRelevantDoc", ".sendemail-body", false, "json");
    });
    
    //FORM SUBMIT
    $("form#emailForm").submit(function () {
        var currentBtnHtml = $("#btnsend").html();
        $("#btnsend").html("Sending...").attr({"disabled": "true"});
        var generaldatafor = $("#generaldatafor").val();
        var cc = $("#cc").val();
        relDocEmail();
        onDone = function (data) {
           runDataTables("showEmpRelevantDocForm");
           $("#notifications").notify({
                message: { html : "Email Sent" },
                type    : "info",
                transition : "fade"
            }).show();
        };

        if ($(this).find(".has-error").length == 0) {
            onSuccess = function (data) {
                $("#btnsend").removeAttr("disabled").html(currentBtnHtml);
                $("#sendEmailModal").modal("hide");
            };

            dataSend = $(this).serialize();
            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpRelevantDoc", null, false, null);
        } else {
            $("#btnsend").removeAttr("disabled").html(currentBtnHtml);
        }

        return false;
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
            
    $("#company").change(function(){
        empId = "<%=employee.getOID()%>";
        dataFor = "getPosition";
        var compId = $(this).val();
        var target = $(this).data("target");
        var dataSends = {
            "FRM_FIELD_DATA_FOR": dataFor,
            "FRM_FIELD_OID": oid,
            "command": command,
            "empId" : empId,
            "posComp" : compId
        }
        var onDones = function(data){
            $(target).html(data.FRM_FIELD_HTML);
//            $("#empReceived").chosen("destroy");
//            $("#empReceived").html(data.FRM_FIELD_HTML2).chosen();
//            $(".btnadditem").attr("data-empid",employeeId);
        };
        var onSuccesses = function(data){

        };
        getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmployee", target, false, "json");
    });
    
    $("#division").change(function(){
        empId = "<%=employee.getOID()%>";
        dataFor = "getPosition";
        var compId = $("#company").val();
        var divId = $(this).val();
        var target = $(this).data("target");
        var dataSends = {
            "FRM_FIELD_DATA_FOR": dataFor,
            "FRM_FIELD_OID": oid,
            "command": command,
            "empId" : empId,
            "posComp" : compId,
            "podDiv" : divId
        }
        var onDones = function(data){
            $(target).html(data.FRM_FIELD_HTML);
//            $("#empReceived").chosen("destroy");
//            $("#empReceived").html(data.FRM_FIELD_HTML2).chosen();
//            $(".btnadditem").attr("data-empid",employeeId);
        };
        var onSuccesses = function(data){

        };
        getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmployee", target, false, "json");
    });
    
    $("#department").change(function(){
        empId = "<%=employee.getOID()%>";
        dataFor = "getPosition";
        var compId = $("#company").val();
        var divId = $("#division").val();
        var deptId = $(this).val();
        var target = $(this).data("target");
        var dataSends = {
            "FRM_FIELD_DATA_FOR": dataFor,
            "FRM_FIELD_OID": oid,
            "command": command,
            "empId" : empId,
            "posComp" : compId,
            "podDiv" : divId,
            "posDept" : deptId
        }
        var onDones = function(data){
            $(target).html(data.FRM_FIELD_HTML);
//            $("#empReceived").chosen("destroy");
//            $("#empReceived").html(data.FRM_FIELD_HTML2).chosen();
//            $(".btnadditem").attr("data-empid",employeeId);
        };
        var onSuccesses = function(data){

        };
        getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmployee", target, false, "json");
    });
    
    $("#section").change(function(){
        empId = "<%=employee.getOID()%>";
        dataFor = "getPosition";
        var compId = $("#company").val();
        var divId = $("#division").val();
        var deptId = $("#department").val();
        var secId = $(this).val();
        var target = $(this).data("target");
        var dataSends = {
            "FRM_FIELD_DATA_FOR": dataFor,
            "FRM_FIELD_OID": oid,
            "command": command,
            "empId" : empId,
            "posComp" : compId,
            "podDiv" : divId,
            "posDept" : deptId,
            "posSec" : secId
        }
        var onDones = function(data){
            $(target).html(data.FRM_FIELD_HTML);
//            $("#empReceived").chosen("destroy");
//            $("#empReceived").html(data.FRM_FIELD_HTML2).chosen();
//            $(".btnadditem").attr("data-empid",employeeId);
        };
        var onSuccesses = function(data){

        };
        getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmployee", target, false, "json");
    });
    
    $("#subSection").change(function(){
        empId = "<%=employee.getOID()%>";
        dataFor = "getPosition";
        var compId = $("#company").val();
        var divId = $("#division").val();
        var deptId = $("#department").val();
        var secId = $("#section").val();
        var subSecId = $(this).val();
        var target = $(this).data("target");
        var dataSends = {
            "FRM_FIELD_DATA_FOR": dataFor,
            "FRM_FIELD_OID": oid,
            "command": command,
            "empId" : empId,
            "posComp" : compId,
            "podDiv" : divId,
            "posDept" : deptId,
            "posSec" : secId,
            "possubsec" : subSecId
        }
        var onDones = function(data){
            $(target).html(data.FRM_FIELD_HTML);
//            $("#empReceived").chosen("destroy");
//            $("#empReceived").html(data.FRM_FIELD_HTML2).chosen();
//            $(".btnadditem").attr("data-empid",employeeId);
        };
        var onSuccesses = function(data){

        };
        getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmployee", target, false, "json");
    });
            
    var approvalModal = function(elementId,showModal){
        $(elementId).click(function(){
            oid = $(this).data('oid');
            dataFor = $(this).data('for');
            $("#fingerdatafor").val(dataFor);
            if (showModal==="1"){
                $("#modalApproveFinger").modal("show");
                $("#modalApproveFinger .modal-dialog").css("width", "50%");
            }
            loadFingerPatern(oid,dataFor);
        });
    };  
    
    approvalModal(".btnacknowledge","1");
    
    var loadFingerPatern = function(oid,dataFor){
        oid = oid;
        dataFor = "showAcknowledgeForm";
        empId = "<%= employee.getOID()%>";
        var url = "<%=baseURL%>";
            dataSend = {
                "FRM_FIELD_DATA_FOR"       : dataFor,                                       
                "FRM_FIELD_OID"            : oid, 
                "empId"                    : empId,
                "FRM_FIELD_APPROOT"        : url
            };

            onSuccess = function(data){

            };

            onDone = function(data){
               fingerClick(".loginFinger");
            };

            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmployee", "#dynamicFingerPatern", false, "json");
        };
        
    var fingerClick = function (elementId){
        //alert('test');
        $(elementId).click(function(){
            var empId= "<%= employee.getOID()%>";
            interval2 =  setInterval(function() {
                checkStatusUser(empId);
            },5000);

        });
    };

    var checkStatusUser = function(empId){
        var dataFor = "checkStatusUser";                                  
        command = "<%= Command.NONE%>";
        dataSend = {
            "FRM_FIELD_DATA_FOR"       : dataFor,                                       
            "FRM_FIELD_LOGIN_ID"       : empId,                                   
            "command"                  : command
        };

        onSuccess = function(data){

        };

        onDone = function(data){
            if (data.FRM_FIELD_HTML=="1"){
                clearInterval(interval2);                                      
                alert('Verification Success...');  
                //PROSES DILANJUTKAN
                processingApproveFinger();
            }
        };

        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmployee", "", false, "json");

    };

    var processingApproveFinger= function(){
        var oidDetail = $("#FRM_FIELD_OID_DETIL_FINGER_APPROVE").val();
        command = "<%= Command.SAVE%>";
        var employee = "<%= employee.getOID()%>";
        var dataFor = "approveFinger";
        var generaldatafor = $("#fingerdatafor").val();
        var servletName=null;
        if(generaldatafor == "showEmpAwardAcknowledge"){
            servletName = "AjaxEmpAward";
        } else if(generaldatafor == "showEmpRepAcknowledge"){
            servletName = "AjaxEmpReprimand";
        } if(generaldatafor == "showEmpCareerAcknowledge"){
            servletName = "CareerAjax";
        } if(generaldatafor == "showEmpAcknowledge"){
            servletName = "AjaxEmployee";
        } if(generaldatafor == "showEmpWarningAcknowledge"){
            servletName = "AjaxEmpWarning";
        } if(generaldatafor == "showEmpDocGeneralAcknowledge"){
            servletName = "AjaxEmpDoc";
        }
        dataSend = {
            "FRM_FIELD_DATA_FOR"       : dataFor,                                       
            "FRM_FIELD_OID_DETAIL"     : oidDetail,
            "command"                  : command,
            "FRM_FIELD_EMP_ID"         : employee
        };

        onSuccess = function(data){

        };

        onDone = function(data){
            if(generaldatafor == "showEmpAwardAcknowledge"){
                runDataTables("showEmpAwardForm");
            } else if(generaldatafor == "showEmpRepAcknowledge"){
                runDataTables("showEmpReprimandForm");
            } else if(generaldatafor == "showEmpCareerAcknowledge"){
                runDataTables("career_form");
            } else if(generaldatafor == "showEmpAcknowledge"){
                $("#btnacknowledgeemp").html('<i class="fa fa-check-circle"></i> Acknowledged');
                $("#btnacknowledgeemp").removeClass('btn-warning').addClass('btn-success ');
                $(".btnacknowledge").removeAttr("id");
            } else if(generaldatafor == "showEmpWarningAcknowledge"){
                runDataTables("showempwarningform");
            } else if(generaldatafor == "showEmpDocGeneralAcknowledge"){
                runDataTables("showEmpGeneralDoc");
            }
                
            $("#modalApproveFinger").modal("hide");
        };

        getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, "", false, "json");

    };

    //SHOW VIEW FORM
    var viewDoc = function(elementId){
        $(elementId).click(function(){
            $("#viewdoc").modal("show");
            command = $("#command").val();
            oid = $(this).data('oid');
            dataFor = $(this).data('for');
            var title = $(this).html();
            var target = $(this).data('target');
            $("#viewdocform #generaldatafor").val(dataFor);
            $("#viewdocform #oid").val(oid);
            $("#viewdoc .modal-dialog").css("width", "90%");
            //SET TITLE MODAL
            $(".viewdoc-title").html("General Document");

            dataSend = {
                "FRM_FIELD_DATA_FOR"	: dataFor,
                "FRM_FIELD_OID"		 : oid,
                "command"		 : command
            }
            onDone = function(data){
                $(target).html(data.FRM_FIELD_HTML);
            };
            onSuccess = function(data){

            };
            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".viewdoc-body", false, "json");
        });
    };
    
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
    <div id="fingerModal" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="fingerModal-title"></h4>
		</div>
                <form id="frmFingerPatern" name="frmFingerPatern" method="post" action="">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
                    <input type="hidden" name="FRM_FIELD_APPROOT">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="datachange" id="datachange">
                    <input type="hidden" name="empId" value="<%= employee.getOID() %>">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body fingerModal-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<a class="btn btn-primary fingerSpot" id="buttonFinger" href="#" type="button">Save</a>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>  
    <div id="uploadFingerModal" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="uploadFingerModal-title"></h4>
		</div>
                <form id="frmUploadFinger" name="frmUploadFinger" method="post" action="">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
                    <input type="hidden" name="FRM_FIELD_APPROOT">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="datachange" id="datachange">
                    <input type="hidden" name="empId" value="<%= employee.getOID() %>">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body uploadFingerModal-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary" id="btnUploadFInger"><i class="fa fa-check"></i> Save</button>
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
                    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
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
    <div id="sendEmailModal" class="modal fade nonprint" >
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="title">Send Email</h4>
		</div>
                <form id="emailForm" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SUBMIT %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="empId" id="empId">
                    <input type="hidden" name="relevanDocId" id="relevanDocId">
                    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body sendemail-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnsend"><i class="fa fa-check"></i> Send</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <!-- MODAL FINGER-->
    <div id="modalApproveFinger" class="modal fade" tabindex="-1">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="modal-title-approve-finger" value="Select Finger"></h4>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <input type="hidden" name="FRM_FIELD_DATA_FOR" id="fingerdatafor">
                        <div class="col-md-12" id="dynamicFingerPatern">

                        </div>
                    </div>
                </div>
                <div class="modal-footer">                              
                    <button type="button" data-dismiss="modal" class="btn btn-danger">Close</button>
                </div>
            </div>
        </div>
    </div>  
<!--    <div id="editor" class="modal fade nonprint" tabindex="-1" style="overflow-y: auto">
          <div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="editor-title"></h4>
		</div>
                <form id="editorform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="template_id" id="template_id">
                    <input type="hidden" name="empId" id="empId">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
                                <textarea name='textarea' style='visibility: hidden; display: none;'></textarea>
				<div class="box-body editor-body">
                                    
				</div>
                                
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>-->
    <div id="viewdoc" class="modal fade nonprint" tabindex="-1" style="overflow-y: auto">
          <div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="viewdoc-title"></h4>
		</div>
                <form id="viewdocform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="template_id" id="template_id">
                    <input type="hidden" name="empId" id="empId">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
<!--                                <textarea id="editor2"></textarea>-->
				<div class="box-body viewdoc-body">
                                    
				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
                        <button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <div id="editor" class="modal fade nonprint" tabindex="-1" style="overflow-y: auto">
          <div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="editor-title"></h4>
		</div>
                <form id="editorform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="template_id" id="template_id">
                    <input type="hidden" name="empId" id="empId">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
                                <textarea name='<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DETAILS]%>' id='<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DETAILS]%>' style='visibility: hidden; display: none;'></textarea>
				<div class="box-body editor-body">
                                    
				</div>
                                
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btneditor"><i class="fa fa-check"></i> Save</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <div id="transferFingerModal" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="transferFingerModal-title"></h4>
		</div>
                <form id="frmTransferFinger" name="frmTransferFinger" method="post" action="">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
                    <input type="hidden" name="FRM_FIELD_APPROOT">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="datachange" id="datachange">
                    <input type="hidden" name="empId" value="<%= employee.getOID() %>">
                    <input type="hidden" name="fingerId" id="fingerId">
                    <input type="hidden" name="secondMachineStr" id="secondMachineStr">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body transferFingerModal-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary" id="btntransfer"><i class="fa fa-check"></i> Save</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>                             
    </body>
</html>
