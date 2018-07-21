
<% 
/* 
 * Page Name  		:  leave_edit.jsp
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.session.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.form.leave.dp.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_DP_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/

//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=false;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!

	public String drawList(Vector objectClass, String approot){
            System.out.println("==============> Box Dp App Detail");
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No.","10%");
		ctrlist.addHeader("DP Taken Date","70%");
		ctrlist.addHeader("Replace of","20%");
				
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
			DpAppDetail dpAppDetail = (DpAppDetail)objectClass.get(i);
					
			System.out.println(dpAppDetail.getOID()+"-------"+Formater.formatDate(dpAppDetail.getTakenDate(), "dd MMMM yyyy"));
			Vector rowx = new Vector();

			rowx.add("<input type='hidden' name='DP_TAKEN"+i+"' value='"+dpAppDetail.getTakenDate().getTime()+"'>"+String.valueOf(i+1));
			rowx.add(Formater.formatDate(dpAppDetail.getTakenDate(), "dd MMMM yyyy"));
                        String strDpReplace = "";
                        long dpReplaceId = 0;
                        long takenDate = 0;
                        takenDate = new Date().getTime();
			if(dpAppDetail.getDpId()>0){
                            DpStockManagement dpStockManagement = new DpStockManagement();
                            try{
                                dpStockManagement = PstDpStockManagement.fetchExc(dpAppDetail.getDpId());
                            }catch(Exception xe){}
                            strDpReplace = Formater.formatDate(dpStockManagement.getDtOwningDate(),"dd MMMM yyyy");
                            dpReplaceId = dpStockManagement.getOID();
			}
                        
                        String strClear = "<input name='ISAPPROVED"+i+"' value='1' type='checkbox' checked='true'>";
                        
			String strGetReplaceDate = "<input type='hidden' name='DP_REPLACE_KEY"+i+"' value='"+dpReplaceId+"'>" +
                                "<input type='text' readonly='true' name='DP_REPLACE_VAL"+i+"' value='"+strDpReplace+"'>";	
			rowx.add(strGetReplaceDate+strClear);
                        lstData.add(rowx);
			//lstLinkData.add(String.valueOf(dpAppDetail.getDpId()));
		}

		return ctrlist.draw();
	}

%>
<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request,"start");
int countDpTaken = FRMQueryString.requestInt(request,"countDpTaken");
long oidDpAppMain = FRMQueryString.requestLong(request, "oidDpAppMain");
long oidDpAppDetail = FRMQueryString.requestLong(request, "oidDpAppDetail");
long oidEmployee = FRMQueryString.requestLong(request, "oidEmployee");
long lTakenDate = FRMQueryString.requestLong(request, "lTakenDate");
long approvedId1 = FRMQueryString.requestLong(request, "approvedId1");
long approvedId2 = FRMQueryString.requestLong(request, "approvedId2");
long approvedId3 = FRMQueryString.requestLong(request, "approvedId3");
boolean isApprove1 = FRMQueryString.requestBoolean(request, "isApprove1");
boolean isApprove2 = FRMQueryString.requestBoolean(request, "isApprove2");
boolean isApprove3 = FRMQueryString.requestBoolean(request, "isApprove3");

int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";

ControlLine ctrLine = new ControlLine();

DpAppMain dpAppMain = new DpAppMain();
if(oidDpAppMain > 0){
        dpAppMain = PstDpAppMain.fetchExc(oidDpAppMain);
}else if(oidDpAppDetail>0){
    try{
        DpAppDetail dpAppDetail = new DpAppDetail();
        dpAppDetail = PstDpAppDetail.fetchExc(oidDpAppDetail);
        dpAppMain = PstDpAppMain.fetchExc(dpAppDetail.getDpAppMainId());
        oidDpAppMain = dpAppDetail.getDpAppMainId();
    }catch(Exception ex){}
}

/**
    MENGECEK JUMLAH APPROVAL YANG DIPERLUKAN

*/

I_Leave leaveConfig = null;           
try {
    leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
}catch(Exception e) {
    System.out.println("Exception : " + e.getMessage());
}

