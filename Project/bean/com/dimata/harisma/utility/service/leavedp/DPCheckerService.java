
package com.dimata.harisma.utility.service.leavedp;

import java.sql.*;
import java.util.*;
import java.util.Date;

import com.dimata.util.*;
import com.dimata.qdep.db.*;

import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.leave.dp.DpAppDetail;
import com.dimata.harisma.entity.leave.dp.DpAppMain;
import com.dimata.harisma.entity.leave.dp.PstDpAppDetail;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.form.attendance.*;
import com.dimata.harisma.form.masterdata.*;
import com.dimata.harisma.session.attendance.SessDpStockManagement;
import com.dimata.harisma.session.attendance.SessEmpSchedule;
import com.dimata.harisma.session.attendance.SessLongLeave;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.harisma.session.leave.dp.SessDpAppDetail;
import com.dimata.system.entity.PstSystemProperty;


/**
 *
 * @author bayu
 */

public class DPCheckerService {
    
    
    private static boolean isRunning = false;
    
    
   /**
     * @desc mengecek apakah service sedang berjalan atau tidak
     * @return service status : boolean
     */
    
    public static boolean getStatus() {
        return isRunning;
    }
    
   /**
     * @desc menjalankan service
     */
    
    public void startService() {
        if (!isRunning) 
        {
            System.out.println(".................... Service DpCheck started ....................");
            try 
            {                
                isRunning = true;
                Thread thr = new Thread(new AutomaticDPChecker());
                thr.setDaemon(false);
                thr.start();
            }
            catch (Exception e) 
            {
                System.out.println(">>> Exc on starting Service DpCheck : " + e.toString());
            }
        }
    }
    
     /**
     * @desc menghentikan service
     */
    
    public void stopService() {
        isRunning = false;
        System.out.println(".................... Service DpCheck stopped ....................");        
    }    
    
    
    public static void main(String[] args) {
        DPCheckerService dp = new DPCheckerService();
        dp.process();
        System.exit(0);
    }
    
    
    
    /**
     * @desc method utama untuk memproses
     *       pengecekan perolehan DP
     * @return void
     */
    
