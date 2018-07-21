/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Satrya Ramayu 2014-06-12
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: CtrlCompany
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata.mappingkadiv;

/**
 *
 * @author Wiweka
 */
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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailPosition;
import com.dimata.harisma.entity.masterdata.mappingkadiv.MappingKadivDetail;
import com.dimata.harisma.entity.masterdata.mappingkadiv.MappingKadivMain;
import com.dimata.harisma.entity.masterdata.mappingkadiv.PstMappingKadivDetail;
import com.dimata.harisma.entity.masterdata.mappingkadiv.PstMappingKadivMain;
import java.util.Vector;

public class CtrMappingKadivMain extends Control implements I_Language{
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
    private MappingKadivMain mappingKadivMain;
    private PstMappingKadivMain pstMappingKadivMain;
    private FrmMappingKadivMain frmMappingKadivMain;
    
    private MappingKadivDetail mappingKadivDetail;
    private FrmMappingKadivDetail frmMappingKadivDetail;
    int language = LANGUAGE_FOREIGN;

    public CtrMappingKadivMain(HttpServletRequest request) {
        msgString = "";
        mappingKadivMain = new MappingKadivMain();
        
        mappingKadivDetail = new MappingKadivDetail();
        try {
            pstMappingKadivMain = new PstMappingKadivMain(0);
        } catch (Exception e) {
            ;
        }
        frmMappingKadivMain = new FrmMappingKadivMain(request, mappingKadivMain);
        
        frmMappingKadivDetail = new FrmMappingKadivDetail(request, mappingKadivDetail);
       
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmMappingKadivMain.addError(frmMappingKadivMain.FRM_FIELD_MAPPING_KADIV_MAIN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public MappingKadivMain getMappingKadivMain() {
        return mappingKadivMain;
    }
    
    public MappingKadivDetail getMappingKadivDetail() {
        return mappingKadivDetail;
    }
   

    public FrmMappingKadivMain getForm() {
        return frmMappingKadivMain;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmployee) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;
            case Command.GOTO:
                 frmMappingKadivMain.requestEntityObject(mappingKadivMain);
                 //mappingKadivMain.setOID(oidMappingKadivMain);
                 frmMappingKadivDetail.requestEntityObjectMultiple(mappingKadivDetail);
                 //mappingKadivDetail.setMappingkadivMainId(oidMappingKadivMain);
                break;
            case Command.SAVE:
                if (oidEmployee != 0) {
                    try {
                        //mappingKadivMain = PstMappingKadivMain.fetchExc(oidMappingKadivMain);
                         mappingKadivDetail =PstMappingKadivDetail.getMappingKadivJoinDetail(0, 0, "km."+PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_EMPLOYEE_ID]+"="+oidEmployee, "");
                         if(mappingKadivMain==null){
                             mappingKadivMain = new MappingKadivMain();
                         }
                         long oidMainKadivMapping =PstMappingKadivDetail.getOidMainMappingKadiv(0, 0, "km."+PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_EMPLOYEE_ID]+"="+oidEmployee, "");
                         mappingKadivMain.setEmployeeId(oidEmployee);
                         mappingKadivMain.setOID(oidMainKadivMapping);
                    } catch (Exception exc) {
                    }
                }

                frmMappingKadivMain.requestEntityObject(mappingKadivMain);
                frmMappingKadivDetail.requestEntityObjectMultiple(mappingKadivDetail);
                 
                if (frmMappingKadivMain.errorSize() > 0 || mappingKadivDetail.getLocationIds()==null || mappingKadivDetail.getLocationIds().length==0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (mappingKadivMain.getOID() == 0) {
                    try {
                        long oid = pstMappingKadivMain.insertExc(this.mappingKadivMain);

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
                        long oid = pstMappingKadivMain.updateExc(this.mappingKadivMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                FrmMappingKadivDetail frmMappingKadivDetail = new FrmMappingKadivDetail();
                if (rsCode == RSLT_OK && mappingKadivMain != null && mappingKadivMain.getOID() != 0) {
                    Vector vGroupDepartment = frmMappingKadivDetail.getGroupMappingKadiv(mappingKadivMain.getOID(),mappingKadivDetail);
                    //Vector vGroupPosition = frmLeaveConfigurationPosition.getGroupPositionId(mappingKadivMain.getOID()); 

                    if(frmMappingKadivDetail!=null  && frmMappingKadivDetail.getMessage()!=null && frmMappingKadivDetail.getMessage().length()>0){
                         msgString = FRMMessage.getMsg(FRMMessage.ERR_SAVED);
                        return RSLT_UNKNOWN_ERROR;
                    }
                    if (PstMappingKadivDetail.setDetailMappingKadiv(this.mappingKadivMain.getOID(), vGroupDepartment)) {
                        msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
                    } else {
                        msgString = FRMMessage.getErr(FRMMessage.ERR_UNKNOWN);
                        excCode = 0;
                    }
                }
                break;

            case Command.EDIT:
                if (oidEmployee != 0) {
                    try {
                        //mencari berdasarkan employee'nya dan lalu di set di detail
                        //mappingKadivMain = PstMappingKadivMain.getMappingKadiv(0, 1, , "");
                         //PstMappingKadivMain.fetchExc(oidMappingKadivMain);
                        mappingKadivDetail =PstMappingKadivDetail.getMappingKadivJoinDetail(0, 0, "km."+PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_EMPLOYEE_ID]+"="+oidEmployee, "");
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
