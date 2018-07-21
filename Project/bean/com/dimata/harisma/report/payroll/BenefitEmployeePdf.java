/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.payroll;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.payroll.BenefitConfig;
import com.dimata.harisma.entity.payroll.BenefitConfigDeduction;
import com.dimata.harisma.entity.payroll.BenefitPeriod;
import com.dimata.harisma.entity.payroll.PstBenefitConfig;
import com.dimata.harisma.entity.payroll.PstBenefitConfigDeduction;
import com.dimata.harisma.entity.payroll.PstBenefitPeriod;
import com.dimata.harisma.session.payroll.SessBenefitLevel;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description :
 * Date :
 * @author Hendra Putu
 */
public class BenefitEmployeePdf extends HttpServlet {
    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /** Destroys the servlet.
     */
    public void destroy() {

    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        System.out.println("===| BenefitEmployeePdf |===");
        long oidBenefitPeriod = FRMQueryString.requestLong(request, "oid");
        BenefitPeriod benefitPeriod = new BenefitPeriod();
        try {
            benefitPeriod = PstBenefitPeriod.fetchExc(oidBenefitPeriod);
        } catch (Exception exc) {
            System.out.println(" BenefitPeriod : \n" + exc.toString());
        }
        
        Color border = new Color(0x00, 0x00, 0x00);

        // setting some fonts in the color chosen by the user
        Font fontHeaderBig = new Font(Font.HELVETICA, 12, Font.BOLD, border);
        Font fontHeaderSmall = new Font(Font.HELVETICA, 11, Font.NORMAL, border);
        Font fontHeader = new Font(Font.HELVETICA, 14, Font.BOLD, border);
        Font fontContent = new Font(Font.HELVETICA, 12, Font.BOLD, border);
        Font fontTableHeader = new Font(Font.HELVETICA, 11, Font.BOLD, border);
        Font fontTable = new Font(Font.HELVETICA, 11, Font.NORMAL, border);
        Font tableContent = new Font(Font.HELVETICA, 12, Font.NORMAL, border);
        
        Color bgColor = new Color(240, 240, 240);

        Color blackColor = new Color(0, 0, 0);

        Color putih = new Color(250, 250, 250);

        Document document = new Document(PageSize.A4, 30, 30, 50, 50);
        //Document document = new Document(PageSize.A4.rotate(), 10, 10, 30, 30);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);

            // step 3.1: adding some metadata to the document
            document.addSubject("This is a subject.");
            document.addSubject("This is a subject two.");

            String str = PstSystemProperty.getValueByName("PRINT_HEADER");

            //Header 
            HeaderFooter header = new HeaderFooter(new Phrase(str, fontHeader), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            // Footer
            HeaderFooter footer = new HeaderFooter(new Phrase("printed : " + Formater.formatDate(new Date(), "dd/MM/yyyy"), fontHeaderSmall), false);
            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
            document.setFooter(footer);
            
            // step 3.4: opening the document
            document.open();

            Table benefitTop = new Table(1);
            benefitTop.setWidth(100);
            benefitTop.setBorderColor(new Color(255, 255, 255));
            benefitTop.setBorderWidth(1);
            benefitTop.setAlignment(1);
            benefitTop.setCellpadding(0);
            benefitTop.setCellspacing(1);

            //0
            Cell titleCellTop = new Cell(new Chunk("Service Charge Period of "+benefitPeriod.getPeriodFrom()+" / "+benefitPeriod.getPeriodTo(), tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            benefitTop.addCell(titleCellTop);
            
            BenefitConfig bConfig = PstBenefitConfig.fetchExc(benefitPeriod.getBenefitConfigId());
            
            titleCellTop = new Cell(new Chunk("Benefit Configuration name : "+bConfig.getTitle()+""));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            benefitTop.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("Total Revenue : "+Formater.formatNumberMataUang(benefitPeriod.getTotalRevenue(), "Rp")+""));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            benefitTop.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("Deduction",fontContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            benefitTop.addCell(titleCellTop);
            
            document.add(benefitTop);
            
            int deductionHeaderTop[] = {1, 12, 9};
            Table deductionTable = new Table(3);
            deductionTable.setWidth(100);
            deductionTable.setWidths(deductionHeaderTop);
            deductionTable.setBorderColor(new Color(255, 255, 255));
            deductionTable.setBorderWidth(1);
            deductionTable.setAlignment(1);
            deductionTable.setCellpadding(0);
            deductionTable.setCellspacing(1);
            
            Cell deductionCell;
            
            double forDistribute = 0;
            String whereDeduction = "BENEFIT_CONFIG_ID = " + benefitPeriod.getBenefitConfigId();
            String orderDeduction = "DEDUCTION_INDEX ASC";
            int nomor = 1;
            double deductionResult = 0;
            Vector listDeduction = PstBenefitConfigDeduction.list(0, 0, whereDeduction, orderDeduction);
            double[] arrDeduction = new double[listDeduction.size()+1];
            arrDeduction[0] = benefitPeriod.getTotalRevenue();
            for(int i=0; i<listDeduction.size();i++){
                BenefitConfigDeduction deduction = (BenefitConfigDeduction)listDeduction.get(i);
                if (deduction.getDeductionReference() == 0){
                    arrDeduction[deduction.getDeductionIndex()] = benefitPeriod.getTotalRevenue() - ((deduction.getDeductionPercent() * benefitPeriod.getTotalRevenue())/100);
                    deductionResult = (deduction.getDeductionPercent() * benefitPeriod.getTotalRevenue())/100;
                } else {
                    BenefitConfigDeduction ded = PstBenefitConfigDeduction.fetchExc(deduction.getDeductionReference());
                    arrDeduction[deduction.getDeductionIndex()] = arrDeduction[deduction.getDeductionIndex()-1]-((deduction.getDeductionPercent() * arrDeduction[ded.getDeductionIndex()])/100);
                    deductionResult = (deduction.getDeductionPercent() * arrDeduction[ded.getDeductionIndex()])/100;
                }
                forDistribute = arrDeduction[deduction.getDeductionIndex()];


                deductionCell = new Cell(new Chunk(""+nomor, tableContent));
                deductionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                deductionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                deductionCell.setBorderColor(new Color(255, 255, 255));
                deductionTable.addCell(deductionCell);

                deductionCell = new Cell(new Chunk(""+deduction.getDeductionPercent()+"% x "+deduction.getDeductionDescription(), tableContent));
                deductionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                deductionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                deductionCell.setBorderColor(new Color(255, 255, 255));
                deductionTable.addCell(deductionCell);

                deductionCell = new Cell(new Chunk(""+Formater.formatNumberMataUang(deductionResult, "Rp"), tableContent));
                deductionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                deductionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                deductionCell.setBorderColor(new Color(255, 255, 255));
                deductionTable.addCell(deductionCell);
                /* deduction result */
                // colom 1
                deductionCell = new Cell(new Chunk("", tableContent));
                deductionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                deductionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                deductionCell.setBorderColor(new Color(255, 255, 255));
                deductionTable.addCell(deductionCell);
                
                // colom 2
                deductionCell = new Cell(new Chunk("", tableContent));
                deductionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                deductionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                deductionCell.setBorderColor(new Color(255, 255, 255));
                deductionTable.addCell(deductionCell);
                
                // colom 3
                deductionCell = new Cell(new Chunk(""+Formater.formatNumberMataUang(arrDeduction[deduction.getDeductionIndex()],"Rp"), tableContent));
                deductionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                deductionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                deductionCell.setBorderColor(new Color(255, 255, 255));
                deductionTable.addCell(deductionCell);

                nomor++;
            }

            document.add(deductionTable);
            
            //Service Charge Distribution
            int distributionHeaderTop[] = {2, 11, 4, 9};
            Table distributionTable = new Table(4);
            distributionTable.setWidth(100);
            distributionTable.setWidths(distributionHeaderTop);
            distributionTable.setBorderColor(new Color(255, 255, 255));
            distributionTable.setBorderWidth(1);
            distributionTable.setAlignment(1);
            distributionTable.setCellpadding(0);
            distributionTable.setCellspacing(1);

            Cell distCellTop = new Cell(new Chunk("Service Charge Distribution",fontContent));
            distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            distCellTop.setColspan(4);
            distCellTop.setBorderColor(new Color(255, 255, 255));
            distributionTable.addCell(distCellTop);
            
            
                BenefitConfig distribution = PstBenefitConfig.fetchExc(benefitPeriod.getBenefitConfigId());
                int totalEmployee = PstBenefitPeriod.getTotalEmployee(benefitPeriod.getBenefitConfigId(), benefitPeriod.getPeriodId());
                /* data approve */
                long bcApproveId1 = distribution.getApprove1EmpId();
                long bcApproveId2 = distribution.getApprove2EmpId();
                int totalPoint = 0;
                Vector listPoint = PstBenefitPeriod.getTotalPoint(benefitPeriod.getBenefitConfigId(), benefitPeriod.getPeriodId());
                if(listPoint != null && listPoint.size()>0){
                    for(int i=0; i<listPoint.size(); i++){
                        SessBenefitLevel pLevel = (SessBenefitLevel)listPoint.get(i);
                        totalPoint = totalPoint + pLevel.getSumPoint();
                    }
                }
                double distributionResult1 = ((distribution.getDistributionPercent1() * forDistribute)/100)/totalEmployee;
                double distributionResult2 = ((distribution.getDistributionPercent2() * forDistribute)/100)/totalPoint;

                distCellTop = new Cell(new Chunk("1)"));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                distCellTop = new Cell(new Chunk(""+distribution.getDistributionPart1()));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setColspan(3);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                /* part 1*/
                distCellTop = new Cell(new Chunk(" "));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                distCellTop = new Cell(new Chunk(""+distribution.getDistributionPercent1()+"% x "+Formater.formatNumberMataUang(forDistribute,"Rp")));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                distCellTop = new Cell(new Chunk(""+totalEmployee));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                distCellTop = new Cell(new Chunk(""+Formater.formatNumberMataUang(distributionResult1,"Rp")));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                /* part 2*/
                distCellTop = new Cell(new Chunk("2)"));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                distCellTop = new Cell(new Chunk(""+distribution.getDistributionPart2()));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setColspan(3);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                distCellTop = new Cell(new Chunk(" "));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                distCellTop = new Cell(new Chunk(""+distribution.getDistributionPercent2()+"% x "+Formater.formatNumberMataUang(forDistribute,"Rp")));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                distCellTop = new Cell(new Chunk(""+totalPoint));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);
                
                distCellTop = new Cell(new Chunk(""+Formater.formatNumberMataUang(distributionResult2,"Rp")));
                distCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                distCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                distCellTop.setBorderColor(new Color(255, 255, 255));
                distributionTable.addCell(distCellTop);

            document.add(distributionTable);
            
            
            //Benefit Employee
            int employeeHeaderTop[] = {3, 2, 6, 6, 6, 7, 3};
            Table benefitEmpTable = new Table(7);
            benefitEmpTable.setWidth(100);
            benefitEmpTable.setWidths(employeeHeaderTop);
            benefitEmpTable.setBorderColor(new Color(255, 255, 255));
            benefitEmpTable.setBorderWidth(1);
            benefitEmpTable.setAlignment(1);
            benefitEmpTable.setBorder(1);
            benefitEmpTable.setCellpadding(0);
            benefitEmpTable.setCellspacing(1);

            Cell benefitEmpCellTop = new Cell(new Chunk(" ",fontContent));
            benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            benefitEmpCellTop.setColspan(7);
            benefitEmpCellTop.setBorderColor(new Color(255, 255, 255));
            benefitEmpTable.addCell(benefitEmpCellTop);
            
            benefitEmpCellTop = new Cell(new Chunk("Level",fontTableHeader));
            benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            benefitEmpCellTop.setBorder(1);
            benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
            benefitEmpTable.addCell(benefitEmpCellTop);
            
            benefitEmpCellTop = new Cell(new Chunk("Level Point",fontTableHeader));
            benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            benefitEmpCellTop.setBorder(1);
            benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
            benefitEmpTable.addCell(benefitEmpCellTop);
            
            benefitEmpCellTop = new Cell(new Chunk("Flat Service Charge",fontTableHeader));
            benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            benefitEmpCellTop.setBorder(1);
            benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
            benefitEmpTable.addCell(benefitEmpCellTop);
            
            benefitEmpCellTop = new Cell(new Chunk("Point",fontTableHeader));
            benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            benefitEmpCellTop.setBorder(1);
            benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
            benefitEmpTable.addCell(benefitEmpCellTop);
            
            benefitEmpCellTop = new Cell(new Chunk("Service By Point",fontTableHeader));
            benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            benefitEmpCellTop.setBorder(1);
            benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
            benefitEmpTable.addCell(benefitEmpCellTop);
            
            benefitEmpCellTop = new Cell(new Chunk("Total Service Charge",fontTableHeader));
            benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            benefitEmpCellTop.setBorder(1);
            benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
            benefitEmpTable.addCell(benefitEmpCellTop);
            
            benefitEmpCellTop = new Cell(new Chunk("Head Count",fontTableHeader));
            benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            benefitEmpCellTop.setBorder(1);
            benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
            benefitEmpTable.addCell(benefitEmpCellTop);
            
            double serviceByPoint = 0;
            double totalServiceCharge = 0;
            for(int i=0; i<listPoint.size();i++){
                SessBenefitLevel pointLevel = (SessBenefitLevel)listPoint.get(i);
                serviceByPoint = pointLevel.getLevelPoint() * distributionResult2;
                totalServiceCharge = distributionResult1 + serviceByPoint;
                
                benefitEmpCellTop = new Cell(new Chunk(""+pointLevel.getLevel(),fontTable));
                benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                benefitEmpCellTop.setBorder(1);
                benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
                benefitEmpTable.addCell(benefitEmpCellTop);

                benefitEmpCellTop = new Cell(new Chunk(""+pointLevel.getLevelPoint(),fontTable));
                benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                benefitEmpCellTop.setBorder(1);
                benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
                benefitEmpTable.addCell(benefitEmpCellTop);//

                benefitEmpCellTop = new Cell(new Chunk(""+Formater.formatNumberMataUang(distributionResult1,"Rp"),fontTable));
                benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                benefitEmpCellTop.setBorder(1);
                benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
                benefitEmpTable.addCell(benefitEmpCellTop);///

                benefitEmpCellTop = new Cell(new Chunk(""+Formater.formatNumberMataUang(distributionResult2,"Rp"),fontTable));
                benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                benefitEmpCellTop.setBorder(1);
                benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
                benefitEmpTable.addCell(benefitEmpCellTop);////

                benefitEmpCellTop = new Cell(new Chunk(""+Formater.formatNumberMataUang(serviceByPoint,"Rp"),fontTable));
                benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                benefitEmpCellTop.setBorder(1);
                benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
                benefitEmpTable.addCell(benefitEmpCellTop);/////

                benefitEmpCellTop = new Cell(new Chunk(""+Formater.formatNumberMataUang(totalServiceCharge,"Rp"),fontTable));
                benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                benefitEmpCellTop.setBorder(1);
                benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
                benefitEmpTable.addCell(benefitEmpCellTop);

                benefitEmpCellTop = new Cell(new Chunk(""+pointLevel.getCountPoint(),fontTable));
                benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                benefitEmpCellTop.setBorder(1);
                benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
                benefitEmpTable.addCell(benefitEmpCellTop);

            }
            benefitEmpCellTop = new Cell(new Chunk(" ",fontTable));
            benefitEmpCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            benefitEmpCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            benefitEmpCellTop.setBorder(1);
            benefitEmpCellTop.setColspan(7);
            benefitEmpCellTop.setBorderColor(new Color(0, 0, 0));
            benefitEmpTable.addCell(benefitEmpCellTop);
            document.add(benefitEmpTable);
            
            //Approve
            int approveHeaderTop[] = {20,20};
            Table approveTable = new Table(2);
            approveTable.setWidth(100);
            approveTable.setWidths(approveHeaderTop);
            approveTable.setBorderColor(new Color(255, 255, 255));
            approveTable.setBorderWidth(1);
            approveTable.setAlignment(1);
            approveTable.setBorder(1);
            approveTable.setCellpadding(0);
            approveTable.setCellspacing(1);

            Cell approveCellTop = new Cell(new Chunk(" ",fontContent));
            approveCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            approveCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            approveCellTop.setColspan(2);
            approveCellTop.setBorderColor(new Color(255, 255, 255));
            approveTable.addCell(approveCellTop);
            
            approveCellTop = new Cell(new Chunk("Approve 1",fontContent));
            approveCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            approveCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            approveCellTop.setBorderColor(new Color(255, 255, 255));
            approveTable.addCell(approveCellTop);
            
            approveCellTop = new Cell(new Chunk("Approve 2",fontContent));
            approveCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            approveCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            approveCellTop.setBorderColor(new Color(255, 255, 255));
            approveTable.addCell(approveCellTop);
            
            
            approveCellTop = new Cell(new Chunk(" ",fontContent));
            approveCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            approveCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            approveCellTop.setColspan(2);
            approveCellTop.setBorderColor(new Color(255, 255, 255));
            approveTable.addCell(approveCellTop);
            
            approveCellTop = new Cell(new Chunk(" ",fontContent));
            approveCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            approveCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            approveCellTop.setColspan(2);
            approveCellTop.setBorderColor(new Color(255, 255, 255));
            approveTable.addCell(approveCellTop);
            
            String approve1= "";
            long approve1Id = 0;
            
            approve1 = getNameEmployee(benefitPeriod.getApprove1EmpId());
            
            approveCellTop = new Cell(new Chunk(""+approve1,fontTable));
            approveCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            approveCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            approveCellTop.setBorderColor(new Color(255, 255, 255));
            approveTable.addCell(approveCellTop);
            
            String approve2= "";
            long approve2Id = 0;

            approve2 = getNameEmployee(benefitPeriod.getApprove2EmpId());
            
            approveCellTop = new Cell(new Chunk(""+approve2,fontTable));
            approveCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            approveCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            approveCellTop.setBorderColor(new Color(255, 255, 255));
            approveTable.addCell(approveCellTop);
            document.add(approveTable);
        } catch (Exception e) {
            System.out.println("PRINT EMPLOYEE DATA ==>" + e);
        }

        document.close();

        System.out.println("print==============");
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
        
    }
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }

    public String getNameEmployee(long oid){
        String fullName = "Not yet approved";
        if (oid != 0){
            try {
                Employee emp = PstEmployee.fetchExc(oid);
                fullName = emp.getFullName();
                return fullName;
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
        return fullName;
    }
}
