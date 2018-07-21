/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.template;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author ADY
 */
public class Flyout_yg_sdhMau {
    //Menu Employee

    private boolean mnMenuEmployee;
    private boolean mnMenuDataBank;
    private boolean mnCompanyStructure;
    private boolean mnMenuNewEmployee;
    private boolean mnMenuAbsMan;
    //update by satrya 2014-03-22
    private boolean mnMenuEmployeeOutlet;
    private boolean mnMenuLeaveSetting;
    //SubMenu Recognitions
    private boolean mnMenuRecognitions;
    private boolean mnMenuEntryPerDept;
    private boolean mnMenuUpdatePerEmp;
    //SubMenu Recruitment
    private boolean mnMenuRecruitment;
    private boolean mnMenuStaffRequisition;
    private boolean mnMenuEmpApplication;
    private boolean mnMenuOrientationList;
    private boolean mnMenuReminder;
    //SubMenu Warning & Reprimand
    private boolean mnMenuWarning;
    private boolean mnMenuEmpWarning;
    private boolean mnMenuEmpReprimand;
    //SubMenu Excuse Application
    private boolean mnMenuExcuse;
    private boolean mnMenuExcuseForm;
    //SubMenu Attendance
    private boolean mnMenuAttdance;
    private boolean mnMenuWorkingSchedule;
    private boolean mnMenuManualRegistration;
    private boolean mnMenuReGenScheduleHoly;
    //SubMenu Leave Application
    private boolean mnMenuLeaveForm;
    private boolean mnMenuLeaveAlClosing;
    private boolean mnMenuLeavellClosing;
    private boolean mnMenuDpManagement;
    private boolean mnMenuRecalculate;
    //SubMenu Leave Balancing
    private boolean mnMenuAnualLeaveBalancing;
    private boolean mnMenuLongLeaveBalancing;
    private boolean mnMenuDPPayment;
    //SubMenu Assessment
    private boolean mnMenuExplanations;
    private boolean mnMenuPerfomAssment;
    //Menu Training
    private boolean mnMenuTraining;
    private boolean mnMenuTrainingType;
    private boolean mnMenuTrainingVanue;
    private boolean mnMenuTrainingProgram;
    private boolean mnMenuTrainingPlan;
    private boolean mnMenuTrainingActual;
    private boolean mnMenuTrainingSearch;
    private boolean mnMenuTrainingHistory;
    //Menu Report
    private boolean mnMenuReport;
    //SubMenu Staff Control
    private boolean MnMenuStaffControll;
    private boolean MnMenuAttRecord;
    //SubMenu Presence
    private boolean MnMenuDailyReportPresence;
    private boolean MnMenuWeeklyReportPresence;
    private boolean MnMenuMontlyReportPresence;
    private boolean MnMenuYearlyReportPresence;
    private boolean MnMenuAttdSummaryReport;
    //SubMenu Lateness
    private boolean mnMenuLateness;
    private boolean mnMenuLatnessDailyReport;
    private boolean mnMenuLatnessWeeklyReport;
    private boolean mnMenuLatnessMonthlyReport;
    //SubMenu Split Shift
    private boolean mnMenuSplitSift;
    private boolean mnMenuSplitShiftDailyReport;
    private boolean mnMenuSplitShiftWeeklyReport;
    private boolean mnMenuSplitShiftMonthlyReport;
    //SubMenu Night Shift
    private boolean mnMenuNightShift;
    private boolean mnMenuNightShiftDailyReport;
    private boolean mnMenuNightShiftWeeklyReport;
    private boolean mnMenuNightShiftMonthlyReport;
    //SubMenu Night Absenteeism
    private boolean mnMenuAbsenteeism;
    private boolean mnMenuAbsenteeismDailyReport;
    private boolean mnMenuAbsenteeismWeeklyReport;
    private boolean mnMenuAbsenteeismMonthlyReport;
    //SubMenu Night Sickness
    private boolean mnMenuSickness;
    private boolean mnMenuSicknessDailyReport;
    private boolean mnMenuSicknessWeeklyReport;
    private boolean mnMenuSicknessMonthlyReport;
    private boolean mnMenuSicknessZeroReport;
    //SubMenu Night Special Dispensation
    private boolean mnMenuSpcDisp;
    private boolean mnMenuSpcDispDailyReport;
    private boolean mnMenuSpcDispWeeklyReport;
    private boolean mnMenuSpcDispMonthlyReport;
    //SubMenu Leave Report
    private boolean mnMenuLeaveDPSum;
    private boolean mnMenuLeaveDPDetail;
    private boolean mnMenuLeaveDPSumPeriod;
    private boolean mnMenuLeaveDPDetailPeriod;
    private boolean mnMenuSpcUnpaidLeave;
    private boolean mnMenuDPExpired;
    //SubMenu Trainee
    private boolean mnMenuReportTrainee;
    private boolean mnMenuTraineeMonthlyReport;
    private boolean mnMenuTraineeEndPeriodReport;
    //SubMenu Employee
    private boolean mnMenuListEmpCategory;
    private boolean mnMenuListEmpResign;
    private boolean mnMenuListEmpEducation;
    private boolean mnMenuListEmpCatByName;
    private boolean mnMenuListNumbAbs;
    private boolean mnMenuListEmpRace;
    //SubMenu Training Report
    private boolean mnMenuMonthTraining;
    private boolean mnMenuTrainingProfile;
    private boolean mnMenuTrainingTarget;
    private boolean mnMenuTrainingRptByDpt;
    private boolean mnMenuTrainingRptByCourseDetail;
    private boolean mnMenuTrainingRptByEmp;
    private boolean mnMenuTrainingRptByTrainer;
    private boolean mnMenuTrainingRptByCourseDate;
    //SubMenu Payroll
    private boolean mnMenuReportPayroll;
    private boolean mnMenuPayrollListOfSalary;
    //Menu Canteen
    private boolean mnMenuCanteenn;
        //SubMenu Data
        private boolean mnMenuData;
        private boolean mnMenuManualVisitation;
        private boolean mnMenuCanteenSchedule;
        //SubMenu Canteen
        private boolean mnMenuCanteen;
        private boolean mnMenuChecklistGroup;
        private boolean mnMenuChecklistItem;
        private boolean mnMenuChecklistMark;
        private boolean mnMenuMenuItem;
        private boolean mnMenuMealTime;
        private boolean mnMenuCommentGroup;
        private boolean mnMenuCommentQuestion;
        //SubMenu Reports
        private boolean mnMenuReports;
        private boolean mnMenuDetail;
        private boolean mnMenuDailyReport;
        private boolean mnMenuWeeklyReport;
        private boolean mnMenuMonthlyReport;
        //SubMenu Summary
        private boolean mnMenuSummary;
        private boolean mnMenuDailyMealRecord;
        private boolean mnMenuPeriodicMealRecord;
        private boolean mnMenuMealReports;
        private boolean mnMenuMealReportsDepartement;
        private boolean mnMenuMounthlyCanteenReport;
    //Menu Clinic
    private boolean mnMenuClinic;
    private boolean mnMenuEmployeeFamily;
    private boolean mnMenuMedicalRecord;
    private boolean mnMenuEmployeeVisit;
    private boolean mnMenuGuestHandling;
    //SubMenu Medicine
        private boolean mnMenuMedicine;
        private boolean mnMenuListOfMedicine;
        private boolean mnMenuMedicineConsumpsition;
        //SubMenu Disease
        private boolean mnMenuDisease;
        private boolean mnMenuDiseaseType;
        //SUbMenu Medical
        private boolean mnMenuMedical;
        private boolean mnMenuMedicalLevel;
        private boolean mnMenuMedicalCase;
        private boolean mnMenuMedicalBudget;
        private boolean mnMenuGroup;
        private boolean mnMenuTypee;
    //Menu Locker
    private boolean mnMenuLocker;
    private boolean mnMenuLockerTreatment;
    private boolean mnMenuLockerr;
    //Menu Master Data
    private boolean mnMenuMasterData;
        //SubMenu Company
        private boolean mnMenuCompany;
        private boolean mnMenuDivision;
        private boolean mnMenuDepartement;
        private boolean mnMenuPosition;
        private boolean mnMenuSection;
        private boolean mnMenuPublicHoliday;
        private boolean mnMenuLeaveTarget;
        //SubMenu Employee
        private boolean mnMenuEducation;
        private boolean mnMenuFamilyRelation;
        private boolean mnMenuWarningg;
        private boolean mnMenuReprimand;
        private boolean mnMenuLevel;
        private boolean mnMenuCategory;
        private boolean mnMenuReligion;
        private boolean mnMenuMarital;
        private boolean mnMenuRace;
        private boolean mnMenuLanguage;
        private boolean mnMenuImageAssign;
        private boolean mnMenuResigneReason;
        private boolean mnMenuAwardType;
        private boolean mnMenuAbsenanceReason;
        //SubMenu Schedule
        private boolean mnMenuPeriode;
        private boolean mnMenuCategoryy;
        private boolean mnMenuSymbol;
        //subMenu Location
        private boolean mnMenuLocation;
        //SubMenu Locker Data
        private boolean mnMenuLockerLocation;
        private boolean mnMenuLockerCondition;
        //SubMenu Assessment
        private boolean mnMenuGroupRank;
        private boolean mnMenuEvaluationCriteria;
        private boolean mnMenuFormCreator;
        //SubMenu Recruitment
        private boolean mnMenuGeneralQuestion;
        private boolean mnMenuIllnesType;
        private boolean mnMenuInterviewPoint;
        private boolean mnMenuInterviewer;
        private boolean mnMenuInterviewFactor;
        private boolean mnMenuOrientationGroup;
        private boolean mnMenuOrientationActivity;
        //SubMenu Geo Area
        private boolean mnMenuCountryy;
        private boolean mnMenuProvince;
        private boolean mnMenuRegency;
        private boolean mnMenuSubRegency;
    //Menu System
    private boolean mnMenuServiceCenter;
    private boolean mnMenuManualProcess;
    private boolean mnMenuAdminTestAdmin;
    private boolean mnMenuAdminQuerySetup;
        //SubMenu User Management
        private boolean mnMenuUserList;
        private boolean mnMenuGroupPrivilege;
        private boolean mnMenuPrivilege;
        private boolean mnMenuUpdatePassword;
        private boolean mnMenuUserCompare;
        //SubMenu System Management
        private boolean mnMenuSystemProperties;
        private boolean mnMenuLoginHistory;
        private boolean mnMenuSystemLog;
        //SubMenu Time Keeping
        private boolean mnMenuServiceManager;
    //Menu payroll Setup
    private boolean mnMenuGeneral;
    private boolean mnMenuPayrollPeriode;
    private boolean mnMenuBankList;
    private boolean mnMenuPayslipGroup;
    private boolean mnMenuSalaryComponent;
    private boolean mnMenuSalaryLevel;
    private boolean mnMenuEmployeeSetup;
    private boolean mnMenuCurrency;
    private boolean mnMenuCurrencyRate;
    //Menu Overtime
    private boolean mnMenuOvertimeForm;
    private boolean mnMenuOvertimeRptProcess;
    private boolean mnMenuOvertimeIndex;
    private boolean mnMenuOvertimeSummary;
    //Menu Payroll Process
    private boolean mnMenuPrepareData;
    private boolean mnMenuPayrollInput;
    private boolean mnMenuPayrollProcess;
    private boolean mnMenuPayslipPrinting;

