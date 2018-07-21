<%-- 
    Document   : race_ajax
    Created on : Jun 29, 2016, 10:55:15 AM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmRace"%>
<%@page import="com.dimata.harisma.entity.masterdata.Race"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstRace"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlRace"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_RACE);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String raceName = FRMQueryString.requestString(request, "race_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listrace")){
        String whereClause = "";
        String order = "";
        Vector listrace = new Vector();

        CtrlRace ctrlRace = new CtrlRace(request);

        int recordToGet = 3;
        int vectSize = 0;
        vectSize = PstRace.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlRace.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(raceName.equals(""))){
            whereClause = PstRace.fieldNames[PstRace.FLD_RACE_NAME]+" LIKE '%"+raceName+"%'";
            order = PstRace.fieldNames[PstRace.FLD_RACE_NAME];
            vectSize = PstRace.getCount(whereClause);
            listrace = PstRace.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstRace.getCount("");
            order = PstRace.fieldNames[PstRace.FLD_RACE_NAME];
            listrace = PstRace.list(start, recordToGet, "", order);
        }

        if (listrace != null && listrace.size()>0){
        %>
        <table id="example1" class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Race</th>
                    <th>Action</th>
                    </tr>
            </thead>
            <%
            for (int i = 0; i < listrace.size(); i++) {
                Race race = (Race) listrace.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=race.getRaceName()%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= race.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showraceform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= race.getOID() %>" data-for="showraceform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
                    </td>
                </tr>
                <%
            }
            %>
        </table>
        
            <%
        }
    }else if(datafor.equals("showraceform")){

        if(iCommand == Command.SAVE){
            CtrlRace ctrlRace = new CtrlRace(request);
            ctrlRace.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlRace ctrlRace = new CtrlRace(request);
            ctrlRace.action(iCommand, oid);
        }else{
            Race race = new Race();
            if(oid != 0){
                try{
                    race = PstRace.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
        <div class="form-group">
                <label for="Company">Race Name :</label>
                <input type="text" class="form-control" id="description" value="<%= race.getRaceName() %>" name="<%=FrmRace.fieldNames[FrmRace.FRM_FIELD_RACE_NAME]%>">
            </div>
            
        <%
    }
}
    
%> 