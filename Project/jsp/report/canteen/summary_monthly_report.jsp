<%@ page language="java" %>

<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>

<%@ page import = "com.dimata.harisma.session.canteen.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<% privPrint = true; %>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    Date date = FRMQueryString.requestDate(request, "cmbDate");

%>

<%!
    int DATA_NULL = 0;
    int DATA_PRINT = 1;
    
    public Vector drawList(Date period_date,
                           Vector dataOfReport)
    {
        int dataOfReportSize = dataOfReport.size();
        Vector results = new Vector();
        
        if (dataOfReportSize > 0) {
            
            GregorianCalendar gregCal = new GregorianCalendar(period_date.getYear() + 1900, period_date.getMonth(), period_date.getDate());
            
            byte numOfDay = (byte) gregCal.getActualMaximum(gregCal.DAY_OF_MONTH);
            
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("NO.", "2%", "2", "1");
            ctrlist.addHeader("DEPARTMENT", "10%", "2", "1");
            ctrlist.addHeader("VISITS PER DATE", "31%", "1", String.valueOf(numOfDay));
            ctrlist.addHeader("TOTAL", "2%", "2", "1");
            
            int totalPerDay[] = new int [numOfDay];
            
            for (byte date = 1; date <= numOfDay; date++) {
                ctrlist.addHeader("<div align=\"center\">" + String.valueOf(date) + "</div>", "1%", "0", "0");
                totalPerDay[date - 1] = 0;
            } 
            
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            Vector listData = ctrlist.getData();
            
            ctrlist.reset();
            
            
            Vector rowx = null;
            MonthlySummary monthlySummary = null;
            
            int numVisits = 0;
            
            int totalPerDepartment = 0;
            
            for (int item = 0; item < dataOfReportSize; item++) {
                monthlySummary = (MonthlySummary) dataOfReport.get(item);
                
                rowx = new Vector();
                rowx.add("<div align=\"right\">" + String.valueOf(item + 1) + "&nbsp;</div>");
                rowx.add("&nbsp;" + monthlySummary.getDepartmentName());
                
                totalPerDepartment = 0;
                
                for (byte date = 1; date <= numOfDay; date++) {
                    
                    switch (date) {
                        case 1 : numVisits = monthlySummary.getDate1();
                        break;
                        case 2 : numVisits = monthlySummary.getDate2();
                        break;
                        case 3 : numVisits = monthlySummary.getDate3();
                        break;
                        case 4 : numVisits = monthlySummary.getDate4();
                        break;
                        case 5 : numVisits = monthlySummary.getDate5();
                        break;
                        case 6 : numVisits = monthlySummary.getDate6();
                        break;
                        case 7 : numVisits = monthlySummary.getDate7();
                        break;
                        case 8 : numVisits = monthlySummary.getDate8();
                        break;
                        case 9 : numVisits = monthlySummary.getDate9();
                        break;
                        case 10 : numVisits = monthlySummary.getDate10();
                        break;
                        case 11 : numVisits = monthlySummary.getDate11();
                        break;
                        case 12 : numVisits = monthlySummary.getDate12();
                        break;
                        case 13 : numVisits = monthlySummary.getDate13();
                        break;
                        case 14 : numVisits = monthlySummary.getDate14();
                        break;
                        case 15 : numVisits = monthlySummary.getDate15();
                        break;
                        case 16 : numVisits = monthlySummary.getDate16();
                        break;
                        case 17 : numVisits = monthlySummary.getDate17();
                        break;
                        case 18 : numVisits = monthlySummary.getDate18();
                        break;
                        case 19 : numVisits = monthlySummary.getDate19();
                        break;
                        case 20 : numVisits = monthlySummary.getDate20();
                        break;
                        case 21 : numVisits = monthlySummary.getDate21();
                        break;
                        case 22 : numVisits = monthlySummary.getDate22();
                        break;
                        case 23 : numVisits = monthlySummary.getDate23();
                        break;
                        case 24 : numVisits = monthlySummary.getDate24();
                        break;
                        case 25 : numVisits = monthlySummary.getDate25();
                        break;
                        case 26 : numVisits = monthlySummary.getDate26();
                        break;
                        case 27 : numVisits = monthlySummary.getDate27();
                        break;
                        case 28 : numVisits = monthlySummary.getDate28();
                        break;
                        case 29 : numVisits = monthlySummary.getDate29();
                        break;
                        case 30 : numVisits = monthlySummary.getDate30();
                        break;
                        case 31 : numVisits = monthlySummary.getDate31();
                    }    
                
                    totalPerDay[date - 1] += numVisits;
                    totalPerDepartment += numVisits;
                    rowx.add("<div align=\"right\">" + String.valueOf(numVisits) + "&nbsp;</div>");
                }
                
                rowx.add("<div align=\"right\">" + String.valueOf(totalPerDepartment) + "&nbsp;</div>");
                
                listData.add(rowx);
                
                
            }
            
            rowx = new Vector();
            rowx.add("");
            rowx.add("<div align=\"center\"><b>TOTAL</b></div>");
            
            int total = 0;
            
            for (byte date = 0; date < numOfDay; date++) {
                total += totalPerDay[date];
                rowx.add("<div align=\"right\">" + String.valueOf(totalPerDay[date]) + "&nbsp;</div>");
            }
            
            rowx.add("<div align=\"right\">" + String.valueOf(total) + "&nbsp;</div>");
            
            listData.add(rowx);
            
            results.add(String.valueOf(DATA_PRINT));
            results.add(ctrlist.drawList());
        } else {
            results.add(String.valueOf(DATA_NULL));
            results.add("<div class=\"msginfo\">No visitation record found ...</div>");
        }
        return results;
    }
