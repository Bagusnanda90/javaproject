<%-- 
    Document   : competency_ajax
    Created on : 29-Jun-2016, 16:19:04
    Author     : Acer
--%>

<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="com.dimata.harisma.form.masterdata.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_EMPLOYEE_MENU, AppObjInfo.OBJ_EMPLOYEE_AND_FAMILY);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long employeeid = FRMQueryString.requestLong(request, "empId");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listcompetency")){
        String whereClause = "";
        String order = "";
        Vector listEmployeeCompetency = new Vector();

        CtrlEmployeeCompetency ctrlEmployeeCompetency = new CtrlEmployeeCompetency(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmployeeCompetency.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlEmployeeCompetency.actionList(iCommand, start, vectSize, recordToGet);
        }

            whereClause = PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            order = PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_COMPETENCY_ID];
            vectSize = PstEmployeeCompetency.getCount(whereClause);
            listEmployeeCompetency = PstEmployeeCompetency.list(0, 0, whereClause, order);        
        

        if (listEmployeeCompetency != null && listEmployeeCompetency.size()>0){
        %>
        <table id="listEmployeeCompetency" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Competencies</th>
                    <th>Level Value</th>
                    <th>Date of Achievement</th>
                    <th>Special Achievement</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listEmployeeCompetency.size(); i++) {
                    EmployeeCompetency employeeCompetency = (EmployeeCompetency)listEmployeeCompetency.get(i);
                    Competency competency = new Competency();
                    String comp = "";
                    try {
                        competency = PstCompetency.fetchExc(employeeCompetency.getCompetencyId());
                        comp = competency.getCompetencyName();
                        
                    } catch(Exception exc){
                        
                    }
                    
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= comp%></td>
                <td><%= employeeCompetency.getLevelValue() %></td>
                <td><%= employeeCompetency.getDateOfAchvmt() %></td>
                <td><%= employeeCompetency.getSpecialAchievement() %></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= employeeCompetency.getOID() %>" data-empId="<%= employeeCompetency.getEmployeeId() %>" class="addeditdatacomp btn btn-primary" data-command="<%= Command.NONE %>" data-for="showcompetencyform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= employeeCompetency.getOID() %>" data-for="showcompetencyform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedatacomp"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
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
    }else if(datafor.equals("showcompetencyform")){

        if(iCommand == Command.SAVE){
            CtrlEmployeeCompetency ctrlEmployeeCompetency = new CtrlEmployeeCompetency(request);
            ctrlEmployeeCompetency.action(iCommand, oid, request, emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            CtrlEmployeeCompetency ctrlEmployeeCompetency = new CtrlEmployeeCompetency(request);
            ctrlEmployeeCompetency.action(iCommand, oid, request, emplx.getFullName(), appUserIdSess);
        }else{
            EmployeeCompetency employeeCompetency = new EmployeeCompetency();
            if(oid != 0){
                try{
                    employeeCompetency = PstEmployeeCompetency.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <input type="hidden" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_ID] %>" value="<%=employeeid%>">
                <input type="hidden" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_HISTORY] %>" value="0">
                <input type="hidden" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_PROVIDER_ID] %>" value="0">
                <label class="col-sm-3">Competency</label>
                <div class="col-sm-9">
                    <select name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_COMPETENCY_ID] %>" class="form-control">
                        <option value="0">-SELECT-</option>
                        <%
                        Vector listCompetency = PstCompetency.list(0, 0, "", "");
                        if (listCompetency != null && listCompetency.size()>0){
                            for(int c=0; c<listCompetency.size(); c++){
                                Competency competency = (Competency)listCompetency.get(c);
                                if (employeeCompetency.getCompetencyId()==competency.getOID()){
                                    %>
                                    <option value="<%=competency.getOID()%>" selected="selected"><%=competency.getCompetencyName()%></option>
                                    <%
                                } else {
                                %>
                                    <option value="<%=competency.getOID()%>"><%=competency.getCompetencyName()%></option>
                                <%
                                }
                            }                                               
                        } 
                        %>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Level Value</label>
                <div class="col-sm-9">
                    <input type="text" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_LEVEL_VALUE] %>" value="<%= employeeCompetency.getLevelValue() %>"  class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Date of Achievement</label>
                <div class="col-sm-9">
                    <%
                    String DATE_FORMAT_NOW = "yyyy-mm-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat (DATE_FORMAT_NOW);
                    Date DateOA = employeeCompetency.getDateOfAchvmt() == null ? new Date() : employeeCompetency.getDateOfAchvmt();
                    String strDA = sdf.format(DateOA);
                    %>
                    <input type="text" id="datepicker1" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_DATE_OF_ACHVMT] %>" value="<%= strDA %>" class="form-control pull-right datepicker"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Special Achievement</label>
                <div class="col-sm-9">
                    <textarea name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_SPECIAL_ACHIEVEMENT] %>" class="form-control"><%= employeeCompetency.getSpecialAchievement() %></textarea>
                </div>
            </div>
            
        <%
    }
}
    
%>    
    