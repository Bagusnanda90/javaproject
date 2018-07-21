<!-- use internal java class --> 
<%@ page language = "java" %>
<!-- use dimata qdep class -->
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.harisma.session.canteen.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_REPORT_SUMMARY, AppObjInfo.OBJ_SUMMARY_WEEKLY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<% //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT)); %>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    Date date = FRMQueryString.requestDate(request, "cmbDate");
    int weekOfMonth = FRMQueryString.requestInt(request, "cmbWeek");
%>

<%!
    int DATA_NULL = 0;
    int DATA_PRINT = 1;
    
    public Vector drawList(Date firstDay,
                           Vector dataOfReport) 
    {
        Vector results = new Vector();
        int dataReportSize = dataOfReport.size();
        
        int numDepartment = 0;
        
        if (dataReportSize > 0) {
            
            GregorianCalendar gregCal = new GregorianCalendar(firstDay.getYear() + 1900, firstDay.getMonth(), firstDay.getDate());
            
            byte maxDate = (byte) gregCal.getActualMaximum(gregCal.DAY_OF_MONTH);
            byte firstDate = (byte) firstDay.getDate();
            byte actualDay = (byte) gregCal.get(gregCal.DAY_OF_WEEK);
            
            byte lastDate = (byte) (firstDate + 7 - actualDay);
            if (lastDate > maxDate)
                lastDate = maxDate;
            
            byte numOfDay = (byte) (lastDate + 1 - firstDate);
            
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("NO.", "5%", "2", "1");
            ctrlist.addHeader("DEPARTMENT", "40%", "2", "1");
            ctrlist.addHeader("VISITS (PER DATE)", "", "1", String.valueOf(numOfDay)); 
            ctrlist.addHeader("TOTAL", "8%", "2", "1");
            
            int defColWidth = 47 / numOfDay;
            
            for (byte date = firstDate; date <= lastDate; date++) {
                ctrlist.addHeader("<div align=\"center\">" + String.valueOf(date) + "</div>", String.valueOf(defColWidth) + "%", "0", "0");
            }
            
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");        
            Vector listData = ctrlist.getData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            
            byte date;
            int numVisits = 0;
            int totalPerDepartment = 0;
            
            int valuePerDay[] = {0, 0, 0, 0, 0, 0, 0};
            int totalPerDay[] = {0, 0, 0, 0, 0, 0, 0};
            
            Vector rowx = null;
            
            int total = 0;
            
            for (int item = 0; item < dataReportSize; item++) {
                
                MonthlySummary monthlySummary = (MonthlySummary) dataOfReport.get(item);                
                
                rowx = new Vector();
                rowx.add("");
                rowx.add("&nbsp;" + monthlySummary.getDepartmentName());
                
                totalPerDepartment = 0;
                
                for (byte index = 0; index < numOfDay; index++) {
                    date = (byte) (firstDate + index);
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
                    
                    valuePerDay[index] = numVisits;
                    
                    rowx.add("<div align=\"right\">" + String.valueOf(numVisits) + "&nbsp;</div>");
                    totalPerDepartment += numVisits;
                    
                }    
                
                if (totalPerDepartment > 0) {
                    numDepartment++;
                    rowx.set(0, "<div align=\"right\">" + String.valueOf(numDepartment) + "&nbsp;</div>");
                    rowx.add("<div align=\"right\">" + String.valueOf(totalPerDepartment) + "&nbsp;</div>");
                    listData.add(rowx);
                    
                    for (byte index = 0; index < numOfDay; index++)
                        totalPerDay[index] += valuePerDay[index];
                    
                    total += totalPerDepartment;
                }    
                
            }    
            
            rowx = new Vector();
            rowx.add("");
            rowx.add("<div align=\"center\"><b>TOTAL</b></div>");
            
            for (byte index = 0; index < numOfDay; index++)
                rowx.add("<div align=\"right\"><b>" + String.valueOf(totalPerDay[index]) + "</b>&nbsp;</div>");
            rowx.add("<div align=\"right\"><b>" + String.valueOf(total) + "</b>&nbsp;</div>");
            listData.add(rowx);
            
            results.add(String.valueOf(DATA_PRINT));
            results.add(ctrlist.drawList());
        }    
        
        if (numDepartment < 1) {
            results = new Vector();
            results.add(String.valueOf(DATA_NULL));
            results.add("<div class=\"msginfo\">No visitation record found ...</div>");
        }
        return results;
    }    
%>

