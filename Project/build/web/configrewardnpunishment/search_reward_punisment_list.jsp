
<%@page import="com.dimata.harisma.session.employee.SessEmployee"%>
<%@page import="com.dimata.harisma.session.configrewardpunishment.searchrewardpunishment"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="com.dimata.harisma.session.configrewardpunishment.SessTmpSrcRewardPunisment"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.RewardnPunismentDetail"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmRewardPunismentDetail"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.CtrlRewardPunismentDetail"%>
<%@page import="com.dimata.harisma.session.configrewardpunishment.SessSpecialPstSrcRewardPunisment"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentDetail"%>
<%@page import="com.dimata.harisma.entity.jenisSo.JenisSo"%>
<%@page import="com.dimata.harisma.entity.jenisSo.JenisSo"%>
<%@page import="com.dimata.harisma.entity.jenisSo.PstJenisSo"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployeeQuery"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.SrcRewardPunishment"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmSrcRewardPunishment"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmRewardPunismentMain"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ page language="java" %>  

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->

<%@ include file = "../main/javainit.jsp" %> 
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_ENTRI_OPNAME_SALES);%>
<%@ include file = "../main/checkuser.jsp" %>

<!-- Priska 2014-12-1-->
<%!
public String drawList(Vector objectClass, int st,int iCommand,long oidRewardPunisment){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No.","2%");
	ctrlist.addHeader("Nik","6%");
	ctrlist.addHeader("Nama","15%");
	ctrlist.addHeader("Jabatan","18%");
	ctrlist.addHeader("Koefisien","7%");
	ctrlist.addHeader("Hari Kerja","7%");
	ctrlist.addHeader("Adjusment","5%");
        ctrlist.addHeader("Total","15%");
	ctrlist.addHeader("Beban","10%");
        ctrlist.addHeader("Status Opname","10%");
	//ctrlist.addHeader("Tunai","10%");
	//ctrlist.addHeader("Lama","10%");
	//ctrlist.addHeader("Pot/tambahan Gaji","7%");
		
	ctrlist.setLinkRow(1);
	//ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit2('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

        
        //set rupiah
         DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                        dfs.setCurrencySymbol("");
                        dfs.setMonetaryDecimalSeparator(',');
                        dfs.setGroupingSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
             
	for (int i = 0; i < objectClass.size(); i++) {
		
                SessTmpSrcRewardPunisment sessTmpSrcRewardPunisment =  (SessTmpSrcRewardPunisment) objectClass.get(i);
                
                 try{
                     
                   
                    Vector rowx = new Vector();
                    rowx.add(String.valueOf(st + 1 + i));
                    if (iCommand == Command.EDIT && oidRewardPunisment!=0 && oidRewardPunisment==sessTmpSrcRewardPunisment.getRewardpunishmentdetailid() ) {
               
                    rowx.add(sessTmpSrcRewardPunisment.getEmpnumber());
                    rowx.add(sessTmpSrcRewardPunisment.getFullNameEmp());
                    rowx.add(sessTmpSrcRewardPunisment.getEmpposition()!=null && sessTmpSrcRewardPunisment.getEmpposition().length()>0 && !sessTmpSrcRewardPunisment.getEmpposition().equalsIgnoreCase("null") ?sessTmpSrcRewardPunisment.getEmpposition():"-"); 
                    rowx.add(" "+sessTmpSrcRewardPunisment.getKoefisien());
                    rowx.add(sessTmpSrcRewardPunisment.getHarikerja()!=0?""+sessTmpSrcRewardPunisment.getHarikerja():"-"); 
                    
                    rowx.add("<input type=\"hidden\"  name=\"" + FrmRewardPunismentDetail.fieldNames[FrmRewardPunismentDetail.FRM_FLD_REWARD_PUNISMENT_MAIN_ID]+ "\" value=\"" + sessTmpSrcRewardPunisment.getRewardpunishmentMainId() + "\" size=\"10\" class=\"elemenForm\">"+"<input type=\"text\"  name=\"" + FrmRewardPunismentDetail.fieldNames[FrmRewardPunismentDetail.FRM_FLD_ADJUSMENT]+ "\" value=\"" + sessTmpSrcRewardPunisment.getAdjusment() + "\" size=\"10\" class=\"elemenForm\">");
           
                     
                    rowx.add(sessTmpSrcRewardPunisment.getTotal()!=0?""+ sessTmpSrcRewardPunisment.getTotal():"-"); 
                    // "Rp. " + df.format(objEntriOpnameSales.getPlusMinus())
                    rowx.add(sessTmpSrcRewardPunisment.getBeban()!=0?"Rp. " + df.format(sessTmpSrcRewardPunisment.getBeban()):"-"); 
                    rowx.add(sessTmpSrcRewardPunisment.getStatuOpname());
                    
                    // rowx.add(sessTmpSrcRewardPunisment.getTunai()!=0?""+sessTmpSrcRewardPunisment.getTunai():"-");
                    // rowx.add(sessTmpSrcRewardPunisment.getMasacicilan()!=0?""+sessTmpSrcRewardPunisment.getMasacicilan():"-");
                    // rowx.add(sessTmpSrcRewardPunisment.getPotongangaji()!=0?""+sessTmpSrcRewardPunisment.getPotongangaji():"-");
                
                    lstData.add(rowx);
                    lstLinkData.add(String.valueOf(sessTmpSrcRewardPunisment.getRewardpunishmentdetailid()));
                    } else {
                       
                    rowx.add(sessTmpSrcRewardPunisment.getEmpnumber());
                    rowx.add(sessTmpSrcRewardPunisment.getFullNameEmp());
                    rowx.add(sessTmpSrcRewardPunisment.getEmpposition()!=null && sessTmpSrcRewardPunisment.getEmpposition().length()>0 && !sessTmpSrcRewardPunisment.getEmpposition().equalsIgnoreCase("null") ?sessTmpSrcRewardPunisment.getEmpposition():"-"); 
                    rowx.add(""+sessTmpSrcRewardPunisment.getKoefisien());
                    rowx.add(sessTmpSrcRewardPunisment.getHarikerja()!=0?""+sessTmpSrcRewardPunisment.getHarikerja():"-"); 
                    rowx.add(""+sessTmpSrcRewardPunisment.getAdjusment());
                    rowx.add(sessTmpSrcRewardPunisment.getTotal()!=0?""+ sessTmpSrcRewardPunisment.getTotal():"-"); 
                    // "Rp. " + df.format(objEntriOpnameSales.getPlusMinus())
                    rowx.add(sessTmpSrcRewardPunisment.getBeban()!=0?"Rp. " + Formater.formatNumber(sessTmpSrcRewardPunisment.getBeban(), "#,###.##"): "-"); 
                    rowx.add(sessTmpSrcRewardPunisment.getStatuOpname());
                    // rowx.add(sessTmpSrcRewardPunisment.getTunai()!=0?""+sessTmpSrcRewardPunisment.getTunai():"-");
                    // rowx.add(sessTmpSrcRewardPunisment.getMasacicilan()!=0?""+sessTmpSrcRewardPunisment.getMasacicilan():"-");
                    // rowx.add(sessTmpSrcRewardPunisment.getPotongangaji()!=0?""+sessTmpSrcRewardPunisment.getPotongangaji():"-");
                
                    lstData.add(rowx);
                    lstLinkData.add(String.valueOf(sessTmpSrcRewardPunisment.getRewardpunishmentdetailid()));
                    }
                           }catch(Exception exc){
                            System.out.println("Exception " + exc + " EmpNum "+sessTmpSrcRewardPunisment.getEmpnumber());
                           }
               
	}
	return ctrlist.draw();
}
%>
<%
long oidRewardPunisment;
if (FRMQueryString.requestLong(request, "RewardPunisment_oid") !=0){
    oidRewardPunisment = FRMQueryString.requestLong(request, "RewardPunisment_oid");
} else{
    oidRewardPunisment = FRMQueryString.requestLong(request, "oid_RewardPunisment");
}

