/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.payroll;

import com.dimata.aiso.entity.masterdata.Perkiraan;
import com.dimata.aiso.entity.masterdata.PstPerkiraan;
import com.dimata.harisma.entity.masterdata.PstComponentCoaMap;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author Dimata 007
 */
public class JVReportXLS extends HttpServlet {

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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        System.out.println("---===| Excel Report |===---");
        response.setContentType("application/x-msexcel");
        String printHeader = PstSystemProperty.getValueByName("PRINT_HEADER");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = formatDate.format(cal.getTime());
        
        cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Leave Entitle Report "+dateFormat);
        /* Object header */
        HSSFHeader header = sheet.getHeader();
        /* Object footer */
        HSSFFooter footer = sheet.getFooter();

        /* Row and Columns to repeat */
        
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        Font fontTitle = wb.createFont();
        fontTitle.setColor(HSSFColor.WHITE.index);
        style3.setFont(fontTitle);
        style3.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.BLUE().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.YELLOW().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
        
        HSSFCellStyle style5 = wb.createCellStyle();
        style5.setFillBackgroundColor(new HSSFColor.ORANGE().getIndex());//HSSFCellStyle.WHITE);
        style5.setFillForegroundColor(new HSSFColor.ORANGE().getIndex());//HSSFCellStyle.WHITE);
        style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style5.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style5.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style5.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //pemberian warna font
        CellStyle styleFont = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.RED.index);
        styleFont.setFont(font);
        
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        
        /* Header setting */
        String strHeader = "Company : "+printHeader;
        strHeader += "\nReport Name : Leave Entitle Report Excel";
        strHeader += "\nPeriod : "+dateFormat;
        header.setLeft(strHeader);
        /* Footer setting */
        footer.setLeft("Print on : "+dateFormat+" "+sdf.format(cal.getTime()));
        footer.setRight( "Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages() );
        
        long oidPeriod = FRMQueryString.requestLong(request, "period_id");
        Vector listPerkiraan = new Vector(1,1);
        listPerkiraan = PstPerkiraan.list(0, 0, "", "");
        
        int countRow = 1;
        int cols = 0;
        /*
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Account No.","");
        ctrlist.addHeader("Description", "");
        ctrlist.addHeader("Debit", "");
        ctrlist.addHeader("Credit", "");
         */
        row = sheet.createRow((short) countRow);
        cell = row.createCell((short) cols);
        cell.setCellValue("No");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Account No");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Description");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Debit");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Credit");
        cell.setCellStyle(style3);
        cols++;

        countRow = 1;
        int coloum = 0;
        int nomor = 0;
        
        int tanda = 0;
        Vector rowy = new Vector();
        long debitTotal = 0;
        long creditTotal = 0;
        
        if (listPerkiraan != null && listPerkiraan.size() > 0){
            for(int i=0; i<listPerkiraan.size(); i++){
                Perkiraan perkiraan = (Perkiraan)listPerkiraan.get(i);
                // rowx will be created secara berkesinambungan base on i
                Vector rowx = new Vector();
                tanda = perkiraan.getTandaDebetKredit();
                long total = PstComponentCoaMap.getCoA(perkiraan.getOID(), oidPeriod);
                
                
                countRow++;
                nomor++;

                row = sheet.createRow((short) countRow);
                sheet.createFreezePane(2, 2);
                /* No */
                cell = row.createCell((short) coloum);
                cell.setCellValue("" + nomor);
                cell.setCellStyle(style2);
                coloum++;
                /* Account No */
                cell = row.createCell((short) coloum);
                cell.setCellValue(""+perkiraan.getNoPerkiraan());
                cell.setCellStyle(style2);
                coloum++;
                /* Description */
                cell = row.createCell((short) coloum);
                cell.setCellValue(""+perkiraan.getAccountNameEnglish());
                cell.setCellStyle(style2);
                coloum++;

                if (tanda == 0){
                    debitTotal = debitTotal + total;
                    /* Debit */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue(convertLong(total));
                    cell.setCellStyle(style2);
                    coloum++;
                    /* Credit */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue("-");
                    cell.setCellStyle(style2);
                    coloum++;
                } else {
                    creditTotal = creditTotal + total;
                    /* Debit */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue("-");
                    cell.setCellStyle(style2);
                    coloum++;
                    /* Credit */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue(convertLong(total));
                    cell.setCellStyle(style2);
                    coloum++;
                }
                coloum = 0;
            }
            countRow++;
            row = sheet.createRow((short) countRow);
            
            cell = row.createCell((short) coloum);
            cell.setCellValue("-");
            cell.setCellStyle(style2);
            coloum++;
            
            cell = row.createCell((short) coloum);
            cell.setCellValue("-");
            cell.setCellStyle(style2);
            coloum++;
            
            cell = row.createCell((short) coloum);
            cell.setCellValue("GRAND TOTAL");
            cell.setCellStyle(style2);
            coloum++;
            
            cell = row.createCell((short) coloum);
            cell.setCellValue(convertLong(debitTotal));
            cell.setCellStyle(style2);
            coloum++;
            
            cell = row.createCell((short) coloum);
            cell.setCellValue(convertLong(creditTotal));
            cell.setCellStyle(style2);
            coloum++;
        }
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }
    
    /* Convert Long */
    public long convertLong(double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return bDecimal.longValue();
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
