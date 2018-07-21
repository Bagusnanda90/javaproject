<%-- 
    Document   : export_excel_List_Benefit_deduction.jsp
    Created on : Dec 31, 2014, 10:42:46 AM
    Author     : Priska
--%>


<%@page import="com.dimata.harisma.entity.payroll.PstPayEmpLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.harisma.report.payroll.ListBenefitDeduction"%>
<%@page import="com.dimata.harisma.entity.payroll.PayEmpLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.PayBanks"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponenttemp"%>
<%@page import="com.dimata.harisma.entity.masterdata.payday.PstPayDay"%>
<%@page import="com.dimata.harisma.entity.masterdata.payday.HashTblPayDay"%>
<%@page import="com.dimata.harisma.session.attendance.rekapitulasiabsensi.RekapitulasiAbsensi"%>
<%@page import="com.dimata.harisma.entity.masterdata.sesssection.SessSection"%>
<%@page import="com.dimata.harisma.entity.masterdata.sessemployee.EmployeeMinimalis"%>
<%@page import="com.dimata.harisma.entity.masterdata.sessemployee.SessEmployee"%>
<%@page import="com.dimata.harisma.entity.masterdata.sessdepartment.SessDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.sessdivision.SessDivision"%>
<%@page import="com.dimata.harisma.report.attendance.TmpListParamAttdSummary"%>
<%@page import="com.dimata.harisma.report.attendance.AttendanceSummaryXls"%>
<%@page import="com.dimata.harisma.form.payroll.FrmPayInput"%>
<%@page import="com.dimata.harisma.entity.overtime.TableHitungOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPaySlip"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayInput"%>
<%@page import="com.dimata.harisma.session.leave.SessLeaveApp"%>
<%@page import="com.dimata.harisma.entity.overtime.HashTblOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.session.payroll.I_PayrollCalculator"%>
<%@page import="com.lowagie.text.Document"%>
<%@page import="com.dimata.qdep.db.DBHandler"%>
<%@page import="org.apache.poi.hssf.record.ContinueRecord"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.overtime.Overtime"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%@ page language="java" %>

<%@ page import ="java.util.*"%>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.*" %>

<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>

<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import ="com.dimata.harisma.session.attendance.*"%>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ include file = "../../../main/javainit.jsp" %>

<%!    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }
%>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<%@ include file = "../../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;

//cek tipe browser, browser detection
    //String userAgent = request.getHeader("User-Agent");
    //boolean isMSIE = (userAgent!=null && userAgent.indexOf("MSIE") !=-1); 

