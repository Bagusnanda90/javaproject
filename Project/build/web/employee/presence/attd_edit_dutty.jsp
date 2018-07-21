<%@page import="com.dimata.harisma.utility.machine.SaverData"%>
<%@page import="com.dimata.harisma.utility.service.presence.PresenceAnalyser"%>
<% 
/* 
 * Page Name  		:  presence_list.jsp
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
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PRESENCE, AppObjInfo.OBJ_MANUAL_PRESENCE); %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_PRESENCE   	); %>
<%@ include file = "../../main/checkuser.jsp" %>
<% 
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<!-- Jsp Block -->
<%!
public String drawList(Vector objectClass )
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Payroll","5%");
	ctrlist.addHeader("Employee Name","15%");
	ctrlist.addHeader("Presence Datetime","10%");
	ctrlist.addHeader("Status","10%");
        ctrlist.addHeader("Analyzed","5%");
        ctrlist.addHeader("Approved","5%");

	ctrlist.setLinkRow(-1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	for (int i = 0; i < objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
		Presence presence = (Presence) temp.get(0);
		Employee employee = (Employee) temp.get(1);
		Vector rowx = new Vector();
		rowx.add(String.valueOf(employee.getEmployeeNum()));
		rowx.add(String.valueOf(employee.getFullName()));
		String str_dt_PresenceDatetime = ""; 
		
		try
		{
			Date dt_PresenceDatetime = presence.getPresenceDatetime();
                        
			if(dt_PresenceDatetime==null)
			{
				dt_PresenceDatetime = new Date();
			}
			
			//str_dt_PresenceDatetime = Formater.formatDate(dt_PresenceDatetime, "dd MMMM yyyy - hh mm");
			//str_dt_PresenceDatetime =  Formater.formatDate(dt_PresenceDatetime, "dd MMMM yyyy - ") + Formater.formatTimeLocale(dt_PresenceDatetime);
			str_dt_PresenceDatetime =  Formater.formatDate(dt_PresenceDatetime, "dd MMMM yyyy - HH:mm:ss");
		} 
		catch(Exception e) 
		{ 
			str_dt_PresenceDatetime = ""; 
		}
		rowx.add(""+str_dt_PresenceDatetime);

		/*String status = "";
		switch (presence.getStatus()) 
		{
			case 0 :
				status = "In";
				break;
			case 1 :
				status = "Out - Home";
				break;
			case 2 :
				status = "Out - On duty";
				break;
			case 3 :
				status = "In - Lunch";
				break;
			case 4 :
				status = "In - Break";
				break;
			case 5 :
				status = "In - Callback";
				break;
			default :
				break;
		} */
                Vector status_value = Presence.getStatusIndexString();
                Vector status_key = Presence.getStatusAttString();                     
		rowx.add(ControlCombo.draw(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS]+"_"+presence.getOID(),"formElemen",null,
                        String.valueOf(presence.getStatus()), status_value, status_key, "onchange=\"javascript:cmdUpdateDataFlag()\""));
                rowx.add(Presence.ANALYZED_STATUS[presence.getAnalyzed()]);
                rowx.add(""+((presence.getAnalyzed()!= Presence.ANALYZED_OK)? "<input type=\"checkbox\" onclick=\"javascript:cmdUpdateDataFlag()\" value=\"1\" name=\""+
                        FrmPresence.fieldNames[FrmPresence.FRM_FIELD_ANALYZED]+"_"+ presence.getOID()+"\">":(""+
                        Presence.ANALYZED_STATUS[Presence.ANALYZED_OK])));
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(presence.getOID()));
	}
	return ctrlist.draw();
}
%>
<%

ControlLine ctrLine = new ControlLine();
CtrlPresence ctrlPresence = new CtrlPresence(request);
long oidPresence = FRMQueryString.requestLong(request, "presence_id");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
//update by satrya 2012-11-21
long oidEmployee = FRMQueryString.requestLong(request, "employeeId");
long lDateSelected = FRMQueryString.requestLong(request, "requestDateDaily");

int iErrCode = FRMMessage.ERR_NONE;
String msgStr = "";

int iCommand = FRMQueryString.requestCommand(request);
//int iCommandApproval = FRMQueryString.requestInt(request, "commandapproval");
int start = FRMQueryString.requestInt(request, "start");
int recordToGet = 15;
int vectSize = 0;
String whereClause = "";
String orderClause = "";
Employee employee = new Employee();
ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
Position position = new Position();
Department department = new Department();
com.dimata.harisma.entity.masterdata.Section section = new com.dimata.harisma.entity.masterdata.Section(); 
SrcPresence srcPresence = new SrcPresence(); 
 boolean submitIsOk = false;
