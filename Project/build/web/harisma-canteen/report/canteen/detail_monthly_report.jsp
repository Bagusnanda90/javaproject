
<!-- use internal java package -->
<%@ page language = "java" %>

<!-- use dimata package -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.harisma.entity.canteen.*" %>
<!-- use harisma package -->
<%@ page import = "com.dimata.harisma.session.canteen.*" %>

<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_REPORT_DETAIL, AppObjInfo.OBJ_DETAIL_MONTHLY); %>
<%@ include file = "../../../main/checkuser.jsp" %>
<%// boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT)); %>

<%!
int DATA_NULL = 0;
int DATA_PRINT = 1;

public Vector drawList(Date periodDate, Vector dataOfReport)
{
	int dataReportSize = dataOfReport.size();
	Vector results = new Vector();
	if (dataReportSize > 0) {
		byte day = (byte) periodDate.getDate();
		byte month = (byte) periodDate.getMonth();
		int year = periodDate.getYear() + 1900;

		GregorianCalendar calendar = new GregorianCalendar(year, month, day);		

		byte numOfDay = (byte) calendar.getActualMaximum(calendar.DAY_OF_MONTH);

		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("NO.", "3%", "2", "1");
		ctrlist.addHeader("PAYROLL", "5%", "2", "1");
		ctrlist.addHeader("NAME", "22%", "2", "1");
		ctrlist.addHeader("VISITS (PER DATE)", "", "1", String.valueOf(numOfDay));
		ctrlist.addHeader("TOTAL", "4%", "2", "1");

		int defColWidth = 66 / numOfDay;


		int totalPerDay[] = new int [numOfDay];

		for (byte date = 1; date <= numOfDay; date++) {
			ctrlist.addHeader("<div align=\"center\">" + String.valueOf(date) + "</div>", String.valueOf(defColWidth) + "%", "0", "0");
			totalPerDay[date - 1] = 0;
		}

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		Vector listData = ctrlist.getData();

		ctrlist.reset();

		Vector rowx = null;

		MonthlyDetailVisitation monthlyDetailVisitation = null;

		int totalVisits = 0;

		int totalVisitsPerEmployee = 0;

		int numVisits = 0;

		for (int item = 0; item < dataReportSize; item++) {

			monthlyDetailVisitation = (MonthlyDetailVisitation) dataOfReport.get(item);

			rowx = new Vector();
			rowx.add("<div align=\"right\">" + String.valueOf(item + 1) + "&nbsp;</div>");
			rowx.add("&nbsp;" + monthlyDetailVisitation.getEmployeePayroll());
			rowx.add("&nbsp;" + monthlyDetailVisitation.getEmployeeName());

			for (byte date = 1; date <= numOfDay; date++)
			{
				switch (date)
				{
					case 1 : numVisits = monthlyDetailVisitation.getMa1();
					break;
					case 2 : numVisits = monthlyDetailVisitation.getMa2();
					break;
					case 3 : numVisits = monthlyDetailVisitation.getMa3();
					break;
					case 4 : numVisits = monthlyDetailVisitation.getMa4();
					break;
					case 5 : numVisits = monthlyDetailVisitation.getMa5();
					break;
					case 6 : numVisits = monthlyDetailVisitation.getMa6();
					break;
					case 7 : numVisits = monthlyDetailVisitation.getMa7();
					break;
					case 8 : numVisits = monthlyDetailVisitation.getMa8();
					break;
					case 9 : numVisits = monthlyDetailVisitation.getMa9();
					break;
					case 10 : numVisits = monthlyDetailVisitation.getMa10();
					break;
					case 11 : numVisits = monthlyDetailVisitation.getMa11();
					break;
					case 12 : numVisits = monthlyDetailVisitation.getMa12();
					break;
					case 13 : numVisits = monthlyDetailVisitation.getMa13();
					break;
					case 14 : numVisits = monthlyDetailVisitation.getMa14();
					break;
					case 15 : numVisits = monthlyDetailVisitation.getMa15();
					break;
					case 16 : numVisits = monthlyDetailVisitation.getMa16();
					break;
					case 17 : numVisits = monthlyDetailVisitation.getMa17();
					break;
					case 18 : numVisits = monthlyDetailVisitation.getMa18();
					break;
					case 19 : numVisits = monthlyDetailVisitation.getMa19();
					break;
					case 20 : numVisits = monthlyDetailVisitation.getMa20();
					break;
					case 21 : numVisits = monthlyDetailVisitation.getMa21();
					break;
					case 22 : numVisits = monthlyDetailVisitation.getMa22();
					break;
					case 23 : numVisits = monthlyDetailVisitation.getMa23();
					break;
					case 24 : numVisits = monthlyDetailVisitation.getMa24();
					break;
					case 25 : numVisits = monthlyDetailVisitation.getMa25();
					break;
					case 26 : numVisits = monthlyDetailVisitation.getMa26();
					break;
					case 27 : numVisits = monthlyDetailVisitation.getMa27();
					break;
					case 28 : numVisits = monthlyDetailVisitation.getMa28();
					break;
					case 29 : numVisits = monthlyDetailVisitation.getMa29();
					break;
					case 30 : numVisits = monthlyDetailVisitation.getMa30();
					break;
					case 31 : numVisits = monthlyDetailVisitation.getMa31();
				}

				totalPerDay[date - 1] += numVisits;
				rowx.add("<div align=\"right\">" + numVisits + "&nbsp;</div>");
			}

			totalVisitsPerEmployee = monthlyDetailVisitation.getMaTotal();
			rowx.add("<div align=\"right\">" + totalVisitsPerEmployee + "&nbsp;</div>");
			listData.add(rowx);

			totalVisits += totalVisitsPerEmployee;
		}

		rowx = new Vector();
		rowx.add("");
		rowx.add("");
		rowx.add("<div align=\"center\"><b>TOTAL</b></div>");
		for (byte date = 0; date < numOfDay; date++)
		{
			rowx.add("<div align=\"right\"><b>" + String.valueOf(totalPerDay[date]) + "</b>&nbsp;</div>");
		}
		rowx.add("<div align=\"right\"><b>" + String.valueOf(totalVisits) + "</b>&nbsp;</div>");
		listData.add(rowx);

		results.add(String.valueOf(DATA_PRINT));
		results.add(ctrlist.drawList());
	}
	else
	{
		results.add(String.valueOf(DATA_NULL));
		results.add("<div class=\"msginfo\">No visitation record found ...</div>");
	}
	return results;
}
%>

