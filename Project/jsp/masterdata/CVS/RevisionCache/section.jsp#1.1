
<% 
/* 
 * Page Name  		:  section.jsp
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_SECTION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!
    public String getSectionLink(String sectionId){
        String str = "";
        try{
            Section section = PstSection.fetchExc(Long.valueOf(sectionId));
            str = section.getSection();
            return str;
        } catch(Exception e){
            System.out.println(e);
        }
        return str;
    }
	public String drawList(Vector objectClass ,  long sectionId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Section","20%");
		ctrlist.addHeader("Department","25%");
		ctrlist.addHeader("Description","30%");
                ctrlist.addHeader("Section Link","30%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			Section section = (Section)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(sectionId == section.getOID())
				 index = i;

			rowx.add(section.getSection());
			
			//System.out.println("section.getDepartmentId()"+section.getDepartmentId());  
			Vector vector = PstDepartment.list(0,1,PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = "+section.getDepartmentId(),"");
			String strDept = "";
			if(vector != null && vector.size()>0){
				Department depart = (Department)vector.get(0);
				strDept = depart.getDepartment();
			}
			rowx.add(strDept);

			rowx.add(section.getDescription());
                        String strLink = "";
                        String strSectionLinkTo = "";
                        String strSectionLink = section.getSectionLinkTo();
                        if ((strSectionLink != null)&&!"".equals(strSectionLink)){

                            for (String retval : strSectionLink.split(",")) {
                                strLink = "<span style='color: #0066CC; background-color: #DDD;padding: 2px; margin-right:3px;'>"+getSectionLink(retval)+"</span>";
                                strSectionLinkTo += strLink;
                            }
                        }
                        
                        rowx.add(strSectionLinkTo);
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(section.getOID()));
		}
		

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidSection = FRMQueryString.requestLong(request, "hidden_section_id");
String sectionInput = FRMQueryString.requestString(request, "section_input");
long oidSectionTemp = FRMQueryString.requestLong(request, "section_temp_id");
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

CtrlSection ctrlSection = new CtrlSection(request);
ControlLine ctrLine = new ControlLine();
Vector listSection = new Vector(1,1);

/*switch statement */
iErrCode = ctrlSection.action(iCommand , oidSection);
/* end switch*/
FrmSection frmSection = ctrlSection.getForm();


SrcSection srcSection = new SrcSection();
FrmSrcSection frmSrcSection  = new FrmSrcSection(request, srcSection);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST || iCommand==Command.BACK || iCommand==Command.ASK
   || iCommand==Command.EDIT || iCommand==Command.ADD || iCommand==Command.DELETE || (iCommand==Command.SAVE && frmSection.errorSize()==0) )
{
	 try
	 { 
		srcSection = (SrcSection)session.getValue(PstSection.SESS_HR_SECTION); 
	 }
	 catch(Exception e)
	 { 
		srcSection = new SrcSection();
	 }
}
else
{
	frmSrcSection.requestEntityObject(srcSection);
	session.putValue(PstSection.SESS_HR_SECTION, srcSection);	
}



String whereClause = "";
if(srcSection.getSecName()!=null && srcSection.getSecName().length()>0)
{
	whereClause = PstSection.fieldNames[PstSection.FLD_SECTION] + 
				  " LIKE \"%" + srcSection.getSecName() + "%\"";
	
	if(srcSection.getSecDepartment() != 0)
	{
		whereClause = whereClause + " AND " + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + 
			 		  " = " + srcSection.getSecDepartment();
	}
}
else
{
	if(srcSection.getSecDepartment() != 0)
	{
		whereClause = whereClause + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + 
			 		  " = " + srcSection.getSecDepartment();
	}
}

String orderClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+", "+PstSection.fieldNames[PstSection.FLD_SECTION];

/*count list All Section*/
int vectSize = PstSection.getCount(whereClause);

Section section = ctrlSection.getSection();
msgString =  ctrlSection.getMessage();

