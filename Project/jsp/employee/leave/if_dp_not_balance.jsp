
<%@page import="com.dimata.harisma.entity.leave.LeaveApplication"%>
<%@page import="com.dimata.harisma.entity.leave.PstLeaveApplication"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@page import="com.dimata.harisma.utility.machine.SaverData"%>
<%@page import="com.dimata.harisma.utility.service.presence.PresenceAnalyser"%>
<%
    /* 
    Document   : if_dp_not_balance
    Created on : Dec 13, 2012, 1:16:07 PM
    Author     : Satrya Ramayu
     */
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PRESENCE, AppObjInfo.OBJ_MANUAL_PRESENCE); %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    I_Leave leaveConfig = null;

    try {
        leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
    }
%>
<!-- Jsp Block -->
<%
//Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
    //Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
    //String empNum = FRMQueryString.requestString(request, "emp_number");
    //String fullName = FRMQueryString.requestString(request, "full_name");
    //Vector listPeriod = PstPeriod.list(0, 0, "", "");
    //String sOidPeriod = FRMQueryString.requestString(request, "FRM_FIELD_PERIOD_ID");
    int iCommand = FRMQueryString.requestCommand(request);
    String emp_number = FRMQueryString.requestString(request, "emp_number");
    float fifteenMinutes = 0.03125F; // ini untuk yg kerja 1 hari = 8 jam
    float thirtyMinutes = 0.0625F;
    float fortyMinutes = 0.09375F;
    float oneDays = 1.0F;

    boolean chekLeaveBalance = true;
    //DpStockManagement dpStockManagement = null;
    //DpStockTaken dpStockTakenX = null;
    boolean empNull = false; 
    if (iCommand == Command.SAVE) {
        empNull=false;
      if(emp_number!=null && emp_number.length()>0){
        long excCekDpBalance = SessDpStockManagement.ExcDpBalanceCheckByEmpNumber(emp_number);
        Vector listDpManagement = SessDpStockManagement.ListDpManagementToDpBalance(emp_number, false);
       

        // boolean cekInisialisasiAwal= true;
        // long tmpDP_STOK_ID=0;
        if (listDpManagement != null && listDpManagement.size() > 0) {
            for (int i = 0; i < listDpManagement.size(); i++) {
                DpStockManagement dpStockManagement = (DpStockManagement) listDpManagement.get(i);
            
                boolean cekInisialisasiAwal = true;
                     Vector listDpTaken = SessDpStockManagement.ListDpTakenToDpBalance(dpStockManagement.getEmployeeId(), false);   
                if (listDpTaken != null && listDpTaken.size() > 0) {
                    for (int x = 0; x < listDpTaken.size(); x++) {
                        DpStockTaken dpStockTaken = (DpStockTaken) listDpTaken.get(x);
//                    if(dpStockTaken.getFlagDpBalance()== PstDpStockTaken.DP_BALANCE_UNCHECKED){ 
                        if (dpStockTaken.getDpStockId() != 0 && dpStockManagement.getEmployeeId() == dpStockTaken.getEmployeeId()
                                && (dpStockManagement.getOID() == dpStockTaken.getDpStockId())){
                            //update by satrya jam 25-03-2013 jam 7
                             String tmpTakenx = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###");
                              float xtmpTaken = Float.parseFloat(tmpTakenx); 
                            ScheduleSymbol scheduleSymbol = PstEmpSchedule.getDailySchedule(dpStockTaken.getTakenDate(), dpStockTaken.getEmployeeId());
                            boolean cekPaidDate = false;
                            boolean cekOidTakenId=false;
                            if (dpStockTaken.getDpStockId() != 0) {
                                dpStockManagement = PstDpStockManagement.fetchExc(dpStockTaken.getDpStockId());
                            }
                            if (dpStockTaken.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED) {

                                if (cekInisialisasiAwal && dpStockManagement.getiDpQty() != 0 && dpStockTaken.getTakenQty() != 0
                                        && dpStockManagement.getiDpQty() >= dpStockTaken.getTakenQty()) {
                                    //update by satrya 2013-03-20 jam 15
                                    String sDpQTy = Formater.formatNumber(dpStockManagement.getiDpQty(), "#.###############");
                                    float dpQty = Float.parseFloat(sDpQTy);
                                    String sTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(),  "#.###############");
                                    float takenQty = Float.parseFloat(sTakenQty);
                                    dpStockManagement.setQtyResidue(dpQty - takenQty);
                                    //dpStockManagement.setQtyResidue(dpStockManagement.getiDpQty() - dpStockTaken.getTakenQty());
                                    
                                    dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                    //dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                    if (dpStockTaken.getPaidDate() == null) {
                                        dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                        dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        cekPaidDate = true;
                                        cekOidTakenId=true;
                                    }
                                    //cekResidue = false;
                                    PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                    ///dan update qtry residue , qty used
                                    PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                    //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate); 
                                    cekPaidDate = false;//jika sdh di update di set default lge 
                                    cekInisialisasiAwal = false;

                                    listDpTaken.remove(x);
                                    x = x - 1;

                                } else {
                                    if (dpStockManagement.getQtyResidue()!=0 && dpStockManagement.getQtyResidue() >= dpStockTaken.getTakenQty()) {
                                        // if (dpStockManagement.getQtyResidue() >= dpStockTaken.getTakenQty()) {
                                        dpStockManagement.setQtyResidue(dpStockManagement.getQtyResidue() - dpStockTaken.getTakenQty());
                                        dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockTaken.getTakenQty());
                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        listDpTaken.remove(x);
                                        x = x - 1;
                                    } //jika resude < taken_qty dan residue tidak minus, maka taken_qty - residue dan sisanya akan di insert
                                    // lalu residue di set 0
                                    
                                    //update by satrya 2013-03-20
                                    // jika DPQTY'nya lebih kecil dari taken dan residue tidak boleh minus
                                     else if (dpStockManagement.getiDpQty()!= 0 && dpStockManagement.getiDpQty() < dpStockTaken.getTakenQty() && dpStockManagement.getQtyResidue()>0) { 
                                         //else if (dpStockManagement.getiDpQty()!= 0 && dpStockManagement.getiDpQty() < dpStockTaken.getTakenQty() && dpStockManagement.getQtyResidue()>=0) { 

                                         String stmpQtyRes = Formater.formatNumber(dpStockManagement.getQtyResidue(), "#.###############"); 
                                        float tmpQtyRes = Float.parseFloat(stmpQtyRes); 
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty);
                                        
                                      //update by satrya 2013-03-22 jam 15
                                         // dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockManagement.getQtyResidue());
                                        if (dpStockManagement.getQtyResidue() != 0) {
                                           
                                            dpStockTaken.setTakenQty(tmpQtyRes);
                                            //dpStockTaken.setTakenQty(dpStockManagement.getQtyResidue());
                                        }
                                        
                                        dpStockManagement.setQtyResidue(0);

                                        //update by satrya 2013-03-22 jam 15
                                       if((dpStockManagement.getQtyUsed() + dpStockManagement.getQtyResidue()< dpStockManagement.getiDpQty())){
                                           dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockTaken.getTakenQty());
                                       }else{
                                              dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                       }
                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }
                                        //
                                        long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(tmpQtyRes, leaveConfig.getHourOneWorkday());
                                        //dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                           if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                //update by satrya 2013-03-18
                                                //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                                 dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                           }//update by satrya 2013-03-18
                                            else{
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            }
                                        }else{
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                        }
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                       
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                        //prosess insert   
                                        if (tmpQtyRes != 0 && (tmpTakenQty - tmpQtyRes)!=0 && tmpTakenQty > 0 && tmpQtyRes >= 0 && tmpTakenQty >= tmpQtyRes) {
                                            float hasilSisaTakenX = tmpTakenQty - tmpQtyRes;
                                            String stmpTakenQtyx = Formater.formatNumber(hasilSisaTakenX, "#.###############"); 
                                            float tmpTakenQtyx = Float.parseFloat(stmpTakenQtyx); 
                                            //DpStockTaken dpStockTakenX = new DpStockTaken();
                                            //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                            dpStockTaken.setDpStockId(0);
                                            dpStockTaken.setTakenQty(tmpTakenQtyx);
                                            dpStockTaken.setPaidDate(null);
                                            dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                              }
                                            dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                              if (dpStockTaken.getTakenQty() > 0) {
                                                        //mengubah lHasilSisaTaken menjadi melisecond
                                                        lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                               }
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                                //cek waktu istirahat
                                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if (intersecX != 0) {
                                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                            }
//                                            dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                            PstDpStockTaken.insertExc(dpStockTaken);
                                          
                                        }

                                        listDpTaken.remove(x);
                                        x = x - 1;

                                    } //jika residuenya minus, dan taken Qty > DP Qty
                                    else if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() >= 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {

                                         String stmpQtyRes = Formater.formatNumber(dpStockManagement.getQtyResidue(), "#.###############");
                                        float tmpQtyRes = Float.parseFloat(stmpQtyRes);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty);
                                        
                                        dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockManagement.getQtyResidue());
                                        if (dpStockManagement.getQtyResidue() != 0) {
                                           
                                            dpStockTaken.setTakenQty(tmpQtyRes);
                                            //dpStockTaken.setTakenQty(dpStockManagement.getQtyResidue());
                                        }

                                        dpStockManagement.setQtyResidue(0);

                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }
                                        //
                                        long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(tmpQtyRes, leaveConfig.getHourOneWorkday());
                                        //dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                //update by satrya 2013-03-18
                                               // dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                                 dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            }//update by satrya 2013-03-18
                                            else{
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            } 
                                        }else{
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                        }
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        /* - update Taken:
                                         *  paid Date
                                         *  taken qty
                                         *  TakenDate
                                         *  FinishDate
                                         * - update Management :
                                         *  residue
                                         *  use      
                                         */
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                        //prosess insert   
                                        if (tmpQtyRes != 0 && (tmpTakenQty - tmpQtyRes)!=0 && tmpTakenQty > 0 && tmpQtyRes >= 0 && tmpTakenQty >= tmpQtyRes) {
                                            float hasilSisaTakenX = tmpTakenQty - tmpQtyRes;
                                            String stmpTakenQtyx = Formater.formatNumber(hasilSisaTakenX, "#.###############"); 
                                            float tmpTakenQtyx = Float.parseFloat(stmpTakenQtyx); 
                                            //DpStockTaken dpStockTakenX = new DpStockTaken();
                                            //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                            dpStockTaken.setDpStockId(0);
                                            dpStockTaken.setTakenQty(tmpTakenQtyx);
                                            dpStockTaken.setPaidDate(null);
                                            dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                              }
                                            dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                              if (dpStockTaken.getTakenQty() > 0) {
                                                        //mengubah lHasilSisaTaken menjadi melisecond
                                                        lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                               }
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                                //cek waktu istirahat
                                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if (intersecX != 0) {
                                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                            }
