/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;

import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.Presence;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.utility.service.presence.PresenceAnalyser;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.DateCalc;
import com.dimata.util.Formater;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class SaverData {
    static Period prevPeriodServiceManager = null;
    static Period nextPeriodServiceManager = null;
    static Period currPeriodServiceManager = null;
    public static synchronized AnalyseStatusDataPresence saveTransactionVer2(MachineTransaction transaction) throws DBException {
        //update by satrya 2013-08-06
        //public static synchronized boolean saveTransactionVer2(MachineTransaction transaction) throws DBException {
        boolean isSuccess = false;
        //update by satrya 2013-08-06
         AnalyseStatusDataPresence analyseStsDataPresence = new AnalyseStatusDataPresence();
        if (transaction != null) {
            Presence presence = new Presence();
            int presenceStatus = 0;
            long oidPresence = 0;
            long empOid = 0;
            try {
                //update by satrya 2012-06-13
                if(transaction.getCardId()!=null && transaction.getCardId().length()>=1){
                    //update by satrya 2013-08-30
                    //if(transaction.getCardId()!=null && transaction.getCardId().length()>1){
                empOid = PstEmployee.getEmployeeByBarcode(transaction.getCardId());
                if (empOid == 0) { // try get employee id by employee number;
                        String barcode00 = "000000"+transaction.getCardId();
                        if (barcode00.length() > 6){
                            barcode00 = barcode00.substring((barcode00.length()-6), barcode00.length());
                        }
                        empOid = PstEmployee.getEmployeeByBarcode(barcode00);
                        
                    if(empOid==0){
                        empOid = PstEmployee.getEmployeeByLikeBarcode(transaction.getCardId());
//                        if(empOid==0){
//                            empOid = PstEmployee.getEmployeeIdByNum(transaction.getCardId());
//                            if(empOid==0){
//                                empOid = PstEmployee.getEmployeeIdByLikeNum(transaction.getCardId());
//                            }                                                    
//                        }
                    }
                }
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
            if (transaction != null && transaction.getMode() != null && transaction.getMode().length() > 0) {
                     
                switch (transaction.getMode().charAt(0)) {
                    case 'I':
                        presence.setStatus(Presence.STATUS_IN);
                        presenceStatus = Presence.STATUS_IN;
                        break;
                    case 'A':
                        presence.setStatus(Presence.STATUS_IN);
                        presenceStatus = Presence.STATUS_IN;
                        break;
                    case 'B':
                        presence.setStatus(Presence.STATUS_OUT);
                        presenceStatus = Presence.STATUS_OUT;
                        break;
                    case 'O':
                        presence.setStatus(Presence.STATUS_OUT);
                        presenceStatus = Presence.STATUS_OUT;
                        break;
                    case 'C':
                        presence.setStatus(Presence.STATUS_OUT_ON_DUTY);
                        presenceStatus = Presence.STATUS_OUT_ON_DUTY;
                        break;
                    case 'D':
                        presence.setStatus(Presence.STATUS_IN_PERSONAL);
                        presenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;
                    case 'E':
                        presence.setStatus(Presence.STATUS_IN_PERSONAL);
                        presenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;
                    case 'F':
                        presence.setStatus(Presence.STATUS_CALL_BACK);
                        presenceStatus = Presence.STATUS_CALL_BACK;
                        break;
                    default:
                        if (transaction.getVerify() == MachineTransaction.VERIFY_VALID) {
                            presenceStatus = Presence.STATUS_TBD_IN_OUT;
                        } else {
                            presenceStatus = Presence.STATUS_INVALID;
                        }
                        break;
                }
            }//update by satrya 2012-08-18 
            else{
                presenceStatus = Presence.STATUS_TBD_IN_OUT;
                presence.setStatus(Presence.STATUS_TBD_IN_OUT);
            } 

            //inserting downloaded data to database                                   
            presence.setEmployeeId(empOid);
            if (empOid == 0) {
                presence.setStatus(Presence.STATUS_INVALID);
            }
            presence.setPresenceDatetime(transaction.getDateTransaction());
            presence.setAnalyzed(0);
            if( !PstPresence.presenceExist(presence)){
                oidPresence = PstPresence.insertExc(presence);
            }

            if (empOid != 0 && oidPresence!=0) {
                isSuccess = true;
                //long periodId = PstPeriod.getPeriodIdBySelectedDate(transaction.getDateTransaction());
                //update by satrya 2012-09-21
                //if(currPeriodServiceManager==null || !( DateCalc.dayDifference(currPeriodServiceManager.getStartDate(), presence.getPresenceDatetime())<= 0  &&
                       //  DateCalc.dayDifference(presence.getPresenceDatetime(), currPeriodServiceManager.getEndDate())>= 0 )){
                    currPeriodServiceManager = PstPeriod.getPeriodBySelectedDate(presence.getPresenceDatetime());
                    prevPeriodServiceManager = PstPeriod.getPrevPeriodBySelectedDate(presence.getPresenceDatetime());
                    nextPeriodServiceManager = PstPeriod.getNextPeriodBySelectedDate(presence.getPresenceDatetime());
                //}
                int updatedFieldIndex = -1;
                long updatePeriodId = 0;

                //Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdatedVer2(periodId, empOid, presence, transaction.getDateTransaction());
                //update by satrya 2012-09-03
                Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdatedVer2(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presence, transaction.getDateTransaction());

                if (vectFieldIndex != null && (vectFieldIndex.size() == 2 || vectFieldIndex.size()==4)) {
                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));                
                    int updateStatus = 0;

                    try {
                        updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, empOid, updatedFieldIndex, transaction.getDateTransaction());
                        if (updateStatus > 0) {  // comment out by Kartika, karena invalid data nanti ada di hr_presece
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            PstPresence.updateExc(presence);
                            //isSuccess = true;
                        }
                        if(vectFieldIndex.size()==4){
                            // update  data Out di table hr_emp_schedule = time in  (Date) vectFieldIndex.get(4)
                            //kasusnya jika yg di add 2 kali seperti
                                // -  ///kasus jika schedule yg tidak ada cross day tpi employe punch Out melewati hari
                                // -    /// yang schedule nya salah
                                // sehingga vectFieldIndex menjadi == 4
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(2))); 
                           // updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, empOid, updatedFieldIndex, (Date)vectFieldIndex.get(3));
                        }

                    } 
                    catch (Exception e) {
                        System.out.println("[ERROR]::::Update Presence exc : " + e.toString());
                    }                
                }else{
                    //parametter
                    //status == personal In/ personal OUT/OUT of Duty/Call Back
                    // Schedule_Type !=0
                    if(presence.getStatus() == Presence.STATUS_IN_PERSONAL || presence.getStatus() == Presence.STATUS_OUT_PERSONAL
                        ||presence.getStatus() == Presence.STATUS_OUT_ON_DUTY || presence.getStatus() == Presence.STATUS_CALL_BACK
                       && presence.getScheduleType() !=0){
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            PstPresence.updateExc(presence);
                }
            }
                //update by satrya 2013-08-06
                 analyseStsDataPresence.setSuccess(isSuccess);
               if(presence.getOID()!=0){
                analyseStsDataPresence.setPresenceId(""+presence.getOID());
               }
            }
          
        }
        return analyseStsDataPresence;
    }
    
