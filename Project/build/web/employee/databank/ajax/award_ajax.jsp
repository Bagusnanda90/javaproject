<%-- 
    Document   : award_ajax
    Created on : 01-Jul-2016, 10:07:34
    Author     : Acer
--%>


<%@page import="com.dimata.system.session.dataupload.SessDataUpload"%>
<%@page import="com.dimata.util.blob.ImageLoader"%>
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
    
    if(datafor.equals("listaward")){
        String whereClause = "";
        String order = "";
        Vector listEmpAward = new Vector();

        CtrlEmpAward ctrlEmpAward = new CtrlEmpAward(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmpAward.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlEmpAward.actionList(iCommand, start, vectSize, recordToGet);
        }

            whereClause = PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            order = PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_TYPE];
            vectSize = PstEmpAward.getCount(whereClause);
            listEmpAward = PstEmpAward.list(0, 0, whereClause, order);        
        

        if (listEmpAward != null && listEmpAward.size()>0){
        %>
        <table id="listaward" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Date</th>
                    <th>Title of Award</th>
                    <th>Division (Internal)</th>
                    <th>Award From (External)</th>
                    <th>Type</th>
                    <th>Description</th>
                    <th>Document</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listEmpAward.size(); i++) {
                    EmpAward empAward = (EmpAward)listEmpAward.get(i);
                    String external = "-";
                    String div = "-";
                    String Atype = "-";
                    ContactList contList = new ContactList();
                    Division division = new Division();
                    AwardType awardType = new AwardType();
                    try {
                        
                        awardType = PstAwardType.fetchExc(empAward.getAwardType());
                        Atype = awardType.getAwardType();
                        division = PstDivision.fetchExc(empAward.getDivisionId());
                        div = division.getDivision();
                        
                        
                        contList = PstContactList.fetchExc(empAward.getProviderId());
                        external = contList.getCompName();
                                
                    } catch(Exception e){
                        System.out.println("external=>"+e.toString());
                    }
                    
                    
                    
                    
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= empAward.getAwardDate()%></td>
                <td><%= empAward.getTitle() %></td>
                <td><%= div %></td>
                <td><%= external %></td>
                <td><%= Atype %></td>
                <td><%= empAward.getAwardDescription() %></td>
                <%  String document = "";
                    if(!(empAward.getDocument().equals(""))){
                        document = approot+"/imgdoc/"+  empAward.getDocument();
                    }
                %>
                <td><a href="<%=document%>" target="_blank"> <%=empAward.getDocument()%> </a></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empAward.getOID() %>" data-empId="<%= empAward.getEmployeeId() %>" class="addeditdataaward btn btn-primary" data-command="<%= Command.NONE %>" data-for="showawardform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empAward.getOID() %>" data-empId="<%= empAward.getEmployeeId() %>" data-for="showupload" data-command="<%= Command.UPDATE %>" class="btn btn-success uploaddata"  data-toggle="tooltip" data-placement="top" title="Upload"><i class="fa fa-upload"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= empAward.getOID() %>" data-for="showawardform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedataaward"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button> 
                                         
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
    }else if(datafor.equals("showawardform")){

        if(iCommand == Command.SAVE){
            CtrlEmpAward ctrlEmpAward = new CtrlEmpAward(request);
            ctrlEmpAward.action(iCommand, oid, request, emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            CtrlEmpAward ctrlEmpAward = new CtrlEmpAward(request);
            ctrlEmpAward.action(iCommand, oid, request, emplx.getFullName(), appUserIdSess);
        }else{
            EmpAward award = new EmpAward();
            if(oid != 0){
                try{
                    award = PstEmpAward.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="row">
            <div class="col-md-12">
            <input type="hidden" name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_EMPLOYEE_ID] %>" value="<%=employeeid%>">
            <div class="form-group">
                <label class="col-sm-3">Award Type</label>
                <div class="col-sm-9">
                    <select name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_TYPE]%>" class="form-control">
                    <% 
                        Vector lisTyp = PstAwardType.list(0, 0, "", PstAwardType.fieldNames[PstAwardType.FLD_AWARD_TYPE]);
                        for (int i = 0; i < lisTyp.size(); i++) {
                            AwardType typ = (AwardType) lisTyp.get(i);
                            if (award.getAwardType() == typ.getOID()){ %>
                            <option selected="selected" value="<%=typ.getOID()%>"><%=typ.getAwardType()%></option>
                            <% } else {%>
                            <option value="<%=typ.getOID()%>"><%=typ.getAwardType()%></option>
                            <% }
                            }
                    %> 
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Award Name</label>
                <div class="col-sm-9">
                <input type="text" name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_TITLE]%>" value="<%= award.getTitle() %>" class="form-control" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Award Date</label>
                <div class="col-sm-9 fa ">
                <%
                    String DATE_FORMAT_NOW = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                    Date repDate = award.getAwardDate()== null ? new Date() : award.getAwardDate();
                    String strRepDate = sdf.format(repDate);
                %>
                    <input type="text" name="<%= FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DATE]%>" value="<%= award.getAwardDate() %>" class="form-control pull-right datepicker" id="datepicker">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-9">
                <input type="radio" name="rb" value="0" id="internal" /> <strong>Internal</strong>
                </div>
                </div>
            <div class="form-group">
                <label class="col-sm-3">Company</label>
                <div class="col-sm-9">
                    <select class="form-control internal"  name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_COMPANY_ID]%>" id="company" disabled="">
                    <option value="">-select-</option>
                    <%
                    Vector listCompany = PstCompany.list(0, 0, "", PstCompany.fieldNames[PstCompany.FLD_COMPANY]);
                    if (listCompany != null && listCompany.size()>0){
                        for(int i=0; i<listCompany.size(); i++){
                            Company comp = (Company)listCompany.get(i);
                            if (award.getCompanyId() == comp.getOID()){
                                %>
                                <option selected="selected" value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                                <%
                            } else  {
                                %>
                                <option value="<%=comp.getOID()%>"><%= comp.getCompany() %></option>
                                <%
                            }
                        }
                    }
                    %>
                    </select>   
                </div>    
            </div>
            <div class="form-group">
                <label class="col-sm-3">Division</label>
                <div class="col-sm-9">
                <select class="form-control internal" name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_DIVISION_ID]%>" id="division" disabled>
                        <option value="">-select-</option>
                        <%
                            Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                            if (listDivision != null && listDivision.size()>0){
                                for(int i=0; i<listDivision.size(); i++){
                                    Division divisi = (Division)listDivision.get(i);
                                    if (award.getDivisionId() == divisi.getOID()){
                                        %><option selected="selected" value="<%=divisi.getOID()%>" class="<%=divisi.getCompanyId()%>"><%=divisi.getDivision()%></option><%
                                    }  else {
                                        %><option value="<%=divisi.getOID()%>" class="<%=divisi.getCompanyId()%>"><%=divisi.getDivision()%></option><%
                                    }
                                }
                            }

                        %>
                        </select>
                </div>    
            </div>
            <div class="form-group">
                <div class="col-sm-9">
                <input type="radio" name="rb" value="0" id="external" /> <strong>External</strong>
                </div>
                </div>
            <div class="form-group">
                <label class="col-sm-3">Award From</label>
                <div class="col-sm-9">
                    <select id="awardfrom" name="<%=FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_PROVIDER_ID]%>" class="form-control external" disabled>
                    <option value="0">select...</option>
                    <%
                        Vector listContact = PstContactList.list(0, 0, "", "");
                        if (listContact != null && listContact.size()>0){
                            for(int i=0; i<listContact.size(); i++){
                                ContactList contact = (ContactList)listContact.get(i);
                                    award.getProviderId();
                                    if (award.getProviderId()==contact.getOID()){
                                        %>
                                        <option selected="selected" value="<%=contact.getOID()%>"><%=contact.getCompName()%></option>
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
            <div class="form-group">
                <label class="col-sm-3">Description</label>
                <div class="col-sm-9">
                    <textarea name="<%= FrmEmpAward.fieldNames[FrmEmpAward.FRM_FIELD_AWARD_DESC]%>" class="form-control" rows="3"><%= award.getAwardDescription() %></textarea>
                </div>
            </div>
            </div>
            </div>
            
            
        <%
    }
} else if (datafor.equals("showupload")){
 
 
 
 %>
 <input required style="width:0px; height:0px;" id="fileupload" name ="fileupload" type="file">
 <div class="input-group my-colorpicker2 colorpicker-element">
        <input required id="tempname" name="tempname" class="form-control" type="text">
        <div style="cursor: pointer" class="input-group-addon" id="uploadtrigger">
            <i class="fa fa-file-pdf-o"></i>
        </div>
    </div>
 <%
}
    
%>        