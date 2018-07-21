<script language=JavaScript>

// This function used to make pulldown menu only
function fwLoadMenus() 
{
	if (window.fw_menu_0) return;	  	  	  		  		 				

		// Report > Detail
		window.fw_menu_0_1 = new Menu("Detail",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_0_1.addMenuItem("Daily Report", "location='<%=approotCanteen%>/report/canteen/detail_daily_report.jsp'");
		fw_menu_0_1.addMenuItem("Weekly Report", "location='<%=approotCanteen%>/report/canteen/detail_weekly_report.jsp'");
		fw_menu_0_1.addMenuItem("Monthly Report", "location='<%=approotCanteen%>/report/canteen/detail_monthly_report.jsp'");				
		fw_menu_0_1.hideOnMouseOut=true;	 		   	  	  	  

		// Report > Summary
		window.fw_menu_0_2 = new Menu("Summary",160,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_0_2.addMenuItem("Daily Meal Record", "location='<%=approotCanteen%>/report/canteen/summary_daily_report.jsp'");
		fw_menu_0_2.addMenuItem("Periodic Meal Record", "location='<%=approotCanteen%>/report/canteen/summary_periodic_report.jsp'");			
		fw_menu_0_2.addMenuItem("Meal Report", "location='<%=approotCanteen%>/report/canteen/periodic_meal_report.jsp'");
                fw_menu_0_2.addMenuItem("Meal Report Department", "location='<%=approotCanteen%>/report/canteen/summary_periodic_department.jsp'");
                fw_menu_0_2.addMenuItem("Monthly Canteen Report", "location='<%=approotCanteen%>/report/canteen/monthly_canteen_report.jsp'");
		fw_menu_0_2.hideOnMouseOut=true;	 		   	  	  	  


	// Report
	window.fw_menu_0 = new Menu("root",100,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	fw_menu_0.addMenuItem(fw_menu_0_1);
	fw_menu_0.addMenuItem(fw_menu_0_2);		
	fw_menu_0.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
	fw_menu_0.hideOnMouseOut=true;	 


	// Data Management
            /* Master Data > Canteen */
            window.fw_menu_2_4 = new Menu("Canteen",150,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
            fw_menu_2_4.addMenuItem("Checklist Group", "location='<%=approotCanteen%>/canteen/checklistgroup.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Checklist Item", "location='<%=approotCanteen%>/canteen/checklistitem.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Checklist Mark", "location='<%=approotCanteen%>/canteen/checklistmark.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Menu Item", "location='<%=approotCanteen%>/canteen/menuitem.jsp'");
            fw_menu_2_4.addMenuItem("Meal Time", "location='<%=approotCanteen%>/canteen/mealtime.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Comment Group", "location='<%=approotCanteen%>/canteen/cardquestiongroup.jsp'"); 	  
            fw_menu_2_4.addMenuItem("Comment Question", "location='<%=approotCanteen%>/canteen/cardquestion.jsp'"); 	  
            fw_menu_2_4.hideOnMouseOut=true;	
         
	window.fw_menu_1 = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	fw_menu_1.addMenuItem("Manual Visitation", "location='<%=approotCanteen%>/canteen/srccanteenvisitation.jsp'");
	fw_menu_1.addMenuItem("Canteen Schedule", "location='<%=approotCanteen%>/canteen/canteenschedule.jsp'");					
             fw_menu_1.addMenuItem(fw_menu_2_4);
	fw_menu_1.childMenuIcon="<%=approot%>/images/arrows.gif";
	fw_menu_1.hideOnMouseOut=true;

	// Timekeeping
	window.fw_menu_2 = new Menu("root",115,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	fw_menu_2.addMenuItem("Service Manager", "location='<%=approotCanteen%>/system/canteenpro/svcmgr.jsp'"); 	
        //--------------Khusus Bali dinasty-----------------------     
	//fw_menu_2.addMenuItem("------------------", ""); 		
	//fw_menu_2.addMenuItem("Download Data", "location='<%=approotCanteen%>/system/canteenpro/download.jsp'"); 
	//fw_menu_2.addMenuItem("Upload Data", "location='<%=approotCanteen%>/system/canteenpro/srcbarcode.jsp'"); 
        //-------------END Bali dinasty--------------------------- 
	fw_menu_2.addMenuItem("------------------", ""); 		
	fw_menu_2.addMenuItem("Check Machine", "location='<%=approotCanteen%>/system/canteenpro/testmachine.jsp'"); 
	fw_menu_2.addMenuItem("Reset Machine", "location='<%=approotCanteen%>/system/canteenpro/reset.jsp'"); 	
	fw_menu_2.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
	fw_menu_2.hideOnMouseOut=true;	


	// System Management
	window.fw_menu_3 = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	fw_menu_3.addMenuItem("User Management", "location='<%=approotCanteen%>/admin/userlist.jsp'");
	fw_menu_3.addMenuItem("Back Up Database", "location='<%=approotCanteen%>/service/backupdb.jsp'");					
	fw_menu_3.childMenuIcon="<%=approot%>/images/arrows.gif";
	fw_menu_3.hideOnMouseOut=true;

	fw_menu_3.writeMenus();
} 

function MM_jumpMenu(targ,selObj,restore)
{ 
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
</script>  	  		  

<script language=JavaScript src="<%=approot%>/main/fw_menu.js"></script>
<script language=JavaScript1.2>fwLoadMenus();</script>

<input type="hidden" id="M0">
<input type="hidden" id="M1">
<input type="hidden" id="M2">
<input type="hidden" id="M3">

<script>
function setScr() 
{
	document.all.M0.value = document.all.divMenu1.offsetLeft + 1;
	document.all.M1.value = document.all.divMenu2.offsetLeft + 1;
	document.all.M2.value = document.all.divMenu3.offsetLeft + 1;
	document.all.M3.value = document.all.divMenu4.offsetLeft + 1;
}

	window.onload = setScr;
	window.onresize = setScr;
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="10">
  <tr>  
    <td nowrap height="10" valign="middle"> 
      <div align="center"><b> 
                  <A STYLE="text-decoration:none" id="divMenu0" href="<%=approot%>/home.jsp"><font color="#30009D">Home</font></A>
                | <A STYLE="text-decoration:none" id="divMenu1" href="#" onclick="javascript:cordYMenu0(document.all.M0.value);"><font color="#30009D">Reports</font></A> 
                | <A STYLE="text-decoration:none" id="divMenu2" href="#" onclick="javascript:cordYMenu1(document.all.M1.value);"><font color="#30009D">Data</font></A>
                		<!-- ga dipake-->
                <A STYLE="text-decoration:none" id="divMenu3" href="#" onclick="javascript:cordYMenu2(document.all.M2.value);"><font color="#30009D"></font></A> 				
                <A STYLE="text-decoration:none" id="divMenu4" href="#" onclick="javascript:cordYMenu3(document.all.M3.value);"><font color="#30009D"></font></A> 
                <A STYLE="text-decoration:none" id="divMenu5" href="<%=approot%>/logout.jsp"><font color="#30009D"></font></A> 
            </b></div>
        </td> 
    </tr>
</table>
