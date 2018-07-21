/* 
 * Ctrl Name  		:  CtrlDepartment.java 
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
package com.dimata.harisma.form.masterdata;

/* java package */
import java.util.*;
import javax.servlet.*;
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
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.*;

public class CtrlDepartment extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private Department department;
    private PstDepartment pstDepartment;
    private FrmDepartment frmDepartment;
    int language = LANGUAGE_DEFAULT;

    public CtrlDepartment(HttpServletRequest request) {
        msgString = "";
        department = new Department();
        try {
            pstDepartment = new PstDepartment(0);
        } catch (Exception e) {
            ;
        }
        frmDepartment = new FrmDepartment(request, department);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmDepartment.addError(frmDepartment.FRM_FIELD_DEPARTMENT_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public Department getDepartment() {
        return department;
    }

    public FrmDepartment getForm() {
        return frmDepartment;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidDepartment) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidDepartment != 0) {
                    try {
                        department = PstDepartment.fetchExc(oidDepartment);
                    } catch (Exception exc) {
                    }
                }

                frmDepartment.requestEntityObject(department);

                if (frmDepartment.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (department.getOID() == 0) {
                    try {
                        long oid = pstDepartment.insertExc(this.department);

                        /* Untuk NIKKO*/

                        if (oid != 0) {

                            try {

                                String db_backup_url = PstSystemProperty.getValueByName("DB_BACKUP_URL");
                                String db_backup_usr = PstSystemProperty.getValueByName("DB_BACKUP_USR");
                                String db_backup_psd = PstSystemProperty.getValueByName("DB_BACKUP_PSWD");

                                if (db_backup_url.compareTo(PstSystemProperty.SYS_NOT_INITIALIZED) != 0
                                        && db_backup_usr.compareTo(PstSystemProperty.SYS_NOT_INITIALIZED) != 0
                                        && db_backup_psd.compareTo(PstSystemProperty.SYS_NOT_INITIALIZED) != 0){

                                    try {
                                        Class.forName("com.mysql.jdbc.Driver");
                                        System.out.println("Driver Found");
                                    }catch (ClassNotFoundException e) {
                                        javax.swing.JOptionPane.showMessageDialog(null, "Driver Not Found " + e.toString());
                                    }

                                    Connection con = null;
                                    Statement stmt = null;

                                    try{

                                        con = DriverManager.getConnection(db_backup_url, db_backup_usr, db_backup_psd);

                                        String sql = "INSERT INTO "+PstDepartment.TBL_HR_DEPARTMENT+" ( "+
                                                PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+","+
                                                PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+","+
                                                PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+","+
                                                PstDepartment.fieldNames[PstDepartment.FLD_DESCRIPTION]+" ) VALUES ( "+
                                                oid+","+
                                                this.department.getDivisionId()+",'"+
                                                this.department.getDepartment()+"','"+
                                                this.department.getDescription()+"' ) ";

                                        stmt = con.createStatement();
                                        stmt.executeUpdate(sql);        

                                    }catch(Exception E){
                                        System.out.println("[exception] "+E.toString());
                                    }finally{
                                        try {
                                            stmt.close();
                                            con.close();
                                        } catch (Exception E) {
                                            System.out.println("[exc] " + E.toString());
                                        }
                                    }
                                }

                            } catch (Exception E) {
                                System.out.println("[exeption] " + E.toString());
                            }
                        }

                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {
                        
                        long oid = pstDepartment.updateExc(this.department);

                        if(oid != 0){

                            try{

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

                                    try{

                                         con = DriverManager.getConnection(db_backup_url, db_backup_usr, db_backup_psd);

                                         String sql = "UPDATE "+PstDepartment.TBL_HR_DEPARTMENT+" SET "+                                                 
                                                 PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+" = "+this.department.getDivisionId()+","+
                                                 PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" = '"+this.department.getDepartment()+"',"+
                                                 PstDepartment.fieldNames[PstDepartment.FLD_DESCRIPTION]+" = '"+this.department.getDescription()+"' "+
                                                 " WHERE "+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = "+oid;

                                         stmt = con.createStatement();
                                         stmt.executeUpdate(sql);

                                    }catch(Exception E){
                                        System.out.println("[exception] "+E.toString());
                                    }finally {
                                        try {
                                            stmt.close();
                                            con.close();
                                        } catch (Exception E) {
                                            System.out.println("[exc] " + E.toString());
                                        }
                                    }
                                }

                            }catch(Exception E){
                                System.out.println();
                            }
                        }



                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidDepartment != 0) {
                    try {
                        department = PstDepartment.fetchExc(oidDepartment);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidDepartment != 0) {
                    try {
                        if (PstDepartment.checkMaster(oidDepartment)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }

                        department = PstDepartment.fetchExc(oidDepartment);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidDepartment != 0) {
                    try {
                        long oid = PstDepartment.deleteExc(oidDepartment);

                        if(oid != 0){

                            try{

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

                                    try{

                                        con = DriverManager.getConnection(db_backup_url, db_backup_usr, db_backup_psd);

                                        String sql = "DELETE FROM "+PstDepartment.TBL_HR_DEPARTMENT+" WHERE "+
                                                PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = "+oid;

                                        stmt = con.createStatement();
                                        stmt.executeUpdate(sql);

                                    }catch(Exception E){
                                        System.out.println();
                                    }finally{
                                        try{
                                            try {
                                                stmt.close();
                                                con.close();
                                            } catch (Exception e) {
                                                System.out.println("EXCEPTION " + e.toString());
                                            }
                                        }catch(Exception E){
                                            System.out.println("[exc] "+E.toString());
                                        }
                                    }
                                }
                                
                            }catch(Exception E){
                                System.out.println("[exception] "+E.toString());
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
