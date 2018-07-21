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
<%!    public String drawListSymbol(Vector objectClass) {

        String strReason = "<table border=\"0\" cellspacing=\"0\"" + "cellpadding=\"1\" bgcolor=\"#E0EDF0\"><tr>";

        for (int i = 0; i < objectClass.size(); i++) {
            Reason reason = (Reason) objectClass.get(i);
            strReason += "<td width=\"15\">" + String.valueOf(reason.getKodeReason()) + "</td><td>=</td><td>" + String.valueOf(reason.getDescription()) + "</td><td width=\"15\"></td>";

            if ((i % 5) == 4) {
                strReason += "</tr>";
            }
        }
        strReason += "</tr></table>";
        return strReason;
    }

%>

<%!    int DATA_NULL = 0;
    int DATA_PRINT = 1;

    /**
     * create list object consist of : first index ==> status object (will
     * displayed or not) second index ==> object string will displayed third
     * index ==> object vector of string used in report on PDF format.
     */
    public Vector drawList(/*JspWriter outObj, */Vector listSicknessPresence, int dateOfMonth, String sOIDSickLeave, String sOIDSickLeaveWoDC, String sISickWDC, String sISickWoDC) {
        Vector result = new Vector(1, 1);
        if (listSicknessPresence != null && listSicknessPresence.size() > 0) {
            int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            //ctrlist.setHeaderStyle("listgentitle");
            ctrlist.setCellStyles("listgensellstyles");
            ctrlist.setRowSelectedStyles("rowselectedstyles");
            ctrlist.setHeaderStyle("listheaderJs");
            ctrlist.addHeader("No", "2%", "2", "0");
            ctrlist.addHeader("Payroll", "6%", "2", "0");
            ctrlist.addHeader("Employee", "16%", "2", "0");
            ctrlist.addHeader("Period", "70%", "0", "" + dateOfMonth);
            ctrlist.addHeader("" + (startDatePeriod), "2%", "0", "0");
            for (int j = 0; j < dateOfMonth - 1; j++) {
                if (startDatePeriod == dateOfMonth) {
                    startDatePeriod = 1;
                    ctrlist.addHeader("" + (startDatePeriod), "2%", "0", "0");
                } else {
                    startDatePeriod = startDatePeriod + 1;
                    ctrlist.addHeader("" + (startDatePeriod), "2%", "0", "0");
                }

            }
            ctrlist.addHeader("Total", "3%", "2", "0");

            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            ctrlist.reset();
            //ctrlist.drawListHeaderWithJs(outObj);//header
            // vector of data will used in pdf report
            Vector vectDataToPdf = new Vector(1, 1);
            //Vector rowx = new Vector(1,1);

            int index = -1;
            int iSickWDC = -1;
            int iSickWoDC = -1;

            long oidSickLeave = 0;
            long oidSickLeaveWoDC = 0;
            try {
                if ((sOIDSickLeave != null) && (sOIDSickLeave.length() > 0)) {
                    oidSickLeave = Long.parseLong(sOIDSickLeave);
                }
                if ((sOIDSickLeaveWoDC != null) && (sOIDSickLeaveWoDC.length() > 0)) {
                    oidSickLeaveWoDC = Long.parseLong(sOIDSickLeaveWoDC);
                }
                if ((sISickWDC != null) && (sISickWDC.length() > 0)) {
                    iSickWDC = Integer.parseInt(sISickWDC);
                }
                if ((sISickWoDC != null) && (sISickWoDC.length() > 0)) {
                    iSickWoDC = Integer.parseInt(sISickWoDC);
                }
            } catch (Exception exc) {
                System.out.println("===> NOT SICK LEAVE OID DEFINED => USE ABSENCE MANAGEMENT AND SCHEDULE STATUS AS SICKNESS REPORT");
            }

            int maxSicknessPresence = listSicknessPresence.size();
            int dataAmount = 0;
            try {
                for (int i = 0; i < maxSicknessPresence; i++) {
                    SicknessMonthly absenteeismWeekly = (SicknessMonthly) listSicknessPresence.get(i);
                    String empNum = absenteeismWeekly.getEmpNum();
                    String empName = absenteeismWeekly.getEmpName();
                    Vector empSchedules = absenteeismWeekly.getEmpSchedules();
                    Vector absStatus = absenteeismWeekly.getPresenceStatus();
                    Vector absReason = absenteeismWeekly.getAbsReason();

                    int startPeriodSick = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));

                    startPeriodSick = startPeriodSick - 1;

                    if (oidSickLeave == 0) { // sickness report based on schedule status
                        // check apakah dalam vector schedule ada schedule tipe not OFf/ABSENCE ???			
                        boolean containSchldNotOff = SessSickness.containSchldNotOff(empSchedules);

                        if (containSchldNotOff) {
                            int totalSickness = 0;
                            int absenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
                            Vector rowxMonth = new Vector(1, 1);
                            for (int isch = 0; isch < empSchedules.size(); isch++) {
                                if (startPeriodSick == dateOfMonth) {
                                    startPeriodSick = 1;
                                } else {
                                    startPeriodSick = startPeriodSick + 1;
                                }
                                //String schldSymbol = PstScheduleSymbol.getScheduleSymbol(Long.parseLong(String.valueOf(empSchedules.get(isch))));
                                String schldSymbol = "";
                                int schldCategory = 0;
                                String currAbsence = "";
                                Vector vectSchldSymbol = PstScheduleSymbol.getScheduleData(Long.parseLong(String.valueOf(empSchedules.get(startPeriodSick - 1))));
                                if (vectSchldSymbol != null && vectSchldSymbol.size() > 0) {
                                    Vector vectTemp = (Vector) vectSchldSymbol.get(0);
                                    schldSymbol = String.valueOf(vectTemp.get(0));
                                    schldCategory = Integer.parseInt(String.valueOf(vectTemp.get(1)));
                                }

                                if (schldSymbol != null && schldSymbol.length() > 0) {
                                    int statusAbsence = Integer.parseInt(String.valueOf(absStatus.get(startPeriodSick - 1)));
                                    int reasonAbsence = Integer.parseInt(String.valueOf(absReason.get(startPeriodSick - 1)));
                                    if (!(schldCategory == PstScheduleCategory.CATEGORY_OFF
                                            || schldCategory == PstScheduleCategory.CATEGORY_ABSENCE
                                            || schldCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                            || schldCategory == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                            || schldCategory == PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                            && (statusAbsence == PstEmpSchedule.STATUS_PRESENCE_ABSENCE && reasonAbsence == PstEmpSchedule.REASON_ABSENCE_SICKNESS)) {
                                        currAbsence = "DC";
                                        absenceNull += 1;
                                    } // tambahkan untuk reason cstd,khusus intimas
                                    else if (!(schldCategory == PstScheduleCategory.CATEGORY_OFF
                                            || schldCategory == PstScheduleCategory.CATEGORY_ABSENCE /*|| schldCategory==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                             || schldCategory==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                             || schldCategory==PstScheduleCategory.CATEGORY_LONG_LEAVE*/)
                                            && (statusAbsence == PstEmpSchedule.STATUS_PRESENCE_ABSENCE && reasonAbsence == 4)) {
                                        currAbsence = "CSTD";
                                        absenceNull += 1;
                                    } else {
                                        currAbsence = "";
                                    }
                                    rowxMonth.add(currAbsence);

                                    if (currAbsence != null && currAbsence.length() > 0) {
                                        totalSickness += 1;
                                    }
                                } else {
                                    rowxMonth.add("");
                                }
                            }

                            if (absenceNull > 0) {
                                dataAmount += 1;
                                //rowx = new Vector(1,1);
                                Vector rowx = new Vector(1, 1);
                                rowx.add("" + dataAmount);
                                rowx.add(empNum);
                                rowx.add(empName);
                                rowx.addAll(rowxMonth);
                                rowx.add(String.valueOf(totalSickness));
                                lstData.add(rowx);
                                vectDataToPdf.add(rowx);
                               // ctrlist.drawListRowJs(outObj, 0, rowx, 0);
                            }

                        }
                    } else {
                        // check sick leave based on schedule
                        int totalSickness = 0;
                        int absenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
                        Vector rowxMonth = new Vector(1, 1);
                        for (int isch = 0; isch < empSchedules.size(); isch++) {
                            if (startPeriodSick == dateOfMonth) {
                                startPeriodSick = 1;
                            } else {
                                startPeriodSick = startPeriodSick + 1;
                            }
                            //String schldSymbol = PstScheduleSymbol.getScheduleSymbol(Long.parseLong(String.valueOf(empSchedules.get(isch))));
                            String schldSymbol = "";
                            int schldCategory = 0;
                            String currAbsence = "";
                            long oidSch = Long.parseLong(String.valueOf(empSchedules.get(startPeriodSick - 1)));
                            int iReason = -1;
                            //mencari apakah ada reason misal yg bernilai 0= Dc bernilai 1= not DC
                            if (absReason.get(startPeriodSick - 1) != null) {
                                iReason = Integer.parseInt(String.valueOf(absReason.get(startPeriodSick - 1)));
                            }
                            if (oidSch == oidSickLeave) {
                                currAbsence = "DC";
                                absenceNull += 1;
                                totalSickness += 1;
                                rowxMonth.add(currAbsence);
                            } else if (oidSch == oidSickLeaveWoDC) {
                                currAbsence = "W/O DC";
                                absenceNull += 1;
                                totalSickness += 1;
                                rowxMonth.add(currAbsence);
                            } else if (iReason == iSickWDC) {
                                currAbsence = "DC";
                                absenceNull += 1;
                                totalSickness += 1;
                                rowxMonth.add(currAbsence);
                            } else if (iReason == iSickWoDC) {
                                currAbsence = "W/O DC";
                                absenceNull += 1;
                                totalSickness += 1;
                                rowxMonth.add(currAbsence);
                            } else {
                                rowxMonth.add("");
                            }

                        }

                        if (absenceNull > 0) {
                            dataAmount += 1;
                            //rowx = new Vector(1,1);
                            Vector rowx = new Vector(1, 1);
                            rowx.add("" + dataAmount);
                            rowx.add(empNum);
                            rowx.add(empName);
                            rowx.addAll(rowxMonth);
                            rowx.add(String.valueOf(totalSickness));
                            lstData.add(rowx);
                            vectDataToPdf.add(rowx);
                           // ctrlist.drawListRowJs(outObj, 0, rowx, 0);
                        }


                    }
                    //list rows


                }

                if (dataAmount > 0) {
                    result.add(String.valueOf(DATA_PRINT));
                   // ctrlist.drawListEndTableJs(outObj);
                    result.add(ctrlist.drawList());//ctrlist.drawList());				
                    result.add(vectDataToPdf);
                } else {
                    result.add(String.valueOf(DATA_NULL));

                    result.add("<div class=\"msginfo\">&nbsp;&nbsp;No sickness data found ...</div>");
                    result.add(new Vector(1, 1));
                }
            } catch (Exception ex) {
                System.out.println("Exception list Montly sickness" + ex);
            }
            //ctrlist.drawListEndTableJs(outObj); 
        } else {
            result.add(String.valueOf(DATA_NULL));
            result.add("<div class=\"msginfo\">&nbsp;&nbsp;No sickness data found ...</div>");
            result.add(new Vector(1, 1));
        }

        return result;
    }
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidSection = FRMQueryString.requestLong(request, "section");
    long periodId = FRMQueryString.requestLong(request, "period");
    String footer = FRMQueryString.requestString(request, "footer");
    int setFooter = FRMQueryString.requestInt(request, "setFooter");
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
    Period pr = new Period();
    Date date = new Date();
    String periodName = "";
    try {
        pr = PstPeriod.fetchExc(periodId);
        date = pr.getStartDate();
        periodName = pr.getPeriod();


    } catch (Exception e) {
    }

