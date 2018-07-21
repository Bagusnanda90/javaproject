<%-- 
    Document   : grade_level_ajax
    Created on : Jan 28, 2016, 4:28:18 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlGradeLevel"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_GRADE_LEVEL);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String gradeName = FRMQueryString.requestString(request, "grade_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String whereClause = "";
    String order = "";
    Vector listGradeLevel = new Vector();
    
    CtrlGradeLevel ctrlGradeLevel = new CtrlGradeLevel(request);
    
    int recordToGet = 20;
    int vectSize = 0;
    vectSize = PstGradeLevel.getCount("");
    if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
            || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
        start = ctrlGradeLevel.actionList(iCommand, start, vectSize, recordToGet);
    }

    if (!(gradeName.equals("0"))){
        whereClause = PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_CODE]+" LIKE '%"+gradeName+"%'";
        order = PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_CODE];
        vectSize = PstGradeLevel.getCount(whereClause);
        listGradeLevel = PstGradeLevel.list(0, 0, whereClause, order);        
    } else {
        vectSize = PstGradeLevel.getCount("");
        order = PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_CODE];
        listGradeLevel = PstGradeLevel.list(start, recordToGet, "", order);
    }

    if (listGradeLevel != null && listGradeLevel.size()>0){
    %>
    <table class="tblStyle">
        <tr>
            <td class="title_tbl" style="background-color: #DDD;">No</td>
            <td class="title_tbl" style="background-color: #DDD;">Grade Level</td>
            <td class="title_tbl" style="background-color: #DDD;">Action</td>
        </tr>
        <%
        for (int i = 0; i < listGradeLevel.size(); i++) {
            GradeLevel gradeLevel = (GradeLevel) listGradeLevel.get(i);
            %>
            <tr>
                <td><%=(i+1)%></td>
                <td><%= gradeLevel.getCodeLevel() %></td>
                <td>
                    <% if(privUpdate){ %>
                    <a class="btn-small-1" style="color:#575757;" href="javascript:cmdEdit('<%=gradeLevel.getOID()%>')">Edit</a>
                    <% } %>
                    &nbsp;
                    <% if(privDelete){ %>
                    <a class="btn-small-1" style="color:#575757;" href="javascript:cmdAsk('<%=gradeLevel.getOID()%>')">Delete</a>
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