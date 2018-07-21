<%-- 
    Document   : candidate_selection
    Created on : Sep 20, 2015, 5:22:18 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.session.employee.SessSpecialEmployee"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.harisma.entity.search.SrcSpecialEmployee"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployee"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
 long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<!DOCTYPE html>
<%!
    public String getTypeName(long typeId){
        String str = "";
        try {
            TrainType train = PstTrainType.fetchExc(typeId);
            str = train.getTypeName();
        } catch(Exception e){
            System.out.println(""+e.toString());
        }
        return str;
    }
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long candidateMainId = FRMQueryString.requestLong(request, "candidate_main_id");
String[] educationId = FRMQueryString.requestStringValues(request, "chx_edu");
String[] trainingId = FRMQueryString.requestStringValues(request, "chx_training");
String[] competencyId = FRMQueryString.requestStringValues(request, "chx_competency");
String[] kpiId = FRMQueryString.requestStringValues(request, "chx_kpi");

String[] selectCompany = FRMQueryString.requestStringValues(request, "select_company");
String[] selectDivision = FRMQueryString.requestStringValues(request, "select_division");
String[] selectDepart = FRMQueryString.requestStringValues(request, "select_depart");
String[] selectSection = FRMQueryString.requestStringValues(request, "select_section");
String[] selectPosition = FRMQueryString.requestStringValues(request, "select_position");
String[] selectLevel = FRMQueryString.requestStringValues(request, "select_level");
String[] selectCategory = FRMQueryString.requestStringValues(request, "select_category");
int rbSex = FRMQueryString.requestInt(request, "rb_sex");

int typeMenu = FRMQueryString.requestInt(request, "type_menu");

String titleMenu = "";
if (typeMenu == 0){
    titleMenu = "Candidate";
} else {
    titleMenu = "Talent";
}
String whereClause = "";
String orderBy = "";

