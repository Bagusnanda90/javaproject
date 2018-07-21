<%@ page language = "java" %>
<% 
/* 
 * Page Name  		:  exp_rec.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.session.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_CLINIC_EXPENSE, AppObjInfo.OBJ_CLINIC_EXP_RECAPITULATE); %>
<%@ include file = "../../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

%>
<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
Date dtPeriod = FRMQueryString.requestDate(request,"date_periode");
Date dtPeriodend = FRMQueryString.requestDate(request,"date_periode_end");
int persons = FRMQueryString.requestInt(request,"persons");

if(iCommand == Command.NONE){
	dtPeriod = new Date();
		dtPeriod.setMonth(dtPeriod.getMonth()-1);
		dtPeriod.setDate(15);

	dtPeriodend = new Date();
		dtPeriodend.setDate(16);
}
/*variable declaration*/

Vector listAllExpense = SessMedicalRecord.getMedicalExpense(dtPeriod,dtPeriodend);													


%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->  
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Medical Expense Recapitulation</title>
<script language="JavaScript">
function cmdPrint()
{
	var dt   =  document.frmexprecapitulate.date_periode_dy.value;		
	var month   =  document.frmexprecapitulate.date_periode_mn.value;		
	var year    =  document.frmexprecapitulate.date_periode_yr.value;

	var dtend   =  document.frmexprecapitulate.date_periode_end_dy.value;		
	var monthend   =  document.frmexprecapitulate.date_periode_end_mn.value;		
	var yearend    =  document.frmexprecapitulate.date_periode_end_yr.value;
	
	var linkPage   = "exp_buffer.jsp?date_periode_yr=" + year + "&date_periode_mn=" + month +"&date_periode_dy="+dt+
		"&date_periode_end_yr=" + yearend + "&date_periode_end_mn=" + monthend +"&date_periode_end_dy="+dtend;
	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  				
}

function cmdPrintSummary()
{	
	var dt   =  document.frmexprecapitulate.date_periode_dy.value;		
	var month   =  document.frmexprecapitulate.date_periode_mn.value;		
	var year    =  document.frmexprecapitulate.date_periode_yr.value;

	var dtend   =  document.frmexprecapitulate.date_periode_end_dy.value;		
	var monthend   =  document.frmexprecapitulate.date_periode_end_mn.value;		
	var yearend    =  document.frmexprecapitulate.date_periode_end_yr.value;
	
	var linkPage   = "sum_exp_buffer.jsp?date_periode_yr=" + year + "&date_periode_mn=" + month +"&date_periode_dy="+dt+
		"&date_periode_end_yr=" + yearend + "&date_periode_end_mn=" + monthend +"&date_periode_end_dy="+dtend;

	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  				
}

function cmdPrintDetail()
{	
	var dt   =  document.frmexprecapitulate.date_periode_dy.value;		
	var month   =  document.frmexprecapitulate.date_periode_mn.value;		
	var year    =  document.frmexprecapitulate.date_periode_yr.value;

	var dtend   =  document.frmexprecapitulate.date_periode_end_dy.value;		
	var monthend   =  document.frmexprecapitulate.date_periode_end_mn.value;		
	var yearend    =  document.frmexprecapitulate.date_periode_end_yr.value;
	
	var linkPage   = "exp_bytype_buffer.jsp?date_periode_yr=" + year + "&date_periode_mn=" + month +"&date_periode_dy="+dt+
		"&date_periode_end_yr=" + yearend + "&date_periode_end_mn=" + monthend +"&date_periode_end_dy="+dtend;
	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  				
}

function cmdPrintType()
{	
	var month   =  document.frmexprecapitulate.date_periode_mn.value;		
	var year    =  document.frmexprecapitulate.date_periode_yr.value;												
	var linkPage   = "exp_bytype_buffer.jsp?date_periode_yr=" + year + "&date_periode_mn=" + month;			 	
	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  				
}

function chgPeriode(){
	document.frmexprecapitulate.command.value="<%=Command.LIST%>";
	document.frmexprecapitulate.action="exp_rec.jsp";
	document.frmexprecapitulate.submit();
}

//-------------- script control line -------------------
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

</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
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

