<%@page import="com.dimata.harisma.entity.overtime.TmpOvertimeReportDaily"%>
<%@page import="com.dimata.harisma.session.leave.SessLeaveApp"%>
<%@page import="com.dimata.harisma.utility.service.presence.PresenceAnalyser"%>
<%@page import="com.dimata.harisma.session.payroll.SessOvertime"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertime"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.session.payroll.I_PayrollCalculator"%>
<%@page import="com.lowagie.text.Document"%>
<%@page import="com.dimata.qdep.db.DBHandler"%>
<%@page import="org.apache.poi.hssf.record.ContinueRecord"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.overtime.Overtime"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%@ page language="java" %>


<%@ page import ="java.util.*"%>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.*" %>

<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>

<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import ="com.dimata.harisma.session.attendance.*"%>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
    
//cek tipe browser, browser detection
    //String userAgent = request.getHeader("User-Agent");
    //boolean isMSIE = (userAgent!=null && userAgent.indexOf("MSIE") !=-1); 

%>
<%!    public String drawList(Vector objectClass) {

        String strSchedule = "<table border=\"0\" cellspacing=\"0\"" + "cellpadding=\"1\" bgcolor=\"#E0EDF0\"><tr>";

        String in = PstSystemProperty.getValueByName("POSTING_IN_TOLERANCE");
        int inTolerance = Integer.parseInt(in);
        String out = PstSystemProperty.getValueByName("POSTING_OUT_TOLERANCE");
        int outTolerance = Integer.parseInt(out);

        for (int i = 0; i < objectClass.size(); i++) {
            ScheduleSymbol scheduleSymbol = (ScheduleSymbol) objectClass.get(i);
            String str_dt_TimeIn = "";
            String str_dt_TimeOut = "";

            ScheduleCategory sc = new ScheduleCategory();
            try {
                sc = PstScheduleCategory.fetchExc(scheduleSymbol.getScheduleCategoryId());
            } catch (Exception e) {
            }

            try {
                Date dt_TimeIn = scheduleSymbol.getTimeIn();
                if (sc.getCategoryType() == PstScheduleCategory.CATEGORY_REGULAR || sc.getCategoryType() == PstScheduleCategory.CATEGORY_SPLIT_SHIFT || sc.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER || sc.getCategoryType() == PstScheduleCategory.CATEGORY_ACCROSS_DAY) {
                    dt_TimeIn.setMinutes(dt_TimeIn.getMinutes() + inTolerance);
                }
                if (dt_TimeIn == null) {
                    dt_TimeIn = new Date();
                }
                str_dt_TimeIn = Formater.formatTimeLocale(dt_TimeIn);
            } catch (Exception e) {
                str_dt_TimeIn = "";
            }

            try {
                Date dt_TimeOut = scheduleSymbol.getTimeOut();
                if (sc.getCategoryType() == PstScheduleCategory.CATEGORY_REGULAR || sc.getCategoryType() == PstScheduleCategory.CATEGORY_SPLIT_SHIFT || sc.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER || sc.getCategoryType() == PstScheduleCategory.CATEGORY_ACCROSS_DAY) {
                    dt_TimeOut.setMinutes(dt_TimeOut.getMinutes() - outTolerance);
                }
                if (dt_TimeOut == null) {
                    dt_TimeOut = new Date();
                }
                str_dt_TimeOut = Formater.formatTimeLocale(dt_TimeOut);
            } catch (Exception e) {
                str_dt_TimeOut = "";
            }

            String pauseTime = "";
            try {
                ;
                if (scheduleSymbol.getBreakOut() != null && scheduleSymbol.getBreakIn() != null
                        && !(scheduleSymbol.getBreakOut().equals(scheduleSymbol.getBreakIn()))) {
                    pauseTime = "(" + Formater.formatTimeLocale(scheduleSymbol.getBreakOut()) + "-" + Formater.formatTimeLocale(scheduleSymbol.getBreakIn()) + ")";
                }

            } catch (Exception e) {
                pauseTime = "";
            }


            if (str_dt_TimeIn.compareTo(str_dt_TimeOut) != 0) {
                strSchedule += "<td>" + String.valueOf(scheduleSymbol.getSymbol()) + "</td><td>=</td><td>" + str_dt_TimeIn + "-" + str_dt_TimeOut + " " + pauseTime + "</td><td width=\"8\"></td>";
            } else {
                strSchedule += "<td>" + String.valueOf(scheduleSymbol.getSymbol()) + "</td><td>=</td><td>" + String.valueOf(scheduleSymbol.getSchedule()) + "</td><td width=\"8\"></td>";
            }

            if ((i % 5) == 4) {
                strSchedule += "</tr>";
            }
        }
        strSchedule += "</tr></table>";
        return strSchedule;
    }

