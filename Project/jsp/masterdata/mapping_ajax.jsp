<%-- 
    Document   : mapping_ajax
    Created on : 15-Apr-2016, 15:35:37
    Author     : Acer
--%>

<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.masterdata.MappingPosition"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstMappingPosition"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlMappingPosition"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ include file = "../main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String oidMapping = FRMQueryString.requestString(request, "oid_mapping");
    long oidTemplate = FRMQueryString.requestLong(request, "oid_template");
    long selectClone = FRMQueryString.requestLong(request, "select_clone");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long selectPosition = FRMQueryString.requestLong(request, "rb_position");
    

    String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+oidTemplate;
    String order = "";
    
    Vector listMapping = new Vector();
    CtrlMappingPosition ctrlMapping = new CtrlMappingPosition(request);
    
    int recordToGet = 10;
    int vectSize = 0;
    vectSize = PstMappingPosition.getCount(whereClause);
    
    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlMapping.actionList(iCommand, start, vectSize, recordToGet);
    }
    
    if (selectPosition == 0){ // search by Up Position
        if(!(oidMapping.equals("0"))){
        whereClause = "HP."+ PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+oidMapping+"%' AND HMP." + PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+oidTemplate;
        order = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID];
        vectSize = PstMappingPosition.countUpPosition(whereClause);
        recordToGet = 0;
        }
        listMapping = PstMappingPosition.listUpPosition(start, recordToGet, whereClause, order);
    } else {
        if(!(oidMapping.equals("0"))) {// search by Down Position
        whereClause = "HP."+ PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+oidMapping+"%' AND HMP." + PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+oidTemplate;
        vectSize = PstMappingPosition.getCount(whereClause);
        order = PstMappingPosition.fieldNames[PstMappingPosition.FLD_DOWN_POSITION_ID];
        recordToGet = 0;
        }
        listMapping = PstMappingPosition.listDownPosition(start, recordToGet, whereClause, order);
    }
    if (listMapping != null && listMapping.size()>0){
        %>
        <table class="tblStyle">
            <tr>
                <td class="title_tbl" style="background-color: #DDD;">Up Position</td>
                <td class="title_tbl" style="background-color: #DDD;">Down Position</td>
                <td class="title_tbl" style="background-color: #DDD;">Start Date</td>
                <td class="title_tbl" style="background-color: #DDD;">End Date</td>
                <td class="title_tbl" style="background-color: #DDD;">Type of Link</td>
                <td class="title_tbl" style="background-color: #DDD;">Action</td>
            </tr>
            <%
            for(int i=0; i<listMapping.size(); i++){
                MappingPosition maping = (MappingPosition)listMapping.get(i);
                Position positionUp = new Position();
                Position positionDown = new Position();
                try {
                    positionUp = PstPosition.fetchExc(maping.getUpPositionId());
                    positionDown = PstPosition.fetchExc(maping.getDownPositionId());
                } catch (Exception e) {
                    System.out.println("=>"+e.toString());
                }
                %>
                <tr>
                    <td><%= positionUp.getPosition()%></td>
                    <td><%= positionDown.getPosition()%></td>
                    <td><%= maping.getStartDate()%></td>
                    <td><%= maping.getEndDate()%></td>
                    <td><%= PstMappingPosition.typeOfLink[maping.getTypeOfLink()] %></td>
                    <td>
                    <a class="btn-small-e" style="color:#FFF" href="javascript:cmdEdit('<%=maping.getOID()%>')">e</a>
                    <a class="btn-small-x" style="color:#FFF" href="javascript:cmdAsk('<%=maping.getOID()%>')">&times;</a>
                </td>
                </tr>
                <%
            }
            %>
        </table>
        <div>&nbsp;</div>
        <div id="record_count">
            <%
            if (vectSize >= recordToGet){
                %>
                List : <%=start%> &HorizontalLine; <%= (start+recordToGet) %> | 
                <%
            }
            %>
            Total : <%= vectSize %>
        </div>
        <div class="pagging">
            <a style="color:#F5F5F5" href="javascript:cmdListFirst('<%=start%>')" class="btn-small">First</a>
            <a style="color:#F5F5F5" href="javascript:cmdListPrev('<%=start%>')" class="btn-small">Previous</a>
            <a style="color:#F5F5F5" href="javascript:cmdListNext('<%=start%>')" class="btn-small">Next</a>
            <a style="color:#F5F5F5" href="javascript:cmdListLast('<%=start%>')" class="btn-small">Last</a>
        </div>
        <%
    }
%>