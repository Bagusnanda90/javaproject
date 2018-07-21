<%-- 
    Document   : department_ajax
    Created on : Jan 7, 2016, 5:08:42 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDepartment"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DEPARTMENT);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String departName = FRMQueryString.requestString(request, "depart_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String whereClause = "";
    String order = "";
    Vector listDepartment = new Vector();
    
    CtrlDepartment ctrlDepart = new CtrlDepartment(request);
    
    int recordToGet = 10;
    int vectSize = 0;
    vectSize = PstDepartment.getCount("");
    if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
            || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
        start = ctrlDepart.actionList(iCommand, start, vectSize, recordToGet);
    }

    if (!(departName.equals("0"))){
        whereClause = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" LIKE '%"+departName+"%'";
        order = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
        vectSize = PstDepartment.getCount(whereClause);
        listDepartment = PstDepartment.listDepartment(0, 0, whereClause, order);        
    } else {
        vectSize = PstDepartment.getCount("");
        order = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
        listDepartment = PstDepartment.list(start, recordToGet, "", order);
    }
    if (listDepartment != null && listDepartment.size()>0){
        %>
        <table class="tblStyle">
            <tr>
                <td class="title_tbl" style="background-color: #DDD;">No</td>
                <td class="title_tbl" style="background-color: #DDD;"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></td>
                <td class="title_tbl" style="background-color: #DDD;"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></td>
                <td class="title_tbl" style="background-color: #DDD;"><%=dictionaryD.getWord(I_Dictionary.DESCRIPTION)%></td>
                <td class="title_tbl" style="background-color: #DDD;">HOD Join to <%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></td>
                <td class="title_tbl" style="background-color: #DDD;">Valid Status</td>
                <td class="title_tbl" style="background-color: #DDD;">Action</td>
            </tr>
            <%
            for(int i=0; i<listDepartment.size(); i++){
                Department depart = (Department)listDepartment.get(i);
                Division division = new Division();
                try {
                    division = PstDivision.fetchExc(depart.getDivisionId());
                } catch (Exception e) {
                    System.out.println("=>"+e.toString());
                }
                %>
                <tr>
                    <td><%=(i+1)%></td>
                    <td><%=depart.getDepartment()%></td>
                    <td><%=division.getDivision()%></td>
                    <td><%= depart.getDescription() !=null && depart.getDescription().length() > 0 ? depart.getDescription():"-" %></td>
                    <td><%=depart.getJoinToDepartment()!=null ?depart.getJoinToDepartment():"-"%></td>
                    <td><%= PstDepartment.validStatusValue[depart.getValidStatus()] %></td>
                    <td><a style="color: #474747" href="javascript:cmdEdit('<%=depart.getOID()%>')" class="btn-small-1">Edit</a>&nbsp;<a style="color: #474747" href="javascript:cmdAsk('<%=depart.getOID()%>')" class="btn-small-1">Delete</a></td>
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
