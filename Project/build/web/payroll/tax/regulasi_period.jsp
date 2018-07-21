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
public String drawList(int iCommand, FrmPayRegulasi frmObject, PayRegulasi objEntity, Vector objectClass, long idPayRegulasi){
	String result = "";
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Nama Period","20%");
	ctrlist.addHeader("Start Date","35%");
	ctrlist.addHeader("End Date","35%");
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
	
	//untuk mengambil name Status
	Vector vKeyNmStatus = new Vector();
    Vector vValNmStatus = new Vector();
    vKeyNmStatus.add(PstPayRegulasi.VALID+"");
    vKeyNmStatus.add(PstPayRegulasi.NOT_VALID+"");
    vValNmStatus.add(PstPayRegulasi.nameStatus[PstPayRegulasi.VALID]);
    vValNmStatus.add(PstPayRegulasi.nameStatus[PstPayRegulasi.NOT_VALID]);
	
	String frmCurrency = "#,###";
	String nmPeriod = objEntity.getPeriod()==null ? "" : ""+objEntity.getPeriod();
	
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			PayRegulasi payRegulasi = (PayRegulasi)objectClass.get(i);
			
			if(idPayRegulasi == payRegulasi.getOID()){
			  index = i;
			}
			Date dtNow = new Date();
			/*if(payRegulasi.getStatus()==1){
				objEntity.setEndDate(dtNow);
				payRegulasi.setEndDate(dtNow);
			}else{
				objEntity.setEndDate(payRegulasi.getEndDate());
				payRegulasi.setEndDate(payRegulasi.getEndDate());
			}*/
			
			rowx = new Vector();
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_PERIODE]+"\" value=\""+payRegulasi.getPeriod()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_START_DATE], payRegulasi.getStartDate() != null ? payRegulasi.getStartDate() : new Date(),"formElemen", 1, -5));
				rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_END_DATE], payRegulasi.getEndDate() != null ? payRegulasi.getEndDate() : new Date(),"formElemen", 1, -5));
				//rowx.add(payRegulasi.getEndDate());
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_STATUS], null, ""+payRegulasi.getStatus(),vKeyNmStatus,vValNmStatus));
			}else{
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(payRegulasi.getOID())+"')\">"+payRegulasi.getPeriod()+"</a>");
				rowx.add(Formater.formatDate(payRegulasi.getStartDate(),"yyyy-MM-dd"));
				rowx.add(Formater.formatDate(payRegulasi.getEndDate(),"yyyy-MM-dd"));
				rowx.add(String.valueOf(PstPayRegulasi.nameStatus[payRegulasi.getStatus()]));
			}
			
			lstData.add(rowx);
		}
		
		rowx = new Vector();
		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_PERIODE]+"\" value=\""+nmPeriod+"\" class=\"formElemen\" size=\"40\">");
			rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_START_DATE], objEntity.getStartDate() != null ? objEntity.getStartDate() : new Date(),"formElemen", 1, -5));
			rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_END_DATE], objEntity.getEndDate() != null ? objEntity.getEndDate() : new Date(),"formElemen", 1, -5));
			//rowx.add(Formater.formatDate(objEntity.getEndDate(),"yyyy-MM-dd"));
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_STATUS], null, ""+objEntity.getStatus(),vKeyNmStatus,vValNmStatus));
		}
		lstData.add(rowx);
		result = ctrlist.draw();	
	}else{
		if(iCommand==Command.ADD){
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_PERIODE]+"\" value=\""+nmPeriod+"\" class=\"formElemen\" size=\"40\">");
			rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_START_DATE], objEntity.getStartDate() != null ? objEntity.getStartDate() : new Date(),"formElemen", 1, -5));
			rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_END_DATE], objEntity.getEndDate() != null ? objEntity.getEndDate() : new Date(),"formElemen", 1, -5));
			//rowx.add(Formater.formatDate(objEntity.getEndDate(),"yyyy-MM-dd"));
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmPayRegulasi.FRM_FIELD_STATUS], null, ""+objEntity.getStatus(),vKeyNmStatus,vValNmStatus));
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
long oid_Tax_Regulasi = FRMQueryString.requestLong(request, "hidden_id_tax_regulasi");

System.out.println("iCommand di atas....."+iCommand);
if(iCommand==Command.SAVE){
	PstPayRegulasi.UpdateStatusRegulasi();
}
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = " STATUS,START_DATE";

// instantiate TaxType classes
CtrlPayRegulasi ctrlPayRegulasi = new CtrlPayRegulasi(request);
ControlLine ctrLine = new ControlLine();


// action on object agama defend on command entered
iErrCode = ctrlPayRegulasi.action(iCommand , oid_Tax_Regulasi);
FrmPayRegulasi frmPayRegulasi = ctrlPayRegulasi.getForm();
PayRegulasi payRegulasi = ctrlPayRegulasi.getPayRegulasi();
msgString =  ctrlPayRegulasi.getMessage();


int vectSize = PstPayRegulasi.getCount(whereClause);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlPayRegulasi.actionList(iCommand, start, vectSize, recordToGet);
}

