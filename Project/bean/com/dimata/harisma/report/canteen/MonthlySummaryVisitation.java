/*
 * MonthlySummaryVisitation.java
 * @author rusdianta
 * Created on January 21, 2005, 5:21 PM
 */

package com.dimata.harisma.report.canteen;

/* --- use internal java package --- */

import java.io.*;
import java.util.*;
import java.awt.Color;
import java.text.*;

/* --- use java servlet pacage --- */
import javax.servlet.*;
import javax.servlet.http.*;

/* --- use pdf lowagie package --- */
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import com.dimata.harisma.session.canteen.MonthlySummary;
import com.dimata.qdep.form.FRMHandler;

public class MonthlySummaryVisitation extends HttpServlet {

    /* --- landscape of A4 paper --- */
    public static final Rectangle A4L = new Rectangle(842, 595);

    /* --- used colors --- */
    public static Color blackColor = Color.BLACK;
    public static Color summaryColor = new Color(240, 240, 240);

    /* --- used fonts --- */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontCellHeader = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);
    public static Font fontContentBold = new Font(Font.TIMES_NEW_ROMAN, 8, Font.BOLD, blackColor);

    /** Creates a new instance of MonthlySummaryVisitation */
    public MonthlySummaryVisitation() {
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected Table getTableHeader(Date date, Date dateto, boolean nominal) throws BadElementException, DocumentException {
        int iStartIterate = date.getDate();
        int iEndIterate = dateto.getDate();

        /* --- make table --- */
        int numColumn = iEndIterate - iStartIterate + 4;
        if (nominal) {
            numColumn = iEndIterate - iStartIterate + 5;
        }
        int maxColSpan = (iEndIterate - iStartIterate) + 1;
        Table table = new Table(numColumn);
        try{
            table.setCellpadding(1);
            table.setCellspacing(1);
            table.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

            float columnWidth[] = new float[numColumn];
            columnWidth[0] = 2.8f;
            columnWidth[1] = 11;
            float totalWidth = 30.09f;
            float defColWidth = 2.61f;

            if(maxColSpan < 10){
                defColWidth = 4;
            }
            //defColWidth = defColWidth * 31 / numOfDay;
            int idx = 2;
            for (int j = iStartIterate; j <= iEndIterate; j++) {
                columnWidth[idx] = defColWidth;
                totalWidth += defColWidth;
                idx++;
            }

            columnWidth[idx] = 4.59f;
            if (nominal) {
                idx++;
                columnWidth[idx] = 8;
            }

            table.setWidths(columnWidth);
            table.setWidth(100);
            table.setAlignment(Table.ALIGN_LEFT);

            /* --- build cell in the table --- */
            Cell cell = new Cell(new Chunk("NO.", fontCellHeader));
            cell.setRowspan(2);
            cell.setColspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);

            cell = new Cell(new Chunk("OUTLET", fontCellHeader));
            cell.setRowspan(2);
            cell.setColspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);

            cell = new Cell(new Chunk("DATE", fontCellHeader));
            cell.setRowspan(1);
            cell.setColspan(maxColSpan);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);

            cell = new Cell(new Chunk("TOTAL", fontCellHeader));
            cell.setRowspan(2);
            cell.setColspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(summaryColor);
            table.addCell(cell);

            if (nominal) {
                cell = new Cell(new Chunk("NOMINAL", fontCellHeader));
                cell.setRowspan(2);
                cell.setColspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cell.setBackgroundColor(summaryColor);
                table.addCell(cell);
            }

            for (int j = iStartIterate; j <= iEndIterate; j++) {
                cell = new Cell(new Chunk(String.valueOf(j), fontCellHeader));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cell.setBackgroundColor(summaryColor);
                table.addCell(cell);
            }
        }catch(Exception e){
            System.out.println("e : "+e.toString());
        }
        return table;
    }

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        /* --- create a document object --- */
        Document document = new Document(A4L, 20, 20, 30, 30);

        /* --- create a output stream --- */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            /* --- create an instant of PdfWriter --- */
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);

            /* --- get data from session --- */
            HttpSession session = request.getSession(true);
            Vector sessData = null;
            try {
                sessData = (Vector) session.getValue("CANTEEN_MONTHLY_SUMMARY_DATA");
            } catch (Exception error) {
                System.out.println("Exception : " + error.toString());
            }

            /*Date period_date = (Date) sessData.get(0);
            String MONTHS[] = new DateFormatSymbols().getMonths();
            byte month = (byte) period_date.getMonth();
            int year = period_date.getYear() + 1900;
            String periodName = (MONTHS[month]).toUpperCase() + " " + year;
            String title = "CANTEEN SUMMARY REPORT\nPERIOD : " + periodName;*/

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

            Vector dataOfReportSize = (Vector) sessData.get(2);
            String strDepartement = (String) sessData.get(3);
            String strSection = (String) sessData.get(4);
            int rpType = Integer.parseInt((String) sessData.get(5));
            Boolean nom = (Boolean) sessData.get(6);
            boolean nominal = nom.booleanValue();
            double valNominal = Double.parseDouble((String) sessData.get(7));
            String str = (String)sessData.get(8);


            String strReportTitle = "MEAL RECORD";
            if(strDepartement.length()>0)
                strReportTitle = strReportTitle + "\nDEPARTMENT : " + strDepartement;
            else
                strReportTitle = strReportTitle + "\nALL DEPARTMENT";

            if (strSection.length() > 0)
                strReportTitle = strReportTitle + "\nOUTLET : " + strSection;

            strReportTitle = strReportTitle + "\n" + periodTitle + str;

            /* --- build a document header --- */
            HeaderFooter header = new HeaderFooter(new Phrase(strReportTitle, fontHeader), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            document.open();

            int iStartIterate = date.getDate();
            int iEndIterate = dateTo.getDate();

            Table table = getTableHeader(date, dateTo, nominal);

            /* --- data processing --- */
            Cell cell;
            MonthlySummary monthlySummary = null;
            String departmentName = "";
            int totalPerDepartment = 0;
            int numVisits = 0;
            int totalPerDay[] = new int[iEndIterate - iStartIterate + 1];
            int valuePerDay[] = new int[iEndIterate - iStartIterate + 1];

            int total = 0;
            int idx = 0;
            for (int j = iStartIterate; j <= iEndIterate; j++) {
                totalPerDay[idx] = 0;
                valuePerDay[idx] = 0;
                idx++;
            }
            for (int i = 0; i < dataOfReportSize.size(); i++) {
                cell = new Cell(new Chunk(String.valueOf(i + 1) + " ", fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                monthlySummary = (MonthlySummary) dataOfReportSize.get(i);
                departmentName = monthlySummary.getDepartmentName();

                cell = new Cell(new Chunk(" " + departmentName, fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                for (int x = 0; x < valuePerDay.length; x++) {
                    valuePerDay[x] = 0;
                }

                totalPerDepartment = 0;
                idx=0;
                for (int j = iStartIterate; j <= iEndIterate; j++) {
                    switch (j) {
                        case 1:
                            numVisits = monthlySummary.getDate1();
                            break;
                        case 2:
                            numVisits = monthlySummary.getDate2();
                            break;
                        case 3:
                            numVisits = monthlySummary.getDate3();
                            break;
                        case 4:
                            numVisits = monthlySummary.getDate4();
                            break;
                        case 5:
                            numVisits = monthlySummary.getDate5();
                            break;
                        case 6:
                            numVisits = monthlySummary.getDate6();
                            break;
                        case 7:
                            numVisits = monthlySummary.getDate7();
                            break;
                        case 8:
                            numVisits = monthlySummary.getDate8();
                            break;
                        case 9:
                            numVisits = monthlySummary.getDate9();
                            break;
                        case 10:
                            numVisits = monthlySummary.getDate10();
                            break;
                        case 11:
                            numVisits = monthlySummary.getDate11();
                            break;
                        case 12:
                            numVisits = monthlySummary.getDate12();
                            break;
                        case 13:
                            numVisits = monthlySummary.getDate13();
                            break;
                        case 14:
                            numVisits = monthlySummary.getDate14();
                            break;
                        case 15:
                            numVisits = monthlySummary.getDate15();
                            break;
                        case 16:
                            numVisits = monthlySummary.getDate16();
                            break;
                        case 17:
                            numVisits = monthlySummary.getDate17();
                            break;
                        case 18:
                            numVisits = monthlySummary.getDate18();
                            break;
                        case 19:
                            numVisits = monthlySummary.getDate19();
                            break;
                        case 20:
                            numVisits = monthlySummary.getDate20();
                            break;
                        case 21:
                            numVisits = monthlySummary.getDate21();
                            break;
                        case 22:
                            numVisits = monthlySummary.getDate22();
                            break;
                        case 23:
                            numVisits = monthlySummary.getDate23();
                            break;
                        case 24:
                            numVisits = monthlySummary.getDate24();
                            break;
                        case 25:
                            numVisits = monthlySummary.getDate25();
                            break;
                        case 26:
                            numVisits = monthlySummary.getDate26();
                            break;
                        case 27:
                            numVisits = monthlySummary.getDate27();
                            break;
                        case 28:
                            numVisits = monthlySummary.getDate28();
                            break;
                        case 29:
                            numVisits = monthlySummary.getDate29();
                            break;
                        case 30:
                            numVisits = monthlySummary.getDate30();
                            break;
                        case 31:
                            numVisits = monthlySummary.getDate31();
                    }

                    valuePerDay[idx] = numVisits;
                    idx++;
                    //if(numVisits > 0)
                        cell = new Cell(new Chunk(String.valueOf(numVisits) + " ", fontContent));
                    //else
                    //    cell = new Cell(new Chunk(" ", fontContent));

                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                }

                totalPerDepartment = monthlySummary.getTotal();

                cell = new Cell(new Chunk(String.valueOf(totalPerDepartment) + " ", fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);

                if (nominal) {
                    cell = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totalPerDepartment * valNominal) + " ", fontContent));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    table.addCell(cell);
                }

                if (!pdfWriter.fitsPage(table)) {
                    table.deleteLastRow();
                    i--;
                    document.add(table);
                    document.newPage();
                    table = getTableHeader(date, dateTo, nominal);
                } else {
                    for(int k=0;k<totalPerDay.length;k++)
                        totalPerDay[k] = totalPerDay[k] + valuePerDay[k];

                    total += totalPerDepartment;
                }
            }

            cell = new Cell(new Chunk("TOTAL", fontContentBold));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            idx = 0;
            for (int j = iStartIterate; j <= iEndIterate; j++) {
                cell = new Cell(new Chunk(String.valueOf(totalPerDay[idx]) + " ", fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                idx++;
            }

            cell = new Cell(new Chunk(total + " ", fontContent));
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            if (nominal) {
                cell = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(total * valNominal) + " ", fontContent));
                cell.setColspan(1);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
            }
            document.add(table);

        } catch (DocumentException error) {
            System.err.println(error.getMessage());
            error.printStackTrace();
        }

        /* --- close document and build a pdf --- */
        document.close();
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream outs = response.getOutputStream();
        baos.writeTo(outs);
        outs.flush();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void destroy() {
    }
}
