/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;

import com.dimata.harisma.entity.attendance.I_Atendance;
import java.sql.*;
import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.Presence;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.canteen.CanteenVisitation;
import com.dimata.harisma.entity.canteen.PstCanteenVisitation;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.session.log.SessSysLogger;
import com.dimata.harisma.utility.machine.SaverData;
import com.dimata.harisma.utility.machine.dcanteen.SaverDataCanteen;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import com.dimata.qdep.db.DBResultSet;
import java.sql.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.harisma.utility.machine.WatcherMachine;
import com.dimata.harisma.utility.service.presence.*;

/**
 *
 * @author kartika
 * Class ini akan menganalisa hr_machine transaction dengan status NOT_YET_POSTED 
 *  kemudian mentransfernya ke hr_presence
 */
public class TransManagerAssistant implements Runnable {

    /**
     * @return the message
     */
    public static String getMessage() {
        return message;
    }

    /**
     * @param aMessage the message to set
     */
    public static void setMessage(String aMessage) {
        message = aMessage;
    }
    //update by satrya 2012-08-08

    /**
     * @return the messageCalculation
     */
    public static String getMessageCalculation() {
        return messageCalculation;
    }

    /**
     * @param aMessageCalculation the messageCalculation to set
     */
    public static void setMessageCalculation(String aMessageCalculation) {
        messageCalculation = aMessageCalculation;
    }

    /**
     * @return the messageProsessAbsence
     */
    public static String getMessageProsessAbsence() {
        return messageProsessAbsence;
    }

    /**
     * @param aMessageProsessAbsence the messageProsessAbsence to set
     */
    public static void setMessageProsessAbsence(String aMessageProsessAbsence) {
        messageProsessAbsence = aMessageProsessAbsence;
    }

    /**
     * @return the messageProsessLateness
     */
    public static String getMessageProsessLateness() {
        return messageProsessLateness;
    }

    /**
     * @param aMessageProsessLateness the messageProsessLateness to set
     */
    public static void setMessageProsessLateness(String aMessageProsessLateness) {
        messageProsessLateness = aMessageProsessLateness;
    }

   

    
    private boolean running = false;
    private long sleepMs = 50;
    private static String message = "";
    //update by satrya 2012-08-08
    private static String  messageCalculation ="";
    //update by satrya 2012-09-04
    private static String messageProsessAbsence="";
    private static String messageProsessLateness="";
    //update by satrya 2013-07-25
   // private static String msgProsessAnalyze="";
    private int recordSize = 0;
    private int progressSize = 0;
    //update by satrya 2012-09-21
    private int recordSizeAbsence =0;
    private int progressSizeAbsence = 0;
    
    //update by satrya 2013-07-25
    //private int recordSizeAnalyse=0;
    //private int progressSizeAnalyze=0;
    
    public static boolean CHECK_SWEEP_TIME = false;
    public static int IGNORE_TIME = 15 * 60 * 1000;          /* --- in milli seconds --- */
    //update by satrya 2012-08-01
    private String payrolNum="";
    private Date fromDate = null;
    private Date toDate = null;
    private long oidDepartement = 0;
    private long oidDivision = 0;
    private String fullName = "";
    private long oidsection = 0;
    private int limitList = 1000;
    //priska menambahkan employee category 20150718
    private String empCat = "";
    public TransManagerAssistant() {
        initClass(10);
    }

    public TransManagerAssistant(Date fromDate, Date toDate, long oidDepartement, String fullName, long oidsection, String empCat,long oidDivision) {
    // public TransManagerAssistant(Date fromDate, Date toDate, long oidDepartement, String fullName, long oidsection) {
        initClass(10);
    }

    public void initClass(long sleepMs) {
        this.sleepMs = sleepMs;
    }

    /**
     * @desc Menyimpan transaction to machine
     * @param <DESC>param</DESC> data transaction
     * @return status boolean
     */
    public static int analistPresentAll() {

        String strMachineAbsence = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
        String strMachineCanteen = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
        CHECK_SWEEP_TIME = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_CHECK_SWEEP_TIME")) > 0;
        int ignoreTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_IGNORE_SWEEP_TIME")) * 60 * 1000;

        if (ignoreTime > 0) {
            IGNORE_TIME = ignoreTime;
        }

        Hashtable strMcNoAbsence = new Hashtable();
        Hashtable strMcNoCanteen = new Hashtable();

        StringTokenizer strTokenizerAb = new StringTokenizer(strMachineAbsence, ",");
        StringTokenizer strTokenizerCt = new StringTokenizer(strMachineCanteen, ",");

        while (strTokenizerAb.hasMoreTokens()) {
            String strAb = strTokenizerAb.nextToken();
            strMcNoAbsence.put(strAb, strAb);
        }

        while (strTokenizerCt.hasMoreTokens()) {
            String strAb = strTokenizerCt.nextToken();
            strMcNoCanteen.put(strAb, strAb);
        }

        int iValidData = 0;

        String whereClause = PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_POSTED] + "=" + PstMachineTransaction.POSTED_STATUS_OPEN;
        Vector vTrans = new Vector(1, 1);
        vTrans = PstMachineTransaction.list(0, 0, whereClause, PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);

