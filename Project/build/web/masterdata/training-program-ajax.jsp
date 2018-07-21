<%-- 
    Document   : training-program-ajax
    Created on : Dec 28, 2015, 5:03:55 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlTraining"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstTrainType"%>
<%@page import="com.dimata.harisma.entity.masterdata.TrainType"%>
<%@page import="com.dimata.harisma.entity.masterdata.Training"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstTraining"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_PROGRAM, AppObjInfo.OBJ_MENU_TRAINING_PROGRAM);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String trainingName = FRMQueryString.requestString(request, "training_name");
    String typeName = FRMQueryString.requestString(request, "type_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String whereClause = "";
    String order = "";
    Vector listTraining = new Vector();
    CtrlTraining ctrTraining = new CtrlTraining(request);
    
    int recordToGet = 10;
    int vectSize = 0;
    vectSize = PstTraining.getCount("");
    if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
            || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
        start = ctrTraining.actionList(iCommand, start, vectSize, recordToGet);
    }

    if (!(trainingName.equals("0"))){
        if (typeName.equals("0")){
            whereClause = PstTraining.fieldNames[PstTraining.FLD_NAME]+" LIKE '%"+trainingName+"%'";
            order = PstTraining.fieldNames[PstTraining.FLD_NAME];
            vectSize = PstTraining.getCount(whereClause);
            listTraining = PstTraining.list(0, 0, whereClause, order);
        } else {
            whereClause = PstTraining.fieldNames[PstTraining.FLD_TYPE]+"="+typeName;
            vectSize = PstTraining.getCount(whereClause);
            listTraining = PstTraining.list(0, 0, whereClause, order);
        }
        
    } else {
        vectSize = PstTraining.getCount("");
        order = PstTraining.fieldNames[PstTraining.FLD_NAME];
        listTraining = PstTraining.list(start, recordToGet, "", order);
    }

    if (listTraining != null && listTraining.size()>0){
        %>
        <table class="tblStyle">
            <tr>
                <td class="title_tbl" style="background-color: #DDD;">No</td>
                <td class="title_tbl" style="background-color: #DDD;">Name</td>
                <td class="title_tbl" style="background-color: #DDD;">Type</td>
                <td class="title_tbl" style="background-color: #DDD;">Action</td>
            </tr>
        <%
        for(int i=0; i<listTraining.size(); i++){
            Training training = (Training)listTraining.get(i);
            TrainType type = new TrainType();
                         
            try {
                type = PstTrainType.fetchExc(training.getType());
            } catch(Exception e) {
                System.out.println(""+e.toString());
                type = new TrainType();
            }
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= training.getName() %></td>
                <td><%= type.getTypeName() %></td>
                
                <td>
                    <% if(privUpdate){ %>
                    <a class="btn-small-1" style="color:#575757;" href="javascript:cmdEdit('<%=training.getOID()%>')">Edit</a>
                    <% } else { %>                    
                    <a class="btn-small-1" style="color:#575757;" href="javascript:cmdEdit('<%=training.getOID()%>')">View Material</a>
                    <% } %>
                    &nbsp;
                    <% if(privDelete){ %>
                    <a class="btn-small-1" style="color:#575757;" href="javascript:cmdAsk('<%=training.getOID()%>')">Delete</a>
                    <% } %>
                </td>
                
            </tr>
            <%
        }
        %>
        </table>
        
        <div>&nbsp;</div>
        <div id="record_count">
            <%
            if (vectSize >= recordToGet){
                %>
                List : <%=start%> &HorizontalLine; <%= (start+recordToGet) %> | 
                <%
            }
            %>
            Total : <%= vectSize %>
        </div>
        <div class="pagging">
            <a style="color:#F5F5F5" href="javascript:cmdListFirst('<%=start%>')" class="btn-small">First</a>
            <a style="color:#F5F5F5" href="javascript:cmdListPrev('<%=start%>')" class="btn-small">Previous</a>
            <a style="color:#F5F5F5" href="javascript:cmdListNext('<%=start%>')" class="btn-small">Next</a>
            <a style="color:#F5F5F5" href="javascript:cmdListLast('<%=start%>')" class="btn-small">Last</a>
        </div>
        <%
    }
%>