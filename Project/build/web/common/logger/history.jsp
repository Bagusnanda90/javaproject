
<% 
/* 
 * Page Name  		:  history.jsp
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
<%@ page import = "java.util.*,
                   com.dimata.common.entity.logger.Logger,
                   com.dimata.common.form.search.FrmSrcLogger,
                   com.dimata.common.entity.search.SrcLogger,
                   com.dimata.common.session.logger.SessLogger" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_LOGIN_HISTORY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
%>

<!-- Jsp Block -->
<%!
public String drawList(Vector objectClass)

{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Login Id","20%");
	ctrlist.addHeader("Employee","20%");
	ctrlist.addHeader("Date","15%");
	ctrlist.addHeader("Proses","40%");

	ctrlist.setLinkRow(-1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	int index = -1;

	for (int i = 0; i < objectClass.size(); i++) {
		Logger logger = (Logger)objectClass.get(i);
		Vector rowx = new Vector();

		Employee emp = new Employee();
		try{
			AppUser appUserx = PstAppUser.fetch(logger.getLoginId());
			emp = PstEmployee.fetchExc(appUserx.getEmployeeId());
		}catch(Exception e){}

		rowx.add(logger.getLoginName());
		rowx.add(""+emp.getFullName());
		rowx.add(""+Formater.formatDate(logger.getDate(),"dd MMM yyyy, hh:mm:ss"));
		rowx.add(logger.getNotes());

		lstData.add(rowx);
		lstLinkData.add(String.valueOf(logger.getOID()));
	}
	return ctrlist.draw(index);
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");

/*variable declaration*/
int recordToGet = 20;
String msgString = "";
String whereClause = "";
String orderClause = " DATE DESC";

    ControlLine ctrLine = new ControlLine();
    SrcLogger srcLogger = new SrcLogger();
    FrmSrcLogger frmSrcLogger = new FrmSrcLogger(request, srcLogger);
    frmSrcLogger.requestEntityObject(srcLogger);

    if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)
            ||(iCommand==Command.LAST)||(iCommand == Command.BACK)){
        try{
            srcLogger = (SrcLogger)session.getValue(SessLogger.SESS_SRC_LOGGER);
            if (srcLogger == null) {
                srcLogger = new SrcLogger();
            }
        }catch(Exception e){
            srcLogger = new SrcLogger();
        }
    }else if(iCommand==Command.NONE)
        srcLogger = new SrcLogger();

    session.putValue(SessLogger.SESS_SRC_LOGGER,srcLogger);

/*count list All Position*/
int vectSize = SessLogger.countLogger(srcLogger);
/*switch list Position*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
     switch(iCommand){
         case Command.FIRST :
             start = 0;
             break;

         case Command.PREV :
             start = start - recordToGet;
             if(start < 0){
                start = 0;
             }
             break;

         case Command.NEXT :
             start = start + recordToGet;
             if(start >= vectSize){
                start = start - recordToGet;
             }
             break;

         case Command.LAST :
             int mdl = vectSize % recordToGet;
             if(mdl>0){
                 start = vectSize - mdl;
             }
             else{
                 start = vectSize - recordToGet;
             }

             break;

         default:
             start = start;
             if(vectSize<1)
                 start = 0;

             if(start > vectSize){
                 mdl = vectSize % recordToGet;
                 if(mdl>0){
                     start = vectSize - mdl;
                 }
                 else{
                     start = vectSize - recordToGet;
                 }
             }
             break;
     }
 }
/* end switch list*/

/* get record to display */
Vector listLogger = SessLogger.searchLogger(srcLogger,start,recordToGet);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listLogger.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
	 }
	 listLogger = SessLogger.searchLogger(srcLogger,start,recordToGet);
}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Login History</title>
<script language="JavaScript">
<!--
function cmdSearch(){
	document.frmposition.command.value="<%=Command.SEARCH%>";
    document.frmposition.start.value=0;
	document.frmposition.action="history.jsp";
	document.frmposition.submit();
	}

function cmdBack(){
	document.frmposition.command.value="<%=Command.BACK%>";
	document.frmposition.action="history.jsp";
	document.frmposition.submit();
	}

function cmdListFirst(){
	document.frmposition.command.value="<%=Command.FIRST%>";
	document.frmposition.action="history.jsp";
	document.frmposition.submit();
}

function cmdListPrev(){
	document.frmposition.command.value="<%=Command.PREV%>";
	document.frmposition.action="history.jsp";
	document.frmposition.submit();
	}

function cmdListNext(){
	document.frmposition.command.value="<%=Command.NEXT%>";
	document.frmposition.action="history.jsp";
	document.frmposition.submit();
}

function cmdListLast(){
	document.frmposition.command.value="<%=Command.LAST%>";
	document.frmposition.action="history.jsp";
	document.frmposition.submit();
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

function MM_preloadImages() { //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
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

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  System &gt; System History<!-- #EndEditable --> </strong></font>
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
                                    <form name="frmposition" method ="post" action="history.jsp">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top">
                                          <td height="8"  colspan="3"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top">
                                                <td height="14" valign="middle" colspan="3" class="listtitle">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td width="8%">&nbsp;</td>
                                                      <td width="92%">&nbsp;</td>
                                                    </tr>
                                                    <tr>
                                                      <td width="8%">&nbsp;Date</td>
                                                      <td width="92%">:&nbsp;<%=ControlDate.drawDateWithStyle(FrmSrcLogger.fieldNames[FrmSrcLogger.FRM_FIELD_START_DATE], srcLogger.getStartDate()==null?new Date():srcLogger.getStartDate(), 0, 5,"formElemen")%> to <%=ControlDate.drawDateWithStyle(FrmSrcLogger.fieldNames[FrmSrcLogger.FRM_FIELD_END_DATE], srcLogger.getEndDate()==null?new Date():srcLogger.getEndDate(), 0,5,"formElemen")%>
                                                      </td>
                                                    </tr>
                                                    <!--<tr>
                                                      <td>&nbsp;Employee</td>
                                                      <td>: 
                                                        <%/*
                                                            Vector empKey = new Vector(1,1);
                                                            Vector empValue = new Vector(1,1);
                                                            Vector listEmp = PstEmployee.list(0, 0, "", PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                                                            for(int i =0;i < listEmp.size();i++){
                                                                  Employee employee = (Employee)listEmp.get(i);
                                                                  empKey.add(employee.getFullName());
                                                                  empValue.add(""+employee.getOID());
                                                            }
                                                              */%> <%//=ControlCombo.draw(FrmSrcLogger.fieldNames[FrmSrcLogger.FRM_FIELD_EMPLOYEE_ID],"formElemen",null,""+srcLogger.getEmployeeId(),empValue,empKey )%> * <%//=frmSrcLogger.getErrorMsg(FrmSrcLogger.FRM_FIELD_EMPLOYEE_ID)%> </td>
                                                    </tr>-->
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                      <td><table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr> 
                                                            <td width="13%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search for History"></a></td>
                                                            <td width="5%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="82%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for History</a></td>
                                                          </tr>
                                                        </table></td>
                                                    </tr>
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                  </table></td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;System 
                                                  History List </td>
                                              </tr>
                                              <%
							try{
								if (listLogger.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listLogger)%> </td>
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
                                       else
                                        cmd = Command.FIRST;
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                              </tr>
                                            </table></td>
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

