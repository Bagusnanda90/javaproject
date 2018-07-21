<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.gui.jsp.ControlList,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.masterdata.LeavePeriod,
                  com.dimata.util.Command,
                  com.dimata.gui.jsp.ControlDate,
                  com.dimata.gui.jsp.ControlCombo,
				  com.dimata.gui.jsp.ControlLine ,						  
                  com.dimata.util.Formater,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.harisma.entity.masterdata.PstLeavePeriod,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.employee.PstEmployee,
                  com.dimata.harisma.entity.attendance.LLStockManagement,
                  com.dimata.harisma.form.attendance.FrmLLStockManagement,
                  com.dimata.harisma.entity.attendance.PstLLStockManagement,
                  com.dimata.harisma.entity.search.SrcLeaveManagement,
                  com.dimata.harisma.form.search.FrmSrcLeaveManagement,
                  com.dimata.harisma.session.attendance.SessLeaveManagement,				  				  				  				  				  
                  com.dimata.harisma.form.attendance.CtrlLLStockManagement"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_LL_MANAGEMENT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
// untuk prose list dan editor LL stock
// created by gadnyana
// update & documented by edhy
public String drawList(int offsetStart, Vector listll, int iCommand, long oidLl) 
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	
	ctrlist.addHeader("No","3%", "0", "0");
	ctrlist.addHeader("Acquisition Date","20%", "0", "0");	
    ctrlist.addHeader("Entitled","8%", "0", "0");
	ctrlist.addHeader("Quantity","8%", "0", "0");
	ctrlist.addHeader("Taken","8%", "0", "0");
	ctrlist.addHeader("Balance","8%", "0", "0");
	ctrlist.addHeader("Status","15%", "0", "0");
	ctrlist.addHeader("Notes","30%", "0", "0");	
	
	ctrlist.setLinkRow(-1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	
	Vector result = new Vector(1,1);	
	Vector list = new Vector(1,1);
	
	for (int i = 0; i < listll.size(); i++) 
	{
		Vector rowx = new Vector(1,1);
        LLStockManagement llStockMnt = (LLStockManagement)listll.get(i);  				

        if(iCommand==Command.EDIT || iCommand==Command.ASK)
		{
			if(oidLl==llStockMnt.getOID() && (llStockMnt.getLLStatus()==PstLLStockManagement.LL_STS_AKTIF  || llStockMnt.getLLStatus()==PstLLStockManagement.LL_STS_NOT_AKTIF))
			{
				// data for combobox status LL
                Vector stValue = new Vector(1,1);
                Vector stKey = new Vector(1,1);				
				stValue.add(String.valueOf(PstLLStockManagement.LL_STS_AKTIF));
				stKey.add(PstLLStockManagement.fieldStatus[PstLLStockManagement.LL_STS_AKTIF]);
				stValue.add(String.valueOf(PstLLStockManagement.LL_STS_NOT_AKTIF));
				stKey.add(PstLLStockManagement.fieldStatus[PstLLStockManagement.LL_STS_NOT_AKTIF]);   								
				
                rowx.add("<input type=\"hidden\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_LEAVE_PERIODE_ID]+"\" value=\""+llStockMnt.getLeavePeriodeId()+"\">"+(offsetStart+i+1));				
                //rowx.add(ControlDate.drawDateWithStyle(FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE], llStockMnt.getDtOwningDate(), 0, -6,"formElemen", ""));				
                rowx.add("<input onClick=\"ds_sh(this);\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((llStockMnt.getDtOwningDate() == null? new Date() : llStockMnt.getDtOwningDate()), "yyyy-MM-dd")+"\"/>"
                        +"<input type=\"hidden\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]+"_mn\">"
                        +"<input type=\"hidden\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]+"_dy\">"
                        +"<input type=\"hidden\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]+"_yr\">"
                        +"<script language=\"JavaScript\" type=\"text/JavaScript\">getThn();</script>");				
                rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_ENTITLED]+"\" value=\""+llStockMnt.getEntitled()+"\" style=\"background-color:#F5F5F5\" readonly class=\"elemenForm\" size=\"3\">");
                rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_LL_QTY]+"\" value=\""+llStockMnt.getLLQty()+"\" class=\"elemenForm\" size=\"3\" onKeyUp=\"javascript:calqty()\">");
                rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_QTY_USED]+"\" value=\""+llStockMnt.getQtyUsed()+"\" style=\"background-color:#F5F5F5\" readonly class=\"elemenForm\" size=\"3\" onKeyUp=\"javascript:calqty()\">");
                rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_QTY_RESIDUE]+"\" value=\""+llStockMnt.getQtyResidue()+"\" style=\"background-color:#F5F5F5\" readonly class=\"elemenForm\" size=\"3\">");
                rowx.add(ControlCombo.draw(FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_LL_STATUS],null,""+llStockMnt.getLLStatus(),stValue,stKey));
                rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_NOTE]+"\" value=\""+llStockMnt.getStNote()+"\" class=\"elemenForm\" size=\"20\">");				
            }
			
			// jika proses EDIT namun status AL bukan aktif, maka perlakuannya seperio proses LIST			
			else
			{
                rowx.add(""+(offsetStart+i+1));
				//if( (llStockMnt.getLLStatus()==PstLLStockManagement.LL_STS_AKTIF) && (llStockMnt.getQtyUsed()==0))
				if( (llStockMnt.getLLStatus()==PstLLStockManagement.LL_STS_AKTIF) )				
				{
	                rowx.add("<a href=\"javascript:cmdEdit('"+llStockMnt.getOID()+"')\">"+Formater.formatDate(llStockMnt.getDtOwningDate(),"MMM dd, yyyy")+"</a>");
				}
				else
				{
					rowx.add(Formater.formatDate(llStockMnt.getDtOwningDate(),"MMM dd, yyyy"));
				}				
                rowx.add(""+llStockMnt.getEntitled());
                rowx.add(""+llStockMnt.getLLQty());
                rowx.add(""+llStockMnt.getQtyUsed());
                rowx.add(""+llStockMnt.getQtyResidue());
                rowx.add(PstLLStockManagement.fieldStatus[llStockMnt.getLLStatus()]);
                rowx.add(llStockMnt.getStNote());  
            }
        }
		
		// jika proses LIST, tanpa ada action apa dari user		
		else
		{
			rowx.add(""+(offsetStart+i+1));
			//if( (llStockMnt.getLLStatus()==PstLLStockManagement.LL_STS_AKTIF) && (llStockMnt.getQtyUsed()==0) )
			if( (llStockMnt.getLLStatus()==PstLLStockManagement.LL_STS_AKTIF) )							
			{
				rowx.add("<a href=\"javascript:cmdEdit('"+llStockMnt.getOID()+"')\">"+Formater.formatDate(llStockMnt.getDtOwningDate(),"MMM dd, yyyy")+"</a>");
			}
			else
			{
				rowx.add(Formater.formatDate(llStockMnt.getDtOwningDate(),"MMM dd, yyyy"));
			}				
			rowx.add(""+llStockMnt.getEntitled());
			rowx.add(""+llStockMnt.getLLQty());
			rowx.add(""+llStockMnt.getQtyUsed());
			rowx.add(""+llStockMnt.getQtyResidue());
			rowx.add(PstLLStockManagement.fieldStatus[llStockMnt.getLLStatus()]);
			rowx.add(llStockMnt.getStNote());  
        }
        lstData.add(rowx);
        lstLinkData.add(String.valueOf(llStockMnt.getOID()));
	}


	// proses add LL oleh user
    if(iCommand==Command.ADD)
	{
        Vector rowx = new Vector(1,1);
		
		// data for combobox status LL
		Vector stValue = new Vector(1,1);
		Vector stKey = new Vector(1,1);				
		stValue.add(String.valueOf(PstLLStockManagement.LL_STS_AKTIF));
		stKey.add(PstLLStockManagement.fieldStatus[PstLLStockManagement.LL_STS_AKTIF]);
		stValue.add(String.valueOf(PstLLStockManagement.LL_STS_NOT_AKTIF));
		stKey.add(PstLLStockManagement.fieldStatus[PstLLStockManagement.LL_STS_NOT_AKTIF]);   									

        rowx.add("<input type=\"hidden\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_LEAVE_PERIODE_ID]+"\" value=\"0\">"+(offsetStart+listll.size()+1));
        //rowx.add(ControlDate.drawDateWithStyle(FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE], new Date(), 0, -6,"formElemen", ""));
        //edit by artha
        rowx.add("<input onClick=\"ds_sh(this);\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((new Date()), "yyyy-MM-dd")+"\"/>"
                        +"<input type=\"hidden\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]+"_mn\">"
                        +"<input type=\"hidden\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]+"_dy\">"
                        +"<input type=\"hidden\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]+"_yr\">"
                        +"<script language=\"JavaScript\" type=\"text/JavaScript\">getThn();</script>");
        
        rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_ENTITLED]+"\" value=\"0\" class=\"elemenForm\" size=\"3\">");
        rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_LL_QTY]+"\" value=\"0\" class=\"elemenForm\" size=\"3\" onKeyUp=\"javascript:calqty()\">");
        rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_QTY_USED]+"\" value=\"0\" style=\"background-color:#F5F5F5\" class=\"elemenForm\" readonly size=\"3\" onKeyUp=\"javascript:calqty()\">");
        rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_QTY_RESIDUE]+"\" value=\"0\" style=\"background-color:#F5F5F5\" class=\"elemenForm\" readonly  size=\"3\">");
        rowx.add(ControlCombo.draw(FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_LL_STATUS],null,"1",stValue,stKey));
        rowx.add("<input type=\"text\" name=\""+FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_NOTE]+"\" value=\"\" class=\"elemenForm\" size=\"30\">");
        lstData.add(rowx);
    }
	return ctrlist.drawList();
}
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int startSummary = FRMQueryString.requestInt(request, "start_summary");
long oidLl = FRMQueryString.requestLong(request,"long_leave_id");
long empOid = FRMQueryString.requestLong(request,FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_EMPLOYEE_ID]);

