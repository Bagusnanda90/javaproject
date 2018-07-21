/*
 * EmpScheduleListXLS.java
 *
 * Created on October 16, 2002, 12:08 PM
 */
package com.dimata.harisma.report;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

//test aja
import com.dimata.qdep.form.*;
//
import java.sql.*;//tambah
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.session.employee.*;
import com.dimata.harisma.util.email;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import javax.activation.DataSource;
//import javax.mail.util.ByteArrayDataSource;

/** 
 *
 * @author  karya
 * @version 
 */
public class EmployeeListEmail extends HttpServlet {

    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /** Destroys the servlet.
     */
    public void destroy() {
    }

    
    //}

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
        ServletOutputStream out = response.getOutputStream();
        
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Employee List");
        /* BY KARTIKA , di hide sementara tgl. 25 September 2012
        HSSFSheet sheetFamily = wb.createSheet("Family List");
        HSSFSheet sheetCarrier = wb.createSheet("Caarier List");
        **/

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
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);

        
        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

     
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("EMPLOYEE LIST");
        cell.setCellStyle(style);
        HttpSession session = request.getSession();
//        srcEmployee = (SrcEmployee) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
        //update by satrya 2013-08-05
        Vector listEmployee = new Vector(1, 1);
        //listEmployee = sessEmployee.searchEmployee(srcEmployee, 0, 5000);
        //update by satrya 2013-08-05
        SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
        searchSpecialQuery = (SearchSpecialQuery)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
        listEmployee = SessSpecialEmployee.searchSpecialEmployee(searchSpecialQuery,0,50000); 
        
        String emailAddress = (String)session.getValue("emailAddress");
        
        //x pdfWriter = null;
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Vector<DataSource> attachment = new Vector(); // by kartika 2015-09-12
        Vector<String> attachmentName = new Vector(); // by kartika 2015-09-12
        Vector<String> emailLogs = new Vector();
        emailLogs.add("List send via email by Dimata Hairisma System on "+ Formater.formatDate(new java.util.Date(),"yyyy-MM-dd hh:mm:ss"));
        
       
        
        //String d = "";

        String[] tableHeader = {

            "NO","PAYROLL","BARCODE_NUMBER","ID CARD","FULL_NAME","COMPANY","DIVISION","DEPARTEMENT","SECTION","EMP. CATEGORY"
            ,"LEVEL","POSITION","RELIGION","GENDER","EDUCATION","GRADUATION","START_DATE_EDUCATION","END_DATE_EDUCATION"
            ,"LANGUAGE","ORAL","WRITTEN","DESCRIPTION","MARITAL STATUS","TAX REPORT","BLOOD"
            ,"JAMSOS NUM","JAMSOS DATE","COMMERCING DATE","RESIGN DATE","RESIGN REASON","RESIGN DESC"
            ,"LOCKER","ADDRESS","PERMANENT ADDRESS","PHONE","HAND PHONE","EMERGENSI PHONE / PERSON NAME"
            ,"POSTAL CODE","BIRTH PLACE","BIRTH DATE","AGE","RACE","FATHER NAME","MOTHER NAME",	"PARENTS ADDRESS"
            ,"FAMILY MEMBER NAME","FAMILY GENDER","FAMILY RELATIONSHIP","FAMLILY GUARANTED","FAMILY DATE OF BIRTH","FAMILY AGE","FAMILY RELIGION"
            ,"FAMILY JOB ADRESS","FAMILY LAST EDUCATION","FAMILY ADDRESS","END DATE CONTRACT","ID Card Type","NPWP","BPJS_NO","BPJS_DATE","SHIO","ELEMEN","IQ","EQ","PROBATION END DATE","STATUS PENSIUN","START DATE PENSIUN","PRESENCE CHECK PARAMETER",
            "MEDICAL INFO","WACOMPANY","WADIVISION","WADEPARTMENT","WASECTION","WAPOSITION","GRADE"

        };
        row = sheet.createRow((short) (0));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);   
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
      int countRow = 1;
       int addRow =0;
     if(listEmployee!=null&&listEmployee.size()>0){
         SessTmpSpecialEmployee sessTmpSpecialEmployee = new SessTmpSpecialEmployee();
          Vector listEducation = PstEmpEducation.listEducation("", PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+","+PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]+ " ASC ");
          Vector listLanguage = PstEmpLanguage.listEmpLanguage("", PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+ " ASC ");
          Vector listFamily = PstFamilyMember.listEmpFamily("", PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+","+PstFamilyMember.fieldNames[PstFamilyMember.FLD_BIRTH_DATE]+ " ASC ");
          int no=1;
          int tmpx=0;
        for (int i = 0; i < listEmployee.size(); i++) {
            sessTmpSpecialEmployee =  (SessTmpSpecialEmployee) listEmployee.get(i);
          try{
              countRow =tmpx>0 && addRow==0?countRow+1:countRow++;
            countRow = countRow + addRow;
            row = sheet.createRow((short) (countRow));
             
           // row = sheet.createRow((short) (i+1));
            
            //"Payroll", "Full Name", "Department", "Section", "Position", 
             //modif by satrya 2013-08-01
           
            sheet.createFreezePane(5, 1);
            if(sessTmpSpecialEmployee.getEmployeeNum()!=null && sessTmpSpecialEmployee.getEmployeeNum().length()>0){ // sessTmpSpecialEmployee.getEmployeeNum().equalsIgnoreCase("25043")){
                
                cell = row.createCell((short) 0);cell.setCellValue(no);cell.setCellStyle(style2);    
                no++;
            }
            cell = row.createCell((short) 1);cell.setCellValue(sessTmpSpecialEmployee.getEmployeeNum()!=null && sessTmpSpecialEmployee.getEmployeeNum().length()>0?sessTmpSpecialEmployee.getEmployeeNum():"-");cell.setCellStyle(style2);            
            cell = row.createCell((short) 2);cell.setCellValue(sessTmpSpecialEmployee.getBarcodeNumber()!=null&& sessTmpSpecialEmployee.getBarcodeNumber().length()>0?sessTmpSpecialEmployee.getBarcodeNumber():"-");cell.setCellStyle(style2);   
            cell = row.createCell((short) 3);cell.setCellValue(sessTmpSpecialEmployee.getIndentCardNr()!=null&& sessTmpSpecialEmployee.getIndentCardNr().length()>0?sessTmpSpecialEmployee.getIndentCardNr():"-");cell.setCellStyle(style2);   
            cell = row.createCell((short) 4);cell.setCellValue(sessTmpSpecialEmployee.getFullName()!=null&& sessTmpSpecialEmployee.getFullName().length()>0?sessTmpSpecialEmployee.getFullName():"-");cell.setCellStyle(style2);   
            cell = row.createCell((short) 5);cell.setCellValue(sessTmpSpecialEmployee.getCompanyName()!=null&& sessTmpSpecialEmployee.getCompanyName().length()>0?sessTmpSpecialEmployee.getCompanyName():"-");cell.setCellStyle(style2);   
            cell = row.createCell((short) 6);cell.setCellValue(sessTmpSpecialEmployee.getDivision()!=null&& sessTmpSpecialEmployee.getDivision().length()>0?sessTmpSpecialEmployee.getDivision():"-");cell.setCellStyle(style2); 
            cell = row.createCell((short) 7);cell.setCellValue(sessTmpSpecialEmployee.getDepartement()!=null&& sessTmpSpecialEmployee.getDepartement().length()>0?sessTmpSpecialEmployee.getDepartement():"-");cell.setCellStyle(style2); 
            cell = row.createCell((short) 8);cell.setCellValue(sessTmpSpecialEmployee.getSection()!=null&& sessTmpSpecialEmployee.getSection().length()>0?sessTmpSpecialEmployee.getSection():"-");cell.setCellStyle(style2); 
            cell = row.createCell((short) 9);cell.setCellValue(sessTmpSpecialEmployee.getEmpCategory()!=null&& sessTmpSpecialEmployee.getEmpCategory().length()>0?sessTmpSpecialEmployee.getEmpCategory():"-");cell.setCellStyle(style2); 
            cell = row.createCell((short) 10);cell.setCellValue(sessTmpSpecialEmployee.getLevel()!=null&& sessTmpSpecialEmployee.getLevel().length()>0?sessTmpSpecialEmployee.getLevel():"-");cell.setCellStyle(style2); 
            cell = row.createCell((short) 11);cell.setCellValue(sessTmpSpecialEmployee.getPosition()!=null&& sessTmpSpecialEmployee.getPosition().length()>0?sessTmpSpecialEmployee.getPosition():"-");cell.setCellStyle(style2); 
            cell = row.createCell((short) 12);cell.setCellValue(sessTmpSpecialEmployee.getReligion()!=null&& sessTmpSpecialEmployee.getReligion().length()>0?sessTmpSpecialEmployee.getReligion():"-");cell.setCellStyle(style2); 
            cell = row.createCell((short) 13);cell.setCellValue(PstEmployee.sexKey[sessTmpSpecialEmployee.getSexEmployee()]);cell.setCellStyle(style2); 
            //education
            // int cool = 13;
            cell = row.createCell((short) 14);cell.setCellValue("");cell.setCellStyle(style2); 
            cell = row.createCell((short) 15);cell.setCellValue("");cell.setCellStyle(style2); 
            cell = row.createCell((short) 16);cell.setCellValue("");cell.setCellStyle(style2); 
            cell = row.createCell((short) 17);cell.setCellValue("");cell.setCellStyle(style2);
            //language
            cell = row.createCell((short) 18);cell.setCellValue("");cell.setCellStyle(style2); 
            cell = row.createCell((short) 19);cell.setCellValue("");cell.setCellStyle(style2); 
            cell = row.createCell((short) 20);cell.setCellValue("");cell.setCellStyle(style2); 
            cell = row.createCell((short) 21);cell.setCellValue("");cell.setCellStyle(style2); 

            
            
            cell = row.createCell((short) 22);cell.setCellValue(sessTmpSpecialEmployee.getMaritalStatus()+"-"+sessTmpSpecialEmployee.getMaritalNumberOfChildren());cell.setCellStyle(style2);
             cell = row.createCell((short) 23);cell.setCellValue(sessTmpSpecialEmployee.getTaxMarital()+"-"+sessTmpSpecialEmployee.getTaxNumberChildren());cell.setCellStyle(style2);
             cell = row.createCell((short) 24);cell.setCellValue(sessTmpSpecialEmployee.getBloodTypeEmployee());cell.setCellStyle(style2);
             cell = row.createCell((short) 25);cell.setCellValue(sessTmpSpecialEmployee.getAstekNumEMployee()!=null && sessTmpSpecialEmployee.getAstekNumEMployee().length()>0?sessTmpSpecialEmployee.getAstekNumEMployee():"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 26);cell.setCellValue(sessTmpSpecialEmployee.getAsktekDate()!=null?Formater.formatDate(sessTmpSpecialEmployee.getAsktekDate(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
             
             cell = row.createCell((short) 27);cell.setCellValue(sessTmpSpecialEmployee.getCommercingDateEmployee()!=null?Formater.formatDate(sessTmpSpecialEmployee.getCommercingDateEmployee(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 28);cell.setCellValue(sessTmpSpecialEmployee.getResignedDate()!=null?Formater.formatDate(sessTmpSpecialEmployee.getResignedDate(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 29);cell.setCellValue(sessTmpSpecialEmployee.getResignedReason()!=null && sessTmpSpecialEmployee.getResignedReason().length()>0? sessTmpSpecialEmployee.getResignedReason():"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 30);cell.setCellValue(sessTmpSpecialEmployee.getResignedDesc()!=null && sessTmpSpecialEmployee.getResignedDesc().length()>0?sessTmpSpecialEmployee.getResignedDesc():"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 31);cell.setCellValue(sessTmpSpecialEmployee.getLockerLocation()!=null && sessTmpSpecialEmployee.getLockerLocation().length()>0 && sessTmpSpecialEmployee.getLockerNumber()!=null && sessTmpSpecialEmployee.getLockerNumber().length()>0? sessTmpSpecialEmployee.getLockerLocation()+"-"+sessTmpSpecialEmployee.getLockerNumber():"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 32);cell.setCellValue(sessTmpSpecialEmployee.getAddressEmployee()!=null && sessTmpSpecialEmployee.getAddressEmployee().length()>0?sessTmpSpecialEmployee.getAddressEmployee():"-");cell.setCellStyle(style2);
             
             cell = row.createCell((short) 33);cell.setCellValue(sessTmpSpecialEmployee.getAddressPermanent()!=null && sessTmpSpecialEmployee.getAddressPermanent().length()>0?sessTmpSpecialEmployee.getAddressPermanent():"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 34);cell.setCellValue(sessTmpSpecialEmployee.getPhoneEmployee()!=null && sessTmpSpecialEmployee.getPhoneEmployee().length()>0?sessTmpSpecialEmployee.getPhoneEmployee():"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 35);cell.setCellValue(sessTmpSpecialEmployee.getHandphone()!=null && sessTmpSpecialEmployee.getHandphone().length()>0?sessTmpSpecialEmployee.getHandphone():"-");cell.setCellStyle(style2);
            String phEmergensi="";
             if(sessTmpSpecialEmployee.getPhoneEmg()!=null && sessTmpSpecialEmployee.getPhoneEmg().length()>0){
                 phEmergensi = sessTmpSpecialEmployee.getPhoneEmg();
             }
             String personEmrg="";
             if(sessTmpSpecialEmployee.getNameEmg()!=null && sessTmpSpecialEmployee.getNameEmg().length()>0){
                 personEmrg = sessTmpSpecialEmployee.getNameEmg();
             }
             cell = row.createCell((short) 36);cell.setCellValue((phEmergensi.length()>0?phEmergensi:"-")+"/"+(personEmrg.length()>0?personEmrg:"-"));cell.setCellStyle(style2);
             cell = row.createCell((short) 37);cell.setCellValue(sessTmpSpecialEmployee.getPostalCodeEmployee());cell.setCellStyle(style2);
             cell = row.createCell((short) 38);cell.setCellValue(sessTmpSpecialEmployee.getBirthPlaceEmployee()!=null && sessTmpSpecialEmployee.getBirthPlaceEmployee().length()>0? sessTmpSpecialEmployee.getBirthPlaceEmployee():"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 39);cell.setCellValue(sessTmpSpecialEmployee.getBirthDateEmployee()!=null ? Formater.formatDate(sessTmpSpecialEmployee.getBirthDateEmployee(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 40);cell.setCellValue(sessTmpSpecialEmployee.getUmur());cell.setCellStyle(style2);
             cell = row.createCell((short) 41);cell.setCellValue( sessTmpSpecialEmployee.getRaceName()!=null && sessTmpSpecialEmployee.getRaceName().length()>0? sessTmpSpecialEmployee.getRaceName()  :"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 42);cell.setCellValue( sessTmpSpecialEmployee.getFather()!=null && sessTmpSpecialEmployee.getFather().length()>0 && !sessTmpSpecialEmployee.getFather().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getFather()  :"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 43);cell.setCellValue( sessTmpSpecialEmployee.getMother()!=null && sessTmpSpecialEmployee.getMother().length()>0 && !sessTmpSpecialEmployee.getMother().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getMother()  :"-");cell.setCellStyle(style2);
             cell = row.createCell((short) 44);cell.setCellValue( sessTmpSpecialEmployee.getParentsAddress()!=null && sessTmpSpecialEmployee.getParentsAddress().length()>0 && !sessTmpSpecialEmployee.getParentsAddress().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getParentsAddress()  :"-");cell.setCellStyle(style2);
             
             //family
            cell = row.createCell((short) 45);cell.setCellValue( "");cell.setCellStyle(style2);
            cell = row.createCell((short) 46);cell.setCellValue( "");cell.setCellStyle(style2);
            cell = row.createCell((short) 47);cell.setCellValue( "");cell.setCellStyle(style2);
            cell = row.createCell((short) 48);cell.setCellValue( "");cell.setCellStyle(style2);
            cell = row.createCell((short) 49);cell.setCellValue( "");cell.setCellStyle(style2);
            cell = row.createCell((short) 50);cell.setCellValue( "");cell.setCellStyle(style2);
            cell = row.createCell((short) 51);cell.setCellValue( "");cell.setCellStyle(style2);
            cell = row.createCell((short) 52);cell.setCellValue( "");cell.setCellStyle(style2);
            cell = row.createCell((short) 53);cell.setCellValue( "");cell.setCellStyle(style2);
            cell = row.createCell((short) 54);cell.setCellValue( "");cell.setCellStyle(style2);
            
            cell = row.createCell((short) 55);cell.setCellValue(sessTmpSpecialEmployee.getEndContractEmployee()!=null ? Formater.formatDate(sessTmpSpecialEmployee.getEndContractEmployee(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 56);cell.setCellValue(sessTmpSpecialEmployee.getIdCardType()!=null && sessTmpSpecialEmployee.getIdCardType().length()>0 && !sessTmpSpecialEmployee.getIdCardType().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getIdCardType()  :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 57);cell.setCellValue(sessTmpSpecialEmployee.getNpwp()!=null && sessTmpSpecialEmployee.getNpwp().length()>0 && !sessTmpSpecialEmployee.getNpwp().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getNpwp()  :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 58);cell.setCellValue(sessTmpSpecialEmployee.getBpjsDate()!=null ? Formater.formatDate(sessTmpSpecialEmployee.getBpjsDate(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 59);cell.setCellValue(sessTmpSpecialEmployee.getStartDatePensiun()!=null ? Formater.formatDate(sessTmpSpecialEmployee.getStartDatePensiun(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 60);cell.setCellValue(sessTmpSpecialEmployee.getShio()!=null && sessTmpSpecialEmployee.getShio().length()>0 && !sessTmpSpecialEmployee.getShio().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getShio()  :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 61);cell.setCellValue(sessTmpSpecialEmployee.getElemen()!=null && sessTmpSpecialEmployee.getElemen().length()>0 && !sessTmpSpecialEmployee.getElemen().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getElemen()  :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 62);cell.setCellValue(sessTmpSpecialEmployee.getIq()!=null && sessTmpSpecialEmployee.getIq().length()>0 && !sessTmpSpecialEmployee.getIq().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getIq()  :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 63);cell.setCellValue(sessTmpSpecialEmployee.getEq()!=null && sessTmpSpecialEmployee.getEq().length()>0 && !sessTmpSpecialEmployee.getEq().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getEq()  :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 64);cell.setCellValue(sessTmpSpecialEmployee.getProbationEndDate()!=null ? Formater.formatDate(sessTmpSpecialEmployee.getProbationEndDate(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 65);cell.setCellValue(sessTmpSpecialEmployee.getStatusPensionProgram()!=null && sessTmpSpecialEmployee.getStatusPensionProgram().length()>0 && !sessTmpSpecialEmployee.getStatusPensionProgram().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getStatusPensionProgram()  :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 66);cell.setCellValue(sessTmpSpecialEmployee.getStartDatePensiun()!=null ? Formater.formatDate(sessTmpSpecialEmployee.getStartDatePensiun(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 67);cell.setCellValue(sessTmpSpecialEmployee.getPresenceCheckParameter()!=null && sessTmpSpecialEmployee.getPresenceCheckParameter().length()>0 && !sessTmpSpecialEmployee.getPresenceCheckParameter().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getPresenceCheckParameter()  :"-");cell.setCellStyle(style2);
            cell = row.createCell((short) 68);cell.setCellValue(sessTmpSpecialEmployee.getMedicalInfo()!=null && sessTmpSpecialEmployee.getMedicalInfo().length()>0 && !sessTmpSpecialEmployee.getMedicalInfo().equalsIgnoreCase("null") ? sessTmpSpecialEmployee.getMedicalInfo()  :"-");cell.setCellStyle(style2);
            
            cell = row.createCell((short) 69);cell.setCellValue(sessTmpSpecialEmployee.getWaCompany()!=null&& sessTmpSpecialEmployee.getWaCompany().length()>0?sessTmpSpecialEmployee.getWaCompany():sessTmpSpecialEmployee.getCompanyName());cell.setCellStyle(style2);   
            cell = row.createCell((short) 70);cell.setCellValue(sessTmpSpecialEmployee.getWaDivision()!=null&& sessTmpSpecialEmployee.getWaDivision().length()>0?sessTmpSpecialEmployee.getWaDivision():sessTmpSpecialEmployee.getDivision());cell.setCellStyle(style2); 
            cell = row.createCell((short) 71);cell.setCellValue(sessTmpSpecialEmployee.getWaDepartement()!=null&& sessTmpSpecialEmployee.getWaDepartement().length()>0?sessTmpSpecialEmployee.getWaDepartement():sessTmpSpecialEmployee.getDepartement());cell.setCellStyle(style2); 
            cell = row.createCell((short) 72);cell.setCellValue(sessTmpSpecialEmployee.getWaSection()!=null&& sessTmpSpecialEmployee.getWaSection().length()>0?sessTmpSpecialEmployee.getWaSection():sessTmpSpecialEmployee.getSection());cell.setCellStyle(style2); 
            cell = row.createCell((short) 73);cell.setCellValue(sessTmpSpecialEmployee.getWaPosition()!=null&& sessTmpSpecialEmployee.getWaPosition().length()>0?sessTmpSpecialEmployee.getWaPosition():sessTmpSpecialEmployee.getPosition());cell.setCellStyle(style2); 
            cell = row.createCell((short) 74);cell.setCellValue(sessTmpSpecialEmployee.getGrade()!=null&& sessTmpSpecialEmployee.getGrade().length()>0?sessTmpSpecialEmployee.getGrade():sessTmpSpecialEmployee.getGrade());cell.setCellStyle(style2); 
            
            // cell = row.createCell((short) 13);cell.setCellValue(PstEmployee.sexKey[employee.getSex()]);cell.setCellStyle(style2); 
            //int coolmsEducation = 13;
           
            int tmpRowEdu = countRow;
            //update by satrya 2013-10-12
            boolean adaListEdu=false;
            if(listEducation!=null && listEducation.size()>0){
                for(int idxEdu=0;idxEdu<listEducation.size();idxEdu++){
                    SessEmpEducation sessEmpEducation = (SessEmpEducation)listEducation.get(idxEdu);
                    if(sessTmpSpecialEmployee.getEmployeeId()==sessEmpEducation.getEmployee_id()){
                       if( 10820==sessTmpSpecialEmployee.getEmployeeId()){
                           boolean test=true;
                       }
                       adaListEdu=true;
                        row = sheet.getRow(tmpRowEdu);
                       if(row==null){
                            row = sheet.createRow((short) (tmpRowEdu));
                            for(int idx=0;idx<14;idx++){
                               cell = row.createCell((short) idx);cell.setCellValue("");cell.setCellStyle(style2); 
                            }
                             for(int idx=18;idx<55;idx++){
                               cell = row.createCell((short) idx);cell.setCellValue("");cell.setCellStyle(style2); 
                            }
                       }
                       
                       
       
                        cell = row.createCell((short) 14);cell.setCellValue(sessEmpEducation.getEducation()!=null?sessEmpEducation.getEducation():"-");cell.setCellStyle(style2); 
                        cell = row.createCell((short) 15);cell.setCellValue(sessEmpEducation.getGraduation()!=null?sessEmpEducation.getGraduation():"-");cell.setCellStyle(style2); 
                        cell = row.createCell((short) 16);cell.setCellValue(sessEmpEducation.getStartDate());cell.setCellStyle(style2); 
                        cell = row.createCell((short) 17);cell.setCellValue(sessEmpEducation.getEndDate());cell.setCellStyle(style2); 
                        
                        listEducation.remove(idxEdu);
                        idxEdu=idxEdu-1;
                        tmpRowEdu=tmpRowEdu+1;
                        
                    }
                }
            }
                     addRow = tmpRowEdu- countRow;

        
            int tmpRowLang = countRow;
            boolean adaListLanguange=false;
            if(listLanguage!=null && listLanguage.size()>0){
                for(int idxLang=0;idxLang<listLanguage.size();idxLang++){
                    SessEmpLanguage sessEmpLanguage = (SessEmpLanguage)listLanguage.get(idxLang);
                    if(sessTmpSpecialEmployee.getEmployeeId()==sessEmpLanguage.getEmployee_id()){
                         row = sheet.getRow(tmpRowLang);
                       adaListLanguange = true;  
                       if(row==null){
                            row = sheet.createRow((short) (tmpRowLang));
                           for(int idx=0;idx<18;idx++){
                            cell = row.createCell((short) idx);cell.setCellValue("");cell.setCellStyle(style2); 
                           }
                           for(int idx=22;idx<45;idx++){
                            cell = row.createCell((short) idx);cell.setCellValue("");cell.setCellStyle(style2); 
                           }
                           //update by satrya 2013-10-17
                           //karena ada yg pecah"
                           for(int idx=45;idx<55;idx++){
                            cell = row.createCell((short) idx);cell.setCellValue("");cell.setCellStyle(style2); 
                           }
                       }

                        cell = row.createCell((short) 18);cell.setCellValue(sessEmpLanguage.getLanguage()!=null?sessEmpLanguage.getLanguage():"-");cell.setCellStyle(style2); 
                        cell = row.createCell((short) 19);cell.setCellValue(sessEmpLanguage.getOral()!=null?sessEmpLanguage.getOral():"-");cell.setCellStyle(style2); 
                        cell = row.createCell((short) 20);cell.setCellValue(sessEmpLanguage.getWritten()!=null?sessEmpLanguage.getWritten():"-");cell.setCellStyle(style2); 
                        cell = row.createCell((short) 21);cell.setCellValue(sessEmpLanguage.getDescription()!=null?sessEmpLanguage.getDescription():"-");cell.setCellStyle(style2); 
                        tmpRowLang=tmpRowLang+1;
                        listLanguage.remove(idxLang);                              
                        idxLang=idxLang-1;
                        
                        
                        
                    }
                }
            }
         
             if(tmpRowLang-countRow > addRow){
                     addRow = tmpRowLang - countRow;
             }
           //}  
             int tmpFam=countRow;
             boolean adaListFamily=false;
             if(listFamily!=null && listFamily.size()>0){
                 for(int idxFam=0; idxFam<listFamily.size();idxFam++){
                     SessEmpFamilyMember  sessEmpFamilyMember = (SessEmpFamilyMember)listFamily.get(idxFam);
                     if(sessEmpFamilyMember.getEmployeeId()==sessTmpSpecialEmployee.getEmployeeId()){
                          row = sheet.getRow(tmpFam);
                          adaListFamily=true;
                            if(row==null){
                                 row = sheet.createRow((short) (tmpFam));
                                 for(int idx=0;idx<45;idx++){
                                    cell = row.createCell((short) idx);cell.setCellValue("");cell.setCellStyle(style2); 
                                  } 
                            }

                            cell = row.createCell((short) 45);cell.setCellValue( sessEmpFamilyMember.getFullName()!=null && sessEmpFamilyMember.getFullName().length()>0? sessEmpFamilyMember.getFullName()  :"-");cell.setCellStyle(style2);
                            cell = row.createCell((short) 46);cell.setCellValue( PstEmployee.sexKey[sessEmpFamilyMember.getSex()]);cell.setCellStyle(style2);
                            cell = row.createCell((short) 47);cell.setCellValue( sessEmpFamilyMember.getFamilyRelation()!=null && sessEmpFamilyMember.getFamilyRelation().length()>0? sessEmpFamilyMember.getFamilyRelation() :"-");cell.setCellStyle(style2);
                            
                            cell = row.createCell((short) 48);cell.setCellValue( sessEmpFamilyMember.getGuaranted()==0? "no" :"yes");cell.setCellStyle(style2);
                            cell = row.createCell((short) 49);cell.setCellValue( sessEmpFamilyMember.getBirthDate()!=null? Formater.formatDate(sessEmpFamilyMember.getBirthDate(), "yyyy-MM-dd") :"-");cell.setCellStyle(style2);
                            cell = row.createCell((short) 50);cell.setCellValue( sessEmpFamilyMember.getUmur());cell.setCellStyle(style2);
                            cell = row.createCell((short) 51);cell.setCellValue( sessEmpFamilyMember.getReligion()!=null && sessEmpFamilyMember.getReligion().length()>0?sessEmpFamilyMember.getReligion():"-");cell.setCellStyle(style2);
                            cell = row.createCell((short) 52);cell.setCellValue( sessEmpFamilyMember.getJob()!=null && sessEmpFamilyMember.getJob().length()>0?sessEmpFamilyMember.getJob():"-");cell.setCellStyle(style2);
                            cell = row.createCell((short) 53);cell.setCellValue( sessEmpFamilyMember.getEducation()!=null && sessEmpFamilyMember.getEducation().length()>0?sessEmpFamilyMember.getEducation():"-");cell.setCellStyle(style2);
                            cell = row.createCell((short) 54);cell.setCellValue( sessEmpFamilyMember.getAddress()!=null && sessEmpFamilyMember.getAddress().length()>0?sessEmpFamilyMember.getAddress():"-");cell.setCellStyle(style2);
                            tmpFam=tmpFam+1;
                            listFamily.remove(idxFam);                              
                        idxFam=idxFam-1;
                     }
                 }
             }
             /**
              * update by satrya 2013-10-12
              * if(tmpFam-countRow > addRow){
                     addRow = tmpFam - countRow;
             }
              */
            //if(adaListFamily){
              if(tmpFam-countRow > addRow){
                     addRow = tmpFam - countRow;
             }
            //}
          }catch(Exception exc){
                System.out.println("Exc "+ exc+" Employe Num "+sessTmpSpecialEmployee.getEmployeeNum());
           }  
          tmpx=tmpx+1;
          }
        }    
             
                            ServletOutputStream sos = response.getOutputStream();     
    
        
                            try {
                                wb.write(baos);
                            } finally {
                                baos.close();
                            }
                            byte[] bytes = baos.toByteArray();
                            DataSource dataSource = new ByteArrayDataSource(bytes, "application/x-msexcel");
                            attachment.add(dataSource); // add as attachement
                            attachmentName.add("List"+".xls");
                            Vector<String> listRec = new Vector();
                            listRec.add("" + emailAddress);
                            email.sendEmail(listRec, null, null, " Employee List ", "tes", attachment, attachmentName);
        
        wb.write(sos);
        sos.flush();
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
