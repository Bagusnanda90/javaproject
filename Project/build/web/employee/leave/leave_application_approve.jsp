<%-- 
    Document   : leave_app_to_be_app
    Created on : Mar 8, 2013, 7:58:33 AM
    Author     : Satrya Ramayu
--%>
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
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false; 
%>
<%!

public String drawList(Vector objectClass,boolean isHRDLogin,int isAdminExecuteLeave,long empId,Hashtable hashViewPeriod) 
{
	ControlList ctrlist = new ControlList();
	
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.PAYROLL)+"</center>","5%");	
	ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.EMPLOYEE)+"</center>","10%");
        ctrlist.addHeader("<center>"+dictionaryD.getWord(I_Dictionary.DEPARTMENT)+"</center>","10%");
       
	ctrlist.addHeader("<center>Date Of Application</center>","10%");		
        ctrlist.addHeader("<center>Doc Status</center>","8%");
	ctrlist.addHeader("<center>AL start date</center>","6%");	
        ctrlist.addHeader("<center>AL end date</center>","6%");
        ctrlist.addHeader("<center>LL start date","6%");
        ctrlist.addHeader("<center>LL end date</center>","6%");
        ctrlist.addHeader("<center>DP start date</center>","6%");
        ctrlist.addHeader("<center>DP end date</center>","6%");
        ctrlist.addHeader("<center>SP start date</center>","6%");
        ctrlist.addHeader("<center>sp end date</center>","6%");
        ctrlist.addHeader("<center>Approve By</center>","6%");
        ctrlist.addHeader("<a href=\"Javascript:SetAllCheckBoxes('frm_leave_application', true)\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_leave_application', false)\">Deselect</a> ","15%");
	
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	for (int i=0; i<objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
                
                Employee employee = (Employee) temp.get(0);
                LeaveApplication objleaveApplication = (LeaveApplication) temp.get(1);
                objleaveApplication.setEmployeeId(employee.getOID());
                //update by satrya 2014-04-15 objleaveApplication.setEmployeeId(empId);
                Department department = (Department) temp.get(2);
                //ViewLeaveAppPeriod viewLeaveAppPeriod = (ViewLeaveAppPeriod) temp.get(3);
		
		String strSubmissionDate = ""; 
		try
		{
			Date dt_SubmitDate = objleaveApplication.getSubmissionDate();
			if(dt_SubmitDate==null)
			{
				dt_SubmitDate = new Date();
			}
			strSubmissionDate = Formater.formatDate(dt_SubmitDate, "MMM dd, yyyy");
		}
		catch(Exception e)
		{ 
			strSubmissionDate = ""; 
		}
                
		//String strApproval = "";               
				
                Vector rowx = new Vector();
                
                rowx.add("<input type=\"hidden\" name=\"app_id\" value=\""+objleaveApplication.getOID()+ "\">"+"<a href=\"javascript:cmdEdit('"+objleaveApplication.getOID()+"','" + employee.getOID() + "')\">"+employee.getEmployeeNum()+"</a>");		
                		
		rowx.add(employee.getFullName()); 
                rowx.add(department.getDepartment());
                //String typeleave = SessLeaveApplication.typeLeave(objleaveApplication.getOID());                    
                
		rowx.add(strSubmissionDate);	
                
                String statusDoc = SessLeaveApplication.getStatusDocument(objleaveApplication.getDocStatus());
                
                if(statusDoc != null){
                    rowx.add(""+statusDoc);	
                }else{
                    rowx.add("");	
                }
                
                String al_start="";
                String al_end = "";
                String ll_start = "";
                String ll_end = "";
                String dp_start = "";
                String dp_end = "";
                String sp_start = "";
                String sp_end = "";
                SessLeaveApplicationViewPeriod sessLeaveApplicationViewPeriod = new SessLeaveApplicationViewPeriod();
                if(objleaveApplication!=null && objleaveApplication.getOID()!=0 && hashViewPeriod!=null && hashViewPeriod.size()>0 && hashViewPeriod.containsKey(""+objleaveApplication.getOID())){
                    sessLeaveApplicationViewPeriod = (SessLeaveApplicationViewPeriod)hashViewPeriod.get(""+objleaveApplication.getOID());
                } 
                // update by satrya 2014-04-08 if(viewLeaveAppPeriod.getAl_start_date() != null){
                if(sessLeaveApplicationViewPeriod.getAlStartDate() != null){
                    //update by satrya 2014-04-08 al_start = Formater.formatDate(viewLeaveAppPeriod.getAl_start_date(),"yyyy-MM-dd");
                    al_start = Formater.formatDate(sessLeaveApplicationViewPeriod.getAlStartDate(),"yyyy-MM-dd HH:mm");
                }
                
                 // update by satrya 2014-04-08  if(viewLeaveAppPeriod.getAl_end_date() != null){
                if(sessLeaveApplicationViewPeriod.getAlEndDate() != null){
                    //al_end = Formater.formatDate(viewLeaveAppPeriod.getAl_end_date(),"yyyy-MM-dd");
                    al_end = Formater.formatDate(sessLeaveApplicationViewPeriod.getAlEndDate(),"yyyy-MM-dd HH:mm");
                }
                
                //if(viewLeaveAppPeriod.getLl_start_date() != null){
                if(sessLeaveApplicationViewPeriod.getLlStartDate()  != null){
                    //ll_start = Formater.formatDate(viewLeaveAppPeriod.getLl_start_date(),"yyyy-MM-dd");
                    ll_start = Formater.formatDate(sessLeaveApplicationViewPeriod.getLlStartDate(),"yyyy-MM-dd HH:mm");
                }
                
                //if(viewLeaveAppPeriod.getLl_end_date() != null){
                if(sessLeaveApplicationViewPeriod.getLlEndDate() != null){
                    ll_end = Formater.formatDate(sessLeaveApplicationViewPeriod.getLlEndDate(),"yyyy-MM-dd HH:mm");
                    //ll_end = Formater.formatDate(viewLeaveAppPeriod.getLl_end_date(),"yyyy-MM-dd");
                }
                
                //if(viewLeaveAppPeriod.getDp_start_date() != null){
                if(sessLeaveApplicationViewPeriod.getDpStartDate() != null){
                    //dp_start = Formater.formatDate(viewLeaveAppPeriod.getDp_start_date(),"yyyy-MM-dd");
                    dp_start = Formater.formatDate(sessLeaveApplicationViewPeriod.getDpStartDate(),"yyyy-MM-dd HH:mm");
                }
                
                //if(viewLeaveAppPeriod.getDp_end_date() != null){
                if(sessLeaveApplicationViewPeriod.getDpEndDate() != null){
                    dp_end = Formater.formatDate(sessLeaveApplicationViewPeriod.getDpEndDate(),"yyyy-MM-dd HH:mm");
                }
                
                if(sessLeaveApplicationViewPeriod.getSpStartDate() != null){ 
                //if(viewLeaveAppPeriod.getSp_start_date() != null){ 
                    sp_start = Formater.formatDate(sessLeaveApplicationViewPeriod.getSpStartDate(),"yyyy-MM-dd HH:mm");
                }
                
                //if(viewLeaveAppPeriod.getSp_end_date() != null){
                if(sessLeaveApplicationViewPeriod.getSpEndDate() != null){
                    sp_end = Formater.formatDate(sessLeaveApplicationViewPeriod.getSpEndDate(),"yyyy-MM-dd HH:mm");
                }
                
               rowx.add(""+al_start);		
               rowx.add(""+al_end);
               rowx.add(""+ll_start);
               rowx.add(""+ll_end);
               rowx.add(""+dp_start);
               rowx.add(""+dp_end);
               rowx.add(""+sp_start);
               rowx.add(""+sp_end);
               String depHead = SessLeaveApplication.getEmployeeApp(objleaveApplication.getDepHeadApproval());
               rowx.add(depHead==null?"":depHead);
               boolean mustApp = false;
            
            try{
                mustApp = SessLeaveApplication.getMustApprove(objleaveApplication, empId);
            }catch(Exception E){
                System.out.println("Exception "+E.toString());
            }
               if((isHRDLogin || isAdminExecuteLeave !=0) && mustApp && depHead!=null){
                    rowx.add("<input type=\"hidden\" name=\"execute"+i+"\"><center><input type=\"checkbox\" name=\"executed"+i+"\" value=\"1\" ></center>");
               }else{
                   rowx.add("<input type=\"hidden\" name=\"execute"+i+"\"><center><input type=\"checkbox\" disabled name=\"executed"+i+"\" value=\"0\" ></center>");
                    //rowx.add("<center><input type=\"checkbox\" disabled=\"true\" value=\"0\" ></center>");
               }
	       lstData.add(rowx);
	}
	return ctrlist.draw();
}
%>


