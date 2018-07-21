<%-- 
    Document   : modul_ajax
    Created on : 19-Apr-2016, 14:42:28
    Author     : Acer
--%>

<%@page import="com.dimata.harisma.entity.log.LogSysHistory"%>
<%@page import="com.dimata.harisma.entity.log.PstLogSysHistory"%>
<%@page import="com.dimata.harisma.entity.admin.AppUser"%>
<%@page import="com.dimata.harisma.entity.admin.PstAppUser"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String modulName = FRMQueryString.requestString(request, "modul_name");
    int iCommand = FRMQueryString.requestCommand(request);
    Vector listModul = new Vector();
    String whereClause = "LOG_MODULE IS NOT NULL";
    String order = "";
    
    if (modulName.equals("0")){
        listModul = PstLogSysHistory.listModule(0, 0, whereClause, "");
    } else {
        whereClause = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_MODULE]+" LIKE '%"+modulName+"%'";
        listModul = PstLogSysHistory.listModule(0, 0, whereClause, ""); 
    }
%>

<table class="tblStyle">
    <tr>
        <td class="title_tbl">No</td>
        <td class="title_tbl">Modul.</td>

    </tr>
    <%
    if (listModul != null && listModul.size()>0){
        for (int i=0; i<listModul.size(); i++){
            String log = (String)listModul.get(i);
            %>
            <tr>
                <td><%=i+1%></td>
                <td><div id="uname" onclick="javascript:cmdGetItem('<%= log %>')"><%= log %></div></td>
            </tr>
            <%
        }
    }
    %>
</table>
