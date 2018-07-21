
<% 
/* 
 * Page Name  		:  position.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: gadnyana
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.session.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

public String drawList(Vector objectClass ,  long lockerId, int start){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No","5%");
        ctrlist.addHeader("Locker Number","8%");
        ctrlist.addHeader("Location","20%");
        ctrlist.addHeader("Key Number","15%");
        ctrlist.addHeader("Spare Key","10%");
        ctrlist.addHeader("Condition","10%");
        ctrlist.addHeader("Employee","37%");

        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        Hashtable hLockerLoc = new Hashtable();
        Vector vTempLoc = new Vector(1,1);
        vTempLoc = PstLockerLocation.listAll();
        for(int i=0;i<vTempLoc.size();i++){
            LockerLocation lLoc = new LockerLocation();
            lLoc = (LockerLocation)vTempLoc.get(i);
            hLockerLoc.put(""+lLoc.getOID(), lLoc);
        }
        
        Hashtable hLockerCon = new Hashtable();
        Vector vTempCon = new Vector(1,1);
        vTempCon = PstLockerCondition.listAll();
        for(int i=0;i<vTempCon.size();i++){
            LockerCondition lCon = new LockerCondition();
            lCon = (LockerCondition)vTempCon.get(i);
            hLockerCon.put(""+lCon.getOID(), lCon);
        }
        int index = -1;
        for (int i = 0; i < objectClass.size(); i++) 
        {
            Vector vect = (Vector)objectClass.get(i);
            Locker locker = (Locker) vect.get(0);
            Vector vEmployee = new Vector(1,1);
            vEmployee = (Vector)vect.get(1);
 
            if(lockerId == locker.getOID())
                     index = i;
            
            Vector rowx = new Vector(); 
            rowx.add(String.valueOf((start+i+1)));
            
            rowx.add(locker.getLockerNumber());
	
            LockerLocation lLoc = new LockerLocation();
            LockerCondition lCon = new LockerCondition();
            try{
                lLoc = (LockerLocation)hLockerLoc.get(""+locker.getLocationId());
                lCon = (LockerCondition)hLockerCon.get(""+locker.getConditionId());
            }catch(Exception ex){}
            rowx.add(lLoc.getLocation());
            rowx.add(locker.getKeyNumber());
            rowx.add(locker.getSpareKey());
            rowx.add((lCon!=null?lCon.getCondition():""));
            rowx.add(createEmp(vEmployee));

            lstData.add(rowx);
            String strLink  = locker.getLocationId()+"','"+locker.getLockerNumber()+"','"+locker.getOID();
            lstLinkData.add(strLink);
        }
        return ctrlist.draw(index);
}


private String createEmp(Vector vEmp){
    Hashtable hDep = new Hashtable();
    Vector vTempDep = new Vector(1,1);
    vTempDep = PstDepartment.listAll();
    for(int i=0;i<vTempDep.size();i++){
        Department dep = new Department();
        dep = (Department)vTempDep.get(i);
        hDep.put(""+dep.getOID(), dep);
    }
    
    String strEmp = "";
    for(int i=0;i<vEmp.size();i++){
        if(strEmp.length()>0){
           strEmp+="<br>"; 
        }
        Employee emp = new Employee();
        Department dep = new Department();
        try{
            emp = (Employee)vEmp.get(i);
            dep = (Department)hDep.get(""+emp.getDepartmentId());
            strEmp += "("+emp.getEmployeeNum()+") "+emp.getFullName()+" - "+dep.getDepartment();
        }catch(Exception ex){}
    }
    return strEmp;
}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidLocker = FRMQueryString.requestLong(request, "hidden_locker_id");
int indexLocker = FRMQueryString.requestInt(request, "indexLocker");
String formName = FRMQueryString.requestString(request, "formName");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
String strMsgError = "";
int iErrCode = FRMMessage.NONE;

boolean isLockerUsed = true;
try{ 
    String strIsCheck = PstSystemProperty.getValueByName("LOCKER_MANY_USER");
    if(strIsCheck.equals("0")){
        isLockerUsed = false;
    }
}catch(Exception ex){}
///////////////// Aturan penampilan data

CtrlLocker ctrlLocker = new CtrlLocker(request);
ControlLine ctrLine = new ControlLine();
Vector listLocker = new Vector(1,1);

/*switch statement */
//////////////// Aturan input data
long locationId = FRMQueryString.requestLong(request, FrmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCATION_ID]);
String lockerNum = FRMQueryString.requestString(request, FrmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCKER_NUMBER]);
if(PstLocker.isLockerNumberUsed(locationId,lockerNum,oidLocker) && iCommand == Command.SAVE){
    strMsgError = "Locker telah ada sebelumnya!";
    System.out.println(strMsgError);
}else{
    iErrCode = ctrlLocker.action(iCommand , oidLocker);
}    
/* end switch*/
FrmLocker frmLocker = ctrlLocker.getForm();

