<%-- 
    Document   : PayrollGroup_ajax
    Created on : 28-Jun-2016, 15:57:16
    Author     : Acer
--%>


<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPayrollGroup"%>
<%@page import="com.dimata.harisma.entity.masterdata.PayrollGroup"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPayrollGroup"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPayrollGroup"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_WARNING);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String payGroupName = FRMQueryString.requestString(request, "payGroup_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listpaygroup")){
        String whereClause = "";
        String order = "";
        Vector listPayrollGroup = new Vector();

        CtrlPayrollGroup ctrlPayrollGroup = new CtrlPayrollGroup(request);
        
        int recordToGet = 10;
        int vectSize = 0;
        vectSize = PstPayrollGroup.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlPayrollGroup.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(payGroupName.equals(""))){
            whereClause = PstPayrollGroup.fieldNames[PstPayrollGroup.FLD_PAYROLL_GROUP_NAME]+" LIKE '%"+payGroupName+"%'";
            order = PstPayrollGroup.fieldNames[PstPayrollGroup.FLD_PAYROLL_GROUP_NAME];
            vectSize = PstPayrollGroup.getCount(whereClause);
            listPayrollGroup = PstPayrollGroup.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstPayrollGroup.getCount("");
            order = PstPayrollGroup.fieldNames[PstPayrollGroup.FLD_PAYROLL_GROUP_NAME];
            listPayrollGroup = PstPayrollGroup.list(start, recordToGet, "", order);
        }

        if (listPayrollGroup != null && listPayrollGroup.size()>0){
        %>
        <table id="listWarningReprimandAyat" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Payroll Group</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listPayrollGroup.size(); i++) {
                    PayrollGroup payrollGroup = (PayrollGroup)listPayrollGroup.get(i);
                    
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= payrollGroup.getPayrollGroupName()%></td>
                <td><%= payrollGroup.getDescription()%></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= payrollGroup.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showpaygroupform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= payrollGroup.getOID() %>" data-for="showpaygroupform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
                    </td>
                </tr>
                <%
            }
            %>
        </table>
            <div>&nbsp;</div>
            <div id="record_count">
                <%
                if (vectSize >= recordToGet){
                    %>
                    List : <%=start%> &HorizontalLine; <%= (start+recordToGet) %> | 
                    <%
                }
                %>
                Total : <%= vectSize %>
            </div>
            <div class="pagging">
                <a style="color:#000000" href=javascript:" data-start="<%= start %>" class="btn-small">First |</a>
                <a style="color:#000000" href="javascript:cmdListPrev('<%=start%>')" class="btn-small">Previous |</a>
                <a style="color:#000000" href="javascript:cmdListNext('<%=start%>')" class="btn-small">Next |</a>
                <a style="color:#000000" href="javascript:cmdListLast('<%=start%>')" class="btn-small">Last</a>
            </div>
        <%
        } else { %>
            <a> <h4>No Record </h4></a>
        <% }
    }else if(datafor.equals("showpaygroupform")){

        if(iCommand == Command.SAVE){
            CtrlPayrollGroup ctrlPayrollGroup = new CtrlPayrollGroup(request);
            ctrlPayrollGroup.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlPayrollGroup ctrlPayrollGroup = new CtrlPayrollGroup(request);
            ctrlPayrollGroup.action(iCommand, oid);
        }else{
            PayrollGroup payrollGroup = new PayrollGroup();
            if(oid != 0){
                try{
                    payrollGroup = PstPayrollGroup.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label>Payroll Group Name :</label>
                <input type="text" name="<%=FrmPayrollGroup.fieldNames[FrmPayrollGroup.FRM_FIELD_PAYROLL_GROUP_NAME]%>" value="<%=payrollGroup.getPayrollGroupName()%>"  class="form-control" placeholder="Type Payroll Group Name..." >
            </div>
            <div class="form-group">
                <label>Description :</label>
                <textarea name="<%= FrmPayrollGroup.fieldNames[FrmPayrollGroup.FRM_FIELD_DESCRIPTION]%>" class="form-control"><%=payrollGroup.getDescription()%></textarea>
            </div>    
            
        <%
    }
}
    
%>    