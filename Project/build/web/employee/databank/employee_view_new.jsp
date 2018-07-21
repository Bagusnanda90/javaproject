<%-- 
    Document   : employee_view_new
    Created on : Dec 23, 2015, 3:27:46 PM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.harisma.session.employee.SessEmployeeView"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployeePicture"%>
<%@page import="com.dimata.harisma.form.employee.CtrlEmployee"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%
    long oidEmployee = emplx.getOID();
    SessEmployeeView sessEmpView = new SessEmployeeView();
    Employee employee = new Employee();
    if (oidEmployee != 0) {
        try {
            employee = PstEmployee.fetchExc(oidEmployee);
        } catch (Exception exc) {
            employee = new Employee();
            System.out.println("Exception employee" + exc);
        }
        // setting employee
        sessEmpView.setEmployeeId(oidEmployee);
    }
    
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
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Databank - Personal Data</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            .breadcrumb {
                background-color: #EEE;
                color:#0099FF;
                padding: 7px 9px;
            }
            .navbar {
                font-family: sans-serif;
                font-size: 12px;
                background-color: #0084ff;
                padding: 7px 9px;
                color : #FFF;
                border-top:1px solid #0084ff;
                border-bottom: 1px solid #0084ff;
            }
            .navbar ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
            }

            .navbar li {
                padding: 7px 9px;
                display: inline;
                cursor: pointer;
            }
            
            .navbar li a {
                color : #F5F5F5;
                text-decoration: none;
            }
            
            .navbar li a:hover {
                color:#FFF;
            }
            
            .navbar li a:active {
                color:#FFF;
            }

            .navbar li:hover {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }

            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                padding: 5px 25px 25px 25px;
                margin: 0px 23px 59px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .box {
                margin: 17px 7px;
                background-color: #FFF;
                color:#575757;
            }
            #box_title {
                padding:21px; 
                font-size: 14px; 
                color: #007fba;
                border-top: 1px solid #28A7D1;
                border-bottom: 1px solid #EEE;
            }
            #box_content {
                padding:21px; 
                font-size: 12px;
                color: #575757;
            }
            .box-info {
                padding:21px 43px; 
                background-color: #F7F7F7;
                border-bottom: 1px solid #CCC;
                -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                 -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
            }
            #title-info-name {
                padding: 11px 15px;
                font-size: 35px;
                color: #535353;
            }
            #title-info-desc {
                padding: 7px 15px;
                font-size: 21px;
                color: #676767;
            }
            
            #photo {
                padding: 7px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }
            .btn {
                background-color: #DDD;
                border-radius: 3px;
                font-family: Arial;
                color: #575757;
                font-size: 12px;
                padding: 9px 17px 9px 17px;
                text-decoration: none;
            }

            .btn:hover {
                color: #474747;
                background-color: #CCC;
                text-decoration: none;
            }
            
            .btn-small {
                text-decoration: none;
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            .btn-small:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }
            
            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }
            
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                margin-bottom: 2px;
                border-bottom: 1px solid #DDD;
                background-color: #EEE;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                padding: 21px;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                margin-top: 2px;
                background-color: #EEE;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 18px 21px;
                background-color: #FF6666;
                color: #FFF;
                border: 1px solid #CF5353;
            }
            #btn-confirm {
                padding: 3px; border: 1px solid #CF5353; 
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px;
            }
            
        </style>
        <script type="text/javascript">
            function cmdPrint(){
                window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeDetailPdf?oid=<%=oidEmployee%>");
            }
        </script>
    </head>
    <body>
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
            <%}%>
            </table>
        </div>
        <div id="menu_utama">
            <span id="menu_title">Databank <strong style="color:#333;"> / </strong> <%=dictionaryD.getWord(I_Dictionary.PERSONAL_DATA)%></span>
        </div>
        <% if (oidEmployee != 0) {%>
            <div class="navbar">
                <ul style="margin-left: 97px">
                    <li class="active"> <a href="employee_view_new.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.PERSONAL_DATA)%></a> </li>
                    <li class=""> <a href="familymember_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.FAMILY_MEMBER) %></a> </li>
                    <li class=""> <a href="emplanguage_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.COMPETENCIES) %></a> </li>
                    <li class=""> <a href="empeducation_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></a> </li>
                    <li class=""> <a href="experience_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EXPERIENCE) %></a></li>
                    <li class=""> <a href="careerpath_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></a> </li>
                    <li class=""> <a href="training_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.TRAINING_ON_DATABANK)%></a> </li>
                    <li class=""> <a href="warning_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.WARNING) %></a> </li>
                    <li class=""> <a href="reprimand_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.REPRIMAND) %></a> </li>
                    <li class=""> <a href="award_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.AWARD) %></a> </li>
                    <li class=""> <a href="doc_relevant_view.jsp" class="tablink"><%=dictionaryD.getWord(I_Dictionary.RELEVANT_DOCS) %></a> </li>
                    <li class=""> <a href="update_pin.jsp" class="tablink">Ubah PIN</a> </li>
                </ul>
            </div>
        <%}%>
        <div class="box-info">
            <table>
                <tr>
                    <td>
                        <%
                        String pictPath = "";
                        try {
                            SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                            pictPath = sessEmployeePicture.fetchImageEmployee(employee.getOID());

                        } catch (Exception e) {
                            System.out.println("err." + e.toString());
                        }%> 
                        <%
                             if (pictPath != null && pictPath.length() > 0) {
                                out.println("<img height=\"135\" id=\"photo\" title=\"Click here to upload\"  src=\"" + approot + "/" + pictPath + "\">");
                             } else {
                        %>
                        <img width="135" height="135" id="photo" src="<%=approot%>/imgcache/no-img.jpg" />
                        <%
                            }
                        %>
                    </td>
                    <td style="padding-left: 15px;">
                        <div id="title-info-name"><%=employee.getFullName()%> [<%=employee.getEmployeeNum()%>]</div>
                        <div id="title-info-desc"><%=PstEmployee.getCompanyStructureName(employee.getOID())%></div>
                    </td>
                    <td valign="top">
                        <a style="color:#575757" class="btn" href="javascript:cmdPrint()">Print CV</a>
                    </td>
                </tr>
            </table>
        </div>
        <div class="content-main">
                <table>
                    <tr>
                        <td valign="top" width="30%"> <!-- Left Side -->
                            <div class="box">
                                <div id="box_title">Basic Information</div>
                                <div id="box_content">
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.PAYROLL)%></div>
                                    <div id="divinput"><%= employee.getEmployeeNum() %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME)%></div>
                                    <div id="divinput"><%= employee.getFullName() %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.ADDRESS)%></div>
                                    <div id="divinput"><%= employee.getAddress() %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.PERMANENT_ADDRESS)%></div>
                                    <div id="divinput"><%= employee.getAddressPermanent() %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.EMERGENCY_PHONE)%> / <%=dictionaryD.getWord("PERSON_NAME")%></div>
                                    <div id="divinput"><%=(employee.getPhoneEmergency() != null ? employee.getPhoneEmergency() : "-")%> / <%=employee.getNameEmg() != null ? employee.getNameEmg() : "-" %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.EMERGENCY_ADDRESS)%></div>
                                    <div id="divinput"><%=employee.getAddressEmg() != null ? employee.getAddressEmg():"-" %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.GENDER)%></div>
                                    <div id="divinput"><%= sessEmpView.getGender(employee.getSex()) %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.PLACE_OF_BIRTH)%></div>
                                    <div id="divinput"><%=employee.getBirthPlace() != null ? employee.getBirthPlace():"-"%></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.DATE_OF_BIRTH)%></div>
                                    <div id="divinput"><%= employee.getBirthDate() == null ? "-" : employee.getBirthDate() %></div>
                                    
                                    <div id="caption">Shio</div>
                                    <div id="divinput"><%=employee.getShio() != null ? employee.getShio():"-" %></div>
                                    
                                    <div id="caption">Element</div>
                                    <div id="divinput"><%=employee.getElemen() != null ? employee.getElemen():"-" %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.RELIGION)%></div>
                                    <div id="divinput"><%= sessEmpView.getReligion(employee.getReligionId()) %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord("MARITAL_STATUS_FOR_HR")%></div>
                                    <div id="divinput"><%= sessEmpView.getMaritalName(employee.getMaritalId()) %> | for Tax Report <%= sessEmpView.getMaritalName(employee.getTaxMaritalId()) %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord("BLOOD_TYPE")%></div>
                                    <div id="divinput"><%= employee.getBloodType() %></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord("RACE")%></div>
                                    <div id="divinput"><%= sessEmpView.getRaceName(employee.getRace()) %></div>
                                </div>
                            </div>
                            <div class="box">
                                <div id="box_title">Parent Information</div>
                                <div id="box_content">
                                    <div id="caption">Nama Lengkap Ayah</div>
                                    <div id="divinput"><%=employee.getFather()%></div>
                                    
                                    <div id="caption">Nama Lengkap Ibu</div>
                                    <div id="divinput"><%=employee.getMother()%></div>
                                    
                                    <div id="caption">Alamat Orang Tua</div>
                                    <div id="divinput"><%=employee.getParentsAddress()%></div>
                                </div>
                            </div>
                        </td>
                        <td valign="top" width="30%"> <!-- Center Side -->
                            <div class="box">
                                <div id="box_title">Company Information</div>
                                <div id="box_content">
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.COMPANY)%></div>
                                    <div id="divinput">
                                        <%= sessEmpView.getCompanyName(employee.getCompanyId()) %>
                                    </div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></div>
                                    <div id="divinput">
                                        <%= sessEmpView.getDivisionName(employee.getDivisionId()) %>
                                    </div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></div>
                                    <div id="divinput">
                                        <%= sessEmpView.getDepartmentName(employee.getDepartmentId()) %>
                                    </div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord("SECTION")%></div>
                                    <div id="divinput">
                                        <%= sessEmpView.getSectionName(employee.getSectionId()) %>
                                    </div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%> <%=dictionaryD.getWord("CATEGORY")%></div>
                                    <div id="divinput"><%= sessEmpView.getEmpCategory(employee.getEmpCategoryId()) %></div>
                                    
                                    <div id="caption">Level & Grade Level</div>
                                    <div id="divinput">
                                        <%= sessEmpView.getLevelName(employee.getLevelId()) %>
                                        &nbsp;-&nbsp;
                                        <%
                                        int SetGrade = 1;
                                        try{
                                            SetGrade = Integer.valueOf(PstSystemProperty.getValueByName("USE_GRADE_SET")); 
                                        } catch (Exception e){
                                           System.out.printf("GRADE DAN LOCATION TIDAK DI SET?"); 
                                        }
                                        if (SetGrade==1) {
                                        %>
                                        <%= sessEmpView.getGradeLevel(employee.getGradeLevelId()) %>
                                        <% } %>
                                    </div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.POSITION)%></div>
                                    <div id="divinput">
                                        <%= sessEmpView.getPositionName(employee.getPositionId()) %>
                                    </div>
                                </div>
                            </div>
                            <div class="box">
                                <div id="box_title">Work Assign Information</div>
                                <div id="box_content">
                                    <div id="caption">W.A. <%=dictionaryD.getWord(I_Dictionary.COMPANY)%></div>
                                    <div id="divinput">
                                        <%= sessEmpView.getCompanyName(employee.getWorkassigncompanyId()) %>
                                    </div>
                                    
                                    <div id="caption">W.A. <%=dictionaryD.getWord(I_Dictionary.DIVISION)%></div>
                                    <div id="divinput">
                                        <%= sessEmpView.getDivisionName(employee.getWorkassigndivisionId()) %>
                                    </div>
                                    
                                    <div id="caption">W.A. <%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></div>
                                    <div id="divinput">
                                        <%= sessEmpView.getDepartmentName(employee.getWorkassigndepartmentId()) %>
                                    </div>
                                    
                                    <div id="caption">W.A. <%=dictionaryD.getWord("SECTION")%></div>
                                    <div id="divinput">
                                        <%= sessEmpView.getSectionName(employee.getWorkassignsectionId()) %>
                                    </div>
                                </div>
                            </div>
                            <div class="box">
                                <div id="box_title">Contact Information</div>
                                <div id="box_content">
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.ZIP_CODE)%></div>
                                    <div id="divinput"><%= employee.getPostalCode() %></div>
                                    
                                    <div id="caption">Telephone / HP</div>
                                    <div id="divinput"><%= employee.getPhone() %> / <%=employee.getHandphone()%></div>
                                    
                                    <div id="caption">Email</div>
                                    <div id="divinput"><%=employee.getEmailAddress()%></div>
                                </div>
                            </div>
                            <div class="box">
                                <div id="box_title">Custom Information</div>
                                <div id="box_content">
                                    <%
                                    Vector listCustom = PstCustomFieldMaster.list(0, 0, "", "");
                                    if (listCustom != null && listCustom.size()>0){
                                        for(int i=0; i<listCustom.size(); i++){
                                            String valueEmpCust = "-";
                                            CustomFieldMaster custom = (CustomFieldMaster)listCustom.get(i);
                                            String whereEmpCust = "CUSTOM_FIELD_ID="+custom.getOID()+" AND EMPLOYEE_ID="+employee.getOID();
                                            Vector listEmpCust = PstEmpCustomField.list(0, 0, whereEmpCust, "");
                                            if (listEmpCust != null && listEmpCust.size()>0){
                                                for(int j=0; j<listEmpCust.size(); j++){
                                                    EmpCustomField empCust = (EmpCustomField)listEmpCust.get(j);
                                                    switch(custom.getFieldType()){
                                                        case 0: valueEmpCust = empCust.getDataText(); break;
                                                        case 1: valueEmpCust = ""+empCust.getDataNumber(); break;
                                                        case 2: valueEmpCust = ""+empCust.getDataNumber(); break;
                                                        case 3: valueEmpCust = ""+empCust.getDataDate(); break;
                                                        case 4: valueEmpCust = ""+empCust.getDataDate(); break;
                                                    }
                                                }
                                            }
                                            %>
                                            <div id="caption"><%= custom.getFieldName() %></div>
                                            <div id="divinput"><%= valueEmpCust %></div>
                                            <%
                                        }
                                    }
                                    %>
                                    
                                </div>
                            </div>
                        </td>
                        <td valign="top" width="30%"> <!-- Right Side -->
                            <div class="box">
                                <div id="box_title">Account Information</div>
                                <div id="box_content">
                                    <div id="caption"><%=dictionaryD.getWord("BARCODE_NUM")%></div>
                                    <div id="divinput"><%=(employee.getBarcodeNumber() != null ? employee.getBarcodeNumber() : "-")%></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.ID_CARD_NO)%> | <%=dictionaryD.getWord(I_Dictionary.TYPE)%> | <%=dictionaryD.getWord("VALID_TO")%></div>
                                    <div id="divinput"><%=employee.getIndentCardNr()%> | <%= employee.getIdcardtype() %> | <%= employee.getIndentCardValidTo() == null ? "-" : employee.getIndentCardValidTo() %></div>
                                    
                                    <div id="caption">Payroll Group</div>
                                    <div id="divinput"><%=employee.getPayrollGroup()%></div>
                                    
                                    <div id="caption">Nomor Rekening</div>
                                    <div id="divinput"><%=employee.getNoRekening()%></div>
                                    
                                    <div id="caption">NPWP</div>
                                    <div id="divinput"><%=(employee.getNpwp() != null ? employee.getNpwp() : "-")%></div>
                                    
                                    <div id="caption">BPJS Ketenaga Kerjaan Number</div>
                                    <div id="divinput"><%=employee.getAstekNum()%></div>
                                    
                                    <div id="caption">BPJS Ketenaga Kerjaan Date</div>
                                    <div id="divinput"><%=employee.getAstekDate() == null ? "-" : employee.getAstekDate()%></div>
                                    
                                    <div id="caption">BPJS Kesehatan No.</div>
                                    <div id="divinput"><%=employee.getBpjs_no()!= null ? employee.getBpjs_no():"-"%></div>
                                    
                                    <div id="caption">BPJS Kesehatan Date</div>
                                    <div id="divinput"><%=(employee.getBpjs_date() != null ? employee.getBpjs_date() : "-")%></div>
                                    
                                    <div id="caption">Member of BPJS Kesehatan</div>
                                    <div id="divinput">
                                        <% for (int i = 0; i < PstEmployee.memberOfBPJSKesehatanValue.length; i++) {
                                                String strMemOfBpjsKesehatan = "";
                                                if (employee.getMemOfBpjsKesahatan() == PstEmployee.memberOfBPJSKesehatanValue[i]) {
                                                    strMemOfBpjsKesehatan = "checked";
                                                }
                                        %> <input type="radio" name="bpjs_kesehatan" value="<%="" + PstEmployee.memberOfBPJSKesehatanValue[i]%>" <%=strMemOfBpjsKesehatan%> style="border:'none'">
                                        <%=PstEmployee.memberOfBPJSKesehatanKey[i]%> <%}%> 
                                    </div>
                                    
                                    <div id="caption">Member of BPJS Ketenagakerjaan</div>
                                    <div id="divinput">
                                        <% for (int i = 0; i < PstEmployee.memberOfBPJSKetenagaKerjaanValue.length; i++) {
                                                String strMemOfBpjsKetenagaKerjaan = "";
                                                if (employee.getMemOfBpjsKetenagaKerjaan() == PstEmployee.memberOfBPJSKetenagaKerjaanValue[i]) {
                                                    strMemOfBpjsKetenagaKerjaan = "checked";
                                                }
                                        %> <input type="radio" name="bpjs_ketenagakrja" value="<%="" + PstEmployee.memberOfBPJSKetenagaKerjaanValue[i]%>" <%=strMemOfBpjsKetenagaKerjaan%> style="border:'none'">
                                        <%=PstEmployee.memberOfBPJSKetenagaKerjaanKey[i]%> <%}%> 
                                    </div>
                                </div>
                            </div>
                            <div class="box">
                                <div id="box_title">Default Schedule</div>
                                <div id="box_content">
                                    <%
                                    String whereClauseDS = PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]+"="+employee.getOID();
                                    String orderDS= PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX] ;
                                    Vector dftSchedules = PstDefaultSchedule.list(0, 35, whereClauseDS, orderDS);
                                    //Vector dftSchedules = PstDefaultSchedule.list(0, 7, whereClauseDS, orderDS);
                                   %>
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
                                               <td><div id="div_item_sch"><%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%></div> </td>
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
                                               <td><div id="div_item_sch"><%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%></div></td>
                                               <%
                                               }%>
                                       </tr>
                                       <tr>
                                           <td> Week 3rd </td>
                                           <%                    
                                             for(int idx=15;idx <= 21; idx++){
                                               DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                               %>
                                               <td><div id="div_item_sch"><%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%></div></td>
                                               <%
                                               }%>
                                       </tr>
                                       <tr>
                                           <td> Week 4th </td>
                                           <%                    
                                             for(int idx=22;idx <= 28; idx++){
                                               DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                               %>
                                               <td><div id="div_item_sch"><%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%></div></td>
                                               <%
                                               }%>
                                       </tr>
                                       <tr>
                                           <td> Week 5th </td>
                                           <%                    
                                             for(int idx=29;idx <=35; idx++){
                                               DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                                               %>
                                               <td><div id="div_item_sch"><%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()): "-" )%></div> </td>
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
                                               <td><div id="div_item_sch"><%=( dfltSch.getDayIndex()==idx? PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule2()): "-" )%></div></td>
                                               <%
                                               }%>                
                                       </tr>
                                       <%}%>
                                   </table>
                                </div>
                            </div>
                            <div class="box">
                                <div id="box_title">Other Information</div>
                                <div id="box_content">
                                    <div id="caption"><%=dictionaryD.getWord("COMMENCING_DATE")%></div>
                                    <div id="divinput"><%=employee.getCommencingDate() == null ? "-" : employee.getCommencingDate()%></div>
                                    
                                    <div id="caption"><%=dictionaryD.getWord("PROBATION_END_DATE")%></div>
                                    <div id="divinput"><%=employee.getProbationEndDate() == null ? "-" : employee.getProbationEndDate()%></div>
                                    
                                    <div id="caption">Status Pensiun Program</div>
                                    <div id="divinput">
                                        <% for (int i = 0; i < PstEmployee.statusPensiunProgramValue.length; i++) {
                                                String strStPensiun = "";
                                                if (employee.getStatusPensiunProgram() == PstEmployee.statusPensiunProgramValue[i]) {
                                                        strStPensiun = "checked";
                                                }
                                        %> <input type="radio" name="statuspension" value="<%="" + PstEmployee.statusPensiunProgramValue[i]%>" <%=strStPensiun%> style="border:'none'">
                                        <%=PstEmployee.statusPensiunProgramKey[i]%> <%}%>
                                    </div>
                                    
                                    <div id="caption">Start Date Program Pensiun</div>
                                    <div id="divinput"><%=employee.getStartDatePensiun()%></div>
                                    
                                    <div id="caption">Resign Status</div>
                                    <div id="divinput">
                                        <% for (int i = 0; i < PstEmployee.resignValue.length; i++) {
                                                String strRes = "";
                                                if (employee.getResigned() == PstEmployee.resignValue[i]) {
                                                    strRes = "checked";
                                                }
                                        %> <input type="radio" name="resignstatus" value="<%="" + PstEmployee.resignValue[i]%>" <%=strRes%> style="border:'none'">
                                        <%=PstEmployee.resignKey[i]%> <%}%> 
                                    </div>
                                    
                                    <div id="caption">Resign Date</div>
                                    <div id="divinput"><%=employee.getResignedDate()%></div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>
