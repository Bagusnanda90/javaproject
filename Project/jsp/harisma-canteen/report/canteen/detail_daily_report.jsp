
<!-- use java language -->
<%@ page language = "java" %>

<!-- use qdep package -->
<%@ page import = "com.dimata.gui.jsp.ControlCombo" %>
<%@ page import = "com.dimata.gui.jsp.ControlDate" %>
<%@ page import = "com.dimata.gui.jsp.ControlList" %>
<%@ page import = "com.dimata.util.Command" %>
<%@ page import = "com.dimata.qdep.form.FRMQueryString.*" %>
<%@ page import = "com.dimata.harisma.entity.canteen.*" %>
<!-- use harisma package -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.canteen.DetailDailyVisitation" %>
<%@ page import = "com.dimata.harisma.session.canteen.SessCanteenVisitation" %>

<%@ include file = "../../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_REPORT_DETAIL, AppObjInfo.OBJ_DETAIL_DAILY); %>
<%@ include file = "../../../main/checkuser.jsp" %>
<%// boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT)); %>


<%
 /* --- get web page parameter --- */

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

 int iCommand = FRMQueryString.requestCommand(request);
 long oidDepartment = FRMQueryString.requestLong(request, "DEPARTMENT_ID"); 
 long oidSection = FRMQueryString.requestLong(request, "SECTION_ID");
 long oidSchCanteen = FRMQueryString.requestLong(request, "SCH");
 int rpType = FRMQueryString.requestInt(request, "MORNING_AFTERNOON_NIGHT_ID");
 String departmentName = FRMQueryString.requestString(request, "hidden_dept_name");
 String sectionName = FRMQueryString.requestString(request, "hidden_section_name");
 Date date = FRMQueryString.requestDate(request, "date");

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

    public Vector drawList(Vector dataOfReport) {
        int dataReportSize = dataOfReport.size();
        Vector results = new Vector();
        if (dataReportSize > 0) {
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("NO.", "4%", "1", "1");
            ctrlist.addHeader("PAYROLL", "10%", "1", "1");
            ctrlist.addHeader("NAME", "36%", "1", "1");
            ctrlist.addHeader("SCHEDULE", "10%", "1" , "1");
            ctrlist.addHeader("SWEPT TIME", "27%", "1", "1");
            ctrlist.addHeader("VISITS", "13%", "1", "1");
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();

            DetailDailyVisitation detailDailyVisitation = null;
            Vector visitTimes = null;
            String strVisitTimes = "";
            int visitTimesSize;
            int queue;

            java.util.Vector rowx = null;

            String beginStyle = "";
            String endStyle = "";

            int numVisits = 0;

            int totalVisits = 0;
            int totalNightShift = 0;

            for (int i = 0; i < dataReportSize; i++) {
                detailDailyVisitation = (DetailDailyVisitation) dataOfReport.get(i);

                if (detailDailyVisitation.getNightShift()) {
                    beginStyle = "<font color=\"#0099FF\"><b>";
                    endStyle = "</b></font>";
                } else {
                    beginStyle = "";
                    endStyle = "";
                }

                rowx = new Vector();

                rowx.add("<div align=\"right\">" + beginStyle + String.valueOf(i + 1) + endStyle + "&nbsp;</div>");
                rowx.add("&nbsp;" + beginStyle + detailDailyVisitation.getEmployeePayroll() + endStyle);
                rowx.add("&nbsp;" + beginStyle + detailDailyVisitation.getEmployeeName() + endStyle);
                rowx.add("&nbsp;" + beginStyle + detailDailyVisitation.getScheduleSymbol() + endStyle);
                rowx.add("&nbsp;" + beginStyle + detailDailyVisitation.getStrVisitTimes() + endStyle);
                rowx.add("<div align=\"right\">" + beginStyle + detailDailyVisitation.getNumVisits() + endStyle + "&nbsp;</div>");

                lstData.add(rowx);
                totalVisits = totalVisits + detailDailyVisitation.getNumVisits();
            }

            rowx = new java.util.Vector();
            rowx.add("");
            rowx.add("");
            rowx.add("<div align=\"center\"><b>TOTAL</b></div>");
            rowx.add("");
            rowx.add("");
            rowx.add("<div align=\"right\"><b>" + String.valueOf(totalVisits) + "</b>&nbsp;</div>");
            lstData.add(rowx);

            results.add(String.valueOf(DATA_PRINT));
            results.add(ctrlist.drawList());
            results.add(String.valueOf(totalVisits - totalNightShift));
            results.add(String.valueOf(totalNightShift));
            results.add("1");
        } else {
            results.add(String.valueOf(DATA_NULL));
            results.add("<div class=\"msginfo\">No visitation record found ...</div>");
            results.add("0");
            results.add("0");
            results.add("0");
        }
        return results;
    }
	
	
%>

<%
    
    Vector dataOfVisits = new Vector();
    if (iCommand == Command.LIST) {
        SessCanteenVisitation sessCanteenVisitation = new SessCanteenVisitation();
        //dataOfVisits = sessCanteenVisitation.getDetailVisitationReportDaily(oidDepartment, oidSection, date, rpType);
        dataOfVisits = sessCanteenVisitation.getDetailVisitationReportDaily(oidDepartment, oidSection, date, scheduleId , rpType);

    }

    Vector dataOfReport = drawList(dataOfVisits);
    String listReport = String.valueOf(dataOfReport.get(1));

    int flag = Integer.parseInt((String) dataOfReport.get(4));
    if (session.getValue("CANTEEN_DAILY_DETAIL_DATA") != null) {
        session.removeValue("CANTEEN_DAILY_DETAIL_DATA");
    }

    /* --- build data that transferred to session --- */
    Vector dailyDetailData = new Vector();
    if(oidDepartment==0)
        departmentName = "";

    dailyDetailData.add(departmentName);
    dailyDetailData.add(date);
    dailyDetailData.add(dataOfVisits);

    if(oidSection==0)
        sectionName = "";
    dailyDetailData.add(sectionName);

    if(rpType==0)
        dailyDetailData.add("");
    else
        dailyDetailData.add("NIGHT");
    session.putValue("CANTEEN_DAILY_DETAIL_DATA", dailyDetailData);
