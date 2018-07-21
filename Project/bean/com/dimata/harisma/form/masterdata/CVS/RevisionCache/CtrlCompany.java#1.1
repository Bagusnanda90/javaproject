/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: CtrlCompany
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Wiweka
 */
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

public class CtrlCompany extends Control implements I_Language{
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
    private Company company;
    private PstCompany pstCompany;
    private FrmCompany frmCompany;
    int language = LANGUAGE_DEFAULT;

    public CtrlCompany(HttpServletRequest request) {
        msgString = "";
        company = new Company();
        try {
            pstCompany = new PstCompany(0);
        } catch (Exception e) {
            ;
        }
        frmCompany = new FrmCompany(request, company);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmCompany.addError(frmCompany.FRM_FIELD_COMPANY_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public Company getCompany() {
        return company;
    }

    public FrmCompany getForm() {
        return frmCompany;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidCompany) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidCompany != 0) {
                    try {
                        company = PstCompany.fetchExc(oidCompany);
                    } catch (Exception exc) {
                    }
                }

                frmCompany.requestEntityObject(company);

                if (frmCompany.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (company.getOID() == 0) {
                    try {
                        long oid = pstCompany.insertExc(this.company);

                        
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

                                        String sql = "INSERT INTO " + PstCompany.TBL_HR_COMPANY + " ("
                                                + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + ","
                                                + PstCompany.fieldNames[PstCompany.FLD_COMPANY] + ","
                                                + PstCompany.fieldNames[PstCompany.FLD_DESCRIPTION]
                                                + ") VALUES ( "
                                                + company.getOID() + ",'"
                                                + company.getCompany() + "','"
                                                + company.getDescription() + "')";

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
                        long oid = pstCompany.updateExc(this.company);

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

                                        String sql = "UPDATE " + PstCompany.TBL_HR_COMPANY + " SET "
                                                + PstCompany.fieldNames[PstCompany.FLD_COMPANY] + " = '" + this.company.getCompany() + "',"
                                                + PstCompany.fieldNames[PstCompany.FLD_DESCRIPTION] + " = '" + this.company.getDescription() + "' "
                                                + " WHERE " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + " = " + oid;

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
                if (oidCompany != 0) {
                    try {
                        company = PstCompany.fetchExc(oidCompany);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidCompany != 0) {
                    try {
                        if (PstCompany.checkMaster(oidCompany)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }

                        company = PstCompany.fetchExc(oidCompany);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidCompany != 0) {
                    try {
                        long oid = PstCompany.deleteExc(oidCompany);

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

                                        String sql = "DELETE FROM "+PstCompany.TBL_HR_COMPANY+" WHERE "+
                                                PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+" = "+oid;

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
