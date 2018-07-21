<%@page import="com.dimata.harisma.entity.payroll.PstCurrencyType"%>
<%@page import="com.dimata.harisma.entity.payroll.CurrencyType"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.form.payroll.FrmPayPeriod"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlPayPeriod"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_PERIOD);%>
<%@ include file = "../../main/checkuser.jsp" %>
<!-- JSP Block -->
<%!

public String drawList(int iCommand, Vector objectClass, FrmPayPeriod frmObject, PayPeriod objEntity, long payPeriodId,CurrencyType currencyType, boolean privUpdate)
{
	
    ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        
	ctrlist.addHeader("Periode","");
	ctrlist.addHeader("Work Days","");
	ctrlist.addHeader("Pay Slip Date","");
	ctrlist.addHeader("Start/End Period","");
	ctrlist.addHeader("UMR","");
    ctrlist.addHeader("Pay Process/Date/By ","");
	ctrlist.addHeader("Pay Process Close Tax/Date/By ","");
	ctrlist.addHeader("Tax is Paid ","");

	Vector lstData = ctrlist.getData();
	ctrlist.reset();
	Vector rowx = new Vector();
	int index = -1;
	
	
	//untuk mengambil proses Gaji
	Vector vKeyProsesGj = new Vector();
    Vector vValProsesGj = new Vector();
    vKeyProsesGj.add(PstPayPeriod.SUDAH+"");
    vKeyProsesGj.add(PstPayPeriod.BELUM+"");
    vValProsesGj.add(PstPayPeriod.prosesGaji[PstPayPeriod.SUDAH]);
    vValProsesGj.add(PstPayPeriod.prosesGaji[PstPayPeriod.BELUM]);
	
	//untuk ambil disetor ke pajak ato tidak
	Vector vKeyPajakSetor = new Vector();
    Vector vValPajakSetor = new Vector();
    vKeyPajakSetor.add(PstPayPeriod.PAJAK_DISETOR+"");
    vValPajakSetor.add("");
	
	ControlCheckBox cb = new ControlCheckBox();
	String frmCurrency = "#,###";
                String mataUang="Rp";
                if(currencyType!=null && currencyType.getCode()!=null && currencyType.getFormatCurrency()!=null){
                    frmCurrency = currencyType.getFormatCurrency();
                    mataUang = currencyType.getCode();
                }
	//System.out.println("objectClass"+objectClass.size());
	if(objectClass!=null && objectClass.size()>0){
		for (int i = 0; i < objectClass.size(); i++) {
			PayPeriod payPeriode = (PayPeriod)objectClass.get(i);
			 if(payPeriodId == payPeriode.getOID())
				index = i;
			//FrmKpPns.userFormatStringDecimal
			rowx = new Vector();
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PERIOD] +"\" value=\""+payPeriode.getPeriod()+"\" class=\"formElemen\" size=\"20\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_WORK_DAYS] +"\" value=\""+payPeriode.getWorkDays()+"\" class=\"formElemen\" size=\"20\">");
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_SLIP_DATE], payPeriode.getPaySlipDate(), 1,-5, "formElemen", ""));
                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_START_DATE], payPeriode.getStartDate(),1,-5, "formElemen", "")+"/"+ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_END_DATE], payPeriode.getEndDate(), 1,-5, "formElemen", ""));
                rowx.add(mataUang +" <input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_MIN_REG_WAGE] +"\" value=\""+FrmPayPeriod.userFormatStringDecimal(payPeriode.getMinRegWage())+"\" class=\"formElemen\" size=\"4\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROCESS], null, ""+payPeriode.getPayProsess(),vKeyProsesGj,vValProsesGj)+"/"+ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_DATE] , payPeriode.getPayProcDate(), 1,-5, "formElemen", "")+"/<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_BY] +"\" value=\""+payPeriode.getPayProcBy()+"\" class=\"formElemen\" size=\"4\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROCESS_CLOSE], null, ""+payPeriode.getPayProcessClose(),vKeyProsesGj,vValProsesGj)+"/"+ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_DATE_CLOSE] , payPeriode.getPayProcDateClose(), 1,-5, "formElemen", "")+"/<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_BY_CLOSE] +"\" value=\""+payPeriode.getPayProcByClose()+"\" class=\"formElemen\" size=\"4\">");
				Vector vCheck = new Vector();
                vCheck.add(payPeriode.getTaxIsPaid()+"");
                rowx.add(cb.draw(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_TAX_IS_PAID],vKeyPajakSetor,vValPajakSetor,vCheck));
			}else{
				
				Date startDate = payPeriode.getStartDate();
				Date endDate = payPeriode.getEndDate();
				Date payProcDate = payPeriode.getPayProcDate();
				Date payProcDateClose = payPeriode.getPayProcDateClose();
				
				String dateStart = startDate==null?"-":Formater.formatDate(payPeriode.getStartDate(), "dd-MM-yyyy");
				String endStart = endDate==null?"-":Formater.formatDate(payPeriode.getEndDate(), "dd-MM-yyyy");
				String procPayDate = payProcDate==null?"-":Formater.formatDate(payPeriode.getPayProcDate(), "dd-MM-yyyy");
				String procPayDateClose = payProcDateClose==null?"-":Formater.formatDate(payPeriode.getPayProcDateClose(), "dd-MM-yyyy");
				
				if(payPeriode.getTaxIsPaid()!=PstPayPeriod.PAJAK_DISETOR){
                                    if (privUpdate == true){
                                        rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(payPeriode.getOID())+"')\">"+payPeriode.getPeriod()+"</a>");
                                    } else {
                                        rowx.add(payPeriode.getPeriod());
                                    }
                                    
                                } else
                                    rowx.add(payPeriode.getPeriod());
                rowx.add(String.valueOf(payPeriode.getWorkDays()));
                rowx.add(payPeriode.getPaySlipDate()==null?"":Formater.formatDate(payPeriode.getPaySlipDate(), "dd-MM-yyyy"));
                rowx.add(dateStart+"/"+endStart);
                rowx.add(mataUang +" "+String.valueOf(FrmPayPeriod.userFormatStringDecimal(payPeriode.getMinRegWage())));
                rowx.add(String.valueOf(PstPayPeriod.prosesGaji[payPeriode.getPayProsess()])+"/"+procPayDate+"/"+payPeriode.getPayProcBy());
                rowx.add(String.valueOf(PstPayPeriod.prosesGaji[payPeriode.getPayProcessClose()])+"/"+procPayDateClose+"/"+payPeriode.getPayProcByClose());
				rowx.add(String.valueOf(PstPayPeriod.pajakNames[payPeriode.getTaxIsPaid()]));
			}
			lstData.add(rowx);	
		}
			rowx = new Vector();
			
			if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PERIOD] +"\" value=\""+objEntity.getPeriod()+"\" class=\"formElemen\" size=\"20\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_WORK_DAYS] +"\" value=\""+objEntity.getWorkDays()+"\" class=\"formElemen\" size=\"20\">");
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_SLIP_DATE] , new Date(), 1,-5, "formElemen", ""));
                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_START_DATE], new Date(),1,-5, "formElemen", "")+"/"+ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_END_DATE], new Date(), 1,-5, "formElemen", ""));
                rowx.add(mataUang + " <input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_MIN_REG_WAGE] +"\" value=\""+FrmPayPeriod.userFormatStringDecimal(objEntity.getMinRegWage())+"\" class=\"formElemen\" size=\"4\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROCESS], null, ""+2/*objEntity.getPayProsess()*/,vKeyProsesGj,vValProsesGj)+"/"+ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_DATE] , new Date(), 1,-5, "formElemen", "")+"/<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_BY] +"\" value=\""+objEntity.getPayProcBy()+"\" class=\"formElemen\" size=\"4\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROCESS_CLOSE], null, ""+objEntity.getPayProcessClose(),vKeyProsesGj,vValProsesGj)+"/"+ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_DATE_CLOSE] , new Date(), 1,-5, "formElemen", "")+"/<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_BY_CLOSE] +"\" value=\""+objEntity.getPayProcByClose()+"\" class=\"formElemen\" size=\"4\">");
				Vector vCheck = new Vector();
                vCheck.add(objEntity.getTaxIsPaid()+"");
                rowx.add(cb.draw(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_TAX_IS_PAID],vKeyPajakSetor,vValPajakSetor,vCheck));
				
			}
			lstData.add(rowx);
			
	}else{
		if(iCommand==Command.ADD){
                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PERIOD] +"\" value=\""+objEntity.getPeriod()+"\" class=\"formElemen\" size=\"20\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_WORK_DAYS] +"\" value=\""+objEntity.getWorkDays()+"\" class=\"formElemen\" size=\"20\">");
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_SLIP_DATE] , new Date(), 1,-5, "formElemen", ""));
                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_START_DATE], new Date(),1,-5, "formElemen", "")+"/"+ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_END_DATE], new Date(), 1,-5, "formElemen", ""));
                rowx.add( mataUang +" <input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_MIN_REG_WAGE] +"\" value=\""+FrmPayPeriod.userFormatStringDecimal(objEntity.getMinRegWage())+"\" class=\"formElemen\" size=\"4\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROCESS], null, ""+2/*objEntity.getPayProsess()*/,vKeyProsesGj,vValProsesGj)+"/"+ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_DATE] , new Date(), 1,-5, "formElemen", "")+"/<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_BY] +"\" value=\""+objEntity.getPayProcBy()+"\" class=\"formElemen\" size=\"4\">");
                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROCESS_CLOSE], null, ""+objEntity.getPayProcessClose(),vKeyProsesGj,vValProsesGj)+"/"+ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_DATE_CLOSE] , new Date(), 1,-5, "formElemen", "")+"/<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_PAY_PROC_BY_CLOSE] +"\" value=\""+objEntity.getPayProcByClose()+"\" class=\"formElemen\" size=\"4\">");
				Vector vCheck = new Vector();
                vCheck.add(objEntity.getTaxIsPaid()+"");
                rowx.add(cb.draw(frmObject.fieldNames[FrmPayPeriod.FRM_FIELD_TAX_IS_PAID],vKeyPajakSetor,vValPajakSetor,vCheck));
				
			lstData.add(rowx);
			
		}
	}
	return ctrlist.draw(index);
}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidPeriod = FRMQueryString.requestLong(request, "hidden_payPeriod_id");

