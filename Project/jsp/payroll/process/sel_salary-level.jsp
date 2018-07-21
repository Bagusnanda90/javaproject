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

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_LEVEL);%>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- JSP Block -->
<%!
public String drawList(int iCommand, FrmSalaryLevel frmObject, SalaryLevel objEntity, Vector objectClass, long idSalaryLevel){
	String result = "";

	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("80%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","5%");
	ctrlist.addHeader("Level Code <br><input type=\"text\" name=\"salary_level_code\" size=\"12\">","10%");
	ctrlist.addHeader("Level Name <br><input type=\"text\" name=\"salary_level_name\" size=\"12\"> <input type=\"button\" value=\"Search\" onClick=\"javascript:searchLevel()\">","40%");
	ctrlist.addHeader("Salary","10%");
	ctrlist.addHeader("Currency","20%");
	ctrlist.addHeader("Salary Component","15%");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdSelect('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	//untuk mengambil amount_gaji
	Vector vKeyAmountGj = new Vector();
    Vector vValAmountGj = new Vector();
    vKeyAmountGj.add(PstSalaryLevel.BRUTO+"");
    vKeyAmountGj.add(PstSalaryLevel.NETTO+"");
	vKeyAmountGj.add(PstSalaryLevel.NONE+"");
    vValAmountGj.add(PstSalaryLevel.amount_is[PstSalaryLevel.BRUTO]);
    vValAmountGj.add(PstSalaryLevel.amount_is[PstSalaryLevel.NETTO]);
	vValAmountGj.add(PstSalaryLevel.amount_is[PstSalaryLevel.NONE]);
	
	//untuk menampilkan currency 
	Vector curr_value = new Vector(1,1);
    Vector curr_key = new Vector(1,1);
	String orderCl = PstCurrencyType.fieldNames[PstCurrencyType.FLD_NAME]+" DESC ";
    Vector listCurr= PstCurrencyType.list(0, 0, "", orderCl);
    for (int i = 0; i < listCurr.size(); i++) {
		 CurrencyType curr = (CurrencyType) listCurr.get(i);
		 curr_key.add(curr.getName());
		 curr_value.add(String.valueOf(curr.getCode()));
    }
	
	String kdSalaryLevel = objEntity.getLevelCode()==null ? "" : ""+objEntity.getLevelCode();
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			SalaryLevel salaryLevel = (SalaryLevel)objectClass.get(i);
			if(idSalaryLevel == salaryLevel.getOID()){
			  index = i;
			}
			
			rowx = new Vector();
				CurrencyType objCur = new CurrencyType();
				String whereCl = PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE]+" = '"+salaryLevel.getCur_Code()+"'";
				Vector listCurren = PstCurrencyType.list(0,0,whereCl,"");
				System.out.println("currencyType::::::"+listCurren.size());
				if(listCurren.size()>0){
					objCur = (CurrencyType)listCurren.get(0);
				}
                        
			if((index==i) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_SORT_IDX] +"\" value=\""+salaryLevel.getSort_Idx()+"\" class=\"formElemen\" size=\"10\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_LEVEL_CODE] +"\" value=\""+salaryLevel.getLevelCode()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_LEVEL_NAME] +"\" value=\""+salaryLevel.getLevelName()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_AMOUNT_IS], null, ""+salaryLevel.getAmountIs(),vKeyAmountGj,vValAmountGj));
				rowx.add(ControlCombo.drawWithStyle(frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_CUR_CODE],null, ""+salaryLevel.getCur_Code(), curr_value, curr_key, "formElemen"));
				//rowx.add("<a href='salary-level-comp.jsp?'>view details</a>");
                                rowx.add("<a href=\"javascript:cmdSelect('"+String.valueOf(salaryLevel.getLevelCode())+"')\"> Select level</a>");
			}else{
				
				rowx.add(""+i+1);//String.valueOf(salaryLevel.getSort_Idx()));
				rowx.add("<a href=\"javascript:cmdSelect('"+String.valueOf(salaryLevel.getLevelCode())+"')\">"+(salaryLevel.getLevelCode()!=null && salaryLevel.getLevelCode().length()>0?salaryLevel.getLevelCode(): "- NO CODE -")+"</a>");
				rowx.add(salaryLevel.getLevelName());
				rowx.add(String.valueOf(PstSalaryLevel.amount_is[salaryLevel.getAmountIs()]));
				rowx.add(salaryLevel.getCur_Code()+" - "+objCur.getName());
				rowx.add("<a href=\"javascript:cmdSelect('"+String.valueOf(salaryLevel.getLevelCode())+"')\"> Select level</a>");
				//rowx.add("<a href='salary-level-comp.jsp?salary_level="+salaryLevel.getLevelCode()"'>view details</a>");
			}
			lstData.add(rowx);
		}
		rowx = new Vector();

		if(iCommand==Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize()>0)){
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_SORT_IDX] +"\" value=\""+objEntity.getAmountIs()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_LEVEL_CODE] +"\" value=\""+kdSalaryLevel+"\" class=\"formElemen\" size=\"40\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_LEVEL_NAME] +"\" value=\""+objEntity.getLevelName()+"\" class=\"formElemen\" size=\"40\">");
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_AMOUNT_IS], null, ""+objEntity.getAmountIs(),vKeyAmountGj,vValAmountGj));
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_CUR_CODE], null, ""+objEntity.getCur_Code(),curr_value, curr_key, "formElemen"));
			rowx.add("<a href='salary-level-comp.jsp'>view details</a>");
		}
		lstData.add(rowx);
		result = ctrlist.draw();
	}else{
		if(iCommand==Command.ADD){
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_SORT_IDX] +"\" value=\""+objEntity.getAmountIs()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_LEVEL_CODE] +"\" value=\""+kdSalaryLevel+"\" class=\"formElemen\" size=\"40\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_LEVEL_NAME] +"\" value=\""+objEntity.getLevelName()+"\" class=\"formElemen\" size=\"40\">");
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_AMOUNT_IS], null, ""+objEntity.getAmountIs(),vKeyAmountGj,vValAmountGj));
			rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevel.FRM_FIELD_CUR_CODE], null, ""+objEntity.getCur_Code(),curr_value, curr_key, "formElemen"));
			rowx.add("<a href='salary-level-comp.jsp'>view details</a>");
			lstData.add(rowx);
			result = ctrlist.draw();
		}else{
			result = "<i>No Salary Level found ...</i>";
		}
	}
	return result;
}
%>

