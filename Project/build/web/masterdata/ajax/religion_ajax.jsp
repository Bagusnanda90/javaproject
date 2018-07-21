<%-- 
    Document   : religion_ajax
    Created on : Jun 29, 2016, 10:28:09 AM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmReligion"%>
<%@page import="com.dimata.harisma.entity.masterdata.Religion"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstReligion"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlReligion"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_RELIGION);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String religionName = FRMQueryString.requestString(request, "religion_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listreligion")){
        String whereClause = "";
        String order = "";
        Vector listreligion = new Vector();

        CtrlReligion ctrlReligion = new CtrlReligion(request);

        int recordToGet = 3;
        int vectSize = 0;
        vectSize = PstReligion.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlReligion.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(religionName.equals(""))){
            whereClause = PstReligion.fieldNames[PstReligion.FLD_RELIGION]+" LIKE '%"+religionName+"%'";
            order = PstReligion.fieldNames[PstReligion.FLD_RELIGION];
            vectSize = PstReligion.getCount(whereClause);
            listreligion = PstReligion.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstReligion.getCount("");
            order = PstReligion.fieldNames[PstReligion.FLD_RELIGION];
            listreligion = PstReligion.list(start, recordToGet, "", order);
        }

        if (listreligion != null && listreligion.size()>0){
        %>
            <table id="example1" class="table table-bordered table-hover">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Religion</th>
                        <th>Action</th>
                        </tr>
                </thead>
                <%
                for (int i = 0; i < listreligion.size(); i++) {
                Religion religion = (Religion) listreligion.get(i);

                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=religion.getReligion()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= religion.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showreligionform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= religion.getOID() %>" data-for="showreligionform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
                    </td>
                </tr>
                <%
                }
                %>
            </table>
            
            <%
        }
        }else if(datafor.equals("showreligionform")){

        if(iCommand == Command.SAVE){
            CtrlReligion ctrlReligion = new CtrlReligion(request);
            ctrlReligion.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlReligion ctrlReligion = new CtrlReligion(request);
            ctrlReligion.action(iCommand, oid);
        }else{
            Religion religion = new Religion();
            if(oid != 0){
                try{
                    religion = PstReligion.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Company">Religion Name :</label>
                <input type="text" class="form-control" id="company" value="<%= religion.getReligion() %>" name="<%= FrmReligion.fieldNames[FrmReligion.FRM_FIELD_RELIGION] %>">
            </div>
            </div>
        <%
    }
}
    
%> 