/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.leave;
// package java

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.ListSp;
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
public class LeaveSpUnpaidLeave extends HttpServlet{
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
        HSSFSheet sheet = wb.createSheet("Report Spesial & Unpaid Leave Period - Dp, AL, LL");

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

         //update by devin 2014-04-17
        HSSFCellStyle styleFont = wb.createCellStyle();
        styleFont.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleFont.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleFont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleFont.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFont.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        
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
         
        //Vector vListSp = new Vector();  
        //update by devin 2014-04-17
        SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement(); 
        HttpSession sess = request.getSession(true);
        
        try {               
            
            srcLeaveManagement = (SrcLeaveManagement)sess.getValue("DETAIL_LEAVE_SP_REPORT");

        }catch(Exception e){
            System.out.println("[exception] "+e.toString());
        } 
        
        if(srcLeaveManagement != null){
            
            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);
            
            String[] tableTitle = {
                "MELIA BALI HOTEL",
                "MONTH END DETAIL REPORT - DP, AL, LL",
                "Month : " + Formater.formatDate(new Date(), "MMMM yyyy")
            };
            
            String[] tableSubTitle = {};

            /*Human Resources	0582	I Putu Arya Mudita PREV.	 TKN PREV.	 EXP PREV.	 QTY.	 TAKEN	 EXP	 BAL.	 PREV.	 TAKEN PREV.	 QTY.	 TAKEN	 BAL.	 PREV.	 TAKEN PREV.	 EXP PREV.	 QTY	 TAKEN	 EXP	 BAL. */
            String[] tableHeader = {
                //EMPLOYEE
                "NO", "DEPARTMENT", "EMPLOYEE NUMBER","FULL NAME" 
                //DP
                , "SPECIAL LEAVE TAKEN", "SPECIAL LEAVE 2B TAKEN", "UNPAID LEAVE TAKEN", "UNPAID LEAVE 2B TAKEN"
            };            
            
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
            //update by devin 2014-04-17
            long oidCompany=srcLeaveManagement.getCompanyId();
            long oidDivision=srcLeaveManagement.getDivisionId();
            long oidDepartment=srcLeaveManagement.getEmpDeptId();
            long oidSection=srcLeaveManagement.getEmpSectionId();
            String empNum=srcLeaveManagement.getEmpNum();
            String fullName=srcLeaveManagement.getEmpName();
            long empCat=srcLeaveManagement.getEmpCatId();
            int checklistSec=0;
            long checkDepartment=0;
            long checkSection=0;
            long oidDivisionByEmp=0;
            long oidDepartmentByEmp=0;
            long oidSectionByEmp=0;
            
