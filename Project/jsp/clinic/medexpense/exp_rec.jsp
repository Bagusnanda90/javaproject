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
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_MEDICAL, AppObjInfo.OBJ_MEDICAL_RECORD); %>
<%@ include file = "../../main/checkuser.jsp" %>
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
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidExpRecapitulate = FRMQueryString.requestLong(request, "rec_medical_expense_oid");
Date dtPeriod = FRMQueryString.requestDate(request,FrmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_PERIODE]);
int persons = FRMQueryString.requestInt(request,"persons");

if(iCommand == Command.NONE){
	dtPeriod = new Date();
	dtPeriod.setDate(1);
}
/*variable declaration*/
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlExpRecapitulate ctrlExpRecapitulate = new CtrlExpRecapitulate(request);
ControlLine ctrLine = new ControlLine();
Vector listExpRecapitulate = new Vector(1,1);

/*switch statement */
iErrCode = ctrlExpRecapitulate.action(iCommand , oidExpRecapitulate);
/* end switch*/
FrmExpRecapitulate frmExpRecapitulate = ctrlExpRecapitulate.getForm();
ExpRecapitulate expRecapitulate = ctrlExpRecapitulate.getExpRecapitulate();
msgString =  ctrlExpRecapitulate.getMessage();
Vector listAllExpense = PstExpRecapitulate.getMedicalExpense(dtPeriod);													


%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Medical Expense Recapitulation</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmexprecapitulate.rec_medical_expense_oid.value="0";
	document.frmexprecapitulate.command.value="<%=Command.ADD%>";
	document.frmexprecapitulate.prev_command.value="<%=prevCommand%>";
	document.frmexprecapitulate.action="exp_rec.jsp";
	document.frmexprecapitulate.submit();
}

function cmdAsk(oidExpRecapitulate){
	document.frmexprecapitulate.rec_medical_expense_oid.value=oidExpRecapitulate;
	document.frmexprecapitulate.command.value="<%=Command.ASK%>";
	document.frmexprecapitulate.prev_command.value="<%=prevCommand%>";
	document.frmexprecapitulate.action="exp_rec.jsp";
	document.frmexprecapitulate.submit();
}

function cmdConfirmDelete(oidExpRecapitulate){
	document.frmexprecapitulate.rec_medical_expense_oid.value=oidExpRecapitulate;
	document.frmexprecapitulate.command.value="<%=Command.DELETE%>";
	document.frmexprecapitulate.prev_command.value="<%=prevCommand%>";
	document.frmexprecapitulate.action="exp_rec.jsp";
	document.frmexprecapitulate.submit();
}
function cmdSave(){
	document.frmexprecapitulate.command.value="<%=Command.SAVE%>";
	document.frmexprecapitulate.prev_command.value="<%=prevCommand%>";
	document.frmexprecapitulate.action="exp_rec.jsp";
	document.frmexprecapitulate.submit();
	}

function cmdEdit(oidExpRecapitulate){
	document.frmexprecapitulate.rec_medical_expense_oid.value=oidExpRecapitulate;
	document.frmexprecapitulate.command.value="<%=Command.EDIT%>";
	document.frmexprecapitulate.prev_command.value="<%=prevCommand%>";
	document.frmexprecapitulate.action="exp_rec.jsp";
	document.frmexprecapitulate.submit();
	}

function cmdCancel(oidExpRecapitulate){
	document.frmexprecapitulate.rec_medical_expense_oid.value=oidExpRecapitulate;
	document.frmexprecapitulate.command.value="<%=Command.EDIT%>";
	document.frmexprecapitulate.prev_command.value="<%=prevCommand%>";
	document.frmexprecapitulate.action="exp_rec.jsp";
	document.frmexprecapitulate.submit();
}

function cmdBack(){
	document.frmexprecapitulate.command.value="<%=Command.BACK%>";
	document.frmexprecapitulate.action="exp_rec.jsp";
	document.frmexprecapitulate.submit();
	}


function cntTotal(){
	var amount = document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_AMOUNT] %>.value;
	document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_TOTAL] %>.value=amount/1;
	
	if(document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_TOTAL] %>.value=="NaN"){
		alert("Check Amount");
		document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_AMOUNT] %>.value="";
		document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_TOTAL] %>.value="";
		document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_AMOUNT] %>.focus();
	}	
}

