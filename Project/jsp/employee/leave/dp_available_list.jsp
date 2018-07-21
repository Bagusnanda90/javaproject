<%@page import="com.dimata.harisma.session.leave.SessLeaveApp"%>
<%@page import="com.dimata.harisma.session.leave.SessLeaveApplication"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import="java.util.Date"%>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*"%>
<%@ include file = "../../main/javainit.jsp" %>
<%!    public void jspInit() {
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

    }
%>
<%!    I_Leave leaveConfig = null;

    private String drawList(Vector vectDpStockManagement, float dpTotReq, Date takenDate, Date finishDate, long dpStokId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "10%");
        ctrlist.addHeader("Entitle Date", "20%");
        ctrlist.addHeader("Days Qty ", "20%");
        ctrlist.addHeader("Taken ", "20%");
        ctrlist.addHeader("Will be Taken", "20%");
        ctrlist.addHeader("Eligible", "15%");
        //update by satrya 2012-11-25
        ctrlist.addHeader("Select ", "10%");
        ctrlist.addHeader("Hours ", "10%");

        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdSelect('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        DpStockManagement dpStockMan = new DpStockManagement();
        float dpFullFilled = 0;
        float total = 0;
        // if(vectDpStockManagement!=null && vectDpStockManagement.size()>0){
        // dpStockMan =  (DpStockManagement) vectDpStockManagement.get(0);

        //residueEligible= dpTotReq - dpStockMan.getEligible()  ;
        //}
        int start = 0;

        if (vectDpStockManagement != null && vectDpStockManagement.size() > 0) {
            for (int i = 0; i < vectDpStockManagement.size(); i++) {
                // int start = 0;  
                boolean cekExpired = false;
                dpStockMan = (DpStockManagement) vectDpStockManagement.get(i);
                float dpWillBeTakenPerDp = 0.0f; 

                float dpRoundEligible = dpStockMan.getEligible();

                //dpRoundEligible = ((int)dpRoundEligible) + (float)((int)((dpRoundEligible - (float)((int)dpRoundEligible)) * 100000f )/ 3125) * 0.03125f; 
                //atau bisa juga memakai ini
                if (dpStokId == 0) {
                   
                    //update by satrya 2013-11-06
                    float minBolehCuti=0.03125f;//15 menit
                   
                    if(dpStockMan.getEligible()>=minBolehCuti){
                        
                       /* if(dpStockMan.getEligible() < tigaPuluhMenit){
                            dpRoundEligible = minBolehCuti;
                        }else if(dpStockMan.getEligible() < EmpatPuluhLimaMenit){
                              dpRoundEligible = tigaPuluhMenit;
                        }else if(dpStockMan.getEligible() < limaPuluhSembilanMenit){
                              dpRoundEligible = EmpatPuluhLimaMenit;
                        }*/
                        dpRoundEligible = ((int)dpRoundEligible) + (float)((int)((dpRoundEligible - (float)((int)dpRoundEligible)) * 100000f )/ 3125) * 0.03125f; 
                    
                    }
                      // di hidden by satrya karena dapat merubah nilai elibilenya dan menyebabkan minus dpRoundEligible = (float) (Math.round(dpStockMan.getEligible() / 0.03125) * 0.03125); 
                    if (dpRoundEligible <= 0.000000f) { // (dpStockMan.getEligible()<=0.000000f){
                        //update by satrya 2012-10-22   
                        //start= start + 1;//jika getEligible() minus 
                        continue;
                    }
                   
                    //update by satrya 2013-08-29
                    //if (dpStockMan.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO ) {
                   //update by satrya 2014-01-20
                   Date dtPembanding = (Date)takenDate;
                   if(dtPembanding!=null){
                       dtPembanding.setHours(0);
                       dtPembanding.setMinutes(0);
                       dtPembanding.setSeconds(0);
                   }
                    if (dpStockMan.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO && leaveConfig.getDPExpired() ) {

                        if (dtPembanding!=null && (dpStockMan.getDtExpiredDate().getTime() / (24L * 60L * 60L * 1000L)) < (dtPembanding.getTime()) / (24L * 60L * 60L * 1000L)) {
                            cekExpired = true;
                        }
                    } else if (dpStockMan.getiExceptionFlag() == PstDpStockManagement.EXC_STS_YES && leaveConfig.getDPExpired()) {

                        if (dtPembanding!=null && dpStockMan.getDtExpiredDateExc() != null && (dpStockMan.getDtExpiredDateExc().getTime() / (24L * 60L * 60L * 1000L)) < (dtPembanding.getTime()) / (24L * 60L * 60L * 1000L)) {
                            cekExpired = true;
                        }
                    }
                    if (cekExpired == true) {
                        dpWillBeTakenPerDp = dpWillBeTakenPerDp;
                    } else if (dpRoundEligible >= (dpTotReq - dpFullFilled)) {
                        dpWillBeTakenPerDp = dpTotReq - dpFullFilled;
                    } else {
                        dpWillBeTakenPerDp = dpRoundEligible;
                    }
                    dpFullFilled = dpFullFilled + dpWillBeTakenPerDp;
                }//pengecekan jika dia edit
                else {
                    dpWillBeTakenPerDp = dpTotReq;
                }
                Vector rowx = new Vector(1, 1);
                start = start + 1;
                rowx.add(String.valueOf(start));
                rowx.add("" + dpStockMan.getDtOwningDate());
                rowx.add("" + Formater.formatWorkDayHoursMinutes(dpStockMan.getiDpQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                rowx.add("" + Formater.formatWorkDayHoursMinutes(dpStockMan.getQtyUsed(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                rowx.add("" + Formater.formatWorkDayHoursMinutes(dpStockMan.getToBeTaken(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));///wilbe taken
                rowx.add("" + Formater.formatWorkDayHoursMinutes(dpStockMan.getEligible(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));

                //update by satrya 2012-11-25
                //cekbok & hours

                // rowx.add("<input type=\"checkbox\" name=\"userSelect\" onclick=\"javascript:cmdCalculate()\" value=\"" + residueEligible + "\" >");///wilbe taken
                //residueEligible = residueEligible - dpStockMan.getEligible();
                //String inputName="";
                String inputName = "" + dpStockMan.getOID() + "_d_" + Formater.formatDate(dpStockMan.getDtOwningDate(), "yyyy-MM-dd") + "_t_" + dpWillBeTakenPerDp + "_w_" + dpStockMan.getToBeTaken() + "_e_" + dpStockMan.getEligible();
                if (cekExpired == true) {
                    rowx.add("Expired");
                } else if (dpWillBeTakenPerDp > 0) {
                    rowx.add("<input  type=\"checkbox\" name=\"userSelect\" value=\"" + inputName + "\" checked=\"checked\">");
                } else {
                    rowx.add("<input name=\"userSelect\" value=\"" + inputName + "\"   disabled=\"true\" type=\"checkbox\">");
                }
                total = total + dpWillBeTakenPerDp;
                rowx.add(Formater.formatWorkDayHoursMinutes(dpWillBeTakenPerDp, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                //String spanX = "<span id=\"sp-click\">Checked: true</span>"; 
                // if(spanX.equalsIgnoreCase("<span id=\"sp-click\">Checked: true</span>")){
                //   residueEligible= dpTotReq - dpStockMan.getEligible();
                //}

                lstData.add(rowx);

                lstLinkData.add(String.valueOf(dpStockMan.getOID()) + "','" + Formater.formatDate(dpStockMan.getDtOwningDate(), "yyyy-MM-dd"));
            }
            Vector rowx = new Vector(1, 1);
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");///wilbe taken
            rowx.add("");

            //update by satrya 2012-11-25
            //cekbok & hours

            // rowx.add("<input type=\"checkbox\" name=\"userSelect\" onclick=\"javascript:cmdCalculate()\" value=\"" + residueEligible + "\" >");///wilbe taken
            //residueEligible = residueEligible - dpStockMan.getEligible();

            rowx.add("Total:");

            rowx.add(Formater.formatWorkDayHoursMinutes(total, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
            //String spanX = "<span id=\"sp-click\">Checked: true</span>"; 
            // if(spanX.equalsIgnoreCase("<span id=\"sp-click\">Checked: true</span>")){
            //   residueEligible= dpTotReq - dpStockMan.getEligible();
            //}

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(dpStockMan.getOID()) + "','" + Formater.formatDate(dpStockMan.getDtOwningDate(), "yyyy-MM-dd"));



        }

        return ctrlist.draw();
    }
%>
<%
    final int max_data = 20;
    boolean submitIsOk = false;
    boolean checkBreak = false;
    int iCommand = FRMQueryString.requestCommand(request);
    int iCommands = FRMQueryString.requestInt(request, "iCommands");
    float eligibles = FRMQueryString.requestFloat(request, "eligibles");
    long empId = FRMQueryString.requestLong(request, "FRM_FLD_EMPLOYEE_ID");
    long leaveAppId = FRMQueryString.requestLong(request, "FRM_FLD_LEAVE_APPLICATION_ID");
    long dpStokId = FRMQueryString.requestLong(request, "FRM_FLD_DP_STOCK_ID");
    String strDayOff = PstSystemProperty.getValueByName("OID_DAY_OFF");
    long oidDayOff = 0;
    if (strDayOff != null && strDayOff.length() > 0) {
        oidDayOff = Long.parseLong(strDayOff);

    }
    //long scheduleId=0;
    //float tempDpTotal = FRMQueryString.requestFloat(request,"tmpTotalDp");
    float totMaxDPAvailable = 1.0f;
    int iErrCodeDp = FRMMessage.ERR_NONE;
    int indexDP = FRMQueryString.requestInt(request, "indexDP");
    float dpTotReq = FRMQueryString.requestFloat(request, "dpTotReq");
    float intersec = FRMQueryString.requestFloat(request, "intersec");
    long calculateBreak = FRMQueryString.requestLong(request, "calculasiBreak");
    String sFinishDateX = FRMQueryString.requestString(request, "sFinishDateX");
    String sIntersec = FRMQueryString.requestString(request, "sIntersec");
    String totDp = FRMQueryString.requestString(request, "totDp");
    String sFinishDateTime = "";//Formater.formatDate(finishDate,"HH:mm");
    String sTakenDateTime = "";//Formater.formatDate(takenDate,"HH:mm");
    Date takenDate = FRMQueryString.requestDateVer3(request, "FRM_FIELD_DP_TAKEN_DATE");
    // Date takenDate = new Date(takenDateYear-1900,takenDateMn-1,takenDatedy,takenDateHr,takenDateMm);
    String sTakenDate = Formater.formatDate(takenDate, "EE,dd MMMM yyyy");
    String sTakenDateX = Formater.formatDate(takenDate, "dd-MM-yyyy");
    long lTakenDate = takenDate.getTime();
    Date finishTime = FRMQueryString.requestDateVer3(request, "FRM_FIELD_DP_FINNISH_DATE");
    java.util.Date finishDate = takenDate;
    Date finishDateTmp = new Date(lTakenDate);
    finishDateTmp.setHours(finishTime.getHours());
    finishDateTmp.setMinutes(finishTime.getMinutes());
    finishDateTmp.setSeconds(finishTime.getSeconds());
    finishDate = finishDateTmp;

    long periodId = PstPeriod.getPeriodIdBySelectedDateString(Formater.formatDate(takenDate, "yyyy-MM-dd"));
    ScheduleSymbol scheduleSymbol = PstEmpSchedule.getEmpScheduleDateTime(periodId, empId, takenDate, 0);

    String stempDpTotal = "";
    long dpStokTakenId = 0;
    float tempDpTotal = 0;
    Vector dpEligable = new Vector();
    ///update by satrya 2013-01-22
    if (iCommand != Command.SAVE) {
        try {
            if (takenDate.getHours() == finishDate.getHours()
                    && takenDate.getMinutes() == finishDate.getMinutes()) { // jika jam dan menit sama berarti cuti dalam hitungan hari kerja
                float dpQty = SessLeaveApplication.DATEDIFF(finishDate, takenDate);
                dpTotReq = (dpQty + 1);
                takenDate.setHours(scheduleSymbol.getTimeIn().getHours());
                takenDate.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                takenDate.setSeconds(0);
                finishDate.setHours(scheduleSymbol.getTimeOut().getHours());
                finishDate.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                finishDate.setSeconds(0);
                sFinishDateTime = Formater.formatDate(finishDate, "HH:mm");
                sTakenDateTime = Formater.formatDate(takenDate, "HH:mm");
            } else {
                // jika tidak : maka cuti jam-an ( 30 menit resolusi )  maximum satu hari 
                // alStockTaken.setTakenFinnishDate(alStockTaken.getTakenDate());
                dpTotReq = DateCalc.workDayDifference(takenDate, finishDate, leaveConfig.getHourOneWorkday());
                intersec = PstEmpSchedule.breakTimeIntersection(empId, takenDate, finishDate) / (8f * 60f * 60f * 1000f);
                dpTotReq = (dpTotReq - intersec);
                sFinishDateTime = Formater.formatDate(finishDate, "HH:mm");
                sTakenDateTime = Formater.formatDate(takenDate, "HH:mm");
            }
            calculateBreak = PstEmpSchedule.breakTimeIntersectionVer2(empId, takenDate, finishDate);
            sFinishDateX = Formater.formatDate(finishDate, "dd-MM-yyyy");
            sIntersec = Formater.formatWorkDayHoursMinutes(intersec, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
            totDp = Formater.formatWorkDayHoursMinutes(dpTotReq, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
            stempDpTotal = "0";

            tempDpTotal = com.dimata.harisma.session.attendance.SessDpStockManagement.getTotalSelectedDateLeave(leaveAppId, empId, takenDate);

            if (tempDpTotal != 0) {
                stempDpTotal = Formater.formatWorkDayHoursMinutes(tempDpTotal, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
            }

            dpEligable = com.dimata.harisma.session.attendance.SessDpStockManagement.ListDp(empId, dpStokId);
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
    }
    dpStokTakenId = PstDpStockTaken.getDpStokTakenId(dpStokId);

    /*if(takenDate.getHours() == scheduleSymbol.getBreakOut().getHours() 
    && takenDate.getMinutes() == scheduleSymbol.getBreakOut().getMinutes()
    && finishDate.getHours() == scheduleSymbol.getBreakIn().getHours()
    && finishDate.getMinutes() == scheduleSymbol.getBreakIn().getMinutes()){ 
    checkBreak = true; 
    }*/


    String msgString = "";

    if (iCommand == Command.SAVE) {

        try {

            CtrlDpStockTaken ctrlDpStockTaken = new CtrlDpStockTaken(request);
            iErrCodeDp = ctrlDpStockTaken.actionMultiple(iCommand, dpStokId, empId, scheduleSymbol, dpTotReq, dpStokTakenId, calculateBreak);
            Vector dpTakens = ctrlDpStockTaken.getForm().getDpTakens();
            msgString = ctrlDpStockTaken.getMessage();
            if (!(msgString.length() > 0)) {
                submitIsOk = true;
            }
            /*if((iErrCodeDp == 0 && iCommand == Command.SAVE) || (iErrCodeDp == 0 && iCommand == Command.DELETE)){
            indexDP = -1;
            }
            FrmDpStockTaken frmDpStockTaken = ctrlDpStockTaken.getForm();
            errMsgDp = ctrlDpStockTaken.getMessage();   */
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
        // }
        // }


    }

%>
<script language="JavaScript">
    <% if (tempDpTotal != 0 && tempDpTotal > totMaxDPAvailable) {%>
        alert("You have already apply '<%=stempDpTotal%>' hours for DP on '<%=sTakenDate%>'."+
            " You don't need to apply DP again, otherweise you need to apply another date "+
            " Please select that date.");
        window.close();
    <%} else if (dpTotReq > totMaxDPAvailable) {%>
        alert("You have already apply '<%=dpTotReq%>' hours for DP on '<%=sTakenDate%>'."+
            " You don't need to apply DP again, otherweise you need to apply another date "+
            " Please select that date.");
        window.close();
        
        //durasi tdk boleh lebih yang di pakai patokan terlebih dahulu tanggalnya setelah itu baru total harinya
    <%} else if (tempDpTotal != 0 && (dpTotReq >= tempDpTotal) || (tempDpTotal + dpTotReq) > totMaxDPAvailable && dpStokTakenId == 0) {%> 
        alert("You has requested more than one day for DP on '<%=sTakenDate%>'."+
            " You don't need to apply DP again, otherweise you need to apply another date "+
            " Please select that date.");
        window.close();
    <%} else if ( (scheduleSymbol != null) && (oidDayOff != 0 && scheduleSymbol.getScheduleSymbolId() != 0) && (oidDayOff == scheduleSymbol.getScheduleSymbolId())) {%>  
        alert("Your schedule is 'OFF'."+
            " You can't select leave this date."+
            " Please select another date.");
        window.close();
    <%} else if ( (scheduleSymbol.getScheduleSymbolId() > 0) &&(takenDate.getHours() == scheduleSymbol.getBreakOut().getHours() && takenDate.getMinutes() == scheduleSymbol.getBreakOut().getMinutes())
              && (finishDate.getHours() == scheduleSymbol.getBreakIn().getHours() && finishDate.getMinutes() == scheduleSymbol.getBreakIn().getMinutes())) {%>
                  alert("time you select the same hour break ('<%=sTakenDateTime%>' AND '<%=sFinishDateTime%>')");  
                  window.close();
    <%}%>
       
        function cmdSelect(dpOid, date) {        
            var oid = dpOid;
            //self.opener.document.forms.frm_leave_application.DpPaidDate.value = dpOid;
            //self.opener.document.forms.frm_leave_application.DpPaidDate.value = dpOid;
            self.opener.document.forms.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]%>.value=dpOid;
            self.opener.document.forms.frm_leave_application.<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_PAID_DATE]%>.value = date;
           
            self.close();
        }
        function cmdSave(){
            document.frm_dp_list.command.value="<%=Command.SAVE%>";  
            document.frm_dp_list.indexDP.value = 0; 
            document.frm_dp_list.action="dp_available_list.jsp";
            document.frm_dp_list.submit(); 
        
        }
        function openLeaveOverlap(oidLeave)
        {
            document.frm_dp_list.command.value="<%=Command.EDIT%>";
            document.frm_dp_list.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
            document.frm_dp_list.action="leave_app_edit.jsp";
            document.frm_dp_list.submit();
        }
    <% if (submitIsOk) {%>
         //update by satrya 2014-01-20
            //self.opener.document.forms.frm_leave_application.command.value = "0";
            //update by satrya 2014-02-08
            self.opener.document.forms.frm_leave_application.command.value = "<%=Command.REFRESH%>";
           
        self.opener.cmdRel();
    
     //window.opener.location.reload();
        window.close();//for IE
        //document.frm_dp_list.action= window.close(); //di firefox mau
	
    <%}%>
    
</script>
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - DP Eligible List</title>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --><!-- #EndEditable --><!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
        <!-- #EndEditable --><!-- #BeginEditable "headerscript" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
    </head>
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" class="tabbg">
            <tr>
                <td width="88%" valign="top" align="left">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                        <tr>
                            <td>
                                <!-- #BeginEditable "content" -->
                                <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbgBorder">
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width="11%">DP Request </td>
                                        <td width="16%" nowrap="nowrap">: <%=sTakenDate%></td>
                                        <td width="8%">&nbsp;</td>
                                        <td width="21%" nowrap="nowrap">: <%=sTakenDateTime + "-" + sFinishDateTime%></td>
                                        <td width="44%">&nbsp;</td> 
                                    </tr>
                                    <tr>
                                        <td width="11%" nowrap="nowrap">Total DP Request </td>
                                        <td width="16%">: <%=totDp%></td>
                                        <td width="8%">Rest Time</td>
                                        <td>: <%=sIntersec%> </td>
                                        <td>&nbsp;</td>
                                    </tr>
                                </table>
                                <!-- #EndEditable -->
                                <!-- #BeginEditable "content" -->
                                <table width="100%"   border="0" cellspacing="1" cellpadding="1" class="tabbgBorder">
                                    <tr> 
                                        <td>
                                            <form name="frm_dp_list" method="post" action="">
                                                <input type="hidden" name="command" value="">
                                                <input type="hidden" name="indexDP" value="<%=String.valueOf(indexDP)%>"> 
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=empId%>">     
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_LEAVE_APPLICATION_ID]%>" value="<%=leaveAppId%>">
                                                <input type="hidden" name="tmpTotalDp" value="<%=tempDpTotal%>">
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_ID]%>" value="<%=dpStokId%>">

                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID]%>" value="<%=dpStokTakenId%>">

                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE] + "_yr"%>" value="<%=takenDate.getYear() + 1900%>">
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE] + "_mn"%>" value="<%=takenDate.getMonth() + 1%>">
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE] + "_dy"%>" value="<%=takenDate.getDate()%>">
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE] + "_hr"%>" value="<%=takenDate.getHours()%>">
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_TAKEN_DATE] + "_mi"%>" value="<%=takenDate.getMinutes()%>">

                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE] + "_yr"%>" value="<%=finishDate.getYear() + 1900%>">
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE] + "_mn"%>" value="<%=finishDate.getMonth() + 1%>">
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE] + "_dy"%>" value="<%=finishDate.getDate()%>">
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE] + "_hr"%>" value="<%=finishDate.getHours()%>">
                                                <input type="hidden" name="<%=FrmDpStockTaken.fieldNames[FrmDpStockTaken.FRM_FIELD_FINNISH_DATE] + "_mi"%>" value="<%=finishDate.getMinutes()%>">

                                                <!-- update by satrya 2013-01-22 -->
                                                <input type="hidden" name="calculasiBreak" value="<%=calculateBreak%>">
                                                <input type="hidden" name="sFinishDateX" value="<%=sFinishDateX%>">
                                                <input type="hidden" name="sIntersec" value="<%=sIntersec%>">
                                                <input type="hidden" name="totDp" value="<%=totDp%>">
                                                <input type="hidden" name="dpTotReq" value="<%=dpTotReq%>">
                                                <input type="hidden" name="intersec" value="<%=intersec%>">
                                                <hr></hr>
                                                <tr>
                                                    <td height="20" colspan="3"><font color="#FF6600" face="Arial"><strong>  DP ELIGIBLE LIST  </strong></font> </td>
                                                </tr>

                                                <tr>
                                                    <td colspan="3" valign="top">
                                                        <%
                                                            if (dpEligable != null && dpEligable.size() > 0) {
                                                                out.println(drawList(dpEligable, dpTotReq, takenDate, finishDate, dpStokId));
                                                            }
                                                        %>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <div align="center">
                                                            <table align="center" width="100%">
                                                                <%if ((iCommand == Command.SAVE)) {%>
                                                                <tr>
                                                                        <!--<td colspan="4" bgcolor="#FFFF00"> Message : <%//= msgString %></td>-->
                                                                    <%if (msgString.length() > 0) {%>
                                                                    <td colspan="4" bgcolor="#FFFF00"> Message : <%= msgString%></td>
                                                                    <%} else {%>
                                                                    <td colspan="4" bgcolor="#FFFF00">
                                                                        Message : <%= CtrlDpStockTaken.resultText[CtrlDpStockTaken.LANGUAGE_FOREIGN][iErrCodeDp]%>  
                                                                    </td>
                                                                    <%}%>
                                                                </tr>
                                                                <%}%>
                                                                <%if (iCommands == Command.EDIT || leaveConfig.getDPEligibleMinus(eligibles)) {%> 
                                                                <!-- uspdate by satrya 2013-08-30<//%if (iCommands == Command.EDIT || eligibles > 0) {%>-->
                                                                <tr>
                                                                    <td width="2%">&nbsp;</td>
                                                                    <td width="2%"><div align="right"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></div></td>
                                                                    <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"><a href="javascript:cmdSave()" class="command">Submit</a></td>
                                                                    <td width="2%" nowrap></td>		
                                                                </tr>
                                                                <%}%>
                                                            </table>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </form>
                                        </td>
                                    </tr>

                                </table>
                                <!-- #EndEditable --> </td>
                        </tr>
                    </table></td>
            </tr>
        </table>
    </body>
    <!-- #BeginEditable "script" --><!-- #EndEditable --><!-- #EndTemplate -->
</html>
