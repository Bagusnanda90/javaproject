<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_RATE);%>
<%@ include file = "../../main/checkuser.jsp" %>
<!-- JSP Block -->
<%!
public String drawList(int iCommand, FrmCurrency_Rate frmObject, Currency_Rate objEntity, Vector objectClass, long idCurrency_Rate, boolean privUpdate){
	String result = "";

	ControlList ctrlist = new ControlList();
	ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
	ctrlist.addHeader("Currency Type","15%");
	ctrlist.addHeader("Rate Company","10%");
	ctrlist.addHeader("Rate Bank","15%");
	ctrlist.addHeader("Rate Tax","10%");
	ctrlist.addHeader("Start Date","20%");
	ctrlist.addHeader("End Date","20%");
	ctrlist.addHeader("Status","10%");

	Vector lstData = ctrlist.getData();
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	//untuk mengambil status
	Vector vKeyStatus = new Vector();
    Vector vValStatus = new Vector();
    vKeyStatus.add(PstCurrency_Rate.AKTIF+"");
    vKeyStatus.add(PstCurrency_Rate.HISTORY+"");
	vValStatus.add(PstCurrency_Rate.status[PstCurrency_Rate.AKTIF]);
    vValStatus.add(PstCurrency_Rate.status[PstCurrency_Rate.HISTORY]);
	
	//untuk menampilkan currency 
	Vector curr_value = new Vector(1,1);
    Vector curr_key = new Vector(1,1);
	String orderCl = PstCurrencyType.fieldNames[PstCurrencyType.FLD_NAME]+" DESC ";
    Vector listCurr= PstCurrencyType.list(0, 0, "", orderCl);
    for (int i = 0; i < listCurr.size(); i++) {
		 CurrencyType curr = (CurrencyType) listCurr.get(i);
		 curr_key.add(curr.getName());
		 curr_value.add(String.valueOf(curr.getCode()));
    }
	
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			Currency_Rate currency_Rate = (Currency_Rate)objectClass.get(i);
			if(idCurrency_Rate == currency_Rate.getOID()){
			  index = i;
			}
			
			rowx = new Vector();
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				rowx.add(ControlCombo.drawWithStyle(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_CURR_CODE],null, ""+currency_Rate.getCurr_code(), curr_value, curr_key, "formElemen"));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_RATE_COMPANY] +"\" value=\""+currency_Rate.getRate_company()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_RATE_BANK] +"\" value=\""+currency_Rate.getRate_bank()+"\" class=\"formElemen\" size=\"10\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_RATE_TAX] +"\" value=\""+currency_Rate.getTax_rate()+"\" class=\"formElemen\" size=\"10\">");
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_START_DATE], currency_Rate.getTgl_mulai(), 1,-5, "formElemen", ""));
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_END_DATE], currency_Rate.getTgl_akhir(), 1,-5, "formElemen", ""));
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_STATUS], null, ""+currency_Rate.getStatus(),vKeyStatus,vValStatus));
			}else{
				CurrencyType objCur = new CurrencyType();
				String whereCl = PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE]+" = '"+currency_Rate.getCurr_code()+"'";
				Vector listCurren = PstCurrencyType.list(0,0,whereCl,"");
				System.out.println("currencyType::::::"+listCurren.size());
				if(listCurren.size()>0){
					objCur = (CurrencyType)listCurren.get(0);
				}
				
				Date startDate = currency_Rate.getTgl_mulai();
				Date endDate = currency_Rate.getTgl_akhir();
				
				String dateStart = startDate==null?"-":Formater.formatDate(currency_Rate.getTgl_mulai(), "dd-MM-yyyy");
				String endStart = endDate==null?"-":Formater.formatDate(currency_Rate.getTgl_akhir(), "dd-MM-yyyy");
				if (privUpdate == true){
                                    rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(currency_Rate.getOID())+"')\">"+currency_Rate.getCurr_code()+" - "+objCur.getName()+"</a>");
                                } else {
                                    rowx.add(currency_Rate.getCurr_code()+" - "+objCur.getName());
                                }
				
				rowx.add(String.valueOf(FrmCurrency_Rate.userFormatStringDecimal(currency_Rate.getRate_company())));
				rowx.add(String.valueOf(FrmCurrency_Rate.userFormatStringDecimal(currency_Rate.getRate_bank())));
				rowx.add(String.valueOf(FrmCurrency_Rate.userFormatStringDecimal(currency_Rate.getTax_rate())));
				rowx.add(dateStart);
				rowx.add(endStart);
				rowx.add(String.valueOf(PstCurrency_Rate.status[currency_Rate.getStatus()]));
			}
			lstData.add(rowx);
		}
		rowx = new Vector();

		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
			rowx.add(ControlCombo.drawWithStyle(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_CURR_CODE],null, ""+objEntity.getCurr_code(), curr_value, curr_key, "formElemen"));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_RATE_COMPANY] +"\" value=\""+objEntity.getRate_company()+"\" class=\"formElemen\" size=\"40\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_RATE_BANK] +"\" value=\""+objEntity.getRate_bank()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_RATE_TAX] +"\" value=\""+objEntity.getTax_rate()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_START_DATE], new Date(), 1,-5, "formElemen", ""));
			rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_END_DATE], new Date(), 1,-5, "formElemen", ""));
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_STATUS], null, ""+objEntity.getStatus(),vKeyStatus,vValStatus));
			
		}
		lstData.add(rowx);
		result = ctrlist.draw();
	}else{
		if(iCommand==Command.ADD){
			rowx.add(ControlCombo.drawWithStyle(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_CURR_CODE],null, ""+objEntity.getCurr_code(), curr_value, curr_key, "formElemen"));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_RATE_COMPANY] +"\" value=\""+objEntity.getRate_company()+"\" class=\"formElemen\" size=\"40\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_RATE_BANK] +"\" value=\""+objEntity.getRate_bank()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_RATE_TAX] +"\" value=\""+objEntity.getTax_rate()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_START_DATE], new Date(), 1,-5, "formElemen", ""));
			rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_END_DATE], new Date(), 1,-5, "formElemen", ""));
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmCurrency_Rate.FRM_FIELD_STATUS], null, ""+objEntity.getStatus(),vKeyStatus,vValStatus));
			lstData.add(rowx);
			result = ctrlist.draw();
		}else{
			result = "<i>No data found...</i>";
		}
	}
	return result;
}
%>
<%
// request data from jsp page
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidCurrency_Rate = FRMQueryString.requestLong(request, "hidden_id_currency_rate");

