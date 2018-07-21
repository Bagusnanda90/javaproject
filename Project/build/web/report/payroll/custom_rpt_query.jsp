<%-- 
    Document   : custom_rpt_query
    Created on : Apr 1, 2015, 9:50:22 AM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.harisma.entity.payroll.CustomRptConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptConfig"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            h1 {color: #575757; border-bottom: 1px solid #EEE; padding-bottom: 9px;}
            code {background-color: #EEE; color: #575757; padding: 5px;}
        </style>
        <script type="text/javascript">
            
        </script>
    </head>
    <body>
        <h1>Query Result</h1>
<%
String str = "SELECT ";
String whereList = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_CONFIG_TABLE_NAME = 'hr_employee' AND RPT_MAIN_ID =504404585605045609";
String orderBy = "RPT_CONFIG_SHOW_IDX ASC";
Vector listField = PstCustomRptConfig.list(0, 0, whereList, orderBy);
String join = "";
if (listField != null && listField.size() > 0){
    for(int i=0; i<listField.size(); i++){
        CustomRptConfig rpt = (CustomRptConfig)listField.get(i);
        
        str += rpt.getRptConfigFieldName();
        if (i == listField.size()-1){
            str +=" ";
        } else {
            str += ", ";
        }
        
    }
}
for(int k =0; k<PstCustomRptConfig.joinData.length; k++){
    join += PstCustomRptConfig.joinData[k]+" ";
}
str += "<br />FROM hr_employee<br />"+join;
%>

    </body>
</html>