%>
<%!    public String drawList(JspWriter outJsp, Vector listdatanew, Vector vpcomponent, long sourcetype, Hashtable listAttdAbsensi, Vector vReason) {
        String result = "";

        if (sourcetype == 0) {
            sourcetype = 1;
        } else if (sourcetype == 1) {
            sourcetype = 2;
        }

        long propProbation = -1;
        try {
            propProbation = Long.parseLong(PstSystemProperty.getValueByName("OID_PROBATION"));
        } catch (Exception ex) {
            System.out.println("Execption PROBATION: " + ex);
        }

        long propDailyworker = -1;
        try {
            propDailyworker = Long.parseLong(PstSystemProperty.getValueByName("OID_DAILYWORKER"));
        } catch (Exception ex) {
            System.out.println("Execption DAILY WORKER: " + ex);
        }

        long propGPokok = -1;
        try {
            propGPokok = Long.parseLong(PstSystemProperty.getValueByName("OID_GAJIPOKOK"));
        } catch (Exception ex) {
            System.out.println("Execption OID_GAJIPOKOK " + ex);
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

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        //ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(2);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("Head Count", "5%", "2", "0");
        ctrlist.addHeader("No", "5%", "2", "0");
        ctrlist.addHeader("Nama", "5%", "2", "0");
        ctrlist.addHeader("BANK", "5%", "2", "0");
        ctrlist.addHeader("BANK ACC", "5%", "2", "0");
        ctrlist.addHeader("HARI Kerja", "5%", "2", "0");

        int n = 0;
        if (vpcomponent != null && vpcomponent.size() > 0) {
            for (int d = 0; d < vpcomponent.size(); d++) {
                PayComponent payComponent = (PayComponent) vpcomponent.get(d);
                if (payComponent.getCompType() == 1) {
                    n = n + 1;
                } else if ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponent.getOID() == COMPONENT_TOTAL_POTONGAN_OID)) {
                    n = n + 1;
                }
            }
        }

        ctrlist.addHeader("Component", "20%", "0", "" + (vpcomponent != null && vpcomponent.size() > 0 ? n : 0));
        if (vpcomponent != null && vpcomponent.size() > 0) {
            for (int d = 0; d < vpcomponent.size(); d++) {
                PayComponent payComponent = (PayComponent) vpcomponent.get(d);
                if (payComponent.getCompType() == 1) {
                    ctrlist.addHeader("" + payComponent.getCompName(), "5%", "0", "0");
                } else if ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponent.getOID() == COMPONENT_TOTAL_POTONGAN_OID)) {
                    ctrlist.addHeader("" + payComponent.getCompName(), "5%", "0", "0");
                }
            }
        }

        ctrlist.addHeader("Grand Total", "5%", "2", "0");

        String empNumTest = "";
        int no = 0;
        String TempCompany = "";
        String TempDivision = "";
        String TempDepartment = "";
        String TempSection = "";

        String TempCompany2 = "";
        String TempDivision2 = "";
        String TempDepartment2 = "";
        String TempSection2 = "";

        //set total company default
        Vector totalCompany = new Vector();
        Vector totalDivision = new Vector();
        Vector totalDepartment = new Vector();
        Vector totalSection = new Vector();

        if (vpcomponent != null && vpcomponent.size() > 0) {
            for (int d = 0; d < vpcomponent.size(); d++) {
                totalCompany.add(0);
                totalDivision.add(0);
                totalDepartment.add(0);
                totalSection.add(0);
            }
        }




        double grandtotalallsect = 0;
        double grandtotalalldept = 0;
        double grandtotalalldivi = 0;
        double grandtotalallcomp = 0;

        try {
            if (listdatanew.size() > 0) {
                //ctrlist.drawListHeaderExcel(outJsp);//header
                ctrlist.drawListHeaderExcel(outJsp);//header
                for (int i = 0; i < listdatanew.size(); i++) {
                    Vector temp = (Vector) listdatanew.get(i);


                    int taxHomepay = 0;

                    Employee employee = (Employee) temp.get(0);

                    RekapitulasiPresenceDanSchedule rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule();
                    if (listAttdAbsensi != null && listAttdAbsensi.size() > 0 && listAttdAbsensi.containsKey("" + employee.getOID())) {
                        rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule) listAttdAbsensi.get("" + employee.getOID());
                        listAttdAbsensi.remove("" + employee.getOID());
                    }
                    if (true || rekapitulasiPresenceDanSchedule.getPresenceOnTime() > 0) {

                        String Company = (String) temp.get(1);
                        String Division = (String) temp.get(2);
                        String Department = (String) temp.get(3);
                        String Section = "";
                        if (temp.get(5) != null) {
                            Section = (String) temp.get(4);
                        } else {
                            Section = "";
                        }
                        String empCategory = "";
                        if (temp.get(8) != null) {
                            empCategory = (String) temp.get(8);
                        } else {
                            empCategory = "";
                        }


                        PayBanks payBanks = (PayBanks) temp.get(5);
                        PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(6);
                        Vector pcomp = (Vector) temp.get(7);

                        if (i == 0) {
                            TempCompany2 = (String) temp.get(1);
                            TempDivision2 = (String) temp.get(2);
                            TempDepartment2 = (String) temp.get(3);
                            if (temp.get(4) != null) {
                                TempSection2 = (String) temp.get(4);
                            } else {
                                TempSection2 = "";
                            }

                        }

                        if ((!TempCompany.equals(Company)) && (Company != null)) {

                            //cetak dulu jumlah yang sebelumnya jumlah total per company

                            no = 0;
                            TempCompany = Company;
                            Vector rowxcomp = new Vector();
                            rowxcomp.add("");
                            rowxcomp.add("<B>COMPANY</B>");
                            rowxcomp.add("<B>" + Company + "</B>");
                            rowxcomp.add("");
                            rowxcomp.add("");
                            rowxcomp.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponttemp.getCompType() == 1) || ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID))) {
                                        rowxcomp.add("");
                                    }
                                }
                            }

                            rowxcomp.add("");
                            ctrlist.drawListRowExcel(outJsp, 0, rowxcomp, i);
                        } else {
                        }

                        if ((!TempDivision.equals(Division)) && (Division != null)) {
                            no = 0;
                            TempDivision = Division;
                            Vector rowx = new Vector();
                            rowx.add("");
                            rowx.add("<B>DIVISION</B>");
                            rowx.add("<B> ->" + Division + "</B>");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponttemp.getCompType() == 1) || ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID))) {
                                        rowx.add("");
                                    }
                                }
                            }
                            rowx.add("");
                            ctrlist.drawListRowExcel(outJsp, 0, rowx, i);
                        }

                        if ((!TempDepartment.equals(Department)) && (Department != null)) {
                            no = 0;
                            TempDepartment = Department;
                            Vector rowx = new Vector();
                            rowx.add("");
                            rowx.add("<B>DEPARTMENT</B>");
                            rowx.add("<B> -->" + Department + "</B>");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponttemp.getCompType() == 1) || ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID))) {
                                        rowx.add("");
                                    }
                                }
                            }
                            rowx.add("");
                            ctrlist.drawListRowExcel(outJsp, 0, rowx, i);
                        }

                        if ((!TempSection.equals(Section)) && (Section != null)) {
                            no = 0;
                            TempSection = Section;
                            Vector rowx = new Vector();
                            rowx.add("");
                            rowx.add("<B>SECTION</B>");
                            rowx.add("<B> -->" + Section + "</B>");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponttemp.getCompType() == 1) || ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID))) {
                                        rowx.add("");
                                    }
                                }
                            }
                            rowx.add("");
                            ctrlist.drawListRowExcel(outJsp, 0, rowx, i);
                        } else {
                        }

                        Vector rowx = new Vector();
                        rowx.add("" + (no + 1));
                        rowx.add("" + employee.getEmployeeNum());
                        rowx.add("" + employee.getFullName());
                        rowx.add(payBanks.getBankName() != null ? "" + payBanks.getBankName() : "-");
                        rowx.add("" + payEmpLevel.getBankAccNr());
                        rowx.add("" + rekapitulasiPresenceDanSchedule.getTotalWorkingDays());

                        double totalgajitunjanganservice = 0;

                        double totalpotongan = 0;
                        if (pcomp != null && pcomp.size() > 0) {
                            for (int d = 0; d < pcomp.size(); d++) {
                                PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                // && (payComponenttemp.getCompId() == propGPokok)
                                taxHomepay = PstPayEmpLevel.getTaxHomePayStatus(employee.getOID(), payComponenttemp.getCompCode());

                                if ((payComponenttemp.getCompType() == 1) || ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID))) {


                                    if (rekapitulasiPresenceDanSchedule.getPresenceOnTime() == 0) {
                                        rowx.add("Rp.-");
                                    } else {
                                        //total section
                                        double totalsect = Double.parseDouble(totalSection.get(d).toString());
                                        totalSection.set(d, totalsect + payComponenttemp.getCompValue());
                                        //total percompany
                                        double totalcomp = Double.parseDouble(totalCompany.get(d).toString());
                                        totalCompany.set(d, totalcomp + payComponenttemp.getCompValue());
                                        //total division
                                        double totaldiv = Double.parseDouble(totalDivision.get(d).toString());
                                        totalDivision.set(d, totaldiv + payComponenttemp.getCompValue());
                                        //total dpartment
                                        double totaldepart = Double.parseDouble(totalDepartment.get(d).toString());
                                        totalDepartment.set(d, totaldepart + payComponenttemp.getCompValue());

                                        rowx.add("Rp. " + Formater.formatNumber(payComponenttemp.getCompValue(), "#,###.##"));
                                        // if (taxHomepay == 1) {
                                        if (COMPONENT_GAJI_BRUTO_OID == payComponenttemp.getCompId()) {
                                            totalgajitunjanganservice = payComponenttemp.getCompValue();
                                        }
                                        // }
                                        if ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID)) {
                                            totalpotongan = payComponenttemp.getCompValue();
                                        }

                                    }

                                }
                            }
                        }
                        if (rekapitulasiPresenceDanSchedule.getPresenceOnTime() == 0) {
                            rowx.add("Rp.-");
                        } else {

                            //hitung total semua
                            grandtotalallsect = grandtotalallsect + (totalgajitunjanganservice - totalpotongan);
                            grandtotalalldept = grandtotalalldept + (totalgajitunjanganservice - totalpotongan);
                            grandtotalalldivi = grandtotalalldivi + (totalgajitunjanganservice - totalpotongan);
                            grandtotalallcomp = grandtotalallcomp + (totalgajitunjanganservice - totalpotongan);

                            rowx.add("Rp. " + Formater.formatNumber((totalgajitunjanganservice - totalpotongan), "#,###.##"));



                        }
                        ctrlist.drawListRowExcel(outJsp, 0, rowx, i);

                        //mengecek section selanjutnya
                        Vector temp2 = new Vector();
                        String section2 = "";
                        String company2 = "";
                        String division2 = "";
                        String department2 = "";
                        if (i != (listdatanew.size() - 1)) {
                            if (listdatanew.get(i + 1) != null) {
                                temp2 = (Vector) listdatanew.get(i + 1);

                                company2 = (String) temp2.get(1);
                                division2 = (String) temp2.get(2);
                                department2 = (String) temp2.get(3);
                                if (temp2.get(4) != null) {
                                    section2 = (String) temp2.get(4);
                                } else {
                                    section2 = "";
                                }

                            } else {;
                                section2 = "";
                                company2 = "";
                                division2 = "";
                                department2 = "";
                            }
                        }

                        // cetak grand total section
                        if ((TempSection2.equals("")) && (!section2.equals(""))) {
                            TempSection2 = section2;
                        }
                        if ((!TempSection2.equals(section2)) && ((!TempSection2.equals("")) || (TempSection2 != null))) {
                            no = 0;
                            TempSection2 = section2;
                            Vector rowxtotalsec = new Vector();
                            rowxtotalsec.add("");
                            rowxtotalsec.add("<B>TOTAL</B>");
                            rowxtotalsec.add("<B> --> SECTION </B>");
                            rowxtotalsec.add("");
                            rowxtotalsec.add("");
                            rowxtotalsec.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponenttemp.getCompType() == 1) || ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID))) {
                                        double tsect = Double.valueOf(totalSection.get(d).toString());
                                        rowxtotalsec.add("Rp. " + Formater.formatNumber(tsect, "#,###.##"));
                                    }
                                }
                            }

                            rowxtotalsec.add("Rp. " + Formater.formatNumber((grandtotalallsect), "#,###.##"));
                            grandtotalallsect = 0;
                            //set total section default

                            if (vpcomponent != null && vpcomponent.size() > 0) {
                                for (int d = 0; d < vpcomponent.size(); d++) {
                                    totalSection.set(d, 0);
                                }
                            }

                            ctrlist.drawListRowExcel(outJsp, 0, rowxtotalsec, i);
                        }

                        // cetak grand total department
                        if (((!TempDepartment2.equals(department2)) && (department2 != null) && i != 0) || (i == (listdatanew.size() - 1))) {
                            no = 0;
                            TempDepartment2 = department2;
                            Vector rowxtotaldepart = new Vector();
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("<B>TOTAL</B>");
                            rowxtotaldepart.add("<B> --> DEPARTMENT </B>");
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponenttemp.getCompType() == 1) || ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID))) {
                                        double tdepart = Double.valueOf(totalDepartment.get(d).toString());
                                        rowxtotaldepart.add("Rp. " + Formater.formatNumber(tdepart, "#,###.##"));
                                    }

                                }
                            }


                            rowxtotaldepart.add("Rp. " + Formater.formatNumber((grandtotalalldept), "#,###.##"));
                            grandtotalalldept = 0;
                            //set total section default

                            if (vpcomponent != null && vpcomponent.size() > 0) {
                                for (int d = 0; d < vpcomponent.size(); d++) {
                                    totalDepartment.set(d, 0);
                                }
                            }

                            ctrlist.drawListRowExcel(outJsp, 0, rowxtotaldepart, i);
                        }


                        // cetak grand total division
                        if (((!TempDivision2.equals(division2)) && (division2 != null) && i != 0) || (i == (listdatanew.size() - 1))) {
                            no = 0;
                            TempDivision2 = division2;
                            Vector rowxtotaldiv = new Vector();
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("<B>TOTAL</B>");
                            rowxtotaldiv.add("<B> --> DIVISION </B>");
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponenttemp.getCompType() == 1) || ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID))) {
                                        double tdivi = Double.valueOf(totalDivision.get(d).toString());
                                        rowxtotaldiv.add("Rp. " + Formater.formatNumber(tdivi, "#,###.##"));
                                    }
                                }
                            }



                            rowxtotaldiv.add("Rp. " + Formater.formatNumber((grandtotalalldivi), "#,###.##"));
                            grandtotalalldivi = 0;
                            //set total section default

                            if (vpcomponent != null && vpcomponent.size() > 0) {
                                for (int d = 0; d < vpcomponent.size(); d++) {
                                    totalDivision.set(d, 0);
                                }
                            }

                            ctrlist.drawListRowExcel(outJsp, 0, rowxtotaldiv, i);
                        }



                        // cetak grand total company
                        if (((!TempCompany2.equals(company2)) && (company2 != null) && i != 0) || (i == (listdatanew.size() - 1))) {
                            no = 0;
                            TempCompany2 = company2;
                            Vector rowxtotalcomp = new Vector();
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("<B>TOTAL</B>");
                            rowxtotalcomp.add("<B> --> COMPANY </B>");
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponenttemp.getCompType() == 1) || ((COMPONENT_TOTAL_POTONGAN_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_TOTAL_POTONGAN_OID))) {
                                        double tcompa = Double.valueOf(totalCompany.get(d).toString());
                                        rowxtotalcomp.add("Rp. " + Formater.formatNumber(tcompa, "#,###.##"));
                                    }
                                }
                            }

                            rowxtotalcomp.add("Rp. " + Formater.formatNumber((grandtotalallcomp), "#,###.##"));
                            grandtotalallcomp = 0;
                            //set total section default

                            if (vpcomponent != null && vpcomponent.size() > 0) {
                                for (int d = 0; d < vpcomponent.size(); d++) {
                                    totalCompany.set(d, 0);
                                }
                            }

                            ctrlist.drawListRowExcel(outJsp, 0, rowxtotalcomp, i);
                        }


                        no = no + 1;
                    }
                }

                //ctrlist.drawListEndTable(outJsp);

                ctrlist.drawListEndTable(outJsp);

            } else {
                System.out.println("Belum ada data");
            }
        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + empNumTest + " " + ex);
        }
        return result;
    }
