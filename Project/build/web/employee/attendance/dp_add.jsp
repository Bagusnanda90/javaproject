
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,                  
                  com.dimata.util.Command,
                  com.dimata.util.Formater,				  
                  com.dimata.gui.jsp.*,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.gui.jsp.ControlList,				  
                  com.dimata.gui.jsp.ControlLine ,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.masterdata.LeavePeriod,				  
                  com.dimata.harisma.entity.masterdata.PstLeavePeriod,
                  com.dimata.harisma.entity.masterdata.PstPeriod,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.employee.PstEmployee,
                  com.dimata.harisma.entity.attendance.DpStockManagement,
                  com.dimata.harisma.entity.attendance.PstDpStockManagement,				  
                  com.dimata.harisma.form.attendance.FrmDpStockManagement,
                  com.dimata.harisma.form.attendance.CtrlDpStockManagement,
                  com.dimata.harisma.entity.search.SrcLeaveManagement, 
                  com.dimata.harisma.form.search.FrmSrcLeaveManagement,
                  com.dimata.harisma.entity.leave.I_Leave,
                  com.dimata.harisma.session.attendance.SessLeaveManagement"%>    
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>    
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_DP_MANAGEMENT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
long oidDp = FRMQueryString.requestLong(request,"dp_id");

// get and set data from / to session
try
{		  
	if(session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_AL) != null)
	{
		SrcLeaveManagement srcLeaveManagement = (SrcLeaveManagement)session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_AL); 			
		session.putValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_AL, srcLeaveManagement);			
	}
}
catch(Exception e)
{ 
	System.out.println("Exc when get/set data from/to session");
}

// konstanta untuk navigasi ke database  
String msgString = "";
int iErrCode = 0;

// for manage data dp
CtrlDpStockManagement ctrlDpStockManagement = new CtrlDpStockManagement(request);	
iErrCode = ctrlDpStockManagement.action(iCommand, oidDp);
msgString =  ctrlDpStockManagement.getMessage();
DpStockManagement objDpStockManagement = ctrlDpStockManagement.getDpStockManagement();

String messageSave = "";
boolean savedOk = false;
if (iCommand==Command.SAVE && iErrCode == 0)
{
	msgString = "<div class=\"msginfo\">&nbsp;&nbsp;Data has been saved ...</div>"; 
	iCommand = Command.ADD;
	objDpStockManagement = new DpStockManagement();
	oidDp = 0;
	savedOk = true;
}

I_Leave leaveConfig=null; 
try{
leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
}catch(Exception except){
System.out.println("Exception"+except);
}
String readOnly="readonly";
String StyleBg="background-color:#F5F5F5;text-align:right";
boolean cekDpMinus=true;
if(leaveConfig!=null && leaveConfig.getConfigurationDpMinus()==false){
    readOnly=""; 
    StyleBg="text-align:right";
    cekDpMinus = false; 
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - DP Management</title>
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> <!-- #EndEditable --> 
<!-- mySQL format yyyy-mm-dd 
<script language="JavaScript" src="<%//=approot%>/main/calendar/calendar3.js"></script>
-->
<!-- Date only with year scrolling -->
<script language="JavaScript">
<!--
function calqty()
{
    var alqty = document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_QTY]%>.value;
    var usedqty = document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_USED]%>.value;
    if(isNaN(alqty) || alqty=="")
	{
        alqty = 0;
	}
	
    if(isNaN(usedqty) || usedqty=="")
	{
        usedqty = 0;
	}

    if(parseFloat(alqty) - parseFloat(usedqty) < 0 && <%=cekDpMinus%>)
	{
		alert('Used quantity should be less or equal with Entitled quantity !!!');
		usedqty = 0;
		document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_USED]%>.value = 0;
	}	
	
    var resqty = document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_RESIDUE]%>.value = parseFloat(alqty) - parseFloat(usedqty);
}


function cmdSave()
{
	document.frdp.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.frdp.action="dp_add.jsp";
      if(<%=leaveConfig!=null && leaveConfig.getConfigurationDpMinus()==false%>){
          //alert(document.frdp.<//%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_QTY]%>);
          //alert(document.frdp.<//%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_QTY]%>.value);
        var alqty = document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_QTY]%>.value;
        
        if(parseFloat(alqty)==0){
            var answer = confirm (" are you sure to input DP Acquisition '0' value?")
            if (answer){
                 //alert ("Woo Hoo! So am I.")
                 document.frdp.submit();
            }
               
           // else 
                //alert ("Darn. Well, keep trying then.")
            
        }else{
            
             document.frdp.submit();
        }
     }else{
         document.frdp.submit();
         
     }
	
}

function cmdBack(){
	document.frdp.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frdp.action="dp.jsp";
	document.frdp.submit();
}

