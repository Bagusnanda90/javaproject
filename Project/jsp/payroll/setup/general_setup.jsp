 
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


<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_GENERAL);%>
<%@ include file = "../../main/checkuser.jsp" %>
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
		document.frm_pay_general.action="general.jsp";
		document.frm_pay_general.submit();
	}
	
function cmdBack(){
		document.frm_pay_general.command.value="<%=Command.FIRST%>";
                //document.frm_employee.command.value="<%=Command.LIST%>";
		document.frm_pay_general.action="general_list.jsp";
		document.frm_pay_general.submit();
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
      </table></td>
  </tr><tr><td width="88%" valign="top" align="left">
  <table width="100%" border="0" cellspacing="3" cellpadding="2"><tr><td width="100%">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td height="20"> <font color="#FF6600" face="Arial"><strong> Setup &gt; 
          General Parameter </strong></font> </td>
      </tr><tr><td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td class="tablecolor">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor"><tr><td valign="top">
          <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg"><tr><td valign="top">
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
                      <td nowrap class="listtitle" colspan="4">Setup Perusahaan</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Nama Perusahaan</td>
                      <td width="14%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_COMPANY_NAME] %>"  value="<%=payGeneral.getCompanyName()%>" size="30" maxlength="64"> 
					  *<%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_COMPANY_NAME)%>
                      </td>
                      <td width="8%" align="right">NPWP / NPPKP</td>
                      <td width="55%" nowrap> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_REG_TAX_NR] %>"   value="<%=payGeneral.getRegTaxNr()%>" size="20" maxlength="50">
                        / 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_REG_TAX_BUS_NR] %>" size="20"  value="<%=payGeneral.getRegTaxNr()%>" maxlength="50"> 
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Alamat</td>
                      <td width="14%"> <input type="text"  name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_COMP_ADDRESS] %>"  value="<%=payGeneral.getCompAddress()%>" size="30"  maxlength="128"> 
					  *<%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_COMP_ADDRESS)%>
                      </td>
                      <td width="8%" nowrap align="right">Tanggal Pengukuhan 
                      </td>
                      <td width="55%"> <%=ControlDate.drawDateWithStyle(frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_REG_TAX_DATE], payGeneral.getRegTaxDate()==null?new Date():payGeneral.getRegTaxDate(), 1, -35,"formElemen")%> </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Kota / Kode Pos</td>
                      <td width="14%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_CITY] %>"  value="<%=payGeneral.getCity()%>" size="16">
                        / 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_ZIP_CODE] %>"  value="<%=payGeneral.getZipCode()%>" size="6" maxlength="4"> 
                      </td>
                      <td width="8%" nowrap align="right">Tel./ Fax</td>
                      <td width="55%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TEL] %>" value="<%=payGeneral.getTel()%>"  size="16">
                        / 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_FAX] %>"  value="<%=payGeneral.getFax()%>" size="16"> </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Jenis Usaha</td>
                      <td width="14%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_BUSSINESS_TYPE] %>" value="<%=payGeneral.getBussinesType()%>"  size="32" maxlength="128"> 
                      </td>
                      <td width="8%" nowrap align="right">Pimpinan &amp; Jabatan</td>
                      <td width="55%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_LEADER_NAME] %>"  value="<%=payGeneral.getLeaderName()%>" size="20">
                        / 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_LEADER_POSITION] %>" value="<%=payGeneral.getLeaderPosition()%>" size="20"> </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Kantor Pembayaran Pajak</td>
                      <td width="14%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_OFFICE] %>"  value="<%=payGeneral.getTaxOffice()%>"size="32" maxlength="128"> 
                      </td>
                      <td width="8%" nowrap align="right">Lokasi pelaporan </td>
                      <td width="55%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_REP_LOCATION] %>"  value="<%=payGeneral.getTaxRepLocation()%>" size="32" maxlength="128"> 
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Jumlah Jam Kerja per 
                        hari</td>
                      <td width="14%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_WORK_DAYS] %>"  value="<%=payGeneral.getWorkDays()%>" size="6" maxlength="4"> 
                      </td>
                      <td width="8%" nowrap align="right">Local Currency </td>
                      <td width="55%"> 
					   <%
                          Vector curr_value = new Vector(1,1);
                          Vector curr_key = new Vector(1,1);
                          Vector listCurr= PstCurrencyType.list(0, 0, "", " NAME ");
                           for (int i = 0; i < listCurr.size(); i++) {
                                 CurrencyType curr = (CurrencyType) listCurr.get(i);
                                 curr_key.add(curr.getName());
                                  curr_value.add(String.valueOf(curr.getOID()));
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
                      <td colspan="4" nowrap class="listtitle">Setup Parameter 
                        Pajak</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Tahun Takwin</td>
                      <td width="14%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_YEAR] %>" value="<%=payGeneral.getTaxYear()%>" size="6" maxlength="4"> 
                      </td>
                      <td width="8%" align="right">Bulan Berjalan</td>
                      <td width="55%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_MONTH] %>"  value="<%=payGeneral.getTaxMonth()%>" size="6" maxlength="4"> 
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Tanggal Pelaporan Pajak</td>
                      <td width="14%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_REP_DATE] %>"  value="<%=payGeneral.getTaxRepDate()%>" size="15" maxlength="12"> 
                      </td>
                      <td width="8%" nowrap align="right">&nbsp;</td>
                      <td width="55%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Tarif Biaya Jabatan</td>
                      <td width="14%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_POS_COST_PCT] %>"  value="<%=payGeneral.getTaxPosCostPct()%>" size="6" maxlength="4">
                        % </td>
                      <td width="8%" nowrap align="right">Pembayaran PPh 21<br>
                        Terhutang </td>
                      <td width="55%"> <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_PAID_PCT] %>"  value="<%=payGeneral.getCity()%>" size="6" maxlength="4">
                        % </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Pembulatan Pendapatan<br>
                        Kena Pajak ( ke 1000-an)</td>
                      <td width="14%">
					     <% for(int i=0;i<PstPayGeneral.resignValue.length;i++){
                            String strRes = "";
							
                            if(payGeneral.getTaxRound1000()==PstPayGeneral.resignValue[i]){
                               strRes = "checked";
                             }
                           %> <input type="radio" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_ROUND1000]%>" value="<%=PstPayGeneral.resignValue[i]%>" <%=strRes%> style="border:'none'">
                            <%=PstPayGeneral.resignKey[i]%> <%}%> </td>
                      <td width="8%" nowrap align="right">Maksimum Biaya Jabatan 
                      </td>
                      <td width="55%" nowrap> Rp. 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_POS_COST_MAX] %>"  value="<%=payGeneral.getTaxPosCostMax()%>" size="10" maxlength="16"> 
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" align="right">Metode Perhitungan Pajak </td>
                      <td width="14%">
					 <%
                                                    Vector vKey = new Vector();
                                                    Vector vValue = new Vector();

                                                    vKey.add(PstPayGeneral.STS_GROSS+"");
													vKey.add(PstPayGeneral.STS_NETTO+"");
                                                    

                                                    vValue.add(PstPayGeneral.stMetode[PstPayGeneral.STS_GROSS]);
													vValue.add(PstPayGeneral.stMetode[PstPayGeneral.STS_NETTO]);
                                                                                                    %>
                                                  <%=ControlCombo.draw(FrmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_CALC_MTD],"formElemen",null, ""+payGeneral.getTaxCalcMtd(), vKey, vValue) %> * <%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_TAX_CALC_MTD)%> </td>
					  
					 
                      <td width="8%">&nbsp;</td>
                      <td width="55%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" align="right">Penghasilan Tidak Kena Pajak<br> 
                      </td>
                      <td width="14%" nowrap>Diri Sendiri : Rp. 
                        <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_NON_TAX_INCOME]%>" value="<%=payGeneral.getNonTaxIncome()%>" size="10" maxlength="16"> 
                      </td>
                      <td width="8%" nowrap>Istri : Rp. 
                        <input  type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_NON_TAX_WIFE]%>"  value="<%=payGeneral.getNonTaxWife()%>" size="10" maxlength="16"> 
                      </td>
                      <td width="55%">Tanggungan : Rp. 
					    <input  type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_NON_TAX_DEPNT]%>"  value="<%=payGeneral.getNonTaxDepnt()%>" size="10" maxlength="16">
                      </td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%" nowrap align="right">Formulir 1721 Ditandatangani 
                        Oleh<br> </td>
                      <td width="14%" nowrap>  <%
                                                    Vector signKey = new Vector();
                                                    Vector signValue = new Vector();

                                                    signKey.add(PstPayGeneral.PEMOTONG_PAJAK+"");
													signKey.add(PstPayGeneral.KUASA_PEMOTONG+"");
                                                    

                                                    signValue.add(PstPayGeneral.signBy[PstPayGeneral.PEMOTONG_PAJAK]);
													signValue.add(PstPayGeneral.signBy[PstPayGeneral.KUASA_PEMOTONG]);
                                                                                                    %>
                                                  <%=ControlCombo.draw(FrmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_FORM_SIGN_BY],"formElemen",null, ""+payGeneral.getTaxFormSignBy(), signKey, signValue) %> * <%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_TAX_FORM_SIGN_BY)%> </td>
					 
                      <td width="8%">&nbsp;</td>
                      <td width="55%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%"> </td>
                      <td width="14%">&nbsp;</td>
                      <td width="8%">&nbsp;</td>
                      <td width="55%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td  colspan="2"> <%
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
															%> <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a>
															  <a href="javascript:cmdSave()" class="command" style="text-decoration:none">Save Setup General</a></td>
                      <%//= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
                    <td width="55%" colspan="2" nowrap> 
					<a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a>
			         <a href="javascript:cmdBack()" class="command" style="text-decoration:none">Back to List General</a>
					</td>
                    <td width="0%" nowrap>&nbsp;</td>
                    <td width="0%">&nbsp;</td>
                    <td width="12%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%">&nbsp;</td>
                      <td width="14%">&nbsp;</td>
                      <td width="8%">&nbsp;</td>
                      <td width="55%">&nbsp;</td>
                      <td width="0%">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="11%">&nbsp;</td>
                      <td width="14%">&nbsp;</td>
                      <td width="8%">&nbsp;</td>
                      <td width="55%">&nbsp;</td>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <%@ include file = "../../main/footer.jsp" %> </td>
  </tr>
</table>
</body>
{script} 
</html>
