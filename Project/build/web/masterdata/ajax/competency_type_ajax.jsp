<%-- 
    Document   : competency_type_ajax
    Created on : Jun 29, 2016, 7:59:07 PM
    Author     : ARYS
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmCompetencyType"%>
<%@page import="com.dimata.harisma.entity.masterdata.CompetencyType"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetencyType"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCompetencyType"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_COMPETENCE_TYPE);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String comtypeName = FRMQueryString.requestString(request, "competency_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listcomtype")){
        String whereClause = "";
        String order = "";
        Vector listcomtype = new Vector();

        CtrlCompetencyType ctrlCompetencyType = new CtrlCompetencyType(request);

        int recordToGet = 3;
        int vectSize = 0;
        vectSize = PstCompetencyType.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlCompetencyType.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(comtypeName.equals(""))){
            whereClause = PstCompetencyType.fieldNames[PstCompetencyType.FLD_TYPE_NAME]+" LIKE '%"+comtypeName+"%'";
            order = PstCompetencyType.fieldNames[PstCompetencyType.FLD_TYPE_NAME];
            vectSize = PstCompetencyType.getCount(whereClause);
            listcomtype = PstCompetencyType.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstCompetencyType.getCount("");
            order = PstCompetencyType.fieldNames[PstCompetencyType.FLD_TYPE_NAME];
            listcomtype = PstCompetencyType.list(start, recordToGet, "", order);
        }

        if (listcomtype != null && listcomtype.size()>0){
        %>
        <table id="listcomtype" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Type Name</th>
                    <th>Note</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listcomtype.size(); i++) {
                CompetencyType competencyType = (CompetencyType) listcomtype.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=competencyType.getTypeName()%></td>
                    <td><%=competencyType.getNote()%></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= competencyType.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showcomtypeform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= competencyType.getOID() %>" data-for="showcomtypeform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                      
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
    }else if(datafor.equals("showcomtypeform")){

        if(iCommand == Command.SAVE){
            CtrlCompetencyType ctrlCompetencyType = new CtrlCompetencyType(request);
            ctrlCompetencyType.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlCompetencyType ctrlCompetencyType = new CtrlCompetencyType(request);
            ctrlCompetencyType.action(iCommand, oid);
        }else{
            CompetencyType competencyType = new CompetencyType();
            if(oid != 0){
                try{
                    competencyType = PstCompetencyType.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
        <div class="form-group">
                <label for="Company">Type Name :</label>
                <input type="text" class="form-control" id="name" value="<%= competencyType.getTypeName() %>" name="<%=FrmCompetencyType.fieldNames[FrmCompetencyType.FRM_FIELD_TYPE_NAME]%>">
            </div>
            <div class="form-group">
                <label for="Address">Note :</label>
                <textarea rows="5" class="form-control" id="Note" name="<%=FrmCompetencyType.fieldNames[FrmCompetencyType.FRM_FIELD_NOTE]%>"><%= competencyType.getNote() %></textarea>
            </div>
            
        <%
    }
}
    
%>