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
public class PayrollSummaryXlsRekonsiliasiGaji extends HttpServlet {

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
    public PayrollSummaryXlsRekonsiliasiGaji() {
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
        formatRupiah.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        formatRupiah.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        formatRupiah.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        formatRupiah.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);

        
        
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
        
        HSSFCellStyle styleAtas = wb.createCellStyle();
        styleAtas.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleAtas.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleAtas.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleAtas.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleBawah = wb.createCellStyle();
        styleBawah.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleBawah.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleBawah.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleBawah.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleKanan = wb.createCellStyle();
        styleKanan.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKanan.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKanan.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleKanan.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleKiri = wb.createCellStyle();
        styleKiri.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKiri.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKiri.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleKiri.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleKiriAtas = wb.createCellStyle();
        styleKiriAtas.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKiriAtas.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKiriAtas.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleKiriAtas.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleKiriAtas.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleKiriBawah = wb.createCellStyle();
        styleKiriBawah.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKiriBawah.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKiriBawah.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleKiriBawah.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleKiriBawah.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleKananBawah = wb.createCellStyle();
        styleKananBawah.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKananBawah.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKananBawah.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleKananBawah.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleKananBawah.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleKananAtas = wb.createCellStyle();
        styleKananAtas.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKananAtas.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKananAtas.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleKananAtas.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleKananAtas.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleKananFull = wb.createCellStyle();
        styleKananFull.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKananFull.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKananFull.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleKananFull.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleKananFull.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        styleKananFull.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        
         HSSFCellStyle styleKiriFull = wb.createCellStyle();
        styleKiriFull.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKiriFull.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKiriFull.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleKiriFull.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleKiriFull.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        styleKiriFull.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleAtasBawah = wb.createCellStyle();
        styleAtasBawah.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleAtasBawah.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleAtasBawah.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleAtasBawah.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleAtasBawah.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        
        HSSFCellStyle styleKosong = wb.createCellStyle();
        styleKosong.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKosong.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleKosong.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        
        //update by priska 2015-03-14
        HSSFCellStyle formatRupiahKiriKosong = wb.createCellStyle();
        HSSFDataFormat dfsKK = wb.createDataFormat();
        String formatKK = "_("+mataUang+"* #,##0.00_);_("+mataUang+"* (#,##0.00);_("+mataUang+"* \"-\"??_);_(@_)";
        formatRupiahKiriKosong.setDataFormat(dfsKK.getFormat(formatKK));//
        formatRupiahKiriKosong.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        formatRupiahKiriKosong.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        formatRupiahKiriKosong.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        formatRupiahKiriKosong.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        
        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("REKONSILIASI GAJI");
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
        Vector listEmpPaySlipBefore = SessEmployee.listEmpPaySlipPerdepart(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeIdbefore, -1, bIncResign);
        
        if (listEmpPaySlipBefore == null){
            periodeIdbefore = periodId;
            listEmpPaySlipBefore = SessEmployee.listEmpPaySlipPerdepart(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeIdbefore, -1, bIncResign);
        
        }
        PayPeriod objPeriodbefore = new PayPeriod();
        //update by priska 2015-02-12
        //Period objPeriod = new Period();
        try {
            objPeriodbefore = PstPayPeriod.fetchExc(periodIdbefore);
        } catch (Exception exc) {
        }
        //priska 20150312
        //mencari employee yang tidak ada 
        Vector listEmpDif = SessEmployee.listEmpPaySlipDiff(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign);
        Vector listEmpDifBeforeV = SessEmployee.listEmpPaySlipDiff(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeIdbefore, -1, bIncResign);
        Hashtable listEmpDifBefore = SessEmployee.hashlistTblEmpDiff(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeIdbefore, -1, bIncResign);
        Vector gajilistEmpDif = new Vector(); 
        Vector gajilistEmpDifBefore = new Vector(); 
        int intEmpDIf = 0 ;
        Vector sesEm = new Vector();
        int in = 0 ;
        int jumlahemp = listEmpDif.size();
        for (int i = 0 ; i < jumlahemp; i++){
            Employee employeeDiff = (Employee)listEmpDif.get(i);
            if (listEmpDifBefore.containsKey(employeeDiff.getOID())){
                listEmpDifBefore.remove(employeeDiff.getOID());
              //  listEmpDif.remove(i);
                sesEm.add(employeeDiff.getOID());
            } 
           
        }
        
