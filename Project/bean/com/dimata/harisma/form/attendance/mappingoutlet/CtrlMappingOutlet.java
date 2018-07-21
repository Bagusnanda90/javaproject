/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.attendance.mappingoutlet;

import com.dimata.harisma.form.attendance.employeeoutlet.*;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.employeeoutlet.EmployeeOutlet;
import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.attendance.mappingoutlet.MappingOutlet;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class CtrlMappingOutlet extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_FORM_OVERLAP_SCHEDULE = 4;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", " schedule sama"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete", " Employee is Overlap schedule"}
    };
    private int start;
    private String msgString;
    private MappingOutlet objMappingOutlet;
    //private MappingOutlet prevObjEmployeeOutlet;
    private PstEmployeeOutlet pstEmployeeOutlet;
    private FrmMappingOutlet frmMappingOutlet;
    int language = LANGUAGE_FOREIGN;

    public CtrlMappingOutlet(HttpServletRequest request) {
        msgString = "";
        objMappingOutlet = new MappingOutlet();
        try {
            pstEmployeeOutlet = new PstEmployeeOutlet(0);
        } catch (Exception e) {;
        }
        frmMappingOutlet = new FrmMappingOutlet(request, objMappingOutlet);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmMappingOutlet.addError(frmMappingOutlet.FRM_FIELD_OUTLET_EMPLOYEE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }

    private int getControlMsgId(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public MappingOutlet getMappingOutlet() {
        return objMappingOutlet;
    }

    public FrmMappingOutlet getForm() {
        return frmMappingOutlet;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, Hashtable hashSchedule, Hashtable hashScheduleSymbol) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_UNKNOWN_ERROR;
        switch (cmd) {
            case Command.EDIT:
                break;

            case Command.SAVE:
                //frmMappingOutlet.requestEntityObject(objMappingOutlet);

                frmMappingOutlet.requestEntityObjectMultiple(hashSchedule, hashScheduleSymbol); 
                if (frmMappingOutlet != null && frmMappingOutlet.getMsgFrm() != null && frmMappingOutlet.getMsgFrm().length() > 0) {
                    msgString = frmMappingOutlet.getMsgFrm();
                    return RSLT_FORM_INCOMPLETE;
                }

                //Hashtable hashEmpMsg = new Hashtable();
                if (frmMappingOutlet.getMappingOutletList() != null && frmMappingOutlet.getMappingOutletList().size() > 0) {
                    Hashtable hashEmpErorr = new Hashtable();
                    for (int idx = 0; idx < frmMappingOutlet.getMappingOutletList().size(); idx++) {
                        objMappingOutlet = new MappingOutlet();
                        objMappingOutlet = (MappingOutlet) frmMappingOutlet.getMappingOutletList().get(idx);
                        if (frmMappingOutlet != null && frmMappingOutlet.getCheckRequeredSize() > 0) {
                            //objMappingOutlet = (MappingOutlet) frmMappingOutlet.getMappingOutletList().get(idx);
                            if(frmMappingOutlet.isCheckRequered(objMappingOutlet.getEmployeeId()+"_schedule")){ 
                                continue;
                            }
                            if(frmMappingOutlet.isCheckRequered(objMappingOutlet.getEmployeeId()+"_outlet")){ 
                                continue;
                            }
                            if(frmMappingOutlet.isCheckRequered(objMappingOutlet.getEmployeeId()+"_position")){ 
                                continue;
                            }
                            if(frmMappingOutlet.isCheckRequered(objMappingOutlet.getEmployeeId()+"_dt")){ 
                                continue;
                            }
                            
                        }
                        String where = "";
                        //update by satrya 2014-11-09
                        
                        if (objMappingOutlet != null && objMappingOutlet.getDtStart() != null && objMappingOutlet.getDtdEnd() != null) {
                            where = "'" + Formater.formatDate(objMappingOutlet.getDtdEnd(), "yyyy-MM-dd 23:59:ss") + "' >=" + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM]
                                    + " AND "
                                    + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= '" + Formater.formatDate(objMappingOutlet.getDtStart(), "yyyy-MM-dd 00:00:ss") + "'"
                                    + " AND " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=" + objMappingOutlet.getEmployeeId();
                            //karena menyebabkan dia tidak overlap
                            /*if (objMappingOutlet.getOID() != 0) {
                                where = where + " AND " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_OUTLET_EMPLOYEE_ID] + "!=" + objMappingOutlet.getOID();
                            }*/
                        }
                        //cek jika ada overlap saat itu
                        Vector listEmployeeOutlet = PstEmployeeOutlet.list(0, 0, where, "");
//                        if (listEmployeeOutlet != null && listEmployeeOutlet.size() > 0) {
//                            for (int x = 0; x < listEmployeeOutlet.size(); x++) {
//                                EmployeeOutlet employeeOutlets = (EmployeeOutlet) listEmployeeOutlet.get(x);
//                                Employee employee = new Employee();
//                                try {
//                                    employee = PstEmployee.fetchExc(employeeOutlets.getEmployeeId());
//                                } catch (Exception exc) {
//                                }
//                                String fullName = employee.getFullName();
//                                String start = employeeOutlets.getDtFrom() != null ? Formater.formatDate(employeeOutlets.getDtFrom(), "dd MMM yyyy") : "";
//                                String end = employeeOutlets.getDtTo() != null ? Formater.formatDate(employeeOutlets.getDtTo(), "dd MMM yyyy") : "";
//                                Date newStartDate = new Date(employeeOutlets.getDtFrom().getYear(), employeeOutlets.getDtFrom().getMonth(),employeeOutlets.getDtFrom().getDate(), 0, 0, 0);
//                                Date newEndDate =  new Date(employeeOutlets.getDtTo().getYear(), employeeOutlets.getDtTo().getMonth(),employeeOutlets.getDtTo().getDate(), 0, 0, 0) ;
//                                Date startDate = objMappingOutlet.getDtStart();
//                                Date endDate = objMappingOutlet.getDtdEnd();
//                                //update by satrya 2014-11-16
//                                objMappingOutlet.setOID(employeeOutlets.getOID());
//                                //hashEmpMsg.put(employee.getOID(), fullName);
//                                if (newStartDate.after(startDate) && newStartDate.before(endDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
//                                } else if (newEndDate.after(startDate) && newEndDate.before(endDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
//                                } else if (startDate.after(newStartDate) && startDate.before(newEndDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
//                                } else if (endDate.after(newStartDate) && endDate.before(newEndDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
//                                } 
//                                
//                                else if (newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
//                                }
//                                //msgString = msgString + " Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end;
//                                //hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
//                            }
//                            //return RSLT_FORM_OVERLAP_SCHEDULE;  
//                        }

                        
                        String whereFirst = "";
                        //update by priska 2015-06-05
                        if (objMappingOutlet != null && objMappingOutlet.getDtStart() != null && objMappingOutlet.getDtdEnd() != null) {
                            whereFirst = PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] +" <= '" + Formater.formatDate(objMappingOutlet.getDtStart(), "yyyy-MM-dd 00:00:00") + "' " 
                                    + " AND "
                                    + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] +" >= '" + Formater.formatDate(objMappingOutlet.getDtdEnd(), "yyyy-MM-dd 23:59:59") + "' " 
                                    + " AND " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=" + objMappingOutlet.getEmployeeId();
                        }
                        
                        String whereTwo = "";
                        //update by priska 2015-06-05
                        if (objMappingOutlet != null && objMappingOutlet.getDtStart() != null && objMappingOutlet.getDtdEnd() != null) {
                            whereTwo = " (( "+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " BETWEEN \"" + Formater.formatDate(objMappingOutlet.getDtStart(), "yyyy-MM-dd 00:00:00")
                                       + "\"" + " AND " + "\"" + Formater.formatDate(objMappingOutlet.getDtdEnd(), "yyyy-MM-dd 23:59:59") + "\" ) OR ( " 
                                       +PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " BETWEEN \"" + Formater.formatDate(objMappingOutlet.getDtStart(), "yyyy-MM-dd 00:00:00")
                                       + "\"" + " AND " + "\"" + Formater.formatDate(objMappingOutlet.getDtdEnd(), "yyyy-MM-dd 23:59:59") + "\" ))"
                                       + " AND " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=" + objMappingOutlet.getEmployeeId();
                        }
                        
                        //priska 20150608
                       // Vector listEmployeeOutletFirst = PstEmployeeOutlet.list(0, 0, whereFirst, PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM]);
                        Vector listEmployeeOutletTwo = PstEmployeeOutlet.list(0, 0, whereTwo, PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM]);
                       
//                        if (listEmployeeOutletFirst != null && listEmployeeOutletFirst.size() > 0) {
//                           EmployeeOutlet employeeOutletFirst = (EmployeeOutlet) listEmployeeOutletFirst.get(0);
//                           
//                           try {
//                               long oid3 = pstEmployeeOutlet.deleteExc(employeeOutletFirst.getOID()); 
//                               objMappingOutlet.setOID(0);
//                            } catch (Exception e){
//                                 System.out.println("Exc employeeOutletFirst insert" + e);
//                            }
//                           
//                           if (employeeOutletFirst.getDtFrom() != objMappingOutlet.getDtStart() && employeeOutletFirst.getDtFrom().before(objMappingOutlet.getDtStart()) ){
//                               try {
//                               Date DtTo = (Date) objMappingOutlet.getDtStart().clone();
//                               DtTo.setDate(DtTo.getDate() - 1);
//                               EmployeeOutlet employeeOutletdtfrom = new EmployeeOutlet();
//                               employeeOutletdtfrom.setDtFrom(employeeOutletFirst.getDtFrom());
//                               employeeOutletdtfrom.setDtTo(DtTo);
//                               employeeOutletdtfrom.setEmployeeId(employeeOutletFirst.getEmployeeId());
//                               employeeOutletdtfrom.setEmployeeName(employeeOutletFirst.getEmployeeName());
//                               employeeOutletdtfrom.setLocationId(employeeOutletFirst.getLocationId());
//                               employeeOutletdtfrom.setPositionId(employeeOutletFirst.getPositionId());
//                               employeeOutletdtfrom.setScheduleType(employeeOutletFirst.getScheduleType());
//                               long oid1 = pstEmployeeOutlet.insertExc(employeeOutletdtfrom);
//                           } catch (Exception e){
//                               System.out.println("Exc employeeOutletFirst insert" + e);
//                           }
//                           }
//                           
//                            if (employeeOutletFirst.getDtTo() != objMappingOutlet.getDtdEnd() && employeeOutletFirst.getDtTo().after(objMappingOutlet.getDtdEnd()) ){
//                          
//                                try {
//                                   Date DtStart = (Date) objMappingOutlet.getDtdEnd().clone();
//                                   DtStart.setDate(DtStart.getDate() + 1);
//                                   //employeeOutletFirst.setDtFrom(DtStart);
//                                   EmployeeOutlet employeeOutletdtto = new EmployeeOutlet();
//                                   employeeOutletdtto.setDtFrom(DtStart);
//                                   employeeOutletdtto.setDtTo(employeeOutletFirst.getDtTo());
//                                   employeeOutletdtto.setEmployeeId(employeeOutletFirst.getEmployeeId());
//                                   employeeOutletdtto.setEmployeeName(employeeOutletFirst.getEmployeeName());
//                                   employeeOutletdtto.setLocationId(employeeOutletFirst.getLocationId());
//                                   employeeOutletdtto.setPositionId(employeeOutletFirst.getPositionId());
//                                   employeeOutletdtto.setScheduleType(employeeOutletFirst.getScheduleType());
//                               
//                                   long oid2 = pstEmployeeOutlet.insertExc(employeeOutletdtto);
//                               } catch (Exception e){
//                                   System.out.println("Exc employeeOutletFirst insert" + e);
//                               }          
//                            }
//                            
//                            
//                        } else 
                        if (listEmployeeOutletTwo != null && listEmployeeOutletTwo.size() > 0){
                            for (int li = 0; li < listEmployeeOutletTwo.size(); li++){
                                 EmployeeOutlet employeeOutletTwo = (EmployeeOutlet) listEmployeeOutletTwo.get(li);
                                 
                                 //priska 20150608
                                //memotong bagian date end dimana date endnya berapa diantara date mapping yang baru/ yang akan dimasukan 
                               if (!employeeOutletTwo.getDtFrom().equals(objMappingOutlet.getDtStart()) && employeeOutletTwo.getDtFrom().before(objMappingOutlet.getDtStart()) && (employeeOutletTwo.getDtTo().getTime() >= objMappingOutlet.getDtStart().getTime() && employeeOutletTwo.getDtTo().getTime() <= objMappingOutlet.getDtdEnd().getTime()  ) ){
                               try {
                               Date DtTo = (Date) objMappingOutlet.getDtStart().clone();
                               DtTo.setDate(DtTo.getDate() - 1);
                               EmployeeOutlet employeeOutletdtfrom = new EmployeeOutlet();
                               employeeOutletdtfrom.setDtFrom(employeeOutletTwo.getDtFrom());
                               employeeOutletdtfrom.setDtTo(DtTo);
                               employeeOutletdtfrom.setEmployeeId(employeeOutletTwo.getEmployeeId());
                               employeeOutletdtfrom.setEmployeeName(employeeOutletTwo.getEmployeeName());
                               employeeOutletdtfrom.setLocationId(employeeOutletTwo.getLocationId());
                               employeeOutletdtfrom.setPositionId(employeeOutletTwo.getPositionId());
                               employeeOutletdtfrom.setScheduleType(employeeOutletTwo.getScheduleType());
                               long oid1 = pstEmployeeOutlet.insertExc(employeeOutletdtfrom);
                               
                               //hapus yang lama karna sudah diganti
                               long oiddel = pstEmployeeOutlet.deleteExc(employeeOutletTwo.getOID()); 
                               objMappingOutlet.setOID(0);
                            
                           } catch (Exception e){
                               System.out.println("Exc employeeOutletFirst insert" + e);
                           }
                           }
                               //memotong bagian date from dimana date fromnya berapa diantara date mapping yang baru/ yang akan dimasukan 
                             
                                if (!employeeOutletTwo.getDtTo().equals(objMappingOutlet.getDtdEnd()) && employeeOutletTwo.getDtTo().after(objMappingOutlet.getDtdEnd()) && (employeeOutletTwo.getDtFrom().getTime() >= objMappingOutlet.getDtStart().getTime() && employeeOutletTwo.getDtFrom().getTime() <= objMappingOutlet.getDtdEnd().getTime()  ) ){
                               try {
                               Date DtStart = (Date) objMappingOutlet.getDtdEnd().clone();
                               DtStart.setDate(DtStart.getDate() + 1);
                               //employeeOutletFirst.setDtFrom(DtStart);
                               EmployeeOutlet employeeOutletdtTo = new EmployeeOutlet();
                               employeeOutletdtTo.setDtFrom(DtStart);
                               employeeOutletdtTo.setDtTo(employeeOutletTwo.getDtTo());
                               employeeOutletdtTo.setEmployeeId(employeeOutletTwo.getEmployeeId());
                               employeeOutletdtTo.setEmployeeName(employeeOutletTwo.getEmployeeName());
                               employeeOutletdtTo.setLocationId(employeeOutletTwo.getLocationId());
                               employeeOutletdtTo.setPositionId(employeeOutletTwo.getPositionId());
                               employeeOutletdtTo.setScheduleType(employeeOutletTwo.getScheduleType());
                               long oid1 = pstEmployeeOutlet.insertExc(employeeOutletdtTo);
                               
                               //hapus yang lama karna sudah diganti
                               long oiddel = pstEmployeeOutlet.deleteExc(employeeOutletTwo.getOID()); 
                               objMappingOutlet.setOID(0);
                           } catch (Exception e){
                               System.out.println("Exc employeeOutletFirst insert" + e);
                           }
                           } 
                                
                               //jika date from dan date endnya berapa diantara date from dan date end yang baru maka dihapus
                             
                                if ((employeeOutletTwo.getDtFrom().getTime() >= objMappingOutlet.getDtStart().getTime() && employeeOutletTwo.getDtFrom().getTime() <= objMappingOutlet.getDtdEnd().getTime()) && (employeeOutletTwo.getDtTo().getTime() >= objMappingOutlet.getDtStart().getTime() && employeeOutletTwo.getDtTo().getTime() <= objMappingOutlet.getDtdEnd().getTime()) ){
                               try {
                              
                               //hapus yang lama karna sudah diganti
                               long oiddel = pstEmployeeOutlet.deleteExc(employeeOutletTwo.getOID()); 
                               objMappingOutlet.setOID(0);
                           } catch (Exception e){
                               System.out.println("Exc employeeOutletFirst insert" + e);
                           }
                           } 
                                
                            }
                            
                        }
                        
                        if (objMappingOutlet.getOID() == 0 && hashEmpErorr.containsKey(objMappingOutlet.getEmployeeId()) == false) {
                        //    double diffday = objMappingOutlet.getDtdEnd().getTime() - objMappingOutlet.getDtStart().getTime();
                        //    double dayD = diffday / (24 * 60 * 60 * 1000);
                        //    Date DayOne = (Date) objMappingOutlet.getDtStart().clone();
                        //    for (int d =0 ; d < dayD ;d++ ){
                                 try {
                                EmployeeOutlet employeeOutlet = new EmployeeOutlet();
                                employeeOutlet.setDtFrom(objMappingOutlet.getDtStart());
                                employeeOutlet.setDtTo(objMappingOutlet.getDtdEnd());
                            //    employeeOutlet.setDtFrom(DayOne);
                            //    employeeOutlet.setDtTo(DayOne);
                                employeeOutlet.setEmployeeId(objMappingOutlet.getEmployeeId());
                                employeeOutlet.setLocationId(objMappingOutlet.getLocationOutletId());
                                employeeOutlet.setPositionId(objMappingOutlet.getPositionId());

                                long oid = pstEmployeeOutlet.insertExc(employeeOutlet);
                                long oidSchedule=0;
                                if(objMappingOutlet.getScheduleId()!=0){
                                      oidSchedule = PstEmpSchedule.getUpdateScheduleOutlet(objMappingOutlet.getDtStart(), objMappingOutlet.getDtdEnd(), objMappingOutlet.getScheduleId(), objMappingOutlet.getEmployeeId());
                                }
                                // int oidSchedule = PstEmpSchedule.getUpdateScheduleOutlet(objMappingOutlet.getDtFrom(), objMappingOutlet.getDtTo(), objMappingOutlet.getScheduleType(), objMappingOutlet.getEmployeeId());
                                String fullName = "";
                                /*if(hashEmpMsg!=null && hashEmpMsg.size()>0 && hashEmpMsg.get(objMappingOutlet.getEmployeeId())!=null){
                                 fullName = (String)hashEmpMsg.get(objMappingOutlet.getEmployeeId());
                                 }*/
                                Employee employee = new Employee();
                                try {
                                    employee = PstEmployee.fetchExc(objMappingOutlet.getEmployeeId());
                                    fullName = employee.getFullName();
                                    msgString = msgString + "<br>" + resultText[language][RSLT_OK] + " save employee " + fullName;
                                    rsCode=RSLT_OK;
                                } catch (Exception exc) {
                                    System.out.println("Exc empLoutelet insert" + exc);
                                }

                            } catch (DBException dbexc) {
                                excCode = dbexc.getErrorCode();
                                msgString = getSystemMessage(excCode);
                                return getControlMsgId(excCode);
                            } catch (Exception exc) {
                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                            }
                               //  DayOne.setDate(DayOne.getDate() + 1);
                          //  }
                            
                           

                        } else if (objMappingOutlet.getOID() != 0 && hashEmpErorr.containsKey(objMappingOutlet.getEmployeeId()) == false) {
                            //maka dia update
                            try {
                                //update yg lama di update
                                EmployeeOutlet prevObjEmployeeOutlet = new EmployeeOutlet();
                                try {
                                    prevObjEmployeeOutlet = PstEmployeeOutlet.fetchExc(objMappingOutlet.getOID());
                                } catch (Exception exc) {
                                    System.out.println("fetch empLoutelet " + exc);
                                }

                                //lalu di insert
                                //kenapa di set houre n minutesnya karena ketika memilih schedulenya di tgl yg sama maka dia akan muncul jadi 2 tanggall yg sama
                                Date dtNewStart =(objMappingOutlet.getDtStart()==null?null:(Date)objMappingOutlet.getDtStart().clone());
                                if(dtNewStart!=null){
                                    dtNewStart.setHours(0);dtNewStart.setMinutes(0); 
                                }
                                Date dtNewEnd =(objMappingOutlet.getDtStart()==null?null:(Date)objMappingOutlet.getDtdEnd().clone());
//                                if(dtNewEnd!=null){
//                                    dtNewEnd.setHours(23);dtNewEnd.setMinutes(59); 
//                                }
                                Date dtPrevStart =(prevObjEmployeeOutlet.getDtFrom()==null?null:(Date)prevObjEmployeeOutlet.getDtFrom().clone());
                                if(dtPrevStart!=null){
                                    dtPrevStart.setHours(0);dtPrevStart.setMinutes(0); 
                                }
                                Date dtPrevEnd =(prevObjEmployeeOutlet.getDtTo()==null?null:(Date)prevObjEmployeeOutlet.getDtTo().clone());
                                //update by satrya 2014-11-16
//                                if(dtPrevEnd!=null){
//                                    dtPrevEnd.setHours(23);dtPrevEnd.setMinutes(59); 
//                                }
                                /*if (objMappingOutlet.getDtStart() != null && prevObjEmployeeOutlet.getDtFrom() != null
                                        && objMappingOutlet.getDtdEnd() !=null && prevObjEmployeeOutlet.getDtTo()!=null
                                        && (objMappingOutlet.getDtStart().getTime() != prevObjEmployeeOutlet.getDtFrom().getTime()
                                        || objMappingOutlet.getDtdEnd().getTime() != prevObjEmployeeOutlet.getDtTo().getTime())) {*/
//                                if (dtNewStart != null && dtPrevStart != null
//                                        && dtNewEnd !=null && dtPrevEnd!=null
//                                        && (dtNewStart.getTime() != dtPrevStart.getTime()
//                                        || dtNewEnd.getTime() != dtPrevEnd.getTime())) {
                                    //update by satrya 2014-11-16
                                //karena ada kasus pertama dari tanggal 12 nov s./d 14 nov lalu di rubah menjadi 14 nov saja, dia menjadi nov double
                                    if (false) {
                                    EmployeeOutlet employeeOutlet = new EmployeeOutlet();
                                    employeeOutlet.setDtFrom(objMappingOutlet.getDtStart());
                                    employeeOutlet.setDtTo(objMappingOutlet.getDtdEnd());
                                    employeeOutlet.setEmployeeId(objMappingOutlet.getEmployeeId());
                                    employeeOutlet.setLocationId(objMappingOutlet.getLocationOutletId());
                                    employeeOutlet.setPositionId(objMappingOutlet.getPositionId());

                                    long oid = pstEmployeeOutlet.insertExc(employeeOutlet);
                                    int oidSchedule=0;
                                    //if(objMappingOutlet.getHashCheckedSchedule(objMappingOutlet.getEmployeeId())){
                                    if(objMappingOutlet.getScheduleId()!=0){
                                      oidSchedule = PstEmpSchedule.getUpdateScheduleOutlet(objMappingOutlet.getDtStart(), objMappingOutlet.getDtdEnd(), objMappingOutlet.getScheduleId(), objMappingOutlet.getEmployeeId());
                                    }
                                    if(oid!=0){
                                        if(oidSchedule!=0){
                                            String fullName = "";
                                            Employee employee = new Employee();
                                            try {
                                                employee = PstEmployee.fetchExc(objMappingOutlet.getEmployeeId());
                                                fullName = employee.getFullName();
                                                msgString = msgString + "<br>" + resultText[language][RSLT_OK] + " save and update schedule employee " + fullName;
                                                rsCode=RSLT_OK;
                                            } catch (Exception exc) {
                                                System.out.println("Exc empLoutelet insert" + exc);
                                            }
                                        }else{
                                            String fullName = "";
                                            Employee employee = new Employee();
                                            try {
                                                employee = PstEmployee.fetchExc(objMappingOutlet.getEmployeeId());
                                                fullName = employee.getFullName();
                                                msgString = msgString + "<br>" + resultText[language][RSLT_OK] + " save employee mapping " + fullName;
                                                rsCode=RSLT_OK;
                                            } catch (Exception exc) {
                                                System.out.println("Exc empLoutelet insert" + exc);
                                            }
                                        }
                                    }
                                    if(objMappingOutlet.getDtdEnd().getTime()<prevObjEmployeeOutlet.getDtTo().getTime()){
                                        //update
                                        //prevObjEmployeeOutlet.setDtFrom(objMappingOutlet.getDtStart());
                                        employeeOutlet = new EmployeeOutlet();
                                        employeeOutlet.setDtFrom(new Date(objMappingOutlet.getDtdEnd().getTime()+1000 * 60 * 60 * 24));
                                        employeeOutlet.setDtTo(prevObjEmployeeOutlet.getDtTo());
                                        employeeOutlet.setEmployeeId(prevObjEmployeeOutlet.getEmployeeId());
                                        employeeOutlet.setLocationId(prevObjEmployeeOutlet.getLocationId());
                                        employeeOutlet.setPositionId(prevObjEmployeeOutlet.getPositionId());
                                        employeeOutlet.setOID(objMappingOutlet.getOID());
                                        long ioid = pstEmployeeOutlet.updateExc(employeeOutlet);
                                        rsCode=RSLT_OK;
                                    }
                                } else if (objMappingOutlet.getDtStart() != null && prevObjEmployeeOutlet.getDtFrom() != null) {
                                    //lalukan update
                                    EmployeeOutlet employeeOutlet = new EmployeeOutlet();
                                    employeeOutlet.setDtFrom(objMappingOutlet.getDtStart());
                                    employeeOutlet.setDtTo(objMappingOutlet.getDtdEnd());
                                    employeeOutlet.setEmployeeId(objMappingOutlet.getEmployeeId());
                                    employeeOutlet.setLocationId(objMappingOutlet.getLocationOutletId());
                                    employeeOutlet.setPositionId(objMappingOutlet.getPositionId());
                                     employeeOutlet.setOID(objMappingOutlet.getOID());
                                    long oid = pstEmployeeOutlet.updateExc(employeeOutlet);
                                    int oidSchedule=0;
                                    //if(objMappingOutlet.getHashCheckedSchedule(objMappingOutlet.getEmployeeId())){
                                    //  oidSchedule = PstEmpSchedule.getUpdateScheduleOutlet(objMappingOutlet.getDtStart(), objMappingOutlet.getDtdEnd(), objMappingOutlet.getScheduleId(), objMappingOutlet.getEmployeeId());
                                    //}
                                     if(objMappingOutlet.getScheduleId()!=0 && oid!=0){
                                      oidSchedule = PstEmpSchedule.getUpdateScheduleOutlet(objMappingOutlet.getDtStart(), objMappingOutlet.getDtdEnd(), objMappingOutlet.getScheduleId(), objMappingOutlet.getEmployeeId());
                                    }
                                    if(oid!=0){
                                        if(oidSchedule!=0){
                                            String fullName = "";
                                            Employee employee = new Employee();
                                            try {
                                                employee = PstEmployee.fetchExc(objMappingOutlet.getEmployeeId());
                                                fullName = employee.getFullName();
                                                msgString = msgString + "<br>" + resultText[language][RSLT_OK] + " save and update schedule employee " + fullName;
                                                rsCode=RSLT_OK;
                                            } catch (Exception exc) {
                                                System.out.println("Exc empLoutelet insert" + exc);
                                            }
                                        }else{
                                            String fullName = "";
                                            Employee employee = new Employee();
                                            try {
                                                employee = PstEmployee.fetchExc(objMappingOutlet.getEmployeeId());
                                                fullName = employee.getFullName();
                                                msgString = msgString + "<br>" + resultText[language][RSLT_OK] + " save employee mapping " + fullName;
                                                 rsCode=RSLT_OK;
                                            } catch (Exception exc) {
                                                System.out.println("Exc empLoutelet insert" + exc);
                                            }
                                        }
                                    }
                                }
                                // int oidSchedule = PstEmpSchedule.getUpdateScheduleOutlet(objMappingOutlet.getDtFrom(), objMappingOutlet.getDtTo(), objMappingOutlet.getScheduleType(), objMappingOutlet.getEmployeeId());
                                

                            } catch (DBException dbexc) {
                                excCode = dbexc.getErrorCode();
                                msgString = getSystemMessage(excCode);
                                return getControlMsgId(excCode);
                            } catch (Exception exc) {
                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                            }
                        }
                    }//end loop employee selected

                }

                break;



            case Command.DELETE:

                try {


                    frmMappingOutlet.requestEntityObjectMultiple(hashSchedule, hashScheduleSymbol);
                    if (frmMappingOutlet != null && frmMappingOutlet.getMsgFrm() != null && frmMappingOutlet.getMsgFrm().length() > 0) {
                        msgString = frmMappingOutlet.getMsgFrm();
                        return RSLT_FORM_INCOMPLETE;
                    }

                    //Hashtable hashEmpMsg = new Hashtable();
                    if (frmMappingOutlet.getMappingOutletList() != null && frmMappingOutlet.getMappingOutletList().size() > 0) {
                        for (int idx = 0; idx < frmMappingOutlet.getMappingOutletList().size(); idx++) {
                            objMappingOutlet = new MappingOutlet();
                            objMappingOutlet = (MappingOutlet) frmMappingOutlet.getMappingOutletList().get(idx);
//                            if (frmMappingOutlet != null && frmMappingOutlet.errorSize() > 0) {
//                                objMappingOutlet = (EmployeeOutlet) frmMappingOutlet.getMappingOutletList().get(idx);
//                                msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
//                                return RSLT_FORM_INCOMPLETE;
//                            }
                           

                            if (objMappingOutlet.getOID() != 0 && objMappingOutlet.getDtStart()!=null && objMappingOutlet.getDtdEnd()!=null && objMappingOutlet.getEmployeeId()!=0) {
                                try {
                                    long oid = pstEmployeeOutlet.deleteEmployeeOutlet(objMappingOutlet.getDtStart(),objMappingOutlet.getDtdEnd(),objMappingOutlet.getEmployeeId());
                                    String fullName = "";
                                    /*if(hashEmpMsg!=null && hashEmpMsg.size()>0 && hashEmpMsg.get(objMappingOutlet.getEmployeeId())!=null){
                                     fullName = (String)hashEmpMsg.get(objMappingOutlet.getEmployeeId());
                                     }*/
                                    Employee employee = new Employee();
                                    try {
                                        employee = PstEmployee.fetchExc(objMappingOutlet.getEmployeeId());
                                        fullName = employee.getFullName();
                                       
                                    } catch (Exception exc) {
                                        System.out.println("Exc empLoutelet insert" + exc);
                                    }
                                    
                                     if (oid != 0) {
                                        msgString = msgString + "<br>" + FRMMessage.getMessage(FRMMessage.MSG_DELETED) + fullName;;//FRMMessage.getMessage(FRMMessage.MSG_DELETED) ;
                                        rsCode=RSLT_OK;
                                        //excCode = RSLT_OK;
                                    } else {
                                        msgString = msgString + "<br>" +  FRMMessage.getMessage(FRMMessage.ERR_DELETED) + fullName;
                                        //msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                        //excCode = RSLT_FORM_INCOMPLETE;
                                    }
                                } catch (Exception exc) {
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                    return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                                }

                            }
                        }

                    }
 
                } catch (Exception exc) {
                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                }

                break;

            default:

        }
        return rsCode;
    }
}
