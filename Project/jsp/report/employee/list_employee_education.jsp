
<% 
/* 
 * Page Name  		:  srcemployee.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 				: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>

<%!
public Hashtable setHashTotal(Hashtable hasTotal, String religion, int qtyMale, int qtyFemale) {
	try {
		if(hasTotal.get(religion) != null) {
			Vector temp1 = (Vector)hasTotal.get(religion);
			int totalMale = Integer.parseInt((String)temp1.get(0));
			int totalFemale = Integer.parseInt((String)temp1.get(1));
			
			totalMale = totalMale + qtyMale;
			totalFemale = totalFemale + qtyFemale;
			temp1 = new Vector();
			temp1.add(String.valueOf(totalMale));
			temp1.add(String.valueOf(totalFemale));
			
            hasTotal.remove(religion);
            hasTotal.put(religion, temp1);
		}
		else {
			Vector temp2 = new Vector(1,1);
			temp2.add(String.valueOf(qtyMale));
			temp2.add(String.valueOf(qtyFemale));
			hasTotal.put(religion, temp2);
		}
	}
	catch(Exception e){
	}
	return hasTotal;
}

%>

<%!
public Vector drawList(Vector objectClass, Vector vListEducation, SrcEmployee srcEmployee, int iCommand, long oidPosition)
{
	Vector vListReligion = PstReligion.list(0,0,"","");
	
	Vector result = new Vector(1,1);
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.dataFormat("No.","3%","2","0","center","left");
	ctrlist.dataFormat(" Level","14%","2","0","center","left");
	ctrlist.dataFormat("Education","10%","2","0","center","left");
	
	
	if(vListReligion!=null && vListReligion.size()>0)
	{
                int width = 65/ (vListReligion.size() * 2);
            
		for(int i=0;i<vListReligion.size();i++)
		{
			Religion objReligion = (Religion)vListReligion.get(i);
			String nmReligi = objReligion.getReligion();
			
			String sIdx = String.valueOf(width)+"%";
			ctrlist.dataFormat(nmReligi,sIdx,"0","2","center","left");
		}
	}
	ctrlist.dataFormat("Total","8%","2","0","center","left");
	
	if(vListReligion!=null && vListReligion.size()>0)
	{
                int width = 65 / (vListReligion.size() * 2);
            
		for(int i=0;i<vListReligion.size();i++)
		{
			
			String sIdx = String.valueOf(width)+"%";
			ctrlist.dataFormat("M",sIdx,"0","0","center","left");
			ctrlist.dataFormat("F",sIdx,"0","0","center","left");
		}
	}
	
	
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	int l = 0;
	int sumReligionFemale = 0;
	
	Hashtable has1 = new Hashtable();
	
	Vector vListPdf = new Vector(1,1);
	
	if(objectClass!=null && objectClass.size()>0){
	
		
		for(int k=0; k<objectClass.size(); k++){
			rowx = new Vector(1,1);
			Vector temp = (Vector)objectClass.get(k);
			Level objLevel = (Level)temp.get(0);
			
			l = l + 1;
			rowx.add(String.valueOf(l));
			rowx.add(objLevel.getLevel());
			
			// lopping pendidikan
			Hashtable has = new Hashtable();
			for(int j=0; j<vListEducation.size(); j++){
				Education education = (Education)vListEducation.get(j);
								
				if(j!=0){
					rowx = new Vector(1,1);
					rowx.add("");
					rowx.add("");
				}
				
				rowx.add(education.getEducation());
			    
                                int countJumlah = 0;
                                
				// lopping agama
				if(vListReligion!=null && vListReligion.size()>0){
				
					for(int i=0;i<vListReligion.size();i++){
						Religion objRelegion = (Religion)vListReligion.get(i);
						
							int countMale = SessEmployee.listEducationEmpMale(education.getOID(),objLevel.getOID(),objRelegion.getOID(),srcEmployee,oidPosition);
							int countFemale = SessEmployee.listEducationEmpFemale(education.getOID(),objLevel.getOID(),objRelegion.getOID(),srcEmployee,oidPosition);
						
						// for set total per religion
						has = setHashTotal(has,objRelegion.getReligion(),countMale,countFemale);
						rowx.add(String.valueOf(countMale));
						rowx.add(String.valueOf(countFemale));	
                                                
                                                countJumlah += countMale + countFemale;			
						
					}
				}
				
				//int countJumlah = SessEmployee.listEducationJum(education.getOID(),objLevel.getOID(),srcEmployee,oidPosition);
				rowx.add(String.valueOf(countJumlah));
				lstData.add(rowx);
				vListPdf.add(rowx);
			}
			rowx = new Vector(1,1);
			rowx.add("");
			rowx.add("");	
			rowx.add("Total");
			int totalAkhir = 0;
			 if(vListReligion!=null && vListReligion.size()>0){
					for(int i=0;i<vListReligion.size();i++){
						Religion objRelegion = (Religion)vListReligion.get(i);
						Vector vect = (Vector)has.get(objRelegion.getReligion());
						
						int totMale = Integer.parseInt((String)vect.get(0));
						int totFemale = Integer.parseInt((String)vect.get(1));
						totalAkhir = totalAkhir + ( totMale+totFemale );
						has1 = setHashTotal(has1,objRelegion.getReligion(),totMale,totFemale);
						rowx.add(vect.get(0));
						rowx.add(vect.get(1));
					}
			}
			
			rowx.add(""+totalAkhir);	
			lstData.add(rowx);
			vListPdf.add(rowx);
			
			rowx = new Vector(1,1);
			rowx.add("&nbsp");
			rowx.add("&nbsp");	
			rowx.add("&nbsp");
			
			if(vListReligion!=null && vListReligion.size()>0){
					for(int i=0;i<vListReligion.size();i++){
						rowx.add("&nbsp");
						rowx.add("&nbsp");
					}
			}
			rowx.add("&nbsp");	
			lstData.add(rowx);
			//vListPdf.add(rowx);			
		}
	}
	rowx = new Vector(1,1);
	rowx.add("");
	rowx.add("TOTAL");
	rowx.add("");
	int totFinish = 0;
	 		if(vListReligion!=null && vListReligion.size()>0){
					for(int i=0;i<vListReligion.size();i++){
						Religion objRelegion = (Religion)vListReligion.get(i);
						Vector vect1 = (Vector)has1.get(objRelegion.getReligion());
						
						int totMale1 = Integer.parseInt((String)vect1.get(0));
						int totFemale1 = Integer.parseInt((String)vect1.get(1));
						totFinish = totFinish + ( totMale1+totFemale1 );
						rowx.add(vect1.get(0));
						rowx.add(vect1.get(1));
						
					}
			}
	rowx.add(""+totFinish);
	lstData.add(rowx);
	vListPdf.add(rowx);
	
	result.add(ctrlist.drawMeWithColsList());
	result.add(vListPdf);
	return result;

}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
SrcEmployee srcEmployee = new SrcEmployee();
FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
long oidLevel = FRMQueryString.requestLong(request,"level");
long oidPosition = FRMQueryString.requestLong(request,"position");


int rowSpan = 0;

Vector tempLevel = new Vector(1,1);
Vector tempEducation = new Vector(1,1);

tempLevel = SessEmployee.tmpNmLevel(oidLevel);
tempEducation = SessEmployee.tmpNmPendidikan();
Vector vListReligion = PstReligion.list(0,0,"","");


Vector vectDataToPdf = new Vector();
String listData = "";
Vector vListResult = new Vector(1,1);
if(iCommand == Command.LIST || iCommand == Command.PRINT)
{
		frmSrcEmployee.requestEntityObject(srcEmployee);
		//System.out.println("srcEmployee.getStartCommenc()Jsp:::::::::::::::::::::::::::::"+srcEmployee.getStartCommenc());
		//System.out.println("srcEmployee.getEndCommenc()Jsp:::::::::::::::::::::::::::::"+srcEmployee.getEndCommenc());
		vListResult = drawList(tempLevel, tempEducation, srcEmployee, iCommand,oidPosition);
		listData = String.valueOf(vListResult.get(0));
		vectDataToPdf = (Vector)vListResult.get(1);
}


if(iCommand==Command.BACK)
{
	frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
	try
	{
		srcEmployee = (SrcEmployee)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
		if(srcEmployee == null) 
		{
			srcEmployee = new SrcEmployee();
		}
		System.out.println("ecccccc "+srcEmployee.getOrderBy());
	}
	catch (Exception e)
	{
		srcEmployee = new SrcEmployee();
	}
}

Vector temp = new Vector();
	temp.add(String.valueOf(oidLevel));
	temp.add(srcEmployee);
	temp.add(vectDataToPdf);
	
	if(session.getValue("LIST_EMP_EDUCATION")!=null){
		session.removeValue("LIST_EMP_EDUCATION");
	}
	session.putValue("LIST_EMP_EDUCATION",temp);
	//System.out.println("temp:::::::::::::::::::::::"+temp);



%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Search Employee</title>
<script language="JavaScript">
<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpEducationPdf");
<%}%>

function cmdAdd(){
		document.frmempeducation.command.value="<%=Command.ADD%>";
		document.frmempeducation.action="list_employee_education.jsp";
		document.frmempeducation.submit();
}

function reportPdf(){
    document.frmempeducation.command.value="<%=Command.PRINT%>";
	document.frmempeducation.action="list_employee_education.jsp";
	document.frmempeducation.submit();
}

function cmdSearch(){
		document.frmempeducation.command.value="<%=Command.LIST%>";
		document.frmempeducation.action="list_employee_education.jsp";
		document.frmempeducation.submit();
}

function cmdSpecialQuery(){
		document.frmempeducation.action="specialquery.jsp";
		document.frmempeducation.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function cmdUpdateLevp(){
	document.frmempeducation.command.value="<%=Command.ADD%>";
	document.frmempeducation.action="list_employee_education.jsp"; 
	document.frmempeducation.submit();
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
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
			  <!-- #BeginEditable "contenttitle" --> 
                  Report &gt;Employee &gt; Employee Search Education <!-- #EndEditable --> 
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
                                    <form name="frmempeducation" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td valign="middle" colspan="2"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%">&nbsp;</td>
                                                <td width="0%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%"> <table border="0" cellspacing="2" cellpadding="2" width="74%">
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap>Level</td>
                                                      <td width="83%"> <%
                                         				  Vector deptValue = new Vector(1,1);
														  Vector deptKey = new Vector(1,1);
														  deptValue.add("0");
														  deptKey.add("All Level...");
														  Vector listLevel = PstLevel.list(0, 0, "", "LEVEL");
														  for(int d=0;d<listLevel.size();d++){
															Level level = (Level)listLevel.get(d);
															deptValue.add(""+level.getOID());
															deptKey.add(level.getLevel());
														  }
														  %> <%=ControlCombo.draw("level",null,""+oidLevel,deptValue,deptKey,"")%> </td>
                                                    </tr>
													
													<tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap>Position</td>
                                                      <td width="83%"> <%
                                         				  Vector posValue = new Vector(1,1);
														  Vector posKey = new Vector(1,1);
														  posValue.add("0");
														  posKey.add("All Postion...");
														  Vector listPosition = PstPosition.list(0, 0, "", "POSITION");
														  for(int d=0;d<listPosition.size();d++){
															Position position = (Position)listPosition.get(d);
															posValue.add(""+position.getOID());
															posKey.add(position.getPosition());
														  }
														  %> <%=ControlCombo.draw("position",null,""+oidPosition,posValue,posKey,"onChange=\"javascript:cmdUpdateLevp()\"")%> </td>
                                                    </tr>
													
													<tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap>Commencing Date</td>
                                                      <td width="83%">
                                         				<%=ControlDate.drawDateWithStyle(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC], srcEmployee.getStartCommenc(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> &nbsp;to&nbsp; 
                                                        <%=ControlDate.drawDateWithStyle(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC], srcEmployee.getEndCommenc(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>                                          </tr>
														<tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap>Resigned 
                                                        Status </td>
                                                      <td width="83%"> <input type="radio" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RESIGNED]%>" value="0" checked>
                                                        No 
                                                        <input type="radio" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RESIGNED]%>" value="1">
                                                        Yes 
                                                        </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%"> <div align="left"></div></td>
                                                      <td width="83%"> <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr> 
                                                            <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for Employee</a></td>
                                                            <% 
															if(privAdd)
															{
															%>
                                                            <%
															}
															else
															{
															%>
                                                            <td width="2%">&nbsp;</td>
                                                            <%
															}
															%>
                                                          </tr>
                                                        </table></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td>&nbsp;</td>
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                  </table></td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2">&nbsp;</td>
                                              </tr>
                                              <%if(iCommand == Command.LIST || iCommand==Command.PRINT){%>
                                              <tr> 
                                                <td colspan="2"></td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2"></td>
                                              </tr>
                                              <tr> 
                                                <td height="29" colspan="2">
												<%//=drawList(tempLevel, tempEducation, srcEmployee)
												out.println(listData);
												%>
												</td>
                                              </tr>
                                              <tr> 
                                                <td height="43" colspan="2"></td>
                                              </tr>
                                              <tr>
                                                <td height="43" colspan="2">
												 <table width="27%" border="0" cellspacing="1" cellpadding="1">
												  <tr>
													  <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
													  <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
														Education</a></b>
													  </td>
													</tr>
											  	</table>
												</td>
                                              </tr>
                                              <%
											  }
											  %>
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

