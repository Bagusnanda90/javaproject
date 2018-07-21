<%-- 
    Document   : dp_available_list_home
    Created on : May 10, 2010, 1:06:20 PM
    Author     : root
--%>

<%@ page language = "java" %>

<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import="java.util.Date"%>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*"%>
<%@ include file = "../../main/javainit.jsp" %>

<%!
private String drawList(Vector vectDpStockManagement){
    ControlList ctrlist = new ControlList();
    ctrlist.setAreaWidth("100%");
    ctrlist.setListStyle("listgen");
    ctrlist.setTitleStyle("listgentitle");
    ctrlist.setCellStyle("listgensell");
    ctrlist.setHeaderStyle("listgentitle");
    ctrlist.addHeader("No","20%");
    ctrlist.addHeader("Entitle Date","20%");
    ctrlist.addHeader("Days Qty ","20%");
    ctrlist.addHeader("Taken ","20%");
    ctrlist.addHeader("Will be Taken","20%");
    ctrlist.addHeader("Eligible","20%");
    
    ctrlist.setLinkRow(1);
    ctrlist.setLinkSufix("");
    Vector lstData = ctrlist.getData();
    Vector lstLinkData = ctrlist.getLinkData();
    ctrlist.setLinkPrefix("javascript:cmdSelect('");
    ctrlist.setLinkSufix("')");
    ctrlist.reset();
    
    for(int i=0;i<vectDpStockManagement.size();i++){
        
        DpStockManagement dpStockMan =  (DpStockManagement) vectDpStockManagement.get(i);
        
        if(dpStockMan.getEligible()<1)
             continue;
        
        Vector rowx = new Vector(1,1);
        rowx.add(String.valueOf(i+1));        
        rowx.add(""+dpStockMan.getDtOwningDate());
        rowx.add(""+dpStockMan.getiDpQty());
        rowx.add(""+dpStockMan.getQtyUsed());
        rowx.add(""+dpStockMan.getToBeTaken());
        rowx.add(""+dpStockMan.getEligible());         
        lstData.add(rowx);            
        
        lstLinkData.add(String.valueOf(dpStockMan.getOID())+"','"+Formater.formatDate(dpStockMan.getDtOwningDate(),"yyyy-MM-dd"));           
        
    }
    
    return ctrlist.draw();
}
%>

<%
    final int max_data = 20;
    int iCommand = FRMQueryString.requestCommand(request);    
    long empId = FRMQueryString.requestLong(request,"employee_id");
    Date reqDate = FRMQueryString.requestDate(request,FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE]);
            
    Vector dpEligable = com.dimata.harisma.session.attendance.SessDpStockManagement.listDpStockEligible(0, 0, empId, reqDate);
    
%>

<script language="JavaScript">
    function cmdSelect(dpOid, date) {        
        var oid = dpOid;
        self.opener.document.forms.frm_leave_application.DpPaidDate.value = dpOid;
        self.opener.document.forms.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]%>.value=dpOid;
        self.opener.document.forms.frm_leave_application.DpPaidDate.value = date;
        self.close();
    }
</script>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - DP Eligible List</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
        <!-- #BeginEditable "styles" --> 
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
    </head> 
    
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >            
            <tr> 
                <td width="88%" valign="top" align="left"> 
                
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                       <tr> 
                                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                                DP ELIGIBLE LIST
                                  <!-- #EndEditable --> 
                                  </strong></font> </td>
                        </tr>
                            <tr> 
                            <td class="tablecolor"> 
                                <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                    <tr> 
                                        <td valign="top"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                <tr> 
                                                    <td valign="top">
                                                        <!-- #BeginEditable "content" -->
                                                        <%
                                                        if(dpEligable.size()>0){
                                                            out.println(drawList(dpEligable));
                                                        }
                                                        %>
                                                        <!-- #EndEditable -->
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td> 
            </tr>
            
        </table>
    </body>
    <!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
