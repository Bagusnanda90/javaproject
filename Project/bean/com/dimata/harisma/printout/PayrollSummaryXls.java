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

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

import com.dimata.qdep.form.*;
import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.overtime.PstOvertime;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.session.admin.SessUserSession;
import com.dimata.harisma.session.employee.SessEmployee;
import com.dimata.system.entity.system.PstSystemProperty;
import java.io.PrintWriter;
import com.dimata.harisma.form.payroll.*;

/**
 *
 * @author Yunny
 */
public class PayrollSummaryXls extends HttpServlet {

    private static int pageLength = 29; // maximum row per slip
    private static int pageWidth = 80; // maximum lebar per slip
    private static int leftMargin = 2; // margin di kiri
    private static int maxLeftgSiteLength = 48; // maximum lebar bagian kiri slip spt untuk company dan data karyawan
    private static int startCompany = 1; // row mulai print company
    private static int startColHeaderValue = 14;
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
    public PayrollSummaryXls() {
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

        System.out.println("---===| Excel Payroll Summary Report |===---");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Employee List");

        String frmCurrency = "#,###";
        String mataUang="Rp";
        CurrencyType currencyType = new CurrencyType();
        try{
            Vector currType = PstCurrencyType.list(0, 1, PstCurrencyType.fieldNames[PstCurrencyType.FLD_INCLUDE_INF_PROCESS]+"="+PstCurrencyType.YES_USED, "");
            if(currType!=null && currType.size()>0){
                currencyType =(CurrencyType)currType.get(0);
                frmCurrency = currencyType.getFormatCurrency();
                mataUang = currencyType.getCode()==null ||  currencyType.getCode().length()==0?"Rp": currencyType.getCode();
                if(mataUang!=null && mataUang.equalsIgnoreCase("USD")){
                    mataUang = mataUang.replace("USD", "$");
                    mataUang = mataUang.replace("usd", "$");
                    mataUang = mataUang.replace("Usd", "$");
                    mataUang = mataUang.replace("USd", "$");
                    mataUang = mataUang.replace("uSd", "$");
                    mataUang = mataUang.replace("uSD", "$");
                    mataUang = mataUang.replace("usD", "$");
                }
            }
        }catch(Exception exc){
        
        }
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);

        //update by devin 2014-02-14
        HSSFCellStyle formatRupiah = wb.createCellStyle();
        HSSFDataFormat dfs = wb.createDataFormat();
        String format = "_("+mataUang+"* #,##0.00_);_("+mataUang+"* (#,##0.00);_("+mataUang+"* \"-\"??_);_(@_)";
        formatRupiah.setDataFormat(dfs.getFormat(format));//
        formatRupiah.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        formatRupiah.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        formatRupiah.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        formatRupiah.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        formatRupiah.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        formatRupiah.setBorderRight(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("EMPLOYEE PAYROLL LIST");
        cell.setCellStyle(style3);

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
            System.out.println(" >>> Exception during check login");
            System.out.flush();
            return;
        }

        boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
        boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));

        if (!privView && !privPrint) {
            System.out.println(" >>> You have no privileges");
            System.out.flush();
            return;
        }


        int detailSlipYN = FRMQueryString.requestInt(request, "detail_slip");
        int iCommand = FRMQueryString.requestCommand(request);
        long periodId = FRMQueryString.requestLong(request, "periodId");
        int keyPeriod = FRMQueryString.requestInt(request, "paySlipPeriod");
                 //update by satrya 2013-01-24
        long payGroupId = FRMQueryString.requestLong(request,"payGroupId");
        //String frmCurrency = "#,###";

        boolean printZeroValue = true;
        String sprintZeroValue = PstSystemProperty.getValueByName("PAYROLL_PRINT_ZERO_VALUE");
        if (sprintZeroValue == null || sprintZeroValue.length() == 0 || sprintZeroValue.equalsIgnoreCase("YES")
                || sprintZeroValue.equalsIgnoreCase("1") || sprintZeroValue.equalsIgnoreCase("Not initialized")) {
            printZeroValue = true;
        } else {
            printZeroValue = false;
        }


        PayPeriod objPeriod = new PayPeriod();
        //update by satrya 2013-02-12
        //Period objPeriod = new Period();
        try {
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

        Vector listEmpPaySlip = SessEmployee.listEmpPaySlip(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign,0);
//        String s_employee_id = null;
//        String s_payslip_id = null;
//        String s_level_code = null;
        long oidEmployee = 0;
        //update by satrya 2013-01-14
        if (aksiCommand == 1) {
            System.out.println("print selected");
            Vector listEmpId = new Vector();
            for (int i = 0; i < listEmpPaySlip.size(); i++) {
              
                try {
                    oidEmployee = FRMQueryString.requestLong(request, "print" + i + ""); // row yang dicheked
                } catch (Exception e) {
                    System.out.println("Exception" + e);
                }
                //System.out.println("nilai statusSave"+statusSave);
                //update by satrya 2013-01-14
                if (oidEmployee != 0) {
                    listEmpId.add(oidEmployee);
                }
            }
            if (listEmpId != null && listEmpId.size() > 0) {
                listEmpPaySlip = SessEmployee.listEmpPaySlipByEmployeeId(listEmpId, periodeId, -1, bIncResign);
            }
        }


        int MODEL_PAYSLIP = PaySlip.MODEL_UP_DOWN;
        try {
            String strSlip = PstSystemProperty.getValueByName("MODEL PAYSLIP");
            if (strSlip != null) {
                MODEL_PAYSLIP = Integer.parseInt(strSlip);
            }
        } catch (Exception exc) {
            System.out.println("Exception"+exc);
        }

        PayPeriod payPeriod = new PayPeriod();
        // Period period = new Period();
        //update by satrya 2013-02-12
        String periodName = "";
        Date endDatePeriod = new Date();
        try {
            payPeriod = PstPayPeriod.fetchExc(periodId);
            periodName = payPeriod.getPeriod();
            endDatePeriod = payPeriod.getEndDate();
        } catch (Exception e) {
        }
        PayGeneral payGen = new PayGeneral();
        Employee employee = new Employee();
        PaySlip paySlip = new PaySlip();

        row = sheet.createRow((short) (1));

        cell = row.createCell((short) 0);
        cell.setCellValue("Period ");
        cell.setCellStyle(style3);

        cell = row.createCell((short) 1);
        cell.setCellValue("" + periodName + (detailSlipYN == 1 ? " / " + Formater.formatTimeLocale(endDatePeriod, "dd-MM-yyyy") : ""));
        cell.setCellStyle(style3);

        String whereClauseBenefit = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + "=" + PstPayComponent.TYPE_BENEFIT +" AND "+ PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID] + "=" + payGroupId+" AND "+ PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS] + " > " + PstPayComponent.NO_SHOW_IN_REPORTS;
        String whereClauseDeduction = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + "=" + PstPayComponent.TYPE_DEDUCTION +" AND "+ PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID] + "=" + payGroupId+" AND "+ PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS] + " > " + PstPayComponent.NO_SHOW_IN_REPORTS;
        String orderClauseComp = " SORT_IDX ";
        Vector listBenefit = PstPayComponent.list(0, 100, whereClauseBenefit, orderClauseComp);
        Vector listDeduction = PstPayComponent.list(0, 100, whereClauseDeduction, orderClauseComp);
        Hashtable salCompTable = new Hashtable();


        int HeaderCol = 0;

        row = sheet.createRow((short) (2));

        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("No.");
        cell.setCellStyle(style);

        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Company");
        cell.setCellStyle(style);

        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Payroll Nr.");
        cell.setCellStyle(style);

        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Fullname");
        cell.setCellStyle(style);

        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Division");
        cell.setCellStyle(style);

        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Department");
        cell.setCellStyle(style);
        /*
        * What's change? add section
        * Date : 20150414
        * Author : Hendra McHen
        */
        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Section");
        cell.setCellStyle(style);

        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Commencing");
        cell.setCellStyle(style);

        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Bank Name");
        cell.setCellStyle(style);

        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Bank Account");
        cell.setCellStyle(style);

        cell = row.createCell((short) HeaderCol);
        cell.setCellValue("Note");
        cell.setCellStyle(style);



        int colStartCompSal = HeaderCol;
        /*
             * What's change? if (payComponent.getShowpayslip()>0)
             * Date : 20150414
             * Author : Hendra McHen
             */
        if (listBenefit != null) {
            for (int i = 0; i < listBenefit.size(); i++) {
                PayComponent payComponent = (PayComponent) listBenefit.get(i);
                if (payComponent.getShowpayslip()>0){
                    salCompTable.put("" + payComponent.getCompName(), new Integer(HeaderCol++));
                    cell = row.createCell((short) HeaderCol);
                    cell.setCellValue("" + payComponent.getCompName());
                    cell.setCellStyle(style);
                }
            }
        }
        if (listDeduction != null) {
            for (int i = 0; i < listDeduction.size(); i++) {
                PayComponent payComponent = (PayComponent) listDeduction.get(i);
                if (payComponent.getShowpayslip() > 0){
                    salCompTable.put("" + payComponent.getCompName(), new Integer(HeaderCol++));
                    cell = row.createCell((short) HeaderCol);
                    cell.setCellValue("" + payComponent.getCompName());
                    cell.setCellStyle(style);
                }
            }
        }
        HeaderCol++;
        cell = row.createCell((short) HeaderCol++);
        cell.setCellValue("Total");
        cell.setCellStyle(style);


        int listRow = 0;

        for (int ip = 0; ip < listEmpPaySlip.size(); ip++) {
            Vector vlst = (Vector) listEmpPaySlip.get(ip);
            Employee objEmployee = (Employee) vlst.get(0);
            PayEmpLevel objPayEmpLevel = (PayEmpLevel) vlst.get(1);
            PaySlip objPaySlip = (PaySlip) vlst.get(2);

            long employeeId = objEmployee.getOID();
            String salaryLevel = objPayEmpLevel.getLevelCode();
            long paySlipId = objPaySlip.getOID();

            Vector listPaySalary = PstSalaryLevelDetail.listPaySlip(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_BENEFIT, paySlipId,keyPeriod, printZeroValue,payGroupId);
            Vector listPayDeduction = PstSalaryLevelDetail.listPaySlip(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_DEDUCTION, paySlipId, keyPeriod, printZeroValue,payGroupId);

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
             String bankAcc="";
            String bankNameX="";
            
            Date commDate = new Date();
            //System.out.println("empSlip.size()"+empSlip.size());
            if ((empSlip != null) && empSlip.size() > 0) {
                employee = (Employee) empSlip.get(0);
                paySlip = (PaySlip) empSlip.get(1);
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
            note = paySlip.getNote();
            //update by satrya 20130203
            bankAcc=objPayEmpLevel.getBankAccNr();
            bankNameX=objPaySlip.getBankName();
            if (employee != null && (employee.getCompanyId() != payGen.getOID() || payGen.getOID() == 0) && employee.getCompanyId() != 0) {
                String whereGen = "" + PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID] + "=" + employee.getCompanyId();
                Vector vectCity = PstPayGeneral.list(0, 0, whereGen, "");
                if (vectCity != null && vectCity.size() > 0) {
                    payGen = (PayGeneral) vectCity.get(0);
                }
            }

            switch (MODEL_PAYSLIP) {

                case PaySlip.MODEL_UP_DOWN:
                    try {


                        listRow = ip + 3;

                        row = sheet.createRow((short) listRow);
                        int col = 0;

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + (ip + 1));
                        cell.setCellStyle(style2);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + payGen.getCompanyName());
                        cell.setCellStyle(style2);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + employeeNum);
                        cell.setCellStyle(style2);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + employeeName);
                        cell.setCellStyle(style2);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + division);
                        cell.setCellStyle(style2);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + department);
                        cell.setCellStyle(style2);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + section);
                        cell.setCellStyle(style2);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + Formater.formatTimeLocale(commDate, "dd-MM-yyyy"));
                        cell.setCellStyle(style2);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + bankNameX);
                        cell.setCellStyle(style2);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + bankAcc);
                        cell.setCellStyle(style2);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("" + note);
                        cell.setCellStyle(style2);

                        //update by devin 2014-02-14
                        //col = drawListBenefit(salCompTable, row, col, style2, listPaySalary, employeeId, periodId, paySlipId);
                        col = drawListBenefit(salCompTable, row, col, formatRupiah, listPaySalary, employeeId, periodId, paySlipId);
                        col = drawListDeduction(salCompTable, row, col, formatRupiah, listPayDeduction, employeeId, periodId, paySlipId);
                         //update by devin 2014-02-14
                        //col = drawListDeduction(salCompTable, row, col, style2, listPayDeduction, employeeId, periodId, paySlipId);

                        // cari total benefit
                        double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_BENEFIT, paySlipId, keyPeriod,payGroupId);
                        //System.out.println("totalBenefit..............."+totalBenefit);
                        double totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_DEDUCTION, paySlipId, keyPeriod,payGroupId);

                        // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                        double totTakHomePay = totalBenefit - totalDeduction;

                        cell = row.createCell((short) col++);
                        cell.setCellValue(totTakHomePay);//"" + Formater.formatNumber(totTakHomePay, frmCurrency));
                        //UPDATE BY DEVIN 2014-02-14
                        cell.setCellStyle(formatRupiah);

                        cell.setCellFormula(( (""+totalBenefit) +"-"+ (""+totalDeduction))); 

                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
                    break;
                default:
                    System.out.println("No settting for default format ");
            }
        }

        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.flush();
        sos.close();

        //out.flush();
    }
