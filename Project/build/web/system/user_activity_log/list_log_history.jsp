<%-- 
    Document   : list_log_history
    Created on : May 17, 2016, 4:55:56 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.log.LogSysHistory"%>
<%@page import="com.dimata.harisma.entity.log.PstLogSysHistory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<%
/* parameter of filter */
long companyId = FRMQueryString.requestLong(request, "company_id");
long divisionId = FRMQueryString.requestLong(request, "division_id");
long departmentId = FRMQueryString.requestLong(request, "department_id");
long sectionId = FRMQueryString.requestLong(request, "section_id");
String dateFrom = FRMQueryString.requestString(request, "start_date");
String dateTo = FRMQueryString.requestString(request, "end_date");
long userId = FRMQueryString.requestLong(request, "user_id");
int status = FRMQueryString.requestInt(request, "cb_status");
String module = FRMQueryString.requestString(request, "modul");
StructureModule structureModule = new StructureModule();
Vector listLogSysHistory = new Vector();
String whereClause = "";
Vector<String> whereCollect = new Vector<String>();
String orderBy = "";
if (!(dateFrom.equals(""))&& !(dateTo.equals(""))){
    whereClause = " "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_UPDATE_DATE]+" BETWEEN '"+dateFrom+"' AND '"+dateTo+"' ";
    whereCollect.add(whereClause);
    orderBy = ""+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_UPDATE_DATE];
}
if (userId != 0){
    whereClause = " "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_USER_ID]+"="+userId+" ";
    whereCollect.add(whereClause);
}
if (status != 0){
    whereClause = " "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_STATUS]+"="+status+" ";
    whereCollect.add(whereClause);
}
if (!module.equals("")){
    whereClause = " "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_MODULE]+"='"+module+"' ";
    whereCollect.add(whereClause);
}
if (companyId != 0){
    whereClause = " "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_COMPANY_ID]+"="+companyId;
    whereCollect.add(whereClause);
}
if (divisionId != 0){
    whereClause = " "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_DIVISION_ID]+"="+divisionId;
    whereCollect.add(whereClause);
}
if (departmentId != 0){
    whereClause = " "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_DEPARTMENT_ID]+"="+departmentId;
    whereCollect.add(whereClause);
}
if (sectionId != 0){
    whereClause = " "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_SECTION_ID]+"="+sectionId;
    whereCollect.add(whereClause);
}
if (whereCollect != null && whereCollect.size()>0){
    whereClause = "";
    for (int i=0; i<whereCollect.size(); i++){
        String where = (String)whereCollect.get(i);
        whereClause += where;
        if (i < (whereCollect.size()-1)){
             whereClause += " AND ";
        }
    }
}
listLogSysHistory = PstLogSysHistory.list(0, 0, whereClause, orderBy);
%>

    <%
    if (listLogSysHistory != null && listLogSysHistory.size()>0){
        %>
    <div id="menu_utama">
        <span id="menu_title">
            Daftar Data Hasil Pencarian
            <span style="font-size: 11px; color: #474747;">(Klik Detail untuk melakukan approve)</span>
        </span>
    </div>
    <table class="tblStyle">
    <tr>
        <td class="title_tbl">No</td>
        <td class="title_tbl">Date</td>
        <td class="title_tbl">User</td>
        <td class="title_tbl">Module</td>
        <td class="title_tbl"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></td>
        <td class="title_tbl"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></td>
        <td class="title_tbl"><%=dictionaryD.getWord(I_Dictionary.SECTION)%></td>
        <td class="title_tbl">Action</td>
        <td class="title_tbl">More Detail</td>
        <td class="title_tbl">Status</td>
    </tr>
        <%
        for (int i=0; i<listLogSysHistory.size(); i++){
            LogSysHistory logSysHistory = (LogSysHistory) listLogSysHistory.get(i);
            String styleAction = "";
            String trCSS = "tr1";
            String moreDetail = "";
            if (logSysHistory.getLogUserAction().equals("ADD")){
                styleAction = "style_add";
                moreDetail = "<a href=\"javascript:cmdDetail('" + logSysHistory.getOID() +"')\">Detail</a>";
            }
            if (logSysHistory.getLogUserAction().equals("EDIT")){
                styleAction = "style_edit";
                moreDetail = "<a href=\"javascript:cmdDetail('" + logSysHistory.getOID() +"')\">Detail</a>";
            }
            if (logSysHistory.getLogUserAction().equals("DELETE")){
                styleAction = "style_delete";
                moreDetail = "<a href=\"javascript:cmdDetail('" + logSysHistory.getOID() +"')\">Detail</a>";
            }
            if (logSysHistory.getLogUserAction().equals("LOGIN")){
                styleAction = "style_login";
                moreDetail = "";
            }
            if (i % 2 == 0){
                trCSS = "tr1";
            } else {
                trCSS = "tr2";
            }
            if (logSysHistory.getLogStatus() == I_DocStatus.DOCUMENT_STATUS_FINAL){
                trCSS = "tr3";
            }
            if (logSysHistory.getLogStatus() == I_DocStatus.DOCUMENT_STATUS_CANCELLED){
                trCSS = "tr4";
            }
            %>
            <tr class="<%= trCSS %>">
                <td><%=(i+1)%></td>
                <td><%= ""+logSysHistory.getLogUpdateDate() %></td>
                <td><%=logSysHistory.getLogLoginName()%></td>
                <td><%= logSysHistory.getLogModule() %></td>
                <td><%= structureModule.getDivisionName(logSysHistory.getDivisionId()) %></td>
                <td><%= structureModule.getDepartmentName(logSysHistory.getDepartmentId()) %></td>
                <td><%= structureModule.getSectionName(logSysHistory.getSectionId()) %></td>
                <td><%= "<div id=\""+styleAction+"\">"+logSysHistory.getLogUserAction()+"</div>" %></td>
                <td><%= moreDetail %></td>
                <td><strong><%= ""+I_DocStatus.fieldDocumentStatus[logSysHistory.getLogStatus()] %></strong></td>
            </tr>
            <%
        }
        %>
    </table>
        <%
    } else {
    %>
    <h2>There is no data available!</h2>
    <%
    }
    %>