long viewDirect = FRMQueryString.requestLong(request, "viewDirect");


int iCommand = FRMQueryString.requestCommand(request);
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int start = FRMQueryString.requestInt(request, "start");
int iErrCode = FRMMessage.ERR_NONE;
String msgString = "";
    
int recordToGet = 15;
int vectSize = 0;
String orderClause = "";
String whereClause = "RESIGNED = 0";

ControlLine ctrLine = new ControlLine();
//update by priska 2014-12-1
SrcRewardPunishment srcRewardPunishment = new SrcRewardPunishment();
CtrlRewardPunismentDetail ctrlRewardPunismentDetail = new CtrlRewardPunismentDetail(request);


if (viewDirect != 0){
   srcRewardPunishment.setRewardPunismentMainId(String.valueOf(viewDirect)); 
} 

FrmSrcRewardPunishment frmSrcRewardPunishment = new FrmSrcRewardPunishment(request, srcRewardPunishment);
if(iCommand == Command.LIST)
{
                frmSrcRewardPunishment.requestEntityObject(srcRewardPunishment);
}

if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand == Command.BACK))
{
 try
 {  
       //srcRewardPunishment = (SrcRewardPunishment)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE); 
	//		if (searchSpecialQuery == null) {
	//			searchSpecialQuery = new SearchSpecialQuery();
	//		}                 
     
 }
 catch(Exception e)
 {
	//searchSpecialQuery = new SearchSpecialQuery();
 }
}

//session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, searchSpecialQuery);

if(iCommand == Command.SAVE && prevCommand == Command.ADD)
{
	start = PstRewardAndPunishmentDetail.findLimitStart(oidRewardPunisment,recordToGet, whereClause,orderClause);
	vectSize = PstRewardAndPunishmentDetail.getCount(whereClause);
}
else
{
    vectSize = SessSpecialPstSrcRewardPunisment.countsearchSpecialRewardPunisment(srcRewardPunishment,0,0);  
}