%>
<%!    int DATA_NULL = 0;
    int DATA_PRINT = 1;

    /**
     * get working duration of actual In and Out presence
     * @param dtActualIn => date actual IN 
     * @param dtActualOut => date actual OUT  
     * @return String of working duration in Hour and Minutes format 
     */
    public String getWorkingDuration(Date dtActualIn, Date dtActualOut, long breakLong) {
        String result = "";
        Date x = new Date();
        Date dt = new Date(breakLong);
        x = dt;
        if (dtActualIn != null && dtActualOut != null) { 
            long iDurTimeIn = dtActualIn.getTime() / 1000; 
            long iDurTimeOut = (dtActualOut.getTime()-breakLong) / 1000;
            long iDuration = 0;
            if (iDurTimeIn != iDurTimeOut) {
                iDuration = (iDurTimeIn == 0 || iDurTimeOut == 0) ? 0 : iDurTimeOut - iDurTimeIn;
            }
            long iDurationHour = (iDuration - (iDuration % 3600)) / 3600;
            long iDurationMin = iDuration % 3600 / 60;

            if (!(iDurationHour == 0 && iDurationMin == 0)) {
                String strDurationHour = (iDurationHour != 0) ? iDurationHour + "h, " : "";
                String strDurationMin = (iDurationMin != 0) ? iDurationMin + "m" : "";
                result = strDurationHour + strDurationMin;
            }
        }
        return result;
    }

    /**
     * get difference of schedule and actual In presence
     * @param dtSchedule => date schedule IN 
     * @param dtActual => date actual IN  
     * @return String of difference in Hour and Minutes format 
     */
    public String getDiffIn(Date dtParam, Date dtActual) {
        String result = "";
        if (dtParam == null || dtActual == null) {
            return result;
        }

        // utk mengecek jika waktu di schedule adalah jam 24:00 maka dianggap sebagai jam 00:00 keesokan harinya
        Date dtSchedule = new Date(dtParam.getYear(), dtParam.getMonth(), dtParam.getDate(), dtParam.getHours(), dtParam.getMinutes());
        if (dtSchedule.getHours() == 0 && dtSchedule.getMinutes() == 0) {
            dtSchedule = new Date(dtParam.getYear(), dtParam.getMonth(), dtParam.getDate() + 1, 0, 0);

        }

        if (dtSchedule != null && dtActual != null) {
            dtSchedule.setSeconds(0);
            dtActual.setSeconds(0); 
            long iDuration = dtSchedule.getTime() / 60000 - dtActual.getTime() / 60000;
            long iDurationHour = (iDuration - (iDuration % 60)) / 60;
            long iDurationMin = iDuration % 60;
            if (!(iDurationHour == 0 && iDurationMin == 0)) {
                String strDurationHour = iDurationHour + "h, ";
                String strDurationMin = iDurationMin + "m";
                result = strDurationHour + strDurationMin;
            }
        }
        return result;
    }

    /**
     * get difference of schedule and actual Out presence
     * @param dtSchedule => date schedule OUT 
     * @param dtActual => date actual OUT  
     * @return String of difference Out Hour and Minutes format 
     */
    public String getDiffOut(Date dtParam, Date dtActual) {
        String result = "";
        if (dtParam == null || dtActual == null) {
            return result;
        }
      //int schld1stCategory = PstEmpSchedule.getScheduleCategory(INT_FIRST_SCHEDULE, employeeId, presenceDate);
        //mencari schedule yg ada cross day
        Date dtSchedule = new Date(dtParam.getYear(), dtParam.getMonth(), dtParam.getDate(), dtParam.getHours(), dtParam.getMinutes());
        if (dtSchedule != null && dtActual != null) {
            dtSchedule.setSeconds(0);
            dtActual.setSeconds(0);
            long iDuration = dtActual.getTime() / 60000 - dtSchedule.getTime() / 60000;
            long iDurationHour = (iDuration - (iDuration % 60)) / 60;
            long iDurationMin = iDuration % 60;
            if (!(iDurationHour == 0 && iDurationMin == 0)) {
                String strDurationHour = iDurationHour + "h, ";
                String strDurationMin = iDurationMin + "m";
                result = strDurationHour + strDurationMin;
            }
                    
          
        }
        return result;
    }

    /**
     * create list object
     * consist of : 
     *  first index  ==> status object (will displayed or not)
     *  second index ==> object string will displayed
     *  third index  ==> object vector of string used in report on PDF format.
     */
    //public Vector drawList(Vector listAttendanceRecordDailly, Date dtSch, boolean privUpdatePresence) {
    public Vector drawList(JspWriter outObj,int iCommand,Vector listAttendanceRecordDailly, Vector listPresencePersonalInOut,Vector listOvertimeDaily,HolidaysTable holidaysTable,  Date selectDateFrom, Date selectDateTo,String fullName,String empNum,boolean privUpdatePresence,int start,int showOvertime,boolean isHRDLogin,I_Atendance attdConfig) {
        Vector result = new Vector(1, 1); 
       
        if (listAttendanceRecordDailly != null && listAttendanceRecordDailly.size() > 0) {
            long Al_oid = 0;
            long DP_oid = 0;
            long LL_oid = 0;
           try {
                Al_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_AL"));
                DP_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_DP"));
                LL_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_LL"));
            } catch (Exception exc) {
            }
            Vector statusVal = new Vector();
            Vector statusTxt = new Vector();
            for (int s = 0; s < PstEmpSchedule.strPresenceStatus.length; s++) {
                statusVal.add("" + s);
                statusTxt.add("" + PstEmpSchedule.strPresenceStatus[s]);
            }


            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            //update by satrya 2012-09-17
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            //update by satrya 2012-09-17
            //ctrlist.setCellStyles("listgensellstyles");
            //ctrlist.setRowSelectedStyles("rowselectedstyles");
             ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
            
            ctrlist.setHeaderStyle("listheaderJs");
            //ctrlist.setHeaderStyle("tableheader");
      if(attdConfig!=null && attdConfig.getConfigurasiReportScheduleDaily()==I_Atendance.CONFIGURASI_I_REPORT_DAILY_SCHEDULE_AND_CEK_BOX_ADA_DIBELAKANG){
            ctrlist.addHeader("No", "1%", "2", "0");
            //tgl 2012-7-18
            ctrlist.addHeader("Date", "1%", "2", "0");
            ctrlist.addHeader("Payrol"+"<br>"+"Numb", "1%", "2", "0");
            ctrlist.addHeader("Employee", "10%", "2", "0");//6%
             
           
            ctrlist.addHeader("Actual", "2%", "0", "4");
            ctrlist.addHeader("Time"+"<br>"+"In", "2%", "0", "0");
           
             ctrlist.addHeader("Break"+"<br>"+"Out", "2%", "0", "0");
             ctrlist.addHeader("Break"+"<br>"+"In", "2%", "0", "0");
            ctrlist.addHeader("Time"+"<br>"+"Out", "2%", "0", "0");
            ctrlist.addHeader("Difference", "3%", "0", "2");
            //sama
            ctrlist.addHeader("In", "2%", "0", "0");
            ctrlist.addHeader("Out", "2%", "0", "0");
            //harusnya
            ctrlist.addHeader("Duration", "3%", "2", "0");
            
            //update by satrya 2013-08-15
            ctrlist.addHeader("Schedule", "50%", "0", "1");
            ctrlist.addHeader("Symbol", "50%", "0", "0");
            
            ctrlist.addHeader("Leave", "2%", "2", "0");
            //update by satrya 2012-09-13
            //update by satrya 2013-07-26
           if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
            ctrlist.addHeader("Insentif", "2%", "2", "0");//insentif
           } 
          if(showOvertime==0){ 
            ctrlist.addHeader("OT."+"<br>"+"Frm", "2%", "2", "0");//overtime form
            ctrlist.addHeader("Allwn", "2%", "2", "0");//Allowance
            ctrlist.addHeader("Paid"+"<br>"+"/Dp", "2%", "2", "0");//PAID
            ctrlist.addHeader("Net"+"<br>"+"OT", "2%", "2", "0");//NET Overtime
            ctrlist.addHeader("OT."+"<br>"+"Idx", "2%", "2", "0");//Overtime INdex
          }  
             ctrlist.addHeader("Status", "1%", "2", "0");
             ctrlist.addHeader("Reason", "2%", "2", "0");
             ctrlist.addHeader("Note", "1%", "2", "0");
             
          //update by satrya 2013-08-15  
          if (privUpdatePresence) {
                ctrlist.addHeader("Select to  <br> Update/Analyze <br><a href=\"Javascript:SetAllCheckBoxes('frpresence','userSelect', true)\">All</a> | <a href=\"Javascript:SetAllCheckBoxes('frpresence','userSelect', false)\">Deselect All</a> ", "1%", "2", "0");
          }
     }else{
            ctrlist.addHeader("No", "1%", "2", "0");
            //tgl 2012-7-18
            ctrlist.addHeader("Date", "1%", "2", "0");
            ctrlist.addHeader("Payrol"+"<br>"+"Numb", "1%", "2", "0");
            ctrlist.addHeader("Employee", "10%", "2", "0");//6%
             if (privUpdatePresence) {
                ctrlist.addHeader("Select to  <br> Update/Analyze <br><a href=\"Javascript:SetAllCheckBoxes('frpresence','userSelect', true)\">All</a> | <a href=\"Javascript:SetAllCheckBoxes('frpresence','userSelect', false)\">Deselect All</a> ", "1%", "2", "0");
            }
            ctrlist.addHeader("Schedule", "50%", "0", "1");
            ctrlist.addHeader("Symbol", "50%", "0", "0");
           // ctrlist.addHeader("Time In", "2%", "0", "0");
            //ctrlist.addHeader("Time Out", "2%", "0", "0");
            ctrlist.addHeader("Actual", "2%", "0", "4");
            ctrlist.addHeader("Time"+"<br>"+"In", "2%", "0", "0");
            ///duration
            ///timeout
            ///diference
             ctrlist.addHeader("Break"+"<br>"+"Out", "2%", "0", "0");
             ctrlist.addHeader("Break"+"<br>"+"In", "2%", "0", "0");
            ctrlist.addHeader("Time"+"<br>"+"Out", "2%", "0", "0");
            ctrlist.addHeader("Difference", "3%", "0", "2");
            //sama
            ctrlist.addHeader("In", "2%", "0", "0");
            ctrlist.addHeader("Out", "2%", "0", "0");
            //harusnya
            ctrlist.addHeader("Duration", "3%", "2", "0");
            ctrlist.addHeader("Leave", "2%", "2", "0");
            //update by satrya 2012-09-13
            //update by satrya 2013-07-26
           if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
            ctrlist.addHeader("Insentif", "2%", "2", "0");//insentif
           } 
          if(showOvertime==0){ 
            ctrlist.addHeader("OT."+"<br>"+"Frm", "2%", "2", "0");//overtime form
            ctrlist.addHeader("Allwn", "2%", "2", "0");//Allowance
            ctrlist.addHeader("Paid"+"<br>"+"/Dp", "2%", "2", "0");//PAID
            ctrlist.addHeader("Net"+"<br>"+"OT", "2%", "2", "0");//NET Overtime
            ctrlist.addHeader("OT."+"<br>"+"Idx", "2%", "2", "0");//Overtime INdex
          }  
             ctrlist.addHeader("Status", "1%", "2", "0");
            ctrlist.addHeader("Reason", "2%", "2", "0");
            ctrlist.addHeader("Note", "1%", "2", "0");
                
     }    
            
           
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            
            int index = -1;
            Vector list = new Vector(1, 1);

            // vector of data will used in pdf report
            Vector vectDataToPdf = new Vector(1, 1);
            if(listAttendanceRecordDailly !=null && listAttendanceRecordDailly.size() > 0){
                //update by satrya 2012-10-15
                 ctrlist.drawListHeaderWithJsVer2(outObj);//header
                
                try {
                    //untuk special Leave <satrya 2012-08-01>
                    Vector listScheduleSymbol = new Vector(1, 1);
                    try{
                        
                         listScheduleSymbol.add(new Long(PstSystemProperty.getValueByName("OID_SPECIAL")));
                     }catch(Exception E){
                         
                         //System.out.println("EXCEPTION SYS PROP OID_SPECIAL : "+E.toString());
                           outObj.println("<blink>SYS PROP OID_SPECIAL NOT TO BE SET</blink>" );
                     }

                     try{
                         listScheduleSymbol.add(new Long(PstSystemProperty.getValueByName("OID_UNPAID")));
                       }catch(Exception E){
                            //System.out.println("EXCEPTION SYS PROP OID_UNPAID : "+E.toString());
                             outObj.println("<blink>SYS PROP OID_UNPAID NOT TO BE SET</blink>" );
                     }
                                       //update by satrya 2012-08-05
                /**
                    untuk melakukan settingan jika statusnya full day atau jam-jam'an
                **/
                        int iLeaveMinuteEnable = 0;//hanya cuti full day jika fullDayLeave = 0
                        try{
                            iLeaveMinuteEnable = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_MINUTE_ENABLE"));
                        }catch(Exception ex){
                            //System.out.println("Execption LEAVE_MINUTE_ENABLE: " + ex);
                            outObj.println("<blink>LEAVE_MINUTE_ENABLE NOT TO BE SET</blink>" );
                        }
              
                        int iPropInsentifLevel = 0;//hanya cuti full day jika fullDayLeave = 0
                        try{
                          if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                            iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
                          }
                        }catch(Exception ex){
                           
                            //System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
                            outObj.println("<blink>PAYROLL_INSENTIF_MAX_LEVEL NOT TO BE SET</blink>" );
                        }
            
                    ///cek untuk mendqapatkan insentif
                    I_PayrollCalculator payrollCalculatorConfig = null;
                    try{
                        payrollCalculatorConfig = (I_PayrollCalculator)(Class.forName(PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME")).newInstance());
                    }catch(Exception e) {
                       outObj.println("Exception PAYROLL_CALC_CLASS_NAME " + e.getMessage());
                    }
                         Vector reason_value = new Vector(1, 1);
                Vector reason_key = new Vector(1, 1);
                Vector listReason = new Vector(1, 1);
                Vector reason_tooltip = new Vector(1, 1);
                //Vector vReason=new Vector(1, 1);
                //String selectedReason = String.valueOf(presenceReportDaily.getSelectedDate());
                listReason = PstReason.list(0, 0, "", "REASON");
                for (int r = 0; r < listReason.size(); r++) {
                    Reason reason = (Reason) listReason.get(r);
                    reason_tooltip.add(reason.getReason()); 
                     reason_key.add(reason.getKodeReason());
                    reason_value.add(String.valueOf(reason.getNo()));
                }  
                  Hashtable scheduleSymbolIdMap = PstScheduleSymbol.getScheduleSymbolIdMap(listScheduleSymbol);
                   Hashtable breakTimeDuration = PstScheduleSymbol.getBreakTimeDuration();
            for (int i = 0; i < listAttendanceRecordDailly.size(); i++) { 
                PresenceReportDaily presenceReportDaily = (PresenceReportDaily) listAttendanceRecordDailly.get(i);
                //update by satrya 2012-07-26
                long dtSch = new Date().getTime();
                String cmdAddAttd = ""; 
                long lDatePresence = presenceReportDaily.getSelectedDate().getTime();
                String strEmpFullName = presenceReportDaily.getEmpFullName();
                String strPayrolNumber = presenceReportDaily.getEmpNum();
                long religion_id = presenceReportDaily.getReligion_id();
                //update by satrya 2012-08-01
                long lEmpId = presenceReportDaily.getEmpId();
                Date dateLeave = new Date(lDatePresence);
                 //String sDateLeave = Formater.formatTimeLocale(dateLeave, "yyyy-MM-dd");
                
                 
              
                //String strEmpNum = presenceReportDaily.getEmpNum();
               
                //start = start + 1;// penambahan no
                start =  start+ 1; 
                SimpleDateFormat formatter = new SimpleDateFormat("d/MM/yyyy");
                SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                String dayString = formatterDay.format(presenceReportDaily.getSelectedDate());
                String dateString = formatter.format(presenceReportDaily.getSelectedDate());
                String dateStringColor = (dayString.equalsIgnoreCase("Sat")) ? "<font color=\"darkblue\">" + dateString + "</font>" : dateString;
                String dayStringColor = (dayString.equalsIgnoreCase("Sat")) ? "<font color=\"darkblue\">" + dayString + "</font>" : dayString;
                dateStringColor = (dayString.equalsIgnoreCase("Sun")) ? "<font color=\"red\">" + dateStringColor + "</font>" : dateStringColor;
                dayStringColor = (dayString.equalsIgnoreCase("Sun")) ? "<font color=\"red\">" + dayStringColor + "</font>" : dayStringColor;
                //update by satrya 2012-08-06

                int iPositionLevel = PstPosition.iGetPositionLevel(presenceReportDaily.getEmpId());
                
                int intScheduleCategory1st = presenceReportDaily.getSchldCategory1st();
                String strSchldSymbol1 = presenceReportDaily.getScheduleSymbol1();
                String strSchldSymbol2 = presenceReportDaily.getScheduleSymbol2();
                Date dtSchldIn1st = (Date) presenceReportDaily.getScheduleIn1st();
                Date dtSchldOut1st = (Date) presenceReportDaily.getScheduleOut1st();
                Date dtActualIn1st = (Date) presenceReportDaily.getActualIn1st();
                Date dtActualOut1st = (Date) presenceReportDaily.getActualOut1st();
                //update by satrya 2013-05-28
                String strSchldIn1st="";
                String strSchldOut1st="";
                
                 /**
                    update by satrya
                    untuk menampilkan AL,LL, DP,etc
               **/
                 long oidLeave  = 0;
                 String sSymbolLeave = ""; 
               Vector listLeaveAplication =  PstLeaveApplication.listOid(lEmpId, dtSchldIn1st,dtSchldOut1st);
               if(listLeaveAplication !=null && listLeaveAplication.size()>0){
                   //LeaveOidSym obj = new LeaveOidSym();
                   try{
                   for (int j = 0; j < listLeaveAplication.size(); j++) {
                    LeaveOidSym leaveOidSym = (LeaveOidSym) listLeaveAplication.get(j);
                    oidLeave = leaveOidSym.getLeaveOid();
                    String sSymbolLeaveX= (String.valueOf(leaveOidSym.getLeaveSymbol()));
                    sSymbolLeave = sSymbolLeave  + sSymbolLeaveX + ",";
                }
                    if(sSymbolLeave!=null && sSymbolLeave.length()>0){
                         sSymbolLeave= sSymbolLeave.substring(0,sSymbolLeave.length()-1); 
                        }
                 
                   }catch(Exception ex){System.out.println("Exception list Leave Application"+ex);}
                
               }
                int leaveStatus =-1;
                try{
                    leaveStatus = PstLeaveApplication.getLeaveFormStatus(lEmpId, dtSchldIn1st,dtSchldOut1st);
                  }catch(Exception ex){
                      System.out.println("Leave leave Status"+ex);
                  }
                if(dtSchldIn1st!=null){
                    strSchldIn1st = Formater.formatDate(dtSchldIn1st, "HH:mm");
                }
                if(dtSchldOut1st!=null){
                    strSchldOut1st = Formater.formatDate(dtSchldOut1st, "HH:mm");
                }
                String strSchINOut=strSchldIn1st+"-"+strSchldOut1st;
                //update by satrya 2012-09-26
                 Date dtSchldBO1st = (Date) presenceReportDaily.getScheduleBO1st();
                 String sDtSchldBO1st = Formater.formatDate(dtSchldBO1st, "HH:mm");
                 
                Date dtSchldBO2nd = (Date) presenceReportDaily.getScheduleBO2nd();
                 String sDtSchldBO2nd = Formater.formatDate(dtSchldBO2nd, "HH:mm");
                 
                 Date dtSchldBI1st = (Date) presenceReportDaily.getScheduleBI1st();
                 String sDtSchldBI1st = Formater.formatDate(dtSchldBI1st, "HH:mm");
                   
                  Date dtSchldBI2nd = (Date) presenceReportDaily.getScheduleBI2nd();
                  String sDtSchldBI2nd = Formater.formatDate(dtSchldBI2nd, "HH:mm");
                   
                  String strScheduleDesc1st = (String) presenceReportDaily.getScheduleDesc1st();
                  String strScheduleDesc2nd = (String) presenceReportDaily.getScheduleDesc2nd();
                //int presenceStatus = (int) presenceReportDaily.getPresenceStatus();
                                       //update by satrya 2012-07-23
                 Hashtable reasonMap = PstReason.getReason(0, 500, "", PstReason.fieldNames[PstReason.FLD_REASON]); 

               String strNote = presenceReportDaily.getNote1nd().equals("") ? "-" : presenceReportDaily.getNote1nd();
               String strStatus = PstEmpSchedule.strPresenceStatus[presenceReportDaily.getStatus1()] !=null ? PstEmpSchedule.strPresenceStatus[presenceReportDaily.getStatus1()] : "-";
               String strSymStatus = PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] !=null ? PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] : "-";
               //update by satrya 2012-09-27
                String  pSelectedDate = Formater.formatDate( presenceReportDaily.getSelectedDate(), "yyyy-MM-dd");
               // String sPresenceDateTime = Formater.formatDate( presenceReportDaily.getPresenceDateTime(), "yyyy-MM-dd");
                //untuk yg 2nd
                int intScheduleCategory2nd = presenceReportDaily.getSchldCategory2nd();
                Date dtSchldIn2nd = (Date) presenceReportDaily.getScheduleIn2nd();
                Date dtSchldOut2nd = (Date) presenceReportDaily.getScheduleOut2nd();
                Date dtActualIn2nd = (Date) presenceReportDaily.getActualIn2nd();
                Date dtActualOut2nd = (Date) presenceReportDaily.getActualOut2nd();
                ///update by satrya 2012-07-23
                //hide by satrya 2013-01-23 di karenakan belum di temukan kenapa status2nd -1
               //String strNote2nd = presenceReportDaily.getNote2nd().equals("") ? "-" : presenceReportDaily.getNote2nd();
               // String strStatus2nd =PstEmpSchedule.strPresenceStatus[presenceReportDaily.getStatus2()] !=null ? PstEmpSchedule.strPresenceStatus[presenceReportDaily.getStatus2()] :"-";
                  String strSymStatus2nd = PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] !=null ? PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] : "-";
                Vector rowx = new Vector();
                Vector rowxPdf = new Vector();

                // ---> SPLIT SHIFT / EOD SCHEDULE					
                //if(intScheduleCategory==PstScheduleCategory.CATEGORY_SPLIT_SHIFT)
                //update by satrya 2012-08-19
                String inputName = "" + presenceReportDaily.getEmpScheduleId() + "_d_" + presenceReportDaily.getSelectedDate().getDate() + "_d_" + presenceReportDaily.getSelectedDate().getTime()+ "_d_" + presenceReportDaily.getEmpId()+ "_d_"+ presenceReportDaily.getEmpNum();
                //String inputName = "" + presenceReportDaily.getEmpScheduleId() + "_d_" + presenceReportDaily.getSelectedDate().getDate(); 
                String inputName2nd = "" + presenceReportDaily.getEmpScheduleId() + "_d2nd_" + presenceReportDaily.getSelectedDate().getDate();
               
                //end
                String payCompCode = PayComponent.COMPONENT_INS; 
                String bOut =""; 
                String bIn = "";
                String dBout = "";
                String dBin = ""; 
                //update by satrya 2012-09-13
                //menginisialisasikan variable untuk overtime
                String insentif ="-";
                int oVForm =-1;
                int allwance =-1;
                int paid =-1 ;
                long ovId=0;
                //double   NetOv =0.0;
                String   NetOv ="-";
                //double oVerIdx= 0.0;
                String oVerIdx= "-";
                Presence preBOut = null;
                long preBreakOut = 0;
                long preBreakIn = 0;
                long breakDuration =0L;
                //update by satrya 2012-10-15
                /*long diffBo = 0L;
                long diffBi = 0L;*/
                // update by satrya 2012-10-09
                //menginisialisasikan variable untuk Holiday
                String daysHolidayName = ""; 
                String cmdDaysHoliday="";
                 Presence presenceBreak = new Presence(); 
               // HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(selectedDateFrom, selectedDateTo); 
                if(holidaysTable.isHoliday(religion_id !=0 ? religion_id : 0, presenceReportDaily.getSelectedDate())){
                   daysHolidayName = holidaysTable.getDescHoliday(religion_id,presenceReportDaily.getSelectedDate());
                }
                 if(daysHolidayName !=null && daysHolidayName.length()>0){
                       cmdDaysHoliday = "<p class=\"masterTooltip\"><abbr title=\""+daysHolidayName+"\"><font color=\"#FF0000\">"+"Libur<br>"+dayStringColor+",<br>"+dateStringColor+"</font></p>"; 
                 }else{
                        cmdDaysHoliday = dayStringColor+",<br>"+dateStringColor+"&nbsp;";
                 }
                //update by satrya 2012-10-10 
                 if(listPresencePersonalInOut!=null && listPresencePersonalInOut.size() > 0 ){
                         Date dtSchDateTime = null; 
                         Date dtpresenceDateTime = null;
                         Date dtSchEmpScheduleBIn = null;
                         Date dtSchEmpScheduleBOut = null;
                         long preBreakOutX=0;
                         long preBreakInX=0;
                              Date dtBreakOut=null; 
                          Date dtBreakIn=null;
                          boolean ispreBreakOutsdhdiambil = false; 
                     for(int bIdx = 0;bIdx < listPresencePersonalInOut.size();bIdx++){
                         presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                         //update by satrya 2012-10-17
                         if(presenceReportDaily.getScheduleBO1st()!=null){
                             dtSchEmpScheduleBOut = (Date) presenceReportDaily.getScheduleBO1st().clone();
                             dtSchEmpScheduleBOut.setHours(dtSchEmpScheduleBOut.getHours());
                             dtSchEmpScheduleBOut.setMinutes(dtSchEmpScheduleBOut.getMinutes());
                            dtSchEmpScheduleBOut.setSeconds(0);
                         }
                         if(presenceReportDaily.getScheduleBI1st()!=null){
                             dtSchEmpScheduleBIn = (Date) presenceReportDaily.getScheduleBI1st().clone();
                             dtSchEmpScheduleBIn.setHours(dtSchEmpScheduleBIn.getHours());
                             dtSchEmpScheduleBIn.setMinutes(dtSchEmpScheduleBIn.getMinutes());
                            dtSchEmpScheduleBIn.setSeconds(0);
                         }
                         if(presenceBreak.getScheduleDatetime()!=null){
                            dtSchDateTime = (Date)presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0);                            
                         }
                         if(presenceBreak.getPresenceDatetime() !=null){ 
                                //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);       
                         }
                         if(presenceBreak.getEmployeeId()==presenceReportDaily.getEmpId() 
                                  && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),presenceReportDaily.getSelectedDate())==0 )
                                  && presenceBreak.getScheduleDatetime()== null ){ 
                              if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+"<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                  dBout = bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                              else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ "<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                   dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                             
                          }
                         else if(presenceBreak.getScheduleDatetime() !=null
                                 && presenceBreak.getEmployeeId()==presenceReportDaily.getEmpId()
                                 &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),presenceReportDaily.getSelectedDate())==0 )){
                             //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                             if(presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                  //update by satrya 2012-09-27
                                 //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                 //update by satrya 2013-07-28
                                      
                                  //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                  //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                   preBreakOutX  = dtpresenceDateTime==null?0:dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                  dtBreakOut = dtpresenceDateTime; 
                                  if(dtSchEmpScheduleBIn!=null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()){
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();
                                  }
                                  else if((presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){ ///jika karyawan mendahului istirahat
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
                                    
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakOut = presenceBreak.getPresenceDatetime().getTime();//jika schedulenya 00:00
                                  }
                                  else{
                                       preBreakOut = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PO
                                  }
                                 
                                  ispreBreakOutsdhdiambil = false; 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){
                                  //istirahat terlamabat 
                                   preBreakInX = presenceBreak.getPresenceDatetime()==null?0:presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                   dtBreakIn = presenceBreak.getPresenceDatetime();
                                 if(preBreakOut !=0L){   
                                  //update by satrya 2012-09-27
                                    //if(presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){
                                     //update by satrya 2013-07-28\
                                    //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                  if(dtSchEmpScheduleBIn!=null && dtBreakOut!=null && dtBreakIn!=null &&
                                          dtBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()){
                                      //karena sudah pasti melewatijam istirahatnya
                                     long  tmpBreakDuration = ((Long)breakTimeDuration.get(""+presenceReportDaily.getScheduleId1())).longValue();
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime() + tmpBreakDuration;
                                  }   
                                  else if(presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){ ///jika karyawan melewati jam istirahat
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakIn = presenceBreak.getPresenceDatetime().getTime(); //jika schedulenya 00:00 
                                  }
                                  else{
                                      preBreakIn = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PI
                                  }
                                  
                                   breakDuration = breakDuration + (preBreakIn -  preBreakOut);
                                
                                 
                                 ispreBreakOutsdhdiambil = true;
                                   preBreakOut =0L;
                                   
                                    //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                   // preBOut=null;
                                  }
                                 // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());
                                 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+"<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                   dBout = dBout+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                              } else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ "<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                  dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");  
                                  listPresencePersonalInOut.remove(bIdx);                              
                                   bIdx=bIdx-1;
                                 
                              }
                             
                             if(ispreBreakOutsdhdiambil){
                                   //cek da cuti
                                Vector vLisOverlapCuti  = SessLeaveApp.checkOverLapsLeaveTaken(lEmpId, dtBreakOut,dtBreakIn);
                                if(vLisOverlapCuti!=null && vLisOverlapCuti.size()>0){
                                      for(int idxcuti=0; idxcuti< vLisOverlapCuti.size();idxcuti++){
                                          LeaveCheckTakenDateFinish leaveCheckTaken = (LeaveCheckTakenDateFinish)vLisOverlapCuti.get(idxcuti);
                                          if(leaveCheckTaken.getTakenDate()!=null && dtBreakOut!=null
                                                  && preBreakOutX < leaveCheckTaken.getTakenDate().getTime() 
                                                  && preBreakOutX < dtSchEmpScheduleBOut.getTime()){
                                                bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                                          }else{
                                              bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br>";
                                          }

                                          if(dtSchEmpScheduleBIn!=null && dtBreakIn!=null 
                                                  && preBreakInX > leaveCheckTaken.getFinishDate().getTime() 
                                                  && preBreakInX > dtSchEmpScheduleBIn.getTime()){

                                               bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                                               
                                          }else{
                                              bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br>";
                                          }
                                        
                                          vLisOverlapCuti.remove(idxcuti);                              
                                            idxcuti=idxcuti-1;
                                              break;
                                      }
                                }//end cek cuti
                                else{
                                    if(preBreakOutX < dtSchEmpScheduleBOut.getTime() || preBreakOutX > dtSchEmpScheduleBIn.getTime()){
                                          bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                                    }else{
                                        bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br>";
                                    }
                                    if(preBreakInX > dtSchEmpScheduleBIn.getTime()){
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                                    }else{
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br>";
                                    }
                                }
                                 //update by satrya 2013-06-17
                                   preBreakOutX=0L;
                                   preBreakInX=0L;
                             }
                          // }
                              
                         }//end else if
                           
                     }//end for break In
                          if(preBreakOutX==0 || preBreakInX==0){ 
                                 //jika hanya satu saja yg muncul atau ada misalnya hanya break IN saja atau break Out saja
                                 //update by satrya 2013-06-17
                               if(preBreakOutX!=0){
                                 if(preBreakOutX < dtSchEmpScheduleBOut.getTime() || preBreakOutX > dtSchEmpScheduleBIn.getTime()){
                                          bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                                    }else{
                                        bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br>";
                                    }
                              }
                              if(preBreakInX!=0){
                                    if(preBreakInX > dtSchEmpScheduleBIn.getTime()){
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                                    }else{
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br>";
                                    }
                             }
                             }
                         //update by satrya 2012-10-18
                             //jika di loop tersebut tidak cocok maka di kurangi schedulenya
                                if(breakDuration ==0 && presenceReportDaily.getScheduleId1()!=0 && breakTimeDuration.get(""+presenceReportDaily.getScheduleId1()) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                                        try{                          
                                         breakDuration = ((Long)breakTimeDuration.get(""+presenceReportDaily.getScheduleId1())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                                        }catch(Exception ex){
                                            System.out.println("Exception scheduleSymbol"+ex.toString());
                                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                                        }
                                      }
                    }
                   //jika employee tidak ada yang keluar maka akan di potong jam istirahat default
                  else{
                    if(breakDuration ==0 && presenceReportDaily.getScheduleId1()!=0 && breakTimeDuration.get(""+presenceReportDaily.getScheduleId1()) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                        try{                          
                         breakDuration = ((Long)breakTimeDuration.get(""+presenceReportDaily.getScheduleId1())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                        }catch(Exception ex){
                            System.out.println("Exception scheduleSymbol"+ex.toString());
                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                        }
                      } 
                  }
                     //list Overtime
                 // sementara belum di pakai
                 //TmpOvertimeReportDaily  tmtmpOvertimeReportDaily = new TmpOvertimeReportDaily(); 
                if(showOvertime==0){
                      pSelectedDate = Formater.formatDate( presenceReportDaily.getSelectedDate(), "yyyy-MM-dd");
                     if(listOvertimeDaily!=null && listOvertimeDaily.size()> 0){  
                     for(int oVx = 0;oVx < listOvertimeDaily.size();oVx++){
                         OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDaily.get(oVx);
                         
                         String pdateOv = Formater.formatDate(overtimeDetail.getDateFrom(), "yyyy-MM-dd");
                         if(overtimeDetail.getOID() !=0 && overtimeDetail.getEmployeeId()==presenceReportDaily.getEmpId() 
                               && pdateOv.equals(pSelectedDate)){
                             ovId= overtimeDetail.getOID();
                            oVForm = overtimeDetail.getStatus();
                            //I_DocStatus.fieldDocumentStatusShort[overtimeDetail.getStatus()];
                            //update by satrya 2012-10-31
                            if(overtimeDetail.getStatus()==I_DocStatus.DOCUMENT_STATUS_PROCEED){
                                allwance = overtimeDetail.getAllowance(); 
                            //Overtime.allowanceType[overtimeDetail.getAllowance()]; 
                            }
                            paid = overtimeDetail.getPaidBy();
                            //OvertimeDetail.paidByKey[overtimeDetail.getPaidBy()];
                            if(overtimeDetail.getNetDuration() !=0.0){
                                NetOv = Formater.formatNumber(overtimeDetail.getRoundDuration(), "###.##");  
                                //NetOv = Formater.formatNumber(overtimeDetail.getDuration(), "###.##");  
                            }
                            if(overtimeDetail.getTot_Idx() != 0.0){
                                  oVerIdx = Formater.formatNumber(overtimeDetail.getTot_Idx(), "###.##");  ;
                            }
                              listOvertimeDaily.remove(oVx);                              
                              oVx=oVx-1;
                              break;
                         }
                     }
                    }
               }//end cek show overtime
                    String sSymbol =""; 
                        if(strSchldSymbol1 !=null && strSchldSymbol1.length() >0){
                            
                              if(Al_oid != presenceReportDaily.getScheduleId1() && LL_oid != presenceReportDaily.getScheduleId1() && DP_oid != presenceReportDaily.getScheduleId1()){
                                  if(iLeaveMinuteEnable !=1){
                                  sSymbol ="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\"   id=\"inputName\" name=\"" + inputName + "_symbol\" value=\"" + strSchldSymbol1.toUpperCase() + "\"  size=\"2\" "
                                        + "title =\""+strSchINOut+"&nbsp;["+sDtSchldBO1st+"&nbsp;"+sDtSchldBI1st+"]\">"
                                         ;// +"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                        
                                    }else{  
                                      if(sSymbolLeave !=null && sSymbolLeave.length() > 0){
                                          sSymbol ="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\"  id=\"inputName\" name=\"" + inputName + "_symbol\" value=\"" + strSchldSymbol1.toUpperCase() + "\"  size=\"2\" "
                                            + "title =\""+strSchINOut+"&nbsp;["+sDtSchldBO1st+"&nbsp;"+sDtSchldBI1st+"]\">"+" / <B>"+sSymbolLeave+"</B>"
                                                  ;// +"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                      }
                                      else{
                                          sSymbol ="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\" id=\"inputName\" name=\"" + inputName + "_symbol\" value=\"" + strSchldSymbol1.toUpperCase() + "\"  size=\"2\""
                                                    + "title =\""+strSchINOut+"&nbsp;["+sDtSchldBO1st+"&nbsp;"+sDtSchldBI1st+"]\">"
                                               ;//+"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                      }                            
                                    }                            
                            } else{ 
                                  if(iLeaveMinuteEnable !=1){
                                  sSymbol = strSchldSymbol1 ;
                                   
                                    }else
                                    {
                                     sSymbol ="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\"  id=\"inputName\" name=\"" + inputName + "_symbol\" value=\"" + strSchldSymbol1.toUpperCase() + "\"  size=\"3\" "
 + "                                         title =\""+strSchINOut+"&nbsp;["+sDtSchldBO1st+"&nbsp;"+sDtSchldBI1st+"]\">"+" / <B>"+sSymbolLeave+"</B>"
                                            ;//  +"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                    }
                             }
                        }
                    else{
                          //sSymbol ="<input onblur=\"javascript:checkSymbol(this)\" type=\"text\"  name=\"" + inputName + "_symbol\" value=\"" + strSchldSymbol1 + "\"  size=\"3\">";
                          sSymbol="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\"  id=\"inputName\" name=\"" + inputName+ "_symbol\" value=\"" + strSchldSymbol1 !=null || strSchldSymbol1.length() > 0 ? strSchldSymbol1.toUpperCase() : ""+0 + "\"  size=\"3\" "
                                    + "title =\""+"-"+"&nbsp;["+"00:00"+"&nbsp;"+"00:00"+"]\">";
                        }
                  //mencari sSymbol2nd
                        String sSymbol2nd =""; 
                        if(strSchldSymbol2 !=null && strSchldSymbol2.length() >0){
                            
                              if(Al_oid != presenceReportDaily.getScheduleId2() && LL_oid != presenceReportDaily.getScheduleId2() && DP_oid != presenceReportDaily.getScheduleId2()){
                                  if(iLeaveMinuteEnable !=1){
                                  sSymbol2nd ="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\"   id=\"inputName\" name=\"" + inputName2nd + "_symbol\" value=\"" + strSchldSymbol2.toUpperCase() + "\"  size=\"2\" "
                                        + "title =\""+strScheduleDesc2nd+"&nbsp;["+sDtSchldBO2nd+"&nbsp;"+sDtSchldBI2nd+"]\">"
                                         ;// +"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                        
                                    }else{ 
                                      if(sSymbolLeave !=null && sSymbolLeave.length() > 0){
                                          sSymbol2nd ="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\"  id=\"inputName\" name=\"" + inputName2nd + "_symbol\" value=\"" + strSchldSymbol2.toUpperCase() + "\"  size=\"2\" "
                                            + "title =\""+strScheduleDesc2nd+"&nbsp;["+sDtSchldBO2nd+"&nbsp;"+sDtSchldBI2nd+"]\">"+" / <B>"+sSymbolLeave+"</B>"
                                                  ;// +"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                      }
                                      else{
                                          sSymbol2nd ="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\" id=\"inputName\" name=\"" + inputName2nd+ "_symbol\" value=\"" + strSchldSymbol2.toUpperCase() + "\"  size=\"2\""
                                                    + "title =\""+strScheduleDesc2nd+"&nbsp;["+sDtSchldBO2nd+"&nbsp;"+sDtSchldBI2nd+"]\">"
                                               ;//+"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                      }                            
                                    }                            
                            } else{ 
                                  if(iLeaveMinuteEnable !=1){
                                  sSymbol2nd = strSchldSymbol2;
                                   
                                    }else
                                    {
                                     sSymbol2nd ="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\"  id=\"inputName\" name=\"" + inputName2nd + "_symbol\" value=\"" + strSchldSymbol2.toUpperCase() + "\"  size=\"3\" "
 + "                                         title =\""+strScheduleDesc2nd+"&nbsp;["+sDtSchldBO2nd+"&nbsp;"+sDtSchldBI2nd+"]\">"+" / <B>"+sSymbolLeave+"</B>"
                                            ;
                                    }
                             }
                        }
                    else{
                          //sSymbol2nd="-";
                             sSymbol2nd="<input class=\"masterTooltip\" onblur=\"javascript:checkSymbol(this)\" type=\"text\"  id=\"inputName\" name=\"" + inputName2nd+ "_symbol\" value=\"" + strSchldSymbol2 !=null || strSchldSymbol2.length() > 0 ? strSchldSymbol2.toUpperCase() : "0" + "\"  size=\"3\" "
                                    + "title =\""+"-"+"&nbsp;["+"00:00"+"&nbsp;"+"00:00"+"]\">";
                        }
                   ///command script
                   String cmdLeaveSript ="";
                   String cmdLeaveSript2nd ="";
                    if(presenceReportDaily.getScheduleId2() !=0){
                       if(presenceReportDaily.getScheduleId2() == Al_oid && presenceReportDaily.getScheduleId2() != 0 || sSymbolLeave.equalsIgnoreCase("AL")){
                            if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                               //"<a href=\"javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "');\"><center>New</center></a>";
                             cmdLeaveSript2nd = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#00000\"> [View]</font></a>";
                            }else{
                             cmdLeaveSript2nd = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                            }
                        }
                        else if(presenceReportDaily.getScheduleId2() == LL_oid && presenceReportDaily.getScheduleId2() != 0  || sSymbolLeave.equalsIgnoreCase("LL")){
                             if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                               //"<a href=\"javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "');\"><center>New</center></a>";
                             cmdLeaveSript2nd = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#00000\"> [View]</font></a>";
                            }else{
                             cmdLeaveSript2nd = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                            }
                        }
                        else if(presenceReportDaily.getScheduleId2() == DP_oid && presenceReportDaily.getScheduleId2() != 0  || sSymbolLeave.equalsIgnoreCase("DP")){
                             if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                               //"<a href=\"javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "');\"><center>New</center></a>";
                             cmdLeaveSript2nd = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#00000\"> [View]</font></a>";
                            }else{
                             cmdLeaveSript2nd = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                            }
                        }
                        else if(scheduleSymbolIdMap.containsKey(""+presenceReportDaily.getScheduleId2()) || scheduleSymbolIdMap.containsValue(sSymbolLeave)){
                             if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                               //"<a href=\"javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "');\"><center>New</center></a>";
                             cmdLeaveSript2nd = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#00000\"> [View]</font></a>";
                            }else{
                             cmdLeaveSript2nd  = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                            }
                        }
                         //jika statusnya DRAFF Dan To Be Approval
                        else if((leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT) || (leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE)){
                            cmdLeaveSript2nd  = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                       }
                         //end
                         else{
                             cmdLeaveSript2nd = "<a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                    }
                    }else{
                        cmdLeaveSript2nd = "<a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                    }
                   
                   
                   //untuk schedule 1
                    if(presenceReportDaily.getScheduleId1() !=0){
                        //update by satrya 2013-04-14
                       if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){//presenceReportDaily.getScheduleId1() == Al_oid && presenceReportDaily.getScheduleId1() != 0 || (sSymbolLeave!=null && sSymbolLeave.length()>0)){
                           //if(presenceReportDaily.getScheduleId1() == Al_oid && presenceReportDaily.getScheduleId1() != 0 || sSymbolLeave.equalsIgnoreCase("AL")){
                            if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                               //"<a href=\"javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "');\"><center>New</center></a>";
                             cmdLeaveSript = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#00000\"> [View]</font></a>";
                             if(listLeaveAplication!=null && listLeaveAplication.size()>0){
                              cmdLeaveSript = cmdLeaveSript + " <br><br> <a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                             }
                            }else{
                             cmdLeaveSript = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                             if(listLeaveAplication!=null && listLeaveAplication.size()>0){
                              cmdLeaveSript = cmdLeaveSript + " <br><br><a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                             }
                            }
                        }
                       /* hidden by satrya 2013-04-09 
                       else if(presenceReportDaily.getScheduleId1() == LL_oid && presenceReportDaily.getScheduleId1() != 0  || sSymbolLeave.equalsIgnoreCase("LL")){
                             if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                               //"<a href=\"javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "');\"><center>New</center></a>";
                             cmdLeaveSript = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#00000\"> [View]</font></a>";
                            }else{
                             cmdLeaveSript = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                            }
                        }
                        else if(presenceReportDaily.getScheduleId1() == DP_oid && presenceReportDaily.getScheduleId1() != 0  || sSymbolLeave.equalsIgnoreCase("DP")){
                             if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                               //"<a href=\"javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "');\"><center>New</center></a>";
                             cmdLeaveSript = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#00000\"> [View]</font></a>";
                            }else{
                             cmdLeaveSript = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                            }
                        }
                        else if(scheduleSymbolIdMap.containsKey(""+presenceReportDaily.getScheduleId1()) || scheduleSymbolIdMap.containsValue(sSymbolLeave)){
                             if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                               //"<a href=\"javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "');\"><center>New</center></a>";
                             cmdLeaveSript = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#00000\"> [View]</font></a>";
                            }else{
                             cmdLeaveSript  = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                            }
                        }*/
                         //jika statusnya DRAFF Dan To Be Approval
                        else if((leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT) || (leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE) || (leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED)){
                            cmdLeaveSript  = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                             if(listLeaveAplication!=null && listLeaveAplication.size()>0){
                              cmdLeaveSript = cmdLeaveSript + " <br><br> <a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                             }
                       }
                         //end
                         else{
                             cmdLeaveSript = "<a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";

                        }
                    }else{
                        cmdLeaveSript = "<a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                    }
                   
                  
                    //untuk insentif 1st
                   //update by satrya 2013-07-26
                   boolean isInsentif=false;
                   if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                       isInsentif = payrollCalculatorConfig.checkPayrollComponent(payCompCode, presenceReportDaily.getEmpId(), presenceReportDaily.getDepartement_id(), presenceReportDaily.getSelectedDate(),holidaysTable.isHoliday(religion_id !=0 ? religion_id : 0, presenceReportDaily.getSelectedDate()), oidLeave, ovId, presenceReportDaily.getStatus1(), iPositionLevel, iPropInsentifLevel, strSchldSymbol1);
                   }
                    
                if (dtSchldIn2nd != null && dtSchldOut2nd != null) {
                    // ---> FIRST SCHEDULE															
                    // calculate working duration
                    String strDurationFirst = getWorkingDuration(dtActualIn1st, dtActualOut1st, breakDuration);

                    // process generate string time interval for selected schedule
                    String strDtSchldIn = Formater.formatTimeLocale(dtSchldIn1st);
                    String strDtSchldOut = Formater.formatTimeLocale(dtSchldOut1st);
                    boolean schedule1stWithoutInterval = false;
                    if (!(intScheduleCategory1st == PstScheduleCategory.CATEGORY_REGULAR
                            || intScheduleCategory1st == PstScheduleCategory.CATEGORY_SPLIT_SHIFT
                            || intScheduleCategory1st == PstScheduleCategory.CATEGORY_NIGHT_WORKER
                            || intScheduleCategory1st == PstScheduleCategory.CATEGORY_ACCROSS_DAY
                            || intScheduleCategory1st == PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY)) {
                        if (strDtSchldIn.compareTo(strDtSchldOut) == 0) {
                            strDtSchldIn = "-";
                            strDtSchldOut = "-";
                            schedule1stWithoutInterval = true;
                        }
                    }

                    // calculate diffence between schedule and actual
                    String strDiffIn1st = "-";
                    String strDiffOut1st = "-";
                    if (!schedule1stWithoutInterval) {
                        strDiffIn1st = getDiffIn(dtSchldIn1st, dtActualIn1st);
                        strDiffOut1st = getDiffOut(dtSchldOut1st, dtActualOut1st);
                    }

                    Vector rowx2nd = new Vector(1, 1);
                    //untuk menampilkan angka urutan
                    rowx2nd.add(String.valueOf(start));
                    //untuk menampilkan hari libur
                    rowx2nd.add(cmdDaysHoliday); 
                    //untuk menampilkan hari PayrolNumber
                    rowx2nd.add(strPayrolNumber);
                    //untuk menampilkan hari Nama EMployee
                    rowx2nd.add(""+strEmpFullName);
                    //untuk menampilkan sysmbol
                    String cmdEditAttendace = "<a href=javascript:cmdEditAttendace('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#6666FFF\">Edit</font></a>";
                    String cmdEditAttendanceManual =  "<a href=javascript:cmdEditAttendaceManual('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#6666FFF\">Edit Duty</font></a>";
                    rowx2nd.add(strSchldSymbol1);
                    ///menampilkan actual
                    rowx2nd.add((dtActualIn1st != null) ? Formater.formatTimeLocale(dtActualIn1st, "d/M/yyyy") +"<br>"+Formater.formatTimeLocale(dtActualIn1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");
                    //menampilkan Break In dan breask Out
                    rowx2nd.add(bOut !=null || bOut.length() > 0 ? bOut : "-");
                    rowx2nd.add(bIn !=null || bIn.length() > 0 ? bIn : "-");
                    //menampilkan actualOut
                    rowx2nd.add((dtActualOut1st != null) ? Formater.formatTimeLocale(dtActualOut1st, "d/M/yyyy")+"<br>"+ Formater.formatTimeLocale(dtActualOut1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");//untuk Time Out
                    //diference 1st
                     if(strDiffIn1st !=null && strDiffIn1st.length() > 0){
                           rowx2nd.add(strDiffIn1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffIn1st+"</font>" :strDiffIn1st );//untuk diference IN
                     }else{
                         rowx2nd.add("<font color=\"#CC0000\">"+"-"+"</font>");
                     }
                     if(strDiffOut1st !=null && strDiffOut1st.length() > 0){
                         rowx2nd.add(strDiffOut1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffOut1st+"</font>" : strDiffOut1st );//untuk Diference OUT
                     }else{
                         rowx2nd.add("<font color=\"#CC0000\">"+"-"+"</font>");
                     }

                     //untuk Duration
                     if(strDurationFirst !=null && strDurationFirst.length() > 0){
                        rowx2nd.add(strDurationFirst.indexOf("-")>-1 ? "<font color=\"#CC0000\"><center>"+strDurationFirst+"</center></font>" :"<font color=\"#000000\"><B><center>"+strDurationFirst+"</center></B></font>" ); //untuk durasi
                     }else{rowx2nd.add("<font color=\"#CC0000\">"+"-"+"</font>");}
                     rowx2nd.add("");
                     rowx2nd.add("");
                     rowx2nd.add("");
                     rowx2nd.add("");
                     rowx2nd.add("");
                     rowx2nd.add("");
                     rowx2nd.add("");
                     rowx2nd.add("");
                     rowx2nd.add("");
                     rowx2nd.add("");
                     rowx2nd.add("");
                   
                    lstData.add(rowx2nd);

////belum dis entuh
                    Vector rowx2ndPdf = new Vector(1, 1);
                    rowx2ndPdf.add(String.valueOf(start));
                    rowx2ndPdf.add(daysHolidayName);
                    rowx2ndPdf.add(strEmpFullName);
                    rowx2ndPdf.add(strSchldSymbol1);
                    
                    rowx2ndPdf.add((dtActualIn1st != null) ? Formater.formatTimeLocale(dtActualIn1st, "d/M/yyyy") + Formater.formatTimeLocale(dtActualIn1st, "HH:mm") : "-");
                    rowx2ndPdf.add(bOut !=null || bOut.length() > 0 ? "-"+bOut : "-");
                    rowx2ndPdf.add(bIn !=null || bIn.length() > 0 ? "-"+bIn : "-");
                    rowx2ndPdf.add((dtActualOut1st != null) ? Formater.formatTimeLocale(dtActualOut1st, "d/M/yyyy") + Formater.formatTimeLocale(dtActualOut1st, "HH:mm") : "-");
                    //diference 1st
                     if(strDiffIn1st !=null && strDiffIn1st.length() > 0){
                           rowx2ndPdf.add(strDiffIn1st.indexOf("-")>-1 ? strDiffIn1st :strDiffIn1st );//untuk diference IN
                     }else{
                         rowx2ndPdf.add("-");
                     }
                     if(strDiffOut1st !=null && strDiffOut1st.length() > 0){
                         rowx2ndPdf.add(strDiffOut1st.indexOf("-")>-1 ? strDiffOut1st : strDiffOut1st );//untuk Diference OUT
                     }else{
                         rowx2ndPdf.add("-");
                     }

                     //untuk Duration
                     if(strDurationFirst !=null && strDurationFirst.length() > 0){
                        rowx2ndPdf.add(strDurationFirst.indexOf("-")>-1 ?  strDurationFirst :strDurationFirst ); //untuk durasi
                     }else{
                        rowx2ndPdf.add("-");
                     }
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    rowx2ndPdf.add("");
                    vectDataToPdf.add(rowx2ndPdf);

                    // ---> SECOND SCHEDULE	
                    // calculate working duration
                    String strDurationSecond = getWorkingDuration(dtActualIn2nd, dtActualOut2nd, breakDuration);

                    // process generate string time interval for selected schedule
                    String strDtSchldIn2nd = Formater.formatTimeLocale(dtSchldIn2nd);
                    String strDtSchldOut2nd = Formater.formatTimeLocale(dtSchldOut2nd);

                    boolean schedule2ndWithoutInterval = false;

                    if (!(intScheduleCategory2nd == PstScheduleCategory.CATEGORY_REGULAR
                            || intScheduleCategory2nd == PstScheduleCategory.CATEGORY_SPLIT_SHIFT
                            || intScheduleCategory2nd == PstScheduleCategory.CATEGORY_NIGHT_WORKER
                            || intScheduleCategory2nd == PstScheduleCategory.CATEGORY_ACCROSS_DAY
                            || intScheduleCategory2nd == PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY)) {
                        if (strDtSchldIn2nd.compareTo(strDtSchldOut2nd) == 0) {
                            strDtSchldIn2nd = "-";
                            strDtSchldOut2nd = "-";
                            schedule2ndWithoutInterval = true;
                        }
                    }

                    // calculate diffence between schedule and actual
                    String strDiffIn2nd = "-";
                    String strDiffOut2nd = "-";
                    if (!schedule2ndWithoutInterval) {
                        strDiffIn2nd = getDiffIn(dtSchldIn2nd, dtActualIn2nd);
                        strDiffOut2nd = getDiffOut(dtSchldOut2nd, dtActualOut2nd);
                    }
                    //real
                    rowx.add("");
                    rowx.add("");
                    rowx.add("");
                    rowx.add("");
                    //rowx.add(sSymbol2nd +"<br>"+cmdEditAttendanceManual);
                    rowx.add(sSymbol2nd +"<br>"+cmdEditAttendace + cmdEditAttendanceManual);
                    ///menampilkan actual
                    rowx.add((dtActualIn2nd != null) ? Formater.formatTimeLocale(dtActualIn2nd, "d/M/yyyy") +"<br>"+Formater.formatTimeLocale(dtActualIn2nd, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");
                    //menampilkan Break In dan break Out
                    rowx.add("");
                    rowx.add("");
                     //menampilkan actualOut
                    rowx.add((dtActualOut2nd != null) ? Formater.formatTimeLocale(dtActualOut2nd, "d/M/yyyy")+"<br>"+ Formater.formatTimeLocale(dtActualOut2nd, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");//untuk Time Out
                     //diference 2nd
                     if(strDiffIn2nd !=null && strDiffIn2nd.length() > 0){
                           rowx.add(strDiffIn2nd.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffIn2nd+"</font>" :strDiffIn2nd );//untuk diference IN
                     }else{
                         rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                     }
                     if(strDiffOut1st !=null && strDiffOut1st.length() > 0){
                         rowx.add(strDiffOut2nd.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffOut2nd+"</font>" : strDiffOut2nd );//untuk Diference OUT
                     }else{
                         rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                     }
                    
                     //untuk Duration
                     if(strDurationSecond !=null && strDurationSecond.length() > 0){
                        rowx.add(strDurationSecond.indexOf("-")>-1 ? "<font color=\"#CC0000\"><center>"+strDurationSecond+"</center></font>" :"<font color=\"#000000\"><B><center>"+strDurationSecond+"</center></B></font>" ); //untuk durasi
                     }else{rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");}
                     
                   //untuk menampilkan status Leave
                    rowx.add("<center>"+cmdLeaveSript2nd+"</center>");
                    
                    // untuk colom insentif,overtime form,allowance,Paid,Net overtime,OT.index
                    /*if(presenceReportDaily.getStatus1() == PstEmpSchedule.STATUS_PRESENCE_OK 
                       && oidLeave==0 && !strSchldSymbol1.equalsIgnoreCase("off")&& iPositionLevel<=iPropInsentifLevel){
                      if(presenceReportDaily.getDepartement_id()== iPropDeptInstf &&(dayString.equalsIgnoreCase("Sat") || dayString.equalsIgnoreCase("Sun"))){
                            insentif = "";
                        }else{
                            insentif = "&#10004;";
                        }
                        //insentif = "&#10004;";
                    }*/
                    if(isInsentif){
                         insentif = "&#10004;";
                    }else{
                        insentif = "";
                    }
                    //cek untuk karyawan APG (security)
                    /*if(presenceReportDaily.getDepartement_id()== iPropDeptInstf &&(ovId !=0L)){
                          insentif = "";
                        }else{
                            insentif = "&#10004;";
                        }
                    */
                  //update by satrya 2013-07-26
                  if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                    rowx.add("<center>"+insentif+"</center>");
                  }
                  if(showOvertime==0){
                    rowx.add("<center>"+oVForm+"</center>");
                    rowx.add("<center>"+allwance+"</center>");
                    rowx.add("<center>"+paid+"</center>");
                    rowx.add("<center>"+NetOv+"</center>");
                    rowx.add("<center>"+oVerIdx+"</center>");
                  }

                    //untuk menampilkan reason
                    String cbReason = ControlCombo.drawTooltip(inputName2nd + "_reason2nd", "select...", "" + presenceReportDaily.getReasonNo2nd(), reason_value, reason_key, "onkeydown=\"javascript:fnTrapKD()\"",reason_tooltip); 
                    // String cbReason = ControlCombo.draw(inputName2nd + "_reason2nd", "select...", "" + presenceReportDaily.getReasonNo2nd(), reason_value, reason_key, "onkeydown=\"javascript:fnTrapKD()\"");
                    rowx.add(cbReason);
                    
                    //untuk menampilkan note
                    rowx.add("<textarea name=\"" + inputName2nd + "_note2nd\" cols=\"15\" rows=\"3\">"+presenceReportDaily.getNote2nd()+"</textarea>");
                    //rowx.add("<input type=\"text\" name=\"" + inputName + "_note\" value=\"" + presenceReportDaily.getNote1nd() + "\" >");
                    
                    //untuk menampilkan status
                    String cbStatus = ControlCombo.draw(inputName2nd + "_status2nd", "elementForm", null, "" + presenceReportDaily.getStatus2(), statusVal, statusTxt, " onkeydown=\"javascript:fnTrapKD()\" ");
                    rowx.add(!privUpdatePresence ? ("" + PstEmpSchedule.strPresenceStatus[ (0 <= presenceReportDaily.getStatus2() && presenceReportDaily.getStatus2() < PstEmpSchedule.strPresenceStatus.length) ? presenceReportDaily.getStatus2() : 0]) : "" + cbStatus +"");
                    
                    if (privUpdatePresence) {
                        //rowx.add("<input type=\"checkbox\" name=\"" + strEmpNum + "_select\" value=\"\" >");
                        rowx.add("<input type=\"checkbox\" name=\"userSelect\" value=\"" + inputName2nd + "\" >");
                    }
                    
////belum dis entuh
                    rowxPdf.add("");
                    rowxPdf.add("");
                    rowxPdf.add("");
                    rowxPdf.add("");
                    rowxPdf.add(strSchldSymbol2);
                    rowxPdf.add((dtActualIn2nd != null) ? Formater.formatTimeLocale(dtActualIn2nd, "d/M/yyyy")+Formater.formatTimeLocale(dtActualIn2nd, "HH:mm"):"-");
                    rowxPdf.add("");
                    rowxPdf.add("");
                    rowxPdf.add((dtActualOut2nd != null) ? Formater.formatTimeLocale(dtActualOut2nd, "d/M/yyyy") + Formater.formatTimeLocale(dtActualOut2nd, "HH:mm") : "-");
                    //diference 1st
                     if(strDiffIn2nd !=null && strDiffIn2nd.length() > 0){
                           rowxPdf.add(strDiffIn2nd.indexOf("-")>-1 ? strDiffIn2nd : strDiffIn2nd );//untuk diference IN
                     }else{
                         rowxPdf.add("-");
                     }
                     if(strDiffOut2nd !=null && strDiffOut2nd.length() > 0){
                         rowxPdf.add(strDiffOut2nd.indexOf("-")>-1 ? strDiffOut2nd : strDiffOut2nd );//untuk Diference OUT
                     }else{
                         rowxPdf.add("-");
                     }

                     //untuk Duration
                     if(strDurationFirst !=null && strDurationFirst.length() > 0){
                        rowxPdf.add(strDurationFirst.indexOf("-")>-1 ? strDurationFirst:strDurationFirst); //untuk durasi
                     }else{
                        rowxPdf.add("-");
                     }
                    rowxPdf.add(cmdLeaveSript2nd);
                   if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                    rowxPdf.add(insentif);
                   }
                  if(showOvertime==0){
                    rowxPdf.add(oVForm);
                    rowxPdf.add(allwance);
                    rowxPdf.add(paid);
                    rowxPdf.add(NetOv);
                    rowxPdf.add(oVerIdx);
                  }
                    rowxPdf.add(presenceReportDaily.getReasonNo2nd());
                    rowxPdf.add(presenceReportDaily.getNote2nd());
                    rowxPdf.add(strSymStatus2nd);

                } // ---> REGULAR SCHEDULE			
                else {
//loop untuk b-Out / B-In
                    // calculate working duration
                     
                    String strDurationFirst = getWorkingDuration(dtActualIn1st, dtActualOut1st, breakDuration );

                    // process generate string time interval for selected schedule
                    String strDtSchldIn = Formater.formatTimeLocale(dtSchldIn1st);
                    String strDtSchldOut = Formater.formatTimeLocale(dtSchldOut1st);
                    boolean schedule1stWithoutInterval = false;
                    if (strDtSchldIn.compareTo(strDtSchldOut) == 0) {
                        strDtSchldIn = "-";
                        strDtSchldOut = "-";
                        schedule1stWithoutInterval = true;
                    }

                    // calculate diffence between schedule and actual
                    String strDiffIn1st = "-";
                    String strDiffOut1st = "-";
                    if (!schedule1stWithoutInterval) {
                        try { 
                            strDiffIn1st = getDiffIn(dtSchldIn1st, dtActualIn1st);
                        strDiffOut1st = getDiffOut(dtSchldOut1st, dtActualOut1st);
                        }catch(Exception ex){
                            System.out.println("exCeption Interval "+ex);
                        }
                    }
                     //untuk menampilkan sysmbol
                   //update by satrya 2012-08-11
                   String cmdEditAttendace = "<a href=javascript:cmdEditAttendace('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#6666FFF\">Edit</font></a>";
                   String cmdEditAttendanceManual =  "<a href=javascript:cmdEditAttendaceManual('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#6666FFF\">Edit Duty</font></a>";
                    //rowx.add(sSymbol +"<br>"+cmdEditAttendanceManual);
                   if(privUpdatePresence && isHRDLogin){ 
                    cmdAddAttd =  "<a href=javascript:cmdAddAttd('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#6666FFF\">Add</font></a>";
                   }
                   
                        
                   //untuk menampilkan status
                    String cbStatus = ControlCombo.draw(inputName + "_status", "elementForm", null, "" + presenceReportDaily.getStatus1(), statusVal, statusTxt, " onkeydown=\"javascript:fnTrapKD()\" "); 
                    //update by satrya 2012-08-19
                    //untuk menampilkan reason
                 String cbReason = ControlCombo.drawTooltip(inputName + "_reason", "select...", "" + presenceReportDaily.getReasonNo1nd(), reason_value, reason_key, "onkeydown=\"javascript:fnTrapKD()\"",reason_tooltip);
                 // String cbReason = ControlCombo.drawTooltip(inputName + "_reason", "select...", "" + presenceReportDaily.getReasonNo1nd(), reason_value, reason_key, "onkeydown=\"javascript:fnTrapKD()\"",reason_tooltip);
                   
               if(attdConfig!=null && attdConfig.getConfigurasiReportScheduleDaily()==I_Atendance.CONFIGURASI_I_REPORT_DAILY_SCHEDULE_AND_CEK_BOX_ADA_DIBELAKANG){    
                rowx.add(String.valueOf(start));
                //untuk menampilkan hari libur
                rowx.add(cmdDaysHoliday);
                //update by satrya 2012-09-06
                rowx.add(strPayrolNumber);
                rowx.add(strEmpFullName);
                 
                   
                 
                 rowx.add((dtActualIn1st != null) ? Formater.formatTimeLocale(dtActualIn1st, "d/M/yy") +"<br>"+Formater.formatTimeLocale(dtActualIn1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");
                    //menampilkan Break In dan break Out
                 rowx.add(bOut !=null && bOut.length() > 0 ? bOut : "-");
                 rowx.add(bIn !=null && bIn.length() > 0 ? bIn : "-");
                 rowx.add((dtActualOut1st != null) ? Formater.formatTimeLocale(dtActualOut1st, "d/M/yy")+"<br>"+ Formater.formatTimeLocale(dtActualOut1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");//untuk Time Out
                 if(strDiffIn1st !=null && strDiffIn1st.length() > 0){
                           rowx.add(strDiffIn1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffIn1st+"</font>" :strDiffIn1st );//untuk diference IN
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 if(strDiffOut1st !=null && strDiffOut1st.length() > 0){
                         rowx.add(strDiffOut1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffOut1st+"</font>" : strDiffOut1st );//untuk Diference OUT
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 //untuk Duration
                 if(strDurationFirst !=null && strDurationFirst.length() > 0){
                     rowx.add(strDurationFirst.indexOf("-")>-1 ? "<font color=\"#CC0000\"><center>"+strDurationFirst+"</center></font>" :"<font color=\"#000000\"><B><center>"+strDurationFirst+"</center></B></font>" ); //untuk durasi
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 //end duration
                 rowx.add(sSymbol +"<br>"+ (cmdAddAttd.length()>0?cmdAddAttd + " || ":"") + cmdEditAttendace  +" || "+cmdEditAttendanceManual);
                 //untuk menampilkan status Leave
                 rowx.add("<center>"+cmdLeaveSript+"</center>");
                //update by satrya 2012-09-13
                // untuk colom insentif,overtime form,allowance,Paid,Net overtime,OT.index
                if(isInsentif){
                    insentif = "&#10004;";
                }else{
                    insentif = "";
                }
                //update by satrya 2013-07-025
                if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                        rowx.add("<center>"+insentif+"</center>");
                }
                if(showOvertime==0){ 
                    rowx.add(ControlCombo.draw(inputName + "_oVForm", "select...", ""+oVForm, PstOvertime.getValueOtForm(), PstOvertime.getKeyOtForm(), "onkeydown=\"javascript:fnTrapKD()\""));
                    rowx.add(ControlCombo.draw(inputName + "_allwance", "select...", ""+allwance, PstOvertime.getValueAllowance(), PstOvertime.getKeyAllowance(), "onkeydown=\"javascript:fnTrapKD()\""));
                    rowx.add(ControlCombo.draw(inputName + "_paid", "select...", ""+paid, OvertimeDetail.getPaidByVal(), OvertimeDetail.getPaidByKey(), "onkeydown=\"javascript:fnTrapKD()\"")); 
                    rowx.add("<input type=\"text\" size=\"4\" name=\""+inputName+"_NetOv\" value=\"" + NetOv + "\" >");
                    rowx.add("<input type=\"text\" size=\"4\" name=\""+inputName+"_oVerIdx\" value=\"" + oVerIdx + "\" >");  
                }
                //rowx.add(!privUpdatePresence ? "" : ((presenceReportDaily.getScheduleId1() == Al_oid) || (presenceReportDaily.getScheduleId1() == LL_oid) || (presenceReportDaily.getScheduleId1() == DP_oid)) || (scheduleSymbolIdMap.containsKey(""+presenceReportDaily.getScheduleId1()))   
                rowx.add(!privUpdatePresence ? ("" + PstEmpSchedule.strPresenceStatus[ (0 <= presenceReportDaily.getStatus1() && presenceReportDaily.getStatus1() < PstEmpSchedule.strPresenceStatus.length) ? presenceReportDaily.getStatus1() : 0]) : "" + cbStatus +"");
                rowx.add(cbReason);
                //untuk menampilkan note
                rowx.add("<textarea name=\"" + inputName + "_note\" cols=\"15\" rows=\"2\">"+presenceReportDaily.getNote1nd()+"</textarea>");
                //rowx.add("<input type=\"text\" name=\"" + inputName + "_note\" value=\"" + presenceReportDaily.getNote1nd() + "\" >");
                
                if (privUpdatePresence) {
                        //rowx.add("<input type=\"checkbox\" name=\"" + strEmpNum + "_select\" value=\"\" >");
                    rowx.add("<input type=\"checkbox\" name=\"userSelect\" value=\"" + inputName + "\" >"+"<input type=\"hidden\" size=\"4\" name=\""+inputName+"_ovId\" value=\"" + ovId + "\" >");
                 }
              }else{
                 rowx.add(String.valueOf(start));
                //untuk menampilkan hari libur
                rowx.add(cmdDaysHoliday);
                //update by satrya 2012-09-06
                rowx.add(strPayrolNumber);
                rowx.add(strEmpFullName);
                 if (privUpdatePresence) {
                        //rowx.add("<input type=\"checkbox\" name=\"" + strEmpNum + "_select\" value=\"\" >");
                    rowx.add("<input type=\"checkbox\" name=\"userSelect\" value=\"" + inputName + "\" >"+"<input type=\"hidden\" size=\"4\" name=\""+inputName+"_ovId\" value=\"" + ovId + "\" >");
                 }
                   
                 rowx.add(sSymbol +"<br>"+ (cmdAddAttd.length()>0?cmdAddAttd + " || ":"") + cmdEditAttendace  +" || "+cmdEditAttendanceManual);
                 rowx.add((dtActualIn1st != null) ? Formater.formatTimeLocale(dtActualIn1st, "d/M/yy") +"<br>"+Formater.formatTimeLocale(dtActualIn1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");
                    //menampilkan Break In dan break Out
                 rowx.add(bOut !=null && bOut.length() > 0 ? bOut : "-");
                 rowx.add(bIn !=null && bIn.length() > 0 ? bIn : "-");
                 rowx.add((dtActualOut1st != null) ? Formater.formatTimeLocale(dtActualOut1st, "d/M/yy")+"<br>"+ Formater.formatTimeLocale(dtActualOut1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");//untuk Time Out
                 if(strDiffIn1st !=null && strDiffIn1st.length() > 0){
                           rowx.add(strDiffIn1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffIn1st+"</font>" :strDiffIn1st );//untuk diference IN
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 if(strDiffOut1st !=null && strDiffOut1st.length() > 0){
                         rowx.add(strDiffOut1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffOut1st+"</font>" : strDiffOut1st );//untuk Diference OUT
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 //untuk Duration
                 if(strDurationFirst !=null && strDurationFirst.length() > 0){
                     rowx.add(strDurationFirst.indexOf("-")>-1 ? "<font color=\"#CC0000\"><center>"+strDurationFirst+"</center></font>" :"<font color=\"#000000\"><B><center>"+strDurationFirst+"</center></B></font>" ); //untuk durasi
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 //end duration
                 //untuk menampilkan status Leave
                 rowx.add("<center>"+cmdLeaveSript+"</center>");
                //update by satrya 2012-09-13
                // untuk colom insentif,overtime form,allowance,Paid,Net overtime,OT.index
                if(isInsentif){
                    insentif = "&#10004;";
                }else{
                    insentif = "";
                }
                //update by satrya 2013-07-025
                if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                        rowx.add("<center>"+insentif+"</center>");
                }
                if(showOvertime==0){ 
                    rowx.add(ControlCombo.draw(inputName + "_oVForm", "select...", ""+oVForm, PstOvertime.getValueOtForm(), PstOvertime.getKeyOtForm(), "onkeydown=\"javascript:fnTrapKD()\""));
                    rowx.add(ControlCombo.draw(inputName + "_allwance", "select...", ""+allwance, PstOvertime.getValueAllowance(), PstOvertime.getKeyAllowance(), "onkeydown=\"javascript:fnTrapKD()\""));
                    rowx.add(ControlCombo.draw(inputName + "_paid", "select...", ""+paid, OvertimeDetail.getPaidByVal(), OvertimeDetail.getPaidByKey(), "onkeydown=\"javascript:fnTrapKD()\"")); 
                    rowx.add("<input type=\"text\" size=\"4\" name=\""+inputName+"_NetOv\" value=\"" + NetOv + "\" >");
                    rowx.add("<input type=\"text\" size=\"4\" name=\""+inputName+"_oVerIdx\" value=\"" + oVerIdx + "\" >");  
                }
                //rowx.add(!privUpdatePresence ? "" : ((presenceReportDaily.getScheduleId1() == Al_oid) || (presenceReportDaily.getScheduleId1() == LL_oid) || (presenceReportDaily.getScheduleId1() == DP_oid)) || (scheduleSymbolIdMap.containsKey(""+presenceReportDaily.getScheduleId1()))   
                rowx.add(!privUpdatePresence ? ("" + PstEmpSchedule.strPresenceStatus[ (0 <= presenceReportDaily.getStatus1() && presenceReportDaily.getStatus1() < PstEmpSchedule.strPresenceStatus.length) ? presenceReportDaily.getStatus1() : 0]) : "" + cbStatus +"");
                rowx.add(cbReason);
                //untuk menampilkan note
                rowx.add("<textarea name=\"" + inputName + "_note\" cols=\"15\" rows=\"2\">"+presenceReportDaily.getNote1nd()+"</textarea>");
                //rowx.add("<input type=\"text\" name=\"" + inputName + "_note\" value=\"" + presenceReportDaily.getNote1nd() + "\" >");
              
              }     
                    
                   
                    
                   rowxPdf.add(String.valueOf(start));
                    //update by satrya
                    if(holidaysTable.isHoliday(religion_id !=0 ? religion_id : 0, presenceReportDaily.getSelectedDate())){
                        rowxPdf.add(Formater.formatTimeLocale(presenceReportDaily.getSelectedDate(), "EEEE, d/M/yyyy")+"."+"  ["+daysHolidayName+" ] ");
                    }
                    else{
                        rowxPdf.add(Formater.formatTimeLocale(presenceReportDaily.getSelectedDate(), "EEEE, d/M/yyyy"));
                    }
                    //update by satrya 2012-09-06
                    rowxPdf.add(strPayrolNumber);
                   // rowxPdf.add(strEmpFullName +" [ "+strPayrolNumber+" ] ");
                    rowxPdf.add(strEmpFullName);
                    rowxPdf.add(strSchldSymbol1);
                    rowxPdf.add((dtActualIn1st != null) ? Formater.formatTimeLocale(dtActualIn1st) : "-");
                    rowxPdf.add((dBout != null && dBout.length()>0) ? dBout : "-");//break out
                    rowxPdf.add((dBin != null && dBin.length()>0) ? dBin  : "-");//break in
                    rowxPdf.add((dtActualOut1st != null) ? Formater.formatTimeLocale(dtActualOut1st) : "-");
                    rowxPdf.add(strDiffIn1st);
                    rowxPdf.add(strDiffOut1st);
                    rowxPdf.add(strDurationFirst);
                   //update by satrya 2013-07-26
                   if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){ 
                    rowxPdf.add(insentif);
                   }
                 if(showOvertime==0){
                     
                    rowxPdf.add(oVForm==-1?"-":I_DocStatus.fieldDocumentStatus[oVForm]);
                    rowxPdf.add(allwance==-1?"-":Overtime.allowanceType[allwance]); 
                    rowxPdf.add(paid==-1?"-":OvertimeDetail.paidByKey[paid]);
                    rowxPdf.add(NetOv); 
                    rowxPdf.add(oVerIdx);
                  }
                    //update by satrya 2012-07-23
                    rowxPdf.add(presenceReportDaily !=null &&  reasonMap !=null && presenceReportDaily.getReasonNo1nd() !=0 ? (String) reasonMap.get(""+presenceReportDaily.getReasonNo1nd())  : "-" );
                    rowxPdf.add(strNote);
                    rowxPdf.add(strSymStatus); 
                }
                   //update by satrya 2012-10-15
                ctrlist.drawListRowJsVer2(outObj, 0, rowx, i);
               // lstData.add(rowx);r
                vectDataToPdf.add(rowxPdf);
               // int test = rowxPdf.size();
                }
           }catch(Exception ex){
               
               System.out.println("Exception presenceReportDaily : " +ex.toString());
               
           }
         }//end list

            result.add(String.valueOf(DATA_PRINT));
           //result.add("");
            //ctrlist.drawList(outObj, index);
            ctrlist.drawListEndTableJsVer2(outObj);
            result.add(vectDataToPdf);
        } else {
            result.add(String.valueOf(DATA_NULL));
            result.add("<div class=\"msginfo\">&nbsp;&nbsp;No attendance record found ...</div>");
            result.add(new Vector(1, 1));
        }

        return result;
    }
    
    
    
    /**
        create by satrya 2013-11-27
        Keterangan: user hanya bisa melihat attdance tidak bisa Edit Data
    **/
    public Vector drawListViewOnly(JspWriter outObj,int iCommand,Vector listAttendanceRecordDailly, Vector listPresencePersonalInOut,Vector listOvertimeDaily,HolidaysTable holidaysTable,  Date selectDateFrom, Date selectDateTo,String fullName,String empNum,int start,int showOvertime,I_Atendance attdConfig) {
        Vector result = new Vector(1, 1); 
       
        if (listAttendanceRecordDailly != null && listAttendanceRecordDailly.size() > 0) {
            long Al_oid = 0;
            long DP_oid = 0;
            long LL_oid = 0;
           try {
                Al_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_AL"));
                DP_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_DP"));
                LL_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_LL"));
            } catch (Exception exc) {
            }
            Vector statusVal = new Vector();
            Vector statusTxt = new Vector();
            for (int s = 0; s < PstEmpSchedule.strPresenceStatus.length; s++) {
                statusVal.add("" + s);
                statusTxt.add("" + PstEmpSchedule.strPresenceStatus[s]);
            }


            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            //update by satrya 2012-09-17
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setCellStyles("listgensellstyles");
            ctrlist.setRowSelectedStyles("rowselectedstyles");
            ctrlist.setHeaderStyle("listheaderJs");
            
      if(attdConfig!=null && attdConfig.getConfigurasiReportScheduleDaily()==I_Atendance.CONFIGURASI_I_REPORT_DAILY_SCHEDULE_AND_CEK_BOX_ADA_DIBELAKANG){
            ctrlist.addHeader("No", "1%", "2", "0");
            ctrlist.addHeader("Date", "1%", "2", "0");
            ctrlist.addHeader("Payrol"+"<br>"+"Numb", "1%", "2", "0");
            ctrlist.addHeader("Employee", "10%", "2", "0");//6%
            ctrlist.addHeader("Actual", "2%", "0", "4");
            ctrlist.addHeader("Time"+"<br>"+"In", "2%", "0", "0");
            ctrlist.addHeader("Break"+"<br>"+"Out", "2%", "0", "0");
            ctrlist.addHeader("Break"+"<br>"+"In", "2%", "0", "0");
            ctrlist.addHeader("Time"+"<br>"+"Out", "2%", "0", "0");
            ctrlist.addHeader("Difference", "3%", "0", "2");
            ctrlist.addHeader("In", "2%", "0", "0");
            ctrlist.addHeader("Out", "2%", "0", "0");
            ctrlist.addHeader("Duration", "3%", "2", "0");
            ctrlist.addHeader("Schedule", "50%", "0", "1");
            ctrlist.addHeader("Symbol", "50%", "0", "0");
            ctrlist.addHeader("Leave", "2%", "2", "0");
           if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
            ctrlist.addHeader("Insentif", "2%", "2", "0");//insentif
           } 
          if(showOvertime==0){ 
            ctrlist.addHeader("OT."+"<br>"+"Frm", "2%", "2", "0");//overtime form
            ctrlist.addHeader("Allwn", "2%", "2", "0");//Allowance
            ctrlist.addHeader("Paid"+"<br>"+"/Dp", "2%", "2", "0");//PAID
            ctrlist.addHeader("Net"+"<br>"+"OT", "2%", "2", "0");//NET Overtime
            ctrlist.addHeader("OT."+"<br>"+"Idx", "2%", "2", "0");//Overtime INdex
          }  
             ctrlist.addHeader("Status", "1%", "2", "0");
             ctrlist.addHeader("Reason", "2%", "2", "0");
             ctrlist.addHeader("Note", "1%", "2", "0");
         
     }else{
            ctrlist.addHeader("No", "1%", "2", "0");
            ctrlist.addHeader("Date", "1%", "2", "0");
            ctrlist.addHeader("Payrol"+"<br>"+"Numb", "1%", "2", "0");
            ctrlist.addHeader("Employee", "10%", "2", "0");//6%
             
            ctrlist.addHeader("Schedule", "50%", "0", "1");
            ctrlist.addHeader("Symbol", "50%", "0", "0");
            ctrlist.addHeader("Actual", "2%", "0", "4");
            ctrlist.addHeader("Time"+"<br>"+"In", "2%", "0", "0");
            ctrlist.addHeader("Break"+"<br>"+"Out", "2%", "0", "0");
            ctrlist.addHeader("Break"+"<br>"+"In", "2%", "0", "0");
            ctrlist.addHeader("Time"+"<br>"+"Out", "2%", "0", "0");
            ctrlist.addHeader("Difference", "3%", "0", "2");
            ctrlist.addHeader("In", "2%", "0", "0");
            ctrlist.addHeader("Out", "2%", "0", "0");
            ctrlist.addHeader("Duration", "3%", "2", "0");
            ctrlist.addHeader("Leave", "2%", "2", "0");
            if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                ctrlist.addHeader("Insentif", "2%", "2", "0");//insentif
            } 
          if(showOvertime==0){ 
            ctrlist.addHeader("OT."+"<br>"+"Frm", "2%", "2", "0");//overtime form
            ctrlist.addHeader("Allwn", "2%", "2", "0");//Allowance
            ctrlist.addHeader("Paid"+"<br>"+"/Dp", "2%", "2", "0");//PAID
            ctrlist.addHeader("Net"+"<br>"+"OT", "2%", "2", "0");//NET Overtime
            ctrlist.addHeader("OT."+"<br>"+"Idx", "2%", "2", "0");//Overtime INdex
          }  
            ctrlist.addHeader("Status", "1%", "2", "0");
            ctrlist.addHeader("Reason", "2%", "2", "0");
            ctrlist.addHeader("Note", "1%", "2", "0");
                
     }    
            
           
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            
            int index = -1;
            Vector list = new Vector(1, 1);

            // vector of data will used in pdf report
            Vector vectDataToPdf = new Vector(1, 1);
            if(listAttendanceRecordDailly !=null && listAttendanceRecordDailly.size() > 0){
                //update by satrya 2012-10-15
                 ctrlist.drawListHeaderWithJsVer2(outObj);//header
                try {
                    //untuk special Leave <satrya 2012-08-01>
                    /**
                        untuk melakukan settingan jika statusnya full day atau jam-jam'an
                    **/
                    int iLeaveMinuteEnable = 0;//hanya cuti full day jika fullDayLeave = 0
                   /**
                        untuk pengaturan apakah mendapatkan insentif atau tidak
                        untuk cek apakah department house kipping perlu dtp insentif
                    **/
                    int iPropInsentifLevel = 0;//hanya cuti full day jika fullDayLeave = 0 
                    ///cek untuk mendqapatkan insentif
                    I_PayrollCalculator payrollCalculatorConfig = null;                    
                    Vector listScheduleSymbol = new Vector(1, 1);
                    try{
                        listScheduleSymbol.add(new Long(PstSystemProperty.getValueByName("OID_SPECIAL")));
                        listScheduleSymbol.add(new Long(PstSystemProperty.getValueByName("OID_UNPAID")));
                        iLeaveMinuteEnable = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_MINUTE_ENABLE"));
                         payrollCalculatorConfig = (I_PayrollCalculator)(Class.forName(PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME")).newInstance());
                         if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                            iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
                            
                          }
                     }catch(Exception E){
                        outObj.println("<blink>SYS PROP  NOT TO BE SET</blink>" );
                     }
               Vector reason_value = new Vector(1, 1);
                Vector reason_key = new Vector(1, 1);
                Vector listReason = new Vector(1, 1);
                Vector reason_tooltip = new Vector(1, 1);
                listReason = PstReason.list(0, 0, "", "REASON");
                for (int r = 0; r < listReason.size(); r++) {
                    Reason reason = (Reason) listReason.get(r);
                    reason_tooltip.add(reason.getReason()); 
                     reason_key.add(reason.getKodeReason());
                    reason_value.add(String.valueOf(reason.getNo()));
                }  
                  Hashtable scheduleSymbolIdMap = PstScheduleSymbol.getScheduleSymbolIdMap(listScheduleSymbol);
                   Hashtable breakTimeDuration = PstScheduleSymbol.getBreakTimeDuration();
                   
            for (int i = 0; i < listAttendanceRecordDailly.size(); i++) { 
                PresenceReportDaily presenceReportDaily = (PresenceReportDaily) listAttendanceRecordDailly.get(i);
               
                String cmdAddAttd = ""; 
                long lDatePresence = presenceReportDaily.getSelectedDate().getTime();
                String strEmpFullName = presenceReportDaily.getEmpFullName();
                String strPayrolNumber = presenceReportDaily.getEmpNum();
                long religion_id = presenceReportDaily.getReligion_id();
                //update by satrya 2012-08-01
                long lEmpId = presenceReportDaily.getEmpId();
               
                start =  start+ 1; 
                SimpleDateFormat formatter = new SimpleDateFormat("d/MM/yyyy");
                SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                String dayString = formatterDay.format(presenceReportDaily.getSelectedDate());
                String dateString = formatter.format(presenceReportDaily.getSelectedDate());
                String dateStringColor = (dayString.equalsIgnoreCase("Sat")) ? "<font color=\"darkblue\">" + dateString + "</font>" : dateString;
                String dayStringColor = (dayString.equalsIgnoreCase("Sat")) ? "<font color=\"darkblue\">" + dayString + "</font>" : dayString;
                dateStringColor = (dayString.equalsIgnoreCase("Sun")) ? "<font color=\"red\">" + dateStringColor + "</font>" : dateStringColor;
                dayStringColor = (dayString.equalsIgnoreCase("Sun")) ? "<font color=\"red\">" + dayStringColor + "</font>" : dayStringColor;
                //update by satrya 2012-08-06

                int iPositionLevel = PstPosition.iGetPositionLevel(presenceReportDaily.getEmpId());
                
                int intScheduleCategory1st = presenceReportDaily.getSchldCategory1st();
                String strSchldSymbol1 = presenceReportDaily.getScheduleSymbol1();
                String strSchldSymbol2 = presenceReportDaily.getScheduleSymbol2();
                Date dtSchldIn1st = (Date) presenceReportDaily.getScheduleIn1st();
                Date dtSchldOut1st = (Date) presenceReportDaily.getScheduleOut1st();
                Date dtActualIn1st = (Date) presenceReportDaily.getActualIn1st();
                Date dtActualOut1st = (Date) presenceReportDaily.getActualOut1st();
                //update by satrya 2013-05-28
                String strSchldIn1st="";
                String strSchldOut1st="";
                 if(dtSchldIn1st!=null){
                    strSchldIn1st = Formater.formatDate(dtSchldIn1st, "HH:mm");
                }
                if(dtSchldOut1st!=null){
                    strSchldOut1st = Formater.formatDate(dtSchldOut1st, "HH:mm");
                }
                 /**
                    update by satrya
                    untuk menampilkan AL,LL, DP,etc
               **/
                 long oidLeave  = 0;
                 String sSymbolLeave = ""; 
               Vector listLeaveAplication =  PstLeaveApplication.listOid(lEmpId, dtSchldIn1st,dtSchldOut1st);
               if(listLeaveAplication !=null && listLeaveAplication.size()>0){
                   //LeaveOidSym obj = new LeaveOidSym();
                   try{
                   for (int j = 0; j < listLeaveAplication.size(); j++) {
                    LeaveOidSym leaveOidSym = (LeaveOidSym) listLeaveAplication.get(j);
                    oidLeave = leaveOidSym.getLeaveOid();
                    String sSymbolLeaveX= (String.valueOf(leaveOidSym.getLeaveSymbol()));
                    sSymbolLeave = sSymbolLeave  + sSymbolLeaveX + ",";
                }
                    if(sSymbolLeave!=null && sSymbolLeave.length()>0){
                         sSymbolLeave= sSymbolLeave.substring(0,sSymbolLeave.length()-1); 
                        }
                 
                   }catch(Exception ex){System.out.println("Exception list Leave Application"+ex);}
                
               }
                int leaveStatus =-1;
                try{
                    leaveStatus = PstLeaveApplication.getLeaveFormStatus(lEmpId, dtSchldIn1st,dtSchldOut1st);
                  }catch(Exception ex){
                      System.out.println("Leave leave Status"+ex);
                  }
               
                String strSchINOut=strSchldIn1st+"-"+strSchldOut1st;
                //update by satrya 2012-09-26
                 Date dtSchldBO1st = (Date) presenceReportDaily.getScheduleBO1st();
                 String sDtSchldBO1st = Formater.formatDate(dtSchldBO1st, "HH:mm");
                 
                Date dtSchldBO2nd = (Date) presenceReportDaily.getScheduleBO2nd();
                 String sDtSchldBO2nd = Formater.formatDate(dtSchldBO2nd, "HH:mm");
                 
                 Date dtSchldBI1st = (Date) presenceReportDaily.getScheduleBI1st();
                 String sDtSchldBI1st = Formater.formatDate(dtSchldBI1st, "HH:mm");
                   
                  Date dtSchldBI2nd = (Date) presenceReportDaily.getScheduleBI2nd();
                  String sDtSchldBI2nd = Formater.formatDate(dtSchldBI2nd, "HH:mm");
                   
                  String strScheduleDesc1st = (String) presenceReportDaily.getScheduleDesc1st();
                  String strScheduleDesc2nd = (String) presenceReportDaily.getScheduleDesc2nd();
                
               Hashtable reasonMap = PstReason.getReason(0, 500, "", PstReason.fieldNames[PstReason.FLD_REASON]); 
               String strNote = presenceReportDaily.getNote1nd().equals("") ? "-" : presenceReportDaily.getNote1nd();
               String strStatus = PstEmpSchedule.strPresenceStatus[presenceReportDaily.getStatus1()] !=null ? PstEmpSchedule.strPresenceStatus[presenceReportDaily.getStatus1()] : "-";
               String strSymStatus = PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] !=null ? PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] : "-";
               String  pSelectedDate = Formater.formatDate( presenceReportDaily.getSelectedDate(), "yyyy-MM-dd");
               //untuk yg 2nd
                int intScheduleCategory2nd = presenceReportDaily.getSchldCategory2nd();
                Date dtSchldIn2nd = (Date) presenceReportDaily.getScheduleIn2nd();
                Date dtSchldOut2nd = (Date) presenceReportDaily.getScheduleOut2nd();
                Date dtActualIn2nd = (Date) presenceReportDaily.getActualIn2nd();
                Date dtActualOut2nd = (Date) presenceReportDaily.getActualOut2nd();
                ///update by satrya 2012-07-23
                //hide by satrya 2013-01-23 di karenakan belum di temukan kenapa status2nd -1
               //String strNote2nd = presenceReportDaily.getNote2nd().equals("") ? "-" : presenceReportDaily.getNote2nd();
               // String strStatus2nd =PstEmpSchedule.strPresenceStatus[presenceReportDaily.getStatus2()] !=null ? PstEmpSchedule.strPresenceStatus[presenceReportDaily.getStatus2()] :"-";
                  String strSymStatus2nd = PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] !=null ? PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] : "-";
                Vector rowx = new Vector();
                Vector rowxPdf = new Vector();

                // ---> SPLIT SHIFT / EOD SCHEDULE					
                //if(intScheduleCategory==PstScheduleCategory.CATEGORY_SPLIT_SHIFT)
                //update by satrya 2012-08-19
                String inputName = "" + presenceReportDaily.getEmpScheduleId() + "_d_" + presenceReportDaily.getSelectedDate().getDate() + "_d_" + presenceReportDaily.getSelectedDate().getTime()+ "_d_" + presenceReportDaily.getEmpId()+ "_d_"+ presenceReportDaily.getEmpNum();
                //String inputName = "" + presenceReportDaily.getEmpScheduleId() + "_d_" + presenceReportDaily.getSelectedDate().getDate(); 
                String inputName2nd = "" + presenceReportDaily.getEmpScheduleId() + "_d2nd_" + presenceReportDaily.getSelectedDate().getDate();
               
                //end
                String payCompCode = PayComponent.COMPONENT_INS; 
                String bOut =""; 
                String bIn = "";
                String dBout = "";
                String dBin = ""; 
               
                //menginisialisasikan variable untuk overtime
                String insentif ="-";
                int oVForm =-1;
                int allwance =-1;
                int paid =-1 ;
                long ovId=0;
                String   NetOv ="-";
                String oVerIdx= "-";
                long preBreakOut = 0;
                long preBreakIn = 0;
                long breakDuration =0L;
                
                //menginisialisasikan variable untuk Holiday
                String daysHolidayName = ""; 
                String cmdDaysHoliday="";
                 Presence presenceBreak = new Presence(); 
               // HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(selectedDateFrom, selectedDateTo); 
                if(holidaysTable.isHoliday(religion_id !=0 ? religion_id : 0, presenceReportDaily.getSelectedDate())){
                   daysHolidayName = holidaysTable.getDescHoliday(religion_id,presenceReportDaily.getSelectedDate());
                }
                 if(daysHolidayName !=null && daysHolidayName.length()>0){
                       cmdDaysHoliday = "<p class=\"masterTooltip\"><abbr title=\""+daysHolidayName+"\"><font color=\"#FF0000\">"+"Libur<br>"+dayStringColor+",<br>"+dateStringColor+"</font></p>"; 
                 }else{
                        cmdDaysHoliday = dayStringColor+",<br>"+dateStringColor+"&nbsp;";
                 }
                //update by satrya 2012-10-10 
                 if(listPresencePersonalInOut!=null && listPresencePersonalInOut.size() > 0 ){
                         Date dtSchDateTime = null; 
                         Date dtpresenceDateTime = null;
                         Date dtSchEmpScheduleBIn = null;
                         Date dtSchEmpScheduleBOut = null;
                         long preBreakOutX=0;
                         long preBreakInX=0;
                         Date dtBreakOut=null; 
                         Date dtBreakIn=null;
                         boolean ispreBreakOutsdhdiambil = false; 
                     for(int bIdx = 0;bIdx < listPresencePersonalInOut.size();bIdx++){
                         presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                         //update by satrya 2012-10-17
                         if(presenceReportDaily.getScheduleBO1st()!=null){
                             dtSchEmpScheduleBOut = (Date) presenceReportDaily.getScheduleBO1st().clone();
                             dtSchEmpScheduleBOut.setHours(dtSchEmpScheduleBOut.getHours());
                             dtSchEmpScheduleBOut.setMinutes(dtSchEmpScheduleBOut.getMinutes());
                            dtSchEmpScheduleBOut.setSeconds(0);
                         }
                         if(presenceReportDaily.getScheduleBI1st()!=null){
                             dtSchEmpScheduleBIn = (Date) presenceReportDaily.getScheduleBI1st().clone();
                             dtSchEmpScheduleBIn.setHours(dtSchEmpScheduleBIn.getHours());
                             dtSchEmpScheduleBIn.setMinutes(dtSchEmpScheduleBIn.getMinutes());
                            dtSchEmpScheduleBIn.setSeconds(0);
                         }
                         if(presenceBreak.getScheduleDatetime()!=null){
                            dtSchDateTime = (Date)presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0);                            
                         }
                         if(presenceBreak.getPresenceDatetime() !=null){ 
                                //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);       
                         }
                         if(presenceBreak.getEmployeeId()==presenceReportDaily.getEmpId() 
                                  && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),presenceReportDaily.getSelectedDate())==0 )
                                  && presenceBreak.getScheduleDatetime()== null ){ 
                              if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+"<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                  dBout = bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                              else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ "<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                   dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                             
                          }
                         else if(presenceBreak.getScheduleDatetime() !=null
                                 && presenceBreak.getEmployeeId()==presenceReportDaily.getEmpId()
                                 &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),presenceReportDaily.getSelectedDate())==0 )){
                             //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                             if(presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                  //update by satrya 2012-09-27
                                 //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                 //update by satrya 2013-07-28
                                      
                                  //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                  //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                   preBreakOutX  = dtpresenceDateTime==null?0:dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                  dtBreakOut = dtpresenceDateTime; 
                                  if(dtSchEmpScheduleBIn!=null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()){
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();
                                  }
                                  else if((presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){ ///jika karyawan mendahului istirahat
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
                                    
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakOut = presenceBreak.getPresenceDatetime().getTime();//jika schedulenya 00:00
                                  }
                                  else{
                                       preBreakOut = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PO
                                  }
                                 
                                  ispreBreakOutsdhdiambil = false; 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){
                                  //istirahat terlamabat 
                                   preBreakInX = presenceBreak.getPresenceDatetime()==null?0:presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                   dtBreakIn = presenceBreak.getPresenceDatetime();
                                 if(preBreakOut !=0L){   
                                  //update by satrya 2012-09-27
                                    //if(presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){
                                     //update by satrya 2013-07-28\
                                    //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                  if(dtSchEmpScheduleBIn!=null && dtBreakOut!=null && dtBreakIn!=null &&
                                          dtBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()){
                                      //karena sudah pasti melewatijam istirahatnya
                                     long  tmpBreakDuration = ((Long)breakTimeDuration.get(""+presenceReportDaily.getScheduleId1())).longValue();
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime() + tmpBreakDuration;
                                  }   
                                  else if(presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){ ///jika karyawan melewati jam istirahat
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakIn = presenceBreak.getPresenceDatetime().getTime(); //jika schedulenya 00:00 
                                  }
                                  else{
                                      preBreakIn = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PI
                                  }
                                  
                                   breakDuration = breakDuration + (preBreakIn -  preBreakOut);
                                
                                 
                                 ispreBreakOutsdhdiambil = true;
                                   preBreakOut =0L;
                                   
                                    //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                   // preBOut=null;
                                  }
                                 // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());
                                 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+"<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                   dBout = dBout+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                              } else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ "<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                  dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");  
                                  listPresencePersonalInOut.remove(bIdx);                              
                                   bIdx=bIdx-1;
                                 
                              }
                             
                             if(ispreBreakOutsdhdiambil){
                                   //cek da cuti
                                Vector vLisOverlapCuti  = SessLeaveApp.checkOverLapsLeaveTaken(lEmpId, dtBreakOut,dtBreakIn);
                                if(vLisOverlapCuti!=null && vLisOverlapCuti.size()>0){
                                      for(int idxcuti=0; idxcuti< vLisOverlapCuti.size();idxcuti++){
                                          LeaveCheckTakenDateFinish leaveCheckTaken = (LeaveCheckTakenDateFinish)vLisOverlapCuti.get(idxcuti);
                                          if(leaveCheckTaken.getTakenDate()!=null && dtBreakOut!=null
                                                  && preBreakOutX < leaveCheckTaken.getTakenDate().getTime() 
                                                  && preBreakOutX < dtSchEmpScheduleBOut.getTime()){
                                                bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                                          }else{
                                              bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br>";
                                          }

                                          if(dtSchEmpScheduleBIn!=null && dtBreakIn!=null 
                                                  && preBreakInX > leaveCheckTaken.getFinishDate().getTime() 
                                                  && preBreakInX > dtSchEmpScheduleBIn.getTime()){

                                               bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                                               
                                          }else{
                                              bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br>";
                                          }
                                        
                                          vLisOverlapCuti.remove(idxcuti);                              
                                            idxcuti=idxcuti-1;
                                              break;
                                      }
                                }//end cek cuti
                                else{
                                    if(preBreakOutX < dtSchEmpScheduleBOut.getTime() || preBreakOutX > dtSchEmpScheduleBIn.getTime()){
                                          bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                                    }else{
                                        bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br>";
                                    }
                                    if(preBreakInX > dtSchEmpScheduleBIn.getTime()){
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                                    }else{
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br>";
                                    }
                                }
                                 //update by satrya 2013-06-17
                                   preBreakOutX=0L;
                                   preBreakInX=0L;
                             }
                          // }
                              
                         }//end else if
                           
                     }//end for break In
                          if(preBreakOutX==0 || preBreakInX==0){ 
                                 //jika hanya satu saja yg muncul atau ada misalnya hanya break IN saja atau break Out saja
                                 //update by satrya 2013-06-17
                               if(preBreakOutX!=0){
                                 if(preBreakOutX < dtSchEmpScheduleBOut.getTime() || preBreakOutX > dtSchEmpScheduleBIn.getTime()){
                                          bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                                    }else{
                                        bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br>";
                                    }
                              }
                              if(preBreakInX!=0){
                                    if(preBreakInX > dtSchEmpScheduleBIn.getTime()){
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                                    }else{
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br>";
                                    }
                             }
                             }
                         //update by satrya 2012-10-18
                             //jika di loop tersebut tidak cocok maka di kurangi schedulenya
                                if(breakDuration ==0 && presenceReportDaily.getScheduleId1()!=0 && breakTimeDuration.get(""+presenceReportDaily.getScheduleId1()) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                                        try{                          
                                         breakDuration = ((Long)breakTimeDuration.get(""+presenceReportDaily.getScheduleId1())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                                        }catch(Exception ex){
                                            System.out.println("Exception scheduleSymbol"+ex.toString());
                                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                                        }
                                      }
                    }
                   //jika employee tidak ada yang keluar maka akan di potong jam istirahat default
                  else{
                    if(breakDuration ==0 && presenceReportDaily.getScheduleId1()!=0 && breakTimeDuration.get(""+presenceReportDaily.getScheduleId1()) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                        try{                          
                         breakDuration = ((Long)breakTimeDuration.get(""+presenceReportDaily.getScheduleId1())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                        }catch(Exception ex){
                            System.out.println("Exception scheduleSymbol"+ex.toString());
                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                        }
                      } 
                  }
                     //list Overtime
                 // sementara belum di pakai
                 //TmpOvertimeReportDaily  tmtmpOvertimeReportDaily = new TmpOvertimeReportDaily(); 
                if(showOvertime==0){
                      pSelectedDate = Formater.formatDate( presenceReportDaily.getSelectedDate(), "yyyy-MM-dd");
                     if(listOvertimeDaily!=null && listOvertimeDaily.size()> 0){  
                     for(int oVx = 0;oVx < listOvertimeDaily.size();oVx++){
                         OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDaily.get(oVx);
                         
                         String pdateOv = Formater.formatDate(overtimeDetail.getDateFrom(), "yyyy-MM-dd");
                         if(overtimeDetail.getOID() !=0 && overtimeDetail.getEmployeeId()==presenceReportDaily.getEmpId() 
                               && pdateOv.equals(pSelectedDate)){
                             ovId= overtimeDetail.getOID();
                            oVForm = overtimeDetail.getStatus();
                            //I_DocStatus.fieldDocumentStatusShort[overtimeDetail.getStatus()];
                            //update by satrya 2012-10-31
                            if(overtimeDetail.getStatus()==I_DocStatus.DOCUMENT_STATUS_PROCEED){
                                allwance = overtimeDetail.getAllowance(); 
                            //Overtime.allowanceType[overtimeDetail.getAllowance()]; 
                            }
                            paid = overtimeDetail.getPaidBy();
                            //OvertimeDetail.paidByKey[overtimeDetail.getPaidBy()];
                            if(overtimeDetail.getNetDuration() !=0.0){
                                NetOv = Formater.formatNumber(overtimeDetail.getRoundDuration(), "###.##");  
                                //NetOv = Formater.formatNumber(overtimeDetail.getDuration(), "###.##");  
                            }
                            if(overtimeDetail.getTot_Idx() != 0.0){
                                  oVerIdx = Formater.formatNumber(overtimeDetail.getTot_Idx(), "###.##");  ;
                            }
                              listOvertimeDaily.remove(oVx);                              
                              oVx=oVx-1;
                              break;
                         }
                     }
                    }
               }//end cek show overtime
                    String sSymbol =""; 
                        if(strSchldSymbol1 !=null && strSchldSymbol1.length() >0){
                            
                              if(Al_oid != presenceReportDaily.getScheduleId1() && LL_oid != presenceReportDaily.getScheduleId1() && DP_oid != presenceReportDaily.getScheduleId1()){
                                  if(iLeaveMinuteEnable !=1){
                                  sSymbol ="<p title =\""+strSchINOut+"&nbsp;["+sDtSchldBO1st+"&nbsp;"+sDtSchldBI1st+"] \">"+strSchldSymbol1.toUpperCase()+"</p>";  
                                         ;// +"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                        
                                    }else{  
                                      if(sSymbolLeave !=null && sSymbolLeave.length() > 0){
                                          sSymbol ="<p title =\""+strSchINOut+"&nbsp;["+sDtSchldBO1st+"&nbsp;"+sDtSchldBI1st+"]\">"+ strSchldSymbol1.toUpperCase() +"</p>"+" / <B>"+sSymbolLeave+"</B>"
                                                  ;// +"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                      }
                                      else{
                                          sSymbol ="<p title =\""+strSchINOut+"&nbsp;["+sDtSchldBO1st+"&nbsp;"+sDtSchldBI1st+"]\">"+ strSchldSymbol1.toUpperCase() +"</p>"
                                                    
                                               ;//+"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                      }                            
                                    }                            
                            } else{ 
                                  if(iLeaveMinuteEnable !=1){
                                  sSymbol = strSchldSymbol1 ;
                                   
                                    }else
                                    {
                                     sSymbol =
                                             "<p title =\""+strSchINOut+"&nbsp;["+sDtSchldBO1st+"&nbsp;"+sDtSchldBI1st+"]\">"+ strSchldSymbol1.toUpperCase() +"</p>"+" / <B>"+sSymbolLeave+"</B>" 
                                            ;//  +"<a href=\"#\" id=\"clueTipBox\" class=\"clueTipBox\" title=\"Title Text|This box will open when text box get focus OR mouse hover on 'Hint' text.\">Hint</a>";
                                    }
                             }
                        }
                    else{
                          //sSymbol ="<input onblur=\"javascript:checkSymbol(this)\" type=\"text\"  name=\"" + inputName + "_symbol\" value=\"" + strSchldSymbol1 + "\"  size=\"3\">";
                          sSymbol="<p title =\""+strSchINOut+"&nbsp;[00:00 &nbsp; 00:00]\">"+ strSchldSymbol1.toUpperCase() +"</p>";
                        }
                  
                   ///command script
                   String cmdLeaveSript ="";
                  
                   //untuk schedule 1
                    if(presenceReportDaily.getScheduleId1() !=0){
                        //update by satrya 2013-04-14
                       if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){//presenceReportDaily.getScheduleId1() == Al_oid && presenceReportDaily.getScheduleId1() != 0 || (sSymbolLeave!=null && sSymbolLeave.length()>0)){
                           //if(presenceReportDaily.getScheduleId1() == Al_oid && presenceReportDaily.getScheduleId1() != 0 || sSymbolLeave.equalsIgnoreCase("AL")){
                            if(leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                               //"<a href=\"javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "');\"><center>New</center></a>";
                             cmdLeaveSript = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#00000\"> [View]</font></a>";
                             if(listLeaveAplication!=null && listLeaveAplication.size()>0){
                              cmdLeaveSript = cmdLeaveSript + " <br><br> <a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                             }
                            }else{
                             cmdLeaveSript = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                             if(listLeaveAplication!=null && listLeaveAplication.size()>0){
                              cmdLeaveSript = cmdLeaveSript + " <br><br><a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                             }
                            }
                        }
                      
                         //jika statusnya DRAFF Dan To Be Approval
                        else if((leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT) || (leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE) || (leaveStatus ==PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED)){
                            cmdLeaveSript  = "<a href=javascript:cmdEditLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')><font color=\"#FF0000\"><blink>[Open]</<blink></font></a>";
                             if(listLeaveAplication!=null && listLeaveAplication.size()>0){
                              cmdLeaveSript = cmdLeaveSript + " <br><br> <a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                             }
                       }
                         //end
                         else{
                             cmdLeaveSript = "<a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";

                        }
                    }else{
                        cmdLeaveSript = "<a href=javascript:cmdNewLeave('" +  presenceReportDaily.getEmpId() + "','" + lDatePresence + "')>[New]</a>";
                    }
                   
                  
                    //untuk insentif 1st
                   //update by satrya 2013-07-26
                   boolean isInsentif=false;
                   if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                       isInsentif = payrollCalculatorConfig.checkPayrollComponent(payCompCode, presenceReportDaily.getEmpId(), presenceReportDaily.getDepartement_id(), presenceReportDaily.getSelectedDate(),holidaysTable.isHoliday(religion_id !=0 ? religion_id : 0, presenceReportDaily.getSelectedDate()), oidLeave, ovId, presenceReportDaily.getStatus1(), iPositionLevel, iPropInsentifLevel, strSchldSymbol1);
                   }
                 // ---> REGULAR SCHEDULE			
                {
//loop untuk b-Out / B-In
                    // calculate working duration
                     
                    String strDurationFirst = getWorkingDuration(dtActualIn1st, dtActualOut1st, breakDuration );

                    // process generate string time interval for selected schedule
                    String strDtSchldIn = Formater.formatTimeLocale(dtSchldIn1st);
                    String strDtSchldOut = Formater.formatTimeLocale(dtSchldOut1st);
                    boolean schedule1stWithoutInterval = false;
                    if (strDtSchldIn.compareTo(strDtSchldOut) == 0) {
                        strDtSchldIn = "-";
                        strDtSchldOut = "-";
                        schedule1stWithoutInterval = true;
                    }

                    // calculate diffence between schedule and actual
                    String strDiffIn1st = "-";
                    String strDiffOut1st = "-";
                    if (!schedule1stWithoutInterval) {
                        try { 
                            strDiffIn1st = getDiffIn(dtSchldIn1st, dtActualIn1st);
                        strDiffOut1st = getDiffOut(dtSchldOut1st, dtActualOut1st);
                        }catch(Exception ex){
                            System.out.println("exCeption Interval "+ex);
                        }
                    }
                     //untuk menampilkan sysmbol
                   //update by satrya 2012-08-11
                   String cmdEditAttendace = "";
                   String cmdEditAttendanceManual =  "";
                   
                   
                        
                   //untuk menampilkan status
                    String cbStatus = ControlCombo.draw(inputName + "_status", "elementForm", null, "" + presenceReportDaily.getStatus1(), statusVal, statusTxt, "disabled"); 
                    //update by satrya 2012-08-19
                    //untuk menampilkan reason
                 String cbReason = ControlCombo.drawTooltip(inputName + "_reason", "select...", "" + presenceReportDaily.getReasonNo1nd(), reason_value, reason_key, "disabled",reason_tooltip);
                 // String cbReason = ControlCombo.drawTooltip(inputName + "_reason", "select...", "" + presenceReportDaily.getReasonNo1nd(), reason_value, reason_key, "onkeydown=\"javascript:fnTrapKD()\"",reason_tooltip);
                   
               if(attdConfig!=null && attdConfig.getConfigurasiReportScheduleDaily()==I_Atendance.CONFIGURASI_I_REPORT_DAILY_SCHEDULE_AND_CEK_BOX_ADA_DIBELAKANG){    
                rowx.add(String.valueOf(start));
                //untuk menampilkan hari libur
                rowx.add(cmdDaysHoliday);
                //update by satrya 2012-09-06
                rowx.add(strPayrolNumber);
                rowx.add(strEmpFullName);
                 
                   
                 
                 rowx.add((dtActualIn1st != null) ? Formater.formatTimeLocale(dtActualIn1st, "d/M/yy") +"<br>"+Formater.formatTimeLocale(dtActualIn1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");
                    //menampilkan Break In dan break Out
                 rowx.add(bOut !=null && bOut.length() > 0 ? bOut : "-");
                 rowx.add(bIn !=null && bIn.length() > 0 ? bIn : "-");
                 rowx.add((dtActualOut1st != null) ? Formater.formatTimeLocale(dtActualOut1st, "d/M/yy")+"<br>"+ Formater.formatTimeLocale(dtActualOut1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");//untuk Time Out
                 if(strDiffIn1st !=null && strDiffIn1st.length() > 0){
                           rowx.add(strDiffIn1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffIn1st+"</font>" :strDiffIn1st );//untuk diference IN
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 if(strDiffOut1st !=null && strDiffOut1st.length() > 0){
                         rowx.add(strDiffOut1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffOut1st+"</font>" : strDiffOut1st );//untuk Diference OUT
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 //untuk Duration
                 if(strDurationFirst !=null && strDurationFirst.length() > 0){
                     rowx.add(strDurationFirst.indexOf("-")>-1 ? "<font color=\"#CC0000\"><center>"+strDurationFirst+"</center></font>" :"<font color=\"#000000\"><B><center>"+strDurationFirst+"</center></B></font>" ); //untuk durasi
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 //end duration
                 rowx.add(sSymbol +"<br>"+ (cmdAddAttd.length()>0?cmdAddAttd + " || ":""));
                 //untuk menampilkan status Leave
                 rowx.add("<center>"+cmdLeaveSript+"</center>");
                //update by satrya 2012-09-13
                // untuk colom insentif,overtime form,allowance,Paid,Net overtime,OT.index
                if(isInsentif){
                    insentif = "&#10004;";
                }else{
                    insentif = "";
                }
                //update by satrya 2013-07-025
                if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                        rowx.add("<center>"+insentif+"</center>");
                }
                if(showOvertime==0){ 
                    rowx.add(ControlCombo.draw(inputName + "_oVForm", "select...", ""+oVForm, PstOvertime.getValueOtForm(), PstOvertime.getKeyOtForm(), "disabled"));
                    rowx.add(ControlCombo.draw(inputName + "_allwance", "select...", ""+allwance, PstOvertime.getValueAllowance(), PstOvertime.getKeyAllowance(), "disabled"));
                    rowx.add(ControlCombo.draw(inputName + "_paid", "select...", ""+paid, OvertimeDetail.getPaidByVal(), OvertimeDetail.getPaidByKey(), "disabled")); 
                    rowx.add(""+NetOv);
                    rowx.add(""+oVerIdx);  
                }
                //rowx.add(!privUpdatePresence ? "" : ((presenceReportDaily.getScheduleId1() == Al_oid) || (presenceReportDaily.getScheduleId1() == LL_oid) || (presenceReportDaily.getScheduleId1() == DP_oid)) || (scheduleSymbolIdMap.containsKey(""+presenceReportDaily.getScheduleId1()))   
                rowx.add(""+cbStatus);
                rowx.add(""+cbReason);
                //untuk menampilkan note
                rowx.add(""+presenceReportDaily.getNote1nd());
                
              }else{
                 rowx.add(String.valueOf(start));
                //untuk menampilkan hari libur
                rowx.add(cmdDaysHoliday);
                //update by satrya 2012-09-06
                rowx.add(strPayrolNumber);
                rowx.add(strEmpFullName);
                
                   
                 rowx.add(sSymbol +"<br>"+ (cmdAddAttd.length()>0?cmdAddAttd + " || ":"") + cmdEditAttendace  +" || "+cmdEditAttendanceManual);
                 rowx.add((dtActualIn1st != null) ? Formater.formatTimeLocale(dtActualIn1st, "d/M/yy") +"<br>"+Formater.formatTimeLocale(dtActualIn1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");
                    //menampilkan Break In dan break Out
                 rowx.add(bOut !=null && bOut.length() > 0 ? bOut : "-");
                 rowx.add(bIn !=null && bIn.length() > 0 ? bIn : "-");
                 rowx.add((dtActualOut1st != null) ? Formater.formatTimeLocale(dtActualOut1st, "d/M/yy")+"<br>"+ Formater.formatTimeLocale(dtActualOut1st, "HH:mm")+"<br>" : "<font color=\"#CC0000\">"+"-"+"</font>");//untuk Time Out
                 if(strDiffIn1st !=null && strDiffIn1st.length() > 0){
                           rowx.add(strDiffIn1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffIn1st+"</font>" :strDiffIn1st );//untuk diference IN
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 if(strDiffOut1st !=null && strDiffOut1st.length() > 0){
                         rowx.add(strDiffOut1st.indexOf("-")>-1 ? "<font color=\"#CC0000\">"+strDiffOut1st+"</font>" : strDiffOut1st );//untuk Diference OUT
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 //untuk Duration
                 if(strDurationFirst !=null && strDurationFirst.length() > 0){
                     rowx.add(strDurationFirst.indexOf("-")>-1 ? "<font color=\"#CC0000\"><center>"+strDurationFirst+"</center></font>" :"<font color=\"#000000\"><B><center>"+strDurationFirst+"</center></B></font>" ); //untuk durasi
                 }else{
                       rowx.add("<font color=\"#CC0000\">"+"-"+"</font>");
                 }
                 //end duration
                 //untuk menampilkan status Leave
                 rowx.add("<center>"+cmdLeaveSript+"</center>");
                //update by satrya 2012-09-13
                // untuk colom insentif,overtime form,allowance,Paid,Net overtime,OT.index
                if(isInsentif){
                    insentif = "&#10004;";
                }else{
                    insentif = "";
                }
                //update by satrya 2013-07-025
                if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                        rowx.add("<center>"+insentif+"</center>");
                }
                if(showOvertime==0){ 
                    rowx.add(ControlCombo.draw(inputName + "_oVForm", "select...", ""+oVForm, PstOvertime.getValueOtForm(), PstOvertime.getKeyOtForm(), "disabled"));
                    rowx.add(ControlCombo.draw(inputName + "_allwance", "select...", ""+allwance, PstOvertime.getValueAllowance(), PstOvertime.getKeyAllowance(), "disabled"));
                    rowx.add(ControlCombo.draw(inputName + "_paid", "select...", ""+paid, OvertimeDetail.getPaidByVal(), OvertimeDetail.getPaidByKey(), "disabled")); 
                    rowx.add(""+NetOv);
                    rowx.add(""+ oVerIdx);  
                }
                rowx.add(""+cbStatus);
                rowx.add(""+cbReason);
                //untuk menampilkan note
                rowx.add(""+presenceReportDaily.getNote1nd());
                
              }     
                    
                   
                    
                   rowxPdf.add(String.valueOf(start));
                    //update by satrya
                    if(holidaysTable.isHoliday(religion_id !=0 ? religion_id : 0, presenceReportDaily.getSelectedDate())){
                        rowxPdf.add(Formater.formatTimeLocale(presenceReportDaily.getSelectedDate(), "EEEE, d/M/yyyy")+"."+"  ["+daysHolidayName+" ] ");
                    }
                    else{
                        rowxPdf.add(Formater.formatTimeLocale(presenceReportDaily.getSelectedDate(), "EEEE, d/M/yyyy"));
                    }
                    //update by satrya 2012-09-06
                    rowxPdf.add(strPayrolNumber);
                   // rowxPdf.add(strEmpFullName +" [ "+strPayrolNumber+" ] ");
                    rowxPdf.add(strEmpFullName);
                    rowxPdf.add(strSchldSymbol1);
                    rowxPdf.add((dtActualIn1st != null) ? Formater.formatTimeLocale(dtActualIn1st) : "-");
                    rowxPdf.add((dBout != null && dBout.length()>0) ? dBout : "-");//break out
                    rowxPdf.add((dBin != null && dBin.length()>0) ? dBin  : "-");//break in
                    rowxPdf.add((dtActualOut1st != null) ? Formater.formatTimeLocale(dtActualOut1st) : "-");
                    rowxPdf.add(strDiffIn1st);
                    rowxPdf.add(strDiffOut1st);
                    rowxPdf.add(strDurationFirst);
                   //update by satrya 2013-07-26
                   if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){ 
                    rowxPdf.add(insentif);
                   }
                 if(showOvertime==0){
                     
                    rowxPdf.add(oVForm==-1?"-":I_DocStatus.fieldDocumentStatus[oVForm]);
                    rowxPdf.add(allwance==-1?"-":Overtime.allowanceType[allwance]); 
                    rowxPdf.add(paid==-1?"-":OvertimeDetail.paidByKey[paid]);
                    rowxPdf.add(NetOv); 
                    rowxPdf.add(oVerIdx);
                  }
                    //update by satrya 2012-07-23
                    rowxPdf.add(presenceReportDaily !=null &&  reasonMap !=null && presenceReportDaily.getReasonNo1nd() !=0 ? (String) reasonMap.get(""+presenceReportDaily.getReasonNo1nd())  : "-" );
                    rowxPdf.add(strNote);
                    rowxPdf.add(strSymStatus); 
                }
                   //update by satrya 2012-10-15
                ctrlist.drawListRowJsVer2(outObj, 0, rowx, i);
               // lstData.add(rowx);r
                vectDataToPdf.add(rowxPdf);
               // int test = rowxPdf.size();
                }
           }catch(Exception ex){
               
               System.out.println("Exception presenceReportDaily : " +ex.toString());
               
           }
         }//end list

            result.add(String.valueOf(DATA_PRINT));
           //result.add("");
            //ctrlist.drawList(outObj, index);
            ctrlist.drawListEndTableJsVer2(outObj);
            result.add(vectDataToPdf);
        } else {
            result.add(String.valueOf(DATA_NULL));
            result.add("<div class=\"msginfo\">&nbsp;&nbsp;No attendance record found ...</div>");
            result.add(new Vector(1, 1));
        }

        return result;
    }

    public String getSelected(Section s, Vector secSelect) {
        if (secSelect != null && secSelect.size() > 0) {
            for (int i = 0; i < secSelect.size(); i++) {
                Section sec = (Section) secSelect.get(i);
                if (sec.getOID() == s.getOID()) {
                    return "checked";
                }
            }
        }
        return "";
    }


%>



<%
// get data from form request
    //ControlLine ctrLine = new ControlLine();
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    float minOvertimeHour = 0.0F;
try{
    String minOv = PstSystemProperty.getValueByName("MIN_OVERTM_DURATION");
    if(minOv!=null && minOv.length()>0){
        minOvertimeHour =  Float.parseFloat(minOv)/60f;
    }
 } 
catch(Exception exc){
     System.out.println("Exception"+exc);
 }
    //end
    //update by satrya 2012-07-13
    Date selectedDateFrom = FRMQueryString.requestDateVer3(request, "check_date_start");
    Date selectedDateTo = FRMQueryString.requestDateVer3(request, "check_date_finish");
    String empNum = FRMQueryString.requestString(request, "emp_number");
    String fullName = FRMQueryString.requestString(request, "full_name");
    //update by satrya 2012-09-28
    //String status1st = FRMQueryString.requestString(request, "status_schedule1st");
    
    String sStatusResign = FRMQueryString.requestString(request, "statusResign"); 
    int statusResign=0;
    if(sStatusResign!=null && sStatusResign.length()>0){
        statusResign = Integer.parseInt(sStatusResign); 
    }
    
    String[] stsEmpCategory = null; 
    int sizeCategory = PstEmpCategory.listAll()!=null ? PstEmpCategory.listAll().size():0;   
    stsEmpCategory = new String[sizeCategory]; 
    //Vector stsEmpCategorySel= new Vector(); 
    String stsEmpCategorySel = "";
    int maxEmpCat = 0; 
    for(int j = 0 ; j < sizeCategory ; j++){                
        String name = "EMP_CAT_"+j;
        String val = FRMQueryString.requestString(request,name);
        stsEmpCategory[j] = val;
        if(val!=null && val.length()>0){ 
           //stsEmpCategorySel.add(""+val); 
            stsEmpCategorySel = stsEmpCategorySel + val +",";
        }
        maxEmpCat++;
    }
    
    //update by satrya 2013-03-29
   String[] stsSchedule = null;
    String stsScheduleSel = "";
            stsSchedule = new String[PstEmpSchedule.strPresenceStatus.length]; 
            //Vector stsScheduleSel= new Vector(); 
            int maxStsSchedule = 0;
            
            for(int j = 0 ; j < PstEmpSchedule.strPresenceStatusIdx.length ; j++){                
                String name = "STS_SCH_"+PstEmpSchedule.strPresenceStatusIdx[j];
                String val = FRMQueryString.requestString(request,name);
                stsSchedule[j] = val;
                if(val!=null && val.length()>0){  
                   stsScheduleSel = stsScheduleSel + ""+PstEmpSchedule.strPresenceStatusIdx[j]+","; 
                }
                maxStsSchedule++;
            }
     
      String[] stsPresence = null;
            stsPresence = new String[Presence.STATUS_ATT_IDX.length]; 
            Vector stsPresenceSel= new Vector(); 
            int max1 = 0;
            
            for(int j = 0 ; j < Presence.STATUS_ATT_IDX.length ; j++){                
                String name = "ATTD_"+Presence.STATUS_ATT_IDX[j];
                String val = FRMQueryString.requestString(request,name);
                stsPresence[j] = val;
                if(val!=null && val.equals("1")){ 
                   stsPresenceSel.add(""+Presence.STATUS_ATT_IDX[j]); 
                }
                max1++;
            }
    //update by satrya 2013-04-08
    int reason_sts = FRMQueryString.requestInt(request, "reason_status");
    //String status2nd = FRMQueryString.requestString(request, "status_schedule2nd");         
    SessEmpSchedule sessEmpSchedule = new SessEmpSchedule(); 
    //FrmPresence frpresence = new FrmPresence();
    int iCommand = FRMQueryString.requestCommand(request);
    int iErrCode = FRMMessage.NONE;
    String Errmsg=""; 
    //update by satrya 2012-07-25
     int prevCommand = FRMQueryString.requestInt(request, "prev_command");
     //int limitStart = FRMQueryString.requestInt(request, "start");
     int vectSize = 0;
     int start = FRMQueryString.requestInt(request, "start");
    //String  whereClause ="";
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidSection = FRMQueryString.requestLong(request, "section");
    Date date = FRMQueryString.requestDate(request, "date");
    String strDate = FRMQueryString.requestString(request, "datesrc");
    String source = FRMQueryString.requestString(request, "source");
    ///deklarasi variable
     int recordToGet = 400;
      
    if (strDate != "") {
        Date datesrc = Formater.formatDate(strDate, "yyyy-MM-dd");
        date = datesrc;

    }

    Vector vct = new Vector(1, 1);


    String wh = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
    vct = PstSection.list(0, 0, wh, PstSection.fieldNames[PstSection.FLD_SECTION]);

//out.println(oidDepartment);

    Vector secSelect = new Vector(1, 1);
    Vector secOID = new Vector(1, 1);
    if (vct != null && vct.size() > 0) {
        for (int i = 0; i < vct.size(); i++) {
            Section section = (Section) vct.get(i);
            if (iCommand != Command.NONE && iCommand != Command.ADD) {
                int ix = FRMQueryString.requestInt(request, "chx_" + section.getOID());

                if (ix == 1) {
                    secSelect.add(section);
                    secOID.add("" + section.getOID());
                    //out.println("section.getOID()"+section.getOID());
                }
            } else {
                secSelect.add(section);
            }
        }
    }
//update by satrya 2012-10-04
/*     if (iCommand == Command.GOTO) {
               // sessEmpSchedule = new sessEmpSchedule(request, sessEmpSchedule);  
                //SessEmpSchedule.requestEntityObject(sessEmpSchedule); 
            } else { 
                try{
                    sessEmpSchedule =(SessEmpSchedule) session.getValue(SessEmpSchedule.SESS_SRC_EMPSCHEDULE+"REPORT");
                    if(sessEmpSchedule==null){
                        sessEmpSchedule = new SessEmpSchedule();
                    }
                  } catch(Exception exc){
                      //update by satrya 2012-10-05
                      System.out.println("Exception GoTo sessEmpSchedule in Presence Report Daily"+exc.toString());
                  }
             }*/
    String message=" Update Daily Report Schedule Success No.Payroll ";
    int updateSchedule=0;
    if (iCommand == Command.SAVE || iCommand == Command.POST || iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST) {
        //update by satrya 2012-08-20
        String userSelect[] = request.getParameterValues("userSelect");
        if (userSelect != null && userSelect.length > 0) {
            String schSymbol2nd ="";
            Long objSchId2nd = 0L;
             long oidSch1nd2nd = 0;
             String note2nd = "";
              int reason2nd = 0;
                int status2nd = 0;
                
            Hashtable symbolMap = PstScheduleSymbol.getSymbolMap(0, 500, "", PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]); 
            for (int i = 0; i < userSelect.length; i++) {
                try {
                   // System.out.println(userSelect[i]);
                    long oidScheduleEmp = Long.parseLong((userSelect[i].split("_")[0]));
                    
                    int dateSelect = Integer.parseInt((userSelect[i].split("_")[2]));
                    
                    Date dtSelect = new Date(Long.parseLong((userSelect[i].split("_")[4])));
                    long employeeIdSelect = Long.parseLong((userSelect[i].split("_")[6]));
                    String empNumSelect = userSelect[i].split("_")[8]; 
                    
                    String schSymbol = request.getParameter(userSelect[i] + "_symbol").toUpperCase(); 
                    Long objSchId = (Long) symbolMap.get(schSymbol); 
                    long oidSch1nd = objSchId.longValue();
                    String note = request.getParameter(userSelect[i] + "_note");
                    int reason = FRMQueryString.requestInt(request, (userSelect[i] + "_reason"));
                    int status = FRMQueryString.requestInt(request, (userSelect[i] + "_status"));
                    
                    //update by satrya 2013-01-31
                    int oVForm = FRMQueryString.requestInt(request, (userSelect[i] + "_oVForm"));
                    int allowance = FRMQueryString.requestInt(request, (userSelect[i] + "_allwance"));
                    int paid = FRMQueryString.requestInt(request, (userSelect[i] + "_paid"));
                    String sNetOv = FRMQueryString.requestString(request, (userSelect[i] + "_NetOv"));
                    String sOverIdx = FRMQueryString.requestString(request, (userSelect[i] + "_oVerIdx"));
                    
                    String sOvId = FRMQueryString.requestString(request, (userSelect[i] + "_ovId"));
                    long ovId=0; 
                    if(sOvId!=null && sOvId.length()>0){
                        ovId = Long.parseLong(sOvId);
                    }
                    //gunanya itu di setting mnanual
                    int flagStatus= 2;// 2 artinya di set manualy lewat attd daily
                    double dNetOv=0;
                    double dOverIdx=0;
                    if(sNetOv.equalsIgnoreCase("-")){
                        sNetOv = "0";
                    }
                    if(sNetOv!=null && sNetOv.length()>0 && !sNetOv.equalsIgnoreCase("-")){
                        dNetOv = Double.parseDouble(sNetOv);
                    }
                    if(sOverIdx!=null && sOverIdx.length()>0 && !sOverIdx.equalsIgnoreCase("-")){
                        dOverIdx = Double.parseDouble(sOverIdx);
                    }
                    //untuk yg  schedule2nd
                    if(schSymbol2nd !=""){
                   schSymbol2nd = request.getParameter(userSelect[i] + "_symbol2nd").toUpperCase(); 
                    objSchId2nd = (Long) symbolMap.get(schSymbol2nd); 
                   oidSch1nd2nd = objSchId2nd.longValue();
                   note2nd = request.getParameter(userSelect[i] + "_note2nd");
                   reason2nd = FRMQueryString.requestInt(request, (userSelect[i] + "_reason2nd"));
                    status2nd = FRMQueryString.requestInt(request, (userSelect[i] + "_status2nd"));
                     
                    //PstEmpSchedule.updateScheduleByAbsenceDaily(oidScheduleEmp, dateSelect, oidSch1nd,status, reason, note,oidSch1nd2nd,status2nd,reason2nd,note2nd);
                    }
                     //PstEmpSchedule.updateScheduleByAbsenceDaily(oidScheduleEmp, dateSelect, oidSch1nd,status, reason, note);
                    // update by satrya 2013-04-19
                  if(source!=null && source.length()>0 && source.equalsIgnoreCase("analyzePresence")){
                      //di update dulu,lalu di analyze
                       updateSchedule = PstEmpSchedule.updateScheduleByAbsenceDaily(oidScheduleEmp, dateSelect, oidSch1nd,status, reason, note,oidSch1nd2nd,status2nd,reason2nd,note2nd);
                       
                      SessPresence.analysePresenceManual (dtSelect, employeeIdSelect);
                      if(updateSchedule !=0){
                       message = message + empNumSelect +"," ;
                    }
                    //PresenceAnalyser.analyzePresencePerEmployeeByEmployeeId(dtSelect, employeeIdSelect);
                  }else if(source!=null && source.length()>0 && source.equalsIgnoreCase("updateSchedule")){
                    //int updateLeaveReason = PstLeaveApplication.setReasonIdToLeaveOrExcuse(dtSelect, employeeIdSelect, reason);  
                   updateSchedule = PstEmpSchedule.updateScheduleByAbsenceDaily(oidScheduleEmp, dateSelect, oidSch1nd,status, reason, note,oidSch1nd2nd,status2nd,reason2nd,note2nd);
                   //PresenceAnalyser.analyzePresencePerEmployeeByEmployeeId(dtSelect, employeeIdSelect);
                   // if(updateLeaveReason!=0){  
                    //   message = message + " Update Leave Reason Success No.Payroll" + empNumSelect +",";
                   //}
                    if(updateSchedule !=0){
                       message = message + empNumSelect +"," ;
                    }
                  }
                    //update manual Overtime
                    //update by satrya 2013-02-03
                if(iCommand == Command.POST){ 
                  if(ovId!=0){
                   
                    PstOvertimeDetail.updateOTDetail(ovId, oVForm, allowance, paid, dNetOv, dOverIdx,flagStatus);
                    OvertimeDetail overtimeDetail = PstOvertimeDetail.fetchExc(ovId);
                    if(overtimeDetail.getPaidBy()==OvertimeDetail.PAID_BY_SALARY){  
                        //SessOvertime.calcOvTmIndex(overtimeDetail, true, minOvertimeHour); 
                        DpStockManagement dpStock = new DpStockManagement(); 
                        dpStock.setEmployeeId(overtimeDetail.getEmployeeId());
                        // kenapa di pakai empTime.getOID() supaya  spesific masing" overtime detail dp_stoc yg di generate,di karenakan jika memakai empTime.GetPeriodId , jika karyawan tersebut OT di period yg sama maka nantinya bisa terhapus
                        dpStock.setLeavePeriodeId(overtimeDetail.getOID());
                        dpStock.setDtOwningDate(overtimeDetail.getRealDateFrom());
                        long oid =PstDpStockManagement.deleteByPeriodId(dpStock);
                        //update by satrya 2012-12-20
                        if(oid==-1){
                            Errmsg = Errmsg +"<br>"+  "can't update paid by to salary, because the DP have been used "+overtimeDetail.getEmployee_num();
                            overtimeDetail.setPaidBy(OvertimeDetail.PAID_BY_DAY_OFF);
                        }
                     }else{
                         //empTime.setTot_Idx(0);// dibayar dengan day off
                         //SessOvertime.calcOvTmDayOff(empTime, reloadOvertimeIndexMap, minOvertimeHour); 
                         //insert DP
                         if((overtimeDetail.getStatus()==I_DocStatus.DOCUMENT_STATUS_FINAL || overtimeDetail.getStatus()==I_DocStatus.DOCUMENT_STATUS_PROCEED) 
                                 && overtimeDetail.getRealDateFrom()!=null && overtimeDetail.getRealDateTo()!=null ){                                                                             
                             DpStockManagement dpStock = new DpStockManagement(); 
                             dpStock.setDtOwningDate(overtimeDetail.getRealDateFrom());
                             dpStock.setDtExpiredDate(new Date(overtimeDetail.getRealDateFrom().getTime()+ ((30L*24L*60L*60L*1000L)*(long)leaveConfig.getDpValidity(leaveConfig.getStrLevels()[0]) )));
                             dpStock.setDtStartDate(overtimeDetail.getRealDateFrom());
                             dpStock.setEmployeeId(overtimeDetail.getEmployeeId());
                             // kenapa di pakai empTime.getOID() supaya  spesific masing" overtime detail dp_stoc yg di generate,di karenakan jika memakai empTime.GetPeriodId , jika karyawan tersebut OT di period yg sama maka nantinya bisa terhapus
                             dpStock.setLeavePeriodeId(overtimeDetail.getOID());                                                                       
                             dpStock.setQtyResidue((float)(overtimeDetail.getTot_Idx()/8f )); //empTime.getNetDuration()/8f));
                             dpStock.setiDpQty((float)(overtimeDetail.getTot_Idx()/8f)); // empTime.getNetDuration()/8f));
                             dpStock.setQtyUsed(0f); 
                             dpStock.setStNote("Dp generated from overtime by "+ userIsLogin.toLowerCase());
                             dpStock.setToBeTaken(0f);
                             if(overtimeDetail.getTot_Idx()>0){
                                 //update by satrya 2012-12-20
                                   if(overtimeDetail.getStatus()== I_DocStatus.DOCUMENT_STATUS_PROCEED){
                                        PstDpStockManagement.insertOrUpdateByPeriodId(dpStock);
                                   }
                             }else{
                               long oid = PstDpStockManagement.deleteByPeriodId(dpStock); 
                                //update by satrya 2012-12-20
                                /*if(oid==-1){
                                    msgStr = msgStr +"<br>"+ " can't update paid by to salary, because the DP have been used "+empTime.getEmployee_num();
                                     empTime.setPaidBy(OvertimeDetail.PAID_BY_DAY_OFF);
                                }*/
                             }
                         }
                     }
                    
                   }  
                 }
                    /*if(ovId!=0){
                        OvertimeDetail overtimeDetail = PstOvertimeDetail.fetchExc(ovId); 
                        PstOvertimeDetail.setManualOt(overtimeDetail, minOvertimeHour);
                    }*/
                 }catch (Exception exc) {
                    System.out.println("Exception can't report daily"+exc);
                   %>
                   <script language="JavaScript">
                       alert("Your input Is not Correct, Please try again");
                   </script>
                    <%   
                }
            }
            if(message!=null && message.length()>0){
                message = message.substring(0,message.length()-1);
            }
        } 
	
    }//untk save
//update by satrya 2013-04-09
      int showOvertime = 0;
    try{
        showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
    }catch(Exception ex){

        System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>" ); 
        showOvertime=0; 
    }
    //update by satrya 2013-07-08
        I_Atendance attdConfig = null;
               try {
                   attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
               } catch (Exception e) {
                   System.out.println("Exception : " + e.getMessage());
                   System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
               }
     Vector listAttendanceRecordDailly = new Vector(1, 1);
     Vector listPresencePersonalInOut = new Vector(1,1);
     Vector listOvertimeDaily = new Vector(1,1);
     String getEmployeePresence = "";
     HolidaysTable holidaysTable  = new HolidaysTable();
   // if(iCommand==Command.BACK || iCommand == Command.GOTO){
       
        //}
 
   //update by satrya 2013-04-3
   
   vectSize = SessEmpSchedule.getCountSessEmpSchedule(sessEmpSchedule,oidDepartment,selectedDateFrom,selectedDateTo,oidSection,empNum,fullName,stsScheduleSel,stsPresenceSel,reason_sts,stsEmpCategorySel,statusResign);	
try
                {	 
                    if(selectedDateFrom!=null && selectedDateTo!=null){
                        sessEmpSchedule.setDepartement(oidDepartment);
                        sessEmpSchedule.setEmpFullName(fullName);
                        sessEmpSchedule.setEmpNum(empNum);
                        sessEmpSchedule.setFromDate(selectedDateFrom);
                        sessEmpSchedule.setToDate(selectedDateTo);
                        sessEmpSchedule.setStatus1(stsScheduleSel);
                        sessEmpSchedule.setSection(oidSection);
                        sessEmpSchedule.setReasonSts(reason_sts); 
                      //vectSize = SessEmpSchedule.getCountSessEmpSchedule(sessEmpSchedule,sessEmpSchedule.getDepartement(),sessEmpSchedule.getFromDate(),sessEmpSchedule.getToDate(),sessEmpSchedule.getSection(),sessEmpSchedule.getEmpNum(),sessEmpSchedule.getEmpFullName(),sessEmpSchedule.getStatus1());
                   }

                }
                catch(Exception e){
                        sessEmpSchedule = new SessEmpSchedule();
                }
	if(iCommand==Command.FIRST|| iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST || iCommand == Command.SAVE || iCommand == Command.POST)
	{
		 
                CtrlEmpSchedule ctrlEmpSchedule = new CtrlEmpSchedule(request);	
		start = ctrlEmpSchedule.actionList(iCommand, start, vectSize, recordToGet);
                 sessEmpSchedule.setStart(start);
                session.putValue(SessEmpSchedule.SESS_SRC_EMPSCHEDULE, sessEmpSchedule);
                
	}
        
        
        if(iCommand==Command.REFRESH){
           try
                    {	 
                      sessEmpSchedule = (SessEmpSchedule)session.getValue(SessEmpSchedule.SESS_SRC_EMPSCHEDULE); 
                       /*if (sessEmpSchedule == null){
                                    sessEmpSchedule = new SessEmpSchedule();
                       }*/
                 }catch(Exception e){
                            //sessEmpSchedule = new SessEmpSchedule();
                     System.out.println("sessEmpSchedule is null");
                 }
        }

if(iCommand==Command.LIST || iCommand==Command.REFRESH || iCommand==Command.FIRST|| iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST || iCommand == Command.SAVE || iCommand == Command.POST){
	// list record yang sesuai 	
	//vectListAL = SessLeaveManagement.listSummaryAlStockInt(srcLeaveManagement, start, recordToGet); 
          String order = "DATE("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, "
                        + "TIME("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, " 
                        + PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]
                        + " , "+ PstPresence.fieldNames[PstPresence.FLD_STATUS] + " ASC ";  

          if(sessEmpSchedule!=null){
	  listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(sessEmpSchedule.getDepartement(), sessEmpSchedule.getFromDate(), sessEmpSchedule.getToDate(),
                 sessEmpSchedule.getSection(), sessEmpSchedule.getEmpNum().trim(),  sessEmpSchedule.getEmpFullName().trim(),
                  sessEmpSchedule.getStatus1(), sessEmpSchedule==null && sessEmpSchedule.getStart() == 0 ? start : sessEmpSchedule.getStart(), recordToGet,stsPresenceSel,sessEmpSchedule.getReasonSts(),stsEmpCategorySel,statusResign);
                 
                 
          }else{
              listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(oidDepartment, selectedDateFrom, selectedDateTo, oidSection, empNum, fullName, stsScheduleSel, start, recordToGet,stsPresenceSel,reason_sts,stsEmpCategorySel,statusResign);      
          }
          //Vector listPresencePersonalInOut = null;
         if(sessEmpSchedule!=null){
          //listPresencePersonalInOut =  PstPresence.list(vectSize , recordToGet, order, sessEmpSchedule.getDepartement(), sessEmpSchedule.getEmpFullName().trim()
            //      , sessEmpSchedule.getFromDate(), sessEmpSchedule.getToDate(), sessEmpSchedule.getSection(), sessEmpSchedule.getEmpNum().trim());
           listPresencePersonalInOut =  PstPresence.list(0 , 0, order, sessEmpSchedule.getDepartement(), sessEmpSchedule.getEmpFullName().trim() 
                , sessEmpSchedule.getFromDate(), sessEmpSchedule.getToDate(), sessEmpSchedule.getSection(), sessEmpSchedule.getEmpNum().trim(),stsPresenceSel,stsEmpCategorySel,statusResign);//di tambahkan stsPresenceSel
          
         }else{
          // listPresencePersonalInOut =  PstPresence.list(vectSize , recordToGet, order, oidDepartment, fullName.trim(), 
            //  selectedDateFrom, selectedDateTo, oidSection, empNum.trim());
           
            listPresencePersonalInOut =  PstPresence.list(0 , 0, order, oidDepartment, fullName.trim(), 
              selectedDateFrom, selectedDateTo, oidSection, empNum.trim(),stsPresenceSel,stsEmpCategorySel,statusResign); 
         }
          //update by satrya 2012-09-13
      if(showOvertime==0){
         if(sessEmpSchedule!=null){
          listOvertimeDaily = PstOvertimeDetail.listOvertime(0, 0, sessEmpSchedule.getDepartement(), sessEmpSchedule.getEmpFullName().trim(),
                  sessEmpSchedule.getFromDate(), sessEmpSchedule.getToDate(), sessEmpSchedule.getSection(), sessEmpSchedule.getEmpNum().trim(), "",stsPresenceSel);
         }else{
          listOvertimeDaily = PstOvertimeDetail.listOvertime(0, 0, oidDepartment, fullName.trim(), 
                  selectedDateFrom, selectedDateTo, oidSection, empNum.trim(), "",stsPresenceSel); 
         }
     }
          //update by satrya 2012-09-18
        //  Vector listHoliday =  PstPublicHolidays.getHoliday(selectedDateFrom, selectedDateTo);
        if(sessEmpSchedule!=null){
          holidaysTable = PstPublicHolidays.getHolidaysTable(sessEmpSchedule.getFromDate(), sessEmpSchedule.getToDate());
        }else{
               holidaysTable = PstPublicHolidays.getHolidaysTable(selectedDateFrom, selectedDateTo);
        }

        
   }//list
           //Vector listStrukturEmp = PstDepartment.listStruktur(empNum, fullName);
          /* if((iCommand == Command.LIST) || (iCommand == Command.SAVE)){
        listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(oidDepartment, selectedDateFrom, selectedDateTo, oidSection, empNum, fullName);
      
   }*/	

%>
<%
String[][] information = {
        {"jika tugas kantor dari rumah dan full keluar, maka tambahkan <strong>attendance dan pilih Add</strong>:",
         "a. tambahkan lah Jam kerja In.",
         "b. tambahkanlah Jam kerja Out.",
         "c. lalu buka di Edit Duty.",
         "pilihlah Status In tersebut sebagai Out On Dutty.",
         "pilihlah Status Out sebagai Call Back.",
         "d. untuk Out On dutty artinya mulai tugas kantor.",
         "e. untuk Call Back artinya karywan telah kembali dari tugas kantor.",
         "jika tugas mulai dari kantor maka pilihlah Edit Duty:",
         "a.rubahlah Out Personal menjadi Out On Dutty.",
         "b.rubahlah In Personal menjadi call Back.",
         "lalu pilihlah tombol Update Daily Presence atau Update & Analyze Daily Presence"
         },
         //english
        {"if dutty from house and full dutty, then <strong>add attendance and select button Add</strong>",//question
         "a. please insert attendance IN.",//selected1
         "b. please insert attendance Out.",//selected2
         "please open button Edit Duty.",//selected3
         "please select Status In and change  Out On Dutty.",//child1
         "please select Status Out and change  call back.",//child2
         "d. for Out On dutty is start on dutty.",//selected4
         "e. for Call Back is employee back on dutty",//selected5
         "if dutty start from office, please select Edit Duty button:",//question2
         "a.please change Out Personal without Out On Dutty.",//selected6
         "b.please change  In Personal without call Back.",////selected7
         "please select button Update Daily Presence Or Update & Analyze Daily Presence"
         }
    };
int language=CtrlEmpSchedule.LANGUAGE_DEFAULT; 
int question1=0;

int selected1=1;
int selected2=2;
int selected3=3;

int child1=4;
int child2=5;

int selected4=6;
int selected5=7;

int question2=8;
int selected6=9;
int selected7=10;

int question3=11;
%>
<!-- End of JSP Block -->
<html> 
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head> 
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Report Presence Daily Per Department</title>
        <!--update by satrya 2012-08-12 -->
        <script type="text/javascript" src="../../javascripts/jquery.min-1.6.2.js"></script>

<style type="text/css">
.tooltip {
	display:none;
	position:absolute;
	border:1px solid #333;
	background-color:#161616;
	border-radius:5px;
	padding:10px;
	color:#fff;
	font-size:12px Arial;
}
</style>
<style type="text/css">
      
            .bdr{border-bottom:2px dotted #0099FF;}
		
		.highlight {
			color: #090;
		}
		
		.example {
			color: #08C;
			cursor: pointer;
			padding: 4px;
			border-radius: 4px;
		}
		
		.example:after {
			font-family: Consolas, Courier New, Arial, sans-serif;
			content: '?';
			margin-left: 6px;
			color: #08C;
		}
		
		.example:hover {
			background: #F2F2F2;
		}
		
		.example.dropdown-open {
			background: #888;
			color: #FFF;
		}
		
		.example.dropdown-open:after {
			color: #FFF;
		}
		
	</style>
<script type="text/javascript">
$(document).ready(function() {
        // Tooltip only Text
        $('.masterTooltip').hover(function(){
                // Hover over code
                var title = $(this).attr('title');
                $(this).data('tipText', title).removeAttr('title');
                $('<p class="tooltip"></p>')
                .text(title)
                .appendTo('body')
                .fadeIn('fast');
        }, function() {
                // Hover out code
                $(this).attr('title', $(this).data('tipText'));
                $('.tooltip').remove();
        }).mousemove(function(e) {
                var mousex = e.pageX + 20; //Get X coordinates
                var mousey = e.pageY + 10; //Get Y coordinates
                $('.tooltip')
                .css({ top: mousey, left: mousex })
        });
});
</script>
        <script language="JavaScript">

            function fnTrapKD(){
                if (event.keyCode == 13) {
                    //document.all.aSearch.focus();
                    cmdView();
                }
            }
                      //update by satrya 2012-08-21
        <%
            Vector listScheduleSymbol = new Vector(1, 1);
            listScheduleSymbol = PstScheduleSymbol.list(0, 500, "", PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]);

        %>
//script untuk cek resolusi browser yg di gunakan
  /*if (window.screen){
var w = screen.width;
tmt_url_640 = "presence_report_daily_20130522.jsp";
    if(w>=1250){
    self.location.replace(tmt_url_640);
    }
}*/
function cmdAddAttd(employeeOid,lDateFrom){
  var linkPage = "<%=approot%>/employee/presence/presence_edit.jsp?"+"source=attdReportDaily"+"&FRM_FIELD_EMPLOYEE_ID="+employeeOid+"&lDateFrom="+lDateFrom+"&privAdd="+true+"&privUpdate="+true+"&command=<%=(Command.ADD)%>";  
  var newWin = window.open(linkPage,"attdReportDaily","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
  
  newWin.focus();
 

}
function SetAllCheckBoxes(FormName, FieldName, CheckValue)
{
	if(!document.forms[FormName])
		return;
	var objCheckBoxes = document.forms[FormName].elements[FieldName];
	if(!objCheckBoxes)
		return;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = CheckValue;
	else
		// set the check value for all check boxes
			for(var i = 0; i < countCheckBoxes; i++)
			objCheckBoxes[i].checked = CheckValue;
}

         function  checkSymbol(obj){
             if(obj==null)
                 return;
             var symbol= obj.value;
             if(symbol!=null){
                 <% if(listScheduleSymbol!=null){ 
                     for(int i=0;i < listScheduleSymbol.size(); i++ ){
                    ScheduleSymbol sch = (ScheduleSymbol)listScheduleSymbol.get(i);
                    %>
                 if(symbol=="<%=sch.getSymbol()%>"){
                     return;
                 }
               
                 <%}
                    }%>
                    alert(symbol+" is not a schedule symbol / please check the capital letters " + symbol.toUpperCase() );
                 obj.focus();
             }
         }
            function cmdEditAbsence( scheduleId, dateIdx){
                var linkPage = "<%=approot%>/employee/absence/absence_edit.jsp?source=presence&command=<%=(Command.EDIT)%>&hidden_absence_id="+scheduleId+"&hidden_absence_date="+dateIdx; 
                //window.open(linkPage,"Absence Edit","height=600,width=800,status=yes,toolbar=yes,menubar=yes,location=yes");  			
                var newWin = window.open(linkPage,"Absence_Edit","height=600,width=800,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=no");  			
                newWin.focus();
            }

            function cmdEditPresence(empNum,symbol,date){
                document.frpresence.action="edit_presence.jsp?empNum=" + empNum+ "&symbol=" + symbol+ "&date=" +date;
                document.frpresence.submit();
            }

            function cmdNewLeave(employeeId, requestDateDaily){            
                var linkPage = "<%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<%=(Command.ADD)%>&employeeId="+employeeId+"&requestDateDaily="+requestDateDaily; 
                var newWin = window.open(linkPage,"Leave","height=700,width=950,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }

            function cmdEditLeave(employeeId, requestDateDaily){            
                //var linkPage = "<!--%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<!--%=(Command.EDIT)%-->"; 
                var linkPage = "<%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<%=(Command.EDIT)%>&employeeId="+employeeId+"&requestDateDaily="+requestDateDaily; 
                //window.open(linkPage,"Leave","height=600,width=800,status=yes,toolbar=yes,menubar=no,location=no");  			
                var newWin = window.open(linkPage,"Leave","height=700,width=950,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }
            //update by satrya 2012-08-011
            function cmdEditAttendace(employeeId, requestDateDaily){            
                //var linkPage = "<!--%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<!--%=(Command.EDIT)%-->"; 
                var linkPage = "<%=approot%>/report/presence/attendance_edit.jsp?source=presence&command=<%=(Command.EDIT)%>&employeeId="+employeeId+"&requestDateDaily="+requestDateDaily; 
                //window.open(linkPage,"Leave","height=600,width=800,status=yes,toolbar=yes,menubar=no,location=no");  			
                var newWin = window.open(linkPage,"Leave","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }
             function cmdEditAttendaceManual(employeeId, requestDateDaily){            
                //var linkPage = "<!--%=approot%>/employee/leave/leave_app_edit.jsp?source=presence&command=<!--%=(Command.EDIT)%-->"; 
                var linkPage = "<%=approot%>/employee/presence/attd_edit_dutty.jsp?source=presence&employeeId="+employeeId+"&requestDateDaily="+requestDateDaily; 
                //window.open(linkPage,"Leave","height=600,width=800,status=yes,toolbar=yes,menubar=no,location=no");  			
                var newWin = window.open(linkPage,"Leave","height=700,width=950,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
                newWin.focus();
            }
        

            function cmdView(){
                document.frpresence.command.value="<%=Command.LIST%>";
                document.frpresence.start.value=0; 
                 document.frpresence.source.value="";
                document.frpresence.action="presence_report_daily.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }
             function cmdRefresh(){
                document.frpresence.command.value="<%=Command.REFRESH%>";
                  document.frpresence.source.value="";
                //document.frpresence.start.value=0; 
                document.frpresence.action="presence_report_daily.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }
            

            /*function cmdUpdateDep(){
                document.frpresence.command.value="<%//=Command.ADD%>";
                document.frpresence.action="presence_report_daily.jsp"; 
                document.frpresence.submit();
            }*/
              //update by satrya 2012-10-04
           function cmdUpdateDep(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                 document.frpresence.action="presence_report_daily.jsp";
                 document.frpresence.target = "";
                 document.frpresence.submit();
            }

            function updateDaily(oid){
                document.frpresence.command.value="<%=Command.SAVE%>";
                 document.frpresence.source.value="updateSchedule";
                document.frpresence.hidden_empschedule_id.value = oid;
                document.frpresence.action="presence_report_daily.jsp"; 
                document.frpresence.target = "";
                document.frpresence.submit();        
            }
            function updateAndAnalyzeDaily(oid){
                document.frpresence.command.value="<%=Command.SAVE%>";
                document.frpresence.source.value="analyzePresence";
                document.frpresence.hidden_empschedule_id.value = oid;
                document.frpresence.action="presence_report_daily.jsp"; 
                document.frpresence.target = "";
                document.frpresence.submit();   
                //alert("test");
            }
            
            function updateOvertime(oid){
                document.frpresence.command.value="<%=Command.POST%>";
                 document.frpresence.source.value="";
                document.frpresence.hidden_empschedule_id.value = oid;
                document.frpresence.action="presence_report_daily.jsp"; 
                document.frpresence.target = "";
                document.frpresence.submit();        
            }
            function cmdListFirst(){
		document.frpresence.command.value="<%=Command.FIRST%>";
		document.frpresence.action="presence_report_daily.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}

	function cmdListPrev(){
		document.frpresence.command.value="<%=Command.PREV%>";
		document.frpresence.action="presence_report_daily.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}

	function cmdListNext(){
		document.frpresence.command.value="<%=Command.NEXT%>";
		document.frpresence.action="presence_report_daily.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}

	function cmdListLast(){
		document.frpresence.command.value="<%=Command.LAST%>";
		document.frpresence.action="presence_report_daily.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}
        function reportExcel(){	 
                var linkPage = "<%=printroot%>.report.attendance.AttendanceReportDailyXls"; 
                document.frpresence.action="<%=printroot%>.report.attendance.AttendanceReportDailyXls"; 
                document.frpresence.target = "ReportExcel";
                document.frpresence.submit();
                document.frpresence.target = "";
        }
        function reportPdf(){	 
                var linkPage = "<%=printroot%>.report.staffcontrol.AttRecordDailyPdf"; 
                document.frpresence.target = "";
                window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
        }
        function reportPerEmployeePdf(){	 
                var linkPage = "<%=printroot%>.report.staffcontrol.AttRecordDailyPerEmployeePdf"; 
                document.frpresence.action="<%=printroot%>.report.staffcontrol.AttRecordDailyPerEmployeePdf"; 
                document.frpresence.target = "ReportPerEmployee";
                document.frpresence.submit();
                document.frpresence.target = "";
                //window.close(<%=printroot%>"/servlet/com.dimata.harisma.report.staffcontrol.AttRecordDailyPerEmployeePdf");
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
        
            <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
    <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
    <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
     <!-- untuk menampilkan tooltips -->
	<link type="text/css" rel="stylesheet" href="../../stylesheets/jquery.dropdown.css" />
	<script type="text/javascript" src="../../javascripts/jquery.dropdown.js"></script>
    <style type="text/css">
        #content {
 height: auto;
 margin-top: 20px;
 min-height:500px;
}
    	BODY,TD
		{
		    font-family: ·L³n¥¿¶ÂÅé, Tahoma, Arial, Verdana;
		    font-weight: normal;
		    font-size: 12px;
		    color: #333333;
		}
    </style>
     
        <!-- #EndEditable --> 
    </head>
    
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

        <%//if(isMSIE){%>
       <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
        <!-- jika menggunakan selain IE -->
        <%//}else{%>
       <!--<table width="124%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >-->
        <%//}%>
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                                                &gt; Daily Presence <!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td> 
                                            <div id="content">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td class="tablecolor"> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top"> <!-- #BeginEditable "content" -->
                                                                                <form name="frpresence" method="post" action="">
                                                                                    <input type="hidden" name="hidden_empschedule_id" value="<%=iCommand%>">
                                                                                    <!-- input type="hidden" name="command" value="<//%=iCommand%>" -->
                                                                                    <input type="hidden" name="command" value="">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                                                                    <input type="hidden" name="source" value="<%=String.valueOf(source)%>">
                                                                                    <!--input type="hidden" name="hidden_empschedule_id" value="<//%=oidEmpSchedule%>"-->
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <!-- update by satrya 2012-07-16 -->
                                                                                        <tr>
                                                                                          <td width="6%" nowrap="nowrap"> <div align="left">Payrol Num </div></td>
                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                          <input class="masterTooltip" type="text" size="40" name="emp_number"  value="<%= sessEmpSchedule!=null && sessEmpSchedule.getEmpNum() != null? sessEmpSchedule.getEmpNum() : empNum %>" title="You can Input Payroll Number more than one, ex-sample : 1111,2222" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
																								
											<td width="5%" nowrap="nowrap"> Full Name </td>
                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                          <input class="masterTooltip" type="text" size="50" name="full_name"  value="<%= sessEmpSchedule!=null && sessEmpSchedule.getEmpFullName() != null ? sessEmpSchedule.getEmpFullName() : fullName%>" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
                                                                                        </tr>
                                                                                        
                                                                                           
                                                                                       
                                                                                        <tr> 
                                                                                            <td width="6%" align="right" nowrap> 
                                                                                          <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                                                                            <td width="30%" nowrap="nowrap"> : 
                                                                                                <%

                                                                                                    Vector dept_value = new Vector(1, 1);
                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                    //Vector listDept = new Vector(1, 1);
                                                                                                    DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                                                                                    if (processDependOnUserDept) {
                                                                                                        if (emplx.getOID() > 0) {
                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                            } else {
                                                                                                                Position position = null;
                                                                                                                try {
                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                } catch (Exception exc) {
                                                                                                                }
                                                                                                                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                                                                                                                } else {

                                                                                                                    String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                                                                                            + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                                                                                                                    try {
                                                                                                                        String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                                                        Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                                        int grpIdx = -1;
                                                                                                                        int maxGrp = depGroup == null ? 0 : depGroup.size();
                                                                                                                        int countIdx = 0;
                                                                                                                        int MAX_LOOP = 10;
                                                                                                                        int curr_loop = 0;
                                                                                                                        do { // find group department belonging to curretn user base in departmentOid
                                                                                                                            curr_loop++;
                                                                                                                            String[] grp = (String[]) depGroup.get(countIdx);
                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                String comp = grp[g];
                                                                                                                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                    grpIdx = countIdx;   // A ha .. found here 
                                                                                                                                }
                                                                                                                            }
                                                                                                                            countIdx++;
                                                                                                                        } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                                                                                        // compose where clause
                                                                                                                        if (grpIdx >= 0) {
                                                                                                                            String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                String comp = grp[g];
                                                                                                                                whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                                                                                            }
                                                                                                                        }
                                                                                                                    } catch (Exception exc) {
                                                                                                                            System.out.println(" Parsing Join Dept" + exc);
                                                                                                                         
                                                                                                                    }
                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                                                                                                                    //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            //dept_value.add("0");
                                                                                                            //dept_key.add("select ...");
                                                                                                            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                            //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                        }
                                                                                                    } else {
                                                                                                        //dept_value.add("0");
                                                                                                        //dept_key.add("select ...");
                                                                                                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                        //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                    }
                                                                                                    dept_value = keyList.getDepIDs();
                                                                                                    dept_key = keyList.getDepNames();

                                                                                                    /*for (int i = 0; i < listDept.size(); i++) {
                                                                                                    Department dept = (Department) listDept.get(i);
                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                    } */


                                                                                                    String selectValueDepartment = "" + oidDepartment;//+objSrcLeaveApp.getDepartmentId();

                                                                                                %>
                                                                                                <%//=ControlCombo.draw("department", "elementForm", null, selectValueDepartment, dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"")%>
                                                                                                    
                                                                                          <%=ControlCombo.draw("department", "elementForm", null, selectValueDepartment, dept_value, dept_key, " onChange=\"javascript:cmdUpdateDep()\"")%>                                                                                            </td>
											<td width="5%" align="left" nowrap valign="top"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                <%
                                                                                                    /*if(vct!=null && vct.size()>0){
                                                                                                    for(int i=0; i<vct.size(); i++){
                                                                                                    Section s = (Section)vct.get(i);
                                                                                                    String isSelect = getSelected(s, secSelect);
                                                                                                    
                                                                                                    %>
                                                                                                <input type="checkbox" name="chx_<%=s.getOID()%>" value="1" <%=isSelect%>><%=s.getSection()%>
                                                                                                <%}			
                                                                                                    }*/

                                                                                                    Vector sec_value = new Vector(1, 1);
                                                                                                    Vector sec_key = new Vector(1, 1);
                                                                                                    sec_value.add("0");
                                                                                                    sec_key.add("select ...");

                                                                                                    //String sWhereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + sSelectedDepartment;                                                       
                                                                                                    //Vector listSec = PstSection.list(0, 0, sWhereClause, " SECTION ");
                                                                                                    String secWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                    Vector listSec = PstSection.list(0, 0, secWhere, " SECTION ");
                                                                                                    for (int i = 0; i < listSec.size(); i++) {
                                                                                                        Section sec = (Section) listSec.get(i);
                                                                                                        sec_key.add(sec.getSection());
                                                                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                                                                    }
                                                                                                %>
                                                                                          <%=ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key)%>                                                                                            </td>
                                                                                       
                                                                                        </tr>
                                                                                       
                                                                                        <tr> 
                                                                                            <td width="6%" align="right" nowrap> <div align=left>Date</div>                                                                                             </td>
                                                                                            <td width="30%" nowrap="nowrap">:  <!--%//=ControlDate.drawDate("date", date == null || iCommand == Command.NONE ? new Date() : date, "formElemen", 0, installInterval)%-->
                                                                                                <!--//update by satrya 2012-7-18-->
                                                                                                <% 
                                                                                                    //String selectDateStart = "" + selectedDateFrom; 
                                                                                                   //String selectDateFinish 
                                                                                                Date st = new Date();
                                                                                                st.setHours(0);
                                                                                                st.setMinutes(0);
                                                                                                st.setSeconds(0);
                                                                                                Date end = new Date();
                                                                                                end.setHours(23);
                                                                                                end.setMinutes(59);
                                                                                                end.setSeconds(59);
                                                                                                 String ctrTimeStart = ControlDate.drawTime("check_date_start",  selectedDateFrom != null ? selectedDateFrom : st, "elemenForm", 24,0, 0); 
                                                                                                 String ctrTimeEnd = ControlDate.drawTime("check_date_finish",  selectedDateTo != null ?  iCommand==Command.NONE ? end : selectedDateTo : end, "elemenForm", 24,0, 0); 
                                                                                                 %>
                                                                                                <%=ControlDate.drawDateWithStyle("check_date_start", selectedDateFrom != null ? selectedDateFrom : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeStart%>  
                                                                                                <%//=ControlDate.drawDateWithStyle("check_date_start", selectedDateFrom != null ? selectedDateFrom : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                                                                          to  <%=ControlDate.drawDateWithStyle("check_date_finish", selectedDateTo != null ? selectedDateTo : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeEnd%>                                                                                            </td>
                                                                                            <% //out.println("date "+date);
                                                                                                //long periodId = PstPeriod.getPeriodIdBySelectedDate(date); 

                                                                                            %>
																							<!-- update by satrya 2012-09-28 -->
                                                                                      
                                                                                             <td width="5%" align="right" nowrap> 
                                                                                          <div align="left">Reason </div></td>
                                                                                            <td width="59%" nowrap="nowrap">: 
                                                                                                <%
                                                                                                
                                                                                                   Vector reason_value = new Vector(1, 1);
                                                                                                    Vector reason_key = new Vector(1, 1);
                                                                                                    Vector reason_tooltip = new Vector(1, 1);
                                                                                               Vector  listReason = PstReason.list(0, 0, "", "REASON");
                                                                                                for (int r = 0; r < listReason.size(); r++) {
                                                                                                    Reason reason = (Reason) listReason.get(r);
                                                                                                    reason_tooltip.add(reason.getReason()); 
                                                                                                     reason_key.add(reason.getKodeReason());
                                                                                                    reason_value.add(String.valueOf(reason.getNo()));
                                                                                                }  
                                                                                                
                                                                                                %>
                                                                                                <%=ControlCombo.drawTooltip("reason_status", "select...", ""+reason_sts, reason_value,  reason_key, "onkeydown=\"javascript:fnTrapKD()\"", reason_tooltip)%>                                                                                            
                                                                                            </td>
                                                                                            
                                                                                        
                                                                                        </tr>
                                                                                        
                                                                                       <tr>
                                                                                            <td align="right" nowrap><div align="left">Status Presence </div></td>
                                                                                            <td>: 
                                                                                              <% 
                String nameInp="";
                Vector x = new Vector(1,1); 
                for(int attdIdx = 0 ; attdIdx < Presence.STATUS_ATT_ISTIRAHAT.length ; attdIdx++){
                     nameInp = "ATTD_"+ Presence.STATUS_ATT_IDX[attdIdx];

                    if(stsPresence[attdIdx].equals("1")){

                    %>
                        <input name=<%=nameInp%> type="checkbox" checked value=1 > <%= Presence.STATUS_ATT_ISTIRAHAT[attdIdx] %> &nbsp;&nbsp;
                    <%
                    }else{
                    %>
                        <input name=<%=nameInp%> type="checkbox" value=1 > <%= Presence.STATUS_ATT_ISTIRAHAT[attdIdx]%> &nbsp;&nbsp;
                    <%
                    }
                    // x.add(nameInp);
                 }                                                                                                                               

            %>	
                                                                                            </td>
                                                                                            
                                                                                            
                                                                                            <td width="5%" align="right" nowrap> 
                                                                                          <div align="left">Resigned Status  </div></td>
                                                                                          
                                                                                            <td>: 
                                                                                                <input type="radio" name="statusResign" value="0" checked>
                                                                                                No 
                                                                                                <input type="radio" name="statusResign" value="1">
                                                                                                Yes 
                                                                                                <input type="radio" name="statusResign" value="2">
                                                                                                All 
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                             <td width="5%" align="right" nowrap> 
                                                                                          <div align="left">Emp. Category </div></td>
                                                                                            <td width="59%" nowrap="nowrap">
                                                                                                <table>
                                                                                                    <tr>
                                                                                                         <td width="83%" colspan="4">
                                  <table>
                                <%
                               
                                      int numCol = 4;
                                      boolean createTr=false;
                                      int numOfColCreated =0;
                                      Hashtable hashGetListEmpSel = new Hashtable();
                                      if(stsEmpCategorySel!=null && stsEmpCategorySel.length()>0){
                                         stsEmpCategorySel =  stsEmpCategorySel.substring(0,stsEmpCategorySel.length()-1);
                                          String whereClause = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " IN ("+stsEmpCategorySel+")"; 
                                          hashGetListEmpSel = PstEmpCategory.getHashListEmpSchedule(0, 0, whereClause, "");
                                      }
                                      Vector vObjek = PstEmpCategory.listAll(); 
                                      //String checked="unchecked"; 
                                      long oidEmpCat=0; 
                                      for(int tc=0; (vObjek!=null) &&  (tc<vObjek.size());tc++){
                                         EmpCategory empCategory = (EmpCategory) vObjek.get(tc); 
                                          oidEmpCat=0;
                                          if(tc%numCol==0){ 
                                              out.println("<tr><td nowrap>"); createTr=true;
                                              numOfColCreated=1;
                                           } else { 
                                              out.println("<td nowrap>");
                                              numOfColCreated=1+numOfColCreated;
                                             }
                                           if(empCategory!=null){
                                             
                                                if(hashGetListEmpSel!=null && hashGetListEmpSel.size()>0 && hashGetListEmpSel.get(empCategory.getOID())!=null){ 
                                                    EmpCategory empCategoryHas = (EmpCategory) hashGetListEmpSel.get(empCategory.getOID());
                                                    oidEmpCat = empCategoryHas.getOID();  

                                                 }
                                                 
                                                  if(oidEmpCat!=0){ 
                                           %>
                                                  <input type="checkbox" name="<%="EMP_CAT_"+tc%>"  checked="checked" value="<%=empCategory.getOID()%>"  /> <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;                                                                      
                                          <%}else{%>
                                            <input type="checkbox" name="<%="EMP_CAT_"+tc%>"  value="<%=empCategory.getOID()%>"  /> <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;                                                                      
                                          <%}%>
                                            <%
                                            if(numOfColCreated==numCol || (tc+2)>vObjek.size()){
                                                out.println("</td></tr>");
                                             }else{
                                                out.println("</td>");
                                             }
                                          }
                                      } 

                                    %>

                                    </table>
                               </td>
                                                                                                    </tr>
                                                                                                </table>                                                                                         
                                                                                            </td>
                                                                                          
                                                                                             <td width="5%" align="right" nowrap> 
                                                                                          <div align="left">Status </div></td>
                                                                                            <td width="59%" nowrap="nowrap">
                                                                                                <table>
                                                                                                    <tr>
                                                                                                         <td width="83%" colspan="4">
                                  <table>
                                <%
                                
                               
                                      numCol = 4;
                                      createTr=false;
                                      numOfColCreated =0;
                                      //Hashtable hashGetListStsScheduleSel = new Hashtable();
                                      //if(stsScheduleSel!=null && stsScheduleSel.length()>0){
                                      //   stsScheduleSel =  stsScheduleSel.substring(0,stsScheduleSel.length()-1);
                                          //String whereClause = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " IN ("+stsScheduleSel+")"; 
                                          //hashGetListEmpSel = PstEmpCategory.getHashListEmpSchedule(0, 0, whereClause, "");
                                      //}
                                      int sizeSchSchedule =PstEmpSchedule.strPresenceStatusIdx.length; 
                                      //String checked="unchecked"; 
                                     // oidEmpCat=0; 
                                      for(int tc=0;(tc<sizeSchSchedule);tc++){
                                         //EmpCategory empCategory = (EmpCategory) vObjek.get(tc); 
                                           nameInp = "STS_SCH_"+ PstEmpSchedule.strPresenceStatusIdx[tc];
                                        
                                          if(tc%numCol==0){ 
                                              out.println("<tr><td nowrap>"); createTr=true;
                                              numOfColCreated=1;
                                           } else { 
                                              out.println("<td nowrap>");
                                              numOfColCreated=1+numOfColCreated;
                                             }
                                           if(nameInp!=null && nameInp.length()>0){
                                                 
                                                  if(stsSchedule[tc].length()>0 && stsSchedule[tc].equalsIgnoreCase(""+PstEmpSchedule.strPresenceStatusIdx[tc])){ 
                                           %>
                                                  <input type="checkbox" name="<%=nameInp%>"  checked="checked" value="<%=PstEmpSchedule.strPresenceStatusIdx[tc]%>"  /> <%=PstEmpSchedule.strPresenceStatus[tc]%> &nbsp;&nbsp;                                                                      
                                          <%}else{%>
                                            <input type="checkbox" name="<%=nameInp%>"  value="<%=PstEmpSchedule.strPresenceStatusIdx[tc]%>"  /> <%=PstEmpSchedule.strPresenceStatus[tc]%> &nbsp;&nbsp;                                                                      
                                          <%}%>
                                            <%
                                            if(numOfColCreated==numCol || (tc+2)>sizeSchSchedule){
                                                out.println("</td></tr>");
                                             }else{
                                                out.println("</td>");
                                             }
                                          }
                                      } 

                                    %>

                                    </table>
                               </td>
                                                                                                    </tr>
                                                                                                </table>                                                                                         
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="6%" nowrap> 
                                                                                          <div align="left"></div></td>
                                                                                            <td width="30%"> 
                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="137">
                                                                                                    <tr> 
                                                                                                        <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                                                                        <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="94" class="command" nowrap><a href="javascript:cmdView()">View 
                                                                                                                Presence</a></td>
                                                                                                    </tr>
                                                                                          </table></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <% if((iCommand == Command.REFRESH)||(iCommand == Command.LIST) || (iCommand == Command.SAVE) || (iCommand == Command.POST)   || iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST){%>
                                                                                        
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <% if (listAttendanceRecordDailly != null && listAttendanceRecordDailly.size() > 0) {%>
                                                                                        <tr><td><hr></td></tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <% 
                                                                                                int dataStatus = 0;
                                                                                                try{
// process on drawlist
    Vector vectResult = isHRDLogin?
            drawList(out,iCommand,(listAttendanceRecordDailly !=null && listAttendanceRecordDailly.size() > 0 ? listAttendanceRecordDailly : new Vector()), (listPresencePersonalInOut !=null && listPresencePersonalInOut.size() > 0 ? listPresencePersonalInOut : new Vector()),(listOvertimeDaily !=null && listOvertimeDaily.size() > 0 ? listOvertimeDaily : new Vector()),holidaysTable,selectedDateFrom,selectedDateTo,fullName,empNum, privUpdatePresence,sessEmpSchedule==null && sessEmpSchedule.getStart() == 0 ? start : sessEmpSchedule.getStart(),showOvertime,isHRDLogin,attdConfig)
            :drawListViewOnly(out, iCommand,(listAttendanceRecordDailly !=null && listAttendanceRecordDailly.size() > 0 ? listAttendanceRecordDailly : new Vector()),(listPresencePersonalInOut !=null && listPresencePersonalInOut.size() > 0 ? listPresencePersonalInOut : new Vector()),(listOvertimeDaily !=null && listOvertimeDaily.size() > 0 ? listOvertimeDaily : new Vector()), holidaysTable, selectedDateFrom, selectedDateTo, fullName, empNum, sessEmpSchedule==null && sessEmpSchedule.getStart() == 0 ? start : sessEmpSchedule.getStart(), showOvertime, attdConfig);
    dataStatus = Integer.parseInt(String.valueOf(vectResult !=null && vectResult.size()> 0 ? vectResult.get(0):0));
    //String listData = String.valueOf(vectResult !=null && vectResult.size() > 0 ? vectResult.get(1):0);
    Vector vectDataToPdf = vectResult != null && vectResult.size()>0 ? ((Vector) vectResult.get(1)) : new Vector() ;

// design vector that handle data to store in session
    Vector vectPresence = new Vector(1, 1);
    //update by satrya 2012-07-24
    //untuk yg memasukkan data PDF
    vectPresence.add(selectedDateFrom);
    vectPresence.add(selectedDateTo);
    vectPresence.add("" + oidDepartment);
    vectPresence.add("" + fullName);
    vectPresence.add("" + empNum);
    vectPresence.add(vectDataToPdf);
    vectPresence.add(secSelect);
    vectPresence.add(""+oidSection); 
    //vectPresence.add(listStrukturEmp);

    if (session.getValue("ATTENDANCE_RECORD_DAILY") != null) {
        session.removeValue("ATTENDANCE_RECORD_DAILY");
           }
    session.putValue("ATTENDANCE_RECORD_DAILY", vectPresence);
    //end                                                                                                
                             }
catch(Exception ex){
    System.out.println("Exception add PDF report"+ex);
}                                                                                                                                                        
                                                                                                        
        %>

                                                                                            </td>
                                                                                        </tr>
        <tr>
    <td>
        <table width="100%">
    <tr>
        <td>
                   
<img src="<%=approot%>/images/attention-icon_x.png"/><span class="example" data-dropdown="#dropdown-1">Rule Edit Dutty</span>
<div id="dropdown-1" class="dropdown dropdown-tip has-icons">
		<div class="dropdown-panel">
			<table  border="0" class="tablecolor">
            <tr>
                <td class="tabbg">
<table width="475px">
  <tr>
    <td width="12" class="bdr">1)</td>
    <td class="bdr"><%=information[language][question1]%></td>
  </tr>
  <tr>
    <td width="12" class="bdr">&nbsp;</td>
    <td width="408" class="bdr"><%=information[language][selected1]%></td>
  </tr>
  <tr>
    <td class="bdr">&nbsp;</td>
    <td class="bdr"><%=information[language][selected2]%></td>
  </tr>
  <tr>
    <td >&nbsp;</td>
    <td ><%=information[language][selected3]%></td>
  </tr>
  <tr>
    <td class="bdr">&nbsp;</td>
    <td class="bdr">
		<ul>
			<li><%=information[language][child1]%></li>
		    <li><%=information[language][child2]%></li>
		</ul>
	</td>
  </tr>
  <tr>
    <td class="bdr">&nbsp;</td>
    <td class="bdr"><%=information[language][selected4]%></td>
  </tr>
  <tr>
    <td class="bdr">&nbsp;</td>
    <td class="bdr"><%=information[language][selected5]%></td>
  </tr>
  <tr>
       <td width="12" class="bdr">2)</td>
    <td class="bdr"><%=information[language][question2]%></td>
  </tr>
  <tr>
    <td class="bdr">&nbsp;</td>
    <td class="bdr"><%=information[language][selected6]%></td>
  </tr>
  <tr>
    <td class="bdr">&nbsp;</td>
    <td class="bdr"><%=information[language][selected7]%></td>
  </tr>
   <tr>
    <td>3)</td>
    <td><%=information[language][question3]%></td>
  </tr>
  
</table>
                </td>
            </tr>
        </table>
		</div>
	</div>  

        </td>
    </tr>
</table>
    </td>
</tr>
                                                                                    </table>
                                                                                        <%if (dataStatus == DATA_PRINT && privPrint) {%>
                                                    <!-- untuk tombol Next Prev -->
                                                    <tr>
                                                            <td>
<% 
ControlLine ctrLine = new ControlLine();												
ctrLine.setLocationImg(approot+"/images");

ctrLine.initDefault();		
int listCommand = Command.NEXT;											
out.println(ctrLine.drawImageListLimit(listCommand, vectSize, sessEmpSchedule==null && sessEmpSchedule.getStart() == 0 ? start : sessEmpSchedule.getStart(), recordToGet));
%>
                                                            </td>
                                                    </tr>
                                                     <!-- update by satrya 2013-04-19 -->
                                                                                       <%if(updateSchedule!=0 && message!=null && message.length()>0){ %>
                                                                                        <tr>
                                                                                            <td bgcolor="#FFFF00">
                                                                                             <img src="<%=approot%>/images/info3.png"><b> Message : </b> <%=message%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%}%>
                                                                                                <tr>
                                                                                <!-- end -->
                                                                                            <td>
                                                                                                <table width="18%" border="0" cellspacing="1" cellpadding="1">
                                                                                                    <tr>
                                                                                                        <%if (privUpdatePresence && isHRDLogin) {%>
                                                                                                        <td width="15%"><a href="javascript:updateDaily()"><img src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" border="0"></a></td>

                                                                                                        <td width="35%" nowrap="nowrap"><b><a href="javascript:updateDaily()" class="buttonlink"><p class="masterTooltip" title=" Update this daily presence report according to data on the form without analyzing attendance status" >Update Daily Presence</p></a></b> 													
                                                                                                        </td>
                                                                                                        <td width="15%"><a href="javascript:updateAndAnalyzeDaily()"><img src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" border="0"></a></td>

                                                                                                        <td width="35%" nowrap="nowrap"><b><a href="javascript:updateAndAnalyzeDaily()" class="buttonlink"><p class="masterTooltip" title=" Update this daily presence report and analyze attendance status according to schedule and IN/OUT attendance records" >Update & Analyze Daily Presence</p></a></b> 													
                                                                                                        </td>
                                                                                                       <%if(showOvertime==0 && isHRDLogin){%>
                                                                                                         <td width="15%"><a href="javascript:updateOvertime()"><img src="<%=approot%>/images/refresh2.jpg" width="24" height="24" border="0"></a></td>

                                                                                                        <td width="35%" nowrap="nowrap"><b><a href="javascript:updateOvertime()" class="buttonlink">Update Overtime</a></b> 													
                                                                                                        </td>
                                                                                                        <%}%>
                                                                                                        <%}%>
                                                                                                        
                                                                                                        <td width="5%">&nbsp;</td>
                                                                                                        <td width="15%"><a href="javascript:reportPdf()"><img src="<%=approot%>/images/pdf.png" width="24" height="24" border="0"></a></td>
                                                                                                        <td width="30%" nowrap="nowrap"><b><a href="javascript:reportPdf()" class="buttonlink">Print Daily Presence</a></b> 
                                                                                                        </td>
                                                                                                        
													<td width="5%">&nbsp;</td>
                                                                                                        <td width="15%"><a href="javascript:reportPerEmployeePdf()"><img src="<%=approot%>/images/pdf.png" width="24" height="24" border="0"></a></td>
                                                                                                        <td width="30%" nowrap="nowrap"><b><a href="javascript:reportPerEmployeePdf()" class="buttonlink">Print  Daily Presence Per Employee</a></b> 
                                                                                                        </td>
                                                                                                        
                                                                                                        <td width="5%">&nbsp;</td>
                                                                                                        <td width="15%"><a href="javascript:reportExcel()"><img src="<%=approot%>/images/excel.png" width="24" height="24" border="0"></a></td>
                                                                                                        <td width="30%" nowrap="nowrap"><b><a href="javascript:reportExcel()" class="buttonlink">Print Daily Presence Excel</a></b> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>

                                                                                        <%}//untuk command list%>										  
                                                                                        <%} else if(!(Errmsg!=null && Errmsg.length()>0)){
                                                                                            
                                                                                        %>
                                                                                        <tr>
                                                                                            <td>&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                        <td width="2%" bgcolor="#FFFF00">
                                                                                          Message :  <%= Errmsg %>
                                                                                      
                                                                                        </td> 
                                                                                        </tr>
                                                                                        <%} else{
                                                                                          iErrCode = CtrlPresence.RSLT_RECORD_NOT_FOUND;  
                                                                                        %>
                                                                                        <tr>
                                                                                            <td>&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>&nbsp;</td>
                                                                                        </tr>
                                                                                        <!--<td width="2%" bgcolor="#FFFF00"></td>-->
                                                                                        <td width="2%" bgcolor="#FFFF00">
                                                                                          Message :  <%= CtrlPresence.resultText[CtrlPresence.LANGUAGE_FOREIGN][iErrCode]%>
                                                                                          <%}%>
                                                                                        </td> 
                                                                                      
                                                                                        <tr>
                                                                                            <td>&nbsp;</td>
                                                                                        </tr>
                                                                                        

                                                                                        <tr>
                                                                                            <td>
                                                                                                <span class="style1">  <font face="Arial">Ket Symbol :</font> </span>
                                                                                                <%=drawList(listScheduleSymbol)%>
                                                                                          </td>                                                                                          
                                                                                        </tr>
                                                                                        
                                                                                    </table>
                                                                                    <%}%>
                                                                                </form>
                                                                                <!-- #EndEditable --> </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                            </div>
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
                    
        </table>
       
    <script type="text/javascript">
	    $(document).ready(function () {
	        gridviewScroll();
	    });
            <%
                int freesize=4;
                if(attdConfig.getConfigurasiReportScheduleDaily() == I_Atendance.CONFIGURASI_I_REPORT_DAILY_SCHEDULE_AND_CEK_BOX_ADA_DIBELAKANG){
                    freesize = 7;
                }
            %>
	    function gridviewScroll() {
	        gridView1 = $('#GridView1').gridviewScroll({
                width: 1310,
                height: 500,
                railcolor: "##33AAFF",
                barcolor: "#CDCDCD",
                barhovercolor: "#606060",
                bgcolor: "##33AAFF",
                freezesize: <%=freesize%>,
                arrowsize: 30,
                varrowtopimg: "<%=approot%>/images/arrowvt.png",
                varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                harrowleftimg: "<%=approot%>/images/arrowhl.png",
                harrowrightimg: "<%=approot%>/images/arrowhr.png",
                headerrowcount: 2,
                railsize: 16,
                barsize: 15
            });
	    }
	</script>

    </body>
    <!-- #BeginEditable "script" --> 
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>