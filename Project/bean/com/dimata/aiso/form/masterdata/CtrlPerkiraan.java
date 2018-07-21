package com.dimata.aiso.form.masterdata;

// package java
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import dimata
import com.dimata.util.*;
import com.dimata.util.lang.*;

// import qdep
import com.dimata.aiso.db.*;
//import com.dimata.aiso.entity.jurnal.PstJurnalDetail;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;

// import aiso
import com.dimata.aiso.entity.masterdata.*;
//import com.dimata.ij.iaiso.PstIjJournalDetail;
import java.sql.ResultSet;

public class CtrlPerkiraan extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_LEVEL_POSTED = 4;
    public static int RSLT_NOT_POSTABLE_LINK = 5;
    public static int RSLT_PARENT_ACCOUNT_NOT_FOUND =6;
    public static int RSLT_COA_IN_USED =7;
    public static String[][] resultText = {
        {
            "Berhasil",
            "Tidak dapat diproses",
            "No Perkiraan sudah ada",
            "Data tidak lengkap",
            "Level rekening tidak sesuai dengan acuannya",
            "Perkiraan yang bersifat header tidak dapat dipakai sebagai perkiraan acuan",
            "Perkiraan induk tidak ditemukan",
            "Perkiraan digunakan di jurnal"
        },
        {
            "Ok",
            "Can not process",
            "Account number exist",
            "Data incomplete",
            "Account's level not appropriate to its reference",
            "Cannot use a header account as a general account link",
            "Parent account not found",
            "CoA is in used in journal"
        }
    };
    private int start;
    private String msgString;
    private Perkiraan perkiraan;
    private PstPerkiraan pstPerkiraan;
    private FrmPerkiraan frmPerkiraan;
    int language = LANGUAGE_DEFAULT;

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public CtrlPerkiraan(HttpServletRequest request) {
        msgString = "";
        perkiraan = new Perkiraan();
        try {
            pstPerkiraan = new PstPerkiraan(0);
        } catch (Exception e) {
        }
        frmPerkiraan = new FrmPerkiraan(request, perkiraan);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmPerkiraan.addError(frmPerkiraan.FRM_NOPERKIRAAN, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public Perkiraan getPerkiraan() {
        return perkiraan;
    }

    public FrmPerkiraan getForm() {
        return frmPerkiraan;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    //public int action(int cmd, long IDPerkiraan, int start,  int recordToGet, String whereClause) 
    public int action(int cmd, long IDPerkiraan) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                frmPerkiraan.requestEntityObject(perkiraan);
                perkiraan.setOID(IDPerkiraan);

                if (frmPerkiraan.errorSize() > 0) {
                    msgString = resultText[language][RSLT_FORM_INCOMPLETE];
                    return RSLT_FORM_INCOMPLETE;
                }

                if (perkiraan.getGeneralAccountLink() < 0) {
                    msgString = resultText[language][RSLT_NOT_POSTABLE_LINK];
                    return RSLT_NOT_POSTABLE_LINK;
                }

                if (!PstPerkiraan.checkAccountlevelPostable(perkiraan.getIdParent(), perkiraan.getLevel())) {
                    msgString = resultText[language][RSLT_LEVEL_POSTED];
                    return RSLT_LEVEL_POSTED;
                }

                if(perkiraan!=null && perkiraan.getIdParent()!=0){
                    try{
                        Perkiraan coAParent = PstPerkiraan.fetchExc(perkiraan.getIdParent());
                        if(coAParent!=null && coAParent.getOID()!=0){
                            perkiraan.setLevel(coAParent.getLevel()+1); // Level cOA anak pasti satu tingkat dibawah parent
                        } 
                    }catch(Exception exc){
                        msgString = resultText[language][RSLT_PARENT_ACCOUNT_NOT_FOUND];
                        return RSLT_PARENT_ACCOUNT_NOT_FOUND;    
                    }
                }else{
                   if(perkiraan!=null){
                       perkiraan.setLevel(1);
                   } 
                }
                if (perkiraan.getOID() == 0) {
                    try {
                        long oid = pstPerkiraan.insertExc(this.perkiraan);
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
                        long oid = pstPerkiraan.updateExc(this.perkiraan);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.EDIT:
                if (IDPerkiraan != 0) {
                    try {
                        perkiraan = (Perkiraan) pstPerkiraan.fetchExc(IDPerkiraan);
                        System.out.println("CtrlPerkiraan perkiraan.getPostable() : " + perkiraan.getPostable());
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (IDPerkiraan != 0) {
                    try {
                        perkiraan = (Perkiraan) pstPerkiraan.fetchExc(IDPerkiraan);
                        int coAInGenJournal = countCoAUsedInGeneralJournal(perkiraan);
                        int coAInIJJournal = countCoAUsedInIJJournal(perkiraan);
                        if(coAInGenJournal>0 || coAInIJJournal>0 ){
                            msgString = FRMMessage.getErr(FRMMessage.MSG_IN_USED) +  resultText[language][RSLT_COA_IN_USED] +" : " +(coAInGenJournal>0 ? " General Journal =" + coAInGenJournal +" details " :"")
                                    + (coAInIJJournal>0 ? " Interactive Journal = " + coAInIJJournal  +" details " :"") ;
                            rsCode = RSLT_COA_IN_USED;
                            return rsCode;
                        }else{
                            msgString = FRMMessage.getErr(FRMMessage.MSG_ASKDEL);
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
                if (IDPerkiraan != 0) {
                    PstPerkiraan pstPerkiraan = new PstPerkiraan();
                    try {
                        Perkiraan perkiraanToDel = new Perkiraan();
                        int coAInGenJournal = countCoAUsedInGeneralJournal(perkiraanToDel);
                        int coAInIJJournal = countCoAUsedInIJJournal(perkiraanToDel);
                        if(coAInGenJournal>0 || coAInIJJournal>0 ){
                            msgString = FRMMessage.getErr(FRMMessage.MSG_IN_USED) + resultText[language][RSLT_COA_IN_USED] +" : " +(coAInGenJournal>0 ? " General Journal =" + coAInGenJournal +" details " :"")
                                    + (coAInIJJournal>0 ? " Interactive Journal = " + coAInIJJournal  +" details " :"") ;
                            rsCode = RSLT_COA_IN_USED;
                            return rsCode;
                        }
                        long oid = pstPerkiraan.deleteExc(IDPerkiraan);
                        this.start = 0;

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
    public static int COA_MAX_LEVEL = 10;
    
    public static int countCoAUsedInGeneralJournal(Perkiraan coA){
        int iUsed=0;
        if(coA==null || coA.getOID()==0){
            return 0;
        }
        try{
          //  iUsed= PstJurnalDetail.getCount(PstJurnalDetail.fieldNames[PstJurnalDetail.FLD_IDPERKIRAAN]+"="+coA.getOID());
        }catch(Exception exc){
            System.out.println(exc);
        }
        return iUsed;
    }

    public static int countCoAUsedInIJJournal(Perkiraan coA){
        int iUsed=0;
        if(coA==null || coA.getOID()==0){
            return 0;
        }
        try{
           // iUsed= PstIjJournalDetail.getCount(PstIjJournalDetail.fieldNames[PstIjJournalDetail.FLD_ACCOUNT_CHART_ID]+"="+coA.getOID());
        }catch(Exception exc){
            System.out.println(exc);
        }
        return iUsed;
    }
    
    public static String refreshIdParent(int accGroup) {
        String message = "";
        String where = "";
        String order = PstPerkiraan.fieldNames[PstPerkiraan.FLD_ACCOUNT_GROUP]
                + "," + PstPerkiraan.fieldNames[PstPerkiraan.FLD_NOPERKIRAAN];
        if (accGroup >= PstPerkiraan.ACC_GROUP_ALL) {
            where = PstPerkiraan.fieldNames[PstPerkiraan.FLD_ACCOUNT_GROUP] + "=" + accGroup;
        }

        Vector vCoAList = PstPerkiraan.list(0, 0, where, order);
        if (vCoAList != null && vCoAList.size() > 0) {
            Perkiraan prevCoA = new Perkiraan();
            //int prevLevel =0;
            Vector<Long> vParent = new Vector(); // parent OID, remember index 0 tidak dipakai, alias tetap 0
            for (int i = 0; i < COA_MAX_LEVEL; i++) {
                vParent.add(new Long(0));
            }
            int countRefreshed = 0;
            for (int idx = 0; idx < vCoAList.size(); idx++) {
                Perkiraan coA = (Perkiraan) vCoAList.get(idx);
                vParent.set(coA.getLevel(), coA.getOID());
                if (prevCoA.getLevel() >= coA.getLevel()) {
                    for (int i = coA.getLevel() + 1; i < COA_MAX_LEVEL; i++) {
                        vParent.set(i, new Long(0));// CoA dibawah level itu di reset
                    }
                }
                if (vParent.get(coA.getLevel() - 1) != 0 && prevCoA.getAccountGroup() == coA.getAccountGroup()
                        && coA.getIdParent() != vParent.get(coA.getLevel() - 1)) {
                    coA.setIdParent(vParent.get(coA.getLevel() - 1));
                    try {
                        PstPerkiraan.updateExc(coA);
                        countRefreshed++;
                        message = message + " " + countRefreshed + "\t" + coA.getNoPerkiraan()
                                + "\t" + coA.getNama() + "/" + coA.getAccountNameEnglish();
                    } catch (Exception exc) {
                        message = message + exc.toString();
                    }
                }
                prevCoA = coA;
            }
            if(message==null || message.length()<2){
                message = "No reference account are need to update.<br>";
            }
            message=message + updateCoAHeader();
        }
        
        return message;
    }
    
    
    public static String updateCoAHeader(){
        String sResult = listCoAWrongPostable();
        if(sResult==null || sResult.length()<2){
            return "All Headers Account are non postable, no update required";
        }
        String sql ="UPDATE " + PstPerkiraan.TBL_PERKIRAAN +
                " SET "+ PstPerkiraan.fieldNames[PstPerkiraan.FLD_POSTABLE] + "=0 WHERE  "+
                PstPerkiraan.fieldNames[PstPerkiraan.FLD_IDPERKIRAAN]+" IN ( "+ sResult +")";
        try{ 
            DBHandler.execUpdate(sql);
            sResult = "Update header account id : "+sResult;
        }catch(Exception exc){
            sResult ="ERROR"+exc.toString();
        }
                
        return sResult;
    }
        
        public static String listCoAWrongPostable(){
            //update account sebagai header jika ada account yang di merefensi ( account induk )
            String sql = "SELECT "+PstPerkiraan.fieldNames[PstPerkiraan.FLD_IDPERKIRAAN]+
                    " FROM " + PstPerkiraan.TBL_PERKIRAAN + " WHERE "
                    + PstPerkiraan.fieldNames[PstPerkiraan.FLD_POSTABLE] + "=1 AND "
                    + PstPerkiraan.fieldNames[PstPerkiraan.FLD_IDPERKIRAAN]
                    + " IN ( SELECT " + PstPerkiraan.fieldNames[PstPerkiraan.FLD_ID_PARENT] + " FROM " + PstPerkiraan.TBL_PERKIRAAN
                    + " WHERE " + PstPerkiraan.fieldNames[PstPerkiraan.FLD_ID_PARENT]
                    + " <>0 GROUP BY " + PstPerkiraan.fieldNames[PstPerkiraan.FLD_ID_PARENT] + " )";
              
            DBResultSet dbrs = null;
            String idCoA ="";
            try{
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
            int idx=0;
            while (rs.next()) {
               idCoA = idCoA +(idx>0?",":"")+ rs.getLong(PstPerkiraan.fieldNames[PstPerkiraan.FLD_IDPERKIRAAN]);
               idx++;
            }
            
            rs.close();
            } catch(Exception exc){
               System.out.println(exc.toString());
            }finally {
               DBResultSet.close(dbrs);
            }
        return idCoA;
    }
}