    public void process() {         
        I_Leave leave = null;
        
        /**
         * Schedule 
         */
        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();
        Hashtable hSysLeaveSO = new Hashtable();
        Hashtable hSysLeaveOF = new Hashtable();
        
        hSysLeaveDP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT);
        hSysLeaveSP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE);
        hSysLeaveLL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_LONG_LEAVE);
        hSysLeaveAL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE);
        hSysLeaveSO = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF);
        hSysLeaveOF = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_OFF);
        
        Hashtable hLeave = new Hashtable(1,1);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT),hSysLeaveDP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE),hSysLeaveSP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE),hSysLeaveLL);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE),hSysLeaveAL);
        hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF), hSysLeaveSO);
        hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_OFF), hSysLeaveOF);
        
        
        /* Instantiate configuration class */
        try {
            leave = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        }
        catch(Exception e) {
            System.out.println("Error on loading leave config " + e.getMessage());
            return;
        }
        
        
        /* get yesterday date */
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, -1);        
        Date yesterday = new Date(calendar.get(Calendar.YEAR)-1900, calendar.get(Calendar.MONTH), calendar.get(calendar.DATE));
                
        /* Get off symbols */
        //Vector listOffSymbols = getOffSymbols();        
        
        /* Get schedule list */
        Vector listSchedule = getSchedule(yesterday);  
        
        
        
        if(listSchedule != null && listSchedule.size()>0) {  
            
            // cek setiap employee
            for(int i=0; i<listSchedule.size(); i++) {
                
                // initialize dp to 0
                int dpAchieved = 0;
                            
                // get schedule data
                Vector schedule = (Vector)listSchedule.get(i);
                
                long scheduleId = Long.parseLong(""+schedule.get(0));
                long employeeId = Long.parseLong(""+schedule.get(1));
                long symbol1 = Long.parseLong(""+schedule.get(2));
                long symbol2 = Long.parseLong(""+schedule.get(3));
                long timeIn1 = Long.parseLong(""+schedule.get(4));
                long timeIn2 = Long.parseLong(""+schedule.get(5));
                long timeOut1 = Long.parseLong(""+schedule.get(6));
                long timeOut2 = Long.parseLong(""+schedule.get(7));
                                               
                // ambil data employee
                Employee emp = new Employee();
                
                try {
                    emp = PstEmployee.fetchExc(employeeId);
                } catch(Exception e) {
                    emp = new Employee();
                }
                
                System.out.println("Processing " + emp.getFullName());
                
                if(emp.getFullName().equals( "Z"))
                    System.out.println("found");
                
                // ambil data level
                Level level = new Level();

                try {
                    level = PstLevel.fetchExc(emp.getLevelId());                    
                } catch(Exception e) {
                    level = new Level();
                }
                
                // check whether this schedule is already been checked
                boolean haveBeenChecked = isAlreadyBeenProcessed(employeeId, new Date());
                
                if(haveBeenChecked)
                    continue;       // skip to next record
                                
                
                // cek hari libur n birthday
                boolean isHoliday = leave.isPublicHoliday(emp, yesterday); 
                boolean isBirthday = leave.isBirthDay(emp, yesterday);
                
                
                /*
                 * CEK MASUK KERJA
                 * 
                 */
                
                if(!leave.isPresent(emp, yesterday))  // tidak masuk kerja
                {
                    
                    // cek apakah hari libur/ultah
                    if(isHoliday) 
                        dpAchieved = 1;     // jika hari libur, dapat DP 1   
                    else if(isBirthday)
                        dpAchieved = 1;     // jika ultah, dapat DP 1
                    
                    // get expiration period
                    if(dpAchieved > 0) {
                        int expired = leave.getDpValidity(level.getLevel());                    
                        saveDPRecord(employeeId, dpAchieved, expired, scheduleId, yesterday);
                    }
                    
                                         
                            /* PROSES PENGECEKAN UNTUK TAKEN (ADDITIONAL) */

                            int typeSymbol = getLeaveSchType(hLeave, symbol1);

                            //PROSES PENGECEKAN PENGGUNAAN DP
                            if(typeSymbol==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT){
                                String strCase = PstSystemProperty.getValueByName("DP_PROCESS_APPROVAL");
                                System.out.println("==========================================================");
                                System.out.println("...............  START TAKEN DP PROCESS ..................");
                                System.out.println("==========================================================");
                                
                                //proses menghitng penggunaan dp
                                if(strCase.equals("TRUE")){ //Memerlukan approval
                                    System.out.println("PROCESS WITH REQUIRED APPROVAL MODE");
                                    processTakenDpWithApproval(employeeId, new Date());
                                }else{  //Tidak memerlukan approval
                                    System.out.println("PROCESS WITH OPTIONAL APPROVAL MODE");
                                    processTakenDpWithOptionalApproval(employeeId, new Date());
                                } 
                            }
                            
                            //NITIP ANNUAL LEAVE PROCCESS
                            if(typeSymbol==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE){
                                String strCase = PstSystemProperty.getValueByName("AL_PROCESS_APPROVAL");
                                System.out.println("==========================================================");
                                System.out.println("...............  START TAKEN AL PROCESS ..................");
                                System.out.println("==========================================================");
                                
                                //proses menghitng penggunaan al
                                if(strCase.equals("TRUE")){ //Memerlukan approval
                                    System.out.println("PROCESS WITH REQUIRED APPROVAL MODE");
                                    processTakenAlWithApproval(employeeId, new Date());
                                }else{  //Tidak memerlukan approval
                                    System.out.println("PROCESS WITH OPTIONAL APPROVAL MODE");
                                    processTakenAlWithOptionalApproval(employeeId, new Date());
                                } 
                            }

                            //NITIP LONG LEAVE PROCCESS
                            if(typeSymbol==PstScheduleCategory.CATEGORY_LONG_LEAVE){
                                String strCase = PstSystemProperty.getValueByName("LL_PROCESS_APPROVAL");
                                if(!strCase.equals("TRUE")){
                                    System.out.println("==========================================================");
                                    System.out.println("...............  START TAKEN LL PROCESS ..................");
                                    System.out.println("...............    ---NO APPROVAL---   ...................");
                                    System.out.println("==========================================================");
                                    SessLongLeave.processTakenLLUnApproved(employeeId, yesterday);
                                }
                            }

                            //NITIP SPECIAL LEAVE PROCESS
                            if(typeSymbol==PstScheduleCategory.CATEGORY_SPECIAL_LEAVE) {
                                String strCase = PstSystemProperty.getValueByName("SL_PROCESS_APPROVAL");
                                System.out.println("==========================================================");
                                System.out.println(".......... START TAKEN SPECIAL LEAVE PROCESS .............");
                                System.out.println("==========================================================");

                                 //proses menghitng penggunaan special leave
                                if(strCase.equals("TRUE")){//Memerlukan approval
                                    System.out.println("PROCESS WITH REQUIRED APPROVAL MODE");
                                    processTakenSpecialLeaveWithApproval(employeeId, new Date());
                                }else{ //Tidak memerlukan approval
                                    System.out.println("PROCESS WITH OPTIONAL APPROVAL MODE");
                                    processTakenSpecialLeaveWithOptionalApproval(employeeId, symbol1, new Date());
                                } 
                            }

                            /* PROSES PENGECEKAN SICK LEAVE  */ 
                            /* NITIP */
                            System.out.println("==========================================================");
                            System.out.println(".......... START SICK LEAVE PROCESS .......................");
                            System.out.println("==========================================================");

                            SickLeaveChecker.proccesSickLeaveNotPresent(symbol1, scheduleId, yesterday);
                            /* END SICK LEAVE PROCESS*/
                    
                }
                else  // jika masuk kerja
                {
                    
                        long totalScheduleTime = 0;
                        boolean offSchedule = false;
                     
                        // cek apakah hari libur/ultah
                        if(isHoliday) 
                            dpAchieved = 1;     // jika libur, dapat DP 1 
                        else if(isBirthday) 
                            dpAchieved = 1;     // jika ultah, dapat DP 1
                    
                    
                                            // cek apakah schedulenya off, kemudian hitung jam kerja
                                            /*if(isInOffSymbols(symbol1, listOffSymbols)) {
                                                totalScheduleTime += getOffScheduleTime(symbol1);
                                                offSchedule = true;
                                            }

                                            if(isInOffSymbols(symbol2, listOffSymbols)) {
                                                totalScheduleTime += getOffScheduleTime(symbol2);
                                                offSchedule = true;
                                            }*/
                    
                        int typeSymbol1 = getLeaveSchType(hLeave, symbol1);

                        if(typeSymbol1==PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF ||
                           typeSymbol1==PstScheduleCategory.CATEGORY_OFF)                        {
                            
                            totalScheduleTime += getOffScheduleTime(symbol1);
                            offSchedule = true;
                        }

                        int typeSymbol2 = getLeaveSchType(hLeave, symbol1);

                        if(typeSymbol2==PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF ||
                           typeSymbol2==PstScheduleCategory.CATEGORY_OFF)
                        {
                            totalScheduleTime += getOffScheduleTime(symbol2);
                            offSchedule = true;
                        }

                        long totalPresentTime = getPresentTime(timeIn1, timeOut1, timeIn2, timeOut2);

                        //                                                
                        System.out.println(">>> schedule 1 " + symbol1);
                        //System.out.println(">>> type sym 1 " + typeSymbol1);
                        System.out.println(">>> time in  1 " + timeIn1);
                        System.out.println(">>> time out 1 " + timeOut1);
                        
                        System.out.println(">>> schedule 2 " + symbol2);
                        //System.out.println(">>> type sym 2 " + typeSymbol2);
                        System.out.println(">>> time in  2 " + timeIn2);
                        System.out.println(">>> time out 2 " + timeOut2);
                        //
                        //
                        System.out.println(offSchedule == true ? ">>> supposed to be off" : ">>> supposed to present");
                        System.out.println(">>> schedule time " + totalScheduleTime);
                        System.out.println(">>> presence time " + totalPresentTime);
                        //
                        
                        // cek apakah jam kerja >= schedule
                        if(offSchedule && totalPresentTime >= totalScheduleTime) {                            
                            dpAchieved += leave.getDPStandardEntitle(level.getLevel());
                        } 
                        
                        System.out.println("dp achieved = " + dpAchieved);
                        
                        if(dpAchieved > 0) {
                            int expired = leave.getDpValidity(level.getLevel());                    
                            saveDPRecord(employeeId, dpAchieved, expired, scheduleId, yesterday);
                        }
                        
                } // end if : presence check
                
            } // end for : next employee
            
        } // end if : listSchedule == null || listSchedule.size()<0
        
    }
    
    
    /**
     * @desc memperoleh schedule seluruh employee
     *       berdasarkan tanggal kemarin
     * @return schedule list : Vector
     */
    
    private Vector getSchedule(Date yesterday) {
        Vector yesterdaySchedules = new Vector(1,1);   
        long periodId = 0;
               
               
        /* get schedule period id */
        periodId = getSchedulePeriod(yesterday);

        
        /* get schedule list */
        DBResultSet dbrs = null;
        
        try {
            int date = yesterday.getDate();
            String sym1 = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1];
            String sym2 = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1];
            String in1 = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN1];
            String in2 = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND1];
            String out1 = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT1];
            String out2 = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND1];

            String sql = " SELECT " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + 
                         "," + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + 
                         "," + sym1.substring(0, 1) + date + 
                         "," + sym2.substring(0, 4) + date +
                         "," + in1.substring(0, 2) + date + 
                         "," + in2.substring(0, 5) + date +
                         "," + out1.substring(0, 3) + date + 
                         "," + out2.substring(0, 6) + date + 
                         " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE +
                         " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                         " = " + periodId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()) {
               Vector schedule = new Vector();
               
               schedule.add(rs.getString(1));
               schedule.add(rs.getString(2));
               schedule.add(rs.getString(3));
               schedule.add(rs.getString(4));
               
               Date dateIn = rs.getTime(5);
               Date dateIn2 = rs.getTime(6);
               
               schedule.add(""+(dateIn != null ? dateIn.getTime() : 0));  
               schedule.add(""+(dateIn2 != null ? dateIn2.getTime() : 0));  
               
               Date dateOut = rs.getTime(7);
               Date dateOut2 = rs.getTime(8);
               
               schedule.add(""+(dateOut != null ? dateOut.getTime() : 0)); 
               schedule.add(""+(dateOut2 != null ? dateOut2.getTime() : 0)); 
               
               yesterdaySchedules.add(schedule);
            }
            
            return yesterdaySchedules;
        }
        catch(Exception e) {
            System.out.println("Error fetching schedule " + e.getMessage());
        }
        finally {
            DBResultSet.close(dbrs);            
        }
        
        return new Vector();
    }
    
    /**
     * @desc memperoleh oid untuk periode schedule
     * @param tanggal schedule : Date
     * @return schedule list : Vector
     */
    
    private long getSchedulePeriod(Date date) {               
        String where = "";
        long periodId = 0;        
        
        try {
            where = PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " <= '" + Formater.formatDate(date, "yyyy-MM-dd") + "' AND " + 
                    PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= '" + Formater.formatDate(date, "yyyy-MM-dd") + "'";
            
            Vector periods = PstPeriod.list(0, 0, where, "");
            
            if(periods != null && periods.size()>0) {
                Period period = (Period)periods.firstElement();
                periodId = period.getOID();
            }
            
            return periodId;
        } 
        catch (Exception e) {
            System.out.println("Exception when get period " + e.getMessage());
        }
        
        return 0;
    }
    
    
    /**
     * @desc memperoleh daftar oid untuk schedule
     *       dengan kategori 'supposed to be off'
     * @param tanggal schedule : Date
     * @return schedule list : Vector
     */
    
    public Vector getOffSymbols() {        
        Vector listSymbols = new Vector(1,1);
        long oidCategory = 0;       
        String where = "";
        
        
        /* get schedule category */
        try {
            where = PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + 
                    PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF + " OR " +
                    PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] + " = " + 
                    PstScheduleCategory.CATEGORY_OFF;
            
            Vector categories = PstScheduleCategory.list(0, 0, where, "");
            
            if(categories != null && categories.size()>0) {
                ScheduleCategory category = (ScheduleCategory)categories.firstElement();
                oidCategory = category.getOID();
                
                // get symbol
                where = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +
                        " = " + oidCategory;
                
                Vector symbols = PstScheduleSymbol.list(0, 0, where, "");
                
                for(int i=0; i<symbols.size(); i++) {
                    ScheduleSymbol symbol = (ScheduleSymbol)symbols.get(i);
                    listSymbols.add("" + symbol.getOID());
                }
                
            }
            
            return listSymbols;
        }
        catch(Exception e) {
            System.out.println("Error fetching category list");
        }
        
        return new Vector();        
    }
    
    /**
     * @desc mengecek suatu schedule termasuk schedule 
     *       dengan kategori 'supposed to be off' atau tidak
     * @param schedule symbol id : long
     * @param list of off schedule symbol ids : Vector
     * @return boolean
     */
    
    private boolean isInOffSymbols(long scheduleSymbolId, Vector offSymbolIds) {
                
        if(scheduleSymbolId > 0) {
            
            // check whether this symbol is one on 'supposed to be off' symbols
            for(int i=0; i<offSymbolIds.size(); i++) {
                if(scheduleSymbolId == Long.parseLong(String.valueOf(offSymbolIds.get(i))))
                    return true;
            }
            
        }
        
        return false;
    }
    
    /**
     * @desc menghitung total waktu kerja
      *      sesuai schedule yang direncanakan
     * @param schedule symbol id : long
     * @return total schedule time : int
     */
    
    private int getOffScheduleTime(long offSymbolId1) {
        
        long totalTime = 0;
        
        try {
            // get first symbol object
            ScheduleSymbol symbol = PstScheduleSymbol.fetchExc(offSymbolId1);
            
            if(symbol != null && symbol.getOID()>0) {
                // get start and end time
                Date timeIn = symbol.getTimeIn();
                Date timeOut = symbol.getTimeOut();
                
                if(timeIn != null && timeOut != null) {
                   long in = timeIn.getTime();
                   long out = timeOut.getTime();
                   
                   // find difference in miliseconds
                   totalTime += (out - in);
                   
                   return (int)(totalTime / (60 * 1000));        
                }
            }
        } 
        catch(Exception e) { 
            System.out.println("");
        }
              
        return 0;
    }
    
    /**
     * @desc menghitung total waktu kerja
      *      sesuai kehadiran aktual
     * @param timeIn   : long
     * @param timeIn2  : long
     * @param timeOut  : long
     * @param timeOut2 : long
     * @return total schedule time : int
     */
    
    private int getPresentTime(long timeIn, long timeOut, long timeIn2nd, long timeOut2nd) {
        
        // get total presence time
        long time1 = timeOut - timeIn;
        long time2 = timeOut2nd - timeIn2nd;
        
        long total = time1 + time2;
        
        return (int)(total / (60 * 1000));
        
    }
    
    /**
     * @desc menyimpan data DP stock yang diperoleh secara otomatis
     * @param employee id : long
     * @param dp quantity : int
     * @param expiration in month : int
     * @param schedule id : long
     * @param schedule date : Date
     * @return oid dp : long
     */
    
    private long saveDPRecord(long employeeId, int dpQuantity, int expired, long scheduleId, Date date) {
        DpStockManagement stock = new DpStockManagement();
        
        stock.setEmployeeId(employeeId);
        stock.setiDpQty(dpQuantity);
        stock.setQtyResidue(dpQuantity);
        stock.setDtOwningDate(new Date());
        
        // get expiration date
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, expired);
        
        stock.setDtExpiredDate(new Date(calendar.get(Calendar.YEAR)-1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)));
        stock.setDtExpiredDateExc(stock.getDtExpiredDate());
        stock.setStNote("Automatic DP");
        
        // save data
        try {
            long res = PstDpStockManagement.insertExc(stock);
        } 
        catch (Exception exception) {
            System.out.println("Error saving DP " + exception.getMessage());
            return 0;
        }
        
        
        // update status
        /*
        try {
            String sql = " UPDATE " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + 
                         " SET " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE1].substring(0,4) +
                         Formater.formatDate(date, "d") + " = 'DP Checked'" + 
                         " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + 
                         " = " + scheduleId;
            
            DBHandler.execUpdate(sql);
        }
        catch(Exception e) {
            System.out.println("Error updating schedule status");
        }
        */
        
        //Cari dp stock manajement yang masih valid dan belum di ajukan menjadi dp application
        Vector vDpStockMan = new Vector(1,1);

        vDpStockMan = SessDpStockManagement.listDpStockAvailable(0, 0, employeeId, date);
        for(int i=0;i<vDpStockMan.size();i++){
            Vector vTemp = new Vector(1,1);
            vTemp = (Vector)vDpStockMan.get(i);
            DpStockManagement dpStockMan = new DpStockManagement();
            dpStockMan = (DpStockManagement) vTemp.get(0);
            int count = Integer.parseInt((String)vTemp.get(1));
            boolean isSuccess = false;
            if(dpStockMan.getQtyResidue()-count>0){
               //Membayar Dp yang asih mi
                PstDpStockManagement.paidDpPayable(employeeId, dpStockMan);  
               break;
            }
        }
        return 0;
    }
    
    /**
     * @desc mengecek apakah suatu schedule telah diproses
     *       untuk penghitungan Dp atau belum
     * @param schedule id : long
     * @param schedule date : int    
     * @return status : boolean
     */
    
    /*private boolean isAlreadyBeenProcessed(long scheduleId, Date date) {
        
        boolean status = false;
        
        try {
            String where = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + 
                           " = " + scheduleId + " AND " + 
                           PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE1].substring(0, 4) +
                           Formater.formatDate(date, "d") + 
                           " = 'DP Checked'";
                        
            Vector schedules = PstEmpSchedule.list(0, 0, where, "");
            
            if(schedules != null && schedules.size()>0)
                return true;
            else
                return false;
        }
        catch(Exception e) {
            System.out.println("Error on checking schedule status");
        }
        
        return false;
        
    }*/
    
    private boolean isAlreadyBeenProcessed(long employeeId, Date date) {
        
        boolean status = false;
        
        try {
            String where = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + 
                           " = " + employeeId + " AND " + 
                           PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                           " = '" + Formater.formatDate(date, "yyyy-MM-dd") + "' AND " +
                           PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE] +
                           " = 'Automatic DP'";
            
            System.out.println("Ini where clause pengecekan : " + where);
                        
            Vector dpMangmnts = PstDpStockManagement.list(0, 0, where, "");
            
            if(dpMangmnts != null && dpMangmnts.size()>0)
                return true;
            else
                return false;
        }
        catch(Exception e) {
            System.out.println("Error on checking schedule status");
        }
        
        return false;
        
    }    
    
    private static boolean processTakenSpecialLeaveWithApproval(long empOid, Date currDate){
        boolean isSuccess= false;        
        Vector vSpecialLeaveDetail = new Vector(1,1);
        vSpecialLeaveDetail = SessLeaveApplication.getSpecialLeaveDetail(empOid, currDate);
        
        for(int i=0;i<vSpecialLeaveDetail.size();i++){
            Vector vTemp = (Vector)vSpecialLeaveDetail.get(i);
            
            SpecialLeave leave = new SpecialLeave();
            SpecialLeaveStock leaveDetail = new SpecialLeaveStock();
                      
            leave = (SpecialLeave) vTemp.get(0);
            leaveDetail = (SpecialLeaveStock) vTemp.get(1);
            
            SpecialLeaveTaken leaveTaken = new SpecialLeaveTaken();
            
            // all approval must be passed
            if(leave.getApproval3Id()>0){            
                try {
                    leaveTaken.setEmployeeId(leaveDetail.getEmployeeId());
                    leaveTaken.setSymbolId(leaveDetail.getSymbolId());
                    leaveTaken.setTakenDate(leaveDetail.getTakenDate());
                    leaveTaken.setTakenQty(leaveDetail.getTakenQty());
                    leaveTaken.setPaidDate(leaveDetail.getTakenDate());
                    PstSpecialLeaveTaken.insertExc(leaveTaken);

                    leaveDetail.setLeaveStatus(PstSpecialLeaveStock.STATUS_PROCESSED);
                    PstSpecialLeaveStock.updateExc(leaveDetail);
                }
                catch(Exception e) {
                    System.out.println("Error processing taken special leave " + e.getMessage());
                }
            }
        }
        
        return isSuccess;
    }
    
    private static boolean processTakenSpecialLeaveWithOptionalApproval(long empOid, long symbolOid, Date currDate){
        boolean isSuccess= false;        
        Vector vSpecialLeaveDetail = new Vector(1,1);
        vSpecialLeaveDetail = SessLeaveApplication.getSpecialLeaveDetail(empOid, currDate);
        
        Date yesterday =  (Date) currDate.clone();
        yesterday.setDate(currDate.getDate()-1);
        boolean isHasLeaveDetail = false;
                
                
        // cek leave yang diajukan
        for(int i=0;i<vSpecialLeaveDetail.size();i++){
            Vector vTemp = (Vector)vSpecialLeaveDetail.get(i);
            
            SpecialLeave leave = new SpecialLeave();
            SpecialLeaveStock leaveDetail = new SpecialLeaveStock();
            SpecialLeaveTaken leaveTaken = new SpecialLeaveTaken();
                      
            leave = (SpecialLeave) vTemp.get(0);
            leaveDetail = (SpecialLeaveStock) vTemp.get(1);
            
            // Mengecek apakah leave yang kemarin memiliki leave detail 
            if(leaveDetail.getTakenDate().getTime() == yesterday.getTime()){
                isHasLeaveDetail = true;
            }                       
            
            // no approval is required                     
            try {
                leaveTaken.setEmployeeId(leaveDetail.getEmployeeId());
                leaveTaken.setSymbolId(leaveDetail.getSymbolId());
                leaveTaken.setTakenDate(leaveDetail.getTakenDate());
                leaveTaken.setTakenQty(leaveDetail.getTakenQty());
                leaveTaken.setPaidDate(leaveDetail.getTakenDate());
                PstSpecialLeaveTaken.insertExc(leaveTaken);
                
                //System.out.println(">>> TAKEN PROPOSED LEAVE " + i);
                //System.out.println(">>> OID = " + leaveTaken.getOID());

                leaveDetail.setLeaveStatus(PstSpecialLeaveStock.STATUS_PROCESSED);
                PstSpecialLeaveStock.updateExc(leaveDetail);
            }
            catch(Exception e) {
                System.out.println("Error processing taken special leave " + e.getMessage());
            }        
        }
        
        
        // leave yang tidak diajukan
        
        Employee emp = new Employee();
        ScheduleSymbol sym = new ScheduleSymbol();
               
        
        if(PstSpecialLeaveTaken.isExistSpecialLeaveTaken(empOid, yesterday) == false && isHasLeaveDetail == false) {
           
            // cek sisa stok
            float stockAvailable = SessLeaveApplication.countEligibleDay(empOid, symbolOid, PstScheduleCategory.CATEGORY_SPECIAL_LEAVE);
                        
            // cek taken yang belum mengurangi stok
            String where = PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_PAID_DATE] + " IS NULL";            
            Vector unpaidSpecialLeave = PstSpecialLeaveTaken.list(0, 0, where, "");
            
            // paid taken yang belum terbayar            
            if(unpaidSpecialLeave != null) {                
                for(int i=0; i<unpaidSpecialLeave.size(); i++) {
                    SpecialLeaveTaken specialLeaveTaken = (SpecialLeaveTaken)unpaidSpecialLeave.get(i);
                    
                    if(stockAvailable > 0.000) {
                        try {
                            specialLeaveTaken.setPaidDate(currDate);
                            PstSpecialLeaveTaken.updateExc(specialLeaveTaken);
                            stockAvailable--;
                            
                            //System.out.println(">>> TAKEN PREV UNPAID LEAVE " + i);
                            //System.out.println(">>> OID = " + specialLeaveTaken.getOID());
                        }
                        catch(Exception e) {
                            System.out.println("Error updating stock taken " + e.getMessage());
                        }
                    }
                }                
            }
                        
            // simpan taken yang baru
            where = PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_EMPLOYEE_ID] +
                    " = " + empOid + " AND " +
                    PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_DATE] +
                    " = " + Formater.formatDate(yesterday, "yyyy-MM-dd");
                           
            Vector listTaken = PstSpecialLeaveTaken.list(0, 0, where, "");
            
            if(listTaken == null || listTaken.size() == 0) {
                try{
                    SpecialLeaveTaken specialLeaveTaken = new SpecialLeaveTaken();
                    specialLeaveTaken.setEmployeeId(empOid);

                    if(stockAvailable > 0)
                        specialLeaveTaken.setTakenDate(yesterday);
                    else
                        specialLeaveTaken.setTakenDate(null);

                    specialLeaveTaken.setTakenQty(1);            

                    PstSpecialLeaveTaken.insertExc(specialLeaveTaken);

                    //System.out.println(">>> TAKEN NEW UNPAID LEAVE ");
                    //System.out.println(">>> OID = " + specialLeaveTaken.getOID());
                }
                catch(Exception ex){
                    System.out.println("Error inserting special leave taken " + ex.getMessage());
                }    
            }
        }
          
        return isSuccess;
    }    
    
    
    /***
     * Memproses pengambilan Dp hanya pada Dp Application yang sudah di approve
     */
    private static boolean processTakenDpWithApproval(long empOid, Date currDate){
        boolean isSuccess= false;
        Vector vDataDpApp = new Vector(1,1);
        vDataDpApp = SessDpAppDetail.getDpAppDetail(empOid, currDate);
        for(int i=0;i<vDataDpApp.size();i++){
            Vector vTemp = new Vector(1,1);
            vTemp = (Vector)vDataDpApp.get(i);
            DpAppDetail dpAppDetail = new DpAppDetail();
            DpAppMain dpAppMain = new DpAppMain();
            
            dpAppDetail = (DpAppDetail) vTemp.get(0);
            dpAppMain = (DpAppMain) vTemp.get(1);
            //Jika dp merupakan dp yang etrbayar
            if(dpAppDetail.getDpId()>0){
                if(dpAppMain.getApproval3Id()>0 && dpAppDetail.getTakenDate().getTime()<currDate.getTime()){
                    isSuccess = SessDpStockManagement.createDpTaken(dpAppDetail.getDpId(), empOid, dpAppDetail.getTakenDate());
                    try{
                        dpAppDetail.setStatus(PstDpAppDetail.STATUS_PROCESSED);
                        PstDpAppDetail.updateExc(dpAppDetail);
                    }catch(Exception ex){
                        isSuccess = false;
                    }
                }
            }else{//jika tidak terbayar
                DpStockTaken dpStockTaken = new DpStockTaken();
                dpStockTaken.setEmployeeId(empOid);
                dpStockTaken.setTakenDate(dpAppDetail.getTakenDate());
                dpStockTaken.setTakenQty(1);
                try{
                    PstDpStockTaken.insertExc(dpStockTaken);
                }catch(Exception ex){}
            }
        }
        return isSuccess;
    }
    
    //
    private static boolean processTakenDpWithOptionalApproval(long empOid, Date currDate){
        boolean isSuccess= false;
        
        
        Date yesterday =  (Date) currDate.clone();
        yesterday.setDate(currDate.getDate()-1);
        boolean isYestHasApp = false;
        
        Vector vDataDpApp = new Vector(1,1);
        vDataDpApp = SessDpAppDetail.getDpAppDetail(empOid, currDate);
        for(int i=0;i<vDataDpApp.size();i++){
            Vector vTemp = new Vector(1,1);
            vTemp = (Vector)vDataDpApp.get(i);
            DpAppDetail dpAppDetail = new DpAppDetail();
            DpAppMain dpAppMain = new DpAppMain();
            
            dpAppDetail = (DpAppDetail) vTemp.get(0);
            dpAppMain = (DpAppMain) vTemp.get(1);
            
            //Mengecek apakah dp yang kemarin memiliki dp app detail 
            if(dpAppDetail.getTakenDate().getTime()==yesterday.getTime()){
                isYestHasApp = true;
            }
            
            //Optioanal apprival. boleh tidak di approve
            if(dpAppDetail.getDpId()>0){
                if(dpAppDetail.getTakenDate().getTime()<currDate.getTime()){
                    isSuccess = SessDpStockManagement.createDpTaken(dpAppDetail.getDpId(), empOid, dpAppDetail.getTakenDate());
                    try{
                        dpAppDetail.setStatus(PstDpAppDetail.STATUS_PROCESSED);
                        PstDpAppDetail.updateExc(dpAppDetail);
                    }catch(Exception ex){
                        isSuccess = false;
                    }
                } 
            }else{//jika tidak terbayar
                DpStockTaken dpStockTaken = new DpStockTaken();
                dpStockTaken.setEmployeeId(empOid);
                dpStockTaken.setTakenDate(dpAppDetail.getTakenDate());
                dpStockTaken.setTakenQty(1);
                try{
                    PstDpStockTaken.insertExc(dpStockTaken);
                }catch(Exception ex){}
            }
        }
        //Dp tidak di ajukan
        if(PstDpStockTaken.existDpStockTaken(empOid, yesterday)==false && isYestHasApp == false){
            //Cari dp stock manajement yang masih valid dan belum di ajukan menjadi dp application
            Vector vDpStockMan = new Vector(1,1);
            
            vDpStockMan = SessDpStockManagement.listDpStockAvailable(0, 0, empOid, yesterday);
            boolean isProcess = false;
            for(int i=0;i<vDpStockMan.size();i++){
                Vector vTemp = new Vector(1,1);
                vTemp = (Vector)vDpStockMan.get(i);
                DpStockManagement dpStockMan = new DpStockManagement();
                dpStockMan = (DpStockManagement) vTemp.get(0);
                int count = Integer.parseInt((String)vTemp.get(1));
                if(dpStockMan.getQtyResidue()-count>0){
                   isSuccess = SessDpStockManagement.createDpTaken(dpStockMan.getOID(), empOid, yesterday);
                   isProcess = isSuccess;
                   break;
                }
            }
            if(!isProcess){
                DpStockTaken dpStockTaken = new DpStockTaken();
                dpStockTaken.setEmployeeId(empOid);
                dpStockTaken.setTakenDate(yesterday);
                dpStockTaken.setTakenQty(1);
                try{
                    PstDpStockTaken.insertExc(dpStockTaken);
                }catch(Exception ex){}
            }
        } 
        
        return isSuccess;
    }
    
    private static boolean processTakenAlWithApproval(long empOid, Date currDate){
        boolean isSuccess= false;        
        Vector vSpecialLeave = SessLeaveApplication.getSpecialLeaveDetail(empOid, currDate);
        
        for(int i=0;i<vSpecialLeave.size();i++){
            Vector vTemp = (Vector)vSpecialLeave.get(i);
            
            SpecialLeave leave = new SpecialLeave();
            SpecialLeaveStock leaveDetail = new SpecialLeaveStock();
            ScheduleSymbol symbol = new ScheduleSymbol();
            ScheduleCategory category = new ScheduleCategory();
                      
            leave = (SpecialLeave) vTemp.get(0);
            leaveDetail = (SpecialLeaveStock) vTemp.get(1);
            
            try {
                symbol = PstScheduleSymbol.fetchExc(leaveDetail.getSymbolId());
                
                category = PstScheduleCategory.fetchExc(symbol.getScheduleCategoryId());
            }
            catch(Exception e) {}
            
            if(category.getCategoryType() == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE) {                
                Vector vAnnualLeaveStock = SessLeaveApplication.getAnnualLeave(empOid, leaveDetail.getTakenDate());
               
                // all approval must be passed
                if(leave.getApproval3Id()>0 && vAnnualLeaveStock != null){   
                    
                    for(int j=0; j<vAnnualLeaveStock.size(); j++) {
                        AlStockManagement alStock = (AlStockManagement)vAnnualLeaveStock.get(j);
                        AlStockTaken alTaken = new AlStockTaken();
                        SpecialLeaveTaken leaveTaken = new SpecialLeaveTaken();
                                        
                        try {
                            leaveTaken.setEmployeeId(leaveDetail.getEmployeeId());
                            leaveTaken.setSymbolId(leaveDetail.getSymbolId());
                            leaveTaken.setTakenDate(leaveDetail.getTakenDate());
                            leaveTaken.setTakenQty(leaveDetail.getTakenQty());
                            leaveTaken.setPaidDate(leaveDetail.getTakenDate());
                            PstSpecialLeaveTaken.insertExc(leaveTaken);

                            alTaken.setAlStockId(alStock.getOID());
                            alTaken.setEmployeeId(empOid);
                            alTaken.setTakenDate(currDate);
                            alTaken.setTakenQty(1);
                            alTaken.setPaidDate(currDate);
                            PstAlStockTaken.insertExc(alTaken);

                            leaveDetail.setLeaveStatus(PstSpecialLeaveStock.STATUS_PROCESSED);
                            PstSpecialLeaveStock.updateExc(leaveDetail);
                            
                            alStock.setAlStatus(PstAlStockManagement.AL_STS_TAKEN);
                            PstAlStockManagement.updateExc(alStock);
                        }
                        catch(Exception e) {
                            System.out.println("Error processing taken annual leave " + e.getMessage());
                        }
                    }
                }
            }
        }
        
        return isSuccess;
    }
    
     private static boolean processTakenAlWithOptionalApproval(long empOid, Date currDate){
        boolean isSuccess= false;        
        Vector vSpecialLeaveDetail = SessLeaveApplication.getSpecialLeaveDetail(empOid, currDate);
        
        Date yesterday =  (Date) currDate.clone();
        yesterday.setDate(currDate.getDate()-1);
        boolean isHasLeaveDetail = false;
                
                
        // cek leave yang diajukan
        for(int i=0;i<vSpecialLeaveDetail.size();i++){
            Vector vTemp = (Vector)vSpecialLeaveDetail.get(i);
            
            SpecialLeave leave = new SpecialLeave();
            SpecialLeaveStock leaveDetail = new SpecialLeaveStock();
            ScheduleSymbol symbol = new ScheduleSymbol();
            ScheduleCategory category = new ScheduleCategory();
           
            leave = (SpecialLeave) vTemp.get(0);
            leaveDetail = (SpecialLeaveStock) vTemp.get(1);
            
            try {
                symbol = PstScheduleSymbol.fetchExc(leaveDetail.getSymbolId());
                
                category = PstScheduleCategory.fetchExc(symbol.getScheduleCategoryId());
            }
            catch(Exception e) {}
            
            // Mengecek apakah leave yang kemarin memiliki leave detail 
            if(leaveDetail.getTakenDate().getTime() == yesterday.getTime()){
                isHasLeaveDetail = true;
            }                       
            
            if(category.getCategoryType() == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE) {                
                Vector vAnnualLeaveStock = SessLeaveApplication.getAnnualLeave(empOid, leaveDetail.getTakenDate());
                
                if(vAnnualLeaveStock != null) {  
              
                    for(int j=0; j<vAnnualLeaveStock.size(); j++) {
                        AlStockManagement alStock = (AlStockManagement)vAnnualLeaveStock.get(j);
                        AlStockTaken alTaken = new AlStockTaken();
                        SpecialLeaveTaken leaveTaken = new SpecialLeaveTaken();
                        
                        try {
                            leaveTaken.setEmployeeId(leaveDetail.getEmployeeId());
                            leaveTaken.setTakenDate(leaveDetail.getTakenDate());
                            leaveTaken.setTakenQty(leaveDetail.getTakenQty());
                            leaveTaken.setPaidDate(leaveDetail.getTakenDate());
                            PstSpecialLeaveTaken.insertExc(leaveTaken);
                            
                            alTaken.setAlStockId(alStock.getOID());
                            alTaken.setEmployeeId(empOid);
                            alTaken.setTakenDate(currDate);
                            alTaken.setTakenQty(1);
                            alTaken.setPaidDate(currDate);
                            PstAlStockTaken.insertExc(alTaken);
                           
                            leaveDetail.setLeaveStatus(PstSpecialLeaveStock.STATUS_PROCESSED);
                            PstSpecialLeaveStock.updateExc(leaveDetail);
                                                         
                            alStock.setAlStatus(PstAlStockManagement.AL_STS_TAKEN);
                            PstAlStockManagement.updateExc(alStock);
                        }
                        catch(Exception e) {
                            System.out.println("Error processing taken annual leave " + e.getMessage());
                        }       
                    }
                }
            }
                               
        }   
        
        
        // leave yang tidak diajukan        
                                   
        if(PstSpecialLeaveTaken.isExistSpecialLeaveTaken(empOid, yesterday) == false && isHasLeaveDetail == false) {

            /* PROSES SPECIAL LEAVE DETAIL * /

            // cek sisa stok
            int eligible = SessLeaveApplication.countEligibleDay(empOid, symbol.getOID(), PstScheduleCategory.CATEGORY_ANNUAL_LEAVE);
            int stockAvailable = eligible;

            // cek taken yang belum mengurangi stok
            String where = PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_PAID_DATE] + " IS NULL";            
            Vector unpaidSpecialLeave = PstSpecialLeaveTaken.list(0, 0, where, "");

            // paid taken yang belum terbayar            
            if(unpaidSpecialLeave != null) {                
                for(int j=0; j<unpaidSpecialLeave.size(); j++) {
                    SpecialLeaveTaken specialLeaveTaken = (SpecialLeaveTaken)unpaidSpecialLeave.get(j);

                    if(stockAvailable > 0) {
                        try {
                            specialLeaveTaken.setPaidDate(currDate);
                            PstSpecialLeaveTaken.updateExc(specialLeaveTaken);
                            stockAvailable--;

                            //System.out.println(">>> TAKEN PREV UNPAID LEAVE " + j);
                            //System.out.println(">>> OID = " + specialLeaveTaken.getOID());
                        }
                        catch(Exception e) {
                            System.out.println("Error updating sl stock taken " + e.getMessage());
                        }
                    }
                }                
            }

            // simpan taken yang baru
            where = PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_EMPLOYEE_ID] +
                    " = " + empOid + " AND " +
                    PstSpecialLeaveTaken.fieldNames[PstSpecialLeaveTaken.FLD_TAKEN_DATE] +
                    " = " + Formater.formatDate(yesterday, "yyyy-MM-dd");

            Vector listTaken = PstSpecialLeaveTaken.list(0, 0, where, "");

            if(listTaken == null || listTaken.size() == 0) {
                try{
                    SpecialLeaveTaken specialLeaveTaken = new SpecialLeaveTaken();
                    specialLeaveTaken.setEmployeeId(empOid);

                    if(stockAvailable > 0)
                        specialLeaveTaken.setTakenDate(yesterday);
                    else
                        specialLeaveTaken.setTakenDate(null);

                    specialLeaveTaken.setTakenQty(1);            

                    PstSpecialLeaveTaken.insertExc(specialLeaveTaken);

                    //System.out.println(">>> TAKEN NEW UNPAID LEAVE ");
                    //System.out.println(">>> OID = " + specialLeaveTaken.getOID());
                }
                catch(Exception ex){
                    System.out.println("Error inserting special leave taken " + ex.getMessage());
                }    
            } */
            
            
            
            /* PROCESS ANNUAL LEAVE */
            
            //Vector vAnnualLeaveStock = SessLeaveApplication.getAnnualLeave(empOid, currDate);
                
            //if(vAnnualLeaveStock != null) {              
                           
               // for(int j=0; j<vAnnualLeaveStock.size(); j++) {
                   // AlStockManagement alStock = (AlStockManagement)vAnnualLeaveStock.get(j);
            
                    float eligible = SessLeaveApplication.countEligibleAL(empOid);
                    float stockAvailable = eligible;
                   
                    // cek taken yang belum mengurangi stok
                    String where = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_PAID_DATE] + " IS NULL";            
                    Vector unpaidAnnualLeave = PstAlStockTaken.list(0, 0, where, "");

                    // paid taken yang belum terbayar            
                    if(unpaidAnnualLeave != null) {                
                        for(int k=0; k<unpaidAnnualLeave.size(); k++) {
                            AlStockTaken annualLeaveTaken = (AlStockTaken)unpaidAnnualLeave.get(k);

                            if(stockAvailable > 0) {
                                try {
                                    annualLeaveTaken.setPaidDate(currDate);
                                    PstAlStockTaken.updateExc(annualLeaveTaken);
                                    stockAvailable--;

                                    //System.out.println(">>> TAKEN PREV UNPAID LEAVE " + j);
                                    //System.out.println(">>> OID = " + specialLeaveTaken.getOID());
                                }
                                catch(Exception e) {
                                    System.out.println("Error updating al stock taken " + e.getMessage());
                                }
                                
                            }  // stockAvailable > 0
                            
                        }  // unpaidAnnualLeave iteration              
                        
                    } // unpaidAnnualLeave != null
                    
                    
                    // simpan taken yang baru
                    where = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] +
                            " = " + empOid + " AND " +
                            PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +
                            " = '" + Formater.formatDate(yesterday, "yyyy-MM-dd") + "'";

                    Vector listAlTaken = PstAlStockTaken.list(0, 0, where, "");

                    if(listAlTaken == null || listAlTaken.size() == 0) {
                        try{
                            AlStockTaken annualLeaveTaken = new AlStockTaken();
                            annualLeaveTaken.setEmployeeId(empOid);

                            annualLeaveTaken.setTakenDate(yesterday);
                            if(stockAvailable > 0)
                                annualLeaveTaken.setPaidDate(currDate);
                            else
                                annualLeaveTaken.setPaidDate(yesterday);

                            annualLeaveTaken.setTakenQty(1);            

                            PstAlStockTaken.insertExc(annualLeaveTaken);

                            //System.out.println(">>> TAKEN NEW UNPAID LEAVE ");
                            //System.out.println(">>> OID = " + specialLeaveTaken.getOID());
                        }
                        catch(Exception ex){
                            System.out.println("Error inserting annual leave taken " + ex.getMessage());
                        }    
                        
                    }
                    
                //} // annual leave iteration
                
            //} // vAnnualLeaveStock != null

        } // unpaid leave 
          
        return isSuccess;
    }    
    
    //Artha ": cek type
    public static int getLeaveSchType(Hashtable hLeave, long leaveOid){
	int type = -1;
	
	String key = String.valueOf(leaveOid);

        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();
        Hashtable hSysLeaveSO = new Hashtable();
        Hashtable hSysLeaveOF = new Hashtable();
        
	hSysLeaveDP = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT));
	hSysLeaveSP = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE));
	hSysLeaveLL = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE));
	hSysLeaveAL = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE));
        hSysLeaveSO = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF));
        hSysLeaveOF = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_OFF));
        
        if(hSysLeaveDP.containsKey(key)){
		return PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT;
	}else if(hSysLeaveSP.containsKey(key)){
		return PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
	}else if(hSysLeaveLL.containsKey(key)){
		return PstScheduleCategory.CATEGORY_LONG_LEAVE;
	}else if(hSysLeaveAL.containsKey(key)){
		return PstScheduleCategory.CATEGORY_ANNUAL_LEAVE;
	}else if(hSysLeaveSO.containsKey(key)){
		return PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF;
	}else if(hSysLeaveOF.containsKey(key)){
		return PstScheduleCategory.CATEGORY_OFF;
	}
        
	return type;
}
    
}