%>

<%!    public String drawList2(JspWriter outJsp, Vector listdatanew, Vector vpcomponent, long sourcetype, Hashtable listAttdAbsensi, Vector vReason) {
        String result = "";

        if (sourcetype == 0) {
            sourcetype = 1;
        } else if (sourcetype == 1) {
            sourcetype = 2;
        }



        long propGPokok = -1;
        try {
            propGPokok = Long.parseLong(PstSystemProperty.getValueByName("OID_GAJIPOKOK"));
        } catch (Exception ex) {
            System.out.println("Execption DAILY WORKER: " + ex);
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

        String ClientName = "";
        try {
            ClientName = String.valueOf(PstSystemProperty.getValueByName("CLIENT_NAME"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
        }

        boolean withH_EO = false;
        if ((ClientName.equals("PEPITO")) && (ClientName.equals("MINIMART"))) {
            withH_EO = true;
        }

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        ctrlist.setMaxFreezingTable(2);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("Head Count", "5%", "2", "0");
        ctrlist.addHeader("No", "5%", "2", "0");
        ctrlist.addHeader("Nama", "5%", "2", "0");
        ctrlist.addHeader("BANK", "5%", "2", "0");
        ctrlist.addHeader("BANK ACC", "5%", "2", "0");
        ctrlist.addHeader("HARI KEHADIRAN", "2%", "2", "0");

        //reason
        if ((ClientName.equals("PEPITO"))) {
                    if (withH_EO) {
                    ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason != null && vReason.size() > 0 ? 11 + vReason.size() : 11));
                    } else {
                    ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason != null && vReason.size() > 0 ? 10 + vReason.size() : 10));
                    }
        } else {
                    if (withH_EO) {
                    ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason != null && vReason.size() > 0 ? 9 + vReason.size() : 9));
                    } else {
                    ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason != null && vReason.size() > 0 ? 8 + vReason.size() : 8));
                    }
        }
        

        ctrlist.addHeader("HK", "5%", "0", "0");
        
        if (withH_EO) {
            ctrlist.addHeader("H", "5%", "0", "0");
            ctrlist.addHeader("E", "5%", "0", "0");
        } else {
            ctrlist.addHeader("H", "5%", "0", "0");
        }

        ctrlist.addHeader("R", "5%", "0", "0");
        ctrlist.addHeader("AL", "5%", "0", "0");
        ctrlist.addHeader("DP", "5%", "0", "0");
        ctrlist.addHeader("SL", "5%", "0", "0");
        ctrlist.addHeader("UL", "5%", "0", "0");
        if (vReason != null && vReason.size() > 0) {
            for (int d = 0; d < vReason.size(); d++) {
                Reason reason = (Reason) vReason.get(d);
                ctrlist.addHeader("" + reason.getKodeReason(), "5%", "0", "0");
            }
        }
        if ((ClientName.equals("PEPITO"))) {
            ctrlist.addHeader("SO 4", "5%", "0", "0");
            ctrlist.addHeader("SO 8", "5%", "0", "0");
        }
        ctrlist.addHeader("Total", "5%", "0", "0");

        //
        ctrlist.addHeader("GAJI HARIAN", "5%", "2", "0");
        ctrlist.addHeader("GAJI POKOK", "5%", "2", "0");

        int n = 0;
        if (vpcomponent != null && vpcomponent.size() > 0) {
            for (int d = 0; d < vpcomponent.size(); d++) {
                PayComponent payComponent = (PayComponent) vpcomponent.get(d);
                if ((payComponent.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponent.getOID() == COMPONENT_GAJI_BRUTO_OID))) {
                    n = n + 1;
                }
            }
        }
        // ctrlist.addHeader("Absensi", "20%", "0", "" + (vpcomponent!=null && vpcomponent.size()>0?8+n:8));
        ctrlist.addHeader("Component", "20%", "0", "" + (vpcomponent != null && vpcomponent.size() > 0 ? n : 0));
        if (vpcomponent != null && vpcomponent.size() > 0) {
            for (int d = 0; d < vpcomponent.size(); d++) {
                PayComponent payComponent = (PayComponent) vpcomponent.get(d);
                if ((payComponent.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponent.getOID() == COMPONENT_GAJI_BRUTO_OID))) {
                    ctrlist.addHeader("" + payComponent.getCompName(), "5%", "0", "0");
                }
            }
        }

        ctrlist.addHeader("GRAND TOTAL", "5%", "2", "0");
        //ctrlist.addHeader("Gaji Harian", "5%", "0", "0");

        String empNumTest = "";
        int no = 0;
        String TempCompany = "";
        String TempDivision = "";
        String TempDepartment = "";
        String TempSection = "";

        String TempCompany2 = "";
        String TempDivision2 = "";
        String TempDepartment2 = "";
        String TempSection2 = "";

        //set total company default
        Vector totalCompany = new Vector();
        Vector totalDivision = new Vector();
        Vector totalDepartment = new Vector();
        Vector totalSection = new Vector();

        if (vpcomponent != null && vpcomponent.size() > 0) {
            for (int d = 0; d < vpcomponent.size(); d++) {
                totalCompany.add(0);
                totalDivision.add(0);
                totalDepartment.add(0);
                totalSection.add(0);
            }
        }

        double grandtotalbenefitallsect = 0;
        double grandtotalbenefitalldept = 0;
        double grandtotalbenefitalldivi = 0;
        double grandtotalbenefitallcomp = 0;

        double grandtotalpotonganallsect = 0;
        double grandtotalpotonganalldept = 0;
        double grandtotalpotonganalldivi = 0;
        double grandtotalpotonganallcomp = 0;

        double grandtotalallsect = 0;
        double grandtotalalldept = 0;
        double grandtotalalldivi = 0;
        double grandtotalallcomp = 0;

        try {
            if (listdatanew.size() > 0) {
                ctrlist.drawListHeaderExcel(outJsp);//header
                //ctrlist.drawListHeaderWithJsVer2(outJsp);//header
                for (int i = 0; i < listdatanew.size(); i++) {
                    Vector temp = (Vector) listdatanew.get(i);


                    int taxHomepay = 0;

                    Employee employee = (Employee) temp.get(0);

                    RekapitulasiPresenceDanSchedule rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule();
                    if (listAttdAbsensi != null && listAttdAbsensi.size() > 0 && listAttdAbsensi.containsKey("" + employee.getOID())) {
                        rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule) listAttdAbsensi.get("" + employee.getOID());
                        listAttdAbsensi.remove("" + employee.getOID());
                    }
                    if (rekapitulasiPresenceDanSchedule.getPresenceOnTime() > 0) {

                        String Company = (String) temp.get(1);
                        String Division = (String) temp.get(2);
                        String Department = (String) temp.get(3);
                        String Section = "";
                        if (temp.get(5) != null) {
                            Section = (String) temp.get(4);
                        } else {
                            Section = "";
                        }
                        String empCategory = "";
                        if (temp.get(8) != null) {
                            empCategory = (String) temp.get(8);
                        } else {
                            empCategory = "";
                        }


                        PayBanks payBanks = (PayBanks) temp.get(5);
                        PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(6);
                        Vector pcomp = (Vector) temp.get(7);

                        if (i == 0) {
                            TempCompany2 = (String) temp.get(1);
                            TempDivision2 = (String) temp.get(2);
                            TempDepartment2 = (String) temp.get(3);
                            if (temp.get(4) != null) {
                                TempSection2 = (String) temp.get(4);
                            } else {
                                TempSection2 = "";
                            }

                        }

                        if ((!TempCompany.equals(Company)) && (Company != null)) {

                            //cetak dulu jumlah yang sebelumnya jumlah total per company

                            no = 0;
                            TempCompany = Company;
                            Vector rowxcomp = new Vector();
                            rowxcomp.add("");
                            rowxcomp.add("<B>COMPANY</B>");
                            rowxcomp.add("<B>" + Company + "</B>");
                            rowxcomp.add("");
                            rowxcomp.add("");
                            rowxcomp.add("");

                            rowxcomp.add("");
                            //reason
                            if (withH_EO) {
                                rowxcomp.add("");
                                rowxcomp.add("");
                            } else {
                                rowxcomp.add("");
                            }

                            rowxcomp.add("");
                            rowxcomp.add("");
                            rowxcomp.add("");
                            rowxcomp.add("");
                            rowxcomp.add("");
                            if (vReason != null && vReason.size() > 0) {
                                for (int d = 0; d < vReason.size(); d++) {
                                    rowxcomp.add("");
                                }
                            }
                            if ((ClientName.equals("PEPITO"))) {
                                rowxcomp.add("");
                                rowxcomp.add("");
                            }
                            rowxcomp.add("");


                            //gaji harian
                            rowxcomp.add("");
                            //gaji pokok
                            rowxcomp.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponttemp.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponttemp.getCompId() == COMPONENT_GAJI_BRUTO_OID))) {
                                        rowxcomp.add("");
                                    }
                                }
                            }

                            rowxcomp.add("");
                            ctrlist.drawListRowExcel(outJsp, 0, rowxcomp, i);
                        } else {
                        }

                        if ((!TempDivision.equals(Division)) && (Division != null)) {
                            no = 0;
                            TempDivision = Division;
                            Vector rowx = new Vector();
                            rowx.add("");
                            rowx.add("<B>DIVISION</B>");
                            rowx.add("<B> ->" + Division + "</B>");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");

                            //reason 
                            //reason
                            rowx.add("");
                            if (withH_EO) {
                                rowx.add("");
                                rowx.add("");
                            } else {
                                rowx.add("");
                            }

                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            if (vReason != null && vReason.size() > 0) {
                                for (int d = 0; d < vReason.size(); d++) {
                                    rowx.add("");
                                }
                            }
                            if ((ClientName.equals("PEPITO"))) {
                                rowx.add("");
                                rowx.add("");
                            }
                            rowx.add("");

                            //gaji harian
                            rowx.add("");
                            //gaji 
                            rowx.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponttemp.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponttemp.getCompId() == COMPONENT_GAJI_BRUTO_OID))) {
                                        rowx.add("");
                                    }
                                }
                            }
                            rowx.add("");
                            ctrlist.drawListRowExcel(outJsp, 0, rowx, i);
                        }

                        if ((!TempDepartment.equals(Department)) && (Department != null)) {
                            no = 0;
                            TempDepartment = Department;
                            Vector rowx = new Vector();
                            rowx.add("");
                            rowx.add("<B>DEPARTMENT</B>");
                            rowx.add("<B> -->" + Department + "</B>");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");

                            //reason
                            //reason
                            rowx.add("");
                            if (withH_EO) {
                                rowx.add("");
                                rowx.add("");
                            } else {
                                rowx.add("");
                            }

                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            if (vReason != null && vReason.size() > 0) {
                                for (int d = 0; d < vReason.size(); d++) {
                                    rowx.add("");
                                }
                            }
                            if ((ClientName.equals("PEPITO"))) {
                                rowx.add("");
                                rowx.add("");
                            }
                            rowx.add("");

                            //gaji harian
                            rowx.add("");
                            //gaji 
                            rowx.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponttemp.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponttemp.getCompId() == COMPONENT_GAJI_BRUTO_OID))) {
                                        rowx.add("");
                                    }
                                }
                            }
                            rowx.add("");
                            ctrlist.drawListRowExcel(outJsp, 0, rowx, i);
                        }

                        if ((!TempSection.equals(Section)) && (Section != null)) {
                            no = 0;
                            TempSection = Section;
                            Vector rowx = new Vector();
                            rowx.add("");
                            rowx.add("<B>SECTION</B>");
                            rowx.add("<B> -->" + Section + "</B>");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");

                            //reason
                            //reason
                            rowx.add("");
                            if (withH_EO) {
                                rowx.add("");
                                rowx.add("");
                            } else {
                                rowx.add("");
                            }

                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            rowx.add("");
                            if (vReason != null && vReason.size() > 0) {
                                for (int d = 0; d < vReason.size(); d++) {
                                    rowx.add("");
                                }
                            }
                            if ((ClientName.equals("PEPITO"))) {
                                rowx.add("");
                                rowx.add("");
                            }
                            rowx.add("");

                            //gaji harian
                            rowx.add("");
                            //gaji 
                            rowx.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponttemp.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponttemp.getCompId() == COMPONENT_GAJI_BRUTO_OID))) {
                                        rowx.add("");
                                    }
                                }
                            }
                            rowx.add("");
                            ctrlist.drawListRowExcel(outJsp, 0, rowx, i);
                        } else {
                        }

                        Vector rowx = new Vector();
                        rowx.add("" + (no + 1));
                        rowx.add("" + employee.getEmployeeNum());
                        rowx.add("" + employee.getFullName());
                        rowx.add(payBanks.getBankName() != null ? "" + payBanks.getBankName() : "-");
                        rowx.add("" + payEmpLevel.getBankAccNr());
                        rowx.add("" + rekapitulasiPresenceDanSchedule.getPresenceOnTime());


                        //cetak total reason
                        //rowx.add("" + rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                        //rowx.add("" + rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                        //if (vReason != null && vReason.size() > 0) {
                        //    for (int d = 0; d < vReason.size(); d++) {
                        //        Reason reason = (Reason) vReason.get(d);
                        //        int totReason = rekapitulasiPresenceDanSchedule.getTotalReason(reason.getNo());
                        //        rowx.add("" + totReason);
                        //    }
                        //}

                        //reason
                        rowx.add("" + rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                        if (withH_EO) {
                            rowx.add("" + rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                            rowx.add("" + rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                        } else {
                            rowx.add("" + rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                        }

                        rowx.add("" + rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                        rowx.add("" + rekapitulasiPresenceDanSchedule.getAnnualLeave());
                        rowx.add("" + rekapitulasiPresenceDanSchedule.getdPayment());
                        rowx.add("" + rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                        rowx.add("" + rekapitulasiPresenceDanSchedule.getSpecialLeave());

                        if (vReason != null && vReason.size() > 0) {
                            for (int d = 0; d < vReason.size(); d++) {
                                Reason reason = (Reason) vReason.get(d);
                                int totReason = rekapitulasiPresenceDanSchedule.getTotalReason(reason.getNo());
                                rowx.add("" + totReason);
                            }
                        }
                        if ((ClientName.equals("PEPITO"))) {
                            rowx.add("" + rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                            rowx.add("" + rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                        }
                        if (withH_EO) {
                            rowx.add("" + rekapitulasiPresenceDanSchedule.getTotalSemuanyaByPepito());
                        } else {
                            rowx.add("" + rekapitulasiPresenceDanSchedule.getTotalSemuanya());
                        }


                        rowx.add("Rp.-");
                        rowx.add("Rp. " + Formater.formatNumber(80, "#,###.##"));

                        double totalgajitunjanganservice = 0;
                        double totalpotongan = 0;
                        if (pcomp != null && pcomp.size() > 0) {
                            for (int d = 0; d < pcomp.size(); d++) {
                                PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                // && (payComponenttemp.getCompId() == propGPokok)
                                taxHomepay = PstPayEmpLevel.getTaxHomePayStatus(employee.getOID(), payComponenttemp.getCompCode());

                                if ((payComponenttemp.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_GAJI_BRUTO_OID))) {

                                    if (rekapitulasiPresenceDanSchedule.getPresenceOnTime() == 0) {
                                        rowx.add("Rp.-");
                                    } else {
                                        //total section
                                        double totalsect = Double.parseDouble(totalSection.get(d).toString());
                                        totalSection.set(d, totalsect + payComponenttemp.getCompValue());
                                        //total percompany
                                        double totalcomp = Double.parseDouble(totalCompany.get(d).toString());
                                        totalCompany.set(d, totalcomp + payComponenttemp.getCompValue());
                                        //total division
                                        double totaldiv = Double.parseDouble(totalDivision.get(d).toString());
                                        totalDivision.set(d, totaldiv + payComponenttemp.getCompValue());
                                        //total dpartment
                                        double totaldepart = Double.parseDouble(totalDepartment.get(d).toString());
                                        totalDepartment.set(d, totaldepart + payComponenttemp.getCompValue());

                                        rowx.add("Rp. " + Formater.formatNumber(payComponenttemp.getCompValue(), "#,###.##"));

                                        if (COMPONENT_GAJI_BRUTO_OID == payComponenttemp.getCompId()) {
                                            totalgajitunjanganservice = payComponenttemp.getCompValue();
                                        }
                                        if (COMPONENT_TOTAL_POTONGAN_OID == payComponenttemp.getCompId()) {
                                            totalpotongan = payComponenttemp.getCompValue();
                                        }

                                    }
                                }
                            }
                        }
                        if (rekapitulasiPresenceDanSchedule.getPresenceOnTime() == 0) {
                            rowx.add("Rp.-");
                        } else {

                            //hitung total semua
                            grandtotalallsect = grandtotalallsect + (totalgajitunjanganservice - totalpotongan);
                            grandtotalalldept = grandtotalalldept + (totalgajitunjanganservice - totalpotongan);
                            grandtotalalldivi = grandtotalalldivi + (totalgajitunjanganservice - totalpotongan);
                            grandtotalallcomp = grandtotalallcomp + (totalgajitunjanganservice - totalpotongan);

                            rowx.add("Rp. " + Formater.formatNumber((totalgajitunjanganservice - totalpotongan), "#,###.##"));

                        }
                        ctrlist.drawListRowExcel(outJsp, 0, rowx, i);

                        //mengecek section selanjutnya
                        Vector temp2 = new Vector();
                        String section2 = "";
                        String company2 = "";
                        String division2 = "";
                        String department2 = "";
                        if (i != (listdatanew.size() - 1)) {
                            if (listdatanew.get(i + 1) != null) {
                                temp2 = (Vector) listdatanew.get(i + 1);

                                company2 = (String) temp2.get(1);
                                division2 = (String) temp2.get(2);
                                department2 = (String) temp2.get(3);
                                if (temp2.get(4) != null) {
                                    section2 = (String) temp2.get(4);
                                } else {
                                    section2 = "";
                                }

                            } else {;
                                section2 = "";
                                company2 = "";
                                division2 = "";
                                department2 = "";
                            }
                        }

                        // cetak grand total section
                        if ((TempSection2.equals("")) && (!section2.equals(""))) {
                            TempSection2 = section2;
                        }
                        if ((!TempSection2.equals(section2)) && ((!TempSection2.equals("")) || (TempSection2 != null))) {
                            no = 0;
                            TempSection2 = section2;
                            Vector rowxtotalsec = new Vector();
                            rowxtotalsec.add("");
                            rowxtotalsec.add("<B>TOTAL</B>");
                            rowxtotalsec.add("<B> --> SECTION </B>");
                            rowxtotalsec.add("");
                            rowxtotalsec.add("");
                            rowxtotalsec.add("");
                            rowxtotalsec.add("");
                            
                            //reason
                            if (withH_EO) {
                                rowxtotalsec.add("");
                                rowxtotalsec.add("");
                            } else {
                                rowxtotalsec.add("");
                            }

                            rowxtotalsec.add("");
                            rowxtotalsec.add("");
                            rowxtotalsec.add("");
                            rowxtotalsec.add("");
                            rowxtotalsec.add("");
                            if (vReason != null && vReason.size() > 0) {
                                for (int d = 0; d < vReason.size(); d++) {
                                    rowxtotalsec.add("");
                                }
                            }
                            if ((ClientName.equals("PEPITO"))) {
                                rowxtotalsec.add("");
                                rowxtotalsec.add("");
                            }
                            rowxtotalsec.add("");

                            //
                            rowxtotalsec.add("");
                            rowxtotalsec.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponenttemp.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_GAJI_BRUTO_OID))) {
                                        double tsect = Double.valueOf(totalSection.get(d).toString());
                                        rowxtotalsec.add("Rp. " + Formater.formatNumber(tsect, "#,###.##"));
                                    }
                                }
                            }

                            rowxtotalsec.add("Rp. " + Formater.formatNumber((grandtotalallsect), "#,###.##"));
                            grandtotalallsect = 0;
                            //set total section default

                            if (vpcomponent != null && vpcomponent.size() > 0) {
                                for (int d = 0; d < vpcomponent.size(); d++) {
                                    totalSection.set(d, 0);
                                }
                            }

                            ctrlist.drawListRowExcel(outJsp, 0, rowxtotalsec, i);
                        }




                        // cetak grand total department
                        if (((!TempDepartment2.equals(department2)) && (department2 != null) && i != 0) || (i == (listdatanew.size() - 1))) {
                            no = 0;
                            TempDepartment2 = department2;
                            Vector rowxtotaldepart = new Vector();
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("<B>TOTAL</B>");
                            rowxtotaldepart.add("<B> --> DEPARTMENT </B>");
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("");

                            //reason
                            rowxtotaldepart.add("");
                            if (withH_EO) {
                                rowxtotaldepart.add("");
                                rowxtotaldepart.add("");
                            } else {
                                rowxtotaldepart.add("");
                            }

                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("");
                            if (vReason != null && vReason.size() > 0) {
                                for (int d = 0; d < vReason.size(); d++) {
                                    rowxtotaldepart.add("");
                                }
                            }
                            if ((ClientName.equals("PEPITO"))) {
                                rowxtotaldepart.add("");
                                rowxtotaldepart.add("");
                            }
                            rowxtotaldepart.add("");

                            //
                            //gaji harian dan gaji normal
                            rowxtotaldepart.add("");
                            rowxtotaldepart.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponenttemp.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_GAJI_BRUTO_OID))) {
                                        double tdepart = Double.valueOf(totalDepartment.get(d).toString());
                                        rowxtotaldepart.add("Rp. " + Formater.formatNumber(tdepart, "#,###.##"));
                                    }

                                }
                            }

                            rowxtotaldepart.add("Rp. " + Formater.formatNumber((grandtotalalldept), "#,###.##"));
                            grandtotalalldept = 0;
                            //set total section default

                            if (vpcomponent != null && vpcomponent.size() > 0) {
                                for (int d = 0; d < vpcomponent.size(); d++) {
                                    totalDepartment.set(d, 0);
                                }
                            }

                            ctrlist.drawListRowExcel(outJsp, 0, rowxtotaldepart, i);
                        }


                        // cetak grand total division
                        if (((!TempDivision2.equals(division2)) && (division2 != null) && i != 0) || (i == (listdatanew.size() - 1))) {
                            no = 0;
                            TempDivision2 = division2;
                            Vector rowxtotaldiv = new Vector();
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("<B>TOTAL</B>");
                            rowxtotaldiv.add("<B> --> DIVISION </B>");
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("");

                            //reason
                            rowxtotaldiv.add("");
                            if (withH_EO) {
                                rowxtotaldiv.add("");
                                rowxtotaldiv.add("");
                            } else {
                                rowxtotaldiv.add("");
                            }

                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("");
                            if (vReason != null && vReason.size() > 0) {
                                for (int d = 0; d < vReason.size(); d++) {
                                    rowxtotaldiv.add("");
                                }
                            }
                            if ((ClientName.equals("PEPITO"))) {
                                rowxtotaldiv.add("");
                                rowxtotaldiv.add("");
                            }
                            rowxtotaldiv.add("");


                            //gaji
                            rowxtotaldiv.add("");
                            rowxtotaldiv.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponenttemp.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_GAJI_BRUTO_OID))) {
                                        double tdivi = Double.valueOf(totalDivision.get(d).toString());
                                        rowxtotaldiv.add("Rp. " + Formater.formatNumber(tdivi, "#,###.##"));
                                    }
                                }
                            }

                            rowxtotaldiv.add("Rp. " + Formater.formatNumber((grandtotalalldivi), "#,###.##"));
                            grandtotalalldivi = 0;
                            //set total section default

                            if (vpcomponent != null && vpcomponent.size() > 0) {
                                for (int d = 0; d < vpcomponent.size(); d++) {
                                    totalDivision.set(d, 0);
                                }
                            }

                            ctrlist.drawListRowExcel(outJsp, 0, rowxtotaldiv, i);
                        }



                        // cetak grand total company
                        if (((!TempCompany2.equals(company2)) && (company2 != null) && i != 0) || (i == (listdatanew.size() - 1))) {
                            no = 0;
                            TempCompany2 = company2;
                            Vector rowxtotalcomp = new Vector();
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("<B>TOTAL</B>");
                            rowxtotalcomp.add("<B> --> COMPANY </B>");
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("");
                            //reason
                            rowxtotalcomp.add("");
                            if (withH_EO) {
                                rowxtotalcomp.add("");
                                rowxtotalcomp.add("");
                            } else {
                                rowxtotalcomp.add("");
                            }

                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("");
                            if (vReason != null && vReason.size() > 0) {
                                for (int d = 0; d < vReason.size(); d++) {
                                    Reason reason = (Reason) vReason.get(d);
                                    rowxtotalcomp.add("");
                                }
                            }
                            if ((ClientName.equals("PEPITO"))) {
                                rowxtotalcomp.add("");
                                rowxtotalcomp.add("");
                            }
                            rowxtotalcomp.add("");


                            //gaji harian
                            rowxtotalcomp.add("");
                            rowxtotalcomp.add("");

                            if (pcomp != null && pcomp.size() > 0) {
                                for (int d = 0; d < pcomp.size(); d++) {
                                    PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                    if ((payComponenttemp.getCompType() == 2) || ((COMPONENT_GAJI_BRUTO_OID != 0) && (payComponenttemp.getCompId() == COMPONENT_GAJI_BRUTO_OID))) {
                                        double tcompa = Double.valueOf(totalCompany.get(d).toString());
                                        rowxtotalcomp.add("Rp. " + Formater.formatNumber(tcompa, "#,###.##"));
                                    }
                                }
                            }

                            rowxtotalcomp.add("Rp. " + Formater.formatNumber((grandtotalallcomp), "#,###.##"));
                            grandtotalallcomp = 0;
                            //set total section default

                            if (vpcomponent != null && vpcomponent.size() > 0) {
                                for (int d = 0; d < vpcomponent.size(); d++) {
                                    totalCompany.set(d, 0);
                                }
                            }

                            ctrlist.drawListRowExcel(outJsp, 0, rowxtotalcomp, i);
                        }


                        no = no + 1;
                    }
                }

                ctrlist.drawListEndTable(outJsp);

            } else {
                System.out.println("Belum ada data");
            }
        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + empNumTest + " " + ex);
        }
        return result;
    }