/**
 * create by satrya 2013-12-21
 * Keterangan: untuk melakukan pencarian schedule tanpa overtime
 * @param transaction
 * @return
 * @throws DBException 
 */
  public static synchronized AnalyseStatusDataPresence saveTransactionWithNoOvertime(MachineTransaction transaction) throws DBException {
        boolean isSuccess = false;
        //update by satrya 2013-08-06
         AnalyseStatusDataPresence analyseStsDataPresence = new AnalyseStatusDataPresence();
        if (transaction != null) {
            Presence presence = new Presence();
            int presenceStatus = 0;
            long oidPresence = 0;
            long empOid = 0;
            try {
                //update by satrya 2012-06-13
                if(transaction.getCardId()!=null && transaction.getCardId().length()>=1){
                    //update by satrya 2013-08-30
                    //if(transaction.getCardId()!=null && transaction.getCardId().length()>1){
                empOid = PstEmployee.getEmployeeByBarcode(transaction.getCardId());
                if (empOid == 0) { // try get employee id by employee number;
                    String barcode00 = "000000"+transaction.getCardId();
                        if (barcode00.length() > 6){
                            barcode00 = barcode00.substring((barcode00.length()-6), barcode00.length());
                        }
                        empOid = PstEmployee.getEmployeeByBarcode(barcode00);
                    if(empOid==0){
                        empOid = PstEmployee.getEmployeeByLikeBarcode(transaction.getCardId());
//                        if(empOid==0){
//                            empOid = PstEmployee.getEmployeeIdByNum(transaction.getCardId());
//                            if(empOid==0){
//                                empOid = PstEmployee.getEmployeeIdByLikeNum(transaction.getCardId());
//                            }                                                    
//                        }
                    }
                }
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
            if (transaction != null && transaction.getMode() != null && transaction.getMode().length() > 0) {
                     
                switch (transaction.getMode().charAt(0)) {
                    case 'I':
                        presence.setStatus(Presence.STATUS_IN);
                        presenceStatus = Presence.STATUS_IN;
                        break;
                    case 'A':
                        presence.setStatus(Presence.STATUS_IN);
                        presenceStatus = Presence.STATUS_IN;
                        break;
                    case 'B':
                        presence.setStatus(Presence.STATUS_OUT);
                        presenceStatus = Presence.STATUS_OUT;
                        break;
                    case 'O':
                        presence.setStatus(Presence.STATUS_OUT);
                        presenceStatus = Presence.STATUS_OUT;
                        break;
                    case 'C':
                        presence.setStatus(Presence.STATUS_OUT_ON_DUTY);
                        presenceStatus = Presence.STATUS_OUT_ON_DUTY;
                        break;
                    case 'D':
                        presence.setStatus(Presence.STATUS_IN_PERSONAL);
                        presenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;
                    case 'E':
                        presence.setStatus(Presence.STATUS_IN_PERSONAL);
                        presenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;
                    case 'F':
                        presence.setStatus(Presence.STATUS_CALL_BACK);
                        presenceStatus = Presence.STATUS_CALL_BACK;
                        break;
                    default:
                        if (transaction.getVerify() == MachineTransaction.VERIFY_VALID) {
                            presenceStatus = Presence.STATUS_TBD_IN_OUT;
                        } else {
                            presenceStatus = Presence.STATUS_INVALID;
                        }
                        break;
                }
            }//update by satrya 2012-08-18 
            else{
                presenceStatus = Presence.STATUS_TBD_IN_OUT;
                presence.setStatus(Presence.STATUS_TBD_IN_OUT);
            } 

            //inserting downloaded data to database                                   
            presence.setEmployeeId(empOid);
            if (empOid == 0) {
                presence.setStatus(Presence.STATUS_INVALID);
            }
            presence.setPresenceDatetime(transaction.getDateTransaction());
            presence.setAnalyzed(0);
            if( !PstPresence.presenceExist(presence)){
                oidPresence = PstPresence.insertExc(presence);
            }

            if (empOid != 0 && oidPresence!=0) {
                isSuccess = true;
                //long periodId = PstPeriod.getPeriodIdBySelectedDate(transaction.getDateTransaction());
                //update by satrya 2012-09-21
                //if(currPeriodServiceManager==null || !( DateCalc.dayDifference(currPeriodServiceManager.getStartDate(), presence.getPresenceDatetime())<= 0  &&
                       //  DateCalc.dayDifference(presence.getPresenceDatetime(), currPeriodServiceManager.getEndDate())>= 0 )){
                    currPeriodServiceManager = PstPeriod.getPeriodBySelectedDate(presence.getPresenceDatetime());
                    prevPeriodServiceManager = PstPeriod.getPrevPeriodBySelectedDate(presence.getPresenceDatetime());
                    nextPeriodServiceManager = PstPeriod.getNextPeriodBySelectedDate(presence.getPresenceDatetime());
                //}
                int updatedFieldIndex = -1;
                long updatePeriodId = 0;

                //Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdatedVer2(periodId, empOid, presence, transaction.getDateTransaction());
                //update by satrya 2012-09-03
                Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdatedVer2(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presence, transaction.getDateTransaction());

                if (vectFieldIndex != null && (vectFieldIndex.size() == 2 || vectFieldIndex.size()==4)) {
                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));                
                    int updateStatus = 0;

                    try {
                        updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, empOid, updatedFieldIndex, transaction.getDateTransaction());
                        if (updateStatus > 0) {  // comment out by Kartika, karena invalid data nanti ada di hr_presece
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            PstPresence.updateExc(presence);
                            //isSuccess = true;
                        }
                        if(vectFieldIndex.size()==4){
                            // update  data Out di table hr_emp_schedule = time in  (Date) vectFieldIndex.get(4)
                            //kasusnya jika yg di add 2 kali seperti
                                // -  ///kasus jika schedule yg tidak ada cross day tpi employe punch Out melewati hari
                                // -    /// yang schedule nya salah
                                // sehingga vectFieldIndex menjadi == 4
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(2))); 
                           // updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, empOid, updatedFieldIndex, (Date)vectFieldIndex.get(3));
                        }

                    } 
                    catch (Exception e) {
                        System.out.println("[ERROR]::::Update Presence exc : " + e.toString());
                    }                
                }else{
                    //parametter
                    //status == personal In/ personal OUT/OUT of Duty/Call Back
                    // Schedule_Type !=0
                    if(presence.getStatus() == Presence.STATUS_IN_PERSONAL || presence.getStatus() == Presence.STATUS_OUT_PERSONAL
                        ||presence.getStatus() == Presence.STATUS_OUT_ON_DUTY || presence.getStatus() == Presence.STATUS_CALL_BACK
                       && presence.getScheduleType() !=0){
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            PstPresence.updateExc(presence);
                }
            }
                //update by satrya 2013-08-06
                 analyseStsDataPresence.setSuccess(isSuccess);
               if(presence.getOID()!=0){
                analyseStsDataPresence.setPresenceId(""+presence.getOID());
               }
            }
          
        }
        return analyseStsDataPresence;
    }
    
