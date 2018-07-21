
<% 
/* 
 * Page Name  		:  medconsump.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.qdep.db.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.system.*" %>
<!--package harisma -->

<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_MEDICINE, AppObjInfo.OBJ_MEDICINE_CONSUMPTION); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

%>
<!-- Jsp Block -->
<%! 

	public String drawList(int iCommand,FrmMedicineConsumption frmObject, Vector objectClass,  long medicineConsumptionId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("99%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setCellSpacing("1");		
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.dataFormat("No","5%","2","0","center","center");
		ctrlist.dataFormat("Medicine","25%","2","0","center","left");
		ctrlist.dataFormat("Last Month","5%","2","0","center","right");
		ctrlist.dataFormat("Purchase This Month","10%","2","0","center","right");
		ctrlist.dataFormat("Stock This Month","7%","2","0","center","right");
		ctrlist.dataFormat("Consumption This Month","18%","0","2","center","");
		ctrlist.dataFormat("Consump","8%","0","0","center","right");
		ctrlist.dataFormat("Unit Price","10%","0","0","center","right");
		ctrlist.dataFormat("Total","10%","2","0","center","right");
		ctrlist.dataFormat("Close Inventory","5%","2","0","center","right");
		ctrlist.dataFormat("Close Amount","10%","2","0","center","right");		

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();		
		double total = 0;
		double closeAmount = 0;
		
		for (int i = 0; i < objectClass.size(); i++) {		
			 Vector vector = (Vector)objectClass.get(i);
			 Medicine medicine = (Medicine)vector.get(0);			 
			 MedicineConsumption medicineConsumption = (MedicineConsumption)vector.get(1);
			 total = total + medicineConsumption.getTotalConsump();
			 closeAmount = closeAmount + medicineConsumption.getCloseAmount();
			 rowx = new Vector();			
			 //0					
			 rowx.add("<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmMedicineConsumption.FRM_FIELD_MEDICINE_CONSUMPTION_ID]+"\" value=\""+medicineConsumption.getOID()+"\"><b>"+(i+1)+"</b>");
			 //1
			 rowx.add("<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmMedicineConsumption.FRM_FIELD_MEDICINE_ID]+"\" value=\""+medicine.getOID()+"\"><b>"+medicine.getName()+"</b>");
			 //2
			 rowx.add("<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmMedicineConsumption.FRM_FIELD_LAST_MONTH]+"\" value=\""+medicineConsumption.getLastMonth()+"\"><input type=\"text\" name=\"last_month\" value=\""+medicineConsumption.getLastMonth()+"\" class=\"transElement\"  size=\"7\" disabled=\"true\">");
			 //3			
			 rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicineConsumption.FRM_FIELD_PURCHASE_THIS_MONTH] +"\" value=\""+medicineConsumption.getPurchaseThisMonth()+"\" class=\"elemenForm\" size=\"7\" onKeyUp=\"javascript:sumStock('"+i+"')\">");			 
			 //4			 	
			 rowx.add("<input type=\"text\" name=\"stock_this_month\" value=\""+medicineConsumption.getStockThisMonth()+"\" class=\"transElement\" disabled=\"true\" size=\"7\">");			 			 
			 //5				
			 rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicineConsumption.FRM_FIELD_CONSUMP_THIS_MONTH] +"\" value=\""+medicineConsumption.getConsumpThisMonth()+"\" class=\"elemenForm\" size=\"7\" onKeyUp=\"javascript:sumConsum('"+i+"')\">");
			 
			 //6
			 rowx.add("<input type=\"hidden\" name=\"med_price\" value=\""+medicine.getUnitPrice()+"\"><input type=\"text\" name=\"unit_price\" value=\""+Formater.formatNumber(medicine.getUnitPrice(),"#.##")+"\" class=\"transElement\" size=\"10\" disabled=\"true\">");
			 
			 //7				
			 rowx.add("<input type=\"text\" name=\"total_consump\" value=\""+Formater.formatNumber(medicineConsumption.getTotalConsump(),"#.##")+"\" class=\"transElement\" size=\"10\" disabled=\"true\">");
			 //8			 			 
			 rowx.add("<input type=\"text\" name=\"close_inventory\" value=\""+medicineConsumption.getCloseInventory()+"\" class=\"transElement\" size=\"7\" disabled=\"true\">");
			 //9			 
			 rowx.add("<input type=\"text\" name=\"close_amount\" value=\""+Formater.formatNumber(medicineConsumption.getCloseAmount(),"##.##")+"\" class=\"transElement\" size=\"15\" disabled=\"true\">");
			 
			 lstData.add(rowx);
		}
		
		if(total!=0){
			rowx = new Vector();
			rowx.add("");
			rowx.add("<b>TOTAL</b>");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");		
			rowx.add("<b>"+Formater.formatNumber(total,"#,###")+"</b>");
			rowx.add("");
			rowx.add("<b>"+Formater.formatNumber(closeAmount,"#,###")+"</b>");
			lstData.add(rowx);
		}

		return ctrlist.drawMeList();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
Date dtPeriod = FRMQueryString.requestDate(request, FrmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MONTH]);
if(iCommand == Command.NONE)
	dtPeriod = new Date();
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";
boolean isConsumption = false;
//
CtrlMedicineConsumption ctrlMedicineConsumption = new CtrlMedicineConsumption(request);
ControlLine ctrLine = new ControlLine();
Vector listMedicineConsumption = new Vector(1,1);
//
if(iCommand == Command.SAVE){
	String[] strMedConsumpId = request.getParameterValues(FrmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MEDICINE_CONSUMPTION_ID]); 
	String[] strMedicineId = request.getParameterValues(FrmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MEDICINE_ID]); 
	String[] strLastMonth =  request.getParameterValues(FrmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_LAST_MONTH]); 
	String[] strPurchase = request.getParameterValues(FrmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_PURCHASE_THIS_MONTH]); 
	String[] strUnitPrice = request.getParameterValues("med_price"); 	 		 			
	String[] strConsump = request.getParameterValues(FrmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_CONSUMP_THIS_MONTH]);	 	 		 		
	for(int i = 0;i < strMedicineId.length;i++){
		MedicineConsumption medicineConsumption = new MedicineConsumption();
		//
		long medConsumpId = 0;
		long medicineId = 0;
		int lastMonth = 0;
		int purchase = 0;
		double unitPrice = 0;
		int stock = 0;
		int consump = 0;
		double totConsump = 0;
		int closeInv = 0;
		double closeAmount = 0;
		try {
		 	medConsumpId = Long.parseLong(strMedConsumpId[i]);			
		 	medicineId = Long.parseLong(strMedicineId[i]);
		    lastMonth = Integer.parseInt(strLastMonth[i]);
			purchase = Integer.parseInt(strPurchase[i]);
			stock = lastMonth + purchase;
			unitPrice = Float.parseFloat(strUnitPrice[i]);
			consump = Integer.parseInt(strConsump[i]);
			totConsump = consump * unitPrice;
			closeInv = stock - consump;
			closeAmount = closeInv * unitPrice;
			medicineConsumption.setOID(medConsumpId);
			medicineConsumption.setMedicineId(medicineId);
			medicineConsumption.setMonth(dtPeriod);
			medicineConsumption.setLastMonth(lastMonth);
			medicineConsumption.setPurchaseThisMonth(purchase);
			medicineConsumption.setStockThisMonth(stock);
			medicineConsumption.setConsumpThisMonth(consump);
			medicineConsumption.setTotalConsump(totConsump);
			medicineConsumption.setCloseInventory(closeInv);
			medicineConsumption.setCloseAmount(closeAmount);			
			if(medicineConsumption.getOID()==0){
				try{
					long oid = PstMedicineConsumption.insertExc(medicineConsumption);
					msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);										
					isConsumption = true;
				}catch(DBException dbexc){
					isConsumption = false;	
					msgString = ctrlMedicineConsumption.getSystemMessage(dbexc.getErrorCode());
					iErrCode = ctrlMedicineConsumption.getControlMsgId(dbexc.getErrorCode());
				}catch (Exception exc){
					isConsumption = false;
					msgString = ctrlMedicineConsumption.getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					iErrCode = ctrlMedicineConsumption.getControlMsgId(I_DBExceptionInfo.UNKNOWN);
				}

			}else{
				try {
					long oid = PstMedicineConsumption.updateExc(medicineConsumption);
					msgString = FRMMessage.getMessage(FRMMessage.MSG_UPDATED);					
					isConsumption = true;
				}catch (DBException dbexc){
					isConsumption = false;
					iErrCode = dbexc.getErrorCode();
					msgString = ctrlMedicineConsumption.getSystemMessage(iErrCode);
				}catch (Exception exc){
					isConsumption = false;
					msgString = ctrlMedicineConsumption.getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
				}

			}
			
		}catch(Exception exc){
			
		}	
		
	}

}

/*switch statement */
iErrCode = ctrlMedicineConsumption.action(iCommand , dtPeriod);
listMedicineConsumption = ctrlMedicineConsumption.getListMedicine();
if(iCommand == Command.SAVE){
	listMedicineConsumption = PstMedicineConsumption.listMedConsumpt(dtPeriod);
}
/* end switch*/
FrmMedicineConsumption frmMedicineConsumption = ctrlMedicineConsumption.getForm();
MedicineConsumption medicineConsumption = ctrlMedicineConsumption.getMedicineConsumption();
if(iCommand == Command.EDIT || iCommand == Command.ASK)
	msgString =  ctrlMedicineConsumption.getMessage();
	
