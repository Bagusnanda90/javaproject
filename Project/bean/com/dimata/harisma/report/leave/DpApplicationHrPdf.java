/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.leave;

import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Formater;
import com.dimata.util.NumberSpeller;
import java.awt.Color;
import java.io.ByteArrayOutputStream;

import javax.servlet.*;
import javax.servlet.http.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;


import com.dimata.harisma.entity.employee.appraisal.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.leave.DpApplication;
import com.dimata.harisma.entity.leave.PstDpApplication;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.session.attendance.SessDayOfPayment;
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author artha
 */
public class DpApplicationHrPdf extends HttpServlet {
   
    /////////////////////////////////////////////////////////
    
            
    public static NumberSpeller numSpeller = new NumberSpeller();

    public static Color border = new Color(0x00, 0x00, 0x00);
    public static Color bgColor = new Color(240,240,240);
    public static Color blackColor = new Color(0,0,0);
    public static Color putih = new Color(250,250,250);

    // setting some fonts in the color chosen by the user
    public static Font fontHeaderBig2 = new Font(Font.HELVETICA, 15, Font.BOLD, border);
    public static Font fontHeaderBig = new Font(Font.HELVETICA, 13, Font.BOLD, border);
    public static Font fontHeaderBigS = new Font(Font.HELVETICA, 12, Font.BOLD, border);
    public static Font fontHeaderSmall = new Font(Font.HELVETICA, 9, Font.NORMAL, border);
    public static Font fontHeaderBoldSmall = new Font(Font.HELVETICA, 9, Font.BOLD, border);
    public static Font fontHeader = new Font(Font.HELVETICA, 12, Font.BOLD, border);
    public static Font fontContent = new Font(Font.HELVETICA, 12, Font.BOLD, border);
    public static Font tableContent = new Font(Font.HELVETICA, 12, Font.NORMAL, border);
    public static Font tableContentBox = new Font(Font.HELVETICA, 9, Font.NORMAL, border);
    public static Font fontSpellCharge = new Font(Font.HELVETICA, 11, Font.BOLDITALIC, border);
    public static Font fontItalicSmall = new Font(Font.HELVETICA, 9, Font.BOLDITALIC, border);
    public static Font fontItalic = new Font(Font.HELVETICA, 11, Font.BOLDITALIC, border);
    public static Font fontItalicBig = new Font(Font.HELVETICA, 13, Font.BOLDITALIC, border);
    public static Font fontItalicBottom = new Font(Font.HELVETICA, 11, Font.ITALIC, border);
    public static Font fontItalicBottomSmall = new Font(Font.HELVETICA, 9, Font.ITALIC, border);
    public static Font fontUnderline = new  Font(Font.HELVETICA, 11, Font.UNDERLINE, border);
    
    public static String imagesRoot = PstSystemProperty.getValueByName("IMG_ROOT");
    public static String imgLogo = imagesRoot+"logo.jpg"; 
    public static String imgBox = imagesRoot+"icon/box.jpg"; 
    public static String imgCheckBox = imagesRoot+"icon/check_box.jpg"; 
        
    /////////////////////////////////////////////////////////
    
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
        Document document = new Document(PageSize.A4, 60, 50, 40, 40);
        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        long oidDpApp = FRMQueryString.requestLong(request, "oidDpApplication");
        
        
        DpApplication dpApp = new DpApplication();
        try{
            //Disini mengambil data
            dpApp = PstDpApplication.fetchExc(oidDpApp);
        }catch(Exception exception){
           
        }
        
        int start = 0;
        
        /**
         * Data Header
         */
        String[] aTitle = {
                "Extra Off Day Request",
                "(Level C and Above)"
        };
        
        //Setting Footer
        String strFooterText = "Hard Rock Hotel - Bali | ";
        boolean isUsePageFooter = false;
        String strFooterDateFormat = "MMMM dd, yyyy";
        
        boolean isUseLogo = true;
        
