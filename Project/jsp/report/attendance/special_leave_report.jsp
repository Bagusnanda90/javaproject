<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.qdep.form.*,				  
                  com.dimata.gui.jsp.*,
                  com.dimata.util.*,				  
                  com.dimata.harisma.entity.masterdata.*,				  				  
                  com.dimata.harisma.entity.employee.*,
                  com.dimata.harisma.entity.attendance.*,
                  com.dimata.harisma.entity.search.*,
                  com.dimata.harisma.form.masterdata.*,				  				  
                  com.dimata.harisma.form.attendance.*,
                  com.dimata.harisma.form.search.*,				  
                  com.dimata.harisma.session.attendance.*,
                  com.dimata.harisma.session.leave.SessLeaveApp"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_ATTENDANCE_RECORD_REPORT); %>
<% // int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_LL_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
int DATA_NULL = 0;
int DATA_PRINT = 1;

/**
 * create list object
 * consist of : 
 *  first index  ==> status object (will displayed or not)
 *  second index ==> object string will displayed
 *  third index  ==> object vector of string used in report on PDF format.
 */
public String drawList(Vector listSpecialLeave) 
{
    String result = "";
    if(listSpecialLeave!=null && listSpecialLeave.size()>0)
    {
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");

            ctrlist.addHeader("NO","5%", "0", "0");
            ctrlist.addHeader("PAYROLL","10%", "0", "0");
            ctrlist.addHeader("NAME","25%", "0", "0");
            ctrlist.addHeader("DEPARTMENT","10%", "0", "0");	
            ctrlist.addHeader("SECTION","10%", "0", "0");
            ctrlist.addHeader("LEAVE PERIOD","20%", "0", "0");
            ctrlist.addHeader("REMARK","20%", "0", "0");

            ctrlist.setLinkRow(-1);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();

            // iterasi sebanyak data Special Leave yang ada
            int iterateNo = 0;
            boolean resultAvailable = false;
            int total = 0;
            for (int i=0; i<listSpecialLeave.size(); i++) 
            {
                Vector vSpList = new Vector(1,1);
                vSpList = (Vector)listSpecialLeave.get(i);

                Vector vSpecialLeave = new Vector(1,1);
                Employee emp = new Employee();
                Department dep = new Department();
                Section sec = new Section();
                Vector vDetail = new Vector(1,1);

                
                iterateNo += 1;
                emp = (Employee)vSpList.get(0);
                dep = (Department)vSpList.get(1);
                sec = (Section)vSpList.get(2);
                vDetail = (Vector)vSpList.get(3);
                vSpecialLeave = (Vector)vSpList.get(4);
                
                Vector rowx = new Vector(1,1);  
                
                rowx.add(""+iterateNo);
                rowx.add(""+emp.getEmployeeNum());
                rowx.add(""+emp.getFullName());
                rowx.add(""+dep.getDepartment());
                rowx.add(""+sec.getSection());
                rowx.add(createStrTakenDate(vDetail));
                rowx.add(createRemark(vSpecialLeave));
                
                lstData.add(rowx);
                lstLinkData.add("0");
            }
            result = ctrlist.drawList();
            
    }
    else
    {					
            result += "<div class=\"msginfo\">&nbsp;&nbsp;No Special Leave data found ...</div>";																
    }
    return result;	
}

private Date getTommorow(Date date){
   Date tdate = (Date)date.clone();
   tdate.setDate(date.getDate()+1);
   return tdate;
}

private String createStrTakenDate(Vector vDetail){
    String strTakenDate = "";
    Date datePrev = null;
    Date dateNext = null;
    for(int j=0;j<vDetail.size();j++){
        Date dateTaken = (Date)vDetail.get(j);
        if(j==0){
            datePrev = dateTaken;
            dateNext = dateTaken;
            if(j+1==vDetail.size()){
                strTakenDate += Formater.formatDate(datePrev, "dd-MMM-yyyy");
            }
        }else{
            if(getTommorow(dateNext).getTime()==dateTaken.getTime()){
                dateNext = dateTaken;
                if(j+1==vDetail.size()){
                    if(strTakenDate.length()>0){strTakenDate += ", <br>";}
                    strTakenDate += Formater.formatDate(datePrev, "dd-MMM-yyyy")
                            +" s/d "+Formater.formatDate(dateNext, "dd-MMM-yyyy");
                }
            }else{
                if(datePrev.getTime()==dateNext.getTime()){
                    if(strTakenDate.length()>0){strTakenDate += ", <br>";}
                     strTakenDate += Formater.formatDate(datePrev, "dd-MMM-yyyy");
                }else{
                    if(strTakenDate.length()>0){strTakenDate += ", <br>";}
                    strTakenDate += Formater.formatDate(datePrev, "dd-MMM-yyyy")
                            +" s/d "+Formater.formatDate(dateNext, "dd-MMM-yyyy");
                }
                datePrev = dateTaken;
                dateNext = dateTaken;
                if(j+1==vDetail.size()){
                    if(strTakenDate.length()>0){strTakenDate += ", <br>";}
                     strTakenDate += Formater.formatDate(datePrev, "dd-MMM-yyyy");
                }
            }
        }

    }
    return strTakenDate;
}

