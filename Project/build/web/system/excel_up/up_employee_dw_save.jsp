<%@ page language = "java" %>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_UPLOAD_DATA, AppObjInfo.OBJ_EMP_UPLOAD_DATA_DW); %>
<%@ include file = "../../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privSubmit=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_SUBMIT));
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Upload Employee Daily Worker</title>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Uploader 
                  &gt; Employee &gt; Category Daily Worker<!-- #EndEditable --> 
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
                                        String[] empnumber = request.getParameterValues("empnumber");
                                        String[] fullname = request.getParameterValues("fullname");
                                        String[] address = request.getParameterValues("address");
                                        String[] phone = request.getParameterValues("phone");
                                        String[] sex = request.getParameterValues("sex");
                                        String[] birthplace = request.getParameterValues("birthplace");
                                        String[] birthdate = request.getParameterValues("birthdate");
                                        String[] empcategory = request.getParameterValues("empcategory");
										System.out.println("empcategory : "+empcategory.length);
										for(int iter=0; iter<empcategory.length; iter++)
										{
											System.out.println("empcategory : "+empcategory[iter]);
										}
										
                                        String[] commencingdate = request.getParameterValues("commencingdate");
                                        String[] department = request.getParameterValues("department");
										
										// tambahan
                                        String[] postalcode = request.getParameterValues("postalcode");
                                        String[] religion = request.getParameterValues("religion");
                                        String[] bloodtype = request.getParameterValues("bloodtype");
                                        String[] ssn = request.getParameterValues("ssn");
                                        String[] marital = request.getParameterValues("marital");
                                        String[] position = request.getParameterValues("position");
                                        String[] section = request.getParameterValues("section");
                                        String[] level = request.getParameterValues("level");																				
                                        String[] division = request.getParameterValues("division");																														

										boolean transferSuccess = false;
                                        for (int i = 0; i < empnumber.length; i++) 
										{

										
                                            Employee emp = new Employee();											
                                            emp.setEmployeeNum(empnumber[i]);											
                                            emp.setFullName(fullname[i]); 											
                                            emp.setAddress(address[i]);
                                            emp.setPhone(phone[i]);
                                            emp.setPostalCode(Integer.parseInt(postalcode[i]));
                                            emp.setSex(Integer.parseInt(sex[i]));
                                            emp.setBirthPlace(birthplace[i]);
                                            emp.setBirthDate(Formater.formatDate(birthdate[i], "yyyy-MM-dd"));
                                            emp.setEmpCategoryId(Long.parseLong(empcategory[i]));
                                            emp.setReligionId(Long.parseLong(religion[i]));
                                            emp.setBloodType(bloodtype[i]);
                                            emp.setAstekNum(ssn[i]);
                                            emp.setMaritalId(Long.parseLong(marital[i]));																																		
                                            emp.setCommencingDate(Formater.formatDate(commencingdate[i], "yyyy-MM-dd"));
                                            emp.setDepartmentId(Long.parseLong(department[i]));
                                            emp.setPositionId(Long.parseLong(position[i]));
                                            emp.setSectionId(Long.parseLong(section[i]));
                                            emp.setLevelId(Long.parseLong(level[i]));
											emp.setDivisionId(Long.parseLong(division[i]));																					
											
                                            try {
												if(PstEmployee.empNumExist(String.valueOf(empnumber[i])))
												{
													System.out.println("update data ke " + i);
													PstEmployee.updateExc(emp);												
												}
												else
												{
													System.out.println("insert data ke " + i);												
													PstEmployee.insertExc(emp);																									
												}
												transferSuccess = true;
                                            }
                                            catch (Exception e) 
											{
                                                System.out.println("Insert error : " + e);
                                            }
                                        }
										
										
										
										if(transferSuccess)
										{
											out.println("Import data employee success ...");
										}
										else
										{
											out.println("<font color=\"#FF0000\">Import data employee failed ...</font>"); 
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