//                                            dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                            PstDpStockTaken.insertExc(dpStockTaken);
                                            
                                            //cek jika sisanya kurang dari 15 menit, maka tidak di insert
                                           /* if (dpStockTaken.getTakenQty() >= fifteenMinutes) {
                                                
                                                //maka akan di set 15 menit
                                                if (dpStockTaken.getTakenQty() >= fifteenMinutes && dpStockTaken.getTakenQty() < thirtyMinutes) {
                                                    if (dpStockTaken.getTakenQty() > 0) {
                                                        //mengubah lHasilSisaTaken menjadi melisecond
                                                        lHasilSisaTaken = Formater.getWorkDayMiliSeconds(fifteenMinutes, leaveConfig.getHourOneWorkday());
                                                    }
                                                    //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                                    if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                        if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                            Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                            takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                            takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                            takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                            Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                            finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                            finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                            finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                            dpStockTaken.setTakenDate(takenDateTmp);
                                                            dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                        }
                                                    }
                                                    dpStockTaken.setTakenQty(fifteenMinutes);
                                                    dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                                    //cek waktu istirahat
                                                    intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                                    if (intersecX != 0) {
                                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                                    }
                                                    PstDpStockTaken.insertExc(dpStockTaken);
                                                } 
                                                
                                                //maka akan di set 30 menit
                                                else if (dpStockTaken.getTakenQty() >= thirtyMinutes && dpStockTaken.getTakenQty() < fortyMinutes) {
                                                    if (dpStockTaken.getTakenQty() > 0) {
                                                        //mengubah lHasilSisaTaken menjadi melisecond
                                                        lHasilSisaTaken = Formater.getWorkDayMiliSeconds(thirtyMinutes, leaveConfig.getHourOneWorkday());
                                                    }
                                                    //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                                    if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                        if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                            Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                            takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                            takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                            takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                            Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                            finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                            finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                            finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                            dpStockTaken.setTakenDate(takenDateTmp);
                                                            dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                        }
                                                    }
                                                    dpStockTaken.setTakenQty(thirtyMinutes);
                                                    dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                                    //cek waktu istirahat
                                                    intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                                    if (intersecX != 0) {
                                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                                    }
                                                    PstDpStockTaken.insertExc(dpStockTaken);
                                                } //maka akan di set 45 menit
                                                else if (dpStockTaken.getTakenQty() >= fortyMinutes && dpStockTaken.getTakenQty() < oneDays) {
                                                    if (dpStockTaken.getTakenQty() > 0) {
                                                        //mengubah lHasilSisaTaken menjadi melisecond
                                                        lHasilSisaTaken = Formater.getWorkDayMiliSeconds(fortyMinutes, leaveConfig.getHourOneWorkday());
                                                    }
                                                    //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                                    if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                        if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                            Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                            takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                            takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                            takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                            Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                            finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                            finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                            finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                            dpStockTaken.setTakenDate(takenDateTmp);
                                                            dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                        }
                                                    }
                                                    dpStockTaken.setTakenQty(fortyMinutes);
                                                    dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                                    //cek waktu istirahat
                                                    intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                                    if (intersecX != 0) {
                                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                                    }
                                                    PstDpStockTaken.insertExc(dpStockTaken);
                                                } else {
                                                    if (dpStockTaken.getTakenQty() > 0) {
                                                        //mengubah lHasilSisaTaken menjadi melisecond
                                                        lHasilSisaTaken = Formater.getWorkDayMiliSeconds(oneDays, leaveConfig.getHourOneWorkday());
                                                    }
                                                    //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                                    if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                        if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                            Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                            takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                            takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                            takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                            Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                            finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                            finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                            finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                            dpStockTaken.setTakenDate(takenDateTmp);
                                                            dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                        }
                                                    }
                                                    dpStockTaken.setTakenQty(oneDays);
                                                    dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                                    //cek waktu istirahat
                                                    intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                                    if (intersecX != 0) {
                                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                                    }
                                                    PstDpStockTaken.insertExc(dpStockTaken);
                                                }

                                            }*///end
                                        }

                                        listDpTaken.remove(x);
                                        x = x - 1;

                                    } //jika residuenya minus, dan taken Qty > DP Qty
                                    else if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() < 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {

                                       // float tmpStockQty = dpStockManagement.getiDpQty();
                                        //float tmpTakenQty = dpStockTaken.getTakenQty();
                                          String stmpStockQty = Formater.formatNumber(dpStockManagement.getiDpQty(), "#.####################");
                                        float tmpStockQty = Float.parseFloat(stmpStockQty);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.####################");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty); 
                                        dpStockTaken.setTakenQty(tmpStockQty);
                                        dpStockManagement.setQtyResidue(0);
                                        dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                          
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }

                                        // update by satrya 2013-01-21
                                        
                                        
                                        long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                        //dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                              if(scheduleSymbol.getTimeIn().getHours()!=0){
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());
                                              }else{
                                                     takenDateTmp.setHours(8);
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());
                                              }
                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                //dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                           }
                                            else{
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            }
                                        }else{
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));  
                                        }
                                        //update by satrya 2013-03-2013
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                        if (tmpTakenQty > 0 && (tmpTakenQty - tmpStockQty)!=0&& tmpStockQty >= 0 && tmpTakenQty >= tmpStockQty) {
                                            float hasilSisaTakenX = tmpTakenQty - tmpStockQty;
                                            //update by satrya 2013-03-18
                                             String sTmpHasilSisaTakenX = Formater.formatNumber(hasilSisaTakenX,  "#.###############");
                                            float tmpHasilSisaTakenX = Float.parseFloat(sTmpHasilSisaTakenX);
                                            //DpStockTaken dpStockTakenX = new DpStockTaken();
                                            //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                            dpStockTaken.setDpStockId(0);
                                            dpStockTaken.setTakenQty(tmpHasilSisaTakenX);
                                            //dpStockTaken.setTakenQty(hasilSisaTakenX);
                                            dpStockTaken.setPaidDate(null);
                                            dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                            lHasilSisaTaken = 0;
                                            if (hasilSisaTakenX > 0) {
                                                //mengubah lHasilSisaTaken menjadi melisecond
                                                lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                            }
                                            //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                            }
                                            dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                            //cek waktu istirahat
                                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if (intersecX != 0) {
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                            }
//                                            dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                            PstDpStockTaken.insertExc(dpStockTaken);
                                        }
                                       
                                        listDpTaken.remove(x);
                                        x = x - 1;
                                    } //jika residue yg di gunakan sudah 0 atau abis
                                    //dan harus ada paid date'nya
                                    //update by satrya 2013-03-22 jam 12
                                    else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0 && dpStockTaken.getDpStockId()!=0
                                            && dpStockManagement.getOID()==dpStockTaken.getDpStockId() && dpStockTaken.getPaidDate()!=null) { 
                                        //DpStockTaken dpStockTaken = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                        dpStockTaken.setPaidDate(null);
                                        //dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                        //long lHasilSisaTaken = 0;
                                        //if(dpStockTaken.getTakenQty() > 0){
                                        // lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                        // }
                                        //dpStockTaken.setTakenDate(dpStockTakenX.getTakenFinnishDate());
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTakenX.getTakenFinnishDate().getTime()+lHasilSisaTaken));
                                        //cek waktu istirahat
                                       /*long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp  = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                            }
                                            //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX)); 
                                            
                                        }*/
                                       //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime()));
                                        PstDpStockTaken.updateExc(dpStockTaken);
                                        listDpTaken.remove(x);
                                        x = x - 1;
                                    }
                                    //update by satrya 2013-03-25 jam 7
                                    //artintya stocknya sudah abis
                                  
                                     else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0 && dpStockTaken.getDpStockId()!=0    
                                            && dpStockManagement.getOID()==dpStockTaken.getDpStockId() 
                                            && dpStockTaken.getPaidDate()==null && xtmpTaken!=0) { 
                                        
                                         dpStockTaken.setDpStockId(0);
                                        dpStockTaken.setPaidDate(null);
                                        PstDpStockTaken.updateExc(dpStockTaken);
                                        listDpTaken.remove(x);
                                        x = x - 1;
                                    }
                                    

                                }
                            } else {
                                // jika doc status belum di excecute, maka di update cuma taken saja
                                if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {
                                    //float tmpQtyRes = dpStockManagement.getQtyResidue();
                                    //float tmpTakenQty = dpStockTaken.getTakenQty();
                                      String stmpQtyRes = Formater.formatNumber(dpStockManagement.getQtyResidue(), "#.###############");
                                        float tmpQtyRes = Float.parseFloat(stmpQtyRes);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty);
                                    //dpStockManagement.setQtyResidue(0);
                                    //dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed()+dpStockManagement.getQtyResidue());
                                    if (dpStockManagement.getQtyResidue() != 0) {
                                        dpStockTaken.setTakenQty(tmpQtyRes);
                                        // dpStockTaken.setTakenQty(dpStockManagement.getQtyResidue());
                                    }
                                    if (dpStockTaken.getPaidDate() == null) {
                                        dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                        dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        cekPaidDate = true;
                                        cekOidTakenId=true;
                                    }
                                    //
                                    long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockManagement.getQtyResidue(), leaveConfig.getHourOneWorkday());


                                    //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                    if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                        if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                            Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                            takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                            takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                            takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                            Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                            finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                            finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                            finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                            dpStockTaken.setTakenDate(takenDateTmp);
                                            dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                        }
                                    }
                                    dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                    //cek waktu istirahat
                                    long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                    if (intersecX != 0) {
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                    }
                                    /* - update Taken:
                                     *  paid Date
                                     *  taken qty
                                     *  TakenDate
                                     *  FinishDate
                                     * - update Management :
                                     *  residue
                                     *  use      
                                     */
                                    PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                    ///dan update qtry residue , qty used
                                    //PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                    cekPaidDate = false;//jika sdh di update di set default lge
                                    //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                    //prosess insert   
                                    if (tmpQtyRes != 0 && (tmpTakenQty - tmpQtyRes)!=0 && tmpTakenQty > 0 && tmpQtyRes >= 0 && tmpTakenQty >= tmpQtyRes) {
                                        float hasilSisaTakenX = tmpTakenQty - tmpQtyRes;
                                        //update by satrya 2013-03-18
                                         String stmpHasilSisaTakenX = Formater.formatNumber(hasilSisaTakenX,  "#.###############");
                                    float tmpHasilSisaTakenX = Float.parseFloat(stmpHasilSisaTakenX);
                                        //DpStockTaken dpStockTakenX = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        dpStockTaken.setTakenQty(tmpHasilSisaTakenX);
                                        //dpStockTaken.setTakenQty(hasilSisaTakenX);
                                        dpStockTaken.setPaidDate(null);
                                        dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                        lHasilSisaTaken = 0;
                                        if (hasilSisaTakenX > 0) {
                                            //mengubah lHasilSisaTaken menjadi melisecond
                                            lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                        }
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                            }
                                        }
                                        dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                        //cek waktu istirahat
                                        intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
