<%-- 
    Document   : relDoc
    Created on : Aug 12, 2016, 2:10:14 PM
    Author     : ARYS
--%>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmpRelevantDoc"%>
<%@page import="com.dimata.harisma.entity.employee.EmpRelevantDoc"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmpRelevantDoc"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.employee.CtrlEmpRelevantDoc"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_COMMPANY);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long employeeid = FRMQueryString.requestLong(request, "empId");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    
    
    if(datafor.equals("listreldoc")){
        String whereClause = "";
        String order = "";
        Vector listRelDoc = new Vector();
        
        CtrlEmpRelevantDoc ctrlEmpRelevantDoc = new CtrlEmpRelevantDoc(request);
        
        int index = -1;
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstEmpRelevantDoc.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlEmpRelevantDoc.actionList(iCommand, start, vectSize, recordToGet);
        }
            
            whereClause = PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMPLOYEE_ID]+" = '"+employeeid+"'";
            vectSize = PstEmpRelevantDoc.getCount("");
            order = PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_RELEVANT_ID];
            listRelDoc = PstEmpRelevantDoc.list(start, recordToGet, whereClause, order);
        
        if (listRelDoc != null && listRelDoc.size()>0){
        %>

<table id ="listRelevantD" class="table table-bordered table-striped">
    <thead>
        <tr>
            <th>No</th>
            <th>Title</th>
            <th>Document</th>
            <th>Description</th>
            <th>Action</th>
        </tr>
    </thead>
    <%
            for (int i = 0; i < listRelDoc.size(); i++) {
                 EmpRelevantDoc empRelevantDoc = (EmpRelevantDoc) listRelDoc.get(i);
                
     %>
     <tr>
         <td><%= (i+1)%></td>
         <td><%= empRelevantDoc.getDocTitle() %></td>
         <%  String document = "";
                    if(!(empRelevantDoc.getFileName().equals(""))){
                        document = approot+"/imgdoc/"+  empRelevantDoc.getFileName();
                    }
                %>
         <td><a href="<%=document%>" target="_blank"> <%=empRelevantDoc.getFileName()%> </a></td>
         <td><%= empRelevantDoc.getDocDescription() %></td>
         <td>
             <button type="button" onclick="location.href='javascript:'" data-oid="<%= empRelevantDoc.getOID() %>" data-empId="<%= empRelevantDoc.getEmployeeId() %>" class="addEdditRelevant btn btn-primary" data-command="<%= Command.NONE %>" data-for="showrelDoc"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
             <button type="button" onclick="location.href='javascript:'" data-oid="<%= empRelevantDoc.getOID() %>" data-empId="<%= empRelevantDoc.getEmployeeId() %>" data-for="showuploadrel" data-command="<%= Command.UPDATE %>" class="btn btn-success uploaddata"  data-toggle="tooltip" data-placement="top" title="Upload"><i class="fa fa-upload"></i></button>
             <button type="button" onclick="location.href='javascript:'" data-oid="<%= empRelevantDoc.getOID() %>" data-for="showrelDoc" data-command="<%= Command.DELETE %>" class="btn btn-danger deleteRelevant"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
             </td>
     </tr>
     <%
       }     
%>
</table>
<%
        } else{%>
        <a><h4>No Record </h4></a>
        <% }
}else if (datafor.equals("showrelDoc")){
    
    if(iCommand == Command.SAVE){
            CtrlEmpRelevantDoc ctrlEmpRelevantDoc = new CtrlEmpRelevantDoc(request);
            ctrlEmpRelevantDoc.action(iCommand, oid, employeeid,request, emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            CtrlEmpRelevantDoc ctrlEmpRelevantDoc = new CtrlEmpRelevantDoc(request);
            ctrlEmpRelevantDoc.action(iCommand, oid, employeeid,request, emplx.getFullName(), appUserIdSess);
        }else{
            EmpRelevantDoc empRelevantDoc = new EmpRelevantDoc();
            
            if(oid != 0){
                try{
                    empRelevantDoc = PstEmpRelevantDoc.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        %>
        
        <div class="form-group">
            <label class="col-md-3">Doc Group</label>
            <div class="col-sm-9">
                <select name="<%= FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_EMP_RELVT_DOC_GRP_ID] %>" class="form-control">
                    <option value="0">-SELECT-</option>
                    <%
                    Vector listDocG = PstEmpRelevantDocGroup.listAll(); 
                    for(int idx=0; idx < listDocG.size();idx++){
                        EmpRelevantDocGroup empRelevantDocGroup = (EmpRelevantDocGroup) listDocG.get(idx);																						
                        if (empRelevantDoc.getEmpRelvtDocGrpId()== empRelevantDocGroup.getOID()) {
                        %>
                        <option selected="selected" value="<%=empRelevantDocGroup.getOID()%>"><%= empRelevantDocGroup.getDocGroup()%></option>
                        <%
                        } else {
                        %>
                        <option value="<%=empRelevantDocGroup.getOID()%>"><%= empRelevantDocGroup.getDocGroup()%></option>
                        <%
                        }												
                    }
                %>
                </select>
            </div>
        </div>
                
        <div class="form-group">
                <label class="col-sm-3">Title</label>
                <div class="col-sm-9">
                    <input type="text" name="<%=FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_TITLE] %>"  value="<%= empRelevantDoc.getDocTitle()%>" class="form-control">
                </div>
        </div> 
        
        <div class="form-group">
                <label class="col-sm-3">Description</label>
                <div class="col-sm-9">
                    <textarea name="<%=FrmEmpRelevantDoc.fieldNames[FrmEmpRelevantDoc.FRM_FIELD_DOC_DESCRIPTION]%>" class="form-control" rows="2"><%= empRelevantDoc.getDocDescription()%></textarea>
                </div>
            </div>        
        
        
<%
    }
}else if (datafor.equals("showuploadrel")){
 
 
 
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