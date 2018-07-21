
package com.dimata.harisma.entity.leave;


import com.dimata.harisma.entity.attendance.AlStockTaken;
import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.DpStockTaken;
import com.dimata.harisma.entity.attendance.LlStockTaken;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.search.SrcLeaveAppAlClosing;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author bayu
 */

public interface I_Leave{
              
    /**
     *
     */
    public final int INTERVAL_LL_5_YEAR = 0;
    /**
     *
     */
    public final int INTERVAL_LL_8_YEAR = 1;
    
    /**
     *
     */
    public final int LEAVE_APPROVE_1 =  1; // level dari leave approval :  contoh 1 = department head only
    /**
     *
     */
    public final int LEAVE_APPROVE_2 =  2; // level dari leave approval :  contoh 2 = department head && HR Manager
    /**
     *
     */
    public final int LEAVE_APPROVE_3 =  3; // level dari leave approval :  contoh 3 = HR Manager && GM
    /**
     *
     */
    public final int LEAVE_APPROVE_4 =  4; // level dari leave approval :  contoh 4 = HR Manager && HR Director
    /**
     *
     */
    public final int LEAVE_APPROVE_5 =  5; // level dari leave approval :  contoh 5 = HRD (untuk intimas)
    
    public final int LEAVE_APPROVE_6 =  6; // level dari leave approval :  contoh 5 = HRD (untuk intimas)
    
    public final int LEAVE_APPROVE_7 =  7; // level dari leave approval :  contoh 5 = HRD (untuk intimas)
    
    public final int LEAVE_APPROVE_8 =  8; // level dari leave approval :  contoh 5 = HRD (untuk intimas)
    
    /**
     *
     */
    public final int EXTEND_1_MONTH  = 1;  //month
    /**
     *
     */
    public final int EXTEND_2_MONTH  = 2;  //month
    /**
     *
     */
    public final int EXTEND_3_MONTH  = 3;  //month
    /**
     *
     */
    public final int EXTEND_6_MONTH  = 6;  //month
    /**
     *
     */
    public final int EXTEND_12_MONTH = 12; //month
    
    //--------------------------------------untuk configurasi LL ------------------------
    /**
     *
     */
    public final int LL_ONCE_A_YEAR     = 0;
    /**
     *
     */
    public final int LL_TWICE_A_YEAR    = 1;
    
    /**
     *
     */
    public final int CONFIGURATION_LL   = LL_TWICE_A_YEAR;//if = 0 - > LL once a year ; if = 1 -> LL twice a year

    /**
     *
     */
    public final int AL_ENTITLE_BY_COMMENCING   = 0 ;
    /**
     *
     */
    public final int AL_ENTITLE_ANUAL           = 1;
    /**
     *
     */
    public final int AL_ENTITLE_BY_PERIOD       = 2;
    /**
     *
     */
    public final int AL_ENTITLE_BY_COMMENCING_PERIODE       = 3;
    /**
     *
     */
    public final String AL_ENTITLE_TYPE[]       = {"Entitle by Commencing Date", "Entitle by new year", "Entitle by period", "Entitle by commencing period"};

    /**
     *
     */
    public final int AL_EARNED_BY_TOTAL     = 0;
    /**
     *
     */
    public final int AL_EARNED_PER_MONTH    = 1;
    /**
     *
     */
    public final String AL_EARNED_TYPE[]    = {"AL earned by total", "AL earned per month"};

    /**
     *
     */
    public final int AL_DISTRIBUTION_EVERY_MONTH            = 0;
    /**
     *
     */
    public final int AL_DISTRIBUTION_EVERY_COMMENCING_DATE  = 1;
    /**
     *
     */
    public final String AL_DISTRIBUTION_TYPE[]              = {"AL distribution Every month","AL distribution every commencing date"};

    /**
     * @Desc configurasi Al apakah boleh minus atau tidak
     */
    public final int AL_CAN_MINUS           = 0;
    /**
     *
     */
    public final int AL_CAN_NOT_MINUS       = 1;
    /**
     *
     */
    public final String AL_VALUE_TYPE[]     = {"AL CAN MINUS","AL CAN NOT MINUS"};

