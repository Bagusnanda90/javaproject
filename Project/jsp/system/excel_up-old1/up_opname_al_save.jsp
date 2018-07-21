<%@ page language = "java" %>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.LeavePeriod" %>

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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Uploader 
                  &gt; Leave Management &gt; AL Stock<!-- #EndEditable --> </strong></font> 
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
										boolean transferSuccess = false;  
                                        String[] empnumber = request.getParameterValues("empnumber");
                                        String[] lastyearQty = request.getParameterValues("lastyearqty");
                                        String[] entitledQty = request.getParameterValues("entitledqty");
										String[] earnedQty = request.getParameterValues("earnedytd");
                                        String[] takenQty = request.getParameterValues("takenytd");
										Date OpnameDate = FRMQueryString.requestDate(request,"opnamedate");
										String strError = "";
										
                                        for (int i=0; i<empnumber.length; i++) 
										{
											long empOid = PstEmployee.getEmployeeIdByNum(empnumber[i]);
											int toClearLastYear = Integer.parseInt(String.valueOf(lastyearQty[i]));
											int entitleThisYear = Integer.parseInt(String.valueOf(entitledQty[i]));
											int earnedYtd = Integer.parseInt(String.valueOf(earnedQty[i]));
											int takenYtd = Integer.parseInt(String.valueOf(takenQty[i]));											

											// jika nomor payroll ada di database
											if(empOid != 0)
											{
												// jika jumlah AL tahun lalu masih ada
												if(toClearLastYear > 0)
												{
													// jika earned this year ada
													if(earnedYtd > 0)
													{
														// proses pengecekan pengambilan AL (ytdLast dan ytdCurr)
														// ---- start pengecekan pengambilan AL ---
														int takenYtdLast = 0;
														int takenYtdCurr = 0;
														int takenAsPayable = 0;																																				
														if(takenYtd > 0)
														{
															if(takenYtd > toClearLastYear)
															{
																takenYtdLast = toClearLastYear;
																
																int takenYtdBalanceCurr = takenYtd - toClearLastYear;
																if(takenYtdBalanceCurr > earnedYtd)
																{
																	takenYtdCurr = earnedYtd;
																	takenAsPayable = takenYtdBalanceCurr - earnedYtd;
																}															
																else
																{
																	takenYtdCurr = takenYtdBalanceCurr;
																}
															}
															else
															{
																takenYtdLast = takenYtd;
															}														
														}
														// ---- end pengecekan pengambilan AL ---																												
														


														// AL Management last year, tanggal perolehan awal bulan JULI tahun lalu
														// ---- start insert AL last year ---																																										
														Date OpnameDateLastYear = new Date(OpnameDate.getYear()-1, 6, 1);																											
														long oidAl1 = 0;
														AlStockManagement objAlStockMgnLastYear = new AlStockManagement();
														objAlStockMgnLastYear.setEntitled(entitleThisYear);
														objAlStockMgnLastYear.setEmployeeId(empOid);
														objAlStockMgnLastYear.setAlQty(toClearLastYear);
														objAlStockMgnLastYear.setDtOwningDate(OpnameDateLastYear);												
														objAlStockMgnLastYear.setStNote("AL Opname");
														objAlStockMgnLastYear.setQtyUsed(takenYtdLast);											
														objAlStockMgnLastYear.setQtyResidue(objAlStockMgnLastYear.getAlQty() - objAlStockMgnLastYear.getQtyUsed());
														objAlStockMgnLastYear.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
														if(objAlStockMgnLastYear.getQtyResidue() == 0)
														{
															objAlStockMgnLastYear.setAlStatus(PstAlStockManagement.AL_STS_TAKEN);
														}
														
														try 
														{
														    // pencatatan perolehan AL curr year
															com.dimata.harisma.entity.attendance.PstAlStockManagement objPstAlStockManagement = new com.dimata.harisma.entity.attendance.PstAlStockManagement();
															oidAl1 = objPstAlStockManagement.insertExc(objAlStockMgnLastYear);															
															
															// pencatatan pengambilan AL (taken)
															if(takenYtdLast > 0)
															{
																PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();
																AlStockTaken objAlStockTaken = new AlStockTaken();
																objAlStockTaken.setAlStockId(oidAl1);
																objAlStockTaken.setEmployeeId(empOid);
																objAlStockTaken.setTakenDate(OpnameDate);
																objAlStockTaken.setTakenQty(takenYtdLast);  
																objAlStockTaken.setPaidDate(OpnameDate);
																objPstAlStockTaken.insertExc(objAlStockTaken);    
															}
															
															transferSuccess = true;
														}
														catch (Exception e) 
														{
															System.out.println("Insert last year error : " + e);   
														}													
														// ---- end insert AL last year ---																																																																				
														
														
														
														// Proses insert AL general (umum/tanpa pengecekan apa-apa)
														// ---- start insert AL curr year ---																																																								
														long oidAl2 = 0;
														AlStockManagement objAlStockMgn = new AlStockManagement();
														objAlStockMgn.setEntitled(entitleThisYear);
														objAlStockMgn.setEmployeeId(empOid);
														objAlStockMgn.setAlQty(earnedYtd);
														objAlStockMgn.setDtOwningDate(OpnameDate);												
														objAlStockMgn.setStNote("AL Opname");
														objAlStockMgn.setQtyUsed(takenYtdCurr);											
														objAlStockMgn.setQtyResidue(objAlStockMgn.getAlQty() - objAlStockMgn.getQtyUsed());
														objAlStockMgn.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
														if(objAlStockMgn.getQtyResidue() == 0)
														{
															objAlStockMgn.setAlStatus(PstAlStockManagement.AL_STS_TAKEN);
														}
														
														try 
														{
															// pencatatan perolehan AL curr year
															com.dimata.harisma.entity.attendance.PstAlStockManagement objPstAlStockManagement = new com.dimata.harisma.entity.attendance.PstAlStockManagement();
															oidAl2 = objPstAlStockManagement.insertExc(objAlStockMgn);																													

															// pencatatan pengambilan AL (taken)
															if(takenYtdCurr > 0)
															{															
																PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();
																AlStockTaken objAlStockTaken = new AlStockTaken();
																objAlStockTaken.setAlStockId(oidAl2);
																objAlStockTaken.setEmployeeId(empOid);
																objAlStockTaken.setTakenDate(OpnameDate);
																objAlStockTaken.setTakenQty(takenYtdCurr);  
																objAlStockTaken.setPaidDate(OpnameDate);															
																objPstAlStockTaken.insertExc(objAlStockTaken);    
															}
															
															transferSuccess = true;
														}
														catch (Exception e) 
														{
															System.out.println("Insert error : " + e);
														}
														// ---- end insert AL curr year ---
														
														
														
														// --- start proses pembayaran hutang dengan data AL last year ---
														// pembayaran hutang AL kalau ada
														objAlStockMgnLastYear.setOID(oidAl1);
														com.dimata.harisma.entity.attendance.PstAlStockManagement objPstAlStockManagement = new com.dimata.harisma.entity.attendance.PstAlStockManagement();
														Vector vectOidLeavePaid1 = objPstAlStockManagement.paidAlPayable(empOid, objAlStockMgnLastYear);																																																																				
														// --- end proses pembayaran hutang dengan data AL last year ---
														
														
														
														// --- start proses pembayaran hutang dengan data AL curr year ---
														objAlStockMgn.setOID(oidAl2);
														Vector vectOidLeavePaid2 = objPstAlStockManagement.paidAlPayable(empOid, objAlStockMgn);																																																																																		
														// --- end proses pembayaran hutang dengan data AL curr year ---														
														
														
														
														// --- start proses pencatatan hutang AL sebanyak sisa stock ---
														if(takenAsPayable > 0)
														{
															// insert data di Al stock taken sebanyak nilai minus
															// karena sekali/satu record taken berarti ngambil 1 AL
															for(int it=0; it<takenAsPayable; it++)  
															{
																try
																{
																	// instantiate object DpStockTaken														  																
																	AlStockTaken objAlStockTaken = new AlStockTaken();
																	objAlStockTaken.setAlStockId(0);
																	objAlStockTaken.setEmployeeId(empOid);
																	objAlStockTaken.setTakenDate(OpnameDate);
																	objAlStockTaken.setTakenQty(1);
		  
																	// insert data objDpStockTaken into database	
																	PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();
																	objPstAlStockTaken.insertExc(objAlStockTaken);    
																}
																catch(Exception e)
																{
																	System.out.println("Exc when insert objAlStockTaken : " + e.toString());
																}
															}														
														}
														// --- end proses pencatatan hutang AL sebanyak sisa stock ---																												
													}
													
													
													
													

													// jika earned this year tidak ada
													else
													{
														// proses pengecekan pengambilan AL (ytdLast dan ytdCurr)
														// ---- start pengecekan pengambilan AL ---
														int takenYtdLast = 0;
														int takenAsPayable = 0;																																				
														if(takenYtd > 0)
														{
															if(takenYtd > toClearLastYear)
															{
																takenYtdLast = toClearLastYear;																
																takenAsPayable = takenYtd - toClearLastYear;
															}
															else
															{
																takenYtdLast = takenYtd;
															}														
														}
														// ---- end pengecekan pengambilan AL ---																												
													
													
														// AL Management last year, tanggal perolehan awal bulan JULI tahun lalu
														// ---- start insert AL last year ---																																										
														Date OpnameDateLastYear = new Date(OpnameDate.getYear()-1, 6, 1);																											
														long oidAl1 = 0;
														AlStockManagement objAlStockMgnLastYear = new AlStockManagement();
														objAlStockMgnLastYear.setEntitled(entitleThisYear);
														objAlStockMgnLastYear.setEmployeeId(empOid);
														objAlStockMgnLastYear.setAlQty(toClearLastYear);
														objAlStockMgnLastYear.setDtOwningDate(OpnameDateLastYear);												
														objAlStockMgnLastYear.setStNote("AL Opname");
														objAlStockMgnLastYear.setQtyUsed(takenYtdLast);											
														objAlStockMgnLastYear.setQtyResidue(objAlStockMgnLastYear.getAlQty() - objAlStockMgnLastYear.getQtyUsed());
														objAlStockMgnLastYear.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
														if(objAlStockMgnLastYear.getQtyResidue() == 0)
														{
															objAlStockMgnLastYear.setAlStatus(PstAlStockManagement.AL_STS_TAKEN);
														}
														
														try 
														{
															// instantiate object DpStockTaken														
															com.dimata.harisma.entity.attendance.PstAlStockManagement objPstAlStockManagement = new com.dimata.harisma.entity.attendance.PstAlStockManagement();
															oidAl1 = objPstAlStockManagement.insertExc(objAlStockMgnLastYear);															
															
															// pencatatan pengambilan AL (taken)
															if(takenYtdLast > 0)
															{
																PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();
																AlStockTaken objAlStockTaken = new AlStockTaken();
																objAlStockTaken.setAlStockId(oidAl1);
																objAlStockTaken.setEmployeeId(empOid);
																objAlStockTaken.setTakenDate(OpnameDate);
																objAlStockTaken.setTakenQty(takenYtdLast); 
																objAlStockTaken.setPaidDate(OpnameDate);															 
																objPstAlStockTaken.insertExc(objAlStockTaken);    
															}
															
															transferSuccess = true;
														}
														catch (Exception e) 
														{
															System.out.println("Insert last year error : " + e);   
														}
														// ---- end insert AL last year ---																																																																				
													
													
														// --- start proses pembayaran hutang dengan data AL last year ---
														// pembayaran hutang AL kalau ada
														objAlStockMgnLastYear.setOID(oidAl1);
														com.dimata.harisma.entity.attendance.PstAlStockManagement objPstAlStockManagement = new com.dimata.harisma.entity.attendance.PstAlStockManagement();
														Vector vectOidLeavePaid1 = objPstAlStockManagement.paidAlPayable(empOid, objAlStockMgnLastYear);																																																																				
														// --- end proses pembayaran hutang dengan data AL last year ---
														
														
														// --- start proses pencatatan hutang AL sebanyak sisa stock ---
														if(takenAsPayable > 0)
														{
															// insert data di Al stock taken sebanyak nilai minus
															// karena sekali/satu record taken berarti ngambil 1 AL
															for(int it=0; it<takenAsPayable; it++)  
															{
																try
																{
																	// instantiate object DpStockTaken  
																	PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();														  																
																	AlStockTaken objAlStockTaken = new AlStockTaken();
																	objAlStockTaken.setAlStockId(0);
																	objAlStockTaken.setEmployeeId(empOid);
																	objAlStockTaken.setTakenDate(OpnameDate);
																	objAlStockTaken.setTakenQty(1);
																	objPstAlStockTaken.insertExc(objAlStockTaken);    
																}
																catch(Exception e)
																{
																	System.out.println("Exc when insert objAlStockTaken : " + e.toString());
																}
															}
														}
														// --- end proses pencatatan hutang AL sebanyak sisa stock ---																																										
													}	
												}
												
												
												
												
												
												
												

												// jika jumlah AL tahun lalu tidak ada
												else
												{												
													// jika earned this year ada
													if(earnedYtd > 0)
													{
														// proses pengecekan pengambilan AL (ytdLast dan ytdCurr)
														// ---- start pengecekan pengambilan AL ---
														int takenYtdCurr = 0;
														int takenAsPayable = 0;																																				
														if(takenYtd > 0)
														{
															if(takenYtd > earnedYtd)
															{
																takenYtdCurr = earnedYtd;																
																takenAsPayable = takenYtd - earnedYtd;
															}
															else
															{
																takenYtdCurr = takenYtd;
															}														
														}
														// ---- end pengecekan pengambilan AL ---																												
													
													
														// -------------------- Proses insert AL general (umum/tanpa pengecekan apa-apa)
														// ---- start insert AL curr year ---																																																								
														long oidAl2 = 0;
														AlStockManagement objAlStockMgn = new AlStockManagement();
														objAlStockMgn.setEntitled(entitleThisYear);
														objAlStockMgn.setEmployeeId(empOid);
														objAlStockMgn.setAlQty(earnedYtd);
														objAlStockMgn.setDtOwningDate(OpnameDate);												
														objAlStockMgn.setStNote("AL Opname");
														objAlStockMgn.setQtyUsed(takenYtdCurr);											
														objAlStockMgn.setQtyResidue(objAlStockMgn.getAlQty() - objAlStockMgn.getQtyUsed());
														objAlStockMgn.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
														if(objAlStockMgn.getQtyResidue() == 0)
														{
															objAlStockMgn.setAlStatus(PstAlStockManagement.AL_STS_TAKEN);
														}
														
														try 
														{
															// instantiate object DpStockTaken
															com.dimata.harisma.entity.attendance.PstAlStockManagement objPstAlStockManagement = new com.dimata.harisma.entity.attendance.PstAlStockManagement();
															oidAl2 = objPstAlStockManagement.insertExc(objAlStockMgn);														
															
															// pencatatan pengambilan AL (taken)
															if(takenYtdCurr > 0)
															{
																PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();
																AlStockTaken objAlStockTaken = new AlStockTaken();
																objAlStockTaken.setAlStockId(oidAl2);
																objAlStockTaken.setEmployeeId(empOid);
																objAlStockTaken.setTakenDate(OpnameDate);
																objAlStockTaken.setTakenQty(takenYtdCurr);  
																objAlStockTaken.setPaidDate(OpnameDate);															
																objPstAlStockTaken.insertExc(objAlStockTaken);    
															}
															
															transferSuccess = true;
														}
														catch (Exception e) 
														{
															System.out.println("Insert error : " + e);
														}
														// ---- end insert AL curr year ---
														
														
														
														// --- start proses pembayaran hutang dengan data AL curr year ---
														objAlStockMgn.setOID(oidAl2);
														com.dimata.harisma.entity.attendance.PstAlStockManagement objPstAlStockManagement = new com.dimata.harisma.entity.attendance.PstAlStockManagement();														
														Vector vectOidLeavePaid2 = objPstAlStockManagement.paidAlPayable(empOid, objAlStockMgn);																																																																																		
														// --- end proses pembayaran hutang dengan data AL curr year ---														
														
														
														// --- start proses pencatatan hutang AL sebanyak sisa stock ---
														if(takenAsPayable > 0)
														{
															// insert data di Al stock taken sebanyak nilai minus
															// karena sekali/satu record taken berarti ngambil 1 AL
															for(int it=0; it<takenAsPayable; it++)  
															{
																try
																{
																	// instantiate object DpStockTaken  
																	PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();														  																
																	AlStockTaken objAlStockTaken = new AlStockTaken();
																	objAlStockTaken.setAlStockId(0);
																	objAlStockTaken.setEmployeeId(empOid);
																	objAlStockTaken.setTakenDate(OpnameDate);
																	objAlStockTaken.setTakenQty(1);
																	objPstAlStockTaken.insertExc(objAlStockTaken);    
																}
																catch(Exception e)
																{
																	System.out.println("Exc when insert objAlStockTaken : " + e.toString());
																}
															}
														} 
														// --- end proses pencatatan hutang AL sebanyak sisa stock ---																																																								
													}
													
													
													
													
													// jika earned this year tidak ada
													else
													{
														// jika takenYtd lebih dari nol, berarti ngutang
														if(takenYtd > 0)
														{
															// insert data di Al stock taken sebanyak nilai minus
															// karena sekali/satu record taken berarti ngambil 1 AL
															for(int it=0; it<takenYtd; it++)  
															{
																try
																{
																	// instantiate object DpStockTaken														  																
																	PstAlStockTaken objPstAlStockTaken = new PstAlStockTaken();
																	AlStockTaken objAlStockTaken = new AlStockTaken();
																	objAlStockTaken.setAlStockId(0);
																	objAlStockTaken.setEmployeeId(empOid);
																	objAlStockTaken.setTakenDate(OpnameDate);
																	objAlStockTaken.setTakenQty(1);
																	objPstAlStockTaken.insertExc(objAlStockTaken);                                                            													
																}
																catch(Exception e)
																{
																	System.out.println("Exc when insert objAlStockTaken : " + e.toString());
																}
															}														
														}
													}
												}												
											}
											
											else
											{
												strError =  strError + "<br>&nbsp;&nbsp;&nbsp;.::error, payroll : " + empnumber[i] + ", employeeId : " + empOid + ", takenYtd : " + takenYtd;											
											}
                                        }										

										if(transferSuccess && !(strError!=null && strError.length()>0))
										{
											out.println("Import data AL Stock success ...");
										}
										else
										{
											out.println("<font color=\"#FF0000\">Import data AL Stock failed ...</font>");
											out.println(strError); 
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
