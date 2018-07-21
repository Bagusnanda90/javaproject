<%-- 
    Document   : position_ajax
    Created on : 28-Jun-2016, 09:32:16
    Author     : Acer
--%>


<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPosition"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String posName = FRMQueryString.requestString(request, "pos_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    I_Atendance attdConfig = null;
        try {
            attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
        }
    if(datafor.equals("listpos")){
        String whereClause = "";
        String order = "";
        Vector listPosition = new Vector();

        CtrlPosition ctrlPosition = new CtrlPosition(request);
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstPosition.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlPosition.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(posName.equals(""))){
            whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+posName+"%'";
            order = PstPosition.fieldNames[PstPosition.FLD_POSITION];
            vectSize = PstPosition.getCount(whereClause);
            listPosition = PstPosition.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstPosition.getCount("");
            order = PstPosition.fieldNames[PstPosition.FLD_POSITION];
            listPosition = PstPosition.list(start, recordToGet, "", order);
        }

        if (listPosition != null && listPosition.size()>0){
        %>
        <table id="listPosition" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Position</th>
                    <%
                    if (attdConfig != null && attdConfig.getConfigurationShowPositionCode()) {
                    %>
                        <th>Position Code</th>
                    <%
                        }
                    %>
                    <th>Type</th>
                    <th>Show In Pay Input</th>
                    <th>Job Description</th>
                    <th>Valid Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <%
            for (int i = 0; i < listPosition.size(); i++) {
                    Position position = (Position)listPosition.get(i);
            %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= position.getPosition()%></td>
                <%
                if (attdConfig != null && attdConfig.getConfigurationShowPositionCode()) {
                %>
                    <td><%= position.getKodePosition()%></td>
                <%
                    }
                %>
                <td><%= PstPosition.strPositionLevelNames[position.getPositionLevel()] %></td>
                <td><%= PstPosition.strShowPayInput[position.getFlagShowPayInput()] %></td>
                <td>
                    <a href="javascript:" data-oid="<%= position.getOID() %>" data-for="showjobdesc" class="showdata" data-command="<%= Command.NONE %>"><i class="fa fa-search"></i> Show Job Desc </a> 
                </td>
                <td><%= PstPosition.validStatusValue[position.getValidStatus()] %></td>
                    <td>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= position.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showposform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-pencil"></i></button>
                        <button type="button" onclick="location.href='javascript:'" data-oid="<%= position.getOID() %>" data-for="showposform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-times"></i></button>
                    
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
    }else if(datafor.equals("showposform")){

        if(iCommand == Command.SAVE){
            CtrlPosition ctrlPosition = new CtrlPosition(request);
            ctrlPosition.action(iCommand, oid, attdConfig);
        }else if(iCommand == Command.DELETE){
            CtrlPosition ctrlPosition = new CtrlPosition(request);
            ctrlPosition.action(iCommand, oid, attdConfig);
        }else{
            Position position = new Position();
            if(oid != 0){
                try{
                    position = PstPosition.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <div class="form-group">
                <label>Position :</label>
                <input type="text" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION] %>" class="form-control" placeholder="Position" value="<%= position.getPosition() %>">
            </div>
            <div class="form-group">
            <%if(attdConfig!=null && attdConfig.getConfigurationShowPositionCode()){%>
                <label>Position Kode :</label>
                <input type="text" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_KODE] %>"  value="<%= position.getKodePosition() %>" class="form-control">
                <%}else{%>
                <input type="hidden" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_KODE] %>"  value="<%= "-" %>" class="form-control">
            <%}%>
            </div>                                        
            <div class="form-group">
                <label>Position Group :</label>
                <select class="form-control" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_GROUP_ID]%>">
                    <option value="0">-Select-</option>
                    <%
                    Vector listPosGroup = PstPositionGroup.listAll(); 
                    for(int idx=0; idx < listPosGroup.size();idx++){
                        PositionGroup positionGroup = (PositionGroup) listPosGroup.get(idx);																						
                        if (position.getPositionGroupId()== positionGroup.getOID()) {
                        %>
                        <option selected="selected" value="<%=positionGroup.getOID()%>"><%= positionGroup.getPositionGroupName()%></option>
                        <%
                        } else {
                        %>
                        <option value="<%=positionGroup.getOID()%>"><%= positionGroup.getPositionGroupName()%></option>
                        <%
                        }												
                    }
                %>
                </select>
            </div>
            <div class="form-group">
                <label>Type :</label>
                <select class="form-control" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_LEVEL]%>">
                <%
                    for(int idx=0; idx < PstPosition.strPositionLevelNames.length;idx++){
                        if (position.getPositionLevel()== Integer.valueOf(PstPosition.strPositionLevelValue[idx])) {
                %>
                        <option selected="selected" value="<%=PstPosition.strPositionLevelValue[idx]%>"><%=PstPosition.strPositionLevelNames[idx]%></option>
                <%
                        } else {
                %>
                        <option value="<%=PstPosition.strPositionLevelValue[idx]%>"><%=PstPosition.strPositionLevelNames[idx]%></option>
                <%
                        }
                    }   
                %>
                </select>
                <label>Type for Payroll :</label>
                <select class="form-control" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_LEVEL_PAYROL]%>">
                    <option value="-1">-None-</option>
                <%
                    for(int idx=0; idx < PstPosition.strPositionLevelNames.length;idx++){																							
                        if (position.getPositionLevelPayrol()== Integer.valueOf(PstPosition.strPositionLevelValue[idx])) {
                %>
                        <option selected="selected" value="<%=PstPosition.strPositionLevelValue[idx]%>"><%=PstPosition.strPositionLevelNames[idx]%></option>
                <%
                        } else {
                %>
                        <option value="<%=PstPosition.strPositionLevelValue[idx]%>"><%=PstPosition.strPositionLevelNames[idx]%></option>
                <%
                        }
                    }   
                %>
                </select>
            </div>
            <div class="form-group">
               <label>Head Title :</label>
                <select class="form-control" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_HEAD_TITLE]%>">
                    <option value="-1">-None-</option>
                <%
                    try{
                    for(int idx=0; idx < PstPosition.strHeadTitle.length;idx++){																						
                        if(position.getHeadTitle() == Integer.valueOf(PstPosition.strHeadTitleInt[idx])){
                %>
                        <option selected="selected" value="<%=PstPosition.strHeadTitleInt[idx]%>"><%=PstPosition.strHeadTitle[idx]%></option>
                <%      } else { %>
                        <option value="<%=PstPosition.strHeadTitleInt[idx]%>"><%=PstPosition.strHeadTitle[idx]%></option>
                <%        }
                    }
                }
                    catch (Exception e){
                        System.out.println("Error on head title : "+e.toString());
                    }
                %>
                </select>
            </div>
            <div class="form-group">
                <label>Valid Status :</label>
                <select name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_STATUS]%>" class="form-control" id="valid_status">
                    <%
                        if (position.getValidStatus() == PstSection.VALID_ACTIVE) {
                    %>
                    <option value="<%=PstPosition.VALID_ACTIVE%>" selected="selected">Active</option>
                    <option value="<%=PstPosition.VALID_HISTORY%>">History</option>
                    <%
                    } else {
                    %>
                    <option value="<%=PstPosition.VALID_ACTIVE%>">Active</option>
                    <option value="<%=PstPosition.VALID_HISTORY%>" selected="selected">History</option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label>Masa Berlaku</label>
                <%
                    String DATE_FORMAT_NOW = "yyyy-MM-dd";
                    Date dateStart = position.getValidStart() == null ? new Date() : position.getValidStart();
                    Date dateEnd = position.getValidEnd() == null ? new Date() : position.getValidEnd();
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                    String strValidStart = sdf.format(dateStart);
                    String strValidEnd = sdf.format(dateEnd);
                %>
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <span class="input-group-addon"><input type="text" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_START]%>" id="datepicker1" value="<%=strValidStart%>" class="datepicker" /></span>
                <span class="input-group-addon">to</span>
                <span class="input-group-addon"><input type="text" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_END]%>" id="datepicker2" value="<%=strValidEnd%>" class="datepicker"/></span>
            </div>
            <div class="form-group">
            <label>Level</label>
            <select name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_LEVEL_ID]%>" class="form-control" id="level">
                <option value="0">-SELECT-</option>
                <%
                    String orderLevel = PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK] + " ASC";
                    Vector listLevel = PstLevel.list(0, 0, "", orderLevel);
                    if (listLevel != null && listLevel.size() > 0) {
                        for (int l = 0; l < listLevel.size(); l++) {
                            Level level = (Level) listLevel.get(l);
                            if (level.getOID() == position.getLevelId()) {
                %>
                <option value="<%=level.getOID()%>" selected="selected"><%=level.getLevel()%></option>
                <%
                } else {
                %>
                <option value="<%=level.getOID()%>"><%=level.getLevel()%></option>
                <%
                            }

                        }
                    }
                %>
            </select>
            </div>
            <div class="form-group">
                <label>Show In Pay Input &nbsp;&nbsp;&nbsp;</label> <br>
                <input type="checkbox" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_FLAG_POSITION_SHOW_PAY_INPUT]%>" <%=(position.getFlagShowPayInput()==PstPosition.YES_SHOW_PAY_INPUT ? "checked" : "" ) %> value="1"> 
                <a>please check to show in pay input </a>
            </div>
            <div class="form-group">
                <label>Department &nbsp;&nbsp;&nbsp;</label> <br>
                <input type="checkbox" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_ALL_DEPARTMENT]%>" <%=(position.getAllDepartment()==PstPosition.ALL_DEPARTMENT_TRUE ? "checked" : "" ) %> value="1" >
                <a>all department </a>
            </div>
            <div class="form-group">
                <label>Option &nbsp;&nbsp;&nbsp;</label> <br>
                <input type="checkbox" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_DISABLED_APP_UNDER_SUPERVISOR]%>" <%=(position.getDisabledAppUnderSupervisor()==PstPosition.DISABLED_APP_UNDER_SUPERVISOR_TRUE ? "checked" : "" ) %> value="1">
                <a>To DISABLE Leave Approval Employee Under Supervisor please check  </a>
            </div>
            <div class="form-group">
                <label>Option &nbsp;&nbsp;&nbsp;</label> <br>
                <input type="checkbox" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_DISABLED_APP_DEPT_SCOPE]%>" <%=(position.getDisabledAppDeptScope()==PstPosition.DISABLED_APP_DEPT_SCOPE_TRUE ? "checked" : "" ) %> value="1" >     
                <a>To DISABLE Leave Approval Department Scope please check  </a>
            </div>   
            <div class="form-group">
                <label>Option &nbsp;&nbsp;&nbsp;</label> <br>
                <input type="checkbox" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_DISABLED_APP_DIV_SCOPE]%>" <%=(position.getDisabedAppDivisionScope()==PstPosition.DISABLED_APP_DIV_SCOPE_TRUE ? "checked" : "" ) %> value="1" >
                <a>To DISABLE Leave Approval Division Scope please check  </a>
            </div>
            <div class="form-group">
                <label>Time</label>
                <input type="text" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_DEDLINE_SCH_BEFORE] %>"  value="<%= position.getDeadlineScheduleBefore() %>" class="form-control">
                <a>(Hour) Limit Time Update Schedule before current time ( unlimited time = 8640 )</a>
            </div>
            <div class="form-group">
                <label>Time</label>
                <input type="text" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_DEDLINE_SCH_AFTER] %>"  value="<%= position.getDeadlineScheduleAfter() %>" class="form-control">
                <a>(Hour) Limit Time Update Schedule after current time ( unlimited time = 8640 )</a>
            </div>
            <div class="form-group">
                <label>Time</label>
                <input type="text" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_DEDLINE_SCH_LEAVE_BEFORE] %>"  value="<%= position.getDeadlineScheduleLeaveBefore() %>" class="form-control">
                <a>(Hour) Limit Time Update Schedule Leave before current time ( unlimited time = 8640 )</a>
            </div>
            <div class="form-group">
                <label>Time</label>
                <input type="text" name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_DEDLINE_SCH_LEAVE_AFTER] %>"  value="<%= position.getDeadlineScheduleLeaveAfter() %>" class="form-control">
                <a>(Hour) Limit Time Update Schedule Leave after current time ( unlimited time = 8640 )</a>
            </div>    
                <div class="form-group">
                    <label>Job Description</label>
                    <textarea name="<%=FrmPosition.fieldNames[FrmPosition.FRM_FIELD_DESCRIPTION] %>" class="form-control" cols="30" rows="3"><%= position.getDescription() %></textarea>
                </div>    
        <%
    }
}
    
%>    