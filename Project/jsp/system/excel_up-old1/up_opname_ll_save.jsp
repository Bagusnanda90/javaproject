<%@ page language = "java" %>
<%@ page import="java.util.Date"%>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ page import="com.dimata.harisma.utility.service.leavedp.*" %>

<%@ include file = "../../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_LEAVE_OPNAME, AppObjInfo.OBJ_EMP_LEAVE_LL_OPNAME); %>
<%@ include file = "../../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privSubmit=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_SUBMIT));
%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Upload LL Stock</title>
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
                  &gt; Leave Management &gt; LL Stock<!-- #EndEditable --> </strong></font> 
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
									String[] commdate = request.getParameterValues("commencingdate");
									String[] entitled1 = request.getParameterValues("entitlement1");
									String[] entitled2 = request.getParameterValues("entitlement2");
									String[] takenmtd = request.getParameterValues("takenmtd");
									String[] takenytd = request.getParameterValues("takenytd");
									Date OpnameDate = FRMQueryString.requestDate(request,"opnamedate");																																								
									String strError = "";
									
									long leavePeriodId = 0;  
									
									Date currDate = new Date();
									for (int i=0; i<empnumber.length; i++)   
									{
										long empOid = PstEmployee.getEmployeeIdByNum(empnumber[i]);
										Date commencingDate = Formater.formatDate(commdate[i], "yyyy-MM-dd");
										int ent1  = Integer.parseInt(String.valueOf(entitled1[i]));
										int ent2  = Integer.parseInt(String.valueOf(entitled2[i]));
										int takenMtd = Integer.parseInt(String.valueOf(takenmtd[i]));										
										int takenYtd = Integer.parseInt(String.valueOf(takenytd[i]));											
										
										
										// jika nomor payroll ada di database
										if(empOid != 0)  
										{
											// jika entitle 1 lebih dari nol
											if(ent1 > 0)
											{
												// jika entitle 2 lebih dari nol
												if(ent2 > 0)
												{
													// perhitungan jumlah takenYtd1 dan takenYtd2 utk masing-masing ent1 & ent2
													// ---- start perhitungan takenYtd ---
													int takenYtd1 = 0;
													int takenYtd2 = 0;
													int takenAsPayable = 0;
													if(takenYtd > ent1)
													{																									
														takenYtd1 = ent1;  
														
														int takenYtdBalance = takenYtd - ent1;
														if(takenYtdBalance > ent2)
														{
															takenYtd2 = ent2;
															takenAsPayable = takenYtdBalance - ent2;
														}															
														else
														{
															takenYtd2 = takenYtdBalance;
														}																											
													}													
													else
													{
														takenYtd1 = takenYtd;																														
													}
													// ---- end perhitungan takenYtd ---													



													// proses untuk record entitlement 1   
													// ---- start proses record entitlement 1 ---																										
													int lLStatus1 = PstLLStockManagement.LL_STS_NOT_AKTIF;
													long oidLl1 = 0;											
													Date commencingDate1 = new Date(commencingDate.getYear()+5, commencingDate.getMonth(), commencingDate.getDate());													
													LLStockManagement objLlStockMgn1 = new LLStockManagement();
													objLlStockMgn1.setEntitled(1);
													objLlStockMgn1.setLeavePeriodeId(leavePeriodId);
													objLlStockMgn1.setEmployeeId(empOid);
													objLlStockMgn1.setLLQty(ent1); 
													objLlStockMgn1.setDtOwningDate(commencingDate1);
													objLlStockMgn1.setStNote("LL Opname");
													objLlStockMgn1.setQtyUsed(takenYtd1);											
													objLlStockMgn1.setQtyResidue(objLlStockMgn1.getLLQty() - objLlStockMgn1.getQtyUsed());
													objLlStockMgn1.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
													if(objLlStockMgn1.getQtyResidue() == 0)
													{
														objLlStockMgn1.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);													
													}
																										
													try 
													{
														// pencatatan perolehan LL entitle I
														com.dimata.harisma.entity.attendance.PstLLStockManagement objPstLLStockManagement1 = new com.dimata.harisma.entity.attendance.PstLLStockManagement();													
														oidLl1 = objPstLLStockManagement1.insertExc(objLlStockMgn1);
														
														// pencatatan pengambilan LL (taken)
														if(takenYtd1 > 0)
														{
															PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
															LlStockTaken objLlStockTaken = new LlStockTaken();
															objLlStockTaken.setLlStockId(oidLl1);
															objLlStockTaken.setEmployeeId(empOid);
															objLlStockTaken.setTakenDate(OpnameDate);
															objLlStockTaken.setTakenQty(takenYtd1); 
															objLlStockTaken.setPaidDate(OpnameDate); 
															objPstLlStockTaken.insertExc(objLlStockTaken);    
														}
														
														transferSuccess = true;
														lLStatus1 = objLlStockMgn1.getLLStatus();													
													}
													catch (Exception e) 
													{
														System.out.println("Insert error : " + e);
													}
													// ---- end proses record entitlement 1 ---																										
													
													
													
													// proses untuk record entitlement 2
													// ---- start proses record entitlement 2 ---																										
													int lLStatus2 = PstLLStockManagement.LL_STS_NOT_AKTIF;
													long oidLl2 = 0;													
													System.out.println(".::Process second ...");													
													Date commencingDate2 = new Date(commencingDate.getYear()+10, commencingDate.getMonth(), commencingDate.getDate());														
													LLStockManagement objLlStockMgn2 = new LLStockManagement();
													objLlStockMgn2.setEntitled(2);
													objLlStockMgn2.setLeavePeriodeId(leavePeriodId);
													objLlStockMgn2.setEmployeeId(empOid);
													objLlStockMgn2.setLLQty(ent2); 
													objLlStockMgn2.setDtOwningDate(commencingDate2);
													objLlStockMgn2.setStNote("LL Opname");
													objLlStockMgn2.setQtyUsed(takenYtd2);											
													objLlStockMgn2.setQtyResidue(objLlStockMgn2.getLLQty() - objLlStockMgn2.getQtyUsed());
													objLlStockMgn2.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
													if(objLlStockMgn2.getQtyResidue() == 0)
													{
														objLlStockMgn2.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);													
													}													
													
													try 
													{
														// pencatatan perolehan LL entitle I
														com.dimata.harisma.entity.attendance.PstLLStockManagement objPstLLStockManagement2 = new com.dimata.harisma.entity.attendance.PstLLStockManagement();																											
														oidLl2 = objPstLLStockManagement2.insertExc(objLlStockMgn2);														
														
														// pencatatan pengambilan LL (taken)
														if(takenYtd2 > 0)
														{
															PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
															LlStockTaken objLlStockTaken = new LlStockTaken();
															objLlStockTaken.setLlStockId(oidLl2);
															objLlStockTaken.setEmployeeId(empOid);
															objLlStockTaken.setTakenDate(OpnameDate);
															objLlStockTaken.setTakenQty(takenYtd2);  
															objLlStockTaken.setPaidDate(OpnameDate);
															objPstLlStockTaken.insertExc(objLlStockTaken);    
														}
														
														transferSuccess = true;
														lLStatus2 = objLlStockMgn2.getLLStatus();
													}
													catch (Exception e) 
													{
														System.out.println("Insert error : " + e);
													}
													// ---- end proses record entitlement 2 ---													
													
													
													// pembayaran hutang 1
													// --- start pembayaran hutang entitle 1 ---
													objLlStockMgn1.setOID(oidLl1);
													com.dimata.harisma.entity.attendance.PstLLStockManagement objPstLLStockManagement = new com.dimata.harisma.entity.attendance.PstLLStockManagement();																											
													Vector vectOidLeavePaid1 = objPstLLStockManagement.paidLlPayable(empOid, objLlStockMgn1);																																								
													// --- end pembayaran hutang entitle 1 ---


													// pembayaran hutang 2
													// --- start pembayaran hutang entitle 2 ---
													objLlStockMgn2.setOID(oidLl2);
													Vector vectOidLeavePaid2 = objPstLLStockManagement.paidLlPayable(empOid, objLlStockMgn2);																																																					
													// --- end pembayaran hutang entitle 2 ---

													// --- start proses pencatatan hutang AL sebanyak sisa stock ---
													if(takenAsPayable > 0)
													{
														// insert data di Al stock taken sebanyak nilai minus
														// karena sekali/satu record taken berarti ngambil 1 AL
														for(int it=0; it<takenAsPayable; it++)  
														{
															try
															{
																// pencatatan pengambilan LL (taken)
																PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
																LlStockTaken objLlStockTaken = new LlStockTaken();
																objLlStockTaken.setLlStockId(0);
																objLlStockTaken.setEmployeeId(empOid);
																objLlStockTaken.setTakenDate(OpnameDate);
																objLlStockTaken.setTakenQty(1);  
																objPstLlStockTaken.insertExc(objLlStockTaken);    
																   
															}
															catch(Exception e)
															{
																System.out.println("Exc when insert objAlStockTaken : " + e.toString());
															}
														}														
													}
													// --- end proses pencatatan hutang AL sebanyak sisa stock ---																												
												}
												
												
												
												// jika entitle 2 adalah nol
												else
												{
													// perhitungan jumlah takenYtd1 dan takenYtd2 utk masing-masing ent1 & ent2
													// ---- start perhitungan takenYtd ---
													int takenYtd1 = 0;
													int takenAsPayable = 0;
													if(takenYtd > ent1)
													{																									
														takenYtd1 = ent1;  														
														takenAsPayable = takenYtd - ent1;
													}													
													else
													{
														takenYtd1 = takenYtd;																														
													}
													// ---- end perhitungan takenYtd ---
																									
												
													// proses untuk record entitlement 1   
													// ---- start proses record entitlement 1 ---													
													int lLStatus1 = PstLLStockManagement.LL_STS_NOT_AKTIF;
													long oidLl1 = 0;											
													Date commencingDate1 = new Date(commencingDate.getYear()+5, commencingDate.getMonth(), commencingDate.getDate());													
													LLStockManagement objLlStockMgn1 = new LLStockManagement();
													objLlStockMgn1.setEntitled(1);
													objLlStockMgn1.setLeavePeriodeId(leavePeriodId);
													objLlStockMgn1.setEmployeeId(empOid);
													objLlStockMgn1.setLLQty(ent1); 
													objLlStockMgn1.setDtOwningDate(commencingDate1);
													objLlStockMgn1.setStNote("LL Opname");
													objLlStockMgn1.setQtyUsed(takenYtd1);											
													objLlStockMgn1.setQtyResidue(objLlStockMgn1.getLLQty() - objLlStockMgn1.getQtyUsed());
													objLlStockMgn1.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
													if(objLlStockMgn1.getQtyResidue() == 0)
													{
														objLlStockMgn1.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);													
													}
																										
													try 
													{
														// pencatatan perolehan LL entitle I													
														com.dimata.harisma.entity.attendance.PstLLStockManagement objPstLLStockManagement1 = new com.dimata.harisma.entity.attendance.PstLLStockManagement();													
														oidLl1 = objPstLLStockManagement1.insertExc(objLlStockMgn1);
														
														// pencatatan pengambilan LL (taken)
														if(takenYtd1 > 0)
														{
															PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
															LlStockTaken objLlStockTaken = new LlStockTaken();
															objLlStockTaken.setLlStockId(oidLl1);
															objLlStockTaken.setEmployeeId(empOid);
															objLlStockTaken.setTakenDate(OpnameDate);
															objLlStockTaken.setTakenQty(takenYtd1);  
															objLlStockTaken.setPaidDate(OpnameDate);
															objPstLlStockTaken.insertExc(objLlStockTaken);    
														}
														
														transferSuccess = true;
														lLStatus1 = objLlStockMgn1.getLLStatus();													
													}
													catch (Exception e) 
													{
														System.out.println("Insert error : " + e);
													}
													// ---- end proses record entitlement 1 ---																										
													
													
													// pembayaran hutang 1
													// --- start pembayaran hutang entitle 1 ---
													objLlStockMgn1.setOID(oidLl1);
													com.dimata.harisma.entity.attendance.PstLLStockManagement objPstLLStockManagement = new com.dimata.harisma.entity.attendance.PstLLStockManagement();																											
													Vector vectOidLeavePaid1 = objPstLLStockManagement.paidLlPayable(empOid, objLlStockMgn1);																																								
													// --- end pembayaran hutang entitle 1 ---
													
													
													// --- start proses pencatatan hutang AL sebanyak sisa stock ---
													if(takenAsPayable > 0)
													{
														// insert data di Al stock taken sebanyak nilai minus
														// karena sekali/satu record taken berarti ngambil 1 AL
														for(int it=0; it<takenAsPayable; it++)  
														{
															try
															{
																// pencatatan pengambilan LL (taken)
																PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
																LlStockTaken objLlStockTaken = new LlStockTaken();
																objLlStockTaken.setLlStockId(0);
																objLlStockTaken.setEmployeeId(empOid);
																objLlStockTaken.setTakenDate(OpnameDate);
																objLlStockTaken.setTakenQty(1);  
																objPstLlStockTaken.insertExc(objLlStockTaken);    
																   
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
											
											
											
											
											
											
											
											// jika entitle 1 adalah nol
											else
											{
												// jika entitle 2 lebih dari nol
												if(ent2 > 0)
												{
													// perhitungan jumlah takenYtd1 dan takenYtd2 utk masing-masing ent1 & ent2
													// ---- start perhitungan takenYtd ---
													int takenYtd2 = 0;
													int takenAsPayable = 0;
													if(takenYtd > ent2)
													{																									
														takenYtd2 = ent2;  														
														takenAsPayable = takenYtd - ent2;
													}													
													else
													{
														takenYtd2 = takenYtd;																														
													}
													// ---- end perhitungan takenYtd ---

												
													// proses untuk record entitlement 2
													// ---- start proses record entitlement 2 ---																										
													int lLStatus2 = PstLLStockManagement.LL_STS_NOT_AKTIF;
													long oidLl2 = 0;													
													System.out.println(".::Process second ...");													
													Date commencingDate2 = new Date(commencingDate.getYear()+10, commencingDate.getMonth(), commencingDate.getDate());														
													LLStockManagement objLlStockMgn2 = new LLStockManagement();
													objLlStockMgn2.setEntitled(2);
													objLlStockMgn2.setLeavePeriodeId(leavePeriodId);
													objLlStockMgn2.setEmployeeId(empOid);
													objLlStockMgn2.setLLQty(ent2); 
													objLlStockMgn2.setDtOwningDate(commencingDate2);
													objLlStockMgn2.setStNote("LL Opname");
													objLlStockMgn2.setQtyUsed(takenYtd2);											
													objLlStockMgn2.setQtyResidue(objLlStockMgn2.getLLQty() - objLlStockMgn2.getQtyUsed());
													objLlStockMgn2.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
													if(objLlStockMgn2.getQtyResidue() == 0)
													{
														objLlStockMgn2.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);													
													}													
													
													try 
													{
														// pencatatan perolehan LL entitle II													
														com.dimata.harisma.entity.attendance.PstLLStockManagement objPstLLStockManagement2 = new com.dimata.harisma.entity.attendance.PstLLStockManagement();																											
														oidLl2 = objPstLLStockManagement2.insertExc(objLlStockMgn2);														
														
														// pencatatan pengambilan LL (taken)
														if(takenYtd2 > 0)
														{
															PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
															LlStockTaken objLlStockTaken = new LlStockTaken();
															objLlStockTaken.setLlStockId(oidLl2);
															objLlStockTaken.setEmployeeId(empOid);
															objLlStockTaken.setTakenDate(OpnameDate);
															objLlStockTaken.setTakenQty(takenYtd2);
															objLlStockTaken.setPaidDate(OpnameDate);  
															objPstLlStockTaken.insertExc(objLlStockTaken);    
														}
														
														transferSuccess = true;
														lLStatus2 = objLlStockMgn2.getLLStatus();
													}
													catch (Exception e) 
													{
														System.out.println("Insert error : " + e);
													}
													// ---- end proses record entitlement 2 ---
													

													// pembayaran hutang 2
													// --- start pembayaran hutang entitle 2 ---
													objLlStockMgn2.setOID(oidLl2);
													com.dimata.harisma.entity.attendance.PstLLStockManagement objPstLLStockManagement = new com.dimata.harisma.entity.attendance.PstLLStockManagement();																																								
													Vector vectOidLeavePaid2 = objPstLLStockManagement.paidLlPayable(empOid, objLlStockMgn2);																																																					
													// --- end pembayaran hutang entitle 2 ---
													
													
													// --- start proses pencatatan hutang AL sebanyak sisa stock ---
													if(takenAsPayable > 0)
													{
														// insert data di Al stock taken sebanyak nilai minus
														// karena sekali/satu record taken berarti ngambil 1 AL
														for(int it=0; it<takenAsPayable; it++)  
														{
															try
															{
																// pencatatan pengambilan LL (taken)
																PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
																LlStockTaken objLlStockTaken = new LlStockTaken();
																objLlStockTaken.setLlStockId(0);
																objLlStockTaken.setEmployeeId(empOid);
																objLlStockTaken.setTakenDate(OpnameDate);
																objLlStockTaken.setTakenQty(1);  
																objPstLlStockTaken.insertExc(objLlStockTaken);    
																   
															}
															catch(Exception e)
															{
																System.out.println("Exc when insert objAlStockTaken : " + e.toString());
															}
														}														
													}
													// --- end proses pencatatan hutang AL sebanyak sisa stock ---																																									
												}
												
												
												

												// jika entitle 2 adalah nol
												else
												{
													// iterasi penyimpanan data history LL stock yang sudah habis diambil
													int intWorkingTime = (currDate.getYear()) - (commencingDate.getYear());
													int intMaxLLAmount = intWorkingTime / 5;
													
													for(int j=0; j<intMaxLLAmount; j++)
													{											
														Date commencingDateItr = new Date(commencingDate.getYear()+(5*(j+1)), commencingDate.getMonth(), commencingDate.getDate());
														
														LLStockManagement objLlStockMgn = new LLStockManagement();
														objLlStockMgn.setEntitled((j+1));
														objLlStockMgn.setLeavePeriodeId(leavePeriodId);
														objLlStockMgn.setEmployeeId(empOid);
														objLlStockMgn.setLLQty(ServiceLLStock.QTY_ENTITLED); 
														objLlStockMgn.setDtOwningDate(commencingDateItr); 
														objLlStockMgn.setStNote("LL Opname");
														objLlStockMgn.setQtyUsed(ServiceLLStock.QTY_ENTITLED);											
														objLlStockMgn.setQtyResidue(objLlStockMgn.getLLQty() - objLlStockMgn.getQtyUsed());
														objLlStockMgn.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);													
														
														try 
														{ 
															// pencatatan perolehan LL entitle ke i	
															com.dimata.harisma.entity.attendance.PstLLStockManagement objPstLLStockManagement = new com.dimata.harisma.entity.attendance.PstLLStockManagement();
															long oidLlIt = objPstLLStockManagement.insertExc(objLlStockMgn);	
															
															// pencatatan pengambilan LL (taken) ke i
															PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
															LlStockTaken objLlStockTaken = new LlStockTaken();
															objLlStockTaken.setLlStockId(oidLlIt);
															objLlStockTaken.setEmployeeId(empOid);
															objLlStockTaken.setTakenDate(commencingDateItr);
															objLlStockTaken.setTakenQty(ServiceLLStock.QTY_ENTITLED);
															objLlStockTaken.setPaidDate(commencingDateItr);  
															objPstLlStockTaken.insertExc(objLlStockTaken);    
															
															transferSuccess = true;
														}
														catch (Exception e) 
														{
															System.out.println("Insert error : " + e);
														}
													}																								
													
													// --- start proses pencatatan hutang AL sebanyak sisa stock ---
													if(takenYtd > 0)
													{
														// insert data di Al stock taken sebanyak nilai minus
														// karena sekali/satu record taken berarti ngambil 1 AL
														for(int it=0; it<takenYtd; it++)  
														{
															try
															{
																// pencatatan pengambilan LL (taken)
																PstLlStockTaken objPstLlStockTaken = new PstLlStockTaken();
																LlStockTaken objLlStockTaken = new LlStockTaken();
																objLlStockTaken.setLlStockId(0);
																objLlStockTaken.setEmployeeId(empOid);
																objLlStockTaken.setTakenDate(OpnameDate);
																objLlStockTaken.setTakenQty(1);  
																objPstLlStockTaken.insertExc(objLlStockTaken);    
																   
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
										}

										// jika nomor payroll tidak ada di database
										else
										{												
											strError =  strError + "<br>&nbsp;&nbsp;&nbsp;.::error, payroll : " + empnumber[i] + ", employeeId : " + empOid + ", entitle1 : " + ent1 + ", entitle2 : " + ent2;
										}																					
									}										

									if(transferSuccess && !(strError!=null && strError.length()>0))
									{
										out.println("Import data LL Stock success ...");
									}
									else
									{
										out.println("<font color=\"#FF0000\">Import data LL Stock failed ...</font>");
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
