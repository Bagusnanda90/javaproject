<%-- 
    Document   : company_ajax
    Created on : 27-Jun-2016, 14:07:32
    Author     : Acer
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmCompany"%>
<%@page import="com.dimata.harisma.entity.masterdata.Company"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompany"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCompany"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_COMMPANY);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String compName = FRMQueryString.requestString(request, "company_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listcompany")){
        String whereClause = "";
        String order = "";
        Vector listCompany = new Vector();

        CtrlCompany ctrlCompany = new CtrlCompany(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstCompany.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlCompany.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(compName.equals(""))){
            whereClause = PstCompany.fieldNames[PstCompany.FLD_COMPANY]+" LIKE '%"+compName+"%'";
            order = PstCompany.fieldNames[PstCompany.FLD_COMPANY];
            vectSize = PstCompany.getCount(whereClause);
            listCompany = PstCompany.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstCompany.getCount("");
            order = PstCompany.fieldNames[PstCompany.FLD_COMPANY];
            listCompany = PstCompany.list(start, recordToGet, "", order);
        }

        if (listCompany != null && listCompany.size()>0){
        %>
        <table id="listCompany" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Company</th>
                    <th>Description</th>
                    <th>Company Parent</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listCompany.size(); i++) {
                Company company = (Company) listCompany.get(i);

                %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=company.getCompany()%></td>
                    <td><%=company.getDescription()%></td>
                    <td><%=PstCompany.getCompanyName(company.getCompanyParentsId())%></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= company.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showcompanyform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= company.getOID() %>" data-for="showcompanyform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
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
    }else if(datafor.equals("showcompanyform")){

        if(iCommand == Command.SAVE){
            CtrlCompany ctrlCompany = new CtrlCompany(request);
            ctrlCompany.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlCompany ctrlCompany = new CtrlCompany(request);
            ctrlCompany.action(iCommand, oid);
        }else{
            Company company = new Company();
            if(oid != 0){
                try{
                    company = PstCompany.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="<%= FrmCompany.fieldNames[FrmCompany.FRM_FIELD_COMPANY] %>">Company :</label>
                <input type="text" class="form-control" id="company" value="<%= company.getCompany() %>" name="<%= FrmCompany.fieldNames[FrmCompany.FRM_FIELD_COMPANY] %>">
            </div>
            <div class="form-group">
                <label for="Company">Company Temporary Address :</label>
                <textarea rows="5" class="form-control" id="cmp_address" name="<%=FrmCompany.fieldNames[FrmCompany.FRM_FIELD_DESCRIPTION]%>"><%= company.getDescription()%></textarea>
            </div>
            <div class="form-group">
                <label for="Company">Company Parents :</label>
                <select rows="5" class="form-control" id="cmp_address" name="<%=FrmCompany.fieldNames[FrmCompany.FRM_FIELD_COMPANY_PARENTS_ID]%>">
                    <option value="0">-Select-</option>
                    
                    <%
                   
                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                    for (int i = 0; i < listComp.size(); i++) {
                        Company div = (Company) listComp.get(i);
                        if(company.getCompanyParentsId()==div.getOID()){
                        %>
                        
                        <option selected="selected" value="<%=String.valueOf(div.getOID())%>"><%=div.getCompany()%></option>
                        <%} else{%>
                        <option value="<%=String.valueOf(div.getOID())%>"><%=div.getCompany()%></option>
                       <% }
                       }%>
                </select>
            </div>
        <%
    }
}
    
%>    