    public void MenuFlyout(JspWriter out, String url, boolean isMSIE, String approot) {
        try {
            boolean menuKiriKosong = true;
            boolean menuTengahKosong = true;
            boolean menuKananKosong = true;
            if (!isMSIE) {
                out.print("<div>");
                if (url != null && url.length() > 0 && url.equalsIgnoreCase("home.jsp")) {
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("employee.jsp")) {
                   out.print("<div id=\"menuheader\"><h2><spacing>Employee</spacing></h2></div>");
                   if(isMnMenuDataBank()|| isMnCompanyStructure() 
                       || isMnMenuNewEmployee() || isMnMenuAbsMan() || isMnMenuEntryPerDept()
                       ||isMnMenuUpdatePerEmp() || isMnMenuStaffRequisition()||isMnMenuEmpApplication()
                       ||isMnMenuOrientationList()||isMnMenuReminder() || isMnMenuEmpWarning()||isMnMenuEmpReprimand() || isMnMenuEmployeeOutlet()){

                    out.print("<div id=\"menu_kiri\">");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuDataBank()) {
                        out.print("<li><a id=\"data_bank\" href='../employee/databank/srcemployee.jsp'><span><p id=\"has-sub-align\">Data Bank</p></span></a></li>");
                    }
                    if (isMnCompanyStructure()) {
                        out.print("<li><a id=\"company_structure\" href='../employee/databank/srcEmployee_structure.jsp'><span><p id=\"has-sub-align\">Company Structure</p></span></a></li>");
                    }
                    if (isMnMenuNewEmployee()) {
                        out.print("<li><a id=\"new_employee\" href='../employee/databank/new_employee_list.jsp'><span><p id=\"has-sub-align\">New Employee</p></span></a></li>");
                    }
                    if (isMnMenuAbsMan()) {
                        out.print("<li><a id=\"absence_management\" href='../employee/absence/srcabsence.jsp'><span><p id=\"has-sub-align\">Absence Management</p></span></a></li>");
                    }
                    if (isMnMenuEntryPerDept()||isMnMenuUpdatePerEmp()) {
                        out.print("<li><a id=\"recognitions\" href='#'><span><p id=\"has-sub-align\">Recognitions</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuEntryPerDept()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recognition/recognitiondep.jsp'><px>01.Entry per Departement</px></a></li>");
                        }
                        if (isMnMenuUpdatePerEmp()) {
                            out.print("<li  style=\"margin-top: =sizeSpasiPerSubMenu;\" id='has-sub-menu1'><a href='../employee/recognition/srcrecognition.jsp'><px>02.Update <enter_word>per Employee</enter_word></px></a></li>");
                        }
                        out.print("</ul>");

                        out.print("</li>");
                    }
                    if (isMnMenuStaffRequisition()||isMnMenuEmpApplication()||isMnMenuOrientationList()||isMnMenuReminder()) {
                        out.print("<li><a id=\"recruitment\" href='#'><span><p id=\"has-sub-align\">Recruitment</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuStaffRequisition()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/srcstaffrequisition.jsp'><px>01.Staff Requisition</px></a></li>");
                        }
                        if (isMnMenuEmpApplication()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/srcrecrapplication.jsp'><px>02.Employement Application</px></a></li>");
                        }
                        if (isMnMenuOrientationList()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/srcorichecklist.jsp'><px>03.Orientation Checklist</px></a></li>");
                        }
                        if (isMnMenuReminder()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/reminder.jsp'><px>04.Reminder</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuEmpWarning()||isMnMenuEmpReprimand()) {
                        out.print("<li><a id=\"warning\" href='#'><span><p id=\"has-sub-align\">Warning & Reprimand</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuEmpWarning()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/warning/src_warning.jsp'><px>01.Warning</px></a></li>");
                        }
                        if (isMnMenuEmpReprimand()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/warning/src_reprimand.jsp'><px>02.Reprimand</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                }
                        if(menuKiriKosong == false){
                        out.print("<div id=\"menu_kanan\">");
                        }else{
                        out.print("<div id=\"menu_kiri\">");
                        }
                        out.print("<div id='cssmenu'>");
                        out.print("<ul>");
                        if (isMnMenuExcuseForm()) {
                            out.print("<li><a id=\"excuse_application\" href='#'><span><p id=\"has-sub-align\">Excuse Application</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                            out.print("<ul>");
                            out.print("<li id='has-sub-menu1'><a href='../employee/leave/excuse_app_src.jsp'><px>01.Excuse Form</px></a></li>");
                            out.print("</ul>");
                            out.print("</li>");
                        }
                        if (isMnMenuWorkingSchedule()||isMnMenuManualRegistration()||isMnMenuReGenScheduleHoly()) {
                            out.print("<li><a id=\"attendance\" href='#'><span><p id=\"has-sub-align\">Attendance</p></span></a>");
                            out.print("<ul>");
                            if (isMnMenuWorkingSchedule()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/attendance/srcempschedule.jsp'><px>01.Working Schedule</px></a></li>");
                            }
                            if (isMnMenuManualRegistration()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/presence/srcpresence.jsp'><px>02.Manual Registration</px></a></li>");
                            }
                            if (isMnMenuReGenScheduleHoly()) {
                                out.print("<li id='has-sub-menu1'><a href='../report/presence/Update_schedule_If_holidays.jsp'><px>03.Re-Generate Sch Holidays</px></a></li>");
                            }
                            out.print("</ul>");
                            out.print("</li>");
                        }
                        //update by satrya 2014-03-22
                        ///MnMenuEmployee Outlet
                        if (isMnMenuEmployeeOutlet()) {
                            out.print("<li><a id=\"employee_outlet\" href='#'><span><p id=\"has-sub-align\">Employee Outlet</p></span></a>");
                            out.print("<ul>");
                            if (isMnMenuEmployeeOutlet()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/outlet/outlet.jsp'><px>01.Employee Outlet</px></a></li>");
                            }
                            
                            out.print("</ul>");
                            out.print("</li>");
                        }
                                
                        if (isMnMenuLeaveForm()||isMnMenuLeaveAlClosing()||isMnMenuLeavellClosing()||isMnMenuDpManagement()||isMnMenuRecalculate()) {
                            out.print("<li><a id=\"leave_application\" href='#'><span><p id=\"has-sub-align\">Leave Application</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                            out.print("<ul>");
                            if (isMnMenuLeaveForm()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/leave/leave_app_src.jsp'><px>01.Leave Form</px></a></li>");
                            }
                            if (isMnMenuLeaveAlClosing()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/leave/leave_al_closing.jsp'><px>02.Leave AL Closing</px></a></li>");
                            }
                            if (isMnMenuLeavellClosing()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/leave/leave_ll_closing.jsp'><px>03.Leave LI Closing</px></a></li>");
                            }
                            if (isMnMenuDpManagement()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/attendance/dp.jsp'><px>04.DP Management</px></a></li>");
                            }
                            if (isMnMenuRecalculate()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/leave/if_dp_not_balance.jsp'><px>05.DP Re-Calculate</px></a></li>");
                            }
                            out.print("</ul>");
                            out.print("</li>");
                        }
                        if (isMnMenuAnualLeaveBalancing()||isMnMenuLongLeaveBalancing()||isMnMenuDPPayment()) {
                            out.print("<li><a id=\"leave_balancing\" href='#'><span><p id=\"has-sub-align\">Leave Balancing</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                            out.print("<ul>");
                            if (isMnMenuAnualLeaveBalancing()) {
                                out.print("<li id='has-sub-menu1'><a href='../system/leave/AL_Balancing.jsp'><px>01.Annual Leave</px></a></li>");
                            }
                            if (isMnMenuLongLeaveBalancing()) {
                                out.print("<li id='has-sub-menu1'><a href='../system/leave/LL_Balancing.jsp'><px>02.Long Leave</px></a></li>");
                            }
                            if (isMnMenuDPPayment()) {
                                out.print("<li id='has-sub-menu1'><a href='../system/leave/DP_Balancing.jsp'><px>03.Day off Payment</px></a></li>");
                            }
                            out.print("</ul>");
                            out.print("</li>");
                        }
                        if (isMnMenuExplanations()||isMnMenuPerfomAssment()) {
                            out.print("<li><a id=\"assessment\" href='#'><span><p id=\"has-sub-align\">Assessment</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                            out.print("<ul>");
                            if (isMnMenuExplanations()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/appraisal/expcoverage.jsp'><px>01.Explanations and Coverage</px></a></li>");
                            }
                            if (isMnMenuPerfomAssment()) {
                                out.print("<li id='has-sub-menu1'><a href='../employee/appraisalnew/srcappraisal.jsp'><px>02.Performance Assessment</px></a></li>");
                            }
                            out.print("</ul>");
                            out.print("</li>");
                        }
                        out.print("</ul>");
                        out.print("</div>");
                        out.print("</div>");
                    
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("training.jsp")) {
                    if (isMnMenuTrainingType() || isMnMenuTrainingVanue() || isMnMenuTrainingPlan() || isMnMenuTrainingActual() || isMnMenuTrainingSearch() || isMnMenuTrainingHistory()) {
                        out.print("<div id=\"menuheader\"><h2><spacing>Training</spacing></h2></div>");
                        out.print("<div id=\"menu_kiri\">");
                        out.print("<div id='cssmenu'>");
                        out.print("<ul>");
                        if (isMnMenuTrainingType()) {
                            out.print("<li><a id=\"training_type\" href='../masterdata/list_train_type.jsp'><span><p id=\"has-sub-align\">Training Type</p></span></a></li>");
                        }
                        if (isMnMenuTrainingVanue()) {
                            out.print("<li><a id=\"training_vanue\" href='../masterdata/list_train_venue.jsp'><span><p id=\"has-sub-align\">Training Vanue</p></span></a></li>");
                        }
                        if (isMnMenuTrainingProgram()) {
                            out.print("<li><a id=\"training_program\" href='../masterdata/srctraining.jsp'><span><p id=\"has-sub-align\">Training Program</p></span></a></li>");
                        }
                        if (isMnMenuTrainingPlan()) {
                            out.print("<li><a id=\"training_plan\" href='../employee/training/training_plan_list.jsp'><span><p id=\"has-sub-align\">Training Plan</p></span></a></li>");
                        }
                        if (isMnMenuTrainingActual()) {
                            out.print("<li><a id=\"training_actual\" href='../employee/training/training_actual_list.jsp'><span><p id=\"has-sub-align\">Training Actual</p></span></a></li>");
                        }
                        if (isMnMenuTrainingSearch()) {
                            out.print("<li><a id=\"training_search\" href='../employee/training/search_training.jsp'><span><p id=\"has-sub-align\">Training Search</p></span></a></li>");
                        }
                        if (isMnMenuTrainingHistory()) {
                            out.print("<li><a id=\"training_history\" href='../employee/training/src_training_hist_hr.jsp'><span><p id=\"has-sub-align\">Training History</p></span></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("reports.jsp")) {
                    out.print("<div id=\"menuheader\"><h2><spacing>Reports</spacing></h2></div>");
                    if(isMnMenuStaffControll()||isMnMenuDailyReportPresence()||isMnMenuLatnessDailyReport()){
                    out.print("<div id=\"menu_kiri\">");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuStaffControll()) {
                        out.print("<li><a id=\"staff_control\" href='#'><span><p id=\"has-sub-align\">Staff Control</p></span></a>");
                        out.print("<ul>");
                        out.print("<li id='has-sub-menu1'><a href='../employee/presence/att_record_monthly.jsp'><px>01.Attendance Record</px></a></li>");
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuDailyReportPresence() || isMnMenuWeeklyReportPresence() || isMnMenuMontlyReportPresence() || isMnMenuYearlyReportPresence() || isMnMenuAttdSummaryReport()) {
                        out.print("<li><a id=\"Presence\" href='#'><span><p id=\"has-sub-align\">Presence</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuDailyReportPresence()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/presence/presence_report_daily.jsp'><px>01.Daily Report</px></a></li>");
                        }
                        if (isMnMenuWeeklyReportPresence()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/presence/weekly_presence.jsp'><px>02.Weekly Report</px></a></li>");
                        }
                        if (isMnMenuMontlyReportPresence()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/presence/monthly_presence.jsp'><px>03.Monthly Report</px></a></li>");
                        }
                        if (isMnMenuYearlyReportPresence()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/presence/year_report_presence.jsp'><px>04.Year Report</px></a></li>");
                        }
                        if (isMnMenuAttdSummaryReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/presence/summary_attendance_sheet.jsp'><px>05.Attendance Summary</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuLatnessDailyReport() || isMnMenuLatnessWeeklyReport() || isMnMenuLatnessMonthlyReport()) {
                        out.print("<li><a id=\"lateness\" href='#'><span><p id=\"has-sub-align\">Lateness</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuLatnessDailyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/lateness/lateness_report.jsp'><px>01.Daily Report</px></a></li>");
                        }
                        if (isMnMenuLatnessWeeklyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/lateness/lateness_weekly_report.jsp'><px>02.Weekly Report</px></a></li>");
                        }
                        if (isMnMenuLatnessMonthlyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/lateness/lateness_monthly_report.jsp'><px>03.Monthly Report</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuSplitShiftDailyReport() || isMnMenuSplitShiftWeeklyReport() || isMnMenuSplitShiftMonthlyReport()) {
                        out.print("<li><a id=\"split_shift\" href='#'><span><p id=\"has-sub-align\">Split Shift</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuSplitShiftDailyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/splitshift/daily_split.jsp'><px>01.Daily Report</px></a></li>");
                        }
                        if (isMnMenuSplitShiftDailyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/splitshift/weekly_split.jsp'><px>02.Weekly Report</px></a></li>");
                        }
                        if (isMnMenuSplitShiftDailyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/splitshift/monthly_split.jsp'><px>03.Monthly Report</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuNightShiftDailyReport() || isMnMenuNightShiftWeeklyReport() || isMnMenuNightShiftMonthlyReport()) {
                        out.print("<li><a id=\"night_shift\" href='#'><span><p id=\"has-sub-align\">Night Shift</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuNightShiftDailyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/nightshift/daily_night.jsp'><px>01.Daily Report</px></a></li>");
                        }
                        if (isMnMenuNightShiftWeeklyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/nightshift/weekly_night.jsp'><px>02.Weekly Report</px></a></li>");
                        }
                        if (isMnMenuNightShiftMonthlyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/nightshift/monthly_night.jsp'><px>03.Monthly Report</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuAbsenteeismDailyReport() || isMnMenuAbsenteeismWeeklyReport() || isMnMenuAbsenteeismMonthlyReport()) {
                        out.print("<li><a id=\"absenteeism\" href='#'><span><p id=\"has-sub-align\">Absenteeism</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuAbsenteeismDailyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/absenteeism/daily_absence.jsp'><px>01.Daily Report</px></a></li>");
                        }
                        if (isMnMenuAbsenteeismWeeklyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/absenteeism/weekly_absence.jsp'><px>02.Weekly Report</px></a></li>");
                        }
                        if (isMnMenuAbsenteeismMonthlyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/absenteeism/monthly_absence.jsp'><px>03.Monthly Report</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuSicknessDailyReport() || isMnMenuSicknessWeeklyReport() || isMnMenuSicknessMonthlyReport() || isMnMenuSicknessZeroReport()) {
                        out.print("<li ><a id=\"sickness\" href='#'><span><p id=\"has-sub-align\">Sickness</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuSicknessDailyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/sickness/daily_sickness.jsp'><px>01.Daily Report</px></a></li>");
                        }
                        if (isMnMenuSicknessWeeklyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/sickness/weekly_sickness.jsp'><px>02.Weekly Report</px></a></li>");
                        }
                        if (isMnMenuSicknessMonthlyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/sickness/monthly_sickness.jsp'><px>03.Monthly Report</px></a></li>");
                        }
                        if (isMnMenuSicknessZeroReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/sickness/zero_sickness.jsp'><px>04.Zero Sickness</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                    //Update By Agus 17-02-2014
                }
                    if(menuKiriKosong == false){
                    out.print("<div id=\"menu_kanan\">");
                    }else{
                    out.print("<div id=\"menu_kiri\">");
                    }
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuSpcDispDailyReport() || isMnMenuSpcDispWeeklyReport() || isMnMenuSpcDispMonthlyReport()) {
                        out.print("<li><a id=\"special_dispensation\" href='#'><span><p id=\"has-sub-align\">Special Dispensation</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuSpcDispDailyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/specialdisp/daily_specialdisp.jsp'><px>01.Daily Report</px></a></li>");
                        }
                        if (isMnMenuSpcDispWeeklyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/specialdisp/weekly_specialdisp.jsp'><px>02.Weekly Report</px></a></li>");
                        }
                        if (isMnMenuSpcDispMonthlyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/specialdisp/monthly_specialdisp.jsp'><px>03.Monthly Report</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuLeaveDPSum() || isMnMenuLeaveDPDetail() || isMnMenuLeaveDPSumPeriod() || isMnMenuLeaveDPDetailPeriod() || isMnMenuSpcUnpaidLeave() || isMnMenuDPExpired()) {
                        out.print("<li><a id=\"leave_report\" href='#'><span><p id=\"has-sub-align\">Leave Report</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuLeaveDPSum()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/leavedp/leave_dp_sum.jsp'><px>01.Leave & DP Summary</px></a></li>");
                        }
                        if (isMnMenuLeaveDPDetail()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/leavedp/leave_dp_detail.jsp'><px>02.Leave & DP Detail</px></a></li>");
                        }
                        if (isMnMenuLeaveDPSumPeriod()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/leavedp/leave_department_by_period.jsp'><px>03.Leave & DP Sum.Period</px></a></li>");
                        }
                        if (isMnMenuLeaveDPDetailPeriod()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/leavedp/leave_dp_detail_period.jsp'><px>04.Leave & DP Detail Period</px></a></li>");
                        }
                        if (isMnMenuSpcUnpaidLeave()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/leave/leave_sp_period.jsp'><px>05.Special & Unpaid Period</px></a></li>");
                        }
                        if (isMnMenuDPExpired()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/attendance/dpexp_report.jsp'><px>06.DP Ekspired</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuTraineeMonthlyReport() || isMnMenuTraineeEndPeriodReport()) {
                        out.print("<li><a id=\"trainee\" href='#'><span><p id=\"has-sub-align\">Trainee</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuTraineeMonthlyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/monthly_tr_rpt.jsp'><px>01.Monthly Report</px></a></li>");
                        }
                        if (isMnMenuTraineeEndPeriodReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/end_tr_rpt.jsp'><px>02.End Period</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuListEmpCategory() || isMnMenuListEmpResign() || isMnMenuListEmpEducation() || isMnMenuListEmpCatByName() || isMnMenuListNumbAbs() || isMnMenuListEmpRace()) {
                        out.print("<li><a id=\"employee\" href='#'><span><p id=\"has-sub-align\">Employee</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuListEmpCategory()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/employee/list_employee_category.jsp'><px>01.List of Employee Category</px></a></li>");
                        }
                        if (isMnMenuListEmpResign()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/employee/list_employee_resignation1.jsp'><px>02.List of Employee Resignation</px></a></li>");
                        }
                        if (isMnMenuListEmpEducation()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/employee/list_employee_education.jsp'><px>03.List of Employee Education</px></a></li>");
                        }
                        if (isMnMenuListEmpCatByName()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/employee/list_employee_by_Name.jsp'><px>04.List of Emp Category by Name</px></a></li>");
                        }
                        if (isMnMenuListNumbAbs()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/employee/list_absence_reason.jsp'><px>05.List Number Of Absences</px></a></li>");
                        }
                        if (isMnMenuListEmpRace()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/employee/list_employee_race.jsp'><px>06.List Of Employee Race</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuMonthTraining() || isMnMenuTrainingProfile() || isMnMenuTrainingTarget() || isMnMenuTrainingRptByDpt() || isMnMenuTrainingRptByEmp()
                            || isMnMenuTrainingRptByTrainer() || isMnMenuTrainingRptByCourseDetail() || isMnMenuTrainingRptByCourseDate()) {
                        out.print("<li><a id=\"training_report\" href='#'><span><p id=\"has-sub-align\">Training Report</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuMonthTraining()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/monthly_train.jsp'><px>01.Monthly Training</px></a></li>");
                        }
                        if (isMnMenuTrainingProfile()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/src_training_profiles.jsp'><px>02.Training Profiles</px></a></li>");
                        }
                        if (isMnMenuTrainingTarget()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/src_training_target.jsp'><px>03.Training Target</px></a></li>");
                        }
                        if (isMnMenuTrainingRptByDpt()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/src_report_dept.jsp'><px>04.Report By Departement</px></a></li>");
                        }
                        if (isMnMenuTrainingRptByEmp()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/src_report_emp.jsp'><px>05.Report By Employee</px></a></li>");
                        }
                        if (isMnMenuTrainingRptByTrainer()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/src_report_trainer.jsp'><px>06.Report By Trainer</px></a></li>");
                        }
                        if (isMnMenuTrainingRptByCourseDetail()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/src_training_course.jsp'><px>07.Report By Course Detail</px></a></li>");
                        }
                        if (isMnMenuTrainingRptByCourseDate()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/training/src_training_coursedate.jsp'><px>08.Report By Course Date</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuPayrollListOfSalary()) {
                        out.print("<li><a id=\"payroll\" href='#'><span><p id=\"has-sub-align\">Payroll</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuPayrollListOfSalary()) {
                            out.print("<li id='has-sub-menu1'><a href='../report/payroll/gaji_transfer.jsp'><px>01.List Of Salary Summary</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("canteen.jsp")) {
                    out.print("<div id=\"menuheader\"><h2><spacing>Canteen</spacing></h2></div>");
                    if(isMnMenuManualVisitation() || isMnMenuCanteenSchedule()){
                    out.print("<div id=\"menu_kiri\">");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuManualVisitation() || isMnMenuCanteenSchedule()||isMnMenuChecklistGroup() || isMnMenuChecklistItem() || isMnMenuChecklistMark() || isMnMenuMenuItem() || isMnMenuMealTime()
                                || isMnMenuCommentGroup() || isMnMenuCommentQuestion()) {
                        out.print("<li><a id=\"excuse_application\" href='#'><span><p id=\"has-sub-align\">Data</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuManualVisitation()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/canteen/srccanteenvisitation.jsp'><px>01.Manual Visitation</px></a></li>");
                        }
                        if (isMnMenuCanteenSchedule()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/canteen/canteenschedule.jsp'><px>02.Canteen Schedule</px></a></li>");
                        }
                        if (isMnMenuChecklistGroup() || isMnMenuChecklistItem() || isMnMenuChecklistMark() || isMnMenuMenuItem() || isMnMenuMealTime()
                                || isMnMenuCommentGroup() || isMnMenuCommentQuestion()) {
                            out.print("<li id='has-sub-menu'><pxsubmenu> Canteen </pxsubmenu></li>");
                        
                        if (isMnMenuChecklistGroup()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/canteen/checklistgroup.jsp'><px>01.Checklist Group</px></a></li>");
                        }
                        if (isMnMenuChecklistItem()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/canteen/checklistitem.jsp'><px>02.Checklist Item</px></a></li>");
                        }
                        if (isMnMenuChecklistMark()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/canteen/checklistmark.jsp'><px>03.Checklist Mark</px></a></li>");
                        }
                        if (isMnMenuMenuItem()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/canteen/menuitem.jsp'><px>04.Menu Item</px></a></li>");
                        }
                        if (isMnMenuMealTime()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/canteen/mealtime.jsp'><px>05.Meal Time</px></a></li>");
                        }
                        if (isMnMenuCommentGroup()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/canteen/cardquestiongroup.jsp'><px>06.Comment Group</px></a></li>");
                        }
                        if (isMnMenuCommentQuestion()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/canteen/cardquestion.jsp'><px>07.Comment Question</px></a></li>");
                        }
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    out.print("</ul>");
                    out.print("</div");
                    out.print("</div>");
                }
                    //Update By Agus 17-02-2014
                    if(menuKiriKosong == false){
                    out.print("<div id=\"menu_kanan\">");
                    }else{
                    out.print("<div id=\"menu_kiri\">");
                    }
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuDailyReport()) {
                        out.print("<li><a id=\"Reports\" href='#'><span><p id=\"has-sub-align\">Reports</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuDailyReport() || isMnMenuWeeklyReport() || isMnMenuMonthlyReport()) {
                            out.print("<li id='has-sub-menu'><pxsubmenu> Detail </pxsubmenu></li>");
                        }
                        if (isMnMenuDailyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/report/canteen/detail_daily_report.jsp'><px>01.Daily Report</px></a></li>");
                        }
                        if (isMnMenuWeeklyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/report/canteen/detail_weekly_report.jsp'><px>02.Weekly Report</px></a></li>");
                        }
                        if (isMnMenuMonthlyReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/report/canteen/detail_monthly_report.jsp'><px>03.Monthly Report</px></a></li>");
                        }
                        if (isMnMenuDailyMealRecord() || isMnMenuPeriodicMealRecord() || isMnMenuMealReports() || isMnMenuMealReportsDepartement() || isMnMenuMounthlyCanteenReport()) {
                            out.print("<li id='has-sub-menu'><pxsubmenu> Summary </pxsubmenu></li>");
                        }
                        if (isMnMenuDailyMealRecord()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/report/canteen/summary_daily_report.jsp'><px>01.Daily Meal Record</px></a></li>");
                        }
                        if (isMnMenuPeriodicMealRecord()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/report/canteen/summary_periodic_report.jsp'><px>02.Periodic Meal Record</px></a></li>");
                        }
                        if (isMnMenuMealReports()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/report/canteen/periodic_meal_report.jsp'><px>03.Meal Report</px></a></li>");
                        }
                        if (isMnMenuMealReportsDepartement()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/report/canteen/summary_periodic_department.jsp'><px>04.Meal Report Departement</px></a></li>");
                        }
                        if (isMnMenuMounthlyCanteenReport()) {
                            out.print("<li id='has-sub-menu1'><a href='../harisma-canteen/report/canteen/monthly_canteen_report.jsp'><px>05.Monthly Canteen Report</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("clinic.jsp")) {
                    out.print("<div id=\"menuheader\"><h2><spacing>Clinic</spacing></h2></div>");
                    if(isMnMenuEmployeeFamily()||isMnMenuMedicalRecord()||isMnMenuEmployeeVisit()||isMnMenuGuestHandling()){
                    out.print("<div id=\"menu_kiri\">");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuEmployeeFamily()) {
                        out.print("<li><a id=\"employee_family\" href='../clinic/srcemployeefam.jsp'><span><p id=\"has-sub-align\">Employee & Family</p></span></a></li>");
                        }
                    if (isMnMenuMedicalRecord()) {
                        out.print("<li><a id=\"medical_record\" href='../clinic/disease/scrmedicalrecord.jsp?ic=2'><span><p id=\"has-sub-align\">Medical Record</p></span></a></li>");
                        }
                    if (isMnMenuEmployeeVisit()) {
                        out.print("<li><a id=\"employee_visit\" href='../clinic/empvisit/srcemployeevisit.jsp'><span><p id=\"has-sub-align\">Employee Visit</p></span></a></li>");
                        }
                    if (isMnMenuGuestHandling()) {
                        out.print("<li><a id=\"guest_handling\" href='../clinic/guest/srcguesthandling.jsp'><span><p id=\"has-sub-align\">Guest Handling</p></span></a></li>");

                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                }
                    //Update By Agus 17-02-2014
                    if(menuKiriKosong == false){
                    out.print("<div id=\"menu_kanan\">");
                    }else{
                    out.print("<div id=\"menu_kiri\">");
                    }
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuListOfMedicine() || isMnMenuMedicineConsumpsition()) {
                        out.print("<li><a id=\"Medicine\" href='#'><span><p id=\"has-sub-align\">Medicine</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuListOfMedicine()) {
                            out.print("<li id='has-sub-menu1'><a href='../clinic/stock/medicine.jsp'><px>01.List Of Medicine</px></a></li>");
                        }
                        if (isMnMenuMedicineConsumpsition()) {
                            out.print("<li id='has-sub-menu1'><a href='../clinic/stock/medconsump.jsp'><px>02.Medicine Consumption</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    out.print("<li><a id=\"Disease\" href='#'><span><p id=\"has-sub-align\">Disease</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                    out.print("<ul>");
                    if (isMnMenuDiseaseType()) {
                        out.print("<li id='has-sub-menu1'><a href='../clinic/disease/diseasetype.jsp'><px>01.Type</px></a></li>");
                    }
                    out.print("</ul>");
                    out.print("</li>");
                    if (isMnMenuMedicalLevel() || isMnMenuMedicalCase() || isMnMenuMedicalBudget() || isMnMenuGroup() || isMnMenuTypee()) {
                        out.print("<li><a id=\"Medical\" href='#'><span><p id=\"has-sub-align\">Medical</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuMedicalLevel()) {
                            out.print("<li id='has-sub-menu1'><a href='../clinic/medexpense/med_level.jsp'><px>01.Medical Level</px></a></li>");
                        }
                        if (isMnMenuMedicalCase()) {
                            out.print("<li id='has-sub-menu1'><a href='../clinic/medexpense/med_case.jsp'><px>02.Medical Case</px></a></li>");
                        }
                        if (isMnMenuMedicalBudget()) {
                            out.print("<li id='has-sub-menu1'><a href='../clinic/medexpense/med_budget.jsp'><px>03.Medical Budget</px></a></li>");
                        }
                        if (isMnMenuGroup()) {
                            out.print("<li id='has-sub-menu1'><a href='../clinic/medexpense/med_group.jsp'><px>04.Group</px></a></li>");
                        }
                        if (isMnMenuTypee()) {
                            out.print("<li id='has-sub-menu1'><a href='../clinic/medexpense/med_type.jsp'><px>05.Type</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("locker.jsp")) {
                    out.print("<div id=\"menuheader\"><h2><spacing>Locker</spacing></h2></div>");
                    out.print("<div id=\"menu_kiri\">");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuLockerr()) {
                        out.print("<li><a id=\"Locker\" href='../locker/srclocker.jsp'><span><p id=\"has-sub-align\">Locker</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("</li>");
                    }
                    if (isMnMenuLockerTreatment()) {
                        out.print("<li><a id=\"locker_treatment\" href='../locker/srclockertreatment.jsp'><span><p id=\"has-sub-align\">Locker Treatment</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("</li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("master_data.jsp")) {
                    out.print("<div id=\"menuheader\"><h2><spacing>Master Data</spacing></h2></div>");
                    out.print("<div id=\"menu_kiri\">  ");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuCompany() || isMnMenuDivision() || isMnMenuDepartement() || isMnMenuPosition() || isMnMenuSection() || isMnMenuPublicHoliday() || isMnMenuLeaveTarget()) {
                        out.print("<li><a id=\"company\" href='#'><span><p id=\"has-sub-align\">Company</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuCompany()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/company.jsp'><px>01.Company</px></a></li>");
                        }
                        if (isMnMenuDivision()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/division.jsp'><px>02.Division</px></a></li>");
                        }
                        if (isMnMenuDepartement()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/department.jsp'><px>03.Departement</px></a></li>");
                        }
                        if (isMnMenuPosition()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/srcposition.jsp'><px>04.Position</px></a></li>");
                        }
                        if (isMnMenuSection()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/srcsection.jsp'><px>05.Section</px></a></li>");
                        }
                        if (isMnMenuPublicHoliday()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/publicHoliday.jsp'><px>06.Public Holiday</px></a></li>");
                        }
                        if (isMnMenuLeaveSetting()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/leave_setting.jsp'><px>07.Leave Configuration</px></a></li>");
                        }
                        if (isMnMenuLeaveTarget()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/leaveTarget.jsp'><px>08.Leave Target</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuEducation() || isMnMenuFamilyRelation() || isMnMenuWarningg() || isMnMenuReprimand() || isMnMenuLevel() || isMnMenuCategory() || isMnMenuReligion()
                            || isMnMenuMarital() || isMnMenuRace() || isMnMenuLanguage() || isMnMenuImageAssign() || isMnMenuResigneReason() || isMnMenuAwardType() || isMnMenuAbsenanceReason()) {
                        out.print("<li><a id=\"employee\" href='#'><span><p id=\"has-sub-align\">Employee</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuEducation()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/education.jsp'><px>01.Education</px></a></li>");
                        }
                        if (isMnMenuFamilyRelation()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/famRelation.jsp'><px>02.Family Relation</px></a></li>");
                        }
                        if (isMnMenuWarningg()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/empwarning.jsp'><px>03.Warning</px></a></li>");
                        }
                        if (isMnMenuReprimand()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/empreprimand.jsp'><px>04.Reprimand</px></a></li>");
                        }
                        if (isMnMenuLevel()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/level.jsp'><px>05.Level</px></a></li>");
                        }
                        if (isMnMenuCategory()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/empcategory.jsp'><px>06.Category</px></a></li>");
                        }
                        if (isMnMenuReligion()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/religion.jsp'><px>07.Religion</px></a></li>");
                        }
                        if (isMnMenuMarital()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/marital.jsp'><px>08.Marital</px></a></li>");
                        }
                        if (isMnMenuRace()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/race.jsp'><px>09.Race</px></a></li>");
                        }
                        if (isMnMenuLanguage()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/masterlanguage.jsp'><px>10.Language</px></a></li>");
                        }
                        if (isMnMenuImageAssign()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/image_assign.jsp'><px>11.Image Assign</px></a></li>");
                        }
                        if (isMnMenuResigneReason()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/resignedreason.jsp'><px>12.Resigned Reason </px></a></li>");
                        }
                        if (isMnMenuAwardType()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/awardtype.jsp'><px>13.Award Type </px></a></li>");
                        }
                        if (isMnMenuAbsenanceReason()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/reason.jsp'><px>14.Abseance Reason </px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuPeriode() || isMnMenuCategoryy() || isMnMenuSymbol()) {
                        out.print("<li><a id=\"Schedule\" href='#'><span><p id=\"has-sub-align\">Schedule</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuPeriode()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/period.jsp'><px>01.Periode</px></a></li>");
                        }
                        if (isMnMenuCategoryy()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/schedulecategory.jsp'><px>02.Category</px></a></li>");
                        }
                        if (isMnMenuSymbol()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/srcschedulesymbol.jsp'><px>03.Symbol</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuLockerLocation() || isMnMenuLockerCondition()) {
                        out.print("<li><a id=\"locker_data\" href='#'><span><p id=\"has-sub-align\">Locker Data</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuLockerLocation()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/lockerlocation.jsp'><px>01.Locker Location</px></a></li>");
                        }
                        if (isMnMenuLockerCondition()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/lockercondition.jsp'><px>02.Locker Condition</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    //update by satrya 2014-03-22
                    if (isMnMenuLocation()) {
                        out.print("<li><a id=\"md_location\" href='#'><span><p id=\"has-sub-align\">Location</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuLocation()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/location.jsp'><px>01.Location</px></a></li>");
                        }
                        
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuGroupRank() || isMnMenuEvaluationCriteria() || isMnMenuFormCreator()) {
                        out.print("<li><a id=\"Assessment\" href='#'><span><p id=\"has-sub-align\">Assessment</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuGroupRank()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/grouprankHR.jsp'><px>01.Group Rank</px></a></li>");
                        }
                        if (isMnMenuEvaluationCriteria()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/evaluation.jsp'><px>02.Evaluation Criteria</px></a></li>");
                        }
                        if (isMnMenuFormCreator()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/assessment/assessmentFormMain.jsp'><px>03.Form Creator</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuGeneralQuestion() || isMnMenuIllnesType() || isMnMenuInterviewPoint() || isMnMenuInterviewer() || isMnMenuInterviewFactor()
                            || isMnMenuOrientationGroup() || isMnMenuOrientationActivity()) {
                        out.print("<li><a id=\"Recruitment\" href='#'><span><p id=\"has-sub-align\">Recruitment</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuGeneralQuestion()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/recrgeneral.jsp'><px>01.General Questions</px></a></li>");
                        }
                        if (isMnMenuIllnesType()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/recrillness.jsp'><px>02.Illness Type</px></a></li>");
                        }
                        if (isMnMenuInterviewPoint()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/recrinterviewpoint.jsp'><px>03.Interview Point</px></a></li>");
                        }
                        if (isMnMenuInterviewer()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/recrinterviewer.jsp'><px>04.Interviewer</px></a></li>");
                        }
                        if (isMnMenuInterviewFactor()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/recrinterviewfactor.jsp'><px>05.Interview Factor</px></a></li>");
                        }
                        if (isMnMenuOrientationGroup()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/origroup.jsp'><px>06.Orientation Group</px></a></li>");
                        }
                        if (isMnMenuOrientationActivity()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/recruitment/oriactivity.jsp'><px>07.Orientation Activity</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuCountryy() || isMnMenuProvince() || isMnMenuRegency() || isMnMenuSubRegency()) {
                        out.print("<li><a id=\"geo_area\" href='#'><span><p id=\"has-sub-align\">Geo Area</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuCountryy()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/negara.jsp'><px>01.Country </px></a></li>");
                        }
                        if (isMnMenuProvince()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/provinsi.jsp'><px>02.Province </px></a></li>");
                        }
                        if (isMnMenuRegency()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/kabupaten.jsp'><px>03.Regency </px></a></li>");
                        }
                        if (isMnMenuSubRegency()) {
                            out.print("<li id='has-sub-menu1'><a href='../masterdata/kecamatan.jsp'><px>04.Sub Regency </px></a></li>");
                        }
                        out.print("<ul>");
                        out.print("</li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("system.jsp")) {
                    out.print("<div id=\"menuheader\"><h2><spacing>System</spacing></h2></div>");
                    if(isMnMenuServiceCenter()||isMnMenuManualProcess()||isMnMenuAdminTestAdmin()||isMnMenuAdminQuerySetup()){
                    out.print("<div id=\"menu_kiri\">");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuServiceCenter()) {
                        out.print("<li><a id=\"service_center\" href='../service/service_center.jsp'><span><p id=\"has-sub-align\">Service Center</p></span></a></li>");
                    }
                    if (isMnMenuManualProcess()) {
                        out.print("<li><a id=\"manual_prosess\" href='../service/attendance_manual_calculation.jsp'><span><p id=\"has-sub-align\">Manual Process</p></span></a></li>");
                    }
                    if (isMnMenuAdminTestAdmin()) {
                        out.print("<li><a id=\"service_center\" href='../system/test_mesin_odbc.jsp'><span><p id=\"has-sub-align\">Admin Test Mesin</p></span></a></li>");
                    }
                    if (isMnMenuAdminQuerySetup()) {
                        out.print("<li><a id=\"admin_query_setup\" href='../system/query_setup.jsp'><span><p id=\"has-sub-align\">Admin Query Setup</p></span></a></li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                }
                    //Update By Agus 17-02-2014
                    if(menuKiriKosong == false){
                    out.print("<div id=\"menu_kanan\">");
                    }else{
                    out.print("<div id=\"menu_kiri\">");
                    }
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuUserList()||isMnMenuGroupPrivilege()||isMnMenuPrivilege()||isMnMenuUpdatePassword()||isMnMenuUserCompare()) {
                        out.print("<li><a id=\"user_management\" href='#'><span><p id=\"has-sub-align\">User Management</p></span></a>");
                        out.print("<ul>");
                        if (isMnMenuUserList()) {
                            out.print("<li id='has-sub-menu1'><a href='../admin/userlist.jsp'><px>01.User List</px></a></li>");
                        }
                        if (isMnMenuGroupPrivilege()) {
                            out.print("<li id='has-sub-menu1'><a href='../admin/grouplist.jsp'><px>02.Group Privilege</px></a></li>");
                        }
                        if (isMnMenuPrivilege()) {
                            out.print("<li id='has-sub-menu1'><a href='../admin/privilegelist.jsp'><px>03.Privilege  </px></a></li>");
                        }
                        if (isMnMenuUpdatePassword()) {
                            out.print("<li id='has-sub-menu1'><a href='../admin/userupdatepasswd.jsp'><px>04.Update Password</px></a></li>");
                        }
                        if (isMnMenuUserCompare()) {
                            out.print("<li id='has-sub-menu1'><a href='../employee/databank/harisma_vs_machine.jsp'><px>05.User Compare</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    if (isMnMenuSystemProperties()||isMnMenuLoginHistory()||isMnMenuSystemLog()) {
                        out.print("<li><a id=\"system_management\" href='#'><span><p id=\"has-sub-align\">System Management</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                        out.print("<ul>");
                        if (isMnMenuSystemProperties()) {
                            out.print("<li id='has-sub-menu1'><a href='../system/sysprop.jsp'><px>01.System Properties</px></a></li>");
                        }
                        if (isMnMenuLoginHistory()) {
                            out.print("<li id='has-sub-menu1'><a href='../common/logger/history.jsp'><px>02.Login History</px></a></li>");
                        }
                        if (isMnMenuSystemLog()) {
                            out.print("<li id='has-sub-menu1'><a href='../log/system_log.jsp'><px>03.System Log</px></a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    }
                    out.print("<li><a id=\"time_keeping\" href='#'><span><p id=\"has-sub-align\">Time Keeping</p></span></a><!-- untuk has-sub bisa di rubah sesuai gambarnya -->");
                    out.print("<ul>");
                    if (isMnMenuServiceManager()) {
                        out.print("<li id='has-sub-menu1'><a href='../system/timekeepingpro/svcmgr.jsp'><px>01.Service Manager</px></a></li>");
                    }
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("payroll_setup.jsp")) {
                    out.print("<div id=\"menuheader\"><h2><spacing>Payroll Setup</spacing></h2></div>");
                    if(isMnMenuGeneral()||isMnMenuPayrollPeriode()||isMnMenuBankList()||isMnMenuPayslipGroup()||isMnMenuSalaryComponent()){
                    out.print("<div id=\"menu_kiri\">");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuGeneral()) {
                        out.print("<li><a id=\"General\" href='../payroll/setup/general_list.jsp'><span><p id=\"has-sub-align\">General</p></span></a></li>");
                    }
                    if (isMnMenuPayrollPeriode()) {
                        out.print("<li><a id=\"payroll_periode\" href='../payroll/setup/period.jsp'><span><p id=\"has-sub-align\">Payroll Periode</p></span></a></li>");
                    }
                    if (isMnMenuBankList()) {
                        out.print("<li><a id=\"bank_list\" href='../payroll/setup/list-bank.jsp'><span><p id=\"has-sub-align\">Bank List</p></span></a></li>");
                    }
                    if (isMnMenuPayslipGroup()) {
                        out.print("<li><a id=\"pay_slip_group\" href='../payroll/process/pay_payslip_group.jsp'><span><p id=\"has-sub-align\">Pay Slip Group</p></span></a></li>");
                    }
                    if (isMnMenuSalaryComponent()) {
                        out.print("<li><a id=\"salary_component\" href='../payroll/setup/salary-comp.jsp'><span><p id=\"has-sub-align\">Salary Component</p></span></a></li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                }
                    //Update By Agus 17-02-2014
                    if(menuKiriKosong == false){
                    out.print("<div id=\"menu_kanan\">");
                    }else{
                    out.print("<div id=\"menu_kiri\">");
                    }
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuSalaryLevel()) {
                        out.print("<li><a id=\"salary_level\" href='../payroll/setup/salary-level.jsp'><span><p id=\"has-sub-align\">Salary Level</p></span></a></li>");
                    }
                    if (isMnMenuEmployeeSetup()) {
                        out.print("<li><a id=\"employee_setup\" href='../payroll/setup/employee-setup.jsp'><span><p id=\"has-sub-align\">Employee Setup</p></span></a></li>");
                    }
                    if (isMnMenuCurrency()) {
                        out.print("<li><a id=\"Currency\" href='../payroll/setup/currency.jsp'><span><p id=\"has-sub-align\">Currency</p></span></a></li>");
                    }
                    if (isMnMenuCurrencyRate()) {
                        out.print("<li><a id=\"Currency_rate\" href='../payroll/setup/currency_rate.jsp'><span><p id=\"has-sub-align\">Currency Rate</p></span></a></li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("overtime.jsp")) {
                    if(isMnMenuOvertimeForm()||isMnMenuOvertimeRptProcess()||isMnMenuOvertimeIndex()||isMnMenuOvertimeSummary()){
                    out.print("<div id=\"menuheader\"><h2><spacing>Overtime</spacing></h2></div>");
                    out.print("<div id=\"menu_kiri\">");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuOvertimeForm()) {
                        out.print("<li><a id=\"overtime_form\" href='../payroll/overtimeform/src_overtime.jsp'><span><p id=\"has-sub-align\">Overtime Form</p></span></a></li>");
                    }
                    if (isMnMenuOvertimeRptProcess()) {
                        out.print("<li><a id=\"overtime_report\" href='../payroll/overtimeform/src_overtime_report.jsp'><span><p id=\"has-sub-align\">Overtime Report & Process</p></span></a></li>");
                    }
                    if (isMnMenuOvertimeIndex()) {
                        out.print("<li><a id=\"overtime_index\" href='../payroll/overtime/ov-index.jsp'><span><p id=\"has-sub-align\">Overtime Index</p></span></a></li>");
                    }
                    if (isMnMenuOvertimeSummary()) {
                        out.print("<li><a id=\"overtime_summary\" href='../payroll/overtimeform/src_overtime_summary.jsp'><span><p id=\"has-sub-align\">Overtime  Summary</p></span></a></li>");
                    }
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                    }
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("payroll_process.jsp")) {
                    out.print("<div id=\"menuheader\"><h2><spacing>Payroll Process</spacing></h2></div>");
                    out.print("<div id=\"menu_kiri\">");
                    out.print("<div id='cssmenu'>");
                    out.print("<ul>");
                    if (isMnMenuPrepareData()) {
                        out.print("<li><a id=\"prepare_data\" href='" + approot + "/payroll/process/pay-pre-data.jsp'><span><p id=\"has-sub-align\">Prepare Data</p></span></a></li>");
                    }
                    if (isMnMenuPayrollInput()) {
                        out.print("<li><a id=\"payroll_input\" href='" + approot + "/payroll/process/pay-input.jsp'><span><p id=\"has-sub-align\">Payroll Input</p></span></a></li>");
                    }
                    if (isMnMenuPayrollProcess()) {
                        out.print("<li><a id=\"payroll_process\" href='" + approot + "/payroll/process/pay-process.jsp'><span><p id=\"has-sub-align\">Payroll Process</p></span></a></li>");
                    }
                    if (isMnMenuPayslipPrinting()) {
                        out.print("<li><a id=\"payslip_printing\" href='" + approot + "/payroll/process/pay-printing.jsp'><span><p id=\"has-sub-align\">Payslip  Printing</p></span></a></li>");
                    }

                    out.print("</ul>");
                    out.print("</div");
                    out.print("<div id=\"menu_kanan\">");
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("help.jsp")) {
                }
                out.print("</div>");
            } else {
                //<!--untuk IE -->
                out.print("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                if (url != null && url.length() > 0 && url.equalsIgnoreCase("home.jsp")) {
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("employee.jsp")) {                   
                        out.print("<div id=\"wrapper\">");
                        out.print("<div id=\"column-left\">");
                        out.print("<div >");
                        out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/employee.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Employee</font></b>");
                        if(isMnMenuDataBank()||isMnCompanyStructure()||isMnMenuNewEmployee()||isMnMenuAbsMan()||isMnMenuEntryPerDept()||isMnMenuUpdatePerEmp()
                                ||isMnMenuStaffRequisition()||isMnMenuEmpApplication()||isMnMenuOrientationList()||isMnMenuReminder()){
                        out.print("</div>");
                        if (isMnMenuDataBank()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/employee/databank/srcemployee.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Data Bank</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnCompanyStructure()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/employee/databank/srcEmployee_structure.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Company Structure</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuNewEmployee()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/employee/databank/new_employee_list.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">New Employee</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuAbsMan()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/employee/absence/srcabsence.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Absence Management</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuEntryPerDept()||isMnMenuUpdatePerEmp()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Recognitions</font></b>");
                            if (isMnMenuEntryPerDept()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/recognition/recognitiondep.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Entry per Departement</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuUpdatePerEmp()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/recognition/recognitiondep.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Update per Employee</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuStaffRequisition()||isMnMenuEmpApplication()||isMnMenuOrientationList()||isMnMenuReminder()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Recruitment</font></b>");
                            if (isMnMenuStaffRequisition()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/recruitment/srcstaffrequisition.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Staff Requisition</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuEmpApplication()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/recruitment/srcrecrapplication.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Employement Application</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuOrientationList()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/recruitment/srcorichecklist.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Orientation Checklist</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuReminder()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/recruitment/reminder.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">04.Reminder</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        out.print("</div>");
                        }
                        if(menuKiriKosong == false){
                        out.print("<div id=\"column-center\">");
                        }else{
                        out.print("<div id=\"column-left\">");
                        }
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                        if (isMnMenuEmpWarning()||isMnMenuEmpReprimand()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Warning & Reprimand</font></b>");
                            if (isMnMenuEmpWarning()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/warning/src_warning.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Warning</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuEmpReprimand()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/warning/src_reprimand.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Reprimand</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuExcuseForm()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Excuse Application</font></b>");
                            if (isMnMenuExcuseForm()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/leave/excuse_app_src.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Excuse Form</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuWorkingSchedule()||isMnMenuManualRegistration()||isMnMenuReGenScheduleHoly()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Attendance</font></b>");
                            if (isMnMenuWorkingSchedule()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/attendance/srcempschedule.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Working Schedule</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuManualRegistration()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/presence/srcpresence.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Manual Registration</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuReGenScheduleHoly()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/presence/Update_schedule_If_holidays.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Re-Generate Sch Holidays</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        //update by satrya 2014-03-33
                        //Menu employee Outlet
                        if (isMnMenuEmployeeOutlet()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Employee Outlet</font></b>");
                            if (isMnMenuEmployeeOutlet()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/outlet/outlet.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Employee Outlet</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            
                            out.print("</div>");
                        }
                        
                        if (isMnMenuLeaveForm()||isMnMenuLeaveAlClosing()||isMnMenuLeavellClosing()||
                        isMnMenuDpManagement()||isMnMenuRecalculate()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Leave Application</font></b>");
                            if (isMnMenuLeaveForm()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/leave/leave_app_src.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Leave Form</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuLeaveAlClosing()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/leave/leave_al_closing.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Leave AL Closing</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuLeavellClosing()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/leave/leave_ll_closing.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Leave LI Closing</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuDpManagement()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/attendance/dp.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">04.DP Management</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuDpManagement()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/leave/if_dp_not_balance.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">05.DP Re-Calculate</font></b");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        out.print("</div>");                        
                        out.print("<div id=\"column-right\">");
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                        if (isMnMenuAnualLeaveBalancing()||isMnMenuLongLeaveBalancing()||isMnMenuDPPayment()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Leave Balancing</font></b>");
                            if (isMnMenuAnualLeaveBalancing()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/system/leave/AL_Balancing.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Annual Leave</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuLongLeaveBalancing()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/system/leave/LL_Balancing.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Long Leave</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuDPPayment()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/system/leave/DP_Balancing.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Day off Payment</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuExplanations()||isMnMenuPerfomAssment()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Assessment</font></b>");
                            if (isMnMenuExplanations()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/appraisal/expcoverage.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Explanations and Coverage</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuPerfomAssment()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/appraisalnew/srcappraisal.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Performance Assessment</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                            }
                            out.print("</div>");                        
                        out.print("</div>");

                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("training.jsp")) {
                    if (isMnMenuTrainingType()||isMnMenuTrainingVanue()||isMnMenuTrainingProgram()||isMnMenuTrainingPlan()||
                    isMnMenuTrainingActual()||isMnMenuTrainingSearch()||isMnMenuTrainingHistory()) {
                        out.print("<div id=\"wrapper\">");
                        out.print("<div id=\"column-left\">");
                        out.print("<div >");
                        out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/training.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Training</font></b>");
                        out.print("</div>");
                        if (isMnMenuTrainingType()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/masterdata/list_train_type.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Training Type</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuTrainingVanue()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/masterdata/list_train_venue.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Training Vanue</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuTrainingProgram()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/masterdata/srctraining.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Training Program</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuTrainingPlan()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/employee/training/training_plan_list.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Training Plan</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuTrainingActual()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/employee/training/training_actual_list.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Training Actual</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuTrainingSearch()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/employee/training/search_training.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Training Search</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuTrainingHistory()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp");
                            out.print("<a href=\"" + approot + "/employee/training/src_training_hist_hr.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Training History</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                        out.print("</div>");
                    }
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("reports.jsp")) {                    
                        out.print("<div id=\"wrapper\">");
                        out.print("<div id=\"column-left1\">");
                        out.print("<div >");
                        out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/report.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Report</font></b>");
                        if(isMnMenuAttRecord()||isMnMenuDailyReportPresence()||isMnMenuWeeklyReportPresence()||isMnMenuMontlyReportPresence()||isMnMenuYearlyReportPresence()
                                ||isMnMenuLatnessDailyReport()||isMnMenuLatnessWeeklyReport()||isMnMenuLatnessMonthlyReport()||isMnMenuSplitShiftDailyReport()||isMnMenuSplitShiftWeeklyReport()||isMnMenuSplitShiftMonthlyReport()){
                        out.print("</div>");
                        if (isMnMenuAttRecord()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\"> Staff Control</font></b>");
                            if (isMnMenuAttRecord()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/employee/presence/att_record_monthly.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Attendance Record</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuDailyReportPresence()||isMnMenuWeeklyReportPresence()||isMnMenuMontlyReportPresence()||isMnMenuYearlyReportPresence()){
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                        out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                        out.print("&nbsp;<b><font size=\"3\">Presence</font></b>");
                        if (isMnMenuDailyReportPresence()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/report/presence/presence_report_daily.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">01.Daily Report</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuWeeklyReportPresence()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/report/presence/weekly_presence.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">02.Weekly Report</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuMontlyReportPresence()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/report/presence/monthly_presence.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">03.Monthly Report</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuYearlyReportPresence()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/report/presence/year_report_presence.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">04.Year Report</font></b");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        if (isMnMenuAttdSummaryReport()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/report/presence/summary_attendance_sheet.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">05.Attendance Summary</font></b>");
                            out.print("</a>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                        }
                        if (isMnMenuLatnessDailyReport()||isMnMenuLatnessWeeklyReport()||isMnMenuLatnessMonthlyReport()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Lateness</font></b>");
                            if (isMnMenuLatnessDailyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/lateness/lateness_report.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Daily Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuLatnessWeeklyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/lateness/lateness_weekly_report.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Weekly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuLatnessMonthlyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/lateness/lateness_monthly_report.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Monthly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuSplitShiftDailyReport()||isMnMenuSplitShiftWeeklyReport()||isMnMenuSplitShiftMonthlyReport()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Split Shift</font></b>");
                            if (isMnMenuSplitShiftDailyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/splitshift/daily_split.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Daily Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuSplitShiftWeeklyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/splitshift/weekly_split.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Weekly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuSplitShiftMonthlyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/splitshift/monthly_split.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Monthly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        out.print("</div>");
                        //Update By Agus 24-02-2014
                        }
                        if(menuKiriKosong == false){
                        out.print("<div id=\"column-center1\">");
                        }else{
                        out.print("<div id=\"column-left\">");
                        }
                        //out.print("<div id=\"column-center1\">");
                        if(isMnMenuNightShiftDailyReport()||isMnMenuNightShiftWeeklyReport()||isMnMenuNightShiftMonthlyReport()
                            ||isMnMenuAbsenteeismDailyReport()||isMnMenuAbsenteeismWeeklyReport()||isMnMenuAbsenteeismMonthlyReport()
                            ||isMnMenuSicknessDailyReport()||isMnMenuSicknessWeeklyReport()||isMnMenuSicknessMonthlyReport()||isMnMenuSicknessZeroReport()
                            ||isMnMenuSpcDispDailyReport()||isMnMenuSpcDispWeeklyReport()||isMnMenuSpcDispMonthlyReport()){
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                        if (isMnMenuNightShiftDailyReport()||isMnMenuNightShiftWeeklyReport()||isMnMenuNightShiftMonthlyReport()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Night Shift</font></b>");
                            if (isMnMenuNightShiftDailyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/nightshift/daily_night.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Daily Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuNightShiftWeeklyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/nightshift/weekly_night.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Weekly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuNightShiftMonthlyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/nightshift/monthly_night.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Monthly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuAbsenteeismDailyReport()||isMnMenuAbsenteeismWeeklyReport()||isMnMenuAbsenteeismMonthlyReport()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\"> Absenteeism </font></b>");
                            if (isMnMenuAbsenteeismDailyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/absenteeism/daily_absence.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Daily Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuAbsenteeismWeeklyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/absenteeism/weekly_absence.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Weekly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuAbsenteeismMonthlyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/absenteeism/monthly_absence.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Monthly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuSicknessDailyReport()||isMnMenuSicknessWeeklyReport()||isMnMenuSicknessMonthlyReport()||isMnMenuSicknessZeroReport()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Sickness</font></b>");
                            if (isMnMenuSicknessDailyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/sickness/daily_sickness.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Daily Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuSicknessWeeklyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/sickness/weekly_sickness.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Weekly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuSicknessMonthlyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/sickness/monthly_sickness.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Monthly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuSicknessZeroReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/sickness/zero_sickness.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">04.Zero Sickness</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuSpcDispDailyReport()||isMnMenuSpcDispWeeklyReport()||isMnMenuSpcDispMonthlyReport()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Special Dispensation</font></b>");
                            if (isMnMenuSpcDispDailyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/specialdisp/daily_specialdisp.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Daily Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuSpcDispWeeklyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/specialdisp/weekly_specialdisp.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Weekly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuSpcDispMonthlyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/specialdisp/monthly_specialdisp.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Monthly Report</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        out.print("</div>");
                        //Update By Agus 24-02-2014
                        }
                        if(menuTengahKosong == false){
                        out.print("<div id=\"column-center2\">");
                        }else if(menuKiriKosong == false){
                        out.print("<div id=\"column-center1\">");
                        }else{
                        out.print("<div id=\"column-left\">");
                        }
                        if(isMnMenuLeaveDPSum()||isMnMenuLeaveDPDetail()||isMnMenuLeaveDPSumPeriod()||isMnMenuLeaveDPDetailPeriod()||isMnMenuSpcUnpaidLeave()||isMnMenuDPExpired()
                                ||isMnMenuTraineeMonthlyReport()||isMnMenuTraineeEndPeriodReport()||isMnMenuListEmpCategory()||isMnMenuListEmpResign()||isMnMenuListEmpEducation()
                                ||isMnMenuListEmpCatByName()||isMnMenuListNumbAbs()||isMnMenuListEmpRace()){    
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            if (isMnMenuLeaveDPSum()||isMnMenuLeaveDPDetail()||isMnMenuLeaveDPSumPeriod()||isMnMenuLeaveDPDetailPeriod()||isMnMenuSpcUnpaidLeave()||isMnMenuDPExpired()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Leave Report</font></b>");
                                if (isMnMenuLeaveDPSum()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/leavedp/leave_dp_sum.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Leave & DP Summary</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuLeaveDPDetail()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/leavedp/leave_dp_detail.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.Leave & DP Detail</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuLeaveDPSumPeriod()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/leavedp/leave_department_by_period.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">03.Leave & DP Sum.Period</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuLeaveDPDetailPeriod()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/leavedp/leave_dp_detail_period.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">04.Leave & DP Detail Period</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuSpcUnpaidLeave()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/employee/leave/leave_sp_period.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">05.Special & Unpaid Period</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuDPExpired()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/attendance/dpexp_report.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">06.DP Ekspired</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }
                            if (isMnMenuTraineeMonthlyReport()||isMnMenuTraineeEndPeriodReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Trainee</font></b>");
                                if (isMnMenuTraineeMonthlyReport()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/monthly_tr_rpt.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Monthly Report</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuTraineeEndPeriodReport()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/end_tr_rpt.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.End Period</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }
                            if (isMnMenuListEmpCategory()||isMnMenuListEmpResign()||isMnMenuListEmpEducation()||isMnMenuListEmpCatByName()||isMnMenuListNumbAbs()||isMnMenuListEmpRace()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Employee</font></b>");
                                if (isMnMenuListEmpCategory()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/employee/list_employee_category.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.List of Employee Category</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuListEmpResign()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/employee/list_employee_resignation1.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.List of Employee Resignation</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuListEmpEducation()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/employee/list_employee_education.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">03.List of Employee Education</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuListEmpCatByName()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/employee/list_employee_by_Name.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">04.List of Emp Category by Name</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuListNumbAbs()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/employee/list_absence_reason.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">05.List Number Of Absences</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuListEmpRace()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/employee/list_employee_race.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">06.List Of Employee Race</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }
                            out.print("</div>");
                            
                            //24-02-2014
                            }
                            if (menuKananKosong == false){
                            out.print("<div id=\"column-right2\">");
                            }else if(menuTengahKosong == false){
                            out.print("<div id=\"column-center2\">");
                            }else if(menuKiriKosong == false){
                            out.print("<div id=\"column-center1\">");
                            }else{
                            out.print("<div id=\"column-left\">");
                            }
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            if (isMnMenuMonthTraining()||isMnMenuTrainingProfile()||isMnMenuTrainingTarget()||isMnMenuTrainingRptByDpt()||
                            isMnMenuTrainingRptByEmp()||isMnMenuTrainingRptByTrainer()||isMnMenuTrainingRptByCourseDate()||isMnMenuTrainingRptByCourseDetail()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Training Report</font></b>");
                                if (isMnMenuMonthTraining()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/monthly_train.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Monthly Training</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuTrainingProfile()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/src_training_profiles.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.Training Profiles</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuTrainingTarget()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/src_training_target.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">03.Training Target</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuTrainingRptByDpt()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/src_report_dept.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">04.Report By Departement</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuTrainingRptByEmp()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/src_report_emp.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">05.Report By Employee</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuTrainingRptByTrainer()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/src_report_trainer.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">06.Report By Trainer</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuTrainingRptByCourseDetail()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/src_training_course.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">07.Report By Course Detail</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuTrainingRptByCourseDate()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/report/training/src_training_coursedate.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">08.Report By Course Date</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }
                            if (isMnMenuPayrollListOfSalary()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Payroll</font></b>");
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/report/payroll/gaji_transfer.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\"> 01.List Of Salary Summary</font></b>");
                                out.print("</a>");
                                out.print("</div>");

                                out.print("</div>");
                            }
                            out.print("</div>");                        
                        out.print("</div>");
                        
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("canteen.jsp")) {
                    
                        out.print("<div id=\"wrapper\">");
                        out.print("<div id=\"column-left\">");
                        out.print("<div>");
                        out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/canteen.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Canteen</font></b>");
                        if(isMnMenuManualVisitation()||isMnMenuCanteenSchedule()||isMnMenuChecklistGroup()||isMnMenuChecklistItem()||isMnMenuChecklistMark()||isMnMenuMenuItem()||isMnMenuCommentGroup()
                        ||isMnMenuCommentQuestion()||isMnMenuMealTime()){
                        out.print("</div>");
                        if (isMnMenuManualVisitation()||isMnMenuCanteenSchedule()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Data</font></b>");
                            if (isMnMenuManualVisitation()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/harisma-canteen/canteen/srccanteenvisitation.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Manual Visitation</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuCanteenSchedule()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/harisma-canteen/canteen/canteenschedule.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Canteen Schedule</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuChecklistGroup()||isMnMenuChecklistItem()||isMnMenuChecklistMark()||isMnMenuMenuItem()||isMnMenuCommentGroup()
                        ||isMnMenuCommentQuestion()||isMnMenuMealTime()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Canteen</font></b>");
                            if (isMnMenuChecklistGroup()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/harisma-canteen/canteen/checklistgroup.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Checklist Group</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuChecklistItem()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/harisma-canteen/canteen/checklistitem.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Checklist Item</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuChecklistMark()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/harisma-canteen/canteen/checklistmark.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Checklist Mark</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuMenuItem()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/harisma-canteen/canteen/menuitem.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">04.Menu Item</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuMealTime()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/harisma-canteen/canteen/mealtime.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">05.Meal Time</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuCommentGroup()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/harisma-canteen/canteen/cardquestiongroup.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">06.Comment Group</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuCommentQuestion()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/harisma-canteen/canteen/cardquestion.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">07.Comment Question</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        out.print("</div>");
                        }
                        if (menuKiriKosong == false){    
                        out.print("<div id=\"column-center\">");
                        }else{
                        out.print("<div id=\"column-left\">");
                        }
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Reports</font></b>");
                                out.print("</div>");
                            
                            if (isMnMenuDailyReport()||isMnMenuWeeklyReport()||isMnMenuMonthlyReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Detail</font></b>");
                                if (isMnMenuDailyReport()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/harisma-canteen/report/canteen/detail_daily_report.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Daily Report</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuWeeklyReport()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/harisma-canteen/report/canteen/detail_weekly_report.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.Weekly Report</font></b>");
                                    out.print("</a>");
                                    out.print("</div>  ");
                                }
                                if (isMnMenuMonthlyReport()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/harisma-canteen/report/canteen/detail_monthly_report.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">03.Monthly Report</font></b>");
                                    out.print("</a>");
                                    out.print("</div> ");
                                }
                                out.print("</div>");
                            }
                            if (isMnMenuDailyMealRecord()||isMnMenuPeriodicMealRecord()||isMnMenuMealReports()||isMnMenuMealReportsDepartement()
                            ||isMnMenuMounthlyCanteenReport()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Summary</font></b>");
                                if (isMnMenuDailyMealRecord()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/harisma-canteen/report/canteen/summary_daily_report.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Daily Meal Record</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuPeriodicMealRecord()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/harisma-canteen/report/canteen/summary_periodic_report.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.Periodic Meal Record</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuMealReports()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/harisma-canteen/report/canteen/periodic_meal_report.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">03.Meal Reports</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuMealReportsDepartement()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/harisma-canteen/report/canteen/summary_periodic_department.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">04.Meal Reports Departement</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuMounthlyCanteenReport()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/harisma-canteen/report/canteen/monthly_canteen_report.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">05.Mounthly Canteen Report</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }
                            out.print("</div>");                        
                    
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("clinic.jsp")) {
                    
                        out.print("<div id=\"wrapper\">");
                        out.print("<div id=\"column-left1\">");
                        out.print("<div>");
                        out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/clinic.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Clinic</font></b>");
                        if(isMnMenuEmployeeFamily()||isMnMenuMedicalRecord()||isMnMenuEmployeeVisit()||isMnMenuGuestHandling()){
                        out.print("</div>");
                        if (isMnMenuEmployeeFamily()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/clinic/srcemployeefam.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Employee & Family</font></b>");
                            out.print("</div>");
                        }
                        if (isMnMenuMedicalRecord()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/clinic/disease/scrmedicalrecord.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Medical Record</font></b>");
                            out.print("</div>");
                        }
                        if (isMnMenuEmployeeVisit()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/clinic/empvisit/srcemployeevisit.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Employee Visit</font></b>");
                            out.print("</div>");
                        }
                        if (isMnMenuGuestHandling()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/clinic/guest/srcguesthandling.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Guest Handling</font></b>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                        }if(menuKiriKosong == false){
                        out.print("<div id=\"column-center1\">");
                        }else{
                        out.print("<div id=\"column-left1\">");
                        }
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                        if (isMnMenuListOfMedicine()||isMnMenuMedicineConsumpsition()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Medicine</font></b>");
                            if (isMnMenuListOfMedicine()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/clinic/stock/medicine.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.List Of Medicine</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuMedicineConsumpsition()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/clinic/stock/medconsump.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Medicine Consumption</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuDiseaseType()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Disease </font></b>");
                            if (isMnMenuDiseaseType()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/clinic/disease/diseasetype.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Type</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuMedicalLevel()||isMnMenuMedicalCase()||isMnMenuMedicalBudget()||isMnMenuGroup()||isMnMenuTypee()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Medical</font></b>");
                            if (isMnMenuMedicalLevel()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/clinic/medexpense/med_level.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Medical Level</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuMedicalCase()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/clinic/medexpense/med_case.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Medical Case</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuMedicalBudget()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/clinic/medexpense/med_budget.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Medical Budget</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuGroup()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/clinic/medexpense/med_group.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">04.Group</font></b>");
                                out.print("</a");
                                out.print("</div>");
                            }
                            if (isMnMenuTypee()) {
                                out.print("</div>");
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/clinic/medexpense/med_type.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">05.Type</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        out.print("</div>");
                    
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("locker.jsp")) {
                    
                        out.print("<div id=\"wrapper\">");
                        out.print("<div id=\"column-left1\">");
                        out.print("<div >");
                        out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/locker.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Locker</font></b>");
                        out.print("</div>");
                        if (isMnMenuLockerr()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/locker/srclocker.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Locker</font></b>");
                            out.print("</a> ");
                            out.print("</div>");
                        }
                        if (isMnMenuLockerTreatment()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<a href=\"" + approot + "/locker/srclockertreatment.jsp\" class=\"menulink\">");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Locker Treatment</font></b>");
                            out.print("</a> ");
                            out.print("</div>");
                        }
                        out.print("</div>");
                    
                    out.print("</div>");
                } 
                else if (url != null && url.length() > 0 && url.equalsIgnoreCase("master_data.jsp")) {

                    //<!-- agus -->20140203                    
                        out.print("<div id=\"wrapper\">");
                        out.print("<div id=\"column-left1\">");
                        out.print("<div >");
                        out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/master.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Master Data</font></b>");
                        if(isMnMenuCompany()||isMnMenuDivision()||isMnMenuDepartement()||isMnMenuPosition()||isMnMenuSection()||isMnMenuPublicHoliday()||isMnMenuLeaveTarget()
                           ||isMnMenuEducation()||isMnMenuFamilyRelation()||isMnMenuWarningg()||isMnMenuReprimand()||isMnMenuLevel()||isMnMenuCategoryy()||isMnMenuReligion()
                           ||isMnMenuMarital()||isMnMenuRace()||isMnMenuLanguage()||isMnMenuImageAssign()||isMnMenuResigneReason()||isMnMenuAwardType()||isMnMenuAbsenanceReason()){
                        out.print("</div>");
                        if (isMnMenuCompany()||isMnMenuDivision()||isMnMenuDepartement()||isMnMenuPosition()||isMnMenuSection()||isMnMenuPublicHoliday()||isMnMenuLeaveTarget()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\"> Company</font></b>");
                            if (isMnMenuCompany()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/company.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Company</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuDivision()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/division.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Division</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuDepartement()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/department.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Departement</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuPosition()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/srcposition.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">04.Position</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuSection()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/srcsection.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">05.Section</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuPublicHoliday()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/publicHoliday.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">06.Public Holiday</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuLeaveTarget()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/leaveTarget.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">07.Leave Target</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        if (isMnMenuEducation()||isMnMenuFamilyRelation()||isMnMenuWarningg()||isMnMenuReprimand()||isMnMenuLevel()||isMnMenuCategoryy()||isMnMenuReligion()
                        ||isMnMenuMarital()||isMnMenuRace()||isMnMenuLanguage()||isMnMenuImageAssign()||isMnMenuResigneReason()||isMnMenuAwardType()||isMnMenuAbsenanceReason()) {
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                            out.print("&nbsp;<b><font size=\"3\">Employee</font></b>");
                            if (isMnMenuEducation()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/education.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">01.Education</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuFamilyRelation()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/famRelation.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">02.Family Relation</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuWarningg()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/empwarning.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">03.Warning</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuReprimand()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/empreprimand.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">04.Reprimand</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuLevel()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/level.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">05.Level</font></b>");
                                out.print("</a>");
                                out.print("</div> ");
                            }
                            if (isMnMenuCategoryy()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/empcategory.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">06.Category</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuReligion()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/religion.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">07.Religion</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuMarital()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/marital.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">08.Marital</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuRace()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/race.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">09.Race</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuLanguage()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/masterlanguage.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">10.Laguage</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuImageAssign()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/image_assign.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">11.Image Assign</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuResigneReason()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/resignedreason.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">12.Resigned Reason</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuAwardType()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/awardtype.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">13.Award Type</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            if (isMnMenuAbsenanceReason()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<a href=\"" + approot + "/masterdata/reason.jsp\" class=\"menulink\">");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">14.Absenace Reason</font></b>");
                                out.print("</a>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                        out.print("</div>");
                        }if(menuKiriKosong == false){
                            out.print("<div id=\"column-center1\">");
                        }else{
                            out.print("<div id=\"column-left1\">");
                        }
                        if(isMnMenuPeriode()||isMnMenuCategory()||isMnMenuSymbol()||isMnMenuLockerLocation()||isMnMenuLockerCondition()
                                ||isMnMenuGroupRank()||isMnMenuEvaluationCriteria()||isMnMenuFormCreator() || isMnMenuLocation()){    
                        out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            if (isMnMenuPeriode()||isMnMenuCategory()||isMnMenuSymbol()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Schedule</font></b>");
                                if (isMnMenuPeriode()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/period.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Periode</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuCategory()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/schedulecategory.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.Category</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuSymbol()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/srcschedulesymbol.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">03.Symbol</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }
                            if (isMnMenuLockerLocation()||isMnMenuLockerCondition()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\"> Locker Data </font></b>");
                                if (isMnMenuLockerLocation()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/lockerlocation.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Locker Location</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuLockerCondition()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/lockercondition.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.Locker Condition</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }
                            
                            //update by satrya 2014-03-22
                            if (isMnMenuLocation()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\"> Location </font></b>");
                                if (isMnMenuLocation()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/location.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Location</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                
                                out.print("</div>");
                            }
                            
                            if (isMnMenuGroupRank()||isMnMenuEvaluationCriteria()||isMnMenuFormCreator()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Assessment</font></b>");
                                if (isMnMenuGroupRank()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/grouprankHR.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Group Rank</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuEvaluationCriteria()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/evaluation.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.Evaluation Criteria</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuFormCreator()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/assessment/assessmentFormMain.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">03.Form Creator</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }
                            out.print("</div>");                       
                        }if(menuTengahKosong){
                            out.print("<div id=\"column-center2\">");
                        }else if(menuKiriKosong){
                            out.print("<div id=\"column-center1\">");
                        }else{
                            out.print("<div id=\"column-left1\">");
                        }
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
                            if (isMnMenuGeneralQuestion()||isMnMenuIllnesType()||isMnMenuInterviewPoint()||isMnMenuInterviewer()
                            ||isMnMenuInterviewFactor()||isMnMenuOrientationGroup()||isMnMenuOrientationActivity()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Recruitment</font></b>");
                                if (isMnMenuGeneralQuestion()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/employee/recruitment/recrgeneral.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.General Question</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuIllnesType()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/employee/recruitment/recrillness.jspreport/leavedp/leave_dp_detail.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.Illness Type</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuInterviewPoint()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/employee/recruitment/recrinterviewpoint.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">03.Interview Point</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuInterviewer()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/employee/recruitment/recrinterviewer.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">04.Interviewer</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuInterviewFactor()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/employee/recruitment/recrinterviewfactor.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">05.Interview Factor</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuOrientationGroup()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/employee/recruitment/origroup.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">06.Orientation Group</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuOrientationActivity()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/employee/recruitment/oriactivity.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">07.Orientation Activity</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }
                            if (isMnMenuCountryy()||isMnMenuProvince()||isMnMenuRegency()||isMnMenuSubRegency()) {
                                out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                out.print("&nbsp;<b><font size=\"3\">Geo Area</font></b>");
                                if (isMnMenuCountryy()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/negara.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">01.Country</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuProvince()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/provinsi.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">02.Province</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuRegency()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/kabupaten.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">03.Regency</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                }
                                if (isMnMenuSubRegency()) {
                                    out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    out.print("<a href=\"" + approot + "/masterdata/kecamatan.jsp\" class=\"menulink\">");
                                    out.print("<img src=\"" + approot + "/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
                                    out.print("&nbsp;<b><font size=\"3\">04.Sub Regency</font></b>");
                                    out.print("</a>");
                                    out.print("</div>");
                                    out.print("</div>");
                                }
                                out.print("</div>");
                            }                        

                        out.print("</div>");

                        //<!-- agus -->20140203

                    
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("system.jsp")) {
				out.print("<div id=\"wrapper\">");
				out.print("<div id=\"column-left\">");
				out.print("<div>");
				out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/system.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">System</font></b>");
				if(isMnMenuServiceCenter()||isMnMenuManualProcess()||isMnMenuAdminTestAdmin()||isMnMenuAdminQuerySetup()){
                                out.print("</div>");
				if(isMnMenuServiceCenter()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/service/service_center.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> System Center</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuManualProcess()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/service/attendance_manual_calculation.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Manual Process</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuAdminTestAdmin()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/system/test_mesin_odbc.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Admin Test Mesin</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuAdminQuerySetup()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/system/query_setup.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Admin Query Setup</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				out.print("</div>");
                                }if(menuKiriKosong == false){
				out.print("<div id=\"column-center\">");
                                }else{
                                out.print("<div id=\"column-left\">");
                                }
				out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
				out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
				if(isMnMenuUserList()||isMnMenuGroupPrivilege()||isMnMenuPrivilege()||isMnMenuUpdatePassword()||isMnMenuUserCompare()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
						out.print("&nbsp;<b><font size=\"3\"> User Management</font></b>");
						if(isMnMenuUserList()){
							out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							out.print("<a href=\""+approot+"/admin/userlist.jsp\" class=\"menulink\">");
								out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
								out.print("&nbsp;<b><font size=\"3\"> 01.User List</font></b>");
							out.print("</a>");
							out.print("</div>");
						}
						if(isMnMenuGroupPrivilege()){
							out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							out.print("<a href=\""+approot+"/admin/grouplist.jsp\" class=\"menulink\">");
								out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
								out.print("&nbsp;<b><font size=\"3\"> 02.Group Privilege</font></b>");
							out.print("</a>");
							out.print("</div>");
						}
						if(isMnMenuPrivilege()){
							out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							out.print("<a href=\""+approot+"/admin/privilegelist.jsp\" class=\"menulink\">");
								out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
								out.print("&nbsp;<b><font size=\"3\"> 03.Privilege</font></b>");
							out.print("</a>");
							out.print("</div>");
						}
						if(isMnMenuUpdatePassword()){
							out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							out.print("<a href=\""+approot+"/admin/userupdatepasswd.jsp\" class=\"menulink\">");
								out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
								out.print("&nbsp;<b><font size=\"3\"> 04.Update password</font></b>");
							out.print("</a>");
							out.print("</div>");
						}
						if(isMnMenuUserCompare()){
							out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								out.print("<a href=\""+approot+"/employee/databank/harisma_vs_machine.jsp\" class=\"menulink\">");
									out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
									out.print("&nbsp;<b><font size=\"3\"> 05.User Compare</font></b>");
								out.print("</a>");
							out.print("</div>");
						}
					out.print("</div>");
				}
				if(isMnMenuSystemProperties()||isMnMenuLoginHistory()||isMnMenuSystemLog()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
					out.print("&nbsp;<b><font size=\"3\">System Management</font></b>");
					if(isMnMenuSystemProperties()){
						out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							out.print("<a href=\""+approot+"/system/index.jsp\" class=\"menulink\">");
								out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
								out.print("&nbsp;<b><font size=\"3\"> 01.System Properties</font></b>");
							out.print("</a>");
						out.print("</div>");
					}
					if(isMnMenuLoginHistory()){
						out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							out.print("<a href=\""+approot+"/common/logger/history.jsp\" class=\"menulink\">");
								out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
								out.print("&nbsp;<b><font size=\"3\"> 02.Login History</font></b>");
							out.print("</a>");
						out.print("</div>");
					}
					if(isMnMenuSystemLog()){
						out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							out.print("<a href=\""+approot+"/log/system_log.jsp\" class=\"menulink\">");
								out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
								out.print("&nbsp;<b><font size=\"3\"> 03.System Log</font></b>");
							out.print("</a>");
						out.print("</div>");
					}
					out.print("</div>");
				}
				if(isMnMenuServiceManager()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
						out.print("&nbsp;<b><font size=\"3\">Time Keeping</font></b>");
						if(isMnMenuServiceManager()){
							out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								out.print("<a href=\""+approot+"/system/timekeepingpro/svcmgr.jsp\" class=\"menulink\">");
									out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
									out.print("&nbsp;<b><font size=\"3\"> 01.Service Manager</font></b>");
								out.print("</a>");
							out.print("</div>");
						}                                         
					out.print("</div>");                             
				}
				out.print("</div>");
				out.print("</div>");	
			
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("payroll_setup.jsp")) {
                    
				out.print("<div id=\"wrapper\">");
				out.print("<div id=\"column-left\">");
				out.print("<div >");
                                out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/paysetup.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Payroll Setup</font></b>");
                                if(isMnMenuGeneral()||isMnMenuPayrollPeriode()||isMnMenuBankList()||isMnMenuPayslipGroup()||isMnMenuSalaryComponent()){
                                out.print("</div>");
				if(isMnMenuGeneral()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.print("<a href=\""+approot+"/payroll/setup/general_list.jsp\" class=\"menulink\">");
						out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
						out.print("&nbsp;<b><font size=\"3\">General</font></b>");
					out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuPayrollPeriode()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.print("<a href=\""+approot+"/payroll/setup/period.jsp\" class=\"menulink\">");
						out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
						out.print("&nbsp;<b><font size=\"3\">Payroll Periode</font></b>");
					out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuBankList()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/setup/list-bank.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\">Bank List</font></b>");
						out.print("</a>");
					out.print("</div>");
				} 
				if(isMnMenuPayslipGroup()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/process/pay_payslip_group.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\">Pay Slip Group</font></b>");
						out.print("</a>");
					out.print("</div>");
				} 
				if(isMnMenuSalaryComponent()){        
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/setup/salary-comp.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\">Salary Component</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				out.print("</div>");
                                }if(menuKiriKosong == false){
				out.print("<div id=\"column-center\">");
                                }else{
                                out.print("<div id=\"column-left\">");
                                }
				out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
				out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>");
				if(isMnMenuSalaryLevel()){ 
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/setup/salary-level.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Salary Level</font></b>");
						out.print("</a>");
					out.print("</div>");
				} 
				if(isMnMenuEmployeeSetup()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/setup/employee-setup.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Employee Setup</font></b>");
						out.print("</a>");
					out.print("</div>");
				} 
				if(isMnMenuCurrency()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/setup/currency.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Currency</font></b>");
						out.print("</a>");
					out.print("</div>");
				} 
				if(isMnMenuCurrencyRate()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/setup/currency_rate.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Currency Rate</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				out.print("</div>");
			out.print("</div>");
			
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("overtime.jsp")) {
                    
				out.print("<div id=\"wrapper\">");
				out.print("<div id=\"column-left\">");
				out.print("<div >");
					out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/overtime.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Overtime</font></b>");
				out.print("</div>");
				if(isMnMenuOvertimeForm()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/overtimeform/src_overtime.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Overtime Form</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuOvertimeRptProcess()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/overtimeform/src_overtime_report.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Overtime Report & Process</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuOvertimeIndex()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/overtime/ov-index.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Overtime Index</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuOvertimeSummary()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/overtimeform/src_overtime_summary.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Overtime Summary</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				out.print("</div>");
			out.print("</div>");
			
                } else if (url != null && url.length() > 0 && url.equalsIgnoreCase("payroll_process.jsp")) {
                    
				out.print("<div id=\"wrapper\">");
				out.print("<div id=\"column-left\">");
				out.print("<div >");
					out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/payprosess.png\" align=\"absmiddle\">&nbsp;<b><font size=\"5\">Payroll Process</font></b>");
				out.print("</div>");
				if(isMnMenuPrepareData()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/process/pay-pre-data.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Prepare Data</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuPayrollInput()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/process/pay-input.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Payroll Input</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuPayrollProcess()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/process/pay-process.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Payroll Process</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				if(isMnMenuPayslipPrinting()){
					out.print("<div align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						out.print("<a href=\""+approot+"/payroll/process/pay-printing.jsp\" class=\"menulink\">");
							out.print("<img src=\""+approot+"/menustylesheet/icon_flyout_with_ie/bulet.png\" align=\"absmiddle\">");
							out.print("&nbsp;<b><font size=\"3\"> Payslip Printing</font></b>");
						out.print("</a>");
					out.print("</div>");
				}
				out.print("</div>");
			out.print("</div>");
			
                } else {
                    out.print("<tr>");
                    out.print("<td>&nbsp;</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align=\"center\">Tidak ada modul yang anda cari</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
            }
        } catch (Exception exc) {
        }

    }

    /**
     * @return the mnMenuEmployee
     */
    public boolean isMnMenuEmployee() {
        return mnMenuEmployee;
    }

    /**
     * @param mnMenuEmployee the mnMenuEmployee to set
     */
    public void setMnMenuEmployee(boolean mnMenuEmployee) {
        this.mnMenuEmployee = mnMenuEmployee;
    }

    /**
     * @return the mnMenuDataBank
     */
    public boolean isMnMenuDataBank() {
        return mnMenuDataBank;
    }

    /**
     * @param mnMenuDataBank the mnMenuDataBank to set
     */
    public void setMnMenuDataBank(boolean mnMenuDataBank) {
        this.mnMenuDataBank = mnMenuDataBank;
    }

    /**
     * @return the mnCompanyStructure
     */
    public boolean isMnCompanyStructure() {
        return mnCompanyStructure;
    }

    /**
     * @param mnCompanyStructure the mnCompanyStructure to set
     */
    public void setMnCompanyStructure(boolean mnCompanyStructure) {
        this.mnCompanyStructure = mnCompanyStructure;
    }

    /**
     * @return the mnMenuNewEmployee
     */
    public boolean isMnMenuNewEmployee() {
        return mnMenuNewEmployee;
    }

    /**
     * @param mnMenuNewEmployee the mnMenuNewEmployee to set
     */
    public void setMnMenuNewEmployee(boolean mnMenuNewEmployee) {
        this.mnMenuNewEmployee = mnMenuNewEmployee;
    }

    /**
     * @return the mnMenuAbsMan
     */
    public boolean isMnMenuAbsMan() {
        return mnMenuAbsMan;
    }

    /**
     * @param mnMenuAbsMan the mnMenuAbsMan to set
     */
    public void setMnMenuAbsMan(boolean mnMenuAbsMan) {
        this.mnMenuAbsMan = mnMenuAbsMan;
    }

    /**
     * @return the mnMenuRecognitions
     */
    public boolean isMnMenuRecognitions() {
        return mnMenuRecognitions;
    }

    /**
     * @param mnMenuRecognitions the mnMenuRecognitions to set
     */
    public void setMnMenuRecognitions(boolean mnMenuRecognitions) {
        this.mnMenuRecognitions = mnMenuRecognitions;
    }

    /**
     * @return the mnMenuEntryPerDept
     */
    public boolean isMnMenuEntryPerDept() {
        return mnMenuEntryPerDept;
    }

    /**
     * @param mnMenuEntryPerDept the mnMenuEntryPerDept to set
     */
    public void setMnMenuEntryPerDept(boolean mnMenuEntryPerDept) {
        this.mnMenuEntryPerDept = mnMenuEntryPerDept;
    }

    /**
     * @return the mnMenuUpdatePerEmp
     */
    public boolean isMnMenuUpdatePerEmp() {
        return mnMenuUpdatePerEmp;
    }

    /**
     * @param mnMenuUpdatePerEmp the mnMenuUpdatePerEmp to set
     */
    public void setMnMenuUpdatePerEmp(boolean mnMenuUpdatePerEmp) {
        this.mnMenuUpdatePerEmp = mnMenuUpdatePerEmp;
    }

    /**
     * @return the mnMenuRecruitment
     */
    public boolean isMnMenuRecruitment() {
        return mnMenuRecruitment;
    }

    /**
     * @param mnMenuRecruitment the mnMenuRecruitment to set
     */
    public void setMnMenuRecruitment(boolean mnMenuRecruitment) {
        this.mnMenuRecruitment = mnMenuRecruitment;
    }

    /**
     * @return the mnMenuStaffRequisition
     */
    public boolean isMnMenuStaffRequisition() {
        return mnMenuStaffRequisition;
    }

    /**
     * @param mnMenuStaffRequisition the mnMenuStaffRequisition to set
     */
    public void setMnMenuStaffRequisition(boolean mnMenuStaffRequisition) {
        this.mnMenuStaffRequisition = mnMenuStaffRequisition;
    }

    /**
     * @return the mnMenuEmpApplication
     */
    public boolean isMnMenuEmpApplication() {
        return mnMenuEmpApplication;
    }

    /**
     * @param mnMenuEmpApplication the mnMenuEmpApplication to set
     */
    public void setMnMenuEmpApplication(boolean mnMenuEmpApplication) {
        this.mnMenuEmpApplication = mnMenuEmpApplication;
    }

    /**
     * @return the mnMenuOrientationList
     */
    public boolean isMnMenuOrientationList() {
        return mnMenuOrientationList;
    }

    /**
     * @param mnMenuOrientationList the mnMenuOrientationList to set
     */
    public void setMnMenuOrientationList(boolean mnMenuOrientationList) {
        this.mnMenuOrientationList = mnMenuOrientationList;
    }

    /**
     * @return the mnMenuReminder
     */
    public boolean isMnMenuReminder() {
        return mnMenuReminder;
    }

    /**
     * @param mnMenuReminder the mnMenuReminder to set
     */
    public void setMnMenuReminder(boolean mnMenuReminder) {
        this.mnMenuReminder = mnMenuReminder;
    }

    /**
     * @return the mnMenuWarning
     */
    public boolean isMnMenuWarning() {
        return mnMenuWarning;
    }

    /**
     * @param mnMenuWarning the mnMenuWarning to set
     */
    public void setMnMenuWarning(boolean mnMenuWarning) {
        this.mnMenuWarning = mnMenuWarning;
    }

    /**
     * @return the mnMenuEmpWarning
     */
    public boolean isMnMenuEmpWarning() {
        return mnMenuEmpWarning;
    }

    /**
     * @param mnMenuEmpWarning the mnMenuEmpWarning to set
     */
    public void setMnMenuEmpWarning(boolean mnMenuEmpWarning) {
        this.mnMenuEmpWarning = mnMenuEmpWarning;
    }

    /**
     * @return the mnMenuEmpReprimand
     */
    public boolean isMnMenuEmpReprimand() {
        return mnMenuEmpReprimand;
    }

    /**
     * @param mnMenuEmpReprimand the mnMenuEmpReprimand to set
     */
    public void setMnMenuEmpReprimand(boolean mnMenuEmpReprimand) {
        this.mnMenuEmpReprimand = mnMenuEmpReprimand;
    }

    /**
     * @return the mnMenuExcuse
     */
    public boolean isMnMenuExcuse() {
        return mnMenuExcuse;
    }

    /**
     * @param mnMenuExcuse the mnMenuExcuse to set
     */
    public void setMnMenuExcuse(boolean mnMenuExcuse) {
        this.mnMenuExcuse = mnMenuExcuse;
    }

    /**
     * @return the mnMenuExcuseForm
     */
    public boolean isMnMenuExcuseForm() {
        return mnMenuExcuseForm;
    }

    /**
     * @param mnMenuExcuseForm the mnMenuExcuseForm to set
     */
    public void setMnMenuExcuseForm(boolean mnMenuExcuseForm) {
        this.mnMenuExcuseForm = mnMenuExcuseForm;
    }

    /**
     * @return the mnMenuAttdance
     */
    public boolean isMnMenuAttdance() {
        return mnMenuAttdance;
    }

    /**
     * @param mnMenuAttdance the mnMenuAttdance to set
     */
    public void setMnMenuAttdance(boolean mnMenuAttdance) {
        this.mnMenuAttdance = mnMenuAttdance;
    }

    /**
     * @return the mnMenuWorkingSchedule
     */
    public boolean isMnMenuWorkingSchedule() {
        return mnMenuWorkingSchedule;
    }

    /**
     * @param mnMenuWorkingSchedule the mnMenuWorkingSchedule to set
     */
    public void setMnMenuWorkingSchedule(boolean mnMenuWorkingSchedule) {
        this.mnMenuWorkingSchedule = mnMenuWorkingSchedule;
    }

    /**
     * @return the mnMenuManualRegistration
     */
    public boolean isMnMenuManualRegistration() {
        return mnMenuManualRegistration;
    }

    /**
     * @param mnMenuManualRegistration the mnMenuManualRegistration to set
     */
    public void setMnMenuManualRegistration(boolean mnMenuManualRegistration) {
        this.mnMenuManualRegistration = mnMenuManualRegistration;
    }

    /**
     * @return the mnMenuReGenScheduleHoly
     */
    public boolean isMnMenuReGenScheduleHoly() {
        return mnMenuReGenScheduleHoly;
    }

    /**
     * @param mnMenuReGenScheduleHoly the mnMenuReGenScheduleHoly to set
     */
    public void setMnMenuReGenScheduleHoly(boolean mnMenuReGenScheduleHoly) {
        this.mnMenuReGenScheduleHoly = mnMenuReGenScheduleHoly;
    }

    /**
     * @return the mnMenuLeaveForm
     */
    public boolean isMnMenuLeaveForm() {
        return mnMenuLeaveForm;
    }

    /**
     * @param mnMenuLeaveForm the mnMenuLeaveForm to set
     */
    public void setMnMenuLeaveForm(boolean mnMenuLeaveForm) {
        this.mnMenuLeaveForm = mnMenuLeaveForm;
    }

    /**
     * @return the mnMenuLeaveAlClosing
     */
    public boolean isMnMenuLeaveAlClosing() {
        return mnMenuLeaveAlClosing;
    }

    /**
     * @param mnMenuLeaveAlClosing the mnMenuLeaveAlClosing to set
     */
    public void setMnMenuLeaveAlClosing(boolean mnMenuLeaveAlClosing) {
        this.mnMenuLeaveAlClosing = mnMenuLeaveAlClosing;
    }

    /**
     * @return the mnMenuLeavellClosing
     */
    public boolean isMnMenuLeavellClosing() {
        return mnMenuLeavellClosing;
    }

    /**
     * @param mnMenuLeavellClosing the mnMenuLeavellClosing to set
     */
    public void setMnMenuLeavellClosing(boolean mnMenuLeavellClosing) {
        this.mnMenuLeavellClosing = mnMenuLeavellClosing;
    }

    /**
     * @return the mnMenuDpManagement
     */
    public boolean isMnMenuDpManagement() {
        return mnMenuDpManagement;
    }

    /**
     * @param mnMenuDpManagement the mnMenuDpManagement to set
     */
    public void setMnMenuDpManagement(boolean mnMenuDpManagement) {
        this.mnMenuDpManagement = mnMenuDpManagement;
    }

    /**
     * @return the mnMenuRecalculate
     */
    public boolean isMnMenuRecalculate() {
        return mnMenuRecalculate;
    }

    /**
     * @param mnMenuRecalculate the mnMenuRecalculate to set
     */
    public void setMnMenuRecalculate(boolean mnMenuRecalculate) {
        this.mnMenuRecalculate = mnMenuRecalculate;
    }

    /**
     * @return the mnMenuAnualLeaveBalancing
     */
    public boolean isMnMenuAnualLeaveBalancing() {
        return mnMenuAnualLeaveBalancing;
    }

    /**
     * @param mnMenuAnualLeaveBalancing the mnMenuAnualLeaveBalancing to set
     */
    public void setMnMenuAnualLeaveBalancing(boolean mnMenuAnualLeaveBalancing) {
        this.mnMenuAnualLeaveBalancing = mnMenuAnualLeaveBalancing;
    }

    /**
     * @return the mnMenuLongLeaveBalancing
     */
    public boolean isMnMenuLongLeaveBalancing() {
        return mnMenuLongLeaveBalancing;
    }

    /**
     * @param mnMenuLongLeaveBalancing the mnMenuLongLeaveBalancing to set
     */
    public void setMnMenuLongLeaveBalancing(boolean mnMenuLongLeaveBalancing) {
        this.mnMenuLongLeaveBalancing = mnMenuLongLeaveBalancing;
    }

    /**
     * @return the mnMenuDPPayment
     */
    public boolean isMnMenuDPPayment() {
        return mnMenuDPPayment;
    }

    /**
     * @param mnMenuDPPayment the mnMenuDPPayment to set
     */
    public void setMnMenuDPPayment(boolean mnMenuDPPayment) {
        this.mnMenuDPPayment = mnMenuDPPayment;
    }

    /**
     * @return the mnMenuExplanations
     */
    public boolean isMnMenuExplanations() {
        return mnMenuExplanations;
    }

    /**
     * @param mnMenuExplanations the mnMenuExplanations to set
     */
    public void setMnMenuExplanations(boolean mnMenuExplanations) {
        this.mnMenuExplanations = mnMenuExplanations;
    }

    /**
     * @return the mnMenuPerfomAssment
     */
    public boolean isMnMenuPerfomAssment() {
        return mnMenuPerfomAssment;
    }

    /**
     * @param mnMenuPerfomAssment the mnMenuPerfomAssment to set
     */
    public void setMnMenuPerfomAssment(boolean mnMenuPerfomAssment) {
        this.mnMenuPerfomAssment = mnMenuPerfomAssment;
    }

    /**
     * @return the mnMenuTraining
     */
    public boolean isMnMenuTraining() {
        return mnMenuTraining;
    }

    /**
     * @param mnMenuTraining the mnMenuTraining to set
     */
    public void setMnMenuTraining(boolean mnMenuTraining) {
        this.mnMenuTraining = mnMenuTraining;
    }

    /**
     * @return the mnMenuTrainingType
     */
    public boolean isMnMenuTrainingType() {
        return mnMenuTrainingType;
    }

    /**
     * @param mnMenuTrainingType the mnMenuTrainingType to set
     */
    public void setMnMenuTrainingType(boolean mnMenuTrainingType) {
        this.mnMenuTrainingType = mnMenuTrainingType;
    }

    /**
     * @return the mnMenuTrainingVanue
     */
    public boolean isMnMenuTrainingVanue() {
        return mnMenuTrainingVanue;
    }

    /**
     * @param mnMenuTrainingVanue the mnMenuTrainingVanue to set
     */
    public void setMnMenuTrainingVanue(boolean mnMenuTrainingVanue) {
        this.mnMenuTrainingVanue = mnMenuTrainingVanue;
    }

    /**
     * @return the mnMenuTrainingProgram
     */
    public boolean isMnMenuTrainingProgram() {
        return mnMenuTrainingProgram;
    }

    /**
     * @param mnMenuTrainingProgram the mnMenuTrainingProgram to set
     */
    public void setMnMenuTrainingProgram(boolean mnMenuTrainingProgram) {
        this.mnMenuTrainingProgram = mnMenuTrainingProgram;
    }

    /**
     * @return the mnMenuTrainingPlan
     */
    public boolean isMnMenuTrainingPlan() {
        return mnMenuTrainingPlan;
    }

    /**
     * @param mnMenuTrainingPlan the mnMenuTrainingPlan to set
     */
    public void setMnMenuTrainingPlan(boolean mnMenuTrainingPlan) {
        this.mnMenuTrainingPlan = mnMenuTrainingPlan;
    }

    /**
     * @return the mnMenuTrainingActual
     */
    public boolean isMnMenuTrainingActual() {
        return mnMenuTrainingActual;
    }

    /**
     * @param mnMenuTrainingActual the mnMenuTrainingActual to set
     */
    public void setMnMenuTrainingActual(boolean mnMenuTrainingActual) {
        this.mnMenuTrainingActual = mnMenuTrainingActual;
    }

    /**
     * @return the mnMenuTrainingSearch
     */
    public boolean isMnMenuTrainingSearch() {
        return mnMenuTrainingSearch;
    }

    /**
     * @param mnMenuTrainingSearch the mnMenuTrainingSearch to set
     */
    public void setMnMenuTrainingSearch(boolean mnMenuTrainingSearch) {
        this.mnMenuTrainingSearch = mnMenuTrainingSearch;
    }

    /**
     * @return the mnMenuTrainingHistory
     */
    public boolean isMnMenuTrainingHistory() {
        return mnMenuTrainingHistory;
    }

    /**
     * @param mnMenuTrainingHistory the mnMenuTrainingHistory to set
     */
    public void setMnMenuTrainingHistory(boolean mnMenuTrainingHistory) {
        this.mnMenuTrainingHistory = mnMenuTrainingHistory;
    }

    /**
     * @return the mnMenuReport
     */
    public boolean isMnMenuReport() {
        return mnMenuReport;
    }

    /**
     * @param mnMenuReport the mnMenuReport to set
     */
    public void setMnMenuReport(boolean mnMenuReport) {
        this.mnMenuReport = mnMenuReport;
    }

    /**
     * @return the MnMenuStaffControll
     */
    public boolean isMnMenuStaffControll() {
        return MnMenuStaffControll;
    }

    /**
     * @param MnMenuStaffControll the MnMenuStaffControll to set
     */
    public void setMnMenuStaffControll(boolean MnMenuStaffControll) {
        this.MnMenuStaffControll = MnMenuStaffControll;
    }

    /**
     * @return the MnMenuAttRecord
     */
    public boolean isMnMenuAttRecord() {
        return MnMenuAttRecord;
    }

    /**
     * @param MnMenuAttRecord the MnMenuAttRecord to set
     */
    public void setMnMenuAttRecord(boolean MnMenuAttRecord) {
        this.MnMenuAttRecord = MnMenuAttRecord;
    }

    /**
     * @return the MnMenuDailyReportPresence
     */
    public boolean isMnMenuDailyReportPresence() {
        return MnMenuDailyReportPresence;
    }

    /**
     * @param MnMenuDailyReportPresence the MnMenuDailyReportPresence to set
     */
    public void setMnMenuDailyReportPresence(boolean MnMenuDailyReportPresence) {
        this.MnMenuDailyReportPresence = MnMenuDailyReportPresence;
    }

    /**
     * @return the MnMenuWeeklyReportPresence
     */
    public boolean isMnMenuWeeklyReportPresence() {
        return MnMenuWeeklyReportPresence;
    }

    /**
     * @param MnMenuWeeklyReportPresence the MnMenuWeeklyReportPresence to set
     */
    public void setMnMenuWeeklyReportPresence(boolean MnMenuWeeklyReportPresence) {
        this.MnMenuWeeklyReportPresence = MnMenuWeeklyReportPresence;
    }

    /**
     * @return the MnMenuMontlyReportPresence
     */
    public boolean isMnMenuMontlyReportPresence() {
        return MnMenuMontlyReportPresence;
    }

    /**
     * @param MnMenuMontlyReportPresence the MnMenuMontlyReportPresence to set
     */
    public void setMnMenuMontlyReportPresence(boolean MnMenuMontlyReportPresence) {
        this.MnMenuMontlyReportPresence = MnMenuMontlyReportPresence;
    }

    /**
     * @return the MnMenuYearlyReportPresence
     */
    public boolean isMnMenuYearlyReportPresence() {
        return MnMenuYearlyReportPresence;
    }

    /**
     * @param MnMenuYearlyReportPresence the MnMenuYearlyReportPresence to set
     */
    public void setMnMenuYearlyReportPresence(boolean MnMenuYearlyReportPresence) {
        this.MnMenuYearlyReportPresence = MnMenuYearlyReportPresence;
    }

    /**
     * @return the MnMenuAttdSummaryReport
     */
    public boolean isMnMenuAttdSummaryReport() {
        return MnMenuAttdSummaryReport;
    }

    /**
     * @param MnMenuAttdSummaryReport the MnMenuAttdSummaryReport to set
     */
    public void setMnMenuAttdSummaryReport(boolean MnMenuAttdSummaryReport) {
        this.MnMenuAttdSummaryReport = MnMenuAttdSummaryReport;
    }

    /**
     * @return the mnMenuLateness
     */
    public boolean isMnMenuLateness() {
        return mnMenuLateness;
    }

    /**
     * @param mnMenuLateness the mnMenuLateness to set
     */
    public void setMnMenuLateness(boolean mnMenuLateness) {
        this.mnMenuLateness = mnMenuLateness;
    }

    /**
     * @return the mnMenuLatnessDailyReport
     */
    public boolean isMnMenuLatnessDailyReport() {
        return mnMenuLatnessDailyReport;
    }

    /**
     * @param mnMenuLatnessDailyReport the mnMenuLatnessDailyReport to set
     */
    public void setMnMenuLatnessDailyReport(boolean mnMenuLatnessDailyReport) {
        this.mnMenuLatnessDailyReport = mnMenuLatnessDailyReport;
    }

    /**
     * @return the mnMenuLatnessWeeklyReport
     */
    public boolean isMnMenuLatnessWeeklyReport() {
        return mnMenuLatnessWeeklyReport;
    }

    /**
     * @param mnMenuLatnessWeeklyReport the mnMenuLatnessWeeklyReport to set
     */
    public void setMnMenuLatnessWeeklyReport(boolean mnMenuLatnessWeeklyReport) {
        this.mnMenuLatnessWeeklyReport = mnMenuLatnessWeeklyReport;
    }

    /**
     * @return the mnMenuLatnessMonthlyReport
     */
    public boolean isMnMenuLatnessMonthlyReport() {
        return mnMenuLatnessMonthlyReport;
    }

    /**
     * @param mnMenuLatnessMonthlyReport the mnMenuLatnessMonthlyReport to set
     */
    public void setMnMenuLatnessMonthlyReport(boolean mnMenuLatnessMonthlyReport) {
        this.mnMenuLatnessMonthlyReport = mnMenuLatnessMonthlyReport;
    }

    /**
     * @return the mnMenuSplitSift
     */
    public boolean isMnMenuSplitSift() {
        return mnMenuSplitSift;
    }

    /**
     * @param mnMenuSplitSift the mnMenuSplitSift to set
     */
    public void setMnMenuSplitSift(boolean mnMenuSplitSift) {
        this.mnMenuSplitSift = mnMenuSplitSift;
    }

    /**
     * @return the mnMenuSplitShiftDailyReport
     */
    public boolean isMnMenuSplitShiftDailyReport() {
        return mnMenuSplitShiftDailyReport;
    }

    /**
     * @param mnMenuSplitShiftDailyReport the mnMenuSplitShiftDailyReport to set
     */
    public void setMnMenuSplitShiftDailyReport(boolean mnMenuSplitShiftDailyReport) {
        this.mnMenuSplitShiftDailyReport = mnMenuSplitShiftDailyReport;
    }

    /**
     * @return the mnMenuSplitShiftWeeklyReport
     */
    public boolean isMnMenuSplitShiftWeeklyReport() {
        return mnMenuSplitShiftWeeklyReport;
    }

    /**
     * @param mnMenuSplitShiftWeeklyReport the mnMenuSplitShiftWeeklyReport to
     * set
     */
    public void setMnMenuSplitShiftWeeklyReport(boolean mnMenuSplitShiftWeeklyReport) {
        this.mnMenuSplitShiftWeeklyReport = mnMenuSplitShiftWeeklyReport;
    }

    /**
     * @return the mnMenuSplitShiftMonthlyReport
     */
    public boolean isMnMenuSplitShiftMonthlyReport() {
        return mnMenuSplitShiftMonthlyReport;
    }

    /**
     * @param mnMenuSplitShiftMonthlyReport the mnMenuSplitShiftMonthlyReport to
     * set
     */
    public void setMnMenuSplitShiftMonthlyReport(boolean mnMenuSplitShiftMonthlyReport) {
        this.mnMenuSplitShiftMonthlyReport = mnMenuSplitShiftMonthlyReport;
    }

    /**
     * @return the mnMenuNightShift
     */
    public boolean isMnMenuNightShift() {
        return mnMenuNightShift;
    }

    /**
     * @param mnMenuNightShift the mnMenuNightShift to set
     */
    public void setMnMenuNightShift(boolean mnMenuNightShift) {
        this.mnMenuNightShift = mnMenuNightShift;
    }

    /**
     * @return the mnMenuNightShiftDailyReport
     */
    public boolean isMnMenuNightShiftDailyReport() {
        return mnMenuNightShiftDailyReport;
    }

    /**
     * @param mnMenuNightShiftDailyReport the mnMenuNightShiftDailyReport to set
     */
    public void setMnMenuNightShiftDailyReport(boolean mnMenuNightShiftDailyReport) {
        this.mnMenuNightShiftDailyReport = mnMenuNightShiftDailyReport;
    }

    /**
     * @return the mnMenuNightShiftWeeklyReport
     */
    public boolean isMnMenuNightShiftWeeklyReport() {
        return mnMenuNightShiftWeeklyReport;
    }

    /**
     * @param mnMenuNightShiftWeeklyReport the mnMenuNightShiftWeeklyReport to
     * set
     */
    public void setMnMenuNightShiftWeeklyReport(boolean mnMenuNightShiftWeeklyReport) {
        this.mnMenuNightShiftWeeklyReport = mnMenuNightShiftWeeklyReport;
    }

    /**
     * @return the mnMenuNightShiftMonthlyReport
     */
    public boolean isMnMenuNightShiftMonthlyReport() {
        return mnMenuNightShiftMonthlyReport;
    }

    /**
     * @param mnMenuNightShiftMonthlyReport the mnMenuNightShiftMonthlyReport to
     * set
     */
    public void setMnMenuNightShiftMonthlyReport(boolean mnMenuNightShiftMonthlyReport) {
        this.mnMenuNightShiftMonthlyReport = mnMenuNightShiftMonthlyReport;
    }

    /**
     * @return the mnMenuAbsenteeism
     */
    public boolean isMnMenuAbsenteeism() {
        return mnMenuAbsenteeism;
    }

    /**
     * @param mnMenuAbsenteeism the mnMenuAbsenteeism to set
     */
    public void setMnMenuAbsenteeism(boolean mnMenuAbsenteeism) {
        this.mnMenuAbsenteeism = mnMenuAbsenteeism;
    }

    /**
     * @return the mnMenuAbsenteeismDailyReport
     */
    public boolean isMnMenuAbsenteeismDailyReport() {
        return mnMenuAbsenteeismDailyReport;
    }

    /**
     * @param mnMenuAbsenteeismDailyReport the mnMenuAbsenteeismDailyReport to
     * set
     */
    public void setMnMenuAbsenteeismDailyReport(boolean mnMenuAbsenteeismDailyReport) {
        this.mnMenuAbsenteeismDailyReport = mnMenuAbsenteeismDailyReport;
    }

    /**
     * @return the mnMenuAbsenteeismWeeklyReport
     */
    public boolean isMnMenuAbsenteeismWeeklyReport() {
        return mnMenuAbsenteeismWeeklyReport;
    }

    /**
     * @param mnMenuAbsenteeismWeeklyReport the mnMenuAbsenteeismWeeklyReport to
     * set
     */
    public void setMnMenuAbsenteeismWeeklyReport(boolean mnMenuAbsenteeismWeeklyReport) {
        this.mnMenuAbsenteeismWeeklyReport = mnMenuAbsenteeismWeeklyReport;
    }

    /**
     * @return the mnMenuAbsenteeismMonthlyReport
     */
    public boolean isMnMenuAbsenteeismMonthlyReport() {
        return mnMenuAbsenteeismMonthlyReport;
    }

    /**
     * @param mnMenuAbsenteeismMonthlyReport the mnMenuAbsenteeismMonthlyReport
     * to set
     */
    public void setMnMenuAbsenteeismMonthlyReport(boolean mnMenuAbsenteeismMonthlyReport) {
        this.mnMenuAbsenteeismMonthlyReport = mnMenuAbsenteeismMonthlyReport;
    }

    /**
     * @return the mnMenuSickness
     */
    public boolean isMnMenuSickness() {
        return mnMenuSickness;
    }

    /**
     * @param mnMenuSickness the mnMenuSickness to set
     */
    public void setMnMenuSickness(boolean mnMenuSickness) {
        this.mnMenuSickness = mnMenuSickness;
    }

    /**
     * @return the mnMenuSicknessDailyReport
     */
    public boolean isMnMenuSicknessDailyReport() {
        return mnMenuSicknessDailyReport;
    }

    /**
     * @param mnMenuSicknessDailyReport the mnMenuSicknessDailyReport to set
     */
    public void setMnMenuSicknessDailyReport(boolean mnMenuSicknessDailyReport) {
        this.mnMenuSicknessDailyReport = mnMenuSicknessDailyReport;
    }

    /**
     * @return the mnMenuSicknessWeeklyReport
     */
    public boolean isMnMenuSicknessWeeklyReport() {
        return mnMenuSicknessWeeklyReport;
    }

    /**
     * @param mnMenuSicknessWeeklyReport the mnMenuSicknessWeeklyReport to set
     */
    public void setMnMenuSicknessWeeklyReport(boolean mnMenuSicknessWeeklyReport) {
        this.mnMenuSicknessWeeklyReport = mnMenuSicknessWeeklyReport;
    }

    /**
     * @return the mnMenuSicknessMonthlyReport
     */
    public boolean isMnMenuSicknessMonthlyReport() {
        return mnMenuSicknessMonthlyReport;
    }

    /**
     * @param mnMenuSicknessMonthlyReport the mnMenuSicknessMonthlyReport to set
     */
    public void setMnMenuSicknessMonthlyReport(boolean mnMenuSicknessMonthlyReport) {
        this.mnMenuSicknessMonthlyReport = mnMenuSicknessMonthlyReport;
    }

    /**
     * @return the mnMenuSicknessZeroReport
     */
    public boolean isMnMenuSicknessZeroReport() {
        return mnMenuSicknessZeroReport;
    }

    /**
     * @param mnMenuSicknessZeroReport the mnMenuSicknessZeroReport to set
     */
    public void setMnMenuSicknessZeroReport(boolean mnMenuSicknessZeroReport) {
        this.mnMenuSicknessZeroReport = mnMenuSicknessZeroReport;
    }

    /**
     * @return the mnMenuSpcDisp
     */
    public boolean isMnMenuSpcDisp() {
        return mnMenuSpcDisp;
    }

    /**
     * @param mnMenuSpcDisp the mnMenuSpcDisp to set
     */
    public void setMnMenuSpcDisp(boolean mnMenuSpcDisp) {
        this.mnMenuSpcDisp = mnMenuSpcDisp;
    }

    /**
     * @return the mnMenuSpcDispDailyReport
     */
    public boolean isMnMenuSpcDispDailyReport() {
        return mnMenuSpcDispDailyReport;
    }

    /**
     * @param mnMenuSpcDispDailyReport the mnMenuSpcDispDailyReport to set
     */
    public void setMnMenuSpcDispDailyReport(boolean mnMenuSpcDispDailyReport) {
        this.mnMenuSpcDispDailyReport = mnMenuSpcDispDailyReport;
    }

    /**
     * @return the mnMenuSpcDispWeeklyReport
     */
    public boolean isMnMenuSpcDispWeeklyReport() {
        return mnMenuSpcDispWeeklyReport;
    }

    /**
     * @param mnMenuSpcDispWeeklyReport the mnMenuSpcDispWeeklyReport to set
     */
    public void setMnMenuSpcDispWeeklyReport(boolean mnMenuSpcDispWeeklyReport) {
        this.mnMenuSpcDispWeeklyReport = mnMenuSpcDispWeeklyReport;
    }

    /**
     * @return the mnMenuSpcDispMonthlyReport
     */
    public boolean isMnMenuSpcDispMonthlyReport() {
        return mnMenuSpcDispMonthlyReport;
    }

    /**
     * @param mnMenuSpcDispMonthlyReport the mnMenuSpcDispMonthlyReport to set
     */
    public void setMnMenuSpcDispMonthlyReport(boolean mnMenuSpcDispMonthlyReport) {
        this.mnMenuSpcDispMonthlyReport = mnMenuSpcDispMonthlyReport;
    }

    /**
     * @return the mnMenuLeaveDPSum
     */
    public boolean isMnMenuLeaveDPSum() {
        return mnMenuLeaveDPSum;
    }

    /**
     * @param mnMenuLeaveDPSum the mnMenuLeaveDPSum to set
     */
    public void setMnMenuLeaveDPSum(boolean mnMenuLeaveDPSum) {
        this.mnMenuLeaveDPSum = mnMenuLeaveDPSum;
    }

    /**
     * @return the mnMenuLeaveDPDetail
     */
    public boolean isMnMenuLeaveDPDetail() {
        return mnMenuLeaveDPDetail;
    }

    /**
     * @param mnMenuLeaveDPDetail the mnMenuLeaveDPDetail to set
     */
    public void setMnMenuLeaveDPDetail(boolean mnMenuLeaveDPDetail) {
        this.mnMenuLeaveDPDetail = mnMenuLeaveDPDetail;
    }

    /**
     * @return the mnMenuLeaveDPSumPeriod
     */
    public boolean isMnMenuLeaveDPSumPeriod() {
        return mnMenuLeaveDPSumPeriod;
    }

    /**
     * @param mnMenuLeaveDPSumPeriod the mnMenuLeaveDPSumPeriod to set
     */
    public void setMnMenuLeaveDPSumPeriod(boolean mnMenuLeaveDPSumPeriod) {
        this.mnMenuLeaveDPSumPeriod = mnMenuLeaveDPSumPeriod;
    }

    /**
     * @return the mnMenuLeaveDPDetailPeriod
     */
    public boolean isMnMenuLeaveDPDetailPeriod() {
        return mnMenuLeaveDPDetailPeriod;
    }

    /**
     * @param mnMenuLeaveDPDetailPeriod the mnMenuLeaveDPDetailPeriod to set
     */
    public void setMnMenuLeaveDPDetailPeriod(boolean mnMenuLeaveDPDetailPeriod) {
        this.mnMenuLeaveDPDetailPeriod = mnMenuLeaveDPDetailPeriod;
    }

    /**
     * @return the mnMenuSpcUnpaidLeave
     */
    public boolean isMnMenuSpcUnpaidLeave() {
        return mnMenuSpcUnpaidLeave;
    }

    /**
     * @param mnMenuSpcUnpaidLeave the mnMenuSpcUnpaidLeave to set
     */
    public void setMnMenuSpcUnpaidLeave(boolean mnMenuSpcUnpaidLeave) {
        this.mnMenuSpcUnpaidLeave = mnMenuSpcUnpaidLeave;
    }

    /**
     * @return the mnMenuDPExpired
     */
    public boolean isMnMenuDPExpired() {
        return mnMenuDPExpired;
    }

    /**
     * @param mnMenuDPExpired the mnMenuDPExpired to set
     */
    public void setMnMenuDPExpired(boolean mnMenuDPExpired) {
        this.mnMenuDPExpired = mnMenuDPExpired;
    }

    /**
     * @return the mnMenuReportTrainee
     */
    public boolean isMnMenuReportTrainee() {
        return mnMenuReportTrainee;
    }

    /**
     * @param mnMenuReportTrainee the mnMenuReportTrainee to set
     */
    public void setMnMenuReportTrainee(boolean mnMenuReportTrainee) {
        this.mnMenuReportTrainee = mnMenuReportTrainee;
    }

    /**
     * @return the mnMenuTraineeMonthlyReport
     */
    public boolean isMnMenuTraineeMonthlyReport() {
        return mnMenuTraineeMonthlyReport;
    }

    /**
     * @param mnMenuTraineeMonthlyReport the mnMenuTraineeMonthlyReport to set
     */
    public void setMnMenuTraineeMonthlyReport(boolean mnMenuTraineeMonthlyReport) {
        this.mnMenuTraineeMonthlyReport = mnMenuTraineeMonthlyReport;
    }

    /**
     * @return the mnMenuTraineeEndPeriodReport
     */
    public boolean isMnMenuTraineeEndPeriodReport() {
        return mnMenuTraineeEndPeriodReport;
    }

    /**
     * @param mnMenuTraineeEndPeriodReport the mnMenuTraineeEndPeriodReport to
     * set
     */
    public void setMnMenuTraineeEndPeriodReport(boolean mnMenuTraineeEndPeriodReport) {
        this.mnMenuTraineeEndPeriodReport = mnMenuTraineeEndPeriodReport;
    }

    /**
     * @return the mnMenuListEmpCategory
     */
    public boolean isMnMenuListEmpCategory() {
        return mnMenuListEmpCategory;
    }

    /**
     * @param mnMenuListEmpCategory the mnMenuListEmpCategory to set
     */
    public void setMnMenuListEmpCategory(boolean mnMenuListEmpCategory) {
        this.mnMenuListEmpCategory = mnMenuListEmpCategory;
    }

    /**
     * @return the mnMenuListEmpResign
     */
    public boolean isMnMenuListEmpResign() {
        return mnMenuListEmpResign;
    }

    /**
     * @param mnMenuListEmpResign the mnMenuListEmpResign to set
     */
    public void setMnMenuListEmpResign(boolean mnMenuListEmpResign) {
        this.mnMenuListEmpResign = mnMenuListEmpResign;
    }

    /**
     * @return the mnMenuListEmpEducation
     */
    public boolean isMnMenuListEmpEducation() {
        return mnMenuListEmpEducation;
    }

    /**
     * @param mnMenuListEmpEducation the mnMenuListEmpEducation to set
     */
    public void setMnMenuListEmpEducation(boolean mnMenuListEmpEducation) {
        this.mnMenuListEmpEducation = mnMenuListEmpEducation;
    }

    /**
     * @return the mnMenuListEmpCatByName
     */
    public boolean isMnMenuListEmpCatByName() {
        return mnMenuListEmpCatByName;
    }

    /**
     * @param mnMenuListEmpCatByName the mnMenuListEmpCatByName to set
     */
    public void setMnMenuListEmpCatByName(boolean mnMenuListEmpCatByName) {
        this.mnMenuListEmpCatByName = mnMenuListEmpCatByName;
    }

    /**
     * @return the mnMenuListNumbAbs
     */
    public boolean isMnMenuListNumbAbs() {
        return mnMenuListNumbAbs;
    }

    /**
     * @param mnMenuListNumbAbs the mnMenuListNumbAbs to set
     */
    public void setMnMenuListNumbAbs(boolean mnMenuListNumbAbs) {
        this.mnMenuListNumbAbs = mnMenuListNumbAbs;
    }

    /**
     * @return the mnMenuListEmpRace
     */
    public boolean isMnMenuListEmpRace() {
        return mnMenuListEmpRace;
    }

    /**
     * @param mnMenuListEmpRace the mnMenuListEmpRace to set
     */
    public void setMnMenuListEmpRace(boolean mnMenuListEmpRace) {
        this.mnMenuListEmpRace = mnMenuListEmpRace;
    }

    /**
     * @return the mnMenuMonthTraining
     */
    public boolean isMnMenuMonthTraining() {
        return mnMenuMonthTraining;
    }

    /**
     * @param mnMenuMonthTraining the mnMenuMonthTraining to set
     */
    public void setMnMenuMonthTraining(boolean mnMenuMonthTraining) {
        this.mnMenuMonthTraining = mnMenuMonthTraining;
    }

    /**
     * @return the mnMenuTrainingProfile
     */
    public boolean isMnMenuTrainingProfile() {
        return mnMenuTrainingProfile;
    }

    /**
     * @param mnMenuTrainingProfile the mnMenuTrainingProfile to set
     */
    public void setMnMenuTrainingProfile(boolean mnMenuTrainingProfile) {
        this.mnMenuTrainingProfile = mnMenuTrainingProfile;
    }

    /**
     * @return the mnMenuTrainingTarget
     */
    public boolean isMnMenuTrainingTarget() {
        return mnMenuTrainingTarget;
    }

    /**
     * @param mnMenuTrainingTarget the mnMenuTrainingTarget to set
     */
    public void setMnMenuTrainingTarget(boolean mnMenuTrainingTarget) {
        this.mnMenuTrainingTarget = mnMenuTrainingTarget;
    }

    /**
     * @return the mnMenuTrainingRptByDpt
     */
    public boolean isMnMenuTrainingRptByDpt() {
        return mnMenuTrainingRptByDpt;
    }

    /**
     * @param mnMenuTrainingRptByDpt the mnMenuTrainingRptByDpt to set
     */
    public void setMnMenuTrainingRptByDpt(boolean mnMenuTrainingRptByDpt) {
        this.mnMenuTrainingRptByDpt = mnMenuTrainingRptByDpt;
    }

    /**
     * @return the mnMenuTrainingRptByCourseDetail
     */
    public boolean isMnMenuTrainingRptByCourseDetail() {
        return mnMenuTrainingRptByCourseDetail;
    }

    /**
     * @param mnMenuTrainingRptByCourseDetail the
     * mnMenuTrainingRptByCourseDetail to set
     */
    public void setMnMenuTrainingRptByCourseDetail(boolean mnMenuTrainingRptByCourseDetail) {
        this.mnMenuTrainingRptByCourseDetail = mnMenuTrainingRptByCourseDetail;
    }

    /**
     * @return the mnMenuTrainingRptByEmp
     */
    public boolean isMnMenuTrainingRptByEmp() {
        return mnMenuTrainingRptByEmp;
    }

    /**
     * @param mnMenuTrainingRptByEmp the mnMenuTrainingRptByEmp to set
     */
    public void setMnMenuTrainingRptByEmp(boolean mnMenuTrainingRptByEmp) {
        this.mnMenuTrainingRptByEmp = mnMenuTrainingRptByEmp;
    }

    /**
     * @return the mnMenuTrainingRptByTrainer
     */
    public boolean isMnMenuTrainingRptByTrainer() {
        return mnMenuTrainingRptByTrainer;
    }

    /**
     * @param mnMenuTrainingRptByTrainer the mnMenuTrainingRptByTrainer to set
     */
    public void setMnMenuTrainingRptByTrainer(boolean mnMenuTrainingRptByTrainer) {
        this.mnMenuTrainingRptByTrainer = mnMenuTrainingRptByTrainer;
    }

    /**
     * @return the mnMenuTrainingRptByCourseDate
     */
    public boolean isMnMenuTrainingRptByCourseDate() {
        return mnMenuTrainingRptByCourseDate;
    }

    /**
     * @param mnMenuTrainingRptByCourseDate the mnMenuTrainingRptByCourseDate to
     * set
     */
    public void setMnMenuTrainingRptByCourseDate(boolean mnMenuTrainingRptByCourseDate) {
        this.mnMenuTrainingRptByCourseDate = mnMenuTrainingRptByCourseDate;
    }

    /**
     * @return the mnMenuReportPayroll
     */
    public boolean isMnMenuReportPayroll() {
        return mnMenuReportPayroll;
    }

    /**
     * @param mnMenuReportPayroll the mnMenuReportPayroll to set
     */
    public void setMnMenuReportPayroll(boolean mnMenuReportPayroll) {
        this.mnMenuReportPayroll = mnMenuReportPayroll;
    }

    /**
     * @return the mnMenuPayrollListOfSalary
     */
    public boolean isMnMenuPayrollListOfSalary() {
        return mnMenuPayrollListOfSalary;
    }

    /**
     * @param mnMenuPayrollListOfSalary the mnMenuPayrollListOfSalary to set
     */
    public void setMnMenuPayrollListOfSalary(boolean mnMenuPayrollListOfSalary) {
        this.mnMenuPayrollListOfSalary = mnMenuPayrollListOfSalary;
    }

    /**
     * @return the mnMenuCanteenn
     */
    public boolean isMnMenuCanteenn() {
        return mnMenuCanteenn;
    }

    /**
     * @param mnMenuCanteenn the mnMenuCanteenn to set
     */
    public void setMnMenuCanteenn(boolean mnMenuCanteenn) {
        this.mnMenuCanteenn = mnMenuCanteenn;
    }

    /**
     * @return the mnMenuData
     */
    public boolean isMnMenuData() {
        return mnMenuData;
    }

    /**
     * @param mnMenuData the mnMenuData to set
     */
    public void setMnMenuData(boolean mnMenuData) {
        this.mnMenuData = mnMenuData;
    }

    /**
     * @return the mnMenuManualVisitation
     */
    public boolean isMnMenuManualVisitation() {
        return mnMenuManualVisitation;
    }

    /**
     * @param mnMenuManualVisitation the mnMenuManualVisitation to set
     */
    public void setMnMenuManualVisitation(boolean mnMenuManualVisitation) {
        this.mnMenuManualVisitation = mnMenuManualVisitation;
    }

    /**
     * @return the mnMenuCanteenSchedule
     */
    public boolean isMnMenuCanteenSchedule() {
        return mnMenuCanteenSchedule;
    }

    /**
     * @param mnMenuCanteenSchedule the mnMenuCanteenSchedule to set
     */
    public void setMnMenuCanteenSchedule(boolean mnMenuCanteenSchedule) {
        this.mnMenuCanteenSchedule = mnMenuCanteenSchedule;
    }

    /**
     * @return the mnMenuCanteen
     */
    public boolean isMnMenuCanteen() {
        return mnMenuCanteen;
    }

    /**
     * @param mnMenuCanteen the mnMenuCanteen to set
     */
    public void setMnMenuCanteen(boolean mnMenuCanteen) {
        this.mnMenuCanteen = mnMenuCanteen;
    }

    /**
     * @return the mnMenuChecklistGroup
     */
    public boolean isMnMenuChecklistGroup() {
        return mnMenuChecklistGroup;
    }

    /**
     * @param mnMenuChecklistGroup the mnMenuChecklistGroup to set
     */
    public void setMnMenuChecklistGroup(boolean mnMenuChecklistGroup) {
        this.mnMenuChecklistGroup = mnMenuChecklistGroup;
    }

    /**
     * @return the mnMenuChecklistItem
     */
    public boolean isMnMenuChecklistItem() {
        return mnMenuChecklistItem;
    }

    /**
     * @param mnMenuChecklistItem the mnMenuChecklistItem to set
     */
    public void setMnMenuChecklistItem(boolean mnMenuChecklistItem) {
        this.mnMenuChecklistItem = mnMenuChecklistItem;
    }

    /**
     * @return the mnMenuChecklistMark
     */
    public boolean isMnMenuChecklistMark() {
        return mnMenuChecklistMark;
    }

    /**
     * @param mnMenuChecklistMark the mnMenuChecklistMark to set
     */
    public void setMnMenuChecklistMark(boolean mnMenuChecklistMark) {
        this.mnMenuChecklistMark = mnMenuChecklistMark;
    }

    /**
     * @return the mnMenuMenuItem
     */
    public boolean isMnMenuMenuItem() {
        return mnMenuMenuItem;
    }

    /**
     * @param mnMenuMenuItem the mnMenuMenuItem to set
     */
    public void setMnMenuMenuItem(boolean mnMenuMenuItem) {
        this.mnMenuMenuItem = mnMenuMenuItem;
    }

    /**
     * @return the mnMenuMealTime
     */
    public boolean isMnMenuMealTime() {
        return mnMenuMealTime;
    }

    /**
     * @param mnMenuMealTime the mnMenuMealTime to set
     */
    public void setMnMenuMealTime(boolean mnMenuMealTime) {
        this.mnMenuMealTime = mnMenuMealTime;
    }

    /**
     * @return the mnMenuCommentGroup
     */
    public boolean isMnMenuCommentGroup() {
        return mnMenuCommentGroup;
    }

    /**
     * @param mnMenuCommentGroup the mnMenuCommentGroup to set
     */
    public void setMnMenuCommentGroup(boolean mnMenuCommentGroup) {
        this.mnMenuCommentGroup = mnMenuCommentGroup;
    }

    /**
     * @return the mnMenuCommentQuestion
     */
    public boolean isMnMenuCommentQuestion() {
        return mnMenuCommentQuestion;
    }

    /**
     * @param mnMenuCommentQuestion the mnMenuCommentQuestion to set
     */
    public void setMnMenuCommentQuestion(boolean mnMenuCommentQuestion) {
        this.mnMenuCommentQuestion = mnMenuCommentQuestion;
    }

    /**
     * @return the mnMenuReports
     */
    public boolean isMnMenuReports() {
        return mnMenuReports;
    }

    /**
     * @param mnMenuReports the mnMenuReports to set
     */
    public void setMnMenuReports(boolean mnMenuReports) {
        this.mnMenuReports = mnMenuReports;
    }

    /**
     * @return the mnMenuDetail
     */
    public boolean isMnMenuDetail() {
        return mnMenuDetail;
    }

    /**
     * @param mnMenuDetail the mnMenuDetail to set
     */
    public void setMnMenuDetail(boolean mnMenuDetail) {
        this.mnMenuDetail = mnMenuDetail;
    }

    /**
     * @return the mnMenuDailyReport
     */
    public boolean isMnMenuDailyReport() {
        return mnMenuDailyReport;
    }

    /**
     * @param mnMenuDailyReport the mnMenuDailyReport to set
     */
    public void setMnMenuDailyReport(boolean mnMenuDailyReport) {
        this.mnMenuDailyReport = mnMenuDailyReport;
    }

    /**
     * @return the mnMenuWeeklyReport
     */
    public boolean isMnMenuWeeklyReport() {
        return mnMenuWeeklyReport;
    }

    /**
     * @param mnMenuWeeklyReport the mnMenuWeeklyReport to set
     */
    public void setMnMenuWeeklyReport(boolean mnMenuWeeklyReport) {
        this.mnMenuWeeklyReport = mnMenuWeeklyReport;
    }

    /**
     * @return the mnMenuMonthlyReport
     */
    public boolean isMnMenuMonthlyReport() {
        return mnMenuMonthlyReport;
    }

    /**
     * @param mnMenuMonthlyReport the mnMenuMonthlyReport to set
     */
    public void setMnMenuMonthlyReport(boolean mnMenuMonthlyReport) {
        this.mnMenuMonthlyReport = mnMenuMonthlyReport;
    }

    /**
     * @return the mnMenuSummary
     */
    public boolean isMnMenuSummary() {
        return mnMenuSummary;
    }

    /**
     * @param mnMenuSummary the mnMenuSummary to set
     */
    public void setMnMenuSummary(boolean mnMenuSummary) {
        this.mnMenuSummary = mnMenuSummary;
    }

    /**
     * @return the mnMenuDailyMealRecord
     */
    public boolean isMnMenuDailyMealRecord() {
        return mnMenuDailyMealRecord;
    }

    /**
     * @param mnMenuDailyMealRecord the mnMenuDailyMealRecord to set
     */
    public void setMnMenuDailyMealRecord(boolean mnMenuDailyMealRecord) {
        this.mnMenuDailyMealRecord = mnMenuDailyMealRecord;
    }

    /**
     * @return the mnMenuPeriodicMealRecord
     */
    public boolean isMnMenuPeriodicMealRecord() {
        return mnMenuPeriodicMealRecord;
    }

    /**
     * @param mnMenuPeriodicMealRecord the mnMenuPeriodicMealRecord to set
     */
    public void setMnMenuPeriodicMealRecord(boolean mnMenuPeriodicMealRecord) {
        this.mnMenuPeriodicMealRecord = mnMenuPeriodicMealRecord;
    }

    /**
     * @return the mnMenuMealReports
     */
    public boolean isMnMenuMealReports() {
        return mnMenuMealReports;
    }

    /**
     * @param mnMenuMealReports the mnMenuMealReports to set
     */
    public void setMnMenuMealReports(boolean mnMenuMealReports) {
        this.mnMenuMealReports = mnMenuMealReports;
    }

    /**
     * @return the mnMenuMealReportsDepartement
     */
    public boolean isMnMenuMealReportsDepartement() {
        return mnMenuMealReportsDepartement;
    }

    /**
     * @param mnMenuMealReportsDepartement the mnMenuMealReportsDepartement to
     * set
     */
    public void setMnMenuMealReportsDepartement(boolean mnMenuMealReportsDepartement) {
        this.mnMenuMealReportsDepartement = mnMenuMealReportsDepartement;
    }

    /**
     * @return the mnMenuMounthlyCanteenReport
     */
    public boolean isMnMenuMounthlyCanteenReport() {
        return mnMenuMounthlyCanteenReport;
    }

    /**
     * @param mnMenuMounthlyCanteenReport the mnMenuMounthlyCanteenReport to set
     */
    public void setMnMenuMounthlyCanteenReport(boolean mnMenuMounthlyCanteenReport) {
        this.mnMenuMounthlyCanteenReport = mnMenuMounthlyCanteenReport;
    }

    /**
     * @return the mnMenuClinic
     */
    public boolean isMnMenuClinic() {
        return mnMenuClinic;
    }

    /**
     * @param mnMenuClinic the mnMenuClinic to set
     */
    public void setMnMenuClinic(boolean mnMenuClinic) {
        this.mnMenuClinic = mnMenuClinic;
    }

    /**
     * @return the mnMenuEmployeeFamily
     */
    public boolean isMnMenuEmployeeFamily() {
        return mnMenuEmployeeFamily;
    }

    /**
     * @param mnMenuEmployeeFamily the mnMenuEmployeeFamily to set
     */
    public void setMnMenuEmployeeFamily(boolean mnMenuEmployeeFamily) {
        this.mnMenuEmployeeFamily = mnMenuEmployeeFamily;
    }

    /**
     * @return the mnMenuMedicalRecord
     */
    public boolean isMnMenuMedicalRecord() {
        return mnMenuMedicalRecord;
    }

    /**
     * @param mnMenuMedicalRecord the mnMenuMedicalRecord to set
     */
    public void setMnMenuMedicalRecord(boolean mnMenuMedicalRecord) {
        this.mnMenuMedicalRecord = mnMenuMedicalRecord;
    }

    /**
     * @return the mnMenuEmployeeVisit
     */
    public boolean isMnMenuEmployeeVisit() {
        return mnMenuEmployeeVisit;
    }

    /**
     * @param mnMenuEmployeeVisit the mnMenuEmployeeVisit to set
     */
    public void setMnMenuEmployeeVisit(boolean mnMenuEmployeeVisit) {
        this.mnMenuEmployeeVisit = mnMenuEmployeeVisit;
    }

    /**
     * @return the mnMenuGuestHandling
     */
    public boolean isMnMenuGuestHandling() {
        return mnMenuGuestHandling;
    }

    /**
     * @param mnMenuGuestHandling the mnMenuGuestHandling to set
     */
    public void setMnMenuGuestHandling(boolean mnMenuGuestHandling) {
        this.mnMenuGuestHandling = mnMenuGuestHandling;
    }

    /**
     * @return the mnMenuMedicine
     */
    public boolean isMnMenuMedicine() {
        return mnMenuMedicine;
    }

    /**
     * @param mnMenuMedicine the mnMenuMedicine to set
     */
    public void setMnMenuMedicine(boolean mnMenuMedicine) {
        this.mnMenuMedicine = mnMenuMedicine;
    }

    /**
     * @return the mnMenuListOfMedicine
     */
    public boolean isMnMenuListOfMedicine() {
        return mnMenuListOfMedicine;
    }

    /**
     * @param mnMenuListOfMedicine the mnMenuListOfMedicine to set
     */
    public void setMnMenuListOfMedicine(boolean mnMenuListOfMedicine) {
        this.mnMenuListOfMedicine = mnMenuListOfMedicine;
    }

    /**
     * @return the mnMenuMedicineConsumpsition
     */
    public boolean isMnMenuMedicineConsumpsition() {
        return mnMenuMedicineConsumpsition;
    }

    /**
     * @param mnMenuMedicineConsumpsition the mnMenuMedicineConsumpsition to set
     */
    public void setMnMenuMedicineConsumpsition(boolean mnMenuMedicineConsumpsition) {
        this.mnMenuMedicineConsumpsition = mnMenuMedicineConsumpsition;
    }

    /**
     * @return the mnMenuDisease
     */
    public boolean isMnMenuDisease() {
        return mnMenuDisease;
    }

    /**
     * @param mnMenuDisease the mnMenuDisease to set
     */
    public void setMnMenuDisease(boolean mnMenuDisease) {
        this.mnMenuDisease = mnMenuDisease;
    }

    /**
     * @return the mnMenuDiseaseType
     */
    public boolean isMnMenuDiseaseType() {
        return mnMenuDiseaseType;
    }

    /**
     * @param mnMenuDiseaseType the mnMenuDiseaseType to set
     */
    public void setMnMenuDiseaseType(boolean mnMenuDiseaseType) {
        this.mnMenuDiseaseType = mnMenuDiseaseType;
    }

    /**
     * @return the mnMenuMedical
     */
    public boolean isMnMenuMedical() {
        return mnMenuMedical;
    }

    /**
     * @param mnMenuMedical the mnMenuMedical to set
     */
    public void setMnMenuMedical(boolean mnMenuMedical) {
        this.mnMenuMedical = mnMenuMedical;
    }

    /**
     * @return the mnMenuMedicalLevel
     */
    public boolean isMnMenuMedicalLevel() {
        return mnMenuMedicalLevel;
    }

    /**
     * @param mnMenuMedicalLevel the mnMenuMedicalLevel to set
     */
    public void setMnMenuMedicalLevel(boolean mnMenuMedicalLevel) {
        this.mnMenuMedicalLevel = mnMenuMedicalLevel;
    }

    /**
     * @return the mnMenuMedicalCase
     */
    public boolean isMnMenuMedicalCase() {
        return mnMenuMedicalCase;
    }

    /**
     * @param mnMenuMedicalCase the mnMenuMedicalCase to set
     */
    public void setMnMenuMedicalCase(boolean mnMenuMedicalCase) {
        this.mnMenuMedicalCase = mnMenuMedicalCase;
    }

    /**
     * @return the mnMenuMedicalBudget
     */
    public boolean isMnMenuMedicalBudget() {
        return mnMenuMedicalBudget;
    }

    /**
     * @param mnMenuMedicalBudget the mnMenuMedicalBudget to set
     */
    public void setMnMenuMedicalBudget(boolean mnMenuMedicalBudget) {
        this.mnMenuMedicalBudget = mnMenuMedicalBudget;
    }

    /**
     * @return the mnMenuGroup
     */
    public boolean isMnMenuGroup() {
        return mnMenuGroup;
    }

    /**
     * @param mnMenuGroup the mnMenuGroup to set
     */
    public void setMnMenuGroup(boolean mnMenuGroup) {
        this.mnMenuGroup = mnMenuGroup;
    }

    /**
     * @return the mnMenuTypee
     */
    public boolean isMnMenuTypee() {
        return mnMenuTypee;
    }

    /**
     * @param mnMenuTypee the mnMenuTypee to set
     */
    public void setMnMenuTypee(boolean mnMenuTypee) {
        this.mnMenuTypee = mnMenuTypee;
    }

    /**
     * @return the mnMenuLocker
     */
    public boolean isMnMenuLocker() {
        return mnMenuLocker;
    }

    /**
     * @param mnMenuLocker the mnMenuLocker to set
     */
    public void setMnMenuLocker(boolean mnMenuLocker) {
        this.mnMenuLocker = mnMenuLocker;
    }

    /**
     * @return the mnMenuLockerTreatment
     */
    public boolean isMnMenuLockerTreatment() {
        return mnMenuLockerTreatment;
    }

    /**
     * @param mnMenuLockerTreatment the mnMenuLockerTreatment to set
     */
    public void setMnMenuLockerTreatment(boolean mnMenuLockerTreatment) {
        this.mnMenuLockerTreatment = mnMenuLockerTreatment;
    }

    /**
     * @return the mnMenuLockerr
     */
    public boolean isMnMenuLockerr() {
        return mnMenuLockerr;
    }

    /**
     * @param mnMenuLockerr the mnMenuLockerr to set
     */
    public void setMnMenuLockerr(boolean mnMenuLockerr) {
        this.mnMenuLockerr = mnMenuLockerr;
    }

    /**
     * @return the mnMenuMasterData
     */
    public boolean isMnMenuMasterData() {
        return mnMenuMasterData;
    }

    /**
     * @param mnMenuMasterData the mnMenuMasterData to set
     */
    public void setMnMenuMasterData(boolean mnMenuMasterData) {
        this.mnMenuMasterData = mnMenuMasterData;
    }

    /**
     * @return the mnMenuCompany
     */
    public boolean isMnMenuCompany() {
        return mnMenuCompany;
    }

    /**
     * @param mnMenuCompany the mnMenuCompany to set
     */
    public void setMnMenuCompany(boolean mnMenuCompany) {
        this.mnMenuCompany = mnMenuCompany;
    }

    /**
     * @return the mnMenuDivision
     */
    public boolean isMnMenuDivision() {
        return mnMenuDivision;
    }

    /**
     * @param mnMenuDivision the mnMenuDivision to set
     */
    public void setMnMenuDivision(boolean mnMenuDivision) {
        this.mnMenuDivision = mnMenuDivision;
    }

    /**
     * @return the mnMenuDepartement
     */
    public boolean isMnMenuDepartement() {
        return mnMenuDepartement;
    }

    /**
     * @param mnMenuDepartement the mnMenuDepartement to set
     */
    public void setMnMenuDepartement(boolean mnMenuDepartement) {
        this.mnMenuDepartement = mnMenuDepartement;
    }

    /**
     * @return the mnMenuPosition
     */
    public boolean isMnMenuPosition() {
        return mnMenuPosition;
    }

    /**
     * @param mnMenuPosition the mnMenuPosition to set
     */
    public void setMnMenuPosition(boolean mnMenuPosition) {
        this.mnMenuPosition = mnMenuPosition;
    }

    /**
     * @return the mnMenuSection
     */
    public boolean isMnMenuSection() {
        return mnMenuSection;
    }

    /**
     * @param mnMenuSection the mnMenuSection to set
     */
    public void setMnMenuSection(boolean mnMenuSection) {
        this.mnMenuSection = mnMenuSection;
    }

    /**
     * @return the mnMenuPublicHoliday
     */
    public boolean isMnMenuPublicHoliday() {
        return mnMenuPublicHoliday;
    }

    /**
     * @param mnMenuPublicHoliday the mnMenuPublicHoliday to set
     */
    public void setMnMenuPublicHoliday(boolean mnMenuPublicHoliday) {
        this.mnMenuPublicHoliday = mnMenuPublicHoliday;
    }

    /**
     * @return the mnMenuLeaveTarget
     */
    public boolean isMnMenuLeaveTarget() {
        return mnMenuLeaveTarget;
    }

    /**
     * @param mnMenuLeaveTarget the mnMenuLeaveTarget to set
     */
    public void setMnMenuLeaveTarget(boolean mnMenuLeaveTarget) {
        this.mnMenuLeaveTarget = mnMenuLeaveTarget;
    }

    /**
     * @return the mnMenuEducation
     */
    public boolean isMnMenuEducation() {
        return mnMenuEducation;
    }

    /**
     * @param mnMenuEducation the mnMenuEducation to set
     */
    public void setMnMenuEducation(boolean mnMenuEducation) {
        this.mnMenuEducation = mnMenuEducation;
    }

    /**
     * @return the mnMenuFamilyRelation
     */
    public boolean isMnMenuFamilyRelation() {
        return mnMenuFamilyRelation;
    }

    /**
     * @param mnMenuFamilyRelation the mnMenuFamilyRelation to set
     */
    public void setMnMenuFamilyRelation(boolean mnMenuFamilyRelation) {
        this.mnMenuFamilyRelation = mnMenuFamilyRelation;
    }

    /**
     * @return the mnMenuWarningg
     */
    public boolean isMnMenuWarningg() {
        return mnMenuWarningg;
    }

    /**
     * @param mnMenuWarningg the mnMenuWarningg to set
     */
    public void setMnMenuWarningg(boolean mnMenuWarningg) {
        this.mnMenuWarningg = mnMenuWarningg;
    }

    /**
     * @return the mnMenuReprimand
     */
    public boolean isMnMenuReprimand() {
        return mnMenuReprimand;
    }

    /**
     * @param mnMenuReprimand the mnMenuReprimand to set
     */
    public void setMnMenuReprimand(boolean mnMenuReprimand) {
        this.mnMenuReprimand = mnMenuReprimand;
    }

    /**
     * @return the mnMenuLevel
     */
    public boolean isMnMenuLevel() {
        return mnMenuLevel;
    }

    /**
     * @param mnMenuLevel the mnMenuLevel to set
     */
    public void setMnMenuLevel(boolean mnMenuLevel) {
        this.mnMenuLevel = mnMenuLevel;
    }

    /**
     * @return the mnMenuCategory
     */
    public boolean isMnMenuCategory() {
        return mnMenuCategory;
    }

    /**
     * @param mnMenuCategory the mnMenuCategory to set
     */
    public void setMnMenuCategory(boolean mnMenuCategory) {
        this.mnMenuCategory = mnMenuCategory;
    }

    /**
     * @return the mnMenuReligion
     */
    public boolean isMnMenuReligion() {
        return mnMenuReligion;
    }

    /**
     * @param mnMenuReligion the mnMenuReligion to set
     */
    public void setMnMenuReligion(boolean mnMenuReligion) {
        this.mnMenuReligion = mnMenuReligion;
    }

    /**
     * @return the mnMenuMarital
     */
    public boolean isMnMenuMarital() {
        return mnMenuMarital;
    }

    /**
     * @param mnMenuMarital the mnMenuMarital to set
     */
    public void setMnMenuMarital(boolean mnMenuMarital) {
        this.mnMenuMarital = mnMenuMarital;
    }

    /**
     * @return the mnMenuRace
     */
    public boolean isMnMenuRace() {
        return mnMenuRace;
    }

    /**
     * @param mnMenuRace the mnMenuRace to set
     */
    public void setMnMenuRace(boolean mnMenuRace) {
        this.mnMenuRace = mnMenuRace;
    }

    /**
     * @return the mnMenuLanguage
     */
    public boolean isMnMenuLanguage() {
        return mnMenuLanguage;
    }

    /**
     * @param mnMenuLanguage the mnMenuLanguage to set
     */
    public void setMnMenuLanguage(boolean mnMenuLanguage) {
        this.mnMenuLanguage = mnMenuLanguage;
    }

    /**
     * @return the mnMenuImageAssign
     */
    public boolean isMnMenuImageAssign() {
        return mnMenuImageAssign;
    }

    /**
     * @param mnMenuImageAssign the mnMenuImageAssign to set
     */
    public void setMnMenuImageAssign(boolean mnMenuImageAssign) {
        this.mnMenuImageAssign = mnMenuImageAssign;
    }

    /**
     * @return the mnMenuResigneReason
     */
    public boolean isMnMenuResigneReason() {
        return mnMenuResigneReason;
    }

    /**
     * @param mnMenuResigneReason the mnMenuResigneReason to set
     */
    public void setMnMenuResigneReason(boolean mnMenuResigneReason) {
        this.mnMenuResigneReason = mnMenuResigneReason;
    }

    /**
     * @return the mnMenuAwardType
     */
    public boolean isMnMenuAwardType() {
        return mnMenuAwardType;
    }

    /**
     * @param mnMenuAwardType the mnMenuAwardType to set
     */
    public void setMnMenuAwardType(boolean mnMenuAwardType) {
        this.mnMenuAwardType = mnMenuAwardType;
    }

    /**
     * @return the mnMenuAbsenanceReason
     */
    public boolean isMnMenuAbsenanceReason() {
        return mnMenuAbsenanceReason;
    }

    /**
     * @param mnMenuAbsenanceReason the mnMenuAbsenanceReason to set
     */
    public void setMnMenuAbsenanceReason(boolean mnMenuAbsenanceReason) {
        this.mnMenuAbsenanceReason = mnMenuAbsenanceReason;
    }

    /**
     * @return the mnMenuPeriode
     */
    public boolean isMnMenuPeriode() {
        return mnMenuPeriode;
    }

    /**
     * @param mnMenuPeriode the mnMenuPeriode to set
     */
    public void setMnMenuPeriode(boolean mnMenuPeriode) {
        this.mnMenuPeriode = mnMenuPeriode;
    }

    /**
     * @return the mnMenuCategoryy
     */
    public boolean isMnMenuCategoryy() {
        return mnMenuCategoryy;
    }

    /**
     * @param mnMenuCategoryy the mnMenuCategoryy to set
     */
    public void setMnMenuCategoryy(boolean mnMenuCategoryy) {
        this.mnMenuCategoryy = mnMenuCategoryy;
    }

    /**
     * @return the mnMenuSymbol
     */
    public boolean isMnMenuSymbol() {
        return mnMenuSymbol;
    }

    /**
     * @param mnMenuSymbol the mnMenuSymbol to set
     */
    public void setMnMenuSymbol(boolean mnMenuSymbol) {
        this.mnMenuSymbol = mnMenuSymbol;
    }

    /**
     * @return the mnMenuLockerLocation
     */
    public boolean isMnMenuLockerLocation() {
        return mnMenuLockerLocation;
    }

    /**
     * @param mnMenuLockerLocation the mnMenuLockerLocation to set
     */
    public void setMnMenuLockerLocation(boolean mnMenuLockerLocation) {
        this.mnMenuLockerLocation = mnMenuLockerLocation;
    }

    /**
     * @return the mnMenuLockerCondition
     */
    public boolean isMnMenuLockerCondition() {
        return mnMenuLockerCondition;
    }

    /**
     * @param mnMenuLockerCondition the mnMenuLockerCondition to set
     */
    public void setMnMenuLockerCondition(boolean mnMenuLockerCondition) {
        this.mnMenuLockerCondition = mnMenuLockerCondition;
    }

    /**
     * @return the mnMenuGroupRank
     */
    public boolean isMnMenuGroupRank() {
        return mnMenuGroupRank;
    }

    /**
     * @param mnMenuGroupRank the mnMenuGroupRank to set
     */
    public void setMnMenuGroupRank(boolean mnMenuGroupRank) {
        this.mnMenuGroupRank = mnMenuGroupRank;
    }

    /**
     * @return the mnMenuEvaluationCriteria
     */
    public boolean isMnMenuEvaluationCriteria() {
        return mnMenuEvaluationCriteria;
    }

    /**
     * @param mnMenuEvaluationCriteria the mnMenuEvaluationCriteria to set
     */
    public void setMnMenuEvaluationCriteria(boolean mnMenuEvaluationCriteria) {
        this.mnMenuEvaluationCriteria = mnMenuEvaluationCriteria;
    }

    /**
     * @return the mnMenuFormCreator
     */
    public boolean isMnMenuFormCreator() {
        return mnMenuFormCreator;
    }

    /**
     * @param mnMenuFormCreator the mnMenuFormCreator to set
     */
    public void setMnMenuFormCreator(boolean mnMenuFormCreator) {
        this.mnMenuFormCreator = mnMenuFormCreator;
    }

    /**
     * @return the mnMenuGeneralQuestion
     */
    public boolean isMnMenuGeneralQuestion() {
        return mnMenuGeneralQuestion;
    }

    /**
     * @param mnMenuGeneralQuestion the mnMenuGeneralQuestion to set
     */
    public void setMnMenuGeneralQuestion(boolean mnMenuGeneralQuestion) {
        this.mnMenuGeneralQuestion = mnMenuGeneralQuestion;
    }

    /**
     * @return the mnMenuIllnesType
     */
    public boolean isMnMenuIllnesType() {
        return mnMenuIllnesType;
    }

    /**
     * @param mnMenuIllnesType the mnMenuIllnesType to set
     */
    public void setMnMenuIllnesType(boolean mnMenuIllnesType) {
        this.mnMenuIllnesType = mnMenuIllnesType;
    }

    /**
     * @return the mnMenuInterviewPoint
     */
    public boolean isMnMenuInterviewPoint() {
        return mnMenuInterviewPoint;
    }

    /**
     * @param mnMenuInterviewPoint the mnMenuInterviewPoint to set
     */
    public void setMnMenuInterviewPoint(boolean mnMenuInterviewPoint) {
        this.mnMenuInterviewPoint = mnMenuInterviewPoint;
    }

    /**
     * @return the mnMenuInterviewer
     */
    public boolean isMnMenuInterviewer() {
        return mnMenuInterviewer;
    }

    /**
     * @param mnMenuInterviewer the mnMenuInterviewer to set
     */
    public void setMnMenuInterviewer(boolean mnMenuInterviewer) {
        this.mnMenuInterviewer = mnMenuInterviewer;
    }

    /**
     * @return the mnMenuInterviewFactor
     */
    public boolean isMnMenuInterviewFactor() {
        return mnMenuInterviewFactor;
    }

    /**
     * @param mnMenuInterviewFactor the mnMenuInterviewFactor to set
     */
    public void setMnMenuInterviewFactor(boolean mnMenuInterviewFactor) {
        this.mnMenuInterviewFactor = mnMenuInterviewFactor;
    }

    /**
     * @return the mnMenuOrientationGroup
     */
    public boolean isMnMenuOrientationGroup() {
        return mnMenuOrientationGroup;
    }

    /**
     * @param mnMenuOrientationGroup the mnMenuOrientationGroup to set
     */
    public void setMnMenuOrientationGroup(boolean mnMenuOrientationGroup) {
        this.mnMenuOrientationGroup = mnMenuOrientationGroup;
    }

    /**
     * @return the mnMenuOrientationActivity
     */
    public boolean isMnMenuOrientationActivity() {
        return mnMenuOrientationActivity;
    }

    /**
     * @param mnMenuOrientationActivity the mnMenuOrientationActivity to set
     */
    public void setMnMenuOrientationActivity(boolean mnMenuOrientationActivity) {
        this.mnMenuOrientationActivity = mnMenuOrientationActivity;
    }

    /**
     * @return the mnMenuCountryy
     */
    public boolean isMnMenuCountryy() {
        return mnMenuCountryy;
    }

    /**
     * @param mnMenuCountryy the mnMenuCountryy to set
     */
    public void setMnMenuCountryy(boolean mnMenuCountryy) {
        this.mnMenuCountryy = mnMenuCountryy;
    }

    /**
     * @return the mnMenuProvince
     */
    public boolean isMnMenuProvince() {
        return mnMenuProvince;
    }

    /**
     * @param mnMenuProvince the mnMenuProvince to set
     */
    public void setMnMenuProvince(boolean mnMenuProvince) {
        this.mnMenuProvince = mnMenuProvince;
    }

    /**
     * @return the mnMenuRegency
     */
    public boolean isMnMenuRegency() {
        return mnMenuRegency;
    }

    /**
     * @param mnMenuRegency the mnMenuRegency to set
     */
    public void setMnMenuRegency(boolean mnMenuRegency) {
        this.mnMenuRegency = mnMenuRegency;
    }

    /**
     * @return the mnMenuSubRegency
     */
    public boolean isMnMenuSubRegency() {
        return mnMenuSubRegency;
    }

    /**
     * @param mnMenuSubRegency the mnMenuSubRegency to set
     */
    public void setMnMenuSubRegency(boolean mnMenuSubRegency) {
        this.mnMenuSubRegency = mnMenuSubRegency;
    }

    /**
     * @return the mnMenuServiceCenter
     */
    public boolean isMnMenuServiceCenter() {
        return mnMenuServiceCenter;
    }

    /**
     * @param mnMenuServiceCenter the mnMenuServiceCenter to set
     */
    public void setMnMenuServiceCenter(boolean mnMenuServiceCenter) {
        this.mnMenuServiceCenter = mnMenuServiceCenter;
    }

    /**
     * @return the mnMenuManualProcess
     */
    public boolean isMnMenuManualProcess() {
        return mnMenuManualProcess;
    }

    /**
     * @param mnMenuManualProcess the mnMenuManualProcess to set
     */
    public void setMnMenuManualProcess(boolean mnMenuManualProcess) {
        this.mnMenuManualProcess = mnMenuManualProcess;
    }

    /**
     * @return the mnMenuAdminTestAdmin
     */
    public boolean isMnMenuAdminTestAdmin() {
        return mnMenuAdminTestAdmin;
    }

    /**
     * @param mnMenuAdminTestAdmin the mnMenuAdminTestAdmin to set
     */
    public void setMnMenuAdminTestAdmin(boolean mnMenuAdminTestAdmin) {
        this.mnMenuAdminTestAdmin = mnMenuAdminTestAdmin;
    }

    /**
     * @return the mnMenuAdminQuerySetup
     */
    public boolean isMnMenuAdminQuerySetup() {
        return mnMenuAdminQuerySetup;
    }

    /**
     * @param mnMenuAdminQuerySetup the mnMenuAdminQuerySetup to set
     */
    public void setMnMenuAdminQuerySetup(boolean mnMenuAdminQuerySetup) {
        this.mnMenuAdminQuerySetup = mnMenuAdminQuerySetup;
    }

    /**
     * @return the mnMenuUserList
     */
    public boolean isMnMenuUserList() {
        return mnMenuUserList;
    }

    /**
     * @param mnMenuUserList the mnMenuUserList to set
     */
    public void setMnMenuUserList(boolean mnMenuUserList) {
        this.mnMenuUserList = mnMenuUserList;
    }

    /**
     * @return the mnMenuGroupPrivilege
     */
    public boolean isMnMenuGroupPrivilege() {
        return mnMenuGroupPrivilege;
    }

    /**
     * @param mnMenuGroupPrivilege the mnMenuGroupPrivilege to set
     */
    public void setMnMenuGroupPrivilege(boolean mnMenuGroupPrivilege) {
        this.mnMenuGroupPrivilege = mnMenuGroupPrivilege;
    }

    /**
     * @return the mnMenuPrivilege
     */
    public boolean isMnMenuPrivilege() {
        return mnMenuPrivilege;
    }

    /**
     * @param mnMenuPrivilege the mnMenuPrivilege to set
     */
    public void setMnMenuPrivilege(boolean mnMenuPrivilege) {
        this.mnMenuPrivilege = mnMenuPrivilege;
    }

    /**
     * @return the mnMenuUpdatePassword
     */
    public boolean isMnMenuUpdatePassword() {
        return mnMenuUpdatePassword;
    }

    /**
     * @param mnMenuUpdatePassword the mnMenuUpdatePassword to set
     */
    public void setMnMenuUpdatePassword(boolean mnMenuUpdatePassword) {
        this.mnMenuUpdatePassword = mnMenuUpdatePassword;
    }

    /**
     * @return the mnMenuUserCompare
     */
    public boolean isMnMenuUserCompare() {
        return mnMenuUserCompare;
    }

    /**
     * @param mnMenuUserCompare the mnMenuUserCompare to set
     */
    public void setMnMenuUserCompare(boolean mnMenuUserCompare) {
        this.mnMenuUserCompare = mnMenuUserCompare;
    }

    /**
     * @return the mnMenuSystemProperties
     */
    public boolean isMnMenuSystemProperties() {
        return mnMenuSystemProperties;
    }

    /**
     * @param mnMenuSystemProperties the mnMenuSystemProperties to set
     */
    public void setMnMenuSystemProperties(boolean mnMenuSystemProperties) {
        this.mnMenuSystemProperties = mnMenuSystemProperties;
    }

    /**
     * @return the mnMenuLoginHistory
     */
    public boolean isMnMenuLoginHistory() {
        return mnMenuLoginHistory;
    }

    /**
     * @param mnMenuLoginHistory the mnMenuLoginHistory to set
     */
    public void setMnMenuLoginHistory(boolean mnMenuLoginHistory) {
        this.mnMenuLoginHistory = mnMenuLoginHistory;
    }

    /**
     * @return the mnMenuSystemLog
     */
    public boolean isMnMenuSystemLog() {
        return mnMenuSystemLog;
    }

    /**
     * @param mnMenuSystemLog the mnMenuSystemLog to set
     */
    public void setMnMenuSystemLog(boolean mnMenuSystemLog) {
        this.mnMenuSystemLog = mnMenuSystemLog;
    }

    /**
     * @return the mnMenuServiceManager
     */
    public boolean isMnMenuServiceManager() {
        return mnMenuServiceManager;
    }

    /**
     * @param mnMenuServiceManager the mnMenuServiceManager to set
     */
    public void setMnMenuServiceManager(boolean mnMenuServiceManager) {
        this.mnMenuServiceManager = mnMenuServiceManager;
    }

    /**
     * @return the mnMenuGeneral
     */
    public boolean isMnMenuGeneral() {
        return mnMenuGeneral;
    }

    /**
     * @param mnMenuGeneral the mnMenuGeneral to set
     */
    public void setMnMenuGeneral(boolean mnMenuGeneral) {
        this.mnMenuGeneral = mnMenuGeneral;
    }

    /**
     * @return the mnMenuPayrollPeriode
     */
    public boolean isMnMenuPayrollPeriode() {
        return mnMenuPayrollPeriode;
    }

    /**
     * @param mnMenuPayrollPeriode the mnMenuPayrollPeriode to set
     */
    public void setMnMenuPayrollPeriode(boolean mnMenuPayrollPeriode) {
        this.mnMenuPayrollPeriode = mnMenuPayrollPeriode;
    }

    /**
     * @return the mnMenuBankList
     */
    public boolean isMnMenuBankList() {
        return mnMenuBankList;
    }

    /**
     * @param mnMenuBankList the mnMenuBankList to set
     */
    public void setMnMenuBankList(boolean mnMenuBankList) {
        this.mnMenuBankList = mnMenuBankList;
    }

    /**
     * @return the mnMenuPayslipGroup
     */
    public boolean isMnMenuPayslipGroup() {
        return mnMenuPayslipGroup;
    }

    /**
     * @param mnMenuPayslipGroup the mnMenuPayslipGroup to set
     */
    public void setMnMenuPayslipGroup(boolean mnMenuPayslipGroup) {
        this.mnMenuPayslipGroup = mnMenuPayslipGroup;
    }

    /**
     * @return the mnMenuSalaryComponent
     */
    public boolean isMnMenuSalaryComponent() {
        return mnMenuSalaryComponent;
    }

    /**
     * @param mnMenuSalaryComponent the mnMenuSalaryComponent to set
     */
    public void setMnMenuSalaryComponent(boolean mnMenuSalaryComponent) {
        this.mnMenuSalaryComponent = mnMenuSalaryComponent;
    }

    /**
     * @return the mnMenuSalaryLevel
     */
    public boolean isMnMenuSalaryLevel() {
        return mnMenuSalaryLevel;
    }

    /**
     * @param mnMenuSalaryLevel the mnMenuSalaryLevel to set
     */
    public void setMnMenuSalaryLevel(boolean mnMenuSalaryLevel) {
        this.mnMenuSalaryLevel = mnMenuSalaryLevel;
    }

    /**
     * @return the mnMenuEmployeeSetup
     */
    public boolean isMnMenuEmployeeSetup() {
        return mnMenuEmployeeSetup;
    }

    /**
     * @param mnMenuEmployeeSetup the mnMenuEmployeeSetup to set
     */
    public void setMnMenuEmployeeSetup(boolean mnMenuEmployeeSetup) {
        this.mnMenuEmployeeSetup = mnMenuEmployeeSetup;
    }

    /**
     * @return the mnMenuCurrency
     */
    public boolean isMnMenuCurrency() {
        return mnMenuCurrency;
    }

    /**
     * @param mnMenuCurrency the mnMenuCurrency to set
     */
    public void setMnMenuCurrency(boolean mnMenuCurrency) {
        this.mnMenuCurrency = mnMenuCurrency;
    }

    /**
     * @return the mnMenuCurrencyRate
     */
    public boolean isMnMenuCurrencyRate() {
        return mnMenuCurrencyRate;
    }

    /**
     * @param mnMenuCurrencyRate the mnMenuCurrencyRate to set
     */
    public void setMnMenuCurrencyRate(boolean mnMenuCurrencyRate) {
        this.mnMenuCurrencyRate = mnMenuCurrencyRate;
    }

    /**
     * @return the mnMenuOvertimeForm
     */
    public boolean isMnMenuOvertimeForm() {
        return mnMenuOvertimeForm;
    }

    /**
     * @param mnMenuOvertimeForm the mnMenuOvertimeForm to set
     */
    public void setMnMenuOvertimeForm(boolean mnMenuOvertimeForm) {
        this.mnMenuOvertimeForm = mnMenuOvertimeForm;
    }

    /**
     * @return the mnMenuOvertimeRptProcess
     */
    public boolean isMnMenuOvertimeRptProcess() {
        return mnMenuOvertimeRptProcess;
    }

    /**
     * @param mnMenuOvertimeRptProcess the mnMenuOvertimeRptProcess to set
     */
    public void setMnMenuOvertimeRptProcess(boolean mnMenuOvertimeRptProcess) {
        this.mnMenuOvertimeRptProcess = mnMenuOvertimeRptProcess;
    }

    /**
     * @return the mnMenuOvertimeIndex
     */
    public boolean isMnMenuOvertimeIndex() {
        return mnMenuOvertimeIndex;
    }

    /**
     * @param mnMenuOvertimeIndex the mnMenuOvertimeIndex to set
     */
    public void setMnMenuOvertimeIndex(boolean mnMenuOvertimeIndex) {
        this.mnMenuOvertimeIndex = mnMenuOvertimeIndex;
    }

    /**
     * @return the mnMenuOvertimeSummary
     */
    public boolean isMnMenuOvertimeSummary() {
        return mnMenuOvertimeSummary;
    }

    /**
     * @param mnMenuOvertimeSummary the mnMenuOvertimeSummary to set
     */
    public void setMnMenuOvertimeSummary(boolean mnMenuOvertimeSummary) {
        this.mnMenuOvertimeSummary = mnMenuOvertimeSummary;
    }

    /**
     * @return the mnMenuPrepareData
     */
    public boolean isMnMenuPrepareData() {
        return mnMenuPrepareData;
    }

    /**
     * @param mnMenuPrepareData the mnMenuPrepareData to set
     */
    public void setMnMenuPrepareData(boolean mnMenuPrepareData) {
        this.mnMenuPrepareData = mnMenuPrepareData;
    }

    /**
     * @return the mnMenuPayrollInput
     */
    public boolean isMnMenuPayrollInput() {
        return mnMenuPayrollInput;
    }

    /**
     * @param mnMenuPayrollInput the mnMenuPayrollInput to set
     */
    public void setMnMenuPayrollInput(boolean mnMenuPayrollInput) {
        this.mnMenuPayrollInput = mnMenuPayrollInput;
    }

    /**
     * @return the mnMenuPayrollProcess
     */
    public boolean isMnMenuPayrollProcess() {
        return mnMenuPayrollProcess;
    }

    /**
     * @param mnMenuPayrollProcess the mnMenuPayrollProcess to set
     */
    public void setMnMenuPayrollProcess(boolean mnMenuPayrollProcess) {
        this.mnMenuPayrollProcess = mnMenuPayrollProcess;
    }

    /**
     * @return the mnMenuPayslipPrinting
     */
    public boolean isMnMenuPayslipPrinting() {
        return mnMenuPayslipPrinting;
    }

    /**
     * @param mnMenuPayslipPrinting the mnMenuPayslipPrinting to set
     */
    public void setMnMenuPayslipPrinting(boolean mnMenuPayslipPrinting) {
        this.mnMenuPayslipPrinting = mnMenuPayslipPrinting;
    }

    /**
     * @return the mnMenuLocation
     */
    public boolean isMnMenuLocation() {
        return mnMenuLocation;
    }

    /**
     * @param mnMenuLocation the mnMenuLocation to set
     */
    public void setMnMenuLocation(boolean mnMenuLocation) {
        this.mnMenuLocation = mnMenuLocation;
    }

    /**
     * @return the mnMenuEmployeeOutlet
     */
    public boolean isMnMenuEmployeeOutlet() {
        return mnMenuEmployeeOutlet;
    }

    /**
     * @param mnMenuEmployeeOutlet the mnMenuEmployeeOutlet to set
     */
    public void setMnMenuEmployeeOutlet(boolean mnMenuEmployeeOutlet) {
        this.mnMenuEmployeeOutlet = mnMenuEmployeeOutlet;
    }

    /**
     * @return the mnMenuLeaveSetting
     */
    public boolean isMnMenuLeaveSetting() {
        return mnMenuLeaveSetting;
    }

    /**
     * @param mnMenuLeaveSetting the mnMenuLeaveSetting to set
     */
    public void setMnMenuLeaveSetting(boolean mnMenuLeaveSetting) {
        this.mnMenuLeaveSetting = mnMenuLeaveSetting;
    }
}