if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
(iCommand==Command.LAST)||(iCommand==Command.LIST))
		start = ctrlRewardPunismentDetail.actionList(iCommand, start, vectSize, recordToGet); 

Vector listRewardPunisment = new Vector(1,1);
if(iCommand == Command.SAVE && prevCommand==Command.ADD)
{
    listRewardPunisment = SessSpecialPstSrcRewardPunisment.searchSpecialRewardPunisment(srcRewardPunishment,start,recordToGet); 
}
else
{   
    try{
        listRewardPunisment = SessSpecialPstSrcRewardPunisment.searchSpecialRewardPunisment(srcRewardPunishment,start,recordToGet); 
        
    }catch(Exception ex){
        
        }
}
session.putValue(SessSpecialPstSrcRewardPunisment.SESS_SRC_REWARD_PUNISMENT, listRewardPunisment);

    iErrCode = ctrlRewardPunismentDetail.action(iCommand, oidRewardPunisment,listRewardPunisment);
    msgString = ctrlRewardPunismentDetail.getMessage();
    
    FrmRewardPunismentDetail frmRewardPunismentDetail = ctrlRewardPunismentDetail.getForm();
    RewardnPunismentDetail rewardnPunismentDetail = ctrlRewardPunismentDetail.getRewardnPunismentDetail();


%>

<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Reward & Punisment Search</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
           
        function cmdChangeLoc(){
                document.<%=FrmRewardPunismentMain.FRM_REWARD_PUNISMENT_MAIN%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmRewardPunismentMain.FRM_REWARD_PUNISMENT_MAIN%>.action="search_entriopnamesales.jsp";
                document.<%=FrmRewardPunismentMain.FRM_REWARD_PUNISMENT_MAIN%>.submit();
            }
            
        function cmdSearch(){
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=Command.LIST%>";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_entriopnamesales.jsp";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
        }
          
       
        function cmdEdit2(oid){
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=Command.EDIT%>";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.RewardPunisment_oid.value=oid;
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment_list.jsp";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
        }  
          
        function cmdProsess(){
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=Command.POST%>";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment_list.jsp";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
        }
            
        function cmdListFirst(){
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=Command.FIRST%>";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment_list.jsp";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=Command.PREV%>";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment_list.jsp";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=Command.NEXT%>";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment_list.jsp";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=Command.LAST%>";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment_list.jsp";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
	}
       
       function cmdPrintXLS(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.RpXLS?viewDirect=<%=viewDirect%>");
	}
            
	function cmdBack(){
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=Command.BACK%>";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment.jsp";
		document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
	}

            function fnTrapKD(){
                if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
            }
            }
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
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>    
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr>  
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
                    <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
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
                  Reward Punisment &gt;  Search Result<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>;"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" style="border:1px solid <%=garisContent%>" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top"> 
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">									  
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="RewardPunisment_oid" value="<%=oidRewardPunisment%>">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle"><span class="listtitle">Employee List</span> 
                                          </td>
                                        </tr>
                                        <%if((listRewardPunisment!=null)&&(listRewardPunisment.size()>0)){%>
                                        <tr> 
                                          <td height="8" width="100%">
                                             
                                              <%=drawList(listRewardPunisment, start, iCommand, oidRewardPunisment)%>
                                              
                                          </td>
                                        </tr>
                                        <%}else{%>
                                        <tr> 
                                          <td height="8" width="100%" class="comment"><span class="comment"><br>
                                            &nbsp;No Data</span> 
                                          </td>
                                        </tr>
                                        <%}%>
                                      </table>                                                                                                                                                                                              
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      
                                        <tr>
                                          <td nowrap align="left" class="command">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td nowrap align="left" class="command"> 
                                            <table border="0" cellspacing="0" cellpadding="0" align="left">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Employee</a></td>
												<% 
												if (viewDirect != 0){
												%>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdPrintXLS()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1003','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1003" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Export to Excel"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdPrintXLS()" class="command">Export to Excel</a></td>
												<%
												}
												%>
												
												<% 
												if(privPrint)
												{
												%> 
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdPrint()" class="command">Print Employee</a></td>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdPrintXLS()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1003','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1003" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Export to Excel"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdPrintXLS()" class="command">Export to Excel</a></td>
                                              	<%
												}
												%>
											  </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                                                    <!-- #EndEditable -->      </table>
                                                                              
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                                    <tr>
                                        <td valign="bottom">
                                            <!-- untuk footer -->
                                            <%@include file="../footer.jsp" %>
                                        </td>

                                    </tr>
                                    <%} else {%>
                                    <tr> 
                                        <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                                            <%@ include file = "../main/footer.jsp" %>
                                            <!-- #EndEditable --> </td>
                                    </tr>
                                    <%}%>
                                </table>
                                </body>
                                <!-- #BeginEditable "script" --> <script language="JavaScript">
                                        var oBody = document.body;
                                        var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
                                </script>

                                <!-- #EndEditable --> <!-- #EndTemplate -->
                                </html>