function cmdSearchEmp(){
	emp_number = document.frdp.EMP_NUMBER.value;
	emp_fullname = document.frdp.EMP_FULLNAME.value;
	emp_department = document.frdp.EMP_DEPARTMENT.value;
	//window.open("empsearchdp.jsp?emp_number=" + emp_number + "&emp_fullname=" + emp_fullname + "&emp_department=" + emp_department);
        //window.open("empsearchdp.jsp?emp_number=" + emp_number + "&emp_fullname=" + emp_fullname + "&emp_department=" + emp_department, null, "height=460,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
        window.open("<%=approot%>/employee/search/search.jsp?formName=frdp&empPathId=<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}

function cmdClearSearchEmp(){
	document.frdp.EMP_NUMBER.value = "";
	document.frdp.EMP_FULLNAME.value = "";
	document.frdp.EMP_DEPARTMENT.value = "";
}

//--------------- Calendar  -------------------------------
function getThn(){
            <%=ControlDatePopup.writeDateCaller("frdp",FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE])%>
            <%=ControlDatePopup.writeDateCaller("frdp",FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE])%>
            <%=ControlDatePopup.writeDateCaller("frdp",FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC])%>
            
    }


    function hideObjectForDate(index){
        if(index==1){
            <%=ControlDatePopup.writeDateHideObj("frdp", FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_STATUS])%>
        }
    }

    function showObjectForDate(){
        <%=ControlDatePopup.writeDateShowObj("frdp", FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_STATUS])%>
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


