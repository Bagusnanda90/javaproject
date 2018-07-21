/*
 * TransferListXLS.java
 *
 * Created on Januari 06, 2015, 2:27 PM
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
public class PayrollSummaryXlsDifferent extends HttpServlet {

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
    public PayrollSummaryXlsDifferent() {
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
        style2.setWrapText(true);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        
        HSSFCellStyle style6 = wb.createCellStyle();
        style6.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());//HSSFCellStyle.WHITE);
        style6.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style6.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        style6.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        style6.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        style6.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
  
        HSSFCellStyle styletitle = wb.createCellStyle();
        styletitle.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());//HSSFCellStyle.WHITE);
        styletitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styletitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styletitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styletitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        
        
        HSSFCellStyle style7 = wb.createCellStyle();
        style7.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());//HSSFCellStyle.WHITE);
        style7.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style7.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style7.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style7.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style7.setBorderTop(HSSFCellStyle.BORDER_THIN);
        
        HSSFCellStyle style8 = wb.createCellStyle();
        style8.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());//HSSFCellStyle.WHITE);
        style8.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style8.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style8.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style8.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style8.setBorderRight(HSSFCellStyle.BORDER_THIN);
        
        
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
        long periodIdbefore = PstPayPeriod.getPayPeriodIdJustBefore(periodId);
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

        PayPeriod objPeriodbefore = new PayPeriod();
        //update by satrya 2013-02-12
        //Period objPeriod = new Period();
        try {
            objPeriodbefore = PstPayPeriod.fetchExc(periodIdbefore);
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
        
        long periodeIdbefore = PstPayPeriod.getPayPeriodIdJustBefore(periodeId);
        
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
       
        Vector listEmpPaySlip = SessEmployee.listEmpPaySlipPerdepart(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign);
        Vector listEmpPaySlipBefore = new Vector();
        if (periodIdbefore!=0 && periodId!=0){
        listEmpPaySlipBefore = SessEmployee.listEmpPaySlipPerdepart(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeIdbefore, -1, bIncResign);
        }
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
        PaySlip paySlip2 = new PaySlip();
        
        
        //penambahan untuk periode before
        PayPeriod payPeriodbefore = new PayPeriod();
        // Period period = new Period();
        //update by satrya 2013-02-12
        String periodNamebefore = "";
        Date endDatePeriodbefore = new Date();
        try {
            payPeriodbefore = PstPayPeriod.fetchExc(periodeIdbefore);
            periodNamebefore = payPeriodbefore.getPeriod();
            endDatePeriodbefore = payPeriodbefore.getEndDate();
        } catch (Exception e) {
        }
        PayGeneral payGenbefore = new PayGeneral();
        Employee employeebefore = new Employee();
        PaySlip paySlipbefore = new PaySlip();
        PaySlip paySlip2before = new PaySlip();
        
       
        
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

        int colStartCompSal = HeaderCol;
        if (listBenefit != null) {
            for (int i = 0; i < listBenefit.size(); i++) {
                PayComponent payComponent = (PayComponent) listBenefit.get(i);
                salCompTable.put("" + payComponent.getCompName(), new Integer(HeaderCol++));
 
            }
        }
        
        if (listDeduction != null) {
            for (int i = 0; i < listDeduction.size(); i++) {
                PayComponent payComponent = (PayComponent) listDeduction.get(i);
                salCompTable.put("" + payComponent.getCompName(), new Integer(HeaderCol++));
                
            }
        }
        


        int listRow = 0;
        String tempcompany ="";
        String tempdivision ="";
        String tempdepartment ="";
        // priska menghitung jumlah perdepartmen
        Vector jbenefit = new Vector();
        Vector jdeduction = new Vector();
        Vector jtbenefit = new Vector();
        Vector jtdeduction = new Vector();
        double totDept = 0;
        double totall = 0;
        
        double xtotalbenefit = 0 ;
        double xtotalbenefitbefore = 0 ;
        double xtotaldeduction = 0;
        double xtotaldeductionbefore = 0;
        
        int ipnew = 0; 
        
        
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
       
           //menghitung jumlah benefit
                        int select = 0 ;
                        for (int i = 0; i < listPaySalary.size(); i++) {
                            Vector tempb = (Vector) listPaySalary.get(i);
                            PayComponent payComponentb = (PayComponent) tempb.get(0);
                            PaySlipComp paySlipCompb = (PaySlipComp) tempb.get(2);
                            Object objb = salCompTable.get("" + payComponentb.getCompName());
                            int colb = objb != null ? ((Integer) objb).intValue() : -1;
                            if (colb > -1) {
                            if (ip==0){
                               jbenefit.add(paySlipCompb.getCompValue());
                               jtbenefit.add(paySlipCompb.getCompValue());
                               select=select+1;
                            } else {
                               double total = Double.parseDouble(jbenefit.get(select).toString()); 
                               jbenefit.set(select,(total + paySlipCompb.getCompValue()) );
                               
                               double ttotal = Double.parseDouble(jtbenefit.get(select).toString()); 
                               jtbenefit.set(select,(ttotal + paySlipCompb.getCompValue()) );
                               select=select+1;
                            }    
                                
                            }
                        }
            
       int selectdec = 0 ;                 
       String outputd = "";
       String codeTax_countd="TAX_CON";
       String codePph21d ="PPH21";
       double nTaxCountd=0.0d;
        for (int i = 0; i < listPayDeduction.size(); i++) {
            Vector tempd = (Vector) listPayDeduction.get(i);
            Vector vectTokend = new Vector(1, 1);
            PayComponent payComponentd = (PayComponent) tempd.get(0);
            PaySlipComp paySlipCompd = (PaySlipComp) tempd.get(2);
           
            Object objd = salCompTable.get("" + payComponentd.getCompName());
            int cold = objd != null ? ((Integer) objd).intValue() : -1;
            if (cold > -1) {
             if (ip==0){
                //  cell = row.createCell((short) col++);
                if(codeTax_countd.equalsIgnoreCase(payComponentd.getCompCode())){
                    jdeduction.add(paySlipCompd.getCompValue());
                    jtdeduction.add(paySlipCompd.getCompValue());
                               selectdec=selectdec+1;
                }
               
                else if(codePph21d.equalsIgnoreCase(payComponentd.getCompCode())){
                    if(nTaxCountd!=0){
                        jdeduction.add(0);
                        jtdeduction.add(0);
                               selectdec=selectdec+1;
                    }else{
                        jdeduction.add(paySlipCompd.getCompValue());
                        jtdeduction.add(paySlipCompd.getCompValue());
                               selectdec=selectdec+1;
                    }
                    
                }else{
                    jdeduction.add(paySlipCompd.getCompValue());
                    jtdeduction.add(paySlipCompd.getCompValue());
                        selectdec=selectdec+1;
                }
            } else {
                if(codeTax_countd.equalsIgnoreCase(payComponentd.getCompCode())){
                    
                   double total = Double.parseDouble(jdeduction.get(selectdec).toString()); 
                   jdeduction.set(selectdec,(total + paySlipCompd.getCompValue()) );
                   
                   double ttotal = Double.parseDouble(jtdeduction.get(selectdec).toString()); 
                   jtdeduction.set(selectdec,(ttotal + paySlipCompd.getCompValue()) );
                   selectdec=selectdec+1;
                               
                               
                }
               
                else if(codePph21d.equalsIgnoreCase(payComponentd.getCompCode())){
                    if(nTaxCountd!=0){
                       double total = Double.parseDouble(jdeduction.get(selectdec).toString()); 
                       jdeduction.set(selectdec,(total + 0) );
                       
                       double ttotal = Double.parseDouble(jtdeduction.get(selectdec).toString()); 
                       jtdeduction.set(selectdec,(ttotal + 0) );
                       selectdec=selectdec+1;
                    }else{
                       double total = Double.parseDouble(jdeduction.get(selectdec).toString()); 
                       jdeduction.set(selectdec,(total + paySlipCompd.getCompValue()) );
                       
                       double ttotal = Double.parseDouble(jtdeduction.get(selectdec).toString()); 
                       jtdeduction.set(selectdec,(ttotal + paySlipCompd.getCompValue()) );
                       selectdec=selectdec+1;
                    }
                    
                }else{
                    //total perdepartement
                       double total = Double.parseDouble(jdeduction.get(selectdec).toString()); 
                       jdeduction.set(selectdec,(total + paySlipCompd.getCompValue()) );
                       
                       //total semua
                       double ttotal = Double.parseDouble(jtdeduction.get(selectdec).toString()); 
                       jtdeduction.set(selectdec,(ttotal + paySlipCompd.getCompValue()) );
                       selectdec=selectdec+1;
                }  
                 
            }
              
            }
        }
                   
        
                        // cari total benefit
                        double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_BENEFIT, paySlipId, keyPeriod,payGroupId);
                        //System.out.println("totalBenefit..............."+totalBenefit);
                        double totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_DEDUCTION, paySlipId, keyPeriod,payGroupId);

                        // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                        double totTakHomePay = totalBenefit - totalDeduction;
                        
                        totDept = totDept + totTakHomePay ;
                        totall = totall+totTakHomePay;
                        
                        xtotalbenefit = xtotalbenefit + totalBenefit;
                        xtotaldeduction = xtotaldeduction + totalDeduction;
       
            
        }
        
    

        
           //menambahkan before periode

        // priska menghitung jumlah perdepartmen
        Vector jtbenefitbefore = new Vector();
        Vector jtdeductionbefore = new Vector();
        double totDeptbefore = 0;
        double totallbefore = 0;
        int ipnewbefore = 0; 
        
     
        for (int ip = 0; ip < listEmpPaySlipBefore.size(); ip++) {
            Vector vlst = (Vector) listEmpPaySlipBefore.get(ip);
            Employee objEmployee = (Employee) vlst.get(0);
            PayEmpLevel objPayEmpLevel = (PayEmpLevel) vlst.get(1);
            PaySlip objPaySlip = (PaySlip) vlst.get(2);

            long employeeId = objEmployee.getOID();
            String salaryLevel = objPayEmpLevel.getLevelCode();
            long paySlipId = objPaySlip.getOID();

            Vector listPaySalary = PstSalaryLevelDetail.listPaySlip(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_BENEFIT, paySlipId,keyPeriod, printZeroValue,payGroupId);
            Vector listPayDeduction = PstSalaryLevelDetail.listPaySlip(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_DEDUCTION, paySlipId, keyPeriod, printZeroValue,payGroupId);

            // get the data for employee who have pay slip
            Vector empSlip = PstPaySlip.getEmpSlip(periodeIdbefore, paySlipId);
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
       
           //menghitung jumlah benefit
                        int select = 0 ;
                        for (int i = 0; i < listPaySalary.size(); i++) {
                            Vector tempb = (Vector) listPaySalary.get(i);
                            PayComponent payComponentb = (PayComponent) tempb.get(0);
                            PaySlipComp paySlipCompb = (PaySlipComp) tempb.get(2);
                            Object objb = salCompTable.get("" + payComponentb.getCompName());
                            int colb = objb != null ? ((Integer) objb).intValue() : -1;
                            if (colb > -1) {
                            if (ip==0){
                               jtbenefitbefore.add(paySlipCompb.getCompValue());
                               select=select+1;
                            } else {
                               double ttotal = Double.parseDouble(jtbenefitbefore.get(select).toString()); 
                               jtbenefitbefore.set(select,(ttotal + paySlipCompb.getCompValue()) );
                               select=select+1;
                            }    
                                
                            }
                        }
            
       int selectdec = 0 ;                 
       String outputd = "";
       String codeTax_countd="TAX_CON";
       String codePph21d ="PPH21";
       double nTaxCountd=0.0d;
        for (int i = 0; i < listPayDeduction.size(); i++) {
            Vector tempd = (Vector) listPayDeduction.get(i);
            Vector vectTokend = new Vector(1, 1);
            PayComponent payComponentd = (PayComponent) tempd.get(0);
            PaySlipComp paySlipCompd = (PaySlipComp) tempd.get(2);
           
            Object objd = salCompTable.get("" + payComponentd.getCompName());
            int cold = objd != null ? ((Integer) objd).intValue() : -1;
            if (cold > -1) {
             if (ip==0){
                //  cell = row.createCell((short) col++);
                if(codeTax_countd.equalsIgnoreCase(payComponentd.getCompCode())){
                    jtdeductionbefore.add(paySlipCompd.getCompValue());
                               selectdec=selectdec+1;
                }
               
                else if(codePph21d.equalsIgnoreCase(payComponentd.getCompCode())){
                    if(nTaxCountd!=0){
                        jtdeductionbefore.add(0);
                               selectdec=selectdec+1;
                    }else{
                        jtdeductionbefore.add(paySlipCompd.getCompValue());
                               selectdec=selectdec+1;
                    }
                    
                }else{
                    jtdeductionbefore.add(paySlipCompd.getCompValue());
                        selectdec=selectdec+1;
                }
            } else {
                if(codeTax_countd.equalsIgnoreCase(payComponentd.getCompCode())){
                   double ttotal = Double.parseDouble(jtdeductionbefore.get(selectdec).toString()); 
                   jtdeductionbefore.set(selectdec,(ttotal + paySlipCompd.getCompValue()) );
                   selectdec=selectdec+1;
                               
                               
                }
               
                else if(codePph21d.equalsIgnoreCase(payComponentd.getCompCode())){
                    if(nTaxCountd!=0){
                       double ttotal = Double.parseDouble(jtdeductionbefore.get(selectdec).toString()); 
                       jtdeductionbefore.set(selectdec,(ttotal + 0) );
                       selectdec=selectdec+1;
                    }else{
                       double ttotal = Double.parseDouble(jtdeductionbefore.get(selectdec).toString()); 
                       jtdeductionbefore.set(selectdec,(ttotal + paySlipCompd.getCompValue()) );
                       selectdec=selectdec+1;
                    }
                    
                }else{
                       //total semua
                       double ttotal = Double.parseDouble(jtdeductionbefore.get(selectdec).toString()); 
                       jtdeductionbefore.set(selectdec,(ttotal + paySlipCompd.getCompValue()) );
                       selectdec=selectdec+1;
                }  
                 
            }
              
            }
        }
                   
        
                        // cari total benefit
                        double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_BENEFIT, paySlipId, keyPeriod,payGroupId);
                        //System.out.println("totalBenefit..............."+totalBenefit);
                        double totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE, salaryLevel, PstPayComponent.TYPE_DEDUCTION, paySlipId, keyPeriod,payGroupId);

                        // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                        double totTakHomePay = totalBenefit - totalDeduction;
                        
                        totDeptbefore = totDeptbefore + totTakHomePay ;
                        totallbefore = totallbefore+totTakHomePay;       
                        
                        xtotalbenefitbefore = xtotalbenefitbefore + totalBenefit ; 
                        xtotaldeductionbefore = xtotaldeductionbefore + totalDeduction ; 
        }
        
  
        row = sheet.createRow((short) (3));

        cell = row.createCell((short) 2);
        cell.setCellValue(" NILAI ");
        cell.setCellStyle(style6);

        cell = row.createCell((short) 6);
        cell.setCellValue(" Head Count " );
        cell.setCellStyle(style6);
        

        row = sheet.createRow((short) (4));

        cell = row.createCell((short) 0);
        cell.setCellValue(" Component Name ");
        cell.setCellStyle(style6);

        cell = row.createCell((short) 1);
        cell.setCellValue("" + periodName );
        cell.setCellStyle(style6);
          
        cell = row.createCell((short) 2);
        cell.setCellValue("" + payPeriodbefore.getPeriod());
        cell.setCellStyle(style6);
        
        cell = row.createCell((short) 3);
        cell.setCellValue(" Varian " );
        cell.setCellStyle(style6);
        
        cell = row.createCell((short) 5);
        cell.setCellValue("" + periodName );
        cell.setCellStyle(style6);
        
        cell = row.createCell((short) 6);
        cell.setCellValue("" + payPeriodbefore.getPeriod() );
        cell.setCellStyle(style6);
        
        cell = row.createCell((short) 7);
        cell.setCellValue(" Varian ");
        cell.setCellStyle(style6);
        
        cell = row.createCell((short) 9);
        cell.setCellValue(" KETERANGAN ");
        cell.setCellStyle(style6);
        
        int nn = 5 ;
        
        
            // if(jtbenefitbefore!=null && jtbenefitbefore.size() > 0) {
            //            for (int i = 0; i < jtbenefitbefore.size(); i++) {
            if (listBenefit != null) {
                            for (int i = 0; i < listBenefit.size(); i++) {
  
                            PayComponent payComponent = (PayComponent) listBenefit.get(i);
                            double nilai = Double.valueOf(jtbenefit.get(i).toString());
                            double nilailalu = 0;
                            if (jtbenefitbefore != null && jtbenefitbefore.size() > 0){
                              nilailalu = Double.valueOf(jtbenefitbefore.get(i).toString());
                            }
                             
                            row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 2);
                            cell.setCellValue(nilailalu);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue(nilai);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 0);
                            cell.setCellValue(payComponent.getCompName());
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue(Math.abs(nilai-nilailalu));
                            cell.setCellStyle(formatRupiah);
                            
                            if (i==0){
                            cell = row.createCell((short) 5);
                            cell.setCellValue(listEmpPaySlip.size());
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 6);
                            cell.setCellValue(listEmpPaySlipBefore.size());
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 7);
                            cell.setCellValue(Math.abs(listEmpPaySlipBefore.size()-listEmpPaySlip.size()));
                            cell.setCellStyle(style6);
                            }
                            
                            cell = row.createCell((short) 9);
                            cell.setCellValue(" ");
                            cell.setCellStyle(style6);
                            
                            }
                        }
                        
            row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 0);
                            cell.setCellValue("TOTAL BENEFIT");
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue(xtotalbenefit);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 2);
                            cell.setCellValue(xtotalbenefitbefore);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue(Math.abs(xtotalbenefit-xtotalbenefitbefore));
                            cell.setCellStyle(formatRupiah);
            
             if (listDeduction != null) {
                            for (int i = 0; i < listDeduction.size(); i++) {
                            PayComponent payComponent = (PayComponent) listDeduction.get(i);
                            
                            double nilai = Double.valueOf(jtdeduction.get(i).toString());
                            
                            double nilailalu = 0;
                            if (jtdeductionbefore != null && jtdeductionbefore.size() > 0){
                              nilailalu  = Double.valueOf(jtdeductionbefore.get(i).toString());
                            }
                            
                            row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 2);
                            cell.setCellValue(nilailalu);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue(nilai);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 0);
                            cell.setCellValue(payComponent.getCompName());
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue(Math.abs(nilai-nilailalu));
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 9);
                            cell.setCellValue(" ");
                            cell.setCellStyle(style6);
                            }
                        }
        
                         row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 0);
                            cell.setCellValue("GAJI NETTO");
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue(xtotaldeduction);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 2);
                            cell.setCellValue(xtotaldeductionbefore);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue(Math.abs(xtotaldeduction-xtotaldeductionbefore));
                            cell.setCellStyle(formatRupiah);

                        row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 0);
                            cell.setCellValue("GAJI NETTO");
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue(xtotalbenefit - xtotaldeduction);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 2);
                            cell.setCellValue(xtotalbenefitbefore - xtotaldeductionbefore);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue(Math.abs((xtotalbenefit - xtotaldeduction)-(xtotalbenefitbefore - xtotaldeductionbefore)));
                            cell.setCellStyle(formatRupiah);
                            
          try {

              nn++;
                        listRow = nn++;

                        row = sheet.createRow((short) listRow);
                        int col = 0;
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("HR UNIT");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("ACC UNIT");
                        cell.setCellStyle(style6);
                       
                        cell = row.createCell((short) col++);
                        cell.setCellValue("PIMPINAN UNIT");
                        cell.setCellStyle(style6);
                                           
                        cell = row.createCell((short) col++);
                        cell.setCellValue("HR CORPERATE");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("DIRECTOR");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("PRESDIR & CEO");
                        cell.setCellStyle(style6);

                        col++;
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" TOTAL GAJI (GAJI CASH-GAJI NETTO)) ");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                   
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                        
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
         
          
          
              try {


                        listRow = nn++;

                        row = sheet.createRow((short) listRow);
                        int col = 0;
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                       
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                                           
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        
                        col++;
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" Transfer Ke koperasi");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                             
                        cell = row.createCell((short) col++);
                        cell.setCellValue("  ");
                        cell.setCellStyle(style6);
                        
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
          
              
               try {


                        listRow = nn++;

                        row = sheet.createRow((short) listRow);
                        int col = 0;
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                       
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                                           
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        col++;
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" Total Transfer ke BCA  ");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                                           
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
              
           try {


                        listRow = nn++;

                        row = sheet.createRow((short) listRow);
                        int col = 0;
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                       
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                                           
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        col++;
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" Total Gaji Cash ");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);    
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                        
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
           
             try {


                        listRow = nn++;

                        row = sheet.createRow((short) listRow);
                        int col = 0;
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style7);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style7);
                       
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style7);
                                           
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style7);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style7);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style7);

                       col++;
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" Masuk Rekening Payroll ");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
             
             try {


                        listRow = nn++;

                        row = sheet.createRow((short) listRow);
                        int col = 0;
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style8);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style8);
                       
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style8);
                                           
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style8);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style8);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style8);

                       col++;
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" Potongan Lain Lain ");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                        
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
             
             try {


                        listRow = nn++;

                        row = sheet.createRow((short) listRow);
                        int col = 0;
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                       
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
                                           
                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);

                        cell = row.createCell((short) col++);
                        cell.setCellValue("");
                        cell.setCellStyle(style6);
      
                        col++;
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" Grand Total ");
                        cell.setCellStyle(style);
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                        
                        cell = row.createCell((short) col++);
                        cell.setCellValue(" ");
                        cell.setCellStyle(style6);
                        
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }               
                        
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.flush();
        sos.close();

        //out.flush();
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