        System.out.println("\n\n\n-------------- START ANALIZING DATA ---------------------");
        setMessage("\n-------------- START ANALIZING DATA ---------------------");
        for (int i = 0; i < vTrans.size(); i++) {

            boolean isSaveData = false;

            MachineTransaction mcTran = new MachineTransaction();
            mcTran = (MachineTransaction) vTrans.get(i);
            //Jika no sesuai dengan no mesin absen
            //System.out.println("[" + (i + 1) + "]Date :: " + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd hh:mm:ss") + " >>" + mcTran.getStation() + " | " + mcTran.getMode() + " | " + mcTran.getCardId());
            setMessage("[" + (i + 1) + "]Date :: " + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd hh:mm:ss") + " >>" + mcTran.getStation() + " | " + mcTran.getMode() + " | " + mcTran.getCardId());
            if (mcTran.getStation().equals((String) strMcNoAbsence.get(mcTran.getStation()))) {
                setMessage("---------- Process at absence : " + mcTran.getCardId());
                isSaveData = true;
                try {
                    if (!SaverData.saveTransaction(mcTran)) {
                        isSaveData = false;
                    }
                } catch (DBException ex) {
                    isSaveData = false;
                    System.out.println("[exception] " + ex);
                }
            }//Jika no sesuai dengan no mesin canteen
            else if (mcTran.getStation().equals((String) strMcNoCanteen.get(mcTran.getStation()))) {
                setMessage("---------- Process at canteen : " + mcTran.getCardId());
                isSaveData = true;
                boolean IGNORE = false;

                if (CHECK_SWEEP_TIME) {
                    //Employee emp = new Employee();
                    long empOid = 0;
                    empOid = PstEmployee.getEmployeeByBarcode(mcTran.getCardId());
                    if (empOid == 0) { // try get employee id by employee number;
                        empOid = PstEmployee.getEmployeeIdByNum(mcTran.getCardId());
                    }
                    if ((empOid == 0) && (mcTran.getCardId().length()==2) ) { // try get employee id by employee number;
                        empOid = PstEmployee.getEmployeeIdByNum("0000"+mcTran.getCardId());
                    }
                    CanteenVisitation canteenVisitation = PstCanteenVisitation.getLatestVisitation(empOid);
                    if (canteenVisitation == null) {
                        IGNORE = false;
                    } else {
                        Date lastEmpTransTime = canteenVisitation.getVisitationTime();
                        long transactionTime = mcTran.getDateTransaction().getTime();
                        long lastEmployeeVisitation = lastEmpTransTime.getTime();
                        long diff = Math.abs(transactionTime - lastEmployeeVisitation);
                        IGNORE = (diff <= IGNORE_TIME);
                        if (IGNORE) {
                            setMessage("Visitation data with employeeOid = " + empOid + " is IGNORED ...");
                        }
                    }
                    if (!IGNORE) {
                        try {
                            if (!SaverDataCanteen.saveTransaction(mcTran)) {
                                isSaveData = false;
                            }
                        } catch (DBException ex) {
                            isSaveData = false;
                        }
                    } else {
                        isSaveData = false;
                    }
                } else {
                    try {
                        if (!SaverDataCanteen.saveTransaction(mcTran)) {
                            isSaveData = false;
                        }
                    } catch (DBException ex) {
                        isSaveData = false;
                    }
                }
            } else {
                setMessage("> Not Process : " + mcTran.getCardId() + " > Machine Number " + mcTran.getStation() + " is not valid, check system property : ABSEN_TMA_NO and CANTEEN_TMA_NO");
            }

            if (isSaveData) {
                mcTran.setPosted(PstMachineTransaction.POSTED_STATUS_PROCESSED);
                iValidData += 1;
            } else {
                mcTran.setPosted(PstMachineTransaction.POSTED_STATUS_INVALID_DATA);
                //SessSysLogger.logWarning("TRANSACTION NOT VALID", "From machine:" + mcTran.getStation() + "; Emp Num:" + mcTran.getCardId() + "; Mode:" + mcTran.getMode() + "; Date:" + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd"));
                setMessage("TRANSACTION NOT VALID" + "From machine:" + mcTran.getStation() + "; Emp Barcode Number:" + mcTran.getCardId() + "; Mode:" + mcTran.getMode() + "; Date:" + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd"));
            }
            try {
                PstMachineTransaction.updateExc(mcTran);
            } catch (Exception ex) {
                setMessage("[ERROR] SessMachineTransaction.analistPresent :::: " + ex.toString());
            }
        }
        return iValidData;

    }

    public void run() {
        this.setRunning(true);
        setMessage("TRANSMANAGER ASSISTANT : Process hr_machine_transaction >>> hr_presence");
        String strMachineAbsence = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
        String strMachineCanteen = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
        CHECK_SWEEP_TIME = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_CHECK_SWEEP_TIME")) > 0;
        int ignoreTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_IGNORE_SWEEP_TIME")) * 60 * 1000;

        //update by satrya 2013-07-08
         I_Atendance attdConfig = null;
                try {
                    attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
                } catch (Exception e) {
                    System.out.println("Exception : " + e.getMessage());
                    System.out.println("Please contact your system administration to setup system property: LEAVE_CONFIG ");
                }
        if (ignoreTime > 0) {
            IGNORE_TIME = ignoreTime;
        }

        Hashtable strMcNoAbsence = new Hashtable();
        Hashtable strMcNoCanteen = new Hashtable();

        StringTokenizer strTokenizerAb = new StringTokenizer(strMachineAbsence, ",");
        StringTokenizer strTokenizerCt = new StringTokenizer(strMachineCanteen, ",");

        while (strTokenizerAb.hasMoreTokens()) {
            String strAb = strTokenizerAb.nextToken();
            //update by satrya 7-6-2012
            if (strAb != null & !strAb.equals("-") && !strAb.equals("_") && !strAb.equals(".")) {
                strMcNoAbsence.put(strAb.trim(), strAb.trim());
            }
        }

        while (strTokenizerCt.hasMoreTokens()) {
            String strAb = strTokenizerCt.nextToken();
            //update by satrya 9 -6-2012
            if (strAb != null & !strAb.equals("-") && !strAb.equals("_") && !strAb.equals(".")) {
                strMcNoCanteen.put(strAb.trim(), strAb.trim());
            }
        }
        //update by satrya 7-06-2012
        boolean checkCanteen = false;

        if (strMcNoCanteen.size() >= 1) {
            checkCanteen = true;
        }

        String strTransInvalidPrev = "";
        int startIndex = 0;
            recordSize = PstPresence.getCountManualCalculation(PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " ASC ", getOidDepartement(), getFullName(), getFromDate(), getToDate(), getOidsection(), getPayrolNum(), getEmpCat(),getOidDivision());
        progressSize = 0;
        progressSizeAbsence = 0;
        setMessageProsessAbsence("");
         setMessageCalculation(" START ANALIZING DATA HR_Presence >> " + recordSize + " DATA");
         Hashtable hashSchSymbol = PstScheduleSymbol.getHashTlScheduleAll();
           String oidSPresence="";
        while (this.running) {
            try {
                Thread.sleep(this.getSleepMs());
                // get records dari table mesin
              
                if (getFromDate() != null && getToDate() != null) {
                    //update by satrya 2012-09-19
                   Presence presence = new Presence();
                   Vector vPresence = PstPresence.listManualCalculation(startIndex, limitList, " PS."+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " ASC ", getOidDepartement(), getFullName(), getFromDate(), getToDate(), getOidsection(), getPayrolNum(), getEmpCat(),getOidDivision()); 
                    
                    if (vPresence != null && vPresence.size() > 0) {
                        startIndex = startIndex + limitList;
                       Thread.sleep(this.getSleepMs());
                        //boolean cekSaveDataTrans=false;
                        
                        
                        for (int i = 0; i < vPresence.size(); i++) {
                            //  for (int i = 0; i < vPresence.size(); i++) {
                            AnalyseStatusDataPresence analyseStatusDataPresence = new AnalyseStatusDataPresence();
                            Thread.sleep(this.getSleepMs());
                            boolean isSaveData = false;
                            if (!this.running) {
                                break;
                            }
                            if (this.jProgressBar != null) {
                            this.jProgressBar.setMinimum(0);
                            this.jProgressBar.setMaximum(recordSize);
                            this.jProgressBar.setValue(progressSize);
                        }
                            progressSize=progressSize+ 1;
                            
                            presence = (Presence) vPresence.get(i);
                            //update by satrya 2012-09-28
                            setMessageCalculation(" Current Data : " + (progressSize) + "  to Go : " +(recordSize- ( progressSize + 1) )+"  Total : "+ recordSize +"  | Date : " + Formater.formatDate(presence.getPresenceDatetime(), "yyyy-MM-dd HH:mm:ss") + " >>" +" Payrol Number:" + presence.getEmpNumb());
                            //update by satrya 2012-08-08
                            //setMessage(" Total Data Presence : [" + (i + 1) + "] Date :: " + Formater.formatDate(presence.getPresenceDatetime(), "yyyy-MM-dd hh:mm:ss") + " >>" +" Employe ID:" + presence.getEmployeeId());

                            //setMessage(" > Process at absence : " + presence.getEmployeeId());
                            isSaveData = true;
                            try {
                                if(attdConfig!=null && attdConfig.getConfigurasiInOut()==I_Atendance.CONFIGURASI_III_SEARCH_BY_SCHEDULE_NO_OVERTIME){
                                     analyseStatusDataPresence = SaverData.saveManualPresenceNoOvertime(presence, getOidDepartement());
                                }else{
                                    analyseStatusDataPresence = SaverData.saveManualPresence(presence, getOidDepartement());
                                }
                                 if(analyseStatusDataPresence.isSuccess()){
                                      //melakukan pengecekan data jika ada data yg di transfer maka akan di set true,sehingga bisa di analyza
                                    oidSPresence = oidSPresence + analyseStatusDataPresence.getPresenceId() + ",";
                                 }
                                //update by satrya 2013-07-29
                                //if (!SaverData.saveManualPresence(presence, getOidDepartement())) {
                                if(!analyseStatusDataPresence.isSuccess()){
                                    isSaveData = false;
                                }
                            
                                /*if (!SaverData.saveManualPresence(presence, getOidDepartement())) {
                                    isSaveData = false;

                                }*/
                            } catch (DBException ex) {
                                isSaveData = false;
                                System.out.println("[exception] " + ex);
                            }
                        }///untk for 

                       // }
                    }else{
                       //update by satrya 2012-09-13
                          if(this.running){
                            
                            //Date nowDate = new Date();
                            //Date dtNow = new Date(nowDate.getYear(), nowDate.getMonth(), nowDate.getDate());
                                if(true){ //((getFromDate().getTime() < dtNow.getTime()) && (getToDate().getTime() < dtNow.getTime())) {
                                    long diffStartToFinish = getToDate().getTime() - getFromDate().getTime();
                                    
                                    if (diffStartToFinish >= 0) {
                                      
                                         //setMessageProsessAbsence("");
                                        // --- start presence data calculation ---
                                        //PresenceAnalyser objPresenceAnalyser = new PresenceAnalyser();
                                        //objPresenceAnalyser.analyzeEmpPresenceData( getOidDepartement(), getOidsection(), getFullName(), getPayrolNum());
                                        //update by satrya 2012-08-01
                                        //objPresenceAnalyser.analyzeEmpPresenceData( getOidDepartement(), getOidsection(), getFullName());
                                        // --- end presence data calculation ---

                                        int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
                                        recordSizeAbsence = itDate;
                                        progressSizeAbsence = 0;
                                         setMessageProsessAbsence("Analize presence status...");
                                        for (int i = 0; i <= itDate; i++) {
                                            Thread.sleep(this.getSleepMs());
                                           Date selectedDate = new Date(getFromDate().getYear(), getFromDate().getMonth(), (getFromDate().getDate() + i));
                                               //update by devin 2014-02-21
                                        long lSelectedDateFrom = selectedDate.getTime();
                                        long lSelectedDateTo = selectedDate.getTime();
                                             
                                        long DateFrom = lSelectedDateFrom;
                                        long DateTo = lSelectedDateTo;
                                         Date selectedDateFrom = new Date(DateFrom);
                                        Date selectedDateTo = new Date(DateTo);
                                        selectedDateFrom.setHours(0);
                                        selectedDateFrom.setMinutes(0);
                                        selectedDateFrom.setSeconds(0);
                                        selectedDateTo.setHours(23);
                                        selectedDateTo.setMinutes(59);
                                        selectedDateTo.setSeconds(59);
                                        int status=4;
                                        long employeeId=0;
                                         Vector ovt = new Vector();
                                          Vector employeeNum = new Vector();
                                          String where="";
                                          if(getPayrolNum().length()>0)
                                          {
                                              where= PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = " + getPayrolNum();
                                               employeeNum = PstEmployee.list(0, 0, where, "");
                                         if(employeeNum !=null && employeeNum.size()>0){
                                             for(int x=0;x<employeeNum.size();x++){
                                                 Employee emp =(Employee)employeeNum.get(x);
                                                 if (emp.getEmpPin().length() > 0){
                                                 employeeId = Long.parseLong( emp.getEmpPin());
                                                 } else {
                                                     System.out.print("employee pin not set");
                                                     employeeId = emp.getOID();
                                                 }
                                             }
                                         }
                                          }
                                         
                                        
                                         ovt = PstOvertimeDetail.getOvertime(selectedDateFrom,selectedDateTo,status,employeeId);
                                           //update by satrya 2012-09-19  
                                            //Presence presence = new Presence();
                                               //presence = (Presence) vPresence.get(i);
                                            // --- start absence data calculation ---				
                                           // AbsenceAnalyser absenceAnalyser = new AbsenceAnalyser();
                                            //update by satrya 2012-09-06
                                            //AbsenceAnalyser.setRunning(true);
                                            //absenceAnalyser.checkEmployeeAbsence(selectedDate, getOidDepartement(), getOidsection(), getFullName(),getPayrolNum());
                                            //update by satrya 2012-08-01
                                            //absenceAnalyser.checkEmployeeAbsence(selectedDate, getOidDepartement(), getOidsection(), getFullName());
                                            ///System.out.println(".::Process check absence on  : " + selectedDate);
                                            //update by satrya 2012-09-24
                                            setMessageProsessAbsence("Analize presence status...  " + " DATE : "+Formater.formatDate(selectedDate, "dd-MM-yyyy"));
                                            TransManagerAssistant transManagerAssistant = new TransManagerAssistant();
                                            //update by satrya 2013-07-29
                                            //PresenceAnalyser.analyzePresencePerEmployee(0,0,selectedDate, getOidDepartement(), getOidsection(), getFullName(), getPayrolNum());
                                            //update by devin 2014-02-21
                                            //PresenceAnalyser.analyzePresencePerEmployee(0,0,selectedDate, getOidDepartement(), getOidsection(), getFullName(), getPayrolNum(),oidSPresence,transManagerAssistant);
                                             PresenceAnalyser.analyzePresencePerEmployee(0,0,selectedDate, getOidDepartement(), getOidsection(), getFullName(), getPayrolNum(),oidSPresence,transManagerAssistant,ovt,getEmpCat(), getOidDivision());
                                            if (!this.running) {
                                                 break;
                                             }
                                             if (this.jProgressBar != null) {
                                                 this.jProgressBar.setMinimum(0);
                                                 this.jProgressBar.setMaximum(recordSizeAbsence);
                                                 this.jProgressBar.setValue(progressSizeAbsence);
                                             }
                                            progressSizeAbsence=progressSizeAbsence+1;
                                            
                         			

                                            // --- start lateness data calculation ---				
                                          // LatenessAnalyser objLatenessAnalyser = new LatenessAnalyser();
                                          //  objLatenessAnalyser.checkEmployeeLateness(selectedDate,getOidDepartement(), getOidsection(), getFullName(), getPayrolNum());
                                            //System.out.println(".::Process check lateness on : " + selectedDate);
                                            //update by satrya 2012-09-04

                                           // setMessageProsessLateness(" DATE : " + Formater.formatDate(selectedDate, "yyyy-MM-dd") + getPayrolNum() ==null ? "" : " || PAYROL NUMBER : " + getPayrolNum());
                                            // Thread.sleep(this.getSleepMs());
                                            // --- end lateness data calculation ---
                                        }
                                        setMessageCalculation("Manual calculation process done ... ");
                                        //update by satrya 2012-09-04
                                       
                                        //progressSize = 0;
                                        //progressSizeAbsence = 0;
                                        //setMessageProsessLateness("");
                                        //setMessage("Manual calculation process done ... ");
                                         this.setRunning(false) ;
                                    } else {
                                         setMessageCalculation("Selected start date should less than or equal to finish date ..");
                                         // setMessage ("Selected start date should less than or equal to finish date ..");
                                          this.setRunning(false) ;
                                    }
                                } else {
                                    setMessageCalculation("Selected start date or finish date should less than today ...") ;
                                    // setMessage ("Selected start date or finish date should less than today ...") ;
                                     this.setRunning(false) ;
                                }

                          }
                    }
                }/// end untuk yg manual calculation 
                else {
                    int iValidData = 0;
                    String whereClause = PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_POSTED] + "=" + PstMachineTransaction.POSTED_STATUS_OPEN;
                    Vector vTrans = new Vector(1, 1);
                    
                    vTrans = PstMachineTransaction.list(0, limitList, whereClause, PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);



                    if (vTrans != null && vTrans.size() > 0) {
                        recordSize = vTrans.size();
                        progressSize = 0;
                        setMessage(" START ANALIZING DATA HR_MACHINE_TRANSACTON >> " + vTrans.size() + " DATA");
                    }
                    if (this.jProgressBar != null) {
                        this.jProgressBar.setMinimum(0);
                        this.jProgressBar.setMaximum(recordSize);
                        this.jProgressBar.setValue(progressSize);
                    }

                    boolean cekSaveDataTrans=false;
                    AnalyseStatusDataPresence analyseStatusDataPresence = new AnalyseStatusDataPresence();
                   // oidSPresence="";
                    for (int i = 0; i < vTrans.size(); i++) {
                        Thread.sleep(this.getSleepMs());
                        
                        boolean isSaveData = false;
                        if (!this.running) {
                            break;
                        }
                        MachineTransaction mcTran = new MachineTransaction();
                        mcTran = (MachineTransaction) vTrans.get(i);
                        //Jika no sesuai dengan no mesin absen
                        setMessage("[" + (i + 1) + "]Date :: " + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd hh:mm:ss") + " >>" + mcTran.getStation() + " | " + mcTran.getMode() + " | " + mcTran.getCardId());
                        if (!checkCanteen || (!mcTran.getStation().equals((String) strMcNoCanteen.get(mcTran.getStation())))) {//||  ( mcTran.getStation().equals((String) strMcNoAbsence.get(mcTran.getStation())))) {
                            //if ( mcTran.getStation().equals((String) strMcNoAbsence.get(mcTran.getStation()))) {

                            setMessage(" > Process at absence : " + mcTran.getCardId() + " ||  Date :: " + Formater.formatDate(mcTran.getDateTransaction(), " yyyy-MM-dd hh:mm:ss "));
                            isSaveData = true;
                            try {
                              if(attdConfig!=null && attdConfig.getConfigurasiInOut()==I_Atendance.CONFIGURASI_II_SEARCH_BY_IN_OUT){
                                  analyseStatusDataPresence = SaverData.saveTransactionByActualInOuT(mcTran,hashSchSymbol);
                                  if(analyseStatusDataPresence.isSuccess()){
                                      //melakukan pengecekan data jika ada data yg di transfer maka akan di set true,sehingga bisa di analyza
                                    oidSPresence = oidSPresence + analyseStatusDataPresence.getPresenceId() + ",";
                                    cekSaveDataTrans=true;
                                  }
                                //if (!SaverData.saveTransactionByActualInOuT(mcTran,hashSchSymbol,analyseStatusDataPresence,oidSPresence)) {
                                 if(!analyseStatusDataPresence.isSuccess()){
                                    isSaveData = false;
                                }
                              }
                              //update by satrya 2013-12-21
                              else if(attdConfig!=null && attdConfig.getConfigurasiInOut()==I_Atendance.CONFIGURASI_III_SEARCH_BY_SCHEDULE_NO_OVERTIME){
                                  analyseStatusDataPresence = SaverData.saveTransactionByActualInOuT(mcTran,hashSchSymbol);
                                  if(analyseStatusDataPresence.isSuccess()){
                                      //melakukan pengecekan data jika ada data yg di transfer maka akan di set true,sehingga bisa di analyza
                                    oidSPresence = oidSPresence + analyseStatusDataPresence.getPresenceId() + ",";
                                    cekSaveDataTrans=true;
                                  }
                                //if (!SaverData.saveTransactionByActualInOuT(mcTran,hashSchSymbol,analyseStatusDataPresence,oidSPresence)) {
                                 if(!analyseStatusDataPresence.isSuccess()){
                                    isSaveData = false;
                                }
                              }else{
                                  analyseStatusDataPresence = SaverData.saveTransactionVer2(mcTran);
                                  if(analyseStatusDataPresence.isSuccess()){
                                      //update by satrya 2014-06-10 agar tidak di analisa otomatis
                                      oidSPresence ="";
                                       //oidSPresence = oidSPresence + analyseStatusDataPresence.getPresenceId() + ",";
                                        cekSaveDataTrans=true;
                                  }
                                if(!analyseStatusDataPresence.isSuccess()){
                                    //update by satrya 2013-08-06
                                    //if (!SaverData.saveTransactionVer2(mcTran)) {
                                    isSaveData = false;
                                }
                              }
                              
                            } catch (DBException ex) {
                                isSaveData = false;
                                 System.out.println("[exception] " + ex);
                            }
                        }//Jika no sesuai dengan no mesin canteen
                        else if (mcTran.getStation().equals((String) strMcNoCanteen.get(mcTran.getStation()))) {
                            setMessage(" > Process at canteen : " + mcTran.getCardId() + " ||  Date :: " + Formater.formatDate(mcTran.getDateTransaction(), " yyyy-MM-dd hh:mm:ss "));
                            isSaveData = true;
                            boolean IGNORE = false;
                            long empOid = 0;
                            empOid = PstEmployee.getEmployeeByBarcode(mcTran.getCardId());
                            try {
                                empOid = PstEmployee.getEmployeeByBarcode(mcTran.getCardId());
                                if (empOid == 0) {
                                        empOid = PstEmployee.getEmployeeByBarcode(""+mcTran.getCardId());
                                        if (empOid == 0) { // try get employee id by employee number;
                                            empOid = PstEmployee.getEmployeeIdByNum(mcTran.getCardId());
                                            if (empOid == 0) {
                                                empOid = PstEmployee.getEmployeeByLikeBarcode(mcTran.getCardId());
                                                if (empOid == 0) {
                                                    empOid = PstEmployee.getEmployeeIdByLikeNum(mcTran.getCardId());
                                                }
                                            }
                                        }
                                }
                            } catch (Exception exc) {
                                System.out.println(exc);
                            }

                            if (CHECK_SWEEP_TIME) {
                                //Employee emp = new Employee();
                                CanteenVisitation canteenVisitation = PstCanteenVisitation.getLatestVisitation(empOid);
                                if (canteenVisitation == null) {
                                    IGNORE = false;
                                } else {
                                    Date lastEmpTransTime = canteenVisitation.getVisitationTime();
                                    long transactionTime = mcTran.getDateTransaction().getTime();
                                    long lastEmployeeVisitation = lastEmpTransTime.getTime();
                                    long diff = Math.abs(transactionTime - lastEmployeeVisitation);
                                    IGNORE = (diff <= IGNORE_TIME);
                                    if (IGNORE) {
                                        setMessage(" Visitation data with employeeOid = " + empOid + " is IGNORED TIME ");
                                    }
                                }
                                if (!IGNORE) {
                                    try {
                                        if (!SaverDataCanteen.saveTransaction(empOid, mcTran)) {
                                            isSaveData = false;
                                        }
                                    } catch (DBException ex) {
                                        isSaveData = false;
                                    }
                                } else {
                                    isSaveData = false;
                                }
                            } else {
                                try {
                                    if (!SaverDataCanteen.saveTransaction(empOid, mcTran)) {
                                        isSaveData = false;
                                    }
                                } catch (DBException ex) {
                                    isSaveData = false;
                                }
                            }
                        } else {
                            setMessage(" > Not Process :" + mcTran.getStation() + "; Emp Num:" + mcTran.getCardId() + "; Mode:" + mcTran.getMode() + "; Date:" + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd")
                                    + " CHECK number machine or system property : ABSEN_TMA_NO AND CANTEEN_TMA_NO");
                        }

                        if (isSaveData) {
                            mcTran.setPosted(PstMachineTransaction.POSTED_STATUS_PROCESSED);
                            iValidData += 1;
                            try {
                                PstMachineTransaction.updateExc(mcTran);
                                if (mcTran.getOID() != 0) {
                                    
                                    try {
                                        //progressSize++;
                                      
                                        if (this.jProgressBar != null) {
                                            this.jProgressBar.setValue(progressSize);
                                        }
                                          progressSize = progressSize + 1;
                                        Thread.sleep(this.getSleepMs());
                                    } catch (Exception exc2) {
                                        System.out.println("Update Failed" + exc2);
                                    }
                                }
                            } catch (Exception ex) {
                                System.out.println("[ERROR] SessMachineTransaction.analistPresent :::: " + ex.toString());
                            }
                        } else {
                            mcTran.setPosted(PstMachineTransaction.POSTED_STATUS_INVALID_DATA);
                            try {
                                PstMachineTransaction.updateExc(mcTran);
                            } catch (Exception ex) {
                                setMessage("[ERROR] SessMachineTransaction.analistPresent :::: " + ex.toString());
                            }
                            String strTransInvalid = "From machine:" + mcTran.getStation() + "; Emp Num:" + mcTran.getCardId() + "; Mode:" + mcTran.getMode() + "; Date:" + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd");
                            if (!strTransInvalid.equals(strTransInvalidPrev)) {
                                strTransInvalidPrev = strTransInvalid;
                                //SessSysLogger.logWarning("TRANSACTION NOT VALID", strTransInvalid);
                                setMessage(strTransInvalid);
                            }
                        }
                    }
                    //analyze status
                    //update by satrya 2013-07-25
                    //update by satrya 2014-06-10 agar tidak di analisa otomatis if(vTrans!=null && vTrans.size()==0){
                    if(vTrans!=null && vTrans.size()==0 && (oidSPresence!=null && oidSPresence.length()>0)){
                        //   if(cekSaveDataTrans){
                        Date startDate = PstPresence.getDatePresence(0, 1, PstPresence.fieldNames[PstPresence.FLD_ANALYZED]+"="+Presence.ANALYZED_NOT_YET + " AND "+PstPresence.fieldNames[PstPresence.FLD_TRANSFERRED]+"="+PstPresence.PRESENCE_TRANSFERRED, PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] +" ASC ");
                        Date endDate = PstPresence.getDatePresence(0, 1, PstPresence.fieldNames[PstPresence.FLD_ANALYZED]+"="+Presence.ANALYZED_NOT_YET + " AND "+PstPresence.fieldNames[PstPresence.FLD_TRANSFERRED]+"="+PstPresence.PRESENCE_TRANSFERRED, PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " DESC ");
                        if(startDate!=null && endDate!=null){
                            long ldtSelectedFrom = startDate.getTime()- (1000 * 60 * 60 * 24);
                            long ldtSelectedTo = endDate.getTime() + (1000 * 60 * 60 * 24); 
                            Date dtSelectedFrom = new Date(ldtSelectedFrom);
                            Date dtSelectedTo = new Date(ldtSelectedTo);
                                long diffStartToFinish = dtSelectedTo.getTime() - dtSelectedFrom.getTime(); 
                                if(diffStartToFinish>=0){
                                    int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
                                  // String where = PstPresence.fieldNames[PstPresence.FLD_ANALYZED]+"="+Presence.ANALYZED_NOT_YET + " AND "+PstPresence.fieldNames[PstPresence.FLD_TRANSFERRED]+"="+PstPresence.PRESENCE_TRANSFERRED;
                                  // String order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " ASC ";
                                   int total=0;
                                    if(itDate==0){
                                        total=itDate+1;
                                    }else{
                                        total=itDate;
                                    }
                                   
                                    recordSizeAbsence = total ;//PstPresence.getCountAnalyseStatus(order, startDate, endDate, where); 
                                    progressSizeAbsence = 0;
                                    setMessageProsessAbsence("Analize presence status...");
                                    for(int i = 0; i <= itDate; i++){
                                        Date selectedDate = new Date(dtSelectedFrom.getYear(), dtSelectedFrom.getMonth(), (dtSelectedFrom.getDate() + i));
                                        //setMessageProsessAbsence("Analize presence status...  " + " DATE : "+Formater.formatDate(selectedDate, "dd-MM-yyyy"));
                                        //analyseStatusDataPresence
                                        TransManagerAssistant transManagerAssistant = new TransManagerAssistant();
                                        
                                        PresenceAnalyser.analyzePresencePerEmployee(0,limitList,selectedDate, 0, 0, "", "",oidSPresence,transManagerAssistant,0);
                                        if (!this.running) {
                                                 break;
                                             }
                                             if (this.jProgressBar != null) {
                                                 this.jProgressBar.setMinimum(0);
                                                 this.jProgressBar.setMaximum( itDate /*recordSizeAbsence*/);
                                                 this.jProgressBar.setValue(progressSizeAbsence);
                                             }
                                            progressSizeAbsence=progressSizeAbsence+1;
                                            
                                    }
                                    cekSaveDataTrans=false;//artinya data sudah habis di transfer
                                    //setMessageCalculation("Manual calculation process done ... ");
                                }
                        }
                        
                    }
                }
            } catch (Exception exc) {
                System.out.println(exc);
            } finally {
                
            }
        }

        this.running = false;


    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
        //update by satrya 2012-09-06
       // AbsenceAnalyser.setRunning(running);
    }