<%
ControlLine ctrLine = new ControlLine();
Control ctrlLeave = new Control();
long oidLeave = FRMQueryString.requestLong(request, "hidden_leave_application_id");
//update by satrya 2012-07-25
String source = FRMQueryString.requestString(request, "source");

//end
int iErrCode = FRMMessage.ERR_NONE;
String msgStr = "";
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");

boolean aksesApproval = false;

Employee objEmployee = new Employee();
Position position = new Position();
Section section = new Section();

try {
   if(emplx.getOID()!=0){
    objEmployee = PstEmployee.fetchExc(emplx.getOID());
   }
} catch (Exception e) {
    System.out.println("EXCEPTION " + e.toString());
}

try {
  if(objEmployee.getPositionId()!=0){
    position = PstPosition.fetchExc(objEmployee.getPositionId());
  }
} catch (Exception e) {
    System.out.println("EXCEPTION " + e.toString());
}

try {
  if(objEmployee.getSectionId()!=0){
    section = PstSection.fetchExc(objEmployee.getSectionId());
  }
} catch (Exception e) {
    System.out.println("EXCEPTION " + e.toString());
}


if (position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE || position.getDisabledAppUnderSupervisor() == PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE || 
        position.getDisabedAppDivisionScope() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE){

    aksesApproval = true;

}

