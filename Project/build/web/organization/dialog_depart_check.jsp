<%-- 
    Document   : dialog_depart_check
    Created on : Apr 26, 2016, 1:53:17 PM
    Author     : Dimata 007
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<!DOCTYPE html>
<%
    long oidTemp = FRMQueryString.requestLong(request, "oid_temp");
    Vector listDepartment = PstDepartment.list(0, 0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
    String whereClause = PstOrgMapDepartment.fieldNames[PstOrgMapDepartment.FLD_TEMPLATE_ID]+"="+oidTemp;
    Vector listOrgMDept = PstOrgMapDepartment.list(0, 0, whereClause, "");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select Department</title>
        <style type="text/css">
            body {
                color: #FFF;
                font-size: 12px;
                font-family: sans-serif;
                background-color: #91B07D;
                padding: 0;
                margin: 0;
            }
            .header {
                color: #5B8540;
                padding: 21px;
                position: fixed;
                background-color: #FFF;
                border-bottom: 1px solid #6A8A55;
                width: 100%;
            }
            .content {
                padding: 21px;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            .footer {
                display: inline;
                position: fixed;
            }
        </style>
        <script type="text/javascript">
            function cmdGetItem(oid, index) {
                self.opener.document.frm.department_id.value = oid;
                self.opener.document.frm.template_id.value = "<%= oidTemp %>";
                self.opener.document.frm.action="";
                document.getElementById("check_"+index).disabled = true;
                var str = document.getElementById("title_"+index).innerHTML;
                var result = str.strike();
                document.getElementById("title_"+index).innerHTML = result;
                self.opener.document.frm.submit();
            }
        </script>
    </head>
    <body>
        <div class="header">
            <h1>Department Checked</h1>
        </div>
        <div class="content">
            <div style="padding-top: 100px; ">&nbsp;</div>
            <table>
            <%
            boolean same = false;
            if (listDepartment != null && listDepartment.size()>0){
                for (int i=0; i<listDepartment.size(); i++){
                    Department depart = (Department)listDepartment.get(i);
                    same = false;
                    if (depart.getValidStatus() != 0){
                        if (listOrgMDept != null && listOrgMDept.size()>0){
                            for(int j=0; j<listOrgMDept.size(); j++){
                                OrgMapDepartment orgMDepart = (OrgMapDepartment)listOrgMDept.get(j);
                                if (depart.getOID() == orgMDepart.getDepartmentId()){
                                    same = true;
                                    break;
                                }
                            }
                        }
                        if (same == true){
                            %>
                            <tr>
                                <td><input type="checkbox" disabled="disabled" checked="checked" /></td>
                                <td><strong style="text-decoration: line-through;"><%= depart.getDepartment() %></strong></td>
                            </tr>
                            <%
                        } else {
                            %>
                            <tr>
                                <td><input type="checkbox" id="check_<%=i%>" name="depart_ids" onclick="javascript:cmdGetItem(this.value, '<%= i %>')" value="<%= depart.getOID() %>" /></td>
                                <td><strong id="title_<%=i%>"><%= depart.getDepartment() %></strong></td>
                            </tr>
                            <%
                        }
                    }
                }
            }
            %>
            </table>
        </div>
    </body>
</html>