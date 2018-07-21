<%-- 
    Document   : data_check
    Created on : 05-May-2017, 21:08:47
    Author     : Gunadi
--%>

<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.session.employee.SessTmpSpecialEmployee"%>
<%@page import="com.dimata.harisma.session.employee.SessSpecialEmployee"%>
<%@page import="com.dimata.harisma.session.employee.SearchSpecialQuery"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!DOCTYPE html>

<%
            response.setHeader("Content-Disposition","attachment;filename= List_Datacheck.xls");
            SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
//            searchSpecialQuery = (SearchSpecialQuery) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
//            if (searchSpecialQuery == null){
//                searchSpecialQuery = new SearchSpecialQuery();
//            }
        
            String empNum = FRMQueryString.requestString(request, "employee_num");
            String empName = FRMQueryString.requestString(request, "employee_name");
            long companyId = FRMQueryString.requestLong(request, "company_id");
            long divisionId = FRMQueryString.requestLong(request, "division_id");
            long departmentId = FRMQueryString.requestLong(request, "department_id");
            long sectionId = FRMQueryString.requestLong(request, "section_id");
            long positionId = FRMQueryString.requestLong(request, "position_id");
            int resign = FRMQueryString.requestInt(request, "resign");
            String inEmpId = FRMQueryString.requestString(request, "inEmpId");
            long levelId = FRMQueryString.requestLong(request, "level_id");
            long empCatId = FRMQueryString.requestLong(request, "emp_category_id");
            long maritalId = FRMQueryString.requestLong(request, "marital_id");
            long raceId = FRMQueryString.requestLong(request, "race_id");
            long religionId = FRMQueryString.requestLong(request, "religion_id");
            int birthMonth = FRMQueryString.requestInt(request, "birth_month");

            String whereClause = "";
            Vector whereVect = new Vector();
            if (empNum.length() > 0){
                    searchSpecialQuery.setEmpnumber(empNum);
                }
                if (empName.length() > 0){
                    searchSpecialQuery.setFullNameEmp(empName);
                }
                if (companyId != 0){
                    String [] company = {""+companyId};
                    searchSpecialQuery.addArrCompany(company);
                }
                if (divisionId != 0){
                    String [] division = {""+divisionId};
                    searchSpecialQuery.addArrDivision(division);
                }
                if (departmentId != 0){
                    String [] department = {""+departmentId};
                    searchSpecialQuery.addArrDepartmentId(department);
                }
                if (sectionId != 0){
                    String [] section = {""+sectionId};
                    searchSpecialQuery.addArrSectionId(section);
                }
                if (positionId != 0){
                    String [] position = {""+positionId};
                    searchSpecialQuery.addArrPositionId(position);
                }
                if (resign > -1){
                    /* tampilkan hanya yang tidak resign */
                    if (resign == 0){
                        searchSpecialQuery.setiResigned(resign);
                    }
                    /* tampilkan yang resign dan yang tidak resign*/
                    if (resign == 1){
                        searchSpecialQuery.setiResigned(resign);
                    }
                }
    //            if (!(inEmpId.equals(""))){
    //                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" IN ("+inEmpId+") ";
    //                whereVect.add(whereClause);
    //            }
                if (empCatId != 0){
                    String [] category = {""+empCatId};
                    searchSpecialQuery.addArrEmpCategory(category);
                }
                if (levelId != 0){
                    String [] level = {""+levelId};
                    searchSpecialQuery.addArrLevelId(level);
                }
                if (maritalId != 0){
                    String [] marital = {""+maritalId};
                    searchSpecialQuery.addArrMaritalId(marital);
                }
                if (raceId != 0){
                    String [] race = {""+raceId};
                    searchSpecialQuery.addArrRaceId(race);
                }
                if (religionId != 0){
                    String [] religion = {""+religionId};
                    searchSpecialQuery.addArrReligionId(religion);
                }
                if (birthMonth != 0){
                    searchSpecialQuery.setBirthMonth(birthMonth);
                }
                searchSpecialQuery.setStartResign(null);
                searchSpecialQuery.setEndResign(null);
                searchSpecialQuery.setDtBirthFrom(null);
                searchSpecialQuery.setDtBirthTo(null);
                searchSpecialQuery.setDtCarrierWorkEnd(null);
                searchSpecialQuery.setDtCarrierWorkStart(null);
                searchSpecialQuery.setDtPeriodEnd(null);
                searchSpecialQuery.setDtPeriodStart(null);
                searchSpecialQuery.setiSex(2);
                
                Vector listEmployee = SessSpecialEmployee.searchSpecialEmployee(searchSpecialQuery, 0, 50000);
                
                String[] relDocCheck = PstSystemProperty.getValueByName("OID_RELEVANT_DOC_GROUP_FOR_CHECK").split(",");