FrmSrcPresence frmSrcPresence = new FrmSrcPresence(request,srcPresence);
frmSrcPresence.requestEntityObject(srcPresence); 
//update by satrya 2012-11-21
if(oidEmployee !=0 && lDateSelected!=0){
  try{
    employee = PstEmployee.fetchExc(oidEmployee);
    position = PstPosition.fetchExc(employee.getPositionId());
    department = PstDepartment.fetchExc(employee.getDepartmentId());
     scheduleSymbol = PstEmpSchedule.getDailySchedule(new Date(lDateSelected), oidEmployee);
     srcPresence.setEmpId(oidEmployee);
    srcPresence.setEmpnumber(employee.getEmployeeNum());
    srcPresence.setFullname(employee.getFullName());
    srcPresence.setDepartment(""+employee.getDepartmentId()); 
    srcPresence.setSection(""+employee.getSectionId());
    srcPresence.setPosition(""+employee.getPositionId());
    

    srcPresence.setPositionName(position.getPosition()); 
    srcPresence.setsCommarcingDate(Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy") ); 
    srcPresence.setDepartmentName(department.getDepartment());
    srcPresence.setPresenceId(oidPresence);
    srcPresence.setFlagsts(true);
    
    Date datefrom = new Date((lDateSelected - (1000 * 60 * 60 * 24)));
    datefrom.setHours(0);
    datefrom.setMinutes(0);
    datefrom.setSeconds(0);
    Date dateto = new Date((lDateSelected + 1000 * 60 * 60 * 24));
    dateto.setHours(23);
    dateto.setMinutes(59);
    dateto.setSeconds(59);
    srcPresence.setDatefrom(datefrom);
    srcPresence.setDateto(dateto);
    Vector status= new Vector(1,1);
    status.add(Presence.STATUS_IN);
    status.add(Presence.STATUS_OUT); 
    status.add(Presence.STATUS_OUT_PERSONAL);
    status.add(Presence.STATUS_IN_PERSONAL);
    srcPresence.setStatusCheck(status);
    }catch(Exception ex){
        System.out.println("Exception att_edit_dutty: " +ex);
    }     
}

if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand == Command.BACK)|| iCommand == Command.EDIT || iCommand== Command.APPROVE)
//if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand == Command.BACK) || iCommandApproval== Command.APPROVE || iCommand == Command.EDIT)
{
	try
	{	 
		srcPresence = (SrcPresence)session.getValue(SessPresence.SESS_SRC_PRESENCE); 
		if (srcPresence == null) 
		{
			srcPresence = new SrcPresence();
		}
               
	}
	catch(Exception e){
		srcPresence = new SrcPresence();
                 
	}
      
}

SessPresence sessPresence = new SessPresence();
session.putValue(SessPresence.SESS_SRC_PRESENCE, srcPresence);

if( iCommand==Command.SAVE && prevCommand==Command.ADD )
{
	//start = PstPresence.findLimitStart(oidPresence, recordToGet, whereClause, orderClause);
	vectSize = PstPresence.getCount(whereClause);
}
else
{
	vectSize = sessPresence.getCountSearch(srcPresence);
}


if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST) ||(iCommand==Command.APPROVE))
//if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST))
{
	start = ctrlPresence.actionList(iCommand, start, vectSize, recordToGet);
}

