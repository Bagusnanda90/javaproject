<%-- 
    Document   : idcard
    Created on : 26-Jul-2016, 13:51:48
    Author     : Acer
--%>
<%@page import="com.dimata.harisma.entity.log.PstLogSysHistory"%>
<%@page import="com.dimata.harisma.entity.log.LogSysHistory"%>
<%@ include file = "../../../main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
  public Vector<String> parsingDataVersi2(String data){
        Vector<String> vdata = new Vector();
        for (String retval : data.split(";")) {
            vdata.add(retval);
        }
        return vdata;
    }  
%>
<%
        long employeeId = FRMQueryString.requestLong(request, "oid");
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
            try{
            logSysHistory = PstLogSysHistory.fetchExc(logSysHistoryOid);
            } catch (Exception exc){
                
            }
        }
        String whereClause = "";
        String order = "";
        int recordToGet = 0;
        int vectSize = 0;

    Vector listLogHistory = new Vector();
    
    whereClause = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_EDITED_EMPLOYEE_ID]+" = '"+employeeId+"' AND " + PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DETAIL] + " LIKE '%CARD%'";
    vectSize = PstEmpAward.getCount(whereClause);
    listLogHistory = PstLogSysHistory.list(0, 0, whereClause, order);        
    if (listLogHistory != null && listLogHistory.size()>0){
%>
<table id="listEmpEducation" class="table table-striped" border="0">
            <thead>
                <tr>
                    
                    <th>ID Card Number</th>
                    <th>Valid Until</th>
                </tr>
            </thead>
            <%
                Employee employee = new Employee();
                 try {
                     
                     employee = PstEmployee.fetchExc(employeeId);
                 } catch (Exception exc){
                
                 }   
            %>
            <tr>
                
                <td><%= employee.getIndentCardNr()%></td>
                <td><%= employee.getIndentCardValidTo() %></td>
            <%
            String idCard = "";
            String valid = "";
            for (int i = 0; i < listLogHistory.size(); i++) {
                    LogSysHistory logSys = (LogSysHistory) listLogHistory.get(i);
                    
                    Vector data = parsingDataVersi2(logSys.getLogDetail());
                    
                    for (int n = 0; n < data.size(); n++){
                        Vector logCur = parsingDataVersi2(logSys.getLogCurr());
                        if(data.get(n).equals("INDENT_CARD_NR")){
                            idCard = logCur.get(n).toString();
                        }
                        if(data.get(n).equals("INDENT_CARD_VALID_TO")){
                            valid = logCur.get(n).toString();
                        }
                    }

                    
            %>
            <tr>
                
                <td><%= idCard%></td>
                <td><%= valid %></td>
                <%
            }
            %>
        </table>
<% } else { %>
            <table id="listEmpEducation" class="table table-bordered table-striped">
            <thead>
                <tr>
                    
                    <th>ID Card Number</th>
                    <th>Valid Until</th>
                </tr>
            </thead>
            <%
                Employee employee = new Employee();
                 try {
                     
                     employee = PstEmployee.fetchExc(employeeId);
                 } catch (Exception exc){
                
                 }   
            %>
            <tr>
                
                <td><%= employee.getIndentCardNr()%></td>
                <td><%= employee.getIndentCardValidTo() %></td>
            
        </table>
        <% }  %>   
        