
<%@ page language="java" %>

<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.harisma.session.canteen.*" %>
<%@ page import = "com.dimata.harisma.entity.canteen.*" %>
<%@ include file = "../../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_REPORT_SUMMARY, AppObjInfo.OBJ_SUMMARY_MONTHLY); %>
<%@ include file = "../../../main/checkuser.jsp" %>
<%// boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT)); %>

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
    Date date = FRMQueryString.requestDate(request, "cmbDate");
    Date dateto = FRMQueryString.requestDate(request, "cmbDateto");
    double valNominal = FRMQueryString.requestDouble(request, "vl_nominal");
    long oidDepartment = FRMQueryString.requestLong(request, "DEPARTMENT_ID");
    long oidSection = FRMQueryString.requestLong(request, "SECTION_ID");
    String departmentName = FRMQueryString.requestString(request, "hidden_dept_name");
    String secName = FRMQueryString.requestString(request, "hidden_sec_name");
    int rpType = FRMQueryString.requestInt(request, "MORNING_AFTERNOON_NIGHT_ID");

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

%>

<%!
    int DATA_NULL = 0;
    int DATA_PRINT = 1;

    public Vector drawList(Date date, Date dateto, Vector dataOfReport, boolean nominal, double valNominal){
        int dataOfReportSize = dataOfReport.size();
        Vector results = new Vector();


        if (dataOfReportSize > 0) {
            int iStartIterate = date.getDate();
            int iEndIterate = dateto.getDate();

            int totalPerDay[] = new int[iEndIterate-iStartIterate+1];
            ControlList ctrlist = new ControlList();
            int total_width = (2 * (iEndIterate - iStartIterate)+1)+ 24;
            if(total_width >100)
                total_width = 100;
            //ctrlist.setAreaWidth(""+total_width+"%");
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("NO.", "3%", "2", "1");
            ctrlist.addHeader("OUTLET", "18%", "2", "1");
            ctrlist.addHeader("VISITS (DATE)", "", "1", String.valueOf((iEndIterate - iStartIterate)+1));
            ctrlist.addHeader("TOTAL", "4%", "2", "1");
            if(nominal)
                ctrlist.addHeader("NOMINAL", "10%", "2", "1");

            int idx = 0;
            for (int j = iStartIterate; j <= iEndIterate; j++) {
                ctrlist.addHeader("<div align=\"center\">" + String.valueOf(j) + "</div>", "2%", "0", "0");
                totalPerDay[idx] = 0;
                idx++;
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
			
			FRMHandler objFRMHandler = new FRMHandler();
			objFRMHandler.setDecimalSeparator(",");
			objFRMHandler.setDigitSeparator(".");												  
			
            for (int item = 0; item < dataOfReportSize; item++) {
                monthlySummary = (MonthlySummary) dataOfReport.get(item);

                rowx = new Vector();
                rowx.add("<div align=\"right\">" + String.valueOf(item + 1) + "&nbsp;</div>");
                rowx.add("&nbsp;" + monthlySummary.getDepartmentName());

                totalPerDepartment = 0;
                idx = 0;
                for (int j = iStartIterate; j <= iEndIterate; j++){
                    switch (j) {
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

                    totalPerDay[idx] += numVisits;
                    idx++;
                    totalPerDepartment += numVisits;
                    rowx.add("<div align=\"right\">" + String.valueOf(numVisits) + "&nbsp;</div>");
                }

                rowx.add("<div align=\"right\">" + String.valueOf(totalPerDepartment) + "&nbsp;</div>");
                if(nominal)
                    rowx.add("<div align=\"right\">"+objFRMHandler.userFormatStringDecimal(totalPerDepartment * valNominal)+"&nbsp;</div>");

                listData.add(rowx);
            }

            rowx = new Vector();
            rowx.add("");
            rowx.add("<div align=\"center\"><b>TOTAL</b></div>");

            int total = 0;
            idx = 0;
            for (int j = iStartIterate; j <= iEndIterate; j++){
                total += totalPerDay[idx];
                rowx.add("<div align=\"right\"><b>" + String.valueOf(totalPerDay[idx]) + "</b>&nbsp;</div>");
                idx++;
            }

            rowx.add("<div align=\"right\"><b>" + String.valueOf(total) + "</b>&nbsp;</div>");

            if(nominal){
                rowx.add("<div align=\"right\"><b>" + objFRMHandler.userFormatStringDecimal(total * valNominal) + "</b>&nbsp;</div>");
            }
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
    boolean nominal = true;
    Vector dataOfVisits = new Vector();
    String pageWidth = "100%";
    if (iCommand == Command.LIST) {
        SessCanteenVisitation sessCanteenVisitation = new SessCanteenVisitation();

        //dataOfVisits = sessCanteenVisitation.getPeriodicSummaryVisitation(date, dateto, oidDepartment,oidSection, rpType);
        dataOfVisits = sessCanteenVisitation.getPeriodicSummaryVisitation(date, dateto, oidDepartment,oidSection, scheduleId);

        if (dataOfVisits.size() > 0){
            pageWidth = "100%";
        }
    }

    Vector drawListOuts = drawList(date,dateto,dataOfVisits,nominal, valNominal);

    boolean showPrintItem = privPrint && Integer.parseInt((String) drawListOuts.get(0)) > 0;

    String listReport = String.valueOf(drawListOuts.get(1));

    if (session.getValue("CANTEEN_MONTHLY_SUMMARY_DATA") != null)
        session.removeValue("CANTEEN_MONTHLY_SUMMARY_DATA");

    Vector monthlyData = new Vector();
    monthlyData.add(date);
    monthlyData.add(dateto);
    monthlyData.add(dataOfVisits);
    if(oidDepartment==0)
        monthlyData.add("");
    else
        monthlyData.add(departmentName.toUpperCase());

    if(oidSection==0)
        monthlyData.add("");
    else
        monthlyData.add(secName.toUpperCase());

    monthlyData.add(String.valueOf(rpType));
    monthlyData.add(new Boolean(nominal));
    monthlyData.add(String.valueOf(valNominal));

    if(rpType==SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON)
        monthlyData.add("");
    else
        monthlyData.add("NIGHT");

    session.putValue("CANTEEN_MONTHLY_SUMMARY_DATA", monthlyData);
%>

<html>
<script language="JavaScript" type="text/JavaScript">
<!--
    function deptChange() {
        document.frmMonthlyReport.command.value = "<%=Command.GOTO%>";
        document.frmMonthlyReport.action = "summary_periodic_report.jsp";
        document.frmMonthlyReport.submit();
    }

    function cekDate(){
        var mth1 = document.frmMonthlyReport.cmbDate_mn.value
        var mth2 = document.frmMonthlyReport.cmbDateto_mn.value
        if(mth1==mth2){
            var yr1 = document.frmMonthlyReport.cmbDate_yr.value
            var yr2 = document.frmMonthlyReport.cmbDateto_yr.value
            if(yr1!=yr2){
                alert("please select same year.");
                return false;
            }
        }else{
            alert("please select same month.");
            return false;
        }
        return true;
    }	

    function cmdView() {
        if(cekDate()){
            document.frmMonthlyReport.command.value = "<%=Command.LIST%>";
            document.frmMonthlyReport.hidden_dept_name.value = document.frmMonthlyReport.DEPARTMENT_ID.options[document.frmMonthlyReport.DEPARTMENT_ID.selectedIndex].text;
            <%if(oidDepartment==0){%>
                document.frmMonthlyReport.hidden_sec_name.value = "";
            <%}else{%>
                document.frmMonthlyReport.hidden_sec_name.value = document.frmMonthlyReport.SECTION_ID.options[document.frmMonthlyReport.SECTION_ID.selectedIndex].text;
            <%}%>
            document.frmMonthlyReport.action = "summary_periodic_report.jsp";
            document.frmMonthlyReport.submit();
        }
    }

    function reportPdf() {
        var linkPage = "<%=printroot%>.report.canteen.MonthlySummaryVisitation";
        handle = window.open("", "<%=canteenWindowName%>");
        handle.close();
        handle = window.open(linkPage, "<%=canteenWindowName%>");
        handle.focus();
    }

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<!-- #BeginTemplate "/Templates/main.dwt" --><!-- DW6 -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - </title>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" -->
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
</SCRIPT>
<!-- #EndEditable -->
</head>

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="<%=pageWidth%>%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
      <!-- #BeginEditable "header" -->
      <%@ include file = "../../../main/header.jsp" %>
      <!-- #EndEditable -->
    </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report
                  &gt;&gt; Summary &gt;&gt; Periodic Meal Record<!-- #EndEditable -->
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
							  <form name="frmMonthlyReport" method="post" action="">
                              <input type="hidden" name="command" value="<%=iCommand%>">
                              <input type="hidden" name="hidden_sec_name" value="<%=secName%>">
                              <input type="hidden" name="hidden_dept_name" value="<%=departmentName%>">

		    				  <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                                <tr>
                                  <td><table width="760">
                                    <tr>
                                      <td width="129"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                      <td width="15">:</td>
                                      <td width="600">
                                        <%
                                        Vector dept_value = new Vector();
                                      	Vector dept_key = new Vector();

                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                      	dept_value.add("All Department ...");
                                      	dept_key.add("0");

                                        String selectDept = String.valueOf(oidDepartment);
                                      	for (int item = 0; item < listDept.size(); item++) {
                                            Department dept = (Department) listDept.get(item);
                                            dept_value.add(dept.getDepartment());
                                            dept_key.add(String.valueOf(dept.getOID()));
                                        }
                                       %>
                                        <%=ControlCombo.draw("DEPARTMENT_ID", "formElemen", null, selectDept, dept_key, dept_value, "onchange=\"javascript:deptChange();\"")%> </td>
                                    </tr>
                                                    <%
                                                        if (oidDepartment > 0)
                                                        {
														%>
                                                <tr>

                                                <td>Outlet</td>
                                                  <td>:</td>
                                                  <td>
                                                        <%
                                                            String whereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + oidDepartment;
                                                            String orderSec = PstSection.fieldNames[PstSection.FLD_SECTION];
                                                            Vector sec_key = new Vector(1,1);
                                                            Vector sec_value = new Vector(1,1);
                                                            sec_key.add("All Outlet ...");
                                                            sec_value.add("0");
                                                            Vector listSection = PstSection.list(0, 0, whereSec, orderSec);
                                                            String selectValueSec = String.valueOf(oidSection);
                                                            for (int i = 0; i < listSection.size(); i++)
                                                            {
                                                                Section section = (Section) listSection.get(i);
                                                                sec_key.add(section.getSection());
                                                                sec_value.add(String.valueOf(section.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("SECTION_ID","elementForm", null, selectValueSec, sec_value, sec_key, "") %>
                                                    </td>
                                                </tr>
                                                <%}%>
                                    <tr>
                                      <td>Period</td>
                                      <td>:</td>
                                      <td>
                                        <%
                                        Date currentDate = new Date();
                                        int currentYear = currentDate.getYear();
                                        int installationYear = installationDate.getYear();
                                        int diffYear = installationYear - currentYear;
                                       %>
                                        <%=ControlDate.drawDate("cmbDate", date == null || iCommand == Command.NONE ? new Date() : date, "formElemen", 0, diffYear)%> to <%=ControlDate.drawDate("cmbDateto", dateto == null || iCommand == Command.NONE ? new Date() : dateto, "formElemen", 0, diffYear)%></td>
                                    </tr>
                                    <%if(nominal){%>
                                    <tr>
                                      <td valign="top">Nominal</td>
                                      <td valign="top">:</td>
                                      <td>
									  <%
										FRMHandler objFRMHandler = new FRMHandler();
										objFRMHandler.setDecimalSeparator(",");
										objFRMHandler.setDigitSeparator(".");												  
									  %>									  
                                        <input name="vl_nominal" type="text" class="numberright" value="<%=objFRMHandler.userFormatStringDecimal(valNominal)%>">
                                      </td>
                                    </tr>
                                    <%}%>
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
                                      <td valign="top">Report Type </td>
                                      <td valign="top">:</td>
                                      <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
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
                                      <td></td>
                                      <td></td>
                                      <td><table width="447">
                                          <tr>
                                            <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Visitation"></a></td>
                                            <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                            <td width="397" class="command" nowrap><a href="javascript:cmdView()">View Visitation</a></td>
                                          </tr>
                                      </table></td>
                                    </tr>
                                  </table></td>
                                  </tr>
                                <tr>
                                  <td><table border="0" cellspacing="2" cellpadding="2" width="100%">
                                    <% if (iCommand == Command.LIST) { %>
                                    <tr>
                                      <td><hr></td>
                                    </tr>
                                    <tr>
                                      <td><%=listReport%></td>
                                    </tr>
                                    <% if (showPrintItem) { %>
                                    <tr>
                                      <td>
                                        <table width="321" border="0" cellpadding="1" cellspacing="1">
                                          <tr>
                                            <td width="8%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" height="24" width="24" border="0"></a></td>
                                            <td width="92%"><b><a href="javascript:reportPdf()" class="buttonlink">Print Monthly Visitation</a></b></td>
                                          </tr>
                                      </table></td>
                                    </tr>
                                    <% } %>
                                    <% } %>
                                  </table></td>
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
