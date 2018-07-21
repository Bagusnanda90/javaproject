
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long imgOid, String imagePath)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Payroll","10%");
		ctrlist.addHeader("Name","40%");
		ctrlist.addHeader("Position","10%");
		ctrlist.addHeader("Section","10%");
		ctrlist.addHeader("Department","10%");
		ctrlist.addHeader("Assign Image","20%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
                        Vector vTemp = (Vector)objectClass.get(i);
			Employee emp = (Employee)vTemp.get(0);
			Position pos = (Position)vTemp.get(1);
			Section sec = (Section)vTemp.get(2);
			Department dep = (Department)vTemp.get(3);
			ImageAssign img = (ImageAssign)vTemp.get(4);
                        
			 Vector rowx = new Vector();
			 if(imgOid == img.getOID())
				 index = i;

			rowx.add(emp.getEmployeeNum());
			rowx.add(emp.getFullName());
			rowx.add(pos.getPosition());
			rowx.add(sec.getSection());
			rowx.add(dep.getDepartment());
                        String strPsth = "";
                        if(img.getPath()!=null){
                            strPsth = img.getPath();
                        }
                        if(strPsth.length()>0){
                            String strImage = "<a href=javascript:cmdAddImage('"+img.getOID()+"')><img name=\'"+img.getOID()+"\' src=\'"+imagePath+"/images/imgassign/"+img.getPath()+"\' alt='' border='0' height='100' width='150'></a>";
                            rowx.add(strImage);
                        }else{
                            rowx.add("<a href=javascript:cmdAddImage('"+img.getOID()+"')>Add Image</a>");
                        }

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(img.getOID()));
		}
		return ctrlist.draw(index);
	}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidImageAssign = FRMQueryString.requestLong(request, FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]);

String pictName =  FRMQueryString.requestString(request, "pict");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstImageAssign.fieldNames[PstImageAssign.FLD_EMPLOYEE_OID];

CtrlImageAssign ctrlImageAssign = new CtrlImageAssign(request);
ControlLine ctrLine = new ControlLine();
Vector listImageAssign = new Vector(1,1);

/*switch statement */
iErrCode = ctrlImageAssign.action(iCommand , oidImageAssign);
/* end switch*/
FrmImageAssign frmImageAssign = ctrlImageAssign.getForm();

/*count list All Position*/
//int vectSize = PstImageAssign.getCount(whereClause);
Vector numberList = PstImageAssign.getList(start,0, whereClause , orderClause);

int vectSize = numberList.size();

ImageAssign imageAssign = ctrlImageAssign.getImageAssign();
msgString =  ctrlImageAssign.getMessage();
 
