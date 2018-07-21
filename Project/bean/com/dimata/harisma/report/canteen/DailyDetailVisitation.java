/*
 * DailyDetailVisitation.java
 * @author  rusdianta
 * Created on January 22, 2005, 11:00 AM
 */

package com.dimata.harisma.report.canteen;

/* --- internal java package --- */
import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.Color;

/* --- java servlet package --- */
import javax.servlet.*;
import javax.servlet.http.*;

/* --- lowagie pdf package --- */
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/* --- harisma package --- */
import com.dimata.harisma.session.canteen.DetailDailyVisitation;

public class DailyDetailVisitation extends HttpServlet {
    
    /* --- initialize an used fonts in this class --- */
    public static Color blackColor = Color.BLACK;
    public static Color summaryColor = new Color(240, 240, 240);
    public static Color whiteColor = Color.WHITE;
    
    /* --- used fonts --- */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContentBold = new Font(Font.TIMES_NEW_ROMAN, 9, Font.BOLD, blackColor);
        
    /** Creates a new instance of DailyDetailVisitation */
    public DailyDetailVisitation() {
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public Table getTableHeader() throws BadElementException, DocumentException {
        /* --- make and build a table --- */
        Table table = new Table(7);
        table.setBorderColor(whiteColor);
        table.setCellspacing(1);
        table.setCellpadding(1);
        table.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        float columnWidth[] = {4, 10, 20, 20, 13, 25, 8};
        table.setWidths(columnWidth);
        table.setWidth(100);
        table.setAlignment(Table.ALIGN_LEFT);
        
        /* --- build cell with title "No." --- */
        Cell cell = new Cell(new Chunk("NO.", fontContent));
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);  
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        /* --- build a cell with title "Payroll" on the table --- */
        cell = new Cell(new Chunk("PAYROLL", fontContent));
        cell.setRowspan(1);
        cell.setColspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        /* --- build a cell with title "Name" on the table --- */        
        cell = new Cell(new Chunk("NAME", fontContent));
        cell.setColspan(2);
        cell.setRowspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        cell = new Cell(new Chunk("SCHEDULE", fontContent));
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        /* --- build a cell with title "Swipe Time" on the table --- */
        cell = new Cell(new Chunk("SWEPT TIME", fontContent));
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        /* --- build a cell with title "Num Of Visits" on the table --- */
        cell = new Cell(new Chunk("VISITS", fontContent));
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        return table;
    }
    
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException 
    {
        /* --- build a document object --- */
        Document document = new Document(PageSize.A4, 30, 30, 50, 50); 
        
        /* --- create a stream object --- */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            /* --- create an instant of PdfWriter --- */
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);

            /* --- get data from session --- */
            HttpSession session = request.getSession(true);
            Vector sessData = null;
            try {
                sessData = (Vector) session.getValue("CANTEEN_DAILY_DETAIL_DATA");
            } catch (Exception error) {
                System.out.println("Exception : " + error.toString());
            }
            String departmentName = (String) sessData.get(0);
            String sectionName = (String) sessData.get(3);
            String condition = (String) sessData.get(4);
            java.util.Date periodDate = (java.util.Date) sessData.get(1);

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
            String periodTitle = dateFormat.format(periodDate);
            periodTitle = periodTitle.toUpperCase();

            //String periodTitle = (String) sessData.get(1);

            String title = "MEAL RECORD";
            if(departmentName.length()>0)
                title = title + "\nDEPARTMENT : " + departmentName;
            else
                title = title + "\nALL DEPARTMENT";

            if(sectionName.length()>0)
                title = title + "\nOUTLET : " +sectionName;

            title = title + "\nPERIOD : " + periodTitle;
            if(condition.length()>0)
                title = title +" "+condition;