//update by devin 2014-02-14
    //public int drawListBenefit(Hashtable salCompTable, HSSFRow row, int startCol, HSSFCellStyle style2, Vector objectClass, long employeeId, long periodId, long paySlipId) {
    public int drawListBenefit(Hashtable salCompTable, HSSFRow row, int startCol, HSSFCellStyle formatRupiah, Vector objectClass, long employeeId, long periodId, long paySlipId) {
        String output = "";
        //String frmCurrency = "#,###";
        for (int i = 0; i < objectClass.size(); i++) {
            Vector temp = (Vector) objectClass.get(i);
            PayComponent payComponent = (PayComponent) temp.get(0);
            PaySlipComp paySlipComp = (PaySlipComp) temp.get(2);
            //Object obj = salCompTable.get("" + payComponent.getCompName());
            //int col = obj != null ? ((Integer) obj).intValue() : -1;
            /*
             * What's change? if (payComponent.getShowpayslip()>0)
             * Date : 20150414
             * Author : Hendra McHen
             */
            if (payComponent.getShowpayslip()>0) {
                HSSFCell cell = row.createCell((short) startCol++);
                cell.setCellValue(paySlipComp.getCompValue());//"" + Formater.formatNumber(paySlipComp.getCompValue(), frmCurrency));
                //update by devin 2014-02-14
                // cell.setCellStyle(style2);
                cell.setCellStyle(formatRupiah);
            }
        }
        return startCol;
    }
    //update by devin 2014-02-14
    //public int drawListDeduction(Hashtable salCompTable, HSSFRow row, int startCol, HSSFCellStyle style2, Vector objectClass, long employeeId, long periodId, long paySlipId)
    public int drawListDeduction(Hashtable salCompTable, HSSFRow row, int startCol, HSSFCellStyle formatRupiah, Vector objectClass, long employeeId, long periodId, long paySlipId) {
        String output = "";
        //update by satrya 2013-02-07
       // String frmCurrency = "#,###";
       String codeTax_count="TAX_CON";
       String codePph21 ="PPH21";
       double nTaxCount=0.0d;
        for (int i = 0; i < objectClass.size(); i++) {
            Vector temp = (Vector) objectClass.get(i);
            Vector vectToken = new Vector(1, 1);
            PayComponent payComponent = (PayComponent) temp.get(0);
            PaySlipComp paySlipComp = (PaySlipComp) temp.get(2);
           
            //Object obj = salCompTable.get("" + payComponent.getCompName());
            ///int col = obj != null ? ((Integer) obj).intValue() : -1;
            /*
             * What's change? if (payComponent.getShowpayslip()>0)
             * Date : 20150414
             * Author : Hendra McHen
             */
            if (payComponent.getShowpayslip()>0) {
                HSSFCell cell = row.createCell((short) startCol++);
                if(codeTax_count.equalsIgnoreCase(payComponent.getCompCode())){
                    nTaxCount= paySlipComp.getCompValue();
                    cell.setCellValue(nTaxCount);
                }
               
                else if(codePph21.equalsIgnoreCase(payComponent.getCompCode())){
                    if(nTaxCount!=0){
                        cell.setCellValue(0);
                    }else{
                        cell.setCellValue(paySlipComp.getCompValue());
                    }
                    
                }else{
                cell.setCellValue(paySlipComp.getCompValue());//"" + Formater.formatNumber(paySlipComp.getCompValue(), frmCurrency));
                }
                 //update by devin 2014-02-14
                // cell.setCellStyle(style2);
                cell.setCellStyle(formatRupiah);
            }
        }
        return startCol;
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