%>


<%
    Vector dataOfVisits = new Vector();
    
    String pageWidth = "100%";
    if (iCommand == Command.LIST) {
        dataOfVisits = SessCanteenVisitation.getMonthlySummaryVisitation(date);
        if (dataOfVisits.size() > 0)
            pageWidth = "1200";
    }
    
    Vector drawListOuts = drawList(date, dataOfVisits);
    
    boolean showPrintItem = privPrint && Integer.parseInt((String) drawListOuts.get(0)) > 0;
    
    String listReport = String.valueOf(drawListOuts.get(1));
    
    if (session.getValue("CANTEEN_MONTHLY_SUMMARY_DATA") != null)
        session.removeValue("CANTEEN_MONTHLY_SUMMARY_DATA");
    
    Vector monthlyData = new Vector();
    monthlyData.add(date);
    monthlyData.add(dataOfVisits);    
    session.putValue("CANTEEN_MONTHLY_SUMMARY_DATA", monthlyData);    
%>


<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Canteen Monthly Summary Report</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
    } 
	 
    function hideObjectForLockers(){ 
    }
	
    function hideObjectForCanteen(){
    }
	
    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }
	
    function showObjectForMenu(){
    }
    
    function MM_swapImgRestore() { // v3.0
        var i, x, a = document.MM_sr;
        for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
            x.src = x.oSrc;
    }

    function MM_preloadImages() { // v3.0
        var d = document;
        if (d.images) {
            if (!d.MM_p)
             d.MM_p = new Array();	
             var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
             for (i = 0; i < a.length; i++)
	      if (a[i].indexOf("#") != 0) {
               d.MM_p[j] = new Image;
               d.MM_p[j++].src = a[i];
              }
        }
    }

    function MM_findObj(n, d) { // v4.0
	var p, i, x;
        if (!d)
         d = document;
        if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
	 d = parent.frames[n.substring(p+1)].document;
         n = n.substring(0,p);
        }
	if (!(x = d[n]) && d.all)
         x = d.all[n];
        for (i = 0; !x && i < d.forms.length; i++)
         x = d.forms[i][n];
        for (i = 0; !x && d.layers && i < d.layers.length; i++)
         x = MM_findObj(n, d.layers[i].document);
	if(!x && document.getElementById)
         x = document.getElementById(n);
        return x;
    }

    function MM_swapImage() { // v3.0
	var i, j = 0, x, a = MM_swapImage.arguments;
        document.MM_sr = new Array;
        for (i = 0; i < (a.length - 2); i += 3)
         if ((x = MM_findObj(a[i])) != null) {
          document.MM_sr[j++] = x;
          if (!x.oSrc)
           x.oSrc = x.src;
          x.src = a[i+2];
         }
    }
    
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="<%=pageWidth%>" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Reports >> Summary >> Monthly Report<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="tablecolor"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top">
                                  <!-- #BeginEditable "content" -->
                                   <form name="frmMonthlyReport" method="POST">
                                    <input type="hidden" name="command" value="<%=iCommand%>"> 
                                    <table width="100%">
                                     <tr>
                                      <td width="1%"></td>
                                      <td width="99%">
                                       <table>
                                        <tr>
                                         <td>Period</td>
                                         <td>:</td>
                                         <td>
                                         <%
                                          int diffYear = installationDate.getYear() - (new Date()).getYear();
                                         %>
                                         <%=ControlDate.drawDateMY("cmbDate", date == null || iCommand == Command.NONE ? new Date() : date, "MMM yyyy", "formElemen", 0, diffYear) %>
                                         </td>
                                        </tr><tr>
                                         <td></td>
                                         <td></td>
                                         <td><table><tr>
                                          <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnSearchOn.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" height="24" width="24" alt="Search Visitation"></a></td>
                                          <td width="2"><img src="<%=approot%>/images/spacer.gif" height="1" width="4"></td>
                                          <td width="94" class="command" nowrap><a href="javascript:cmdView()">View Visitation</a></td>
                                         </tr></table></td>
                                        </tr>
                                       </table>
                                      </td>
                                     </tr><tr>
                                      <td align="center" colspan="2">
                                       <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                        <% if (iCommand == Command.LIST) { %>
                                        <tr><td><hr></td></tr>
                                        <tr><td><%=listReport%></td></tr>
                                        <% if (showPrintItem) { %>
                                         <tr><td>
                                          <table border="0" cellpadding="1" cellspacing="1">
                                           <tr>
                                            <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" height="24" width="24" border="0"></a></td>
                                            <td width="87%"><b><a href="javascript:reportPdf()" class="buttonlink">Print Monthly Visitation</a></b></td>
                                           </tr>
                                          </table>
                                         </td></tr>
                                        <% } %>
                                        <% } %>
                                       </table>
                                      </td>
                                     </tr>
                                    </table>                                   
                                   </form>
                                  <!-- #EndEditable --> 
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td>&nbsp; </td>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> 
 
<script language="javascript">
    
    function cmdView() {
        document.frmMonthlyReport.command.value = "<%=Command.LIST%>";
        document.frmMonthlyReport.action = "summary_monthly_report.jsp";
        document.frmMonthlyReport.submit();
    }
    
    function reportPdf() {
        var linkPage = "<%=printroot%>.report.canteen.MonthlySummaryVisitation";
        handle = window.open("", "<%=canteenWindowName%>");
        handle.close();
        handle = window.open(linkPage, "<%=canteenWindowName%>");
        handle.focus();
    }
    
</script>

<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
