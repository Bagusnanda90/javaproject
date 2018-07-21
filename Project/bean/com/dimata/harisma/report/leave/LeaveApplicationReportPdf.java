/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.leave;
// package java
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

// package servlet
import javax.servlet.*;
import javax.servlet.http.*;

// package lowagie
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

// package qdep
import com.dimata.util.*;
import com.dimata.qdep.form.*;

// package project
import com.dimata.harisma.entity.leave.LeaveApplicationDetail;
import com.dimata.harisma.entity.leave.PstLeaveApplicationDetail;
import com.dimata.harisma.entity.leave.LeaveApplicationDetailView;
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.system.entity.system.*;

/**
 *
 * @author Tu Roy
 */
public class LeaveApplicationReportPdf extends HttpServlet {
    // declaration constant
    public static Color blackColor = new Color(0, 0, 0);
    public static Color whiteColor = new Color(255, 255, 255);

    // setting some fonts in the color chosen by the user 
    public static Font fontHeader = new Font(Font.HELVETICA, 14, Font.BOLD, blackColor);
    public static Font fontContentSmall = new Font(Font.HELVETICA, 8, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.HELVETICA, 10, Font.NORMAL, blackColor);
    public static Font fontContentItalic = new Font(Font.HELVETICA, 10, Font.ITALIC, blackColor);
    public static Font fontContentBold = new Font(Font.HELVETICA, 10, Font.BOLD, blackColor);
    public static Font fontBoldSmall = new Font(Font.HELVETICA, 8, Font.BOLD, blackColor);
    public static Font fontBoldSmallItalic = new Font(Font.HELVETICA, 8, Font.BOLDITALIC, blackColor);
    public static String imagesRoot = PstSystemProperty.getValueByName("IMG_ROOT");
    public static Color colorTitle = Color.GRAY;

