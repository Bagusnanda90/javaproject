<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.Vector"%>
<%@ page import ="java.util.Calendar"%>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.attendance.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import ="com.dimata.harisma.session.specialdisp.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_SPECIAL_DISCREPANCIES_REPORT, AppObjInfo.OBJ_SPECIAL_DISCREPANCIES_MONTHLY_REPORT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!  
    public String drawList(Hashtable hSpecialDispYearly) {
        Vector result = new Vector(1, 1);
        
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

        numHash = hSpecialDispYearly.keys();
        while (numHash.hasMoreElements()) {
            rowx = new Vector(1, 1);

            int total = 0;
            int perMonth = 0;

            str = (String) numHash.nextElement();
            /*System.out.println(str + ": " +
             hLatenessYearly.get(str));*/

            SpecialDispYearly lateYear = (SpecialDispYearly) hSpecialDispYearly.get(str);

            rowx.add("" + jml++);
            rowx.add(lateYear.getEmpNum());
            rowx.add(lateYear.getEmpName());
            for (int m = 0; m < lateYear.getTotalMonthly().size(); m++) {
                SpecialDispMonthlyRekap monthlyLate = (SpecialDispMonthlyRekap) lateYear.getTotalMonthly().get(m);
                if (monthlyLate.getTotalMonth() != 0) {
                    perMonth = monthlyLate.getTotalMonth();

                    rowx.add(""+perMonth);

                    total = total + perMonth;

                } else {
                    rowx.add("-");
                }
            }

            if (total != 0) {
                rowx.add(""+total);
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
    int iCommand = FRMQueryString.requestCommand(request);
    int year = FRMQueryString.requestInt(request, "year");
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidSection = FRMQueryString.requestLong(request, "section");
    long periodId = FRMQueryString.requestLong(request, "period");
//Date date = FRMQueryString.requestDate(request,"month_year");

    Date date = new Date();
    String periodName = "";

    SpecialDispYearly specialDispYearly = new SpecialDispYearly("", "");
    SessSpecialDisp sessSpecialDisp = new SessSpecialDisp();

    Vector<SpecialDispMonthlyRekap> totalSpecialDispMonthlyRekap = new Vector();

    Hashtable<String, SpecialDispYearly> hSpecialDispYearly = new Hashtable();

    Vector listSpecialDispPresence = new Vector(1, 1);
    if (iCommand == Command.LIST) {
        
        Vector listPeriod = PstPeriod.list(0, 0, "YEAR(end_date) = " + year, "start_date, end_date");
        Vector vList = new Vector(1, 1);

        for (int d = 0; d < listPeriod.size(); d++) {
            Period period = (Period) listPeriod.get(d);

            try {
                Period pr = PstPeriod.fetchExc(period.getOID());
                date = pr.getStartDate();
            } catch (Exception e) {
                System.out.println("Exception period in latnessMontly" + e);
            }
            
            // get maximum date of selected month
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.setTime(date);
            int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            
            Date dEndDateOfMonth = period.getEndDate() == null ? new Date() : period.getEndDate();
            //listSpecialDispPresence = SessSpecialDisp.listSpecialDispDataMonthly(oidDepartment,date);
            // untuk aplikasi yang membutuhkan parameter section. JIka cukup dengan parameter Department pake yang atas
            listSpecialDispPresence = SessSpecialDisp.listSpecialDispDataMonthly(oidDepartment, date, oidSection);
            
            totalSpecialDispMonthlyRekap = sessSpecialDisp.getListMonth(listSpecialDispPresence, dateOfMonth, dEndDateOfMonth);
            
            for (int j = 0; j < totalSpecialDispMonthlyRekap.size(); j++) {
                SpecialDispMonthlyRekap specialDispMonthlyRekap = totalSpecialDispMonthlyRekap.get(j);
                SpecialDispYearly specialDispYear = hSpecialDispYearly.get(specialDispMonthlyRekap.getEmpNum());
                if (specialDispYear == null) {
                    SpecialDispYearly empSpecialDispYearly = new SpecialDispYearly(specialDispMonthlyRekap.getEmpNum(), specialDispMonthlyRekap.getEmpName());
                    empSpecialDispYearly.setMonthlyRekap(specialDispMonthlyRekap);
                    hSpecialDispYearly.put(empSpecialDispYearly.getEmpNum(), empSpecialDispYearly);
                } else {
                    specialDispYear.setMonthlyRekap(specialDispMonthlyRekap);
                }
            }
        }
        session.putValue("listresult", hSpecialDispYearly);
    }

%>
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Special Dispensation Report</title>
        <script language="JavaScript">
            function cmdView(){
                document.frpresence.command.value="<%=Command.LIST%>";
                document.frpresence.action="yearly_specialdisp.jsp";
                document.frpresence.submit();
            }

            function reportPdf(){	 
                var linkPage = "<%=printroot%>.report.specialdisp.MonthlySpecialDispPdf";  
                //window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
                window.open(linkPage);  				
            }
            
            function cmdExportExcel(){
                 
            var linkPage = "<%=approot%>/report/specialdisp/export_excel/export_excel_yearly_specialdisp.jsp";    
            var newWin = window.open(linkPage,"attdReportDaily","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
            newWin.focus();
        }

            //-------------- script control line -------------------
            function MM_swapImgRestore() { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                }

                function MM_findObj(n, d) { //v4.0
                    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                    if(!x && document.getElementById) x=document.getElementById(n); return x;
                }

                function MM_swapImage() { //v3.0
                    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                        if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                }
        </script>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        <!-- #EndEditable --> 
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
            <%}%>
            <tr> 
                <td width="88%" valign="top" align="left"> 
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr> 
                            <td width="100%"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                                                &gt; Yearly Special Dispensation<!-- #EndEditable --> </strong></font> 
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top"> <!-- #BeginEditable "content" -->
                                                                                <form name="frpresence" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <tr> 
                                                                                            <td width="9%" align="right" nowrap> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></div>
                                                                                            </td>
                                                                                            <td width="88%"> : 
                                                                                                <%
                                                                                                    Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                    Vector deptValue = new Vector(1, 1);
                                                                                                    Vector deptKey = new Vector(1, 1);
                                                                                                    deptValue.add("0");
                                                                                                    deptKey.add(" - ALL - ");
                                                                                                    for (int d = 0; d < listDepartment.size(); d++) {
                                                                                                        Department department = (Department) listDepartment.get(d);
                                                                                                        deptValue.add("" + department.getOID());
                                                                                                        deptKey.add(department.getDepartment());
                                                                                                    }
                                                                                                    out.println(ControlCombo.draw("department", null, "" + oidDepartment, deptValue, deptKey));
                                                                                                %>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="9%" align="right" nowrap> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION)%></div>
                                                                                            </td>
                                                                                            <td width="88%"> : 
                                                                                                <%
                                                                                                    Vector listSection = PstSection.list(0, 0, "", "SECTION");
                                                                                                    Vector sectValue = new Vector(1, 1);
                                                                                                    Vector sectKey = new Vector(1, 1);
                                                                                                    sectValue.add("0");
                                                                                                    sectKey.add(" - ALL - ");
                                                                                                    for (int d = 0; d < listSection.size(); d++) {
                                                                                                        Section section = (Section) listSection.get(d);
                                                                                                        sectValue.add("" + section.getOID());
                                                                                                        sectKey.add(section.getSection());
                                                                                                    }
                                                                                                    out.println(ControlCombo.draw("section", null, "" + oidSection, sectValue, sectKey));
                                                                                                %>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="9%" align="right" nowrap>
                                                                                                <div align="left">Year</div>
                                                                                            </td>
                                                                                            <td width="88%">:                                            
                                                                                                <%=ControlDate.drawDateYear("year", year == 0 ? Validator.getIntYear(new Date()) : year, "formElemen", -40)%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="9%" nowrap> 
                                                                                                <div align="left"></div>
                                                                                            </td>
                                                                                            <td width="88%"> 
                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="239">
                                                                                                    <tr> 
                                                                                                        <td width="26"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Special Dispensation"></a></td>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="209" class="command" nowrap><a href="javascript:cmdView()">View 
                                                                                                                Special Dispensation</a></td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>

                                                                                    <% if (iCommand == Command.LIST) {%>
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <% if (hSpecialDispYearly != null && hSpecialDispYearly.size() > 0) {%>
                                                                                        <tr><td><hr></td></tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%=drawList(hSpecialDispYearly)%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%if (privPrint) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                                                                                    <tr>
                                                                                                        <td width="17%"><a href="javascript:cmdExportExcel()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                                                                        <td width="83%"><b><a href="javascript:cmdExportExcel()" class="buttonlink">Export to excel</a></b>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%}%>
                                                                                        <%} else {%>
                                                                                        <tr><td><hr></td></tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <div class="msginfo">&nbsp;&nbsp;No special dispensation data found ...</div>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%}%>									  
                                                                                    </table>
                                                                                    <%}%>									  
                                                                                </form>
                                                                                <!-- #EndEditable --> </td>
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
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" --> 
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