// variable declaration
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

// instantiate TaxType classes
CtrlCurrency_Rate ctrlCurrency_Rate = new CtrlCurrency_Rate(request);
ControlLine ctrLine = new ControlLine();


// action on object agama defend on command entered
iErrCode = ctrlCurrency_Rate.action(iCommand , oidCurrency_Rate);
FrmCurrency_Rate frmCurrency_Rate = ctrlCurrency_Rate.getForm();
Currency_Rate currency_Rate = ctrlCurrency_Rate.getCurrency_Rate();
msgString =  ctrlCurrency_Rate.getMessage();

// get records to display
String whereClause = "";
String orderClause = "";

int vectSize = PstCurrency_Rate.getCount(whereClause);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlCurrency_Rate.actionList(iCommand, start, vectSize, recordToGet);
}

Vector listCurrency_Rate = PstCurrency_Rate.list(start, recordToGet, whereClause , orderClause);
if(listCurrency_Rate.size()<1 && start>0){
	 if(vectSize - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listCurrency_Rate = PstCurrency_Rate.list(start, recordToGet, whereClause , orderClause);
}
%>
<%
int idx = FRMQueryString.requestInt(request, "idx");
%>
<!-- End of JSP Block -->

<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<script language="JavaScript">
function cmdAdd(){
	document.frmCurr_Rate.hidden_id_currency_rate.value="0";
	document.frmCurr_Rate.command.value="<%=Command.ADD%>";
	document.frmCurr_Rate.prev_command.value="<%=prevCommand%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
}

function cmdAsk(oidCurrency_Rate){
	document.frmCurr_Rate.hidden_id_currency_rate.value=oidCurrency_Rate;
	document.frmCurr_Rate.command.value="<%=Command.ASK%>";
	document.frmCurr_Rate.prev_command.value="<%=prevCommand%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
}

function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frmCurr_Rate.command.value="<%=Command.DELETE%>";
			document.frmCurr_Rate.action="currency_rate.jsp";
			document.frmCurr_Rate.submit();
		}
}


