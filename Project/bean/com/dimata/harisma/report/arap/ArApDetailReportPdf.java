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

import com.dimata.harisma.entity.report.ArApAging;
import com.dimata.harisma.entity.report.ArApDetail;
import com.dimata.harisma.entity.report.ArApDetailItem;
import com.dimata.harisma.entity.report.ArApDetailPayment;
import com.dimata.harisma.entity.search.SrcArApReport;
//import com.dimata.harisma.session.arap.SessArApDetailReport;
import com.dimata.harisma.session.arap.SessArApDetailReport;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.util.Formater;
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


public class ArApDetailReportPdf extends HttpServlet {

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
                    "Dokumen",
                    "Nomor",
                    "Tanggal",
                    "Tanggal JT",
                    "Nominal",
                    "Bayar",
                    "Saldo",
                    "Keterangan",
                    "Tidak ada data sesuai yang ditemukan",
                    "s/d",
                    "Posting ke Jurnal",
                    "Daftar Angsuran ",
                    "Entry Pembayaran ",
                    "Jatuh Tempo",
                    "Lewat Waktu",
                    "Sekarang",
                    "Besok",
                    "hari",
                    "diatas"
                },
                {
                    "No.",
                    "Contact",
                    "Document",
                    "Number",
                    "Date",
                    "Due Date",
                    "Nominal",
                    "Payed",
                    "Balance",
                    "Description",
                    "List is empty",
                    "to",
                    "Posting Jurnal",
                    "List Term of Payment ",
                    "Payment Entry ",
                    "Due Date",
                    "Over Due",
                    "Today",
                    "Tomorrow",
                    "days",
                    "above"
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
        Document document = new Document(PageSize.A4.rotate(), 60, 30, 50, 30);
        
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
                vecSess = (Vector) sessArAp.getValue(SessArApDetailReport.SESS_SEARCH_ARAP_DETAIL);
                srcArApReport = (SrcArApReport) vecSess.get(0);
                SESS_LANGUAGE = srcArApReport.getLanguage();
                vecArAp = (Vector) vecSess.get(1);
            } catch (Exception e) {
                System.out.println("Exc : " + e.toString());
            }

            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase(SessArApDetailReport.stReportType[SESS_LANGUAGE][srcArApReport.getReportType()] + "\n" +
                    strTitle[SESS_LANGUAGE][4] + ": " + Formater.formatDate(srcArApReport.getFromDate(), "dd-MMM-yyyy") +
                    (srcArApReport.getReportType() < SessArApDetailReport.AR_TODAY_DUE_DATE ? (" " + strTitle[SESS_LANGUAGE][11] + " " + Formater.formatDate(srcArApReport.getUntilDate(), "dd-MMM-yyyy")) : "")
                    + "\n", fontHeader), false);

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

            // todo do it here

            if (srcArApReport.getReportType() == SessArApDetailReport.AR_INCREASE || srcArApReport.getReportType() == SessArApDetailReport.AP_INCREASE) {

                double total = 0;
                for (int i = 0; i < vecArAp.size(); i++) {

                    ArApDetail arApReport = (ArApDetail) vecArAp.get(i);
                    Vector detail = arApReport.getDetail();
                    int rowSize = detail.size();

                    cellArAp = new Cell(new Chunk("" + (i + 1), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setColspan(1);
                    cellArAp.setRowspan(rowSize);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(arApReport.getContactName(), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setColspan(1);
                    cellArAp.setRowspan(rowSize);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    for (int j = 0; j < detail.size(); j++) {

                        ArApDetailItem item = (ArApDetailItem) detail.get(j);

                        cellArAp = new Cell(new Chunk(" " + item.getVoucherNo(), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(" " + Formater.formatDate(item.getVoucherDate(), "dd-MMM-yyyy"), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(" " + Formater.formatDate(item.getDueDate(), "dd-MMM-yyyy"), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(item.getNominal()), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(item.getDescription(), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);
                    }
                    total = total + arApReport.getTotalNominal();

                    //total

                    cellArAp = new Cell(new Chunk("SUB TOTAL", fontContentBold));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setColspan(5);
                    cellArAp.setRowspan(1);
                    cellArAp.setBackgroundColor(summaryColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getTotalNominal()), fontContentBold));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(summaryColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(" ", fontContentBold));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(summaryColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                }


                //total

                cellArAp = new Cell(new Chunk("TOTAL", fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setColspan(5);
                cellArAp.setRowspan(1);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(total), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(" ", fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

            } else if (srcArApReport.getReportType() == SessArApDetailReport.AR_PAYMENT || srcArApReport.getReportType() == SessArApDetailReport.AP_PAYMENT) {

                //todo
                double total = 0;
                for (int i = 0; i < vecArAp.size(); i++) {

                    ArApDetail arApReport = (ArApDetail) vecArAp.get(i);
                    Vector detail = arApReport.getPayment();
                    int rowSize = detail.size();

                    cellArAp = new Cell(new Chunk("" + (i + 1), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setColspan(1);
                    cellArAp.setRowspan(rowSize);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(arApReport.getContactName(), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setColspan(1);
                    cellArAp.setRowspan(rowSize);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    for (int j = 0; j < detail.size(); j++) {

                        ArApDetailPayment item = (ArApDetailPayment) detail.get(j);

                        cellArAp = new Cell(new Chunk(" " + item.getPaymentNo(), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(" " + Formater.formatDate(item.getPaymentDate(), "dd-MMM-yyyy"), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(item.getNominal()), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                    }
                    total = total + arApReport.getTotalPay();

                    //total

                    cellArAp = new Cell(new Chunk("SUB TOTAL", fontContentBold));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setColspan(4);
                    cellArAp.setRowspan(1);
                    cellArAp.setBackgroundColor(summaryColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getTotalPay()), fontContentBold));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(summaryColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                }

                //total

                cellArAp = new Cell(new Chunk("TOTAL", fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setColspan(4);
                cellArAp.setRowspan(1);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(total), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

            } else if (srcArApReport.getReportType() >= SessArApDetailReport.AR_DETAIL && srcArApReport.getReportType() < SessArApDetailReport.AR_AGING) {
                //todo
                double total = 0;
                double totalPay = 0;
                for (int i = 0; i < vecArAp.size(); i++) {

                    ArApDetail arApReport = (ArApDetail) vecArAp.get(i);
                    Vector detail = arApReport.getDetail();
                    int rowSize = detail.size();

                    cellArAp = new Cell(new Chunk("" + (i + 1), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setColspan(1);
                    cellArAp.setRowspan(rowSize);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(arApReport.getContactName(), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setColspan(1);
                    cellArAp.setRowspan(rowSize);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    for (int j = 0; j < detail.size(); j++) {

                        ArApDetailItem item = (ArApDetailItem) detail.get(j);

                        cellArAp = new Cell(new Chunk(" " + item.getVoucherNo(), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(" " + Formater.formatDate(item.getVoucherDate(), "dd-MMM-yyyy"), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER); 
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(" " + Formater.formatDate(item.getDueDate(), "dd-MMM-yyyy"), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(item.getNominal()), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(item.getPayed()), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);

                        cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(item.getBalance()), fontContent));
                        cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellArAp.setBackgroundColor(whiteColor);
                        cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableArAp.addCell(cellArAp);
                    }
                    total = total + arApReport.getTotalNominal();
                    totalPay = totalPay + arApReport.getTotalPay();

                    //total

                    cellArAp = new Cell(new Chunk("SUB TOTAL", fontContentBold));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setColspan(5);
                    cellArAp.setRowspan(1);
                    cellArAp.setBackgroundColor(summaryColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getTotalNominal()), fontContentBold));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(summaryColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getTotalPay()), fontContentBold));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(summaryColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getTotalBalance()), fontContentBold));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(summaryColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                }


                //total

                cellArAp = new Cell(new Chunk("TOTAL", fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setColspan(5);
                cellArAp.setRowspan(1);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(total), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totalPay), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(total - totalPay), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);
            } else {
                // todo
                double totBlc = 0;
                double totNow = 0;
                double totTmr = 0;
                double totDD1 = 0;
                double totDD2 = 0;
                double totDD3 = 0;
                double totODD = 0;
                double totOD1 = 0;
                double totOD2 = 0;
                double totOD3 = 0;
                double totOOD = 0;

                for (int i = 0; i < vecArAp.size(); i++) {
                    ArApAging arApReport = (ArApAging) vecArAp.get(i);

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

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getBalance()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getTodayDueDateValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getTomorrowDueDateValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getFirstPeriodeDueDateValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getSecondPeriodeDueDateValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getThirdPeriodeDueDateValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getOverPeriodeDueDateValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getFirstPeriodeOverDueValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getSecondPeriodeOverDueValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getThirdPeriodeOverDueValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(arApReport.getOverPeriodeOverDueValue()), fontContent));
                    cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellArAp.setBackgroundColor(whiteColor);
                    cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableArAp.addCell(cellArAp);

                    totBlc = totBlc + arApReport.getBalance();
                    totNow = totNow + arApReport.getTodayDueDateValue();
                    totTmr = totTmr + arApReport.getTomorrowDueDateValue();
                    totDD1 = totDD1 + arApReport.getFirstPeriodeDueDateValue();
                    totDD2 = totDD2 + arApReport.getSecondPeriodeDueDateValue();
                    totDD3 = totDD3 + arApReport.getThirdPeriodeDueDateValue();
                    totODD = totODD + arApReport.getOverPeriodeDueDateValue();
                    totOD1 = totOD1 + arApReport.getFirstPeriodeOverDueValue();
                    totOD2 = totOD2 + arApReport.getSecondPeriodeOverDueValue();
                    totOD3 = totOD3 + arApReport.getThirdPeriodeOverDueValue();
                    totOOD = totOOD + arApReport.getOverPeriodeOverDueValue();
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

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totBlc), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totNow), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totTmr), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totDD1), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totDD2), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totDD3), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totODD), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totOD1), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totOD2), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totOD3), fontContentBold));
                cellArAp.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellArAp.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellArAp.setBackgroundColor(summaryColor);
                cellArAp.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableArAp.addCell(cellArAp);

                cellArAp = new Cell(new Chunk(FRMHandler.userFormatStringDecimal(totOOD), fontContentBold));
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
        Table tableArAp = new Table(13);
       //int widthHeader[] = {3, 15, 12, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        //int widthHeader[] = {3, 9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
        int widthHeader[] = {3, 15, 11, 7, 8, 7, 7, 7, 7, 7, 7, 7, 7};
        
        if (srcArApReport.getReportType() == SessArApDetailReport.AR_INCREASE || srcArApReport.getReportType() == SessArApDetailReport.AP_INCREASE) {
            tableArAp = new Table(7);
            int widthTemp[] = {5, 25, 15, 10, 10, 15, 20};
            widthHeader = widthTemp;
        } else if (srcArApReport.getReportType() == SessArApDetailReport.AR_PAYMENT || srcArApReport.getReportType() == SessArApDetailReport.AP_PAYMENT) {
            tableArAp = new Table(5);
            int widthTemp[] = {5, 25, 15, 10, 15};
            widthHeader = widthTemp;
        } else if (srcArApReport.getReportType() >= SessArApDetailReport.AR_DETAIL && srcArApReport.getReportType() < SessArApDetailReport.AR_AGING) {
            tableArAp = new Table(8);
            int widthTemp[] = {5, 25, 15, 10, 10, 10, 10, 15};
            widthHeader = widthTemp;
        }

        tableArAp.setCellpadding(1);
        tableArAp.setCellspacing(1);
        tableArAp.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        tableArAp.setWidths(widthHeader);
        tableArAp.setWidth(100);

        Cell cellArApHeader;
        if (srcArApReport.getReportType() == SessArApDetailReport.AR_INCREASE || srcArApReport.getReportType() == SessArApDetailReport.AP_INCREASE) {

            cellArApHeader = new Cell(new Chunk(strTitle[language][0], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][1], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][2], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(5);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][3], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][4], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][5], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][6], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][9], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

        } else if (srcArApReport.getReportType() == SessArApDetailReport.AR_PAYMENT || srcArApReport.getReportType() == SessArApDetailReport.AP_PAYMENT) {

            cellArApHeader = new Cell(new Chunk(strTitle[language][0], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][1], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][2], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(3);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][3], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][4], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][6], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

        } else if (srcArApReport.getReportType() >= SessArApDetailReport.AR_DETAIL && srcArApReport.getReportType() < SessArApDetailReport.AR_AGING) {

            cellArApHeader = new Cell(new Chunk(strTitle[language][0], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][1], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][2], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(4);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][7], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][8], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][3], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][4], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][5], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][6], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);


        } else {
            cellArApHeader = new Cell(new Chunk(strTitle[language][0], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][1], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][8], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(2);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][15], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(6);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][16], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(4);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][17], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][18], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk("2 " + strTitle[language][11] + " " + srcArApReport.getPeriodeSpan1() + " " + strTitle[language][19], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk((srcArApReport.getPeriodeSpan1() + 1) + " " + strTitle[language][11] + " " + (srcArApReport.getPeriodeSpan1() + srcArApReport.getPeriodeSpan2()) + " " + strTitle[language][19], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk((srcArApReport.getPeriodeSpan1() + srcArApReport.getPeriodeSpan2() + 1) + " " + strTitle[language][11] + " " + (srcArApReport.getPeriodeSpan3() + srcArApReport.getPeriodeSpan2() + srcArApReport.getPeriodeSpan1()) + " " + strTitle[language][19], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][20] + " " + (srcArApReport.getPeriodeSpan3() + srcArApReport.getPeriodeSpan2() + srcArApReport.getPeriodeSpan1()) + " " + strTitle[language][19], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk("1 " + strTitle[language][11] + " " + srcArApReport.getPeriodeSpan1() + " " + strTitle[language][19], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk((srcArApReport.getPeriodeSpan1() + 1) + " " + strTitle[language][11] + " " + (srcArApReport.getPeriodeSpan1() + srcArApReport.getPeriodeSpan2()) + " " + strTitle[language][19], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk((srcArApReport.getPeriodeSpan1() + srcArApReport.getPeriodeSpan2() + 1) + " " + strTitle[language][11] + " " + (srcArApReport.getPeriodeSpan3() + srcArApReport.getPeriodeSpan2() + srcArApReport.getPeriodeSpan1()) + " " + strTitle[language][19], fontContentBold));
            cellArApHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellArApHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellArApHeader.setColspan(1);
            cellArApHeader.setRowspan(1);
            cellArApHeader.setBackgroundColor(summaryColor);
            tableArAp.addCell(cellArApHeader);

            cellArApHeader = new Cell(new Chunk(strTitle[language][20] + " " + (srcArApReport.getPeriodeSpan3() + srcArApReport.getPeriodeSpan2() + srcArApReport.getPeriodeSpan1()) + " " + strTitle[language][19], fontContentBold));
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
