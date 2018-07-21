<%--
    Document   : structure_template_ajax
    Created on : Jul 25, 2016, 9:43:25 AM
    Author     : ARYS
--%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstStructureTemplate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmStructureTemplate"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlStructureTemplate"%>
<%@page import="com.dimata.harisma.entity.masterdata.StructureTemplate"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String structureName = FRMQueryString.requestString(request, "structure_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    if(datafor.equals("liststructure")){
        String whereClause = "";
        String order = "";
        Vector liststructure = new Vector();

        CtrlStructureTemplate ctrlStructureTemplate = new CtrlStructureTemplate(request);

        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstStructureTemplate.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlStructureTemplate.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(structureName.equals(""))){
            whereClause = PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_TEMPLATE_ID]+" LIKE '%"+structureName+"%'";
            order = PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_TEMPLATE_ID];
            vectSize = PstStructureTemplate.getCount(whereClause);
            liststructure = PstStructureTemplate.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstStructureTemplate.getCount("");
            order = PstStructureTemplate.fieldNames[PstStructureTemplate.FLD_TEMPLATE_ID];
            liststructure = PstStructureTemplate.list(start, recordToGet, "", order);
        }
        if (liststructure != null && liststructure.size()>0){
        %>
    <table id="liststructure" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Structure Template Name</th>
                    <th>Description</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Mapping</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < liststructure.size(); i++) {
                StructureTemplate structureTemplate = (StructureTemplate) liststructure.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=structureTemplate.getTemplateName()%></td>
                    <td><%=structureTemplate.getTemplateDesc()%></td>
                    <td><%=structureTemplate.getStartDate()%></td>
                    <td><%=structureTemplate.getEndDate()%></td>
                    <td>
                        <button type="button" onclick="location.href='../masterdata/structure_mapping.jsp?oidtemp=<%= structureTemplate.getOID() %>'" class="btn btn-success"  data-toggle="tooltip" data-placement="top" title="Mapping"><i class="fa fa-map-o"></i></button>
                    </td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= structureTemplate.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showstrT"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= structureTemplate.getOID() %>" data-for="showstrT" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                        
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
         }else if(datafor.equals("showstrT")){

        if(iCommand == Command.SAVE){
            CtrlStructureTemplate ctrlStructureTemplate = new CtrlStructureTemplate(request);
            ctrlStructureTemplate.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlStructureTemplate ctrlStructureTemplate = new CtrlStructureTemplate(request);
            ctrlStructureTemplate.action(iCommand, oid);
        }else{
            StructureTemplate structureTemplate = new StructureTemplate();
            if(oid != 0){
                try{
                    structureTemplate = PstStructureTemplate.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label for="Company">Structure Template Name :</label>
                <input type="text" class="form-control" id="name" value="<%=structureTemplate.getTemplateName()%>" name="<%=FrmStructureTemplate.fieldNames[FrmStructureTemplate.FRM_FIELD_TEMPLATE_NAME]%>">
            </div>
            <div class="form-group">
                <label for="Company">Description :</label>
                <input type="text" class="form-control" id="name" value="<%=structureTemplate.getTemplateDesc()%>" name="<%=FrmStructureTemplate.fieldNames[FrmStructureTemplate.FRM_FIELD_TEMPLATE_DESC]%>">
            </div>
            <div class="form-group">
                <label>Start - End Date</label>
                <%
                    String DATE_FORMAT_NOW = "yyyy-MM-dd";
                    Date dateStart = structureTemplate.getStartDate() == null ? new Date() : structureTemplate.getStartDate();
                    Date dateEnd = structureTemplate.getEndDate() == null ? new Date() : structureTemplate.getStartDate();
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                    String strValidStart = sdf.format(dateStart);
                    String strValidEnd = sdf.format(dateEnd);
                %>
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <span class="input-group-addon"><input type="text" name="<%=FrmStructureTemplate.fieldNames[FrmStructureTemplate.FRM_FIELD_START_DATE]%>" id="datepicker" value="<%=strValidStart%>" class="datepicker"/></span>
                <span class="input-group-addon">to</span>
                <span class="input-group-addon"><input type="text" name="<%=FrmStructureTemplate.fieldNames[FrmStructureTemplate.FRM_FIELD_END_DATE]%>" id="datepicker" value="<%=strValidEnd%>" class="datepicker" /></span>
                
            </div>
        <%
    }
}
    
%> 