//update by satrya 2012-07-25
if(iCommand == Command.APPROVE || (source != null && "presence".equals(source) && iCommand == Command.EDIT)){

    
    String[] leave_application_id = null;
    leave_application_id = request.getParameterValues("app_id");
    
    if(leave_application_id != null && leave_application_id.length > 0){        
    
        boolean[] is_process = null;	
        String[] data_executed = null;		
        //String[] emp_approve   = null;
        data_executed = request.getParameterValues("execute");
        is_process = new boolean[leave_application_id.length];
    
        Vector leaveAppIdProces = new Vector();
       // boolean status_excecution;
    
        for(int i=0; i<leave_application_id.length; i++){
            int ix = FRMQueryString.requestInt(request, "executed"+i);
          //  emp_approve[i] = FRMQueryString.requestString(request,"employee_approval_"+i); 
            if(ix==1){
                long appid = 0;
               // long employeeId=0;
                try{
                    appid = Long.parseLong(leave_application_id[i]);
            //          employeeId  = Long.parseLong(emp_approve[i]);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }    
                //status_excecution = SessLeaveApplication.processSetStatusToBeApp(appid);
                 LeaveApplication objleaveApplication = new LeaveApplication();
                 objleaveApplication.setOID(appid); 
                leaveAppIdProces.add(objleaveApplication);
            }    
        }        
        
             SessLeaveApplication.approvalandSetStatusApproveCheckBox(objEmployee.getOID(),leaveAppIdProces);
    }
}

int recordToGet = 700;
int vectSize = 0;
String whereClause = "";

SrcLeaveApp srcLeaveApp = new SrcLeaveApp();
FrmSrcLeaveApp frmSrcLeaveApp = new FrmSrcLeaveApp(request, srcLeaveApp);
frmSrcLeaveApp.requestEntityObject(srcLeaveApp);

if((iCommand==Command.APPROVE) ||(iCommand==Command.NEXT) || (iCommand==Command.FIRST) || (iCommand==Command.PREV) || (iCommand==Command.LAST)|| (iCommand==Command.BACK))
    //((iCommand==Command.NEXT) || (iCommand==Command.FIRST) || (iCommand==Command.PREV) || (iCommand==Command.LAST))
{
	try
	{ 
		srcLeaveApp = (SrcLeaveApp)session.getValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION); 
		if (srcLeaveApp == null) 
		{
			srcLeaveApp = new SrcLeaveApp();
		}
	}
	catch(Exception e)
	{ 
		srcLeaveApp = new SrcLeaveApp();
	}
}

SessLeaveApplication sessLeaveApplication = new SessLeaveApplication();
session.putValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION, srcLeaveApp);

//vectSize = sessLeaveApplication.countSearchLeaveApplicationStatusApprove(srcLeaveApp);
vectSize = sessLeaveApplication.countSearchLeaveApplicationDocStatus(srcLeaveApp,PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE);
if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.APPROVE))
{
	start = ctrlLeave.actionList(iCommand, start, vectSize, recordToGet);
}
//update by satrya 2014-06-10
Hashtable hashViewPeriod = sessLeaveApplication.viewLeaveAppPeriod(srcLeaveApp, start, recordToGet,PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE);
Vector records = sessLeaveApplication.searchLeaveApplicationDocumentStatus(srcLeaveApp, start, recordToGet,PstLeaveApplication.FLD_STATUS_APPlICATION_TO_BE_APPROVE);
 //update by satrya 2012-11-1
                /**
                    untuk pengaturan apakah departement itu dpt exsekusi leave
                **/
                        int isAdminExecuteLeave = 0;//hanya cuti full day jika fullDayLeave = 0
                        try{
                            isAdminExecuteLeave = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_EXCECUTE_BY_ADMIN"));
                        }catch(Exception ex){
                           System.out.println("Execption LEAVE_EXCECUTE_BY_ADMIN " + ex);
                            isAdminExecuteLeave =0;
                            
                        }