    /**
     * @Desc configurasi Ll apakah bileh minus atau tidak
     */
    public final int LL_CAN_MINUS           = 0;
    /**
     *
     */
    public final int LL_CAN_NOT_MINUS       = 1;
    /**
     *
     */
    public final String LL_VALUE_TYPE[]     = {"LL CAN MINUS","LL CAN NOT MINUS"};
    
        
    //update by satrya 2013-12-10
    public static final int LEAVE_CONFIG_SEND_EMAIL_CREATE_DEP_HEAD = 0;
    public static final int LEAVE_CONFIG_SEND_EMAIL_CREATE_BY_LEAVE_FORM = 1;
    
    
    public static final int LEAVE_CONFIG_AFTER_APPROVALL_HRD_NO_EXECUTE = 0;
    public static final int LEAVE_CONFIG_AFTER_APPROVALL_HRD_YES_EXECUTE = 1;
    
    
    public static final int LEAVE_CONFIG_REQUEST_DP_TAKEN_NOT_LONG = 0;
    public static final int LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG = 1;
    
    //Update By Agus 12-02-2014
    public static final boolean LEAVE_APPLICATION_IS_DAY =true;
    public static final boolean LEAVE_APPLICATION_IS_NOT_DAY =false;
    
    /*
    public final int LEAVE_CONFIG_HARDROCK  = 0;
    public final int LEAVE_CONFIG_INTIMAS   = 1;
    public final int LEAVE_CONFIG_NIKKO     = 2;
    public final int LEAVE_CONFIG_SANUR     = 3;
    public final int LEAVE_CONFIG_MELIA     = 4;
     *
     */
    
    /**
     * <pre>Create By satrya 2013-12-11</pre>
     * <pre>Konfigurasi dp bisa di pilih lebih panjang</pre>
     * @return 
     */
    public int getConfigurationLeaveDpTakenLong();
    /**
     * <pre>Create By satrya 2013-12-11</pre>
     * <pre>Konfigurasi approvall dan langsung execute</pre>
     * <pre>ketika hrd sudah approve maka langsung excecute</pre>
     * @return 
     */
    public int getConfigurationLeaveApprovall();
    //--------------------------------------untuk configurasi LL ------------------------
    /**
     * updat by satrya 2013-12-10
     * Keterangan : untuk konfigurasi apakah dep head atau yg membuat leave
     * @return 
     */
    public int getConfigurationSendMail();
            //update by satrya 2013-0702
    /**
     * Keterangan: apakah konfigurasinya boleh mengambil automatick atau menambahkan stock DP secara automatic DP di service center
     * @return 
     */
    public  boolean getAutomaticDPStockTaken();
    /**
     *
     * @return
     */
    public String[] getStrLevels();
    
    /**
     *
     * @return
     */
    public String[] getStrCategory();
    
    /**
     *
     * @return
     */
    public int[] getIntervalLLinMonths();
    
    
    /**
     *
     * @return
     */ 
    public int[] getIntervalALinMonths();
        
    /** lamannya DP expired dalam bulan
     * @param levelArg : Nama dari  level
     * @return
     */
    public int getDpValidity(String levelArg);
   
    /**
     * @create by satrya 2013 - 10-03
     * @desc: konfiguration minus dp
     * @return 
     */
    public boolean getConfigurationDpMinus();
    /**
     * @desc mengambil nilai index dari level employee
     * @param level
     * @return
     */
    public int getIndexLevel(String level);
    
    /**
     * @param type
     * @desc mengambil nilai index dari type employee
     * @return
     */
    public int getIndexType(String type);
    
     /**
     * @desc mengecek apakah employee DO atau tidak, jika DO maka akan diset True jika tidak maka di set false
     * @param employee Employee
     * @param date Date
     * @return boolean
     */
    public boolean isScheduleOff(Employee employee, Date date,LeaveConfigurationParameter leaveConfigurationParameter);

    /**
     * @desc mengecek apakah employee hadir pd tgl tsb
     * @param employee Employee
     * @param date Date
     * @return boolean
     */
    public boolean isPresent(Employee employee, Date date);
    
    /**
     * @desc mengecek apakah employee mendapat public holiday pada tgl tertentu
     * @param employee Employee
     * @param date Date
     * @return boolean
     */
    public boolean isPublicHoliday(Employee employee, Date date);
    
    /**
     * @desc mengecek apakah employee sedang ulang tahun pada tgl tertentu
     * @param employee Employee
     * @param date Date
     * @return boolean
     */
    public boolean isBirthDay(Employee employee, Date date);
    
  
    /**
     * @desc menambil level employee per id employee
     *      - level yang dimaksud adalah level kerja/type
     * @param employeeId
     * @return string dari level
     */
    
