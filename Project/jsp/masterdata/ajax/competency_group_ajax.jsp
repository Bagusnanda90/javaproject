<%-- 
    Document   : competency_type_ajax
    Created on : Jun 29, 2016, 7:59:07 PM
    Author     : ARYS
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmCompetencyGroup"%>
<%@page import="com.dimata.harisma.entity.masterdata.CompetencyGroup"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetencyGroup"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCompetencyGroup"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_COMPETENCE_GROUP);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String comgroupName = FRMQueryString.requestString(request, "comgroup_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listcomgroup")){
        String whereClause = "";
        String order = "";
        Vector listcomgroup = new Vector();

        CtrlCompetencyGroup ctrlCompetencyGroup = new CtrlCompetencyGroup(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstCompetencyGroup.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlCompetencyGroup.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(comgroupName.equals(""))){
            whereClause = PstCompetencyGroup.fieldNames[PstCompetencyGroup.FLD_GROUP_NAME]+" LIKE '%"+comgroupName+"%'";
            order = PstCompetencyGroup.fieldNames[PstCompetencyGroup.FLD_GROUP_NAME];
            vectSize = PstCompetencyGroup.getCount(whereClause);
            listcomgroup = PstCompetencyGroup.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstCompetencyGroup.getCount("");
            order = PstCompetencyGroup.fieldNames[PstCompetencyGroup.FLD_GROUP_NAME];
            listcomgroup = PstCompetencyGroup.list(start, recordToGet, "", order);
        }

        if (listcomgroup != null && listcomgroup.size()>0){
        %>
        <table id="listcomgroup" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Group Name</th>
                    <th>Note</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listcomgroup.size(); i++) {
                CompetencyGroup competencyGroup = (CompetencyGroup) listcomgroup.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=competencyGroup.getGroupName()%></td>
                    <td><%=competencyGroup.getNote()%></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= competencyGroup.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showcomgoupform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= competencyGroup.getOID() %>" data-for="showcomgoupform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
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
    }else if(datafor.equals("showcomgoupform")){

        if(iCommand == Command.SAVE){
            CtrlCompetencyGroup ctrlCompetencyGroup = new CtrlCompetencyGroup(request);
            ctrlCompetencyGroup.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlCompetencyGroup ctrlCompetencyGroup = new CtrlCompetencyGroup(request);
            ctrlCompetencyGroup.action(iCommand, oid);
        }else{
            CompetencyGroup competencyGroup = new CompetencyGroup();
            if(oid != 0){
                try{
                    competencyGroup = PstCompetencyGroup.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
        <div class="form-group">
                <label for="Company">Group Name :</label>
                <input type="text" class="form-control" id="name" value="<%= competencyGroup.getGroupName() %>" name="<%=FrmCompetencyGroup.fieldNames[FrmCompetencyGroup.FRM_FIELD_GROUP_NAME]%>">
            </div>
            <div class="form-group">
                <label for="Address">Note :</label>
                <textarea rows="5" class="form-control" id="Note" name="<%=FrmCompetencyGroup.fieldNames[FrmCompetencyGroup.FRM_FIELD_NOTE]%>"><%= competencyGroup.getNote() %></textarea>
            </div>
            
        <%
    }
}
    
%>