<%-- 
    Document   : pos_training_edit
    Created on : Feb 5, 2015, 1:13:52 PM
    Author     : Dimata 007
--%>

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
    long oidPosTrain = FRMQueryString.requestLong(request, "hidden_pos_train_id");
    String comm = request.getParameter("comm");
    String oid = request.getParameter("oid");
    int msg = 0;
    if (oidPosTrain != 0 && oidPosTrain > 0){
        oid = String.valueOf(oidPosTrain);
        msg = 1;
    }
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    
    CtrlPositionTrainingRequired ctrPosTraining = new CtrlPositionTrainingRequired(request);

    /*switch statement */
    iErrCode = ctrPosTraining.action(iCommand, oidPosTrain);
    /* end switch*/
    FrmPositionTrainingRequired frmPosTrain = ctrPosTraining.getForm();

%>
<%
    
    Vector listPosTrain = new Vector();
    listPosTrain = PstPositionTrainingRequired.listInnerJoinVer1(oid);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Training Edit</title>
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
                document.<%=FrmPositionTrainingRequired.FRM_NAME_POSITION_TRAINING_REQUIRED%>.action = "pos_training_edit.jsp";
                document.<%=FrmPositionTrainingRequired.FRM_NAME_POSITION_TRAINING_REQUIRED%>.submit();
            }
            function cmdEdit(oid) {
                document.<%=FrmPositionTrainingRequired.FRM_NAME_POSITION_TRAINING_REQUIRED%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmPositionTrainingRequired.FRM_NAME_POSITION_TRAINING_REQUIRED%>.hidden_comp_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmPositionTrainingRequired.FRM_NAME_POSITION_TRAINING_REQUIRED%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
        </script>
    </head>
    <body>
        <div class="header">
            <h2>Training Edit</h2>
        </div>
        <div class="content">
        <form name="<%=FrmPositionTrainingRequired.FRM_NAME_POSITION_TRAINING_REQUIRED%>" action="" method="post">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <input type="hidden" name="hidden_pos_train_id" value="<%=oid%>">
        <%
        if (listPosTrain != null && listPosTrain.size() > 0){
            for(int i=0; i<listPosTrain.size(); i++){
                Vector vect = (Vector)listPosTrain.get(i);
                PositionTrainingRequired posTrain = (PositionTrainingRequired)vect.get(0);
                Position pos = (Position)vect.get(1);
                Training train = (Training)vect.get(2);
            
        %>
        
        <table>
            <tr>
                <td valign="top">Position<input type="hidden" name="<%=frmPosTrain.fieldNames[frmPosTrain.FRM_FIELD_POSITION_ID]%>" value="<%=posTrain.getPositionId()%>" /></td>
                <td valign="top"><%=pos.getPosition()%></td>
            </tr>
            <tr>
                <td valign="top">Training<input type="hidden" name="<%=frmPosTrain.fieldNames[frmPosTrain.FRM_FIELD_TRAINING_ID]%>" value="<%=posTrain.getTrainingId()%>" /></td>
                <td valign="top"><%=train.getName()%></td>
            </tr>
            <tr>
                <td valign="top">Duration Min</td>
                <td valign="top"><input type="text" name="<%=frmPosTrain.fieldNames[frmPosTrain.FRM_FIELD_DURATION_MIN]%>" value="<%=posTrain.getDurationMin()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Duration Recommended</td>
                <td valign="top"><input type="text" name="<%=frmPosTrain.fieldNames[frmPosTrain.FRM_FIELD_DURATION_RECOMMENDED]%>" value="<%=posTrain.getDurationRecommended()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Point Min</td>
                <td valign="top"><input type="text" name="<%=frmPosTrain.fieldNames[frmPosTrain.FRM_FIELD_POINT_MIN]%>" value="<%=posTrain.getPointMin()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Point Recommended</td>
                <td valign="top"><input type="text" name="<%=frmPosTrain.fieldNames[frmPosTrain.FRM_FIELD_POINT_RECOMMENDED]%>" value="<%=posTrain.getPointRecommended()%>" /></td>
            </tr>
            <tr>
                <td valign="top">Note</td>
                <td valign="top">
                    <textarea name="<%=frmPosTrain.fieldNames[frmPosTrain.FRM_FIELD_NOTE]%>"><%=posTrain.getNote()%></textarea>
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
