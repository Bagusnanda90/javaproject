<!-- use java language -->
<%@ page language = "java" %>

<!-- use qdep package -->
<%@ page import = "com.dimata.qdep.form.FRMQueryString,
                   com.dimata.gui.jsp.ControlCombo,
                   com.dimata.harisma.entity.canteen.PstCanteenSchedule,
                   com.dimata.harisma.entity.canteen.CanteenSchedule" %>
<%@ page import = "com.dimata.util.Command" %>
<%@ page import = "com.dimata.gui.jsp.ControlDate" %>
<%@ page import = "com.dimata.gui.jsp.ControlList" %>

<%@ page import = "com.dimata.harisma.session.canteen.SummaryDailyVisitation" %>
<%@ page import = "com.dimata.harisma.session.canteen.SessCanteenVisitation" %>

<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_REPORT_SUMMARY, AppObjInfo.OBJ_SUMMARY_DAILY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%  //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT)); %>

<%
 int iCommand = FRMQueryString.requestCommand(request);
 Date date = FRMQueryString.requestDate(request, "date");
 long oidDepartment = FRMQueryString.requestLong(request, "DEPARTMENT_ID");
 long oidSection = FRMQueryString.requestLong(request, "SECTION_ID");
 int rpType = FRMQueryString.requestInt(request, "MORNING_AFTERNOON_NIGHT_ID");
 String departmentName = FRMQueryString.requestString(request, "hidden_dept_name");
    String secName = FRMQueryString.requestString(request, "hidden_dept_name");


%>