//                                        dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                        PstDpStockTaken.insertExc(dpStockTaken);
                                    }
                                    listDpTaken.remove(x);
                                    x = x - 1;

                                } //jika residue yg di gunakan sudah 0 atau abis
                                //hidden by satrya 2013-03-22 jam 13
                               /* else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0) {
                                    //DpStockTaken dpStockTaken = new DpStockTaken();
                                    //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                    dpStockTaken.setDpStockId(0);
                                    //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                    dpStockTaken.setPaidDate(null);
                                    dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                    //long lHasilSisaTaken = 0;
                                    //if(dpStockTaken.getTakenQty() > 0){
                                    // lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                    // }
                                    //dpStockTaken.setTakenDate(dpStockTakenX.getTakenFinnishDate());
                                    //dpStockTaken.setTakenFinnishDate(new Date(dpStockTakenX.getTakenFinnishDate().getTime()+lHasilSisaTaken));
                                    //cek waktu istirahat
                                    long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                    if (intersecX != 0) {
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                            }
                                        }
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                    }
                                    PstDpStockTaken.updateExc(dpStockTaken);
                                    listDpTaken.remove(x);
                                    x = x - 1;

                                }*/
                                //dan harus ada paid date'nya
                                    //update by satrya 2013-03-22 jam 12
                                    else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0 && dpStockTaken.getDpStockId()!=0
                                            && dpStockManagement.getOID()==dpStockTaken.getDpStockId() && dpStockTaken.getPaidDate()!=null) { 
                                        //DpStockTaken dpStockTaken = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                        dpStockTaken.setPaidDate(null);
                                        //dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                        //long lHasilSisaTaken = 0;
                                        //if(dpStockTaken.getTakenQty() > 0){
                                        // lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                        // }
                                        //dpStockTaken.setTakenDate(dpStockTakenX.getTakenFinnishDate());
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTakenX.getTakenFinnishDate().getTime()+lHasilSisaTaken));
                                        //cek waktu istirahat
                                       /*long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp  = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                            }
                                            //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX)); 
                                            
                                        }*/
                                       //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime()));
                                        PstDpStockTaken.updateExc(dpStockTaken);
                                        listDpTaken.remove(x);
                                        x = x - 1;
                                    }

                            }

                        }// jika dpStockId di dpStock Taken != dpManagement maka di loop kembali ke atas
                         // }//jika flagnya ==0
                    }
                  }
                               
                }
            }
                
        //mencari sisa dari DP yg diatas  part 0
        Vector listDpManagementSisa = SessDpStockManagement.ListDpManagementToDpBalance(emp_number, true);
        
        LeaveApplication leaveApplication = new LeaveApplication();
        
              if (listDpManagementSisa != null && listDpManagementSisa.size() > 0) {
            for (int i = 0; i < listDpManagementSisa.size(); i++) {
                DpStockManagement dpStockManagement = (DpStockManagement) listDpManagementSisa.get(i);
                    Vector listDpTakenStockIdNol = SessDpStockManagement.ListDpTakenToDpBalance(dpStockManagement.getEmployeeId(), true); 
                boolean cekInisialisasiAwal = true;

                if (listDpTakenStockIdNol != null && listDpTakenStockIdNol.size() > 0) {
                    for (int x = 0; x < listDpTakenStockIdNol.size(); x++) {
                        DpStockTaken dpStockTaken = (DpStockTaken) listDpTakenStockIdNol.get(x);

                        if (dpStockManagement.getEmployeeId() == dpStockTaken.getEmployeeId()
                                && (dpStockManagement.getOID() == dpStockTaken.getDpStockId()|| dpStockTaken.getDpStockId()==0)) {
                            /**
                            if (dpStockManagement.getEmployeeId() == dpStockTaken.getEmployeeId()
                                && (dpStockManagement.getOID() == dpStockTaken.getDpStockId()|| dpStockTaken.getDpStockId()==0)) {
                            **/

                            ScheduleSymbol scheduleSymbol = PstEmpSchedule.getDailySchedule(dpStockTaken.getTakenDate(), dpStockTaken.getEmployeeId());
                            boolean cekPaidDate = false;
                            boolean cekOidTakenId= false;
                            if (dpStockTaken.getDpStockId() != 0) {
                                dpStockManagement = PstDpStockManagement.fetchExc(dpStockTaken.getDpStockId()); 
                            }
                            if (dpStockTaken.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED) {

                                if (cekInisialisasiAwal && dpStockManagement.getiDpQty() != 0 && dpStockTaken.getTakenQty() != 0
                                        && dpStockManagement.getiDpQty() >= dpStockTaken.getTakenQty()
                                        //tambahan
                                        //update by satrya 2013-03-20
                                        && dpStockManagement.getQtyResidue() > dpStockTaken.getTakenQty()) {
                                    //update by satrya 2013-03-23 jam 11
                                     if(dpStockManagement.getQtyResidue() > 0 && dpStockManagement.getQtyResidue() >= dpStockTaken.getTakenQty() && dpStockManagement.getQtyUsed()!=0){ 
                                         String sTmpTaken = Formater.formatNumber(dpStockManagement.getQtyResidue() - dpStockTaken.getTakenQty(),"#.##########"); 
                                         float tmpTaken = Float.parseFloat(sTmpTaken); 
                                         dpStockManagement.setQtyResidue(tmpTaken);
                                     }else{
                                         String sTmpTaken = Formater.formatNumber(dpStockManagement.getiDpQty() - dpStockTaken.getTakenQty(),"#.##########"); 
                                         float tmpTaken = Float.parseFloat(sTmpTaken); 
                                         dpStockManagement.setQtyResidue(tmpTaken);
                                     }
                                     //dpStockManagement.setQtyResidue(dpStockManagement.getiDpQty() - dpStockTaken.getTakenQty());
                                       //update by satrya 2013-03-23 jam 12
                                       if((dpStockManagement.getQtyUsed() + dpStockManagement.getQtyResidue()< dpStockManagement.getiDpQty())){
                                           dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockTaken.getTakenQty());
                                       }else{
                                              dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                       }
                                    //dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                    
                                    if (dpStockTaken.getPaidDate() == null) {
                                        dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                        dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        //dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        cekPaidDate = true;
                                        cekOidTakenId=true;  
                                    }
                                    //cekResidue = false;
                                    PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                    ///dan update qtry residue , qty used
                                    PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                    //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate); 
                                    cekPaidDate = false;//jika sdh di update di set default lge 
                                    cekInisialisasiAwal = false;

                                    listDpTakenStockIdNol.remove(x);
                                    x = x - 1;

                                } else {
                                    if (dpStockManagement.getQtyResidue() >= dpStockTaken.getTakenQty()) {
                                        dpStockManagement.setQtyResidue(dpStockManagement.getQtyResidue() - dpStockTaken.getTakenQty());
                                        dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockTaken.getTakenQty());
                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            //dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    } //jika resude < taken_qty dan residue tidak minus, maka taken_qty - residue dan sisanya akan di insert
                                    // lalu residue di set 0
                                    else if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() >= 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {

                                         String stmpQtyRes = Formater.formatNumber(dpStockManagement.getQtyResidue(), "#.###############");
                                        float tmpQtyRes = Float.parseFloat(stmpQtyRes);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty);
                                        
                                        dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockManagement.getQtyResidue());
                                        if (dpStockManagement.getQtyResidue() != 0) {
                                           
                                            dpStockTaken.setTakenQty(tmpQtyRes);
                                            //dpStockTaken.setTakenQty(dpStockManagement.getQtyResidue());
                                        }

                                        dpStockManagement.setQtyResidue(0);

                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                             dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                             //dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }
                                        //
                                        long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(tmpQtyRes, leaveConfig.getHourOneWorkday());
                                        //dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                           if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                //update by satrya 2013-03-18
                                                //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                           }//update by satrya 2013-03-18
                                            else{
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            } 
                                        }else{
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                        }
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        /* - update Taken:
                                         *  paid Date
                                         *  taken qty
                                         *  TakenDate
                                         *  FinishDate
                                         * - update Management :
                                         *  residue
                                         *  use      
                                         */
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                        //prosess insert  
                                        //update by satrya 2013-03-23 jam 18.41
                                        //di karenakan ada yg aneh di pengurangan tmpTakenQty - tmpQtyRes di 24014
                                            String s = Formater.formatNumber(tmpTakenQty - tmpQtyRes, "#.###"); 
                                            float tmpx = Float.parseFloat(s);
                                        if (tmpQtyRes != 0 && tmpx!=0  && tmpTakenQty > 0 && tmpQtyRes >= 0 && tmpTakenQty >= tmpQtyRes) {
                                            float hasilSisaTakenX = tmpTakenQty - tmpQtyRes;
                                            String stmpTakenQtyx = Formater.formatNumber(hasilSisaTakenX, "#.###############"); 
                                            float tmpTakenQtyx = Float.parseFloat(stmpTakenQtyx); 
                                            //DpStockTaken dpStockTakenX = new DpStockTaken();
                                            //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                            dpStockTaken.setDpStockId(0);
                                            dpStockTaken.setTakenQty(tmpTakenQtyx);
                                            dpStockTaken.setPaidDate(null);
                                            dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                              }
                                            dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                              if (dpStockTaken.getTakenQty() > 0) {
                                                        //mengubah lHasilSisaTaken menjadi melisecond
                                                        lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                               }
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                                //cek waktu istirahat
                                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if (intersecX != 0) {
                                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                            }
//                                            dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                            PstDpStockTaken.insertExc(dpStockTaken);
                                           
                                        }

                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;

                                    } //jika residuenya minus, dan taken Qty > DP Qty
                                    else if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() < 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {

                                       // float tmpStockQty = dpStockManagement.getiDpQty();
                                        //float tmpTakenQty = dpStockTaken.getTakenQty();
                                          String stmpStockQty = Formater.formatNumber(dpStockManagement.getiDpQty(), "#.###############");
                                        float tmpStockQty = Float.parseFloat(stmpStockQty);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockManagement.getiDpQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty); 
                                        dpStockTaken.setTakenQty(tmpStockQty);
                                        dpStockManagement.setQtyResidue(0);
                                        dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }

                                        // update by satrya 2013-01-21
                                        
                                        
                                        long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                        //dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                          if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                //dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            }else{
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            }
                                        }else{
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                        }
                                        //update by satrya 2013-03-2013
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                        if (tmpTakenQty > 0 && (tmpTakenQty - tmpStockQty)!=0&&tmpStockQty >= 0 && tmpTakenQty >= tmpStockQty) {
                                            float hasilSisaTakenX = tmpTakenQty - tmpStockQty;
                                            //update by satrya 2013-03-18
                                             String sTmpHasilSisaTakenX = Formater.formatNumber(hasilSisaTakenX,  "#.###############");
                                            float tmpHasilSisaTakenX = Float.parseFloat(sTmpHasilSisaTakenX);
                                            //DpStockTaken dpStockTakenX = new DpStockTaken();
                                            //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                            dpStockTaken.setDpStockId(0);
                                            dpStockTaken.setTakenQty(tmpHasilSisaTakenX);
                                            //dpStockTaken.setTakenQty(hasilSisaTakenX);
                                            dpStockTaken.setPaidDate(null);
                                            dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                            lHasilSisaTaken = 0;
                                            if (hasilSisaTakenX > 0) {
                                                //mengubah lHasilSisaTaken menjadi melisecond
                                                lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                            }
                                            //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                            }
                                            dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                            //cek waktu istirahat
                                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if (intersecX != 0) {
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                            }
//                                            dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                            PstDpStockTaken.insertExc(dpStockTaken);
                                        }
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    } //jika residue yg di gunakan sudah 0 atau abis
                                   /* else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0) {
                                        //DpStockTaken dpStockTaken = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                        dpStockTaken.setPaidDate(null);
                                        dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                        //long lHasilSisaTaken = 0;
                                        //if(dpStockTaken.getTakenQty() > 0){
                                        // lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                        // }
                                        //dpStockTaken.setTakenDate(dpStockTakenX.getTakenFinnishDate());
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTakenX.getTakenFinnishDate().getTime()+lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                            }
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        PstDpStockTaken.updateExc(dpStockTaken);
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    }*/ 
                                    //update by satrya 2013-03-22 jam 1
                                    //dan harus ada paid date'nya
                                    //update by satrya 2013-03-22 jam 12
                                    else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0 && dpStockTaken.getDpStockId()!=0
                                            && dpStockManagement.getOID()==dpStockTaken.getDpStockId() && dpStockTaken.getPaidDate()!=null) { 
                                        //DpStockTaken dpStockTaken = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                        dpStockTaken.setPaidDate(null);
                                        
                                       //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime()));
                                        PstDpStockTaken.updateExc(dpStockTaken);
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    }
                                }
                            } else {
                                // jika doc status belum di excecute, maka di update cuma taken saja
                                if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {
                                    //float tmpQtyRes = dpStockManagement.getQtyResidue();
                                    //float tmpTakenQty = dpStockTaken.getTakenQty();
                                      String stmpQtyRes = Formater.formatNumber(dpStockManagement.getQtyResidue(), "#.###############");
                                        float tmpQtyRes = Float.parseFloat(stmpQtyRes);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty);
                                    //dpStockManagement.setQtyResidue(0);
                                    //dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed()+dpStockManagement.getQtyResidue());
                                    if (dpStockManagement.getQtyResidue() != 0) {
                                        dpStockTaken.setTakenQty(tmpQtyRes);
                                        // dpStockTaken.setTakenQty(dpStockManagement.getQtyResidue());
                                    }
                                    if (dpStockTaken.getPaidDate() == null) {
                                        dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                        dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        cekPaidDate = true;
                                        cekOidTakenId=true;
                                    }
                                    //
                                    long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockManagement.getQtyResidue(), leaveConfig.getHourOneWorkday());


                                    //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                    if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                        if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                            Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                            takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                            takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                            takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                            Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                            finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                            finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                            finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                            dpStockTaken.setTakenDate(takenDateTmp);
                                            dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                        }
                                    }
                                    dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                    //cek waktu istirahat
                                    long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                    if (intersecX != 0) {
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                    }
                                    /* - update Taken:
                                     *  paid Date
                                     *  taken qty
                                     *  TakenDate
                                     *  FinishDate
                                     * - update Management :
                                     *  residue
                                     *  use      
                                     */
                                    PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                    ///dan update qtry residue , qty used
                                    //PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                    cekPaidDate = false;//jika sdh di update di set default lge
                                    //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                    //prosess insert   
                                    if (tmpQtyRes != 0 && (tmpTakenQty - tmpQtyRes)!=0 && tmpTakenQty > 0 && tmpQtyRes >= 0 && tmpTakenQty >= tmpQtyRes) {
                                        float hasilSisaTakenX = tmpTakenQty - tmpQtyRes;
                                        //update by satrya 2013-03-18
                                         String stmpHasilSisaTakenX = Formater.formatNumber(hasilSisaTakenX,  "#.###############");
                                    float tmpHasilSisaTakenX = Float.parseFloat(stmpHasilSisaTakenX);
                                        //DpStockTaken dpStockTakenX = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        dpStockTaken.setTakenQty(tmpHasilSisaTakenX);
                                        //dpStockTaken.setTakenQty(hasilSisaTakenX);
                                        dpStockTaken.setPaidDate(null);
                                        dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                        lHasilSisaTaken = 0;
                                        if (hasilSisaTakenX > 0) {
                                            //mengubah lHasilSisaTaken menjadi melisecond
                                            lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                        }
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                            }
                                        }
                                        dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                        //cek waktu istirahat
                                        intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
