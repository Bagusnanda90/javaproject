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

<%
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- JSP Block -->
<%!
public String drawList(int iCommand, FrmPayTaxVariabel frmObject, PayTaxVariabel objEntity, Vector objectClass, long idPayVariabel){
	System.out.println("iCommand11111111111111111111:::::::::::::::::::::::::::::::::::::::::"+iCommand);
	String result = "";
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("80%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Name Variabel","50%");
	ctrlist.addHeader("Nilai Variabel ","50%");
	ctrlist.addHeader("Persen Variabel ","50%");
	

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	//Jenis Variabel
		Vector jenisKey = new Vector();
        Vector jenisValue = new Vector();
	    jenisKey.add(PstPayTaxVariabel.BIAYA_TUNJ_POT_JABATAN+"");
        jenisKey.add(PstPayTaxVariabel.TUNJANGAN_HARI_TUA+"");
		jenisKey.add(PstPayTaxVariabel.JAMSOSTEK+"");
		jenisValue.add(PstPayTaxVariabel.taxPotongan[PstPayTaxVariabel.BIAYA_TUNJ_POT_JABATAN]);
		jenisValue.add(PstPayTaxVariabel.taxPotongan[PstPayTaxVariabel.TUNJANGAN_HARI_TUA]);
		jenisValue.add(PstPayTaxVariabel.taxPotongan[PstPayTaxVariabel.JAMSOSTEK]);
		
	//String nmVariabel = objEntity.getNameVariabel()==null ? "" : ""+objEntity.getNameVariabel();
	String valueVariabel = objEntity.getValueVariabel()==0 ? "" : ""+objEntity.getValueVariabel();
	String frmCurrency = "#,###";
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			PayTaxVariabel payTaxVariabel = (PayTaxVariabel)objectClass.get(i);
			
			if(idPayVariabel == payTaxVariabel.getOID()){
			  index = i;
			}
			
			rowx = new Vector();
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				//rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxVariabel.FRM_FIELD_JENIS_VARIABEL]+"\" value=\""+payTaxVariabel.getJenis_Variabel()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add(""+ControlCombo.draw(FrmPayTaxVariabel.fieldNames[FrmPayTaxVariabel.FRM_FIELD_JENIS_VARIABEL],"formElemen",null, ""+payTaxVariabel.getJenis_Variabel(), jenisKey, jenisValue));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxVariabel.FRM_FIELD_VARIABEL_VALUE]+"\" value=\""+payTaxVariabel.getValueVariabel()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxVariabel.FRM_FIELD_PERSEN_VARIABEL]+"\" value=\""+payTaxVariabel.getPersen_variabel()+"\" class=\"formElemen\" size=\"40\">");
			}else{
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(payTaxVariabel.getOID())+"')\">"+PstPayTaxVariabel.taxPotongan[payTaxVariabel.getJenis_Variabel()]+"</a>");
				rowx.add(Formater.formatNumber(payTaxVariabel.getValueVariabel(),frmCurrency));
				rowx.add(String.valueOf(payTaxVariabel.getPersen_variabel())+"%");
			}
			
			lstData.add(rowx);
		}
		
		rowx = new Vector();
		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
			//rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxVariabel.FRM_FIELD_JENIS_VARIABEL]+"\" value=\""+nmVariabel+"\" class=\"formElemen\" size=\"40\">");
			rowx.add(""+ControlCombo.draw(FrmPayTaxVariabel.fieldNames[FrmPayTaxVariabel.FRM_FIELD_JENIS_VARIABEL],"formElemen",null, ""+objEntity.getJenis_Variabel(), jenisKey, jenisValue));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxVariabel.FRM_FIELD_VARIABEL_VALUE]+"\" value=\""+valueVariabel+"\" class=\"formElemen\" size=\"40\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxVariabel.FRM_FIELD_PERSEN_VARIABEL]+"\" value=\""+objEntity.getPersen_variabel()+"\" class=\"formElemen\" size=\"40\">");
		}
		lstData.add(rowx);
		result = ctrlist.draw();	
	}else{
		if(iCommand==Command.ADD){
				rowx.add(""+ControlCombo.draw(FrmPayTaxVariabel.fieldNames[FrmPayTaxVariabel.FRM_FIELD_JENIS_VARIABEL],"formElemen",null, ""+objEntity.getJenis_Variabel(), jenisKey, jenisValue));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxVariabel.FRM_FIELD_VARIABEL_VALUE]+"\" value=\""+valueVariabel+"\" class=\"formElemen\" size=\"40\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxVariabel.FRM_FIELD_PERSEN_VARIABEL]+"\" value=\""+objEntity.getPersen_variabel()+"\" class=\"formElemen\" size=\"40\">");
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
// request data from jsp page
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
String codeSalary = FRMQueryString.requestString(request, "hidden_code_salary");
long oidPayTaxVar = FRMQueryString.requestLong(request, "hidden_oid_pay_tax");

// variable declaration
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

// instantiate TaxType classes
CtrlPayTaxVar ctrlPayTaxVariabel = new CtrlPayTaxVar(request);
ControlLine ctrLine = new ControlLine();