Vector listPayRegulasi = PstPayRegulasi.list(start, recordToGet, whereClause , orderClause);
if(listPayRegulasi.size()<1 && start>0){
	 if(vectSize - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listPayRegulasi = PstPayRegulasi.list(start, recordToGet, whereClause , orderClause);
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
	document.frmTaxRegulasi.hidden_id_tax_regulasi.value="0";
	document.frmTaxRegulasi.command.value="<%=Command.ADD%>";
	document.frmTaxRegulasi.prev_command.value="<%=prevCommand%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
}

function cmdSave(){
	document.frmTaxRegulasi.hidden_id_tax_regulasi.value="0";
	document.frmTaxRegulasi.command.value="<%=Command.SAVE%>";
	document.frmTaxRegulasi.prev_command.value="<%=prevCommand%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
	}

function cmdAsk(oidPay){
	document.frmTaxRegulasi.hidden_id_tax_regulasi.value=oidPay;
	document.frmTaxRegulasi.command.value="<%=Command.ASK%>";
	document.frmTaxRegulasi.prev_command.value="<%=prevCommand%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
}

function cmdConfirmDelete(oid){
	document.frmTaxRegulasi.command.value="<%=Command.DELETE%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
}




function cmdEdit(oidPay){
	document.frmTaxRegulasi.hidden_id_tax_regulasi.value=oidPay;
	document.frmTaxRegulasi.command.value="<%=Command.EDIT%>";
	document.frmTaxRegulasi.prev_command.value="<%=prevCommand%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
	}
	


function cmdCancel(oidPay){
	document.frmTaxRegulasi.hidden_id_tax_regulasi.value=oidPay;
	document.frmTaxRegulasi.command.value="<%=Command.EDIT%>";
	document.frmTaxRegulasi.prev_command.value="<%=prevCommand%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
}

function cmdBack(){
	document.frmTaxRegulasi.command.value="<%=Command.LIST%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
	}

function cmdListFirst(){
	document.frmTaxRegulasi.command.value="<%=Command.FIRST%>";
	document.frmTaxRegulasi.prev_command.value="<%=Command.FIRST%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
}

function cmdListPrev(){
	document.frmTaxRegulasi.command.value="<%=Command.PREV%>";
	document.frmTaxRegulasi.prev_command.value="<%=Command.PREV%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
	}

function cmdListNext(){
	document.frmTaxRegulasi.command.value="<%=Command.NEXT%>";
	document.frmTaxRegulasi.prev_command.value="<%=Command.NEXT%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
}

function cmdListLast(){
	document.frmTaxRegulasi.command.value="<%=Command.LAST%>";
	document.frmTaxRegulasi.prev_command.value="<%=Command.LAST%>";
	document.frmTaxRegulasi.action="regulasi_period.jsp";
	document.frmTaxRegulasi.submit();
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Regulasi Period<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frmTaxRegulasi" method="post" action="">
									  <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_id_tax_regulasi" value="<%=oid_Tax_Regulasi%>">
                                      <table width="963" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                        <tr> 
                                          <td width="963">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td> <%=drawList(iCommand, frmPayRegulasi, payRegulasi, listPayRegulasi, oid_Tax_Regulasi)%> </td>
                                        </tr>
										</table>
										<table><tr> 
                                          <td height="8" align="left" colspan="3" class="command"> 
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
														if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oid_Tax_Regulasi == 0))
															cmd = PstPayRegulasi.findLimitCommand(start,recordToGet,vectSize);
														else
															cmd = prevCommand;
													  } 
												   } 
												%>
												<% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                        </tr>
                                        <tr> 
                                          <td height="9"> <table>
                                            </table></td>
                                        </tr>
                                        <tr> 
                                          <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmPayRegulasi.errorSize()<1)){%>
                                          <td width="154" valign="middle"> <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> 
                                            <a href="javascript:cmdAdd()" class="command">Add 
                                            New Pay Tax Regulasi</a></td>
                                          <%}%>
                                        </tr>
                                        <tr> 
                                          <td height="50" valign="middle"> <table width="99%">
                                              <%
											if(iCommand == Command.ADD || iCommand==Command.EDIT)
											{
											%>
                                              <tr> 
                                                <td width="24"> <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></td>
                                                <td width="154"><a href="javascript:cmdSave()" class="command">Save 
                                                  Pay Tax Regulasi</a></td>
												<%if(iCommand==Command.EDIT){%>
                                                <td width="24" valign="middle"> 
                                                  <a href="javascript:cmdAsk('<%=oid_Tax_Regulasi%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Save"></a> 
                                                </td>
                                                <td width="93" valign="middle"><a href="javascript:cmdAsk('<%=oid_Tax_Regulasi%>')" class="command" >Delete 
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
                                          <td height="50" valign="middle">
										  <table width="1021">
                                                    <tr> 
                                                      <%if(iCommand == Command.ASK){%>
                                                      <td colspan="5" valign="left" class="msgquestion"> 
                                                        Anda Yakin Menghapus Data?</td>
                                                     
                                                    </tr>
                                                    <tr> 
                                                      <td width="24" valign="middle"> 
                                                        <a href="javascript:cmdConfirmDelete('<%=oid_Tax_Regulasi%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Add new data"></a> 
                                                      </td>
                                                      <td width="156" valign="middle"> 
                                                        <a href="javascript:cmdConfirmDelete('<%=oid_Tax_Regulasi%>')" class="command"> 
                                                        Ya Hapus Data</a></td>
                                                      <td width="29" valign="middle"><a href="javascript:cmdConfirmDelete('<%=oid_Tax_Regulasi%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Add new data"></a> </td>
                                                      <td width="207" valign="middle"><a href="javascript:cmdCancel()" class="command">Batal</a></td>
                                                      <td width="581" valign="middle"></td>
                                                    </tr>
													 <%}%>
                                                  </table>
										  </td>
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