
<%-- 
    Document   : leave_dp_period
    Created on : Sep 7, 2010, 11:10:24 AM
    Author     : roy ajus
--%>



<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ page import = "com.dimata.harisma.session.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_AL_CLOSING); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%! 
static final int SEARCH_ALL = 1;
static final int SEARCH     = 2;
        
public String drawList(Vector objectClass)
{    
        String result = "";
	ControlList ctrlist = new ControlList();
	
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
        
        ctrlist.addHeader("NO","2%", "2", "0");
        ctrlist.addHeader("DEPT.","10%", "2", "0");
        ctrlist.addHeader("#EMP.","10%", "2", "0");
        ctrlist.addHeader("DP","35%", "0", "7");
        ctrlist.addHeader("AL","25%", "0", "5");
        ctrlist.addHeader("LL","25%", "0", "5");
        
        /* DP */
        ctrlist.addHeader("DP PREV.","5%","0","0");
        ctrlist.addHeader("TKN DP PREV.","5%","0","0");
        ctrlist.addHeader("EXP DP PREV.","5%","0","0");
        ctrlist.addHeader("DP","5%","0","0");
        ctrlist.addHeader("TKN DP","5%","0","0");
        ctrlist.addHeader("EXP DP","5%","0","0");
        ctrlist.addHeader("BAL. DP","5%","0","0");        
        
        /* AL */
        ctrlist.addHeader("AL PREV.","5%","0","0");
        ctrlist.addHeader("AL TAKEN PREV.","5%","0","0");
        ctrlist.addHeader("AL","5%","0","0");
        ctrlist.addHeader("AL TAKEN","5%","0","0");
        ctrlist.addHeader("BAL. AL","5%","0","0");
        
        /* LL */
        ctrlist.addHeader("LL PREV.","5%","0","0");
        ctrlist.addHeader("LL TAKEN PREV.","5%","0","0");
        ctrlist.addHeader("LL","5%","0","0");
        ctrlist.addHeader("LL TAKEN","5%","0","0");        
        ctrlist.addHeader("BAL. LL","5%","0","0");        
        
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
        int no = 1 ; 
        
        int sumEmp = 0;
        int dpQtyBeforeStartPeriod = 0;
        int dpTknBeforeStartPeriod = 0;
        int dpTknExpBeforeStartPeriod = 0;
        int dpQtyCurrentPeriod = 0;
        int dpTknCurrentPeriod = 0;
        int dpTknExpiredCurrentPeriod = 0;  
        int db_balance = 0;
        int alQtyBeforeStartPeriod = 0;
        int alTknBeforeStartPeriod = 0;
        int alQtyCurrentPeriod = 0;
        int alTknCurrentPeriod = 0;
        int al_balance = 0;
        
        if(objectClass != null && objectClass.size() > 0){
            
            for (int i=0; i<objectClass.size(); i++) 
            {
                
                int bal_dp = 0;
                int bal_al = 0;
                int bal_ll = 0;
                Vector rowx = new Vector();
                
                RepLevDepartment repLevDepartment = new RepLevDepartment();
                
                repLevDepartment = (RepLevDepartment)objectClass.get(i);
                
                rowx.add(""+no);
                
                rowx.add(""+repLevDepartment.getDepartment());
                rowx.add(""+repLevDepartment.getCountEmployee());
                rowx.add(""+repLevDepartment.getDpQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpTknExpBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getDpTknCurrentPeriod());
                rowx.add(""+repLevDepartment.getDpTknExpiredCurrentPeriod());
                
                dpQtyBeforeStartPeriod = dpQtyBeforeStartPeriod + repLevDepartment.getDpTknExpBeforeStartPeriod();
                dpTknBeforeStartPeriod = dpTknBeforeStartPeriod + repLevDepartment.getDpTknBeforeStartPeriod();
                dpTknExpBeforeStartPeriod = dpTknExpBeforeStartPeriod + repLevDepartment.getDpTknExpBeforeStartPeriod();
                dpQtyCurrentPeriod = dpQtyCurrentPeriod + repLevDepartment.getDpQtyCurrentPeriod();
                dpTknCurrentPeriod = dpTknCurrentPeriod + repLevDepartment.getDpTknCurrentPeriod();
                dpTknExpiredCurrentPeriod = dpTknExpiredCurrentPeriod + repLevDepartment.getDpTknExpiredCurrentPeriod();
                                        
                bal_dp =  repLevDepartment.getDpQtyBeforeStartPeriod() - 
                          repLevDepartment.getDpTknBeforeStartPeriod() -
                          repLevDepartment.getDpTknExpBeforeStartPeriod() +
                          repLevDepartment.getDpQtyCurrentPeriod() - 
                          repLevDepartment.getDpTknCurrentPeriod() - 
                          repLevDepartment.getDpTknExpiredCurrentPeriod();
                
                rowx.add(""+bal_dp);
                db_balance = db_balance + bal_dp;                
                
                rowx.add(""+repLevDepartment.getAlQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getAlTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getAlQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getAlTknCurrentPeriod());
                
                bal_al = repLevDepartment.getAlQtyBeforeStartPeriod() -
                         repLevDepartment.getAlTknBeforeStartPeriod() +
                         repLevDepartment.getAlQtyCurrentPeriod() - 
                         repLevDepartment.getAlTknCurrentPeriod();
                rowx.add(""+bal_al);
                
                alQtyBeforeStartPeriod = alQtyBeforeStartPeriod + repLevDepartment.getAlQtyBeforeStartPeriod();
                alTknBeforeStartPeriod = alTknBeforeStartPeriod + repLevDepartment.getAlTknBeforeStartPeriod();
                alQtyCurrentPeriod = alQtyCurrentPeriod + repLevDepartment.getAlQtyCurrentPeriod();
                alTknCurrentPeriod = alTknCurrentPeriod + repLevDepartment.getAlTknCurrentPeriod();
                al_balance = al_balance + bal_al;
                
                rowx.add(""+0);
                rowx.add(""+0);
                rowx.add(""+0);
                rowx.add(""+0);
                rowx.add(""+0);
                
                no++;
		lstData.add(rowx);
                lstLinkData.add("0");

            }
            
            Vector rowx = new Vector(1,1);  
            rowx.add("TOTAL");
            rowx.add("");
            rowx.add("");
            
            rowx.add(""+dpQtyBeforeStartPeriod);
            rowx.add(""+dpTknBeforeStartPeriod);            
            rowx.add(""+dpTknExpBeforeStartPeriod);
            rowx.add(""+dpQtyCurrentPeriod);
            rowx.add(""+dpTknCurrentPeriod);
            rowx.add(""+dpTknExpiredCurrentPeriod);
            rowx.add(""+db_balance);
            
            rowx.add(""+alQtyBeforeStartPeriod);
            rowx.add(""+alTknBeforeStartPeriod);
            rowx.add(""+alQtyCurrentPeriod);
            rowx.add(""+alTknCurrentPeriod);
            rowx.add(""+al_balance);
            
            rowx.add(""+0);
            rowx.add(""+0);
            rowx.add(""+0);
            rowx.add(""+0);
            rowx.add(""+0);
            
            lstData.add(rowx);
            lstLinkData.add("0");
                
            result = ctrlist.drawList();
        
        }else{					
            result += "<div class=\"msginfo\">&nbsp;&nbsp;Leave and Dp Stock Data Found found ...</div>";																
        }
        //try{
        //    ctrlist.drawMe(outObj,-1);
        //}catch(Exception e){
        //    System.out.println("Exception "+e.toString());
        //}
        //return "";
        return result;
}
%>



