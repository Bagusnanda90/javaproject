<%-- 
    Document   : export_excel_lateness_yearly_report
    Created on : Sep 16, 2015, 1:54:01 PM
    Author     : khirayinnura
--%>

<%@page import="com.dimata.harisma.session.absenteeism.AbsenteeismMonthlyRekap"%>
<%@page import="com.dimata.harisma.session.absenteeism.AbsenteeismYearly"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.session.lateness.LatenessMonthlyRekap"%>
<%@page import="com.dimata.harisma.session.lateness.LatenessYearly"%>
<%@ include file = "../../../main/javainit.jsp" %>

<!DOCTYPE html>
<%!    public String drawList(JspWriter outObj, Hashtable hLatenessYearly) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        
        ctrlist.addHeader("No", "1%", "2", "0");
        ctrlist.addHeader("Payroll", "1%", "2", "0");
        ctrlist.addHeader("Employee", "10%", "2", "0");
        ctrlist.addHeader("Duration (hour, minutes)", "70%", "1", "" + 12 + "");
        
        int jml = 1;
        
        for (int y = 0; y < 12; y++) {
            ctrlist.addHeader("" + jml++, "2%", "0", "0");
        }
        ctrlist.addHeader("Total", "3%", "2", "0");

        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        Vector rowx = new Vector(1, 1);
        
        Enumeration numHash;
        String str;
        jml = 1;
        
        numHash = hLatenessYearly.keys();
        while (numHash.hasMoreElements()) {
            rowx = new Vector(1,1);
            
            String strTotal = "";
            int minutes = 0;
            int hour = 0;
            int totHour = 0;
            int totMinutes = 0;
            str = (String) numHash.nextElement();
            /*System.out.println(str + ": " +
             hLatenessYearly.get(str));*/

            LatenessYearly lateYear = (LatenessYearly) hLatenessYearly.get(str);
            
            rowx.add(""+jml++);
            rowx.add(lateYear.getEmpNum());
            rowx.add(lateYear.getEmpName());
            for (int m = 0; m < lateYear.getTotalMonthly().size(); m++) {
                LatenessMonthlyRekap monthlyLate = (LatenessMonthlyRekap) lateYear.getTotalMonthly().get(m);
                if(!monthlyLate.getTotalMonth().equals("")){
                   
                    hour = monthlyLate.getSumHour();
                    minutes = monthlyLate.getSumMinute();
                    
                    totHour = totHour + hour;
                    totMinutes = totMinutes + minutes;
                    
                    rowx.add(monthlyLate.getTotalMonth());
                    
                } else {
                    rowx.add("-");
                }
            }
            
            if (totMinutes != 0) {
                        
                int jm = totMinutes / 60;
                if (jm != 0) {
                    totHour = totHour + jm;
                    if ((totMinutes % 60) != 0) {
                        totMinutes = totMinutes % 60;
                    }
                }
            }

            //ubah to string
            if (totHour != 0) {
                strTotal = totHour + "h";
            }
            if (totMinutes != 0) {
                if (strTotal.length() > 0) {
                    if (strTotal.length() > 3) {
                        strTotal = strTotal + ", " + totMinutes + "m";
                    } else {
                        strTotal = strTotal + ",  " + totMinutes + "m";
                    }
                } else {
                    strTotal = totMinutes + "m";
                }
            }
            
            if(!strTotal.equals("")){
                rowx.add(strTotal);
            } else {
                rowx.add("-");
            }
            
            
            lstData.add(rowx);
            lstLinkData.add("0");
        }

        return ctrlist.drawList();
    }
%>

<%
    Vector listResult = new Vector(1, 1);
    Hashtable<String, AbsenteeismYearly> hAbsenteeismYearly = new Hashtable();
   // int year = session.getValue("year");
    

    if(session.getValue("listresult")!=null){
        hAbsenteeismYearly = (Hashtable)session.getValue("listresult"); 
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

                numHash = hAbsenteeismYearly.keys();
                while (numHash.hasMoreElements()) {

                    int total = 0;
                    int perMonth = 0;

                    str = (String) numHash.nextElement();
                    /*System.out.println(str + ": " +
                     hLatenessYearly.get(str));*/

                    AbsenteeismYearly lateYear = (AbsenteeismYearly) hAbsenteeismYearly.get(str);
                    
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
                        AbsenteeismMonthlyRekap monthlyLate = (AbsenteeismMonthlyRekap) lateYear.getTotalMonthly().get(m);
                        if (monthlyLate.getTotalMonth() != 0) {
                            perMonth = monthlyLate.getTotalMonth();
                    %>
                    <td>
                        <%=perMonth%>
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
