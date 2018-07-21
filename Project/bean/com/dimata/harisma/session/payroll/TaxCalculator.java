/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.FamilyMember;
import com.dimata.harisma.entity.employee.FamilyMemberList;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.PstFamilyMember;
import com.dimata.harisma.entity.masterdata.Marital;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstMarital;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PaySlip;
import com.dimata.harisma.entity.payroll.PaySlipComp;
import com.dimata.harisma.entity.payroll.PstPayComponent;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.entity.payroll.PstPaySlipComp;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Date;
import org.apache.poi.ss.formula.functions.Match;

/**
 *
 * @author ktanjana // update by kartika 2014-11-10
 */
public class TaxCalculator {

    public static final int EMPLOYEE_TAX_TYPE_TO_BE_DEFINED = 0;
    public static final int EMPLOYEE_TAX_TYPE_PERMANENT = 1;
    public static final int EMPLOYEE_TAX_TYPE_NON_PERMANENT = 2;
    public static final int EMPLOYEE_TAX_TYPE_CONSULTANT = 3;
    public static final int EMPLOYEE_TAX_TYPE_PENERIMA_PENSIUN = 4;
    
    public static final String[] EMPLOYEE_TAX_TYPE = {"to be defined", "Permanent & Contract Employee", "Non Permanent Employee", "Consultant", "Receive Pension"};
    public static final int[] EMPLOYEE_TAX_TYPE_VALUE = {0, 1, 2, 3,4};
    
    public static String getKodePajak(int tipeKaryawan){
    switch(tipeKaryawan){
                case EMPLOYEE_TAX_TYPE_PERMANENT:
                    return "21-100-01";
                    
                case EMPLOYEE_TAX_TYPE_NON_PERMANENT:
                    return  "21-100-03";
                    
                case EMPLOYEE_TAX_TYPE_CONSULTANT:
                    return  "21-100-07";
                    
                case EMPLOYEE_TAX_TYPE_PENERIMA_PENSIUN:
                    return  "21-100-02";
                    
                default:
                    return  "00-000-00";
            }
    }
    
    public static Hashtable employeeTypeMap = null; // hashtable map : employee_category_id => EMPLOYEE_TAX_TYPE_VALUE

    public static String STATUS_PREFIX_SINGLE = "S";
    public static String STATUS_PREFIX_MARRIED = "M";

    public static final int STATUS_DIRI_SENDIRI = 0;
    public static final int STATUS_KAWIN = 1;
    public static final int STATUS_KAWIN_ANAK_1 = 2;
    public static final int STATUS_KAWIN_ANAK_2 = 3;
    public static final int STATUS_KAWIN_ANAK_3 = 4;
    public static final String[] STATUS_EMPLOYEE = {"Single", "Married", "Married w/ 1 child", "Married w/ 2 children", "Married w/ 3 or more children"};
    public static final String[] STATUS_EMPLOYEE_CODE = {"S", "M-0", "M-1", "M-2", "M-3"};
    public static final int[] STATUS_EMPLOYEE_IDX = {STATUS_DIRI_SENDIRI, STATUS_KAWIN, STATUS_KAWIN_ANAK_1, STATUS_KAWIN_ANAK_2, STATUS_KAWIN_ANAK_3};

    static Vector<PTKP> vPTKP = new Vector();
    public static double BIAYA_JABATAN_BULANAN = 500000; 
    public static double BIAYA_JABATAN_PERSEN = 0.05;  // 5% dari brutto
    public static double BIAYA_JABATAN_MAX = 6000000;  // MAXIMUM PER TAHUN
    public static double POTONGAN_GAJI_KENA_PAJAK_MAX = 100000000; // MAXIMUM POTONGAN GAJI UNTUK PAJAK spt untuk Jaminan Hari Tua dan Kesehatan
    public static double NETTO_PERCENT_CONSULTANT = 0.05;

    public final static int TAX_CALC_PATTERN_DEFAULT = 0;
    public final static int TAX_CALC_PATTERN_DEDUC_PREV = 1;
    private static int taxCalcPattern = TAX_CALC_PATTERN_DEFAULT;
    public static String GAJI_RUTIN_YEAR_TO_PREV_PERIOD = "Gaji Rutin s/d Prev.Period";
    public static String GAJI_RUTIN_THIS_PERIOD = "Gaji Rutin Period Ini";
    public static String FORMAT_DECIMAL = "###,###.##";
    public static String NUMBER_OF_NEXT_PERIOD = "Jumlah Periode dari saat ini s/d akhir tahun";
    public static String NUMBER_OF_MONTH_YTD = "Jumlah Periode sampai periode ini";
    public static String TOTAL_OF_CONTINUE_SALARY = "Jumlah Gaji Rutin Setahun";
    public static String GAJI_NON_RUTIN_YTD = "Gaji Non Rutin s/d Periode Ini";
    public static String GAJI_PPH_DIPOTONG_TERPISAH_THIS_PERIOD = "Gaji yang PPH nya dipotong terpisah di periode Ini";
    public static String TOTAL_GAJI_SETAHUN = "Total Gaji Setahun";
    public static String TOTAL_GAJI_SDGN_PERIODE = "Total Gaji sdgn Periode";
    public static String EMPLOYEE_TYPE = "Employee Type";
    public static String NON_SPECIFIC_EMPLOYEE_TYPE = " NON SPECIFIC TYPE OF EMPLOYEE !!!";
    public static String TITLE_BIAYA_JABATAN = "Biaya Jabatan";
    public static String TITLE_NETTO_GAJI_SETAHUN = "Total Gaji Neto Setahun";
    public static String TITLE_DEDUCTION_YEAR_TO_PREV_PERIOD = "Potongan s/d prev.period";
    public static String TITLE_DEDUCTION_THIS_PERIOD = "Potongan Periode Ini";
    public static String TITLE_TOTAL_DEDUCTION_A_YEAR = "Potongan Setahun";
    public static String TITLE_NETTO_GAJI_SETAHUN_MIN_DEDUC = "Netto Gaji Setahun stlh potongan";
    public static String TITLE_PTKP_SETAHUN = "PTKP (Pendapatan Tidak Kena Pajak)Setahun";
    public static String TITLE_PKP_SETAHUN = "PKP (Pendapatan Kena Pajak) Setahun";
    public static String TITLE_PPH21_SETAHUN = "PPH21 setahun";
    public static String TITLE_PPH21_SDGN_PREV_PERIOD = "PPH21 s/d Periode Sebelumnya";
    public static String TITLE_PPH21_THIS_PERIOD = "PPH21 Periode ini";
    public static String TITLE_PPH21_PREPAY_THIS_PERIOD = "PPH21 PrePay Periode ini";
    public static String TITLE_PPH21_THIS_PERIOD_TO_END_YEAR = "PPH21 dari Periode s/d Akhir Tahun";
    public static String TITLE_TARIF_PROGRESSIVE = "Tarif Progressive";
    public static String TITLE_PKP_SYMBOL = "PKP";

