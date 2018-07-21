/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report;

import com.dimata.harisma.entity.employee.EmpEducation;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmpEducation;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Formater;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Dimata 007
 */
public class ExportPositionOrgXLS extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }
    
    public void destroy() {/*no-code*/}
    
    private static HSSFFont createFont(HSSFWorkbook wb, int size, int color, boolean isBold) {
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) size);
        font.setColor((short) color);
        if (isBold) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        return font;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        

        System.out.println("---===| Excel Report |===---");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Export Position Organization");

        HSSFCellStyle style1 = wb.createCellStyle();
        
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);

        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);

        long divisionId = FRMQueryString.requestLong(request, "division_id");
        long departmentId = FRMQueryString.requestLong(request, "department_id");
        long sectionId = FRMQueryString.requestLong(request, "section_id");
        
        /* Period From and Period To */
        String periodFrom = FRMQueryString.requestString(request, "period_from");
        String periodTo = FRMQueryString.requestString(request, "period_to");
        
        int countRow = 0;
        row = sheet.createRow((short) 1);
        cell = row.createCell((short) 2);
        cell.setCellValue("PERIODE : "+periodFrom+" - "+periodTo);
        cell.setCellStyle(style1);
        
        row = sheet.createRow((short) 2);
        cell = row.createCell((short) 2);
        cell.setCellValue("Division : "+divisionId);
        cell.setCellStyle(style1);
        cell = row.createCell((short) 3);
        cell.setCellValue("-CUSTOM-");
        cell.setCellStyle(style1);
        Vector listEdu = new Vector();

        int no = 0;        

        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}