int maxApporval = leaveConfig.getMaxApproval(dpAppMain.getEmployeeId());


/////////////////////////////////////////////////////////////////
/**
Menyimpan data
*/
if(iCommand==Command.SAVE){
    //Jika dp app main telah ada sebelumnya
    //maka data tinggal di update
    //System.out.println("----------------------------------->");
    if(oidDpAppMain>0){
        //Bersihkan data sebelumnya
        SessDpAppDetail.clearDpAppDetail(oidDpAppMain);
        DpAppMain objDpAppMain = new DpAppMain();
        try{
            objDpAppMain = PstDpAppMain.fetchExc(oidDpAppMain);
            if(isApprove1){
                objDpAppMain.setApprovalId(FRMQueryString.requestLong(request,FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL_ID]));
                objDpAppMain.setApprovalDate(FRMQueryString.requestDate(request,FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL_DATE]));
                isApprove1 = false;
            }else if(isApprove2) {
                objDpAppMain.setApproval2Id(FRMQueryString.requestLong(request,FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL2_ID]));
                objDpAppMain.setApproval2Date(FRMQueryString.requestDate(request,FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL2_DATE]));
                isApprove2 = false;
            }else if(isApprove3) {
                objDpAppMain.setApproval3Id(FRMQueryString.requestLong(request,FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL3_ID]));
                objDpAppMain.setApproval3Date(FRMQueryString.requestDate(request,FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL3_DATE]));
                isApprove3 = false;
            }
            
            //// UNTUK APPROVAL
            switch(maxApporval){
                case 1: 
                    if(objDpAppMain.getApprovalId()>0){
                        objDpAppMain.setApproval2Id(objDpAppMain.getApprovalId());
                        objDpAppMain.setApproval3Id(objDpAppMain.getApprovalId());
                        objDpAppMain.setApproval2Date(objDpAppMain.getApprovalDate());
                        objDpAppMain.setApproval3Date(objDpAppMain.getApprovalDate());
                    }
                    break;
                case 2: 
                    if(objDpAppMain.getApproval2Id()>0){
                        objDpAppMain.setApproval3Id(objDpAppMain.getApproval2Id());
                        objDpAppMain.setApproval3Date(objDpAppMain.getApproval2Date());
                    }
                    break;
            }
            
            oidDpAppMain = PstDpAppMain.updateExc(objDpAppMain);
        }catch(Exception ex){}
    }else{
    // Jika data dp app main belum ada sebelumnya maka data perlu di create
        //FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_BALANCE]
        DpAppMain objDpAppMain = new DpAppMain();
        objDpAppMain.setEmployeeId(FRMQueryString.requestLong(request,FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_EMPLOYEE_ID]));
        objDpAppMain.setSubmissionDate(FRMQueryString.requestDate(request,FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_SUBMISSION_DATE]));
        try{
            oidDpAppMain = PstDpAppMain.insertExc(objDpAppMain);
        }catch(Exception ex){}
    }
    
    //Mengumpulkan dp detail
    Vector dpDetail = new Vector(1,1);
    for(int i=0;i<countDpTaken;i++){
        long dpOID = FRMQueryString.requestLong(request,"DP_REPLACE_KEY"+i);
        int approvedDp = FRMQueryString.requestInt(request,"ISAPPROVED"+i);
        if(oidDpAppMain>0 && approvedDp==1){
            DpAppDetail dpAppDetail = new DpAppDetail();
            dpAppDetail.setDpAppMainId(oidDpAppMain);
            dpAppDetail.setDpId(dpOID);
            long lDateTaken = FRMQueryString.requestLong(request,"DP_TAKEN"+i);
            Date dateTaken = new Date(lDateTaken);
            dpAppDetail.setTakenDate(dateTaken);
            try{
                PstDpAppDetail.insertExc(dpAppDetail);
            }catch(Exception ex){}
        }
    }
    
}

