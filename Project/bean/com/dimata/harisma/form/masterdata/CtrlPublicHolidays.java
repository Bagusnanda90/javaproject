/**
 * User: wardana Date: Apr 8, 2004 Time: 9:36:38 AM Version: 1.0
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.qdep.db.DBException;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.harisma.entity.masterdata.PublicHolidays;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class CtrlPublicHolidays extends Control implements I_Language {

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
    private PublicHolidays objPublicHolidays;
    private PstPublicHolidays objPstPublicHolidays;
    private FrmPublicHolidays objFrmPublicHolidays;
    int language = LANGUAGE_DEFAULT;

    public CtrlPublicHolidays(HttpServletRequest request) {
        msgString = "";
        objPublicHolidays = new PublicHolidays();
        try {
            objPstPublicHolidays = new PstPublicHolidays(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objFrmPublicHolidays = new FrmPublicHolidays(request, objPublicHolidays);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.objFrmPublicHolidays.addError(FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public PublicHolidays getPublicHolidays() {
        return objPublicHolidays;
    }

    public FrmPublicHolidays getForm() {
        return objFrmPublicHolidays;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int iCmd, long lOidHoliday, String currYear, String fromYear) {
        int iRsCode = RSLT_OK;
        switch (iCmd) {
            case Command.ADD:
                break;
            case Command.SAVE:
                iRsCode = actionSave(lOidHoliday);
                break;
            case Command.EDIT:
                iRsCode = actionEditOrAsk(lOidHoliday);
                break;
            case Command.ASK:
                iRsCode = actionEditOrAsk(lOidHoliday);
                break;
            case Command.DELETE:
                iRsCode = actionDelete(lOidHoliday);
                break;
            case Command.POST:
                iRsCode = actionSaveGenerate(lOidHoliday, currYear, fromYear);
                break;
            case Command.ASSIGN:
                iRsCode = actionGenerateDP(currYear);
                break;
            default:
                break;
        }

        return iRsCode;
    }

    private int actionSave(long lOidHoliday) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        if (lOidHoliday != 0) {
            try {
                objPublicHolidays = PstPublicHolidays.fetchExc(lOidHoliday);
            } catch (DBException dbe) {
                dbe.printStackTrace();
            }
        }
        objFrmPublicHolidays.requestEntityObject(objPublicHolidays);

        if (objFrmPublicHolidays.errorSize() > 0) {
            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
            return RSLT_FORM_INCOMPLETE;
        }

        if (objPublicHolidays.getOID() == 0) {
            try {
                PstPublicHolidays.insertExc(objPublicHolidays);
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        } else {
            try {
                PstPublicHolidays.updateExc(objPublicHolidays);
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        }

        return excCode;
    }

    //add by priska untuk generate 20151021
    private int actionSaveGenerate(long lOidHoliday, String stCurrYear, String fromYear) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        Vector listHolidayFrom = new Vector();
        String whereClauseFrom = "Year(" + PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE] + ") = '" + fromYear + "'";
        String orderClause = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE];
        listHolidayFrom = PstPublicHolidays.list(0, 0, whereClauseFrom, orderClause);
        int intStatusCurr = ((Integer) Integer.valueOf(stCurrYear)) - 1900;
        for (int iFrom = 0; iFrom < listHolidayFrom.size(); iFrom++) {
            PublicHolidays publicHolidays = (PublicHolidays) listHolidayFrom.get(iFrom);
            try {
                Date DtHolidayDate = new Date();
                DtHolidayDate.setDate(publicHolidays.getDtHolidayDate().getDate());
                DtHolidayDate.setMonth(publicHolidays.getDtHolidayDate().getMonth());
                DtHolidayDate.setYear(intStatusCurr);
                publicHolidays.setDtHolidayDate(DtHolidayDate);

                Date DtHolidayDateTo = new Date();
                DtHolidayDateTo.setDate(publicHolidays.getDtHolidayDateTo().getDate());
                DtHolidayDateTo.setMonth(publicHolidays.getDtHolidayDateTo().getMonth());
                DtHolidayDateTo.setYear(intStatusCurr);
                publicHolidays.setDtHolidayDateTo(DtHolidayDateTo);

                long oid = PstPublicHolidays.insertExc(publicHolidays);
            } catch (Exception e) {
            }

        }



        return excCode;
    }

    //add by priska untuk generate 20160301
    private int actionGenerateDP(String tahun) {
        msgString = "";
        
        String ClientName = "";
        try {
            ClientName = String.valueOf(PstSystemProperty.getValueByName("CLIENT_NAME"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }
        
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        
        Hashtable vctSchIDOff = PstScheduleSymbol.getHashScheduleId(PstScheduleCategory.CATEGORY_OFF); 
        Vector listHolidayFrom = new Vector();
        String whereClauseFrom = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE] + " LIKE '%" + tahun + "%'";
        String orderClause = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE];
        listHolidayFrom = PstPublicHolidays.list(0, 0, whereClauseFrom, orderClause);
        //int intStatusCurr = ((Integer) Integer.valueOf(stCurrYear)) - 1900;
        
        Vector vlistChecked = new Vector();
        objFrmPublicHolidays.requestMultipleEntityObjectGenerate(listHolidayFrom);
        vlistChecked = objFrmPublicHolidays.getVList();
        
        for (int iFrom = 0; iFrom < vlistChecked.size(); iFrom++) {
            PublicHolidays publicHolidays = (PublicHolidays) vlistChecked.get(iFrom);
            
            String whereEmployee = "";
            Vector employeeList = new Vector();
            if ((publicHolidays.getiHolidaySts() == 0) || (publicHolidays.getiHolidaySts() == 1) || (publicHolidays.getiHolidaySts() == 2)) {
                employeeList = PstEmployee.list(0, 0, "", "");
            } else {
                whereEmployee = PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID] + " = " + publicHolidays.getiHolidaySts() + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;
                employeeList = PstEmployee.list(0, 0, whereEmployee, "");
            }
            for (int xx = 0; xx < employeeList.size(); xx++) {
                Employee employee = (Employee) employeeList.get(xx);
                
                EmpCategory empCategory = new EmpCategory();
                try{
                    empCategory = PstEmpCategory.fetchExc(employee.getEmpCategoryId());
                } catch (Exception e){} 
                if (empCategory.getEntitleDP() == 1){
                
                    
                    try {
                    Date incDate = (Date) publicHolidays.getDtHolidayDate().clone();
                    Date toDate = (Date) publicHolidays.getDtHolidayDateTo().clone();
                    toDate.setDate(toDate.getDate() + 1);
                    
                    do {
                        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                        
                        try{
                            scheduleSymbol = PstEmpSchedule.getDailySchedule(incDate, employee.getOID());
                        }catch (Exception e){
                        }
                        
                        Date expDate = null;
                        expDate = (Date) incDate.clone();
                        try {
                            if (ClientName.equals("RAMA_RESTO")){
                                expDate.setMonth(expDate.getMonth() + 4);
                                expDate.setDate(1);
                                expDate.setDate(expDate.getDate() - 1);
                            } else {
                                expDate.setMonth(expDate.getMonth() + 1);
                            }
                        } catch (Exception e) {
                        }


                        DpStockManagement dpStockManagement = new DpStockManagement();
                        dpStockManagement.setQtyResidue(1);

                        dpStockManagement.setiDpQty(1);
                        dpStockManagement.setDtOwningDate(incDate);
                        dpStockManagement.setDtExpiredDate(expDate);
                        dpStockManagement.setiDpStatus(0);
                        dpStockManagement.setEmployeeId(employee.getOID());
                        dpStockManagement.setStNote(" DP Generate " + incDate);
                        DpStockManagement dpStockManagementX = new DpStockManagement();
                        try {
                            dpStockManagementX = PstDpStockManagement.getDpStockManagement(incDate, employee.getOID());
                        } catch (Exception e) {
                        }
                        
                        if (dpStockManagementX.getOID() == 0 && scheduleSymbol.getOID() != 0  && vctSchIDOff != null && vctSchIDOff.size() > 0 && !vctSchIDOff.containsKey(""+scheduleSymbol.getOID())) {
                            try {
                                long oid = PstDpStockManagement.insertExc(dpStockManagement);
                            } catch (Exception x) {
                            }

                        }
                        incDate.setDate(incDate.getDate() + 1);
                    } while (incDate.before(toDate));


                    // long oid = PstPublicHolidays.insertExc(publicHolidays);
                } catch (Exception e) {
                }
                    
                    
                }
                
                

            }

            
        
        }


        msgString = "Generate Dp Succes";
        return excCode;
    }

    public int actionEditOrAsk(long lOidHoliday) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        if (lOidHoliday != 0) {
            try {
                objPublicHolidays = PstPublicHolidays.fetchExc(lOidHoliday);
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        }
        return excCode;
    }

    public int actionDelete(long lOidHoliday) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        if (lOidHoliday != 0) {
            try {
                long oid = PstPublicHolidays.deleteExc(lOidHoliday);
                if (oid != 0) {
                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                    excCode = RSLT_OK;
                } else {
                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                    excCode = RSLT_FORM_INCOMPLETE;
                }
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        }
        return excCode;
    }
}