    /** Initializes the servlet
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Destroys the servlet
     */
    public void destroy() {
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long leaveApplicationId = 0;
        String appRoot = "";
        leaveApplicationId = FRMQueryString.requestLong(request, "oidLeaveApplication");    // untuk mendaptakan oid leave application
        appRoot = FRMQueryString.requestString(request, "approot");    // untuk mendaptakan oid leave application
        //update by satrya 2014-05-27
        int typeForm = FRMQueryString.requestInt(request, "TYPE_FORM_LEAVE");
        LeaveApplication leaveApplication = new LeaveApplication();
        Employee employee = new Employee();
        Department department = new Department();
        Position position = new Position();
        Division division = new Division();

        Vector alStockTaken = new Vector();
        Vector llStockTaken = new Vector();
        Vector specialTaken = new Vector();
        Vector dpTaken = new Vector();

        try {
            leaveApplication = PstLeaveApplication.fetchExc(leaveApplicationId);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }
        try {
            employee = PstEmployee.fetchExc(leaveApplication.getEmployeeId());
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }
        try {
            department = PstDepartment.fetchExc(employee.getDepartmentId());
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }
        try {
            position = PstPosition.fetchExc(employee.getPositionId());
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }
        try {
            division = PstDivision.fetchExc(employee.getDivisionId());
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        // Get approval department head
        String departmentHeadName = "";
        if (leaveApplication.getDepHeadApproval() != 0) {
            Employee employeeDepHead = new Employee();
            try {
                employeeDepHead = PstEmployee.fetchExc(leaveApplication.getDepHeadApproval());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
            departmentHeadName = employeeDepHead.getFullName();
        }

        // Get approval HrManager
        String HrManager = "";
        if (leaveApplication.getHrManApproval() != 0) {
            Employee employeeHrManager = new Employee();
            try {
                employeeHrManager = PstEmployee.fetchExc(leaveApplication.getHrManApproval());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
            HrManager = employeeHrManager.getFullName();
        }

        // Get approval Gm
        String gm = "";
        if (leaveApplication.getGmApproval() != 0) {
            Employee employeeGm = new Employee();
            try {
                employeeGm = PstEmployee.fetchExc(leaveApplication.getGmApproval());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
            gm = employeeGm.getFullName();
        }

        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        int maxApproval = leaveConfig.getMaxApproval(employee.getOID()); // mendapatkan jumlah approval

        String whereAl = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] + "=" + leaveApplication.getOID();
        String whereLl = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID] + "=" + leaveApplication.getOID();
        String whereSpecial = PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + "=" + leaveApplication.getOID();
        String whereDp = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + "=" + leaveApplication.getOID();

        alStockTaken = PstAlStockTaken.list(0, 0, whereAl, null);
        llStockTaken = PstLlStockTaken.list(0, 0, whereLl, null);
        specialTaken = PstSpecialUnpaidLeaveTaken.list(0, 0, whereSpecial, null);
        dpTaken = PstDpStockTaken.list(0, 0, whereDp, null);

        LeaveAppReport leaveAppReport = new LeaveAppReport();
        leaveAppReport.setFullName(employee.getFullName());
        leaveAppReport.setCommencingDate(employee.getCommencingDate().toString());
        leaveAppReport.setPayRoll(employee.getEmployeeNum().toString());
        leaveAppReport.setDepartment(department.getDepartment());
        leaveAppReport.setDevision(division.getDivision());
        leaveAppReport.setCommencingDate(Formater.formatDate(employee.getCommencingDate(), "yyyy-MM-dd"));
        leaveAppReport.setPosition(position.getPosition());
        leaveAppReport.setDateRequest(Formater.formatDate(leaveApplication.getSubmissionDate(), "yyyy-MM-dd"));
        leaveAppReport.setAnnualLeave(alStockTaken);
        leaveAppReport.setLongLeave(llStockTaken);
        leaveAppReport.setSpecialLeave(specialTaken);
        leaveAppReport.setDp(dpTaken);
        leaveAppReport.setReasonUnpaid(leaveApplication.getLeaveReason());

        if ((maxApproval == I_Leave.LEAVE_APPROVE_1 || maxApproval == I_Leave.LEAVE_APPROVE_2)) {
            if (leaveApplication.getDepHeadApproval() != 0) {
                leaveAppReport.setAppDepHead(departmentHeadName);
                try {
                    leaveAppReport.setAppDepDate(Formater.formatDate(leaveApplication.getDepHeadApproveDate(), "yyyy-MM-dd"));
                } catch (Exception e) {
                    System.out.println("EXCEPTION " + e.toString());
                    leaveAppReport.setAppDepDate("");
                }
            } else {
                leaveAppReport.setAppDepHead("");
                leaveAppReport.setAppDepDate("");
            }
        }

        if ((maxApproval == I_Leave.LEAVE_APPROVE_2 || maxApproval == I_Leave.LEAVE_APPROVE_3)) {
            if (leaveApplication.getHrManApproval() != 0) {
                leaveAppReport.setAppHrManager(HrManager);
                try {
                    leaveAppReport.setAppHrDate(Formater.formatDate(leaveApplication.getHrManApproveDate(), "yyyy-MM-dd"));
                } catch (Exception e) {
                    leaveAppReport.setAppHrDate("");
                }
            } else {
                leaveAppReport.setAppHrManager("");
                leaveAppReport.setAppHrDate("");
            }
        }

        if (maxApproval == I_Leave.LEAVE_APPROVE_3) {
            if (leaveApplication.getGmApproval() != 0) {
                leaveAppReport.setAppGm(gm);
                try {
                    leaveAppReport.setAppGmDate(Formater.formatDate(leaveApplication.getGmApprovalDate(), "yyyy-MM-dd"));
                } catch (Exception e) {
                    leaveAppReport.setAppGmDate("");
                }
            } else {
                leaveAppReport.setAppGm("");
                leaveAppReport.setAppGmDate("");
            }
        }

        // creating the document object
        Document document = new Document(PageSize.A4, 30, 30, 70, 50);

        // creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            // creating an instance of the writer
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            // get data from session 
            HttpSession sessLeaveApplication = request.getSession(true);

            // adding a Header of page
            String reportTitle = typeForm==PstLeaveApplication.EXCUSE_APPLICATION?" EXCUSE LEAVE FORM":"LEAVE APPLICATION FORM";
            HeaderFooter header = new HeaderFooter(new Phrase(reportTitle, fontHeader), false);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);

            // opening the document
            document.open();

            // add content into document
            document.add(getTableHeader(leaveAppReport));
            document.add(getTableLeave(leaveAppReport));
            //document.add(getTableContent(leaveAppReport));
            document.add(getTableNote(leaveAppReport));
            document.add(getTableSignature(leaveAppReport, employee.getOID(), appRoot, leaveApplicationId));

        } catch (DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }

        // closing the document 
        document.close();

        // we have written the pdfstream to a ByteArrayOutputStream, now going to write this outputStream to the ServletOutputStream
        // after we have set the contentlength        
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }

