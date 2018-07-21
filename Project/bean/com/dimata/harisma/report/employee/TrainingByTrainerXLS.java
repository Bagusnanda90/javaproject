/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.employee;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;


import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

import com.dimata.util.*;
import com.dimata.gui.jsp.*;
import com.dimata.qdep.form.*;

//harisma
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.TrainingHistory;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.Training;
import com.dimata.harisma.entity.search.SrcTrainingRpt;
import com.dimata.harisma.session.employee.SessTraining;

/**
 *
 * @author artha
 */
public class TrainingByTrainerXLS   extends HttpServlet {
   
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
        HSSFSheet sheet = wb.createSheet("Training Report");
        
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
        styleHeader.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleValue = wb.createCellStyle();
        styleValue.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValue.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));
        
        /* Get Data From Session */
        Vector listTraining = new Vector();
        SrcTrainingRpt srcTraining = new SrcTrainingRpt();
        HttpSession sess = request.getSession(true);
        try {               
            listTraining = (Vector)sess.getValue("TRAINING_REPORT_TRN_PRINT");

        } 
        catch (Exception e) {
            System.out.println(e.toString());
            listTraining = new Vector();
        }        
        //Jika data tidak kosong
        if ((listTraining != null) && (listTraining.size() > 0)) {                
            Vector vct = (Vector) listTraining.get(0);
            srcTraining = (SrcTrainingRpt)listTraining.get(1);
            
            
            Vector vTempData = (Vector)vct.get(0);
            Position pos = (Position)vTempData.get(1);       
            
            // Create a row and put some cells in it. Rows are 0 based.
            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

            String[] tableTitle = {
                "Report by Trainer"
            };
            String[] tableSubTitle = {
                "Trainer    : "+srcTraining.getTrainer(),
                "Printed Date : "+Formater.formatDate(new Date(), "MMMM dd,yyyy")
            };

            String[] tableHeader = {
                "No"
                ,"Payroll"
                ,"Name"
                ,"Position"
                ,"Department"
                ,"Course Details"
                ,"Date"
                ,"Duration"
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
            int counterEmp = 1; 
            for(int i=0;i<vct.size();i++){
                //Mengambil data
                Vector temp = (Vector) vct.get(i);
                Employee employee = (Employee)temp.get(0);	
                Position position = (Position)temp.get(1);               
                Department department = (Department)temp.get(2);
                Training train = (Training)temp.get(3);
                TrainingHistory hist = (TrainingHistory)temp.get(4);
                
                //Pembuatan Row
                row = sheet.createRow((short) (countRow));
                countRow++;
                
                //Pembuatan Cell
                int collPos = 0;
                if(prevOid != employee.getOID()) {
                    //No
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(counterEmp));//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;
                    counterEmp++;
                    
                    //Payroll
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(employee.getEmployeeNum());//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Name
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(employee.getFullName());//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Position
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(position.getPosition());//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Department
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(department.getDepartment());//Value Here
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

                    //Position
                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;
                    
                    //Department
                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;
                }
                prevOid = employee.getOID();
                //Course Detail
                cell = row.createCell((short) collPos);
                cell.setCellValue(train.getName());//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                //Date
                cell = row.createCell((short) collPos);
                cell.setCellValue(Formater.formatDate(hist.getStartDate(),"dd-MMMM-yyyy"));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                //Duration
                cell = row.createCell((short) collPos);
                cell.setCellValue(SessTraining.getDurationString(hist.getDuration()));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                //Trainer
              /*  cell = row.createCell((short) collPos);
                cell.setCellValue(hist.getTrainer());//Value Here
                cell.setCellStyle(styleValue);
                collPos++; */
            }
        
       }
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
    
    private static HSSFFont createFont(HSSFWorkbook wb, int size,int color,boolean isBold){
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) size);
        font.setColor((short) color);
        if(isBold){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        return font;
    }

}