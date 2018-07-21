<% boolean menuPayroll=true; %>
<script language=JavaScript>
   /* This function used to make pulldown menu only */
    function fwLoadMenus() {
        
        <%
        int TYPE_HARDROCK       = 0;
        int TYPE_NIKKO          = 1;
        int TYPE_SANUR_PARADISE = 2;        
        int TYPE_INTIMAS        = 3;
        
        int TYPE_CONFIG =  TYPE_INTIMAS;
        boolean isDefault = false; 
        boolean mnuTraining=true;
        boolean mnuMedical = true;
        %>
            
        if (window.fw_menu_0) return;	  	  	  		  		 				

                /* Employee > Attendance > Presence */
            window.fw_menu_0_1_1 = new Menu("Presence",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_0_1_1.addMenuItem("Manual Registration", "location='<%=String.valueOf(approot)%>/employee/presence/srcpresence.jsp'");
                fw_menu_0_1_1.addMenuItem("View Presence", "location='<%=String.valueOf(approot)%>/employee/presence/srcviewpresence.jsp'");
                fw_menu_0_1_1.hideOnMouseOut=true;	 		   	  	  	  

            /* Employee > Attendance */
            window.fw_menu_0_1 = new Menu("Attendance",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_0_1.addMenuItem("Working Schedule", "location='<%=String.valueOf(approot)%>/employee/attendance/srcempschedule.jsp'");
                fw_menu_0_1.addMenuItem("Manual Registration", "location='<%=String.valueOf(approot)%>/employee/presence/srcpresence.jsp'");
                fw_menu_0_1.childMenuIcon="<%=approot%>/images/arrows.gif"; 
                fw_menu_0_1.hideOnMouseOut=true;	  

			/* Employee > Leave Management */
			//window.fw_menu_0_2 = new Menu("Leave Management",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
			//fw_menu_0_2.addMenuItem("DP Management", "location='<%=String.valueOf(approot)%>/employee/attendance/dp.jsp'");
			//fw_menu_0_2.addMenuItem("AL Management", "location='<%=String.valueOf(approot)%>/employee/attendance/annualleave.jsp'");
			//fw_menu_0_2.addMenuItem("LL Management", "location='<%=String.valueOf(approot)%>/employee/attendance/longleave.jsp'");                                                         
                             			//fw_menu_0_2.hideOnMouseOut=true;	 		   	  	  	  

			/* Employee > Leave Management */
			window.fw_menu_0_3 = new Menu("Leave Application",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                        fw_menu_0_3.addMenuItem("Leave Form", "location='<%=String.valueOf(approot)%>/employee/leave/leave_app_src.jsp'");			                             
                        fw_menu_0_3.addMenuItem("Leave Al Closing", "location='<%=String.valueOf(approot)%>/employee/leave/leave_al_closing.jsp'");			         
                        fw_menu_0_3.addMenuItem("Leave Ll Closing", "location='<%=String.valueOf(approot)%>/employee/leave/leave_ll_closing.jsp'");			         
                        fw_menu_0_3.addMenuItem("DP Management", "location='<%=String.valueOf(approot)%>/employee/attendance/dp.jsp'");    
			
                        //fw_menu_0_3.addMenuItem("DP Application", "location='<%=String.valueOf(approot)%>/employee/leave/src_dp_application.jsp'");
			//fw_menu_0_3.addMenuItem("Leave Application", "location='<%=String.valueOf(approot)%>/employee/leave/src_leave_application_hr.jsp'");			
			//fw_menu_0_3.addMenuItem("LL Application", "location='<%=String.valueOf(approot)%>/employee/leave/src_ll_application.jsp'");			
                        //fw_menu_0_3.addMenuItem("Leave App", "location='<%=String.valueOf(approot)%>/employee/leave/leave_application.jsp'");			     
			fw_menu_0_3.hideOnMouseOut=true;
                         
                         
                        window.fw_menu_0_4 = new Menu("Leave Balancing",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084"); 
                        fw_menu_0_4.addMenuItem("Annual Leave", "location='<%=String.valueOf(approot)%>/system/leave/AL_Balancing.jsp'");
                        fw_menu_0_4.addMenuItem("Long Leave", "location='<%=String.valueOf(approot)%>/system/leave/LL_Balancing.jsp'");    
                        fw_menu_0_4.addMenuItem("Day Off Payment", "location='<%=String.valueOf(approot)%>/system/leave/DP_Balancing.jsp'"); 
                        fw_menu_0_4.hideOnMouseOut=true;  
                        
            /* Employee > Appraisal */
            window.fw_menu_appraisal = new Menu("Assessment",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_appraisal.addMenuItem("Explanations and Coverage", "location='<%=String.valueOf(approot)%>/employee/appraisal/expcoverage.jsp'");
            //
           <% 
            if(isDefault){  %>
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
              
            /* Employee > Training > Training Activities */
            window.fw_menu_0_5_0 = new Menu("Training Activities",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_0_5_0.addMenuItem("Monthly Planning", "location='<%=String.valueOf(approot)%>/employee/training/training_act_plan_list.jsp'");
            fw_menu_0_5_0.addMenuItem("Actual", "location='<%=String.valueOf(approot)%>/employee/training/training_act_actual_list.jsp'");
            fw_menu_0_5_0.hideOnMouseOut=true;	
			
            /* Employee > Training */
            window.fw_menu_0_5 = new Menu("Training",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_0_5.addMenuItem("Training History", "location='<%=String.valueOf(approot)%>/employee/training/src_training_hist.jsp'");
            fw_menu_0_5.addMenuItem("Special Achievement", "location='<%=String.valueOf(approot)%>/employee/training/src_achieve.jsp'");
            fw_menu_0_5.addMenuItem(fw_menu_0_5_0);
            fw_menu_0_5.addMenuItem("Training Search", "location='<%=String.valueOf(approot)%>/employee/training/src_training_exist.jsp'");
            fw_menu_0_5.childMenuIcon="<%=approot%>/images/arrows.gif"; 
            fw_menu_0_5.hideOnMouseOut=true;	 

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
            
            /* Employee > Warning & Reprimand */
            window.fw_menu_warning_reprimand = new Menu("Warning & Reprimand",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_warning_reprimand.addMenuItem("Warning", "location='<%=String.valueOf(approot)%>/employee/warning/src_warning.jsp'");
            fw_menu_warning_reprimand.addMenuItem("Reprimand", "location='<%=String.valueOf(approot)%>/employee/warning/src_reprimand.jsp'");        
            fw_menu_warning_reprimand.hideOnMouseOut=true;
            
            <%--
            window.fw_menu_tmp_training_master = new Menu("Training Master",210,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_tmp_training_master.addMenuItem("Training Type", "location='<%=String.valueOf(approot)%>/employee/training/list_train_type.jsp'");
            fw_menu_tmp_training_master.addMenuItem("Training Venue", "location='<%=String.valueOf(approot)%>/employee/training/list_train_venue.jsp'");        
            fw_menu_tmp_training_master.addMenuItem("Training Master", "location='<%=String.valueOf(approot)%>/masterdata/srctraining.jsp'");        
            fw_menu_tmp_training_master.hideOnMouseOut=true;
            
            window.fw_menu_tmp_training_activity = new Menu("Training Activity",200,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_tmp_training_activitytraining.addMenuItem("Training Plan", "location='<%=String.valueOf(approot)%>/employee/training/training_plan_list.jsp'");
            fw_menu_tmp_training_activity.addMenuItem("Training Actual", "location='<%=String.valueOf(approot)%>/employee/training/training_actual_list.jsp'");        
            fw_menu_tmp_training_activity.hideOnMouseOut=true;
            
            window.fw_menu_tmp_training_search = new Menu("Training Search",200,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_tmp_training_search.addMenuItem("Training Search", "location='<%=String.valueOf(approot)%>/employee/training/src_training_exist.jsp'");
            fw_menu_tmp_training_search.addMenuItem("Training History", "location='<%=String.valueOf(approot)%>/employee/training/src_training_hist.jsp'");    
            fw_menu_tmp_training_search.addMenuItem("Special Achievements", "location='<%=String.valueOf(approot)%>/employee/training/src_achieve.jsp'");        
            fw_menu_tmp_training_search.hideOnMouseOut=true;
            
            window.fw_menu_tmp_training = new Menu("Training",200,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_tmp_training.addMenuItem(fw_menu_tmp_training_master);
            fw_menu_tmp_training.addMenuItem(fw_menu_tmp_training_activity);
            fw_menu_tmp_training.addMenuItem(fw_menu_tmp_training_search);
            fw_menu_tmp_training.hideOnMouseOut=true;
            /**** Employee > End Temp Training ****/--%>
            
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
            //fw_menu_training.addMenuItem("Special Achievements", "location='<%=String.valueOf(approot)%>/employee/training/src_achieve.jsp'");        
            //fw_menu_training.addMenuItem(fw_menu_training_rpt);
            fw_menu_training.childMenuIcon="<%=approot%>/images/arrows.gif"; 
            fw_menu_training.hideOnMouseOut=true;         

            /* Employee > Recruitment */
            window.fw_menu_recruitment = new Menu("Recruitment",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_recruitment.addMenuItem("Staff Requisition", "location='<%=String.valueOf(approot)%>/employee/recruitment/srcstaffrequisition.jsp'");
            fw_menu_recruitment.addMenuItem("Employment Application", "location='<%=String.valueOf(approot)%>/employee/recruitment/srcrecrapplication.jsp'");
            fw_menu_recruitment.addMenuItem("Orientation Checklist", "location='<%=String.valueOf(approot)%>/employee/recruitment/srcorichecklist.jsp'");
            fw_menu_recruitment.addMenuItem("Reminder", "location='<%=String.valueOf(approot)%>/employee/recruitment/reminder.jsp'");
            //fw_menu_recruitment.addMenuItem(fw_menu_recruit_master);
            fw_menu_recruitment.childMenuIcon="<%=approot%>/images/arrows.gif"; 
            fw_menu_recruitment.hideOnMouseOut=true;	 

        /* Employee */
        window.fw_menu_0 = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_0.addMenuItem("Data Bank", "location='<%=String.valueOf(approot)%>/employee/databank/srcemployee.jsp'"); 
        fw_menu_0.addMenuItem(fw_menu_0_1);
        //fw_menu_0.addMenuItem(fw_menu_0_2);		
        fw_menu_0.addMenuItem(fw_menu_0_3);
        fw_menu_0.addMenuItem(fw_menu_0_4);
        fw_menu_0.addMenuItem("Absence Management", "location='<%=String.valueOf(approot)%>/employee/absence/srcabsence.jsp'");								
        //fw_menu_0.addMenuItem(fw_menu_0_5);
        <% //Khusus untuk menu di Hardrock
        if(TYPE_CONFIG == TYPE_HARDROCK){
        %>    
        fw_menu_0.addMenuItem(fw_menu_appraisal);
        <%}%>
        
        fw_menu_0.addMenuItem(fw_menu_recognation);
        fw_menu_0.addMenuItem(fw_menu_recruitment);
        fw_menu_0.addMenuItem(fw_menu_warning_reprimand);
        //fw_menu_0.addMenuItem(fw_menu_tmp_training);
        fw_menu_0.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_0.hideOnMouseOut=true;	 

		/* Payroll Setup */
		window.fw_menu_11 = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_11.addMenuItem("General", "location='<%=String.valueOf(approot)%>/payroll/setup/general_list.jsp'");
		fw_menu_11.addMenuItem("Payroll Period", "location='<%=String.valueOf(approot)%>/payroll/setup/period.jsp'");
		fw_menu_11.addMenuItem("Bank List", "location='<%=String.valueOf(approot)%>/payroll/setup/list-bank.jsp'");
		fw_menu_11.addMenuItem("Salary Component", "location='<%=String.valueOf(approot)%>/payroll/setup/salary-comp.jsp'");
		fw_menu_11.addMenuItem("Salary Level", "location='<%=String.valueOf(approot)%>/payroll/setup/salary-level.jsp'");
		//fw_menu_11.addMenuItem("Format Report", "location='<%=String.valueOf(approot)%>/payroll/setup/format-report.jsp'");
		//fw_menu_11.addMenuItem("Format Tax Slip Nr.", "location='<%=String.valueOf(approot)%>/payroll/setup/tax-slip-nr.jsp'");
		fw_menu_11.addMenuItem("Employee Setup", "location='<%=String.valueOf(approot)%>/payroll/setup/employee-setup.jsp'");
		fw_menu_11.addMenuItem("Currency", "location='<%=String.valueOf(approot)%>/payroll/setup/currency.jsp'");
		fw_menu_11.addMenuItem("Currency Rate", "location='<%=String.valueOf(approot)%>/payroll/setup/currency_rate.jsp'");
		//khusus intimas
		fw_menu_11.addMenuItem("Procentase Presence", "location='<%=String.valueOf(approot)%>/payroll/setup/procentase_presence.jsp'");
		fw_menu_11.addMenuItem("Summary Additional", "location='<%=String.valueOf(approot)%>/payroll/setup/summary_additional.jsp'");
		// khsusus intimas
		fw_menu_11.childMenuIcon="<%=approot%>/images/arrows.gif"; 
		fw_menu_11.hideOnMouseOut=true;	 
		
		/* Overtime */
		window.fw_menu_12 = new Menu("root",140,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_12.addMenuItem("Overtime Index", "location='<%=String.valueOf(approot)%>/payroll/overtime/ov-index.jsp'");
		fw_menu_12.addMenuItem("Import from Presence", "location='<%=String.valueOf(approot)%>/payroll/overtime/ov-import-present.jsp'");
		fw_menu_12.addMenuItem("Input Overtime", "location='<%=String.valueOf(approot)%>/payroll/overtime/ov-input.jsp'");
		fw_menu_12.addMenuItem("Posting Overtime", "location='<%=String.valueOf(approot)%>/payroll/overtime/ov-posting.jsp'");
		fw_menu_12.childMenuIcon="<%=approot%>/images/arrows.gif"; 
		fw_menu_12.hideOnMouseOut=true;	 
		
		/* Payroll Process */
		window.fw_menu_13 = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_13.addMenuItem("Prepare data", "location='<%=String.valueOf(approot)%>/payroll/process/pay-pre-data.jsp'");
		fw_menu_13.addMenuItem("Payroll Input", "location='<%=String.valueOf(approot)%>/payroll/process/pay-input.jsp'");
		fw_menu_13.addMenuItem("Payroll Process", "location='<%=String.valueOf(approot)%>/payroll/process/pay-process.jsp'");
		fw_menu_13.addMenuItem("Payslip Printing", "location='<%=String.valueOf(approot)%>/payroll/process/pay-printing.jsp'");
		fw_menu_13.childMenuIcon="<%=approot%>/images/arrows.gif"; 
		fw_menu_13.hideOnMouseOut=true;	 
		
		/* Tax  > Tax Setup */
                window.fw_menu_0_1_14 = new Menu("Tax Setup",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");

		fw_menu_0_1_14.addMenuItem("Executives List", "location='<%=String.valueOf(approot)%>/payroll/setup/nama-pejabat.jsp'");
                fw_menu_0_1_14.addMenuItem("Tax Type", "location='<%=String.valueOf(approot)%>/payroll/setup/jenis-pajak.jsp'");
                fw_menu_0_1_14.addMenuItem("Tax Payment Type", "location='<%=String.valueOf(approot)%>/payroll/setup/jenis-setoran.jsp'");
		fw_menu_0_1_14.addMenuItem("Tax Tariff", "location='<%=String.valueOf(approot)%>/payroll/setup/tax-tariff.jsp'");
                fw_menu_0_1_14.addMenuItem("Format Tax Slip Nr.", "location='<%=String.valueOf(approot)%>/payroll/setup/tax-slip-nr.jsp'");
		fw_menu_0_1_14.addMenuItem("Biaya Pot. Pajak", "location='<%=String.valueOf(approot)%>/payroll/tax/list_salary_level.jsp'");
		fw_menu_0_1_14.addMenuItem("Regulasi_Period", "location='<%=String.valueOf(approot)%>/payroll/tax/regulasi_period.jsp'");
		fw_menu_0_1_14.addMenuItem("Tax PTKP", "location='<%=String.valueOf(approot)%>/payroll/tax/tax_ptkp.jsp'");
                fw_menu_0_1_14.hideOnMouseOut=true;	 
		
		/* Tax Process */
		window.fw_menu_14 = new Menu("root",170,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_14.addMenuItem(fw_menu_0_1_14);
		fw_menu_14.addMenuItem("Proses Perhitungan Pajak", "location='<%=String.valueOf(approot)%>/payroll/tax/list_tax.jsp'");
		fw_menu_14.addMenuItem("Report SPM", "location='<%=String.valueOf(approot)%>/payroll/tax/pay-input.jsp'");
		fw_menu_14.childMenuIcon="<%=approot%>/images/arrows.gif"; 
		fw_menu_14.hideOnMouseOut=true;	 
		
            /* Master Data > Schedule */
            window.fw_menu_2_1 = new Menu("Schedule",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_1.addMenuItem("Period", "location='<%=String.valueOf(approot)%>/masterdata/period.jsp'"); 	  
            fw_menu_2_1.addMenuItem("Category", "location='<%=String.valueOf(approot)%>/masterdata/schedulecategory.jsp'"); 
            fw_menu_2_1.addMenuItem("Symbol", "location='<%=String.valueOf(approot)%>/masterdata/srcschedulesymbol.jsp'"); 
            fw_menu_2_1.hideOnMouseOut=true;	 		   	  	  	  

            /* Master Data > Company */
            window.fw_menu_2_2 = new Menu("Company",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_2.addMenuItem("Division", "location='<%=String.valueOf(approot)%>/masterdata/division.jsp'"); 	  
            fw_menu_2_2.addMenuItem("Department", "location='<%=String.valueOf(approot)%>/masterdata/department.jsp'"); 	  
            fw_menu_2_2.addMenuItem("Position", "location='<%=String.valueOf(approot)%>/masterdata/srcposition.jsp'"); 	  
            fw_menu_2_2.addMenuItem("Section", "location='<%=String.valueOf(approot)%>/masterdata/srcsection.jsp'"); 
            fw_menu_2_2.addMenuItem("Public Holiday", "location='<%=String.valueOf(approot)%>/masterdata/publicHoliday.jsp'");			
            fw_menu_2_2.addMenuItem("Leave Target", "location='<%=String.valueOf(approot)%>/masterdata/leaveTarget.jsp'");			
            fw_menu_2_2.hideOnMouseOut=true;	 		   	  	  	  

                /* Employee > Training > Training Activities */
                window.fw_menu_2_3_0 = new Menu("Upload Data",90,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_2_3_0.addMenuItem("Trainee", "location='<%=String.valueOf(approot)%>/system/excel_up/up_employee_train.jsp'");
                fw_menu_2_3_0.addMenuItem("Daily Worker", "location='<%=String.valueOf(approot)%>/system/excel_up/up_employee_dw.jsp'");
                fw_menu_2_3_0.hideOnMouseOut=true;

                /* Employee > Leave Management > Opname DP, AL dan LL */
                window.fw_menu_2_3_1 = new Menu("Leave Management",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_2_3_1.addMenuItem("Day Off Payment", "location='<%=String.valueOf(approot)%>/system/excel_up/up_opname_dp.jsp'");
                fw_menu_2_3_1.addMenuItem("Annual Leave", "location='<%=String.valueOf(approot)%>/system/excel_up/up_opname_al.jsp'");
                fw_menu_2_3_1.addMenuItem("Long Leave", "location='<%=String.valueOf(approot)%>/system/excel_up/up_opname_ll.jsp'");
                fw_menu_2_3_1.hideOnMouseOut=true; 
                 
                window.fw_menu_2_3_2 = new Menu("Leave Balance",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_2_3_2.addMenuItem("Day Off Payment", "location='<%=String.valueOf(approot)%>/system/leave/opnameDP.jsp'");
                fw_menu_2_3_2.addMenuItem("Annual Leave", "location='<%=String.valueOf(approot)%>/system/leave/opnameAL.jsp'");
                fw_menu_2_3_2.addMenuItem("Long Leave", "location='<%=String.valueOf(approot)%>/system/leave/opnameLL.jsp'");
                fw_menu_2_3_2.addMenuItem("Annual Leave", "location='<%=approot%>/system/leave/opnameAl.jsp'");
                fw_menu_2_3_2.addMenuItem("Long Leave", "location='<%=approot%>/system/leave/opnameLL.jsp'");
                fw_menu_2_3_2.hideOnMouseOut=true;

            /* Master Data > Employee */
            window.fw_menu_2_3 = new Menu("Employee",140,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_3.addMenuItem("Education", "location='<%=String.valueOf(approot)%>/masterdata/education.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Level", "location='<%=String.valueOf(approot)%>/masterdata/level.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Category", "location='<%=String.valueOf(approot)%>/masterdata/empcategory.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Religion", "location='<%=String.valueOf(approot)%>/masterdata/religion.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Marital", "location='<%=String.valueOf(approot)%>/masterdata/marital.jsp'"); 
	    fw_menu_2_3.addMenuItem("Race", "location='<%=String.valueOf(approot)%>/masterdata/race.jsp'"); 
            fw_menu_2_3.addMenuItem("Language", "location='<%=String.valueOf(approot)%>/masterdata/masterlanguage.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Image Assign", "location='<%=String.valueOf(approot)%>/masterdata/image_assign.jsp'"); 	  
            //fw_menu_2_3.addMenuItem("Finger Print", "location='<%=String.valueOf(approot)%>/masterdata/fingerPrint.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Resigned Reason", "location='<%=String.valueOf(approot)%>/masterdata/resignedreason.jsp'");    	  
            fw_menu_2_3.addMenuItem("Award Type", "location='<%=String.valueOf(approot)%>/masterdata/awardtype.jsp'");    	                  
            //fw_menu_2_3.addMenuItem(fw_menu_2_3_0); 	  			
            //fw_menu_2_3.addMenuItem(fw_menu_2_3_1); 
            //fw_menu_2_3.addMenuItem(fw_menu_2_3_2); 
           
			//fw_menu_2_3.addMenuItem("Training", "location='<%=String.valueOf(approot)%>/masterdata/srctraining.jsp'"); 
            fw_menu_2_3.addMenuItem("Absence Reason", "location='<%=String.valueOf(approot)%>/masterdata/reason.jsp'"); 
	    fw_menu_2_3.childMenuIcon="<%=approot%>/images/arrows.gif"; 			
            fw_menu_2_3.hideOnMouseOut=true;	 		   	  	  	  

        /* Locker */
        window.fw_m_locker = new Menu("Locker Data",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_m_locker.addMenuItem("Locker Location", "location='<%=String.valueOf(approot)%>/masterdata/lockerlocation.jsp'");
            fw_m_locker.addMenuItem("Locker Condition", "location='<%=String.valueOf(approot)%>/masterdata/lockercondition.jsp'");
            fw_m_locker.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
            fw_m_locker.hideOnMouseOut=true;	

            /* Master Data > Performance Appraisal */
            <%
            if(isDefault){
            %>
                window.fw_m_apprsl = new Menu("Performance Appraisal",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_m_apprsl.addMenuItem("Group Rank", "location='<%=String.valueOf(approot)%>/masterdata/grouprank.jsp'");   
                fw_m_apprsl.addMenuItem("Category Appraisal", "location='<%=String.valueOf(approot)%>/masterdata/groupcategory.jsp'"); 
                fw_m_apprsl.addMenuItem("Evaluation Criteria", "location='<%=String.valueOf(approot)%>/masterdata/evaluation.jsp'"); 
            <%}else{%>
                window.fw_m_apprsl = new Menu("Assessment",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_m_apprsl.addMenuItem("Form Creator", "location='<%=String.valueOf(approot)%>/masterdata/assessment/assessmentFormMain.jsp'");  
                fw_m_apprsl.addMenuItem("Evaluation Criteria", "location='<%=String.valueOf(approot)%>/masterdata/evaluation.jsp'"); 
                fw_m_apprsl.addMenuItem("Group Rank", "location='<%=String.valueOf(approot)%>/masterdata/grouprankHR.jsp'");   
            <%}%>
            fw_m_apprsl.hideOnMouseOut=true;	 		   	  	  	  

        /* Master Data */
        window.fw_menu_2 = new Menu("root",160,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_2.addMenuItem(fw_menu_2_2); 
        fw_menu_2.addMenuItem(fw_menu_2_3); 
        fw_menu_2.addMenuItem(fw_menu_2_1); 
        fw_menu_2.addMenuItem(fw_m_locker); 
        fw_menu_2.addMenuItem(fw_m_apprsl); 
        fw_menu_2.addMenuItem(fw_menu_recruit_master); 
        
        fw_menu_2.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_2.hideOnMouseOut=true;	


            /* System > User Management */
            window.fw_menu_5_0 = new Menu("User Management",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_0.addMenuItem("User List", "location='<%=String.valueOf(approot)%>/admin/userlist.jsp'");
            fw_menu_5_0.addMenuItem("Group Privilege", "location='<%=String.valueOf(approot)%>/admin/grouplist.jsp'");
            fw_menu_5_0.addMenuItem("Privilege", "location='<%=String.valueOf(approot)%>/admin/privilegelist.jsp'");     
            fw_menu_5_0.addMenuItem("Update Password", "location='<%=String.valueOf(approot)%>/admin/userupdatepasswd.jsp'");
            fw_menu_5_0.addMenuItem("User Compare", "location='<%=String.valueOf(approot)%>/employee/databank/harisma_vs_machine.jsp'");
            fw_menu_5_0.hideOnMouseOut=true;	 		   	  	  	  

            /* System > Timekeeping */
            window.fw_menu_5_1 = new Menu("Timekeeping",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            //fw_menu_5_1.addMenuItem("Check machine", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/testmachine.jsp'");
            fw_menu_5_1.addMenuItem("Service Manager", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/svcmgr.jsp'");
            //fw_menu_5_1.addMenuItem("Download Data", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/download.jsp'");
            //fw_menu_5_1.addMenuItem("Reset Machine", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/reset.jsp'");
            //fw_menu_5_1.addMenuItem("Message Management", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/message.jsp'");
            //fw_menu_5_1.addMenuItem("Comm Management", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/close_comm.jsp'");
            //fw_menu_5_1.addMenuItem("Upload data", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/upload_to_harisma.jsp'");
            fw_menu_5_1.hideOnMouseOut=true;	 		   	  	  	  
 
            /* System > Barcode */
            window.fw_menu_5_2 = new Menu("Barcode",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_2.addMenuItem("Insert & Upload Barcode", "location='<%=String.valueOf(approot)%>/system/timekeepingpro/srcbarcode.jsp'");
            fw_menu_5_2.hideOnMouseOut=true;
			
			
            /* System > Working Schedule */
            window.fw_menu_5_3 = new Menu("Working Schedule",170,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");           
            fw_menu_5_3.addMenuItem("Upload & Insert Schedule", "location='<%=String.valueOf(approot)%>/system/excel_up/up_work_schedule.jsp'");
            fw_menu_5_3.hideOnMouseOut=true;	 
			
            /* System > User Management */
            window.fw_menu_5_4 = new Menu("System Management",125,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_4.addMenuItem("System Properties", "location='<%=String.valueOf(approot)%>/system/index.jsp'");        
            fw_menu_5_4.addMenuItem("Login History", "location='<%=String.valueOf(approot)%>/common/logger/history.jsp'");	
	    fw_menu_5_4.addMenuItem("System Log", "location='<%=String.valueOf(approot)%>/log/system_log.jsp'");			
            fw_menu_5_4.hideOnMouseOut=true;
			
            /* System > Presence */    
            window.fw_menu_5_5 = new Menu("Presence",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_5.addMenuItem("Import Presence", "location='<%=String.valueOf(approot)%>/system/presence/import_presence.jsp'");
            fw_menu_5_5.addMenuItem("Absence Analyser", "location='<%=String.valueOf(approot)%>/system/presence/absenceservice.jsp'");
            fw_menu_5_5.addMenuItem("Lateness Analyser", "location='<%=String.valueOf(approot)%>/service/lateness.jsp'");
            fw_menu_5_5.hideOnMouseOut=true;

            /* System > Service */
            window.fw_menu_5_6 = new Menu("Service",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_6.addMenuItem("Leave Management", "location='<%=String.valueOf(approot)%>/service/dp_stock.jsp'");
            fw_menu_5_6.hideOnMouseOut=true; 

            /* System > Manual checking */
            window.fw_menu_5_7 = new Menu("Manual Process",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	    //fw_menu_5_7.addMenuItem("Import Presence", "location='<%=String.valueOf(approot)%>/system/presence/import_presence_dbf.jsp'");			
	    fw_menu_5_7.addMenuItem("Presence Checking", "location='<%=String.valueOf(approot)%>/service/check_presence_manual.jsp'");						
            fw_menu_5_7.addMenuItem("Absenteeism Checking", "location='<%=String.valueOf(approot)%>/service/check_absence_manual.jsp'");
            fw_menu_5_7.addMenuItem("Lateness Checking", "location='<%=String.valueOf(approot)%>/service/check_lateness_manual.jsp'");
            fw_menu_5_7.hideOnMouseOut=true; 
			
	
            /* System */
            window.fw_menu_5 = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5.addMenuItem(fw_menu_5_0);
            fw_menu_5.addMenuItem(fw_menu_5_4);
            //fw_menu_5.addMenuItem(fw_menu_5_2);
            fw_menu_5.addMenuItem(fw_menu_5_1);  
            fw_menu_5.addMenuItem("Service Center", "location='<%=String.valueOf(approot)%>/service/service_center.jsp'");					
            fw_menu_5.addMenuItem("Manual Process", "location='<%=String.valueOf(approot)%>/service/attendance_manual_calculation.jsp'");					
            fw_menu_5.addMenuItem("Admin Query Setup", "location='<%=String.valueOf(approot)%>/system/query_setup.jsp'");					
		//fw_menu_5.addMenuItem("Import Presence", "location='<%=String.valueOf(approot)%>/system/presence/import_presence_dbf.jsp'");					
            fw_menu_5.childMenuIcon="<%=approot%>/images/arrows.gif";
            fw_menu_5.hideOnMouseOut=true;

            /* Reports > Employment */
            window.fw_menu_6_0 = new Menu("Employment",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_0.addMenuItem("Staff Recapitulation", "location='<%=String.valueOf(approot)%>/report/employment/staffrecapitulation.jsp'");
            fw_menu_6_0.hideOnMouseOut=true;	 		   	  	  	  

            /* Reports > Leave & DP Record */
            window.fw_menu_6_1 = new Menu("Leave & DP Record",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_1.addMenuItem("General", "location='<%=String.valueOf(approot)%>/report/leavedp/general.jsp'");
            fw_menu_6_1.addMenuItem("Detail", "location='<%=String.valueOf(approot)%>/report/leavedp/detail.jsp'");
            fw_menu_6_1.hideOnMouseOut=true;	  		   	  	  	  

            /* Reports > Leave & DP Record */
            window.fw_menu_6_1 = new Menu("Leave & DP Record",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_1.addMenuItem("General", "location='<%=String.valueOf(approot)%>/report/leavedp/general.jsp'");
            fw_menu_6_1.addMenuItem("Detail", "location='<%=String.valueOf(approot)%>/report/leavedp/detail.jsp'");
            fw_menu_6_1.hideOnMouseOut=true;

            /* Reports > Staff Control */
            window.fw_menu_6_2 = new Menu("Staff Control",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_2.addMenuItem("Attendance Record", "location='<%=String.valueOf(approot)%>/employee/presence/att_record_monthly.jsp'");						
            fw_menu_6_2.hideOnMouseOut=true;	 		   	  	  	  
			
			/* Reports > Presence */
            window.fw_menu_6_3 = new Menu("Presence",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_3.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/presence/presence_report_daily.jsp'");						
            fw_menu_6_3.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/presence/weekly_presence.jsp'");						
            fw_menu_6_3.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/presence/monthly_presence.jsp'");
            fw_menu_6_3.addMenuItem("Year Report", "location='<%=String.valueOf(approot)%>/report/presence/year_report_presence.jsp'");
            //fw_menu_6_3.addMenuItem("Attendance Summary", "location='<%=String.valueOf(approot)%>/report/presence/presence_summary_sheet.jsp'");						
            //fw_menu_6_3.addMenuItem("Attendance Sum", "location='<%=String.valueOf(approot)%>/report/presence/presence_summary_sheet_old.jsp'");						
            fw_menu_6_3.hideOnMouseOut=true;	

            /* Reports > Lateness */
            window.fw_menu_6_4 = new Menu("Lateness",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_4.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/lateness/lateness_report.jsp'");
            fw_menu_6_4.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/lateness/lateness_weekly_report.jsp'");
            fw_menu_6_4.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/lateness/lateness_monthly_report.jsp'");						
            fw_menu_6_4.hideOnMouseOut=true;	
			
            /* Reports > Split Shift */
            window.fw_menu_6_5 = new Menu("Split Shift",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_6_5.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/splitshift/daily_split.jsp'");
		fw_menu_6_5.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/splitshift/weekly_split.jsp'");
                fw_menu_6_5.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/splitshift/monthly_split.jsp'");			
                fw_menu_6_5.hideOnMouseOut=true;	
			
			/* Reports > Night Shift */ 
            window.fw_menu_6_6 = new Menu("Night Shift",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_6.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/nightshift/daily_night.jsp'");
            fw_menu_6_6.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/nightshift/weekly_night.jsp'");			
            fw_menu_6_6.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/nightshift/monthly_night.jsp'");			
            fw_menu_6_6.hideOnMouseOut=true;				
			
			/* Reports > Absenteeism */ 
            window.fw_menu_6_7 = new Menu("Absenteeism",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_7.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/absenteeism/daily_absence.jsp'");
            fw_menu_6_7.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/absenteeism/weekly_absence.jsp'");			
            fw_menu_6_7.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/absenteeism/monthly_absence.jsp'");			
            fw_menu_6_7.hideOnMouseOut=true;				

			/* Reports > Sickness */ 
            window.fw_menu_6_8 = new Menu("Sickness",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_8.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/sickness/daily_sickness.jsp'");
            fw_menu_6_8.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/sickness/weekly_sickness.jsp'");			
            fw_menu_6_8.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/sickness/monthly_sickness.jsp'");	
            fw_menu_6_8.addMenuItem("Zero Sickness Report", "location='<%=String.valueOf(approot)%>/report/sickness/zero_sickness.jsp'");
            fw_menu_6_8.hideOnMouseOut=true;				

			/* Reports > Special Dispensation */ 
            window.fw_menu_6_9 = new Menu("Special Dispensation",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_9.addMenuItem("Daily Report", "location='<%=String.valueOf(approot)%>/report/specialdisp/daily_specialdisp.jsp'");
            fw_menu_6_9.addMenuItem("Weekly Report", "location='<%=String.valueOf(approot)%>/report/specialdisp/weekly_specialdisp.jsp'");			
            fw_menu_6_9.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/specialdisp/monthly_specialdisp.jsp'");
            fw_menu_6_9.hideOnMouseOut=true;

            /* Reports > Attendance */
            window.fw_menu_6_10 = new Menu("Leave Report",160,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_10.addMenuItem("Leave & DP Summary ", "location='<%=String.valueOf(approot)%>/report/leavedp/leave_dp_sum.jsp'");
            fw_menu_6_10.addMenuItem("Leave & DP Detail ", "location='<%=String.valueOf(approot)%>/report/leavedp/leave_dp_detail.jsp'");            
            fw_menu_6_10.addMenuItem("Leave & DP Sum. Period", "location='<%=String.valueOf(approot)%>/report/leavedp/leave_department_by_period.jsp'");
            fw_menu_6_10.addMenuItem("Leave & DP Detail Period", "location='<%=String.valueOf(approot)%>/report/leavedp/leave_dp_detail_period.jsp'");   
            fw_menu_6_10.addMenuItem("Special & Unpaid Period", "location='<%=String.valueOf(approot)%>/employee/leave/leave_sp_period.jsp'");                  
            fw_menu_6_10.addMenuItem("DP Expired", "location='<%=String.valueOf(approot)%>/report/attendance/dpexp_report.jsp'"); 
          
                
            /** fw_menu_6_10.addMenuItem("Day Off Payment Report", "location='<%=String.valueOf(approot)%>/report/attendance/dp_report.jsp'");
            fw_menu_6_10.addMenuItem("DP Expiration Report", "location='<%=String.valueOf(approot)%>/report/attendance/dpexp_report.jsp'");
            fw_menu_6_10.addMenuItem("Annual Leave Report", "location='<%=String.valueOf(approot)%>/report/attendance/annualleave_report.jsp'");
            fw_menu_6_10.addMenuItem("Long Leave Report", "location='<%=String.valueOf(approot)%>/report/attendance/longleave_report.jsp'");			
            fw_menu_6_10.addMenuItem("Unpaid Leave Report", "location='<%=String.valueOf(approot)%>/report/attendance/unpaid_leave.jsp'");			
            fw_menu_6_10.addMenuItem("Month End Report", "location='<%=String.valueOf(approot)%>/report/dailyon/dailyon_montly.jsp'");			
                **/
            fw_menu_6_10.hideOnMouseOut=true;
			
			
	    /* Reports > Presence */
            window.fw_menu_6_11 = new Menu("Trainee",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            //fw_menu_6_11.addMenuItem("Monthly Report", "location='<%=String.valueOf(approot)%>/report/training/monthly_tr_rpt.jsp'");
            fw_menu_6_11.addMenuItem("End Period", "location='<%=String.valueOf(approot)%>/report/training/end_tr_rpt.jsp'");            
            fw_menu_6_11.hideOnMouseOut=true;
			
			// Reports > Employee */
            window.fw_menu_6_12 = new Menu("Employee",250,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_12.addMenuItem("List Of Employee Category", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_category.jsp'");
	    fw_menu_6_12.addMenuItem("List Of Employee Resignation", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_resignation1.jsp'");
	    fw_menu_6_12.addMenuItem("List Of Employee Education", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_education.jsp'");
	    fw_menu_6_12.addMenuItem("List Of Employee Category By Name", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_by_Name.jsp'");
	    fw_menu_6_12.addMenuItem("List Number Of Absences ", "location='<%=String.valueOf(approot)%>/report/employee/list_absence_reason.jsp'");
	    //fw_menu_6_12.addMenuItem("List Of Employee Leave ", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_leave.jsp'");
            fw_menu_6_12.addMenuItem("List Of Employee Race ", "location='<%=String.valueOf(approot)%>/report/employee/list_employee_race.jsp'");
            fw_menu_6_12.hideOnMouseOut=true;
			/* Report > Payroll > Overtime Report */
                window.fw_menu_6_13_0 = new Menu("Overtime Report",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_6_13_0.addMenuItem("Working Day", "location='<%=String.valueOf(approot)%>/report/payroll/ovt_report.jsp'");
                fw_menu_6_13_0.addMenuItem("Overtime Payment", "location='<%=String.valueOf(approot)%>/report/payroll/upah_ovt.jsp'");
                fw_menu_6_13_0.hideOnMouseOut=true;
			// Reports > Payroll */
                         
                window.fw_menu_6_13 = new Menu("Payroll",200,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                    fw_menu_6_13.addMenuItem(fw_menu_6_13_0);
                    fw_menu_6_13.addMenuItem("Meal Allowance", "location='<%=String.valueOf(approot)%>/report/payroll/uang_makan.jsp'");
                    fw_menu_6_13.addMenuItem("List Of Salary Transfered", "location='<%=String.valueOf(approot)%>/report/payroll/gaji_transfer.jsp'");
                    fw_menu_6_13.addMenuItem("Ekspor Allowance Report", "location='<%=String.valueOf(approot)%>/report/payroll/tunj_ekspor.jsp'");
                    fw_menu_6_13.addMenuItem("List Of Employee Salary", "location='<%=String.valueOf(approot)%>/report/payroll/list_salary.jsp'");
                    fw_menu_6_13.addMenuItem("Staff's Allowance Report", "location='<%=String.valueOf(approot)%>/report/payroll/list_allowance.jsp'");
                    fw_menu_6_13.addMenuItem("Daily Worker Report", "location='<%=String.valueOf(approot)%>/report/payroll/dw_report.jsp'");                             
                    fw_menu_6_13.childMenuIcon="<%=approot%>/images/arrows.gif"; 
                    fw_menu_6_13.hideOnMouseOut=true;

        /* Clinic */
		 /* Clinic > Medicine */
            window.fw_menu_7_0 = new Menu("Medicine",170,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_7_0.addMenuItem("List Of Medicine", "location='<%=String.valueOf(approot)%>/clinic/stock/medicine.jsp'");
            fw_menu_7_0.addMenuItem("Medicine Consumption", "location='<%=String.valueOf(approot)%>/clinic/stock/medconsump.jsp'");
            fw_menu_7_0.hideOnMouseOut=true;

            /* Clinic > Disease */ 
            window.fw_menu_7_1 = new Menu("Disease",100,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_7_1.addMenuItem("Type", "location='<%=String.valueOf(approot)%>/clinic/disease/diseasetype.jsp'");
            fw_menu_7_1.hideOnMouseOut=true;	 

            /* Clinic > Medical Expense */
            window.fw_menu_7_2 = new Menu("Medical",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_7_2.addMenuItem("Medical Level", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_level.jsp'");
            fw_menu_7_2.addMenuItem("Medical Case", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_case.jsp'");
            fw_menu_7_2.addMenuItem("Medical Budget", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_budget.jsp'");
            fw_menu_7_2.addMenuItem("Group", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_group.jsp'");
            fw_menu_7_2.addMenuItem("Type", "location='<%=String.valueOf(approot)%>/clinic/medexpense/med_type.jsp'");
            //fw_menu_3_2.addMenuItem("Expense Recapitulation", "location='<%=String.valueOf(approot)%>/clinic/medexpense/exp_rec.jsp'");
            //fw_menu_7_2.addMenuItem("Medical Record", "location='<%=String.valueOf(approot)%>/clinic/disease/scrmedicalrecord.jsp'");									
            fw_menu_7_2.hideOnMouseOut=true;	 
        
        
        window.fw_menu_7 = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_7.addMenuItem("Employee & Family", "location='<%=approot%>/clinic/srcemployeefam.jsp'"); 
        fw_menu_7.addMenuItem("Medical Record", "location='<%=String.valueOf(approot)%>/clinic/disease/scrmedicalrecord.jsp'");									
        fw_menu_7.addMenuItem(fw_menu_7_0);
        fw_menu_7.addMenuItem("Employee Visit", "location='<%=String.valueOf(approot)%>/clinic/empvisit/srcemployeevisit.jsp'"); 
        fw_menu_7.addMenuItem("Guest Handling", "location='<%=String.valueOf(approot)%>/clinic/guest/srcguesthandling.jsp'"); 
        fw_menu_7.addMenuItem(fw_menu_7_1); 
        fw_menu_7.addMenuItem(fw_menu_7_2); 		
        //fw_menu_3.addMenuItem("Insurance Scheme", "location='<%=String.valueOf(approot)%>/under_construction.jsp'"); 
        fw_menu_7.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_7.hideOnMouseOut=true;


        /* Locker */
        window.fw_menu_1 = new Menu("root",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_1.addMenuItem("Locker", "location='<%=String.valueOf(approot)%>/locker/srclocker.jsp'");
        fw_menu_1.addMenuItem("Locker Treatment", "location='<%=String.valueOf(approot)%>/locker/srclockertreatment.jsp'");
        fw_menu_1.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_1.hideOnMouseOut=true;	


        /* Reports */
        window.fw_menu_6 = new Menu("root",140,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_6.addMenuItem(fw_menu_6_2);
        fw_menu_6.addMenuItem(fw_menu_6_3);
        fw_menu_6.addMenuItem(fw_menu_6_4);
        fw_menu_6.addMenuItem(fw_menu_6_5);
	fw_menu_6.addMenuItem(fw_menu_6_6);
	fw_menu_6.addMenuItem(fw_menu_6_7);
	fw_menu_6.addMenuItem(fw_menu_6_8);
	fw_menu_6.addMenuItem(fw_menu_6_9);
        fw_menu_6.addMenuItem(fw_menu_6_10);
	fw_menu_6.addMenuItem(fw_menu_6_11);
	fw_menu_6.addMenuItem(fw_menu_6_12);
        fw_menu_6.addMenuItem(fw_menu_training_rpt);
        /* 
	fw_menu_6.addMenuItem(fw_menu_6_13);
        */
        fw_menu_6.childMenuIcon="<%=approot%>/images/arrows.gif";
        fw_menu_6.hideOnMouseOut=true;

        fw_menu_6.writeMenus();
    } 

    function MM_jumpMenu(targ,selObj,restore){ 
        eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
        if (restore) selObj.selectedIndex=0;
    }
</script>


<script language="JavaScript"> 
    function cordYMenu0(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_0,cordX,posY);
    }

    function cordYMenu1(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_1,cordX,posY);
    }	

    function cordYMenu2(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_2,cordX,posY);
    }	

    function cordYMenu3(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_3,cordX,posY);
    }	

    function cordYMenu4(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_4,cordX,posY);
    }	

    function cordYMenu5(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_5,cordX,posY);
    }

    function cordYMenu6(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_6,cordX,posY);
    }

    function cordYMenu7(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_7,cordX,posY);
    }


   function cordYMenu11(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_11,cordX,posY);
    }		
	 function cordYMenu12(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_12,cordX,posY);
    }
	
	function cordYMenu13(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_13,cordX,posY);
    }	
	
	function cordYMenu14(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_14,cordX,posY);
    }
    
   function cordYMenu15(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.window.fw_menu_training,cordX,posY);
    }		
</script>  	  		  

<script language=JavaScript src="<%=approot%>/main/fw_menu.js"></script>

<script language=JavaScript1.2>fwLoadMenus();</script>

<input type="hidden" id="M0">
<input type="hidden" id="M1">
<input type="hidden" id="M2">
<input type="hidden" id="M3">
<input type="hidden" id="M4">
<input type="hidden" id="M5">
<input type="hidden" id="M6">
<input type="hidden" id="M7">
<input type="hidden" id="M11">
<input type="hidden" id="M111">
<input type="hidden" id="M12">
<input type="hidden" id="M13">
<input type="hidden" id="M14">
<input type="hidden" id="M15">

<script>
    function setScr(){
	document.all.M0.value = document.all.divMenu0.offsetLeft + 1;
	document.all.M2.value = document.all.divMenu2.offsetLeft + 1;
	document.all.M5.value = document.all.divMenu5.offsetLeft + 1;
	document.all.M6.value = document.all.divMenu6.offsetLeft + 1;
        <%if(TYPE_CONFIG == TYPE_HARDROCK || TYPE_CONFIG == TYPE_SANUR_PARADISE){ %> 
	document.all.M7.value = document.all.divMenu7.offsetLeft + 1;        
	document.all.M1.value = document.all.divMenu1.offsetLeft + 1;
        <% } %>  
	document.all.M11.value = document.all.divMenu11.offsetLeft + 1;
	document.all.M12.value = document.all.divMenu12.offsetLeft + 1;
	document.all.M13.value = document.all.divMenu13.offsetLeft + 1;
	document.all.M14.value = document.all.divMenu14.offsetLeft + 1;
        document.all.M15.value = document.all.divMenu11.offsetLeft + 1;
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
                  <a style="text-decoration:none" id="divMenu00" href="<%=approot%>/home.jsp"><font color="#30009D">Home</font></a>
                | <a style="text-decoration:none" id="divMenu0" href="#" onclick="javascript:cordYMenu0(document.all.M0.value);"><font color="#30009D">Employee</font></a> 
                <% 
                   if(mnuTraining) { %>
			| <a style="text-decoration:none" id="divMenu11" href="#" onClick="javascript:cordYMenu15(document.all.M11.value);"><font color="#30009D"><%=dictionaryD.getWord(I_Dictionary.TRAINING) %></font></a>
			  <% } %>
                | <a style="text-decoration:none" id="divMenu6" href="#" onclick="javascript:cordYMenu6(document.all.M6.value);"><font color="#30009D">Reports</font></a> 
                | <a style="text-decoration:none" id="divMenu10" href="<%=approot%>/harisma-canteen/home.jsp?menu=1"><font color="#30009D">Canteen</font></a>       
                <%if(mnuMedical==true || TYPE_CONFIG == TYPE_HARDROCK || TYPE_CONFIG == TYPE_SANUR_PARADISE ){ %>
                | <a style="text-decoration:none" id="divMenu7" href="#" onclick="javascript:cordYMenu7(document.all.M7.value);"><font color="#30009D">Clinic</font></a>                
                | <a style="text-decoration:none" id="divMenu1" href="#" onclick="javascript:cordYMenu1(document.all.M1.value);"><font color="#30009D">Locker</font></a>                
                <% } %>
                | <a style="text-decoration:none" id="divMenu2" href="#" onclick="javascript:cordYMenu2(document.all.M2.value);"><font color="#30009D">Master Data</font></a> 		
                | <a style="text-decoration:none" id="divMenu5" href="#" onclick="javascript:cordYMenu5(document.all.M5.value);"><font color="#30009D">System</font></a> 		
				<% 
		   if(menuPayroll) { %>
			| <a style="text-decoration:none" id="divMenu11" href="#" onClick="javascript:cordYMenu11(document.all.M111.value);"><font color="#30009D">Payroll
			Setup</font></a>| <a style="text-decoration:none" id="divMenu12" href="#" onClick="javascript:cordYMenu12(document.all.M12.value);"><font color="#30009D">Overtime</font></a> 
			| <a style="text-decoration:none" id="divMenu13" href="#" onClick="javascript:cordYMenu13(document.all.M13.value);"><font color="#30009D">Payroll 
			Process</font></a> | <a style="text-decoration:none" id="divMenu14" href="#" onClick="javascript:cordYMenu14(document.all.M14.value);"><font color="#30009D">Tax 
        	</font></a> 
			  <% } %>
                                
                | <a style="text-decoration:none" id="divMenu9" href="#" onclick="javascript:showHelp()"><font color="#30009D">Help</font></a> 
                | <a style="text-decoration:none" id="divMenu8" href="<%=approot%>/logout.jsp"><font color="#30009D">Logout</font></a> 
            </b></div>
        </td> 
    </tr>
</table>