function cntDiscount(){
	var amount = document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_AMOUNT] %>.value;
	document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_TOTAL] %>.value=amount/1;
	
	if(document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_TOTAL] %>.value=="NaN"){
		alert("Check Amount");
		document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_AMOUNT] %>.value="";
		document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_TOTAL] %>.value="";
		document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_AMOUNT] %>.focus();
	}	
	var total = document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_AMOUNT] %>.value;	
	var disPercent = document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_PERCENT] %>.value;	
	if(disPercent != 0){
		//alert(total);
		//alert(disPercent/100 + " * "+total);		
		var disRp = disPercent/100 * total;
		//alert(disRp);
		disRp = ""+disRp;
		var token = disRp.indexOf(".");
		if(token > 0)
			token = token + 3;
		else
			token = disRp.length;
		disRp = disRp.substring(0,token);			
		document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_RP] %>.value=disRp;
		if(document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_RP] %>.value=="NaN"){
			alert("Check Discount in Percent");
			document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_PERCENT] %>.value="";
			document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_RP] %>.value="";
			document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_PERCENT] %>.focus();		
		}else{
			document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_TOTAL] %>.value=parseFloat(total)- document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_RP] %>.value;
		}
	}else{
		document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_RP] %>.value=0;
	}	
}

function cmdPrint()
{			
	var month   =  document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_PERIODE]+"_mn"%>.value;		
	var year    =  document.frmexprecapitulate.<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_PERIODE]+"_yr"%>.value;	
	var prs    =  document.frmexprecapitulate.persons.value;
	//alert(prs);										
	var linkPage   = "medexpense_buffer.jsp?" +
					 "<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_PERIODE]%>_yr=" + year + "&" +
					 "<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_PERIODE]%>_mn=" + month + "&" +
					 "persons=" + prs;			 	
	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
	
}

function chgPeriode(){
	document.frmexprecapitulate.command.value="<%=Command.LIST%>";
	document.frmexprecapitulate.action="exp_rec.jsp";
	document.frmexprecapitulate.submit();
}

