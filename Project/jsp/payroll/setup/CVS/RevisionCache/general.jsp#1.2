 
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION); %>

<%@ include file = "../../main/javainit.jsp" %>
<%
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
%>
<%

	CtrlPayGeneral ctrlPayGeneral = new CtrlPayGeneral(request);
	long oidPayGeneral = FRMQueryString.requestLong(request, "pay_general_oid");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
	
	int iErrCode = FRMMessage.ERR_NONE;
	String msgString = "";
	ControlLine ctrLine = new ControlLine();
	System.out.println("iCommand = "+iCommand);
	iErrCode = ctrlPayGeneral.action(iCommand , oidPayGeneral);
	msgString = ctrlPayGeneral.getMessage();
	FrmPayGeneral frmPayGeneral = ctrlPayGeneral.getForm();
	PayGeneral payGeneral = ctrlPayGeneral.getPayGeneral();
	oidPayGeneral = payGeneral.getOID();
%>
<%
if(iCommand==Command.DELETE){
	%>
<jsp:forward page="general_list.jsp">
<jsp:param name="prev_command" value="<%=prevCommand%>" />
<jsp:param name="start" value="<%=start%>" />
<jsp:param name="pay_general_oid" value="<%=payGeneral.getOID()%>" />
</jsp:forward>
<%
 }
%>


<!-- JSP Block -->
<!-- End of JSP Block -->
<html>

 
<head>
<title>HARISMA - </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<SCRIPT language=JavaScript>
function cmdSave(){
		document.frm_pay_general.command.value="<%=Command.SAVE%>";
		document.frm_pay_general.action="general_list.jsp";
		document.frm_pay_general.submit();
	}
	
function cmdBack(){
		document.frm_pay_general.command.value="<%=Command.FIRST%>";
		document.frm_pay_general.action="general_list.jsp";
		document.frm_pay_general.submit();
	}
	
