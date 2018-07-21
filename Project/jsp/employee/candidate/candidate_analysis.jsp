<%-- 
    Document   : candidate_analysis
    Created on : Sep 25, 2015, 6:36:13 AM
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
    public String getEmployeeName(long empId){
        String str = "-";
        try {
            Employee emp = PstEmployee.fetchExc(empId);
            str = emp.getFullName();///
        } catch(Exception e){
            System.out.println(""+e.toString());
        }
        return str;
    }
    
    public String getPositionByEmp(long empId){
        String str = "-";
        try {
            Employee emp = PstEmployee.fetchExc(empId);
            try {
                Position posisi = PstPosition.fetchExc(emp.getPositionId());
                str = posisi.getPosition();
            } catch(Exception ex){
                System.out.println("getPositionByEmp=>"+ex.toString());
            }
        } catch(Exception e){
            System.out.println(""+e.toString());
        }
        return str;
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    long candidateMainId = FRMQueryString.requestLong(request, "candidate_main_id");
    String[] chxEmp = FRMQueryString.requestStringValues(request, "chx_emp");
    String[] posName = FRMQueryString.requestStringValues(request, "pos_name");
    int typeMenu = FRMQueryString.requestInt(request, "type_menu");

    String titleMenu = "";
    if (typeMenu == 0){
        titleMenu = "Candidate";
    } else {
        titleMenu = "Talent";
    }
    String whereClause = "";
    String whereEdu = "";
    String whereComp = "";
    String whereTrain = "";
    String whereKpi = "";
    if (candidateMainId > 0){
        whereClause = PstCandidateSelection.fieldNames[PstCandidateSelection.FLD_CANDIDATE_MAIN_ID]+"="+candidateMainId;
        Vector listSelection = PstCandidateSelection.list(0, 0, whereClause, "");
        if (listSelection != null && listSelection.size()>0){
            for(int i=0; i<listSelection.size(); i++){
                CandidateSelection selection = (CandidateSelection)listSelection.get(i);
                if (selection.getCandidateEducationId()>0){
                    whereEdu += selection.getCandidateEducationId()+",";
                }
                if (selection.getCandidateCompetencyId()>0){
                    whereComp += selection.getCandidateCompetencyId()+",";
                }
                if (selection.getCandidateTrainingId()>0){
                    whereTrain += selection.getCandidateTrainingId()+",";
                }
                if (selection.getCandidateKpiId()>0){
                    whereKpi += selection.getCandidateKpiId()+",";
                }
            }
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Candidate Analysis</title>
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
                                        <td height="20"> <div id="menu_utama">Candidate Analysis</div> </td>
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
                                                            <input type="hidden" name="oid" value="<%=candidateMainId%>">
                                                            <%
                                                            if (chxEmp.length > 0){
                                                                for(int i=0; i<chxEmp.length; i++){
                                                            %>
                                                            <table class="tblStyle">
                                                                <tr>
                                                                    <td class="title_tbl"><%=getPositionByEmp(Long.valueOf(chxEmp[i]))%></td>
                                                                    <td class="title_tbl">Required</td>
                                                                    <td class="title_tbl" colspan="2"><%=getEmployeeName(Long.valueOf(chxEmp[i]))%></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="title_tbl">Competency</td>
                                                                    <td class="title_tbl">Gap Req</td>
                                                                    <td class="title_tbl">Score</td>
                                                                    <td class="title_tbl">Gap</td>
                                                                </tr>
                                                                <%
                                                                whereComp += "0";
                                                                whereClause = PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_ID]+" IN("+whereComp+")";
                                                                Vector listCompetency = PstCompetency.list(0, 0, whereClause, "");
                                                                if (listCompetency != null && listCompetency.size()>0){
                                                                    for(int c=0; c<listCompetency.size(); c++){
                                                                        Competency competency = (Competency)listCompetency.get(c);
                                                                        
                                                                %>
                                                                <tr>
                                                                    <td><%=competency.getCompetencyName()%></td>
                                                                    <td>10</td>
                                                                    <td>9</td>
                                                                    <td>-1</td>
                                                                </tr>
                                                                <%
                                                                    }
                                                                }
                                                                %>
                                                                <tr>
                                                                    <td class="title_tbl">Education</td>
                                                                    <td class="title_tbl">Gap Req</td>
                                                                    <td class="title_tbl">Score</td>
                                                                    <td class="title_tbl">Gap</td>
                                                                </tr>
                                                                <%
                                                                whereEdu += "0";
                                                                whereClause = PstEducation.fieldNames[PstEducation.FLD_EDUCATION_ID]+" IN("+whereEdu+")";
                                                                Vector listEducation = PstEducation.list(0, 0, whereClause, "");
                                                                if (listEducation != null && listEducation.size()>0){
                                                                    for(int e=0; e<listEducation.size(); e++){
                                                                        Education edu = (Education)listEducation.get(e);
                                                                %>
                                                                <tr>
                                                                    <td><%=edu.getEducation()%></td>
                                                                    <td>10</td>
                                                                    <td>9</td>
                                                                    <td>-1</td>
                                                                </tr>
                                                                <%
                                                                    }
                                                                }
                                                                %>
                                                                <tr>
                                                                    <td class="title_tbl">Training</td>
                                                                    <td class="title_tbl">Gap Req</td>
                                                                    <td class="title_tbl">Score</td>
                                                                    <td class="title_tbl">Gap</td>
                                                                </tr>
                                                                <%
                                                                whereTrain += "0";
                                                                whereClause = PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+" IN("+whereTrain+")";
                                                                Vector listTraining = PstTraining.list(0, 0, whereClause, "");
                                                                if (listTraining != null && listTraining.size()>0){
                                                                    for(int t=0; t<listTraining.size(); t++){
                                                                        Training training = (Training)listTraining.get(t);
                                                                        %>
                                                                <tr>
                                                                    <td><%=training.getName()%></td>
                                                                    <td>10</td>
                                                                    <td>9</td>
                                                                    <td>-1</td>
                                                                </tr>
                                                                        <%
                                                                    }
                                                                }
                                                                %>
                                                                <tr>
                                                                    <td class="title_tbl">Key Performance Indicator</td>
                                                                    <td class="title_tbl">Gap Req</td>
                                                                    <td class="title_tbl">Score</td>
                                                                    <td class="title_tbl">Gap</td>
                                                                </tr>
                                                                <%
                                                                whereKpi += "0";
                                                                whereClause = PstKPI_List.fieldNames[PstKPI_List.FLD_KPI_LIST_ID]+" IN("+whereKpi+")";
                                                                Vector listKPI = PstKPI_List.list(0, 0, whereClause, "");
                                                                if (listKPI != null && listKPI.size()>0){
                                                                    for(int k=0; k<listKPI.size(); k++){
                                                                        KPI_List kpiList = (KPI_List)listKPI.get(k);
                                                                        %>
                                                                <tr>
                                                                    <td><%=kpiList.getKpi_title()%></td>
                                                                    <td>10</td>
                                                                    <td>9</td>
                                                                    <td>-1</td>
                                                                </tr>
                                                                        <%
                                                                    }
                                                                }
                                                                %>
                                                                <tr>
                                                                    <td class="title_tbl">Total</td>
                                                                    <td class="title_tbl">30</td>
                                                                    <td class="title_tbl">27</td>
                                                                    <td class="title_tbl">27</td>
                                                                </tr>
                                                            </table>
                                                                <div style="margin:9px 0px; border-top:1px solid #DDD;"></div>
                                                            <% 
                                                                    }
                                                                }
                                                            %>
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