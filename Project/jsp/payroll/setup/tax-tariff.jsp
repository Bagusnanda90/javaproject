 
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_LANGUAGE); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
%>


<%
	CtrlPayTaxTariff ctrlPayTaxTariff = new CtrlPayTaxTariff(request);
	long oidPayTaxTariff = FRMQueryString.requestLong(request, "pay_tax_tariff_oid");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
	
	int iErrCode = FRMMessage.ERR_NONE;
	String msgString = "";
	ControlLine ctrLine = new ControlLine();
	System.out.println("iCommand = "+iCommand);
	iErrCode = ctrlPayTaxTariff.action(iCommand , oidPayTaxTariff);
	msgString = ctrlPayTaxTariff.getMessage();
	FrmPayTaxTariff frmPayTaxTariff = ctrlPayTaxTariff.getForm();
	PayTaxTariff payTaxTariff = ctrlPayTaxTariff.getPayTaxTariff();
	oidPayTaxTariff = payTaxTariff.getOID();
	
	
	 /*variable declaration*/
	//String frmCurrency = "#,###";
    int recordToGet = 10;
    String whereClause = "";
    String orderClause = " TAX_YEAR ";
	Vector listPayTaxTariff= new Vector(1,1);
	
	 /*switch statement */
    iErrCode = ctrlPayTaxTariff.action(iCommand , oidPayTaxTariff);
    /* end switch*/
   

    /*count list All Language*/
    int vectSize = PstPayTaxTariff.getCount(whereClause);

    /*switch list Language*/
    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlPayTaxTariff.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    //PayExecutive payExecutive = ctrLanguage.getLanguage();
    msgString =  ctrlPayTaxTariff.getMessage();

    /* get record to display */
    listPayTaxTariff = PstPayTaxTariff.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listPayTaxTariff.size() < 1 && start > 0)
    {
             if (vectSize - recordToGet > recordToGet)
                            start = start - recordToGet;   //go to Command.PREV
             else{
                     start = 0 ;
                     iCommand = Command.FIRST;
                     prevCommand = Command.FIRST; //go to Command.FIRST
             }
             listPayTaxTariff = PstPayTaxTariff.list(start,recordToGet, whereClause , orderClause);
    }

%>