function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frm_pay_general.command.value="<%=Command.DELETE%>";
			document.frm_pay_general.action="general.jsp";
			document.frm_pay_general.submit();
		}
}
	
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
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <%@ include file = "../../main/header.jsp" %> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <%@ include file = "../../main/mnmain.jsp" %> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="10" valign="middle"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
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
  <table width="100%" border="0" cellspacing="3" cellpadding="2"><tr><td width="100%">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td height="20"> <font color="#FF6600" face="Arial"><strong> Setup &gt; 
          General Parameter </strong></font> </td>
      </tr><tr><td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td  style="background-color:<%=bgColorContent%>; ">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor"><tr><td valign="top">
          <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg"><tr><td valign="top">
            <form name="frm_pay_general" method="post" action="">
			<input type="hidden" name="command" value="">
			<input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="pay_general_oid" value="<%=oidPayGeneral%>">
             <input type="hidden" name="prev_command" value="<%=prevCommand%>">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td class="contenttitle">&nbsp;</td>
                </tr>
                <tr> <td>
                  <table width="100%" border="0" cellspacing="1" cellpadding="1">
                    <tr> 
                                                <td nowrap class="listtitle" colspan="4">Company 
                                                  Setup </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Company 
                                                  Name </td>
                      <td width="23%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_COMPANY_NAME] %>"  value="<%=payGeneral.getCompanyName()%>" size="30" maxlength="64"> 
					  *<%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_COMPANY_NAME)%>
                      </td>
                      <td width="17%" align="right">NPWP / NPPKP</td>
                      <td width="27%" nowrap> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_REG_TAX_NR] %>"   value="<%=payGeneral.getRegTaxNr()%>" size="20" maxlength="20">
                        / 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_REG_TAX_BUS_NR] %>" size="20"  value="<%=payGeneral.getRegTaxNr()%>" maxlength="20"> 
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Address</td>
                      <td width="23%"> <input type="text"  name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_COMP_ADDRESS] %>"  value="<%=payGeneral.getCompAddress()%>" size="30"  maxlength="128"> 
					  *<%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_COMP_ADDRESS)%>
                      </td>
                                                <td width="17%" nowrap align="right">Establishment 
                                                  Date </td>
                      <td width="27%"> <%=ControlDate.drawDateWithStyle(frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_REG_TAX_DATE], payGeneral.getRegTaxDate()==null?new Date():payGeneral.getRegTaxDate(), 1, -35,"formElemen")%> </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">City 
                                                  / Pos Code</td>
                      <td width="23%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_CITY] %>"  value="<%=payGeneral.getCity()%>" size="16" maxlength="16"> / 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_ZIP_CODE] %>"  value="<%=payGeneral.getZipCode()%>" size="6" maxlength="6"> 
                      </td>
                      <td width="17%" nowrap align="right">Tel./ Fax</td>
                      <td width="27%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TEL] %>" value="<%=payGeneral.getTel()%>"  size="16">
                        / 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_FAX] %>"  value="<%=payGeneral.getFax()%>" size="16"> </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Bussiness 
                                                  Type </td>
                      <td width="23%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_BUSSINESS_TYPE] %>" value="<%=payGeneral.getBussinessType()%>"  size="32" maxlength="128"> 
                      </td>
                                                <td width="17%" nowrap align="right">Leader 
                                                  Name &amp; Position</td>
                      <td width="27%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_LEADER_NAME] %>"  value="<%=payGeneral.getLeaderName()%>" size="20">
                        / 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_LEADER_POSITION] %>" value="<%=payGeneral.getLeaderPosition()%>" size="20"> </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Tax 
                                                  Office </td>
                      <td width="23%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_OFFICE] %>"  value="<%=payGeneral.getTaxOffice()%>"size="32" maxlength="128"> 
                      </td>
                                                <td width="17%" nowrap align="right">Tax 
                                                  Report Location</td>
                      <td width="27%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_REP_LOCATION] %>"  value="<%=payGeneral.getTaxRepLocation()%>" size="32" maxlength="128"> 
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Work 
                                                  Days</td>
                      <td width="23%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_WORK_DAYS] %>"  value="<%=payGeneral.getWorkDays()%>" size="6" maxlength="4"> 
                      </td>
                      <td width="17%" nowrap align="right">Local Currency </td>
                      <td width="27%"> 
					   <%
                          Vector curr_value = new Vector(1,1);
                          Vector curr_key = new Vector(1,1);
                          Vector listCurr= PstCurrencyType.list(0, 0, "", " NAME ");
                           for (int i = 0; i < listCurr.size(); i++) {
                                 CurrencyType curr = (CurrencyType) listCurr.get(i);
                                 curr_key.add(curr.getName());
                                  curr_value.add(String.valueOf(curr.getCode()));
                           }
                        %> <%= ControlCombo.draw(FrmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_LOCAL_CUR_CODE],"formElemen",null, ""+payGeneral.getLocalCurCode(), curr_value, curr_key) %> * <%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_LOCAL_CUR_CODE)%>
                                          
					   </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td nowrap class="listtitle" align="right">&nbsp;</td>
                      <td nowrap class="listtitle">&nbsp;</td>
                      <td nowrap class="listtitle" align="right">&nbsp;</td>
                      <td nowrap class="listtitle">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td colspan="4" nowrap class="listtitle">Tax 
                                                  Setup </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Tax 
                                                  Year </td>
                      <td width="23%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_YEAR] %>" value="<%=payGeneral.getTaxYear()%>" size="6" maxlength="4"> 
                      </td>
                                                <td width="17%" align="right">Tax 
                                                  Month </td>
                      <td width="27%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_MONTH] %>"  value="<%=payGeneral.getTaxMonth()%>" size="6" maxlength="4"> 
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Tax 
                                                  Report Date</td>
                      <td width="23%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_REP_DATE] %>"  value="<%=payGeneral.getTaxRepDate()%>" size="15" maxlength="12"> 
                      </td>
                      <td width="17%" nowrap align="right">&nbsp;</td>
                      <td width="27%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Tax 
                                                  Position Cost</td>
                      <td width="23%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_POS_COST_PCT] %>"  value="<%=payGeneral.getTaxPosCostPct()%>" size="6" maxlength="4">
                        % </td>
                                                <td width="17%" nowrap align="right">Uncomplete 
                                                  Payment of PPh 21</td>
                      <td width="27%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_PAID_PCT] %>"  value="<%=payGeneral.getTaxPaidPct()%>" size="6" maxlength="4">
                        % </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Tax 
                                                  Round 1000</td>
                      <td width="23%">
					     <% for(int i=0;i<PstPayGeneral.resignValue.length;i++){
                            String strRes = "";
							
                            if(payGeneral.getTaxRound1000()==PstPayGeneral.resignValue[i]){
                               strRes = "checked";
                             }
                           %> <input type="radio" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_ROUND1000]%>" value="<%=PstPayGeneral.resignValue[i]%>" <%=strRes%> style="border:'none'">
                            <%=PstPayGeneral.resignKey[i]%> <%}%> </td>
                                                <td width="17%" nowrap align="right">Tax 
                                                  Position Cost Maks</td>
                      <td width="27%" nowrap> Rp. 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_POS_COST_MAX] %>"  value="<%=payGeneral.getTaxPosCostMax()%>" size="10" maxlength="16"> 
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" align="right">Tax 
                                                  Calculation Methode</td>
                      <td width="23%">
					 <%
                                                    Vector vKey = new Vector();
                                                    Vector vValue = new Vector();

                                                    vKey.add(PstPayGeneral.STS_GROSS+"");
													vKey.add(PstPayGeneral.STS_NETTO+"");
                                                    

                                                    vValue.add(PstPayGeneral.stMetode[PstPayGeneral.STS_GROSS]);
													vValue.add(PstPayGeneral.stMetode[PstPayGeneral.STS_NETTO]);
                                                                                                    %>
                                                  <%=ControlCombo.draw(FrmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_CALC_MTD],"formElemen",null, ""+payGeneral.getTaxCalcMtd(), vKey, vValue) %> * <%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_TAX_CALC_MTD)%> </td>
					  
					 
                      <td width="17%">&nbsp;</td>
                      <td width="27%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" align="right">Non 
                                                  Tax Income<br> 
                      </td>
                                                <td width="23%" nowrap>Employee 
                                                  : Rp. 
                                                  <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_NON_TAX_INCOME]%>" value="<%=payGeneral.getNonTaxIncome()%>" size="10" maxlength="16"> 
                      </td>
                                                <td width="17%" nowrap>Wife : Rp. 
                                                  <input  type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_NON_TAX_WIFE]%>"  value="<%=payGeneral.getNonTaxWife()%>" size="10" maxlength="16"> 
                      </td>
                                                <td width="27%">Guarantee : Rp. 
                                                  <input  type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_NON_TAX_DEPNT]%>"  value="<%=payGeneral.getNonTaxDepnt()%>" size="10" maxlength="16">
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                                                <td width="26%" nowrap align="right">Tax 
                                                  Form 1721 Sign By<br> </td>
                      <td width="23%" nowrap>  <%
                                                    Vector signKey = new Vector();
                                                    Vector signValue = new Vector();

                                                    signKey.add(PstPayGeneral.PEMOTONG_PAJAK+"");
													signKey.add(PstPayGeneral.KUASA_PEMOTONG+"");
                                                    

                                                    signValue.add(PstPayGeneral.signBy[PstPayGeneral.PEMOTONG_PAJAK]);
													signValue.add(PstPayGeneral.signBy[PstPayGeneral.KUASA_PEMOTONG]);
                                                                                                    %>
                                                  <%=ControlCombo.draw(FrmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_FORM_SIGN_BY],"formElemen",null, ""+payGeneral.getTaxFormSignBy(), signKey, signValue) %> * <%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_TAX_FORM_SIGN_BY)%> </td>
					 
                      <td width="17%">&nbsp;</td>
                      <td width="27%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="26%"> </td>
                      <td width="23%">&nbsp;</td>
                      <td width="17%">&nbsp;</td>
                      <td width="27%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td  colspan="1"> <%
													/*ctrLine.setLocationImg(approot+"/images");
													ctrLine.initDefault();
													ctrLine.setTableWidth("80%");
													/*String scomDel = "javascript:cmdAsk('"+oidDivision+"')";
													String sconDelCom = "javascript:cmdConfirmDelete('"+oidDivision+"')";
													String scancel = "javascript:cmdEdit('"+oidDivision+"')";*/
													ctrLine.setBackCaption("Back to List");
													ctrLine.setCommandStyle("buttonlink");
													ctrLine.setBackCaption("Back to List Division");
													ctrLine.setSaveCaption("Save Division");
													ctrLine.setConfirmDelCaption("Yes Delete Division");
													ctrLine.setDeleteCaption("Delete Division");

														/*if (privDelete){
															ctrLine.setConfirmDelCommand(sconDelCom);
															ctrLine.setDeleteCommand(scomDel);
															ctrLine.setEditCommand(scancel);
														}else{ 
															ctrLine.setConfirmDelCaption("");
															ctrLine.setDeleteCaption("");
															ctrLine.setEditCaption("");
														}*/

															//if(privAdd == false  && privUpdate == false){
																/*ctrLine.setSaveCaption("");
															}*/
															%> <img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save">
															  <a href="javascript:cmdSave()" class="command" style="text-decoration:none">Save Setup General</a></td>
														  <%//= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
