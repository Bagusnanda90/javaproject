/*
 * ListEmpCategByNamePdf.java
 *
 * Created on June 2, 2007, 10:27 AM
 */

package com.dimata.harisma.report.listRequest;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;

import com.lowagie.text.*; 
import com.dimata.util.*;
import com.lowagie.text.pdf.PdfWriter;
import com.dimata.harisma.entity.employee.*; 
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.report.ObjListCateg;
import com.dimata.harisma.form.employee.*;
import com.dimata.harisma.session.employee.SessEmployee;

/**
 *
 * @author  emerliana
 */
public class ListEmpCategByNamePdf extends HttpServlet {
    
   public static Color bgColor = new Color(200,200,200); 
  
   public void init(ServletConfig config) throws ServletException {
        super.init(config);
   }
   
    public void destroy() {
   }
   
    public static Color border = new Color(0x00, 0x00, 0x00);

    public static Color blackColor = new Color(0, 0, 0);
    public static Color whiteColor = new Color(255, 255, 255);
    public static Color titleColor = new Color(200, 200, 200);
    public static Color summaryColor = new Color(255, 255, 255);
    public static String formatDate = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    // setting some fonts in the color chosen by the user
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, border); 
    public static Font fontLamp = new Font(Font.TIMES_NEW_ROMAN, 12);
    public static Font fontLsHeader = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLD, border);
    public static Font fontLsHeaderU = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLD | Font.UNDERLINE, border);
    public static Font fontLsJudulU = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD | Font.UNDERLINE, border);
    public static Font fontLsContent = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, border);
    public static Font fontLsIsiContent = new Font(Font.TIMES_NEW_ROMAN, 12);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 9);
    public static Font fontNotAvb = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLDITALIC, border);
    public static Font fontHeaderLine = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL | Font.UNDERLINE, border);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        
        Color bgColor = new Color(200, 200, 200);
        Rectangle rectangle = new Rectangle(30, 20, 30, 20);
        Document document = new Document(PageSize.A4.rotate(), 30, 20, 30, 20);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String strCategory = "";
        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            
            HttpSession sessListCategoryByName = request.getSession(true);  
            Vector tmp = null;
            try{
                tmp = (Vector)sessListCategoryByName.getValue("LIST_EMP_CATEGORY_BY_NAME");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }
            
            strCategory = (String)tmp.get(0);
            Vector vListCategByName = (Vector)tmp.get(1);
            
            long oidCategory = Long.parseLong(strCategory);
            
            document.add(getTableTitle(oidCategory));
            document.add(getTableBlank()); 
            Table tbl = getHeaderCategoryName();
            tbl = addEmpCategory(writer, document, tbl, oidCategory, vListCategByName);  
            document.add(tbl);
            
       }catch (Exception e) {
            System.out.println("Exception e : " + e.toString());
        }
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
    
     public static Table getTableBlank() throws BadElementException, DocumentException {
        Table tableHeader = new Table(1);
        tableHeader.setCellpadding(1);
        tableHeader.setCellspacing(1);
        tableHeader.setBorderColor(whiteColor);
        int widthHead[] = {100};
        tableHeader.setWidths(widthHead);
        tableHeader.setWidth(100);

        Cell cellOpname = new Cell(new Chunk("", fontHeader));
        cellOpname.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellOpname.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellOpname.setBackgroundColor(whiteColor);
        cellOpname.setBorderColor(whiteColor);
        tableHeader.addCell(cellOpname);
        return tableHeader;
    }
     
       public static Table getTableTitle(long oidCategory) throws BadElementException, DocumentException {

        int headerInt[] = {10, 90};   
        Table tableHeader = new Table(2);
        
        tableHeader.setCellpadding(1);
        tableHeader.setCellspacing(1);

        tableHeader.setBorderColor(whiteColor);
        tableHeader.setWidth(100);
        
        String nmCategory = "";
        EmpCategory objCategory = new EmpCategory();
        if(oidCategory!=0)
        {
            try{
               objCategory = PstEmpCategory.fetchExc(oidCategory);
            }catch(Exception e){;}
            nmCategory = objCategory.getEmpCategory();
        }
        else{
            nmCategory = "ALL CATEGORY";
        }
        
        
        /* rows 1 cols 1-2 */
        Cell cellJudul = new Cell(new Chunk("LIST OFF EMPLOYEE  " +
                         "\n"+com.dimata.system.entity.system.PstSystemProperty.getValueByName("COMPANY_NAME"), fontLsJudulU));
        cellJudul.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellJudul.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellJudul.setBorderColor(whiteColor);
        cellJudul.setBackgroundColor(whiteColor);
        cellJudul.setColspan(2);
        tableHeader.addCell(cellJudul);
        
        return tableHeader;
    }
       
       public static Table getHeaderCategoryName(){
        try{
            int headerInt[] = {3, 16, 15, 22, 22, 22};
            Table tbl = new Table(6);
            //tbl.setBorderColor(new Color(255,255,255));
            tbl.setWidth(100);
            tbl.setWidths(headerInt);
            tbl.setBorderWidth(0);
            tbl.setCellpadding(1);
            tbl.setCellspacing(1);
            tbl.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            tbl.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                       

            Cell hcell = null ;
            hcell = new Cell(new Chunk("NO", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor);            
            tbl.addCell(hcell);
            
            hcell = new Cell(new Chunk("CATEGORY", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor);           
            tbl.addCell(hcell); 
            
            hcell = new Cell(new Chunk("GENDER", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor);           
            tbl.addCell(hcell); 
            
            hcell = new Cell(new Chunk("FULL NAME", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor);           
            tbl.addCell(hcell);
            
            hcell = new Cell(new Chunk("DEPARTMENT", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor);           
            tbl.addCell(hcell);
            
             hcell = new Cell(new Chunk("SECTION", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor);           
            tbl.addCell(hcell);
            
           /* tbl.addCell(new Phrase("1", fontHeader));
            tbl.addCell(new Phrase("2", fontHeader));
            tbl.addCell(new Phrase("3", fontHeader));
            tbl.addCell(new Phrase("4", fontHeader));
            tbl.addCell(new Phrase("5", fontHeader));
            tbl.addCell(new Phrase("6", fontHeader));*/
          
          return tbl;
        }
        catch(Exception e){
            System.out.println("Error di header "+e.toString());
        }
        
        return null;
    }
       
    
  public Table addEmpCategory(PdfWriter writer, Document document, Table tbl, long oidEmpCategory, Vector vList){
    try{
              
          int idx = 0;
          if(vList!=null && vList.size()>0)
          {
              if(!writer.fitsPage(tbl)) {
                document.newPage();
              }
              
              for(int k=0;k<vList.size();k++)
              {
                Vector vListCategByName = (Vector)vList.get(k);
                  
                Cell hcell = null ;
                //1
                hcell = new Cell(new Chunk(String.valueOf(vListCategByName.get(0)), fontContent));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);

                hcell = new Cell(new Chunk(String.valueOf(vListCategByName.get(1)), fontContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);
                
                hcell = new Cell(new Chunk(String.valueOf(vListCategByName.get(2)), fontContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);
                
                hcell = new Cell(new Chunk(String.valueOf(vListCategByName.get(3)), fontContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);
                
                hcell = new Cell(new Chunk(String.valueOf(vListCategByName.get(4)), fontContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);
                
                hcell = new Cell(new Chunk(String.valueOf(vListCategByName.get(5)), fontContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);
                
          
               
              if(!writer.fitsPage(tbl)) {
               tbl.deleteLastRow();
               k--;
               document.add(tbl);
               document.newPage();
               tbl = getHeaderCategoryName();  
              }
             
              }
          }
          
          }catch(Exception e){
         System.out.println("Error::"+e.toString());
        }
   return tbl;
       
}  
    
     
     
    
}
