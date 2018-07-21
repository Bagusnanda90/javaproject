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
	ctrlist.setAreaWidth("80%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Nama Marital","20%");
	ctrlist.addHeader("PTKP setahun ","40%");
	ctrlist.addHeader("PTKP sebulan ","40%");
	

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
				rowx.add(marital.getMaritalCode());
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
long idRegulasi = FRMQueryString.requestLong(request, ""+FrmTax_PTKP.fieldNames[FrmTax_PTKP.FRM_FIELD_REGULASI_ID]+"");

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


String sWhereRegulasi = PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_REGULASI_ID]+" = "+idRegulasi;
Vector vListPeriodRegulasi = PstPayRegulasi.list(0,0,sWhereRegulasi,"");
PayRegulasi regulasi = new PayRegulasi();
if(vListPeriodRegulasi.size()>0 && vListPeriodRegulasi!=null){
	regulasi = (PayRegulasi)vListPeriodRegulasi.get(0);
}



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
function cmdSearch(){
	document.frmTaxPTKP.command.value="<%=Command.LIST%>";
	document.frmTaxPTKP.action="view_history.jsp";
	document.frmTaxPTKP.submit();
}


function cmdBack(){
	document.frmTaxPTKP.command.value="<%=Command.LIST%>";
	document.frmTaxPTKP.action="view_history.jsp";
	document.frmTaxPTKP.submit();
	}

function cmdListFirst(){
	document.frmTaxPTKP.command.value="<%=Command.FIRST%>";
	document.frmTaxPTKP.prev_command.value="<%=Command.FIRST%>";
	document.frmTaxPTKP.action="view_history.jsp";
	document.frmTaxPTKP.submit();
}

function cmdListPrev(){
	document.frmTaxPTKP.command.value="<%=Command.PREV%>";
	document.frmTaxPTKP.prev_command.value="<%=Command.PREV%>";
	document.frmTaxPTKP.action="view_history.jsp";
	document.frmTaxPTKP.submit();
	}

function cmdListNext(){
	document.frmTaxPTKP.command.value="<%=Command.NEXT%>";
	document.frmTaxPTKP.prev_command.value="<%=Command.NEXT%>";
	document.frmTaxPTKP.action="view_history.jsp";
	document.frmTaxPTKP.submit();
}

function cmdListLast(){
	document.frmTaxPTKP.command.value="<%=Command.LAST%>";
	document.frmTaxPTKP.prev_command.value="<%=Command.LAST%>";
	document.frmTaxPTKP.action="view_history.jsp";
	document.frmTaxPTKP.submit();
}

function viewHistory()
{
   window.open("view_history.jsp", "group", "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
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
									  <%
									  Date dtStartPeriod = regulasi.getStartDate();
									  Date dtEndPeriod = regulasi.getEndDate();
									  String startPeriod = dtStartPeriod==null ? "-" : Formater.formatDate(dtStartPeriod, "dd-MM-yyyy");
									  String endPeriod = dtEndPeriod==null ? "-" : Formater.formatDate(dtEndPeriod, "dd-MM-yyyy");
									  %>
									  <table width="963" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> <table width="500" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                              <tr> 
                                                <td colspan="2">Periode</td>
                                                <td colspan="2">:</td>
                                                <td width="867" colspan="2">
												 <%
													  Vector vectKey = new Vector();
													  Vector vectVal = new Vector();
													  PayRegulasi payRegulasi = new PayRegulasi();
													  Vector vectList = PstPayRegulasi.list(0,0,PstPayRegulasi.fieldNames[PstPayRegulasi.FLD_STATUS]+" = "+PstPayRegulasi.NOT_VALID, "");
													  for(int i=0; i<vectList.size(); i++){
															payRegulasi = (PayRegulasi)vectList.get(i);
															vectKey.add(""+payRegulasi.getOID());
															vectVal.add(payRegulasi.getPeriod());
															
													}
													%> <%=ControlCombo.draw(FrmTax_PTKP.fieldNames[FrmTax_PTKP.FRM_FIELD_REGULASI_ID],"Pilih",""+idRegulasi,vectKey,vectVal, "onChange=\"javascript:cmdSearch()\"","formElemen")%> 
												</td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2">Pertanggal</td>
                                                <td colspan="2">:</td>
                                                <td width="867" colspan="2"><%=startPeriod%> sampai <%=endPeriod%></td>
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
