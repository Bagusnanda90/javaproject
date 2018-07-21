/*
 * Ctrl Name  		:  CtrlEmployeeMutation.java
 * Created on 	:  [date] [time] AM/PM
 *
 * @author  		: karya
 * @version  		: 01
 */
/*******************************************************************
 * Class Description 	: [project description ... ]
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/
package com.dimata.harisma.form.employee;

/* java package */
import javax.servlet.http.*;
/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.form.locker.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.session.employee.SessEmployee;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import java.util.Vector;
import javax.mail.Session;

public class CtrlEmployeeMutation extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_EMPLYEE_NUM_EXIST = 4;
    //update by devin 2014-02-05
    public static int RSLT_FRM_DATE_IN_RANGE = 5;
    
    public static String[][] resultText = {
         //update by devin 2014-02-05
        //        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", " employee number sudah ada"},

        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", " employee number sudah ada","Tanggal Yang Anda Inputkan sudah Ada"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete", "Employee number exist"}};
    private int start;
    private String msgString;
    private Employee employee;
    private PstEmployee pstEmployee;
    private FrmEmployeeMutation frmEmployeeMutation;
    private FrmCareerPath frmcareerpath;
    //locker;
    private Locker locker;
    private PstLocker pstLocker;
    private FrmLocker frmLocker;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmployeeMutation(HttpServletRequest request) {
        msgString = "";
        employee = new Employee();
        locker = new Locker();
        try {
            pstEmployee = new PstEmployee(0);
            pstLocker = new PstLocker(0);
        } catch (Exception e) {
            ;
        }

        frmEmployeeMutation = new FrmEmployeeMutation(request, employee);

        frmLocker = new FrmLocker(request, locker);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmployeeMutation.addError(frmEmployeeMutation.FRM_FIELD_EMPLOYEE_ID_MUTATION, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public Employee getEmployee() {
        return employee;
    }

    public FrmEmployeeMutation getForm() {
        return frmEmployeeMutation;
    }

    public Locker getLocker() {
        return locker;
    }

    public FrmLocker getFormLocker() {
        return frmLocker;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

/**
 * create by devin
 * @param cmd
 * @param oidEmployee
 * @param request
 * @return 
 */
    public int action(int cmd, long oidEmployee, HttpServletRequest request) {

    return action(cmd, oidEmployee, request, null, null);
}
    //update by devin 2014-02-06
// public int action(int cmd, long oidEmployee, HttpServletRequest request,Date dateFrom, Date dateTo) {
    public int action(int cmd, long oidEmployee, HttpServletRequest request,Date dateFrom, Date dateTo) {
        

        String MachineFnSpot = "";

        try {
            MachineFnSpot = PstSystemProperty.getValueByName("MACHINE_FN_SPOT");
        } catch (Exception e) {
            MachineFnSpot = "";
            System.out.println("Exception " + e.toString());
        }

        msgString = "";
        String tmpBarcodeNumber = "";
        String tmpFullName = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;

        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidEmployee != 0) {
                    try {
                        employee = PstEmployee.fetchExc(oidEmployee);
                        tmpBarcodeNumber = employee.getBarcodeNumber();
                        tmpFullName = employee.getFullName();
                    } catch (Exception exc) {
                    }
                }else {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;                    
                }
  //update by devin 2014-02-06
  //frmEmployeeMutation.requestEntityObject(employee);

        
                frmEmployeeMutation.requestEntityObject(employee);
              //  HttpSession session=request.getSession();   
              //  session.putValue("sesloc", employee.getLocationId());

                if (frmEmployeeMutation.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }


                //update by devin 2014-02-06
                                Vector data = new Vector();
                                if(oidEmployee!=0){
                                   
                                  long locationnew = employee.getLocationId();
                                     //data =PstCareerPath.dateCareerPathwithlocation(oidEmployee,locationnew);
                                  
                                     //data =PstCareerPath.dateCareerPath(oidEmployee);
                                    String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee + " AND (" + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 0 + " OR " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 1 + " )";
                                    String orderClause = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];
                                    data = PstCareerPath.list(0, 0, whereClause, orderClause);
                                    
                                         
                                     
                                
                              if(data !=null && data.size() >0){     
                                 for(int i=0; i<data.size();i++){
                                         CareerPath care = (CareerPath)data.get(i);
                                         if(employee!=null && care!=null && care.getWorkFrom()!=null && care.getWorkTo()!=null && dateFrom!=null && dateTo!=null){
                                   Date newStartDate = care.getWorkFrom();
                                   Date newEndDate = care.getWorkTo();
                                   Date startDate = dateFrom;
                                   Date endDate = dateTo;
                                   String sTanggalTo =Formater.formatDate(newStartDate, "dd-MM-yyyy");
                                   String sTanggalFrom =Formater.formatDate(newEndDate, "dd-MM-yyyy");
                                   String Error=""+sTanggalTo +" TO " + sTanggalFrom;
                                   if ((oidEmployee!=0 ? (care.getOID() == oidEmployee?false:true) :  care.getOID() != oidEmployee) &&newStartDate.after( dateFrom) && newStartDate.before(dateTo)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((oidEmployee!=0 ? (care.getOID() == oidEmployee?false:true) :  care.getOID() != oidEmployee) &&newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((oidEmployee!=0 ? (care.getOID() == oidEmployee?false:true) :  care.getOID() != oidEmployee) &&startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ( (oidEmployee!=0 ? (care.getOID() == oidEmployee?false:true) :  care.getOID() != oidEmployee) && endDate.after(newStartDate) && endDate.before(newEndDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ( (oidEmployee!=0 ? (care.getOID() == oidEmployee?false:true) :  care.getOID() != oidEmployee) && newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    /*else if (newEndDate.equals(endDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }*/
                                     else {
                                        //maka dia tidak overlap
                                     }
                                         
                                 
                                 }
                                 }
                                }
                                }

                try {
                    long oid = pstEmployee.updateExc(this.employee);
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_UPDATED);
                } catch (DBException dbexc) {
                    excCode = dbexc.getErrorCode();
                    msgString = getSystemMessage(excCode);
                } catch (Exception exc) {
                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                }

                break;

            case Command.EDIT:
                if (oidEmployee != 0) {
                    try {
                        employee = PstEmployee.fetchExc(oidEmployee);
                        if (employee.getLockerId() != 0) {
                            locker = PstLocker.fetchExc(employee.getLockerId());
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmployee != 0) {
                    try {
                        employee = PstEmployee.fetchExc(oidEmployee);
                        if (employee.getLockerId() != 0) {
                            locker = PstLocker.fetchExc(employee.getLockerId());
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                System.out.println("oidEmployee " + oidEmployee);
                if (oidEmployee != 0) {
                    try {

                        Employee objEmployee = new Employee();

                        try {
                            objEmployee = PstEmployee.fetchExc(oidEmployee);
                        } catch (Exception e) {
                            System.out.println("Exception " + e.toString());
                        }

                        long oid = PstEmpLanguage.deleteByEmployee(oidEmployee);
                        oid = PstFamilyMember.deleteByEmployee(oidEmployee);
                        oid = PstExperience.deleteByEmployee(oidEmployee);

                        oid = PstCareerPath.deleteByEmployee(oidEmployee);
                        oid = PstEmpSalary.deleteByEmployee(oidEmployee);
                        oid = PstEmpSchedule.deleteByEmployee(oidEmployee);
                        oid = PstLeave.deleteByEmployee(oidEmployee);
                        oid = PstDayOfPayment.deleteByEmployee(oidEmployee);
                        oid = PstPresence.deleteByEmployee(oidEmployee);
                        oid = PstLocker.deleteByEmployee(oidEmployee);
                        oid = PstEmployee.deleteExc(oidEmployee);


                        /*Untuk penghapusan data di database backup untuk kasus NIKKO*/
                        try {

                            String db_backup_url = PstSystemProperty.getValueByName("DB_BACKUP_URL");
                            String db_backup_usr = PstSystemProperty.getValueByName("DB_BACKUP_USR");
                            String db_backup_psd = PstSystemProperty.getValueByName("DB_BACKUP_PSWD");

                            if (db_backup_url.compareTo(PstSystemProperty.SYS_NOT_INITIALIZED) != 0
                                    && db_backup_usr.compareTo(PstSystemProperty.SYS_NOT_INITIALIZED) != 0
                                    && db_backup_psd.compareTo(PstSystemProperty.SYS_NOT_INITIALIZED) != 0) {

                                try {
                                    Class.forName("com.mysql.jdbc.Driver");
                                    System.out.println("Driver Found");
                                } catch (ClassNotFoundException e) {
                                    javax.swing.JOptionPane.showMessageDialog(null, "Driver Not Found " + e.toString());
                                }

                                Connection con = null;
                                Statement stmt = null;
                                try {

                                    con = DriverManager.getConnection(db_backup_url, db_backup_usr, db_backup_psd);

                                    String sql = "DELETE FROM " + PstEmployee.TBL_HR_EMPLOYEE + " WHERE "
                                            + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = " + oidEmployee;

                                    stmt = con.createStatement();
                                    stmt.executeUpdate(sql);

                                } catch (Exception E) {
                                    System.out.println("[exception] UPDATE INTO DATABASE BACKUP " + E.toString());
                                } finally {
                                    try {
                                        stmt.close();
                                        con.close();
                                    } catch (Exception e) {
                                        System.out.println("EXCEPTION " + e.toString());
                                    }
                                }
                            }

                        } catch (Exception E) {

                            System.out.println("[exception] " + E.toString());

                        }

                        /* Penghapusan untuk data pada database mesin finger spot*/

                        if (objEmployee.getOID() != 0) {
                            if (!MachineFnSpot.equals("") && MachineFnSpot.equals("ok")) {
                                SessEmployee.delDbFingerSpot(objEmployee.getBarcodeNumber());
                            }
                        }

                        if (oid != 0) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }

                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            default:

        }
        return rsCode;
    }
}
