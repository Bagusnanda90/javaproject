package com.dimata.harisma.report;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.session.employee.*;
import com.dimata.qdep.form.FRMQueryString;

public class EmployeeListPdf extends HttpServlet {
    // public static final membervariables
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        
        System.out.println("===| EmployeeList |===");
        //SessEmployee sessEmployee = new SessEmployee();
        //SrcEmployee srcEmployee = new SrcEmployee();
        HttpSession session = request.getSession();
        
        //srcEmployee = (SrcEmployee) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
        //update by satrya 2013-08-05
        SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
        searchSpecialQuery = (SearchSpecialQuery)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
        if (searchSpecialQuery == null){
            searchSpecialQuery = new SearchSpecialQuery();
        }
        int iCommand = FRMQueryString.requestCommand(request);
        
        if (iCommand == Command.LIST){
            //Gunadi 22/11/2016
            String empNum = FRMQueryString.requestString(request, "employee_num");
            String empName = FRMQueryString.requestString(request, "employee_name");
            long companyId = FRMQueryString.requestLong(request, "company_id");
            long divisionId = FRMQueryString.requestLong(request, "division_id");
            long departmentId = FRMQueryString.requestLong(request, "department_id");
            long sectionId = FRMQueryString.requestLong(request, "section_id");
            long positionId = FRMQueryString.requestLong(request, "position_id");
            int resign = FRMQueryString.requestInt(request, "resign");
            String inEmpId = FRMQueryString.requestString(request, "inEmpId");
            long levelId = FRMQueryString.requestLong(request, "level_id");
            long empCatId = FRMQueryString.requestLong(request, "emp_category_id");
            long maritalId = FRMQueryString.requestLong(request, "marital_id");
            long raceId = FRMQueryString.requestLong(request, "race_id");
            long religionId = FRMQueryString.requestLong(request, "religion_id");
            int birthMonth = FRMQueryString.requestInt(request, "birth_month");

            String whereClause = "";
            Vector whereVect = new Vector();
            if (empNum.length() > 0){
                    searchSpecialQuery.setEmpnumber(empNum);
                }
                if (empName.length() > 0){
                    searchSpecialQuery.setFullNameEmp(empName);
                }
                if (companyId != 0){
                    String [] company = {""+companyId};
                    searchSpecialQuery.addArrCompany(company);
                }
                if (divisionId != 0){
                    String [] division = {""+divisionId};
                    searchSpecialQuery.addArrDivision(division);
                }
                if (departmentId != 0){
                    String [] department = {""+departmentId};
                    searchSpecialQuery.addArrDepartmentId(department);
                }
                if (sectionId != 0){
                    String [] section = {""+sectionId};
                    searchSpecialQuery.addArrSectionId(section);
                }
                if (positionId != 0){
                    String [] position = {""+positionId};
                    searchSpecialQuery.addArrPositionId(position);
                }
                if (resign > -1){
                    /* tampilkan hanya yang tidak resign */
                    if (resign == 0){
                        searchSpecialQuery.setiResigned(resign);
                    }
                    /* tampilkan yang resign dan yang tidak resign*/
                    if (resign == 1){
                        searchSpecialQuery.setiResigned(resign);
                    }
                }
    //            if (!(inEmpId.equals(""))){
    //                whereClause = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" IN ("+inEmpId+") ";
    //                whereVect.add(whereClause);
    //            }
                if (empCatId != 0){
                    String [] category = {""+empCatId};
                    searchSpecialQuery.addArrEmpCategory(category);
                }
                if (levelId != 0){
                    String [] level = {""+levelId};
                    searchSpecialQuery.addArrLevelId(level);
                }
                if (maritalId != 0){
                    String [] marital = {""+maritalId};
                    searchSpecialQuery.addArrMaritalId(marital);
                }
                if (raceId != 0){
                    String [] race = {""+raceId};
                    searchSpecialQuery.addArrRaceId(race);
                }
                if (religionId != 0){
                    String [] religion = {""+religionId};
                    searchSpecialQuery.addArrReligionId(religion);
                }
                if (birthMonth != 0){
                    searchSpecialQuery.setBirthMonth(birthMonth);
                }
                searchSpecialQuery.setStartResign(null);
                searchSpecialQuery.setEndResign(null);
                searchSpecialQuery.setDtBirthFrom(null);
                searchSpecialQuery.setDtBirthTo(null);
                searchSpecialQuery.setDtCarrierWorkEnd(null);
                searchSpecialQuery.setDtCarrierWorkStart(null);
                searchSpecialQuery.setDtPeriodEnd(null);
                searchSpecialQuery.setDtPeriodStart(null);
                searchSpecialQuery.setiSex(2);
        }
        
