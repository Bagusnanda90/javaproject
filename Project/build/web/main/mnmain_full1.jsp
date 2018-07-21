
<script language=JavaScript>
    /* This function used to make pulldown menu only */
    function fwLoadMenus() {
        if (window.fw_menu_0) return;	  	  	  		  		 				

                /* Employee > Attendance > Presence */
                window.fw_menu_0_1_1 = new Menu("Presence",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_0_1_1.addMenuItem("Manual Registration", "location='<%=approot%>/employee/presence/srcpresence.jsp'");
                fw_menu_0_1_1.addMenuItem("View Presence", "location='<%=approot%>/employee/presence/srcviewpresence.jsp'");
                fw_menu_0_1_1.hideOnMouseOut=true;	 		   	  	  	  

            /* Employee > Attendance */
            window.fw_menu_0_1 = new Menu("Attendance",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_0_1.addMenuItem("Working Schedule", "location='<%=approot%>/employee/attendance/srcempschedule.jsp'");
            fw_menu_0_1.addMenuItem("Manual Registration", "location='<%=approot%>/employee/presence/srcpresence.jsp'");
            fw_menu_0_1.childMenuIcon="<%=approot%>/images/arrows.gif"; 
            fw_menu_0_1.hideOnMouseOut=true;	  

			/* Employee > Leave Management */
			window.fw_menu_0_2 = new Menu("Leave Management",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
			fw_menu_0_2.addMenuItem("DP Management", "location='<%=approot%>/employee/attendance/dp.jsp'");
			fw_menu_0_2.addMenuItem("AL Management", "location='<%=approot%>/employee/attendance/annualleave.jsp'");
			fw_menu_0_2.addMenuItem("LL Management", "location='<%=approot%>/employee/attendance/longleave.jsp'");				
			fw_menu_0_2.hideOnMouseOut=true;	 		   	  	  	  

			/* Employee > Leave Management */
			window.fw_menu_0_3 = new Menu("Leave Application",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
			fw_menu_0_3.addMenuItem("DP Application", "location='<%=approot%>/employee/leave/src_dp_application.jsp'");
			fw_menu_0_3.addMenuItem("Leave Application", "location='<%=approot%>/employee/leave/src_leave_application.jsp'");			
			fw_menu_0_3.hideOnMouseOut=true;	 		   	  	  	  

			/* Employee > Training > Training Activities */
                window.fw_menu_0_5_0 = new Menu("Training Activities",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_0_5_0.addMenuItem("Monthly Planning", "location='<%=approot%>/employee/training/training_act_plan_list.jsp'");
                fw_menu_0_5_0.addMenuItem("Actual", "location='<%=approot%>/employee/training/training_act_actual_list.jsp'");
                fw_menu_0_5_0.hideOnMouseOut=true;	
			
            /* Employee > Training */
            window.fw_menu_0_5 = new Menu("Training",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_0_5.addMenuItem("Training History", "location='<%=approot%>/employee/training/src_training_hist.jsp'");
            fw_menu_0_5.addMenuItem("Special Achievement", "location='<%=approot%>/employee/training/src_achieve.jsp'");
            fw_menu_0_5.addMenuItem(fw_menu_0_5_0);
			fw_menu_0_5.addMenuItem("Training Search", "location='<%=approot%>/employee/training/src_training_exist.jsp'");
            fw_menu_0_5.childMenuIcon="<%=approot%>/images/arrows.gif"; 
            fw_menu_0_5.hideOnMouseOut=true;	 

        /* Employee */
        window.fw_menu_0 = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_0.addMenuItem("Data Bank", "location='<%=approot%>/employee/databank/srcemployee.jsp'"); 
        fw_menu_0.addMenuItem(fw_menu_0_1);
        fw_menu_0.addMenuItem(fw_menu_0_2);		
        fw_menu_0.addMenuItem(fw_menu_0_3);
        fw_menu_0.addMenuItem("Absence Management", "location='<%=approot%>/employee/absence/srcabsence.jsp'");								
		fw_menu_0.addMenuItem(fw_menu_0_5);
        fw_menu_0.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_0.hideOnMouseOut=true;	 

		/* Payroll Setup */
		window.fw_menu_11 = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_11.addMenuItem("General", "location='<%=approot%>/payroll/setup/general.jsp'");
		fw_menu_11.addMenuItem("Payroll Period", "location='<%=approot%>/payroll/setup/period.jsp'");
		fw_menu_11.addMenuItem("Bank List", "location='<%=approot%>/payroll/setup/list-bank.jsp'");
		fw_menu_11.addMenuItem("Executives List", "location='<%=approot%>/payroll/setup/nama-pejabat.jsp'");
		fw_menu_11.addMenuItem("Tax Type", "location='<%=approot%>/payroll/setup/jenis-pajak.jsp'");
		fw_menu_11.addMenuItem("Tax Payment Type", "location='<%=approot%>/payroll/setup/jenis-setoran.jsp'");
		fw_menu_11.addMenuItem("Salary Component", "location='<%=approot%>/payroll/setup/salary-comp.jsp'");
		fw_menu_11.addMenuItem("Salary Level", "location='<%=approot%>/payroll/setup/salary-level.jsp'");
		fw_menu_11.addMenuItem("Tax Tariff", "location='<%=approot%>/payroll/setup/tax-tariff.jsp'");
		fw_menu_11.addMenuItem("Format Report", "location='<%=approot%>/payroll/setup/format-report.jsp'");
		fw_menu_11.addMenuItem("Format Tax Slip Nr.", "location='<%=approot%>/payroll/setup/tax-slip-nr.jsp'");
		fw_menu_11.addMenuItem("Employee Setup", "location='<%=approot%>/payroll/setup/employee-setup.jsp'");
		fw_menu_11.addMenuItem("Currency", "location='<%=approot%>/payroll/setup/currency.jsp'");
		fw_menu_11.addMenuItem("Currency Rate", "location='<%=approot%>/payroll/setup/#'");
		fw_menu_11.childMenuIcon="<%=approot%>/images/arrows.gif"; 
		fw_menu_11.hideOnMouseOut=true;	 


		/* Overtime */
		window.fw_menu_12 = new Menu("root",140,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_12.addMenuItem("Overtime Index", "location='<%=approot%>/payroll/overtime/ov-index.jsp'");
		fw_menu_12.addMenuItem("Import from Presence", "location='<%=approot%>/payroll/overtime/ov-import-present.jsp'");
		fw_menu_12.addMenuItem("Input Overtime", "location='<%=approot%>/payroll/overtime/ov-input.jsp'");
		fw_menu_12.addMenuItem("Posting Overtime", "location='<%=approot%>/payroll/overtime/ov-posting.jsp'");
		fw_menu_12.childMenuIcon="<%=approot%>/images/arrows.gif"; 
		fw_menu_12.hideOnMouseOut=true;	 

		/* Payroll Process */
		window.fw_menu_13 = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_13.addMenuItem("Prepare data", "location='<%=approot%>/payroll/process/pay-pre-data.jsp'");
		fw_menu_13.addMenuItem("Payroll Input", "location='<%=approot%>/payroll/process/pay-input.jsp'");
		fw_menu_13.addMenuItem("Payroll Process", "location='<%=approot%>/payroll/process/pay-process.jsp'");
		fw_menu_13.addMenuItem("Payslip Printing", "location='<%=approot%>/payroll/process/pay-printing.jsp'");
		fw_menu_13.childMenuIcon="<%=approot%>/images/arrows.gif"; 
		fw_menu_13.hideOnMouseOut=true;	 



            /* Master Data > Schedule */
            window.fw_menu_2_1 = new Menu("Schedule",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_1.addMenuItem("Period", "location='<%=approot%>/masterdata/period.jsp'"); 	  
            fw_menu_2_1.addMenuItem("Category", "location='<%=approot%>/masterdata/schedulecategory.jsp'"); 
            fw_menu_2_1.addMenuItem("Symbol", "location='<%=approot%>/masterdata/srcschedulesymbol.jsp'"); 
            fw_menu_2_1.hideOnMouseOut=true;	 		   	  	  	  

            /* Master Data > Company */
            window.fw_menu_2_2 = new Menu("Company",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_2.addMenuItem("Division", "location='<%=approot%>/masterdata/division.jsp'"); 	  
            fw_menu_2_2.addMenuItem("Department", "location='<%=approot%>/masterdata/department.jsp'"); 	  
            fw_menu_2_2.addMenuItem("Position", "location='<%=approot%>/masterdata/srcposition.jsp'"); 	  
            fw_menu_2_2.addMenuItem("Section", "location='<%=approot%>/masterdata/srcsection.jsp'"); 
            fw_menu_2_2.addMenuItem("Public Holiday", "location='<%=approot%>/masterdata/publicHoliday.jsp'");			
            fw_menu_2_2.hideOnMouseOut=true;	 		   	  	  	  


                /* Employee > Training > Training Activities */
                window.fw_menu_2_3_0 = new Menu("Upload Data",90,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_2_3_0.addMenuItem("Trainee", "location='<%=approot%>/system/excel_up/up_employee_train.jsp'");
                fw_menu_2_3_0.addMenuItem("Daily Worker", "location='<%=approot%>/system/excel_up/up_employee_dw.jsp'");
                fw_menu_2_3_0.hideOnMouseOut=true;

                /* Employee > Leave Management > Opname DP, AL dan LL */
                window.fw_menu_2_3_1 = new Menu("Leave Management",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
                fw_menu_2_3_1.addMenuItem("Day Off Payment", "location='<%=approot%>/system/excel_up/up_opname_dp.jsp'");
                fw_menu_2_3_1.addMenuItem("Annual Leave", "location='<%=approot%>/system/excel_up/up_opname_al.jsp'");
                fw_menu_2_3_1.addMenuItem("Long Leave", "location='<%=approot%>/system/excel_up/up_opname_ll.jsp'");				
                fw_menu_2_3_1.hideOnMouseOut=true;

            /* Master Data > Employee */
            window.fw_menu_2_3 = new Menu("Employee",140,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_3.addMenuItem("Education", "location='<%=approot%>/masterdata/education.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Level", "location='<%=approot%>/masterdata/level.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Category", "location='<%=approot%>/masterdata/empcategory.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Religion", "location='<%=approot%>/masterdata/religion.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Marital", "location='<%=approot%>/masterdata/marital.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Language", "location='<%=approot%>/masterdata/masterlanguage.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Resigned Reason", "location='<%=approot%>/masterdata/resignedreason.jsp'");    	  
            fw_menu_2_3.addMenuItem(fw_menu_2_3_0); 	  			
            fw_menu_2_3.addMenuItem(fw_menu_2_3_1); 	  						
			fw_menu_2_3.addMenuItem("Training", "location='<%=approot%>/masterdata/training.jsp'"); 
	        fw_menu_2_3.childMenuIcon="<%=approot%>/images/arrows.gif"; 			
            fw_menu_2_3.hideOnMouseOut=true;	 		   	  	  	  

        /* Master Data */
        window.fw_menu_2 = new Menu("root",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_2.addMenuItem(fw_menu_2_2); 
        fw_menu_2.addMenuItem(fw_menu_2_3); 
        fw_menu_2.addMenuItem(fw_menu_2_1); 
        fw_menu_2.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_2.hideOnMouseOut=true;	


            /* System > User Management */
            window.fw_menu_5_0 = new Menu("User Management",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_0.addMenuItem("User List", "location='<%=approot%>/admin/userlist.jsp'");
            fw_menu_5_0.addMenuItem("Group Privilege", "location='<%=approot%>/admin/grouplist.jsp'");
            fw_menu_5_0.addMenuItem("Privilege", "location='<%=approot%>/admin/privilegelist.jsp'");     
            fw_menu_5_0.addMenuItem("Update Password", "location='<%=approot%>/admin/userupdatepasswd.jsp'");                      				
            fw_menu_5_0.hideOnMouseOut=true;	 		   	  	  	  

            /* System > Timekeeping */
            window.fw_menu_5_1 = new Menu("Timekeeping",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
			//fw_menu_5_1.addMenuItem("Check machine", "location='<%=approot%>/system/timekeeping/testmachine.jsp'");   		
            fw_menu_5_1.addMenuItem("Service Manager", "location='<%=approot%>/system/timekeeping/svcmgr.jsp'");
            fw_menu_5_1.addMenuItem("Download Data", "location='<%=approot%>/system/timekeeping/download.jsp'");
            //fw_menu_5_1.addMenuItem("Reset Machine", "location='<%=approot%>/system/timekeeping/reset.jsp'");
            fw_menu_5_1.hideOnMouseOut=true;	 		   	  	  	  
 
            /* System > Barcode */
            window.fw_menu_5_2 = new Menu("Barcode",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_2.addMenuItem("Insert & Upload Barcode", "location='<%=approot%>/system/timekeeping/srcbarcode.jsp'");
            fw_menu_5_2.hideOnMouseOut=true;
			
			
            /* System > Working Schedule */
            window.fw_menu_5_3 = new Menu("Working Schedule",170,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");           
            fw_menu_5_3.addMenuItem("Upload & Insert Schedule", "location='<%=approot%>/system/excel_up/up_work_schedule.jsp'");
            fw_menu_5_3.hideOnMouseOut=true;	 
			
            /* System > User Management */
            window.fw_menu_5_4 = new Menu("System Management",125,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_4.addMenuItem("System Properties", "location='<%=approot%>/system/index.jsp'");        
            fw_menu_5_4.addMenuItem("Login History", "location='<%=approot%>/common/logger/history.jsp'");			
            fw_menu_5_4.hideOnMouseOut=true;
			
            /* System > Presence */    
            window.fw_menu_5_5 = new Menu("Presence",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_5.addMenuItem("Import Presence", "location='<%=approot%>/system/presence/import_presence.jsp'");
            fw_menu_5_5.addMenuItem("Absence Analyser", "location='<%=approot%>/system/presence/absenceservice.jsp'");
            fw_menu_5_5.addMenuItem("Lateness Analyser", "location='<%=approot%>/service/lateness.jsp'");
            fw_menu_5_5.hideOnMouseOut=true;

            /* System > Service */
            window.fw_menu_5_6 = new Menu("Service",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_6.addMenuItem("Leave Management", "location='<%=approot%>/service/dp_stock.jsp'");
            fw_menu_5_6.hideOnMouseOut=true; 

            /* System > Manual checking */
            window.fw_menu_5_7 = new Menu("Manual Process",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
			//fw_menu_5_7.addMenuItem("Import Presence", "location='<%=approot%>/system/presence/import_presence_dbf.jsp'");			
			fw_menu_5_7.addMenuItem("Presence Checking", "location='<%=approot%>/service/check_presence_manual.jsp'");						
            fw_menu_5_7.addMenuItem("Absenteeism Checking", "location='<%=approot%>/service/check_absence_manual.jsp'");
            fw_menu_5_7.addMenuItem("Lateness Checking", "location='<%=approot%>/service/check_lateness_manual.jsp'");
            fw_menu_5_7.hideOnMouseOut=true; 

        /* System */
        window.fw_menu_5 = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_5.addMenuItem(fw_menu_5_0);
        fw_menu_5.addMenuItem(fw_menu_5_4);
        fw_menu_5.addMenuItem(fw_menu_5_2);
        fw_menu_5.addMenuItem(fw_menu_5_1);  
		fw_menu_5.addMenuItem("Service Center", "location='<%=approot%>/service/service_center.jsp'");					
		fw_menu_5.addMenuItem("Manual Process", "location='<%=approot%>/service/attendance_manual_calculation.jsp'");					
		//fw_menu_5.addMenuItem("Import Presence", "location='<%=approot%>/system/presence/import_presence_dbf.jsp'");					
        fw_menu_5.childMenuIcon="<%=approot%>/images/arrows.gif";
        fw_menu_5.hideOnMouseOut=true;

            /* Reports > Employment */
            window.fw_menu_6_0 = new Menu("Employment",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_0.addMenuItem("Staff Recapitulation", "location='<%=approot%>/report/employment/staffrecapitulation.jsp'");
            fw_menu_6_0.hideOnMouseOut=true;	 		   	  	  	  

            /* Reports > Leave & DP Record */
            window.fw_menu_6_1 = new Menu("Leave & DP Record",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_1.addMenuItem("General", "location='<%=approot%>/report/leavedp/general.jsp'");
            fw_menu_6_1.addMenuItem("Detail", "location='<%=approot%>/report/leavedp/detail.jsp'");
            fw_menu_6_1.hideOnMouseOut=true;	  		   	  	  	  

            /* Reports > Leave & DP Record */
            window.fw_menu_6_1 = new Menu("Leave & DP Record",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_1.addMenuItem("General", "location='<%=approot%>/report/leavedp/general.jsp'");
            fw_menu_6_1.addMenuItem("Detail", "location='<%=approot%>/report/leavedp/detail.jsp'");
            fw_menu_6_1.hideOnMouseOut=true;

            /* Reports > Staff Control */
            window.fw_menu_6_2 = new Menu("Staff Control",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_2.addMenuItem("Attendance Record", "location='<%=approot%>/employee/presence/srcviewpresence.jsp'");						
            fw_menu_6_2.hideOnMouseOut=true;	 		   	  	  	  
			
			/* Reports > Presence */
            window.fw_menu_6_3 = new Menu("Presence",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_3.addMenuItem("Daily Report", "location='<%=approot%>/report/presence/daily_report.jsp'");						
            fw_menu_6_3.hideOnMouseOut=true;	

			/* Reports > Lateness */
            window.fw_menu_6_4 = new Menu("Lateness",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_4.addMenuItem("Daily Report", "location='<%=approot%>/report/lateness/lateness_report.jsp'");
            fw_menu_6_4.addMenuItem("Weekly Report", "location='<%=approot%>/report/lateness/lateness_weekly_report.jsp'");
            fw_menu_6_4.addMenuItem("Monthly Report", "location='<%=approot%>/report/lateness/lateness_monthly_report.jsp'");						
            fw_menu_6_4.hideOnMouseOut=true;	
			
			/* Reports > Split Shift */
            window.fw_menu_6_5 = new Menu("Split Shift",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_5.addMenuItem("Daily Report", "location='<%=approot%>/report/splitshift/daily_split.jsp'");
			fw_menu_6_5.addMenuItem("Weekly Report", "location='<%=approot%>/report/splitshift/weekly_split.jsp'");
            fw_menu_6_5.addMenuItem("Monthly Report", "location='<%=approot%>/report/splitshift/monthly_split.jsp'");			
            fw_menu_6_5.hideOnMouseOut=true;	
			
			/* Reports > Night Shift */ 
            window.fw_menu_6_6 = new Menu("Night Shift",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_6.addMenuItem("Daily Report", "location='<%=approot%>/report/nightshift/daily_night.jsp'");
            fw_menu_6_6.addMenuItem("Weekly Report", "location='<%=approot%>/report/nightshift/weekly_night.jsp'");			
            fw_menu_6_6.addMenuItem("Monthly Report", "location='<%=approot%>/report/nightshift/monthly_night.jsp'");			
            fw_menu_6_6.hideOnMouseOut=true;				
			
			/* Reports > Absenteeism */ 
            window.fw_menu_6_7 = new Menu("Absenteeism",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_7.addMenuItem("Daily Report", "location='<%=approot%>/report/absenteeism/daily_absence.jsp'");
            fw_menu_6_7.addMenuItem("Weekly Report", "location='<%=approot%>/report/absenteeism/weekly_absence.jsp'");			
            fw_menu_6_7.addMenuItem("Monthly Report", "location='<%=approot%>/report/absenteeism/monthly_absence.jsp'");			
            fw_menu_6_7.hideOnMouseOut=true;				

			/* Reports > Sickness */ 
            window.fw_menu_6_8 = new Menu("Sickness",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_8.addMenuItem("Daily Report", "location='<%=approot%>/report/sickness/daily_sickness.jsp'");
            fw_menu_6_8.addMenuItem("Weekly Report", "location='<%=approot%>/report/sickness/weekly_sickness.jsp'");			
            fw_menu_6_8.addMenuItem("Monthly Report", "location='<%=approot%>/report/sickness/monthly_sickness.jsp'");			
            fw_menu_6_8.hideOnMouseOut=true;				

			/* Reports > Special Dispensation */ 
            window.fw_menu_6_9 = new Menu("Special Dispensation",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_9.addMenuItem("Daily Report", "location='<%=approot%>/report/specialdisp/daily_specialdisp.jsp'");
            fw_menu_6_9.addMenuItem("Weekly Report", "location='<%=approot%>/report/specialdisp/weekly_specialdisp.jsp'");			
            fw_menu_6_9.addMenuItem("Monthly Report", "location='<%=approot%>/report/specialdisp/monthly_specialdisp.jsp'");
            fw_menu_6_9.hideOnMouseOut=true;

            /* Reports > Attendance */
            window.fw_menu_6_10 = new Menu("Leave Report",160,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_10.addMenuItem("Day Off Payment Report", "location='<%=approot%>/report/attendance/dp_report.jsp'");
            fw_menu_6_10.addMenuItem("Annual Leave Report", "location='<%=approot%>/report/attendance/annualleave_report.jsp'");
            fw_menu_6_10.addMenuItem("Long Leave Report", "location='<%=approot%>/report/attendance/longleave_report.jsp'");			
            fw_menu_6_10.hideOnMouseOut=true;
			
			
			/* Reports > Presence */
            window.fw_menu_6_11 = new Menu("Training",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_11.addMenuItem("Monthly Report", "location='<%=approot%>/report/training/monthly_tr_rpt.jsp'");
            fw_menu_6_11.hideOnMouseOut=true;
                                        
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
<input type="hidden" id="M11">
<input type="hidden" id="M12">
<input type="hidden" id="M13">

<script>
    function setScr() {
	document.all.M0.value = document.all.divMenu0.offsetLeft + 1;
	document.all.M2.value = document.all.divMenu2.offsetLeft + 1;
	document.all.M5.value = document.all.divMenu5.offsetLeft + 1;
	document.all.M6.value = document.all.divMenu6.offsetLeft + 1;
	document.all.M11.value = document.all.divMenu11.offsetLeft + 1;
	document.all.M12.value = document.all.divMenu12.offsetLeft + 1;
	document.all.M13.value = document.all.divMenu13.offsetLeft + 1;
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
      <div align="center"><b> <A STYLE="text-decoration:none" id="divMenu7" href="<%=approot%>/home.jsp"><font color="#30009D">Home</font></A> 
        | <A STYLE="text-decoration:none" id="divMenu0" href="#" onclick="javascript:cordYMenu0(document.all.M0.value);"><font color="#30009D">Employee 
        </font></A>|                 
		<A STYLE="text-decoration:none" id="divMenu6" href="#" onclick="javascript:cordYMenu6(document.all.M6.value);"><font color="#30009D">Reports</font></A> 
        | <A STYLE="text-decoration:none" id="divMenu2" href="#" onclick="javascript:cordYMenu2(document.all.M2.value);"><font color="#30009D">Master 
        Data</font></A> | <A STYLE="text-decoration:none" id="divMenu5" href="#" onclick="javascript:cordYMenu5(document.all.M5.value);"><font color="#30009D">System</font></A> 
		<% boolean menuPayroll=true;
		   if(menuPayroll) { %>
        | <a style="text-decoration:none" id="divMenu11" href="#" onClick="javascript:cordYMenu11(document.all.M11.value);"><font color="#30009D">Payroll 
        Setup</font></a> | <a style="text-decoration:none" id="divMenu12" href="#" onClick="javascript:cordYMenu12(document.all.M12.value);"><font color="#30009D">Overtime</font></a> 
        | <a style="text-decoration:none" id="divMenu13" href="#" onClick="javascript:cordYMenu13(document.all.M13.value);"><font color="#30009D">Payroll 
        Process</font></a>
		  <% } %>
		 | <a style="text-decoration:none" id="divMenu6" href="#" onClick="javascript:cordYMenu6(document.all.M6.value);"><font color="#30009D"></font></a><a style="text-decoration:none" id="divMenu6" href="#" onClick="javascript:cordYMenu6(document.all.M6.value);"><font color="#30009D"></font></a><A STYLE="text-decoration:none" id="divMenu9" href="#" onclick="javascript:showHelp()"><font color="#30009D">Help</font></A> 
        | <A STYLE="text-decoration:none" id="divMenu8" href="<%=approot%>/logout.jsp"><font color="#30009D">Logout</font></A> 
        </b></div>
        </td> 
    </tr>
</table>