<%
    Vector dataOfVisits = new Vector();
    
    GregorianCalendar gregCal = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), 1);
    byte maxDate = (byte) gregCal.getActualMaximum(gregCal.DAY_OF_MONTH);
    
    byte estimationDate = (byte) (1 + 7 * (weekOfMonth - 1));
    if (estimationDate > maxDate)
        estimationDate = maxDate;
    
    gregCal.set(gregCal.DATE, estimationDate);
    
    byte actualWeek = (byte) gregCal.get(gregCal.WEEK_OF_MONTH);
    
    if (actualWeek == weekOfMonth && iCommand == Command.LIST) {
        
        byte firstDate = 1;
        if (weekOfMonth > 1) {
            byte actualDay = (byte) gregCal.get(gregCal.DAY_OF_WEEK);
            firstDate = (byte) (estimationDate + 1 - actualDay);
        }    
        
        date.setDate(firstDate);
        dataOfVisits = (Vector) SessCanteenVisitation.getMonthlySummaryVisitation(date);
        
    } else
        dataOfVisits = new Vector();
    
    Vector drawListOuts = drawList(date, dataOfVisits); 
    boolean showPrintItem = privPrint && Integer.parseInt((String) drawListOuts.get(0)) > 0;    
    String listReport = String.valueOf(drawListOuts.get(1));
    
    if (session.getValue("CANTEEN_WEEKLY_SUMMARY_DATA") != null)
        session.removeValue("CANTEEN_WEEKLY_SUMMARY_DATA");
    
    Vector weeklyData = new Vector();
    weeklyData.add(date);
    weeklyData.add(dataOfVisits);
    session.putValue("CANTEEN_WEEKLY_SUMMARY_DATA", weeklyData);
%>

<html>

<head>

<title>HARISMA - Canteen Weekly Summary Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<script language="javascript">
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
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      
      <%@ include file = "../../main/header.jsp" %>
      
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
      <%@ include file = "../../main/mnmain.jsp" %>
      </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong>Reports >> Summary >> Weekly Report</strong></font></td>
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
                                   <form name="frmWeeklyReport" method="POST">
                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                    <table width="100%">
                                     <tr>
                                      <td width="1%"></td>
                                      <td width="99%">
                                       <table>
                                        <tr>
                                         <td>Month</td>
                                         <td>:</td>
                                         <td>
                                         <%
                                          int diffYear = installationDate.getYear() - (new Date()).getYear();
                                         %>   
                                         <%=ControlDate.drawDateMY("cmbDate", date == null || iCommand == Command.NONE ? new Date() : date, "MMM yyyy", "formElemen", 0, diffYear, "onChange=\"javascript:getWeek()\"") %>
                                         </td>
                                        </tr><tr>
                                         <td>Week</td>
                                         <td>:</td>
                                         <td>
                                         <%
                                            String WEEKS[] = {"1st Week", "2nd Week", "3rd Week", "4th Week", "5th Week", "6th Week"};
                                            
                                            Vector weekValues = new Vector();
                                            Vector weekKeys = new Vector();
                                            
                                            byte maxWeek = (byte) gregCal.getActualMaximum(gregCal.WEEK_OF_MONTH);
                                            
                                            for (byte item = 0; item < maxWeek; item++) {
                                                weekKeys.add(String.valueOf(item + 1));
                                                weekValues.add(WEEKS[item]);
                                            }
                                            
                                         %>
                                         <%=ControlCombo.draw("cmbWeek", null, String.valueOf(weekOfMonth), weekKeys, weekValues) %>
                                         </td>
                                        </tr><tr>
                                         <td></td>
                                         <td></td>
                                         <td><table><tr>
                                          <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnSearchOn.jpg', 1)" id="aSearch"><img border="0" src="<%=approot%>/images/BtnSearch.jpg" height="24" width="24" alt="Search Visitation"></a></td>
                                          <td width="2"><img src="<%=approot%>/images/spacer.gif" height="1" width="4"></td>
                                          <td width="94" class="command" nowrap><a href="javascript:cmdView()">View Visitation</a></td>
                                         </tr></table></td>
                                        </tr>
                                       </table>
                                      </td>
                                     </tr>
                                     <% if (iCommand == Command.LIST) { %>
                                     <tr>
                                      <td align="center" colspan="2">
                                       <table width="100%">                                        
                                        <tr><td><hr></td></tr>
                                        <tr><td><%=listReport%></td></tr>
                                        <% if (showPrintItem) { %>
                                        <tr><td>
                                         <table cellspacing="2" cellpadding="2">
                                          <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" height="24" width="24" border="0"></a></td>
                                          <td width="87%"><b><a href="javascript:reportPdf()" class="buttonlink">Print Weekly Visitation</a></b></td>
                                         </table>
                                        </td></tr>
                                        <% } %>
                                       </table>
                                      </td>
                                     </tr>
                                     <% } %>
                                    </table>
                                   </form>
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
    <td colspan="2" height="20" <%=bgFooterLama%>> 
      <%@ include file = "../../main/footer.jsp" %>
     </td>
  </tr>
</table>
</body>

<script language="javascript">
    function getWeek() {
        document.frmWeeklyReport.command.value = "<%=Command.REFRESH%>";
        document.frmWeeklyReport.action = "summary_weekly_report.jsp";
        document.frmWeeklyReport.submit();
    }
    
    function cmdView() {
        document.frmWeeklyReport.command.value = "<%=Command.LIST%>";
        document.frmWeeklyReport.action = "summary_weekly_report.jsp";
        document.frmWeeklyReport.submit();
    }
    
    function reportPdf() {
        var linkPage = "<%=printroot%>.report.canteen.WeeklySummaryVisitation";
        handle = window.open("", "<%=canteenWindowName%>");
        handle.close();
        handle = window.open(linkPage, "<%=canteenWindowName%>");
        handle.focus();
    }
</script>

</html>
