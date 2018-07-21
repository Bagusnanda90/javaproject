<%-- 
    Document   : famRelation_ajax
    Created on : Jun 28, 2016, 2:42:54 PM
    Author     : ARYS
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmFamRelation"%>
<%@page import="com.dimata.harisma.entity.masterdata.FamRelation"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstFamRelation"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlFamRelation"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_FAMILY_RELATION);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String famReName = FRMQueryString.requestString(request, "famRe_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("listfamRelation")){
        String whereClause = "";
        String order = "";
        Vector listfamRelation = new Vector();

        CtrlFamRelation ctrlfamRelation = new CtrlFamRelation(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstFamRelation.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlfamRelation.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(famReName.equals(""))){
            whereClause = PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION]+" LIKE '%"+famReName+"%'";
            order = PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION];
            vectSize = PstFamRelation.getCount(whereClause);
            listfamRelation = PstFamRelation.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstFamRelation.getCount("");
            order = PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION];
            listfamRelation = PstFamRelation.list(start, recordToGet, "", order);
        }

        if (listfamRelation != null && listfamRelation.size()>0){
        %>
        <table id="example1" class="table table-bordered table-striped">
        <thead>
            <tr>
                <th>No</th>
                <th>Family Relationship</th>
                <th>Type</th>
                <th>Action</th>
                </tr>
        </thead>
        <%
            for (int i = 0; i < listfamRelation.size(); i++) {
                FamRelation famRelation = (FamRelation) listfamRelation.get(i);

        %>
        <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=famRelation.getFamRelation()%></td>
                    <td><%=PstFamRelation.levelNames[famRelation.getFamRelationType()]%></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= famRelation.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showfamRelationform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= famRelation.getOID() %>" data-for="showfamRelationform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
                    </td>
        </tr>
        <%
            }
        %>
        </table>
        
            <%
        }
    }else if(datafor.equals("showfamRelationform")){

        if(iCommand == Command.SAVE){
            CtrlFamRelation ctrlFamRelation = new CtrlFamRelation(request);
            ctrlFamRelation.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlFamRelation ctrlFamRelation = new CtrlFamRelation(request);
            ctrlFamRelation.action(iCommand, oid);
        }else{
            FamRelation famRelation = new FamRelation();
            if(oid != 0){
                try{
                    famRelation = PstFamRelation.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Company">Family Relationship :</label>
                <input type="text" class="form-control" id="company" value="<%= famRelation.getFamRelation()%>" name="<%=FrmFamRelation.fieldNames[FrmFamRelation.FRM_FIELD_FAMILY_RELATION]%>">
            </div>
            <div class="form-group">
                <label for="Company">Type :</label>
                <select rows="5" class="form-control" id="cmp_address" name="<%=FrmFamRelation.fieldNames[FrmFamRelation.FRM_FIELD_FAMILY_RELATION_TYPE]%>">
                    
                    <%
                   
                    for (int i = 0; i < PstFamRelation.levelNames.length; i++) {
                        if(famRelation.getFamRelationType()== i){
                        %>
                        <option selected="selected" value="<%=i%>"><%=PstFamRelation.levelNames[i]%></option>
                        <%} else{%>
                        <option value="<%=i%>"><%=PstFamRelation.levelNames[i]%></option>
                       <% }
                       }%>
                </select>
            </div>
        <%
    }
}
    
%> 
