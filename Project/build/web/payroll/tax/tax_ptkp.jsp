<%@ page language="java" %>
<%@ page import = "java.util.*" %>

<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<%
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- JSP Block -->
<%!
public String drawList(int iCommand, FrmTax_PTKP frmObject, Tax_PTKP objEntity, Vector objectClass, long idTax_PTKP){
	String result = "";
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Marital Status","20%");
	ctrlist.addHeader("PTKP setahun ","30%");
	ctrlist.addHeader("PTKP sebulan ","20%");
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	String frmCurrency = "#,###";
	
	/* selected idMartial*/
	Vector idmarital_value = new Vector(1,1);
	Vector idmarital_key = new Vector(1,1);
	Vector vctMarital = PstMarital.list(0,0, "", "");
	if(vctMarital!=null && vctMarital.size()>0){
		for(int ix =0; ix<vctMarital.size(); ix++){
			Marital m = (Marital)vctMarital.get(ix);
			idmarital_value.add(""+m.getOID());
			idmarital_key.add(m.getMaritalCode());
		}
	}
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			Tax_PTKP tax_PTKP = (Tax_PTKP)objectClass.get(i);
			
			if(idTax_PTKP == tax_PTKP.getOID()){
			  index = i;
			}
			
			rowx = new Vector();
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmTax_PTKP.FRM_FIELD_MARTIAL_ID],null, ""+tax_PTKP.getMartialId(), idmarital_value, idmarital_key, "formElemen", ""));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_PTKP.FRM_FIELD_SETAHUN_PTKP]+"\" value=\""+tax_PTKP.getPtkp_setahun()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_PTKP.FRM_FIELD_SEBULAN_PTKP]+"\" value=\""+tax_PTKP.getPtkp_sebulan()+"\" class=\"formElemen\" size=\"40\">");
			}else{
				Marital marital = new Marital();
				try{
					marital = PstMarital.fetchExc(tax_PTKP.getMartialId());
				}
				catch(Exception ex){
				}
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(tax_PTKP.getOID())+"')\">"+marital.getMaritalCode()+"</a>");
				rowx.add(Formater.formatNumber(tax_PTKP.getPtkp_setahun(),frmCurrency));
				rowx.add(Formater.formatNumber(tax_PTKP.getPtkp_sebulan(),frmCurrency));
			}
			
			lstData.add(rowx);
		}
		
		rowx = new Vector();
		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmTax_PTKP.FRM_FIELD_MARTIAL_ID],null, ""+objEntity.getMartialId(), idmarital_value, idmarital_key, "formElemen", ""));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_PTKP.FRM_FIELD_SETAHUN_PTKP]+"\" value=\""+objEntity.getPtkp_setahun()+"\" class=\"formElemen\" size=\"40\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_PTKP.FRM_FIELD_SEBULAN_PTKP]+"\" value=\""+objEntity.getPtkp_sebulan()+"\" class=\"formElemen\" size=\"40\">");
		}
		lstData.add(rowx);
		result = ctrlist.draw();	
	}else{
		if(iCommand==Command.ADD){
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmTax_PTKP.FRM_FIELD_MARTIAL_ID],null, ""+objEntity.getMartialId(), idmarital_value, idmarital_key, "formElemen", ""));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_PTKP.FRM_FIELD_SETAHUN_PTKP]+"\" value=\""+objEntity.getPtkp_setahun()+"\" class=\"formElemen\" size=\"40\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmTax_PTKP.FRM_FIELD_SEBULAN_PTKP]+"\" value=\""+objEntity.getPtkp_sebulan()+"\" class=\"formElemen\" size=\"40\">");
			lstData.add(rowx);
			result = ctrlist.draw();
		}else{
			result = "<i>Belum ada Data di dalam sistem</i>";
		}
	}
	return result;
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidTax_Ptkp = FRMQueryString.requestLong(request, "hidden_id_ptkp");
long idRegulasi = FRMQueryString.requestLong(request, "hidden_id_regulasi");

int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

// instantiate TaxType classes
CtrlTax_PTKP ctrlTax_PTKP = new CtrlTax_PTKP(request);
ControlLine ctrLine = new ControlLine();