    /**
     * this method used to create header main
     */
    public static Table getTableHeader(LeaveAppReport leaveAppReport) throws BadElementException, DocumentException {

        Table tableApplication = new Table(6);
        tableApplication.setCellpadding(1);
        tableApplication.setCellspacing(1);
        tableApplication.setBorderColor(whiteColor);
        int widthHeader[] = {18, 3, 29, 18, 3, 29};
        tableApplication.setWidths(widthHeader);
        tableApplication.setWidth(100);

        // first line
        Cell cellApplication = new Cell(new Chunk("Date Request", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(":", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(leaveAppReport.getDateRequest(), fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("", fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        // second line
        cellApplication = new Cell(new Chunk("Name", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(":", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(leaveAppReport.getFullName(), fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("Position", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(":", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(leaveAppReport.getPosition(), fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        // third line
        cellApplication = new Cell(new Chunk("Employee No", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(":", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(leaveAppReport.getPayRoll(), fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("Department", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(":", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(leaveAppReport.getDepartment(), fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        // fourth line
        cellApplication = new Cell(new Chunk("Devision", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(":", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(leaveAppReport.getDevision(), fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("Commencing", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(":", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(leaveAppReport.getCommencingDate(), fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        // blank line
        cellApplication = new Cell(new Chunk("", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setColspan(6);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        return tableApplication;
    }

    /**
     * this method used to create report content
     */
    public static Table getTableLeave(LeaveAppReport leaveAppReport) throws BadElementException, DocumentException {

        Table tableApplication = new Table(5);
        tableApplication.setCellpadding(1);
        tableApplication.setCellspacing(1);
        tableApplication.setBorderColor(whiteColor);
        int widthHeader[] = {15, 25, 20, 20, 20};
        tableApplication.setWidths(widthHeader);
        tableApplication.setWidth(100);

        //line 1
        String strContentHeader = "";
        Cell cellApplication = new Cell(new Chunk(strContentHeader, fontContent));
        cellApplication.setColspan(5);
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        //line 2
        cellApplication = new Cell(new Chunk("NO", fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
        cellApplication.setBorderWidth(1);
        cellApplication.setBorderColor(blackColor);
        cellApplication.setBackgroundColor(colorTitle);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("STATUS LEAVE", fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
        cellApplication.setBorderWidth(1);
        cellApplication.setBorderColor(blackColor);
        cellApplication.setBackgroundColor(colorTitle);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("DATE FROM", fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
        cellApplication.setBorderWidth(1);
        cellApplication.setBorderColor(blackColor);
        cellApplication.setBackgroundColor(colorTitle);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("DATE TO", fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
        cellApplication.setBorderWidth(1);
        cellApplication.setBorderColor(blackColor);
        cellApplication.setBackgroundColor(colorTitle);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("DAY'S", fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
        cellApplication.setBorderWidth(1);
        cellApplication.setBorderColor(blackColor);
        cellApplication.setBackgroundColor(colorTitle);
        tableApplication.addCell(cellApplication);

        int no = 1;

        //line 3
        for (int indexAL = 0; indexAL < leaveAppReport.getAnnualLeave().size(); indexAL++) {
                        
            AlStockTaken alStockTaken = (AlStockTaken) leaveAppReport.getAnnualLeave().get(indexAL);

            cellApplication = new Cell(new Chunk(""+no, fontContentBold));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
            
            if(indexAL == 0){
                cellApplication = new Cell(new Chunk("ANNUAL LEAVE", fontContentBold));
            }else{
                cellApplication = new Cell(new Chunk("", fontContentBold));
            }    
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(alStockTaken.getTakenDate() == null ? "" : Formater.formatDate(alStockTaken.getTakenDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            
            try {
                cellApplication = new Cell(new Chunk(alStockTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(alStockTaken.getTakenFinnishDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            
            //update by devin 2014-03-24
            
            try {
                 float qty=0;
                 float totalQty =0;
                 qty= DateCalc.workDayDifference(alStockTaken.getTakenDate() , alStockTaken.getTakenFinnishDate(), 8f);
                cellApplication = new Cell(new Chunk(""+qty, fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("0", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
            
            no++;
        }
        
        //line 4
        for (int indexLL = 0; indexLL < leaveAppReport.getLongLeave().size(); indexLL++) {
                        
            LlStockTaken llStockTaken = (LlStockTaken) leaveAppReport.getLongLeave().get(indexLL);

            cellApplication = new Cell(new Chunk(""+no, fontContentBold));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
            
            if(indexLL == 0){
                cellApplication = new Cell(new Chunk("LONG LEAVE", fontContentBold));
            }else{
                cellApplication = new Cell(new Chunk("", fontContentBold));
            }    
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(llStockTaken.getTakenDate() == null ? "" : Formater.formatDate(llStockTaken.getTakenDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            
            try {
                cellApplication = new Cell(new Chunk(llStockTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(llStockTaken.getTakenFinnishDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

             //update by devin 2014-03-24
            
            try {
                 float qty=0;
                 float totalQty =0;
                 qty= DateCalc.workDayDifferenceLl(llStockTaken.getTakenDate() , llStockTaken.getTakenFinnishDate(), 8f);
                cellApplication = new Cell(new Chunk(""+qty, fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("0", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
            
            no++;
        }
        
        
        
        //line 4
        for (int indexSP = 0; indexSP < leaveAppReport.getSpecialLeave().size(); indexSP++) {
                        
            SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken) leaveAppReport.getSpecialLeave().get(indexSP);

            cellApplication = new Cell(new Chunk(""+no, fontContentBold));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
            
            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
            String typeLeave = "";
            try{
                scheduleSymbol = PstScheduleSymbol.fetchExc(specialUnpaidLeaveTaken.getScheduledId());
                typeLeave = scheduleSymbol.getSchedule();
            }catch(Exception e){
                System.out.println("EXCEPTION "+e.toString());
            }
           
            cellApplication = new Cell(new Chunk(""+typeLeave, fontContentBold));           
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(specialUnpaidLeaveTaken.getTakenDate() == null ? "" : Formater.formatDate(specialUnpaidLeaveTaken.getTakenDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            
            try {
                cellApplication = new Cell(new Chunk(specialUnpaidLeaveTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(specialUnpaidLeaveTaken.getTakenFinnishDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            
             //update by devin 2014-03-24
            
            try {
                 float qty=0;
                 float totalQty =0;
                 qty= DateCalc.workDayDifference(specialUnpaidLeaveTaken.getTakenDate() , specialUnpaidLeaveTaken.getTakenFinnishDate(), 8f);
                cellApplication = new Cell(new Chunk(""+qty, fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("0", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
            
            no++;
        }
        
        for (int indexDP = 0; indexDP < leaveAppReport.getDp().size(); indexDP++) {
                        
            DpStockTaken dpStockTaken = (DpStockTaken) leaveAppReport.getDp().get(indexDP);

            cellApplication = new Cell(new Chunk(""+no, fontContentBold));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
           
            cellApplication = new Cell(new Chunk("DAY OF PAYMENT", fontContentBold));           
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(dpStockTaken.getTakenDate() == null ? "" : Formater.formatDate(dpStockTaken.getTakenDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            
            try {
                cellApplication = new Cell(new Chunk(dpStockTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(dpStockTaken.getTakenFinnishDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            
           //update by devin 2014-03-24
            
            try {
                 float qty=0;
                 float totalQty =0;
                 qty= DateCalc.workDayDifference(dpStockTaken.getTakenDate() , dpStockTaken.getTakenFinnishDate(), 8f);
                cellApplication = new Cell(new Chunk(""+qty, fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("0", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
            cellApplication.setBorderWidth(1);
            cellApplication.setBorderColor(blackColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
            
            no++;
        }

        return tableApplication;
        
    }

    /**
     * this method used to create report content
     */
    public static Table getTableContent(LeaveAppReport leaveAppReport) throws BadElementException, DocumentException {
        Table tableApplication = new Table(7);
        tableApplication.setCellpadding(1);
        tableApplication.setCellspacing(1);
        tableApplication.setBorderColor(whiteColor);
        int widthHeader[] = {20, 20, 20, 10, 20, 20, 10};
        tableApplication.setWidths(widthHeader);
        tableApplication.setWidth(100);

        // first line
        String strContentHeader = "";
        Cell cellApplication = new Cell(new Chunk(strContentHeader, fontContent));
        cellApplication.setColspan(7);
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        // value untuk annual leave
        for (int indexAL = 0; indexAL < leaveAppReport.getAnnualLeave().size(); indexAL++) {

            AlStockTaken alStockTaken = (AlStockTaken) leaveAppReport.getAnnualLeave().get(indexAL);

            if (indexAL == 0) {
                cellApplication = new Cell(new Chunk("Annual Leave", fontContent));
                cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellApplication.setBorderColor(whiteColor);
                cellApplication.setBackgroundColor(whiteColor);
                tableApplication.addCell(cellApplication);
            } else {
                cellApplication = new Cell(new Chunk("", fontContent));
                cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellApplication.setBorderColor(whiteColor);
                cellApplication.setBackgroundColor(whiteColor);
                tableApplication.addCell(cellApplication);
            }

            cellApplication = new Cell(new Chunk("Date from : ", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(alStockTaken.getTakenDate() == null ? "" : Formater.formatDate(alStockTaken.getTakenDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Date to : ", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(alStockTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(alStockTaken.getTakenFinnishDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);


            cellApplication = new Cell(new Chunk(""+alStockTaken.getTakenQty(), fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        }

        //value untuk long leave
        for (int indexLL = 0; indexLL < leaveAppReport.getLongLeave().size(); indexLL++) {

            LlStockTaken llStockTaken = (LlStockTaken) leaveAppReport.getLongLeave().get(indexLL);

            if (indexLL == 0) {
                cellApplication = new Cell(new Chunk("Long Leave", fontContent));
            } else {
                cellApplication = new Cell(new Chunk("", fontContent));
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Date from : ", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(llStockTaken.getTakenDate() == null ? "" : Formater.formatDate(llStockTaken.getTakenDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Date to", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk(llStockTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(llStockTaken.getTakenFinnishDate(), "yyyy-MM-dd"), fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk(""+llStockTaken.getTakenQty(), fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);


        }

        //value untuk special unpaid leave taken

        for (int indexSP = 0; indexSP < leaveAppReport.getSpecialLeave().size(); indexSP++) {

            SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken) leaveAppReport.getSpecialLeave().get(indexSP);

            if (indexSP == 0) {
                cellApplication = new Cell(new Chunk("Special Leave", fontContent));
            } else {
                cellApplication = new Cell(new Chunk("", fontContent));
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Date from : ", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(specialUnpaidLeaveTaken.getTakenDate() == null ? "" : Formater.formatDate(specialUnpaidLeaveTaken.getTakenDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Date to : ", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);


            try {
                cellApplication = new Cell(new Chunk(specialUnpaidLeaveTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(specialUnpaidLeaveTaken.getTakenFinnishDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk(""+specialUnpaidLeaveTaken.getTakenQty(), fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);


        }

        for (int indexDP = 0; indexDP < leaveAppReport.getDp().size(); indexDP++) {
            DpStockTaken dpStockTaken = (DpStockTaken) leaveAppReport.getDp().get(indexDP);

            if (indexDP == 0) {
                cellApplication = new Cell(new Chunk("Day of Payment", fontContent));
            } else {
                cellApplication = new Cell(new Chunk("", fontContent));
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Date from : ", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(dpStockTaken.getTakenDate() == null ? "" : Formater.formatDate(dpStockTaken.getTakenDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Date to : ", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(dpStockTaken.getTakenFinnishDate() == null ? "" : Formater.formatDate(dpStockTaken.getTakenFinnishDate(), "yyyy-MM-dd"), fontContent));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContent));
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk(""+dpStockTaken.getTakenQty(), fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
        }

        
        
        return tableApplication;
    }

    /**
     * this method used to create report dp taken approved
     */
    public static Table getTableNote(LeaveAppReport leaveAppReport) throws BadElementException, DocumentException {

        Table tableApplication = new Table(4);
        tableApplication.setCellpadding(1);
        tableApplication.setCellspacing(1);
        tableApplication.setBorderColor(whiteColor);
        int widthHeader[] = {20, 10, 50, 20};
        tableApplication.setWidths(widthHeader);
        tableApplication.setWidth(100);

        // first line
        String strContentHeader = "";

        Cell cellApplication = new Cell(new Chunk(strContentHeader, fontContent));
        cellApplication.setColspan(4);
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        // second line  

        cellApplication = new Cell(new Chunk("Reason ", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk(" : ", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        try {
            if (leaveAppReport.getReasonUnpaid() == null || leaveAppReport.getReasonUnpaid().equals("")) {
                cellApplication = new Cell(new Chunk("-", fontContent));
            } else {
                cellApplication = new Cell(new Chunk(leaveAppReport.getReasonUnpaid(), fontContent));
            }

        } catch (Exception e) {
            cellApplication = new Cell(new Chunk("-", fontContent));
        }

        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        return tableApplication;
    }

    /**
     * this method used to create report signature
     */
    public static Table getTableSignature(LeaveAppReport leaveAppReport, long employeeId, String approot, long leaveAppId) throws BadElementException, DocumentException {

        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        int maxApproval = leaveConfig.getMaxApproval(employeeId); // mendapatkan jumlah approval
        LeaveApplication leaveApplication = new LeaveApplication();

        try {
            leaveApplication = PstLeaveApplication.fetchExc(leaveAppId);
        } catch (Exception e) {
            System.out.println("EXCPETION " + e.toString());
        }

        Table tableApplication = new Table(3);
        tableApplication.setCellpadding(1);
        tableApplication.setCellspacing(1);
        tableApplication.setBorderColor(whiteColor);
        int widthHeader[] = {33, 34, 33};
        tableApplication.setWidths(widthHeader);
        tableApplication.setWidth(100);

        Cell cellApplication = new Cell(new Chunk("", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setColspan(3);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        cellApplication = new Cell(new Chunk("", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setColspan(3);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        // first line

        cellApplication = new Cell(new Chunk("Requested by,", fontContent));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        if (maxApproval == I_Leave.LEAVE_APPROVE_1) {

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Approved by,", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        } else if (maxApproval == I_Leave.LEAVE_APPROVE_2) {

            cellApplication = new Cell(new Chunk("Approved by,", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Approved by,", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        } else if (maxApproval == I_Leave.LEAVE_APPROVE_3) {

            cellApplication = new Cell(new Chunk("Approved by,", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("Approved by,", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
        }

        //third line

        cellApplication = new Cell(new Chunk("", fontBoldSmall));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        if (maxApproval == I_Leave.LEAVE_APPROVE_1) {

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("signature", fontBoldSmall));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        } else if (maxApproval == I_Leave.LEAVE_APPROVE_2) {

            cellApplication = new Cell(new Chunk("signature", fontBoldSmall));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("signature", fontBoldSmall));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);


        } else if (maxApproval == I_Leave.LEAVE_APPROVE_3) {

            cellApplication = new Cell(new Chunk("signature", fontBoldSmall));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("signature", fontBoldSmall));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        }

        //fourth line

        cellApplication = new Cell(new Chunk("( Employee )", fontBoldSmallItalic));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        if (maxApproval == I_Leave.LEAVE_APPROVE_1) {

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("( Department Head )", fontBoldSmallItalic));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        } else if (maxApproval == I_Leave.LEAVE_APPROVE_2) {

            cellApplication = new Cell(new Chunk("( Department Head )", fontBoldSmallItalic));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("( HR Manager )", fontBoldSmallItalic));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);


        } else if (maxApproval == I_Leave.LEAVE_APPROVE_3) {

            cellApplication = new Cell(new Chunk("( HR Manager )", fontBoldSmall));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            cellApplication = new Cell(new Chunk("( General Manager )", fontBoldSmallItalic));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);
        }

        //******************** menampilkan asessment ***********************************


        String strPathEmp = "";

        String pathImgAssign = imagesRoot + "imgassign/";
        ;

        Image imgAsgEmp = null;

        ImageAssign imgEmp = PstImageAssign.getImageAssignByEmp(employeeId);

        if (imgEmp.getPath() != null && imgEmp.getPath().length() > 0) {

            strPathEmp = pathImgAssign + imgEmp.getPath();

            try {
                imgAsgEmp = Image.getInstance(strPathEmp);
                imgAsgEmp.setAlignment(Image.ALIGN_CENTER);
                imgAsgEmp.scaleAbsoluteHeight(60);
                imgAsgEmp.scaleAbsoluteWidth(100);
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
        }
        cellApplication = new Cell(new Chunk(String.valueOf(""), fontContent));
        if (imgAsgEmp != null) {
            cellApplication.add(imgAsgEmp);
        } else {
            cellApplication = new Cell(new Chunk("", fontContent));
        }
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_TOP);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        if (maxApproval == I_Leave.LEAVE_APPROVE_1) {

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            /************************* department head assessment ***********************/
            Employee assesment_dp = new Employee();
            ImageAssign imgAssesment_dp = new ImageAssign();
            String strPathAssesment_dp = "";
            Image imgAsgAssesment_dp = null;

            try {
                assesment_dp = PstEmployee.fetchExc(leaveApplication.getDepHeadApproval());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            imgAssesment_dp = PstImageAssign.getImageAssignByEmp(assesment_dp.getOID());

            if (imgAssesment_dp.getPath() != null && imgAssesment_dp.getPath().length() > 0) {
                strPathAssesment_dp = pathImgAssign + imgAssesment_dp.getPath();
            }

            try {
                imgAsgAssesment_dp = Image.getInstance(strPathAssesment_dp);
                imgAsgAssesment_dp.setAlignment(Image.ALIGN_CENTER);
                imgAsgAssesment_dp.scaleAbsoluteHeight(80);
                imgAsgAssesment_dp.scaleAbsoluteWidth(150);
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            cellApplication = new Cell(new Chunk(String.valueOf(""), fontContent));
            if (imgAsgAssesment_dp != null) {
                cellApplication.add(imgAsgAssesment_dp);
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_TOP);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);


        } else if (maxApproval == I_Leave.LEAVE_APPROVE_2) {

            /************************* department head assessment ***********************/
            Employee assesment_dp = new Employee();
            ImageAssign imgAssesment_dp = new ImageAssign();
            String strPathAssesment_dp = "";
            Image imgAsgAssesment_dp = null;

            try {
                assesment_dp = PstEmployee.fetchExc(leaveApplication.getDepHeadApproval());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            imgAssesment_dp = PstImageAssign.getImageAssignByEmp(assesment_dp.getOID());

            if (imgAssesment_dp.getPath() != null && imgAssesment_dp.getPath().length() > 0) {
                strPathAssesment_dp = pathImgAssign + imgAssesment_dp.getPath();
            }
           if(strPathAssesment_dp!=null && strPathAssesment_dp.length()<1){
            try {
                imgAsgAssesment_dp = Image.getInstance(strPathAssesment_dp);
                imgAsgAssesment_dp.setAlignment(Image.ALIGN_CENTER);
                imgAsgAssesment_dp.scaleAbsoluteHeight(80);
                imgAsgAssesment_dp.scaleAbsoluteWidth(150);
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
           }
            cellApplication = new Cell(new Chunk(String.valueOf(""), fontContent));
            if (imgAsgAssesment_dp != null) {
                cellApplication.add(imgAsgAssesment_dp);
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_TOP);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            /************************* hr manager assessment ***********************/
            Employee assesment_hr = new Employee();
            ImageAssign imgAssesment_hr = new ImageAssign();
            String strPathAssesment_hr = "";
            Image imgAsgAssesment_hr = null;

            try {
                assesment_hr = PstEmployee.fetchExc(leaveApplication.getHrManApproval());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            imgAssesment_hr = PstImageAssign.getImageAssignByEmp(assesment_hr.getOID());

            if (imgAssesment_hr.getPath() != null && imgAssesment_hr.getPath().length() > 0) {
                strPathAssesment_hr = pathImgAssign + imgAssesment_hr.getPath();
            }

            try {
                imgAsgAssesment_hr = Image.getInstance(strPathAssesment_hr);
                imgAsgAssesment_hr.setAlignment(Image.ALIGN_CENTER);
                imgAsgAssesment_hr.scaleAbsoluteHeight(80);
                imgAsgAssesment_hr.scaleAbsoluteWidth(150);
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            cellApplication = new Cell(new Chunk(String.valueOf(""), fontContent));
            if (imgAsgAssesment_hr != null) {
                cellApplication.add(imgAsgAssesment_hr);
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_TOP);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        } else if (maxApproval == I_Leave.LEAVE_APPROVE_3) {

            /************************* hr manager assessment ***********************/
            Employee assesment_hr = new Employee();
            ImageAssign imgAssesment_hr = new ImageAssign();
            String strPathAssesment_hr = "";
            Image imgAsgAssesment_hr = null;

            try {
                assesment_hr = PstEmployee.fetchExc(leaveApplication.getHrManApproval());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            imgAssesment_hr = PstImageAssign.getImageAssignByEmp(assesment_hr.getOID());

            if (imgAssesment_hr.getPath() != null && imgAssesment_hr.getPath().length() > 0) {
                strPathAssesment_hr = pathImgAssign + imgAssesment_hr.getPath();
            }

            try {
                imgAsgAssesment_hr = Image.getInstance(strPathAssesment_hr);
                imgAsgAssesment_hr.setAlignment(Image.ALIGN_CENTER);
                imgAsgAssesment_hr.scaleAbsoluteHeight(80);
                imgAsgAssesment_hr.scaleAbsoluteWidth(150);
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            cellApplication = new Cell(new Chunk(String.valueOf(""), fontContent));
            if (imgAsgAssesment_hr != null) {
                cellApplication.add(imgAsgAssesment_hr);
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_TOP);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            /************************* gm assessment ***********************/
            Employee assesment_gm = new Employee();
            ImageAssign imgAssesment_gm = new ImageAssign();
            String strPathAssesment_gm = "";
            Image imgAsgAssesment_gm = null;

            try {
                assesment_gm = PstEmployee.fetchExc(leaveApplication.getGmApproval());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            imgAssesment_gm = PstImageAssign.getImageAssignByEmp(assesment_gm.getOID());

            if (imgAssesment_gm.getPath() != null && imgAssesment_gm.getPath().length() > 0) {
                strPathAssesment_gm = pathImgAssign + imgAssesment_gm.getPath();
            }

            try {
                imgAsgAssesment_gm = Image.getInstance(strPathAssesment_gm);
                imgAsgAssesment_gm.setAlignment(Image.ALIGN_CENTER);
                imgAsgAssesment_gm.scaleAbsoluteHeight(80);
                imgAsgAssesment_gm.scaleAbsoluteWidth(150);
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            cellApplication = new Cell(new Chunk(String.valueOf(""), fontContent));
            if (imgAsgAssesment_gm != null) {
                cellApplication.add(imgAsgAssesment_gm);
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_TOP);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        }


        //five line
        cellApplication = new Cell(new Chunk(leaveAppReport.getFullName(), fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        if (maxApproval == I_Leave.LEAVE_APPROVE_1) {

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(leaveAppReport.getAppDepHead().equals("") ? "..............." : leaveAppReport.getAppDepHead(), fontContentBold));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContentBold));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);


        } else if (maxApproval == I_Leave.LEAVE_APPROVE_2) {
            try {
                cellApplication = new Cell(new Chunk(leaveAppReport.getAppDepHead().equals("") ? "..............." : leaveAppReport.getAppDepHead(), fontContentBold));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContentBold));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(leaveAppReport.getAppHrManager().equals("") ? "..............." : leaveAppReport.getAppHrManager(), fontContentBold));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContentBold));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        } else if (maxApproval == I_Leave.LEAVE_APPROVE_3) {

            cellApplication = new Cell(new Chunk(leaveAppReport.getAppHrManager().equals("") ? "..............." : leaveAppReport.getAppHrManager(), fontContentBold));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(leaveAppReport.getAppGm().equals("") ? "..............." : leaveAppReport.getAppGm(), fontContentBold));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontContentBold));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        }

        //next line
        cellApplication = new Cell(new Chunk("", fontContentBold));
        cellApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellApplication.setBorderColor(whiteColor);
        cellApplication.setBackgroundColor(whiteColor);
        tableApplication.addCell(cellApplication);

        if (maxApproval == I_Leave.LEAVE_APPROVE_1) {

            cellApplication = new Cell(new Chunk("", fontContent));
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(leaveAppReport.getAppDepDate().equals("") ? "" : leaveAppReport.getAppDepDate(), fontBoldSmall));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontBoldSmall));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);


        } else if (maxApproval == I_Leave.LEAVE_APPROVE_2) {
            try {
                cellApplication = new Cell(new Chunk(leaveAppReport.getAppDepDate().equals("") ? "" : leaveAppReport.getAppDepDate(), fontBoldSmall));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontBoldSmall));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(leaveAppReport.getAppHrDate().equals("") ? "" : leaveAppReport.getAppHrDate(), fontBoldSmall));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontBoldSmall));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        } else if (maxApproval == I_Leave.LEAVE_APPROVE_3) {
            try{
                cellApplication = new Cell(new Chunk(leaveAppReport.getAppHrDate().equals("") ? "" : leaveAppReport.getAppHrDate(), fontBoldSmall));
            }catch(Exception e){
                cellApplication = new Cell(new Chunk("", fontBoldSmall));
                System.out.println("EXCEPTION " + e.toString());
            }    
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

            try {
                cellApplication = new Cell(new Chunk(leaveAppReport.getAppGmDate().equals("") ? "" : leaveAppReport.getAppGmDate(), fontBoldSmall));
            } catch (Exception e) {
                cellApplication = new Cell(new Chunk("", fontBoldSmall));
                System.out.println("EXCEPTION " + e.toString());
            }
            cellApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellApplication.setBorderColor(whiteColor);
            cellApplication.setBackgroundColor(whiteColor);
            tableApplication.addCell(cellApplication);

        }

        return tableApplication;
    }
}