//                                        dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                        PstDpStockTaken.insertExc(dpStockTaken);
                                    }
                                    listDpTakenStockIdNol.remove(x);
                                    x = x - 1;

                                } //jika residue yg di gunakan sudah 0 atau abis
                               /* else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0) {
                                    //DpStockTaken dpStockTaken = new DpStockTaken();
                                    //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                    dpStockTaken.setDpStockId(0);
                                    //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                    dpStockTaken.setPaidDate(null);
                                    dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                    //long lHasilSisaTaken = 0;
                                    //if(dpStockTaken.getTakenQty() > 0){
                                    // lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                    // }
                                    //dpStockTaken.setTakenDate(dpStockTakenX.getTakenFinnishDate());
                                    //dpStockTaken.setTakenFinnishDate(new Date(dpStockTakenX.getTakenFinnishDate().getTime()+lHasilSisaTaken));
                                    //cek waktu istirahat
                                    long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                    if (intersecX != 0) {
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                            }
                                        }
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                    }
                                    PstDpStockTaken.updateExc(dpStockTaken);
                                    listDpTakenStockIdNol.remove(x);
                                    x = x - 1;

                                }*/

                            }

                        }// jika dpStockId di dpStock Taken != dpManagement maka di loop kembali ke atas

                   
                }
            }

        }
        
 //update jam 10 tgl 23-03-2013
                    //mencari sisa dari DP yg diatas  part 0
        listDpManagementSisa = SessDpStockManagement.ListDpManagementToDpBalance(emp_number, true);
        
         leaveApplication = new LeaveApplication(); 
        
              if (listDpManagementSisa != null && listDpManagementSisa.size() > 0) {
            for (int i = 0; i < listDpManagementSisa.size(); i++) {
                DpStockManagement dpStockManagement = (DpStockManagement) listDpManagementSisa.get(i);
                    Vector listDpTakenStockIdNol = SessDpStockManagement.ListDpTakenToDpBalance(dpStockManagement.getEmployeeId(), true); 
                boolean cekInisialisasiAwal = true;

                if (listDpTakenStockIdNol != null && listDpTakenStockIdNol.size() > 0) {
                    for (int x = 0; x < listDpTakenStockIdNol.size(); x++) {
                        DpStockTaken dpStockTaken = (DpStockTaken) listDpTakenStockIdNol.get(x);

                        if (dpStockManagement.getEmployeeId() == dpStockTaken.getEmployeeId()
                                && (dpStockManagement.getOID() == dpStockTaken.getDpStockId()|| dpStockTaken.getDpStockId()==0)) {
                            /**
                            if (dpStockManagement.getEmployeeId() == dpStockTaken.getEmployeeId()
                                && (dpStockManagement.getOID() == dpStockTaken.getDpStockId()|| dpStockTaken.getDpStockId()==0)) {
                            **/

                            ScheduleSymbol scheduleSymbol = PstEmpSchedule.getDailySchedule(dpStockTaken.getTakenDate(), dpStockTaken.getEmployeeId());
                            boolean cekPaidDate = false;
                            boolean cekOidTakenId= false;
                            if (dpStockTaken.getDpStockId() != 0) {
                                dpStockManagement = PstDpStockManagement.fetchExc(dpStockTaken.getDpStockId()); 
                            }
                            if (dpStockTaken.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED) {

                                if (cekInisialisasiAwal && dpStockManagement.getiDpQty() != 0 && dpStockTaken.getTakenQty() != 0
                                        && dpStockManagement.getiDpQty() >= dpStockTaken.getTakenQty()
                                        //tambahan
                                        //update by satrya 2013-03-20
                                        && dpStockManagement.getQtyResidue() > dpStockTaken.getTakenQty()) {
                                    //update by satrya 2013-03-23 jam 11
                                     if(dpStockManagement.getQtyResidue() > 0 && dpStockManagement.getQtyResidue() >= dpStockTaken.getTakenQty() && dpStockManagement.getQtyUsed()!=0){ 
                                         String sTmpTaken = Formater.formatNumber(dpStockManagement.getQtyResidue() - dpStockTaken.getTakenQty(),"#.##########"); 
                                         float tmpTaken = Float.parseFloat(sTmpTaken); 
                                         dpStockManagement.setQtyResidue(tmpTaken);
                                     }else{
                                         String sTmpTaken = Formater.formatNumber(dpStockManagement.getiDpQty() - dpStockTaken.getTakenQty(),"#.##########"); 
                                         float tmpTaken = Float.parseFloat(sTmpTaken); 
                                         dpStockManagement.setQtyResidue(tmpTaken);
                                     }
                                     //dpStockManagement.setQtyResidue(dpStockManagement.getiDpQty() - dpStockTaken.getTakenQty());
                                       //update by satrya 2013-03-23 jam 12
                                       if((dpStockManagement.getQtyUsed() + dpStockManagement.getQtyResidue()< dpStockManagement.getiDpQty())){
                                           dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockTaken.getTakenQty());
                                       }else{
                                              dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                       }
                                    //dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                    
                                    if (dpStockTaken.getPaidDate() == null) {
                                        dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                        dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        //dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        cekPaidDate = true;
                                        cekOidTakenId=true;  
                                    }
                                    //cekResidue = false;
                                    PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                    ///dan update qtry residue , qty used
                                    PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                    //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate); 
                                    cekPaidDate = false;//jika sdh di update di set default lge 
                                    cekInisialisasiAwal = false;

                                    listDpTakenStockIdNol.remove(x);
                                    x = x - 1;

                                } else {
                                    if (dpStockManagement.getQtyResidue() >= dpStockTaken.getTakenQty()) {
                                        dpStockManagement.setQtyResidue(dpStockManagement.getQtyResidue() - dpStockTaken.getTakenQty());
                                        dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockTaken.getTakenQty());
                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            //dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    } //jika resude < taken_qty dan residue tidak minus, maka taken_qty - residue dan sisanya akan di insert
                                    // lalu residue di set 0
                                    else if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() >= 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {

                                         String stmpQtyRes = Formater.formatNumber(dpStockManagement.getQtyResidue(), "#.###############");
                                        float tmpQtyRes = Float.parseFloat(stmpQtyRes);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty);
                                        
                                        dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockManagement.getQtyResidue());
                                        if (dpStockManagement.getQtyResidue() != 0) {
                                           
                                            dpStockTaken.setTakenQty(tmpQtyRes);
                                            //dpStockTaken.setTakenQty(dpStockManagement.getQtyResidue());
                                        }

                                        dpStockManagement.setQtyResidue(0);

                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                             dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                             //dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }
                                        //
                                        long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(tmpQtyRes, leaveConfig.getHourOneWorkday());
                                        //dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                           if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                //update by satrya 2013-03-18
                                                //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                           }//update by satrya 2013-03-18
                                            else{
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            } 
                                        }else{
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                        }
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        /* - update Taken:
                                         *  paid Date
                                         *  taken qty
                                         *  TakenDate
                                         *  FinishDate
                                         * - update Management :
                                         *  residue
                                         *  use      
                                         */
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                        //prosess insert  
                                        //update by satrya 2013-03-23 jam 18.41
                                        //di karenakan ada yg aneh di pengurangan tmpTakenQty - tmpQtyRes di 24014
                                            String s = Formater.formatNumber(tmpTakenQty - tmpQtyRes, "#.###"); 
                                            float tmpx = Float.parseFloat(s);
                                        if (tmpQtyRes != 0 && tmpx!=0  && tmpTakenQty > 0 && tmpQtyRes >= 0 && tmpTakenQty >= tmpQtyRes) {
                                            float hasilSisaTakenX = tmpTakenQty - tmpQtyRes;
                                            String stmpTakenQtyx = Formater.formatNumber(hasilSisaTakenX, "#.###############"); 
                                            float tmpTakenQtyx = Float.parseFloat(stmpTakenQtyx); 
                                            //DpStockTaken dpStockTakenX = new DpStockTaken();
                                            //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                            dpStockTaken.setDpStockId(0);
                                            dpStockTaken.setTakenQty(tmpTakenQtyx);
                                            dpStockTaken.setPaidDate(null);
                                            dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                              }
                                            dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                              if (dpStockTaken.getTakenQty() > 0) {
                                                        //mengubah lHasilSisaTaken menjadi melisecond
                                                        lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                               }
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                                //cek waktu istirahat
                                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if (intersecX != 0) {
                                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                            }
//                                            dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                            PstDpStockTaken.insertExc(dpStockTaken);
                                           
                                        }

                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;

                                    } //jika residuenya minus, dan taken Qty > DP Qty
                                    else if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() < 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {

                                       // float tmpStockQty = dpStockManagement.getiDpQty();
                                        //float tmpTakenQty = dpStockTaken.getTakenQty();
                                          String stmpStockQty = Formater.formatNumber(dpStockManagement.getiDpQty(), "#.###############");
                                        float tmpStockQty = Float.parseFloat(stmpStockQty);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockManagement.getiDpQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty); 
                                        dpStockTaken.setTakenQty(tmpStockQty);
                                        dpStockManagement.setQtyResidue(0);
                                        dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }

                                        // update by satrya 2013-01-21
                                        
                                        
                                        long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                        //dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                          if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                //dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            }else{
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            }
                                        }else{
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                        }
                                        //update by satrya 2013-03-2013
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                        if (tmpTakenQty > 0 && (tmpTakenQty - tmpStockQty)!=0&&tmpStockQty >= 0 && tmpTakenQty >= tmpStockQty) {
                                            float hasilSisaTakenX = tmpTakenQty - tmpStockQty;
                                            //update by satrya 2013-03-18
                                             String sTmpHasilSisaTakenX = Formater.formatNumber(hasilSisaTakenX,  "#.###############");
                                            float tmpHasilSisaTakenX = Float.parseFloat(sTmpHasilSisaTakenX);
                                            //DpStockTaken dpStockTakenX = new DpStockTaken();
                                            //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                            dpStockTaken.setDpStockId(0);
                                            dpStockTaken.setTakenQty(tmpHasilSisaTakenX);
                                            //dpStockTaken.setTakenQty(hasilSisaTakenX);
                                            dpStockTaken.setPaidDate(null);
                                            dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                            lHasilSisaTaken = 0;
                                            if (hasilSisaTakenX > 0) {
                                                //mengubah lHasilSisaTaken menjadi melisecond
                                                lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                            }
                                            //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                            }
                                            dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                            //cek waktu istirahat
                                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if (intersecX != 0) {
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                            }
//                                            dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                            PstDpStockTaken.insertExc(dpStockTaken);
                                        }
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    } //jika residue yg di gunakan sudah 0 atau abis
                                   /* else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0) {
                                        //DpStockTaken dpStockTaken = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                        dpStockTaken.setPaidDate(null);
                                        dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                        //long lHasilSisaTaken = 0;
                                        //if(dpStockTaken.getTakenQty() > 0){
                                        // lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                        // }
                                        //dpStockTaken.setTakenDate(dpStockTakenX.getTakenFinnishDate());
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTakenX.getTakenFinnishDate().getTime()+lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                            }
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        PstDpStockTaken.updateExc(dpStockTaken);
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    }*/ 
                                    //update by satrya 2013-03-22 jam 1
                                    //dan harus ada paid date'nya
                                    //update by satrya 2013-03-22 jam 12
                                    else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0 && dpStockTaken.getDpStockId()!=0
                                            && dpStockManagement.getOID()==dpStockTaken.getDpStockId() && dpStockTaken.getPaidDate()!=null) { 
                                        //DpStockTaken dpStockTaken = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                        dpStockTaken.setPaidDate(null);
                                        
                                       //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime()));
                                        PstDpStockTaken.updateExc(dpStockTaken);
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    }
                                }
                            } else {
                                // jika doc status belum di excecute, maka di update cuma taken saja
                                if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {
                                    //float tmpQtyRes = dpStockManagement.getQtyResidue();
                                    //float tmpTakenQty = dpStockTaken.getTakenQty();
                                      String stmpQtyRes = Formater.formatNumber(dpStockManagement.getQtyResidue(), "#.###############");
                                        float tmpQtyRes = Float.parseFloat(stmpQtyRes);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty);
                                    //dpStockManagement.setQtyResidue(0);
                                    //dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed()+dpStockManagement.getQtyResidue());
                                    if (dpStockManagement.getQtyResidue() != 0) {
                                        dpStockTaken.setTakenQty(tmpQtyRes);
                                        // dpStockTaken.setTakenQty(dpStockManagement.getQtyResidue());
                                    }
                                    if (dpStockTaken.getPaidDate() == null) {
                                        dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                        dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        cekPaidDate = true;
                                        cekOidTakenId=true;
                                    }
                                    //
                                    long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockManagement.getQtyResidue(), leaveConfig.getHourOneWorkday());


                                    //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                    if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                        if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                            Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                            takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                            takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                            takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                            Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                            finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                            finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                            finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                            dpStockTaken.setTakenDate(takenDateTmp);
                                            dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                        }
                                    }
                                    dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                    //cek waktu istirahat
                                    long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                    if (intersecX != 0) {
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                    }
                                    /* - update Taken:
                                     *  paid Date
                                     *  taken qty
                                     *  TakenDate
                                     *  FinishDate
                                     * - update Management :
                                     *  residue
                                     *  use      
                                     */
                                    PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                    ///dan update qtry residue , qty used
                                    //PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                    cekPaidDate = false;//jika sdh di update di set default lge
                                    //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                    //prosess insert   
                                    if (tmpQtyRes != 0 && (tmpTakenQty - tmpQtyRes)!=0 && tmpTakenQty > 0 && tmpQtyRes >= 0 && tmpTakenQty >= tmpQtyRes) {
                                        float hasilSisaTakenX = tmpTakenQty - tmpQtyRes;
                                        //update by satrya 2013-03-18
                                         String stmpHasilSisaTakenX = Formater.formatNumber(hasilSisaTakenX,  "#.###############");
                                    float tmpHasilSisaTakenX = Float.parseFloat(stmpHasilSisaTakenX);
                                        //DpStockTaken dpStockTakenX = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        dpStockTaken.setTakenQty(tmpHasilSisaTakenX);
                                        //dpStockTaken.setTakenQty(hasilSisaTakenX);
                                        dpStockTaken.setPaidDate(null);
                                        dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                        lHasilSisaTaken = 0;
                                        if (hasilSisaTakenX > 0) {
                                            //mengubah lHasilSisaTaken menjadi melisecond
                                            lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                        }
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                            }
                                        }
                                        dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                        //cek waktu istirahat
                                        intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
