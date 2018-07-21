<%-- 
    Document   : dialog_struct
    Created on : Apr 25, 2016, 10:39:10 PM
    Author     : Dimata 007
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<!DOCTYPE html>
<%
    long oidTemp = FRMQueryString.requestLong(request, "oid_temp");
    Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
    String whereClause = PstOrgMapDivision.fieldNames[PstOrgMapDivision.FLD_TEMPLATE_ID]+"="+oidTemp;
    Vector listOrgMDiv = PstOrgMapDivision.list(0, 0, whereClause, "");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select Division</title>
        <style type="text/css">
            body {
                color: #FFF;
                font-size: 12px;
                font-family: sans-serif;
                background-color: #ABCDD9;
                padding: 0;
                margin: 0;
            }
            .header {
                color: #377387;
                padding: 21px;
                position: fixed;
                background-color: #FFF;
                border-bottom: 1px solid #377387;
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
                self.opener.document.frm.division_id.value = oid;
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
            <h1>Division Checked</h1>
        </div>
        <div class="content">
            <form name="frm" method="post" action="">
            <div style="padding-top: 70px; ">&nbsp;</div>
            <table>
            <%
            boolean same = false;
            if (listDivision != null && listDivision.size()>0){
                for (int i=0; i<listDivision.size(); i++){
                    Division divisi = (Division)listDivision.get(i);
                    same = false;
                    if (divisi.getValidStatus() != 0){
                        if (listOrgMDiv != null && listOrgMDiv.size()>0){
                            for(int j=0; j<listOrgMDiv.size(); j++){
                                OrgMapDivision orgMDiv = (OrgMapDivision)listOrgMDiv.get(j);
                                if (divisi.getOID() == orgMDiv.getDivisionId()){
                                    same = true;
                                    break;
                                }
                            }
                        }
                        if (same == true){
                            %>
                            <tr>
                                <td><input type="checkbox" disabled="disabled" checked="checked" /></td>
                                <td><strong style="text-decoration: line-through;"><%= divisi.getDivision() %></strong></td>
                            </tr>
                            <%
                        } else {
                            %>
                            <tr>
                                <td><input type="checkbox" id="check_<%=i%>" name="divisi_ids" onclick="javascript:cmdGetItem(this.value, '<%= i %>')" value="<%= divisi.getOID() %>" /></td>
                                <td><strong id="title_<%=i%>"><%= divisi.getDivision() %></strong></td>
                            </tr>
                            <%
                        }
                    }
                }
            }
            %>
            </table>
            </form>
        </div>
    </body>
</html>