<%
 if(iErrCode != FRMMessage.NONE){
      out.println(msgString);
 }
                       %>
														   <td width="300">
														   <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
														   <a href="javascript:cmdConfirmDelete('<%=oidPayGeneral%>')" class="command">Delete Setup General</a> </td>
														<td colspan="2" nowrap> 
														<img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a>
														 <a href="javascript:cmdBack()" class="command" style="text-decoration:none">Back to List General</a>
														</td>
                    <td width="0%" nowrap>&nbsp;</td>
                    <td width="0%">&nbsp;</td>
                    <td width="7%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="26%">&nbsp;</td>
                      <td width="23%">&nbsp;</td>
                      <td width="17%">&nbsp;</td>
                      <td width="27%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="26%">&nbsp;</td>
                      <td width="23%">&nbsp;</td>
                      <td width="17%">&nbsp;</td>
                      <td width="27%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                  </table></td></tr>
                <tr> 
                  <td>&nbsp;</td>
                </tr>
                <tr> 
                  <td>&nbsp;</td>
                </tr>
                <tr> 
                  <td>&nbsp;</td>
                </tr>
              </table>
            </form>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p></td></tr>
          </table></td></tr>
        </table></td></tr>
        <tr> 
          <td>&nbsp; </td>
        </tr>
      </table></td></tr>
    </table></td></tr>
  </table></td></tr>
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
{script} 
</html>
