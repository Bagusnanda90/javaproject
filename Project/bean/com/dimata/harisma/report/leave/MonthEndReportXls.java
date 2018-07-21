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
import com.dimata.harisma.entity.masterdata.LeaveTarget;
import com.dimata.harisma.entity.masterdata.LeaveTargetDetail;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstLeaveTarget;
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
public class MonthEndReportXls extends HttpServlet {
    
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
        response.setContentType("application/x-msexcel");
        //response.setContentType("application/.ods");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Month End Report - Dp, AL, LL");
        
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
        /*
        styleHeader.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleHeader.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
         */
        styleHeader.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        
        HSSFCellStyle styleHeaderBig = wb.createCellStyle();
        styleHeaderBig.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeaderBig.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeaderBig.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        /*
        styleHeaderBig.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleHeaderBig.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleHeaderBig.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleHeaderBig.setBorderRight(HSSFCellStyle.BORDER_THIN);
         * */
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
        
        /* Get Data From Session */
        Vector vListMonthEnd = new Vector(1,1);
        Date currPeriod = new Date();
        LeaveTarget leaveTarget = new LeaveTarget();
        HttpSession sess = request.getSession(true);
        try {               
            vListMonthEnd = (Vector)sess.getValue("SESS_MONTH_END_REPORT");
            currPeriod = new Date(FRMQueryString.requestLong(request, "currPeriod"));
            leaveTarget = PstLeaveTarget.getLeaveTarget(currPeriod);
        } 
        catch (Exception e) {
            System.out.println(e.toString());
            vListMonthEnd = new Vector();
        }        
        //Jika data tidak kosong
        if ((vListMonthEnd != null) && (vListMonthEnd.size() > 0)) {   
            
            // Create a row and put some cells in it. Rows are 0 based.
            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);
            
            DecimalFormat dc = new DecimalFormat("0");
            DecimalFormat dc2 = new DecimalFormat("0.##");
            
            String[] tableTitle = {
                "HARD ROCK HOTEL BALI",
                "MONTH END REPORT - DP, AL, LL",
                "Month : "+Formater.formatDate(currPeriod, "MMMM yyyy")
            };
            String[] tableSubTitle = {
            };

            
            String[] tableHeader = {
                //0
                "NO"                                                
                ,"DEPARTMENT"
                ,"No. of Employee"
                ,"Budget Occ :"
                ,"Actual Occ :"
                ,"DP"
                ,"AL"
                ,"LL"
                ,"Previous Balance"
                ,"Entitle "+Formater.formatDate(currPeriod, "MMM")
                //10
                ,"Sub Total"
                ,"TO TAKE"
                ,"TAKEN"+Formater.formatDate(currPeriod, "MMM")
                ,"% Taken"
                ,"Balance "+Formater.formatDate(currPeriod, "dd.MM.yy")
                ,"Previous Balance"
                ,"Entitle "+Formater.formatDate(currPeriod, "MMM")
                ,"Sub Total"
                ,"TO TAKE"
                ,"TAKEN"+Formater.formatDate(currPeriod, "MMM")
                //20
                ,"% Taken"
                ,"Balance "+Formater.formatDate(currPeriod, "dd.MM.yy")
                ,"Previous Balance"
                ,"Entitle "+Formater.formatDate(currPeriod, "MMM")
                ,"Sub Total"
                ,"TO TAKE"
                ,"TAKEN"+Formater.formatDate(currPeriod, "MMM")
                ,"% Taken"
                ,"Balance "+Formater.formatDate(currPeriod, "dd.MM.yy")
            };
            