/*switch list ImageAssign*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstImageAssign.findLimitStart(imageAssign.getOID(),recordToGet, whereClause);
	oidImageAssign = imageAssign.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlImageAssign.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listImageAssign = PstImageAssign.getList(start,recordToGet, whereClause , orderClause);



/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listImageAssign.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listImageAssign = PstImageAssign.getList(start,recordToGet, whereClause , orderClause);
}

Vector vectPict = new Vector(1,1);
 vectPict.add(""+oidImageAssign);


session.putValue("SELECTED_IMAGE_ASSIGN_SESSION", vectPict);


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data ImageAssign</title>
<script language="JavaScript">


function cmdSearchEmp(){
    window.open("<%=approot%>/employee/search/search.jsp?formName=<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>&empPathId=<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}

function cmdAddImage(oidImageAssign){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]%>.value=oidImageAssign;
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(Command.GOTO)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
}

function cmdAdd(){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]%>.value="0";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.ADD)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
}

function cmdAsk(oidImageAssign){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.ASK)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]%>.value=oidImageAssign;
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
}

function cmdConfirmDelete(oidImageAssign){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]%>.value=oidImageAssign;
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
}
function cmdSave(){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
         <%if(imageAssign.getEmployeeOid()>0){%>
            document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="upload_img_assign_process.jsp";
         <%}else{%>
            document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
         <%}%>
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
	}

function cmdEdit(oidImageAssign){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]%>.value=oidImageAssign;
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
	}

function cmdCancel(oidImageAssign){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]%>.value=oidImageAssign;
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
}

function cmdBack(){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.BACK)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
	}

function cmdListFirst(){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(Command.FIRST)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
}

function cmdListPrev(){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.PREV)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(Command.PREV)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
	}

function cmdListNext(){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(Command.NEXT)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
}

function cmdListLast(){
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.command.value="<%=String.valueOf(Command.LAST)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.prev_command.value="<%=String.valueOf(Command.LAST)%>";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.action="image_assign.jsp";
	document.<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>.submit();
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                  Master Data &gt; Image Assign<!-- #EndEditable -->
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
                                <%if(iCommand == Command.EDIT && prevCommand ==Command.GOTO){
                                    %>
                                    <!--- FORM PERTAMA-->
                                    <form name="<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>" method ="post"  enctype="multipart/form-data">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="vectSize" value="<%=String.valueOf(vectSize)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]%>" value="<%=String.valueOf(oidImageAssign)%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Image Assign
                                                  List </td>
                                              </tr>
                                              <%
                                            try{
                                                    if (listImageAssign.size()>0){
                                            %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%=drawList(listImageAssign,oidImageAssign,approot)%>
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
                                            </table>
                                          </td>
                                        </tr>
                                        
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmImageAssign.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>

                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidImageAssign == 0?"Add":"Edit"%> Image Assign</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                                      <tr align="" valign=""> 
                                                        <%
                                                        Employee emp = new Employee();
                                                        try{
                                                            emp = PstEmployee.fetchExc(imageAssign.getEmployeeOid());
                                                        }catch(Exception ex){}
                                                        %>
                                                          <td valign="" width="17%">Payroll</td>
                                                          <td width="83%" >:<b><%=String.valueOf(emp.getEmployeeNum())%></b></td>
                                                        </tr>
                                                      <tr align="" valign="top"> 
                                                          <td valign="" width="17%"> 
                                                            Name</td>
                                                          <td width="83%">:
                                                          <b><%=String.valueOf(emp.getFullName())%></b>
                                                            <input type="hidden" name="<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_EMPLOYEE_ID]%>"  value="<%=String.valueOf(imageAssign.getEmployeeOid())%>" class="elemenForm" size="30">
                                                           </td>
                                                        </tr>
                                                      <tr align="" valign="top"> 
                                                          <td valign="" colspan="2"> 
                                                          <%if(imageAssign.getEmployeeOid()>0){%>
                                                            <table width="100%" border="0">
                                                              <tr> 
                                                                <td height="21" colspan="3"><strong>Upload Picture</strong><i><font color="#CC0000"></font></i></td>
                                                              </tr>
                                                              <tr> 
                                                                <td height="21" colspan="3"><i><font color="#CC0000"> 
                                                                  ( Click browse... if you want to add/edit picture )
                                                                  </font></i></td>
                                                              </tr>
                                                              <tr> 
                                                                <td height="26" colspan="3"><input type="file" name="pict" size="60" height="100"></td>
                                                              </tr>
                                                            </table>
                                                            <%}%>
                                                            </td>
                                                          </tr>
                                                      <tr>
                                                      <td>
                                                          <table><tr><td>
                                                          <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                          <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                          <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                          <td height="22" valign="middle" colspan="3" width="951"> 
                                                            <a href="javascript:cmdSave()" class="command">Save Image Assign</a> </td>
                                                    </td></tr></table>
                                                        </td> </tr>
                                                        
                                                </table>
                                            </td></tr>
                                                </table>
                                            <%}%>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                    
                                    <%}else{%>
                                    <!--- FORM KEDUA-->
                                    <form name="<%=FrmImageAssign.FRM_IMAGE_ASSIGN%>"><!--   method ="post"  enctype="multipart/form-data" action="upload_img_assign_process.jsp" -->
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="vectSize" value="<%=String.valueOf(vectSize)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]%>" value="<%=String.valueOf(oidImageAssign)%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Image Assign
                                                  List </td>
                                              </tr>
                                              <%
                                            try{
                                                    if (listImageAssign.size()>0){
                                            %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%=drawList(listImageAssign,oidImageAssign,approot)%>
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
                                                <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmImageAssign.errorSize()<1)){
                                                   if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmImageAssign.errorSize()<1)){
                                                   if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td colspan='4'>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add New Image Assign</a> </td>
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
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmImageAssign.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>

                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidImageAssign == 0?"Add":"Edit"%> ImageAssign</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%">
                                                    <%
                                                    String strPayroll = "";
                                                    String strName = "";
                                                    String strDep = "";
                                                    try{
                                                        Employee empTemp = new Employee();
                                                        Department depTemp = new Department();
                                                        empTemp = PstEmployee.fetchExc(imageAssign.getEmployeeOid());
                                                        depTemp = PstDepartment.fetchExc(empTemp.getDepartmentId());
                                                        strPayroll = empTemp.getEmployeeNum();
                                                        strName = empTemp.getFullName();
                                                        strDep = depTemp.getDepartment();
                                                    }catch(Exception ex){}
                                                    %>
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="10%">&nbsp;</td>
                                                      <td width="90%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Payroll</td>
                                                      <td width="83%"> 
                                                        <input type="hidden" name="<%=FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_EMPLOYEE_ID]%>"  value="<%=String.valueOf(imageAssign.getEmployeeOid())%>" class="elemenForm" size="30">
                                                                <input type="text" readonly="readonly" name="EMP_NUMBER"  value="<%=strPayroll%>" class="elemenForm" size="30">
                                                               <%
                                                               if(imageAssign.getOID()<=0){
                                                               %>
                                                                <a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10x','','<%=approot%>/images/icon/folderopen.gif',1)"><img name="Image10x" border="0" src="<%=approot%>/images/icon/folder.gif" width="24" height="24" alt="Search Trainer"></a>
                                                                *<%=frmImageAssign.getErrorMsg(FrmImageAssign.FRM_FIELD_EMPLOYEE_ID)%>
                                                                <%}%>
                                                       </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Name</td>
                                                      <td width="83%"> <input type="text" readonly="readonly" name="EMP_FULLNAME"  value="<%=strName%>" class="elemenForm" size="30">
                                                       </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Department</td>
                                                      <td width="83%"> <input type="text" readonly="readonly" name="EMP_DEPARTMENT"  value="<%=strDep%>" class="elemenForm" size="30">
                                                      </td>
                                                    </tr>
                                                    </table> 
                                                    
                                                </td></tr>
                                              <tr> 
                                                <td colspan="2"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidImageAssign+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidImageAssign+"')";
									String scancel = "javascript:cmdEdit('"+oidImageAssign+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setBackCaption("Back to List Image Assign");
									ctrLine.setSaveCaption("Save Image Assign");
									ctrLine.setConfirmDelCaption("Yes Delete Image Assign");
									ctrLine.setDeleteCaption("Delete Image Assign");

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
                                            <%}%>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                    <%}%>
                                    
                                    <!-- #EndEditable -->
                            </td>
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
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
