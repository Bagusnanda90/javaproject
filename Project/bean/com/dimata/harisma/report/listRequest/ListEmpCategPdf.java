/*
 * listEmpCategPdf.java
 *
 * Created on May 23, 2007, 11:34 AM
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
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.report.ObjListCateg;
import com.dimata.harisma.form.employee.*;
import com.dimata.harisma.session.employee.SessEmployee;

/**
 *
 * @author  emerliana
 */
public class ListEmpCategPdf extends HttpServlet  {
    
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
        String strLevel = "";
        String strFooter = "";
        Date dateFrom = new Date();
        Date dateTo = new Date();
        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            
            
            HttpSession sessListLevel = request.getSession(true);   
            Vector tmp = (Vector)sessListLevel.getValue("LIST_EMP_LEVEL");
            
            strLevel = (String)tmp.get(0);
            strFooter = (String)tmp.get(1);
            dateFrom = (Date)tmp.get(2);
            dateTo = (Date)tmp.get(3);
            
            long oidLevel = Long.parseLong(strLevel);
            
            //System.out.println("oidLevel:::::::::::::::::::::::"+oidLevel);
            
            Vector vEmpLevel = getListCategEmp(dateFrom,dateTo,oidLevel);
            
             //footer
             
             HeaderFooter footer = new HeaderFooter(new Phrase(""+strFooter.toUpperCase(), fontHeader), false);