// get maximum date of selected month
    Calendar newCalendar = Calendar.getInstance();
    newCalendar.setTime(date);
    int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    SessSickness sessSickness = new SessSickness();
    String empNum = FRMQueryString.requestString(request, "emp_number");
    String fullName = FRMQueryString.requestString(request, "full_name");
    Vector listSicknessPresence = new Vector(1, 1);
    if (iCommand == Command.LIST) {
        //listSicknessPresence = SessSickness.listSicknessDataMonthly(oidDepartment,date);
        // untuk aplikasi yang membutuhkan parameter section.Jika cukup dengan parameter department pake yang atas
        listSicknessPresence = SessSickness.listSicknessDataMonthly(oidDepartment, date, oidSection, empNum.trim(), fullName.trim(), sOIDSickLeave, sOIDSickLeaveWoDC, sISickWDC, sISickWoDC);

    }
// process on drawlist
Vector vectResult = drawList(/*out, */listSicknessPresence, dateOfMonth, sOIDSickLeave, sOIDSickLeaveWoDC, sISickWDC, sISickWoDC);
int dataStatus = Integer.parseInt(String.valueOf(vectResult.get(0)));
String listData = String.valueOf(vectResult.get(1));
Vector vectDataToPdf = (Vector) vectResult.get(2);
int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));