<!-- JSP Block -->
<%!
	public String drawList(int iCommand,FrmPayTaxTariff frmObject, PayTaxTariff objEntity, Vector objectClass,  long payTaxTariffId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Tax Year","10%");
		ctrlist.addHeader("Level","15%");
		ctrlist.addHeader("Salary Start ( Rp)","25%");
		ctrlist.addHeader("Salary Up To ( Rp)","25%");
		ctrlist.addHeader("Tax Tarif ( %)","25%");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
		
		Vector sal_value = new Vector(1,1);
    	Vector sal_key = new Vector(1,1);
		//String orderCl = PstCurrencyType.fieldNames[PstCurrencyType.FLD_NAME]+" DESC ";
    	Vector listSal= PstSalaryLevel.list(0, 0, "", " LEVEL_CODE ");
    	for (int i = 0; i < listSal.size(); i++) {
			 SalaryLevel sal = (SalaryLevel) listSal.get(i);
			 sal_key.add(sal.getLevelCode());
			 sal_value.add(String.valueOf(sal.getLevelCode()));
    	}

		for (int i = 0; i < objectClass.size(); i++) {
			 PayTaxTariff payTaxTariff = (PayTaxTariff)objectClass.get(i);
			 rowx = new Vector();
			 if(payTaxTariffId == payTaxTariff.getOID())
				 index = i; 
			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
			 	rowx.add("<input type=\"text\" align =\"center\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_TAX_YEAR] +"\" value=\""+objEntity.getTaxYear()+"\" size=\"20\" class=\"elemenForm\">");
				//rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_LEVEL] +"\" value=\""+objEntity.getLevel()+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add(""+ControlCombo.draw(""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_LEVEL],"formElemen",null, ""+objEntity.getLevel(),sal_value,sal_key) );
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_SALARY_MIN] +"\" value=\""+objEntity.getSalaryMin()+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_SALARY_MAX] +"\" value=\""+objEntity.getSalaryMax()+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_TAX_TARIFF] +"\" value=\""+objEntity.getTaxTariff()+"\" size=\"15\" class=\"elemenForm\"> %");
				//rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayExecutive.FRM_FIELD_TAX_FORM] +"\" value=\""+payExecutive.getTaxForm()+"\" size=\"30\" class=\"elemenForm\">");
				//rowx.add(""+ControlCombo.draw(frmObject.fieldNames[FrmPayExecutive.FRM_FIELD_TAX_FORM],"formElemen",null,payExecutive.getTaxForm(),PstPayExecutive.getTaxForm(),PstPayExecutive.getTaxForm()));
				//rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayExecutive.FRM_FIELD_EXECUTIVE_NAME] +"\" value=\""+objEntity.getExecutiveName()+"\" size=\"30\" class=\"elemenForm\">");
			}else{
				//System.out.println("aku cek");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(payTaxTariff.getOID())+"')\">"+payTaxTariff.getTaxYear()+"</a>");
				rowx.add(""+payTaxTariff.getLevel());
				rowx.add(""+Formater.formatNumber(payTaxTariff.getSalaryMin(),"#,###"));
				rowx.add(""+Formater.formatNumber(payTaxTariff.getSalaryMax(),"#,###"));
				rowx.add(""+payTaxTariff.getTaxTariff()+"%");
			} 
		lstData.add(rowx);
		}
		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0) || (objectClass.size()<1)){ 
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_TAX_YEAR] +"\" value=\""+objEntity.getTaxYear()+"\" size=\"20\" class=\"elemenForm\">");
				//rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_LEVEL] +"\" value=\""+objEntity.getLevel()+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add(""+ControlCombo.draw(""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_LEVEL],"formElemen",null, ""+objEntity.getLevel(),sal_value,sal_key) );
				rowx.add("<input type=\"text\" align =\"center\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_SALARY_MIN] +"\" value=\""+objEntity.getSalaryMin()+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_SALARY_MAX] +"\" value=\""+objEntity.getSalaryMax()+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayTaxTariff.FRM_FIELD_TAX_TARIFF] +"\" value=\""+objEntity.getTaxTariff()+"\" size=\"15\" class=\"elemenForm\"> %");
		}
	 lstData.add(rowx);

		return ctrlist.draw();
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
<SCRIPT language=JavaScript>

function cmdSave(){
	document.frm_tax_tariff.command.value="<%=Command.SAVE%>";
	document.frm_tax_tariff.prev_command.value="<%=prevCommand%>";
	document.frm_tax_tariff.action="tax-tariff.jsp";
	document.frm_tax_tariff.submit();
}

function cmdAdd(){
	document.frm_tax_tariff.pay_tax_tariff_oid.value="0";
	document.frm_tax_tariff.command.value="<%=Command.ADD%>";
	document.frm_tax_tariff.prev_command.value="<%=prevCommand%>";
	document.frm_tax_tariff.action="tax-tariff.jsp";
	document.frm_tax_tariff.submit();
}

function cmdBack(){
	document.frm_tax_tariff.command.value="<%=Command.BACK%>";
	document.frm_tax_tariff.action="tax-tariff.jsp";
	document.frm_tax_tariff.submit();
}
function cmdEdit(oidPayTaxTariff){
	document.frm_tax_tariff.pay_tax_tariff_oid.value=oidPayTaxTariff;
	document.frm_tax_tariff.command.value="<%=Command.EDIT%>";
	document.frm_tax_tariff.prev_command.value="<%=prevCommand%>";
	document.frm_tax_tariff.action="tax-tariff.jsp";
	document.frm_tax_tariff.submit();
}

function cmdListFirst(){
	document.frm_tax_tariff.command.value="<%=Command.FIRST%>";
	document.frm_tax_tariff.prev_command.value="<%=Command.FIRST%>";
	document.frm_tax_tariff.action="tax-tariff.jsp";
	document.frm_tax_tariff.submit();
}

function cmdListPrev(){
	document.frm_tax_tariff.command.value="<%=Command.PREV%>";
	document.frm_tax_tariff.prev_command.value="<%=Command.PREV%>";
	document.frm_tax_tariff.action="tax-tariff.jsp";
	document.frm_tax_tariff.submit();
}

