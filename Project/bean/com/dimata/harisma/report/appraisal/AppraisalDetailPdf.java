/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.appraisal;

import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Formater;
import com.dimata.util.NumberSpeller;
import java.awt.Color;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.servlet.*;
import javax.servlet.http.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.html.HtmlWriter;


import com.dimata.harisma.entity.employee.appraisal.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormSection;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormSection;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.session.employee.appraisal.SessAppraisal;
import com.dimata.harisma.session.employee.assessment.SessAssessmentFormItem;
import com.dimata.harisma.session.employee.assessment.SessAssessmentFormSection;
import com.dimata.harisma.session.employee.assessment.SessAssessmentMain;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author artha
 */
public class AppraisalDetailPdf extends HttpServlet {
   
    /////////////////////////////////////////////////////////
    
            
    public static NumberSpeller numSpeller = new NumberSpeller();

    public static Color border = new Color(0x00, 0x00, 0x00);
    public static Color bgColor = new Color(240,240,240);
    public static Color blackColor = new Color(0,0,0);
    public static Color putih = new Color(250,250,250);

    // setting some fonts in the color chosen by the user
    public static Font fontHeaderBig = new Font(Font.HELVETICA, 12, Font.BOLD, border);
    public static Font fontHeaderBigS = new Font(Font.HELVETICA, 11, Font.BOLD, border);
    public static Font fontHeaderSmall = new Font(Font.HELVETICA, 9, Font.NORMAL, border);
    public static Font fontHeaderBoldSmall = new Font(Font.HELVETICA, 9, Font.BOLD, border);
    public static Font fontHeader = new Font(Font.HELVETICA, 10, Font.BOLD, border);
    public static Font fontContent = new Font(Font.HELVETICA, 10, Font.BOLD, border);
    public static Font tableContent = new Font(Font.HELVETICA, 10, Font.NORMAL, border);
    public static Font tableContentBox = new Font(Font.HELVETICA, 10, Font.NORMAL, border);
    public static Font fontSpellCharge = new Font(Font.HELVETICA, 10, Font.BOLDITALIC, border);
    public static Font fontItalicSmall = new Font(Font.HELVETICA, 9, Font.BOLDITALIC, border);
    public static Font fontItalic = new Font(Font.HELVETICA, 10, Font.BOLDITALIC, border);
    public static Font fontItalicBig = new Font(Font.HELVETICA, 12, Font.BOLDITALIC, border);
    public static Font fontItalicBottom = new Font(Font.HELVETICA, 10, Font.ITALIC, border);
    public static Font fontItalicBottomSmall = new Font(Font.HELVETICA, 9, Font.ITALIC, border);
    public static Font fontUnderline = new  Font(Font.HELVETICA, 10, Font.UNDERLINE, border);
    
    public static String imagesRoot =  PstSystemProperty.getValueByName("IMG_ROOT");
    public static String imgLogo = imagesRoot+"logo.jpg"; 
    public static String imgBox = imagesRoot+"icon/box.jpg"; 
    public static String imgCheckBox = imagesRoot+"icon/check_box.jpg"; 
        
    /////////////////////////////////////////////////////////ma
    
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
        System.out.println("===| AppraisalDetailPdf |===");
        /* creating the document object */
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        long oidAppraisalMain = FRMQueryString.requestLong(request, "appraisalOid");
        
        
        
        AppraisalMain appraisalMain = new AppraisalMain();
        AssessmentFormMain assessmentFormMain = new AssessmentFormMain();
        
        Employee employee = new Employee();
        Employee assessor = new Employee();
        Employee divHead = new Employee();
        Department empDep = new Department();
        Department assDep = new Department();
        Position empPos = new Position();
        Position assPos = new Position();
        Level empLevel = new Level();
        Level assLevel = new Level();
        
        try{
            appraisalMain = PstAppraisalMain.fetchExc(oidAppraisalMain);
            assessmentFormMain = SessAssessmentMain.getAssessment(appraisalMain.getOID());
        }catch(Exception exception){
           
        }
                
        if(appraisalMain.getOID()>0){
            try{
                employee = PstEmployee.fetchExc(appraisalMain.getEmployeeId());
                assessor = PstEmployee.fetchExc(appraisalMain.getAssesorId());
                divHead = PstEmployee.fetchExc(appraisalMain.getAssesorId());
            }catch(Exception ex){}
        }
        try{

            empDep = PstDepartment.fetchExc(employee.getDepartmentId());
            assDep = PstDepartment.fetchExc(assessor.getDepartmentId());
            empPos = PstPosition.fetchExc(employee.getPositionId());
            assPos = PstPosition.fetchExc(assessor.getPositionId());
            empLevel = PstLevel.fetchExc(employee.getLevelId());
            assLevel = PstLevel.fetchExc(assessor.getLevelId());
        }catch(Exception ex){}

        int start = 0;
        