    /**
     * @return the progressSize
     */
    public int getProgressSize() {
        return progressSize > recordSize ? recordSize : progressSize;
    }

    /**
     * @return the recordSize
     */
    public int getRecordSize() {
        //System.out.println("=============================+++++++++======================");
        //System.out.println(recordSize);
        return recordSize;

    }

    /**
     * @return the sleepMs
     */
    public long getSleepMs() {
        return sleepMs;
    }

    /**
     * @param sleepMs the sleepMs to set
     */
    public void setSleepMs(long sleepMs) {
        this.sleepMs = sleepMs;
    }
    private javax.swing.JProgressBar jProgressBar = null;

    public void setProgressBar(javax.swing.JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }
    javax.swing.JTextArea jTextArea = null;

    public void setTextArea(javax.swing.JTextArea jTextAreaPar) {
        jTextArea = jTextAreaPar;
    }

    /**
     * @return the limitList
     */
    public int getLimitList() {
        return limitList;
    }

    /**
     * @param limitList the limitList to set
     */
    public void setLimitList(int limitList) {
        this.limitList = limitList;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the oidDepartement
     */
    public long getOidDepartement() {
        if (oidDepartement != 0) {
            return oidDepartement;
        }
        return 0;
    }

    /**
     * @param oidDepartement the oidDepartement to set
     */
    public void setOidDepartement(long oidDepartement) {
        this.oidDepartement = oidDepartement;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the oidsection
     */
    public long getOidsection() {
        return oidsection;
    }

    /**
     * @param oidsection the oidsection to set
     */
    public void setOidsection(long oidsection) {
        this.oidsection = oidsection;
    }

    /**
     * @return the payrolNum
     */
    public String getPayrolNum() {
        return payrolNum;
}

    /**
     * @param payrolNum the payrolNum to set
     */
    public void setPayrolNum(String payrolNum) {
        this.payrolNum = payrolNum;
    }

    /**
     * @return the recordSizeAbsence
     */
    public int getRecordSizeAbsence() {
        return recordSizeAbsence;
    }

    /**
     * @param recordSizeAbsence the recordSizeAbsence to set
     */
    public void setRecordSizeAbsence(int recordSizeAbsence) {
       this.recordSizeAbsence = recordSizeAbsence;
       
    }

    /**
     * @return the progressSizeAbsence
     */
    public int getProgressSizeAbsence() {
        //return progressSizeAbsence;
         return progressSizeAbsence > recordSizeAbsence ? recordSizeAbsence : progressSizeAbsence;
    }

    /**
     * @param progressSizeAbsence the progressSizeAbsence to set
     */
    public void setProgressSizeAbsence(int progressSizeAbsence) {
        this.progressSizeAbsence = progressSizeAbsence;
    }

    /**
     * @return the empCat
     */
    public String getEmpCat() {
        return empCat;
    }

    /**
     * @param empCat the empCat to set
     */
    public void setEmpCat(String empCat) {
        this.empCat = empCat;
    }

    /**
     * @return the oidDivision
     */
    public long getOidDivision() {
        return oidDivision;
    }

    /**
     * @param oidDivision the oidDivision to set
     */
    public void setOidDivision(long oidDivision) {
        this.oidDivision = oidDivision;
    }

}