function cmdListNext(){
	document.frm_tax_tariff.command.value="<%=Command.NEXT%>";
	document.frm_tax_tariff.prev_command.value="<%=Command.NEXT%>";
	document.frm_tax_tariff.action="tax-tariff.jsp";
	document.frm_tax_tariff.submit();
}

function cmdListLast(){
	document.frm_tax_tariff.command.value="<%=Command.LAST%>";
	document.frm_tax_tariff.prev_command.value="<%=Command.LAST%>";
	document.frm_tax_tariff.action="tax-tariff.jsp";
	document.frm_tax_tariff.submit();
}

function cmdConfirmDelete(oid){
	var x = confirm(" Are You Sure to Delete?");
	if(x){
		document.frm_tax_tariff.command.value="<%=Command.DELETE%>";
		document.frm_tax_tariff.action="tax-tariff.jsp";
		document.frm_tax_tariff.submit();
	}
}

function cmdAsk(oid){
		document.frm_tax_tariff.command.value="<%=Command.ASK%>";
		document.frm_tax_tariff.action="tax-tariff.jsp";
		document.frm_tax_tariff.submit();
}

function fnTrapKD(){
	//alert(event.keyCode);
		switch(event.keyCode) {
			case <%=LIST_PREV%>:
				cmdListPrev();
				break;
			case <%=LIST_NEXT%>:
				cmdListNext();
				break;
			case <%=LIST_FIRST%>:
				cmdListFirst();
				break;
			case <%=LIST_LAST%>:
				cmdListLast();
				break;
			default:
				break;
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
	
	function showObjectForMenu(){
        
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Tax 
                  Tariff <!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frm_tax_tariff" method="post" action="">
										<input type="hidden" name="command" value="">
										<input type="hidden" name="start" value="<%=start%>">
										<input type="hidden" name="pay_tax_tariff_oid" value="<%=oidPayTaxTariff%>">
										 <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <table width="75%" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                        <tr><td>
											<table width="75%" border="0" cellspacing="0" cellpadding="0">   
									           <tr align="left" valign="top"> 
                                                <td  align="left" height="14" valign="middle" colspan="2" class="listtitle">&nbsp;Tax Tariff List </td>
     											</tr> 
									     	</table>
										</td>
										</tr>
										<%
                                                try{
                                                if((listPayTaxTariff == null || listPayTaxTariff.size()<1)&&(iCommand == Command.NONE))
                                                        iCommand = Command.ADD;  
                                                %>
											<tr>
												<td width="24%" colspan="2">
										  	 		<%= drawList(iCommand,frmPayTaxTariff, payTaxTariff,listPayTaxTariff,oidPayTaxTariff)%>
													<table cellpadding="0" cellspacing="0" border="0">
														   <% 
													  }catch(Exception exc){ 
													  }
													   %>
												   </table>
												  </td>
											</tr>
                                      </table>
									  <table width="100%" border="0">
									  <tr align="left" valign="top">
									  <td height="8" align="left" colspan="2" class="command">
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
                                                                cmd =prevCommand; 
                                                   } 
                                                %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                     %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
									   	</span>
									  </td>
									  </tr>
									  	 <%
										if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmPayTaxTariff.errorSize()<1)){
    											if(privAdd){%>
									  		<tr> 
                                            <td width="157"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
                                            <a href="javascript:cmdAdd()" class="command">Add 
                                             New Tax Tariff</a> </td>
                                            </tr>
										<%}
                                            }%>
									   <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmPayTaxTariff.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                        <tr align="left" valign="top"> 
                                         <td>
                                         <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                                          <a href="javascript:cmdSave()" class="command">Save Tax Tariff</a> </td>
										 <td>
                                         <a href="javascript:cmdConfirmDelete()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnDel.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete"></a>
                                          <a href="javascript:cmdConfirmDelete()" class="command">Delete Tax Tariff</a> </td>
										  <td >
                                         <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                          <a href="javascript:cmdBack()" class="command">Back to List Tax Tariff</a> </td>
										  
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
