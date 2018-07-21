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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%
privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- JSP Block -->
<%!
public String drawList(int iCommand, FrmTax_Slip_Nr frmObject, Tax_Slip_Nr objEntity, Vector objectClass, long idTax_Slip_Nr){
	String result = "";

	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("80%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Period","20%");
	ctrlist.addHeader("Prefix","20%");
	ctrlist.addHeader("Digit","20%");
	ctrlist.addHeader("Sufix","20%");
	ctrlist.addHeader("Tax Type","20%");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	//untuk mengambil digit
	Vector vKeyDigit = new Vector();
    Vector vValDigit = new Vector();
    vKeyDigit.add(PstTax_Slip_Nr.DIGIT_I+"");
    vKeyDigit.add(PstTax_Slip_Nr.DIGIT_II+"");
	vKeyDigit.add(PstTax_Slip_Nr.DIGIT_III+"");
	vValDigit.add(PstTax_Slip_Nr.digit[PstTax_Slip_Nr.DIGIT_I]);
    vValDigit.add(PstTax_Slip_Nr.digit[PstTax_Slip_Nr.DIGIT_II]);
	vValDigit.add(PstTax_Slip_Nr.digit[PstTax_Slip_Nr.DIGIT_III]);
	
	//untuk menampilkan periode
	Vector period_value = new Vector(1,1);
    Vector period_key = new Vector(1,1);
	String orderCl = PstPeriod.fieldNames[PstPeriod.FLD_PERIOD];
    Vector listPeriod= PstPeriod.list(0, 0, "", orderCl);
    for (int i = 0; i < listPeriod.size(); i++) {
		 Period period = (Period) listPeriod.get(i);
		 period_key.add(period.getPeriod());
		 period_value.add(String.valueOf(period.getOID()));
    }
	
	
	//untuk menampilkan Tax Type
	Vector taxType_value = new Vector(1,1);
    Vector taxType_key = new Vector(1,1);
	String orderCls = PstTaxType.fieldNames[PstTaxType.FLD_TAX_CODE];
    Vector listTaxType = PstTaxType.list(0, 0, "", orderCls);
    for (int i = 0; i < listTaxType.size(); i++) {
		 TaxType taxType = (TaxType)listTaxType.get(i);
		 taxType_key.add(taxType.getTaxCode()+"-"+taxType.getTaxType());
		 taxType_value.add(String.valueOf(taxType.getTaxCode()));
    }
	
	
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			Tax_Slip_Nr tax_Slip_Nr = (Tax_Slip_Nr)objectClass.get(i);
			if(idTax_Slip_Nr == tax_Slip_Nr.getOID()){
			  index = i;
			}
			
			rowx = new Vector();
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				rowx.add(ControlCombo.drawWithStyle(frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_PERIOD_ID],null, ""+tax_Slip_Nr.getPeriod_id(), period_value, period_key, "formElemen"));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_PREFIX] +"\" value=\""+tax_Slip_Nr.getPrefix()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_DIGIT], null, ""+tax_Slip_Nr.getDigit(),vKeyDigit,vValDigit));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_SUFIX] +"\" value=\""+tax_Slip_Nr.getSufix()+"\" class=\"formElemen\" size=\"10\">");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_TAX_CODE], null, ""+tax_Slip_Nr.getTax_code(),taxType_key,taxType_value));
				
				
			}else{
				Period objPeriod = new Period();
				if(tax_Slip_Nr.getPeriod_id()!=0){
					try{
						objPeriod = PstPeriod.fetchExc(tax_Slip_Nr.getPeriod_id());
					}catch(Exception e){;}
				}
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(tax_Slip_Nr.getOID())+"')\">"+objPeriod.getPeriod()+"</a>");
				rowx.add(tax_Slip_Nr.getPrefix());
				rowx.add(String.valueOf(PstTax_Slip_Nr.digit[tax_Slip_Nr.getDigit()]));
				rowx.add(tax_Slip_Nr.getSufix());
				rowx.add(tax_Slip_Nr.getTax_code());
				
			}
			lstData.add(rowx);
		}
		rowx = new Vector();

		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
			rowx.add(ControlCombo.drawWithStyle(frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_PERIOD_ID],null, ""+objEntity.getPeriod_id(), period_value, period_key, "formElemen"));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_PREFIX] +"\" value=\""+objEntity.getPrefix()+"\" class=\"formElemen\" size=\"40\">");
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_DIGIT], null, ""+objEntity.getDigit(),vKeyDigit,vValDigit));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_SUFIX] +"\" value=\""+objEntity.getSufix()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_TAX_CODE], null, ""+objEntity.getTax_code(),taxType_key,taxType_value));
			
		}
		lstData.add(rowx);
		result = ctrlist.draw();
	}else{
		if(iCommand==Command.ADD){
			rowx.add(ControlCombo.drawWithStyle(frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_PERIOD_ID],null, ""+objEntity.getPeriod_id(), period_value, period_key, "formElemen"));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_PREFIX] +"\" value=\""+objEntity.getPrefix()+"\" class=\"formElemen\" size=\"40\">");
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_DIGIT], null, ""+objEntity.getDigit(),vKeyDigit,vValDigit));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_SUFIX] +"\" value=\""+objEntity.getSufix()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmTax_Slip_Nr.FRM_FIELD_TAX_CODE], null, ""+objEntity.getTax_code(),taxType_key,taxType_value));
			
			lstData.add(rowx);
			result = ctrlist.draw();
		}else{
			result = "<i>No data found ...</i>";
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
long oidTax_Slip_Nr = FRMQueryString.requestLong(request, "hidden_id_tax_slip_nr");

// variable declaration
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;


CtrlTax_Slip_Nr ctrlTax_Slip_Nr = new CtrlTax_Slip_Nr(request);
ControlLine ctrLine = new ControlLine();

iErrCode = ctrlTax_Slip_Nr.action(iCommand , oidTax_Slip_Nr);
FrmTax_Slip_Nr frmTax_Slip_Nr = ctrlTax_Slip_Nr.getForm();
Tax_Slip_Nr tax_Slip_Nr = ctrlTax_Slip_Nr.getTax_Slip_Nr();
msgString =  ctrlTax_Slip_Nr.getMessage();

// get records to display
String whereClause = "";
String orderClause = "";

int vectSize = PstTax_Slip_Nr.getCount(whereClause);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlTax_Slip_Nr.actionList(iCommand, start, vectSize, recordToGet);
}

