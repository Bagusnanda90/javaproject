/*
 * DailySummaryVisitation.java
 * @author  rusdianta
 * Created on January 21, 2005, 9:49 AM
 */

package com.dimata.harisma.report.canteen;

/* --- internal java package --- */
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.text.DateFormat;

/* --- servlet package --- */
import javax.servlet.*;
import javax.servlet.http.*;

/* --- lowagie pdf package --- */
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/* --- harisma package --- */
import com.dimata.harisma.session.canteen.SummaryDailyVisitation;
import com.dimata.harisma.session.canteen.SessCanteenVisitation;
import com.dimata.harisma.entity.canteen.CanteenSchedule;

public class DailySummaryVisitation extends HttpServlet {
    
    /* --- used colors --- */
    public static Color blackColor = new Color(0, 0, 0);
    public static Color summaryColor = new Color(240, 240, 240);
    /* --- used fonts --- */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContentBold = new Font(Font.TIMES_NEW_ROMAN, 9, Font.BOLD, blackColor);
        
    /** Creates a new instance of DailySummaryVisitation */
    public DailySummaryVisitation() {
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);        
    }
    
    /* --- create header of table --- */
    public static Table getTableHeader(int rpType, Vector vSchedule) throws BadElementException, DocumentException {
        
        /* --- drawing a table with its properties --- */
        Table table = null;
        int columnWidth[] = null;
        if(rpType==SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON){
            table = new Table(3 + vSchedule.size());
            columnWidth = new int[3 + vSchedule.size()];
        }else{
            table = new Table(3 + 1);
            columnWidth = new int[3 + 1];
        }
        table.setCellpadding(1);
        table.setCellspacing(1);
        table.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

        // set width colom report
        if(rpType==SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON){
            columnWidth[0] = 4;
            columnWidth[1] = 42;
            columnWidth[columnWidth.length-1] = 8;
            for (int i = 0; i < vSchedule.size(); i++) {
                columnWidth[i+2] = 23;
            }
        }else{
            columnWidth[0] = 4;
            columnWidth[1] = 42;
            columnWidth[2] = 23;
            columnWidth[3] = 8;
        }
        table.setWidths(columnWidth);
        table.setWidth(100);
        table.setAlignment(Table.ALIGN_LEFT);
                
        /* --- draw a first column header --- */
        Cell cell = new Cell(new Chunk("NO.", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        /* --- draw a second column header --- */
        cell = new Cell(new Chunk("OUTLET", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);

        if(rpType==SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON){
            for (int i = 0; i < vSchedule.size(); i++) {
                CanteenSchedule objCanteenSchedule = (CanteenSchedule) vSchedule.get(i);
                cell = new Cell(new Chunk(objCanteenSchedule.getSName().toUpperCase(), fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setBackgroundColor(summaryColor);
                table.addCell(cell);
            }
        }else{
            cell = new Cell(new Chunk("NIGHT", fontContent));
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);
        }

        cell = new Cell(new Chunk("TOTAL", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);

        return table;
    }
    
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException
    {
        /* --- creating a document object --- */
        Document document = new Document(PageSize.A4, 50, 30, 50, 50);
            
        /* --- creating an output stream --- */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();        
        
        try {
            /* --- creating an instant of PdfWriter --- */
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
            
            /* --- get data from session --- */
            HttpSession sessData = request.getSession(true);
            Vector dailySummaryData = null;
            try {
                dailySummaryData = (Vector) sessData.getValue("CANTEEN_DAILY_SUMMARY_DATA");
            } catch (Exception error) {
                System.out.println("Exception : " + error.toString());
            }
            
            /* --- get string of period --- */
            Date periodDate = (Date) dailySummaryData.get(0);
            DateFormat dateFormat = DateFormat.getDateInstance(java.text.DateFormat.LONG, Locale.US);
            String periodTitle = dateFormat.format(periodDate);
            periodTitle = periodTitle.toUpperCase();


            String strDepartement = (String) dailySummaryData.get(2);
            String strSection = (String) dailySummaryData.get(5);
            Vector vtSchedule = (Vector) dailySummaryData.get(3);
            int rpType = Integer.parseInt((String) dailySummaryData.get(4));
            String str = (String) dailySummaryData.get(6);

            String strReportTitle = "MEAL RECORD";
            if(strDepartement.length()>0)
                strReportTitle = strReportTitle + "\nDEPARTMENT : "+strDepartement;
            else
                strReportTitle = strReportTitle + "\nALL DEPARTMENT";

            if(strSection.length()>0)
                strReportTitle = strReportTitle + "\nOUTLET : "+strSection;

            strReportTitle = strReportTitle + "\n"+periodTitle+" "+str;

            /* --- create a header for document --- */
            HeaderFooter header = new HeaderFooter(new Phrase(strReportTitle, fontHeader), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            /* --- open document to add data --- */
            document.open();
                        
            /* --- get a table header --- */
            Table table = getTableHeader(rpType,vtSchedule);
            
            Vector visitsData = (Vector) dailySummaryData.get(1);    
            int sizeOfVisitsData = visitsData.size();
            
            /* --- data processing --- */
            String departmentName = "";
            int totalVisits = 0;
            Cell cell;
            int totalSub[] = null;
            int valuesSub[] = null;
            if(rpType==SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON){
                totalSub = new int[vtSchedule.size()];
                valuesSub = new int[vtSchedule.size()];
            }else{
                totalSub = new int[1];
                valuesSub = new int[1];
            }

            for (int i = 0; i < sizeOfVisitsData; i++) {
                    
                SummaryDailyVisitation data = (SummaryDailyVisitation) visitsData.get(i);

                cell = new Cell(new Chunk(String.valueOf(i + 1) + " ", fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                    
                cell = new Cell(new Chunk(" " + data.getDepartmentName(), fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                Vector vtValues = data.getValues();
                if(vtValues.size()>0){
                    for(int j=0; j<vtValues.size();j++){
                        String values = (String)vtValues.get(j);

                        cell = new Cell(new Chunk(values+" ", fontContent));
                        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        table.addCell(cell);
                        valuesSub[j] = Integer.parseInt(values);
                    }
                }

                cell = new Cell(new Chunk(String.valueOf(data.getNumVisits()) + " ", fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                    
                if (!pdfWriter.fitsPage(table)) {
                    table.deleteLastRow();
                    i--;
                    document.add(table);
                    document.newPage();
                    table = getTableHeader(rpType,vtSchedule);
                } else {
                    for(int j=0; j<vtValues.size();j++)
                        totalSub[j] =  totalSub[j] + valuesSub[j];

                    totalVisits += data.getNumVisits();
                }   
            }
                
            cell = new Cell(new Chunk("TOTAL", fontContentBold));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            if (rpType == SessCanteenVisitation.REPORT_TYPE_DISPLAY_MORNING_AFTERNOON){
                for (int i = 0; i < vtSchedule.size(); i++) {
                    cell = new Cell(new Chunk(String.valueOf(totalSub[i]) + " ", fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                }
            }else{
                cell = new Cell(new Chunk(String.valueOf(totalSub[0]) + " ", fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
            }

            cell = new Cell(new Chunk(String.valueOf(totalVisits) + " ", fontContent));
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            
            document.add(table);
        } catch (DocumentException error) {
            System.err.println(error.getMessage());
            error.printStackTrace();
        }
        
        document.close();
        
        /* --- get adn build pdf stream --- */
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream outs = response.getOutputStream();
        baos.writeTo(outs);
        outs.flush();
    }
    
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    public void destroy() {        
    }
}
