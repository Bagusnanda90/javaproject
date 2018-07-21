
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.AlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstAlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.LLUpload" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_LEAVE_OPNAME, AppObjInfo.OBJ_EMP_LEAVE_AL_OPNAME); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
int iCommand = FRMQueryString.requestCommand(request);

long oid = FRMQueryString.requestLong(request,"hidden_LL_id");
System.out.println("iCommand "+iCommand);
System.out.println("OID Baru "+oid);
String note = FRMQueryString.requestString(request, "note");

LLStockManagement objLLStockManagement = new LLStockManagement();

LLUpload objLLUpload = new LLUpload();
Employee objEmployee = new Employee();
Employee emp = new Employee();

	try{
		
                objLLStockManagement = PstLLStockManagement.fetchExc(oid);
                objEmployee = PstEmployee.fetchExc(objLLStockManagement.getEmployeeId());
		
                System.out.println("emp id "+objLLStockManagement.getEmployeeId());
                System.out.println("Nama employee "+objEmployee.getFullName());
		
		if(iCommand==Command.SAVE){
                        objLLStockManagement.setStNote(note);
                       
			oid = PstLLStockManagement.updateExc(objLLStockManagement);
			
		}
		
	}
	catch(Exception e){
		System.out.println(e.toString());
	}

%>
<html>
<head>
<title>Note Edit</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<script language="JavaScript">
function cmdSave(oid){
	document.frmnote.command.value="<%=Command.SAVE%>";
	document.frmnote.action="note_edit_LL.jsp?hidden_LL_id="+oid;
	document.frmnote.submit();
}


function cmdBack(){
	document.frmnote.command.value="<%=Command.BACK%>";
	document.frmnote.action="srcleavestock.jsp";
	document.frmnote.submit();
}

<%if(iCommand==Command.SAVE){%>
	window.close();
<%}%>

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
</head>

<body bgcolor="#FFFFFF" text="#000000">
<form name="frmnote" method="post" action="">
<input type="hidden" name="command" value="">
<input type="hidden" name="hidden_leave_stock_id" value="<%=oid%>">

  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td colspan="3"><b>LEAVE OPNAME NOTE</b></td>
    </tr>
    <tr> 
      <td width="21%">&nbsp;</td>
      <td width="2%">&nbsp;</td>
      <td width="77%">&nbsp;</td>
    </tr>
    <tr> 
      <td width="21%"><b>Employee</b></td>
      <td width="2%"><b>:</b></td>
      <td width="77%"><%=objEmployee.getFullName()%></td>
    </tr>
    <tr> 
      <td width="21%" nowrap><b>Payroll Number</b></td>
      <td width="2%"><b>:</b></td>
      <td width="77%"><%=objEmployee.getEmployeeNum()%></td>
    </tr>
    <tr> 
      <td width="21%"><b></b></td>
      <td width="2%"><b></b></td>
      <td width="77%">&nbsp;</td>
    </tr>
    <tr> 
      <td width="21%"><b>Note 
        <%
        out.println("for LL");	
	%>
        </b></td>
      <td width="2%"><b>: </b></td>
      <td width="77%"> 
        <%
	String notereal = "";
        
        notereal = objLLStockManagement.getStNote()==null ? "" : objLLStockManagement.getStNote();	
	%>
        <textarea name="note" rows="3" cols="35"><%=notereal%></textarea>
      </td>
    </tr>
    <tr> 
      <td width="21%">&nbsp;</td>
      <td width="2%">&nbsp;</td>
      <td width="77%">&nbsp;</td>
    </tr>
    <tr> 
      <td width="21%">&nbsp;</td>
      <td width="2%">&nbsp;</td>
      <td width="77%"> 
        <table width="50%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="6%" class="command" nowrap><a href="javascript:cmdSave('<%=oid%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)" id="aSearch"><img name="Image101" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="save data"></a></td>
            <td width="2%" class="command" nowrap><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
            <td width="92%" class="command" nowrap><a href="javascript:cmdSave('<%=oid%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)" id="aSearch">Save 
              Note </a></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr> 
      <td width="21%">&nbsp;</td>
      <td width="2%">&nbsp;</td>
      <td width="77%">&nbsp;</td>
    </tr>
  </table>
      </form>
</body>
</html>