%>
<%!    public String drawListTdd(JspWriter outJsp, int source_type) {
        String result = "";
        ControlList ctrlist = new ControlList();
        ControlList ctrlist1 = new ControlList();
        ControlList ctrlist2 = new ControlList();
        ControlList ctrlist3 = new ControlList();
        
        if (source_type == 0){
            
        ctrlist.setAreaWidth("70%");
        ctrlist.addHeader("<center>Hr Unit</center>", "10%","0","2");
        ctrlist.addHeader("<center>Acct Unit</center>", "10%", "0","1");
        ctrlist.addHeader("<center>Pimpinan Unit</center>", "10%","0","3");
        ctrlist.addHeader("<center>Hr Coorporate</center>", "10%", "0","2");
        ctrlist.addHeader("<center>Director</center>", "10%", "0","2");
        ctrlist.addHeader("<center>Pres Dir & CEO</center>", "10%","0","2");
       
        ctrlist1.setAreaWidth("70%");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","2");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","1");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","3");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","2");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","2");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","2");
        
        ctrlist2.setAreaWidth("70%");
        ctrlist2.addHeader("<center> </center>", "10%","3","2");
        ctrlist2.addHeader("<center> </center>", "10%", "3","1");
        ctrlist2.addHeader("<center> </center>", "10%","3","3");
        ctrlist2.addHeader("<center> </center>", "10%", "3","2");
        ctrlist2.addHeader("<center> </center>", "10%", "3","2");
        ctrlist2.addHeader("<center> </center>", "10%","3","2");
       
        ctrlist3.setAreaWidth("70%");
        ctrlist3.addHeader("<center> </center>", "10%","0","2");
        ctrlist3.addHeader("<center> </center>", "10%", "0","1");
        ctrlist3.addHeader("<center> </center>", "10%","0","3");
        ctrlist3.addHeader("<center> </center>", "10%", "0","2");
        ctrlist3.addHeader("<center> </center>", "10%", "0","2");
        ctrlist3.addHeader("<center> </center>", "10%","0","2");
        
        } else {
        ctrlist.setAreaWidth("70%");
        ctrlist.addHeader("<center>Hr Unit</center>", "10%","0","2");
        ctrlist.addHeader("<center>Acct Unit</center>", "10%", "0","1");
        ctrlist.addHeader("<center>Pimpinan Unit</center>", "10%","0","3");
        ctrlist.addHeader("<center>Hr Coorporate</center>", "10%", "0","5");
        ctrlist.addHeader("<center>Director</center>", "10%", "0","4");
        ctrlist.addHeader("<center>Pres Dir & CEO</center>", "10%","0","2");
       
        ctrlist1.setAreaWidth("70%");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","2");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","1");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","3");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","5");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","4");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","2");
        
        ctrlist2.setAreaWidth("70%");
        ctrlist2.addHeader("<center> </center>", "10%", "3","2");
        ctrlist2.addHeader("<center> </center>", "10%", "3","1");
        ctrlist2.addHeader("<center> </center>", "10%", "3","3");
        ctrlist2.addHeader("<center> </center>", "10%", "3","5");
        ctrlist2.addHeader("<center> </center>", "10%", "3","4");
        ctrlist2.addHeader("<center> </center>", "10%", "3","2");
       
        ctrlist3.setAreaWidth("70%");
        ctrlist3.addHeader("<center> </center>", "10%","0","2");
        ctrlist3.addHeader("<center> </center>", "10%", "0","1");
        ctrlist3.addHeader("<center> </center>", "10%","0","3");
        ctrlist3.addHeader("<center> </center>", "10%", "0","5");
        ctrlist3.addHeader("<center> </center>", "10%", "0","4");
        ctrlist3.addHeader("<center> </center>", "10%","0","2");    
        }
        
        int index = -1;
        String empNumTest = "";
        int no = 0;
        ctrlist.drawListHeaderExcel(outJsp);//header
        ctrlist.drawListEndTable(outJsp);
        ctrlist1.drawListHeaderExcel1(outJsp);//header
        ctrlist1.drawListEndTable(outJsp);  
        ctrlist2.drawListHeaderExcel1(outJsp);//header
        ctrlist2.drawListEndTable(outJsp); 
        ctrlist3.drawListHeaderExcel1(outJsp);//header
        ctrlist3.drawListEndTable(outJsp); 
       
        return result;
    }

