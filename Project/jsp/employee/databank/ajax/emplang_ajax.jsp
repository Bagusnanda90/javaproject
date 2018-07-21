<%-- 
    Document   : emplang_ajax
    Created on : 29-Jun-2016, 17:33:53
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
    
    if(datafor.equals("listlanguage")){
        String whereClause = "";
        String order = "";
        Vector listEmployeeLanguage = new Vector();

        CtrlEmpLanguage ctrlEmpLanguage = new CtrlEmpLanguage(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmpLanguage.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlEmpLanguage.actionList(iCommand, start, vectSize, recordToGet);
        }

            whereClause = PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            order = PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID];
            vectSize = PstEmpLanguage.getCount(whereClause);
            listEmployeeLanguage = PstEmpLanguage.list(0, 0, whereClause, order);        
        

        if (listEmployeeLanguage != null && listEmployeeLanguage.size()>0){
        %>
        <table id="listLanguage" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Language</th>
                    <th>Oral</th>
                    <th>Written</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listEmployeeLanguage.size(); i++) {
                    EmpLanguage empLanguage = (EmpLanguage)listEmployeeLanguage.get(i);
                    Language language = new Language();
                    String lang = "";
                    try {
                        language = PstLanguage.fetchExc(empLanguage.getLanguageId());
                        lang = language.getLanguage();
                        
                    } catch(Exception exc){
                        
                    }
                    
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= lang%></td>
                <td><%= PstEmpLanguage.gradeKey[empLanguage.getOral()] %></td>
                <td><%= PstEmpLanguage.gradeKey[empLanguage.getWritten()] %></td>
                <td><%= empLanguage.getDescription() %></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empLanguage.getOID() %>" data-empId="<%= empLanguage.getEmployeeId() %>" class="addeditdatalang btn btn-primary" data-command="<%= Command.NONE %>" data-for="showlanguageform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empLanguage.getOID() %>" data-for="showlanguageform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedatalang"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
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
    }else if(datafor.equals("showlanguageform")){

        if(iCommand == Command.SAVE){
            CtrlEmpLanguage ctrlEmpLanguage = new CtrlEmpLanguage(request);
            ctrlEmpLanguage.action(iCommand, oid, employeeid, request, emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            CtrlEmpLanguage ctrlEmpLanguage = new CtrlEmpLanguage(request);
            ctrlEmpLanguage.action(iCommand, oid, employeeid, request, emplx.getFullName(), appUserIdSess);
        }else{
            EmpLanguage empLanguage = new EmpLanguage();
            if(oid != 0){
                try{
                    empLanguage = PstEmpLanguage.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label class="col-sm-3">Language</label>
                <div class="col-sm-9">
                    <select name="<%= FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID] %>" class="form-control">
                        <option value="0">-SELECT-</option>
                        <%
                        Vector listLanguage = PstLanguage.listAll();
                        for (int i = 0; i < listLanguage.size(); i++) {
                                Language language = (Language) listLanguage.get(i);
                                if (empLanguage.getLanguageId()==language.getOID()){
                                    %>
                                    <option value="<%=language.getOID()%>" selected="selected"><%=language.getLanguage()%></option>
                                    <%
                                } else {
                                %>
                                    <option value="<%=language.getOID()%>"><%=language.getLanguage()%></option>
                                <%
                                }
                            }                                               
                        %>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Oral</label>
                <div class="col-sm-9">
                    <select name="<%= FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_ORAL] %>" class="form-control">
                        <%
                        for (int i = 0; i < PstEmpLanguage.gradeKey.length; i++) {

                                if (empLanguage.getOral()== i){
                                    %>
                                    <option value="<%=i%>" selected="selected"><%=PstEmpLanguage.gradeKey[i]%></option>
                                    <%
                                } else {
                                %>
                                    <option value="<%=i%>"><%=PstEmpLanguage.gradeKey[i]%></option>
                                <%
                                }
                            }                                               
                        %>
                    </select>
                </div>    
            </div>
            <div class="form-group">
                <label class="col-sm-3">Written</label>
                <div class="col-sm-9">
                    <select name="<%= FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_WRITTEN] %>" class="form-control">
                        <%
                        for (int i = 0; i < PstEmpLanguage.gradeKey.length; i++) {

                                if (empLanguage.getWritten()==i){
                                    %>
                                    <option value="<%=i%>" selected="selected"><%=PstEmpLanguage.gradeKey[i]%></option>
                                    <%
                                } else {
                                %>
                                    <option value="<%=i%>"><%=PstEmpLanguage.gradeKey[i]%></option>
                                <%
                                }
                            }                                               
                        %>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Special Achievement</label>
                <div class="col-sm-9">
                    <textarea name="<%=FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_DESCRIPTION]%>" class="form-control" rows="2"><%= empLanguage.getDescription() %></textarea>
                </div>
            </div>
            
        <%
    }
}
    
%>    
    