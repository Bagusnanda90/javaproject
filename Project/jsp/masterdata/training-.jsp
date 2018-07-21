<% 
/* 
 * Page Name  		:  training.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = 0;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long trainingId, Vector vctDepartment)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("99%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Name","20%");
		ctrlist.addHeader("Description","30%");
		ctrlist.addHeader("Department","50%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
	
		//System.out.println(objectClass);	
		for (int i = 0; i < objectClass.size(); i++) {
			Training training = (Training)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(trainingId == training.getOID())
				 index = i;

			rowx.add(training.getName());

			rowx.add(training.getDescription());
			
			rowx.add(PstTraining.getTrainingDepartment(training.getOID(), vctDepartment));

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(training.getOID()));
		}
		System.out.println("_______________________ out");
		return ctrlist.draw(index);
	}
	
	public String chekOID(long oid, Vector vct){
		if(vct!=null && vct.size()>0){
			for(int i=0; i<vct.size(); i++){
				TrainingDept td = (TrainingDept)vct.get(i);
				if(td.getDepartmentId() == oid){
					return "checked";
				}
			}
		}
		return "";
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidTraining = FRMQueryString.requestLong(request, "hidden_training_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";


Vector vctDepartment = PstDepartment.list(0,0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);

CtrlTraining ctrlTraining = new CtrlTraining(request);
ControlLine ctrLine = new ControlLine();
Vector listTraining = new Vector(1,1);

String[] depid = request.getParameterValues("dep_id");

/*switch statement */
iErrCode = ctrlTraining.action(iCommand , oidTraining, depid);
/* end switch*/
FrmTraining frmTraining = ctrlTraining.getForm();

/*count list All Training*/
int vectSize = PstTraining.getCount(whereClause);

Training training = ctrlTraining.getTraining();
msgString =  ctrlTraining.getMessage();

String whereClause1 = PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID]+"="+training.getOID();
Vector vctTrDept = PstTrainingDept.list(0,0, whereClause1, null);

//out.println(vctTrDept);


/*switch list Training*/

/*if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
	start = PstTraining.generateFindStart(training.getOID(),recordToGet, whereClause);
*/

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlTraining.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listTraining = PstTraining.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listTraining.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listTraining = PstTraining.list(start,recordToGet, whereClause , orderClause);
}

//out.println(listTraining);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>harisma--</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmtraining.hidden_training_id.value="0";
	document.frmtraining.command.value="<%=Command.ADD%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdAsk(oidTraining){
	document.frmtraining.hidden_training_id.value=oidTraining;
	document.frmtraining.command.value="<%=Command.ASK%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdConfirmDelete(oidTraining){
	document.frmtraining.hidden_training_id.value=oidTraining;
	document.frmtraining.command.value="<%=Command.DELETE%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}
function cmdSave(){
	document.frmtraining.command.value="<%=Command.SAVE%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
	}

function cmdEdit(oidTraining){
	document.frmtraining.hidden_training_id.value=oidTraining;
	document.frmtraining.command.value="<%=Command.EDIT%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
	}

function cmdCancel(oidTraining){
	document.frmtraining.hidden_training_id.value=oidTraining;
	document.frmtraining.command.value="<%=Command.EDIT%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdBack(){
	document.frmtraining.command.value="<%=Command.BACK%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
	}

function cmdListFirst(){
	document.frmtraining.command.value="<%=Command.FIRST%>";
	document.frmtraining.prev_command.value="<%=Command.FIRST%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdListPrev(){
	document.frmtraining.command.value="<%=Command.PREV%>";
	document.frmtraining.prev_command.value="<%=Command.PREV%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
	}