if (iCommand == Command.SAVE && candidateMainId > 0){
    if (educationId == null || educationId.length == 0 ){
        educationId = new String[1];
        educationId[0] = "0";
    }
    if (trainingId == null || trainingId.length == 0){
        trainingId = new String[1];
        trainingId[0] = "0";
    }
    if (competencyId == null || competencyId.length == 0){
        competencyId = new String[1];
        competencyId[0] = "0";
    }
    if (kpiId == null || kpiId.length == 0){
        kpiId = new String[1];
        kpiId[0] = "0";
    }
    
    int size = -1;
    int eduSize = educationId.length;
    int trainSize = trainingId.length;
    int compSize = competencyId.length;
    int kpiSize = kpiId.length;

    /* menentukan size */
    if (eduSize > size){
        size = eduSize;
    }
    if (trainSize > size){
        size = trainSize;
    }
    if (compSize > size){
        size = compSize;
    }
    if (kpiSize > size){
        size = kpiSize;
    }
    
    String[] arrEducation = new String[size];
    String[] arrTraining = new String[size];
    String[] arrCompetency = new String[size];
    String[] arrKPI = new String[size];
    
    
    if (size > 0){
        for(int h=0; h<size; h++){
            arrEducation[h] = "0";
            arrTraining[h] = "0";
            arrCompetency[h] = "0";
            arrKPI[h] = "0";
        }
        for(int e=0; e<educationId.length; e++){
            arrEducation[e] = educationId[e];
        }
        for(int t=0; t<trainingId.length; t++){
            arrTraining[t] = trainingId[t];
        }
        for(int c=0; c<competencyId.length; c++){
            arrCompetency[c] = competencyId[c];
        }
        for(int k=0; k<kpiId.length; k++){
            arrKPI[k] = kpiId[k];
        }
        
        CandidateSelection canSelection = new CandidateSelection();
        for(int i=0; i<size; i++){
            canSelection.setCandidateMainId(candidateMainId);
            canSelection.setCandidateEducationId(Long.valueOf(arrEducation[i]));
            canSelection.setCandidateTrainingId(Long.valueOf(arrTraining[i]));
            canSelection.setCandidateCompetencyId(Long.valueOf(arrCompetency[i]));
            canSelection.setCandidateKpiId(Long.valueOf(arrKPI[i]));
            try {
                PstCandidateSelection.insertExc(canSelection);
            } catch(Exception e){
                System.out.println(""+e.toString());
            }
        }
        /* Save to hr_candidate_search_target */
        CandidateSearchTarget searchTarget = new CandidateSearchTarget();
        String companyIds = "0";
        String divisionIds = "0";
        String departmentIds = "0";
        String sectionIds = "0";
        String positionIds = "0";
        String levelIds = "0";
        String categories = "0";
        if (selectCompany.length > 0){
            companyIds = "";
            for(int i=0; i<selectCompany.length; i++){
                companyIds += selectCompany[i]+",";
            }
            companyIds +="0";
        }
        if (selectDivision.length > 0){
            divisionIds = "";
            for(int i=0; i<selectDivision.length; i++){
                divisionIds += selectDivision[i] + ",";
            }
            divisionIds +="0";
        }
        if (selectDepart.length > 0){
            departmentIds = "";
            for(int i=0; i<selectDepart.length; i++){
                departmentIds += selectDepart[i]+",";
            }
            departmentIds +="0";
        }
        if (selectSection.length > 0){
            sectionIds = "";
            for(int i=0; i<selectSection.length; i++){
                sectionIds += selectSection[i] +",";
            }
            sectionIds += "0";
        }
        if (selectPosition.length > 0){
            positionIds = "";
            for(int i=0; i<selectPosition.length; i++){
                positionIds += selectPosition[i]+",";
            }
            positionIds +="0";
        }
        if (selectLevel.length > 0){
            levelIds = "";
            for(int i=0; i<selectLevel.length; i++){
                levelIds += selectLevel[i]+",";
            }
            levelIds +="0";
        }
        if (selectCategory.length > 0){
            categories = "";
            for(int i=0; i<selectCategory.length; i++){
                categories += selectCategory[i]+",";
            }
            categories += "0";
        }
        searchTarget.setCandidateMainId(candidateMainId);
        searchTarget.setCompanyIds(companyIds);
        searchTarget.setDivisionIds(divisionIds);
        searchTarget.setDepartmentIds(departmentIds);
        searchTarget.setSectionIds(sectionIds);
        searchTarget.setPositionIds(positionIds);
        searchTarget.setLevelIds(levelIds);
        searchTarget.setEmpCategoryIds(categories);
        searchTarget.setSex(0);
        try {
            PstCandidateSearchTarget.insertExc(searchTarget);
        } catch(Exception e){
            System.out.println("Insert to hr_candidate_search_target=>"+e.toString());
        }
        /*
        String redirectURL = approot+"/employee/candidate/candidate_complete.jsp?oid="+candidateMainId;
        response.sendRedirect(redirectURL);
        */
    }
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Candidate Selection</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">

            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; font-weight: bold; background-color: #F5F5F5;}
            
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}
            
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
            #btn1 {
              background-color: #DDD;
              border: 1px solid #CCC;
              border-radius: 3px;
              color: #575757;
              font-size: 11px;
              padding: 2px 5px;
              cursor: pointer;
            }
            #btn1:hover {
              color: #373737;
              background-color: #c5c5c5;
              border: 1px solid #999;
            }
            
            #btn2 {
              background-color: #00ccff;
              border: 1px solid #00aeda;
              border-radius: 3px;
              color: #FFF;
              font-size: 14px;
              padding: 9px 17px;
              cursor: pointer;
            }
            #btn2:hover {
              color: #FFF;
              background-color: #0096bb;
              border: 1px solid #007592;
            }
            
            #btn_disable {
              background-color: #EEE;
              border: 1px solid #D5D5D5;
              border-radius: 3px;
              color: #CCC;
              font-size: 14px;
              padding: 9px 17px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_part {background-color: #FFF; border-left: 1px solid #3cb0fd; padding:5px 15px;  color: #575757; margin: 1px 0px;}
            
            .tdPadding {padding:9px 12px; font-weight: bold;background-color: #DDD; color: #575757;}

            .divLocation {
                margin:3px 0px; 
                padding:5px 7px; 
                background-color: #FFF;
                color: #575757;
                font-weight: bold;
            }
            
            .position {
                color: #474747;
                background-color: #DDD;
                padding: 3px 5px;
                margin: 3px;
                border-radius: 3px;
            }
            
            #nav-next {
              background-color: #00ccff;
              color: #FFF;
              font-size: 14px;
              padding: 11px 17px 10px 17px;
              cursor: pointer;
            }
            #nav-next-disable {
              background-color: #d9d9d9;
              color: #FFF;
              font-size: 14px;
              padding: 11px 17px 10px 17px;
              cursor: pointer;
            }
            .title_part {
                color:#0599ab;
                padding: 7px 21px;
                background-color: #FFF;
                border-left: 1px solid #0099FF;
            }
            hr {border-top:1px solid #D5D5D5;}
            .pesan {
                padding: 21px;
                background-color: #c9ff6a;
                color: #689f09;
                border-radius: 5px;
            }
        </style>
        <script type="text/javascript">
            function cmdNext(){
                document.frm.command.value="<%=Command.SAVE%>";
                document.frm.action = "candidate_selection.jsp";
                document.frm.submit();
            }
            function cmdGoTo(val, oid){
                var link = "";
                switch(val){
                    case "1":
                        link = "candidate_main.jsp";
                        break;
                    case "2":
                        link = "candidate_location.jsp";
                        break;
                    case "3":
                        link = "candidate_position.jsp";
                        break;
                    case "4":
                        link = "candidate_matrix.jsp";
                        break;
                    case "5":
                        link = "candidate_selection.jsp";
                        break;
                    case "6":
                        link = "candidate_complete.jsp";
                        break;
                }
                document.frm.command.value="0";
                document.frm.candidate_main_id.value=oid;
                document.frm.action = link;
                document.frm.submit();
            }
        </script>

    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" height="54"> 
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
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%}%>
            <tr> 
                <td valign="top" align="left" width="100%"> 
                    <table border="0" cellspacing="3" cellpadding="2" id="tbl0" width="100%">
                        <tr> 
                            <td  colspan="3" valign="top" style="padding: 12px"> 
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama">Candidate Selection</div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top" width="100%">
                                       
                                            
                                            <table width="100%" style="padding:9px; border:1px solid <%=garisContent%>;"  border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        <form name="frm" method="post" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>">
                                                            <input type="hidden" name="candidate_main_id" value="<%=candidateMainId%>" />
                                                            <table>
                                                                <tr>
                                                                    <td colspan="2" style="border-bottom:1px solid #CCC; padding:15px 9px;">
                                                                        <table cellspacing="0" cellpadding="0" border="0">
                                                                            <tr>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('1','<%=candidateMainId%>')">1) <%=titleMenu%> Main</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-enable.png" /></td>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('2','<%=candidateMainId%>')">2) <%=titleMenu%> Location</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-enable.png" /></td>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('3','<%=candidateMainId%>')">3) <%=titleMenu%> Position</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-enable.png" /></td>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('4','<%=candidateMainId%>')">4) <%=titleMenu%> Detail</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-enable.png" /></td>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('5','<%=candidateMainId%>')">5) <%=titleMenu%> Selection</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next.png" /></td>
                                                                                <td valign="top"><div id="nav-next-disable" onclick="cmdGoTo('6','<%=candidateMainId%>')">6) <%=titleMenu%> Summary</div></td>                                                                                
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <%
                                                                        if (iCommand == Command.SAVE){
                                                                            iCommand = Command.NONE;
                                                                            %>
                                                                            <div class="pesan">Data berhasil disimpan!</div>
                                                                            <%
                                                                        }
                                                                        %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top">
                                                                        <table>
                                                                            <tr>
                                                                                <td valign="top">
                                                                                    <table>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <div class="title_part">Education</div>
                                                                                                <table class="tblStyle">
                                                                                                    <tr>
                                                                                                        <td class="title_tbl">&nbsp;</td>
                                                                                                        <td class="title_tbl">Education</td>
                                                                                                        <td class="title_tbl">Education Description</td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                    Vector listEducation = PstEducation.list(0, 0, "", "");
                                                                                                    if (listEducation != null && listEducation.size()>0){
                                                                                                        for(int e=0; e<listEducation.size(); e++){
                                                                                                            Education edu = (Education)listEducation.get(e);
                                                                                                            %>
                                                                                                            <tr>
                                                                                                                <td><input type="checkbox" name="chx_edu" value="<%=edu.getOID()%>" /></td>
                                                                                                                <td><%=edu.getEducation()%></td>
                                                                                                                <td><%=edu.getEducationDesc()%></td>
                                                                                                            </tr>
                                                                                                            <%
                                                                                                        }
                                                                                                    }
                                                                                                    %>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <div class="title_part">Training</div>
                                                                                                <table class="tblStyle">
                                                                                                    <tr>
                                                                                                        <td class="title_tbl">&nbsp;</td>
                                                                                                        <td class="title_tbl">Training</td>
                                                                                                        <td class="title_tbl">Type</td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                    Vector listTraining = PstTraining.list(0, 0, "", "");
                                                                                                    if (listTraining != null && listTraining.size()>0){
                                                                                                        for(int t=0; t<listTraining.size(); t++){
                                                                                                            Training training = (Training)listTraining.get(t);
                                                                                                            %>
                                                                                                            <tr>
                                                                                                                <td><input type="checkbox" name="chx_training" value="<%=training.getOID()%>" /></td>
                                                                                                                <td><%=training.getName()%></td>
                                                                                                                <td><%=getTypeName(training.getType())%></td>
                                                                                                            </tr>
                                                                                                            <%
                                                                                                        }
                                                                                                    }
                                                                                                    %>
                                                                                                    
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <div class="title_part">Competency</div>
                                                                                                <table class="tblStyle">
                                                                                                    <tr>
                                                                                                        <td class="title_tbl">&nbsp;</td>
                                                                                                        <td class="title_tbl">Competency Name</td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                    Vector listCompetency = PstCompetency.list(0, 0, "", "");
                                                                                                    if (listCompetency != null && listCompetency.size()>0){
                                                                                                        for (int c=0; c<listCompetency.size(); c++){
                                                                                                            Competency competency = (Competency)listCompetency.get(c);
                                                                                                    %>
                                                                                                    <tr>
                                                                                                        <td><input type="checkbox" name="chx_competency" value="<%=competency.getOID()%>" /></td>
                                                                                                        <td><%=competency.getCompetencyName()%></td>
                                                                                                    </tr>
                                                                                                    <% 
                                                                                                        }
                                                                                                    }
                                                                                                    %>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <div class="title_part">Key Performance Indicator (KPI)</div>
                                                                                                <table class="tblStyle">
                                                                                                    <tr>
                                                                                                        <td class="title_tbl">&nbsp;</td>
                                                                                                        <td class="title_tbl">KPI Title</td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                    Vector listKPI = PstKPI_List.list(0, 0, "", "");
                                                                                                    if (listKPI != null && listKPI.size()>0){
                                                                                                        for(int k=0; k<listKPI.size(); k++){
                                                                                                            KPI_List kpi = (KPI_List)listKPI.get(k);
                                                                                                            %>
                                                                                                            <tr>
                                                                                                                <td><input type="checkbox" name="chx_kpi" value="<%=kpi.getOID()%>" /></td>
                                                                                                                <td><%=kpi.getKpi_title()%></td>
                                                                                                            </tr>
                                                                                                            <%
                                                                                                        }
                                                                                                    }
                                                                                                    %>
                                                                                                    
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
                                                                    <td valign="top">                                                                  
