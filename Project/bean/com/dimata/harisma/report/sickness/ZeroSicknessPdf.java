package com.dimata.harisma.report.sickness;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.Date;
import java.util.*;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.util.Formater;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;  

public class ZeroSicknessPdf extends HttpServlet {
    // public static final membervariables

    static Color border = new Color(0x00, 0x00, 0x00);
    static Color bgTable = new Color(200, 200, 200);
    static Color bgCells = new Color(255, 255, 255);    
    
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    
    public static Font fontHeader = new Font(Font.HELVETICA, 6, Font.BOLD, blackColor);
    public static Font fontTitleHeader = new Font(Font.HELVETICA, 8, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.HELVETICA, 7, Font.NORMAL, blackColor);        
    public static Font fontItem = new Font(Font.HELVETICA, 7, Font.NORMAL, blackColor);    

    /**
     * @param fontHeader
     * @param oidDept
     * @param selectedDate
     * @return
     */    
    public static Table createHeader(Font fontItem, long oidDept, long oidSec, String startPeriod, String endPeriod,Vector listStruktur)   
    {
        /*Table headertable = null;*/
          String departement = "";
          String company = "";
          String division = "";
          String section ="";
          String empNum = "";
          String fullName ="";
          
          if(listStruktur !=null && listStruktur.size()>0){
                for(int d=0; d < listStruktur.size(); d++){
                     StrukturEmployee strukturEmployee = (StrukturEmployee)listStruktur.get(d);
                    company = strukturEmployee.getCompany();
                    division = strukturEmployee.getDivision();
                    departement = strukturEmployee.getDepartment();
                    section = strukturEmployee.getSection();
                    empNum = strukturEmployee.getEmpNumber();
                    fullName = strukturEmployee.getFullName();
                }
            }
          if(empNum ==null || empNum.length() <= 0 ){
               empNum="-";
            }
          if(fullName ==null || fullName.length() <= 0 ){
               fullName="-";
            }
            if(departement ==null || departement.length() <= 0 ){
               departement="-";
            }
             if(section ==null || section.length() <= 0 ){
               section="-";
            }
              if(company ==null || company.length() <= 0 ){
               company="-";
            }
            if(division ==null || division.length() <= 0 ){
               division="-";
            }
              Table tableHeader = null;
        try
        {
           tableHeader = new Table(7);
           tableHeader.setCellpadding(0);
           tableHeader.setCellspacing(0);
           //tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
           int widthHeader[] = {4, 1, 6,1,4, 1, 10};
    	   tableHeader.setWidths(widthHeader);
           tableHeader.setWidth(100);
          
           Cell  objTableNew;
           objTableNew = new Cell (new Chunk("ZERO SICKNESS REPORT FROM : "+startPeriod.toUpperCase() + " TO " + endPeriod.toUpperCase(),fontTitleHeader));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setHorizontalAlignment(Cell.ALIGN_LEFT);
           objTableNew.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           objTableNew.setBorderColor(whiteColor);
           objTableNew.setColspan(7);
           tableHeader.addCell(objTableNew);
           ///payrol number
           objTableNew = new Cell (new Chunk("Payrol Number",fontItem));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(empNum,fontItem));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk(""));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk("Name",fontItem));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(fullName,fontItem));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           
           
           objTableNew = new Cell (new Chunk("Company",fontItem));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(company,fontItem));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk(""));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk("Division",fontItem));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(division,fontItem));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           
           objTableNew = new Cell (new Chunk("Departement",fontItem));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(departement,fontItem));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk(""));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk("Section",fontItem));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(section,fontItem));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
          
        /*Table headertable = null;
            headertable = new Table(2);
            int hdrwidths[] = {98,2};
            headertable.setWidths(hdrwidths);
            headertable.setWidth(100);
            headertable.setPadding(0);
            headertable.setSpacing(0);
            headertable.setDefaultCellBorder(Cell.NO_BORDER);
            headertable.setBorder(Table.NO_BORDER);

            
           
            
            
            
            
            headertable.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            headertable.addCell(new Phrase("ZERO SICKNESS REPORT", fontHeader));
            headertable.addCell(new Phrase("FROM : " + startPeriod.toUpperCase() + " TO " + endPeriod.toUpperCase(), fontHeader));
            
           /* String sDepartmentName = ""; 
            String sSectionName = "";
            
            if(oidDept != 0)
            {
                Department dep = new Department();            
                try
                {
                    dep = PstDepartment.fetchExc(oidDept);
                    sDepartmentName = dep.getDepartment();
                }
                catch(Exception e)
                {
                    System.out.println("Exc when fetch Department : " + e.toString());
                }
            }
            else
            {
                sDepartmentName = "ALL";
            }
            
            if(oidSec != 0)
            {
                Section sec = new Section();            
                try
                {
                    sec = PstSection.fetchExc(oidSec);
                    sSectionName = sec.getSection();
                }
                catch(Exception e)
                {
                    System.out.println("Exc when fetch Department : " + e.toString());
                }
            }
            else
            {
                sSectionName = "ALL";
            }
            
            headertable.addCell(new Phrase("COMPANY : "+company.toUpperCase(), fontHeader));
            headertable.addCell(new Phrase("DEPARTMENT : " + departement.toUpperCase(), fontHeader));
            headertable.addCell(new Phrase("DIVISION : "+division.toUpperCase(), fontHeader));
            headertable.addCell(new Phrase("SECTION : " + section.toUpperCase(), fontHeader));
        */
        }
        catch(Exception e)
        {
            System.out.println("Exc when fetch createHeader : " + e.toString());
        }

        return tableHeader;
    }

    
    /**
     * @param fontHeader
     * @param toDt
     * @return
     */    
    public static Table createHeaderDetail(Font fontHeader, String[] strTitle)
    {        
        Table datatable = null;
        try
        {
            // create table
            datatable = new Table(5);
            datatable.setPadding(1);
            datatable.setSpacing(1);
            int[] headerwidths={5,10,20,20,20};
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(1);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultCellBackgroundColor(summaryColor);
            
            datatable.addCell(new Phrase(strTitle[0], fontHeader));
            datatable.addCell(new Phrase(strTitle[1], fontHeader));
            datatable.addCell(new Phrase(strTitle[2], fontHeader));
            datatable.addCell(new Phrase(strTitle[3], fontHeader));
            datatable.addCell(new Phrase(strTitle[4], fontHeader));
            
            // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)       
        {
            System.out.println("Exc createHeaderDetail : " + e.toString());
        }
        return datatable;
    }

     
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException 
    {

        HttpSession session = request.getSession(true);
        Vector temp = (Vector)session.getValue("ZERO_SICKNESS");

        Document document = new Document(PageSize.A4, 30, 30, 20, 20);        

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
         long oidDept = 0;
         long oidSec = 0;
                   String empNumb="";
             String fullName="";
               Vector listStruktur = new Vector(1,1);
        try 
        {            
            PdfWriter.getInstance(document, baos);
            document.open();

            String[] strTitle = (String[])temp.get(0);
             if(temp.get(1) !=null){
                         oidDept = Long.parseLong((String)temp.get(1));
                       // if(oidDepartment !=0){
	               // department = (Department)PstDepartment.fetchExc(oidDepartment);
                        //}
                      } 
                        if(temp.get(2) !=null){
                          oidSec = Long.parseLong((String)temp.get(2));
                        //if(oidSection !=0){
                            //section = (com.dimata.harisma.entity.masterdata.Section)PstSection.fetchExc(oidSection);
                        //}
                      }
             if(temp.get(6)!=null)
                        empNumb = (String)temp.get(6);
                        if(temp.get(7)!=null)
                        fullName = (String)temp.get(7);
                       if((empNumb !=null && empNumb.length() > 0) 
                                 || fullName !=null && fullName.length()>0
                                 || oidDept !=0
                                 || oidSec !=0){
                            listStruktur = PstDepartment.listStruktur(empNumb.trim(), fullName.trim(),oidDept,oidSec);
                         }
	         
           // oidDept = Long.parseLong((String)temp.get(1)); 
            //long oidSec = Long.parseLong((String)temp.get(2));
            String perStart = (String)temp.get(3);
            String perEnd = (String)temp.get(4);
            Vector list = (Vector)temp.get(5);            

            document.add(createHeader(fontHeader, oidDept, oidSec, perStart, perEnd,listStruktur));  
            Table datatable = createHeaderDetail(fontTitle, strTitle);
            datatable.setDefaultRowspan(1);
            datatable.setDefaultCellBackgroundColor(bgCells);
                       
            for (int i = 0; i < list.size(); i++) 
            {
                Vector tmp = (Vector)list.get(i);
                               
                // menggambar list data                
                datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
                datatable.addCell(new Phrase((String)tmp.get(0), fontItem));
                datatable.addCell(new Phrase((String)tmp.get(1), fontItem));
                datatable.addCell(new Phrase((String)tmp.get(2), fontItem));
                datatable.addCell(new Phrase((String)tmp.get(3), fontItem));
                datatable.addCell(new Phrase((String)tmp.get(4), fontItem));
            }
            document.add(datatable);
            
        }
        catch(DocumentException de) 
        {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }
        
        document.close();
        
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();  
        baos.writeTo(out);
        out.flush();
    }
}