Vector listTax_Slip_Nr = PstTax_Slip_Nr.list(start, recordToGet, whereClause , orderClause);
if(listTax_Slip_Nr.size()<1 && start>0){
	 if(vectSize - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listTax_Slip_Nr = PstTax_Slip_Nr.list(start, recordToGet, whereClause , orderClause);
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
	document.frmTax_Nr.hidden_id_tax_slip_nr.value="0";
	document.frmTax_Nr.command.value="<%=Command.ADD%>";
	document.frmTax_Nr.prev_command.value="<%=prevCommand%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
}

function cmdAsk(oidTax_Slip_Nr){
	document.frmTax_Nr.hidden_id_tax_slip_nr.value=oidTax_Slip_Nr;
	document.frmTax_Nr.command.value="<%=Command.ASK%>";
	document.frmTax_Nr.prev_command.value="<%=prevCommand%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
}

function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frmTax_Nr.command.value="<%=Command.DELETE%>";
			document.frmTax_Nr.action="tax-slip-nr.jsp";
			document.frmTax_Nr.submit();
		}
}


function cmdSave(){
	document.frmTax_Nr.command.value="<%=Command.SAVE%>";
	document.frmTax_Nr.prev_command.value="<%=prevCommand%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
	}

function cmdEdit(oidTax_Slip_Nr){
	document.frmTax_Nr.hidden_id_tax_slip_nr.value=oidTax_Slip_Nr;
	document.frmTax_Nr.command.value="<%=Command.EDIT%>";
	document.frmTax_Nr.prev_command.value="<%=prevCommand%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
	}

function cmdCancel(oidTax_Slip_Nr){
	document.frmTax_Nr.hidden_id_tax_slip_nr.value=oidTax_Slip_Nr;
	document.frmTax_Nr.command.value="<%=Command.EDIT%>";
	document.frmTax_Nr.prev_command.value="<%=prevCommand%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
}

function cmdBack(){
	document.frmTax_Nr.command.value="<%=Command.BACK%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
	}

function cmdListFirst(){
	document.frmTax_Nr.command.value="<%=Command.FIRST%>";
	document.frmTax_Nr.prev_command.value="<%=Command.FIRST%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
}

function cmdListPrev(){
	document.frmTax_Nr.command.value="<%=Command.PREV%>";
	document.frmTax_Nr.prev_command.value="<%=Command.PREV%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
	}

function cmdListNext(){
	document.frmTax_Nr.command.value="<%=Command.NEXT%>";
	document.frmTax_Nr.prev_command.value="<%=Command.NEXT%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
}

function cmdListLast(){
	document.frmTax_Nr.command.value="<%=Command.LAST%>";
	document.frmTax_Nr.prev_command.value="<%=Command.LAST%>";
	document.frmTax_Nr.action="tax-slip-nr.jsp";
	document.frmTax_Nr.submit();
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Format 
                  of Tax Slip Number<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frmTax_Nr" method="post" action="">
									<input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_id_tax_slip_nr" value="<%=oidTax_Slip_Nr%>">
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgensell">
									    <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
										<%
										try{
										%>
                                        <tr> 
                                          <td align="left">
										  <%=drawList(iCommand, frmTax_Slip_Nr, tax_Slip_Nr, listTax_Slip_Nr, oidTax_Slip_Nr)%> 
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
                                          <td>
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
														if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidTax_Slip_Nr == 0))
															cmd = PstTax_Slip_Nr.findLimitCommand(start,recordToGet,vectSize);
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
										<%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmTax_Slip_Nr.errorSize()<1)){%>
									  <td width="128"  valign="middle"> <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> 
					 				  <a href="javascript:cmdAdd()" class="command">Add 
										  New Tax Slip</a></td>
									  <%}%>
									</tr>
									<tr> 
									<%
									   if((iCommand == Command.ADD || iCommand == Command.EDIT)){
									%>
									  <td> <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a>
										  <a href="javascript:cmdSave()" class="command" >Save 
										  Setup Tax Slip</a></td>
										  <td colspan="2">
                                         <a href="javascript:cmdConfirmDelete()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnDel.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete"></a>
                                          <a href="javascript:cmdConfirmDelete()" class="command">Delete Tax Slip</a> </td>
										 <td><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a>
										 <a href="javascript:cmdBack()" class="command" >Back 
										  to List Tax Slip</a></td>
									</tr>
									<%}%>
									  <tr>
										<td>&nbsp;</td>
										<td width="33">&nbsp;</td>
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
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