Vector records = sessPresence.searchPresence(srcPresence, start, recordToGet);
if ( records.size()<1 && start>0 )
{
	 if (vectSize - recordToGet > recordToGet)
	 {
		start = start - recordToGet;   
	 }		
	 else
	 {
		 start = 0 ;
	 } 
	 records = sessPresence.searchPresence(srcPresence, start, recordToGet);             
}
         if(iCommand==Command.APPROVE && records !=null && records.size()>0){
             //   if(iCommandApproval==Command.APPROVE && records !=null && records.size()>0){
             for (int idx=0; idx< records.size(); idx++){
                 try{
		Vector temp = (Vector) records.get(idx);
		Presence presenceX = (Presence) temp.get(0);
                 int approve = FRMQueryString.requestInt(request, FrmPresence.fieldNames[FrmPresence.FRM_FIELD_ANALYZED]+"_"+ presenceX.getOID());                              
                    if (approve==1){
                        int newStatus = FRMQueryString.requestInt(request, FrmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS]+"_"+ presenceX.getOID());
                        
                       presenceX.setStatus(newStatus);
                        presenceX.setAnalyzed(Presence.ANALYZED_OK);
                        PstPresence.updateExc(presenceX);
                        //long oidDepartement = PstDepartment.getOidDept(presenceX.getEmpNumb(), "", 0, 0, presenceX.getEmployeeId());
                        //SaverData.saveManualPresence(presenceX, oidDepartement);
                        long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceX.getPresenceDatetime());
                         int updatedFieldIndex = -1;
                        long updatePeriodId = periodId;
                        Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, presenceX.getEmployeeId(), presenceX.getStatus(), presenceX.getPresenceDatetime());                        
                        
                        if(vectFieldIndex!=null && vectFieldIndex.size()==2) 
                        {
                            updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                            //System.out.println("... Update field : " + PstEmpSchedule.fieldNames[updatedFieldIndex] + " on period " + updatePeriodId);
                        }                        
                                                
                        int updateStatus = 0;
                        try  
                        {
                            updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presenceX.getEmployeeId(), updatedFieldIndex, presenceX.getPresenceDatetime());
                            submitIsOk = true;
                            if(updateStatus>0)  
                            {
                                presenceX.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            }
                        }
                        catch(Exception e) 
                        {
                            System.out.println("Update Presence exc : "+e.toString());
                             submitIsOk = false;
                        }
                        
                       //update by satrya 2012-10-17
                       PresenceAnalyser.analyzePresencePerEmployeeByEmployeeId(presenceX.getPresenceDatetime(), presenceX.getEmployeeId()); 
                    }
                  }catch(Exception exc){  
                      System.out.println(""+exc);
                  }
             }
             iCommand=Command.LIST;
         }
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Presence</title>
<script language="JavaScript">
     function cmdExit(){
     window.close();
     } 
   
     <% if(submitIsOk) {%>
        self.opener.cmdRefresh();
        //update by satrya 2013-01-18
         window.close();//in IE
        // if(confirm("Data are  changed!, click Ok to close window")  ){
		//document.frm_presence.command.value="<%//=Command.LAST%>";
               // document.frm_presence.action= window.close();
		//document.frm_presence.submit();
                
	  // }
    <%}%>
   
   var dataUpdate =0;
   
        function cmdUpdateDataFlag(){
            dataUpdate=1;
        }    
        
	function cmdAdd(){                
            //update by satrya 2012-08-01
                document.frm_presence.presence_id.value = 0;
		document.frm_presence.command.value="<%=Command.ADD%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	}

	function cmdEdit(oid){
		document.frm_presence.command.value="<%=Command.EDIT%>";
                document.frm_presence.presence_id.value = oid;
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
	}

	function cmdValidate(oid){
		document.frm_presence.command.value="<%=Command.CONFIRM %>";
                document.frm_presence.presence_id.value = oid;
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
	}


	function cmdApprove(){
                document.frm_presence.command.value="<%=Command.APPROVE%>";
                //document.frm_presence.commandapproval.value="<//%=Command.APPROVE%>";                                
                //document.frm_presence.command.value="<//%=Command.NEXT %>";                
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
	}

	function cmdListFirst(){  
              //update by satrya 2012-08-01
          if(dataUpdate !=0){
           if(confirm("Data are  changed!, click Ok to continue or cancel to approve data")  ){
		document.frm_presence.command.value="<%=Command.FIRST%>";
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
	   }
           } else{
                document.frm_presence.command.value="<%=Command.FIRST%>";
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
           }
		
	}

	function cmdListPrev(){
             if(dataUpdate !=0){
           if(confirm("Data are  changed!, click Ok to continue or cancel to approve data")  ){
		document.frm_presence.command.value="<%=Command.PREV%>";
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
	   }
           } else{
                document.frm_presence.command.value="<%=Command.PREV%>";
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
           }
		
	}

	function cmdListNext(){
            //update by satrya 2012-08-01
          if(dataUpdate !=0){
           if(confirm("Data are  changed!, click Ok to continue or cancel to approve data")  ){
		document.frm_presence.command.value="<%=Command.NEXT%>";
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
	   }
           } else{
               document.frm_presence.command.value="<%=Command.NEXT%>";
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
           }
                    
      }

	function cmdListLast(){
         //update by satrya 2012-08-01
          if(dataUpdate !=0){
           if(confirm("Data are  changed!, click Ok to continue or cancel to approve data")  ){
		document.frm_presence.command.value="<%=Command.LAST%>";
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
	   }
           } else{
            document.frm_presence.command.value="<%=Command.LAST%>";
		document.frm_presence.action="attd_edit_dutty.jsp";
		document.frm_presence.submit();
           }
		
	}

	function cmdBack(){
		document.frm_presence.command.value="<%=Command.BACK%>";
		document.frm_presence.action="srcpresence.jsp";
		document.frm_presence.submit();
	}
	function fnTrapKD(){
		//alert(event.keyCode);
		switch(event.keyCode) {
			case <%=LIST_PREV%>:
				cmdListPrev();
				break;
			case <%=LIST_NEXT%>:
				cmdListNext();
				break;
			case <%=LIST_FIRST%>:
				cmdListFirst();
				break;
			case <%=LIST_LAST%>:
				cmdListLast();
				break;
			default:
				break;
		}
	}
       

</script>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --><!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --><!-- #BeginEditable "headerscript" -->
<SCRIPT language=JavaScript>
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
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"><!-- #BeginEditable "header" -->
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr>
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"><!-- #BeginEditable "menumain" -->
      <%@ include file = "../../main/mnmain.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr>
    <td  bgcolor="#9BC1FF" height="10" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
        </tr>
      </table></td>
  </tr>
  <%}%>
  <tr>
    <td width="88%" valign="top" align="left"><table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr>
          <td width="100%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20"><font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> Employee &gt; Attendance &gt; Manual Presence<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="tablecolor" style="background-color:<%=bgColorContent%>; ">
                          <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr>
                            <td style="border:1px solid <%=garisContent%>" valign="top"><table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr>
                                  <td valign="top"><!-- #BeginEditable "content" -->
                                    <form name="frm_presence" method="post" action="">
                                      <!--input type="hidden" name="command" value=""-->
                                      <input type="hidden" name="commandapproval" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="presence_id" value="<%=oidPresence%>">
                                      <!-- update by satrya 2012-07-16-->
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table border="0" width="100%">
                                        <tr>
                                          <td><div align="center"><b><font size="3">SET STATUS EMPLOYEE OF DUTY</font></b></div></td>
                                        </tr>
                                        <tr>
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                          <td><table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr>
                                                <!--<input type="hidden" name="<//%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]%>" value="<%//=String.valueOf(presence.getEmployeeId())%>" class="formElemen">-->
                                                <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%>
                                                <td width="10%" nowrap><div align="left">Name</div></td>
                                                <td width="40%" nowrap>: <%= employee.getFullName() %> </td>
                                                <td width="13%" nowrap><div align="left">Employee 
                                                    Number</div></td>
                                                <td width="37%"> : <%= employee.getEmployeeNum() %> </td>
                                              </tr>
                                              <tr>
                                                <td width="10%" nowrap><div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div></td>
                                                <td width="40%" nowrap>: <%= position.getPosition() %> </td>
                                                <td width="13%" nowrap><div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                                <td width="37%"> : <%= department.getDepartment() %> </td>
                                              </tr>
                                              <tr>
                                                <td width="10%" nowrap><div align="left">Presence Date Time</div></td>
                                                <td width="40%"> : <%= Formater.formatTimeLocale(new Date(lDateSelected), "EEEE, dd MMMM yyyy")%> </td>
                                                <td width="10%" nowrap><div align="left">Schedule Type</div></td>
                                                <td width="40%"> : <%= scheduleSymbol.getSymbol() !=null && scheduleSymbol.getSymbol().length() > 0 ? scheduleSymbol.getSymbol() +" &nbsp; "+"["+scheduleSymbol.getSchedule()+"]" : "-"%> </td>
                                              </tr>
                                            </table>
											</td>
                                        </tr>
										<tr>
										<td><hr></td>
										</tr>
                                        <tr>
                                          <td height="8" width="100%" class="listtitle">Presence 
                                            List</td>
                                        </tr>
                                      </table>
                                      <%if((records!=null)&&(records.size()>0)){%>
                                      <%=drawList(records)%>
                                      <%}
					else{
					%>
                                      <span class="comment"><br>
                                      &nbsp;Records is empty ...</span>
                                      <%}%>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td><table width="100%" cellspacing="0" cellpadding="3">
                                              <tr>
                                                <td><% ctrLine.setLocationImg(approot+"/images");
						   ctrLine.initDefault();
                                                   out.println(ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet));
												
												//int lstCommand = iCommand==Command.BACK ? Command.LIST : iCommand;
						%>
                                                  <%//=ctrLine.drawImageListLimit(lstCommand,vectSize,start,recordToGet)%>
                                                </td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <tr>
                                          <td width="46%">&nbsp;</td>
                                        </tr>
                                        <tr>
                                          <td width="46%" nowrap align="left" class="command"><!-- &nbsp; <a href="javascript:cmdAdd()">Add New</a> | <a href="javascript:cmdBack()">Back to search</a> -->
                                            <table width="60" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                             
                                                <%if(privUpdate){%>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdApprove()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save Approval"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="169" nowrap><b><a href="javascript:cmdApprove()" class="command">Save Approval</a></b></td>
                                                <%}%>
                                                 <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdExit()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="150" nowrap><a href="javascript:cmdExit()" class="command">Exit</a></td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                      </table>
                                    </form>
                                    <!-- #EndEditable --> </td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
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
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<sript language="Javascript" >                                
<% //if(submitIsOk) {%>
     <!--self.closed();
     window.close();-->
               
<%//}%>
</sript>
<!-- #EndEditable --><!-- #EndTemplate -->
</html>
