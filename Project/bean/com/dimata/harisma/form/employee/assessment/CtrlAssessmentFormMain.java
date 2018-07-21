/* 
 * Ctrl Name  		:  CtrlAssessmentFormMain.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...]
 * *****************************************************************
 */
package com.dimata.harisma.form.employee.assessment;

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
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.form.locker.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMainDetail;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormMainDetail;
import com.dimata.harisma.session.admin.SessAppGroup;

public class CtrlAssessmentFormMain extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Form Main sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private AssessmentFormMain assessmentFormMain;
    private PstAssessmentFormMain pstAssessmentFormMain;
    private FrmAssessmentFormMain frmAssessmentFormMain;
    int language = LANGUAGE_DEFAULT;

    public CtrlAssessmentFormMain(HttpServletRequest request) {
        msgString = "";
        assessmentFormMain = new AssessmentFormMain();
        try {
            pstAssessmentFormMain = new PstAssessmentFormMain(0);
        } catch (Exception e) {;
        }

        frmAssessmentFormMain = new FrmAssessmentFormMain(request, assessmentFormMain);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmAssessmentFormMain.addError(frmAssessmentFormMain.FRM_FIELD_ASS_FORM_MAIN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public AssessmentFormMain getAssessmentFormMain() {
        return assessmentFormMain;
    }

    public FrmAssessmentFormMain getForm() {
        return frmAssessmentFormMain;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidAssessmentFormMain) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidAssessmentFormMain != 0) {
                    try {
                        assessmentFormMain = PstAssessmentFormMain.fetchExc(oidAssessmentFormMain);
                    } catch (Exception exc) {
                    }
                }

                frmAssessmentFormMain.requestEntityObject(assessmentFormMain);

                //System.out.println("frmAssessmentFormMain.errorSize() : "+frmAssessmentFormMain.errorSize());

                if (frmAssessmentFormMain.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    rsCode = RSLT_FORM_INCOMPLETE;
                    return RSLT_FORM_INCOMPLETE;
                }

                if (assessmentFormMain.getOID() == 0) {
                    try {
                        long oid = pstAssessmentFormMain.insertExc(this.assessmentFormMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        rsCode = RSLT_FORM_INCOMPLETE;
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        rsCode = RSLT_FORM_INCOMPLETE;
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                } else {
                    try {
                        long oid = pstAssessmentFormMain.updateExc(this.assessmentFormMain);
                    } catch (DBException dbexc) {
                        rsCode = RSLT_FORM_INCOMPLETE;
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                if (rsCode == RSLT_OK && assessmentFormMain != null && assessmentFormMain.getOID() != 0) {
                    Vector vGroupRank = frmAssessmentFormMain != null ? frmAssessmentFormMain.getGroupRank(assessmentFormMain.getOID()) : new Vector();

                    if (PstAssessmentFormMainDetail.setDetailGroupRankId(this.assessmentFormMain.getOID(), vGroupRank)) { 
                        msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
                    } else {
                        msgString = FRMMessage.getErr(FRMMessage.ERR_UNKNOWN);
                        excCode = 0;
                    }
                }
                Vector vDetailFormMain = PstAssessmentFormMainDetail.list(0, 0, PstAssessmentFormMainDetail.fieldNames[PstAssessmentFormMainDetail.FLD_ASS_FORM_MAIN_ID] + "=" + this.assessmentFormMain.getOID(), "");
                if (vDetailFormMain != null && vDetailFormMain.size() > 0) {
                    String sGroupRankId = "";
                    for (int idx = 0; idx < vDetailFormMain.size(); idx++) {
                        AssessmentFormMainDetail assessmentFormMainDetail = (AssessmentFormMainDetail) vDetailFormMain.get(idx);
                        sGroupRankId = sGroupRankId + assessmentFormMainDetail.getGroupRankId() + ",";
                    }
                    if (sGroupRankId != null && sGroupRankId.length() > 0 && assessmentFormMain != null) {
                        assessmentFormMain.setsGroupRankId(sGroupRankId.split(","));
                    }
                }
                break;

            case Command.EDIT:
                if (oidAssessmentFormMain != 0) {
                    try {
                        assessmentFormMain = PstAssessmentFormMain.fetchExc(oidAssessmentFormMain);
                        vDetailFormMain = PstAssessmentFormMainDetail.list(0, 0, PstAssessmentFormMainDetail.fieldNames[PstAssessmentFormMainDetail.FLD_ASS_FORM_MAIN_ID] + "=" + this.assessmentFormMain.getOID(), "");
                        if (vDetailFormMain != null && vDetailFormMain.size() > 0) {
                            String sGroupRankId = "";
                            for (int idx = 0; idx < vDetailFormMain.size(); idx++) {
                                AssessmentFormMainDetail assessmentFormMainDetail = (AssessmentFormMainDetail) vDetailFormMain.get(idx);
                                sGroupRankId = sGroupRankId + assessmentFormMainDetail.getGroupRankId() + ",";
                            }
                            if (sGroupRankId != null && sGroupRankId.length() > 0 && assessmentFormMain != null) {
                                assessmentFormMain.setsGroupRankId(sGroupRankId.split(","));
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

            case Command.ASK:
                if (oidAssessmentFormMain != 0) {
                    try {
                        //msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        assessmentFormMain = PstAssessmentFormMain.fetchExc(oidAssessmentFormMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidAssessmentFormMain != 0) {
                    try {
                        long oid = PstAssessmentFormMain.deleteExc(oidAssessmentFormMain);
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
