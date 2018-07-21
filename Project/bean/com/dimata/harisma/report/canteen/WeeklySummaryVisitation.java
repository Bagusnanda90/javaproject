/*
 * WeeklySummaryVisitation.java
 * @author rusdianta
 * Created on January 21, 2005, 2:10 PM
 */

package com.dimata.harisma.report.canteen;

/* --- use internal java class --- */
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.text.*;

/* --- use internal java servlet class --- */
import javax.servlet.*;
import javax.servlet.http.*;

/* --- use lowagie pdf class --- */
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/* --- use harisma session class --- */
import com.dimata.harisma.session.canteen.*;

public class WeeklySummaryVisitation extends HttpServlet {
    
    /* --- set of colors that used in this class --- */
    public static Color blackColor = Color.BLACK;
    public static Color whiteColor = Color.WHITE;
    public static Color summaryColor = new Color(240, 240, 240);
    
    /* --- set of used fonts --- */
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContentBold = new Font(Font.TIMES_NEW_ROMAN, 9, Font.BOLD, blackColor); 
    
    /** Creates a new instance of WeeklySummaryVisitation */
    public WeeklySummaryVisitation() {
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public Table getTableHeader(byte firstDate,
                                byte numOfDay) throws BadElementException, DocumentException
    {
        /* --- number of column in the table --- */
        int numSubCell = numOfDay;
                
        /* --- draw a table and set its properties --- */
        Table table = new Table(numSubCell + 3);
        table.setCellpadding(1);
        table.setCellspacing(1);
        table.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        
        float columnWidth[] = new float [numSubCell + 3];
        columnWidth[0] = 5;
        columnWidth[1] = 38;
        columnWidth[numSubCell + 2] = 8;        
        float totalWidth = 51f;
        float defColWidth = 7f;
        defColWidth = defColWidth * 7 / numOfDay;
        for (int item = 0; item < numSubCell; item++) {
            columnWidth[item + 2] = defColWidth;
            totalWidth += defColWidth;
        }
        
        table.setWidths(columnWidth);
        table.setWidth(totalWidth);
        table.setAlignment(Table.ALIGN_LEFT);
        
        /* --- draw first cell --- */ 
        Cell cell = new Cell(new Chunk("NO.", fontContent));
        cell.setRowspan(2);
        cell.setColspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);        
        table.addCell(cell);
        
        /* --- draw "department" cell --- */
        cell = new Cell(new Chunk("DEPARTMENT", fontContent));
        cell.setRowspan(2);
        cell.setColspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
                
        /* --- draw super column "Visits per Date" --- */
        cell = new Cell(new Chunk("VISITS (PER DATE)", fontContent));
        cell.setRowspan(1);
        cell.setColspan(numSubCell);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        /* --- draw "Total" column --- */
        cell = new Cell(new Chunk("TOTAL", fontContent));
        cell.setRowspan(2);
        cell.setColspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        byte date;
        
        /* --- draw  sub-cell of "Visits per Date" column --- */
        for (int index = 0; index < numOfDay; index++) {
            date = (byte) (firstDate + index);
            
            cell = new Cell(new Chunk(String.valueOf(date), fontContent));
            cell.setRowspan(1);
            cell.setColspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);
        }
        
