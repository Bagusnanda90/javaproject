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
<!-- JSP Block -->
<%!
public String drawListIndex(int iCommand, FrmOvt_Idx frmObject, Ovt_Idx objEntity, Vector objectClass, long idOvt_Idx){
	String result = "";

	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("40%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	
	ctrlist.dataFormat("Overtime Length","50%","0","2","center","left");
	ctrlist.dataFormat("Overtime","50%","1","0","center","left");
	
	ctrlist.dataFormat("< From","25%","0","0","center","left");
	ctrlist.dataFormat("<= To","25%","0","0","center","left");
	ctrlist.dataFormat("Indexes","50%","0","0","center","left");
	
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			Ovt_Idx ovt_Idx = (Ovt_Idx)objectClass.get(i);
			if(idOvt_Idx == ovt_Idx.getOID()){
			  index = i;
			}

			rowx = new Vector();
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Idx.FRM_FIELD_HOUR_FROM] +"\" value=\""+ovt_Idx.getHour_from()+"\" class=\"formElemen\" size=\"10\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Idx.FRM_FIELD_HOUR_TO] +"\" value=\""+ovt_Idx.getHour_to()+"\" class=\"formElemen\" size=\"20\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Idx.FRM_FIELD_OVT_IDX] +"\" value=\""+ovt_Idx.getOvt_idx()+"\" class=\"formElemen\" size=\"20\">");
			}else{
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(ovt_Idx.getOID())+"')\">"+ovt_Idx.getHour_from()+"</a>");
				rowx.add(String.valueOf(ovt_Idx.getHour_to()));
				rowx.add(String.valueOf(ovt_Idx.getOvt_idx()));
			}
			lstData.add(rowx);
		}
		rowx = new Vector();

		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
		    rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Idx.FRM_FIELD_HOUR_FROM] +"\" value=\""+objEntity.getHour_from()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Idx.FRM_FIELD_HOUR_TO] +"\" value=\""+objEntity.getHour_to()+"\" class=\"formElemen\" size=\"20\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Idx.FRM_FIELD_OVT_IDX] +"\" value=\""+objEntity.getOvt_idx()+"\" class=\"formElemen\" size=\"20\">");
		}
		lstData.add(rowx);
		result = ctrlist.drawMeList();
	}else{
		if(iCommand==Command.ADD){
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Idx.FRM_FIELD_HOUR_FROM] +"\" value=\""+objEntity.getHour_from()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Idx.FRM_FIELD_HOUR_TO] +"\" value=\""+objEntity.getHour_to()+"\" class=\"formElemen\" size=\"20\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Idx.FRM_FIELD_OVT_IDX] +"\" value=\""+objEntity.getOvt_idx()+"\" class=\"formElemen\" size=\"20\">");
			lstData.add(rowx);
			result = ctrlist.drawMeList();
			
		}else{
			result = "<i>Belum ada data dalam sistem ...</i>";
		}
	}
	return result;
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
String overtime_Code = FRMQueryString.requestString(request, "hidden_overtime_code");
long oidOvt_Idx = FRMQueryString.requestLong(request, "hidden_ovt_idx");

// variable declaration
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

CtrlOvt_Idx ctrlOvt_Idx = new CtrlOvt_Idx(request);
ControlLine ctrLine = new ControlLine();

iErrCode = ctrlOvt_Idx.action(iCommand , oidOvt_Idx, overtime_Code);
FrmOvt_Idx frmOvt_Idx = ctrlOvt_Idx.getForm();
Ovt_Idx ovt_Idx = ctrlOvt_Idx.getOvt_Idx();
msgString =  ctrlOvt_Idx.getMessage();


//list untuk overtime index
String whereClauseIdx = PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+" = '"+overtime_Code+"'";
String orderClauseIdx = PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_HOUR_FROM];


