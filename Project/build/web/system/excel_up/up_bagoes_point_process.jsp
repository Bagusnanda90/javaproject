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

<%
    int NUM_HEADER = 1;
	int NUM_CELL = 3;
	
	
	int iCommand = FRMQueryString.requestCommand(request);
    int month = FRMQueryString.requestInt(request, "month");		
	int year = FRMQueryString.requestInt(request, "year");		
	
	Date date = new Date();
	if(month==4){
		date.setDate(23);
	}
	else{
		date.setDate(15);
	}
	
	date.setYear(year-1900);
	
	date.setMonth(month);	
	
	//out.println(date);

    String msgString = "";
    if(iCommand == Command.SAVE){
			String[] employeeId = request.getParameterValues("employee_id");
			//out.println(employeeId[0]);
			String[] points = request.getParameterValues("point");
			//out.println(points[0]);

			Date start = (Date)date.clone();
			start.setDate(1);
			
			Date end = (Date)date.clone(); 
			end.setMonth(end.getMonth()+1);
			end.setDate(1);
			end.setDate(end.getDate()-1);

            for(int e=0;e < employeeId.length;e++){
                //EmpSchedule recog = new EmpSchedule();

                String where = "";
                    where += PstRecognition.fieldNames[PstRecognition.FLD_EMPLOYEE_ID] + "=" + employeeId[e];
                    where += " AND " + PstRecognition.fieldNames[PstRecognition.FLD_RECOG_DATE]+" BETWEEN \"";
                    where += Formater.formatDate(start, "yyyy-MM-dd") +"\" AND \""+ Formater.formatDate(end, "yyyy-MM-dd")+"\"";
                Vector vcheck = PstRecognition.list(0, 0, where, "");
				
				System.out.println("...===checking equality===...");
			

                if (vcheck !=null && vcheck.size() > 0) {
                    // update
                    try{
                        Recognition recog = (Recognition) vcheck.get(0);
                        recog.setEmployeeId(Long.parseLong(""+employeeId[e]));
                        recog.setRecogDate(date);
						Float f = new Float(Float.parseFloat(""+points[e]));
						
						int x = f.intValue();
						
                        recog.setPoint(x);
						
                        PstRecognition.updateExc(recog);
                    }
                    catch(Exception exc){
                        msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+" > Exc : "+exc.toString()+"</div>";
                    }
                }
                else {
                    // insert
                    try {

                        Recognition recog = new Recognition();
                        recog.setEmployeeId(Long.parseLong(""+employeeId[e]));
                        recog.setRecogDate(date);
                        Float f = new Float(Float.parseFloat(""+points[e]));
						
						int x = f.intValue();
						
                        recog.setPoint(x);

                        PstRecognition.insertExc(recog);
                    }
                    catch(Exception exc){
                        msgString = msgString + "<div class=\"errfont\">\n\tCan't save data row "+(e+1)+" Excep :: "+exc.toString()+"</div>";
                    }
                }
            }//end for
			
            if(msgString == null || msgString.length()<1)
                msgString = "<div class=\"msginfo\">Data have been saved</div>";
        
    }//end amd save

