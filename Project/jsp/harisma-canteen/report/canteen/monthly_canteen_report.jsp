
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

public Vector drawListOld(Date periodDate,  Vector dataOfReports, Vector lastDataOfReports, String[] departmentNames)
{
        Vector results = new Vector();
   
        if(dataOfReports.size()>0) {            
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("60%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("NO.", "3%", "1", "1");	
            ctrlist.addHeader("DEPARTMENT", "22%", "1", "1");
            ctrlist.addHeader("TOTAL", "4%", "1", "1");

            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector listData = ctrlist.getData();
            ctrlist.reset();
            
            int grandTotal = 0;
            
            for(int x=0; x<dataOfReports.size(); x++) {                
                Vector dataOfReport = (Vector)dataOfReports.get(x);
                int dataReportSize = dataOfReport.size();
                
		byte day = (byte) periodDate.getDate();
		byte month = (byte) periodDate.getMonth();
		int year = periodDate.getYear() + 1900;

		GregorianCalendar calendar = new GregorianCalendar(year, month, day);
		byte numOfDay = (byte) calendar.getActualMaximum(calendar.DAY_OF_MONTH);

                
		Vector rowx = null;

		MonthlyDetailVisitation monthlyDetailVisitation = null;

		int totalVisits = 0;
		int totalVisitsPerEmployee = 0;
		int numVisits = 0;

		for (int item = 0; item < dataReportSize; item++) {
			monthlyDetailVisitation = (MonthlyDetailVisitation) dataOfReport.get(item);
                        totalVisitsPerEmployee = monthlyDetailVisitation.getMaTotal();
                        totalVisits += totalVisitsPerEmployee;
		}
                
                // summary per department
                rowx = new Vector();
                rowx.add("<div align=\"right\">" + String.valueOf(x + 1) + "&nbsp;</div>");		
                rowx.add(departmentNames[x]);		
                rowx.add("<div align=\"right\">" + totalVisits + "&nbsp;</div>");
                listData.add(rowx);
                
                grandTotal += totalVisits;
		
            }
            
            // total summary
            Vector rowx = new Vector();
            rowx.add("");		
            rowx.add("<div align=\"center\"><b>TOTAL</b></div>");		
            rowx.add("<div align=\"right\"><b>" + grandTotal + "</b>&nbsp;</div>");
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

public Vector drawList(Date periodDate,  Vector dataOfReports, Vector lastDataOfReports, String[] departmentNames)
{
        Vector results = new Vector();
   
        if(dataOfReports.size()>0) {            
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("60%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("No.", "3%", "1", "1");	
            ctrlist.addHeader("Department", "22%", "1", "1");
            ctrlist.addHeader("This Month", "4%", "1", "1");
            ctrlist.addHeader("Last Month", "4%", "1", "1");

            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector listData = ctrlist.getData();
            Vector listRptData = new Vector();
            ctrlist.reset();
            
            int grandTotal = 0;
            int grandTotalPrev = 0;
            
            for(int x=0; x<dataOfReports.size(); x++) {      

                // currently selected month
                Vector dataOfReport = (Vector)dataOfReports.get(x);
                int dataReportSize = dataOfReport.size();
                
		byte day = (byte) periodDate.getDate();
		byte month = (byte) periodDate.getMonth();
		int year = periodDate.getYear() + 1900;

		GregorianCalendar calendar = new GregorianCalendar(year, month, day);
		byte numOfDay = (byte) calendar.getActualMaximum(calendar.DAY_OF_MONTH);

                
		Vector rowx = null;
                Vector rptRowx = null;

		MonthlyDetailVisitation monthlyDetailVisitation = null;

		int totalVisits = 0;
		int totalVisitsPerEmployee = 0;
		int numVisits = 0;

		for (int item = 0; item < dataReportSize; item++) {
			monthlyDetailVisitation = (MonthlyDetailVisitation) dataOfReport.get(item);
                        totalVisitsPerEmployee = monthlyDetailVisitation.getMaTotal();
                        totalVisits += totalVisitsPerEmployee;
		}
                
                
                // previous month
                Vector lastDataOfReport = (Vector)lastDataOfReports.get(x);
                int lastDataReportSize = lastDataOfReport.size();
                
		byte prevday = (byte) periodDate.getDate();
		byte prevmonth = (byte) (periodDate.getMonth()-1);
		int prevyear = periodDate.getYear() + 1900;

		GregorianCalendar prevcalendar = new GregorianCalendar(prevyear, prevmonth, prevday);
		byte prevnumOfDay = (byte) prevcalendar.getActualMaximum(calendar.DAY_OF_MONTH);


		MonthlyDetailVisitation prevmonthlyDetailVisitation = null;

		int prevtotalVisits = 0;
		int prevtotalVisitsPerEmployee = 0;
		int prevnumVisits = 0;

		for (int item = 0; item < lastDataReportSize; item++) {
			prevmonthlyDetailVisitation = (MonthlyDetailVisitation) lastDataOfReport.get(item);
                        prevtotalVisitsPerEmployee = prevmonthlyDetailVisitation.getMaTotal();
                        prevtotalVisits += prevtotalVisitsPerEmployee;
		}
         
                
                
                
                // summary per department
                rowx = new Vector();
                rowx.add("<div align=\"right\">" + String.valueOf(x + 1) + "&nbsp;</div>");		
                rowx.add(departmentNames[x]);		
                rowx.add("<div align=\"right\">" + totalVisits + "&nbsp;</div>");
                rowx.add("<div align=\"right\">" + prevtotalVisits + "&nbsp;</div>");
                listData.add(rowx);
                
                rptRowx = new Vector();
                rptRowx.add(String.valueOf(x + 1));		
                rptRowx.add(departmentNames[x]);		
                rptRowx.add(totalVisits + "");
                rptRowx.add(prevtotalVisits + "");
                listRptData.add(rptRowx);
                
                grandTotal += totalVisits;
                grandTotalPrev += prevtotalVisits;
		
            }
            
            // total summary
            Vector rowx = new Vector();
            rowx.add("");		
            rowx.add("<div align=\"center\"><b>TOTAL</b></div>");		
            rowx.add("<div align=\"right\"><b>" + grandTotal + "</b>&nbsp;</div>");
            rowx.add("<div align=\"right\"><b>" + grandTotalPrev + "</b>&nbsp;</div>");
            listData.add(rowx);
            
            Vector rptRowx = new Vector();
            rptRowx.add("");		
            rptRowx.add("TOTAL");		
            rptRowx.add(grandTotal + "");
            rptRowx.add(grandTotalPrev + "");
            listRptData.add(rptRowx);
            
            results.add(String.valueOf(DATA_PRINT));
            results.add(ctrlist.drawList());
            results.add(listRptData);
            
	}
	else
	{               
            results.add(String.valueOf(DATA_NULL));
            results.add("<div class=\"msginfo\">No visitation record found ...</div>");
            results.add(new Vector());
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

Vector lstDept = PstDepartment.list(0, 0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
long[] departmentOids = new long[0];
String[] departmentNames = new String[0];

if(lstDept != null && lstDept.size()>0) {
    departmentOids = new long[lstDept.size()];
    departmentNames = new String[lstDept.size()];
    
    for(int i=0; i<lstDept.size(); i++) {
        Department dept = (Department)lstDept.get(i);
        
        departmentOids[i] = dept.getOID();
        departmentNames[i] = dept.getDepartment();
    }
}



Vector dataOfVisits = new Vector(1,1);
Vector lastDataOfVisits = new Vector(1, 1);

if (iCommand == Command.LIST)
{
	SessCanteenVisitation objSessCanteenVisitation = new SessCanteenVisitation();
	//dataOfVisits = objSessCanteenVisitation.getDetailVisitationReportMonthly(gotoDept, secOid, date, rpType);
        //dataOfVisits = objSessCanteenVisitation.getDetailVisitationReportMonthly(departmentOids, date, rpType);
        dataOfVisits = objSessCanteenVisitation.getDetailVisitationReportMonthly(departmentOids, date, scheduleId);
        
        Date lastMonth = (Date)date.clone();
        lastMonth.setMonth(date.getMonth() - 1);
        //lastDataOfVisits = objSessCanteenVisitation.getDetailVisitationReportMonthly(departmentOids, lastMonth, rpType);
        lastDataOfVisits = objSessCanteenVisitation.getDetailVisitationReportMonthly(departmentOids, lastMonth, scheduleId);
}

Vector drawListOuts = drawList(date, dataOfVisits, lastDataOfVisits, departmentNames);

String listReport = String.valueOf(drawListOuts.get(1));
boolean showPrintItem = (String.valueOf(drawListOuts.get(0)).equals(String.valueOf(DATA_PRINT)) ? true : false);

/*if (gotoDept == 0)
{
	listReport = "<div class=\"msginfo\">Please select a department ...</div>";
}*/

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

    monthlyDetailData.add(date);
    monthlyDetailData.add(drawListOuts.get(2));

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
	document.frmMonthlyDetailReport.action = "monthly_canteen_report.jsp";
	document.frmMonthlyDetailReport.submit();
}

function cmdView() {    
        document.frmMonthlyDetailReport.command.value = "<%=Command.LIST%>";
        //document.frmMonthlyDetailReport.hidden_dept_name.value = document.frmMonthlyDetailReport.DEPARTMENT_ID.options[document.frmMonthlyDetailReport.DEPARTMENT_ID.selectedIndex].text;

        <%if(gotoDept!=0){%>
            //document.frmMonthlyDetailReport.hidden_sec_name.value = document.frmMonthlyDetailReport.SECTION_ID.options[document.frmMonthlyDetailReport.SECTION_ID.selectedIndex].text;
        <%}%>

        document.frmMonthlyDetailReport.action = "monthly_canteen_report.jsp";
        document.frmMonthlyDetailReport.submit();   
}

function reportXls() {
	var linkPage = "<%=printroot%>.report.canteen.MonthlyReportGeneralVisitation";
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->&nbsp;Reports &gt;&gt; Summary &gt;&gt; Monthly <!-- #EndEditable -->
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
														 <td width="17%"><a href="javascript:reportXls()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
														 <td width="87%"><b><a href="javascript:reportXls()" class="buttonlink">Print Monthly Visitation</a></b></td>
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