           // HeaderFooter headerleft = new HeaderFooter(new Phrase("LEVEL : " + levName.toUpperCase(), fontHeader), false);
            footer.setAlignment(Element.ALIGN_LEFT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
            document.setFooter(footer);
           //------------------------------------
            document.open();
            document.add(getTableTitle(oidLevel));
            document.add(getTableBlank());
            Table tbl = getHeaderCategory(vEmpLevel);
            tbl = addEmpContent(writer, document, tbl, vEmpLevel, oidLevel,dateFrom,dateTo); 
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
    
    public static Table getTableTitle(long oidLevel) throws BadElementException, DocumentException {

        int headerInt[] = {10, 90};   
        Table tableHeader = new Table(2);
        
        tableHeader.setCellpadding(1);
        tableHeader.setCellspacing(1);

        tableHeader.setBorderColor(whiteColor);
        tableHeader.setWidth(100);
        
        String nmLevel = "";
        if(oidLevel!=0)
        {
            Level level = new Level();
            try
            {
                level = PstLevel.fetchExc(oidLevel);
            }catch(Exception e){;}
            nmLevel = level.getLevel();
        }
        else{
            nmLevel = "ALL LEVEL";
        }
        
        /* rows 1 cols 1-2 */
        Cell cellJudul = new Cell(new Chunk("LIST OFF EMPLOYEE CATEGORY " +
                        "\n"+com.dimata.system.entity.system.PstSystemProperty.getValueByName("COMPANY_NAME"), fontLsJudulU));
        cellJudul.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellJudul.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellJudul.setBorderColor(whiteColor);
        cellJudul.setBackgroundColor(whiteColor);
        cellJudul.setColspan(2);
        tableHeader.addCell(cellJudul);
        
        return tableHeader;
        
        
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
    
    
   public static Table getHeaderCategory(Vector vList)
   {
        
        try{
            
            ObjListCateg objListCateg = (ObjListCateg)vList.get(0);
            
            Vector vListCateg = objListCateg.getListValue(); 
             
            int[] headerInt = new int[3+(vListCateg.size()*2)];
            headerInt[0] = 3;
            headerInt[1] = 27;
            headerInt[2] = 10;
           
            int idx = 3;
            for(int k=0;k<(vListCateg.size()*2);k++){
                headerInt[idx+k] = 10;
                //JenisKepeg objKepeg = (JenisKepeg)vCount.get(1);
            }
            Table tbl = new Table(3+(vListCateg.size()*2));
            
             //tbl.setBorderColor(new Color(255,255,255));
            tbl.setWidth(100);
            tbl.setWidths(headerInt);
            tbl.setBorderWidth(0);
            tbl.setCellpadding(1);
            tbl.setCellspacing(1);
            tbl.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            tbl.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                       

            Cell hcell = null ;
            hcell = new Cell(new Chunk("No", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor); 
            hcell.setRowspan(3);
            tbl.addCell(hcell);
            
            hcell = new Cell(new Chunk("LEVEL", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor); 
            hcell.setRowspan(3);
            tbl.addCell(hcell); 
            
            hcell = new Cell(new Chunk("NUMBER OF CATEGORY", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor);
            hcell.setColspan((vListCateg.size()*2));
            hcell.setRowspan(1);
            tbl.addCell(hcell); 
            
            hcell = new Cell(new Chunk("TOTAL", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor); 
            hcell.setRowspan(3);
            tbl.addCell(hcell); 
            
           
            
            for(int k=0;k<vListCateg.size();k++){
                Vector chld = (Vector)vListCateg.get(k);
                //System.out.println("vCounnnnnnnnnnnnnnnnn"+chld);
                EmpCategory objCategory = (EmpCategory)chld.get(1);
                
                hcell = new Cell(new Chunk(""+objCategory.getEmpCategory(), fontHeader));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                hcell.setBackgroundColor(whiteColor);
                hcell.setColspan(2);
                tbl.addCell(hcell);
            }
            
            for(int k=0;k<vListCateg.size();k++){

                hcell = new Cell(new Chunk("M", fontHeader));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                hcell.setBackgroundColor(whiteColor); 
                //hcell.setColspan(2); 
                tbl.addCell(hcell);

                hcell = new Cell(new Chunk("F", fontHeader));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                hcell.setBackgroundColor(whiteColor); 
                //hcell.setColspan(2); 
                tbl.addCell(hcell);
            }
            

           
          return tbl;
        }
        catch(Exception e){
            System.out.println("Error di header "+e.toString());
        }
        
        return null;
    }
   
  
   
   public Table addEmpContent(PdfWriter writer, Document document, Table tbl, Vector vList, long oidLevel,Date dateFrom,Date dateTo){
       try{
              
           String objLevel = "";
           int idx = 0;
           
          ListEmpCategPdf objCateg = new ListEmpCategPdf();
          vList = objCateg.getListCategEmp(dateFrom,dateTo,oidLevel);
          
          if(vList!=null && vList.size()>0)
          {
              if(!writer.fitsPage(tbl)) {
                document.newPage();
              }
              for(int k=0;k<vList.size();k++)
              {
                  ObjListCateg objListCateg = (ObjListCateg)vList.get(k);
                   objLevel = objListCateg.getNmLevel();

                   int Jmlhnya = objListCateg.getJumlah();
                   int totalJumlah = objListCateg.getTotJumlah();
                   Vector vCount = objListCateg.getListValue();
                 
                if(k==vList.size()-1){
                   Cell hcell = null ;
                    //1
                    hcell = new Cell(new Chunk("", fontContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);
                    
                    hcell = new Cell(new Chunk("Total", fontContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);
                    
                    for(int j=0;j<vCount.size();j++)
                    {
                        Vector temp = (Vector)vCount.get(j);
                        String male = (String)temp.get(0);
                        String female = (String)temp.get(2);

                        hcell = new Cell(new Chunk(""+male, fontContent));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(hcell);

                        hcell = new Cell(new Chunk(""+female, fontContent));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(hcell);
                    }

                    hcell = new Cell(new Chunk(""+totalJumlah, fontHeader));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);
                 
                 }else{
                      
                      Cell hcell = null ;
                    //1
                    hcell = new Cell(new Chunk(""+(idx+1), fontContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);

                    hcell = new Cell(new Chunk(""+objLevel, fontContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);

                    for(int j=0;j<vCount.size();j++)
                    {
                        Vector temp = (Vector)vCount.get(j);
                        String Count = (String)temp.get(0);
                        String CountY = (String)temp.get(2);

                        hcell = new Cell(new Chunk(""+Count, fontContent));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(hcell);

                        hcell = new Cell(new Chunk(""+CountY, fontContent));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(hcell);
                    }

                    hcell = new Cell(new Chunk(""+Jmlhnya, fontHeader));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);
                    
                    
                 }  
                
               if(!writer.fitsPage(tbl)) {
                       tbl.deleteLastRow();
                       k--;
                       document.add(tbl);
                       document.newPage();
                       tbl = getHeaderCategory(vList);  
                      }

                       idx = idx + 1;

                  }
          }
          
          }catch(Exception e){
         System.out.println("Error ::"+e.toString());
        }
     return tbl;
   }
    
    
    public static Vector getListCategEmp(Date dateFrom,Date dateTo,long oidLevel)
    {
        Vector result = new Vector();
        Vector listCategory = new Vector();
        Vector listEmpLevel = new Vector();
        
       listEmpLevel = SessEmployee.tmpNmLevel(oidLevel);
       listCategory = PstEmpCategory.list(0,0, "","");
       
       if(listEmpLevel!=null && listEmpLevel.size()>0)
       {
           for(int k=0;k<=listEmpLevel.size();k++)
           {
               ObjListCateg objCateg = new ObjListCateg();
               if(k==listEmpLevel.size()){
                   int totalJumlah = SessEmployee.listEmployeeSumCateg(dateFrom,dateTo,0);
                   objCateg.setTotJumlah(totalJumlah);
                   
                   if(listCategory!=null && listCategory.size()>0)
                   {
                     for(int i =0;i<listCategory.size();i++)    
                     {
                         Vector temp = new Vector();
                         EmpCategory objEmpCateg = (EmpCategory)listCategory.get(i);
                         int totalMale = SessEmployee.listEmployeeCategMale(dateFrom,dateTo,objEmpCateg.getOID(),0);
                         System.out.println("totalMale..."+totalMale);
			 int totalFemale = SessEmployee.listEmployeeCategFemale(dateFrom,dateTo,objEmpCateg.getOID(),0);
                         temp.add(""+totalMale);
                         temp.add(objEmpCateg);
                         temp.add(""+totalFemale);

                         objCateg.setListValue(temp);
                         
                     }
                   }
                    result.add(objCateg);
               }else{
                   Vector tempA = (Vector)listEmpLevel.get(k);
                   Level objLevel = (Level)tempA.get(0);

                   long oidLevel_list = objLevel.getOID();
                   objCateg.setNmLevel(objLevel.getLevel());

                   int j = SessEmployee.listEmployeeSumCateg(dateFrom,dateTo,oidLevel_list);
                   //System.out.println("jjjjj:::"+j);
                   objCateg.setJumlah(j);

                   if(listCategory!=null && listCategory.size()>0)
                   {
                     for(int i =0;i<listCategory.size();i++)    
                     {
                            Vector temp = new Vector();
                             EmpCategory objEmpCateg = (EmpCategory)listCategory.get(i);

                             long oidCateg = objEmpCateg.getOID();

                             int x = SessEmployee.listEmployeeCategMale(dateFrom,dateTo,oidCateg, oidLevel_list);
                             int y = SessEmployee.listEmployeeCategFemale(dateFrom,dateTo,oidCateg, oidLevel_list);

                             temp.add(""+x);
                             temp.add(objEmpCateg);
                             temp.add(""+y);

                             objCateg.setListValue(temp);
                         }
                     }
                    result.add(objCateg);
                }
               
              
           }
       }
       
       return result;
    }
    
    
    
}
