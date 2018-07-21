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
public String drawList(int iCommand, FrmCurrency_Rate frmObject, Currency_Rate objEntity, Vector objectClass, long idCurrency_Rate){
	String result = "";

	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("80%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Currency Type","15%");
	ctrlist.addHeader("Rate Company","10%");
	ctrlist.addHeader("Rate Bank","15%");
	ctrlist.addHeader("Rate Tax","10%");
	ctrlist.addHeader("Start Date","20%");
	ctrlist.addHeader("End Date","20%");
	ctrlist.addHeader("Status","10%");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
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
				
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(currency_Rate.getOID())+"')\">"+currency_Rate.getCurr_code()+" - "+objCur.getName()+"</a>");
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
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Currency 
                  Rate <!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmCurr_Rate" method="post" action="">
									  <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_id_currency_rate" value="<%=oidCurrency_Rate%>">
                                      <table width="963" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
										<%
										try{
										%>
                                        <tr> 
                                          <td>
										  <%=drawList(iCommand, frmCurrency_Rate, currency_Rate, listCurrency_Rate, oidCurrency_Rate)%> 
										  </td>
                                        </tr>
										<% 
										  }catch(Exception exc){ 
										  System.out.println("Err::::::"+exc.toString());
										  }%>
										
										<tr> 
                                          <td>
										  <table>
											 
											
										  </table>
										  </td>
                                        </tr>
                                      </table>
									  <table width="100%" border="0">
									  <tr> 
                                          <td width="31%">
										   <span class="command"> 
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
														if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCurrency_Rate == 0))
															cmd = PstCurrency_Rate.findLimitCommand(start,recordToGet,vectSize);
														else
															cmd = prevCommand;
													  } 
												   } 
												%>
												<% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span>
										  </td>
                                        </tr>
									  <tr> 
									  <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmCurrency_Rate.errorSize()<1)){%>
									  <td colspan="2" valign="middle"> <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> 
										<a href="javascript:cmdAdd()" class="command">Add 
												New Currency Rate</a></td>
									  <%}%>
										</tr>
										<tr> 
										<%
										   if((iCommand == Command.ADD || iCommand == Command.EDIT)){
										%>
										  <td> <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a>
										   <a href="javascript:cmdSave()" class="command" style="text-decoration:none">Save Setup Currency Rate</a></td>
										   <td width="27%">
                                          <a href="javascript:cmdConfirmDelete()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnDel.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete"></a>
                                          <a href="javascript:cmdConfirmDelete()" class="command">Delete Currency</a> </td>
								    	  <td width="42%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a>
										  <a href="javascript:cmdBack()" class="command" style="text-decoration:none">Back to List Currency Rate</a></td>
										</tr>
										<%}%>
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
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
