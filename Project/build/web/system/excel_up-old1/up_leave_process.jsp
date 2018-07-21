 <%@ page language = "java" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.System"%>
<%@ page import="java.io.ByteArrayInputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.dimata.util.Excel"%>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="com.dimata.util.blob.TextLoader" %>
<%@ page import="org.apache.poi.hssf.usermodel.*" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ include file = "../../main/javainit.jsp" %>


<%!
public String checkForUnique(Vector vct, String str){
	if(vct!=null && vct.size()>0){
		for(int i=0; i<vct.size(); i++){
			String s = (String)vct.get(i);
			if(s.equalsIgnoreCase(str)){
				return "found multiple employee used .... "+str;
			}
		}
	}
	return "";
}

public String checkForUniquePayroll(Vector vct, String str){
	if(vct!=null && vct.size()>0){
		for(int i=0; i<vct.size(); i++){
			String s = (String)vct.get(i);
			if(s.equalsIgnoreCase(str)){
				return "found multiple payroll used .... "+str;
			}
		}
	}
	return "";
}

%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    long periodId = FRMQueryString.requestLong(request,"period_id");

    String msgString = "";
    if(iCommand == Command.SAVE){
        if(periodId == 0)
            msgString = "<div class=\"errfont\">Please choose the Period leave !</div>";
        else{
            String[] employeeId = request.getParameterValues("employee_id");
            String[] dplm = request.getParameterValues("DP_LM");
            String[] dpadd = request.getParameterValues("DP_ADD");
            String[] dptake = request.getParameterValues("DP_TAKE");
			
            String[] allm = request.getParameterValues("AL_LM");
            String[] aladd = request.getParameterValues("AL_ADD");
            String[] altake = request.getParameterValues("AL_TAKE");

            String[] lllm = request.getParameterValues("LL_LM");
            String[] lladd = request.getParameterValues("LL_ADD");
			String[] lltake = request.getParameterValues("LL_TAKE");
	
			
			//============================================
			//process to handle starting leave stock 
			//open it if want to update existing leavestock
			//=============================================

			
            for(int e=0;e < employeeId.length;e++){
			
                //EmpSchedule empLeave = new EmpSchedule();

                String where = "";
                    where += PstLeaveStock.fieldNames[PstLeaveStock.FLD_EMPLOYEE_ID];
                    where += "=" + employeeId[e];
                Vector vcheck = PstLeaveStock.list(0, 0, where, "");
				System.out.println("...===checking equality===...");
				System.out.println("...===employee size ::: "+vcheck.size()+"===...");
				
				//baru...
				System.out.println("...=== " + employeeId[e]);
				if ((employeeId[e].equals("null")) || (employeeId[e].equals("?"))) {
					System.out.println("...employeeId[e].equals(?)...");				
					msgString = msgString + "\n\t\t...can't save data leave ..."+(e +1);				
				}else{
	
					if (vcheck.size() > 0) {
						System.out.println("---in update ....");
						// update
						try{
							
							LeaveStock empLeave = (LeaveStock) vcheck.get(0);
							empLeave.setEmployeeId(Long.parseLong(""+employeeId[e]));
							
							empLeave.setLeavePeriodId(periodId);
							
							float val = 0;
							val = Float.parseFloat(dplm[e]) + Float.parseFloat(dpadd[e]) - Float.parseFloat(dptake[e]);						
							empLeave.setDpAmount((new Float(val)).intValue());
							
							val = Float.parseFloat(allm[e]) + Float.parseFloat(aladd[e]) - Float.parseFloat(altake[e]); 												
							empLeave.setAlAmount((new Float(val)).intValue());
							
							val = Float.parseFloat(lllm[e]) + Float.parseFloat(lladd[e]) - Float.parseFloat(lltake[e]);												
							empLeave.setLlAmount((new Float(val)).intValue());
							
							val = Float.parseFloat(dpadd[e]);
							//empLeave.setAddDp((new Float(val)).intValue());
							empLeave.setAddDp(0);
							
							val = Float.parseFloat(aladd[e]);
							//empLeave.setAddAl((new Float(val)).intValue());
							empLeave.setAddAl(0);
							
							val = Float.parseFloat(lladd[e]);
							//empLeave.setAddLl((new Float(val)).intValue());
							empLeave.setAddLl(0);
	
							PstLeaveStock.updateExc(empLeave); 
							
							
							
						}
						catch(Exception exc){
							msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+" :: "+exc.toString()+"</div>";
						}
					}
					else {
						// insert
						System.out.println("---in insert ....");
						try {
							LeaveStock empLeave = new LeaveStock();
							empLeave.setEmployeeId(Long.parseLong(""+employeeId[e]));
							empLeave.setLeavePeriodId(periodId);
							
							float val = 0;
							val = Float.parseFloat(dplm[e]) + Float.parseFloat(dpadd[e]) - Float.parseFloat(dptake[e]) ;						
							empLeave.setDpAmount((new Float(val)).intValue());
							
							val = Float.parseFloat(allm[e]) + Float.parseFloat(aladd[e]) - Float.parseFloat(altake[e]); 												; 												
							empLeave.setAlAmount((new Float(val)).intValue());
							
							val = Float.parseFloat(lllm[e]) + Float.parseFloat(lladd[e]) - Float.parseFloat(lltake[e]);												;												
							empLeave.setLlAmount((new Float(val)).intValue());
							
							val = Float.parseFloat(dpadd[e]);
							//empLeave.setAddDp((new Float(val)).intValue());
							empLeave.setAddDp(0);
							
							val = Float.parseFloat(aladd[e]);
							//empLeave.setAddAl((new Float(val)).intValue());
							empLeave.setAddAl(0);
							
							val = Float.parseFloat(lladd[e]);
							//empLeave.setAddLl((new Float(val)).intValue());
							empLeave.setAddLl(0);
	
							PstLeaveStock.insertExc(empLeave);
						}
						catch(Exception exc){
							msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+" :: "+exc.toString()+"</div>";
						}
					}
					
					if(msgString == null || msgString.length()<1)
						msgString = "<div class=\"msginfo\">Data have been saved</div>";
					
				}// end of employee exist
			
            }
			
			
			
			//===================================================================
			//process for starting dptaken
			//===================================================================
			/*
			for(int e=0;e < employeeId.length;e++){
                //EmpSchedule empLeave = new EmpSchedule();

                String where1 = "";
                    where1 += PstDayOfPayment.fieldNames[PstDayOfPayment.FLD_EMPLOYEE_ID];
                    where1 += "=" + employeeId[e];
                Vector vcheck1 = PstDayOfPayment.list(0, 0, where1, "");
				System.out.println("...===checking equality===...");
			
				float valtaken = 0;
				valtaken = Float.parseFloat(dptake[e]);
			
				//baru...
				System.out.println("...=== " + employeeId[e]);
				if ((employeeId[e].equals("null")) || (employeeId[e].equals("?"))) {
					System.out.println("...employeeId[e].equals(?)...");				
					System.out.println("...can't save data dptaken ...");				
				}else{
					if(valtaken<1){
						msgString = msgString + "<div>\n\tDP = 0 .... not saved ...";
					}
					else{
						if (vcheck1.size() > 0) {
							System.out.println("---in update ....");
							// update
							try{
								
								DayOfPayment dayofpay = (DayOfPayment) vcheck1.get(0);
								dayofpay.setEmployeeId(Long.parseLong(""+employeeId[e]));
								
														
								dayofpay.setDuration((new Float(valtaken)).intValue());
								
								dayofpay.setDpFrom(new Date());
								Date dt = new Date();
								dt.setDate(dt.getDate()+((new Float(valtaken)).intValue()));
								dayofpay.setDpTo(dt);							
								dayofpay.setAprDeptheadDate(new Date());							
								dayofpay.setRemarks("Dimata :: DP Taken automatic first input");
		
								PstDayOfPayment.updateExc(dayofpay); 
								
								
								
							}
							catch(Exception exc){
								msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+" :: "+exc.toString()+"</div>";
							}
						}
						else {
							// insert
							System.out.println("---in insert ....");
							try {
								DayOfPayment dayofpay = new DayOfPayment();
								dayofpay.setEmployeeId(Long.parseLong(""+employeeId[e]));
								
														
								dayofpay.setDuration((new Float(valtaken)).intValue());
								
								dayofpay.setDpFrom(new Date());
								Date dt = new Date();
								dt.setDate(dt.getDate()+((new Float(valtaken)).intValue()));
								dayofpay.setDpTo(dt);							
								dayofpay.setAprDeptheadDate(new Date());							
								dayofpay.setRemarks("Dimata :: DP Taken automatic first input");
		
								PstDayOfPayment.insertExc(dayofpay);
							}
							catch(Exception exc){
								msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+" :: "+exc.toString()+"</div>";
							}
						}
						
						if(msgString == null || msgString.length()<1)
							msgString = "<div class=\"msginfo\">Data have been saved</div>";
					}
					
				}// end of employee exist
			
            }*/
			
			
			//================================================================
			//proceed for hr leave data 
			//================================================================
			
			/*for(int e=0;e < employeeId.length;e++){
                //EmpSchedule empLeave = new EmpSchedule();

                String where2 = "";
                    where2 += PstLeave.fieldNames[PstLeave.FLD_EMPLOYEE_ID];
                    where2 += "=" + employeeId[e];
                Vector vcheck2 = PstLeave.list(0, 0, where2, "");
				System.out.println("...===checking equality===...");
			
				float valaltaken = Float.parseFloat(altake[e]);
				float vallltaken = Float.parseFloat(lltake[e]);
			
				//baru...
				System.out.println("...=== " + employeeId[e]);
				if ((employeeId[e].equals("null")) || (employeeId[e].equals("?"))) {
					System.out.println("...employeeId[e].equals(?)...");				
					System.out.println("...can't save data dptaken ...");				
				}else{
					if(valaltaken<1 && vallltaken<1){
						msgString = msgString + "<div>\n\tal = 0 && ll=0 :: index : "+(e+1)+".... not saved ...";
					}
					else{
						if (vcheck2.size() > 0) {
							System.out.println("---in update ....");
							// update
							try{
								
								Leave leave = (Leave) vcheck2.get(0);
								leave.setEmployeeId(Long.parseLong(""+employeeId[e]));
								
								int altake1 = (new Float(valaltaken)).intValue();
								int lltake1 = (new Float(vallltaken)).intValue();
								
								leave.setLeaveFrom(new Date());
								Date dt = new Date();
								dt.setDate(dt.getDate()+(altake1+lltake1));
								leave.setLeaveTo(dt);							
								
								leave.setDuration(altake1+lltake1);
								leave.setAnnualLeave(altake1);
								leave.setLongLeave(lltake1);
								leave.setSubmitDate(new Date());
								leave.setReason("Dimata :: fist input for leave taken ...");
		
								PstLeave.updateExc(leave); 
								
								
								
							}
							catch(Exception exc){
								msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+" :: "+exc.toString()+"</div>";
							}
						}
						else {
							// insert
							System.out.println("---in insert ....");
							try {
								Leave leave = new Leave();
								leave.setEmployeeId(Long.parseLong(""+employeeId[e]));
								
								int altake1 = (new Float(valaltaken)).intValue();
								int lltake1 = (new Float(vallltaken)).intValue();
								
								leave.setLeaveFrom(new Date());
								Date dt = new Date();
								dt.setDate(dt.getDate()+(altake1+lltake1));
								leave.setLeaveTo(dt);							
								
								leave.setDuration(altake1+lltake1);
								leave.setAnnualLeave(altake1);
								leave.setLongLeave(lltake1);
								leave.setSubmitDate(new Date());
								leave.setReason("Dimata :: fist input for leave taken ...");
		
								PstLeave.insertExc(leave);
							}
							catch(Exception exc){
								msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+" :: "+exc.toString()+"</div>";
							}
						}
						
						if(msgString == null || msgString.length()<1)
							msgString = "<div class=\"msginfo\">Data have been saved</div>";
					} 
					
				}// end of employee exist
			
            }*/
			
            
        }
    }

    Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
    Vector periodKey = new Vector(1,1);
    Vector periodValue = new Vector(1,1);
    for(int p=0;p<listPeriod.size();p++){
        Period period = (Period)listPeriod.get(p);
        periodKey.add(period.getPeriod());
        periodValue.add(""+period.getOID());
    }