//                                        dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                        PstDpStockTaken.insertExc(dpStockTaken);
                                    }
                                    listDpTakenStockIdNol.remove(x);
                                    x = x - 1;

                                } //jika residue yg di gunakan sudah 0 atau abis
                               /* else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0) {
                                    //DpStockTaken dpStockTaken = new DpStockTaken();
                                    //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                    dpStockTaken.setDpStockId(0);
                                    //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                    dpStockTaken.setPaidDate(null);
                                    dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                    //long lHasilSisaTaken = 0;
                                    //if(dpStockTaken.getTakenQty() > 0){
                                    // lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                    // }
                                    //dpStockTaken.setTakenDate(dpStockTakenX.getTakenFinnishDate());
                                    //dpStockTaken.setTakenFinnishDate(new Date(dpStockTakenX.getTakenFinnishDate().getTime()+lHasilSisaTaken));
                                    //cek waktu istirahat
                                    long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                    if (intersecX != 0) {
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                            }
                                        }
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                    }
                                    PstDpStockTaken.updateExc(dpStockTaken);
                                    listDpTakenStockIdNol.remove(x);
                                    x = x - 1;

                                }*/

                            }

                        }// jika dpStockId di dpStock Taken != dpManagement maka di loop kembali ke atas

                   
                }
            }

        }
     }
         
     listDpManagementSisa = SessDpStockManagement.ListDpManagementToDpBalance(emp_number, true);
        
         leaveApplication = new LeaveApplication(); 
        
              if (listDpManagementSisa != null && listDpManagementSisa.size() > 0) {
            for (int i = 0; i < listDpManagementSisa.size(); i++) {
                DpStockManagement dpStockManagement = (DpStockManagement) listDpManagementSisa.get(i);
                    Vector listDpTakenStockIdNol = SessDpStockManagement.ListDpTakenToDpBalance(dpStockManagement.getEmployeeId(), true); 
                boolean cekInisialisasiAwal = true;

                if (listDpTakenStockIdNol != null && listDpTakenStockIdNol.size() > 0) {
                    for (int x = 0; x < listDpTakenStockIdNol.size(); x++) {
                        DpStockTaken dpStockTaken = (DpStockTaken) listDpTakenStockIdNol.get(x);

                        if (dpStockManagement.getEmployeeId() == dpStockTaken.getEmployeeId()
                                && (dpStockManagement.getOID() == dpStockTaken.getDpStockId()|| dpStockTaken.getDpStockId()==0)) {
                            /**
                            if (dpStockManagement.getEmployeeId() == dpStockTaken.getEmployeeId()
                                && (dpStockManagement.getOID() == dpStockTaken.getDpStockId()|| dpStockTaken.getDpStockId()==0)) {
                            **/

                            ScheduleSymbol scheduleSymbol = PstEmpSchedule.getDailySchedule(dpStockTaken.getTakenDate(), dpStockTaken.getEmployeeId());
                            boolean cekPaidDate = false;
                            boolean cekOidTakenId= false;
                            if (dpStockTaken.getDpStockId() != 0) {
                                dpStockManagement = PstDpStockManagement.fetchExc(dpStockTaken.getDpStockId()); 
                            }
                            if (dpStockTaken.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED) {

                                if (cekInisialisasiAwal && dpStockManagement.getiDpQty() != 0 && dpStockTaken.getTakenQty() != 0
                                        && dpStockManagement.getiDpQty() >= dpStockTaken.getTakenQty()
                                        //tambahan
                                        //update by satrya 2013-03-20
                                        && dpStockManagement.getQtyResidue() > dpStockTaken.getTakenQty()) {
                                    //update by satrya 2013-03-23 jam 11
                                     if(dpStockManagement.getQtyResidue() > 0 && dpStockManagement.getQtyResidue() >= dpStockTaken.getTakenQty() && dpStockManagement.getQtyUsed()!=0){ 
                                         String sTmpTaken = Formater.formatNumber(dpStockManagement.getQtyResidue() - dpStockTaken.getTakenQty(),"#.##########"); 
                                         float tmpTaken = Float.parseFloat(sTmpTaken); 
                                         dpStockManagement.setQtyResidue(tmpTaken);
                                     }else{
                                         String sTmpTaken = Formater.formatNumber(dpStockManagement.getiDpQty() - dpStockTaken.getTakenQty(),"#.##########"); 
                                         float tmpTaken = Float.parseFloat(sTmpTaken); 
                                         dpStockManagement.setQtyResidue(tmpTaken);
                                     }
                                     //dpStockManagement.setQtyResidue(dpStockManagement.getiDpQty() - dpStockTaken.getTakenQty());
                                       //update by satrya 2013-03-23 jam 12
                                       if((dpStockManagement.getQtyUsed() + dpStockManagement.getQtyResidue()< dpStockManagement.getiDpQty())){
                                           dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockTaken.getTakenQty());
                                       }else{
                                              dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                       }
                                    //dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                    
                                    if (dpStockTaken.getPaidDate() == null) {
                                        dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                        dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        //dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        cekPaidDate = true;
                                        cekOidTakenId=true;  
                                    }
                                    //cekResidue = false;
                                    PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                    ///dan update qtry residue , qty used
                                    PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                    //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate); 
                                    cekPaidDate = false;//jika sdh di update di set default lge 
                                    cekInisialisasiAwal = false;

                                    listDpTakenStockIdNol.remove(x);
                                    x = x - 1;

                                } else {
                                    if (dpStockManagement.getQtyResidue() >= dpStockTaken.getTakenQty()) {
                                        dpStockManagement.setQtyResidue(dpStockManagement.getQtyResidue() - dpStockTaken.getTakenQty());
                                        dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockTaken.getTakenQty());
                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            //dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    } //jika resude < taken_qty dan residue tidak minus, maka taken_qty - residue dan sisanya akan di insert
                                    // lalu residue di set 0
                                    else if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() >= 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {

                                         String stmpQtyRes = Formater.formatNumber(dpStockManagement.getQtyResidue(), "#.###############");
                                        float tmpQtyRes = Float.parseFloat(stmpQtyRes);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty);
                                        
                                        dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed() + dpStockManagement.getQtyResidue());
                                        if (dpStockManagement.getQtyResidue() != 0) {
                                           
                                            dpStockTaken.setTakenQty(tmpQtyRes);
                                            //dpStockTaken.setTakenQty(dpStockManagement.getQtyResidue());
                                        }

                                        dpStockManagement.setQtyResidue(0);

                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                             dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                             //dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }
                                        //
                                        long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(tmpQtyRes, leaveConfig.getHourOneWorkday());
                                        //dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                           if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                //update by satrya 2013-03-18
                                                //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                           }//update by satrya 2013-03-18
                                            else{
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            } 
                                        }else{
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                        }
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        /* - update Taken:
                                         *  paid Date
                                         *  taken qty
                                         *  TakenDate
                                         *  FinishDate
                                         * - update Management :
                                         *  residue
                                         *  use      
                                         */
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                        //prosess insert  
                                        //update by satrya 2013-03-23 jam 18.41
                                        //di karenakan ada yg aneh di pengurangan tmpTakenQty - tmpQtyRes di 24014
                                            String s = Formater.formatNumber(tmpTakenQty - tmpQtyRes, "#.###"); 
                                            float tmpx = Float.parseFloat(s);
                                        if (tmpQtyRes != 0 && tmpx!=0  && tmpTakenQty > 0 && tmpQtyRes >= 0 && tmpTakenQty >= tmpQtyRes) {
                                            float hasilSisaTakenX = tmpTakenQty - tmpQtyRes;
                                            String stmpTakenQtyx = Formater.formatNumber(hasilSisaTakenX, "#.###############"); 
                                            float tmpTakenQtyx = Float.parseFloat(stmpTakenQtyx); 
                                            //DpStockTaken dpStockTakenX = new DpStockTaken();
                                            //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                            dpStockTaken.setDpStockId(0);
                                            dpStockTaken.setTakenQty(tmpTakenQtyx);
                                            dpStockTaken.setPaidDate(null);
                                            dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                              }
                                            dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                              if (dpStockTaken.getTakenQty() > 0) {
                                                        //mengubah lHasilSisaTaken menjadi melisecond
                                                        lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                               }
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                                //cek waktu istirahat
                                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if (intersecX != 0) {
                                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                            }
//                                            dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                            PstDpStockTaken.insertExc(dpStockTaken);
                                           
                                        }

                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;

                                    } //jika residuenya minus, dan taken Qty > DP Qty
                                    else if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() < 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {

                                       // float tmpStockQty = dpStockManagement.getiDpQty();
                                        //float tmpTakenQty = dpStockTaken.getTakenQty();
                                          String stmpStockQty = Formater.formatNumber(dpStockManagement.getiDpQty(), "#.###############");
                                        float tmpStockQty = Float.parseFloat(stmpStockQty);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockManagement.getiDpQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty); 
                                        dpStockTaken.setTakenQty(tmpStockQty);
                                        dpStockManagement.setQtyResidue(0);
                                        dpStockManagement.setQtyUsed(dpStockTaken.getTakenQty());
                                        if (dpStockTaken.getPaidDate() == null) {
                                            dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                            dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                            cekPaidDate = true;
                                            cekOidTakenId=true;
                                        }

                                        // update by satrya 2013-01-21
                                        
                                        
                                        long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                        //dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                          if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                //dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            }else{
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                            }
                                        }else{
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime() + lHasilSisaTaken));
                                        }
                                        //update by satrya 2013-03-2013
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() - lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                        ///dan update qtry residue , qty used
                                        PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                        cekPaidDate = false;//jika sdh di update di set default lge
                                        //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                        if (tmpTakenQty > 0 && (tmpTakenQty - tmpStockQty)!=0&&tmpStockQty >= 0 && tmpTakenQty >= tmpStockQty) {
                                            float hasilSisaTakenX = tmpTakenQty - tmpStockQty;
                                            //update by satrya 2013-03-18
                                             String sTmpHasilSisaTakenX = Formater.formatNumber(hasilSisaTakenX,  "#.###############");
                                            float tmpHasilSisaTakenX = Float.parseFloat(sTmpHasilSisaTakenX);
                                            //DpStockTaken dpStockTakenX = new DpStockTaken();
                                            //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                            dpStockTaken.setDpStockId(0);
                                            dpStockTaken.setTakenQty(tmpHasilSisaTakenX);
                                            //dpStockTaken.setTakenQty(hasilSisaTakenX);
                                            dpStockTaken.setPaidDate(null);
                                            dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                            lHasilSisaTaken = 0;
                                            if (hasilSisaTakenX > 0) {
                                                //mengubah lHasilSisaTaken menjadi melisecond
                                                lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                            }
                                            //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                            }
                                            dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                            //cek waktu istirahat
                                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if (intersecX != 0) {
                                                dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                            }