String frmCurrency = "#,###";
String mataUang="Rp.";
        CurrencyType currencyType = new CurrencyType();
        try{
            Vector currType = PstCurrencyType.list(0, 1, PstCurrencyType.fieldNames[PstCurrencyType.FLD_INCLUDE_INF_PROCESS]+"="+PstCurrencyType.YES_USED, "");
            if(currType!=null && currType.size()>0){
                currencyType =(CurrencyType)currType.get(0);
                frmCurrency = currencyType.getFormatCurrency();
                mataUang = currencyType.getCode(); 
            }
        }catch(Exception exc){
        
        }
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE];

CtrlPayPeriod ctrlPayPeriod = new CtrlPayPeriod(request);
ControlLine ctrLine = new ControlLine();
String strJenisBahasa = "Period";
String strCommand = "Period";
String strSave =  ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_SAVE,true);
String strDelete = ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_ASK,true);
String strCancel = ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_CANCEL,false);
String strConfirmDel = ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_DELETE,true);
String strSaveInfo = ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_SAVE_SUCCESS,false);
String strConfirmDelInfo = ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_DELETE_SUCCESS,false);
String strDelQuest = ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_DELETE_QUESTION,false);
String strBack = ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_BACK,true);
String strAdd = ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_ADD,true);
String strPrevCaption = ctrLine.getCommand(SESS_LANGUAGE,strCommand,ctrLine.CMD_PREV,false);

