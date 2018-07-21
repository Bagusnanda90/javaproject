/*
 * TransferListXLS.java
 *
 * Created on December 24, 2007, 2:27 PM
 */
package com.dimata.harisma.printout;

import com.dimata.harisma.entity.admin.AppObjInfo;
import com.dimata.harisma.report.payroll.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.dimata.qdep.form.*;
import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.overtime.OvertimeDetail;
import com.dimata.harisma.entity.overtime.OvertimeSummary;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.session.admin.SessUserSession;
import com.dimata.harisma.session.employee.SessEmployee;
import com.dimata.system.entity.system.PstSystemProperty;
import java.io.PrintWriter;
import com.dimata.harisma.form.payroll.*;
import com.dimata.harisma.session.payroll.TaxCalculator;
import com.dimata.harisma.util.email;
import com.dimata.printman.DSJ_PrintObj;
import java.text.SimpleDateFormat;

/**
 *
 * @author Yunny
 */
public class PayPrintText extends HttpServlet {

    private static int pageLength = 32; // maximum row per slip
    private static int pageWidth = 89; // maximum lebar per slip
    //private static int pageWidth = 80; // maximum lebar per slip
    private static int leftMargin = 1; // margin di kiri
    private static int maxLeftgSiteLength = 43; // maximum lebar bagian kiri slip spt untuk company dan data karyawan
    //private static int maxLeftgSiteLength = 48; // maximum lebar bagian kiri slip spt untuk company dan data karyawan
    private static int startCompany = 1; // row mulai print company
    private static int startColHeaderValue = 19;
    private static int startRowSlipComp = 1; // start row slip component
    static int startColSlipComp = getLeftMargin() + getMaxLeftgSiteLength() + 3; // start col slip component    
    static int maxWidthSlipComp = ((getPageWidth() - startColSlipComp) * 2) / 3;
    static int startColSlipValue = startColSlipComp + maxWidthSlipComp + 1; // start col slip component
    static int maxWidthSlipValue = ((getPageWidth() - startColSlipComp)) / 3;

    /**
     * @return the pageLength
     */
    public static int getPageLength() {
        return pageLength;
    }

    /**
     * @param aPageLength the pageLength to set
     */
    public static void setPageLength(int aPageLength) {
        pageLength = aPageLength;
    }

    /**
     * @return the pageWidth
     */
    public static int getPageWidth() {
        return pageWidth;
    }

    /**
     * @return the leftMargin
     */
    public static int getLeftMargin() {
        return leftMargin;
    }

    /**
     * @return the maxLeftgSiteLength
     */
    public static int getMaxLeftgSiteLength() {
        return maxLeftgSiteLength;
    }

    /**
     * @return the startCompany
     */
    public static int getStartCompany() {
        return startCompany;
    }

    /**
     * @return the startColHeaderValue
     */
    public static int getStartColHeaderValue() {
        return startColHeaderValue;
    }

    /**
     * @return the startRowSlipComp
     */
    public static int getStartRowSlipComp() {
        return startRowSlipComp;
    }

    /**
     * @param aPageWidth the pageWidth to set
     */
    public static void setPageWidth(int aPageWidth) {
        pageWidth = aPageWidth;
    }

    /**
     * @param aLeftMargin the leftMargin to set
     */
    public static void setLeftMargin(int aLeftMargin) {
        leftMargin = aLeftMargin;
    }

    /**
     * @param aMaxLeftgSiteLength the maxLeftgSiteLength to set
     */
    public static void setMaxLeftgSiteLength(int aMaxLeftgSiteLength) {
        maxLeftgSiteLength = aMaxLeftgSiteLength;
    }

    /**
     * @param aStartCompany the startCompany to set
     */
    public static void setStartCompany(int aStartCompany) {
        startCompany = aStartCompany;
    }

    /**
     * @param aStartColHeaderValue the startColHeaderValue to set
     */
    public static void setStartColHeaderValue(int aStartColHeaderValue) {
        startColHeaderValue = aStartColHeaderValue;
    }

    /**
     * @param aStartRowSlipComp the startRowSlipComp to set
     */
    public static void setStartRowSlipComp(int aStartRowSlipComp) {
        startRowSlipComp = aStartRowSlipComp;
    }

    /**
     * Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /**
     * Destroys the servlet.
     */
    public void destroy() {
    }

    /**
     * Creates a new instance of TransferListXLS
     */
    public PayPrintText() {
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        System.out.println("---===| Print Payroll as Text |===--- ");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("text/txt");//application/msword");

        PrintWriter out = response.getWriter(); //response.getOutputStream();


        /* get data from session */
        HttpSession session = request.getSession(true);
        int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_PRINT);

        boolean isLoggedIn = false; // indicate if the user is loggen in or not
        SessUserSession userSession = (SessUserSession) session.getValue(SessUserSession.HTTP_SESSION_NAME); // instant of object user session

        try {
            if (userSession == null) {
                userSession = new SessUserSession();
            } else {
                if (userSession.isLoggedIn() == true) {
                    isLoggedIn = true;
                }
            }
        } catch (Exception exc) {
            out.println(" >>> Exception during check login");
            out.flush();
            return;
        }

        boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
        boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));

        if (!privView && !privPrint) {
            out.println(" >>> You have no privileges");
            out.flush();
            return;
        }

        boolean toEmail = FRMQueryString.requestBoolean(request, "toEmail");
        int detailSlipYN = FRMQueryString.requestInt(request, "detail_slip");
        int iCommand = FRMQueryString.requestCommand(request);
        long periodId = FRMQueryString.requestLong(request, "periodId");
        int keyPeriod = FRMQueryString.requestInt(request, "paySlipPeriod");
        //update by satrya 2013-01-24
        long payGroupId = FRMQueryString.requestLong(request, "payGroupId");
        //String frmCurrency = "#,###";
        //update by satrya 2014-6-01
        String frmCurrency = "#,###";
        String mataUang = "Rp";
        CurrencyType currencyType = new CurrencyType();
        try {
            Vector currType = PstCurrencyType.list(0, 1, PstCurrencyType.fieldNames[PstCurrencyType.FLD_INCLUDE_INF_PROCESS] + "=" + PstCurrencyType.YES_USED, "");
            if (currType != null && currType.size() > 0) {
                currencyType = (CurrencyType) currType.get(0);
                frmCurrency = currencyType.getFormatCurrency();
                mataUang = currencyType.getCode() + ".";
            }
        } catch (Exception exc) {
        }

        boolean printZeroValue = true;
        String sprintZeroValue = PstSystemProperty.getValueByName("PAYROLL_PRINT_ZERO_VALUE");
        if (sprintZeroValue == null || sprintZeroValue.length() == 0 || sprintZeroValue.equalsIgnoreCase("YES")
                || sprintZeroValue.equalsIgnoreCase("1") || sprintZeroValue.equalsIgnoreCase("Not initialized")) {
            printZeroValue = true;
        } else {
            printZeroValue = false;
        }


        PayPeriod objPeriod = new PayPeriod();
        // Period objPeriod = new Period();
        try {
            //update by satrya 2013-02-12
            objPeriod = PstPayPeriod.fetchExc(periodId);
        } catch (Exception exc) {
        }

        CtrlPaySlipComp ctrlPaySlipComp = new CtrlPaySlipComp(request);
        int start = FRMQueryString.requestInt(request, "start");
        int inclResign = FRMQueryString.requestInt(request, "INCLUDE_RESIGN");
        boolean bIncResign = (inclResign == 1);
        long oidDivision = FRMQueryString.requestLong(request, "division");
        long oidDepartment = FRMQueryString.requestLong(request, "department");
        long oidSection = FRMQueryString.requestLong(request, "section");
        long oidPaySlipComp = FRMQueryString.requestLong(request, "section");
        String searchNrFrom = FRMQueryString.requestString(request, "searchNrFrom");
        String searchNrTo = FRMQueryString.requestString(request, "searchNrTo");
        String searchName = FRMQueryString.requestString(request, "searchName");
        int dataStatus = FRMQueryString.requestInt(request, "dataStatus");
        String codeComponenGeneral = FRMQueryString.requestString(request, "compCode");
        String compName = FRMQueryString.requestString(request, "compName");
        int aksiCommand = FRMQueryString.requestInt(request, "aksiCommand");
        long periodeId = FRMQueryString.requestLong(request, "periodId");
        int numKolom = FRMQueryString.requestInt(request, "numKolom");
        int statusSave = FRMQueryString.requestInt(request, "statusSave");
        
        int iErrCode = FRMMessage.ERR_NONE;
        String msgString = "";
        String msgStr = "";
        int recordToGet = 1000;
        int vectSize = 0;
        String orderClause = "";
        String whereClause = "";

        iErrCode = ctrlPaySlipComp.action(iCommand, oidPaySlipComp);
        FrmPaySlipComp frmPaySlipComp = ctrlPaySlipComp.getForm();
        PaySlipComp paySlipComp = ctrlPaySlipComp.getPaySlipComp();
        msgString = ctrlPaySlipComp.getMessage();

        Vector listEmpPaySlip = SessEmployee.listEmpPaySlip(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign, 0);