//untuk list overtime index
int vectSizeIdx = PstOvt_Idx.getCount(whereClauseIdx);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlOvt_Idx.actionList(iCommand, start, vectSizeIdx, recordToGet);
}

Vector listOvt_Idx = PstOvt_Idx.list(start, recordToGet, whereClauseIdx, orderClauseIdx);
if(listOvt_Idx.size()<1 && start>0){
	 if(vectSizeIdx - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listOvt_Idx = PstOvt_Idx.list(start, recordToGet, whereClauseIdx, orderClauseIdx);
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
<script language="JavaScript">
function cmdAdd(){
	document.frmovertimeIndex.hidden_ovt_idx.value="0";
	document.frmovertimeIndex.command.value="<%=Command.ADD%>";
	document.frmovertimeIndex.prev_command.value="<%=prevCommand%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
}

function cmdAsk(oidOvt_Idx){
	document.frmovertimeIndex.hidden_ovt_idx.value=oidOvt_Idx;
	document.frmovertimeIndex.command.value="<%=Command.ASK%>";
	document.frmovertimeIndex.prev_command.value="<%=prevCommand%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
}

function cmdConfirmDelete(oidOvt_Idx){
	document.frmovertimeIndex.hidden_ovt_idx.value=oidOvt_Idx;
	document.frmovertimeIndex.command.value="<%=Command.DELETE%>";
	document.frmovertimeIndex.prev_command.value="<%=prevCommand%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
}

function cmdSave(){
	document.frmovertimeIndex.command.value="<%=Command.SAVE%>";
	document.frmovertimeIndex.prev_command.value="<%=prevCommand%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
}

function cmdEdit(oidOvt_Idx){
	document.frmovertimeIndex.hidden_ovt_idx.value=oidOvt_Idx;
	document.frmovertimeIndex.command.value="<%=Command.EDIT%>";
	document.frmovertimeIndex.prev_command.value="<%=prevCommand%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
}

function cmdCancel(oidOvt_Idx){
	document.frmovertimeIndex.hidden_ovt_idx.value=oidOvt_Idx;
	document.frmovertimeIndex.command.value="<%=Command.EDIT%>";
	document.frmovertimeIndex.prev_command.value="<%=prevCommand%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
}

function cmdBack(){
	document.frmovertimeIndex.command.value="<%=Command.BACK%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
}

function cmdListFirst(){
	document.frmovertimeIndex.command.value="<%=Command.FIRST%>";
	document.frmovertimeIndex.prev_command.value="<%=Command.FIRST%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
}

function cmdListPrev(){
	document.frmovertimeIndex.command.value="<%=Command.PREV%>";
	document.frmovertimeIndex.prev_command.value="<%=Command.PREV%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
	}

function cmdListNext(){
	document.frmovertimeIndex.command.value="<%=Command.NEXT%>";
	document.frmovertimeIndex.prev_command.value="<%=Command.NEXT%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
}

function cmdListLast(){
	document.frmovertimeIndex.command.value="<%=Command.LAST%>";
	document.frmovertimeIndex.prev_command.value="<%=Command.LAST%>";
	document.frmovertimeIndex.action="input_ovt_index.jsp";
	document.frmovertimeIndex.submit();
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
      <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           
            <%}else{%>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
       
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
     
    </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Overtime 
                  Index <!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td > 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmovertimeIndex" method="post" action="">
									<input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSizeIdx" value="<%=vectSizeIdx%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_ovt_idx" value="<%=oidOvt_Idx%>">
									  <input type="hidden" name="hidden_overtime_code" value="<%=overtime_Code%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
									    <tr> 
                                          <td class="listtitle">Overtime Index for : <font size="3"><%=overtime_Code%></font></td>
                                        </tr>
										
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                              <%
											try{
											%>
                                              <tr align="center"> 
											    <td width="8%" align="left"><%=drawListIndex(iCommand, frmOvt_Idx, ovt_Idx, listOvt_Idx, oidOvt_Idx)%></td>
                                              </tr>
                                              <% 
											  }catch(Exception exc){ 
											  System.out.println("Err::::::"+exc.toString());
											  }%>
                                              <tr align="center"> 
                                                <td nowrap align="left"> <%
												 int cmd = 0;
												   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
													(iCommand == Command.NEXT || iCommand == Command.LAST))
														cmd =iCommand; 
												   else{
													  if(iCommand == Command.NONE || prevCommand == Command.NONE)
														cmd = Command.FIRST;
													  else{
															if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
																cmd = PstOvt_Idx.findLimitCommand(start,recordToGet,vectSizeIdx);
															else									 
																cmd = prevCommand;
													  }  
												   } 
												%> <% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												 %> <%=ctrLine.drawImageListLimit(cmd,vectSizeIdx,start,recordToGet)%>  </td>
                                              </tr>
                                              <tr align="center"> 
                                                <td align="left"> <table>
                                                    <tr> 
                                                      <%//if((iCommand != Command.ADD || iCommand != Command.ASK || iCommand != Command.EDIT)|| (frmOvt_Idx.errorSize()<1)){
													  if(iCommand==Command.NONE || iCommand==Command.BACK || iCommand == Command.DELETE || iCommand==Command.SAVE){
													  %>
                                                      <td colspan="2" valign="middle"> 
                                                        <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> 
                                                      </td>
                                                      <td width="100%" colspan="2" valign="middle"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add 
                                                        New Overtime Index</a></td>
                                                      <%}%>
                                                    </tr>
                                                  </table></td>
                                              </tr>
											  <tr align="center"> 
                                                <td align="left"> <table width="100%">
                                                    <tr> 
                                                      <%if(iCommand == Command.ASK){%>
                                                      <td colspan="5" valign="left" class="msgquestion"> 
                                                        Anda Yakin Menghapus Data?</td>
                                                     
                                                    </tr>
                                                    <tr> 
                                                      <td width="24" valign="middle"> 
                                                        <a href="javascript:cmdConfirmDelete('<%=oidOvt_Idx%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Add new data"></a> 
                                                      </td>
                                                      <td width="156" valign="middle"> 
                                                        <a href="javascript:cmdConfirmDelete('<%=oidOvt_Idx%>')" class="command"> 
                                                        Ya Hapus Data</a></td>
                                                      <td width="29" valign="middle"><a href="javascript:cmdConfirmDelete('<%=oidOvt_Idx%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Add new data"></a> </td>
                                                      <td width="207" valign="middle"><a href="javascript:cmdCancel()" class="command">Batal</a></td>
                                                      <td width="581" valign="middle"></td>
                                                    </tr>
													 <%}%>
                                                  </table></td>
                                              </tr>
                                              <tr align="center">
                                                <td align="left">
												<table width="713">
                                                    <tr> 
												<%
												 //if((iCommand == Command.ADD || iCommand == Command.EDIT)){
												 if((iCommand == Command.ADD || iCommand == Command.EDIT )){
												%>
                                                      <td colspan="2" valign="middle"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a> 
                                                      </td>
													<td colspan="2" valign="middle"> 
													  <a href="javascript:cmdSave()" class="command" >Save 
													  Overtime Index</a></td>
													
													<% if(iCommand==Command.EDIT)
													{%>
													<td colspan="2" valign="middle"> <a href="javascript:cmdAsk('<%=oidOvt_Idx%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Save"></a>
													</td>
													<td colspan="2" valign="middle"> 
													   <a href="javascript:cmdAsk('<%=oidOvt_Idx%>')" class="command" >Delete Overtime Index</a></td>
													<td colspan="2" valign="middle"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a>
													</td>
													<%}%>
													<td width="267" colspan="2" valign="middle"><a href="javascript:cmdBack()" class="command" >Back 
													  to List Overtime Index</a></td>
												</tr>
												<%}%>
											  </table>
												</td>
                                              </tr>
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