//Jika di hapus
if(iCommand == Command.DELETE){
    try{
        long rest = PstDpAppMain.deleteExc(oidDpAppMain);
        if(rest == oidDpAppMain){
            //Bersihkan data sebelumnya
            SessDpAppDetail.clearDpAppDetail(oidDpAppMain);
            %>
                <jsp:forward page="dp_application_list.jsp">
                <jsp:param name="start" value="<%=start%>" />
                <jsp:param name="iCommand" value="<%=Command.LIST%>" />        
                </jsp:forward>
            <%
        }
    }catch(Exception ex){}
}

/////////////////////////////////////////////////////////////////


// get employee and deparment data
String strEmpFullName = "";
String strEmpDepartment = "";
String strEmpPosition = "";
String strEmpDivision = "";
Employee objEmployee = new Employee();

if(oidDpAppMain > 0){
        dpAppMain = PstDpAppMain.fetchExc(oidDpAppMain);
}else if(oidDpAppDetail>0){
    try{
        DpAppDetail dpAppDetail = new DpAppDetail();
        dpAppDetail = PstDpAppDetail.fetchExc(oidDpAppDetail);
        dpAppMain = PstDpAppMain.fetchExc(dpAppDetail.getDpAppMainId());
        oidDpAppMain = dpAppDetail.getDpAppMainId();
    }catch(Exception ex){}
}
try
{
	if( oidEmployee == 0 && dpAppMain.getEmployeeId()>0)
	{
		oidEmployee =  dpAppMain.getEmployeeId();
	}
	
	objEmployee = PstEmployee.fetchExc(oidEmployee);

	// get fullname
	strEmpFullName = objEmployee.getFullName();
	
	// get deparment
	Department objDepartment = new Department();
	objDepartment = PstDepartment.fetchExc(objEmployee.getDepartmentId());
	strEmpDepartment = objDepartment.getDepartment();
        
        //get position
        Position objPosition = new Position();
        objPosition = PstPosition.fetchExc(objEmployee.getPositionId());
        strEmpPosition = objPosition.getPosition();
        
        //get division
        Division objDivision = new Division();
        objDivision = PstDivision.fetchExc(objEmployee.getDivisionId());
        strEmpDivision = objDivision.getDivision();
}
catch(Exception e)
{
	System.out.println("Exc when fetch employee and deparment data : " + e.toString());
}


Vector vListDp = new Vector(1,1);

Date dateTemp = new Date(lTakenDate);
//System.out.println("================>>>> "+Formater.formatDate(dateTemp, "dd-MM-yyyy"));
vListDp = SessDpAppDetail.listDpAppDetail(dpAppMain.getOID());
countDpTaken = vListDp.size();    
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - DP Application</title>
<script language="JavaScript">
<!--


function selectDp(index){
   <%int indexTemp =0;%>
   <%for(int i=0;i<vListDp.size();i++){%>
        if(<%=String.valueOf(i)%>==index){
            <%indexTemp = i;%>
         //   alert(takenVal);
            var path = "src_dp.jsp?employeeId=<%=String.valueOf(oidEmployee)%>"
           +"&takenDate="+takenVal
           +"&formName=frm_dp_application"
           +"&fieldNameVal=DP_REPLACE_VAL<%=String.valueOf(indexTemp)%>"
           +"&fieldNameKey=DP_REPLACE_KEY<%=String.valueOf(indexTemp)%>";
           window.open(path, null, "height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
        }
    <%}%>
}

function cmdPrint()
{ 
    //alert("CREATE REPORT < %=String.valueOf(oidDpAppMain)%>");
    pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.DpAppPdf?oidDpAppMain=<%=String.valueOf(oidDpAppMain)%>";
    window.open(pathUrl);
}

function cmdClear(index)
{ 
    <%
    int indexTemp2 =0;
    for(int i=0;i<vListDp.size();i++){
    %>
        if(<%=String.valueOf(i)%>==index){
            <%
            indexTemp2 = i;
            %>
            document.frm_dp_application.DP_REPLACE_VAL<%=String.valueOf(indexTemp2)%>.value="";
            document.frm_dp_application.DP_REPLACE_KEY<%=String.valueOf(indexTemp2)%>.value="";
        }
    <%
    }
    %>
}

function cmdCancel()
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.CANCEL)%>";
	document.frm_dp_application.action="dp_application_edit.jsp";
	document.frm_dp_application.submit();
} 