/**
 * create by satrya 2013-07-08
 * @param transaction
 * @return
 * @throws DBException 
 */
public static synchronized AnalyseStatusDataPresence saveTransactionByActualInOuT(MachineTransaction transaction,Hashtable hashSchSymbol) throws DBException {
        boolean isSuccess = false;
        AnalyseStatusDataPresence analyseStsDataPresence = new AnalyseStatusDataPresence();
        if (transaction != null) {
            Presence presence = new Presence();
            int presenceStatus = 0;
            long oidPresence = 0;
            long empOid = 0;
            try {
                //update by satrya 2012-06-13
                if(transaction.getCardId()!=null && transaction.getCardId().length()>=1){
                    //update by satrya 2013-08-30
                    //if(transaction.getCardId()!=null && transaction.getCardId().length()>1){
                empOid = PstEmployee.getEmployeeByBarcode(transaction.getCardId());
                if (empOid == 0) { // try get employee id by employee number;
                     String barcode00 = "000000"+transaction.getCardId();
                        if (barcode00.length() > 6){
                            barcode00 =barcode00.substring((barcode00.length()-6), barcode00.length());
                        }
                        empOid = PstEmployee.getEmployeeByBarcode(barcode00);
                    if(empOid==0){
                       
                        empOid = PstEmployee.getEmployeeByLikeBarcode(transaction.getCardId());
//                        if(empOid==0){
//                            empOid = PstEmployee.getEmployeeIdByNum(transaction.getCardId());
//                            if(empOid==0){
//                                empOid = PstEmployee.getEmployeeIdByLikeNum(transaction.getCardId());
//                            }                                                    
//                        }
                    }
                }
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
            if (transaction != null && transaction.getMode() != null && transaction.getMode().length() > 0) {
                     
                switch (transaction.getMode().charAt(0)) {
                    case '0':
                        presence.setStatus(Presence.STATUS_IN);
                        presenceStatus = Presence.STATUS_IN;
                        break;
                    case '1':
                        presence.setStatus(Presence.STATUS_OUT);
                        presenceStatus = Presence.STATUS_OUT;
                        break;
                    case '2':
                        presence.setStatus(Presence.STATUS_OUT_PERSONAL);
                        presenceStatus = Presence.STATUS_OUT_PERSONAL;
                        break;
                    case '3':
                        presence.setStatus(Presence.STATUS_IN_PERSONAL);
                        presenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;
                    case '4':
                        presence.setStatus(Presence.STATUS_IN);
                        presenceStatus = Presence.STATUS_IN;
                        break;
                    case '5':
                        presence.setStatus(Presence.STATUS_OUT);
                        presenceStatus = Presence.STATUS_OUT;
                        break;
                    case '6':
                        presence.setStatus(Presence.STATUS_INVALID);
                        presenceStatus = Presence.STATUS_INVALID;
                        break;
                   
                    default:
                        if (transaction.getVerify() == MachineTransaction.VERIFY_VALID) {
                            presenceStatus = Presence.STATUS_TBD_IN_OUT;
                        } else {
                            presenceStatus = Presence.STATUS_INVALID;
                        }
                        break;
                }
            }//update by satrya 2012-08-18 
            else{
                presenceStatus = Presence.STATUS_TBD_IN_OUT;
                presence.setStatus(Presence.STATUS_TBD_IN_OUT);
            } 

            //inserting downloaded data to database                                   
            presence.setEmployeeId(empOid);
            if (empOid == 0) {
                presence.setStatus(Presence.STATUS_INVALID);
            }
            presence.setPresenceDatetime(transaction.getDateTransaction());
            presence.setPresenceRounded(transaction.getDateTransaction());
            presence.setAnalyzed(0);
            if( !PstPresence.presenceExist(presence)){
                oidPresence = PstPresence.insertExc(presence);
            }

            if (empOid != 0 && oidPresence!=0) {
                isSuccess = true;
                //long periodId = PstPeriod.getPeriodIdBySelectedDate(transaction.getDateTransaction());
                //update by satrya 2012-09-21
                //if(currPeriodServiceManager==null || !( DateCalc.dayDifference(currPeriodServiceManager.getStartDate(), presence.getPresenceDatetime())<= 0  &&
                       //  DateCalc.dayDifference(presence.getPresenceDatetime(), currPeriodServiceManager.getEndDate())>= 0 )){
                    currPeriodServiceManager = PstPeriod.getPeriodBySelectedDate(presence.getPresenceDatetime());
                    prevPeriodServiceManager = PstPeriod.getPrevPeriodBySelectedDate(presence.getPresenceDatetime());
                    nextPeriodServiceManager = PstPeriod.getNextPeriodBySelectedDate(presence.getPresenceDatetime());
                //}
                int updatedFieldIndex = -1;
                long updatePeriodId = 0;

                
                Vector vectFieldIndex = null;
               
                if(presence.getPresenceDatetime()!=null){
                    boolean isCrossDays = false;
                     int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(presence.getPresenceDatetime());
                     int idxSch1OnPresenceIn = PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1;
                    // int idxSch1OnPresenceOut = PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1;
                    Date existPresenceSch1In = PstEmpSchedule.getPresenceOnSchedule(idxSch1OnPresenceIn, empOid, currPeriodServiceManager.getOID());
                    
                    
                    boolean isInNull = true;
                    if(existPresenceSch1In!=null || presence.getStatus()==Presence.STATUS_IN){
                        //mencari dan merubah schedulenya
                        isInNull=false;
                      if(presence.getStatus()==Presence.STATUS_IN){
                        vectFieldIndex =PstEmpSchedule.getFieldIndexSearchSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,false);
                      }else{
                         ///  Time Out mengikuti schedule IN
                          //jika dia ada di dalam out(presence out 2013-07-21) cari IN hari ini 2013-07-2013,jika IN hari ini belum ada kemungkinan presenceOUt tsb adalah Out kemaren
                        if(existPresenceSch1In!=null && presence.getStatus()==Presence.STATUS_OUT){
                            isCrossDays = false;
                            vectFieldIndex =PstEmpSchedule.getFieldIndexTableSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,isCrossDays,0,0,0,null);
                        }else{
                            isCrossDays = true;
                             vectFieldIndex =PstEmpSchedule.getFieldIndexTableSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,isCrossDays,0,0,0,null);
                        }
                        
                      }
                    }else{
                        Date dtYesterday = new Date(presence.getPresenceDatetime().getYear(), presence.getPresenceDatetime().getMonth(), presence.getPresenceDatetime().getDate() - 1);
                    long periodIdInOutYesterday = 0;
                    int idxFieldNameYesterday = PstEmpSchedule.getIdxNameOfTableBySelectedDate(dtYesterday);
                     int idxSch1OnPresenceYesterdayOut = PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldNameYesterday - 1;
                    if (currPeriodServiceManager!=null && DateCalc.dayDifference(presence.getPresenceDatetime(), currPeriodServiceManager.getStartDate()) == 0) {
                        periodIdInOutYesterday = prevPeriodServiceManager!=null?prevPeriodServiceManager.getOID():0;
                    } else {
                        periodIdInOutYesterday = currPeriodServiceManager!=null?currPeriodServiceManager.getOID():0;
                    }
                    long oidSchedule = PstEmpSchedule.getOidScheduleSymbolOnSchedule(idxSch1OnPresenceYesterdayOut, empOid, periodIdInOutYesterday);
                    ScheduleSymbol scheduleSymbol = PstScheduleSymbol.getSchedule(oidSchedule,hashSchSymbol);
                    isCrossDays=false;//artinya bahwa schedule yg kemarin adalah crossdays
                    long longSchld1stIn=0;
                    
                    if(scheduleSymbol!=null && scheduleSymbol.getTimeIn()!=null && scheduleSymbol.getTimeOut()!=null
                            && scheduleSymbol.getTimeIn().getHours()> scheduleSymbol.getTimeOut().getHours()){
                        isCrossDays=true;
                    }
                    long longOutYesterdaySchdl = PstEmpSchedule.getLongPresenceDateYesterday(PstEmpSchedule.INT_FIRST_SCHEDULE, Presence.STATUS_OUT, presence.getEmployeeId(), presence.getPresenceDatetime());
                    /*Date dtTemp = new Date(longOutYesterdaySchdl);
                    Date dtAverage = new Date(dtTemp.getYear(), dtTemp.getMonth(), dtTemp.getDate(), dtTemp.getHours() + PstEmpSchedule.RESIDUE_TIME_ON_PRESENCE, dtTemp.getMinutes(), 0);
                    long lAverage = dtAverage.getTime();*/
                    if(scheduleSymbol!=null && scheduleSymbol.getTimeIn()!=null){
                        longSchld1stIn = (new Date(presence.getPresenceDatetime().getYear(), presence.getPresenceDatetime().getMonth(), presence.getPresenceDatetime().getDate(), scheduleSymbol.getTimeIn().getHours(), scheduleSymbol.getTimeIn().getMinutes(), scheduleSymbol.getTimeIn().getSeconds())).getTime();
                    }
                     long lookUpBeforIn = (longSchld1stIn - longOutYesterdaySchdl) > (PstEmpSchedule.MAX_NORMAL_SCH_DISTANCE_TODAY)
                            ? Math.abs(((longSchld1stIn - longOutYesterdaySchdl)) / 2)
                            : (Math.abs(((longSchld1stIn - longOutYesterdaySchdl)) / 5));
                            
                     long lookUpAfterOutYesterday = ((longSchld1stIn - longOutYesterdaySchdl) > (PstEmpSchedule.MAX_NORMAL_SCH_DISTANCE_TODAY))?Math.abs(((longSchld1stIn - longOutYesterdaySchdl))) - lookUpBeforIn: - PstEmpSchedule.MAX_NORMAL_SCH_DISTANCE_TODAY;
                      long longPresenceTime = presence.getPresenceDatetime().getTime();
                            ScheduleSymbol schYesterday = PstEmpSchedule.getScheduleDateTimeYesterday(PstEmpSchedule.INT_FIRST_SCHEDULE, empOid, presence.getPresenceDatetime());//dtYesterday
                            long longInYesterdaySchdl = schYesterday != null && schYesterday.getTimeIn() != null ? schYesterday.getTimeIn().getTime() : 0L;
//mencari jika dia masih di schedule maka sesuaikan dengan In, jika tidak maka mencari schedule baru
                            long lookUpBeforeOutYesterday = Math.abs((longOutYesterdaySchdl - longInYesterdaySchdl) / 2);
                    ///mengecek apakakah dia cross day? jika ia, maka Tyme Out akan menyesuaikan schedule yg kemarin
                        if(existPresenceSch1In==null || presence.getStatus()==Presence.STATUS_OUT){
                            // update by satrya 2013-07-15 karna masalah cross days if(existPresenceSch1In==null && presence.getStatus()==Presence.STATUS_OUT && isCrossDays==false){
                            isInNull=true;
                               //range batas out = 16 jam
                            long longPresence = presence.getPresenceDatetime().getTime();
                            // if(isCrossDays || ((longPresenceTime <= (longOutYesterdaySchdl + lookUpAfterOutYesterday))) && presence.getStatus()==Presence.STATUS_OUT){
                            if(isCrossDays || ((longPresenceTime <= (longOutYesterdaySchdl + lookUpAfterOutYesterday))) && presence.getStatus()==Presence.STATUS_OUT){
                                vectFieldIndex =PstEmpSchedule.getFieldIndexTableSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager,
                                        empOid, presenceStatus,isInNull, presence,isCrossDays,
                                        longOutYesterdaySchdl,lookUpBeforeOutYesterday,lookUpAfterOutYesterday,existPresenceSch1In); 
                            }else{
                                vectFieldIndex =PstEmpSchedule.getFieldIndexSearchSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,false);
                            }
                            ///merubah schedule outnya
                        }else{
                            ///  Time Out mengikuti schedule IN
                            vectFieldIndex =PstEmpSchedule.getFieldIndexTableSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,isCrossDays,longOutYesterdaySchdl,lookUpBeforeOutYesterday,lookUpAfterOutYesterday,existPresenceSch1In);
                        }
                    }
                }
                if (vectFieldIndex != null && (vectFieldIndex.size() == 2 || vectFieldIndex.size()==3 ||vectFieldIndex.size()==4)) {
                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1))); 
                    long updateScheduleId = Long.parseLong(String.valueOf(vectFieldIndex.get(2)));
                    int indDtSchedule = Integer.parseInt(String.valueOf(vectFieldIndex.get(3)));
                    int updateStatus = 0;
                   
                    try {
                        updateStatus = PstEmpSchedule.updateScheduleDataByPresenceVer2(updatePeriodId, empOid,updateScheduleId, updatedFieldIndex, transaction.getDateTransaction(),indDtSchedule);
                        if (updateStatus > 0) {  // comment out by Kartika, karena invalid data nanti ada di hr_presece
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            PstPresence.updateExc(presence);
                            
                            //isSuccess = true;
                        }
                        if(vectFieldIndex.size()==4){
                            // update  data Out di table hr_emp_schedule = time in  (Date) vectFieldIndex.get(4)
                            //kasusnya jika yg di add 2 kali seperti
                                // -  ///kasus jika schedule yg tidak ada cross day tpi employe punch Out melewati hari
                                // -    /// yang schedule nya salah
                                // sehingga vectFieldIndex menjadi == 4
                           // updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(2))); 
                           // updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, empOid, updatedFieldIndex, (Date)vectFieldIndex.get(3));
                        }

                    } 
                    catch (Exception e) {
                        System.out.println("[ERROR]::::Update Presence exc : " + e.toString());
                    }                
                }else{
                    //parametter
                    //status == personal In/ personal OUT/OUT of Duty/Call Back
                    // Schedule_Type !=0
                    if(presence.getStatus() == Presence.STATUS_IN_PERSONAL || presence.getStatus() == Presence.STATUS_OUT_PERSONAL
                        ||presence.getStatus() == Presence.STATUS_OUT_ON_DUTY || presence.getStatus() == Presence.STATUS_CALL_BACK
                       && presence.getScheduleType() !=0){
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            PstPresence.updateExc(presence);
                }
            }
                analyseStsDataPresence.setSuccess(isSuccess);
               if(presence.getOID()!=0){
                analyseStsDataPresence.setPresenceId(""+presence.getOID());
               }
           }
          
        } 
        return analyseStsDataPresence;
    }