%>

<html>
<head>
<title>HARISMA - Leave Application To Be App</title>
<script language="JavaScript">

function cmdProccess() {
    document.frm_leave_application.command.value = "<%=String.valueOf(Command.APPROVE)%>";
    document.frm_leave_application.action = "leave_application_approve.jsp";
    document.frm_leave_application.submit();
}    
    
function cmdAdd()
{
	document.frm_leave_application.command.value="<%=Command.ADD%>";
	document.frm_leave_application.action="leave_app_edit.jsp";
	document.frm_leave_application.submit();
}

function cmdEdit(oidLeave, oidEmployee)
{
	document.frm_leave_application.command.value="<%=Command.EDIT%>";
        document.frm_leave_application.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
	document.frm_leave_application.oid_employee.value = oidEmployee;
	document.frm_leave_application.action="leave_app_edit.jsp";
	document.frm_leave_application.submit();
}

function cmdListFirst()
{
	document.frm_leave_application.command.value="<%=Command.FIRST%>";
	document.frm_leave_application.action="leave_application_approve.jsp";
	document.frm_leave_application.submit();
}

function cmdListPrev()
{
	document.frm_leave_application.command.value="<%=Command.PREV%>";
	document.frm_leave_application.action="leave_application_approve.jsp";
	document.frm_leave_application.submit();
}

function cmdListNext()  
{
	document.frm_leave_application.command.value="<%=Command.NEXT%>";
	document.frm_leave_application.action="leave_application_approve.jsp";
	document.frm_leave_application.submit();
}

function cmdListLast()
{
	document.frm_leave_application.command.value="<%=Command.LAST%>";
	document.frm_leave_application.action="leave_application_approve.jsp";
	document.frm_leave_application.submit();
}

function cmdBack()
{
	document.frm_leave_application.command.value="<%=Command.BACK%>";
	document.frm_leave_application.action="leave_app_src.jsp";
	document.frm_leave_application.submit();
}
function SetAllCheckBoxes(FormName, CheckValue){
	    if(!document.forms[FormName])
		return;
            <%  
               if(records!=null){ 
                for(int i = 0 ; i < records.size() ; i++){
                    String nameInp = "executed"+i; 
                    Vector temp = (Vector) records.get(i);
                    Employee employee = (Employee) temp.get(0);
                    LeaveApplication objleaveApplication = (LeaveApplication) temp.get(1);
                    objleaveApplication.setEmployeeId(employee.getOID());
                    //update by satrya 2014-04-15 objleaveApplication.setEmployeeId(empId);
                    Department department = (Department) temp.get(2);
                     String depHead = SessLeaveApplication.getEmployeeApp(objleaveApplication.getDepHeadApproval());
                     boolean mustApp = false;
                      try{
                        mustApp = SessLeaveApplication.getMustApprove(objleaveApplication, objEmployee.getOID());
                    }catch(Exception E){
                        System.out.println("Exception "+E.toString());
                    }
                     if((isHRDLogin || isAdminExecuteLeave !=0) && mustApp && depHead!=null){
                    %>  
                            document.forms[FormName].<%=nameInp%>.checked = CheckValue;
                    <%
                     }
                 }
               }else{ 
            %>
                    return;
                    <%}%>
        }  
