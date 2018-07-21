/*
 * SpecialQueryResultNew20130108.java
 *
 * Created on June 16, 2003, 1:32 PM
 */
 
package com.dimata.harisma.session.employee;           

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

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
import com.dimata.gui.jsp.*;
import com.dimata.qdep.form.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.form.employee.*;
import com.dimata.harisma.entity.admin.*;
import com.dimata.harisma.entity.payroll.PayGeneral;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.employee.*;

/** 
 *
 * @author  karya
 * @version 
 */
public class SpecialQueryResult extends HttpServlet {
   
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
        
        String sex      = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SEX]);
        int iSex=0;
        if(sex!=null && sex.length()>0){
            iSex = Integer.parseInt(sex);
        }
        String resigned = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RESIGNED]);
         int iResign=0;
        if(resigned!=null && resigned.length()>0){
            iResign = Integer.parseInt(resigned);
        }
       // System.out.println("resigned = " + resigned + " --- " + Integer.valueOf(resigned));
        String sort     = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_ORDER]);

        String workyearfrom    = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKYEARFROM]);
        //System.out.println("workyearfrom = " + workyearfrom);
        String workmonthfrom   = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKMONTHFROM]);
        String workyearto      = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKYEARTO]);
        String workmonthto     = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKMONTHTO]);

        String ageyearfrom    = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEYEARFROM]);
        String agemonthfrom   = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEMONTHFROM]);
        String ageyearto      = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEYEARTO]);
        String agemonthto     = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEMONTHTO]);

        String radiocommdate = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RADIO_COMMERCING_DATE]);
        int iradioCommrcing =0;
        if(radiocommdate!=null && radiocommdate.length()>0){
            iradioCommrcing = Integer.parseInt(radiocommdate);
        }
        java.util.Date commdatefrom    = FRMQueryString.requestDateIfYearMonthNol(request, FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMMDATEFROM]);
        java.util.Date commdateto      = FRMQueryString.requestDateIfYearMonthNol(request, FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMMDATETO]);

        java.util.Date birthDayFrom = FRMQueryString.requestDateIfYearMonthNol(request, FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BIRTH_DATE_FROM]);
        java.util.Date birthDayTo = FRMQueryString.requestDateIfYearMonthNol(request, FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BIRTH_DATE_TO]);
        
        String addrssEmp = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EMP_ADDRESS]);
        String addrssEmpPermanen = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_ADDRESS_EMP_PERMANET]);
        
       String sMontDate = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EMP_BIRTH_DAY_MONTH]);
        int monthDate=0;
        if(sMontDate!=null && sMontDate.length()>0){
            monthDate = Integer.parseInt(sMontDate);
        }
        
        String sBirthYearFrom = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BIRTH_YEAR_FROM]);
        int birthYearFrom=0;
        if(sBirthYearFrom!=null && sBirthYearFrom.length()>0){
            birthYearFrom = Integer.parseInt(sBirthYearFrom);
        }
        String sBirthYearTo = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BIRTH_YEAR_TO]);
        int birthYearTo=0;
        if(sBirthYearTo!=null && sBirthYearTo.length()>0){
            birthYearTo = Integer.parseInt(sBirthYearTo);
        }
       // Vector vparam = new Vector(1,1);
        String[] arrCompany = null;
        String[] arrDepartmentId = null;
        String[] arrPositionId = null;
        String[] arrSectionId = null;
        String[] arrLevelId = null;
        String[] arrEducationId = null;
        String[] arrReligionId = null;
        String[] arrMaritalId = null;
        String[] arrBlood = null;
        String[] arrLanguageId = null;
        String[] arrRaceId = null;
        String[] arrEmpCategory = null;
        String[] arrDivisionId=null;
        //update by satrya 2013-08-15
        String[] arrCountry = null;
        String[] arrKabupaten = null;
        String[] arrKecamatan = null;
        String[] arrProvinsi=null;
        
        String[] arrCountryPermanent = null;
         String[] arrKabupatenPermanent = null;
        String[] arrKecamatanPermanent = null;
        String[] arrProvinsiPermanent=null;

       // System.out.println("\r=== specialquery_list.jsp ===");
        try {
            
            arrCompany = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMPANY_ID]);
            arrDepartmentId = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_DEPARTMENT]);
            arrPositionId   = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_POSITION]);
            arrSectionId    = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SECTION]);
            arrLevelId      = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_LEVEL]);
            arrEducationId  = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EDUCATION]);
            arrReligionId   = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RELIGION]);
            arrMaritalId    = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_MARITAL]);
            arrBlood        = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BLOOD]);
            arrLanguageId   = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_LANGUAGE]);
            arrRaceId       = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RACE]);
            arrEmpCategory  = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EMP_CATEGORY]);
            arrDivisionId = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EMP_DIVISION_ID]);
            //System.out.println(" arrDepartmentId.length = " + arrDepartmentId.length);
