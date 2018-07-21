<%-- 
    Document   : doc_type_ajax
    Created on : Jun 29, 2016, 3:06:34 PM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocType"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocType"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDocType"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DOCUMENT_TYPE);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String doctypeName = FRMQueryString.requestString(request, "docType_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listdoctype")){
        String whereClause = "";
        String order = "";
        Vector listdoctype = new Vector();

        CtrlDocType ctrlDocType = new CtrlDocType(request);

        int recordToGet = 3;
        int vectSize = 0;
        vectSize = PstDocType.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlDocType.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(doctypeName.equals(""))){
            whereClause = PstDocType.fieldNames[PstDocType.FLD_TYPE_NAME]+" LIKE '%"+doctypeName+"%'";
            order = PstDocType.fieldNames[PstDocType.FLD_TYPE_NAME];
            vectSize = PstDocType.getCount(whereClause);
            listdoctype = PstDocType.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstDocType.getCount("");
            order = PstDocType.fieldNames[PstDocType.FLD_TYPE_NAME];
            listdoctype = PstDocType.list(start, recordToGet, "", order);
        }

        if (listdoctype != null && listdoctype.size()>0){
        %>
        <table id="listdoctype" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Type Name</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listdoctype.size(); i++) {
                DocType docType = (DocType) listdoctype.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=docType.getType_name()%></td>
                    <td><%=docType.getDescription()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= docType.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showdoctypeform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= docType.getOID() %>" data-for="showdoctypeform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
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
    }else if(datafor.equals("showdoctypeform")){

        if(iCommand == Command.SAVE){
            CtrlDocType ctrlDocType = new CtrlDocType(request);
            ctrlDocType.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlDocType ctrlDocType = new CtrlDocType(request);
            ctrlDocType.action(iCommand, oid);
        }else{
            DocType docType = new DocType();
            if(oid != 0){
                try{
                    docType = PstDocType.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
        <div class="form-group">
                <label for="Company">Type Name :</label>
                <input type="text" class="form-control" id="type" value="<%= docType.getType_name() %>" name="<%=FrmDocType.fieldNames[FrmDocType.FRM_FIELD_TYPE_NAME]%>">
            </div>
            <div class="form-group">
                <label for="Address">Description :</label>
                <textarea rows="5" class="form-control" id="description" name="<%=FrmDocType.fieldNames[FrmDocType.FRM_FIELD_DESCRIPTION]%>"><%= docType.getDescription() %></textarea>
            </div>
            
        <%
    }
}
    
%>