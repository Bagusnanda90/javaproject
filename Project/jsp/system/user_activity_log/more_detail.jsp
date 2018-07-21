<%-- 
    Document   : more_detail
    Created on : May 19, 2016, 4:55:23 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.qdep.db.DBHandler"%>
<%@page import="com.dimata.harisma.entity.log.ChangeValue"%>
<%@page import="com.dimata.harisma.entity.log.DictionaryField"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.log.PstLogSysHistory"%>
<%@page import="com.dimata.harisma.entity.log.LogSysHistory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<style type="text/css">
    body {
        color: #575757;
        margin: 0;
        padding: 0;
        font-size: 12px;
        font-family: sans-serif;
    }
    .tblStyle {border-collapse: collapse; }
    .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; }
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}

    .content-main {
        padding: 21px;
    }
    .btn {
        background-color: #DDD;
        border-radius: 3px;
        font-family: Arial;
        border-radius: 3px;
        color: #575757;
        font-size: 12px;
        padding: 7px 11px 7px 11px;
        text-decoration: none;
    }

    .btn:hover {
        color: #474747;
        background-color: #CCC;
        text-decoration: none;
    }
    #style_add {
        font-weight: bold;
        color: #677A1F;
        font-size: 11px;
        background-color: #E8F5BA;
        padding: 3px 5px;
        border-radius: 3px;
    }
    #style_edit {
        font-weight: bold;
        color: #257865;
        font-size: 11px;
        background-color: #BAF5E7;
        padding: 3px 5px;
        border-radius: 3px;
    }
    #style_delete {
        font-weight: bold;
        color: #8F2F3A;
        font-size: 11px;
        background-color: #F5CBD0;
        padding: 3px 5px;
        border-radius: 3px;
    }
    #style_login {
        font-weight: bold;
        color: #B83916;
        font-size: 11px;
        background-color: #FFD5C9;
        padding: 3px 5px;
        border-radius: 3px;
    }
    .header {
        position: fixed;
        width: 100%;
        padding: 21px;
        color:#FFF;
        border-top: 1px solid #CCC;
        border-bottom: 1px solid #CCC;
        background-color: #EEE;
    }
    #inp {
        color: #474747;
        border: 1px solid #CCC;
        border-radius: 3px;
        padding: 5px 7px;
    }
</style>
<script type="text/javascript">
    function cmdPerintah(){
        document.frm.perintah.value="1";
        document.frm.action="more_detail.jsp";
        document.frm.submit();
    }
    function cmdApprove(oid, act){
        document.frm.oid_log.value=oid;
        document.frm.action_param.value=act;
        document.frm.action="more_detail.jsp";
        document.frm.submit();
    }
</script>
<%!
    public String parsingData(String data){
        String str = "";
        str = data.replaceAll(";", "<br>");
        return str;
    }
    
    public Vector<String> parsingDataVersi2(String data){
        Vector<String> vdata = new Vector();
        for (String retval : data.split(";")) {
            vdata.add(retval);
        }
        return vdata;
    }
    
    public String getTableDetail(String field, String prev, String curr){
        DictionaryField dictionaryField = new DictionaryField();
        dictionaryField.loadWord();
        ChangeValue changeValue = new ChangeValue();
        String table = "";
        Vector vField = parsingDataVersi2(field);
        Vector vPrev = parsingDataVersi2(prev);
        Vector vCurr = parsingDataVersi2(curr);
        table = "<table class=\"tblStyle\">";
        table += "<tr>";
        table += "<td class=\"title_tbl\">Field</td><td class=\"title_tbl\">Previous</td><td class=\"title_tbl\">Current</td>";
        table += "</tr>";
        if (vField != null && vField.size()>0){
            for(int i=1; i<vField.size(); i++){
                String dataField = (String)vField.get(i);
                String dataPrev = (String)vPrev.get(i);
                String dataCurr = (String)vCurr.get(i);
                String bg = "";
                if (dataPrev.equals(dataCurr)){
                    bg = "#FFF";
                } else {
                    bg = "#F5CBD0";
                }
                table += "<tr style=\"background-color: "+bg+"\">";
                table += "<td><strong>"+dictionaryField.getWord(dataField)+"</strong></td>";
                table += "<td>"+changeValue.getStringValue(dataField, dataPrev)+"</td>";
                table += "<td>"+changeValue.getStringValue(dataField, dataCurr)+"</td>";
                table += "</tr>";
            }
        }
        table += "</table>";
        return table;
    }
    