<%
// request data from jsp page
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidSalaryLevel = FRMQueryString.requestLong(request, "hidden_id_salary_level");
String sLevelCode = FRMQueryString.requestString(request, "salary_level_code");
String sLevelName = FRMQueryString.requestString(request, "salary_level_name");
String frmname = FRMQueryString.requestString(request, "frmname");

// variable declaration
int recordToGet = 100;
String msgString = "";
int iErrCode = FRMMessage.NONE;

// instantiate TaxType classes
CtrlSalaryLevel ctrlSalaryLevel = new CtrlSalaryLevel(request);
ControlLine ctrLine = new ControlLine();


// action on object agama defend on command entered
iErrCode = ctrlSalaryLevel.action(iCommand , oidSalaryLevel);
FrmSalaryLevel frmSalaryLevel = ctrlSalaryLevel.getForm();
SalaryLevel salaryLevel = ctrlSalaryLevel.getSalaryLevel();
msgString =  ctrlSalaryLevel.getMessage();

// get records to display
String whereClause = "";

if(sLevelCode!=null && sLevelCode.length()>0){
    whereClause = PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_CODE] + " LIKE \"%" + sLevelCode +"%\"";
}

if(sLevelName!=null && sLevelName.length()>0){
  if(sLevelCode!=null && sLevelCode.length()>0){
           whereClause = whereClause + " OR ";
  }
    whereClause = whereClause + PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_NAME] + " LIKE \"%" + sLevelName +"%\"";
}

String orderClause = PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_SORT_IDX] + "," + PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_CODE];

int vectSize = PstSalaryLevel.getCount(whereClause);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlSalaryLevel.actionList(iCommand, start, vectSize, recordToGet);
}

Vector listSalaryLevel = PstSalaryLevel.list(start, recordToGet, whereClause , orderClause);
if(listSalaryLevel.size()<1 && start>0){
	 if(vectSize - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listSalaryLevel = PstSalaryLevel.list(start, recordToGet, whereClause , orderClause);
}
%>
<%
int idx = FRMQueryString.requestInt(request, "idx");
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
	document.frmSalaryLevel.hidden_id_salary_level.value="0";
	document.frmSalaryLevel.command.value="<%=Command.ADD%>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdAsk(oidSalaryLevel){
	document.frmSalaryLevel.hidden_id_salary_level.value=oidSalaryLevel;
	document.frmSalaryLevel.command.value="<%=Command.ASK%>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frmSalaryLevel.command.value="<%=Command.DELETE%>";
			document.frmSalaryLevel.action="sel_salary-level.jsp";
			document.frmSalaryLevel.submit();
		}
}