//                                            dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                            PstDpStockTaken.insertExc(dpStockTaken);
                                        }
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    } //jika residue yg di gunakan sudah 0 atau abis
                                   /* else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0) {
                                        //DpStockTaken dpStockTaken = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                        dpStockTaken.setPaidDate(null);
                                        dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                        //long lHasilSisaTaken = 0;
                                        //if(dpStockTaken.getTakenQty() > 0){
                                        // lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                        // }
                                        //dpStockTaken.setTakenDate(dpStockTakenX.getTakenFinnishDate());
                                        //dpStockTaken.setTakenFinnishDate(new Date(dpStockTakenX.getTakenFinnishDate().getTime()+lHasilSisaTaken));
                                        //cek waktu istirahat
                                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                            if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                                if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                    Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                    takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                    takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                    takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                    Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                    finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                    finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                    finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                    dpStockTaken.setTakenDate(takenDateTmp);
                                                    dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                                }
                                            }
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
                                        PstDpStockTaken.updateExc(dpStockTaken);
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    }*/ 
                                    //update by satrya 2013-03-22 jam 1
                                    //dan harus ada paid date'nya
                                    //update by satrya 2013-03-22 jam 12
                                    else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0 && dpStockTaken.getDpStockId()!=0
                                            && dpStockManagement.getOID()==dpStockTaken.getDpStockId() && dpStockTaken.getPaidDate()!=null) { 
                                        //DpStockTaken dpStockTaken = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                        dpStockTaken.setPaidDate(null);
                                        
                                       //dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime()));
                                        PstDpStockTaken.updateExc(dpStockTaken);
                                        listDpTakenStockIdNol.remove(x);
                                        x = x - 1;
                                    }
                                }
                            } else {
                                // jika doc status belum di excecute, maka di update cuma taken saja
                                if (dpStockManagement.getQtyResidue() != 0 && dpStockManagement.getQtyResidue() < dpStockTaken.getTakenQty()) {
                                    //float tmpQtyRes = dpStockManagement.getQtyResidue();
                                    //float tmpTakenQty = dpStockTaken.getTakenQty();
                                      String stmpQtyRes = Formater.formatNumber(dpStockManagement.getQtyResidue(), "#.###############");
                                        float tmpQtyRes = Float.parseFloat(stmpQtyRes);
                                        //update by satrya 2013-03-16
                                        String stmpTakenQty = Formater.formatNumber(dpStockTaken.getTakenQty(), "#.###############");
                                        float tmpTakenQty = Float.parseFloat(stmpTakenQty);
                                    //dpStockManagement.setQtyResidue(0);
                                    //dpStockManagement.setQtyUsed(dpStockManagement.getQtyUsed()+dpStockManagement.getQtyResidue());
                                    if (dpStockManagement.getQtyResidue() != 0) {
                                        dpStockTaken.setTakenQty(tmpQtyRes);
                                        // dpStockTaken.setTakenQty(dpStockManagement.getQtyResidue());
                                    }
                                    if (dpStockTaken.getPaidDate() == null) {
                                        dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                                        dpStockTaken.setDpStockId(dpStockManagement.getOID());
                                        cekPaidDate = true;
                                        cekOidTakenId=true;
                                    }
                                    //
                                    long lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockManagement.getQtyResidue(), leaveConfig.getHourOneWorkday());


                                    //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                    if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                        if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                            Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                            takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                            takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                            takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                            Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                            finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                            finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                            finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                            dpStockTaken.setTakenDate(takenDateTmp);
                                            dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                        }
                                    }
                                    dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                    dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                    //cek waktu istirahat
                                    long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                    if (intersecX != 0) {
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                    }
                                    /* - update Taken:
                                     *  paid Date
                                     *  taken qty
                                     *  TakenDate
                                     *  FinishDate
                                     * - update Management :
                                     *  residue
                                     *  use      
                                     */
                                    PstDpStockTaken.updateTakenNew(dpStockTaken, cekPaidDate, cekOidTakenId);
                                    ///dan update qtry residue , qty used
                                    //PstDpStockManagement.updateQtyUsedResidue(dpStockManagement);
                                    cekPaidDate = false;//jika sdh di update di set default lge
                                    //SessDpStockManagement.updateTakenAndQtyUseAndResidueAndPaidDate(dpChekBalancingX,cekPaidDate);

                                    //prosess insert   
                                    if (tmpQtyRes != 0 && (tmpTakenQty - tmpQtyRes)!=0 && tmpTakenQty > 0 && tmpQtyRes >= 0 && tmpTakenQty >= tmpQtyRes) {
                                        float hasilSisaTakenX = tmpTakenQty - tmpQtyRes;
                                        //update by satrya 2013-03-18
                                         String stmpHasilSisaTakenX = Formater.formatNumber(hasilSisaTakenX,  "#.###############");
                                    float tmpHasilSisaTakenX = Float.parseFloat(stmpHasilSisaTakenX);
                                        //DpStockTaken dpStockTakenX = new DpStockTaken();
                                        //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                        dpStockTaken.setDpStockId(0);
                                        dpStockTaken.setTakenQty(tmpHasilSisaTakenX);
                                        //dpStockTaken.setTakenQty(hasilSisaTakenX);
                                        dpStockTaken.setPaidDate(null);
                                        dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                        lHasilSisaTaken = 0;
                                        if (hasilSisaTakenX > 0) {
                                            //mengubah lHasilSisaTaken menjadi melisecond
                                            lHasilSisaTaken = Formater.getWorkDayMiliSeconds(hasilSisaTakenX, leaveConfig.getHourOneWorkday());
                                        }
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                            }
                                        }
                                        dpStockTaken.setTakenDate(dpStockTaken.getTakenFinnishDate());
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + lHasilSisaTaken));
                                        //cek waktu istirahat
                                        intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if (intersecX != 0) {
                                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                        }