    public String getLevel(long employeeId);
    
    /**
     * @param employeeId
     * @desc mengabil employee category per employee id
     * @return String dari category
     */
    public String getCategory(long employeeId);
    
    
    /**
     * @param employeeId
     * @return
     * @desc mengambil banyak approal yang diperlukan dalam Leave Reaquest
     */
    public int getMaxApproval(long employeeId);    
   
    /**     
     * @return
     * @desc mengambil level paling tinggi dari leave approval yang diconfigurasikan
     */
    public int getMaxLeaveApprovalLevel();    
    
    /**     
     * @return
     * @desc mengambil apakah level approval yang di check ada
     */
    public boolean isLeaveApprovalLevel(int level);    

    
    /**
     *
     * @param employeeId
     * @return
     */
    public Vector getManagerApproval(long employeeId);
    /* untuk mendapatkan manager approval */
    
    
    /**
     *
     * @param employeeId
     * @return
     */
    public Vector getApprovalDepartmentHead(long employeeId);
    /* Untuk mendapatkan departemnt head approval*/
    
        //priska menambahkan leave approval top link 20150807
    public Vector getApprovalEmployeeTopLink(long employee_id, int typeOfLink);
    
            //priska menambahkan leave approval top link 20150807
    public Vector getApprovalEmployeeTopLinkByLevel(long employee_id, int typeOfLink,int levelPoint);
    
    public Vector getApprovalEmployeeTopLinkByLevelMultipleEmp(String employee_id, int typeOfLink,int levelPoint);
    /**
     *
     * @param departmentId
     * @param sectionId
     * @param employee_id
     * @return
     */
    public Vector listSectionApproval(long departmentId, long sectionId, long employee_id);
    /* Untuk mendaptkan section approval*/
    
    
    /**
     *
     * @param departmentId
     * @param employee_id
     * @return
     */
    public Vector listDepartmentApproval(long departmentId, long employee_id);
    /* Untuk emndapatkan department approval */
    
    
    /**
     * <p>Create By satrya 2014-01-18</p>
     * Keterangan: setelah approvall tidak bisa di rubah approvall'nya
     * @return 
     */
    public boolean CanNotChangeApproval();
    
    /**
     *
     * @param division_id
     * @param employee_id
     * @return
     */
    public Vector listDivisionApproval(long division_id, long employee_id);
    /* Untuk mendapatkan division approval*/
    
    
    /**
     *
     * @param employee_id
     * @return
     */
    public Vector listHRManager(long employee_id);

    
    /**
     * create by satrya 2013-10-30
     * @param employee_id
     * @param oidEmpHr
     * @return 
     */
    public Vector listHRManagerSendEmail(long employee_id,String oidEmpHr);
    
    
    /**
     * create by satrya 2013-09-30
     * Keterangan: untuk mencari list get employee yg aktive
     * @param objSrcLeaveAppAlClosing
     * @param leaveConfig
     * @return 
     */
    public Vector listgetEmployeeActiveByPeriode(SrcLeaveAppAlClosing objSrcLeaveAppAlClosing,I_Leave leaveConfig);
    /* Untuk mendapatkan periode dari entitle Al */    
    /**
     *
     * @return
     */
    public int getDatePeriod();
    
    
    /* Untuk mendapatkan periode bulanan entitle Al */
    /**
     *
     * @return
     */
    public int getMonthPeriod();
    
    
    /* Untuk mendapatkan minimal waktu untuk memperoleh stock cuti */
    /**
     *
     * @return
     */
    public int getMinimalWorkAL();

    /* Untuk mendapatkan minimal waktu bekerja untuk boleh mengambil cuti */
    /**
     *
     * @return
     */
    public int getMinimalWorkCanTakeAL();

    /* Untuk mendapatkan tanggal closing jika menggunakan Leave by Period */
    /**
     *
     * @return
     */
    public int getDateClosingAnnual();

    /* Untuk mendapatkan bulan closing jika menggunakan Leave by Period */
    /**
     *
     * @return
     */
    public int getMonthClosingAnnual();

    
     /**
     * @descr Method for getting number of AL depending on employee Level and Employee type
     * @param level
     * @param employeeType
     * @param loSMonth = length of service in days
     * @return
     */
    public float getALEntitleAnualLeave(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate);
    /**
     * @descr Method for getting number of AL depending on employee Level and Employee type
     * @param level
     * @param employeeType
     * @param loSMonth = length of service in days
     * @return
     */
    public float getALEntitleAnualLeaveAlaCommecingPeriode(LeaveAlClosing leaveAlClosing) ;
    //public int getALEntitleAnualLeave(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate);