function ctnAvg(total){
	total = ""+total;
	total = total.substring(0,total.indexOf("."));
	//alert(total);
	var prs = document.frmexprecapitulate.persons.value;
	document.frmexprecapitulate.emp.value=prs/1;
	if(document.frmexprecapitulate.emp.value=="NaN"){
		alert("Check Total Staff");
		document.frmexprecapitulate.persons.value="";
		document.frmexprecapitulate.persons.focus();
		document.frmexprecapitulate.average.value="";
	}else{		
		var avg = ""+(parseFloat(total)/parseFloat(prs));		
		var token = avg.indexOf(".");
		if(token>0)
			token = token + 3;
		else
			token = avg.length;
			
		avg = avg.substring(0,token);		
		document.frmexprecapitulate.average.value=avg;
	}
			
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
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="rec_medical_expense_oid" value="<%=oidExpRecapitulate%>">
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
                                                  <% //out.println("expRecapitulate.getPeriode() "+expRecapitulate.getPeriode());%>
                                                  <%=	ControlDate.drawDateMY(frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_PERIODE], dtPeriod,"MMMM","formElemen", 0,-1) %> 
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
															ExpRecapitulate expRec = (ExpRecapitulate)temp.get(2);
															System.out.println("expRec.getMedicalTypeId() "+expRec.getMedicalTypeId());																												
														%>
                                                    <% if(expTypeId != medicalType.getMedExpenseTypeId()){																
																expTypeId = medicalType.getMedExpenseTypeId();																
														%>
                                                    <% if(sumTotal != 0){
															 
															%>
                                                    <tr valign="top"> 
                                                      <td colspan="2" class="listgensell" height="33"><b>&nbsp;&nbsp;&nbsp;</b>SUB 
                                                        TOTAL</td>
                                                      <td width="11%" class="listgensell" align="right" height="33"><%=Formater.formatNumber(sumAmount,"#,###")%></td>
                                                      <td width="10%" class="listgensell" height="33">&nbsp;</td>
                                                      <td width="10%" class="listgensell" align="right" height="33"><%=Formater.formatNumber(sumDiscRp,"#,###")%></td>
                                                      <td width="14%" class="listgensell" align="right" height="33"><%=Formater.formatNumber(sumTotal,"#,###")%></td>
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
                                                      <td width="7%" align="center" <%if((expRec.getOID()==oidExpRecapitulate)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><%=medicalType.getTypeCode()%></td>
                                                      <td width="39%" <%if((expRec.getOID()==oidExpRecapitulate)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><a href="javascript:cmdEdit('<%=expRec.getOID()%>')"><%=medicalType.getTypeName()%></a></td>
                                                      <td width="11%" align="right" <%if((expRec.getOID()==oidExpRecapitulate)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><%=Formater.formatNumber(expRec.getAmount(),"#,###")%></td>
                                                      <td width="10%" align="right" <%if((expRec.getOID()==oidExpRecapitulate)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><%=expRec.getDiscountInPercent()%>&nbsp;%</td>
                                                      <td width="10%" align="right" <%if((expRec.getOID()==oidExpRecapitulate)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><%=Formater.formatNumber(expRec.getDiscountInRp(),"#,###")%></td>
                                                      <td width="14%" align="right" <%if((expRec.getOID()==oidExpRecapitulate)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><%=Formater.formatNumber(expRec.getTotal(),"#,###")%></td>
                                                      <td width="9%" align="right" <%if((expRec.getOID()==oidExpRecapitulate)&&(iCommand==Command.EDIT)){%>class="tabtitlehidden"<%}else{%>class="listgensell"<%}%>><%=expRec.getPerson()%></td>
                                                    </tr>
                                                    <%  sumAmount = sumAmount + expRec.getAmount();
														sumDiscRp = sumDiscRp + expRec.getDiscountInRp();
														sumTotal = sumTotal + expRec.getTotal();
														sumPerson = sumPerson + expRec.getPerson();
													}
													 allAmount = allAmount + sumAmount;
													 allDiscRp = allDiscRp + sumDiscRp;
													 allTotal = allTotal + sumTotal;
													 allPerson = allPerson + sumPerson;  
													 %>
                                                    <tr valign="top"> 
                                                      <td colspan="2" class="listgensell" height="33">&nbsp;&nbsp;&nbsp;SUB 
                                                        TOTAL</td>
                                                      <td width="11%" class="listgensell" align="right"><%=Formater.formatNumber(sumAmount,"#,###")%></td>
                                                      <td width="10%" class="listgensell">&nbsp;</td>
                                                      <td width="10%" class="listgensell" align="right"><%=Formater.formatNumber(sumDiscRp,"#,###")%></td>
                                                      <td width="14%" class="listgensell" align="right"><%=Formater.formatNumber(sumTotal,"#,###")%></td>
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
                                                      <td width="11%" class="listgensell" align="right"><%=Formater.formatNumber(allAmount,"#,###")%></td>
                                                      <td width="10%" class="listgensell">&nbsp;</td>
                                                      <td width="10%" class="listgensell" align="right"><%=Formater.formatNumber(allDiscRp,"#,###")%></td>
                                                      <td width="14%" class="listgensell" align="right"><%=Formater.formatNumber(allTotal,"#,###")%></td>
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
                                              <tr align="left" valign="top"> 
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>Total Staff &nbsp;&nbsp; 
                                                  <%
												 /* try{
													  String tmpAvg = Formater.formatNumber(allTotal,"###");												  
													  allTotal = Float.parseFloat(tmpAvg);
													  out.println(allTotal);
												  }catch(Exception e){
												  	  //out.println(e.toString());
												  }*/												  
												  %>
                                                  <input type="text" name="persons" size="3" onKeyUp="javascript:ctnAvg(<%=allTotal%>)" value="<%=allPerson%>">
                                                  persons </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="31">&nbsp;</td>
                                                <td height="31">&nbsp;</td>
                                                <td height="31">Average medical 
                                                  expenses per employee <%=Formater.formatNumber(allTotal,"#,###")%> 
                                                  / 
                                                  <INPUT name=emp readOnly size=3
												  style="BACKGROUND-COLOR: #EAF7FF; BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; BORDER-RIGHT: 0px; BORDER-TOP: 0px;   TEXT-ALIGN: left" value="<%=allPerson%>">
                                                  = 
                                                  <INPUT name=average readOnly size=20 
												  style="BACKGROUND-COLOR: #EAF7FF; BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; BORDER-RIGHT: 0px; BORDER-TOP: 0px;  TEXT-ALIGN: left" value="<%=Formater.formatNumber((allTotal/allPerson),"#,###.00")%>">
                                                </td>
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
											  }%>
                                              <% if(iCommand == Command.NONE || iCommand == Command.SAVE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand == Command.LIST){%>
                                              <tr align="left" valign="top"> 
                                                <% if(privAdd){%>
                                                <td colspan="3"> 
                                                  <table width="90%">
                                                    <tr> 
                                                      <td valign="top" width="20%"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                          <tr> 
                                                            <td width="15%" valign="top"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image341','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image341" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24"></a></td>
                                                            <td width="75%" valign="top" nowrap class="button">&nbsp; 
                                                              <a href="javascript:cmdAdd()" class="buttonlink">Add 
                                                              New Medical Expense 
                                                              </a></td>
                                                            <td width="20%" valign="top">&nbsp;</td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                      <% }%>
                                                      <% if((listAllExpense != null && listAllExpense.size()>0)/*&&(privPrint)*/){%>
                                                      <td valign="top" width="20%"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                          <tr> 
                                                            <td width="15%" valign="top"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image351','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image351" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24"></a></td>
                                                            <td width="75%" valign="top" nowrap class="button">&nbsp; 
                                                              <a href="javascript:cmdPrint()" class="buttonlink">Print 
                                                              Medical Expense</a></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                      <%}else{%>
                                                      <td valign="top" width="20%">&nbsp;</td>
                                                      <%}%>
                                                      <td valign="top" width="60%">&nbsp;</td>
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
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="top" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmExpRecapitulate.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidExpRecapitulate==0?"Add":"Edit"%> 
                                                  Medical Expense </td>
                                              </tr>
                                              <tr> 
                                                <td height="100%" width="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="20%">&nbsp;</td>
                                                      <td width="80%" class="comment">*)Required</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="20%">Medicine 
                                                        Expense Type </td>
                                                      <td width="80%"> 
                                                        <% Vector medicineexpensetypeid_value = new Vector(1,1);
															Vector medicineexpensetypeid_key = new Vector(1,1);
															String sel_medicineexpensetypeid = ""+expRecapitulate.getMedicalTypeId();
														   	Vector listExpenseType = PstMedicalType.list(0,0,"",PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID]);
															for(int i =0;i < listExpenseType.size();i++){
																MedicalType medicalType = (MedicalType)listExpenseType.get(i);
																medicineexpensetypeid_value.add(""+medicalType.getOID());
																medicineexpensetypeid_key.add(medicalType.getTypeCode()+ " - "+medicalType.getTypeName());
															}
														   %>
                                                        <%= ControlCombo.draw(frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_MEDICAL_TYPE_ID],"elemenForm",null, sel_medicineexpensetypeid, medicineexpensetypeid_value,medicineexpensetypeid_key) %> 
                                                        * <%= frmExpRecapitulate.getErrorMsg(FrmExpRecapitulate.FRM_FIELD_MEDICAL_TYPE_ID) %> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="20%">Amount</td>
                                                      <td width="80%"> 
                                                        <input type="text" name="<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_AMOUNT] %>"  value="<%=(expRecapitulate.getAmount() >0.0f)?Formater.formatNumber(expRecapitulate.getAmount(),"###"):"" %>" class="elemenForm" onKeyUp="javascript:cntDiscount()" size="20">
                                                        * <%= frmExpRecapitulate.getErrorMsg(FrmExpRecapitulate.FRM_FIELD_AMOUNT) %> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="20%">Discount 
                                                      </td>
                                                      <td width="80%"> 
                                                        <input type="text" name="<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_PERCENT] %>"  value="<%= (expRecapitulate.getDiscountInPercent()> 0.0f)?""+expRecapitulate.getDiscountInPercent():"" %>" class="elemenForm" onKeyUp="javascript:cntDiscount()" size="5">
                                                        in % &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                        <input type="text" name="<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_DISCOUNT_IN_RP] %>"  value="<%= (expRecapitulate.getDiscountInRp()>0.0f)?Formater.formatNumber(expRecapitulate.getDiscountInRp(),"###"):"" %>" class="elemenForm" readonly="yes" size="20">
                                                        in Rp </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="20%">Total</td>
                                                      <td width="80%"> 
                                                        <input type="text" name="<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_TOTAL] %>"  value="<%= (expRecapitulate.getTotal() > 0.0f)?Formater.formatNumber(expRecapitulate.getTotal(),"###"):"" %>" class="elemenForm" readonly="yes" size="20">
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="20%">Number 
                                                        Of Person</td>
                                                      <td width="80%"> 
                                                        <input type="text" name="<%=frmExpRecapitulate.fieldNames[FrmExpRecapitulate.FRM_FIELD_PERSON] %>"  value="<%= expRecapitulate.getPerson()==0?"":Formater.formatNumber(expRecapitulate.getPerson(),"###") %>" class="elemenForm" size="5">
                                                        * <%= frmExpRecapitulate.getErrorMsg(FrmExpRecapitulate.FRM_FIELD_PERSON) %> 
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td class="command"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidExpRecapitulate+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidExpRecapitulate+"')";
									String scancel = "javascript:cmdEdit('"+oidExpRecapitulate+"')";
									ctrLine.setBackCaption("Back to List Medical Expense");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("Add New Medical Expense");
									ctrLine.setSaveCaption("Save Medical Expense");
									ctrLine.setDeleteCaption("Delete Medical Expense");
									ctrLine.setConfirmDelCaption("Delete Medical Expense");

									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
									}
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="2"> 
                                                  <div align="left"></div>
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
                                          </td>
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
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --><!-- #EndEditable --> <!-- #EndTemplate -->
</html>