%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Working Schedule</title>
<script language="JavaScript">
    function cmdSave(){
        document.frmupload.command.value="<%=Command.SAVE%>";
        document.frmupload.action="up_bagoes_point_process.jsp";
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
        //document.frmupload.period_id.style.visibility="hidden";  
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
        //document.frmupload.period_id.style.visibility="";  
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
                  Uploader > Bagoes Point<!-- #EndEditable --> </strong></font> 
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
                                    int numcol = 3;
                                    StringBuffer drawList =  new StringBuffer();
                                    try {
                                        if (iCommand == Command.SAVE) {
                                            Vector vector = (Vector)session.getValue("BAGOES_POINT");
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
											
                                            if(session.getValue("BAGOES_POINT") != null)
                                                session.removeValue("BAGOES_POINT");
												
                                            session.putValue("BAGOES_POINT",vector);
                                            v = (Vector)vector.clone();
                                        }
                                        drawList.append("<form name=\"frmupload\" method=\"post\" action=\"\">"+
                                           "\n<input type=\"hidden\" name=\"command\" value=\""+iCommand+"\">");

                                            if(v.size()>0){
                                                    drawList.append("\n<table cellpadding=\"2\" cellspacing=\"2\" border=\"0\">"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td><li></td>"+
                                                            "\n\t\t<td colspan=\"2\">Choose the Period of Bagoes Point</td>"+ 
                                                            "\n\t</tr>"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td>&nbsp;</td>"+
                                                            "\n\t\t<td>Period</td>"+
                                                            "\n\t\t<td>"+
															"<select name=\"month\">"+															
															  "<option value=\"0\" "+((month==0) ? "selected" : "")+">Jan</option>"+
															  "<option value=\"1\" "+((month==1) ? "selected" : "")+">Feb</option>"+
															  "<option value=\"2\" "+((month==2) ? "selected" : "")+">Mar</option>"+
															  "<option value=\"3\" "+((month==3) ? "selected" : "")+">Apr</option>"+
															  "<option value=\"4\" "+((month==4) ? "selected" : "")+">Mei</option>"+
															  "<option value=\"5\" "+((month==5) ? "selected" : "")+">Jun</option>"+
															  "<option value=\"6\" "+((month==6) ? "selected" : "")+">Jul</option>"+
															  "<option value=\"7\" "+((month==7) ? "selected" : "")+">Aug</option>"+
															  "<option value=\"8\" "+((month==8) ? "selected" : "")+">Sep</option>"+
															  "<option value=\"9\" "+((month==9) ? "selected" : "")+">Okt</option>"+
															  "<option value=\"10\" "+((month==10) ? "selected" : "")+">Nop</option>"+
															  "<option value=\"12\" "+((month==11) ? "selected" : "")+">Des</option>"+
															"</select>"+
															"<select name=\"year\">"+															
															  "<option value=\"2000\" "+((year==2000) ? "selected" : "")+">2000</option>"+
															  "<option value=\"2001\" "+((year==2001) ? "selected" : "")+">2001</option>"+
															  "<option value=\"2002\" "+((year==2002) ? "selected" : "")+">2002</option>"+
															  "<option value=\"2003\" "+((year==2003) ? "selected" : "")+">2003</option>"+
															  "<option value=\"2004\" "+((year==2004) ? "selected" : "")+">2004</option>"+
															  "<option value=\"2005\" "+((year==2005) ? "selected" : "")+">2005</option>"+
															"</select>"+
															"</td>"+
                                                            "\n\t</tr>"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td><li></td>"+
                                                            "\n\t\t<td colspan=\"2\">List of Bagoes Point</td>"+ 
                                                            "\n\t</tr>"+
                                                            "\n</table>"+
                                                            "\n<table cellpadding=\"1\" cellspacing=\"1\" border=\"0\" width=\"100%\" class=\"listgen\">"+
                                                            "\n\t<tr>"+
                                                            "\n\t\t<td width=\"10%\"  class=\"tableheader\">No</td>"+
                                                            "\n\t\t<td width=\"50%\"  class=\"tableheader\">Employee</td>"+
                                                            "\n\t\t<td width=\"20%\"  class=\"tableheader\">Payroll</td>"+
                                                            "\n\t\t<td width=\"20%\"  align=\"center\" class=\"tableheader\">Point</td>"+
                                                            "\n\t</tr>");

                                                            double width =  100/(new Double(numcol)).doubleValue(); 
                                                            System.out.println("width == "+width);
															
															//out.println(v);
															
															drawList.append("\n\t<tr class=\"listgensell\">");

                                                            Hashtable hashPayroll = new Hashtable();
                                                            Vector listEmployee = PstEmployee.list(0, 0, "", "");
                                                            for (int e = 0; e < listEmployee.size(); e++) {
                                                                Employee employee = (Employee) listEmployee.get(e);
                                                                hashPayroll.put(employee.getEmployeeNum(), String.valueOf(employee.getOID()));
                                                            }
															
															System.out.println("------------ numcol : "+numcol );
															
                                                            for (int i = numcol; i < v.size(); i++) {
																			
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
																				
																				//System.out.println("dataPayroll : "+dataPayroll);
																				
                                                                                if(hashPayroll.get(""+dataPayroll)==null)
                                                                                        payroll = "?";
                                                                                else
                                                                                        payroll = ""+dataPayroll;
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\"><input type=\"hidden\" name=\"employee_id\" value=\"" + hashPayroll.get(v.elementAt(i)) + "\">"+payroll+"</td>");
                                                                                break;
                                                                        case 0 :
                                                                                drawList.append("\n\t\t<td  width=\""+width+"%\" align=\"center\"><input type=\"hidden\" name=\"point\" value=\"" + ((String)v.elementAt(i)).trim() + "\">"+(String)v.elementAt(i)+"</td>");
                                                                                break;
                                                                        default :
                                                                                break;
                                                                    }
                                                                    if (((i+1) % numcol) == 0) {
                                                                            drawList.append("</tr> "+((i != v.size()-1)? "\n\t<tr class=\"listgensell\">":"\n</table>"));
                                                                    }
                                                            }//end for
															
															
															
															
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
                                                                "\n\t\t<td nowrap> <a href=\"javascript:cmdSave()\" class=\"command\">Save Bagoes Point</a></td>"+
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
                                    <% } %>
                                    <br>
                                    
                                    <br>
                                    <!-- #EndEditable --> </td>
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
