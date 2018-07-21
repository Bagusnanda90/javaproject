<%-- 
    Document   : empreprimand_ajax
    Created on : 28-Jun-2016, 16:33:22
    Author     : Acer
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmReprimand"%>
<%@page import="com.dimata.harisma.entity.masterdata.Reprimand"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstReprimand"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlReprimand"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_REPRIMAND);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String repName = FRMQueryString.requestString(request, "rep_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listreprimand")){
        String whereClause = "";
        String order = "";
        Vector listReprimand = new Vector();

        CtrlReprimand ctrlReprimand = new CtrlReprimand(request);
        
        int recordToGet = 10;
        int vectSize = 0;
        vectSize = PstReprimand.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlReprimand.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(repName.equals(""))){
            whereClause = PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_DESC]+" LIKE '%"+repName+"%'";
            order = PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_DESC];
            vectSize = PstReprimand.getCount(whereClause);
            listReprimand = PstReprimand.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstReprimand.getCount("");
            order = PstReprimand.fieldNames[PstReprimand.FLD_REPRIMAND_DESC];
            listReprimand = PstReprimand.list(start, recordToGet, "", order);
        }

        if (listReprimand != null && listReprimand.size()>0){
        %>
        <table id="listReprimand" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Description</th>
                    <th>Reprimand Point</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listReprimand.size(); i++) {
                    Reprimand reprimand = (Reprimand)listReprimand.get(i);
                    
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= reprimand.getReprimandDesc()%></td>
                <td><%= reprimand.getReprimandPoint()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= reprimand.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showrepform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= reprimand.getOID() %>" data-for="showrepform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
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
    }else if(datafor.equals("showrepform")){

        if(iCommand == Command.SAVE){
            CtrlReprimand ctrlReprimand = new CtrlReprimand(request);
            ctrlReprimand.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlReprimand ctrlReprimand = new CtrlReprimand(request);
            ctrlReprimand.action(iCommand, oid);
        }else{
            Reprimand reprimand = new Reprimand();
            if(oid != 0){
                try{
                    reprimand = PstReprimand.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label>Description :</label>
                <input type="text" name="<%=FrmReprimand.fieldNames[FrmReprimand.FRM_FIELD_REPRIMAND_DESC]%>" value="<%=reprimand.getReprimandDesc()%>"  class="form-control" placeholder="Type Reprimand Description..." >
            </div>
            <div class="form-group">
                <label>Reprimand Point :</label>
                <textarea name="<%= FrmReprimand.fieldNames[FrmReprimand.FRM_FIELD_REPRIMAND_POINT]%>" class="form-control"><%=reprimand.getReprimandPoint()%></textarea>
            </div>    
            
        <%
    }
}
    
%>    