function cmdListNext(){
	document.frmtraining.command.value="<%=Command.NEXT%>";
	document.frmtraining.prev_command.value="<%=Command.NEXT%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdListLast(){
	document.frmtraining.command.value="<%=Command.LAST%>";
	document.frmtraining.prev_command.value="<%=Command.LAST%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}


function setChecked(val) {
	dml=document.frmtraining;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {						
		dml.elements[i].checked = val;					
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
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../main/mnmain.jsp" %>
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
                  Master Data &gt; Recruitment &gt; Training Master Data<!-- #EndEditable --> 
                  </strong></font> </td>
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
                                    <form name="frmtraining" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_training_id" value="<%=oidTraining%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3">&nbsp; 
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Training 
                                                  Master List </td>
                                              </tr>
                                              <%
												try{
													if (listTraining!=null && listTraining.size()>0){
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
												
                                                  <%= drawList(listTraining, oidTraining, vctDepartment)%> 
												  
												  </td>
                                              </tr>
                                              <%  }else{
											  %>
											  <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
											  <span class="comment">&nbsp;List is empty ...</span>
											  </td></tr>
											  <%} 
											  }catch(Exception exc){ 
											  	out.println(exc.toString());
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
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">
												
												 <%if(iCommand!=Command.ADD && iCommand!=Command.ASK && iCommand!=Command.SAVE && iCommand!=Command.EDIT && iErrCode==FRMMessage.NONE){%>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Training Master</a></td>
                                                    </tr>
                                                  </table>
												  <%}%>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||iCommand==Command.SAVE ||(iErrCode!=FRMMessage.NONE)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" colspan="3"><b>___Training 
                                                  Master Editor</b></td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" width="17%">&nbsp;</td>
                                                <td height="21" colspan="2" width="83%" class="comment">*)= 
                                                  required</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="17%">Training 
                                                  Name</td>
                                                <td height="21" colspan="2" width="83%"> 
                                                  <input type="text" name="<%=frmTraining.fieldNames[FrmTraining.FRM_FIELD_NAME] %>"  value="<%= training.getName() %>" class="formElemen" size="50">
                                                  * <%= frmTraining.getErrorMsg(FrmTraining.FRM_FIELD_NAME) %> 
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="17%">Description</td>
                                                <td height="21" colspan="2" width="83%"> 
                                                  <textarea name="<%=frmTraining.fieldNames[FrmTraining.FRM_FIELD_DESCRIPTION] %>" class="formElemen" cols="50" rows="3"><%= training.getDescription() %></textarea>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="top" width="17%">&nbsp;</td>
                                                <td height="8" colspan="2" width="83%">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="top" width="17%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td height="8" colspan="2" width="83%"> 
                                                  <%if(vctDepartment!=null && vctDepartment.size()>0){
												  int itr = vctDepartment.size()/3;
												  
												  //out.println("x : "+itr);
												  //out.println("vctDepartment.size : "+vctDepartment.size());
												  
												  if(itr % 3 > 0){
												  	itr = itr+1;
												  }
												  
												  int index = 0;
												  %>
                                                  <table width="98%" cellpadding="0" cellspacing="0">
                                                    <%for(int i=0; i<itr; i++){
														
														if(i > 0){
															index = (3*i);
														}
													
														//out.println(index);
													 
													%>
                                                    <tr> 
                                                      <td width="4%"> 
                                                        <%
													  Department dep1 = new Department();
													  String chk = "";
													  if(index < vctDepartment.size()){
													  		dep1 = (Department)vctDepartment.get(index);
															chk = chekOID(dep1.getOID(), vctTrDept);
															%>
                                                        <input type="checkbox" name="dep_id" value="<%=dep1.getOID()%>" <%=chk%>>
                                                        <%
													  }
													  %>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        <%if(index < vctDepartment.size()){%>
                                                        <%=dep1.getDepartment()%> 
                                                        <%}%>
                                                      </td>
                                                      <td width="4%"> 
                                                        <%
													  Department dep2 = new Department();
													  index = index +  1;
													  if(index < vctDepartment.size()){
													  		dep2 = (Department)vctDepartment.get(index);
															chk = chekOID(dep2.getOID(), vctTrDept);
															%>
                                                        <input type="checkbox" name="dep_id" value="<%=dep2.getOID()%>" <%=chk%>>
                                                        <%
													  }
													  %>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        <%if(index < vctDepartment.size()){%>
                                                        <%=dep2.getDepartment()%> 
                                                        <%}%>
                                                      </td>
                                                      <td width="3%"> 
                                                        <%
													  Department dep3 = new Department();
													  index = index + 1;
													  if(index < vctDepartment.size()){
													  		dep3 = (Department)vctDepartment.get(index);
															chk = chekOID(dep3.getOID(), vctTrDept);
															%>
                                                        <input type="checkbox" name="dep_id" value="<%=dep3.getOID()%>" <%=chk%>>
                                                        <%
													  }
													  %>
                                                      </td>
                                                      <td width="31%" nowrap> 
                                                        <%if(index < vctDepartment.size()){%>
                                                        <%=dep3.getDepartment()%> 
                                                        <%}%>
                                                      </td>
                                                    </tr>
                                                    <%}%>
                                                  </table>
                                                  <%}else{%>
                                                  <span class="comment">no department 
                                                  available ...</span> 
                                                  <%}%>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="17%">&nbsp;</td>
                                                <td height="8" colspan="2" width="83%">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="17%">&nbsp;</td>
                                                <td height="8" colspan="2" width="83%"><a href="javascript:setChecked(1)">General 
                                                  Training( Select All Department 
                                                  )</a> &nbsp;&nbsp;| &nbsp;&nbsp;<a href="javascript:setChecked(0)">Release 
                                                  All</a></td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="17%">&nbsp;</td>
                                                <td height="8" colspan="2" width="83%">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="3" class="command"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidTraining+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidTraining+"')";
									String scancel = "javascript:cmdEdit('"+oidTraining+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
										ctrLine.setDeleteCaption("Delete");
										ctrLine.setSaveCaption("Save");
										ctrLine.setAddCaption("Add new");
										ctrLine.setDeleteCaption("Delete");

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
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
									}
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> </td>
                                              </tr>
                                              <tr> 
                                                <td width="13%">&nbsp;</td>
                                                <td width="87%">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="3"> 
                                                  <div align="left"></div>
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
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
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>

<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
