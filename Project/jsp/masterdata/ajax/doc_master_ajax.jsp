<%-- 
    Document   : division_ajax
    Created on : 27-Jun-2016, 19:49:35
    Author     : Acer
--%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMasterTemplate"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMaster"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDocMaster"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMaster"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDocMaster"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DEPARTMENT);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String docMasName = FRMQueryString.requestString(request, "docMaster_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listdocMas")){
        String whereClause = "";
        String order = "";
        Vector listdocMas = new Vector();

        CtrlDocMaster ctrlDocMaster = new CtrlDocMaster(request);
       
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstDocMaster.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlDocMaster.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(docMasName.equals(""))){
            whereClause = PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_MASTER_ID]+" LIKE '%"+docMasName+"%'";
            order = PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_MASTER_ID];
            vectSize = PstDocMaster.getCount(whereClause);
            listdocMas = PstDocMaster.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstDocMaster.getCount("");
            order = PstDocMaster.fieldNames[PstDocMaster.FLD_DOC_MASTER_ID];
            listdocMas = PstDocMaster.list(start, recordToGet, "", order);
        }

        if (listdocMas != null && listdocMas.size()>0){
        %>
        <table id="listdocmaster" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Type Name</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Form Detail</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
           
            
            for (int i = 0; i < listdocMas.size(); i++) {
                DocMaster docMaster = (DocMaster) listdocMas.get(i);
                DocType docType = new DocType();
                String dtname = "";
                try {
                    docType = PstDocType.fetchExc(docMaster.getDoc_type_id());
                    dtname = docType.getType_name();}
                    catch (Exception exc){
                }
                
             %>
                <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=dtname%></td>
                    <td><%=docMaster.getDoc_title()%></td>
                    <td><%=docMaster.getDescription()%></td>
                    <td>
                        <a href="/masterdata/doc_master_expense.jsp?DocMasterId=" <i class="fa fa-file"></i> Expense </a> 
                        ||
                        <a href="/masterdata/doc_master_template.jsp?DocMasterId=" <i class="fa fa-adjust"></i> Template </a>
                        ||
                        <a href="/masterdata/doc_master_flow.jsp?DocMasterId=" <i class="fa fa-filter"></i> Flow </a>
                        ||
                        <a href="/masterdata/doc_master_flow.jsp?DocMasterId=" <i class="fa fa-tasks"></i> Action </a>
                    </td>
                    <td>
                        <a href="javascript:" data-oid="<%= docMaster.getOID() %>" data-for="showdocMform" class="addeditdata" data-command="<%= Command.NONE %>"><i class="fa fa-pencil"></i> Edit </a> 
                        |
                        <a href="javascript:" data-oid="<%= docMaster.getOID() %>" data-for="showdocMform" class="deletedata" data-command="<%= Command.DELETE %>"><i class="fa fa-times"></i> Delete</a>
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
    }else if(datafor.equals("showdocMform")){

        if(iCommand == Command.SAVE){
            CtrlDocMaster ctrlDocMaster = new CtrlDocMaster(request);
            ctrlDocMaster.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlDocMaster ctrlDocMaster = new CtrlDocMaster(request);
            ctrlDocMaster.action(iCommand, oid);
        }else{
            DocMaster docMaster = new DocMaster();
            if(oid != 0){
                try{
                    docMaster = PstDocMaster.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label class="col-sm-4">Doc type :</label>
                <div class="col-sm-7"> <select rows="5" class="form-control" id="cmpname" name="<%=FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DOC_TYPE_ID]%>">
                   
                      <%
                      Vector listdocType = PstDocType.list(0, 0, "", "");
                      if(listdocType != null && listdocType.size()>0){
                          for (int i = 0; i<listdocType.size() ; i++){
                              DocType docType = (DocType) listdocType.get(i);
                               if(docMaster.getDoc_type_id()==docMaster.getOID()){  
                              %>
                              <option selected="selected" value="<%= docType.getOID()%>"><%= docType.getType_name() %></option>
                              <%}else{%>
                              <option  value="<%= docType.getOID()%>"><%=docType.getType_name()%></option>
                              <%
                             }
                             }
                           }
                      %>
                </select>
            </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-4">Title :</label>
                <div class="col-sm-7"><textarea rows="5" class="form-control" id="description" name="<%=FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DESCRIPTION]%>"><%= docMaster.getDoc_title() %></textarea>
            </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-4">Description :</label>
                <div class="col-sm-7"><textarea rows="5" class="form-control" id="description" name="<%=FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DESCRIPTION]%>"><%= docMaster.getDescription() %></textarea>
            </div>
            </div>
        
        <%
    }
}
    
%>    