function cmdSave(){
	document.frmCurr_Rate.command.value="<%=Command.SAVE%>";
	document.frmCurr_Rate.prev_command.value="<%=prevCommand%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
	}

function cmdEdit(oidCurrency_Rate){
	document.frmCurr_Rate.hidden_id_currency_rate.value=oidCurrency_Rate;
	document.frmCurr_Rate.command.value="<%=Command.EDIT%>";
	document.frmCurr_Rate.prev_command.value="<%=prevCommand%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
	}

function cmdCancel(oidCurrency_Rate){
	document.frmCurr_Rate.hidden_id_currency_rate.value=oidCurrency_Rate;
	document.frmCurr_Rate.command.value="<%=Command.EDIT%>";
	document.frmCurr_Rate.prev_command.value="<%=prevCommand%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
}

function cmdBack(){
	document.frmCurr_Rate.command.value="<%=Command.BACK%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
	}

function cmdListFirst(){
	document.frmCurr_Rate.command.value="<%=Command.FIRST%>";
	document.frmCurr_Rate.prev_command.value="<%=Command.FIRST%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
}

function cmdListPrev(){
	document.frmCurr_Rate.command.value="<%=Command.PREV%>";
	document.frmCurr_Rate.prev_command.value="<%=Command.PREV%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
	}

function cmdListNext(){
	document.frmCurr_Rate.command.value="<%=Command.NEXT%>";
	document.frmCurr_Rate.prev_command.value="<%=Command.NEXT%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
}

function cmdListLast(){
	document.frmCurr_Rate.command.value="<%=Command.LAST%>";
	document.frmCurr_Rate.prev_command.value="<%=Command.LAST%>";
	document.frmCurr_Rate.action="currency_rate.jsp";
	document.frmCurr_Rate.submit();
}

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
	
	function showObjectForMenu(){
        
    }
</SCRIPT>
<style type="text/css">
    .tblStyle {border-collapse: collapse;}
    .tblStyle td {padding: 7px 9px; border: 1px solid #CCC; font-size: 12px;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
    .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
    body {color:#373737;}
    #menu_utama {padding: 9px 14px; border-bottom: 1px solid #CCC; font-size: 14px; background-color: #F3F3F3;}
    #menu_title {color:#0099FF; font-size: 14px;}

    .title_part {
        font-size: 12px;
        color:#0099FF; 
        background-color: #F7F7F7; 
        border-left: 1px solid #0099FF; 
        padding: 7px 9px;
    }

    body {background-color: #EEE; margin: 0;}
    .header {

    }
    .content-main {
        padding: 21px 32px;
        margin: 0px 23px 59px 23px;
    }
    .content-info {
        padding: 21px;
        border-bottom: 1px solid #E5E5E5;
    }
    .content-title {
        padding: 21px;
        border-bottom: 1px solid #E5E5E5;
        margin-bottom: 5px;
    }
    #title-large {
        color: #575757;
        font-size: 16px;
        font-weight: bold;
    }
    #title-small {
        color:#797979;
        font-size: 11px;
    }
    .content {
        padding: 21px;
    }
    
    .btn {
        background-color: #00a1ec;
        border-radius: 3px;
        font-family: Arial;
        color: #EEE;
        font-size: 12px;
        padding: 7px 11px 7px 11px;
        text-decoration: none;
    }

    .btn:hover {
        color: #FFF;
        background-color: #007fba;
        text-decoration: none;
    }

    .btn-small {
        font-family: sans-serif;
        text-decoration: none;
        padding: 5px 7px; 
        background-color: #676767; color: #F5F5F5; 
        font-size: 11px; cursor: pointer;
        border-radius: 3px;
    }
    .btn-small:hover { background-color: #474747; color: #FFF;}

    .btn-small-1 {
        font-family: sans-serif;
        text-decoration: none;
        padding: 5px 7px; 
        background-color: #EEE; color: #575757; 
        font-size: 11px; cursor: pointer;
        border-radius: 3px;
    }
    .btn-small-1:hover { background-color: #DDD; color: #474747;}

    .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
    .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
    #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}

    .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
    .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }

    #caption {
        font-size: 12px;
        color: #474747;
        font-weight: bold;
        padding: 2px 0px 3px 0px;
    }
    #divinput {
        margin-bottom: 5px;
        padding-bottom: 5px;
    }

    #div_item_sch {
        background-color: #EEE;
        color: #575757;
        padding: 5px 7px;
    }

    #record_count{
        font-size: 12px;
        font-weight: bold;
        padding-bottom: 9px;
    }
    #box-form {
        background-color: #EEE; 
        border-radius: 5px;
    }
    .form-style {
        font-size: 12px;
        color: #575757;
        border: 1px solid #DDD;
        border-radius: 5px;
    }
    .form-title {
        padding: 11px 21px;
        text-align: left;
        border-bottom: 1px solid #DDD;
        background-color: #FFF;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        font-weight: bold;
    }
    .form-content {
        text-align: left;
        padding: 21px;
        background-color: #DDD;
    }
    .form-footer {
        border-top: 1px solid #DDD;
        padding: 11px 21px;
        background-color: #FFF;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
    }
    #confirm {
        padding: 13px 17px;
        background-color: #FF6666;
        color: #FFF;
        border-radius: 3px;
        font-size: 12px;
        font-weight: bold;
        visibility: hidden;
    }
    #btn-confirm {
        padding: 4px 9px; border-radius: 3px;
        background-color: #CF5353; color: #FFF; 
        font-size: 11px; cursor: pointer;
    }
    .footer-page {
        font-size: 12px;
    }
