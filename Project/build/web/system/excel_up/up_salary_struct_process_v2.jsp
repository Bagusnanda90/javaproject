<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@ page language = "java" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.System"%>
<%@ page import="java.io.ByteArrayInputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.text.NumberFormat"%>

<%@ page import="com.dimata.util.Excel"%>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="com.dimata.util.blob.TextLoader" %>
<%@ page import="org.apache.poi.hssf.usermodel.*" %>

<%@ page import="org.apache.poi.poifs.filesystem.POIFSFileSystem" %>
<%@ page import="org.apache.poi.hssf.record.*" %>
<%@ page import="org.apache.poi.hssf.model.*" %>
<%@ page import="org.apache.poi.hssf.usermodel.*" %>
<%@ page import="org.apache.poi.hssf.util.*" %>


<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ page import="com.dimata.harisma.entity.payroll.*" %>

<%@ include file = "../../main/javainit.jsp" %>


<%
    int NUM_HEADER = 2;
    int NUM_CELL = 0;//22;
    // update by Kartika 23 Nov 2012    
    int ROW_COMP_MIN =3;
    int ROW_COMP_MAX =7; 
    int COL_PAYROLL_MIN=0;
    int COL_PAYROLL_MAX=3;     

     String LEVEL_CODE="LEVEL CODE";
     String LEVEL_NAME="LEVEL NAME";
     String START_DATE="START DATE";
     String END_DATE="END DATE";
     String CASH_BANK="CASH/BANK";
     String BANK_ACCOUNT="BANK ACCOUNT";
     String NPWP="NPWP";
     String TAX_POSITION="TAX POSITION";
     String START_MONTH="START MONTH";
     String END_MONTH="END MONTH";
     String COMMENCING_STATUS="COMMENCING STATUS";
     String PREVIOUS_INCOME="PREVIOUS INCOME";
     String PREVIOUS_TAX="PREVIOUS TAX";
     String MEAL_ALLOWANCE="MEAL ALLOWANCE";
     String OVERTIME_INDEX="OVERTIME INDEX";
     String SALARY_TYPE ="SALARY TYPE";
     String CURRENCY = "CURRENCY";

    Hashtable salaryLevelData  = new Hashtable();
     salaryLevelData.put(LEVEL_CODE,"1");
     salaryLevelData.put(LEVEL_NAME,"1");
     salaryLevelData.put(START_DATE,"1");
     salaryLevelData.put(END_DATE,"1");
     salaryLevelData.put(CASH_BANK,"1");
     salaryLevelData.put(BANK_ACCOUNT,"1");
     salaryLevelData.put(NPWP,"1");
     salaryLevelData.put(TAX_POSITION,"1");
     salaryLevelData.put(START_MONTH,"1");
     salaryLevelData.put(END_MONTH,"1");
     salaryLevelData.put(COMMENCING_STATUS,"1"); 
     salaryLevelData.put(PREVIOUS_INCOME,"1");
     salaryLevelData.put(PREVIOUS_TAX,"1");
     salaryLevelData.put(MEAL_ALLOWANCE,"1");
     salaryLevelData.put(OVERTIME_INDEX,"1");
     salaryLevelData.put(SALARY_TYPE,"1");
     salaryLevelData.put(CURRENCY,"1");
          
           
    int iCommand = FRMQueryString.requestCommand(request);
    Date curr_date = FRMQueryString.requestDate(request,"curr_date");
 

    String msgString = "";
    if(iCommand == Command.SAVE){
        if(curr_date == null)
            msgString = "<div class=\"errfont\">Please choose the Period of Working Schedule !</div>";
        else{
            String[] employeeId = request.getParameterValues("employee_id");
            String[] los1  = request.getParameterValues("LOS1");
            String[] los2  = request.getParameterValues("LOS2");
            String[] curr_basic = request.getParameterValues("CURR_BASIC");
            String[] curr_trans = request.getParameterValues("CURR_TRANS");
            String[] curr_total = request.getParameterValues("CURR_TOTAL");
            String[] new_basic  = request.getParameterValues("NEW_BASIC");
            String[] new_trans  = request.getParameterValues("NEW_TRANS");
            String[] new_total  = request.getParameterValues("NEW_TOTAL");
            String[] inc_basic  = request.getParameterValues("INC_BASIC");
            String[] inc_trans  = request.getParameterValues("INC_TRANS");
            String[] inc_add    = request.getParameterValues("INC_ADD");
            String[] inc_total  = request.getParameterValues("INC_TOTAL");
            String[] pct_basic  = request.getParameterValues("PCT_BASIC");
            String[] pct_trans  = request.getParameterValues("PCT_TRANS");
            String[] pct_total  = request.getParameterValues("PCT_TOTAL");

            for(int e=0; e<employeeId.length; e++){
                String where = "";
                    where += PstEmpSalary.fieldNames[PstEmpSalary.FLD_CURR_DATE] + "='" + Formater.formatDate(curr_date, "yyyy-MM-dd") + "' ";
                    where += " AND " + PstEmpSalary.fieldNames[PstEmpSalary.FLD_EMPLOYEE_ID];
                    where += "=" + employeeId[e];
                Vector vcheck = PstEmpSalary.list(0, 0, where, "");

                if (vcheck.size() > 0) {
                    System.out.println("...update");
                    try{
                        EmpSalary empSalary = (EmpSalary) vcheck.get(0);
                        empSalary.setEmployeeId(Long.parseLong(""+employeeId[e]));
                        empSalary.setCurrDate(curr_date);
                        empSalary.setLos1(Integer.parseInt(los1[e]));
                        empSalary.setLos2(Integer.parseInt(los2[e]));
                        empSalary.setCurrBasic(Double.parseDouble(curr_basic[e]));
                        empSalary.setCurrTransport(Double.parseDouble(curr_trans[e]));
                        empSalary.setCurrTotal(Double.parseDouble(curr_total[e]));
                        empSalary.setNewBasic(Double.parseDouble(new_basic[e]));
                        empSalary.setNewTransport(Double.parseDouble(new_trans[e]));
                        empSalary.setNewTotal(Double.parseDouble(new_total[e]));
                        empSalary.setIncSalary(Double.parseDouble(inc_basic[e]));
                        empSalary.setIncTotal(Double.parseDouble(inc_total[e]));
                        empSalary.setIncTransport(Double.parseDouble(inc_trans[e]));
                        empSalary.setAdditional(Double.parseDouble(inc_add[e]));
                        empSalary.setPercentageBasic(Double.parseDouble(pct_basic[e]));
                        empSalary.setPercentageTotal(Double.parseDouble(pct_total[e]));
                        empSalary.setPercentTransport(Double.parseDouble(pct_trans[e]));
                        PstEmpSalary.updateExc(empSalary);
                    }
                    catch(Exception exc){
                        msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+"</div>";
                    }
                }
                else {
                    System.out.println("...insert");
                    try {
                        EmpSalary empSalary = new EmpSalary();                        
                        empSalary.setEmployeeId(Long.parseLong(""+employeeId[e]));
                        empSalary.setCurrDate(curr_date);
                        empSalary.setLos1(Integer.parseInt(los1[e]));
                        empSalary.setLos2(Integer.parseInt(los2[e]));
                        empSalary.setCurrBasic(Double.parseDouble(curr_basic[e]));
                        empSalary.setCurrTransport(Double.parseDouble(curr_trans[e]));
                        empSalary.setCurrTotal(Double.parseDouble(curr_total[e]));
                        empSalary.setNewBasic(Double.parseDouble(new_basic[e]));
                        empSalary.setNewTransport(Double.parseDouble(new_trans[e]));
                        empSalary.setNewTotal(Double.parseDouble(new_total[e]));
                        empSalary.setIncSalary(Double.parseDouble(inc_basic[e]));
                        empSalary.setIncTotal(Double.parseDouble(inc_total[e]));
                        empSalary.setIncTransport(Double.parseDouble(inc_trans[e]));
                        empSalary.setAdditional(Double.parseDouble(inc_add[e]));
                        empSalary.setPercentageBasic(Double.parseDouble(pct_basic[e]));
                        empSalary.setPercentageTotal(Double.parseDouble(pct_total[e]));
                        empSalary.setPercentTransport(Double.parseDouble(pct_trans[e]));
                        PstEmpSalary.insertExc(empSalary);
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
%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Salary</title>
<script language="JavaScript">
    function cmdSave(){
        document.frmupload.command.value="<%=Command.SAVE%>";
        document.frmupload.action="up_salary_struct_v2.jsp";
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
    } 

    function hideObjectForLockers(){ 
    }

    function hideObjectForCanteen(){
    }

    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
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
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
   <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
            <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
              Uploader > Employee Salary |  <a  href="<%=approot%>/payroll/setup/employee-setup.jsp"><font color="#30009D">Back to Select</font></a> | <a  href="<%=approot%>/home.jsp"><font color="#30009D">Back to Menu</font></a><!-- #EndEditable --> </strong></font> 
            </td>
        </tr>
        <tr> 
          <td> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor" style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                                <% String allMessage="";
        try {                                
                                    NumberFormat numberFormatter;
                                    numberFormatter = NumberFormat.getNumberInstance();

                                    TextLoader uploader = new TextLoader();
                                    FileOutputStream fOut = null;
                                    ByteArrayInputStream inStream = null;
                                    Vector v = new Vector();
                                    int numcol = 22;//12;
                                    StringBuffer drawList =  new StringBuffer();
                                    org.apache.poi.hssf.record.crypto.Biff8EncryptionKey.setCurrentUserPassword("matadihati");
                                    uploader.uploadText(config, request, response);
                                    Object obj = uploader.getTextFile("file");
                                    byte byteText[] = null;
                                    byteText = (byte[]) obj;
                                    inStream = new ByteArrayInputStream(byteText);
                                            Excel tp = new Excel();
                                
        Vector result = new Vector();
        

            
            POIFSFileSystem fs = new POIFSFileSystem(inStream);

            HSSFWorkbook wb = new HSSFWorkbook(fs);
            System.out.println("creating workbook");
            
            int numOfSheets = wb.getNumberOfSheets();
            //System.out.println("Number Of Sheets = " + numOfSheets);
            Vector<SalaryLevel> listOfSalaryLevels = new Vector();
            String sheetName = "structure";
            out.println("<br><strong> Looking for Sheet name shall be : "+ sheetName +"<br>");                           
            for (int i=0; i<numOfSheets; i++) {
                
                int r = 0;
                
                HSSFSheet sheet = (HSSFSheet)wb.getSheetAt(i);                
                if( !sheetName.equalsIgnoreCase(sheet.getSheetName().trim())) {
                    out.println(" Sheet "+ sheet.getSheetName() + " is not structure");
                    continue;
                } else {
                    out.println(" Sheet "+ sheet.getSheetName() + " is  structure !");
                }
  
                
                int rows = sheet.getPhysicalNumberOfRows();                
                
                Hashtable payCompInput = new Hashtable();
                Hashtable payCompTakeHome = new Hashtable();
                Hashtable payEmpLevelInput = new Hashtable();
                boolean payCompDone = false;
                int col_payroll = -1;
                int row_payroll = -1;
                //System.out.println("\tgetNumberOfSheets = " + wb.getNumberOfSheets());
                //System.out.println("\tgetPhysicalNumberOfRows = " + sheet.getPhysicalNumberOfRows());
                                %> <table class="tablecolor" > <%
                // loop untuk row dimulai dari numHeaderRow (0, .. numHeaderRow diabaikan) => untuk yang bukan sheet pertaman
                int start = (i == 0) ? 0 : NUM_HEADER;
                for (r=start; r < rows; r++) {
                    allMessage = allMessage+ "<br> Row "+r + " : ";
                    Employee employee = null;
                    PayEmpLevel payEmpLevel = null;
                    SalaryLevel salaryLevel = null;
                    try{ 
	                    HSSFRow row = sheet.getRow(r);
	                    int cells = 0;
                        //if number of cell is static
                        if(NUM_CELL > 0){
                        	cells = NUM_CELL;
                        }
                        //number of cell is dinamyc
                        else{
                            cells = row.getPhysicalNumberOfCells();
                        }

                        // ambil jumlah kolom yang sebenarnya
                        NUM_CELL = cells;

	                    %> <tr> <%
	                    //System.out.println("ROW " + row.getRowNum());
                            String cellColor="";                            
	                    for (int c = 0; c < cells; c++)
	                    {   
                                cellColor="#EEEEEE";
	                        HSSFCell cell  = row.getCell((short) c);
	                        String   value = null;
                                if(cell!=null){
	                        switch (cell.getCellType())
	                        {
	                            case HSSFCell.CELL_TYPE_FORMULA :
	                                //value = "FORMULA ";
	                                value = String.valueOf(cell.getCellFormula());;
	                                break;
	                            case HSSFCell.CELL_TYPE_NUMERIC :
	                                //value = "NUMERIC value=" + cell.getNumericCellValue();
	                                value = String.valueOf(cell.getNumericCellValue());
	                                break;
	                            case HSSFCell.CELL_TYPE_STRING :
	                                //value = "STRING value=" + cell.getStringCellValue();
	                                value = String.valueOf(cell.getStringCellValue());                                        
                                        if(!payCompDone && value!=null && (r>=ROW_COMP_MIN && r <= ROW_COMP_MAX)){                                             
                                            if( salaryLevelData.get(value.trim().toUpperCase()) !=null){
                                                payEmpLevelInput.put(""+c, value.trim().toUpperCase()); 
                                                cellColor="#669966";
                                            } else{
                                            int idxComp = value.lastIndexOf("/"); 
                                            String strCompName = idxComp < 0 ? value.trim() : value.substring(0, idxComp ).trim();
                                            PayComponent comp=PstPayComponent.getManualInputComponent(strCompName); 
                                            if(comp!=null && comp.getCompName().length()>0){
                                               payCompInput.put(""+c, comp);                                                 
                                               cellColor="#669966";
                                               if(idxComp>0){
                                                  String takeHome = value.substring(idxComp, value.length());
                                                  if( takeHome!=null && takeHome.length()>0 && takeHome.trim().contains("Y")){
                                                    payCompTakeHome.put(""+c,"Y");
                                                  } else{
                                                      payCompTakeHome.put(""+c,"N");
                                                  }
                                               }else{
                                                   payCompTakeHome.put(""+c,"N");
                                               }                                               
                                            }
                                            }
                                         }
	                                break;                                                                        
	                            default :
                                        value = String.valueOf(cell.getStringCellValue()!=null?cell.getStringCellValue():"");
                                        ;
	                        }
                                
                                try{ // search for the row containts the first employee data 
                                if(col_payroll<0 && r>ROW_COMP_MIN && c>=COL_PAYROLL_MIN && c<= COL_PAYROLL_MAX && value!=null){
                                     if(value.endsWith(".0")){  // update by Kartika 23 Nov 2012
                                         value=value.substring(0, value.length()-2);
                                     };
                                    
                                     employee = PstEmployee.getEmployeeByNum(value);
                                     if(employee!=null && employee.getOID()!=0 && employee.getEmployeeNum().length()>0){ 
                                         row_payroll = r;
                                         col_payroll = c;                                        
                                         cellColor="#669966";
                                     } else{
                                        String tempPayroll = value.substring(0, value.lastIndexOf("."));
                                         employee =PstEmployee.getEmployeeByNum(tempPayroll);
                                         if(employee!=null && employee.getEmployeeNum().length()>0){
                                             row_payroll = r;
                                             col_payroll = c;
                                             cellColor="#669966"; 
                                             value = tempPayroll;
                                         }  /*else {
                                                 row_payroll = r;
                                                 col_payroll = c;
                                                 cellColor="#CC0000"; 
                                                 value = tempPayroll ;
                                             }*/
                                     }
                                }} catch(Exception exc){} 
                                
                                if(row_payroll>=0 && r >=row_payroll && employee==null){ // position for employee payroll data
                                try{ 
                                    if( c== col_payroll && value!=null){
                                     if(value.endsWith(".0")){
                                         value=value.substring(0, value.length()-2);
                                     };
                                        
                                         employee =PstEmployee.getEmployeeByNum(value);
                                         if(employee!=null && employee.getOID()!=0 && employee.getEmployeeNum().length()>0){
                                             row_payroll = r;
                                             col_payroll = c;                                        
                                             cellColor="#669966";
                                         } else{
                                            String tempPayroll = value.substring(0, value.lastIndexOf("."));
                                             employee =PstEmployee.getEmployeeByNum(tempPayroll);
                                             if(employee!=null && employee.getEmployeeNum().length()>0){
                                                 row_payroll = r;
                                                 col_payroll = c;
                                                 cellColor="#669966"; 
                                                 value = tempPayroll;
                                             } else {
                                                 row_payroll = r;
                                                 col_payroll = c;
                                                 cellColor="#CC0000"; 
                                                 value = "NOT FOUND !" + tempPayroll ;
                                             } 
                                         }
                                    }                                    
                                } catch(Exception exc){}                                                                         
                                }
                                if(employee!=null && employee.getEmployeeNum().length()>0 && payEmpLevel==null ){ 
                                      salaryLevel =new SalaryLevel();
                                      payEmpLevel = new PayEmpLevel(); 
                                      payEmpLevel.setEmployeeId(employee.getOID());                                                                            
                                }
                                
                                
                                if( value!=null && salaryLevel!=null && employee.getEmployeeNum().length()>0){                                 
                                    if(payEmpLevelInput.containsKey(""+c)){
                                          String dataName = (String) payEmpLevelInput.get(""+c);                                          
                                          if(dataName.equalsIgnoreCase(LEVEL_CODE)){
                                             cellColor="#669966"; 
                                             salaryLevel.setLevelCode(value);
                                           } else  if(dataName.equalsIgnoreCase(LEVEL_NAME)){
                                                cellColor="#669966"; 
                                                salaryLevel.setLevelName(value);
                                           } else  if(dataName.equalsIgnoreCase(START_DATE)){
                                             try{                       
                                                GregorianCalendar gc = new GregorianCalendar(1900, Calendar.JANUARY,1);                                                
                                                int dayVal = Math.round(Float.parseFloat(value));
                                                gc.add(Calendar.DATE, dayVal-2); // kurangi tanggal
                                                payEmpLevel.setStartDate(gc.getTime());                                                
                                                cellColor="#669966";
                                             } catch(Exception exc){                                                                                                 
                                                 payEmpLevel.setStartDate(new Date());
                                                 cellColor="##FF0000";
                                             }
                                             value = Formater.formatDate(payEmpLevel.getStartDate(), "dd MMM yyyy" );
                                             cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
                                           } else  if(dataName.equalsIgnoreCase(CASH_BANK)){                                                
                                                try{                                                   
                                                    payEmpLevel.setBankId(PstPayBanks.getOidByName(""+value));
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(BANK_ACCOUNT)){                                                
                                                try{                                                   
                                                    payEmpLevel.setBankAccNr(""+  Formater.formatNumber(Double.parseDouble(value),"#####################"));
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){ payEmpLevel.setBankAccNr(""+value);}
                                           } else  if(dataName.equalsIgnoreCase(NPWP)){                                                
                                                try{                                                   
                                                    //payEmpLevel.set (PstPayBanks.getOidByName(""+value)); 
                                                    //cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(TAX_POSITION)){                                                
                                                try{                                                   
                                                    payEmpLevel.setPosForTax(""+value);
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(START_MONTH)){                                                
                                                try{                                                   
                                                    payEmpLevel.setPayPerBegin(Math.round(Float.parseFloat(value)));
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(END_MONTH)){                                                
                                                try{                                                   
                                                    payEmpLevel.setPayPerEnd(Math.round(Float.parseFloat(value)));
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(COMMENCING_STATUS)){                                                
                                                try{                                                   
                                                    payEmpLevel.setCommencingSt(Math.round(Float.parseFloat(value)));
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(PREVIOUS_INCOME)){                                                
                                                try{                                                   
                                                    payEmpLevel.setPrevIncome(Double.parseDouble(value));
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(PREVIOUS_TAX)){                                                
                                                try{                                                   
                                                    payEmpLevel.setPrevTaxPaid(Math.round(Float.parseFloat(value))); 
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(MEAL_ALLOWANCE)){                                                
                                                try{                                                   
                                                    payEmpLevel.setMealAllowance(Math.round(Float.parseFloat(value)));
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(OVERTIME_INDEX)){                                                
                                                try{                                                   
                                                    payEmpLevel.setOvtIdxType(Math.round(Float.parseFloat(value)));
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(SALARY_TYPE)){                                                
                                                try{                                                   
                                                    salaryLevel.setAmountIs(Math.round(Float.parseFloat(value)));
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(CURRENCY)){
                                                try{                                                   
                                                    salaryLevel.setCur_Code(value); 
                                                    cellColor="#669966"; 
                                                }catch(Exception exc){}
                                           } else  if(dataName.equalsIgnoreCase(END_DATE)){
                                             try{                       
                                                GregorianCalendar gc = new GregorianCalendar(1900, Calendar.JANUARY,1);                                                
                                                int dayVal = Math.round(Float.parseFloat(value));
                                                gc.add(Calendar.DATE, dayVal-2); // kurangi tanggal
                                                payEmpLevel.setEndDate(gc.getTime());                                                
                                                cellColor="#669966";
                                             } catch(Exception exc){                                                                                                 
                                                 payEmpLevel.setEndDate(null);
                                                 cellColor="##FF0000";
                                             }
                                             value = Formater.formatDate(payEmpLevel.getEndDate(), "dd MMM yyyy" );
                                             cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                           }                                                                                                                                                                                                               
                                    }
                                }
                                
                                 if( value!=null && employee!=null && employee.getEmployeeNum().length()>0){ 
                                     // employee found
                                     if(payCompInput.containsKey(""+c)){
                                          cellColor="#669966";                                                                                                                                    
                                          SalaryLevelDetail salDetail = new SalaryLevelDetail();
                                          PayComponent payComp = (PayComponent) payCompInput.get(""+c); 
                                          salDetail.setSortIdx(payComp.getSortIdx());                                          
                                          salDetail.setCompCode(payComp.getCompCode());
                                          salDetail.setComponentId(payComp.getOID());
                                          salDetail.setLevelCode(salaryLevel.getLevelCode());
                                          salDetail.setPayPeriod(PstSalaryLevelDetail.PERIODE_MONTHLY);
                                          if(payCompTakeHome.containsKey(""+c)){
                                              String strYN = (String) payCompTakeHome.get(""+c);  
                                              if(strYN!=null && strYN.trim().equalsIgnoreCase("Y")){
                                                  salDetail.setTakeHomePay(PstSalaryLevelDetail.YES_TAKE);
                                              }else{
                                                  salDetail.setTakeHomePay(PstSalaryLevelDetail.NO_TAKE);
                                              }
                                          }   else{
                                              salDetail.setTakeHomePay(PstSalaryLevelDetail.NO_TAKE);
                                          }                                                                                         
                                          salDetail.setCopyData(PstSalaryLevelDetail.NO_COPY);
                                          if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC) {
                                            salDetail.setFormula("="+value);    
                                          } else{
                                            if(value.equals("IN") ){
                                                salDetail.setFormula("= IN_"+ payComp.getCompName().trim().replace(" ","_"));      
                                              } else{
                                                salDetail.setFormula("= "+value);
                                              }
                                          }                                              
                                          salaryLevel.addSalDetails(salDetail);
                                     }                                             
                                 }                                                                                                         
                               }                                
	                       if(cell.getCellType()!=HSSFCell.CELL_TYPE_NUMERIC) { %> 
                                    <td bgcolor="<%=cellColor%>" ><%=(value!=null? value:"")%></td> <%
                                } else{ 
                                    try{  
                                      %> <td bgcolor="<%=cellColor%>" ><%=(""+  Formater.formatNumber(Double.parseDouble(value),"#####################"))%></td> <%
                                         //System.out.println("Test"+Formater.formatNumber(Double.parseDouble(value),"#####################"));
                                    } catch(Exception exc1){}
                                }
	                        //result.addElement(value);
	                    }//end of row
                        if(!payCompDone && payCompInput.size()>0){
                            payCompDone=true;
                        }
                        %> </tr> <%    
                    } catch(Exception e){
	                    allMessage = allMessage+"<br>"+("=> Can't get data ..sheet : "+i+", row : "+r+", => Exception e : "+e.toString());
	                }
                    
                    if(salaryLevel!=null){ 
                        listOfSalaryLevels.add(salaryLevel);                        
                        try{
                          if(salaryLevel.getLevelCode()==null || salaryLevel.getLevelCode().length()<1 || 
                                    employee ==null || employee.getFullName()==null || employee.getFullName().length()<1 ){
                                out.println("<br> Level Code/Name INCOMPLETE :"+
                                        salaryLevel.getLevelCode() + "/" + salaryLevel.getLevelName() +
                                        " Can't Insert/update salary level and employee's salary level" ) ;
                          } else{

                        if(  salaryLevel.getLevelName()==null || salaryLevel.getLevelName().length()<1 ){
                            salaryLevel.setLevelName(employee.getFullName() + "-"+salaryLevel.getLevelCode() ); 
                        }                                                       
                         SalaryLevel existingLevel = PstSalaryLevel.getByLevelCode(salaryLevel.getLevelCode());
                         if(existingLevel!=null &&  existingLevel.getOID()!=0){
                              salaryLevel.setOID(existingLevel.getOID());
                              PstSalaryLevel.updateExcWithDetail(salaryLevel);
                            } else {
                              PstSalaryLevel.insertExcWithDetail(salaryLevel);
                            }
                         payEmpLevel.setLevelCode(salaryLevel.getLevelCode());
                         PstPayEmpLevel.UpdateStatus(employee.getOID()); // set existing menjadi history;
                         long levelOid = PstPayEmpLevel.getOidPayLevelByEmployeeOid(payEmpLevel.getEmployeeId(),
                                 payEmpLevel.getLevelCode(), payEmpLevel.getStartDate());
                         allMessage=allMessage+ " "+ payEmpLevel.getLevelCode() + " " + payEmpLevel.getStartDate();
                         if(levelOid==0){
                            PstPayEmpLevel.insertExc(payEmpLevel);
                          } else {
                             payEmpLevel.setOID(levelOid);
                             PstPayEmpLevel.updateExc(payEmpLevel);
                          }      
                          }                                           
                        }catch(Exception exc){ 
                          allMessage=allMessage+"<br>"+exc;
                        }
                    }                    
	            } //end of sheet
                    %> </table> <%
                            
            } //end of all sheets
            session.putValue("PAYSLIP_TO_UPLOAD", listOfSalaryLevels);
            
            //return result;
        }
        catch (Exception e) {
            allMessage = allMessage+"<br>"+("---=== Error : ReadStream ===---\n" + e);
        }
                        %>        
                                
                                
                                  <br>
                                  Message : <%= allMessage %>
                                  <br>
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
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
            <%}%>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