<%

privAdd=false;

int iCommand = FRMQueryString.requestCommand(request);
int Type = FRMQueryString.requestInt(request, "Type");

SrcLeaveAppAlClosing objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing();

FrmSrcLeaveAppAlClosing objFrmSrcLeaveAppAlClosing = new FrmSrcLeaveAppAlClosing(request, objSrcLeaveAppAlClosing);

Vector result = new Vector();

/* Jika kondisi adalah view */
if(iCommand == Command.VIEW){
    
        objFrmSrcLeaveAppAlClosing.requestEntityObject(objSrcLeaveAppAlClosing);
    
        result = SessLeaveApplication.sumLeave_Department(objSrcLeaveAppAlClosing.getPeriodId());
        
        //result = SessLeaveApplication.sumLeave_Department(objSrcLeaveAppAlClosing.getEmpCommancingDateStart(), objSrcLeaveAppAlClosing.getEmpCommancingDateStart());
    
        try{
		session.removeValue("LEAVE_DEPARTMENT_REPORT");
	}
	catch(Exception e)
	{
		System.out.println("Exc when remove from session(\"LEAVE_DEPARTMENT_REPORT\") : " + e.toString());	
	}
	

	Vector listToSession = new Vector(1,1);
	listToSession.add(objSrcLeaveAppAlClosing);
	listToSession.add(result);	
	
	try{
		session.putValue("LEAVE_DEPARTMENT_REPORT",listToSession);
	}catch(Exception e){
		System.out.println("Exc when put to session(\"LEAVE_DEPARTMENT_REPORT\") : " + e.toString());		
	}
    
}


