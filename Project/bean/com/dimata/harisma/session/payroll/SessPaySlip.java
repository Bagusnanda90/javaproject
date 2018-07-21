/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import java.util.Vector;
import java.text.*;
import java.util.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.session.employee.SessEmployee;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.system.entity.system.*;
import java.sql.*;
import com.dimata.qdep.db.*;

/**
 *
 * @author Ketut Kartika T
 */
public class SessPaySlip {

    public static String generatePaySlip(long periodId, String payLevelCode) {
        return generatePaySlip(periodId, payLevelCode, 0,0, 0, 0, "", "", "", 0);
    }
    
  public static String generatePaySlip(long periodId, String payLevelCode,long companyId, long divisionId, long departmentId, long sectionId, String searchNrFrom, String searchNrTo, String searchName, int dataStatus)
          //update by satrya 2014-02-03 public static String generatePaySlip(long periodId, String payLevelCode, long divisionId, long departmentId, long sectionId, String searchNrFrom, String searchNrTo, String searchName, int dataStatus)
    {
        if (periodId != 0) {
            try {

                PayPeriod payPeriod = PstPayPeriod.fetchExc(periodId);
                //Period period = PstPeriod.fetchExc(periodId);
                //System.out.println("periodId atas..."+period.getOID());
                // insert ke payslip

                if( (payPeriod == null ) || (payPeriod.getPaySlipDate()==null)){
                    return "Period setting is not complete, Pay Slip Date has to be set !";
                }
                
                Vector vectEmp = SessEmployee.getEmpPaySlip(periodId,  payLevelCode,companyId, divisionId, departmentId, sectionId, 
                     searchNrFrom, searchNrTo, searchName);
                
                if (vectEmp != null && vectEmp.size() > 0) {
                    try {
                        PayGeneral payGen = new PayGeneral();
                        for (int i = 0; i < vectEmp.size(); i++) {
                            Vector temp = (Vector) vectEmp.get(i);
                            Employee emp = (Employee) temp.get(0);
                            Division division = (Division) temp.get(1);
                            Department department = (Department) temp.get(2);
                            Position position = (Position) temp.get(3);
                            Section section = (Section) temp.get(4);
                            PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(5);

                            long lPslip = PstPaySlip.getPaySlipId(payPeriod.getOID(), emp.getOID());

                            if (lPslip == 0) {
                                
                                PaySlip paySlip = new PaySlip();
                                paySlip.setPeriodId(payPeriod.getOID());
                                paySlip.setEmployeeId(emp.getOID());
                                paySlip.setStatus(0);
                                paySlip.setPaidStatus(0);
                                if(payGen.getOID()==0 ||payGen.getOID()!=emp.getOID() ){
                                    String whereGen = ""+PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID]+"="+emp.getCompanyId();
                                    Vector vectCity = PstPayGeneral.list(0,0,whereGen,"");                                                           
                                    if(vectCity!=null && vectCity.size() > 0){
                                            payGen = (PayGeneral) vectCity.get(0);                                                                    
                                    }                                      
                                }
                                paySlip.setPaySlipDate(payPeriod.getPaySlipDate());


                                paySlip.setDayPresent(0);
                                paySlip.setDayPaidLv(0);
                                paySlip.setDayAbsent(0);
                                paySlip.setDayUnpaidLv(0);
                                paySlip.setDivision(division.getDivision());
                                paySlip.setDepartment(department.getDepartment());
                                paySlip.setPosition(position.getPosition());
                                paySlip.setSection(section.getSection());
                                paySlip.setNote("");
                                paySlip.setCommencDate(emp.getCommencingDate());
                                paySlip.setPaySlipType(0);
                                paySlip.setCompCode(""+payGen.getCompanyName());
                                paySlip.setDayLate(0);

                                try {
                                    PstPaySlip.insertExc(paySlip);
                                } catch (Exception exc1) {

                                    System.out.println(exc1);

                                }
                            }
                        //paySlip.set
                        //System.out.println("emp id..."+emp.getOID());


                        }
                    } catch (Exception exc) {
                        System.out.println("Err CtlPeriod.COMMAND.SAVE");
                    }

                }


            //samapi sisni......................
            } catch (Exception exc) {
                String msgString = exc.getMessage();
                System.out.println(msgString);
                return msgString;
            }

        }
        return "OK";
    }

    public static Vector calcSalaryCompPeriod(String formula, long empId, long periodId) {
        SalaryCompResult compRslt = new SalaryCompResult();

        EmpSchedule empSch = PstEmpSchedule.fetch(periodId, empId);
        Vector vRslt = new Vector();

        PayPeriod selPeriod = new PayPeriod();
        // Period selPeriod = new Period();
        int maxDateOfMonth = 31;
        if (periodId != 0) {
            try {
                selPeriod = PstPayPeriod.fetchExc(periodId);
                //selPeriod = PstPeriod.fetchExc(periodId);
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.setTime(selPeriod.getStartDate());
                maxDateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            } catch (Exception e) {
            }
        } else {
            return vRslt;
        }

        if (formula == null) {
            System.out.println("calcSalaryComp : salary Formula is null ");
            compRslt.ScheduleNote = "";
            compRslt.compValue = 0;
            compRslt.systemNote = "calcSalaryComp : salary Formula is null ";
            vRslt.add(compRslt);
            return vRslt;
        }

        /*
        String strStartDate = String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD"));
        int iStDatePeriod = 1;
        try {
        iStDatePeriod = Integer.parseInt(strStartDate);
        } catch (Exception exc) {
        }*/

        String procentasePresence = String.valueOf(PstSystemProperty.getValueByName("PROCENTASE_PRESENCE"));
        String strAbsent = String.valueOf(PstSystemProperty.getValueByName("DAY_ABSENT"));
        String salaryEmp = String.valueOf(PstSystemProperty.getValueByName("SALARY_FOR_OVT"));
        String tot_idx = String.valueOf(PstSystemProperty.getValueByName("TOTAL_IDX_OVT"));
        String tot_ovt = String.valueOf(PstSystemProperty.getValueByName("OVT_DURATION"));
        String strPresence = String.valueOf(PstSystemProperty.getValueByName("DAY_PRESENT"));
        String strDayLate = String.valueOf(PstSystemProperty.getValueByName("DAY_LATE"));
        String strMinOvtDur = String.valueOf(PstSystemProperty.getValueByName("MIN_OVERTM_DURATION"));
        double minOvtDuration = 0;
        try {
            minOvtDuration = Double.parseDouble(strMinOvtDur);
        } catch (Exception exc) {
        }

        Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);

        //ambil nilai index working Day
        double idxMaksWD = PstOvt_Idx.getMaxOvtIdx(PstOvt_Type.WORKING_DAY);
        double idxMinWD = PstOvt_Idx.getMinOvtIdx(PstOvt_Type.WORKING_DAY);

        Vector vectToken = new Vector(1, 1);

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
                                        vectToken.add(compToken);
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
        if (vectToken.size() == 1) {
            String strCek = (String) vectToken.get(0);
            String cekSub = strCek.substring(1, 2);
            if (cekSub.equals("0") || cekSub.equals("1") || cekSub.equals("2") || cekSub.equals("3") || cekSub.equals("4") || cekSub.equals("5") || cekSub.equals("6") || cekSub.equals("7") || cekSub.equals("8") || cekSub.equals("9")) {
                compRslt.compValue = Double.parseDouble(strCek);
                vRslt.add(compRslt);
                return vRslt;
            } else {
                // jika nilai bukan berupa rumus tapi berupa variabel dari komponen lain,maka diambil nilainya
                compRslt.compValue = PstPaySlipComp.getCompValueEmployee(empId, periodId, strCek);
                vRslt.add(compRslt);
                return vRslt;
            }
        } //jika formulanya berupa rumus
        else {
            Vector vectComp = new Vector(1, 1);
            StringTokenizer tokenComp = new StringTokenizer(formula);
            while (tokenComp.hasMoreTokens()) {
                String compToken = (String) tokenComp.nextToken();
                vectComp.add(compToken);
            }

            // looping dalam satu period jika component gaji berupa formula
            int iStDatePeriod = selPeriod.getStartDate().getDate();
            int iCurrDate = iStDatePeriod;
            PaySlip paySlip = new PaySlip();
            do {
                String formSQL = "";
                compRslt = new SalaryCompResult();
                int stData = (int) empSch.getStatusData(iCurrDate);
                int absReason = (int) empSch.getReason(iCurrDate);
                switch (stData) {
                    case PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED:
                        break;
                    case PstEmpSchedule.STATUS_PRESENCE_OK:
                        if (empSch.getPresentDurationMilSeconds(iCurrDate) > 0) {
                            paySlip.setDayAbsent(0);
                            paySlip.setDayLate(0);
                            paySlip.setDayPresent(1);
                            paySlip.setProcentasePresence(100);
                            compRslt.ScheduleNote = "V";
                        } else {
                            stData = PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED;
                            paySlip.setDayAbsent(0);
                            paySlip.setDayLate(0);
                            paySlip.setDayPresent(0);
                            paySlip.setProcentasePresence(0);
                            compRslt.ScheduleNote = "-";
                        }
                        break;
                    case PstEmpSchedule.STATUS_PRESENCE_LATE:
                        paySlip.setDayAbsent(0);
                        paySlip.setDayLate(1);
                        paySlip.setDayPresent(1);
                        paySlip.setProcentasePresence(100);
                        compRslt.ScheduleNote = "TL";
                        break;
                    case PstEmpSchedule.STATUS_PRESENCE_ABSENCE:
                        paySlip.setDayAbsent(1);
                        paySlip.setDayLate(0);
                        paySlip.setDayPresent(0);
                        paySlip.setProcentasePresence(0);
                        compRslt.ScheduleNote = "A" + absReason;
                        break;
                    case PstEmpSchedule.STATUS_PRESENCE_ONLY_IN:
                        paySlip.setDayAbsent(0);
                        paySlip.setDayLate(0);
                        paySlip.setDayPresent(1);
                        paySlip.setProcentasePresence(100);
                        compRslt.ScheduleNote = "OIN";
                        break;
                    case PstEmpSchedule.STATUS_PRESENCE_ONLY_OUT:
                        paySlip.setDayAbsent(0);
                        paySlip.setDayLate(0);
                        paySlip.setDayPresent(1);
                        paySlip.setProcentasePresence(100);
                        compRslt.ScheduleNote = "OO";
                        break;
                    case PstEmpSchedule.STATUS_PRESENCE_EARLY_HOME:
                        paySlip.setDayAbsent(0);
                        paySlip.setDayLate(0);
                        paySlip.setDayPresent(1);
                        paySlip.setProcentasePresence(100);
                        compRslt.ScheduleNote = "EH";
                        break;
                    case PstEmpSchedule.STATUS_PRESENCE_LATE_EARLY:
                        paySlip.setDayAbsent(0);
                        paySlip.setDayLate(1);
                        paySlip.setDayPresent(0);
                        paySlip.setProcentasePresence(100);
                        compRslt.ScheduleNote = "LE";
                        break;
                    default:
                        ;
                }

                for (int r = 0; r < vectComp.size(); r++) {
                    String payCom = (String) vectComp.get(r);
                    int lengthPayCom = payCom.length();
                    String lastChar = payCom.substring(lengthPayCom - 1, lengthPayCom);
                    String firstChar = payCom.substring(0, lengthPayCom - 1);
                    String compNum = payCom.substring(0, 1);
                    if (payCom.equals("=")) {
                    //formSQL = formSQL;
                    } // jika tokennya berupa operator
                    else if (payCom.equals("(") || payCom.equals(")") || payCom.equals("*") || payCom.equals("/") || payCom.equals("+") || payCom.equals("-")) {
                        formSQL = formSQL + payCom;
                    } // jika tokennya berupa numerik/angka, maka langsung digabung
                    else if (compNum.equals("0") || compNum.equals("1") || compNum.equals("2") || compNum.equals("3") || compNum.equals("4") || compNum.equals("5") || compNum.equals("6") || compNum.equals("6") || compNum.equals("7") || compNum.equals("8") || compNum.equals("9")) {
                        formSQL = formSQL + payCom;
                    } // jika tokennya berupa persen,replace persen dengan angka
                    else if (lastChar.equals("%")) {
                        double numPersen = Double.parseDouble(firstChar);
                        double valuePersen = (numPersen / 100);
                        String strValuePersen = String.valueOf(valuePersen);
                        formSQL = formSQL + strValuePersen;
                    } // berupa variabel/komponent yang nilainya sudah disimpan di tabel atau ambil dari sistem property
                    else {
                        boolean compCalculated = false;
                        // untuk pengambilan nilai day late
                        if (payCom.equals("" + strDayLate) || payCom.equals(SalaryLevelDetail.DATE_LATE)) {
                            double day_late = 0;
                            compCalculated = true;
                            day_late = paySlip.getDayLate();
                            if (day_late > 0.0001d) {
                                compRslt.ScheduleNote = "TL";
                            }
                            formSQL = formSQL + String.valueOf(day_late);
                        }

                        //untuk mengambil nilai absent                                                          
                        if (payCom.equals("" + strAbsent) || payCom.equals(SalaryLevelDetail.DATE_ABSENT)) {
                            double day_absent = 0;
                            compCalculated = true;
                            day_absent = paySlip.getDayAbsent();
                            if (day_absent > 0.0001d) {
                                compRslt.ScheduleNote = "A";
                            }
                            formSQL = formSQL + String.valueOf(day_absent);
                        }

                        //untuk mengambil nilai day in period                                                          
                        if (payCom.equals(SalaryLevelDetail.DAY_PERIOD)) {
                            formSQL = formSQL + paySlip.getDayPresent();//String.valueOf(selPeriod.getDayInPeriod());
                            compCalculated = true;
                        }

                        //untuk mengambil nilai working day in period                                                          
                        if (payCom.equals(SalaryLevelDetail.WORK_DAY_PERIOD)) {
                            formSQL = formSQL + paySlip.getDayPresent();//String.valueOf(selPeriod.getWorkDays());
                            compCalculated = true;
                        }

                        //untuk mengambil nilai off schedule 
                        if (payCom.equals("" + SalaryLevelDetail.DAY_OFF_SCHEDULE)) {
                            compCalculated = true;
                            //EmpSchedule empSch = PstEmpSchedule.fetch(periodId, empId);
                            int offDay = empSch.matchOfScheduleSymbol(iCurrDate, vctSchIDOff);
                            if (offDay > 0) {
                                compRslt.ScheduleNote = "O";
                            }

                            formSQL = formSQL + offDay;//String.valueOf(selPeriod.getWorkDays());
                        }

                        //untuk mengambil nilai date overtime pada off schedule 
                        if (payCom.equals("" + SalaryLevelDetail.TOTAL_DAY_OFF_OVERTIME)) {
                        //EmpSchedule empSch = PstEmpSchedule.fetch(periodId, empId);
                            /*
                        double tOvtDate = 0.0;
                        if (empSch != null) {
                        Vector dates = empSch.dateOfScheduleSymbol(vctSchIDOff, selPeriod.getStartDate());
                        tOvtDate = PstOvt_Employee.getTotalDatesOverTm(dates, selPeriod.getOID(), emp.getEmployeeNum(), minOvtDuration);
                        }
                        formSQL = formSQL + String.valueOf(tOvtDate);
                        compCalculated = true;
                         * */
                        }

                        // untuk mengambil nilai status absensi dan reason                                  
                        if (payCom.startsWith(SalaryLevelDetail.SCH_STS_RSN)) {
                            String sTemp = "";
                            if (payCom.length() > SalaryLevelDetail.SCH_STS_RSN.length()) {
                                payCom.substring(SalaryLevelDetail.SCH_STS_RSN.length() + 1);// get data code_Schedulestatus_reason                                        
                                String sSchStatus = sTemp.substring(0, sTemp.indexOf("_"));
                                String sSchReason = sTemp.substring(sTemp.indexOf("_") + 1, sTemp.length());
                                // lanjutkan dengan code tertentu
                                compCalculated = true;
                            }
                        }

                        if ((payCom.startsWith(SalaryLevelDetail.ABSENT_RSN)) && (paySlip.getDayPresent() < 1)) {
                            String sTemp = "";
                            int sumRsn = 0;
                            compCalculated = true;
                            if (payCom.length() > SalaryLevelDetail.ABSENT_RSN.length()) {
                                sTemp = payCom.substring(SalaryLevelDetail.SCH_STS_RSN.length());// get data number_of_Schedulestatus_reason                                        
                                String sSchReason = sTemp.substring(0, sTemp.length());
                                try {
                                    Integer iRsn = new Integer(sSchReason);
                                    if (iRsn.intValue() == (int) empSch.getReason(iCurrDate)) {
                                        sumRsn = 1;
                                        compRslt.ScheduleNote = compRslt.ScheduleNote + " " + iRsn;
                                    }
                                } catch (Exception exc) {
                                    System.out.println("payroll-calc : ABSENT_RSN : " + exc);
                                }
                            }
                            formSQL = formSQL + String.valueOf(sumRsn);
                        }

                        if (payCom.startsWith(SalaryLevelDetail.DAYWORK_LESS_MNT)) {
                            String sTemp = "";
                            int dayLessWork = 0;
                            compCalculated = true;

                            try {
                                if (payCom.length() > SalaryLevelDetail.DAYWORK_LESS_MNT.length()) {
                                    sTemp = payCom.substring(SalaryLevelDetail.DAYWORK_LESS_MNT.length());// get data minutes minimum of work minutes in day                                        
                                    String sMinMinutes = sTemp.substring(1, sTemp.length());
                                    Integer iMinMinutes = new Integer(sMinMinutes);
                                    //EmpSchedule empSch = PstEmpSchedule.fetch(periodId, empId);
                                    long lMaxMIN = (iMinMinutes.longValue() * 60L * 1000L) - 1L;
                                    dayLessWork = empSch.isDatePresentDuration(iCurrDate, 1L, lMaxMIN, PstEmpSchedule.STATUS_PRESENCE_OK, 1, 1);  // get date that status present OK but work hour is less then maxMinutes
                                }
                            } catch (Exception exc) {
                                System.out.println("payroll-process : DAYWORK_LESS_MINUTES : " + exc);
                            }

                            formSQL = formSQL + String.valueOf(dayLessWork);
                        }

                        // untyk presence
                        if (payCom.equals("" + strPresence) || payCom.equals(SalaryLevelDetail.DATE_PRESENT)) {
                            double day_presence = 0;
                            compCalculated = true;
                            day_presence = paySlip.getDayPresent();
                            formSQL = formSQL + String.valueOf(day_presence);
                        } // kondisi untuk tunjangan ekspor(khusus intimas)
                        else if (payCom.equals("" + procentasePresence)) {
                            double procenPresence = 0;
                            procenPresence = (paySlip.getProcentasePresence() / 100);

                            formSQL = formSQL + String.valueOf(procenPresence);
                        } else if (payCom.equals("" + salaryEmp)) {
                            double sumSalary = PstPaySlip.getSumSalary(periodId, empId);
                            formSQL = formSQL + String.valueOf(sumSalary);
                        } else if (payCom.equals("" + tot_idx)) {
                            double total_idx = 0.0d;//PstOvt_Employee.getTotIdx(paySlipId);
                            formSQL = formSQL + String.valueOf(total_idx);
                        } else if (payCom.equals("" + tot_ovt)) {
                            double total_ovt = 0.0d; //PstOvt_Employee.getTotOvtDuration(paySlipId);
                            formSQL = formSQL + String.valueOf(total_ovt);
                        } else if (!compCalculated /*!payCom.equals(SalaryLevelDetail.DAY_PERIOD) &&
                                !payCom.equals(SalaryLevelDetail.WORK_DAY_PERIOD) &&
                                !payCom.equals("" + SalaryLevelDetail.DAY_OFF_SCHEDULE) && 
                                !payCom.equals("" + SalaryLevelDetail.TOTAL_DAY_OFF_OVERTIME) && 
                                !payCom.startsWith(SalaryLevelDetail.ABSENT_RSN) && 
                                !payCom.startsWith(SalaryLevelDetail.SCH_STS_RSN)*/) {
                            //untuk pengambilan komponent yang tidak ada spasinya
                            // pengecekan untuk spasi                                                                                
                            String payCompSpace = PstPaySlipComp.getCodeComponent(payCom);
                            if (payCompSpace.length() == 0) {
                                payCom = " " + payCom;
                            }
                            double compValue = PstPaySlipComp.getCompValueEmployee(empId, periodId, payCom);
                            String strCompValue = String.valueOf(compValue);
                            formSQL = formSQL + strCompValue;
                        }
                    }
                }
                // lempar ke MySQL
                //System.out.println("formSQL  " + compCode + " adalah" + formSQL);
                double compFormValue = PstPaySlipComp.getCompFormValue(formSQL);
                if (compFormValue < 0.0) {
                    compFormValue = 0.0;
                }
                formSQL = "";

                compRslt.compValue = compFormValue;
                compRslt.systemNote = "";
                compRslt.paySlip = paySlip;

                vRslt.add(compRslt);

                iCurrDate++;
                if (iCurrDate > maxDateOfMonth) {
                    if (iStDatePeriod == 1) {
                        iCurrDate = 0;
                    } else {
                        iCurrDate = 1;
                    }
                }

            } while (iCurrDate > 0 && iCurrDate < 32 && iCurrDate != iStDatePeriod);

            return vRslt;
        }
    }

    public static String getFormula(long empId, long periodId, String payCompCode) {
        //Period period = PstPeriod.fetchExc(periodId);

        // pengambilan formula untuk diproses
        String formula = "";

        // ambil level untuk employee jika payEmpLevel 
        PayEmpLevel payEmpLevel = null;

        if (payEmpLevel == null) {
            String whereEmpLevel = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] + "=" + empId + " AND " +
                    PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + PstPayEmpLevel.CURRENT;
            String orderEmpLevel = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE];
            Vector empLevels = PstPayEmpLevel.list(0, 1, whereEmpLevel, orderEmpLevel);

            if ((empLevels == null) || (empLevels.size() < 1)) {
                return "No Employee Level is found for the employee";
            }

            payEmpLevel = (PayEmpLevel) empLevels.get(0);
        }


        String where = PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE] + "='" + payEmpLevel.getLevelCode() + "'" +
                " AND " + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_FORMULA] + " NOT LIKE '%IN_%'" +
                " AND " + PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE] + "='" + payCompCode + "'";
        //" AND "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]+"="+PstSalaryLevelDetail.YES_TAKE;
        Vector component = PstSalaryLevelDetail.list(0, 1, where, "");

        if ((component == null) || (component.size() < 1)) {
            return null;
        }

        // ambil formula jika belum didefinisikan 
        SalaryLevelDetail salaryComp = null;
        for (int c = 0; c < component.size(); c++) {
            Vector vectToken = new Vector(1, 1);
            salaryComp = (SalaryLevelDetail) component.get(c);
            String compCode = salaryComp.getCompCode();
            if (compCode.trim().compareTo(payCompCode.trim()) == 0) {
                break; // end loop                   
            }
        }
        formula = salaryComp.getFormula();

        return formula;
    }

    public static final int PAID_BY_BANK=0;
    public static final int PAID_BY_CASH=1;
    public static final int PAID_BY_ALL=2;
    public static Vector<String> paidByValue(){
        Vector valPaidBy = new Vector();
        valPaidBy.add(""+PAID_BY_BANK);
        valPaidBy.add(""+PAID_BY_CASH);
        valPaidBy.add(""+PAID_BY_ALL);
        return valPaidBy;
    }
    
    public static Vector<String> paidByKey(){
        Vector keyPaidBy = new Vector();
        keyPaidBy.add("Through Bank Only");
        keyPaidBy.add("Cash Only");
        keyPaidBy.add("Both type");        
        return keyPaidBy;
    }
    /**
     * 
     * @param getCash
     * @param salLevelSelect
     * @param oidPeriod
     * @param departmentId
     * @param withSummary
     * @param totalAddSummary
     * @param paidBy
     * @param empCategory
     * @return 
     */
    public static ListSalary getSalary(boolean getCash, Vector salLevelSelect, long oidPeriod,
            long departmentId, boolean withSummary, String totalAddSummary, int paidBy, Vector empCategory,long paySLipGroup) {

        double totalSalary = 0;
        ListSalary listSalary = new ListSalary();
        //listSalary.queryReport = new Vector(1, 1);

        if (salLevelSelect == null || salLevelSelect.size() < 1) {
            salLevelSelect = PstSalaryLevel.list(0, 0, "", PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_NAME]);
        }

        listSalary.queryReport = SessEmployee.getEmpSalary(salLevelSelect, oidPeriod, departmentId, paidBy,empCategory );

        if (withSummary) {
            System.out.println("oidPeriod  " + oidPeriod);
            Vector vectTransfered = SessEmployee.getEmpSalary(salLevelSelect, oidPeriod, departmentId, paidBy,empCategory );
            listSalary.totalBenefit = 0.0;
            for (int i = 0; i < vectTransfered.size(); i++) {
                Vector vectTemp = (Vector) vectTransfered.get(i);
                //Employee employee = (Employee) vectTemp.get(0);
                PayEmpLevel payEmpLevel = (PayEmpLevel) vectTemp.get(1);
                PaySlip paySlip = (PaySlip) vectTemp.get(4);
                int totalBenefit = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE, payEmpLevel.getLevelCode(), PstPayComponent.TYPE_BENEFIT, paySlip.getOID(), PstSalaryLevelDetail.PERIODE_MONTHLY,paySLipGroup);
                int totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE, payEmpLevel.getLevelCode(), PstPayComponent.TYPE_DEDUCTION, paySlip.getOID(), PstSalaryLevelDetail.PERIODE_MONTHLY,paySLipGroup);
                int takeHomePay = totalBenefit - totalDeduction;
                totalSalary = totalSalary + takeHomePay;
                listSalary.totalBenefit = listSalary.totalBenefit + totalBenefit;
                listSalary.totalDeduction = listSalary.totalDeduction = totalDeduction;
            }
        }


        //Jika ada additional
        int addSalary = PstPayAdditional.getAddValue(totalAddSummary);
        totalSalary = totalSalary + addSalary;
        //---------------------

        listSalary.totalSalary = totalSalary;

        return listSalary;
    }

    public static double getSumPaySlipComp(String compCode, long paySlipId) {
        DBResultSet dbrs = null;
        try {
            String whereClause = "";
            String sql = "SELECT SUM(COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ")" +
                    " FROM " + PstPaySlipComp.TBL_PAY_SLIP_COMP + " AS COMP" +
                    " WHERE COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] + "=" + paySlipId + "" + " AND COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + compCode + "'";

            //System.out.println("sql PstSalaryLevelDetail.getSumBenefitDoub -------"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            double sumBenefit = 0;

            while (rs.next()) {
                sumBenefit = rs.getDouble(1);
            }

            rs.close();
            return sumBenefit;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static double getSumOvtTraining(long oidPeriod, String totalNonTransfered) {
        double nonTransfered = 0; //non transfer here means for Intimas = Training Employee    
        Vector vectNonTransfered = SessEmployee.getEmpNonTransfered(oidPeriod, PstSystemProperty.getValueByName("OID_TRAINING"));
        if (vectNonTransfered != null && vectNonTransfered.size() > 0) {
            for (int j = 0; j < vectNonTransfered.size(); j++) {
                Vector temp = (Vector) vectNonTransfered.get(j);
                PaySlip paySlip = (PaySlip) temp.get(0);
                // kode lembur diset di system property dengan nama "CODE_OVT"
                double empNonTransfered = PstPaySlipComp.getReportCompValue(paySlip.getOID(), PstSystemProperty.getValueByName("CODE_OVT"));
                nonTransfered = nonTransfered + empNonTransfered;
            }
        }
        //ambil summ salary
        //Jika ada additional

        double addNonTransfered = PstPayAdditional.getAddValue(totalNonTransfered);
        nonTransfered = nonTransfered + addNonTransfered;

        return nonTransfered;
    }
    public static double getTotalSalary(long oidPeriod, String totalSummaryTransfered, String totalNonTransfered) {
     return getTotalSalary(oidPeriod,  totalSummaryTransfered,  totalNonTransfered, PAID_BY_ALL, null);
    }

    public static double getTotalSalary(long oidPeriod, String totalSummaryTransfered, String totalNonTransfered, int paidBy, Vector empCategory) {
        try{
        ListSalary listSalTransfer = SessPaySlip.getSalary(false, null, oidPeriod, 0, true, totalSummaryTransfered,paidBy,empCategory,0);
        ListSalary listSalNonTransfer = SessPaySlip.getSalary(true, null, oidPeriod, 0, true, totalNonTransfered,paidBy,empCategory,0);
        double addTransfered = PstPayAdditional.getAddValue(totalSummaryTransfered);
        return listSalTransfer.totalSalary + listSalNonTransfer.totalSalary + addTransfered;
        } catch(Exception exc){
            System.out.println("getTotalSalary >> "+ exc);
            return 0.0d;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String payLevelCode = "LEVEL 1_TRAINING";
        //System.out.println("Return : " + generatePaySlip(504404366915889192L, payLevelCode));
    }
}