function cmdSave(){
	document.frmSalaryLevel.command.value="<%=Command.SAVE%>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
	}
        
function searchLevel(){
	document.frmSalaryLevel.command.value="<%=Command.LIST %>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
	}        
        
function cmdListAll(){ 
	document.frmSalaryLevel.command.value="<%=Command.LIST %>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";        
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
    }                 


function cmdSelect(salaryLevel){
        self.opener.document.<%=frmname%>.level.value = salaryLevel;
        self.close();        
	}
	
function cmdView(salaryLevel,cur,code_cur){
    document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="salary-level-comp.jsp?salary_level=" + salaryLevel + "&cur=" + cur + "&code_cur=" + code_cur+
         "&start=0";
	document.frmSalaryLevel.submit();
	}

function cmdCancel(oidSalaryLevel){
	document.frmSalaryLevel.hidden_id_salary_level.value=oidSalaryLevel;
	document.frmSalaryLevel.command.value="<%=Command.EDIT%>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdBack(){
	document.frmSalaryLevel.command.value="<%=Command.BACK%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
	}

function cmdListFirst(){
	document.frmSalaryLevel.command.value="<%=Command.FIRST%>";
	document.frmSalaryLevel.prev_command.value="<%=Command.FIRST%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdListPrev(){
	document.frmSalaryLevel.command.value="<%=Command.PREV%>";
	document.frmSalaryLevel.prev_command.value="<%=Command.PREV%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
	}

function cmdListNext(){
	document.frmSalaryLevel.command.value="<%=Command.NEXT%>";
	document.frmSalaryLevel.prev_command.value="<%=Command.NEXT%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdListLast(){
	document.frmSalaryLevel.command.value="<%=Command.LAST%>";
	document.frmSalaryLevel.prev_command.value="<%=Command.LAST%>";
	document.frmSalaryLevel.action="sel_salary-level.jsp";
	document.frmSalaryLevel.submit();
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
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Salary 
                  Level<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="tablecolor" style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmSalaryLevel" method="post" action="">
									  <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_id_salary_level" value="<%=oidSalaryLevel%>">
                                      <input type="hidden" name="frmname" value="<%=frmname%>" >
                                      <table width="963" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                        <tr> 
                                          <td width="963">&nbsp;</td>
                                        </tr>
										<%
										try{
										%>
                                        <tr> 
                                          <td>
										 <%=drawList(iCommand, frmSalaryLevel, salaryLevel, listSalaryLevel, oidSalaryLevel)%> 
										  </td>
                                        </tr>
										<% 
										  }catch(Exception exc){ 
										  System.out.println("Err::::::"+exc.toString());
										  }%>
										
										<tr>
										<td>
										<table>
										  </table>
										</td>
										</tr>
                                        
                                      </table>
									  <table width="100%" border="0">
									  <tr> 
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
													  { 
														if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidSalaryLevel == 0))
															cmd = PstSalaryLevel.findLimitCommand(start,recordToGet,vectSize);
														else
															cmd = prevCommand;
													  } 
												   } 
												%>
												<% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
											</tr>
										  	<tr> 
											  <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmSalaryLevel.errorSize()<1)){%>
											  <td width="154" valign="middle"> <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> 
											  <a href="javascript:cmdAdd()" class="command">Add New Salary Level</a>
                                                                                          <a href="javascript:cmdListAll()" onMouseOut="MM_swapImgRestore()" 
                                                                                             onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSearch.jpg',1)">
                                                                                              <img name="Image264x" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" 
                                                                                                   alt="List All"></a> 
											  <a href="javascript:cmdListAll()" class="command">List All of Salary Levels</a>
                                                                                          </td>
											</tr>
									  <!--		<tr> 
                                          <td> <input type="submit" name="Submit" value="Regenerate Data Forms"> 
                                          </td>
										  <td width="0"></td>
                                        </tr>-->
										 <%}%>
										<tr> 
											<%
											   if((iCommand == Command.ADD || iCommand == Command.EDIT)){
											%>
											  <td colspan="2" valign="middle"> <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a>
												<a href="javascript:cmdSave()" class="command" style="text-decoration:none">Save Setup Salary Level</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
										 		 <a href="javascript:cmdConfirmDelete()" class="command">Delete Salary Level</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back"></a>
												<a href="javascript:cmdBack()" class="command" style="text-decoration:none">Back to List Salary Level</a></td>
											</tr>
											<%}%>
									</table>
                                      <br>
                                      Note : Salary Netto = tax will be paid by 
                                      company ; Brutto = tax paid by employee 
                                      ; None will paid no tax 
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
