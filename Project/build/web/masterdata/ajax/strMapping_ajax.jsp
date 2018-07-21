<%--
    Document   : strMapping_ajax
    Created on : Jul 26, 2016, 9:36:26 AM
    Author     : ARYS
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.entity.masterdata.MappingPosition"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlMappingPosition"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmMappingPosition"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstMappingPosition"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../../main/javainit.jsp" %>

<%
    String strMapName = FRMQueryString.requestString(request, "structure_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    long oidTemp = FRMQueryString.requestLong(request, "oidtemp");
    if(datafor.equals("liststrMapp")){
        String whereClause = "";
        String order = "";
        Vector liststrMapp = new Vector();

        CtrlMappingPosition ctrlMappingPosition = new CtrlMappingPosition(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstMappingPosition.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlMappingPosition.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(strMapName.equals(""))){
            whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_MAPPING_POSITION_ID]+" LIKE '%"+strMapName+"%'";
            order = PstMappingPosition.fieldNames[PstMappingPosition.FLD_MAPPING_POSITION_ID];
            vectSize = PstMappingPosition.getCount(whereClause);
            liststrMapp = PstMappingPosition.list(0, 0, whereClause, order);        
        } else {
            whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+oidTemp;
            vectSize = PstMappingPosition.getCount("");
            order = PstMappingPosition.fieldNames[PstMappingPosition.FLD_MAPPING_POSITION_ID];
            liststrMapp = PstMappingPosition.list(start, recordToGet, whereClause, order);
        }
        if (liststrMapp != null && liststrMapp.size()>0){
        %>


        <table id="liststructure" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Up Position</th>
                    <th>Down Position</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Type of Link</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for(int i=0; i<liststrMapp.size(); i++){
                MappingPosition maping = (MappingPosition)liststrMapp.get(i);
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
                    <td><%=(i + 1)%></td>
                    <td><%= positionUp.getPosition()%></td>
                    <td><%= positionDown.getPosition()%></td>
                    <td><%= maping.getStartDate()%></td>
                    <td><%= maping.getEndDate()%></td>
                    <td><%= PstMappingPosition.typeOfLink[maping.getTypeOfLink()] %></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= maping.getOID() %>"  class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="ShowStrMapp"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= maping.getOID() %>" data-for="ShowStrMapp" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                     
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
         }else if(datafor.equals("ShowStrMapp")){

        if(iCommand == Command.SAVE){
            CtrlMappingPosition ctrlMappingPosition = new CtrlMappingPosition(request);
            ctrlMappingPosition.action(iCommand, oid);
        }else if(iCommand == Command.DELETE){
            CtrlMappingPosition ctrlMappingPosition = new CtrlMappingPosition(request);
            ctrlMappingPosition.action(iCommand, oid);
        }else{
            MappingPosition mappingPosition = new MappingPosition();
            Position positionUp = new Position();
            Position positionDown = new Position();
       
            if(oid != 0){
                try{
                    mappingPosition = PstMappingPosition.fetchExc(oid);
                    positionUp = PstPosition.fetchExc(mappingPosition.getUpPositionId());
                    positionDown = PstPosition.fetchExc(mappingPosition.getDownPositionId());
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                
                
                
            }
           
            %>
            
            <%!
            public String getPositionName(long posId){
            String position = "";
            Position pos = new Position();
            try {
                pos = PstPosition.fetchExc(posId);
            } catch(Exception ex){
                System.out.println("getPositionName ==> "+ex.toString());
            }
            position = pos.getPosition();
            return position;
            }
            %>
            <input type="hidden" name="<%=FrmMappingPosition.fieldNames[FrmMappingPosition.FRM_FIELD_TEMPLATE_ID]%>" value="<%=oidTemp%>">
            <label>Up Position</label>
            <div class="input-group" >
                <input type="hidden" name="<%=FrmMappingPosition.fieldNames[FrmMappingPosition.FRM_FIELD_UP_POSITION_ID]%>" id="uppositionoid">
                <input type="text" disabled="true" class="form-control" id="uppositionname" value="<%= positionUp.getPosition()%>">
                <span class="input-group-btn">
                    <button id="filter" type="button" class="btn btn-primary btn-block browse" href="#stack1" data-for="filterup" data-oid="0">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </span>
            </div>
            
            <label>Down Position</label>
            <div class="input-group" >
                <input type="hidden" name="<%=FrmMappingPosition.fieldNames[FrmMappingPosition.FRM_FIELD_DOWN_POSITION_ID]%>" id="downpositionoid">
                <input type="text" disabled="true" class="form-control" id="downpositionname"  value="<%= positionDown.getPosition()%>">
                <span class="input-group-btn">
                    <button id="filter" type="button" class="btn btn-primary btn-block browse" href="#stack1" data-for="filterdown" data-oid="0">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </span>
            </div>
            
            <div class="form-group">
                <label>Validity Period</label>
                <%
                    String DATE_FORMAT_NOW = "yyyy-MM-dd";
                    Date dateStart = mappingPosition.getStartDate() == null ? new Date() : mappingPosition.getStartDate();
                    Date dateEnd = mappingPosition.getEndDate() == null ? new Date() : mappingPosition.getStartDate();
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                    String strValidStart = sdf.format(dateStart);
                    String strValidEnd = sdf.format(dateEnd);
                %>
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <span class="input-group-addon"><input type="text" name="<%=FrmMappingPosition.fieldNames[FrmMappingPosition.FRM_FIELD_START_DATE]%>" id="datepicker" value="<%=strValidStart%>" class="datepicker"/></span>
                <span class="input-group-addon">to</span>
                <span class="input-group-addon"><input type="text" name="<%=FrmMappingPosition.fieldNames[FrmMappingPosition.FRM_FIELD_END_DATE]%>" id="datepicker" value="<%=strValidEnd%>" class="datepicker" /></span>
            </div>
            <div class="form-group">
                <label>Type of Link :</label>
                <div> <select class="form-control" id="cmpname" name="<%=FrmMappingPosition.fieldNames[FrmMappingPosition.FRM_FIELD_TYPE_OF_LINK]%>">
                   
                      <%
                        String[] arrType = {"-select-","Supervisory","Coordination","Leave Approval","Schedule Approval","EO Form Approval","Warning"};
                        for (int t=0; t<arrType.length; t++){
                            if(t == mappingPosition.getTypeOfLink()){
                                %>
                                <option value="<%=t%>" selected="selected"><%=arrType[t]%></option>
                                <%
                            } else {
                                %>
                                <option value="<%=t%>"><%=arrType[t]%></option>
                                <%
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