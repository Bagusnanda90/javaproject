/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.leave;
// package java

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.payroll.PayGeneral;
import com.dimata.harisma.entity.payroll.PstPayGeneral;
import com.dimata.harisma.entity.search.SrcLeaveManagement;
import com.dimata.harisma.session.leave.RepLevDepartment;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

// package lowagie
import com.lowagie.text.*;

// package qdep
import com.dimata.util.*;
import com.dimata.qdep.form.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook; import org.apache.poi.hssf.util.*;
/**
 *
 * @author roy a.
 */
public class LeaveDetailReportXls extends HttpServlet{
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
        HSSFSheet sheet = wb.createSheet("Report Detail Period - Dp, AL, LL");

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

         //update by devin 2014-04-16
        HSSFCellStyle styleFont = wb.createCellStyle();
        styleFont.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleFont.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleFont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleFont.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFont.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        
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
         
        SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();
        
        HttpSession sess = request.getSession(true);
        
        try {               
            
            srcLeaveManagement = (SrcLeaveManagement)sess.getValue("DETAIL_LEAVE_DP_PERIOD_REPORT");

        }catch(Exception e){
            System.out.println("[exception] "+e.toString());
        } 
        
        if(srcLeaveManagement != null){
            
            
            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);
            
            String[] tableTitle = {
                "",
                "MONTH END DETAIL REPORT - DP, AL, LL",
                "Month : " + Formater.formatDate(new Date(), "MMMM yyyy")
            };
            
            String[] tableSubTitle = {};

            /*Human Resources	0582	I Putu Arya Mudita PREV.	 TKN PREV.	 EXP PREV.	 QTY.	 TAKEN	 EXP	 BAL.	 PREV.	 TAKEN PREV.	 QTY.	 TAKEN	 BAL.	 PREV.	 TAKEN PREV.	 EXP PREV.	 QTY	 TAKEN	 EXP	 BAL. */
            String[] tableHeader = {
                //EMPLOYEE
                "NO", "DEPARTMENT", "EMPLOYEE NUMBER","FULL NAME" 
                //DP
                , "QTY DP PREVIOUS", "TKN DP PREVIOUS", "DP EXPIRED PREVIOUS", "QTY DP", "TKN DP","TKN DP EXPIRED"," BALANCE DP" 
                //AL
                , "QTY AL PREVIOUS", "TKN AL PREVIOUS", "QTY AL", "QTY AL PREVIOUS","TO BE TAKEN","BALANCE AL" 
                //LL
                , "QTY LL PREVIOUS", "TKN LL PREVIOUS", "LL EXPIRED PREVIOUS", "QTY LL", "TKN LL", "EXPIRED LL", "BALANCE LL"
            };            
            
            /**
             *@DESC     :UNTUK COUNT ROW
             */
            
            int countRow = 0;
            int colomAfterName=22;
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
            
            
            int counterData = 1;
            //update by devin 2014-04-16
            int valueSection=0;
            long checkOidDept=0;
            long checkOidSec=0;
            
            float totPQtyDp   = 0;
            float totPTknDP   = 0;
            float totPExpDP   = 0;
            float totQtyDp    = 0;
            float totTknDp    = 0;
            float totExpDp    = 0;
            float totBDP      = 0;
            
            float totPQtyAL   = 0;
            float totPTknAl   = 0;
            float totQtyAl    = 0;
            float totTknAl    = 0;
            float toBeTaken   =  0;
            float totBAL      = 0;
            
