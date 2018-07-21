<%-- 
    Document   : training_program_browse
    Created on : Oct 10, 2015, 10:20:16 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.employee.FrmTrainingHistory"%>
<%@page import="com.dimata.harisma.entity.masterdata.Training"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstTraining"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String trainingName = FRMQueryString.requestString(request, "training_name");
    String whereClause = PstTraining.fieldNames[PstTraining.FLD_NAME]+" LIKE '%"+trainingName+"%'";
    String order = ""+PstTraining.fieldNames[PstTraining.FLD_NAME];
    
    Vector listTraining = new Vector();
    if (trainingName.length() > 0){
        listTraining = PstTraining.list(0, 0, whereClause, order);
    } else {
        listTraining = PstTraining.list(0, 0, "", order);
    }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Training Program</title>
        <style type="text/css">
            body {
                color:#575757;
                font-size: 11px;
                font-family: sans-serif;
                background-color: #F5F5F5;
            }
            .item {
                padding: 3px;
                border:1px solid #CCC;
                border-radius: 3px;
                background-color: #EEE;
                margin: 3px;
                cursor: pointer;
            }
            .teks {
                font-size: 11px;
                color:#474747;
                padding: 5px; 
                border:1px solid #CCC;
                border-radius: 3px;
                margin: 3px;
            }
        </style>
        <script language="javascript">
            function cmdGetItem(oid, posname) {
                self.opener.document.fredit.<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_PROGRAM] %>.value = posname;       
                self.opener.document.fredit.<%= FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_ID] %>.value = oid; 
                self.close();
            }
        </script>
    </head>
    <body>
        <h1 style="border-bottom: 1px solid #0599ab; padding-bottom: 12px;">Training Program</h1>
        <form method="post" name="frm" action="">
            <input class="teks" placeholder="search position..." type="text" name="position_name" size="50" />
        </form>
        <%
        if (listTraining != null && listTraining.size()>0){
            for(int i=0; i<listTraining.size(); i++){
                Training training = (Training)listTraining.get(i);
                %>
                <div class="item" onclick="javascript:cmdGetItem('<%=training.getOID()%>', '<%=training.getName()%>')"><%=training.getName()%></div>
                <%
            }
        }
        %>
    </body>
</html>