        try{
             // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);

            // step 3.1: adding some metadata to the document
            document.addSubject("This is a subject.");
            document.addSubject("This is a subject two."); 
            
            // FOOTER
            HeaderFooter footer = new HeaderFooter(new Phrase(strFooterText+Formater.formatDate(dpApp.getSubmissionDate(), strFooterDateFormat), fontHeaderSmall),isUsePageFooter);
            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
            document.setFooter(footer);
                        
            // step 3.4: opening the document
            document.open();
            
            //INFORMATION
            //Header
            Table tableHeader = createHeader(aTitle,isUseLogo);
            document.add(tableHeader);
            
            //Body
            Table tableBody = createBody(dpApp);
            document.add(tableBody);
            
            //Approval
            Table tableApproval = createApproval(dpApp);
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
    public Table createHeader(String[] aTitle, boolean isUseLogo)
    {        
        Table datatable = null;  
        Cell cellData = new Cell("");
        try
        {                        
            int maxColumn = 2;                        
            float headerwidths[] = new float[maxColumn];
          //  for(int i=0;i<maxColumn;i++){
                headerwidths[0] = 100/maxColumn-10;
                headerwidths[0] = 100/maxColumn+10;
          //  }
            
            //images
             /* image logo */
           Image logoImg = null;
            try{
                logoImg = Image.getInstance(imgLogo);
               // logoImg.setAlignment(Image.ALIGN_CENTER);
            }catch(Exception ex){}
            
            
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(1); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setBorder(Table.NO_BORDER);
            datatable.setDefaultCellBorder(Cell.NO_BORDER);
            
            
            // Menambahkan logo
            if(isUseLogo){
                cellData = new Cell();
              //  cellData.add(logoImg);
                //cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                //cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                cellData.setColspan(maxColumn);
                datatable.addCell(cellData);
            }
            
            //Title
            for(int i=0;i<aTitle.length;i++){
                cellData = new Cell(new Phrase(aTitle[i],fontHeaderBig2));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                cellData.setLeading(12);
                cellData.setColspan(maxColumn);
                datatable.addCell(cellData);
            }
            // this is the end of the table header
          //  datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on DpApplicationHrPdf.createHeader : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * Detail Information
     */    
    public Table createBody(DpApplication dpApplication)
    {        
        Table datatable = null; 
        Cell cellData = new Cell("");
        
        //Date formate
        String strDateFormat = "MMMM dd,yyyy";
        
        Employee employee = new Employee();
        Position position = new Position();
        Department department = new Department();
        DpStockManagement dpMan = new DpStockManagement();
        
        try{
            employee = PstEmployee.fetchExc(dpApplication.getEmployeeId());
            position = PstPosition.fetchExc(employee.getPositionId());
            department = PstDepartment.fetchExc(employee.getDepartmentId());
            dpMan = PstDpStockManagement.fetchExc(dpApplication.getDpId());
        }catch(Exception ex){}
        
        try
        {                        
            int maxColumn = 8;                        
            float headerwidths[] = new float[maxColumn];
            for(int i=0;i<maxColumn;i++){
                headerwidths[i] = 100/maxColumn;
            }
            
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(0); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            datatable.setDefaultCellBorder(Cell.NO_BORDER);
            datatable.setBorder(Table.NO_BORDER);
            
            //Table detail
            //Space 1,1
            cellData = new Cell(new Phrase("\n",fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(maxColumn);
            //cellData.setLeading(1);
            datatable.addCell(cellData);
            
            /*DATA EMPLOYEE*/
            //Name 2,1
            cellData = new Cell(new Phrase("Name",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(2);
            datatable.addCell(cellData);
            //Value 2,2
            cellData = new Cell(new Phrase(": "+employee.getFullName(),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(maxColumn-2);
            datatable.addCell(cellData);
            
            //Position 3,1
            cellData = new Cell(new Phrase("Position",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(2);
            datatable.addCell(cellData);
            //Value 3,2
            cellData = new Cell(new Phrase(": "+position.getPosition(),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(maxColumn-2);
            datatable.addCell(cellData);
            
            //Department 4,1
            cellData = new Cell(new Phrase("Department",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(2);
            datatable.addCell(cellData);
            //Value 4,2
            cellData = new Cell(new Phrase(": "+department.getDepartment(),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(maxColumn-2);
            datatable.addCell(cellData);
            
            //Text 4,2
            String strDp = "\n\nI would like to take my Day off on "+Formater.formatDate(dpApplication.getTakenDate(), strDateFormat)
                    +" in replace of "+Formater.formatDate(dpMan.getDtOwningDate(), strDateFormat)
                    +" as per approved extra workday request form attached.\n\nThank you.";
            cellData = new Cell(new Phrase(strDp,tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(maxColumn);
            datatable.addCell(cellData);
            
            // this is the end of the table header
         //   datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on DpApplicationHrPdf.createBody : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * Detail Approval
     */  
     private Table createApproval(DpApplication dpApplication){
       // Vector vCell = new Vector(1,1);
        Cell cellData = new Cell("");
        int span1 = 30;
        int span2 = 15;
        int span3 = 10;
        int span10 = 3;
        int span15 = 2;
        int span30 = 1;
        
        //Balance Dp
        //int balance = 0;
        //balance = SessDayOfPayment.getDpBalance(dpApplication.getEmployeeId());
        //if(!(balance>0)){balance=0;}
        
        String strPathEmp = "";
        String strPathAss = "";
        ImageAssign imgEmp = PstImageAssign.getImageAssignByEmp(dpApplication.getEmployeeId());
        ImageAssign imgAss = PstImageAssign.getImageAssignByEmp(dpApplication.getApprovalId());
        String pathImgAssign = imagesRoot+"imgassign/";
        if(imgEmp.getPath()!=null && imgEmp.getPath().length()>0){
            strPathEmp = pathImgAssign+imgEmp.getPath();
        }
        if(imgAss.getPath()!=null && imgAss.getPath().length()>0){
            strPathAss =pathImgAssign+imgAss.getPath();
        }
        
        int borderWidth = 1;
        Table table = null; 
        try
        {                                    
            int maxColumn = 30;                        
            float headerwidths[] = new float[maxColumn];
            for(int i=0;i< maxColumn;i++){
                headerwidths[i] = 100/maxColumn;
            }
            
            // create table
            table = new Table(maxColumn);
            table.setPadding(1);
            table.setSpacing(0); //1
            table.setWidths(headerwidths);
            table.setWidth(100);
            table.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            table.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            table.setDefaultCellBorder(Cell.NO_BORDER);
            table.setBorder(Table.NO_BORDER);
            
            Employee employee = new Employee();
            Employee assessor = new Employee();
            if(dpApplication.getOID()>0){
                try{
                    employee = PstEmployee.fetchExc(dpApplication.getEmployeeId());
                    assessor = PstEmployee.fetchExc(dpApplication.getApprovalId());
                }catch(Exception ex){}
            }
                
            //Space 1.1
            cellData = new Cell(new Chunk(String.valueOf("\n\n"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //Title 2.1
            cellData = new Cell(new Chunk(String.valueOf("Request by,"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span1-span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //Title 2.2
            cellData = new Cell(new Chunk(String.valueOf("Approved by,"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData); 
            
            //images
             /* image logo */
           Image imgAsgEmp = null;
           Image imgAsgAss = null;
            try{
                imgAsgEmp = Image.getInstance(strPathEmp);
                imgAsgEmp.setAlignment(Image.ALIGN_LEFT);
                imgAsgEmp.scaleAbsoluteHeight(80);
                imgAsgEmp.scaleAbsoluteWidth(120);
                //imgAsgEmp.scalePercent(50);
            }catch(Exception ex){}
            try{                
                imgAsgAss = Image.getInstance(strPathAss);
                imgAsgAss.setAlignment(Image.ALIGN_LEFT);
                imgAsgAss.scaleAbsoluteHeight(80);
                imgAsgAss.scaleAbsoluteWidth(120);
                //imgAsgAss.scalePercent(50);
            }catch(Exception ex){}
                       
           //Img Appraisal 1
            if(imgAsgEmp!=null){
                cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
                cellData.add(imgAsgEmp);
            }else{
                 //Space
                cellData = new Cell(new Chunk(String.valueOf("\n\n\n"),tableContent));
            }
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span1-span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //Img Appraisal 2
            if(imgAsgEmp!=null){
                cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
                cellData.add(imgAsgAss);
            }else{
                //Space
                cellData = new Cell(new Chunk(String.valueOf("\n\n\n"),tableContent));
            }
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
           
            //Employee name
            cellData = new Cell(new Chunk(String.valueOf("("+employee.getFullName()+")"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span1-span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //Approved name
            cellData = new Cell(new Chunk(String.valueOf("("+assessor.getFullName()+")"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(span3);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //Space
            cellData = new Cell(new Chunk(String.valueOf("\n\n"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //Previous Balance 3.1
            cellData = new Cell(new Chunk(String.valueOf("Previous Balance : "+dpApplication.getBalance()),fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span2);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //New Balance 3.2
            cellData = new Cell(new Chunk(String.valueOf("New Balance : "+(dpApplication.getNewBalance())),fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span2);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //Space 4,1
            cellData = new Cell(new Chunk(String.valueOf("\n\n"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
           /**
            * NOTE
            */
            //Title note 5,1
            cellData = new Cell(new Chunk(String.valueOf("Note : "),fontHeader));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //note 1 6,1
            cellData = new Cell(new Chunk(String.valueOf("Level C & B approval will be given by Dept. Head or Division Head concern"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            //note 1 7,1
            cellData = new Cell(new Chunk(String.valueOf("Level A & Executive Committee Members will be approved by General Manager"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
                        
            //Space 8,1
            cellData = new Cell(new Chunk(String.valueOf("\n"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_JUSTIFIED);
            cellData.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cellData.setColspan(span1);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //note 1 9,1
            cellData = new Cell(new Chunk(String.valueOf("CC : "),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span10);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //note 1 9,1
            cellData = new Cell(new Chunk(String.valueOf("Department Head/Division Head"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span1-span10);
            cellData.setRowspan(1);
            table.addCell(cellData);
            
            //note 1 10,1
            cellData = new Cell(new Chunk(String.valueOf(""),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span10);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
            //note 1 10,1
            cellData = new Cell(new Chunk(String.valueOf("Human Resources"),tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
            cellData.setColspan(span1-span10);
            cellData.setRowspan(1);
            cellData.setLeading(8);
            table.addCell(cellData);
            
        }catch(Exception ex){System.out.println("[ERROR] DpApplicationHrPdf.createApproval : "+ex.toString());}
        return table;
    }
    
     
    ///////////////////////////////////////////////////
    
}
