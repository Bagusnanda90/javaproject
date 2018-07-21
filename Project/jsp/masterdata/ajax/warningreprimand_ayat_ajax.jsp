<%-- 
    Document   : warningreprimand_ayat_ajax
    Created on : 28-Jun-2016, 15:28:33
    Author     : Acer
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmWarningReprimandAyat"%>
<%@page import="com.dimata.harisma.entity.masterdata.WarningReprimandAyat"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlWarningReprimandAyat"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_WARNING);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String ayatName = FRMQueryString.requestString(request, "ayat_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listayat")){
        String whereClause = "";
        String order = "";
        Vector listWarningReprimandAyat = new Vector();

        CtrlWarningReprimandAyat ctrlWarningReprimandAyat = new CtrlWarningReprimandAyat(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstWarningReprimandAyat.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlWarningReprimandAyat.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(ayatName.equals(""))){
            whereClause = PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_TITLE]+" LIKE '%"+ayatName+"%'";
            order = PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_TITLE];
            vectSize = PstWarningReprimandAyat.getCount(whereClause);
            listWarningReprimandAyat = PstWarningReprimandAyat.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstWarningReprimandAyat.getCount("");
            order = PstWarningReprimandAyat.fieldNames[PstWarningReprimandAyat.FLD_AYAT_TITLE];
            listWarningReprimandAyat = PstWarningReprimandAyat.list(start, recordToGet, "", order);
        }

        if (listWarningReprimandAyat != null && listWarningReprimandAyat.size()>0){
        %>
        <table id="listWarningReprimandAyat" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Verse</th>
                    <th>Description</th>
                    <th>Article</th>
                    <th>Page</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listWarningReprimandAyat.size(); i++) {
                    WarningReprimandAyat warningReprimandAyat = (WarningReprimandAyat)listWarningReprimandAyat.get(i);
                    WarningReprimandPasal warningReprimandPasal = new WarningReprimandPasal();
                    String pasal = "-";
                    try {
                        warningReprimandPasal = PstWarningReprimandPasal.fetchExc(warningReprimandAyat.getPasalId());
                        pasal = warningReprimandPasal.getPasalTitle();
                    } catch (Exception e) {
                        //System.out.print("getDivisionType=>"+e.toString());//
                    }
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= warningReprimandAyat.getAyatTitle()%></td>
                <td><%= warningReprimandAyat.getAyatDescription()%></td>
                <td><%= pasal %></td>
                <td><%= warningReprimandAyat.getAyatPage()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= warningReprimandAyat.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showayatform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= warningReprimandAyat.getOID() %>" data-for="showayatform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                        
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
    }else if(datafor.equals("showayatform")){

        if(iCommand == Command.SAVE){
            CtrlWarningReprimandAyat ctrlWarningReprimandAyat = new CtrlWarningReprimandAyat(request);
            ctrlWarningReprimandAyat.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlWarningReprimandAyat ctrlWarningReprimandAyat = new CtrlWarningReprimandAyat(request);
            ctrlWarningReprimandAyat.action(iCommand, oid);
        }else{
            WarningReprimandAyat warningReprimandAyat = new WarningReprimandAyat();
            if(oid != 0){
                try{
                    warningReprimandAyat = PstWarningReprimandAyat.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label>Verse :</label>
                <input type="text" name="<%=FrmWarningReprimandAyat.fieldNames[FrmWarningReprimandAyat.FRM_FIELD_AYAT_TITLE]%>" value="<%=warningReprimandAyat.getAyatTitle()%>"  class="form-control" placeholder="Type Verse Title..." >
            </div>
            <div class="form-group">
                <label>Description :</label>
                <textarea name="<%= FrmWarningReprimandAyat.fieldNames[FrmWarningReprimandAyat.FRM_FIELD_AYAT_DESCRIPTION]%>" class="form-control"><%=warningReprimandAyat.getAyatDescription()%></textarea>
            </div>    
            <div class="form-group">
                <label>Chapter:</label>
                <select class="form-control" name="<%=FrmWarningReprimandAyat.fieldNames[FrmWarningReprimandAyat.FRM_FIELD_PASAL_ID]%>">
                    <option value="0">-Select-</option>
                    <%
                    Vector listPasal = PstWarningReprimandPasal.listAll(); 
                    for(int idx=0; idx < listPasal.size();idx++){
                        WarningReprimandPasal warningReprimandPasal = (WarningReprimandPasal) listPasal.get(idx);																						
                        if (warningReprimandAyat.getPasalId()== warningReprimandPasal.getOID()) {
                        %>
                        <option selected="selected" value="<%=warningReprimandPasal.getOID()%>"><%= warningReprimandPasal.getPasalTitle()%></option>
                        <%
                        } else {
                        %>
                        <option value="<%=warningReprimandPasal.getOID()%>"><%= warningReprimandPasal.getPasalTitle()%></option>
                        <%
                        }												
                    }
                %>
                </select>
            </div>
                <div class="form-group">
                    <label>Page :</label>
                    <input type="text" name="<%= FrmWarningReprimandAyat.fieldNames[FrmWarningReprimandAyat.FRM_FIELD_AYAT_PAGE]%>" class="form-control" value="<%= warningReprimandAyat.getAyatPage() %>">
                </div>
        <%
    }
}
    
%>    