%>

<%@page contentType="application/x-msexcel" pageEncoding="UTF-8"%>

<html>
    <head>
        <title></title>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
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
        <style type="text/css">
            
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC; background-color: #F7F7F7;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757; }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
            #menu_teks {color:#CCC;}
            #box_title {padding:9px; background-color: #D5D5D5; font-weight: bold; color:#575757; margin-bottom: 7px; }
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

            .navbar li:hover {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }

            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
        </style>
    </head>
    <body>
        <table border="1">
               <tr>
			<td rowspan="2" style="text-align: center;">No</td>
                        <td rowspan="2" style="text-align: center;">Name</td>
                        <td rowspan="2" style="text-align: center;">Payroll</td>
			<td colspan="11" rowspan="1" style="text-align: center;">Company Information</td>
			<td colspan="26" rowspan="1" style="text-align: center;">Personal Data</td>
                        <td colspan="4" rowspan="1" style="text-align: center;">Career Acknowledge</td>
                        <% if (relDocCheck.length > 0){ %>
			<td colspan="<%=relDocCheck.length %>" rowspan="1" style="text-align: center;">Relevant Document</td>
                        <% } %>
		</tr>
                        <tr style="text-align: center">
                            <td>Company</td>
                            <td>Division</td>
                            <td>Department</td>
                            <td>Section</td>
                            <td>Sub Section</td>
                            <td>Employee Category</td>
                            <td>Level</td>
                            <td>Position</td>
                            <td>Commencing Date</td>
                            <td>Probation</td>
                            <td>Contract</td>
                            <td>Temporary Address</td>  
                            <td>Permanent Address</td>
                            <td>Zip Code</td>
                            <td>Telephone</td>
                            <td>Handphone</td>
                            <td>Emergency Phone</td>
                            <td>Person Name</td>
                            <td>Emergency Address</td>
                            <td>Place of Birth </td>
                            <td>Date of Birth</td>
                            <td>Religion</td>
                            <td>Marital Status</td>
                            <td>Blood Type</td>
                            <td>Race</td>
                            <td>ID Card</td>
                            <td>ID Card Valid Until</td>
                            <td>Secondary ID Card</td>
                            <td>Secondary ID Card Valid Until</td>
                            <td>Email</td>
                            <td>No Rekening</td>
                            <td>NPWP</td>
                            <td>Family</td>
                            <td>Competencies</td>
                            <td>Language</td>
                            <td>Education</td>
                            <td>Experience</td>
                            <td>Career Path</td>
                            <td>Warning</td>
                            <td>Reprimand</td>
                            <td>Award</td>
                            <% if (relDocCheck.length > 0){ 
                                for (int i = 0; i < relDocCheck.length; i++) {
                                    EmpRelevantDocGroup docGroup = new EmpRelevantDocGroup();
                                    try {
                                        docGroup = PstEmpRelevantDocGroup.fetchExc(Long.valueOf(relDocCheck[i]));
                                    } catch(Exception exc){}
                            %>
                                <td><%=docGroup.getDocGroup()%></td>
                            <% } 
                                } 
                            %>
                        </tr>
                        <%
                            if (listEmployee.size() >0){
                                 SessTmpSpecialEmployee sessTmpSpecialEmployee = new SessTmpSpecialEmployee();
                                for (int i=0; i < listEmployee.size(); i++){
                                    sessTmpSpecialEmployee = (SessTmpSpecialEmployee) listEmployee.get(i);
                                    
                                    Company comp = new Company();
                                    Division div = new Division();
                                    Department dept = new Department();
                                    Section sec = new Section();
                                    SubSection subSec = new SubSection();
                                    EmpCategory empCat = new EmpCategory();
                                    Level level = new Level();
                                    Position pos = new Position();
                                    
                                    String whereFamily = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId();
                                    String whereCompetency = PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId();
                                    String whereLanguage = PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId();
                                    String whereEducation = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId();
                                    String whereExperience = PstExperience.fieldNames[PstExperience.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId();
                                    
                                    Vector listFamily = PstFamilyMember.list(0, 0, whereFamily, "");
                                    Vector listCompetency = PstEmployeeCompetency.list(0, 0, whereCompetency, "");
                                    Vector listLanguage = PstEmpLanguage.list(0,0, whereLanguage, "");
                                    Vector listEducation = PstEmpEducation.list(0,0,whereLanguage,"");
                                    Vector listExperience = PstExperience.list(0,0,whereExperience,"");
                                    
                                    String whereCareer = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId()+" AND "
                                                         + PstCareerPath.fieldNames[PstCareerPath.FLD_ACKNOWLEDGE_STATUS]+" = 0";
                                    String whereWarning = PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId()+" AND "
                                                         + PstEmpWarning.fieldNames[PstEmpWarning.FLD_ACKNOWLEDGE_STATUS]+" = 0";
                                    String whereReprimand = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId()+" AND "
                                                         + PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_ACKNOWLEDGE_STATUS]+" = 0";
                                    String whereAward = PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId()+" AND "
                                                         + PstEmpAward.fieldNames[PstEmpAward.FLD_ACKNOWLEDGE_STATUS]+" = 0";
                                    
                                    Vector listCareer = PstCareerPath.list(0, 0, whereCareer, "");
                                    Vector listWarning = PstEmpWarning.list(0,0,whereWarning,"");
                                    Vector listReprimand = PstEmpReprimand.list(0,0, whereReprimand, "");
                                    Vector listAward = PstEmpAward.list(0,0,whereAward,"");
                                    
                                    
                                    
                        %>
                        <tr>
                            <td><%=i+1%></td>
                            <td><%=sessTmpSpecialEmployee.getFullName()%></td>
                            <td>="<%=sessTmpSpecialEmployee.getEmployeeNum()%>"</td>
                            <td><%=sessTmpSpecialEmployee.getCompanyName()%></td>
                            <td><%=sessTmpSpecialEmployee.getDivision()%></td>
                            <td><%=sessTmpSpecialEmployee.getDepartement()%></td>
                            <td><%=sessTmpSpecialEmployee.getSection()%></td>
                            <% if (sessTmpSpecialEmployee.getSubSection() != null){ %>
                            <td><%=sessTmpSpecialEmployee.getSubSection()%></td>
                            <% } else { %>
                            <td></td>
                            <% } %>
                            <td><%=sessTmpSpecialEmployee.getEmpCategory()%></td>
                            <td><%=sessTmpSpecialEmployee.getLevel()%></td>
                            <td><%=sessTmpSpecialEmployee.getPosition()%></td>
                            <% if (sessTmpSpecialEmployee.getCommercingDateEmployee() != null){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (sessTmpSpecialEmployee.getProbationEndDate() != null){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (sessTmpSpecialEmployee.getEndContractEmployee() != null){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (sessTmpSpecialEmployee.getAddressEmployee() != null && !sessTmpSpecialEmployee.getAddressEmployee().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (sessTmpSpecialEmployee.getAddressPermanent() != null && !sessTmpSpecialEmployee.getAddressPermanent().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (sessTmpSpecialEmployee.getPostalCodeEmployee() > 0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>                            
                            
                            <% if (sessTmpSpecialEmployee.getPhoneEmployee() != null && !sessTmpSpecialEmployee.getPhoneEmployee().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>   
                            
                            <% if (sessTmpSpecialEmployee.getHandphone() != null && !sessTmpSpecialEmployee.getHandphone().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
 
                            <% if (sessTmpSpecialEmployee.getPhoneEmg() != null && !sessTmpSpecialEmployee.getPhoneEmg().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>                               
                            
                            <% if (sessTmpSpecialEmployee.getNameEmg() != null && !sessTmpSpecialEmployee.getNameEmg().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>   
                            
                            <% if (sessTmpSpecialEmployee.getAddressEmg() != null && !sessTmpSpecialEmployee.getAddressEmg().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>          
                            
                            <% if (sessTmpSpecialEmployee.getBirthPlaceEmployee() != null && !sessTmpSpecialEmployee.getBirthPlaceEmployee().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (sessTmpSpecialEmployee.getBirthDateEmployee() != null){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (sessTmpSpecialEmployee.getReligion() != null && !sessTmpSpecialEmployee.getReligion().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (sessTmpSpecialEmployee.getMaritalStatus() != null && !sessTmpSpecialEmployee.getMaritalStatus().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (sessTmpSpecialEmployee.getBloodTypeEmployee() != null && !sessTmpSpecialEmployee.getBloodTypeEmployee().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (sessTmpSpecialEmployee.getRaceName() != null && !sessTmpSpecialEmployee.getRaceName().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (sessTmpSpecialEmployee.getIndentCardNr() != null && !sessTmpSpecialEmployee.getIndentCardNr().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (sessTmpSpecialEmployee.getIdCardValid() != null){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (sessTmpSpecialEmployee.getSecondaryIdNo() != null && !sessTmpSpecialEmployee.getSecondaryIdNo().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>  
                            
                            <% if (sessTmpSpecialEmployee.getSecondaryIdValid() != null){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>   
                            
                            <% if (sessTmpSpecialEmployee.getEmail() != null && !sessTmpSpecialEmployee.getEmail().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (sessTmpSpecialEmployee.getNoRekening() != null && !sessTmpSpecialEmployee.getNoRekening().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>  
                            
                            <% if (sessTmpSpecialEmployee.getNpwp() != null && !sessTmpSpecialEmployee.getNpwp().equals("")){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (listFamily.size()>0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>    
                            
                            <% if (listCompetency.size()>0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (listLanguage.size()>0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %> 
                            
                            <% if (listEducation.size()>0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (listExperience.size()>0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (listCareer.size() == 0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (listWarning.size() == 0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (listReprimand.size() == 0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <% if (listAward.size() == 0){ %>
                            <td></td>
                            <% } else { %>
                            <td bgcolor="#606060"></td>
                            <% } %>
                            
                            <%
                                if (relDocCheck.length > 0){ 
                                for (int x = 0; x < relDocCheck.length; x++) {
                                    String whereDoc = PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMPLOYEE_ID]+"="+sessTmpSpecialEmployee.getEmployeeId()+
                                                        " AND "+PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMP_RELVT_DOC_GRP_ID]+" = "+relDocCheck[x];
                                    Vector listDoc = PstEmpRelevantDoc.list(0,0,whereDoc,"");
                                    if (listDoc != null && listDoc.size()>0) {
                            %>
                                <td></td>
                            <%      } else { %>
                                <td bgcolor="#606060"></td>
                            <%
                                                       }
                                }
                                }
                            
                            %>
                            
                            
                        </tr>
                        <%
                                }
                            }
                        
                        %>
                    </table>
        
    </body>
</html>