//                                        dpStockTaken.setFlagDpBalance(PstDpStockTaken.DP_BALANCE_CHECKED); 
                                        PstDpStockTaken.insertExc(dpStockTaken);
                                    }
                                    listDpTakenStockIdNol.remove(x);
                                    x = x - 1;

                                } //jika residue yg di gunakan sudah 0 atau abis
                               /* else if (dpStockManagement != null && dpStockManagement.getQtyResidue() == 0) {
                                    //DpStockTaken dpStockTaken = new DpStockTaken();
                                    //dpStockTaken.setEmployeeId(dpChekBalancingX.getDp_employee_id());
                                    dpStockTaken.setDpStockId(0);
                                    //dpStockTaken.setTakenQty(dpChekBalancingX.getDp_takenQty());
                                    dpStockTaken.setPaidDate(null);
                                    dpStockTaken.setLeaveApplicationId(dpStockTaken.getLeaveApplicationId());//0 

                                    //long lHasilSisaTaken = 0;
                                    //if(dpStockTaken.getTakenQty() > 0){
                                    // lHasilSisaTaken = Formater.getWorkDayMiliSeconds(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                                    // }
                                    //dpStockTaken.setTakenDate(dpStockTakenX.getTakenFinnishDate());
                                    //dpStockTaken.setTakenFinnishDate(new Date(dpStockTakenX.getTakenFinnishDate().getTime()+lHasilSisaTaken));
                                    //cek waktu istirahat
                                    long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                    if (intersecX != 0) {
                                        //jika user memilih dp_stock_taken dan DP_FINISH = 00:00:00 atau 1 hari
                                        if (scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                            if (dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) {
                                                Date takenDateTmp = new Date(dpStockTaken.getTakenDate().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());

                                                Date finishDateTmp = new Date(dpStockTaken.getTakenFinnishDate().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());

                                                dpStockTaken.setTakenDate(takenDateTmp);
                                                dpStockTaken.setTakenFinnishDate(finishDateTmp);
                                            }
                                        }
                                        dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime() + intersecX));
                                    }
                                    PstDpStockTaken.updateExc(dpStockTaken);
                                    listDpTakenStockIdNol.remove(x);
                                    x = x - 1;

                                }*/

                            }

                        }// jika dpStockId di dpStock Taken != dpManagement maka di loop kembali ke atas

                   
                }
            }

        }
     }
        //long excCekDpBalance = SessDpStockManagement.ExcDpBalanceCheckByEmpNumber(emp_number);  
        /*DpStockManagement dpStockManagementX = null;
        // ?
        long oidStockTakenX=0;
        String order = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" ASC ";
        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"="+emp_number + " AND "
        +PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+" !=0 ";
        
        String orderX = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]+" ASC ";
        String whereClauseX = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"="+emp_number+ " AND "
        +PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID]+" =0 ";
        
        //mencari jika masih ada residue
        Vector listDpStockManagement = PstDpStockManagement.listByEmpNumb(whereClause, order);   
        Vector listDpStockTaken = PstDpStockTaken.listStockTakenByEmpNumb(whereClauseX, orderX);
        if(listDpStockTaken!=null && listDpStockTaken.size()>0){
        for(int idx=0; idx<listDpStockTaken.size(); idx++){
        DpStockTaken dpStockTaken = (DpStockTaken)listDpStockTaken.get(idx);
        
        if(listDpStockManagement!=null && listDpStockManagement.size()>0){
        for(int xx=0; xx<listDpStockManagement.size();xx++){
        DpChekBalancing dpChekBalancing = (DpChekBalancing)listDpStockManagement.get(xx);
        
        dpStockTaken.setDpStockId(dpChekBalancing.getDp_stokId()); 
        dpStockTaken.setPaidDate(dpChekBalancing.getDp_owningDate()); 
        if(oidStockTakenX!=0 && dpStockTaken.getEmployeeId()==dpChekBalancing.getDp_employee_id()){ 
        dpStockManagementX = PstDpStockManagement.fetchExc(oidStockTakenX);
        dpChekBalancing.setDp_qtyResidue(dpStockManagementX.getQtyResidue());// agar mendapatkan residue yg baru
        }
        if(dpChekBalancing.getDp_qtyResidue()>0 && dpStockTaken.getEmployeeId()!=0 && (dpStockTaken.getEmployeeId()==dpChekBalancing.getDp_employee_id())){
        float qtyUsedNew = dpChekBalancing.getDp_qtyUsed() + dpStockTaken.getTakenQty();  
        if(dpChekBalancing.getDp_qtyResidue()>=qtyUsedNew){ 
        dpChekBalancing.setDp_qtyUsed(qtyUsedNew);
        dpChekBalancing.setDp_qtyResidue(dpChekBalancing.getDp_qtyResidue()-qtyUsedNew);
        }else{
        float qtyUsedx = qtyUsedNew - dpChekBalancing.getDp_qty();
        dpChekBalancing.setDp_qtyUsed(qtyUsedx);
        dpChekBalancing.setDp_qtyResidue(dpChekBalancing.getDp_qtyResidue()-qtyUsedx);
        }
        //update qtyUsed dan residue in DPSTOCKMANAGEMENT dan update DP_PAID_DATE dan DP_STOCK_ID
        oidStockTakenX= PstDpStockManagement.updateQtyUsedResidue(dpChekBalancing);  
        PstDpStockTaken.updateStockIdPaidDate(dpStockTaken,true); 
        
        }
        }
        }
        }
        }*/
        /*chekLeaveBalance = false;
        Vector listCekDpBalanceVer1 = SessDpStockManagement.ListLeaveForBalanceDp(chekLeaveBalance,emp_number); 
        Vector dpEligable = new Vector();
        if (listCekDpBalanceVer1 != null && listCekDpBalanceVer1.size() > 0) {
        for (int i = 0; i < listCekDpBalanceVer1.size(); i++) {
        DpChekBalancing dpChekBalancing = (DpChekBalancing) listCekDpBalanceVer1.get(i);
        boolean cekExpired = false;
        float dpWillBeTakenPerDp = 0.0f;
        
        float dpRoundEligible = dpChekBalancing.getEligible();
        float dpFullFilled = 0;
        PstDpStockTaken.updateTakenQtyAndQtyUse(dpChekBalancing, false); 
        }
        }*/
    }//save
             }else{
                empNull=true;
             }
        }



