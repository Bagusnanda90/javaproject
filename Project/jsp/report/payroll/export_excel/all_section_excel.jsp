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

    //priska 2015-03-09
    long propDailyworker = -1;
    try {
        propDailyworker = Long.parseLong(PstSystemProperty.getValueByName("OID_DAILYWORKER"));
    } catch (Exception ex) {
        System.out.println("Execption DAILY WORKER: " + ex);
    }


    Vector listEmpPaySlip = SessEmployee.listEmpPaySlipPerdepart(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign);
    String wherepaycomponentBenefit = PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS] + " > " + 0 + " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + " = " + 1;
    String wherepaycomponentDeduction = PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS] + " > " + 0 + " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + " = " + 2;
    Vector vPayComponentBenefit = PstPayComponent.list(0, 0, wherepaycomponentBenefit, PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID] + " ASC ");
    Vector vPayComponentDeduction = PstPayComponent.list(0, 0, wherepaycomponentDeduction, PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID] + " ASC ");


    Hashtable compvalueBenefit = new Hashtable();
    Hashtable compvalueDeduction = new Hashtable();
    if (vPayComponentBenefit.size() > 0) {
        for (int ib = 0; ib < vPayComponentBenefit.size(); ib++) {
            PayComponent payComponent = (PayComponent) vPayComponentBenefit.get(ib);
            compvalueBenefit.put("" + payComponent.getOID(), 0);
        }
    }
    if (vPayComponentDeduction.size() > 0) {
        for (int ib = 0; ib < vPayComponentDeduction.size(); ib++) {
            PayComponent payComponent = (PayComponent) vPayComponentDeduction.get(ib);
            compvalueDeduction.put("" + payComponent.getOID(), 0);
        }
    }

    String WhereSection = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + oidDepartment;
    Vector allSection = PstSection.list(0, 0, WhereSection, "SECTION");
    Hashtable hSectionBenefit = new Hashtable();
    Hashtable hSectionDeduction = new Hashtable();
    if (allSection.size() > 0) {
        for (int x = 0; x < allSection.size(); x++) {
            Section section = (Section) allSection.get(x);
            hSectionBenefit.put("" + section.getOID(), compvalueBenefit);
            hSectionDeduction.put("" + section.getOID(), compvalueDeduction);
        }
    }

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

    double totalgajiPeriod = 0;
    double totalgajiPeriodBefore = 0;
    if (listEmpPaySlip.size() > 0) {

        for (int ip = 0; ip < listEmpPaySlip.size(); ip++) {
            Vector vlst = (Vector) listEmpPaySlip.get(ip);
            Employee objEmployee = (Employee) vlst.get(0);
            PayEmpLevel objPayEmpLevel = (PayEmpLevel) vlst.get(1);
            PaySlip objPaySlip = (PaySlip) vlst.get(2);

            long empSectionId = objEmployee.getSectionId();
            Hashtable compvalueBenefitTemp =  new Hashtable();
            compvalueBenefitTemp = (Hashtable) hSectionBenefit.get("" + empSectionId);
            
            
            if (vPayComponentBenefit.size() > 0) {
                for (int ib = 0; ib < vPayComponentBenefit.size(); ib++) {
                    PayComponent payComponent = (PayComponent) vPayComponentBenefit.get(ib);
                    try {
                        double nilaiX = (Double) Double.parseDouble("" + compvalueBenefitTemp.get("" + payComponent.getOID()));
                        double valueComp = PstPaySlipComp.getCompValue(objPaySlip.getOID(), payComponent.getCompCode());
                        compvalueBenefitTemp.remove("" + payComponent.getOID());
                        compvalueBenefitTemp.put("" + payComponent.getOID(), (nilaiX + valueComp));
                    } catch (Exception e) {
                    }
                }
            }
            hSectionBenefit.remove("" + empSectionId);
            hSectionBenefit.put("" + empSectionId, compvalueBenefitTemp);


        }
    }

%>

<%@page contentType="application/x-msexcel" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>All Section</title>
    </head>
    <body>

        <table width="100%" border="0">
            <tr>
                <td><table width="100%" border="0">
                        <tr>
                            <td width="15%">RINGKASAN GAJI</td>
                            <td width="10%">&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="15%">Period </td>
                            <td width="10%">February 2016</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="15%">Division</td>
                            <td width="10%">Operation</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="15%">Department</td>
                            <td width="10%">Pepito Express Udayana</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="15%">Section</td>
                            <td width="10%">&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><table width="100%" border="1">
                        <tr>
                            <td width="15%">NO</td>
                            <td width="10%">SECTION</td>
                            <td>HEADCOUNT</td>
                            <%
                                if (vPayComponentBenefit.size() > 0) {
                                    for (int i = 0; i < vPayComponentBenefit.size(); i++) {
                                        PayComponent payComponent = (PayComponent) vPayComponentBenefit.get(i);
                            %>
                            <td><%=payComponent.getCompName()%></td>
                            <% }} %>
                        </tr>
                        <%
                            if (allSection.size() > 0) {
                                for (int ix = 0; ix < allSection.size(); ix++) {
                                    Section section = (Section) allSection.get(ix);
                        %>
                        <tr>
                            <td width="15%"><%=ix + 1%></td>
                            <td width="10%"><%=section.getSection()%></td>
                            <td>HeadCount</td>
                            <%
                                Hashtable compValueBenefitT = (Hashtable) hSectionBenefit.get(""+section.getOID()); 
                                if (vPayComponentBenefit.size() > 0) {
                                    for (int i = 0; i < vPayComponentBenefit.size(); i++) {
                                        PayComponent payComponent = (PayComponent) vPayComponentBenefit.get(i);
                                        double n1 = (Double) Double.parseDouble("" + compValueBenefitT.get("" + payComponent.getOID()));
                            %>
                            <td><%=n1%></td>
                            <% } }%>
                        </tr>
                        <% } }  %>

                    </table></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><table width="100%" border="1">
                        <tr>
                            <td width="15%">HR Unit</td>
                            <td width="10%">ACC Unit</td>
                            <td width="10%">Pimpinan Unit</td>
                            <td width="10%">HR Coorporate</td>
                            <td width="10%">Director</td>
                            <td width="10%">Presdir</td>
                            <td width="10%">Presdir CEO</td>
                            <td rowspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="15%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                        </tr>
                    </table></td>
            </tr>
        </table>

    </body>
</html>
