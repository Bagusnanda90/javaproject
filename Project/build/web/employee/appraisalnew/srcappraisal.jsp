
<% 
/* 
 * Page Name  		:  srcdevimprovementplan.jsp
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_PERFORMANCE_APPRAISAL); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<!-- Jsp Block -->
<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));

System.out.println("hrdDepartmentOid : "+hrdDepartmentOid+" , OID_HRD_DEPARTMENT "+OID_HRD_DEPARTMENT);

boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

Vector depGroup = PstSystemProperty.getSystemPropertyGroupVector("JOIN_DEPARMENT", ""+departmentOid);

int iCommand = FRMQueryString.requestCommand(request);
long oidEmpAppraisal = FRMQueryString.requestLong(request, "employee_appraisal_oid");
SrcAppraisal srcAppraisal = new SrcAppraisal();
FrmSrcAppraisal frmSrcAppraisal = new FrmSrcAppraisal();

if(iCommand==Command.BACK)
    {        
        frmSrcAppraisal = new FrmSrcAppraisal(request, srcAppraisal);
		try{
			srcAppraisal = (SrcAppraisal)session.getValue(SessAppraisal.SESS_SRC_APPRAISAL);
			if(srcAppraisal == null)
				srcAppraisal = new SrcAppraisal();
		}catch(Exception e){
			srcAppraisal = new SrcAppraisal();
		}
	}

try{
	session.removeValue(SessAppraisal.SESS_SRC_APPRAISAL);
}catch(Exception e){
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Search Assessment</title>
<script language="JavaScript">

function cmdAdd(){
	document.frmsrcappraisal.command.value="<%=Command.ADD%>";
	document.frmsrcappraisal.action="empappraisal_edit.jsp";
	document.frmsrcappraisal.submit();
}

function cmdSearch(){
	document.frmsrcappraisal.command.value="<%=Command.LIST%>";
	document.frmsrcappraisal.action="empappraisal_list.jsp";
	document.frmsrcappraisal.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
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
<!--
function hideObjectForEmployee(){  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATESTART]+"_dy"%>.style.visibility="hidden";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATESTART]+"_yr"%>.style.visibility="hidden";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATEEND]+"_dy"%>.style.visibility="hidden";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATEEND]+"_mn"%>.style.visibility="hidden";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATEEND]+"_yr"%>.style.visibility="hidden";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_RANK]%>.style.visibility="hidden";  
} 
	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATEEND]+"_dy"%>.style.visibility="hidden";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATEEND]+"_yr"%>.style.visibility="hidden";  
}

function showObjectForMenu(){
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATESTART]+"_dy"%>.style.visibility="";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATESTART]+"_yr"%>.style.visibility="";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATEEND]+"_dy"%>.style.visibility="";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATEEND]+"_mn"%>.style.visibility="";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATEEND]+"_yr"%>.style.visibility="";  
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_RANK]%>.style.visibility="";  
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
</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
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
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Search Employee Assessment <!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>;"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcappraisal" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                     <input type="hidden" name=" employee_appraisal_oid" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="1">
                                        <tr align="left"> 
                                          <td height="21" valign="middle" width="17%">&nbsp;</td>
                                          <td height="21" width="1%" class="comment" valign="top">&nbsp;</td>
                                          <td height="21" width="82%" class="comment" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" width="17%" align="right">Employee 
                                            Name </td>
                                          <td height="21" width="1%" valign="top">:</td>
                                          <td height="21" width="82%" valign="top"> 
                                            <input type="text" name="<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_EMPLOYEE]%>" value="<%=srcAppraisal.getEmployee()%>" size="50" onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" width="17%" align="right"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td height="21" width="1%" valign="top">:</td>
                                          <td height="21" width="82%" valign="top"> 
                                            <% 
                                            
                                                 Vector dept_value = new Vector(1,1);
                                                 Vector dept_key = new Vector(1,1);   
                                                 
                                                 if(isHRDLogin){
                                                        
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        dept_key.add("all department...");
                                                        dept_value.add("0");
                                                        
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                 }else{
                                                        String where = "";
                                                        int max = depGroup.size() - 1;
                                                        for(int i = 0; i < depGroup.size() ; i++){
                                                            if(i==0 && i != max){
                                                                
                                                                where = where + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = "+ depGroup.get(i)+" OR ";
                                                                
                                                            }else{
                                                                if(i==max){
                                                                    
                                                                    where = where + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = "+ depGroup.get(i);
                                                                    
                                                                }else{
                                                                    
                                                                    where = where + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ " = "+ depGroup.get(i)+" OR ";
                                                                    
                                                                }
                                                                
                                                            }
                                                        }
                                                    
                                                        Vector listDept = PstDepartment.list(0, 0, where, null);
                                                        dept_key.add("all department...");
                                                        dept_value.add("0");
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                     
                                                 }     
                                              %>
                                              <%= ControlCombo.draw(frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DEPARTMENT_ID],"formElemen",null,String.valueOf(srcAppraisal.getDepartmentId()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDep()\"") %>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" width="17%" align="right">Appraisor</td>
                                          <td height="21" width="1%" valign="top">:</td>
                                          <td height="21" width="82%" valign="top"> 
                                            <input type="text" name="<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_APPRAISOR]%>" value="<%=srcAppraisal.getAppraisor()%>" size="50" onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" width="17%" align="right">Score 
                                            Average </td>
                                          <td height="21" width="1%" valign="top">:</td>
                                          <td height="21" width="82%" valign="top"> 
                                            <input type="text" name="<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_AVERAGESTART] %>"  value="<%if((srcAppraisal.getAveragestart()-0.0f)>0.0f){%><%= srcAppraisal.getAveragestart() %><%}%>" class="elemenForm" size="10" onkeydown="javascript:fnTrapKD()">
                                            to 
                                            <input type="text" name="<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_AVERAGEEND] %>"  value="<%if((srcAppraisal.getAverageend()-0.0f)>0.0f){%><%= srcAppraisal.getAverageend() %><%}%>" class="elemenForm" size="10" onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" width="17%" align="right">Rank</td>
                                          <td height="21" width="1%" valign="top">:</td>
                                          <td height="21" width="82%" valign="top"> 
                                            <%  Vector rank_value = new Vector(1,1);
												Vector rank_key = new Vector(1,1);
												rank_value.add("0");
												rank_key.add(" All Rank ");
												Vector listRank = PstGroupRank.listAll();
												for(int i = 0;i <listRank.size();i++){
													GroupRank groupRank = (GroupRank)listRank.get(i);
													rank_value.add(""+groupRank.getOID());
													rank_key.add(groupRank.getGroupName());
												}
											  %>
                                            <%= ControlCombo.draw(frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_RANK],"elementForm", null, ""+srcAppraisal.getRank(), rank_value, rank_key, "onkeydown=\"javascript:fnTrapKD()\"") %> 
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" width="17%" align="right">Date 
                                            of Appraisal </td>
                                          <td height="21" width="1%" valign="top">:</td>
                                          <td height="21" width="82%" valign="top"> 
                                            <%=	ControlDate.drawDateWithStyle(frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATESTART], srcAppraisal.getDatestart(), 0,-5,"formElemen", "onkeydown=\"javascript:fnTrapKD()\"") %> 
                                            to <%=	ControlDate.drawDateWithStyle(frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_DATEEND], srcAppraisal.getDateend(), 0,-5, "formElemen", "onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                        </tr>
                                         <tr align="left"> 
                                          <td height="21" width="17%" align="right">Approval Status</td>
                                          <td height="21" width="1%" valign="top">:</td>
                                          <td height="21" width="82%" valign="top"> 
                                            <%  Vector approvedValue = new Vector(1,1);
                                                Vector approvedKey = new Vector(1,1);
                                                approvedValue.add("0");
                                                approvedKey.add(" All Approval");
                                                approvedValue.add("1");
                                                approvedKey.add(" Not Approved");
                                                approvedValue.add("2");
                                                approvedKey.add(" Approved");
                                              %>
                                            <%= ControlCombo.draw(frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_APPROVED],"elementForm", null, ""+srcAppraisal.getApproved(), approvedValue, approvedKey, "onkeydown=\"javascript:fnTrapKD()\"") %> 
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" width="17%" align="right">Sort 
                                            By </td>
                                          <td height="21" width="1%" valign="top">:</td>
                                          <td height="21" width="82%" valign="top"><%=	ControlCombo.draw(frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_ORDER_BY], "formElemen",null,""+srcAppraisal.getOrderBy(), FrmSrcAppraisal.getOrderValue(),FrmSrcAppraisal.getOrderKey(),"onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="21" valign="top" width="17%">&nbsp;</td>
                                          <td height="21" width="1%" valign="top">&nbsp;</td>
                                          <td height="21" width="82%" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td height="30" valign="top">&nbsp; </td>
                                          <td height="30" valign="top">&nbsp;</td>
                                          <td height="30" valign="top"> 
                                            <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>                                                 
                                                <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Appraisal"></a></td>
                                                <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="26%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                  for Assessment</a></td>
                                                <% if(privAdd){%>
												<td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="20%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Appraisal"></a></td>
                                                <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="22%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                  New Assessment</a></td>
                                                <%}else{%>
												<td width="50%">&nbsp;</td>
												<%}%>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="21" valign="top" width="17%">&nbsp;</td>
                                          <td width="1%">&nbsp;</td>
                                          <td width="82%">&nbsp;</td>
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
	document.frmsrcappraisal.<%=frmSrcAppraisal.fieldNames[FrmSrcAppraisal.FRM_FIELD_EMPLOYEE]%>.focus();
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