        try{
             // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);

            // step 3.1: adding some metadata to the document
            document.addSubject("This is a subject.");
            document.addSubject("This is a subject two.");    
            //Header 
            HeaderFooter header = new HeaderFooter(new Phrase("", fontHeaderSmall), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
         //   document.setHeader(header);
            // FOOTER
            HeaderFooter footer = new HeaderFooter(new Phrase("Page : ", fontHeaderSmall), true);
            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
            document.setFooter(footer);
            
            // step 3.4: opening the document
            document.open();
            
            //INFORMATION
            //Information Header
            Table tableHeaderTitle = createInfHeader(assessmentFormMain);
            document.add(tableHeaderTitle);
            //Information Detail
            Table tableHeaderDetail = createInfDetailEmp(appraisalMain,assessmentFormMain);
            document.add(tableHeaderDetail);
            //Information
            Table tableHeaderInfDetail = createInfDetailRole();
            document.add(tableHeaderInfDetail);
            //Rank
            Table tableHeaderInfRank = createInfDetailRange();
            document.add(tableHeaderInfRank);
            //Create Page
            int maxPage = SessAssessmentFormItem.getMaxPage(assessmentFormMain.getOID());
            for(int i=1;i<=maxPage;i++){
                document.newPage();
                Table tablePage = createPage(appraisalMain, assessmentFormMain, i);
                document.add(tablePage);
            }
            
            //Menambahkan approval
            Table tableApproval = createCellTypeApproval(appraisalMain);
            document.add(tableApproval);
            //DATA
          
            
        }catch(Exception e){}
        
        
        document.close();
        
//        System.out.println("print==============");
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
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
    
    /** 
     * Header Information
     */    
    public Table createInfHeader(AssessmentFormMain assessmentFormMain)
    {        
        Table datatable = null;  
        Cell cellData = new Cell("");
        try
        {                        
            int maxColumn = 100;                        
            float headerwidths[] = new float[maxColumn];
            for(int i=0;i<100;i++){
                headerwidths[i] = 1;
            }
            
            //images
             /* image logo */
           Image logoImg = null;
            try{
                logoImg = Image.getInstance(imgLogo);
                logoImg.setAlignment(Image.ALIGN_CENTER);
                logoImg.scalePercent(50);
            }catch(Exception ex){
                System.out.println("EXCEPTION "+ex.toString());
            }
            
            
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(0); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
       //     datatable.setDefaultRowspan(1);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
          //  datatable.setBackgroundColor(putih);
            datatable.setBorder(Table.NO_BORDER);
            datatable.setDefaultCellBorder(Cell.NO_BORDER);
            //datatable.setBorder(Table.NO_BORDER);
            int rowSpan = 1;
            if(assessmentFormMain.getSubtitle()!=null){
                rowSpan = 2;
                if(assessmentFormMain.getTitle_L2()!=null){
                    rowSpan = 3;
                    if(assessmentFormMain.getSubtitle_L2()!=null){
                        rowSpan = 4;
                    }
                }
            }
            
            // 
            cellData = new Cell(new Chunk("",fontContent));
            cellData.add(logoImg);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);  
            cellData.setColspan(10);
            cellData.setRowspan(rowSpan);
            datatable.addCell(cellData);
            
            
            
            cellData = new Cell(new Phrase(assessmentFormMain.getTitle(),fontHeaderBig));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        //    cellData.setLeading(1);
            cellData.setColspan(90);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            datatable.addCell(cellData);
            
            if(assessmentFormMain.getSubtitle()!=null){
                cellData = new Cell(new Phrase(assessmentFormMain.getSubtitle(),fontHeader));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellData.setColspan(90);
                cellData.setRowspan(1);
                cellData.setLeading(12);
                cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                datatable.addCell(cellData);
            }
            if(assessmentFormMain.getTitle_L2()!=null){
                cellData = new Cell(new Phrase(assessmentFormMain.getTitle_L2(),fontItalicBig));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellData.setColspan(90);
                cellData.setRowspan(1);
            //    cellData.setLeading(1);
                cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                datatable.addCell(cellData);
            }
            if(assessmentFormMain.getSubtitle_L2()!=null){
                cellData = new Cell(new Phrase(assessmentFormMain.getSubtitle_L2(),fontItalic));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellData.setColspan(90);
                cellData.setRowspan(1);
                cellData.setLeading(12);
                cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                datatable.addCell(cellData);
            }
            
            // this is the end of the table header
          //  datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetail : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * Detail Information
     */    
    public Table createInfDetailEmp(AppraisalMain appraisalMain,AssessmentFormMain assessmentFormMain)
    {        
        Table datatable = null; 
        Cell cellData = new Cell("");
        try
        {                        
            int maxColumn = 4;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 1;
            headerwidths[1] = 32;
            headerwidths[2] = 2;
            headerwidths[3] = 65;
            
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(0); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
       //     datatable.setDefaultRowspan(1);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            datatable.setBackgroundColor(bgColor);
            datatable.setBorderColor(border);
            //datatable.setDefaultCellBorder(Cell.NO_BORDER);
            //datatable.setBorder(Table.NO_BORDER);
            
            //Table detail
            String strItem = "";
            String strMainData = assessmentFormMain.getMainData();
            boolean isDataAvailable = false;

            Employee employee = new Employee();
            Employee assessor = new Employee();
            Department empDep = new Department();
            Department assDep = new Department();
            Position empPos = new Position();
            Position assPos = new Position();
            Level empLevel = new Level();
            Level assLevel = new Level();

            try{
                employee = PstEmployee.fetchExc(appraisalMain.getEmployeeId());
                assessor = PstEmployee.fetchExc(appraisalMain.getAssesorId());
                empDep = PstDepartment.fetchExc(employee.getDepartmentId());
           //     assDep = PstDepartment.fetchExc(assessor.getDepartmentId());
                empPos = PstPosition.fetchExc(employee.getPositionId());
                assPos = PstPosition.fetchExc(assessor.getPositionId());
                empLevel = PstLevel.fetchExc(employee.getLevelId());
               // assLevel = PstLevel.fetchExc(assessor.getLevelId()); 

            }catch(Exception ex){}

            Vector vAppData = new Vector(1,1);

            vAppData.add(employee.getFullName());
            vAppData.add(empPos.getPosition());
            vAppData.add(empDep.getDepartment());
            vAppData.add(Formater.formatDate(appraisalMain.getDateAssumedPosition(), "dd MMMM yyyy"));
            vAppData.add(Formater.formatDate(appraisalMain.getDateJoinedHotel(), "dd MMMM yyyy"));
            vAppData.add(Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy"));
            vAppData.add(assessor.getFullName());
            vAppData.add(assPos.getPosition());
            vAppData.add(Formater.formatDate(appraisalMain.getDateOfAssessment(), "dd MMMM yyyy"));
            vAppData.add(Formater.formatDate(appraisalMain.getDateOfLastAssessment(), "dd MMMM yyyy"));
            vAppData.add(Formater.formatDate(appraisalMain.getDateOfNextAssessment(), "dd MMMM yyyy"));

            //Space
            cellData = new Cell(new Phrase("",fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellData.setLeading(1);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            datatable.addCell(cellData);
            
            cellData = new Cell(new Phrase("",fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellData.setLeading(1);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            datatable.addCell(cellData);
            //Space
            cellData = new Cell(new Phrase("",fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellData.setLeading(1);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            datatable.addCell(cellData);
            //Space
            cellData = new Cell(new Phrase("",fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellData.setLeading(1);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            datatable.addCell(cellData);
            
            if(PstAssessmentFormMain.fieldFormTypes!=null && PstAssessmentFormMain.fieldFormTypes.length>0){
                for(int i=0; i<PstAssessmentFormMain.fieldFormTypes.length; i++){
                       boolean isUsed = PstAssessmentFormMain.cekFormUsed(strMainData, i);
                      // System.out.println(i+"=="+isUsed);
                       String strVal = PstAssessmentFormMain.fieldFormValue[i][PstAssessmentFormMain.LANGUAGE_FIRST];
                       String strValL2 = PstAssessmentFormMain.fieldFormValue[i][PstAssessmentFormMain.LANGUAGE_OTHER];

                    if(isUsed){
                        String strValue = (String)vAppData.get(i);
                        isDataAvailable = true;
                        //Title 1
                        //Space
                        cellData = new Cell(new Phrase("",fontHeader));
                        cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
                        cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                        
                        cellData = new Cell(new Phrase(strVal,fontHeader));
                        cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
                        cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                        //Space
                        cellData = new Cell(new Phrase(":",fontHeader));
                        cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
                        cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                        //Data Employee
                        cellData = new Cell(new Phrase(strValue,fontHeader));
                        cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
                        cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                        //Title 2
                        //Space
                        cellData = new Cell(new Phrase("",fontItalicSmall));
                        cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                        cellData.setLeading(8);
                        cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                        
                        cellData = new Cell(new Phrase("("+strValL2+")",fontItalicSmall));
                        cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                        cellData.setLeading(8);
                        cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                        //Space
                        cellData = new Cell(new Phrase("",fontItalicSmall));
                        cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                        cellData.setLeading(8);
                        cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                        //Space
                        cellData = new Cell(new Phrase("",fontItalicSmall));
                        cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                        cellData.setLeading(8);
                        cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                    }
                }
            }
            //Space
            cellData = new Cell(new Phrase("",fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellData.setLeading(3);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            datatable.addCell(cellData);
            //Space
            cellData = new Cell(new Phrase("",fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellData.setLeading(3);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            datatable.addCell(cellData);
            //Space
            cellData = new Cell(new Phrase("",fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellData.setLeading(3);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            datatable.addCell(cellData);
            //Space
            cellData = new Cell(new Phrase("",fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellData.setLeading(3);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            datatable.addCell(cellData);
            
            // this is the end of the table header
         //   datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetail : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * Detail Information
     */    
    public Table createInfDetailRole()
    {        
        Table datatable = null; 
        Cell cellData = new Cell("");
        try
        {                        
            int maxColumn = 2;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 98;
            
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(0); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
       //     datatable.setDefaultRowspan(1);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
        //    datatable.setBackgroundColor(bgColor);
           // datatable.setBorderColor(border);
            datatable.setDefaultCellBorder(Cell.NO_BORDER);
            datatable.setBorder(Table.NO_BORDER);
            
            //Table detail
            //Header
            cellData = new Cell(new Phrase("Preparation",fontContent));
            cellData.setColspan(2);
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            //Space
            //cellData = new Cell(new Phrase("",fontContent)); datatable.addCell(cellData);
            
            cellData = new Cell(new Phrase("(Persiapan)",fontItalic));
            cellData.setColspan(2);
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
            //cellData = new Cell(new Phrase("",fontContent)); datatable.addCell(cellData);
            
            //01
            cellData = new Cell(new Phrase("-",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("Use this form only for those positions identified in the assessment guide.",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("(Gunakan Formulir ini hanya untuk jabatan yang tercantum dalam petunjuk penilaian)",fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
            
            //02
            cellData = new Cell(new Phrase("-",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("Carefully read Human Resources P&P-66 - prior to assessment.",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("(Bacalah dengan cermat peraturandan prosedur Human Resources P&P-66 - sebelum penilaian dilakukan)",fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
            
            //Assessor Information
            //Space
            cellData = new Cell(new Phrase("",fontContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",fontContent)); datatable.addCell(cellData);
            //Header
            cellData = new Cell(new Phrase("Assessor",fontContent));
            cellData.setColspan(2);
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            //Space
           // cellData = new Cell(new Phrase("",fontContent)); datatable.addCell(cellData);
            
            cellData = new Cell(new Phrase("(Penilai/Penguji)",fontItalic));
            cellData.setColspan(2);
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
           // cellData = new Cell(new Phrase("",fontContent)); datatable.addCell(cellData);
            
            //01
   //        cellData = new Cell(new Phrase("",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("Minimum two weeks before the scheduled review, you should:",tableContent));
            cellData.setColspan(2);
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
        //    cellData = new Cell(new Phrase("",fontContent)); datatable.addCell(cellData);
            
            cellData = new Cell(new Phrase("Minimal 2 minggu sebelum penilaian dijadwalkan, anda harus:",fontItalicBottom));
            cellData.setColspan(2);
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
      //      cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            
            //02
            cellData = new Cell(new Phrase("-",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("Explain the performance assessment process to the Band Member assessed.",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("(Terangkan proses penilaian kepada Band Member yang akan dinilai)",fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
            
            //03
            cellData = new Cell(new Phrase("-",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("Provide a performance assessment form to the Band Member and schedule date & time for the interview.",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("(Sediakan formulir penilaian kepada Band Member & membuat jadwal untuk penilaian)",fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
            
            //04
            cellData = new Cell(new Phrase("-",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("After receiving the completed form, the assessor considers the information received and objectively completes the assessor's column. The assessor collects all supporting data required to carry put the assessment.",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("(Setelah menerima formulir yang telah diisi secara lengkap, penilai mempertimbangkan informasi yang diterima dan melengkapi kolom penguji secara objectif. Penilai mengumpulkan seluruh data yang diperlukan untuk melakukan penilaian.)",fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
                    
            //At interview
            cellData = new Cell(new Phrase("",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",tableContent)); datatable.addCell(cellData);
            
            cellData = new Cell(new Phrase("",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("At Interview",fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            
            cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("(Saat Wawancara)",fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
            
            //01
            cellData = new Cell(new Phrase("-",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("Comments and ratings are agreed and recorded on Assessor's Form. Agree on an Action Plan together with the Band Member. Forward this form duly completed in ink, with appropriate signatures to the Human Resources office.",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("(Komentar dan tingkat penilaian diakui dan dicatat dalam formulir penilaian. Rencana kegiatan disetujui juga oleh band memeber. Formulir diisi dengan Pulpen dan dengan tanda tangan yang jelas untuk dikumpulkan ke Human Resources Departemen)",fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
            
            //02
            cellData = new Cell(new Phrase("-",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("If an agreement is not reached, the supervisor's rating prevails with an explanation as appropriate.",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("(Apabila tidak tercapai kesepakatan, rangking ditentukan oleh supervisor dengan penjelasan yang beralasan)",fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
                    
            //03
            cellData = new Cell(new Phrase("-",tableContent)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("Based on Band Member's and Assessor's comments, a constructive discussion should take place resulting in an individual training and development plan.",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            cellData = new Cell(new Phrase("",fontItalicBottom)); datatable.addCell(cellData);
            cellData = new Cell(new Phrase("(Berdasarkan komentar dari band member dan penilai, akan diambil langkah-langkah untuk pengembangan dan training individu yang diperlukan)",fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
                    
            // this is the end of the table header
         //   datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetail : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * Detail Information
     */    
    public Table createInfDetailRange()
    {        
        Table datatable = null; 
        Cell cellData = new Cell("");
        try
        {                        
            Vector vRating = new Vector();
            vRating = PstEvaluation.list(0, 0, "", PstEvaluation.fieldNames[PstEvaluation.FLD_MAX_PERCENTAGE]+" DESC");
            if(vRating==null){   // update by Kartika : 20150205
                new Vector();
            }
            if(vRating.size()==0){
                Evaluation evaluation = new Evaluation();
                evaluation.setCode("TBD");
                evaluation.setMaxPercentage(100);
                evaluation.setName("Please set Evaluation Createria ( see Master > Assesment )");
            }
            
            int colSize = 100/(vRating==null || vRating.size()==0? 1:vRating.size());
            int borderWidth = 1;
            
            int maxColumn = vRating.size();                        
            float headerwidths[] = new float[maxColumn];
            for(int i=0;i< maxColumn;i++){
                headerwidths[i] = colSize;
            }
            
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(0); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
       //     datatable.setDefaultRowspan(1);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
        //    datatable.setBackgroundColor(bgColor);
           // datatable.setBorderColor(border);
            datatable.setDefaultCellBorder(Cell.NO_BORDER);
            datatable.setBorder(Table.NO_BORDER);
            
            //Space
            cellData = new Cell(new Phrase("",fontContent));
            cellData.setColspan(maxColumn);
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            
            //Header
            cellData = new Cell(new Phrase("Assessment Ratings:",fontContent));
            cellData.setColspan(maxColumn);
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            datatable.addCell(cellData);
            //Space
            //cellData = new Cell(new Phrase("",fontContent)); datatable.addCell(cellData);
            
            cellData = new Cell(new Phrase("(Tingkat Penilaian)",fontItalic));
            cellData.setColspan(maxColumn);
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            datatable.addCell(cellData);
            
            //Table detail
            if(vRating.size()>0){
                double maxValPrev = 0;
                double maxValCurr = 0;
                for(int i=0;i<vRating.size();i++){
                    Evaluation evCurr = (Evaluation)vRating.get(i);
                    Evaluation evNext = new Evaluation();
                    if(i+1<vRating.size()){
                        evNext = (Evaluation)vRating.get(i+1);
                    }
                    maxValCurr = evCurr.getMaxPercentage();
                    maxValPrev = evNext.getMaxPercentage();
                    String strTitle = evCurr.getName();
                    int posTitleDiv = strTitle.indexOf("(");
                    //Title
                    if(posTitleDiv>-1){
                        if(i==0){
                            cellData = new Cell(new Chunk(String.valueOf(strTitle.substring(0,posTitleDiv)+" "+strTitle.substring(posTitleDiv,strTitle.length())),fontContent));
                            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.NO_BORDER);
                            datatable.addCell(cellData);
                        }else{
                            cellData = new Cell(new Chunk(String.valueOf(strTitle.substring(0,posTitleDiv)+" "+strTitle.substring(posTitleDiv,strTitle.length())),fontContent));
                            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.TOP | Rectangle.RIGHT | Rectangle.NO_BORDER);
                            datatable.addCell(cellData);
                        }
                    }else{
                        if(i==0){
                            cellData = new Cell(new Chunk(String.valueOf(strTitle),fontContent));
                            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.NO_BORDER);
                            datatable.addCell(cellData);
                        }else{
                            cellData = new Cell(new Chunk(String.valueOf(strTitle),fontContent));
                            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.TOP | Rectangle.RIGHT | Rectangle.NO_BORDER);
                            datatable.addCell(cellData);
                        
                        }
                    }
               }
                //space
               for(int i=0;i<vRating.size();i++){
                    //Space
                    if(i==0){
                        cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
                        cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                        cellData.setBorderColor(Color.BLACK);
                        cellData.setBorderWidth(borderWidth);
                        cellData.setLeading(1);
                        cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                    }else{
                        cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
                        cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                        cellData.setBorderColor(Color.BLACK);
                        cellData.setBorderWidth(borderWidth);
                        cellData.setLeading(1);
                        cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                        datatable.addCell(cellData);
                    }
               } 
              //Percentase
              for(int i=0;i<vRating.size();i++){
                    Evaluation evCurr = (Evaluation)vRating.get(i);
                    Evaluation evNext = new Evaluation();
                    if(i+1<vRating.size()){
                        evNext = (Evaluation)vRating.get(i+1);
                    }
                    maxValCurr = evCurr.getMaxPercentage();
                    maxValPrev = evNext.getMaxPercentage();
                    if(maxValPrev>0){
                        if(i==0){
                            cellData = new Cell(new Chunk(String.valueOf((maxValPrev+1)+"% - "+maxValCurr+"%"),fontItalic));
                            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                            datatable.addCell(cellData);
                        }else{
                            cellData = new Cell(new Chunk(String.valueOf((maxValPrev+1)+"% - "+maxValCurr+"%"),fontItalic));
                            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                            datatable.addCell(cellData);
                        }
                    }else{
                        if(i==0){
                            cellData = new Cell(new Chunk(String.valueOf((maxValCurr+1)+"% & below"),fontItalic));
                            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                            datatable.addCell(cellData);
                        }else{
                            cellData = new Cell(new Chunk(String.valueOf((maxValCurr+1)+"% & below"),fontItalic));
                            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                            datatable.addCell(cellData);
                        }
                    }
                }

                //Desc 1
                for(int ij=0;ij<vRating.size();ij++){
                    Evaluation evCurr = (Evaluation)vRating.get(ij);
                    Evaluation evNext = new Evaluation();
                    if(ij+1<vRating.size()){
                        evNext = (Evaluation)vRating.get(ij+1);
                    }
                    maxValCurr = evCurr.getMaxPercentage();
                    maxValPrev = evNext.getMaxPercentage();
                    String strDesc = evCurr.getDesription();
                    int posDescDiv = strDesc.indexOf("(");

                    if(posDescDiv>-1){
                       //First Languange
                        if(ij==0){
                            cellData = new Cell(new Chunk(String.valueOf(strDesc.substring(0,posDescDiv)),tableContent));
                            //cellData.setRight(2);  ////////////////Temp
                            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                            datatable.addCell(cellData);
                        }else{
                            cellData = new Cell(new Chunk(String.valueOf(strDesc.substring(0,posDescDiv)),tableContent));
                            //cellData.setRight(2);
                            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                            datatable.addCell(cellData);
                        }
                    }else{
                        //First Langange
                        if(ij==0){
                            cellData = new Cell(new Chunk(String.valueOf(strDesc),tableContent));
                            //cellData.setRight(2);
                            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                            datatable.addCell(cellData);
                        }else{
                            cellData = new Cell(new Chunk(String.valueOf(strDesc),tableContent));
                            //cellData.setRight(2);
                            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                            datatable.addCell(cellData);
                        }
                    }
                }
                
                //Desc 2
                for(int ij=0;ij<vRating.size();ij++){
                    Evaluation evCurr = (Evaluation)vRating.get(ij);
                    Evaluation evNext = new Evaluation();
                    if(ij+1<vRating.size()){
                        evNext = (Evaluation)vRating.get(ij+1);
                    }
                    maxValCurr = evCurr.getMaxPercentage();
                    maxValPrev = evNext.getMaxPercentage();
                    String strDesc = evCurr.getDesription();
                    int posDescDiv = strDesc.indexOf("(");

                    if(posDescDiv>-1){
                        //Sub languange
                        if(ij==0){
                            cellData = new Cell(new Chunk(String.valueOf(strDesc.substring(posDescDiv,strDesc.length())),fontItalicBottom));
                            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                            datatable.addCell(cellData);
                        }else{
                            cellData = new Cell(new Chunk(String.valueOf(strDesc.substring(posDescDiv,strDesc.length())),fontItalicBottom));
                            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                            datatable.addCell(cellData);
                        }
                    }else
                        if(ij==0){
                            cellData = new Cell(new Chunk(String.valueOf(""),fontItalicBottom));
                            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                            datatable.addCell(cellData);
                        }else{
                            cellData = new Cell(new Chunk(String.valueOf(""),fontItalicBottom));
                            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                            cellData.setBorderColor(Color.BLACK);
                            cellData.setBorderWidth(borderWidth);
                            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                            datatable.addCell(cellData);
                        }
                    }
                } 
                    
            // this is the end of the table header
         //   datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createInfDetailRange : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * Detail Information
     */    
    public Table createPage(AppraisalMain appraisalMain,AssessmentFormMain assessmentFormMain,int page)
    {        
        Table datatable = null; 
        Cell cellData = new Cell("");
        try
        {                                    
            int maxColumn = 30;                        
            float headerwidths[] = new float[maxColumn];
            for(int i=0;i< maxColumn;i++){
                headerwidths[i] = 100/30;
            }
            
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(0); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
       //     datatable.setDefaultRowspan(1);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
        //    datatable.setBackgroundColor(bgColor);
           // datatable.setBorderColor(border);
            datatable.setDefaultCellBorder(Cell.NO_BORDER);
            datatable.setBorder(Table.NO_BORDER);
            
            /////////////////////////////////////////TO CREATE PAGE
          //  System.out.println("::::::::::: CREATE PAGE APPRAISAL "+page);
            Vector vSection = new Vector(1,1);
          //  Vector vItem = new Vector(1,1);
            vSection=SessAssessmentFormSection.getSections(page, assessmentFormMain.getOID());
         //   vItem=PstAssessmentFormItem.listItem(mainOid, page);


            Hashtable hAppraisal = new Hashtable();
            hAppraisal = SessAppraisal.listAppraisal(appraisalMain.getOID());

            if(vSection.size()>0){
                for(int k=0;k<vSection.size();k++){
                    AssessmentFormSection assSection = new AssessmentFormSection();
                    assSection = (AssessmentFormSection)vSection.get(k);
                    if(assSection.getPage()==page){
                        datatable = createCellSection(datatable, assSection);
                    }

                    Vector vItems = new Vector(1,1);
                    vItems = SessAssessmentFormItem.listItem(assSection.getOID(), page);

                    for(int l=0;l<vItems.size();l++){
                        AssessmentFormItem assItem = new AssessmentFormItem();
                        assItem = (AssessmentFormItem)vItems.get(l);
                        if(assItem.getPage()==page){
                            Appraisal appraisal = new Appraisal();
                            if(
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT||
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_SELECT_2_WITHOUT_RANGE||
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE||
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT||
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_EMP_COMM||
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_ASS_COMM||
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT||
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK||
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM||
                                assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM
                            ){
                                if(hAppraisal.get(String.valueOf(assItem.getOID()))!=null){
                                    appraisal = (Appraisal)hAppraisal.get(String.valueOf(assItem.getOID()));
                                }
                            }
                           // System.out.println("KE- "+i);
                            switch(assItem.getType()){
                                case PstAssessmentFormItem.ITEM_TYPE_SPACE : 
                               //     strPage+=createItemTypeSpace(assItem);
                                    createCellSpace(datatable, assItem);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT :
                                    datatable = createCellType2ColNoText(datatable, assItem, appraisal);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER : 
                                    datatable = createCellType2ColHeader(datatable, assItem);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT :
                                    datatable = createCellType1ColWithText(datatable, assItem, appraisal);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_TEXT :
                                    datatable = createCellTypeText(datatable, assItem);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_TEXT_BOLD :
                                    datatable = createCellTypeTextBold(datatable, assItem);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER : 
                                    datatable = createCellTypeSelectHeader(datatable, assItem);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_SELECT_2_WITHOUT_RANGE : 
                                    datatable = createCellTypeSelectA(datatable, assItem, appraisal);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE : 
                                    datatable = createCellTypeSelectB(datatable, assItem, appraisal);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT : 
                                    datatable = createCellTypeInput(datatable, assItem, appraisal);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK :
                                    datatable = createCellTypeInputCheck(datatable, assItem, appraisal);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK_HEADER :
                                    datatable = createCellTypeInputCheckHeader(datatable, assItem);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM : 
                                    datatable = createCellType2ColComm(datatable, assItem, appraisal);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM : 
                                    datatable = createCellType2ColComm(datatable, assItem, appraisal);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_INPUT_EMP_COMM : 
                                    datatable = createCellType1ColCommEmp(datatable, assItem, appraisal);
                                    break;
                                case PstAssessmentFormItem.ITEM_TYPE_INPUT_ASS_COMM : 
                                    datatable = createCellType1ColCommAss(datatable, assItem, appraisal);
                                    break;
                            }
                        }
                    }
                }
            }
            
            //System.out.println(strPage);
            if(vSection.size()>0){
                return datatable;
            }
            return datatable;
            ///////////////////////////////////////////////////////
            
         // this is the end of the table header
         //   datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createPage : "+e.toString());
        }
        return datatable;
    }
    
    ///////////////////CELL CREATOR////////////////////
    private static Table createCellSectionTitle(Table table,AssessmentFormSection assessmentFormSection){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        //01
        try{
            //Row 1 Col 1
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormSection.getSection()),fontHeader));
            cellData.setColspan(span1);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            table.addCell(cellData);
            //Row 1 Col 2
            if(assessmentFormSection.getSection_L2().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormSection.getSection_L2()),fontItalicBottom));
                cellData.setColspan(span1);
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setLeading(10);
                table.addCell(cellData);
            }
            //Row 1 Col 3
            if(assessmentFormSection.getDescription().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormSection.getDescription()),fontHeaderBoldSmall));
                cellData.setColspan(span1);
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                table.addCell(cellData);
            }
            //Row 1 Col 4
            if(assessmentFormSection.getDescription_L2().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormSection.getDescription_L2()),fontItalicBottomSmall));
                cellData.setColspan(span1);
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setLeading(10);
                table.addCell(cellData);
            }
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellSectionTitle : "+ex.toString());}
        return table;
    }
    
    private static Table createCellSectionTitleWithBg(Table table,AssessmentFormSection assessmentFormSection){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int borderWidth = 1;
        //01
        try{
            //Row 1 Col 1
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormSection.getSection()),fontHeader));
            cellData.setColspan(span1);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(bgColor);
            cellData.setBorderWidth(borderWidth);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.NO_BORDER);
            table.addCell(cellData);
            
            //Row 1 Col 2
            if(assessmentFormSection.getSection_L2().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormSection.getSection_L2()),fontItalicBottom));
                cellData.setColspan(span1);
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setBorderColor(Color.BLACK);
                cellData.setBackgroundColor(bgColor);
                cellData.setBorderWidth(borderWidth);
                cellData.setLeading(10);
                cellData.setRowspan(1);
                cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                table.addCell(cellData);
            }
            //Row 1 Col 3
            if(assessmentFormSection.getDescription().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormSection.getDescription()),fontHeaderBoldSmall));
                cellData.setColspan(span1);
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setBorderColor(Color.BLACK);
                cellData.setBackgroundColor(bgColor);
                cellData.setBorderWidth(borderWidth);
                cellData.setRowspan(1);
                cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                table.addCell(cellData);
            }
            //Row 1 Col 4
            if(assessmentFormSection.getDescription_L2().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormSection.getDescription_L2()),fontItalicBottomSmall));
                cellData.setColspan(span1);
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setBorderColor(Color.BLACK);
                cellData.setBackgroundColor(bgColor);
                cellData.setBorderWidth(borderWidth);
                cellData.setLeading(10);
                cellData.setRowspan(1);
                cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                table.addCell(cellData);
            }
            
            cellData = new Cell(new Chunk(String.valueOf(""),fontItalicBottomSmall));
            cellData.setColspan(span1);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(bgColor);
            cellData.setBorderWidth(borderWidth);
            cellData.setRowspan(1);
            cellData.setLeading(1);
            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
            table.addCell(cellData);
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellSectionTitle : "+ex.toString());}
        return table;
    }
    
    private static Table createCellSection(Table table,AssessmentFormSection assessmentFormSection){
        switch(assessmentFormSection.getType()){
            case PstAssessmentFormSection.TYPE_TEXT_ONLY: 
                table = createCellSectionTitle(table, assessmentFormSection);
                break;
            case PstAssessmentFormSection.TYPE_WITH_BACKGROUND: 
                table = createCellSectionTitleWithBg(table, assessmentFormSection);
                break;
            default: 
                table = createCellSectionTitle(table, assessmentFormSection);
                break;
        }
        return table;
    }
    
    private static Table createCellType2ColHeader(Table table,AssessmentFormItem assessmentFormItem){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int borderWidth = 1;
        int span1 = 30;
        int span2 = 15;
        //01
        try{
            //Title 1
             if(assessmentFormItem.getTitle().length()>0){
                    cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),fontHeader));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBackgroundColor(bgColor);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span2);
                    cellData.setRowspan(1);
                    cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.NO_BORDER);
                    table.addCell(cellData);
              }
             //Title 2
              if(assessmentFormItem.getItemPoin1().length()>0){
                    cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin1()),fontHeader));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBackgroundColor(bgColor);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span2);
                    cellData.setRowspan(1);
                    cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.NO_BORDER);
                    table.addCell(cellData);
              }
             
             
             
             //Sub title 1
              if(assessmentFormItem.getTitle_L2().length()>0){
                    cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle_L2()),fontItalic));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBackgroundColor(bgColor);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span2);
                    cellData.setRowspan(1);
                    cellData.setLeading(10);
                    if(assessmentFormItem.getHeight()>1){
                        cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                    }else{
                        cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                    }
                    table.addCell(cellData);
              }else if(assessmentFormItem.getTitle().length()>0){
                    cellData = new Cell(new Chunk(String.valueOf(""),fontItalic));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBackgroundColor(bgColor);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span2);
                    cellData.setLeading(10);
                    cellData.setRowspan(1);
                    if(assessmentFormItem.getHeight()>1){
                        cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                    }else{
                        cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                    }
                    table.addCell(cellData);
              }
             //Sub title2
              if(assessmentFormItem.getItemPoin2().length()>0){
                    cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin2()),fontItalic));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBackgroundColor(bgColor);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span2);
                    cellData.setRowspan(1);
                    cellData.setLeading(10);
                    if(assessmentFormItem.getHeight()>1){
                        cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                    }else{
                        cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                    }
                    table.addCell(cellData);
              }else if(assessmentFormItem.getItemPoin1().length()>0){
                    cellData = new Cell(new Chunk(String.valueOf(""),fontItalic));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBackgroundColor(bgColor);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span2);
                    cellData.setRowspan(1);
                    cellData.setLeading(10);
                    if(assessmentFormItem.getHeight()>1){
                        cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                    }else{
                        cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                    }
                    table.addCell(cellData);
              }
             for(int i=1;i<assessmentFormItem.getHeight();i++){
                 for(int j=0;j<2;j++){
                    if(j==0){
                        cellData = new Cell(new Chunk(String.valueOf(""),fontItalic));
                        cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                        cellData.setBorderColor(Color.BLACK);
                        cellData.setBackgroundColor(bgColor);
                        cellData.setBorderWidth(borderWidth);
                        cellData.setColspan(span2);
                        cellData.setRowspan(1);
                        if(i==assessmentFormItem.getHeight()-1){
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                        }else{
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                        }
                        table.addCell(cellData);
                    }else{
                        cellData = new Cell(new Chunk(String.valueOf(""),fontItalic));
                        cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                        cellData.setBorderColor(Color.BLACK);
                        cellData.setBackgroundColor(bgColor);
                        cellData.setBorderWidth(borderWidth);
                        cellData.setColspan(span2);
                        cellData.setRowspan(1);
                        if(i==assessmentFormItem.getHeight()-1){
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
                        }else{
                            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                        }
                        table.addCell(cellData);
                    }
                 }
              }
              
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellType2ColHeader : "+ex.toString());}
        return table;
    }
    
    private static Table createCellType2ColNoText(Table table,AssessmentFormItem assessmentFormItem, Appraisal appraisal){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int borderWidth = 1;
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        
        int maxTexts = 120;
        //01
        try{
            //////////////////////////////////////////////////
            //Row 1 Col 1
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getNumber()+"."),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span30);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.NO_BORDER | Rectangle.NO_BORDER);  
            table.addCell(cellData);
           
          //Row 2 Col 1
          if(assessmentFormItem.getTitle().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setBorderColor(Color.BLACK);
                cellData.setBorderWidth(borderWidth);
                cellData.setColspan(span2-span30);
                cellData.setRowspan(1);
                cellData.setBorder(Rectangle.NO_BORDER | Rectangle.TOP | Rectangle.RIGHT | Rectangle.NO_BORDER);
                table.addCell(cellData);
          }
          
          //Row 3 Col 1
          
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span2-2);
            cellData.setRowspan(2);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            table.addCell(cellData);
          //Row 3 Col 1  
            cellData = new Cell(new Chunk(String.valueOf(((appraisal.getRating()>0)?appraisal.getRating():0)),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(2);
            cellData.setRowspan(2);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            table.addCell(cellData);
          
            //Row 1 Col 2
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span30);
            cellData.setRowspan(1);
//            System.out.println("----------------->"+cellData.height());
            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);  
            table.addCell(cellData);
            
          if(assessmentFormItem.getTitle_L2().length()>0){
              //Row 2 Col 2
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle_L2()),fontItalic));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setBorderColor(Color.BLACK);
                cellData.setLeading(8);
                cellData.setBorderWidth(borderWidth);
                cellData.setColspan(span2-span30);
                cellData.setRowspan(1);
                cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
                table.addCell(cellData);
          }else if(assessmentFormItem.getTitle().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(""),fontItalic));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setBorderColor(Color.BLACK);
                cellData.setLeading(8);
                cellData.setBorderWidth(borderWidth);
                cellData.setColspan(span2-span30);
                cellData.setRowspan(1);
                cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
                table.addCell(cellData);
          }
            
          int iSub = 0;
          if(assessmentFormItem.getItemPoin1().length()>0){
              iSub = 1;
              if(assessmentFormItem.getItemPoin3().length()>0){
                  iSub = 2;
                  if(assessmentFormItem.getItemPoin5().length()>0){
                      iSub = 3;
                  }
              }
          }
          
          
          //Row 1 Col 3
          if(iSub==1||iSub==2||iSub==3){
              cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin1()),fontContent));
              cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
              cellData.setVerticalAlignment(Cell.ALIGN_TOP);
              cellData.setBorderColor(Color.BLACK);
              cellData.setBorderWidth(borderWidth);
              cellData.setColspan(span2);
              cellData.setRowspan(1);
              cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
              table.addCell(cellData);
          }else{
              cellData = new Cell(new Chunk(String.valueOf(fitText(appraisal.getEmpComment(),assessmentFormItem)),tableContentBox));
              cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
              cellData.setVerticalAlignment(Cell.ALIGN_TOP);
              cellData.setBorderColor(Color.BLACK);
              cellData.setBorderWidth(borderWidth);
              cellData.setColspan(span2);
              cellData.setRowspan(1);
      //        System.out.println("::::::::::::::: "+cellData.cellWidth());
              cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
              table.addCell(cellData);
          }
          
          
          //Row 2 Col 3
          cellData = new Cell(new Chunk(String.valueOf(fitText(appraisal.getAssComment(),assessmentFormItem)),tableContentBox));
          cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
          cellData.setVerticalAlignment(Cell.ALIGN_TOP);
          cellData.setBorderColor(Color.BLACK);
          cellData.setBorderWidth(borderWidth);
          cellData.setColspan(span2);
          switch(iSub){
              case 1: cellData.setRowspan(3); break;
              case 2: cellData.setRowspan(6); break;
              case 3: cellData.setRowspan(9); break;
              default: cellData.setRowspan(1); break;
          }
          cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
          table.addCell(cellData);
          
          //Row 1 Col 4
          if(iSub==1||iSub==2||iSub==3){
              cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin2()),fontItalic));
              cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
              cellData.setVerticalAlignment(Cell.ALIGN_TOP);
              cellData.setBorderColor(Color.BLACK);
              cellData.setBorderWidth(borderWidth);
              cellData.setLeading(8);
              cellData.setColspan(span2);
              cellData.setRowspan(1);
              cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
              table.addCell(cellData);
          
         
          //Row 1 Col 5
          cellData = new Cell(new Chunk(String.valueOf(fitText(appraisal.getEmpComment(),assessmentFormItem)),tableContentBox));
          cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
          cellData.setVerticalAlignment(Cell.ALIGN_TOP);
          cellData.setBorderColor(Color.BLACK);
          cellData.setBorderWidth(borderWidth);
          cellData.setColspan(span2);
          cellData.setRowspan(1);
  //        System.out.println("::::::::::::::: "+cellData.cellWidth());
          cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
          table.addCell(cellData);
          
           }
          
          if(iSub==1||iSub==2||iSub==3){
          //Row 1 Col 6
              cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin3()),fontContent));
              cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
              cellData.setVerticalAlignment(Cell.ALIGN_TOP);
              cellData.setBorderColor(Color.BLACK);
              cellData.setBorderWidth(borderWidth);
              cellData.setColspan(span2);
              cellData.setRowspan(1);
              cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
              table.addCell(cellData);
              
          //Row 1 Col 7
              cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin4()),fontItalic));
              cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
              cellData.setVerticalAlignment(Cell.ALIGN_TOP);
              cellData.setBorderColor(Color.BLACK);
              cellData.setBorderWidth(borderWidth);
              cellData.setLeading(8);
              cellData.setColspan(span2);
              cellData.setRowspan(1);
              cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
              table.addCell(cellData);
          
              //Row 1 Col 8
              cellData = new Cell(new Chunk(String.valueOf(fitText(appraisal.getAnswer_1(),assessmentFormItem)),tableContentBox));
              cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
              cellData.setVerticalAlignment(Cell.ALIGN_TOP);
              cellData.setBorderColor(Color.BLACK);
              cellData.setBorderWidth(borderWidth);
              cellData.setColspan(span2);
              cellData.setRowspan(1);
              cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
              table.addCell(cellData);
          }
          