if(iCommand == Command.EDIT)	
	isConsumption = ctrlMedicineConsumption.isConsumption();
	

%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Medicine Consumption</title>
<script language="JavaScript">
function sumStock(idx){	
  	var indeks = parseInt(idx);
	var oLastMonth = document.all.item("last_month");
	var oPurchase = document.all.item("<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_PURCHASE_THIS_MONTH]%>");		
	document.frMedCons.stock_this_month[indeks].value = parseInt(oPurchase[indeks].value) + parseInt(oLastMonth[indeks].value);
	  if (document.frMedCons.stock_this_month[indeks].value=="NaN")
		{
		   alert("Check Purchase This Month");
		   document.frMedCons.stock_this_month[indeks].value="";
		   document.frMedCons.<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_PURCHASE_THIS_MONTH]%>[indeks].value="";  
		   document.frMedCons.<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_PURCHASE_THIS_MONTH]%>[indeks].focus();
		}

}

function sumConsum(idx){
	var indeks = parseInt(idx);	
	var totConsump = document.frMedCons.<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_CONSUMP_THIS_MONTH]%>[indeks].value * document.frMedCons.unit_price[indeks].value;
	document.frMedCons.total_consump[indeks].value=totConsump;
	if((document.frMedCons.total_consump[indeks].value =="NaN")||(parseInt(document.frMedCons.stock_this_month[indeks].value) < parseInt(document.frMedCons.<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_CONSUMP_THIS_MONTH]%>[indeks].value))){
		alert("Check Consumption This Month");
		document.frMedCons.total_consump[indeks].value="0.0";
		document.frMedCons.<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_CONSUMP_THIS_MONTH]%>[indeks].value="";
		document.frMedCons.<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_CONSUMP_THIS_MONTH]%>[indeks].focus();
	}else{		
		document.frMedCons.total_consump[indeks].value=parseFloat(totConsump);		
		var residu = document.frMedCons.stock_this_month[indeks].value - document.frMedCons.<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_CONSUMP_THIS_MONTH]%>[indeks].value;
		document.frMedCons.close_inventory[indeks].value=residu;		
		document.frMedCons.close_amount[indeks].value=residu * document.frMedCons.unit_price[indeks].value ;
        }
	
}

