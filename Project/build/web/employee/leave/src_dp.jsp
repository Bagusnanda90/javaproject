<%@page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.form.search.FrmSrcEmployee" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.AlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstAlUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessAlUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<%// int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%//@include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%!
public String drawList(Vector objectClass, int st){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No.","5%");
	ctrlist.addHeader("Owning Date","65%");
	ctrlist.addHeader("Qty","10%");
	ctrlist.addHeader("On Request","10%");
	ctrlist.addHeader("Entitled","10%");
	//ctrlist.addHeader("Section","12%");
		
	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	for (int i = 0; i < objectClass.size(); i++) {
		Vector temp = (Vector)objectClass.get(i);
		DpStockManagement dpStockMan = (DpStockManagement)temp.get(0);
		int onRequest = Integer.parseInt((String)temp.get(1));
                
		Vector rowx = new Vector();
		rowx.add(String.valueOf(st + 1 + i));
		rowx.add(Formater.formatDate(dpStockMan.getDtOwningDate(), "dd MMMM yyyy"));
		rowx.add(String.valueOf(dpStockMan.getQtyResidue()));
		rowx.add(String.valueOf(onRequest));
		rowx.add(String.valueOf(dpStockMan.getQtyResidue()-onRequest));
		
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(dpStockMan.getOID()) + "','" + Formater.formatDate(dpStockMan.getDtOwningDate(), "dd MMMM yyyy"));
	}
	return ctrlist.draw();
}
%>

<%


    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    boolean status = false;
    int iErrCode = FRMMessage.ERR_NONE;
    long employeeId = FRMQueryString.requestLong(request,"employeeId");
    long takenDate = FRMQueryString.requestLong(request,"takenDate");
    String formName = FRMQueryString.requestString(request,"formName");
    String fieldNameVal = FRMQueryString.requestString(request,"fieldNameVal");
    String fieldNameKey = FRMQueryString.requestString(request,"fieldNameKey");
    
    ControlLine ctrLine = new ControlLine();
    CtrlDpStockManagement ctrlDp = new CtrlDpStockManagement(request);
    SessDpStockManagement sessDpStockMan = new SessDpStockManagement();
    
    int recordToGet = 10;
    int vectSize = sessDpStockMan.countDpStockAvailable(employeeId,new Date(takenDate));
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        start = ctrlDp.actionList(iCommand, start, vectSize, recordToGet);
    }else{
        start = 0;
        iCommand = Command.LIST;
    }
   Vector listDp = new Vector(1,1);
   try{
        listDp = sessDpStockMan.listDpStockAvailable(start,recordToGet,employeeId,new Date(takenDate));
   }catch(Exception ex){
   }
   
  // System.out.println("SIZE :: "+listDp.size());
%>

<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Search Employee</title>
<script language="JavaScript">
    
function cmdEdit(oid, strDate) {
   // alert("< %//=fieldNameVal%>");
        self.opener.document.<%=formName%>.<%=fieldNameVal%>.value = strDate;
        self.opener.document.<%=formName%>.<%=fieldNameKey%>.value = oid;
        self.close();
    }

//-------------- script control line -------------------
        function cmdListFirst(){
		document.search.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.search.action="src_dp.jsp";
		document.search.submit();
	}

	function cmdListPrev(){
		document.search.command.value="<%=String.valueOf(Command.PREV)%>";
		document.search.action="src_dp.jsp";
		document.search.submit();
	}

	function cmdListNext(){
		document.search.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.search.action="src_dp.jsp";
		document.search.submit();
	}

	function cmdListLast(){
		document.search.command.value="<%=String.valueOf(Command.LAST)%>";
		document.search.action="src_dp.jsp";
		document.search.submit();
	}


        function MM_swapImgRestore() 
        { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
        }

        function MM_preloadImages() 
        { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
        }

        function MM_findObj(n, d) 
        { //v4.0
                var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                if(!x && document.getElementById) x=document.getElementById(n); return x;
        }

        function MM_swapImage() 
        { //v3.0
                var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
        }
</script>


<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
    
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
    <tr><td id="ds_calclass">
    </td></tr>
</table> 
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >

  
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                List DP
                <!-- #EndEditable --> 
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                <% if (privStart) { %>
                                    <form name="search" method="post" action="">
                                    <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <input type="hidden" name="employeeId" value="<%=String.valueOf(employeeId)%>">
                                    <input type="hidden" name="takenDate" value="<%=String.valueOf(takenDate)%>">
                                    <input type="hidden" name="formName" value="<%=String.valueOf(formName)%>">
                                    <input type="hidden" name="fieldNameVal" value="<%=String.valueOf(fieldNameVal)%>">
                                    <input type="hidden" name="fieldNameKey" value="<%=String.valueOf(fieldNameKey)%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                          
                                          <%
                                          
                                          Employee employee = new Employee();
                                          try{
                                            employee = PstEmployee.fetchExc(employeeId);
                                          }catch(Exception ex){}
                                          
                                          %>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="19%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                  <b><%=employee.getEmployeeNum()%></b>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                    <b><%=employee.getFullName()%></b>
                                                </td>
                                              </tr>
                                              </table>
                                            </td>
                                      </tr>
                                      <tr>
                                        <td>
                                            
                                          <% if (((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <%if((listDp!=null)&&(listDp.size()>0)){%>
                                                <tr> 
                                                  <td height="8" width="100%"><%=drawList(listDp, start)%></td>
                                                </tr>
                                                <%}else{%>
                                                <tr> 
                                                  <td height="8" width="100%" class="comment"><span class="comment"><br>
                                                    &nbsp;No DP available</span> 
                                                  </td>
                                                </tr>
                                                <%}%>
                                              <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %><%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
                                                </td>
                                              </tr>
                                            </table>
                                          <% } %>
                                          </td>
                                          </tr>
                                        </table>
                                    </form>
                                <% } 
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% } %>
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
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