//        String s_employee_id = null;
//        String s_payslip_id = null;
//        String s_level_code = null;
        long oidEmployee = 0;
        String noPrint = "";
        //update by satrya 2013-01-29
        Vector sumOvertimeDailyDurIdxSalary = new Vector();
        Vector sumOvertimeDailyDurIdxDP = new Vector();
        Vector sumOvertimeDailyDurIdxTotal = new Vector();
        if (objPeriod.getStartDate() != null && objPeriod.getEndDate() != null) {
            sumOvertimeDailyDurIdxSalary = PstOvertimeDetail.listSummaryOT(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, objPeriod.getStartDate(), objPeriod.getEndDate(), bIncResign, OvertimeDetail.PAID_BY_SALARY, -1);
            sumOvertimeDailyDurIdxDP = PstOvertimeDetail.listSummaryOT(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, objPeriod.getStartDate(), objPeriod.getEndDate(), bIncResign, -1, OvertimeDetail.PAID_BY_DAY_OFF);
            sumOvertimeDailyDurIdxTotal = PstOvertimeDetail.listSummaryOT(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, objPeriod.getStartDate(), objPeriod.getEndDate(), bIncResign, -1, -1);
        }
        //update by satrya 2013-01-14
        String[] listNoPrint = new String[listEmpPaySlip.size()];
        int noInc = 0;
        if (aksiCommand == 1) {
            System.out.println("print selected");
            Vector listEmpId = new Vector();
            
            for (int i = 0; i < listEmpPaySlip.size(); i++) {

                try {
                    oidEmployee = FRMQueryString.requestLong(request, "print" + i + ""); // row yang dicheked
                    noPrint = FRMQueryString.requestString(request, "no_print" + i + "");
                } catch (Exception e) {
                    System.out.println("Exception" + e);
                }
                //System.out.println("nilai statusSave"+statusSave);
                //update by satrya 2013-01-14
                if (oidEmployee != 0) {
                    listEmpId.add(oidEmployee);
                    listNoPrint[noInc] = noPrint;
                    noInc++;
                }
                
            }
            if (listEmpId != null && listEmpId.size() > 0) {
                listEmpPaySlip = SessEmployee.listEmpPaySlipByEmployeeId(listEmpId, periodeId, -1, bIncResign);
            }
        } else {
            for (int i = 0; i<listEmpPaySlip.size(); i++){
                listNoPrint[i] = String.valueOf(1+i);
            }
        }


        int MODEL_PAYSLIP = PaySlip.MODEL_UP_DOWN;
        int payslipOT = 0;
        try {
            String strSlip = PstSystemProperty.getValueByName("MODEL PAYSLIP");
            if (strSlip != null) {
                MODEL_PAYSLIP = Integer.parseInt(strSlip);
            }
        } catch (Exception exc) {
        }

        String spayslipOT = null;
        try {
            spayslipOT = PstSystemProperty.getValueByName("PAYROLL_PAY_SLIP_CONFIG_OVERTIME_LOCATION");
            if (spayslipOT != null) {
                payslipOT = Integer.parseInt(spayslipOT);
            }
        } catch (Exception exc) {
            System.out.println("Exception PAYROLL_PAY_SLIP_CONFIG_OVERTIME_LOCATION not be set");
            payslipOT = 0;
        }
        long SignatureOnOff =0;
        try {
               SignatureOnOff = Long.parseLong(PstSystemProperty.getValueByName("AKTIF_SIGNATURE"));
        } catch (Exception E) {
        }
        
        String codeUpahLembur = null;
        try {
            codeUpahLembur = PstSystemProperty.getValueByName("PAYROLL_PAY_SLIP_CODE_UPAH_LEMBUR");

        } catch (Exception exc) {
            System.out.println("Exception PAYROLL_PAY_SLIP_CODE_UPAH_LEMBUR not be set");

        }
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
        //update by satrya 2013-02-21
        String codeTax_count = "";
        try {
            codeTax_count = PstSystemProperty.getValueByName("PAY_TAX_CON");
        } catch (Exception exc) {
            codeTax_count = "TAX_CON";
        }
        //priska 20151218
        String ShowAttendance ="";
        try {
             ShowAttendance = com.dimata.system.entity.PstSystemProperty.getValueByName("SHOW_ATTENDANCE_DATA_ON_PAYSLIP");
        } catch (Exception ex) {
            System.out.println("Execption SHOW_ATTENDANCE_DATA_ON_PAYSLIP " + ex);
        }
        
        PayPeriod payPeriod = new PayPeriod();
        //Period period = new Period();
        String periodName = "";
        Date endDatePeriod = new Date();
        try {
            payPeriod = PstPayPeriod.fetchExc(periodId);
            //period = PstPeriod.fetchExc(periodId);
            periodName = payPeriod.getPeriod();
            //update by satrya 2013-02-12
            endDatePeriod = payPeriod.getEndDate();
        } catch (Exception e) {
        }
        PayGeneral payGen = new PayGeneral();
        Employee employee = new Employee();
        PaySlip paySlip = new PaySlip();
        String garis = "";
        int enter = 4;
        int incEnter = 0;
        long sectionTemp = 0;
        int increment = 1;
        for (int ip = 0; ip < listEmpPaySlip.size(); ip++) {
            Vector vlst = (Vector) listEmpPaySlip.get(ip);
            Employee objEmployee = (Employee) vlst.get(0);
            PayEmpLevel objPayEmpLevel = (PayEmpLevel) vlst.get(1);
            PaySlip objPaySlip = (PaySlip) vlst.get(2);

            long employeeId = objEmployee.getOID();
            String salaryLevel = objPayEmpLevel.getLevelCode();
            long paySlipId = objPaySlip.getOID();
            // Update 2015-01-12 by Hendra McHen
            Vector listPaySalary = PstSalaryLevelDetail.listPaySlipNew(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_BENEFIT, paySlipId, keyPeriod, printZeroValue, payGroupId);
            Vector listPayDeduction = PstSalaryLevelDetail.listPaySlipNew(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_DEDUCTION, paySlipId, keyPeriod, printZeroValue, payGroupId);

            // get the data for employee who have pay slip
            Vector empSlip = PstPaySlip.getEmpSlip(periodId, paySlipId);
            String employeeName = "";
            String employeeNum = "";
            int status = 0;
            int paidStatus = 0;
            String division = "";
            String department = "";
            String section = "";
            String position = "";
            double day_present = 0;
            double day_paid_lv = 0;
            double day_absent = 0;
            double day_unpaid_lv = 0;
            double day_late = 0;
            String note = "";
            //update by satrya 2013-01-20
            String bankAcc = "";
            String bankNameX = "";
            Date commDate = new Date();
            //update by satrya 2013-05-06
            double ovIdAdjustment = 0;
            String privNote = "";
            String printPaySlip = "";
            String otDuration = "";
            // double otIdx=0;
            double otPaidBySalary = 0;
            double otPaidByDP = 0;
            double otPaidByTotal = 0;
            String paidBySalary = "";
            //System.out.println("empSlip.size()"+empSlip.size());
           /* if ((empSlip != null) && empSlip.size() > 0) {
             employee = (Employee) empSlip.get(0);
             paySlip = (PaySlip) empSlip.get(1);
             }*/
            if ((empSlip != null) && empSlip.size() > 0) {
                if (sumOvertimeDailyDurIdxSalary != null && sumOvertimeDailyDurIdxSalary.size() > 0 || sumOvertimeDailyDurIdxDP != null && sumOvertimeDailyDurIdxDP.size() > 0) {
                    if (sumOvertimeDailyDurIdxSalary != null && sumOvertimeDailyDurIdxSalary.size() > 0) {
                        for (int idxSal = 0; idxSal < sumOvertimeDailyDurIdxSalary.size(); idxSal++) {
                            OvertimeSummary overtimeDetailSummary = (OvertimeSummary) sumOvertimeDailyDurIdxSalary.get(idxSal);
                            if (overtimeDetailSummary.getEmployeeId() == employeeId) {
                                otPaidBySalary = overtimeDetailSummary.getOtPaidSummarySallary();
                                paidBySalary = Formater.formatWorkDayHoursMinutes((float) overtimeDetailSummary.getOtDuration() / leaveConfig.getHourOneWorkday(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                                sumOvertimeDailyDurIdxSalary.remove(idxSal);
                                idxSal = idxSal - 1;
                                break;
                            }
                        }
                    }
                    if (sumOvertimeDailyDurIdxDP != null && sumOvertimeDailyDurIdxDP.size() > 0) {
                        for (int idxDP = 0; idxDP < sumOvertimeDailyDurIdxDP.size(); idxDP++) {
                            OvertimeSummary overtimeDetailSummary = (OvertimeSummary) sumOvertimeDailyDurIdxDP.get(idxDP);
                            if (overtimeDetailSummary.getEmployeeId() == employeeId) {
                                otPaidByDP = overtimeDetailSummary.getOtPaidSummaryDp();
                                sumOvertimeDailyDurIdxDP.remove(idxDP);
                                idxDP = idxDP - 1;
                                break;
                            }
                        }
                    }
                    if (sumOvertimeDailyDurIdxTotal != null && sumOvertimeDailyDurIdxTotal.size() > 0) {
                        for (int idxOtTot = 0; idxOtTot < sumOvertimeDailyDurIdxTotal.size(); idxOtTot++) {
                            OvertimeSummary overtimeDetailSummary = (OvertimeSummary) sumOvertimeDailyDurIdxTotal.get(idxOtTot);
                            if (overtimeDetailSummary.getEmployeeId() == employeeId) {
                                otPaidByTotal = overtimeDetailSummary.getOtPaidSummarySallaryAndDP();
                                otDuration = Formater.formatWorkDayHoursMinutes((float) overtimeDetailSummary.getOtDuration(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                                sumOvertimeDailyDurIdxTotal.remove(idxOtTot);
                                idxOtTot = idxOtTot - 1;
                                break;
                            }
                        }
                    }
                    employee = (Employee) empSlip.get(0);
                    paySlip = (PaySlip) empSlip.get(1);
                } else {
                    employee = (Employee) empSlip.get(0);
                    paySlip = (PaySlip) empSlip.get(1);
                }

            } else {
                employee = new Employee();
                paySlip = new PaySlip();
            }
            employeeName = employee.getFullName();
            employeeNum = employee.getEmployeeNum();
            status = paySlip.getStatus();
            paidStatus = paySlip.getPaidStatus();
            division = paySlip.getDivision();
            department = paySlip.getDepartment();
            section = paySlip.getSection();
            position = paySlip.getPosition();
            commDate = paySlip.getCommencDate();
            day_present = paySlip.getDayPresent();
            day_paid_lv = paySlip.getDayPaidLv();
            day_absent = paySlip.getDayAbsent();
            day_unpaid_lv = paySlip.getDayUnpaidLv();
            day_late = paySlip.getDayLate();
            if (payslipOT == 0) {
                note = paySlip.getNote();
            } else {

                note = paySlip.getNote() + "<br> Overtime Duration: " + otDuration + "<br> Overtime Idx Total: " + otPaidByTotal + " <br> Overtime Idx By Paid Salary: " + otPaidBySalary
                        + " <br> Overtime Idx By Paid Day OFF: " + otPaidByDP + " <br> Overtime Idx Adjusment: " + ovIdAdjustment + (privNote != null && privNote.length() > 0 ? " <br> Overtime Idx Adjusment: " + privNote : "");
            }
            //update by satrya 2013-01-20
            if (getMaxLeftgSiteLength() != 0) {
                for (int x = 0; x < getMaxLeftgSiteLength(); x++) {
                    garis = garis + "_";
                }
            }
            /* Get No Rekening | Hendra Putu | 2016-03-29 */
            try {
                Employee empx = PstEmployee.fetchExc(employeeId);
                bankAcc = empx.getNoRekening();
            } catch(Exception e){
                System.out.println(e.toString());
            }
            ///bankAcc = employee.getNoRekening(); //objPayEmpLevel.getBankAccNr();
            bankNameX = objPaySlip.getBankName();
            ovIdAdjustment = objPaySlip.getOvIdxAdj();
            privNote = objPaySlip.getPrivateNote();

            if (employee != null && (employee.getCompanyId() != payGen.getOID() || payGen.getOID() == 0) && employee.getCompanyId() != 0) {
                String whereGen = "" + PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID] + "=" + employee.getCompanyId();
                Vector vectCity = PstPayGeneral.list(0, 0, whereGen, "");
                if (vectCity != null && vectCity.size() > 0) {
                    payGen = (PayGeneral) vectCity.get(0);
                }
            }
            printPaySlip = (payGen.getCity() != null && payGen.getCity().length() > 0 ? payGen.getCity() : "") + "," + (objPaySlip.getPaySlipDate() != null ? Formater.formatDate(objPaySlip.getPaySlipDate(), "dd/MMM/yy") : "");


            /*
             * Description : Get position and period
             * Date : 2015-02-24
             * Author : Hendra Putu
             */
            Date toDay = new Date();
            Calendar cal = Calendar.getInstance();
            //cal.add(Calendar.DATE, 1);
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            String formatted = formatDate.format(cal.getTime());
            
            Position pos = new Position();
            Marital mStatus = new Marital();
            Employee emp = new Employee();
            try {
                emp = PstEmployee.fetchExc(employee.getOID());
                pos = PstPosition.fetchExc(emp.getPositionId());
                mStatus = PstMarital.fetchExc(emp.getMaritalId());
            } catch(Exception e){
                System.out.print(e);
            }
            if (sectionTemp == 0){
                sectionTemp = objEmployee.getSectionId();
            } else {
                if (sectionTemp == objEmployee.getSectionId()){
                    increment++;
                } else {
                    increment = 1;
                    sectionTemp = 0;
                }
            }
            
            if (incEnter > 15){
                enter = enter - 1;
                incEnter = 0;
            } else {
                enter = 4;
                incEnter++;
            }
            switch (MODEL_PAYSLIP) {

                case PaySlip.MODEL_UP_DOWN:
                    try {
                        DSJ_PrintObj obj = new DSJ_PrintObj();
                        obj.setPageLength(getPageLength());
                        obj.setPageWidth(getPageWidth());
                        obj.setSkipLineIsPaperFix(2);
                        obj.setLeftMargin(getLeftMargin());
                        obj.setFont(DSJ_PrintObj.SANS_SERIF);
                        obj.setCpiIndex(10);
                        
                        //obj.addLines(getPageLength(), "");
                        
                        obj.setLine(enter, startColSlipValue+5, "No.", 5);
                        obj.setLine(enter, startColSlipValue+9, ""+increment, 5);
                        int m = enter;
                        obj.setLine(getStartCompany()+m, getLeftMargin(), "Section", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany()+m++, getLeftMargin() + getStartColHeaderValue()+5, ": "+section, getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany()+m, getLeftMargin(), "Employee No.", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany()+m++, getLeftMargin() + getStartColHeaderValue()+5, ": "+employeeNum+"/"+employeeName, getMaxLeftgSiteLength()+9);
                        obj.setLine(getStartCompany()+m, getLeftMargin(), "Position", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany()+m++, getLeftMargin() + getStartColHeaderValue()+5, ": "+pos.getPosition(), getMaxLeftgSiteLength()+9);
                        int r = enter-1;
                        obj.setLine(getStartRowSlipComp()+r, startColSlipComp+5, "Period", getMaxLeftgSiteLength());
                        obj.setLine(getStartRowSlipComp()+r++, startColSlipValue+5, ": "+periodName, getMaxLeftgSiteLength());
                        obj.setLine(getStartRowSlipComp()+r, startColSlipComp+5, "Status", getMaxLeftgSiteLength());
                        obj.setLine(getStartRowSlipComp()+r++, startColSlipValue+5, ": "+TaxCalculator.STATUS_EMPLOYEE_CODE[mStatus.getMaritalStatusTax()], getMaxLeftgSiteLength());
                        //obj.setLineCenterAlign(getStartCompany(), getLeftMargin(), getMaxLeftgSiteLength()/2, payGen.getCompanyName(), getMaxLeftgSiteLength());
                        int n = enter+6;
                        int tempN = n+5;
                        int c = n - 1;
                        //obj.setLineCenterAlign((getStartCompany() + n++)/2, getLeftMargin(), getMaxLeftgSiteLength()/2, payGen.getCompAddress(), getMaxLeftgSiteLength());
                        //obj.setLineCenterAlign((getStartCompany() + n++)/2, getLeftMargin(), getMaxLeftgSiteLength()/2,payGen.getCity() + " " + payGen.getZipCode(), getMaxLeftgSiteLength());
                        //obj.setLineCenterAlign((getStartCompany() + n++)/2, getLeftMargin(), getMaxLeftgSiteLength()/2,"Telp. " + payGen.getTel() + " Fax. " + payGen.getFax(), getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany()+c, getLeftMargin(), payGen.getCompanyName(), getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin(), payGen.getCompAddress(), getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin(), payGen.getCity() + " " + payGen.getZipCode(), getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin(), "Telp. " + payGen.getTel() + " Fax. " + payGen.getFax(), getMaxLeftgSiteLength());

                        obj.setLine(getStartCompany() + n++, getLeftMargin(), garis, getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin(), " ", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n, getLeftMargin(), "Period", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin() + getStartColHeaderValue()+5, ": "
                                + periodName + (detailSlipYN == 1 ? " / " + Formater.formatTimeLocale(endDatePeriod, "dd-MM-yyyy") : ""), getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n, getLeftMargin(), "Empl. Name ", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin() + getStartColHeaderValue()+5, ": " + employeeName, getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n, getLeftMargin(), "Empl. Number", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin() + getStartColHeaderValue()+5, ": " + employeeNum, getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n, getLeftMargin(), (detailSlipYN == 1 ? "Div. / Department" : "Department"), getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin() + getStartColHeaderValue()+5, (detailSlipYN == 1 ? (": " + division + " / " + department) : ": " + department), getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n, getLeftMargin(), "Comm. date", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin() + getStartColHeaderValue()+5, ": " + Formater.formatTimeLocale(commDate, "dd-MM-yyyy"), getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n, getLeftMargin(), "Bank Account", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin() + getStartColHeaderValue()+5, ": " + bankAcc, getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n, getLeftMargin(), "Bank Name", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin() + getStartColHeaderValue()+5, ": " + bankNameX, getMaxLeftgSiteLength());

                        Date endDt = objPeriod.getEndDate() == null ? new Date() : objPeriod.getEndDate();
                        // end date
                        Calendar gre = Calendar.getInstance();
                        gre.setTime(endDt);
                        int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                        Date dtEnd = (objPeriod.getPaySlipDate() != null) ? objPeriod.getPaySlipDate() : (new Date(endDt.getYear(), endDt.getMonth(), day));

                        obj.setLine(getStartCompany() + n, getLeftMargin(), "Note", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin() + getStartColHeaderValue()+5, ":", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + n++, getLeftMargin(), " " + note, getMaxLeftgSiteLength(), 3);

                        drawListBenefit(obj, getStartRowSlipComp()+enter+4, listPaySalary, employeeId, periodId, paySlipId, paidBySalary, codeUpahLembur, currencyType);

                        drawListDeduction(obj, getStartRowSlipComp()+enter+4+ (listPaySalary != null ? listPaySalary.size() : 0), listPayDeduction, employeeId, periodId, paySlipId, codeTax_count, currencyType);
 
                        if (SignatureOnOff >0){
                        drawListSignature(obj, getStartRowSlipComp()+enter+4+ (listPaySalary != null ? listPaySalary.size() : 0)+(listPayDeduction != null ? listPayDeduction.size() : 0),objEmployee);
                        }
                        // cari total benefit
                        double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_BENEFIT, paySlipId, keyPeriod, payGroupId);
                        //System.out.println("totalBenefit..............."+totalBenefit);
                        double totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_DEDUCTION, paySlipId, keyPeriod, payGroupId);

                        // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                        double totTakHomePay = totalBenefit - totalDeduction;
                        obj.setLine(getStartRowSlipComp()+tempN + (listPaySalary != null ? listPaySalary.size() : 0)
                                + (listPayDeduction != null ? listPayDeduction.size() : 0) - (codeTax_count != null && codeTax_count.length() > 0 ? 2 : 0), startColSlipComp,
                                "Total Take Home Pay", maxWidthSlipComp);
                        //update by satrya 2013-05-06
                        //String ov= otDuration +"/"+otPaidByTotal+"/"+otPaidBySalary+"/"+otPaidByDP+"/"+ovIdAdjustment;

                        obj.setLine(getStartRowSlipComp()+tempN + (listPaySalary != null ? listPaySalary.size() : 0)
                                + (listPayDeduction != null ? listPayDeduction.size() : 0) - (codeTax_count != null && codeTax_count.length() > 0 ? 2 : 0), startColSlipValue,
                                ":  " + mataUang + Formater.formatNumberVer1(totTakHomePay, frmCurrency), maxWidthSlipComp);

                        obj.setLine(getStartRowSlipComp()+tempN+19, startColSlipComp,"", maxWidthSlipComp);
                        if (payslipOT == 0) {
                            /* obj.setLine((getStartRowSlipComp() + (listPaySalary!=null? listPaySalary.size():0) + 
                             (listPayDeduction!=null? listPayDeduction.size():0) - (codeTax_count!=null && codeTax_count.length()>0? 2:0)) +1, getLeftMargin(), "Ov.Hr/ov.Idx/ov.Paid Sal/ov.Paid DP/ov.Idx adj", getMaxLeftgSiteLength());
                             obj.setLine((getStartRowSlipComp() + (listPaySalary!=null? listPaySalary.size():0) + 
                             (listPayDeduction!=null? listPayDeduction.size():0)- (codeTax_count!=null && codeTax_count.length()>0? 2:0)) +1, startColSlipComp, ": "+ ov,  pageWidth - leftMargin - maxLeftgSiteLength );
                             */
                            if (privNote != null && privNote.length() > 0 && !privNote.equalsIgnoreCase("null")) {
                                obj.setLine((getStartRowSlipComp() + (listPaySalary != null ? listPaySalary.size() : 0)
                                        + (listPayDeduction != null ? listPayDeduction.size() : 0) - (codeTax_count != null && codeTax_count.length() > 0 ? 2 : 0)) + 1, getLeftMargin(), "Note Private", getMaxLeftgSiteLength());
                                obj.setLine((getStartRowSlipComp() + (listPaySalary != null ? listPaySalary.size() : 0)
                                        + (listPayDeduction != null ? listPayDeduction.size() : 0) - (codeTax_count != null && codeTax_count.length() > 0 ? 2 : 0)) + 1, startColSlipComp, ": " + privNote, pageWidth - leftMargin - maxLeftgSiteLength);
                            }
                        }
                        //if(privNote!=null && privNote.length()>0 && !privNote.equalsIgnoreCase("null")){
                          /*  obj.setLine((getStartCompany() + (listPaySalary!=null? listPaySalary.size():0) + 
                         (listPayDeduction!=null? listPayDeduction.size():0)- (codeTax_count!=null && codeTax_count.length()>0? 2:0)) +(payslipOT==0?1:2), getLeftMargin(), "Tgl Cetak",getMaxLeftgSiteLength());
                            
                         obj.setLine((getStartCompany() + (listPaySalary!=null? listPaySalary.size():0) + 
                         (listPayDeduction!=null? listPayDeduction.size():0)- (codeTax_count!=null && codeTax_count.length()>0? 2:0)) +(payslipOT==0?2:3), getLeftMargin(), ": "+ printPaySlip,  getMaxLeftgSiteLength() );*/
                        /*
                        obj.setLine(getStartCompany() + ((listPaySalary != null ? listPaySalary.size() : 0)
                                + (listPayDeduction != null ? listPayDeduction.size() : 0) - (codeTax_count != null && codeTax_count.length() > 0 ? 2 : 0) + (privNote != null && privNote.length() > 0 && !privNote.equalsIgnoreCase("null") ? 2 : 1)), getLeftMargin(), "Tgl Cetak", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany() + ((listPaySalary != null ? listPaySalary.size() : 0)
                                + (listPayDeduction != null ? listPayDeduction.size() : 0) - (codeTax_count != null && codeTax_count.length() > 0 ? 2 : 0) + (privNote != null && privNote.length() > 0 && !privNote.equalsIgnoreCase("null") ? 2 : 1)), getLeftMargin() + getStartColHeaderValue(), ": " + printPaySlip, getMaxLeftgSiteLength());
                        */
                        int nilaiPresence = PstPayInput.getPayInputValue(paySlipId, "FRM.FIELD.PRESENCE.ONTIME.TIME", periodeId, employeeId);
                        int nilaiWorkday = PstPayInput.getPayInputValue(paySlipId, "FRM.FIELD.EMP.WORK.DAYS", periodeId, employeeId);
                        obj.setLine(getStartCompany()+ n, getLeftMargin(), "Tgl Cetak", getMaxLeftgSiteLength());
                        obj.setLine(getStartCompany()+ n, getLeftMargin() + getStartColHeaderValue()+5, ": " + formatted, getMaxLeftgSiteLength()-2);
                        
                        if (ShowAttendance.equals("1")){
                            obj.setLine(getStartCompany()+ n+1, getLeftMargin(), "Workdays", getMaxLeftgSiteLength());
                            obj.setLine(getStartCompany()+ n+1, getLeftMargin() + getStartColHeaderValue()+5, ": " +nilaiWorkday, getMaxLeftgSiteLength()-2);
                            obj.setLine(getStartCompany()+ n+2, getLeftMargin(), "Attendance", getMaxLeftgSiteLength());
                            obj.setLine(getStartCompany()+ n+2, getLeftMargin() + getStartColHeaderValue()+5, ": " +nilaiPresence, getMaxLeftgSiteLength()-2);
                        
                        }
                        
                        // }
                        String strEmail = "";
                        for (int idx = 0; idx < obj.getLines().size(); idx++) {
                            String aLine = (String) obj.getLines().get(idx);
                            strEmail = strEmail+ aLine; 
                            out.println(aLine);
                        }
                        if(toEmail && employee!=null && employee.getEmailAddress()!=null && employee.getEmailAddress().length()>5){
                            Vector<String> listRec = new Vector();
                            listRec.add(""+employee.getEmailAddress());
                            email.sendEmail(listRec, null, null, "Payslip "+ payPeriod.getPeriod() + " of " + employee.getFullName(), strEmail, null, null); 
                        }
                        
                        
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
                    break;
                default:
                    out.println("No settting for default format ");
            }
        }
        out.flush();
    }

    public DSJ_PrintObj drawListBenefit(DSJ_PrintObj obj, int startRow, Vector objectClass, long employeeId, long periodId, long paySlipId, String paidBySalary, String codeUpahLembur, CurrencyType currencyType) {
        String output = "";
        String garis = "";
        int inc = 0;
        double totalBenefit = 0;
        //String frmCurrency = "#,###";
        //update by satrya 2014-06-01
        String frmCurrency = "#,###";
        String mataUang = "Rp";
        if (currencyType != null && currencyType.getCode() != null && currencyType.getFormatCurrency() != null) {
            frmCurrency = currencyType.getFormatCurrency();
            mataUang = currencyType.getCode() == null || currencyType.getCode().length() == 0 ? "Rp" : currencyType.getCode() + ".";
        }
        for (int i = 0; i < objectClass.size(); i++) {
            Vector temp = (Vector) objectClass.get(i);
            Vector vectToken = new Vector(1, 1);
            PayComponent payComponent = (PayComponent) temp.get(0);
            SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail) temp.get(1);
            PaySlipComp paySlipComp = (PaySlipComp) temp.get(2);
            // ambil value dari komponent-komponent
            //obj.setLine(startRow+i, startColSlipComp, payComponent.getCompName() , maxWidthSlipComp);
            //obj.setLine(startRow+i, startColSlipValue, ":  Rp. " + Formater.formatNumberVer1(paySlipComp.getCompValue(), frmCurrency), maxWidthSlipComp);
            if (codeUpahLembur != null && codeUpahLembur.length() > 0 && codeUpahLembur.equalsIgnoreCase(payComponent.getCompCode())) {
                //jika componennya mengandung UL 
                obj.setLine(startRow + i, startColSlipComp, payComponent.getCompName() + " (" + paidBySalary + ")", maxWidthSlipComp);
                obj.setLine(startRow + i, startColSlipValue, ":  "+ mataUang + Formater.formatNumberVer1(paySlipComp.getCompValue(), frmCurrency), maxWidthSlipComp);
                if (payComponent.getUsedInForml()!= 0){
                    totalBenefit = totalBenefit + paySlipComp.getCompValue();
                }
                
            } else {
                obj.setLine(startRow + i, startColSlipComp, payComponent.getCompName(), maxWidthSlipComp);
                obj.setLine(startRow + i, startColSlipValue, ":  " + mataUang + Formater.formatNumberVer1(paySlipComp.getCompValue(), frmCurrency), maxWidthSlipComp);
                if (payComponent.getUsedInForml()!= 0){
                    totalBenefit = totalBenefit + paySlipComp.getCompValue();
                }
            }
            inc++;
        }
        if (maxWidthSlipComp != 0) {
            for (int x = 0; x < maxWidthSlipComp+1; x++) {
                garis = garis + "_";
            }
        }
        obj.setLine(startRow + inc, startColSlipComp, garis, maxWidthSlipComp+2);
        obj.setLine(startRow + inc, startColSlipValue, garis, maxWidthSlipComp-10);
        
        obj.setLine(startRow + inc+1, startColSlipComp, "Total Benefit", maxWidthSlipComp);
        obj.setLine(startRow + inc+1, startColSlipValue, ":  " + mataUang + Formater.formatNumberVer1(totalBenefit, frmCurrency), maxWidthSlipComp);
        
        obj.setLine(startRow + inc+2, startColSlipComp, " ", maxWidthSlipComp);
        obj.setLine(startRow + inc+2, startColSlipValue, " ", maxWidthSlipComp);
        return obj;
    }

    public String drawListDeduction(DSJ_PrintObj obj, int startRow, Vector objectClass, long employeeId, long periodId, long paySlipId, String codeTax_count, CurrencyType currencyType) {
        String output = "";
        int totalBenefit = 0;
        String garis = "";
        double totalDeduction = 0;
        //String frmCurrency = "#,###";
        String frmCurrency = "#,###";
        String mataUang = "Rp";
        if (currencyType != null && currencyType.getCode() != null && currencyType.getFormatCurrency() != null) {
            frmCurrency = currencyType.getFormatCurrency();
            mataUang = currencyType.getCode() == null || currencyType.getCode().length() == 0 ? "Rp." : (currencyType.getCode() + ".");
        }
        int rows = startRow + 3;
        for (int i = 0; i < objectClass.size(); i++) {

            Vector temp = (Vector) objectClass.get(i);
            Vector vectToken = new Vector(1, 1);
            PayComponent payComponent = (PayComponent) temp.get(0);
            SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail) temp.get(1);
            PaySlipComp paySlipComp = (PaySlipComp) temp.get(2);
            // ambil value dari komponent-komponent

            if (codeTax_count != null && codeTax_count.length() > 0 && codeTax_count.equalsIgnoreCase(payComponent.getCompCode())) {
                //jika componennya mengandung pph yg di hitung konsultan maka tidak di tampilkan
                rows = rows;
            } else {
                obj.setLine(rows, startColSlipComp, payComponent.getCompName(), maxWidthSlipComp);
                obj.setLine(rows, startColSlipValue, ":  " + mataUang + Formater.formatNumberVer1(paySlipComp.getCompValue(), frmCurrency), maxWidthSlipComp);
                rows = rows + 1;
                totalDeduction = totalDeduction + paySlipComp.getCompValue();
            }

        }
        if (maxWidthSlipComp != 0) {
            for (int x = 0; x < maxWidthSlipComp+1; x++) {
                garis = garis + "_";
            }
        }
        obj.setLine(rows, startColSlipComp, garis, maxWidthSlipComp+2);
        obj.setLine(rows, startColSlipValue, garis, maxWidthSlipComp-10);
        obj.setLine(rows+1, startColSlipComp, "Total Deduction", maxWidthSlipComp);
        obj.setLine(rows+1, startColSlipValue, ":  " + mataUang + Formater.formatNumberVer1(totalDeduction, frmCurrency), maxWidthSlipComp);
        return output;
    }

    
     public String drawListSignature(DSJ_PrintObj obj, int startRow, Employee objEmployee) {
        String output = "";
        int totalBenefit = 0;
        
        int rows = startRow + 8;
        Date thisDate =  new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String DateNew = formatDate.format(thisDate);
        
        Company company = new Company();
        try{
            company = PstCompany.fetchExc(objEmployee.getCompanyId());
        }catch (Exception e){
            
        }
        Vector empWithLevel = PstEmployee.listEmployeeByLevel(objEmployee);
        Vector emplevel = (Vector) empWithLevel.get(0);
        
        Employee employee = new Employee();
        Level level = new Level();
        employee = (Employee) emplevel.get(0);
        level = (Level) emplevel.get(1);
        
        String garis = "_";
        for (int i = 0; i < employee.getFullName().length(); i++ ){
            garis = garis +"_";
        }
        
        obj.setLine(rows, startColSlipComp, "____________, "+DateNew, maxWidthSlipComp);
        obj.setLine(rows, startColSlipValue, "", maxWidthSlipComp);
        obj.setLine(rows+1, startColSlipComp, company.getCompany(), maxWidthSlipComp);
        obj.setLine(rows+1, startColSlipValue, "", maxWidthSlipComp);
        
        obj.setLine(rows+2, startColSlipComp, level.getLevel()+", ", maxWidthSlipComp);
        obj.setLine(rows+2, startColSlipValue, "", maxWidthSlipComp);
        obj.setLine(rows+3, startColSlipComp, "", maxWidthSlipComp);
        obj.setLine(rows+3, startColSlipValue, "", maxWidthSlipComp);
        obj.setLine(rows+4, startColSlipComp, "", maxWidthSlipComp);
        obj.setLine(rows+4, startColSlipValue, "", maxWidthSlipComp);
        obj.setLine(rows+5, startColSlipComp, employee.getFullName(), maxWidthSlipComp);
        obj.setLine(rows+5, startColSlipValue, "", maxWidthSlipComp);
        obj.setLine(rows+6, startColSlipComp, garis, maxWidthSlipComp);
        obj.setLine(rows+6, startColSlipValue, "", maxWidthSlipComp);
        obj.setLine(rows+7, startColSlipComp, employee.getEmployeeNum(), maxWidthSlipComp);
        obj.setLine(rows+7, startColSlipValue, "", maxWidthSlipComp);
        
        return output;
    }
    
    
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}