// action on object agama defend on command entered
iErrCode = ctrlTax_PTKP.action(iCommand , oidTax_Ptkp, idRegulasi, request);
FrmTax_PTKP frmTax_PTKP = ctrlTax_PTKP.getForm();
Tax_PTKP tax_PTKP = ctrlTax_PTKP.getTax_PTKP();
msgString =  ctrlTax_PTKP.getMessage();

//list regulasi periode yang valid
String sWhereRegulasi = PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_STATUS]+" = "+PstPayRegulasi.VALID;
Vector vListPeriodRegulasi = PstPayRegulasi.list(0,0,sWhereRegulasi,"");
PayRegulasi regulasi = new PayRegulasi();
if(vListPeriodRegulasi.size()>0 && vListPeriodRegulasi!=null){
	regulasi = (PayRegulasi)vListPeriodRegulasi.get(0);
}
idRegulasi = regulasi.getOID();
whereClause = PstTax_PTKP.fieldNames[PstTax_PTKP.FLD_REGULASI_ID]+" = "+idRegulasi;
Vector listTax_PTKP = PstTax_PTKP.list(start, recordToGet, whereClause , orderClause);

int vectSize = PstTax_PTKP.getCount(whereClause);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlTax_PTKP.actionList(iCommand, start, vectSize, recordToGet);
}

if(listTax_PTKP.size()<1 && start>0){
	 if(vectSize - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listTax_PTKP = PstTax_PTKP.list(start, recordToGet, whereClause , orderClause);
}

Vector vListRegulasiX = PstPayRegulasi.list(0,0,"","");
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
	document.frmTaxPTKP.hidden_id_ptkp.value="0";
	document.frmTaxPTKP.command.value="<%=Command.ADD%>";
	document.frmTaxPTKP.prev_command.value="<%=prevCommand%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
}

function cmdAsk(oidPay){
	document.frmTaxPTKP.hidden_id_ptkp.value=oidPay;
	document.frmTaxPTKP.command.value="<%=Command.ASK%>";
	document.frmTaxPTKP.prev_command.value="<%=prevCommand%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
}

function cmdConfirmDelete(oid){
	document.frmTaxPTKP.command.value="<%=Command.DELETE%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
}


function cmdSave(){
	document.frmTaxPTKP.command.value="<%=Command.SAVE%>";
	document.frmTaxPTKP.prev_command.value="<%=prevCommand%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
	}

function cmdEdit(oidPay){
	document.frmTaxPTKP.hidden_id_ptkp.value=oidPay;
	document.frmTaxPTKP.command.value="<%=Command.EDIT%>";
	document.frmTaxPTKP.prev_command.value="<%=prevCommand%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
	}
	


function cmdCancel(oidPay){
	document.frmTaxPTKP.hidden_id_ptkp.value=oidPay;
	document.frmTaxPTKP.command.value="<%=Command.EDIT%>";
	document.frmTaxPTKP.prev_command.value="<%=prevCommand%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
}

function cmdBack(){
	document.frmTaxPTKP.command.value="<%=Command.LIST%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
	}

function cmdListFirst(){
	document.frmTaxPTKP.command.value="<%=Command.FIRST%>";
	document.frmTaxPTKP.prev_command.value="<%=Command.FIRST%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
}

function cmdListPrev(){
	document.frmTaxPTKP.command.value="<%=Command.PREV%>";
	document.frmTaxPTKP.prev_command.value="<%=Command.PREV%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
	}

function cmdListNext(){
	document.frmTaxPTKP.command.value="<%=Command.NEXT%>";
	document.frmTaxPTKP.prev_command.value="<%=Command.NEXT%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
}

function cmdListLast(){
	document.frmTaxPTKP.command.value="<%=Command.LAST%>";
	document.frmTaxPTKP.prev_command.value="<%=Command.LAST%>";
	document.frmTaxPTKP.action="tax_ptkp.jsp";
	document.frmTaxPTKP.submit();
}