    /**
     * @descr set configuration for AL number entitling for employee depending on Level and type of employee
     * @param level
     * @param employeeType
     * @param numberAL
     */
    public void setALEntitleAnualLeave(String level, String employeeType, int numberAL);

    /**
     * keterangan: update by satrya 2013-11-22
     * untuk melakukan cek extra AL
     * @param level
     * @param employeeType
     * @param LoSDays
     * @param commencingDate
     * @param checkDate
     * @return 
     */
    public float getAlExtraAl(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate);
    
    /**
     * keterangan: untuk melakukan cek apakah sudah ada nilai extra AL
     * create by satrya 2013-11-22
     * @param extraAl
     * @return 
     */
    public Date getAlExtraDateAl(float extraAl,Date commercingDate);
    
    public String setAutoALEntitle(Date startDate);
    //public String setAutoALEntitle();
    
    /**
     * @descr: untuk penambahan AL otomatis per karyawan
     * create by satrya 2013-08-24
     * @param startDate
     * @param employeeId
     * @return 
     */
    public String setAutoALEntitle(Date startDate,long employeeId);
    
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
    public boolean getALStockMinus(AlStockTaken alStockTaken);
    
    //update by satrya 2013-08-18
    //sedikit di konfigurasi
    //public boolean getALStockMinus();
    
    
    //update by satrya 201308-20
    public boolean getALStockMinus(float eligible,long oidEmployee);
    //update by priska 2015-08-04
     /**
     * 
     * @param AL Allowance 
     */
   // public boolean getALallowance(AlStockTaken alStockTaken,long periodeId, long employeeId);
    public boolean getALallowance(LeaveApplication leaveApplication);
     //update by priska 2015-08-04
     /**
     * 
     * @param LL Allowance 
     */
    public boolean getLLallowance(LeaveApplication leaveApplication);
    
    
    //priska cek al pada periode tertentu 20150807
    public boolean getALallowanceByPeriode(long periodeId, long employeeId);
    public boolean getLLallowanceByPeriode(long periodeId, long employeeId);
    //priska menambahkan get al ll apakah ada al&ll allowance dalam setahun
//public boolean getALallowanceforAll(long LeaveAplicationID,long periodeId, long employeeId);
//    
//    public boolean getLLallowanceforAll(long LeaveAplicationID,long periodeId, long employeeId);
    /**
     * set if LL stock can be minus ( more LL is taken by employee then earned )
     * @param LLminus
     */
    public void setLLStockMinus(boolean LLminus);

    /**
     * get LL stock minus configuration
     * @return boolean
     */
    public boolean getLLStockMinus(LlStockTaken llStockTaken,I_Leave leave,float eligbleDay);
    
    //update by satrya 201308-20
     public boolean getLLStockMinus(float eligbleDay);
     
    /**
     * keterangan menambahkan automatis Entitle LL
     * @param startDate
     * @return 
     */
    public String setAutoLLEntitle(Date startDate,I_Leave leave);
     //update by priska 2015-08-04
     /**
     * 
     * @param LL Allowance 
     */
   // public boolean getLLallowance(long LeaveAplicationID);
     
    //update by satrya 2012-10-08
     /**
     * set if DD stock can be minus ( more DD is taken by employee then earned )
     * @param DPminus 
     */
     public void setDPStockMinus(boolean DPminus);
    /**
     * create by satrya 2013-08-29
     * get DP stock minus configuration
     * @return 
     */
     //public boolean getDPStockMinus();
    public boolean  getDPStockMinus(Vector listal,long oidEmployee,DpStockTaken dpStockTaken,float dpTakenPrev);
    
    /**
     * create by satrya 2013-12-16
     * @param dpStockTaken
     * @param residueSystem
     * @param dpTakenPrev
     * @return 
     */
     public boolean  getDPStockMinus(DpStockTaken dpStockTaken,float residueSystem,float dpTakenPrev);
    /**
     * untuk konfigurasi jika ada dp baru maka akan di bayarkan langsung
     * create by satrya 2013-09-02
     * @return 
     */
    public boolean getDPPaidPayable();
    /**
     * Konfigurasi DP minus
     * create by satrya 2013-08-30
     * @param dpStockManagement
     * @param dpStockTaken
     * @return 
     */
    public boolean getDPStockMinus(DpStockManagement dpStockManagement,DpStockTaken dpStockTaken);
    
