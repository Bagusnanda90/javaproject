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
<%@ page import ="com.dimata.harisma.session.sickness.*"%>
<%@ page import ="java.lang.Math.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_SICK_DAYS_REPORT, AppObjInfo.OBJ_SICK_DAYS_MONTHLY_REPORT);%>
<%@ include file = "../../main/checkuser.jsp" %>


<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
    public String drawList(Hashtable hSicknessYearly) {
        
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

        numHash = hSicknessYearly.keys();
        while (numHash.hasMoreElements()) {
            rowx = new Vector(1, 1);

            int total = 0;
            int perMonth = 0;

            str = (String) numHash.nextElement();
            /*System.out.println(str + ": " +
             hLatenessYearly.get(str));*/

            SicknessYearly lateYear = (SicknessYearly) hSicknessYearly.get(str);

            rowx.add("" + jml++);
            rowx.add(lateYear.getEmpNum());
            rowx.add(lateYear.getEmpName());
            for (int m = 0; m < lateYear.getTotalMonthly().size(); m++) {
                SicknessMonthlyRekap monthlyLate = (SicknessMonthlyRekap) lateYear.getTotalMonthly().get(m);
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
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidSection = FRMQueryString.requestLong(request, "section");
    long periodId = FRMQueryString.requestLong(request, "period");
    String footer = FRMQueryString.requestString(request, "footer");
    int setFooter = FRMQueryString.requestInt(request, "setFooter");
    String empNum = FRMQueryString.requestString(request, "emp_number");
    String fullName = FRMQueryString.requestString(request, "full_name");
    
    int year = FRMQueryString.requestInt(request, "year");
    
    String sOIDSickLeave = "";
    String sOIDSickLeaveWoDC = "";
    String sISickWDC = "";
    String sISickWoDC = "";
    try {
        sOIDSickLeave = PstSystemProperty.getValueByName("OID_SICK_LEAVE");
        sOIDSickLeaveWoDC = PstSystemProperty.getValueByName("OID_SICK_LEAVE_WO_DC");

        sISickWDC = PstSystemProperty.getValueByName("SICK_REASON_WITH_DC");
        sISickWoDC = PstSystemProperty.getValueByName("SICK_REASON_NOT_DC");
    } catch (Exception ex) {
        System.out.println("Exception:" + ex);
    }
//Date date = FRMQueryString.requestDate(request,"month_year");
    
    Date date = new Date();
    String periodName = "";
    
    SicknessYearly sicknessYearly = new SicknessYearly("", "");
    SessSickness sessSickness = new SessSickness();

    Vector<SicknessMonthlyRekap> totalSicknessMonthlyRekap = new Vector();

    Hashtable<String, SicknessYearly> hSicknessYearly = new Hashtable();
    
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
            
            Vector listSicknessPresence = new Vector(1, 1);
                //listSicknessPresence = SessSickness.listSicknessDataMonthly(oidDepartment,date);
                // untuk aplikasi yang membutuhkan parameter section.Jika cukup dengan parameter department pake yang atas
            listSicknessPresence = SessSickness.listSicknessDataMonthly(oidDepartment, date, oidSection, empNum.trim(), fullName.trim(), sOIDSickLeave, sOIDSickLeaveWoDC, sISickWDC, sISickWoDC);
            
            totalSicknessMonthlyRekap = sessSickness.getListMonth(listSicknessPresence, dateOfMonth, dEndDateOfMonth, sOIDSickLeave, sOIDSickLeaveWoDC, sISickWDC, sISickWoDC);
            
            for (int j = 0; j < totalSicknessMonthlyRekap.size(); j++) {
                SicknessMonthlyRekap sicknessMonthlyRekap = totalSicknessMonthlyRekap.get(j);
                SicknessYearly sicknessYear = hSicknessYearly.get(sicknessMonthlyRekap.getEmpNum());
                if (sicknessYear == null) {
                    SicknessYearly empSicknessYearly = new SicknessYearly(sicknessMonthlyRekap.getEmpNum(), sicknessMonthlyRekap.getEmpName());
                    empSicknessYearly.setMonthlyRekap(sicknessMonthlyRekap);
                    hSicknessYearly.put(empSicknessYearly.getEmpNum(), empSicknessYearly);
                } else {
                    sicknessYear.setMonthlyRekap(sicknessMonthlyRekap);
                }
            }
        }
    }
    session.putValue("listresult", hSicknessYearly);

%>
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Sickness Report</title>
        <script language="JavaScript">
            <%
                Vector listreason = new Vector(1, 1);
                listreason = PstReason.list(0, 500, "", PstReason.fieldNames[PstReason.FLD_REASON_CODE]);


            %>

                function cmdView(){
                    document.frpresence.command.value="<%=Command.LIST%>";
                    document.frpresence.action="yearly_sickness.jsp";
                    document.frpresence.submit();
                }

                function cmdFooter(){
                    document.frpresence.setFooter.value="1";
                    document.frpresence.action="yearly_sickness.jsp";
                    document.frpresence.submit();
                }

                function reportPdf(){	 
                    var linkPage = "<%=printroot%>.report.sickness.MonthlySicknessPdf";  
                    //window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
                    window.open(linkPage);  				
                }
                
                function cmdExportExcel(){
                 
                    var linkPage = "<%=approot%>/report/sickness/export_excel/export_excel_yearly_sickness.jsp";    
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
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" />
        <!-- #EndEditable --> 
    </head>
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
        <style>
            .fakeContainer { /* The parent container */
                margin: 0px;
                padding: 0px;
                border: none;
                width: 1310px; /* Required to set */
                height: 400px; /* Required to set */
                overflow: hidden; /* Required to set */
            }
            .skinCon {
                float: left;
                margin: 0px;
                border: none;
                width: 1310px;
            }

        </style>
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
                                                &gt; Yearly Sickness<!-- #EndEditable --> </strong></font> 
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
                                                                                    <input type="hidden" name="setFooter" value="<%=setFooter%>">
                                                                                    <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <!-- update by satrya 2012-10-03 -->
                                                                                        <tr> 

                                                                                            <td width="9%"> <div align="left">Payrol Num </div></td>
                                                                                            <td width="88%">:
                                                                                                <input type="text" size="20" name="emp_number"  value="<%= sessSickness.getEmpNum() != null ? sessSickness.getEmpNum() : empNum%>" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
                                                                                        </tr>
                                                                                        <tr>

                                                                                            <td width="9%"> <div align="left"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME)%></div></td>
                                                                                            <td width="88%">:
                                                                                                <input type="text" size="50" name="full_name"  value="<%= sessSickness.getEmpFullName() != null ? sessSickness.getEmpFullName() : fullName%>" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
                                                                                        </tr>

                                                                                        <!-- end -->
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
                                                                                            <td width="9%" align="right" nowrap> 
                                                                                                <div align="left"><a href="javascript:cmdFooter()">Set Footer</a></div>
                                                                                            </td>
                                                                                            <% if (setFooter == 1) {%>
                                                                                            <td  colspan="2"> : <input name="footer" type="text" size="85" value="<%=footer%>">
                                                                                            </td>
                                                                                            <td width="1" class="command" nowrap> 
                                                                                            </td>
                                                                                            <%
                                                                                                }
                                                                                            %>	
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="9%" nowrap> 
                                                                                                <div align="left"></div>
                                                                                            </td>
                                                                                            <td width="88%"> 
                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="239">
                                                                                                    <tr> 
                                                                                                        <td width="26"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Sickness"></a></td>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="209" class="command" nowrap><a href="javascript:cmdView()">View 
                                                                                                                Sickness</a></td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <% if (iCommand == Command.LIST) {%>
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <% if (hSicknessYearly != null && hSicknessYearly.size() > 0) {%>
                                                                                        <tr><td><hr></td></tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%=drawList(hSicknessYearly)%>											
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%if (privPrint) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                                                                                    <tr>
                                                                                                        <td width="17%"><a href="javascript:cmdExportExcel()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0" alt="Print Yearly Sickness"></a></td>												  
                                                                                                        <td width="83%">
                                                                                                            <b><a href="javascript:cmdExportExcel()" class="command">Export to Excel</a></b> 
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
                                                                                                <%
                                                                                                    out.println("<div class=\"msginfo\">&nbsp;&nbsp;No sickness data found ...</div>");
                                                                                                %>
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
        <script type="text/javascript" src="<%=approot%>/javascripts/superTables.js"></script>
        <script type="text/javascript">
                //<![CDATA[

                (function () {
                    new superTable("demoTable", {
                        cssSkin : "sDefault",
                        fixedCols : 3,
                        headerRows : 2
                    });
                })();

                //]]>
        </script>
    </body>
    <!-- #BeginEditable "script" --> 
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
