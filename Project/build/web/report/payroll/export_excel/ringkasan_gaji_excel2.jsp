<%-- 
    Document   : rekonsiliasiExcel
    Created on : 26-Feb-2016, 06:12:09
    Author     : GUSWIK
--%>

<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.entity.payroll.PaySlipComp"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPaySlipComp"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPaySlip"%>
<%@page import="com.dimata.harisma.entity.payroll.PstSalaryLevelDetail"%>
<%@page import="com.dimata.harisma.entity.payroll.PayEmpLevel"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.entity.payroll.PaySlip"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstSection"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.Section"%>
<%@page import="com.dimata.harisma.entity.masterdata.Department"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpCategory"%>
<%@page import="com.dimata.harisma.entity.masterdata.EmpCategory"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployee"%>
<%@page import="java.util.Vector"%>
<!DOCTYPE html>
<%
    int start = FRMQueryString.requestInt(request, "start");
    int inclResign = FRMQueryString.requestInt(request, "INCLUDE_RESIGN");
    boolean bIncResign = (inclResign == 1);
    long oidDivision = FRMQueryString.requestLong(request, "division");
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidSection = FRMQueryString.requestLong(request, "section");
    long oidPaySlipComp = FRMQueryString.requestLong(request, "section");
    String searchNrFrom = FRMQueryString.requestString(request, "searchNrFrom");
    String searchNrTo = FRMQueryString.requestString(request, "searchNrTo");
    String searchName = FRMQueryString.requestString(request, "searchName");
    int aksiCommand = FRMQueryString.requestInt(request, "aksiCommand");
    long periodeId = FRMQueryString.requestLong(request, "periodId");
    long periodeIdbefore = PstPayPeriod.getPayPeriodIdJustBefore(periodeId);

    //TOTAL_GAJI_OID
    long TOTAL_GAJI_OID = 0;
    String compcode = "";
    try {
        TOTAL_GAJI_OID = Long.parseLong(PstSystemProperty.getValueByName("TOTAL_GAJI_OID"));
        PayComponent payComponent = new PayComponent();
        payComponent = PstPayComponent.fetchExc(TOTAL_GAJI_OID);
        compcode = payComponent.getCompCode();
    } catch (Exception ex) {
        System.out.println("Execption TOTAL_GAJI_OID " + ex);
    }

     long COMPONENT_TOTAL_POTONGAN_OID = 0;
        try {
            COMPONENT_TOTAL_POTONGAN_OID = Long.parseLong(PstSystemProperty.getValueByName("COMPONENT_TOTAL_POTONGAN_OID"));
        } catch (Exception ex) {
            System.out.println("Execption COMPONENT_TOTAL_POTONGAN_OID " + ex);
        }

        //COMPONENT_GAJI_BRUTO_OID
        long COMPONENT_GAJI_BRUTO_OID = 0;
        try {
            COMPONENT_GAJI_BRUTO_OID = Long.parseLong(PstSystemProperty.getValueByName("COMPONENT_GAJI_BRUTO_OID"));
        } catch (Exception ex) {
            System.out.println("Execption COMPONENT_GAJI_BRUTO_OID " + ex);
        }
        
    //priska 2015-03-09
    long propDailyworker = -1;
    try {
        propDailyworker = Long.parseLong(PstSystemProperty.getValueByName("OID_DAILYWORKER"));
    } catch (Exception ex) {
        System.out.println("Execption DAILY WORKER: " + ex);
    }


    Vector listEmpPaySlip = SessEmployee.listEmpPaySlipPerdepart(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign);
    Vector listEmpPaySlipBeforePeriode = SessEmployee.listEmpPaySlipPerdepart(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeIdbefore, -1, bIncResign);

    String wherepaycomponentBenefit = PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS] + " > " + 0 + " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + " = " + 1;
    String wherepaycomponentDeduction = PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS] + " > " + 0 + " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + " = " + 2;
    Vector vPayComponentBenefit = PstPayComponent.list(0, 0, wherepaycomponentBenefit, PstPayComponent.fieldNames[PstPayComponent.FLD_SORT_IDX] + " ASC ");
    Vector vPayComponentDeduction = PstPayComponent.list(0, 0, wherepaycomponentDeduction, PstPayComponent.fieldNames[PstPayComponent.FLD_SORT_IDX] + " ASC ");

    //data for show
    PayPeriod payPeriod = new PayPeriod();
    PayPeriod payPeriodBefore = new PayPeriod();
    Division divisionObj = new Division();
    Department departmentObj = new Department();
    Section sectionObj = new Section();


    try {
        payPeriod = PstPayPeriod.fetchExc(periodeId);
    } catch (Exception e) {
    }
    try {
        payPeriodBefore = PstPayPeriod.fetchExc(periodeIdbefore);
    } catch (Exception e) {
    }
    try {
        divisionObj = PstDivision.fetchExc(oidDivision);
    } catch (Exception e) {
    }
    try {
        departmentObj = PstDepartment.fetchExc(oidDepartment);
    } catch (Exception e) {
    }
    try {
        sectionObj = PstSection.fetchExc(oidSection);
    } catch (Exception e) {
    }

    //menampung jumlah employee berdasarkan kategori
    Vector empCatV = PstEmpCategory.listAll();
    Vector Vempcategory = PstEmpCategory.list(0, 0, null, null);
    Hashtable nilaicatperiode = new Hashtable();
    Hashtable nilaicatperiodeBefore = new Hashtable();

    for (int i = 0; i < empCatV.size(); i++) {
        EmpCategory empCategory = new EmpCategory();
        empCategory = (EmpCategory) empCatV.get(i);
        nilaicatperiode.put(empCategory.getOID(), 0);
        nilaicatperiodeBefore.put(empCategory.getOID(), 0);
    }



    Hashtable compvalueBenefit = new Hashtable();
    Hashtable compvalueDeduction = new Hashtable();
    Hashtable compvalueBenefitPeriodeBefore = new Hashtable();
    Hashtable compvalueDeductionPeriodeBefore = new Hashtable();
    if (vPayComponentBenefit.size() > 0) {
        for (int ib = 0; ib < vPayComponentBenefit.size(); ib++) {
            PayComponent payComponent = (PayComponent) vPayComponentBenefit.get(ib);
            compvalueBenefit.put("" + payComponent.getOID(), 0);
            compvalueBenefitPeriodeBefore.put("" + payComponent.getOID(), 0);
        }
    }
    if (vPayComponentDeduction.size() > 0) {
        for (int ib = 0; ib < vPayComponentDeduction.size(); ib++) {
            PayComponent payComponent = (PayComponent) vPayComponentDeduction.get(ib);
            compvalueDeduction.put("" + payComponent.getOID(), 0);
            compvalueDeductionPeriodeBefore.put("" + payComponent.getOID(), 0);
        }
    }
    double totalgajiPeriod = 0;
    double totalgajiPeriodBefore = 0;
    double totalgajikariawanbaru = 0;
    double totalgajiDW = 0;
    double totalgajiDWPerideBefore = 0;
    double totalCompKoperasi = 0;
    double totalCompKeterlambatan = 0;
    double totalCompBPJS = 0;
    double totalCompJHT = 0;
    double totalCompPototnganlainlain = 0;
    double totalCompTotalTransferBANK = 0;
    double totalCompTotalTransferCASH = 0;
    double totalGajiNetto = 0;
    double totalGajiNettoPeriodeBefore = 0;
    if (listEmpPaySlip.size() > 0) {

        for (int ip = 0; ip < listEmpPaySlip.size(); ip++) {
            Vector vlst = (Vector) listEmpPaySlip.get(ip);
            Employee objEmployee = (Employee) vlst.get(0);
            PayEmpLevel objPayEmpLevel = (PayEmpLevel) vlst.get(1);
            PaySlip objPaySlip = (PaySlip) vlst.get(2);

            //menghitung jumlah employee percategory
            long empcategoryoid = objEmployee.getEmpCategoryId();

            if (empcategoryoid != 0) {
                try {
                    int nilaiX = (Integer) nilaicatperiode.get(empcategoryoid);
                    nilaicatperiode.remove(empcategoryoid);
                    nilaicatperiode.put(empcategoryoid, (nilaiX + 1));
                } catch (Exception e) {
                }

                if (empcategoryoid == propDailyworker) {
                }
            }

            double totalCompGajiBrutoTemp = 0;
            double totalCompPotonganTemp = 0;
            if (vPayComponentBenefit.size() > 0) {
                for (int ib = 0; ib < vPayComponentBenefit.size(); ib++) {
                    PayComponent payComponent = (PayComponent) vPayComponentBenefit.get(ib);
                    try {
                        double nilaiX = (Double) Double.parseDouble("" + compvalueBenefit.get("" + payComponent.getOID()));
                        double valueComp = PstPaySlipComp.getCompValue(objPaySlip.getOID(), payComponent.getCompCode());
                        compvalueBenefit.remove("" + payComponent.getOID());
                        compvalueBenefit.put("" + payComponent.getOID(), (nilaiX + valueComp));
                        
                        if (payComponent.getOID() == COMPONENT_GAJI_BRUTO_OID){
                            totalCompGajiBrutoTemp = valueComp;
                        }
                      
                    } catch (Exception e) {
                    }
                }
            }
            if (vPayComponentDeduction.size() > 0) {
                for (int ib = 0; ib < vPayComponentDeduction.size(); ib++) {
                    PayComponent payComponent = (PayComponent) vPayComponentDeduction.get(ib);
                    try {
                        double nilaiX = (Double) Double.parseDouble("" + compvalueDeduction.get("" + payComponent.getOID()));
                        double valueComp = PstPaySlipComp.getCompValue(objPaySlip.getOID(), payComponent.getCompCode());
                        compvalueDeduction.remove("" + payComponent.getOID());
                        compvalueDeduction.put("" + payComponent.getOID(), (nilaiX + valueComp));
                        
                        if (payComponent.getOID() == COMPONENT_TOTAL_POTONGAN_OID){
                            totalCompPotonganTemp = valueComp;
                        }
                    } catch (Exception e) {
                    }
                }
            }

            totalGajiNetto = totalGajiNetto + (totalCompGajiBrutoTemp-totalCompPotonganTemp);
            
            if (objPayEmpLevel.getBankId()!=0){
                totalCompTotalTransferBANK = totalCompTotalTransferBANK + (totalCompGajiBrutoTemp-totalCompPotonganTemp);
            }
            if (objPayEmpLevel.getBankId()== 0){
                totalCompTotalTransferCASH = totalCompTotalTransferCASH + (totalCompGajiBrutoTemp-totalCompPotonganTemp);
            }
            
            double valueTotalGaji = PstPaySlipComp.getCompValue(objPaySlip.getOID(), compcode);
            totalgajiPeriod = totalgajiPeriod + valueTotalGaji;

            if (objEmployee.getCommencingDate().after(payPeriod.getStartDate())) {
                totalgajikariawanbaru = totalgajikariawanbaru + valueTotalGaji;
            }
            if (empcategoryoid == propDailyworker) {
                totalgajiDW = totalgajiDW + valueTotalGaji;
            }
            
        }
    }

    if (listEmpPaySlipBeforePeriode.size() > 0) {

        for (int ip = 0; ip < listEmpPaySlipBeforePeriode.size(); ip++) {
            Vector vlst = (Vector) listEmpPaySlipBeforePeriode.get(ip);
            Employee objEmployee = (Employee) vlst.get(0);
            PayEmpLevel objPayEmpLevel = (PayEmpLevel) vlst.get(1);
            PaySlip objPaySlip = (PaySlip) vlst.get(2);

            //menghitung jumlah employee percategory
            long empcategoryoid = objEmployee.getEmpCategoryId();

            if (empcategoryoid != 0) {
                try {
                    int nilaiX = (Integer) nilaicatperiodeBefore.get(empcategoryoid);
                    nilaicatperiodeBefore.remove(empcategoryoid);
                    nilaicatperiodeBefore.put(empcategoryoid, (nilaiX + 1));
                } catch (Exception e) {
                }
            }


            
            double totalCompGajiBrutoTemp = 0;
            double totalCompPotonganTemp = 0;
            if (vPayComponentBenefit.size() > 0) {
                for (int ib = 0; ib < vPayComponentBenefit.size(); ib++) {
                    PayComponent payComponent = (PayComponent) vPayComponentBenefit.get(ib);
                    try {
                        double nilaiX = (Double) Double.parseDouble("" + compvalueBenefitPeriodeBefore.get("" + payComponent.getOID()));
                        double valueComp = PstPaySlipComp.getCompValue(objPaySlip.getOID(), payComponent.getCompCode());
                        compvalueBenefitPeriodeBefore.remove("" + payComponent.getOID());
                        compvalueBenefitPeriodeBefore.put("" + payComponent.getOID(), (nilaiX + valueComp));
                        
                        if (payComponent.getOID() == COMPONENT_GAJI_BRUTO_OID){
                            totalCompGajiBrutoTemp = valueComp;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            if (vPayComponentDeduction.size() > 0) {
                for (int ib = 0; ib < vPayComponentDeduction.size(); ib++) {
                    PayComponent payComponent = (PayComponent) vPayComponentDeduction.get(ib);
                    try {
                        double nilaiX = (Double) Double.parseDouble("" + compvalueDeductionPeriodeBefore.get("" + payComponent.getOID()));
                        double valueComp = PstPaySlipComp.getCompValue(objPaySlip.getOID(), payComponent.getCompCode());
                        compvalueDeductionPeriodeBefore.remove("" + payComponent.getOID());
                        compvalueDeductionPeriodeBefore.put("" + payComponent.getOID(), (nilaiX + valueComp));
                        if (payComponent.getOID() == COMPONENT_TOTAL_POTONGAN_OID){
                            totalCompPotonganTemp = valueComp;
                        }
                    } catch (Exception e) {
                    }
                }
            }

            
            totalGajiNettoPeriodeBefore = totalGajiNettoPeriodeBefore + (totalCompGajiBrutoTemp-totalCompPotonganTemp);
            
            
            double valueTotalGaji = PstPaySlipComp.getCompValue(objPaySlip.getOID(), compcode);
            totalgajiPeriodBefore = totalgajiPeriodBefore + valueTotalGaji;


            if (empcategoryoid == propDailyworker) {
                totalgajiDWPerideBefore = totalgajiDWPerideBefore + valueTotalGaji;
            }
        }
    }
    Hashtable x = compvalueDeduction;
    Hashtable x2 = compvalueDeductionPeriodeBefore;
%>

<%@page contentType="application/x-msexcel" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Ringkasan</title>
    </head>
    <body>
        <table width="100%" border="0">
            <tr>
                <td>
                    <table width="100%" border="0">
                        <tr>
                            <td width="15%">RINGKASAN GAJI</td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="15%">Period </td>
                            <td width="10%"><%=payPeriod.getPeriod()%></td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="15%">Division</td>
                            <td width="10%"><%=divisionObj.getDivision() == null ? "-" : divisionObj.getDivision()%></td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="15%">Department</td>
                            <td width="10%"><%=departmentObj.getDepartment() == null ? "-" : departmentObj.getDepartment()%></td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="10%">Section</td>
                            <td width="10%"><%=sectionObj.getSection() == null ? "-" : sectionObj.getSection()%></td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><table width="100%" border="1">
                        <tr>
                            <td width="15%">&nbsp;</td>
                            <td colspan="3" bgcolor="#CCCCCC" style="text-align:center" >Value</td>
                            <td rowspan="<%=(vPayComponentBenefit.size() + vPayComponentDeduction.size() + 4)%>" style="border-top:hidden; border-bottom:hidden;">&nbsp;</td>
                            <td colspan="3" align="center" bgcolor="#CCCCCC">Head Count</td>
                            <td rowspan="<%=(vPayComponentBenefit.size() + vPayComponentDeduction.size() + 4)%>" style="border-top:hidden; border-bottom:hidden;">&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="15%" bgcolor="#CCCCCC">Period </td>
                            <td width="10%" bgcolor="#CCCCCC"><%=payPeriod.getPeriod()%></td>
                            <td width="10%" bgcolor="#CCCCCC"><%=payPeriodBefore.getPeriod()%></td>
                            <td width="10%" bgcolor="#CCCCCC">Varian</td>
                            <td bgcolor="#CCCCCC"><%=payPeriod.getPeriod()%></td>
                            <td bgcolor="#CCCCCC"><%=payPeriodBefore.getPeriod()%></td>
                            <td bgcolor="#CCCCCC">Varian</td>
                            <td bgcolor="#CCCCCC">Keterangan</td>
                        </tr>
                        <%
                            double jumlahN1 = 0;
                            double jumlahN2 = 0;
                            if (vPayComponentBenefit.size() > 0) {
                                for (int i = 0; i < vPayComponentBenefit.size(); i++) {
                                    PayComponent payComponent = (PayComponent) vPayComponentBenefit.get(i);
                                    double n1 = (Double) Double.parseDouble("" + compvalueBenefit.get("" + payComponent.getOID()));
                                    double n2 = (Double) Double.parseDouble("" + compvalueBenefitPeriodeBefore.get("" + payComponent.getOID()));

                                    
                                    
                                    
                                    jumlahN1 = jumlahN1 + n1;
                                    jumlahN2 = jumlahN2 + n2;

                        %>
                        <tr>
                            <td width="15%"><%=payComponent.getCompName()%></td>
                            <td width="10%"><%=(n1 != 0 ? "Rp. " + Formater.formatNumber(n1, "#,###.##") : "-")%></td>
                            <td width="10%"><%=(n2 != 0 ? "Rp. " + Formater.formatNumber(n2, "#,###.##") : "-")%></td>
                            <td width="10%"><%=((Math.abs(n1 - n2)) != 0 ? "Rp. " + Formater.formatNumber((Math.abs(n1 - n2)), "#,###.##") : "-")%></td>
                            <% if (i == 0) {%>
                            <td bgcolor="#FFFF00"><%=(listEmpPaySlip.size() != 0 ? "" + listEmpPaySlip.size() : "-")%></td>
                            <td bgcolor="#FFFF00"><%=(listEmpPaySlipBeforePeriode.size() != 0 ? "" + listEmpPaySlipBeforePeriode.size() : "-")%></td>
                            <td bgcolor="#FFFF00"><%=((Math.abs(listEmpPaySlip.size() - listEmpPaySlipBeforePeriode.size())) != 0 ? "" + (Math.abs(listEmpPaySlip.size() - listEmpPaySlipBeforePeriode.size())) : "-")%></td>
                            <td>&nbsp;</td>
                            <% } else {%>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <% }%>
                        </tr>
                        <%    }
                            }%>
                       

                        <%
                            double jumlahDeduction1 = 0;
                            double jumlahDeduction2 = 0;
                            if (vPayComponentDeduction.size() > 0) {
                                for (int i = 0; i < vPayComponentDeduction.size(); i++) {
                                    PayComponent payComponent = (PayComponent) vPayComponentDeduction.get(i);
                                    double n1 = (Double) Double.parseDouble("" + compvalueDeduction.get("" + payComponent.getOID()));
                                    double n2 = (Double) Double.parseDouble("" + compvalueDeductionPeriodeBefore.get("" + payComponent.getOID()));
                                    
                                    if (payComponent.getOID() == 504404573355902694l){
                                        totalCompKoperasi = totalCompKoperasi + n1; 
                                    }
                                    if (payComponent.getOID() == 504404602386465600l){
                                        totalCompKeterlambatan = totalCompKeterlambatan + n1; 
                                    }
                                    if (payComponent.getOID() == 504404573355939341l){
                                        totalCompBPJS = totalCompBPJS + n1; 
                                    }
                                    if (payComponent.getOID() == 504404573356139694l){
                                        totalCompJHT = totalCompJHT + n1; 
                                    }
                                    if (payComponent.getOID() == 504404583546588435l){
                                        totalCompPototnganlainlain = totalCompPototnganlainlain + n1; 
                                    }
                                    
                                    jumlahDeduction1 = jumlahDeduction1 + n1;
                                    jumlahDeduction2 = jumlahDeduction2 + n2;
                        %>
                        <tr>
                            <td width="15%"><%=payComponent.getCompName()%></td>
                            <td width="10%"><%=(n1 != 0 ? "Rp. " + Formater.formatNumber(n1, "#,###.##") : "-")%></td>
                            <td width="10%"><%=(n2 != 0 ? "Rp. " + Formater.formatNumber(n2, "#,###.##") : "-")%></td>
                            <td width="10%"><%=((Math.abs(n1 - n2)) != 0 ? "Rp. " + Formater.formatNumber((Math.abs(n1 - n2)), "#,###.##") : "-")%></td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <%    }
                            }%>
                        <tr>
                            <td width="15%">Gaji Netto</td>
                            <td width="10%"><%=(totalGajiNetto != 0 ? "Rp. " + Formater.formatNumber(totalGajiNetto, "#,###.##") : "-")%></td>
                            <td width="10%"><%=(totalGajiNettoPeriodeBefore != 0 ? "Rp. " + Formater.formatNumber(totalGajiNettoPeriodeBefore, "#,###.##") : "-")%></td>
                            <td width="10%"><%=((Math.abs(totalGajiNetto - totalGajiNettoPeriodeBefore)) != 0 ? "Rp. " + Formater.formatNumber((Math.abs(totalGajiNetto - totalGajiNettoPeriodeBefore)), "#,###.##") : "-")%></td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="1">
                        <tr>
                            <td width="15%">HR Unit</td>
                            <td width="10%">ACC Unit</td>
                            <td width="10%">Pimpinan Unit</td>
                            <td width="10%">HR Coorporate</td>
                            <td width="10%">Director</td>
                            <td width="10%">Presdir</td>
                            <td width="10%">Presdir CEO</td>
                            <td width="10%" rowspan="9">&nbsp;</td>
                            <td width="10%">Gaji Transfer ke BCA ==&gt;&gt; Gaji Netto</td>
                            <td width="10%"><%=(totalGajiNetto != 0 ? "Rp. " + Formater.formatNumber(totalGajiNetto, "#,###.##") : "-")%></td>
                        </tr>
                        <tr>
                            <td width="15%" rowspan="8">&nbsp;</td>
                            <td width="10%" rowspan="8">&nbsp;</td>
                            <td width="10%" rowspan="8">&nbsp;</td>
                            <td width="10%" rowspan="8">&nbsp;</td>
                            <td width="10%" rowspan="8">&nbsp;</td>
                            <td width="10%" rowspan="8">&nbsp;</td>
                            <td width="10%" rowspan="8">&nbsp;</td>
                            <td width="10%">Transfer Ke Koperasi</td>
                            <td width="10%"><%=(totalCompKoperasi != 0 ? "Rp. " + Formater.formatNumber(totalCompKoperasi, "#,###.##") : "-")%></td>
                        </tr>
                        <tr>
                            <td width="10%">Total Transfer ke BCA </td>
                            <td width="10%"><%=(totalCompTotalTransferBANK != 0 ? "Rp. " + Formater.formatNumber(totalCompTotalTransferBANK, "#,###.##") : "-")%></td>
                        </tr>
                        <tr>
                            <td width="10%">Gaji Cash </td>
                            <td width="10%"><%=(totalCompTotalTransferCASH != 0 ? "Rp. " + Formater.formatNumber(totalCompTotalTransferCASH, "#,###.##") : "-")%></td>
                        </tr>
                        <tr>
                            <td width="10%">BPJS 1%*</td>
                            <td width="10%"><%=(totalCompBPJS != 0 ? "Rp. " + Formater.formatNumber(totalCompBPJS, "#,###.##") : "-")%></td>
                        </tr>
                        <tr>
                            <td width="10%">JHT 2%</td>
                            <td width="10%"><%=(totalCompJHT != 0 ? "Rp. " + Formater.formatNumber(totalCompJHT, "#,###.##") : "-")%></td>
                        </tr>
                        <tr>
                            <td width="10%">Keterlambatan</td>
                            <td width="10%"><%=(totalCompKeterlambatan != 0 ? "Rp. " + Formater.formatNumber(totalCompKeterlambatan, "#,###.##") : "-")%></td>
                        </tr> 
                        <tr>
                            <td width="10%">Potongan Lain</td>
                            <td width="10%"><%=(totalCompPototnganlainlain != 0 ? "Rp. " + Formater.formatNumber(totalCompPototnganlainlain, "#,###.##") : "-")%></td>
                        </tr> 
                        <tr>
                            <td width="10%" bgcolor="#FFFF00">Grand Total </td>
                            <td width="10%" bgcolor="#FFFF00"><%=((totalCompPototnganlainlain+totalCompKeterlambatan+totalCompJHT+totalCompBPJS+totalCompTotalTransferCASH+totalCompTotalTransferBANK+totalCompKoperasi) != 0 ? "Rp. " + Formater.formatNumber((totalCompPototnganlainlain+totalCompKeterlambatan+totalCompJHT+totalCompBPJS+totalCompTotalTransferCASH+totalCompTotalTransferBANK+totalCompKoperasi), "#,###.##") : "-")%></td>
                        </tr>
                    </table>

                </td>
            </tr>
        </table>
    </body>
</html>
