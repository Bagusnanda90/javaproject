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
privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>


<!-- JSP Block -->
<%!
public String drawList(int iCommand, FrmOvt_Type frmObject, Ovt_Type objEntity, Vector objectClass, long idOvt_Type){
	String result = "";

	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("80%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.dataFormat("Kode","5%","2","0","center","left");
	ctrlist.dataFormat("Nama","15%","2","0","center","left");
	ctrlist.dataFormat("Description","20%","2","0","center","left");
	ctrlist.dataFormat("Normal Work","30%","0","2","center","left");
	ctrlist.dataFormat("Overwritten","10%","2","0","center","left");
	ctrlist.dataFormat("","20%","2","0","center","left");
	
	ctrlist.dataFormat("Start Time","15%","0","0","center","left");
	ctrlist.dataFormat("End Time","15%","0","0","center","left");
	
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	//untuk mengambil name overtime
	Vector vKeyNmOvertime = new Vector();
    Vector vValNmOvertime = new Vector();
    vKeyNmOvertime.add(PstOvt_Type.WORKING_DAY+"");
    vKeyNmOvertime.add(PstOvt_Type.HOLIDAY+"");
    vValNmOvertime.add(PstOvt_Type.nameOvt[PstOvt_Type.WORKING_DAY]);
    vValNmOvertime.add(PstOvt_Type.nameOvt[PstOvt_Type.HOLIDAY]);
	
	
	Vector vKeyBy_Schedule = new Vector();
    Vector vValBy_Schedule= new Vector();
    vKeyBy_Schedule.add(PstOvt_Type.BY_SCHEDULE+"");
    vValBy_Schedule.add("");
	
	ControlCheckBox cb = new ControlCheckBox();
	
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			Ovt_Type ovt_Type = (Ovt_Type)objectClass.get(i);
			if(idOvt_Type == ovt_Type.getOID()){
			  index = i;
			}

			rowx = new Vector();
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_OVT_TYPE_CODE] +"\" value=\""+ovt_Type.getOvt_Type_Code()+"\" class=\"formElemen\" size=\"10\">");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_TYPE_OF_DAY], null, ""+ovt_Type.getType_of_day(),vKeyNmOvertime,vValNmOvertime));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_DESCRIPTION] +"\" value=\""+ovt_Type.getDescription()+"\" class=\"formElemen\" size=\"20\">");
				rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_HOUR_BEGIN], ovt_Type.getStd_work_hour_begin() != null ? ovt_Type.getStd_work_hour_begin() : new Date(),"formElemen", 24, 1, 0));
				rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_HOUR_END], ovt_Type.getStd_work_hour_end() != null ? ovt_Type.getStd_work_hour_end() : new Date(),"formElemen", 24, 1, 0));
				Vector vCheck = new Vector();
                vCheck.add(ovt_Type.getOwrite_by_schdl()+"");
				rowx.add(cb.draw(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_OWRITE_BY_SCHDL],vKeyBy_Schedule,vValBy_Schedule,vCheck));
				rowx.add("<a href=\"javascript:viewOvertimeIndex('"+ovt_Type.getOvt_Type_Code()+"')\">Input Overtime Index</a>");
			}else{
			    String str_dt_Hour_begin = ""; 
				String str_dt_Hour_end = ""; 
			    Date dt_Hour_begin = ovt_Type.getStd_work_hour_begin();
				Date dt_Hour_end = ovt_Type.getStd_work_hour_end();
				str_dt_Hour_begin =  Formater.formatDate(dt_Hour_begin, "HH:mm"); 
				str_dt_Hour_end = Formater.formatDate(dt_Hour_end, "HH:mm"); 
				
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(ovt_Type.getOID())+"')\">"+ovt_Type.getOvt_Type_Code()+"</a>");
				rowx.add(String.valueOf(PstOvt_Type.nameOvt[ovt_Type.getType_of_day()]));
				rowx.add(ovt_Type.getDescription());
				rowx.add(str_dt_Hour_begin);
				rowx.add(str_dt_Hour_end);
				rowx.add(String.valueOf(PstOvt_Type.schedule[ovt_Type.getOwrite_by_schdl()]));
				rowx.add("<a href=\"javascript:viewOvertimeIndex('"+ovt_Type.getOvt_Type_Code()+"')\">Input Overtime Index</a>");
			}
			lstData.add(rowx);
		}
		rowx = new Vector();

		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
		System.out.println("input ke add1 ");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_OVT_TYPE_CODE] +"\" value=\""+objEntity.getOvt_Type_Code()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_TYPE_OF_DAY], null, ""+objEntity.getType_of_day(),vKeyNmOvertime,vValNmOvertime));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_DESCRIPTION] +"\" value=\""+objEntity.getDescription()+"\" class=\"formElemen\" size=\"20\">");
			rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_HOUR_BEGIN], objEntity.getStd_work_hour_begin() != null ? objEntity.getStd_work_hour_begin() : new Date(),"formElemen", 24, 1, 0));
			rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_HOUR_END], objEntity.getStd_work_hour_end() != null ? objEntity.getStd_work_hour_end() : new Date(),"formElemen", 24, 1, 0));
			Vector vCheck = new Vector();
			vCheck.add(objEntity.getOwrite_by_schdl()+"");
			rowx.add(cb.draw(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_OWRITE_BY_SCHDL],vKeyBy_Schedule,vValBy_Schedule,vCheck));
			rowx.add("Input Overtime Index");
		}
		lstData.add(rowx);
		result = ctrlist.drawMeList();
	}else{
		if(iCommand==Command.ADD){
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_OVT_TYPE_CODE] +"\" value=\""+objEntity.getOvt_Type_Code()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_TYPE_OF_DAY], null, ""+objEntity.getType_of_day(),vKeyNmOvertime,vValNmOvertime));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_DESCRIPTION] +"\" value=\""+objEntity.getDescription()+"\" class=\"formElemen\" size=\"20\">");
			rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_HOUR_BEGIN], objEntity.getStd_work_hour_begin() != null ? objEntity.getStd_work_hour_begin() : new Date(),"formElemen", 24, 1, 0));
			rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_HOUR_END], objEntity.getStd_work_hour_end() != null ? objEntity.getStd_work_hour_end() : new Date(),"formElemen", 24, 1, 0));
			Vector vCheck = new Vector();
			vCheck.add(objEntity.getOwrite_by_schdl()+"");
			rowx.add(cb.draw(frmObject.fieldNames[FrmOvt_Type.FRM_FIELD_OWRITE_BY_SCHDL],vKeyBy_Schedule,vValBy_Schedule,vCheck));
			rowx.add("Input Overtime Index");
			lstData.add(rowx);
			result = ctrlist.drawMeList();
			
		}else{
			result = "<i>Belum ada data dalam sistem ...</i>";
		}
	}
	return result;
}
%>
<!-- End of JSP Block -->
<%
// request data from jsp page
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidOvertimeIdx = FRMQueryString.requestLong(request, "hidden_id_idx_overtime");
String overtime_code = FRMQueryString.requestString(request, "hidden_overtime_code");
// variable declaration
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

