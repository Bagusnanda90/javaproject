
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report;

//~--- non-JDK imports --------------------------------------------------------
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.session.locker.SessLocker;
import com.dimata.util.Formater;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

//~--- JDK imports ------------------------------------------------------------

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Document : Report Reprimand to XLS
 *
 * @author Hendra McHen
 */
public class ReprimandListXLS extends HttpServlet {

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
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        System.out.println("\t---===| Reprimand Excel Report |===---");

        // response.setContentType("text/html");
        // response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Reprimand List");
        HSSFCellStyle style = wb.createCellStyle();

        style.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());    // HSSFCellStyle.WHITE);
        style.setFillForegroundColor(new HSSFColor.WHITE().getIndex());    // HSSFCellStyle.WHITE);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);

        HSSFCellStyle style2 = wb.createCellStyle();

        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());    // HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());    // HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();

        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());    // HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());    // HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style4 = wb.createCellStyle();

        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());    // HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());    // HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);

        cell.setCellValue("REPRIMAND LIST");
        cell.setCellStyle(style);

        for (int j = 1; j < 8; j++) {
            cell = row.createCell((short) j);
            cell.setCellStyle(style);
        }

        String[] tableHeader = {
            "NO", "PAYROLL", "EMPLOYEE", "DEPARTMENT", "SECTION", "POSITION", "DATE", "DESCRIPTION"
        };

        row = sheet.createRow((short) (2));

        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }

        HttpSession session = request.getSession();
        SrcEmpReprimand sessEmpReprimand = new SrcEmpReprimand();

        sessEmpReprimand = (SrcEmpReprimand) session.getValue("SRC_EMP_REPRIMAND");

        Date test = sessEmpReprimand.getStartingReprimandDate();
        Vector listSource = new Vector();

        listSource = (Vector) session.getValue("session_list");
        row = sheet.createRow((short) (5));

        // cell = row.createCell((short)3);

        int a = 3;
        int no = 0;

        for (int i = 0; i < listSource.size(); i++) {
            Vector list = (Vector) listSource.get(i);
            Employee emp = (Employee) list.get(0);
            EmpReprimand rpm = (EmpReprimand) list.get(1);
            Department dep = (Department) list.get(2);
            Section sec = (Section) list.get(3);
            Position pos = (Position) list.get(4);

            row = sheet.createRow((short) (a + i));
            no++;
            cell = row.createCell((short) 0);
            cell.setCellValue(String.valueOf(no));
            cell.setCellStyle(style);
            cell = row.createCell((short) 1);
            cell.setCellValue(emp.getEmployeeNum());
            cell.setCellStyle(style);
            cell = row.createCell((short) 2);
            cell.setCellValue(emp.getFullName());
            cell.setCellStyle(style);
            cell = row.createCell((short) 3);
            cell.setCellValue(dep.getDepartment());
            cell.setCellStyle(style);
            cell = row.createCell((short) 4);
            cell.setCellValue(sec.getSection());
            cell.setCellStyle(style);
            cell = row.createCell((short) 5);
            cell.setCellValue(pos.getPosition());
            cell.setCellStyle(style);
            cell = row.createCell((short) 6);
            cell.setCellValue(Formater.formatDate(rpm.getValidityDate(), "d-MMM-yyyy"));
            cell.setCellStyle(style);
            cell = row.createCell((short) 7);
            cell.setCellValue((rpm.getDescription().length() > 50)
                    ? rpm.getDescription().substring(0, 50) + " ..."
                    : rpm.getDescription());
            cell.setCellStyle(style);
        }

        ServletOutputStream sos = response.getOutputStream();

        wb.write(sos);
        sos.close();
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


//~ Formatted by Jindent --- http://www.jindent.com
