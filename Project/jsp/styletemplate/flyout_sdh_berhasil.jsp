<%-- 
    Document   : flyout
    Created on : Sep 18, 2013, 5:13:29 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.entity.template.Flyout"%>


<%if(!isMSIE){%>
<link rel="stylesheet" href="../stylesheets/flayout_no_ie.css" type="text/css">
<%}else{%>
<style type="text/css">
    .content{position:relative; width:100%; 
/*background-color:white;
background-repeat: repeat-x;
padding-top:61px;*/
min-height: 400px;
height: 400px !important;
background-attachment: local;
 background-image:url(../stylesheets/images/background.png);
 background-repeat: no-repeat;
background-position: right bottom;
 /*background:url(../menuaplikasi/imgHeader/elemet%20grafis.png) no-repeat right ;*/
}
</style>
<link rel="stylesheet" href="../stylesheets/flayout_with_ie.css" type="text/css">
<%}%>
<%
Flyout flyout = new Flyout();
flyout.setMnMenuDataBank(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnCompanyStructure(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_COMPANY_STRUCTURE, AppObjInfo.OBJ_MENU_COMPANY_STRUCTURE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuNewEmployee(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_NEW_EMPLOYEE, AppObjInfo.OBJ_MENU_NEW_EMPLOYEE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuAbsMan(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ABSENCE_MANAGEMENT, AppObjInfo.OBJ_MENU_ABSENCE_MANAGEMENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuEntryPerDept(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECOGNITION, AppObjInfo.OBJ_MENU_ENTRY_PER_DEPT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuUpdatePerEmp(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECOGNITION, AppObjInfo.OBJ_MENU_UPDATE_PER_EMPLOYEE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuStaffRequisition(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECRUITMENT, AppObjInfo.OBJ_MENU_STAFF_REQUISITION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuEmpApplication(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECRUITMENT, AppObjInfo.OBJ_MENU_EMPLOYEE_APPLICATION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuOrientationList(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECRUITMENT, AppObjInfo.OBJ_MENU_ORIENTATION_CHECK_LIST), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuReminder(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECRUITMENT, AppObjInfo.OBJ_MENU_REMINDER), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuEmpWarning(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_WARNING_AND_REPRIMAND, AppObjInfo.OBJ_MENU_WARNING), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuEmpReprimand(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_WARNING_AND_REPRIMAND, AppObjInfo.OBJ_MENU_REPRIMAND), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuExcuseForm(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_EXCUSE_APPLICATION, AppObjInfo.OBJ_MENU_EXCUSE_FORM), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuWorkingSchedule(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ATTENDANCE, AppObjInfo.OBJ_MENU_WORKING_SCHEDULE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuManualRegistration(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ATTENDANCE, AppObjInfo.OBJ_MENU_MANUAL_REGISTRATION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuReGenScheduleHoly(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ATTENDANCE, AppObjInfo.OBJ_MENU_REGENERATE_SCHEDULE_HOLIDAYS), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLeaveForm(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_LEAVE_FORM), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLeaveAlClosing(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_LEAVE_AL_CLOSING), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLeavellClosing(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_LEAVE_LI_CLOSING), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuDpManagement(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_DP_MANAGEMENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuRecalculate(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_DP_RECALCULATE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuAnualLeaveBalancing(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_BALANCING, AppObjInfo.OBJ_MENU_ANNUAL_LEAVE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLongLeaveBalancing(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_BALANCING, AppObjInfo.OBJ_MENU_LONG_LEAVE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuDPPayment(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_BALANCING, AppObjInfo.OBJ_MENU_DAY_OFF_PAYMENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuExplanations(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ASSESSMENT, AppObjInfo.OBJ_MENU_EXPLANATION_COVERAGE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuPerfomAssment( userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ASSESSMENT, AppObjInfo.OBJ_MENU_PERFORMANCE_ASSESSMENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingType(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_TYPE, AppObjInfo.OBJ_MENU_TRAINING_TYPE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingVanue(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_VANUE, AppObjInfo.OBJ_MENU_TRAINING_VANUE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingProgram(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_PROGRAM, AppObjInfo.OBJ_MENU_TRAINING_PROGRAM), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingPlan(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_PLAN, AppObjInfo.OBJ_MENU_TRAINING_PLAN), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingActual(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_ACTUAL, AppObjInfo.OBJ_MENU_TRAINING_ACTUAL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingSearch(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_TRAINING_SEARCH, AppObjInfo.OBJ_MENU_TRAINING_SEARCH), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingHistory(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_TRAINING_HISTORY, AppObjInfo.OBJ_MENU_TRAINING_HISTORY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuAttRecord(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_STAFF_CONTROL_REPORT, AppObjInfo.OBJ_MENU_ATTENDANCE_RECORD_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuDailyReportPresence(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_PRESENCE_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuWeeklyReportPresence(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_PRESENCE_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMontlyReportPresence(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_PRESENCE_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuYearlyReportPresence(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_YEAR_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuAttdSummaryReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_ATTENDANCE_SUMMARY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLatnessDailyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LATENESS_REPORT, AppObjInfo.OBJ_MENU_LATENESS_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLatnessWeeklyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LATENESS_REPORT, AppObjInfo.OBJ_MENU_LATENESS_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLatnessMonthlyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LATENESS_REPORT, AppObjInfo.OBJ_MENU_LATENESS_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSplitShiftDailyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPLIT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_SPLIT_SHIFT_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSplitShiftWeeklyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPLIT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_SPLIT_SHIFT_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSplitShiftMonthlyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPLIT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_SPLIT_SHIFT_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuNightShiftDailyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_NIGHT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_NIGHT_SHIFT_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuNightShiftWeeklyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_NIGHT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_NIGHT_SHIFT_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuNightShiftMonthlyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_NIGHT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_NIGHT_SHIFT_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuAbsenteeismDailyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_ABSENTEEISM_REPORT, AppObjInfo.OBJ_MENU_ABSENTEEISM_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuAbsenteeismWeeklyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_ABSENTEEISM_REPORT, AppObjInfo.OBJ_MENU_ABSENTEEISM_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuAbsenteeismMonthlyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_ABSENTEEISM_REPORT, AppObjInfo.OBJ_MENU_ABSENTEEISM_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSicknessDailyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SICKNESS_REPORT, AppObjInfo.OBJ_MENU_SICKNESS_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSicknessWeeklyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SICKNESS_REPORT, AppObjInfo.OBJ_MENU_SICKNESS_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSicknessMonthlyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SICKNESS_REPORT, AppObjInfo.OBJ_MENU_SICKNESS_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSicknessZeroReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SICKNESS_REPORT, AppObjInfo.OBJ_MENU_SICKNESS_ZERO_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSpcDispDailyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPECIAL_DISPENSATION_REPORT, AppObjInfo.OBJ_MENU_SPECIAL_DISPENSATION_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSpcDispWeeklyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPECIAL_DISPENSATION_REPORT, AppObjInfo.OBJ_MENU_SPECIAL_DISPENSATION_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSpcDispMonthlyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPECIAL_DISPENSATION_REPORT, AppObjInfo.OBJ_MENU_SPECIAL_DISPENSATION_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLeaveDPSum(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_LEAVE_DP_SUMMARY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLeaveDPDetail(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_LEAVE_DP_DETAIL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLeaveDPSumPeriod(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_LEAVE_DP_SUMMARY_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLeaveDPDetailPeriod(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_LEAVE_DP_DETAIL_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSpcUnpaidLeave(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_SPECIAL_UNPAID_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuDPExpired(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_DP_EXPIRED), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTraineeMonthlyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINEE_REPORT, AppObjInfo.OBJ_MENU_TRAINEE_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTraineeEndPeriodReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINEE_REPORT, AppObjInfo.OBJ_MENU_TRAINEE_END_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuListEmpCategory(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_CATEGORY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuListEmpResign(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_RESIGNATION_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuListEmpEducation(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_EDUCATION_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuListEmpCatByName(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_CATEGORY_BY_NAME_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuListNumbAbs(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_NUMBER_ABSENCES_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuListEmpRace(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_RACE_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMonthTraining(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingProfile(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_PROFILES_REPORT), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuTrainingTarget(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_TARGET_REPORT), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuTrainingRptByDpt (userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_DEPARTEMENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingRptByCourseDetail (userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DETAIL), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuTrainingRptByEmp (userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_EMPLOYEE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTrainingRptByTrainer (userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_TRAINNER), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuTrainingRptByCourseDate (userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuPayrollListOfSalary (userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuManualVisitation(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_DATA_CANTEEN, AppObjInfo.OBJ_MENU_MANUAL_VISITIATION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuCanteenSchedule(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_DATA_CANTEEN, AppObjInfo.OBJ_MENU_DATA_CANTEEN_SCHEDULE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuChecklistGroup(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN, AppObjInfo.OBJ_MENU_CANTEEN_CHECKLIST_GROUP), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuChecklistItem(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN, AppObjInfo.OBJ_MENU_CANTEEN_CHECKLIST_ITEM), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuChecklistMark(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN, AppObjInfo.OBJ_MENU_CANTEEN_CHECKLIST_MARK), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMenuItem(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN, AppObjInfo.OBJ_MENU_CANTEEN_MENU_ITEM), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMealTime(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN, AppObjInfo.OBJ_MENU_CANTEEN_MEAL_TIME), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuCommentGroup(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN, AppObjInfo.OBJ_MENU_CANTEEN_COMMENT_GROUP), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuCommentQuestion(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN, AppObjInfo.OBJ_MENU_CANTEEN_COMMENT_QUESTION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuDailyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN_REPORT_DETAIL, AppObjInfo.OBJ_MENU_CANTEEN_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuWeeklyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN_REPORT_DETAIL, AppObjInfo.OBJ_MENU_CANTEEN_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMonthlyReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN_REPORT_DETAIL, AppObjInfo.OBJ_MENU_CANTEEN_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuDailyMealRecord(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN_REPORT_SUMMARY, AppObjInfo.OBJ_MENU_DAILY_MEAL_RECORD_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuPeriodicMealRecord(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN_REPORT_SUMMARY, AppObjInfo.OBJ_MENU_PERIODIC_MEAL_RECORD_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMealReports(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN_REPORT_SUMMARY, AppObjInfo.OBJ_MENU_MEAL_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMealReportsDepartement(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN_REPORT_SUMMARY, AppObjInfo.OBJ_MENU_MEAL_REPORT_DEPARTEMENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMounthlyCanteenReport(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CANTEEN, AppObjInfo.G2_MENU_CANTEEN_REPORT_SUMMARY, AppObjInfo.OBJ_MENU_MONTHLY_CANTEEN_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
//clinic
flyout.setMnMenuEmployeeFamily(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_EMPLOYEE_AND_FAMILY, AppObjInfo.OBJ_MENU_EMPLOYEE_AND_FAMILY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMedicalRecord(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_CLINIC_MEDICAL_RECORD, AppObjInfo.OBJ_MENU_CLINIC_MEDICAL_RECORD), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuEmployeeVisit(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_EMPLOYEE_VISIT, AppObjInfo.OBJ_MENU_EMPLOYEE_VISIT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuGuestHandling(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_GUEST_HANDLING, AppObjInfo.OBJ_MENU_GUEST_HANDLING), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuListOfMedicine(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_MEDICINE, AppObjInfo.OBJ_MENU_LIST_OF_MEDICINE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMedicineConsumpsition(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_MEDICINE, AppObjInfo.OBJ_MENU_MEDICINE_CONSUMPTION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuDiseaseType(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_DISEASE, AppObjInfo.OBJ_MENU_DISEASE_TYPE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMedicalLevel(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_MEDICAL, AppObjInfo.OBJ_MENU_MEDICAL_LEVEL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMedicalCase(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_MEDICAL, AppObjInfo.OBJ_MENU_MEDICAL_CASE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMedicalBudget(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_MEDICAL, AppObjInfo.OBJ_MENU_MEDICAL_BUDGET), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuGroup(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_MEDICAL, AppObjInfo.OBJ_MENU_MEDICAL_GROUP), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuTypee(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_CLINIC, AppObjInfo.G2_MENU_MEDICAL, AppObjInfo.OBJ_MENU_MEDICAL_TYPE), 
             AppObjInfo.COMMAND_VIEW))); 
//Locker
flyout.setMnMenuLockerTreatment(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_LOCKER, AppObjInfo.G2_MENU_LOCKER_TREATMENT, AppObjInfo.OBJ_MENU_LOCKER_TREATMENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLockerr(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_LOCKER, AppObjInfo.G2_MENU_LOCKER, AppObjInfo.OBJ_MENU_LOCKER), 
             AppObjInfo.COMMAND_VIEW)));
//Masterdata
flyout.setMnMenuCompany(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_COMMPANY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuDivision(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_DIVISION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuDepartement(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_DEPARTMENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuPosition(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_POSITION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSection(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_SECTION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuPublicHoliday(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_PUBLIC_HOLIDAY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLeaveTarget(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_LEAVE_TARGET), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuEducation(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_EDUCATION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuFamilyRelation(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_FAMILY_RELATION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuWarningg(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_WARNING), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuReprimand(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_REPRIMAND), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLevel(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_LEVEL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuCategory(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_CATEGORY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuReligion(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_RELIGION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuMarital(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_MARITAL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuRace(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_RACE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLanguage(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_LANGUAGE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuImageAssign(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_IMAGE_ASSIGN), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuResigneReason(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_RESIGNED_REASON), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuAwardType(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_AWARD_TYPE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuAbsenanceReason(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_ABSENCE_REASON), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuPeriode(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_SCHEDULE, AppObjInfo.OBJ_MENU_SCHEDULE_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuCategoryy(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_SCHEDULE, AppObjInfo.OBJ_MENU_SCHEDULE_CATEGORY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSymbol(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_SCHEDULE, AppObjInfo.OBJ_MENU_SCHEDULE_SYMBOL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLockerLocation(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_LOCKER_DATA, AppObjInfo.OBJ_MENU_LOCKER_DATA_LOCATION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuLockerCondition(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_LOCKER_DATA, AppObjInfo.OBJ_MENU_LOCKER_DATA_CONDITION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuGroupRank(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_ASSESSMENT, AppObjInfo.OBJ_MENU_MD_ASSESSMENT_GROUP_RANK), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuEvaluationCriteria(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_ASSESSMENT, AppObjInfo.OBJ_MENU_MD_ASSESSMENT_EVALUATION_CRITERIA), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuFormCreator(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_ASSESSMENT, AppObjInfo.OBJ_MENU_MD_ASSESSMENT_FORM_CREATOR), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuGeneralQuestion(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_GENERAL_QUESTION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuIllnesType(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_ILLNESS_TYPE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuInterviewPoint(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_INTERVIEW_POINTS), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuInterviewer(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_INTERVIEWER), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuInterviewFactor( userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_INTERVIEW_FACTOR), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuOrientationGroup(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_ORIENTATION_GROUP), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuOrientationActivity(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_ORIENTATION_ACTIVITY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuCountryy(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_GEO_AREA, AppObjInfo.OBJ_MENU_EMP_COUNTRY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuProvince(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_GEO_AREA, AppObjInfo.OBJ_MENU_EMP_PROVINCE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuRegency(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_GEO_AREA, AppObjInfo.OBJ_MENU_EMP_REGENCY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSubRegency(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_GEO_AREA, AppObjInfo.OBJ_MENU_EMP_SUBREGENCY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuServiceCenter(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_SERVICE_CENTER, AppObjInfo.OBJ_MENU_SERVICE_CENTER), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuManualProcess(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_MANUAL_PROCESS, AppObjInfo.OBJ_MENU_MANUAL_PROCESS), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuAdminTestAdmin(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_ADMIN_TEST_MESIN, AppObjInfo.OBJ_MENU_ADMIN_TEST_MESIN), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuAdminQuerySetup(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_ADMIN_QUERY_SETUP, AppObjInfo.OBJ_MENU_ADMIN_QUERY_SETUP), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuUserList(true); 
flyout.setMnMenuGroupPrivilege(true); 
flyout.setMnMenuPrivilege(true); 
flyout.setMnMenuUpdatePassword(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_USER_MANAGEMENT, AppObjInfo.OBJ_MENU_USER_UPDATE_PASSWORD), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuUserCompare(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_USER_MANAGEMENT, AppObjInfo.OBJ_MENU_USER_COMPARE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSystemProperties(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_MENU_SYSTEM_PROPERTIES), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuLoginHistory(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_MENU_LOGIN_HISTORY), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuSystemLog(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_MENU_SYSTEM_LOG), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuServiceManager(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_TIMEKEEPING, AppObjInfo.OBJ_MENU_SERVICE_MANAGER), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuGeneral(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_GENERAL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuPayrollPeriode(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_PAYROLL_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuBankList(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_BANK_LIST), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuPayslipGroup(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_PAY_SLIP_GROUP), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSalaryComponent(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_SALARY_COMPONENT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuSalaryLevel(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_SALARY_LEVEL), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuEmployeeSetup(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_EMPLOYEE_SETUP), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuCurrency(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_CURRENCY), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuCurrencyRate(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_CURRENCY_RATE), 
             AppObjInfo.COMMAND_VIEW)));


flyout.setMnMenuOvertimeForm(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_OVERTIME, AppObjInfo.OBJ_MENU_PAYROLL_OVERTIME_FORM), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuOvertimeRptProcess(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_OVERTIME, AppObjInfo.OBJ_MENU_PAYROLL_OVERTIME_REPORT_PROCESS), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuOvertimeIndex(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_OVERTIME, AppObjInfo.OBJ_MENU_PAYROLL_OVERTIME_INDEX), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuOvertimeSummary(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_OVERTIME, AppObjInfo.OBJ_MENU_PAYROLL_OVERTIME_SUMMARY), 
             AppObjInfo.COMMAND_VIEW)));
           
//Menu Payroll Process
flyout.setMnMenuPrepareData(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_PREPARE_DATA), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.setMnMenuPayrollInput(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_INPUT), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuPayrollProcess(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_PROCESS), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuPayslipPrinting(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_PRINTING), 
             AppObjInfo.COMMAND_VIEW)));

//update by satrya 2014-03-22
flyout.setMnMenuLocation(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MASTERDATA_MENU_LOCATION_OUTLET, AppObjInfo.OBJ_MASTERDATA_MENU_LOCATION_OUTLET), 
             AppObjInfo.COMMAND_VIEW)));
flyout.setMnMenuEmployeeOutlet(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_EMPLOYEE_OUTLET, AppObjInfo.OBJ_MENU_EMPLOYEE_OUTLET), 
             AppObjInfo.COMMAND_VIEW)));

flyout.setMnMenuLeaveSetting(userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_LEAVE_SETTING), 
             AppObjInfo.COMMAND_VIEW))); 

flyout.MenuFlyout(out, url, isMSIE, approot);

%>

                
               

