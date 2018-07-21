<%-- 
    Document   : candidate_result
    Created on : Sep 22, 2015, 4:23:55 PM
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
    Vector listEmployee = new Vector();
    if (iCommand == Command.VIEW && candidateMainId > 0){
        whereClause = PstCandidateSearchTarget.fieldNames[PstCandidateSearchTarget.FLD_CANDIDATE_MAIN_ID]+"="+candidateMainId;
        Vector listTarget = PstCandidateSearchTarget.list(0, 0, whereClause, "");
        if (listTarget != null && listTarget.size()>0){
            whereClause = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"=0 AND ";
            for (int i=0; i<listTarget.size(); i++){
                CandidateSearchTarget target = (CandidateSearchTarget)listTarget.get(i);
                if (target.getCompanyIds().length()>0){
                    whereClause += PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+" IN("+target.getCompanyIds()+")";
                }
                if (target.getDivisionIds().length()>0){
                    whereClause += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+" IN("+target.getDivisionIds()+")";
                }
                if (target.getDepartmentIds().length()>0){
                    whereClause += " OR "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" IN("+target.getDepartmentIds()+")";
                }
                if (target.getSectionIds().length()>0){
                    whereClause += " OR "+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+" IN("+target.getSectionIds()+")";
                }
                if (target.getPositionIds().length()>0){
                    whereClause += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+" IN("+target.getPositionIds()+")";
                }
                if (target.getLevelIds().length()>0){
                    whereClause += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+" IN("+target.getLevelIds()+")";
                }
                if (target.getEmpCategoryIds().length()>0){
                    whereClause += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+" IN("+target.getEmpCategoryIds()+")";
                }            
            }
            listEmployee = PstCandidateSearchTarget.listEmployee(whereClause);
        }

    }

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Candidate Result</title>
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
            }
            #nav-next-disable {
              background-color: #d9d9d9;
              color: #FFF;
              font-size: 14px;
              padding: 11px 17px 10px 17px;
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
                document.frm.command.value="<%=Command.SAVE%>";
                document.frm.action = "";
                document.frm.submit();
            }
            function cmdAnalysis(){
                document.frm.command.value="<%=Command.VIEW%>";
                document.frm.target = "_blank";
                document.frm.action = "candidate_analysis.jsp";
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
                                        <td height="20"> <div id="menu_utama">Candidate Result</div> </td>
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
                                                            <input type="hidden" name="candidate_main_id" value="<%=candidateMainId%>">
                                                            <%
                                                            if (listEmployee != null && listEmployee.size()>0){
                                                            %>
                                                            <button id="btn" onclick="cmdAnalysis()">Analisis</button>
                                                            <div>&nbsp;</div>
                                                            <table class="tblStyle">
                                                                <tr>
                                                                    <td class="title_tbl">&nbsp;</td>
                                                                    <td class="title_tbl">Rank</td>
                                                                    <td class="title_tbl">Employee Name</td>
                                                                    <td class="title_tbl">Division</td>
                                                                    <td class="title_tbl">Department</td>
                                                                    <td class="title_tbl">Section</td>
                                                                    <td class="title_tbl">Position</td>
                                                                </tr>
                                                                <%
                                                                for (int i=0; i<listEmployee.size(); i++){
                                                                    Employee emp = (Employee)listEmployee.get(i);
                                                                    %>
                                                                <tr>
                                                                    <td><input type="checkbox" name="chx_emp" value="<%=emp.getOID()%>" /></td>
                                                                    <td><%=i%></td>
                                                                    <td><%=emp.getFullName()%></td>
                                                                    <td><%=getDivisionName(emp.getDivisionId())%></td>
                                                                    <td><%=getDepartmentName(emp.getDepartmentId())%></td>
                                                                    <td><%=getSectionName(emp.getSectionId())%></td>
                                                                    <td><input type="hidden" name="pos_name" value="<%= PstPosition.getPositionName(String.valueOf(emp.getPositionId())) %>" /> <%= PstPosition.getPositionName(String.valueOf(emp.getPositionId())) %></td>
                                                                </tr>
                                                                    <%
                                                                }
                                                                %>
                                                            </table>
                                                            <% } %>
                                                            
                                                            
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