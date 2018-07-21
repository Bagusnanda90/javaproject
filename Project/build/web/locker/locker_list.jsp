<% 
/* 
 * Page Name  		:  locker_list.jsp
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
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../main/javainit.jsp" %>

<%// int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOCKER, AppObjInfo.G2_LOCKER, AppObjInfo.OBJ_LOCKER); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    //out.print("privView=" + privView + " | privAdd=" + privAdd);
%>

<!-- Jsp Block -->
<%!
    
public String drawList(Vector objectClass ,  long lockerId, int start){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No","5%");
        ctrlist.addHeader("Locker Number","8%");
        ctrlist.addHeader("Location","20%");
        ctrlist.addHeader("Key Number","10%");
        ctrlist.addHeader("Spare Key","10%");
        ctrlist.addHeader("Condition","10%");
        ctrlist.addHeader("Employee","37%");

        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        Hashtable hLockerLoc = new Hashtable();
        Vector vTempLoc = new Vector(1,1);
        vTempLoc = PstLockerLocation.listAll();
        for(int i=0;i<vTempLoc.size();i++){
            LockerLocation lLoc = new LockerLocation();
            lLoc = (LockerLocation)vTempLoc.get(i);
            hLockerLoc.put(""+lLoc.getOID(), lLoc);
        }
        
        Hashtable hLockerCon = new Hashtable();
        Vector vTempCon = new Vector(1,1);
        vTempCon = PstLockerCondition.listAll();
        for(int i=0;i<vTempCon.size();i++){
            LockerCondition lCon = new LockerCondition();
            lCon = (LockerCondition)vTempCon.get(i);
            hLockerCon.put(""+lCon.getOID(), lCon);
        }
        int index = -1;
        for (int i = 0; i < objectClass.size(); i++) 
        {
            Vector vect = (Vector)objectClass.get(i);
            Locker locker = (Locker) vect.get(0);
            Vector vEmployee = new Vector(1,1);
            vEmployee = (Vector)vect.get(1);
 
            if(lockerId == locker.getOID())
                     index = i;
            
            Vector rowx = new Vector(); 
            rowx.add(String.valueOf((start+i+1)));
            
            rowx.add(locker.getLockerNumber());
	
            LockerLocation lLoc = new LockerLocation();
            LockerCondition lCon = new LockerCondition();
            try{
                lLoc = (LockerLocation)hLockerLoc.get(""+locker.getLocationId());
                lCon = (LockerCondition)hLockerCon.get(""+locker.getConditionId());
            }catch(Exception ex){}
            rowx.add(lLoc.getLocation());
            rowx.add(locker.getKeyNumber());
            rowx.add(locker.getSpareKey()==null ? "-":locker.getSpareKey());
            rowx.add((lCon!=null?lCon.getCondition():""));
            rowx.add(createEmp(vEmployee));

            lstData.add(rowx);
            String strLink  = ""+locker.getOID();
            lstLinkData.add(strLink);
        }
        return ctrlist.draw(index);
}


private String createEmp(Vector vEmp){
    Hashtable hDep = new Hashtable();
    Vector vTempDep = new Vector(1,1);
    vTempDep = PstDepartment.listAll();
    for(int i=0;i<vTempDep.size();i++){
        Department dep = new Department();
        dep = (Department)vTempDep.get(i);
        hDep.put(""+dep.getOID(), dep);
    }
    
    String strEmp = "";
    for(int i=0;i<vEmp.size();i++){
        if(strEmp.length()>0){
           strEmp+="<br>"; 
        }
        Employee emp = new Employee();
        Department dep = new Department();
        try{
            emp = (Employee)vEmp.get(i);
            dep = (Department)hDep.get(""+emp.getDepartmentId());
            strEmp += "("+emp.getEmployeeNum()+") "+emp.getFullName()+" - "+dep.getDepartment();
        }catch(Exception ex){}
    }
    return strEmp;
}

%>
<%
    ControlLine ctrLine = new ControlLine();
    CtrlLocker ctrlLocker = new CtrlLocker(request);
    long oidLocker = FRMQueryString.requestLong(request, "hidden_locker_id");

    int iErrCode = FRMMessage.ERR_NONE;
    String msgStr = "";
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int recordToGet = 10;
    int vectSize = 0;
    String whereClause = "";

    boolean isLockerUsed = true;
    
    //out.println("iCommand : "+iCommand);
    //out.println("<br>start : "+start);
    //out.println("<br>recordToGet : "+recordToGet);

    SrcLocker srcLocker = new SrcLocker();
    FrmSrcLocker frmSrcLocker = new FrmSrcLocker(request, srcLocker);
    frmSrcLocker.requestEntityObject(srcLocker);
    if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)){
        try{ 
            srcLocker = (SrcLocker)session.getValue(SessLocker.SESS_SRC_LOCKER); 
        }catch(Exception e){ 
            srcLocker = new SrcLocker();
        }
    }

    SessLocker sessLocker = new SessLocker();
    session.putValue(SessLocker.SESS_SRC_LOCKER, srcLocker);
    
    Vector VListAll = new Vector(1,1);
    try{
        VListAll = SessLocker.listLockerEmp(0, 0, srcLocker, isLockerUsed);
    }catch(Exception ex){}
    
    vectSize = VListAll.size();
    //out.println("vectSize : "+vectSize);
    ctrlLocker.action(iCommand , oidLocker);
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST))
        start = ctrlLocker.actionList(iCommand, start, vectSize, recordToGet);
    Vector records = SessLocker.listLockerEmp(start, recordToGet, srcLocker, isLockerUsed);
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Locker</title>
<script language="JavaScript">
    function cmdPrint(){
		window.open("locker_buffer.jsp","list","height=600,width=800,scrollbars=yes, status=no,toolbar=no,menubar=no,location=no");
        //window.open("<%=approot%>/servlet/com.dimata.harisma.report.LockerListPdf?time=<%=System.currentTimeMillis()%>");
    }

    function cmdPrintXLS(){
		window.open("lockerXLS_buffer.jsp","listxls","height=600,width=800,scrollbars=yes, status=no,toolbar=no,menubar=yes,location=no");
        //window.open("<%=approot%>/servlet/com.dimata.harisma.report.LockerListXLS?time=<%=System.currentTimeMillis()%>");
    }

    function cmdAdd(){
        document.frm_locker.command.value="<%=Command.ADD%>";
        document.frm_locker.action="locker_edit.jsp";
        document.frm_locker.submit();
    }

    function cmdEdit(oid){
        document.frm_locker.hidden_locker_id.value=oid;
        document.frm_locker.command.value="<%=Command.EDIT%>";
        document.frm_locker.action="locker_edit.jsp";
        document.frm_locker.submit();
    }

    function cmdListFirst(){
        document.frm_locker.command.value="<%=Command.FIRST%>";
        document.frm_locker.action="locker_list.jsp";
        document.frm_locker.submit();
    }

    function cmdListPrev(){
        document.frm_locker.command.value="<%=Command.PREV%>";
        document.frm_locker.action="locker_list.jsp";
        document.frm_locker.submit();
    }

    function cmdListNext(){
        document.frm_locker.command.value="<%=Command.NEXT%>";
        document.frm_locker.action="locker_list.jsp";
        document.frm_locker.submit();
    }

    function cmdListLast(){
        document.frm_locker.command.value="<%=Command.LAST%>";
        document.frm_locker.action="locker_list.jsp";
        document.frm_locker.submit();
    }

    function cmdBack(){
        document.frm_locker.command.value="<%=Command.BACK%>";
        document.frm_locker.action="srclocker.jsp";
        document.frm_locker.submit();
    }

</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
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
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Locker &gt; Locker List<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frm_locker" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_locker_id" value="<%=oidLocker%>">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="comment">Locker 
                                            List</td>
                                        </tr>
                                      </table>
                                      <%if((records!=null)&&(records.size()>0)){%>
                                      <%=drawList(records,oidLocker,start)%> 
                                      <%}
					else{
					%>
                                      <span class="comment"><br>
                                      &nbsp;Records is empty ...</span> 
                                      <%}%>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <table width="100%" cellspacing="0" cellpadding="3">
                                              <tr> 
                                                <td> 
                                                  <% ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
						%>
                                                  <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="46%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <%-- <td width="46%" nowrap align="left" class="command">&nbsp; <a href="javascript:cmdAdd()">Add New</a> | <a href="javascript:cmdBack()">Back to search</a> </td> --%>
					  <td>
                                            <table width="100" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Locker</a></td>
						 <%if(privAdd){%>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Locker</a></b></td>
						 <%}%>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="15"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><b><a href="javascript:cmdPrint()" class="command">Print Locker</a></b></td>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdPrintXLS()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1003','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1003" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Export to Excel"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdPrintXLS()" class="command">Export to Excel</a></td>
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