<%!
    int DATA_NULL = 0;
    int DATA_PRINT = 1;

    public java.util.Vector drawList(Vector dataOfReport, int rptType, Vector vListOfCanteenSchedule) {
        int dataReportSize = dataOfReport.size();
        Vector results = new Vector();

        if (dataReportSize > 0) {
            ControlList ctrlist = new ControlList();
            if (rptType == SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON){
                ctrlist.setAreaWidth("100%");
            }else{
                ctrlist.setAreaWidth("90%");
            }
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("NO.", "5%", "1", "1");
            ctrlist.addHeader("OUTLET", "50%", "1", "1");
            int totalSub[] = null;
            if (rptType == SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON){
                totalSub = new int[vListOfCanteenSchedule.size()];
                for (int i = 0; i < vListOfCanteenSchedule.size(); i++) {
                    CanteenSchedule objCanteenSchedule = (CanteenSchedule) vListOfCanteenSchedule.get(i);
                    ctrlist.addHeader("<div align=\"center\">"+objCanteenSchedule.getSName().toUpperCase()+"</div>", "10%", "1", "1");
                }
            }else{
                totalSub = new int[1];
                ctrlist.addHeader("<div align=\"center\">NIGHT</div>", "10%", "1", "1");
            }
            ctrlist.addHeader("<div align=\"center\">TOTAL</div>", "7%", "1", "1");
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            Vector listData = ctrlist.getData();
            ctrlist.reset();

            int totalVisits = 0;
            for (int i = 0; i< dataReportSize; i++) {
                Vector rowx = new Vector();
                SummaryDailyVisitation summaryDailyVisitation = (SummaryDailyVisitation) dataOfReport.get(i);

                rowx.add("<div align=\"right\">" + String.valueOf(i + 1) + "&nbsp;</div>");
                rowx.add("&nbsp;" + summaryDailyVisitation.getDepartmentName());

                Vector vtValues = summaryDailyVisitation.getValues();
                if(vtValues.size()>0){
                    for(int j=0; j<vtValues.size();j++){
                        int tot = totalSub[j];
                        String values = (String)vtValues.get(j);
                        rowx.add("<div align=\"right\">" + values + "&nbsp;</div>");
                        totalSub[j] = Integer.parseInt(values) + tot;
                    }
                }

                rowx.add("<div align=\"right\">" + summaryDailyVisitation.getNumVisits() + "&nbsp;</div>");
                totalVisits += summaryDailyVisitation.getNumVisits();

                listData.add(rowx);
            }

            /**
             * untuk mencari total visitation
             */
            Vector rowx = new java.util.Vector();
            rowx.add("");
            rowx.add("<div align=\"center\"><b>TOTAL</b></div>");
            if (rptType == SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON){
                for (int i = 0; i < vListOfCanteenSchedule.size(); i++) {
                    rowx.add("<div align=\"right\"><b>" + String.valueOf(totalSub[i]) + "</b>&nbsp;</div>");
                }
            }else{
                rowx.add("<div align=\"right\"><b>" + String.valueOf(totalSub[0]) + "</b>&nbsp;</div>");
            }
            rowx.add("<div align=\"right\"><b>" + String.valueOf(totalVisits) + "</b>&nbsp;</div>");
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
    java.util.Vector dataOfVisits = new java.util.Vector();
    if (iCommand == Command.LIST){
        SessCanteenVisitation sessCanteenVisitation = new SessCanteenVisitation();
        dataOfVisits = sessCanteenVisitation.getSummaryDailyVisitation(date, oidDepartment,oidSection, rpType);
    }

    Vector vListOfCanteenSchedule = new Vector();
    if (rpType == SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON){
        vListOfCanteenSchedule = PstCanteenSchedule.list(0, 0, "", PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_TIME_OPEN]);
    }
    Vector dataOfReport = drawList(dataOfVisits,rpType,vListOfCanteenSchedule);
    boolean showPrintItem = (Integer.parseInt((String) dataOfReport.get(0)) > 0 && privPrint);

    String reportList = String.valueOf(dataOfReport.get(1));
    if (session.getValue("CANTEEN_DAILY_SUMMARY_DATA") != null)
        session.removeValue("CANTEEN_DAILY_SUMMARY_DATA");

    Vector summaryDetailData = new Vector();
    summaryDetailData.add(date);
    summaryDetailData.add(dataOfVisits);
    if(oidDepartment==0)
        summaryDetailData.add("");
    else
        summaryDetailData.add(departmentName.toUpperCase());

    summaryDetailData.add(vListOfCanteenSchedule);
    summaryDetailData.add(String.valueOf(rpType));
    if(oidSection==0)
        summaryDetailData.add("");
    else
        summaryDetailData.add(secName.toUpperCase());

    if(rpType==SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON)
        summaryDetailData.add("");
    else
        summaryDetailData.add("NIGHT");

    session.putValue("CANTEEN_DAILY_SUMMARY_DATA", summaryDetailData);
%>

<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Canteen Daily Meal Record</title>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<script language="javascript">
<!--
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
<!-- #EndEditable -->
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong><!-- #BeginEditable "contenttitle" -->Reports 
                  >> Summary >> Daily Meal Record<!-- #EndEditable --> </strong></font> 
                </td>
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
                                  <td valign="top"> <!-- #BeginEditable "content" -->
                                   <form name="frmSummaryReport" method="POST">
                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                    <input type="hidden" name="hidden_dept_name" value="<%=departmentName%>">
                                    <input type="hidden" name="hidden_sec_name" value="<%=secName%>">
                                    <table width="100%"  border="0" cellspacing="1" cellpadding="1">
                                      <tr>
                                        <td><table width="100%">
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
                                                    <%=ControlDate.drawDate("date", date == null || iCommand == Command.NONE ? new Date() : date, "formElemen", 0, diffYear)%> </td>
                                                </tr>
                                                <tr>
                                                  <td valign="top">Report Type </td>
                                                  <td valign="top">:</td>
                                                  <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                                      <tr>
                                                        <td><input name="MORNING_AFTERNOON_NIGHT_ID" type="radio" value="0" <%if(rpType==0){%>checked<%}%>>
                  Morning and Afternoon </td>
                                                      </tr>
                                                      <tr>
                                                        <td><input type="radio" name="MORNING_AFTERNOON_NIGHT_ID" value="1" <%if(rpType==1){%>checked<%}%>>
                  Night </td>
                                                      </tr>
                                                  </table></td>
                                                </tr>
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
                                          <%if (iCommand == Command.LIST){%>
                                          <tr>
                                            <td><hr></td>
                                          </tr>
                                          <tr>
                                            <td><%=reportList%></td>
                                          </tr>
                                          <% if (showPrintItem) { %>
                                          <tr>
                                            <td>
                                              <table>
                                                <tr>
                                                  <td width="17%"><a href="javascript:reportPdf()"><img border="0" height="24" width="24" src="../../images/BtnNew.jpg"></a></td>
                                                  <td width="87%"><b><a href="javascript:reportPdf()" class="buttonlink">Print Daily Visitation</a></b></td>
                                                </tr>
                                            </table></td>
                                          </tr>
                                          <% }} %>
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
  <tr>
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" -->
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>

<script language="javascript">

function deptChange() {
	document.frmSummaryReport.command.value = "<%=Command.GOTO%>";
	document.frmSummaryReport.action = "summary_daily_report.jsp";
	document.frmSummaryReport.submit();
}

 function cmdView() {
    document.frmSummaryReport.command.value = "<%=Command.LIST%>";
    document.frmSummaryReport.hidden_dept_name.value = document.frmSummaryReport.DEPARTMENT_ID.options[document.frmSummaryReport.DEPARTMENT_ID.selectedIndex].text;
    <%
    if(oidDepartment==0){
    %>
        document.frmSummaryReport.hidden_sec_name.value = "";
    <%}else{%>
        document.frmSummaryReport.hidden_sec_name.value = document.frmSummaryReport.SECTION_ID.options[document.frmSummaryReport.SECTION_ID.selectedIndex].text;
    <%}%>
    document.frmSummaryReport.action = "summary_daily_report.jsp";
    document.frmSummaryReport.submit();
 }

 function reportPdf() {
    var linkPage = "<%=printroot%>.report.canteen.DailySummaryVisitation";
    handle = window.open("", "<%=canteenWindowName%>");
    handle.close();
    handle = window.open(linkPage, "<%=canteenWindowName%>");
    handle.focus();

 }
</script>
</html>
