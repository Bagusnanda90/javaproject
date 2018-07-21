/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import java.util.Hashtable;

/**
 *
 * @author Dimata 007
 */
public interface I_Atendance {
    /**
     * KONFIGUARSI PENCARIAN IN OUT SCHEDULE
     */
    public final int CONFIGURASI_I_SEARCH_BY_SCHEDULE           = 0;
    public final int CONFIGURASI_II_SEARCH_BY_IN_OUT            = 1;
    public final int CONFIGURASI_III_SEARCH_BY_SCHEDULE_NO_OVERTIME    = 2;
    
    public final int CONFIGURASI_I_TAKEN_INSENTIF = 0;
    public final int CONFIGURASI_II_NO_TAKEN_INSENTIF = 1;
    
    public final int CONFIGURASI_I_REPORT_DAILY_SCHEDULE_AND_CEK_BOX_ADA_DIBELAKANG = 0;
    public final int CONFIGURASI_II_NORMAL_REPORT_DAILY = 1;
    
    //Update By Agus 11-02-2014
    public final int CONFIGURASI_I_VIEW_BIRTHDAY_A_WEEK=0;
    public final int CONFIGURASI_II_VIEW_BIRTHDAY_A_MONTH=1;
    public final int CONFIGURASI_III_VIEW_BIRTHDAY_A_MONTH_NOT_SHOW_ANNYVERSARY_DATE_HAS_PASSED=2;
    
    //Update By Agus 11-02-2014
    public final int CONFIGURASI_I_VIEW_SHOW_NO_REKENING=0;
    public final int CONFIGURASI_II_VIEW_NOT_SHOW_NO_REKENING=1;
    
    public final int CONFIGURASI_I_VIEW_SHOW_ALL_CONFIGURATION_MINIMART=0;
    public final int CONFIGURASI_II_VIEW_NOT_SHOW_ALL_CONFIGURATION_MINIMART=1;
    
    //UPDATE BY SATRYA 2014-07-3
    public final int CONFIGURATION_I_GENERATE_MANUAL_INPUT_EMPLOYEE_NUMBER=0;
    public final int CONFIGURATION_II_GENERATE_AUTOMATIC_EMPLOYEE_NUMBER=1;
    
    
    /**
     * KONFIGURASI attendance
     * @return 
     */
    public int getConfigurasiInOut();
    
    public int getConfigurasiInsentif();
    
    public int getConfigurasiReportScheduleDaily();
    
    public boolean getConfigurasiWorkingDays(long syspropScheduleOff,long oidScheduleOff,int statusAbs);
    
    public int getConfigurationBirthDay();
    
    //update by satrya 2014-03-06
    public boolean getConfigurationShowPositionCode();
    
    //update by satrya 2014-06-13
    public int getConfigurationShowNoRekening();
    
    public int getConfigurationOutletMiniMarket();
    
    /**
     * keterangan: untuk hastable reason khusus 
     * @return 
     */
    public Hashtable getReasonIdKhusus();
    
    /**
     * Keterangan: konfigurasi input employee number
     * @return 
     */
    public int getConfigurasiInputEmpNum();
    
    
}
