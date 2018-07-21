<%@ page language = "java" %>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../../../main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../../main/checkuser.jsp" %>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HRIS - </title>
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
			  <!-- #BeginEditable "contenttitle" -->{contenttitle}<!-- #EndEditable --> 
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
                                        String[] empnumber = request.getParameterValues("empnumber");
                                        String[] fullname = request.getParameterValues("fullname");
                                        String[] address = request.getParameterValues("address");
										String[] address2 = request.getParameterValues("address2");
                                        String[] phone = request.getParameterValues("phone");
                                        String[] hp = request.getParameterValues("hp");
                                        String[] sex = request.getParameterValues("sex");
										String[] religion = request.getParameterValues("religion");
										String[] division = request.getParameterValues("division");
										String[] department = request.getParameterValues("department");
										String[] section = request.getParameterValues("section");										
										String[] marital = request.getParameterValues("marital");
                                        String[] position = request.getParameterValues("position");
										String[] level = request.getParameterValues("level");										
                                        String[] birthplace = request.getParameterValues("birthplace");
                                        String[] birthdate = request.getParameterValues("dob");
										String[] commencingdate = request.getParameterValues("commencing");
                                        //String[] empcategory = request.getParameterValues("empcategory");                                        
                                        String[] bloodtype = request.getParameterValues("blood");
                                        //String[] ssn = request.getParameterValues("ssn");
                                        
                                        
                                        
                                        
                                        //String[] level = request.getParameterValues("level");

                                        for (int i = 0; i < empnumber.length; i++) 
										{
										
											String snum = empnumber[i];
											int idx = snum.indexOf(".");
											if(idx>-1){
												snum = snum.substring(0,idx);
											}
											
											System.out.println("snum : "+snum);
											
											int lng = snum.length();
											if(lng==1){
												snum = "0000"+snum;
											}
											else if(lng==2){
												snum = "000"+snum;
											}
											else if(lng==3){
												snum = "00"+snum;
											}
											else if(lng==4){
												snum = "0"+snum;
											}
											else{
												snum = snum;
											}
											
											out.println("<br>snum : "+snum);
										
											String where = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"='"+snum+"'";
											Vector vct = PstEmployee.list(0,0, where, null);
											
											Employee emp = new Employee();
											
											if(vct!=null && vct.size()>0){
												emp = (Employee)vct.get(0);	
											}
                                            
                                            emp.setEmployeeNum(snum);
                                            emp.setFullName(fullname[i]);
                                            emp.setAddress(address[i]);
                                            emp.setPhone(phone[i]);
											emp.setHandphone(hp[i]);
                                            //emp.setPostalCode(Integer.parseInt(postalcode[i]));
                                            emp.setSex(Integer.parseInt(sex[i]));
                                            emp.setBirthPlace(birthplace[i]);
											try{
												out.println(", birt : "+birthdate[i]);
                                            	if(Formater.formatDate(birthdate[i], "MM/dd/yyyy")!=null){
													emp.setBirthDate(Formater.formatDate(birthdate[i], "MM/dd/yyyy"));
													
													out.println(", formater : "+Formater.formatDate(birthdate[i], "MM/dd/yyyy")+", class : "+emp.getBirthDate());
												}
											}
											catch(Exception ex){
												System.out.println("--- exception");
											}
											
                                            emp.setReligionId(Long.parseLong((religion[i]==null) ? "0" : religion[i]));
                                            emp.setBloodType(bloodtype[i]);
                                            //emp.setAstekNum(ssn[i]);
                                            emp.setMaritalId(Long.parseLong((marital[i]==null) ? "0" : marital[i]));
											try{
												out.println(",------- commenc : "+commencingdate[i]);
												if(Formater.formatDate(commencingdate[i], "MM/dd/yyyy")!=null){
                                            	 	emp.setCommencingDate(Formater.formatDate(commencingdate[i], "MM/dd/yyyy"));
													out.println(", formater : "+Formater.formatDate(commencingdate[i], "MM/dd/yyyy")+", class : "+emp.getCommencingDate());
												}
											}
											catch(Exception ex){
												System.out.println("--- exception1");
											}
                                            
											emp.setDepartmentId(Long.parseLong((department[i]==null) ? "0" : department[i]));
											emp.setDivisionId(Long.parseLong((division[i]==null) ? "0" : division[i]));
											
											
											//PROSES POSITION - kalo ada ambil - kalo ga ada input baru
											String s = PstPosition.fieldNames[PstPosition.FLD_POSITION]+"='"+position[i]+"'";
											Vector vx = PstPosition.list(0,0, s, "");
											long posId = 0;
											if(vx!=null && vx.size()>0){
												Position p = (Position)vx.get(0);
												posId = p.getOID();
											}
											else{
												Position p = new Position();
												p.setPosition(position[i]);
												try{
													posId = PstPosition.insertExc(p);
													System.out.println("\n----- inserting new position : "+position[i]+", id : "+posId+"\n");
												}
												catch(Exception e){
												}
											}
											
                                            emp.setPositionId(posId);
											
											//PROSES section - kalo ada ambil - kalo ga ada input baru
											s = PstSection.fieldNames[PstSection.FLD_SECTION]+"='"+section[i]+"'"+
												" AND "+PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+emp.getDepartmentId();
											vx = PstSection.list(0,0, s, "");
											posId = 0;
											if(vx!=null && vx.size()>0){
												Section p = (Section)vx.get(0);
												posId = p.getOID();
											}
											else{
												Section p = new Section();
												p.setSection(section[i]);
												p.setDepartmentId(emp.getDepartmentId());
												try{
													posId = PstSection.insertExc(p);
													System.out.println("\n----- inserting new section : "+section[i]+", id : "+posId+"\n");
												}
												catch(Exception e){
												}
											}
											
                                            emp.setSectionId(posId);
											
											//PROSES LEVEL - kalo ada ambil - kalo ga ada input baru
											s = PstLevel.fieldNames[PstLevel.FLD_LEVEL]+"='"+level[i]+"'";
											vx = PstLevel.list(0,0, s, "");
											posId = 0;
											if(vx!=null && vx.size()>0){
												Level p = (Level)vx.get(0);
												posId = p.getOID();
											}
											else{
												Level p = new Level();
												p.setLevel(level[i]);
												try{
													posId = PstLevel.insertExc(p);
													System.out.println("\n----- inserting new level : "+level[i]+", id : "+posId+"\n");
												}
												catch(Exception e){
												}
											}
											
                                            emp.setLevelId(posId);
											
											vx = PstEmpCategory.list(0,0, PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+"='Permanent'", "");
											posId = 0;
											if(vx!=null && vx.size()>0){
												EmpCategory p = (EmpCategory)vx.get(0);
												posId = p.getOID();
											}
											emp.setEmpCategoryId(posId);
											
											
                                            try {
												if(vct!=null && vct.size()>0){
	                                                PstEmployee.updateExc(emp);
													System.out.println("--- update employee i="+i);
												}else{
													PstEmployee.insertExc(emp);
													System.out.println("--- insert new employee i="+i);
												}
                                            }
                                            catch (Exception e) 
											{
                                                System.out.println("Insert error xxxxxx : " + e);
                                            }
											
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
