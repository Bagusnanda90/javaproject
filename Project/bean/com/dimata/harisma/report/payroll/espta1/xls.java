/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.payroll.espta1;

import com.dimata.harisma.entity.masterdata.Company;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.report.SrcEsptA1;
import com.dimata.harisma.report.SrcEsptMonth;
import com.dimata.harisma.session.payroll.Pajak;
import com.dimata.harisma.session.payroll.SessESPT;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author Kartika
 */
public class xls extends HttpServlet {

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

    private static HSSFFont createFont(HSSFWorkbook wb, int size, int color, boolean isBold) {
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) size);
        font.setColor((short) color);
        if (isBold) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        return font;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("---===| Excel Report ESPT A |===---");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("1721_bp_A1");

        HSSFCellStyle styleStandar = wb.createCellStyle();
        styleStandar.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleStandar.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleStandar.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleStandar.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
        
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        // Create a row and put some cells in it. Rows are 0 based.
        // Create a row and put some cells in it. Rows are 0 based.
        //HSSFRow row = sheet.createRow((short) 0);
        //HSSFCell cell = row.createCell((short) 0);

        //update by satrya 2014-02-01
        //pemberian warna font
        CellStyle styleFont = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.RED.index);
        styleFont.setFont(font);

        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);

        String printHeader = PstSystemProperty.getValueByName("PRINT_HEADER");
        long periodId = FRMQueryString.requestLong(request, "inp_period_id");
        long companyId = FRMQueryString.requestLong(request, "company_id");
        long divisionId = FRMQueryString.requestLong(request, "division_id");
        long departmentId = FRMQueryString.requestLong(request, "department_id");
        long sectionId = FRMQueryString.requestLong(request, "inp_section_id");
        //priska 20151215 search all
        long search = 0 ;
        try{ search = FRMQueryString.requestLong(request, "search");} catch (Exception e){}
        Company company = new Company();
        Division division = new Division();
        Department department = new Department();
        Section section = new Section();
        try {
            company = PstCompany.fetchExc(companyId);
            division = PstDivision.fetchExc(divisionId);
            department = PstDepartment.fetchExc(departmentId);
            section = PstSection.fetchExc(sectionId);
        } catch (Exception e) {
            System.out.println(e + " Company Structur ");
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = formatDate.format(cal.getTime());

        String companyName = "-" + company.getCompany();
        String divisionName = division != null && division.getDivision().length() > 0 ? division.getDivision() : "-";
        String departmenName = department != null && department.getDepartment().length() > 0 ? department.getDepartment() : "-";
        String sectionName = section != null && section.getSection().length() > 0 ? section.getSection() : "-";
        String[] tableTitle = {
            printHeader,
            "ESPT A1",
            "DATE : " + dateFormat,
            "COMPANY : " + companyName,
            "DIVISION : " + divisionName,
            "DEPARTEMENT : " + departmenName,
            "SECTION : " + sectionName
        };

        int countRow = 0;

        for (int k = 0; k < tableTitle.length; k++) {
            row = sheet.createRow((short) (countRow));
            countRow++;
            cell = row.createCell((short) 0);
            cell.setCellValue(tableTitle[k]);
            cell.setCellStyle(styleTitle);
        }

        row = sheet.createRow((short) 7);
        cell = row.createCell((short) 0);

        String[] tableHeaderItem = SessESPT.TABLE_HEAD_A1;

        // Header Pertama
        //row = sheet.createRow((short) (2));
        
        
        
        for (int k = 0; k < tableHeaderItem.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeaderItem[k]);
            cell.setCellStyle(style3);
        }

        cell = row.createCell((short) tableHeaderItem.length);
        cell.setCellValue("Emp Num");
        cell.setCellStyle(style3);
        
        countRow = 7;
        int no = 0;
        String kodePajak = "";
        Vector listEspt = new Vector();
        String inSalBrutto = PstSystemProperty.getValueByName("PAYROLL_ESPT_SALARY_BRUTTO_COMP");//'TI','ALW28', 'ALW02';         
        inSalBrutto = "'" + inSalBrutto.replaceAll(",", "','") + "'";
        String inPPHBrutto = PstSystemProperty.getValueByName("PAYROLL_ESPT_PPH_COMP");
        inPPHBrutto = "'" + inPPHBrutto.replaceAll(",", "','") + "'";

        Pajak pajak = new Pajak();
        try {
            if (search == 1){
            listEspt = SessESPT.getListEsptA1MonthTestAll(pajak, periodId, divisionId, departmentId, sectionId, inSalBrutto, inPPHBrutto);
            } else {
            listEspt = SessESPT.getListEsptA1Month(pajak, periodId, divisionId, departmentId, sectionId, inSalBrutto, inPPHBrutto);
            }

            if (listEspt != null && listEspt.size() > 0) {
                for (int i = 0; i < listEspt.size(); i++) {
                    // membuat object WarningReprimandAyat berdasarkan objectClass ke-i
                    SrcEsptA1 espt = (SrcEsptA1) listEspt.get(i);
                    countRow++;
                    no++;
                    //countRow = countRow + addRow;
                    row = sheet.createRow((short) (countRow));
                    sheet.createFreezePane(9, 7);

                    short s=0;
       
                    
                    
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getMasaPajak());
                    cell.setCellStyle(style2);

                    cell = row.createCell((short) s++);
                    cell.setCellValue((espt.getTahunPajak().getYear() + 1900));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getPembetulan());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(""+espt.getNomorBuktiPotong());
                    
                    //cell.setCellValue(espt.getNomorBuktiPotong()); //5
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getMasaPerolehanAwal());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getMasaPerolehanAkhir());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getNPWP());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getNIK());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getNama()); 
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getAlamat());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getJenisKelamin());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getStatusPTKP());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getJumlahTanggungan());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getNamaJabatan()); //15
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getWPLuarNegeri());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getKodeNegara());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getKodePajak());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_1(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_2(), "#,###")).replace(",", "")); 
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_3(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_4(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_5(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_6(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_7(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_8(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_9(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_10(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_11(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_12(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_13(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_14(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_15(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_16(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_17(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_18(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_19(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue((Formater.formatNumberVer1(espt.getJumlah_20(), "#,###")).replace(",", ""));
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getStatusPindah());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getNPWPPemotong());
                    cell.setCellStyle(style2);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getNamaPemotong()); 
                    
                    String dateS = Formater.formatDate(espt.getTanggalBuktiPotong(), "dd/MM/yyyy");
                    cell.setCellStyle(styleTitle);
                    cell = row.createCell((short) s++);
                    cell.setCellValue(dateS);
                    
                    cell = row.createCell((short) s++);
                    cell.setCellValue(espt.getEmpNum());
                    cell.setCellStyle(style2);

                }
            }
        } catch (Exception exc) {
            row = sheet.createRow((short) (countRow));
            sheet.createFreezePane(7, 7);
            cell = row.createCell((short) 0);
            cell.setCellValue(no);
            cell.setCellStyle(style2);

            cell = row.createCell((short) 1);
            cell.setCellValue(exc.toString());
            cell.setCellStyle(style2);
        }
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }

    /* Convert Long */
    public long convertLong(double val) {
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return bDecimal.longValue();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
