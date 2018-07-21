<%-- 
    Document   : choose_employee
    Created on : Feb 18, 2015, 3:13:54 PM
    Author     : Priska
--%>

<%@page import="java.util.Date"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<!DOCTYPE html>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    Date selectedDateFrom = FRMQueryString.requestDateVer3(request, "check_date_start");
    Date selectedDateTo = FRMQueryString.requestDateVer3(request, "check_date_finish");
    String empNum = FRMQueryString.requestString(request, "emp_number");
    String fullName = FRMQueryString.requestString(request, "full_name");
    
    int reason_sts = FRMQueryString.requestInt(request, "reason_status");    
    long oidDepartment = FRMQueryString.requestLong(request, "department");        
    long oidSection = FRMQueryString.requestLong(request, "section");
    
    long oidCompany = FRMQueryString.requestLong(request, "hidden_companyId");
    long oidDivision = FRMQueryString.requestLong(request, "hidden_divisionId");
    Date date = FRMQueryString.requestDate(request, "date");
    
    int vectSize = 0;
    
     //int recordToGet = 40000;

     String sStatusResign = FRMQueryString.requestString(request, "statusResign"); 
    int statusResign=0;
    if(sStatusResign!=null && sStatusResign.length()>0){
        statusResign = Integer.parseInt(sStatusResign); 
    }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose Employee</title>
        <style type="text/css">
            body {
                color:#575757;
                font-size: 11px;
                font-family: sans-serif;
                background-color: #F5F5F5;
                margin: 0;
                padding: 0;
            }
            .item {
                padding: 1px 3px;
                border:1px solid #CCC;
                border-radius: 3px;
                background-color: #EEE;
                margin: 3px;
                cursor: pointer;
            }
            .item:hover {
                background-color: #5CD3FA;
                border:1px solid #41B9E0;
                color: #FFF;
            }
            .item-active {
                background-color: #5CD3FA;
                border:1px solid #41B9E0;
                color: #FFF;
                padding: 1px 3px;
                border-radius: 3px;
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
            .btn {
              background: #C7C7C7;
              border: 1px solid #BBBBBB;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 11px;
              padding: 3px 7px;
              cursor: pointer;
            }

            .btn:hover {
              color: #FFF;
              background: #B3B3B3;
              border: 1px solid #979797;
            }
        </style>
        <script language="javascript">
            function cmdCount(val){
                var nilai = document.getElementById("count").innerHTML;
                var count = parseInt(nilai);
                var valCheck = document.getElementById("checklist"+val).checked;
                if (valCheck == true){
                    document.getElementById("div_item"+val).className="item-active";
                    count = count + 1;
                } else {
                    document.getElementById("div_item"+val).className="item";
                    count = count - 1;
                }
                
                document.getElementById("count").innerHTML=count;
            }
            
        </script>
    </head>
    <body>
        
        <form method="post" name="frm" action="">
            
            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="position: fixed; background-color: #FFF; padding: 17px; border-bottom: 1px solid #CCC;">
                <tr>
                    <td valign="middle" colspan="2">
                        <div style="font-size: 14px;padding-bottom: 5px; font-weight: bold;">List Employee</div>
                        <div><strong id="count">0</strong> Selected</div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input class="teks" placeholder="search employee..." type="text" name="employee_name" size="57" /><button class="btn">Search</button>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input class="teks" placeholder="Subject" name="subject" size="69" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input class="teks" placeholder="Cc" name="cc" size="69" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea class="teks" name="description" cols="50" placeholder="description"></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
                        <button class="btn" style="margin-right: 3px;" onclick="cmdSend()">Send</button>
                    </td>
                </tr>
            </table>
            <div style="padding: 125px 0px;">&nbsp;</div>
            
            
        </form>
        
    </body>
</html>