function fnTrapKD()
{
	switch(event.keyCode) 
	{
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
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Leave Application &gt; Leave Form &gt; Leave Excecution<!-- #EndEditable --> 
                  </strong></font> </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td style="background-color:<%=bgColorContent%>; ">  
                  <table width="100%" border="0" cellspacing="1" cellpadding="1">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                                   
							  <%
							  if(privView)
							  {
							  %> 
								<form name="frm_leave_application" method="post" action="">
                                                                  <input type="hidden" name="<%=frmSrcLeaveApp.fieldNames[frmSrcLeaveApp.FRM_FIELD_EMP_NUMBER] %>"  value="<%= srcLeaveApp.getEmpNum() %>">   
                                                                  <input type="hidden" name="<%=frmSrcLeaveApp.fieldNames[frmSrcLeaveApp.FRM_FIELD_FULLNAME] %>"  value="<%= srcLeaveApp.getFullName() %>" >
                                                                  <input type="hidden" name="<%=frmSrcLeaveApp.fieldNames[frmSrcLeaveApp.FRM_FIELD_DEPARTMENT] %>"  value="<%= srcLeaveApp.getDepartmentId() %>" >
                                                                  <input type="hidden" name="<%=frmSrcLeaveApp.fieldNames[frmSrcLeaveApp.FRM_FIELD_SECTION] %>"  value="<%= srcLeaveApp.getSectionId() %>" >
                                                                  <input type="hidden" name="<%=frmSrcLeaveApp.fieldNames[frmSrcLeaveApp.FRM_FIELD_POSITION] %>"  value="<%= srcLeaveApp.getPositionId() %>" >
                                                                  <input type="hidden" name="<%=frmSrcLeaveApp.fieldNames[frmSrcLeaveApp.FRM_FIELD_SUBMISSION]%>" <%=(srcLeaveApp.isSubmission() ? "checked" : "")%>>
                                                                  <input type="hidden" name="<%=frmSrcLeaveApp.fieldNames[frmSrcLeaveApp.FRM_FIELD_SUBMISSION_DATE] %>"  value="<%= Formater.formatDate(srcLeaveApp.getSubmissionDate(),"yyyy-MM-dd") %>" >
								  <input type="hidden" name="command" value="">
								  <input type="hidden" name="start" value="<%=start%>">
								  <input type="hidden" name="hidden_leave_application_id" value="<%=oidLeave%>">
								  <%
								  if((records!=null)&&(records.size()>0))
								  {
									 out.println(drawList(records,isHRDLogin,isAdminExecuteLeave,objEmployee.getOID(),hashViewPeriod)); 
								  }										  
								  else
								  {
									  out.println("<span class=\"comment\"><br>&nbsp;Records is empty ...</span>");
								  }
								  %>
								  <table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr> 
									  <td> 
										<table width="100%" cellspacing="0" cellpadding="3">
										 <!-- <tr> 
											<td align="right"><a href="Javascript:SetAllCheckBoxes('execute', true)">Select All</a> | <a href="Javascript:SetAllCheckBoxes('execute', false)">Deselect All</a></td>
										  </tr>-->
                                                                                  <tr> 
											<td> 
											  <% 
											  ctrLine.setLocationImg(approot+"/images");
											  ctrLine.initDefault();
											  out.println(ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet));
											  %>
											</td>
										  </tr>
										  <!-- update by satrya 2013-03-6 -->
										 <!-- <tr>
										  	<td><a href="Javascript:SetAllCheckBoxes('frm_leave_application', true)">Select All</a> | <a href="Javascript:SetAllCheckBoxes('frm_leave_application', false)">Deselect All</a></td>
										  </tr>-->
										</table>
									  </td>
									</tr>
									<tr> 
									  <td width="46%">&nbsp;</td>
									</tr>
									<tr> 
									  <td width="46" nowrap align="left" class="command">
										    <table width="51%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td colspan="3">
                                                 <table><tr>
                                                  <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To Search Leave Application"></a></td>
                                                  <td width="5"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                  <td width="229" nowrap> <a href="javascript:cmdBack()" class="command">Back To Search Leave Application</a></td>
                                                  <td width="5" ></td>   
                                                  <%
                                                  if((records!=null)&&(records.size()>0)){
                                                      if(isHRDLogin || isAdminExecuteLeave !=0){
                                                  %>    
                                                    <td width="24"><a href="javascript:cmdProccess()" onMouseOut="MM_swapImgRestore()" onMouseOut="MM_swapImgRestore()" ><img name="Image300" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add Leave Application" ></a></td>
                                                    <td width="229" nowrap><a href="javascript:cmdProccess()" class="command">Prosess</a></td>
                                                  <%
                                                      }
                                                  }else{
                                                  %>
                                                    <td width="24">&nbsp;</td>
                                                    <td width="229" nowrap>&nbsp;</td>
                                                  <%
                                                  }
                                                  %>
                                                 </tr>
                                                </table>
                                              </td>
                                              </tr>
                                            </table>
									  </td>
									</tr>
								  </table>
                                                                  
                                                                  <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>" value="">                           
                                                                  <input type="hidden" name="oid_employee" value="">     
                                                                  <input type="hidden" name="oid_period" value="">
								</form>
								<%
								}
								else
								{
								%>
								<div align="center">You do not have sufficient privilege to access this page.</div>
								<%
								}
								%>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>>
      <%@ include file = "../../main/footer.jsp" %>
     </td>
  </tr>
</table>
</body>
</html>
