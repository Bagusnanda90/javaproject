/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstReason;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.payroll.PayEmpLevel;
import com.dimata.harisma.entity.payroll.PayInput;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PaySlip;
import com.dimata.harisma.entity.payroll.PaySlipComp;
import com.dimata.harisma.entity.payroll.PstOvt_Idx;
import com.dimata.harisma.entity.payroll.PstOvt_Type;
import com.dimata.harisma.entity.payroll.PstPayComponent;
import com.dimata.harisma.entity.payroll.PstPayEmpLevel;
import com.dimata.harisma.entity.payroll.PstPayInput;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.entity.payroll.PstPaySlipComp;
import com.dimata.harisma.entity.payroll.PstSalaryLevelDetail;
import com.dimata.harisma.entity.payroll.SalaryLevel;
import com.dimata.harisma.entity.payroll.SalaryLevelDetail;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.system.entity.system.SystemProperty;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author ktanjana
 */
public class PayProcess implements Runnable {

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param aMessage the message to set
     */
    public void setMessage(String aMessage) {
        message = aMessage;
    }
    private String sumMessage = "";
    private boolean running = false;
    private long sleepMs = 100;
    private String message = "";
    private int recordSize = 0;
    private int progressSize = 0;
    private long payGroupId=0;// update by Kartika 24 July 2015
    private String levelCode = "";
    private PayPeriod prevPeriod = null;
    //update by satrya 2013-02-12
    // private Period prevPeriod = null;
    private PayPeriod payPeriod = new PayPeriod();
    // private Period period = new Period();
    //update by satrya 2013-02-12
    private long periodId = 0;
    private long oidDivision = 0;
    private long oidDepartment = 0;
    private Department department;
    private Division division;
    private Vector levelSel = null;
    private int limitList = 1000;

    // by Kartika 2015-06-30
    private Date rsgnStartdate = null;
    private Date rsgnEnddate = null;
    private Date minCommencing = null;
    private long payrollGroupId=0;
    public PayProcess(long periodId, String levelCode,long oidDivision, long oidDepartment, Vector levelSel, long payGroupIdX, long payrollGroupId) {
        this.periodId = periodId;
        this.levelCode = levelCode;
        this.payGroupId=payGroupIdX;
        this.payrollGroupId=payrollGroupId;
        if (periodId != 0) {
            try {
                payPeriod = PstPayPeriod.fetchExc(periodId);
                //update by satrya 2013-02-12
                //period = PstPeriod.fetchExc(periodId);
                prevPeriod = PstPayPeriod.getPreviousPeriod(periodId);
            } catch (Exception e) {
            }
        }
        if (oidDivision != 0) {
            try {
                division = PstDivision.fetchExc(oidDivision);
            } catch (Exception exc){
                
            }
        } else{
            division = new Division();
        }
        this.oidDivision = oidDivision;
        if (oidDepartment != 0) {
            try {
                department = PstDepartment.fetchExc(oidDepartment);
            } catch (Exception exc) {

            }
        } else {
            department = new Department();
        }
        this.oidDepartment = oidDepartment;
        this.levelSel = levelSel;
    }

    public void setPayProcess(long periodId, String levelCode,long oidDivision, long oidDepartment, Vector levelSel, long payGroupIdX, long payrollGroupId) {
        this.periodId = periodId;
        this.levelCode = levelCode;
        this.payGroupId = payGroupIdX;
        this.payrollGroupId = payrollGroupId;
        if (periodId != 0) {
            try {
                payPeriod = PstPayPeriod.fetchExc(periodId);
                //  period = PstPeriod.fetchExc(periodId);
            } catch (Exception e) {
            }
        }
        if (oidDivision != 0) {
            try {
                division = PstDivision.fetchExc(oidDivision);
            } catch (Exception exc) {
                
            }
        } else {
            division = new Division();
        }
        this.oidDivision = oidDivision;
        if (oidDepartment != 0) {
            try {
                department = PstDepartment.fetchExc(oidDepartment);
            } catch (Exception exc) {

            }
        } else {
            department = new Department();
        }
        this.oidDepartment = oidDepartment;
        this.levelSel = levelSel;
    }

