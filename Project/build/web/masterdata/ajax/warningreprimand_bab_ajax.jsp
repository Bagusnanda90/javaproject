<%-- 
    Document   : warningreprimand_bab_ajax
    Created on : 28-Jun-2016, 13:49:48
    Author     : Acer
--%>


<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmWarningReprimandBab"%>
<%@page import="com.dimata.harisma.entity.masterdata.WarningReprimandBab"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstWarningReprimandBab"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlWarningReprimandBab"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_WARNING);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String babName = FRMQueryString.requestString(request, "bab_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listbab")){
        String whereClause = "";
        String order = "";
        Vector listWarningReprimandBab = new Vector();

        CtrlWarningReprimandBab ctrlWarningReprimandBab = new CtrlWarningReprimandBab(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstWarningReprimandBab.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlWarningReprimandBab.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(babName.equals(""))){
            whereClause = PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_TITLE]+" LIKE '%"+babName+"%'";
            order = PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_TITLE];
            vectSize = PstWarningReprimandBab.getCount(whereClause);
            listWarningReprimandBab = PstWarningReprimandBab.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstWarningReprimandBab.getCount("");
            order = PstWarningReprimandBab.fieldNames[PstWarningReprimandBab.FLD_BAB_TITLE];
            listWarningReprimandBab = PstWarningReprimandBab.list(start, recordToGet, "", order);
        }

        if (listWarningReprimandBab != null && listWarningReprimandBab.size()>0){
        %>
        <table id="listWarningReprimandBab" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Chapter</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listWarningReprimandBab.size(); i++) {
                    WarningReprimandBab warningReprimandBab = (WarningReprimandBab)listWarningReprimandBab.get(i);
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= warningReprimandBab.getBabTitle()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= warningReprimandBab.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showbabform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= warningReprimandBab.getOID() %>" data-for="showbabform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                        
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
    }else if(datafor.equals("showbabform")){

        if(iCommand == Command.SAVE){
            CtrlWarningReprimandBab ctrlWarningReprimandBab = new CtrlWarningReprimandBab(request);
            ctrlWarningReprimandBab.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlWarningReprimandBab ctrlWarningReprimandBab = new CtrlWarningReprimandBab(request);
            ctrlWarningReprimandBab.action(iCommand, oid);
        }else{
            WarningReprimandBab warningReprimandBab = new WarningReprimandBab();
            if(oid != 0){
                try{
                    warningReprimandBab = PstWarningReprimandBab.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label>Chapter :</label>
                <input type="text" name="<%=FrmWarningReprimandBab.fieldNames[FrmWarningReprimandBab.FRM_FIELD_BAB_TITLE]%>" value="<%=warningReprimandBab.getBabTitle()%>"  class="form-control" placeholder="Type Chapter Title..." >
            </div>
              
        <%
    }
}
    
%>    