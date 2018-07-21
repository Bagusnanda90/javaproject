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
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<%
    int NUM_HEADER = 2;
	int NUM_CELL = 22;
	
	int iCommand = FRMQueryString.requestCommand(request);
    Date curr_date = FRMQueryString.requestDate(request,"curr_date");
    //System.out.print("curr_date = " + curr_date);

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
        document.frmupload.action="up_salary_process.jsp";
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
            <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
              Uploader > Employee Salary<!-- #EndEditable --> </strong></font> 
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
                                    NumberFormat numberFormatter;
                                    numberFormatter = NumberFormat.getNumberInstance();

                                    TextLoader uploader = new TextLoader();
                                    FileOutputStream fOut = null;
                                    ByteArrayInputStream inStream = null;
                                    Vector v = new Vector();
                                    int numcol = 22;//12;
                                    StringBuffer drawList =  new StringBuffer();
                                    try {
                                        if (iCommand == Command.SAVE) {
                                            Vector vector = (Vector)session.getValue("EMPLOYEE_SALARY");
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
                                            if(session.getValue("EMPLOYEE_SALARY") != null)
                                                session.removeValue("EMPLOYEE_SALARY");
                                            session.putValue("EMPLOYEE_SALARY",vector);
                                            v = (Vector)vector.clone();
                                        }
										
										//out.println(v);
                                        System.out.println("\tv.size()=" + v.size());
                                        //for (int i=0; i<v.size(); i++) {
                                        //    System.out.println(v.elementAt(i));
                                        //}

                                        drawList.append("<form name=\"frmupload\" method=\"post\" action=\"\">"+
                                           "\n<input type=\"hidden\" name=\"command\" value=\""+iCommand+"\">");

                                            if(v.size()>0){
                                                    drawList.append("\n<table cellpadding=\"2\" cellspacing=\"2\" border=\"0\">"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td><li></td>"+
                                                            "\n\t\t<td colspan=\"2\">Choose the Salary Date</td>"+ 
                                                            "\n\t</tr>"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td>&nbsp;</td>"+
                                                            "\n\t\t<td>Date</td>"+
                                                            "\n\t\t<td>"+ControlDate.drawDateWithStyle("curr_date", new Date(), 0, -20,"formElemen")+"</td>"+
                                                            "\n\t</tr>"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td><li></td>"+
                                                            "\n\t\t<td colspan=\"2\">List of Employee Salary</td>"+ 
                                                            "\n\t</tr>"+
                                                            "\n</table>"+
                                                            "\n<table cellpadding=\"1\" cellspacing=\"1\" border=\"0\" width=\"100%\" class=\"listgen\">"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td width=\"2%\" rowspan=\"2\" class=\"tableheader\">No.</td>"+
                                                            "\n\t\t<td width=\"8%\" rowspan=\"2\" class=\"tableheader\">Name</td>"+
                                                            "\n\t\t<td width=\"8%\" rowspan=\"2\" class=\"tableheader\">Payroll</td>"+
                                                            "\n\t\t<td width=\"3%\" rowspan=\"2\" class=\"tableheader\" nowrap>LOS 1</td>"+
                                                            "\n\t\t<td width=\"3%\" rowspan=\"2\" class=\"tableheader\" nowrap>LOS 2</td>"+
                                                            "\n\t\t<td width=\"30%\" colspan=\"3\" align=\"center\" class=\"tableheader\">Current Salary</td>"+
                                                            "\n\t\t<td width=\"30%\" colspan=\"3\" align=\"center\" class=\"tableheader\">New Salary</td>"+
                                                            "\n\t\t<td width=\"30%\" colspan=\"4\" align=\"center\" class=\"tableheader\">Increment</td>"+
                                                            "\n\t\t<td width=\"30%\" colspan=\"3\" align=\"center\" class=\"tableheader\">Percentage</td>"+
                                                            "\n\t</tr>"+
                                                            "\n\t<tr class=\"listheader\">");

                                                            drawList.append("\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Basic</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Trans.</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Total</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Basic</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Trans.</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Total</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Basic</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Trans.</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Additional</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Total</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Basic</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Trans.</td>"
                                                            + "\n\t\t<td align=\"center\" width=\"5%\" class=\"tableheader\">Total</td>");

                                                            drawList.append("\n\t</tr>\n\t<tr class=\"listgensell\">");
                                                            Hashtable hashPayroll = new Hashtable();
                                                            Hashtable hashName = new Hashtable();
                                                            Vector listEmployee = PstEmployee.list(0, 0, "", "");
                                                            for (int e = 0; e < listEmployee.size(); e++) {
                                                                Employee employee = (Employee) listEmployee.get(e);
                                                                hashPayroll.put(employee.getEmployeeNum(), String.valueOf(employee.getOID()));
                                                                //System.out.print(employee.getFullName());
                                                                hashName.put(String.valueOf(employee.getEmployeeNum()), employee.getFullName());
                                                            }

                                                            int startCell = numcol * 2;
                                                            int dbnamelength = 0;

                                                            double curr_basic = 0.0;
                                                            double curr_trans = 0.0;
                                                            double curr_total = 0.0;
                                                            double new_basic = 0.0;
                                                            double new_trans = 0.0;
                                                            double new_total = 0.0;
                                                            double inc_basic = 0.0;
                                                            double inc_trans = 0.0;
                                                            double inc_add = 0.0;
                                                            double inc_total = 0.0;
                                                            double pct_basic = 0.0;
                                                            double pct_trans = 0.0;
                                                            double pct_total = 0.0;

                                                            for (int i = startCell; i < v.size(); i++) {
                                                                    switch ((i % numcol)) {
                                                                        case 0 : // No.
                                                                                drawList.append("\n\t\t<td  width=\"2%\" align=\"right\">"+Math.round((double) Double.parseDouble(v.elementAt(i).toString()))+"&nbsp;</td>");
                                                                                break;
                                                                        case 1 : // Name
                                                                                String dbname = "";
                                                                                if (hashName.get(v.elementAt(i+1)) == null) {
                                                                                    dbname = "?";
                                                                                }
                                                                                else {
                                                                                    dbname = String.valueOf(hashName.get(v.elementAt(i+1)));
                                                                                    dbnamelength = dbname.length();
                                                                                    if (dbnamelength > 10) {
                                                                                        dbnamelength = 10;
                                                                                    }
                                                                                    dbname = dbname.substring(0,dbnamelength) + "...";
                                                                                }
                                                                                //drawList.append("\n\t\t<td width=\"8%\" nowrap>&nbsp;"+ v.elementAt(i) +"<br>&nbsp;<font color=\"navy\">" + dbname + "</font></td>");
                                                                                drawList.append("\n\t\t<td width=\"8%\" nowrap>&nbsp;"+ v.elementAt(i) +"</td>");
                                                                                break;
                                                                        case 2 : // Payroll
                                                                                String payroll = "";
                                                                                if(hashPayroll.get(""+v.elementAt(i))==null)
                                                                                        payroll = "?";
                                                                                else
                                                                                        payroll = ""+v.elementAt(i);
                                                                                drawList.append("\n\t\t<td width=\"5%\" align=\"center\" nowrap><input type=\"hidden\" name=\"employee_id\" value=\"" + hashPayroll.get(v.elementAt(i)) + "\">"+payroll+"</td>");
                                                                                break;
                                                                        case 7 : // LOS 1
                                                                                drawList.append("\n\t\t<td width=\"1%\" align=\"right\"><input type=\"hidden\" name=\"LOS1\" value=\"" + Math.round((double) Double.parseDouble(v.elementAt(i).toString())) + "\">"+Math.round((double) Double.parseDouble(v.elementAt(i).toString()))+"</td>");
                                                                                break;
                                                                        case 8 : // LOS 2
                                                                                drawList.append("\n\t\t<td width=\"1%\" align=\"right\"><input type=\"hidden\" name=\"LOS2\" value=\"" + Math.round((double) Double.parseDouble(v.elementAt(i).toString())) + "\">"+Math.round((double) Double.parseDouble(v.elementAt(i).toString()))+"</td>");
                                                                                break;
                                                                        case 9 : // Current Basic
                                                                                try {
                                                                                    curr_basic = Double.parseDouble(v.elementAt(i).toString());
                                                                                }
                                                                                catch (Exception e) { curr_basic = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\"><input type=\"hidden\" name=\"CURR_BASIC\" value=\"" + curr_basic + "\">"+numberFormatter.format(curr_basic)+"</td>");
                                                                                break;
                                                                        case 10 : // Current Transport
                                                                                try {
                                                                                    curr_trans = Double.parseDouble(v.elementAt(i).toString());
                                                                                }
                                                                                catch (Exception e) { curr_trans = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\"><input type=\"hidden\" name=\"CURR_TRANS\" value=\"" + curr_trans + "\">"+numberFormatter.format(curr_trans)+"</td>");
                                                                                break;
                                                                        case 11: // Current Total
                                                                                try {
                                                                                    curr_total = curr_basic + curr_trans;//Double.parseDouble(v.elementAt(i).toString());
                                                                                }
                                                                                catch (Exception e) { curr_total = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\" bgcolor=\"yellow\"><input type=\"hidden\" name=\"CURR_TOTAL\" value=\"" + curr_total + "\">"+numberFormatter.format(curr_total)+"</td>");
                                                                                break;
                                                                        case 12 : // New Basic
                                                                                try {
                                                                                    new_basic = Double.parseDouble(v.elementAt(i).toString());
                                                                                }
                                                                                catch (Exception e) { new_basic = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\"><input type=\"hidden\" name=\"NEW_BASIC\" value=\"" + new_basic + "\">"+numberFormatter.format(new_basic)+"</td>");
                                                                                break;
                                                                        case 13 : // New Transport
                                                                                try {
                                                                                    new_trans = Double.parseDouble(v.elementAt(i).toString());
                                                                                }
                                                                                catch (Exception e) { new_trans = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\"><input type=\"hidden\" name=\"NEW_TRANS\" value=\"" + new_trans + "\">"+numberFormatter.format(new_trans)+"</td>");
                                                                                break;
                                                                        case 14 : // New Total
                                                                                try {
                                                                                    new_total = new_basic + new_trans;//Double.parseDouble(v.elementAt(i).toString());
                                                                                }
                                                                                catch (Exception e) { new_total = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\" bgcolor=\"yellow\"><input type=\"hidden\" name=\"NEW_TOTAL\" value=\"" + new_total + "\">"+numberFormatter.format(new_total)+"</td>");
                                                                                // Increment Basic
                                                                                inc_basic = new_basic - curr_basic;
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\" bgcolor=\"yellow\"><input type=\"hidden\" name=\"INC_BASIC\" value=\"" + inc_basic + "\">"+numberFormatter.format(inc_basic)+"</td>");
                                                                                // Increment Transportation
                                                                                inc_trans = new_trans - curr_trans;
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\" bgcolor=\"yellow\"><input type=\"hidden\" name=\"INC_TRANS\" value=\"" + inc_trans + "\">"+numberFormatter.format(inc_trans)+"</td>");
                                                                                break;
                                                                        case 17 : // Additional
                                                                                try {
                                                                                    inc_add = Double.parseDouble(v.elementAt(i).toString());
                                                                                }
                                                                                catch (Exception e) { inc_add = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"5%\" align=\"right\"><input type=\"hidden\" name=\"INC_ADD\" value=\"" + inc_add + "\">"+numberFormatter.format(inc_add)+"</td>");
                                                                                // Increment Total
                                                                                inc_total = inc_basic + inc_trans + inc_add;
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\" nowrap bgcolor=\"yellow\"><input type=\"hidden\" name=\"INC_TOTAL\" value=\"" + inc_total + "\">"+numberFormatter.format(inc_total)+"</td>");
                                                                                // Percentage Basic
																				try{
																					if(curr_basic>0){
																						pct_basic = inc_basic / curr_basic * 100;
																					}
																					else{
																						pct_basic = 0;
																					}
																				}
                                                                                catch (Exception e) { pct_basic = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\" nowrap bgcolor=\"yellow\"><input type=\"hidden\" name=\"PCT_BASIC\" value=\"" + pct_basic + "\">"+numberFormatter.format(pct_basic)+"%</td>");
                                                                                // Percentage Transport
																				try{
																					if(curr_trans>0){
                                                                                		pct_trans = inc_trans / curr_trans * 100;
																					}
																					else{
																						pct_trans = 0;
																					}
																				}
                                                                                catch (Exception e) { pct_trans = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\" nowrap bgcolor=\"yellow\"><input type=\"hidden\" name=\"PCT_TRANS\" value=\"" + pct_trans + "\">"+numberFormatter.format(pct_trans)+"%</td>");
                                                                                // Percentage Total
																				try{
																					if(curr_total>0){
                                                                                		pct_total = inc_total / curr_total * 100;
																					}
																					else{
																						pct_total = 0;
																					}
																				}
                                                                                catch (Exception e) { pct_total = 0.0; }
                                                                                drawList.append("\n\t\t<td  width=\"8%\" align=\"right\" nowrap bgcolor=\"yellow\"><input type=\"hidden\" name=\"PCT_TOTAL\" value=\"" + pct_total + "\">"+numberFormatter.format(pct_total)+"%</td>");
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
                                                                "\n\t\t<td nowrap> <a href=\"javascript:cmdSave()\" class=\"command\">Save Salary</a></td>"+
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
