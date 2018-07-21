
<% 
/* 
 * Page Name  		:  evaluation.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_EVALUATION_CRITERIA); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(int iCommand,FrmEvaluation frmObject, Evaluation objEntity, Vector objectClass,  long evaluationId, int SESS_LANGUAGE)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                ctrlist.addHeader("Type","10%");
		ctrlist.addHeader("Code","10%");
                ctrlist.addHeader(" Max Point","10%");
		ctrlist.addHeader(" Max (%)","10%");
		ctrlist.addHeader("Name","35%");
		ctrlist.addHeader("Desription","45%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

                Vector typeKey = new Vector(1, 1);
                Vector typeValue = new Vector(1, 1);
              
                
                for (int i = 0; i < Evaluation.EVAL_TYPE[0].length ; i++) {
                    typeKey.add( Evaluation.EVAL_TYPE[SESS_LANGUAGE][i]);
                    typeValue.add("" + i);
                }
          
		for (int i = 0; i < objectClass.size(); i++) {
			 Evaluation evaluation = (Evaluation)objectClass.get(i);
			 rowx = new Vector();
			 if(evaluationId == evaluation.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
				rowx.add(""+ ControlCombo.draw(FrmEvaluation.fieldNames[FrmEvaluation.FRM_FIELD_EVAL_TYPE], "formElemen", null, "" + evaluation.getEvalType(), typeValue, typeKey));	
				rowx.add("<input type=\"text\" name=\""+ frmObject.fieldNames[FrmEvaluation.FRM_FIELD_CODE] +"\" value=\""+evaluation.getCode()+"\" class=\"elemenForm\" size=\"10\">");
                                rowx.add("<input type=\"text\" name=\""+ frmObject.fieldNames[FrmEvaluation.FRM_FIELD_MAX_POINT ] +"\" value=\""+ evaluation.getMaxPoint()+"\" class=\"elemenForm\" size=\"5\">");
				rowx.add("<input type=\"text\" name=\""+ frmObject.fieldNames[FrmEvaluation.FRM_FIELD_MAX_PERCENTAGE] +"\" value=\""+evaluation.getMaxPercentage()+"\" class=\"elemenForm\" size=\"5\">"+" %");
				rowx.add("<input type=\"text\" name=\""+ frmObject.fieldNames[FrmEvaluation.FRM_FIELD_NAME] +"\" value=\""+evaluation.getName()+"\" class=\"elemenForm\" size=\"35\">");
				rowx.add("<textarea name=\""+ frmObject.fieldNames[FrmEvaluation.FRM_FIELD_DESRIPTION] +"\"  class=\"elemenForm\" cols=\"40\" rows=\"3\">"+evaluation.getDesription()+"</textarea>");
			}else{  
                                rowx.add(""+ Evaluation.EVAL_TYPE[SESS_LANGUAGE][(int)evaluation.getEvalType()]);
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(evaluation.getOID())+"')\">"+evaluation.getCode()+"</a>");
                                rowx.add(""+evaluation.getMaxPoint());
				rowx.add(""+evaluation.getMaxPercentage()+ " % ");
				rowx.add(evaluation.getName());
				rowx.add(evaluation.getDesription());
			} 

			lstData.add(rowx); 
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
                                rowx.add(""+ ControlCombo.draw(FrmEvaluation.fieldNames[FrmEvaluation.FRM_FIELD_EVAL_TYPE], "formElemen", null, "" +objEntity.getEvalType(), typeValue, typeKey));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEvaluation.FRM_FIELD_CODE] +"\" value=\""+objEntity.getCode()+"\" class=\"elemenForm\" size=\"10\">");
                                rowx.add("<input type=\"text\" name=\""+ frmObject.fieldNames[FrmEvaluation.FRM_FIELD_MAX_POINT ] +"\" value=\""+objEntity.getMaxPoint()+"\" class=\"elemenForm\" size=\"5\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEvaluation.FRM_FIELD_MAX_PERCENTAGE] +"\" value=\""+objEntity.getMaxPercentage()+"\" class=\"elemenForm\" size=\"5\"> %");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEvaluation.FRM_FIELD_NAME] +"\" value=\""+objEntity.getName()+"\" class=\"elemenForm\" size=\"35\">");
				rowx.add("<textarea name=\""+frmObject.fieldNames[FrmEvaluation.FRM_FIELD_DESRIPTION] +"\"  class=\"elemenForm\" cols=\"40\" rows=\"3\">"+objEntity.getDesription()+"</textarea>");

		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidEvaluation = FRMQueryString.requestLong(request, "hidden_evaluation_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstEvaluation.fieldNames[PstEvaluation.FLD_EVAL_TYPE]+","+ PstEvaluation.fieldNames[PstEvaluation.FLD_MAX_POINT ]+","+ PstEvaluation.fieldNames[PstEvaluation.FLD_MAX_PERCENTAGE];

CtrlEvaluation ctrlEvaluation = new CtrlEvaluation(request);
ControlLine ctrLine = new ControlLine();
Vector listEvaluation = new Vector(1,1);

/*switch statement */
iErrCode = ctrlEvaluation.action(iCommand , oidEvaluation);
/* end switch*/
FrmEvaluation frmEvaluation = ctrlEvaluation.getForm();