/*switch list Section*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstSection.findLimitStart(section.getOID(),recordToGet, whereClause, orderClause);
	oidSection = section.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlSection.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listSection = PstSection.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listSection.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listSection = PstSection.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Section</title>
<script language="JavaScript">
function cmdBackToSearch(){
	document.frmsection.command.value="<%=Command.BACK%>";
	document.frmsection.action="srcsection.jsp";
	document.frmsection.submit();
}

function cmdAdd(){
	document.frmsection.hidden_section_id.value="0";
	document.frmsection.command.value="<%=Command.ADD%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
}

function cmdAsk(oidSection){
	document.frmsection.hidden_section_id.value=oidSection;
	document.frmsection.command.value="<%=Command.ASK%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
}

function cmdConfirmDelete(oidSection){
	document.frmsection.hidden_section_id.value=oidSection;
	document.frmsection.command.value="<%=Command.DELETE%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
}
function cmdSave(){
	document.frmsection.command.value="<%=Command.SAVE%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
	}

function cmdEdit(oidSection){
	document.frmsection.hidden_section_id.value=oidSection;
	document.frmsection.command.value="<%=Command.EDIT%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
	}

function cmdCancel(oidSection){
	document.frmsection.hidden_section_id.value=oidSection;
	document.frmsection.command.value="<%=Command.EDIT%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
}

function cmdBack(){
	document.frmsection.command.value="<%=Command.BACK%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
	}

function cmdListFirst(){
	document.frmsection.command.value="<%=Command.FIRST%>";
	document.frmsection.prev_command.value="<%=Command.FIRST%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
}

function cmdListPrev(){
	document.frmsection.command.value="<%=Command.PREV%>";
	document.frmsection.prev_command.value="<%=Command.PREV%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
	}

function cmdListNext(){
	document.frmsection.command.value="<%=Command.NEXT%>";
	document.frmsection.prev_command.value="<%=Command.NEXT%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
}

function cmdListLast(){
	document.frmsection.command.value="<%=Command.LAST%>";
	document.frmsection.prev_command.value="<%=Command.LAST%>";
	document.frmsection.action="section.jsp";
	document.frmsection.submit();
}

function cmdAddSectionLink(){
    var data = document.getElementById("select_section").value;
    var result = document.getElementById("section_input").value;
    if(result!=""){
        result = result +","+ data;
    } else {
        result = result + data;
    }
    document.getElementById("section_input").value = result;
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
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Master Data &gt; Section<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmsection" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_section_id" value="<%=oidSection%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Section 
                                                  List </td>
                                              </tr>
                                              <%
											  if (listSection.size()>0)
											  {
											  %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listSection,oidSection)%> </td>
                                              </tr>
                                              <%
											  }
											  else
											  { 
											  %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3" class="errfont">There 
                                                  is no section data found ...</td>
                                              </tr>											  
											  <%
											  }
											  %>
						  
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command" valign="top"> 
                                                  <span class="command"> 
                                                  <% 
								   int cmd = 0;
									   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
										(iCommand == Command.NEXT || iCommand == Command.LAST))
											cmd =iCommand; 
								   else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
										cmd = Command.FIRST;
									  else{
									  		if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
												cmd = PstDepartment.findLimitCommand(start,recordToGet,vectSize);
											else									 
									  			cmd =prevCommand;
									  }  
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                              </tr>
                                              <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmSection.errorSize()<1)){
											  if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmSection.errorSize()<1)){  
											   %>
                                              <tr align="left" valign="top"> 
                                                <td valign="top"> 
                                                  <table cellpadding="0" cellspacing="0" border="0" width="50%">
                                                    <tr> 
													<%if(privAdd){%>
                                                      <td width="5%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Section"></a></td>
                                                      <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="37%" valign="middle"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add 
                                                        New Section</a> </td>
														<%}%>
                                                      <td width="5%"><a href="javascript:cmdBackToSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2611','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image2611" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To Search Section"></a></td>
                                                      <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="49%" height="22" valign="middle"> 
                                                        <a href="javascript:cmdBackToSearch()" class="command">Back 
                                                        To Search Section</a> 
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <% 
											  }%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp; </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmSection.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidSection == 0?"Add":"Edit"%><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                              </tr>
                                              <tr> 
                                                <td height="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left"> 
                                                      <td valign="top" width="18%">&nbsp;</td>
                                                      <td width="82%" valign="top" class="comment" >*)entry 
                                                        required </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td valign="top" width="18%"> 
                                                        Section </td>
                                                      <td width="82%" valign="top"> 
                                                        <input type="text" name="<%=frmSection.fieldNames[FrmSection.FRM_FIELD_SECTION] %>"  value="<%= section.getSection()%>" class="elemenForm" size="30">
                                                        * <%=frmSection.getErrorMsg(FrmSection.FRM_FIELD_SECTION)%> </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td valign="top" width="18%"> 
                                                        Department </td>
                                                      <td width="82%" valign="top"> 
                                                        <%
                                                              Vector deptKey = new Vector(1,1);
                                                              Vector deptValue = new Vector(1,1);
                                                              /*Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                              for(int i =0;i < listDepartment.size();i++){
                                                                    Department department = (Department)listDepartment.get(i);
                                                                    deptKey.add(department.getDepartment());
                                                                    deptValue.add(""+department.getOID());														
                                                              }*/
                                                    Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, "");
                                                    //Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                    String prevCompany="";
                                                    String prevDivision="";
                                                    for (int i = 0; i < listCostDept.size(); i++) {
                                                        Department dept = (Department) listCostDept.get(i);
                                                        if(prevCompany.equals(dept.getCompany())){
                                                           if(prevDivision.equals(dept.getDivision())){
                                                               deptKey.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dept.getDepartment());                                                               
                                                               deptValue.add(String.valueOf(dept.getOID()));
                                                           } else{
                                                               deptKey.add("&nbsp;-"+dept.getDivision()+"-");                                                               
                                                               deptValue.add("-2");
                                                               deptKey.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dept.getDepartment());                                                               
                                                               deptValue.add(String.valueOf(dept.getOID()));
                                                               prevDivision=dept.getDivision();
                                                           }
                                                        } else {
                                                               deptKey.add("-"+dept.getCompany()+"-") ;                                                               
                                                               deptValue.add("-1");                                                                                                                                                                                     
                                                               deptKey.add("&nbsp;-"+dept.getDivision()+"-");                                                                                                                              
                                                               deptValue.add("-2");                                                                                                                                                                                     
                                                               deptKey.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dept.getDepartment());                                                               
                                                               deptValue.add(String.valueOf(dept.getOID()));
                                                               prevCompany =dept.getCompany();
                                                               prevDivision=dept.getDivision();                                                            
                                                        }
                                                   }                                                  

                                                              %>
                                                        <%=ControlCombo.draw(frmSection.fieldNames[FrmSection.FRM_FIELD_DEPARTMENT_ID],"formElemen",null,""+section.getDepartmentId(),deptValue,deptKey )%> * <%=frmSection.getErrorMsg(FrmSection.FRM_FIELD_DEPARTMENT_ID)%> </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td valign="top" width="18%"> 
                                                        Description</td>
                                                      <td width="82%" valign="top"> 
                                                        <textarea name="<%=frmSection.fieldNames[FrmSection.FRM_FIELD_DESCRIPTION] %>" class="elemenForm" cols="30" rows="3"><%= section.getDescription() %></textarea>
                                                      </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="top">Add Section Link To</td>
                                                        
                                                        <td>
                                                            <button id="btn" onclick="javascript:cmdAddSectionLink()">Add Section Link</button>
                                                            <select id="select_section">
                                                                <option value="0">-select-</option>
                                                            <%
                                                            Vector listSectionLink = new Vector();
                                                            listSectionLink = PstSection.list(0, 0, "", "");
                                                            if (listSectionLink != null && listSectionLink.size() > 0){
                                                                for(int i=0; i<listSectionLink.size(); i++){
                                                                    Section sec = (Section) listSectionLink.get(i);
                                                                    %>
                                                                    <option value="<%=""+sec.getOID()%>"><%="["+sec.getOID()+"] "+sec.getSection()%></option>
                                                                    <%
                                                                }
                                                            }
                                                            String sectionData = "";
                                                            if (section.getSectionLinkTo() !=null && !section.getSectionLinkTo().equals("")){
                                                                //if (sectionInput.equals("")){
                                                                   sectionData = section.getSectionLinkTo(); 
                                                                //} else {
                                                                   //sectionData = sectionInput;
                                                                //}
                                                            } else {
                                                                if (oidSectionTemp == section.getOID()){
                                                                    sectionData = sectionInput;
                                                                } else {
                                                                    sectionData = "";
                                                                }
                                                            }
                                                            sectionInput = "";
                                                            %>
                                                            </select><br />
                                                            <input type="hidden" name="section_temp_id" value="<%=section.getOID()%>" />
                                                            <input type="hidden" id="section_input" name="section_input" value="<%=sectionData%>" size="70" />
                                                            <input type="text" size="49"  name="<%=frmSection.fieldNames[FrmSection.FRM_FIELD_SECTION_LINK_TO]%>" value="<%=sectionData%>" size="70" />
                                                        </td>
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
                                                    String scomDel = "javascript:cmdAsk('"+oidSection+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidSection+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidSection+"')";									
                                                    ctrLine.setCommandStyle("buttonlink");
                                                    ctrLine.setBackCaption("Back to List Section");
                                                    ctrLine.setSaveCaption("Save Section");
                                                    ctrLine.setConfirmDelCaption("Yes Delete Section");
                                                    ctrLine.setDeleteCaption("Delete Section");

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
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> </td>
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
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
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