            float totPQtyLL   = 0;
            float totPTknLL   = 0;
            float totPExpLL   = 0;
            float totQtyLL    = 0;
            float totTknLL    = 0;
            float totExpLL    = 0;
            float totBLL      = 0;
            /**
             *  int totPQtyDp   = 0;
            int totPTknDP   = 0;
            int totPExpDP   = 0;
            int totQtyDp    = 0;
            int totTknDp    = 0;
            int totExpDp    = 0;
            int totBDP      = 0;
            
            int totPQtyAL   = 0;
            int totPTknAl   = 0;
            int totQtyAl    = 0;
            int totTknAl    = 0;
            int totBAL      = 0;
            
            int totPQtyLL   = 0;
            int totPTknLL   = 0;
            int totPExpLL   = 0;
            int totQtyLL    = 0;
            int totTknLL    = 0;
            int totExpLL    = 0;
            int totBLL      = 0;
             */
            //update by devin 2014-04-16
            long oidCompany=srcLeaveManagement.getCompanyId();
            long oidDivision=srcLeaveManagement.getDivisionId();
            long oidDepartment=srcLeaveManagement.getEmpDeptId();
            long oidSection=srcLeaveManagement.getEmpSectionId();
             String empNum=srcLeaveManagement.getEmpNum();
            String fullName=srcLeaveManagement.getEmpName();
            long empCat=srcLeaveManagement.getEmpCatId();
             long oidDivisionByEmp=0;
            long oidDepartmentByEmp=0;
            long oidSectionByEmp=0;
            if(srcLeaveManagement!=null){
                String whereCompany="";
                Vector listCompany = new Vector();
                if(oidCompany!=0){
                    whereCompany=PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID]+"="+oidCompany;
                }
                if((empNum!=null && empNum.length()>0) || (fullName!=null && fullName.length()>0)){
                   String whereEmpNum=""; 
                   if((empNum!=null && empNum.length()>0) && (fullName=="" && fullName.length()==0)){ 
                         whereEmpNum=PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"="+empNum;
                   }else if((empNum=="" && empNum.length()==0) && (fullName!=null && fullName.length()>0)){
                         whereEmpNum=PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"="+fullName;
                   }else if((empNum!=null && empNum.length()>0) && (fullName!=null && fullName.length()>0)){
                         whereEmpNum=PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"="+empNum+" AND "+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+"="+fullName; 
                   }
            
                  Vector listEmpNum=PstEmployee.list(0, 0, whereEmpNum, "");
                  if(listEmpNum!=null && listEmpNum.size()>0){
                      for(int emp=0;emp<listEmpNum.size();emp++){
                          Employee employee =(Employee)listEmpNum.get(emp); 
                          whereCompany=PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID]+"="+employee.getCompanyId();
                          if(employee.getDivisionId()>0){
                           oidDivisionByEmp=employee.getDivisionId(); 
                          }if(employee.getDepartmentId()>0){
                           oidDepartmentByEmp=employee.getDepartmentId();     
                          }if(employee.getSectionId()>0){
                              oidSectionByEmp=employee.getSectionId();  
                          }
                
                         
                      }
                  }
                  
               }
                listCompany=PstPayGeneral.list(0, 0, whereCompany, "");
                if(listCompany!=null && listCompany.size()>0){
                    for(int vCom=0;vCom<listCompany.size();vCom++){
                        counterData=1;
                         int collPosCom = 0;
                        PayGeneral payGeneral = (PayGeneral)listCompany.get(vCom);
                         row = sheet.createRow((short) (countRow));
                        countRow++;
                        cell = row.createCell((short) collPosCom);
                        cell.setCellValue(String.valueOf(""));
                        cell.setCellStyle(styleValue);
                        collPosCom++;
                        cell = row.createCell((short) collPosCom);
                        cell.setCellValue(String.valueOf(payGeneral.getCompanyName()));
                        cell.setCellStyle(styleFont);
                        collPosCom++;
                        for(int com=0;com<colomAfterName;com++){
                            cell = row.createCell((short) collPosCom);
                            cell.setCellValue(String.valueOf(""));
                            cell.setCellStyle(styleValue);
                            collPosCom++;
                        }
                        String whereClauseDiv="";
                         if(oidCompany!=0 && oidDivision!=0){
                        whereClauseDiv=PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"="+oidDivision;
                    }else if(oidCompany!=0 && oidDivision==0){
                        whereClauseDiv=PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+oidCompany;
                    }else if(oidCompany==0 && oidDivision==0){
                         whereClauseDiv=PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+payGeneral.getOID();
                    }
                         //update by devin 2014-04-18
                      if(oidDivisionByEmp!=0 && oidDivisionByEmp>0){ 
                          whereClauseDiv=PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"="+oidDivisionByEmp;
                      }
                        Vector listDivision =PstDivision.list(0, 0, whereClauseDiv, "");
                        if(listDivision!=null && listDivision.size()>0){
                            for(int vDiv=0;vDiv<listDivision.size();vDiv++){
                                int collDivision = 0;
                         Division division = (Division)listDivision.get(vDiv);
                         row = sheet.createRow((short) (countRow));
                        countRow++;
                        cell = row.createCell((short) collDivision);
                        cell.setCellValue(String.valueOf(counterData));
                        cell.setCellStyle(styleValue);
                        collDivision++;
                        counterData++;
                        cell = row.createCell((short) collDivision);
                        cell.setCellValue(String.valueOf("  -"+division.getDivision()));
                        cell.setCellStyle(styleFont);
                        collDivision++;
                        for(int div=0;div<colomAfterName;div++){
                            cell = row.createCell((short) collDivision);
                            cell.setCellValue(String.valueOf(""));
                            cell.setCellStyle(styleValue);
                            collDivision++;
                        }
                        String whereDept="";
                         if(oidDivision!=0 && oidDepartment!=0){
                        whereDept=PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"="+oidDepartment;
                    }else if(oidDivision!=0 && oidDepartment==0){
                        whereDept=PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+oidDivision;
                    }else if(oidDivision==0 && oidDepartment==0){
                         whereDept=PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+division.getOID();
                    }
                    if(oidDepartmentByEmp!=0 && oidDepartmentByEmp>0){
                          whereDept=PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"="+oidDepartmentByEmp;
                      }
                        Vector listDept =PstDepartment.list(0, 0, whereDept, "");
                        if(listDept!=null && listDept.size()>0){
                            for(int dept=0;dept<listDept.size();dept++){
                                
                                valueSection=0;
                                Department department=(Department)listDept.get(dept);
                                String whereSec="";
                                Vector checkSection=new Vector();
                                if(oidDepartment!=0 && oidSection!=0){
                                    whereSec=PstSection.fieldNames[PstSection.FLD_SECTION_ID]+"="+oidSection;
                                    checkSection=PstSection.list(0, 0, whereSec, "");
                                }else if(oidDepartment!=0 && oidSection==0){
                                    whereSec=PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment; 
                                    checkSection=PstSection.list(0, 0, whereSec, "");
                                }else if(oidDepartment==0 && oidSection==0){
                                     whereSec=PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+department.getOID(); 
                                     checkSection=PstSection.list(0, 0, whereSec, "");
                                }
                                 if(oidCompany!=0){
                                         srcLeaveManagement.setCompanyId(oidCompany);
                                     }else{
                                          srcLeaveManagement.setCompanyId(payGeneral.getOID());
                                     }
                                      if(oidDivision!=0){
                                         srcLeaveManagement.setDivisionId(oidDivision);
                                     }else{
                                          srcLeaveManagement.setDivisionId(division.getOID());
                                     }
                                      if(oidDepartment!=0){
                                         srcLeaveManagement.setEmpDeptId(oidDepartment);
                                     }else{
                                          srcLeaveManagement.setEmpDeptId(department.getOID());
                                     }
                                     if(oidSection!=0){
                                         srcLeaveManagement.setEmpSectionId(oidSection);
                                     }
                                     if(empNum!=null && empNum.length()>0){  
                                   srcLeaveManagement.setEmpNum(empNum);
                              }
                              if(fullName!=null && fullName.length()>0){
                                  srcLeaveManagement.setEmpName(fullName);
                              }
                               if(empCat!=0 && empCat>0){
                                  srcLeaveManagement.setEmpCatId(empCat);
                              }
                                      Vector listData= SessLeaveApplication.leave_Detail(srcLeaveManagement,checkSection,valueSection);
                                      if(listData==null || listData.size()==0){
                                           int collDept = 0;
                                          row = sheet.createRow((short) (countRow));
                        countRow++;
                        cell = row.createCell((short) collDept);
                        cell.setCellValue(String.valueOf(""));
                        cell.setCellStyle(styleValue);
                        collDept++;
                      
                        cell = row.createCell((short) collDept);
                        cell.setCellValue(String.valueOf("    "+department.getDepartment()));
                        cell.setCellStyle(styleFont);
                        collDept++;
                        for(int deptValue=0;deptValue<colomAfterName;deptValue++){
                            cell = row.createCell((short) collDept);
                            cell.setCellValue(String.valueOf(""));
                            cell.setCellStyle(styleValue);
                            collDept++;
                        }
                                      }
                                      if(listData!=null && listData.size()>0){
                                      for(int i = 0; i < listData.size() ; i ++){
                
                int collPos = 0;
                
                RepLevDepartment listRepLevDetail = new RepLevDepartment();
                
                try{
                    listRepLevDetail = (RepLevDepartment)listData.get(i);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
                
                
                /**
                 * @DESC    : CREATE NEW ROW
                 */
                row = sheet.createRow((short) (countRow));
                countRow++;
                
                /**
                 * @DESC    : NO
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(""));
                cell.setCellStyle(styleValue);
                collPos++;
                
                
                Department departmentt = new Department();
                try{
                    departmentt = PstDepartment.fetchExc(listRepLevDetail.getDepartmentId());
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
                
                /**
                 * @DESC    : DEPARTMENT
                 */
                if(checkOidDept!=departmentt.getOID()){
                    checkOidDept=departmentt.getOID();
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf("    "+departmentt.getDepartment()));
                cell.setCellStyle(styleFont);
                collPos++;
                }else{
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(""));
                cell.setCellStyle(styleFont);
                collPos++;  
                }
                
                
                /**
                 * @DESC    : EMP NO
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getEmp_num()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                /**
                 * @DESC    : FULL NAME
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getFull_name()));
                cell.setCellStyle(styleValue);
                collPos++;                
               
                
                /**
                 * @DESC    : DP QTY PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpQtyBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totPQtyDp = totPQtyDp + listRepLevDetail.getDpQtyBeforeStartPeriod();
                
                /**
                 * @DESC    : DP TKN PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpTknBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totPTknDP = totPTknDP + listRepLevDetail.getDpTknBeforeStartPeriod();
                
                /**
                 * @DESC    : DP EXP PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpTknExpBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totPExpDP = totPExpDP + listRepLevDetail.getDpTknExpBeforeStartPeriod();
                
                /**
                 * @DESC    : DP QTY
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpQtyCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totQtyDp = totQtyDp + listRepLevDetail.getDpQtyCurrentPeriod();
                
                /**
                 * @DESC    : DP TKN
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpTknCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totTknDp = totTknDp + listRepLevDetail.getDpTknCurrentPeriod();
                
                /**
                 * @DESC    : DP EXP
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpTknExpiredCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totExpDp = totExpDp + listRepLevDetail.getDpTknExpiredCurrentPeriod();
                
                float balDp = 0;
                //int balDp = 0;
                
                balDp = listRepLevDetail.getDpQtyBeforeStartPeriod() - listRepLevDetail.getDpTknBeforeStartPeriod() - 
                        listRepLevDetail.getDpTknExpBeforeStartPeriod() + listRepLevDetail.getDpQtyCurrentPeriod() - 
                        listRepLevDetail.getDpTknCurrentPeriod() - listRepLevDetail.getDpTknExpiredCurrentPeriod();
                
                /**
                 * @DESC    : BALANCE DP
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(balDp));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totBDP = totBDP + balDp;
                
                
                /**
                 * @DESC    : AL QTY PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlQtyBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;                        
                
                totPQtyAL = totPQtyAL + listRepLevDetail.getAlQtyBeforeStartPeriod();
                
                /**
                 * @DESC    : TKN AL PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlTknBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totPTknAl = totPTknAl + listRepLevDetail.getAlTknBeforeStartPeriod();
                
                /**
                 * @DESC    : AL QTY
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlQtyCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totQtyAl = totQtyAl + listRepLevDetail.getAlQtyCurrentPeriod();
                
                /**
                 * @DESC    : TKN AL
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlTknCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totTknAl = totTknAl + listRepLevDetail.getAlTknCurrentPeriod();                                    
                
                //UPDATE BY DEVIN 2014-04-03
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlToBeTaken()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                toBeTaken = toBeTaken + listRepLevDetail.getAlToBeTaken(); 
                float balAL = 0;
                //int balAL = 0;
                balAL = listRepLevDetail.getAlQtyBeforeStartPeriod() - listRepLevDetail.getAlTknBeforeStartPeriod() + 
                        listRepLevDetail.getAlQtyCurrentPeriod() - listRepLevDetail.getAlTknCurrentPeriod()-listRepLevDetail.getAlToBeTaken();
                
                /**
                 * @DESC    : TKN AL
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(balAL));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totBAL = totBAL + balAL;                
                
                /**
                 * @DESC    : LL QTY PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLQtyBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;                
                
                totPQtyLL = totPQtyLL + listRepLevDetail.getLLQtyBeforeStartPeriod();
                
                /**
                 * @DESC    : TKN LL PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLTknBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totPTknLL = totPTknLL + listRepLevDetail.getLLTknBeforeStartPeriod();
                
                /**
                 * @DESC    : TKN LL EXP PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLTknExpBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totPExpLL = totPExpLL + listRepLevDetail.getLLTknExpBeforeStartPeriod();
                
                /**
                 * @DESC    : QTY LL
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLQtyCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totQtyLL = totQtyLL + listRepLevDetail.getLLQtyCurrentPeriod();
                
                /**
                 * @DESC    : TKN LL
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLTknCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totTknLL = totTknLL + listRepLevDetail.getLLTknCurrentPeriod();
                
                /**
                 * @DESC    : TKN LL EXP
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLTknExpiredCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totExpLL = totExpLL + listRepLevDetail.getLLTknExpiredCurrentPeriod();                
                
                /**
                 * @DESC    : BAL LL
                 */
                float bLL = 0;
                // int bLL = 0;
                bLL     = listRepLevDetail.getLLQtyBeforeStartPeriod() - listRepLevDetail.getLLTknBeforeStartPeriod() - 
                          listRepLevDetail.getLLTknExpBeforeStartPeriod() + listRepLevDetail.getLLQtyCurrentPeriod() - 
                          listRepLevDetail.getLLTknCurrentPeriod() - listRepLevDetail.getLLTknExpiredCurrentPeriod();
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(bLL));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totBLL    = totBLL + bLL;
                
                
            }
                                      }
                if(checkSection!=null && checkSection.size()>0){
                    for(int secc=0;secc<checkSection.size();secc++){ 
                        valueSection=1;
                        Section sectionn=(Section)checkSection.get(secc); 
                        if(oidSection==0){  
                                   srcLeaveManagement.setEmpSectionId(sectionn.getOID()); 
                              }
                                Vector listDataSection= SessLeaveApplication.leave_Detail(srcLeaveManagement,checkSection,valueSection); 
                                if(listDataSection!=null && listDataSection.size()>0){
                                for(int i = 0; i < listDataSection.size() ; i ++){
            
                int collPosSec = 0;
                
                RepLevDepartment listRepLevDetail = new RepLevDepartment();
                
                try{
                    listRepLevDetail = (RepLevDepartment)listDataSection.get(i);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
                
                
                /**
                 * @DESC    : CREATE NEW ROW
                 */
            row = sheet.createRow((short) (countRow));
                countRow++;
            
                /**
                 * @DESC    : NO
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(""));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                
                Section objSection = new Section();
                try{
                    objSection = PstSection.fetchExc(sectionn.getOID());
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
                
                /**
                 * @DESC    : DEPARTMENT
                 */
                if(checkOidSec!=objSection.getOID()){
                    checkOidSec=objSection.getOID();
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf("      "+objSection.getSection()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                }else{
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(""));
                cell.setCellStyle(styleValue);
                collPosSec++;  
                }
                
                
                /**
                 * @DESC    : EMP NO
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getEmp_num()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                /**
                 * @DESC    : FULL NAME
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getFull_name()));
                cell.setCellStyle(styleValue);
                collPosSec++;                
               
                
                /**
                 * @DESC    : DP QTY PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpQtyBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totPQtyDp = totPQtyDp + listRepLevDetail.getDpQtyBeforeStartPeriod();
                
                /**
                 * @DESC    : DP TKN PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpTknBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totPTknDP = totPTknDP + listRepLevDetail.getDpTknBeforeStartPeriod();
                
                /**
                 * @DESC    : DP EXP PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpTknExpBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totPExpDP = totPExpDP + listRepLevDetail.getDpTknExpBeforeStartPeriod();
                
                /**
                 * @DESC    : DP QTY
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpQtyCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totQtyDp = totQtyDp + listRepLevDetail.getDpQtyCurrentPeriod();
                
                /**
                 * @DESC    : DP TKN
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpTknCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totTknDp = totTknDp + listRepLevDetail.getDpTknCurrentPeriod();
                
                /**
                 * @DESC    : DP EXP
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getDpTknExpiredCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totExpDp = totExpDp + listRepLevDetail.getDpTknExpiredCurrentPeriod();
                
                float balDp = 0;
                //int balDp = 0;
                
                balDp = listRepLevDetail.getDpQtyBeforeStartPeriod() - listRepLevDetail.getDpTknBeforeStartPeriod() - 
                        listRepLevDetail.getDpTknExpBeforeStartPeriod() + listRepLevDetail.getDpQtyCurrentPeriod() - 
                        listRepLevDetail.getDpTknCurrentPeriod() - listRepLevDetail.getDpTknExpiredCurrentPeriod();
                
                /**
                 * @DESC    : BALANCE DP
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(balDp));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totBDP = totBDP + balDp;
                
                
                /**
                 * @DESC    : AL QTY PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlQtyBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;                        
                
                totPQtyAL = totPQtyAL + listRepLevDetail.getAlQtyBeforeStartPeriod();
                
                /**
                 * @DESC    : TKN AL PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlTknBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totPTknAl = totPTknAl + listRepLevDetail.getAlTknBeforeStartPeriod();
                
                /**
                 * @DESC    : AL QTY
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlQtyCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totQtyAl = totQtyAl + listRepLevDetail.getAlQtyCurrentPeriod();
                
                /**
                 * @DESC    : TKN AL
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlTknCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totTknAl = totTknAl + listRepLevDetail.getAlTknCurrentPeriod();                                    
                
                //UPDATE BY DEVIN 2014-04-03
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getAlToBeTaken()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                toBeTaken = toBeTaken + listRepLevDetail.getAlToBeTaken(); 
                float balAL = 0;
                //int balAL = 0;
                balAL = listRepLevDetail.getAlQtyBeforeStartPeriod() - listRepLevDetail.getAlTknBeforeStartPeriod() + 
                        listRepLevDetail.getAlQtyCurrentPeriod() - listRepLevDetail.getAlTknCurrentPeriod()-listRepLevDetail.getAlToBeTaken();
                
                /**
                 * @DESC    : TKN AL
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(balAL));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totBAL = totBAL + balAL;                
                
                /**
                 * @DESC    : LL QTY PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLQtyBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;                
                
                totPQtyLL = totPQtyLL + listRepLevDetail.getLLQtyBeforeStartPeriod();
                
                /**
                 * @DESC    : TKN LL PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLTknBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totPTknLL = totPTknLL + listRepLevDetail.getLLTknBeforeStartPeriod();
                
                /**
                 * @DESC    : TKN LL EXP PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLTknExpBeforeStartPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totPExpLL = totPExpLL + listRepLevDetail.getLLTknExpBeforeStartPeriod();
                
                /**
                 * @DESC    : QTY LL
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLQtyCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totQtyLL = totQtyLL + listRepLevDetail.getLLQtyCurrentPeriod();
                
                /**
                 * @DESC    : TKN LL
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLTknCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totTknLL = totTknLL + listRepLevDetail.getLLTknCurrentPeriod();
                
                /**
                 * @DESC    : TKN LL EXP
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listRepLevDetail.getLLTknExpiredCurrentPeriod()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totExpLL = totExpLL + listRepLevDetail.getLLTknExpiredCurrentPeriod();                
                
                /**
                 * @DESC    : BAL LL
                 */
                float bLL = 0;
                // int bLL = 0;
                bLL     = listRepLevDetail.getLLQtyBeforeStartPeriod() - listRepLevDetail.getLLTknBeforeStartPeriod() - 
                          listRepLevDetail.getLLTknExpBeforeStartPeriod() + listRepLevDetail.getLLQtyCurrentPeriod() - 
                          listRepLevDetail.getLLTknCurrentPeriod() - listRepLevDetail.getLLTknExpiredCurrentPeriod();
                
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(bLL));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totBLL    = totBLL + bLL;
                
                
            }
                    }
                                
                    }
                }
                                
                            }
                        }
                        
                                
                            }
                        }
                        
                        
                    }//end loop company
                    }
                }
            
            
            row = sheet.createRow((short) (countRow));
            
            int collPos = 0;            
            
            cell = row.createCell((short) collPos);
            cell.setCellValue("");
            cell.setCellStyle(styleValue);
            collPos++;            
             
            cell = row.createCell((short) collPos);
            cell.setCellValue("TOTAL");
            cell.setCellStyle(styleValue);
            collPos++;            
            
            cell = row.createCell((short) collPos);
            cell.setCellValue("");
            cell.setCellStyle(styleValue);
            collPos++; 
            
            cell = row.createCell((short) collPos);
            cell.setCellValue("");
            cell.setCellStyle(styleValue);
            collPos++;   
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totPQtyDp));
            cell.setCellStyle(styleValue);
            collPos++;   
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totPTknDP));
            cell.setCellStyle(styleValue);
            collPos++;  
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totPExpDP));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totQtyDp));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totTknDp));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totExpDp));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totBDP));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totPQtyAL));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totPTknAl));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totQtyAl));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totTknAl));
            cell.setCellStyle(styleValue);
            collPos++;
            //UPDATE BY DEVIN 2014-04-03
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(toBeTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totBAL));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totPQtyLL));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totPTknLL));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totPExpLL));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totQtyLL));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totTknLL));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totExpLL));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totBLL));
            cell.setCellStyle(styleValue);
            collPos++;
            
            ServletOutputStream sos = response.getOutputStream();
            wb.write(sos);
            sos.close();
            
        }
    }
}