        Vector listEmployee = new Vector(1,1);
        //listEmployee = sessEmployee.searchEmployee(srcEmployee, 0, 5000);
        listEmployee = SessSpecialEmployee.searchSpecialEmployee(searchSpecialQuery,0,50000); 
        /*
        Hashtable scheduleSymbol = new Hashtable();
        Vector listScd = PstScheduleSymbol.listAll();
        scheduleSymbol.put("0", "-");
        for (int ls = 0; ls < listScd.size(); ls++) {
            ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
            scheduleSymbol.put(String.valueOf(scd.getOID()), scd.getSymbol());
        }

        for (int i = 0; i < listEmpSchedule.size(); i++) {
                Vector temp = (Vector) listEmpSchedule.get(i);
                Employee employee = (Employee) temp.get(1);
                Period period = (Period) temp.get(2);
                EmpSchedule empSchedule = (EmpSchedule)temp.get(0);
                System.out.println(period.getPeriod());
                System.out.println(employee.getFullName());
                System.out.println(scheduleSymbol.get(String.valueOf(empSchedule.getD21())));
        }
         */
        
        // setting the color values
        Color border = new Color(0x00, 0x00, 0x00);
        Color bgTable = new Color(0x40, 0x00, 0xFF);
        Color bgCells = new Color(0xFF, 0xFF, 0x00);
        // setting some fonts in the color chosen by the user
        Font font1 = new Font(Font.HELVETICA, 12, Font.NORMAL, border);
        Font font2 = new Font(Font.HELVETICA, 8, Font.NORMAL, border);
        Font font2a = new Font(Font.HELVETICA, 8, Font.BOLD, border);
        // creating some content that will be used frequently
        Paragraph newLine = new Paragraph("\n", font1);
        Anchor anchor = new Anchor("visit http://www.lowagie.com/iText/", font1);
        anchor.setReference("http://www.lowagie.com/iText/");
        Paragraph link = new Paragraph(anchor);
        link.setAlignment(Element.ALIGN_CENTER);
        
        // step1: creating the document object
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
        
