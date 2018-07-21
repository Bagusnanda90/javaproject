/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;

import java.util.Date;

/**
 * 
 * @author Ketut Kartika T
 * @desc interface ini akan diimplementasi oleh class yang mempunyai aturan dan konfigurasi tentang leave dan DP yang specific untuk suatu perusahaan
 */
public interface I_LeaveConfig {
    
    public final int AL_ENTITLE_BY_COMMENCING   = 0 ; 
    public final int AL_ENTITLE_ANUAL           = 1;
    public final String AL_ENTITLE_TYPE[]       = {"Entitle by Commencing Date", "Entitle by new year"};
    
    public final int AL_EARNED_BY_TOTAL     = 0;
    public final int AL_EARNED_PER_MONTH    = 1;
    public final String AL_EARNED_TYPE[]    = {"AL earned by total", "AL earned per month"};
    
    public final int AL_DISTRIBUTION_EVERY_MONTH            = 0;
    public final int AL_DISTRIBUTION_EVERY_COMMENCING_DATE  = 1;
    public final String AL_DISTRIBUTION_TYPE[]              = {"AL distribution Every month","AL distribution every commencing date"};
    
    /**
     * @Desc configurasi Al apakah bileh minus atau tidak 
     */    
    public final int AL_CAN_MINUS           = 0;
    public final int AL_CAN_NOT_MINUS       = 1;
    public final String AL_VALUE_TYPE[]     = {"AL CAN MINUS","AL CAN NOT MINUS"};
    
    /**
     * @Desc configurasi Al apakah bileh minus atau tidak 
     */
    public final int LL_CAN_MINUS           = 0;
    public final int LL_CAN_NOT_MINUS       = 1;
    public final String LL_VALUE_TYPE[]     = {"LL CAN MINUS","LL CAN NOT MINUS"};  
    
        
    /**
     * @descr Method for getting number of AL depending on employee Level and Employee type
     * @param level
     * @param employeeType
     * @return
     */
    public int getALEntitleAnualLeave(String level, String employeeType);
    
    /**
     * @descr set configuration for AL number entitling for employee depending on Level and type of employee
     * @param level
     * @param employeeType
     * @param numberAL
     */
    public void setALEntitleAnualLeave(String level, String employeeType, int numberAL);
    
    
    /**
     * @descr setter method for configuration of AL entetling period the value shall be AL_ENTITLE_BY_COMMENCING or AL_ENTITLE_ANUAL
     * @param al_entitle_by
     */
    public void setALEntitleBy(int al_entitle_by);
    /**
     * @descr getter method for configuration of AL entetling period. the return value will be AL_ENTITLE_BY_COMMENCING or AL_ENTITLE_ANUAL 
     * @return
     */
    public int getALEntitleBy();
    
    
    /**
     * Set type of AL earned
     * @param al_earn_by  : AL_EARNED_BY_TOTAL=0 or  AL_EARNED_PER_MONTH=1;
     */
    public void setALEarnedBy(int al_earn_by );
    
    /**
     * get type of AL earned
     * @return
     */
    public int getAlEarnedBy();
    
    /**
     * set if AL stock can be minus ( more AL is taken by employee then earned )
     * @param ALminus
     */
    public void setALStockMinus(boolean ALminus);
    
    /**
     * get AL stock minus configuration
     * @return boolean
     */
    public boolean getALStockMinus();

    /**
     * set if LL stock can be minus ( more LL is taken by employee then earned )
     * @param ALminus
     */
    public void setLLStockMinus(boolean LLminus);

    /**
     * get LL stock minus configuration
     * @return boolean
     */
    public boolean getLLStockMinus();

    /**
     * @descr set configuration for expiration days of AL after retreating of entitling date. 
     * @param days
     */
    public void setALExpirationDay(int days);
    /**
     * @descr get configuration for expiration days of AL after retreating of entitling date. 
     * @return
     */
    public int getALExpirationDay();
    
    
    /**
     * @descr set configuration for warning days before AL for employee expired 
     * @param days
     */
    public void setALExpWarning(int days);
    /**
     * @descr get configuration for warning days before AL for employee expired 
     * @param days
     */
    public int getALExpWarning();
    

    /**
     * @descr Method for getting number of AL during LL entetling depending on employee Level and Employee type
     * @param level
     * @param employeeType
     * @return
     */
    public int getALEntitlebyLL(String level, String employeeType);
    
