/*
 * EarlyMonthlyPdf.java
 *
 * Created on January 2, 2008, 3:16 PM
 */

package com.dimata.harisma.report.employee;

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

/**
 *
 * @author  yunny
 */
public class EarlyMonthlyPdf  extends HttpServlet{
    
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";
    
    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 7, Font.NORMAL, blackColor);

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    }
    
    
    public static Table createHeaderDetail(Font font1, Date dt, Date toDt)
    {        
        Table datatable = null;
        try{
            int max = toDt.getDate() - dt.getDate();
            //float headerwidths[] = new float[4+(max+1)];
            float headerwidths[] = new float[3+(max+1)]; // karena payrollnya dihilangkan
            headerwidths[0] = 2.5f;
            //headerwidths[1] = 5;
            headerwidths[1] = 14;
            //int index = 3;
            int index = 2;
            for(int k=0;k<=max;k++)
            {
                headerwidths[index] = 3;
                index++;
            }
            headerwidths[index] = 3;

            // create table
            //datatable = new Table(4+(max+1));
            datatable = new Table(3+(max+1));
            datatable.setPadding(1);
            datatable.setSpacing(2);
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultCellBackgroundColor(summaryColor);
            datatable.addCell(new Phrase("No", font1));
            //datatable.addCell(new Phrase("Payroll", font1));
            datatable.addCell(new Phrase("Employee", font1));

            datatable.setDefaultColspan(max+1);
            datatable.setDefaultRowspan(1);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Duration (hour, minutes)", font1));

            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Total", font1));

            for(int k=dt.getDate();k<=toDt.getDate();k++){
                datatable.setDefaultColspan(1);
                datatable.setDefaultRowspan(1);
                datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                datatable.addCell(new Phrase(String.valueOf(k), font1));
            }

            // this is the end of the table header
            datatable.endHeaders();
        }catch(Exception e){}
        return datatable;
    }
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
            //System.out.println("= = = | Employee Monthly Lateness | = = =");

        HttpSession session = request.getSession(true);
        Vector temp = (Vector)session.getValue("MONTHLY_EARLY");

        // creating some content that will be used frequently
        Paragraph newLine = new Paragraph("\n", fontTitle);
        // step1: creating the document object
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 50, 50);
        
        // step2.1: creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);
            // step 3.1: adding some metadata to the document
            document.addSubject("This is a report.");
            // step 3.4: opening the document

            // create header
            Vector list = (Vector)temp.get(3);
            long oidDept = 0;
            if(Long.parseLong((String)temp.get(0))==0){
                for (int i = 0; i < list.size(); i++) {
                    Vector tmp = (Vector)list.get(i);
                    oidDept = Long.parseLong((String)tmp.get(0));
                    break;
                }
            }else{
                oidDept = Long.parseLong((String)temp.get(0));
            }
            Date startDate = (Date)temp.get(1);
            Department dep = new Department();
            try{
                dep = PstDepartment.fetchExc(oidDept);
            }catch(Exception e){}
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("EARLY HOME REPORT PER " + (Formater.formatDate(startDate,"MMMM yyyy").toUpperCase()) +
                				  "\nDEPARTMENT : "+ dep.getDepartment().toUpperCase(), fontHeader), false);

            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            document.open();

            Date endDate = (Date)temp.get(2);
            Table datatable = createHeaderDetail(fontContent,startDate,endDate);
            datatable.setDefaultRowspan(1);
            datatable.setDefaultCellBackgroundColor(whiteColor);

            //System.out.println("= = >> CREATE DATA DETAIL ...");
            long oid = 0;
            Cell cellEmpData = null;
            for (int i = 0; i < list.size(); i++) {
                Vector tmp = (Vector)list.get(i);
                long oidDep = Long.parseLong((String)tmp.get(0));
                if(i==0)
                    oid = oidDep;

                /*if(oidDep!=oid){
                    document.add(datatable);
                    oid = oidDep;
                    document.newPage();
                    document.add(createHeader(temp,font1,oidDep));
                    datatable = createHeaderDetail(font2a,startDate,endDate);
                    datatable.setDefaultCellBorderWidth(1);
                    datatable.setDefaultRowspan(1);
                }*/
                datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
                if(oidDep!=-1){
                    cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    datatable.addCell(cellEmpData);

                    /*
                    cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    datatable.addCell(cellEmpData);    
                     */

                    cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(3)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    datatable.addCell(cellEmpData);
                }else{
                    //datatable.setDefaultColspan(3);
                    cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(3)),fontContent));
                    //cellEmpData.setColspan(3);
                    cellEmpData.setColspan(2);
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    datatable.addCell(cellEmpData);
                    //datatable.setDefaultColspan(1);
                }

                int idx = 4;                
                for(int k=startDate.getDate();k<=endDate.getDate();k++){
                    cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(idx)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    datatable.addCell(cellEmpData);
                    //datatable.addCell(new Phrase((String)tmp.get(idx), fontContent));
                    idx++;
                } 

                cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(idx)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                datatable.addCell(cellEmpData);
                //datatable.addCell(new Phrase((String)tmp.get(idx), fontContent));
            }
            document.add(datatable);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
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

    
    /** Creates a new instance of EarlyMonthlyPdf */
    public EarlyMonthlyPdf() {
    }
    
}
