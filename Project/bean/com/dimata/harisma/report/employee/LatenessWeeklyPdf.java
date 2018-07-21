package com.dimata.harisma.report.employee;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.Date;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.PstDepartment;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

public class LatenessWeeklyPdf extends HttpServlet {
	/* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    }

    public static String getStrWeek(int idx){
        String str = "";
        switch(idx){
            case 1:
                str = "WEEK I";
                break;
            case 2:
                str = "WEEK II";
                break;
            case 3:
                str = "WEEK III";
                break;
            case 4:
                str = "WEEK IV";
                break;
            case 5:
                str = "WEEK V";
                break;
            case 6:
                str = "WEEK VI";
                break;
            case 7:
                str = "WEEK VII";
                break;
        }
        return str;
    }

    public static Table createHeaderDetail(Font font1, Date dt, Date toDt){
        System.out.println("= = >> CREATE HEADER DETAIL ...");
        Table datatable = null;
        try{
            int max = toDt.getDate() - dt.getDate();
            int headerwidths[] = new int[4+(max+1)];
            headerwidths[0] = 5;
            headerwidths[1] = 10;
            headerwidths[2] = 20;
            int index = 3;
            for(int k=0;k<=max;k++){
                headerwidths[index] = 5;
                index++;
            }
            headerwidths[index] = 8;

            // create table
            datatable = new Table(4+(max+1));
            datatable.setPadding(1);
            datatable.setSpacing(1);
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultCellBackgroundColor(summaryColor);
            datatable.addCell(new Phrase("NO", font1));
            datatable.addCell(new Phrase("PAYROLL", font1));
            datatable.addCell(new Phrase("EMPLOYEE", font1));

            datatable.setDefaultColspan(max+1);
            datatable.setDefaultRowspan(1);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("DATE LATE (HOUR,MINUTES)", font1));

            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("TOTAL LATE", font1));

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
        System.out.println("= = = | Employee Weekly Lateness | = = =");

        HttpSession session = request.getSession(true);
        Vector temp = (Vector)session.getValue("WEEKLY_LATENESS");

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
            Vector list = (Vector)temp.get(5);
            long oidDept = 0;
            if(Long.parseLong((String)temp.get(1))==0){
                for (int i = 0; i < list.size(); i++) {
                    Vector tmp = (Vector)list.get(i);
                    oidDept = Long.parseLong((String)tmp.get(0));
                    break;
                }
            }else{
                oidDept = Long.parseLong((String)temp.get(1));
            }
            Department dep = new Department();
            try{
                dep = PstDepartment.fetchExc(oidDept);
            }catch(Exception e){}
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("LATENESS REPORT " + getStrWeek(Integer.parseInt(""+temp.get(0))).toUpperCase() + " OF "+Formater.formatDate((Date)temp.get(2),"MMMM yyyy").toUpperCase() +
                                  "\nDEPARTMENT : "+ dep.getDepartment().toUpperCase(), fontHeader), false);

            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            document.open();

            Date startDate = (Date)temp.get(3);
            Date endDate = (Date)temp.get(4);
            //document.add(createHeader(temp,font1,oidDept));
            Table datatable = createHeaderDetail(fontContent,startDate,endDate);
            //datatable.setDefaultCellBorderWidth(1);
            datatable.setDefaultRowspan(1);
            datatable.setDefaultCellBackgroundColor(whiteColor);

            System.out.println("= = >> CREATE DATA DETAIL ...");
            long oid = 0;
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
                    datatable.addCell(new Phrase((String)tmp.get(1), fontContent));
                    datatable.addCell(new Phrase((String)tmp.get(2), fontContent));
                    datatable.addCell(new Phrase((String)tmp.get(3), fontContent));
                }else{
                    datatable.setDefaultColspan(3);
                    datatable.addCell(new Phrase((String)tmp.get(3), fontContent));
                    datatable.setDefaultColspan(1);
                }
                int idx = 4;
                for(int k=startDate.getDate();k<=endDate.getDate();k++){
                    datatable.addCell(new Phrase((String)tmp.get(idx), fontContent));
                    idx++;
                }
                datatable.addCell(new Phrase((String)tmp.get(idx), fontContent));
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
}