    /**
     * @descr set configuration for AL number entitling for employee DURING LL ENTETLING depending on Level and type of employee
     * @param level
     * @param employeeType
     * @param numberAL
     */
    public void setALEntitleLL(String level, String employeeType, int numberAL);
    
    
    /**
     * @descr menset konfigurasi list dari jangka waktu dan jumlah LL yang diperoleh ( bisa lebih dari satu setting : e.g.  : 5 tahun = 26  , 7 tahun = 2 ) tergantung dari Level karyawan.
     * @param level
     * @param employeeType
     * @param monthsLoS : Length of service in months 
     * @param numberLL
     */
    public void setLLEntitle(String level, String employeeType, int monthsLoS, int numberLL);
    
    
    /**
     * @descr menset konfigurasi list dari jangka waktu dan jumlah LL yang diperoleh ( bisa lebih dari satu setting : e.g.  : 5 tahun = 26  , 7 tahun = 2 ) tergantung dari Level karyawan.
     * @param level
     * @param employeeType
     * @param monthsLoS : Length of service in months 
     * @param numberLL
     * @param validMonths : length of validity in months
     */
    public void setLLEntitle(String level, String employeeType, int monthsLoS, int numberLL, int validMonths);
    
    
    /**
     * @descr get konfigurasi list dari jangka waktu dan jumlah LL yang diperoleh ( bisa lebih dari satu setting : e.g.  : 5 tahun = 26  , 7 tahun = 2 ) tergantung dari Level karyawan.
     * @param level
     * @param employeeType
     * @param monthsLoS : Length of service in months 
     * @param numberLL
     */
    public int getLLEntile(String level, String employeeType, int monthsLoS);


    /**
     * @descr get konfigurasi list dari jangka waktu validity LL ( misal yang didapat setiap 5 tahun valid 4 tahun)tergantung dari Level karyawan.
     * @param level
     * @param employeeType
     * @param monthsLoS : Length of service in months 
     * @param numberLL
     * @return range of months of validity of LL
     */
    public int getLLValidityMonths(String level, String employeeType, int monthsLoS);
    
     
    /**
     * menset jumlah minimal LL yang boleh di ambil dalam satu tahun
     *
     */
    public void setMinLLTakenAnual(int minLL);
    
    /**
     * mengambil jumlah minimal LL yang boleh di ambil dalam satu tahun
     *
     */
    public int getMinLLTakenAnual();
    
    
    /**
     * menset jumlah maximum LL yang boleh di ambil dalam satu tahun
     *
     */
    public void setMaxLLTakenAnual(int minLL);
    
    /**
     * mengambil jumlah maximum LL yang boleh di ambil dalam satu tahun
     *
     */
    public int getMaxLLTakenAnual();    
  
    
        /**
     * @descr set configuration for expiration days of LL after retreating of entitling date. 
     * @param days
     */
    public void setLLExpirationDay(int days);
    /**
     * @descr get configuration for expiration days of LL after retreating of entitling date. 
     * @return
     */
    public int getLLExpirationDay();
    
    
    
    /**
     * @descr set configuration for warning days before LL expiration for employee expires
     * @param days
     */
    public void setLLExpWarning(int days);
    
    /**
     * @descr get configuration for warning days before LL for employee  expires
     * @param days
     */
    public int getLLExpWarning();
    
    /**
     * @param set standard DP entitle based on employee level
     * @param level
     * @param dpDays
     */
    public void setDPStandardEntitle(String level, int dpDays);
    
    /**
     * @descr get standard DP entitle based on employee level
     * @param level
     * @return
     */
    public int getDPStandardEntitle(String level);

    
    /**
     * @descr getDP entitle for a specific employee on a specific date. 
     * This will calculate DP entitle according to company regulation.
     * Sample : Data will be included on calculation :
     *     - employee : type, religion ( to know his public holiday ), birthday
     *     - employee working schedule
     * @param payNum
     * @param date
     * @return
     */
    public int getDPEntitleByDate(String payNum, Date date);    
    
    /**
     * @desc setingan AL distribution type 
     */    
    public int getDistributionBy(); 


    /**
     * @Desc Untuk mendapatkan limit date peride bulanan
     * Untuk mengecek batas tanggal untu menentukan periode bulanan, seperti bila limit adalah 17,
     * kemudian karyawan tersebut masuk tanggal 15,karena 15 kurang dari 17 maka akan dapat periode bulana pada bulan sekarang,
     * Sedangkan bila commencing date adalah 19, karena 19 lebih besar dari 17, maka periode bulanan yang didapt adalah periode berikutnya
     * @return
     */
    public int getLimitPeriode();

    
    
}