// get and set data from / to session
try
{		  
	if(session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_LL) != null)
	{
		SrcLeaveManagement srcLeaveManagement = (SrcLeaveManagement)session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_LL); 			
		session.putValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_LL, srcLeaveManagement);	  		
	}
}
catch(Exception e)
{ 
	System.out.println("Exc when get/set data from/to session");
}

// konstanta untuk navigasi ke database
String msgString = "";
int recordToGet = 10;
int vectSize = 0;
int iErrCode = 0;

// for save data annual leave
CtrlLLStockManagement ctrlLLStockManagement = new CtrlLLStockManagement(request);
iErrCode = ctrlLLStockManagement.action(iCommand, oidLl);
msgString = ctrlLLStockManagement.getMessage();

// mencari nilai limitStart
vectSize = SessLeaveManagement.countDetailAlStock(empOid);	
if(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST)
{
	start = ctrlLLStockManagement.actionList(iCommand, start, vectSize, recordToGet);
}	

// process get list LL
Employee emp = new Employee();
Vector vectListLL = new Vector(1,1);
if(empOid!=0)
{
	try
	{
		emp = PstEmployee.fetchExc(empOid);
	}
	catch(Exception e)
	{
		System.out.println("exc when fetch emp : " + e.toString());
	}	
	vectListLL = SessLeaveManagement.listDetailLlStock(empOid, start, recordToGet); 			
}	
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - LL Management</title>
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<script language="JavaScript">
function cmdBackSummary(){
	document.frpresence.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frpresence.start.value="<%=String.valueOf(startSummary)%>";	
	document.frpresence.action="longleave.jsp";
	document.frpresence.submit();
}