</style>
</head>
<div class="header">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
        <%@include file="../../styletemplate/template_header.jsp" %>
        <%} else {%>
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
        <%}%>
        </table>
    </div>
    <div id="menu_utama">
        <span id="menu_title">Payroll <strong style="color:#333;"> / </strong>Currency Rate</span>
    </div>
    <div class="content-main">
        <form name="frmCurr_Rate" method="post" action="">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <input type="hidden" name="vectSize" value="<%=vectSize%>">
            <input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="prev_command" value="<%=prevCommand%>">
            <input type="hidden" name="hidden_id_currency_rate" value="<%=oidCurrency_Rate%>">
            <div>&nbsp;</div>
                <%
                if (listCurrency_Rate != null && listCurrency_Rate.size() > 0) {
                %>
                <%=drawList(iCommand, frmCurrency_Rate, currency_Rate, listCurrency_Rate, oidCurrency_Rate, privUpdate)%>
                <%
                } else {
                            
                }
                %>
             <div>&nbsp;</div>

             <div class="command"> 
                 <%
                     int cmd = 0;
                     if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                             || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                         cmd = iCommand;
                     } else {
                         if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                             cmd = Command.FIRST;
                         } else {
                             if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCurrency_Rate == 0)) {
                                 cmd = PstCurrency_Rate.findLimitCommand(start, recordToGet, vectSize);
                             } else {
                                 cmd = prevCommand;
                             }
                         }
                     }
                 %>
                 <% ctrLine.setLocationImg(approot + "/images");
                     ctrLine.initDefault();
                 %>
                 <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
             </div>
	<div>&nbsp;</div>								  
	<%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmCurrency_Rate.errorSize()<1)){
            if (privAdd == true){
        %>
              <a href="javascript:cmdAdd()" style="color:#FFF;" class="btn">Add New Currency Rate</a>
        <% 
            } 
        }
        %>

        <%
           if((iCommand == Command.ADD || iCommand == Command.EDIT)){
        %>
          
            <a href="javascript:cmdSave()" class="btn" style="color:#FFF;">Save Setup Currency Rate</a></td>
          
            <a href="javascript:cmdConfirmDelete()" class="btn" style="color:#FFF;">Delete Currency</a> </td>
        
            <a href="javascript:cmdBack()" class="btn" style="color:#FFF;">Back to List Currency Rate</a>
       <%}%>


        </form>
    </div>
    <div class="footer-page">
        <table>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom"><%@include file="../../footer.jsp" %></td>
            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
            </tr>
            <%}%>
        </table>
    </div>
      
</body>
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
