 
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
 * Output 			: [output ...] 
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
<!--package common -->
<%@ page import = "com.dimata.common.entity.search.*" %>
<%@ page import = "com.dimata.common.form.search.*" %>
<%@ page import = "com.dimata.common.entity.contact.*" %>
<%@ page import = "com.dimata.common.session.contact.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_ORGANIZER , AppObjInfo.OBJ_TRAINING_ORGANIZER_CONTACT ); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!
public static String strTitle[][] = {
	{"Kelompok Kontak","Kode","Nama","Alamat","Urutkan Berdasar","Kota","Propinsi","Negara"},	
	{"Contact Class","Code","Name","Address","Sort By","Town","Province","Country"}
};

public static final String masterTitle[] = {
	"Pencarian Kontak","Contact Inquiries"	
};

public static final String compTitle[] = {
	"Kontak","Contact"
};

public String getJspTitle(String textJsp[][], int index, int language, String prefiks, boolean addBody){
	String result = "";
	if(addBody){ 
		if(language==com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT){	
			result = textJsp[language][index] + " " + prefiks;
		}else{
			result = prefiks + " " + textJsp[language][index];		
		}
	}else{
		result = textJsp[language][index];
	} 
	return result;
}

public boolean getTruedFalse(Vector vect, long index){
	for(int i=0;i<vect.size();i++){
		long iStatus = Long.parseLong((String)vect.get(i));
		if(iStatus==index)
			return true;
	}
	return false;
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
SrcContactList srcContactList = new SrcContactList();
FrmSrcContactList frmSrcContactList = new FrmSrcContactList();

/**
* ControlLine and Commands caption
*/
ControlLine ctrLine = new ControlLine();
String currPageTitle = compTitle[SESS_LANGUAGE];
String strAddComp = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_ADD,true);
String strSearchComp = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_SEARCH,true);

if(iCommand==Command.BACK){
	frmSrcContactList = new FrmSrcContactList(request, srcContactList);
	try{
		srcContactList = (SrcContactList)session.getValue(SessContactList.SESS_SRC_CONTACT_LIST);
		if(srcContactList == null) {
			srcContactList = new SrcContactList();
		}
	}catch(Exception e){
		srcContactList = new SrcContactList();
	}
}
%>
<html><!-- #BeginTemplate "/Templates/main-menu-left-frames.dwt" --><!-- DW6 -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Hairisma - <%= FRMQueryString.requestString(request, "title")  %></title>
<script language="JavaScript">
function cmdAdd(){
		document.frm_contactlist.command.value="<%=Command.ADD%>";
		document.frm_contactlist.action="contact_company_edit.jsp";
		document.frm_contactlist.submit(); 
}

function cmdSearch(){
		document.frm_contactlist.command.value="<%=Command.LIST%>";
		document.frm_contactlist.action="contact_company_list.jsp";
		document.frm_contactlist.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) { 
		document.all.aSearch.focus();
		cmdSearch();
   }
}
</script>
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language="javascript">
<!--
function hideObjectForMenuJournal(){ 
}
	
function hideObjectForMenuReport(){
}
	
function hideObjectForMenuPeriod(){
}
	
function hideObjectForMenuMasterData(){
}

function hideObjectForMenuSystem(){
}

