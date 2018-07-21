<%@ page language = "java" %>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.LeavePeriod" %>

<%@ include file = "../../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_LEAVE_OPNAME, AppObjInfo.OBJ_EMP_LEAVE_DP_OPNAME); %>
<%@ include file = "../../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privSubmit=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_SUBMIT));
%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Upload DP Stock</title>
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
                  &gt; Leave Management &gt; DP Stock<!-- #EndEditable --> </strong></font> 
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
										String[] acqdate = request.getParameterValues("acquisitiondate");										
                                        String[] dpstock = request.getParameterValues("dpstock");
										//Date acquisitionDate = FRMQueryString.requestDate(request,"opnamedate");										
										Date dpExpiredDate = null;
										String strError = "";																				

                                        for (int i=0; i<empnumber.length; i++) 
										{
											long empOid = PstEmployee.getEmployeeIdByNum(empnumber[i]);
											Date acquisitionDate = Formater.formatDate(acqdate[i], "yyyy-MM-dd");
											dpExpiredDate = new Date(acquisitionDate.getYear(), acquisitionDate.getMonth()+4, acquisitionDate.getDate());
											int dpOpnameQty = Integer.parseInt(String.valueOf(dpstock[i]));											

											if(empOid != 0 && dpOpnameQty != 0)
											{
												DpStockManagement objDpStockMgn = new DpStockManagement();
												
												// quantity DP lebih besar dari nol
												if(dpOpnameQty > 0)
												{
													objDpStockMgn.setLeavePeriodeId(0);
													objDpStockMgn.setEmployeeId(empOid);
													objDpStockMgn.setiDpQty(dpOpnameQty);
													objDpStockMgn.setDtOwningDate(acquisitionDate);
													objDpStockMgn.setDtExpiredDate(dpExpiredDate);
													objDpStockMgn.setiExceptionFlag(PstDpStockManagement.EXC_STS_NO);
													objDpStockMgn.setDtExpiredDateExc(dpExpiredDate);
													objDpStockMgn.setiDpStatus(PstDpStockManagement.DP_STS_AKTIF);
													objDpStockMgn.setStNote("DP Opname");
													objDpStockMgn.setQtyUsed(0);											
													objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());
												}
												
												// Tidak ada stock DP (nol atau minus)
												else												
												{
													objDpStockMgn.setLeavePeriodeId(0);
													objDpStockMgn.setEmployeeId(empOid);
													objDpStockMgn.setiDpQty(0);
													objDpStockMgn.setDtOwningDate(acquisitionDate);
													objDpStockMgn.setDtExpiredDate(dpExpiredDate);
													objDpStockMgn.setiExceptionFlag(PstDpStockManagement.EXC_STS_NO);
													objDpStockMgn.setDtExpiredDateExc(dpExpiredDate);
													objDpStockMgn.setiDpStatus(PstDpStockManagement.DP_STS_AKTIF);
													objDpStockMgn.setStNote("DP Opname");
													objDpStockMgn.setQtyUsed((-1)*dpOpnameQty);											
													objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());												
												}
												
												try 
												{
													Vector vectOidLeavePaid = new Vector(1,1);
													
													// Jika stock DP lebih dari nol 
													// maka insert DP stock 
													if(dpOpnameQty > 0)
													{
														com.dimata.harisma.entity.attendance.PstDpStockManagement objPstDpStockManagement = new com.dimata.harisma.entity.attendance.PstDpStockManagement();
														long oidDp = objPstDpStockManagement.insertExc(objDpStockMgn);														
														
														// pembayaran hutang DP kalau ada
														objDpStockMgn.setOID(oidDp);
														vectOidLeavePaid = objPstDpStockManagement.paidDpPayable(empOid, objDpStockMgn);																												
													}													
													
													// Jika stock DP nol atau minus, berarti proses ngutang DP
													// maka insert ke leave stock taken(tipe DP), dengan pengambilan DP pada saat tanggal opname
													// OID schedule yang ngambil DP juga di set 0, karena memang ga ada
													else
													{
														// insert data di Dp stock taken sebanyak nilai minus
														// karena sekali/satu record taken berarti ngambil 1 DP
														for(int it=0; it>dpOpnameQty; it--)
														{
															try
															{
																// instantiate object DpStockTaken														  																
																DpStockTaken objDpStockTaken = new DpStockTaken();
																objDpStockTaken.setDpStockId(0);
																objDpStockTaken.setEmployeeId(empOid);
																objDpStockTaken.setTakenDate(acquisitionDate);
																objDpStockTaken.setTakenQty(1);
	  
																// insert data objDpStockTaken into database	
																PstDpStockTaken objPstDpStockTaken = new PstDpStockTaken();
																objPstDpStockTaken.insertExc(objDpStockTaken);                                                            													
															}
															catch(Exception e)
															{
																System.out.println("Exc when insert objDpStockTaken : " + e.toString());
															}
														}
													}
													
													transferSuccess = true;
												}
												catch (Exception e) 
												{
													System.out.println("Insert error : " + e);
												}
											}
											else
											{
												strError =  strError + "<br>&nbsp;&nbsp;&nbsp;.::error, payroll : " + empnumber[i] + ", employeeId : " + empOid + ", OpnameQty : " + dpOpnameQty;
											}	
                                        }										

										if(transferSuccess && !(strError!=null && strError.length()>0))
										{
											out.println("Import data DP Stock success ...");
										}
										else
										{
											out.println("<font color=\"#FF0000\">Import data DP Stock failed ...</font>"); 
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