function cmdPrint()
{			
	var month = document.frMedCons.<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MONTH]+"_mn"%>.value;		
	var year    = document.frMedCons.<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MONTH]+"_yr"%>.value;										
	var linkPage   = "medconsump_buffer.jsp?" +
					 "<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MONTH]%>_yr=" + year + "&" +
					 "<%=frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MONTH]%>_mn=" + month + "&" ;				 	
	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
	
}


function cmdAdd(){
	document.frMedCons.command.value="<%=Command.ADD%>";
	document.frMedCons.action="medconsump.jsp";
	document.frMedCons.submit();
}

function cmdAsk(){
	document.frMedCons.command.value="<%=Command.ASK%>";
	document.frMedCons.action="medconsump.jsp";
	document.frMedCons.submit();
}

function cmdConfirmDelete(){
	document.frMedCons.command.value="<%=Command.DELETE%>";
	document.frMedCons.action="medconsump.jsp";
	document.frMedCons.submit();
}

function cmdSave(){
	document.frMedCons.command.value="<%=Command.SAVE%>";
	document.frMedCons.action="medconsump.jsp";
	document.frMedCons.submit();
}

function cmdEdit(){
	document.frMedCons.command.value="<%=Command.EDIT%>";
	document.frMedCons.action="medconsump.jsp";
	document.frMedCons.submit();
}