<%

int countSchedule = 0;

try{
    countSchedule = PstCanteenSchedule.getCount(null);
}catch(Exception E){
    System.out.println("excption "+E.toString());
}

Vector lstCantSch = new Vector();
String orderCnt = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE]+" ASC ";

try{
    lstCantSch = PstCanteenSchedule.list(0, 0, "", orderCnt);
}catch(Exception E){
    System.out.println("excption "+E.toString());
}

long oidSchCanteen = FRMQueryString.requestLong(request, "SCH");
int iCommand = FRMQueryString.requestCommand(request);
long gotoDept = FRMQueryString.requestLong(request, "DEPARTMENT_ID");
long secOid = FRMQueryString.requestLong(request, "SECTION_ID");
String departmentName = FRMQueryString.requestString(request, "hidden_dept_name");
Date date = FRMQueryString.requestDate(request, "cmbDate");
int rpType = FRMQueryString.requestInt(request, "MORNING_AFTERNOON_NIGHT_ID");
String sectionName = FRMQueryString.requestString(request, "hidden_sec_name");

String[] scheduleId = null;

scheduleId = new String[countSchedule+1];

int max1 = 0;
for(int j = 0 ; j < countSchedule ; j++){

    CanteenSchedule canteenSch = new CanteenSchedule();
    canteenSch = (CanteenSchedule)lstCantSch.get(j);
    String name = "SCH_"+canteenSch.getOID();
    scheduleId[j] = FRMQueryString.requestString(request,name);
    max1++;
}

