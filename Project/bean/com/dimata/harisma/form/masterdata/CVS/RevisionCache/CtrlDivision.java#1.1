/* 
 * Ctrl Name  		:  CtrlPosition.java 
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

public class CtrlDivision extends Control implements I_Language {

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
    private Division division;
    private PstDivision pstDivision;
    private FrmDivision frmDivision;
    int language = LANGUAGE_DEFAULT;

    public CtrlDivision(HttpServletRequest request) {
        msgString = "";
        division = new Division();
        try {
            pstDivision = new PstDivision(0);
        } catch (Exception e) {
            ;
        }
        frmDivision = new FrmDivision(request, division);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmDivision.addError(frmDivision.FRM_FIELD_DIVISION_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public Division getDivision() {
        return division;
    }

    public FrmDivision getForm() {
        return frmDivision;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidDivision) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidDivision != 0) {
                    try {
                        division = PstDivision.fetchExc(oidDivision);
                    } catch (Exception exc) {
                    }
                }

                frmDivision.requestEntityObject(division);

                if (frmDivision.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (division.getOID() == 0) {
                    try {
                        long oid = pstDivision.insertExc(this.division);

                        /* UNTUK KASUS NIKKO*/
                        /* SETIAP MEMASUKAN DATA DIVISION BARU MAKA AKAN JUGA MENUPDATE
                        DATA DI DATABASE LAIN, KARENA DATABSE ADA 2*/

                        if (oid != 0) {
                            try {

                                String db_backup_url = PstSystemProperty.getValueByName("DB_BACKUP_URL");
                                String db_backup_usr = PstSystemProperty.getValueByName("DB_BACKUP_USR");
                                String db_backup_psd = PstSystemProperty.getValueByName("DB_BACKUP_PSWD");

                                /* Pengecekan kelengkapan konfigurasi di system property */
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

                                        String sql = "INSERT INTO " + PstDivision.TBL_HR_DIVISION + " ("
                                                + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + ","
                                                + PstDivision.fieldNames[PstDivision.FLD_DIVISION] + ","
                                                + PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + ","
                                                + PstDivision.fieldNames[PstDivision.FLD_DESCRIPTION]
                                                + ") VALUES ( "
                                                + division.getOID() + ",'"
                                                + division.getDivision() + "','"
                                                + division.getCompanyId() + "','"
                                                + division.getDescription() + "')";

                                        stmt = con.createStatement();
                                        stmt.executeUpdate(sql);

                                    } catch (Exception E) {
                                        System.out.println("[exception] " + E.toString());
                                    } finally {
                                        try {
                                            stmt.close();
                                            con.close();
                                        } catch (Exception E) {
                                            System.out.println("[exc] " + E.toString());
                                        }
                                    }

                                }

                            } catch (Exception E) {
                                System.out.println("[exception] " + E.toString());
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
                        long oid = pstDivision.updateExc(this.division);

                        if (oid != 0) {

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
                                        
                                        String sql = "UPDATE " + PstDivision.TBL_HR_DIVISION + " SET "
                                                + PstDivision.fieldNames[PstDivision.FLD_DIVISION] + " = '" + this.division.getDivision() + "',"
                                                + PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + " = '" + this.division.getCompanyId() + "',"
                                                + PstDivision.fieldNames[PstDivision.FLD_DESCRIPTION] + " = '" + this.division.getDescription() + "' "
                                                + " WHERE " + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " = " + oid;

                                        stmt = con.createStatement();
                                        stmt.executeUpdate(sql);

                                    } catch (Exception E) {
                                        System.out.println("[exception] "+E.toString());
                                    } finally {
                                        try {
                                            stmt.close();
                                            con.close();

                                        } catch (Exception E) {
                                            System.out.println("[exc] " + E.toString());
                                        }
                                    }
                                }

                            } catch (Exception E) {
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
                if (oidDivision != 0) {
                    try {
                        division = PstDivision.fetchExc(oidDivision);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidDivision != 0) {
                    try {
                        if (PstDivision.checkMaster(oidDivision)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }

                        division = PstDivision.fetchExc(oidDivision);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidDivision != 0) {
                    try {
                        long oid = PstDivision.deleteExc(oidDivision);

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

                                        String sql = "DELETE FROM "+PstDivision.TBL_HR_DIVISION+" WHERE "+
                                                PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" = "+oid;

                                        stmt = con.createStatement();
                                        stmt.executeUpdate(sql);

                                    }catch(Exception E){
                                        System.out.println("[exc] "+E.toString());
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