            String whereCompany="";
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
            Vector listCompany=PstPayGeneral.list(0, 0, whereCompany, "");
            if(listCompany!=null && listCompany.size()>0){
                for(int com=0;com<listCompany.size();com++){
                    PayGeneral payGeneral=(PayGeneral)listCompany.get(com);
                     row = sheet.createRow((short) (countRow));
                     int collPosCom=0;
                     countRow++;
                    cell = row.createCell((short) collPosCom);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPosCom++;
                    cell = row.createCell((short) collPosCom);
                    cell.setCellValue(String.valueOf(payGeneral.getCompanyName()));
                    cell.setCellStyle(styleFont);
                    collPosCom++;
                    for(int intCom=0;intCom<6;intCom++){
                         cell = row.createCell((short) collPosCom);
                        cell.setCellValue(String.valueOf(""));
                        cell.setCellStyle(styleValue);
                        collPosCom++;
                    }
                    String whereDivision="";
                    if(oidCompany!=0 && oidDivision!=0){
                        whereDivision=PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"="+oidDivision;
                    }else if(oidCompany!=0 && oidDivision==0){
                        whereDivision=PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+oidCompany;
                    }else if(oidCompany==0 && oidDivision==0){
                         whereDivision=PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"="+payGeneral.getOID();
                    }
                    //update by devin 2014-04-18
                      if(oidDivisionByEmp!=0 && oidDivisionByEmp>0){ 
                          whereDivision=PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"="+oidDivisionByEmp;
                      }
                    Vector listDivision=PstDivision.list(0, 0, whereDivision, "");
                    if(listDivision!=null && listDivision.size()>0){
                        for(int div=0;div<listDivision.size();div++){
                            Division division=(Division)listDivision.get(div);
                            row = sheet.createRow((short) (countRow));
                     int collPosDiv=0;
                     countRow++;
                    cell = row.createCell((short) collPosDiv);
                    cell.setCellValue(String.valueOf(counterData));
                    cell.setCellStyle(styleValue);
                    collPosDiv++;
                    counterData++;
                    cell = row.createCell((short) collPosDiv);
                    cell.setCellValue(String.valueOf("  "+division.getDivision()));
                    cell.setCellStyle(styleFont);
                    collPosDiv++;
                    for(int intDiv=0;intDiv<6;intDiv++){
                         cell = row.createCell((short) collPosDiv);
                        cell.setCellValue(String.valueOf(""));
                        cell.setCellStyle(styleValue);
                        collPosDiv++;
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
                    Vector listDepartment=PstDepartment.list(0, 0, whereDept, "");
                    if(listDepartment!=null && listDepartment.size()>0){
                        for(int dept=0;dept<listDepartment.size();dept++){
                            checklistSec=0;
                            Department department =(Department)listDepartment.get(dept);
                            String whereSection="";
                            Vector listDataSection = new Vector();
                            if(oidDepartment!=0 && oidSection!=0){
                                whereSection=PstSection.fieldNames[PstSection.FLD_SECTION_ID]+"="+oidSection;
                                listDataSection=PstSection.list(0, 0, whereSection, "");
                            }else if(oidDepartment!=0 && oidSection==0){
                                 whereSection=PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
                                 listDataSection=PstSection.list(0, 0, whereSection, "");
                            }else if(oidDepartment==0 && oidSection==0){
                                 whereSection=PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+department.getOID();
                                 listDataSection=PstSection.list(0, 0, whereSection, "");
                            }
                            if(oidSectionByEmp!=0 && oidSectionByEmp>0){
                                   whereSection=PstSection.fieldNames[PstSection.FLD_SECTION_ID]+"="+oidSectionByEmp;
                                  listDataSection=PstSection.list(0, 0, whereSection, "");
                              }
                            if(oidCompany==0){
                                srcLeaveManagement.setCompanyId(payGeneral.getOID());
                            }
                            if(oidDivision==0){
                                srcLeaveManagement.setDivisionId(division.getOID());
                            }
                            if(oidDepartment==0){
                                srcLeaveManagement.setEmpDeptId(department.getOID());
                            }if(empNum!=null && empNum.length()>0){  
                                   srcLeaveManagement.setEmpNum(empNum);
                              }
                              if(fullName!=null && fullName.length()>0){
                                  srcLeaveManagement.setEmpName(fullName);
                              }
                               if(empCat!=0 && empCat>0){
                                  srcLeaveManagement.setEmpCatId(empCat);
                              }
                            Vector listDataDept = SessLeaveApplication.listSchedule(srcLeaveManagement,checklistSec,listDataSection);
                            if(listDataDept==null || listDataDept.size()==0){
                                row = sheet.createRow((short) (countRow));
                     int collPosDepartment=0;
                     countRow++;
                    cell = row.createCell((short) collPosDepartment);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPosDepartment++;
                   
                    cell = row.createCell((short) collPosDepartment);
                    cell.setCellValue(String.valueOf("  "+department.getDepartment()));
                    cell.setCellStyle(styleFont);
                    collPosDepartment++;
                    for(int intDiv=0;intDiv<6;intDiv++){
                         cell = row.createCell((short) collPosDepartment);
                        cell.setCellValue(String.valueOf(""));
                        cell.setCellStyle(styleValue);
                        collPosDepartment++;
                    }
                            }
                            if(listDataDept!=null && listDataDept.size()>0){
                                for(int deptValue=0;deptValue<listDataDept.size();deptValue++){
                                    
                int collPos = 0;
                
                ListSp listSp = new ListSp();
                
                try{
                    listSp = (ListSp)listDataDept.get(deptValue); 
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
                
                
                Department objdepartment = new Department();
                try{
                    objdepartment = PstDepartment.fetchExc(listSp.getDepartmentId());
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
                
                /**
                 * @DESC    : DEPARTMENT
                 */
                if(checkDepartment!=objdepartment.getOID()){
                    checkDepartment=objdepartment.getOID();
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf("    "+objdepartment.getDepartment()));
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
                cell.setCellValue(String.valueOf(listSp.getEmployeeNum()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                /**
                 * @DESC    : FULL NAME
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSp.getFullName()));
                cell.setCellStyle(styleValue);
                collPos++;                
               
                
                /**
                 * @DESC    : DP QTY PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSp.getTakenSp()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totPQtyDp = totPQtyDp + listSp.getTakenSp();
                
                /**
                 * @DESC    : DP TKN PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSp.getToBeTakenSp()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totPTknDP = totPTknDP + listSp.getToBeTakenSp();
                
                /**
                 * @DESC    : DP EXP PREVIOUS
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSp.getTakenUnp()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totPExpDP = totPExpDP + listSp.getTakenUnp();
                
                /**
                 * @DESC    : DP QTY
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSp.getTobeTakenUnp()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totQtyDp = totQtyDp + listSp.getTobeTakenUnp();
                
                /**
                 * @DESC    : DP TKN
                 */
                                }
                
                                if(listDataSection!=null && listDataSection.size()>0){
                                    for(int c=0;c<listDataSection.size();c++){
                                        Section section =(Section)listDataSection.get(c);
                                        checklistSec=1;
                                        if(oidSection==0){
                                            srcLeaveManagement.setEmpSectionId(section.getOID());
                                        }
                                         Vector listDataSec = SessLeaveApplication.listSchedule(srcLeaveManagement,checklistSec,listDataSection); 
                                         if(listDataSec!=null && listDataSec.size()>0){
                                             for(int intSec=0;intSec<listDataSec.size();intSec++){
                int collPosSec = 0;
                
                ListSp listSp = new ListSp();
                
                try{
                    listSp = (ListSp)listDataSec.get(intSec); 
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
                    objSection = PstSection.fetchExc(section.getOID());
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
                
                /**
                 * @DESC    : DEPARTMENT
                 */
                if(checkSection!=objSection.getOID()){
                   checkSection=objSection.getOID();
                    cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(objSection.getSection()));
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
                cell.setCellValue(String.valueOf(listSp.getEmployeeNum()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                /**
                 * @DESC    : FULL NAME
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listSp.getFullName()));
                cell.setCellStyle(styleValue);
                collPosSec++;                
               
                
                /**
                 * @DESC    : DP QTY PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listSp.getTakenSp()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totPQtyDp = totPQtyDp + listSp.getTakenSp();
                
                /**
                 * @DESC    : DP TKN PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listSp.getToBeTakenSp()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totPTknDP = totPTknDP + listSp.getToBeTakenSp();
                
                /**
                 * @DESC    : DP EXP PREVIOUS
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listSp.getTakenUnp()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totPExpDP = totPExpDP + listSp.getTakenUnp();
                
                /**
                 * @DESC    : DP QTY
                 */
                cell = row.createCell((short) collPosSec);
                cell.setCellValue(String.valueOf(listSp.getTobeTakenUnp()));
                cell.setCellStyle(styleValue);
                collPosSec++;
                
                totQtyDp = totQtyDp + listSp.getTobeTakenUnp();
                
                /**
                 * @DESC    : DP TKN
                 */
                                             }
                                         }
                                        
                                    }
                                }
                            }
                        }
                    }
                        }
                    }
                    
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
            
            
            
            ServletOutputStream sos = response.getOutputStream();
            wb.write(sos);
            sos.close();
            
        }
    }
}