    public void run() {
        message = "Payroll process is running now";
        String procentasePresence = String.valueOf(PstSystemProperty.getValueByName("PROCENTASE_PRESENCE"));
        String strAbsent = String.valueOf(PstSystemProperty.getValueByName("DAY_ABSENT"));
        String salaryEmp = String.valueOf(PstSystemProperty.getValueByName("SALARY_FOR_OVT"));
        String tot_idx = PstPaySlip.PAY_COMP_OVERTIME_IDX;//String.valueOf(PstSystemProperty.getValueByName("TOTAL_IDX_OVT"));
        String tot_ovt = String.valueOf(PstSystemProperty.getValueByName("OVT_DURATION"));
        String strPresence = String.valueOf(PstSystemProperty.getValueByName("DAY_PRESENT"));
        String strDayLate = String.valueOf(PstSystemProperty.getValueByName("DAY_LATE"));
        String strMinOvtDur = String.valueOf(PstSystemProperty.getValueByName("MIN_OVERTM_DURATION"));
        double minOvtDuration = 0;

        int maxDateOfMonth = 31;
        if (periodId != 0) {
            try {
                payPeriod = PstPayPeriod.fetchExc(periodId);
                prevPeriod = PstPayPeriod.getPreviousPeriod(periodId);
            } catch (Exception e) {
            }
        } else {
            payPeriod = new PayPeriod();
        }
        if (payPeriod == null) {
            message = "Period is not set ";
            return;
        }
        try {
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.setTime(payPeriod.getStartDate());
            maxDateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception exc1) {
            System.out.println(exc1);
        }

        Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
        //Vector vAbsenReason = PstReason.list(0, 1000, "", "");        

        message = "Getting employee level list";
        Vector listEmpLevel = new Vector(1, 1);
     //   listEmpLevel = PstPayEmpLevel.listEmpLevel(levelCode, getLevelSel(), oidDepartment);

        listEmpLevel = PstPayEmpLevel.listEmpLevelwithDateResign(levelCode, getLevelSel(), oidDivision, oidDepartment, minCommencing, PstEmployee.NO_RESIGN, payPeriod.getStartDate(), payPeriod.getEndDate(),rsgnStartdate, rsgnEnddate,payrollGroupId);
        // listEmpLevel = PstPayEmpLevel.listEmpLevel(levelCode, getLevelSel(), oidDepartment );            
        try {
            minOvtDuration = Double.parseDouble(strMinOvtDur);
        } catch (Exception exc) {
            System.out.println(exc);
        }

        this.setRunning(true);
        {
            try {
                Thread.sleep(this.getSleepMs() * 100);

                //ambil nilai index working Day
                double idxMaksWD = PstOvt_Idx.getMaxOvtIdx(PstOvt_Type.WORKING_DAY);
                double idxMinWD = PstOvt_Idx.getMinOvtIdx(PstOvt_Type.WORKING_DAY);

                String strForDebug = "";  // dummy variable for debungging puposed
                String strForDebug2 = "";
                long longForDebug = 0;
                PaySlipComp paySlipComp = null;
                // listEmpLevel = PstPayEmpLevel.listEmpLevel(levelCode, oidDepartment);

                Vector<String> vSalComponents = new Vector();
                if (listEmpLevel != null && listEmpLevel.size() > 0) {
                    vSalComponents = PstPayComponent.listCompString(0, 1000, "", "");
                }

                String parollCalculatorClassName = "";
                I_PayrollCalculator payrollCalculator = null;
                try {
                    parollCalculatorClassName = PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME");
                    if (parollCalculatorClassName == null || parollCalculatorClassName.length() < 1) {
                        parollCalculatorClassName = "com.dimata.harisma.session.payroll.PayrollCalculator";
                    }
                    payrollCalculator = (I_PayrollCalculator) (Class.forName(parollCalculatorClassName).newInstance());
                    if (payPeriod != null) {
                        payrollCalculator.initializedPreloadedData(payPeriod.getStartDate(), payPeriod.getEndDate());
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
                if (payrollCalculator == null) {
                    try {
                        parollCalculatorClassName = "com.dimata.harisma.session.payroll.PayrollCalculator";
                        payrollCalculator = (I_PayrollCalculator) (Class.forName(parollCalculatorClassName).newInstance());
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
                }
                //update by devin 2014-02-15
                Pajak pajak = new Pajak();
                try {
                    double ptkpDiriSendiri = 0;
                    double ptkpKawin = 0;
                    double ptkpAnak1 = 0;
                    double ptkpAnak2 = 0;
                    double ptkpAnak3 = 0;
                    double jbtanMax = 0;
                    double jabatanPersen = 0;
                    double nettoPercenKonsultan = 0;
                    double percen1 = 0;
                    double percen2 = 0;
                    double percen3 = 0;
                    double percen4 = 0;
                    double range1 = 0;
                    double range2 = 0;
                    double range3 = 0;
                    try {
                        ptkpDiriSendiri = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_PTKP_DIRI_SENDIRI").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_PTKP_DIRI_SENDIRI"));
                        ptkpKawin = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_PTKP_KAWIN").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_PTKP_KAWIN"));
                        ptkpAnak1 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_PTKP_KAWIN_ANAK_1").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_PTKP_KAWIN_ANAK_1"));
                        ptkpAnak2 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_PTKP_KAWIN_ANAK_2").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_PTKP_KAWIN_ANAK_2"));
                        ptkpAnak3 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_PTKP_KAWIN_ANAK_3").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_PTKP_KAWIN_ANAK_3"));
                        jbtanMax = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_BIAYA_JABATAN_MAX").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_BIAYA_JABATAN_MAX"));
                        jabatanPersen = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_BIAYA_JABATAN_PERSEN").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_BIAYA_JABATAN_PERSEN"));
                        nettoPercenKonsultan = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_NETTO_PERCENT_CONSULTANT").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_NETTO_PERCENT_CONSULTANT"));
                        percen1 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_percen_1").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_percen_1"));
                        percen2 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_percen_2").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_percen_2"));
                        percen3 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_percen_3").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_percen_3"));
                        percen4 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_percen_4").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_percen_4"));
                        range1 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_range_1").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_range_1"));
                        range2 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_range_2").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_range_2"));
                        range3 = Double.parseDouble(PstSystemProperty.getValueByName("PAYROLL_range_3").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? "0" : PstSystemProperty.getValueByName("PAYROLL_range_3"));
                    } catch (Exception exc) {

                    }
                    pajak.setPtkpDiriSendiri(ptkpDiriSendiri);
                    pajak.setPtkpKawin(ptkpKawin);
                    pajak.setPtkpKawinAnak1(ptkpAnak1);
                    pajak.setPtkpKawinAnak2(ptkpAnak2);
                    pajak.setPtkpKawinAnak3(ptkpAnak3);
                    pajak.setBiayaJabatanMaksimal(jbtanMax);
                    pajak.setBiayaJabatanPersen(jabatanPersen);
                    pajak.setNettoPercenKonsultan(nettoPercenKonsultan);
                    pajak.setPercen1(percen1);
                    pajak.setPercen2(percen2);
                    pajak.setPercen3(percen3);
                    pajak.setPercen4(percen4);
                    pajak.setRange1(range1);
                    pajak.setRange2(range2);
                    pajak.setRange3(range3);

                } catch (Exception exc) {

                }
                int listSize = listEmpLevel.size();
                Vector listPeriodDate = PstPeriod.getListStartEndDatePeriod(payPeriod.getStartDate(), payPeriod.getEndDate());
                Hashtable listPayInputTbl = PstPayInput.hashListPayInput(0, 0, PstPayInput.fieldNames[PstPayInput.FLD_PERIODE_ID] + "=" + payPeriod.getOID(), "");
                Vector listPosition = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT] + "=" + PstPosition.YES_SHOW_PAY_INPUT, PstPosition.fieldNames[PstPosition.FLD_POSITION] + " ASC ");
                Vector listReason = PstReason.list(0, 0, PstReason.fieldNames[PstReason.FLD_FLAG_IN_PAY_INPUT] + "=" + PstReason.SHOW_REASON_IN_PAY_INPUT_YES, PstReason.fieldNames[PstReason.FLD_REASON] + " ASC ");
                // x
                for (int p = 0; p < listEmpLevel.size(); p++) {

                    Vector temp = (Vector) listEmpLevel.get(p);
                    Employee emp = (Employee) temp.get(0);
                    PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(1);
                    SalaryLevel salary = (SalaryLevel) temp.get(2);
                    long empId = emp.getOID();

                    message = " " + emp.getEmployeeNum() + " / " + emp.getFullName() + " Level : " + payEmpLevel.getLevelCode();
                    setSumMessage(sumMessage + "<br>" + (p + 1) + " " + message);
                    message = " Process employee " + (((p + 1) * 100) / listSize) + "% at " + p + " of " + listSize + " : " + message;
                    try {
                        Vector bnf = PstSalaryLevelDetail.listComponent(0, 1000, payEmpLevel.getLevelCode(), PstPayComponent.TYPE_BENEFIT, 
                             " AND " + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA] + " NOT LIKE '%IN\\_%'" + (this.payGroupId!=0 ?(" AND "+PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID]+"="+this.payGroupId):"" ) );
                        //Vector deduc= PstSalaryLevelDetail.listComponent(0, 1000 , payEmpLevel.getLevelCode() , PstPayComponent.TYPE_DEDUCTION, " AND " + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA] + " NOT LIKE '%IN\\_%'");
                        Vector deduc = PstSalaryLevelDetail.listComponentVer1(0, 1000, payEmpLevel.getLevelCode(), PstPayComponent.TYPE_DEDUCTION, 
                             " AND " + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA] + " NOT LIKE '%IN\\_%'" + (this.payGroupId!=0 ?(" AND "+PstPayComponent.fieldNames[PstPayComponent.FLD_PAYSLIP_GROUP_ID]+"="+this.payGroupId):"" ), empId);
                        // pengambilan formula untuk diproses
                    /* String where = PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE] + "='" + payEmpLevel.getLevelCode() + "'" +
                         " AND " + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA] + " NOT LIKE '%IN_%'";
                         //" AND "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]+"="+PstSalaryLevelDetail.YES_TAKE;
                         Vector component = PstSalaryLevelDetail.list(0, 0, where, "");
                         //System.out.println("component"+component.size());
                         * */
                        Vector component = bnf;
                        component.addAll(deduc);

                        //if (empId == longForDebug) {
                        //  System.out.println("POINT to Debug");
                        //}
                        //ambli paySlipId dari employee yang bersangkutan pada periode ini
                        long paySlipId = PstPaySlip.getPaySlipId(getPayPeriod().getOID(), empId);
                        //di sini mencari empSchedulenya berdasarkan periode'nya
                        //EmpSchedule empSch = PstEmpSchedule.fetch(getPayPeriod().getOID(), empId);
                        if (paySlipId == 0) {
                            message = "No payroll slip found for " + emp.getEmployeeNum() + " / " + emp.getFullName() + ". Please do prepare data first";
                            sumMessage = sumMessage + " / " + message;
                            continue;
                        }
                        PaySlip paySlip = null;
                        PayInput payInput = null;
                        try {
                            paySlip = PstPaySlip.fetchExc(paySlipId);
                            if (listPayInputTbl != null && listPayInputTbl.size() > 0) {
                                payInput = new PayInput();
                                payInput.setEmployeeId(paySlip.getEmployeeId());
                                payInput.setPeriodId(paySlip.getPeriodId());
                                payInput.setPositionId(emp.getPositionId());
                                payInput.setPaySlipId(paySlip.getOID());
                                PstPayInput.resultToObject(payInput, listPayInputTbl, listReason, listPosition);
                            }
                        } catch (Exception e) {
                            System.out.println("Exc" + e);
                        }

                        //menghitung per komponen per employee
                        for (int c = 0; c < component.size(); c++) {
                            try {
                                if (!this.running) {
                                    break;
                                }
                                Vector vectToken = new Vector(1, 1);
                                SalaryLevelDetail salaryComp = (SalaryLevelDetail) component.get(c);
                                String compCode = salaryComp.getCompCode();
                                paySlipComp = new PaySlipComp();

                                String formula = salaryComp.getFormula();
                                formula = formula + " * 1 ";
                                StringTokenizer tokenSpace = new StringTokenizer(formula, "");
                                while (tokenSpace.hasMoreTokens()) {
                                    String compToken = (String) tokenSpace.nextToken();
                                    StringTokenizer token = new StringTokenizer(formula, "=");
                                    while (token.hasMoreTokens()) {
                                        compToken = (String) token.nextToken();
                                        StringTokenizer tokenFormula2 = new StringTokenizer(compToken, "(");
                                        while (tokenFormula2.hasMoreTokens()) {
                                            compToken = (String) tokenFormula2.nextToken();
                                            StringTokenizer tokenFormula3 = new StringTokenizer(compToken, ")");
                                            while (tokenFormula3.hasMoreTokens()) {
                                                compToken = (String) tokenFormula3.nextToken();
                                                StringTokenizer tokenFormula4 = new StringTokenizer(compToken, "*");
                                                while (tokenFormula4.hasMoreTokens()) {
                                                    compToken = (String) tokenFormula4.nextToken();
                                                    StringTokenizer tokenFormula5 = new StringTokenizer(compToken, "/");
                                                    while (tokenFormula5.hasMoreTokens()) {
                                                        compToken = (String) tokenFormula5.nextToken();
                                                        StringTokenizer tokenFormula6 = new StringTokenizer(compToken, "+");
                                                        while (tokenFormula6.hasMoreTokens()) {
                                                            compToken = (String) tokenFormula6.nextToken();
                                                            StringTokenizer tokenFormula = new StringTokenizer(compToken, "-");
                                                            while (tokenFormula.hasMoreTokens()) {
                                                                compToken = (String) tokenFormula.nextToken();
                                                                vectToken.add(compToken.toUpperCase());
                                                                //System.out.println("vectToken "+vectToken);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                //System.out.println("vectTokenSize.........."+vectToken.size());
                                // jika nilainya berupa konstanta
                                if (vectToken != null && vectToken.size() == 1 && PstPaySlip.checkComponentFormula(vectToken) < 0) {
                                    String strCek = (String) vectToken.get(0);
                                    //String cekSub = strCek.substring(1, 2);
                                    double compValue = PstPaySlipComp.getCompValueEmployee(empId, getPayPeriod().getOID(), strCek);
                                    boolean isNumber = false;
                                    Double theNumber = 0.0d;
                                    if (Math.abs(compValue) <= 0.0d) { // jika tidak ketemu di component lain, maka check angka atau tidak
                                        try {
                                            theNumber = Double.parseDouble(strCek);
                                            isNumber = true;
                                        } catch (Exception exc) {
                                            sumMessage = sumMessage + " " + compCode + " is blank or not well formulated; ";
                                        }
                                    }
                                    //if (cekSub.equals("0") || cekSub.equals("1") || cekSub.equals("2") || cekSub.equals("3") || cekSub.equals("4") || cekSub.equals("5") || cekSub.equals("6") || cekSub.equals("7") || cekSub.equals("8") || cekSub.equals("9")) {
                                    if (isNumber) {
                                        // langsung insert ke tabel pay slip comp untuk dipake komponen lain yang akan mengunakan nilai ini
                                        paySlipComp.setPaySlipId(paySlipId);
                                        paySlipComp.setCompCode(compCode);
                                        paySlipComp.setCompValue(theNumber);

                                        String whereSlip = PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] + "=" + paySlipId
                                                + " AND " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + " = '" + compCode.trim() + "'";
                                        Vector vectSlipComp = PstPaySlipComp.list(0, 0, whereSlip, "");
                                        try {
                                            if (vectSlipComp.size() == 0) {
                                                PstPaySlipComp.insertExc(paySlipComp);
                                            } else if (vectSlipComp.size() > 0) {
                                                PstPaySlipComp.updateValueComp(Double.parseDouble(strCek), whereSlip);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("ERR" + e.toString());
                                        }
                                    } else {
                                        // jika nilai bukan berupa rumus tapi berupa variabel dari komponen lain,maka diambil nilainya
                                        //int compValue = PstPaySlipComp.getCompValueEmployee(empId, getPayPeriod().getOID(), strCek);
                                        paySlipComp.setPaySlipId(paySlipId);
                                        paySlipComp.setCompCode(compCode);
                                        paySlipComp.setCompValue(compValue);
                                        String whereSlip = PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] + "=" + paySlipId
                                                + " AND " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + " = '" + compCode + "'";
                                        Vector vectSlipComp = PstPaySlipComp.list(0, 0, whereSlip, "");
                                        try {
                                            if (vectSlipComp.size() == 0) {
                                                PstPaySlipComp.insertExc(paySlipComp);
                                            } else if (vectSlipComp.size() > 0) {
                                                PstPaySlipComp.updateValueComp(compValue, whereSlip);
                                            } else {
                                                //System.out.println("compValue Formula............................." + compValue);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("ERR" + e.toString());
                                        }

                                    }
                                } //jika formulanya berupa rumus
                                else {
                           //         EmpSchedule empSch = PstEmpSchedule.getEmpSchedule(empId, period.getOID(), diffDay, dtPeriodNew);

                                    //update by priska 2015-03-05
                                    message = PstPaySlip.calculatePaySlipComponent(
                                            getPayPeriod(), getPrevPeriod(), emp, paySlip, salaryComp,
                                            //empSch,
                                            vctSchIDOff,
                                            maxDateOfMonth,
                                            procentasePresence, strAbsent, salaryEmp, tot_idx, tot_ovt,
                                            strPresence, strDayLate, minOvtDuration, vSalComponents, payrollCalculator, listPeriodDate, pajak, payInput, listReason, listPosition, payPeriod.getStartDate(), payPeriod.getEndDate());
                                    setSumMessage(sumMessage + " / " + message);

                                }
                            } catch (Exception excComp) {
                                System.out.println(" Componen Exc - count " + c + " > " + excComp);
                            }
                        }
                    } catch (Exception exc) {
                        System.out.println(" " + exc);
                    }

                }
            } catch (Exception exc) {
                System.out.println(exc);

            }
        }
        this.running = false;
        message = "Payroll calculation done";
    }

    /**
     * @return the recordSize
     */
    public int getRecordSize() {
        return recordSize;
    }

    /**
     * @return the progressSize
     */
    public int getProgressSize() {
        return progressSize;
    }

    /**
     * @return the levelCode
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * @return the period.getOID()
     */
    public PayPeriod getPayPeriod() {
        return payPeriod;
    }

    /**
     * @return the oidDepartment
     */
    public long getOidDepartment() {
        return oidDepartment;
    }

    /**
     * @param oidDepartment the oidDepartment to set
     */
    public void setOidDepartment(long oidDepartment) {
        this.oidDepartment = oidDepartment;
    }

    /**
     * @return the levelSel
     */
    public Vector getLevelSel() {
        return levelSel;
    }

    /**
     * @param levelSel the levelSel to set
     */
    public void setLevelSel(Vector levelSel) {
        this.levelSel = levelSel;
    }

    /**
     * @return the limitList
     */
    public int getLimitList() {
        return limitList;
    }

    /**
     * @param limitList the limitList to set
     */
    public void setLimitList(int limitList) {
        this.limitList = limitList;
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return the sleepMs
     */
    public long getSleepMs() {
        return sleepMs;
    }

    /**
     * @param sleepMs the sleepMs to set
     */
    public void setSleepMs(long sleepMs) {
        this.sleepMs = sleepMs;
    }

    /**
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @return the sumMessage
     */
    public String getSumMessage() {
        return sumMessage;
    }

    /**
     * @param sumMessage the sumMessage to set
     */
    public void setSumMessage(String sumMessage) {
        this.sumMessage = sumMessage;
    }

    /**
     * @return the prevPeriod
     */
    public PayPeriod getPrevPeriod() {
        return prevPeriod;
    }

    /**
     * @param prevPeriod the prevPeriod to set
     */
    private void setPrevPeriod(PayPeriod prevPeriod) {
        // private void setPrevPeriod(Period prevPeriod) {
        this.prevPeriod = prevPeriod;
    }

    /**
     * @return the rsgnStartdate
     */
    public Date getRsgnStartdate() {
        return rsgnStartdate;
    }

    /**
     * @param rsgnStartdate the rsgnStartdate to set
     */
    public void setRsgnStartdate(Date rsgnStartdate) {
        this.rsgnStartdate = rsgnStartdate;
    }

    /**
     * @return the rsgnEnddate
     */
    public Date getRsgnEnddate() {
        return rsgnEnddate;
    }

    /**
     * @param rsgnEnddate the rsgnEnddate to set
     */
    public void setRsgnEnddate(Date rsgnEnddate) {
        this.rsgnEnddate = rsgnEnddate;
    }

    /**
     * @return the minCommencing
     */
    public Date getMinCommencing() {
        return minCommencing;
    }

    /**
     * @param minCommencing the minCommencing to set
     */
    public void setMinCommencing(Date minCommencing) {
        this.minCommencing = minCommencing;
    }

    /**
     * @return the payGroupId
     */
    public long getPayGroupId() {
        return payGroupId;
}

    /**
     * @param payGroupId the payGroupId to set
     */
    public void setPayGroupId(long payGroupId) {
        this.payGroupId = payGroupId;
    }

    /**
     * @return the oidDivision
     */
    public Division getDivision() {
        return division;
    }
    /**
     * @return the oidDepartment
     */
    public long getOidDivision() {
        return oidDivision;
    }

    /**
     * @param oidDivision the oidDepartment to set
     */
    public void setOidDivision(long oidDivision) {
        this.oidDivision = oidDivision;
    }

}
