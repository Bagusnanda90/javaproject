/*
 * DpApplicationPdf.java
 *
 * Created on January 12, 2005, 12:02 PM
 */

package com.dimata.harisma.report.leave;

// package java
import com.dimata.harisma.entity.attendance.SpecialLeave;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.dp.DpAppDetail;
import com.dimata.harisma.entity.leave.dp.DpAppMain;
import com.dimata.harisma.entity.leave.dp.PstDpAppMain;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.session.leave.dp.SessDpAppDetail;
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

// package lowagie
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

// package qdep
import com.dimata.util.*;
import com.dimata.qdep.form.*;
import com.dimata.system.entity.PstSystemProperty;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook; import org.apache.poi.hssf.util.*;
import org.apache.poi.hssf.util.Region;

/**
 *
 * @author  gedhy
 */
public class UnpaidLeaveReportXls extends HttpServlet {
    
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
    
    private static HSSFFont createFont(HSSFWorkbook wb, int size,int color,boolean isBold){
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) size);
        font.setColor((short) color);
        if(isBold){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        return font;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        //response.setContentType("text/html");
        /*
        response.setContentType("application/x-msexcel");
        java.io.PrintWriter out = response.getWriter();
         */
        
        System.out.println("---===| Excel Report |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        //response.setContentType("application/x-msexcel");
        response.setContentType("application/.ods");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Unpaid Leave Report");
        
        //Stile
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
        styleHeader.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleHeader.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleHeader.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        
        HSSFCellStyle styleFooter = wb.createCellStyle();
        styleFooter.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleFooter.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleFooter.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFooter.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleFooter.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleFooter.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleValue = wb.createCellStyle();
        styleValue.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleValue.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));
        
        /* Get Data From Session */
        Vector vListUnpaidLeave = new Vector(1,1);
        String strYear = "";
        HttpSession sess = request.getSession(true);
        try {               
            vListUnpaidLeave = (Vector)sess.getValue("SESS_UNPAID_LEAVE");
            strYear = FRMQueryString.requestString(request, "year");
        } 
        catch (Exception e) {
            System.out.println(e.toString());
            vListUnpaidLeave = new Vector();
        }        
        //Jika data tidak kosong
        if ((vListUnpaidLeave != null) && (vListUnpaidLeave.size() > 0)) {   
            
            // Create a row and put some cells in it. Rows are 0 based.
            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

            String[] tableTitle = {
                "HARD ROCK HOTEL BALI",
                "BAND MEMBER UNPAID LEAVE"
            };
            String[] tableSubTitle = {
                "Period "+strYear
            };

            String[] tableHeader = {
                "No"
                ,"Payroll"
                ,"Name"
                ,"Department"
                ,"Position"
                ,"No. of days req"
                ,"Unpaid Leave From"
                ,"Unpaid Leave To"
                ,"Remark"
            };

            //Untuk mengcount Row
            int countRow = 0;

            ///////////////////////////////////// TITLE /////////////////////////////////
            for (int k = 0; k < tableTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableTitle[k]);
                cell.setCellStyle(styleTitle);
            }

            ///////////////////////////////////// SUB TITLE /////////////////////////////////
            for (int k = 0; k < tableSubTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableSubTitle[k]);
                cell.setCellStyle(styleSubTitle);
            }

            ///////////////////////////////////// HEADER /////////////////////////////////
            row = sheet.createRow((short) (countRow));
            countRow++;
            for (int k = 0; k < tableHeader.length; k++) {
                cell = row.createCell((short) k);
                cell.setCellValue(tableHeader[k]);
                cell.setCellStyle(styleHeader);
            }
            
            ///////////////////////////////////// DATA /////////////////////////////////
            long prevOid = 0;
            int counterEmp = 0; 
            int total = 0;
            for(int i=0;i<vListUnpaidLeave.size();i++){
                //Mengambil data
                 Vector vSpList = new Vector(1,1);
                vSpList = (Vector)vListUnpaidLeave.get(i);

                SpecialLeave sp = new SpecialLeave();
                Employee emp = new Employee();
                Department dep = new Department();
                Position pos = new Position();
                Vector vDetail = new Vector(1,1);

                
                counterEmp += 1;
                sp = (SpecialLeave)vSpList.get(0);
                emp = (Employee)vSpList.get(1);
                dep = (Department)vSpList.get(2);
                pos = (Position)vSpList.get(3);
                vDetail = (Vector)vSpList.get(4);

                String strCount = (String)vDetail.get(0);

                int count = Integer.parseInt(strCount);
                Date dtStart = (Date)vDetail.get(1);
                Date dtEnd = (Date)vDetail.get(2);

                total += count;
                
                String strRes =(sp.getUnpaidLeaveReason()!=null?sp.getUnpaidLeaveReason():"");
                String strRem =(sp.getOtherRemarks()!=null?sp.getOtherRemarks():"");
                String strResAndRem = "";
                if(strRes.length()>0){
                    strResAndRem = strRes;
                    if(strRem.length()>0){
                        strResAndRem += "\n("+strRem+")";
                    }
                }else{
                    if(strRem.length()>0){
                        strResAndRem += "("+strRem+")";
                    }
                }
                
                //Pembuatan Row
                row = sheet.createRow((short) (countRow));
                countRow++;
                
                //Pembuatan Cell
                int collPos = 0;
                if(prevOid != emp.getOID()) {
                    //No
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(counterEmp));//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;
                    counterEmp++;
                    
                    //Payroll
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(emp.getEmployeeNum());//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Name
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(emp.getFullName());//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Department
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(dep.getDepartment());//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Position
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(pos.getPosition());//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;
                }else{
                    //No
                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;
                    
                    //Payroll
                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Name
                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Department
                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;
                    
                    //Position
                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;
                }
                prevOid = emp.getOID();
                
                //No of syas request
                cell = row.createCell((short) collPos);
                cell.setCellValue(strCount);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                //Unpaid Leave Start
                cell = row.createCell((short) collPos);
                cell.setCellValue(Formater.formatDate(dtStart,"dd-MMMM-yyyy"));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                //Unpaid Leave To
                cell = row.createCell((short) collPos);
                cell.setCellValue(Formater.formatDate(dtEnd,"dd-MMMM-yyyy"));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                
                
                //Remark
                cell = row.createCell((short) collPos);
                cell.setCellValue(strResAndRem);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
            }
            //Pembuatan Row
            row = sheet.createRow((short) (countRow));
            countRow++;
            
            int collPos = 0;
            //No
            cell = row.createCell((short) collPos);
            cell.setCellValue("");//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;

            //Payroll
            cell = row.createCell((short) collPos);
            cell.setCellValue("");//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;

            //Name
            cell = row.createCell((short) collPos);
            cell.setCellValue("TOTAL");//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;

            //Department
            cell = row.createCell((short) collPos);
            cell.setCellValue("");//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;

            //Position
            cell = row.createCell((short) collPos);
            cell.setCellValue("");//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;

                //No of syas request
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+total);//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;

            //Unpaid Leave Start
            cell = row.createCell((short) collPos);
            cell.setCellValue("");//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;

            //Unpaid Leave To
            cell = row.createCell((short) collPos);
            cell.setCellValue("");//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;

            //Remark
            cell = row.createCell((short) collPos);
            cell.setCellValue("");//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
        
       }
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    } 
}
