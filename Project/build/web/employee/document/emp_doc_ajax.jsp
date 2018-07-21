<%-- 
    Document   : emp_doc_ajax
    Created on : 29-Jul-2016, 13:39:18
    Author     : Acer
--%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlEmpDoc"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmEmpDoc"%>
<%@page import="com.dimata.harisma.report.EmployeeAmountXLS"%>
<%@ include file = "../../main/javainit.jsp" %> 
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DEPARTMENT); %>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oid = FRMQueryString.requestLong(request, "oid");
    long oidEmpDoc = FRMQueryString.requestLong(request, "emp_doc_id");
    String datafor = FRMQueryString.requestString(request, "datafor");
    if(datafor.equals("listdoc")){
        
        
    
        Vector listEmpDoc = new Vector();
    
        CtrlEmpDoc ctrlEmpDoc = new CtrlEmpDoc(request);
        int iErrCode = ctrlEmpDoc.action(iCommand , oidEmpDoc);
        EmpDoc empDoc = ctrlEmpDoc.getEmpDoc();

        int recordToGet = 10;
        int vectSize = 0;
        listEmpDoc = PstEmpDoc.list(0, recordToGet, "", "");
    
        if (listEmpDoc != null && listEmpDoc.size()>0){
%>
        <table id="listdoc" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Master Document</th>
                    <th>Document Title</th>
                    <th>Document Number</th>
                    <th>Document Date</th>
                    <th>Valid Date</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for(int i=0; i<listEmpDoc.size(); i++){
                EmpDoc empDocument = (EmpDoc)listEmpDoc.get(i);
                String docMasterName = "-";
                try {
                    DocMaster docMaster = PstDocMaster.fetchExc(empDocument.getDoc_master_id());
                    docMasterName = docMaster.getDoc_title();
                } catch(Exception ex){
                    System.out.println("emp doc=>"+ex.toString());
                }
                
            
                %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%= docMasterName%></td>
                    <td><%= empDocument.getDoc_title()%></td>
                    <td><%= empDocument.getDoc_number()%></td>
                    <td><%= empDocument.getRequest_date()%></td>
                    <td><%= empDocument.getDate_of_issue() %></td>
                    <td>
                        <a href="javascript:" data-oid="<%= empDocument.getOID() %>" data-command="<%= Command.NONE %>"><i class="fa fa-pencil"></i> Detail </a> 
                        |
                        <a href="javascript:" data-oid="<%= empDocument.getOID() %>" data-for="showdocform" class="addeditdata" data-command="<%= Command.NONE %>"><i class="fa fa-pencil"></i> Edit </a> 
                        |
                        <a href="javascript:" data-oid="<%= empDocument.getOID() %>" data-for="showdocform" class="deletedata" data-command="<%= Command.DELETE %>"><i class="fa fa-times"></i> Delete</a>

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
    }else if(datafor.equals("showdocform")){

        if(iCommand == Command.SAVE){
            CtrlEmpDoc ctrlEmpDoc = new CtrlEmpDoc(request);
            ctrlEmpDoc.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlEmpDoc ctrlEmpDoc = new CtrlEmpDoc(request);
            ctrlEmpDoc.action(iCommand, oid);
        }else{
            EmpDoc empDoc = new EmpDoc();
            if(oid != 0){
                try{
                     empDoc = PstEmpDoc.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
           
            %>
            
            
            <label>Document Master</label>
            <div class="input-group" >
                <select name="<%= FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_MASTER_ID] %>" class="form-group">
                <option value="0">-SELECT-</option>
                <%
                Vector listDocMaster = PstDocMaster.list(0, 0, "", "");    
                for (int i = 0; i < listDocMaster.size(); i++) {
                    DocMaster docMaster = (DocMaster) listDocMaster.get(i);
                    if (docMaster.getOID() == empDoc.getDoc_master_id()){
                        %>
                        <option selected="selected" value="<%= docMaster.getOID() %>"><%= docMaster.getDoc_title() %></option>
                        <%
                    } else {
                        %>
                        <option value="<%= docMaster.getOID() %>"><%= docMaster.getDoc_title() %></option>
                        <%
                    }
                }
                %>
                </select>
            </div>
            
            <label>Document Title</label>
            <div class="input-group" >
                <input type="text" name="<%= FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_TITLE] %>" size="50" value="<%= empDoc.getDoc_title() %>" class="form-group" />
            </div>
            
            <label>Document Number</label>
            <div class="input-group" >
                <input type="text" name="<%= FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_NUMBER] %>" size="50" value="<%= empDoc.getDoc_number() %>" class="form-group"/>
            </div>
            
            <label>Document Date</label>
            <div class="input-group" >
                <input type="text" class="datepicker" name="<%= FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_REQUEST_DATE_STRING] %>" value="<%= empDoc.getRequest_date() %>" />
            </div>
            
            <label>Valid Date</label>
            <div class="input-group" >
                <input type="text" class="mydate" name="<%= FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DATE_OF_ISSUE_STRING] %>" value="<%= empDoc.getDate_of_issue() %>" />
            </div>
  <%          
        }    
    }
%>