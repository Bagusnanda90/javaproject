/*
 * MonthlyDetailVisitation.java
 * @author rusdianta
 * Created on January 25, 2005, 11:42 AM
 */

package com.dimata.harisma.report.canteen;

/* --- use internal java package --- */
import java.io.*;
import java.awt.Color;
import java.util.Vector;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.DateFormatSymbols;

/* --- use java servlet package --- */
import javax.servlet.*;
import javax.servlet.http.*;

/* --- use lowagie pdf package --- */
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.dimata.harisma.session.canteen.MonthlyDetailVisitation;

public class MonthlyReportDetailVisitation extends HttpServlet {
    
    /* --- definitions of used color --- */
    public static final Color blackColor = new Color(0, 0, 0);
    public static final Color summaryColor = new Color(240, 240, 240);
    
    /* --- definitions of used color --- */
    public static final Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static final Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor); 
    public static final Font fontContentBold = new Font(Font.TIMES_NEW_ROMAN, 9, Font.BOLD, blackColor);
    
    /* --- landscape of A4 paper --- */
    public static final Rectangle A4L = new Rectangle(842, 595);        
    
    /** Creates a new instance of MonthlyDetailVisitation */
    public MonthlyReportDetailVisitation() {
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public Table getTableHeader(byte numOfDay) throws BadElementException, DocumentException {
        
        byte numColumn = (byte) (numOfDay + 4);
        //System.out.println("==>> colom = "+numColumn);

        /* --- just build a table --- */
        Table table = new Table(numColumn);
        table.setCellspacing(1);
        table.setCellpadding(1);
        table.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        float columnWidth[] = new float [numColumn];
        try{
            columnWidth[0] = 2.5f;
            columnWidth[1] = 6;
            columnWidth[2] = 12;
            columnWidth[numColumn - 1] = 3.5f;
            float totalWidth = 24.5f;

            for (byte day = 0; day < numOfDay; day++) {
                columnWidth[day + 3] = 2.44f;
                totalWidth += 2.44f;
            }

            table.setWidths(columnWidth);
            table.setWidth(totalWidth);
            table.setAlignment(Table.ALIGN_LEFT);

            /* --- make a cell with title "No." --- */
            Cell cell = new Cell(new Chunk("NO.", fontContent));
            cell.setColspan(1);
            cell.setRowspan(2);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);

            /* --- make a cell with title "Payroll" --- */
            cell = new Cell(new Chunk("PAYROLL", fontContent));
            cell.setColspan(1);
            cell.setRowspan(2);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);

            /* --- make a cell with title "Name" --- */
            cell = new Cell(new Chunk("NAME", fontContent));
            cell.setColspan(1);
            cell.setRowspan(2);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);

            /* --- make a cell with title "Number Of Visits" --- */
            cell = new Cell(new Chunk("VISITS", fontContent));
            cell.setColspan(numOfDay);
            cell.setRowspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);

            /* --- make a cell with title "Total" --- */
            cell = new Cell(new Chunk("TOTAL", fontContent));
            cell.setColspan(1);
            cell.setRowspan(2);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);

            /* --- make a subcell that contains date in a current period --- */
            for (byte day = 1; day <= numOfDay; day++) {
                cell = new Cell(new Chunk(String.valueOf(day), fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cell.setBackgroundColor(summaryColor);
                table.addCell(cell);
            }
        }catch(Exception e){
            System.out.println("Error : "+e.toString());
        }
        return table;
    }
    
    public void processRequest(HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException
    {
        /* --- create a document object --- */
        Document document = new Document(A4L, 30, 30, 50, 50);
        
        /* --- create an output stream --- */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {            
            
            /* --- create an instant of PdfWriter --- */
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
            
            /* --- get data from session --- */
            HttpSession session = request.getSession(true);
            Vector sessionData = null;
            try {
                sessionData = (Vector) session.getValue("CANTEEN_MONTHLY_DETAIL_DATA");
            } catch (Exception error) {
                System.out.println("Exception : " + error.toString());
            }
            
            String departmentName = (String) sessionData.get(0);
            //String periodName = (String) sessionData.get(1);
            String sectionName = (String) sessionData.get(4);
            String condition = (String) sessionData.get(5);

            Date firstDay = (Date) sessionData.get(1);
            GregorianCalendar gregCal = new GregorianCalendar(firstDay.getYear() + 1900, firstDay.getMonth(), firstDay.getDate());
            String months[] = new DateFormatSymbols().getMonths();
            String periodName = (months[gregCal.get(gregCal.MONTH)]).toUpperCase() + " " + gregCal.get(gregCal.YEAR);

            String title = "CANTEEN MONTHLY REPORT\nDEPARTMENT : " + departmentName;
            if(sectionName.length()>0)
                title = title + "\nOUTLET : " +sectionName;

            title = title + "\nPERIOD : " + periodName;
            if(condition.length()>0)
                title = title +" "+condition;

            /* --- create a header for document --- */
            HeaderFooter header = new HeaderFooter(new Phrase(title, fontHeader), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            
            document.open();
            
            String S = (String) sessionData.get(2);
            byte numOfDay = (byte) Integer.parseInt(S);

            //System.out.println("==>> proses sebelum bikin header ....");
            Table table = getTableHeader(numOfDay);
           // System.out.println("==>> proses setelah bikin header ....");

            Vector visitsData = (Vector) sessionData.get(3);
            int numOfEmployee = visitsData.size();

            Cell cell;
            String employeePayroll = "";
            String employeeName = "";
            byte day;

            byte index;
            byte date;
            int value = 0;
            boolean useRecord;
            int valuePerDay[] = new int [numOfDay+1];
            int totalVisitsPerEmployee;
            int totalPerDay[] = new int [numOfDay+1];
            
            for (day = 0; day < numOfDay; day++) {
                totalPerDay[day] = 0;
            }
            //System.out.println("==>> totalPerDay = "+totalPerDay.length);
            int total = 0;
            
            /* --- data processing --- */
            for (int item = 0; item < numOfEmployee; item++) {

                MonthlyDetailVisitation mdv = (MonthlyDetailVisitation) visitsData.get(item);
                useRecord = false;

                for (index = 0; index < numOfDay; index++) {
                    date = (byte) (1 + index);

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
                    //numOfEmployee++;

                    cell = new Cell(new Chunk(String.valueOf(item+1) + " ", fontContent));
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

                    //System.out.println("==>> selesai 1 row ");

                    if (!pdfWriter.fitsPage(table)) {
                        table.deleteLastRow();
                        item--;
                        document.add(table);
                        document.newPage();
                        table = getTableHeader(numOfDay);
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
            
            /* --- show cell that contain title "TOTAL" --- */
            cell = new Cell(new Chunk("TOTAL", fontContentBold));
            cell.setColspan(3);
            cell.setRowspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            
            /* --- show total of visitation per day in that department --- */
            for (day = 0; day < numOfDay; day++) {
                cell = new Cell(new Chunk(String.valueOf(totalPerDay[day]), fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
            }
            
            /* --- show total of all visitation in a period in that department --- */
            cell = new Cell(new Chunk(String.valueOf(total), fontContent));
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
}