Vector listPayPeriod = new Vector(1,1);

/*switch statement */
iErrCode = ctrlPayPeriod.action(iCommand , oidPeriod);
/* end switch*/
FrmPayPeriod frmPayPeriod = ctrlPayPeriod.getForm();

/*count list All Period*/
int vectSize = PstPayPeriod.getCount(whereClause);

PayPeriod payPeriod = ctrlPayPeriod.getPeriod();
msgString =  ctrlPayPeriod.getMessage();

/*switch list Period*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	start = PstPayPeriod.findLimitStart(payPeriod.getOID(),recordToGet, whereClause,orderClause);
	oidPeriod = payPeriod.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlPayPeriod.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listPayPeriod = PstPayPeriod.list(start,recordToGet, whereClause , orderClause); 

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listPayPeriod.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listPayPeriod = PstPayPeriod.list(start,recordToGet, whereClause , orderClause);
}
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
	document.frmpayPeriod.hidden_payPeriod_id.value="0";
	document.frmpayPeriod.command.value="<%=Command.ADD%>";
	document.frmpayPeriod.prev_command.value="<%=prevCommand%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
}

function cmdAsk(oidPeriod){
	document.frmpayPeriod.hidden_payPeriod_id.value=oidPeriod;
	document.frmpayPeriod.command.value="<%=Command.ASK%>";
	document.frmpayPeriod.prev_command.value="<%=prevCommand%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
}


function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frmpayPeriod.command.value="<%=Command.DELETE%>";
			document.frmpayPeriod.action="period.jsp";
			document.frmpayPeriod.submit();
		}
}

function cmdSave(){
	document.frmpayPeriod.command.value="<%=Command.SAVE%>";
	document.frmpayPeriod.prev_command.value="<%=prevCommand%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
	}

function cmdEdit(oidPeriod){
	document.frmpayPeriod.hidden_payPeriod_id.value=oidPeriod;
	document.frmpayPeriod.command.value="<%=Command.EDIT%>";
	document.frmpayPeriod.prev_command.value="<%=prevCommand%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
	}

function cmdCancel(oidPeriod){
	document.frmpayPeriod.hidden_payPeriod_id.value=oidPeriod;
	document.frmpayPeriod.command.value="<%=Command.EDIT%>";
	document.frmpayPeriod.prev_command.value="<%=prevCommand%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
}

function cmdBack(){
	document.frmpayPeriod.command.value="<%=Command.BACK%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
	}

function cmdListFirst(){
	document.frmpayPeriod.command.value="<%=Command.FIRST%>";
	document.frmpayPeriod.prev_command.value="<%=Command.FIRST%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
}

function cmdListPrev(){
	document.frmpayPeriod.command.value="<%=Command.PREV%>";
	document.frmpayPeriod.prev_command.value="<%=Command.PREV%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
	}

function cmdListNext(){
	document.frmpayPeriod.command.value="<%=Command.NEXT%>";
	document.frmpayPeriod.prev_command.value="<%=Command.NEXT%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
}

function cmdListLast(){
	document.frmpayPeriod.command.value="<%=Command.LAST%>";
	document.frmpayPeriod.prev_command.value="<%=Command.LAST%>";
	document.frmpayPeriod.action="period.jsp";
	document.frmpayPeriod.submit();
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

    body {background-color: #EEE;}
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
<!-- #EndEditable --> 
</head>
<body>
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
            <span id="menu_title">Payroll <strong style="color:#333;"> / </strong>Pay Slip Period</span>
        </div>
        <div class="content-main">
            <form name="frmpayPeriod" method="post" action="">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <input type="hidden" name="vectSize" value="<%=vectSize%>">
            <input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="prev_command" value="<%=prevCommand%>">
            <input type="hidden" name="hidden_payPeriod_id" value="<%=oidPeriod%>">

            <%
            if (listPayPeriod != null && listPayPeriod.size()>0){
                %>
                <%=drawList(iCommand, listPayPeriod, frmPayPeriod, payPeriod, oidPeriod,currencyType, privUpdate)%> 
                <%
            } else {
                %>No Data<%
            }
            %>
            
            <% 
                  int cmd = 0;
                          if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
                               (iCommand == Command.NEXT || iCommand == Command.LAST))
                                       cmd =iCommand; 
                  else{
                         if(iCommand == Command.NONE || prevCommand == Command.NONE)
                               cmd = Command.FIRST;
                         else
                         { 
                               if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidPeriod == 0))
                                       cmd = PstPayPeriod.findLimitCommand(start,recordToGet,vectSize);
                               else
                                       cmd = prevCommand;
                         } 
                  } 
            %>
            <% ctrLine.setLocationImg(approot+"/images");
            ctrLine.initDefault();
            %>
          <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
          <div>&nbsp;</div>                                     
            <%if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmPayPeriod.errorSize() < 1)) {%>

                <%
                if (privAdd == true){
                    %>
                    <a href="javascript:cmdAdd()" style="color:#FFF" class="btn">Add New Period</a><%
                }
                %>

            <%}%>
                                                    
                <%
                   if((iCommand == Command.ADD || iCommand == Command.EDIT)){
                %>

                    <a href="javascript:cmdSave()" style="color:#FFF" class="btn" >Save Period</a>
                  <a href="javascript:cmdConfirmDelete()" style="color:#FFF" class="btn">Delete Period</a>
                  <a href="javascript:cmdBack()" style="color:#FFF" class="btn">Back to List Period</a>
                 <% } %>
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
</html>
