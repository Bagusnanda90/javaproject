/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.employee.CareerPath;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstCareerPath;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.DivisionType;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Marital;
import com.dimata.harisma.entity.masterdata.Negara;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstDivisionType;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstMarital;
import com.dimata.harisma.entity.masterdata.PstNegara;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.payroll.PayComponent;
import com.dimata.harisma.entity.payroll.PayEmpLevel;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PaySlip;
import com.dimata.harisma.entity.payroll.PaySlipComp;
import com.dimata.harisma.entity.payroll.PstPayBanks;
import com.dimata.harisma.entity.payroll.PstPayComponent;
import com.dimata.harisma.entity.payroll.PstPayEmpLevel;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.entity.payroll.PstPaySlipComp;
import com.dimata.harisma.entity.payroll.PstSalaryLevelDetail;
import com.dimata.harisma.entity.payroll.SalaryLevelDetail;
import com.dimata.harisma.report.SrcEsptMonth;
import com.dimata.harisma.report.SrcEsptA1;
import com.dimata.harisma.session.leave.SessLeaveClosing;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Description : class relation Date : 2015-02-26
 *
 * @author Hendra Putu
 */
public class SessESPT {

    public static String TABLE_HEAD_A1[] = {
        "Masa Pajak",
        "Tahun",
        "Pembetulan",
        "Nomor Bukti Potong", //5
        "Masa Perolehan Awal ",
        "Masa Perolehan Akhir",
        "NPWP",
        "NIK",
        "Nama", //10
        "Alamat",
        "Jenis Kelamin",
        "Status PTKP",
        "Jumlah Tanggungan",
        "Nama Jabatan", //15
        "WP Luar Neger",
        "Kode Negara",
        "Kode Pajak",
        "Jumlah 1",
        "Jumlah 2",//20
        "Jumlah 3",
        "Jumlah 4",
        "Jumlah 5",
        "Jumlah 6",
        "Jumlah 7",
        "Jumlah 8",
        "Jumlah 9",
        "Jumlah 10",
        "Jumlah 11",
        "Jumlah 12",
        "Jumlah 13",
        "Jumlah 14",
        "Jumlah 15",
        "Jumlah 16",
        "Jumlah 17",
        "Jumlah 18",
        "Jumlah 19",
        "Jumlah 20",
        "Status Pindah",
        "NPWP Pemotong",
        "Nama Pemotong",
        "Tanggal Bukti Potong"
    };

    
    public static String TABLE_HEAD_ANNUAL_TAX_DIFFERENT[] = {
        "No",
        "Emp Num",
        "Emp Name",
        "NPWP",
        "Type Tax",
        "Jumlah Bruto",
        "Jumlah PPH21 - 19",
        "Jumlah PPH21 - 20"
    };
    public static double getPph21(long periodId, long employeeId, String inPPHCodes) {
        double pph21 = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT hr_employee.EMPLOYEE_ID, pay_slip_comp.COMP_VALUE FROM hr_employee ";
            sql += " INNER JOIN pay_slip ON pay_slip.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";
            sql += " INNER JOIN pay_slip_comp ON pay_slip_comp.PAY_SLIP_ID=pay_slip.PAY_SLIP_ID ";
            sql += " WHERE pay_slip.PERIOD_ID=" + periodId + " ";
            sql += " AND hr_employee.EMPLOYEE_ID=" + employeeId + " ";
            sql += " AND pay_slip_comp.COMP_CODE IN (" + inPPHCodes + ")";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                pph21 = pph21 + rs.getDouble("COMP_VALUE");
            }
            return pph21;
        } catch (Exception e) {
            System.out.println("\t Exception on  search espt : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return pph21;
    }

    /**
     *
     * @param periodId
     * @param employeeId
     * @param inCompCode : componen code untuk di tambah di IN(...) spt
     * 'TI','ALW28'
     * @return
     */
    public static double getTotalBruto(long periodId, long employeeId, String inCompCode) {
        double totalBruto = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT hr_employee.EMPLOYEE_ID, pay_slip_comp.COMP_VALUE FROM hr_employee ";
            sql += " INNER JOIN pay_slip ON pay_slip.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";
            sql += " INNER JOIN pay_slip_comp ON pay_slip_comp.PAY_SLIP_ID=pay_slip.PAY_SLIP_ID ";
            sql += " WHERE pay_slip.PERIOD_ID=" + periodId + " ";
            sql += " AND hr_employee.EMPLOYEE_ID=" + employeeId + " ";
            sql += " AND pay_slip_comp.COMP_CODE IN (" + inCompCode + ")";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                totalBruto = totalBruto + rs.getDouble("COMP_VALUE");
            }
            return totalBruto;
        } catch (Exception e) {
            System.out.println("\t Exception on  search espt : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return totalBruto;
    }

    public static double getTotalBrutoOftheYears(long periodId, long employeeId, String inCompCode) {
        double totalBruto = 0;
        DBResultSet dbrs = null;
        
        Vector periodList = PstPaySlip.getYearPeriodListToThisPeriod(periodId);
        String periodeIdOneYears = "";
        if (periodList.size() != 0){
        for (int x = 0 ; x<periodList.size() ; x++){
            PayPeriod payPeriod = (PayPeriod) periodList.get(x);
             periodeIdOneYears = periodeIdOneYears+ payPeriod.getOID()+",";
        }
        periodeIdOneYears =periodeIdOneYears.substring(0, periodeIdOneYears.length()-1);
        }
        
        try {
            String sql = "SELECT hr_employee.EMPLOYEE_ID, pay_slip_comp.COMP_VALUE FROM hr_employee ";
            sql += " INNER JOIN pay_slip ON pay_slip.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";
            sql += " INNER JOIN pay_slip_comp ON pay_slip_comp.PAY_SLIP_ID=pay_slip.PAY_SLIP_ID ";
            sql += " WHERE pay_slip.PERIOD_ID IN (" + periodeIdOneYears + ") ";
            sql += " AND hr_employee.EMPLOYEE_ID=" + employeeId + " ";
            sql += " AND pay_slip_comp.COMP_CODE IN (" + inCompCode + ")";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                totalBruto = totalBruto + rs.getDouble("COMP_VALUE");
            }
            return totalBruto;
        } catch (Exception e) {
            System.out.println("\t Exception on  search espt : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return totalBruto;
    }
    
    public static Vector getListEsptMonth(long periodId, long divisionId, long departmentId, long sectionId, String inPPHBrutto, String inPPHCodes) {
    return getListEsptMonth(periodId, divisionId, departmentId, sectionId, inPPHBrutto, inPPHCodes,-1) ;
    }
    
    public static Vector getListEsptMonth(long periodId, long divisionId, long departmentId, long sectionId, String inPPHBrutto, String inPPHCodes,int inp_resignStatus) {

        Vector list = new Vector();
        //////////////////////////
        PayPeriod payPeriod =new PayPeriod();
        try{
                payPeriod = PstPayPeriod.fetchExc(periodId);
        } catch (Exception e) {}
        
        Vector periodList = PstPaySlip.getYearPeriodListToThisPeriod(periodId);
        String periodeIdOneYears = "";
        if (periodList.size() != 0){
        for (int x = 0 ; x<periodList.size() ; x++){
            PayPeriod payPeriod2 = (PayPeriod) periodList.get(x);
            periodeIdOneYears = periodeIdOneYears+ payPeriod2.getOID()+",";
        }
        periodeIdOneYears =periodeIdOneYears.substring(0, periodeIdOneYears.length()-1);
        }
        int withoutDH = 0;
        try {
            withoutDH = Integer.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("SAL_LEVEL_WITHOUT_DH"));
        } catch (Exception e) {
            System.out.printf("VALUE_NOTDC TIDAK DI SET?");
        }
        DBResultSet dbrs = null;
      
        try {
            String sql = "SELECT DISTINCT(hr_employee.EMPLOYEE_ID),";
                   
                   if (inp_resignStatus != 3) {
                   sql += "pay_period.PERIOD,";
                   }
                   sql += "hr_employee.EMPLOYEE_NUM, hr_employee.FULL_NAME, ";
            sql += " hr_employee.NPWP, hr_emp_category.TYPE_FOR_TAX FROM hr_employee ";
            sql += " INNER JOIN hr_emp_category ON hr_emp_category.EMP_CATEGORY_ID=hr_employee.EMP_CATEGORY_ID ";
            sql += " INNER JOIN pay_slip ON pay_slip.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";

            if (withoutDH == 1) {
                sql += " INNER JOIN pay_emp_level  ON (hr_employee.EMPLOYEE_ID = pay_emp_level.EMPLOYEE_ID AND pay_emp_level.`LEVEL_CODE` NOT LIKE '%-DH%') ";

            }
            sql += " WHERE 1=1 ";

            if (divisionId != 0) {
                sql += " AND hr_employee.DIVISION_ID=" + divisionId + " ";
            }
            if (departmentId != 0) {
                sql += " AND hr_employee.DEPARTMENT_ID=" + departmentId + " ";
            }
            if (sectionId != 0) {
                sql += " AND hr_employee.SECTION_ID=" + sectionId + " ";
            } //perbaruan by priska 20151224
            if (inp_resignStatus == PstEmployee.NO_RESIGN){
                    sql+= "  AND ( hr_employee." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN + " OR " + " hr_employee." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + " BETWEEN \"" + Formater.formatDate(payPeriod.getStartDate(), "yyyy-MM-dd  00:00:00") + "\"" + " AND " + "\"" + Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd  23:59:59") + "\""
                    + " ) "; 
                    sql += " AND pay_period.PERIOD_ID=" + periodId + " ";
            } else if (inp_resignStatus == PstEmployee.YES_RESIGN) {
                sql += " AND hr_employee.RESIGNED=" + PstEmployee.YES_RESIGN + " ";
                sql += " AND pay_period.PERIOD_ID IN (" + periodId + ") ";
            } else if (inp_resignStatus == 3) {
                sql += " AND hr_employee.RESIGNED=" + PstEmployee.YES_RESIGN + " ";
                sql += " AND pay_period.PERIOD_ID IN (" + periodeIdOneYears + ") ";
            }else {
                sql += " AND pay_period.PERIOD_ID=" + periodId + " ";
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                SrcEsptMonth espt = new SrcEsptMonth();
                
                long lastPeriodIdbyPaySlip = PstPaySlip.getLastPaySlipPeriodId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), ""+(payPeriod.getEndDate().getYear()+1900));
                if (inp_resignStatus != 3) {
                    espt.setPeriod(rs.getString(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD]));
                } else {
                    PayPeriod lastPayPeriod = PstPayPeriod.fetchExc(lastPeriodIdbyPaySlip);
                    espt.setPeriod(lastPayPeriod.getPeriod()); 
                    periodId=lastPeriodIdbyPaySlip;
                }
                espt.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                espt.setEmployeeName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                espt.setTypeForTax(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_TYPE_FOR_TAX]));
                espt.setNpwp(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_NPWP]));
                if (inp_resignStatus != 3) {
                espt.setJumlahBruto(getTotalBruto(periodId, rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), inPPHBrutto));
                } else {
                espt.setJumlahBruto(getTotalBrutoOftheYears(periodId, rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), inPPHBrutto));
                }
                
                espt.setJumlahPph(getPph21(periodId, rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), inPPHCodes));
                list.add(espt);
            }

            return list;
        } catch (Exception e) {
            System.out.println("\t Exception on  search espt : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }

        return list;
    }

    
    
    //priska 20151224
    
    public static Vector getListEsptYear(long periodId, long divisionId, long departmentId, long sectionId, String inPPHBrutto, String inPPHCodes) {

        Vector list = new Vector();
        //////////////////////////
        PayPeriod payPeriod =new PayPeriod();
        try{
                payPeriod = PstPayPeriod.fetchExc(periodId);
        } catch (Exception e) {}
        
        Vector periodList = PstPaySlip.getYearPeriodListToThisPeriod(periodId);
        String periodeIdOneYears = "";
        if (periodList.size() != 0){
        for (int x = 0 ; x<periodList.size() ; x++){
            PayPeriod payPeriod2 = (PayPeriod) periodList.get(x);
            periodeIdOneYears = periodeIdOneYears+ payPeriod2.getOID()+",";
        }
        periodeIdOneYears =periodeIdOneYears.substring(0, periodeIdOneYears.length()-1);
        }
        int withoutDH = 0;
        try {
            withoutDH = Integer.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("SAL_LEVEL_WITHOUT_DH"));
        } catch (Exception e) {
            System.out.printf("VALUE_NOTDC TIDAK DI SET?");
        }
        DBResultSet dbrs = null;
      
        try {
            String sql = "SELECT DISTINCT(hr_employee.EMPLOYEE_ID),";
                   
                   
                   sql += "hr_employee.EMPLOYEE_NUM, hr_employee.FULL_NAME, ";
            sql += " hr_employee.NPWP, hr_emp_category.TYPE_FOR_TAX FROM hr_employee ";
            sql += " INNER JOIN hr_emp_category ON hr_emp_category.EMP_CATEGORY_ID=hr_employee.EMP_CATEGORY_ID ";
            sql += " INNER JOIN pay_slip ON pay_slip.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";

            if (withoutDH == 1) {
                sql += " INNER JOIN pay_emp_level  ON (hr_employee.EMPLOYEE_ID = pay_emp_level.EMPLOYEE_ID AND pay_emp_level.`LEVEL_CODE` NOT LIKE '%-DH%') ";

            }
            sql += " WHERE 1=1 ";

            if (divisionId != 0) {
                sql += " AND hr_employee.DIVISION_ID=" + divisionId + " ";
            }
            if (departmentId != 0) {
                sql += " AND hr_employee.DEPARTMENT_ID=" + departmentId + " ";
            }
            if (sectionId != 0) {
                sql += " AND hr_employee.SECTION_ID=" + sectionId + " ";
            } //perbaruan by priska 20151224
           
                sql += " AND hr_employee.RESIGNED=" + PstEmployee.YES_RESIGN + " ";
                sql += " AND pay_period.PERIOD_ID IN (" + periodeIdOneYears + ") ";
            
                sql += " ORDER BY hr_employee.EMPLOYEE_NUM ASC ";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                SrcEsptMonth espt = new SrcEsptMonth();
                
                long lastPeriodIdbyPaySlip = PstPaySlip.getLastPaySlipPeriodId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), ""+(payPeriod.getEndDate().getYear()+1900));
                PayPeriod lastPayPeriod = PstPayPeriod.fetchExc(lastPeriodIdbyPaySlip);
                espt.setPeriod(lastPayPeriod.getPeriod()); 
                periodId=lastPeriodIdbyPaySlip;
                
                espt.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                espt.setEmployeeName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                espt.setTypeForTax(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_TYPE_FOR_TAX]));
                espt.setNpwp(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_NPWP]));
                double setJumlahBruto = getTotalBrutoOftheYears(periodId, rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), inPPHBrutto);
                espt.setJumlahBruto(setJumlahBruto);
                double setJumlahPph = PstPayComponent.getPPH21OneYears(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), periodId);
                espt.setJumlahPph(setJumlahPph);
                if ((setJumlahBruto - setJumlahPph) != 0){
                list.add(espt);
                }
                
            }

            return list;
        } catch (Exception e) {
            System.out.println("\t Exception on  search espt : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }

        return list;
    }
    
    /**
     * @author Kartika
     * @Description : untuk mengambil list dari karyawan yang pindah kantor atau
     * berhenti pada periode yang dipilih sertga filter yang dipilih. Untuk
     * periode di tengah tahun ( Januari sdgn November ) Yang dipilih adalah :
     * 1. karyawan yang pindah divisi dengan tipe cabang dengan data npwp yang
     * berbeda 2. Karyawan yang berhenti bekerja di tengah waktu
     *
     * Untuk periode akhir tahun : desember Yang dipilih semua karyawan dengan
     * pembedaan di PPH sebelumnya ( untuk karyawan yang pindah cabang )
     * @param periodId
     * @param divisionId
     * @param departmentId
     * @param sectionId
     * @param inPPHBrutto
     * @param inPPHCodes
     * @return
     */
    public static Vector<SrcEsptA1> getListEsptA1Month(Pajak pajak, long periodId, long divisionId, long departmentId, long sectionId, String inPPHBrutto, String inPPHCodes) throws Exception {

        Vector list = new Vector();
        //////////////////////////

        DBResultSet dbrs = null;
        int withoutDH = 0;
        String clientName ="";
        try {
             clientName = com.dimata.system.entity.PstSystemProperty.getValueByName("CLIENT_NAME");
        } catch (Exception ex) {
            System.out.println("Execption CLIENT_NAME " + ex);
        }
        
        try {
            withoutDH = Integer.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("SAL_LEVEL_WITHOUT_DH"));
        } catch (Exception e) {
            System.out.printf("VALUE_NOTDC TIDAK DI SET?");
        }
        try {
            String tblEmp = PstEmployee.TBL_HR_EMPLOYEE;
            String sql = "SELECT DISTINCT(hr_employee.EMPLOYEE_ID) AS EMPLOYEE_OID, pay_period.PERIOD, hr_employee.FULL_NAME"
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_NPWP]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_INDENT_CARD_NR]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_TAX_MARITAL_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_COUNTRY_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE];
            sql += ", hr_emp_category.TYPE_FOR_TAX, hr_employee.`DIVISION_ID`, hr_employee.`DEPARTMENT_ID`, 0 AS HIS_DIV_ID,0 AS HIS_DEP_ID, NULL AS WORK_FROM, NULL  AS WORK_TO " + " FROM hr_employee ";
            sql += " INNER JOIN hr_emp_category ON hr_emp_category.EMP_CATEGORY_ID=hr_employee.EMP_CATEGORY_ID ";
            sql += " INNER JOIN pay_slip ON pay_slip.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";

            if (withoutDH == 1) {
                sql += " INNER JOIN pay_emp_level  ON (hr_employee.EMPLOYEE_ID = pay_emp_level.EMPLOYEE_ID AND pay_emp_level.`LEVEL_CODE` NOT LIKE '%-DH%') ";

            }

            PayPeriod payPeriod = null;
            try {
                payPeriod = PstPayPeriod.fetchExc(periodId);
                if (payPeriod == null || payPeriod.getOID() == 0) {
                    throw new Exception("Payroll Period can't not be get with id=" + periodId, new Throwable());
                }
            } catch (Exception exc) {
                throw new Exception("Payroll Period can't not be get with id=" + periodId, exc);
            }
            if ((payPeriod.getEndDate().getMonth() + 1) != 12) {
                sql += " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " between \"" + Formater.formatDate(payPeriod.getStartDate(), "yyyy-MM-dd 00:00:00")
                        + "\" AND \"" + Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd 23:59:59") + "\" AND pay_slip.PERIOD_ID=" + periodId;
            } else {
                sql += " WHERE pay_slip.PERIOD_ID=" + periodId;
            }
            if (divisionId != 0) {
                sql += " AND hr_employee.DIVISION_ID=" + divisionId + " ";
            }
            if (departmentId != 0) {
                sql += " AND hr_employee.DEPARTMENT_ID=" + departmentId + " ";
            }
            if (sectionId != 0) {
                sql += " AND hr_employee.SECTION_ID=" + sectionId + " ";
            }

            if ((payPeriod.getEndDate().getMonth() + 1) != 12) { // jika tidak akhir tahun ambil karyawan yang pindah cabang
                String sql2 = "SELECT DISTINCT(hr_employee.EMPLOYEE_ID) AS EMPLOYEE_OID, pay_period.PERIOD, hr_employee.FULL_NAME"
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_NPWP]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_INDENT_CARD_NR]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_TAX_MARITAL_ID]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_COUNTRY_ID]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE];
                sql2 += ", hr_emp_category.TYPE_FOR_TAX , hr_employee.`DIVISION_ID`, hr_employee.`DEPARTMENT_ID`, "
                        + " `hr_work_history_now`.`DIVISION_ID` AS HIS_DIV_ID, `hr_work_history_now`.`DEPARTMENT_ID` AS HIS_DEP_ID, `hr_work_history_now`.`WORK_FROM`, `hr_work_history_now`.`WORK_TO`"
                        + " FROM hr_employee ";
                sql2 += " INNER JOIN hr_emp_category ON hr_emp_category.EMP_CATEGORY_ID=hr_employee.EMP_CATEGORY_ID ";
                sql2 += " INNER JOIN pay_slip ON pay_slip.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
                sql2 += " INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";
                sql2 += " LEFT JOIN hr_work_history_now ON hr_work_history_now.EMPLOYEE_ID = hr_employee.EMPLOYEE_ID ";

                if (withoutDH == 1) {
                    sql2 += " INNER JOIN pay_emp_level  ON (hr_employee.EMPLOYEE_ID = pay_emp_level.EMPLOYEE_ID AND pay_emp_level.`LEVEL_CODE` NOT LIKE '%-DH%') ";

                }

                if ((payPeriod.getEndDate().getMonth() + 1) != 12) {
                    sql2 += " WHERE `hr_work_history_now`.`WORK_TO`  between \"" + Formater.formatDate(payPeriod.getStartDate(), "yyyy-MM-dd 00:00:00")
                            + "\" AND \"" + Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd 23:59:59") + "\" AND pay_slip.PERIOD_ID=" + periodId;
                } else {
                    sql2 += " WHERE pay_slip.PERIOD_ID=" + periodId;
                }
                if (divisionId != 0) {
                    sql2 += " AND hr_employee.DIVISION_ID=" + divisionId + " ";
                }
                if (departmentId != 0) {
                    sql2 += " AND hr_employee.DEPARTMENT_ID=" + departmentId + " ";
                }
                if (sectionId != 0) {
                    sql2 += " AND hr_employee.SECTION_ID=" + sectionId + " ";
                }

                sql = sql + " UNION " + sql2;
            }
            
            sql = sql + "  ORDER BY `EMPLOYEE_NUM`  ASC ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Hashtable<String, Marital> maritalMap = PstMarital.listMap(0, 0, "", "");
            Hashtable<String, Position> positionMap = PstPosition.listMap(0, 0, "", "");
            Hashtable<String, EmpCategory> categoryMap = PstEmpCategory.listMap(0, 0, "", "");
            Hashtable<String, Negara> countryMap = PstNegara.listMap(0, 0, "", "");
            Hashtable<String, Division> divisonMap = PstDivision.listMap(0, 0, "", "");
            Hashtable<String, Department> departmentMap = PstDepartment.listMap(0, 0, "", "", "");
            Hashtable<String, DivisionType> divTypeMap = PstDivisionType.listMap(0, 0, "", "");
            Employee empPemotongPusat = null;
            Hashtable<String, Employee> mapDivIdEmployee = null;

            try {
                String strPemotongPusat = PstSystemProperty.getValueByNameWithStringNull("TAX_A1_POSISI_PEMOTONG_PUSAT");
                String strPemotongCabang = PstSystemProperty.getValueByNameWithStringNull("TAX_A1_POSISI_PEMOTONG_CABANG");

                Vector vPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_POSITION] + "=\"" + strPemotongPusat + "\"", "");
                Position posPemotongPusat = vPos != null && vPos.size() > 0 ? (Position) vPos.get(0) : null;

                vPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_POSITION] + "=\"" + strPemotongCabang + "\"", "");
                Position posPemotongCabang = vPos != null && vPos.size() > 0 ? (Position) vPos.get(0) : null;

                Vector vHistory = null;

                if (posPemotongPusat != null && posPemotongPusat.getOID() != 0) {
                    vHistory = PstCareerPath.list(0, 1, PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID] + "=" + posPemotongPusat.getOID() + " AND "
                            + "( \"" + Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd 00:00:00") + "\" BETWEEN " + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM] + " AND "
                            + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO], "");

                    if (vHistory == null || vHistory.size() < 1) {
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongPusat.getOID(), "");
                        empPemotongPusat = vEmployee != null && vEmployee.size() > 0 ? (Employee) vEmployee.get(0) : null;
                    } else {
                        CareerPath carPath = (CareerPath) vHistory.get(0);
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongPusat.getOID(), "");
                        empPemotongPusat = (Employee) vEmployee.get(0);
                    }
                }

                if (posPemotongCabang != null && posPemotongCabang.getOID() != 0) {
                    vHistory = PstCareerPath.list(0, 0, PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID] + "=" + posPemotongCabang.getOID() + " AND "
                            + "( \"" + Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd 00:00:00") + "\" BETWEEN " + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM] + " AND "
                            + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO], "");

                    if (vHistory == null || vHistory.size() < 1) {
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongCabang.getOID(), "");
                        if( vEmployee != null && vEmployee.size() > 0){
                            mapDivIdEmployee = new Hashtable<String, Employee>();
                            for(int idx=0;idx < vEmployee.size() ; idx ++ ){
                                Employee emp = (Employee) vEmployee.get(idx);
                                mapDivIdEmployee.put(""+emp.getDivisionId(), emp);
                            }
                        }
                    } else {
                        CareerPath carPath = (CareerPath) vHistory.get(0);
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongCabang.getOID(), "");
                         if( vEmployee != null && vEmployee.size() > 0){
                            mapDivIdEmployee = new Hashtable<String, Employee>();
                            for(int idx=0;idx < vEmployee.size() ; idx ++ ){
                                Employee emp = (Employee) vEmployee.get(idx);
                                mapDivIdEmployee.put(""+emp.getDivisionId(), emp);
                            }
                        }
                    }
                }
                
                if(empPemotongPusat!=null){
                    if(mapDivIdEmployee==null){
                        mapDivIdEmployee = new Hashtable<String, Employee>();
                    }
                    mapDivIdEmployee.put(""+empPemotongPusat.getDivisionId(), empPemotongPusat);
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }

            while (rs.next()) {
                try {
                    long divId_now = rs.getLong("DIVISION_ID");
                    long divId_his = rs.getLong("HIS_DIV_ID");
                    
                    long empId = rs.getLong("EMPLOYEE_OID");

                    Date resignDate = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]);
                    if (resignDate == null || !(resignDate != null
                            && (resignDate.equals(payPeriod.getEndDate()) || resignDate.equals(payPeriod.getStartDate())
                            || (resignDate.after(payPeriod.getStartDate()) && resignDate.before(payPeriod.getEndDate()))))) {
                        Division divNow = divisonMap.get("" + divId_now);
                        Division divHis = divisonMap.get("" + divId_his);
                        if ((divNow != null && divHis != null && divHis.getNpwp() != null && divNow.getNpwp() != null && divNow.getNpwp().equalsIgnoreCase(divHis.getNpwp()))
                                || (divNow != null && divHis == null)) {
                            continue;
                        }

                        long depId_now = rs.getLong("DEPARTMENT_ID");
                        long depId_his = rs.getLong("HIS_DEP_ID");
                        Department depNow = departmentMap.get("" + depId_now);
                        Department depHis = departmentMap.get("" + depId_his);
                        if ((depNow != null && depHis != null && depHis.getNpwp() != null && depNow.getNpwp() != null && depNow.getNpwp().equalsIgnoreCase(depHis.getNpwp()))
                                || (depNow != null && divHis == null)) {
                            continue;
                        }
                    }

                    SrcEsptA1 espt = new SrcEsptA1();
                    espt.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                    espt.setResignedDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                    espt.setMasaPajak(payPeriod.getEndDate().getMonth() + 1);
                    espt.setTahunPajak(payPeriod.getEndDate());
                    espt.setPembetulan(0);
                    espt.setNomorBuktiPotong("");
