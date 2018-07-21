<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*"%>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import ="com.dimata.harisma.session.attendance.*"%>
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
    //boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!
public Vector drawList(Vector listEmployee, Date dtSch) {
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("90%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","1%", "2", "0");
	ctrlist.addHeader("Employee","6%", "2", "0");
	ctrlist.addHeader("Schedule","6%", "0", "3");
	ctrlist.addHeader("Symbol","2%", "0", "0");
	ctrlist.addHeader("Time In","2%", "0", "0");
	ctrlist.addHeader("Time Out","2%", "0", "0");
	ctrlist.addHeader("Actual","2%", "0", "3");
	ctrlist.addHeader("Time In","2%", "0", "0");
	ctrlist.addHeader("Duration","2%", "0", "0");	
	ctrlist.addHeader("Time Out","2%", "0", "0");
	ctrlist.addHeader("Difference","4%", "0", "2");
	ctrlist.addHeader("In","2%", "0", "0");
	ctrlist.addHeader("Out","2%", "0", "0");
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector result = new Vector(1,1);	
	Vector list = new Vector(1,1);
	
	for (int i = 0; i < listEmployee.size(); i++) {
		Vector temp = new Vector(1,1);
		Employee employee = (Employee)listEmployee.get(i);
		ScheduleSymbol scheduleSymbol = (ScheduleSymbol)SessPresence.getSchedule(employee.getOID(),dtSch);		
		temp.add(employee);
		temp.add(scheduleSymbol);
		
		Vector presence = new Vector(1,1);
		Date dt = (Date)dtSch.clone();
		Date timeIn = scheduleSymbol.getTimeIn();
		Date timeOut = scheduleSymbol.getTimeOut();
		Date actualTimeIn = null;
		Date actualTimeOut = null;		
		

                long PRESENT_NOT_EXIST=(7*24*60*60*1000L); // set 7 hari perbedaan .. hal yg tidak mungkin antara schedule dan present IN/OUT
                long MAX_PRESNT_VS_SCHDL=(6*60*60*1000L);                 
                			
		if(timeIn != null)
		{
			timeIn = new Date(dt.getYear(),dt.getMonth(),dt.getDate(),timeIn.getHours(),timeIn.getMinutes());
			actualTimeIn = (Date)SessPresence.getPresenceActualIn(employee.getOID(),timeIn);
                        
                            long difIn = actualTimeIn!=null ? Math.abs(actualTimeIn.getTime()-timeIn.getTime()) : PRESENT_NOT_EXIST;                                    
                            
                            Date timeIn2 = new Date(timeIn.getTime()-24*60*60*1000);  // date sebelumnya
                            Date actualTimeIn2 = (Date)SessPresence.getPresenceActualIn(employee.getOID(),timeIn2);
                            long difIn2 = actualTimeIn2!=null ? Math.abs(actualTimeIn2.getTime()-timeIn.getTime()) : PRESENT_NOT_EXIST;                                    

                            Date timeIn3 = new Date(timeIn.getTime()+24*60*60*1000);  // date setelah time in schedule
                            Date actualTimeIn3 = (Date)SessPresence.getPresenceActualIn(employee.getOID(),timeIn3);
                            long difIn3 = actualTimeIn3!=null ? Math.abs(actualTimeIn3.getTime()-timeIn.getTime()) : PRESENT_NOT_EXIST;                                    
                            

                            
                            // get the minimum schedule actual dif                             
                            if(difIn < difIn2){
                                if(difIn<difIn3){
                                    // difIn minimum                                    
                                    //actualTimeIn = actualTimeIn; // no change
                                    if(difIn>MAX_PRESNT_VS_SCHDL){
                                        actualTimeIn=null;
                                    }
                                    
                                }else{
                                    // difIn3 minimum
                                    actualTimeIn = actualTimeIn3;
                                    if(difIn3>MAX_PRESNT_VS_SCHDL){
                                        actualTimeIn=null;
                                    }
                                }
                                
                            }else{
                                if(difIn2<difIn3){
                                    // difIn2  minimum
                                    actualTimeIn = actualTimeIn2;
                                    if(difIn2>MAX_PRESNT_VS_SCHDL){
                                        actualTimeIn=null;
                                    }

                                 } else {
                                    //difIn3 minimum                                    
                                    actualTimeIn = actualTimeIn3;
                                    if(difIn3>MAX_PRESNT_VS_SCHDL){
                                        actualTimeIn=null;
                                    }
                                 }
                                    
                            }
                        
                        
		}
		
		if(timeOut != null)
		{			
			int day = dt.getDate();
			if(timeOut.getHours()< timeIn.getHours())
			{									
				day = day+1;													
			}
						
			timeOut = new Date(dt.getYear(),dt.getMonth(),day,timeOut.getHours(),timeOut.getMinutes());		
			actualTimeOut = (Date)SessPresence.getPresenceActualOut(employee.getOID(),timeOut);	

                        
                            if(timeOut.getTime()<timeIn.getTime()){
                                timeOut = new Date(timeOut.getTime()+ (1000*60*60*24));                            		
                            }

                            actualTimeOut = (Date)SessPresence.getPresenceActualOut(employee.getOID(),timeOut);
                            long difOut = actualTimeOut!=null ? Math.abs(actualTimeOut.getTime()-timeOut.getTime()) : PRESENT_NOT_EXIST;                                    
                            
                            Date timeOut2 = new Date(timeOut.getTime()-24*60*60*1000);  // date sebelumnya
                            Date actualTimeOut2 = (Date)SessPresence.getPresenceActualOut(employee.getOID(),timeOut2);
                            long difOut2 = actualTimeOut2!=null ? Math.abs(actualTimeOut2.getTime()-timeOut.getTime()) : PRESENT_NOT_EXIST;                                    

                            Date timeOut3 = new Date(timeOut.getTime()+24*60*60*1000);  // date setelah time in schedule
                            Date actualTimeOut3 = (Date)SessPresence.getPresenceActualOut(employee.getOID(),timeOut3);
                            long difOut3 = actualTimeOut3!=null ? Math.abs(actualTimeOut3.getTime()-timeOut.getTime()) : PRESENT_NOT_EXIST;                                    

                            // get the minimum schedule actual dif                             
                            if(difOut < difOut2){
                                if(difOut<difOut3){
                                    // difOut minimum                                    
                                    //actualTimeOut = actualTimeOut; // no change
                                    if(difOut>MAX_PRESNT_VS_SCHDL){
                                        actualTimeOut=null;
                                    }
                                    
                                }else{
                                    // difOut3 minimum
                                    actualTimeOut = actualTimeOut3;
                                    if(difOut3>MAX_PRESNT_VS_SCHDL){
                                        actualTimeOut=null;
                                    }
                                }
                                
                            }else{
                                if(difOut2<difOut3){
                                    // difOut2  minimum
                                    actualTimeOut = actualTimeOut2;
                                    if(difOut2>MAX_PRESNT_VS_SCHDL){
                                        actualTimeOut=null;
                                    }

                                 } else {
                                    //difOut3 minimum                                    
                                    actualTimeOut = actualTimeOut3;
                                    if(difOut3>MAX_PRESNT_VS_SCHDL){
                                        actualTimeOut=null;
                                    }
                                 }                                    
                            }
                                                		
		}
		
		
		long lAcTimeIn = 0;
		long lAcTimeOut = 0;		
		long iDuration = 0;
		long iDurationHour = 0;
		long iDurationMin = 0;
		String strDurationHour = "";
		String strDurationMin = "";
		
		Vector rowx = new Vector();
		
		rowx.add(""+(i+1));
		rowx.add(employee.getFullName().toUpperCase());
		
		/* -------------- schedule -------------- */
		rowx.add(scheduleSymbol.getSymbol()); 		
		if((timeIn == null && timeOut == null)||(timeIn.equals(timeOut))){
			timeIn = null;
			timeOut = null;
		}			
		
		rowx.add(timeIn != null?Formater.formatTimeLocale(timeIn, "DD/mm/yyyy HH:mm"):"");		
		rowx.add(timeOut != null?Formater.formatTimeLocale(timeOut, "DD/mm/yyyy HH:mm"):"");
				
		
		/* -------------- actual Time In -------------- */
		String formTime = "";						
		if (actualTimeIn != null) {			
			formTime = Formater.formatTimeLocale(actualTimeIn, "DD/mm/yyyy HH:mm");
			actualTimeIn.setSeconds(0);
			lAcTimeIn = actualTimeIn.getTime();								
		}else {
			formTime = "";			
		}
		rowx.add(formTime);
		presence.add(formTime);
				
		/* -------------- Duration time in and time out -------------- */	
		if(actualTimeOut != null){
			actualTimeOut.setSeconds(0);			
			lAcTimeOut = actualTimeOut.getTime();
		}
		if(actualTimeIn != null && actualTimeOut != null){			
			iDuration = lAcTimeOut/60000 - lAcTimeIn/60000;
			iDurationHour = (iDuration - (iDuration % 60)) / 60;
			iDurationMin = iDuration % 60;
			strDurationHour = iDurationHour + "h, ";
			strDurationMin = iDurationMin + "m ";
			formTime = strDurationHour + strDurationMin;
		}else{
			formTime = "";
		}
		rowx.add(formTime);
		presence.add(formTime);
				
		/* -------------- actual time out -------------- */		
		if (actualTimeOut != null) {
			formTime = Formater.formatTimeLocale(actualTimeOut, "DD/mm/yyyy HH:mm");
		}else {
			formTime = "";
		}
		rowx.add(formTime);
		presence.add(formTime);
		
		/* -------------- difference beetwen time in and  actual time in -------------- */
		if(timeIn != null && actualTimeIn != null){
			timeIn.setSeconds(0);			
			long lTimeIn = timeIn.getTime();													
			iDuration = lTimeIn/60000 - lAcTimeIn/60000;
                        
                        if(iDuration<(24*60*60*1000/60000)){                        
                        
			iDurationHour = (iDuration - (iDuration % 60)) / 60;
			iDurationMin = iDuration % 60;
			strDurationHour = iDurationHour + "h, ";
			strDurationMin = iDurationMin + "m ";				
			formTime = strDurationHour + strDurationMin;
                        }
                        else{
                            formTime = "";
                        }
		}else{
			formTime = "";
		}
		rowx.add(formTime);
		presence.add(formTime);
		
		/* -------------- difference beetwen time out and  actual time out -------------- */
		if(timeOut != null && actualTimeOut != null){
			timeOut.setSeconds(0);
			long lTimeOut = timeOut.getTime();		
			iDuration = lAcTimeOut/60000 - lTimeOut/60000 ;
			iDurationHour = (iDuration - (iDuration % 60)) / 60;
			iDurationMin = iDuration % 60;
			strDurationHour = iDurationHour + "h, ";
			strDurationMin = iDurationMin + "m ";
			formTime = strDurationHour + strDurationMin;
		}else{
			formTime = "";
		}
		rowx.add(formTime);
		presence.add(formTime);
		
		temp.add(presence);		
		list.add(temp);
		lstData.add(rowx);
	}
	
	result.add(ctrlist.drawList());
	result.add(list);	

	return result;
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
Date date = FRMQueryString.requestDate(request,"date");
Vector listEmployee = new Vector(1,1);
if(iCommand == Command.LIST)
	listEmployee = PstEmployee.list(0,0,PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+" = "+oidDepartment+ " AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = "+PstEmployee.NO_RESIGN,
			PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
	

Vector listPresence = drawList(listEmployee,date);
String drawList = "";
Vector vctDailyPresence = new Vector(1,1); 
vctDailyPresence.add(date);
vctDailyPresence.add(""+oidDepartment);
if(listPresence != null && listPresence.size()==2){
	drawList = (String)listPresence.get(0);
	Vector list = (Vector)listPresence.get(1);
	vctDailyPresence.add(list);
}

if(session.getValue("DAILY_PRESENCE")!=null){
	session.removeValue("DAILY_PRESENCE");
}
session.putValue("DAILY_PRESENCE",vctDailyPresence);
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="daily_report.jsp";
	document.frpresence.submit();
}

function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.staffcontrol.DailyPresencePdf"; 
	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
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
</SCRIPT>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                  &gt; Daily Presence <!-- #EndEditable --> </strong></font> </td>
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
									<input type="hidden" name="command" value="<%=iCommand%>">
									  <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="88%"> : 
                                            <%
                                          Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");										  
										  Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);										  
										  for(int d=0;d<listDepartment.size();d++){
										  	Department department = (Department)listDepartment.get(d);
											deptValue.add(""+department.getOID());
											deptKey.add(department.getDepartment());
										  }
										  %>
                                            <%=ControlCombo.draw("department",null,""+oidDepartment,deptValue,deptKey)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Date </div>
                                          </td>
                                          <td width="88%">: <%=ControlDate.drawDate("date",date==null || iCommand == Command.NONE?new Date():date,"formElemen",1,-2)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="88%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="137">
                                              <tr> 
                                                <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="94" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Presence</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
									  <% if(iCommand == Command.LIST){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									  <% if(listEmployee != null && listEmployee.size()>0){%>
									    <tr><td><hr></td></tr>
                                        <tr>
											<td><%=drawList%></td>
										  </tr>
										  <%if(privPrint){%>
										  <tr>
											<td>
											  <table width="18%" border="0" cellspacing="1" cellpadding="1">
												<tr>
												  <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
													Daily Presence</a></b> 
												  </td>
												</tr>
											  </table>
											 </td>
										  </tr>
										  <%}%>										  
									    <%}else{%>
										<tr>
											<td>&nbsp;</td>
										  </tr>
										  <tr>
											<td>&nbsp;</td>
										  </tr>
										  <%}%>
										</table>
										<%}%>
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
                      <td>&nbsp;</td>
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
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" -->
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