// action on object agama defend on command entered
iErrCode = ctrlPayTaxVariabel.action(iCommand , oidPayTaxVar, codeSalary);
FrmPayTaxVariabel frmPayTaxVariabel = ctrlPayTaxVariabel.getForm();
PayTaxVariabel payTaxVariabel = ctrlPayTaxVariabel.getPayTaxVariabel();
msgString =  ctrlPayTaxVariabel.getMessage();

// get records to display
String whereClause = PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_LEVEL_CODE]+" = '"+codeSalary+"'";
String orderClause = "";

int vectSize = PstPayTaxVariabel.getCount(whereClause);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlPayTaxVariabel.actionList(iCommand, start, vectSize, recordToGet);
}

Vector listPayTaxVariabel = PstPayTaxVariabel.list(start, recordToGet, whereClause , orderClause);
if(listPayTaxVariabel.size()<1 && start>0){
	 if(vectSize - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listPayTaxVariabel = PstPayTaxVariabel.list(start, recordToGet, whereClause , orderClause);
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
	document.frmPayTax.hidden_oid_pay_tax.value="0";
	document.frmPayTax.command.value="<%=Command.ADD%>";
	document.frmPayTax.prev_command.value="<%=prevCommand%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
}

function cmdAsk(oidPay){
	document.frmPayTax.hidden_oid_pay_tax.value=oidPay;
	document.frmPayTax.command.value="<%=Command.ASK%>";
	document.frmPayTax.prev_command.value="<%=prevCommand%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
}

function cmdConfirmDelete(oid){
	document.frmPayTax.command.value="<%=Command.DELETE%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
}


function cmdSave(){
	document.frmPayTax.command.value="<%=Command.SAVE%>";
	document.frmPayTax.prev_command.value="<%=prevCommand%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
	}

function cmdEdit(oidPay){
	document.frmPayTax.hidden_oid_pay_tax.value=oidPay;
	document.frmPayTax.command.value="<%=Command.EDIT%>";
	document.frmPayTax.prev_command.value="<%=prevCommand%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
	}
	


function cmdCancel(oidPay){
	document.frmPayTax.hidden_oid_pay_tax.value=oidPay;
	document.frmPayTax.command.value="<%=Command.EDIT%>";
	document.frmPayTax.prev_command.value="<%=prevCommand%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
}

function cmdBack(){
	document.frmPayTax.command.value="<%=Command.LIST%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
	}

function cmdListFirst(){
	document.frmPayTax.command.value="<%=Command.FIRST%>";
	document.frmPayTax.prev_command.value="<%=Command.FIRST%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
}

function cmdListPrev(){
	document.frmPayTax.command.value="<%=Command.PREV%>";
	document.frmPayTax.prev_command.value="<%=Command.PREV%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
	}

function cmdListNext(){
	document.frmPayTax.command.value="<%=Command.NEXT%>";
	document.frmPayTax.prev_command.value="<%=Command.NEXT%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
}

function cmdListLast(){
	document.frmPayTax.command.value="<%=Command.LAST%>";
	document.frmPayTax.prev_command.value="<%=Command.LAST%>";
	document.frmPayTax.action="variabel_component.jsp";
	document.frmPayTax.submit();
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Salary 
                  Level<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frmPayTax" method="post" action="">
									  <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_code_salary" value="<%=codeSalary%>">
									  <input type="hidden" name="hidden_oid_pay_tax" value="<%=oidPayTaxVar%>">
                                      <table width="963" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                        <tr> 
                                          <td width="963">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td> <%=drawList(iCommand, frmPayTaxVariabel, payTaxVariabel, listPayTaxVariabel, oidPayTaxVar)%> </td>
                                        </tr>
										 <tr> 
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
														if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidPayTaxVar == 0))
															cmd = PstSalaryLevel.findLimitCommand(start,recordToGet,vectSize);
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
                                          <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmPayTaxVariabel.errorSize()<1)){%>
                                          <td width="154" valign="middle"> <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> 
                                            <a href="javascript:cmdAdd()" class="command">Add 
                                            New Pay Tax Variabel</a></td>
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
                                                <td width="168"><a href="javascript:cmdSave()" class="command">Save 
                                                  Pay Tax Variabel </a></td>
												<%if(iCommand==Command.EDIT){%>
                                                <td width="24" valign="middle"> 
                                                  <a href="javascript:cmdAsk('<%=oidPayTaxVar%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Save"></a> 
                                                </td>
                                                <td width="100" valign="middle"><a href="javascript:cmdAsk('<%=oidPayTaxVar%>')" class="command" >Delete 
                                                  Data</a></td>
                                                <%}%>
												<td width="537" valign="middle"> <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Add new data"></a> 
													<a href="javascript:cmdBack()" class="command">Bact to List</a></td>
                                                <td colspan="2" valign="middle"> 
                                                </td>
                                                <td width="1">&nbsp;</td>
                                                <td width="104">&nbsp;</td>
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
                                                        <a href="javascript:cmdConfirmDelete('<%=oidPayTaxVar%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Add new data"></a> 
                                                      </td>
                                                      <td width="156" valign="middle"> 
                                                        <a href="javascript:cmdConfirmDelete('<%=oidPayTaxVar%>')" class="command"> 
                                                        Ya Hapus Data</a></td>
                                                      <td width="29" valign="middle"><a href="javascript:cmdConfirmDelete('<%=oidPayTaxVar%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Add new data"></a> </td>
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
