/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.attendance;

import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.canteen.CanteenSchedule;
import com.dimata.harisma.entity.canteen.PstCanteenSchedule;
import com.dimata.harisma.entity.canteen.SumReportDepartment;
import com.dimata.harisma.entity.canteen.SumReportParameter;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.Reason;
import com.dimata.harisma.session.attendance.SessPresence;
import com.dimata.harisma.session.attendance.YearPresence;
import com.dimata.harisma.session.canteen.SessCanteenVisitation;
import com.dimata.harisma.session.leave.RepLevDepartment;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

// package lowagie
import com.lowagie.text.*;

// package qdep
import com.dimata.util.*;
import com.dimata.qdep.form.*;
import com.dimata.system.entity.PstSystemProperty;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.hssf.util.*;

/**
 *
 * @author Roy Andika
 */
public class YearPresenceXls extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /** Destroys the servlet.
     */
    public void destroy() {
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }

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
        HSSFSheet sheet = wb.createSheet("Year Presence report");

        //Style
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleSubTitle = wb.createCellStyle();
        styleSubTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleSubTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleSubTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleSubTitle.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeader.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        styleHeader.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleHeaderBig = wb.createCellStyle();
        styleHeaderBig.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeaderBig.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeaderBig.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        styleHeaderBig.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleFooter = wb.createCellStyle();
        styleFooter.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleFooter.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleFooter.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFooter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleFooter.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleFooter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleFooter.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleFooter.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleValueBold = wb.createCellStyle();
        styleValueBold.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleValueBold.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleValueBold.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValueBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValueBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleValueBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleValueBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleValueBold.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleValue = wb.createCellStyle();
        styleValue.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleValue.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));

        HttpSession sess = request.getSession(true);

        Vector tmpListEmployee = new Vector();
        Vector listEmployee = new Vector();
        Vector listPresenceReport = new Vector();
        int year = 0;
        long oidDepartment = 0;
        long oidSection = 0;
        //update by devin 2014-02-05
        long oidCompany=0;
        long oidDivision=0;
        String empNumb = "";
        String fullName="";
        try {

            tmpListEmployee = (Vector) sess.getValue("PRESENCE_YEAR_EMPLOYEE");
            if(tmpListEmployee.get(0)!=null){
            year = Integer.parseInt("" + tmpListEmployee.get(0));
            }
            if(tmpListEmployee.get(1) !=null){
            oidDepartment = Long.parseLong("" + tmpListEmployee.get(1));
            }
            if(tmpListEmployee.get(2)!=null){
            oidSection = Long.parseLong("" + tmpListEmployee.get(2));
            }
            //update by devin 2014-02-05
            if(tmpListEmployee.get(3)!=null){
            oidCompany = Long.parseLong("" + tmpListEmployee.get(3));
            }
            if(tmpListEmployee.get(4)!=null){
            oidDivision = Long.parseLong("" + tmpListEmployee.get(4));
            }
            //update by satrya 2012-10-17
            if(tmpListEmployee.get(5)!=null){
             empNumb = String.valueOf("" + tmpListEmployee.get(5));
            }
            if(tmpListEmployee.get(6)!=null){
            fullName = String.valueOf("" + tmpListEmployee.get(6));
            }
            if(tmpListEmployee.get(7)!=null){
            listEmployee = (Vector) tmpListEmployee.get(7);
            }
            //update by devin 2014-02-05
            // listPresenceReport = SessPresence.yearPresence(oidDepartment, oidSection, year,empNumb,fullName);

            listPresenceReport = SessPresence.yearPresence(oidDepartment,oidDivision, oidCompany,oidSection, year,empNumb,fullName);

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        if (listPresenceReport != null && listPresenceReport.size() > 0 && listEmployee != null && listEmployee.size() > 0) {

            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

            String printHeader = PstSystemProperty.getValueByName("PRINT_HEADER");

            if (printHeader.equals(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                printHeader = "";
            }

            String[] tableTitle = {
                printHeader,
                "REKAP KETIDAKHADIRAN KARYAWAN",
                "PERIODE TAHUN : " + year
            };

            String[] tableSubTitle = {};

            Vector vPeriod = SessPresence.getPeriodPresence(year);
            Vector vReason = SessPresence.reasonYear();

            int max = vPeriod.size() * vReason.size();

            /*=============== HEADER TABLE ====================*/

            String[] tableHeader = new String[max + 4 + vPeriod.size() + vReason.size()];

            tableHeader[0] = "NO";
            tableHeader[1] = "DEPARTMENT";
            tableHeader[2] = "PAYROLL";
            tableHeader[3] = "FULL NAME";

            int idx = 4;
            int maxPeriod = vPeriod.size() - 1;
            if (vPeriod != null && vPeriod.size() > 0) {

                for (int i = 0; i < vPeriod.size(); i++) {

                    for (int id = 0; id < vReason.size(); id++) {

                        Reason reason = (Reason) vReason.get(id);
                        tableHeader[idx] = reason.getReason();
                        idx++;

                    }
                    if(i != maxPeriod  ){
                        tableHeader[idx] = "";
                        idx++;
                    }
                }


                if(vReason != null && vReason.size() > 0){

                    tableHeader[idx] = "";
                    idx++;

                    for(int jHeader = 0 ; jHeader < vReason.size() ; jHeader++){

                        Reason reason = (Reason)vReason.get(jHeader);
                        tableHeader[idx] = reason.getReason();
                        idx++;

                    }
                }
                
            }


            String[] tableSubHeader = new String[max + 4 + vPeriod.size() + vReason.size()];

            tableSubHeader[0] = "";
            tableSubHeader[1] = "";
            tableSubHeader[2] = "";
            tableSubHeader[3] = "";

            int idxSub = 4;

            if (vPeriod != null && vPeriod.size() > 0){

                for (int i = 0; i < vPeriod.size(); i++) {

                    Period objPeriod = (Period)vPeriod.get(i);

                    for (int id = 0; id < vReason.size(); id++) {

                        if (id == 0) {
                            tableSubHeader[idxSub] = Formater.formatDate(objPeriod.getEndDate(), "MMMM yyyy");
                        } else {
                            tableSubHeader[idxSub] = "";
                        }
                        idxSub++;

                    }
                    if(i != maxPeriod  ){
                        tableSubHeader[idxSub] = "";
                        idxSub++;
                    }
                }

                if(vReason != null && vReason.size() > 0){

                    tableSubHeader[idxSub] = "";
                    idxSub++;

                    for(int jSubHeader = 0 ; jSubHeader < vReason.size() ; jSubHeader++){

                        if(jSubHeader == 0){
                            tableSubHeader[idxSub] = "TOTAL";
                        }
                        idxSub++;

                    }
                }

            }


            /**
             *@DESC     :UNTUK COUNT ROW
             */
            int countRow = 0;

            /**
             * @DESC    : TITTLE
             */
            for (int k = 0; k < tableTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableTitle[k]);
                cell.setCellStyle(styleTitle);
            }

            /**
             * @DESC    : SUB TITTLE
             */
            for (int k = 0; k < tableSubTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableSubTitle[k]);
                cell.setCellStyle(styleSubTitle);
            }

            countRow = countRow + 2;

            /**
             * @SUB HEADER
             */
            row = sheet.createRow((short) (countRow));
            countRow++;
            for (int k = 0; k < tableSubHeader.length; k++) {
                cell = row.createCell((short) k);
                cell.setCellValue(tableSubHeader[k]);
                cell.setCellStyle(styleHeader);
            }
            /**
             * @DESC    : HEADER
             */
            row = sheet.createRow((short) (countRow));
            countRow++;
            for (int k = 0; k < tableHeader.length; k++) {
                cell = row.createCell((short) k);
                cell.setCellValue(tableHeader[k]);
                cell.setCellStyle(styleHeader);
            }

            int no = 0;
            boolean match = false;

            for (int idxEmp = 0; idxEmp < listEmployee.size(); idxEmp++) {

                int countReason[] = new int[vReason.size()];
                no++;

                YearPresence listEmp = (YearPresence) listEmployee.get(idxEmp);

                int collPos = 0;

                /**
                 * @DESC    : CREATE NEW ROW
                 */
                row = sheet.createRow((short) (countRow));
                countRow++;

                /**
                 * @DESC    : NO
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(no));
                cell.setCellStyle(styleValue);
                collPos++;

                /**
                 * @DESC    : DEPARTMENT
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listEmp.getDep()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                /**
                 * @DESC    : PAYROLL
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listEmp.getEmployeeNum()));
                cell.setCellStyle(styleValue);

                collPos++;

                /**
                 * @DESC    : FULL NAME
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listEmp.getFullName()));
                cell.setCellStyle(styleValue);
                collPos++;

                /* Mengecek periode schedule */
                for (int i_period = 0; i_period < vPeriod.size(); i_period++) {

                    Period objPeriod = (Period) vPeriod.get(i_period);
                    match = false;

                    for (int idxList = 0; idxList < listPresenceReport.size(); idxList++) {

                        YearPresence yearPresence = (YearPresence) listPresenceReport.get(idxList);

                        if (listEmp.getEmployeeId() == yearPresence.getEmployeeId() && objPeriod.getOID() == objPeriod.getOID()) {

                            //looping sebanyak value schedule
                            for (int schIdx = 0; schIdx < yearPresence.getValueSchedule().length; schIdx++) {

                                /**
                                 * @DESC    : TOTAL
                                 */
                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(yearPresence.getValueSchedule()[schIdx]));
                                cell.setCellStyle(styleValue);
                                collPos++;

                                countReason[schIdx] = countReason[schIdx] + yearPresence.getValueSchedule()[schIdx];

                            }

                            match = true;
                            //jika sudah ditampilkan akan di remove
                            listPresenceReport.remove(idxList);
                            break;

                        }
                    }

                    if (match == false) {

                        for (int i = 0; i < SessPresence.reasonYear().size(); i++) {

                            /**
                             * @DESC    : TOTAL
                             */
                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(0));
                            cell.setCellStyle(styleValue);
                            collPos++;

                        }
                    }

                    if(i_period != maxPeriod){
                        cell = row.createCell((short) collPos);
                        cell.setCellValue("");
                        cell.setCellStyle(styleValue);
                        collPos++;
                    }
                }

                if(vReason != null && vReason.size() > 0){

                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");
                    cell.setCellStyle(styleValue);
                    collPos++;

                    for(int j = 0; j < vReason.size(); j++){
                        
                        cell = row.createCell((short) collPos);
                        cell.setCellValue(""+countReason[j]);
                        cell.setCellStyle(styleValue);
                        collPos++;
                        
                    }
                }
            }

            ServletOutputStream sos = response.getOutputStream();
            wb.write(sos);
            sos.close();
        }
    }
}
