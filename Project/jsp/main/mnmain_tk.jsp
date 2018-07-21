
<script language=JavaScript>
/* This function used to make pulldown menu only */
function fwLoadMenus() {
	if (window.fw_menu_0) return;	  	  	  		  		 				

		// Timekeeping > Barcode 
		window.fw_menu_1_0 = new Menu("Barcode",180,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_1_0.addMenuItem("Insert Barcode to Database", "location='<%=approot%>/system/timekeeping/insert2db.jsp'");
		fw_menu_1_0.addMenuItem("Upload Barcode to Machine", "location='<%=approot%>/system/timekeeping/upload2tma.jsp'");
		//fw_menu_1_0.addMenuItem("Insert & Upload Barcode", "location='<%=approot%>/system/timekeeping/srcbarcode.jsp'");
		//fw_menu_1_0.addMenuItem("Upload Per Employee", "location='<%=approot%>/system/timekeeping/upload2tma4emp.jsp'");
		fw_menu_1_0.hideOnMouseOut=true;			

		// Timekeeping > Timekeeping 
		window.fw_menu_1_1 = new Menu("Timekeeping",110,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
		fw_menu_1_1.addMenuItem("Service Manager", "location='<%=approot%>/system/timekeeping/svcmgr.jsp'");
		//fw_menu_1_1.addMenuItem("Upload Barcode", "location='<%=approot%>/system/timekeeping/srcbarcode.jsp'");
		fw_menu_1_1.addMenuItem("Download Data", "location='<%=approot%>/system/timekeeping/download.jsp'");
		//fw_menu_1_1.addMenuItem("Reset Machine", "location='<%=approot%>/system/timekeeping/reset.jsp'");
		fw_menu_1_1.hideOnMouseOut=true;	 		   	  	  	  


	// Timekeeping 
	window.fw_menu_1 = new Menu("root",130,17,"Verdana, Arial, Helvetica, sans-serif",10,"#000000","#ffffff","#ffffff","#000084");
	fw_menu_1.addMenuItem(fw_menu_1_0);
	fw_menu_1.addMenuItem(fw_menu_1_1);		
	fw_menu_1.childMenuIcon="<%=approot%>/images/arrows.gif"; 	  	  	  
	fw_menu_1.hideOnMouseOut=true;

	fw_menu_1.writeMenus();
} 

function MM_jumpMenu(targ,selObj,restore){ 
	eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
	if (restore) selObj.selectedIndex=0;
}
</script>


<script language="JavaScript"> 
function cordYMenu0(cordX){		
	posY = Math.abs(document.all.TOPTITLE.height) + Math.abs(document.all.MAINMENU.height) + 4;
	window.FW_showMenu(window.fw_menu_1,cordX,posY);
	hideObjectForEmployee();
}
</script>  	  		  
<script language=JavaScript src="<%=approot%>/main/fw_menu.js"></script>
<script language=JavaScript1.2>fwLoadMenus();</script>
<input type="hidden" id="M0">
<script>
function setScr() {
	document.all.M0.value = document.all.divMenu0.offsetLeft + 1;
}

window.onload = setScr;
window.onresize = setScr;
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="10">
  <tr>  
    <td nowrap height="10" valign="middle"> 
      <div align="center"><b> 
	  <A STYLE="text-decoration:none" href="<%=approot%>/home.jsp"><font color="#30009D">Home</font></A>
	  | <A STYLE="text-decoration:none" id="divMenu0" href="#" onclick="javascript:cordYMenu0(document.all.M0.value);"><font color="#30009D">Timekeeping</font></A> 
	  | <A STYLE="text-decoration:none" href="<%=approot%>/logout.jsp"><font color="#30009D">Logout</font></A> 
	  </b></div>
     </td> 
  </tr>
</table>
