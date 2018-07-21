<%-- 
    Document   : close_all_end_date
    Created on : Mar 18, 2016, 9:42:39 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.payroll.PstValue_Mapping"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_COMPONENT);%>
<%@ include file = "../../main/checkuser.jsp" %>
<!DOCTYPE html>
<%
int iCommand = FRMQueryString.requestCommand(request);
String message ="";
String compCode = FRMQueryString.requestString(request, "comp_code");

Date dateChange = FRMQueryString.requestDate(request, "date_change");

if(iCommand==Command.SUBMIT)
{
     message = PstValue_Mapping.listUpdateStatusValue(compCode, dateChange);
}

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Close All End Date</title>
        <style type="text/css">
            body {
                font-family: sans-serif;
                color:#575757;
                padding: 21px;
                background-color: #EEE;
            }
            .header {
                
            }
            .content-main {
                padding: 5px 25px 25px 25px;
                margin: 0px 23px 59px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .info {
                font-size: 12px;
                color: #67A2B5;
                padding: 15px;
                margin: 7px 0px;
                border-radius: 3px;
                background-color: #D3ECF5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .box {
                margin: 17px 7px;
                background-color: #FFF;
                color:#575757;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 15px 7px 15px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            select {
                border:1px solid #CCC;
                padding: 5px 7px;
            }
            
        </style>
        <script type="text/javascript">
            function cmdSaveChange(compCode){
                document.frm.command.value="<%=Command.SUBMIT%>";
                document.frm.comp_code.value=compCode;
                document.frm.action="close_all_end_date.jsp";
                document.frm.submit();
            }
            
            function cmdRefresh() {
                self.opener.document.frm.command.value = "<%= Command.NONE %>";                 
                self.opener.document.frm.submit();
            }
        </script>
    </head>
    <body>
        <h1>Close All End Date</h1>
        <form name="frm" method="post" action="">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <input type="hidden" name="comp_code" value="<%= compCode %>">
            <%	
            Date selectedDateChange = dateChange!=null ? dateChange : new Date();
            %>
            <%=ControlDate.drawDate("date_change", selectedDateChange, 0, installInterval) %>
            <div>&nbsp;</div>
            <a class="btn" href="javascript:cmdSaveChange('<%= compCode %>')">Save change</a>
            <a class="btn" href="javascript:cmdRefresh()">Refresh</a>
            <p style="font-size: 11px; background-color: #DEF5BC; color:#759E37; padding: 12px; border-radius: 3px">
                <%= message %>
            </p>
        </form>
    </body>
</html>