function cmdEdit(oid)
{ 
	document.frm_dp_application.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frm_dp_application.action="dp_application_edit.jsp";
	document.frm_dp_application.submit(); 
} 

function cmdSave()
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.SAVE)%>"; 
	document.frm_dp_application.action="dp_application_edit.jsp";
	document.frm_dp_application.submit();
}

function cmdAsk(oid)
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.ASK)%>"; 
	document.frm_dp_application.action="dp_application_edit.jsp";
	document.frm_dp_application.submit();
} 

function cmdConfirmDelete(oid)
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frm_dp_application.action="dp_application_edit.jsp"; 
	document.frm_dp_application.submit();
}  

function cmdBack()
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.FIRST)%>"; 
	document.frm_dp_application.action="<%=approot%>/employee/leave/dp_application_list.jsp";
	document.frm_dp_application.submit();
}

function checkApproval(index)
{
	var empLoggedIn = "<%=String.valueOf(emplx.getOID())%>";
	var empApprovalSelected = 0;
        if(index==1){
            empApprovalSelected = document.frm_dp_application.<%=FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL_ID]%>.value;
        }
        if(index==2){
            empApprovalSelected = document.frm_dp_application.<%=FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL2_ID]%>.value;
        }
        if(index==3){
            empApprovalSelected = document.frm_dp_application.<%=FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL3_ID]%>.value;
        }
	if(empLoggedIn != 0)
	{
		if(empApprovalSelected != 0)
		{
			if(empLoggedIn != empApprovalSelected)
			{
				document.frm_dp_application.command.value="<%=String.valueOf(Command.LIST)%>";
				document.frm_dp_application.indexApproval.value=index;
				document.frm_dp_application.oidDevHead.value=empApprovalSelected;
				document.frm_dp_application.action="leave_application_app_login_dp.jsp";
				document.frm_dp_application.submit();  		
			}else{
                          /*  if(index==1){
                                document.frm_dp_application.isApprove1.value = "true";
                            }   		
                            if(index==2){
                                document.frm_dp_application.isApprove2.value = "true";
                            }   		
                            if(index==3){
                                document.frm_dp_application.isApprove3.value = "true";
                            }   */	
                        }
		}
		else
		{
			alert('Please choose an authorized manager to approve this DP Application ...');    					
		}
	}
	else
	{
            alert('You should login into Harisma as an authorized user ...');
            if(index==1){
		document.frm_dp_application.<%=FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL_ID]%>.value = "0";
            }   		
            if(index==2){
		document.frm_dp_application.<%=FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL2_ID]%>.value = "0";
            }   		
            if(index==3){
		document.frm_dp_application.<%=FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL3_ID]%>.value = "0";
            }   		
	}
}