%>

<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Canteen Detail Daily Report</title>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->

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

    function changedept(){
        document.frmDailyReport.command.value = "<%=Command.SEARCH%>";
        document.frmDailyReport.action = "detail_daily_report.jsp";
        document.frmDailyReport.submit();
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
  </tr><tr>
   <td bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Reports >> Detail >> Daily Report<!-- #EndEditable -->
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
                                    <form name="frmDailyReport" method="POST">
                                     <input type="hidden" name="command" value="<%=iCommand%>">
                                     <input type="hidden" name="hidden_dept_name" value="<%=departmentName%>">
                                     <input type="hidden" name="hidden_section_name" value="<%=sectionName%>">
                                     <table width="100%">
                                     <tr>
                                      <td width="1%"></td>
                                      <td><table width="760"><tr>
                                       <td width="129"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                       <td width="15">:</td>
                                       <td width="600">
                                       <%
                                        Vector dept_value = new Vector();
                                      	Vector dept_key = new Vector();

                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                      	dept_value.add("Please select a department");
                                      	dept_key.add("0");

                                        String selectDept = String.valueOf(oidDepartment);                                        

                                      	for (int item = 0; item < listDept.size(); item++) {
                                         Department dept = (Department) listDept.get(item);
                                         dept_value.add(dept.getDepartment());
                                         dept_key.add(String.valueOf(dept.getOID()));
                                        }

                                       %>
                                       <%=ControlCombo.draw("DEPARTMENT_ID", "formElemen", null, selectDept, dept_key, dept_value, "onChange=\"javascript:changedept()\"")%>
                                       </td>
                                      </tr>
                                          <tr>
                                                <td>Outlet</td>
                                            <td>:</td>
                                            <td><%
                                        Vector sec_value = new Vector();
                                      	Vector sec_key = new Vector();
                                        String whereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
                                        Vector listSec = PstSection.list(0, 0, whereClause , PstSection.fieldNames[PstSection.FLD_SECTION]);
                                      	sec_value.add("All Outlet ...");
                                      	sec_key.add("0");

                                        String selectSec = String.valueOf(oidSection);
                                      	for (int item = 0; item < listSec.size(); item++) {
                                            Section section = (Section) listSec.get(item);
                                            sec_value.add(section.getSection());
                                            sec_key.add(String.valueOf(section.getOID()));
                                        }
                                       %>
                                       <%=ControlCombo.draw("SECTION_ID", "formElemen", null, selectSec, sec_key, sec_value, "")%>
</td>
                                          </tr>
                                          <tr>
                                       <td>Date</td>
                                       <td>:</td>
                                       <td>
                                       <%
                                        Date currentDate = new Date();
                                        int currentYear = currentDate.getYear();
                                        int installationYear = installationDate.getYear();
                                        int diffYear = installationYear - currentYear;
                                       %>
                                       <%=ControlDate.drawDate("date", date == null || iCommand == Command.NONE ? new Date() : date, "formElemen", 0, diffYear)%>
                                       </td>
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
                                       <td><table width="447"><tr>
                                        <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnSearchOn.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Visitation"></a></td>
                                        <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                        <td width="397" class="command" nowrap><a href="javascript:cmdView()">View Visitation</a></td>
                                       </tr></table></td>
                                      </tr></table></td></tr>
                                      <% if (iCommand == Command.LIST) { %>
                                      <tr><td align="center" colspan="2">
                                       <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                       <% if (dataOfReport.size() > 0) { %>
                                        <tr><td><hr></td></tr>
                                        <tr><td><%=listReport%></td></tr>
                                        <% if (flag > 0) { %>
                                        <tr>
                                          <td>&nbsp;</td>
                                        </tr>
                                        <% } %>
                                       <% if (privPrint && Integer.parseInt((String) dataOfReport.get(0)) > 0) { %>
                                        <tr><td>
                                         <table border="0" cellpadding="1" cellspacing="1">
                                          <tr>
                                           <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                           <td width="87%"><b><a href="javascript:reportPdf()" class="buttonlink">Print Daily Visitation</a></b></td>
                                          </tr>
                                         </table>
                                        </td></tr>
                                       <%
                                         }
                                        }
                                       %>
                                       </table>
                                      </td>
                                     </tr>
                                     <%
                                      }
                                     %>
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

<script language="javascript">

 function cmdView() {
    if(document.frmDailyReport.DEPARTMENT_ID.value != "0"){
        document.frmDailyReport.command.value = "<%=Command.LIST%>";
        document.frmDailyReport.hidden_dept_name.value = document.frmDailyReport.DEPARTMENT_ID.options[document.frmDailyReport.DEPARTMENT_ID.selectedIndex].text;
        document.frmDailyReport.hidden_section_name.value = document.frmDailyReport.SECTION_ID.options[document.frmDailyReport.SECTION_ID.selectedIndex].text;
        document.frmDailyReport.action = "detail_daily_report.jsp";
        document.frmDailyReport.submit();
    }else{
        alert("Please select a department.");
    }
 }

 function reportPdf() {
    var linkPage = "<%=printroot%>.report.canteen.DailyDetailVisitation";
    handle = window.open("", "<%=canteenWindowName%>");
    handle.close();
    handle = window.open(linkPage, "<%=canteenWindowName%>");
    handle.focus();

 }
</script>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
