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
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
<%
    int NUM_HEADER = 3;
	int NUM_CELL = -1;
	
	
	int iCommand = FRMQueryString.requestCommand(request);
    long periodId = FRMQueryString.requestLong(request,"period_id");

    String msgString = "";
    if(iCommand == Command.SAVE){
        if(periodId == 0)
            msgString = "<div class=\"errfont\">Please choose the Period of Working Schedule !</div>";
        else{
            String[] employeeId = request.getParameterValues("employee_id");
			
			System.out.println(" %%%%%%%%%%%%%%%%% employeeId : "+employeeId.length);
			
            String[] d21 = request.getParameterValues("D21");
            String[] d22 = request.getParameterValues("D22");
            String[] d23 = request.getParameterValues("D23");
            String[] d24 = request.getParameterValues("D24");
            String[] d25 = request.getParameterValues("D25");
            String[] d26 = request.getParameterValues("D26");
            String[] d27 = request.getParameterValues("D27");
            String[] d28 = request.getParameterValues("D28");
            String[] d29 = request.getParameterValues("D29");
            String[] d30 = request.getParameterValues("D30");
            String[] d31 = request.getParameterValues("D31");
            String[] d1  = request.getParameterValues("D1");
            String[] d2  = request.getParameterValues("D2");
            String[] d3  = request.getParameterValues("D3");
            String[] d4  = request.getParameterValues("D4");
            String[] d5  = request.getParameterValues("D5");
            String[] d6  = request.getParameterValues("D6");
            String[] d7  = request.getParameterValues("D7");
            String[] d8  = request.getParameterValues("D8");
            String[] d9  = request.getParameterValues("D9");
            String[] d10 = request.getParameterValues("D10");
            String[] d11 = request.getParameterValues("D11");
            String[] d12 = request.getParameterValues("D12");
            String[] d13 = request.getParameterValues("D13");
            String[] d14 = request.getParameterValues("D14");
            String[] d15 = request.getParameterValues("D15");
            String[] d16 = request.getParameterValues("D16");
            String[] d17 = request.getParameterValues("D17");
            String[] d18 = request.getParameterValues("D18");
            String[] d19 = request.getParameterValues("D19");
            String[] d20 = request.getParameterValues("D20");

            for(int e=0;e < employeeId.length;e++){
                //EmpSchedule empSchedule = new EmpSchedule();
                String where = "";
                    where += PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId;
                    where += " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID];
                    where += "=" + employeeId[e];
                Vector vcheck = PstEmpSchedule.list(0, 0, where, "");
				System.out.println("...===checking equality===...");
				System.out.println("...payroll num : "+employeeId[e]);
			
			//baru...
			System.out.println("...=== " + d29[e]);
			if (d29[e].equals("null")) {
				System.out.println("...d29[e].equals(?)...");
				d29[e] = "0";
			}
			System.out.println("...=== " + d30[e]);
			if (d30[e].equals("null")) {
				System.out.println("...d30[e].equals(?)...");
				d30[e] = "0";
			}
			System.out.println("...=== " + d31[e]);
			if (d31[e].equals("null")) {
				System.out.println("...d31[e].equals(?)...");
				d31[e] = "0";
			}

                if (vcheck.size() > 0) {
                    // update
                    try{
                        EmpSchedule empSchedule = (EmpSchedule) vcheck.get(0);
                        empSchedule.setEmployeeId(Long.parseLong(""+employeeId[e]));
                        empSchedule.setPeriodId(periodId);
                        
                        
                        //cek batasan hari ke masa lalu untuk bisa di edit atau tidak (melebihi batasan hari di position)
                            EmpSchedule empScheduleBeforeUpdate = new EmpSchedule();
                            try {
                                empScheduleBeforeUpdate = PstEmpSchedule.fetchExc(empSchedule.getOID());
                            } catch (Exception ex) {}
                                Employee employee = new Employee();
                            try {
                                employee = PstEmployee.fetchExc(empScheduleBeforeUpdate.getEmployeeId());
                            } catch (Exception ex) {}
                                Position position = new Position();
                            try {
                                position = positionOfLoginUser;
                            } catch (Exception ex) {}

                            double Beforedays =  position.getDeadlineScheduleBefore()/24;
                            Date deadDay = new Date();
                            //Date today = new Date();

                            //mencari batasan harinya (sebelumnya)
                            deadDay.setHours(deadDay.getHours() - position.getDeadlineScheduleBefore());


                            Period periodDead = PstPeriod.getPeriodBySelectedDate(deadDay);
                            //dibuat agar beberapa hari sebelumnya tidak bisa dirubah
                            if (periodDead.getOID() == empScheduleBeforeUpdate.getPeriodId()){
                               int startDate = periodDead.getStartDate().getDate();
                               int endDate = periodDead.getEndDate().getDate();

                               Date startDateClone = (Date) periodDead.getStartDate().clone() ;
                               int nilai = 0;
                               do{
                                  // startDateClone.setDate(startDateClone.getDate()+1);
                                   if (startDateClone.getDate() == deadDay.getDate() || nilai == 1){
                                       //mencari harinya keberapa
                                              if (startDateClone.getDate() == 1){
                                                 empSchedule.setD1(Long.parseLong(""+d1[e]));
                                              }else if (startDateClone.getDate() == 2){
                                                 empSchedule.setD2(Long.parseLong(""+d2[e]));
                                              }else if (startDateClone.getDate() == 3){
                                                 empSchedule.setD3(Long.parseLong(""+d3[e]));
                                              }else if (startDateClone.getDate() == 4){
                                                 empSchedule.setD4(Long.parseLong(""+d4[e]));
                                              }else if (startDateClone.getDate() == 5){
                                                 empSchedule.setD5(Long.parseLong(""+d5[e]));
                                              }else if (startDateClone.getDate() == 6){
                                                 empSchedule.setD6(Long.parseLong(""+d6[e]));
                                              }else if (startDateClone.getDate() == 7){
                                                 empSchedule.setD7(Long.parseLong(""+d7[e]));
                                              }else if (startDateClone.getDate() == 8){
                                                 empSchedule.setD8(Long.parseLong(""+d8[e]));
                                              }else if (startDateClone.getDate() == 9){
                                                 empSchedule.setD9(Long.parseLong(""+d9[e]));
                                              }else if (startDateClone.getDate() == 10){
                                                 empSchedule.setD10(Long.parseLong(""+d10[e]));
                                              }else if (startDateClone.getDate() == 11){
                                                 empSchedule.setD11(Long.parseLong(""+d11[e]));
                                              }else if (startDateClone.getDate() == 12){
                                                 empSchedule.setD12(Long.parseLong(""+d12[e]));
                                              }else if (startDateClone.getDate() == 13){
                                                 empSchedule.setD13(Long.parseLong(""+d13[e]));
                                              }else if (startDateClone.getDate() == 14){
                                                 empSchedule.setD14(Long.parseLong(""+d14[e]));
                                              }else if (startDateClone.getDate() == 15){
                                                 empSchedule.setD15(Long.parseLong(""+d15[e]));
                                              }else if (startDateClone.getDate() == 16){
                                                 empSchedule.setD16(Long.parseLong(""+d16[e]));
                                              }else if (startDateClone.getDate() == 17){
                                                 empSchedule.setD17(Long.parseLong(""+d17[e]));
                                              }else if (startDateClone.getDate() == 18){
                                                 empSchedule.setD18(Long.parseLong(""+d18[e]));
                                              }else if (startDateClone.getDate() == 19){
                                                 empSchedule.setD19(Long.parseLong(""+d19[e]));
                                              }else if (startDateClone.getDate() == 20){
                                                 empSchedule.setD20(Long.parseLong(""+d20[e]));
                                              }else if (startDateClone.getDate() == 21){
                                                 empSchedule.setD21(Long.parseLong(""+d21[e]));
                                              }else if (startDateClone.getDate() == 22){
                                                 empSchedule.setD22(Long.parseLong(""+d22[e]));
                                              }else if (startDateClone.getDate() == 23){
                                                 empSchedule.setD23(Long.parseLong(""+d23[e]));
                                              }else if (startDateClone.getDate() == 24){
                                                 empSchedule.setD24(Long.parseLong(""+d24[e]));
                                              }else if (startDateClone.getDate() == 25){
                                                 empSchedule.setD25(Long.parseLong(""+d25[e]));
                                              }else if (startDateClone.getDate() == 26){
                                                 empSchedule.setD26(Long.parseLong(""+d26[e]));
                                              }else if (startDateClone.getDate() == 27){
                                                 empSchedule.setD27(Long.parseLong(""+d27[e]));
                                              }else if (startDateClone.getDate() == 28){
                                                 empSchedule.setD28(Long.parseLong(""+d28[e]));
                                              }else if (startDateClone.getDate() == 29){
                                                 empSchedule.setD29(Long.parseLong(""+d29[e]));
                                              }else if (startDateClone.getDate() == 30){
                                                 empSchedule.setD30(Long.parseLong(""+d30[e]));
                                              }else if (startDateClone.getDate() == 31){
                                                 empSchedule.setD31(Long.parseLong(""+d31[e]));
                                              }
                                        
                                       nilai = 1;
                                   }
                                   startDateClone.setDate(startDateClone.getDate()+1);
                               }while(startDateClone.getDate() != endDate);



                            }  else {
                             //mencari berada sebelum periode ini atau setelahnya
                             //karena jika setelahnya maka dia masih bisa diupdate dan jika sebelumnya maka tidak bisa diupdate
                                Period periodeEmpScheduleBeforeUpdate = PstPeriod.fetchExc(empScheduleBeforeUpdate.getPeriodId());
                                if (periodeEmpScheduleBeforeUpdate.getStartDate().after(periodDead.getEndDate())){
                                    
                                        empSchedule.setD21(Long.parseLong(""+d21[e]));
                                        empSchedule.setD22(Long.parseLong(""+d22[e]));
                                        empSchedule.setD23(Long.parseLong(""+d23[e]));
                                        empSchedule.setD24(Long.parseLong(""+d24[e]));
                                        empSchedule.setD25(Long.parseLong(""+d25[e]));
                                        empSchedule.setD26(Long.parseLong(""+d26[e]));
                                        empSchedule.setD27(Long.parseLong(""+d27[e]));
                                        empSchedule.setD28(Long.parseLong(""+d28[e]));
                                        empSchedule.setD29(Long.parseLong(""+d29[e]));
                                        empSchedule.setD30(Long.parseLong(""+d30[e]));
                                        empSchedule.setD31(Long.parseLong(""+d31[e]));
                                        empSchedule.setD1(Long.parseLong(""+d1[e]));
                                        empSchedule.setD2(Long.parseLong(""+d2[e]));
                                        empSchedule.setD3(Long.parseLong(""+d3[e]));
                                        empSchedule.setD4(Long.parseLong(""+d4[e]));
                                        empSchedule.setD5(Long.parseLong(""+d5[e]));
                                        empSchedule.setD6(Long.parseLong(""+d6[e]));
                                        empSchedule.setD7(Long.parseLong(""+d7[e]));
                                        empSchedule.setD8(Long.parseLong(""+d8[e]));
                                        empSchedule.setD9(Long.parseLong(""+d9[e]));
                                        empSchedule.setD10(Long.parseLong(""+d10[e]));
                                        empSchedule.setD11(Long.parseLong(""+d11[e]));
                                        empSchedule.setD12(Long.parseLong(""+d12[e]));
                                        empSchedule.setD13(Long.parseLong(""+d13[e]));
                                        empSchedule.setD14(Long.parseLong(""+d14[e]));
                                        empSchedule.setD15(Long.parseLong(""+d15[e]));
                                        empSchedule.setD16(Long.parseLong(""+d16[e]));
                                        empSchedule.setD17(Long.parseLong(""+d17[e]));
                                        empSchedule.setD18(Long.parseLong(""+d18[e]));
                                        empSchedule.setD19(Long.parseLong(""+d19[e]));
                                        empSchedule.setD20(Long.parseLong(""+d20[e]));
                                    
                                }
                                
                            }
                        
                        
                        /*
                        empSchedule.setD21(Long.parseLong(""+d21[e]));
                        empSchedule.setD22(Long.parseLong(""+d22[e]));
                        empSchedule.setD23(Long.parseLong(""+d23[e]));
                        empSchedule.setD24(Long.parseLong(""+d24[e]));
                        empSchedule.setD25(Long.parseLong(""+d25[e]));
                        empSchedule.setD26(Long.parseLong(""+d26[e]));
                        empSchedule.setD27(Long.parseLong(""+d27[e]));
                        empSchedule.setD28(Long.parseLong(""+d28[e]));
                        empSchedule.setD29(Long.parseLong(""+d29[e]));
                        empSchedule.setD30(Long.parseLong(""+d30[e]));
                        empSchedule.setD31(Long.parseLong(""+d31[e]));
                        empSchedule.setD1(Long.parseLong(""+d1[e]));
                        empSchedule.setD2(Long.parseLong(""+d2[e]));
                        empSchedule.setD3(Long.parseLong(""+d3[e]));
                        empSchedule.setD4(Long.parseLong(""+d4[e]));
                        empSchedule.setD5(Long.parseLong(""+d5[e]));
                        empSchedule.setD6(Long.parseLong(""+d6[e]));
                        empSchedule.setD7(Long.parseLong(""+d7[e]));
                        empSchedule.setD8(Long.parseLong(""+d8[e]));
                        empSchedule.setD9(Long.parseLong(""+d9[e]));
                        empSchedule.setD10(Long.parseLong(""+d10[e]));
                        empSchedule.setD11(Long.parseLong(""+d11[e]));
                        empSchedule.setD12(Long.parseLong(""+d12[e]));
                        empSchedule.setD13(Long.parseLong(""+d13[e]));
                        empSchedule.setD14(Long.parseLong(""+d14[e]));
                        empSchedule.setD15(Long.parseLong(""+d15[e]));
                        empSchedule.setD16(Long.parseLong(""+d16[e]));
                        empSchedule.setD17(Long.parseLong(""+d17[e]));
                        empSchedule.setD18(Long.parseLong(""+d18[e]));
                        empSchedule.setD19(Long.parseLong(""+d19[e]));
                        empSchedule.setD20(Long.parseLong(""+d20[e])); */
                        PstEmpSchedule.updateExc(empSchedule);
                    }
                    catch(Exception exc){
                        msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+"</div>";
                    }
                }
                else {
                    // insert
                    try {
                        EmpSchedule empSchedule = new EmpSchedule();
                        empSchedule.setEmployeeId(Long.parseLong(""+employeeId[e]));
                        empSchedule.setPeriodId(periodId);
                        empSchedule.setD21(Long.parseLong(""+d21[e]));
                        empSchedule.setD22(Long.parseLong(""+d22[e]));
                        empSchedule.setD23(Long.parseLong(""+d23[e]));
                        empSchedule.setD24(Long.parseLong(""+d24[e]));
                        empSchedule.setD25(Long.parseLong(""+d25[e]));
                        empSchedule.setD26(Long.parseLong(""+d26[e]));
                        empSchedule.setD27(Long.parseLong(""+d27[e]));
                        empSchedule.setD28(Long.parseLong(""+d28[e]));
                        empSchedule.setD29(Long.parseLong(""+d29[e]));
                        empSchedule.setD30(Long.parseLong(""+d30[e]));
                        empSchedule.setD31(Long.parseLong(""+d31[e]));
                        empSchedule.setD1(Long.parseLong(""+d1[e]));
                        empSchedule.setD2(Long.parseLong(""+d2[e]));
                        empSchedule.setD3(Long.parseLong(""+d3[e]));
                        empSchedule.setD4(Long.parseLong(""+d4[e]));
                        empSchedule.setD5(Long.parseLong(""+d5[e]));
                        empSchedule.setD6(Long.parseLong(""+d6[e]));
                        empSchedule.setD7(Long.parseLong(""+d7[e]));
                        empSchedule.setD8(Long.parseLong(""+d8[e]));
                        empSchedule.setD9(Long.parseLong(""+d9[e]));
                        empSchedule.setD10(Long.parseLong(""+d10[e]));
                        empSchedule.setD11(Long.parseLong(""+d11[e]));
                        empSchedule.setD12(Long.parseLong(""+d12[e]));
                        empSchedule.setD13(Long.parseLong(""+d13[e]));
                        empSchedule.setD14(Long.parseLong(""+d14[e]));
                        empSchedule.setD15(Long.parseLong(""+d15[e]));
                        empSchedule.setD16(Long.parseLong(""+d16[e]));
                        empSchedule.setD17(Long.parseLong(""+d17[e]));
                        empSchedule.setD18(Long.parseLong(""+d18[e]));
                        empSchedule.setD19(Long.parseLong(""+d19[e]));
                        empSchedule.setD20(Long.parseLong(""+d20[e]));
                        PstEmpSchedule.insertExc(empSchedule);
                    }
                    catch(Exception exc){
                        msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+"</div>";
                    }
                }
            }
            if(msgString == null || msgString.length()<1)
                msgString = "<div class=\"msginfo\">Data have been saved</div>";
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
        document.frmupload.action="up_sch_process_mhalf.jsp";
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
                                    TextLoader uploader = new TextLoader();
                                    FileOutputStream fOut = null;
                                    ByteArrayInputStream inStream = null;
                                    Vector v = new Vector();
                                    int numcol = 33;
                                    StringBuffer drawList =  new StringBuffer();
                                    try {
                                        if (iCommand == Command.SAVE) {
                                            Vector vector = (Vector)session.getValue("WORK_SCHEDULE");
                                            v = (Vector)vector.clone();
                                        }
                                        else {
                                            uploader.uploadText(config, request, response);
                                            Object obj = uploader.getTextFile("file");
                                            byte byteText[] = null;
                                            byteText = (byte[]) obj;
                                            inStream = new ByteArrayInputStream(byteText);
                                            Excel tp = new Excel();
                                            Vector vector = tp.ReadStream((InputStream) inStream, NUM_HEADER, NUM_CELL);
											
											numcol = tp.getNumberOfColumn();
											System.out.println(" -----  numcol : "+numcol);
											
                                            if(session.getValue("WORK_SCHEDULE") != null)
                                                session.removeValue("WORK_SCHEDULE");
                                            session.putValue("WORK_SCHEDULE",vector);
                                            v = (Vector)vector.clone();
											
											
											//out.println(v);
                                        }
                                        drawList.append("<form name=\"frmupload\" method=\"post\" action=\"\">"+
                                           "\n<input type=\"hidden\" name=\"command\" value=\""+iCommand+"\">");

                                            if(v.size()>0){
                                                    drawList.append("\n<table cellpadding=\"2\" cellspacing=\"2\" border=\"0\">"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td><li></td>"+
                                                            "\n\t\t<td colspan=\"2\">Choose the Period of Working Schedule</td>"+ 
                                                            "\n\t</tr>"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td>&nbsp;</td>"+
                                                            "\n\t\t<td>Period</td>"+
                                                            "\n\t\t<td>"+ControlCombo.draw("period_id","formElemen","select...",""+periodId,periodValue,periodKey)+"</td>"+
                                                            "\n\t</tr>"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td><li></td>"+
                                                            "\n\t\t<td colspan=\"2\">List of Working Schedule</td>"+ 
                                                            "\n\t</tr>"+
                                                            "\n</table>"+
                                                            "\n<table cellpadding=\"1\" cellspacing=\"1\" border=\"0\" width=\"100%\" class=\"listgen\">"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td width=\"1%\" rowspan=\"3\" class=\"tableheader\">No</td>"+
                                                            "\n\t\t<td width=\"15%\" rowspan=\"3\" class=\"tableheader\">Employee</td>"+
                                                            "\n\t\t<td width=\"8%\" rowspan=\"3\" class=\"tableheader\">Payroll</td>"+
                                                            "\n\t\t<td width=\"77%\" colspan=\""+(numcol-2)+"\" align=\"center\" class=\"tableheader\">Date</td>"+
                                                            "\n\t</tr>"+
                                                            "\n\t<tr class=\"listheader\">");

                                                            double width =  77/(new Double(numcol)).doubleValue(); 
                                                            System.out.println("width == "+width);
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
                                                            }

                                                            Hashtable hashPayroll = new Hashtable();
															String whereC = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"=0"; 
                                                            Vector listEmployee = PstEmployee.list(0, 0, whereC, "");
															
															System.out.println("listEmployee.size() : "+listEmployee.size());
                                                            
															for (int e = 0; e < listEmployee.size(); e++) {
                                                                Employee employee = (Employee) listEmployee.get(e);
                                                                hashPayroll.put(employee.getEmployeeNum(), String.valueOf(employee.getOID()));
                                                            }
                                                            for (int i = numcol; i < v.size(); i++) {
                                                                    String sch = "";
                                                                    if(hashSchedule.get(((String)v.elementAt(i)).trim())== null)
                                                                            sch = "?";
                                                                    else
                                                                            sch = ""+v.elementAt(i);
																			
                                                                    switch (((i+1) % numcol)) {
                                                                        case 1 :
                                                                                drawList.append("\n\t\t<td>"+((i/numcol))+"</td><td  width=\""+width+"%\">"+v.elementAt(i)+"</td>");
                                                                                break;
                                                                        case 2 :
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
                                                                                break;
                                                                        case 3 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D21\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 4 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D22\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 5 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D23\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 6 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D24\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 7 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D25\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 8 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D26\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 9 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D27\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 10 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D28\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 11 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D29\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 12 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D30\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 13 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D31\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 14 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D1\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 15 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D2\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 16 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D3\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 17 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D4\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 18 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D5\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 19 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D6\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 20 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D7\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 21 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D8\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 22 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D9\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 23 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D10\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 24 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D11\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 25 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D12\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 26 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D13\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 27 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D14\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 28 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D15\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 29 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D16\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 30 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D17\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 31 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D18\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 32 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D19\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
                                                                                break;
                                                                        case 0 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"D20\" value=\"" + hashSchedule.get(((String)v.elementAt(i)).trim()) + "\">"+sch+"</td>");
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
                                                            //if (iCommand != Command.SAVE) {
                                                            drawList.append("\n\t<tr>"+ 
                                                                "\n\t\t<td width=\"4\"><img src=\""+approot+"/images/spacer.gif\" width=\"4\" height=\"4\"></td>"+
                                                                "\n\t\t<td width=\"24\"><a href=\"javascript:cmdSave()\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image300','','"+approot+"/images/BtnSaveOn.jpg',1)\"><img name=\"Image300\" border=\"0\" src=\""+approot+"/images/BtnSave.jpg\" width=\"24\" height=\"24\" alt=\"Save\"></a></td>"+
                                                                "\n\t\t<td width=\"4\"><img src=\""+approot+"/images/spacer.gif\" width=\"4\" height=\"4\"></td>"+
                                                                "\n\t\t<td nowrap> <a href=\"javascript:cmdSave()\" class=\"command\">Save Working Schedule</a></td>"+
                                                                "\n\t</tr>");
                                                             //   }
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
