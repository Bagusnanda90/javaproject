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
    long COMPONENT_GAJI_BRUTO_OID = 0;
    String compcode = "";
    try {
        COMPONENT_GAJI_BRUTO_OID = Long.parseLong(PstSystemProperty.getValueByName("COMPONENT_GAJI_BRUTO_OID"));
        PayComponent payComponent = new PayComponent();
        payComponent = PstPayComponent.fetchExc(COMPONENT_GAJI_BRUTO_OID);
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
    Vector listEmpPaySlipBeforePeriode = SessEmployee.listEmpPaySlipPerdepart(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeIdbefore, -1, bIncResign);


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

    double totalgajiPeriod = 0;
    double totalgajiPeriodBefore = 0;
    double totalgajikariawanbaru = 0;
    double totalgajiDW = 0;
    double totalgajiDWPerideBefore = 0;
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
                
                if (empcategoryoid == propDailyworker){
                    
                }
            }
            
            double valueTotalGaji1 = PstPaySlipComp.getCompValue(objPaySlip.getOID(), "BNF01");
            totalgajiPeriod = totalgajiPeriod + valueTotalGaji1;
            double valueTotalGaji = PstPaySlipComp.getCompValue(objPaySlip.getOID(), "BNF02");
            totalgajiPeriod = totalgajiPeriod + valueTotalGaji;
            double valueTotalGaji2 = PstPaySlipComp.getCompValue(objPaySlip.getOID(), "BNF03");
            totalgajiPeriod = totalgajiPeriod + valueTotalGaji2;

            if (objEmployee.getCommencingDate().after(payPeriod.getStartDate())) {
                totalgajikariawanbaru = totalgajikariawanbaru + valueTotalGaji;
            }
            if (empcategoryoid == propDailyworker){
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

            double valueTotalGaji1 = PstPaySlipComp.getCompValue(objPaySlip.getOID(), "BNF01");
            totalgajiPeriodBefore = totalgajiPeriodBefore + valueTotalGaji1;
            double valueTotalGaji = PstPaySlipComp.getCompValue(objPaySlip.getOID(), "BNF02");
            totalgajiPeriodBefore = totalgajiPeriodBefore + valueTotalGaji;
            double valueTotalGaji2 = PstPaySlipComp.getCompValue(objPaySlip.getOID(), "BNF03");
            totalgajiPeriodBefore = totalgajiPeriodBefore + valueTotalGaji2;
            
       
            if (empcategoryoid == propDailyworker){
                totalgajiDWPerideBefore = totalgajiDWPerideBefore + valueTotalGaji;
            }
        }
    }
%>