    /**
     * create by satrya 2013-08-29
     * get DP Expired configuration
     * @return 
     */
    public boolean getDPExpired();
   
    /**
     * create by satrya 2014-01-18
     * <p>tidak menghitung balance tanpa menghitung dp yg sdh expired</p>
     * @return 
     */
    public boolean getBalanceNotCalculationDpExpired();
    /**
     * create by satrya 2013-08-29
     * get DP eligible minus configuration
     * @return 
     */
    public boolean getDPEligibleMinus(float eligbleDay);
    
     /**
     * get DP Unpaid configuration
     * jika data unpaid boleh kosong
     * @return 
     */
     public boolean getDPUnpaidIsNull();
    
     /**
      * create by satrya 2013-09-30
      * keterangan : nilai extend misalkan 1,2,3,4
      * @return 
      */
     public Vector getAlValExtend();
     /**
      * create by satrya 2013-09-30
      * keterangan: key Extend AL misal 1month,2month dst
      * @return 
      */
     public Vector getAlKeyExtend();
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
     * @return
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
     * Konfigurasi untuk menggunakan message advance Limit
     * @return 
     */
    public boolean isMessageUseAdvanceMinusLimit();
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
     * @return
     */
    public int getLLEntile(String level, String employeeType, int monthsLoS);


    /**
     * @descr: untuk konfigurasi apakah di tampilkan entitle 2,prev 2, exp entitle 2
     * @return 
     */
     public boolean getLLShowEntile2();
    /**
     * @descr get konfigurasi list dari jangka waktu validity LL ( misal yang didapat setiap 5 tahun valid 4 tahun)tergantung dari Level karyawan.
     * @param level
     * @param employeeType
     * @param monthsLoS : Length of service in months
     * @return range of months of validity of LL
     */
    public int getLLValidityMonths(String level, String employeeType, int monthsLoS);


    /**
     * menset jumlah minimal LL yang boleh di ambil dalam satu tahun
     *
     * @param minLL
     */
    public void setMinLLTakenAnual(int minLL);

    /**
     * mengambil jumlah minimal LL yang boleh di ambil dalam satu tahun
     *
     * @return
     */
    public int getMinLLTakenAnual();


    /**
     * menset jumlah maximum LL yang boleh di ambil dalam satu tahun
     *
     * @param minLL
     */
    public void setMaxLLTakenAnual(int minLL);

    /**
     * mengambil jumlah maximum LL yang boleh di ambil dalam satu tahun
     *
     * @return
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
     * @return
     * @descr get configuration for warning days before LL for employee  expires
     */
    public int getLLExpWarning();

    /**
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
    public int getDPEntitleByDate(String payNum, Date date,LeaveConfigurationParameter leaveConfigurationParameter);
    //public int getDPEntitleByDate(String payNum, Date date);

    /**
     * @return
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

    /*Untuk mendapatkan perhitungan stock Al dari setiap employee*/
    /**
     *
     * @param entDt
     * @return
     */
    public float getQtyEntitle(Date entDt,String level, String employeeType, Date commencingDate);

    /*Untuk mendapatakan date entitle pertama bagi yang menggunakan konfigurasi leave menggunakan periode*/
    /**
     *
     * @param DtCommencing
     * @return
     */
    public Date getEntitle_I(Date DtCommencing, String level, String employeeType);

     /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatakan Priode start dan end bila menggunakan periode
      * @param commancingDate
      * @param dtStart
     * @return
     */
    public Date getPeriode(Date commancingDate, int dtStart);

    /**
     *
     * @param yer
     * @param dtPeriod
     * @return
     */
    public Date getPeriodeClosing(Date yer, int dtPeriod);

    /**
     *
     */
    public void procesGetEntitleAutomatic();

    /**
     * @return
     * @Author  Roy Andika
     * @Desc    Untuk mendapatkan periode closing bulanan
     */
    public Date getPeriodMonth();
    
    public String getFormatLeave();
    public float getHourOneWorkday();
    
    /**
     * @author 
     * @param overtimeId
     * @return 
     */
    public Vector<Employee> overtimeRequester(long overtimeId);
    public Vector<Employee> overtimeApprover(long overtimeId);
    public Vector<Employee> overtimeFinalApprover(long overtimeId);
    
    //Update By Agus 12-02-2014
    public boolean isLeaveApplicationIsDay();
}