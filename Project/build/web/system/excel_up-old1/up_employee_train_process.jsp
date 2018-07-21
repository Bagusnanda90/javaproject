<%@ page language = "java" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.System"%>
<%@ page import="java.io.ByteArrayInputStream"%>
<%@ page import="java.io.IOException"%>

<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.util.Excel"%>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="com.dimata.util.blob.TextLoader" %>
<%@ page import="org.apache.poi.hssf.usermodel.*" %>

<%@ include file = "../../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_UPLOAD_DATA, AppObjInfo.OBJ_EMP_UPLOAD_DATA_TRAINEE); %>
<%@ include file = "../../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privSubmit=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_SUBMIT));
%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Upload Employee Trainee</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Uploader &gt; Employee &gt; Category Trainee<!-- #EndEditable --> 
                  </strong></font> </td>
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
							
								try 
								{
									uploader.uploadText(config, request, response);
									Object obj = uploader.getTextFile("file");
									byte byteText[] = null;
									byteText = (byte[]) obj;
									ByteArrayInputStream inStream;
									inStream = new ByteArrayInputStream(byteText); 
							
									Excel tp = new Excel();
									int numcol = 9; // jumlah kolom untuk employee train excel
									v = tp.ReadStream((InputStream) inStream, numcol);
									double dt = 0.0;							
							
									// Proses untuk category employee (training)
									String strEmpCat = "TRAIN"; // change if name of category is different															
									long idCategory = 0;
									Vector listCtg = PstEmpCategory.listAll();
									if(listCtg!=null && listCtg.size()>0)										
									{
										int maxCat = listCtg.size();
										for(int k=0; k<maxCat; k++)
										{
											EmpCategory ctg = (EmpCategory) listCtg.get(k);
											if((ctg.getEmpCategory().toUpperCase()).indexOf(strEmpCat) >= 0 )
											{
												idCategory = ctg.getOID();
												break;	
											}	
										}
									}
							
									// Proses religion
									Hashtable hashReligion = new Hashtable();
									Vector listRel = PstReligion.listAll();
									Religion dummyRel = (Religion) listRel.get(0);
									hashReligion.put("", String.valueOf(dummyRel.getOID()));
									for (int ls = 0; ls < listRel.size(); ls++) {
										Religion rel = (Religion) listRel.get(ls);
										hashReligion.put(rel.getReligion(), String.valueOf(rel.getOID()));
									}
							
									// Proses category
									/*
									Hashtable hashCategory = new Hashtable();
									Vector listCtg = PstEmpCategory.listAll();
									EmpCategory dummyCat = (EmpCategory) listCtg.get(0);
									hashReligion.put("", String.valueOf(dummyCat.getOID()));
									for (int k = 0; k < listCtg.size(); k++) {
										EmpCategory ctg = (EmpCategory) listCtg.get(k);
										hashCategory.put(ctg.getEmpCategory(), String.valueOf(ctg.getOID()));
									}
									*/
							
									// Proses marital
									Hashtable hashMarital = new Hashtable();
									Vector listMar = PstMarital.listAll();
									Marital dummyMar = (Marital) listMar.get(0);
									hashMarital.put("", String.valueOf(dummyMar.getOID()));
									for (int ls = 0; ls < listMar.size(); ls++) {
										Marital mar = (Marital) listMar.get(ls);
										hashMarital.put(mar.getMaritalCode(), String.valueOf(mar.getOID()));
									}
							
									// Proses division
									Hashtable hashDivision = new Hashtable();
									Vector listDiv = PstDivision.listAll();
									Division dummyDiv = (Division) listDiv.get(0);
									hashDivision.put("", String.valueOf(dummyDiv.getOID()));
									for (int ls = 0; ls < listDiv.size(); ls++) {
										Division div = (Division) listDiv.get(ls);
										hashDivision.put(div.getDivision(), String.valueOf(div.getOID()));
									}

									// Proses department
									Hashtable hashDepartment = new Hashtable();
									Vector listDep = PstDepartment.listAll();
									Department dummyDep = (Department) listDep.get(0);
									hashDepartment.put("", String.valueOf(dummyDep.getOID()));
									for (int ls = 0; ls < listDep.size(); ls++) {
										Department dep = (Department) listDep.get(ls);
										hashDepartment.put(dep.getDepartment(), String.valueOf(dep.getOID()));
									}
							
									// Proses position
									Hashtable hashPosition = new Hashtable();
									Vector listPos = PstPosition.listAll();
									Position dummyPos = (Position) listPos.get(0);
									hashPosition.put("", String.valueOf(dummyPos.getOID()));
									for (int ls = 0; ls < listPos.size(); ls++) {
										Position pos = (Position) listPos.get(ls);
										hashPosition.put(pos.getPosition(), String.valueOf(pos.getOID()));
									}
							
									// Proses section
									Hashtable hashSection = new Hashtable();
									Vector listSec = PstSection.listAll();
									Section dummySec = (Section) listSec.get(0);
									hashSection.put("", String.valueOf(dummySec.getOID()));
									for (int ls = 0; ls < listSec.size(); ls++) {
										Section sec = (Section) listSec.get(ls);
										hashSection.put(sec.getSection(), String.valueOf(sec.getOID()));
									}
							
									// Proses level
									Hashtable hashLevel = new Hashtable();
									Vector listLev = PstLevel.listAll();
									Level dummyLev = (Level) listLev.get(0);
									hashLevel.put("", String.valueOf(dummyLev.getOID()));
									for (int ls = 0; ls < listLev.size(); ls++) {
										Level lev = (Level) listLev.get(ls);
										hashLevel.put(lev.getLevel(), String.valueOf(lev.getOID()));
									}
																							
									
									out.println("<form name=\"frmupload\" method=\"post\" action=\"up_employee_train_save.jsp\">");
									out.println("<table class=\"listarea\"><tr><td>");
									out.println("<table class=\"listgen\" cellpadding=\"1\" cellspacing=\"1\"><tr>");
																		
									// Jika ukuran vector yang menyimpan data dari excel lebih dari nol, maka proses
									if(v!=null && v.size()>0)
									{
										int maxV = v.size();
										
										// create header/title
										for(int tit=0; tit<numcol; tit++) 
										{											
											out.println("<td class=\"listgentitle\">"+v.elementAt(tit)+"</td>");
										}
										out.println("</tr><tr>");										
										
										
										// iterasi dilakukan mulai indeks ke (numcol) karena baris pertama dari schedule excel adalah JUDUL/TITLE								
										for(int i=numcol; i<maxV; i++) 
										{											
											// Dicheck sisa hasil bagi antara i dengan numcol, maka akan diproses sbb : 
											switch ((i % numcol)) 
											{
												case 0 : // kalo sisanya 0 ==> pada kolom I (employee number)
													out.println("<td class=\"listgensell\">");
													out.println("<input type=\"hidden\" name=\"postalcode\" value=\"0\">");												
													out.println("<input type=\"hidden\" name=\"religion\" value=\"" + String.valueOf(hashReligion.get(String.valueOf(""))) + "\">");												
													out.println("<input type=\"hidden\" name=\"bloodtype\" value=\"0\">");												
													out.println("<input type=\"hidden\" name=\"ssn\" value=\"0\">");												
													out.println("<input type=\"hidden\" name=\"marital\" value=\"" + String.valueOf(hashMarital.get(String.valueOf(""))) + "\">");												
													out.println("<input type=\"hidden\" name=\"position\" value=\"" + String.valueOf(hashPosition.get(String.valueOf(""))) + "\">");												
													out.println("<input type=\"hidden\" name=\"section\" value=\"" + String.valueOf(hashSection.get(String.valueOf(""))) + "\">");												
													out.println("<input type=\"hidden\" name=\"level\" value=\"" + String.valueOf(hashLevel.get(String.valueOf(""))) + "\">");												
													out.println("<input type=\"hidden\" name=\"division\" value=\"" + String.valueOf(hashDivision.get(String.valueOf(""))) + "\">");																																						
																																																																																																								
													out.println("<input type=\"hidden\" name=\"empcategory\" value=\"" + idCategory + "\">");																																					
													out.println("<input type=\"hidden\" name=\"empnumber\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i));
													out.println("</tr>");
													break;
													
												case 1 : // kalo sisanya 1 ==> pada kolom II (employee name)
													out.println("<td class=\"listgensell\"><input type=\"hidden\" name=\"fullname\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
													break;
													
												case 2 : // kalo sisanya 2 ==> pada kolom III (employee commencing data)
													try {
														dt = Double.parseDouble(String.valueOf(v.elementAt(i)));
													}
													catch (Exception e) {
													}
													out.println("<td class=\"listgensell\"><input type=\"hidden\" name=\"commencingdate\" value=\"" + Formater.formatDate(HSSFDateUtil.getJavaDate(dt), "yyyy-MM-dd") + "\" >"+Formater.formatDate(HSSFDateUtil.getJavaDate(dt), "dd-MM-yyyy")+"</td>");
													break;
													
												case 3 : // kalo sisanya 3 ==> pada kolom IV (employee department)
													String oidDept = "0";
													try
													{
														oidDept = String.valueOf(hashDepartment.get(String.valueOf(v.elementAt(i))));
													}
													catch(Exception e)
													{
														System.out.println("Err : "+e.toString());
													}
													out.println("<td class=\"listgensell\"><input type=\"hidden\" name=\"department\" value=\"" + oidDept + "\" >"+v.elementAt(i)+"</td>");
													break;
													
												case 4 : // kalo sisanya 4 ==> pada kolom V (employee birth place)
													out.println("<td class=\"listgensell\"><input type=\"hidden\" name=\"birthplace\" value=\"" + v.elementAt(i) + "\" >"+v.elementAt(i)+"</td>");
													break;
													
												case 5 : // kalo sisanya 5 ==> pada kolom VI (employee birth date)
													try {
														dt = Double.parseDouble(String.valueOf(v.elementAt(i)));
													}
													catch (Exception e) {
													}
													out.println("<td class=\"listgensell\"><input type=\"hidden\" name=\"birthdate\" value=\"" + Formater.formatDate(HSSFDateUtil.getJavaDate(dt), "yyyy-MM-dd") + "\" >"+Formater.formatDate(HSSFDateUtil.getJavaDate(dt), "dd-MM-yyyy")+"</td>");
													break;
													
												case 6 : // kalo sisanya 6 ==> pada kolom VII (employee address)
													out.println("<td class=\"listgensell\"><input type=\"hidden\" name=\"address\" value=\"" + v.elementAt(i) + "\" >"+v.elementAt(i)+"</td>");
													break;
													
												case 7 : // kalo sisanya 7 ==> pada kolom VIII (employee sex)
													String sex = String.valueOf(v.elementAt(i));
													String intSex = "0";
													if (sex.equalsIgnoreCase("F")) 
													{
														intSex = "1";
													}												
													out.println("<td class=\"listgensell\"><input type=\"hidden\" name=\"sex\" value=\"" + intSex + "\" >"+sex+"</td>");
													break;
													
												case 8 : // kalo sisanya 8 ==> pada kolom IX (employee phone)
													out.println("<td class=\"listgensell\"><input type=\"hidden\" name=\"phone\" value=\"" + String.valueOf(v.elementAt(i)) + "\" >"+String.valueOf(v.elementAt(i))+"</td>");
													out.println("</tr><tr>");
													break;
													
												default :
													out.println("<td class=\"listgensell\">" + v.elementAt(i) + "</td>");
													break;
											}
										}										
									}
									
									out.println("</table>");
									out.println("</td></tr></table>");
																		
									if(privSubmit)
									{
										out.println("<table><tr><td>");
										out.println("<input type=\"submit\" value=\"Upload Data Employee\">");
										out.println("</td></tr></table>");
									}
									
									out.println("</form>");
									inStream.close();
									
								}
								catch (Exception e) 
								{
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
