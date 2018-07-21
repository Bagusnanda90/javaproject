/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.payroll;

import com.dimata.harisma.entity.payroll.LeaveEntitleReport;
import com.dimata.harisma.entity.payroll.PstCustomRptMain;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
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
public class LeaveEntitleReportXLS extends HttpServlet {

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
        
        long oidPeriod = FRMQueryString.requestLong(request, "period_id");
        long oidDepartment = FRMQueryString.requestLong(request, "department_id");
        
        Vector listReport = new Vector(1,1);
        listReport = PstCustomRptMain.listLeaveEntitleReport(oidPeriod, oidDepartment);
        
        /* Header setting */
        String strHeader = "Company : "+printHeader;
        strHeader += "\nReport Name : Leave Entitle Report Excel";
        strHeader += "\nPeriod : "+dateFormat;
        header.setLeft(strHeader);
        /* Footer setting */
        footer.setLeft("Print on : "+dateFormat+" "+sdf.format(cal.getTime()));
        footer.setRight( "Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages() );
        
        int countRow = 1;
        int cols = 0;
        /* Header Table 
         ctrlist.addHeader("Account Code.","");
        ctrlist.addHeader("Account Name", "");
        ctrlist.addHeader("Department", "");
        ctrlist.addHeader("Section", "");
        ctrlist.addHeader("Base Amount", "");
        ctrlist.addHeader("Head Count", "");
        ctrlist.addHeader("Apply Amount", "");
         */
        row = sheet.createRow((short) countRow);
        cell = row.createCell((short) cols);
        cell.setCellValue("No");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Account Code");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Account Name");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Department");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Section");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Base Amount");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Head Count");
        cell.setCellStyle(style3);
        cols++;
        
        cell = row.createCell((short) cols);
        cell.setCellValue("Apply Amount");
        cell.setCellStyle(style3);
        cols++;
        
        countRow = 1;
        int coloum = 0;
        int nomor = 0;
        if (listReport != null && listReport.size()>0){
            double total = 0;
            int headCount = 0;

            String[][] data = new String[listReport.size()+1][3];//
            int temp = 0;
            for (int h = 0; h < listReport.size()+1; h++) {
                data[h][0] = "test";
                data[h][1] = "test";
                data[h][2] = "0";
            }
            for (int i = 0; i < listReport.size(); i++) {
                LeaveEntitleReport en = (LeaveEntitleReport)listReport.get(i);
                data[i][0] = en.getDepartment();
                data[i][1] = en.getSection();
                data[i][2] = ""+en.getBaseAmount();
            }
            int nextDepart = 0;
            int nextSection = 1;
            int headCountDepart = 0;
            double totalDepart = 0;
            double appAmount = 0;
            double totalAppAmount = 0;
            int inc = 0;
            double totalBase = 0;
            double totalApp = 0;
            int totalHeadCount = 0;

            for(int j = 0; j < listReport.size()+1; j++){

                    if (data[j][1].equals(data[nextSection][1])){
                        total = total + Double.valueOf(data[j][2]);
                        headCount++;
                        temp = j;
                    } else {

                        countRow++;
                        nomor++;
                        
                        row = sheet.createRow((short) countRow);
                        sheet.createFreezePane(2, 2);
                        /* No */
                        cell = row.createCell((short) coloum);
                        cell.setCellValue("" + nomor);
                        cell.setCellStyle(style2);
                        coloum++;
                        /* Account Code */
                        cell = row.createCell((short) coloum);
                        cell.setCellValue("-");
                        cell.setCellStyle(style2);
                        coloum++;
                        /* Account Name */
                        cell = row.createCell((short) coloum);
                        cell.setCellValue("-");
                        cell.setCellStyle(style2);
                        coloum++;
                        /* Department */
                        cell = row.createCell((short) coloum);
                        cell.setCellValue(""+data[temp][0]);
                        cell.setCellStyle(style2);
                        coloum++;
                        /* Section */
                        cell = row.createCell((short) coloum);
                        cell.setCellValue(""+data[temp][1]);
                        cell.setCellStyle(style2);
                        coloum++;
                        
                        appAmount = total / 30;
                        totalAppAmount = totalAppAmount + appAmount;
                        
                        /* Base Amount */
                        cell = row.createCell((short) coloum);
                        cell.setCellValue(convertLong(total));
                        cell.setCellStyle(style2);
                        coloum++;
                        /* Head Account */
                        cell = row.createCell((short) coloum);
                        cell.setCellValue(headCount);
                        cell.setCellStyle(style2);
                        coloum++;
                        /* Apply Amount */
                        cell = row.createCell((short) coloum);
                        cell.setCellValue(convertLong(appAmount));
                        cell.setCellStyle(style2);
                        coloum++;

                        coloum = 0;
                        
                        headCountDepart += headCount;
                        totalDepart += total;
                        total = Double.valueOf(data[j][2]);
                        headCount = 1;
                        temp = j;
                        nextSection = j;
                    }

                if (!(data[j][0].equals(data[nextDepart][0]))){  
                    countRow++;
                    row = sheet.createRow((short) countRow);
                    /* No */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue("-");
                    cell.setCellStyle(style4);
                    coloum++;
                    /* Account Code */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue("-");
                    cell.setCellStyle(style4);
                    coloum++;
                    /* Account Name */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue("-");
                    cell.setCellStyle(style4);
                    coloum++;
                    /* Department */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue("-");
                    cell.setCellStyle(style4);
                    coloum++;
                    /* Section */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue("Total Department");
                    cell.setCellStyle(style4);
                    coloum++;
                    /* Base Amount */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue(convertLong(totalDepart));
                    cell.setCellStyle(style4);
                    coloum++;
                    /* Head Count */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue(headCountDepart);
                    cell.setCellStyle(style4);
                    coloum++;
                    /* Apply Amount */
                    cell = row.createCell((short) coloum);
                    cell.setCellValue(convertLong(totalAppAmount));
                    cell.setCellStyle(style4);
                    coloum++;
                    
                    coloum = 0;

                    totalHeadCount += headCountDepart;
                    totalBase += totalDepart;
                    totalApp += totalAppAmount;
                    headCountDepart = 0;
                    totalDepart = 0;
                    totalAppAmount = 0;
                    nextDepart = j;
                    inc++;
                }
            }
            /* No */
            countRow++;
            row = sheet.createRow((short) countRow);
            cell = row.createCell((short) coloum);
            cell.setCellValue("-");
            cell.setCellStyle(style5);
            coloum++;
            /* Account Code */
            cell = row.createCell((short) coloum);
            cell.setCellValue("-");
            cell.setCellStyle(style5);
            coloum++;
            /* Account Name */
            cell = row.createCell((short) coloum);
            cell.setCellValue("-");
            cell.setCellStyle(style5);
            coloum++;
            /* Department */
            cell = row.createCell((short) coloum);
            cell.setCellValue("-");
            cell.setCellStyle(style5);
            coloum++;
            /* Section */
            cell = row.createCell((short) coloum);
            cell.setCellValue("Total Company");
            cell.setCellStyle(style5);
            coloum++;
            /* Base Amount */
            cell = row.createCell((short) coloum);
            cell.setCellValue(convertLong(totalBase));
            cell.setCellStyle(style5);
            coloum++;
            /* Head Count */
            cell = row.createCell((short) coloum);
            cell.setCellValue(totalHeadCount);
            cell.setCellStyle(style5);
            coloum++;
            /* Apply Amount */
            cell = row.createCell((short) coloum);
            cell.setCellValue(convertLong(totalApp));
            cell.setCellStyle(style5);
            coloum++;

            coloum = 0;
        }
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }
    
    /* Convert int */
    public int convertInteger(double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return bDecimal.intValue();
    }
    
    /* Convert Long */
    public long convertLong(double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return bDecimal.longValue();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }
    
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