%>
<!-- End of Jsp Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Re-Calculate DP Balance</title>
        <script language="JavaScript">
            function cmdSave(){
            
                document.frm_leave.command.value="<%=Command.SAVE%>";
                document.frm_leave.action="if_dp_not_balance.jsp";
                document.frm_leave.submit();
             }
             <%if(empNull==false){%>  
                          <%}else{%>
                     alert("Employee Number Kosong");
                <%}%>
               function cmdList(){
                document.frm_leave.command.value="<%=Command.GOTO%>"; 
                document.frm_leave.action="dp_list_not_balance.jsp";
                document.frm_leave.target = ""
                document.frm_leave.submit();
            }
        </script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --><!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --><!-- #BeginEditable "headerscript" -->
        <SCRIPT language=JavaScript>
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

        </SCRIPT>
        <!-- #EndEditable -->
    </head>
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"><!-- #BeginEditable "header" -->
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"><!-- #BeginEditable "menumain" -->
                    <%@ include file = "../../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="10" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                    </table></td>
            </tr>
            <%}%>
            <tr>
                <td width="88%" valign="top" align="left"><table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr>
                            <td width="100%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td height="20"><font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> Employee &gt; Leave &gt; Balance DP<!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; ">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr>
                                                                <td style="border:1px solid <%=garisContent%>" valign="top"><table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top"><!-- #BeginEditable "content" -->
                                                                                <form name="frm_leave" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">

                                                                                    <table border="0" width="100%">
                                                                                        <tr>
                                                                                            <td colspan="2"><div align="center"><b><font size="3">Calculate Day Off Minus or Not Balance </font></b></div></td>
                                                                                        </tr>
																						<tr>
																						<td>&nbsp;</td>
																						</tr>

                                                                                    </table>
																					<table align="left" width="100%">
																					<tr>
																						<td colspan="2"><blink><font size="2px" color="#FF0000">Perhatian! Module Ini akan melakukan calculasi ulang semua DP yg diambil</font></blink></td>
																					  </tr>
																					  <tr>
																					  	<td width="9%">&nbsp;</td>
																					  </tr>
																						<tr>
																							<td colspan="2">
																								Keterangan :																							
																							    <ul>
																									<li>
																										Yang di lakukan pertama kali adalah silahkan Excecute semua cuti DP yang ada																									</li>
																								  </ul>																							    <ul>
																									<li>
																										Silahkan masukkan Employee Number Karyawan <blink><font color="#FF0000">Perhatian! di mohonkan untuk menginputkan number karyawan</font></blink>																									</li>
																								  </ul></td>
																						</tr>
																						<tr>
																						<td>
																							<table width="100%">
																								 <tr>
                                                                                            <td width="10%">Employee Number :</td>
                                                                                            <td width="40%"><input name="emp_number" type="text" size="30" maxlength="30"></td>
																							<td width="50%">&nbsp;</td>
                                                                                        </tr>
																							</table>
																						</td>
																						</tr>
																						<tr>
																							<td>
																								<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr>
                                                                                            <td width="7%" nowrap align="left" class="command"><table width="60" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save"></a></td>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="169" nowrap><b><a href="javascript:cmdSave()" class="command">Prosess</a></b></td>
                                                                                                    </tr>
                                                                                          </table>																						  </td>
                                                                                                <td width="100%" nowrap align="left" class="command">
																					     <table width="60" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="24"><a href="javascript:cmdList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save"></a></td>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="169" nowrap><b><a href="javascript:cmdList()" class="command">Go To Menu List Residue Taken Day Off</a></b></td>
                                                                                                    </tr>
                                                                                          </table>																							</td>
																						</tr>
																				  </table>																					</td>
                                                                                        </tr>
																				  </table>
                                                                                </form>
                                                                                <!-- #EndEditable --> </td>
                                                                        </tr>
                                                                    </table></td>
                                                            </tr>
                                                        </table></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table></td>
            </tr>
           <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
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
    <script language="JavaScript">
        //    var oBody = document.body;
         //   var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <SCRIPT>
// Before you reuse this script you may want to have your head examined
// 
// Copyright 1999 InsideDHTML.com, LLC.  

function doBlink() {
  // Blink, Blink, Blink...
  var blink = document.all.tags("BLINK")
  for (var i=0; i < blink.length; i++)
    blink[i].style.visibility = blink[i].style.visibility == "" ? "hidden" : "" 
}

function startBlink() {
  // Make sure it is IE4
  if (document.all)
    setInterval("doBlink()",1000)
}
window.onload = startBlink;
</SCRIPT>
    <!-- #EndEditable --><!-- #EndTemplate -->
</html>