//SRC
SrcLocker srcLocker = new SrcLocker();
FrmSrcLocker frmSrcLocker = new FrmSrcLocker(request, srcLocker);
frmSrcLocker.requestEntityObject(srcLocker);

/*count list All Position*/
Vector VListAll = new Vector(1,1);
try{
    VListAll = SessLocker.listLockerEmp(0, 0, srcLocker, isLockerUsed);
}catch(Exception ex){}
int vectSize = VListAll.size();

Locker locker = ctrlLocker.getLocker();
msgString =  ctrlLocker.getMessage();
 
/*switch list Locker*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstLocker.findLimitStart(locker.getOID(),recordToGet, whereClause);
	oidLocker = locker.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlLocker.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */

listLocker = SessLocker.listLockerEmp(start, recordToGet, srcLocker, isLockerUsed);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listLocker.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listLocker = SessLocker.listLockerEmp(start, recordToGet, srcLocker, isLockerUsed);
}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Locker</title>
<script language="JavaScript">

function cmdEdit(lockerLoc,LockerNum,lockerId){
        self.opener.document.frm_employee.LOCKER_LOCATION.value = lockerLoc;
        self.opener.document.frm_employee.LOCKER_NUMBER_POS.value = LockerNum;
        self.opener.document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LOCKER_ID]%>.value = lockerId;
        self.close();
}

function cmdAdd(){
	document.frmlocker.hidden_locker_id.value="0";
	document.frmlocker.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
}

function cmdSearch(){
	document.frmlocker.command.value="<%=""+Command.LIST%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
}

function cmdAsk(oidLocker){
	document.frmlocker.hidden_locker_id.value=oidLocker;
	document.frmlocker.command.value="<%=String.valueOf(Command.ASK)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
}

function cmdConfirmDelete(oidLocker){
	document.frmlocker.hidden_locker_id.value=oidLocker;
	document.frmlocker.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
}
function cmdSave(){
	document.frmlocker.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
	}

function cmdEditOrg(oidLocker){
	document.frmlocker.hidden_locker_id.value=oidLocker;
	document.frmlocker.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
	}

function cmdCancel(oidLocker){
	document.frmlocker.hidden_locker_id.value=oidLocker;
	document.frmlocker.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
}

function cmdBack(){
	document.frmlocker.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
	}

function cmdListFirst(){
	document.frmlocker.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
}

function cmdListPrev(){
	document.frmlocker.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
	}

function cmdListNext(){
	document.frmlocker.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
}

function cmdListLast(){
	document.frmlocker.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmlocker.prev_command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmlocker.action="srcLocker.jsp";
	document.frmlocker.submit();
}

function fnTrapKD(){
	//alert(event.keyCode);
	switch(event.keyCode) {
		case <%=String.valueOf(LIST_PREV)%>:
			cmdListPrev();
			break;
		case <%=String.valueOf(LIST_NEXT)%>:
			cmdListNext();
			break;
		case <%=String.valueOf(LIST_FIRST)%>:
			cmdListFirst();
			break;
		case <%=String.valueOf(LIST_LAST)%>:
			cmdListLast();
			break;
		default:
			break;
	}
}

