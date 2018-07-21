<%-- 
    Document   : warningreprimand_pasal_ajax
    Created on : 28-Jun-2016, 14:22:43
    Author     : Acer
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmWarningReprimandPasal"%>
<%@page import="com.dimata.harisma.entity.masterdata.WarningReprimandPasal"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstWarningReprimandPasal"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlWarningReprimandPasal"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_WARNING);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String pasalName = FRMQueryString.requestString(request, "pasal_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listpasal")){
        String whereClause = "";
        String order = "";
        Vector listWarningReprimandPasal = new Vector();

        CtrlWarningReprimandPasal ctrlWarningReprimandPasal = new CtrlWarningReprimandPasal(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstWarningReprimandPasal.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlWarningReprimandPasal.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(pasalName.equals(""))){
            whereClause = PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_TITLE]+" LIKE '%"+pasalName+"%'";
            order = PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_TITLE];
            vectSize = PstWarningReprimandPasal.getCount(whereClause);
            listWarningReprimandPasal = PstWarningReprimandPasal.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstWarningReprimandPasal.getCount("");
            order = PstWarningReprimandPasal.fieldNames[PstWarningReprimandPasal.FLD_PASAL_TITLE];
            listWarningReprimandPasal = PstWarningReprimandPasal.list(start, recordToGet, "", order);
        }

        if (listWarningReprimandPasal != null && listWarningReprimandPasal.size()>0){
        %>
        <table id="listWarningReprimandPasal" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Article</th>
                    <th>Chapter</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listWarningReprimandPasal.size(); i++) {
                    WarningReprimandPasal warningReprimandPasal = (WarningReprimandPasal)listWarningReprimandPasal.get(i);
                    WarningReprimandBab warningReprimandBab = new WarningReprimandBab();
                    String bab = "-";
                    try {
                        warningReprimandBab = PstWarningReprimandBab.fetchExc(warningReprimandPasal.getBabId());
                        bab = warningReprimandBab.getBabTitle();
                    } catch (Exception e) {
                        //System.out.print("getDivisionType=>"+e.toString());//
                    }
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= warningReprimandPasal.getPasalTitle()%></td>
                <td><%= bab %></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= warningReprimandPasal.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showpasalform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= warningReprimandPasal.getOID() %>" data-for="showpasalform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                        
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
    }else if(datafor.equals("showpasalform")){

        if(iCommand == Command.SAVE){
            CtrlWarningReprimandPasal ctrlWarningReprimandPasal = new CtrlWarningReprimandPasal(request);
            ctrlWarningReprimandPasal.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlWarningReprimandPasal ctrlWarningReprimandPasal = new CtrlWarningReprimandPasal(request);
            ctrlWarningReprimandPasal.action(iCommand, oid);
        }else{
            WarningReprimandPasal warningReprimandPasal = new WarningReprimandPasal();
            if(oid != 0){
                try{
                    warningReprimandPasal = PstWarningReprimandPasal.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label>Article :</label>
                <input type="text" name="<%=FrmWarningReprimandPasal.fieldNames[FrmWarningReprimandPasal.FRM_FIELD_PASAL_TITLE]%>" value="<%=warningReprimandPasal.getPasalTitle()%>"  class="form-control" placeholder="Type Article Title..." >
            </div>
            <div class="form-group">
                <label>Chapter:</label>
                <select class="form-control" name="<%=FrmWarningReprimandPasal.fieldNames[FrmWarningReprimandPasal.FRM_FIELD_BAB_ID]%>">
                    <option value="0">-Select-</option>
                    <%
                    Vector listBab = PstWarningReprimandBab.listAll(); 
                    for(int idx=0; idx < listBab.size();idx++){
                        WarningReprimandBab warningReprimandBab = (WarningReprimandBab) listBab.get(idx);																						
                        if (warningReprimandPasal.getBabId()== warningReprimandBab.getOID()) {
                        %>
                        <option selected="selected" value="<%=warningReprimandBab.getOID()%>"><%= warningReprimandBab.getBabTitle()%></option>
                        <%
                        } else {
                        %>
                        <option value="<%=warningReprimandBab.getOID()%>"><%= warningReprimandBab.getBabTitle()%></option>
                        <%
                        }												
                    }
                %>
                </select>
            </div>  
        <%
    }
}
    
%>    