        return table;
    }
    
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException
    {
        /* --- create a document object --- */
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        
        /* --- create an output stream --- */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
            
            /* --- get session data that reference from jsp page --- */
            HttpSession session = request.getSession(true);
            Vector sessData = null;
            try {
                sessData = (Vector) session.getValue("CANTEEN_WEEKLY_SUMMARY_DATA");
            } catch (Exception error) {
                System.out.println("Exception : " + error.toString());
            }
            
            /* --- build a title on document --- */
            Date firstDay = (Date) sessData.get(0);
            GregorianCalendar gregCal = new GregorianCalendar(firstDay.getYear() + 1900, firstDay.getMonth(), firstDay.getDate());
            byte actualWeek = (byte) gregCal.get(gregCal.WEEK_OF_MONTH);
            String WEEKS[] = {"1st", "2nd", "3rd", "4th", "5th", "6th"};
            String months[] = new DateFormatSymbols().getMonths();
            String period_name = WEEKS[actualWeek - 1] + " WEEK OF " + (months[gregCal.get(gregCal.MONTH)]).toUpperCase() + " " + gregCal.get(gregCal.YEAR);
            String title = "CANTEEN SUMMARY REPORT\nPERIOD : " + period_name;
            
            /* --- build header for document --- */
            HeaderFooter header = new HeaderFooter(new Phrase(title, fontTitle), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            /* --- open document to add some data --- */
            document.open();
            
            byte firstDate = (byte) firstDay.getDate();
            byte actualDay = (byte) gregCal.get(gregCal.DAY_OF_WEEK);
            byte lastDate = (byte) (firstDate + 7 - actualDay);
            
            byte maxDate = (byte) gregCal.getActualMaximum(gregCal.DAY_OF_MONTH);
            if (lastDate > maxDate)
                lastDate = maxDate;
            byte numOfDay = (byte) (lastDate + 1 - firstDate);
            
            //Vector periodDate = (Vector) sessData.get(1);
            //int numOfDay = periodDate.size();
            
            Table table = getTableHeader(firstDate, numOfDay);
            
            /* --- data processing --- */
            Vector summaryData = (Vector) sessData.get(1);
            int dataSize = summaryData.size();
            
            Cell cell;
            int numVisits = 0;
            byte date;
            int totalPerDepartment = 0;
            int valuePerDay[] = {0, 0, 0, 0, 0, 0, 0};
            int totalPerDay[] = {0, 0, 0, 0, 0, 0, 0};
            int numOfDepartment = 0;
            String departmentName = "";
            int total = 0;
                
            /*Vector data;
            String S;
            */
            
            for (int item = 0; item < dataSize; item++) {
                MonthlySummary monthlySummary = (MonthlySummary) summaryData.get(item);
                totalPerDepartment = 0;
                    
                for (byte index = 0; index < numOfDay; index++) {
                    date = (byte) (firstDate + index);
                    
                    switch (date) {
                    case 1 : numVisits = monthlySummary.getDate1();
                    break;
                    case 2 : numVisits = monthlySummary.getDate2();
                    break;
                    case 3 : numVisits = monthlySummary.getDate3();
                    break;
                    case 4 : numVisits = monthlySummary.getDate4();
                    break;
                    case 5 : numVisits = monthlySummary.getDate5();
                    break;
                    case 6 : numVisits = monthlySummary.getDate6();
                    break;
                    case 7 : numVisits = monthlySummary.getDate7();
                    break;
                    case 8 : numVisits = monthlySummary.getDate8();
                    break;
                    case 9 : numVisits = monthlySummary.getDate9();
                    break;
                    case 10 : numVisits = monthlySummary.getDate10();
                    break;
                    case 11 : numVisits = monthlySummary.getDate11();
                    break;
                    case 12 : numVisits = monthlySummary.getDate12();
                    break;
                    case 13 : numVisits = monthlySummary.getDate13();
                    break;
                    case 14 : numVisits = monthlySummary.getDate14();
                    break;
                    case 15 : numVisits = monthlySummary.getDate15();
                    break;
                    case 16 : numVisits = monthlySummary.getDate16();
                    break;
                    case 17 : numVisits = monthlySummary.getDate17();
                    break;
                    case 18 : numVisits = monthlySummary.getDate18();
                    break;
                    case 19 : numVisits = monthlySummary.getDate19();
                    break;
                    case 20 : numVisits = monthlySummary.getDate20();
                    break;
                    case 21 : numVisits = monthlySummary.getDate21();
                    break;
                    case 22 : numVisits = monthlySummary.getDate22();
                    break;
                    case 23 : numVisits = monthlySummary.getDate23();
                    break;
                    case 24 : numVisits = monthlySummary.getDate24();
                    break;
                    case 25 : numVisits = monthlySummary.getDate25();
                    break;
                    case 26 : numVisits = monthlySummary.getDate26();
                    break;
                    case 27 : numVisits = monthlySummary.getDate27();
                    break;
                    case 28 : numVisits = monthlySummary.getDate28();
                    break;
                    case 29 : numVisits = monthlySummary.getDate29();
                    break;
                    case 30 : numVisits = monthlySummary.getDate30();
                    break;
                    case 31 : numVisits = monthlySummary.getDate31();                            
                    }
                        
                    valuePerDay[index] = numVisits;
                    totalPerDepartment += numVisits; 
                }
                    
                if (totalPerDepartment > 0) {
                    numOfDepartment++;
                        
                    cell = new Cell(new Chunk(String.valueOf(numOfDepartment) + " ", fontContent));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                        
                    departmentName = monthlySummary.getDepartmentName();                    
                        
                    cell = new Cell(new Chunk(" " + departmentName, fontContent));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                        
                    for (byte index = 0; index < numOfDay; index++) {
                        numVisits = valuePerDay[index];
                            
                        cell = new Cell(new Chunk(String.valueOf(numVisits) + " ", fontContent));
                        cell.setColspan(1);
                        cell.setRowspan(1);
                        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        table.addCell(cell);
                    }   
                        
                    cell = new Cell(new Chunk(String.valueOf(totalPerDepartment) + " ", fontContent));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                }
                    
                if (!pdfWriter.fitsPage(table)) {
                    table.deleteLastRow();
                    item--;
                    document.add(table);
                    document.newPage();
                    table = getTableHeader(firstDate, numOfDay);
                } else {
                    for (byte index = 0; index < numOfDay; index++) 
                        totalPerDay[index] += valuePerDay[index];
                    total += totalPerDepartment;
                }
            }
                
            cell = new Cell(new Chunk("TOTAL", fontContentBold));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
                
            for (byte index = 0; index < numOfDay; index++) {
                cell = new Cell(new Chunk(String.valueOf(totalPerDay[index]) + " ", fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
            } 
                
            cell = new  Cell(new Chunk(String.valueOf(total) + " ", fontContent));
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
        
            document.add(table);
        } catch (DocumentException error) {
            System.err.println(error.getMessage());
            error.printStackTrace();
        }
        
        document.close();
        
        /* --- build pdf stream --- */
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
