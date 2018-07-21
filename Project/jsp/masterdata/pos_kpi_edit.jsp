<%-- 
    Document   : pos_kpi_edit
    Created on : Aug 17, 2015, 1:08:40 PM
    Author     : khirayinnura
--%>

<%@page import="com.dimata.harisma.entity.masterdata.KPI_List"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.masterdata.PositionKPI"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionKPI"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPositionKPI"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPositionKPI"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidPosKpi = FRMQueryString.requestLong(request, "pos_kpi_id");
    String comm = request.getParameter("comm");
    String oid = request.getParameter("oid");
    int msg = 0;
    if (oidPosKpi != 0 && oidPosKpi > 0){
        oid = String.valueOf(oidPosKpi);
        msg = 1;
    }
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";
    
    CtrlPositionKPI ctrPosKpi = new CtrlPositionKPI(request);

    /*switch statement */
    iErrCode = ctrPosKpi.action(iCommand, oidPosKpi);
    /* end switch*/
    FrmPositionKPI frmPosKpi = ctrPosKpi.getForm();

%>
<%
    
    Vector listPosKpi = new Vector();
    listPosKpi = PstPositionKPI.listInnerJoinVer1(oid);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            #btn {padding: 3px; border: 1px solid #CCC; color: #777; background-color: #EEE;}
            #btn:hover {border: 1px solid #999; color: #333; background-color: #CCC;}
            td {padding-right: 14px;}
            #msg {border: 1px solid #ADCF53; background-color: #E9FFAD; color: #739613; padding: 3px 5px;}
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmPositionKPI.FRM_NAME_POSITION_KPI%>.action = "pos_kpi_edit.jsp";
                document.<%=FrmPositionKPI.FRM_NAME_POSITION_KPI%>.submit();
            }
            function cmdEdit(oid) {
                document.<%=FrmPositionKPI.FRM_NAME_POSITION_KPI%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmPositionKPI.FRM_NAME_POSITION_KPI%>.pos_kpi_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmPositionKPI.FRM_NAME_POSITION_KPI%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
        </script>
    </head>
    <body>
        <h2>Edit KPI</h2>
        <form name="<%=FrmPositionKPI.FRM_NAME_POSITION_KPI%>" action="" method="post">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <input type="hidden" name="pos_kpi_id" value="<%=oid%>">
        <%
        if (listPosKpi != null && listPosKpi.size() > 0){
            for(int i=0; i<listPosKpi.size(); i++){
                Vector vect = (Vector)listPosKpi.get(i);
                PositionKPI posKpi = (PositionKPI)vect.get(0);
                Position pos = (Position)vect.get(1);
                KPI_List kpiList = (KPI_List)vect.get(2);
            
        %>
        
        <table>
            <tr>
                <td valign="top">Position<input type="hidden" name="<%=frmPosKpi.fieldNames[frmPosKpi.FRM_FIELD_POSITION_ID]%>" value="<%=posKpi.getPositionId()%>" /></td>
                <td valign="top"><%=pos.getPosition()%></td>
            </tr>
            <tr>
                <td valign="top">KPI<input type="hidden" name="<%=frmPosKpi.fieldNames[frmPosKpi.FRM_FIELD_KPI_LIST_ID]%>" value="<%=posKpi.getKpiListId()%>" /></td>
                <td valign="top"><%=kpiList.getKpi_title()%></td>
            </tr>
            <tr>
                <td valign="top">Duration Month</td>
                <td valign="top"><input type="text" name="<%=frmPosKpi.fieldNames[frmPosKpi.FRM_FIELD_DURATION_MONTH]%>" value="<%=posKpi.getDurationMonth()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Minimum Achievment</td>
                <td valign="top"><input type="text" name="<%=frmPosKpi.fieldNames[frmPosKpi.FRM_FIELD_KPI_MIN_ACHIEVMENT]%>" value="<%=posKpi.getKpiMinAchievment()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Recommended Achievment</td>
                <td valign="top"><input type="text" name="<%=frmPosKpi.fieldNames[frmPosKpi.FRM_FIELD_KPI_RECOMMEND_ACHIEV]%>" value="<%=posKpi.getKpiRecommendAchiev()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Score Minimum</td>
                <td valign="top"><input type="text" name="<%=frmPosKpi.fieldNames[frmPosKpi.FRM_FIELD_SCORE_MIN]%>" value="<%=posKpi.getScoreMin()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Score Recommended</td>
                <td valign="top">
                    <input type="text" name="<%=frmPosKpi.fieldNames[frmPosKpi.FRM_FIELD_SCORE_RECOMMENDED]%>" value="<%=posKpi.getScoreRecommended()%>" />
                </td>
            </tr>
            <tr>
                <td valign="top" colspan="2"><button id="btn" onclick="javascript:cmdSave()">Save</button></td>
            </tr>
        </table>
        <%
            }
        }
        %>
        </form>
        <%
            if (msg > 0){
        %>
                <div id="msg">Data have been save</div>
        <%
            }
        %>
    </body>
</html>
