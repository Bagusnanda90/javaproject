/* 
 * Ctrl Name  		:  CtrlSection.java 
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

public class CtrlSection extends Control implements I_Language {

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
    private Section section;
    private PstSection pstSection;
    private FrmSection frmSection;
    int language = LANGUAGE_DEFAULT;

    public CtrlSection(HttpServletRequest request) {
        msgString = "";
        section = new Section();
        try {
            pstSection = new PstSection(0);
        } catch (Exception e) {
            ;
        }
        frmSection = new FrmSection(request, section);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmSection.addError(frmSection.FRM_FIELD_SECTION_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public Section getSection() {
        return section;
    }

    public FrmSection getForm() {
        return frmSection;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidSection) {
        return action(cmd, oidSection, "");
    }
    
    public int action(int cmd, long oidSection, String oidDeleteAll) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidSection != 0) {
                    try {
                        section = PstSection.fetchExc(oidSection);
                    } catch (Exception exc) {
                    }
                }

                frmSection.requestEntityObject(section);

                if (frmSection.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (section.getOID() == 0) {
                    try {
                        long oid = pstSection.insertExc(this.section);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        if (oid != 0) {

                            String db_backup_url = PstSystemProperty.getValueByName("DB_BACKUP_URL");
                            String db_backup_usr = PstSystemProperty.getValueByName("DB_BACKUP_USR");
                            String db_backup_psd = PstSystemProperty.getValueByName("DB_BACKUP_PSWD");

                            /* Untuk pengecekan kelengkapan system property */
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

                                    String sql = "INSERT INTO " + PstSection.TBL_HR_SECTION + " ("
                                            + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + ","
                                            + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + ","
                                            + PstSection.fieldNames[PstSection.FLD_SECTION] + ","
                                            + PstSection.fieldNames[PstSection.FLD_DESCRIPTION] + " ) VALUES ( "
                                            + oid + ","
                                            + this.section.getDepartmentId() + ",'"
                                            + this.section.getSection() + "','"
                                            + this.section.getDescription() + "' )";

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

                        long oid = pstSection.updateExc(this.section);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
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

                                    try{

                                        con = DriverManager.getConnection(db_backup_url, db_backup_usr, db_backup_psd);

                                        String sql = "UPDATE "+PstSection.TBL_HR_SECTION+" SET "+
                                                PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+this.section.getDepartmentId()+","+
                                                PstSection.fieldNames[PstSection.FLD_SECTION]+"='"+this.section.getSection()+"',"+
                                                PstSection.fieldNames[PstSection.FLD_DESCRIPTION]+"='"+this.section.getDescription()+"' "+
                                                " WHERE "+PstSection.fieldNames[PstSection.FLD_SECTION_ID]+"="+oid;

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

                            } catch (Exception E) {
                                System.out.println("[Exception] " + E.toString());
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
                if (oidSection != 0) {
                    try {
                        section = PstSection.fetchExc(oidSection);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidSection != 0) {
                    try {
                        if (PstSection.checkMaster(oidSection)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }

                        section = PstSection.fetchExc(oidSection);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidSection != 0) {
                    try {
                        long oid = PstSection.deleteExc(oidSection);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
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

                                        String sql = "DELETE FROM "+PstSection.TBL_HR_SECTION+" WHERE "+
                                                PstSection.fieldNames[PstSection.FLD_SECTION_ID]+"="+oid;

                                        stmt = con.createStatement();
                                        stmt.executeUpdate(sql);

                                    }catch(Exception E){
                                        System.out.println("[exception] "+E.toString());
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
                case Command.DELETEALL:
                //splits => untuk mengurutkan beberapa data menjadi baris data
                String[] splits = oidDeleteAll.split(",");
                for (String asset : splits) {
                    if (asset != "") {
                        long oidDoc = Long.parseLong(asset);
                        if (oidDoc != 0) {
                            try {
                                long oid = PstSection.deleteExc(oidDoc);
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
                    }
                }
                break;
            default:

        }
        return rsCode;
    }
}
