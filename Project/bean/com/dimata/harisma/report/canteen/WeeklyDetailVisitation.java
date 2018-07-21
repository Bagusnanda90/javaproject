/*
 * WeeklyDetailVisitation.java
 * @author rusdianta
 * Created on January 24, 2005, 9:44 AM
 */

package com.dimata.harisma.report.canteen;

/* --- internal java package --- */
import java.io.*;
import java.awt.Color;
import java.util.*;
import java.text.*;

/* --- use java servlet package --- */ 
import javax.servlet.*;
import javax.servlet.http.*;

/* --- use lowagie pdf package --- */
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/* --- use harisma package --- */
import com.dimata.harisma.session.canteen.MonthlyDetailVisitation;

public class WeeklyDetailVisitation extends HttpServlet {
    
    /* --- color definition that used in this class --- */
    public static final Color blackColor = new Color(0, 0, 0);
    public static final Color summaryColor = new Color(240, 240, 240);
    
    /* --- all fonts that used int this class --- */
    public static final Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static final Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static final Font fontContentBold = new Font(Font.TIMES_NEW_ROMAN, 9, Font.BOLD, blackColor);
    
    /** Creates a new instance of MonthlyDetailVisitation */
    public WeeklyDetailVisitation() {
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public Table getTableHeader(byte firstDate,
                                byte numOfDay) throws BadElementException, DocumentException
    {        
        //byte numOfDay = (byte) dayData.size();        
        byte numColumns = (byte) (numOfDay + 4);
        
        /* --- build a table for document --- */
        Table table = new Table(numColumns);
        table.setCellspacing(1);
        table.setCellpadding(1);
        table.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        table.setAlignment(Table.ALIGN_LEFT);
        
        float columnWidth[] = new float [numColumns];
        columnWidth[0] = 5;
        columnWidth[1] = 10;
        columnWidth[2] = 35;
        columnWidth[numColumns - 1] = 8;
        float totalWidth = 58;        
        float defColWidth = 6f;
        //if (numOfDay < 4)
        defColWidth = defColWidth * 7 / numOfDay;        
        for (byte day = 0; day < numOfDay; day++) {
            columnWidth[day + 3] = defColWidth;
            totalWidth += defColWidth;
        }
        
        table.setWidths(columnWidth);
        table.setWidth(totalWidth);
        
        /* --- just build all cell in the table --- */
        Cell cell = new Cell(new Chunk("NO.", fontContent));
        cell.setColspan(1);
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        cell = new Cell(new Chunk("PAYROLL", fontContent));
        cell.setColspan(1);
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        cell = new Cell(new Chunk("NAME", fontContent));
        cell.setColspan(1);
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        cell = new Cell(new Chunk("VISITS (PER DATE)", fontContent));
        cell.setColspan(numOfDay);
        cell.setRowspan(1);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        cell = new Cell(new Chunk("TOTAL", fontContent));
        cell.setColspan(1);
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        /* --- build a subcell that contains all day that exist in a period of a week --- */
        for (byte day = 0; day < numOfDay; day++) {
            cell = new Cell(new Chunk(String.valueOf(firstDate + day), fontContent));
            cell.setColspan(1);
            cell.setRowspan(1);
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
        /* --- create a document with A4 papersize --- */
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        
        /* --- create an output stream --- */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {        
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
            
            /* --- get data from session --- */
            HttpSession session = request.getSession(true);
            Vector sessData = null;
            try {
                sessData = (Vector) session.getValue("CANTEEN_WEEKLY_DETAIL_DATA");
            } catch (Exception error) {
                System.out.println("Exception : " + error.toString());
            }
            
            String departmentName = (String) sessData.get(0);
            String sectionName = (String) sessData.get(3);
            String condition = (String) sessData.get(4);
            Date firstDay = (Date) sessData.get(1);
            
            GregorianCalendar gregCal = new GregorianCalendar(firstDay.getYear() + 1900, firstDay.getMonth(), firstDay.getDate());
            byte actualWeek = (byte) gregCal.get(gregCal.WEEK_OF_MONTH);
            String WEEKS[] = {"1st", "2nd", "3rd", "4th", "5th", "6th"};
            String months[] = new DateFormatSymbols().getMonths();            
            String periodName = WEEKS[actualWeek - 1] + " WEEK OF " + (months[gregCal.get(gregCal.MONTH)]).toUpperCase() + " " + gregCal.get(gregCal.YEAR);            
            String title = "CANTEEN WEEKLY DETAIL REPORT\nDEPARTMENT : " + departmentName;

            if(sectionName.length()>0)
                title = title + "\nSECTION : " +sectionName;

            title = title + "\nPERIOD : " + periodName;
            if(condition.length()>0)
                title = title +" "+condition;

            /* --- just build a header for document --- */
            HeaderFooter header = new HeaderFooter(new Phrase(title, fontHeader), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);            
            document.open();
            
            byte firstDate = (byte) firstDay.getDate();
            byte actualDay = (byte) gregCal.get(gregCal.DAY_OF_WEEK);
            byte lastDate = (byte) (firstDate + 7 - actualDay);            
            byte maxDate = (byte) gregCal.getActualMaximum(gregCal.DAY_OF_MONTH);
            if (lastDate > maxDate)
                lastDate = maxDate;            
            byte numOfDay = (byte) (lastDate + 1 - firstDate);
            
            //Vector dayData = (Vector) sessData.get(2);
            // System.out.println("Nilai dari dayData = " + dayData);
             
            Table table = getTableHeader(firstDate, numOfDay);
            
            Vector allVisitsData = (Vector) sessData.get(2); 
            int numOfRecord = allVisitsData.size();
            
            //System.out.println("Nilai dari allVisitsData = " + allVisitsData);
            //System.out.println("Nilai dari numOfEmployee = " + numOfEmployee);
            
            byte index;
            byte date;                
            int value = 0;                
            boolean useRecord;                
            int valuePerDay[] = {0, 0, 0, 0, 0, 0, 0};                
            int numOfEmployee = 0;
            Cell cell;
            String employeePayroll;
            String employeeName;
            byte day;
            int totalVisitsPerEmployee;
            int total = 0;
            int totalPerDay[] = {0, 0, 0, 0, 0, 0, 0};
                
            /*
            Vector employeeData;
            byte numOfDay = (byte) dayData.size();
            int numVisits;
            for (day = 0; day < numOfDay; day++)
            totalPerDay[day] = 0; */
                
            for (int item = 0; item < numOfRecord; item++) {
                MonthlyDetailVisitation mdv = (MonthlyDetailVisitation) allVisitsData.get(item);
                useRecord = false;
                    
                for (index = 0; index < numOfDay; index++) {                        
                    date = (byte) (firstDate + index);
                    
                    switch (date) {
                    case 1 : value = mdv.getMa1();
                    break;
                    case 2 : value = mdv.getMa2();
                    break;
                    case 3 : value = mdv.getMa3();
                    break;
                    case 4 : value = mdv.getMa4();
                    break;
                    case 5 : value = mdv.getMa5();
                    break;
                    case 6 : value = mdv.getMa6();
                    break;
                    case 7 : value = mdv.getMa7();
                    break;
                    case 8 : value = mdv.getMa8();
                    break;
                    case 9 : value = mdv.getMa9();
                    break;
                    case 10 : value = mdv.getMa10();
                    break;
                    case 11 : value = mdv.getMa11();
                    break;
                    case 12 : value = mdv.getMa12();
                    break;
                    case 13 : value = mdv.getMa13();
                    break;
                    case 14 : value = mdv.getMa14();
                    break;
                    case 15 : value = mdv.getMa15();
                    break;
                    case 16 : value = mdv.getMa16();
                    break;
                    case 17 : value = mdv.getMa17();
                    break;
                    case 18 : value = mdv.getMa18();
                    break;
                    case 19 : value = mdv.getMa19();
                    break;
                    case 20 : value = mdv.getMa20();
                    break;
                    case 21 : value = mdv.getMa21();
                    break;
                    case 22 : value = mdv.getMa22();
                    break;
                    case 23 : value = mdv.getMa23();
                    break;
                    case 24 : value = mdv.getMa24();
                    break;
                    case 25 : value = mdv.getMa25();
                    break;
                    case 26 : value = mdv.getMa26();
                    break;
                    case 27 : value = mdv.getMa27();
                    break;
                    case 28 : value = mdv.getMa28();
                    break;
                    case 29 : value = mdv.getMa29();
                    break;
                    case 30 : value = mdv.getMa30();
                    break;
                    case 31 : value = mdv.getMa31();
                    }
                        
                    valuePerDay[index] = value;
                    if (value > 0) 
                        useRecord = true;
                }
                if (useRecord) {
                    numOfEmployee++;
                        
                    cell = new Cell(new Chunk(String.valueOf(numOfEmployee) + " ", fontContent));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                       
                    employeePayroll = mdv.getEmployeePayroll();
                    
                    cell = new Cell(new Chunk(" " + employeePayroll, fontContent));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                        
                    employeeName = mdv.getEmployeeName();
                    
                    cell = new Cell(new Chunk(" " + employeeName, fontContent));
                    cell.setColspan(1);
                    cell.setRowspan(1);                    
                    cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                        
                    totalVisitsPerEmployee = 0;
                    
                    for (day = 0; day < numOfDay; day++) {
                        //numVisits = Integer.parseInt((String) employeeData.get(day + 2));
                        value = valuePerDay[day];
                        totalVisitsPerEmployee += value;                            
                        
                        cell = new Cell(new Chunk(String.valueOf(value) + " ", fontContent));
                        cell.setColspan(1);
                        cell.setRowspan(1);
                        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        table.addCell(cell);
                    }     
                        
                    cell = new Cell(new Chunk(String.valueOf(totalVisitsPerEmployee) + " ", fontContent));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                        
                    if (!pdfWriter.fitsPage(table)) {
                        table.deleteLastRow();
                        item--;
                        document.add(table);
                        document.newPage();
                        table = getTableHeader(firstDate, numOfDay);                        
                    } else {
                        for (day = 0; day < numOfDay; day++) 
                            totalPerDay[day] += valuePerDay[day]; 
                        //numVisits = Integer.parseInt((String) employeeData.get(day + 2));
                        total += totalVisitsPerEmployee;                    
                    }    
                    //employeeData = (Vector) allVisitsData.get(item);
                    // totalVisitsPerEmployee = 0;
                }
            }
            
            cell = new Cell(new Chunk("TOTAL", fontContentBold));
            cell.setColspan(3);
            cell.setRowspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
                
            for (day = 0; day < numOfDay; day++) {
                cell = new Cell(new Chunk(String.valueOf(totalPerDay[day]) + " ", fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
            }
                
            cell = new Cell(new Chunk(String.valueOf(total) + " ", fontContent));
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
        
        /* --- build a pdf from stream --- */
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
