<%-- 
    Document   : doc_expenses_ajax
    Created on : Jun 29, 2016, 7:25:07 PM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDocExpenses"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocExpenses"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocExpenses"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDocExpenses"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DOCUMENT_EXPENSE);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String docExName = FRMQueryString.requestString(request, "docEx_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listdocex")){
        String whereClause = "";
        String order = "";
        Vector listdocex = new Vector();

        CtrlDocExpenses ctrlDocExpenses = new CtrlDocExpenses(request);

        int recordToGet = 3;
        int vectSize = 0;
        vectSize = PstDocExpenses.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlDocExpenses.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(docExName.equals(""))){
            whereClause = PstDocExpenses.fieldNames[PstDocExpenses.FLD_EXPENSE_NAME]+" LIKE '%"+docExName+"%'";
            order = PstDocExpenses.fieldNames[PstDocExpenses.FLD_EXPENSE_NAME];
            vectSize = PstDocExpenses.getCount(whereClause);
            listdocex = PstDocExpenses.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstDocExpenses.getCount("");
            order = PstDocExpenses.fieldNames[PstDocExpenses.FLD_EXPENSE_NAME];
            listdocex = PstDocExpenses.list(start, recordToGet, "", order);
        }

        if (listdocex != null && listdocex.size()>0){
        %>
        <table id="listdocex" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Expenses Name</th>
                    <th>Value Expenses</th>
                    <th>Note</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listdocex.size(); i++) {
                DocExpenses docExpenses = (DocExpenses) listdocex.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=docExpenses.getExpense_name()%></td>
                    <td><%=docExpenses.getPlan_expense_value()%></td>
                    <td><%=docExpenses.getNote()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= docExpenses.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showdocexform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= docExpenses.getOID() %>" data-for="showdocexform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
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
    }else if(datafor.equals("showdocexform")){

        if(iCommand == Command.SAVE){
            CtrlDocExpenses ctrlDocExpenses = new CtrlDocExpenses(request);
            ctrlDocExpenses.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlDocExpenses ctrlDocExpenses = new CtrlDocExpenses(request);
            ctrlDocExpenses.action(iCommand, oid);
        }else{
            DocExpenses docExpenses = new DocExpenses();
            if(oid != 0){
                try{
                    docExpenses = PstDocExpenses.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
        <div class="form-group">
                <label for="Company">Expenses Name :</label>
                <input type="text" class="form-control" id="name" value="<%= docExpenses.getExpense_name() %>" name="<%=FrmDocExpenses.fieldNames[FrmDocExpenses.FRM_FIELD_EXPENSE_NAME]%>">
            </div>
            <div class="form-group">
                <label for="Company">Value Expenses :</label>
                <input type="text" class="form-control" id="value" value="<%= docExpenses.getPlan_expense_value() %>" name="<%=FrmDocExpenses.fieldNames[FrmDocExpenses.FRM_FIELD_PLAN_EXPENSE_VALUE]%>">
            </div>
            <div class="form-group">
                <label for="Address">Note :</label>
                <textarea rows="5" class="form-control" id="Note" name="<%=FrmDocExpenses.fieldNames[FrmDocExpenses.FRM_FIELD_NOTE]%>"><%= docExpenses.getNote() %></textarea>
            </div>
            
        <%
    }
}
    
%>
