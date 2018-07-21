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
				rowx.add("Input Overtime Index");
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
				rowx.add("Input Overtime Index");
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
	ctrlist.dataFormat("Overtime","50%","0","0","center","left");
	
	ctrlist.dataFormat("<=From","25%","0","0","center","left");
	ctrlist.dataFormat("<To","25%","0","0","center","left");
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
<!-- End of JSP Block -->
<%
// request data from jsp page
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidOvertimeIdx = FRMQueryString.requestLong(request, "hidden_id_idx_overtime");

int iCommandIdx = FRMQueryString.requestCommand(request);
int startIdx = FRMQueryString.requestInt(request, "startIdx");
int prevCommandIdx = FRMQueryString.requestInt(request, "prev_commandIdx");
long oidOvt_Idx = FRMQueryString.requestLong(request, "hidden_ovt_idx");
int status = FRMQueryString.requestInt(request,"status");

// variable declaration
int recordToGet = 1;
String msgString = "";
int recordToGetIdx = 1;
String msgStringIdx = "";

int iErrCode = FRMMessage.NONE;
int iErrCodeIdx = FRMMessage.NONE;

CtrlOvt_Type ctrlOvt_Type = new CtrlOvt_Type(request);
ControlLine ctrLine = new ControlLine();

iErrCode = ctrlOvt_Type.action(iCommand , oidOvertimeIdx, request);
FrmOvt_Type frmOvt_Type = ctrlOvt_Type.getForm();
Ovt_Type ovt_Type = ctrlOvt_Type.getOvt_Type();
msgString =  ctrlOvt_Type.getMessage();

//untuk form dan control di index
CtrlOvt_Idx ctrlOvt_Idx = new CtrlOvt_Idx(request);
ControlLine ctrLineIdx = new ControlLine();

iErrCodeIdx = ctrlOvt_Idx.action(iCommandIdx , oidOvt_Idx);
FrmOvt_Idx frmOvt_Idx = ctrlOvt_Idx.getForm();
Ovt_Idx ovt_Idx = ctrlOvt_Idx.getOvt_Idx();
msgStringIdx =  ctrlOvt_Idx.getMessage();

// get records to display overtime tipe
String whereClause = "";
String orderClause = PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE];

//list untuk overtime index
String whereClauseIdx = "";
String orderClauseIdx = PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_HOUR_FROM];

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

//untuk list overtime index
int vectSizeIdx = PstOvt_Idx.getCount(whereClauseIdx);
if(iCommandIdx==Command.FIRST || iCommandIdx==Command.PREV || iCommandIdx==Command.NEXT || iCommandIdx==Command.LAST){
	startIdx = ctrlOvt_Idx.actionList(iCommandIdx, startIdx, vectSizeIdx, recordToGetIdx);
}