/*count list All Evaluation*/
int vectSize = PstEvaluation.getCount(whereClause);

/*switch list Evaluation*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlEvaluation.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

Evaluation evaluation = ctrlEvaluation.getEvaluation();
msgString =  ctrlEvaluation.getMessage();

/* get record to display */
listEvaluation = PstEvaluation.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listEvaluation.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listEvaluation = PstEvaluation.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Evaluation </title>
<script language="JavaScript">


function cmdAdd(){
	document.frmevaluation.hidden_evaluation_id.value="0";
	document.frmevaluation.command.value="<%=Command.ADD%>";
	document.frmevaluation.prev_command.value="<%=prevCommand%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdAsk(oidEvaluation){
	document.frmevaluation.hidden_evaluation_id.value=oidEvaluation;
	document.frmevaluation.command.value="<%=Command.ASK%>";
	document.frmevaluation.prev_command.value="<%=prevCommand%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdConfirmDelete(oidEvaluation){
	document.frmevaluation.hidden_evaluation_id.value=oidEvaluation;
	document.frmevaluation.command.value="<%=Command.DELETE%>";
	document.frmevaluation.prev_command.value="<%=prevCommand%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdSave(){
	document.frmevaluation.command.value="<%=Command.SAVE%>";
	document.frmevaluation.prev_command.value="<%=prevCommand%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdEdit(oidEvaluation){
	document.frmevaluation.hidden_evaluation_id.value=oidEvaluation;
	document.frmevaluation.command.value="<%=Command.EDIT%>";
	document.frmevaluation.prev_command.value="<%=prevCommand%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdCancel(oidEvaluation){
	document.frmevaluation.hidden_evaluation_id.value=oidEvaluation;
	document.frmevaluation.command.value="<%=Command.EDIT%>";
	document.frmevaluation.prev_command.value="<%=prevCommand%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdBack(){
	document.frmevaluation.command.value="<%=Command.BACK%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdListFirst(){
	document.frmevaluation.command.value="<%=Command.FIRST%>";
	document.frmevaluation.prev_command.value="<%=Command.FIRST%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdListPrev(){
	document.frmevaluation.command.value="<%=Command.PREV%>";
	document.frmevaluation.prev_command.value="<%=Command.PREV%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdListNext(){
	document.frmevaluation.command.value="<%=Command.NEXT%>";
	document.frmevaluation.prev_command.value="<%=Command.NEXT%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
}

function cmdListLast(){
	document.frmevaluation.command.value="<%=Command.LAST%>";
	document.frmevaluation.prev_command.value="<%=Command.LAST%>";
	document.frmevaluation.action="evaluation.jsp";
	document.frmevaluation.submit();
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
//-------------- script form image -------------------

function cmdDelPict(oidEvaluation){
	document.frmimage.hidden_evaluation_id.value=oidEvaluation;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="evaluation.jsp";
	document.frmimage.submit();
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
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
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

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
  <%}%>
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
                  Masterdata &gt; Performance Appraisal &gt; Evaluation<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmevaluation" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_evaluation_id" value="<%=oidEvaluation%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              
                                              <%
												try{													
												%>
											  <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Evaluation 
                                                  List </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">
												<% if(iCommand == Command.NONE)
														iCommand = Command.ADD;	 
													//out.println(iCommand);	%>
                                                  <%= drawList(iCommand,frmEvaluation, evaluation,listEvaluation,oidEvaluation, SESS_LANGUAGE)%> 
                                                </td>
                                              </tr>                                             
											   <% 
											  }catch(Exception exc){ 
											  	System.out.println("exc........."+exc.toString());
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
                                                  <% ctrLine.setLocationImg(approot+"/images/ctr_line");
													ctrLine.initDefault();
													 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
											   <% if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmEvaluation.errorSize()<1)){%>
													<%if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4" height="23"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24" height="23"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6" height="23"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td valign="middle" colspan="3" width="951" height="23"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Evaluation</a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
											  <%}
											  }%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidEvaluation+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidEvaluation+"')";
									String scancel = "javascript:cmdEdit('"+oidEvaluation+"')";
									ctrLine.setBackCaption("Back to List Evaluation");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setConfirmDelCaption("Yes Delete Evaluation");
									ctrLine.setDeleteCaption("Delete Evaluation");
									ctrLine.setAddCaption("Add New Evaluation");
									ctrLine.setSaveCaption("Save Evaluation");
									

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
									
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString);
									%>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
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
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