// design vector that handle data to store in session/
Vector vectPresence = new Vector(1, 1);
vectPresence.add("" + periodName);
vectPresence.add("" + oidDepartment);
vectPresence.add(vectDataToPdf);
vectPresence.add("" + dateOfMonth);
vectPresence.add("" + startDatePeriod);
vectPresence.add("" + footer);
vectPresence.add(empNum);
vectPresence.add(fullName);
vectPresence.add("" + oidSection);

if (session.getValue("SICKNESS_MONTHLY") != null) {
    session.removeValue("SICKNESS_MONTHLY");
}
session.putValue("SICKNESS_MONTHLY", vectPresence);

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
                    document.frpresence.action="monthly_sickness.jsp";
                    document.frpresence.submit();
                }

                function cmdFooter(){
                    document.frpresence.setFooter.value="1";
                    document.frpresence.action="monthly_sickness.jsp";
                    document.frpresence.submit();
                }

                function reportPdf(){	 
                    var linkPage = "<%=printroot%>.report.sickness.MonthlySicknessPdf";  
                    //window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
                    window.open(linkPage);  				
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
                                                &gt; Monthly Sickness<!-- #EndEditable --> </strong></font> 
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
                                                                                                <div align="left">Period</div>
                                                                                            </td>
                                                                                            <td width="88%">: <%//=ControlDate.drawDateMY("month_year",date==null || iCommand == Command.NONE?new Date():date,"MMM yyyy","formElemen",0,installInterval)%>
                                                                                                <%
                                                                                                    Vector listPeriod = PstPeriod.list(0, 0, "", "PERIOD");
                                                                                                    Vector periodValue = new Vector(1, 1);
                                                                                                    Vector periodKey = new Vector(1, 1);
                                                                                                    //deptValue.add("0");
                                                                                                    //deptKey.add("ALL");

                                                                                                    for (int d = 0; d < listPeriod.size(); d++) {
                                                                                                        Period period = (Period) listPeriod.get(d);
                                                                                                        periodValue.add("" + period.getOID());
                                                                                                        periodKey.add(period.getPeriod());
                                                                                                    }
                                                                                                %> <%=ControlCombo.draw("period", null, "" + periodId, periodValue, periodKey)%>	

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
                                                                                        <tr><td><hr></td></tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%
                                                                                                    
                                                                                                   out.println(listData);
%>											
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%if (dataStatus==DATA_PRINT && privPrint) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                                                                                    <tr>
                                                                                                        <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                                                                        <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
                                                                                                                    Lateness</a></b>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
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