%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Working Schedule</title>
<script language="JavaScript">
    function cmdSave(){
        document.frmupload.command.value="<%=Command.SAVE%>";
        document.frmupload.action="up_leave_process.jsp";
        document.frmupload.submit();
    }
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){    
        document.frmupload.period_id.style.visibility="hidden";  
    } 

    function hideObjectForLockers(){ 
    }

    function hideObjectForCanteen(){
    }

    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }

    function showObjectForMenu(){  
        document.frmupload.period_id.style.visibility="";  
    }

    function MM_swapImgRestore() { //v3.0
      var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
    }

    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }

    function MM_findObj(n, d) { //v4.0
      var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
      for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
      if(!x && document.getElementById) x=document.getElementById(n); return x;
    }

    function MM_swapImage() { //v3.0
      var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
       if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
    }
</SCRIPT>
<!-- #EndEditable -->
</head> 
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
      <!-- #EndEditable --> </td> 
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
        </tr>
      </table>
    </td> 
  </tr>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
            <font color="#FF6600" face="Arial"><strong>
              <!-- #BeginEditable "contenttitle" -->
                Uploader > Working Schedule
              <!-- #EndEditable --> 
            </strong></font> 
          </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                                <%
									Vector vctEmpNum = new Vector(1,1);
									Vector vctPayroll = new Vector(1,1);
									
                                    TextLoader uploader = new TextLoader();
                                    FileOutputStream fOut = null;
                                    ByteArrayInputStream inStream = null;
                                    Vector v = new Vector();
                                    //======================= ubah ... ====================
                                    int numcol = 11; 
                                    //=====================================================
                                    StringBuffer drawList =  new StringBuffer();
                                    try {
                                        if (iCommand == Command.SAVE) {
                                            Vector vector = (Vector)session.getValue("LEAVE_FORM");
                                            v = (Vector)vector.clone();
                                        }
                                        else {
                                            uploader.uploadText(config, request, response);
                                            Object obj = uploader.getTextFile("file");
                                            byte byteText[] = null;
                                            byteText = (byte[]) obj;
                                            inStream = new ByteArrayInputStream(byteText);
                                            Excel tp = new Excel();
                                            Vector vector = tp.ReadStream((InputStream) inStream, numcol);
                                            if(session.getValue("LEAVE_FORM") != null)
                                                session.removeValue("LEAVE_FORM");
                                            session.putValue("LEAVE_FORM",vector);
                                            v = (Vector)vector.clone();
                                        }
										
										//out.println("v.size() : "+v.size());
										//out.println(v);
										
                                        drawList.append("<form name=\"frmupload\" method=\"post\" action=\"\">"+
                                           "\n<input type=\"hidden\" name=\"command\" value=\""+iCommand+"\">");

                                            if(v.size()>0){
                                                    drawList.append("\n<table cellpadding=\"2\" cellspacing=\"2\" border=\"0\">"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td><li></td>"+
                                                            "\n\t\t<td colspan=\"2\">Choose the Period of Leave</td>"+ 
                                                            "\n\t</tr>"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td>&nbsp;</td>"+
                                                            "\n\t\t<td>Period</td>"+
                                                            "\n\t\t<td>"+ControlCombo.draw("period_id","formElemen","select...",""+periodId,periodValue,periodKey)+"</td>"+
                                                            "\n\t</tr>"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td><li></td>"+
                                                            //======================= ubah ... ====================
                                                            "\n\t\t<td colspan=\"2\">List of Leaving</td>"+ 
                                                            //===========================================
                                                            "\n\t</tr>"+
                                                            "\n</table>"+
                                                     /*       "\n<table cellpadding=\"1\" cellspacing=\"1\" border=\"0\" width=\"100%\" class=\"listgen\">"+
                                                            "\n\t<tr>"+
                                                            //"\n\t\t<td width=\"1%\" rowspan=\"3\" class=\"tableheader\">No</td>"+
                                                            "\n\t\t<td width=\"15%\" rowspan=\"3\" class=\"tableheader\">Employee</td>"+
                                                            "\n\t\t<td width=\"8%\" rowspan=\"3\" class=\"tableheader\">Payroll</td>"+
                                                            "\n\t\t<td width=\"77%\" colspan=\""+(numcol-2)+"\" align=\"center\" class=\"tableheader\">Date</td>"+
                                                            "\n\t</tr>"+
                                                            "\n\t<tr class=\"listheader\">");*/

                                                           // double width =  77/(new Double(numcol)).doubleValue(); 
                                                           /* System.out.println("width == "+width);
                                                            for (int i = numcol; i < (numcol+numcol); i++){
                                                                if ((i%numcol) > 1){
                                                                    //drawList.append("\n\t\t<td align=\"center\" width=\""+width+"%\" class=\"tableheader\">"+(String.valueOf(v.elementAt(numcol))).substring(0,1)+"</td>");
                                                                }
                                                                v.remove(numcol);
                                                            } 
                                                            drawList.append("\n\t</tr>\n\t<tr>");

                                                            for (int h = numcol; h < (numcol+numcol); h++){
                                                                if ((h % numcol) > 1){
                                                                    String dt = String.valueOf(v.elementAt(numcol));
                                                                    drawList.append("\n\t\t<td align=\"center\" width=\""+width+"%\" class=\"tableheader\">"+dt.substring(0,dt.indexOf("."))+"</td>");
                                                                }
                                                                v.remove(numcol);
                                                            } 
                                                            drawList.append("\n\t</tr>\n\t<tr class=\"listgensell\">");

                                                            Hashtable hashSchedule = new Hashtable();
                                                            hashSchedule.put(" ","0");
                                                            Vector listSchSymbol = PstScheduleSymbol.list(0, 0, "", "");
                                                            for (int ls = 0; ls < listSchSymbol.size(); ls++) {
                                                                ScheduleSymbol schSymbol = (ScheduleSymbol) listSchSymbol.get(ls);
                                                                hashSchedule.put(schSymbol.getSymbol(), String.valueOf(schSymbol.getOID()));
                                                            }*/
															
															"\n\t\t<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" class=\"listgen\">"+
															  "\n\t\t\t<tr class=\"tableheader\"> "+
																"\n\t\t\t<td rowspan=\"2\">No</td>"+
																"\n\t\t\t<td rowspan=\"2\">Employee</td>"+
																"\n\t\t\t<td rowspan=\"2\">Payroll</td>"+
																"\n\t\t\t<td colspan=\"3\"> "+
																  "\n\t\t\t<div align=\"center\">DP</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td colspan=\"3\"> "+
																  "\n\t\t\t<div align=\"center\">AL</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td colspan=\"3\"> "+
																  "\n\t\t\t<div align=\"center\">LL</div>"+
																"\n\t\t\t</td>"+
															  "\n\t\t\t</tr>"+															  
															  "\n\t\t\t<tr class=\"tableheader\">"+ 
																"\n\t\t\t<td> "+
																  "\n\t\t\t<div align=\"center\">LM</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td> "+
																"\n\t\t\t  <div align=\"center\">ADD</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td> "+
																"\n\t\t\t  <div align=\"center\">TAKE</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td> "+
																"\n\t\t\t  <div align=\"center\">LM</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td> "+
																"\n\t\t\t  <div align=\"center\">ADD</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td> "+
																 "\n\t\t\t <div align=\"center\">TAKE</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td> "+
																"\n\t\t\t  <div align=\"center\">LM</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td> "+
																"\n\t\t\t  <div align=\"center\">ADD</div>"+
																"\n\t\t\t</td>"+
																"\n\t\t\t<td> "+
																 "\n\t\t\t <div align=\"center\">TAKE</div>"+
																"\n\t\t\t</td>"+
															  "\n\t\t\t</tr>"+															  
															  "\n\t\t\t<tr class=\"listgensell\">");
															  
															double width =  77/(new Double(numcol)).doubleValue(); 
															

                                                            Hashtable hashPayroll = new Hashtable();
                                                            Vector listEmployee = PstEmployee.list(0, 0, "", "");
                                                            for (int e = 0; e < listEmployee.size(); e++) {
                                                                Employee employee = (Employee) listEmployee.get(e);
																if(employee.getResigned()==0){
                                                                	hashPayroll.put(employee.getEmployeeNum(), String.valueOf(employee.getOID()));
																}
                                                            }
															
															
                                                            for (int i = 22; i < v.size(); i++) {
                                                                    //String sch = "";
                                                                    //if(hashSchedule.get(v.elementAt(i))== null)
                                                                            //sch = "?";
                                                                    //else
                                                                            //sch = ""+v.elementAt(i);
                                                                    switch (((i+1) % numcol)) {
                                                                        case 1 ://name
                                                                                drawList.append("\n\t\t<td>"+((i/11)-1)+"</td><td  width=\""+width+"%\" nowrap>"+v.elementAt(i)+"</td>");
                                                                                break;
																				
                                                                        case 2 ://payroll number
																				String payroll = "";
																				
																				String dataPayroll = (String)v.elementAt(i);
																				int idx = dataPayroll.indexOf("'");
																				if(idx > -1){
																					dataPayroll = dataPayroll.substring(2, dataPayroll.length());																					
																				}
																				
																				
                                                                                if(hashPayroll.get(""+dataPayroll)==null)
                                                                                        payroll = "?";
                                                                                else
                                                                                        payroll = ""+dataPayroll;
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\"><input type=\"hidden\" name=\"employee_id\" value=\"" + hashPayroll.get(v.elementAt(i)) + "\">"+payroll+"</td>");
																				
																				
																				out.println(checkForUnique(vctEmpNum, ""+hashPayroll.get(v.elementAt(i))));
																				out.println(checkForUniquePayroll(vctPayroll, payroll));
																				
																				vctEmpNum.add(""+hashPayroll.get(v.elementAt(i)));
																				
                                                                                break;
																				
                                                                        case 3 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"DP_LM\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
                                                                                break;
                                                                        case 4 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"DP_ADD\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
                                                                                break;
                                                                        case 5 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"DP_TAKE\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
                                                                                break;
                                                                        case 6 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"AL_LM\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
                                                                                break;
                                                                        case 7 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"AL_ADD\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
                                                                                break;
                                                                        case 8 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"AL_TAKE\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
                                                                                break;
                                                                        case 9 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"LL_LM\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
                                                                                break;
                                                                        case 10 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"LL_ADD\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
                                                                                break;
                                                                        case 0 :
																				
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"LL_TAKE\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td></tr><tr>");
                                                                                break;
																					
                                                                        default :
                                                                                break;
                                                                    }
                                                                    if (((i+1) % numcol) == 0) {
                                                                            drawList.append("</tr> "+((i != v.size()-1)? "\n\t<tr class=\"listgensell\">":"\n</table>"));
                                                                    }
                                                            }
                                                            drawList.append("<br>"+
                                                                "\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">");
                                                                if(iCommand == Command.SAVE && (msgString != null && msgString.length() > 0)){
                                                                        drawList.append("\n\t<tr>"+
                                                                            "\n\t\t<td colspan=\"4\">"+msgString+"</td>"+
                                                                            "\n\t</tr>"+
                                                                            "\n\t<tr>"+
                                                                            "\n\t\t<td colspan=\"4\">&nbsp;</td>"+
                                                                            "\n\t</tr>");
                                                                }
                                                           // if (iCommand != Command.SAVE) {
                                                            drawList.append("\n\t<tr>"+ 
                                                                "\n\t\t<td width=\"4\"><img src=\""+approot+"/images/spacer.gif\" width=\"4\" height=\"4\"></td>"+
                                                                "\n\t\t<td width=\"24\"><a href=\"javascript:cmdSave()\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image300','','"+approot+"/images/BtnSaveOn.jpg',1)\"><img name=\"Image300\" border=\"0\" src=\""+approot+"/images/BtnSave.jpg\" width=\"24\" height=\"24\" alt=\"Save\"></a></td>"+
                                                                "\n\t\t<td width=\"4\"><img src=\""+approot+"/images/spacer.gif\" width=\"4\" height=\"4\"></td>"+
                                                                "\n\t\t<td nowrap> <a href=\"javascript:cmdSave()\" class=\"command\">Save Leave</a></td>"+
                                                                "\n\t</tr>");
																
															drawList.append("\n\t<tr>"+ 
                                                                "\n\t\t<td width=\"4\"><img src=\""+approot+"/images/spacer.gif\" width=\"4\" height=\"4\"></td>"+
                                                                "\n\t\t<td width=\"24\"><a href=\"up_leave.jsp\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image300','','"+approot+"/images/BtnBackOn.jpg',1)\"><img name=\"Image300\" border=\"0\" src=\""+approot+"/images/BtnBack.jpg\" width=\"24\" height=\"24\" alt=\"Save\"></a></td>"+
                                                                "\n\t\t<td width=\"4\"><img src=\""+approot+"/images/spacer.gif\" width=\"4\" height=\"4\"></td>"+
                                                                "\n\t\t<td nowrap> <a href=\"up_leave.jsp\" class=\"command\">Back To Prev</a></td>"+
                                                                "\n\t</tr>");	
                                                            //    }
                                                            drawList.append("\n</table>");
                                            }
                                            drawList.append("</form>");
                                            if(iCommand != Command.SAVE)
                                                    inStream.close();
                                    }
                                    catch (Exception e) {
                                            System.out.println("---======---\nError : " + e);
                                    }
                                    if(drawList != null && drawList.length()>0){
                                %>
                                <%=drawList%> 
                                <% } %><br><br>
                                <!-- #EndEditable --> 
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td> 
        </tr>
      </table>
    </td> 
        </tr>
      </table>
    </td> 
  </tr>
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
