/*
 * EmpScheduleListXLS.java
 *
 * Created on October 16, 2002, 12:08 PM
 */
 
package com.dimata.harisma.report;           

import com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentMain;
import com.dimata.harisma.entity.configrewardnpunisment.RewardnPunismentMain;
import com.dimata.harisma.entity.configrewardnpunisment.SrcRewardPunishment;
import com.dimata.harisma.entity.jenisSo.PstJenisSo;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.harisma.session.configrewardpunishment.SessSpecialPstSrcRewardPunisment;
import com.dimata.harisma.session.configrewardpunishment.SessTmpSrcRewardPunisment;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Formater;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

/** 
 *
 * @author  karya
 * @version 
 */
public class RpXLS extends HttpServlet {
   
    /** Initializes the servlet.
    */  
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        System.out.println("---===| Excel Report |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("REWARD PUNISMENT");

        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFRow rowx = sheet.createRow((short) 0);
        HSSFRow rownama = sheet.createRow((short) 0);
        HSSFRow rowtanggal = sheet.createRow((short) 0);
        
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("REWARD PUNISMENT");
        cell.setCellStyle(style);
        for (int j = 1; j < 21; j++) {
            cell = row.createCell((short) j);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)20));

        row = sheet.createRow((short) (1));
        for (int k = 0; k < 21; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue("");
            cell.setCellStyle(style4);
        }

        String[] tableHeader = {
            "NIK", "Nama", "Jabatan","%"," Hr Kerja","Adjusment","Total","Beban","Tunai","Penambahan Gaji","Lama Masa Cicilan","Status Opname"

        };
        
        row = sheet.createRow((short) (2));
        long viewDirect = FRMQueryString.requestLong(request, "viewDirect");
        try{
        RewardnPunismentMain rewardnPunismentMain =  PstRewardAndPunishmentMain.fetchExc(viewDirect);//
        String namalokasi = PstLocation.GetNamaLocation(rewardnPunismentMain.getLocationId());
        String jenisSo = PstJenisSo.GetNamaJenisSo(rewardnPunismentMain.getJenisSoId());
            cell = row.createCell((short) 0);cell.setCellValue(namalokasi);cell.setCellStyle(style2);            
            cell = row.createCell((short) 1);cell.setCellValue(jenisSo);cell.setCellStyle(style2);
            cell = row.createCell((short) 2);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 3);cell.setCellValue(String.valueOf(rewardnPunismentMain.getDtFromPeriod()));cell.setCellStyle(style2);
            cell = row.createCell((short) 4);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 5);cell.setCellValue(String.valueOf(rewardnPunismentMain.getDtToPeriod()));cell.setCellStyle(style2);
            cell = row.createCell((short) 6);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 7);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 8);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 9);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 10);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 11);cell.setCellValue("");cell.setCellStyle(style2);
        } catch (Exception e){
            
        }
        
        row = sheet.createRow((short) (3));
        try{
        RewardnPunismentMain rewardnPunismentMain =  PstRewardAndPunishmentMain.fetchExc(viewDirect);//
            cell = row.createCell((short) 0);cell.setCellValue("Periode ");cell.setCellStyle(style2);            
            cell = row.createCell((short) 1);cell.setCellValue(String.valueOf(rewardnPunismentMain.getDtFromPeriod()));cell.setCellStyle(style2);
            cell = row.createCell((short) 2);cell.setCellValue("-");cell.setCellStyle(style2);
            cell = row.createCell((short) 3);cell.setCellValue(String.valueOf(rewardnPunismentMain.getDtToPeriod()));cell.setCellStyle(style2);
            cell = row.createCell((short) 4);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 5);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 6);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 7);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 8);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 9);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 10);cell.setCellValue("");cell.setCellStyle(style2);
            cell = row.createCell((short) 11);cell.setCellValue("");cell.setCellStyle(style2);
        } catch (Exception e){
            
        }
        

        
         
        row = sheet.createRow((short) (6));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
        
        SessSpecialPstSrcRewardPunisment sessSpecialPstSrcRewardPunisment = new SessSpecialPstSrcRewardPunisment();
        SrcRewardPunishment srcRewardPunishment = new SrcRewardPunishment();
        
        HttpSession session = request.getSession();
        
        //srcRewardPunishment = (SrcRewardPunishment) session.getValue(sessSpecialPstSrcRewardPunisment.SESS_SRC_REWARD_PUNISMENT);
        Vector listData = (Vector) session.getValue(sessSpecialPstSrcRewardPunisment.SESS_SRC_REWARD_PUNISMENT);
        //String d = "";
        double totaltotal = 0;
        double totalbeban = 0;
        
        for (int i = 0; i < listData.size(); i++) {
            SessTmpSrcRewardPunisment sessTmpSrcRewardPunisment = (SessTmpSrcRewardPunisment) listData.get(i);
         
            row = sheet.createRow((short) (i+7));
            
            //"Payroll", "Full Name", "Address", "Postal Code", "Phone", 
            cell = row.createCell((short) 0);cell.setCellValue(String.valueOf(sessTmpSrcRewardPunisment.getEmpnumber()));cell.setCellStyle(style2);            
            cell = row.createCell((short) 1);cell.setCellValue(sessTmpSrcRewardPunisment.getFullNameEmp());cell.setCellStyle(style2);
            cell = row.createCell((short) 2);cell.setCellValue(sessTmpSrcRewardPunisment.getEmpposition());cell.setCellStyle(style2);
            cell = row.createCell((short) 3);cell.setCellValue(String.valueOf(sessTmpSrcRewardPunisment.getKoefisien()));cell.setCellStyle(style2);
            cell = row.createCell((short) 4);cell.setCellValue(String.valueOf(sessTmpSrcRewardPunisment.getHarikerja()));cell.setCellStyle(style2);
            cell = row.createCell((short) 5);cell.setCellValue(String.valueOf(sessTmpSrcRewardPunisment.getAdjusment()));cell.setCellStyle(style2);
            cell = row.createCell((short) 6);cell.setCellValue(String.valueOf(sessTmpSrcRewardPunisment.getTotal()));cell.setCellStyle(style2);
            cell = row.createCell((short) 7);cell.setCellValue(String.valueOf(Formater.formatNumber(sessTmpSrcRewardPunisment.getBeban(), "#,###.##")));cell.setCellStyle(style2);
            cell = row.createCell((short) 8);cell.setCellValue(String.valueOf(sessTmpSrcRewardPunisment.getTunai()));cell.setCellStyle(style2);
            cell = row.createCell((short) 9);cell.setCellValue(String.valueOf(Formater.formatNumber(sessTmpSrcRewardPunisment.getPotongangaji(), "#,###.##")));cell.setCellStyle(style2);
            cell = row.createCell((short) 10);cell.setCellValue(String.valueOf(sessTmpSrcRewardPunisment.getMasacicilan()));cell.setCellStyle(style2);
            cell = row.createCell((short) 11);cell.setCellValue(String.valueOf(sessTmpSrcRewardPunisment.getStatuOpname()));cell.setCellStyle(style2);
            
            
            totaltotal = totaltotal+sessTmpSrcRewardPunisment.getTotal();
            totalbeban = totalbeban+sessTmpSrcRewardPunisment.getBeban();
        }
            rowx = sheet.createRow((short) (listData.size()+10));
            
            cell = rowx.createCell((short) 0);cell.setCellValue("");cell.setCellStyle(style2);            
            cell = rowx.createCell((short) 1);cell.setCellValue(" TOTAL ");cell.setCellStyle(style2);
            cell = rowx.createCell((short) 2);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowx.createCell((short) 3);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowx.createCell((short) 4);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowx.createCell((short) 5);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowx.createCell((short) 6);cell.setCellValue(String.valueOf(totaltotal));cell.setCellStyle(style2);
            cell = rowx.createCell((short) 7);cell.setCellValue(String.valueOf(Formater.formatNumber(totalbeban, "#,###.##")));cell.setCellStyle(style2);
            cell = rowx.createCell((short) 8);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowx.createCell((short) 9);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowx.createCell((short) 10);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowx.createCell((short) 10);cell.setCellValue("");cell.setCellStyle(style2);
            
            Date sekarang = new  Date();
            //Date newsekarang = Formater.formatDate(sekarang, "YYYY-MM-dd");
            rowtanggal = sheet.createRow((short) (listData.size()+11));
            cell = rowtanggal.createCell((short) 0);cell.setCellValue(sekarang);cell.setCellStyle(style2);            
            cell = rowtanggal.createCell((short) 1);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 2);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 3);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 4);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 5);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 6);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 7);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 8);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 9);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 10);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rowtanggal.createCell((short) 10);cell.setCellValue("");cell.setCellStyle(style2);
            
            rownama = sheet.createRow((short) (listData.size()+13));
            cell = rownama.createCell((short) 0);cell.setCellValue("Dibuat Oleh");cell.setCellStyle(style2);            
            cell = rownama.createCell((short) 1);cell.setCellValue(" TOTAL ");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 2);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 3);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 4);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 5);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 6);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 7);cell.setCellValue("Mengetahui ");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 8);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 9);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 10);cell.setCellValue("");cell.setCellStyle(style2);
            cell = rownama.createCell((short) 10);cell.setCellValue("");cell.setCellStyle(style2);
        // Write the output to a file
        //FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        //wb.write(fileOut);
        //fileOut.close();        
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    } 


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

}