//            for (int i=0; i<arrDepartmentId.length; i++) {
//                System.out.println(" arrDepartmentId #" + i + " = " + arrDepartmentId[i]);
//            }
            arrCountry = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COUNTRY]);
            arrKabupaten = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_KABUPATEN_ID]);
            arrKecamatan = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_KECAMATAN_ID]);
            arrProvinsi=request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_PROVINSI_ID]);

            arrCountryPermanent = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COUNTRY_PERMANENT]);
            arrKabupatenPermanent = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_KABUPATEN_ID_PERMANENT]);
            arrKecamatanPermanent = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_KECAMATAN_ID_PERMANENT]);
            arrProvinsiPermanent=request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_PROVINSI_ID_PERMANENT]);
        
       
        }
        catch (Exception e) {
            System.out.println("Exception SpecialQuery export Excel"+e);
        }
        System.out.println("\r=============================");
        SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
        searchSpecialQuery.addArrCompany(arrCompany);
        searchSpecialQuery.addArrDepartmentId(arrDepartmentId);
        searchSpecialQuery.addArrPositionId(arrPositionId);        
        searchSpecialQuery.addArrSectionId(arrSectionId);        
        searchSpecialQuery.addArrLevelId(arrLevelId);
        searchSpecialQuery.addArrEducationId(arrEducationId);
        searchSpecialQuery.addArrReligionId(arrReligionId);
        searchSpecialQuery.addArrMaritalId(arrMaritalId);
        searchSpecialQuery.addArrBlood(arrBlood);
        
        searchSpecialQuery.addArrLanguageId(arrLanguageId);
        searchSpecialQuery.setiSex(iSex);
        searchSpecialQuery.setiResigned(iResign);
        
        searchSpecialQuery.setRadiocommdate(iradioCommrcing);
        searchSpecialQuery.addCommdatefrom(commdatefrom);
        searchSpecialQuery.addCommdateto(commdateto);
        
        searchSpecialQuery.addWorkyearfrom(workyearfrom);
        searchSpecialQuery.addWorkmonthfrom(workmonthfrom);
        searchSpecialQuery.addWorkyearto(workyearto);
        searchSpecialQuery.addWorkmonthto(workmonthto);
        
        searchSpecialQuery.addAgeyearfrom(ageyearfrom);
        searchSpecialQuery.addAgemonthfrom(agemonthfrom);
        searchSpecialQuery.addAgeyearto(ageyearto);
        searchSpecialQuery.addAgemonthto(agemonthto);
       
        searchSpecialQuery.addSort(sort);
        searchSpecialQuery.addArrRaceId(arrRaceId);
       
        searchSpecialQuery.addArrEmpCategory(arrEmpCategory);
        searchSpecialQuery.addArrDivision(arrDivisionId);
        //updatye satrya 2013-08-15
         searchSpecialQuery.addArrCountryId(arrCountry);
        searchSpecialQuery.addArrProvinsiId(arrProvinsi);
          searchSpecialQuery.addArrKabupatenId(arrKabupaten);
          searchSpecialQuery.addArrKecamatanId(arrKecamatan);
          
        searchSpecialQuery.addArrCountryIdPermanent(arrCountryPermanent);
        searchSpecialQuery.addArrProvinsiIdPermanent(arrProvinsiPermanent);
        searchSpecialQuery.addArrKabupatenIdPermanent(arrKabupatenPermanent);
        searchSpecialQuery.addArrKecamatanIdPermanent(arrKecamatanPermanent);
          
          searchSpecialQuery.setBirthMonth(monthDate);
          searchSpecialQuery.setDtBirthFrom(birthDayFrom);
          searchSpecialQuery.setDtBirthTo(birthDayTo);
          searchSpecialQuery.setAddrsEmployee(addrssEmp);
          searchSpecialQuery.setAddressPermanent(addrssEmpPermanen);

          searchSpecialQuery.setBirthYearFrom(birthYearFrom);
          searchSpecialQuery.setBirthYearTo(birthYearTo);
        /*vparam.add(arrCompany);         //0
        vparam.add(arrDepartmentId);    //1
        vparam.add(arrPositionId);      //2
        vparam.add(arrSectionId);       //3
        vparam.add(arrLevelId);         //4
        
        if(arrEducationId==null){
            vparam.add(""+0);     //5
        }else{
            vparam.add(arrEducationId);     //5
        }
        vparam.add(arrReligionId);      //6
        vparam.add(arrMaritalId);       //7
       //create by satrya 2012-12-23
        if(arrBlood==null){
             vparam.add(""+0);           //8
        }else{
           vparam.add(arrBlood);           //8
        }
        vparam.add(arrLanguageId);      //9
        vparam.add(Integer.valueOf(sex));        //10
        vparam.add(Integer.valueOf(resigned));   //11
        vparam.add(radiocommdate);      //12
        vparam.add(commdatefrom);       //13
        vparam.add(commdateto);         //14

        vparam.add(workyearfrom);   //15
        vparam.add(workmonthfrom);  //16
        vparam.add(workyearto);     //17
        vparam.add(workmonthto);    //18
        vparam.add(ageyearfrom);    //19
        vparam.add(agemonthfrom);   //20
        vparam.add(ageyearto);      //21
        vparam.add(agemonthto);     //22

        vparam.add(sort);           //23
        vparam.add(arrRaceId);      //24*/

        Vector listEmployee = new Vector(1,1);
        listEmployee = SessSpecialEmployee.searchSpecialEmployee(searchSpecialQuery);
        //listEmployee = SessSpecialEmployee.searchSpecialEmployee(vparam);
        
        System.out.println("---===| Excel Report |===---");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Special Query Result");

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Create a row and put some cells in it. Rows are 0 based.
        //HSSFRow row = sheet.createRow((short) 0);
        //HSSFCell cell = row.createCell((short) 0);
        HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

        String[] tableHeader = {

            "NO","PAYROLL","BARCODE_NUMBER","ID CARD","FULL_NAME","COMPANY","DIVISION","DEPARTEMENT","SECTION","EMP. CATEGORY"
            ,"LEVEL","POSITION","RELIGION","GENDER","EDUCATION","GRADUATION","START_DATE_EDUCATION","END_DATE_EDUCATION"
            ,"LANGUAGE","ORAL","WRITTEN","DESCRIPTION","MARITAL STATUS","TAX REPORT","BLOOD"
            ,"JAMSOS NUM","JAMSOS DATE","COMMERCING DATE","RESIGN DATE","RESIGN REASON","RESIGN DESC"
            ,"LOCKER","ADDRESS","PERMANENT ADDRESS","PHONE","HAND PHONE","EMERGENSI PHONE / PERSON NAME"
            ,"POSTAL CODE","BIRTH PLACE","BIRTH DATE","AGE","RACE","FATHER NAME","MOTHER NAME",	"PARENTS ADDRESS"
            ,"FAMILY MEMBER NAME","FAMILY GENDER","FAMILY RELATIONSHIP","FAMLILY GUARANTED","FAMILY DATE OF BIRTH","FAMILY AGE","FAMILY RELIGION"
            ,"FAMILY JOB ADRESS","FAMILY LAST EDUCATION","FAMILY ADDRESS"

        };
        row = sheet.createRow((short) (0));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
      int countRow = 1;
       int addRow =0;
      if(listEmployee!=null && listEmployee.size()>0){
          Vector listEducation = PstEmpEducation.listEducation("", PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+","+PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]+ " ASC ");
          Vector listLanguage = PstEmpLanguage.listEmpLanguage("", PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+ " ASC ");
          Vector listFamily = PstFamilyMember.listEmpFamily("", PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+","+PstFamilyMember.fieldNames[PstFamilyMember.FLD_BIRTH_DATE]+ " ASC ");
          int no=1;
        for (int i = 0; i < listEmployee.size(); i++) {
         
            SessTmpSpecialEmployee sessTmpSpecialEmployee = (SessTmpSpecialEmployee) listEmployee.get(i);
          try{
//            Employee employee = (Employee) temp.get(0);
//            Department department = (Department) temp.get(1);
//            Position position = (Position) temp.get(2);
//            Section section = (Section) temp.get(3);
//            EmpCategory empCategory = (EmpCategory) temp.get(4);
//            Level level = (Level) temp.get(5);
//            Religion religion = (Religion) temp.get(6);
//            Marital marital = (Marital) temp.get(7);
//            //Education education = (Education) temp.get(8);
//            Language language = (Language) temp.get(8);
//            Race race = (Race) temp.get(9);
            //update by satrya 2013-08-01
            countRow = countRow++;
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
             
           // cell = row.createCell((short) 13);cell.setCellValue(PstEmployee.sexKey[employee.getSex()]);cell.setCellStyle(style2); 
            //int coolmsEducation = 13;
           
            int tmpRowEdu = countRow;
            if(listEducation!=null && listEducation.size()>0){
                for(int idxEdu=0;idxEdu<listEducation.size();idxEdu++){
                    SessEmpEducation sessEmpEducation = (SessEmpEducation)listEducation.get(idxEdu);
                    if(sessTmpSpecialEmployee.getEmployeeId()==sessEmpEducation.getEmployee_id()){
                       
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
            if(listLanguage!=null && listLanguage.size()>0){
                for(int idxLang=0;idxLang<listLanguage.size();idxLang++){
                    SessEmpLanguage sessEmpLanguage = (SessEmpLanguage)listLanguage.get(idxLang);
                    if(sessTmpSpecialEmployee.getEmployeeId()==sessEmpLanguage.getEmployee_id()){
                         row = sheet.getRow(tmpRowLang);
                       if(row==null){
                            row = sheet.createRow((short) (tmpRowLang));
                           for(int idx=0;idx<18;idx++){
                            cell = row.createCell((short) idx);cell.setCellValue("");cell.setCellStyle(style2); 
                           }
                           for(int idx=22;idx<45;idx++){
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
             
             int tmpFam=countRow;
             if(listFamily!=null && listFamily.size()>0){
                 for(int idxFam=0; idxFam<listFamily.size();idxFam++){
                     SessEmpFamilyMember  sessEmpFamilyMember = (SessEmpFamilyMember)listFamily.get(idxFam);
                     if(sessEmpFamilyMember.getEmployeeId()==sessTmpSpecialEmployee.getEmployeeId()){
                          row = sheet.getRow(tmpFam);
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
              if(tmpFam-countRow > addRow){
                     addRow = tmpFam - countRow;
             }
             //System.out.println(" Employee "+sessTmpSpecialEmployee.getFullName()+" Employee Num: "+ sessTmpSpecialEmployee.getEmployeeNum()+" DEPARTEMENT "+sessTmpSpecialEmployee.getDepartement());
            }catch(Exception exc){
                System.out.println("Exception export excel data bank employee "+ exc +" Employee Num "+ sessTmpSpecialEmployee.getEmployeeNum());
            }
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