        // step2.1: creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SessTmpSpecialEmployee sessTmpSpecialEmployee = new SessTmpSpecialEmployee();
        try {
            // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);
            
            // step 3.1: adding some metadata to the document
            document.addSubject("This is a report.");
            
            // step 3.2: adding a Header
            HeaderFooter header = new HeaderFooter(new Phrase("Employee List", font1), false);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(border);
            document.setHeader(header);
            
            // step 3.3: adding a Footer
            HeaderFooter footer = new HeaderFooter(new Phrase("", font1), false);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(border);
            document.setFooter(footer);
            
            // step 3.4: opening the document
            document.open();
            
            Table datatable = new Table(15);
            
            datatable.setPadding(1);
            datatable.setSpacing(1);
            //datatable.setBorder(Rectangle.NO_BORDER);
            int headerwidths[] = {
                3, 3, 15, 6, 7, 5, 
                6, 5, 10, 7, 
                7, 7, 10, 8, 8
            };
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            
            // the first cell spans 10 columns
            //Cell cell = new Cell(new Phrase("Administration -System Users Report", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD)));
            //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setLeading(30);
            //cell.setColspan(10);
            //cell.setBorder(Rectangle.NO_BORDER);
            //cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            //datatable.addCell(cell);
            
            // These cells span 2 rows
            //datatable.setDefaultCellBorderWidth(2);
            //datatable.setDefaultHorizontalAlignment(1);
            //datatable.setDefaultRowspan(2);
            //datatable.addCell("User Id");
            //datatable.addCell(new Phrase("Name", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
            //datatable.addCell("Company");
            //datatable.addCell("Department");
            
            // This cell spans the remaining 6 columns in 1 row
            //datatable.setDefaultRowspan(1);
            //datatable.setDefaultColspan(6);
            //datatable.addCell("Permissions");
            
            // These cells span 1 row and 1 column
            //.setBackgroundColor(new Color(0xD0, 0xD0, 0xD0));
            
            datatable.setDefaultVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.setDefaultColspan(1);
            //Cell cell = new Cell(new Phrase("Period", font2a));
            //cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            //datatable.addCell(cell);
            datatable.addCell(new Phrase("No.", font2a));
            datatable.addCell(new Phrase("NIK", font2a));
            datatable.addCell(new Phrase("Full Name", font2a));
            //datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
            //datatable.addCell(new Phrase("Address", font2a));
            //datatable.addCell(new Phrase("Postal Code", font2a));
            //datatable.addCell(new Phrase("Phone", font2a));
            //datatable.addCell(new Phrase("Handphone", font2a));
            //datatable.addCell(new Phrase("Sex", font2a));
            datatable.addCell(new Phrase("Birth Place", font2a));
            datatable.addCell(new Phrase("Birth Date", font2a));
            datatable.addCell(new Phrase("Religion", font2a));
            //update by yunny,Dulunya Blood
            datatable.addCell(new Phrase("Education", font2a));
            //datatable.addCell(new Phrase("Astek Number", font2a));
            //datatable.addCell(new Phrase("Astek Date", font2a));
            datatable.addCell(new Phrase("Marital", font2a));
            //datatable.addCell(new Phrase("Locker", font2a));
            datatable.addCell(new Phrase("Department", font2a));
            datatable.addCell(new Phrase("Position", font2a));
            datatable.addCell(new Phrase("Level", font2a));
            //datatable.addCell(new Phrase("Category", font2a));
            datatable.addCell(new Phrase("Comm. Date", font2a));
            datatable.addCell(new Phrase("Address", font2a));
            datatable.addCell(new Phrase("Phone", font2a));
            datatable.addCell(new Phrase("Curier", font2a));
            
            // this is the end of the table header
            datatable.endHeaders();
            
            //datatable.setBackgroundColor(new Color(0xFF, 0xFF, 0xFF));
            datatable.setDefaultCellBorderWidth(1);
            datatable.setDefaultRowspan(1);
          if(listEmployee!=null && listEmployee.size()>0){
            for (int i = 0; i < listEmployee.size(); i++) {
                sessTmpSpecialEmployee =  (SessTmpSpecialEmployee) listEmployee.get(i);
                //update by satrya 2013-08-05
//                Vector temp = (Vector) listEmployee.get(i);
//                Employee employee = (Employee)temp.get(0);
//                Department department = (Department)temp.get(1);
//                Position position = (Position)temp.get(2);
//                com.dimata.harisma.entity.masterdata.Section section = (com.dimata.harisma.entity.masterdata.Section)temp.get(3);
//                EmpCategory empCategory = (EmpCategory)temp.get(4);
//                Level level = (Level)temp.get(5);
//                Religion religion = (Religion)temp.get(6);
//                Marital marital = (Marital)temp.get(7);
//                Locker locker = (Locker)temp.get(8);
                //System.out.println("Pembawa coy"+employee.)
                /*khusus untuk menampilkan education employee
                 * edited by Yunny
                 **/
                String whereCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+" = "+sessTmpSpecialEmployee.getEmployeeId();
                String orderCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" DESC ";
                Vector vedu = PstEmpEducation.list(0,0,whereCl,orderCl);
                
                Education education = new Education();
                EmpEducation empEducation = new EmpEducation();
                if(vedu!=null && vedu.size()>0)
                    {
                        empEducation = (EmpEducation)vedu.get(0);
                       
                        try{
                          if(empEducation!=null && empEducation.getEducationId()!=0){
                            education = PstEducation.fetchExc(+empEducation.getEducationId());
                          }
                        }catch(Exception e){
                            System.out.println("Error"+e.toString());}
                    }
               
               //Yunny edit sampe disini 
                
                datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
                datatable.addCell(new Phrase(String.valueOf(i+1), font2));
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getEmployeeNum()!=null?sessTmpSpecialEmployee.getEmployeeNum():"-", font2));
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getFullName()!=null?sessTmpSpecialEmployee.getFullName():"-", font2));
                //datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
                //datatable.addCell(new Phrase(employee.getAddress(), font2));
                //datatable.addCell(new Phrase(String.valueOf(employee.getPostalCode()), font2));
                //datatable.addCell(new Phrase(employee.getPhone(), font2));
                //datatable.addCell(new Phrase(employee.getHandphone(), font2));
                //datatable.addCell(new Phrase(PstEmployee.sexKey[employee.getSex()], font2));
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getBirthPlaceEmployee()!=null?sessTmpSpecialEmployee.getBirthPlaceEmployee():"-", font2));
                datatable.addCell(new Phrase( (sessTmpSpecialEmployee.getBirthDateEmployee()!=null?String.valueOf(Formater.formatDate(sessTmpSpecialEmployee.getBirthDateEmployee(), "dd MMM yyyy")):""), font2));
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getReligion()!=null?sessTmpSpecialEmployee.getReligion():"-", font2));
                //System.out.println("\tBloooooood = " + employee.getBloodType());
                datatable.addCell(new Phrase(education!=null && education.getEducation()!=null?education.getEducation():"-", font2));
                //datatable.addCell(new Phrase(employee.getAstekNum(), font2));
                //datatable.addCell(new Phrase(String.valueOf(employee.getAstekDate()), font2));
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getMaritalStatus()!=null ? sessTmpSpecialEmployee.getMaritalStatus():"-", font2));
               
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getDepartement()!=null?sessTmpSpecialEmployee.getDepartement():"-", font2));
                
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getPosition()!=null?sessTmpSpecialEmployee.getPosition():"-", font2));
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getLevel()!=null?sessTmpSpecialEmployee.getLevel():"-", font2));
                //datatable.addCell(new Phrase(empCategory.getEmpCategory(), font2));
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getCommercingDateEmployee()!=null ? String.valueOf(Formater.formatDate(sessTmpSpecialEmployee.getCommercingDateEmployee(), "dd MMM yyyy")):"", font2));
                datatable.addCell(new Phrase(sessTmpSpecialEmployee.getAddressEmployee()!=null?sessTmpSpecialEmployee.getAddressEmployee():"-", font2));
                if(sessTmpSpecialEmployee.getHandphone()==null){
                    datatable.addCell(new Phrase(sessTmpSpecialEmployee.getHandphone()!=null?sessTmpSpecialEmployee.getHandphone():"-", font2));
                }
                else{
                    datatable.addCell(new Phrase(sessTmpSpecialEmployee.getPhoneEmployee()!=null?sessTmpSpecialEmployee.getPhoneEmployee():"-", font2));
                }
              datatable.addCell(new Phrase(sessTmpSpecialEmployee.getCurrier()!=null && sessTmpSpecialEmployee.getCurrier().length()>0 && !sessTmpSpecialEmployee.getCurrier().equalsIgnoreCase("null")? sessTmpSpecialEmployee.getCurrier():"-", font2));
                    
                
                //datatable.addCell(new Phrase(employee.getBarcodeNumber(), font2));
            }
        }
            
            document.add(datatable);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
            System.out.println("Exception"+sessTmpSpecialEmployee.getEmployeeNum());
        }
        
        // step 5: closing the document
        document.close();
        
        // we have written the pdfstream to a ByteArrayOutputStream,
        // now we are going to write this outputStream to the ServletOutputStream
        // after we have set the contentlength (see http://www.lowagie.com/iText/faq.html#msie)
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }
}