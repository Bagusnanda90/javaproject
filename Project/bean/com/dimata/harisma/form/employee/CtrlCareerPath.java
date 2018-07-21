/* 
 * Ctrl Name  		:  CtrlCareerPath.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.employee;

/* java package */
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* dimata package */
import com.dimata.util.*;
import com.dimata.gui.jsp.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.masterdata.location.Location;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.system.entity.PstSystemProperty;
//import javax.mail.Session;
//import sun.security.action.GetLongAction;

public class CtrlCareerPath extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_FRM_DATE_IN_RANGE = 4;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", "Tanggal yang diinputkan sudah ada"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private String msgErrorDep;
    private CareerPath careerPath;
    private Employee employee;
    private PstEmployee pstEmployee;
    private PstCareerPath pstCareerPath;
    private FrmCareerPath frmCareerPath;
    int language = LANGUAGE_DEFAULT;

    public CtrlCareerPath(HttpServletRequest request) {
        msgString = "";
        careerPath = new CareerPath();
        try {
            pstCareerPath = new PstCareerPath(0);
        } catch (Exception e) {;
        }
        frmCareerPath = new FrmCareerPath(request, careerPath);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmCareerPath.addError(frmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public CareerPath getCareerPath() {
        return careerPath;
    }

    public FrmCareerPath getForm() {
        return frmCareerPath;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidCareerPath, long oidEmployee, HttpServletRequest request) {
        return action(cmd, oidCareerPath, oidEmployee, request, "", 0, "", ""); 
    }
    
    public int action(int cmd, long oidCareerPath, long oidEmployee, HttpServletRequest request, String loginName, long userId, String oidDeleteAll) {
        return action(cmd, oidCareerPath, oidEmployee, request, loginName, userId, oidDeleteAll, ""); 
    }
    
    public int action(int cmd, long oidCareerPath, long oidEmployee, HttpServletRequest request, String loginName, long userId, String oidDeleteAll, String object) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        int sysLog = Integer.parseInt(String.valueOf(PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
        String logDetail = "";
        String logPrev = "";
        String logCurr = "";
        Date nowDate = new Date();
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                CareerPath prevCareerPath = null;
                if (oidCareerPath != 0) {
                    try {
                        careerPath = PstCareerPath.fetchExc(oidCareerPath);
                        if (sysLog == 1) {
                            prevCareerPath = PstCareerPath.fetchExc(oidCareerPath);

                        }
                    } catch (Exception exc) {
                    }
                }

                careerPath.setOID(oidCareerPath);

                frmCareerPath.requestEntityObject(careerPath);

                careerPath.setEmployeeId(oidEmployee);
                //start dedy-20151123 ambil jumlah data berdasarkan tanggal work
                String str_dt_WorkFrom = Formater.formatDate(careerPath.getWorkFrom(), "yyyy-MM-dd");
                String str_dt_WorkTo = Formater.formatDate(careerPath.getWorkTo(), "yyyy-MM-dd");

                int sumWork = PstCareerPath.getCount("work_from='" + str_dt_WorkFrom + "' && work_to='" + str_dt_WorkTo + "'");
                //end

                //    careerPath.setLocationId(frm);
                //   HttpSession session=request.getSession(false);  
                //   String sesloc = (String)session.getValue("sesloc");  

                //   careerPath.setLocationId(Long.valueOf(sesloc));

                //department
                Vector vector = PstDepartment.list(0, 1, PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + careerPath.getDepartmentId(), ""); //update by satrya 2013-12-19 PstDepartment.list(0,1,PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = "+careerPath.getDepartmentId(),"");
                if (vector != null && vector.size() > 0) {
                    Department dept = (Department) vector.get(0);
                    careerPath.setDepartment(dept.getDepartment());
                }
                //company
                vector = PstCompany.list(0, 1, PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + " = " + careerPath.getCompanyId(), "");
                if (vector != null && vector.size() > 0) {
                    Company comp = (Company) vector.get(0);
                    careerPath.setCompany(comp.getCompany());
                }
                //section
                vector = PstSection.list(0, 1, PstSection.fieldNames[PstSection.FLD_SECTION_ID] + " = " + careerPath.getSectionId(), "");
                if (vector != null && vector.size() > 0) {
                    Section section = (Section) vector.get(0);
                    careerPath.setSection(section.getSection());
                }

                //position
                vector = PstPosition.list(0, 1, PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = " + careerPath.getPositionId(), "");
                if (vector != null && vector.size() > 0) {
                    Position position = (Position) vector.get(0);
                    careerPath.setPosition(position.getPosition());
                }
                //division
                vector = PstDivision.list(0, 1, PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " = " + careerPath.getDivisionId(), "");
                if (vector != null && vector.size() > 0) {
                    Division division = (Division) vector.get(0);
                    careerPath.setDivision(division.getDivision());
                }
                //level
                vector = PstLevel.list(0, 1, PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " = " + careerPath.getLevelId(), "");
                if (vector != null && vector.size() > 0) {
                    Level level = (Level) vector.get(0);
                    careerPath.setLevel(level.getLevel());
                }
                //emp_category
                vector = PstEmpCategory.list(0, 1, PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = " + careerPath.getEmpCategoryId(), "");
                if (vector != null && vector.size() > 0) {
                    EmpCategory empCategory = (EmpCategory) vector.get(0);
                    careerPath.setEmpCategory(empCategory.getEmpCategory());
                }



                vector = PstEmployee.list(0, 1, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = " + careerPath.getEmployeeId(), "");
                if (vector != null && vector.size() > 0) {
                    Employee emplocation = (Employee) vector.get(0);
                    careerPath.setLocationId(emplocation.getLocationId());
                }

                //Location
                vector = PstLocation.list(0, 1, PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + " = " + careerPath.getLocationId(), "");
                if (vector != null && vector.size() > 0) {
                    Location location = (Location) vector.get(0);
                    careerPath.setLocation(location.getName());
                }
                /*
                if (frmCareerPath.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }*/
                //update by devin 2014-02-05
                /* Update by Hendra Putu - 2016-01-19 */
                //if (!(PstSystemProperty.getValueByName("CLIENT_NAME").equals("BPD"))) { sudah di fixkan..  priska 20160331
                    Vector data = new Vector();
                    if (careerPath.getEmployeeId() != 0) {
                        long oidEmp = careerPath.getEmployeeId();
                        
                        //data = PstCareerPath.dateCareerPath(oidEmp);
                        String whereClauseX = "";
                        if ((careerPath.getHistoryType() == 0) || (careerPath.getHistoryType() == 1)){
                          whereClauseX = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee + " AND (" + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 0 + " OR " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 1 + " )";
                        } else {
                          whereClauseX = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee + " AND (" + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 2 + " OR " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 3 + " )";
                        }
                        String orderClauseX = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];
                        data = PstCareerPath.list(0, 0, whereClauseX, orderClauseX);
                        
                        //cari contract from
                        
                        if (data != null && data.size() > 0) {
                            for (int i = 0; i < data.size(); i++) {
                                CareerPath care = (CareerPath) data.get(i);
                                if (careerPath != null && care != null && care.getWorkFrom() != null && care.getWorkTo() != null && careerPath.getWorkFrom() != null && careerPath.getWorkTo() != null) {
                                    Date newStartDate = care.getWorkFrom();
                                    Date newEndDate = care.getWorkTo();
                                    Date startDate = careerPath.getWorkFrom();
                                    Date endDate = careerPath.getWorkTo();
                                    String sTanggalTo = Formater.formatDate(newStartDate, "dd-MM-yyyy");
                                    String sTanggalFrom = Formater.formatDate(newEndDate, "dd-MM-yyyy");
                                    String Error = "" + sTanggalTo + " TO " + sTanggalFrom;
                                    if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && newStartDate.after(careerPath.getWorkFrom()) && newStartDate.before(careerPath.getWorkTo())) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && endDate.after(newStartDate) && endDate.before(newEndDate)) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } /*else if (newEndDate.equals(endDate)) {
                                     msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                     return RSLT_FRM_DATE_IN_RANGE;
                                     }*/ else {
                                        //maka dia tidak overlap
                                    }
                                }
                            }
                            
                           // careerPath.setContractFrom(PstCareerPath.getNewContractFromOrCommencingDate(careerPath.getEmployeeId()));
                            
                            
                            
                        }
                    }
                //}

                if (careerPath.getOID() == 0) {
                    try {
                        if (sumWork != 0) {
                            msgString = resultText[language][RSLT_UNKNOWN_ERROR] + " " + resultText[language][RSLT_FRM_DATE_IN_RANGE];
                            rsCode = RSLT_FRM_DATE_IN_RANGE;
                        } else {
                            long oid = pstCareerPath.insertExc(this.careerPath);
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                            EmpDocList empDocList = new EmpDocList();
                            empDocList.setEmp_doc_id(this.careerPath.getEmpDocId());
                            empDocList.setEmployee_id(this.careerPath.getEmployeeId());
                            empDocList.setEmp_reprimand(0);
                            empDocList.setObject_name(object);
                            empDocList.setEmp_career(oid);
                            empDocList.setEmp_award_id(0);
                            empDocList.setEmp_training(0);
                            long oidEmpDocList = PstEmpDocList.insertExc(empDocList);
                            if (sysLog == 1) {
                                String className = careerPath.getClass().getName();
                                LogSysHistory logSysHistory = new LogSysHistory();
                                String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;
                                logCurr += "COMPANY_ID=["+careerPath.getCompanyId()+"];";
                                logCurr += "DIVISION_ID=["+careerPath.getDivisionId()+"];";
                                logCurr += "DEPARTMENT_ID=["+careerPath.getDepartmentId()+"];";
                                logCurr += "SECTION_ID=["+careerPath.getSectionId()+"];";
                                logCurr += "POSITION_ID=["+careerPath.getPositionId()+"];";
                                logCurr += "LEVEL_ID=["+careerPath.getLevelId()+"];";
                                logCurr += "EMP_CATEGORY_ID=["+careerPath.getEmpCategoryId()+"];";
                                logCurr += "WORK_FROM='"+careerPath.getWorkFrom()+"';";
                                logCurr += "WORK_TO='"+careerPath.getWorkTo()+"';";
                                logCurr += "SALARY=["+careerPath.getSalary()+"];";
                                logCurr += "DESCRIPTION='"+careerPath.getDescription()+"';";
                                logCurr += "PROVIDER_ID=["+careerPath.getProviderID()+"];";
                                logCurr += "HISTORY_TYPE=["+careerPath.getHistoryType()+"];";
                                logCurr += "HISTORY_GROUP=["+careerPath.getHistoryGroup()+"];";
                                logCurr += "NOMOR_SK='"+careerPath.getNomorSk()+"';";
                                logCurr += "TANGGAL_SK='"+careerPath.getTanggalSk()+"';";
                                logCurr += "GRADE_LEVEL_ID=["+careerPath.getGradeLevelId()+"];";
                                logSysHistory.setLogDocumentId(0);
                                logSysHistory.setLogUserId(userId);
                                logSysHistory.setApproverId(userId);
                                logSysHistory.setApproveDate(nowDate);
                                logSysHistory.setLogLoginName(loginName);
                                logSysHistory.setLogDocumentNumber("");
                                logSysHistory.setLogDocumentType(className); //entity
                                logSysHistory.setLogUserAction("ADD"); // command
                                logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                                logSysHistory.setLogUpdateDate(nowDate);
                                logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                                logSysHistory.setLogDetail("-"); // apa yang dirubah
                                logSysHistory.setLogModule("Career Path");
                                logSysHistory.setLogPrev("-");
                                logSysHistory.setLogCurr(logCurr);
                                logSysHistory.setLogStatus(0);
                                logSysHistory.setLogEditedUserId(careerPath.getEmployeeId());

                                PstLogSysHistory.insertExc(logSysHistory);
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
                        long oid = pstCareerPath.updateExc(this.careerPath);
                        EmpDocList empDocList = new EmpDocList();
                        empDocList.setEmp_doc_id(this.careerPath.getEmpDocId());
                        empDocList.setEmployee_id(this.careerPath.getEmployeeId());
                        empDocList.setEmp_reprimand(0);
                        empDocList.setObject_name(object);
                        empDocList.setEmp_career(oid);
                        empDocList.setEmp_award_id(0);
                        empDocList.setEmp_training(0);
                        long oidEmpDocList = PstEmpDocList.updateExc(empDocList);
                        // logHistory
                        if (sysLog == 1) {
                            careerPath = PstCareerPath.fetchExc(oid);

                            if (careerPath != null && prevCareerPath != null) {
                                if (careerPath.getCompanyId() != prevCareerPath.getCompanyId()) {
                                    Company com = PstCompany.fetchExc(careerPath.getCompanyId());
                                    Company prevCom = PstCompany.fetchExc(prevCareerPath.getCompanyId());
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID]+";";
                                    logPrev += "["+prevCareerPath.getCompanyId()+"]"+prevCom.getCompany()+";";
                                    logCurr += "["+careerPath.getCompanyId()+"]"+com.getCompany()+";";
                                }
                                if (careerPath.getDivisionId() != prevCareerPath.getDivisionId()) {
                                    Division div = PstDivision.fetchExc(careerPath.getDivisionId());
                                    Division prevDiv = PstDivision.fetchExc(prevCareerPath.getDivisionId());
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]+";";
                                    logPrev += "["+prevCareerPath.getDivisionId()+"]"+prevDiv.getDivision()+";";
                                    logCurr += "["+careerPath.getDivisionId()+"]"+div.getDivision()+";";
                                }
                                if (careerPath.getDepartmentId() != prevCareerPath.getDepartmentId()) {
                                    Department dept = PstDepartment.fetchExc(careerPath.getDepartmentId());
                                    Department prevDept = PstDepartment.fetchExc(prevCareerPath.getDepartmentId());
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID]+";";
                                    logPrev += "["+prevCareerPath.getDepartmentId()+"]"+prevDept.getDepartment()+";";
                                    logCurr += "["+careerPath.getDepartmentId()+"]"+dept.getDepartment()+";";
                                }
                                if (careerPath.getSectionId() != prevCareerPath.getSectionId()) {
                                    Section section = PstSection.fetchExc(careerPath.getSectionId());
                                    Section prevSection = PstSection.fetchExc(prevCareerPath.getSectionId());
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID]+";";
                                    logPrev += "["+prevCareerPath.getSectionId()+"]"+prevSection.getSection()+";";
                                    logCurr += "["+careerPath.getSectionId()+"]"+section.getSection()+";";
                                }
                                if (careerPath.getPositionId() != prevCareerPath.getPositionId()) {
                                    Position position = PstPosition.fetchExc(careerPath.getPositionId());
                                    Position prevPosition = PstPosition.fetchExc(prevCareerPath.getPositionId());
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]+";";
                                    logPrev += "["+prevCareerPath.getPositionId()+"]"+prevPosition.getPosition()+";";
                                    logCurr += "["+careerPath.getPositionId()+"]"+position.getPosition()+";";
                                }
                                if (careerPath.getLevelId() != prevCareerPath.getLevelId()) {
                                    Level level = PstLevel.fetchExc(careerPath.getLevelId());
                                    Level prevLevel = PstLevel.fetchExc(prevCareerPath.getLevelId());
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]+";";
                                    logPrev += "["+prevCareerPath.getLevelId()+"]"+prevLevel.getLevel()+";";
                                    logCurr += "["+careerPath.getLevelId()+"]"+level.getLevel()+";";
                                }
                                if (careerPath.getEmpCategoryId() != prevCareerPath.getEmpCategoryId()) {
                                    EmpCategory empCat = PstEmpCategory.fetchExc(careerPath.getEmpCategoryId());
                                    EmpCategory prevEmpCat = PstEmpCategory.fetchExc(prevCareerPath.getEmpCategoryId());
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID]+";";
                                    logPrev += "["+prevCareerPath.getEmpCategoryId()+"]"+prevEmpCat.getEmpCategory()+";";
                                    logCurr += "["+careerPath.getEmpCategoryId()+"]"+empCat.getEmpCategory()+";";
                                }
                                if (careerPath.getWorkFrom() != null){
                                    if (!careerPath.getWorkFrom().equals(prevCareerPath.getWorkFrom())){
                                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]+";";
                                        logPrev += "'"+prevCareerPath.getWorkFrom()+"';";
                                        logCurr += "'"+careerPath.getWorkFrom()+"';";
                                    }
                                }
                                if (careerPath.getWorkTo() != null){
                                    if (!careerPath.getWorkTo().equals(prevCareerPath.getWorkTo())){
                                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]+";";
                                        logPrev += "'"+prevCareerPath.getWorkTo()+"';";
                                        logCurr += "'"+careerPath.getWorkTo()+"';";
                                    }
                                }
                                if (careerPath.getSalary() != prevCareerPath.getSalary()) {
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_SALARY]+";";
                                    logPrev += ""+prevCareerPath.getSalary()+";";
                                    logCurr += ""+careerPath.getSalary()+";";
                                }
                                if (!careerPath.getDescription().equals(prevCareerPath.getDescription())) {
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_DESCRIPTION]+";";
                                    logPrev += "'"+prevCareerPath.getDescription()+"';";
                                    logCurr += "'"+careerPath.getDescription()+"';";
                                }
                                if (careerPath.getProviderID() != prevCareerPath.getProviderID()) {
                                    ContactList waContact = PstContactList.fetchExc(careerPath.getProviderID());
                                    ContactList prevWaContact = PstContactList.fetchExc(prevCareerPath.getProviderID());
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_PROVIDER_ID]+";";
                                    logPrev += "["+prevCareerPath.getProviderID()+"]"+prevWaContact.getCompName()+";";
                                    logCurr += "["+careerPath.getProviderID()+"]"+waContact.getCompName()+";";
                                }
                                if (careerPath.getHistoryType() != prevCareerPath.getHistoryType()) {
                                    String type = "";
                                    String prevType = "";
                                    if (careerPath.getHistoryType() == 0) {
                                        type = "Career";
                                    } 
                                    else if (careerPath.getHistoryType() == 1) {
                                        type = "Pejabat Sementara";
                                    }
                                    else if (careerPath.getHistoryType() == 2) {
                                        type = "Pelaksana Tugas";
                                    }
                                    else {
                                        type = "Detasir";
                                    }
                                    if (prevCareerPath.getHistoryType() == 0) {
                                        prevType = "Career";
                                    } 
                                    else if (prevCareerPath.getHistoryType() == 1) {
                                        prevType = "Pejabat Sementara";
                                    }
                                    else if (prevCareerPath.getHistoryType() == 2) {
                                        prevType = "Pelaksana Tugas";
                                    }
                                    else {
                                        prevType = "Detasir";
                                    }
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE]+";";
                                    logPrev += "["+prevCareerPath.getHistoryType()+"]"+prevType+";";
                                    logCurr += "["+careerPath.getHistoryType()+"]"+type+";";
                                }
                                if (careerPath.getHistoryGroup() != prevCareerPath.getHistoryGroup()){
                                    String group = "";
                                    String prevGroup = "";
                                    if (careerPath.getHistoryGroup() == 0){
                                        group = "Riwayat Jabatan";
                                    }
                                    else if (careerPath.getHistoryGroup() == 1){
                                        group = "Riwayat Grade";
                                    }
                                    else{
                                        group = "Riwayat Jabatan dan Grade";
                                    }
                                    
                                    if (prevCareerPath.getHistoryGroup() == 0){
                                        prevGroup = "Riwayat Jabatan";
                                    }
                                    else if (prevCareerPath.getHistoryGroup() == 1){
                                        prevGroup = "Riwayat Grade";
                                    }
                                    else{
                                        prevGroup = "Riwayat Jabatan dan Grade";
                                    }
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_GROUP]+";";
                                    logPrev += "["+prevCareerPath.getHistoryGroup()+"]"+prevGroup+";";
                                    logCurr += "["+careerPath.getHistoryGroup()+"]"+group+";";
                                }
                                if (!careerPath.getNomorSk().equals(prevCareerPath.getNomorSk()) && careerPath.getNomorSk() != null) {
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_NOMOR_SK]+";";
                                    logPrev += "'"+prevCareerPath.getNomorSk()+"';";
                                    logCurr += "'"+careerPath.getNomorSk()+"';";
                                }
                                if (careerPath.getTanggalSk() != null){
                                    if (!careerPath.getTanggalSk().equals(prevCareerPath.getTanggalSk())){
                                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_TANGGAL_SK]+";";
                                        logPrev += "'"+prevCareerPath.getTanggalSk()+"';";
                                        logCurr += "'"+careerPath.getTanggalSk()+"';";
                                    }
                                }
                                if(careerPath.getGradeLevelId() != prevCareerPath.getGradeLevelId()){
                                    GradeLevel grade = PstGradeLevel.fetchExc(careerPath.getGradeLevelId());
                                    GradeLevel prevGrade = PstGradeLevel.fetchExc(prevCareerPath.getGradeLevelId());
                                    logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_GRADE_LEVEL_ID]+";";
                                    logPrev += ""+prevCareerPath.getGradeLevelId()+"]"+prevGrade.getCodeLevel()+";";
                                    logCurr += ""+careerPath.getGradeLevelId()+"]"+grade.getCodeLevel()+";";
                                }

                                String className = careerPath.getClass().getName();

                                LogSysHistory logSysHistory = new LogSysHistory();

                                String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;

                                logSysHistory.setLogDocumentId(0);
                                logSysHistory.setLogUserId(userId);
                                logSysHistory.setApproverId(userId);
                                logSysHistory.setApproveDate(nowDate);
                                logSysHistory.setLogLoginName(loginName);
                                logSysHistory.setLogDocumentNumber("");
                                logSysHistory.setLogDocumentType(className); //entity
                                logSysHistory.setLogUserAction("EDIT"); // command
                                logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                                logSysHistory.setLogUpdateDate(nowDate);
                                logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                                logSysHistory.setLogDetail(logDetail); // apa yang dirubah
                                logSysHistory.setLogModule("Career Path");
                                logSysHistory.setLogPrev(logPrev);
                                logSysHistory.setLogCurr(logCurr);
                                logSysHistory.setLogStatus(0);
                                logSysHistory.setLogEditedUserId(careerPath.getEmployeeId());

                                PstLogSysHistory.insertExc(logSysHistory);
                            }
                        }

                        msgString = FRMMessage.getMessage(FRMMessage.MSG_UPDATED);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidCareerPath != 0) {
                    try {
                        careerPath = PstCareerPath.fetchExc(oidCareerPath);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidCareerPath != 0) {
                    try {
                        careerPath = PstCareerPath.fetchExc(oidCareerPath);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidCareerPath != 0) {
                    try {
                        
                        careerPath = PstCareerPath.fetchExc(oidCareerPath);
                        
                        long oid = PstCareerPath.deleteExc(oidCareerPath);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                        
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_SALARY]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_DESCRIPTION]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_LOCATION_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_LOCATION]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_NOTE]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_PROVIDER_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_NOMOR_SK]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_TANGGAL_SK]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_DOC_ID]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_GROUP]+";";
                        logDetail += PstCareerPath.fieldNames[PstCareerPath.FLD_GRADE_LEVEL_ID]+";";
                        
                       
                        
                            logPrev  = careerPath.getOID()+";";
                        if(careerPath.getEmployeeId() != 0){
                            Employee emp = new Employee();
                            try{
                                emp = PstEmployee.fetchExc(careerPath.getEmployeeId());
                            }catch (Exception e){}
                            logPrev += "["+careerPath.getEmployeeId()+"]"+emp.getFullName()+";";
                        }
                        else{
                            logPrev += careerPath.getEmployeeId()+";";
                        }
                        if(careerPath.getCompanyId() != 0){
                            Company comp = new Company(); 
                            try{
                                comp = PstCompany.fetchExc(careerPath.getCompanyId());
                            }catch(Exception e){}
                            logPrev += "["+careerPath.getCompanyId()+"]"+comp.getCompany()+";";                            
                        }
                        else{
                            logPrev += careerPath.getCompanyId()+";";
                        }

                            logPrev += "'"+careerPath.getCompany()+"';";
                        if(careerPath.getDepartmentId() != 0){
                            Department dep = new Department();
                            try{
                                dep = PstDepartment.fetchExc(careerPath.getDepartmentId());
                            }catch(Exception e){}
                            
                            logPrev += "["+careerPath.getDepartmentId()+"]"+dep.getDepartment()+";";
                        }
                        else{
                            logPrev += careerPath.getDepartmentId()+";";
                        }    
                            logPrev += "'"+careerPath.getDepartment()+"';";
                        if(careerPath.getPositionId() != 0){
                            try{
                                
                            }catch(Exception e){}
                            Position pos = PstPosition.fetchExc(careerPath.getPositionId());
                            logPrev += "["+careerPath.getPositionId()+"]"+pos.getPosition()+";";
                        }
                        else{
                            logPrev += careerPath.getPositionId()+";";
                        }
                            logPrev += "'"+careerPath.getPosition()+"';";
                        if(careerPath.getSectionId() != 0){
                            Section sec = PstSection.fetchExc(careerPath.getSectionId());
                            logPrev += "["+careerPath.getSectionId()+"]"+sec.getSection()+";";
                        }
                        else{
                            logPrev += careerPath.getSectionId()+";";
                        }
                            logPrev += "'"+careerPath.getSection()+"';";
                            logPrev += "'"+careerPath.getWorkFrom()+"';";
                            logPrev += "'"+careerPath.getWorkTo()+"';";
                            logPrev += "'"+careerPath.getSalary()+"';";
                            logPrev += "'"+careerPath.getDescription()+"';";
                        if(careerPath.getEmpCategoryId() != 0){
                            EmpCategory empCat = PstEmpCategory.fetchExc(careerPath.getEmpCategoryId());
                            logPrev += "["+careerPath.getEmpCategoryId()+"]"+empCat.getEmpCategory()+";";
                        }
                        else{
                            logPrev += careerPath.getEmpCategoryId()+";";
                        }
                            logPrev += "'"+careerPath.getEmpCategory()+"';";
                        if(careerPath.getDivisionId() != 0){
                            Division div = PstDivision.fetchExc(careerPath.getDivisionId());
                            logPrev += "["+careerPath.getDivisionId()+"]"+div.getDivision()+";";
                        }
                        else{
                            logPrev += careerPath.getDivisionId()+":";
                        }
                            logPrev += "'"+careerPath.getDivision()+"';";
                        if(careerPath.getLevelId() != 0){
                            Level level = PstLevel.fetchExc(careerPath.getLevelId());
                            logPrev += "["+careerPath.getLevelId()+"]"+level.getLevel()+";";
                        }
                        else{
                            logPrev += careerPath.getLevelId()+";";
                        }
                            logPrev += "'"+careerPath.getLevel()+"';";
                            logPrev += careerPath.getLocationId()+";";
                            logPrev += "'"+careerPath.getLocation()+"';";
                            logPrev += "'"+careerPath.getNote()+"';";
                        if(careerPath.getProviderID() != 0){
                            ContactList cont = PstContactList.fetchExc(careerPath.getProviderID());
                            logPrev += "["+careerPath.getProviderID()+"]"+cont.getCompName()+";";
                        }
                        else{
                            logPrev += careerPath.getProviderID()+";";
                        }
                        logPrev += careerPath.getHistoryType()+";";
                        logPrev += "'"+careerPath.getNomorSk()+"';";
                        logPrev += "'"+careerPath.getTanggalSk()+"';";
                        logPrev += careerPath.getEmpDocId()+";";
                        logPrev += careerPath.getHistoryGroup()+";";
                        if(careerPath.getGradeLevelId() != 0){
                            GradeLevel grade = PstGradeLevel.fetchExc(careerPath.getGradeLevelId());
                            logPrev += "["+careerPath.getGradeLevelId()+"]"+grade.getCodeLevel()+";";
                        }
                        else{
                            logPrev += careerPath.getGradeLevelId()+";";
                        }

                        
                        logCurr  = "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        
                                
                       

                        // logHistory
                        if (sysLog == 1) {


                            String className = careerPath.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;

                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(className); //entity
                            logSysHistory.setLogUserAction("DELETE"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logDetail); // apa yang dirubah
                            logSysHistory.setLogStatus(0);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Career Path");
                            logSysHistory.setLogEditedUserId(careerPath.getEmployeeId());

                            PstLogSysHistory.insertExc(logSysHistory);

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
