<%-- 
    Document   : training-ajax
    Created on : Dec 30, 2015, 10:26:17 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.masterdata.PstTraining"%>
<%@page import="com.dimata.harisma.entity.masterdata.Training"%>
<%@page import="com.dimata.harisma.entity.employee.TrainingHistory"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.employee.CtrlTrainingHistory"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.employee.PstTrainingHistory"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    int trainSelect = FRMQueryString.requestInt(request, "rb_cari");
    String trainingCari = FRMQueryString.requestString(request, "training_cari");
    
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String whereClause = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] + "=" + oidEmployee;
    int privEdit = FRMQueryString.requestInt(request, "priv_edit");
    int privDel = FRMQueryString.requestInt(request, "priv_del");
    String order = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+" DESC";
    Vector listTrainingHistory = new Vector();
    CtrlTrainingHistory ctrlTrainHistory = new CtrlTrainingHistory(request);
    
    int recordToGet = 10;
    int vectSize = 0;
    vectSize = PstTrainingHistory.getCount(PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] + "=" + oidEmployee);
    if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
            || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
        start = ctrlTrainHistory.actionList(iCommand, start, vectSize, recordToGet);
    }
    
    if (trainSelect == 0){ /* Searching by Training Program */
        if (!(trainingCari.equals("0"))){
            whereClause += " AND "+ PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_PROGRAM]+" LIKE '%"+trainingCari+"%'";
        }
        listTrainingHistory = PstTrainingHistory.list(start, recordToGet, whereClause, order);
    } else { /* Searching by Training Title */
        if (!(trainingCari.equals("0"))){
            whereClause += " AND "+ PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_TITLE]+" LIKE '%"+trainingCari+"%'";
        }
        listTrainingHistory = PstTrainingHistory.list(start, recordToGet, whereClause, order);
    }

    int recordCount = start+recordToGet;
    if (vectSize < recordToGet){
        recordCount = vectSize;
    }
    if (vectSize < recordCount){
        recordCount = vectSize;
    }
    
    if (listTrainingHistory != null && listTrainingHistory.size()>0){
        %>
        <table class="tblStyle">
            <tr>
                <td class="title_tbl" style="background-color: #DDD;">Training Program</td>
                <td class="title_tbl" style="background-color: #DDD;">Training Title</td>
                <td class="title_tbl" style="background-color: #DDD;">Trainer</td>
                <td class="title_tbl" style="background-color: #DDD;">Start Date</td>
                <td class="title_tbl" style="background-color: #DDD;">End Date</td>
                <td class="title_tbl" style="background-color: #DDD;">Duration</td>
                <td class="title_tbl" style="background-color: #DDD;">Remark</td>
                <td class="title_tbl" style="background-color: #DDD;">&nbsp;</td>
            </tr>
        <%
        for(int i=0; i<listTrainingHistory.size(); i++){
            TrainingHistory trainHistory = (TrainingHistory)listTrainingHistory.get(i);
            String trainingName = ""+trainHistory.getTrainingProgram();
            trainHistory.getTrainingId();
            double duration = trainHistory.getDuration() / 60;
            %>
            <tr>
                <td><%= trainingName %></td>
                <td><%= trainHistory.getTrainingTitle()!=null && trainHistory.getTrainingTitle().length()>0 ? trainHistory.getTrainingTitle() : "-" %></td>
                <td><%= trainHistory.getTrainer() != null && trainHistory.getTrainer().length()>0 ? trainHistory.getTrainer() : "-" %></td>
                <td><%= ""+trainHistory.getStartDate() %></td>
                <td><%= ""+trainHistory.getEndDate() %></td>
                <td><%= ""+ duration+ " Jam - "+trainHistory.getDuration() %></td>
                <td><%= trainHistory.getRemark() %></td>
                <td>
                    <%
                    if (privEdit == 1){
                        %>
                        <div class="btn-small" onclick="javascript:cmdEdit('<%=trainHistory.getOID()%>')" style="color: #FFF">E</div>
                        <%
                    }
                    %>
                    <%
                    if (privDel == 1){
                        %>
                        <div class="btn-small" onclick="javascript:cmdAsk('<%=trainHistory.getOID()%>')" style="color: #FFF">&times;</div>
                        <%
                    }
                    %>
                </td>
            </tr>
            <%
        }
        %>
        </table>
        
        <div>&nbsp;</div>
        <div id="record_count">List : <%=(start+1)%> &HorizontalLine; <%= (recordCount) %> | Total : <%= vectSize %></div>
        <div class="pagging">
            <a style="color:#F5F5F5" href="javascript:cmdListFirst('<%=start%>')" class="btn-small">First</a>
            <a style="color:#F5F5F5" href="javascript:cmdListPrev('<%=start%>')" class="btn-small">Previous</a>
            <a style="color:#F5F5F5" href="javascript:cmdListNext('<%=start%>')" class="btn-small">Next</a>
            <a style="color:#F5F5F5" href="javascript:cmdListLast('<%=start%>')" class="btn-small">Last</a>
        </div>
        <%
    }
%>