function viewHistory()
{
   window.open("view_history.jsp", "group", "height=400,width=700,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Tarif PTKP<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frmTaxPTKP" method="post" action="">
									  <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_id_ptkp" value="<%=oidTax_Ptkp%>">
									  <input type="hidden" name="hidden_id_regulasi" value="<%=idRegulasi%>">
									  <table width="500" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> <table width="600" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                              <tr> 
                                                <td colspan="2">Periode</td>
                                                <td colspan="2">:</td>
                                                <td width="867" colspan="2"><%=regulasi.getPeriod()%></td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2">Pertanggal</td>
                                                <td colspan="2">:</td>
                                                <td width="867" colspan="2"><%=Formater.formatDate(regulasi.getStartDate(),"dd-MM-yyyy")%> sampai <%=Formater.formatDate(regulasi.getEndDate(),"dd-MM-yyyy")%></td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
										
                                        <tr> 
                                          <td colspan="3"> <%=drawList(iCommand, frmTax_PTKP, tax_PTKP, listTax_PTKP, oidTax_Ptkp)%> </td>
                                        </tr>
										</table>
										<table>
                                        <tr> 
                                          <td height="8" align="left" colspan="5" class="command"> 
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
														if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidTax_Ptkp == 0))
															cmd = PstTax_PTKP.findLimitCommand(start,recordToGet,vectSize);
														else
															cmd = prevCommand;
													  } 
												   } 
												%>
                                            <% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												 %>
                                            <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                        </tr>
										
                                        <tr> 
                                          <td height="9" colspan="3"> <table>
                                            </table></td>
                                        </tr>
                                        <tr>
										  <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmTax_PTKP.errorSize()<1)){%>
                                          <td width="26" valign="middle"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> </td>
                                          <td width="256" valign="middle"> <a href="javascript:cmdAdd()" class="command">Add 
                                            New Pay Tax PTKP</a></td>
										  <%
										  if(vListRegulasiX.size()>1)
										  {%>	
										  	<td width="731" valign="middle"><a href="javascript:viewHistory()" class="command">History</a></td>
										  <%
										  }%>
                                          <%}%>
                                        </tr>
                                        <tr> 
                                          <td height="50" colspan="3" valign="middle"> 
                                            <table width="99%">
                                              <%
											if(iCommand == Command.ADD || iCommand==Command.EDIT)
											{
											%>
                                              <tr> 
                                                <td width="24"> <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></td>
                                                <td width="154"><a href="javascript:cmdSave()" class="command">Save 
                                                  Pay Tax PTKP</a></td>
                                                <%if(iCommand==Command.EDIT){%>
                                                <td width="24" valign="middle"> 
                                                  <a href="javascript:cmdAsk('<%=oidTax_Ptkp%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Save"></a> 
                                                </td>
                                                <td width="93" valign="middle"><a href="javascript:cmdAsk('<%=oidTax_Ptkp%>')" class="command" >Delete 
                                                  Data</a></td>
                                                <%}%>
                                                <td width="24" valign="middle"> 
                                                  <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Add new data"></a> 
                                                <td width="551" valign="middle"><a href="javascript:cmdBack()" class="command">Back 
                                                  to List</a></td>
                                                <td colspan="2" valign="middle"> 
                                                </td>
                                                <td width="1">&nbsp;</td>
                                                <td width="100">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td colspan="8"> </td>
                                              </tr>
                                              <%
											}
											%>
                                            </table></td>
                                        </tr>
                                        <tr> 
                                          <td height="50" colspan="3" valign="middle"> 
                                            <table width="1021">
                                              <tr> 
                                                <%if(iCommand == Command.ASK){%>
                                                <td colspan="5" valign="left" class="msgquestion"> 
                                                  Anda Yakin Menghapus Data?</td>
                                              </tr>
                                              <tr> 
                                                <td width="24" valign="middle"> 
                                                  <a href="javascript:cmdConfirmDelete('<%=oidTax_Ptkp%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Add new data"></a> 
                                                </td>
                                                <td width="156" valign="middle"> 
                                                  <a href="javascript:cmdConfirmDelete('<%=oidTax_Ptkp%>')" class="command"> 
                                                  Ya Hapus Data</a></td>
                                                <td width="29" valign="middle"><a href="javascript:cmdConfirmDelete('<%=oidTax_Ptkp%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Add new data"></a> 
                                                </td>
                                                <td width="207" valign="middle"><a href="javascript:cmdCancel()" class="command">Batal</a></td>
                                                <td width="581" valign="middle"></td>
                                              </tr>
                                              <%}%>
                                            </table></td>
                                        </tr>
                                      </table>
									  <table width="100%" border="0">
									  <tr> 
                                           
											</tr>
										  
									  <!--		<tr> 
                                          <td> <input type="submit" name="Submit" value="Regenerate Data Forms"> 
                                          </td>
										  <td width="0"></td>
                                        </tr>-->
										
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