%>
<%
long logSysHistoryOid = FRMQueryString.requestLong(request, "oid");
long oidLog = FRMQueryString.requestLong(request, "oid_log");
int actionParam = FRMQueryString.requestInt(request, "action_param");
String approveNote = FRMQueryString.requestString(request, "approve_note");
int statusUpdate = 0;
String queryUp = "";
Date now = new Date();
if (oidLog != 0 && actionParam != 0){
    if (actionParam == 1){
        statusUpdate = PstLogSysHistory.approveLog(appUserIdSess, ""+now, approveNote, oidLog);
    }
    if (actionParam == 2){
        statusUpdate = PstLogSysHistory.notApproveLog(appUserIdSess, ""+now, approveNote, oidLog);
    }
}

LogSysHistory logSysHistory = new LogSysHistory();
if (logSysHistoryOid != 0){
    logSysHistory = PstLogSysHistory.fetchExc(logSysHistoryOid);
}
%>
<% if (logSysHistoryOid != 0){ %>
<form name="frm" action="">
    <input type="hidden" name="oid_log" value="" />
    <input type="hidden" name="action_param" value="" />
<div class="header">
    <h1 style="padding-top: 7px; color: #007592;">Detail Log System History</h1>
    <%
    if (logSysHistory != null){
        if (logSysHistory.getLogStatus() != I_DocStatus.DOCUMENT_STATUS_FINAL){
            %>
            <a href="javascript:cmdApprove('<%= logSysHistoryOid %>', '1')" class="btn">Approve</a>
            <a href="javascript:cmdApprove('<%= logSysHistoryOid %>', '2')" class="btn">Not Approve</a>
            <div>&nbsp;</div>
            <input id="inp" type="text" name="approve_note" size="70" placeholder="Alasan approve atau tidak approve..." value="" />
            <%
        }
    }
    %>
    
    <div>&nbsp;</div>
</div>
<div style="padding: 77px">&nbsp;</div>
<div class="content-main">
    
    
        
        <%
            if (logSysHistory != null){
                String styleAction = "";
                if (logSysHistory.getLogUserAction().equals("ADD")){
                    styleAction = "style_add";
                }
                if (logSysHistory.getLogUserAction().equals("EDIT")){
                    styleAction = "style_edit";
                }
                if (logSysHistory.getLogUserAction().equals("DELETE")){
                    styleAction = "style_delete";
                }
                if (logSysHistory.getLogUserAction().equals("LOGIN")){
                    styleAction = "style_login";
                }
                %>
                <table style="padding: 0px; margin: 0px; font-size: 12px;">
                    <tr>
                        <td><strong>Module</strong></td>
                        <td> : <%= logSysHistory.getLogModule() %></td>
                    </tr>
                    <tr>
                        <td><strong>Action</strong></td>
                        <td> : <strong id="<%= styleAction %>"><%=logSysHistory.getLogUserAction()%></strong></td>
                    </tr>
                    <tr>
                        <td><strong>Status</strong></td>
                        <td> : <%= I_DocStatus.fieldDocumentStatus[logSysHistory.getLogStatus()] %></td>
                    </tr>
                </table>
                <div>&nbsp;</div>
                <%= getTableDetail(logSysHistory.getLogDetail(), logSysHistory.getLogPrev(), logSysHistory.getLogCurr()) %>
                <%
            }
        %>
    
</div>
</form>
<% } %>
<%
if (statusUpdate != 0){
    %>
    <div id="style_add">Data telah diperbarui ! Silahkan klik tombol Search untuk me-refresh hasil pencarian.</div>
    <strong><%= queryUp %></strong>
    <%
}
%>