            /* --- just build a header for document --- */
            HeaderFooter header = new HeaderFooter(new Phrase(title, fontHeader), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            document.open();

            Table table = getTableHeader();

            Vector data = (Vector) sessData.get(2);
            int sizeOfData = data.size();
            //System.out.println("Nilai dari size : " + size);
            /* --- start of data processing --- */
            DetailDailyVisitation detailDailyVisitation = null;
            //Vector currEmployeeData = null;
            String employeePayroll = "";
            String employeeName = "";
            Cell cell;
            int numVisits = 0;
            Vector dataOfTimeVisits = null;
            String textVisits = "";
            int totalVisits = 0;
            int totalNightVisits = 0;

            for (int j = 0; j < sizeOfData; j++) {
                //currEmployeeData = (Vector) data.get(item);
                //employeePayroll = (String) currEmployeeData.get(0);
                detailDailyVisitation = (DetailDailyVisitation) data.get(j);
                employeePayroll = detailDailyVisitation.getEmployeePayroll();

                cell = new Cell(new Chunk(String.valueOf(j + 1) + " ", fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                cell = new Cell(new Chunk(" " + employeePayroll, fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                //employeeName = (String) currEmployeeData.get(1);
                employeeName = detailDailyVisitation.getEmployeeName();

                cell = new Cell(new Chunk(" " + employeeName, fontContent));
                cell.setColspan(2);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                cell = new Cell(new Chunk(" " + detailDailyVisitation.getScheduleSymbol(), fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                cell = new Cell(new Chunk(" " + detailDailyVisitation.getStrVisitTimes(), fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                numVisits = detailDailyVisitation.getNumVisits();

                cell = new Cell(new Chunk(String.valueOf(numVisits) + " ", fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                if (!pdfWriter.fitsPage(table)) {
                    table.deleteLastRow();
                    j--;
                    document.add(table);
                    document.newPage();
                    table = getTableHeader();
                } else {
                    totalVisits = totalVisits + detailDailyVisitation.getNumVisits();
                }
            }

            cell = new Cell(new Chunk("TOTAL", fontContent));
            //cell.setBorder(Rectangle.BOTTOM);
            //cell.setBorderColor(blackColor);
            cell.setColspan(6);
            cell.setRowspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new Cell(new Chunk(String.valueOf(totalVisits) + " ", fontContent));
            //cell.setBorder(Rectangle.BOX);
            //cell.setBorderColor(blackColor);
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            /*cell = new Cell();
            cell.setBorder(Rectangle.TOP);
            //cell.setBorderColor(whiteColor);
            cell.setColspan(7);
            cell.setRowspan(1);
            table.addCell(cell);

            cell = new Cell(new Chunk("TOTAL VISITS (MORNING & AFTERNOON)", fontContent));
            cell.setBorderColor(whiteColor);
            cell.setColspan(3);
            cell.setRowspan(1);
            cell.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            table.addCell(cell);

            cell = new Cell(new Chunk(":  " + String.valueOf(totalVisits - totalNightVisits), fontContent));
            cell.setBorderColor(whiteColor);
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setVerticalAlignment(Cell.ALIGN_BOTTOM);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            table.addCell(cell);

            cell = new Cell();
            cell.setBorderColor(whiteColor);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new Cell(new Chunk("TOTAL VISITS (NIGHT)", fontContent));
            cell.setBorderColor(whiteColor);
            cell.setColspan(3);
            cell.setRowspan(1);
            cell.setVerticalAlignment(Cell.ALIGN_TOP);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            table.addCell(cell);

            cell = new Cell(new Chunk(":  " + String.valueOf(totalNightVisits), fontContent));
            cell.setBorderColor(whiteColor);
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setVerticalAlignment(Cell.ALIGN_TOP);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            table.addCell(cell);

            cell = new Cell();
            cell.setBorderColor(whiteColor);
            cell.setColspan(3);
            table.addCell(cell);*/

            document.add(table);
        } catch (DocumentException error) {
            System.err.println(error.getMessage());
            error.printStackTrace();
        }
        
        document.close();
        
        /* --- build a pdf --- */
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
