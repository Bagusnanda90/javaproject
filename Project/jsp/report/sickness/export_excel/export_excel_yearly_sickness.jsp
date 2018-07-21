<%-- 
    Document   : export_excel_lateness_yearly_report
    Created on : Sep 16, 2015, 1:54:01 PM
    Author     : khirayinnura
--%>

<%@page import="com.dimata.harisma.session.sickness.SicknessMonthlyRekap"%>
<%@page import="com.dimata.harisma.session.sickness.SicknessYearly"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../../../main/javainit.jsp" %>

<!DOCTYPE html>

<%
    Vector listResult = new Vector(1, 1);
    Hashtable<String, SicknessYearly> hSicknessYearly = new Hashtable();
   // int year = session.getValue("year");
    

    if(session.getValue("listresult")!=null){
        hSicknessYearly = (Hashtable)session.getValue("listresult"); 
    }    
%>
<%@page contentType="application/x-msexcel" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
        <!-- #EndEditable -->
        
        <style type="text/css">
            .tHeader {
                text-align: center;
            } 
        </style>
    </head>
    <body>
        <table width="100%" border="1">
           <!-- <tr>
                <td>-->
                    <!--%=drawList(out, hLatenessYearly)%-->
                <!--</td>
            </tr>-->
            <tr class="tHeader">
                <td rowspan="2">
                    No
                </td>
                <td rowspan="2">
                    Payroll
                </td>
                <td rowspan="2">
                    Employee
                </td>
                <td colspan="12">
                    Duration (hour, minutes)
                </td>
                <td rowspan="2">
                    Total
                </td>
            </tr>
            <tr class="tHeader">
                <%
                    
                    int jml = 1;
        
                    for (int y = 0; y < 12; y++) {
                %>
                <td>
                    <%="" + jml++%>
                </td>
                <% } %>
            </tr>
            
            <%
                Enumeration numHash;
                String str;
                jml = 1;

                numHash = hSicknessYearly.keys();
                while (numHash.hasMoreElements()) {

                    int total = 0;
                    int perMonth = 0;

                    str = (String) numHash.nextElement();
                    /*System.out.println(str + ": " +
                     hLatenessYearly.get(str));*/

                    SicknessYearly lateYear = (SicknessYearly) hSicknessYearly.get(str);
                    
                    %>
                <tr>
                    <td>
                        <%=""+jml++%>
                    </td>
                    <td>
                        <%=lateYear.getEmpNum()%>
                    </td>
                    <td>
                        <%=lateYear.getEmpName()%>
                    </td>
                    <%

                    for (int m = 0; m < lateYear.getTotalMonthly().size(); m++) {
                        SicknessMonthlyRekap monthlyLate = (SicknessMonthlyRekap) lateYear.getTotalMonthly().get(m);
                        if (monthlyLate.getTotalMonth() != 0) {
                            perMonth = monthlyLate.getTotalMonth();
                    %>
                    <td>
                        <%=monthlyLate.getTotalMonth()%>
                    </td>
                    <% 
                        total = total + perMonth;
                    } else { %>
                    <td>
                        -
                    </td>
                    <%
                        }
                    }

                    if(total != 0){ %>
                    <td>
                        <%=total%>
                    </td>
                    <% } else { %>
                    <td>
                        -
                    </td>
                    <%}%>
                </tr>
                <%}%>
        </table>
    </body>
</html>