function cmdCancel(){
	document.frMedCons.command.value="<%=Command.EDIT%>";
	document.frMedCons.action="medconsump.jsp";
	document.frMedCons.submit();
}

function cmdBack(){
	document.frMedCons.command.value="<%=Command.BACK%>";
	document.frMedCons.action="medconsump.jsp";
	document.frMedCons.submit();
}
function cmdList(){
	document.frMedCons.command.value="<%=Command.EDIT%>";
	document.frMedCons.action="medconsump.jsp";
	document.frMedCons.submit();	
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
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg','<%=approot%>/images/BtnEditOn.jpg','<%=approot%>/images/BtnDelOn.jpg','<%=approot%>/images/BtnBackOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->Clinic 
                  &gt; Medicine Consumption<!-- #EndEditable --> 
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
                                    <form name="frMedCons" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">									                                                                             
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3"> 
                                                  <div align="right"></div>
                                                </td>
                                              </tr>											  
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="7%">&nbsp;&nbsp;Month 
                                                  Of </td>
                                                <td height="8" valign="middle" width="60%"><%=ControlDate.drawDateMY(frmMedicineConsumption.fieldNames[FrmMedicineConsumption.FRM_FIELD_MONTH],dtPeriod==null?new Date():dtPeriod,"MMMM",
        												"elemenForm",0,-1)%>&nbsp;</td>
                                                <td height="8" valign="middle" width="33%">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="2"> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4" height="23"><img src="<%=approot%>/images/spacer.gif" width="20" height="8"></td>
                                                      <td width="24" height="23"><a href="javascript:cmdList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Get List"></a></td>
                                                      <td width="6" height="23"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="23" valign="middle" colspan="3" width="951"><a href="javascript:cmdList()" class="command">Get 
                                                        List Medicine Consumption</a> 
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td height="8" valign="middle" width="33%">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;</td>
                                              </tr>
                                              <%
											  if(listMedicineConsumption != null && listMedicineConsumption.size()>0){
												try{												
												%>
                                              <% if(isConsumption){%>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;List 
                                                  Of Medicine Consumption </td>
                                              </tr>
                                              <%}else{%>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle"> 
                                                  <div align="left">
                                                    Medicine Consumption for 
                                                      <%=Formater.formatDate(dtPeriod,"MMMM yyyy")%> 
                                                      not available, Please insert 
                                                      these data !
                                                  </div></td>
                                              </tr>
                                              <%}%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(iCommand,frmMedicineConsumption, listMedicineConsumption,0)%> 
                                                </td>
                                              </tr>
                                              <% 
												  }catch(Exception exc){ 													
												  }
											  }%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="7%">&nbsp;</td>
                                          <td height="8" colspan="2" width="93%">&nbsp; 
                                          </td>
                                        </tr>
                                        <%if(iCommand == Command.EDIT && msgString.length()>0){%>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="errfont"><i><img src="<%=approot%>/images/spacer.gif" width="20" height="8"><%=msgString%></i></td>
                                        </tr>
                                        <%}else{%>
                                        <% if(iCommand == Command.ASK && msgString.length()>0 && privDelete){%>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="errfont"><i><img src="<%=approot%>/images/spacer.gif" width="20" height="8"><%=msgString%></i></td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="errfont"> 
                                            <table width="90%">
                                              <tr> 
                                                <td valign="top" width="20%"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td width="15%" valign="top"><a href="javascript:cmdEdit()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image34','','<%=approot%>/images/BtnEditOn.jpg',1)"><img name="Image34" border="0" src="<%=approot%>/images/BtnEdit.jpg" width="24" height="24"></a></td>
                                                      <td width="75%" valign="top" nowrap class="button">&nbsp; 
                                                        <a href="javascript:cmdEdit()" class="buttonlink">Cancel</a></td>
                                                      <td width="20%" valign="top">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td valign="top" width="20%"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td width="15%" valign="top"><a href="javascript:cmdConfirmDelete()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image35','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image35" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24"></a></td>
                                                      <td width="75%" valign="top" nowrap class="button">&nbsp; 
                                                        <a href="javascript:cmdConfirmDelete()" class="buttonlink">Yes 
                                                        Delete Consumption</a></td>
                                                      <td width="20%" valign="top">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td valign="top" width="60%">&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}else{%>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command"> 
                                            <%
												ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												ctrLine.setTableWidth("90%");
												String scomDel = "javascript:cmdAsk()";
												String sconDelCom = "javascript:cmdConfirmDelete()";
												String scancel = "javascript:cmdEdit()";
												ctrLine.setBackCaption("Back to Get List Consumption");
												ctrLine.setAddCaption("");
												ctrLine.setCommandStyle("buttonlink");
												ctrLine.setSaveCaption("Save Consumption");
												ctrLine.setDeleteCaption("Delete Consumption");
												ctrLine.setConfirmDelCaption("Yes Delete Consumption");
			
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
												if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
													ctrLine.setBackCaption("");	
												}
									
											%>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                          </td>
                                        </tr>
										<%}
										}%>
										<% if(isConsumption){%>
                                        <tr align="left" valign="top" >
                                          <td colspan="3" class="command">
                                            <table width="90%">
                                              <tr><% if(privPrint){%> 
                                                <td valign="top" width="20%"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td width="15%" valign="top"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image341','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image341" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24"></a></td>
                                                      <td width="75%" valign="top" nowrap class="button">&nbsp; 
                                                        <a href="javascript:cmdPrint()" class="buttonlink">Print 
                                                        Consumption </a></td>
                                                      <td width="20%" valign="top">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td><%}%><% if(iCommand == Command.SAVE){%>
                                                <td valign="top" width="20%"> 												
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td width="15%" valign="top"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image351','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image351" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24"></a></td>
                                                      <td width="75%" valign="top" nowrap class="button">&nbsp; 
                                                        <a href="javascript:cmdBack()" class="buttonlink">Back 
                                                        to Get Consumption</a></td>                                                      
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
                                    </form>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --><!-- #EndEditable -->
<!-- #EndTemplate --></html>
<%
}else{
%>    
    <script language="javascript">
              window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
    </script>             
<%
}
%>