scheduleId[max1] = FRMQueryString.requestString(request,"SCH_"+0);

Vector dataOfVisits = new Vector(1,1);
if (iCommand == Command.LIST)
{

        SessCanteenVisitation objSessCanteenVisitation = new SessCanteenVisitation();
	//dataOfVisits = objSessCanteenVisitation.getDetailVisitationReportMonthly(gotoDept, secOid, date, rpType);
        dataOfVisits = objSessCanteenVisitation.getDetailVisitationReportMonthly(gotoDept, secOid, date, scheduleId);

}

Vector drawListOuts = drawList(date, dataOfVisits);
boolean showPrintItem = true;

String listReport = String.valueOf(drawListOuts.get(1));
if (gotoDept == 0)
{
	listReport = "<div class=\"msginfo\">Please select a department ...</div>";
}

if (session.getValue("CANTEEN_MONTHLY_DETAIL_DATA") != null)
{
	session.removeValue("CANTEEN_MONTHLY_DETAIL_DATA");
}

    byte day = (byte) date.getDate();
    byte month = (byte) date.getMonth();
    int year = date.getYear() + 1900;
    GregorianCalendar calendar = new GregorianCalendar(year, month, day);
    byte numOfDay = (byte) calendar.getActualMaximum(calendar.DAY_OF_MONTH);

Vector monthlyDetailData = new Vector();
    if(gotoDept==0){
        departmentName = "";
    }
monthlyDetailData.add(departmentName);
monthlyDetailData.add(date);
monthlyDetailData.add(""+numOfDay);
monthlyDetailData.add(dataOfVisits);

if(secOid==0)
    sectionName = "";
monthlyDetailData.add(sectionName);

if(rpType==SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON)
    monthlyDetailData.add("");
else
    monthlyDetailData.add("NIGHT");

session.putValue("CANTEEN_MONTHLY_DETAIL_DATA", monthlyDetailData);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Canteen Monthly Detail Report</title>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<script language="javascript">
<!--
function deptChange() {
	document.frmMonthlyDetailReport.command.value = "<%=Command.GOTO%>";
	document.frmMonthlyDetailReport.action = "detail_monthly_report.jsp";
	document.frmMonthlyDetailReport.submit();
}

function cmdView() {
    if(document.frmMonthlyDetailReport.DEPARTMENT_ID.value != "0"){
        document.frmMonthlyDetailReport.command.value = "<%=Command.LIST%>";
        document.frmMonthlyDetailReport.hidden_dept_name.value = document.frmMonthlyDetailReport.DEPARTMENT_ID.options[document.frmMonthlyDetailReport.DEPARTMENT_ID.selectedIndex].text;

        <%if(gotoDept!=0){%>
            document.frmMonthlyDetailReport.hidden_sec_name.value = document.frmMonthlyDetailReport.SECTION_ID.options[document.frmMonthlyDetailReport.SECTION_ID.selectedIndex].text;
        <%}%>

        document.frmMonthlyDetailReport.action = "detail_monthly_report.jsp";
        document.frmMonthlyDetailReport.submit();
    }else{
        alert("Please select a department.");
    }
}

