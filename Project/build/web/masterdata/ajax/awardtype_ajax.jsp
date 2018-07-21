<%-- 
    Document   : awardtype_ajax
    Created on : Jun 29, 2016, 12:49:52 PM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.*"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_AWARD_TYPE);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String awardName = FRMQueryString.requestString(request, "award_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listaward")){
        String whereClause = "";
        String order = "";
        Vector listaward = new Vector();

        CtrlAwardType ctrlAwardType = new CtrlAwardType(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstRace.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlAwardType.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(awardName.equals(""))){
            whereClause = PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE]+" LIKE '%"+awardName+"%'";
            order = PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE];
            vectSize = PstAwardType.getCount(whereClause);
            listaward = PstAwardType.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstAwardType.getCount("");
            order = PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE];
            listaward = PstAwardType.list(start, recordToGet, "", order);
        }

        if (listaward != null && listaward.size()>0){
        %>
        <table id="example1" class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Award Type</th>
                    <th>Description</th>
                    <th>Action</th>
                    </tr>
            </thead>
            <%
            for (int i = 0; i < listaward.size(); i++) {
                AwardType awardType = (AwardType) listaward.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=awardType.getAwardType()%></td>
                    <td><%=awardType.getDescription()%></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= awardType.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showawardtypeform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= awardType.getOID() %>" data-for="showawardtypeform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                   
                    </td>
                </tr>
                <%
            }
            %>
        </table>
        
            <%
        }
    }else if(datafor.equals("showawardtypeform")){

        if(iCommand == Command.SAVE){
            CtrlAwardType ctrlAwardType = new CtrlAwardType(request);
            ctrlAwardType.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlAwardType ctrlAwardType = new CtrlAwardType(request);
            ctrlAwardType.action(iCommand, oid);
        }else{
            AwardType awardType = new AwardType();
            if(oid != 0){
                try{
                    awardType = PstAwardType.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
            <label class="col-sm-4">Type</label>
            <div class="col-sm-7"> <input type="text" class="form-control" id="type" value="<%= awardType.getAwardType() %>" name="<%=FrmAwardType.fieldNames[FrmAwardType.FRM_FIELD_AWARD_TYPE]%>"></div>
            </div>
            <div class="form-group">
                <label class="col-sm-4">Description</label>
                <div class="col-md-7"><textarea rows="5" class="form-control" id="description" name="<%=FrmAwardType.fieldNames[FrmAwardType.FRM_FIELD_DESCRIPTION] %>"><%= awardType.getDescription() %></textarea></div>
            </div>
            
        <%
    }
}
    
%> 