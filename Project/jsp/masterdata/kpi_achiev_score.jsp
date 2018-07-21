<%-- 
    Document   : kpi_achiev_score
    Created on : Oct 3, 2015, 11:08:52 AM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.util.DynamicField"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<%@ include file = "../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    
    public boolean isThereData(Vector data){
        boolean out = false;
        if (data != null && data.size()>0){
            out = true;
        }
        return out;
    }
   
%>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int commandOther = FRMQueryString.requestInt(request, "command_other");
    long oidKpiScore = FRMQueryString.requestLong(request, "oid_kpi_score");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");

    /*variable declaration*/
    int recordToGet = 50;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";
    
    CtrlKpiAchievScore ctrlKpiScore = new CtrlKpiAchievScore(request);
    Vector listKpiScore = new Vector(1,1);
    
    iErrCode = ctrlKpiScore.action(iCommand, oidKpiScore);
    FrmKpiAchievScore frmKpiScore = ctrlKpiScore.getForm();
    int vectSize = PstKpiAchievScore.getCount(whereClause);
    KpiAchievScore kpiScore = ctrlKpiScore.getKpiAchievScore();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstKpiAchievScore.findLimitStart(kpiScore.getOID(), recordToGet, whereClause, orderClause);
        oidKpiScore = kpiScore.getOID();
        kpiScore = new KpiAchievScore();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlKpiScore.actionList(iCommand, start, vectSize, recordToGet);
    }
    listKpiScore = PstKpiAchievScore.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listKpiScore.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listKpiScore = PstKpiAchievScore.list(start, recordToGet, whereClause, orderClause);
    }
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>KPI Achieve Score</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                background-color: #FFF;
                margin: 25px 23px 59px 23px;
                border: 1px solid #DDD;
                border-radius: 5px;
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
            .btn {
                background: #ebebeb;
                background-image: -webkit-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -moz-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -ms-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -o-linear-gradient(top, #ebebeb, #dddddd);
                background-image: linear-gradient(to bottom, #ebebeb, #dddddd);
                -webkit-border-radius: 5;
                -moz-border-radius: 5;
                border-radius: 3px;
                font-family: Arial;
                color: #7a7a7a;
                font-size: 12px;
                padding: 5px 11px 5px 11px;
                border: solid #d9d9d9 1px;
                text-decoration: none;
            }

            .btn:hover {
                color: #474747;
                background: #ddd;
                background-image: -webkit-linear-gradient(top, #ddd, #CCC);
                background-image: -moz-linear-gradient(top, #ddd, #CCC);
                background-image: -ms-linear-gradient(top, #ddd, #CCC);
                background-image: -o-linear-gradient(top, #ddd, #CCC);
                background-image: linear-gradient(to bottom, #ddd, #CCC);
                text-decoration: none;
                border: 1px solid #C5C5C5;
            }
            
            .btn-small {
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
            
            #caption {padding: 7px 0px 2px 0px; font-size: 12px; font-weight: bold; color: #575757;}
            #div_input {}
            
            .form-style {
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
                
            }
            
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.action = "kpi_achiev_score.jsp";
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.submit();
            }
            function cmdSave(){
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdAdd(){
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.command.value="<%=Command.ADD%>";
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.oid_kpi_score.value = "0";
                getCmd();
            }
            function cmdEdit(oid) {
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.oid_kpi_score.value = oid;
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }
            function cmdAsk(oid){
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.command.value = "<%=Command.ASK%>";
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.oid_kpi_score.value = oid;
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>.oid_kpi_score.value = oid;
                getCmd();
            }
        </script>
        
    </head>
    <body>
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../main/mnmain.jsp" %>
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
        <div class="content-main">
            <div class="content-title">
                <div id="title-large">Key Performance Indicator Achieve Score</div>
                <div id="title-small">Memberi score terhadap KPI Achieve Score yang telah ditentukan.</div>
            </div>
            <div class="content">
                <form name="<%=FrmKpiAchievScore.FRM_NAME_KPI_ACHIEV_SCORE%>" method="POST" action="">
                <input type="hidden" name="command" value="<%=iCommand%>" />
                <input type="hidden" name="command_other" value="<%=commandOther%>" />
                <input type="hidden" name="oid_kpi_score" value="<%=oidKpiScore%>" />
                <input type="hidden" name="start" value="<%=start%>" />
                <input type="hidden" name="prev_command" value="<%=prevCommand%>" />
                <p>
                    <%
                    if (iCommand == Command.ASK){
                    %>
                    <table>
                        <tr>
                            <td valign="top">
                                <div id="confirm">
                                    <strong>Are you sure to delete item ?</strong> &nbsp;
                                    <button id="btn-confirm" onclick="javascript:cmdDelete('<%=oidKpiScore%>')">Yes</button>
                                    &nbsp;<button id="btn-confirm" onclick="javascript:cmdBack()">No</button>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <%
                    }
                    %>
                </p>
                <p><button class="btn" onclick="cmdAdd()">Tambah Data</button></p>
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td valign="top">
                            <%
                            Vector dfKPIScore = PstKpiAchievScore.getDistinctKpiAchievScore();
                            if (isThereData(dfKPIScore)){
                                for(int i=0; i<dfKPIScore.size(); i++){
                                    DynamicField dyField = (DynamicField)dfKPIScore.get(i);
                            %>
                            <table class="tbl-main">
                                <tr>
                                    <td valign="top" colspan="2" id="tbl-title"><%=dyField.getField(PstKPI_List.TBL_HR_KPI_LIST+"."+PstKPI_List.fieldNames[PstKPI_List.FLD_KPI_TITLE])%></td>
                                </tr>
                                <tr>
                                    <td valign="top">&nbsp;</td>
                                    <td valign="top">
                                        <table class="tbl-small">
                                            <tr>
                                                <td id="tbl-title">Achieve min</td>
                                                <td id="tbl-title">Achieve max</td>
                                                <td id="tbl-title">Duration min</td>
                                                <td id="tbl-title">Duration max</td>
                                                <td id="tbl-title">Score</td>
                                                <td id="tbl-title">Valid Start</td>
                                                <td id="tbl-title">Valid End</td>
                                                <td id="tbl-title">&nbsp;</td>
                                            </tr>
                                            <%
                                            whereClause = PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_KPI_LIST_ID]+" = "+dyField.getField(PstKpiAchievScore.TBL_KPI_ACHIEV_SCORE+"."+PstKpiAchievScore.fieldNames[PstKpiAchievScore.FLD_KPI_LIST_ID]);
                                            Vector listScore = PstKpiAchievScore.list(0, 0, whereClause, "");
                                            if (isThereData(listScore)){
                                                for(int j=0; j<listScore.size(); j++){
                                                    KpiAchievScore kScore = (KpiAchievScore)listScore.get(j);
                                            %>
                                            <tr>
                                                <td><%=kScore.getAchievPctgMin()%></td>
                                                <td><%=kScore.getAchievPctgMax()%></td>
                                                <td><%=kScore.getAchievDurationMin()%></td>
                                                <td><%=kScore.getAchievDurationMax()%></td>
                                                <td><%=kScore.getScore()%></td>
                                                <td><%=kScore.getValidStart()%></td>
                                                <td><%=kScore.getValidEnd()%></td>
                                                <td><button class="btn-small" onclick="cmdAsk('<%=kScore.getOID()%>')">&times;</button></td>
                                            </tr>
                                            <%
                                                }
                                            }
                                            %>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <%  }
                            } 
                            %>
                        </td>
                        <td valign="top">
                            <% if (iCommand == Command.ADD || iCommand == Command.EDIT){%>
                            <div style="margin-left:45px">
                                <div class="form-style">
                                    <div class="form-title">Form KPI Achieve Score</div>
                                    <div class="form-content">
                                        <div id="caption">KPI List</div>
                                        <div id="div_input">
                                            <select name="<%= FrmKpiAchievScore.fieldNames[FrmKpiAchievScore.FRM_FIELD_KPI_LIST_ID] %>">
                                                <option value="0">-SELECT-</option>
                                                <%
                                                Vector listKPI = PstKPI_List.list(0, 0, "", "");
                                                if (isThereData(listKPI)){
                                                    for(int i=0; i<listKPI.size(); i++){
                                                        KPI_List kpiList= (KPI_List)listKPI.get(i);
                                                        %>
                                                        <option value="<%=kpiList.getOID()%>"><%=kpiList.getKpi_title()%></option>
                                                        <%
                                                    }
                                                }
                                                %>
                                            </select>
                                        </div>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td style="padding-right: 11px">
                                                    <div id="caption">Achieve min</div>
                                                    <div id="div_input">
                                                        <input type="text" name="<%= FrmKpiAchievScore.fieldNames[FrmKpiAchievScore.FRM_FIELD_ACHIEV_PCTG_MIN] %>" value="" />
                                                    </div>
                                                </td>
                                                <td>
                                                    <div id="caption">Achieve max</div>
                                                    <div id="div_input">
                                                        <input type="text" name="<%= FrmKpiAchievScore.fieldNames[FrmKpiAchievScore.FRM_FIELD_ACHIEV_PCTG_MAX] %>" value="" />
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="padding-right: 11px">
                                                    <div id="caption">Duration min</div>
                                                    <div id="div_input">
                                                        <input type="text" name="<%= FrmKpiAchievScore.fieldNames[FrmKpiAchievScore.FRM_FIELD_ACHIEV_DURATION_MIN] %>" value="" />
                                                    </div>
                                                </td>
                                                <td>
                                                    <div id="caption">Duration max</div>
                                                    <div id="div_input">
                                                        <input type="text" name="<%= FrmKpiAchievScore.fieldNames[FrmKpiAchievScore.FRM_FIELD_ACHIEV_DURATION_MAX] %>" value="" />
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="padding-right: 11px">
                                                    <div id="caption">Valid Start</div>
                                                    <div id="div_input">
                                                        <input type="text" name="<%= FrmKpiAchievScore.fieldNames[FrmKpiAchievScore.FRM_FIELD_VALID_START] %>" value="" />
                                                    </div>
                                                </td>
                                                <td>
                                                    <div id="caption">Valid End</div>
                                                    <div id="div_input">
                                                        <input type="text" name="<%= FrmKpiAchievScore.fieldNames[FrmKpiAchievScore.FRM_FIELD_VALID_END] %>" value="" />
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="padding-right: 11px">
                                                    <div id="caption">Score</div>
                                                    <div id="div_input">
                                                        <input type="text" name="<%= FrmKpiAchievScore.fieldNames[FrmKpiAchievScore.FRM_FIELD_SCORE] %>" value="" />
                                                    </div>
                                                </td>
                                                <td>&nbsp;</td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="form-footer">
                                        <button class="btn" onclick="cmdSave()">Save</button>
                                        <button class="btn" onclick="cmdBack()">Close</button>
                                    </div>
                                </div>
                            </div>
                            <% } %>
                        </td>
                    </tr>
                </table>
                </form>
            </div>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>