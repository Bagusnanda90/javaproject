/*
 * SpecialQueryResult.java
 *
 * Created on June 16, 2003, 1:32 PM
 */
 
package com.dimata.harisma.session.appraisal;           

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
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.appraisal.Appraisal;
import com.dimata.harisma.entity.employee.appraisal.AppraisalMain;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisal;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisalMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormItem;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.GroupRank;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.search.SrcAppraisal;
import com.dimata.harisma.session.employee.appraisal.SessAppraisalMain_old;



/** 
 *
 * @author  karya
 * @version 
 */
public class AppraisalResult extends HttpServlet {
   
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
        /* output your page here */
        SrcAppraisal srcAppraisal = new SrcAppraisal();
        HttpSession sessAppraisal = request.getSession(true);
        try{
            srcAppraisal = (SrcAppraisal)sessAppraisal.getValue(SessAppraisalMain_old.SESS_SRC_APPRAISAL_MAIN);
        }catch(Exception e){
            System.out.println("Exc : "+e.toString());
        }
        
        Vector vAppraisalData = new Vector(1,1);
        vAppraisalData = SessAppraisalMain_old.searchAppraisal(srcAppraisal, 0, 0);
        
        
        
        System.out.println("---===| Excel Report |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        //response.setContentType("application/x-msexcel");
        response.setContentType("application/.ods");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Appraisal Result");

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);

        Vector title = PstAssessmentFormItem.getAssessorCommentTitle();
        
        String[] tableHeader = {
            "NO"
            ,"PAYROLL NUMBER"
            ,"NAME"
            ,"DEPARTMENT"
            ,"POSITION"
            ,"LEVEL"
            ,"ASSESOR"
            ,"APPRAISAL DATE"
            ,"APPROVE BY"
            ,"APPROVE DATE"
        };
        row = sheet.createRow((short) (0));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
        //Add Title
        for(int i=tableHeader.length;i<title.size()+tableHeader.length;i++){
            AssessmentFormItem assItem = new AssessmentFormItem();
            assItem = (AssessmentFormItem)title.get(i-tableHeader.length);
            cell = row.createCell((short) i);
            cell.setCellValue(assItem.getTitle()+" ("+assItem.getTitle_L2()+")");
            cell.setCellStyle(style3);
            
            cell = row.createCell((short) (i+1));
            cell.setCellValue(assItem.getItemPoin1()+" ("+assItem.getItemPoin2()+")");
            cell.setCellStyle(style3);
        }
         
        for(int i=0;i<vAppraisalData.size();i++){
            Vector temp = new Vector(1,1);
            temp = (Vector)vAppraisalData.get(i);
            AppraisalMain appraisalMain = (AppraisalMain)temp.get(0);
            Employee employee = (Employee)temp.get(1);
            Employee assessor = (Employee)temp.get(2);
            Department department = (Department)temp.get(3);
            Position position = (Position)temp.get(4);
            Level level = (Level)temp.get(5);
            GroupRank groupRank = (GroupRank)temp.get(6);			
           // Employee devHead = (Employee)temp.get(7);
            
             AppraisalMain appraisalMainFull = new AppraisalMain();
             try{
                appraisalMainFull = PstAppraisalMain.fetchExc(appraisalMain.getOID());
             }catch(Exception ex){}
            
            row = sheet.createRow((short) (i+1));
            //Number
            cell = row.createCell((short) 0);cell.setCellValue(String.valueOf(i+1));cell.setCellStyle(style2);
            //Employee
            cell = row.createCell((short) 1);cell.setCellValue(String.valueOf(employee.getEmployeeNum()));cell.setCellStyle(style2);
            cell = row.createCell((short) 2);cell.setCellValue(String.valueOf(employee.getFullName()));cell.setCellStyle(style2);
            cell = row.createCell((short) 3);cell.setCellValue(String.valueOf(department.getDepartment()));cell.setCellStyle(style2);
            cell = row.createCell((short) 4);cell.setCellValue(String.valueOf(position.getPosition()));cell.setCellStyle(style2);
            cell = row.createCell((short) 5);cell.setCellValue(String.valueOf(level.getLevel()));cell.setCellStyle(style2);
            //Assessor
            cell = row.createCell((short) 6);cell.setCellValue(String.valueOf(assessor.getFullName()));cell.setCellStyle(style2);
            cell = row.createCell((short) 7);cell.setCellValue(String.valueOf(appraisalMainFull.getDateOfAssessment()!=null?Formater.formatDate(appraisalMainFull.getDateOfAssessment(), "dd/MM/yyyy"):""));cell.setCellStyle(style2);
            //Department Head
            if(appraisalMain.getDivisionHeadId()>0){
                Employee divHead = new Employee();
                if(appraisalMain.getOID()>0){
                    try{
                        divHead = PstEmployee.fetchExc(appraisalMain.getDivisionHeadId());
                    }catch(Exception ex){}
                }
                cell = row.createCell((short) 8);cell.setCellValue(String.valueOf(divHead.getFullName()));cell.setCellStyle(style2);
                cell = row.createCell((short) 9);cell.setCellValue(String.valueOf(appraisalMainFull.getDivisionHeadSignDate()!=null?Formater.formatDate(appraisalMainFull.getDivisionHeadSignDate(), "dd/MM/yyyy"):""));cell.setCellStyle(style2);
            }else{
                cell = row.createCell((short) 8);cell.setCellValue(String.valueOf(""));cell.setCellStyle(style2);
                cell = row.createCell((short) 9);cell.setCellValue(String.valueOf(""));cell.setCellStyle(style2);
            }
            
            //Assessor Comments
            Vector vAppraisal = new Vector(1,1);
            vAppraisal = PstAppraisal.getAppraisalAssessorComments(appraisalMain.getOID());
            for(int j=0;j<vAppraisal.size();j++){
                Appraisal app =  new Appraisal();
                app = (Appraisal)vAppraisal.get(j);
                cell = row.createCell((short) (10+j));cell.setCellValue(app.getAssComment()!=null?app.getAssComment():"");cell.setCellStyle(style2);
                cell = row.createCell((short) (11+j));cell.setCellValue(app.getEmpComment()!=null?app.getEmpComment():"");cell.setCellStyle(style2);
            }
            //Untuk memenuhi col
            for(int k=0;k<title.size()-vAppraisal.size();k++){
                cell = row.createCell((short) (11+vAppraisal.size()+k));cell.setCellValue("");cell.setCellStyle(style2);
                cell = row.createCell((short) (12+vAppraisal.size()+k));cell.setCellValue("");cell.setCellStyle(style2);
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

}
