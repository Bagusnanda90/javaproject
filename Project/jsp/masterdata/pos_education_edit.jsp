<%-- 
    Document   : pos_education_edit
    Created on : Feb 5, 2015, 1:14:09 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.masterdata.Education"%>
<%@page import="com.dimata.harisma.entity.masterdata.PositionEducationRequired"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionEducationRequired"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPositionEducationRequired"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPositionEducationRequired"%>
<%@page import="com.dimata.harisma.entity.masterdata.Training"%>
<%@page import="com.dimata.harisma.entity.masterdata.PositionTrainingRequired"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionTrainingRequired"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPositionTrainingRequired"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPositionTrainingRequired"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPositionCompetencyRequired"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPositionCompetencyRequired"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.masterdata.CompetencyDetail"%>
<%@page import="com.dimata.harisma.entity.masterdata.CompetencyLevel"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.masterdata.Competency"%>
<%@page import="com.dimata.harisma.entity.masterdata.PositionCompetencyRequired"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionCompetencyRequired"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidPosEdu = FRMQueryString.requestLong(request, "hidden_pos_edu_id");
    String comm = request.getParameter("comm");
    String oid = request.getParameter("oid");
    int msg = 0;
    if (oidPosEdu != 0 && oidPosEdu > 0){
        oid = String.valueOf(oidPosEdu);
        msg = 1;
    }
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";
    
    CtrlPositionEducationRequired ctrPosEdu = new CtrlPositionEducationRequired(request);

    /*switch statement */
    iErrCode = ctrPosEdu.action(iCommand, oidPosEdu);
    /* end switch*/
    FrmPositionEducationRequired frmPosEdu = ctrPosEdu.getForm();

%>
<%
    
    Vector listPosEdu = new Vector();
    listPosEdu = PstPositionEducationRequired.listInnerJoinVer1(oid);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Education Edit</title>
        <style type="text/css">
            body {
                color:#575757;
                margin: 0;
                padding: 0;
            }
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
            td {padding-right: 14px;}
            #msg {border: 1px solid #ADCF53; background-color: #E9FFAD; color: #739613; padding: 3px 5px;}
            .header {
                background-color: #EEE;
                padding: 15px 21px;
            }
            h2 {
                color: #0096bb;
            }
            input {
                border: 1px solid #DDD;
            }
            .content {
                padding: 15px 21px;
            }
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmPositionEducationRequired.FRM_NAME_POSITION_EDUCATION_REQUIRED%>.action = "pos_education_edit.jsp";
                document.<%=FrmPositionEducationRequired.FRM_NAME_POSITION_EDUCATION_REQUIRED%>.submit();
            }
            function cmdEdit(oid) {
                document.<%=FrmPositionEducationRequired.FRM_NAME_POSITION_EDUCATION_REQUIRED%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmPositionEducationRequired.FRM_NAME_POSITION_EDUCATION_REQUIRED%>.hidden_comp_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmPositionEducationRequired.FRM_NAME_POSITION_EDUCATION_REQUIRED%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
        </script>
    </head>
    <body>
        <div class="header">
            <h2>Education Edit</h2>
        </div>
        <div class="content">
        <form name="<%=FrmPositionEducationRequired.FRM_NAME_POSITION_EDUCATION_REQUIRED%>" action="" method="post">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <input type="hidden" name="hidden_pos_edu_id" value="<%=oid%>">
        <%
        if (listPosEdu != null && listPosEdu.size() > 0){
            for(int i=0; i<listPosEdu.size(); i++){
                Vector vect = (Vector)listPosEdu.get(i);
                PositionEducationRequired posEdu = (PositionEducationRequired)vect.get(0);
                Position pos = (Position)vect.get(1);
                Education edu = (Education)vect.get(2);
            
        %>
        
        <table>
            <tr>
                <td valign="top">Position<input type="hidden" name="<%=frmPosEdu.fieldNames[frmPosEdu.FRM_FIELD_POSITION_ID]%>" value="<%=posEdu.getPositionId()%>" /></td>
                <td valign="top"><%=pos.getPosition()%></td>
            </tr>
            <tr>
                <td valign="top">Education<input type="hidden" name="<%=frmPosEdu.fieldNames[frmPosEdu.FRM_FIELD_EDUCATION_ID]%>" value="<%=posEdu.getEducationId()%>" /></td>
                <td valign="top"><%=edu.getEducation()%></td>
            </tr>
            <tr>
                <td valign="top">Duration Min</td>
                <td valign="top"><input type="text" name="<%=frmPosEdu.fieldNames[frmPosEdu.FRM_FIELD_DURATION_MIN]%>" value="<%=posEdu.getDurationMin()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Duration Recommended</td>
                <td valign="top"><input type="text" name="<%=frmPosEdu.fieldNames[frmPosEdu.FRM_FIELD_DURATION_RECOMMENDED]%>" value="<%=posEdu.getDurationRecommended()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Point Min</td>
                <td valign="top"><input type="text" name="<%=frmPosEdu.fieldNames[frmPosEdu.FRM_FIELD_POINT_MIN]%>" value="<%=posEdu.getPointMin()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Point Recommended</td>
                <td valign="top"><input type="text" name="<%=frmPosEdu.fieldNames[frmPosEdu.FRM_FIELD_POINT_RECOMMENDED]%>" value="<%=posEdu.getPointRecommended()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Note</td>
                <td valign="top">
                    <textarea name="<%=frmPosEdu.fieldNames[frmPosEdu.FRM_FIELD_NOTE]%>"><%=posEdu.getNote()%></textarea>
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
        </div>
    </body>
</html>
