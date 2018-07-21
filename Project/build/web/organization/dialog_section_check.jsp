<%-- 
    Document   : dialog_section_check
    Created on : Apr 26, 2016, 1:53:34 PM
    Author     : Dimata 007
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<!DOCTYPE html>
<%
    long oidTemp = FRMQueryString.requestLong(request, "oid_temp");
    Vector listSection = PstSection.list(0, 0, "", PstSection.fieldNames[PstSection.FLD_SECTION]);
    String whereClause = PstOrgMapSection.fieldNames[PstOrgMapSection.FLD_TEMPLATE_ID]+"="+oidTemp;
    Vector listOrgMSect = PstOrgMapSection.list(0, 0, whereClause, "");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select Section</title>
        <style type="text/css">
            body {
                color: #FFF;
                font-size: 12px;
                font-family: sans-serif;
                background-color: #CCB889;
                padding: 0;
                margin: 0;
            }
            .header {
                color: #998453;
                padding: 21px;
                position: fixed;
                background-color: #FFF;
                border-bottom: 1px solid #A89567;
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
                self.opener.document.frm.section_id.value = oid;
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
            <h1>Section Checked</h1>
        </div>
        <div class="content">
            <div style="padding-top: 100px; ">&nbsp;</div>
            <table>
            <%
            boolean same = false;
            if (listSection != null && listSection.size()>0){
                for (int i=0; i<listSection.size(); i++){
                    Section section = (Section)listSection.get(i);
                    same = false;
                    if (listOrgMSect != null && listOrgMSect.size()>0){
                        for (int j=0; j<listOrgMSect.size(); j++){
                            OrgMapSection orgMSect = (OrgMapSection)listOrgMSect.get(j);
                           if (section.getOID() == orgMSect.getSectionId()){
                               same = true;
                               break;
                           }
                        }
                    }
                    if (same == true){
                        %>
                        <tr>
                            <td><input type="checkbox" disabled="disabled" checked="checked" /></td>
                            <td><strong style="text-decoration: line-through;"><%= section.getSection() %></strong></td>
                        </tr>
                        <%
                    } else {
                        %>
                        <tr>
                            <td><input type="checkbox" id="check_<%=i%>" name="section_ids" onclick="javascript:cmdGetItem(this.value, '<%= i %>')" value="<%= section.getOID() %>" /></td>
                            <td><strong id="title_<%=i%>"><%= section.getSection() %></strong></td>
                        </tr>
                        <%
                    }
                }
            }
            %>
            </table>
        </div>
    </body>
</html>