    /**
     * load map untuk employee category => tax type
     */
    public static void loadEmployeeTypeMap() {
        employeeTypeMap = new Hashtable();
        try {
            Vector listType = PstEmpCategory.listAll();
            if (listType != null && listType.size() > 0) {
                for (int idx = 0; idx < listType.size(); idx++) {
                    EmpCategory emp = (EmpCategory) listType.get(idx);
                    employeeTypeMap.put("" + emp.getOID(), "" + emp.getTypeForTax());
                }
            }
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    public static int getEmployeeTypeMap(long empTypeOid) {
        if (employeeTypeMap == null) {
            loadEmployeeTypeMap();
        }
        if (employeeTypeMap != null) {
            try {
                return Integer.parseInt((String) employeeTypeMap.get("" + empTypeOid));
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
        return EMPLOYEE_TAX_TYPE_TO_BE_DEFINED;
    }

    public static void loadPTKP() {
        // must be sort by the latest to the oldest
        PTKP ptkp2015 = new PTKP();
        Date date2015 = new Date(2015 - 1900, 1 - 1, 1);
        ptkp2015.setStartDate(date2015);
        ptkp2015.setPTKP_DIRI_SENDIRI(36000000.0);
        ptkp2015.setPTKP_KAWIN(39000000.0);// 
        ptkp2015.setPTKP_KAWIN_ANAK_1(42000000.0);// 
        ptkp2015.setPTKP_KAWIN_ANAK_2(45000000.0);// 
        ptkp2015.setPTKP_KAWIN_ANAK_3(48000000.0);// 

        vPTKP.add(ptkp2015);

        PTKP ptkp2014 = new PTKP();
        Date date2014 = new Date(2014 - 1900, 1 - 1, 1);
        ptkp2014.setStartDate(date2014);
        ptkp2014.setPTKP_DIRI_SENDIRI(24300000.0);
        ptkp2014.setPTKP_KAWIN(26325000.0);// 
        ptkp2014.setPTKP_KAWIN_ANAK_1(28350000.0);// 
        ptkp2014.setPTKP_KAWIN_ANAK_2(30375000.0);// 
        ptkp2014.setPTKP_KAWIN_ANAK_3(32400000.0);// 

        vPTKP.add(ptkp2014);
    }

    public static PTKP getPTKP(Date atDate) {
        if (vPTKP == null || vPTKP.size() == 0) {
            loadPTKP();
        }

        if (atDate == null || vPTKP == null || vPTKP.size() == 0) {
            return new PTKP();
        }

        for (int i = 0; i < vPTKP.size(); i++) {
            PTKP aPTKP = vPTKP.get(i);
            Date start = aPTKP.getStartDate();
            if (start != null) {
                if (atDate.equals(start) || atDate.after(start)) {
                    return aPTKP;
                }
            }
        }
        return new PTKP();
    }

    /*
     public static double calcSalaryTax_old(long employeeId, long periodId, Pajak pajak){
     double pph21KurangBayarBulanTerhitung =0.0;
     try{
     Employee employee = PstEmployee.fetchExc(employeeId);            
     String where = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+"="+employeeId;
     String order = PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELATIONSHIP];
     //Vector<FamilyMember> familymember = PstFamilyMember.list(0, 0, where, order);
            
     double gajiYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(periodId, employeeId,"");
            
     int maritalStatus =STATUS_DIRI_SENDIRI; // status perkawinan ambil dari status kawin pajak , default STATUS_DIRI_SENDIRI
            
     int jumlahBulanDihitung =0; //  hitung dari awal tahun
     PayPeriod payPeriod = PstPayPeriod.fetchExc(periodId);
     //Period period = PstPeriod.fetchExc(periodId);
     if(payPeriod.getPayProcDateClose()!=null){
     jumlahBulanDihitung = payPeriod.getPayProcDateClose().getMonth()+1;
     }else{
     jumlahBulanDihitung = payPeriod.getEndDate().getMonth()+1;
     }            
                               
     Marital marital = null;
     if(employee.getTaxMaritalId()!=0){
     marital = PstMarital.fetchExc(employee.getTaxMaritalId());                
     }else{
     marital = PstMarital.fetchExc(employee.getMaritalId());
     }
            
     if( marital.getMaritalCode().indexOf(STATUS_PREFIX_SINGLE)>-1){ // jika ada kode single
     maritalStatus = STATUS_DIRI_SENDIRI;
     }else{
     if( marital.getMaritalCode().indexOf(STATUS_PREFIX_MARRIED)>-1){ // jika ada kode single
     switch(marital.getNumOfChildren()){
     case 0:
     maritalStatus=STATUS_KAWIN;
     break;
     case 1:
     maritalStatus=STATUS_KAWIN_ANAK_1;
     break;
     case 2:
     maritalStatus=STATUS_KAWIN_ANAK_2;
     break;
     case 3:
     maritalStatus=STATUS_KAWIN_ANAK_3;
     break;
     default:
     if(marital.getNumOfChildren()>3){
     maritalStatus=STATUS_KAWIN_ANAK_3;
     } else{
     maritalStatus=STATUS_KAWIN;
     }
     break;                                                    
     }
     }
                
     }
            
                                    
     double ptkpSampaiBulanTerhitung=0;            
     switch (maritalStatus){
     case STATUS_DIRI_SENDIRI:
     ptkpSampaiBulanTerhitung = (PTKP_DIRI_SENDIRI / 12 ) * jumlahBulanDihitung;
     break;
     case STATUS_KAWIN:
     ptkpSampaiBulanTerhitung = (PTKP_KAWIN / 12 ) * jumlahBulanDihitung;
     break;
     case STATUS_KAWIN_ANAK_1: 
     ptkpSampaiBulanTerhitung = (PTKP_KAWIN_ANAK_1 / 12 ) * jumlahBulanDihitung;
     break;
     case STATUS_KAWIN_ANAK_2:
     ptkpSampaiBulanTerhitung = (PTKP_KAWIN_ANAK_2 / 12 ) * jumlahBulanDihitung;
     break;
     case STATUS_KAWIN_ANAK_3:
     ptkpSampaiBulanTerhitung = (PTKP_KAWIN_ANAK_3 / 12 ) * jumlahBulanDihitung;
     break;                  
     }            
                        
     int employeeTaxType = getEmployeeTypeMap(employee.getEmpCategoryId());  // dapat dari jenis karyawan            
     double pendapatanNettoBulanTerhitung=0;            
     double pkpSampaiBulanTerhitung =0;   
     switch (employeeTaxType){
     case EMPLOYEE_TAX_TYPE_PERMANENT:
     double biayaJabatan = gajiYearToPeriod  * BIAYA_JABATAN_PERSEN ;
     if(biayaJabatan<=BIAYA_JABATAN_MAX){
     pendapatanNettoBulanTerhitung = gajiYearToPeriod - biayaJabatan;
     }else{
     pendapatanNettoBulanTerhitung = gajiYearToPeriod - BIAYA_JABATAN_MAX;                        
     }
     pkpSampaiBulanTerhitung= pendapatanNettoBulanTerhitung - ptkpSampaiBulanTerhitung;
     break;
                    
     case EMPLOYEE_TAX_TYPE_NON_PERMANENT:
     pendapatanNettoBulanTerhitung = gajiYearToPeriod;
     pkpSampaiBulanTerhitung= pendapatanNettoBulanTerhitung - ptkpSampaiBulanTerhitung;
     break;
                    
     case EMPLOYEE_TAX_TYPE_CONSULTANT:
     pkpSampaiBulanTerhitung = 0.5 * gajiYearToPeriod;
     break;
     default:
     pendapatanNettoBulanTerhitung = gajiYearToPeriod;
     pkpSampaiBulanTerhitung= pendapatanNettoBulanTerhitung - ptkpSampaiBulanTerhitung;
     ;
     }
                        
     double pph21SampaiBulanTerhitung =0; // akan dihitung
     double pph21SampaiBulanSebelumnya = PstPaySlip.getSumTaxYearToPreviousPeriod(periodId, employeeId);                                    
            
     pkpSampaiBulanTerhitung= Math.floor( pkpSampaiBulanTerhitung/1000d )*1000d;                                        
     pph21SampaiBulanTerhitung = hitungTarifPPH21(pkpSampaiBulanTerhitung,pajak);
     pph21KurangBayarBulanTerhitung = pph21SampaiBulanTerhitung -  pph21SampaiBulanSebelumnya;            
            
     }catch(Exception exc){
     System.out.println(exc);
     }
        
     return pph21KurangBayarBulanTerhitung;
     }
     */
    public static double calcSalaryTax(long employeeId, PayPeriod payPeriod, PayPeriod prevPeriod, Pajak pajak) {
        return calcSalaryTax(employeeId, payPeriod, prevPeriod, pajak, null);
    }

    public static double calcSalaryTax(long employeeId, PayPeriod payPeriod, PayPeriod prevPeriod, Pajak pajak, Vector withoutComponents) {
        return calcSalaryTax(employeeId, payPeriod, prevPeriod, pajak, withoutComponents, false);
    }

    public static double calcSalaryTax(long employeeId, PayPeriod payPeriod, PayPeriod prevPeriod, Pajak pajak, Vector withoutComponents, boolean returnPeriodToEndYear) {
        return calcSalaryTax(employeeId, payPeriod, prevPeriod, pajak, withoutComponents, returnPeriodToEndYear, 0);
    }

    public static double calcSalaryTax(long employeeId, PayPeriod payPeriod, PayPeriod prevPeriod, Pajak pajak, Vector withoutComponents, boolean returnPeriodToEndYear, int backPeriod) {
        //public static double calcSalaryTax(long employeeId, Period period, Period prevPeriod){
        double pph21KurangBayarBulanTerhitung = 0.0;
        if (payPeriod == null || payPeriod.getOID() == 0) {
            return 0.0d;
        }

        Employee employee = null;
        try {
            employee = PstEmployee.fetchExc(employeeId);
        } catch (Exception exc) {
            System.out.println(exc);
        }

        if (employee == null || employee.getOID() == 0) {
            return 0.0d;
        }

        String whereGajiTidakRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]
                + " IN ( " + PstPayComponent.TUNJANGAN + ", " + PstPayComponent.BONUS_THR + ") ";

        double gajiNonRutinThisPeriod = 0;
        if (employee.getResigned() == 1) { //just check if the gaji non rutin exist
            gajiNonRutinThisPeriod = PstPaySlip.getSumSalaryBenefit(payPeriod.getOID(), employeeId, whereGajiTidakRutin);
        }

        try {
            String sWithout = "";
            String sComps = "";
            if (withoutComponents != null && withoutComponents.size() > 0) {
                sWithout = " AND (";
                for (int idx = 0; idx < withoutComponents.size(); idx++) {
                    String sCode = (String) withoutComponents.get(idx);
                    sWithout = sWithout + " COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + " = \"" + sCode + "\" "
                            + (idx < (withoutComponents.size() - 1) ? ") AND  " : "");
                    sComps = sComps + sCode + ";";
                }
                sWithout = sWithout + ") ";
            }

            if (employee.getResignedDate() == null && employee.getResigned() == 1) {
                employee.setResignedDate(payPeriod.getEndDate());
            }
            /*
             if( employee.getResignedDate()!=null && employee.getResigned() == 1) {
             employee.getResignedDate().setHours(0);
             employee.getResignedDate().setMinutes(0);
             employee.getResignedDate().setSeconds(0);
             payPeriod.getEndDate().setHours(0);
             payPeriod.getEndDate().setMinutes(0);
             payPeriod.getEndDate().setSeconds(0);}*/
            //untuk karyawn yang resign check apakah tanggal resign cocok perperiode ini
            if (employee.getResigned() == 0 || (employee.getResigned() == 1 && employee.getResignedDate().after(payPeriod.getEndDate()))) {
                ///String where = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID] + "=" + employeeId;
                //String order = PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELATIONSHIP];
                //Vector<FamilyMember> familymember = PstFamilyMember.list(0, 0, where, order);                        

                String whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] + " = " + PstPayComponent.GAJI;

                double gajiRutinYearToPrevPeriod = prevPeriod != null && prevPeriod.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryYearToPeriod(prevPeriod.getOID(), employeeId, whereGajiRutin) : 0.0d; // gaji tetap dari awal tahun sampai periode sebelumnya, dalam tahun yg sama               
                double gajiRutinOfPeriod = 0;

                if (backPeriod < 0) {
                    backPeriod = 0;
                }

                if (backPeriod == 0) {
                    gajiRutinOfPeriod = PstPaySlip.getSumSalaryOfPeriod(payPeriod.getOID(), employeeId, whereGajiRutin);  // gaji tetap dari di periode terhitung
                } else { //backPeriod > 0                    
                    gajiRutinOfPeriod = PstPaySlip.getSumSalaryOfPeriod(prevPeriod.getOID(), employeeId, whereGajiRutin); // gaji tetap dari di periode terhitung di asumsikan = gaji rutin periode sebelumnya
                }

                pajak.setHtmlDetailCalc("<table><tr><td>A. " + GAJI_RUTIN_YEAR_TO_PREV_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(gajiRutinYearToPrevPeriod, FORMAT_DECIMAL) + "</td></tr>");
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>B. " + GAJI_RUTIN_THIS_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(gajiRutinOfPeriod, FORMAT_DECIMAL) + "</td></tr>");

                int jumlahPeriodeKedepan = 0; //  hitung dari awal tahun            

              //  if (payPeriod.getPayProcDateClose() != null) {
             //       jumlahPeriodeKedepan = 12 - payPeriod.getPayProcDateClose().getMonth();
             //   } else {
                    jumlahPeriodeKedepan = 12 - payPeriod.getEndDate().getMonth();
             //   }
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>C. " + NUMBER_OF_NEXT_PERIOD + "</td><td>:</td><td>" + jumlahPeriodeKedepan + "</td></tr>");

                double gajiRutinSetahun = gajiRutinYearToPrevPeriod + gajiRutinOfPeriod * jumlahPeriodeKedepan;

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>D. " + TOTAL_OF_CONTINUE_SALARY + "=A+BxC</td><td>:</td><td>" + Formater.formatNumber(gajiRutinSetahun, FORMAT_DECIMAL) + "</td></tr>");

                whereGajiTidakRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]
                        + " IN ( " + PstPayComponent.TUNJANGAN + ", " + PstPayComponent.BONUS_THR + ") ";

                double gajiNonRutinYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(payPeriod.getOID(), employeeId, whereGajiTidakRutin);

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>E. " + GAJI_NON_RUTIN_YTD + "</td><td>:</td><td>" + Formater.formatNumber(gajiNonRutinYearToPeriod, FORMAT_DECIMAL) + "</td></tr>");

                double compGajiOfPeriodWithout = (sWithout == null || sWithout.trim().length() < 1) ? 0.0d
                        : (PstPaySlip.getSumSalaryOfPeriod(payPeriod.getOID(), employeeId, whereGajiRutin + " " + sWithout)
                        + PstPaySlip.getSumSalaryOfPeriod(payPeriod.getOID(), employeeId, whereGajiTidakRutin + " " + sWithout));  // total component gaji yang ppph nya sudah dipotong terpisah
                if (compGajiOfPeriodWithout != 0) {
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>E.1 " + GAJI_PPH_DIPOTONG_TERPISAH_THIS_PERIOD + "( " + sComps + " ) </td><td>:</td><td>(" + Formater.formatNumber(compGajiOfPeriodWithout, FORMAT_DECIMAL) + ")</td></tr>");
                }

                double gajiTotalSetahun = gajiRutinSetahun + gajiNonRutinYearToPeriod - compGajiOfPeriodWithout;

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>F. " + TOTAL_GAJI_SETAHUN + "= D + E</td><td>:</td><td>" + Formater.formatNumber(gajiTotalSetahun, FORMAT_DECIMAL) + "</td></tr>");

                int employeeTaxType = getEmployeeTypeMap(employee.getEmpCategoryId());  // dapat dari jenis karyawan                        
                double pendapatanNettoSetahun = 0.0;

                switch (employeeTaxType) {
                    case EMPLOYEE_TAX_TYPE_PERMANENT:
                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>G. " + EMPLOYEE_TYPE + "</td><td>:</td><td>" + EMPLOYEE_TAX_TYPE[EMPLOYEE_TAX_TYPE_PERMANENT] + "</td></tr>");

                        //update by devin 2014-02-15                    
                        double biayaJabatan = Math.round(gajiTotalSetahun * (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN));
                        //update by priska karena biaya jabatan tidak boleh melebihi jumlah payslip dikali rata rata bulan dalam pajak 20151217
                        int countJumlahPaySlip = PstPaySlip.getJumlahPayslip(employeeId, ""+(payPeriod.getEndDate().getYear()+1900));
                        double nilaiMaksimum = (countJumlahPaySlip * TaxCalculator.BIAYA_JABATAN_BULANAN);
                        if (biayaJabatan > nilaiMaksimum){
                            biayaJabatan = nilaiMaksimum;
                        }
                        
                        
                        if ((biayaJabatan) <= (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX)) {
                            pendapatanNettoSetahun = gajiTotalSetahun - biayaJabatan;
                            pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>H. " + TITLE_BIAYA_JABATAN + "= F x  " + (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN) * 100 + " %</td><td>:</td><td>" + Formater.formatNumber(biayaJabatan, FORMAT_DECIMAL) + "</td></tr>");
                        } else {
                            pendapatanNettoSetahun = gajiTotalSetahun - (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX);
                            pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>H. " + TITLE_BIAYA_JABATAN + "= F x  " + (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN) * 100 + " %</td><td>:</td><td>" + Formater.formatNumber((pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX), FORMAT_DECIMAL) + "</td></tr>");
                        }

                        break;

                    case EMPLOYEE_TAX_TYPE_NON_PERMANENT:
                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>G. " + EMPLOYEE_TYPE + "</td><td>:</td><td>" + EMPLOYEE_TAX_TYPE[EMPLOYEE_TAX_TYPE_NON_PERMANENT] + "</td></tr>");

                        pendapatanNettoSetahun = gajiTotalSetahun;
                        break;

                    case EMPLOYEE_TAX_TYPE_CONSULTANT:
                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>G. " + EMPLOYEE_TYPE + "</td><td>:</td><td>" + EMPLOYEE_TAX_TYPE[EMPLOYEE_TAX_TYPE_CONSULTANT] + "</td></tr>");

                        //update by devin 2014-02-15
                        pendapatanNettoSetahun = (pajak != null && pajak.getNettoPercenKonsultan() != 0 ? pajak.getNettoPercenKonsultan() : NETTO_PERCENT_CONSULTANT) * gajiTotalSetahun;
                        break;
                    default:
                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>G. " + EMPLOYEE_TYPE + "</td><td>:</td><td>" + NON_SPECIFIC_EMPLOYEE_TYPE + "</td></tr>");

                        pendapatanNettoSetahun = gajiTotalSetahun;
                }

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>I. " + TITLE_NETTO_GAJI_SETAHUN + "</td><td>:</td><td>" + Formater.formatNumber(pendapatanNettoSetahun, FORMAT_DECIMAL) + "</td></tr>");

                String wherePotonganGaji = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] + " = " + PstPayComponent.POTONGAN_GAJI;

                double potonganGajiYearToPrevPeriod = prevPeriod != null && prevPeriod.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryYearToPeriod(prevPeriod.getOID(), employeeId, wherePotonganGaji) : 0.0d; // potongan gaji dari awal tahun sampai periode sebelumnya, dalam tahun yg sama
                double potonganGajiOfPeriod = PstPaySlip.getSumSalaryOfPeriod(payPeriod.getOID(), employeeId, wherePotonganGaji);  // potongan gaji dari di periode terhitung
                double totalPotongan = Math.abs(potonganGajiYearToPrevPeriod) + Math.abs(potonganGajiOfPeriod) * jumlahPeriodeKedepan;// jumlahPeriodeKedepan; // update by Pak Tut 15 Dec 2015

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>J. " + TITLE_DEDUCTION_YEAR_TO_PREV_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(potonganGajiYearToPrevPeriod, FORMAT_DECIMAL) + "</td></tr>");
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>K. " + TITLE_DEDUCTION_THIS_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(potonganGajiOfPeriod, FORMAT_DECIMAL) + "</td></tr>");
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>L. " + TITLE_TOTAL_DEDUCTION_A_YEAR /*+" ( Max ="+Formater.formatNumber(POTONGAN_GAJI_KENA_PAJAK_MAX, FORMAT_DECIMAL)+")"*/ + "</td><td>:</td><td>" + Formater.formatNumber((totalPotongan <= POTONGAN_GAJI_KENA_PAJAK_MAX ? totalPotongan : POTONGAN_GAJI_KENA_PAJAK_MAX), FORMAT_DECIMAL) + "</td></tr>");

                if (totalPotongan <= POTONGAN_GAJI_KENA_PAJAK_MAX) {
                    pendapatanNettoSetahun = pendapatanNettoSetahun - totalPotongan;
                } else {
                    pendapatanNettoSetahun = pendapatanNettoSetahun - POTONGAN_GAJI_KENA_PAJAK_MAX;
                }

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>M. " + TITLE_NETTO_GAJI_SETAHUN_MIN_DEDUC + "</td><td>:</td><td>" + Formater.formatNumber(pendapatanNettoSetahun, FORMAT_DECIMAL) + "</td></tr>");

                Marital marital = null;
                try {
                    if (employee.getTaxMaritalId() != 0) {
                        marital = PstMarital.fetchExc(employee.getTaxMaritalId());
                    } else {
                        marital = PstMarital.fetchExc(employee.getMaritalId());
                    }
                } catch (Exception e) {
                }

                int maritalStatus = marital == null ? STATUS_DIRI_SENDIRI : marital.getMaritalStatusTax(); // status perkawinan ambil dari status kawin pajak , default STATUS_DIRI_SENDIRI            
                PTKP ptkp = getPTKP(payPeriod.getEndDate());
                double ptkpSetahun = 0;
                switch (maritalStatus) {
                    case STATUS_DIRI_SENDIRI:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpDiriSendiri() != 0 ? pajak.getPtkpDiriSendiri() : ptkp.getPTKP_DIRI_SENDIRI());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_DIRI_SENDIRI] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                    case STATUS_KAWIN:
                        //update by devin 2014-02-15                    
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawin() != 0 ? pajak.getPtkpKawin() : ptkp.getPTKP_KAWIN());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                    case STATUS_KAWIN_ANAK_1:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak1() != 0 ? pajak.getPtkpKawinAnak1() : ptkp.getPTKP_KAWIN_ANAK_1());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN_ANAK_1] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                    case STATUS_KAWIN_ANAK_2:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak2() != 0 ? pajak.getPtkpKawinAnak2() : ptkp.getPTKP_KAWIN_ANAK_2());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN_ANAK_2] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                    case STATUS_KAWIN_ANAK_3:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak3() != 0 ? pajak.getPtkpKawinAnak3() : ptkp.getPTKP_KAWIN_ANAK_3());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN_ANAK_3] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                }

                double pkpSetahun = pendapatanNettoSetahun - ptkpSetahun;
                if (pkpSetahun < 0.00) {
                    pkpSetahun = 0.0;
                }
                pkpSetahun = Math.floor(pkpSetahun / 1000d) * 1000d;

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>O. " + TITLE_PKP_SETAHUN + " = M - N </td><td>:</td><td>" + Formater.formatNumber(pkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                double pph21Setahun = hitungTarifPPH21(pkpSetahun, pajak, employee.getNpwp());

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>P. " + TITLE_PPH21_SETAHUN + "</td><td>:</td><td>" + Formater.formatNumber(pph21Setahun, FORMAT_DECIMAL) + "</td></tr>");

                double pph21SampaiBulanSebelumnya = PstPaySlip.getSumTaxYearToPreviousPeriod(payPeriod.getOID(), employeeId);

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Q. " + TITLE_PPH21_SDGN_PREV_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(pph21SampaiBulanSebelumnya, FORMAT_DECIMAL) + "</td></tr>");

                // update by Kartika 2015-07-23
                double pph21PrepayBulanIni = PstPaySlip.getTaxPrepayPeriod(payPeriod.getOID(), employeeId);
                if (pph21PrepayBulanIni != 0) {
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Q1. " + TITLE_PPH21_PREPAY_THIS_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(pph21PrepayBulanIni, FORMAT_DECIMAL) + "</td></tr>");
                }

                if (returnPeriodToEndYear) { // update by Kartika 2015-06-29
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Q. " + TITLE_PPH21_THIS_PERIOD_TO_END_YEAR + " =( ( P - Q )) </td><td>:</td><td>" + Formater.formatNumber(pph21Setahun - pph21SampaiBulanSebelumnya, FORMAT_DECIMAL) + "</td></tr>");
                    return pph21Setahun - pph21SampaiBulanSebelumnya - pph21PrepayBulanIni;
                }

                /*update by devin 2104-02-14 agar tidak dibulatkan  Math.round((pph21Setahun-pph21SampaiBulanSebelumnya)/jumlahPeriodeKedepan)*/
                pph21KurangBayarBulanTerhitung = Math.floor(((double) (pph21Setahun - pph21SampaiBulanSebelumnya) / ((double) jumlahPeriodeKedepan)) - pph21PrepayBulanIni); // update by Kartika -pph21PrepayBulanIni
                // -  pph21SampaiBulanSebelumnya)/ (12 -jumlahBulanDihitung-1 );            

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Q. " + TITLE_PPH21_THIS_PERIOD + " = ( P - Q ) / C </td><td>:</td><td>" + Formater.formatNumber(pph21KurangBayarBulanTerhitung, FORMAT_DECIMAL) + "</td></tr>");

            } else if (employee.getResigned() == 1 && (((employee.getResignedDate().after(payPeriod.getStartDate()) || (employee.getResignedDate().equals(payPeriod.getStartDate())))
                    && (employee.getResignedDate().before(payPeriod.getEndDate()) || employee.getResignedDate().equals(payPeriod.getEndDate())))
                    || (gajiNonRutinThisPeriod > 0))) { // bisa juga gajiNonRutinThisPeriod >0 untuk karyawan sdh resign diberikan  karyawan.
                // PERHITUNGAN KARYAWAN YANG RESIGN DALAM PERIODE GAJI 
                String where = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID] + "=" + employeeId;
                String order = PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELATIONSHIP];
                //Vector<FamilyMember> familymember = PstFamilyMember.list(0, 0, where, order);                        

                String whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] + " = " + PstPayComponent.GAJI;

                double gajiRutinUntilPrevPeriod = prevPeriod != null && prevPeriod.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryYearToPeriod(prevPeriod.getOID(), employeeId, whereGajiRutin) : 0.0d; // gaji tetap dari awal tahun sampai periode sebelumnya, dalam tahun yg sama

                if (backPeriod < 0) {
                    backPeriod = 0;
                }

                double gajiRutinOfPeriod = 0.0;
                if (backPeriod == 0) {
                    gajiRutinOfPeriod = PstPaySlip.getSumSalaryOfPeriod(payPeriod.getOID(), employeeId, whereGajiRutin);  // gaji tetap dari di periode terhitung
                } else {
                    gajiRutinOfPeriod = PstPaySlip.getSumSalaryOfPeriod(prevPeriod.getOID(), employeeId, whereGajiRutin);  // gaji tetap dari di periode terhitung di asumsikan = gaji rutin periode sebelumnya
                }

                pajak.setHtmlDetailCalc("<table><tr><td>A. " + GAJI_RUTIN_YEAR_TO_PREV_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(gajiRutinUntilPrevPeriod, FORMAT_DECIMAL) + "</td></tr>");
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>B. " + GAJI_RUTIN_THIS_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(gajiRutinOfPeriod, FORMAT_DECIMAL) + "</td></tr>");

                int jumlahPerioYTD = 0; //  hitung dari awal tahun            

                if (payPeriod.getPayProcDateClose() != null) {
                    jumlahPerioYTD = payPeriod.getPayProcDateClose().getMonth() + 1;
                } else {
                    jumlahPerioYTD = payPeriod.getEndDate().getMonth();
                }
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>C. " + NUMBER_OF_MONTH_YTD + "</td><td>:</td><td>" + jumlahPerioYTD + "</td></tr>");

                double gajiRutinSdgnResign = gajiRutinUntilPrevPeriod + gajiRutinOfPeriod;

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>D. " + TOTAL_OF_CONTINUE_SALARY + "=A+B</td><td>:</td><td>" + Formater.formatNumber(gajiRutinSdgnResign, FORMAT_DECIMAL) + "</td></tr>");

                whereGajiTidakRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]
                        + " IN ( " + PstPayComponent.TUNJANGAN + ", " + PstPayComponent.BONUS_THR + ") ";
                double gajiNonRutinYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(payPeriod.getOID(), employeeId, whereGajiTidakRutin);

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>E. " + GAJI_NON_RUTIN_YTD + "</td><td>:</td><td>" + Formater.formatNumber(gajiNonRutinYearToPeriod, FORMAT_DECIMAL) + "</td></tr>");

                double compGajiOfPeriodWithout = (sWithout == null || sWithout.trim().length() < 1) ? 0.0d : PstPaySlip.getSumSalaryOfPeriod(payPeriod.getOID(), employeeId, whereGajiRutin + " " + sWithout);  // total component gaji yang ppph nya sudah dipotong terpisah // update by Pak Tut 14 Dec 2015
                if (compGajiOfPeriodWithout != 0) {
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>E.1 " + GAJI_PPH_DIPOTONG_TERPISAH_THIS_PERIOD + "( " + sComps + " ) </td><td>:</td><td>(" + Formater.formatNumber(compGajiOfPeriodWithout, FORMAT_DECIMAL) + ")</td></tr>");
                }

                double gajiTotalSdnPeriode = gajiRutinSdgnResign + gajiNonRutinYearToPeriod - compGajiOfPeriodWithout;

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>F. " + TOTAL_GAJI_SDGN_PERIODE + "= D + E</td><td>:</td><td>" + Formater.formatNumber(gajiTotalSdnPeriode, FORMAT_DECIMAL) + "</td></tr>");

                int employeeTaxType = getEmployeeTypeMap(employee.getEmpCategoryId());  // dapat dari jenis karyawan                        
                double pendapatanNettoSdgnPeriode = 0.0;

                switch (employeeTaxType) {
                    case EMPLOYEE_TAX_TYPE_PERMANENT:
                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>G. " + EMPLOYEE_TYPE + "</td><td>:</td><td>" + EMPLOYEE_TAX_TYPE[EMPLOYEE_TAX_TYPE_PERMANENT] + "</td></tr>");

                        //update by devin 2014-02-15                    
                        double biayaJabatan = Math.round(gajiTotalSdnPeriode * (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN));

                        if ((biayaJabatan) <= ((pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX) * ((float) jumlahPerioYTD / 12f))) {
                            pendapatanNettoSdgnPeriode = gajiTotalSdnPeriode - biayaJabatan;
                            pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>H. " + TITLE_BIAYA_JABATAN + "= F x  " + (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN) * 100 + " %</td><td>:</td><td>" + Formater.formatNumber(biayaJabatan, FORMAT_DECIMAL) + "</td></tr>");
                        } else {
                            pendapatanNettoSdgnPeriode = gajiTotalSdnPeriode - ((pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX) * ((float) jumlahPerioYTD / 12f));
                            pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>H. " + TITLE_BIAYA_JABATAN + "= F x  " + (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN) * 100 + " %</td><td>:</td><td>" + Formater.formatNumber((pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX), FORMAT_DECIMAL) + "</td></tr>");
                        }

                        break;

                    case EMPLOYEE_TAX_TYPE_NON_PERMANENT:
                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>G. " + EMPLOYEE_TYPE + "</td><td>:</td><td>" + EMPLOYEE_TAX_TYPE[EMPLOYEE_TAX_TYPE_NON_PERMANENT] + "</td></tr>");

                        pendapatanNettoSdgnPeriode = gajiTotalSdnPeriode;
                        break;

                    case EMPLOYEE_TAX_TYPE_CONSULTANT:
                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>G. " + EMPLOYEE_TYPE + "</td><td>:</td><td>" + EMPLOYEE_TAX_TYPE[EMPLOYEE_TAX_TYPE_CONSULTANT] + "</td></tr>");

                        //update by devin 2014-02-15
                        pendapatanNettoSdgnPeriode = (pajak != null && pajak.getNettoPercenKonsultan() != 0 ? pajak.getNettoPercenKonsultan() : NETTO_PERCENT_CONSULTANT) * gajiTotalSdnPeriode;
                        break;
                    default:
                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>G. " + EMPLOYEE_TYPE + "</td><td>:</td><td>" + NON_SPECIFIC_EMPLOYEE_TYPE + "</td></tr>");

                        pendapatanNettoSdgnPeriode = gajiTotalSdnPeriode;
                }

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>I. " + TITLE_NETTO_GAJI_SETAHUN + "</td><td>:</td><td>" + Formater.formatNumber(pendapatanNettoSdgnPeriode, FORMAT_DECIMAL) + "</td></tr>");

                String wherePotonganGaji = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] + " = " + PstPayComponent.POTONGAN_GAJI;

                double potonganGajiUntilPrevPeriod = prevPeriod != null && prevPeriod.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryYearToPeriod(prevPeriod.getOID(), employeeId, wherePotonganGaji) : 0.0d; // potongan gaji dari awal tahun sampai periode sebelumnya, dalam tahun yg sama
                double potonganGajiOfPeriod = PstPaySlip.getSumSalaryOfPeriod(payPeriod.getOID(), employeeId, wherePotonganGaji);  // potongan gaji dari di periode terhitung
                double totalPotongan = Math.abs(potonganGajiUntilPrevPeriod) + Math.abs(potonganGajiOfPeriod) ;//* (12-jumlahPerioYTD); // update by Pak Tut 15 Dec 2015

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>J. " + TITLE_DEDUCTION_YEAR_TO_PREV_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(potonganGajiUntilPrevPeriod, FORMAT_DECIMAL) + "</td></tr>");
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>K. " + TITLE_DEDUCTION_THIS_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(potonganGajiOfPeriod, FORMAT_DECIMAL) + "</td></tr>");
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>L. " + TITLE_TOTAL_DEDUCTION_A_YEAR /*+" ( Max ="+Formater.formatNumber(POTONGAN_GAJI_KENA_PAJAK_MAX, FORMAT_DECIMAL)+")"*/ + "</td><td>:</td><td>" + Formater.formatNumber((totalPotongan <= POTONGAN_GAJI_KENA_PAJAK_MAX ? totalPotongan : POTONGAN_GAJI_KENA_PAJAK_MAX), FORMAT_DECIMAL) + "</td></tr>");

                if (totalPotongan <= POTONGAN_GAJI_KENA_PAJAK_MAX) {
                    pendapatanNettoSdgnPeriode = pendapatanNettoSdgnPeriode - totalPotongan;
                } else {
                    pendapatanNettoSdgnPeriode = pendapatanNettoSdgnPeriode - POTONGAN_GAJI_KENA_PAJAK_MAX;
                }

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>M. " + TITLE_NETTO_GAJI_SETAHUN_MIN_DEDUC + "</td><td>:</td><td>" + Formater.formatNumber(pendapatanNettoSdgnPeriode, FORMAT_DECIMAL) + "</td></tr>");

                Marital marital = null;
                try {
                    if (employee.getTaxMaritalId() != 0) {
                        marital = PstMarital.fetchExc(employee.getTaxMaritalId());
                    } else {
                        marital = PstMarital.fetchExc(employee.getMaritalId());
                    }
                } catch (Exception e) {
                }

                int maritalStatus = marital == null ? STATUS_DIRI_SENDIRI : marital.getMaritalStatusTax(); // status perkawinan ambil dari status kawin pajak , default STATUS_DIRI_SENDIRI            
                PTKP ptkp = getPTKP(payPeriod.getEndDate());
                double ptkpSetahun = 0;
                switch (maritalStatus) {
                    case STATUS_DIRI_SENDIRI:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpDiriSendiri() != 0 ? pajak.getPtkpDiriSendiri() : ptkp.getPTKP_DIRI_SENDIRI());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_DIRI_SENDIRI] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                    case STATUS_KAWIN:
                        //update by devin 2014-02-15                    
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawin() != 0 ? pajak.getPtkpKawin() : ptkp.getPTKP_KAWIN());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                    case STATUS_KAWIN_ANAK_1:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak1() != 0 ? pajak.getPtkpKawinAnak1() : ptkp.getPTKP_KAWIN_ANAK_1());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN_ANAK_1] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                    case STATUS_KAWIN_ANAK_2:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak2() != 0 ? pajak.getPtkpKawinAnak2() : ptkp.getPTKP_KAWIN_ANAK_2());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN_ANAK_2] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                    case STATUS_KAWIN_ANAK_3:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak3() != 0 ? pajak.getPtkpKawinAnak3() : ptkp.getPTKP_KAWIN_ANAK_3());

                        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN_ANAK_3] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                        break;
                }

                double pkpSetahun = pendapatanNettoSdgnPeriode - ptkpSetahun;
                if (pkpSetahun < 0.00) {
                    pkpSetahun = 0.0;
                }
                pkpSetahun = Math.floor(pkpSetahun / 1000d) * 1000d;

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>O. " + TITLE_PKP_SETAHUN + " = M - N </td><td>:</td><td>" + Formater.formatNumber(pkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                double pph21Setahun = hitungTarifPPH21(pkpSetahun, pajak, employee.getNpwp());

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>P. " + TITLE_PPH21_SETAHUN + "</td><td>:</td><td>" + Formater.formatNumber(pph21Setahun, FORMAT_DECIMAL) + "</td></tr>");

                double pph21SampaiBulanSebelumnya = PstPaySlip.getSumTaxYearToPreviousPeriod(payPeriod.getOID(), employeeId);

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Q. " + TITLE_PPH21_SDGN_PREV_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(pph21SampaiBulanSebelumnya, FORMAT_DECIMAL) + "</td></tr>");

                // update by Kartika 2015-07-23
                double pph21PrepayBulanIni = PstPaySlip.getTaxPrepayPeriod(payPeriod.getOID(), employeeId);
                if (pph21PrepayBulanIni != 0) {
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Q1. " + TITLE_PPH21_PREPAY_THIS_PERIOD + "</td><td>:</td><td>" + Formater.formatNumber(pph21PrepayBulanIni, FORMAT_DECIMAL) + "</td></tr>");
                }

                if (returnPeriodToEndYear) { // update by Kartika 2015-06-29
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Q. " + TITLE_PPH21_THIS_PERIOD_TO_END_YEAR + " = ( P - Q ) </td><td>:</td><td>" + Formater.formatNumber(pph21Setahun - pph21SampaiBulanSebelumnya, FORMAT_DECIMAL) + "</td></tr>");
                    return pph21Setahun - pph21SampaiBulanSebelumnya;
                }

                /*update by devin 2104-02-14 agar tidak dibulatkan  Math.round((pph21Setahun-pph21SampaiBulanSebelumnya)/jumlahPeriodeKedepan)*/
                // pph21KurangBayarBulanTerhitung = Math.floor((double) (pph21Setahun - pph21SampaiBulanSebelumnya) / ((double) jumlahPerioYTD));
                /* baris code di atas pph21KurangBayarBulanTerhitung diganti dengn code di bawah*/
                pph21KurangBayarBulanTerhitung = Math.floor((double) (pph21Setahun - pph21SampaiBulanSebelumnya - pph21PrepayBulanIni));
                // -  pph21SampaiBulanSebelumnya)/ (12 -jumlahBulanDihitung-1 );            

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Q. " + TITLE_PPH21_THIS_PERIOD + " = P - Q - Q1 </td><td>:</td><td>" + Formater.formatNumber(pph21KurangBayarBulanTerhitung, FORMAT_DECIMAL) + "</td></tr>");
                updateSlipComp(payPeriod.getOID(), employeeId, pph21KurangBayarBulanTerhitung);
            }

        } catch (Exception exc) {
            System.out.println(exc);
        }
        //String gUPComponent = "BNF19";// chk di salary level detail
        String gUPComponent = getComponentWithFormula("SALARY_GR_UP_TAX");
        
        
        if( gUPComponent != null && gUPComponent.length() > 0 ){
            //ambil nilainya
            double nilaiGUPTax = getGrossUpValue(employeeId, payPeriod, gUPComponent);
            long paySlipId = getPayslipId(employeeId, payPeriod, gUPComponent);
            // ambil nilai g up tax dari payrollslip
            
            if (nilaiGUPTax != pph21KurangBayarBulanTerhitung){ // di bulatkan dengan even terlebih dahulu ....
                
                //set nilai gross Up component =  pph21KurangBayarBulanTerhitung;
                setGrossUpValue(paySlipId,gUPComponent, pph21KurangBayarBulanTerhitung);
                pph21KurangBayarBulanTerhitung = calcSalaryTax(employeeId, payPeriod, prevPeriod, pajak);
                setGrossUpValue(paySlipId,gUPComponent, pph21KurangBayarBulanTerhitung);
            }
        }
        return pph21KurangBayarBulanTerhitung;
    }

    public static String getComponentWithFormula(String formula){
        String compCode = "";
        DBResultSet dbrs = null;
        
        try {

            String sql = "SELECT plc.COMP_CODE "+  
                    " FROM pay_level_comp AS plc" +
                    " WHERE plc.FORMULA LIKE '%" + formula + "%'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                compCode = rs.getString(1);
            }
            rs.close();
            return compCode;
        } catch (Exception e) {
            return compCode;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public static double  getGrossUpValue(long employeeId, PayPeriod payPeriod,String gUPComponent){
        double compValue = 0;
        DBResultSet dbrs = null;
        
        try {

            String sql = "SELECT PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]+ 
                    " FROM " + PstPaySlipComp.TBL_PAY_SLIP_COMP + " AS PAY" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP" +
                    " ON PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + payPeriod.getOID() +
                    " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] + "=" + employeeId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "= \"" + gUPComponent + "\"" ;

            //System.out.println("SQL PstPaySlipComp.getOtherDeduction"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
        
        
    }
    
  public static long  getPayslipId(long employeeId, PayPeriod payPeriod,String gUPComponent){
        long compValue = 0;
        DBResultSet dbrs = null;
        
        try {

            String sql = "SELECT PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+ 
                    " FROM " + PstPaySlipComp.TBL_PAY_SLIP_COMP + " AS PAY" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP" +
                    " ON PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + payPeriod.getOID() +
                    " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] + "=" + employeeId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "= \"" + gUPComponent+"\"" ;

            //System.out.println("SQL PstPaySlipComp.getOtherDeduction"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                compValue = rs.getLong(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
        
        
    }  
     
  public static int setGrossUpValue(long paySLipId, String gUPComponent, double value){
        try {
            String sql = " UPDATE  pay_slip_comp SET `COMP_VALUE` = "+value+
                         " WHERE `PAY_SLIP_ID` = "+paySLipId+" AND `COMP_CODE` = \""+gUPComponent+"\"" ;
            int status = DBHandler.execUpdate(sql);
            return 0;
        } catch (Exception exc) {
            System.out.println("error update fam member by employee " + exc.toString());
        }
        return -1;
  }
 
    
    public static double getBiayaJabatanAnnual(int masaAwal, int masaAkhir, long empCategoryID, double gajiTotal, Pajak pajak, boolean WNI) {

        int employeeTaxType = getEmployeeTypeMap(empCategoryID);  // dapat dari jenis karyawan                        
        double biayaJabatan = 0;

        switch (employeeTaxType) {
            case EMPLOYEE_TAX_TYPE_PERMANENT:
                if (WNI) {
                    biayaJabatan = Math.round(gajiTotal * (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN));
                    if (!((biayaJabatan) <= (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX))) {
                        biayaJabatan = (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX);
                    }
                } else {
                    biayaJabatan = Math.round(gajiTotal * (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN) * ((masaAkhir - masaAwal +1) / 12));
                    if (!((biayaJabatan) <= ((pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX) * ((masaAkhir - masaAwal+1) / 12)))) {
                        biayaJabatan = (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX) * ((masaAkhir - masaAwal+1) / 12);
                    }
                }
                break;

            case EMPLOYEE_TAX_TYPE_NON_PERMANENT:
                biayaJabatan = 0;
                break;

            case EMPLOYEE_TAX_TYPE_CONSULTANT:
                biayaJabatan = 0;
                break;

            default:
                biayaJabatan = 0;
        }
        return biayaJabatan;
    }

    public static double getPTKPSetahun(int maritalStatus, Pajak pajak, Date checkData, boolean WNI, int masaAwal, int masaAkhir) {
        PTKP ptkp = getPTKP(checkData==null ? new Date():checkData);
        double ptkpSetahun = 0;
        switch (maritalStatus) {
            case STATUS_KAWIN:
                //update by devin 2014-02-15                    
                ptkpSetahun = (pajak != null && pajak.getPtkpKawin() != 0 ? pajak.getPtkpKawin() : ptkp.getPTKP_KAWIN());

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                break;
            case STATUS_KAWIN_ANAK_1:
                //update by devin 2014-02-15
                ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak1() != 0 ? pajak.getPtkpKawinAnak1() : ptkp.getPTKP_KAWIN_ANAK_1());

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN_ANAK_1] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                break;
            case STATUS_KAWIN_ANAK_2:
                //update by devin 2014-02-15
                ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak2() != 0 ? pajak.getPtkpKawinAnak2() : ptkp.getPTKP_KAWIN_ANAK_2());

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN_ANAK_2] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                break;
            case STATUS_KAWIN_ANAK_3:
                //update by devin 2014-02-15
                ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak3() != 0 ? pajak.getPtkpKawinAnak3() : ptkp.getPTKP_KAWIN_ANAK_3());

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_KAWIN_ANAK_3] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                break;
                
            case STATUS_DIRI_SENDIRI:
            default:
                //update by devin 2014-02-15
                ptkpSetahun = (pajak != null && pajak.getPtkpDiriSendiri() != 0 ? pajak.getPtkpDiriSendiri() : ptkp.getPTKP_DIRI_SENDIRI());

                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>N. " + TITLE_PTKP_SETAHUN + "(Status =" + STATUS_EMPLOYEE[STATUS_DIRI_SENDIRI] + ")</td><td>:</td><td>" + Formater.formatNumber(ptkpSetahun, FORMAT_DECIMAL) + "</td></tr>");

                break;
        }
        if(!WNI){
            ptkpSetahun = ptkpSetahun * ( masaAkhir - masaAwal + 1) / 12;
        }
        
        return ptkpSetahun;
    }

    /**
     * Menghitung pajak untuk gaji tidak tetap spt THR dan Bonus Algoritma : PPh
     * Sementara atas Bonus = PPh21 (Gaji bulan sebelumnya + Bonus bulan ini ) -
     * PPh21 ( Gaji bulan sebelumnya ) Jadi kita menggunakan perumpamaan jika
     * gaji bulan ini sama dengan bulan sebelujmnya. Note class yg di tambah :
     * PstPaySlip ,
     *
     * @param employeeId
     * @param payPeriod
     * @param prevPeriod
     * @param pajak
     * @return
     */
    public static double calcSalaryTax_NonContinueMonthly(long employeeId, PayPeriod payPeriod, PayPeriod prevPeriod, Pajak pajak) {
        //public static double calcSalaryTax(long employeeId, Period period, Period prevPeriod){
        double pph21KurangBayarBulanTerhitung = 0.0;
        if (payPeriod == null || payPeriod.getOID() == 0) {
            return 0.0d;
        }

        try {
            String whereGajiTidakRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]
                    + " IN ( " + PstPayComponent.TUNJANGAN + ", " + PstPayComponent.BONUS_THR + ") ";

            double gajiNonRutinThisPeriod = PstPaySlip.getSumSalaryBenefit(payPeriod.getOID(), employeeId, whereGajiTidakRutin);
            if (Math.abs(gajiNonRutinThisPeriod) <= 0.0d) {
                return 0.0;
            }
            Employee employee = PstEmployee.fetchExc(employeeId);
            String where = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID] + "=" + employeeId;
            String order = PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELATIONSHIP];
            //Vector<FamilyMember> familymember = PstFamilyMember.list(0, 0, where, order);                        
            String payPostDeducCodes = PstSystemProperty.getValueByName("PAYROLL_TAX_POST_DEDUCTION");
            String whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] + " = " + PstPayComponent.GAJI + " AND COMP.COMP_CODE NOT IN (" + payPostDeducCodes + ")";

            double gajiRutinYearToPrevPeriod = prevPeriod != null && prevPeriod.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryYearToPeriod(prevPeriod.getOID(), employeeId, whereGajiRutin) : 0.0d; // gaji tetap dari awal tahun sampai periode sebelumnya, dalam tahun yg sama
            double gajiRutinOfPeriod = PstPaySlip.getSumSalaryOfPeriod(prevPeriod.getOID(), employeeId, whereGajiRutin);  // gaji tetap dari di satu periode sebelum periode terhitung

            int jumlahPeriodeKedepan = 0; //  hitung dari awal tahun            

            if (payPeriod.getPayProcDateClose() != null) {
                jumlahPeriodeKedepan = 12 - payPeriod.getPayProcDateClose().getMonth();
            } else {
                jumlahPeriodeKedepan = 12 - payPeriod.getEndDate().getMonth();
            }

            double gajiRutinSetahun = ((gajiRutinYearToPrevPeriod + gajiRutinOfPeriod) * 12d) / (1d + 12d - (double) jumlahPeriodeKedepan);
            //double gajiNonRutinYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(payPeriod.getOID(), employeeId, whereGajiTidakRutin);                        
            double gajiTotalSetahun = gajiRutinSetahun;// + gajiNonRutinYearToPeriod;  GAJI NON RUTIN DIHITUNG DIBAWAH

            double biayaJabatan = 0.0; // biaya jabatan rutin

            int employeeTaxType = getEmployeeTypeMap(employee.getEmpCategoryId());  // dapat dari jenis karyawan                        
            double pendapatanNettoSetahun = 0.0;
            switch (employeeTaxType) {
                case EMPLOYEE_TAX_TYPE_PERMANENT:
                    //update by devin 2014-02-15
                    biayaJabatan = Math.round(gajiTotalSetahun * (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN));
                    //update by priska karena biaya jabatan tidak boleh melebihi jumlah payslip dikali rata rata bulan dalam pajak 20151217    
                    int countJumlahPaySlip = PstPaySlip.getJumlahPayslip(employeeId, ""+(payPeriod.getEndDate().getYear()+1900));
                        double nilaiMaksimum = (countJumlahPaySlip * TaxCalculator.BIAYA_JABATAN_BULANAN);
                        if (biayaJabatan > nilaiMaksimum){
                            biayaJabatan = nilaiMaksimum;
                        }
                        
                    
                    if ((biayaJabatan) <= (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX)) {
                        pendapatanNettoSetahun = gajiTotalSetahun - biayaJabatan;
                    } else {
                        pendapatanNettoSetahun = gajiTotalSetahun - (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX);
                    }
                    break;

                case EMPLOYEE_TAX_TYPE_NON_PERMANENT:
                    pendapatanNettoSetahun = gajiTotalSetahun;
                    break;

                case EMPLOYEE_TAX_TYPE_CONSULTANT:
                    //update by devin 2014-02-15
                    pendapatanNettoSetahun = (pajak != null && pajak.getNettoPercenKonsultan() != 0 ? pajak.getNettoPercenKonsultan() : NETTO_PERCENT_CONSULTANT) * gajiTotalSetahun;
                    break;
                default:
                    pendapatanNettoSetahun = gajiTotalSetahun;
            }

            String whereDeduc = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] + " = " + PstPayComponent.GAJI + " AND COMP.COMP_CODE IN (" + payPostDeducCodes + ")";;
            double payTaxDeducCompValPrevPeriod = prevPeriod != null && prevPeriod.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryDeductionYearToPeriod(prevPeriod.getOID(), employeeId, whereDeduc) : 0.0d; // gaji tetap dari awal tahun sampai periode sebelumnya, dalam tahun yg sama            
            double payTaxDeducCompVal = prevPeriod != null && prevPeriod.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryDeduction(payPeriod.getOID(), employeeId, whereDeduc) : 0.0d;
            pendapatanNettoSetahun = pendapatanNettoSetahun - (((payTaxDeducCompValPrevPeriod + payTaxDeducCompVal) * 12d) / (1d + 12d - (double) jumlahPeriodeKedepan));

            int maritalStatus = STATUS_DIRI_SENDIRI; // status perkawinan ambil dari status kawin pajak , default STATUS_DIRI_SENDIRI            

            Marital marital = null;
            if (employee.getTaxMaritalId() != 0) {
                marital = PstMarital.fetchExc(employee.getTaxMaritalId());
            } else {
                marital = PstMarital.fetchExc(employee.getMaritalId());
            }

            if (marital.getMaritalCode().indexOf(STATUS_PREFIX_SINGLE) > -1) { // jika ada kode single
                maritalStatus = STATUS_DIRI_SENDIRI;
            } else {
                if (marital.getMaritalCode().indexOf(STATUS_PREFIX_MARRIED) > -1) { // jika ada kode single
                    switch (marital.getNumOfChildren()) {
                        case 0:
                            maritalStatus = STATUS_KAWIN;
                            break;
                        case 1:
                            maritalStatus = STATUS_KAWIN_ANAK_1;
                            break;
                        case 2:
                            maritalStatus = STATUS_KAWIN_ANAK_2;
                            break;
                        case 3:
                            maritalStatus = STATUS_KAWIN_ANAK_3;
                            break;
                        default:
                            if (marital.getNumOfChildren() > 3) {
                                maritalStatus = STATUS_KAWIN_ANAK_3;
                            } else {
                                maritalStatus = STATUS_KAWIN;
                            }
                            break;
                    }
                }

            }

            double ptkpSetahun = 0;
            PTKP ptkp = getPTKP(payPeriod.getEndDate());
            if (employeeTaxType == EMPLOYEE_TAX_TYPE_PERMANENT || employeeTaxType == EMPLOYEE_TAX_TYPE_NON_PERMANENT) {
                switch (maritalStatus) {
                    case STATUS_DIRI_SENDIRI:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpDiriSendiri() != 0 ? pajak.getPtkpDiriSendiri() : ptkp.getPTKP_DIRI_SENDIRI());
                        break;
                    case STATUS_KAWIN:
                        //update by devin 2014-02-15

                        ptkpSetahun = (pajak != null && pajak.getPtkpKawin() != 0 ? pajak.getPtkpKawin() : ptkp.getPTKP_KAWIN());
                        break;
                    case STATUS_KAWIN_ANAK_1:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak1() != 0 ? pajak.getPtkpKawinAnak1() : ptkp.getPTKP_KAWIN_ANAK_1());
                        break;
                    case STATUS_KAWIN_ANAK_2:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak2() != 0 ? pajak.getPtkpKawinAnak2() : ptkp.getPTKP_KAWIN_ANAK_2());
                        break;
                    case STATUS_KAWIN_ANAK_3:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak3() != 0 ? pajak.getPtkpKawinAnak3() : ptkp.getPTKP_KAWIN_ANAK_3());
                        break;
                }
            }

            // perhitungan pph21 dari pendapatan non rutin , sampai bulan terhitung
            double gajiNonRutinToPeriod = PstPaySlip.getSumSalaryBenefit/*getSumSalaryYearToPeriod*/(payPeriod.getOID(), employeeId, whereGajiTidakRutin);
            double biayaJabatanNonRutinToPeriod = 0.0; // biaya jabatan rutin dihitung bulanan                        
            double pendapatanNettoNonRutinToPeriod = 0.0;  // bulanan  

            switch (employeeTaxType) {
                case EMPLOYEE_TAX_TYPE_PERMANENT:
                    biayaJabatanNonRutinToPeriod = Math.round(gajiNonRutinToPeriod * (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN));
                    if ((biayaJabatan + biayaJabatanNonRutinToPeriod) <= (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX)) {
                        pendapatanNettoNonRutinToPeriod = gajiNonRutinToPeriod - biayaJabatanNonRutinToPeriod;
                    } else {
                        pendapatanNettoNonRutinToPeriod = gajiNonRutinToPeriod - ((pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX) - biayaJabatan);
                    }
                    break;
                case EMPLOYEE_TAX_TYPE_NON_PERMANENT:
                    pendapatanNettoNonRutinToPeriod = gajiNonRutinToPeriod;
                    break;
                case EMPLOYEE_TAX_TYPE_CONSULTANT:
                    pendapatanNettoNonRutinToPeriod = (pajak != null && pajak.getNettoPercenKonsultan() != 0 ? pajak.getNettoPercenKonsultan() : NETTO_PERCENT_CONSULTANT) * gajiNonRutinToPeriod;
                    break;
                default:
                    pendapatanNettoNonRutinToPeriod = gajiNonRutinToPeriod;
            }

            pendapatanNettoNonRutinToPeriod = Math.floor(pendapatanNettoNonRutinToPeriod / 1000d) * 1000d;

            double pkpSetahunGabung = pendapatanNettoSetahun + pendapatanNettoNonRutinToPeriod - ptkpSetahun;
            if (pkpSetahunGabung < 0.00) {
                pkpSetahunGabung = 0.0;
            }

            pkpSetahunGabung = Math.floor(pkpSetahunGabung / 1000d) * 1000d;
            double pph21SetahunGabung = hitungTarifPPH21(pkpSetahunGabung, pajak,employee.getNpwp());

            double pph21SampaiBulanSebelumnya = PstPaySlip.getSumTaxYearToPreviousPeriod(payPeriod.getOID(), employeeId);
            pph21KurangBayarBulanTerhitung = ((pph21SetahunGabung * (1d + 12d - (double) jumlahPeriodeKedepan)) / 12d) - pph21SampaiBulanSebelumnya;

            double pkpSetahunTanpaNonRutin = pendapatanNettoSetahun - ptkpSetahun;
            if (pkpSetahunTanpaNonRutin < 0.00) {
                pkpSetahunTanpaNonRutin = 0.0;
            }

            pkpSetahunTanpaNonRutin = Math.floor(pkpSetahunTanpaNonRutin / 1000d) * 1000d;

            double pph21SetahunTanpaNonRutin = hitungTarifPPH21(pkpSetahunTanpaNonRutin, pajak, employee.getNpwp());

            double pph21KurangBayarBulanTerhitungTanpaNonRutin = ((pph21SetahunTanpaNonRutin * (1d + 12d - (double) jumlahPeriodeKedepan)) / 12d) - pph21SampaiBulanSebelumnya;

            pph21KurangBayarBulanTerhitung = pph21KurangBayarBulanTerhitung - pph21KurangBayarBulanTerhitungTanpaNonRutin;

        } catch (Exception exc) {
            System.out.println(exc);
        }

        return pph21KurangBayarBulanTerhitung; // calcSalaryTax_NonContinueMonthly
    }

    /**
     * Menghitung pajak untuk gaji tidak tetap spt THR dan Bonus Algoritma : PPh
     * Sementara atas Bonus = PPh21 (Gaji bulan sebelumnya( di set spt gaji 2
     * bulan sebelumnya) + Bonus bulan ini ) - PPh21 ( Gaji bulan sebelumnya( di
     * set spt gaji 2 bulan sebelumnya) ) Jadi kita menggunakan perumpamaan jika
     * gaji bulan ini sama dengan gaji 2 bulan sebelujmnya.
     *
     * @param employeeId
     * @param payPeriod
     * @param prevPeriod
     * @param pajak
     * @return
     */
    public static double calcSalaryTax_Prev2_NonContinueMonthly(long employeeId, PayPeriod payPeriod, PayPeriod prevPeriod, Pajak pajak) {
        //public static double calcSalaryTax(long employeeId, Period period, Period prevPeriod){
        double pph21KurangBayarBulanTerhitung = 0.0;
        if (payPeriod == null || payPeriod.getOID() == 0) {
            return 0.0d;
        }

        if (prevPeriod == null || prevPeriod.getOID() == 0) {
            try {
                prevPeriod = PstPayPeriod.getPreviousPayPeriod(payPeriod.getOID());
            } catch (Exception exc) {
                System.out.println("calcSalaryTax_Prev2_NonContinueMonthly : " + exc);
            }
        }

        if (prevPeriod == null) {
            prevPeriod = new PayPeriod();
        }

        PayPeriod prev2Period = null;
        try {
            prev2Period = PstPayPeriod.getPreviousPayPeriod(prevPeriod.getOID());
        } catch (Exception exc) {
            System.out.println("calcSalaryTax_Prev2_NonContinueMonthly : " + exc);
        }

        if (prev2Period == null) {
            prev2Period = new PayPeriod();
        }

        try {
            String whereGajiTidakRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]
                    + " IN ( " + PstPayComponent.TUNJANGAN + ", " + PstPayComponent.BONUS_THR + ") ";

            double gajiNonRutinThisPeriod = PstPaySlip.getSumSalaryBenefit(payPeriod.getOID(), employeeId, whereGajiTidakRutin);
            if (Math.abs(gajiNonRutinThisPeriod) <= 0.0d) {
                return 0.0;
            }
            Employee employee = PstEmployee.fetchExc(employeeId);

            String payPostDeducCodes = PstSystemProperty.getValueByName("PAYROLL_TAX_POST_DEDUCTION");
            String whereGajiRutin = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] + " = " + PstPayComponent.GAJI + " AND COMP.COMP_CODE NOT IN (" + payPostDeducCodes + ")";

            double gajiRutinPrev2Period = prev2Period != null && prev2Period.getEndDate() != null ? PstPaySlip.getSumSalaryOfPeriod(prev2Period.getOID(), employeeId, whereGajiRutin) : 0.0d; // gaji tetap apada 2 periode sebelumnya, dalam boleh cross year sebelumnya, misal klu minta yg januari
            double gajiRutinYearToPrev2Period = prev2Period != null && prev2Period.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryYearToPeriod(prev2Period.getOID(), employeeId, whereGajiRutin) : 0.0d; // gaji tetap dari awal tahun sampai 2 periode sebelumnya, dalam tahun yg sama
            double gajiRutinOfPrevPeriod = prevPeriod != null && prevPeriod.getEndDate() != null ? PstPaySlip.getSumSalaryOfPeriod(prevPeriod.getOID(), employeeId, whereGajiRutin) : 0.0d;

            double gajiRutinYearToPrevPeriod = gajiRutinYearToPrev2Period + gajiRutinYearToPrev2Period; // gaji tetap dari awal tahun pada 2 periode sebelumnya, dalam tahun yg sama
            double gajiRutinOfPeriod = gajiRutinPrev2Period;  // gaji tetap dari di satu periode sebelum periode terhitung

            int jumlahPeriodeKedepan = 0; //  hitung dari awal tahun            

            if (payPeriod.getPayProcDateClose() != null) {
                jumlahPeriodeKedepan = 12 - payPeriod.getPayProcDateClose().getMonth();
            } else {
                jumlahPeriodeKedepan = 12 - payPeriod.getEndDate().getMonth();
            }

            double gajiRutinSetahun = ((gajiRutinYearToPrevPeriod + gajiRutinOfPeriod) * 12d) / (1d + 12d - (double) jumlahPeriodeKedepan);
            //double gajiNonRutinYearToPeriod = PstPaySlip.getSumSalaryYearToPeriod(payPeriod.getOID(), employeeId, whereGajiTidakRutin);                        
            double gajiTotalSetahun = gajiRutinSetahun;// + gajiNonRutinYearToPeriod;  GAJI NON RUTIN DIHITUNG DIBAWAH

            double biayaJabatan = 0.0; // biaya jabatan rutin

            int employeeTaxType = getEmployeeTypeMap(employee.getEmpCategoryId());  // dapat dari jenis karyawan                        
            double pendapatanNettoSetahun = 0.0;
            switch (employeeTaxType) {
                case EMPLOYEE_TAX_TYPE_PERMANENT:
                    //update by devin 2014-02-15
                    biayaJabatan = Math.round(gajiTotalSetahun * (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN));
                        
                    //update by priska karena biaya jabatan tidak boleh melebihi jumlah payslip dikali rata rata bulan dalam pajak 20151217
                    int countJumlahPaySlip = PstPaySlip.getJumlahPayslip(employeeId, ""+(payPeriod.getEndDate().getYear()+1900));
                        double nilaiMaksimum = (countJumlahPaySlip * TaxCalculator.BIAYA_JABATAN_BULANAN);
                        if (biayaJabatan > nilaiMaksimum){
                            biayaJabatan = nilaiMaksimum;
                        }
                        
                    if ((biayaJabatan) <= (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX)) {
                        pendapatanNettoSetahun = gajiTotalSetahun - biayaJabatan;
                    } else {
                        pendapatanNettoSetahun = gajiTotalSetahun - (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX);
                    }
                    break;

                case EMPLOYEE_TAX_TYPE_NON_PERMANENT:
                    pendapatanNettoSetahun = gajiTotalSetahun;
                    break;

                case EMPLOYEE_TAX_TYPE_CONSULTANT:
                    //update by devin 2014-02-15
                    pendapatanNettoSetahun = (pajak != null && pajak.getNettoPercenKonsultan() != 0 ? pajak.getNettoPercenKonsultan() : NETTO_PERCENT_CONSULTANT) * gajiTotalSetahun;
                    break;
                default:
                    pendapatanNettoSetahun = gajiTotalSetahun;
            }

            String whereDeduc = " AND " + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] + " = " + PstPayComponent.GAJI + " AND COMP.COMP_CODE IN (" + payPostDeducCodes + ")";;
            double payTaxDeducCompValPrevPeriod = prevPeriod != null && prevPeriod.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryDeductionYearToPeriod(prevPeriod.getOID(), employeeId, whereDeduc) : 0.0d; // gaji tetap dari awal tahun sampai periode sebelumnya, dalam tahun yg sama            
            double payTaxDeducCompVal = prevPeriod != null && prevPeriod.getEndDate().getYear() == payPeriod.getEndDate().getYear() ? PstPaySlip.getSumSalaryDeduction(payPeriod.getOID(), employeeId, whereDeduc) : 0.0d;
            pendapatanNettoSetahun = pendapatanNettoSetahun - (((payTaxDeducCompValPrevPeriod + payTaxDeducCompVal) * 12d) / (1d + 12d - (double) jumlahPeriodeKedepan));

            int maritalStatus = STATUS_DIRI_SENDIRI; // status perkawinan ambil dari status kawin pajak , default STATUS_DIRI_SENDIRI            

            Marital marital = null;
            if (employee.getTaxMaritalId() != 0) {
                marital = PstMarital.fetchExc(employee.getTaxMaritalId());
            } else {
                marital = PstMarital.fetchExc(employee.getMaritalId());
            }

            if (marital.getMaritalCode().indexOf(STATUS_PREFIX_SINGLE) > -1) { // jika ada kode single
                maritalStatus = STATUS_DIRI_SENDIRI;
            } else {
                if (marital.getMaritalCode().indexOf(STATUS_PREFIX_MARRIED) > -1) { // jika ada kode single
                    switch (marital.getNumOfChildren()) {
                        case 0:
                            maritalStatus = STATUS_KAWIN;
                            break;
                        case 1:
                            maritalStatus = STATUS_KAWIN_ANAK_1;
                            break;
                        case 2:
                            maritalStatus = STATUS_KAWIN_ANAK_2;
                            break;
                        case 3:
                            maritalStatus = STATUS_KAWIN_ANAK_3;
                            break;
                        default:
                            if (marital.getNumOfChildren() > 3) {
                                maritalStatus = STATUS_KAWIN_ANAK_3;
                            } else {
                                maritalStatus = STATUS_KAWIN;
                            }
                            break;
                    }
                }

            }

            double ptkpSetahun = 0;
            PTKP ptkp = getPTKP(payPeriod.getEndDate());
            if (employeeTaxType == EMPLOYEE_TAX_TYPE_PERMANENT || employeeTaxType == EMPLOYEE_TAX_TYPE_NON_PERMANENT) {
                switch (maritalStatus) {
                    case STATUS_DIRI_SENDIRI:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpDiriSendiri() != 0 ? pajak.getPtkpDiriSendiri() : ptkp.getPTKP_DIRI_SENDIRI());
                        break;
                    case STATUS_KAWIN:
                        //update by devin 2014-02-15

                        ptkpSetahun = (pajak != null && pajak.getPtkpKawin() != 0 ? pajak.getPtkpKawin() : ptkp.getPTKP_KAWIN());
                        break;
                    case STATUS_KAWIN_ANAK_1:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak1() != 0 ? pajak.getPtkpKawinAnak1() : ptkp.getPTKP_KAWIN_ANAK_1());
                        break;
                    case STATUS_KAWIN_ANAK_2:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak2() != 0 ? pajak.getPtkpKawinAnak2() : ptkp.getPTKP_KAWIN_ANAK_2());
                        break;
                    case STATUS_KAWIN_ANAK_3:
                        //update by devin 2014-02-15
                        ptkpSetahun = (pajak != null && pajak.getPtkpKawinAnak3() != 0 ? pajak.getPtkpKawinAnak3() : ptkp.getPTKP_KAWIN_ANAK_3());
                        break;
                }
            }

            // perhitungan pph21 dari pendapatan non rutin , sampai bulan terhitung
            double gajiNonRutinToPeriod = PstPaySlip.getSumSalaryBenefit/*getSumSalaryYearToPeriod*/(payPeriod.getOID(), employeeId, whereGajiTidakRutin);
            double biayaJabatanNonRutinToPeriod = 0.0; // biaya jabatan rutin dihitung bulanan                        
            double pendapatanNettoNonRutinToPeriod = 0.0;  // bulanan  

            switch (employeeTaxType) {
                case EMPLOYEE_TAX_TYPE_PERMANENT:
                    biayaJabatanNonRutinToPeriod = Math.round(gajiNonRutinToPeriod * (pajak != null && pajak.getBiayaJabatanPersen() != 0 ? pajak.getBiayaJabatanPersen() : BIAYA_JABATAN_PERSEN));
                    if ((biayaJabatan + biayaJabatanNonRutinToPeriod) <= (pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX)) {
                        pendapatanNettoNonRutinToPeriod = gajiNonRutinToPeriod - biayaJabatanNonRutinToPeriod;
                    } else {
                        pendapatanNettoNonRutinToPeriod = gajiNonRutinToPeriod - ((pajak != null && pajak.getBiayaJabatanMaksimal() != 0 ? pajak.getBiayaJabatanMaksimal() : BIAYA_JABATAN_MAX) - biayaJabatan);
                    }
                    break;
                case EMPLOYEE_TAX_TYPE_NON_PERMANENT:
                    pendapatanNettoNonRutinToPeriod = gajiNonRutinToPeriod;
                    break;
                case EMPLOYEE_TAX_TYPE_CONSULTANT:
                    pendapatanNettoNonRutinToPeriod = (pajak != null && pajak.getNettoPercenKonsultan() != 0 ? pajak.getNettoPercenKonsultan() : NETTO_PERCENT_CONSULTANT) * gajiNonRutinToPeriod;
                    break;
                default:
                    pendapatanNettoNonRutinToPeriod = gajiNonRutinToPeriod;
            }

            pendapatanNettoNonRutinToPeriod = Math.floor(pendapatanNettoNonRutinToPeriod / 1000d) * 1000d;

            double pkpSetahunGabung = pendapatanNettoSetahun + pendapatanNettoNonRutinToPeriod - ptkpSetahun;
            if (pkpSetahunGabung < 0.00) {
                pkpSetahunGabung = 0.0;
            }

            pkpSetahunGabung = Math.floor(pkpSetahunGabung / 1000d) * 1000d;
            double pph21SetahunGabung = hitungTarifPPH21(pkpSetahunGabung, pajak, employee.getNpwp());

            double pph21SampaiBulanSebelumnya = PstPaySlip.getSumTaxYearToPreviousPeriod(payPeriod.getOID(), employeeId);
            pph21KurangBayarBulanTerhitung = ((pph21SetahunGabung * (1d + 12d - (double) jumlahPeriodeKedepan)) / 12d) - pph21SampaiBulanSebelumnya;

            double pkpSetahunTanpaNonRutin = pendapatanNettoSetahun - ptkpSetahun;
            if (pkpSetahunTanpaNonRutin < 0.00) {
                pkpSetahunTanpaNonRutin = 0.0;
            }

            pkpSetahunTanpaNonRutin = Math.floor(pkpSetahunTanpaNonRutin / 1000d) * 1000d;

            double pph21SetahunTanpaNonRutin = hitungTarifPPH21(pkpSetahunTanpaNonRutin, pajak, employee.getNpwp());

            double pph21KurangBayarBulanTerhitungTanpaNonRutin = ((pph21SetahunTanpaNonRutin * (1d + 12d - (double) jumlahPeriodeKedepan)) / 12d) - pph21SampaiBulanSebelumnya;

            pph21KurangBayarBulanTerhitung = pph21KurangBayarBulanTerhitung - pph21KurangBayarBulanTerhitungTanpaNonRutin;

        } catch (Exception exc) {
            System.out.println(exc);
        }

        return pph21KurangBayarBulanTerhitung; // calcSalaryTax_NonContinueMonthly
    }

    private static void updateSlipComp(long periodId, long employeeId, double pph21) {
        long paySlipId = 0;
        String whereSlip = " EMPLOYEE_ID=" + employeeId + " AND PERIOD_ID=" + periodId + " ";
        Vector listPaySlip = PstPaySlip.list(0, 0, whereSlip, "");
        if (listPaySlip != null && listPaySlip.size() > 0) {
            for (int h = 0; h < listPaySlip.size(); h++) {
                PaySlip pSlip = (PaySlip) listPaySlip.get(h);
                paySlipId = pSlip.getOID();
            }
        }
        String whereClause = " PAY_SLIP_ID=" + paySlipId + " AND COMP_CODE='PPH21' ";
        /* SELECT * FROM pay_slip_comp WHERE PAY_SLIP_ID=504404593041301260 AND COMP_CODE='PPH21' */
        Vector listPaySlipComp = PstPaySlipComp.list(0, 0, whereClause, "");
        if (listPaySlipComp != null && listPaySlipComp.size() > 0) {
            PaySlipComp paySlipComp = new PaySlipComp();
            for (int i = 0; i < listPaySlipComp.size(); i++) {
                PaySlipComp pSlipComp = (PaySlipComp) listPaySlipComp.get(i);
                paySlipComp.setCompCode(pSlipComp.getCompCode());
                paySlipComp.setCompValue(pph21);
                paySlipComp.setPaySlipId(paySlipId);
                paySlipComp.setOID(pSlipComp.getOID());
            }
            try {
                PstPaySlipComp.updateExc(paySlipComp);
            } catch (Exception ex) {
                System.out.println("updateSlipComp => " + ex.toString());
            }
        }
    }

    public static double hitungTarifPPH21(double pkpSetahun, Pajak pajak, String npwp) {

        double pph = 0;
        /**
         * update by devin 2014-02-17 double range_1= 50000000; double percen_1
         * = 0.05; double range_2= 250000000; double percen_2 = 0.15; double
         * range_3= 500000000; double percen_3 = 0.25; double percen_4 = 0.30;
         */
         double range_1 = 0;
        double percen_1 = 0;
        double range_2 = 0;
        double percen_2 = 0;
        double range_3 = 0;
        double percen_3 = 0;
        double percen_4 = 0;

        if (npwp.equals("000000000000000") || npwp.equals("0")){
         range_1 = (pajak != null && pajak.getRange1() != 0 ? pajak.getRange1() : 50000000);
         percen_1 = (pajak != null && pajak.getPercen1() != 0 ? pajak.getPercen1() : 0.06);
         range_2 = (pajak != null && pajak.getRange2() != 0 ? pajak.getRange2() : 250000000);
         percen_2 = (pajak != null && pajak.getPercen2() != 0 ? pajak.getPercen2() : 0.16);
         range_3 = (pajak != null && pajak.getRange3() != 0 ? pajak.getRange3() : 500000000);
         percen_3 = (pajak != null && pajak.getPercen3() != 0 ? pajak.getPercen3() : 0.26);
         percen_4 = (pajak != null && pajak.getPercen4() != 0 ? pajak.getPercen4() : 0.31);    
        } else {
         range_1 = (pajak != null && pajak.getRange1() != 0 ? pajak.getRange1() : 50000000);
         percen_1 = (pajak != null && pajak.getPercen1() != 0 ? pajak.getPercen1() : 0.05);
         range_2 = (pajak != null && pajak.getRange2() != 0 ? pajak.getRange2() : 250000000);
         percen_2 = (pajak != null && pajak.getPercen2() != 0 ? pajak.getPercen2() : 0.15);
         range_3 = (pajak != null && pajak.getRange3() != 0 ? pajak.getRange3() : 500000000);
         percen_3 = (pajak != null && pajak.getPercen3() != 0 ? pajak.getPercen3() : 0.25);
         percen_4 = (pajak != null && pajak.getPercen4() != 0 ? pajak.getPercen4() : 0.30);    
        }
        
        pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>" + TITLE_TARIF_PROGRESSIVE + "</td><td>&nbsp;</td><td>&nbsp;</td></tr>");

        if (pkpSetahun <= range_1) {
            pph = pkpSetahun * percen_1;
            pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 1 : " + Formater.formatNumber(0, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " <= " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + "</td><td></td><td align=\"left\">" + (percen_1 * 100) + "%" + " x " + Formater.formatNumber(pkpSetahun, FORMAT_DECIMAL) + " = " + Formater.formatNumber((pkpSetahun * percen_1), FORMAT_DECIMAL) + "</td></tr>");
        } else {
            if (pkpSetahun <= range_2) {
                pph = range_1 * percen_1 + (pkpSetahun - range_1) * percen_2;
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 1 : " + Formater.formatNumber(0, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " <= " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + "</td><td></td><td align=\"left\">" + (percen_1 * 100) + "%" + " x " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + " = " + Formater.formatNumber((range_1 * percen_1), FORMAT_DECIMAL) + "</td></tr>");
                pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 2 : " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " <= " + Formater.formatNumber(range_2, FORMAT_DECIMAL) + "</td><td></td><td align=\"left\">" + (percen_2 * 100) + "%" + " x " + Formater.formatNumber(pkpSetahun - range_1, FORMAT_DECIMAL) + " = " + Formater.formatNumber(((pkpSetahun - range_1) * percen_2), FORMAT_DECIMAL) + "</td></tr>");

            } else {
                if (pkpSetahun <= range_3) {
                    pph = (range_1 * percen_1) + ((range_2 - range_1) * percen_2) + ((pkpSetahun - range_2) * percen_3);
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 1 : " + Formater.formatNumber(0, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " <= " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + "</td><td></td><td align=\"left\">" + (percen_1 * 100) + "%" + " x " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + " = " + Formater.formatNumber((range_1 * percen_1), FORMAT_DECIMAL) + "</td></tr>");
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 2 : " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " <= " + Formater.formatNumber(range_2, FORMAT_DECIMAL) + "</td><td></td><td align=\"left\">" + (percen_2 * 100) + "%" + " x " + Formater.formatNumber(range_2 - range_1, FORMAT_DECIMAL) + " = " + Formater.formatNumber(((range_2 - range_1) * percen_2), FORMAT_DECIMAL) + "</td></tr>");
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 3 : " + Formater.formatNumber(range_2, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " <= " + Formater.formatNumber(range_3, FORMAT_DECIMAL) + "</td><td></td><td align=\"left\">" + (percen_3 * 100) + "%" + " x " + Formater.formatNumber(pkpSetahun - range_2, FORMAT_DECIMAL) + " = " + Formater.formatNumber(((pkpSetahun - range_2) * percen_3), FORMAT_DECIMAL) + "</td></tr>");
                } else {
                    pph = (range_1 * percen_1) + ((range_2 - range_1) * percen_2) + ((range_3 - range_2) * percen_3) + (pkpSetahun - range_3) * percen_4;
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 1 : " + Formater.formatNumber(0, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " <= " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + "</td><td></td><td align=\"left\">" + (percen_1 * 100) + "%" + " x " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + " = " + Formater.formatNumber((range_1 * percen_1), FORMAT_DECIMAL) + "</td></tr>");
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 2 : " + Formater.formatNumber(range_1, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " <= " + Formater.formatNumber(range_2, FORMAT_DECIMAL) + "</td><td></td><td align=\"left\">" + (percen_2 * 100) + "%" + " x " + Formater.formatNumber(range_2 - range_1, FORMAT_DECIMAL) + " = " + Formater.formatNumber(((range_2 - range_1) * percen_2), FORMAT_DECIMAL) + "</td></tr>");
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 3 : " + Formater.formatNumber(range_2, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " <= " + Formater.formatNumber(range_3, FORMAT_DECIMAL) + "</td><td></td><td align=\"left\">" + (percen_3 * 100) + "%" + " x " + Formater.formatNumber(pkpSetahun - range_2, FORMAT_DECIMAL) + " = " + Formater.formatNumber(((range_3 - range_2) * percen_3), FORMAT_DECIMAL) + "</td></tr>");
                    pajak.setHtmlDetailCalc(pajak.getHtmlDetailCalc() + "<tr><td>Range 4 : " + Formater.formatNumber(range_3, FORMAT_DECIMAL) + " < " + TITLE_PKP_SYMBOL + " </td><td></td><td align=\"left\">" + (percen_4 * 100) + "%" + " x " + Formater.formatNumber(pkpSetahun - range_3, FORMAT_DECIMAL) + " = " + Formater.formatNumber(((pkpSetahun - range_3) * percen_4), FORMAT_DECIMAL) + "</td></tr>");

                }
            }
        }

        /*
         if(pkpSetahun>0){ // range 0-50 jt : 5%
         pph = pkpSetahun * percen_1 /100;
         }
      
         if(pkpSetahun>=range_1){
         // range 50-250 jt : 15%
         if(pkpSetahun>=range_2){
         pph = pph + (range_2 - range_1) * percen_2/100;
         } else {
         pph = pph + (pkpSetahun - range_1) * percen_2/100;           
         }
         }

         if(pkpSetahun>=range_2){
         // range 250 - 500 jt : 25%
         if(pkpSetahun>=range_3){
         pph = pph + (range_3 - range_2) * percen_3/100;
         } else {
         pph = pph + (pkpSetahun - range_2) * percen_3/100;           
         }
         }
      
         if(pkpSetahun>=range_3){
         // range diatas 500 jt : 30%
         pph = pph + (pkpSetahun - range_3) * percen_4/100;           
         }*/
        return pph;
    }

    
    /**
     * @return the taxCalcPattern
     */
    public static int getTaxCalcPattern() {
        return taxCalcPattern;
    }

    /**
     * @param aTaxCalcPattern the taxCalcPattern to set
     */
    public static void setTaxCalcPattern(int aTaxCalcPattern) {
        taxCalcPattern = aTaxCalcPattern;
    }
}
