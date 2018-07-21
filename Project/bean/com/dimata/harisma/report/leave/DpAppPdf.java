/*
 * DpApplicationPdf.java
 *
 * Created on January 12, 2005, 12:02 PM
 */

package com.dimata.harisma.report.leave;

// package java
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.leave.dp.DpAppDetail;
import com.dimata.harisma.entity.leave.dp.DpAppMain;
import com.dimata.harisma.entity.leave.dp.PstDpAppMain;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
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

/**
 *
 * @author  gedhy
 */
public class DpAppPdf extends HttpServlet {
    
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
    public static Font fontContent = new Font(Font.HELVETICA, 11, Font.BOLD, border);
    public static Font tableContent = new Font(Font.HELVETICA, 11, Font.NORMAL, border);
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

    /** Initializes the servlet
    */
    public void init(ServletConfig config) throws ServletException {  
        super.init(config);
    }

    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Destroys the servlet
    */
    public void destroy() {
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {


        // creating the document object
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);

        // creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // get data from session
        Vector vListDpApp = new Vector(1,1);
        DpAppMain dpAppMain = new DpAppMain();
        long oidDpAppMain = FRMQueryString.requestLong(request, "oidDpAppMain");
        try
        {
            vListDpApp = SessDpAppDetail.listDpAppDetail(oidDpAppMain);
            dpAppMain = PstDpAppMain.fetchExc(oidDpAppMain);
        }
        catch(Exception e)
        {
            System.out.println("Exc : "+e.toString());
        }
        
        //First Config
        String[] aTitle = {
                "DAY PAYMENT (DP) REQUEST"
        };

        //Setting Footer
        String strFooterText = "Hard Rock Hotel - Bali | ";
        boolean isUsePageFooter = false;
        String strFooterDateFormat = "MMMM dd, yyyy";

        boolean isUseLogo = true;
        
        try {

            // creating an instance of the writer
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            
            // step 3.1: adding some metadata to the document
            document.addSubject("This is a subject.");
            document.addSubject("This is a subject two."); 
            
            // FOOTER
            HeaderFooter footer = new HeaderFooter(new Phrase(strFooterText+Formater.formatDate(new Date(), strFooterDateFormat), fontHeaderSmall),isUsePageFooter);
            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
            //document.setFooter(footer);
            
            // step 3.4: opening the document
            document.open();
            
            //INFORMATION
            //Header
            Table tableHeader = createHeader(aTitle,isUseLogo);
            document.add(tableHeader);
            
            //Emp Date
            Table tableEmpDetail = createEmpId(dpAppMain);
            createDetail(dpAppMain, vListDpApp, tableEmpDetail);
            createApproval(dpAppMain, tableEmpDetail);
            document.add(tableEmpDetail);
            
           //Menampilkan Report
            
            
           //End -- Menampilkan Report
            
        }
        catch(DocumentException de) 
        {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }

        // closing the document 
        document.close();

        // we have written the pdfstream to a ByteArrayOutputStream, now going to write this outputStream to the ServletOutputStream
	// after we have set the contentlength        
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
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
            System.out.println("Exception on DpAppPdf.createHeader : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * Employee Information
     */    
    public Table createEmpId(DpAppMain dpAppMain)
    {        
        ///////////////////////////////////////
        // get employee and deparment data
        String strEmpFullName = "";
        String strEmpDepartment = "";
        String strEmpPosition = "";
        String strEmpDivision = "";
        Employee objEmployee = new Employee();

        try
        {
                objEmployee = PstEmployee.fetchExc(dpAppMain.getEmployeeId());

                // get fullname
                strEmpFullName = objEmployee.getFullName();

                // get deparment
                Department objDepartment = new Department();
                objDepartment = PstDepartment.fetchExc(objEmployee.getDepartmentId());
                strEmpDepartment = objDepartment.getDepartment();

                //get position
                Position objPosition = new Position();
                objPosition = PstPosition.fetchExc(objEmployee.getPositionId());
                strEmpPosition = objPosition.getPosition();

                //get division
                Division objDivision = new Division();
                objDivision = PstDivision.fetchExc(objEmployee.getDivisionId());
                strEmpDivision = objDivision.getDivision();
        }
        catch(Exception e)
        {
                System.out.println("Exc when fetch employee and deparment data : " + e.toString());
        }
        ///////////////////////////////////////
        
        Table datatable = null;  
        Cell cellData = new Cell("");
        try
        {                        
            int maxColumn = 100;                        
            float headerwidths[] = new float[maxColumn];
            for(int i=0;i<maxColumn;i++){
                headerwidths[i] = 100/maxColumn;
            }
                        
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(1); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            //datatable.setBorder(Table.NO_BORDER);
            datatable.setDefaultCellBorder(Cell.NO_BORDER);
            
            //1.1 Employee Name
            cellData = new Cell(new Chunk("Name",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(15);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //1.2 Employee Name
            cellData = new Cell(new Chunk(":",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(2);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //1.3 Employee Name
            cellData = new Cell(new Chunk(strEmpFullName,fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(83);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //2.1 Employee Position
            cellData = new Cell(new Chunk("Position",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(15);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //2.2 Employee Position
            cellData = new Cell(new Chunk(":",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(2);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //2.3 Employee Position
            cellData = new Cell(new Chunk(strEmpPosition,fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(33);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //2.4 Employee Payroll
            cellData = new Cell(new Chunk("Payroll",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(15);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //2.5 Employee Payroll
            cellData = new Cell(new Chunk(":",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(2);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //2.6 Employee Payroll
            cellData = new Cell(new Chunk(objEmployee.getEmployeeNum(),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(33);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //3.1 Employee Position
            cellData = new Cell(new Chunk("Division",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(15);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //3.2 Employee Position
            cellData = new Cell(new Chunk(":",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(2);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //3.3 Employee Position
            cellData = new Cell(new Chunk(strEmpDivision,fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(33);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //3.4 Employee Payroll
            cellData = new Cell(new Chunk("Department",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(15);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //3.5 Employee Payroll
            cellData = new Cell(new Chunk(":",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(2);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //3.6 Employee Payroll
            cellData = new Cell(new Chunk(strEmpDepartment,fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(33);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //4.1 Employee Position
            cellData = new Cell(new Chunk("Commencing Date",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(15);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //4.2 Employee Position
            cellData = new Cell(new Chunk(":",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(2);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //4.3 Employee Position
            cellData = new Cell(new Chunk(Formater.formatDate(objEmployee.getCommencingDate(), "MMMM dd, yyyy"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(33);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //4.4 Employee Payroll
            cellData = new Cell(new Chunk("Date of Request",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(15);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //4.5 Employee Payroll
            cellData = new Cell(new Chunk(":",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(2);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            //4.6 Employee Payroll
            cellData = new Cell(new Chunk(Formater.formatDate(dpAppMain.getSubmissionDate(), "MMMM dd, yyyy"),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(33);
            cellData.setRowspan(1);
            datatable.addCell(cellData);
            
            // this is the end of the table header
          //  datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on DpAppPdf.createEmpId : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * Header Information
     */    
    public void createDetail(DpAppMain dpAppMain ,Vector vDpDetail, Table table)
    {        
        Table datatable = table;  
        Cell cellData = new Cell("");
        int borderWidth = 1;
        try
        {                        
          //1.1
            cellData = new Cell(new Chunk("No. of days eligible",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(20);
            cellData.setRowspan(2);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          //1.2
            cellData = new Cell(new Chunk("No. of days requested",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(20);
            cellData.setRowspan(2);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          //1.3
            cellData = new Cell(new Chunk("Date",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(60);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          //2.1
            cellData = new Cell(new Chunk("From",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(30);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          //2.2
            cellData = new Cell(new Chunk("To",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(30);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          //3.1
            cellData = new Cell(new Chunk(String.valueOf(dpAppMain.getBalance()),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(20);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          //3.2
            cellData = new Cell(new Chunk(String.valueOf(vDpDetail.size()),fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(20);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
            //Membuat string from and to taken dp
            Vector vDpTaken = new Vector(1,1);
            vDpTaken = createTakenDate(vDpDetail);
            String strFrom = (String)vDpTaken.get(0);
            String strTo = (String)vDpTaken.get(1);
            
          //3.3
            cellData = new Cell(new Chunk(strFrom,fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(30);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          //3.4
            cellData = new Cell(new Chunk(strTo,fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(30);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
            
        }
        catch(Exception e)
        {
            System.out.println("Exception on DpAppPdf.createDetail : "+e.toString());
        }
        //return datatable;
    }
    
       /** 
     * Header Information
     */    
    public void createApproval(DpAppMain dpAppMain, Table table)
    {        
        Table datatable = table;  
        Cell cellData = new Cell("");
        int borderWidth = 1;
        try
        {                        
            String strEmp = "";
            String strApp1 = "";
            String strApp2 = "";
            String strApp3 = "";
            
            String strDtEmp = "";
            String strDtApp1 = "";
            String strDtApp2 = "";
            String strDtApp3 = "";
            int maxApporval = 0;
            try{
                Employee emp = new Employee();
                Employee app1 = new Employee();
                Employee app2 = new Employee();
                Employee app3 = new Employee();
                
                emp = PstEmployee.fetchExc(dpAppMain.getEmployeeId());
                app1 = PstEmployee.fetchExc(dpAppMain.getApprovalId());
                app2 = PstEmployee.fetchExc(dpAppMain.getApproval2Id());
                app3 = PstEmployee.fetchExc(dpAppMain.getApproval3Id());
                
                strEmp = emp.getFullName();
                strApp1 = app1.getFullName();
                strApp2 = app2.getFullName();
                strApp3 = app3.getFullName();
                
                strDtEmp = Formater.formatDate(dpAppMain.getSubmissionDate(), "MMMM dd, yyyy");
                strDtApp1 = Formater.formatDate(dpAppMain.getApprovalDate(), "MMMM dd, yyyy");
                strDtApp2 = Formater.formatDate(dpAppMain.getApproval2Date(), "MMMM dd, yyyy");
                strDtApp3 = Formater.formatDate(dpAppMain.getApproval3Date(), "MMMM dd, yyyy");
                
                I_Leave leaveConfig = null;           
                try {
                    leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
                }catch(Exception e) {
                    System.out.println("Exception : " + e.getMessage());
                }

                maxApporval = leaveConfig.getMaxApproval(dpAppMain.getEmployeeId());
                
            }catch(Exception ex){}
            
          //1.1
            cellData = new Cell(new Chunk("Approval",fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(25);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.NO_BORDER | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          //1.2
            cellData = new Cell(new Chunk("Signature",fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(45);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.TOP | Rectangle.NO_BORDER | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          //1.3
            cellData = new Cell(new Chunk("Date",fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(30);
            cellData.setRowspan(1);
            cellData.setBorderColor(Color.BLACK);
            cellData.setBackgroundColor(putih);
            cellData.setBorderWidth(borderWidth);
            cellData.setBorder(Rectangle.NO_BORDER | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            datatable.addCell(cellData);
            
          ////////////DATA
             //2.1
            cellData = new Cell(new Chunk("Employee",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(25);
            cellData.setRowspan(1);datatable.addCell(cellData);
             //2.2
            cellData = new Cell(new Chunk(":",tableContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(2);
            cellData.setRowspan(1);datatable.addCell(cellData);
             //2.3
            cellData = new Cell(new Chunk(strEmp,fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(43);
            cellData.setRowspan(1);datatable.addCell(cellData);
             //2.4
            cellData = new Cell(new Chunk(strDtEmp,fontContent));
            cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellData.setVerticalAlignment(Cell.ALIGN_TOP);
            cellData.setColspan(30);
            cellData.setRowspan(1);datatable.addCell(cellData);
            
            if(maxApporval>=1){
                 //3.1
                cellData = new Cell(new Chunk("Div./Department Head",tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(25);
                cellData.setRowspan(1);datatable.addCell(cellData);
                 //3.2
                cellData = new Cell(new Chunk(":",tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(2);
                cellData.setRowspan(1);datatable.addCell(cellData);
                 //3.3
                cellData = new Cell(new Chunk(strApp1,fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(43);
                cellData.setRowspan(1);datatable.addCell(cellData);
                 //3.4
                cellData = new Cell(new Chunk(strDtApp1,fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(30);
                cellData.setRowspan(1);datatable.addCell(cellData);
            }
            
            if(maxApporval>=2){
                 //4.1
                cellData = new Cell(new Chunk("Executive Producer",tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(25);
                cellData.setRowspan(1);datatable.addCell(cellData);
                 //4.2
                cellData = new Cell(new Chunk(":",tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(2);
                cellData.setRowspan(1);datatable.addCell(cellData);
                 //4.3
                cellData = new Cell(new Chunk(strApp2,fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(43);
                cellData.setRowspan(1);datatable.addCell(cellData);
                 //4.4
                cellData = new Cell(new Chunk(strDtApp2,fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(30);
                cellData.setRowspan(1);datatable.addCell(cellData);
            }
            if(maxApporval>=3){
                 //5.1
                cellData = new Cell(new Chunk("Producer - Talent",tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(25);
                cellData.setRowspan(1);datatable.addCell(cellData);
                 //5.2
                cellData = new Cell(new Chunk(":",tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(2);
                cellData.setRowspan(1);datatable.addCell(cellData);
                 //5.3
                cellData = new Cell(new Chunk(strApp3,fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(43);
                cellData.setRowspan(1);datatable.addCell(cellData);
                 //5.4
                cellData = new Cell(new Chunk(strDtApp3,fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setColspan(30);
                cellData.setRowspan(1);datatable.addCell(cellData);
            }
        }
        catch(Exception e)
        {
            System.out.println("Exception on DpAppPdf.createDetail : "+e.toString());
        }
       // return datatable;
    }
    
    
    private Vector createTakenDate(Vector vDpAppDetail){
        Vector strDp = new Vector(1,1);
        String strTakenDateFrom = "";
        String strTakenDateTo = "";
        String lastFrom = "";
        boolean isToVal = false;
        Date datePrev = null;
        Date dateNext = null;
        for(int j=0;j<vDpAppDetail.size();j++){
            DpAppDetail dpAppDetail = new DpAppDetail();
            dpAppDetail = (DpAppDetail)vDpAppDetail.get(j);
            if(j==0){
                datePrev = dpAppDetail.getTakenDate();
                dateNext = dpAppDetail.getTakenDate();
                if(j+1==vDpAppDetail.size()){
                    strTakenDateFrom += Formater.formatDate(datePrev, "MMM dd, yyyy");
                    strTakenDateTo += Formater.formatDate(datePrev, "MMM dd, yyyy");
                    lastFrom = Formater.formatDate(datePrev, "MMM dd, yyyy");
                }
            }else{
                if(getTommorow(dateNext).getTime()==dpAppDetail.getTakenDate().getTime()){
                    dateNext = dpAppDetail.getTakenDate();
                    if(j+1==vDpAppDetail.size()){
                        if(strTakenDateFrom.length()>0 && isToVal==false){
                            strTakenDateFrom += "\n";
                            strTakenDateTo += lastFrom+"\n";
                        }
                        strTakenDateFrom += Formater.formatDate(datePrev, "MMM dd, yyyy");
                        strTakenDateTo += Formater.formatDate(dateNext, "MMM dd, yyyy");
                        isToVal = true;
                    }
                }else{
                    if(datePrev.getTime()==dateNext.getTime()){
                        if(strTakenDateFrom.length()>0 && isToVal==false){
                            strTakenDateFrom += "\n";
                            strTakenDateTo += lastFrom+"\n";
                        }else{
                            strTakenDateFrom += Formater.formatDate(datePrev, "MMM dd, yyyy");
                            lastFrom = Formater.formatDate(datePrev, "MMM dd, yyyy");
                            isToVal=false;
                        }
                    }else{
                        if(strTakenDateFrom.length()>0 && isToVal==false){
                            strTakenDateFrom += "\n";
                            strTakenDateTo += lastFrom+"\n";
                        }
                        strTakenDateFrom += Formater.formatDate(datePrev, "MMM dd, yyyy");
                        strTakenDateTo += Formater.formatDate(dateNext, "MMM dd, yyyy");
                    }
                    datePrev = dpAppDetail.getTakenDate();
                    dateNext = dpAppDetail.getTakenDate();
                    if(j+1==vDpAppDetail.size()){
                        if(strTakenDateFrom.length()>0 && isToVal==false){
                            strTakenDateFrom += "\n";
                            strTakenDateTo += lastFrom+"\n";
                        }else{
                            strTakenDateFrom += Formater.formatDate(datePrev, "MMM dd, yyyy");
                            lastFrom = Formater.formatDate(datePrev, "MMM dd, yyyy");
                            isToVal=false;
                        }
                    }
                }
            }
        }
        strDp.add(strTakenDateFrom);
        strDp.add(strTakenDateTo);
        return strDp;
    }
    
    private Date getTommorow(Date date){
       Date tdate = (Date)date.clone();
       tdate.setDate(date.getDate()+1);
       return tdate;
    }
    
}