CtrlOvt_Type ctrlOvt_Type = new CtrlOvt_Type(request);
ControlLine ctrLine = new ControlLine();

iErrCode = ctrlOvt_Type.action(iCommand , oidOvertimeIdx, request);
FrmOvt_Type frmOvt_Type = ctrlOvt_Type.getForm();
Ovt_Type ovt_Type = ctrlOvt_Type.getOvt_Type();
msgString =  ctrlOvt_Type.getMessage();


// get records to display overtime tipe
String whereClause = "";
String orderClause = PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE];

//untuk list overtime type
int vectSize = PstOvt_Type.getCount(whereClause);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlOvt_Type.actionList(iCommand, start, vectSize, recordToGet);
}

Vector listOvt_Type = PstOvt_Type.list(start, recordToGet, whereClause , orderClause);
if(listOvt_Type.size()<1 && start>0){
	 if(vectSize - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listOvt_Type = PstOvt_Type.list(start, recordToGet, whereClause , orderClause);
}
%>
<%
int idx = FRMQueryString.requestInt(request, "idx");
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<script language="JavaScript">
function cmdAdd(){
	document.frmovertime.hidden_id_idx_overtime.value="0";
	document.frmovertime.command.value="<%=Command.ADD%>";
	document.frmovertime.prev_command.value="<%=prevCommand%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
}

function cmdAsk(oidOvertimeIdx){
	document.frmovertime.hidden_id_idx_overtime.value=oidOvertimeIdx;
	document.frmovertime.command.value="<%=Command.ASK%>";
	document.frmovertime.prev_command.value="<%=prevCommand%>";
	document.frmovertime.action="jenis-pajak.jsp";
	document.frmovertime.submit();
}

function cmdConfirmDelete(oidOvertimeIdx){
	document.frmovertime.hidden_id_idx_overtime.value=oidOvertimeIdx;
	document.frmovertime.command.value="<%=Command.DELETE%>";
	document.frmovertime.prev_command.value="<%=prevCommand%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
}

function cmdSave(){
	document.frmovertime.command.value="<%=Command.SAVE%>";
	document.frmovertime.prev_command.value="<%=prevCommand%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
}

function cmdEdit(oidOvertimeIdx){
	document.frmovertime.hidden_id_idx_overtime.value=oidOvertimeIdx;
	document.frmovertime.command.value="<%=Command.EDIT%>";
	document.frmovertime.prev_command.value="<%=prevCommand%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
}

function cmdCancel(oidOvertimeIdx){
	document.frmovertime.hidden_id_idx_overtime.value=oidOvertimeIdx;
	document.frmovertime.command.value="<%=Command.EDIT%>";
	document.frmovertime.prev_command.value="<%=prevCommand%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
}

function cmdBack(){
	document.frmovertime.command.value="<%=Command.BACK%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
}

function cmdListFirst(){
	document.frmovertime.command.value="<%=Command.FIRST%>";
	document.frmovertime.prev_command.value="<%=Command.FIRST%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
}

function cmdListPrev(){
	document.frmovertime.command.value="<%=Command.PREV%>";
	document.frmovertime.prev_command.value="<%=Command.PREV%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
	}

function cmdListNext(){
	document.frmovertime.command.value="<%=Command.NEXT%>";
	document.frmovertime.prev_command.value="<%=Command.NEXT%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
}

function cmdListLast(){
	document.frmovertime.command.value="<%=Command.LAST%>";
	document.frmovertime.prev_command.value="<%=Command.LAST%>";
	document.frmovertime.action="ov-index.jsp";
	document.frmovertime.submit();
}

function viewOvertimeIndex(overtime_code)
{
   window.open("input_ovt_index.jsp?hidden_overtime_code="+overtime_code+"", "group", "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
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
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Overtime 
                  Index <!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frmovertime" method="post" action="">
									 <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_id_idx_overtime" value="<%=oidOvertimeIdx%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td class="listtitle">Overtime Type</td>
                                        </tr>
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="1024" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                              <%
											try{
											%>
                                              <tr align="center"> 
                                                <td width="8%" align="left"><%=drawList(iCommand, frmOvt_Type, ovt_Type, listOvt_Type, oidOvertimeIdx)%></td>
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
																cmd = PstOvt_Type.findLimitCommand(start,recordToGet,vectSize);
															else									 
																cmd =prevCommand;
													  }  
												   } 
												%> <% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												 %> <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </td>
                                              </tr>
                                              <tr align="center"> 
                                                <td align="left"> <table>
                                                    <tr> 
                                                      <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmOvt_Type.errorSize()<1)){%>
                                                      <td colspan="2" valign="middle"> 
                                                        <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> 
                                                      </td>
                                                      <td width="261" colspan="2" valign="middle"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add 
                                                        New Overtime Type</a></td>
                                                      <%}%>
                                                    </tr>
                                                  </table></td>
                                              </tr>
                                              <tr align="center">
                                                <td align="left">
												<table>
												<tr> 
												<%
												   if((iCommand == Command.ADD || iCommand == Command.EDIT)){
												%>
												  <td colspan="2" valign="middle"> <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a>
													</td>
													<td width="267" colspan="2" valign="middle"> 
													  <a href="javascript:cmdSave()" class="command" >Save 
													  Overtime Type</a></td>
													<td colspan="2" valign="middle"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a>
													</td>
													<td width="267" colspan="2" valign="middle"><a href="javascript:cmdBack()" class="command" >Back 
													  to List Overtime Type</a></td>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