//-------------- script control line -------------------
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
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
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

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
                  Master Data &gt; Locker<!-- #EndEditable -->
            </strong></font>
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmlocker" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=""+iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=""+vectSize%>">
                                      <input type="hidden" name="start" value="<%=""+start%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="hidden_locker_id" value="<%=""+oidLocker%>">
                                      <input type="hidden" name="indexLocker" value="<%=""+indexLocker%>">
                                      <input type="hidden" name="formName" value="<%=formName%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                          <tr align="left" valign="top"> 
                                        <td height="8"  colspan="3">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">Locker List </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="20%">Location</td>
                                          <td height="21" valign="top" width="3%">:</td>
                                          <td height="21" width="87%"> 
                                            <%
                                                Vector locationid_value = new Vector(1,1); 
                                                Vector locationid_key = new Vector(1,1); 
                                                locationid_key.add("All Location");
                                                locationid_value.add("0");
                                                    String selectVal = String.valueOf(srcLocker.getLocation()); 
                                                    Vector listLockerLocation = new Vector(1,1);
                                                    listLockerLocation = PstLockerLocation.listAll();
                                                    for (int i = 0; i < listLockerLocation.size(); i++) {
                                                        LockerLocation lockerLocation = (LockerLocation) listLockerLocation.get(i);
                                                        locationid_key.add(lockerLocation.getLocation());
                                                        locationid_value.add(String.valueOf(lockerLocation.getOID()));
                                                    }
                                          %>
                                            <%= ControlCombo.draw(frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_LOCATION], null, selectVal, locationid_value, locationid_key) %> 
                                                </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="20%">Locker Number</td>
                                          <td height="21" valign="top" width="3%">:</td>
                                          <td height="21" width="87%"> 
                                            <input type="text" name="<%=frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_LOCKERNUMBER] %>"  value="<%= srcLocker.getLockernumber() %>" class="elemenForm">
                                                </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="20%">Locker Key</td>
                                          <td height="21" valign="top" width="3%">:</td>
                                          <td height="21" width="87%"> 
                                            <input type="text" name="<%=frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_LOCKERKEY] %>"  value="<%= srcLocker.getLockerkey() %>" class="elemenForm">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="20%">Spare Key</td>
                                          <td height="21" valign="top" width="3%">:</td>
                                          <td height="21" width="87%"> 
                                            <input type="text" name="<%=frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_SPAREKEY] %>"  value="<%= (srcLocker.getSparekey() == null) ? "" : srcLocker.getSparekey() %>" class="elemenForm">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="20%">Condition</td>
                                          <td height="21" valign="top" width="3%">:</td>
                                          <td height="21" width="87%"> 
                                            <%-- <input type="text" name="<%=frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_CONDITION] %>"  value="<%= srcLocker.getCondition() %>" class="elemenForm">
                                            --%>
                                            <%
                                                    Vector conditionid_value = new Vector(1,1); 
                                                    Vector conditionid_key = new Vector(1,1); 
                                                    conditionid_key.add("All Condition");
                                                    conditionid_value.add("-1");
                                                    String selectVal2 = String.valueOf(srcLocker.getCondition()); 
                                                    Vector listLockerCondition = new Vector(1,1);
                                                    listLockerCondition = PstLockerCondition.listAll();
                                                    for (int i = 0; i < listLockerCondition.size(); i++) {
                                                        LockerCondition lockerCondition = (LockerCondition) listLockerCondition.get(i);
                                                        conditionid_key.add(lockerCondition.getCondition());
                                                        conditionid_value.add(String.valueOf(lockerCondition.getOID()));
                                                    }
                                              %>
                                            <%= ControlCombo.draw(frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_CONDITION], null, selectVal2, conditionid_value, conditionid_key) %> 
                                          </td>
                                        </tr>
                                              <%
                                            try{
                                                    if (listLocker.size()>0){
                                            %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listLocker,oidLocker,start)%>
                                                </td>
                                              </tr>
                                              <%  } 
						  }catch(Exception exc){ 
						  }%>
                                              <tr align="left" valign="top"> 
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
									  	cmd =prevCommand; 
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                              <%
                                          if(strMsgError.length()>0){
                                      %>
                                      <tr>
                                          <td colspan="3">
                                              <font color="red"><%=strMsgError%></font>
                                          </td>
                                      </tr>
                                      <%}%>
											<%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmLocker.errorSize()<1)){
											   if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmLocker.errorSize()<1)){
											   if(privAdd){%>
                                              <tr align="left" valign="top"> 
											  	<td>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                     <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                     <td width="8"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Locker"></a></td>
                                                     <td width="4"><img src="<%=approot%>/images/spacer.gif" width="2" height="8"></td>
                                                     <td width="110" class="command" nowrap><a href="javascript:cmdSearch()">Search for Locker</a></td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add New Locker</a> </td>
                                                    </tr>
                                                  </table>
												</td>
                                              </tr>
											  <%}
											  }%>
                                            </table>
                                          </td>
                                        </tr>
                                            <tr>
                                                    <td>&nbsp;
                                                    </td>
                                            </tr>
                                     
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmLocker.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidLocker == 0?"Add":"Edit"%> Locker</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">&nbsp;</td>
                                                      <td valign="top" width="17%">&nbsp;</td>
                                                      <td width="83%" class="comment">*)entry required </td>
                                                    </tr>
                                                    <tr align="left"> 
                                          <td height="21" valign="top" width="20%">Location</td>
                                          <td width="2%"  valign="top"  >:</td>
                                          <td height="21" valign="top" width="78%">
                                            <% 
                                                Vector locationid_value2 = new Vector(1,1);
                                                Vector locationid_key2 = new Vector(1,1);
                                                String sel_locationid2 = ""+locker.getLocationId();
                                                //String sel_locationid = "" + sessLocker.getLocationId();
                                                Vector listLockerLocation2 = new Vector(1,1);
                                                listLockerLocation2 = PstLockerLocation.listAll();
                                                for (int i = 0; i < listLockerLocation2.size(); i++) {
                                                        LockerLocation lockerLocation = (LockerLocation) listLockerLocation.get(i);
                                                        locationid_key2.add(lockerLocation.getLocation());
                                                        locationid_value2.add(String.valueOf(lockerLocation.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(FrmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCATION_ID],null, sel_locationid2, locationid_value2, locationid_key2) %> 
                                            * <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_LOCATION_ID)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" valign="top" width="20%">Locker 
                                          <td height="21" valign="top" width="2%">:</td>
                                            Number</td>
                                          <td height="21" valign="top" width="78%">
                                            <input type="text" name="<%=FrmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCKER_NUMBER]%>" value="<%=locker.getLockerNumber()%>" class="formElemen">
                                            * <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_LOCKER_NUMBER)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" valign="top" width="20%">Key 
                                          <td height="21" valign="top" width="2%">:</td>
                                            Number</td>
                                          <td height="21" valign="top" width="78%">
                                            <input type="text" name="<%=FrmLocker.fieldNames[FrmLocker.FRM_FIELD_KEY_NUMBER]%>" value="<%=locker.getKeyNumber()%>" class="formElemen">
                                            <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_KEY_NUMBER)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" valign="top" width="20%">Spare 
                                          <td height="21" valign="top" width="2%">:</td>
                                            Key</td>
                                          <td height="21" valign="top" width="78%">
                                            <input type="text" name="<%=FrmLocker.fieldNames[FrmLocker.FRM_FIELD_SPARE_KEY]%>" value="<%=(locker.getSpareKey()==null)? "" : locker.getSpareKey()%>" class="formElemen">
                                            <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_SPARE_KEY)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" valign="top" width="20%">Condition</td>
                                          <td height="21" valign="top" width="2%">:</td>
                                          <td height="21" valign="top" width="78%">
                                            <% 
                                                Vector conditionid_value2 = new Vector(1,1);
                                                Vector conditionid_key2 = new Vector(1,1);
                                                String sel_conditionid2 = ""+locker.getConditionId();
                                                //String sel_conditionid = "" + sessLocker.getConditionId();
                                                Vector listLockerCondition2 = new Vector(1,1);
                                                listLockerCondition2 = PstLockerCondition.listAll();
                                                for (int i = 0; i < listLockerCondition2.size(); i++) {
                                                        LockerCondition lockerCondition = (LockerCondition) listLockerCondition.get(i);
                                                        conditionid_key2.add(lockerCondition.getCondition());
                                                        conditionid_value2.add(String.valueOf(lockerCondition.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(FrmLocker.fieldNames[FrmLocker.FRM_FIELD_CONDITION_ID],null, sel_conditionid2, conditionid_value2, conditionid_key2) %> 
                                            * <%=frmLocker.getErrorMsg(FrmLocker.FRM_FIELD_CONDITION_ID)%></td>
                                        </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              
                                              <tr> 
                                                <td colspan="2"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "";//"javascript:cmdAsk('"+oidLocker+"')";
									String sconDelCom = "";//"javascript:cmdConfirmDelete('"+oidLocker+"')";
									String scancel = "";//"javascript:cmdEdit('"+oidLocker+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setBackCaption("Back to List Locker");
									ctrLine.setSaveCaption("Save Locker");
									ctrLine.setConfirmDelCaption("Yes Delete Locker");
									ctrLine.setDeleteCaption("Delete Locker");

									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
                                                                                ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
                                                                                ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}
									
									if(iCommand == Command.ASK){
										ctrLine.setDeleteQuestion(msgString);
                                                                        }
                                                                        ctrLine.setDeleteCaption("");
                                                                        ctrLine.setEditCaption("");
                                                                                        
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                    <!-- #EndEditable -->
                            </td>
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
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
