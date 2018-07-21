/*
 * ListEmpEducationPdf.java
 *
 * Created on May 28, 2007, 3:59 PM
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
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstReligion;
import com.dimata.harisma.entity.masterdata.Religion;
import com.dimata.harisma.entity.report.ObjListCateg;
import com.dimata.harisma.entity.search.SrcEmployee;
import com.dimata.harisma.form.employee.*;
import com.dimata.harisma.session.employee.SessEmployee;

/**
 *
 * @author  emerliana
 */
public class ListEmpEducationPdf extends HttpServlet  {
    
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
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 8, Font.BOLD, border); 
    public static Font fontLamp = new Font(Font.TIMES_NEW_ROMAN, 12);
    public static Font fontLsHeader = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLD, border);
    public static Font fontLsHeaderU = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLD | Font.UNDERLINE, border);
    public static Font fontLsJudulU = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD | Font.UNDERLINE, border);
    public static Font fontLsContent = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, border);
    public static Font fontLsIsiContent = new Font(Font.TIMES_NEW_ROMAN, 12);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 8);
    public static Font fontContent1 = new Font(Font.TIMES_NEW_ROMAN, 8, Font.BOLD);
    public static Font fontNotAvb = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLDITALIC, border);
    public static Font fontHeaderLine = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL | Font.UNDERLINE, border);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        
        Color bgColor = new Color(200, 200, 200);
        Rectangle rectangle = new Rectangle(30, 20, 30, 20);
        Document document = new Document(PageSize.A4.rotate(), 30, 20, 30, 30);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String strLevel = "";
        SrcEmployee srcEmployee = new SrcEmployee();
        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            
            HttpSession sessListEducation = request.getSession(true);   
            Vector tmp = (Vector)sessListEducation.getValue("LIST_EMP_EDUCATION");
            
            strLevel = (String)tmp.get(0);
            srcEmployee = (SrcEmployee)tmp.get(1);
            
            long oidLevel = Long.parseLong(strLevel);
            
            Vector vEmpEducation = (Vector)tmp.get(2);
            
            document.add(getTableTitle(oidLevel,srcEmployee));
            document.add(getTableBlank());
            Table tbl = getHeaderEducation();
            tbl = addEmpContent(writer, document, tbl, vEmpEducation, oidLevel, srcEmployee); 
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
    
    public static Table getTableTitle(long oidLevel,SrcEmployee srcEmployee) throws BadElementException, DocumentException {

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
        
        Date startPeriod = srcEmployee.getStartCommenc();
        Date endPeriod = srcEmployee.getEndCommenc();
        
        System.out.println("startPeriod "+startPeriod);
        /* rows 1 cols 1-2 */
        Cell cellJudul = new Cell(new Chunk("LIST OFF EMPLOYEE EDUCATION ("+nmLevel+")"+
        "\n"+com.dimata.system.entity.system.PstSystemProperty.getValueByName("COMPANY_NAME"), fontLsJudulU));
        cellJudul.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellJudul.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellJudul.setBorderColor(whiteColor);
        cellJudul.setBackgroundColor(whiteColor);
        cellJudul.setColspan(2);
        tableHeader.addCell(cellJudul);
        
        
        if((startPeriod==null)|| (endPeriod==null)){
            cellJudul = new Cell(new Chunk("Per Period "+Formater.formatDate(new Date(),"dd-MMM-yyyy"), fontLsJudulU));

        }
        else{
            cellJudul = new Cell(new Chunk("Per Period "+Formater.formatDate(startPeriod,"dd-MMM-yyyy")+" sampai dengan "+Formater.formatDate(endPeriod,"dd-MMM-yyyy"), fontLsJudulU));
        }
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
    
  
  public static Table getHeaderEducation()
   {
        try{
            
            //ObjListCateg objListCateg = (ObjListCateg)vList.get(0);
            Vector vListReligion = PstReligion.list(0,0, "","");
            
             
            //Vector vListEducation = objListCateg.getListValue(); 
            
            System.out.println("vListCateg:::::"+vListReligion.size());
            
            int[] headerInt = new int[4+(vListReligion.size()*2)];
            headerInt[0] = 3;
            headerInt[1] = 27;
            headerInt[2] = 10;
            headerInt[3] = 10;
           
            
            int idx = 4;
            for(int k=0;k<(vListReligion.size()*2);k++){
                headerInt[idx+k] = 10;
                //JenisKepeg objKepeg = (JenisKepeg)vCount.get(1);
            }
            Table tbl = new Table(4+(vListReligion.size()*2));
            
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
            
            hcell = new Cell(new Chunk("EDU", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor); 
            hcell.setRowspan(3);
            tbl.addCell(hcell); 
            
            hcell = new Cell(new Chunk("NUMBER OFF RELIGION ", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor);
            hcell.setColspan((vListReligion.size()*2));
            hcell.setRowspan(1);
            tbl.addCell(hcell); 
            
            hcell = new Cell(new Chunk("TOTAL", fontHeader));
            //hcell.setBorderColor(new Color(255,255,255));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor); 
            hcell.setRowspan(3);
            tbl.addCell(hcell); 
            
           
            
            for(int k=0;k<vListReligion.size();k++){
                //Vector chld = (Vector)vListReligion.get(k);
                //System.out.println("vCounnnnnnnnnnnnnnnnn"+chld);
                Religion objReligion = (Religion)vListReligion.get(k);
                
                hcell = new Cell(new Chunk(""+objReligion.getReligion(), fontHeader));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                hcell.setBackgroundColor(whiteColor);
                hcell.setColspan(2);
                tbl.addCell(hcell);
            }
            
            for(int k=0;k<vListReligion.size();k++){

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
            

           /* tbl.addCell(new Phrase("1", fontHeader));
            tbl.addCell(new Phrase("2", fontHeader));
            tbl.addCell(new Phrase("3", fontHeader));
            tbl.addCell(new Phrase("4", fontHeader));
           

            for(int k=0;k<(vListReligion.size()*2);k++){                
                tbl.addCell(new Phrase(""+(5+k), fontHeader));
            }*/
          
          return tbl;
        }
        catch(Exception e){
            System.out.println("Error di header "+e.toString());
        }
        
        return null;
    }
  
  
 /* public Table addEmpContent(PdfWriter writer, Document document, Table tbl, Vector vList, long oidLevel, SrcEmployee srcEmployee){
       try{
              
           String objLevel = "";
           int idx = 0;
           
           Vector listReligion = new Vector();
           Vector listEmpLevel = new Vector();
           Vector listEmpPendidikan = new Vector();
        
           listEmpLevel = SessEmployee.tmpNmLevel(oidLevel);
           listEmpPendidikan = SessEmployee.tmpNmPendidikan();
           listReligion = PstReligion.list(0,0, "","");
           
          
          Hashtable has1 = new Hashtable();
          
          if(listEmpLevel!=null && listEmpLevel.size()>0)
          {
              if(!writer.fitsPage(tbl)) {
                document.newPage();
              }
              
              for(int k=0;k<listEmpLevel.size();k++)
              {
                Vector temp = (Vector)listEmpLevel.get(k);
                Level level = (Level)temp.get(0); 
                
                Cell hcell = null ;
                //1
                hcell = new Cell(new Chunk(""+(k+1), fontContent));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);
                
                hcell = new Cell(new Chunk(""+level.getLevel(), fontContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);
                
                int countJumlah = 0;
                Hashtable has = new Hashtable();
                
                for(int i=0;i<listEmpPendidikan.size();i++)
                {
                    Education education = (Education)listEmpPendidikan.get(i);
                    
                    
                        if(i!=0)
                        {
                            
                            hcell = new Cell(new Chunk("", fontContent));
                            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            tbl.addCell(hcell);

                            hcell = new Cell(new Chunk("", fontContent));
                            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            tbl.addCell(hcell);
                        }
			hcell = new Cell(new Chunk(""+education.getEducation(), fontContent));
                        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(hcell);
                    
                        for(int j=0;j<listReligion.size();j++)
                        {
                            Religion objRelegion = (Religion)listReligion.get(j);
                            
                            int countMale = SessEmployee.listEducationEmpMale(education.getOID(),level.getOID(),objRelegion.getOID(),srcEmployee);
			    int countFemale = SessEmployee.listEducationEmpFemale(education.getOID(),level.getOID(),objRelegion.getOID(),srcEmployee);
                            
                            // for set total per religion
			    has = setHashTotal(has,objRelegion.getReligion(),countMale,countFemale);
                            
                            hcell = new Cell(new Chunk(""+countMale, fontContent));
                            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            tbl.addCell(hcell);

                            hcell = new Cell(new Chunk(""+countFemale, fontContent));
                            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            tbl.addCell(hcell);
                        }
                        
                        countJumlah = SessEmployee.listEducationJum(education.getOID(),level.getOID(),srcEmployee);
                        
                        hcell = new Cell(new Chunk(""+countJumlah, fontHeader));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(hcell);
                
                    }
                
              //rows untuk sub total per level
              hcell = new Cell(new Chunk("", fontContent));
              hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
              hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              tbl.addCell(hcell);

              hcell = new Cell(new Chunk("", fontContent));
              hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
              hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              tbl.addCell(hcell);
              
              hcell = new Cell(new Chunk("Total", fontContent1));
              hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
              hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              tbl.addCell(hcell);
              
              for(int j=0;j<listReligion.size();j++)
              {
                Religion objRelegion = (Religion)listReligion.get(j);
                
                Vector vect = (Vector)has.get(objRelegion.getReligion());
                int totMale = Integer.parseInt((String)vect.get(0));
		int totFemale = Integer.parseInt((String)vect.get(1));
                
                has1 = setHashTotal(has1,objRelegion.getReligion(),totMale,totFemale);
                
                hcell = new Cell(new Chunk(""+totMale, fontContent1));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);

                hcell = new Cell(new Chunk(""+totFemale, fontContent1));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);
              }  
              
              hcell = new Cell(new Chunk("", fontContent));
              hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
              hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              tbl.addCell(hcell); 
              
              
              
      
               
              if(!writer.fitsPage(tbl)) {
               tbl.deleteLastRow();
               tbl.deleteLastRow();
               tbl.deleteLastRow();
               tbl.deleteLastRow();
               tbl.deleteLastRow();
               tbl.deleteLastRow();
              
               k--;
               document.add(tbl);
               document.newPage();
               tbl = getHeaderEducation();  
              }
              
              
              }
            }
          
              Cell hcell = null ;
              hcell = new Cell(new Chunk("", fontContent));
              hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
              hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              tbl.addCell(hcell);

              hcell = new Cell(new Chunk("TOTAL", fontContent1));
              hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
              hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              tbl.addCell(hcell);
              
              hcell = new Cell(new Chunk("", fontContent));
              hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
              hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              tbl.addCell(hcell);
              
              for(int j=0;j<listReligion.size();j++)
              {
                Religion objRelegion = (Religion)listReligion.get(j);
                
                Vector vect1 = (Vector)has1.get(objRelegion.getReligion());
                int totMale1 = Integer.parseInt((String)vect1.get(0));
		int totFemale1 = Integer.parseInt((String)vect1.get(1));
                
                hcell = new Cell(new Chunk(""+totMale1, fontContent1));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);

                hcell = new Cell(new Chunk(""+totFemale1, fontContent1));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tbl.addCell(hcell);
              }  
              
              hcell = new Cell(new Chunk("", fontContent));
              hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
              hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              tbl.addCell(hcell);
              
          
          }catch(Exception e){
         System.out.println("Error::"+e.toString());
        }
     return tbl;
   }*/
  
public Table addEmpContent(PdfWriter writer, Document document, Table tbl, Vector vList, long oidLevel, SrcEmployee srcEmployee){
 try{
        Vector vListReligion = PstReligion.list(0,0, "","");
        
        if(vList!=null && vList.size()>0)
          {
              if(!writer.fitsPage(tbl)) {
                document.newPage();
              }
              
              for(int k=0;k<vList.size();k++)
              {
                  Vector vListEducation = (Vector)vList.get(k);
                  
                    Cell hcell = null ;
                    //1
                    hcell = new Cell(new Chunk(String.valueOf(vListEducation.get(0)), fontContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);

                   hcell = new Cell(new Chunk(String.valueOf(vListEducation.get(1)), fontContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);

                    hcell = new Cell(new Chunk(String.valueOf(vListEducation.get(2)), fontContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);
                    int countMale = 1;
                    int countFemale = 2;
                    
                   for(int l=0;l<vListReligion.size();l++)
                   {
                        countMale = countMale + 2;
                        countFemale = countFemale + 2;
                        
                        hcell = new Cell(new Chunk(String.valueOf(vListEducation.get(countMale)), fontContent));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(hcell);
                        
                        hcell = new Cell(new Chunk(String.valueOf(vListEducation.get(countFemale)), fontContent));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbl.addCell(hcell);
                        
                        System.out.println("countMale:::::::::::::::"+countMale);
                        System.out.println("countFemale:::::::::::::::"+countFemale);
                        System.out.println("l:::::::::::::::"+l);
                        
                   }
                    int jumList = vListReligion.size();
                    int countJumlah = (jumList * 2) + 3;
                    hcell = new Cell(new Chunk(String.valueOf(vListEducation.get(countJumlah)), fontContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbl.addCell(hcell);
                    
                    
                     if(!writer.fitsPage(tbl)) {
                       tbl.deleteLastRow();
                       //tbl.deleteLastRow();
                       k--;
                       document.add(tbl);
                       document.newPage();
                       tbl = getHeaderEducation();  
                      }
                     /*if(!writer.fitsPage(tbl)) {
                       tbl.deleteLastRow();
                       tbl.deleteLastRow();
                       tbl.deleteLastRow();
                       tbl.deleteLastRow();
                       tbl.deleteLastRow();
                       tbl.deleteLastRow();
                       k--;
                       document.add(tbl);
                       document.newPage();
                       tbl = getHeaderEducation();  
                      }*/
             
              }
          }    
        
   }catch(Exception e){
     System.out.println("Error::"+e.toString());
   }
 return tbl;
}
  
//hastable untuk menghitung jumlah total dari semua list dan sub total
public Hashtable setHashTotal(Hashtable hasTotal, String religion, int qtyMale, int qtyFemale) {
	try {
		if(hasTotal.get(religion) != null) {
			Vector temp1 = (Vector)hasTotal.get(religion);
			int totalMale = Integer.parseInt((String)temp1.get(0));
			int totalFemale = Integer.parseInt((String)temp1.get(1));
			
			totalMale = totalMale + qtyMale;
			totalFemale = totalFemale + qtyFemale;
			temp1 = new Vector();
			temp1.add(String.valueOf(totalMale));
			temp1.add(String.valueOf(totalFemale));
			
            hasTotal.remove(religion);
            hasTotal.put(religion, temp1);
		}
		else {
			Vector temp2 = new Vector(1,1);
			temp2.add(String.valueOf(qtyMale));
			temp2.add(String.valueOf(qtyFemale));
			hasTotal.put(religion, temp2);
		}
	}
	catch(Exception e){
	}
	return hasTotal;
}
 
 
}
