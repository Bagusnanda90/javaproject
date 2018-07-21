<%-- 
    Document   : empwarning_ajax
    Created on : Jun 28, 2016, 4:34:40 PM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmWarning"%>
<%@page import="com.dimata.harisma.entity.masterdata.Warning"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstWarning"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlWarning"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_WARNING);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String warName = FRMQueryString.requestString(request, "warning_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listwarning")){
        String whereClause = "";
        String order = "";
        Vector listwarning = new Vector();

        CtrlWarning ctrlWarning = new CtrlWarning(request);

        int recordToGet = 3;
        int vectSize = 0;
        vectSize = PstWarning.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlWarning.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(warName.equals(""))){
            whereClause = PstWarning.fieldNames[PstWarning.FLD_WARN_DESC]+" LIKE '%"+warName+"%'";
            order = PstWarning.fieldNames[PstWarning.FLD_WARN_POINT];
            vectSize = PstWarning.getCount(whereClause);
            listwarning = PstWarning.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstWarning.getCount("");
            order = PstWarning.fieldNames[PstWarning.FLD_WARN_POINT];
            listwarning = PstWarning.list(start, recordToGet, "", order);
        }

        if (listwarning != null && listwarning.size()>0){
        %>
        <table id="example1" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Description</th>
                    <th>Warning Point</th>
                    <th>Action</th>
                    </tr>
            </thead>
            <%
            for (int i = 0; i < listwarning.size(); i++) {
                Warning warning = (Warning) listwarning.get(i);

                %>
             <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=warning.getWarnDesc()%></td>
                    <td><%=warning.getWarnPoint()%></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= warning.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showwarningform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= warning.getOID() %>" data-for="showwarningform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
                    </td>
                </tr>
                <%
            }
            %>
        </table>
            <%
        }
    }else if(datafor.equals("showwarningform")){

        if(iCommand == Command.SAVE){
            CtrlWarning ctrlWarning = new CtrlWarning(request);
            ctrlWarning.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlWarning ctrlWarning = new CtrlWarning(request);
            ctrlWarning.action(iCommand, oid);
        }else{
            Warning warning = new Warning();
            if(oid != 0){
                try{
                    warning = PstWarning.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
        <div class="form-group">
                <label for="Company">Description :</label>
                <input type="text" class="form-control" id="description" value="<%= warning.getWarnDesc() %>" name="<%=FrmWarning.fieldNames[FrmWarning.FRM_FIELD_WARN_DESC]%>">
            </div>
            <div class="form-group">
                <label for="Company">Level :</label>
                <input type="text" class="form-control" id="description" value="<%= "" + warning.getWarnPoint() %>" name="<%=FrmWarning.fieldNames[FrmWarning.FRM_FIELD_WARN_POINT]%>">
            </div>
            
        <%
    }
}
    
%> 