<%-- 
    Document   : candidate_approve_source
    Created on : Sep 9, 2015, 5:45:09 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.employee.FrmCandidateMain"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String employeeName = FRMQueryString.requestString(request, "employee_name");
    String approveNo = FRMQueryString.requestString(request, "id");
    
    String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE '%"+employeeName+"%'";
    String order = ""+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];
    Vector listEmployee = new Vector();
    if (employeeName.length() > 0){
        listEmployee = PstEmployee.list(0, 0, whereClause, order);
    } else {
        listEmployee = PstEmployee.list(0, 0, "", order);
    }

    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Approve By</title>
        <style type="text/css">
            body {
                color:#575757;
                font-size: 11px;
                font-family: sans-serif;
                background-color: #F5F5F5;
            }
            .item {
                padding: 3px;
                border:1px solid #CCC;
                border-radius: 3px;
                background-color: #EEE;
                margin: 3px;
                cursor: pointer;
            }
            .teks {
                font-size: 11px;
                color:#474747;
                padding: 5px; 
                border:1px solid #CCC;
                border-radius: 3px;
                margin: 3px;
            }
        </style>
        <script language="javascript">
            function cmdGetItem(oid) {
                var input_val = "FRM_FIELD_APPROVE_BY_ID_1";
                
                self.opener.document.frm.approve_1.value=oid;
                self.opener.document.frm.submit();
            }
        </script>
    </head>
    <body>
        <h1 style="border-bottom: 1px solid #0599ab; padding-bottom: 12px;">Approve By</h1>
        <form method="post" name="frmApp" action="">
            <input class="teks" placeholder="search employee..." type="text" name="employee_name" size="50" />
        </form>
        <%
        if (listEmployee != null && listEmployee.size()>0){
            for(int i=0; i<listEmployee.size(); i++){
                Employee employee = (Employee)listEmployee.get(i);
                %>
                <div class="item" onclick="cmdGetItem('<%=employee.getOID()%>')"><%=employee.getFullName()%></div>
                <%
            }
        }
        %>
    </body>
</html>