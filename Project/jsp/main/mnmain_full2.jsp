
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
            fw_menu_0_1.addMenuItem("Leave Opname", "location='<%=approot%>/employee/leave/srcleavestock.jsp'");
            fw_menu_0_1.addMenuItem("Leave Management", "location='<%=approot%>/employee/leave/srcleave.jsp'");
            fw_menu_0_1.addMenuItem("Day off Payment", "location='<%=approot%>/employee/dayofpayment/srcdayofpayment.jsp'");
            fw_menu_0_1.addMenuItem(fw_menu_0_1_1);
            //fw_menu_0_1.addMenuItem("Occupancy Rate & Manning Req.", "location='<%=approot%>/under_construction.jsp'");
            fw_menu_0_1.childMenuIcon="<%=approot%>/images/arrows.gif"; 
            fw_menu_0_1.hideOnMouseOut=true;	  

            /* Employee > Appraisal */
            window.fw_menu_0_3 = new Menu("Appraisal",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_0_3.addMenuItem("Explanations and Coverage", "location='<%=approot%>/employee/appraisal/expcoverage.jsp'");
            fw_menu_0_3.addMenuItem("Performance Appraisal", "location='<%=approot%>/employee/appraisal/srcappraisal.jsp'");
            fw_menu_0_3.hideOnMouseOut=true;	 

            /* Employee > Recognition */
            window.fw_menu_0_4 = new Menu("Recognition",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_0_4.addMenuItem("Entry per Department", "location='<%=approot%>/employee/recognition/recognitiondep.jsp'");
            fw_menu_0_4.addMenuItem("Update per Employee", "location='<%=approot%>/employee/recognition/srcrecognition.jsp'");
            fw_menu_0_4.hideOnMouseOut=true;
			
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

            /* Employee > Recruitment */
            window.fw_menu_0_6 = new Menu("Recruitment",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_0_6.addMenuItem("Staff Requisition", "location='<%=approot%>/employee/recruitment/srcstaffrequisition.jsp'");
            fw_menu_0_6.addMenuItem("Employment Application", "location='<%=approot%>/employee/recruitment/srcrecrapplication.jsp'");
            fw_menu_0_6.addMenuItem("Orientation Checklist", "location='<%=approot%>/employee/recruitment/srcorichecklist.jsp'");
            fw_menu_0_6.addMenuItem("Reminder", "location='<%=approot%>/employee/recruitment/reminder.jsp'");
            fw_menu_0_6.childMenuIcon="<%=approot%>/images/arrows.gif"; 
            fw_menu_0_6.hideOnMouseOut=true;	 

        /* Employee */
        window.fw_menu_0 = new Menu("root",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_0.addMenuItem("Data Bank", "location='<%=approot%>/employee/databank/srcemployee.jsp'"); 
        fw_menu_0.addMenuItem(fw_menu_0_1);
        fw_menu_0.addMenuItem("Salary", "location='<%=approot%>/employee/salary/srcempsalary.jsp'"); 
        fw_menu_0.addMenuItem(fw_menu_0_3);
        //fw_menu_0.addMenuItem("Recognition", "location='<%=approot%>/employee/recognition/recognition.jsp'");    	  	  
        fw_menu_0.addMenuItem(fw_menu_0_4);
        fw_menu_0.addMenuItem(fw_menu_0_5);
        fw_menu_0.addMenuItem(fw_menu_0_6);
        fw_menu_0.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_0.hideOnMouseOut=true;	 

        /* Locker */
        window.fw_menu_1 = new Menu("root",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_1.addMenuItem("Locker", "location='<%=approot%>/locker/srclocker.jsp'");
        fw_menu_1.addMenuItem("Locker Treatment", "location='<%=approot%>/locker/srclockertreatment.jsp'");
        fw_menu_1.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_1.hideOnMouseOut=true;	

            /* Master Data > Performance Appraisal */
            window.fw_menu_2_0 = new Menu("Performance Appraisal",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_0.addMenuItem("Group Rank", "location='<%=approot%>/masterdata/grouprank.jsp'");   
            fw_menu_2_0.addMenuItem("Category Appraisal", "location='<%=approot%>/masterdata/groupcategory.jsp'"); 
            fw_menu_2_0.addMenuItem("Evaluation Criteria", "location='<%=approot%>/masterdata/evaluation.jsp'"); 
            fw_menu_2_0.hideOnMouseOut=true;	 		   	  	  	  

            /* Master Data > Schedule */
            window.fw_menu_2_1 = new Menu("Schedule",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_1.addMenuItem("Period", "location='<%=approot%>/masterdata/period.jsp'"); 	  
            fw_menu_2_1.addMenuItem("Category", "location='<%=approot%>/masterdata/schedulecategory.jsp'"); 
            fw_menu_2_1.addMenuItem("Symbol", "location='<%=approot%>/masterdata/schedulesymbol.jsp'");
            fw_menu_2_1.hideOnMouseOut=true;

            /* Master Data > Company */
            window.fw_menu_2_2 = new Menu("Company",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_2.addMenuItem("Division", "location='<%=approot%>/masterdata/division.jsp'");
            fw_menu_2_2.addMenuItem("Department", "location='<%=approot%>/masterdata/department.jsp'");
            fw_menu_2_2.addMenuItem("Position", "location='<%=approot%>/masterdata/position.jsp'"); 	  
            fw_menu_2_2.addMenuItem("Section", "location='<%=approot%>/masterdata/section.jsp'"); 
            fw_menu_2_2.addMenuItem("Locker Location", "location='<%=approot%>/masterdata/lockerlocation.jsp'");
            fw_menu_2_2.addMenuItem("Locker Condition", "location='<%=approot%>/masterdata/lockercondition.jsp'");
            fw_menu_2_2.addMenuItem("Leave Period", "location='<%=approot%>/masterdata/leaveperiod.jsp'");
            fw_menu_2_2.hideOnMouseOut=true;	 		   	  	  	  

            /* Master Data > Employee */
            window.fw_menu_2_3 = new Menu("Employee",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_3.addMenuItem("Education", "location='<%=approot%>/masterdata/education.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Level", "location='<%=approot%>/masterdata/level.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Category", "location='<%=approot%>/masterdata/empcategory.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Religion", "location='<%=approot%>/masterdata/religion.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Marital", "location='<%=approot%>/masterdata/marital.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Language", "location='<%=approot%>/masterdata/masterlanguage.jsp'"); 	  
            fw_menu_2_3.addMenuItem("Resigned Reason", "location='<%=approot%>/masterdata/resignedreason.jsp'"); 	  
			fw_menu_2_3.addMenuItem("Training", "location='<%=approot%>/masterdata/training.jsp'"); 
            fw_menu_2_3.hideOnMouseOut=true;	 		   	  	  	  

            /* Master Data > Canteen */
            window.fw_menu_2_4 = new Menu("Canteen",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_4.addMenuItem("Checklist Group", "location='<%=approot%>/canteen/checklistgroup.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Checklist Item", "location='<%=approot%>/canteen/checklistitem.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Checklist Mark", "location='<%=approot%>/canteen/checklistmark.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Menu Item", "location='<%=approot%>/canteen/menuitem.jsp'");
            fw_menu_2_4.addMenuItem("Meal Time", "location='<%=approot%>/canteen/mealtime.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Comment Group", "location='<%=approot%>/canteen/cardquestiongroup.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Comment Question", "location='<%=approot%>/canteen/cardquestion.jsp'"); 	  
            fw_menu_2_4.hideOnMouseOut=true;	 		   	  	  	  

            /* Master Data > Recruitment */
            window.fw_menu_2_5 = new Menu("Recruitment",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_5.addMenuItem("General Questions", "location='<%=approot%>/employee/recruitment/recrgeneral.jsp'"); 	  
            fw_menu_2_5.addMenuItem("Illness Type", "location='<%=approot%>/employee/recruitment/recrillness.jsp'"); 	  
            fw_menu_2_5.addMenuItem("Interview Point", "location='<%=approot%>/employee/recruitment/recrinterviewpoint.jsp'"); 	  
            fw_menu_2_5.addMenuItem("Interviewer", "location='<%=approot%>/employee/recruitment/recrinterviewer.jsp'"); 	  
            fw_menu_2_5.addMenuItem("Interview Factor", "location='<%=approot%>/employee/recruitment/recrinterviewfactor.jsp'"); 	  
            fw_menu_2_5.addMenuItem("Orientation Group", "location='<%=approot%>/employee/recruitment/origroup.jsp'"); 	  
            fw_menu_2_5.addMenuItem("Orientation Activity", "location='<%=approot%>/employee/recruitment/oriactivity.jsp'"); 	  			 	  
            fw_menu_2_5.hideOnMouseOut=true;	 		   	  	  	  

        /* Master Data */
        window.fw_menu_2 = new Menu("root",160,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_2.addMenuItem(fw_menu_2_2); 
        fw_menu_2.addMenuItem(fw_menu_2_3); 
        fw_menu_2.addMenuItem(fw_menu_2_1); 
        fw_menu_2.addMenuItem(fw_menu_2_0);
        fw_menu_2.addMenuItem(fw_menu_2_4);
        fw_menu_2.addMenuItem(fw_menu_2_5);
        fw_menu_2.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_2.hideOnMouseOut=true;	

            /* Clinic > Medicine */
            window.fw_menu_3_0 = new Menu("Medicine",170,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_3_0.addMenuItem("List Of Medicine", "location='<%=approot%>/clinic/stock/medicine.jsp'");
            fw_menu_3_0.addMenuItem("Medicine Consumption", "location='<%=approot%>/clinic/stock/medconsump.jsp'");
            fw_menu_3_0.hideOnMouseOut=true;

            /* Clinic > Disease */ 
            window.fw_menu_3_1 = new Menu("Disease",100,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_3_1.addMenuItem("Type", "location='<%=approot%>/clinic/disease/diseasetype.jsp'");
            fw_menu_3_1.hideOnMouseOut=true;	 

            /* Clinic > Medical Expense */
            window.fw_menu_3_2 = new Menu("Medical",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_3_2.addMenuItem("Group", "location='<%=approot%>/clinic/medexpense/med_group.jsp'");
			fw_menu_3_2.addMenuItem("Type", "location='<%=approot%>/clinic/medexpense/med_type.jsp'");
            //fw_menu_3_2.addMenuItem("Expense Recapitulation", "location='<%=approot%>/clinic/medexpense/exp_rec.jsp'");
			fw_menu_3_2.addMenuItem("Medical Record", "location='<%=approot%>/clinic/disease/scrmedicalrecord.jsp'");									
            fw_menu_3_2.hideOnMouseOut=true;	 

        /* Clinic */
        window.fw_menu_3 = new Menu("root",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_3.addMenuItem(fw_menu_3_0);
        fw_menu_3.addMenuItem("Employee Visit", "location='<%=approot%>/clinic/empvisit/srcemployeevisit.jsp'"); 
        fw_menu_3.addMenuItem("Guest Handling", "location='<%=approot%>/clinic/guest/srcguesthandling.jsp'"); 
        fw_menu_3.addMenuItem(fw_menu_3_1); 
        fw_menu_3.addMenuItem(fw_menu_3_2); 		
        //fw_menu_3.addMenuItem("Insurance Scheme", "location='<%=approot%>/under_construction.jsp'"); 
        fw_menu_3.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_3.hideOnMouseOut=true;

        /* Canteen */
        window.fw_menu_4 = new Menu("root",140,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_4.addMenuItem("Menu List Monthly", "location='<%=approot%>/canteen/menulist.jsp'"); 
        fw_menu_4.addMenuItem("Daily Evaluation", "location='<%=approot%>/canteen/cafechecklist.jsp'");
        fw_menu_4.addMenuItem("Comment Card", "location='<%=approot%>/canteen/srccommentcardheader.jsp'");
        fw_menu_4.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
        fw_menu_4.hideOnMouseOut=true;

            /* System > Timekeeping */
            window.fw_menu_5_1 = new Menu("Timekeeping",120,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_1.addMenuItem("Service Manager", "location='<%=approot%>/system/timekeeping/svcmgr.jsp'");
            //fw_menu_5_1.addMenuItem("Upload Barcode", "location='<%=approot%>/system/timekeeping/srcbarcode.jsp'");
            fw_menu_5_1.addMenuItem("Download Data", "location='<%=approot%>/system/timekeeping/download.jsp'");
            //fw_menu_5_1.addMenuItem("Reset Machine", "location='<%=approot%>/system/timekeeping/reset.jsp'");
            fw_menu_5_1.hideOnMouseOut=true;	 		   	  	  	  
 
            /* System > Barcode */
            window.fw_menu_5_2 = new Menu("Barcode",190,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_5_2.addMenuItem("Insert Barcode to Database", "location='<%=approot%>/system/timekeeping/insert2db.jsp'");
            fw_menu_5_2.addMenuItem("Upload Barcode to Machine", "location='<%=approot%>/system/timekeeping/upload2tma.jsp'");
            fw_menu_5_2.addMenuItem("Insert & Upload Barcode", "location='<%=approot%>/system/timekeeping/srcbarcode.jsp'");
			fw_menu_5_2.addMenuItem("Upload Per Employee", "location='<%=approot%>/system/timekeeping/upload2tma4emp.jsp'");
            fw_menu_5_2.hideOnMouseOut=true;
			
			
            /* System > Working Schedule */
            window.fw_menu_5_3 = new Menu("Working Schedule",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");           
            fw_menu_5_3.addMenuItem("Upload & Insert Schedule", "location='<%=approot%>/system/excel_up/up_work_schedule.jsp'");
            fw_menu_5_3.hideOnMouseOut=true;	 		   	  	  	  

        /* System */
        window.fw_menu_5 = new Menu("root",125,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_5.addMenuItem("User", "location='<%=approot%>/admin/userlist.jsp'");
        fw_menu_5.addMenuItem("Group", "location='<%=approot%>/admin/grouplist.jsp'"); 
        fw_menu_5.addMenuItem("Privilege", "location='<%=approot%>/admin/privilegelist.jsp'");
		fw_menu_5.addMenuItem("Back Up Service", "location='<%=approot%>/service/back_up.jsp'");
		fw_menu_5.addMenuItem("Leave Stock Service", "location='<%=approot%>/service/leave_stock.jsp'"); /*tambahan wayan*/
        fw_menu_5.addMenuItem("System Properties", "location='<%=approot%>/system/index.jsp'");
        fw_menu_5.addMenuItem("System History", "location='<%=approot%>/common/logger/history.jsp'");
        fw_menu_5.addMenuItem(fw_menu_5_2);
        fw_menu_5.addMenuItem(fw_menu_5_1);
		fw_menu_5.addMenuItem(fw_menu_5_3);
        fw_menu_5.childMenuIcon="<%=approot%>/images/arrows.gif";
        fw_menu_5.hideOnMouseOut=true;

            /* Reports > Employment */
            window.fw_menu_6_0 = new Menu("Employment",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_0.addMenuItem("Staff Recapitulation", "location='<%=approot%>/report/employment/staffrecapitulation.jsp'");
            fw_menu_6_0.hideOnMouseOut=true;	 		   	  	  	  

            /* Reports > Leave & DP Record */
            window.fw_menu_6_1 = new Menu("Leave & DP Record",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_1.addMenuItem("Recap. Per Department", "location='<%=approot%>/report/leavedp/general.jsp'");
            fw_menu_6_1.addMenuItem("Detail Per Employee", "location='<%=approot%>/report/leavedp/detail.jsp'");
            //fw_menu_6_1.addMenuItem("Long Leave", "location='<%=approot%>/under_construction.jsp'");
            fw_menu_6_1.hideOnMouseOut=true;	 		   	  	  	  

            /* Reports > Staff Control */
            window.fw_menu_6_2 = new Menu("Staff Control",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_2.addMenuItem("Attendance Record", "location='<%=approot%>/employee/presence/srcviewpresence.jsp'");
            fw_menu_6_2.addMenuItem("Manning Summary", "location='<%=approot%>/report/presence/manning.jsp'");
            fw_menu_6_2.hideOnMouseOut=true;	 		   	  	  	  

            /* Reports > Lockers */
            window.fw_menu_6_3 = new Menu("Lockers",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_3.addMenuItem("Lockers", "location='<%=approot%>/report/lockers/lockersglobal.jsp'");
            fw_menu_6_3.hideOnMouseOut=true;	 		   	  	  	  

            /* Reports > Clinic */
            window.fw_menu_6_4 = new Menu("Clinic",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_4.addMenuItem("Medical Expenses", "location='<%=approot%>/report/clinic/expense/exp_rec.jsp'");
            fw_menu_6_4.addMenuItem("Summary Receipt", "location='<%=approot%>/report/clinic/expense/summ_receipt.jsp'");			
            //fw_menu_6_4.addMenuItem("Clinic Checks", "location='<%=approot%>/under_construction.jsp'");
            //fw_menu_6_4.addMenuItem("Doctor's Certificate", "location='<%=approot%>/under_construction.jsp'");
            fw_menu_6_4.addMenuItem("Employee Visits", "location='<%=approot%>/clinic/empvisit/srcemployeevisit.jsp'"); 
            fw_menu_6_4.addMenuItem("Guest Handling", "location='<%=approot%>/clinic/guest/srcguesthandling.jsp'"); 
            fw_menu_6_4.hideOnMouseOut=true;	 		   	  	  	  

            /* Reports > Recognition */
            window.fw_menu_6_5 = new Menu("Recognition",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_5.addMenuItem("Monthly Report", "location='<%=approot%>/report/recognition/recmonthly.jsp'");
            fw_menu_6_5.addMenuItem("Quarterly Report", "location='<%=approot%>/report/recognition/recquarterly.jsp'");
            fw_menu_6_5.addMenuItem("Yearly Report", "location='<%=approot%>/report/recognition/recyearly.jsp'");
            fw_menu_6_5.hideOnMouseOut=true;
			
			/* Reports > Presence */
            window.fw_menu_6_6 = new Menu("Presence",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_6.addMenuItem("Daily Report", "location='<%=approot%>/report/presence/daily_report.jsp'");
            fw_menu_6_6.addMenuItem("Monthly Report", "location='<%=approot%>/report/presence/monthly_presence_report.jsp'");			
            fw_menu_6_6.hideOnMouseOut=true;	
			
			/* Reports > Presence */
            window.fw_menu_6_7 = new Menu("Training",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_6_7.addMenuItem("Monthly Report", "location='<%=approot%>/report/training/monthly_tr_rpt.jsp'");
            fw_menu_6_7.hideOnMouseOut=true;	 		   	  	  	  
	 		   	  	  	  

        /* Reports */
        window.fw_menu_6 = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
        fw_menu_6.addMenuItem(fw_menu_6_0);
        fw_menu_6.addMenuItem(fw_menu_6_1); 
        fw_menu_6.addMenuItem(fw_menu_6_2);
        fw_menu_6.addMenuItem(fw_menu_6_3);
        fw_menu_6.addMenuItem(fw_menu_6_4);
        fw_menu_6.addMenuItem(fw_menu_6_5);
		fw_menu_6.addMenuItem(fw_menu_6_6);
		fw_menu_6.addMenuItem(fw_menu_6_7);
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
        hideObjectForEmployee();
    }

    function cordYMenu1(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_1,cordX,posY);
        hideObjectForLockers();
    }	

    function cordYMenu2(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_2,cordX,posY);
        hideObjectForMasterdata();
    }	

    function cordYMenu3(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_3,cordX,posY);
        hideObjectForClinic();
    }	

    function cordYMenu4(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_4,cordX,posY);
        hideObjectForCanteen();
    }	

    function cordYMenu5(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_5,cordX,posY);
    }

    function cordYMenu6(cordX){		
        posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
        window.FW_showMenu(window.fw_menu_6,cordX,posY);
        //hideObjectForReports();
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
<script>
    function setScr() {
        document.all.M0.value = document.all.divMenu0.offsetLeft + 1;
	document.all.M1.value = document.all.divMenu1.offsetLeft + 1;
	document.all.M2.value = document.all.divMenu2.offsetLeft + 1;
	document.all.M3.value = document.all.divMenu3.offsetLeft + 1;
	document.all.M4.value = document.all.divMenu4.offsetLeft + 1;
	document.all.M5.value = document.all.divMenu5.offsetLeft + 1;
	document.all.M6.value = document.all.divMenu6.offsetLeft + 1;
    }
    window.onload = setScr;
    window.onresize = setScr;
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="10">
  <tr>  
    <td nowrap height="10" valign="middle"> 
      <div align="center"><b> 
                  <A STYLE="text-decoration:none" id="divMenu7" href="<%=approot%>/home.jsp"><font color="#30009D">Home</font></A>
                | <A STYLE="text-decoration:none" id="divMenu0" href="#" onclick="javascript:cordYMenu0(document.all.M0.value);"><font color="#30009D">Employee</font></A> 
                | <A STYLE="text-decoration:none" id="divMenu1" href="#" onclick="javascript:cordYMenu1(document.all.M1.value);"><font color="#30009D">Locker</font></A> 
                | <A STYLE="text-decoration:none" id="divMenu4" href="#" onclick="javascript:cordYMenu4(document.all.M4.value);"><font color="#30009D">Canteen</font></A> 
                | <A STYLE="text-decoration:none" id="divMenu3" href="#" onclick="javascript:cordYMenu3(document.all.M3.value);"><font color="#30009D">Clinic</font></A> 
                | <A STYLE="text-decoration:none" id="divMenu6" href="#" onclick="javascript:cordYMenu6(document.all.M6.value);"><font color="#30009D">Reports</font></A> 
                | <A STYLE="text-decoration:none" id="divMenu2" href="#" onclick="javascript:cordYMenu2(document.all.M2.value);"><font color="#30009D">Master Data</font></A> 		
                | <A STYLE="text-decoration:none" id="divMenu5" href="#" onclick="javascript:cordYMenu5(document.all.M5.value);"><font color="#30009D">System</font></A> 		
                | <A STYLE="text-decoration:none" id="divMenu8" href="<%=approot%>/logout.jsp"><font color="#30009D">Logout</font></A> 
            </b></div>
        </td> 
    </tr>
</table>
