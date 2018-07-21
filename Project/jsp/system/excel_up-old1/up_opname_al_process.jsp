<%@ page language = "java" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.System"%>
<%@ page import="java.io.ByteArrayInputStream"%>  
<%@ page import="java.io.IOException"%>

<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.gui.jsp.*"%>
<%@ page import="com.dimata.util.Excel"%>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="com.dimata.util.blob.TextLoader" %>
<%@ page import="org.apache.poi.hssf.usermodel.*" %>

<%@ include file = "../../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_LEAVE_OPNAME, AppObjInfo.OBJ_EMP_LEAVE_AL_OPNAME); %>
<%@ include file = "../../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privSubmit=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_SUBMIT));
%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Upload AL Stock</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
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
                  Uploader &gt; Leave Management &gt; AL Stock<!-- #EndEditable --> 
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
							  	// ngambil/upload file sesuai dengan yang dipilih oleh "browse"
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
									
									// jumlah kolom untuk al opname excel
									int numcol = 7; 
									v = tp.ReadStream((InputStream) inStream, numcol);
									double dt = 0.0;							
							
									// proses untuk data al stock employee
									out.println("<form name=\"frmupload\" method=\"post\" action=\"up_opname_al_save.jsp\">");
									out.println("&nbsp;&nbsp;<b>List employee's AL Stock</b>");
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
										out.println("</tr>");																			
										
											
										// iterasi dilakukan mulai indeks ke (numcol) karena baris pertama dari schedule excel adalah JUDUL/TITLE								
										for(int i=numcol; i<maxV; i++) 
										{											
											// dicheck sisa hasil bagi antara i dengan numcol, maka akan diproses sbb : 
											int it = i / numcol;
											switch ((i % numcol)) 
											{
												case 0 : // kalo sisanya 0 ==> pada kolom I (data number)
													out.println("<tr>");
													out.println("<td class=\"listgensell\">"+it+"</td>");
													break;
													
												case 1 : // kalo sisanya 1 ==> pada kolom I (employee payroll)
													out.println("<td class=\"listgensell\"><input type=\"hidden\" name=\"empnumber\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");												
													break;

												case 2 : // kalo sisanya 2 ==> pada kolom II (employee name)
													out.println("<td class=\"listgensell\">"+v.elementAt(i)+"</td>");
													break;

												case 3 : // kalo sisanya 3 ==> pada kolom III (last year quantity)
													out.println("<td align=\"right\" class=\"listgensell\"><input type=\"hidden\" name=\"lastyearqty\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
													break;

												case 4 : // kalo sisanya 4 ==> pada kolom IV (entitled quantity)
													out.println("<td align=\"right\" class=\"listgensell\"><input type=\"hidden\" name=\"entitledqty\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
													break;

												case 5 : // kalo sisanya 5 ==> pada kolom V (earned ytd qty)
													out.println("<td align=\"right\" class=\"listgensell\"><input type=\"hidden\" name=\"earnedytd\" value=\"" + v.elementAt(i) + "\">"+v.elementAt(i)+"</td>");
													break;
													
												case 6 : // kalo sisanya 6 ==> pada kolom VI (al stock)
													out.println("<td align=\"right\" class=\"listgensell\"><input type=\"hidden\" name=\"takenytd\" value=\"" + v.elementAt(i) + "\" >"+v.elementAt(i)+"</td>");
													out.println("</tr>");													
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
										out.println("<br>&nbsp;&nbsp;<b>Opname Date : </b>");																		
										out.println("<table><tr><td>");
										out.println("&nbsp;"+ControlDate.drawDate("opnamedate", new Date(), "formElemen", 1, -2));
										out.println("<input type=\"submit\" value=\"Upload AL\">");									
										out.println("</td></tr></table>");
									}

									out.println("</form>");
									inStream.close();
									
								}
								catch (Exception e) 
								{
									System.out.println("---===Error : " + e.toString());   
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