Vector listOvt_Idx = PstOvt_Idx.list(startIdx, recordToGetIdx, whereClauseIdx, orderClauseIdx);
if(listOvt_Idx.size()<1 && startIdx>0){
	 if(vectSizeIdx - recordToGetIdx>recordToGetIdx){
		 startIdx = startIdx - recordToGetIdx;
	 }else{
		 startIdx = 0 ;
		 iCommandIdx = Command.FIRST;
		 prevCommandIdx = Command.FIRST;
	 }
	 listOvt_Idx = PstOvt_Idx.list(startIdx, recordToGetIdx, whereClauseIdx, orderClauseIdx);
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
	document.frmpayrollsetup.submit();
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

<script language="JavaScript">
function cmdAddIdx(){
	document.frmovertimeIdx.hidden_ovt_idx.value="0";
	document.frmovertimeIdx.status.value="2";
	document.frmovertimeIdx.commandIdx.value="<%=Command.ADD%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=prevCommandIdx%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
}

function cmdAskIdx(oidOvt_Idx){
	document.frmovertimeIdx.hidden_ovt_idx.value=oidOvt_Idx;
	document.frmovertimeIdx.commandIdx.value="<%=Command.ASK%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=prevCommandIdx%>";
	document.frmovertimeIdx.action="jenis-pajak.jsp";
	document.frmovertimeIdx.submit();
}

function cmdConfirmDeleteIdx(oidOvt_Idx){
	document.frmovertimeIdx.hidden_ovt_idx.value=oidOvt_Idx;
	document.frmovertimeIdx.commandIdx.value="<%=Command.DELETE%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=prevCommandIdx%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
}

function cmdSaveIdx(){
	document.frmovertimeIdx.commandIdx.value="<%=Command.SAVE%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=prevCommandIdx%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
}

function cmdEditIdx(oidOvt_Idx){
	document.frmovertimeIdx.hidden_ovt_idx.value=oidOvt_Idx;
	document.frmovertimeIdx.commandIdx.value="<%=Command.EDIT%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=prevCommandIdx%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
}

function cmdCancelIdx(oidOvt_Idx){
	document.frmovertimeIdx.hidden_ovt_idx.value=oidOvt_Idx;
	document.frmovertimeIdx.commandIdx.value="<%=Command.EDIT%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=prevCommandIdx%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
}

function cmdBackIdx(){
	document.frmovertimeIdx.commandIdx.value="<%=Command.BACK%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
}

function cmdListFirst(){
	document.frmovertimeIdx.commandIdx.value="<%=Command.FIRST%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=Command.FIRST%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
}

function cmdListPrev(){
	document.frmovertimeIdx.commandIdx.value="<%=Command.PREV%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=Command.PREV%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
	}

function cmdListNext(){
	document.frmovertimeIdx.commandIdx.value="<%=Command.NEXT%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=Command.NEXT%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
}

function cmdListLast(){
	document.frmovertimeIdx.commandIdx.value="<%=Command.LAST%>";
	document.frmovertimeIdx.prev_commandIdx.value="<%=Command.LAST%>";
	document.frmovertimeIdx.action="ov-index.jsp";
	document.frmovertimeIdx.submit();
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
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                      </table>
                                    </form>
									
									<form name="frmovertimeIdx" method="post" action="">
									 <input type="hidden" name="commandIdx" value="<%=iCommandIdx%>">
                                      <input type="hidden" name="vectSizeIdx" value="<%=vectSizeIdx%>">
                                      <input type="hidden" name="startIdx" value="<%=startIdx%>">
                                      <input type="hidden" name="prev_commandIdx" value="<%=prevCommandIdx%>">
                                      <input type="hidden" name="hidden_ovt_idx" value="<%=oidOvt_Idx%>">
									  <input type="hidden" name="status" value="<%=status%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td class="listtitle">Overtime Index 
                                            for :</td>
                                        </tr>
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="1024" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                              <%
											try{
											%>
                                              <tr align="center"> 
											    <td width="8%" align="left"><%=drawListIndex(iCommandIdx, frmOvt_Idx, ovt_Idx, listOvt_Idx, oidOvt_Idx)%></td>
                                              </tr>
                                              <% 
											  }catch(Exception exc){ 
											  System.out.println("Err::::::"+exc.toString());
											  }%>
                                              <tr align="center"> 
                                                <td nowrap align="left"> <%
												 int cmdIdx = 0;
												   if ((iCommandIdx == Command.FIRST || iCommandIdx == Command.PREV )|| 
													(iCommandIdx == Command.NEXT || iCommandIdx == Command.LAST))
														cmdIdx =iCommandIdx; 
												   else{
													  if(iCommandIdx == Command.NONE || prevCommandIdx == Command.NONE)
														cmdIdx = Command.FIRST;
													  else{
															if((iCommandIdx == Command.SAVE) && (iErrCodeIdx == FRMMessage.NONE))
																cmdIdx = PstOvt_Idx.findLimitCommand(startIdx,recordToGetIdx,vectSizeIdx);
															else									 
																cmdIdx = prevCommandIdx;
													  }  
												   } 
												%> <% ctrLineIdx.setLocationImg(approot+"/images");
												ctrLineIdx.initDefault();
												 %> <%=ctrLineIdx.drawImageListLimit(cmdIdx,vectSizeIdx,startIdx,recordToGetIdx)%> </td>
                                              </tr>
                                              <tr align="center"> 
                                                <td align="left"> <table>
                                                    <tr> 
                                                      <%if((iCommandIdx != Command.ADD && iCommandIdx != Command.ASK && iCommandIdx != Command.EDIT)&& (frmOvt_Idx.errorSize()<1)){%>
                                                      <td colspan="2" valign="middle"> 
                                                        <a href="javascript:cmdAddIdx()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> 
                                                      </td>
                                                      <td width="261" colspan="2" valign="middle"> 
                                                        <a href="javascript:cmdAddIdx()" class="command">Add 
                                                        New Overtime Index</a></td>
                                                      <%}%>
                                                    </tr>
                                                  </table></td>
                                              </tr>
                                              <tr align="center">
                                                <td align="left">
												<table>
												<tr> 
												<%
												   if((iCommandIdx == Command.ADD || iCommandIdx == Command.EDIT)){
												%>
												  <td colspan="2" valign="middle"> <a href="javascript:cmdSaveIdx()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a>
													</td>
													<td width="267" colspan="2" valign="middle"> 
													  <a href="javascript:cmdSaveIdx()" class="command" >Save 
													  Overtime Index</a></td>
													<td colspan="2" valign="middle"><a href="javascript:cmdBackIdx()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a>
													</td>
													<td width="267" colspan="2" valign="middle"><a href="javascript:cmdBackIdx()" class="command" >Back 
													  to List Overtime Index</a></td>
												</tr>
												<%}%>
											  </table>
												</td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle">Overtime Index 
                                            for : <font size="3">WD1 </font></td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <table width="500" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                              <tr align="center"> 
                                                <td colspan="2" class="listgentitle"><a name="OVINDEX"></a>Overtime 
                                                  Lenght </td>
                                                <td width="28%" class="listgentitle">Overtime</td>
                                              </tr>
                                              <tr align="center"> 
                                                <td width="8%" class="listgentitle">&lt;= 
                                                  From </td>
                                                <td width="8%" class="listgentitle">&lt; 
                                                  To </td>
                                                <td width="28%" class="listgentitle">Indexes</td>
                                              </tr>
                                              <tr> 
                                                <td width="8%" nowrap align="center">1</td>
                                                <td width="8%" nowrap align="center"> 
                                                  2 </td>
                                                <td width="28%" align="center"> 
                                                  1.5</td>
                                              </tr>
                                              <tr> 
                                                <td width="8%" nowrap align="center">2</td>
                                                <td width="8%" nowrap align="center">Up</td>
                                                <td width="28%" align="center">2</td>
                                              </tr>
                                              <tr> 
                                                <td width="8%" nowrap align="center"> 
                                                  <input type="text" name="textfield42" size="8">
                                                </td>
                                                <td width="8%" nowrap align="center"> 
                                                  <input type="text" name="textfield3" size="10">
                                                </td>
                                                <td width="28%" align="center"> 
                                                  <input type="text" name="textfield22" size="24">
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