</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Clinic 
                  &gt; Recapitulation Of Medical Expense <!-- #EndEditable --> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmexprecapitulate" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">                                                            
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" width="7%" class="txtheading1">&nbsp;</td>
                                                <td height="14" valign="middle" width="1%" class="txtheading1">&nbsp;</td>
                                                <td height="14" valign="middle" width="92%" class="txtheading1">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" width="7%" class="txtheading1">&nbsp;PERIODE</td>
                                                <td height="14" valign="middle" width="1%" class="txtheading1"> 
                                                  : </td>
                                                <td height="14" valign="middle" width="92%"> 
                                                  <%=ControlDate.drawDateWithStyle("date_periode", dtPeriod,5, -10,"formElemen", "")%> to <%=ControlDate.drawDateWithStyle("date_periode_end", dtPeriodend, 5, -10,"formElemen", "")%> 
                                                  <!--<% //out.println("expRecapitulate.getPeriode() "+expRecapitulate.getPeriode());%>
                                                  <%=	ControlDate.drawDateMY("date_periode", dtPeriod,"MMMM","formElemen", 0,-1) %> -->
                                                  <a href="javascript:chgPeriode()"><i><b>change</b></i></a> 
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">&nbsp;</td>
                                              </tr>
                                              <%
												try{													
													if (listAllExpense.size()>0){
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3" class="listtitle">Recapitulation 
                                                  Of Medical Expense List </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td valign="middle" colspan="3"> 
                                                  <table width="100%" border="0" cellspacing="1" cellpadding="2" class="listgen">
                                                    <tr> 
                                                      <td colspan="2" class="listgentitle"> 
                                                        <div align="center">DESCRIPTION</div>
                                                      </td>
                                                      <td class="listgentitle" width="11%"> 
                                                        <div align="center">AMOUNT 
                                                        </div>
                                                      </td>
                                                      <td colspan="2" class="listgentitle"> 
                                                        <div align="center">DISCOUNT 
                                                        </div>
                                                      </td>
                                                      <td width="14%" class="listgentitle"> 
                                                        <div align="center">TOTAL</div>
                                                      </td>
                                                      <td width="9%" class="listgentitle"> 
                                                        <div align="center">PERSON</div>
                                                      </td>
                                                    </tr>
                                                    <%  long expTypeId = 0;														
														double sumAmount = 0;
														double sumDiscRp = 0;
														double sumTotal = 0;
														int sumPerson = 0;
														double allAmount = 0;
														double allDiscRp = 0;
														double allTotal = 0;
														int allPerson = 0;														
														for(int i=0; i<listAllExpense.size();i++){
															Vector temp = (Vector)listAllExpense.get(i);
															MedicalType medicalType = (MedicalType)temp.get(0);
															MedExpenseType medExpenseType = (MedExpenseType)temp.get(1);
															MedicalRecord expRec = (MedicalRecord)temp.get(2);
															Vector vector = (Vector)temp.get(3);
															
															int person = Integer.parseInt(""+vector.get(0));																												
														%>
                                                    <% if(expTypeId != medicalType.getMedExpenseTypeId()){																
																expTypeId = medicalType.getMedExpenseTypeId();																
														%>
                                                    <% if(sumTotal != 0){
															 
															%>
                                                    <tr valign="top"> 
                                                      <td colspan="2" class="listgensell" height="33"><b>&nbsp;&nbsp;&nbsp;</b>SUB 
                                                        TOTAL</td>
                                                      <td width="11%" class="listgensell" align="right" height="33"><%=Formater.formatNumber(sumAmount,"#,###.#")%></td>
                                                      <td width="10%" class="listgensell" height="33">&nbsp;</td>
                                                      <td width="10%" class="listgensell" align="right" height="33"><%=Formater.formatNumber(sumDiscRp,"#,###.#")%></td>
                                                      <td width="14%" class="listgensell" align="right" height="33"><%=Formater.formatNumber(sumTotal,"#,###.#")%></td>
                                                      <td width="9%" class="listgensell" align="right" height="33"><%=sumPerson%></td>
                                                    </tr>
                                                    <%       
															 allAmount = allAmount + sumAmount;
															 allDiscRp = allDiscRp + sumDiscRp;
															 allTotal = allTotal + sumTotal;
															 allPerson = allPerson + sumPerson;                                                                                                    
															 sumAmount = 0;
															 sumDiscRp = 0;
															 sumTotal = 0;
															 sumPerson = 0;
														}%>
                                                    <tr> 
                                                      <td colspan="7" class="listgensell"><b><%=medExpenseType.getType().toUpperCase()%></b></td>
                                                    </tr>
                                                    <%}%>
                                                    <tr> 
                                                      <td width="7%" align="center" class="listgensell"><%=medicalType.getTypeCode()%></td>
                                                      <td width="39%" class="listgensell"><%=medicalType.getTypeName()%></td>
                                                      <td width="11%" align="right" class="listgensell"><%=Formater.formatNumber(expRec.getAmount(),"#,###.#")%></td>
                                                      <td width="10%" align="right" class="listgensell">
													  <%
													  double dis = (expRec.getDiscountInRp()/expRec.getAmount()) * 100;
													  %>
													  <%//=expRec.getDiscountInPercent()%>
													  <%=Formater.formatNumber(dis, "#,###.#")%>&nbsp;%</td>
                                                      <td width="10%" align="right" class="listgensell"><%=Formater.formatNumber(expRec.getDiscountInRp(),"#,###.#")%></td>
                                                      <td width="14%" align="right" class="listgensell"><%=Formater.formatNumber(expRec.getTotal(),"#,###.#")%></td>
                                                      <td width="9%" align="right" class="listgensell"><%=person%></td>
                                                    </tr>
                                                    <%  sumAmount = sumAmount + expRec.getAmount();
														sumDiscRp = sumDiscRp + expRec.getDiscountInRp();
														sumTotal = sumTotal + expRec.getTotal();
														sumPerson = sumPerson + person;
													}
													 allAmount = allAmount + sumAmount;
													 allDiscRp = allDiscRp + sumDiscRp;
													 allTotal = allTotal + sumTotal;
													 allPerson = allPerson + sumPerson;  
													 %>
                                                    <tr valign="top"> 
                                                      <td colspan="2" class="listgensell" height="33">&nbsp;&nbsp;&nbsp;SUB 
                                                        TOTAL</td>
                                                      <td width="11%" class="listgensell" align="right"><%=Formater.formatNumber(sumAmount,"#,###.#")%></td>
                                                      <td width="10%" class="listgensell">&nbsp;</td>
                                                      <td width="10%" class="listgensell" align="right"><%=Formater.formatNumber(sumDiscRp,"#,###.#")%></td>
                                                      <td width="14%" class="listgensell" align="right"><%=Formater.formatNumber(sumTotal,"#,###.#")%></td>
                                                      <td width="9%" class="listgensell" align="right"><%=sumPerson%></td>
                                                    </tr>
                                                    <tr valign="top"> 
                                                      <td colspan="2" class="listgensell">&nbsp;</td>
                                                      <td width="11%" class="listgensell" align="right">&nbsp;</td>
                                                      <td width="10%" class="listgensell">&nbsp;</td>
                                                      <td width="10%" class="listgensell" align="right">&nbsp;</td>
                                                      <td width="14%" class="listgensell" align="right">&nbsp;</td>
                                                      <td width="9%" class="listgensell" align="right">&nbsp;</td>
                                                    </tr>
                                                    <tr valign="top"> 
                                                      <td colspan="2" class="listgensell">&nbsp;&nbsp;&nbsp;GRAND 
                                                        TOTAL</td>
                                                      <td width="11%" class="listgensell" align="right"><%=Formater.formatNumber(allAmount,"#,###.#")%></td>
                                                      <td width="10%" class="listgensell">&nbsp;</td>
                                                      <td width="10%" class="listgensell" align="right"><%=Formater.formatNumber(allDiscRp,"#,###.#")%></td>
                                                      <td width="14%" class="listgensell" align="right"><%=Formater.formatNumber(allTotal,"#,###.#")%></td>
                                                      <td width="9%" class="listgensell" align="right"><%=allPerson%></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td class="comment">&nbsp;</td>
                                                <td class="comment">&nbsp;</td>
                                                <td class="comment">&nbsp;</td>
                                              </tr>
                                              <%  
											  	}else{ %>
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" class="comment">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" class="comment">No 
                                                  Recapitulation Of Medical Expense 
                                                  List available</td>
                                              </tr>
                                              <% } 
											  }catch(Exception exc){ 
											  	System.out.println(exc);
											  }%>
                                              <% if((listAllExpense != null && listAllExpense.size()>0)/*&&(privPrint)*/){%>
                                              <tr align="left" valign="top">                                                 
                                                <td colspan="3"> 
                                                  <table width="100%">
                                                    <tr> 
                                                      <td valign="top" width="18%"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                          <tr> 
                                                            <td width="15%" valign="top"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image341','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image341" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24"></a></td>
                                                            <td width="75%" valign="top" nowrap class="button">&nbsp; 
                                                              <a href="javascript:cmdPrint()" class="buttonlink">Print 
                                                              Medical Expenses 
                                                              </a></td>
                                                            <td width="20%" valign="top">&nbsp;</td>
                                                          </tr>
                                                        </table>
                                                      </td>                                                                                                          
                                                      <td valign="top" width="24%"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                          <tr> 
                                                            <td width="10%" valign="top"><a href="javascript:cmdPrintSummary()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image351','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image351" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24"></a></td>
                                                            <td width="90%" valign="top" nowrap class="button">&nbsp; 
                                                              <a href="javascript:cmdPrintSummary()" class="buttonlink">Print 
                                                              Summary Receive 
                                                              Report (D)</a></td>
                                                          </tr>
                                                        </table>
                                                      </td>                                                     
                                                      <td valign="top" width="28%"> 
                                                        <table width="95%" border="0" cellpadding="0" cellspacing="0">
                                                          <tr> 
                                                            <td width="13%" valign="top"><a href="javascript:cmdPrintDetail()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image351','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image351" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24"></a></td>
                                                            <td width="87%" valign="top" nowrap class="button">&nbsp; 
                                                              <a href="javascript:cmdPrintDetail()" class="buttonlink">Print 
                                                              Summary Receive 
                                                              Report (C) </a></td>
                                                          </tr>
                                                        </table>
                                                      </td>                                                      
                                                      <td valign="top" width="30%">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%}%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle">&nbsp;</td>
                                        </tr>                                        
                                      </table>
                                    </form>
                                    <!-- #EndEditable --> </td>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --><!-- #EndEditable --> <!-- #EndTemplate -->
</html>