</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/harisma_proj/images/BtnDelOn.jpg','/harisma_proj/images/BtnNewOn.jpg','<%=approot%>/images/BtnSearchOn.jpg','/harisma_proj/images/BtnBackOn.jpg')">
<!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<!-- End Calender-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                  &gt; DP (Day Off Payment) &gt; Add New DP Per Employee<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frdp" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="dp_id" value="<%=String.valueOf(oidDp)%>">
                                      <input type="hidden" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_LEAVE_PERIODE_ID]%>" value="0">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td colspan="3"> 
                                            <li>&nbsp;<b>Pick one employee using 
                                              Search form</b> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <table cellpadding="1" cellspacing="1" border="0" width="40%" bgcolor="#E0EDF0">
                                              <tr> 
                                                <td width="84"></td>
                                                <td width="11"></td>
                                                <td width="285"></td>
                                              </tr>
                                              <tr> 
                                                <td valign="top" width="84"> 
                                                  <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL) %></div>
                                                </td>
                                                <td width="11">:</td>
                                                <td width="285"> 
                                                  <input type="text" name="EMP_NUMBER"  value="" class="elemenForm" size="10" readonly>
                                                  <input type="hidden" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EMPLOYEE_ID]%>" value="" class="formElemen" >
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td valign="top" width="84"> 
                                                  <div align="left">Name</div>
                                                </td>
                                                <td width="11">:</td>
                                                <td width="285"> 
                                                  <input type="text" name="EMP_FULLNAME"  value="" class="elemenForm" size="40" readonly>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td valign="top" width="84"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="11">:</td>
                                                <td width="285"> 
                                                 <input type="text" name="EMP_DEPARTMENT"  value="" class="elemenForm" size="20" readonly>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td valign="top" width="84">&nbsp;</td>
                                                <td width="11">&nbsp;</td>
                                                <td width="285"> <a href="javascript:cmdSearchEmp()" class="command">Search 
                                                  Employee</a> | <a href="javascript:cmdClearSearchEmp()" class="command">Clear 
                                                  Search</a> </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <li>&nbsp;<b>Fill Day Off Payment 
                                              data </b> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <table width="100%" border="0">
                                              <tr> 
                                                <td valign="top" width="50%"> 
                                                  <table width="100%" border="0">
                                                    <tr> 
                                                      <td width="24%">Acquisition 
                                                        Date</td>
                                                      <td width="4%">:</td>
                                                      <td width="72%"> 
                                                        <%
                                                              Date selectedOwnDate = objDpStockManagement.getDtOwningDate()!=null ? objDpStockManagement.getDtOwningDate() : new Date();
                                                            //  out.println(ControlDate.drawDate(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE], selectedOwnDate, 0, installInterval)); 
							%>
                                                    <%=ControlDatePopup.writeDate(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE],selectedOwnDate)%>
                                                </td>
                                                
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="24%">Expired Date</td>
                                                      <td width="4%">:</td>
                                                      <td width="72%"> 
                                                        <%
                                                              //I_Leave leaveConfig = null;
                                                              //try {
            //leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        //} catch (Exception e) {
          //  System.out.println("Exception : " + e.getMessage());
       // }
                                                        
                                                              Date selectedExpDate = objDpStockManagement.getDtExpiredDate()!=null ? objDpStockManagement.getDtExpiredDate() : new Date();
                                                              //out.println(ControlDate.drawDate(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE], selectedExpDate, 1, (installInterval-1))); 
							%>
                                                        <%=ControlDatePopup.writeDate(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE],selectedExpDate)%>
                                                        <%//=ControlDateWinPop.writeInputDate(
                                                        //FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE], 
                                                        //"objExpDate", 
                                                        //selectedExpDate)%>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="24%">DP Acquisition</td>
                                                      <td width="4%">:</td>
                                                      <td width="72%"> 
                                                        <input type="text" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_QTY]%>" value="<%=String.valueOf(objDpStockManagement.getiDpQty())%>" onKeyUp="javascript:calqty()" size="10" style="text-align:right">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="24%">DP Taken</td>
                                                      <td width="4%">:</td>
                                                      <td width="72%"> 
                                                        <input type="text" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_USED]%>" value="<%=String.valueOf(objDpStockManagement.getQtyUsed())%>" onKeyUp="javascript:calqty()" style="<%=StyleBg%>" <%=readOnly%> size="10">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="24%">DP Balance</td>
                                                      <td width="4%">:</td>
                                                      <td width="72%"> 
                                                        <input type="text" bgcolor =\"000000\" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_RESIDUE]%>" value="<%=String.valueOf(objDpStockManagement.getQtyResidue())%>" onKeyUp="javascript:calqty()" style="background-color:#F5F5F5;text-align:right" readonly size="10">
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td valign="top" width="50%"> 
                                                  <table width="100%" border="0">
                                                    <tr> 
                                                      <td width="26%">Exception 
                                                        Status </td>
                                                      <td width="4%">:</td>
                                                      <td width="70%"> 
                                                        <%
                                                          Vector fgValue = new Vector(1,1);
                                                          Vector fgKey = new Vector(1,1);
                                                          fgValue.add("0");
                                                          fgKey.add("NO");
                                                          fgValue.add("1");
                                                          fgKey.add("YES");
                                                          out.println(ControlCombo.draw(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXCEPTION_FLAG],null,"0",fgValue,fgKey));
                                                          %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="26%">Exc. Expired 
                                                        Date</td>
                                                      <td width="4%">:</td>
                                                      <td width="70%"> 
                                                        <%
                                                          Date selectedExcDate = objDpStockManagement.getDtExpiredDateExc()!=null ? objDpStockManagement.getDtExpiredDateExc() : new Date();
                                                         //out.println(ControlDate.drawDate(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC], selectedExcDate, 1, (installInterval-1))); 
                                                          %>
                                                         <%=ControlDatePopup.writeDate(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC],selectedExcDate,1)%>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="26%">Status</td>
                                                      <td width="4%">:</td>
                                                      <td width="70%"> 
                                                        <%
                                                          Vector stValue = new Vector(1,1);
                                                          Vector stKey = new Vector(1,1);

                                                          stValue.add(""+PstDpStockManagement.DP_STS_AKTIF);
                                                          stKey.add(PstDpStockManagement.fieldStatus[PstDpStockManagement.DP_STS_AKTIF]);

                                                          out.println(ControlCombo.draw(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_STATUS],null,"",stValue,stKey));			
                                                          %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="26%">Notes</td>
                                                      <td width="4%">:</td>
                                                      <td width="70%"> 
                                                        <input type="text" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_NOTE]%>" size="40" value="">
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">
                                          <%
                                          if(savedOk)
                                          {
                                                out.println(msgString);
                                          }
                                          else
                                          {
                                          out.println("&nbsp;");
                                          }
                                          %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                              <%
                                                ControlLine ctrLine = new ControlLine();
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("60%");

                                                    String scomDel = "javascript:cmdAsk('"+oidDp+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidDp+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidDp+"')";
                                                    ctrLine.setBackCaption("Back to List");
                                                    ctrLine.setCommandStyle("buttonlink");
                                                    ctrLine.setBackCaption("Back to List DP");
                                                    ctrLine.setSaveCaption("Save DP");
                                                    ctrLine.setConfirmDelCaption("Yes Delete DP");
                                                    ctrLine.setDeleteCaption("Delete DP");
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
                                                    %>										  
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
								    <script language="javascript">
									document.frdp.EMP_NUMBER.focus();
								    </script>	
                                                                     

                                    <script language="JavaScript">
                                    <!-- // create calendar object(s) just after form tag closed
                                             // specify form element as the only parameter (document.forms['formname'].elements['inputname']);
                                             // note: you can have as many calendar objects as you need for your application
                                             //-------------- Form Win Date--------------
                                            <%//=ControlDateWinPop.writeObj(
                                              //          "frdp", 
                                              //          FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE],
                                              //          "objExpDate", 
                                              //          (approot+"/main/"), 
                                              //          true)%>
                                    //-->
                                    </script>
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
<!-- #BeginEditable "script" --> <!-- #EndEditable --> 
<!-- #EndTemplate -->
</html>