/**
 * create by satrya 2013-07-29
 * @param presence
 * @param hashSchSymbol
 * @return
 * @throws DBException 
 */
public static synchronized AnalyseStatusDataPresence saveManualTransactionByActualInOuT(Presence presence,Hashtable hashSchSymbol) throws DBException {
        boolean isSuccess = false;
        AnalyseStatusDataPresence analyseStsDataPresence = new AnalyseStatusDataPresence();
        if (presence != null && presence.getPresenceDatetime()!=null) {
            long oidPresence =  presence.getOID();
            long empOid = presence.getEmployeeId();
            int presenceStatus = presence.getStatus();
            if (empOid != 0 && oidPresence!=0) {
                isSuccess = true;
                    currPeriodServiceManager = PstPeriod.getPeriodBySelectedDate(presence.getPresenceDatetime());
                    prevPeriodServiceManager = PstPeriod.getPrevPeriodBySelectedDate(presence.getPresenceDatetime());
                    nextPeriodServiceManager = PstPeriod.getNextPeriodBySelectedDate(presence.getPresenceDatetime());
            
                int updatedFieldIndex = -1;
                long updatePeriodId = 0;

                
                Vector vectFieldIndex = null;
               
                if(presence.getPresenceDatetime()!=null){
                    boolean isCrossDays = false;
                     int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(presence.getPresenceDatetime());
                     int idxSch1OnPresenceIn = PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1;
                    // int idxSch1OnPresenceOut = PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1;
                    Date existPresenceSch1In = PstEmpSchedule.getPresenceOnSchedule(idxSch1OnPresenceIn, empOid, currPeriodServiceManager.getOID());
                    
                    
                    boolean isInNull = true;
                    if(existPresenceSch1In!=null || presence.getStatus()==Presence.STATUS_IN){
                        //mencari dan merubah schedulenya
                        isInNull=false;
                      if(presence.getStatus()==Presence.STATUS_IN){
                        vectFieldIndex =PstEmpSchedule.getFieldIndexSearchSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,false);
                      }else{
                         ///  Time Out mengikuti schedule IN
                          //jika dia ada di dalam out(presence out 2013-07-21) cari IN hari ini 2013-07-2013,jika IN hari ini belum ada kemungkinan presenceOUt tsb adalah Out kemaren
                        if(existPresenceSch1In!=null && presence.getStatus()==Presence.STATUS_OUT){
                            isCrossDays = false;
                            vectFieldIndex =PstEmpSchedule.getFieldIndexTableSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,isCrossDays,0,0,0,null);
                        }else{
                            isCrossDays = true;
                             vectFieldIndex =PstEmpSchedule.getFieldIndexTableSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,isCrossDays,0,0,0,null);
                        }
                        
                      }
                    }else{
                        Date dtYesterday = new Date(presence.getPresenceDatetime().getYear(), presence.getPresenceDatetime().getMonth(), presence.getPresenceDatetime().getDate() - 1);
                    long periodIdInOutYesterday = 0;
                    int idxFieldNameYesterday = PstEmpSchedule.getIdxNameOfTableBySelectedDate(dtYesterday);
                     int idxSch1OnPresenceYesterdayOut = PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldNameYesterday - 1;
                    if (currPeriodServiceManager!=null && DateCalc.dayDifference(presence.getPresenceDatetime(), currPeriodServiceManager.getStartDate()) == 0) {
                        periodIdInOutYesterday = prevPeriodServiceManager!=null?prevPeriodServiceManager.getOID():0;
                    } else {
                        periodIdInOutYesterday = currPeriodServiceManager!=null?currPeriodServiceManager.getOID():0;
                    }
                    long oidSchedule = PstEmpSchedule.getOidScheduleSymbolOnSchedule(idxSch1OnPresenceYesterdayOut, empOid, periodIdInOutYesterday);
                    ScheduleSymbol scheduleSymbol = PstScheduleSymbol.getSchedule(oidSchedule,hashSchSymbol);
                    isCrossDays=false;//artinya bahwa schedule yg kemarin adalah crossdays
                    long longSchld1stIn=0;
                    
                    if(scheduleSymbol!=null && scheduleSymbol.getTimeIn()!=null && scheduleSymbol.getTimeOut()!=null
                            && scheduleSymbol.getTimeIn().getHours()> scheduleSymbol.getTimeOut().getHours()){
                        isCrossDays=true;
                    }
                    long longOutYesterdaySchdl = PstEmpSchedule.getLongPresenceDateYesterday(PstEmpSchedule.INT_FIRST_SCHEDULE, Presence.STATUS_OUT, presence.getEmployeeId(), presence.getPresenceDatetime());
                    /*Date dtTemp = new Date(longOutYesterdaySchdl);
                    Date dtAverage = new Date(dtTemp.getYear(), dtTemp.getMonth(), dtTemp.getDate(), dtTemp.getHours() + PstEmpSchedule.RESIDUE_TIME_ON_PRESENCE, dtTemp.getMinutes(), 0);
                    long lAverage = dtAverage.getTime();*/
                    if(scheduleSymbol!=null && scheduleSymbol.getTimeIn()!=null){
                        longSchld1stIn = (new Date(presence.getPresenceDatetime().getYear(), presence.getPresenceDatetime().getMonth(), presence.getPresenceDatetime().getDate(), scheduleSymbol.getTimeIn().getHours(), scheduleSymbol.getTimeIn().getMinutes(), scheduleSymbol.getTimeIn().getSeconds())).getTime();
                    }
                     long lookUpBeforIn = (longSchld1stIn - longOutYesterdaySchdl) > (PstEmpSchedule.MAX_NORMAL_SCH_DISTANCE_TODAY)
                            ? Math.abs(((longSchld1stIn - longOutYesterdaySchdl)) / 2)
                            : (Math.abs(((longSchld1stIn - longOutYesterdaySchdl)) / 5));
                            
                     long lookUpAfterOutYesterday = ((longSchld1stIn - longOutYesterdaySchdl) > (PstEmpSchedule.MAX_NORMAL_SCH_DISTANCE_TODAY))?Math.abs(((longSchld1stIn - longOutYesterdaySchdl))) - lookUpBeforIn: - PstEmpSchedule.MAX_NORMAL_SCH_DISTANCE_TODAY;
                      long longPresenceTime = presence.getPresenceDatetime().getTime();
                            ScheduleSymbol schYesterday = PstEmpSchedule.getScheduleDateTimeYesterday(PstEmpSchedule.INT_FIRST_SCHEDULE, empOid, presence.getPresenceDatetime());//dtYesterday
                            long longInYesterdaySchdl = schYesterday != null && schYesterday.getTimeIn() != null ? schYesterday.getTimeIn().getTime() : 0L;
//mencari jika dia masih di schedule maka sesuaikan dengan In, jika tidak maka mencari schedule baru
                            long lookUpBeforeOutYesterday = Math.abs((longOutYesterdaySchdl - longInYesterdaySchdl) / 2);
                    ///mengecek apakakah dia cross day? jika ia, maka Tyme Out akan menyesuaikan schedule yg kemarin
                        if(existPresenceSch1In==null || presence.getStatus()==Presence.STATUS_OUT){
                            // update by satrya 2013-07-15 karna masalah cross days if(existPresenceSch1In==null && presence.getStatus()==Presence.STATUS_OUT && isCrossDays==false){
                            isInNull=true;
                               //range batas out = 16 jam
                            //long longPresence = presence.getPresenceDatetime().getTime();
                            // if(isCrossDays || ((longPresenceTime <= (longOutYesterdaySchdl + lookUpAfterOutYesterday))) && presence.getStatus()==Presence.STATUS_OUT){
                            if(isCrossDays || ((longPresenceTime <= (longOutYesterdaySchdl + lookUpAfterOutYesterday))) && presence.getStatus()==Presence.STATUS_OUT){
                                vectFieldIndex =PstEmpSchedule.getFieldIndexTableSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager,
                                        empOid, presenceStatus,isInNull, presence,isCrossDays,
                                        longOutYesterdaySchdl,lookUpBeforeOutYesterday,lookUpAfterOutYesterday,existPresenceSch1In); 
                            }else{
                                vectFieldIndex =PstEmpSchedule.getFieldIndexSearchSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,false);
                            }
                            ///merubah schedule outnya
                        }else{
                            ///  Time Out mengikuti schedule IN
                            vectFieldIndex =PstEmpSchedule.getFieldIndexTableSchedule(prevPeriodServiceManager, currPeriodServiceManager, nextPeriodServiceManager, empOid, presenceStatus,isInNull, presence,isCrossDays,longOutYesterdaySchdl,lookUpBeforeOutYesterday,lookUpAfterOutYesterday,existPresenceSch1In);
                        }
                    }
                }
                if (vectFieldIndex != null && (vectFieldIndex.size() == 2 || vectFieldIndex.size()==3 ||vectFieldIndex.size()==4)) {
                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1))); 
                    long updateScheduleId = Long.parseLong(String.valueOf(vectFieldIndex.get(2)));
                    int indDtSchedule = Integer.parseInt(String.valueOf(vectFieldIndex.get(3)));
                    int updateStatus = 0;
                   
                    try {
                        updateStatus = PstEmpSchedule.updateScheduleDataByPresenceVer2(updatePeriodId, empOid,updateScheduleId, updatedFieldIndex, presence.getPresenceDatetime(),indDtSchedule);
                        if (updateStatus > 0) {  // comment out by Kartika, karena invalid data nanti ada di hr_presece
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            PstPresence.updateExc(presence);
                            
                            //isSuccess = true;
                        }
                        if(vectFieldIndex.size()==4){
                            // update  data Out di table hr_emp_schedule = time in  (Date) vectFieldIndex.get(4)
                            //kasusnya jika yg di add 2 kali seperti
                                // -  ///kasus jika schedule yg tidak ada cross day tpi employe punch Out melewati hari
                                // -    /// yang schedule nya salah
                                // sehingga vectFieldIndex menjadi == 4
                           // updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(2))); 
                           // updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, empOid, updatedFieldIndex, (Date)vectFieldIndex.get(3));
                        }

                    } 
                    catch (Exception e) {
                        System.out.println("[ERROR]::::Update Presence exc : " + e.toString());
                    }                
                }else{
                    //parametter
                    //status == personal In/ personal OUT/OUT of Duty/Call Back
                    // Schedule_Type !=0
                    if(presence.getStatus() == Presence.STATUS_IN_PERSONAL || presence.getStatus() == Presence.STATUS_OUT_PERSONAL
                        ||presence.getStatus() == Presence.STATUS_OUT_ON_DUTY || presence.getStatus() == Presence.STATUS_CALL_BACK
                       && presence.getScheduleType() !=0){
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            PstPresence.updateExc(presence);
                }
            }
                analyseStsDataPresence.setSuccess(isSuccess);
               if(presence.getOID()!=0){
                analyseStsDataPresence.setPresenceId(""+presence.getOID());
               }
           }
          
        } 
        return analyseStsDataPresence;
    }