%>


<%

    String source = FRMQueryString.requestString(request, "source");
    int source_type = FRMQueryString.requestInt(request, "source_type");
    int iCommand = FRMQueryString.requestCommand(request);
    
    ListBenefitDeduction listBenefitDeduction = new ListBenefitDeduction();
    if(session.getValue("benefitdeduction")!=null){
        listBenefitDeduction = (ListBenefitDeduction)session.getValue("benefitdeduction"); 
    }
    String where = "" ;
    if(session.getValue("where")!=null){
        where = (String)session.getValue("where"); 
    }
    Vector listCompany = new Vector();
    Hashtable hashDivision = new Hashtable();
    Hashtable hashDepartment = new Hashtable();
    Hashtable hashSection = new Hashtable();
    Hashtable hashEmployee  = new Hashtable();
    Hashtable hashEmployeeSection = new Hashtable();
    Hashtable listAttdAbsensi =null;
      Date dtFrom = null; 
      Date dtTo = null;
      
      
      String judul="BENEFIT DEDUCTION";
    Vector vReason=null;
    I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
    int iPropInsentifLevel = 0;
        long lHolidays = 0;
        try {
            iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
            lHolidays = Long.parseLong(PstSystemProperty.getValueByName("OID_PUBLIC_HOLIDAY"));
        } catch (Exception ex) {
            System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
        }

        I_PayrollCalculator payrollCalculatorConfig = null;
        try {
            payrollCalculatorConfig = (I_PayrollCalculator) (Class.forName(PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception PAYROLL_CALC_CLASS_NAME " + e.getMessage());
        }
        int propReasonNo = -1;
        try {
            propReasonNo = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_REASON_DUTTY_NO"));

        } catch (Exception ex) {
            System.out.println("Execption REASON_DUTTY_NO: " + ex);
        }
        int propCheckLeaveExist = 0;
        try {
            propCheckLeaveExist = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_WHEN_LEAVE_EXIST"));

        } catch (Exception ex) {
            System.out.println("Execption ATTANDACE_WHEN_LEAVE_EXIST: " + ex);
        }

       int showOvertime = 0;
        try {
            showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
        } catch (Exception ex) {

            System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>");
            showOvertime = 0;
        }

        //update by satrya 2013-04-09
        long oidScheduleOff = 0;
        try {
            oidScheduleOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
        } catch (Exception ex) {

            System.out.println("<blink>OID_DAY_OFF NOT TO BE SET</blink>");
            oidScheduleOff = 0;
        }
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
      
       Hashtable vctSchIDOff = new Hashtable();
        Hashtable hashSchOff = new Hashtable();
        HolidaysTable holidaysTable = new HolidaysTable();
        Hashtable hashPositionLevel = PstPosition.hashGetPositionLevel();
        String whereClausePeriod = "";
        Date dtfrom = PstPayPeriod.getfromdate(listBenefitDeduction.getPeriodeId());
        
        Date dtend = PstPayPeriod.getenddate(listBenefitDeduction.getPeriodeId());
        
        if (dtend != null && dtfrom != null) {
            whereClausePeriod = "\"" + Formater.formatDate(dtend, "yyyy-MM-dd HH:mm:ss") + "\" >="
                    + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " AND "
                    + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= \"" + Formater.formatDate(dtfrom, "yyyy-MM-dd HH:mm:ss") + "\"";
        }

        Hashtable hashPeriod = new Hashtable();
        Vector vPayComponent = null;
        Vector Listdatanew = null;    

        if(iCommand==0 && listBenefitDeduction!=null){ 
         String order = " e." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] ;
         int nilai=1;
         
         if (listBenefitDeduction.getSourceTYpe() == 0){
             nilai = 1;
         } else if (listBenefitDeduction.getSourceTYpe() == 1){
             nilai = 2;
         }
             
        // String wherepaycomponent = PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS] + " = " + 1 +" AND " +PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + " = " + nilai;
       String wherepaycomponent = PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS] + " > " + 0 ;
        
         vPayComponent = PstPayComponent.list(0, 0, wherepaycomponent, PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID]  +" ASC "); 
                         
        EmployeeSrcRekapitulasiAbs employeeSrcRekapitulasiAbs = PstEmployee.getEmployeeFilter(0, 0, where, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
        hashSchOff = PstScheduleSymbol.getHashScheduleIdOFF(PstScheduleCategory.CATEGORY_OFF);
        vctSchIDOff = PstScheduleSymbol.getHashScheduleId(PstScheduleCategory.CATEGORY_OFF); 
        String sectionId = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.getSectionIdByEmpId(0, 0, (employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" IN("+employeeSrcRekapitulasiAbs.getEmpId()+")":""), PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]):"";
        holidaysTable = PstPublicHolidays.getHolidaysTable(dtfrom, dtend); 
        hashPeriod = PstPeriod.hashlistTblPeriod(0, 0, whereClausePeriod, "");
        hashPositionLevel = PstPosition.hashGetPositionLevel();
        listCompany = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?     PstCompany.list(0, 0, (employeeSrcRekapitulasiAbs.getCompanyId()!=null && employeeSrcRekapitulasiAbs.getCompanyId().length()>0 ?PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+" IN("+employeeSrcRekapitulasiAbs.getCompanyId()+")":""), PstCompany.fieldNames[PstCompany.FLD_COMPANY])  :new Vector(); 
        hashDivision = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0? PstDivision.hashListDivision(0, 0, (employeeSrcRekapitulasiAbs.getDivisionId()!=null && employeeSrcRekapitulasiAbs.getDivisionId().length()>0?PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" IN("+employeeSrcRekapitulasiAbs.getDivisionId()+")":"")):new Hashtable();   
        hashDepartment = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstDepartment.hashListDepartment(0, 0, (employeeSrcRekapitulasiAbs.getDepartmentId()!=null && employeeSrcRekapitulasiAbs.getDepartmentId().length()>0?PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" IN("+employeeSrcRekapitulasiAbs.getDepartmentId()+")":"")):new Hashtable(); 
        hashSection = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstSection.hashListSection(0, 0, (sectionId!=null && sectionId.length()>0?PstSection.fieldNames[PstSection.FLD_SECTION_ID]+" IN("+sectionId+")":"")):new Hashtable();     
        hashEmployee = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.hashListEmployee(0, 0, listBenefitDeduction.whereClauseEmpId(employeeSrcRekapitulasiAbs.getEmpId())):new Hashtable(); 
            
        Listdatanew = PstEmployee.listBenefitDeduction(0, 0, null, order, listBenefitDeduction.getPeriodeId(), listBenefitDeduction.getSourceTYpe(), vPayComponent,listBenefitDeduction, employeeSrcRekapitulasiAbs.getEmpId() );

        listAttdAbsensi =  employeeSrcRekapitulasiAbs.getEmpId()!=null &&  employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmpSchedule.getListAttendaceRekap(attdConfig,leaveConfig,dtfrom, dtend, employeeSrcRekapitulasiAbs.getEmpId(), vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod):new Hashtable();  
        vReason = PstReason.listReason(0, 0, PstReason.fieldNames[PstReason.FLD_REASON_TIME] + "=" + PstReason.REASON_TIME_YES, PstReason.fieldNames[PstReason.FLD_REASON_CODE]+ " ASC "); 
            
    }
