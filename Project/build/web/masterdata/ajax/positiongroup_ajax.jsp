<%-- 
    Document   : positiongroup_ajax
    Created on : 28-Jun-2016, 11:49:15
    Author     : Acer
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPositionGroup"%>
<%@page import="com.dimata.harisma.entity.masterdata.PositionGroup"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionGroup"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPositionGroup"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String posGroupName = FRMQueryString.requestString(request, "pos_group_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listposgroup")){
        String whereClause = "";
        String order = "";
        Vector listPositionGroup = new Vector();

        CtrlPositionGroup ctrlPositionGroup = new CtrlPositionGroup(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstPositionGroup.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlPositionGroup.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(posGroupName.equals(""))){
            whereClause = PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_NAME]+" LIKE '%"+posGroupName+"%'";
            order = PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_NAME];
            vectSize = PstPositionGroup.getCount(whereClause);
            listPositionGroup = PstPositionGroup.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstPositionGroup.getCount("");
            order = PstPositionGroup.fieldNames[PstPositionGroup.FLD_POSITION_GROUP_NAME];
            listPositionGroup = PstPositionGroup.list(start, recordToGet, "", order);
        }

        if (listPositionGroup != null && listPositionGroup.size()>0){
        %>
        <table id="listPositionGroup" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Position Group</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listPositionGroup.size(); i++) {
                    PositionGroup positionGroup = (PositionGroup)listPositionGroup.get(i);
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= positionGroup.getPositionGroupName()%></td>
                <td><%= positionGroup.getDescription()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= positionGroup.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showposgroupform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= positionGroup.getOID() %>" data-for="showposgroupform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
                    </td>
                </tr>
                <%
            }
            %>
        </table>
            
        <%
        } else { %>
            <a> <h4>No Record </h4></a>
        <% }
    }else if(datafor.equals("showposgroupform")){

        if(iCommand == Command.SAVE){
            CtrlPositionGroup ctrlPositionGroup = new CtrlPositionGroup(request);
            ctrlPositionGroup.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlPositionGroup ctrlPositionGroup = new CtrlPositionGroup(request);
            ctrlPositionGroup.action(iCommand, oid);
        }else{
            PositionGroup positionGroup = new PositionGroup();
            if(oid != 0){
                try{
                    positionGroup = PstPositionGroup.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label>Position Group :</label>
                <input type="text" name="<%=FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_POSITION_GROUP_NAME] %>" class="form-control" placeholder="Position Group" value="<%= positionGroup.getPositionGroupName()%>">
            </div>
            <div class="form-group">
                <label>Description :</label>
                <textarea name="<%=FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_DESCRIPTION] %>" class="form-control" ><%=positionGroup.getDescription()%></textarea> 
            </div>
            
              
        <%
    }
}
    
%>    