
<% 
/* 
 * Page Name  		:  srcPosition.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>



<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_INPUT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%!
	public String drawList(int iCommand,FrmPayAdditional frmObject, PayAdditional objEntity, Vector objectClass,  long summaryId,long oidPeriod)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Additional Name","10%");
		ctrlist.addHeader("Value","15%");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
		//untuk status currency dipakai atau tidak
		for (int i = 0; i < objectClass.size(); i++) {
			 PayAdditional payAdditional = (PayAdditional)objectClass.get(i);
			 rowx = new Vector();
			 if(summaryId == payAdditional.getOID())
				 index = i; 
			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
			 	rowx.add("<input type=\"hidden\" align =\"center\" name=\""+frmObject.fieldNames[FrmPayAdditional.FRM_FIELD_PERIOD_ID] +"\" value=\""+oidPeriod+"\" size=\"20\" class=\"elemenForm\"><input type=\"text\" align =\"center\" name=\""+frmObject.fieldNames[FrmPayAdditional.FRM_FIELD_SUMMARY_NAME] +"\" value=\""+objEntity.getSummaryName()+"\" size=\"20\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayAdditional.FRM_FIELD_VALUE] +"\" value=\""+objEntity.getValue()+"\" size=\"30\" class=\"elemenForm\">");
			 }else{
				//System.out.println("aku cek");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(payAdditional.getOID())+"')\">"+payAdditional.getSummaryName()+"</a>");
				rowx.add(""+payAdditional.getValue());
			} 
		lstData.add(rowx);
		}
		 rowx = new Vector();
		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0) || (objectClass.size()<1)){ 
				rowx.add("<input type=\"hidden\" align =\"center\" name=\""+frmObject.fieldNames[FrmPayAdditional.FRM_FIELD_PERIOD_ID] +"\" value=\""+oidPeriod+"\" size=\"20\" class=\"elemenForm\"><input type=\"text\" align =\"center\" name=\""+frmObject.fieldNames[FrmPayAdditional.FRM_FIELD_SUMMARY_NAME] +"\" value=\""+objEntity.getSummaryName()+"\" size=\"20\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayAdditional.FRM_FIELD_VALUE] +"\" value=\""+objEntity.getValue()+"\" size=\"30\" class=\"elemenForm\">");
		}
	 lstData.add(rowx);

		return ctrlist.draw();
	}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
CtrlPayAdditional ctrlPayAdditional = new CtrlPayAdditional(request);
long oidPeriod = FRMQueryString.requestLong(request,"periode");
long oidSummary = FRMQueryString.requestLong(request, "hidden_summary_id");
int iErrCode = FRMMessage.ERR_NONE;
String msgString = "";
ControlLine ctrLine = new ControlLine();
System.out.println("iCommand = "+iCommand);
iErrCode = ctrlPayAdditional.action(iCommand , oidSummary);
msgString = ctrlPayAdditional.getMessage();
FrmPayAdditional frmPayAdditional = ctrlPayAdditional.getForm();
PayAdditional payAdditional = ctrlPayAdditional.getPayAdditional();
oidSummary = payAdditional.getOID();
iErrCode = ctrlPayAdditional.action(iCommand , oidSummary);

/*if(iCommand==Command.BACK)
{        
	frmSrcSection  = new FrmSrcSection (request, srcSection);
	try
	{			
		srcSection = (SrcSection) session.getValue(PstSection.SESS_HR_SECTION);			
		if(srcSection== null)
		{
			srcSection = new SrcSection();
		}		
	}
	catch (Exception e)
	{
		srcSection = new SrcSection();
	}
}*/
if(iCommand==Command.NONE){
	Date date = new Date();
	oidPeriod = PstPeriod.getPeriodIdBySelectedDate(date);
}
Vector listAdditional = new Vector(1,1);
String whereClause = PstPayAdditional.fieldNames[PstPayAdditional.FLD_PERIOD_ID]+"="+oidPeriod;

