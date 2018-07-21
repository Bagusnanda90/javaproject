<%-- 
    Document   : position-ajax
    Created on : Jan 14, 2016, 5:09:33 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPosition"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String positionName = FRMQueryString.requestString(request, "position_name");
    long validStatusSelect = FRMQueryString.requestLong(request, "valid_status_select");
    long levelSelect = FRMQueryString.requestLong(request, "level_select");
    long divisionId = FRMQueryString.requestLong(request, "division_select");
    
    String test = "default";
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String whereClause = "";
    String order = "";
    Vector listPosition = new Vector();
    CtrlPosition ctrlPosition= new CtrlPosition(request);
    
    int recordToGet = 20;
    int vectSize = 0;
    vectSize = PstPosition.getCount("");
    if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
            || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
        start = ctrlPosition.actionList(iCommand, start, vectSize, recordToGet);
    }
    
    if (!(positionName.equals("0")) && validStatusSelect == 0 && levelSelect == 0 && divisionId == 0){
        test = "Searching By Position Name";
        whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+positionName+"%'";
        order = PstPosition.fieldNames[PstPosition.FLD_POSITION];
        vectSize = PstPosition.getCount(whereClause);
        listPosition = PstPosition.list(start, recordToGet, whereClause, order);
    }
    
    if (positionName.equals("0") && validStatusSelect != 0 && levelSelect == 0 && divisionId == 0){
        test = "Searching By Valid Status";
        whereClause = PstPosition.fieldNames[PstPosition.FLD_VALID_STATUS]+"="+validStatusSelect;
        order = PstPosition.fieldNames[PstPosition.FLD_POSITION];
        vectSize = PstPosition.getCount(whereClause);
        listPosition = PstPosition.list(start, recordToGet, whereClause, order);
    }
    
    if (positionName.equals("0") && validStatusSelect == 0 && levelSelect != 0 && divisionId == 0){
        test = "Searching By Level";
        whereClause = PstPosition.fieldNames[PstPosition.FLD_LEVEL_ID]+"="+levelSelect;
        order = PstPosition.fieldNames[PstPosition.FLD_POSITION];
        vectSize = PstPosition.getCount(whereClause);
        listPosition = PstPosition.list(start, recordToGet, whereClause, order);
    }
    
    if (positionName.equals("0") && validStatusSelect == 0 && levelSelect == 0 && divisionId != 0){
        test = "Searching By Division";
        String strIN = "0";
        whereClause = PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+"="+divisionId;
        Vector listPosDiv = PstPositionDivision.list(0, 0, whereClause, "");
        if (listPosDiv != null && listPosDiv.size()>0){
            for(int i=0; i<listPosDiv.size(); i++){
                PositionDivision posDiv = (PositionDivision)listPosDiv.get(i);
                strIN += posDiv.getPositionId()+", ";
            }
            strIN += "0";
        }
        whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" IN("+strIN+")";
        order = PstPosition.fieldNames[PstPosition.FLD_POSITION];
        vectSize = PstPosition.getCount(whereClause);
        listPosition = PstPosition.list(0, 0, whereClause, order);
    }
    
    if (positionName.equals("0") && validStatusSelect == 0 && levelSelect == 0 && divisionId == 0){
        test = "Show All Without Filter";
        order = PstPosition.fieldNames[PstPosition.FLD_POSITION];
        vectSize = PstPosition.getCount("");
        listPosition = PstPosition.list(start, recordToGet, "", order);
    }

    if (listPosition != null && listPosition.size()>0){
        String trCSS = "tr1";
        %>
        <div style="color:#575757; font-size: 13px; padding: 7px 11px; border-left: 1px solid #007592; margin: 7px 0px; background-color: #FFF;"><%=test%></div>
        <table class="tblStyle">
            <tr>
                <td class="title_tbl" style="background-color: #DDD;">No</td>
                <td class="title_tbl" style="background-color: #DDD;">Title</td>
                <td class="title_tbl" style="background-color: #DDD;">Level</td>
                <td class="title_tbl" style="background-color: #DDD;">Job Description</td>
                <td class="title_tbl" style="background-color: #DDD;">Valid Status</td>
                <td class="title_tbl" style="background-color: #DDD;">Action</td>
            </tr>
        <%
        for(int i=0; i<listPosition.size(); i++){
            Position position = (Position)listPosition.get(i);
            if (i % 2 == 0){
                trCSS = "tr1";
            } else {
                trCSS = "tr2";
            }
            %>
            <tr class="<%= trCSS %>">
                <td><%= (i+1) %></td>
                <td><%= position.getPosition() %></td>
                <td>
                    <%
                    String strLevel = "-";
                    try {
                        Level lev = PstLevel.fetchExc(position.getLevelId());
                        strLevel = lev.getLevel();
                    } catch(Exception e){
                        System.out.println(""+e.toString());
                    }
                    %>
                    <%= strLevel %>
                </td>
                <td>
                    <%
                    if (position.getDescription().length()>2){
                    %>
                        <a href="javascript:cmdOpenDesc('<%=position.getOID()%>')">View Description</a>
                    <%
                    } else {
                        %>-<%
                    }
                    %>
                </td>
                <td>
                    <%= PstPosition.validStatusValue[position.getValidStatus()] %>
                </td>
                <td>
                    <% if(privUpdate){ %>
                    <a class="btn-small-1" style="color:#575757;" href="javascript:cmdEdit('<%=position.getOID()%>')">Edit</a>
                    <% } %>   
                    &nbsp;
                    <% if(privDelete){ %>
                    <a class="btn-small-1" style="color:#575757;" href="javascript:cmdAsk('<%=position.getOID()%>')">Delete</a>
                    <% } %>
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
                List : <%=start+1%> &HorizontalLine; <%= (start+recordToGet) %> | 
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