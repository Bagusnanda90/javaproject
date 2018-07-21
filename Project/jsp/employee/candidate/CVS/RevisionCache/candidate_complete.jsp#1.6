<%-- 
    Document   : candidate_complete
    Created on : Sep 21, 2015, 4:24:04 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String getCompanyName(long oidCompany){
        String str = "";
        Company company = new Company();
        try {
            company = PstCompany.fetchExc(oidCompany);
            str = company.getCompany();
        } catch(Exception e){
            System.out.println("getCompanyName=>"+e.toString());
        }
        return str;
    }
    
    public String getDivisionName(long oidDivision){
        String str = "";
        Division division = new Division();
        try {
            division = PstDivision.fetchExc(oidDivision);
            str = division.getDivision();
        } catch(Exception e){
            System.out.println("getDivisionName=>"+e.toString());
        }
        return str;
    }
    
    public String getDepartmentName(long oidDepartment){
        String str = "";
        Department department = new Department();
        try {
            department = PstDepartment.fetchExc(oidDepartment);
            str = department.getDepartment();
        } catch(Exception e){
            System.out.println("getDepartmentName=>"+e.toString());
        }
        return str;
    }
    
    public String getSectionName(long oidSection){
        String str = "";
        Section section = new Section();
        try {
            section = PstSection.fetchExc(oidSection);
            str = section.getSection();
        } catch(Exception e){
            System.out.println("getSectionName=>"+e.toString());
        }
        return str;
    }
    
    public String getLevelName(long oidLevel){
        String str = "";
        try {
            Level level = PstLevel.fetchExc(oidLevel);
            str = level.getLevel();
        } catch(Exception e){
            System.out.println("getLevelName()=>"+e.toString());
        }
        return str;
    }
    
    public String getEmpCategory(long oidCat){
        String str = "";
        try {
            EmpCategory empCat = PstEmpCategory.fetchExc(oidCat);
            str = empCat.getEmpCategory();
        } catch(Exception e){
            System.out.println("getEmpCategory=>"+e.toString());
        }
        return str;
    }
    
    public String getLocationName(long locationId){
        String str = "";
        String whereClause = PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_CANDIDATE_LOC_ID]+"="+locationId;
        Vector listCanLoc = PstCandidateLocation.list(0, 0, whereClause, "");
        if (listCanLoc != null && listCanLoc.size()>0){
            for(int i=0; i<listCanLoc.size(); i++){
                CandidateLocation canLoc = (CandidateLocation)listCanLoc.get(i);
                if (canLoc.getGenId() > 0){
                    str += getCompanyName(canLoc.getGenId())+" / ";
                }
                if (canLoc.getDivisionId() > 0){
                    str += getDivisionName(canLoc.getDivisionId())+" / ";
                }
                if (canLoc.getDepartmentId() > 0){
                    str += getDepartmentName(canLoc.getDepartmentId())+" / ";
                }
                if (canLoc.getSectionId() > 0){
                    str += getSectionName(canLoc.getSectionId());
                }
            }
        }
        return str;
    }

    public String getEducationName(long eduId){
        String str = "-";
        try {
            Education edu = PstEducation.fetchExc(eduId);
            str = edu.getEducation();
        } catch(Exception e){
            System.out.println(""+e.toString());
        }
        return str;
    }
    
    public String getTrainingName(long trainId){
        String str = "-";
        try {
            Training training = PstTraining.fetchExc(trainId);
            str = training.getName();
        } catch(Exception e){
            System.out.println(""+e.toString());
        }
        return str;
    }
    
    public String getCompetencyName(long compId){
        String str = "-";
        try {
            Competency competency = PstCompetency.fetchExc(compId);
            str = competency.getCompetencyName();
        } catch(Exception e){
            System.out.println(""+e.toString());
        }
        return str;
    }
    
    public String getKPIName(long kpiId){
        String str = "-";
        try {
            KPI_List kpiList = PstKPI_List.fetchExc(kpiId);
            str = kpiList.getKpi_title();
        } catch(Exception e){
            System.out.println(""+e.toString());
        }
        return str;
    }

    public String getEmployeeName(long empId){
        String str = "";
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(empId);
            str = emp.getFullName();
        } catch(Exception e){
            System.out.println("Employee Name=>"+e.toString());
        }
        return str;
    }
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long candidateMainId = FRMQueryString.requestLong(request, "candidate_main_id");
int typeMenu = FRMQueryString.requestInt(request, "type_menu");

