<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<script language=JavaScript>
   /* This function used to make pulldown menu only */
    function fwLoadMenus() {        
        <%
        int TYPE_HARDROCK       = 0;
        int TYPE_NIKKO          = 1;
        int TYPE_SANUR_PARADISE = 2;        
        int TYPE_INTIMAS        = 3;        
        int TYPE_ATTENDANCE_TRANSFER_ONLY=4;
        int TYPE_CONFIG =  TYPE_HARDROCK;
        
        I_Dictionary dictionaryD = userSession.getUserDictionary();
 dictionaryD.loadWord();
        boolean isAppraisal = false;//false jika ingin menampilkan appaisal yg baru
        boolean mnuAttendance = true;
        boolean mnuLeave = true;
        boolean mnuExcuse = true;
        boolean mnuAppraisal = true;
        boolean mnuRecruitment=true;
        //update by satrya 2013-06-06
        // boolean mnuTraining=true;
        //boolean mnuCanteen=true;
        boolean mnuMedical = true;
        boolean mnuLocker = true;
        //update by satrya 2014-02-25
        boolean mnuOutlet = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_OUTLET, AppObjInfo.OBJ_EMPLOYEE_OUTLET), 
             AppObjInfo.COMMAND_VIEW)) ;
        boolean mnLocationOutlet=userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_LOCATION_OUTLET, AppObjInfo.OBJ_LOCATION_OUTLET), 
             AppObjInfo.COMMAND_VIEW)) ;
        
        boolean mnuMsdLocationOutlet = userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_LOCATION_OUTLET, AppObjInfo.OBJ_LOCATION_OUTLET), 
             AppObjInfo.COMMAND_VIEW)) ;
        boolean mnuPayrollInput =
             userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_INPUT), 
             AppObjInfo.COMMAND_VIEW)) ;
        boolean mnuPayroll = 
          userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_GENERAL), 
             AppObjInfo.COMMAND_VIEW));
        boolean mnuOvertime = false;
             /* userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME, AppObjInfo.OBJ_PAYROLL_OVERTIME_FORM), 
             AppObjInfo.COMMAND_VIEW)); */
        
        boolean mnuCanteen= userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_CANTEEN, AppObjInfo.OBJ_MENU_ITEM), 
             AppObjInfo.COMMAND_VIEW)); 
             
         boolean mnuTraining=userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_TRAINING_SEARCH), 
             AppObjInfo.COMMAND_VIEW));
         
         boolean mnuLeaveConfig=userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_LEAVE_CONFIGURATION), 
             AppObjInfo.COMMAND_VIEW));
         //update by devin 2014-04-08
         boolean mnuRewardnPunishmentSetting=userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_REWARD_PUNISMENT), 
             AppObjInfo.COMMAND_VIEW));
         boolean mnuKoefisionPositionSetting=userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_KOEFISIEN_POSITION), 
             AppObjInfo.COMMAND_VIEW));
          boolean mnuAdjusmentWorkingDay=false;
            boolean mnuJenisSo=userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_LOCATION_OUTLET, AppObjInfo.OBJ_JENIS_SO), 
             AppObjInfo.COMMAND_VIEW));
        %>
            
        if (window.fw_menu_0) return;	
        <%if(mnuOutlet){%>    
            window.fw_menu_employee_outlet = new Menu("Employee Outlet",190,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_employee_outlet.addMenuItem("Employee Outlet", "location='<%=String.valueOf(approot)%>/employee/outlet/outlet.jsp'");
            
            fw_menu_employee_outlet.hideOnMouseOut=true;
        <%}%>
        <%if(mnuAttendance){ %>
            /* Employee > Attendance > Presence */
            window.fw_menu_presence = new Menu("Presence",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_presence.addMenuItem("Manual Registration", "location='<%=String.valueOf(approot)%>/employee/presence/srcpresence.jsp'");
            fw_menu_presence.addMenuItem("View Presence", "location='<%=String.valueOf(approot)%>/employee/presence/srcviewpresence.jsp'");
            fw_menu_presence.hideOnMouseOut=true;

            /* Employee > Attendance */
            window.fw_menu_attendance = new Menu("Attendance",190,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_attendance.addMenuItem("Working Schedule", "location='<%=String.valueOf(approot)%>/employee/attendance/srcempschedule.jsp'");
            fw_menu_attendance.addMenuItem("Manual Registration", "location='<%=String.valueOf(approot)%>/employee/presence/srcpresence.jsp'");
            //update by satrya 2013-01-14
            fw_menu_attendance.addMenuItem("Re-Generate Schedule Holidays", "location='<%=String.valueOf(approot)%>/report/presence/Update_schedule_If_holidays.jsp'");
            fw_menu_attendance.childMenuIcon="<%=approot%>/images/arrows.gif";
            fw_menu_attendance.hideOnMouseOut=true;
          <% } %>

          <%if(mnuLeave){%>
                /* Employee > Leave Management */
                window.fw_leave_app = new Menu("Leave Application",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_leave_app.addMenuItem("Leave Form", "location='<%=String.valueOf(approot)%>/employee/leave/leave_app_src.jsp'");
                fw_leave_app.addMenuItem("Leave Al Closing", "location='<%=String.valueOf(approot)%>/employee/leave/leave_al_closing.jsp'");
                fw_leave_app.addMenuItem("Leave Ll Closing", "location='<%=String.valueOf(approot)%>/employee/leave/leave_ll_closing.jsp'");
                fw_leave_app.addMenuItem("DP Management", "location='<%=String.valueOf(approot)%>/employee/attendance/dp.jsp'");
                //update by satrya 2013-03-2013
                fw_leave_app.addMenuItem("DP Re-Calculate DP", "location='<%=String.valueOf(approot)%>/employee/leave/if_dp_not_balance.jsp'");
                fw_leave_app.hideOnMouseOut=true;

                window.fw_menu_leave_balnc = new Menu("Leave Balancing",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_leave_balnc.addMenuItem("Annual Leave", "location='<%=String.valueOf(approot)%>/system/leave/AL_Balancing.jsp'");
                fw_menu_leave_balnc.addMenuItem("Long Leave", "location='<%=String.valueOf(approot)%>/system/leave/LL_Balancing.jsp'");
                fw_menu_leave_balnc.addMenuItem("Day Off Payment", "location='<%=String.valueOf(approot)%>/system/leave/DP_Balancing.jsp'");
                fw_menu_leave_balnc.hideOnMouseOut=true;
           <%}%>
               //update by satrya 2013-04-11
                <%if(mnuExcuse){%>
                /* Employee > Leave Management */
                window.fw_excuse_app = new Menu("Excuse Application",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_excuse_app.addMenuItem("Excuse Form", "location='<%=String.valueOf(approot)%>/employee/leave/excuse_app_src.jsp'");
                fw_excuse_app.hideOnMouseOut=true;

           <%}%>

        <% if(mnuAppraisal){%>
                /* Employee > Appraisal */
                window.fw_menu_appraisal = new Menu("Assessment",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_appraisal.addMenuItem("Explanations and Coverage", "location='<%=String.valueOf(approot)%>/employee/appraisal/expcoverage.jsp'");
                //
               <%
                if(isAppraisal){  %>
                    fw_menu_appraisal.addMenuItem("Performance Appraisal", "location='<%=String.valueOf(approot)%>/employee/appraisal/srcappraisal.jsp'");
                <% }else{ %>
                    fw_menu_appraisal.addMenuItem("Performance Assessment", "location='<%=String.valueOf(approot)%>/employee/appraisalnew/srcappraisal.jsp'");
                <% } %>
                fw_menu_appraisal.hideOnMouseOut=true;

                /* Employee > Recognition */
                window.fw_menu_recognation = new Menu("Recognition",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_recognation.addMenuItem("Entry per Department", "location='<%=String.valueOf(approot)%>/employee/recognition/recognitiondep.jsp'");
                fw_menu_recognation.addMenuItem("Update per Employee", "location='<%=String.valueOf(approot)%>/employee/recognition/srcrecognition.jsp'");
                fw_menu_recognation.hideOnMouseOut=true;

             <%}%>

       <%if(mnuRecruitment){%>
            /* Master Data > Recruitment */
            window.fw_menu_recruit_master = new Menu("Recruitment",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_recruit_master.addMenuItem("General Questions", "location='<%=String.valueOf(approot)%>/employee/recruitment/recrgeneral.jsp'");
            fw_menu_recruit_master.addMenuItem("Illness Type", "location='<%=String.valueOf(approot)%>/employee/recruitment/recrillness.jsp'");
            fw_menu_recruit_master.addMenuItem("Interview Point", "location='<%=String.valueOf(approot)%>/employee/recruitment/recrinterviewpoint.jsp'");
            fw_menu_recruit_master.addMenuItem("Interviewer", "location='<%=String.valueOf(approot)%>/employee/recruitment/recrinterviewer.jsp'");
            fw_menu_recruit_master.addMenuItem("Interview Factor", "location='<%=String.valueOf(approot)%>/employee/recruitment/recrinterviewfactor.jsp'");
            fw_menu_recruit_master.addMenuItem("Orientation Group", "location='<%=String.valueOf(approot)%>/employee/recruitment/origroup.jsp'");
            fw_menu_recruit_master.addMenuItem("Orientation Activity", "location='<%=String.valueOf(approot)%>/employee/recruitment/oriactivity.jsp'");
            fw_menu_recruit_master.hideOnMouseOut=true;

            /* Employee > Recruitment */
            window.fw_menu_recruitment = new Menu("Recruitment",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_recruitment.addMenuItem("Staff Requisition", "location='<%=String.valueOf(approot)%>/employee/recruitment/srcstaffrequisition.jsp'");
            fw_menu_recruitment.addMenuItem("Employment Application", "location='<%=String.valueOf(approot)%>/employee/recruitment/srcrecrapplication.jsp'");
            fw_menu_recruitment.addMenuItem("Orientation Checklist", "location='<%=String.valueOf(approot)%>/employee/recruitment/srcorichecklist.jsp'");
            fw_menu_recruitment.addMenuItem("Reminder", "location='<%=String.valueOf(approot)%>/employee/recruitment/reminder.jsp'");
            //fw_menu_recruitment.addMenuItem(fw_menu_recruit_master);
            fw_menu_recruitment.childMenuIcon="<%=approot%>/images/arrows.gif";
            fw_menu_recruitment.hideOnMouseOut=true;
         <%}%>

            /* Employee > Warning & Reprimand */
            window.fw_menu_warning_reprimand = new Menu("Warning & Reprimand",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_warning_reprimand.addMenuItem("Warning", "location='<%=String.valueOf(approot)%>/employee/warning/src_warning.jsp'");
            fw_menu_warning_reprimand.addMenuItem("Reprimand", "location='<%=String.valueOf(approot)%>/employee/warning/src_reprimand.jsp'");
            fw_menu_warning_reprimand.hideOnMouseOut=true;

            //update by devin 2014-03-27
             <%if(mnuRewardnPunishmentSetting){%>
            window.fw_menu_reward_and_punishment_setting = new Menu("Reward Punishment",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_reward_and_punishment_setting.addMenuItem("Reward Punishment Setting", "location='<%=String.valueOf(approot)%>/configrewardnpunishment/configrewardnpunishment.jsp'");
            fw_menu_reward_and_punishment_setting.hideOnMouseOut=true;
             <%}%>
                   //update by devin 2014-03-28
             <%if(mnuKoefisionPositionSetting){%>
            window.fw_menu_koefision_position_setting = new Menu("Koefision Position",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_koefision_position_setting.addMenuItem("Koefision Position Setting", "location='<%=String.valueOf(approot)%>/koefisionposition/koefisionposition.jsp'");
            fw_menu_koefision_position_setting.hideOnMouseOut=true;
             <%}%>
                 <%if(mnuAdjusmentWorkingDay){%>
            window.fw_menu_Adjusment_working_day_setting = new Menu("Adjusment Working Day",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_Adjusment_working_day_setting.addMenuItem("Adjusment Working Day ", "location='<%=String.valueOf(approot)%>/adjusmentworkingday/adjusmentworkingday.jsp'");
            fw_menu_Adjusment_working_day_setting.hideOnMouseOut=true;
             <%}%>
        <%//if(mnuTraining){%>
            /* Employee > Training > Training Activities */
            window.fw_menu_train_act = new Menu("Training Activities",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_train_act.addMenuItem("Monthly Planning", "location='<%=String.valueOf(approot)%>/employee/training/training_act_plan_list.jsp'");
            fw_menu_train_act.addMenuItem("Actual", "location='<%=String.valueOf(approot)%>/employee/training/training_act_actual_list.jsp'");
            fw_menu_train_act.hideOnMouseOut=true;
			
            /* Employee > Training */
            window.fw_menu_training = new Menu("Training",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_training.addMenuItem("Training History", "location='<%=String.valueOf(approot)%>/employee/training/src_training_hist.jsp'");
            fw_menu_training.addMenuItem("Special Achievement", "location='<%=String.valueOf(approot)%>/employee/training/src_achieve.jsp'");
            fw_menu_training.addMenuItem(fw_menu_train_act);
            fw_menu_training.addMenuItem("Training Search", "location='<%=String.valueOf(approot)%>/employee/training/src_training_exist.jsp'");
            fw_menu_training.childMenuIcon="<%=approot%>/images/arrows.gif";
            fw_menu_training.hideOnMouseOut=true;
            
            /* TRAINING REPORTS */
            window.fw_menu_training_rpt = new Menu("Training Report",170,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_training_rpt.addMenuItem("Monthly Training", "location='<%=String.valueOf(approot)%>/report/training/monthly_train.jsp'");
            fw_menu_training_rpt.addMenuItem("Training Profiles", "location='<%=String.valueOf(approot)%>/report/training/src_training_profiles.jsp'");        
            fw_menu_training_rpt.addMenuItem("Training Target", "location='<%=String.valueOf(approot)%>/report/training/src_training_target.jsp'");
            fw_menu_training_rpt.addMenuItem("Report By Department", "location='<%=String.valueOf(approot)%>/report/training/src_report_dept.jsp'");   
            fw_menu_training_rpt.addMenuItem("Report By Employee", "location='<%=String.valueOf(approot)%>/report/training/src_report_emp.jsp'");
            fw_menu_training_rpt.addMenuItem("Report By Trainer", "location='<%=String.valueOf(approot)%>/report/training/src_report_trainer.jsp'");   
            fw_menu_training_rpt.addMenuItem("Report By Course Detail", "location='<%=String.valueOf(approot)%>/report/training/src_training_course.jsp'");
            fw_menu_training_rpt.addMenuItem("Report By Course Date", "location='<%=String.valueOf(approot)%>/report/training/src_training_coursedate.jsp'");    
            fw_menu_training_rpt.hideOnMouseOut=true;
            
            /* TRAINING */
            window.fw_menu_training = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_training.addMenuItem("Training Type", "location='<%=String.valueOf(approot)%>/masterdata/list_train_type.jsp'");
            fw_menu_training.addMenuItem("Training Venue", "location='<%=String.valueOf(approot)%>/masterdata/list_train_venue.jsp'");        
            fw_menu_training.addMenuItem("Training Program", "location='<%=String.valueOf(approot)%>/masterdata/srctraining.jsp'");   
            fw_menu_training.addMenuItem("Training Plan", "location='<%=String.valueOf(approot)%>/employee/training/training_plan_list.jsp'");
            fw_menu_training.addMenuItem("Training Actual", "location='<%=String.valueOf(approot)%>/employee/training/training_actual_list.jsp'");   
            fw_menu_training.addMenuItem("Training Search", "location='<%=String.valueOf(approot)%>/employee/training/search_training.jsp'");
            fw_menu_training.addMenuItem("Training History", "location='<%=String.valueOf(approot)%>/employee/training/src_training_hist_hr.jsp'");    
            fw_menu_training.childMenuIcon="<%=approot%>/images/arrows.gif"; 
            fw_menu_training.hideOnMouseOut=true;         
         <%//}%>

        /* Employee */
        window.fw_menu_employee = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_employee.addMenuItem("Data Bank", "location='<%=String.valueOf(approot)%>/employee/databank/srcemployee.jsp'");
        fw_menu_employee.addMenuItem("Company Structure", "location='<%=String.valueOf(approot)%>/employee/databank/srcEmployee_structure.jsp'");
        /**
         *Ari_20110910
         *Menambahkan sub menu New Employee List {
         */
        fw_menu_employee.addMenuItem("New Employee", "location='<%=String.valueOf(approot)%>/employee/databank/new_employee_list.jsp'");
        /*}*/
        //update by satrya 2014-02-25
        <%if(mnuOutlet){%>    
        fw_menu_employee.addMenuItem(fw_menu_employee_outlet);
        <%}%>
        fw_menu_employee.addMenuItem(fw_menu_attendance);
        fw_menu_employee.addMenuItem(fw_leave_app);
        fw_menu_employee.addMenuItem(fw_menu_leave_balnc);
        <% if(mnuExcuse){%>
        fw_menu_employee.addMenuItem(fw_excuse_app);
        <%}%>
        
        
        fw_menu_employee.addMenuItem("Absence Management", "location='<%=String.valueOf(approot)%>/employee/absence/srcabsence.jsp'");
        
        <% if(mnuAppraisal){%>
            fw_menu_employee.addMenuItem(fw_menu_appraisal);
            fw_menu_employee.addMenuItem(fw_menu_recognation);
        <%} if(mnuRecruitment){%>                
        fw_menu_employee.addMenuItem(fw_menu_recruitment);
	  <%}%>
        fw_menu_employee.addMenuItem(fw_menu_warning_reprimand);
        <%if(mnuRewardnPunishmentSetting){%>
             fw_menu_employee.addMenuItem(fw_menu_reward_and_punishment_setting);
        <%}%>
             <%if(mnuKoefisionPositionSetting){%>
             fw_menu_employee.addMenuItem(fw_menu_koefision_position_setting);
        <%}%>
            <%if(mnuAdjusmentWorkingDay){%>
             fw_menu_employee.addMenuItem(fw_menu_Adjusment_working_day_setting);
        <%}%>
            
        fw_menu_employee.childMenuIcon="<%=approot%>/images/arrows.gif";
        fw_menu_employee.hideOnMouseOut=true;
		/* Payroll Setup */
                window.fw_menu_pay_setup = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                 <%if(mnuPayrollInput && !mnuPayroll){%>
                     fw_menu_pay_setup.addMenuItem("Payroll Period", "location='<%=String.valueOf(approot)%>/payroll/setup/period.jsp'");
                  <%}else{%>
                      fw_menu_pay_setup.addMenuItem("General", "location='<%=String.valueOf(approot)%>/payroll/setup/general_list.jsp'");
		fw_menu_pay_setup.addMenuItem("Payroll Period", "location='<%=String.valueOf(approot)%>/payroll/setup/period.jsp'");
		fw_menu_pay_setup.addMenuItem("Bank List", "location='<%=String.valueOf(approot)%>/payroll/setup/list-bank.jsp'");
                //update by satrya 2013-01-24
                fw_menu_pay_setup.addMenuItem("Pay Slip Group", "location='<%=String.valueOf(approot)%>/payroll/process/pay_payslip_group.jsp'");
		fw_menu_pay_setup.addMenuItem("Salary Component", "location='<%=String.valueOf(approot)%>/payroll/setup/salary-comp.jsp'");
		fw_menu_pay_setup.addMenuItem("Salary Level", "location='<%=String.valueOf(approot)%>/payroll/setup/salary-level.jsp'");
		fw_menu_pay_setup.addMenuItem("Employee Setup", "location='<%=String.valueOf(approot)%>/payroll/setup/employee-setup.jsp'");
		fw_menu_pay_setup.addMenuItem("Currency", "location='<%=String.valueOf(approot)%>/payroll/setup/currency.jsp'");
		fw_menu_pay_setup.addMenuItem("Currency Rate", "location='<%=String.valueOf(approot)%>/payroll/setup/currency_rate.jsp'");
                   <%}%>
		
		
		//khusus intimas
                ////khusus intimas
		fw_menu_pay_setup.childMenuIcon="<%=approot%>/images/arrows.gif";
		fw_menu_pay_setup.hideOnMouseOut=true;
		

		/* Overtime */
                
		window.fw_menu_pay_ovtm = new Menu("root",200,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_pay_ovtm.addMenuItem("Overtime Form", "location='<%=String.valueOf(approot)%>/payroll/overtimeform/src_overtime.jsp'");
                fw_menu_pay_ovtm.addMenuItem("Overtime Report & Process", "location='<%=String.valueOf(approot)%>/payroll/overtimeform/src_overtime_report.jsp'");                		
                fw_menu_pay_ovtm.addMenuItem("Overtime Index", "location='<%=String.valueOf(approot)%>/payroll/overtime/ov-index.jsp'");                
                fw_menu_pay_ovtm.addMenuItem("Overtime Summary", "location='<%=String.valueOf(approot)%>/payroll/overtimeform/src_overtime_summary.jsp'");                
		fw_menu_pay_ovtm.childMenuIcon="<%=approot%>/images/arrows.gif";
		fw_menu_pay_ovtm.hideOnMouseOut=true;
		

		/* Payroll Process */
                
		window.fw_menu_pay_proc = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                <%if(mnuPayrollInput && !mnuPayroll){%>
                    fw_menu_pay_proc.addMenuItem("Payroll Input", "location='<%=String.valueOf(approot)%>/payroll/process/pay-input.jsp'");
                <%}else{%>
                fw_menu_pay_proc.addMenuItem("Prepare data", "location='<%=String.valueOf(approot)%>/payroll/process/pay-pre-data.jsp'");
		fw_menu_pay_proc.addMenuItem("Payroll Input", "location='<%=String.valueOf(approot)%>/payroll/process/pay-input.jsp'");
                
		fw_menu_pay_proc.addMenuItem("Payroll Process", "location='<%=String.valueOf(approot)%>/payroll/process/pay-process.jsp'");
		fw_menu_pay_proc.addMenuItem("Payslip Printing", "location='<%=String.valueOf(approot)%>/payroll/process/pay-printing.jsp'");
                <%}%>
		fw_menu_pay_proc.childMenuIcon="<%=approot%>/images/arrows.gif";
		fw_menu_pay_proc.hideOnMouseOut=true;
		

		/* Tax  > Tax Setup */
                
                window.fw_menu_tax_setup = new Menu("Tax Setup",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");

		fw_menu_tax_setup.addMenuItem("Executives List", "location='<%=String.valueOf(approot)%>/payroll/setup/nama-pejabat.jsp'");
                fw_menu_tax_setup.addMenuItem("Tax Type", "location='<%=String.valueOf(approot)%>/payroll/setup/jenis-pajak.jsp'");
                fw_menu_tax_setup.addMenuItem("Tax Payment Type", "location='<%=String.valueOf(approot)%>/payroll/setup/jenis-setoran.jsp'");
		fw_menu_tax_setup.addMenuItem("Tax Tariff", "location='<%=String.valueOf(approot)%>/payroll/setup/tax-tariff.jsp'");
                fw_menu_tax_setup.addMenuItem("Format Tax Slip Nr.", "location='<%=String.valueOf(approot)%>/payroll/setup/tax-slip-nr.jsp'");
		fw_menu_tax_setup.addMenuItem("Biaya Pot. Pajak", "location='<%=String.valueOf(approot)%>/payroll/tax/list_salary_level.jsp'");
		fw_menu_tax_setup.addMenuItem("Regulasi_Period", "location='<%=String.valueOf(approot)%>/payroll/tax/regulasi_period.jsp'");
		fw_menu_tax_setup.addMenuItem("Tax PTKP", "location='<%=String.valueOf(approot)%>/payroll/tax/tax_ptkp.jsp'");
                fw_menu_tax_setup.hideOnMouseOut=true;
		

		/* Tax Process */
                
		window.fw_menu_pay_tax = new Menu("root",170,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_pay_tax.addMenuItem(fw_menu_tax_setup);
		fw_menu_pay_tax.addMenuItem("Proses Perhitungan Pajak", "location='<%=String.valueOf(approot)%>/payroll/tax/list_tax.jsp'");
		fw_menu_pay_tax.addMenuItem("Report SPM", "location='<%=String.valueOf(approot)%>/payroll/tax/pay-input.jsp'");
		fw_menu_pay_tax.childMenuIcon="<%=approot%>/images/arrows.gif";
		fw_menu_pay_tax.hideOnMouseOut=true;
		

            /* Master Data > Schedule */
            window.fw_master_schedule = new Menu("Schedule",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_master_schedule.addMenuItem("Period", "location='<%=String.valueOf(approot)%>/masterdata/period.jsp'");
            fw_master_schedule.addMenuItem("Category", "location='<%=String.valueOf(approot)%>/masterdata/schedulecategory.jsp'");
            fw_master_schedule.addMenuItem("Symbol", "location='<%=String.valueOf(approot)%>/masterdata/srcschedulesymbol.jsp'");
            fw_master_schedule.hideOnMouseOut=true;
            //update by devin 2014-04-29
            //master data jenis so
            
            window.fw_master_jenis_so = new Menu("Jenis So",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
             fw_master_jenis_so.addMenuItem("Jenis So", "location='<%=String.valueOf(approot)%>/masterdata/jenisso.jsp'");
              window.fw_master_period_stock_opname = new Menu("Stock Opname",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
             fw_master_period_stock_opname.addMenuItem("Stock Opname", "location='<%=String.valueOf(approot)%>/masterdata/periodstockopname.jsp'");
         
            /* Master Data > Company */
            window.fw_master_company = new Menu("Company",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            /**
             *Ari_20111003
             *Menambah Company di sub masterdata
             */
            fw_master_company.addMenuItem("Company", "location='<%=String.valueOf(approot)%>/masterdata/company.jsp'");
            fw_master_company.addMenuItem("Division", "location='<%=String.valueOf(approot)%>/masterdata/division.jsp'");
            fw_master_company.addMenuItem("Department", "location='<%=String.valueOf(approot)%>/masterdata/department.jsp'");
            fw_master_company.addMenuItem("Position", "location='<%=String.valueOf(approot)%>/masterdata/srcposition.jsp'");
            fw_master_company.addMenuItem("Section", "location='<%=String.valueOf(approot)%>/masterdata/srcsection.jsp'");
            fw_master_company.addMenuItem("Public Holiday", "location='<%=String.valueOf(approot)%>/masterdata/publicHoliday.jsp'");
            fw_master_company.addMenuItem("Leave Target", "location='<%=String.valueOf(approot)%>/masterdata/leaveTarget.jsp'");
            
           
             
            fw_master_company.hideOnMouseOut=true;
            
            window.fw_master_leave_konfiguration= new Menu("Leave Configuration",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
             fw_master_leave_konfiguration.addMenuItem("Leave Configuration", "location='<%=String.valueOf(approot)%>/masterdata/leave_setting.jsp'");
            
 
            /* Master Data > Geographic Area */
            window.fw_master_geoarea = new Menu("Geo Area",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            /**
             *Ari_20111003
             *Menambah Company di sub masterdata
             */
            fw_master_geoarea.addMenuItem("Country", "location='<%=String.valueOf(approot)%>/masterdata/negara.jsp'");
            fw_master_geoarea.addMenuItem("Province", "location='<%=String.valueOf(approot)%>/masterdata/provinsi.jsp'");
            fw_master_geoarea.addMenuItem("Regency", "location='<%=String.valueOf(approot)%>/masterdata/kabupaten.jsp'");
            fw_master_geoarea.addMenuItem("Sub Regency", "location='<%=String.valueOf(approot)%>/masterdata/kecamatan.jsp'");
            fw_master_geoarea.hideOnMouseOut=true;


                /* Employee > Training > Training Activities */
                window.fw_employee_train_act = new Menu("Upload Data",90,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_employee_train_act.addMenuItem("Trainee", "location='<%=String.valueOf(approot)%>/system/excel_up/up_employee_train.jsp'");
                fw_employee_train_act.addMenuItem("Daily Worker", "location='<%=String.valueOf(approot)%>/system/excel_up/up_employee_dw.jsp'");
                fw_employee_train_act.hideOnMouseOut=true;

                /* Employee > Leave Management > Opname DP, AL dan LL */
                window.fw_employee_leave_man = new Menu("Leave Management",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_employee_leave_man.addMenuItem("Day Off Payment", "location='<%=String.valueOf(approot)%>/system/excel_up/up_opname_dp.jsp'");
                fw_employee_leave_man.addMenuItem("Annual Leave", "location='<%=String.valueOf(approot)%>/system/excel_up/up_opname_al.jsp'");
                fw_employee_leave_man.addMenuItem("Long Leave", "location='<%=String.valueOf(approot)%>/system/excel_up/up_opname_ll.jsp'");
                fw_employee_leave_man.hideOnMouseOut=true;
                 
                window.fw_leave_balance = new Menu("Leave Balance",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_leave_balance.addMenuItem("Day Off Payment", "location='<%=String.valueOf(approot)%>/system/leave/opnameDP.jsp'");
                fw_leave_balance.addMenuItem("Annual Leave", "location='<%=String.valueOf(approot)%>/system/leave/opnameAL.jsp'");
                fw_leave_balance.addMenuItem("Long Leave", "location='<%=String.valueOf(approot)%>/system/leave/opnameLL.jsp'");
                fw_leave_balance.addMenuItem("Annual Leave", "location='<%=approot%>/system/leave/opnameAl.jsp'");
                fw_leave_balance.addMenuItem("Long Leave", "location='<%=approot%>/system/leave/opnameLL.jsp'");
                fw_leave_balance.hideOnMouseOut=true;

            /* Master Data > Employee */
            window.fw_master_employee = new Menu("Employee",140,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_master_employee.addMenuItem("Education", "location='<%=String.valueOf(approot)%>/masterdata/education.jsp'");
            /**
             *Ari_13092011
             *Menambahkan sub Menu Family Relation,warning dan reprimand pada masterdata {
             */
            fw_master_employee.addMenuItem("Family Relation", "location='<%=String.valueOf(approot)%>/masterdata/famRelation.jsp'");
            fw_master_employee.addMenuItem("Warning", "location='<%=String.valueOf(approot)%>/masterdata/empwarning.jsp'");
            fw_master_employee.addMenuItem("Reprimand", "location='<%=String.valueOf(approot)%>/masterdata/empreprimand.jsp'");
            /*}*/
            fw_master_employee.addMenuItem("Level", "location='<%=String.valueOf(approot)%>/masterdata/level.jsp'");
            fw_master_employee.addMenuItem("Category", "location='<%=String.valueOf(approot)%>/masterdata/empcategory.jsp'");
            fw_master_employee.addMenuItem("Religion", "location='<%=String.valueOf(approot)%>/masterdata/religion.jsp'");
            fw_master_employee.addMenuItem("Marital", "location='<%=String.valueOf(approot)%>/masterdata/marital.jsp'");
	    fw_master_employee.addMenuItem("Race", "location='<%=String.valueOf(approot)%>/masterdata/race.jsp'");
            fw_master_employee.addMenuItem("Language", "location='<%=String.valueOf(approot)%>/masterdata/masterlanguage.jsp'");
            fw_master_employee.addMenuItem("Image Assign", "location='<%=String.valueOf(approot)%>/masterdata/image_assign.jsp'");
            //fw_master_employee.addMenuItem("Finger Print", "location='<%=String.valueOf(approot)%>/masterdata/fingerPrint.jsp'");
            fw_master_employee.addMenuItem("Resigned Reason", "location='<%=String.valueOf(approot)%>/masterdata/resignedreason.jsp'");
            fw_master_employee.addMenuItem("Award Type", "location='<%=String.valueOf(approot)%>/masterdata/awardtype.jsp'");
           
            fw_master_employee.addMenuItem("Absence Reason", "location='<%=String.valueOf(approot)%>/masterdata/reason.jsp'");
	    fw_master_employee.childMenuIcon="<%=approot%>/images/arrows.gif";
            fw_master_employee.hideOnMouseOut=true;

        /* Locker */
        window.fw_m_locker = new Menu("Locker Data",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_m_locker.addMenuItem("Locker Location", "location='<%=String.valueOf(approot)%>/masterdata/lockerlocation.jsp'");
            fw_m_locker.addMenuItem("Locker Condition", "location='<%=String.valueOf(approot)%>/masterdata/lockercondition.jsp'");
            fw_m_locker.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
            fw_m_locker.hideOnMouseOut=true;
            
       /* update by satrya 2014-02-24 location */
       <%if(mnLocationOutlet){%>
       window.fw_m_location = new Menu("Location",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_m_location.addMenuItem("Location Outlet", "location='<%=String.valueOf(approot)%>/masterdata/location.jsp'");
            fw_m_location.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
            fw_m_location.hideOnMouseOut=true;
       <%}%>     
            /* Master Data > Performance Appraisal */
            <%
	     if(mnuAppraisal){
            if(isAppraisal){
            %>
                window.fw_m_apprsl = new Menu("Performance Appraisal",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_m_apprsl.addMenuItem("Group Rank", "location='<%=String.valueOf(approot)%>/masterdata/grouprank.jsp'");   
                //fw_m_apprsl.addMenuItem("Category Appraisal", "location='<%//=String.valueOf(approot)%>/masterdata/groupcategory.jsp'"); 
                fw_m_apprsl.addMenuItem("Evaluation Criteria", "location='<%=String.valueOf(approot)%>/masterdata/evaluation.jsp'"); 
            <%}else{%>
                window.fw_m_apprsl = new Menu("Assessment",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_m_apprsl.addMenuItem("Group Rank", "location='<%=String.valueOf(approot)%>/masterdata/grouprankHR.jsp'");   
                fw_m_apprsl.addMenuItem("Evaluation Criteria", "location='<%=String.valueOf(approot)%>/masterdata/evaluation.jsp'"); 
                fw_m_apprsl.addMenuItem("Form Creator", "location='<%=String.valueOf(approot)%>/masterdata/assessment/assessmentFormMain.jsp'");  
            <%}%>
            fw_m_apprsl.hideOnMouseOut=true;	 		   	  	  	  
		<% }%>
        /* Master Data */
        window.fw_master_data = new Menu("root",160,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_master_data.addMenuItem(fw_master_company);
        fw_master_data.addMenuItem(fw_master_employee);
        fw_master_data.addMenuItem(fw_master_schedule);
        <%if(mnuLeaveConfig){%>
        fw_master_data.addMenuItem(fw_master_leave_konfiguration);
        <%}%>
        //update by devin 2014-04-30
        <%if(mnuJenisSo){%>
        fw_master_data.addMenuItem(fw_master_jenis_so);
        fw_master_data.addMenuItem(fw_master_period_stock_opname);
        <%}%>
	  <% if(mnuLocker) { %> 
        fw_master_data.addMenuItem(fw_m_locker);
	  <% } if(mnuMsdLocationOutlet){%> 
        fw_master_data.addMenuItem(fw_m_location);
        <%} if(mnuAppraisal){ %>
        fw_master_data.addMenuItem(fw_m_apprsl);
	  <%} if (mnuRecruitment){ %>
        fw_master_data.addMenuItem(fw_menu_recruit_master);
   	  <% }%>
        fw_master_data.addMenuItem(fw_master_geoarea);       
        
        fw_master_data.childMenuIcon="<%=approot%>/images/arrows.gif";
        fw_master_data.hideOnMouseOut=true;


            /* System > User Management */
            window.fw_system_user = new Menu("User Management",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_system_user.addMenuItem("User List", "location='<%=String.valueOf(approot)%>/admin/userlist.jsp'");
            fw_system_user.addMenuItem("Group Privilege", "location='<%=String.valueOf(approot)%>/admin/grouplist.jsp'");
            fw_system_user.addMenuItem("Privilege", "location='<%=String.valueOf(approot)%>/admin/privilegelist.jsp'");
            fw_system_user.addMenuItem("Update Password", "location='<%=String.valueOf(approot)%>/admin/userupdatepasswd.jsp'");
            fw_system_user.addMenuItem("User Compare", "location='<%=String.valueOf(approot)%>/employee/databank/harisma_vs_machine.jsp'");
            fw_system_user.hideOnMouseOut=true;

            /* System > Timekeeping */
            window.fw_system_timekeep = new Menu("Timekeeping",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_system_timekeep.addMenuItem("Service Manager", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/svcmgr.jsp'");
            fw_system_timekeep.hideOnMouseOut=true;
 
            /* System > Barcode */
            window.fw_system_barcode = new Menu("Barcode",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_system_barcode.addMenuItem("Insert & Upload Barcode", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/srcbarcode.jsp'");
            fw_system_barcode.hideOnMouseOut=true;
			
			
            /* System > Working Schedule */
            window.fw_system_work_schdl = new Menu("Working Schedule",170,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_system_work_schdl.addMenuItem("Upload & Insert Schedule", "location='<%=String.valueOf(approot)%>/system/excel_up/up_work_schedule.jsp'");
            fw_system_work_schdl.hideOnMouseOut=true;
			
            /* System > System Management */
            window.fw_system_man = new Menu("System Management",125,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_system_man.addMenuItem("System Properties", "location='<%=String.valueOf(approot)%>/system/index.jsp'");
            fw_system_man.addMenuItem("Login History", "location='<%=String.valueOf(approot)%>/common/logger/history.jsp'");
	    fw_system_man.addMenuItem("System Log", "location='<%=String.valueOf(approot)%>/log/system_log.jsp'");
            fw_system_man.hideOnMouseOut=true;
			
            /* System > Presence */    
            window.fw_system_presence = new Menu("Presence",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_system_presence.addMenuItem("Import Presence", "location='<%=String.valueOf(approot)%>/system/presence/import_presence.jsp'");
            fw_system_presence.addMenuItem("Absence Analyser", "location='<%=String.valueOf(approot)%>/system/presence/absenceservice.jsp'");
            fw_system_presence.addMenuItem("Lateness Analyser", "location='<%=String.valueOf(approot)%>/service/lateness.jsp'");
            fw_system_presence.hideOnMouseOut=true;

            /* System > Service */
            window.fw_system_service = new Menu("Service",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_system_service.addMenuItem("Leave Management", "location='<%=String.valueOf(approot)%>/service/dp_stock.jsp'");
            fw_system_service.hideOnMouseOut=true;

            /* System > Manual checking */
            window.fw_system_manual_proc = new Menu("Manual Process",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	    //fw_system_manual_proc.addMenuItem("Import Presence", "location='<%=String.valueOf(approot)%>/system/presence/import_presence_dbf.jsp'");
	    fw_system_manual_proc.addMenuItem("Presence Checking", "location='<%=String.valueOf(approot)%>/service/check_presence_manual.jsp'");
            fw_system_manual_proc.addMenuItem("Absenteeism Checking", "location='<%=String.valueOf(approot)%>/service/check_absence_manual.jsp'");
            fw_system_manual_proc.addMenuItem("Lateness Checking", "location='<%=String.valueOf(approot)%>/service/check_lateness_manual.jsp'");
            fw_system_manual_proc.hideOnMouseOut=true;
			
	
            /* System */
            window.fw_menu_system = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_system.addMenuItem(fw_system_user);
            fw_menu_system.addMenuItem(fw_system_man);
            //fw_menu_system.addMenuItem(fw_system_barcode);
            fw_menu_system.addMenuItem(fw_system_timekeep);
            fw_menu_system.addMenuItem("Service Center", "location='<%=String.valueOf(approot)%>/service/service_center.jsp'");
            fw_menu_system.addMenuItem("Manual Process", "location='<%=String.valueOf(approot)%>/service/attendance_manual_calculation.jsp'");
           //update by satrya 2013-11-15
           fw_menu_system.addMenuItem("Admin Test Mesin Absence", "location='<%=String.valueOf(approot)%>/system/test_mesin_odbc.jsp'");
            fw_menu_system.addMenuItem("Admin Query Setup", "location='<%=String.valueOf(approot)%>/system/query_setup.jsp'");
            //update by satrya 2014-02-27
           fw_menu_system.addMenuItem("Change Tempalte", "location='<%=String.valueOf(approot)%>/styletemplate/chage_template.jsp'");
            ///styletemplate/chage_template.jsp
		//fw_menu_system.addMenuItem("Import Presence", "location='<%=String.valueOf(approot)%>/system/presence/import_presence_dbf.jsp'");
            fw_menu_system.childMenuIcon="<%=approot%>/images/arrows.gif";
            fw_menu_system.hideOnMouseOut=true;

            /* Reports > Employment */
            window.fw_report_employment = new Menu("Employment",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_employment.addMenuItem("Staff Recapitulation", "location='<%=String.valueOf(approot)%>/report/employment/staffrecapitulation.jsp'");
            fw_report_employment.hideOnMouseOut=true;

            /* Reports > Leave & DP Record */
            window.fw_report_leave = new Menu("Leave & DP Record",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_leave.addMenuItem("General", "location='<%=String.valueOf(approot)%>/report/leavedp/general.jsp'");
            fw_report_leave.addMenuItem("Detail", "location='<%=String.valueOf(approot)%>/report/leavedp/detail.jsp'");
            fw_report_leave.hideOnMouseOut=true;

            /* Reports > Staff Control */
            window.fw_report_staff_ctrl = new Menu("Staff Control",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_staff_ctrl.addMenuItem("Attendance Record", "location='<%=String.valueOf(approot)%>/employee/presence/att_record_monthly.jsp'");
            fw_report_staff_ctrl.hideOnMouseOut=true;
			
			/* Reports > Presence */
            window.fw_report_presence = new Menu("Presence",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_presence.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/presence/presence_report_daily.jsp'");
            fw_report_presence.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/presence/weekly_presence.jsp'");
            fw_report_presence.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/presence/monthly_presence.jsp'");
            fw_report_presence.addMenuItem("Year Report", "location='<%=String.valueOf(approot)%>/report/presence/year_report_presence.jsp'");
            //update by satrya 2012-12-11
            fw_report_presence.addMenuItem("Attendance Summary", "location='<%=String.valueOf(approot)%>/report/presence/summary_attendance_sheet.jsp'");
            //fw_report_presence.addMenuItem("Attendance Summary", "location='<%=String.valueOf(approot)%>/report/presence/presence_summary_sheet.jsp'");
            //fw_report_presence.addMenuItem("Attendance Sum", "location='<%=String.valueOf(approot)%>/report/presence/presence_summary_sheet_old.jsp'");
            fw_report_presence.hideOnMouseOut=true;

            /* Reports > Lateness */
            window.fw_report_lateness = new Menu("Lateness",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_lateness.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/lateness/lateness_report.jsp'");
            fw_report_lateness.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/lateness/lateness_weekly_report.jsp'");
            fw_report_lateness.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/lateness/lateness_monthly_report.jsp'");
            fw_report_lateness.hideOnMouseOut=true;
			
            /* Reports > Split Shift */
            window.fw_report_split_shift = new Menu("Split Shift",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_report_split_shift.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/splitshift/daily_split.jsp'");
		fw_report_split_shift.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/splitshift/weekly_split.jsp'");
                fw_report_split_shift.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/splitshift/monthly_split.jsp'");
                fw_report_split_shift.hideOnMouseOut=true;
			
			/* Reports > Night Shift */ 
            window.fw_report_night_shift = new Menu("Night Shift",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_night_shift.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/nightshift/daily_night.jsp'");
            fw_report_night_shift.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/nightshift/weekly_night.jsp'");
            fw_report_night_shift.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/nightshift/monthly_night.jsp'");
            fw_report_night_shift.hideOnMouseOut=true;
			
			/* Reports > Absenteeism */ 
            window.fw_report_absent = new Menu("Absenteeism",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_absent.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/absenteeism/daily_absence.jsp'");
            fw_report_absent.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/absenteeism/weekly_absence.jsp'");
            fw_report_absent.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/absenteeism/monthly_absence.jsp'");
            fw_report_absent.hideOnMouseOut=true;

			/* Reports > Sickness */ 
            window.fw_report_sickness = new Menu("Sickness",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_sickness.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/sickness/daily_sickness.jsp'");
            fw_report_sickness.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/sickness/weekly_sickness.jsp'");
            fw_report_sickness.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/sickness/monthly_sickness.jsp'");
            fw_report_sickness.addMenuItem("Zero Sickness Report", "location='<%=String.valueOf(approot)%>/report/sickness/zero_sickness.jsp'");
            fw_report_sickness.hideOnMouseOut=true;

			/* Reports > Special Dispensation */ 
            window.fw_report_special_disp = new Menu("Special Dispensation",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_special_disp.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/specialdisp/daily_specialdisp.jsp'");
            fw_report_special_disp.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/specialdisp/weekly_specialdisp.jsp'");
            fw_report_special_disp.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/specialdisp/monthly_specialdisp.jsp'");
            fw_report_special_disp.hideOnMouseOut=true;

            /* Reports > Attendance */
            window.fw_report_attendance = new Menu("Leave Report",160,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_attendance.addMenuItem("Leave & DP Summary ", "location='<%=String.valueOf(approot)%>/report/leavedp/leave_dp_sum.jsp'");
            fw_report_attendance.addMenuItem("Leave & DP Detail ", "location='<%=String.valueOf(approot)%>/report/leavedp/leave_dp_detail.jsp'");
            fw_report_attendance.addMenuItem("Leave & DP Sum. Period", "location='<%=String.valueOf(approot)%>/report/leavedp/leave_department_by_period.jsp'");
            fw_report_attendance.addMenuItem("Leave & DP Detail Period", "location='<%=String.valueOf(approot)%>/report/leavedp/leave_dp_detail_period.jsp'");
            fw_report_attendance.addMenuItem("Special & Unpaid Period", "location='<%=String.valueOf(approot)%>/employee/leave/leave_sp_period.jsp'");
            fw_report_attendance.addMenuItem("DP Expired", "location='<%=String.valueOf(approot)%>/report/attendance/dpexp_report.jsp'");
          
                
            /** fw_report_attendance.addMenuItem("Day Off Payment Report", "location='<%=String.valueOf(approot)%>/report/attendance/dp_report.jsp'");
            fw_report_attendance.addMenuItem("DP Expiration Report", "location='<%=String.valueOf(approot)%>/report/attendance/dpexp_report.jsp'");
            fw_report_attendance.addMenuItem("Annual Leave Report", "location='<%=String.valueOf(approot)%>/report/attendance/annualleave_report.jsp'");
            fw_report_attendance.addMenuItem("Long Leave Report", "location='<%=String.valueOf(approot)%>/report/attendance/longleave_report.jsp'");
            fw_report_attendance.addMenuItem("Unpaid Leave Report", "location='<%=String.valueOf(approot)%>/report/attendance/unpaid_leave.jsp'");
            fw_report_attendance.addMenuItem("Month End Report", "location='<%=String.valueOf(approot)%>/report/dailyon/dailyon_montly.jsp'");
                **/
            fw_report_attendance.hideOnMouseOut=true;
			
			
	    /* Reports > Presence */
            window.fw_report_trainee = new Menu("Trainee",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_trainee.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/training/monthly_tr_rpt.jsp'");
            fw_report_trainee.addMenuItem("End Period", "location='<%=String.valueOf(approot)%>/report/training/end_tr_rpt.jsp'");
            fw_report_trainee.hideOnMouseOut=true;
			
			// Reports > Employee */
            window.fw_report_employee = new Menu("Employee",250,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_report_employee.addMenuItem("List Of Employee Category", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_category.jsp'");
	    fw_report_employee.addMenuItem("List Of Employee Resignation", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_resignation1.jsp'");
	    fw_report_employee.addMenuItem("List Of Employee Education", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_education.jsp'");
	    fw_report_employee.addMenuItem("List Of Employee Category By Name", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_by_Name.jsp'");
	    fw_report_employee.addMenuItem("List Number Of Absences ", "location='<%=String.valueOf(approot)%>/report/employee/list_absence_reason.jsp'");
	    //fw_report_employee.addMenuItem("List Of Employee Leave ", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_leave.jsp'");
            fw_report_employee.addMenuItem("List Of Employee Race ", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_race.jsp'");
            fw_report_employee.hideOnMouseOut=true;
			/* Report > Payroll > Overtime Report */
                        
                window.fw_report_pay_ovt = new Menu("Overtime Report",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_report_pay_ovt.addMenuItem("Working Day", "location='<%=String.valueOf(approot)%>/report/payroll/ovt_report.jsp'");
                fw_report_pay_ovt.addMenuItem("Overtime Payment", "location='<%=String.valueOf(approot)%>/report/payroll/upah_ovt.jsp'");
                fw_report_pay_ovt.hideOnMouseOut=true;
                        
			// Reports > Payroll */
                
                window.fw_report_pay = new Menu("Payroll",200,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                    //fw_report_pay.addMenuItem(fw_report_pay_ovt);
                    //fw_report_pay.addMenuItem("Meal Allowance", "location='<%=String.valueOf(approot)%>/report/payroll/uang_makan.jsp'");
                    fw_report_pay.addMenuItem("List Of Salary Summary", "location='<%=String.valueOf(approot)%>/report/payroll/gaji_transfer.jsp'");
                    //fw_report_pay.addMenuItem("Ekspor Allowance Report", "location='<%=String.valueOf(approot)%>/report/payroll/tunj_ekspor.jsp'");
                    //fw_report_pay.addMenuItem("List Of Employee Salary", "location='<%=String.valueOf(approot)%>/report/payroll/list_salary.jsp'");
                    //fw_report_pay.addMenuItem("Staff's Allowance Report", "location='<%=String.valueOf(approot)%>/report/payroll/list_allowance.jsp'");
                    //fw_report_pay.addMenuItem("Daily Worker Report", "location='<%=String.valueOf(approot)%>/report/payroll/dw_report.jsp'");
                    fw_report_pay.childMenuIcon="<%=approot%>/images/arrows.gif";
                    fw_report_pay.hideOnMouseOut=true;
                 
        /* Clinic */
		 /* Clinic > Medicine */
            window.fw_clinic_medicine = new Menu("Medicine",170,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_clinic_medicine.addMenuItem("List Of Medicine", "location='<%=String.valueOf(approot)%>/clinic/stock/medicine.jsp'");
            fw_clinic_medicine.addMenuItem("Medicine Consumption", "location='<%=String.valueOf(approot)%>/clinic/stock/medconsump.jsp'");
            fw_clinic_medicine.hideOnMouseOut=true;

            /* Clinic > Disease */ 
            window.fw_clinic_disease = new Menu("Disease",100,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_clinic_disease.addMenuItem("Type", "location='<%=String.valueOf(approot)%>/clinic/disease/diseasetype.jsp'");
            fw_clinic_disease.hideOnMouseOut=true;

            /* Clinic > Medical Expense */
            window.fw_clinic_medical_exp = new Menu("Medical",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_clinic_medical_exp.addMenuItem("Medical Level", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_level.jsp'");
            fw_clinic_medical_exp.addMenuItem("Medical Case", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_case.jsp'");
            fw_clinic_medical_exp.addMenuItem("Medical Budget", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_budget.jsp'");
            fw_clinic_medical_exp.addMenuItem("Group", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_group.jsp'");
            fw_clinic_medical_exp.addMenuItem("Type", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_type.jsp'");
            //fw_menu_3_2.addMenuItem("Expense Recapitulation", "location='<%=String.valueOf(approot)%>/clinic/medexpense/exp_rec.jsp'");
            //fw_clinic_medical_exp.addMenuItem("Medical Record", "location='<%=String.valueOf(approot)%>/clinic/disease/scrmedicalrecord.jsp'");
            fw_clinic_medical_exp.hideOnMouseOut=true;
        
        
        window.fw_menu_clinic = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        // Ari
        //fw_menu_clinic.addMenuItem("Employee & Family", "location='<%=approot%>/clinic/srcemployeefam.jsp'");
        fw_menu_clinic.addMenuItem("Employee & Family", "location='<%=String.valueOf(approot)%>/clinic/srcemployeefam.jsp'");
        fw_menu_clinic.addMenuItem("Medical Record", "location='<%=String.valueOf(approot)%>/clinic/disease/scrmedicalrecord.jsp'");
        fw_menu_clinic.addMenuItem(fw_clinic_medicine);
        fw_menu_clinic.addMenuItem("Employee Visit", "location='<%=String.valueOf(approot)%>/clinic/empvisit/srcemployeevisit.jsp'");
        fw_menu_clinic.addMenuItem("Guest Handling", "location='<%=String.valueOf(approot)%>/clinic/guest/srcguesthandling.jsp'");
        fw_menu_clinic.addMenuItem(fw_clinic_disease);
        fw_menu_clinic.addMenuItem(fw_clinic_medical_exp);
        fw_menu_clinic.childMenuIcon="<%=approot%>/images/arrows.gif";
        fw_menu_clinic.hideOnMouseOut=true;


        /* Locker */
        window.fw_menu_locker = new Menu("root",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_locker.addMenuItem("Locker", "location='<%=String.valueOf(approot)%>/locker/srclocker.jsp'");
        fw_menu_locker.addMenuItem("Locker Treatment", "location='<%=String.valueOf(approot)%>/locker/srclockertreatment.jsp'");
        fw_menu_locker.childMenuIcon="<%=approot%>/images/arrows.gif";
        fw_menu_locker.hideOnMouseOut=true;


        /* Reports */
        window.fw_menu_report = new Menu("root",140,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_report.addMenuItem(fw_report_staff_ctrl);
        fw_menu_report.addMenuItem(fw_report_presence);
        fw_menu_report.addMenuItem(fw_report_lateness);
        fw_menu_report.addMenuItem(fw_report_split_shift);
	fw_menu_report.addMenuItem(fw_report_night_shift);
	fw_menu_report.addMenuItem(fw_report_absent);
	fw_menu_report.addMenuItem(fw_report_sickness);
	fw_menu_report.addMenuItem(fw_report_special_disp);
        fw_menu_report.addMenuItem(fw_report_attendance);
	fw_menu_report.addMenuItem(fw_report_trainee);
	fw_menu_report.addMenuItem(fw_report_employee);
        fw_menu_report.addMenuItem(fw_menu_training_rpt);
	fw_menu_report.addMenuItem(fw_report_pay);
        fw_menu_report.childMenuIcon="<%=approot%>/images/arrows.gif";
        fw_menu_report.hideOnMouseOut=true;
        fw_menu_report.writeMenus();
    } 

    function MM_jumpMenu(targ,selObj,restore){ 
        eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
        if (restore) selObj.selectedIndex=0;
    }
</script>


<script language="JavaScript"> 
    function cordYMenuEmployee(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_employee,cordX,posY);
    }

   function cordYMenuTraining(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.window.fw_menu_training,cordX,posY);
    }

    function cordYMenuReport(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_report,cordX,posY);
    }
    function cordYMenuClinic(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_clinic,cordX,posY);
    }

   function cordYMenuLocker(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_locker,cordX,posY);
    }	

    function cordYMenuMaster(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_master_data,cordX,posY);
    }	

   
    function cordYMenuSystem(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_system,cordX,posY);
    }

   function cordYMenuPaySetup(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_pay_setup,cordX,posY);
    }		
    
    function cordYMenuPayOvtime(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_pay_ovtm,cordX,posY);
    }
	
    function cordYMenuPayProcess(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_pay_proc,cordX,posY);
    }	
	
    function cordYMenuPayTax(cordX){
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_pay_tax,cordX,posY);
    }
    
</script>  	  		  

<script language=JavaScript src="<%=approot%>/main/fw_menu.js"></script>

<script language=JavaScript1.2>fwLoadMenus();</script>

<input type="hidden" id="xEmployee">
<input type="hidden" id="xTraining">
<input type="hidden" id="xReports">
<input type="hidden" id="xCanteen">
<input type="hidden" id="xClinic">
<input type="hidden" id="xLocker">
<input type="hidden" id="xMaster">
<input type="hidden" id="xSystem">
<input type="hidden" id="xPaySetup">
<input type="hidden" id="xOvTime">
<input type="hidden" id="xPayProcess">
<input type="hidden" id="xTax">

<script>
    function setScr(){
	document.all.xEmployee.value = document.all.divEmployee.offsetLeft + 1;
        <%if(mnuTraining) { %>        
	document.all.xTraining.value = document.all.divTraining.offsetLeft + 1;
        <%} %>
	document.all.xReports.value = document.all.divReports.offsetLeft + 1;
       <%if(mnuCanteen){%> 
	document.all.xCanteen.value = document.all.divCanteen.offsetLeft + 1;
       <%}%>
        <%if(mnuMedical==true){ %>
	document.all.xClinic.value = document.all.divClinic.offsetLeft + 1;
        <%} if(mnuLocker) {%>
	document.all.xLocker.value = document.all.divLocker.offsetLeft + 1;
	<% }%>
	document.all.xMaster.value = document.all.divMaster.offsetLeft + 1;
	document.all.xSystem.value = document.all.divSystem.offsetLeft + 1;
        <% if(mnuPayroll || mnuPayrollInput) { %>
	document.all.xPaySetup.value = document.all.divPaySetup.offsetLeft + 1;
        <% } 
        if(mnuOvertime){ %>
	document.all.xOvTime.value = document.all.divOvTime.offsetLeft + 1;
        <% } 
        if(mnuPayroll || mnuPayrollInput) {%>
        document.all.xPayProcess.value = document.all.divPayProcess.offsetLeft + 1;
        document.all.xTax.value = document.all.divTax.offsetLeft + 1;
        <%}%>
    }
    window.onload = setScr;
    window.onresize = setScr;
	
function showHelp(){
	window.open("<%=approot%>/manual/users_manual.htm","helpPage","height=600,width=800,status=no,scrollbars=yes,toolbar=yes,menubar=yes,location=no");
}		
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="10">
  <tr>  
    <td nowrap height="10" valign="middle"> 
      <div align="center"><b>
              <% if(TYPE_CONFIG!= TYPE_ATTENDANCE_TRANSFER_ONLY ){%>
              
        <a style="text-decoration:none" id="divHome" href="<%=approot%>/home.jsp"><font color="#30009D">Home</font></a>
      | <a style="text-decoration:none" id="divEmployee" href="#" onclick="javascript:cordYMenuEmployee(document.all.xEmployee.value);"><font color="#30009D">Employee</font></a>
      <%if(mnuTraining) { %>
      | <a style="text-decoration:none" id="divTraining" href="#" onClick="javascript:cordYMenuTraining(document.all.xTraining.value);"><font color="#30009D"><%=dictionaryD.getWord(I_Dictionary.TRAINING) %></font></a>
      <% } %>
      | <a style="text-decoration:none" id="divReports" href="#" onclick="javascript:cordYMenuReport(document.all.xReports.value);"><font color="#30009D">Reports</font></a>
      <%if(mnuCanteen){%> 
      | <a style="text-decoration:none" id="divCanteen" href="<%=approot%>/harisma-canteen/home.jsp?menu=1"><font color="#30009D">Canteen</font></a>
      <%}%>
     <%if(mnuMedical==true){ %>
      | <a style="text-decoration:none" id="divClinic" href="#" onclick="javascript:cordYMenuClinic(document.all.xClinic.value);"><font color="#30009D">Clinic</font></a>
	<%} if(mnuLocker){ %>
      | <a style="text-decoration:none" id="divLocker" href="#" onclick="javascript:cordYMenuLocker(document.all.xLocker.value);"><font color="#30009D">Locker</font></a>
     <% } %>
      | <a style="text-decoration:none" id="divMaster" href="#" onclick="javascript:cordYMenuMaster(document.all.xMaster.value);"><font color="#30009D">Master Data</font></a>
      | <a style="text-decoration:none" id="divSystem" href="#" onclick="javascript:cordYMenuSystem(document.all.xSystem.value);"><font color="#30009D">System</font></a>
     <% if(mnuPayroll || mnuPayrollInput) { %>
      | <a style="text-decoration:none" id="divPaySetup" href="#" onClick="javascript:cordYMenuPaySetup(document.all.xPaySetup.value);"><font color="#30009D">Payroll Setup</font></a>
      <%}
      if(mnuOvertime) { %>
      | <a style="text-decoration:none" id="divOvTime" href="#" onClick="javascript:cordYMenuPayOvtime(document.all.xOvTime.value);"><font color="#30009D">Overtime</font></a>
      <%}
     if(mnuPayroll || mnuPayrollInput) {           
      %>
      | <a style="text-decoration:none" id="divPayProcess" href="#" onClick="javascript:cordYMenuPayProcess(document.all.xPayProcess.value);"><font color="#30009D">Payroll Process</font></a>      
      <!-- | <a style="text-decoration:none" id="divTax" href="#" onClick="javascript:cordYMenuPayTax(document.all.xTax.value);"><font color="#30009D">Tax </font></a> -->
     <% } %>
      | <a style="text-decoration:none" id="divMenu9" href="#" onclick="javascript:showHelp()"><font color="#30009D">Help</font></a> 
      | <a style="text-decoration:none" id="divMenu8" href="<%=approot%>/logout.jsp"><font color="#30009D">Logout</font></a> 
      <% } else { %>
         <a href="<%=String.valueOf(approot)%>/system/timekeepingpro/svcmgr.jsp" >Attendance Download Service </a>
      <%} %>
          </b></div>
     </td> 
    </tr>
</table>