//                    Date dt = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]);
//                    if (dt.getYear() < payPeriod.getEndDate().getYear()) {
//                        espt.setMasaPerolehanAwal(1);
//                    } else if (dt.getYear() == payPeriod.getEndDate().getYear()) {
//                        espt.setMasaPerolehanAwal(dt.getMonth() + 1);
//                    } else {
//                        continue; // if commencing after pay period then the employee doesnot include in the repoert
//                    }
//                    espt.setMasaPerolehanAkhir(payPeriod.getEndDate().getMonth() + 1);
                    
                    Date dt = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]);
                    if (dt.getYear() < payPeriod.getEndDate().getYear()) {
                        espt.setMasaPerolehanAwal(1);
                    } else if (dt.getYear() == payPeriod.getEndDate().getYear()) {
                        //seharusnya mengambil payslip periode pertamanya
                        espt.setMasaPerolehanAwal(dt.getMonth() + 1);
                        Date date = new Date();
                        try {
                            date = PstPaySlip.getFirstPayslipDate(empId, ""+(payPeriod.getEndDate().getYear()+1900));
                            if (date != null){
                                espt.setMasaPerolehanAwal(date.getMonth() + 1);
                            }
                        }catch (Exception e){} 
                        
                        
                    } else {
                        continue; // if commencing after pay period then the employee doesnot include in the repoert
                    }
                    
                    
                    int massaPerolehan = 0;
                    PayPeriod payPeriodEmp = new PayPeriod();
                    try {
                        payPeriodEmp = PstPayPeriod.getPayPeriodBySelectedDate(resignDate);
                    }catch (Exception e){ }
                    if (payPeriodEmp == null ){
                        massaPerolehan = payPeriod.getEndDate().getMonth() + 1;
                    } else {
                        massaPerolehan = payPeriodEmp.getEndDate().getMonth() + 1;
                        //seharusnya mengambil payslip periode akhir
                        Date date = new Date();
                        try {
                            date = PstPaySlip.getEndPayslipDate(empId, ""+(payPeriod.getEndDate().getYear()+1900));
                            if (date != null){
                                massaPerolehan = date.getMonth() + 1;
                            }
                        }catch (Exception e){} 
                    }
                    espt.setMasaPerolehanAkhir(massaPerolehan);
                    
                    
                    espt.setNPWP(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_NPWP]));
                    espt.setNIK(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_INDENT_CARD_NR]));
                    espt.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    espt.setNama(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    espt.setAlamat(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                    int sex = rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]);
                    espt.setJenisKelamin(sex == PstEmployee.MALE ? "M" : "F");

                    long taxStatus = rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_TAX_MARITAL_ID]);
                    Marital marital = maritalMap.get("" + taxStatus);
                    if (marital.getMaritalStatusTax() == TaxCalculator.STATUS_DIRI_SENDIRI) {
                        espt.setStatusPTKP("TK");
                        espt.setJumlahTanggungan(0);
                    } else {
                        espt.setStatusPTKP("K");
                        espt.setJumlahTanggungan(marital.getMaritalStatusTax() - 1);
                    }
                    Position position = positionMap.get("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                    espt.setNamaJabatan(position != null ? position.getPosition() : "");

                    EmpCategory empCategory = categoryMap.get("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));

                    boolean WNI = true;
                    if (empCategory == null || empCategory.getCategoryType() != PstEmpCategory.CATEGORY_ASING) {
                        espt.setWPLuarNegeri("N");
                    } else {
                        espt.setWPLuarNegeri("Y");
                        WNI = false;
                    }

                    Negara negara = countryMap.get("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_ADDR_COUNTRY_ID]));
                    espt.setKodeNegara("" + (negara != null ? negara.getNmNegara() : ""));

                    espt.setKodePajak(TaxCalculator.getKodePajak(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_TYPE_FOR_TAX])));
                    long employeeId = rs.getLong("EMPLOYEE_OID");

                    String whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_GAJI;
                    double gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin); // gaji tetap dari awal tahun sampai periode sebelumnya, dalam tahun yg sama               
                    espt.setJumlah_1(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_TUNJ_PPH;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_2(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_TUNJ_LAIN_LEMBUR;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_3(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_HONOR;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_4(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_PREMI_ASURANSI;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_5(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_NATURA;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_6(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_TANTIEM_BONUS_THR;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_7(gajiYearToPeriod);

                    espt.setJumlah_8(espt.getJumlah_1() + espt.getJumlah_2() + espt.getJumlah_3() + espt.getJumlah_4() + espt.getJumlah_5() + espt.getJumlah_6() + espt.getJumlah_7());
                    if (clientName.equals("BOROBUDUR")){
                    espt.setJumlah_9(espt.getJumlah_8()*TaxCalculator.BIAYA_JABATAN_PERSEN);
                    } else {
                    espt.setJumlah_9(TaxCalculator.getBiayaJabatanAnnual(1, payPeriod.getEndDate().getMonth() + 1, employeeId, espt.getJumlah_8(), pajak, WNI));
                    }
                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_IURAN_PENSIUN_THT_JHT;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    if (clientName.equals("BOROBUDUR")){
                    espt.setJumlah_10(Math.abs(gajiYearToPeriod));
                    } else {  espt.setJumlah_10(Math.abs(gajiYearToPeriod)); }
                    espt.setJumlah_11(espt.getJumlah_9() + espt.getJumlah_10());
                    espt.setJumlah_12(espt.getJumlah_8() - espt.getJumlah_11());

                    espt.setJumlah_13(0); // penghasilan neto masa sebelumnya untuk karyawan yang pindah cabang mesti dihitung dari A1 cabang sebelumnya.
                    espt.setJumlah_14(espt.getJumlah_12() + espt.getJumlah_13());

                    espt.setJumlah_15(TaxCalculator.getPTKPSetahun(marital.getMaritalStatusTax(), pajak, payPeriod.getEndDate(), WNI, 1, payPeriod.getEndDate().getMonth() + 1));
                    if (clientName.equals("BOROBUDUR")){
                        double jum16 = (espt.getJumlah_14() - espt.getJumlah_15());
                        if (jum16 > 1000){ //pembulatan ribuan ke bawah priska 20151212
                            String sjum16 = Formater.formatNumberVer1(jum16, "#,###");
                            sjum16 = sjum16.substring(0, (sjum16.length()-3)) + "000";
                            sjum16 = sjum16.replace(",", "");
                            try { jum16 = Double.parseDouble(sjum16); } catch (Exception e) { }
                        } else if (jum16<0){
                            jum16=0;
                        }    
                    espt.setJumlah_16(jum16);
                    } else {
                    espt.setJumlah_16(espt.getJumlah_14() + espt.getJumlah_15());
                    } 
                    
                    
                    //espt.setJumlah_16(espt.getJumlah_14() - espt.getJumlah_15());
                    //espt.setJumlah_17(TaxCalculator.hitungTarifPPH21(espt.getJumlah_16(), pajak));

                     String npwpS = rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_NPWP]);
                    if ((npwpS.equals("0")) || (npwpS.equals(""))){
                        npwpS="000000000000000";
                    }
                    espt.setJumlah_17(TaxCalculator.hitungTarifPPH21(espt.getJumlah_16(), pajak, npwpS));
                    
                    
                    espt.setJumlah_18(0); //PPH21 yang telah dipotong di masa sebelumnya untuk karyawan yang pindah cabang mesti dihitung dari A1 cabang sebelumnya.
                    espt.setJumlah_19(espt.getJumlah_17() - espt.getJumlah_18());
                    double nilai = PstPayComponent.getPPH21OneYears(employeeId, periodId);
                    espt.setJumlah_20(nilai);
                    
                    Employee taxApprover = null;
                    long divId = divId_now == 0 ? divId_his : divId_now ;
                    if(mapDivIdEmployee!=null){   
                        taxApprover = mapDivIdEmployee.get(""+ divId);
                    }
                    
                    if(taxApprover==null ){
                        Division divTemp = divisonMap.get(""+divId);
                        if(divTemp!=null && divTemp.getOID()!=0 && divTemp.getDivisionTypeId()!=0){
                            DivisionType divType = divTypeMap.get(""+divTemp.getDivisionTypeId());
                            if(divType.getGroupType()!= PstDivisionType.TYPE_BRANCH_OF_COMPANY){
                                taxApprover = empPemotongPusat;
                            }
                        }
                    }
                    
                    if (taxApprover==null && empPemotongPusat!=null) {
                        taxApprover=empPemotongPusat;
                    }
                    
                    if (taxApprover==null && pajak != null) {
                        espt.setNPWPPemotong(pajak.getNPWPPemotong());
                        espt.setNamaPemotong(pajak.getNamaPemotong());
                        espt.setTanggalBuktiPotong(pajak.getTanggalBuktiPotong());
                    }else{
                        espt.setNPWPPemotong(taxApprover.getNpwp());
                        espt.setNamaPemotong(taxApprover.getFullName());
                        espt.setTanggalBuktiPotong(pajak.getTanggalBuktiPotong());                        
                    }
                    
                   //sementara di burubudur 20151212
                    espt.setNPWPPemotong("472316280013000");
                    espt.setNamaPemotong("DJONI");
                    Division division = divisonMap.get(""+divId);
                    espt.setDivision( division !=null ? division.getDivision() :"");
                            
                    list.add(espt);
                } catch (Exception exc) {
                    SrcEsptA1 espt = new SrcEsptA1();
                    espt.setNIK(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    espt.setNama(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    espt.setNomorBuktiPotong(exc.toString());
                    list.add(espt);
                }
            }

            return list;
        } catch (Exception e) {
            System.out.println("\t Exception on  search espt : " + e);
            throw new Exception("" + e);
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    
    
    
        /**
     * @author PRISKA
    
     * Untuk periode akhir tahun : desember Yang dipilih semua karyawan dengan
     * pembedaan di PPH sebelumnya ( untuk karyawan yang pindah cabang )
     * @param periodId
     * @param divisionId
     * @param departmentId
     * @param sectionId
     * @param inPPHBrutto
     * @param inPPHCodes
     * @return
     */
    
    
    public static Vector<SrcEsptA1> getListEsptA1MonthTestAll(Pajak pajak, long periodId, long divisionId, long departmentId, long sectionId, String inPPHBrutto, String inPPHCodes) throws Exception {

        Vector list = new Vector();
        //////////////////////////
        Vector periodList = PstPaySlip.getYearPeriodListToThisPeriod(periodId);
        String periodeIdOneYears = "";
        PayPeriod payPeriodFirst =new PayPeriod();
        if (periodList.size() != 0){
        for (int x = 0 ; x<periodList.size() ; x++){
            PayPeriod payPeriod = (PayPeriod) periodList.get(x);
            periodeIdOneYears = periodeIdOneYears + payPeriod.getOID()+",";
        }
        payPeriodFirst = (PayPeriod) periodList.get(0);
        periodeIdOneYears =periodeIdOneYears.substring(0, periodeIdOneYears.length()-1);
        }
        DBResultSet dbrs = null;
        int withoutDH = 0;
        String clientName ="";
        try {
             clientName = com.dimata.system.entity.PstSystemProperty.getValueByName("CLIENT_NAME");
        } catch (Exception ex) {
            System.out.println("Execption CLIENT_NAME " + ex);
        }
        
        try {
            withoutDH = Integer.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("SAL_LEVEL_WITHOUT_DH"));
        } catch (Exception e) {
            System.out.printf("VALUE_NOTDC TIDAK DI SET?");
        }
        try {
            String tblEmp = PstEmployee.TBL_HR_EMPLOYEE;
            String sql = "SELECT DISTINCT(hr_employee.EMPLOYEE_ID) AS EMPLOYEE_OID, pay_period.PERIOD, pay_period.PERIOD_ID, hr_employee.FULL_NAME"
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_NPWP]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_INDENT_CARD_NR]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_TAX_MARITAL_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_COUNTRY_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE];
            sql += ", hr_emp_category.TYPE_FOR_TAX, hr_employee.`DIVISION_ID`, hr_employee.`DEPARTMENT_ID`, 0 AS HIS_DIV_ID,0 AS HIS_DEP_ID, NULL AS WORK_FROM, NULL  AS WORK_TO " + " FROM hr_employee ";
            sql += " INNER JOIN hr_emp_category ON (hr_emp_category.EMP_CATEGORY_ID=hr_employee.EMP_CATEGORY_ID AND hr_employee.EMP_CATEGORY_ID NOT IN (\"11002\"))";
            sql += " INNER JOIN pay_slip ON pay_slip.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";

            if (withoutDH == 1) {
                sql += " INNER JOIN pay_emp_level  ON (hr_employee.EMPLOYEE_ID = pay_emp_level.EMPLOYEE_ID AND pay_emp_level.`LEVEL_CODE` NOT LIKE '%-DH%') ";

            }

            PayPeriod payPeriod = null;
            try {
                payPeriod = PstPayPeriod.fetchExc(periodId);
                if (payPeriod == null || payPeriod.getOID() == 0) {
                    throw new Exception("Payroll Period can't not be get with id=" + periodId, new Throwable());
                }
            } catch (Exception exc) {
                throw new Exception("Payroll Period can't not be get with id=" + periodId, exc);
            }
             if ((payPeriod.getEndDate().getMonth() + 1) != 12) {
                sql += " WHERE pay_slip.PERIOD_ID IN (" + periodeIdOneYears+ ")";
            } else {
                sql += " WHERE pay_slip.PERIOD_ID IN (" + periodeIdOneYears + " ) ";
            }
            if (divisionId != 0) {
                sql += " AND hr_employee.DIVISION_ID=" + divisionId + " ";
            }
            if (departmentId != 0) {
                sql += " AND hr_employee.DEPARTMENT_ID=" + departmentId + " ";
            }
            if (sectionId != 0) {
                sql += " AND hr_employee.SECTION_ID=" + sectionId + " ";
            }
           
             //sql = sql + " AND `hr_employee`.`PAYROLL_GROUP` = 11001 ";
             //sql = sql + " AND `hr_employee`.`EMPLOYEE_NUM` = 2646 ";
             //sql = sql + " AND `hr_employee`.`EMPLOYEE_NUM` IN (1215,1513,1528,1535) ";
            sql = sql + "  GROUP BY EMPLOYEE_OID ";
            sql = sql + "  ORDER BY `EMPLOYEE_NUM`  ASC ";
            
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Hashtable<String, Marital> maritalMap = PstMarital.listMap(0, 0, "", "");
            Hashtable<String, Position> positionMap = PstPosition.listMap(0, 0, "", "");
            Hashtable<String, EmpCategory> categoryMap = PstEmpCategory.listMap(0, 0, "", "");
            Hashtable<String, Negara> countryMap = PstNegara.listMap(0, 0, "", "");
            Hashtable<String, Division> divisonMap = PstDivision.listMap(0, 0, "", "");
            Hashtable<String, Department> departmentMap = PstDepartment.listMap(0, 0, "", "", "");
            Hashtable<String, DivisionType> divTypeMap = PstDivisionType.listMap(0, 0, "", "");
            Employee empPemotongPusat = null;
            Hashtable<String, Employee> mapDivIdEmployee = null;

            try {
                String strPemotongPusat = PstSystemProperty.getValueByNameWithStringNull("TAX_A1_POSISI_PEMOTONG_PUSAT");
                String strPemotongCabang = PstSystemProperty.getValueByNameWithStringNull("TAX_A1_POSISI_PEMOTONG_CABANG");

                Vector vPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_POSITION] + "=\"" + strPemotongPusat + "\"", "");
                Position posPemotongPusat = vPos != null && vPos.size() > 0 ? (Position) vPos.get(0) : null;

                vPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_POSITION] + "=\"" + strPemotongCabang + "\"", "");
                Position posPemotongCabang = vPos != null && vPos.size() > 0 ? (Position) vPos.get(0) : null;

                Vector vHistory = null;

                if (posPemotongPusat != null && posPemotongPusat.getOID() != 0) {
                    vHistory = PstCareerPath.list(0, 1, PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID] + "=" + posPemotongPusat.getOID() + " AND "
                            + "( \"" + Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd 00:00:00") + "\" BETWEEN " + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM] + " AND "
                            + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO], "");

                    if (vHistory == null || vHistory.size() < 1) {
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongPusat.getOID(), "");
                        empPemotongPusat = vEmployee != null && vEmployee.size() > 0 ? (Employee) vEmployee.get(0) : null;
                    } else {
                        CareerPath carPath = (CareerPath) vHistory.get(0);
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongPusat.getOID(), "");
                        empPemotongPusat = (Employee) vEmployee.get(0);
                    }
                }

                if (posPemotongCabang != null && posPemotongCabang.getOID() != 0) {
                    vHistory = PstCareerPath.list(0, 0, PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID] + "=" + posPemotongCabang.getOID() + " AND "
                            + "( \"" + Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd 00:00:00") + "\" BETWEEN " + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM] + " AND "
                            + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO], "");

                    if (vHistory == null || vHistory.size() < 1) {
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongCabang.getOID(), "");
                        if( vEmployee != null && vEmployee.size() > 0){
                            mapDivIdEmployee = new Hashtable<String, Employee>();
                            for(int idx=0;idx < vEmployee.size() ; idx ++ ){
                                Employee emp = (Employee) vEmployee.get(idx);
                                mapDivIdEmployee.put(""+emp.getDivisionId(), emp);
                            }
                        }
                    } else {
                        CareerPath carPath = (CareerPath) vHistory.get(0);
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongCabang.getOID(), "");
                         if( vEmployee != null && vEmployee.size() > 0){
                            mapDivIdEmployee = new Hashtable<String, Employee>();
                            for(int idx=0;idx < vEmployee.size() ; idx ++ ){
                                Employee emp = (Employee) vEmployee.get(idx);
                                mapDivIdEmployee.put(""+emp.getDivisionId(), emp);
                            }
                        }
                    }
                }
                
                if(empPemotongPusat!=null){
                    if(mapDivIdEmployee==null){
                        mapDivIdEmployee = new Hashtable<String, Employee>();
                    }
                    mapDivIdEmployee.put(""+empPemotongPusat.getDivisionId(), empPemotongPusat);
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
            int x = 0;
            while (rs.next()) {
                try {
                    long divId_now = rs.getLong("DIVISION_ID");
                    long divId_his = rs.getLong("HIS_DIV_ID");
                    long empId = rs.getLong("EMPLOYEE_OID");
                    Date resignDate = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]);
                    if (false) {
                        Division divNow = divisonMap.get("" + divId_now);
                        Division divHis = divisonMap.get("" + divId_his);
                        if ((divNow != null && divHis != null && divHis.getNpwp() != null && divNow.getNpwp() != null && divNow.getNpwp().equalsIgnoreCase(divHis.getNpwp()))
                                || (divNow != null && divHis == null)) {
                            continue;
                        }
                        
                        long depId_now = rs.getLong("DEPARTMENT_ID");
                        long depId_his = rs.getLong("HIS_DEP_ID");
                        Department depNow = departmentMap.get("" + depId_now);
                        Department depHis = departmentMap.get("" + depId_his);
                        if ((depNow != null && depHis != null && depHis.getNpwp() != null && depNow.getNpwp() != null && depNow.getNpwp().equalsIgnoreCase(depHis.getNpwp()))
                                || (depNow != null && divHis == null)) {
                            continue;
                        }
                    }

                    SrcEsptA1 espt = new SrcEsptA1();
                    espt.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                    espt.setResignedDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                    espt.setMasaPajak(payPeriod.getEndDate().getMonth() + 1);
                    espt.setTahunPajak(payPeriod.getEndDate());
                    espt.setPembetulan(0);
                    
                    
                    Date dt = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]);
                    if (dt.getYear() < payPeriod.getEndDate().getYear()) {
                        espt.setMasaPerolehanAwal(1);
                    } else if (dt.getYear() == payPeriod.getEndDate().getYear()) {
                        //seharusnya mengambil payslip periode pertamanya
                        espt.setMasaPerolehanAwal(dt.getMonth() + 1);
                        Date date = new Date();
                        try {
                            date = PstPaySlip.getFirstPayslipDate(empId, ""+(payPeriod.getEndDate().getYear()+1900));
                            if (date != null){
                                espt.setMasaPerolehanAwal(date.getMonth() + 1);
                            }
                        }catch (Exception e){} 
                        
                        
                    } else {
                        continue; // if commencing after pay period then the employee doesnot include in the repoert
                    }
                    
                    
                    int massaPerolehan = 0;
                    PayPeriod payPeriodEmp = new PayPeriod();
                    try {
                        payPeriodEmp = PstPayPeriod.getPayPeriodBySelectedDate(resignDate);
                    }catch (Exception e){ }
                    if (payPeriodEmp == null ){
                        massaPerolehan = payPeriod.getEndDate().getMonth() + 1;
                    } else {
                        massaPerolehan = payPeriodEmp.getEndDate().getMonth() + 1;
                        //seharusnya mengambil payslip periode akhir
                        Date date = new Date();
                        try {
                            date = PstPaySlip.getEndPayslipDate(empId, ""+(payPeriod.getEndDate().getYear()+1900));
                            if (date != null){
                                massaPerolehan = date.getMonth() + 1;
                            }
                        }catch (Exception e){} 
                    }
                    espt.setMasaPerolehanAkhir(massaPerolehan);
                    
                    
                    x=x+1;
                    String tahunPajak = ""+(espt.getTahunPajak().getYear() + 1900);
                    String noUrut7digit = "0000000"+(x);
                    
                    String masaPerolehanAkhir = "00"+ espt.getMasaPerolehanAkhir();
                    espt.setNomorBuktiPotong("1.1-"+masaPerolehanAkhir.substring((masaPerolehanAkhir.length()-2),masaPerolehanAkhir.length())+"."+(tahunPajak.substring(2, tahunPajak.length())+"-"+noUrut7digit.substring((noUrut7digit.length()-7),noUrut7digit.length()) ));
                   // espt.setNomorBuktiPotong("");
                    
                    String npwpS = rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_NPWP]);
                    if ((npwpS.equals("0")) || (npwpS.equals(""))){
                        npwpS="000000000000000";
                    }
                    espt.setNPWP(npwpS);
                    espt.setNIK(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_INDENT_CARD_NR]));
                    espt.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    espt.setNama(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    espt.setAlamat(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                    int sex = rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]);
                    espt.setJenisKelamin(sex == PstEmployee.MALE ? "M" : "F");

                    long taxStatus = rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_TAX_MARITAL_ID]);
                    Marital marital = maritalMap.get("" + taxStatus);
                    if (marital.getMaritalStatusTax() == TaxCalculator.STATUS_DIRI_SENDIRI) {
                        espt.setStatusPTKP("TK");
                        espt.setJumlahTanggungan(0);
                    } else {
                        espt.setStatusPTKP("K");
                        espt.setJumlahTanggungan(marital.getMaritalStatusTax() - 1);
                    }
                    Position position = positionMap.get("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                    espt.setNamaJabatan(position != null ? position.getPosition() : "");

                    EmpCategory empCategory = categoryMap.get("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));

                    boolean WNI = true;
                    if (empCategory == null || empCategory.getCategoryType() != PstEmpCategory.CATEGORY_ASING) {
                        espt.setWPLuarNegeri("N");
                    } else {
                        espt.setWPLuarNegeri("Y");
                        WNI = false;
                    }

                    Negara negara = countryMap.get("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_ADDR_COUNTRY_ID]));
                    espt.setKodeNegara("" + (negara != null ? negara.getNmNegara() : ""));

                    espt.setKodePajak(TaxCalculator.getKodePajak(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_TYPE_FOR_TAX])));
                    
                    
                    if (clientName.equals("BOROBUDUR")){
                    espt.setKodeNegara("");    
                    espt.setKodePajak("21-100-01");
                    } 
                    
                    long employeeId = rs.getLong("EMPLOYEE_OID");

                    String whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_GAJI;
                    double gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin); // gaji tetap dari awal tahun sampai periode sebelumnya, dalam tahun yg sama               
                    espt.setJumlah_1(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_TUNJ_PPH;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_2(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_TUNJ_LAIN_LEMBUR;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_3(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_HONOR;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_4(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_PREMI_ASURANSI;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_5(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_NATURA;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_6(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_TANTIEM_BONUS_THR;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_7(gajiYearToPeriod);

                    espt.setJumlah_8(espt.getJumlah_1() + espt.getJumlah_2() + espt.getJumlah_3() + espt.getJumlah_4() + espt.getJumlah_5() + espt.getJumlah_6() + espt.getJumlah_7());
                    if (clientName.equals("BOROBUDUR")){
                       if ((espt.getJumlah_8()*TaxCalculator.BIAYA_JABATAN_PERSEN) >= 6000000   ){
                           espt.setJumlah_9(6000000);
                       } else {
                           espt.setJumlah_9(espt.getJumlah_8()*TaxCalculator.BIAYA_JABATAN_PERSEN);
                       }
                    
                    } else {
                    espt.setJumlah_9(TaxCalculator.getBiayaJabatanAnnual(1, payPeriod.getEndDate().getMonth() + 1, employeeId, espt.getJumlah_8(), pajak, WNI));
                    }
                    
                    int countJumlahPaySlip = PstPaySlip.getJumlahPayslip(empId, ""+(payPeriod.getEndDate().getYear()+1900));
                    double nilaiMaksimum = (countJumlahPaySlip * TaxCalculator.BIAYA_JABATAN_BULANAN);
                    if (espt.getJumlah_9() > nilaiMaksimum){
                        espt.setJumlah_9(nilaiMaksimum);
                    }
                    
                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_IURAN_PENSIUN_THT_JHT;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    if (clientName.equals("BOROBUDUR")){
                    espt.setJumlah_10(Math.abs(gajiYearToPeriod));
                    } else {  espt.setJumlah_10(Math.abs(gajiYearToPeriod)); }
                    espt.setJumlah_11(espt.getJumlah_9() + espt.getJumlah_10());
                    espt.setJumlah_12(espt.getJumlah_8() - espt.getJumlah_11());

                    espt.setJumlah_13(0); // penghasilan neto masa sebelumnya untuk karyawan yang pindah cabang mesti dihitung dari A1 cabang sebelumnya.
                    espt.setJumlah_14(espt.getJumlah_12() + espt.getJumlah_13());

                    espt.setJumlah_15(TaxCalculator.getPTKPSetahun(marital.getMaritalStatusTax(), pajak, payPeriod.getEndDate(), WNI, 1, payPeriod.getEndDate().getMonth() + 1));
                    if (clientName.equals("BOROBUDUR")){
                        try {
                        double jum16 = (espt.getJumlah_14() - espt.getJumlah_15());
                        if (jum16 > 1000){ //pembulatan ribuan ke bawah priska 20151212
                            String sjum16 = Formater.formatNumberVer1(jum16, "#,###");
                            sjum16 = sjum16.substring(0, (sjum16.length()-3)) + "000";
                            sjum16 = sjum16.replace(",", "");
                            try { jum16 = Double.parseDouble(sjum16); } catch (Exception e) { }
                        } else if (jum16<0){
                            jum16=0;
                        }    
                    espt.setJumlah_16(jum16);
                        }catch (Exception e) {}
                        } else {
                    espt.setJumlah_16(espt.getJumlah_14() + espt.getJumlah_15());
                    } 
                    
                    
                    //espt.setJumlah_16(espt.getJumlah_14() - espt.getJumlah_15());
                    espt.setJumlah_17(TaxCalculator.hitungTarifPPH21(espt.getJumlah_16(), pajak, npwpS));

                    espt.setJumlah_18(0); //PPH21 yang telah dipotong di masa sebelumnya untuk karyawan yang pindah cabang mesti dihitung dari A1 cabang sebelumnya.
                    espt.setJumlah_19(espt.getJumlah_17() - espt.getJumlah_18());
                    double nilai = PstPayComponent.getPPH21OneYears(employeeId, periodId);
                    espt.setJumlah_20(nilai);
                    
                    Employee taxApprover = null;
                    long divId = divId_now == 0 ? divId_his : divId_now ;
                    if(mapDivIdEmployee!=null){   
                        taxApprover = mapDivIdEmployee.get(""+ divId);
                    }
                    
                    if(taxApprover==null ){
                        Division divTemp = divisonMap.get(""+divId);
                        if(divTemp!=null && divTemp.getOID()!=0 && divTemp.getDivisionTypeId()!=0){
                            DivisionType divType = divTypeMap.get(""+divTemp.getDivisionTypeId());
                            if(divType.getGroupType()!= PstDivisionType.TYPE_BRANCH_OF_COMPANY){
                                taxApprover = empPemotongPusat;
                            }
                        }
                    }
                    
                    if (taxApprover==null && empPemotongPusat!=null) {
                        taxApprover=empPemotongPusat;
                    }
                    
                    if (taxApprover==null && pajak != null) {
                        espt.setNPWPPemotong(pajak.getNPWPPemotong());
                        espt.setNamaPemotong(pajak.getNamaPemotong());
                        espt.setTanggalBuktiPotong(pajak.getTanggalBuktiPotong());
                    }else{
                        espt.setNPWPPemotong(taxApprover.getNpwp());
                        espt.setNamaPemotong(taxApprover.getFullName());
                        espt.setTanggalBuktiPotong(pajak.getTanggalBuktiPotong());                        
                    }
                    
                   //sementara di burubudur 20151212
                    espt.setNPWPPemotong("472316280013000");
                    espt.setNamaPemotong("Djoni");
                    Division division = divisonMap.get(""+divId);
                    espt.setDivision( division !=null ? division.getDivision() :"");
                            
                    list.add(espt);
                } catch (Exception exc) {
                    SrcEsptA1 espt = new SrcEsptA1();
                    espt.setNIK(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    espt.setNama(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    espt.setNomorBuktiPotong(exc.toString());
                    list.add(espt);
                }
            }

            return list;
        } catch (Exception e) {
            System.out.println("\t Exception on  search espt : " + e);
            throw new Exception("" + e);
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    
    
    public static Vector<SrcEsptA1> getListEsptA1AnnualTaxDifferent(Pajak pajak, long periodId, long divisionId, long departmentId, long sectionId, String inPPHBrutto, String inPPHCodes) throws Exception {

        Vector list = new Vector();
        //////////////////////////
        Vector periodList = PstPaySlip.getYearPeriodListToThisPeriod(periodId);
        String periodeIdOneYears = "";
        PayPeriod payPeriodFirst =new PayPeriod();
        if (periodList.size() != 0){
        for (int x = 0 ; x<periodList.size() ; x++){
            PayPeriod payPeriod = (PayPeriod) periodList.get(x);
            periodeIdOneYears = periodeIdOneYears + payPeriod.getOID()+",";
        }
        payPeriodFirst = (PayPeriod) periodList.get(0);
        periodeIdOneYears =periodeIdOneYears.substring(0, periodeIdOneYears.length()-1);
        }
        DBResultSet dbrs = null;
        int withoutDH = 0;
        String clientName ="";
        try {
             clientName = com.dimata.system.entity.PstSystemProperty.getValueByName("CLIENT_NAME");
        } catch (Exception ex) {
            System.out.println("Execption CLIENT_NAME " + ex);
        }
        
        try {
            withoutDH = Integer.valueOf(com.dimata.system.entity.PstSystemProperty.getValueByName("SAL_LEVEL_WITHOUT_DH"));
        } catch (Exception e) {
            System.out.printf("VALUE_NOTDC TIDAK DI SET?");
        }
        try {
            String tblEmp = PstEmployee.TBL_HR_EMPLOYEE;
            String sql = "SELECT DISTINCT(hr_employee.EMPLOYEE_ID) AS EMPLOYEE_OID, pay_period.PERIOD, pay_period.PERIOD_ID, hr_employee.FULL_NAME"
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_NPWP]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_INDENT_CARD_NR]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_TAX_MARITAL_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_COUNTRY_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + "," + tblEmp + "." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE];
            sql += ", hr_emp_category.TYPE_FOR_TAX, hr_employee.`DIVISION_ID`, hr_employee.`DEPARTMENT_ID`, 0 AS HIS_DIV_ID,0 AS HIS_DEP_ID, NULL AS WORK_FROM, NULL  AS WORK_TO " + " FROM hr_employee ";
            sql += " INNER JOIN hr_emp_category ON (hr_emp_category.EMP_CATEGORY_ID=hr_employee.EMP_CATEGORY_ID AND hr_employee.EMP_CATEGORY_ID NOT IN (\"11002\"))";
            sql += " INNER JOIN pay_slip ON pay_slip.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";

            if (withoutDH == 1) {
                sql += " INNER JOIN pay_emp_level  ON (hr_employee.EMPLOYEE_ID = pay_emp_level.EMPLOYEE_ID AND pay_emp_level.`LEVEL_CODE` NOT LIKE '%-DH%') ";

            }

            PayPeriod payPeriod = null;
            try {
                payPeriod = PstPayPeriod.fetchExc(periodId);
                if (payPeriod == null || payPeriod.getOID() == 0) {
                    throw new Exception("Payroll Period can't not be get with id=" + periodId, new Throwable());
                }
            } catch (Exception exc) {
                throw new Exception("Payroll Period can't not be get with id=" + periodId, exc);
            }
             if ((payPeriod.getEndDate().getMonth() + 1) != 12) {
                sql += " WHERE pay_slip.PERIOD_ID IN (" + periodeIdOneYears+ ")";
            } else {
                sql += " WHERE pay_slip.PERIOD_ID IN (" + periodeIdOneYears + " ) ";
            }
            if (divisionId != 0) {
                sql += " AND hr_employee.DIVISION_ID=" + divisionId + " ";
            }
            if (departmentId != 0) {
                sql += " AND hr_employee.DEPARTMENT_ID=" + departmentId + " ";
            }
            if (sectionId != 0) {
                sql += " AND hr_employee.SECTION_ID=" + sectionId + " ";
            }
           
             sql = sql + " AND `hr_employee`.`PAYROLL_GROUP` = 11001 ";
            // sql = sql + " AND `hr_employee`.`EMPLOYEE_NUM` IN (1292,1324) ";
            sql = sql + "  GROUP BY EMPLOYEE_OID ";
            sql = sql + "  ORDER BY `EMPLOYEE_NUM`  ASC ";
            
           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Hashtable<String, Marital> maritalMap = PstMarital.listMap(0, 0, "", "");
            Hashtable<String, Position> positionMap = PstPosition.listMap(0, 0, "", "");
            Hashtable<String, EmpCategory> categoryMap = PstEmpCategory.listMap(0, 0, "", "");
            Hashtable<String, Negara> countryMap = PstNegara.listMap(0, 0, "", "");
            Hashtable<String, Division> divisonMap = PstDivision.listMap(0, 0, "", "");
            Hashtable<String, Department> departmentMap = PstDepartment.listMap(0, 0, "", "", "");
            Hashtable<String, DivisionType> divTypeMap = PstDivisionType.listMap(0, 0, "", "");
            Employee empPemotongPusat = null;
            Hashtable<String, Employee> mapDivIdEmployee = null;

            try {
                String strPemotongPusat = PstSystemProperty.getValueByNameWithStringNull("TAX_A1_POSISI_PEMOTONG_PUSAT");
                String strPemotongCabang = PstSystemProperty.getValueByNameWithStringNull("TAX_A1_POSISI_PEMOTONG_CABANG");

                Vector vPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_POSITION] + "=\"" + strPemotongPusat + "\"", "");
                Position posPemotongPusat = vPos != null && vPos.size() > 0 ? (Position) vPos.get(0) : null;

                vPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_POSITION] + "=\"" + strPemotongCabang + "\"", "");
                Position posPemotongCabang = vPos != null && vPos.size() > 0 ? (Position) vPos.get(0) : null;

                Vector vHistory = null;

                if (posPemotongPusat != null && posPemotongPusat.getOID() != 0) {
                    vHistory = PstCareerPath.list(0, 1, PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID] + "=" + posPemotongPusat.getOID() + " AND "
                            + "( \"" + Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd 00:00:00") + "\" BETWEEN " + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM] + " AND "
                            + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO], "");

                    if (vHistory == null || vHistory.size() < 1) {
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongPusat.getOID(), "");
                        empPemotongPusat = vEmployee != null && vEmployee.size() > 0 ? (Employee) vEmployee.get(0) : null;
                    } else {
                        CareerPath carPath = (CareerPath) vHistory.get(0);
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongPusat.getOID(), "");
                        empPemotongPusat = (Employee) vEmployee.get(0);
                    }
                }

                if (posPemotongCabang != null && posPemotongCabang.getOID() != 0) {
                    vHistory = PstCareerPath.list(0, 0, PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID] + "=" + posPemotongCabang.getOID() + " AND "
                            + "( \"" + Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd 00:00:00") + "\" BETWEEN " + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM] + " AND "
                            + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO], "");

                    if (vHistory == null || vHistory.size() < 1) {
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongCabang.getOID(), "");
                        if( vEmployee != null && vEmployee.size() > 0){
                            mapDivIdEmployee = new Hashtable<String, Employee>();
                            for(int idx=0;idx < vEmployee.size() ; idx ++ ){
                                Employee emp = (Employee) vEmployee.get(idx);
                                mapDivIdEmployee.put(""+emp.getDivisionId(), emp);
                            }
                        }
                    } else {
                        CareerPath carPath = (CareerPath) vHistory.get(0);
                        Vector vEmployee = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posPemotongCabang.getOID(), "");
                         if( vEmployee != null && vEmployee.size() > 0){
                            mapDivIdEmployee = new Hashtable<String, Employee>();
                            for(int idx=0;idx < vEmployee.size() ; idx ++ ){
                                Employee emp = (Employee) vEmployee.get(idx);
                                mapDivIdEmployee.put(""+emp.getDivisionId(), emp);
                            }
                        }
                    }
                }
                
                if(empPemotongPusat!=null){
                    if(mapDivIdEmployee==null){
                        mapDivIdEmployee = new Hashtable<String, Employee>();
                    }
                    mapDivIdEmployee.put(""+empPemotongPusat.getDivisionId(), empPemotongPusat);
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
            int x = 0;
            while (rs.next()) {
                try {
                    long divId_now = rs.getLong("DIVISION_ID");
                    long divId_his = rs.getLong("HIS_DIV_ID");
                    long empId = rs.getLong("EMPLOYEE_OID");
                    Date resignDate = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]);
                    if (false) {
                        Division divNow = divisonMap.get("" + divId_now);
                        Division divHis = divisonMap.get("" + divId_his);
                        if ((divNow != null && divHis != null && divHis.getNpwp() != null && divNow.getNpwp() != null && divNow.getNpwp().equalsIgnoreCase(divHis.getNpwp()))
                                || (divNow != null && divHis == null)) {
                            continue;
                        }
                        
                        long depId_now = rs.getLong("DEPARTMENT_ID");
                        long depId_his = rs.getLong("HIS_DEP_ID");
                        Department depNow = departmentMap.get("" + depId_now);
                        Department depHis = departmentMap.get("" + depId_his);
                        if ((depNow != null && depHis != null && depHis.getNpwp() != null && depNow.getNpwp() != null && depNow.getNpwp().equalsIgnoreCase(depHis.getNpwp()))
                                || (depNow != null && divHis == null)) {
                            continue;
                        }
                    }

                    SrcEsptA1 espt = new SrcEsptA1();
                    espt.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                    espt.setResignedDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                    espt.setMasaPajak(payPeriod.getEndDate().getMonth() + 1);
                    espt.setTahunPajak(payPeriod.getEndDate());
                    espt.setPembetulan(0);
                    
                    
                    Date dt = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]);
                    if (dt.getYear() < payPeriod.getEndDate().getYear()) {
                        espt.setMasaPerolehanAwal(1);
                    } else if (dt.getYear() == payPeriod.getEndDate().getYear()) {
                        //seharusnya mengambil payslip periode pertamanya
                        espt.setMasaPerolehanAwal(dt.getMonth() + 1);
                        Date date = new Date();
                        try {
                            date = PstPaySlip.getFirstPayslipDate(empId, ""+(payPeriod.getEndDate().getYear()+1900));
                            if (date != null){
                                espt.setMasaPerolehanAwal(date.getMonth() + 1);
                            }
                        }catch (Exception e){} 
                        
                        
                    } else {
                        continue; // if commencing after pay period then the employee doesnot include in the repoert
                    }
                    
                    
                    int massaPerolehan = 0;
                    PayPeriod payPeriodEmp = new PayPeriod();
                    try {
                        payPeriodEmp = PstPayPeriod.getPayPeriodBySelectedDate(resignDate);
                    }catch (Exception e){ }
                    if (payPeriodEmp == null ){
                        massaPerolehan = payPeriod.getEndDate().getMonth() + 1;
                    } else {
                        massaPerolehan = payPeriodEmp.getEndDate().getMonth() + 1;
                        //seharusnya mengambil payslip periode akhir
                        Date date = new Date();
                        try {
                            date = PstPaySlip.getEndPayslipDate(empId, ""+(payPeriod.getEndDate().getYear()+1900));
                            if (date != null){
                                massaPerolehan = date.getMonth() + 1;
                            }
                        }catch (Exception e){} 
                    }
                    espt.setMasaPerolehanAkhir(massaPerolehan);
                    
                    
                    x=x+1;
                    String tahunPajak = ""+(espt.getTahunPajak().getYear() + 1900);
                    String noUrut7digit = "0000000"+(x);
                    
                    String masaPerolehanAkhir = "00"+ espt.getMasaPerolehanAkhir();
                    espt.setNomorBuktiPotong("1.1-"+masaPerolehanAkhir.substring((masaPerolehanAkhir.length()-2),masaPerolehanAkhir.length())+"."+(tahunPajak.substring(2, tahunPajak.length())+"-"+noUrut7digit.substring((noUrut7digit.length()-7),noUrut7digit.length()) ));
                   // espt.setNomorBuktiPotong("");
                    
                    String npwpS = rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_NPWP]);
                    if ((npwpS.equals("0")) || (npwpS.equals(""))){
                        npwpS="000000000000000";
                    }
                    espt.setNPWP(npwpS);
                    espt.setNIK(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_INDENT_CARD_NR]));
                    espt.setEmpNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    espt.setNama(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    espt.setAlamat(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                    int sex = rs.getInt(PstEmployee.fieldNames[PstEmployee.FLD_SEX]);
                    espt.setJenisKelamin(sex == PstEmployee.MALE ? "M" : "F");

                    long taxStatus = rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_TAX_MARITAL_ID]);
                    Marital marital = maritalMap.get("" + taxStatus);
                    if (marital.getMaritalStatusTax() == TaxCalculator.STATUS_DIRI_SENDIRI) {
                        espt.setStatusPTKP("TK");
                        espt.setJumlahTanggungan(0);
                    } else {
                        espt.setStatusPTKP("K");
                        espt.setJumlahTanggungan(marital.getMaritalStatusTax() - 1);
                    }
                    Position position = positionMap.get("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                    espt.setNamaJabatan(position != null ? position.getPosition() : "");

                    EmpCategory empCategory = categoryMap.get("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));

                    boolean WNI = true;
                    if (empCategory == null || empCategory.getCategoryType() != PstEmpCategory.CATEGORY_ASING) {
                        espt.setWPLuarNegeri("N");
                    } else {
                        espt.setWPLuarNegeri("Y");
                        WNI = false;
                    }

                    Negara negara = countryMap.get("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_ADDR_COUNTRY_ID]));
                    espt.setKodeNegara("" + (negara != null ? negara.getNmNegara() : ""));

                    espt.setKodePajak(TaxCalculator.getKodePajak(rs.getInt(PstEmpCategory.fieldNames[PstEmpCategory.FLD_TYPE_FOR_TAX])));
                    
                    
                    if (clientName.equals("BOROBUDUR")){
                    espt.setKodeNegara("");    
                    espt.setKodePajak("21-100-01");
                    } 
                    
                    long employeeId = rs.getLong("EMPLOYEE_OID");

                    String whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_GAJI;
                    double gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin); // gaji tetap dari awal tahun sampai periode sebelumnya, dalam tahun yg sama               
                    espt.setJumlah_1(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_TUNJ_PPH;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_2(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_TUNJ_LAIN_LEMBUR;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_3(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_HONOR;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_4(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_PREMI_ASURANSI;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_5(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_NATURA;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_6(gajiYearToPeriod);

                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_TANTIEM_BONUS_THR;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    espt.setJumlah_7(gajiYearToPeriod);

                    espt.setJumlah_8(espt.getJumlah_1() + espt.getJumlah_2() + espt.getJumlah_3() + espt.getJumlah_4() + espt.getJumlah_5() + espt.getJumlah_6() + espt.getJumlah_7());
                    if (clientName.equals("BOROBUDUR")){
                       if ((espt.getJumlah_8()*TaxCalculator.BIAYA_JABATAN_PERSEN) >= 6000000   ){
                           espt.setJumlah_9(6000000);
                       } else {
                           espt.setJumlah_9(espt.getJumlah_8()*TaxCalculator.BIAYA_JABATAN_PERSEN);
                       }
                    
                    } else {
                    espt.setJumlah_9(TaxCalculator.getBiayaJabatanAnnual(1, payPeriod.getEndDate().getMonth() + 1, employeeId, espt.getJumlah_8(), pajak, WNI));
                    }
                    
                    int countJumlahPaySlip = PstPaySlip.getJumlahPayslip(empId, ""+(payPeriod.getEndDate().getYear()+1900));
                    double nilaiMaksimum = (countJumlahPaySlip * TaxCalculator.BIAYA_JABATAN_BULANAN);
                    if (espt.getJumlah_9() > nilaiMaksimum){
                        espt.setJumlah_9(nilaiMaksimum);
                    }
                    
                    whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_RPT_GROUP] + " = " + PstPayComponent.TAX_RPT_IURAN_PENSIUN_THT_JHT;
                    gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId, whereGajiRutin);
                    if (clientName.equals("BOROBUDUR")){
                    espt.setJumlah_10(Math.abs(gajiYearToPeriod));
                    } else {  espt.setJumlah_10(gajiYearToPeriod); }
                    espt.setJumlah_11(espt.getJumlah_9() + espt.getJumlah_10());
                    espt.setJumlah_12(espt.getJumlah_8() - espt.getJumlah_11());

                    espt.setJumlah_13(0); // penghasilan neto masa sebelumnya untuk karyawan yang pindah cabang mesti dihitung dari A1 cabang sebelumnya.
                    espt.setJumlah_14(espt.getJumlah_12() + espt.getJumlah_13());

                    espt.setJumlah_15(TaxCalculator.getPTKPSetahun(marital.getMaritalStatusTax(), pajak, payPeriod.getEndDate(), WNI, 1, payPeriod.getEndDate().getMonth() + 1));
                    if (clientName.equals("BOROBUDUR")){
                        try {
                        double jum16 = (espt.getJumlah_14() - espt.getJumlah_15());
                        if (jum16 > 1000){ //pembulatan ribuan ke bawah priska 20151212
                            String sjum16 = Formater.formatNumberVer1(jum16, "#,###");
                            sjum16 = sjum16.substring(0, (sjum16.length()-3)) + "000";
                            sjum16 = sjum16.replace(",", "");
                            try { jum16 = Double.parseDouble(sjum16); } catch (Exception e) { }
                        } else if (jum16<0){
                            jum16=0;
                        }    
                    espt.setJumlah_16(jum16);
                        }catch (Exception e) {}
                        } else {
                    espt.setJumlah_16(espt.getJumlah_14() + espt.getJumlah_15());
                    } 
                    
                    
                    //espt.setJumlah_16(espt.getJumlah_14() - espt.getJumlah_15());
                    espt.setJumlah_17(TaxCalculator.hitungTarifPPH21(espt.getJumlah_16(), pajak, npwpS));

                    espt.setJumlah_18(0); //PPH21 yang telah dipotong di masa sebelumnya untuk karyawan yang pindah cabang mesti dihitung dari A1 cabang sebelumnya.
                    espt.setJumlah_19(espt.getJumlah_17() - espt.getJumlah_18());
                    double nilai = PstPayComponent.getPPH21OneYears(employeeId, periodId);
                    espt.setJumlah_20(nilai);
                    
                    Employee taxApprover = null;
                    long divId = divId_now == 0 ? divId_his : divId_now ;
                    if(mapDivIdEmployee!=null){   
                        taxApprover = mapDivIdEmployee.get(""+ divId);
                    }
                    
                    if(taxApprover==null ){
                        Division divTemp = divisonMap.get(""+divId);
                        if(divTemp!=null && divTemp.getOID()!=0 && divTemp.getDivisionTypeId()!=0){
                            DivisionType divType = divTypeMap.get(""+divTemp.getDivisionTypeId());
                            if(divType.getGroupType()!= PstDivisionType.TYPE_BRANCH_OF_COMPANY){
                                taxApprover = empPemotongPusat;
                            }
                        }
                    }
                    
                    if (taxApprover==null && empPemotongPusat!=null) {
                        taxApprover=empPemotongPusat;
                    }
                    
                    if (taxApprover==null && pajak != null) {
                        espt.setNPWPPemotong(pajak.getNPWPPemotong());
                        espt.setNamaPemotong(pajak.getNamaPemotong());
                        espt.setTanggalBuktiPotong(pajak.getTanggalBuktiPotong());
                    }else{
                        espt.setNPWPPemotong(taxApprover.getNpwp());
                        espt.setNamaPemotong(taxApprover.getFullName());
                        espt.setTanggalBuktiPotong(pajak.getTanggalBuktiPotong());                        
                    }
                    
                   //sementara di burubudur 20151212
                    espt.setNPWPPemotong("472316280013000");
                    espt.setNamaPemotong("Djoni");
                    Division division = divisonMap.get(""+divId);
                    espt.setDivision( division !=null ? division.getDivision() :"");
                    if ((espt.getJumlah_19() - espt.getJumlah_20()) !=0 ){
                    list.add(espt);
                    }
                } catch (Exception exc) {
                    SrcEsptA1 espt = new SrcEsptA1();
                    espt.setNIK(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    espt.setNama(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    espt.setNomorBuktiPotong(exc.toString());
                    list.add(espt);
                }
            }

            return list;
        } catch (Exception e) {
            System.out.println("\t Exception on  search espt : " + e);
            throw new Exception("" + e);
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
}
