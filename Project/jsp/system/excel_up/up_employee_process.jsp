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

<%@ include file = "../../../main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../../main/checkuser.jsp" %>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Upload Employee Data</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../../main/mnmain.jsp" %>
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
                Uploader
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
<%   
    String a = request.getParameter("file"); 
    TextLoader uploader = new TextLoader();
    FileOutputStream fOut = null;
    Vector v = new Vector();

    try {
        uploader.uploadText(config, request, response);
        Object obj = uploader.getTextFile("file");
        byte byteText[] = null;
        byteText = (byte[]) obj;
        ByteArrayInputStream inStream;
        inStream = new ByteArrayInputStream(byteText); 

        Excel tp = new Excel();
        int numcol = 18;
        v = tp.ReadStream((InputStream) inStream, numcol);
        out.println("<form name=\"frmupload\" method=\"post\" action=\"up_employee_save.jsp\">");
        out.println("<table cellpadding=\"1\" cellspacing=\"1\" border=\"1\"><tr>");
        double dt = 0.0;

        Hashtable hashReligion = new Hashtable();
        Vector listRel = PstReligion.listAll();
        Religion dummyRel = (Religion) listRel.get(0);
        hashReligion.put("", String.valueOf(dummyRel.getOID()));
        //hashReligion.put("0", "-");
        for (int ls = 0; ls < listRel.size(); ls++) {
            Religion rel = (Religion) listRel.get(ls);
            hashReligion.put(rel.getReligion(), String.valueOf(rel.getOID()));
        }

        Hashtable hashCategory = new Hashtable();
        Vector listCtg = PstEmpCategory.listAll();
        //hashReligion.put("0", "-");
        for (int k = 0; k < listCtg.size(); k++) {
            EmpCategory ctg = (EmpCategory) listCtg.get(k);
            hashCategory.put(ctg.getEmpCategory(), String.valueOf(ctg.getOID()));
        }
		
		Hashtable hashDivision = new Hashtable();
		Vector listDivi = PstDivision.listAll();
        for (int k = 0; k < listDivi.size(); k++) {
            Division divi = (Division) listDivi.get(k);
            hashDivision.put(divi.getDivision(), String.valueOf(divi.getOID()));
        }

        Hashtable hashMarital = new Hashtable();
        Vector listMar = PstMarital.listAll();
        Marital dummyMar = (Marital) listMar.get(0);
        hashMarital.put("", String.valueOf(dummyMar.getOID()));
        //hashMarital.put("", "0");
        for (int ls = 0; ls < listMar.size(); ls++) {
            Marital mar = (Marital) listMar.get(ls);
            hashMarital.put(mar.getMaritalCode(), String.valueOf(mar.getOID()));
        }

        Hashtable hashDepartment = new Hashtable();
        Vector listDep = PstDepartment.listAll();
        Department dummyDep = (Department) listDep.get(0);
        hashDepartment.put("", String.valueOf(dummyDep.getOID()));
        for (int ls = 0; ls < listDep.size(); ls++) {
            Department dep = (Department) listDep.get(ls);
            hashDepartment.put(dep.getDepartment(), String.valueOf(dep.getOID()));
        }

        /*Hashtable hashPosition = new Hashtable();
        Vector listPos = PstPosition.listAll();
        Position dummyPos = (Position) listPos.get(0);
        hashPosition.put("", String.valueOf(dummyPos.getOID()));
        for (int ls = 0; ls < listPos.size(); ls++) {
            Position pos = (Position) listPos.get(ls);
            hashPosition.put(pos.getPosition(), String.valueOf(pos.getOID()));
        }*/

        /*Hashtable hashSection = new Hashtable();
        Vector listSec = PstSection.listAll();
        Section dummySec = (Section) listSec.get(0);
        hashSection.put("-", String.valueOf(dummySec.getOID()));
        for (int ls = 0; ls < listSec.size(); ls++) {
            Section sec = (Section) listSec.get(ls);
            hashSection.put(sec.getSection(), String.valueOf(sec.getOID()));
        }

        Hashtable hashLevel = new Hashtable();
        Vector listLev = PstLevel.listAll();
        Level dummyLev = (Level) listLev.get(0);
        hashLevel.put("-", String.valueOf(dummyLev.getOID()));
        for (int ls = 0; ls < listLev.size(); ls++) {
            Level lev = (Level) listLev.get(ls);
            hashLevel.put(lev.getLevel(), String.valueOf(lev.getOID()));
        }*/
		
		out.println(""+
		"<td>NIK</td>"+
		"<td>NAME</td>"+
		"<td>ADDR</td>"+
		"<td>ADDR2</td>"+
		//"<td>EMG ADDR</td>"+
		"<td>PHONE</td>"+
		"<td>HP</td>"+
		"<td>SEX</td>"+
		"<td>RELIGION</td>"+
		"<td>DIVISION</td>"+
		"<td>DEPARTMENT</td>"+
		"<td>SECTION</td>"+
		"<td>MARITAL</td>"+
		"<td>POSITION</td>"+
		"<td>LEVEL</td>"+
		"<td>BRT PLACE</td>"+
		"<td>BRT DATE</td>"+
		"<td>CMC DATE</td>"+
		"<td>BLOOD</td>"+
		"</tr><tr>");

		System.out.println("numcol : "+numcol);
		System.out.println("v.size() : "+v.size());
        for (int i = 0; i < v.size(); i++) {
            if (i > numcol - 1) {
                switch (((i+1) % numcol)) {
                    case 1 :
                        out.println("<td><input type=\"text\" name=\"empnumber\" value=\"" + v.elementAt(i) + "\" size=\"5\"></td>");
                        break;
                    case 2 :
                        out.println("<td><input type=\"text\" name=\"fullname\" value=\"" + v.elementAt(i) + "\" size=\"10\"></td>");
                        break;
                    case 3 :
                        out.println("<td><input type=\"text\" name=\"address\" value=\"" + v.elementAt(i) + "\" size=\"10\"></td>");
                        break;
					case 4 :
                        out.println("<td><input type=\"text\" name=\"address2\" value=\"" + v.elementAt(i) + "\" size=\"10\"></td>");
                        break;
					case 5 :
                        out.println("<td><input type=\"text\" name=\"phone\" value=\"" + v.elementAt(i) + "\" size=\"10\"></td>");
                        break;
								
                    case 6 :
                        out.println("<td><input type=\"text\" name=\"hp\" value=\"" + v.elementAt(i) + "\" size=\"5\"></td>");
                        break;
						
                    case 7 :
                        String sex = String.valueOf(v.elementAt(i));
                        if (sex.equalsIgnoreCase("F")) {
                            out.println("<td><input type=\"text\" name=\"sex\" value=\"1\" size=\"5\"></td>");
                        }
                        else {
                            out.println("<td><input type=\"text\" name=\"sex\" value=\"0\" size=\"5\"></td>");
                        }
                        break;

                    case 8 :
                        String rel = String.valueOf(v.elementAt(i));
                        out.println("<td><input type=\"text\" name=\"religion\" value=\"" + ((hashReligion.get(rel)==null) ? "0" : hashReligion.get(rel)) + "\" size=\"5\"></td>");
                        break;
						
					case 9 :
                        String divi = String.valueOf(v.elementAt(i));
                        out.println("<td><input type=\"text\" name=\"division\" value=\"" + ((hashDivision.get(divi)==null) ? "0" : hashDivision.get(divi)) + "\" size=\"5\"></td>");
                        break;
						
					case 10 :
                        out.println("<td><input type=\"text\" name=\"department\" value=\"" + ((hashDepartment.get(String.valueOf(v.elementAt(i)))==null) ? "0" : hashDepartment.get(String.valueOf(v.elementAt(i)))) + "\" size=\"5\"></td>");
                        break;
					case 11 :
                        //out.println("<td>sec<input type=\"text\" name=\"section\" value=\"" + ((hashSection.get(String.valueOf(v.elementAt(i)))==null) ? "0" : hashSection.get(String.valueOf(v.elementAt(i)))) + "\" size=\"5\"></td>");
						out.println("<td><input type=\"text\" name=\"section\" value=\"" + String.valueOf(v.elementAt(i)) + "\" size=\"5\"></td>");
                        break;
					
					case 12 :
                        out.println("<td><input type=\"text\" name=\"marital\" value=\"" + ((hashMarital.get(String.valueOf(v.elementAt(i)))==null) ? "0" : hashMarital.get(String.valueOf(v.elementAt(i)))) + "\" size=\"5\"></td>");
                        break;	
					case 13 :
                        //out.println("<td>posit<input type=\"text\" name=\"position\" value=\"" + ((hashPosition.get(String.valueOf(v.elementAt(i)))==null) ? "0" : hashPosition.get(String.valueOf(v.elementAt(i)))) + "\" size=\"5\"></td>");
						out.println("<td><input type=\"text\" name=\"position\" value=\"" + String.valueOf(v.elementAt(i)) + "\" size=\"5\"></td>");
                        break;
					case 14 :
                        //out.println("<td>lvl<input type=\"text\" name=\"level\" value=\"" + ((hashLevel.get(String.valueOf(v.elementAt(i))) ==null) ? "0" : hashLevel.get(String.valueOf(v.elementAt(i)))) + "\" size=\"5\"></td>");
						out.println("<td><input type=\"text\" name=\"level\" value=\"" + String.valueOf(v.elementAt(i)) + "\" size=\"5\"></td>");
                        break;	
					case 15 :
                        out.println("<td><input type=\"text\" name=\"birthplace\" value=\"" + ((v.elementAt(i)==null) ? "" : v.elementAt(i)) + "\" size=\"5\"></td>");
                        break;							
					case 16 :
                        out.println("<td><input type=\"text\" name=\"dob\" value=\"" + ((v.elementAt(i)==null) ? "" : v.elementAt(i)) + "\" size=\"5\"></td>");
                        break;
					case 17 :
                        out.println("<td><input type=\"text\" name=\"commencing\" value=\"" + ((v.elementAt(i)==null) ? "" : v.elementAt(i)) + "\" size=\"5\"></td>");
                        break;	
					case 0 :
                        out.println("<td><input type=\"text\" name=\"blood\" value=\"" + ((v.elementAt(i)==null) ? "" : v.elementAt(i)) + "\" size=\"5\"></td>");
                        break;								
                    
                    default :
                        out.println("<td>" + v.elementAt(i) + "</td>");
                        break;
                }
            }
            /*
            if ((((i+1) % numcol) == 9) || (((i+1) % numcol) == 0)) {
                try {
                    dt = Double.parseDouble(String.valueOf(v.elementAt(i)));
                }
                catch (Exception e) {
                    
                }
                out.println("<td nowrap>" + Formater.formatDate(HSSFDateUtil.getJavaDate(dt), "dd-MM-yyyy") + "</td>");
                //out.println("<td>" + dt + "</td>");
            }
            else {
                out.println("<td>" + v.elementAt(i) + "</td>");
            }
            */
            if (((i+1) % numcol) == 0) {
                out.println("</tr>");
            }
        }
        out.println("</table>");
        out.println("<input type=\"submit\" value=\"Save\">");
        out.println("</form>");
        inStream.close();
    }
    catch (Exception e) {
        System.out.println("---======---\nError : " + e);
    }
%>
<!-- #EndEditable -->
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td>&nbsp; </td>
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
      <%@ include file = "../../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