function showObjectForMenu(){
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
-->
</SCRIPT>
<!-- #EndEditable -->
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->    
        <style type="text/css">
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                background-color: #FFF;
                margin: 25px 23px 59px 23px;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .btn {
                background: #ebebeb;
                background-image: -webkit-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -moz-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -ms-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -o-linear-gradient(top, #ebebeb, #dddddd);
                background-image: linear-gradient(to bottom, #ebebeb, #dddddd);
                -webkit-border-radius: 5;
                -moz-border-radius: 5;
                border-radius: 3px;
                font-family: Arial;
                color: #7a7a7a;
                font-size: 12px;
                padding: 5px 11px 5px 11px;
                border: solid #d9d9d9 1px;
                text-decoration: none;
            }

            .btn:hover {
                color: #474747;
                background: #ddd;
                background-image: -webkit-linear-gradient(top, #ddd, #CCC);
                background-image: -moz-linear-gradient(top, #ddd, #CCC);
                background-image: -ms-linear-gradient(top, #ddd, #CCC);
                background-image: -o-linear-gradient(top, #ddd, #CCC);
                background-image: linear-gradient(to bottom, #ddd, #CCC);
                text-decoration: none;
                border: 1px solid #C5C5C5;
            }
            
            .btn-small {
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            .btn-small:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {padding: 7px 0px 2px 0px; font-size: 12px; font-weight: bold; color: #575757;}
            #div_input {}
            
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                margin-bottom: 2px;
                border-bottom: 1px solid #DDD;
                background-color: #EEE;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                padding: 21px;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                margin-top: 2px;
                background-color: #EEE;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 18px 21px;
                background-color: #FF6666;
                color: #FFF;
                border: 1px solid #CF5353;
            }
            #btn-confirm {
                padding: 3px; border: 1px solid #CF5353; 
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                
            }
            
        </style>
</head> 

<body>
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
            </table>
        </div>
            <div class="content-main">
                <div class="content-title">
                    <div id="title-large">Pencarian Kontak: Kontak</div>
                    <div id="title-small">Pencraian kontak.</div>
                </div>

            <div class="content">
            <form name="frm_contactlist" method="post" action="">
              <input type="hidden" name="command" value="<%=iCommand%>">
			  <%try{%>
              <table border="0" width="100%">
                <tr> 
                  <td height="8">
                    
                  </td>
                </tr>
              </table>			  			  
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr align="left" valign="top"> 
                  <td colspan="2"> 
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                      <tr> 
                        <td width="97%"> 
                          <table border="0" cellspacing="2" cellpadding="2" width="100%">
                            <tr align="left" valign="top"> 
                              <td width="10%" nowrap><%=getJspTitle(strTitle,0,SESS_LANGUAGE,currPageTitle,false)%></td>
                              <td width="1%">:</td>
                              <td width="89%"> <div><table width="100%">
<%  
               
							  Vector vectClass = PstContactClass.listAll();
							  int iCount = 0;						
							  int iMaxColumn = 4;
							  int iMaxRow = vectClass.size() / iMaxColumn;
							  if(vectClass.size() % iMaxColumn > 0)
								iMaxRow += 1;					   
								for(int r = 0; r < iMaxRow; r++){
								%>
								 <tr>						  
							  		<%for(int i=0; i<iMaxColumn; i++){
							  			ContactClass cntClass = (ContactClass)vectClass.get(iCount);
										long idxContactClass = cntClass.getOID();							
										String strContactClass = cntClass.getClassName();							  							  
							  			%>
							  			<td><input type="checkbox" class="formElemen" name="<%=frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_TYPE]%>" value="<%=(idxContactClass)%>" <%if(getTruedFalse(srcContactList.getType(),idxContactClass)){%>checked<%}%> >
							  			<%=strContactClass%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td> <%iCount++;%> 
										<%if(iCount >= vectClass.size())
											break;
										%>
									<%}%>
									</tr>
								  <%}%>	
							  	
							  </table>
                                  </div> 
                                                                                                      
			    </td>
                            </tr>
                            <tr align="left" valign="top"> 
                              <td width="10%" nowrap> 
                                <div align="left"><%=getJspTitle(strTitle,1,SESS_LANGUAGE,currPageTitle,false)%></div>
                              </td>
                              <td width="1%">:</td>
                              <td width="89%">&nbsp; 
                                <input type="text" name="<%=frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_CODE] %>"  value="<%=srcContactList.getCode()%>" onkeydown="javascript:fnTrapKD()" size="20">
                              </td> 
                            </tr>
                            <script language="javascript">
								document.frm_contactlist.<%=frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_CODE]%>.focus();
							</script>
                            <tr align="left" valign="top"> 
                              <td width="10%" nowrap> 
                                <div align="left"><%=getJspTitle(strTitle,2,SESS_LANGUAGE,currPageTitle,false)%></div>
                              </td>
                              <td width="1%">:</td>
                              <td width="89%">&nbsp; 
                                <input type="text" name="<%=frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_NAME] %>"  value="<%=srcContactList.getName()%>" size="40" onkeydown="javascript:fnTrapKD()">
                              </td>
                            </tr>
                            <tr align="left" valign="top"> 
                              <td width="10%" nowrap> 
                                <div align="left"><%=getJspTitle(strTitle,3,SESS_LANGUAGE,currPageTitle,false)%></div>
                              </td>
                              <td width="1%">:</td>
                              <td width="89%">&nbsp; 
                                <input type="text" name="<%=frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_ADDRESS] %>"  value="<%=srcContactList.getAddress()%>" size="60" onkeydown="javascript:fnTrapKD()">
                              </td>
                            </tr>
							<!-- start update -->
							<tr align="left" valign="top"> 
                              <td width="10%" nowrap> 
                                <div align="left"><%=getJspTitle(strTitle,5,SESS_LANGUAGE,currPageTitle,false)%></div>
                              </td>
                              <td width="1%">:</td>
                              <td width="89%">&nbsp; 
                                <input type="text" name="<%=frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_CITY] %>"  value="<%=srcContactList.getCity()%>" size="40" onkeydown="javascript:fnTrapKD()">
                              </td>
                            </tr>
							<tr align="left" valign="top"> 
                              <td width="10%" nowrap> 
                                <div align="left"><%=getJspTitle(strTitle,6,SESS_LANGUAGE,currPageTitle,false)%></div>
                              </td>
                              <td width="1%">:</td>
                              <td width="89%">&nbsp; 
                                <input type="text" name="<%=frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_PROVINCE]%>"  value="<%=srcContactList.getProvince()%>" size="40" onkeydown="javascript:fnTrapKD()">
                              </td>
                            </tr>
							<tr align="left" valign="top"> 
                              <td width="10%" nowrap> 
                                <div align="left"><%=getJspTitle(strTitle,7,SESS_LANGUAGE,currPageTitle,false)%></div>
                              </td>
                              <td width="1%">:</td>
                              <td width="89%">&nbsp; 
                                <input type="text" name="<%=frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_COUNTRY] %>"  value="<%=srcContactList.getCountry()%>" size="40" onkeydown="javascript:fnTrapKD()">
                              </td>
                            </tr>
                            <tr align="left" valign="top"> 
                              <td width="10%" height="21" nowrap> 
                                <div align="left"><%=getJspTitle(strTitle,4,SESS_LANGUAGE,currPageTitle,false)%></div>
                              </td>
                              <td width="1%">:</td>
                              <td width="89%">&nbsp; 
                                <%= ControlCombo.draw(frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_ORDER],"formElemen",null, ""+srcContactList.getOrderBy(), frmSrcContactList.getOrderVal(), frmSrcContactList.getOrdKey(SESS_LANGUAGE), " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                            </tr>
							<!-- End Update -->
                            <tr align="left" valign="top"> 
                              <td width="10%">&nbsp;</td>
                              <td width="1%">&nbsp;</td>
                              <td width="89%">&nbsp;</td>
                            </tr>
                            <tr align="left" valign="top"> 
                              <td width="10%"> 
                                <div align="left"></div>
                              </td>
                              <td width="1%" class="command">&nbsp;</td>
                              <td width="89%" class="command">&nbsp;<a id="aSearch" href="javascript:cmdSearch()"><%=strSearchComp%></a> 
                                <% if(privAdd){%>
                                | <a href="javascript:cmdAdd()"><%=strAddComp%></a> 
                                <%}%>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
			  <%}catch(Exception e){
			  	System.out.println("err : "+e.toString());
				}
			  %>
            </form>
            </div>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