        for (int i = 0 ; i< sesEm.size(); i++ ){
            long empOid = Long.valueOf(sesEm.get(i).toString());
            for (int ii=0; ii<listEmpDif.size(); ii++){
                Employee employeeDiff1 = (Employee)listEmpDif.get(ii);
                if (employeeDiff1.getOID() == empOid){
                    listEmpDif.remove(ii);
                    ii--;
                }
            }
        }

        //priska 2015-03-09
        long propDailyworker = -1;
        try {
             propDailyworker = Long.parseLong(PstSystemProperty.getValueByName("OID_DAILYWORKER"));
        } catch (Exception ex) {
            System.out.println("Execption DAILY WORKER: " + ex);
        }
        long propGPokok = -1;
        try {
             propGPokok = Long.parseLong(PstSystemProperty.getValueByName("OID_GAJIPOKOK"));
        } catch (Exception ex) {
            System.out.println("Execption DAILY WORKER: " + ex);
        }
        PayComponent payComponent123 = new PayComponent();
        try{
            payComponent123 = PstPayComponent.fetchExc(propGPokok);
        } catch (Exception e) {
            System.out.print("properties gaji pokok tidak disetting");
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
        
        try{
         PayPeriod payPeriodBefore = PstPayPeriod.fetchExc(periodeIdbefore); 
        } catch (Exception e) {
            
        }
        
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
        int ipnew = 0; 
        
        Vector Vempcategory = PstEmpCategory.list(0, 0, null, null);
        Vector nilaicatperiode1 = new Vector();
        Vector nilaicatperiode2 = new Vector();
        for (int i = 0; i < Vempcategory.size(); i++ ){
            nilaicatperiode1.add(0);
            nilaicatperiode2.add(0);
        }
        
        double benefit1 = 0;
        double benefitbefore = 0;
        double gajiDw = 0 ;
        double gajiDwBefore = 0 ;
        
        for (int ip = 0; ip < listEmpPaySlip.size(); ip++) {
            Vector vlst = (Vector) listEmpPaySlip.get(ip);
            Employee objEmployee = (Employee) vlst.get(0);
            PayEmpLevel objPayEmpLevel = (PayEmpLevel) vlst.get(1);
            PaySlip objPaySlip = (PaySlip) vlst.get(2);
            
            //menghitung jumlah employee percategory
            long empcategoryoid = objEmployee.getEmpCategoryId();
            
            for (int i=0 ; i<Vempcategory.size(); i++){
                EmpCategory empCategory = (EmpCategory) Vempcategory.get(i);
                
                 if ((empcategoryoid == empCategory.getOID()) && (empcategoryoid > 0)){
                        long nilaibefore = Long.parseLong(nilaicatperiode1.get(i).toString()); 
                        nilaicatperiode1.set(i, (nilaibefore+1));
                 }
            }
            
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
                                if (objEmployee.getEmpCategoryId() == propDailyworker && payComponentb.getCompCode().equals(payComponent123.getCompCode())){
                                gajiDw = gajiDw +  paySlipCompb.getCompValue();
                                }
                                
                                //untuk mendapatkan gaji
                                if (payComponentb.getCompCode().equals(payComponent123.getCompCode())){
                                   if( listEmpDif.size() > 0 ){
                                    Employee employeeNn = (Employee)listEmpDif.get(intEmpDIf);
                                       if (objEmployee.getOID() == employeeNn.getOID()){
                                          gajilistEmpDif.add(paySlipCompb.getCompValue());
                                          if(intEmpDIf < gajilistEmpDif.size()){
                                              
                                       intEmpDIf++;
                                          }
                                       }
                                   }
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

                        benefit1 = benefit1 + totalBenefit;
                        
                        // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                        double totTakHomePay = totalBenefit - totalDeduction;
                        
                        totDept = totDept + totTakHomePay ;
                        totall = totall+totTakHomePay;
       
            
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

            
            //menghitung jumlah employee percategory
            long empcategoryoid = objEmployee.getEmpCategoryId();
            
            for (int i=0 ; i<Vempcategory.size(); i++){
                EmpCategory empCategory = (EmpCategory) Vempcategory.get(i);
                
                 if ((empcategoryoid == empCategory.getOID()) && (empcategoryoid > 0)){
                        long nilaibefore = Long.parseLong(nilaicatperiode2.get(i).toString()); 
                        nilaicatperiode2.set(i, (nilaibefore+1));
                 }
            }
            
            
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
                                
                            if (objEmployee.getEmpCategoryId() == propDailyworker && payComponentb.getCompCode().equals(payComponent123.getCompCode())){
                                gajiDwBefore = gajiDwBefore +  paySlipCompb.getCompValue();
                                }
                            //untuk mendapatkan gaji
                            if (payComponentb.getCompCode().equals(payComponent123.getCompCode())){
                               gajilistEmpDifBefore.add(paySlipCompb.getCompValue()); 
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

                        benefitbefore = benefitbefore + totalBenefit;
                        
                        // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                        double totTakHomePay = totalBenefit - totalDeduction;
                        
                        totDeptbefore = totDeptbefore + totTakHomePay ;
                        totallbefore = totallbefore+totTakHomePay;        
                        
                        
        }
        
  
        row = sheet.createRow((short) (3));

        cell = row.createCell((short) 2);
        cell.setCellStyle(styleKiriFull);
        
        cell = row.createCell((short) 3);
        cell.setCellValue(" Head Count " );
        cell.setCellStyle(style6);
        
        cell = row.createCell((short) 4);
        cell.setCellStyle(styleKananFull);
        

        row = sheet.createRow((short) (4));
        
        cell = row.createCell((short) 2);
        cell.setCellValue("" + periodName );
        cell.setCellStyle(styleKiriFull);
        
        cell = row.createCell((short) 3);
        cell.setCellValue("" + payPeriodbefore.getPeriod() );
        cell.setCellStyle(styleAtasBawah);
        
        cell = row.createCell((short) 4);
        cell.setCellValue(" Varian ");
        cell.setCellStyle(styleKananFull);
        
        
        int nn = 5 ;
        long totalvarian = 0;
                          
                        if(Vempcategory!=null && Vempcategory.size() > 0) {
                        for (int i = 0; i < Vempcategory.size(); i++) {
                            
                            EmpCategory empCategory = (EmpCategory) Vempcategory.get(i);
                            
                            long nilai1 = Long.valueOf(nilaicatperiode1.get(i).toString());
                            long nilai2 = Long.valueOf(nilaicatperiode2.get(i).toString());
                            
                            
                            row = sheet.createRow((short) (nn++));
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue(empCategory.getEmpCategory());
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 2);
                            cell.setCellValue(nilai1);
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue(nilai2);
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellValue(Math.abs(nilai2-nilai1));
                            cell.setCellStyle(style6);
                            
                            totalvarian = totalvarian + (Math.abs(nilai2-nilai1)) ;
                            }
                        }
                        
                        row = sheet.createRow((short) (nn++));
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue("TOTAL");
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 2);
                            cell.setCellValue(listEmpPaySlip.size());
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue(listEmpPaySlipBefore.size());
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellValue(totalvarian);
                            cell.setCellStyle(style6);
        
                    nn++;      
                    row = sheet.createRow((short) (nn++));
                            
                                                       
                            cell = row.createCell((short) 2);
                            cell.setCellValue("");
                            cell.setCellStyle(styleKiriFull);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue("NILAI RUPIAH");
                            cell.setCellStyle(styleAtasBawah);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellValue("");
                            cell.setCellStyle(styleKananFull);
        
                    row = sheet.createRow((short) (nn++));
                            
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue("");
                            cell.setCellStyle(style6);
                    
                            cell = row.createCell((short) 2);
                            cell.setCellValue(""+periodName);
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue(""+periodNamebefore);
                            cell.setCellStyle(style6);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellValue("VARIAN");
                            cell.setCellStyle(style6);    
                            
                     row = sheet.createRow((short) (nn++));
                            
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue(" GAJI POKOK + T.JABATAN + GAJI HARIAN");
                            cell.setCellStyle(style6);
                    
                            cell = row.createCell((short) 2);
                            cell.setCellValue(benefit1);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellValue(benefitbefore);
                            cell.setCellStyle(formatRupiah);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellValue(Math.abs(benefit1-benefitbefore));
                            cell.setCellStyle(formatRupiah);    
                            
                            nn++;
                        row = sheet.createRow((short) (nn++));
                              
                            cell = row.createCell((short) 1);
                            cell.setCellValue(" PENJELASAN  PERBEDAAN ");
                            cell.setCellStyle(styleKiriAtas);
                            
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleAtas);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleAtas);
                    
                            cell = row.createCell((short) 4);
                            cell.setCellStyle(styleKananAtas);
                            
                        row = sheet.createRow((short) (nn++));
                            
                            String ni = "";
                            if (listEmpPaySlip.size() > listEmpPaySlipBefore.size()){
                                int absEmp = listEmpPaySlip.size() - listEmpPaySlipBefore.size();
                                ni = ni + " A TERJADI PENAMBAHAN PEGAWAI " + String.valueOf(absEmp) + " Orang ";
                            } else if (listEmpPaySlip.size() < listEmpPaySlipBefore.size()) {
                                int absEmp = listEmpPaySlipBefore.size() - listEmpPaySlip.size();
                                ni = ni + " A TERJADI PENGURANGAN PEGAWAI " + String.valueOf(absEmp) + " Orang ";
                            } 
                            
                            if (benefit1 > benefitbefore && benefitbefore != 0 ) {
                                ni = ni + ", TERJADI KENAIKAN GAJI ";
                            } 
                            
                            cell = row.createCell((short) 1);
                            cell.setCellValue(ni);
                            cell.setCellStyle(styleKiri);
                            
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleKosong);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleKosong);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellStyle(styleKanan);
                    
                         row = sheet.createRow((short) (nn++));
                            
                            cell = row.createCell((short) 1);
                            cell.setCellStyle(styleKiri);
                            
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleKosong);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleKosong);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellStyle(styleKanan); 
                            double totallistempdif = 0 ;
                        if (listEmpDif.size() > 0 && listEmpDifBeforeV.size() > 0 ) {
                                for (int e = 0; e<listEmpDif.size(); e++){
                                    Employee employee2 = (Employee)listEmpDif.get(e);
                                    row = sheet.createRow((short) (nn++));  
                                    
                                    cell = row.createCell((short) 1);
                                    cell.setCellValue(" "+employee2.getFullName());
                                    cell.setCellStyle(styleKiri);     
                                    
                                    cell = row.createCell((short) 2);
                                    cell.setCellStyle(styleKosong);
                                    
                                    cell = row.createCell((short) 3);
                                    cell.setCellStyle(styleKosong);
                                    
                                    double gajibEmpDif = Double.valueOf(((Double)gajilistEmpDif.get(e)).doubleValue());
                                  
                                    totallistempdif = totallistempdif + gajibEmpDif;
                                    
                                    cell = row.createCell((short) 4);
                                    cell.setCellValue(gajibEmpDif);
                                    cell.setCellStyle(formatRupiahKiriKosong);
                                    
                                }
                            }
                        row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 1);
                            cell.setCellStyle(styleKiriBawah);   
                            
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleBawah);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleBawah);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellStyle(styleKananBawah);
                         row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 1);
                            cell.setCellValue(" TOTAL PENAMBAHAN");
                            cell.setCellStyle(styleKiriFull);   
                            
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleAtasBawah);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleAtasBawah);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellValue(totallistempdif);
                            cell.setCellStyle(formatRupiahKiriKosong); 
                            
                         row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 1);
                            cell.setCellStyle(styleKiriAtas);
                            
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleAtas);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleAtas);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellStyle(styleKananAtas);
                            
                         row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 1);
                            cell.setCellValue("B PENGURANGAN : RESIGN, DEMOSI, LAIN-LAIN");
                            cell.setCellStyle(styleKiri);   
                            
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleKosong);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleKosong);
                            
                            cell = row.createCell((short) 4);
                            cell.setCellStyle(styleKanan);
                            
                           double nilaitotal = 0;  
                            if (listEmpDif.size() > 0 && listEmpDifBeforeV.size() > 0 ) {
                                for (int e = 0; e<listEmpDifBeforeV.size(); e++){
                                    Employee employee2 = (Employee)listEmpDifBeforeV.get(e);
                                    if (listEmpDifBefore.containsKey(employee2.getOID())){
                                    row = sheet.createRow((short) (nn++));  
                                    
                                    cell = row.createCell((short) 1);
                                    cell.setCellValue( " " +employee2.getFullName());
                                    cell.setCellStyle(styleKiri);  
                                    
                                    cell = row.createCell((short) 2);
                                    cell.setCellStyle(styleKosong);
                                    cell = row.createCell((short) 3);
                                    cell.setCellStyle(styleKosong);
                                    
                                    double gajib = Double.valueOf(((Double)gajilistEmpDifBefore.get(e)).doubleValue());
                                    nilaitotal = nilaitotal + gajib;
                                    
                                    cell = row.createCell((short) 4);
                                    cell.setCellValue(gajib);
                                    cell.setCellStyle(formatRupiahKiriKosong);  
                                    }
                                    
                                    
                                }
                            }
                    row = sheet.createRow((short) (nn++));  
                                    
                                    cell = row.createCell((short) 1);
                                    cell.setCellStyle(styleKiri);  
                                   
                                    cell = row.createCell((short) 2);
                                    cell.setCellStyle(styleKosong);
                                    
                                    cell = row.createCell((short) 3);
                                    cell.setCellStyle(styleKosong);
                                    
                                    cell = row.createCell((short) 4);
                                    cell.setCellStyle(styleKanan);
                 
                     row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 1);
                            cell.setCellValue("C Perubahan gaji Harian Naik / Turun ( Selisih Gaji Harian / DW ) ");
                            cell.setCellStyle(styleKiri);  
                            
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleKosong);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleKosong); 
                            
                            cell = row.createCell((short) 4);
                            cell.setCellValue(gajiDw-gajiDwBefore);
                            cell.setCellStyle(formatRupiahKiriKosong);  
                            nilaitotal = nilaitotal + (gajiDw-gajiDwBefore) ;
                        row = sheet.createRow((short) (nn++));  
                                    
                                    cell = row.createCell((short) 1);
                                    cell.setCellStyle(styleKiriBawah);  
                                   
                                    cell = row.createCell((short) 2);
                                    cell.setCellStyle(styleBawah);
                                    
                                    cell = row.createCell((short) 3);
                                    cell.setCellStyle(styleBawah);
                                    
                                    cell = row.createCell((short) 4);
                                    cell.setCellStyle(styleKananBawah);    
                    nn++;
                         row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 1);
                            cell.setCellValue(" TOTAL PENGURANGAN ");
                            cell.setCellStyle(styleKiriFull); 
                            
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleAtasBawah);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleAtasBawah); 
                            
                            cell = row.createCell((short) 4);
                            cell.setCellValue(nilaitotal);
                            cell.setCellStyle(formatRupiah);  

                            
                    nn++;
                         row = sheet.createRow((short) (nn++));
                            cell = row.createCell((short) 1);
                            cell.setCellValue(" KONTROL A DIKURANGI B ");
                            cell.setCellStyle(styleKiriFull); 
                         
                            cell = row.createCell((short) 2);
                            cell.setCellStyle(styleAtasBawah);
                            
                            cell = row.createCell((short) 3);
                            cell.setCellStyle(styleAtasBawah); 
                            
                            cell = row.createCell((short) 4);
                            cell.setCellValue(nilaitotal-(Math.abs(benefit1-benefitbefore)));
                            cell.setCellStyle(formatRupiah);
                            
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
