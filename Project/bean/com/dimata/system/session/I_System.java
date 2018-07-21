/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.session;

/**
 *
 * @author khirayinnura
 */
public interface I_System {
    
    public static final int AISO = 0;
    public static final int DRS = 1;
    public static final int HAIRISMA = 2;
    public static final int HANOMAN = 3;
    public static final int LOGBOOK = 4;
    public static final int PROCHAIN = 5;
    public static final int SARAS = 6;
    public static final int TRAVISI = 7;
    
    public static final String SYSTEM_NAME[] = {
        "AISO",
        "DRS",
        "Hairisma",
        "Hanoman",
        "Logbook",
        "Prochain",
        "Saras",
        "Travisi"
    };
    
    // AISO
    public static final int MODUL_AISO_JOURNAL = 0;
    public static final int MODUL_AISO_AP = 1;
    public static final int MODUL_AISO_AR = 2;
    public static final int MODUL_AISO_ASSET = 3;
    
    //DRS
    
    //"Hairisma"
    public static final int MODUL_HAIRISMA_ALL = 0;
    public static final int MODUL_HAIRISMA_ATTENDANCE_MANAGEMENT = 1;
    public static final int MODUL_HAIRISMA_CANTEEN_DATA_MANAGEMENT = 2;
    public static final int MODUL_HAIRISMA_CUSTOME_REPORTS = 3;
    public static final int MODUL_HAIRISMA_EMPLOYEE_APPAISAL_AND_RECOGNITION = 4;
    public static final int MODUL_HAIRISMA_EMPLOYEE_DATABANK = 5;
    public static final int MODUL_HAIRISMA_EMPLOYEE_MEALS_REPORTING = 6;
    public static final int MODUL_HAIRISMA_LOCKER_MANAGEMENT = 7;
    public static final int MODUL_HAIRISMA_MASTER_DATA_MANAGEMENT = 8;
    public static final int MODUL_HAIRISMA_MEDICAL_DATA_MANAGEMENT = 9;
    public static final int MODUL_HAIRISMA_OVERTIME = 10;
    public static final int MODUL_HAIRISMA_PAYROLL_PROCESS = 11;
    public static final int MODUL_HAIRISMA_PAYROLL_REPORT = 12;
    public static final int MODUL_HAIRISMA_PAYROLL_SET_UP = 13;
    public static final int MODUL_HAIRISMA_RECRUITMENT_MANAGEMENT = 14;
    public static final int MODUL_HAIRISMA_REPORTS = 15;
    public static final int MODUL_HAIRISMA_TIME_KEEPING_MANAGEMENT = 16;
    public static final int MODUL_HAIRISMA_TRAINING_MANAGEMENT = 17;
    public static final int MODUL_HAIRISMA_WARNING_AND_REPRIMAND = 18;
        
    //"Hanoman"
    //"Logbook"
    //"Prochain"
    //"Saras"
    //"Travisi"
    
    public static final String MODULS[][] = {
        //"AISO"
        {
            "JOURNAL",
            "AP",
            "AR",
            "ASSET"
        },
        //DRS
        {},
        //"Hairisma"
        {
            "All",
            "Attendance Management",
            "Canteen Data Management",
            "Custome Reports",
            "Employee Appaisal And Recognition",
            "Employee Databank",
            "Employee Meals Reporting",
            "Locker Management",
            "Master Data Management",
            "Medical Data Management",
            "Overtime",
            "Payroll Process",
            "Payroll Report",
            "Payroll Set Up",
            "Recruitment Management",
            "Reports",
            "Time Keeping Management",
            "Training Management",
            "Warning And Reprimand"            
        },
        //"Hanoman"
        {},
        //"Logbook"
        {},
        //"Prochain"
        {},
        //"Saras"
        {},
        //"Travisi"
        {}
    };
    
}