%>


<%@page contentType="application/x-msexcel" pageEncoding="UTF-8" %>

<html> 
    <head>
        <title>HARISMA - Rekapitulasi Absensi</title>
    </head>

    <body >
       
        <table>
           <%if (iCommand == 0) {%> 
                            <tr>
                                
                                <td>
                                    <table>
                                      
                                        <tr>
                                            <td style="font-size: large"><b><%=judul%></b></td>
                                        </tr>
                                        <tr>
                                            <td style="font-size: large"><b><%=dtfrom != null && dtend !=null? Formater.formatDate(dtfrom,"dd MMM yyyy")+" s/d " + Formater.formatDate(dtend,"dd MMM yyyy"):"-"%></b></td>
                                        </tr>
                                        <tr>
                                            <td style="font-size: large"><b></b></td>
                                        </tr>
                                      
                                        <tr>
                                            <%
                                                                                                if(listBenefitDeduction.getSourceTYpe()==0){%>
                                                                                                    <%=drawList(out, Listdatanew, vPayComponent, listBenefitDeduction.getSourceTYpe(), listAttdAbsensi,vReason)%>
                                                                                              <%
                                                                                                
                                                                                                }else{%>
                                                                                                    <%=drawList2(out, Listdatanew, vPayComponent, listBenefitDeduction.getSourceTYpe(), listAttdAbsensi,vReason)%>
                                                                                               <%}
                                                                                                %>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                         <tr>
                                            <td><%=drawListTdd(out,source_type)%></td> 
                                        </tr>
                                        
                                         <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                       
                                    </table>
                                        
                                </td>  
                            </tr>

                            <%}%>
        </table>
   
    </body>

</html>