<%-- 
    Document   : competency_type_ajax
    Created on : Jun 29, 2016, 7:59:07 PM
    Author     : ARYS
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmEmpRelevantDocGroup"%>
<%@page import="com.dimata.harisma.entity.masterdata.EmpRelevantDocGroup"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpRelevantDocGroup"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlEmpRelevantDocGroup"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMP_DOCUMENT);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String reldocName = FRMQueryString.requestString(request, "reldoc_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listreldoc")){
        String whereClause = "";
        String order = "";
        Vector listreldoc = new Vector();

        CtrlEmpRelevantDocGroup ctrlEmpRelevantDocGroup = new CtrlEmpRelevantDocGroup(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmpRelevantDocGroup.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlEmpRelevantDocGroup.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(reldocName.equals(""))){
            whereClause = PstEmpRelevantDocGroup.fieldNames[PstEmpRelevantDocGroup.FLD_DOC_GROUP]+" LIKE '%"+reldocName+"%'";
            order = PstEmpRelevantDocGroup.fieldNames[PstEmpRelevantDocGroup.FLD_DOC_GROUP];
            vectSize = PstEmpRelevantDocGroup.getCount(whereClause);
            listreldoc = PstEmpRelevantDocGroup.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstEmpRelevantDocGroup.getCount("");
            order = PstEmpRelevantDocGroup.fieldNames[PstEmpRelevantDocGroup.FLD_DOC_GROUP];
            listreldoc = PstEmpRelevantDocGroup.list(start, recordToGet, "", order);
        }

        if (listreldoc != null && listreldoc.size()>0){
        %>
        <table id="listcomgroup" class="table table-bordered table-striped" width="100%">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Doc Group</th>
                    <th>Doc Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listreldoc.size(); i++) {
                EmpRelevantDocGroup empRelevantDocGroup = (EmpRelevantDocGroup) listreldoc.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=empRelevantDocGroup.getDocGroup()%></td>
                    <td><%=empRelevantDocGroup.getDocGroupDesc()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= empRelevantDocGroup.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showreldocform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= empRelevantDocGroup.getOID() %>" data-for="showreldocform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
                    </td>
            </tr>
                <%
            }
            %>
        </table>
        <div>&nbsp;</div>
            <%
        }
    }else if(datafor.equals("showreldocform")){

        if(iCommand == Command.SAVE){
            CtrlEmpRelevantDocGroup ctrlEmpRelevantDocGroup = new CtrlEmpRelevantDocGroup(request);
            ctrlEmpRelevantDocGroup.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlEmpRelevantDocGroup ctrlEmpRelevantDocGroup = new CtrlEmpRelevantDocGroup(request);
            ctrlEmpRelevantDocGroup.action(iCommand, oid);
        }else{
            EmpRelevantDocGroup empRelevantDocGroup = new EmpRelevantDocGroup();
            if(oid != 0){
                try{
                    empRelevantDocGroup = PstEmpRelevantDocGroup.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Company">Group Name :</label>
                <input type="text" class="form-control" id="name" value="<%= empRelevantDocGroup.getDocGroup() %>" name="<%=FrmEmpRelevantDocGroup.fieldNames[FrmEmpRelevantDocGroup.FRM_FIELD_DOC_GROUP]%>">
            </div>
            <div class="form-group">
                <label for="Address">Note :</label>
                <textarea rows="5" class="form-control" id="Note" name="<%=FrmEmpRelevantDocGroup.fieldNames[FrmEmpRelevantDocGroup.FRM_FIELD_DOC_GROUP_DESC]%>"><%= empRelevantDocGroup.getDocGroupDesc() %></textarea>
            </div>
        <%
    }
}
    
%>