<!-- Parameter pencarian yang diambil dari spesial query databank -->
<table>
    <tr>
        <td valign="middle">
            <div class="title_part">Pilih Parameter Pencarian :</div>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <table border="0" cellspacing="1" cellpadding="1">
                <tr>
                    <td valign="top" class="tdPadding"><%=dictionaryD.getWord(I_Dictionary.COMPANY) %></td>
                    <td valign="top" class="tdPadding"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                    <td valign="top" class="tdPadding"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                    <td valign="top" class="tdPadding"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                </tr>
                <tr>
                    <td valign="top">
                        <select name="select_company" multiple="multiple" size="11">
                            <option value="0">-SELECT-</option>
                        <%
                        Vector listCompany = PstCompany.list(0, 0, "", "");
                        if (listCompany != null && listCompany.size()>0){
                            for (int i=0; i<listCompany.size(); i++){
                                Company comp = (Company)listCompany.get(i);
                                %>
                                <option value="<%=comp.getOID()%>"><%=comp.getCompany()%></option>
                                <%
                            }
                        }
                        %>
                        </select>
                    </td>
                    <td valign="top">
                        <select name="select_division" multiple="multiple" size="11">
                            <option value="0">-SELECT-</option>
                            <%
                            orderBy = PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" ASC";
                            Vector listDivision = PstDivision.list(0, 0, "", orderBy);
                            if (listDivision != null && listDivision.size()>0){
                                for(int i=0; i<listDivision.size(); i++){
                                    Division division = (Division)listDivision.get(i);
                                    %>
                                    <option value="<%=division.getOID()%>"><%=division.getDivision()%></option>
                                    <%
                                }
                            }
                            %>
                        </select>
                    </td>
                    <td valign="top">
                        <select name="select_depart" multiple="multiple" size="11">
                            <option value="0">-SELECT-</option>
                            <%
                            orderBy = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" ASC";
                            Vector listDepartment = PstDepartment.list(0, 0, "", orderBy);
                            if (listDepartment != null && listDepartment.size()>0){
                                for(int i=0; i<listDepartment.size(); i++){
                                    Department depart = (Department)listDepartment.get(i);
                                    %>
                                    <option value="<%=depart.getOID()%>"><%=depart.getDepartment()%></option>
                                    <%
                                }
                            }
                            %>
                        </select>
                    </td>
                    <td valign="top">
                        <select name="select_section" multiple="multiple" size="11">
                            <option value="0">-SELECT-</option>
                            <%
                            orderBy = PstSection.fieldNames[PstSection.FLD_SECTION]+" ASC";
                            Vector listSection = PstSection.list(0, 0, "", orderBy);
                            if (listSection != null && listSection.size()>0){
                                for(int i=0; i<listSection.size(); i++){
                                    Section section = (Section)listSection.get(i);
                                    %>
                                    <option value="<%=section.getOID()%>"><%=section.getSection()%></option>
                                    <%
                                }
                            }
                            %>
                        </select>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <table>
                <tr>
                    <td valign="top" class="tdPadding">Position</td>
                    <td valign="top" class="tdPadding">Level</td>
                    <td valign="top" class="tdPadding">Emp.Category</td>
                    <td valign="top" class="tdPadding">Sex</td>
                </tr>
                <tr>
                    <td valign="top">
                        <select name="select_position" multiple="multiple" size="11">
                            <option value="0">-SELECT-</option>
                            <%
                            orderBy = PstPosition.fieldNames[PstPosition.FLD_POSITION]+" ASC";
                            Vector listPosition = PstPosition.list(0, 0, "", orderBy);
                            if (listPosition != null && listPosition.size()>0){
                                for(int i=0; i<listPosition.size(); i++){
                                    Position position = (Position)listPosition.get(i);
                                    %>
                                    <option value="<%=position.getOID()%>"><%=position.getPosition()%></option>
                                    <%
                                }
                            }
                            %>
                        </select>
                    </td>
                    <td valign="top">
                        <select name="select_level" multiple="multiple" size="11">
                            <option value="0">-SELECT-</option>
                            <%
                            orderBy = PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" ASC";
                            Vector listLevel = PstLevel.list(0, 0, "", orderBy);
                            if (listLevel != null && listLevel.size()>0){
                                for(int i=0; i<listLevel.size(); i++){
                                    Level level = (Level)listLevel.get(i);
                                    %>
                                    <option value="<%=level.getOID()%>"><%=level.getLevel()%></option>
                                    <%
                                }
                            }
                            %>
                        </select>
                    </td>
                    <td valign="top">
                        <select name="select_category" multiple="multiple" size="11">
                            <option value="0">-SELECT-</option>
                            <%
                            orderBy = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+" ASC";
                             Vector listEmpCat = PstEmpCategory.list(0, 0, "", orderBy);
                             if (listEmpCat != null && listEmpCat.size()>0){
                                 for(int i=0; i<listEmpCat.size(); i++){
                                     EmpCategory empCat = (EmpCategory)listEmpCat.get(i);
                                     %>
                                     <option value="<%=empCat.getOID()%>"><%=empCat.getEmpCategory()%></option>
                                     <%
                                 }
                             }
                            %>
                        </select>
                    </td>
                    <td valign="top" style="background-color: #FFF; padding:3px 7px;">
                        <input type="radio" name="rb_sex" value="0" />Male<br />
                        <input type="radio" name="rb_sex" value="1" />Female<br />
                        <input type="radio" checked="checked" name="rb_sex" value="2" />All
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<!-- End Spesial query -->
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2" style="border-top:1px solid #DDD; padding-top: 12px">
                                                                        <button id="btn2" onclick="cmdNext()">Save</button>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            
                                                        </form>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>
                                        
                                        </td>
                                    </tr>
                                </table><!---End Tble--->
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>
                            
            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" --> <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
