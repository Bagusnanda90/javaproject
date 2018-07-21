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
import javax.swing.border.Border;

/* --- lowagie pdf package --- */
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/* --- harisma package --- */
import com.dimata.harisma.session.canteen.SummaryDailyVisitation;
import com.dimata.util.Formater;
import com.dimata.qdep.form.FRMHandler;

public class PeriodicMealPayment extends HttpServlet {
    
    /* --- used colors --- */
    public static Color blackColor = new Color(0, 0, 0);
    public static Color summaryColor = new Color(240, 240, 240);
    /* --- used fonts --- */
    public static Font fontHeaderFooter = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLD, blackColor);
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContentBold = new Font(Font.TIMES_NEW_ROMAN, 9, Font.BOLD, blackColor);
        
    /** Creates a new instance of DailySummaryVisitation */
    public PeriodicMealPayment() {
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);        
    }
    
    /* --- create header of table --- */
    public static Table getTableHeader() throws BadElementException, DocumentException {

        Table table = new Table(4);
        table.setCellpadding(1);
        table.setCellspacing(1);
        //table.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        table.setAlignment(Table.ALIGN_CENTER);

        int columnWidth[] = {5,5,10,10};
        table.setWidths(columnWidth);
        table.setWidth(60);
        //table.setAlignment(Table.ALIGN_LEFT);
                
        /* --- draw a first column header --- */
        Cell cell = new Cell(new Chunk("DAY", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);
        
        /* --- draw a second column header --- */
        cell = new Cell(new Chunk("DATE", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);

        cell = new Cell(new Chunk("TOTAL VISIT", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(1);
        cell.setRowspan(1);
        cell.setBackgroundColor(summaryColor);
        table.addCell(cell);

        cell = new Cell(new Chunk("TOTAL PAYMENT/DAY", fontContent));
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
            HttpSession sessDataSess = request.getSession(true);
            Vector sessData = null;
            try {
                sessData = (Vector) sessDataSess.getValue("CANTEEN_PAYMENT_MEAL_DATA");
            } catch (Exception error) {
                System.out.println("Exception : " + error.toString());
            }


            /* --- get string of period --- */
            Date date = (Date) sessData.get(0);
            DateFormat dateFormat = DateFormat.getDateInstance(java.text.DateFormat.LONG, Locale.US);
            String periodTitle = dateFormat.format(date);
            periodTitle = periodTitle.toUpperCase();

            Date dateTo = (Date) sessData.get(1);
            if (!date.equals(dateTo)) {
                dateFormat = DateFormat.getDateInstance(java.text.DateFormat.LONG, Locale.US);
                String periodToTitle = dateFormat.format(dateTo);
                periodTitle = periodTitle + " to " + periodToTitle.toUpperCase();
            }

            Vector dataOfReport = (Vector) sessData.get(2);
            String strDepartement = (String) sessData.get(3);
            String strSection = (String) sessData.get(4);
            int rpType = Integer.parseInt((String) sessData.get(5));
            Boolean nom = (Boolean) sessData.get(6);
            boolean nominal = nom.booleanValue();
            double valNominal = Double.parseDouble((String) sessData.get(7));
            String str = (String)sessData.get(8);

            /**
             * get preapare dan approve document
             */
            String appPrepared = (String)sessData.get(9);
            String appApproved = (String)sessData.get(10);
            //System.out.println("preapared : "+appPrepared);
            //System.out.println("appApproved : "+appApproved);

            String strReportTitle = "MEAL REPORT";

            if(strDepartement.length()>0){
                strReportTitle = strReportTitle + "\nDEPARTMENT : "+strDepartement;
                if(strSection.length()>0)
                    strReportTitle = strReportTitle + "\nOUTLET : "+strSection;

                strReportTitle = strReportTitle + "\nPERIOD : "+periodTitle + " "+str;
            }else{
                strReportTitle = strReportTitle + "\n"+periodTitle + str;
                //strReportTitle = strReportTitle + "\n";
            }
            // strReportTitle = strReportTitle + "\n"+periodTitle;

            /* --- create a header for document --- */
            HeaderFooter header = new HeaderFooter(new Phrase(strReportTitle, fontHeaderFooter), false);

            if(strDepartement.length()>0)
                header.setAlignment(Element.ALIGN_CENTER);
            else
                header.setAlignment(Element.ALIGN_CENTER);

            header.setBorder(Rectangle.NO_BORDER);
            //header.setBorderColor(blackColor);
            document.setHeader(header);
            /* --- open document to add data --- */
            document.open();
                        
            Table table = getTableHeader();
            int totalVisits = 0;
            for (int i = 0; i < dataOfReport.size(); i++) {
                SummaryDailyVisitation data = (SummaryDailyVisitation) dataOfReport.get(i);

                DateFormat dateFormatx = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
                String periodTitlex = dateFormatx.format(data.getDate());

                Cell cell = new Cell(new Chunk(" "+periodTitlex.substring(0,periodTitlex.indexOf(","))+ " ", fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                    
                cell = new Cell(new Chunk(" " + Formater.formatDate(data.getDate(),"dd-MMM-yy"), fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                cell = new Cell(new Chunk(String.valueOf(data.getNumVisits()) + " ", fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                    
                cell = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(data.getNumVisits() * valNominal) + " ", fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                if (!pdfWriter.fitsPage(table)) {
                    table.deleteLastRow();
                    i--;
                    document.add(table);
                    document.newPage();
                    table = getTableHeader();
                } else {
                    totalVisits += data.getNumVisits();
                }   
            }
                
            Cell cell = new Cell(new Chunk("TOTAL", fontContentBold));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new Cell(new Chunk(String.valueOf(totalVisits) + " ", fontContent));
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totalVisits * valNominal) + " ", fontContent));
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            
            document.add(table);

            Table tableApp = new Table(2);
            tableApp.setWidth(60);
            tableApp.setBorder(Table.NO_BORDER);
            tableApp.setAlignment(Table.ALIGN_CENTER);

            Cell cells = new Cell(new Chunk("", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk("", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk("Prepared by,", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk("Approved by,", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk("", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk("", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk("", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk("", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk("", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk("", fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk(appPrepared, fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            cells = new Cell(new Chunk(appApproved, fontContent));
            cells.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cells.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cells.setBorder(Cell.NO_BORDER);
            tableApp.addCell(cells);

            document.add(tableApp);

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
