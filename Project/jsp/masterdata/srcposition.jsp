
<% 
/* 
 * Page Name  		:  srcPosition.jsp
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
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
SrcPosition srcPosition = new SrcPosition();
FrmSrcPosition frmSrcPosition  = new FrmSrcPosition();

if(iCommand==Command.BACK)
{        
    frmSrcPosition  = new FrmSrcPosition (request, srcPosition);
    try
    {			
        srcPosition = (SrcPosition) session.getValue(PstPosition.SESS_HR_POSITION);			
        if(srcPosition== null)
        {
            srcPosition = new SrcPosition();
        }		
    }
    catch (Exception e)
    {
        srcPosition = new SrcPosition();
    }
}
I_Dictionary dictionaryD = userSession.getUserDictionary();
dictionaryD.loadWord();
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Position</title>
<script language="JavaScript">
<!--
function cmdSearch(){ 
	document.frmsrcposition.command.value="<%=Command.LIST%>";
	document.frmsrcposition.action="position.jsp";
	document.frmsrcposition.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
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
</script>
<style type="text/css">
    .btn {
        background: #3498db;
        border: 1px solid #0066CC;
        border-radius: 3px;
        font-family: Arial;
        color: #ffffff;
        font-size: 12px;
        padding: 3px 9px 3px 9px;
     }

    .btn:hover {
      background: #3cb0fd;
      border: 1px solid #3498db;
    }
    .tblStyle {border-collapse: collapse;font-size: 11px;}
    .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
    .title_content {
        padding: 9px 14px; 
        border-left: 1px solid #0099FF; 
        font-size: 14px; 
        background-color: #F3F3F3; 
        color:#0099FF;
        font-weight: bold;
    }
    .title_part {
        padding: 9px 14px; 
        border-left: 1px solid #0099FF; 
        font-size: 12px; 
        background-color: #F3F3F3; 
        color:#333; 
        font-weight: bold;
    }
    .part_content {
        border:1px solid #0099FF;
        border-radius: 5px;
        background-color: #F5F5F5;
    }
    .part_name {
        padding: 12px 19px; border-bottom: 1px solid #0099FF;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        background-color: #a9d5f2;
        color:#04619e;
        font-weight: bold;
        font-size: 12px;
    }
</style>
<link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
<script src="<%=approot%>/javascripts/jquery.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
<script>
$(function() {
    $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
});
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
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
                <td height="20">
                    <div class="title_content">Searching / Position</div>
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcposition" method="post" action="javascript:cmdSearch()">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                            <td valign="top" colspan="2" style="padding: 21px;"> 
                                            <table cellspacing="2" cellpadding="2">
                                             
                                                
                                                <tr>
                                                    <td valign="middle"><strong>Waktu</strong></td>
                                                    <%
                                                        if (srcPosition.getRadioButton() == 1){
                                                            %>
                                                            <td valign="middle"><input type="radio" name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_RADIO_BTN]%>" checked="checked" value="1" /> Current
                                                                <input type="radio" name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_RADIO_BTN]%>" value="2" /> History
                                                                <input type="text" name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_START_DATE]%>" id="datepicker1" value="<%=srcPosition.getStartDate() %>" />&nbsp;to
                                                                &nbsp;<input type="text" name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_END_DATE]%>" id="datepicker2" value="<%=srcPosition.getEndDate() %>" />
                                                            </td>
                                                            <%
                                                        } else {
                                                            // history
                                                            %>
                                                            <td valign="middle"><input type="radio" name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_RADIO_BTN]%>" value="1" /> Current
                                                                <input type="radio" name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_RADIO_BTN]%>" checked="checked" value="2" /> History
                                                                <input type="text" name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_START_DATE]%>" id="datepicker1" value="<%=srcPosition.getStartDate() %>" />&nbsp;to
                                                                &nbsp;<input type="text" name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_END_DATE]%>" id="datepicker2" value="<%=srcPosition.getEndDate() %>" />
                                                            </td>
                                                            <%
                                                        }
                                                    %>
                                                </tr>
                                                
                                              <tr> 
                                                <td> 
                                                    <div align="left"><strong><%=dictionaryD.getWord("NAME")%></strong></div>
                                                </td>
                                                <td> 
                                                    <input type="text" name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_NAME]%>"  value="<%=srcPosition.getPosName() %>" class="elemenForm"  onkeypress="javascript:fnTrapKD()" size="40">
                                                </td>
                                                <script language="javascript">
                                                    document.frmsrcposition.<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_NAME]%>.focus();
                                                </script>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                    <div align="left"><strong><%=dictionaryD.getWord("TYPE")%></strong></div>
                                                </td>
                                                <td> 
                                                <%
                                                Vector levelKey = new Vector(1,1);
                                                Vector levelValue = new Vector(1,1);

                                                levelKey.add("All Type");
                                                levelValue.add("-1");	

                                                for(int idx=0; idx < PstPosition.strPositionLevelNames.length;idx++){																							
                                                    levelKey.add(PstPosition.strPositionLevelNames[idx]);
                                                    levelValue.add(PstPosition.strPositionLevelValue[idx]);														
                                                  }

                                                /*levelKey.add(PstPosition.strPositionLevelNames[PstPosition.LEVEL_SECRETARY]);
                                                levelValue.add(""+PstPosition.LEVEL_SECRETARY);														

                                                levelKey.add(PstPosition.strPositionLevelNames[PstPosition.LEVEL_SUPERVISOR]);
                                                levelValue.add(""+PstPosition.LEVEL_SUPERVISOR);														

                                                levelKey.add(PstPosition.strPositionLevelNames[PstPosition.LEVEL_MANAGER]);
                                                levelValue.add(""+PstPosition.LEVEL_MANAGER);														

                                                levelKey.add(PstPosition.strPositionLevelNames[PstPosition.LEVEL_GENERAL_MANAGER]);
                                                levelValue.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														
* */

                                                out.println(ControlCombo.draw(frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_LEVEL], "formElemen", null, ""+srcPosition.getPosLevel(), levelValue, levelKey));
                                                %>												  
                                                </td>												  
                                              </tr>
                                              <tr>
                                                  <td valign="middle"><strong>Level</strong></td>
                                                  <td valign="middle">
                                                      <select name="<%=frmSrcPosition.fieldNames[frmSrcPosition.FRM_FIELD_LEVEL_RANK_ID]%>">
                                                          <option value="0">-SELECT-</option>
                                                          <%
                                                          String order = PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" ASC";
                                                          Vector listLevelRank = PstLevel.list(0, 0, "", order);
                                                          if (listLevelRank != null && listLevelRank.size()>0){
                                                              for(int i=0; i<listLevelRank.size(); i++){
                                                                  Level level = (Level)listLevelRank.get(i);
                                                                  if (srcPosition.getLevelRankID() == level.getLevelRank() && srcPosition.getLevelRankID() != 0){
                                                                      %>
                                                                      <option value="<%=level.getOID()%>" selected="selected"><%=level.getLevel()%></option>
                                                                      <%
                                                                  } else {
                                                                      %>
                                                                      <option value="<%=level.getOID()%>"><%=level.getLevel()%></option>
                                                                      <%
                                                                  }
                                                                  
                                                              }
                                                          }
                                                          %>
                                                      </select>
                                                  </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                    &nbsp;
                                                </td>
                                                <td>
                                                    <button class="btn" onclick="cmdSearch()"><%=dictionaryD.getWord("SEARCH")%></button>
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
<!-- #BeginEditable "script" --> <!-- #EndEditable --> 
<!-- #EndTemplate --></html>