if(iCommand == Command.YES){
    
        objFrmSrcLeaveAppAlClosing.requestEntityObject(objSrcLeaveAppAlClosing);
    
        result = SessLeaveApplication.sumLeave_Department(objSrcLeaveAppAlClosing.getPeriodId());
    
        try{
		session.removeValue("LEAVE_DEPARTMENT_REPORT");
	}
	catch(Exception e)
	{
		System.out.println("Exc when remove from session(\"LEAVE_DEPARTMENT_REPORT\") : " + e.toString());	
	}
	

	Vector listToSession = new Vector(1,1);
	listToSession.add(objSrcLeaveAppAlClosing);
	listToSession.add(result);	
	
	try{
		session.putValue("LEAVE_DEPARTMENT_REPORT",listToSession);
	}catch(Exception e){
		System.out.println("Exc when put to session(\"LEAVE_DEPARTMENT_REPORT\") : " + e.toString());		
	}
    
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Report Leave Department By Period</title>
<script language="JavaScript">
<!--

function cmdPrintXLS() {
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value ="<%=String.valueOf(Command.ACTIVATE)%>";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action ="leave_department_by_period.jsp";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}    

function cmdSearchPeriod(){
        getCommancingDateStart();
        getCommancingDateEnd();
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value ="<%=String.valueOf(Command.VIEW)%>";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action ="leave_department_by_period.jsp";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}


function getThn(){
}

function hideObjectForDate(index){
}

function showObjectForDate(){
} 

function getCommancingDateStart(){    
     <%=ControlDatePopup.writeDateCaller(FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP,FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_START])%>
}

function getCommancingDateEnd(){    
     <%=ControlDatePopup.writeDateCaller(FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP,FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_END])%>
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
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #BeginEditable "stylestab" -->  
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">

<!-- Untuk Calendar-->
<%=ControlDatePopup.writeTable(approot)%>
<!-- End Calendar-->


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
                  Employee &gt; Leave &gt; Leave sum Report <!-- #EndEditable --> 
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
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
                                    		  <!-- #BeginEditable "content" --> 
                                    <form name="<%=objFrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>" method="post" action="">
                                      <!--<input type="hidden" name="command" value="">-->
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="Type" value="<%=String.valueOf(Type)%>">     
                                      <table border="0" cellspacing="2" cellpadding="2" width="100%" > 
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%">&nbsp;</td>
                                        </tr>                                        
                                        <tr> 
                                          <td width="13%">Period</td>
                                          <td width="2%">:</td>
                                          <td width="85%" class="command" nowrap>                                         
                                          <%
                                                                                      
                                            String selectValuePeriod = ""+objSrcLeaveAppAlClosing.getPeriodId();
                                            
                                            Vector period_value = new Vector(1, 1);
                                            Vector period_key = new Vector(1, 1);
            
                                            period_value.add("0");
                                            period_key.add("select...");
                                            Vector listPeriod = new Vector(1, 1);
           
                                            listPeriod = PstPeriod.list(0, 0, "", PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" DESC");
                                            for (int i = 0; i < listPeriod.size(); i++) {
                                                Period period = (Period) listPeriod.get(i);
                                                period_key.add(period.getPeriod());
                                                period_value.add(String.valueOf(period.getOID()));
                                            }
                                           %>
                                           <%= ControlCombo.draw(FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_PERIOD], null, selectValuePeriod, period_value, period_key, " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                        </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%"> 
                                            <table border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td width="25px"></td>
                                                <td width="5 px"></td>                                                
                                                <td width="30px" class="command" nowrap align="left"><a href="javascript:cmdSearchPeriod()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search closing employee" ></a></td>  
                                                <td width="100px"><a href="javascript:cmdSearchPeriod()">Search</a></td>                                                
                                                <td width="25px"></td>
                                                <td width="15px"></td>
                                                <td width="100px"></td>                                                                                                     
                                              </tr>                                              
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%">&nbsp;</td>
                                        </tr>
                                         <tr>
                                        <td colspan="3">
                                        <% 
                                        if(result != null && result.size() > 0) {                                               
                                        %>        
                                                <%=drawList(result)%>
                                        </td>
                                        </tr> 
                                         <tr>
                                        <td colspan="3">
                                        &nbsp;
                                        </td>
                                        </tr> 
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%"> 
                                            <table border="0" cellpadding="0" cellspacing="0" width="225">
                                              <tr> 
                                                <td width="25px" ><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOut="MM_swapImgRestore()" ><img name="Image300" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Close Period" ></a></td>
                                                <td width="5px"></td>
                                                <td width="30px" class="command" nowrap><a href="javascript:cmdPrint()">Print XLS</a></td>                                                                                                                                                      
                                                <td width="15px"></td>
                                                <td width="30px" ></td>
                                                <td width="10px"></td>
                                                <td width="10px"></td>                                                                                                     
                                              </tr>                                                   
                                            </table>
                                          </td>
                                        </tr>
                                        <%
                                        }
                                        %>
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
</html>