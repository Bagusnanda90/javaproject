<%-- 
    Document   : empeducation_ajax.jsp
    Created on : 29-Jun-2016, 18:20:30
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
    
        CtrlEmpEducation ctrlEmpEducation = new CtrlEmpEducation(request);
        FrmEmpEducation frmEmpEducation = ctrlEmpEducation.getForm();
    
    if(datafor.equals("listeducation")){
        String whereClause = "";
        String order = "";
        Vector listEmpEducation = new Vector();
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmpEducation.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlEmpEducation.actionList(iCommand, start, vectSize, recordToGet);
        }

            whereClause = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            order = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID];
            vectSize = PstEmpEducation.getCount(whereClause);
            listEmpEducation = PstEmpEducation.list(0, 0, whereClause, order);        
        

        if (listEmpEducation != null && listEmpEducation.size()>0){
            
        %>
        <table id="listEmpEducation" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Education</th>
                    <th>University / Institution</th>
                    <th>Detail</th>
                    <th>Start Years</th>
                    <th>End Year</th>
                    <th>Point</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listEmpEducation.size(); i++) {
                    EmpEducation empEducation = (EmpEducation)listEmpEducation.get(i);
                    Education education = new Education();
                    ContactList conList = new ContactList();
                    String edu = "";
                    String contact = "";
                    try {
                        education = PstEducation.fetchExc(empEducation.getEducationId());
                        edu = education.getEducation();
                        conList = PstContactList.fetchExc(empEducation.getInstitutionId());
                        contact = conList.getCompName();
                    } catch(Exception exc){
                        
                    }
                    
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= edu%></td>
                <td><%= contact %></td>
                <td><%= empEducation.getGraduation() %></td>
                <td><%= empEducation.getStartDate()%></td>
                <td><%= empEducation.getEndDate()%></td>
                <td><%= empEducation.getPoint()%></td>
                <td><%= empEducation.getEducationDesc()%></td>
                    <td>
                        <a href="javascript:" data-oid="<%= empEducation.getOID() %>" data-empId="<%= empEducation.getEmployeeId() %>" data-for="showeducationform" class="addeditdataedu" data-command="<%= Command.NONE %>"><i class="fa fa-pencil"></i> Edit </a> 
                        |
                        <a href="javascript:" data-oid="<%= empEducation.getOID() %>" data-for="showeducationform" class="deletedataedu" data-command="<%= Command.DELETE %>"><i class="fa fa-times"></i> Delete</a>

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
    }else if(datafor.equals("showeducationform")){

        if(iCommand == Command.SAVE){
            //CtrlEmpEducation ctrlEmpEducation = new CtrlEmpEducation(request);
            ctrlEmpEducation.action(iCommand, oid, employeeid, request, emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            //CtrlEmpEducation ctrlEmpEducation = new CtrlEmpEducation(request);
            ctrlEmpEducation.action(iCommand, oid, employeeid, request, emplx.getFullName(), appUserIdSess);
        }else{
            EmpEducation empEducation = new EmpEducation();
            if(oid != 0){
                try{
                    empEducation = PstEmpEducation.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            
            %>
            <label> <p class="text-danger">* } required field</p></label>
            <input type="hidden" name="<%=FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMPLOYEE_ID] %>" value="<%=employeeid%>">
            <div class="form-group">
                <label class="col-sm-3">Education *</label> 
                <div class="col-sm-9">
                    <select name="<%= FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_ID] %>" class="form-control">
                        <option value="0">-SELECT-</option>
                        <%
                        Vector listEducation = PstEducation.listAll();
                        for (int i = 0; i < listEducation.size(); i++) {
                                Education education = (Education) listEducation.get(i);
                                if (empEducation.getEducationId()==education.getOID()){
                                    %>
                                    <option value="<%=education.getOID()%>" selected="selected"><%=education.getEducation()%></option>
                                    <%
                                } else {
                                %>
                                    <option value="<%=education.getOID()%>"><%=education.getEducation()%></option>
                                <%
                                }
                            }                                               
                        %>
                    </select>
                </div>
            </div>
            <div class="form-group">        
            <label class="col-sm-3">Start Year *</label>
            <div class="col-sm-9"><td width="85%"> <%=ControlDate.drawDateYear(frmEmpEducation.fieldNames[frmEmpEducation.FRM_FIELD_START_DATE], empEducation.getStartDate(),"formElemen",-45,0) %>
                to <%=	ControlDate.drawDateYear(frmEmpEducation.fieldNames[frmEmpEducation.FRM_FIELD_END_DATE], empEducation.getEndDate(),"formElemen",-45,0) %> 
                
                <%String strStart = frmEmpEducation.getErrorMsg(frmEmpEducation.FRM_FIELD_START_DATE);
                String strEnd = frmEmpEducation.getErrorMsg(frmEmpEducation.FRM_FIELD_END_DATE);
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
                <label class="col-sm-3">University / Institution</label>
                    <div class="col-sm-9">
                        <select class="form-control" name="<%=FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_INSTITUTION_ID] %>">
                        <option value="0">-SELECT-</option>
                        <%
                        /**
                        * Update by Hendra Putu | 2015-11-18
                        */
                        // select contact_class where contact type = 13 [Institution] . result get contact class id
                        String whereCClass = PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"=13";
                        Vector listCClass = PstContactClass.list(0, 0, whereCClass, "");
                        long cClassId = 0;
                        if (listCClass != null && listCClass.size()>0){
                            ContactClass cclass = (ContactClass)listCClass.get(0);
                            cClassId = cclass.getOID();
                        }
                        String inContactId = "";
                        if (cClassId != 0){
                            String whereCAssign = PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CNT_CLS_ID] +"="+cClassId;
                            Vector listCAssign = PstContactClassAssign.list(0, 0, whereCAssign, "");
                            if (listCAssign != null && listCAssign.size()>0){
                                for(int a=0; a<listCAssign.size(); a++){
                                    ContactClassAssign cA = (ContactClassAssign)listCAssign.get(a);
                                    inContactId += cA.getContactId()+",";
                                }
                                inContactId += "0";
                            }
                        }


                        String whereContact = PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]+" IN("+inContactId+")";
                        Vector listContact = PstContactList.list(0, 0, whereContact, "");
                        if (listContact != null && listContact.size()>0){
                            for(int i=0; i<listContact.size(); i++){
                                ContactList contact = (ContactList)listContact.get(i);
                                if (empEducation.getInstitutionId()==contact.getOID()){
                                    %>
                                    <option selected="selected" value="<%=contact.getOID()%>"><%=contact.getCompName()%></option>
                                    <%
                                } else {
                                    %>
                                    <option value="<%=contact.getOID()%>"><%=contact.getCompName()%></option>
                                    <%
                                }
                                %>

                                <%
                            }
                        }
                        %>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Detail</label>
                <div class="col-sm-9">
                    <input type="text" name="<%=FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_GRADUATION] %>"  value="<%= empEducation.getGraduation() %>" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Point</label>
                <div class="col-sm-9">
                    <input type="text" name="<%=FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_POINT] %>"  value="<%= empEducation.getPoint()%>" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Description</label>
                <div class="col-sm-9">
                    <textarea name="<%=FrmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_DESC]%>" class="form-control" rows="2"><%= empEducation.getEducationDesc()%></textarea>
                </div>
            </div>
                
        <%
    }
}
    
%>    
    