String titleMenu = "";
if (typeMenu == 0){
    titleMenu = "Candidate";
} else {
    titleMenu = "Talent";
}
String whereClause = "";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Candidate Complete</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">
            body {color:#575757;}
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
              background-color: #BAC955;
              border: 1px solid #828C3B;
              border-radius: 3px;
              color: #FFF;
              font-size: 14px;
              padding: 9px 17px;
              cursor: pointer;
            }
            #btn2:hover {
              color: #FFF;
              background-color: #828C3B;
              border: 1px solid #666E2E;
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
            .content {
                padding: 7px 0px 15px 9px;
            }
        </style>
        <script type="text/javascript">
            function cmdProcess(){
                document.frm.command.value="<%=Command.VIEW%>";
                document.frm.target = "_blank";
                document.frm.action = "candidate_result.jsp";
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
                                        <td height="20"> <div id="menu_utama">Candidate Complete</div> </td>
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
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-enable.png" /></td>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('6','<%=candidateMainId%>')">6) <%=titleMenu%> Summary</div></td>                                   
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top">
                                                                        <table>
                                                                            <tr>
                                                                                <td valign="top">
                                                                                    <div class="title_part">Candidate Main</div>
                                                                                    <div class="content">
                                                                                        <table>
                                                                                            <tr>
                                                                                                <%
                                                                                                
                                                                                                CandidateMain candidateMain = new CandidateMain();
                                                                                                try {
                                                                                                    candidateMain = PstCandidateMain.fetchExc(candidateMainId);
                                                                                                    
                                                                                                    
                                                                                                } catch(Exception e){
                                                                                                    System.out.println("Candidate Main =>"+e.toString());
                                                                                                }
                                                                                                %>
                                                                                                <td valign="top">
                                                                                                    <table>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 5px">Title</th>
                                                                                                            <td><%=candidateMain.getTitle()%></td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 5px">Type</th>
                                                                                                            <td><%=PstCandidatePosition.candidateTypeNames[candidateMain.getCandidateType()]%></td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 5px">Objective</th>
                                                                                                            <td><%=candidateMain.getObjective()%></td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 5px">Due Date</th>
                                                                                                            <td><%=candidateMain.getDueDate()%></td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 5px">Request by</th>
                                                                                                            <td><%=getEmployeeName(candidateMain.getRequestedBy())%></td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 5px">Date of Request</th>
                                                                                                            <td><%=candidateMain.getDateOfRequest()%></td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 5px">Score Tolerance</th>
                                                                                                            <td><%=candidateMain.getScoreTolerance()%></td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 5px">Status Document</th>
                                                                                                            <td><%=I_DocStatus.fieldDocumentStatus[candidateMain.getStatusDoc()]%></td>
                                                                                                        </tr>
                                                                                                    </table>
                                                                                                </td>
                                                                                                <!--
                                                                                                <td valign="top" style="padding-left:21px">
                                                                                                    <table>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 7px">Approve By (1)</th>
                                                                                                            <td>candidateMain.getApproveById1()</td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 7px">Approve Date (1)</th>
                                                                                                            <td>candidateMain.getApproveDate1()</td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 7px">Approve By (2)</th>
                                                                                                            <td>candidateMain.getApproveById2()</td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 7px">Approve Date (2)</th>
                                                                                                            <td>candidateMain.getApproveDate2()</td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 7px">Approve By (3)</th>
                                                                                                            <td>candidateMain.getApproveById3()</td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 7px">Approve Date (3)</th>
                                                                                                            <td>candidateMain.getApproveDate3()</td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 7px">Approve By (4)</th>
                                                                                                            <td>candidateMain.getApproveById4()</td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                            <th style="padding-right: 7px">Approve Date (4)</th>
                                                                                                            <td>candidateMain.getApproveDate4()</td>
                                                                                                        </tr>
                                                                                                    </table>
                                                                                                </td>-->
                                                                                            </tr>
                                                                                        </table>
                                                                                    </div>
                                                                                    <div class="title_part">Candidate Location, Position, and Detail</div>
                                                                                    <div class="content">
                                                                                        <table class="tblStyle">
                                                                                            <tr>
                                                                                                <td class="title_tbl">Location</td>
                                                                                                <td class="title_tbl">Position</td>
                                                                                                <td class="title_tbl">Number of Candidate</td>
                                                                                                <td class="title_tbl">Due Date</td>
                                                                                                <td class="title_tbl">Objective</td>
                                                                                                <td class="title_tbl">Candidate Type</td>
                                                                                            </tr>
                                                                                            <%
                                                                                            whereClause = PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_MAIN_ID]+"="+candidateMainId;
                                                                                            Vector listCanPosition = PstCandidatePosition.list(0, 0, whereClause, "");
                                                                                            if (listCanPosition != null && listCanPosition.size()>0){
                                                                                                for(int i=0; i<listCanPosition.size(); i++){
                                                                                                    CandidatePosition canPos = (CandidatePosition)listCanPosition.get(i);                                                                                                    
                                                                                            %>
                                                                                            <tr>
                                                                                                <td><%=getLocationName(canPos.getCandidateLocId())%></td>
                                                                                                <td><%=PstPosition.getPositionName(String.valueOf(canPos.getPositionId()))%></td>
                                                                                                <td><%=canPos.getNumberOfCandidate()%></td>
                                                                                                <td><%=canPos.getDueDate()%></td>
                                                                                                <td><%=canPos.getObjectives()%></td>
                                                                                                <td><%=PstCandidatePosition.candidateTypeNames[canPos.getCandidateType()]%></td>
                                                                                            </tr>
                                                                                            <%
                                                                                                }                                                                                            
                                                                                            } 
                                                                                            %>
                                                                                        </table>
                                                                                    </div>
                                                                                    <div class="title_part">Candidate Selection</div>
                                                                                    <div class="content">
                                                                                        <table class="tblStyle">
                                                                                            <tr>
                                                                                                <td class="title_tbl">Education</td>
                                                                                                <td class="title_tbl">Training</td>
                                                                                                <td class="title_tbl">Competency</td>
                                                                                                <td class="title_tbl">KPI</td>
                                                                                            </tr>
                                                                                            <%
                                                                                            whereClause = PstCandidateSelection.fieldNames[PstCandidateSelection.FLD_CANDIDATE_MAIN_ID]+"="+candidateMainId;
                                                                                            Vector listSelection = PstCandidateSelection.list(0, 0, whereClause, "");
                                                                                            if (listSelection != null && listSelection.size()>0){
                                                                                                for (int i=0; i<listSelection.size(); i++){
                                                                                                    CandidateSelection canSelection = (CandidateSelection)listSelection.get(i);
                                                                                            %>
                                                                                            <tr>
                                                                                                <td><%=getEducationName(canSelection.getCandidateEducationId())%></td>
                                                                                                <td><%=getTrainingName(canSelection.getCandidateTrainingId())%></td>
                                                                                                <td><%=getCompetencyName(canSelection.getCandidateCompetencyId())%></td>
                                                                                                <td><%=getKPIName(canSelection.getCandidateKpiId())%></td>
                                                                                            </tr>
                                                                                            <%
                                                                                                }                                                                                  
                                                                                            }
                                                                                            %>
                                                                                        </table>
                                                                                    </div>
                                                                                    <div class="title_part">Candidate Search Target</div>
                                                                                    <div class="content">
                                                                                        <%
                                                                                        Vector vCompany = new Vector();
                                                                                        Vector vDivision = new Vector();
                                                                                        Vector vDepart = new Vector();
                                                                                        Vector vSection = new Vector();
                                                                                        Vector vPosition = new Vector();
                                                                                        Vector vLevel = new Vector();
                                                                                        Vector vEmpCat = new Vector();
                                                                                        String sex = "";
                                                                                        whereClause = PstCandidateSearchTarget.fieldNames[PstCandidateSearchTarget.FLD_CANDIDATE_MAIN_ID]+"="+candidateMainId;
                                                                                        Vector listTarget = PstCandidateSearchTarget.list(0, 0, whereClause, "");
                                                                                        if (listTarget != null && listTarget.size()>0){
                                                                                            for(int i=0; i<listTarget.size(); i++){
                                                                                                CandidateSearchTarget target = (CandidateSearchTarget)listTarget.get(i);
                                                                                                for (String retval : target.getCompanyIds().split(",")) {
                                                                                                    if (!(retval.equals("0"))){
                                                                                                        vCompany.add(retval);
                                                                                                    }
                                                                                                }
                                                                                                for (String retval : target.getDivisionIds().split(",")) {
                                                                                                    if (!(retval.equals("0"))){
                                                                                                        vDivision.add(retval);
                                                                                                    }
                                                                                                }
                                                                                                for (String retval : target.getDepartmentIds().split(",")) {
                                                                                                    if (!(retval.equals("0"))){
                                                                                                        vDepart.add(retval);
                                                                                                    }
                                                                                                }
                                                                                                for (String retval : target.getSectionIds().split(",")) {
                                                                                                    if (!(retval.equals("0"))){
                                                                                                        vSection.add(retval);
                                                                                                    }
                                                                                                }
                                                                                                for (String retval : target.getPositionIds().split(",")) {
                                                                                                    if (!(retval.equals("0"))){
                                                                                                        vPosition.add(retval);
                                                                                                    }
                                                                                                }
                                                                                                for (String retval : target.getLevelIds().split(",")) {
                                                                                                    if (!(retval.equals("0"))){
                                                                                                        vLevel.add(retval);
                                                                                                    }
                                                                                                }
                                                                                                for (String retval : target.getEmpCategoryIds().split(",")) {
                                                                                                    if (!(retval.equals("0"))){
                                                                                                        vEmpCat.add(retval);
                                                                                                    }
                                                                                                }
                                                                                                sex = target.getSex()==0 ? "Male":"Female";
                                                                                            }
                                                                                            %>
                                                                                            <div style="font-weight: bold;">Company</div>
                                                                                            <%
                                                                                            for(int c=0; c<vCompany.size(); c++){
                                                                                                String compId = (String)vCompany.get(c);
                                                                                                %>
                                                                                                <div style="padding-left:11px">-<%=getCompanyName(Long.valueOf(compId))%></div>
                                                                                                <%
                                                                                            }
                                                                                            %>
                                                                                            <div style="font-weight: bold;">Division</div>
                                                                                            <%
                                                                                            for(int d1=0; d1<vDivision.size(); d1++){
                                                                                                String divId = (String)vDivision.get(d1);
                                                                                                %>
                                                                                                <div style="padding-left:11px">-<%=getDivisionName(Long.valueOf(divId))%></div>
                                                                                                <%
                                                                                            }
                                                                                            %>
                                                                                            <div style="font-weight: bold;">Department</div>
                                                                                            <%
                                                                                            for(int d2=0; d2<vDepart.size(); d2++){
                                                                                                String depId = (String)vDepart.get(d2);
                                                                                                %>
                                                                                                <div style="padding-left:11px">-<%=getDepartmentName(Long.valueOf(depId))%></div>
                                                                                                <%
                                                                                            }
                                                                                            %>
                                                                                            <div style="font-weight: bold;">Section</div>
                                                                                            <%
                                                                                            for(int s=0; s<vSection.size(); s++){
                                                                                                String secId = (String)vSection.get(s);
                                                                                                %>
                                                                                                <div style="padding-left:11px">-<%=getSectionName(Long.valueOf(secId))%></div>
                                                                                                <%
                                                                                            }
                                                                                            %>
                                                                                            <div style="font-weight: bold;">Position</div>
                                                                                            <%
                                                                                            for(int p=0; p<vPosition.size(); p++){
                                                                                                String posId = (String)vPosition.get(p);
                                                                                                %>
                                                                                                <div style="padding-left:11px">-<%= PstPosition.getPositionName(posId) %></div>
                                                                                                <%
                                                                                            }
                                                                                            %>
                                                                                            <div style="font-weight: bold;">Level</div>
                                                                                            <%
                                                                                            for(int l=0; l<vLevel.size(); l++){
                                                                                                String levId = (String)vLevel.get(l);
                                                                                                %>
                                                                                                <div style="padding-left:11px">-<%=getLevelName(Long.valueOf(levId))%></div>
                                                                                                <%
                                                                                            }
                                                                                        }
                                                                                        %>
                                                                                    </div>    
                                                                                </td>
                                                                            </tr>
                                                                        </table>                                                                        
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2" style="border-top:1px solid #DDD; padding-top: 12px">
                                                                        <button id="btn2" onclick="cmdProcess()">Process</button>
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