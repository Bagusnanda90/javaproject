/*
 * CashFlowDeptPdf.java
 *
 * Created on September 1, 2005, 2:27 PM
 */

package com.dimata.harisma.report.arap;

/**
 *
 * @author  nengock
 */

import com.dimata.harisma.entity.search.SrcArApReport;
import com.dimata.harisma.entity.report.ArApPerContact;
import com.dimata.harisma.entity.report.ArApPerDueDate;
//import com.dimata.harisma.session.arap.SessArApReport;
import com.dimata.harisma.session.arap.SessArApReport; 
import com.dimata.util.Formater;
import com.dimata.qdep.form.FRMHandler;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;


public class ArApReportPdf extends HttpServlet {

    /* declaration constant */
    public static Color blackColor = new Color(0, 0, 0);
    public static Color whiteColor = new Color(255, 255, 255);
    public static Color titleColor = new Color(200, 200, 200);
    public static Color summaryColor = new Color(240, 240, 240);
    public static String formatDate = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontFooter = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);
    public static Font fontContentBold = new Font(Font.TIMES_NEW_ROMAN, 8, Font.BOLD, blackColor);

    public static String strTitle[][] =
            {
                {
                    "No.",
                    "Kontak",
                    "Awal",
                    "Penambahan",
                    "Pembayaran",
                    "Akhir",
                    "Mutasi",
                    "Tidak ada data sesuai yang ditemukan",
                    "Tanggal Jatuh Tempo",
                    "Hutang",
                    "Piutang",
                    "Selisih",
                    "Tanggal",
                    "s/d"
                },
                {
                    "No.",
                    "Contact",
                    "Prev Balance",
                    "Increment",
                    "Payment",
                    "Balance",
                    "Mutation",
                    "List is empty",
                    "Due Date",
                    "Payable",
                    "Receivable",
                    "Differ",
                    "Date",
                    "to"
                }
            };

    /**
     * Initializes the servlet
     */
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init servlet");
        super.init(config);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Destroys the servlet
     */
    public void destroy() {
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* creating the document object */
        Document document = new Document(PageSize.A4, 60, 30, 50, 30);
        
        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessArAp = request.getSession(true);
            Vector vecSess = null;
            SrcArApReport srcArApReport = new SrcArApReport();
            int SESS_LANGUAGE = 0;
            Vector vecArAp = new Vector();
            try {
                vecSess = (Vector) sessArAp.getValue(SessArApReport.SESS_SEARCH_ARAP);
                srcArApReport = (SrcArApReport) vecSess.get(0);
                SESS_LANGUAGE = srcArApReport.getLanguage();
                vecArAp = (Vector) vecSess.get(1);
            } catch (Exception e) {
                System.out.println("Exc : " + e.toString());
            }

            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase(SessArApReport.stReportType[SESS_LANGUAGE][srcArApReport.getReportType()] + "\n" +
                    strTitle[SESS_LANGUAGE][12] + ": " + Formater.formatDate(srcArApReport.getFromDate(), "dd-MMM-yyyy") +
                    " " + strTitle[SESS_LANGUAGE][13] + " " + Formater.formatDate(srcArApReport.getUntilDate(), "dd-MMM-yyyy") + "\n", fontHeader), false);

            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);

            HeaderFooter footer = new HeaderFooter(new Phrase(srcArApReport.getReportLink(), fontFooter), false);

            footer.setAlignment(Element.ALIGN_LEFT);
            footer.setBorder(Rectangle.BOTTOM);
            footer.setBorderColor(blackColor);
            document.setFooter(footer);

            /* opening the document, needed for add something into document */
            document.open();

            /* create header */
            Table tableArAp = getTableHeader(srcArApReport, SESS_LANGUAGE);

            /* generate employee attendance report's data */
            Cell cellArAp = new Cell("");
            double totalPrev = 0;
            double totalInc = 0;
            double totalDec = 0;

            if (srcArApReport.getReportType() == SessArApReport.ARAP_REPORT_PER_DUE_DATE) {
                String pref = "";
                String suf = "";
                int mul = 1;
                for (int i = 0; i < vecArAp.size(); i++) {

                    ArApPerDueDate arApReport = (ArApPerDueDate) vecArAp.get(i);
                    pref = "";
                    suf = "";
                    mul = 1;

                    cellArAp = new Cell(new Chunk("" + (i + 1), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk("   " + Formater.formatDate(arApReport.getDueDate(), "dd-MMMMM-yyyy"), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getPayable()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getReceivable()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    if (arApReport.getDiffer() < 0) {
                        pref = "(";
                        suf = ")";
                        mul = -1;
                    }

                    cellArAp = new Cell(new Chunk(pref + FRMHandler.userFormatStringDecimal(arApReport.getDiffer() * mul) + suf, fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    totalInc = totalInc + arApReport.getReceivable();
                    totalDec = totalDec + arApReport.getPayable();
                }


                //total
                cellArAp = new Cell(new Chunk("TOTAL", fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setColspan(2);
                cellArAp.setRowspan(1);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totalDec), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totalInc), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                if ((totalInc - totalDec) < 0) {
                    pref = "(";
                    suf = ")";
                    mul = -1;
                } else {
                    pref = "";
                    suf = "";
                    mul = 1;
                }

                cellArAp = new Cell(new Chunk(pref + FRMHandler.userFormatStringDecimal((totalInc - totalDec) * mul) + suf, fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

            } else {

                for (int i = 0; i < vecArAp.size(); i++) {
                    ArApPerContact arApReport = (ArApPerContact) vecArAp.get(i);

                    cellArAp = new Cell(new Chunk("" + (i + 1), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(arApReport.getContactName(), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getPrevBalance()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getIncrement()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getDecrement()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getBalance()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    totalPrev = totalPrev + arApReport.getPrevBalance();
                    totalInc = totalInc + arApReport.getIncrement();
                    totalDec = totalDec + arApReport.getDecrement();
                }


                //total
                cellArAp = new Cell(new Chunk("TOTAL", fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setColspan(2);
                cellArAp.setRowspan(1);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totalPrev), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totalInc), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totalDec), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totalPrev + totalInc - totalDec), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

            }
            //-----------


            document.add(tableArAp);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }
        
        /* closing the document */
        document.close();
        
        /* we have written the pdfstream to a ByteArrayOutputStream, now going to write this outputStream to the ServletOutputStream
         * after we have set the contentlength
         */
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }


    /**
     * this method used to create header table
     */
    public static Table getTableHeader(SrcArApReport srcArApReport, int language) throws BadElementException, DocumentException {
        Table tableArAp = new Table(6);
        int widthHeader[] = {5, 30, 15, 15, 15, 15};
        if (srcArApReport.getReportType() == SessArApReport.ARAP_REPORT_PER_DUE_DATE) {
            tableArAp = new Table(5);
            int widthTemp[] = {5, 15, 15, 15, 15};
            widthHeader = widthTemp;
        }

        tableArAp.setCellpadding(1);
        tableArAp.setCellspacing(1);
        tableArAp.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        tableArAp.setWidths(widthHeader);
        tableArAp.setWidth(100);

        Cell cellArApHeader;
        if (srcArApReport.getReportType() == SessArApReport.ARAP_REPORT_PER_DUE_DATE) {
            cellArApHeader = new Cell(new Chunk(strTitle[language][0], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][8], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][9], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][10], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][11], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

        } else {
            cellArApHeader = new Cell(new Chunk(strTitle[language][0], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][1], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][2], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][6], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(2);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][5], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][3], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][4], fontContent));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);
        }
        return tableArAp;
    }

}
