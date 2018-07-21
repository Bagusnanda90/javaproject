<script language=JavaScript>

// This function used to make pulldown menu only
function fwLoadMenus() 
{
	if (window.fw_menu_0) return;	  	  	  		  		 				

		// Report > Detail
		window.fw_menu_0_1 = new Menu("Detail",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_0_1.addMenuItem("Daily Report", "location='<%=approot%>/report/canteen/detail_daily_report.jsp'");
		fw_menu_0_1.addMenuItem("Weekly Report", "location='<%=approot%>/report/canteen/detail_weekly_report.jsp'");
		fw_menu_0_1.addMenuItem("Monthly Report", "location='<%=approot%>/report/canteen/detail_monthly_report.jsp'");				
		fw_menu_0_1.hideOnMouseOut=true;	 		   	  	  	  

		// Report > Summary
		window.fw_menu_0_2 = new Menu("Summary",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_0_2.addMenuItem("Daily Report", "location='<%=approot%>/report/canteen/summary_daily_report.jsp'");
		fw_menu_0_2.addMenuItem("Weekly Report", "location='<%=approot%>/report/canteen/summary_weekly_report.jsp'");			
		fw_menu_0_2.addMenuItem("Monthly Report", "location='<%=approot%>/report/canteen/summary_monthly_report.jsp'");						
		fw_menu_0_2.hideOnMouseOut=true;	 		   	  	  	  


	// Report
	window.fw_menu_0 = new Menu("root",100,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	fw_menu_0.addMenuItem(fw_menu_0_1);
	fw_menu_0.addMenuItem(fw_menu_0_2);		
	fw_menu_0.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
	fw_menu_0.hideOnMouseOut=true;	 


	// Timekeeping
	window.fw_menu_1 = new Menu("root",115,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	fw_menu_1.addMenuItem("Service Manager", "location='<%=approot%>/system/canteen/svcmgr.jsp'"); 	
	fw_menu_1.addMenuItem("------------------", ""); 		
	fw_menu_1.addMenuItem("Download Data", "location='<%=approot%>/system/canteen/download.jsp'"); 
	fw_menu_1.addMenuItem("Upload Data", "location='<%=approot%>/system/canteen/srcbarcode.jsp'"); 
	fw_menu_1.addMenuItem("------------------", ""); 		
	fw_menu_1.addMenuItem("Check Machine", "location='<%=approot%>/system/canteen/testmachine.jsp'"); 
	fw_menu_1.addMenuItem("Reset Machine", "location='<%=approot%>/system/canteen/reset.jsp'"); 	
	fw_menu_1.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
	fw_menu_1.hideOnMouseOut=true;	


	// System Management
	window.fw_menu_2 = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	fw_menu_2.addMenuItem("User Management", "location='<%=approot%>/admin/userlist.jsp'");
	fw_menu_2.addMenuItem("Back Up Database", "location='<%=approot%>/service/backupdb.jsp'");					
	fw_menu_2.childMenuIcon="<%=approot%>/images/arrows.gif";
	fw_menu_2.hideOnMouseOut=true;

	fw_menu_2.writeMenus();
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
</script>  	  		  

<script language=JavaScript src="<%=approot%>/main/fw_menu.js"></script>
<script language=JavaScript1.2>fwLoadMenus();</script>

<input type="hidden" id="M0">
<input type="hidden" id="M1">
<input type="hidden" id="M2">

<script>
function setScr() 
{
	document.all.M0.value = document.all.divMenu1.offsetLeft + 1;
	document.all.M1.value = document.all.divMenu2.offsetLeft + 1;
	document.all.M2.value = document.all.divMenu3.offsetLeft + 1;
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
                | <A STYLE="text-decoration:none" id="divMenu2" href="#" onclick="javascript:cordYMenu1(document.all.M1.value);"><font color="#30009D">Timekeeping</font></A>
                | <A STYLE="text-decoration:none" id="divMenu3" href="#" onclick="javascript:cordYMenu2(document.all.M2.value);"><font color="#30009D">System</font></A> 		
                | <A STYLE="text-decoration:none" id="divMenu4" href="<%=approot%>/logout.jsp"><font color="#30009D">Logout</font></A> 
            </b></div>
        </td> 
    </tr>
</table>