<%@page contentType="application/x-msexcel" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Rekonsiliasi</title>
    </head>
    <body>
        <table width="60%"  >
            <tr>
                <td>
                    <table width="100%" border="0">
                        <tr>
                            <td colspan="4">REKONSILIASI    GAJI
                            </td>
                        </tr>
                        <tr>
                            <td width="20%">Periode</td>
                            <td width="80%" colspan="3"><%=payPeriod.getPeriod()%></td>
                        </tr>
                        <tr>
                            <td width="20%">Division</td>
                            <td width="80%" colspan="3"><%=divisionObj.getDivision() == null ? "-" : divisionObj.getDivision()%></td>
                        </tr>
                        <tr>
                            <td width="20%">Department</td>
                            <td width="80%" colspan="3"><%=departmentObj.getDepartment() == null ? "-" : departmentObj.getDepartment()%></td>
                        </tr>
                        <tr>
                            <td width="20%">Section</td>
                            <td width="80%" colspan="3"><%=sectionObj.getSection() == null ? "-" : sectionObj.getSection()%></td>
                        </tr>
                        <tr>
                            <td width="20%">&nbsp;</td>
                            <td width="80%" colspan="3">&nbsp;</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="1" style="border-style:inset">
                        <tr>
                            <td width="40%" border="1" style="border-style:hidden;">&nbsp;</td>
                            <td colspan="3" bgcolor="#CCCCCC" style="text-align:center">Head Count</td>
                        </tr>
                        <tr>
                            <td width="40%" border="1" style="border-style:hidden;">&nbsp;</td>
                            <td bgcolor="#CCCCCC"><%=payPeriod.getPeriod()%></td>
                            <td bgcolor="#CCCCCC"><%=payPeriodBefore.getPeriod()%></td>
                            <td bgcolor="#CCCCCC">Varian</td>
                        </tr>
                        <%
                            int totalPeriodEmpCount = 0;
                            int totalPeriodEmpCountBefore = 0;
                            int totalPeriodEmpCountVarian = 0;

                            if (empCatV.size() > 0) {
                                for (int i = 0; i < empCatV.size(); i++) {
                                    EmpCategory empCategory = new EmpCategory();
                                    empCategory = (EmpCategory) empCatV.get(i);
                        %>
                        <tr>
                            <td width="40%"><%=empCategory.getEmpCategory()%></td>
                            <td><%=nilaicatperiode.get(empCategory.getOID())%></td>
                            <td><%=nilaicatperiodeBefore.get(empCategory.getOID())%></td>
                            <td><%
                                int n1 = (Integer) nilaicatperiode.get(empCategory.getOID());
                                int n2 = (Integer) nilaicatperiodeBefore.get(empCategory.getOID());
                                int varian = Math.abs(n1 - n2);


                                totalPeriodEmpCount = totalPeriodEmpCount + n1;
                                totalPeriodEmpCountBefore = totalPeriodEmpCountBefore + n2;
                                totalPeriodEmpCountVarian = totalPeriodEmpCountVarian + varian;

                                %><%=varian%></td>
                        </tr>
                        <% }
                            }%>


                        <tr>
                            <td width="40%" bgcolor="#FFFF00" style="text-align:center">Total</td>
                            <td bgcolor="#FFFF00"><%=(totalPeriodEmpCount != 0 ? "" +totalPeriodEmpCount : "-")%></td>
                            <td bgcolor="#FFFF00"><%=(totalPeriodEmpCountBefore != 0 ? "" +totalPeriodEmpCountBefore: "-")%></td>
                            <td bgcolor="#FFFF00"><%=(totalPeriodEmpCountVarian != 0 ? "" +totalPeriodEmpCountVarian: "-")%></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td><table width="100%" border="1" style="border-style:inset">
                        <tr>
                            <td width="40%" border="1" style="border-style:hidden;">&nbsp;</td>
                            <td colspan="3" bgcolor="#CCCCCC" style="text-align:center">Value</td>
                        </tr>
                        <tr>
                            <td width="40%" border="1" style="border-style:hidden;">&nbsp;</td>
                            <td bgcolor="#CCCCCC">February 2016</td>
                            <td bgcolor="#CCCCCC">January 2016</td>
                            <td bgcolor="#CCCCCC">Varian</td>
                        </tr>
                        <tr>
                            <td width="40%">Gaji    Pokok + Jabatan + Gaji Pokok Harian</td>
                            <td><%=(totalgajiPeriod != 0 ? "Rp. " + Formater.formatNumber(totalgajiPeriod, "#,###.##") : "-")%></td>
                            <td><%=(totalgajiPeriodBefore != 0 ? "Rp. " + Formater.formatNumber(totalgajiPeriodBefore, "#,###.##") : "-")%></td>
                            <td><%=((Math.abs(totalgajiPeriod - totalgajiPeriodBefore)) != 0 ? "Rp. " + Formater.formatNumber((Math.abs(totalgajiPeriod - totalgajiPeriodBefore)), "#,###.##") : "-")%></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td><table width="100%" border="1" style="border-style:inset">
                        <tr>
                            <td colspan="4" bgcolor="#CCCCCC" style="text-align:center">SALARY INCREASING DESCRIPTION</td>
                        </tr>

                        <tr>
                            <td width="40%">A. PENAMBAHAN KARYAWAN</td>
                            <td>-</td>
                            <td>-</td>
                            <td><%=(totalgajikariawanbaru != 0 ? "Rp. " + Formater.formatNumber(totalgajikariawanbaru, "#,###.##") : "-")%></td>
                        </tr>
                        <tr>
                            <td width="40%">B. KENAIKAN GAJI</td>
                            <td>-</td>
                            <td>-</td>
                            <td><% if ((totalgajiPeriod - totalgajiPeriodBefore) > 0) {%>
                                <%=((totalgajiPeriod - totalgajiPeriodBefore) != 0 ? "Rp. " + Formater.formatNumber((totalgajiPeriod - totalgajiPeriodBefore), "#,###.##") : "-")%>
                                <% }%>
                            </td>
                        </tr>
                        <tr>
                            <td width="40%">C. ADJUSTMENT</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td width="40%" bgcolor="#FFFF00">TOTAL INCREASING</td>
                            <td bgcolor="#FFFF00">-</td>
                            <td bgcolor="#FFFF00">-</td>
                            <td bgcolor="#FFFF00">-</td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td><table width="100%" border="1" style="border-style:inset">
                        <tr>
                            <td colspan="4" bgcolor="#CCCCCC" style="text-align:center">SALARY DECREASING DESCRIPTION</td>
                        </tr>

                        <tr>
                            <td width="40%">A. RESIGN</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td width="40%">B. DEMOSI</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td width="40%">C. DLL</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td width="40%" bgcolor="#FFFF00">TOTAL DECREASING</td>
                            <td bgcolor="#FFFF00">-</td>
                            <td bgcolor="#FFFF00">-</td>
                            <td bgcolor="#FFFF00">RP -</td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td><table width="100%" border="1" style="border-style:inset">
                        <tr>
                            <td colspan="4" bgcolor="#CCCCCC" style="text-align:center">DW SALARY CHANGES DESCRIPTION</td>
                        </tr>

                        <tr>
                            <td width="40%">A.    VARIAN DW SALARY</td>
                            <td>-</td>
                            <td>-</td>
                            <td>
                                <%//=((totalgajiDW - totalgajiDWPerideBefore) != 0 ? "Rp. " + Formater.formatNumber((totalgajiDW - totalgajiDWPerideBefore), "#,###.##") : "-")%>
                            </td>
                        </tr>
                        <tr>
                            <td width="40%" bgcolor="#FFFF00">TOTAL DW SALARY CHANGES</td>
                            <td bgcolor="#FFFF00">-</td>
                            <td bgcolor="#FFFF00">-</td>
                            <td bgcolor="#FFFF00">-</td>
                        </tr>
                        <tr>
                            <td width="40%">GRAND TOTAL</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td width="40%" bgcolor="#FFFF00">GRAND TOTAL VARIAN</td>
                            <td bgcolor="#FFFF00">-</td>
                            <td bgcolor="#FFFF00">-</td>
                            <td bgcolor="#FFFF00"><%=((Math.abs(totalgajiPeriod - totalgajiPeriodBefore)) != 0 ? "Rp. " + Formater.formatNumber((Math.abs(totalgajiPeriod - totalgajiPeriodBefore)), "#,###.##") : "-")%></td>
                        </tr>
                    </table>
                </td>
            </tr>

        </table>
    </body>
</html>