private String createRemark(Vector vSpecialLeave){
    String strRemark = "";
    for(int i=0;i<vSpecialLeave.size();i++){
        SpecialLeave sp = new SpecialLeave();
        sp = (SpecialLeave)vSpecialLeave.get(i);
        if(strRemark.length()>0){
            strRemark += "<br>";
        }
        strRemark += sp.getOtherRemarks();
    }
    return strRemark;
}

%>


<%
int iCommand = FRMQueryString.requestCommand(request);
Date currPeriod = FRMQueryString.requestDate(request,"currPeriod");
long scheduleSymbolId = FRMQueryString.requestLong(request,"scheduleSymbolId");

ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
try{
    scheduleSymbol = (ScheduleSymbol)PstScheduleSymbol.fetchExc(scheduleSymbolId);
}catch(Exception ex){}
// for list
Vector listSpecialLeave = new Vector(1,1);
if(Command.LIST==iCommand){
    listSpecialLeave = SessLeaveApp.listSpecialLeave(currPeriod,scheduleSymbolId);
    session.removeValue("SESS_SPECIAL_LEAVE");
    session.putValue("SESS_SPECIAL_LEAVE", listSpecialLeave);
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Special Leave Report</title>
<script language="JavaScript">
<!--
function cmdView()
{
	document.frSpSpecial.command.value="<%=String.valueOf(Command.LIST)%>";
	document.frSpSpecial.action="special_leave_report.jsp";
	document.frSpSpecial.submit();
}

function cmdPrint()
{
	var linkPage = "<%=printroot%>.report.leave.SpecialLeaveReportPDF?scheduleSymbolId=<%=""+scheduleSymbolId%>&currPeriod=<%=""+currPeriod.getTime()%>";
	window.open(linkPage);
}

//-------------- script control line -------------------
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
//-->
</script>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<!-- #EndEditable -->
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report 
                  &gt; Attendance &gt; Special Leave Report<!-- #EndEditable --> 
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
                                    <form name="frSpSpecial" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Period</td>
                                          <td width="3%">:</td>
                                          <td width="78%">
                                              <%=ControlDate.drawDateMY("currPeriod", (currPeriod!=null?currPeriod:new Date()), "MMM yyyy", "formElemen", 0, installInterval)%> 
                                            </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Special Leave</td>
                                          <td width="3%">:</td>
                                          <td width="78%">
                                              <%
                                              Vector vCategory = new Vector(1,1);
                                              Vector vSchedule = new Vector(1,1);
                                              
                                              ScheduleCategory schCategory = new ScheduleCategory();
                                              try{
                                                  String strWhereClause = PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]
                                                          +"="+PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
                                                  vCategory = PstScheduleCategory.list(0, 0, strWhereClause, null);
                                                  schCategory = (ScheduleCategory)vCategory.get(0);
                                              }catch(Exception ex){}
                                              
                                              String whereClause = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]
                                                      +" = "+schCategory.getOID();
                                              
                                                  Vector schKey = new Vector(1,1);
                                                  Vector schValue = new Vector(1,1);
                                                  Vector listSchedule = PstScheduleSymbol.list(0, 0, whereClause,PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]);
                                                  for(int i =0;i < listSchedule.size();i++){
                                                        ScheduleSymbol scheduleSymbolTemp = (ScheduleSymbol)listSchedule.get(i);
                                                        schKey.add(scheduleSymbolTemp.getSchedule()+" ("+scheduleSymbolTemp.getSymbol()+")");
                                                        schValue.add(""+scheduleSymbolTemp.getOID());														
                                                  }
                                                  %>
                                            <%=ControlCombo.draw("scheduleSymbolId","formElemen",null,""+scheduleSymbol.getOID(),schValue,schKey)%>
                                            </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="18%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr> 
                                                <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Report AL"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Report Special Leave</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>									  
									  
									  
                                      <% 
                                      if(iCommand == Command.LIST)
                                      {
                                      %>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr>
                                          <td><hr></td>
                                        </tr>
                                        <tr>
                                          <td><%=drawList(listSpecialLeave)%></td>
                                        </tr>
                                        <%
                                            if(listSpecialLeave.size()>0 && privPrint)
                                            {
                                        %>
                                        <tr>
                                          <td class="command">
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr>
                                                <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image110','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image110" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print Report"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdPrint()">Print Report</a></td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <%
                                            }
                                        %>
                                      </table>
                                      <%
                                          }
                                      %>									  
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
