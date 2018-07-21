<!-- use internal java package -->
<%@ page language="java" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.harisma.session.canteen.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_REPORT_DETAIL, AppObjInfo.OBJ_DETAIL_WEEKLY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<% //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT)); %>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    long deptOid = FRMQueryString.requestLong(request, "cmbDepartment");
    long secOid = FRMQueryString.requestLong(request, "cmbSection");
    Date date = FRMQueryString.requestDate(request, "cmbDate");
    int weekOfMonth = FRMQueryString.requestInt(request, "cmbWeek");
    String departmentName = FRMQueryString.requestString(request, "hidden_dept_name");
    String sectionName = FRMQueryString.requestString(request, "hidden_sec_name");

    int rpType = FRMQueryString.requestInt(request, "MORNING_AFTERNOON_NIGHT_ID");
//long gotoDept = FRMQueryString.requestLong(request, "hidden_goto_dept");
//long gotoSec = FRMQueryString.requestLong(request, "SECTION_ID");
%>

<%!

    int DATA_NULL = 0;
    int DATA_PRINT = 1;

    public Vector drawList(Date firstDate,
                           Vector dataOfReport)
    {
        Vector results = new Vector();
        int dataOfReportSize = dataOfReport.size();
        //System.out.println("Nilai dari date = " + firstDay);

        int numOfEmployee = 0;

        if (dataOfReportSize > 0) {

            GregorianCalendar gregCal = new GregorianCalendar(firstDate.getYear() + 1900, firstDate.getMonth(), firstDate.getDate());

            byte maxDate = (byte) gregCal.getActualMaximum(gregCal.DAY_OF_MONTH);
            byte dateOfFirstDay = (byte) firstDate.getDate();
            byte actualDay = (byte) gregCal.get(gregCal.DAY_OF_WEEK);

            byte dateOfLastDay = (byte) (dateOfFirstDay + 7 - actualDay);
            if (dateOfLastDay > maxDate)
                dateOfLastDay = maxDate;

            byte numOfDay = (byte) (dateOfLastDay + 1 - dateOfFirstDay);

            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("NO.", "5%", "2", "1");
            ctrlist.addHeader("PAYROLL", "10%", "2", "1");
            ctrlist.addHeader("NAME", "48%", "2", "1");

            int defColWidth = 29 / numOfDay;

            ctrlist.addHeader("VISITS (PER DATE)", "", "1", String.valueOf(numOfDay));
            ctrlist.addHeader("TOTAL", "8%", "2", "1");

            for (byte date = dateOfFirstDay; date <= dateOfLastDay; date++) {
                ctrlist.addHeader("<div align=\"center\">" + String.valueOf(date) + "</div>", String.valueOf(defColWidth) + "%", "0", "0");
            }

            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector listData = ctrlist.getData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();

            Vector rowx = null;
            int value = 0;
            byte index;
            byte date;

            boolean useRecord = false;

            int totalPerEmployee = 0;

            int totalPerDay[] = {0, 0, 0, 0, 0, 0, 0};

            int total = 0;

            int valuePerDay[] = {0, 0, 0, 0, 0, 0, 0};



            for (int item = 0; item < dataOfReportSize; item++) {
                MonthlyDetailVisitation mdv = (MonthlyDetailVisitation) dataOfReport.get(item);

                useRecord = false;
                totalPerEmployee = 0;

                for (byte day = 0; day < 7; day++)
                    valuePerDay[day] = 0;

                rowx = new Vector();
                rowx.add("");
                rowx.add("&nbsp;" + mdv.getEmployeePayroll());
                rowx.add("&nbsp;" + mdv.getEmployeeName());
                for (index = 0; index < numOfDay; index++) {

                    date = (byte) (dateOfFirstDay + index);

                    switch (date) {
                        case 1 : value = mdv.getMa1();
                        break;
                        case 2 : value = mdv.getMa2();
                        break;
                        case 3 : value = mdv.getMa3();
                        break;
                        case 4 : value = mdv.getMa4();
                        break;
                        case 5 : value = mdv.getMa5();
                        break;
                        case 6 : value = mdv.getMa6();
                        break;
                        case 7 : value = mdv.getMa7();
                        break;
                        case 8 : value = mdv.getMa8();
                        break;
                        case 9 : value = mdv.getMa9();
                        break;
                        case 10 : value = mdv.getMa10();
                        break;
                        case 11 : value = mdv.getMa11();
                        break;
                        case 12 : value = mdv.getMa12();
                        break;
                        case 13 : value = mdv.getMa13();
                        break;
                        case 14 : value = mdv.getMa14();
                        break;
                        case 15 : value = mdv.getMa15();
                        break;
                        case 16 : value = mdv.getMa16();
                        break;
                        case 17 : value = mdv.getMa17();
                        break;
                        case 18 : value = mdv.getMa18();
                        break;
                        case 19 : value = mdv.getMa19();
                        break;
                        case 20 : value = mdv.getMa20();
                        break;
                        case 21 : value = mdv.getMa21();
                        break;
                        case 22 : value = mdv.getMa22();
                        break;
                        case 23 : value = mdv.getMa23();
                        break;
                        case 24 : value = mdv.getMa24();
                        break;
                        case 25 : value = mdv.getMa25();
                        break;
                        case 26 : value = mdv.getMa26();
                        break;
                        case 27 : value = mdv.getMa27();
                        break;
                        case 28 : value = mdv.getMa28();
                        break;
                        case 29 : value = mdv.getMa29();
                        break;
                        case 30 : value = mdv.getMa30();
                        break;
                        case 31 : value = mdv.getMa31();
                    }

                    valuePerDay[index] = value;

                    rowx.add("<div align=\"right\">" + String.valueOf(value) + "&nbsp;</div>");

                    totalPerEmployee += value;

                    if (value > 0)
                        useRecord = true;

                }
                if (useRecord) {
                    numOfEmployee++;
                    rowx.set(0, "<div align=\"right\">" + String.valueOf(numOfEmployee) + "&nbsp;</div>");
                    rowx.add("<div align=\"right\">" + String.valueOf(totalPerEmployee) + "&nbsp;</div>");
                    listData.add(rowx);
                    total += totalPerEmployee;

                    for (index = 0; index < numOfDay; index++)
                        totalPerDay[index] += valuePerDay[index];
                }
            }

            rowx = new Vector();
            rowx.add("");
            rowx.add("");
            rowx.add("<div align=\"center\"><b>TOTAL</b></div>");

            for (byte day = 0; day < numOfDay; day++)
                rowx.add("<div align=\"right\"><b>" + String.valueOf(totalPerDay[day]) + "</b>&nbsp;</div>");

            rowx.add("<div align=\"right\"><b>" + String.valueOf(total) + "</b>&nbsp;</div>");

            listData.add(rowx);

            results.add(String.valueOf(DATA_PRINT));
            results.add(ctrlist.drawList());
        }

        if (numOfEmployee < 1) {
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

        SessCanteenVisitation sessCanteenVisitation = new SessCanteenVisitation();
        dataOfVisits = (Vector)sessCanteenVisitation.getDetailVisitationReportWeekly(deptOid,secOid, date,0,weekOfMonth,rpType);
    } else
        dataOfVisits = new Vector();

    Vector drawListOuts = drawList(date, dataOfVisits);

    boolean showPrintItem = privPrint && Integer.parseInt((String) drawListOuts.get(0)) > 0;

    String listReport = String.valueOf(drawListOuts.get(1));
    if (deptOid == 0)
        listReport = "<div class=\"msginfo\">Please select a department ...</div>";

    if (session.getValue("CANTEEN_WEEKLY_DETAIL_DATA") != null)
        session.removeValue("CANTEEN_WEEKLY_DETAIL_DATA");

    Vector weeklyData = new Vector();
    if(deptOid==0)
        departmentName = "";

    weeklyData.add(departmentName);
    weeklyData.add(date);
    weeklyData.add(dataOfVisits);

    if(secOid==0)
        sectionName = "";
    weeklyData.add(sectionName);

    if(rpType==SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON)
        weeklyData.add("");
    else
        weeklyData.add("(NIGHT)");

    session.putValue("CANTEEN_WEEKLY_DETAIL_DATA", weeklyData);
%>

<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Weekly Detail Report</title>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<script language="javascript">

    function deptChange() {
        document.frmWeeklyDetailReport.command.value = "<%=Command.UPDATE%>";
        document.frmWeeklyDetailReport.action = "detail_weekly_report.jsp";
        document.frmWeeklyDetailReport.submit();
    }

    function getWeek() {
        document.frmWeeklyDetailReport.command.value = "<%=Command.UPDATE%>";
        document.frmWeeklyDetailReport.action = "detail_weekly_report.jsp";
        document.frmWeeklyDetailReport.submit();
    }

    function cmdView() {
        if(document.frmWeeklyDetailReport.cmbDepartment.value != "0"){
            document.frmWeeklyDetailReport.command.value = "<%=Command.LIST%>";
            document.frmWeeklyDetailReport.hidden_dept_name.value = document.frmWeeklyDetailReport.cmbDepartment.options[document.frmWeeklyDetailReport.cmbDepartment.selectedIndex].text;
            <%
            if(deptOid != 0){
            %>
                document.frmWeeklyDetailReport.hidden_sec_name.value = document.frmWeeklyDetailReport.cmbSection.options[document.frmWeeklyDetailReport.cmbSection.selectedIndex].text;
            <%}%>
            document.frmWeeklyDetailReport.action = "detail_weekly_report.jsp";
            document.frmWeeklyDetailReport.submit();
        }else{
            alert("Please select a department.");
        }
    }

    function reportPdf() {
        var linkPage = "<%=printroot%>.report.canteen.WeeklyDetailVisitation";
        handle = window.open("", "<%=canteenWindowName%>");
        handle.close();
        handle = window.open(linkPage, "<%=canteenWindowName%>");
        handle.focus();
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
<!-- #EndEditable -->
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Reports >> Detail >> Weekly Report<!-- #EndEditable -->
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
								<form name="frmWeeklyDetailReport" method="post" action="">
								  <input type="hidden" name="command" value="<%=iCommand%>">
								  <input type="hidden" name="hidden_dept_name" value="<%=departmentName%>">
                                  <input type="hidden" name="hidden_sec_name" value="<%=sectionName%>">
								  <table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
										  <td>
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
											  <tr>
												<td width="1%">&nbsp;</td>
												<td width="99%">

											      <table border="0" cellspacing="2" cellpadding="2" bgcolor="" width="100%">
                                                    <tr>
                                                      <td width="10%" nowrap><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                      <td width="1%">:</td>
                                                      <td width="89%">
                                                        <%
														Vector dept_value = new Vector(1,1);
														Vector dept_key = new Vector(1,1);
														Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
														dept_key.add("Please select a department");
														dept_value.add("0");
														String selectDept = String.valueOf(deptOid);
														for (int i = 0; i < listDept.size(); i++)
														{
															Department dept = (Department) listDept.get(i);
															dept_key.add(dept.getDepartment());
															dept_value.add(String.valueOf(dept.getOID()));
														}
														%>
                                                        <%= ControlCombo.draw("cmbDepartment","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %> </td>
                                                    </tr>
                                                    <%
													if (deptOid > 0)
													{
													%>
                                                    <tr>
                                                      <td width="10%" nowrap>
                                                        Outlet </td>
                                                      <td width="1%">:</td>
                                                      <td width="89%">
                                                        <%
															String whereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + deptOid;
															String orderSec = PstSection.fieldNames[PstSection.FLD_SECTION];
															Vector sec_key = new Vector(1,1);
															Vector sec_value = new Vector(1,1);
															sec_key.add("All Outlet...");
															sec_value.add("0");
															Vector listSection = PstSection.list(0, 0, whereSec, orderSec);
															String selectValueSec = String.valueOf(secOid);
															for (int i = 0; i < listSection.size(); i++)
															{
																Section section = (Section) listSection.get(i);
																sec_key.add(section.getSection());
																sec_value.add(String.valueOf(section.getOID()));
															}
															%>
                                                        <%= ControlCombo.draw("cmbSection","elementForm", null, selectValueSec, sec_value, sec_key, "") %> </td>
                                                    </tr>
                                                    <%
													}
													%>
                                                    <tr>
                                                      <td width="10%" nowrap>
                                                        Period</td>
                                                      <td width="1%">:</td>
                                                      <td width="89%">
                                                        <%
													  int diffYear = installationDate.getYear() - (new Date()).getYear();
													  out.println(ControlDate.drawDateMY("cmbDate", date == null || iCommand == Command.NONE ? new Date() : date, "MMM yyyy", "formElemen", 0, diffYear,"onchange=\"javascript:getWeek();\""));
													  %>
                                                    </tr>
                                                    <tr>
                                                      <td width="10%" nowrap>Week</td>
                                                      <td width="1%">:</td>
                                                      <td width="89%">
														<%
														String weeks[] = {"1st Week", "2nd Week", "3rd Week", "4th Week", "5th Week", "6th Week"};

														Vector weekValues = new Vector();
														Vector weekKeys = new Vector();

														gregCal.set(gregCal.DATE, maxDate);
														byte maxWeek = (byte) gregCal.getActualMaximum(gregCal.WEEK_OF_MONTH);

														for (int item = 0; item < maxWeek; item++)
														{                                                                                                                       
                                                                                                                       weekKeys.add(String.valueOf(item + 1));
                                                                                                                       weekValues.add(weeks[item]);
														}
														%>
														<%=ControlCombo.draw("cmbWeek", "", String.valueOf(weekOfMonth), weekKeys, weekValues) %>
													  </td>
                                                    </tr>
                                                    <%
                                                    if(false){
                                                    %>
                                                    <tr>
                                                      <td width="10%" nowrap>Report Type</td>
                                                      <td width="1%">:</td>
                                                      <td width="89%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><input name="MORNING_AFTERNOON_NIGHT_ID" type="radio" value="0" <%if(rpType==0){%>checked<%}%>>
                                            Morning and Afternoon </td>
    </tr>
  <tr>
    <td><input type="radio" name="MORNING_AFTERNOON_NIGHT_ID" value="1" <%if(rpType==1){%>checked<%}%>>
      Night </td>
    </tr>
</table>
                                                      </td>
                                                    </tr>
                                                    <%
                                                    }
                                                    %>
                                                    <tr>
                                                      <td width="10%" nowrap>&nbsp;</td>
                                                      <td width="1%">&nbsp;</td>
                                                      <td width="89%">
                                                        <table>
                                                          <tr>
                                                            <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnSearchOn.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" height="24" width="24" alt="Search Visitation"></a></td>
                                                            <td width="2"><img src="<%=approot%>/images/spacer.gif" height="1" width="4"></td>
                                                            <td width="94" class="command" nowrap><a href="javascript:cmdView()">View
                                                              Visitation</a></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                  </table>
												</td>
											  </tr>
											  <tr>
												<td align="center" colspan="2">
												 <table border="0" cellpadding="2" cellspacing="2" width="100%">
												 <%
												 if (iCommand == Command.LIST)
												 {
												 %>
												  <tr><td><hr></td></tr>
												  <tr><td><%=listReport%></td></tr>
												 <%
												 if (showPrintItem)
												 {
												 %>
												  <tr>
												  <td>
												   <table border="0" cellpadding="1" cellspacing="1">
													<tr>
													 <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
													 <td width="87%"><b><a href="javascript:reportPdf()" class="buttonlink">Print Weekly Visitation</a></b></td>
													</tr>
												   </table>
												  </td>
												  </tr>
												 <%
												 }
												 }
												 %>
												 </table>
												</td>
											  </tr>
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