function reportPdf() {
	var linkPage = "<%=printroot%>.report.canteen.MonthlyReportDetailVisitation";
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
//-->
</script>
<!-- #EndEditable -->
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
      <!-- #BeginEditable "header" -->
      <%@ include file = "../../../main/header.jsp" %>
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
  <%}%>
  <tr>
    <td width="88%" valign="top" align="left">
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr>
          <td width="100%">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->&nbsp;Reports &gt;&gt; Detail &gt;&gt; Monthly <!-- #EndEditable -->
                  </strong></font> </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td  style="background-color:<%=bgColorContent%>; ">
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr>
                            <td valign="top">
                              <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr>
                                  <td valign="top"> <!-- #BeginEditable "content" -->
									<form name="frmMonthlyDetailReport" method="post" action="">
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
															String selectDept = String.valueOf(gotoDept);
															for (int i = 0; i < listDept.size(); i++)
															{
																Department dept = (Department) listDept.get(i);
																dept_key.add(dept.getDepartment());
																dept_value.add(String.valueOf(dept.getOID()));
															}
															%>
                                                        <%= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange()\"") %> </td>
                                                    </tr>
                                                    <%
														if (gotoDept > 0)
														{
														%>
                                                    <tr>
                                                      <td width="10%" nowrap>
                                                        Outlet </td>
                                                      <td width="1%">:</td>
                                                      <td width="89%">
                                                        <%
																String whereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + gotoDept;
																String orderSec = PstSection.fieldNames[PstSection.FLD_SECTION];
																Vector sec_key = new Vector(1,1);
																Vector sec_value = new Vector(1,1);
																sec_key.add("All Outlet ...");
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
                                                        <%= ControlCombo.draw("SECTION_ID","elementForm", null, selectValueSec, sec_value, sec_key, "") %> </td>
                                                    </tr>
                                                    <%
														}
														%>

                                                    <tr>
                                                      <td width="10%" nowrap>
                                                        Periode</td>
                                                      <td width="1%">:</td>
                                                      <td width="89%">
                                                        <%
				                                          int diffYear = installationDate.getYear() - (new Date()).getYear();
														  out.println(ControlDate.drawDateMY("cmbDate", date == null || iCommand == Command.NONE ? new Date() : date, "MMM yyyy", "formElemen", 0, diffYear));
														  %>
                                                    </tr>
                                                     <tr>
                                            <td valign="top">Time</td>
                                            <td>:</td>
                                            <td valign="top">
                                            <%
                                            Vector listCntSch = new Vector();

                                            String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE]+" ASC ";
                                            listCntSch = PstCanteenSchedule.list(0, 0, "" , order);

                                            int count = 0;

                                            for(int i = 0 ; i < listCntSch.size() ; i++){

                                                CanteenSchedule canteenSchedule = new CanteenSchedule();
                                                canteenSchedule = (CanteenSchedule)listCntSch.get(i);

                                                String nameInp = "SCH_"+canteenSchedule.getOID();

                                                if(scheduleId[i].compareTo("1")==0){

                                            %>

                                                    <input name=<%=nameInp%> type="checkbox" checked value=1 > <%=canteenSchedule.getSName()%>

                                            <%
                                                }else{
                                            %>
                                                    <input name=<%=nameInp%> type="checkbox" value=1 > <%=canteenSchedule.getSName()%>
                                            <%
                                                }

                                                 count ++;
                                            }

                                            if(scheduleId[count].compareTo("1")==0){
                                            %>
                                                <input name="SCH_0" type="checkbox" value=1 checked > Outside Schedule
                                            <%
                                            }else{
                                            %>
                                                <input name="SCH_0" type="checkbox" value=1 > Outside Schedule
                                            <%
                                            }
                                            %>
                                            </td>
                                      </tr>


                                                    <%
                                                    if(false){
                                                    %>
                                                    <tr>
                                                      <td valign="top" nowrap>Report Type </td>
                                                      <td valign="top">:</td>
                                                      <td valign="top"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><input name="MORNING_AFTERNOON_NIGHT_ID" type="radio" value="0" <%if(rpType==0){%>checked<%}%>>
                                            During canteen opening schedule </td>
    </tr>
  <tr>
    <td><input type="radio" name="MORNING_AFTERNOON_NIGHT_ID" value="1" <%if(rpType==1){%>checked<%}%>>
      During outside opening schedule </td>
    </tr>
  <tr>
    <td><input type="radio" name="MORNING_AFTERNOON_NIGHT_ID" value="2" <%if(rpType==2){%>checked<%}%>>
      All </td>
    </tr>
</table></td>
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
														<td width="94" class="command" nowrap><a href="javascript:cmdView()">View Visitation</a></td>
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
														 <td width="87%"><b><a href="javascript:reportPdf()" class="buttonlink">Print Monthly Visitation</a></b></td>
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
  <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