function calqty(){
    var alqty = document.frpresence.<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_LL_QTY]%>.value;
    var usedqty = document.frpresence.<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_QTY_USED]%>.value;
    if(isNaN(alqty) || alqty=="")
        alqty = 0;
    if(isNaN(usedqty) || usedqty=="")
        usedqty = 0;
    var resqty = document.frpresence.<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_QTY_RESIDUE]%>.value = parseInt(alqty) - parseInt(usedqty);
}

function cmdEdit(oid){
    document.frpresence.long_leave_id.value=oid;
	document.frpresence.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdSave(){
	document.frpresence.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdAdd(){
    document.frpresence.long_leave_id.value=0;
	document.frpresence.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdAsk(oidAl)
{
	document.frpresence.long_leave_id.value=oidAl;
	document.frpresence.command.value="<%=String.valueOf(Command.ASK)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdConfirmDelete(oidAl)
{
	document.frpresence.long_leave_id.value=oidAl;
	document.frpresence.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdCancel(oidAl){
	document.frpresence.long_leave_id.value=oidAl;
	document.frpresence.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdBack(){
	document.frpresence.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdListFirst(){
	document.frpresence.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdListPrev(){
	document.frpresence.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdListNext(){
	document.frpresence.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

function cmdListLast(){
	document.frpresence.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frpresence.action="longleave_detail.jsp";
	document.frpresence.submit();
}

//------------- Calendar ---------------------------
function getThn(){
            var date1 = ""+document.frpresence.<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]%>.value;
            var thn = date1.substring(0,4);
            var bln = date1.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date1.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.frpresence.<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]%>_mn.value=bln;
            document.frpresence.<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]%>_dy.value=hri;
            document.frpresence.<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_OWNING_DATE]%>_yr.value=thn;
    }



    function hideObjectForDate(){
    }

    function showObjectForDate(){
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
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<!-- #EndEditable -->
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                  &gt; LL (Long Leave) &gt; Detail Per Employee<!-- #EndEditable --> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frpresence" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">									  
                                      <input type="hidden" name="start_summary" value="<%=String.valueOf(startSummary)%>">									  									  
                                      <input type="hidden" name="long_leave_id" value="<%=String.valueOf(oidLl)%>">									  
                                      <input type="hidden" name="<%=FrmLLStockManagement.fieldNames[FrmLLStockManagement.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=String.valueOf(empOid)%>">									  									  
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <%
										//--- start untuk menampilkan nomor dan nama employee yang sedang di-edit
										if(empOid!=0)
										{
										%>
                                        <tr> 
                                          <td>&nbsp;&nbsp;<b>Payroll &nbsp;: <%=emp.getEmployeeNum().toUpperCase()%></b></td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;&nbsp;<b>Name &nbsp; &nbsp;: 
                                            <%=emp.getFullName().toUpperCase()%></b></td>
                                        </tr>
                                        <%
										}
										//--- end untuk menampilkan nomor dan nama employee yang sedang di-edit
										%>

                                        <%
										//--- start untuk menampilkan list data result, baik summary maupun detail per employee 
										String drawList = "";
										if(vectListLL.size()>0)
										{
											drawList = drawList(start, vectListLL, iCommand, oidLl);
										}
										
										String strDpData = drawList.trim();
										if(strDpData!=null && strDpData.length()>0)
										{
										%>
                                        <tr> 
                                          <td><%=strDpData%></td>
                                        </tr>
                                        <%
										}
										else if(iCommand == Command.LIST)
										{
										%>
                                        <tr> 
                                          <td><%="<div class=\"msginfo\">&nbsp;&nbsp;No long leave data found ...</div>"%></td>
                                        </tr>
                                        <%	
										}
										//--- end untuk menampilkan list data result, baik summary maupun detail per employee 
										%>
									  
                                        <tr> 
                                          <td> 
                                            <% 
										    ControlLine ctrLine = new ControlLine();												
											ctrLine.setLanguage(SESS_LANGUAGE);											
											ctrLine.setLocationImg(approot+"/images");
											ctrLine.initDefault();		
											int listCommand = iCommand;											
											if(iCommand==Command.EDIT && empOid!=0)
											{
												listCommand = Command.LIST;
											}
											out.println(ctrLine.drawImageListLimit(listCommand, vectSize, start, recordToGet));
											%>
                                          </td>
                                        </tr>
                                        <%
										if(iCommand==Command.LIST || iCommand==Command.FIRST || iCommand==Command.PREV 
										|| iCommand==Command.NEXT || iCommand==Command.LAST || iCommand==Command.BACK 
										|| (iCommand==Command.SAVE && iErrCode==0) || (iCommand==Command.DELETE && iErrCode==0))
										{										
										%>
                                        <tr> 
                                          <td> 
                                            <table width="30%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
											  <%if(privAdd){%>
                                                <td width="29"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image35','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image35" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24"></a></td>
                                                <td width="83" valign="top" nowrap class="button">&nbsp; 
                                                  <a href="javascript:cmdAdd()" class="buttonlink">Add 
                                                  New LL</a></td>
												  <%}%>
												<td width="10">&nbsp;</td>   
                                                <td width="29"><a href="javascript:cmdBackSummary()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image36','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image36" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24"></a></td>
                                                <td width="143" valign="top" nowrap class="button">&nbsp; 
                                                  <a href="javascript:cmdBackSummary()" class="buttonlink">Back 
                                                  To Summary LL</a></td>												  
                                              </tr>
                                            </table>   
                                          </td>
                                        </tr>
                                        <%
										}
										else
										{
										%>
                                        <tr> 
                                          <td> 
                                            <%
											if(empOid!=0)
											{
												ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												ctrLine.setTableWidth("50%");
												String scomDel = "javascript:cmdAsk('"+oidLl+"')";
												String sconDelCom = "javascript:cmdConfirmDelete('"+oidLl+"')";
												String scancel = "javascript:cmdEdit('"+oidLl+"')";
												ctrLine.setBackCaption("Back to List");
												ctrLine.setCommandStyle("buttonlink");
												ctrLine.setBackCaption("Back to List LL");
												ctrLine.setSaveCaption("Save LL");
												ctrLine.setConfirmDelCaption("Yes Delete LL");
												ctrLine.setDeleteCaption("Delete LL");
												ctrLine.setAddCaption("");
			
												if(privDelete)
												{
													ctrLine.setConfirmDelCommand(sconDelCom);
													ctrLine.setDeleteCommand(scomDel);
													ctrLine.setEditCommand(scancel);
												}
												else
												{ 
													ctrLine.setConfirmDelCaption("");
													ctrLine.setDeleteCaption("");
													ctrLine.setEditCaption("");
												}
			
												if(privAdd==false && privUpdate==false)
												{
													ctrLine.setSaveCaption("");
												}
			
												if(iCommand==Command.ASK)
												{
													ctrLine.setDeleteQuestion(msgString);
												}
												
												out.println(ctrLine.drawImage(iCommand, iErrCode, msgString));	
											}
											%>
                                          </td>
                                        </tr>
										<%
										}
										%>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>

<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
