<%-- 
    Document   : experience_ajax
    Created on : 30-Jun-2016, 20:27:40
    Author     : Acer
--%>

<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@page import="com.dimata.common.entity.contact.ContactClassAssign"%>
<%@page import="com.dimata.common.entity.contact.PstContactClassAssign"%>
<%@page import="com.dimata.common.entity.contact.ContactClass"%>
<%@page import="com.dimata.common.entity.contact.PstContactClass"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>                                
<%@page import="com.dimata.common.entity.contact.ContactList"%>
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
    
    
    CtrlExperience ctrlExperience = new CtrlExperience(request);
    FrmExperience frmExperience = ctrlExperience.getForm();
    
    
    if(datafor.equals("listexperience")){
        String whereClause = "";
        String order = "";
        Vector listEmpExperience = new Vector();
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstExperience.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlExperience.actionList(iCommand, start, vectSize, recordToGet);
        }

            whereClause = PstExperience.fieldNames[PstExperience.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            order = PstExperience.fieldNames[PstExperience.FLD_COMPANY_NAME];
            vectSize = PstExperience.getCount(whereClause);
            listEmpExperience = PstExperience.list(0, 0, whereClause, order);        
        

        if (listEmpExperience != null && listEmpExperience.size()>0){
        %>
        <table id="listex" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Company Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Position</th>
                    <th>Move Reason</th>
                    <th>Provider</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listEmpExperience.size(); i++) {
                    Experience experience = (Experience)listEmpExperience.get(i);
                    String provider = "";
                    try {
                        ContactList contList = PstContactList.fetchExc(experience.getProviderID());
                        provider = contList.getCompName();
                    } catch(Exception e){
                        System.out.println("provider=>"+e.toString());
                    }

                    
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= experience.getCompanyName()%></td>
                <td><%= String.valueOf(experience.getStartDate()) %></td>
                <td><%= String.valueOf(experience.getEndDate()) %></td>
                <td><%= experience.getPosition()%></td>
                <td><%= experience.getMoveReason()%></td>
                <td><%= provider%></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= experience.getOID() %>" data-empId="<%= experience.getEmployeeId() %>" class="addeditdataexp btn btn-primary" data-command="<%= Command.NONE %>" data-for="showexpform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= experience.getOID() %>" data-for="showexpform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedataexp"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
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
    }else if(datafor.equals("showexpform")){

        if(iCommand == Command.SAVE){
            ctrlExperience.action(iCommand, oid, employeeid, request, emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            ctrlExperience.action(iCommand, oid, employeeid, request, emplx.getFullName(), appUserIdSess);
        }else{
            Experience experience = new Experience();
            if(oid != 0){
                try{
                    experience = PstExperience.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <input type="hidden" name="<%=FrmExperience.fieldNames[FrmExperience.FRM_FIELD_EMPLOYEE_ID] %>" value="<%=employeeid%>">
            <div class="form-group">
                <label class="col-sm-3">Company Name</label>
                <div class="col-sm-9">
                    <input type="text" name="<%=FrmExperience.fieldNames[FrmExperience.FRM_FIELD_COMPANY_NAME] %>"  value="<%= experience.getCompanyName() %>" class="form-control">
                </div>
            </div>
            
            <div class="form-group">        
            <label class="col-sm-3">Start Year *</label>
            <div class="col-sm-9"><td width="85%"> <%=ControlDate.drawDateYear(frmExperience.fieldNames[FrmExperience.FRM_FIELD_START_DATE], experience.getStartDate(),"formElemen",-45,0) %>
                to <%=	ControlDate.drawDateYear(frmExperience.fieldNames[FrmExperience.FRM_FIELD_END_DATE], experience.getEndDate(),"formElemen",-45,0) %> 
                
                <%String strStart = frmExperience.getErrorMsg(frmExperience.FRM_FIELD_START_DATE);
                 String strEnd = frmExperience.getErrorMsg(frmExperience.FRM_FIELD_END_DATE);
                 System.out.println("strStart "+strStart);
                 System.out.println("strEnd "+strEnd);
                 if((strStart.length()>0)&&(strEnd.length()>0)){
                %>
                <%= strStart %> 
                <%}else{
                        if((strStart.length()>0)||(strEnd.length()>0)){%>
                <%= strStart.length()>0?strStart:strEnd %> 
                <%}
                }%>
                </td></div>
            </div>    
                
            <div class="form-group">
                <label class="col-sm-3">Position</label>
                <div class="col-sm-9">
                    <input type="text" name="<%=FrmExperience.fieldNames[FrmExperience.FRM_FIELD_POSITION] %>"  value="<%= experience.getPosition() %>" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Move Reason</label>
                <div class="col-sm-9">
                    <textarea name="<%=FrmExperience.fieldNames[FrmExperience.FRM_FIELD_MOVE_REASON] %>" class="form-control" rows="3"><%= experience.getMoveReason() %></textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Provider</label>
                <div class="col-sm-9">
                    <select name="<%=FrmExperience.fieldNames[FrmExperience.FRM_FIELD_PROVIDER_ID] %>" class="form-control">
                        <option value="0">-SELECT-</option>
                        <%
                        Vector listProvider = PstContactList.list(0, 0, "", "");
                        if (listProvider != null && listProvider.size()>0){
                            for (int i=0; i<listProvider.size(); i++){
                                ContactList contact = (ContactList)listProvider.get(i);
                                if(experience.getProviderID() == contact.getOID()){
                                %>
                                <option  selected="selected" value="<%=contact.getOID()%>"><%=contact.getCompName()%></option>
                                <%
                            } else {
                                %>
                                <option value="<%=contact.getOID()%>"><%=contact.getCompName()%></option>
                                <%
                            }
                        }
                    }
                        %>
                    </select>
                </div>
            </div>
            
            
        <%
    }
}
    
%>    
    