//--------------- Calendar  -------------------------------
function getThn(){
            <%--=ControlDatePopup.writeDateCaller("frm_dp_application",FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_SUBMISSION_DATE])--%>
            <%=ControlDatePopup.writeDateCaller("frm_dp_application",FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL_DATE])%>
            <%=ControlDatePopup.writeDateCaller("frm_dp_application",FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL2_DATE])%>
            <%=ControlDatePopup.writeDateCaller("frm_dp_application",FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL3_DATE])%>
    }


    function hideObjectForDate(index){
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
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Leave Management &gt; DP Application<!-- #EndEditable --> 
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
                                    <form name="frm_dp_application" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="oidDpAppMain" value="<%=String.valueOf(oidDpAppMain)%>">
                                      <input type="hidden" name="oidDpAppDetail" value="<%=String.valueOf(oidDpAppDetail)%>">
                                      <input type="hidden" name="oidEmployee" value="<%=String.valueOf(oidEmployee)%>">
                                      <input type="hidden" name="lTakenDate" value="<%=String.valueOf(lTakenDate)%>">
                                      <input type="hidden" name="countDpTaken" value="<%=String.valueOf(countDpTaken)%>">
                                      <input type="hidden" name="approvedId1" value="<%=String.valueOf(approvedId1)%>">
                                      <input type="hidden" name="approvedId2" value="<%=String.valueOf(approvedId2)%>">
                                      <input type="hidden" name="approvedId3" value="<%=String.valueOf(approvedId3)%>">
                                      <input type="hidden" name="isApprove1" value="<%=String.valueOf(isApprove1)%>">
                                      <input type="hidden" name="isApprove2" value="<%=String.valueOf(isApprove2)%>">
                                      <input type="hidden" name="isApprove3" value="<%=String.valueOf(isApprove3)%>">
                                      <input type="hidden" name="indexApproval" value="1">
                                      <input type="hidden" name="oidDevHead" value="0">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <input type="hidden" name="TO_DEPARTMENT" size="40" value="HRD">
                                        <input type="hidden" name="TO_POSITION" size="40" value="HR MANAGER">
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                              <tr> 
                                              
                                                <td align="center"><b><font size="3">
                                                    DAY PAYMENT (DP) REQUEST
                                                  </font></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="center"> 
                                                  <div align="center"><b><font size="3">
                                                      
                                                  </font></b></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0">
                                                    <tr align="left"> 
                                                      <!--Name -->
                                                      <td  width="10%">Name</td>
                                                      <td  width="2%">:</td>
                                                        <input type="hidden" name="<%=FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_EMPLOYEE_ID]%>" value="<%=String.valueOf(oidEmployee)%>">
                                                      <td  width="88%"colspan="4"><b><%=strEmpFullName%></b></td>
                                                    </tr>
                                                    <tr> 
                                                      <!--Position -->
                                                      <td width="10%">Position</td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=strEmpPosition%></b></td>
                                                      <!--Payroll-->
                                                      <td width="10%">Payroll</td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=objEmployee.getEmployeeNum()%></b></td>
                                                    </tr>
                                                    <tr> 
                                                      <!--Division -->
                                                      <td width="10%"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=strEmpDivision%></b></td>
                                                      <!--Department-->
                                                      <td width="10%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=strEmpDepartment%></b></td>
                                                    </tr>
                                                    <tr> 
                                                      <!--Commencing Date -->
                                                      <td width="10%">Commencing Date</td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b><%=Formater.formatDate(objEmployee.getCommencingDate(), "MMMM dd, yyyy")%></b></td>
                                                      <!--Date of Request-->
                                                      <td width="10%">Date of Request</td>
                                                      <td width="2%">:</td>
                                                      <td width="38%"><b>
                                                          <%=Formater.formatDate(dpAppMain.getSubmissionDate(), "MMMM dd, yyyy")%>
                                                          <%//=ControlDatePopup.writeDate(FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_SUBMISSION_DATE],(dpAppMain.getSubmissionDate()==null ? new Date() : dpAppMain.getSubmissionDate()))%>
                                                      </b></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                            </tr>
                                            
                                                    <tr><td><hr></td></tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0">
                                                    <tr> 
                                                    <%
                                                          int balance = dpAppMain.getBalance();
                                                          %>
                                                    <input type="hidden" name="<%=FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_BALANCE]%>" value="<%=String.valueOf(balance)%>">
                                                      <td colspan="3">No. of days eligible :<b><%=String.valueOf(balance)%></b></td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="3">
                                                        <%
                                                        //System.out.println("-------------------------->"+vListDp.size());
                                                        if(vListDp.size()>0){
                                                            out.println(drawList(vListDp,approot));
                                                        }%>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp; </td>
                                              </tr>
                                              <tr> 
                                                <td valign="top"> 
                                                  <table width="100%" border="0" class="tablecolor" cellpadding="1" cellspacing="1">
                                                    <tr> 
                                                      <td valign="top"> 
                                                        <table width="100%" border="0" bgcolor="#F9FCFF">
                                                          <tr>
                                                                    <td width="15%" align="left"><b>Approval</b></td >
                                                                    <td width="2%" ></td>
                                                                    <td width="60%" align="center"><b>Signature</b></td>
                                                                    <td width="23%" align="center"><b>Date</b></td>
                                                                </tr>
                                                          <!--Employee-->
                                                          <tr>
                                                              <td width="15%" >Employee</td >
                                                              <td width="2%" >:</td>
                                                              <td width="60%" ><b><%=strEmpFullName%></b></td>
                                                              <td width="23%" ><b>
                                                                  <%=Formater.formatDate(dpAppMain.getSubmissionDate(), "MMMM dd, yyyy")%>
                                                                  <%//=ControlDatePopup.writeDate(FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_SUBMISSION_DATE],(dpAppMain.getSubmissionDate()==null ? new Date() : dpAppMain.getSubmissionDate()))%>
                                                              </b></td>
                                                          </tr>
                                                          <!--Dept Head-->
                                                          <%
                                                          if(dpAppMain.getOID()>0 &&  maxApporval>=1){
                                                          %>
                                                          <tr>
                                                              <td width="15%" >Div./Department Head</td >
                                                              <td width="2%" >:</td>
                                                              <td width="60%" ><b>
                                                                  <%if(dpAppMain.getApprovalId()>0){
                                                                      String strApp = "";
                                                                      Employee employee = new Employee();
                                                                      try{
                                                                          employee = PstEmployee.fetchExc(dpAppMain.getApprovalId());
                                                                          strApp = employee.getFullName();
                                                                      }catch(Exception ex){}
                                                                  %>
                                                                      <%=strApp%>
                                                                  <%}else{%>
                                                                  <%												  
                                                                      if(dpAppMain.getOID()!=0)
                                                                      {
                                                                              Vector divHeadKey = new Vector(1,1);
                                                                              Vector divHeadValue = new Vector(1,1);

                                                                              divHeadKey.add("Select Department Head");
                                                                              divHeadValue.add("0");

                                                                              String selectedApproval = ""+dpAppMain.getApprovalId();
                                                                              if(approvedId1>0){
                                                                                selectedApproval = ""+approvedId1;
                                                                              }

                                                                              Vector vectPositionLvl1 = new Vector(1,1);
                                                                              vectPositionLvl1.add(""+PstPosition.LEVEL_SECRETARY);
                                                                              vectPositionLvl1.add(""+PstPosition.LEVEL_SUPERVISOR);
                                                                              vectPositionLvl1.add(""+PstPosition.LEVEL_MANAGER);   
                                                                              vectPositionLvl1.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														           

                                                                              Vector listDivHead = SessEmployee.listEmployeeByPositionLevel(objEmployee, vectPositionLvl1);
                                                                              for(int i=0; i<listDivHead.size(); i++)
                                                                              {
                                                                                      Employee objEmp = (Employee)listDivHead.get(i);

                                                                                      if(objEmployee.getOID() != objEmp.getOID())
                                                                                      {
                                                                                              divHeadKey.add(objEmp.getFullName());
                                                                                              divHeadValue.add(""+objEmp.getOID());
                                                                                      }
                                                                              }
                                                                              String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(1)\"";
                                                                              out.println(ControlCombo.draw(FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL_ID], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                                      }
                                                                  }											  		  
                                                              %>
                                                          
                                                              </b></td>
                                                              <td width="23%" ><b>
                                                                    <%=ControlDatePopup.writeDate(FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL_DATE],(dpAppMain.getApprovalDate()==null ? new Date() : dpAppMain.getApprovalDate()))%>
                                                                  <%--if(dpAppMain.getApprovalId()>0){%>
                                                                  <%}--%>
                                                            </b></td>
                                                          </tr>
                                                          
                                                          <%}%>
                                                          <!--Exc Prod-->
                                                          <%
                                                          if(dpAppMain.getApprovalId()>0 && maxApporval>=2){
                                                          %>
                                                          <tr>
                                                              <td width="15%" >Exceutive Producer</td >
                                                              <td width="2%" >:</td>
                                                              <td width="60%" ><b>
                                                              <% 
                                                                  if(dpAppMain.getApproval2Id()>0)
                                                                  {
                                                                      String strApp = "";
                                                                      Employee employee = new Employee();
                                                                      try{
                                                                          employee = PstEmployee.fetchExc(dpAppMain.getApproval2Id());
                                                                          strApp = employee.getFullName();
                                                                      }catch(Exception ex){}
                                                                  %>
                                                                      <%=strApp%>
                                                                  <%}else{%>
                                                                  <%												  
                                                                      if(dpAppMain.getOID()>0 && dpAppMain.getApprovalId()>0)
                                                                          {
                                                                                  Vector divHeadKey = new Vector(1,1);
                                                                                  Vector divHeadValue = new Vector(1,1);

                                                                                  divHeadKey.add("Select Executive Producer");
                                                                                  divHeadValue.add("0");

                                                                                  String selectedApproval = ""+dpAppMain.getApproval2Id();
                                                                                  if(approvedId2>0){
                                                                                    selectedApproval = ""+approvedId2;
                                                                                  }

                                                                                  Vector vectPositionLvl1 = new Vector(1,1);
                                                                                 // vectPositionLvl1.add(""+PstPosition.LEVEL_SECRETARY);
                                                                                  vectPositionLvl1.add(""+PstPosition.LEVEL_SUPERVISOR);
                                                                                  vectPositionLvl1.add(""+PstPosition.LEVEL_MANAGER);   
                                                                                  vectPositionLvl1.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														           

                                                                                  Vector listDivHead = SessEmployee.listEmployeeByPositionLevel(objEmployee, vectPositionLvl1);
                                                                                  for(int i=0; i<listDivHead.size(); i++)
                                                                                  {
                                                                                          Employee objEmp = (Employee)listDivHead.get(i);

                                                                                          if(objEmployee.getOID() != objEmp.getOID())
                                                                                          {
                                                                                                  divHeadKey.add(objEmp.getFullName());
                                                                                                  divHeadValue.add(""+objEmp.getOID());
                                                                                          }
                                                                                  }
                                                                                  String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(2)\"";
                                                                                  out.println(ControlCombo.draw(FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL2_ID], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                                          }					
                                                                  }											  		  
                                                              %>
                                                          
                                                              </b></td>
                                                              <td width="23%" ><b>
                                                                   <%=ControlDatePopup.writeDate(FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL2_DATE],(dpAppMain.getApproval2Date()==null ? new Date() : dpAppMain.getApproval2Date()))%>
                                                                  <%--if(dpAppMain.getApproval2Id()>0){%>
                                                                  <%}--%>
                                                              </b></td>
                                                          </tr>
                                                          <%}%>
                                                          
                                                          <!--Prod Talent-->
                                                          <%
                                                          if(dpAppMain.getApproval2Id()>0 && maxApporval>=3){
                                                          %>
                                                          <tr>
                                                              <td width="15%" >Producer-Talent</td >
                                                              <td width="2%" >:</td>
                                                              <td width="60%" ><b>
                                                                  <%if(dpAppMain.getApproval3Id()>0){
                                                                      String strApp = "";
                                                                      Employee employee = new Employee();
                                                                      try{
                                                                          employee = PstEmployee.fetchExc(dpAppMain.getApproval3Id());
                                                                          strApp = employee.getFullName();
                                                                      }catch(Exception ex){}
                                                                  %>
                                                                              <%=strApp%>
                                                                          <%}else{%>
                                                                          <%												  
                                                                             if(dpAppMain.getOID()>0 && dpAppMain.getApproval2Id()>0)
                                                                                  {
                                                                                          Vector divHeadKey = new Vector(1,1);
                                                                                          Vector divHeadValue = new Vector(1,1);

                                                                                          divHeadKey.add("Select Producer - Talent");
                                                                                          divHeadValue.add("0");

                                                                                          String selectedApproval = ""+dpAppMain.getApproval3Id();
                                                                                          if(approvedId3>0){
                                                                                            selectedApproval = ""+approvedId3;
                                                                                          }

                                                                                          Vector vectPositionLvl1 = new Vector(1,1);
                                                                                         // vectPositionLvl1.add(""+PstPosition.LEVEL_SECRETARY);
                                                                                          vectPositionLvl1.add(""+PstPosition.LEVEL_SUPERVISOR);
                                                                                          vectPositionLvl1.add(""+PstPosition.LEVEL_MANAGER);   
                                                                                          vectPositionLvl1.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														           

                                                                                          Vector listDivHead = SessEmployee.listEmployeeByPositionLevel(objEmployee, vectPositionLvl1);
                                                                                          for(int i=0; i<listDivHead.size(); i++)
                                                                                          {
                                                                                                  Employee objEmp = (Employee)listDivHead.get(i);

                                                                                                  if(objEmployee.getOID() != objEmp.getOID())
                                                                                                  {
                                                                                                          divHeadKey.add(objEmp.getFullName());
                                                                                                          divHeadValue.add(""+objEmp.getOID());
                                                                                                  }
                                                                                          }
                                                                                          String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval(3)\"";
                                                                                          out.println(ControlCombo.draw(FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL3_ID], null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                                                  }
                                                                          }												  		  
                                                                      %>
                                                              </b></td>
                                                              <td width="23%" ><b>
                                                                    <%=ControlDatePopup.writeDate(FrmDpAppMain.fieldNames[FrmDpAppMain.FRM_FLD_APPROVAL3_DATE],(dpAppMain.getApproval3Date()==null ? new Date() : dpAppMain.getApproval3Date()))%>
                                                                  <%--if(dpAppMain.getApproval3Id()>0){--%>
                                                                  <%--}--%>
                                                              </b></td>
                                                          </tr>                                                          
                                                          <%} %>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <%
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("80");
                                                    ctrLine.setCommandStyle("buttonlink");												

                                                    String scomDel = "javascript:cmdAsk('"+oidDpAppMain+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidDpAppMain+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidDpAppMain+"')";

                                                    ctrLine.setAddCaption("");												
                                                    ctrLine.setBackCaption("Back to List Dp");
                                                    ctrLine.setConfirmDelCaption("Yes Delete Dp Application");
                                                    ctrLine.setDeleteCaption("Delete Dp Application");
                                                    ctrLine.setSaveCaption("Save Dp Application"); 

                                                    if ( (privDelete) && (dpAppMain.getApprovalId()==0) )
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

                                                    if((!privAdd) && (!privUpdate))
                                                    {
                                                            ctrLine.setSaveCaption("");
                                                    }

                                                    if (!privAdd)
                                                    {
                                                            ctrLine.setAddCaption("");
                                                    }

                                                    if(dpAppMain.getApprovalId()!=0)
                                                    {
                                                            ctrLine.setConfirmDelCaption("");
                                                            ctrLine.setDeleteCaption("");
                                                            ctrLine.setEditCaption("");												
                                                            ctrLine.setAddCaption("");													
                                                    }
                                                    if(dpAppMain.getApproval3Id()!=0)
                                                    {
                                                            ctrLine.setSaveCaption("");												
                                                    }

                                                    out.println(ctrLine.drawImage(iCommand, iErrCode, errMsg));
                                                    %>
                                                </td>
                                              </tr>
                                              <%if(dpAppMain.getApproval3Id()>0){%>
                                               <tr> 
                                                <td>
                                                <table><tr>
                                                  <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                  <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                  <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                  <td height="22" valign="middle" colspan="3" width="951"> 
                                                    <a href="javascript:cmdPrint()" class="command">Print</a> </td>
                                                    </tr>
                                                </table>
                                                </td>
                                                </tr>
                                              <%}%>
                                            </table>
                                          </td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                      </table>
                                    </form>
									<%
									if( (dpAppMain.getApprovalId()==0) )
									{
									%>									
                                    <script language="javascript">
										document.frm_dp_application.btnSrcDp.focus();
									</script>	
									<%
									}
									%>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> <!-- #EndEditable --> 
<!-- #EndTemplate --></html>