//update by satrya 2012-07-02
    //update by satrya 2012-09-03
    static Period prevPeriodManual = null;
    static Period nextPeriodManual = null;
    static Period currPeriodManual = null;
    
    public static synchronized AnalyseStatusDataPresence saveManualPresence(Presence presence, long departementId) throws DBException {
   //public static synchronized boolean saveManualPresence(Presence presence, long departementId) throws DBException {
        boolean isSuccess = false;
        AnalyseStatusDataPresence analyseStsDataPresence = new AnalyseStatusDataPresence();
        if (presence != null) {
            //Presence presence = new Presence();
            //update by satrya 2012-09-03
            if (presence.getEmployeeId() != 0 && presence.getOID()!=0) {
                //untuk test
                if(presence.getOID()==504404546898581142L){
                    boolean x=true;
                }
                isSuccess = true;
               /*if(currPeriodManual==null || !( DateCalc.dayDifference(currPeriodManual.getStartDate(), presence.getPresenceDatetime())<= 0  &&
                         DateCalc.dayDifference(presence.getPresenceDatetime(), currPeriodManual.getEndDate())>= 0 )){*/
                    currPeriodManual = PstPeriod.getPeriodBySelectedDate(presence.getPresenceDatetime());
                    prevPeriodManual = PstPeriod.getPrevPeriodBySelectedDate(presence.getPresenceDatetime());
                    nextPeriodManual = PstPeriod.getNextPeriodBySelectedDate(presence.getPresenceDatetime());
                //}
                                
                //long periodId = PstPeriod.getPeriodIdBySelectedDate(presence.getPresenceDatetime());
                int updatedFieldIndex = -1;
                long updatePeriodId = 0;
                 //update by satrya 2012-09-03
                Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdatedVer2(
                        prevPeriodManual,currPeriodManual, nextPeriodManual, presence.getEmployeeId(), presence, 
                        presence.getPresenceDatetime(), departementId );//mencari schedule kerja

               /* if (vectFieldIndex != null && vectFieldIndex.size() == 2) {
                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));                
                    int updateStatus = 0;

                    try {
                        updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presence.getPresenceDatetime());
                        if (updateStatus > 0) {  // comment out by Kartika, karena invalid data nanti ada di hr_presece
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            PstPresence.updateExc(presence);
                            //isSuccess = true;
                        }

                    } catch (Exception e) {
                        System.out.println("[ERROR]::::Update Presence exc : " + e.toString());
                    }                
                }*/
                //update by satrya 2012-08-08
                if (vectFieldIndex != null && (vectFieldIndex.size() == 2 || vectFieldIndex.size()==4)) {
                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));                
                    int updateStatus = 0;
                    
                    try {
                        updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex,  presence.getPresenceDatetime());
                        if (updateStatus > 0) {  // comment out by Kartika, karena invalid data nanti ada di hr_presece
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                           
                           PstPresence.updateExc(presence);
                           
                            //isSuccess = true;
                        }
                        if(vectFieldIndex.size()==4){
                            // update  data Out di table hr_emp_schedule = time in  (Date) vectFieldIndex.get(4)
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(2))); 
                            //updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, (Date)vectFieldIndex.get(3));
            }
          
                    } catch (Exception e) {
                        System.out.println("[ERROR]::::Update Presence exc : " + e.toString());
                    }
                } 
            }
               analyseStsDataPresence.setSuccess(isSuccess);
               if(presence.getOID()!=0){
                analyseStsDataPresence.setPresenceId(""+presence.getOID());
               }
          
        }
        return analyseStsDataPresence;//return isSuccess;
    }
    /**
     * create by satrya 2013-12-21
     * Keterangan: untuk manual menghitung schedule tanpa overtime
     * @param presence
     * @param departementId
     * @return
     * @throws DBException 
     */
    public static synchronized AnalyseStatusDataPresence saveManualPresenceNoOvertime(Presence presence, long departementId) throws DBException {
   //public static synchronized boolean saveManualPresence(Presence presence, long departementId) throws DBException {
        boolean isSuccess = false;
        AnalyseStatusDataPresence analyseStsDataPresence = new AnalyseStatusDataPresence();
        if (presence != null) {
            //Presence presence = new Presence();
            //update by satrya 2012-09-03
            if (presence.getEmployeeId() != 0 && presence.getOID()!=0) {
                isSuccess = true;
               /*if(currPeriodManual==null || !( DateCalc.dayDifference(currPeriodManual.getStartDate(), presence.getPresenceDatetime())<= 0  &&
                         DateCalc.dayDifference(presence.getPresenceDatetime(), currPeriodManual.getEndDate())>= 0 )){*/
                    currPeriodManual = PstPeriod.getPeriodBySelectedDate(presence.getPresenceDatetime());
                    prevPeriodManual = PstPeriod.getPrevPeriodBySelectedDate(presence.getPresenceDatetime());
                    nextPeriodManual = PstPeriod.getNextPeriodBySelectedDate(presence.getPresenceDatetime());
                //}
                                
                //long periodId = PstPeriod.getPeriodIdBySelectedDate(presence.getPresenceDatetime());
                int updatedFieldIndex = -1;
                long updatePeriodId = 0;
                 //update by satrya 2012-09-03
                Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdatedNoOvertime(prevPeriodManual, currPeriodManual, nextPeriodManual, presence.getEmployeeId(), presence, presence.getPresenceDatetime());

                //update by satrya 2012-08-08
                if (vectFieldIndex != null && (vectFieldIndex.size() == 2 || vectFieldIndex.size()==4)) {
                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));                
                    int updateStatus = 0;
                    
                    try {
                        updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex,  presence.getPresenceDatetime());
                        if (updateStatus > 0) {  // comment out by Kartika, karena invalid data nanti ada di hr_presece
                            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                           
                           PstPresence.updateExc(presence);
                           
                            //isSuccess = true;
                }
                        if(vectFieldIndex.size()==4){
                            // update  data Out di table hr_emp_schedule = time in  (Date) vectFieldIndex.get(4)
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(2))); 
                            //updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, (Date)vectFieldIndex.get(3));
            }
          
                    } catch (Exception e) {
                        System.out.println("[ERROR]::::Update Presence exc : " + e.toString());
                    }
                } 
            }
               analyseStsDataPresence.setSuccess(isSuccess);
               if(presence.getOID()!=0){
                analyseStsDataPresence.setPresenceId(""+presence.getOID());
               }
          
        }
        return analyseStsDataPresence;//return isSuccess;
    }

    /**
     * Menyimpan data transaksi yang diperoleh dari mesin
     */
    public static synchronized boolean saveTransaction(MachineTransaction transaction) throws DBException {

        boolean isSuccess = false;

        if (transaction != null) {
            Presence presence = new Presence();
            int presenceStatus = 0;
            long oidPresence = 0;
            long empOid = 0;
            try {
                //update by satrya 2012-06-13
                if(transaction.getCardId()!=null && transaction.getCardId().length()>=1){
                    //update by satrya 2013-08-30
                    //if(transaction.getCardId()!=null && transaction.getCardId().length()>1){
                empOid = PstEmployee.getEmployeeByBarcode(transaction.getCardId());
                if (empOid == 0) { // try get employee id by employee number;
                    String barcode00 = "000000"+transaction.getCardId();
                        if (barcode00.length() > 6){
                            barcode00 = barcode00.substring((barcode00.length()-6), barcode00.length());
                        }
                        empOid = PstEmployee.getEmployeeByBarcode(barcode00);
                    if(empOid==0){
                        empOid = PstEmployee.getEmployeeByLikeBarcode(transaction.getCardId());
//                        if(empOid==0){
//                            empOid = PstEmployee.getEmployeeIdByNum(transaction.getCardId());
//                            if(empOid==0){
//                                empOid = PstEmployee.getEmployeeIdByLikeNum(transaction.getCardId());
//                            }                                                    
//                        }
                    }
                }
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }

            if (transaction != null && transaction.getMode() != null && transaction.getMode().length() > 0) {
                switch (transaction.getMode().charAt(0)) {
                    case 'A':
                        presence.setStatus(Presence.STATUS_IN);
                        presenceStatus = Presence.STATUS_IN;
                        break;
                    case 'B':
                        presence.setStatus(Presence.STATUS_OUT);
                        presenceStatus = Presence.STATUS_OUT;
                        break;
                    case 'C':
                        presence.setStatus(Presence.STATUS_OUT_ON_DUTY);
                        presenceStatus = Presence.STATUS_OUT_ON_DUTY;
                        break;
                    case 'D':
                        presence.setStatus(Presence.STATUS_IN_PERSONAL);
                        presenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;
                    case 'E':
                        presence.setStatus(Presence.STATUS_IN_PERSONAL);
                        presenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;
                    case 'F':
                        presence.setStatus(Presence.STATUS_CALL_BACK);
                        presenceStatus = Presence.STATUS_CALL_BACK;
                        break;
                    default:
                        if (transaction.getVerify() == MachineTransaction.VERIFY_VALID) {
                            presence.setStatus(Presence.STATUS_TBD_IN_OUT);
                            presenceStatus = Presence.STATUS_TBD_IN_OUT;
                        } else {
                            presence.setStatus(Presence.STATUS_INVALID);
                            presenceStatus = Presence.STATUS_INVALID;
                        }
                        break;
                }
            } else{
                presenceStatus = Presence.STATUS_TBD_IN_OUT;
                presence.setStatus(Presence.STATUS_TBD_IN_OUT);
            }

            //inserting downloaded data to database                                   
            presence.setEmployeeId(empOid);
            if (empOid == 0) {
                presence.setStatus(Presence.STATUS_INVALID);
            }
            presence.setPresenceDatetime(transaction.getDateTransaction());
            presence.setAnalyzed(0);
           // presence.setStation(transaction.getStation());
            oidPresence = PstPresence.insertExc(presence);

            if (empOid != 0 && presence.getStatus()!=Presence.STATUS_INVALID) { // if status INVALID maka tidak di analysis hanya di insert ke hr_presence
                //long periodId = PstPeriod.getPeriodIdBySelectedDate(transaction.getDateTransaction());
                int updatedFieldIndex = -1;
                long updatePeriodId = 0;
                //Presence presence = new Presence();
            //update by satrya 2012-09-03
            if (presence.getEmployeeId() != 0 && presence.getOID()!=0) {
                isSuccess = true;
               if(currPeriodServiceManager==null || !( DateCalc.dayDifference(currPeriodServiceManager.getStartDate(), presence.getPresenceDatetime())<= 0  &&
                         DateCalc.dayDifference(presence.getPresenceDatetime(), currPeriodServiceManager.getEndDate())>= 0 )){
                    currPeriodServiceManager = PstPeriod.getPeriodBySelectedDate(presence.getPresenceDatetime());
                    prevPeriodServiceManager = PstPeriod.getPrevPeriodBySelectedDate(presence.getPresenceDatetime());
                    nextPeriodServiceManager = PstPeriod.getNextPeriodBySelectedDate(presence.getPresenceDatetime());
                }

                
                
                //Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdatedVer2(periodId, empOid, presence, transaction.getDateTransaction());
               //update by satrya 2012-09-03
               Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdatedVer2(prevPeriodServiceManager,currPeriodServiceManager,
                       nextPeriodServiceManager, empOid, presence,transaction.getDateTransaction());
                if (vectFieldIndex != null && vectFieldIndex.size() == 2) {
                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                }

                int updateStatus = 0;

                try {
                    updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, empOid, updatedFieldIndex, transaction.getDateTransaction());
                    if (updateStatus > 0) {  
                        presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                        PstPresence.updateExc(presence);
                        isSuccess = true;
                    }

                } catch (Exception e) {
                    System.out.println("[ERROR]::::Update Presence exc : " + e.toString());
                }
            }
        }
       
     }
        return isSuccess;
    }

    /**
     * @Desc    Untuk transfer data di NIKKO
     */
    public static  synchronized void analisisPresence(){

        Vector vPresence = new Vector();

        String where = PstPresence.fieldNames[PstPresence.FLD_TRANSFERRED] + " = " + PstPresence.PRESENCE_NOT_TRANSFERRED;

        try {

            vPresence = PstPresence.list(0, 0, where, null);

            if (vPresence != null && vPresence.size() > 0) {

                for (int i = 0; i < vPresence.size(); i++) {

                    Presence presence = new Presence();
                    presence = (Presence) vPresence.get(i);

                    long periodId = PstPeriod.getPeriodIdBySelectedDate(presence.getPresenceDatetime());

                    Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presence.getPresenceDatetime());
                    int updatedFieldIndex = -1;
                    long updatePeriodId = periodId;

                    if (vectFieldIndex != null && vectFieldIndex.size() == 2) {
                        updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                        updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                    }

                    int updateStatus = 0;

                    try {

                        updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presence.getPresenceDatetime());

                    } catch (Exception E) {

                        System.out.println("[exception] " + E.toString());

                    }

                }

            }


        } catch (Exception E) {
            System.out.println("[exception] " + E.toString());
        }

    }

}
 