          if(iSub==3){
          //Row 1 Col 9
              cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin5()),fontContent));
              cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
              cellData.setVerticalAlignment(Cell.ALIGN_TOP);
              cellData.setBorderColor(Color.BLACK);
              cellData.setBorderWidth(borderWidth);
              cellData.setColspan(span2);
              cellData.setRowspan(1);
              cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
              table.addCell(cellData);
              
          //Row 1 Col 10
              cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin6()),fontItalic));
              cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
              cellData.setVerticalAlignment(Cell.ALIGN_TOP);
              cellData.setBorderColor(Color.BLACK);
              cellData.setBorderWidth(borderWidth);
              cellData.setLeading(8);
              cellData.setColspan(span2);
              cellData.setRowspan(1);
              cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
              table.addCell(cellData);
          
              //Row 1 Col 11
              cellData = new Cell(new Chunk(String.valueOf(fitText(appraisal.getAnswer_2(),assessmentFormItem)),tableContentBox));
              cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
              cellData.setVerticalAlignment(Cell.ALIGN_TOP);
              cellData.setBorderColor(Color.BLACK);
              cellData.setBorderWidth(borderWidth);
              cellData.setColspan(span2);
              cellData.setRowspan(1);
              cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);  
              table.addCell(cellData);
          }
          //Penutup
          //Row 1 Col 12
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setLeading(2);
            cellData.setColspan(span2);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);  
            table.addCell(cellData);
            
          //Row 2 Col 5
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setLeading(2);
            cellData.setColspan(span2);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);  
            table.addCell(cellData);
          
          
           //Untuk menampilkan- total nilai
          int maxNumber = SessAssessmentFormItem.getMaxNumber(assessmentFormItem.getAssFormSection());
          if(maxNumber == assessmentFormItem.getNumber()){
                AppraisalMain appMainTemp = new AppraisalMain();
                try{
                    cellData = new Cell(new Chunk(String.valueOf(""),fontHeaderBigS));
                    cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(span1);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    appMainTemp = PstAppraisalMain.fetchExc(appraisal.getAppMainId());
                    //////////////////////////////////////////////////////////////////1
                    //1.1
                    cellData = new Cell(new Chunk(String.valueOf("INDIVIDUAL PERFORMANCE % "),fontHeaderBigS));
                    cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(12);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    
                    //1.2
                    cellData = new Cell(new Chunk(String.valueOf("(add ratings)"),fontHeaderSmall));
                    cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(3);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    
                    //1.3
                    String strVal = appMainTemp.getTotalScore()+" / "+appMainTemp.getTotalAssessment()+" = ";
                    cellData = new Cell(new Chunk(String.valueOf(strVal),fontHeaderBigS));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(7);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    
                    //1.4
                    cellData = new Cell(new Chunk(String.valueOf(appMainTemp.getScoreAverage()+" %"),fontHeaderBigS));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBorderWidth(2);
                //    cellData.setLeading(2);
                    cellData.setColspan(3);
                    cellData.setRowspan(2);
                    cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);  
                    table.addCell(cellData);
                    
                    //1.5
                    cellData = new Cell(new Chunk(String.valueOf("(average %)"),fontHeaderSmall));
                    cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(5);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    //////////////////////////////////////////////////////////////////END 1
                }catch(Exception ex){}
          }      
            //////////////////////////////////////////////////
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellType2ColNoText : "+ex.toString());}
        return table;
    }
    
     private static Table createCellSpace(Table table,AssessmentFormItem assessmentFormItem){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        //01
        try{
            //Row 1 Col 1
            for(int i=0;i<assessmentFormItem.getHeight();i++){
                cellData = new Cell(new Chunk(String.valueOf("\n"),fontHeader));
                cellData.setColspan(span1);
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                table.addCell(cellData);
            }
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellSpace : "+ex.toString());}
        return table;
    }
    
     private static Table createCellType1ColWithText(Table table,AssessmentFormItem assessmentFormItem, Appraisal appraisal){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int borderWidth = 1;
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        //01
        try{
            //Row 1 Col 1
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getNumber()+"."),fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span30);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            table.addCell(cellData);
            
            //Row 1 Col 2
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span1-(span30+span15));
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.TOP | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            table.addCell(cellData);
            
            //Row 1 Col 3
            cellData = new Cell(new Chunk(String.valueOf(((appraisal.getRating()>0)?appraisal.getRating():0)),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span15);
            cellData.setRowspan(2);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            table.addCell(cellData);
            
            //Row 2 Col 1
            cellData = new Cell(new Chunk(String.valueOf(""),fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span30);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            table.addCell(cellData);
            
            //Row 2 Col 2
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle_L2()),fontItalic));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span1-(span30+span15));
            cellData.setRowspan(1);
            cellData.setLeading(8);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
            table.addCell(cellData);
            
            int countNumber = 1;
              for(int i=0;i<6;i++){
                  String strPoin_1 = "";
                  String strPoin_2 = "";
                  
                  String strTempPoin = "";
                  switch(i){
                      case 0: 
                          strTempPoin = assessmentFormItem.getItemPoin1();
                          break;
                      case 1: 
                          strTempPoin = assessmentFormItem.getItemPoin2();
                          break;
                      case 2: 
                          strTempPoin = assessmentFormItem.getItemPoin3();
                          break;
                      case 3: 
                          strTempPoin = assessmentFormItem.getItemPoin4();
                          break;
                      case 4: 
                          strTempPoin = assessmentFormItem.getItemPoin5();
                          break;
                      case 5: 
                          strTempPoin = assessmentFormItem.getItemPoin6();
                          break;
                  }
                  int splitPos = strTempPoin.indexOf("(");
                  if(splitPos>-1){
                        strPoin_1 = strTempPoin.substring(0,splitPos);
                        strPoin_2 = strTempPoin.substring(splitPos,strTempPoin.length());
                  }else{
                        strPoin_1 = strTempPoin;
                  }
                  if(strPoin_1.length()>0 || !(strPoin_1.equals(""))){
                      //Row 2 Col 1
                    cellData = new Cell(new Chunk(String.valueOf(countNumber+"."),tableContentBox));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span30);
                    cellData.setRowspan(1);
                    cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                    table.addCell(cellData);
                    countNumber += 1;
                    //Row 2 Col 2
                    cellData = new Cell(new Chunk(String.valueOf(strPoin_1),tableContentBox));
                    cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span1-(span30));
                    cellData.setRowspan(1);
                    cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                    table.addCell(cellData);
                    
                    //Row 3 Col 2
                    cellData = new Cell(new Chunk(String.valueOf(""),tableContentBox));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span30);
                    cellData.setRowspan(1);
                    cellData.setLeading(8);
                    cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
                    table.addCell(cellData);
                    
                    //Row 3 Col 2
                    cellData = new Cell(new Chunk(String.valueOf(strPoin_2),fontItalicBottom));
                    cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                    cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBorderWidth(borderWidth);
                    cellData.setColspan(span1-(span30));
                    cellData.setRowspan(1);
                    cellData.setLeading(12);
                    cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.NO_BORDER);
                    table.addCell(cellData);
                 }
              }
            //Row 3 Col 2
            cellData = new Cell(new Chunk(String.valueOf(fitText( "",assessmentFormItem)),fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            cellData.setLeading(1);
            cellData.setBorder(Rectangle.LEFT | Rectangle.NO_BORDER | Rectangle.RIGHT | Rectangle.BOTTOM);
            table.addCell(cellData);
            
            //Untuk menampilkan- total nilai
          int maxNumber = SessAssessmentFormItem.getMaxNumber(assessmentFormItem.getAssFormSection());
          if(maxNumber == assessmentFormItem.getNumber()){
                AppraisalMain appMainTemp = new AppraisalMain();
                try{
                    cellData = new Cell(new Chunk(String.valueOf(""),fontHeaderBigS));
                    cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(span1);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    appMainTemp = PstAppraisalMain.fetchExc(appraisal.getAppMainId());
                    //////////////////////////////////////////////////////////////////1
                    //1.1
                    cellData = new Cell(new Chunk(String.valueOf("INDIVIDUAL PERFORMANCE % "),fontHeaderBigS));
                    cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(12);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    
                    //1.2
                    cellData = new Cell(new Chunk(String.valueOf("(add ratings)"),fontHeaderSmall));
                    cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(3);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    
                    //1.3
                    String strVal = appMainTemp.getTotalScore()+" / "+appMainTemp.getTotalAssessment()+" = ";
                    cellData = new Cell(new Chunk(String.valueOf(strVal),fontHeaderBigS));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(7);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    
                    //1.4
                    cellData = new Cell(new Chunk(String.valueOf(appMainTemp.getScoreAverage()+" %"),fontHeaderBigS));
                    cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setBorderColor(Color.BLACK);
                    cellData.setBorderWidth(2);
                //    cellData.setLeading(2);
                    cellData.setColspan(3);
                    cellData.setRowspan(2);
                    cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);  
                    table.addCell(cellData);
                    
                    //1.5
                    cellData = new Cell(new Chunk(String.valueOf("(average %)"),fontHeaderSmall));
                    cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                    cellData.setColspan(5);
                    cellData.setRowspan(2);
                    table.addCell(cellData);
                    //////////////////////////////////////////////////////////////////END 1
                }catch(Exception ex){}
          } 
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellType1ColWithText : "+ex.toString());}
        return table;
    }
    
     private static Table createCellTypeText(Table table,AssessmentFormItem assessmentFormItem){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        //01
        try{
            //Row 1 Col 1
            if(assessmentFormItem.getTitle().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1);
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            //Row 2 Col 1
            if(assessmentFormItem.getTitle_L2().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(fitText(assessmentFormItem.getTitle_L2(),assessmentFormItem)),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1);
                cellData.setRowspan(1);
                cellData.setLeading(10);
                table.addCell(cellData);
            }
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellTypeText : "+ex.toString());}
        return table;
    }
     
     private static Table createCellTypeTextBold(Table table,AssessmentFormItem assessmentFormItem){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        //01
        try{
            //Row 1 Col 1
            if(assessmentFormItem.getTitle().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1);
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            //Row 2 Col 1
            if(assessmentFormItem.getTitle_L2().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(fitText(assessmentFormItem.getTitle_L2(),assessmentFormItem)),fontItalic));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1);
                cellData.setRowspan(1);
                cellData.setLeading(10);
                table.addCell(cellData);
            }
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellTypeTextBold : "+ex.toString());}
        return table;
    }
    
     private static Table createCellTypeSelectHeader(Table table,AssessmentFormItem assessmentFormItem){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        //01
        try{
            //1.1
            if(assessmentFormItem.getTitle().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span2);
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            //1.2
            if(assessmentFormItem.getItemPoin1().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin1()),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span2);
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            
            if(assessmentFormItem.getTitle_L2().length()>0 && assessmentFormItem.getItemPoin2().length()>0){
            //2.1
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle_L2()),fontItalic));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span2);
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);
            //2.2
                cellData = new Cell(new Chunk(String.valueOf(fitText(assessmentFormItem.getItemPoin2(),assessmentFormItem)),fontItalic));
                cellData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span2);
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);
            }
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellTypeSelectHeader : "+ex.toString());}
        return table;
    }
    
     private static Table createCellTypeSelectA(Table table,AssessmentFormItem assessmentFormItem, Appraisal appraisal){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        
        //images
         /* image logo */
        Image logoBox = null;
        Image logoCheckBox = null;
        try{
            logoBox = Image.getInstance(imgBox);
            logoCheckBox = Image.getInstance(imgCheckBox);
        //    logoBox.setAlignment(Image.ALIGN_MIDDLE);
        //    logoCheckBox.setAlignment(Image.ALIGN_MIDDLE);
            logoBox.scalePercent(80);
            logoCheckBox.scalePercent(80);
        }catch(Exception ex){}
        
        //01
        try{
            //1.1
            cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
            cellData.add((appraisal.getEmpComment()!=null)?(appraisal.getEmpComment().equals("1")?logoCheckBox:logoBox):logoBox);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(2);
            table.addCell(cellData);
            //1.2
            if(assessmentFormItem.getTitle().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15));
                cellData.setRowspan(1);
                table.addCell(cellData);
            }else{
                cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15));
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            //1.3
            cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
            cellData.add((appraisal.getAssComment()!=null)?(appraisal.getAssComment().equals("1")?logoCheckBox:logoBox):logoBox);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(2);
            table.addCell(cellData);
            
            //2.1
         /*   cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setLeading(8);
            cellData.setRowspan(1);
            table.addCell(cellData); */
            
            //2.2
            if(assessmentFormItem.getTitle_L2().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle_L2()),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15));
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);
            }else{
                cellData = new Cell(new Chunk(String.valueOf(""),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15));
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);
            }
            
            //2.3
     /*       cellData = new Cell(new Chunk(String.valueOf(fitText("", assessmentFormItem)),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData); */
            
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellTypeSelectA : "+ex.toString());}
        return table;
    }
    
     private static Table createCellTypeSelectB(Table table,AssessmentFormItem assessmentFormItem, Appraisal appraisal){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        //images
         /* image logo */
        Image logoBox = null;
        Image logoCheckBox = null;
        try{
            logoBox = Image.getInstance(imgBox);
            logoCheckBox = Image.getInstance(imgCheckBox);
            
        //    logoBox.setAlignment(Image.ALIGN_MIDDLE);
        //    logoCheckBox.setAlignment(Image.ALIGN_MIDDLE);
            logoBox.scalePercent(80);
            logoCheckBox.scalePercent(80);
        }catch(Exception ex){}
        
        //01
        try{
            //1.1
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.add((appraisal.getEmpComment()!=null)?(appraisal.getEmpComment().equals("1")?logoCheckBox:logoBox):logoBox);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(2);
            table.addCell(cellData);
            //1.2
            if(assessmentFormItem.getTitle().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15+span3));
                cellData.setRowspan(1);
                table.addCell(cellData);
            }else{
                cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15+span3));
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            //1.3
            if(assessmentFormItem.getItemPoin1().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin1()),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span3);
                cellData.setRowspan(1);
                table.addCell(cellData);
            }else{
                cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span3);
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            //1.4
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.add((appraisal.getAssComment()!=null)?(appraisal.getAssComment().equals("1")?logoCheckBox:logoBox):logoBox);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(2);
            table.addCell(cellData); 
            
            //2.1
     /*       cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData); */
            
            //2.2
            if(assessmentFormItem.getTitle_L2().length()>0){
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle_L2()),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15));
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);
            }else{
                cellData = new Cell(new Chunk(String.valueOf(""),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15));
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);
            }
            
            //2.3
    /*        cellData = new Cell(new Chunk(String.valueOf(fitText("", assessmentFormItem)),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData); */
            
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellTypeSelectB : "+ex.toString());}
        return table;
    }
         
     private static Table createCellTypeInput(Table table,AssessmentFormItem assessmentFormItem, Appraisal appraisal){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        //01
        try{
            //1.1
            cellData = new Cell(new Chunk(String.valueOf(".::"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span30);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            
            //1.2
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span2-(span30+span30));
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //1.3
            cellData = new Cell(new Chunk(String.valueOf(":"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span30);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //1.4
            cellData = new Cell(new Chunk(String.valueOf(((appraisal.getAssComment()!=null)?appraisal.getAssComment():"")),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span2);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            if(assessmentFormItem.getTitle_L2()!=null){
                 //2.1
                cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span30);
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);


                //2.2
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle_L2()),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span2-(span30+span30));
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);

                //2.3
                cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span30);
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);

                //2.4
                cellData = new Cell(new Chunk(String.valueOf(fitText("", assessmentFormItem)),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span2);
                cellData.setRowspan(1);
                cellData.setLeading(8);
                table.addCell(cellData);
            }
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellTypeInput : "+ex.toString());}
        return table;
    }
    
     private static Table createCellTypeInputCheckHeader(Table table,AssessmentFormItem assessmentFormItem){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        //01
        try{
            //1.1
            cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span1-(span15+span15+span3));
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            
            //1.2
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //1.3
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin1()),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //1.4
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            if(assessmentFormItem.getTitle_L2().length()>0 && assessmentFormItem.getItemPoin2().length()>0){
                //2.1
                cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15+span3));
                cellData.setLeading(8);
                cellData.setRowspan(1);
                table.addCell(cellData);
                //2.2
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle_L2()),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span15);
                cellData.setLeading(8);
                cellData.setRowspan(1);
                table.addCell(cellData);
                //2.3
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getItemPoin2()),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span15);
                cellData.setLeading(8);
                cellData.setRowspan(1);
                table.addCell(cellData);
                
                //2.4
                cellData = new Cell(new Chunk(String.valueOf(fitText("", assessmentFormItem)),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span3);
                
                cellData.setLeading(8);
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellTypeInput : "+ex.toString());}
        return table;
    }
    
     private static Table createCellTypeInputCheck(Table table,AssessmentFormItem assessmentFormItem, Appraisal appraisal){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        //images
         /* image logo */
        Image logoBox = null;
        Image logoCheckBox = null;
        try{
            logoBox = Image.getInstance(imgBox);
            logoCheckBox = Image.getInstance(imgCheckBox);
      //      logoBox.setAlignment(Image.ALIGN_MIDDLE);
      //      logoCheckBox.setAlignment(Image.ALIGN_MIDDLE);
            logoBox.scalePercent(80);
            logoCheckBox.scalePercent(80);
        }catch(Exception ex){}
        
        //01
        try{
            //1.1
            cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle()),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span1-(span15+span15+span3));
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //1.2
            cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
            cellData.add((appraisal.getAssComment()!=null)?(appraisal.getAssComment().equals("1")?logoCheckBox:logoBox):logoBox);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(2);
            table.addCell(cellData);
                        
            //1.3
            cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
            cellData.add((appraisal.getAssComment()!=null)?(appraisal.getAssComment().equals("1")?logoBox:logoCheckBox):logoCheckBox);
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span15);
            cellData.setRowspan(2);
            table.addCell(cellData);
            
            //1.4
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            if(assessmentFormItem.getTitle_L2().length()>0){
                //2.1
                cellData = new Cell(new Chunk(String.valueOf(assessmentFormItem.getTitle_L2()),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span1-(span15+span15+span3));
                cellData.setLeading(8);
                cellData.setRowspan(1);
                table.addCell(cellData);
                //2.2
                cellData = new Cell(new Chunk(String.valueOf(fitText("", assessmentFormItem)),fontItalicBottom));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(span3);
                cellData.setLeading(8);
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellTypeInput : "+ex.toString());}
        return table;
    }
    
     private static Table createCellType2ColComm(Table table,AssessmentFormItem assessmentFormItem, Appraisal appraisal){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        
        int borderWidth = 1;
        //01
        try{
            
            table = createCellType2ColHeader(table, assessmentFormItem);
            
            //1.1
            cellData = new Cell(new Chunk(String.valueOf(appraisal.getEmpComment()!=null?appraisal.getEmpComment():""),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span2);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            table.addCell(cellData);
            
             //1.2
            cellData = new Cell(new Chunk(String.valueOf(fitText(appraisal.getAssComment()!=null?appraisal.getAssComment():"",assessmentFormItem)),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span2);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            table.addCell(cellData);
            
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellType2ColComm : "+ex.toString());}
        return table;
    }
    
     private static Table createCellType1ColCommEmp(Table table,AssessmentFormItem assessmentFormItem, Appraisal appraisal){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        
        int borderWidth = 1;
        //01
        try{
            
            table = createCellTypeTextBold(table, assessmentFormItem);
            
            //1.1
            cellData = new Cell(new Chunk(String.valueOf(fitText(appraisal.getEmpComment()!=null?appraisal.getEmpComment():"",assessmentFormItem)),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
        //    cellData.setBorderColor(Color.BLACK);
        //    cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            table.addCell(cellData);
            
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellType1ColCommEmp : "+ex.toString());}
        return table;
    }
     
     private static Table createCellType1ColCommAss(Table table,AssessmentFormItem assessmentFormItem, Appraisal appraisal){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        
        int borderWidth = 1;
        //01
        try{
            
            table = createCellTypeTextBold(table, assessmentFormItem);
            
            //1.1
            cellData = new Cell(new Chunk(String.valueOf(fitText(appraisal.getAssComment()!=null?appraisal.getAssComment():"",assessmentFormItem)),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            //cellData.setBorderColor(Color.BLACK);
            //cellData.setBorderWidth(borderWidth);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER | Rectangle.NO_BORDER);
            table.addCell(cellData);
            
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellType1ColCommAss : "+ex.toString());}
        return table;
    }
    
     private static Table createCellTypeApproval( AppraisalMain appraisalMain){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        
        String strPathEmp = "";
        String strPathAss = "";
        String strPathDiv = ""; 
        ImageAssign imgEmp = PstImageAssign.getImageAssignByEmp(appraisalMain.getEmployeeId());
        ImageAssign imgAss = PstImageAssign.getImageAssignByEmp(appraisalMain.getAssesorId());
        ImageAssign imgDiv = PstImageAssign.getImageAssignByEmp(appraisalMain.getDivisionHeadId());
        String pathImgAssign = imagesRoot+"imgassign/";
        if(imgEmp.getPath()!=null && imgEmp.getPath().length()>0){
            strPathEmp = pathImgAssign+imgEmp.getPath();
        }
        if(imgAss.getPath()!=null && imgAss.getPath().length()>0){
            strPathAss =pathImgAssign+imgAss.getPath();
        }
        if(imgDiv.getPath()!=null && imgDiv.getPath().length()>0){
            strPathDiv = pathImgAssign+imgDiv.getPath();
        }
        
        int borderWidth = 1;
        Table table = null; 
        try
        {                                    
            int maxColumn = 30;                        
            float headerwidths[] = new float[maxColumn];
            for(int i=0;i< maxColumn;i++){
                headerwidths[i] = 100/30;
            }
            
            // create table
            table = new Table(maxColumn);
            table.setPadding(1);
            table.setSpacing(0); //1
            table.setWidths(headerwidths);
            table.setWidth(100);
       //     datatable.setDefaultRowspan(1);
            table.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            table.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
        //    datatable.setBackgroundColor(bgColor);
           // datatable.setBorderColor(border);
            table.setDefaultCellBorder(Cell.NO_BORDER);
            table.setBorder(Table.NO_BORDER);
            
            Employee employee = new Employee();
            Employee assessor = new Employee();
            Employee divHead = new Employee();
            if(appraisalMain.getOID()>0){
                try{
                    employee = PstEmployee.fetchExc(appraisalMain.getEmployeeId());
                    assessor = PstEmployee.fetchExc(appraisalMain.getAssesorId());
                    divHead = PstEmployee.fetchExc(appraisalMain.getDivisionHeadId());
                }catch(Exception ex){}
            }
                
            //0.1
            cellData = new Cell(new Chunk(String.valueOf("\n\n"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //1.1
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span1-span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //1.2
            cellData = new Cell(new Chunk(String.valueOf("Noted By:"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //2.1
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span1-span3);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            //2.2
            cellData = new Cell(new Chunk(String.valueOf("(Dicatat oleh)"),fontItalic));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            //3.1
            cellData = new Cell(new Chunk(String.valueOf("\n\n"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //4.1
            cellData = new Cell(new Chunk(String.valueOf(employee.getFullName()),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //4.2
            cellData = new Cell(new Chunk(String.valueOf(assessor.getFullName()),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //4.3
            cellData = new Cell(new Chunk(String.valueOf(divHead.getFullName()),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //5.1
            cellData = new Cell(new Chunk(String.valueOf("Signature"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //5.2
            cellData = new Cell(new Chunk(String.valueOf("Signature"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //5.3
            cellData = new Cell(new Chunk(String.valueOf("Signature"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //6.1
            cellData = new Cell(new Chunk(String.valueOf("Band Member"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setLeading(8);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //6.2
            cellData = new Cell(new Chunk(String.valueOf("Assessor"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            //6.3
            cellData = new Cell(new Chunk(String.valueOf("*Division Head"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            //7.1
            cellData = new Cell(new Chunk(String.valueOf("(Band Member)"),fontItalic));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setLeading(8);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //7.2
            cellData = new Cell(new Chunk(String.valueOf("(Penilai)"),fontItalic));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            //7.3
            cellData = new Cell(new Chunk(String.valueOf("(Kepala Divisi)"),fontItalic));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            //images
             /* image logo */
           Image imgAsgEmp = null;
           Image imgAsgAss = null;
           Image imgAsgDiv = null;
            try{
                imgAsgEmp = Image.getInstance(strPathEmp);
                imgAsgEmp.setAlignment(Image.ALIGN_CENTER);
                imgAsgEmp.scaleAbsoluteHeight(80);
                imgAsgEmp.scaleAbsoluteWidth(120);
                //imgAsgEmp.scalePercent(50);
            }catch(Exception ex){}
            try{
                imgAsgAss = Image.getInstance(strPathAss);
                imgAsgAss.setAlignment(Image.ALIGN_CENTER);
                imgAsgAss.scaleAbsoluteHeight(80);
                imgAsgAss.scaleAbsoluteWidth(120);
                //imgAsgAss.scalePercent(50);
            }catch(Exception ex){}
            try{   
                imgAsgDiv = Image.getInstance(strPathDiv);
                imgAsgDiv.setAlignment(Image.ALIGN_CENTER);
                imgAsgDiv.scaleAbsoluteHeight(80);
                imgAsgDiv.scaleAbsoluteWidth(120);
                //imgAsgDiv.scalePercent(50);
            }catch(Exception ex){}
                       
           //Img 1
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            if(imgAsgEmp!=null){
                cellData.add(imgAsgEmp);
            }
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //Img 2
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            if(imgAsgAss!=null){
                cellData.add(imgAsgAss);
            }
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //Img 3
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            if(imgAsgDiv!=null){
                cellData.add(imgAsgDiv);
            }
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //8.1
            if(imgAsgEmp!=null&&imgAsgAss!=null&&imgAsgDiv!=null){
                cellData = new Cell(new Chunk(String.valueOf("\n\n"),fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
                cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
                cellData.setColspan(span1);
                cellData.setRowspan(1);
                table.addCell(cellData);
            }
            //9.1
            cellData = new Cell(new Chunk(String.valueOf(Formater.formatDate(appraisalMain.getEmployeeSignDate(), "dd MMMM yyyy")),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //9.2
            cellData = new Cell(new Chunk(String.valueOf(Formater.formatDate(appraisalMain.getAssessorSignDate(), "dd MMMM yyyy")),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //9.3
            cellData = new Cell(new Chunk(String.valueOf(Formater.formatDate(appraisalMain.getDivisionHeadSignDate(),"dd MMMM yyyy")),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //10.1
            cellData = new Cell(new Chunk(String.valueOf("Date (Tanggal)"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setLeading(8);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //10.2
            cellData = new Cell(new Chunk(String.valueOf("Date (Tanggal)"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            //10.3
            cellData = new Cell(new Chunk(String.valueOf("Date (Tanggal)"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            cellData = new Cell(new Chunk(String.valueOf(""),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
         ///   cellData.setLeading(8);
            table.addCell(cellData);
            
            cellData = new Cell(new Chunk(String.valueOf("*Note: If the assessor were the Division Head, the General Manager would sign the Note By corner."),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
         ///   cellData.setLeading(8);
            table.addCell(cellData);
            
            cellData = new Cell(new Chunk(String.valueOf("*Catatan: Jika penilai adalah Kepala Divisi, General Manager akan menandatangani do sebelah tanda tangan Division Head."),fontItalicBottom));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
        }catch(Exception ex){System.out.println("[ERROR] AppraisalDetailPdf.createCellType1ColCommAss : "+ex.toString());}
        return table;
    }
    
     
    ///////////////////////////////////////////////////
    
    private static String fitText(String strSource, AssessmentFormItem assessmentFormItem){
        String strLength = strSource;
        for(int i=1;i<assessmentFormItem.getHeight();i++){
           strLength += "\n";
        }
        return strLength;
    }
    
}
