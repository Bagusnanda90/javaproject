<%-- 
    Document   : family_ajax
    Created on : 29-Jun-2016, 14:27:12
    Author     : Acer
--%>


<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.ParseException"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_EMPLOYEE_MENU, AppObjInfo.OBJ_EMPLOYEE_AND_FAMILY);%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long employeeid = FRMQueryString.requestLong(request, "empId");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("listfamily")){
        String whereClause = "";
        String order = "";
        Vector listfam = new Vector();

        CtrlFamilyMember ctrlFamilyMember = new CtrlFamilyMember(request);
        int index = -1;
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmpReprimand.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlFamilyMember.actionList(iCommand, start, vectSize, recordToGet);
        }
            
            whereClause = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            vectSize = PstFamilyMember.getCount("");
            order = PstFamilyMember.fieldNames[PstFamilyMember.FLD_FULL_NAME];
            listfam = PstFamilyMember.list(start, recordToGet, whereClause, order);
        
        if (listfam != null && listfam.size()>0){
%>
        <table id="listfam" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Full Name / Sex</th>
                    <th>Relationship</th>
                    <th>Guaranted</th>
                    <th>Date of Birth</th>
                    <th>Job / Address</th>
                    <th>ID KTP</th>
                    <th>Education / Religion</th>
                    <th>Phone Number</th>
                    <th>BPJS Number</th>
                    <th>Action</th>
                </tr>
            </thead>
            <% 
            for (int i = 0; i <listfam.size(); i++){
                FamilyMember fam = (FamilyMember) listfam.get(i);
                String relation = "";
                FamRelation famRelation = new FamRelation();
                try {
                    famRelation = PstFamRelation.fetchExc(Long.valueOf(fam.getRelationship()));
                    relation = famRelation.getFamRelation();
                } catch (Exception e){
                    
                }
                
                
            %>
            <tr>
                <td><%=(i + 1)%></td>
                <td><%=fam.getFullName() + " / "+ PstEmployee.sexKey[fam.getSex()]%></td>
                <td><%=relation%></td>
                <td><%=fam.getGuaranteed()==true ? "Yes" : "No"%></td>
                <td><%=fam.getBirthDate()%></td>
                <td><%=fam.getJob() +" / " + fam.getAddress()%></td>
                <td><%=fam.getCardId()%></td>
                <td><%=fam.getEducationId() + " / " + fam.getReligionId()%></td>
                <td><%=fam.getNoTelp()%></td>
                <td><%=fam.getBpjsNum()%></td>
                <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= fam.getOID() %>" data-empId="<%= fam.getEmployeeId() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showfamform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= fam.getOID() %>" data-for="showfamform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
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
} else if(datafor.equals("showfamform")){

        if(iCommand == Command.SAVE){
            CtrlFamilyMember ctrlFamilyMember = new CtrlFamilyMember(request);
            ctrlFamilyMember.action(iCommand, employeeid,oid,request,emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            CtrlFamilyMember ctrlFamilyMember = new CtrlFamilyMember(request);
            ctrlFamilyMember.action(iCommand, employeeid,oid,request,emplx.getFullName(), appUserIdSess);
        }else{
            FamilyMember fam = new FamilyMember();
            if(oid != 0){
                try{
                    fam = PstFamilyMember.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }%>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-sm-3">Full Name</label>
                        <div class="col-sm-9">
                            <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME]%>"  value="<%= fam.getFullName()%>" class="form-control">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-sm-3">Sex</label>
                        <div class="col-sm-9">
                            <% for (int i = 0; i < PstEmployee.sexValue.length; i++) {
                            String str = "";
                            if (fam.getSex() == PstEmployee.sexValue[i]) {
                                str = "checked";
                            }
                            %> <input type="radio" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_SEX]%>" value="<%="" + PstEmployee.sexValue[i]%>" <%=str%> >
                            <%=PstEmployee.sexKey[i]%> 
                      <% }%>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-sm-3">Relationship</label>  
                        <div class="col-sm-9">
                            <select name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELATIONSHIP]%>" class="form-control">
                                <%
                                   Vector listRelationX =PstFamRelation.listAll();
                                   for (int i = 0; i < listRelationX.size(); i++) {
                                        FamRelation famRelation =(FamRelation) listRelationX.get(i);
                                        //relation4_value.add(""+famRelation.getFamRelationType());
                                        if (fam.getRelationType()== famRelation.getOID()){
                                         %><option selected="selected" value="<%=famRelation.getOID()%>"><%= famRelation.getFamRelation()%></option> <%
                                        } else {
                                        %><option value="<%=famRelation.getOID()%>"><%=famRelation.getFamRelation()%></option><%
                                        }
                                        
                                    } 
                                %>
                            </select>
                        </div>
                    </div>
                            
                    <div class="form-group">
                        <label class="col-sm-3">Guaranteed</label>
                        <div class="col-sm-9">
                            <input type="checkbox" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_GUARANTEED]%>" value="1" <%if (fam.getGuaranteed()) {%>checked<%}%>> Yes
                        </div>
                    </div>
                        
                    <div class="form-group">
                        <label class="col-sm-3">Date of Birth</label>
                        <div class="col-sm-9">
                            <%
                            String DATE_FORMAT_NOW = "yyyy-MM-dd";
                            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                            Date birth = fam.getBirthDate()== null ? new Date() : fam.getBirthDate();
                            String strbirtdate = sdf.format(birth);
                            %>
                            <input type="text" id="datepicker" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BIRTH_DATE]%>" class="form-control pull-right datepicker" value="<%=strbirtdate %>">
                        </div>
                    </div>
                        
                    <div class="form-group">
                        <label class="col-sm-3">Job</label>
                        <div class="col-sm-9">
                            <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB]%>"  value="<%= fam.getJob()%>" class="form-control">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-sm-3">Job Address</label>
                        <div class="col-sm-9">
                            <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_ADDRESS]%>"  value="<%= fam.getAddress()%>" class="form-control">
                        </div>
                    </div>   
                        
                    <div class="form-group">
                        <label class="col-sm-3">ID Card Number</label>
                        <div class="col-sm-9">
                            <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_CARD_ID]%>" value="<%= fam.getCardId() %>" class="form-control"/>
                        </div>
                    </div>
                        
                    <div class="form-group">
                        <label class="col-sm-3">Education</label>
                        <div class="col-sm-9">
                            <select name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_EDUCATION_ID]%>" class="form-control">
                            <%          
                                Vector listEducation = PstEducation.listAll();
                                for(int i=0;i<listEducation.size();i++){
                                    Education education = (Education) listEducation.get(i);
                                    if (fam.getEducationId() == education.getOID()){
                                        %><option selected="selected" value="<%=education.getOID()%>"><%=education.getEducation()%></option><%
                                    } else {
                                        %><option value="<%=education.getOID()%>"><%=education.getEducation()%></option><%
                                    }

                                }
                            %>
                            </select>
                        </div>
                    </div>
                            
                    <div class="form-group">
                        <label class="col-sm-3">Religion</label>
                        <div class="col-sm-9">
                            <select name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELIGION_ID]%>" class="form-control">
                            <% 
                                Vector listReligion = PstReligion.listAll();
                                for (int i = 0; i < listReligion.size(); i++) {
                                    Religion religion = (Religion) listReligion.get(i);
                                    if (fam.getReligionId() == religion.getOID()){
                                        %><option selected="selected" value="<%=religion.getOID()%>"><%=religion.getReligion()%></option><%
                                    } else {
                                        %><option value="<%=religion.getOID()%>"><%=religion.getReligion()%></option><%
                                    }
                                }
                            %>
                            </select>
                        </div>
                    </div>
                            
                    <div class="form-group">
                        <label class="col-sm-3">Telephone Number</label>
                        <div class="col-sm-9">
                            <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_NO_TELP]%>"  value="<%= fam.getNoTelp() %>" class="form-control">
                        </div>
                    </div>
                        
                        <div class="form-group">
                            <label class="col-sm-3">BPJS Number</label>
                            <div class="col-sm-9">
                               <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BPJS_NUM]%>"  value="<%= fam.getBpjsNum() %>" class="form-control"> 
                            </div>
                        </div>    
                </div>
            </div>
            <%
    }
}
    
%>  
                