            int[][][] mergeCell = {
                //Row Col
                //0
                 {{0,0},{2,0}}
                ,{{0,1},{2,1}}
                ,{{0,2},{2,2}}
                ,{{0,3},{0,13}}
                ,{{0,14},{0,23}}
                ,{{1,3},{1,9}}
                ,{{1,10},{1,16}}
                ,{{1,17},{1,23}}
                ,{{2,3},{2,3}}
                ,{{2,4},{2,4}}
                //10
                ,{{2,5},{2,5}}
                ,{{2,6},{2,6}}
                ,{{2,7},{2,7}}
                ,{{2,8},{2,8}}
                ,{{2,9},{2,9}}
                ,{{2,10},{2,10}}
                ,{{2,11},{2,11}}
                ,{{2,12},{2,12}}
                ,{{2,13},{2,13}}
                ,{{2,14},{2,14}}
                //20
                ,{{2,15},{2,15}}
                ,{{2,16},{2,16}}
                ,{{2,17},{2,17}}
                ,{{2,18},{2,18}}
                ,{{2,19},{2,19}}
                ,{{2,20},{2,20}}
                ,{{2,21},{2,21}}
                ,{{2,22},{2,22}}
                ,{{2,23},{2,23}}
            };
            
            int[][] cellBorder = {
                //Kiri Atas Kanan Bawah
                //0 
                 {1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                //10
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                //20
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1},{1,1,1,1}
                ,{1,1,1,1}
            };
            
            HSSFCellStyle[] styleCell = {
                //0 
                 styleHeader,styleHeader
                ,styleHeader,styleHeaderBig
                ,styleHeaderBig,styleHeaderBig
                ,styleHeaderBig,styleHeaderBig
                ,styleHeader,styleHeader
                //10         
                ,styleHeader,styleHeader
                ,styleHeader,styleHeader
                ,styleHeader,styleHeader
                ,styleHeader,styleHeader
                ,styleHeader,styleHeader
                //20         
                ,styleHeader,styleHeader
                ,styleHeader,styleHeader
                ,styleHeader,styleHeader
                ,styleHeader,styleHeader
                ,styleHeader
            };

            //Untuk mengcount Row
            int countRow = 0;