if(iCommand==Command.LIST || iCommand==Command.NONE || iCommand==Command.EDIT || iCommand==Command.ADD || iCommand==Command.SAVE || iCommand==Command.DELETE || iCommand==Command.BACK)
{       
	listAdditional = PstPayAdditional.list(0,0,"","");
}	
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Section</title>
<script language="JavaScript">
<!--
function cmdSearch(){ 
	document.frmsrcsection.command.value="<%=Command.LIST%>";
	document.frmsrcsection.action="summary_additional.jsp";
	document.frmsrcsection.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

function cmdEdit(oidSummary){
	document.frmsrcsection.hidden_summary_id.value=oidSummary;
	document.frmsrcsection.command.value="<%=Command.EDIT%>";
	document.frmsrcsection.action="summary_additional.jsp";
	document.frmsrcsection.submit();
	}

function cmdAdd(){
	document.frmsrcsection.hidden_summary_id.value="0";
	document.frmsrcsection.command.value="<%=Command.ADD%>";
	document.frmsrcsection.action="summary_additional.jsp";
	document.frmsrcsection.submit();
}
function cmdSave(){
	document.frmsrcsection.command.value="<%=Command.SAVE%>";
	document.frmsrcsection.action="summary_additional.jsp";
	document.frmsrcsection.submit();
}
function cmdConfirmDelete(oid){
		var x = confirm(" Are you sure to delete?");
		if(x){
			document.frmsrcsection.command.value="<%=Command.DELETE%>";
			document.frmsrcsection.action="summary_additional.jsp";
			document.frmsrcsection.submit();
		}
}
function cmdBack(){
	document.frmsrcsection.command.value="<%=Command.BACK%>";
	document.frmsrcsection.action="summary_additional.jsp";
	document.frmsrcsection.submit();
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
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Payroll Setup &gt; Summary Addtional<!-- #EndEditable --> </strong></font> 
                </td>
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
                                    <form name="frmsrcsection" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
									  <input type="hidden" name="hidden_summary_id" value="<%=oidSummary%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td valign="top" colspan="2"> 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2" >
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="8%">&nbsp;</td>
                                                <td width="89%">&nbsp;</td>
                                              </tr>
                                              <!--<tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="8%"> 
                                                  <div align="left">Period</div>
                                                </td>
                                                <td width="89%"> -->
												<%
												/*Vector perKey = new Vector(1,1);
												Vector perValue = new Vector(1,1);
												
												Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");												
												for(int i =0;i < listPeriod.size();i++)
												{
													Period period = (Period)listPeriod.get(i);
													perKey.add(period.getPeriod());
													perValue.add(""+period.getOID());																												
												}*/
												%>
                                            		<%//=ControlCombo.draw("periode",null,""+oidPeriod,perValue,perKey,"")%>
											  <!--	</td>												  
                                              </tr>-->
                                                   <!-- <tr> 
													 <td width="3%"></td>
													 <td width="3%"></td>
                                                      <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Section"></a>
                                                      <a href="javascript:cmdSearch()">Search 
                                                         for Additional</a> </td>
                                                    </tr>-->
											  <%
                                                if(listAdditional!= null || listAdditional.size() >  0 ){
											  %>
											   <tr> 
                                                <td width="3%"></td>
                                                <td width="8%">&nbsp;</td>
                                                <td width="89%"><%= drawList(iCommand,frmPayAdditional, payAdditional,listAdditional,oidSummary,oidPeriod)%>
												</td>
												</tr>
												
												<%
													}else {
												%>
												<tr> 
                                                <td width="3%"></td>
                                                <td width="8%">No data found....</td>
                                                <td width="89%">
												</td>
												</tr>
												<%
												}%>
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="8%"> 
                                                  <div align="left"></div>
                                                </td>
                                                <td width="89%"> 
                                                  <table border="0" cellpadding="0" cellspacing="0" width="151">
												  
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
										<table width="100%" border="0">
										<%
										if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmPayAdditional.errorSize()<1)){
    											if(privAdd){%>
										  <tr>
										  <td width="79">&nbsp;</td>
										  <td width="204"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
										 	<a href="javascript:cmdAdd()" class="command">Add 
											New Additional</a> </td>
											<td width="164">&nbsp;</td>
										  <td height="22" valign="middle" colspan="3" width="302"> 
											</td>
										  </tr>
										  <%}
                                            }%>
											<%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmPayAdditional.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                        <tr align="left" valign="top"> 
                                                <td> <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a> 
                                                  <a href="javascript:cmdSave()" class="command">Save 
                                                  Additional</a> </td>
                                                <td> <a href="javascript:cmdConfirmDelete()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnDel.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete"></a> 
                                                  <a href="javascript:cmdConfirmDelete()" class="command">Delete 
                                                  Additional</a> </td>
                                                <td> <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a> 
                                                  <a href="javascript:cmdBack()" class="command">Back 
                                                  to List Additional</a> </td>
										  </tr>
										<%}%>
										</table>

                                          </td>
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
<!-- #BeginEditable "script" --> <!-- #EndEditable --> 
<!-- #EndTemplate --></html>