            ///////////////////////////////////// TITLE /////////////////////////////////
            for (int k = 0; k < tableTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                cell = row.createCell((short) 0);
                cell.setCellValue(tableTitle[k]);
                cell.setCellStyle(styleTitle);
                countRow++;
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
            for (int k = 0; k < tableHeader.length; k++) {
                sheet.addMergedRegion(new Region((mergeCell[k][0][0]+countRow),(short)(mergeCell[k][0][1]),(mergeCell[k][1][0]+countRow),(short)(mergeCell[k][1][1])));
                
                row = sheet.createRow((short) (mergeCell[k][0][0]+countRow));
                cell = row.createCell((short) (mergeCell[k][0][1]));
                ///VALUE
                cell.setCellValue(tableHeader[k]);
                
                
                ///BORDER
                HSSFCellStyle style = styleCell[k];
                //LEFT
                if(cellBorder[k][0]==1){
                    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                }
                if(cellBorder[k][0]==2){
                    style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
                }
                //TOP
                if(cellBorder[k][1]==1){
                    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                }
                if(cellBorder[k][1]==2){
                    style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                }
                //RIGHT
                if(cellBorder[k][2]==1){
                    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                }
                if(cellBorder[k][2]==2){
                    style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                }
                //BOTTOM
                if(cellBorder[k][3]==1){
                    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                }
                if(cellBorder[k][3]==2){
                    style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                }
                cell.setCellStyle(style);
            }
            countRow+= mergeCell[ tableHeader.length-1][1][0]+1;
            ///////////////////////////////////// DATA ////////////////////////////////
             // iterasi sebanyak data Special Leave yang ada
            int iterateNo = 0;
            boolean resultAvailable = false;
            int totalEmp = 0;
            int totalDPBlc = 0;
            int totalDPent = 0;
            int totalDPTOTake = 0;
            int totalDPTaken = 0;
            
            int totalALBlc = 0;
            int totalALent = 0;
            int totalALTOTake = 0;
            int totalALTaken = 0;
            
            int totalLLBlc = 0;
            int totalLLent = 0;
            float totalLLTOTake = 0;
            int totalLLTaken = 0;
            
            for(int i=0;i<vListMonthEnd.size();i++){
                iterateNo += 1;
                
                Vector vSpList = new Vector(1,1);
                vSpList = (Vector)vListMonthEnd.get(i);

                Department dep = new Department();
                String strEmpCount = "";
                Vector vDP = new Vector(1,1);
                Vector vAL = new Vector(1,1);
                Vector vLL = new Vector(1,1);
                LeaveTargetDetail objLeaveTargetDetail = new LeaveTargetDetail();

                String strDPBlc = "";
                String strDPEnt = "";
                String strDPToTaken = "";
                String strDPTaken = "";

                String strALBlc = "";
                String strALEnt = "";
                String strALToTaken = "";
                String strALTaken = "";

                String strLLBlc = "";
                String strLLEnt = "";
                String strLLToTaken = "";
                String strLLTaken = "";
                
                //Inisialisasi Data
                dep = (Department)vSpList.get(0);
                strEmpCount = (String)vSpList.get(1);
                int empCount = Integer.parseInt(strEmpCount);
                vDP = (Vector)vSpList.get(2);
                vAL = (Vector)vSpList.get(3);
                vLL = (Vector)vSpList.get(4);
                
                totalEmp += empCount;
                
                strDPBlc = (String)vDP.get(0);
                strDPEnt = (String)vDP.get(1);
                strDPTaken = (String)vDP.get(2);
                strDPToTaken = ""+dc.format((leaveTarget.getDpTarget()>0?leaveTarget.getDpTarget():0));
                int dpBlc = Integer.parseInt(strDPBlc);
                int dpEnt = Integer.parseInt(strDPEnt);
                float dpToTaken = Float.parseFloat(strDPToTaken);
                int dpTaken = Integer.parseInt(strDPTaken);
                
                totalDPBlc += dpBlc;
                totalDPent += dpEnt;
                totalDPTaken += dpTaken;
                
                strALBlc = (String)vAL.get(0);
                strALEnt = (String)vAL.get(1);
                strALTaken = (String)vAL.get(2);
                strALToTaken = ""+dc.format((leaveTarget.getAlTarget()>0?leaveTarget.getAlTarget():0));
                int alBlc = Integer.parseInt(strALBlc);
                int alEnt = Integer.parseInt(strALEnt);
                float alToTaken = Float.parseFloat(strALToTaken);
                int alTaken = Integer.parseInt(strALTaken);
                
                totalALBlc += alBlc;
                totalALent += alEnt;
                totalALTaken += alTaken;
                
                strLLBlc = (String)vLL.get(0);
                strLLEnt = (String)vLL.get(1);
                strLLTaken = (String)vLL.get(2);
                strLLToTaken = ""+dc.format((leaveTarget.getLlTarget()>0?leaveTarget.getLlTarget():0));
                int llBlc = Integer.parseInt(strLLBlc);
                int llEnt = Integer.parseInt(strLLEnt);
                float llToTaken = Float.parseFloat(strLLToTaken);
                int llTaken = Integer.parseInt(strLLTaken);
                
                totalLLBlc += llBlc;
                totalLLent += llEnt;
                totalLLTaken += llTaken;
                
                
                double dToTakeDp = (dpBlc+dpEnt)*dpToTaken/100;
                double dToTakeAl = (alBlc+alEnt)*alToTaken/100;
                double dToTakeLl = (llBlc+llEnt)*llToTaken/100;
                
                String strToDP = dc.format(dToTakeDp);
                String strToAL = dc.format(dToTakeAl);
                String strToLL = dc.format(dToTakeLl);
                
                int iToTakeDp = Integer.parseInt(strToDP);
                int iToTakeAl = Integer.parseInt(strToAL);
                int iToTakeLl = Integer.parseInt(strToLL);
                
                //Pembuatan Row
                row = sheet.createRow((short) (countRow));
                countRow++;
                
                int collPos = 0;
                                
                //No
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(iterateNo));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Department
                cell = row.createCell((short) collPos);
                cell.setCellValue(dep.getDepartment());//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //No. of employee
                cell = row.createCell((short) collPos);
                cell.setCellValue(strEmpCount);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                //////////////////////////// DP
                //Prev Blc
                cell = row.createCell((short) collPos);
                cell.setCellValue(strDPBlc);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Ent this period
                cell = row.createCell((short) collPos);
                cell.setCellValue(strDPEnt);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Sub Total
                cell = row.createCell((short) collPos);
                cell.setCellValue(""+(dpBlc+dpEnt));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //To Take
                cell = row.createCell((short) collPos);
                cell.setCellValue(strToDP);//Value Here
                cell.setCellStyle(styleValueBold);
                collPos++;
                
                 //Taken this period
                cell = row.createCell((short) collPos);
                cell.setCellValue(strDPTaken);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                double perc = 0;
                if(dToTakeDp<=0){
                    if(dpTaken==0){
                        perc = 0;
                    }else{
                        perc = 100;
                    }
                }else{
                    perc = (dpTaken*100)/iToTakeDp;
                }

                //Perc Taken
                cell = row.createCell((short) collPos);
                cell.setCellValue(dc2.format(perc)+" %");//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Balance End Period
                cell = row.createCell((short) collPos);
                cell.setCellValue(""+(dpBlc+dpEnt-dpTaken));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                
                //////////////////////////// AL
                //Prev Blc
                cell = row.createCell((short) collPos);
                cell.setCellValue(strALBlc);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Ent this period
                cell = row.createCell((short) collPos);
                cell.setCellValue(strALEnt);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Sub Total
                cell = row.createCell((short) collPos);
                cell.setCellValue(""+(alBlc+alEnt));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //To Take
                cell = row.createCell((short) collPos);
                cell.setCellValue(strToAL);//Value Here
                cell.setCellStyle(styleValueBold);
                collPos++;
                
                 //Taken this period
                cell = row.createCell((short) collPos);
                cell.setCellValue(strALTaken);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                if(dToTakeAl<=0){
                    if(alTaken==0){
                        perc = 0;
                    }else{
                        perc = 100;
                    }
                }else{
                    perc = (alTaken*100)/iToTakeAl;
                }

                //Perc Taken
                cell = row.createCell((short) collPos);
                cell.setCellValue(dc2.format(perc)+" %");//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Balance End Period
                cell = row.createCell((short) collPos);
                cell.setCellValue(""+(alBlc+alEnt-alTaken));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                //////////////////////////// LL
                //Prev Blc
                cell = row.createCell((short) collPos);
                cell.setCellValue(strLLBlc);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Ent this period
                cell = row.createCell((short) collPos);
                cell.setCellValue(strLLEnt);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Sub Total
                cell = row.createCell((short) collPos);
                cell.setCellValue(""+(llBlc+llEnt));//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //To Take
                cell = row.createCell((short) collPos);
                cell.setCellValue(strToLL);//Value Here
                cell.setCellStyle(styleValueBold);
                collPos++;
                
                 //Taken this period
                cell = row.createCell((short) collPos);
                cell.setCellValue(strLLTaken);//Value Here
                cell.setCellStyle(styleValue);
                collPos++;
                
                if(dToTakeLl<=0){
                    if(llTaken==0){
                        perc = 0;
                    }else{
                        perc = 100;
                    }
                }else{
                    perc = (llTaken*100)/iToTakeLl;
                }

                //Perc Taken
                cell = row.createCell((short) collPos);
                cell.setCellValue(dc2.format(perc)+" %");//Value Here
                cell.setCellStyle(styleValue);
                collPos++;

                //Balance End Period
                cell = row.createCell((short) collPos);
                cell.setCellValue(""+(llBlc+llEnt-llTaken));//Value Here
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

            //Total
            cell = row.createCell((short) collPos);
            cell.setCellValue("TOTAL");//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;

            //Total Emp
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+totalEmp);//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            
            /////////////////////// DP
            //Total Prev Blc
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+totalDPBlc);//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Ent This Period
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+totalDPent);//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Sub Total
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+(totalDPBlc+totalDPent));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total To Take
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+(dc2.format(totalDPTOTake)));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Taken this Period
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+(totalDPTaken));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Perc Taken
            float perc = 0;
            if(totalDPTOTake<=0){
                perc = 100;
            }else{
                perc = (totalDPTaken*100)/totalDPTOTake;
            }
            cell = row.createCell((short) collPos);
            cell.setCellValue(dc2.format(perc));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Balance End Period
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+((totalDPBlc+totalDPent-totalDPTaken)));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            
            /////////////////////// AL
            //Total Prev Blc
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+totalALBlc);//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Ent This Period
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+totalALent);//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Sub Total
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+(totalALBlc+totalALent));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total To Take
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+(dc2.format(totalALTOTake)));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Taken this Period
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+(totalALTaken));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Perc Taken
            perc = 0;
            if(totalALTOTake<=0){
                perc = 100;
            }else{
                perc = (totalALTaken*100)/totalALTOTake;
            }
            cell = row.createCell((short) collPos);
            cell.setCellValue(dc2.format(perc));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Balance End Period
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+((totalALBlc+totalALent-totalALTaken)));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            
            /////////////////////// LL
            //Total Prev Blc
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+totalLLBlc);//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Ent This Period
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+totalLLent);//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Sub Total
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+(totalLLBlc+totalLLent));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total To Take
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+(dc2.format(totalLLTOTake)));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Taken this Period
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+(totalLLTaken));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Perc Taken
            perc = 0;
            if(totalLLTOTake<=0){
                perc = 100;
            }else{
                perc = (totalLLTaken*100)/totalLLTOTake;
            }
            cell = row.createCell((short) collPos);
            cell.setCellValue(dc2.format(perc));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            //Total Balance End Period
            cell = row.createCell((short) collPos);
            cell.setCellValue(""+((totalLLBlc+totalLLent-totalLLTaken)));//Value Here
            cell.setCellStyle(styleFooter);
            collPos++;
            
            
            int totalDpAlLl = (totalDPBlc+totalDPent+totalALBlc+totalALent-totalALTaken+totalLLBlc+totalLLent);
            int totalDpAlLLTaken = (totalDPTaken+totalALTaken+totalLLTaken);
            float totalDpAlLlTargeted = (totalDPTOTake+totalALTOTake+totalLLTOTake);
            float percTaken = (totalDpAlLLTaken*100)/totalDpAlLl;
            float percTargeted = (totalDpAlLLTaken*100)/totalDpAlLlTargeted;
            
            //Pembuatan Row
            countRow++;
            row = sheet.createRow((short) (countRow));
            countRow++;
            cell = row.createCell((short) 0);
            cell.setCellValue("Previous Taotal DP,AL & LL = "+totalDpAlLl);//Value Here
            cell.setCellStyle(styleSubTitle);
            collPos++;
            
            row = sheet.createRow((short) (countRow));
            countRow++;
            cell = row.createCell((short) 0);
            cell.setCellValue("Actual Taken = "+totalDpAlLLTaken+"             "+dc2.format(percTaken)+"%");//Value Here
            cell.setCellStyle(styleSubTitle);
            collPos++;
            
            row = sheet.createRow((short) (countRow));
            countRow++;
            cell = row.createCell((short) 0);
            cell.setCellValue("Targeted = "+dc2.format(totalDpAlLlTargeted)+"             "+dc2.format(percTargeted));//Value Here
            cell.setCellStyle(styleSubTitle);
            collPos++;
       }
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    } 
}
